package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class EquipAirplane extends BasicEquip {
	
	IIcon[] icons = new IIcon[4];
	
	public EquipAirplane() {
		super();
		this.setUnlocalizedName("EquipAirplane");
		this.types = 15;	//T=4,F=5,B=4,R=2
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
	    if(meta < 4) {				//0~3 = Torpedo
	    	return this.icons[0];
	    }
	    else if(meta < 9) {			//4~8 = Fighter
	    	return this.icons[1];
	    }
	    else if(meta < 13) {		//9~12 = Bomber
	    	return this.icons[2];
	    }
	    else if(meta < 15) {		//13~14 = Recon
	    	return this.icons[3];
	    }
	   
	    return icons[0];
	}

}
