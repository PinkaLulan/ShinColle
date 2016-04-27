package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.tileentity.TileEntityCrane;

public class SlotCrane extends Slot {
	
	private int slotIndex;
	private TileEntityCrane tile;

	
	public SlotCrane(IInventory tile, int slotIndex, int x, int y) {
		super(tile, slotIndex, x, y);
		this.slotIndex = slotIndex;
		this.tile = (TileEntityCrane) tile;
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return false;
    }
	
	@Override
	public boolean canTakeStack(EntityPlayer player) {
        return false;
    }

	
}


