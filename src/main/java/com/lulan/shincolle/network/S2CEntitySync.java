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
import net.minecraftforge.fml.common.FMLCommonHandler;
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
	private byte packetType;
	private int entityID, valueInt;
	private int[] valueInt1;
	private float[] valueFloat1;
	private double[] valueDouble1;
	private byte[] valueByte1;
	private boolean[] valueBoolean1;
	
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
	 * type 11:ship formation data
	 * type 12:ship unbuff data
	 * type 14:ship timer only
	 * type 15:ship guard target only
	 * type 16:ship UID only
	 */
	public S2CEntitySync(Entity entity, byte type)
	{
        this.entity = entity;
        this.packetType = type;
    }

	/**for mount seat sync
	 * type 8:  projectile type sync
	 * type 9:  entity pos sync (for teleport)
	 * type 10: entity rot sync (for looking update)
	 * type 13: entity motion sync (for knockback)
	 */
	public S2CEntitySync(Entity entity, int value, byte type)
	{
		this.entity = entity;
        this.packetType = type;
        this.valueInt = value;
    }

	//接收packet方法 (CLIENT SIDE)
	@Override
	public void fromBytes(ByteBuf buf)
	{
		//get type and entityID
		this.packetType = buf.readByte();
		this.entityID = buf.readInt();
		
		switch (this.packetType)
		{
		case PID.SyncShip_All:	//sync all attr
			this.valueInt1 = PacketHelper.readIntArray(buf, 1+28);
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 9+7+14);
			this.valueByte1 = PacketHelper.readByteArray(buf, 7+6);
			this.valueBoolean1 = PacketHelper.readBooleanArray(buf, 17);
		break;
		case PID.SyncShip_Emo: //entity emotion only
			this.valueByte1 = PacketHelper.readByteArray(buf, 7);
			this.valueBoolean1 = PacketHelper.readBooleanArray(buf, 1);
		break;
		case PID.SyncShip_Flag: //entity flag only
			this.valueBoolean1 = PacketHelper.readBooleanArray(buf, 17);
		break;
		case PID.SyncShip_Formation: //ship formation data only
			this.valueInt1 = PacketHelper.readIntArray(buf, 7);
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 1);
		break;
		case PID.SyncShip_Minor: //entity minor only
			this.valueInt1 = PacketHelper.readIntArray(buf, 28);
		break;
		case PID.SyncShip_Guard:  //sync guard for particle display
			this.valueInt1 = PacketHelper.readIntArray(buf, 6);
		break;
		case PID.SyncShip_ID:
			this.valueInt1 = PacketHelper.readIntArray(buf, 3);
		break;
		case PID.SyncShip_Unbuff:	//sync unbuff attr
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 9+7);
		break;
		case PID.SyncShip_Timer: //ship timer only
			this.valueInt = buf.readInt();
		break;
		case PID.SyncEntity_Emo: //IShipEmotion sync emtion
			this.valueByte1 = PacketHelper.readByteArray(buf, 6);
		break;
		case PID.SyncMount_AllRider: //player mount sync (only send when player right click on mount)
			this.valueInt = buf.readInt();
			if (this.valueInt > 0) this.valueInt1 = PacketHelper.readIntArray(buf, this.valueInt);
		break;
		case PID.SyncProjectile:	//missile type sync
			this.valueInt = buf.readInt();
		break;
		case PID.SyncEntity_PosRot:	//entity position sync
			this.valueDouble1 = PacketHelper.readDoubleArray(buf, 3);
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 2);
		break;
		case PID.SyncEntity_Rot:	//entity rotation sync TODO need sync?
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 2);
		break;
		case PID.SyncEntity_Motion:	//entity motion sync
			this.valueFloat1 = PacketHelper.readFloatArray(buf, 3);
		break;
		}//end switch
		
	}

	//發出packet方法
	@Override
	public void toBytes(ByteBuf buf)
	{
		//send packet and entity id
		buf.writeByte(this.packetType);
		buf.writeInt(entity.getEntityId());
		
		switch (this.packetType)
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
			buf.writeInt(this.valueInt);
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
	
	//packet handle method
	private static void handle(S2CEntitySync msg, MessageContext ctx)
	{
		boolean getTarget = false;
		
		//get target entity
		Entity entity = EntityHelper.getEntityByID(msg.entityID, 0, true);
		BasicEntityShip ship;
		
		switch(msg.packetType)
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
			if (entity instanceof BasicEntityShip ||
				entity instanceof IShipEmotion ||
				entity instanceof EntityLiving)
			{
				getTarget = true;
			}
		break;
		case PID.SyncMount_AllRider:
			if (entity instanceof BasicEntityMount) getTarget = true;
		break;
		case PID.SyncProjectile:	//missile sync
		case PID.SyncEntity_PosRot: //entity position, rotation, motion sync
		case PID.SyncEntity_Rot:
		case PID.SyncEntity_Motion:
			if (entity != null) getTarget = true;
		break;
		}

		//get entity to sync
		if (getTarget)
		{
			switch (msg.packetType)
			{
			case PID.SyncShip_All:	//sync all attr
			{
				ship = (BasicEntityShip) entity;
				
				ship.setStateMinor(ID.M.ShipLevel, msg.valueInt1[0]);
				ship.setStateMinor(ID.M.Kills, msg.valueInt1[1]);
				ship.setStateMinor(ID.M.ExpCurrent, msg.valueInt1[2]);
				ship.setStateMinor(ID.M.NumAmmoLight, msg.valueInt1[3]);
				ship.setStateMinor(ID.M.NumAmmoHeavy, msg.valueInt1[4]);
				ship.setStateMinor(ID.M.NumGrudge, msg.valueInt1[5]);
				ship.setStateMinor(ID.M.NumAirLight, msg.valueInt1[6]);
				ship.setStateMinor(ID.M.NumAirHeavy, msg.valueInt1[7]);
				ship.setStateMinor(ID.M.FollowMin, msg.valueInt1[8]);
				ship.setStateMinor(ID.M.FollowMax, msg.valueInt1[9]);
				ship.setStateMinor(ID.M.FleeHP, msg.valueInt1[10]);
				ship.setStateMinor(ID.M.GuardX, msg.valueInt1[11]);
				ship.setStateMinor(ID.M.GuardY, msg.valueInt1[12]);
				ship.setStateMinor(ID.M.GuardZ, msg.valueInt1[13]);
				ship.setStateMinor(ID.M.GuardDim, msg.valueInt1[14]);
				ship.setStateMinor(ID.M.GuardID, msg.valueInt1[15]);
				ship.setStateMinor(ID.M.GuardType, msg.valueInt1[16]);
				ship.setStateMinor(ID.M.PlayerUID, msg.valueInt1[17]);
				ship.setStateMinor(ID.M.ShipUID, msg.valueInt1[18]);
				ship.setStateMinor(ID.M.PlayerEID, msg.valueInt1[19]);
				ship.setStateMinor(ID.M.FormatType, msg.valueInt1[20]);
				ship.setStateMinor(ID.M.FormatPos, msg.valueInt1[21]);
				ship.setStateMinor(ID.M.Morale, msg.valueInt1[22]);
				ship.setStateMinor(ID.M.InvSize, msg.valueInt1[23]);
				ship.setStateMinor(ID.M.LevelChunkLoader, msg.valueInt1[24]);
				ship.setStateMinor(ID.M.LevelFlare, msg.valueInt1[25]);
				ship.setStateMinor(ID.M.LevelSearchlight, msg.valueInt1[26]);
				ship.setStateMinor(ID.M.WpStay, msg.valueInt1[27]);
				ship.setStateTimer(ID.T.CraneTime, msg.valueInt1[28]);
				
				ship.setStateFinal(ID.HP, msg.valueFloat1[0]);
				ship.setStateFinal(ID.ATK, msg.valueFloat1[1]);
				ship.setStateFinal(ID.DEF, msg.valueFloat1[2]);
				ship.setStateFinal(ID.SPD, msg.valueFloat1[3]);
				ship.setStateFinal(ID.MOV, msg.valueFloat1[4]);
				ship.setStateFinal(ID.HIT, msg.valueFloat1[5]);
				ship.setStateFinal(ID.ATK_H, msg.valueFloat1[6]);
				ship.setStateFinal(ID.ATK_AL, msg.valueFloat1[7]);
				ship.setStateFinal(ID.ATK_AH, msg.valueFloat1[8]);
				ship.setEffectEquip(ID.EF_CRI, msg.valueFloat1[9]);
				ship.setEffectEquip(ID.EF_DHIT, msg.valueFloat1[10]);
				ship.setEffectEquip(ID.EF_THIT, msg.valueFloat1[11]);
				ship.setEffectEquip(ID.EF_MISS, msg.valueFloat1[12]);
				ship.setEffectEquip(ID.EF_AA, msg.valueFloat1[13]);
				ship.setEffectEquip(ID.EF_ASM, msg.valueFloat1[14]);
				ship.setEffectEquip(ID.EF_DODGE, msg.valueFloat1[15]);
				ship.setEffectFormation(ID.Formation.ATK_L, msg.valueFloat1[16]);
				ship.setEffectFormation(ID.Formation.ATK_H, msg.valueFloat1[17]);
				ship.setEffectFormation(ID.Formation.ATK_AL, msg.valueFloat1[18]);
				ship.setEffectFormation(ID.Formation.ATK_AH, msg.valueFloat1[19]);
				ship.setEffectFormation(ID.Formation.DEF, msg.valueFloat1[20]);
				ship.setEffectFormation(ID.Formation.MOV, msg.valueFloat1[21]);
				ship.setEffectFormation(ID.Formation.MISS, msg.valueFloat1[22]);
				ship.setEffectFormation(ID.Formation.DODGE, msg.valueFloat1[23]);
				ship.setEffectFormation(ID.Formation.CRI, msg.valueFloat1[24]);
				ship.setEffectFormation(ID.Formation.DHIT, msg.valueFloat1[25]);
				ship.setEffectFormation(ID.Formation.THIT, msg.valueFloat1[26]);
				ship.setEffectFormation(ID.Formation.AA, msg.valueFloat1[27]);
				ship.setEffectFormation(ID.Formation.ASM, msg.valueFloat1[28]);
				ship.setEffectFormationFixed(ID.FormationFixed.MOV, msg.valueFloat1[29]);
				
				ship.setStateEmotion(ID.S.State, msg.valueByte1[0], false);
				ship.setStateEmotion(ID.S.State2, msg.valueByte1[1], false);
				ship.setStateEmotion(ID.S.Emotion, msg.valueByte1[2], false);
				ship.setStateEmotion(ID.S.Emotion2, msg.valueByte1[3], false);
				ship.setStateEmotion(ID.S.HPState, msg.valueByte1[4], false);
				ship.setStateEmotion(ID.S.Phase, msg.valueByte1[5], false);
				ship.setStateEmotion(ID.S.Emotion3, msg.valueByte1[6], false);
				ship.setBonusPoint(ID.HP, msg.valueByte1[7]);
				ship.setBonusPoint(ID.ATK, msg.valueByte1[8]);
				ship.setBonusPoint(ID.DEF, msg.valueByte1[9]);
				ship.setBonusPoint(ID.SPD, msg.valueByte1[10]);
				ship.setBonusPoint(ID.MOV, msg.valueByte1[11]);
				ship.setBonusPoint(ID.HIT, msg.valueByte1[12]);

				ship.setStateFlag(ID.F.CanFloatUp, msg.valueBoolean1[0]);
				ship.setStateFlag(ID.F.IsMarried, msg.valueBoolean1[1]);
				ship.setStateFlag(ID.F.NoFuel, msg.valueBoolean1[2]);
				ship.setStateFlag(ID.F.UseMelee, msg.valueBoolean1[3]);
				ship.setStateFlag(ID.F.UseAmmoLight, msg.valueBoolean1[4]);
				ship.setStateFlag(ID.F.UseAmmoHeavy, msg.valueBoolean1[5]);
				ship.setStateFlag(ID.F.UseAirLight, msg.valueBoolean1[6]);
				ship.setStateFlag(ID.F.UseAirHeavy, msg.valueBoolean1[7]);
				ship.setStateFlag(ID.F.UseRingEffect, msg.valueBoolean1[8]);
				ship.setStateFlag(ID.F.OnSightChase, msg.valueBoolean1[9]);
				ship.setStateFlag(ID.F.PVPFirst, msg.valueBoolean1[10]);
				ship.setStateFlag(ID.F.AntiAir, msg.valueBoolean1[11]);
				ship.setStateFlag(ID.F.AntiSS, msg.valueBoolean1[12]);
				ship.setStateFlag(ID.F.PassiveAI, msg.valueBoolean1[13]);
				ship.setStateFlag(ID.F.TimeKeeper, msg.valueBoolean1[14]);
				ship.setStateFlag(ID.F.PickItem, msg.valueBoolean1[15]);
				ship.setStateFlag(ID.F.CanPickItem, msg.valueBoolean1[16]);
			}
			break;
			case PID.SyncShip_Emo: //entity emotion only
			{
				ship = (BasicEntityShip) entity;
				
				ship.setStateEmotion(ID.S.State, msg.valueByte1[0], false);
				ship.setStateEmotion(ID.S.State2, msg.valueByte1[1], false);
				ship.setStateEmotion(ID.S.Emotion, msg.valueByte1[2], false);
				ship.setStateEmotion(ID.S.Emotion2, msg.valueByte1[3], false);
				ship.setStateEmotion(ID.S.HPState, msg.valueByte1[4], false);
				ship.setStateEmotion(ID.S.Phase, msg.valueByte1[5], false);
				ship.setStateEmotion(ID.S.Emotion3, msg.valueByte1[6], false);
				
				ship.setStateFlag(ID.F.NoFuel, msg.valueBoolean1[0]);
			}
			break;
			case PID.SyncShip_Flag: //entity flag only
			{
				ship = (BasicEntityShip) entity;
				
				ship.setStateFlag(ID.F.CanFloatUp, msg.valueBoolean1[0]);
				ship.setStateFlag(ID.F.IsMarried, msg.valueBoolean1[1]);
				ship.setStateFlag(ID.F.NoFuel, msg.valueBoolean1[2]);
				ship.setStateFlag(ID.F.UseMelee, msg.valueBoolean1[3]);
				ship.setStateFlag(ID.F.UseAmmoLight, msg.valueBoolean1[4]);
				ship.setStateFlag(ID.F.UseAmmoHeavy, msg.valueBoolean1[5]);
				ship.setStateFlag(ID.F.UseAirLight, msg.valueBoolean1[6]);
				ship.setStateFlag(ID.F.UseAirHeavy, msg.valueBoolean1[7]);
				ship.setStateFlag(ID.F.UseRingEffect, msg.valueBoolean1[8]);
				ship.setStateFlag(ID.F.OnSightChase, msg.valueBoolean1[9]);
				ship.setStateFlag(ID.F.PVPFirst, msg.valueBoolean1[10]);
				ship.setStateFlag(ID.F.AntiAir, msg.valueBoolean1[11]);
				ship.setStateFlag(ID.F.AntiSS, msg.valueBoolean1[12]);
				ship.setStateFlag(ID.F.PassiveAI, msg.valueBoolean1[13]);
				ship.setStateFlag(ID.F.TimeKeeper, msg.valueBoolean1[14]);
				ship.setStateFlag(ID.F.PickItem, msg.valueBoolean1[15]);
				ship.setStateFlag(ID.F.CanPickItem, msg.valueBoolean1[16]);
			}
			break;
			case PID.SyncShip_Formation: //ship formation data only
			{
				ship = (BasicEntityShip) entity;
				
				ship.setStateMinor(ID.M.GuardX, msg.valueInt1[0]);
				ship.setStateMinor(ID.M.GuardY, msg.valueInt1[1]);
				ship.setStateMinor(ID.M.GuardZ, msg.valueInt1[2]);
				ship.setStateMinor(ID.M.GuardDim, msg.valueInt1[3]);
				ship.setStateMinor(ID.M.GuardType, msg.valueInt1[4]);
				ship.setStateMinor(ID.M.FormatType, msg.valueInt1[5]);
				ship.setStateMinor(ID.M.FormatPos, msg.valueInt1[6]);
				ship.setEffectFormationFixed(ID.FormationFixed.MOV, msg.valueFloat1[0]);
			}
			break;
			case PID.SyncShip_Minor: //entity minor only
			{
				ship = (BasicEntityShip) entity;
				
				ship.setStateMinor(ID.M.ShipLevel, msg.valueInt1[0]);
				ship.setStateMinor(ID.M.Kills, msg.valueInt1[1]);
				ship.setStateMinor(ID.M.ExpCurrent, msg.valueInt1[2]);
				ship.setStateMinor(ID.M.NumAmmoLight, msg.valueInt1[3]);
				ship.setStateMinor(ID.M.NumAmmoHeavy, msg.valueInt1[4]);
				ship.setStateMinor(ID.M.NumGrudge, msg.valueInt1[5]);
				ship.setStateMinor(ID.M.NumAirLight, msg.valueInt1[6]);
				ship.setStateMinor(ID.M.NumAirHeavy, msg.valueInt1[7]);
				ship.setStateMinor(ID.M.FollowMin, msg.valueInt1[8]);
				ship.setStateMinor(ID.M.FollowMax, msg.valueInt1[9]);
				ship.setStateMinor(ID.M.FleeHP, msg.valueInt1[10]);
				ship.setStateMinor(ID.M.GuardX, msg.valueInt1[11]);
				ship.setStateMinor(ID.M.GuardY, msg.valueInt1[12]);
				ship.setStateMinor(ID.M.GuardZ, msg.valueInt1[13]);
				ship.setStateMinor(ID.M.GuardDim, msg.valueInt1[14]);
				ship.setStateMinor(ID.M.GuardID, msg.valueInt1[15]);
				ship.setStateMinor(ID.M.GuardType, msg.valueInt1[16]);
				ship.setStateMinor(ID.M.PlayerUID, msg.valueInt1[17]);
				ship.setStateMinor(ID.M.ShipUID, msg.valueInt1[18]);
				ship.setStateMinor(ID.M.PlayerEID, msg.valueInt1[19]);
				ship.setStateMinor(ID.M.FormatType, msg.valueInt1[20]);
				ship.setStateMinor(ID.M.FormatPos, msg.valueInt1[21]);
				ship.setStateMinor(ID.M.Morale, msg.valueInt1[22]);
				ship.setStateMinor(ID.M.InvSize, msg.valueInt1[23]);
				ship.setStateMinor(ID.M.LevelChunkLoader, msg.valueInt1[24]);
				ship.setStateMinor(ID.M.LevelFlare, msg.valueInt1[25]);
				ship.setStateMinor(ID.M.LevelSearchlight, msg.valueInt1[26]);
				ship.setStateMinor(ID.M.WpStay, msg.valueInt1[27]);
			}
			break;
			case PID.SyncShip_Guard:  //sync guard for particle display
			{
				ship = (BasicEntityShip) entity;
				
				ship.setStateMinor(ID.M.GuardX, msg.valueInt1[0]);
				ship.setStateMinor(ID.M.GuardY, msg.valueInt1[1]);
				ship.setStateMinor(ID.M.GuardZ, msg.valueInt1[2]);
				ship.setStateMinor(ID.M.GuardDim, msg.valueInt1[3]);
				ship.setStateMinor(ID.M.GuardID, msg.valueInt1[4]);
				ship.setStateMinor(ID.M.GuardType, msg.valueInt1[5]);
			}
			break;
			case PID.SyncShip_ID:
			{
				ship = (BasicEntityShip) entity;
				
				ship.setStateMinor(ID.M.PlayerUID, msg.valueInt1[0]);
				ship.setStateMinor(ID.M.ShipUID, msg.valueInt1[1]);
				ship.setStateMinor(ID.M.PlayerEID, msg.valueInt1[2]);
			}
			break;
			case PID.SyncShip_Unbuff:	//sync unbuff attr
			{
				ship = (BasicEntityShip) entity;
				
				ship.setStateFinalBU(ID.HP, msg.valueFloat1[0]);
				ship.setStateFinalBU(ID.ATK, msg.valueFloat1[1]);
				ship.setStateFinalBU(ID.DEF, msg.valueFloat1[2]);
				ship.setStateFinalBU(ID.SPD, msg.valueFloat1[3]);
				ship.setStateFinalBU(ID.MOV, msg.valueFloat1[4]);
				ship.setStateFinalBU(ID.HIT, msg.valueFloat1[5]);
				ship.setStateFinalBU(ID.ATK_H, msg.valueFloat1[6]);
				ship.setStateFinalBU(ID.ATK_AL, msg.valueFloat1[7]);
				ship.setStateFinalBU(ID.ATK_AH, msg.valueFloat1[8]);
				ship.setEffectEquipBU(ID.EF_CRI, msg.valueFloat1[9]);
				ship.setEffectEquipBU(ID.EF_DHIT, msg.valueFloat1[10]);
				ship.setEffectEquipBU(ID.EF_THIT, msg.valueFloat1[11]);
				ship.setEffectEquipBU(ID.EF_MISS, msg.valueFloat1[12]);
				ship.setEffectEquipBU(ID.EF_AA, msg.valueFloat1[13]);
				ship.setEffectEquipBU(ID.EF_ASM, msg.valueFloat1[14]);
				ship.setEffectEquipBU(ID.EF_DODGE, msg.valueFloat1[15]);
			}
			break;
			case PID.SyncShip_Timer: //ship timer only
			{
				ship = (BasicEntityShip) entity;
				
				ship.setStateTimer(ID.T.CraneTime, msg.valueInt);
			}
			break;
			case PID.SyncEntity_Emo: //IShipEmotion sync emtion
			{
				IShipEmotion entity2 = (IShipEmotion) entity;
				
				entity2.setStateEmotion(ID.S.State, msg.valueByte1[0], false);
				entity2.setStateEmotion(ID.S.State2, msg.valueByte1[1], false);
				entity2.setStateEmotion(ID.S.Emotion, msg.valueByte1[2], false);
				entity2.setStateEmotion(ID.S.Emotion2, msg.valueByte1[3], false);
				entity2.setStateEmotion(ID.S.HPState, msg.valueByte1[4], false);
				entity2.setStateEmotion(ID.S.Phase, msg.valueByte1[5], false);
			}
			break;
			case PID.SyncMount_AllRider: //player mount sync (only send when player right click on mount)
			{
				//get rider to sync
				if (msg.valueInt > 0)
				{
					//set mount
					for (int i = 0; i < msg.valueInt; i++)
					{
						Entity ent = EntityHelper.getEntityByID(msg.valueInt1[i], 0, true);
						if (ent != null) ent.startRiding(entity, true);
					}

					//set mount pose if riders > 1
					if (msg.valueInt > 1) ((BasicEntityMount) entity).setStateEmotion(ID.S.Emotion, 1, false);
				}
			}
			break;
			case PID.SyncProjectile:	//missile type sync
			{
				if (entity instanceof EntityAbyssMissile)
				{
					((EntityAbyssMissile)entity).setMissileType(msg.valueInt);
				}
				else if (entity instanceof EntityProjectileBeam)
				{
					((EntityProjectileBeam)entity).setProjectileType(msg.valueInt);
				}
			}
			break;
			case PID.SyncEntity_PosRot:	//entity position sync
			{
				entity.setPositionAndRotation(msg.valueDouble1[0], msg.valueDouble1[1],
						msg.valueDouble1[2], msg.valueFloat1[0], msg.valueFloat1[1]);
			}
			break;
			case PID.SyncEntity_Rot:	//entity rotation sync TODO need sync?
			{
				if (entity instanceof EntityLivingBase)
				{
					((EntityLivingBase) entity).rotationYawHead = msg.valueFloat1[0];
					entity.rotationPitch = msg.valueFloat1[1];
				}
				else
				{
					entity.rotationYaw = msg.valueFloat1[0];
					entity.rotationPitch = msg.valueFloat1[1];
				}
				
				if (entity.getRidingEntity() instanceof BasicEntityMount)
				{
					((BasicEntityMount) entity.getRidingEntity()).rotationYawHead = msg.valueFloat1[0];
					((BasicEntityMount) entity.getRidingEntity()).rotationYaw = msg.valueFloat1[1];
				}
			}
			break;
			case PID.SyncEntity_Motion:	//entity motion sync
			{
				entity.setVelocity(msg.valueFloat1[0], msg.valueFloat1[1], msg.valueFloat1[2]);
			}
			break;
			}//end switch
		}//end can sync
		else
		{
			LogHelper.info("DEBUG : packet handler: S2CEntitySync: entity is null, type: "+
							msg.packetType+" eid: "+msg.entityID);
		}
	}
	
	//packet handler (inner class)
	public static class Handler implements IMessageHandler<S2CEntitySync, IMessage>
	{
		//收到封包時顯示debug訊息
		@Override
		public IMessage onMessage(S2CEntitySync message, MessageContext ctx)
		{
			/**
			 * 1.8之後minecraft主程式分為minecraft server/clinet跟networking兩個thread執行
			 * 因此handler這邊必須使用addScheduledTask將封包處理方法加入到並行控制佇列中處理
			 * 以避免多執行緒下各種並行處理問題
			 */
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> S2CEntitySync.handle(message, ctx));
			return null;
		}

    }
	
	
}
