package com.lulan.shincolle.utility;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.CreatePacketS2C;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiLargeShipyard;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
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
		if(entity != null) {
			switch(button) {
			case AttrID.B_ShipInv_AmmoLight:
				LogHelper.info("DEBUG : set entity value "+button+" "+value);
				entity.setEntityFlagI(AttrID.F_UseAmmoLight, value);
				break;
			case AttrID.B_ShipInv_AmmoHeavy:
				LogHelper.info("DEBUG : set entity value "+button+" "+value);
				entity.setEntityFlagI(AttrID.F_UseAmmoHeavy, value);
				break;
			}
		}
		else {
			LogHelper.info("DEBUG : set entity by GUI fail, entity null");
		}
	}
	
	//process Shipyard GUI click
	public static void setTileEntityByGUI(TileEntity tile, int button, int value) {
		if(tile != null) {
			if(tile instanceof TileEntitySmallShipyard) {
				LogHelper.info("DEBUG : set tile entity value "+button+" "+value);
				((TileEntitySmallShipyard)tile).buildType = value;
				return;
			}
//			if(tile instanceof TileMultiLargeShipyard) {
//				LogHelper.info("DEBUG : set tile entity value "+button+" "+value);
//			}
				
		}
		else {
			LogHelper.info("DEBUG : set tile entity by GUI fail, entity null");
		}
		
	}
	
}
