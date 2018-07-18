package com.lulan.shincolle.init;

import com.lulan.shincolle.handler.CapabilityHandler;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.handler.EventHandler;

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
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
	}
	
	
}
