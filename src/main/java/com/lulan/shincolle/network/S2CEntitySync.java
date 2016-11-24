package com.lulan.shincolle.network;

import java.util.List;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.PacketHelper;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**SERVER TO CLIENT : ENTITY SYNC PACKET
 * 用於entity的資料同步
 * packet handler同樣建立在此class中
 * 
 * tut by diesieben07: http://www.minecraftforge.net/forum/index.php/topic,20135.0.html
 */
public class S2CEntitySync implements IMessage
{
	
	private Entity entity;
	private int entityID, value, type;
	
	//packet id
	public static final class PID
	{
		public static final byte SyncShip_All = 0;
		public static final byte SyncShip_Emo = 1;
		public static final byte SyncShip_Flag = 2;
		public static final byte SyncShip_Minor = 3;
		public static final byte SyncEntity_Emo = 4;
		public static final byte SyncMount_AllRider = 5;
		public static final byte NO_USE_0 = 6;
		public static final byte NO_USE_1 = 7;
		public static final byte SyncProjectile = 8;
		public static final byte SyncEntity_PosRot = 9;
		public static final byte SyncEntity_Rot = 10;
		public static final byte SyncShip_Formation = 11;
		public static final byte SyncShip_Unbuff = 12;
		public static final byte SyncEntity_Motion = 13;
		public static final byte SyncShip_Timer = 14;
		public static final byte SyncShip_Guard = 15;
		public static final byte SyncShip_ID = 16;
	}

	
	public S2CEntitySync() {}	//必須要有空參數constructor, forge才能使用此class
	
	/**entity sync: 
	 * type 0: all attribute
	 * type 1: entity emotion only
	 * type 2: entity flag only
	 * type 3: entity minor only
	 * type 4: all emotion
	 * type 5: player ride mount packet
	 * type 6: 
	 * type 7: mounts sync
	 * type 11:ship formation data
	 * type 12:ship unbuff data
	 * type 14:ship timer only
	 * type 15:ship guard target only
	 * type 16:ship UID only
	 */
	public S2CEntitySync(Entity entity, int type)
	{
        this.entity = entity;
        this.type = type;
    }

	/**for mount seat sync
	 * type 8:  projectile type sync
	 * type 9:  entity pos sync (for teleport)
	 * type 10: entity rot sync (for looking update)
	 * type 13: entity motion sync (for knockback)
	 */
	public S2CEntitySync(Entity entity, int value, int type)
	{
		this.entity = entity;
        this.value = value;
        this.type = type;
    }

	//接收packet方法 (CLIENT SIDE)
	@Override
	public void fromBytes(ByteBuf buf)
	{
		boolean getTarget = false;
		
		//get type and entityID
		this.type = buf.readByte();
		this.entityID = buf.readInt();
		
		//get target entity
		this.entity = EntityHelper.getEntityByID(entityID, 0, true);
		
		switch(this.type)
		{
		case PID.SyncShip_All:
		case PID.SyncShip_Emo:
		case PID.SyncShip_Flag:
		case PID.SyncShip_Formation:
		case PID.SyncShip_Minor:
		case PID.SyncShip_Guard:
		case PID.SyncShip_ID:
		case PID.SyncShip_Unbuff:
		case PID.SyncShip_Timer:
		case PID.SyncEntity_Emo:
			if (this.entity instanceof BasicEntityShip ||
				this.entity instanceof IShipEmotion ||
				this.entity instanceof EntityLiving)
			{
				getTarget = true;
			}
		break;
		case PID.SyncMount_AllRider:
			if (this.entity instanceof BasicEntityMount)
			{
				getTarget = true;
			}
		break;
		case PID.SyncProjectile:	//missile sync
		case PID.SyncEntity_PosRot: //entity position, rotation, motion sync
		case PID.SyncEntity_Rot:
		case PID.SyncEntity_Motion:
			if (this.entity != null)
			{
				getTarget = true;
			}
		break;
		}

		//get entity to sync
		if (getTarget)
		{
			switch (type)
			{
			case PID.SyncShip_All:	//sync all attr
			{
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
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
				entity.setStateMinor(ID.M.Morale, buf.readInt());
				entity.setStateMinor(ID.M.InvSize, buf.readInt());
				entity.setStateMinor(ID.M.LevelChunkLoader, buf.readInt());
				entity.setStateMinor(ID.M.LevelFlare, buf.readInt());
				entity.setStateMinor(ID.M.LevelSearchlight, buf.readInt());
				entity.setStateMinor(ID.M.WpStay, buf.readInt());
				
				entity.setStateTimer(ID.T.CraneTime, buf.readInt());
				
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
				entity.setStateEmotion(ID.S.Emotion3, buf.readByte(), false);

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
				entity.setStateFlag(ID.F.PickItem, buf.readBoolean());
				entity.setStateFlag(ID.F.CanPickItem, buf.readBoolean());
				
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
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
				entity.setStateEmotion(ID.S.State, buf.readByte(), false);
				entity.setStateEmotion(ID.S.State2, buf.readByte(), false);
				entity.setStateEmotion(ID.S.Emotion, buf.readByte(), false);
				entity.setStateEmotion(ID.S.Emotion2, buf.readByte(), false);
				entity.setStateEmotion(ID.S.HPState, buf.readByte(), false);
				entity.setStateEmotion(ID.S.Phase, buf.readByte(), false);
				entity.setStateEmotion(ID.S.Emotion3, buf.readByte(), false);
				
				entity.setStateFlag(ID.F.NoFuel, buf.readBoolean());
			}
			break;
			case PID.SyncShip_Flag: //entity flag only
			{
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
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
				entity.setStateFlag(ID.F.PickItem, buf.readBoolean());
				entity.setStateFlag(ID.F.CanPickItem, buf.readBoolean());
			}
			break;
			case PID.SyncShip_Formation: //ship formation data only
			{
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
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
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
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
				entity.setStateMinor(ID.M.Morale, buf.readInt());
				entity.setStateMinor(ID.M.InvSize, buf.readInt());
				entity.setStateMinor(ID.M.LevelChunkLoader, buf.readInt());
				entity.setStateMinor(ID.M.LevelFlare, buf.readInt());
				entity.setStateMinor(ID.M.LevelSearchlight, buf.readInt());
				entity.setStateMinor(ID.M.WpStay, buf.readInt());
			}
			break;
			case PID.SyncShip_Guard:  //sync guard for particle display
			{
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
				entity.setStateMinor(ID.M.GuardX, buf.readInt());
				entity.setStateMinor(ID.M.GuardY, buf.readInt());
				entity.setStateMinor(ID.M.GuardZ, buf.readInt());
				entity.setStateMinor(ID.M.GuardDim, buf.readInt());
				entity.setStateMinor(ID.M.GuardID, buf.readInt());
				entity.setStateMinor(ID.M.GuardType, buf.readInt());
			}
			break;
			case PID.SyncShip_ID:
			{
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
				entity.setStateMinor(ID.M.PlayerUID, buf.readInt());
				entity.setStateMinor(ID.M.ShipUID, buf.readInt());
				entity.setStateMinor(ID.M.PlayerEID, buf.readInt());
			}
			break;
			case PID.SyncShip_Unbuff:	//sync unbuff attr
			{
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
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
			case PID.SyncShip_Timer: //ship timer only
			{
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
				entity.setStateTimer(ID.T.CraneTime, buf.readInt());
			}
			break;
			case PID.SyncEntity_Emo: //IShipEmotion sync emtion
			{
				IShipEmotion entity = (IShipEmotion) this.entity;
				
				entity.setStateEmotion(ID.S.State, buf.readByte(), false);
				entity.setStateEmotion(ID.S.State2, buf.readByte(), false);
				entity.setStateEmotion(ID.S.Emotion, buf.readByte(), false);
				entity.setStateEmotion(ID.S.Emotion2, buf.readByte(), false);
				entity.setStateEmotion(ID.S.HPState, buf.readByte(), false);
				entity.setStateEmotion(ID.S.Phase, buf.readByte(), false);
			}
			break;
			case PID.SyncMount_AllRider: //player mount sync (only send when player right click on mount)
			{
				int riderLen = buf.readInt();
				
				//get rider to sync
				if (riderLen > 0)
				{
					int[] riders = PacketHelper.readIntArray(buf, riderLen);
					
					//set mount
					for (int i = 0; i < riderLen; i++)
					{
						Entity ent = EntityHelper.getEntityByID(riders[i], 0, true);
						if (ent != null) ent.startRiding(this.entity, true);
					}

					//set mount pose if riders > 1
					if (riderLen > 1) ((BasicEntityMount)this.entity).setStateEmotion(ID.S.Emotion, 1, false);
				}
			}
			break;
			case PID.SyncProjectile:	//missile type sync
			{
				this.value = buf.readInt();
				
				if (this.entity instanceof EntityAbyssMissile)
				{
					((EntityAbyssMissile)this.entity).setMissileType(value);
				}
				else if (this.entity instanceof EntityProjectileBeam)
				{
					((EntityProjectileBeam)this.entity).setProjectileType(value);
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
				
				this.entity.setPositionAndRotation(px, py, pz, yaw, pit);
			}
			break;
			case PID.SyncEntity_Rot:	//entity rotation sync TODO need sync?
			{
				float yaw = buf.readFloat();
				float pit = buf.readFloat();
				LogHelper.info("DEBUG : sync rotation: "+yaw);
				
				if (this.entity instanceof EntityLivingBase)
				{
					((EntityLivingBase) this.entity).rotationYawHead = yaw;
					this.entity.rotationPitch = pit;
				}
				else
				{
					this.entity.rotationYaw = yaw;
					this.entity.rotationPitch = pit;
				}
				
				if (this.entity.getRidingEntity() instanceof BasicEntityMount)
				{
					((BasicEntityMount)this.entity.getRidingEntity()).rotationYawHead = yaw;
					((BasicEntityMount)this.entity.getRidingEntity()).rotationYaw = yaw;
				}
			}
			break;
			case PID.SyncEntity_Motion:	//entity motion sync
			{
				double px = buf.readFloat();
				double py = buf.readFloat();
				double pz = buf.readFloat();
				
				this.entity.setVelocity(px, px, pz);
			}
			break;
			}//end switch
		}//end can sync
		else
		{
			buf.clear();
			LogHelper.info("DEBUG : packet handler: S2CEntitySync: entity is null, type: "+type+" eid: "+entityID);
		}
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf)
	{
		//send packet and entity id
		buf.writeByte(type);
		buf.writeInt(entity.getEntityId());
		
		switch (this.type)
		{
		case PID.SyncShip_All:	//sync all data
		{
			BasicEntityShip entity = (BasicEntityShip) this.entity;
			
			buf.writeInt(entity.getStateMinor(ID.M.ShipLevel));
			buf.writeInt(entity.getStateMinor(ID.M.Kills));
			buf.writeInt(entity.getStateMinor(ID.M.ExpCurrent));
			buf.writeInt(entity.getStateMinor(ID.M.NumAmmoLight));
			buf.writeInt(entity.getStateMinor(ID.M.NumAmmoHeavy));
			buf.writeInt(entity.getStateMinor(ID.M.NumGrudge));
			buf.writeInt(entity.getStateMinor(ID.M.NumAirLight));
			buf.writeInt(entity.getStateMinor(ID.M.NumAirHeavy));
			buf.writeInt(entity.getStateMinor(ID.M.FollowMin));
			buf.writeInt(entity.getStateMinor(ID.M.FollowMax));
			buf.writeInt(entity.getStateMinor(ID.M.FleeHP));
			buf.writeInt(entity.getStateMinor(ID.M.GuardX));
			buf.writeInt(entity.getStateMinor(ID.M.GuardY));
			buf.writeInt(entity.getStateMinor(ID.M.GuardZ));
			buf.writeInt(entity.getStateMinor(ID.M.GuardDim));
			buf.writeInt(entity.getStateMinor(ID.M.GuardID));
			buf.writeInt(entity.getStateMinor(ID.M.GuardType));
			buf.writeInt(entity.getStateMinor(ID.M.PlayerUID));
			buf.writeInt(entity.getStateMinor(ID.M.ShipUID));
			buf.writeInt(entity.getStateMinor(ID.M.PlayerEID));
			buf.writeInt(entity.getStateMinor(ID.M.FormatType));
			buf.writeInt(entity.getStateMinor(ID.M.FormatPos));
			buf.writeInt(entity.getStateMinor(ID.M.Morale));
			buf.writeInt(entity.getStateMinor(ID.M.InvSize));
			buf.writeInt(entity.getStateMinor(ID.M.LevelChunkLoader));
			buf.writeInt(entity.getStateMinor(ID.M.LevelFlare));
			buf.writeInt(entity.getStateMinor(ID.M.LevelSearchlight));
			buf.writeInt(entity.getStateMinor(ID.M.WpStay));
			
			buf.writeInt(entity.getStateTimer(ID.T.CraneTime));
			
			buf.writeFloat(entity.getStateFinal(ID.HP));
			buf.writeFloat(entity.getStateFinal(ID.ATK));
			buf.writeFloat(entity.getStateFinal(ID.DEF));
			buf.writeFloat(entity.getStateFinal(ID.SPD));
			buf.writeFloat(entity.getStateFinal(ID.MOV));
			buf.writeFloat(entity.getStateFinal(ID.HIT));
			buf.writeFloat(entity.getStateFinal(ID.ATK_H));
			buf.writeFloat(entity.getStateFinal(ID.ATK_AL));
			buf.writeFloat(entity.getStateFinal(ID.ATK_AH));
			
			buf.writeByte(entity.getStateEmotion(ID.S.State));
			buf.writeByte(entity.getStateEmotion(ID.S.State2));
			buf.writeByte(entity.getStateEmotion(ID.S.Emotion));
			buf.writeByte(entity.getStateEmotion(ID.S.Emotion2));
			buf.writeByte(entity.getStateEmotion(ID.S.HPState));
			buf.writeByte(entity.getStateEmotion(ID.S.Phase));
			buf.writeByte(entity.getStateEmotion(ID.S.Emotion3));

			buf.writeByte(entity.getBonusPoint(ID.HP));
			buf.writeByte(entity.getBonusPoint(ID.ATK));
			buf.writeByte(entity.getBonusPoint(ID.DEF));
			buf.writeByte(entity.getBonusPoint(ID.SPD));
			buf.writeByte(entity.getBonusPoint(ID.MOV));
			buf.writeByte(entity.getBonusPoint(ID.HIT));

			buf.writeBoolean(entity.getStateFlag(ID.F.CanFloatUp));
			buf.writeBoolean(entity.getStateFlag(ID.F.IsMarried));
			buf.writeBoolean(entity.getStateFlag(ID.F.NoFuel));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseMelee));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseAmmoLight));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseAmmoHeavy));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseAirLight));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseAirHeavy));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseRingEffect));
			buf.writeBoolean(entity.getStateFlag(ID.F.OnSightChase));
			buf.writeBoolean(entity.getStateFlag(ID.F.PVPFirst));
			buf.writeBoolean(entity.getStateFlag(ID.F.AntiAir));
			buf.writeBoolean(entity.getStateFlag(ID.F.AntiSS));
			buf.writeBoolean(entity.getStateFlag(ID.F.PassiveAI));
			buf.writeBoolean(entity.getStateFlag(ID.F.TimeKeeper));
			buf.writeBoolean(entity.getStateFlag(ID.F.PickItem));
			buf.writeBoolean(entity.getStateFlag(ID.F.CanPickItem));
			
			buf.writeFloat(entity.getEffectEquip(ID.EF_CRI));
			buf.writeFloat(entity.getEffectEquip(ID.EF_DHIT));
			buf.writeFloat(entity.getEffectEquip(ID.EF_THIT));
			buf.writeFloat(entity.getEffectEquip(ID.EF_MISS));
			buf.writeFloat(entity.getEffectEquip(ID.EF_AA));
			buf.writeFloat(entity.getEffectEquip(ID.EF_ASM));
			buf.writeFloat(entity.getEffectEquip(ID.EF_DODGE));
			
			buf.writeFloat(entity.getEffectFormation(ID.Formation.ATK_L));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.ATK_H));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.ATK_AL));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.ATK_AH));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.DEF));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.MOV));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.MISS));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.DODGE));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.CRI));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.DHIT));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.THIT));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.AA));
			buf.writeFloat(entity.getEffectFormation(ID.Formation.ASM));
			
			buf.writeFloat(entity.getEffectFormationFixed(ID.FormationFixed.MOV));
		}
		break;
		case PID.SyncShip_Emo:	//entity state only
		{
			BasicEntityShip entity = (BasicEntityShip) this.entity;
			
			buf.writeByte(entity.getStateEmotion(ID.S.State));
			buf.writeByte(entity.getStateEmotion(ID.S.State2));
			buf.writeByte(entity.getStateEmotion(ID.S.Emotion));
			buf.writeByte(entity.getStateEmotion(ID.S.Emotion2));
			buf.writeByte(entity.getStateEmotion(ID.S.HPState));
			buf.writeByte(entity.getStateEmotion(ID.S.Phase));
			buf.writeByte(entity.getStateEmotion(ID.S.Emotion3));
			
			buf.writeBoolean(entity.getStateFlag(ID.F.NoFuel));
		}
		break;
		case PID.SyncShip_Flag:	//entity flag only
		{
			BasicEntityShip entity = (BasicEntityShip) this.entity;
			
			buf.writeBoolean(entity.getStateFlag(ID.F.CanFloatUp));
			buf.writeBoolean(entity.getStateFlag(ID.F.IsMarried));
			buf.writeBoolean(entity.getStateFlag(ID.F.NoFuel));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseMelee));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseAmmoLight));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseAmmoHeavy));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseAirLight));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseAirHeavy));
			buf.writeBoolean(entity.getStateFlag(ID.F.UseRingEffect));
			buf.writeBoolean(entity.getStateFlag(ID.F.OnSightChase));
			buf.writeBoolean(entity.getStateFlag(ID.F.PVPFirst));
			buf.writeBoolean(entity.getStateFlag(ID.F.AntiAir));
			buf.writeBoolean(entity.getStateFlag(ID.F.AntiSS));
			buf.writeBoolean(entity.getStateFlag(ID.F.PassiveAI));
			buf.writeBoolean(entity.getStateFlag(ID.F.TimeKeeper));
			buf.writeBoolean(entity.getStateFlag(ID.F.PickItem));
			buf.writeBoolean(entity.getStateFlag(ID.F.CanPickItem));
		}
		break;
		case PID.SyncShip_Minor:	//sync minor only
		{
			BasicEntityShip entity = (BasicEntityShip) this.entity;
			
			buf.writeInt(entity.getStateMinor(ID.M.ShipLevel));
			buf.writeInt(entity.getStateMinor(ID.M.Kills));
			buf.writeInt(entity.getStateMinor(ID.M.ExpCurrent));
			buf.writeInt(entity.getStateMinor(ID.M.NumAmmoLight));
			buf.writeInt(entity.getStateMinor(ID.M.NumAmmoHeavy));
			buf.writeInt(entity.getStateMinor(ID.M.NumGrudge));
			buf.writeInt(entity.getStateMinor(ID.M.NumAirLight));
			buf.writeInt(entity.getStateMinor(ID.M.NumAirHeavy));
			buf.writeInt(entity.getStateMinor(ID.M.FollowMin));
			buf.writeInt(entity.getStateMinor(ID.M.FollowMax));
			buf.writeInt(entity.getStateMinor(ID.M.FleeHP));
			buf.writeInt(entity.getStateMinor(ID.M.GuardX));
			buf.writeInt(entity.getStateMinor(ID.M.GuardY));
			buf.writeInt(entity.getStateMinor(ID.M.GuardZ));
			buf.writeInt(entity.getStateMinor(ID.M.GuardDim));
			buf.writeInt(entity.getStateMinor(ID.M.GuardID));
			buf.writeInt(entity.getStateMinor(ID.M.GuardType));
			buf.writeInt(entity.getStateMinor(ID.M.PlayerUID));
			buf.writeInt(entity.getStateMinor(ID.M.ShipUID));
			buf.writeInt(entity.getStateMinor(ID.M.PlayerEID));
			buf.writeInt(entity.getStateMinor(ID.M.FormatType));
			buf.writeInt(entity.getStateMinor(ID.M.FormatPos));
			buf.writeInt(entity.getStateMinor(ID.M.Morale));
			buf.writeInt(entity.getStateMinor(ID.M.InvSize));
			buf.writeInt(entity.getStateMinor(ID.M.LevelChunkLoader));
			buf.writeInt(entity.getStateMinor(ID.M.LevelFlare));
			buf.writeInt(entity.getStateMinor(ID.M.LevelSearchlight));
			buf.writeInt(entity.getStateMinor(ID.M.WpStay));
		}
		break;
		case PID.SyncShip_Guard:	//sync guard for particle display
		{
			BasicEntityShip entity = (BasicEntityShip) this.entity;
			
			buf.writeInt(entity.getStateMinor(ID.M.GuardX));
			buf.writeInt(entity.getStateMinor(ID.M.GuardY));
			buf.writeInt(entity.getStateMinor(ID.M.GuardZ));
			buf.writeInt(entity.getStateMinor(ID.M.GuardDim));
			buf.writeInt(entity.getStateMinor(ID.M.GuardID));
			buf.writeInt(entity.getStateMinor(ID.M.GuardType));
		}
		break;
		case PID.SyncShip_ID:		//sync id only
		{
			BasicEntityShip entity = (BasicEntityShip) this.entity;
			
			buf.writeInt(entity.getStateMinor(ID.M.PlayerUID));
			buf.writeInt(entity.getStateMinor(ID.M.ShipUID));
			buf.writeInt(entity.getStateMinor(ID.M.PlayerEID));
		}
		break;
		case PID.SyncShip_Unbuff:	//sync unbuff data
		{
			BasicEntityShip entity = (BasicEntityShip) this.entity;
			
			buf.writeFloat(entity.getStateFinalBU(ID.HP));
			buf.writeFloat(entity.getStateFinalBU(ID.ATK));
			buf.writeFloat(entity.getStateFinalBU(ID.DEF));
			buf.writeFloat(entity.getStateFinalBU(ID.SPD));
			buf.writeFloat(entity.getStateFinalBU(ID.MOV));
			buf.writeFloat(entity.getStateFinalBU(ID.HIT));
			buf.writeFloat(entity.getStateFinalBU(ID.ATK_H));
			buf.writeFloat(entity.getStateFinalBU(ID.ATK_AL));
			buf.writeFloat(entity.getStateFinalBU(ID.ATK_AH));
			
			buf.writeFloat(entity.getEffectEquipBU(ID.EF_CRI));
			buf.writeFloat(entity.getEffectEquipBU(ID.EF_DHIT));
			buf.writeFloat(entity.getEffectEquipBU(ID.EF_THIT));
			buf.writeFloat(entity.getEffectEquipBU(ID.EF_MISS));
			buf.writeFloat(entity.getEffectEquipBU(ID.EF_AA));
			buf.writeFloat(entity.getEffectEquipBU(ID.EF_ASM));
			buf.writeFloat(entity.getEffectEquipBU(ID.EF_DODGE));
		}
		break;
		case PID.SyncEntity_Emo:	//IShipEmotion emotion only
		{
			IShipEmotion entity = (IShipEmotion) this.entity;
			
			buf.writeByte(entity.getStateEmotion(ID.S.State));
			buf.writeByte(entity.getStateEmotion(ID.S.State2));
			buf.writeByte(entity.getStateEmotion(ID.S.Emotion));
			buf.writeByte(entity.getStateEmotion(ID.S.Emotion2));
			buf.writeByte(entity.getStateEmotion(ID.S.HPState));
			buf.writeByte(entity.getStateEmotion(ID.S.Phase));
		}
		break;
		case PID.SyncShip_Timer:			//sync timer only
		{
			BasicEntityShip entity = (BasicEntityShip) this.entity;
			
			buf.writeInt(entity.getStateTimer(ID.T.CraneTime));
		}
		break;
		case PID.SyncMount_AllRider:		//sync rider list
		{
			BasicEntityMount mount = (BasicEntityMount) this.entity;
			List<Entity> list = mount.getPassengers();
			int length = list.size();
			
			//send length
			buf.writeInt(length);
			
			//send list
			if (length > 0)
			{
				for (Entity ent : list)
				{
					buf.writeInt(ent.getEntityId());
				}
			}
		}
		break;
		case PID.SyncProjectile:	//missile tpye sync
		{
			buf.writeInt(this.value);
		}
		break;
		case PID.SyncEntity_PosRot:	//entity position sync
		{
			buf.writeDouble(this.entity.posX);
			buf.writeDouble(this.entity.posY);
			buf.writeDouble(this.entity.posZ);
			buf.writeFloat(this.entity.rotationYaw);
			buf.writeFloat(this.entity.rotationPitch);
		}
		break;
		case PID.SyncEntity_Rot:	//entity rotation sync
		{
			if (this.entity instanceof EntityLivingBase)
			{
				buf.writeFloat(((EntityLivingBase) this.entity).rotationYawHead);
				buf.writeFloat(this.entity.rotationPitch);
			}
			else
			{
				buf.writeFloat(this.entity.rotationYaw);
				buf.writeFloat(this.entity.rotationPitch);
			}
		}
		break;
		case PID.SyncShip_Formation: //ship formation data only
			{
				BasicEntityShip entity = (BasicEntityShip) this.entity;
				
				buf.writeInt(entity.getStateMinor(ID.M.GuardX));
				buf.writeInt(entity.getStateMinor(ID.M.GuardY));
				buf.writeInt(entity.getStateMinor(ID.M.GuardZ));
				buf.writeInt(entity.getStateMinor(ID.M.GuardDim));
				buf.writeInt(entity.getStateMinor(ID.M.GuardType));
				buf.writeInt(entity.getStateMinor(ID.M.FormatType));
				buf.writeInt(entity.getStateMinor(ID.M.FormatPos));
				buf.writeFloat(entity.getEffectFormationFixed(ID.FormationFixed.MOV));
			}
			break;
		case PID.SyncEntity_Motion:	//entity motion sync
			{
				buf.writeFloat((float) entity.motionX);
				buf.writeFloat((float) entity.motionY);
				buf.writeFloat((float) entity.motionZ);
			}
			break;
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CEntitySync, IMessage>
	{
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CEntitySync message, MessageContext ctx)
		{
//			System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
//			LogHelper.info("DEBUG : recv Entity Sync packet : type "+recvType+" id "+entityID);
			return null;
		}
    }
	
	
}
