package com.lulan.shincolle.block;

import com.lulan.shincolle.item.IShipItemType;
import com.lulan.shincolle.reference.ID;

import net.minecraft.block.material.Material;

public class BlockGrudge extends BasicBlock implements IShipItemType {
	public BlockGrudge() {
		super(Material.sand);
		this.setBlockName("BlockGrudge");
		this.setHarvestLevel("shovel", 0);
	    this.setHardness(1F);
	    this.setLightLevel(1F);
	    this.setStepSound(soundTypeSand);
		
	}
	
	@Override
	public int getItemType() {
		return ID.ItemType.BlockGrudge;
	}
	
	
}
