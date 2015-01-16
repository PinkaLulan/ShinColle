package com.lulan.shincolle.init;

import cpw.mods.fml.common.registry.GameRegistry;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;

public class ModTileEntity {
	
	public static void init() {
		GameRegistry.registerTileEntity(TileEntitySmallShipyard.class, "TileEntitySmallShipyard");
	}

}
