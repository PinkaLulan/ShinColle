package com.lulan.shincolle.init;

import com.lulan.shincolle.worldgen.ShinColleWorldGen;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**ore gen and chest loot
 */
public class ModWorldGen
{
	
	public static final ShinColleWorldGen scWorldGen = new ShinColleWorldGen();
	
	
	public static void init()
	{
		//generate ore
		GameRegistry.registerWorldGenerator(scWorldGen , 0);
	}
	
	
}
