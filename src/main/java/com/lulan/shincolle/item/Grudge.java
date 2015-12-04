package com.lulan.shincolle.item;

import com.lulan.shincolle.reference.ID;

public class Grudge extends BasicItem implements IShipItemType {
	public Grudge() {
		super();
		this.setUnlocalizedName("Grudge");
	}
	
	@Override
	public int getItemType() {
		return ID.ItemType.Grudge;
	}
	
	
}
