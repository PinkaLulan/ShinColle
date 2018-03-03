package com.lulan.shincolle.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockPolymetalGravel extends BasicBlockFalling
{

	public static final String NAME = "BlockPolymetalGravel";
	
	
	public BlockPolymetalGravel()
	{
		super(Material.SAND);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHarvestLevel("shovel", 0);
	    this.setHardness(0.8F);
	    this.setSoundType(SoundType.SAND);
	}
	
	
}