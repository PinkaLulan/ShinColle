package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.Arrays;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.dataclass.AttrsAdv;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.MathHelper;

/** FORMATION HELPER
 * 
 *  formation process:
 *  1. pointer GUI set formation (client to server)
 *  2. server apply formation id to team (sync data to client)
 *  3. ship update formation buff (calc new attrs and sync data to client)
 *     slow update: check every X ticks
 *     fast update: check update flag every Y ticks 
 */
public class FormationHelper
{

	
	public FormationHelper() {}
	
	/**
	 * set current team formation id
	 * 
	 * parms: 0:player eid, 1:world id, 2:formation id
	 */
	public static void setFormationID(int[] parms)
	{
		EntityPlayer player = EntityHelper.getEntityPlayerByID(parms[0], parms[1], false);
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null)
		{
			setFormationID(player, capa.getCurrentTeamID(), parms[2]);
		}
	}
	
	/** set team formation id */
	public static void setFormationID(EntityPlayer player, int teamID, int formatID)
	{
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if(capa != null)
		{
			int num = capa.getNumberOfShip(teamID);
			
			if (num > 4 && formatID > 0)
			{
				//apply formation
				setFormationForShip(capa, teamID, formatID);
				capa.setFormatID(teamID, formatID);
				
				//update formation guard position
				ArrayList<BasicEntityShip> ships = capa.getShipEntityByMode(2);
				
				if (ships.size() > 0)
				{
					//apply command to flag ship only
					BasicEntityShip s = ships.get(0);
					
					//ship is NOT guarding entity and NOT follow owner
					if (s.getStateMinor(ID.M.GuardType) != 2 && !s.getStateFlag(ID.F.CanFollow))
					{
						applyFormationMoving(ships, formatID, MathHelper.floor(s.posX), (int)s.posY, MathHelper.floor(s.posZ), false);
					}
				}
			}
			else
			{
				setFormationForShip(capa, teamID, 0);
				capa.setFormatID(teamID, 0);
			}
			
			//sync formation data
			capa.sendSyncPacket(1);
		}
	}
	
	/** set formation id and position to ship */
	public static void setFormationForShip(CapaTeitoku capa, int teamID, int formatID)
	{
		BasicEntityShip ship = null;
		float temp = 0F;
		
		//set formation id to all ship in team
		for (int j = 0; j < 6; j++)
		{
			ship = capa.getShipEntity(teamID, j);
			
			if (ship != null)
			{
				//set update flag
				if (ship != null) ship.setUpdateFlag(ID.FlagUpdate.FormationBuff, true);
				
				//check if same ship in other team, cancel the buff in other team
				int sid = ship.getShipUID();
				
				for (int k = 0; k < 9; k++)
				{
					//check different team
					if (k != teamID)
					{
						int[] temp2 = capa.checkIsInTeam(sid, k);

						//if get ship in other team with formation
						if (temp2[1] > 0)
						{
							//reset formation id
							capa.setFormatID(k, 0);
							
							//set ship update flag in the team
							for (int m = 0; m < 6; m++)
							{
								ship = capa.getShipEntity(k, m);
								if (ship != null) ship.setUpdateFlag(ID.FlagUpdate.FormationBuff, true);
							}//end for set ship flag
						}//end if ship in other team
					}//end if different team
				}//end for check ship in other team
			}//end if get ship
		}//end for check all ship in team
	}
	
	/** get moving speed in formation team
	 *  speed = min speed in team + formation MOV buff
	 */
	public static float getFormationMOV(BasicEntityShip ship)
	{
		float mov = 0F;
		
		//check formation speed
        if (ship != null)
        {
        	//ship is in formation
        	if (ship.getStateMinor(ID.M.FormatType) > 0)
        	{
        		AttrsAdv attrs = (AttrsAdv) ship.getAttrs();
        		
        		if (attrs != null)
        		{
        			mov = attrs.getMinMOV() + attrs.getAttrsFormation(ID.Attrs.MOV) * (float)ConfigHandler.scaleShip[ID.AttrsBase.MOV];
        		}
        	}
        }
        
        //check limit
        if (mov > ConfigHandler.limitShipAttrs[ID.Attrs.MOV]) mov = (float) ConfigHandler.limitShipAttrs[ID.Attrs.MOV];
        else if (mov < 0F) mov = 0F;
        
        return mov;
	}
	
	/** get buff value by formation/slot id */
	public static float[] getFormationBuffValue(int formationID, int slotID)
	{
		float[] fvalue = Values.FormationAttrs.get(formationID * 10 + slotID);
		
		if (fvalue != null)
		{
			return Arrays.copyOf(fvalue, fvalue.length);
		}
		
		return AttrsAdv.getResetFormationValue();
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
	 *     a. 單縱陣           1      b. 複縱陣                                c. 輪型陣(6船)   (5船, 船位以搜尋到順序為準)
	 *                2                              2               2
	 *                3            3  4          3   6   4       3       4
	 *                4            1  2              1               1
	 *                5            5  6              5               5
	 *                6
	 *                
	 *     d. 梯形陣                  1    e. 單橫陣
 	 *                 2
 	 *               3           5 3 1 2 4 6
 	 *             4 
 	 *           5
	 *         6
	 */
	public static void applyFormationMoving(ArrayList<BasicEntityShip> ships, int formatID, int x, int y, int z, boolean samePosChecking)
	{
		//get flag ship
		BasicEntityShip flagShip = null;
		EntityPlayer owner = null;
		
		//get the toppest ship as flag ship
		for (BasicEntityShip s : ships)
		{
			if (s != null)
			{
				flagShip = s;
				owner = EntityHelper.getEntityPlayerByUID(flagShip.getPlayerUID());
				
				//check owner is same dimension
				if (owner.dimension == s.dimension)
				{
					break;
				}
				else
				{
					return;
				}
			}
		}
		
		if (flagShip != null && owner != null)
		{
			//backup flagship guard position
			int[] oldPosition = null;
			
			if (flagShip.getGuardedPos(1) > 0 && flagShip.getGuardedPos(4) >= 0)
			{
				oldPosition = new int[] {flagShip.getGuardedPos(0), flagShip.getGuardedPos(1), flagShip.getGuardedPos(2)};
			}
			
			//along x axis, face positive direction
			boolean[] faceXP = getFormationDirection(x, z, flagShip.posX, flagShip.posZ);
			
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
			
			for (BasicEntityShip s : ships)
			{
				if (s != null)
				{
					switch (formatID)
					{
					case 1:  //line ahead
					case 4:  //echelon
						//get next pos
						newPos = setFormationPosAndApplyGuardPos1(s, formatID, faceXP[0], faceXP[1], newPos[0], newPos[1], newPos[2]);
						break;
					case 2:  //double line
					case 3:  //diamond
					case 5:  //line abreast
						setFormationPosAndApplyGuardPos2(s, formatID, faceXP[0], faceXP[1], newPos[0], newPos[1], newPos[2]);
						break;
					default:
						//apply moving
						applyShipGuard(s, x, y, z, true);
						break;
					}
					
					//show emotes
					s.applyEmotesReaction(5);
					
					//sync guard
					CommonProxy.channelE.sendTo(new S2CEntitySync(s, S2CEntitySync.PID.SyncShip_Minor), (EntityPlayerMP) owner);
				}
			}//end apply to all ships
			
			//check flag ship is same position
			if (samePosChecking && oldPosition != null && flagShip.getGuardedPos(0) == oldPosition[0] &&
				flagShip.getGuardedPos(1) == oldPosition[1] && flagShip.getGuardedPos(2) == oldPosition[2])
			{
				//clear guard state for all ships in team
				for (BasicEntityShip s : ships)
				{
					if (s != null)
					{
						s.setGuardedPos(-1, -1, -1, 0, 0);		//reset guard position
						s.setGuardedEntity(null);
						s.setStateFlag(ID.F.CanFollow, true);	//set follow
					}
				}
			}
			
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
	public static int[] setFormationPosAndApplyGuardPos1(BasicEntityShip ship, int formatType, boolean alongX, boolean faceP, int x, int y, int z)
	{
		//get safe pos
		int[] pos = BlockHelper.getSafeBlockWithin5x5(ship.world, x, y, z);
		
		if (pos != null)
		{
			//apply moving
			applyShipGuard(ship, pos[0], pos[1], pos[2], true);
			LogHelper.debug("DEBUG: apply formation move: safe: "+pos[0]+" "+pos[1]+" "+pos[2]);
			
			//return next pos
			switch (formatType)
			{
			case 4:  //echelon
				return nextEchelonPos(faceP, pos[0], pos[1], pos[2]);
			}
			
			//default formaion = line ahead
			return nextLineAheadPos(alongX, faceP, pos[0], pos[1], pos[2]);
		}
		else
		{
			//apply moving
			applyShipGuard(ship, x, y, z, true);
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
	public static void setFormationPosAndApplyGuardPos2(BasicEntityShip ship, int formatType, boolean alongX, boolean faceP, int x, int y, int z)
	{
		int formatPos = ship.getStateMinor(ID.M.FormatPos);
		int[] pos = new int[] {x, y, z};
		
		//check error position
		if (formatPos < 0 || formatPos > 5) formatPos = 0;

		//calc next pos
		switch (formatType)
		{
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
		pos = BlockHelper.getSafeBlockWithin5x5(ship.world, pos[0], pos[1], pos[2]);
		
		if (pos != null)
		{
			//apply moving
			applyShipGuard(ship, pos[0], pos[1], pos[2], true);
			LogHelper.debug("DEBUG: apply formation move: safe: "+pos[0]+" "+pos[1]+" "+pos[2]);
		}
		else
		{
			//apply moving
			applyShipGuard(ship, x, y, z, true);
			LogHelper.debug("DEBUG: apply formation move: not safe: "+x+" "+y+" "+z);
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
	public static int[] nextLineAheadPos(boolean alongX, boolean faceP, int x, int y, int z)
	{
		int[] pos = new int[] {x, y, z};
		
		//calc next pos
		if (alongX)
		{				//along X
			if (faceP)
			{			//face positive
				pos[0] = pos[0] - 3;
			}
			else
			{			//face negative
				pos[0] = pos[0] + 3;
			}
		}
		else
		{				//along Z
			if (faceP)
			{			//face positive
				pos[2] = pos[2] - 3;
			}
			else
			{			//face negative
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
	public static int[] nextDoubleLinePos(boolean alongX, boolean faceP, int formatPos, int x, int y, int z)
	{
		int[] pos = new int[] {x, y, z};
		
		//calc target block by formatPos
		switch (formatPos)
		{
		case 1:
			if (alongX)
			{				//along X
				pos[2] = pos[2] + 3;
			}
			else
			{				//along Z
				pos[0] = pos[0] + 3;
			}
			break;
		case 2:
			if (alongX)
			{				//along X
				if (faceP)
				{			//face positive
					pos[0] = pos[0] + 3;
				}
				else
				{			//face negative
					pos[0] = pos[0] - 3;
				}
			}
			else
			{				//along Z
				if (faceP)
				{			//face positive
					pos[2] = pos[2] + 3;
				}
				else
				{			//face negative
					pos[2] = pos[2] - 3;
				}
			}
			break;
		case 3:
			if (alongX)
			{				//along X
				if (faceP)
				{			//face positive
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] + 3;
				}
				else
				{			//face negative
					pos[0] = pos[0] - 3;
					pos[2] = pos[2] + 3;
				}
			}
			else
			{				//along Z
				if (faceP)
				{			//face positive
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] + 3;
				}
				else
				{			//face negative
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] - 3;
				}
			}
			break;
		case 4:
			if (alongX)
			{				//along X
				if (faceP)
				{			//face positive
					pos[0] = pos[0] - 3;
				}
				else
				{			//face negative
					pos[0] = pos[0] + 3;
				}
			}
			else
			{				//along Z
				if (faceP)
				{			//face positive
					pos[2] = pos[2] - 3;
				}
				else
				{			//face negative
					pos[2] = pos[2] + 3;
				}
			}
			break;
		case 5:
			if (alongX)
			{				//along X
				if (faceP)
				{			//face positive
					pos[0] = pos[0] - 3;
					pos[2] = pos[2] + 3;
				}
				else
				{			//face negative
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] + 3;
				}
			}
			else
			{				//along Z
				if (faceP)
				{			//face positive
					pos[0] = pos[0] + 3;
					pos[2] = pos[2] - 3;
				}
				else
				{			//face negative
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
	public static int[] nextDiamondPos(boolean alongX, boolean faceP, int formatPos, int x, int y, int z)
	{
		int[] pos = new int[] {x, y, z};
		
		//calc target block by formatPos
		switch (formatPos)
		{
		case 1:
			if (alongX)
			{				//along X
				if (faceP)
				{			//face positive
					pos[0] = pos[0] + 5;
				}
				else
				{			//face negative
					pos[0] = pos[0] - 5;
				}
			}
			else
			{				//along Z
				if (faceP)
				{			//face positive
					pos[2] = pos[2] + 5;
				}
				else
				{			//face negative
					pos[2] = pos[2] - 5;
				}
			}
			break;
		case 2:
			if (alongX)
			{				//along X
				if (faceP)
				{			//face positive
					pos[0] = pos[0] + 1;
					pos[2] = pos[2] - 4;
				}
				else
				{			//face negative
					pos[0] = pos[0] - 1;
					pos[2] = pos[2] - 4;
				}
			}
			else
			{				//along Z
				if (faceP)
				{			//face positive
					pos[0] = pos[0] - 4;
					pos[2] = pos[2] + 1;
				}
				else
				{			//face negative
					pos[0] = pos[0] - 4;
					pos[2] = pos[2] - 1;
				}
			}
			break;
		case 3:
			if (alongX)
			{				//along X
				if (faceP)
				{			//face positive
					pos[0] = pos[0] + 1;
					pos[2] = pos[2] + 4;
				}
				else
				{			//face negative
					pos[0] = pos[0] - 1;
					pos[2] = pos[2] + 4;
				}
			}
			else
			{				//along Z
				if (faceP)
				{			//face positive
					pos[0] = pos[0] + 4;
					pos[2] = pos[2] + 1;
				}
				else
				{			//face negative
					pos[0] = pos[0] + 4;
					pos[2] = pos[2] - 1;
				}
			}
			break;
		case 4:
			if (alongX)
			{				//along X
				if (faceP)
				{			//face positive
					pos[0] = pos[0] - 3;
				}
				else
				{			//face negative
					pos[0] = pos[0] + 3;
				}
			}
			else
			{				//along Z
				if (faceP)
				{			//face positive
					pos[2] = pos[2] - 3;
				}
				else
				{			//face negative
					pos[2] = pos[2] + 3;
				}
			}
			break;
		case 5:
			if (alongX)
			{				//along X
				if (faceP)
				{			//face positive
					pos[0] = pos[0] + 2;
				}
				else
				{			//face negative
					pos[0] = pos[0] - 2;
				}
			}
			else
			{				//along Z
				if (faceP)
				{			//face positive
					pos[2] = pos[2] + 2;
				}
				else
				{			//face negative
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
	public static int[] nextEchelonPos(boolean faceP, int x, int y, int z)
	{
		int[] pos = new int[] {x, y, z};
		
		//calc next pos
		if (faceP)
		{			//face positive
			pos[0] = pos[0] - 2;
			pos[2] = pos[2] - 2;
		}
		else
		{			//face negative
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
	public static int[] nextLineAbreastPos(boolean alongX, int formatPos, int x, int y, int z)
	{
		int[] pos = new int[] {x, y, z};
		
		//calc target block by formatPos
		switch (formatPos)
		{
		case 1:
			if (alongX)
			{				//along X
				pos[2] = pos[2] + 3;
			}
			else
			{				//along Z
				pos[0] = pos[0] + 3;
			}
			break;
		case 2:
			if (alongX)
			{				//along X
				pos[2] = pos[2] - 3;
			}
			else
			{				//along Z
				pos[0] = pos[0] - 3;
			}
			break;
		case 3:
			if (alongX)
			{				//along X
				pos[2] = pos[2] + 6;
			}
			else
			{				//along Z
				pos[0] = pos[0] + 6;
			}
			break;
		case 4:
			if (alongX)
			{				//along X
				pos[2] = pos[2] - 6;
			}
			else
			{				//along Z
				pos[0] = pos[0] - 6;
			}
			break;
		case 5:
			if (alongX)
			{				//along X
				pos[2] = pos[2] + 9;
			}
			else
			{				//along Z
				pos[0] = pos[0] + 9;
			}
			break;
		}
		
		return pos;
	}
	
	/** calc formation face direction
	 *  return {along X axis, face positive}
	 */
	public static boolean[] getFormationDirection(double toX, double toZ, double fromX, double fromZ)
	{
		double dx = toX - fromX;
		double dz = toZ - fromZ;
		boolean[] face = new boolean[2];
		
		face[0] = CalcHelper.isAbsGreater(dx, dz);
		
		if (face[0])
		{				//along X
			face[1] = dx >= 0 ? true : false;
		}
		else
		{				//along Z
			face[1] = dz >= 0 ? true : false;
		}
		
		return face;
	}
	
	/** return guarding entity position in formation
	 *  
	 *  1. set guard target as flagship position
	 *  2. calc formation position by guard target posXZ and oldXZ
	 */
	public static double[] getFormationGuardingPos(IShipAttackBase host, Entity target, double oldX, double oldZ)
	{
		int formatID = host.getStateMinor(ID.M.FormatType);  //get formation ID
		int formatPos = host.getStateMinor(ID.M.FormatPos);
		double[] pos = new double[] {target.posX, target.posY, target.posZ};
		int[] tempPos = null;
		
		//no formation, return target position
		if (formatID <= 0) return pos;
		
		//check error position
		if (formatPos < 0 || formatPos > 5) formatPos = 0;
		
		//calc formation position by formation type
		boolean[] faceXP = getFormationDirection(target.posX, target.posZ, oldX, oldZ);
		
		tempPos = calcFormationPos(formatID, formatPos, pos, faceXP);
		
		if (tempPos != null)
		{
			//check block is safe
			tempPos = BlockHelper.getSafeBlockWithin5x5(target.world, tempPos[0], tempPos[1], tempPos[2]);
			
			if (tempPos != null)
			{
				pos[0] = tempPos[0];
				pos[1] = tempPos[1];
				pos[2] = tempPos[2];
			}
		}
		
		return pos;
	}
	
	/** calc formation position */
	public static int[] calcFormationPos(int formatID, int formatPos, double[] flagshipPos, boolean[] faceXP)
	{
		int[] newPos = new int[] {MathHelper.floor(flagshipPos[0]), (int)(flagshipPos[1]+0.5D), MathHelper.floor(flagshipPos[2])};
		
		//host is flagship
		if (formatPos == 0)
		{
			return newPos;
		}
		else
		{
			switch (formatID)
			{
			case 1:  //line ahead
				for (int i = 0; i < formatPos; i++)
				{
					newPos = nextLineAheadPos(faceXP[0], faceXP[1], newPos[0], newPos[1], newPos[2]);
				}
				break;
			case 4:  //echelon
				for (int i = 0; i < formatPos; i++)
				{
					newPos = nextEchelonPos(faceXP[1], newPos[0], newPos[1], newPos[2]);
				}
				break;
			case 2:  //double line
				newPos = nextDoubleLinePos(faceXP[0], faceXP[1], formatPos, newPos[0], newPos[1], newPos[2]);
				break;
			case 3:  //diamond
				newPos = nextDiamondPos(faceXP[0], faceXP[1], formatPos, newPos[0], newPos[1], newPos[2]);
				break;
			case 5:  //line abreast
				newPos = nextLineAbreastPos(faceXP[0], formatPos, newPos[0], newPos[1], newPos[2]);
				break;
			}
			
			return newPos;
		}
	}
	

	/** set ship guard, and check guard position is not same
	 * 
	 *  GuardType = 1: guard a block
	 */
	public static void applyShipGuard(BasicEntityShip ship, int x, int y, int z, boolean forceSet)
	{
		if (ship != null)
		{
			int gx = ship.getStateMinor(ID.M.GuardX);
			int gy = ship.getStateMinor(ID.M.GuardY);
			int gz = ship.getStateMinor(ID.M.GuardZ);
			int gd = ship.getStateMinor(ID.M.GuardDim);
			
			//clear attack target
			ship.setAttackTarget(null);
			ship.setEntityTarget(null);
			
			//same guard position, cancel guard mode
			if (!forceSet && gx == x && gy == y && gz == z && gd == ship.world.provider.getDimension())
			{
				ship.setGuardedPos(-1, -1, -1, 0, 0);		//reset guard position
				ship.setGuardedEntity(null);
				ship.setStateFlag(ID.F.CanFollow, true);	//set follow
			}
			//apply guard mode
			else
			{
				ship.setSitting(false);						//stop sitting
				ship.setGuardedEntity(null);				//clear guard target
				ship.setGuardedPos(x, y, z, ship.world.provider.getDimension(), 1);
				ship.setStateFlag(ID.F.CanFollow, false);	//stop follow
				
				if (!ship.getStateFlag(ID.F.NoFuel))
				{
					//show emotes
					ship.applyEmotesReaction(5);
					
					Entity ent = ship.getRidingEntity();
					
					if (ent instanceof BasicEntityMount)
					{
						((BasicEntityMount) ent).getShipNavigate().tryMoveToXYZ(x, y, z, 1D);
						((BasicEntityMount) ent).getLookHelper().setLookPosition(x, y, z, 30F, 40F);
					}
					else
					{
						ship.getShipNavigate().tryMoveToXYZ(x, y, z, 1D);
						ship.getLookHelper().setLookPosition(x, y, z, 30F, 40F);
					}
				}
			}
		}
	}
	
	/** set ship guard, and check guard position is not same
	 * 
	 *  GuardType = 2: guard an entity
	 */
	public static void applyShipGuardEntity(BasicEntityShip ship, Entity guarded)
	{
		Entity getEnt = ship.getGuardedEntity();
		
		//same guard position, cancel guard
		if (getEnt != null && getEnt.getEntityId() == guarded.getEntityId())
		{
			ship.setGuardedPos(-1, -1, -1, 0, 0);		//reset guard position
			ship.setGuardedEntity(null);
			ship.setStateFlag(ID.F.CanFollow, true);	//set follow
		}
		//apply guard
		else
		{
			ship.setSitting(false);						//stop sitting
			ship.setGuardedPos(-1, -1, -1, guarded.world.provider.getDimension(), 2);
			ship.setGuardedEntity(guarded);
			ship.setStateFlag(ID.F.CanFollow, false);	//stop follow
			
			if (!ship.getStateFlag(ID.F.NoFuel))
			{
				//show emotes
				ship.applyEmotesReaction(5);
				
				Entity ent = ship.getRidingEntity();
				
				if(ent instanceof BasicEntityMount)
				{
					((BasicEntityMount) ent).getShipNavigate().tryMoveToEntityLiving(guarded, 1D);
				}
				else
				{
					ship.getShipNavigate().tryMoveToEntityLiving(guarded, 1D);
				}
			}
		}
	}
	
	/** set ship attack target with team list */
	public static void applyTeamAttack(EntityPlayer player, int meta, Entity target)
	{
		//NOTE: player跟target的null check已經在呼叫此方法前處理過
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);

		if (capa != null)
		{
			int worldID = player.world.provider.getDimension();
			ArrayList<BasicEntityShip> ships = capa.getShipEntityByMode(meta);
			
			if (ships.isEmpty()) return;
			
			for (BasicEntityShip ship : ships)
			{
				if (ship.world.provider.getDimension() == worldID &&
					player.getDistanceToEntity(ship) < 64F)
				{
					//設定ship攻擊目標
					ship.setSitting(false);
					ship.setEntityTarget(target);
					ship.applyEmotesReaction(5);
				}
			}
		}
	}
	
	/**
	 * set ship move with team list
	 * 
	 * parms: 0:player eid, 1:world id, 2:meta 3:guard type 4:guard target id
	 * 
	 * note: guard type: no use for now
	 */
	public static void applyTeamGuard(int[] parms)
	{
		EntityPlayer player = EntityHelper.getEntityPlayerByID(parms[0], parms[1], false);
		Entity target = EntityHelper.getEntityByID(parms[4], parms[1], false);
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null)
		{
			//get current team formation id
			int formatID = capa.getFormatIDCurrentTeam();
			ArrayList<BasicEntityShip> ships = capa.getShipEntityByMode(parms[2]);
			
			if (ships.isEmpty()) return;
			
			//若為formation mode且有設定陣型, 則只有在5 or 6船時可以下命令
			if ((parms[2] < 2 && formatID <= 0) || (parms[2] == 2 && (formatID <= 0 || ships.size() > 4)))
			{
				for (BasicEntityShip ship : ships)
				{
					if (ship.world.provider.getDimension() == parms[1] &&
						player.getDistanceToEntity(ship) < 64F)
					{
						//設定ship移動地點
						applyShipGuardEntity(ship, target);
						//sync
						CommonProxy.channelE.sendTo(new S2CEntitySync(ship, S2CEntitySync.PID.SyncShip_Minor), (EntityPlayerMP) player);
					}
				}
			}
		}		
	}

	/** set ship move with team list 
	 * 
	 *  parms: 0:player eid, 1:world id, 2:meta 3:guard type 4:posX 5:posY 6:posZ
	 *  
	 *  note: guard type: no use for now
	 */
	public static void applyTeamMove(int[] parms)
	{
		EntityPlayer player = EntityHelper.getEntityPlayerByID(parms[0], parms[1], false);
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if(capa != null)
		{
			//get current team formation id
			int formatID = capa.getFormatIDCurrentTeam();
			ArrayList<BasicEntityShip> ships = capa.getShipEntityByMode(parms[2]);
			
			if (ships.isEmpty()) return;
			
			//single/group mode
			if (parms[2] < 2 && formatID <= 0)
			{
				for (BasicEntityShip ship : ships)
				{
					if (ship.world.provider.getDimension() == parms[1] &&
						player.getDistanceToEntity(ship) < 64F)
					{
						//設定ship移動地點
						applyShipGuard(ship, parms[4], parms[5], parms[6], false);
						//sync guard
						CommonProxy.channelE.sendTo(new S2CEntitySync(ship, S2CEntitySync.PID.SyncShip_Minor), (EntityPlayerMP) player);
					}
				}
			}//end single/group move
			//formation mode: 有設定陣型時, 則只有在5 or 6船時可以下命令
			else if (parms[2] == 2)
			{
				if (ships.size() > 4 || formatID == 0)
				{
					for (BasicEntityShip s : ships)
					{
						//check formation id is same, distance < 64, same dimension
						if (s.getStateMinor(ID.M.FormatType) != formatID ||
							player.getDistanceToEntity(s) > 64F ||
							player.dimension != s.dimension)
						{
							return;	//can't move
						}
					}
					
					//set formation position
					applyFormationMoving(ships, formatID, parms[4], parms[5], parms[6], true);
				}
			}//end formation move
		}
	}
	
	/**
	 * set ship sitting with team list, only called at server side
	 * 1. 若目標不在隊伍, 則單獨設定目標坐下
	 * 2. 若目標在隊伍, 則依照 pointer類型設定目標坐下
	 *  
	 * parms: 0:player eid, 1:world id, 2:meta, 3:ship uid
	 */
	public static void applyTeamSit(int[] parms)
	{
		EntityPlayer player = EntityHelper.getEntityPlayerByID(parms[0], parms[1], false);
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);

		if(capa != null)
		{
			//不在隊伍名單裡面
			if (capa.checkIsInCurrentTeam(parms[3]) < 0)
			{
				BasicEntityShip target = EntityHelper.getShipByUID(parms[3]);
				
				target.setEntitySit(!target.isSitting());
				target.setRiderAndMountSit();
			}
			//有在隊伍中, 則依照pointer類型抓目標
			else
			{
				ArrayList<BasicEntityShip> ships = capa.getShipEntityByMode(parms[2]);
				
				if (ships.isEmpty()) return;
				
				boolean sit = !ships.get(0).isSitting();
				
				for (BasicEntityShip s : ships)
				{
					if (s.world.provider.getDimension() == parms[1])
					{
						s.setEntitySit(sit);
						s.setRiderAndMountSit();
					}
				}
			}//end not in team
		}
	}
	
	/**
	 * set ship select (focus) with team list, only called at server side
	 * 
	 * parms: 0:player eid, 1:world id, 2:meta, 3:ship uid
	 */
	public static void applyTeamSelect(int[] parms)
	{
		EntityPlayer player = EntityHelper.getEntityPlayerByID(parms[0], parms[1], false);
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);
		
		if (capa != null)
		{
			int i = capa.checkIsInCurrentTeam(parms[3]);
			
			//check entity is in team
			if (i >= 0)
			{
				switch (parms[2])
				{
				case 0:  //single mode (僅一隻可以focus)
					/**single mode不能取消focus, 一定有一隻會是focus狀態*/
					capa.clearSelectStateCurrentTeam();
					capa.setSelectStateCurrentTeam(i, true);
					break;
				case 1:  //group mode (不限focus數量)
					capa.setSelectStateCurrentTeam(i, !capa.getSelectStateCurrentTeam(i));
					break;
				}
			}
			
			//sync team list to client
			capa.sendSyncPacket(0);
		}	
	}
	
	
}
