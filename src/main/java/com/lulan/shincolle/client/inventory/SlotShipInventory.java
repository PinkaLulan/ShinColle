package com.lulan.shincolle.client.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.lulan.shincolle.entity.BasicEntityShipLarge;
import com.lulan.shincolle.entity.EntityDestroyerI;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEquip;
import com.lulan.shincolle.utility.LogHelper;

/**CUSTOM SHIP INVENTORY
 * slot: S0(80,19) S1(80,37) S2(80,55) S3(80,73) S4(80,91) S5~S22(8,112)
 * player inventory(8,155) hotbar(8,213)
 * S0~S4 for equip only
 */
public class SlotShipInventory extends Slot {

	private int slotIndex;
	private IInventory extProps;

	public SlotShipInventory(IInventory extProps, int slotIndex, int x, int y) {
		super(extProps, slotIndex, x, y);
		this.extProps = extProps;
		this.slotIndex = slotIndex;
	}
	
	//設定每個slot可以放進的物品: 0:
	@Override
	public boolean isItemValid(ItemStack itemstack) {	
		if(itemstack != null) {
			Item item = itemstack.getItem();
			
			if(slotIndex < ContainerShipInventory.SLOTS_EQUIP) {	//只有equip item可以塞進equip slot
				if(item instanceof BasicEquip) {
					return true;
				}
			}
			else {
				return true;
			}
		}
		return false;
    }
	
}
