package com.lulan.shincolle.client.inventory;

import com.lulan.shincolle.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

/**SLOT POSITION
 * S0:grudge(33,29) S1:abyssium(53,29) S2:ammo(73,29) S3:poly(93,29)
 * S4:fuel(8,53) fuel bar(10,18) fire(64,51) arrow(114,29) S5:output(145,29)
 * player inv(8,87) action bar(8,145) 
 */
public class SlotSmallShipyard extends Slot {
	
	private int slotIndex;

	public SlotSmallShipyard(IInventory entity, int slotIndex, int x, int y) {
		super(entity, slotIndex, x, y);
		this.slotIndex = slotIndex;
	}
	
	//設定每個slot可以放進的物品: 0:grudge 1:abyssium 2:ammo 3:polymetal 4:fuel 5:output
	@Override
	public boolean isItemValid(ItemStack itemstack) {
		if(itemstack != null) {
			Item item = itemstack.getItem();
			
			switch(slotIndex) {
			case 0:		//grudge slot
				return item == ModItems.Grudge;
			case 1:		//abyssium slot
				return item == ModItems.Abyssium;
			case 2:		//ammo slot
				return item == ModItems.Ammo;
			case 3:		//polymetal slot
				return item == ModItems.Polymetal;
			case 4:		//fuel slot
				return TileEntityFurnace.isItemFuel(itemstack);
			default:
				return false;
			}
		}
		return false;
    }

}
