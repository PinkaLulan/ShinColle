package com.lulan.shincolle.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockResourceBlock(this), this.getRegistryName());
	}
	
	
}
