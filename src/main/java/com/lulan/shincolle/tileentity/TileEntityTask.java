package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.block.BlockTask;
import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.BlockHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityTask extends BasicTileInventory implements ITileGuardPoint, ITickable
{
	
	private int tick, partDelay, modeTask;
	private boolean isActive, isPaired;
	private BlockPos chestPos;
	public static final int[] NOSLOT = new int[] {};
	
	//target
	public EntityPlayer owner;
	private IInventory chest;
	
	
	public TileEntityTask()
	{
		super();
		
		//0~8: loading items, 9~17: unloading items
		this.itemHandler = new CapaInventory(0, this);
		this.chest = null;
		this.isActive = false;
		this.isPaired = false;
		this.tick = 0;
		this.partDelay = 0;
		this.chestPos = BlockPos.ORIGIN;
	}
	
	@Override
	public String getRegName()
	{
		return BlockTask.TILENAME;
	}
	
	@Override
	public byte getGuiIntID()
	{
		return ID.Gui.TASK;
	}
	
	@Override
	public byte getPacketID(int type)
	{
		switch (type)
		{
		case 0:
			return S2CGUIPackets.PID.TileTask;
		}
		
		return -1;
	}
	
	//依照輸出入口設定, 決定漏斗等裝置如何輸出入物品到特定slot中
	//注意: 此設定必須跟getCapability相同以免出現bug
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return NOSLOT;
	}
	
	//讀取nbt資料
	@Override
    public void readFromNBT(NBTTagCompound nbt)
	{
        super.readFromNBT(nbt);
        
        //load values
        this.isActive = nbt.getBoolean("active");
        this.isPaired = nbt.getBoolean("paired");
        
        //load pos
        int[] pos =  nbt.getIntArray("chestPos");
        if (pos == null || pos.length != 3) this.chestPos = BlockPos.ORIGIN;
        else this.chestPos = new BlockPos(pos[0], pos[1], pos[2]);
    }
	
	//將資料寫進nbt
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
        
		//save values
		nbt.setBoolean("active", this.isActive);
        nbt.setBoolean("paired", this.isPaired);
        
        //save pos
        if (this.chestPos != null)
        {
        	nbt.setIntArray("chestPos", new int[] {this.chestPos.getX(), this.chestPos.getY(), this.chestPos.getZ()});
        }
        else
        {
        	nbt.setIntArray("chestPos", new int[] {0, 0, 0});
        }
        
        return nbt;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return true;
	}
	
  	@Override
  	public boolean canInsertItem(int slot, ItemStack item, EnumFacing face)
  	{
  		return false;
  	}
	
	//使用管線/漏斗輸出時呼叫, 不適用於手動置入
	@Override
	public boolean canExtractItem(int slot, ItemStack item, EnumFacing face)
	{
		return false;
	}
	
	//set paired chest
	public void setPairedChest(BlockPos pos, boolean sendPacket)
	{
		TileEntity tile = this.world.getTileEntity(pos);
		
		if (tile instanceof IInventory)
		{
			this.chestPos = pos;
			this.isPaired = true;
			this.chest = (IInventory) tile;
		}
		
		if (!this.world.isRemote && sendPacket)
		{
			this.sendSyncPacket();
		}
	}
	
	//get paired chest
	public void checkPairedChest()
	{
		if (this.isPaired)
		{
			//get chest if no chest tile entity
			if (this.chest == null)
			{
				TileEntity tile = this.world.getTileEntity(this.chestPos);
				
				if (tile instanceof IInventory)
				{
					this.chest = (IInventory) tile;
				}
			}
			
			//check chest valid
			if (this.chest instanceof IInventory && !((TileEntity) this.chest).isInvalid())
			{
				return;
			}
			
			//tile lost, reset
			clearPairedChest();
			sendSyncPacket();
		}
	}
	
	public void clearPairedChest()
	{
		this.chest = null;
		this.isPaired = false;
		this.chestPos = BlockPos.ORIGIN;
	}
	
	//set data from packet data
	public void setSyncData(int[] data)
	{
		if (data != null)
		{
			setPairedChest(new BlockPos(data[6], data[7], data[8]), false);
		}
	}
	
	public void setChestWaypoint(BlockPos pos)
	{
		if (pos != null)
		{
			this.chestPos = pos;
		}
	}

	public BlockPos getChestWaypoint()
	{
		return this.chestPos;
	}
	
	@Override
	public void update()
	{
		
	}
	
	@Override
  	public ItemStack getStackInSlot(int i)
	{
  		return this.itemHandler.getStackInSlot(i);
  	}
	
	@Override
  	public ItemStack decrStackSize(int i, int j)
	{
		return null;
	}
	
	@Override
  	public void setInventorySlotContents(int i, ItemStack stack)
	{
  		this.itemHandler.setStackInSlot(i, stack);	
  	}
	
	//每格可放的最大數量上限
  	@Override
  	public int getInventoryStackLimit()
  	{
  		return 0;
  	}
  	
	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return 0;
		}
		
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
		break;
		}
	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		//check tile owner
		if (BlockHelper.checkTileOwner(player, this))
		{
			return super.isUsableByPlayer(player);
		}
		
		return false;
	}
	
	
}