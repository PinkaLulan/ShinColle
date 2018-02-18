package com.lulan.shincolle.client.gui.inventory;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.item.BasicEquip;

import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * slots for morph inventory
 */
public class SlotMorphInventory extends Slot
{

	private int slotIndex;  //slot index
	private CapaTeitoku capa;

	public SlotMorphInventory(CapaTeitoku capa, int slotIndex, int x, int y)
	{
		super(capa, slotIndex, x, y);
		this.capa = capa;
		this.slotIndex = slotIndex;
	}

	//設定每個slot可以放進的物品
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (stack != null)
		{
			Item item = stack.getItem();
			
			//只有equip item可以塞進equip slot
			if (item instanceof BasicEquip)
			{
				return true;
			}
		}
		
		return false;
    }
	
	
}