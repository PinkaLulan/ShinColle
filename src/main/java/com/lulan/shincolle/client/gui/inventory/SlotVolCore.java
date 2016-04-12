package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.tileentity.TileEntityVolCore;

public class SlotVolCore extends Slot {
	
	private int slotIndex;
	private TileEntityVolCore tile;

	public SlotVolCore(IInventory tile, int slotIndex, int x, int y) {
		super(tile, slotIndex, x, y);
		this.slotIndex = slotIndex;
		this.tile = (TileEntityVolCore) tile;
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return tile.isItemValidForSlot(this.slotIndex, itemstack);
    }

	
}

