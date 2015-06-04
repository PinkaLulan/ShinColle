package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
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
	private IShipEmotion entity2e;
	private Entity entity3;
	private EntityMountSeat entity3s;
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
	//type 7: mounts sync
	public S2CEntitySync(IShipEmotion entity, int type) {
        this.entity2 = (EntityLiving) entity;
        this.entity2e = entity;
        this.type = type;
    }
	
	//for mount seat sync
	//type 6: player mount packet, set player or clear player on the seat
	public S2CEntitySync(EntityMountSeat entity, int type) {
        this.entity3s = entity;
        this.type = type;
    }
	
	//接收packet方法 (CLIENT SIDE)
	@Override
	public void fromBytes(ByteBuf buf) {
		boolean getSyncTarget = false;
		//get type and entityID
		this.type = buf.readByte();
		this.entityID = buf.readInt();
		
		switch(this.type) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 7:
			this.entity2 = (EntityLiving) EntityHelper.getEntityByID(entityID, 0, true);
			//確認有抓到要sync的entity
			if(entity2 != null) {
				getSyncTarget = true;
				
				if(entity2 instanceof BasicEntityShip) {
					this.entity = (BasicEntityShip) this.entity2;
				}
				else {
					this.entity2e = (IShipEmotion) this.entity2;
				}
			}
			break;
		case 6:	//packet id 6: for mount seat2 sync
			this.entity3 = EntityHelper.getEntityByID(entityID, 0, true);
			
			if(entity3 instanceof EntityMountSeat) {
				entity3s = (EntityMountSeat) entity3;
				getSyncTarget = true;
			}
			break;
		}

		if(getSyncTarget) {
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
					entity.setStateMinor(ID.N.GuardX, buf.readInt());
					entity.setStateMinor(ID.N.GuardY, buf.readInt());
					entity.setStateMinor(ID.N.GuardZ, buf.readInt());
					entity.setStateMinor(ID.N.GuardDim, buf.readInt());
					entity.setStateMinor(ID.N.GuardID, buf.readInt());
					entity.setStateMinor(ID.N.OwnerID, buf.readInt());
					
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
					entity.setStateMinor(ID.N.GuardX, buf.readInt());
					entity.setStateMinor(ID.N.GuardY, buf.readInt());
					entity.setStateMinor(ID.N.GuardZ, buf.readInt());
					entity.setStateMinor(ID.N.GuardDim, buf.readInt());
					entity.setStateMinor(ID.N.GuardID, buf.readInt());
					entity.setStateMinor(ID.N.OwnerID, buf.readInt());
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
			case 5: //player mount sync (only send when player right click on mount)
				{
					int playerId = buf.readInt();
					int seatId = buf.readInt();
					
					EntityPlayer player = (EntityPlayer) EntityHelper.getEntityByID(playerId, 0, true);
					this.entity3s = (EntityMountSeat) EntityHelper.getEntityByID(seatId, 0, true);
					
					LogHelper.info("DEBUG : player mount sync packet: "+player+" "+entity3s);
					if(player != null && entity3s != null) {
						player.mountEntity(entity3s);
						player.ridingEntity = entity3s;
						entity3s.riddenByEntity = player;
						entity3s.host = (BasicEntityMount) entity2;
						entity3s.host.seat2 = entity3s;
						entity3s.host.setStateEmotion(ID.S.Emotion, 1, false);
					}	
				}
				break;
			case 6:	//seat2 sync
				{
					int playerId = buf.readInt();
					int hostId = buf.readInt();
							
					//dismount packet
					if(playerId < 0) {	//id設為-1表示為dismount packet
						entity3s.setRiderNull();
					}
					//mount sync packet
					else {
						EntityPlayer player = (EntityPlayer) EntityHelper.getEntityByID(playerId, 0, true);
						BasicEntityMount mount = (BasicEntityMount) EntityHelper.getEntityByID(hostId, 0, true);

						//sync for mount
						if(mount != null) {
							LogHelper.info("DEBUG : sync seat2 packet: get seat2");
							entity3s.host = mount;
							entity3s.riddenByEntity = player;
							entity3s.host.seat2 = entity3s;
							entity3s.host.riddenByEntity2 = player;
							entity3s.host.setStateEmotion(ID.S.Emotion, 1, false);
						}
						//host not found, dismount
						else {
							LogHelper.info("DEBUG : sync seat2 packet: seat2 null");
							entity3s.setRiderNull();
						}
					}
				}
				break;
			case 7:
				{
					int hostid = buf.readInt();
					int rider2id = buf.readInt();
					int seat2id = buf.readInt();
					
					BasicEntityMount mount = (BasicEntityMount) entity2;
					Entity getEnt = null;

					if(mount != null) {
						//set host
						if(hostid > 0) {
							mount.host = (BasicEntityShip) EntityHelper.getEntityByID(hostid, 0, true);
						}
						else {
							mount.host = null;
						}
						//set rider2
						if(rider2id > 0) {
							mount.riddenByEntity2 = (EntityLivingBase) EntityHelper.getEntityByID(rider2id, 0, true);
						}
						else {
							mount.riddenByEntity2 = null;
						}
						//set seat2
						if(seat2id > 0) {
							mount.seat2 = (EntityMountSeat) EntityHelper.getEntityByID(seat2id, 0, true);
						}
						else {
							mount.seat2 = null;
						}
					}
				}
				break;
			}
		}
		else {
			buf.clear();
			LogHelper.info("DEBUG : packet handler: S2CEntitySync: entity is null "+type);
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
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardX));
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardY));
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardZ));
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardDim));
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardID));
				buf.writeInt(this.entity.getStateMinor(ID.N.OwnerID));
				
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
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardX));
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardY));
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardZ));
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardDim));
				buf.writeInt(this.entity.getStateMinor(ID.N.GuardID));
				buf.writeInt(this.entity.getStateMinor(ID.N.OwnerID));
			}
			break;
		case 4:	//IShipEmotion emotion only
			{
				buf.writeByte(4);	//type 4
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
				buf.writeByte(5);	//type 5
				buf.writeInt(this.entity2.getEntityId());
				buf.writeInt(((BasicEntityMount)this.entity2e).riddenByEntity2.getEntityId());
				buf.writeInt(((BasicEntityMount)this.entity2e).seat2.getEntityId());
			}
			break;
		case 6:	//IShipEmotion player mount packet
			{
				buf.writeByte(6);	//type 6
				
				//dismount packet
				if(entity3s.riddenByEntity == null || entity3s.host.seat2 == null) {
					buf.writeInt(this.entity3s.getEntityId());
					buf.writeInt(-1);
					buf.writeInt(-1);
				}
				//mount sync packet
				else {
					buf.writeInt(this.entity3s.getEntityId());
					buf.writeInt(this.entity3s.riddenByEntity.getEntityId());
					buf.writeInt(this.entity3s.host.getEntityId());
				}
			}
			break;
		case 7:	//mounts sync
			{
				if(entity2 instanceof BasicEntityMount) {
					BasicEntityMount mount = (BasicEntityMount) entity2;
					
					buf.writeByte(7);	//type 7
					buf.writeInt(mount.getEntityId());
					//get host id
					if(mount.host != null) {
						buf.writeInt(mount.host.getEntityId());
					}
					else {
						buf.writeInt(-1);	//cause client get entity = null
					}
					//get rider2 id
					if(mount.riddenByEntity2 != null) {
						buf.writeInt(mount.riddenByEntity2.getEntityId());
					}
					else {
						buf.writeInt(-1);	//cause client get entity = null
					}
					//get seat2 id
					if(mount.seat2 != null) {
						buf.writeInt(mount.seat2.getEntityId());
					}
					else {
						buf.writeInt(-1);	//cause client get entity = null
					}
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
