package com.lulan.shincolle.intermod;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.IShipMorph;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.utility.LogHelper;

import mchorse.metamorph.api.MorphAPI;
import mchorse.metamorph.api.events.MorphActionEvent;
import mchorse.metamorph.api.morphs.EntityMorph;
import mchorse.metamorph.capabilities.morphing.IMorphing;
import mchorse.metamorph.capabilities.morphing.Morphing;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;

/**
 * for mod: Metamorph
 */
public class MetamorphHelper
{
	
	
	/**
	 * called in event: EventHandler::onPlayerTick
	 */
	public static void onPlayerTickHelper(EntityPlayer player, CapaTeitoku capa)
	{
		//null check, every 16 ticks check
		if (capa == null || (player.ticksExisted & 31) != 0) return;
		
        IMorphing im = Morphing.get(player);
        
        if (im != null && im.getCurrentMorph() instanceof EntityMorph)
        {
        	EntityMorph em = (EntityMorph) im.getCurrentMorph();
        	
        	if (em.getEntity() instanceof IShipMorph)
        	{
        		//server side and player tick = 32, demorph (for reset morph on login)
        		if (!player.world.isRemote && player.ticksExisted == 32)
        		{
        			MorphAPI.demorph(player);
        			return;
        		}
        		
        		IShipMorph ism = (IShipMorph) em.getEntity();
        		
        		//both side: set morph host
        		if (ism.getMorphHost() == null)
        		{
        			ism.setIsMorph(true);
        			ism.setMorphHost(player);
        			capa.morphEntity = em.getEntity();
        		}
        	}
        }
        else
        {
        	capa.morphEntity = null;
        }
	}
	
	/**
	 * called in event: EventHandler::onMorphAction
	 */
	public static void onMorphActionHelper(MorphActionEvent event)
	{
		//if player hotbar != 1~4, open appearance gui
		LogHelper.debug("DEBUG: Metamorph: cast morph action: "+event.player.inventory.currentItem);
		
		//if player hotbar = 1~4, use ship skill
		if (!ConfigHandler.enableMetamorphSkill) return;
	}
	
}