package com.lulan.shincolle.init;

import cpw.mods.fml.common.registry.GameRegistry;

import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiLargeShipyard;
import com.lulan.shincolle.tileentity.TileMultiPolymetal;

public class ModTileEntity {
	
	public static void init() {
		GameRegistry.registerTileEntity(TileEntitySmallShipyard.class, "TileEntitySmallShipyard");
		GameRegistry.registerTileEntity(TileMultiLargeShipyard.class, "TileMultiLargeShipyard");
		GameRegistry.registerTileEntity(TileMultiPolymetal.class, "TileMultiPolymetal");
	}

}
