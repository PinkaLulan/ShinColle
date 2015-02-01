package com.lulan.shincolle.handler;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**for EVENT_BUS event only <br>
 * (not for FML event)
 */
public class EVENT_BUS_EventHandler {

	//change vanilla mob drop (add grudge)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void eventDrop(LivingDropsEvent event) {
	    if(event.entity instanceof EntityMob || event.entity instanceof EntitySlime ) {
	        ItemStack drop = new ItemStack(ModItems.Grudge, 1);
	        event.drops.add(new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ, drop));
	    }
	}
	
	//add kills number
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void eventDeath(LivingDeathEvent event) {
		Entity ent = event.source.getSourceOfDamage();
	    if(ent != null && ent instanceof BasicEntityShip) {
	        ((BasicEntityShip)ent).addKills();
	    }
	}
	
	//add ship extend props to entity
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
	    if(event.entity instanceof BasicEntityShip) {
	    	LogHelper.info("DEBUG : get entity construct event");
	        event.entity.registerExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME, new ExtendShipProps());
	    }
	}
	
}
