package com.lulan.shincolle.utility;

import net.minecraft.entity.player.EntityPlayerMP;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.network.S2CGUIPackets;
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
			setFormationID(props, props.getCurrentTeamID(), formatID);
		}
	}
	
	/** set team formation id */
	public static void setFormationID(ExtendPlayerProps props, int teamID, int formatID) {
		if(props != null) {
			int num = props.getNumberOfShip(teamID);
			
			if(num > 4 && formatID > 0) {	//can apply formation
				setFormationForShip(props, teamID, formatID);
				props.setFormatID(teamID, formatID);
			}
			else {
				setFormationForShip(props, teamID, 0);
				props.setFormatID(teamID, 0);
			}
			
			//sync formation data
			props.sendSyncPacket(0);
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
			ship = props.getEntityOfTeam(teamID, i);
			
			if(ship != null) {
				ship.setStateMinor(ID.M.FormatType, formatID);	//set formation type
				ship.setUpdateFlag(ID.FU.FormationBuff, true);  //set update
				
				temp = ship.getStateFinalBU(ID.MOV);			//get MIN moving speed in team
				if(temp < maxMOV) maxMOV = temp;
			}
		}
		
		//apply same moving speed to all ships in team
		maxMOV += buffMOV;  //moving speed for formation
		
		for(int j = 0; j < 6; j++) {
			ship = props.getEntityOfTeam(teamID, j);
			
			if(ship != null) {
				ship.setEffectFormationFixed(ID.FormationFixed.MOV, maxMOV);		//set moving speed
				
				//check if same ship in other team, cancel the buff in other team
				for(int k = 0; k < 9; k++) {
					//check different team
					if(k != teamID) {
						int[] temp2 = props.checkIsInTeam(ship.getShipUID(), k);
						
						//get ship in other team
						if(temp2[1] > 0) {
							//clear formation buff
							for(int m = 0; m < 6; m++) {
								ship = props.getEntityOfTeam(k, m);
								
								if(ship != null) {
									ship.setStateMinor(ID.M.FormatType, 0);			//set formation type
									ship.setUpdateFlag(ID.FU.FormationBuff, true);  //set update
								}
							}//end for clear formation buff
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
		return new float[] {0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
	}
	
	
	
}
