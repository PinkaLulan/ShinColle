package com.lulan.shincolle.utility;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.CreatePacketS2C;
import com.lulan.shincolle.reference.AttrID;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityHelper {

	public EntityHelper() {}
	
	//get entity by ID
	public static Entity getEntityByID(int entityID, World world) {
		for(Object obj: world.getLoadedEntityList()) {
			if(entityID != -1 && ((Entity)obj).getEntityId() == entityID) {
				LogHelper.info("DEBUG : found entity by ID/client? "+entityID+" "+world.isRemote);
				return ((Entity)obj);
			}
		}
		return null;
	}
	
	//process GUI click
	public static void setEntityByGUI(BasicEntityShip entity, int button, int value) {
		switch(button) {
		case AttrID.B_ShipInv_AmmoLight:
			LogHelper.info("DEBUG : set entity flag value "+button+" "+value);
			entity.setEntityFlagI(AttrID.F_UseAmmoLight, value);
			break;
		case AttrID.B_ShipInv_AmmoHeavy:
			LogHelper.info("DEBUG : set entity flag value "+button+" "+value);
			entity.setEntityFlagI(AttrID.F_UseAmmoHeavy, value);
			break;
		}
	}
	
}
