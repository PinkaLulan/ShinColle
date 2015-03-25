package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class OwnerPaper extends BasicItem {
	
	public OwnerPaper() {
		super();
		this.setUnlocalizedName("OwnerPaper");
		this.maxStackSize = 1;
	}
	
	//activate or deactivate ring
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		//right click to sign the paper
//		if(!world.isRemote) {
			//add sign data
			if(!itemstack.hasTagCompound()) {	//use first time
				itemstack.setTagCompound(new NBTTagCompound());
				itemstack.getTagCompound().setString("signA", player.getUniqueID().toString());
				itemstack.getTagCompound().setString("signA2", player.getDisplayName());
				itemstack.getTagCompound().setString("signB", "");
				itemstack.getTagCompound().setString("signB2", "");
				itemstack.getTagCompound().setBoolean("signPos", false);
			}
			else {	//use > second time
				//signPos: true -> sign at A, false -> sign at B
				if(itemstack.getTagCompound().getBoolean("signPos")) {
					itemstack.getTagCompound().setString("signA", player.getUniqueID().toString());
					itemstack.getTagCompound().setString("signA2", player.getDisplayName());
					itemstack.getTagCompound().setBoolean("signPos", false);
				}
				else {
					itemstack.getTagCompound().setString("signB", player.getUniqueID().toString());
					itemstack.getTagCompound().setString("signB2", player.getDisplayName());
					itemstack.getTagCompound().setBoolean("signPos", true);
				}
			}
			
			return itemstack;
//		}
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {  	
    	
    	if(itemstack.hasTagCompound()) {
    		list.add(EnumChatFormatting.AQUA + itemstack.getTagCompound().getString("signA2"));
    		list.add(EnumChatFormatting.AQUA + itemstack.getTagCompound().getString("signB2"));	
    	}
    	
    }

}
