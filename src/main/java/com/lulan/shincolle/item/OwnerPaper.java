package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.entity.ExtendPlayerProps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class OwnerPaper extends BasicItem {
	
	public static final String SignNameA = "SignNameA";	//player name tag
	public static final String SignNameB = "SignNameB";
	public static final String SignIDA = "SignIDA";		//player id tag
	public static final String SignIDB = "SignIDB";
	
	
	public OwnerPaper() {
		super();
		this.setUnlocalizedName("OwnerPaper");
		this.maxStackSize = 1;
	}
	
	//right click to sign the paper
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
//		if(!world.isRemote) {
			ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);

			if(extProps != null) {
				//first time use
				if(!itemstack.hasTagCompound()) {
					itemstack.setTagCompound(new NBTTagCompound());
					itemstack.getTagCompound().setString(SignNameA, player.getDisplayName());
					itemstack.getTagCompound().setString(SignNameB, "");
					itemstack.getTagCompound().setInteger(SignIDA, -1);
					itemstack.getTagCompound().setInteger(SignIDB, -1);
					itemstack.getTagCompound().setBoolean("signPos", false);
					
					//get player UID
					itemstack.getTagCompound().setInteger(SignIDA, extProps.getPlayerUID());

				}
				//use > second time
				else {
					//signPos: true -> sign at A, false -> sign at B
					if(itemstack.getTagCompound().getBoolean("signPos")) {
						itemstack.getTagCompound().setString(SignNameA, player.getDisplayName());
						itemstack.getTagCompound().setInteger(SignIDA, extProps.getPlayerUID());
						itemstack.getTagCompound().setBoolean("signPos", false);
					}
					else {
						itemstack.getTagCompound().setString(SignNameB, player.getDisplayName());
						itemstack.getTagCompound().setInteger(SignIDB, extProps.getPlayerUID());
						itemstack.getTagCompound().setBoolean("signPos", true);
					}
				}
			}//end extprops != null
//		}
		
		return itemstack;
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
    	if(itemstack.hasTagCompound()) {
    		list.add(EnumChatFormatting.AQUA + itemstack.getTagCompound().getString(SignNameA));
    		list.add(EnumChatFormatting.RED + String.valueOf(itemstack.getTagCompound().getInteger(SignIDA)));
    		list.add(EnumChatFormatting.AQUA + itemstack.getTagCompound().getString(SignNameB));
    		list.add(EnumChatFormatting.RED + String.valueOf(itemstack.getTagCompound().getInteger(SignIDB)));
    	}
    	
    }

}
