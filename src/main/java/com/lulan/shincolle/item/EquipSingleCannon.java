package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class EquipSingleCannon extends BasicEquip {
	
	public EquipSingleCannon() {
		super();
		this.setUnlocalizedName("EquipSingleCannon");
		this.types = 2;
	}	
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
    	int meta = itemstack.getItemDamage();   	
    	switch(meta) {
    	case 0:	//5-Inch Single Cannon
    		list.add(EnumChatFormatting.RED +"+3 Firepower");
    		break;
    	case 1: //6-Inch Single Cannon
    		list.add(EnumChatFormatting.RED +"+5 Firepower");
    		break;
    	}
    }

}
