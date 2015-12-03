package com.lulan.shincolle.init;

import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.tileentity.TileMultiPolymetal;

import cpw.mods.fml.common.registry.GameRegistry;


public class ModTileEntity {
	
	public static void init() {
		GameRegistry.registerTileEntity(TileEntityDesk.class, "TileEntityBlockDesk");
		GameRegistry.registerTileEntity(TileEntitySmallShipyard.class, "TileEntitySmallShipyard");
		GameRegistry.registerTileEntity(TileMultiGrudgeHeavy.class, "TileMultiLargeShipyard");
		GameRegistry.registerTileEntity(TileMultiPolymetal.class, "TileMultiPolymetal");
	}

}
