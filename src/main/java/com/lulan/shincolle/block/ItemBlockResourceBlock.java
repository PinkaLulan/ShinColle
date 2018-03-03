package com.lulan.shincolle.block;

import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.item.IShipResourceItem;

import net.minecraft.block.Block;

public class ItemBlockResourceBlock extends BasicItemBlock implements IShipResourceItem
{

	
	public ItemBlockResourceBlock(Block block)
	{
		super(block);
	}

	//get resource value
	@Override
	public int[] getResourceValue(int meta)
	{
		if (this.getBlock() == ModBlocks.BlockGrudge)
		{
			return new int[] {9, 0, 0, 0};
		}
		else if (this.getBlock() == ModBlocks.BlockAbyssium)
		{
			return new int[] {0, 9, 0, 0};
		}
		else if (this.getBlock() == ModBlocks.BlockPolymetal)
		{
			return new int[] {0, 0, 0, 9};
		}
		else if (this.getBlock() == ModBlocks.BlockPolymetalGravel)
		{
			return new int[] {0, 0, 0, 4};
		}
		else if (this.getBlock() == ModBlocks.BlockGrudgeHeavyDeco)
		{
			return new int[] {18, 0, 0, 0};
		}
		
		return new int[] {0, 0, 0, 0};
	}
    

}