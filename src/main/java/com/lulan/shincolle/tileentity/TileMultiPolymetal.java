package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BasicBlockMulti;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**SERVANT BLOCK: POLYMETAL
 * servant block的所有方法都是跟master block呼叫
 */
public class TileMultiPolymetal extends BasicTileMulti {

	public static final int SLOTS_NUM = 10;
	public static final int SLOTS_OUT = 0;
	
	public TileMultiPolymetal() {}
	
	@Override
  	public int getSizeInventory() {
		BasicTileMulti tile = (BasicTileMulti)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		//用master的資料return
		if(tile != null) {
			if(tile instanceof TileMultiGrudgeHeavy) {
				return ((TileMultiGrudgeHeavy)tile).slots.length;
			} 
		}
		return 0;
  	}

  	@Override
  	public ItemStack getStackInSlot(int i) {
  		BasicTileMulti tile = (BasicTileMulti)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		//用master的資料return
		if(tile != null) {
			if(tile instanceof TileMultiGrudgeHeavy) {
				return ((TileMultiGrudgeHeavy)tile).slots[i];
			} 
		}
		return null;
  	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		BasicTileMulti tile = (BasicTileMulti)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		//用master的資料return
		if(tile != null) {
			if(tile instanceof TileMultiGrudgeHeavy) {
				return ((TileMultiGrudgeHeavy)tile).SLOTS_ALL;
			} 
		}
		return new int[] {};
	}
	
	@Override
  	public ItemStack decrStackSize(int i, int j) {
		BasicTileMulti tile = (BasicTileMulti)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		//用master的資料return
		if(tile != null) {
			if(tile instanceof TileMultiGrudgeHeavy) {
				return ((TileMultiGrudgeHeavy)tile).decrStackSize(i, j);
			} 
		}
		return null;
	}
	
  	@Override
  	public void setInventorySlotContents(int i, ItemStack itemstack) {
  		BasicTileMulti tile = (BasicTileMulti)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		//用master的資料return
		if(tile != null) {
			if(tile instanceof TileMultiGrudgeHeavy) {
				((TileMultiGrudgeHeavy)tile).setInventorySlotContents(i, itemstack);
			} 
		}
  	}
  	
  	@Override
  	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
  		BasicTileMulti tile = (BasicTileMulti)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		//用master的資料return
		if(tile != null) {
			if(tile instanceof TileMultiGrudgeHeavy) {
				return ((TileMultiGrudgeHeavy)tile).isItemValidForSlot(slot, itemstack);
			} 
		}
		return false;
  	}
  	
  	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
  		BasicTileMulti tile = (BasicTileMulti)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		//用master的資料return
		if(tile != null) {
			if(tile instanceof TileMultiGrudgeHeavy) {
				return ((TileMultiGrudgeHeavy)tile).canExtractItem(slot, itemstack, side);
			} 
		}
		return false;
	}
  	
  	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
  		BasicTileMulti tile = (BasicTileMulti)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		//用master的資料return
		if(tile != null) {
			if(tile instanceof TileMultiGrudgeHeavy) {
				return ((TileMultiGrudgeHeavy)tile).isUseableByPlayer(player);
			} 
		}
		return false;
	}
  	
  	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
  		BasicTileMulti tile = (BasicTileMulti)this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
		//用master的資料return
		if(tile != null) {
			if(tile instanceof TileMultiGrudgeHeavy) {
				return ((TileMultiGrudgeHeavy)tile).isItemValidForSlot(slot, itemstack);
			} 
		}
		return false;
	}

}
