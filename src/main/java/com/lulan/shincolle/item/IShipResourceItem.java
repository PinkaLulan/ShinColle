package com.lulan.shincolle.item;

/** resource item
 *  get item resource value for material item
 */
public interface IShipResourceItem
{

	/** get resource value: int[4]: 0:grudge, 1:abyssium, 2:ammo, 3:polymetal */
	public int[] getResourceValue(int meta);
	
	
}