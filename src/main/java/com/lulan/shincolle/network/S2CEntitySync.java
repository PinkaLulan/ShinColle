package com.lulan.shincolle.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.mounts.EntityMountSeat;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
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
	private int entityID, value, type;
	
	//packet id
	public static final class PID {
		public static final byte SyncShip_All = 0;
		public static final byte SyncShip_Emo = 1;
		public static final byte SyncShip_Flag = 2;
		public static final byte SyncShip_Minor = 3;
		public static final byte SyncEntity_Emo = 4;
		public static final byte SyncMount_ByPlayer = 5;
		public static final byte SyncSeat = 6;
		public static final byte SyncMount_ByMount = 7;
		public static final byte SyncProjectile = 8;
		public static final byte SyncEntity_PosRot = 9;
		public static final byte SyncEntity_Rot = 10;
		public static final byte SyncShip_Formation = 11;
		public static final byte SyncShip_Unbuff = 12;
		public static final byte SyncEntity_Motion = 13;
	}

	
	public S2CEntitySync() {}	//必須要有空參數constructor, forge才能使用此class
	
	/**entity sync: 
	 * type 0: all attribute
	 * type 1: entity emotion only
	 * type 2: entity flag only
	 * type 3: entity minor only
	 * type 11:ship formation data
	 * type 12:ship unbuff data
	 */
	public S2CEntitySync(BasicEntityShip entity, int type) {
        this.entity = entity;
        this.type = type;
    }
	
	/**for mount entity sync
	//type 4: all emotion
	//type 5: player mount packet
	//type 7: mounts sync
	 */
	public S2CEntitySync(IShipEmotion entity, int type) {
        this.entity2 = (EntityLiving) entity;
        this.entity2e = entity;
        this.type = type;
    }
	
	/**for mount seat sync
	//type 6: player mount packet, set player or clear player on the seat
	 */
	public S2CEntitySync(EntityMountSeat entity, int type) {
        this.entity3s = entity;
        this.type = type;
    }
	
	/**for mount seat sync
	 * type 8:  projectile type sync
	 * type 9:  entity pos sync (for teleport)
	 * type 10: entity rot sync (for looking update)
	 * type 13: entity motion sync (for knockback)
	 */
	public S2CEntitySync(Entity entity, int value, int type) {
        this.entity3 = entity;
        this.value = value;
        this.type = type;
    }

	//接收packet方法 (CLIENT SIDE)
	@Override
	public void fromBytes(ByteBuf buf) {
		boolean getSyncTarget = false;
		
		//get type and entityID
		this.type = buf.readByte();
		this.entityID = buf.readInt();
		
		//get target entity
		switch(this.type) {
		case PID.SyncShip_All:
		case PID.SyncShip_Emo:
		case PID.SyncShip_Flag:
		case PID.SyncShip_Formation:
		case PID.SyncShip_Minor:
		case PID.SyncShip_Unbuff:
		case PID.SyncEntity_Emo:
		case PID.SyncMount_ByPlayer:
		case PID.SyncMount_ByMount:
//			LogHelper.info("DEBUG : sync entity: eid "+this.entityID+" type "+this.type);
//			this.entity2 = (EntityLiving) EntityHelper.getEntityByID(entityID, 0, true);
			Entity ent = EntityHelper.getEntityByID(entityID, 0, true);
			
			//確認有抓到要sync的entity
			if(ent instanceof EntityLiving) {
				this.entity2 = (EntityLiving) ent;
				getSyncTarget = true;
				
				if(entity2 instanceof BasicEntityShip) {
					this.entity = (BasicEntityShip) this.entity2;
				}
				else if(entity2 instanceof IShipEmotion) {
					this.entity2e = (IShipEmotion) this.entity2;
				}
				else {
					getSyncTarget = false;
				}
			}
			break;
		case PID.SyncSeat:	//packet id 6: for mount seat2 sync
			this.entity3 = EntityHelper.getEntityByID(entityID, 0, true);
			
			if(entity3 instanceof EntityMountSeat) {
				entity3s = (EntityMountSeat) entity3;
				getSyncTarget = true;
			}
			break;
		case PID.SyncProjectile:	//missile sync
			this.entity3 = EntityHelper.getEntityByID(entityID, 0, true);
			
			if(entity3 != null) {
				getSyncTarget = true;
			}
			break;
		case PID.SyncEntity_PosRot: //entity position, rotation, motion sync
		case PID.SyncEntity_Rot:
		case PID.SyncEntity_Motion:
			this.entity3 = EntityHelper.getEntityByID(entityID, 0, true);
			if(entity3 != null) {
				getSyncTarget = true;
			}
			break;
		}

		if(getSyncTarget) {
			switch(type) {
			case PID.SyncShip_All:	//sync all attr
				{
					entity.setStateMinor(ID.M.ShipLevel, buf.readInt());
					entity.setStateMinor(ID.M.Kills, buf.readInt());
					entity.setStateMinor(ID.M.ExpCurrent, buf.readInt());
					entity.setStateMinor(ID.M.NumAmmoLight, buf.readInt());
					entity.setStateMinor(ID.M.NumAmmoHeavy, buf.readInt());
					entity.setStateMinor(ID.M.NumGrudge, buf.readInt());
					entity.setStateMinor(ID.M.NumAirLight, buf.readInt());
					entity.setStateMinor(ID.M.NumAirHeavy, buf.readInt());
					entity.setStateMinor(ID.M.FollowMin, buf.readInt());
					entity.setStateMinor(ID.M.FollowMax, buf.readInt());
					entity.setStateMinor(ID.M.FleeHP, buf.readInt());
					entity.setStateMinor(ID.M.GuardX, buf.readInt());
					entity.setStateMinor(ID.M.GuardY, buf.readInt());
					entity.setStateMinor(ID.M.GuardZ, buf.readInt());
					entity.setStateMinor(ID.M.GuardDim, buf.readInt());
					entity.setStateMinor(ID.M.GuardID, buf.readInt());
					entity.setStateMinor(ID.M.GuardType, buf.readInt());
					entity.setStateMinor(ID.M.PlayerUID, buf.readInt());
					entity.setStateMinor(ID.M.ShipUID, buf.readInt());
					entity.setStateMinor(ID.M.PlayerEID, buf.readInt());
					entity.setStateMinor(ID.M.FormatType, buf.readInt());
					entity.setStateMinor(ID.M.FormatPos, buf.readInt());
					
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
					entity.setStateFlag(ID.F.OnSightChase, buf.readBoolean());
					entity.setStateFlag(ID.F.PVPFirst, buf.readBoolean());
					entity.setStateFlag(ID.F.AntiAir, buf.readBoolean());
					entity.setStateFlag(ID.F.AntiSS, buf.readBoolean());
					entity.setStateFlag(ID.F.PassiveAI, buf.readBoolean());
					entity.setStateFlag(ID.F.TimeKeeper, buf.readBoolean());
					
					entity.setEffectEquip(ID.EF_CRI, buf.readFloat());
					entity.setEffectEquip(ID.EF_DHIT, buf.readFloat());
					entity.setEffectEquip(ID.EF_THIT, buf.readFloat());
					entity.setEffectEquip(ID.EF_MISS, buf.readFloat());
					entity.setEffectEquip(ID.EF_AA, buf.readFloat());
					entity.setEffectEquip(ID.EF_ASM, buf.readFloat());
					entity.setEffectEquip(ID.EF_DODGE, buf.readFloat());
					
					entity.setEffectFormation(ID.Formation.ATK_L, buf.readFloat());
					entity.setEffectFormation(ID.Formation.ATK_H, buf.readFloat());
					entity.setEffectFormation(ID.Formation.ATK_AL, buf.readFloat());
					entity.setEffectFormation(ID.Formation.ATK_AH, buf.readFloat());
					entity.setEffectFormation(ID.Formation.DEF, buf.readFloat());
					entity.setEffectFormation(ID.Formation.MOV, buf.readFloat());
					entity.setEffectFormation(ID.Formation.MISS, buf.readFloat());
					entity.setEffectFormation(ID.Formation.DODGE, buf.readFloat());
					entity.setEffectFormation(ID.Formation.CRI, buf.readFloat());
					entity.setEffectFormation(ID.Formation.DHIT, buf.readFloat());
					entity.setEffectFormation(ID.Formation.THIT, buf.readFloat());
					entity.setEffectFormation(ID.Formation.AA, buf.readFloat());
					entity.setEffectFormation(ID.Formation.ASM, buf.readFloat());
					
					entity.setEffectFormationFixed(ID.FormationFixed.MOV, buf.readFloat());
				}
				break;
			case PID.SyncShip_Emo: //entity emotion only
				{
					entity.setStateEmotion(ID.S.State, buf.readByte(), false);
					entity.setStateEmotion(ID.S.State2, buf.readByte(), false);
					entity.setStateEmotion(ID.S.Emotion, buf.readByte(), false);
					entity.setStateEmotion(ID.S.Emotion2, buf.readByte(), false);
					entity.setStateEmotion(ID.S.HPState, buf.readByte(), false);
					entity.setStateEmotion(ID.S.Phase, buf.readByte(), false);
				}
				break;
			case PID.SyncShip_Flag: //entity flag only
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
					entity.setStateFlag(ID.F.OnSightChase, buf.readBoolean());
					entity.setStateFlag(ID.F.PVPFirst, buf.readBoolean());
					entity.setStateFlag(ID.F.AntiAir, buf.readBoolean());
					entity.setStateFlag(ID.F.AntiSS, buf.readBoolean());
					entity.setStateFlag(ID.F.PassiveAI, buf.readBoolean());
					entity.setStateFlag(ID.F.TimeKeeper, buf.readBoolean());
				}
				break;
			case PID.SyncShip_Formation: //ship formation data only
				{
					entity.setStateMinor(ID.M.GuardX, buf.readInt());
					entity.setStateMinor(ID.M.GuardY, buf.readInt());
					entity.setStateMinor(ID.M.GuardZ, buf.readInt());
					entity.setStateMinor(ID.M.GuardDim, buf.readInt());
					entity.setStateMinor(ID.M.GuardType, buf.readInt());
					entity.setStateMinor(ID.M.FormatType, buf.readInt());
					entity.setStateMinor(ID.M.FormatPos, buf.readInt());
					entity.setEffectFormationFixed(ID.FormationFixed.MOV, buf.readFloat());
				}
				break;
			case PID.SyncShip_Minor: //entity minor only
				{
					entity.setStateMinor(ID.M.ShipLevel, buf.readInt());
					entity.setStateMinor(ID.M.Kills, buf.readInt());
					entity.setStateMinor(ID.M.ExpCurrent, buf.readInt());
					entity.setStateMinor(ID.M.NumAmmoLight, buf.readInt());
					entity.setStateMinor(ID.M.NumAmmoHeavy, buf.readInt());
					entity.setStateMinor(ID.M.NumGrudge, buf.readInt());
					entity.setStateMinor(ID.M.NumAirLight, buf.readInt());
					entity.setStateMinor(ID.M.NumAirHeavy, buf.readInt());
					entity.setStateMinor(ID.M.FollowMin, buf.readInt());
					entity.setStateMinor(ID.M.FollowMax, buf.readInt());
					entity.setStateMinor(ID.M.FleeHP, buf.readInt());
					entity.setStateMinor(ID.M.GuardX, buf.readInt());
					entity.setStateMinor(ID.M.GuardY, buf.readInt());
					entity.setStateMinor(ID.M.GuardZ, buf.readInt());
					entity.setStateMinor(ID.M.GuardDim, buf.readInt());
					entity.setStateMinor(ID.M.GuardID, buf.readInt());
					entity.setStateMinor(ID.M.GuardType, buf.readInt());
					entity.setStateMinor(ID.M.PlayerUID, buf.readInt());
					entity.setStateMinor(ID.M.ShipUID, buf.readInt());
					entity.setStateMinor(ID.M.PlayerEID, buf.readInt());
					entity.setStateMinor(ID.M.FormatType, buf.readInt());
					entity.setStateMinor(ID.M.FormatPos, buf.readInt());
				}
				break;
			case PID.SyncShip_Unbuff:	//sync unbuff attr
				{
					entity.setStateFinalBU(ID.HP, buf.readFloat());
					entity.setStateFinalBU(ID.ATK, buf.readFloat());
					entity.setStateFinalBU(ID.DEF, buf.readFloat());
					entity.setStateFinalBU(ID.SPD, buf.readFloat());
					entity.setStateFinalBU(ID.MOV, buf.readFloat());
					entity.setStateFinalBU(ID.HIT, buf.readFloat());
					entity.setStateFinalBU(ID.ATK_H, buf.readFloat());
					entity.setStateFinalBU(ID.ATK_AL, buf.readFloat());
					entity.setStateFinalBU(ID.ATK_AH, buf.readFloat());
					
					entity.setEffectEquipBU(ID.EF_CRI, buf.readFloat());
					entity.setEffectEquipBU(ID.EF_DHIT, buf.readFloat());
					entity.setEffectEquipBU(ID.EF_THIT, buf.readFloat());
					entity.setEffectEquipBU(ID.EF_MISS, buf.readFloat());
					entity.setEffectEquipBU(ID.EF_AA, buf.readFloat());
					entity.setEffectEquipBU(ID.EF_ASM, buf.readFloat());
					entity.setEffectEquipBU(ID.EF_DODGE, buf.readFloat());
				}
				break;
			case PID.SyncEntity_Emo: //IShipEmotion sync emtion
				{
					entity2e.setStateEmotion(ID.S.State, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.State2, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.Emotion, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.Emotion2, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.HPState, buf.readByte(), false);
					entity2e.setStateEmotion(ID.S.Phase, buf.readByte(), false);
				}
				break;
			case PID.SyncMount_ByPlayer: //player mount sync (only send when player right click on mount)
				{
					int playerId = buf.readInt();
					int seatId = buf.readInt();
					
					EntityPlayer player = EntityHelper.getEntityPlayerByID(playerId, 0, true);
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
			case PID.SyncSeat:	//seat2 sync
				{
					int playerId = buf.readInt();
					int hostId = buf.readInt();
							
					//dismount packet
					if(playerId < 0) {	//id設為-1表示為dismount packet
						entity3s.setRiderNull();
					}
					//mount sync packet
					else {
						EntityPlayer player = EntityHelper.getEntityPlayerByID(playerId, 0, true);
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
			case PID.SyncMount_ByMount:  //sync mount by mount entity
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
			case PID.SyncProjectile:	//missile type sync
				{
					this.value = buf.readInt();
					
					if(entity3 instanceof EntityAbyssMissile) {
						((EntityAbyssMissile)entity3).setMissileType(value);
					}
					if(entity3 instanceof EntityProjectileBeam) {
						((EntityProjectileBeam)entity3).setProjectileType(value);
					}
				}
				break;
			case PID.SyncEntity_PosRot:	//entity position sync
				{
					double px = buf.readDouble();
					double py = buf.readDouble();
					double pz = buf.readDouble();
					float yaw = buf.readFloat();
					float pit = buf.readFloat();
					
					entity3.setPositionAndRotation(px, py, pz, yaw, pit);
				}
				break;
			case PID.SyncEntity_Rot:	//entity rotation sync
				{
					float yaw = buf.readFloat();
					float pit = buf.readFloat();
					LogHelper.info("DEBUG : sync rotation: "+yaw);
					
					if(this.entity3 instanceof EntityLivingBase) {
						((EntityLivingBase) entity3).rotationYawHead = yaw;
						entity3.rotationPitch = pit;
					}
					else {
						entity3.rotationYaw = yaw;
						entity3.rotationPitch = pit;
					}
					
					if(this.entity3.ridingEntity instanceof BasicEntityMount) {
						((BasicEntityMount)this.entity3.ridingEntity).rotationYawHead = yaw;
						((BasicEntityMount)this.entity3.ridingEntity).rotationYaw = yaw;
					}
				}
				break;
			case PID.SyncEntity_Motion:	//entity motion sync
				{
					double px = buf.readFloat();
					double py = buf.readFloat();
					double pz = buf.readFloat();
					
					entity3.setVelocity(px, px, pz);
				}
				break;
			}
		}
		else {
			buf.clear();
			LogHelper.info("DEBUG : packet handler: S2CEntitySync: entity is null, type: "+type+" eid: "+entityID);
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf) {
		switch(this.type) {
		case PID.SyncShip_All:	//sync all data
			{
				buf.writeByte(PID.SyncShip_All);	//type 0
				buf.writeInt(this.entity.getEntityId());
				buf.writeInt(this.entity.getStateMinor(ID.M.ShipLevel));
				buf.writeInt(this.entity.getStateMinor(ID.M.Kills));
				buf.writeInt(this.entity.getStateMinor(ID.M.ExpCurrent));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumAmmoLight));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumAmmoHeavy));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumGrudge));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumAirLight));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumAirHeavy));
				buf.writeInt(this.entity.getStateMinor(ID.M.FollowMin));
				buf.writeInt(this.entity.getStateMinor(ID.M.FollowMax));
				buf.writeInt(this.entity.getStateMinor(ID.M.FleeHP));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardX));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardY));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardZ));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardDim));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardID));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardType));
				buf.writeInt(this.entity.getStateMinor(ID.M.PlayerUID));
				buf.writeInt(this.entity.getStateMinor(ID.M.ShipUID));
				buf.writeInt(this.entity.getStateMinor(ID.M.PlayerEID));
				buf.writeInt(this.entity.getStateMinor(ID.M.FormatType));
				buf.writeInt(this.entity.getStateMinor(ID.M.FormatPos));
				
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
				buf.writeBoolean(this.entity.getStateFlag(ID.F.OnSightChase));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.PVPFirst));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.AntiAir));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.AntiSS));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.PassiveAI));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.TimeKeeper));
				
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_CRI));
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_DHIT));
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_THIT));
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_MISS));
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_AA));
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_ASM));
				buf.writeFloat(this.entity.getEffectEquip(ID.EF_DODGE));
				
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.ATK_L));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.ATK_H));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.ATK_AL));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.ATK_AH));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.DEF));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.MOV));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.MISS));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.DODGE));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.CRI));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.DHIT));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.THIT));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.AA));
				buf.writeFloat(this.entity.getEffectFormation(ID.Formation.ASM));
				
				buf.writeFloat(this.entity.getEffectFormationFixed(ID.FormationFixed.MOV));
			}
			break;
		case PID.SyncShip_Emo:	//entity state only
			{
				buf.writeByte(PID.SyncShip_Emo);	//type 1
				buf.writeInt(this.entity.getEntityId());
				buf.writeByte(this.entity.getStateEmotion(ID.S.State));
				buf.writeByte(this.entity.getStateEmotion(ID.S.State2));
				buf.writeByte(this.entity.getStateEmotion(ID.S.Emotion));
				buf.writeByte(this.entity.getStateEmotion(ID.S.Emotion2));
				buf.writeByte(this.entity.getStateEmotion(ID.S.HPState));
				buf.writeByte(this.entity.getStateEmotion(ID.S.Phase));
			}
			break;
		case PID.SyncShip_Flag:	//entity flag only
			{
				buf.writeByte(PID.SyncShip_Flag);	//type 2
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
				buf.writeBoolean(this.entity.getStateFlag(ID.F.OnSightChase));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.PVPFirst));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.AntiAir));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.AntiSS));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.PassiveAI));
				buf.writeBoolean(this.entity.getStateFlag(ID.F.TimeKeeper));
			}
			break;
		case PID.SyncShip_Minor:	//sync minor only
			{
				buf.writeByte(PID.SyncShip_Minor);	//type 3
				buf.writeInt(this.entity.getEntityId());
				buf.writeInt(this.entity.getStateMinor(ID.M.ShipLevel));
				buf.writeInt(this.entity.getStateMinor(ID.M.Kills));
				buf.writeInt(this.entity.getStateMinor(ID.M.ExpCurrent));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumAmmoLight));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumAmmoHeavy));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumGrudge));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumAirLight));
				buf.writeInt(this.entity.getStateMinor(ID.M.NumAirHeavy));
				buf.writeInt(this.entity.getStateMinor(ID.M.FollowMin));
				buf.writeInt(this.entity.getStateMinor(ID.M.FollowMax));
				buf.writeInt(this.entity.getStateMinor(ID.M.FleeHP));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardX));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardY));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardZ));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardDim));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardID));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardType));
				buf.writeInt(this.entity.getStateMinor(ID.M.PlayerUID));
				buf.writeInt(this.entity.getStateMinor(ID.M.ShipUID));
				buf.writeInt(this.entity.getStateMinor(ID.M.PlayerEID));
				buf.writeInt(this.entity.getStateMinor(ID.M.FormatType));
				buf.writeInt(this.entity.getStateMinor(ID.M.FormatPos));
			}
			break;
		case PID.SyncShip_Unbuff:	//sync unbuff data
			{
				buf.writeByte(PID.SyncShip_Unbuff);	//type 0
				buf.writeInt(this.entity.getEntityId());
	
				buf.writeFloat(this.entity.getStateFinalBU(ID.HP));
				buf.writeFloat(this.entity.getStateFinalBU(ID.ATK));
				buf.writeFloat(this.entity.getStateFinalBU(ID.DEF));
				buf.writeFloat(this.entity.getStateFinalBU(ID.SPD));
				buf.writeFloat(this.entity.getStateFinalBU(ID.MOV));
				buf.writeFloat(this.entity.getStateFinalBU(ID.HIT));
				buf.writeFloat(this.entity.getStateFinalBU(ID.ATK_H));
				buf.writeFloat(this.entity.getStateFinalBU(ID.ATK_AL));
				buf.writeFloat(this.entity.getStateFinalBU(ID.ATK_AH));
				
				buf.writeFloat(this.entity.getEffectEquipBU(ID.EF_CRI));
				buf.writeFloat(this.entity.getEffectEquipBU(ID.EF_DHIT));
				buf.writeFloat(this.entity.getEffectEquipBU(ID.EF_THIT));
				buf.writeFloat(this.entity.getEffectEquipBU(ID.EF_MISS));
				buf.writeFloat(this.entity.getEffectEquipBU(ID.EF_AA));
				buf.writeFloat(this.entity.getEffectEquipBU(ID.EF_ASM));
				buf.writeFloat(this.entity.getEffectEquipBU(ID.EF_DODGE));
			}
			break;
		case PID.SyncEntity_Emo:	//IShipEmotion emotion only
			{
				buf.writeByte(PID.SyncEntity_Emo);	//type 4
				buf.writeInt(this.entity2.getEntityId());
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.State));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.State2));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.Emotion));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.Emotion2));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.HPState));
				buf.writeByte(this.entity2e.getStateEmotion(ID.S.Phase));
			}
			break;
		case PID.SyncMount_ByPlayer:	//IShipEmotion player mount packet
			{
				buf.writeByte(PID.SyncMount_ByPlayer);	//type 5
				buf.writeInt(this.entity2.getEntityId());
				buf.writeInt(((BasicEntityMount)this.entity2e).riddenByEntity2.getEntityId());
				buf.writeInt(((BasicEntityMount)this.entity2e).seat2.getEntityId());
			}
			break;
		case PID.SyncSeat:	//mount seat2 sync
			{
				buf.writeByte(PID.SyncSeat);	//type 6
				
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
		case PID.SyncMount_ByMount:	//mounts sync
			{
				if(entity2 instanceof BasicEntityMount) {
					BasicEntityMount mount = (BasicEntityMount) entity2;
					
					buf.writeByte(PID.SyncMount_ByMount);	//type 7
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
		case PID.SyncProjectile:	//missile tpye sync
			{
				buf.writeByte(PID.SyncProjectile);	//type 8
				buf.writeInt(this.entity3.getEntityId());
				buf.writeInt(this.value);
			}
			break;
		case PID.SyncEntity_PosRot:	//entity position sync
			{
				buf.writeByte(PID.SyncEntity_PosRot);	//type 9
				buf.writeInt(this.entity3.getEntityId());
				buf.writeDouble(this.entity3.posX);
				buf.writeDouble(this.entity3.posY);
				buf.writeDouble(this.entity3.posZ);
				buf.writeFloat(this.entity3.rotationYaw);
				buf.writeFloat(this.entity3.rotationPitch);
			}
			break;
		case PID.SyncEntity_Rot:	//entity rotation sync
			{
				buf.writeByte(PID.SyncEntity_Rot);
				buf.writeInt(this.entity3.getEntityId());
				
				if(this.entity3 instanceof EntityLivingBase) {
					buf.writeFloat(((EntityLivingBase) this.entity3).rotationYawHead);
					buf.writeFloat(this.entity3.rotationPitch);
				}
				else {
					buf.writeFloat(this.entity3.rotationYaw);
					buf.writeFloat(this.entity3.rotationPitch);
				}
			}
			break;
		case PID.SyncShip_Formation: //ship formation data only
			{
				buf.writeByte(PID.SyncShip_Formation);	//type 3
				buf.writeInt(this.entity.getEntityId());
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardX));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardY));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardZ));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardDim));
				buf.writeInt(this.entity.getStateMinor(ID.M.GuardType));
				buf.writeInt(this.entity.getStateMinor(ID.M.FormatType));
				buf.writeInt(this.entity.getStateMinor(ID.M.FormatPos));
				buf.writeFloat(this.entity.getEffectFormationFixed(ID.FormationFixed.MOV));
			}
			break;
		case PID.SyncEntity_Motion:	//entity motion sync
			{
				buf.writeByte(PID.SyncEntity_Motion);
				buf.writeInt(this.entity3.getEntityId());
				buf.writeFloat((float) this.entity3.motionX);
				buf.writeFloat((float) this.entity3.motionY);
				buf.writeFloat((float) this.entity3.motionZ);
			}
			break;
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CEntitySync, IMessage> {
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CEntitySync message, MessageContext ctx) {
//			System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
//			LogHelper.info("DEBUG : recv Entity Sync packet : type "+recvType+" id "+entityID);
			return null;
		}
    }

}
