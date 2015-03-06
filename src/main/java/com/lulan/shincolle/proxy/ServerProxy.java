package com.lulan.shincolle.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {

	public static World getServerWorld() {
		return MinecraftServer.getServer().getEntityWorld();
	}
	
	@Override
	public void registerKeyBindings() {		
	}

	@Override
	public void registerRender() {		
	}

}
