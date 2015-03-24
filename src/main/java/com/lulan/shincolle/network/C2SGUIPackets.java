package com.lulan.shincolle.network;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.proxy.ServerProxy;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**SERVER TO CLIENT : SPAWN PARTICLE PACKET
 * 用於指定位置生成particle
 * packet handler同樣建立在此class中
 * 
 * tut by diesieben07: http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
 */
public class C2SGUIPackets implements IMessage {
	
	private World world;
	private BasicEntityShip entity;
	private BasicTileEntity tile;
	private EntityPlayer player;
	private int entityID, worldID, type, button, value, value2, posX, posY, posZ;
	
	
	public C2SGUIPackets() {}	//必須要有空參數constructor, forge才能使用此class
	
	//GUI click: 
	//type 0: ship entity gui click
	public C2SGUIPackets(BasicEntityShip entity, int button, int value) {
        this.entity = entity;
        this.worldID = entity.worldObj.provider.dimensionId;
        this.type = 0;
        this.button = button;
        this.value = value;
    }
	
	//type 1: shipyard gui click
	public C2SGUIPackets(BasicTileEntity tile, int button, int value, int value2) {
        this.tile = tile;
        this.worldID = tile.getWorldObj().provider.dimensionId;
        this.type = 1;
        this.button = button;
        this.value = value;
        this.value2 = value2;
    }
	
	//type 2: player gui click
	public C2SGUIPackets(EntityPlayer player, int button, int value, int value2) {
        this.player = player;
        this.worldID = player.worldObj.provider.dimensionId;
        this.type = 2;
        this.button = button;
        this.value = value;
        this.value2 = value2;
    }
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf) {	
		//get type and entityID
		this.type = buf.readByte();
	
		switch(type) {
		case 0:	//ship entity gui click
			{
				entityID = buf.readInt();
				worldID = buf.readInt();
				button = buf.readByte();
				value = buf.readByte();
				
				//get entity
				entity = (BasicEntityShip) EntityHelper.getEntityByID(entityID, worldID, false);
				
				//set value
				EntityHelper.setEntityByGUI(entity, (int)button, (int)value);
			}
			break;
		case 1: //shipyard gui click
			{
				this.worldID = buf.readInt();
				this.posX = buf.readInt();
				this.posY = buf.readInt();
				this.posZ = buf.readInt();
				this.button = buf.readByte();
				this.value = buf.readByte();
				this.value2 = buf.readByte();
				
				//get tile
				world = DimensionManager.getWorld(worldID);
				
				if(world != null) {
					this.tile = (BasicTileEntity) world.getTileEntity(posX, posY, posZ);
				}
				
				//set value
				EntityHelper.setTileEntityByGUI(tile, (int)button, (int)value, (int)value2);
			}
			break;
		case 2: //player gui click
			{
				this.entityID = buf.readInt();
				this.button = buf.readByte();
				this.value = buf.readByte();
				this.value2 = buf.readByte();
				
				//get player
				player = (EntityPlayer) EntityHelper.getEntityByID(entityID, worldID, false);
				
				//set value
//				EntityHelper.setEntityByGUI(entity, (int)button, (int)value);
			}
			break;
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case 0:	//ship entity gui click
			{
				buf.writeByte(0);
				buf.writeInt(this.entity.getEntityId());
				buf.writeInt(this.worldID);
				buf.writeByte(this.button);
				buf.writeByte(this.value);
			}
			break;
		case 1:	//shipyard gui click
			{
				buf.writeByte(1);
				buf.writeInt(this.worldID);
				buf.writeInt(this.tile.xCoord);
				buf.writeInt(this.tile.yCoord);
				buf.writeInt(this.tile.zCoord);
				buf.writeByte(this.button);
				buf.writeByte(this.value);
				buf.writeByte(this.value2);
			}
			break;
		case 2:	//ship entity gui click
			{
				buf.writeByte(2);
				buf.writeInt(this.entity.getEntityId());
				buf.writeInt(this.worldID);
				buf.writeByte(this.button);
				buf.writeByte(this.value);
				buf.writeByte(this.value2);
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


