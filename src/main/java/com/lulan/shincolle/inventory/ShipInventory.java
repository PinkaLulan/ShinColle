package com.lulan.shincolle.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;


//此class已跟extend entity prop合併, 待刪除中
public class ShipInventory implements IInventory {

	//0~5:equip 6~23:inventory
	private ItemStack[] slots = new ItemStack[ContainerShipInventory.SLOTS_TOTAL];		
	private static final String tagName = "ShipInv";	//ship inventory nbt tag
	
	
	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null) {
            if (itemStack.stackSize <= j) {			  //若數量<=j個
                setInventorySlotContents(i, null);	  //則該slot清空
            }
            else {									  //若數量 >j個
                itemStack = itemStack.splitStack(j);  //該itemstack數量-j
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(i, null);//全部拿光, slot清空
                }
            }
        }
        return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack itemStack = getStackInSlot(i);
        if (itemStack != null) {
            setInventorySlotContents(i, null);
        }
        return itemStack;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		slots[i] = itemstack;
		//若手上物品超過該格子限制數量, 則只能放進限制數量
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}		
	}

	@Override
	public String getInventoryName() {
		return "Ship Inventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	//check item valid in CUSTOM SLOT CLASS, not here
	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}
	
	//讀取nbt data存的item資料
	public void readFromNBT(NBTTagCompound compound) {
		NBTTagList list = compound.getTagList(tagName, 10);

		for(int i=0; i<list.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) list.getCompoundTagAt(i);
			byte sid = item.getByte("Slot");

			if (sid>=0 && sid<slots.length) {
				slots[sid] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}
	
	//將item資料寫進nbt
	public void writeToNBT(NBTTagCompound compound) {
		NBTTagList list = new NBTTagList();

		for(int i=0; i<slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				slots[i].writeToNBT(item);
				list.appendTag(item);
			}
		}
		
		compound.setTag(tagName, list);	//slot資料全部存在tag: ShipInv中
	}


}
