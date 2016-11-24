package com.lulan.shincolle.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockGrudge extends BasicBlock
{

	public static final String NAME = "BlockGrudge";
	
	
	public BlockGrudge()
	{
		super(Material.SAND);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHarvestLevel("shovel", 0);
	    this.setHardness(1F);
	    this.setLightLevel(1F);
	    this.setResistance(200F);
	    this.setSoundType(SoundType.SAND);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockResourceBlock(this), this.getRegistryName());
	}
	
	
}
