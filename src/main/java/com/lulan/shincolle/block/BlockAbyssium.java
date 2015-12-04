package com.lulan.shincolle.block;

import com.lulan.shincolle.item.IShipItemType;
import com.lulan.shincolle.reference.ID;

import net.minecraft.block.material.Material;

public class BlockAbyssium extends BasicBlock implements IShipItemType {
	public BlockAbyssium() {
		super(Material.iron);
		this.setBlockName("BlockAbyssium");
		this.setHarvestLevel("pickaxe", 0);
	    this.setHardness(3F);
		
	}
	
	@Override
	public int getItemType() {
		return ID.ItemType.BlockAbyssium;
	}
	
		
}
