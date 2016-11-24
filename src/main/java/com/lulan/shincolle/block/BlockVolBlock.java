package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class BlockVolBlock extends BasicBlock
{

	public static final String NAME = "BlockVolBlock";
	
	
	public BlockVolBlock()
	{
		super(Material.SAND);
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
	    this.setLightLevel(1F);
	    this.setResistance(200F);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), this.getRegistryName());
	}
	
	
}
