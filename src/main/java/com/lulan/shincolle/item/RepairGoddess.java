package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RepairGoddess extends BasicItem {
	
	public RepairGoddess() {
		super();
		this.setUnlocalizedName("RepairGoddess");
		this.maxStackSize = 16;
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {  	
    	list.add(EnumChatFormatting.RED + I18n.format("gui.shincolle:repairgoddess"));
    }
    
	//item glow effect
  	@Override
  	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack item, int pass) {
  		return true;
	}

}
