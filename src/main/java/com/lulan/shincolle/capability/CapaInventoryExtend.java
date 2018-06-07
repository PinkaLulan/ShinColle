package com.lulan.shincolle.capability;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;


/**
 * extensible inventory, GUI get slots by scroll
 *  
 * 2018/6/7
 *   separate equip and item inventory
 *   item slots are extensible by equip or ship ability
 *   equip slots are paged {@link capability.CapaInventoryPaged}
 * 
 * 2018/6/5
 *   remove IInventory system
 */
public class CapaInventoryExtend<T> extends CapaInventory<T>
{
	
	/** current inventory line id for gui scroll bar, 1 line = 3 slots */
	private int currentSlotLine = 0;
	
	
	public CapaInventoryExtend(int size, T host)
	{
		super(size, host);
	}
	
	/** get current line number */
	public int getCurrentLine()
	{
		return this.currentSlotLine;
	}
	
	/** set current line number and sync */
	public void setCurrentLine(int line)
	{
		this.currentSlotLine = line;
		this.onContentsChanged();
	}
	
	/** size with all enabled pages */
    @Override
	public int getSlots()
	{
    	if (this.host instanceof BasicEntityShip)
		{
    		//TODO rewrite entity attributes method (for easy accessing slot number)
			int slots = this.SLOTS_PLAYERINV +
					    this.host.getStateMinor(ID.M.DrumState) * this.SLOTS_PAGE;
			
			if (slots > this.SLOTS_MAX) return this.SLOTS_MAX;
			else return slots;
		}
		
		return this.SLOTS_PLAYERINV;
	}
    
    /**
     * if equip slot changed => recalc ship's equip attrs
     */
	@Override
	protected void onContentsChanged(int index)
	{
		//if equip slots, update equip attributes
		if (!this.host.world.isRemote && index < this.SLOTS_EQUIP)
		{
			this.host.calcShipAttributes(2, true);
		}
		
		super.onContentsChanged(index);
	}
	
	@Override
	protected void validateSlotIndex(int index)
	{
		if (index < 0 || index >= this.getSlots())
			throw new RuntimeException("Slot " + index + " not in valid range - [0," + this.getSlots() + ")");
	}
    
    
}