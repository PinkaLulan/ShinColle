package com.lulan.shincolle.item;


public class Grudge extends BasicItem implements IShipResourceItem, IShipFoodItem {
	
	public Grudge() {
		super();
		this.setUnlocalizedName("Grudge");
	}
	
	@Override
	public int[] getResourceValue(int meta) {
		return new int[] {1, 0, 0, 0};
	}
	
	@Override
	public float getFoodValue(int meta) {
		return 10F;
	}

	@Override
	public float getSaturationValue(int meta) {
		return 0.5F;
	}

	@Override
	public int getSpecialEffect(int meta) {
		return 1;
	}
	
	
}
