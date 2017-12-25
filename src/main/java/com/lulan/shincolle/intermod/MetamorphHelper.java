package com.lulan.shincolle.intermod;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipMorph;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

import mchorse.metamorph.api.MorphAPI;
import mchorse.metamorph.api.events.MorphActionEvent;
import mchorse.metamorph.api.morphs.EntityMorph;
import mchorse.metamorph.capabilities.morphing.IMorphing;
import mchorse.metamorph.capabilities.morphing.Morphing;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

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
        		
        		//both side: set morph host
        		IShipMorph ism = (IShipMorph) em.getEntity();
        		
    			ism.setIsMorph(true);
    			ism.setMorphHost(player);
    			capa.morphEntity = em.getEntity();
        	}
        }
        else
        {
        	capa.morphEntity = null;
        }
	}
	
	/**
	 * called in event: EventHandler::onMorphAction
	 * SERVER SIDE ONLY
	 */
	public static void onMorphActionHelper(MorphActionEvent event)
	{
		LogHelper.debug("DEBUG: Metamorph: cast morph action: "+event.player.inventory.currentItem);
		
		//server side
		if (!event.player.world.isRemote)
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(event.player);
			int ci = event.player.inventory.currentItem;
			
//			//active ship skill bar: currentItem = 0~3: skill, 4~8:inventory
//			if (ConfigHandler.enableMetamorphSkill)
//			{
//				//open morph gui
//				if (ci > 3)
//				{
//					openMorphGui(capa);
//					return;
//				}
//				//use morph skill 0~3
//				else
//				{
//					//TODO
//				}
//			}
//			else
//			{
				//open morph gui
				openMorphGui(capa);
				return;
//			}
		}
		
	}
	
	/**
	 * open morph entity gui
	 */
	public static void openMorphGui(CapaTeitoku capa)
	{
		if (capa != null && capa.player != null && !capa.player.world.isRemote &&
			capa.morphEntity instanceof BasicEntityShip)
		{
			FMLNetworkHandler.openGui(capa.player, ShinColle.instance, ID.Gui.MORPHINVENTORY, capa.player.world, 0, 0, 0);
			return;
		}
	}
	
	
}