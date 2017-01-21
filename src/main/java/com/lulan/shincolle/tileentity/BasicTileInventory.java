package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.PacketHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

abstract public class BasicTileInventory extends BasicTileEntity implements ISidedInventory, IShipOwner
{
	
	protected CapaInventory itemHandler;
	protected int syncTime = 0;		//for sync (optional)
	protected int playerUID = 0;
	
	public BasicTileInventory()
	{
        super();
    }
	
	//load item data
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        
        this.playerUID = nbt.getInteger("pid");
        
        if (nbt.hasKey(CapaInventory.InvName))
        {
        	itemHandler.deserializeNBT((NBTTagCompound) nbt.getTag(CapaInventory.InvName));
        }
    }

    //save item data
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        nbt.setInteger("pid", this.playerUID);
        
        nbt.setTag(CapaInventory.InvName, itemHandler.serializeNBT());
        
        return nbt;
    }

    //check capability
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
    	//check capability: inventory
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }
        
        //check other capability
        return super.hasCapability(capability, facing);
    }

    //get capability
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
    	//get capability: inventory
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) itemHandler;
        }
        
        //get other capability
        return super.getCapability(capability, facing);
    }
    
    /** methods for IInventory to access IItemHandler
     */
	//是否可以右鍵點開方塊
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		//由於會有多個tile entity副本, 要先確認座標相同的副本才能使用
		//確認player要在該tile entity 8格內
		//確認該tile entity沒有被標為invalid
		if (world.getTileEntity(pos) == this && !isInvalid() &&
			player.getDistanceSq(pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D) <= 64)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
    
  	@Override
  	public int getSizeInventory()
  	{
  		if (this.itemHandler != null) return this.itemHandler.getSlots();
  		return 0;
  	}
  	
  	@Override
  	public ItemStack getStackInSlot(int i)
  	{
  		try
  		{
  			return this.itemHandler.getStackInSlot(i);
  		}
  		catch (Exception e)
  		{
  			e.printStackTrace();
  			return null;
  		}
  	}
  	
    //移除slot i中, 數量j個物品, 回傳為itemstack, 左右鍵等動作存取slot時會呼叫此方法
  	//(非shift動作) shift動作在container中的transferStackInSlot中實作
  	@Override
  	public ItemStack decrStackSize(int id, int count)
  	{
  		try
		{
  			if (id >= 0 && id < itemHandler.getSlots() &&
  				itemHandler.getStackInSlot(id) != null && count > 0)
  	        {
  	            ItemStack itemstack = itemHandler.getStackInSlot(id).splitStack(count);

  	            if (itemHandler.getStackInSlot(id).stackSize == 0)
  	            {
  	            	itemHandler.setStackInSlot(id, null);
  	            }

  	            return itemstack;
  	        }
  	        else
  	        {
  	            return null;
  	        }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
  	}
  	
	//NO USE: 用於GUI關閉時移除特定slot中的物品, 使其掉落出來, 目前沒有需要此方法
  	@Override
	public ItemStack removeStackFromSlot(int id)
	{
  		return null;
  		
//		try
//		{
//			if (id >= 0 && id < itemHandler.getSlots())
//	        {
//	            ItemStack itemstack = itemHandler.getStackInSlot(id);
//	            itemHandler.setStackInSlot(id, null);
//	            return itemstack;
//	        }
//	        else
//	        {
//	            return null;
//	        }
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			return null;
//		}
	}
  	
    //將slot設成目標itemstack(也可以設成null) 用於decrStackSize等方法
  	@Override
  	public void setInventorySlotContents(int id, ItemStack stack)
  	{
  		if (itemHandler != null && itemHandler.getSlots() > id)
		{
  			itemHandler.setStackInSlot(id, stack);
			
			//若手上物品超過該格子限制數量, 則只能放進限制數量
	  		if (stack != null && stack.stackSize > getInventoryStackLimit())
	  		{
	  			stack.stackSize = getInventoryStackLimit();
	  		}
		}
  	}
  	
    //每格可放的最大數量上限
  	@Override
  	public int getInventoryStackLimit()
  	{
  		return 64;
  	}
  	
    //使用管線/漏斗輸入時呼叫, 不適用於手動置入
  	@Override
  	public boolean canInsertItem(int slot, ItemStack item, EnumFacing face)
  	{
  		return isItemValidForSlot(slot, item);
  	}
  	
  	@Override
	public boolean canExtractItem(int slot, ItemStack item, EnumFacing face)
	{
		return false;
	}
  	
	@Override
	public int[] getSlotsForFace(EnumFacing face)
	{
		return new int[] {};
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return false;
	}
	

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public String getName()
	{
		return "tile." + Reference.MOD_ID + ":" + getRegName();
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}
	
	/** FIELD相關方法
	 *  使其他mod或class也能存取該tile的內部值
	 *  ex: gui container可用get/setField來更新數值
	 */
	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear() {}
	
	@Override
	public int getPlayerUID()
	{
		return this.playerUID;
	}

	@Override
	public void setPlayerUID(int uid)
	{
		this.playerUID = uid;
		
		//sync uid to all around
		if (!this.world.isRemote)
		{
			PacketHelper.sendS2CEntitySync(0, this, this.world, this.pos, null);
		}
	}

	@Override
	public Entity getHostEntity()
	{
		return null;
	}
	

}