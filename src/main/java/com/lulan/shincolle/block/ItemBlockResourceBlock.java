package com.lulan.shincolle.block;

import net.minecraft.block.Block;

import com.lulan.shincolle.item.IShipResourceItem;

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
		if (this.getBlock() instanceof BlockGrudge)
		{
			return new int[] {9, 0, 0, 0};
		}
		
		if (this.getBlock() instanceof BlockAbyssium)
		{
			return new int[] {0, 9, 0, 0};
		}
		
		if (this.getBlock() instanceof BlockPolymetal)
		{
			return new int[] {0, 0, 0, 9};
		}
		
		if (this.getBlock() instanceof BlockPolymetalGravel)
		{
			return new int[] {0, 0, 0, 4};
		}
		
		return new int[] {0, 0, 0, 0};
	}
    

}

