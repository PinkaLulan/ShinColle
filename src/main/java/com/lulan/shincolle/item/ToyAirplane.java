package com.lulan.shincolle.item;

public class ToyAirplane extends BasicItem implements IShipFoodItem {
	
	public ToyAirplane() {
		super();
		this.setUnlocalizedName("ToyAirplane");
	}
	
	@Override
	public float getFoodValue(int meta) {
		return 150F;
	}

	@Override
	public float getSaturationValue(int meta) {
		return 1.5F;
	}

	@Override
	public int getSpecialEffect(int meta) {
		return 5;
	}
	
	
}