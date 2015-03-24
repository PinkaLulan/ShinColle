package com.lulan.shincolle.block;

import net.minecraft.block.material.Material;

public class BlockPolymetalGravel extends BasicBlockFalling {

	public BlockPolymetalGravel() {
		super(Material.sand);
	    this.setBlockName("BlockPolymetalGravel");
	    this.setHardness(0.8F);
	    this.setHarvestLevel("shovel", 0);
	    this.setStepSound(soundTypeSand);
	}
	
}
