package com.lulan.shincolle.block;

import com.lulan.shincolle.item.IShipItemType;
import com.lulan.shincolle.reference.ID;

import net.minecraft.block.material.Material;

public class BlockPolymetalGravel extends BasicBlockFalling implements IShipItemType {

	public BlockPolymetalGravel() {
		super(Material.sand);
	    this.setBlockName("BlockPolymetalGravel");
	    this.setHardness(0.8F);
	    this.setHarvestLevel("shovel", 0);
	    this.setStepSound(soundTypeSand);
	}
	
	@Override
	public int getItemType() {
		return ID.ItemType.BlockPolymetalGravel;
	}
	
	
}
