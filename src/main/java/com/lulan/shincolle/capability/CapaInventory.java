package com.lulan.shincolle.capability;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.tileentity.BasicTileInventory;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.ItemStackHandler;

/** inventory capability
 *  for tile / entity / itemstack
 *  
 *  if canInsertSlot/canExtractSlot is null, all slot will be insertable/extractable
 */
public class CapaInventory<T> extends ItemStackHandler
{

	public static final String InvName = "CpInv";	//ship inventory nbt tag name
	
	//host type:  -1:error  0:tile  1:entity  2:item
	protected int hostType = -1;
	protected T host;
	protected ISidedInventory hostInv;	//for slot insertion/extraction checking
	
	
    public CapaInventory(int size, T host)
    {
        super(size);
        this.host = host;
        
        if (host instanceof ISidedInventory) this.hostInv = (ISidedInventory) host;
        
        //check host
        if (this.host instanceof BasicEntityShip) { hostType = 0; }
        else if (this.host instanceof BasicTileInventory) { hostType = 1; }
        else if (this.host instanceof Entity) { hostType = 2; }
        else if (this.host instanceof ItemStack) { hostType = 3; }
        else
        {
        	throw new IllegalArgumentException("Capability: Inventory is only for Tile/Entity/Item host!");
        }
    }
    
    public T getHost()
    {
    	return this.host;
    }
    
    /** get slots at a time: IN: start slot id, length */
    public ItemStack[] getStacksInSlots(int slotStart, int length)
    {
    	//check slot id and length
    	validateSlotIndex(slotStart);
    	
    	if (slotStart + length > getSlots() || length < 0)
    	{
    		throw new RuntimeException("Slot length not in valid range - [0, " + stacks.length + ")");
    	}
    	
    	//return items
    	ItemStack[] items = new ItemStack[length];
    	int slotEnd = slotStart + length;
    	
    	for (int i = slotStart; i < slotEnd; i++)
    	{
    		items[i] = stacks[i];
    	}
    	
    	return items;
    }
    
	//mark update
	@Override
    protected void onContentsChanged(int slot)
	{
        switch (hostType)
        {
        case 0:  //ship entity
        	//send packet TODO
        	break;
        case 1:  //tile
        	((TileEntity) this.host).markDirty();
        	//send packet TODO
        	break;
        case 2:  //other entity
        	//send packet TODO
        	break;
        case 3:  //item
        	//send packet TODO
        	break;
    	default:
    		break;
        }
    }
	
	//on nbt load
	@Override
	protected void onLoad()
    {
    }
	
	//insert item
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
    	if (this.hostInv != null)
    	{
    		if (this.hostInv.canInsertItem(slot, stack, EnumFacing.UP))
    		{
    			return super.insertItem(slot, stack, simulate);
    		}
    		else
    		{
    			return stack;	//disable insertion
    		}
    	}
    	
    	return super.insertItem(slot, stack, simulate);
    }
	
    //extract item
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
    	if (this.hostInv != null)
    	{
    		if (this.hostInv.canExtractItem(slot, this.getStackInSlot(slot), EnumFacing.UP))
    		{
    			return super.extractItem(slot, amount, simulate);
    		}
    		else
    		{
    			return null;	//disable extraction
    		}
    	}
    	
    	return super.extractItem(slot, amount, simulate);
    }
    
    
}