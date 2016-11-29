package com.lulan.shincolle.init;

import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntityLightBlock;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileEntityVolCore;
import com.lulan.shincolle.tileentity.TileEntityWaypoint;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.tileentity.TileMultiPolymetal;

import cpw.mods.fml.common.registry.GameRegistry;


public class ModTileEntity {
	
	public static void init() {
		//tile entity
		GameRegistry.registerTileEntity(TileEntityDesk.class, "TileEntityBlockDesk");
		GameRegistry.registerTileEntity(TileEntityLightBlock.class, "TileEntityLightBlock");
		
		GameRegistry.registerTileEntity(TileEntityVolCore.class, "TileEntityVolCore");
		GameRegistry.registerTileEntity(TileEntityWaypoint.class, "TileEntityWaypoint");
		

	}

	
}
