package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.crafting.EquipCalc;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.renderentity.BasicRenderEntity;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class MarriageRing extends BasicItem {
	
	public MarriageRing() {
		super();
		this.setUnlocalizedName("MarriageRing");
	}
	
	//activate or deactivate ring
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
			boolean invActive = !isActive;
			itemstack.getTagCompound().setBoolean("isActive", invActive);
			
			//change player state
			ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null) {
				extProps.setRingActive(invActive);
				
				//disable fly
				if(!extProps.isRingActive() && !player.capabilities.isCreativeMode && extProps.isRingFlying()) {
					player.capabilities.isFlying = false;
					extProps.setRingFlying(false);
				}
				
				//sync ring state to client
				CommonProxy.channelG.sendTo(new S2CGUIPackets(extProps), (EntityPlayerMP) player);
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
	
	//這裡有許多client端判定, 因此不能設為server only, 如此動作才會正確 (ex: change dig speed, fly mode...)
	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int slot, boolean inUse) {
		ExtendPlayerProps extProps = null;
		EntityPlayer owner = null;

		if(entity instanceof EntityPlayer) {
			extProps = (ExtendPlayerProps) entity.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			owner = (EntityPlayer) entity;
			
			//ring effects
			if(extProps != null) {
				//fly mode: marriages > 5
				if(extProps.getMarriageNum() > 5) {
					if(entity.isInWater() || entity.handleLavaMovement()) {
						//not flying and ring is active
						if(!owner.capabilities.isFlying && extProps.isRingActive()) {
							owner.capabilities.isFlying = true;
							extProps.setRingFlying(true);
						}
					}
					//cancel flying when leave water
					else {
						if(extProps.isRingFlying() && !owner.capabilities.isCreativeMode && owner.capabilities.isFlying) {
							owner.capabilities.isFlying = false;
							extProps.setRingFlying(false);
						}
					}
				}	
			
				//water breathing: no requirement
				if(owner.ticksExisted % 100 == 0) {
		            if(owner.getAir() < 300) {
		            	owner.setAir(300);
		            }
				}

			}//end extPros != null
		}//end instanceof player
	}
	
	//show ability text
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {  		
		ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null) {
    		list.add(EnumChatFormatting.AQUA + I18n.format("gui.shincolle:ringText") + " " + extProps.getMarriageNum());
    	}
	}
	
	
}
