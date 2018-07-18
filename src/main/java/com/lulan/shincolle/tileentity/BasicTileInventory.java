package com.lulan.shincolle.tileentity;

import javax.annotation.Nullable;

import com.lulan.shincolle.capability.CapaInventory;
import com.lulan.shincolle.entity.IShipInventory;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.utility.PacketHelper;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 * tile with inventory
 */
abstract public class BasicTileInventory extends BasicTileEntity implements IShipOwner, IShipInventory
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
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
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
    @Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
    	//get capability: inventory
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return (T) itemHandler;
        }
        
        //get other capability
        return super.getCapability(capability, facing);
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

    @Override
    public void onContentChanged(int slot)
    {
        this.markDirty();
    }
	
	
}