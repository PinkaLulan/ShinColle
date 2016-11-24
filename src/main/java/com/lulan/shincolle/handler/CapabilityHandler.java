package com.lulan.shincolle.handler;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.capability.CapaTeitokuProvider;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * teitoku data capability storage
 * tut: http://www.planetminecraft.com/blog/forge-tutorial-capability-system/
 *
 */
public class CapabilityHandler
{
	
	public static final ResourceLocation CAPA_TEITOKU_NAME = new ResourceLocation(Reference.MOD_ID, CapaTeitoku.CAPA_KEY);


	/**
	 * attach capability event
	 */
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent.Entity event)
	{
		if (event.getEntity() instanceof EntityPlayer && !(event.getEntity() instanceof FakePlayer))
		{
			event.addCapability(CAPA_TEITOKU_NAME, new CapaTeitokuProvider());
		}
		
	}
	
	
}
