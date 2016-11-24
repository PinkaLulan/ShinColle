package com.lulan.shincolle.init;

import com.lulan.shincolle.handler.CapabilityHandler;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.handler.EVENT_BUS_EventHandler;
import com.lulan.shincolle.handler.FML_COMMON_EventHandler;

import net.minecraftforge.common.MinecraftForge;

/**
 * 註冊所有event handler
 *
 */
public class ModEvents
{
	
	
	public static void init()
	{
		//登錄以下handler到event bus中 使其能接收event
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());
		MinecraftForge.EVENT_BUS.register(new FML_COMMON_EventHandler());
		MinecraftForge.EVENT_BUS.register(new EVENT_BUS_EventHandler());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
	}
	
	
}
