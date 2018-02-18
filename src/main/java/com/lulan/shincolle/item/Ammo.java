package com.lulan.shincolle.item;

import net.minecraftforge.fml.common.registry.GameRegistry;

/** meta:
 *     0: ammo 
 *     1: ammo container
 *     2: heavy ammo
 *     3: heavy ammo container
 *
 */
public class Ammo extends BasicItem implements IShipResourceItem, IShipFoodItem
{
	
	private static final String NAME = "Ammo";
	
	
	public Ammo()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        this.setHasSubtypes(true);
        
        GameRegistry.register(this);
	}
	
	@Override
	public int getTypes()
	{
		return 4;
	}

	@Override
	public int[] getResourceValue(int meta)
	{
		switch (meta)
		{
		case 0:
			return new int[] {0, 0, 1, 0};
		case 1:
			return new int[] {0, 0, 9, 0};
		case 2:
			return new int[] {0, 0, 4, 0};
		case 3:
			return new int[] {0, 0, 36, 0};
		default:
			return new int[] {0, 0, 0, 0};
		}
	}
	
	@Override
	public float getFoodValue(int meta)
	{
		return 5F;
	}

	@Override
	public float getSaturationValue(int meta)
	{
		return 0.3F;
	}

	@Override
	public int getSpecialEffect(int meta)
	{
		return 3;
	}
	
	
}