package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicEquip extends BasicItem {	
	byte types;
	IIcon[] icons = new IIcon[3];
	
	public BasicEquip() {
		super();
		this.maxStackSize = 1;	
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
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
	    if(meta < 2) {				//0,1 = single cannon
	    	return this.icons[0];
	    }
	    else if(meta < 7) {			//2~6 = twin cannon
	    	return this.icons[1];
	    }
	    else if(meta < 9) {			//7,8 = triple cannon
	    	return this.icons[2];
	    }
	   
	    return icons[0];
	}

}
