package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;

public class BlockAbyssium extends BasicBlock {
	public BlockAbyssium() {
		super(Material.iron);
		this.setBlockName("BlockAbyssium");
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
		
	}
	
		
}
