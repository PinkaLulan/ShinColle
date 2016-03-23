package com.lulan.shincolle.item;


public class Grudge extends BasicItem implements IShipResourceItem {
	
	public Grudge() {
		super();
		this.setUnlocalizedName("Grudge");
	}
	
	@Override
	public int[] getResourceValue(int meta) {
		return new int[] {1, 0, 0, 0};
	}
	
	
}
