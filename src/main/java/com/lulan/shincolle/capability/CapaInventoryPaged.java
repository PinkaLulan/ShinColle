package com.lulan.shincolle.capability;

/**
 * paged inventory, GUI get slots by page button
 */
public class CapaInventoryPaged<T> extends CapaInventory<T>
{
	
	/** current inventory page id for gui */
	private int currentSlotPage = 0;
	
	
	public CapaInventoryPaged(int size, T host)
	{
		super(size, host);
	}
	
	
}