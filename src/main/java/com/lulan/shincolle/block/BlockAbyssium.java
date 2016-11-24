package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockAbyssium extends BasicBlock
{
	
	public static final String NAME = "BlockAbyssium";
	
	
	public BlockAbyssium()
	{
		super(Material.IRON);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockResourceBlock(this), this.getRegistryName());
	}
	
	
}
