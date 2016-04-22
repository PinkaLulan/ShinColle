package com.lulan.shincolle.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**SERVANT BLOCK: POLYMETAL
 * servant block的所有方法都是跟master block呼叫
 */
public class TileMultiPolymetal extends BasicTileMulti implements IFluidHandler{

	public static final int SLOTS_NUM = 10;
	public static final int SLOTS_OUT = 0;
	
	
	public TileMultiPolymetal() {
		super();
	}
	
	@Override
  	public int getSizeInventory() {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).slots.length;
		}
		return 0;
  	}

  	@Override
  	public ItemStack getStackInSlot(int i) {
  		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).slots[i];
		}
		return null;
  	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return TileMultiGrudgeHeavy.SLOTS_ALL;
		}
		return new int[] {};
	}
	
	@Override
  	public ItemStack decrStackSize(int i, int j) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).decrStackSize(i, j);
		}
		return null;
	}
	
  	@Override
  	public void setInventorySlotContents(int i, ItemStack itemstack) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			((TileMultiGrudgeHeavy)tile).setInventorySlotContents(i, itemstack);
		}
  	}
  	
  	@Override
  	public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).isItemValidForSlot(slot, itemstack);
		}
		return false;
  	}
  	
  	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).canExtractItem(slot, itemstack, side);
		}
		return false;
	}
  	
  	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).isUseableByPlayer(player);
		}
		return false;
	}
  	
  	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).isItemValidForSlot(slot, itemstack);
		}
		return false;
	}

	@Override
	public int getFuelSlotMin() {
		return SLOTS_OUT+1;
	}

	@Override
	public int getFuelSlotMax() {
		return SLOTS_NUM-1;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack fluid, boolean doFill) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).fill(from, fluid, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack fluid, boolean doDrain) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).drain(from, fluid, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).drain(from, maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).canFill(from, fluid);
		}
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).canDrain(from, fluid);
		}
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		TileEntity tile = this.getMaster();
		
		//type 1: large shipyard
		if(tile instanceof TileMultiGrudgeHeavy) {
			return ((TileMultiGrudgeHeavy)tile).getTankInfo(from);
		}
		return null;
	}
	

}
