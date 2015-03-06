package com.lulan.shincolle.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**SERVER TO CLIENT : ENTITY SYNC PACKET
 * 用於entity的資料同步
 * packet handler同樣建立在此class中
 * 
 * tut by diesieben07: http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
 */
public class S2CEntitySync implements IMessage {
	
	private BasicEntityShip sendEntity;
	private BasicEntityShip recvEntity;
	private int sendType;
	private int recvType;
	private int entityID;
	
	public S2CEntitySync() {}	//必須要有空參數constructor, forge才能使用此class
	
	//entity sync: 
	//type 0: all attribute
	//type 1: entity state only
	//type 2: entity flag only
	public S2CEntitySync(BasicEntityShip entity, int type) {
        this.sendEntity = entity;
        this.sendType = type;
    }
	
	//接收packet方法 (CLIENT SIDE)
	@Override
	public void fromBytes(ByteBuf buf) {
		World clientWorld = ClientProxy.getClientWorld();
		//get type and entityID
		this.recvType = buf.readByte();
		this.entityID = buf.readInt();
		recvEntity = (BasicEntityShip) EntityHelper.getEntityByID(entityID, clientWorld);
	
		if(recvEntity != null) {
			switch(recvType) {
			case 0:	//sync all attr
				{
					recvEntity.setStateMinor(ID.N.ShipLevel, buf.readInt());
					recvEntity.setStateMinor(ID.N.Kills, buf.readInt());
					recvEntity.setStateMinor(ID.N.ExpCurrent, buf.readInt());
					recvEntity.setStateMinor(ID.N.NumAmmoLight, buf.readInt());
					recvEntity.setStateMinor(ID.N.NumAmmoHeavy, buf.readInt());
					recvEntity.setStateMinor(ID.N.NumGrudge, buf.readInt());
					recvEntity.setStateMinor(ID.N.NumAirLight, buf.readInt());
					recvEntity.setStateMinor(ID.N.NumAirHeavy, buf.readInt());

					recvEntity.setStateFinal(ID.HP, buf.readFloat());
					recvEntity.setStateFinal(ID.ATK, buf.readFloat());
					recvEntity.setStateFinal(ID.DEF, buf.readFloat());
					recvEntity.setStateFinal(ID.SPD, buf.readFloat());
					recvEntity.setStateFinal(ID.MOV, buf.readFloat());
					recvEntity.setStateFinal(ID.HIT, buf.readFloat());
					recvEntity.setStateFinal(ID.ATK_H, buf.readFloat());
					recvEntity.setStateFinal(ID.ATK_AL, buf.readFloat());
					recvEntity.setStateFinal(ID.ATK_AH, buf.readFloat());

					recvEntity.setStateEmotion(ID.S.State, buf.readByte(), false);
					recvEntity.setStateEmotion(ID.S.Emotion, buf.readByte(), false);
					recvEntity.setStateEmotion(ID.S.Emotion2, buf.readByte(), false);

					recvEntity.setBonusPoint(ID.HP, buf.readByte());
					recvEntity.setBonusPoint(ID.ATK, buf.readByte());
					recvEntity.setBonusPoint(ID.DEF, buf.readByte());
					recvEntity.setBonusPoint(ID.SPD, buf.readByte());
					recvEntity.setBonusPoint(ID.MOV, buf.readByte());
					recvEntity.setBonusPoint(ID.HIT, buf.readByte());

					recvEntity.setStateFlag(ID.F.CanFloatUp, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.IsMarried, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.NoFuel, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseMelee, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseAmmoLight, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseAmmoHeavy, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseAirLight, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseAirHeavy, buf.readBoolean());
					
					recvEntity.setEffectEquip(ID.EF_CRI, buf.readFloat());
					recvEntity.setEffectEquip(ID.EF_DHIT, buf.readFloat());
					recvEntity.setEffectEquip(ID.EF_THIT, buf.readFloat());
					recvEntity.setEffectEquip(ID.EF_MISS, buf.readFloat());
				}
				break;
			case 1: //entity state only
				{
					recvEntity.setStateEmotion(ID.S.State, buf.readByte(), false);
					recvEntity.setStateEmotion(ID.S.Emotion, buf.readByte(), false);
					recvEntity.setStateEmotion(ID.S.Emotion2, buf.readByte(), false);
				}
				break;
			case 2: //entity flag only
				{
					recvEntity.setStateFlag(ID.F.CanFloatUp, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.IsMarried, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.NoFuel, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseMelee, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseAmmoLight, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseAmmoHeavy, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseAirLight, buf.readBoolean());
					recvEntity.setStateFlag(ID.F.UseAirHeavy, buf.readBoolean());
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
				buf.writeInt(this.sendEntity.getStateMinor(ID.N.ShipLevel));
				buf.writeInt(this.sendEntity.getStateMinor(ID.N.Kills));
				buf.writeInt(this.sendEntity.getStateMinor(ID.N.ExpCurrent));
				buf.writeInt(this.sendEntity.getStateMinor(ID.N.NumAmmoLight));
				buf.writeInt(this.sendEntity.getStateMinor(ID.N.NumAmmoHeavy));
				buf.writeInt(this.sendEntity.getStateMinor(ID.N.NumGrudge));
				buf.writeInt(this.sendEntity.getStateMinor(ID.N.NumAirLight));
				buf.writeInt(this.sendEntity.getStateMinor(ID.N.NumAirHeavy));

				buf.writeFloat(this.sendEntity.getStateFinal(ID.HP));
				buf.writeFloat(this.sendEntity.getStateFinal(ID.ATK));
				buf.writeFloat(this.sendEntity.getStateFinal(ID.DEF));
				buf.writeFloat(this.sendEntity.getStateFinal(ID.SPD));
				buf.writeFloat(this.sendEntity.getStateFinal(ID.MOV));
				buf.writeFloat(this.sendEntity.getStateFinal(ID.HIT));
				buf.writeFloat(this.sendEntity.getStateFinal(ID.ATK_H));
				buf.writeFloat(this.sendEntity.getStateFinal(ID.ATK_AL));
				buf.writeFloat(this.sendEntity.getStateFinal(ID.ATK_AH));
				
				buf.writeByte(this.sendEntity.getStateEmotion(ID.S.State));
				buf.writeByte(this.sendEntity.getStateEmotion(ID.S.Emotion));
				buf.writeByte(this.sendEntity.getStateEmotion(ID.S.Emotion2));

				buf.writeByte(this.sendEntity.getBonusPoint(ID.HP));
				buf.writeByte(this.sendEntity.getBonusPoint(ID.ATK));
				buf.writeByte(this.sendEntity.getBonusPoint(ID.DEF));
				buf.writeByte(this.sendEntity.getBonusPoint(ID.SPD));
				buf.writeByte(this.sendEntity.getBonusPoint(ID.MOV));
				buf.writeByte(this.sendEntity.getBonusPoint(ID.HIT));

				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.CanFloatUp));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.IsMarried));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.NoFuel));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseMelee));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseAmmoLight));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseAmmoHeavy));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseAirLight));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseAirHeavy));
				
				buf.writeFloat(this.sendEntity.getEffectEquip(ID.EF_CRI));
				buf.writeFloat(this.sendEntity.getEffectEquip(ID.EF_DHIT));
				buf.writeFloat(this.sendEntity.getEffectEquip(ID.EF_THIT));
				buf.writeFloat(this.sendEntity.getEffectEquip(ID.EF_MISS));
			}
			break;
		case 1:	//entity state only
			{
				buf.writeByte(1);	//type 1
				buf.writeInt(this.sendEntity.getEntityId());	//entity id
				buf.writeByte(this.sendEntity.getStateEmotion(ID.S.State));
				buf.writeByte(this.sendEntity.getStateEmotion(ID.S.Emotion));
				buf.writeByte(this.sendEntity.getStateEmotion(ID.S.Emotion2));
			}
			break;
		case 2:	//entity flag only
			{
				buf.writeByte(2);	//type 2
				buf.writeInt(this.sendEntity.getEntityId());	//entity id
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.CanFloatUp));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.IsMarried));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.NoFuel));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseMelee));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseAmmoLight));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseAmmoHeavy));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseAirLight));
				buf.writeBoolean(this.sendEntity.getStateFlag(ID.F.UseAirHeavy));	
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
//			LogHelper.info("DEBUG : recv Entity Sync packet : type "+recvType+" id "+entityID);
			return null;
		}
    }

}
