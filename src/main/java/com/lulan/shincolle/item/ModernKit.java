package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ModernKit extends BasicItem {

	public ModernKit() {
		super();
		this.setUnlocalizedName("ModernKit");
		this.maxStackSize = 64;
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {  	
    	list.add(EnumChatFormatting.GOLD + I18n.format("gui.shincolle:modernkit"));
    }
	
}
