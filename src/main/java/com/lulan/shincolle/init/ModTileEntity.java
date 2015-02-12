package com.lulan.shincolle.init;

import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.tileentity.TileMultiPolymetal;

import cpw.mods.fml.common.registry.GameRegistry;


public class ModTileEntity {
	
	public static void init() {
		GameRegistry.registerTileEntity(TileEntitySmallShipyard.class, "TileEntitySmallShipyard");
		GameRegistry.registerTileEntity(TileMultiGrudgeHeavy.class, "TileMultiLargeShipyard");
		GameRegistry.registerTileEntity(TileMultiPolymetal.class, "TileMultiPolymetal");
	}

}
