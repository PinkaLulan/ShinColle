package com.lulan.shincolle.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**SERVER TO CLIENT : SPAWN PARTICLE PACKET
 * 用於指定位置生成particle
 * packet handler同樣建立在此class中
 * 
 * tut by diesieben07: http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
 */
public class C2SGUIPackets implements IMessage {
	
	private static World serverWorld;
	private static BasicEntityShip sendEntity;
	private static BasicEntityShip recvEntity;
	private static BasicTileEntity sendTile;
	private static BasicTileEntity recvTile;
	private static int entityID;
	private static int sendType;
	private static int recvType;
	private static int sendButton;
	private static int recvButton;
	private static int sendValue;
	private static int recvValue;
	private static int sendValue2;
	private static int recvValue2;
	private static int recvPosX;
	private static int recvPosY;
	private static int recvPosZ;
	
	
	public C2SGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//GUI click: 
	//type 0: ship entity gui click
	public C2SGUIPackets(BasicEntityShip entity, int button, int value) {
        this.sendEntity = entity;
        this.sendType = 0;
        this.sendButton = button;
        this.sendValue = value;
    }
	
	//type 1: shipyard gui click
	public C2SGUIPackets(BasicTileEntity tile, int button, int value, int value2) {
        this.sendTile = tile;
        this.sendType = 1;
        this.sendButton = button;
        this.sendValue = value;
        this.sendValue2 = value2;
    }
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf) {
		//get server world
		this.serverWorld = MinecraftServer.getServer().getEntityWorld();
		
		//get type and entityID
		this.recvType = buf.readByte();
	
		switch(recvType) {
		case 0:	//ship entity gui click
			{
				this.entityID = buf.readInt();
				recvEntity = (BasicEntityShip) EntityHelper.getEntityByID(entityID, serverWorld);
				this.recvButton = buf.readByte();
				this.recvValue = buf.readByte();
				//set value
				EntityHelper.setEntityByGUI(recvEntity, (int)recvButton, (int)recvValue);
//				LogHelper.info("DEBUG : recv GUI click: "+recvButton+" "+recvValue);
			}
			break;
		case 1: //shipyard gui click
			{
				this.recvPosX = buf.readInt();
				this.recvPosY = buf.readInt();
				this.recvPosZ = buf.readInt();
				this.recvButton = buf.readByte();
				this.recvValue = buf.readByte();
				this.recvValue2 = buf.readByte();			
				this.recvTile = (BasicTileEntity) serverWorld.getTileEntity(recvPosX, recvPosY, recvPosZ);
				//set value
				EntityHelper.setTileEntityByGUI(recvTile, (int)recvButton, (int)recvValue, (int)recvValue2);
//				LogHelper.info("DEBUG : recv packet (server side): GUI click:"+recvButton+" "+recvValue+" "+recvValue2);
			}
			break;
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.sendType) {
		case 0:	//ship entity gui click
			{
				buf.writeByte(0);
				buf.writeInt(this.sendEntity.getEntityId());
				buf.writeByte(this.sendButton);
				buf.writeByte(this.sendValue);
			}
			break;
		case 1:	//shipyard gui click
			{
				buf.writeByte(1);
				buf.writeInt(this.sendTile.xCoord);
				buf.writeInt(this.sendTile.yCoord);
				buf.writeInt(this.sendTile.zCoord);
				buf.writeByte(this.sendButton);
				buf.writeByte(this.sendValue);
				buf.writeByte(this.sendValue2);
			}
			break;
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<C2SGUIPackets, IMessage> {
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(C2SGUIPackets message, MessageContext ctx) {
//          System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
//			LogHelper.info("DEBUG : recv GUI Click packet : type "+recvType+" button ");
			return null;
		}
    }
	

}


