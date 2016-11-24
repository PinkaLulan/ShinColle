package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

/** slot for large shipyard
 *  check the item type in spec slot
 */
public class SlotLargeShipyard extends Slot
{
	
	private int slotIndex;
	private TileMultiGrudgeHeavy tile;

	public SlotLargeShipyard(IInventory entity, int slotIndex, int x, int y)
	{
		super(entity, slotIndex, x, y);
		this.slotIndex = slotIndex;
		this.tile = (TileMultiGrudgeHeavy)entity;
	}
	
	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		return tile.isItemValidForSlot(this.slotIndex, itemstack);
    }
	
	
}
