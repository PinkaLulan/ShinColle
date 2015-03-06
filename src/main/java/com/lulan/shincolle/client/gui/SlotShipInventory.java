package com.lulan.shincolle.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.lulan.shincolle.init.ModItems;

/**SLOT POSITION
 * 
 * 
 * player inv(8,155) action bar(8,213) 
 */
public class SlotShipInventory extends Slot {

	private int slotIndex;

	public SlotShipInventory(EntityPlayer player, IInventory entity, int slotIndex, int x, int y) {
		super(entity, slotIndex, x, y);
		this.slotIndex = slotIndex;
	}
	
	//設定每個slot可以放進的物品: 0:
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if(itemstack != null) {
			Item item = itemstack.getItem();
			
			switch(slotIndex) {
			case 0:
				return true;
			default:
				return false;
			}
		}
		return false;
    }
	
}
