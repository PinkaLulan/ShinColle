package com.lulan.shincolle.client.gui.inventory;

import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.item.BasicEquip;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**CUSTOM SHIP INVENTORY
 * slot: S0(80,19) S1(80,37) S2(80,55) S3(80,73) S4(80,91) S5~S22(8,112)
 * player inventory(8,155) hotbar(8,213)
 * S0~S4 for equip only
 */
public class SlotShipInventory extends Slot
{

	private int slotIndex;  //slot index
	private CapaShipInventory capa;

	public SlotShipInventory(CapaShipInventory capa, int slotIndex, int x, int y)
	{
		super(capa, slotIndex, x, y);
		this.capa = capa;
		this.slotIndex = slotIndex;
	}

	//設定每個slot可以放進的物品: 0:
	@Override
	public boolean isItemValid(ItemStack stack)
	{	
		if (!stack.isEmpty())
		{
			Item item = stack.getItem();
			
			//只有equip item可以塞進equip slot
			if (slotIndex < ContainerShipInventory.SLOTS_SHIPINV)
			{
				if (item instanceof BasicEquip)
				{
					return true;
				}
			}
			else
			{
				return true;
			}
		}
		
		return false;
    }
	
	//get itemstack in slot with paging
	@Override
    public ItemStack getStack()
    {
		//if equip slots, show slot 0~5
		if (this.slotIndex < 6)
		{
			return this.inventory.getStackInSlot(this.slotIndex);
		}
		//inventory slots, show paged slots
		else
		{
			return this.inventory.getStackInSlot(this.slotIndex + this.capa.getInventoryPage() * 18);
		}
    }
	
	//set itemstack to slot with paging
	@Override
    public void putStack(ItemStack stack)
    {
		//if equip slots, show slot 0~5
		if (this.slotIndex < 6)
		{
			this.inventory.setInventorySlotContents(this.slotIndex, stack);
		}
		//inventory slots, show paged slots
		else
		{
			this.inventory.setInventorySlotContents(this.slotIndex + this.capa.getInventoryPage() * 18, stack);
		}
        
        this.onSlotChanged();
    }
	
	@Override
    public ItemStack decrStackSize(int amount)
    {
		//if equip slots, show slot 0~5
		if (this.slotIndex < 6)
		{
			return this.inventory.decrStackSize(this.slotIndex, amount);
		}
		//inventory slots, show paged slots
		else
		{
			return this.inventory.decrStackSize(this.slotIndex + this.capa.getInventoryPage() * 18, amount);
		}
    }

	
}