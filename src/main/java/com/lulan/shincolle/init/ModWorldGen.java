package com.lulan.shincolle.init;

import com.lulan.shincolle.worldgen.ShinColleWorldGen;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModWorldGen {
	
	public static final ShinColleWorldGen scWorldGen = new ShinColleWorldGen();
	
	public static void init() {
		GameRegistry.registerWorldGenerator(scWorldGen , 0);
	}

}
