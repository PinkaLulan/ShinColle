package com.lulan.shincolle.handler;

import java.io.IOException;

import com.lulan.shincolle.network.ProcessPacketServerSide;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;

//server packet handler by Jabelar
public class ServerPacketHandler {
	protected String channelName;
	protected EntityPlayerMP thePlayer;
 
	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) throws IOException {
		//取得頻道名稱
		channelName = event.packet.channel();
  
		//取得玩家
		// Thanks to GoToLink for helping figure out how to get player entity
		NetHandlerPlayServer theNetHandlerPlayServer = (NetHandlerPlayServer)event.handler;
		thePlayer = theNetHandlerPlayServer.playerEntity;
  
		//從該玩家接收packet
		// if you want the server (the configurationManager is useful as it has player lists and such
		// you can use something like: MinecraftServer server MinecraftServer.getServer();
		if (channelName.equals(CommonProxy.channelName)) {
			LogHelper.info("Server received packet from player = "+thePlayer.getEntityId());
			ProcessPacketServerSide.processPacketOnServer(event.packet.payload(), event.packet.getTarget(), thePlayer);
		}
	}
}