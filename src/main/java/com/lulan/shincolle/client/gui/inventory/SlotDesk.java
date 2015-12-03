package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.tileentity.TileEntityDesk;

public class SlotDesk extends Slot {

	private int slotIndex;
	private TileEntityDesk tile;

	public SlotDesk(IInventory entity, int slotIndex, int x, int y) {
		super(entity, slotIndex, x, y);
		this.slotIndex = slotIndex;
		this.tile = (TileEntityDesk)entity;
	}
	
	//設定每個slot可以放進的物品: 0:grudge 1:abyssium 2:ammo 3:polymetal 4:fuel 5:output
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		return tile.isItemValidForSlot(this.slotIndex, itemstack);
    }
	
	//player can not take item from slot
	public boolean canTakeStack(EntityPlayer player) {
        return false;
    }
	
	
}
