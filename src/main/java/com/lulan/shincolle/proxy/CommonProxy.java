package com.lulan.shincolle.proxy;

import com.lulan.shincolle.handler.ClientPacketHandler;
import com.lulan.shincolle.handler.ServerPacketHandler;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class CommonProxy implements IProxy {
	
	public static final String channelName = Reference.MOD_ID;
	public static SimpleNetworkWrapper channel;
	
//	//old netty packets
//	public static FMLEventChannel channel;
	
	
	@Override
	public void registerChannel() {
		
//		//old netty packets
//		//packet handler + channel register
//		channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelName);
//		channel.register(new ServerPacketHandler());
//		channel.register(new ClientPacketHandler());
		
		//SimpleNetworkWrapper packets
		channel = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		
		
	}

}
