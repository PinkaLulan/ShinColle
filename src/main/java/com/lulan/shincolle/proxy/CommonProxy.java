package com.lulan.shincolle.proxy;

import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CInputPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.reference.ID;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public abstract class CommonProxy implements IProxy {
	
	public static final String channelNameE = "shinEntity";
	public static final String channelNameG = "shinGUI";
	public static final String channelNameP = "shinParticle";
	
	/**packet system
	 * channelE: entity sync
	 * channelG: gui sync and input
	 * channelP: particle
	 */
	public static SimpleNetworkWrapper channelE;
	public static SimpleNetworkWrapper channelG;
	public static SimpleNetworkWrapper channelP;

	public static boolean isMultiplayer = false;	//the world is MP or SP
	
	
	@Override
	public void registerChannel() {
		//SimpleNetworkWrapper packets
		channelE = NetworkRegistry.INSTANCE.newSimpleChannel(channelNameE);
		channelG = NetworkRegistry.INSTANCE.newSimpleChannel(channelNameG);
		channelP = NetworkRegistry.INSTANCE.newSimpleChannel(channelNameP);
		
		//register packets
		//entity sync packet
		channelE.registerMessage(S2CEntitySync.Handler.class, S2CEntitySync.class, ID.Packets.S2C_EntitySync, Side.CLIENT);
		//particle packet
		channelP.registerMessage(S2CSpawnParticle.Handler.class, S2CSpawnParticle.class, ID.Packets.S2C_Particle, Side.CLIENT);
		//GUI/Input packet
		channelG.registerMessage(S2CGUIPackets.Handler.class, S2CGUIPackets.class, ID.Packets.S2C_GUISync, Side.CLIENT);
		channelG.registerMessage(C2SGUIPackets.Handler.class, C2SGUIPackets.class, ID.Packets.C2S_GUIInput, Side.SERVER);
		channelG.registerMessage(S2CInputPackets.Handler.class, S2CInputPackets.class, ID.Packets.S2C_CmdSync, Side.CLIENT);
		channelG.registerMessage(C2SInputPackets.Handler.class, C2SInputPackets.class, ID.Packets.C2S_CmdInput, Side.SERVER);
		
	}
	

}
