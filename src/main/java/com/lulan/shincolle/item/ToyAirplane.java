package com.lulan.shincolle.item;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ToyAirplane extends BasicItem implements IShipFoodItem
{
	
	private static final String NAME = "ToyAirplane";
	
	public ToyAirplane()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        
        GameRegistry.register(this);
	}
	
	@Override
	public float getFoodValue(int meta)
	{
		return 150F;
	}

	@Override
	public float getSaturationValue(int meta)
	{
		return 1.5F;
	}

	@Override
	public int getSpecialEffect(int meta)
	{
		return 5;
	}
	
	
}