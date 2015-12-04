package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.reference.ID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class Ammo extends BasicItem implements IShipItemType {
	byte types = 4;
	IIcon[] icons = new IIcon[4];
	
	public Ammo() {
		super();
		this.setUnlocalizedName("Ammo");
		this.setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i=0; i<types; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) {	
		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"0");
		icons[1] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"1");
		icons[2] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"2");
		icons[3] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"3");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		if(meta > types - 1) meta = types - 1;
	    return this.icons[meta];
	}
	
	@Override
	public int getItemType() {
		return ID.ItemType.Ammo;
	}
	
	
}
