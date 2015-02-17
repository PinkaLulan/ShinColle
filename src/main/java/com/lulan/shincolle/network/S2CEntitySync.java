package com.lulan.shincolle.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**SERVER TO CLIENT : ENTITY SYNC PACKET
 * 用於entity的資料同步
 * packet handler同樣建立在此class中
 * 
 * tut by diesieben07: http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
 */
public class S2CEntitySync implements IMessage {
	
	private static World clientWorld;
	private static BasicEntityShip sendEntity;
	private static BasicEntityShip recvEntity;
	private static int sendType;
	private static int recvType;
	private static int entityID;
	
	public S2CEntitySync() {}	//必須要有空參數constructor, forge才能使用此class
	
	//entity sync: 
	//type 0: all attribute
	//type 1: entity state only
	//type 2: entity flag only
	public S2CEntitySync(BasicEntityShip entity, int type) {
        this.sendEntity = entity;
        this.sendType = type;
        clientWorld = Minecraft.getMinecraft().theWorld;
    }
	
	//接收packet方法
	@Override
	public void fromBytes(ByteBuf buf) {
		//get type and entityID
		this.recvType = buf.readByte();
		this.entityID = buf.readInt();
		recvEntity = (BasicEntityShip) EntityHelper.getEntityByID(entityID, clientWorld);
	
		if(recvEntity != null) {
			switch(recvType) {
			case 0:	//sync all attr
				{
					recvEntity.setShipLevel(buf.readShort(), false);
					recvEntity.setKills(buf.readInt());
					recvEntity.setExpCurrent(buf.readInt());
					recvEntity.setNumAmmoLight(buf.readInt());
					recvEntity.setNumAmmoHeavy(buf.readInt());
					recvEntity.setNumGrudge(buf.readInt());

					recvEntity.setFinalState(AttrID.HP, buf.readFloat());
					recvEntity.setFinalState(AttrID.ATK, buf.readFloat());
					recvEntity.setFinalState(AttrID.DEF, buf.readFloat());
					recvEntity.setFinalState(AttrID.SPD, buf.readFloat());
					recvEntity.setFinalState(AttrID.MOV, buf.readFloat());
					recvEntity.setFinalState(AttrID.HIT, buf.readFloat());

					recvEntity.setEntityState(buf.readByte(), false);
					recvEntity.setEntityEmotion(buf.readByte(), false);
					recvEntity.setEntitySwimType(buf.readByte(), false);

					recvEntity.setBonusPoint(AttrID.HP, buf.readByte());
					recvEntity.setBonusPoint(AttrID.ATK, buf.readByte());
					recvEntity.setBonusPoint(AttrID.DEF, buf.readByte());
					recvEntity.setBonusPoint(AttrID.SPD, buf.readByte());
					recvEntity.setBonusPoint(AttrID.MOV, buf.readByte());
					recvEntity.setBonusPoint(AttrID.HIT, buf.readByte());

					recvEntity.setEntityFlag(AttrID.F_CanFloatUp, buf.readBoolean());
					recvEntity.setEntityFlag(AttrID.F_IsMarried, buf.readBoolean());
					recvEntity.setEntityFlag(AttrID.F_UseAmmoLight, buf.readBoolean());
					recvEntity.setEntityFlag(AttrID.F_UseAmmoHeavy, buf.readBoolean());
					recvEntity.setEntityFlag(AttrID.F_NoFuel, buf.readBoolean());
				}
				break;
			case 1: //entity state only
				{
					recvEntity.setEntityState(buf.readByte(), false);
					recvEntity.setEntityEmotion(buf.readByte(), false);
					recvEntity.setEntitySwimType(buf.readByte(), false);
				}
				break;
			case 2: //entity flag only
				{
					recvEntity.setEntityFlag(AttrID.F_CanFloatUp, buf.readBoolean());
					recvEntity.setEntityFlag(AttrID.F_IsMarried, buf.readBoolean());
					recvEntity.setEntityFlag(AttrID.F_UseAmmoLight, buf.readBoolean());
					recvEntity.setEntityFlag(AttrID.F_UseAmmoHeavy, buf.readBoolean());
					recvEntity.setEntityFlag(AttrID.F_NoFuel, buf.readBoolean());
				}
				break;
			}
		}
		else {
			LogHelper.info("DEBUG : packet handler: S2CEntitySync entity is null");
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.sendType) {
		case 0:	//sync all data
			{
				buf.writeByte(0);	//type 0
				buf.writeInt(this.sendEntity.getEntityId());	//entity id
				buf.writeShort(sendEntity.getShipLevel());
				buf.writeInt(this.sendEntity.getKills());
				buf.writeInt(this.sendEntity.getExpCurrent());
				buf.writeInt(this.sendEntity.getNumAmmoLight());
				buf.writeInt(this.sendEntity.getNumAmmoHeavy());
				buf.writeInt(this.sendEntity.getNumGrudge());

				buf.writeFloat(this.sendEntity.getFinalState(AttrID.HP));
				buf.writeFloat(this.sendEntity.getFinalState(AttrID.ATK));
				buf.writeFloat(this.sendEntity.getFinalState(AttrID.DEF));
				buf.writeFloat(this.sendEntity.getFinalState(AttrID.SPD));
				buf.writeFloat(this.sendEntity.getFinalState(AttrID.MOV));
				buf.writeFloat(this.sendEntity.getFinalState(AttrID.HIT));
				
				buf.writeByte(this.sendEntity.getEntityState(AttrID.State));
				buf.writeByte(this.sendEntity.getEntityState(AttrID.Emotion));
				buf.writeByte(this.sendEntity.getEntityState(AttrID.SwimType));

				buf.writeByte(this.sendEntity.getBonusPoint(AttrID.HP));
				buf.writeByte(this.sendEntity.getBonusPoint(AttrID.ATK));
				buf.writeByte(this.sendEntity.getBonusPoint(AttrID.DEF));
				buf.writeByte(this.sendEntity.getBonusPoint(AttrID.SPD));
				buf.writeByte(this.sendEntity.getBonusPoint(AttrID.MOV));
				buf.writeByte(this.sendEntity.getBonusPoint(AttrID.HIT));

				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_CanFloatUp));
				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_IsMarried));
				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_UseAmmoLight));
				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_UseAmmoHeavy));
				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_NoFuel));
			}
			break;
		case 1:	//entity state only
			{
				buf.writeByte(1);	//type 1
				buf.writeInt(this.sendEntity.getEntityId());	//entity id
				buf.writeByte(this.sendEntity.getEntityState(AttrID.State));
				buf.writeByte(this.sendEntity.getEntityState(AttrID.Emotion));
				buf.writeByte(this.sendEntity.getEntityState(AttrID.SwimType));
			}
			break;
		case 2:	//entity flag only
			{
				buf.writeByte(2);	//type 2
				buf.writeInt(this.sendEntity.getEntityId());	//entity id
				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_CanFloatUp));
				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_IsMarried));
				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_UseAmmoLight));
				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_UseAmmoHeavy));
				buf.writeBoolean(this.sendEntity.getEntityFlag(AttrID.F_NoFuel));	
			}
			break;
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CEntitySync, IMessage> {
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CEntitySync message, MessageContext ctx) {
//          System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
			LogHelper.info("DEBUG : recv Entity Sync packet : type "+recvType+" id "+entityID);
			return null;
		}
    }

}
