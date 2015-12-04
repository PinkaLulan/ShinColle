package com.lulan.shincolle.item;

import com.lulan.shincolle.reference.ID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class EquipArmor extends BasicEquip {
	
	IIcon[] icons = new IIcon[1];
	
	public EquipArmor() {
		super();
		this.setUnlocalizedName("EquipArmor");
		this.types = 2;
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {	
		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1));
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
	    return icons[0];
	}
	
	@Override
	public int getItemType() {
		return ID.ItemType.EquipArmor;
	}
	

}


