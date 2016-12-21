package com.lulan.shincolle.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import com.lulan.shincolle.block.BlockPolymetal;
import com.lulan.shincolle.capability.CapaInventory;

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
    	//check capability: inventory
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
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
        
        //get other capability
        return super.getCapability(capability, facing);
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
		try
		{
			if (id >= 0 && id < itemHandler.getSlots())
	        {
	            ItemStack itemstack = itemHandler.getStackInSlot(id);
	            itemHandler.setStackInSlot(id, null);
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
  	
//	@Override
//	public int fill(ForgeDirection from, FluidStack fluid, boolean doFill)
//	{
//		TileEntity tile = this.getMaster();
//		
//		//type 1: large shipyard
//		if(tile instanceof TileMultiGrudgeHeavy) {
//			return ((TileMultiGrudgeHeavy)tile).fill(from, fluid, doFill);
//		}
//		return 0;
//	}
//
//	@Override
//	public FluidStack drain(ForgeDirection from, FluidStack fluid, boolean doDrain) {
//		TileEntity tile = this.getMaster();
//		
//		//type 1: large shipyard
//		if(tile instanceof TileMultiGrudgeHeavy) {
//			return ((TileMultiGrudgeHeavy)tile).drain(from, fluid, doDrain);
//		}
//		return null;
//	}
//
//	@Override
//	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
//		TileEntity tile = this.getMaster();
//		
//		//type 1: large shipyard
//		if(tile instanceof TileMultiGrudgeHeavy) {
//			return ((TileMultiGrudgeHeavy)tile).drain(from, maxDrain, doDrain);
//		}
//		return null;
//	}
//
//	@Override
//	public boolean canFill(ForgeDirection from, Fluid fluid) {
//		TileEntity tile = this.getMaster();
//		
//		//type 1: large shipyard
//		if(tile instanceof TileMultiGrudgeHeavy) {
//			return ((TileMultiGrudgeHeavy)tile).canFill(from, fluid);
//		}
//		return false;
//	}
//
//	@Override
//	public boolean canDrain(ForgeDirection from, Fluid fluid) {
//		TileEntity tile = this.getMaster();
//		
//		//type 1: large shipyard
//		if(tile instanceof TileMultiGrudgeHeavy) {
//			return ((TileMultiGrudgeHeavy)tile).canDrain(from, fluid);
//		}
//		return false;
//	}
//
//	@Override
//	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
//		TileEntity tile = this.getMaster();
//		
//		//type 1: large shipyard
//		if(tile instanceof TileMultiGrudgeHeavy) {
//			return ((TileMultiGrudgeHeavy)tile).getTankInfo(from);
//		}
//		return null;
//	}
  	
  	
}
