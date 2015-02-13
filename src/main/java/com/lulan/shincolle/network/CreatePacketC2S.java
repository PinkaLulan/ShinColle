package com.lulan.shincolle.network;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.IOException;

import net.minecraft.tileentity.TileEntity;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Names;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;

/**CREATE PACKET CLIENT TO SERVER
 */
public class CreatePacketC2S { 
	
	public CreatePacketC2S() {}

	//GUI click packet: ship inventory
	private static FMLProxyPacket createGUIShipInvClickPacket(BasicEntityShip entity, int button, int value) throws IOException {
		
		//建立packet傳輸stream
		ByteBufOutputStream bbos = new ByteBufOutputStream(Unpooled.buffer());
		
		//Packet ID (會放在封包頭以辨識封包類型)
		bbos.writeByte(Names.Packets.GUI_SHIPINV);
		//Entity ID (用於辨識entity是那一隻)
		bbos.writeInt(entity.getEntityId());
		//以下寫入要傳送的資料
		bbos.writeByte((byte)button);
		bbos.writeByte((byte)value);

		// put payload into a packet  
		FMLProxyPacket thePacket = new FMLProxyPacket(bbos.buffer(), CommonProxy.channelName);
		// don't forget to close stream to avoid memory leak
		bbos.close();
  
		return thePacket;
	}
	
	//GUI click packet: shipyard
	private static FMLProxyPacket createGUIShipyardClickPacket(TileEntity entity, int button, int value, int value2) throws IOException {
		
		//建立packet傳輸stream
		ByteBufOutputStream bbos = new ByteBufOutputStream(Unpooled.buffer());
		
		//Packet ID (會放在封包頭以辨識封包類型)
		bbos.writeByte(Names.Packets.GUI_SHIPYARD);
		//Entity ID (用於辨識entity是那一隻)
		bbos.writeInt(entity.xCoord);
		bbos.writeInt(entity.yCoord);
		bbos.writeInt(entity.zCoord);
		//以下寫入要傳送的資料
		bbos.writeByte((byte)button);
		bbos.writeByte((byte)value);
		bbos.writeByte((byte)value2);

		// put payload into a packet  
		FMLProxyPacket thePacket = new FMLProxyPacket(bbos.buffer(), CommonProxy.channelName);
		// don't forget to close stream to avoid memory leak
		bbos.close();
  
		return thePacket;
	}
 
	//send packet to server
	private static void sendToServer(FMLProxyPacket parPacket) {
		CommonProxy.channel.sendToServer(parPacket);
	}
  
	//send GUI click packet
	public static void sendC2SGUIShipInvClick(BasicEntityShip entity, int button, int value) {
		try {
			LogHelper.info("DEBUG : send GUI ShipInv click packet to server");
			sendToServer(createGUIShipInvClickPacket(entity, button, value));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//send GUI click packet
	public static void sendC2SGUIShipyardClick(TileEntity entity, int button, int value, int value2) {
		try {
			LogHelper.info("DEBUG : send GUI Shipyard click packet to server");
			sendToServer(createGUIShipyardClickPacket(entity, button, value, value2));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
