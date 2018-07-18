package com.lulan.shincolle.item;

/** ship food item
 *
 */
public interface IShipFoodItem
{
	
	
	/** get resource value */
	public float getFoodValue(int meta);
	
	/** get saturation value */
	public float getSaturationValue(int meta);
	
	/** get special effect */
	public int getSpecialEffect(int meta);
	
	
}