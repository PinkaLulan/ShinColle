package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.EntityMountSeat;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;

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
	
	private BasicEntityShip entity;
	private EntityLiving entity2;
	private EntityMountSeat entitySeat;
	private IShipEmotion entity2e;
	private int entityID;
	private int type;

	
	public S2CEntitySync() {}	//必須要有空參數constructor, forge才能使用此class
	
	//entity sync: 
	//type 0: all attribute
	//type 1: entity emotion only
	//type 2: entity flag only
	//type 3: entity minor only
	public S2CEntitySync(BasicEntityShip entity, int type) {
        this.entity = entity;
        this.type = type;
    }
	
	//for mount entity sync
	//type 4: all emotion
	//type 5: player mount packet
	public S2CEntitySync(IShipEmotion entity, int type) {
        this.entity2 = (EntityLiving) entity;
        this.entity2e = entity;
        this.type = type;
    }
	
	//for mount seat sync
	//type 6: player mount packet, set player or clear player on the seat
	public S2CEntitySync(EntityMountSeat entity, int type) {
        this.entitySeat= entity;
        this.type = type;
    }
	
	//接收packet方法 (CLIENT SIDE)
	@Override
	public void fromBytes(ByteBuf buf) {
		//get type and entityID
		this.type = buf.readByte();
		
		if(this.type < 6) {
			this.entityID = buf.readInt();
			this.entity2 = (EntityLiving) EntityHelper.getEntityByID(entityID, 0, true);
			
			if(entity2 instanceof BasicEntityShip) {
				this.entity = (BasicEntityShip) this.entity2;
			}
			else {
				this.entity2e = (IShipEmotion) this.entity2;
			}
		}

		if(entity2 != null) {
			switch(type) {
			case 0:	//sync all attr
				{
					entity.setStateMinor(ID.N.ShipLevel, buf.readInt());
					entity.setStateMinor(ID.N.Kills, buf.readInt());
					entity.setStateMinor(ID.N.ExpCurrent, buf.readInt());
					entity.setStateMinor(ID.N.NumAmmoLight, buf.readInt());
					entity.setStateMinor(ID.N.NumAmmoHeavy, buf.readInt());
					entity.setStateMinor(ID.N.NumGrudge, buf.readInt());
					entity.setStateMinor(ID.N.NumAirLight, buf.readInt());
					entity.setStateMinor(ID.N.NumAirHeavy, buf.readInt());
					entity.setStateMinor(ID.N.FollowMin, buf.readInt());
					entity.setStateMinor(ID.N.FollowMax, buf.readInt());
					entity.setStateMinor(ID.N.FleeHP, buf.readInt());
					entity.setStateMinor(ID.N.TargetAI, buf.readInt());
					
					entity.setStateFinal(ID.HP, buf.readFloat());
					entity.setStateFinal(ID.ATK, buf.readFloat());
					entity.setStateFinal(ID.DEF, buf.readFloat());
					entity.setStateFinal(ID.SPD, buf.readFloat());
					entity.setStateFinal(ID.MOV, buf.readFloat());
					entity.setStateFinal(ID.HIT, buf.readFloat());
					entity.setStateFinal(ID.ATK_H, buf.readFloat());
					entity.setStateFinal(ID.ATK_AL, buf.readFloat());
					entity.setStateFinal(ID.ATK_AH, buf.readFloat());
					
					entity.setStateEmotion(ID.S.State, buf.readByte(), false);
					entity.setStateEmotion(ID.S.State2, buf.readByte(), false);
					entity.setStateEmotion(ID.S.Emotion, buf.readByte(), false);
					entity.setStateEmotion(ID.S.Emotion2, buf.readByte(), false);
					entity.setStateEmotion(ID.S.HPState, buf.readByte(), false);
					entity.setStateEmotion(ID.S.Phase, buf.readByte(), false);

					entity.setBonusPoint(ID.HP, buf.readByte());
					entity.setBonusPoint(ID.ATK, buf.readByte());
					entity.setBonusPoint(ID.DEF, buf.readByte());
					entity.setBonusPoint(ID.SPD, buf.readByte());
					entity.setBonusPoint(ID.MOV, buf.readByte());
					entity.setBonusPoint(ID.HIT, buf.readByte());

					entity.setStateFlag(ID.F.CanFloatUp, buf.readBoolean());
					entity.setStateFlag(ID.F.IsMarried, buf.readBoolean());
					entity.setStateFlag(ID.F.NoFuel, buf.readBoolean());
					entity.setStateFlag(ID.F.UseMelee, buf.readBoolean());
					entity.setStateFlag(ID.F.UseAmmoLight, buf.readBoolean());
					entity.setStateFlag(ID.F.UseAmmoHeavy, buf.readBoolean());
					entity.setStateFlag(ID.F.UseAirLight, buf.readBoolean());
					entity.setStateFlag(ID.F.UseAirHeavy, buf.readBoolean());
					entity.setStateFlag(ID.F.UseRingEffect, buf.readBoolean());
					
					entity.setEffectEquip(ID.EF_CRI, buf.readFloat());
					entity.setEffectEquip(ID.EF_DHIT, buf.readFloat());
					entity.setEffectEquip(ID.EF_THIT, buf.readFloat());
					entity.setEffectEquip(ID.EF_MISS, buf.readFloat());
				}
				break;
			case 1: //entity emotion only
				{
					entity.setStateEmotion(ID.S.State, buf.readByte(), false);
					entity.setStateEmotion(ID.S.State2, buf.readByte(), false);
					entity.setStateEmotion(ID.S.Emotion, buf.readByte(), false);
					entity.setStateEmotion(ID.S.Emotion2, buf.readByte(), false);
					entity.setStateEmotion(ID.S.HPState, buf.readByte(), false);
					entity.setStateEmotion(ID.S.Phase, buf.readByte(), false);
				}
				break;
			case 2: //entity flag only
				{
					entity.setStateFlag(ID.F.CanFloatUp, buf.readBoolean());
					entity.setStateFlag(ID.F.IsMarried, buf.readBoolean());
					entity.setStateFlag(ID.F.NoFuel, buf.readBoolean());
					entity.setStateFlag(ID.F.UseMelee, buf.readBoolean());
					entity.setStateFlag(ID.F.UseAmmoLight, buf.readBoolean());
					entity.setStateFlag(ID.F.UseAmmoHeavy, buf.readBoolean());
					entity.setStateFlag(ID.F.UseAirLight, buf.readBoolean());
					entity.setStateFlag(ID.F.UseAirHeavy, buf.readBoolean());
					entity.setStateFlag(ID.F.UseRingEffect, buf.readBoolean());
				}
				break;
			case 3: //entity minor only
				{
					entity.setStateMinor(ID.N.ShipLevel, buf.readInt());
					entity.setStateMinor(ID.N.Kills, buf.readInt());
					entity.setStateMinor(ID.N.ExpCurrent, buf.readInt());
					entity.setStateMinor(ID.N.NumAmmoLight, buf.readInt());
					entity.setStateMinor(ID.N.NumAmmoHeavy, buf.readInt());
					entity.setStateMinor(ID.N.NumGrudge, buf.readInt());
					entity.setStateMinor(ID.N.NumAirLight, buf.readInt());
					entity.setStateMinor(ID.N.NumAirHeavy, buf.readInt());
					entity.setStateMinor(ID.N.FollowMin, buf.readInt());
					entity.setStateMinor(ID.N.FollowMax, buf.readInt());
					entity.setStateMinor(ID.N.FleeHP, buf.readInt());
					entity.setStateMinor(ID.N.TargetAI, buf.readInt());
				}
				break;
			case 4: //IShipEmotion sync emtion
				{
					entity2e.setStateEmotion(ID.S.State, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.State2, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.Emotion, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.Emotion2, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.HPState, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.Phase, buf.readByte(), false);
				}
				break;
			case 5: //IShipEmotion player mount sync
				{
					int playerId = buf.readInt();
					int seatId = buf.readInt();
					
					EntityPlayer player = (EntityPlayer) EntityHelper.getEntityByID(playerId, 0, true);
					EntityMountSeat seat = (EntityMountSeat) EntityHelper.getEntityByID(seatId, 0, true);
					
					LogHelper.info("DEBUG : player mount packet: "+player+" "+seat);
					if(player != null && seat != null) {
						player.mountEntity(seat);
						player.ridingEntity = seat;
						seat.riddenByEntity = player;
						seat.host = (BasicEntityMount) entity2;
						seat.host.seat2 = seat;
						seat.host.setStateEmotion(ID.S.Emotion, 1, false);
					}	
				}
				break;
			case 6:	//seat sync
				{
					int seatId = buf.readInt();
					int playerId = buf.readInt();
					int hostId = buf.readInt();
					
					EntityMountSeat seat = (EntityMountSeat) EntityHelper.getEntityByID(seatId, 0, true);
					
					if(playerId <= 0) {
						if(seat != null) {
							seat.setRiderNull();
						}
					}
					else {
						EntityPlayer player = (EntityPlayer) EntityHelper.getEntityByID(playerId, 0, true);
						BasicEntityMount mount = (BasicEntityMount) EntityHelper.getEntityByID(hostId, 0, true);
						
						if(seat != null && mount != null) {
							seat.host = mount;
							seat.riddenByEntity = player;
							seat.host.seat2 = seat;
							seat.host.riddenByEntity2 = player;
							seat.host.setStateEmotion(ID.S.Emotion, 1, false);
						}
					}
				}
				break;
			}
		}
		else {
			buf.clear();
			LogHelper.info("DEBUG : packet handler: S2CEntitySync entity is null "+type);
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case 0:	//sync all data
			{
				buf.writeByte(0);	//type 0
				buf.writeInt(this.entity.getEntityId());
				buf.writeInt(this.entity.getStateMinor(ID.N.ShipLevel));
				buf.writeInt(this.entity.getStateMinor(ID.N.Kills));
				buf.writeInt(this.entity.getStateMinor(ID.N.ExpCurrent));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumAmmoLight));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumAmmoHeavy));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumGrudge));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumAirLight));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumAirHeavy));
				buf.writeInt(this.entity.getStateMinor(ID.N.FollowMin));
				buf.writeInt(this.entity.getStateMinor(ID.N.FollowMax));
				buf.writeInt(this.entity.getStateMinor(ID.N.FleeHP));
				buf.writeInt(this.entity.getStateMinor(ID.N.TargetAI));

				buf.writeFloat(this.entity.getStateFinal(ID.HP));
				buf.writeFloat(this.entity.getStateFinal(ID.ATK));
				buf.writeFloat(this.entity.getStateFinal(ID.DEF));
				buf.writeFloat(this.entity.getStateFinal(ID.SPD));
				buf.writeFloat(this.entity.getStateFinal(ID.MOV));
				buf.writeFloat(this.entity.getStateFinal(ID.HIT));
				buf.writeFloat(this.entity.getStateFinal(ID.ATK_H));
				buf.writeFloat(this.entity.getStateFinal(ID.ATK_AL));
				buf.writeFloat(this.entity.getStateFinal(ID.ATK_AH));
				
				buf.writeByte(this.entity.getStateEmotion(ID.S.State));
				buf.writeByte(this.entity.getStateEmotion(ID.S.State2));
				buf.writeByte(this.entity.getStateEmotion(ID.S.Emotion));
				buf.writeByte(this.entity.getStateEmotion(ID.S.Emotion2));
				buf.writeByte(this.entity.getStateEmotion(ID.S.HPState));
				buf.writeByte(this.entity.getStateEmotion(ID.S.Phase));

				buf.writeByte(this.entity.getBonusPoint(ID.HP));
				buf.writeByte(this.entity.getBonusPoint(ID.ATK));
				buf.writeByte(this.entity.getBonusPoint(ID.DEF));
				buf.writeByte(this.entity.getBonusPoint(ID.SPD));
				buf.writeByte(this.entity.getBonusPoint(ID.MOV));
				buf.writeByte(this.entity.getBonusPoint(ID.HIT));

				buf.writeBoolean(this.entity.getStateFlag(ID.F.CanFloatUp));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.IsMarried));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.NoFuel));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseMelee));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseAmmoLight));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseAmmoHeavy));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseAirLight));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseAirHeavy));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseRingEffect));
				
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_CRI));
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_DHIT));
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_THIT));
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_MISS));
			}
			break;
		case 1:	//entity state only
			{
				buf.writeByte(1);	//type 1
				buf.writeInt(this.entity.getEntityId());
				buf.writeByte(this.entity.getStateEmotion(ID.S.State));
				buf.writeByte(this.entity.getStateEmotion(ID.S.State2));
				buf.writeByte(this.entity.getStateEmotion(ID.S.Emotion));
				buf.writeByte(this.entity.getStateEmotion(ID.S.Emotion2));
				buf.writeByte(this.entity.getStateEmotion(ID.S.HPState));
				buf.writeByte(this.entity.getStateEmotion(ID.S.Phase));
			}
			break;
		case 2:	//entity flag only
			{
				buf.writeByte(2);	//type 2
				buf.writeInt(this.entity.getEntityId());
				buf.writeBoolean(this.entity.getStateFlag(ID.F.CanFloatUp));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.IsMarried));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.NoFuel));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseMelee));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseAmmoLight));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseAmmoHeavy));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseAirLight));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseAirHeavy));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.UseRingEffect));
			}
			break;
		case 3:	//sync minor only
			{
				buf.writeByte(3);	//type 3
				buf.writeInt(this.entity.getEntityId());
				buf.writeInt(this.entity.getStateMinor(ID.N.ShipLevel));
				buf.writeInt(this.entity.getStateMinor(ID.N.Kills));
				buf.writeInt(this.entity.getStateMinor(ID.N.ExpCurrent));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumAmmoLight));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumAmmoHeavy));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumGrudge));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumAirLight));
				buf.writeInt(this.entity.getStateMinor(ID.N.NumAirHeavy));
				buf.writeInt(this.entity.getStateMinor(ID.N.FollowMin));
				buf.writeInt(this.entity.getStateMinor(ID.N.FollowMax));
				buf.writeInt(this.entity.getStateMinor(ID.N.FleeHP));
				buf.writeInt(this.entity.getStateMinor(ID.N.TargetAI));
			}
			break;
		case 4:	//IShipEmotion emotion only
			{
				buf.writeByte(4);	//type 1
				buf.writeInt(this.entity2.getEntityId());
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.State));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.State2));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.Emotion));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.Emotion2));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.HPState));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.Phase));
			}
			break;
		case 5:	//IShipEmotion player mount packet
			{
				buf.writeByte(5);	//type 1
				buf.writeInt(this.entity2.getEntityId());
				buf.writeInt(((BasicEntityMount)this.entity2e).riddenByEntity2.getEntityId());
				buf.writeInt(((BasicEntityMount)this.entity2e).seat2.getEntityId());
			}
			break;
		case 6:	//IShipEmotion player mount packet
			{
				buf.writeByte(6);	//type 1
				
				if(entitySeat.riddenByEntity == null || entitySeat.host.seat2 == null) {
					buf.writeInt(this.entitySeat.getEntityId());
					buf.writeInt(-1);
					buf.writeInt(-1);
				}
				else {
					buf.writeInt(this.entitySeat.getEntityId());
					buf.writeInt(this.entitySeat.riddenByEntity.getEntityId());
					buf.writeInt(this.entitySeat.host.getEntityId());
				}
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
