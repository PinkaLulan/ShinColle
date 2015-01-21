package com.lulan.shincolle.entity;

import java.util.Random;
import java.util.UUID;

import com.lulan.shincolle.inventory.ShipInventory;
import com.lulan.shincolle.network.createPacketS2C;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.AttrValues;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**SHIP DATA <br>
 * attribute array (for performance) <br> <br>
 * 
 * AttrBonusShort: 0:ShipBonusHP 1:ShipBonusATK 2:ShipBonusDEF <br>
 * AttrBonusFloat: 0:ShipBonusSPD 1:ShipBonusMOV 2:ShipBonusHIT <br>
 * AttrFinalShort: 0:ShipFinalHP 1:ShipFinalATK 2:ShipFinalDEF <br>
 * AttrFinalFloat: 0:ShipFinalSPD 1:ShipFinalMOV 2:ShipFinalHIT <br>
 * EntityState: 0:State 1:Emotion 2:SwimType <br> <br>
 * 
 * Final HP = (Base + Bonus * Level) * config HP scale <br>
 * Final ATK/DEF = Base + Bonus * Level + Eqiuip <br>
 * Final AttackSpeed/MovementSpeed/HitRate = BaseSPD + BonusSPD <br>
 * 
 * NYI: Hit Rate for Hit AI
 */
public abstract class BasicEntityShip extends EntityTameable {

	public Random rand;
	public ExtendShipProps ExtProps;		//entity額外NBT紀錄
	//for attribute calc
	public short ShipLevel;				//ship level
	public int Kills;					//kill mobs (= exp)
	public byte ShipType;				//ship type
	//AttrBonusShort: 0:ShipBonusHP 1:ShipBonusATK 2:ShipBonusDEF
	public short[] AttrBonusShort;
	//AttrBonusFloat: 0:ShipBonusSPD 1:ShipBonusMOV 2:ShipBonusHIT
	public float[] AttrBonusFloat;
	//AttrFinalShort: 0:ShipFinalHP 1:ShipFinalATK 2:ShipFinalDEF
	public short[] AttrFinalShort;
	//AttrFinalFloat: 0:ShipFinalSPD 1:ShipFinalMOV 2:ShipFinalHIT
	public float[] AttrFinalFloat;
	//EntityState: 0:State 1:Emotion 2:SwimType
	public byte[] EntityState;
	//for AI
	public int StartEmotion;			//表情開始時間
	public String BlockUnderName;		//腳下方塊名稱, 不需要存NBT
	
	
	public BasicEntityShip(World world) {
		super(world);
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
	
	/**GETTER: no getter (for performance)
	 * SETTER: use setter for sync value between server & client	
	 * 		   (only for variables that need to sync)
	 */
	//called when entity level up
	public void setShipLevel(short par1, boolean sync) {
		ShipLevel = par1;		
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
	
	//called when entity spawn
	public void setAttrBonus(short[] par1, float[] par2, boolean sync) {
		AttrBonusShort[AttrID.HP] = par1[AttrID.HP];
		AttrBonusShort[AttrID.ATK] = par1[AttrID.ATK];
		AttrBonusShort[AttrID.DEF] = par1[AttrID.DEF];
		
		AttrBonusFloat[AttrID.SPD] = par2[AttrID.SPD];
		AttrBonusFloat[AttrID.MOV] = par2[AttrID.MOV];
		AttrBonusFloat[AttrID.HIT] = par2[AttrID.HIT];
		
		if (sync && !worldObj.isRemote) {
			createPacketS2C.sendS2CEntitySync(this);     
		}
	}
	
	//called when entity spawn/level up
	public void setAttrFinal(short[] par1, float[] par2, boolean sync) {
		AttrFinalShort[AttrID.HP] = par1[AttrID.HP];
		AttrFinalShort[AttrID.ATK] = par1[AttrID.ATK];
		AttrFinalShort[AttrID.DEF] = par1[AttrID.DEF];
		
		AttrFinalFloat[AttrID.SPD] = par2[AttrID.SPD];
		AttrFinalFloat[AttrID.MOV] = par2[AttrID.MOV];
		AttrFinalFloat[AttrID.HIT] = par2[AttrID.HIT];
		
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
