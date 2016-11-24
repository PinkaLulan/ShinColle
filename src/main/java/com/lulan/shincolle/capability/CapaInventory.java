package com.lulan.shincolle.capability;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import com.lulan.shincolle.entity.BasicEntityShip;

/** inventory capability
 *  for tile / entity / itemstack
 */
public class CapaInventory<T> extends ItemStackHandler
{

	public static final String InvName = "CpInv";	//ship inventory nbt tag name
	
	//host type:  -1:error  0:tile  1:entity  2:item
	protected int hostType = -1;
	protected T hostObj;
	
	
    public CapaInventory(int size, T host)
    {
        super(size);
        
        //set host
        hostObj = host;
        
        //check host
        if (hostObj instanceof BasicEntityShip) { hostType = 0; }
        else if (hostObj instanceof TileEntity) { hostType = 1; }
        else if (hostObj instanceof Entity) { hostType = 2; }
        else if (hostObj instanceof ItemStack) { hostType = 3; }
        else
        {
        	throw new IllegalArgumentException("Capability: Inventory is only for Tile/Entity/Item host!");
        }
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
        case 0:  //tile
        	//send packet TODO
        	break;
        case 1:  //tile
        	((TileEntity) hostObj).markDirty();
        	//send packet TODO
        	break;
        case 2:  //entity
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
	
	
}
