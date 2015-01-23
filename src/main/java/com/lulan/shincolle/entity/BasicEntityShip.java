package com.lulan.shincolle.entity;

import java.util.Random;
import java.util.UUID;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.inventory.ShipInventory;
import com.lulan.shincolle.network.createPacketS2C;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.AttrValues;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**SHIP DATA <br>
 * Explanation in crafting/ShipCalc.class
 */
public abstract class BasicEntityShip extends EntityTameable {

	public Random rand;
	public ExtendShipProps ExtProps;		//entity額外NBT紀錄
	//for attribute calc
	public short ShipLevel;				//ship level
	public int Kills;					//kill mobs (= exp)
	public byte ShipType;				//ship type
	public byte ShipID;
	//AttrEquipShort: 0:ShipEquipHP 1:ShipEquipATK 2:ShipEquipDEF
	public short[] AttrEquipShort;
	//AttrEquipFloat: 0:ShipEquipSPD 1:ShipEquipMOV 2:ShipEquipHIT
	public float[] AttrEquipFloat;
	//AttrFinalShort: 0:ShipFinalHP 1:ShipFinalATK 2:ShipFinalDEF
	public short[] AttrFinalShort;
	//AttrFinalFloat: 0:ShipFinalSPD 1:ShipFinalMOV 2:ShipFinalHIT
	public float[] AttrFinalFloat;
	//EntityState: 0:State 1:Emotion 2:SwimType
	public byte[] EntityState;
	//BonusPoint: 0:HP 1:ATK 2:DEF 3:SPD 4:MOV 5:HIT
	public byte[] BonusPoint;
	//TypeModify: 0:HP 1:ATK 2:DEF 3:SPD 4:MOV 5:HIT
	public float[] TypeModify;
	//for AI
	public int StartEmotion;			//表情開始時間
	public String BlockUnderName;		//腳下方塊名稱, 不需要存NBT
	
	
	public BasicEntityShip(World world) {
		super(world);
		ShipLevel = 1;				//ship level
		Kills = 0;					//kill mobs (= exp)
		//AttrEquipShort: 0:ShipEquipHP 1:ShipEquipATK 2:ShipEquipDEF
		AttrEquipShort = new short[] {0, 0, 0};
		//AttrEquipFloat: 0:ShipEquipSPD 1:ShipEquipMOV 2:ShipEquipHIT
		AttrEquipFloat = new float[] {0F, 0F, 0F};
		//AttrFinalShort: 0:ShipFinalHP 1:ShipFinalATK 2:ShipFinalDEF
		AttrFinalShort = new short[3];
		//AttrFinalFloat: 0:ShipFinalSPD 1:ShipFinalMOV 2:ShipFinalHIT
		AttrFinalFloat = new float[3];
		//EntityState: 0:State 1:Emotion 2:SwimType
		EntityState = new byte[] {0, 0, 0};
		//BonusPoint: 0:HP 1:ATK 2:DEF 3:SPD 4:MOV 5:HIT
		BonusPoint = new byte[] {0, 0, 0, 0, 0, 0};
		//TypeModify: 0:HP 1:ATK 2:DEF 3:SPD 4:MOV 5:HIT
		TypeModify = new float[] {1F, 1F, 1F, 1F, 1F, 1F};
		//for AI
		StartEmotion = 0;			//表情開始時間
		BlockUnderName = "";		//腳下方塊名稱, 不需要存NBT
	}
	
	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		return null;
	}
	
	public void setOwner(String string) {
		 this.dataWatcher.updateObject(17, string);
	}
	
	public String getOwnerName() {
		 return this.dataWatcher.getWatchableObjectString(17);
	}
	
	//setting attributes, called at load nbt data & init mob
	public void setShipAttributes(byte id) {
		//init or renew bonus value, for short value: discard decimal
		//HP = (base + (point + 1) * level * typeModify) * config HP ratio
		AttrFinalShort[AttrID.HP] = (short) (((float)AttrValues.BaseHP[id] + (float)(BonusPoint[AttrID.HP]+1) * (float)ShipLevel * TypeModify[AttrID.HP]) * ConfigHandler.hpRatio); 
		//ATK = base + ((point + 1) * level / 3 + equip) * typeModify
		AttrFinalShort[AttrID.ATK] = (short) ((float)AttrValues.BaseATK[id] + ((float)(BonusPoint[AttrID.ATK]+1) * (((float)ShipLevel)/3F) + (float)AttrEquipShort[AttrID.ATK]) * TypeModify[AttrID.ATK]);
		//DEF = base + ((point + 1) * level / 5 * 0.15 + equip) * typeModify
		AttrFinalShort[AttrID.DEF] = (short) ((float)AttrValues.BaseDEF[id] + ((float)(BonusPoint[AttrID.DEF]+1) * (((float)ShipLevel)/5F) * 0.15F + (float)AttrEquipShort[AttrID.DEF]) * TypeModify[AttrID.DEF]);
		//SPD = base + ((point + 1) * level / 10 * 0.02 + equip) * typeModify
		AttrFinalFloat[AttrID.SPD] = AttrValues.BaseSPD[id] + ((float)(BonusPoint[AttrID.SPD]+1) * (((float)ShipLevel)/10F) * 0.02F + AttrEquipFloat[AttrID.SPD]) * TypeModify[AttrID.SPD+3];
		//MOV = base + ((point + 1) * level / 10 * 0.01 + equip) * typeModify
		AttrFinalFloat[AttrID.MOV] = AttrValues.BaseMOV[id] + ((float)(BonusPoint[AttrID.MOV]+1) * (((float)ShipLevel)/10F) * 0.01F + AttrEquipFloat[AttrID.MOV]) * TypeModify[AttrID.MOV+3];
		//HIT = base + ((point + 1) * level / 10 * 0.8 + equip) * typeModify
		AttrFinalFloat[AttrID.HIT] = AttrValues.BaseHIT[id] + ((float)(BonusPoint[AttrID.HIT]+1) * (((float)ShipLevel)/10F) * 0.8F + AttrEquipFloat[AttrID.HIT]) * TypeModify[AttrID.HIT+3];
		//KB Resistance = Level / 10 * 0.04
		float resisKB = (((float)ShipLevel)/10F) * 0.04F;
		
		//set attribute by final value	
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(AttrFinalShort[AttrID.HP]);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(AttrFinalFloat[AttrID.MOV]);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(resisKB);
		
		sendSyncPacket();	//sync nbt data
	}
	
	/**GETTER: no getter
	 * SETTER: use setter for sync value between server & client	
	 * 		   (only for variables that need to sync)
	 */	
	//called when entity level up
	public void setShipLevel(short par1, boolean sync) {
		ShipLevel = par1;
		setShipAttributes(ShipID);
		if (sync && !worldObj.isRemote) {
			createPacketS2C.sendS2CEntitySync(this);     
		}
	}
	
	//called when a mob die near the entity
	public void setKills(int par1, boolean sync) {
		Kills = par1;		
		if (sync && !worldObj.isRemote) {
			createPacketS2C.sendS2CEntitySync(this);     
		}
	}
	
	//called when entity equip
	public void setAttrEquip(short[] par1, float[] par2, boolean sync) {
		AttrEquipShort[AttrID.HP] = par1[AttrID.HP];
		AttrEquipShort[AttrID.ATK] = par1[AttrID.ATK];
		AttrEquipShort[AttrID.DEF] = par1[AttrID.DEF];
		
		AttrEquipFloat[AttrID.SPD] = par2[AttrID.SPD];
		AttrEquipFloat[AttrID.MOV] = par2[AttrID.MOV];
		AttrEquipFloat[AttrID.HIT] = par2[AttrID.HIT];
		
		if (sync && !worldObj.isRemote) {
			createPacketS2C.sendS2CEntitySync(this);     
		}
	}
	
	//called when entity AI/HP change
	public void setEntityState(byte[] par1, boolean sync) {
		EntityState[AttrID.State] = par1[AttrID.State];
		EntityState[AttrID.Emotion] = par1[AttrID.Emotion];
		EntityState[AttrID.SwimType] = par1[AttrID.SwimType];
		
		if (sync && !worldObj.isRemote) {
			createPacketS2C.sendS2CEntitySync(this);     
		}
	}
	
	//manual send sync packet
	public void sendSyncPacket() {
		if (!worldObj.isRemote) {
			createPacketS2C.sendS2CEntitySync(this);     
		}
	}
	

	
}
