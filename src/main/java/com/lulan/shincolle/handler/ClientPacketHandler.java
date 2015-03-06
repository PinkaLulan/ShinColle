package com.lulan.shincolle.handler;

import java.io.IOException;

import com.lulan.shincolle.network.ProcessPacketClientSide;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;

//client packet handler by Jabelar
public class ClientPacketHandler extends ServerPacketHandler {
	
	@SubscribeEvent
	public void onClientPacket(ClientCustomPacketEvent event) throws IOException {
		//取得頻道名稱
		channelName = event.packet.channel();
		
		if (channelName.equals(CommonProxy.channelName)) {
			ProcessPacketClientSide.processPacketOnClient(event.packet.payload(), event.packet.getTarget());
		}
	}  
}