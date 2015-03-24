package com.lulan.shincolle.handler;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**for EVENT_BUS event only <br>
 * (not for FML event)
 */
public class EVENT_BUS_EventHandler {

	//change vanilla mob drop (add grudge), this is SERVER event
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void eventDrop(LivingDropsEvent event) {
	    if(event.entity instanceof EntityMob || event.entity instanceof EntitySlime ) {
	        ItemStack drop = new ItemStack(ModItems.Grudge, 1);
	        event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, drop));
	    }
	    
	    if(event.entity instanceof BasicEntityShip) {
	    	BasicEntityShip entity = (BasicEntityShip)event.entity;
	    	
	    	//drop ship item
	    	if(entity.getShipLevel() > 1) {		
	    		ItemStack item = new ItemStack(ModItems.ShipSpawnEgg, 1, entity.getShipID()+2);
		    	BasicEntityItem entityItem = new BasicEntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY+0.5D, event.entity.posZ, item);
		    	NBTTagCompound nbt = new NBTTagCompound();
		    	ExtendShipProps extProps = entity.getExtProps();
		    	
		    	//get inventory data
				NBTTagList list = new NBTTagList();
				for(int i = 0; i < extProps.slots.length; i++) {
					if(extProps.slots[i] != null) {
						NBTTagCompound item2 = new NBTTagCompound();
						item2.setByte("Slot", (byte)i);
						extProps.slots[i].writeToNBT(item2);
						list.appendTag(item2);
					}
				}
				
				//get attributes data
		    	int[] attrs = new int[8];
		    	attrs[0] = entity.getShipLevel() - 1;	//decrease level 1
		    	attrs[1] = entity.getBonusPoint(ID.HP);
		    	attrs[2] = entity.getBonusPoint(ID.ATK);
		    	attrs[3] = entity.getBonusPoint(ID.DEF);
		    	attrs[4] = entity.getBonusPoint(ID.SPD);
		    	attrs[5] = entity.getBonusPoint(ID.MOV);
		    	attrs[6] = entity.getBonusPoint(ID.HIT);
		    	attrs[7] = entity.getStateFlagI(ID.F.IsMarried);
		    	
		    	//save nbt and spawn entity item
		    	nbt.setTag("ShipInv", list);	//save inventory data to nbt
		    	nbt.setIntArray("Attrs", attrs);	//save attributes data to nbt
		    	entityItem.getEntityItem().setTagCompound(nbt);	  //save nbt to entity item
		    	event.entity.worldObj.spawnEntityInWorld(entityItem);	//spawn entity item
	    	}	
	    }
	}
	
	//apply ring effect: boost dig speed in water
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void eventGetBreakSpeed(PlayerEvent.BreakSpeed event) {
		ExtendPlayerProps extProps = (ExtendPlayerProps) event.entityPlayer.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		if(extProps != null && (event.entityPlayer.isInWater() || event.entityPlayer.handleLavaMovement())) {
			int digBoost = extProps.getDigSpeedBoost() + 1;
			//boost speed
			event.newSpeed = event.originalSpeed * digBoost;
		}		
	}
	
	//apply ring effect: vision in the water
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void eventSetLiquidFog(EntityViewRenderEvent.FogDensity event) {
		if(event.entity.isInsideOfMaterial(Material.lava) ||
		   event.entity.isInsideOfMaterial(Material.water)){
			
			ExtendPlayerProps extProps = (ExtendPlayerProps) event.entity.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
			
			if(extProps != null && extProps.isRingActive()) {
				float fogDen = 0.1F - (float)extProps.getMarriageNum() * 0.02F;
				
				if(fogDen < 0.01F) fogDen = 0.001F;
				
				event.setCanceled(true);	//取消原本的fog render
	            event.density = fogDen;		//重設fog濃度
	            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			}   
            
//			float fogStart = 0F;	//for linear fog
//			float fogEnd = 20F;		//for linear fog
//            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
//            GL11.glFogf(GL11.GL_FOG_START, fogStart);	//fog start distance, only for linear fog
//            GL11.glFogf(GL11.GL_FOG_END, fogEnd);		//fog end distance, only for linear fog
		}
	}
	
	//add kills number
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void eventDeath(LivingDeathEvent event) {
		Entity ent = event.source.getSourceOfDamage();
	    if(ent != null) {
	    	if(ent instanceof BasicEntityShip) {
	    		((BasicEntityShip)ent).addKills();
	    	}
	    	else if(ent instanceof BasicEntityAirplane) {
	    		if(((BasicEntityAirplane) ent).getOwner() != null) {
	    			((BasicEntityShip)((BasicEntityAirplane) ent).getOwner()).addKills();
	    		}
	    	}  
	    }
	}
	
	//add extend props to entity
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
	    //ship ext props
		if(event.entity instanceof BasicEntityShip && event.entity.getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME) == null) {
	    	LogHelper.info("DEBUG : add ship extend props");
	        event.entity.registerExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME, new ExtendShipProps());
	    }
		
		//player ext props
		if(event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME) == null) {
	    	LogHelper.info("DEBUG : add player extend props");
	        event.entity.registerExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME, new ExtendPlayerProps());
		}
	}
	
}
