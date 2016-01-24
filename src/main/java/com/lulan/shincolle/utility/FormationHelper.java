package com.lulan.shincolle.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;

/** FORMATION HELPER
 * 
 *  formation process:
 *  1. pointer GUI set formation (client to server)
 *  2. server apply formation id to team (sync data to client)
 *  3. ship update formation buff (calc new attrs and sync data to client)
 *     slow update: check every X ticks
 *     fast update: check update flag every Y ticks 
 */
public class FormationHelper {

	
	public FormationHelper() {}
	
	/** set current team formation id */
	public static void setFormationID(ExtendPlayerProps props, int formatID) {
		if(props != null) {
			setFormationID(props, props.getPointerTeamID(), formatID);
		}
	}
	
	/** set team formation id */
	public static void setFormationID(ExtendPlayerProps props, int teamID, int formatID) {
		if(props != null) {
			int num = props.getNumberOfShip(teamID);
			
			if(num > 4 && formatID > 0) {	//can apply formation
				setFormationForShip(props, teamID, formatID);
				props.setFormatID(teamID, formatID);
				
				//update formation guard position
				BasicEntityShip[] ships = props.getShipEntityByMode(2);
				FormationHelper.applyFormationMoving(ships, formatID);
			}
			else {
				setFormationForShip(props, teamID, 0);
				props.setFormatID(teamID, 0);
			}
			
			//sync formation data
			props.sendSyncPacket(1);
		}
	}
	
	/** set formation id and pos to ship */
	public static void setFormationForShip(ExtendPlayerProps props, int teamID, int formatID) {
		BasicEntityShip ship = null;
		float buffMOV = getFormationBuffValue(formatID, 0)[ID.Formation.MOV];
		float maxMOV = 1000F;
		float temp = 0F;
		
		//set buff value to ship
		for(int i = 0; i < 6; i++) {
			ship = props.getShipEntity(teamID, i);
			
			if(ship != null) {
				ship.setUpdateFlag(ID.FU.FormationBuff, true);  //set update
				
				temp = ship.getStateFinalBU(ID.MOV);			//get MIN moving speed in team
				if(temp < maxMOV) maxMOV = temp;
			}
		}
		
		//apply same moving speed to all ships in team
		maxMOV += buffMOV;  //moving speed for formation
		
		for(int j = 0; j < 6; j++) {
			ship = props.getShipEntity(teamID, j);
			
			if(ship != null) {
				ship.setEffectFormationFixed(ID.FormationFixed.MOV, maxMOV);		//set moving speed
				int sid = ship.getShipUID();
				
				//check if same ship in other team, cancel the buff in other team
				for(int k = 0; k < 9; k++) {
					//check different team
					if(k != teamID) {
						int[] temp2 = props.checkIsInTeam(sid, k);

						//get ship in other team with formation
						if(temp2[1] > 0) {
							//clear formation buff
							props.setFormatID(k, 0);
//							LogHelper.info("DEBUG : check format "+k+" "+temp2[0]+" "+temp2[1]+" "+ship);
							//set ship update flag
							for(int m = 0; m < 6; m++) {
								ship = props.getShipEntity(k, m);
								
								if(ship != null) {
									ship.setUpdateFlag(ID.FU.FormationBuff, true);  //set update
								}
							}//end for set ship flag
						}//end if ship in other team
					}//end if different team
				}//end for check ship in other team
			}//end if get ship
		}//end for check all ship in team
	}
	
	/** get formation moving speed, SERVER SIDE ONLY */
	public static float getFormationMOV(ExtendPlayerProps props, int teamID) {
		float val = 0F;
		
		if(props != null && teamID >= 0 && teamID < 9) {
			val = props.getMinMOVInTeam(teamID) +
				  getFormationBuffValue(props.getFormatID(teamID), 0)[ID.Formation.MOV];
		}
		
		return val;
	}
	
	/** get buff value by formation/slot id */
	public static float[] getFormationBuffValue(int formationID, int slotID) {
		float[] fvalue = Values.FormationBuffsMap.get(formationID * 10 + slotID);
		
		if(fvalue != null) {
			return fvalue;
		}
		return Values.zeros13;
	}
	
	/** apply ship moving by flag ship position, check is guard BLOCK or ENTITY */
	public static void applyFormationMoving(BasicEntityShip[] ships, int formatID) {
		if(ships != null) {
			//get flag ship
			for(BasicEntityShip s : ships) {
				if(s != null) {
					applyFormationMoving(ships, formatID, (int)s.posX, (int)s.posY, (int)s.posZ);
					break;
				}
			}
		}
	}
	
	/** calc formation guard position
	 * 
	 *  前置條件:
	 *  ship必須設定在StatesMinor設定formatPos / formatType
	 *  此方法根據type跟pos設定移動位置
	 *  
	 *  0. 陣型ID
	 *     1: 單縱陣, LineAhead
	 *     2: 複縱陣, Double Line
	 *     3: 輪形陣, Diamond
	 *     4: 梯形陣, Echelon
	 *     5: 單橫陣, Line Abreast
	 * 
	 *  1. 陣型方向
	 *     以1 or 2號位當隊長, 計算隊長原本位置跟目標位置的x,z座標差
	 *     x,z座標差較大的那個作為陣型的前後方向, ex: 若x較大, 則隊伍前後方向以x軸為準
	 *  
	 *  2. 陣型位置: 1 or 2為旗艦
	 *  
	 *     a. 單縱陣         1      b. 複縱陣                                c. 輪型陣(6船)       (5船, 船位以搜尋到順序為準)
	 *                2               3  4              2             2
	 *                3               1  2          3   6   4     3       4
	 *                4               5  6              1             1
	 *                5                                 5             5
	 *                6
	 *                
	 *     d. 梯形陣               1    e. 單橫陣
 	 *                 2
 	 *               3            5 3 1 2 4 6
 	 *             4 
 	 *           5
	 *         6
	 */
	public static void applyFormationMoving(BasicEntityShip[] ships, int formatID, int x, int y, int z) {
		//get flag ship
		int formatPos = 0;
		double dx, dy, dz;
		boolean alongX, faceP;  //along x axis, face positive direction
		BasicEntityShip flagShip = null;
		EntityPlayer owner = null;
		
		//get the toppest ship as flag ship
		for(BasicEntityShip s : ships) {
			if(s != null) {
				flagShip = s;
				owner = EntityHelper.getEntityPlayerByUID(flagShip.getPlayerUID());
				break;
			}
		}
		
		if(flagShip != null && owner != null) {
			//calc moving direction
			dx = (double)x - flagShip.posX;
			dz = (double)z - flagShip.posZ;
			alongX = CalcHelper.isAbsGreater(dx, dz);
			
			if(alongX) {		//along X
				if(dx >= 0) {	//face positive
					faceP = true;
				}
				else {			//face negative
					faceP = false;
				}
			}
			else {				//along Z
				if(dz >= 0) {	//face positive
					faceP = true;
				}
				else {			//face negative
					faceP = false;
				}
			}
			
			/** calc position
			 *  
			 *  1. line ahead:
			 *     applyLineAheadPos傳入目前陣型位置, 回傳下一船位置, 不需要formatPos
			 *     只根據傳入位置自動向陣型方向延伸3格
			 *     
			 *  2. other:
			 *     傳入flag ship位置跟formatPos, 無回傳值
			 *     根據flag ship位置跟formatPos計算各船位置
			 *     
			 */
			int[] newPos = new int[] {x, y, z};
			
			for(BasicEntityShip s : ships) {
				if(s != null) {
					switch(formatID) {
					case 1:  //line ahead
					case 4:  //echelon
						//get next pos
						newPos = setFormationPos1(s, formatID, alongX, faceP, newPos[0], newPos[1], newPos[2]);
						break;
					case 2:  //double line
					case 3:  //diamond
					case 5:  //line abreast
						setFormationPos2(s, formatID, alongX, faceP, newPos[0], newPos[1], newPos[2]);
						break;
					default:
						//apply moving
						EntityHelper.applyShipGuard(s, 1, x, y, z);
						break;
					}
					
					//sync guard
					CommonProxy.channelE.sendTo(new S2CEntitySync(s, 3), (EntityPlayerMP) owner);
				}
			}//end apply to all ships
		}//end get flag ship
	}
	
	/** apply formation position type 1
	 * 
	 *  適用於直線延伸陣型: line ahead / echelon
	 *  
	 *  input:  prev target pos
	 *  exec:   apply ship guard
	 *  return: next target pos
	 */
	public static int[] setFormationPos1(BasicEntityShip ship, int formatType, boolean alongX, boolean faceP, int x, int y, int z) {
		//get safe pos
		int[] pos = BlockHelper.getSafeBlockWithin5x5(ship.worldObj, x, y, z);
		
		if(pos != null) {
			//apply moving
			EntityHelper.applyShipGuard(ship, 1, pos[0], pos[1], pos[2]);
			LogHelper.info("DEBUG : apply formation move: safe: "+pos[0]+" "+pos[1]+" "+pos[2]);
			
			//return next pos
			switch(formatType) {
			case 4:  //echelon
				return nextEchelonPos(faceP, pos[0], pos[1], pos[2]);
			}
			
			//default formaion = line ahead
			return nextLineAheadPos(alongX, faceP, pos[0], pos[1], pos[2]);
		}
		else {
			//apply moving
			EntityHelper.applyShipGuard(ship, 1, x, y, z);
			LogHelper.info("DEBUG : apply formation move: not safe: "+x+" "+y+" "+z);
			
			return new int[] {x, y, z};
		}
	}
	
	/** apply formation position type 2
	 *  
	 *  適用於非直線延伸陣型: double line / diamond / line abreast
	 * 
	 *  input: flag ship pos
	 *  exec:  apply ship guard
	 */
	public static void setFormationPos2(BasicEntityShip ship, int formatType, boolean alongX, boolean faceP, int x, int y, int z) {
		int formatPos = ship.getStateMinor(ID.M.FormatPos);
		int[] pos = new int[] {x, y, z};
		
		//check error position
		if(formatPos < 0 || formatPos > 5) formatPos = 0;

		//calc next pos
		switch(formatType) {
		case 2:  //double line
			pos = nextDoubleLinePos(alongX, faceP, formatPos, pos[0], pos[1], pos[2]);
			break;
		case 3:  //diamond
			pos = nextDiamondPos(alongX, faceP, formatPos, pos[0], pos[1], pos[2]);
			break;
		case 5:  //line abreast
			pos = nextLineAbreastPos(alongX, formatPos, pos[0], pos[1], pos[2]);
			break;
		}
		
		//get safe pos
		pos = BlockHelper.getSafeBlockWithin5x5(ship.worldObj, pos[0], pos[1], pos[2]);
		
		if(pos != null) {
			//apply moving
			EntityHelper.applyShipGuard(ship, 1, pos[0], pos[1], pos[2]);
			LogHelper.info("DEBUG : apply formation move: safe: "+pos[0]+" "+pos[1]+" "+pos[2]);
		}
		else {
			//apply moving
			EntityHelper.applyShipGuard(ship, 1, x, y, z);
			LogHelper.info("DEBUG : apply formation move: not safe: "+x+" "+y+" "+z);
		}
	}
	
	/** calc next LINE AHEAD pos
	 *  單縱陣         0
	 *          1
	 *          2
	 *          3
	 *          4
	 *          5
	 */
	public static int[] nextLineAheadPos(boolean alongX, boolean faceP, int x, int y, int z) {
		int[] pos = new int[] {x, y, z};
		
		//calc next pos
		if(alongX) {		//along X
			if(faceP) {		//face positive
				pos[0] = pos[0] - 3;
			}
			else {			//face negative
				pos[0] = pos[0] + 3;
			}
		}
		else {				//along Z
			if(faceP) {		//face positive
				pos[2] = pos[2] - 3;
			}
			else {			//face negative
				pos[2] = pos[2] + 3;
			}
		}
		
		return pos;
	}
	
	/** calc next DOUBLE LINE pos
	 *  複縱陣      2  3
	 *         0  1
	 *         4  5
	 */
	public static int[] nextDoubleLinePos(boolean alongX, boolean faceP, int formatPos, int x, int y, int z) {
		int[] pos = new int[] {x, y, z};
		
		//calc target block by formatPos
		switch(formatPos) {
		case 1:
			if(alongX) {		//along X
				pos[2] = pos[2] + 3;
			}
			else {				//along Z
				pos[0] = pos[0] + 3;
			}
			break;
		case 2:
			if(alongX) {		//along X
				if(faceP) {		//face positive
					pos[0] = pos[0] + 3;
				}
				else {			//face negative
					pos[0] = pos[0] - 3;
				}
			}
			else {				//along Z
				if(faceP) {		//face positive
					pos[2] = pos[2] + 3;
				}
				else {			//face negative
					pos[2] = pos[2] - 3;
				}
			}
			break;
		case 3:
			if(alongX) {		//along X
				if(faceP) {		//face positive
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] + 3;
				}
				else {			//face negative
					pos[0] = pos[0] - 3;
					pos[2] = pos[2] + 3;
				}
			}
			else {				//along Z
				if(faceP) {		//face positive
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] + 3;
				}
				else {			//face negative
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] - 3;
				}
			}
			break;
		case 4:
			if(alongX) {		//along X
				if(faceP) {		//face positive
					pos[0] = pos[0] - 3;
				}
				else {			//face negative
					pos[0] = pos[0] + 3;
				}
			}
			else {				//along Z
				if(faceP) {		//face positive
					pos[2] = pos[2] - 3;
				}
				else {			//face negative
					pos[2] = pos[2] + 3;
				}
			}
			break;
		case 5:
			if(alongX) {		//along X
				if(faceP) {		//face positive
					pos[0] = pos[0] - 3;
					pos[2] = pos[2] + 3;
				}
				else {			//face negative
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] + 3;
				}
			}
			else {				//along Z
				if(faceP) {		//face positive
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] - 3;
				}
				else {			//face negative
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] + 3;
				}
			}
			break;
		}
		
		return pos;
	}
	
	/** calc next DIAMOND pos
	 *  輪型陣              1
	 *         2  5  3
	 *            0
	 *            4
	 */
	public static int[] nextDiamondPos(boolean alongX, boolean faceP, int formatPos, int x, int y, int z) {
		int[] pos = new int[] {x, y, z};
		
		//calc target block by formatPos
		switch(formatPos) {
		case 1:
			if(alongX) {		//along X
				if(faceP) {		//face positive
					pos[0] = pos[0] + 5;
				}
				else {			//face negative
					pos[0] = pos[0] - 5;
				}
			}
			else {				//along Z
				if(faceP) {		//face positive
					pos[2] = pos[2] + 5;
				}
				else {			//face negative
					pos[2] = pos[2] - 5;
				}
			}
			break;
		case 2:
			if(alongX) {		//along X
				if(faceP) {		//face positive
					pos[0] = pos[0] + 1;
					pos[2] = pos[2] - 4;
				}
				else {			//face negative
					pos[0] = pos[0] - 1;
					pos[2] = pos[2] - 4;
				}
			}
			else {				//along Z
				if(faceP) {		//face positive
					pos[0] = pos[0] - 4;
					pos[2] = pos[2] + 1;
				}
				else {			//face negative
					pos[0] = pos[0] - 4;
					pos[2] = pos[2] - 1;
				}
			}
			break;
		case 3:
			if(alongX) {		//along X
				if(faceP) {		//face positive
					pos[0] = pos[0] + 1;
					pos[2] = pos[2] + 4;
				}
				else {			//face negative
					pos[0] = pos[0] - 1;
					pos[2] = pos[2] + 4;
				}
			}
			else {				//along Z
				if(faceP) {		//face positive
					pos[0] = pos[0] + 4;
					pos[2] = pos[2] + 1;
				}
				else {			//face negative
					pos[0] = pos[0] + 4;
					pos[2] = pos[2] - 1;
				}
			}
			break;
		case 4:
			if(alongX) {		//along X
				if(faceP) {		//face positive
					pos[0] = pos[0] - 3;
				}
				else {			//face negative
					pos[0] = pos[0] + 3;
				}
			}
			else {				//along Z
				if(faceP) {		//face positive
					pos[2] = pos[2] - 3;
				}
				else {			//face negative
					pos[2] = pos[2] + 3;
				}
			}
			break;
		case 5:
			if(alongX) {		//along X
				if(faceP) {		//face positive
					pos[0] = pos[0] + 2;
				}
				else {			//face negative
					pos[0] = pos[0] - 2;
				}
			}
			else {				//along Z
				if(faceP) {		//face positive
					pos[2] = pos[2] + 2;
				}
				else {			//face negative
					pos[2] = pos[2] - 2;
				}
			}
			break;
		}
		
		return pos;
	}
	
	/** calc next ECHELON pos
	 *  梯形陣                       0
	 *              1
	 *            2
	 *          3
	 *        4
	 *      5
	 */
	public static int[] nextEchelonPos(boolean faceP, int x, int y, int z) {
		int[] pos = new int[] {x, y, z};
		
		//calc next pos
		if(faceP) {		//face positive
			pos[0] = pos[0] - 2;
			pos[2] = pos[2] - 2;
		}
		else {			//face negative
			pos[0] = pos[0] + 2;
			pos[2] = pos[2] + 2;
		}
		
		return pos;
	}
	
	/** calc next LINE ABREAST pos
	 *  單橫陣
	 *  
	 *  4  2  0  1  3  5
	 *  
	 */
	public static int[] nextLineAbreastPos(boolean alongX, int formatPos, int x, int y, int z) {
		int[] pos = new int[] {x, y, z};
		
		//calc target block by formatPos
		switch(formatPos) {
		case 1:
			if(alongX) {		//along X
				pos[2] = pos[2] + 3;
			}
			else {				//along Z
				pos[0] = pos[0] + 3;
			}
			break;
		case 2:
			if(alongX) {		//along X
				pos[2] = pos[2] - 3;
			}
			else {				//along Z
				pos[0] = pos[0] - 3;
			}
			break;
		case 3:
			if(alongX) {		//along X
				pos[2] = pos[2] + 6;
			}
			else {				//along Z
				pos[0] = pos[0] + 6;
			}
			break;
		case 4:
			if(alongX) {		//along X
				pos[2] = pos[2] - 6;
			}
			else {				//along Z
				pos[0] = pos[0] - 6;
			}
			break;
		case 5:
			if(alongX) {		//along X
				pos[2] = pos[2] + 9;
			}
			else {				//along Z
				pos[0] = pos[0] + 9;
			}
			break;
		}
		
		return pos;
	}
	
	
	
}
