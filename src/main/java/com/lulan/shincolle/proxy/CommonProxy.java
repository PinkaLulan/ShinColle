package com.lulan.shincolle.proxy;

import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.reference.Names;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public abstract class CommonProxy implements IProxy {
	
	public static final String channelName = Reference.MOD_ID;
	public static SimpleNetworkWrapper channel;
	
	@Override
	public void registerChannel() {	
		//SimpleNetworkWrapper packets
		channel = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		//register packets
		channel.registerMessage(S2CEntitySync.Handler.class, S2CEntitySync.class, Names.Packets.ENTITY_SYNC, Side.CLIENT);
		channel.registerMessage(S2CSpawnParticle.Handler.class, S2CSpawnParticle.class, Names.Packets.SPAWN_PARTICLE, Side.CLIENT);
		channel.registerMessage(S2CGUIPackets.Handler.class, S2CGUIPackets.class, Names.Packets.GUI_SYNC, Side.CLIENT);
		channel.registerMessage(C2SGUIPackets.Handler.class, C2SGUIPackets.class, Names.Packets.GUI_CLICK, Side.SERVER);
	}

}
