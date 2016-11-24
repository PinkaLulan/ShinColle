package com.lulan.shincolle.item;

import net.minecraftforge.fml.common.registry.GameRegistry;


public class Grudge extends BasicItem implements IShipResourceItem, IShipFoodItem
{
	
	private static final String NAME = "Grudge";
	
	public Grudge()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        
        GameRegistry.register(this);
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
