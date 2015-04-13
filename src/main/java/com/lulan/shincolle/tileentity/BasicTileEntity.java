package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BasicTileEntity extends TileEntity implements ISidedInventory {
	
	protected ItemStack slots[];
	protected String customName;
	protected int syncTime;		//for sync
	
	public BasicTileEntity() {
        customName = "";
    }
	
	public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }
    
    public boolean hasCustomName() {
        return customName != null && customName.length() > 0;
    }
    
    //確認是否有自訂名稱(使用name tag取名過的話)
  	@Override
  	public boolean hasCustomInventoryName() {
  		return this.customName != null && this.customName.length() > 0;
  	}
    
  	@Override
  	public int getSizeInventory() {
  		return slots.length;
  	}

  	@Override
  	public ItemStack getStackInSlot(int i) {
  		return slots[i];
  	}
  	
    //移除slot i中, 數量j個物品, 回傳為itemstack, 左右鍵等動作存取slot時會呼叫此方法
  	//(非shift動作) shift動作在container中的transferStackInSlot中實作
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
  	
    //關閉gui時是否取出slot中的物品, 以便讓物品掉落出來, 用於合成台等方塊 (此方塊沒有用到)
  	@Override
  	public ItemStack getStackInSlotOnClosing(int i) {
//  		ItemStack itemStack = getStackInSlot(i);
//          if (itemStack != null) {
//              setInventorySlotContents(i, null);
//          }
//          return itemStack;
  		return null;
  	}
  	
    //將slot設成目標itemstack(也可以設成null) 用於decrStackSize等方法
  	@Override
  	public void setInventorySlotContents(int i, ItemStack itemstack) {
  		slots[i] = itemstack;
  		//若手上物品超過該格子限制數量, 則只能放進限制數量
  		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
  			itemstack.stackSize = getInventoryStackLimit();
  		}	
  	}
  	
    //每格可放的最大數量上限
  	@Override
  	public int getInventoryStackLimit() {
  		return 64;
  	}
  	
    //此兩方法不能跟container並用
  	public void openInventory() {}
  	public void closeInventory() {}
  	
    //使用管線/漏斗輸入時呼叫, 不適用於手動置入
  	@Override
  	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
  		return this.isItemValidForSlot(slot, itemstack);  //不管side 皆用此判定
  	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return null;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		return false;
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		return false;
	}
	
	//sync data for GUI display
	public void sendSyncPacket() {
		if(!this.worldObj.isRemote) {
			TargetPoint point = new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 8D);
			CommonProxy.channelG.sendToAllAround(new S2CGUIPackets(this), point);
		}
	}

}
