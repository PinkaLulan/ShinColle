package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BlockPolymetal;
import com.lulan.shincolle.capability.CapaInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**SERVANT BLOCK: POLYMETAL
 * servant block的所有方法都是跟master block呼叫
 */
// IFluidHandler TODO
public class TileMultiPolymetal extends BasicTileMulti
{

	
	/** 注意constructor只會在server端呼叫, client端需要另外init以免噴出NPE */
	public TileMultiPolymetal()
	{
		super();
		
		//use master's inventory
		this.itemHandler = new CapaInventory(0, this);
	}

	@Override
	public String getRegName()
	{
		return BlockPolymetal.TILENAME;
	}
	
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).getSlotsForFace(side);
		}
		
		return new int[] {};
	}
	
  	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
  	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).isItemValidForSlot(slot, itemstack);
		}
		
		return false;
	}
  	
  	@Override
	public boolean canExtractItem(int slot, ItemStack item, EnumFacing face)
  	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).canExtractItem(slot, item, face);
		}
		return false;
	}

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
    	TileEntity master = this.getMaster();
    	
    	if (master != null) 
    	{
    		return master.hasCapability(capability, facing);
    	}
    	else
    	{
    		return false;
    	}
    }

    //get capability
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
    	TileEntity master = this.getMaster();
    	
    	if (master != null) 
    	{
    		return master.getCapability(capability, facing);
    	}
    	else
    	{
    		return null;
    	}
    }
    
    /** methods for IInventory to access IItemHandler
     */
	//是否可以右鍵點開方塊
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).isUsableByPlayer(player);
		}
		
		return false;
	}
    
  	@Override
  	public int getSizeInventory()
  	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).getSizeInventory();
		}
		
		return 0;
  	}
  	
  	@Override
  	public ItemStack getStackInSlot(int i)
  	{
  		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).getStackInSlot(i);
		}
		
		return null;
  	}
  	
    //移除slot i中, 數量j個物品, 回傳為itemstack, 左右鍵等動作存取slot時會呼叫此方法
  	//(非shift動作) shift動作在container中的transferStackInSlot中實作
  	@Override
  	public ItemStack decrStackSize(int id, int count)
  	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).decrStackSize(id, count);
		}
		return null;
  	}
  	
  	@Override
	public ItemStack removeStackFromSlot(int id)
	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).removeStackFromSlot(id);
		}
		return null;
	}
	
  	@Override
  	public void setInventorySlotContents(int i, ItemStack itemstack)
  	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			((TileMultiGrudgeHeavy) tile).setInventorySlotContents(i, itemstack);
		}
  	}
  	
  	@Override
  	public boolean canInsertItem(int slot, ItemStack item, EnumFacing face)
  	{
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if (tile instanceof TileMultiGrudgeHeavy)
		{
			return ((TileMultiGrudgeHeavy) tile).canInsertItem(slot, item, face);
		}
		return false;
  	}
  	
  	
}