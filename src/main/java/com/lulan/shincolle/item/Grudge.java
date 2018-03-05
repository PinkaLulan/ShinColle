package com.lulan.shincolle.item;

/** meta:
 *     0: grudge
 *     1: experience grudge
 *
 */
public class Grudge extends BasicItem implements IShipResourceItem, IShipFoodItem
{
	
	private static final String NAME = "Grudge";
	
	
	public Grudge()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHasSubtypes(true);
	}
	
	@Override
	public int getTypes()
	{
		return 2;
	}
	
	@Override
	public int[] getResourceValue(int meta)
	{
		return new int[] {1, 0, 0, 0};
	}
	
	@Override
	public float getFoodValue(int meta)
	{
		return 10F;
	}

	@Override
	public float getSaturationValue(int meta)
	{
		return 0.5F;
	}

	@Override
	public int getSpecialEffect(int meta)
	{
		return 1;
	}
	
	
}