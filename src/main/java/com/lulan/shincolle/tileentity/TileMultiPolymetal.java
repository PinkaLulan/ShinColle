package com.lulan.shincolle.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileMultiPolymetal extends BasicTileMulti {

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}

}
