package com.lulan.shincolle.item;

import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MarriageRing extends BasicItem {
	
	public MarriageRing() {
		super();
		this.setUnlocalizedName("MarriageRing");
	}
	
	//use item on block
	@Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		//right click to launch	
		if(!world.isRemote) {
			//change ring state
			if(!itemstack.hasTagCompound()) {
				itemstack.setTagCompound(new NBTTagCompound());
				itemstack.getTagCompound().setBoolean("isActive", false);
			}
			
			boolean isActive = itemstack.getTagCompound().getBoolean("isActive");
			itemstack.getTagCompound().setBoolean("isActive", !isActive);
			
			//change player state
			ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null) {
				extProps.setRingActive(!isActive);

				if(!extProps.isRingActive()) {
					LogHelper.info("DEBUG : cancel fly");
					extProps.setRingFlying(false);
					player.capabilities.isFlying = false;
				}
			}
			
			return true;
		}

		return false;
	}
	
	//use item in air
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		//right click to launch	
		if(!world.isRemote) {
			//change ring state
			if(!itemstack.hasTagCompound()) {
				itemstack.setTagCompound(new NBTTagCompound());
				itemstack.getTagCompound().setBoolean("isActive", false);
			}
			
			boolean isActive = itemstack.getTagCompound().getBoolean("isActive");
			itemstack.getTagCompound().setBoolean("isActive", !isActive);
			
			//change player state
			ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null) {
				extProps.setRingActive(!isActive);
				
				if(!extProps.isRingActive()) {
LogHelper.info("DEBUG : cancel fly");
					extProps.setRingFlying(false);
					player.capabilities.isFlying = false;	//not work?
				}
			}
		}
		
		return itemstack;
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item, int pass) {
		if(item.hasTagCompound()) {
			return item.getTagCompound().getBoolean("isActive");
		}
        return false;
    }
	
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int slot, boolean inUse) {
		ExtendPlayerProps extProps = null;
		
		if(entity instanceof EntityPlayer) {
			extProps = (ExtendPlayerProps) entity.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
			if(extProps != null) {
				if(entity.isInWater()) {
					if(item.hasTagCompound() && item.getTagCompound().getBoolean("isActive")) {
						extProps.setRingFlying(true);
						((EntityPlayer) entity).capabilities.isFlying = true;
					}
					//ring is not actived, cancel fly
					else if(extProps.isRingFlying()) {
						extProps.setRingFlying(false);
						((EntityPlayer) entity).capabilities.isFlying = false;
					}
				}
				else {	//增加ring flying flag檢查, 避免持續關掉其他mod的fly功能
					if(extProps.isRingFlying()) {
						extProps.setRingFlying(false);
						((EntityPlayer) entity).capabilities.isFlying = false;
					}
				}
			}
		}	
	}

	
	
}
