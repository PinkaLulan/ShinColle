package com.lulan.shincolle.proxy;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.reference.Names;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public abstract class CommonProxy implements IProxy {
	
	public static final String channelNameE = "shinEntity";
	public static final String channelNameG = "shinGUI";
	public static final String channelNameP = "shinParticle";
	
	public static SimpleNetworkWrapper channelE;
	public static SimpleNetworkWrapper channelG;
	public static SimpleNetworkWrapper channelP;
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
	
	@Override
	public void registerChannel() {
		//SimpleNetworkWrapper packets
		channelE = NetworkRegistry.INSTANCE.newSimpleChannel(channelNameE);
		channelG = NetworkRegistry.INSTANCE.newSimpleChannel(channelNameG);
		channelP = NetworkRegistry.INSTANCE.newSimpleChannel(channelNameP);
		
		//register packets
		//entity sync packet
		channelE.registerMessage(S2CEntitySync.Handler.class, S2CEntitySync.class, Names.Packets.ENTITY_SYNC, Side.CLIENT);
		//GUI packet
		channelP.registerMessage(S2CSpawnParticle.Handler.class, S2CSpawnParticle.class, Names.Packets.SPAWN_PARTICLE, Side.CLIENT);
		//particle packet
		channelG.registerMessage(S2CGUIPackets.Handler.class, S2CGUIPackets.class, Names.Packets.GUI_SYNC, Side.CLIENT);
		channelG.registerMessage(C2SGUIPackets.Handler.class, C2SGUIPackets.class, Names.Packets.GUI_CLICK, Side.SERVER);
	}
	
	//save entity data in globe variable for resuming data after death
	//map set: UUID, nbt data
	public static void storeEntityData(String uuid, NBTTagCompound nbt) {
		extendedEntityData.put(uuid, nbt);
	}

	//get nbt data in map
	public static NBTTagCompound getEntityData(String name) {
		return extendedEntityData.get(name);
	}


}
