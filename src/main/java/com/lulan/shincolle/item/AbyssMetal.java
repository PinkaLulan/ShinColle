package com.lulan.shincolle.item;

/**meta:
 * 0: abyssium ingot
 * 1: polymetallic nodules
 * 
 */
public class AbyssMetal extends BasicItem implements IShipResourceItem, IShipFoodItem
{	
	
	private static final String NAME = "AbyssMetal";
	
	
	public AbyssMetal()
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
		if (meta == 0)
		{
			return new int[] {0, 1, 0, 0};
		}
		else
		{
			return new int[] {0, 0, 0, 1};
		}
	}

	@Override
	public float getFoodValue(int meta)
	{
		return 30F;
	}

	@Override
	public float getSaturationValue(int meta)
	{
		return 0.8F;
	}

	@Override
	public int getSpecialEffect(int meta)
	{
		if (meta == 0)
		{
			return 2;
		}
		
		return 4;
	}
	
	
}