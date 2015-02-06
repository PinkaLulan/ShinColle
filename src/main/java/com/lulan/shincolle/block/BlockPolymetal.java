package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;

public class BlockPolymetal extends BasicBlock {
	public BlockPolymetal() {
		super(Material.iron);
		this.setBlockName("BlockPolymetal");
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
		
	}
		
}
