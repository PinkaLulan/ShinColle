package com.lulan.shincolle.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.ai.EntityAIShipInRangeTarget;
import com.lulan.shincolle.ai.EntityAIShipSit;
import com.lulan.shincolle.client.inventory.ContainerShipInventory;
import com.lulan.shincolle.client.particle.EntityFXSpray;
import com.lulan.shincolle.client.particle.EntityFXTexts;
import com.lulan.shincolle.crafting.EquipCalc;
import com.lulan.shincolle.entity.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.IExtendedEntityProperties;

/**SHIP DATA <br>
 * Explanation in crafting/ShipCalc.class
 */
public abstract class BasicEntityShip extends EntityTameable implements IShipAttack, IShipEmotion, IShipFloating {

	protected ExtendShipProps ExtProps;	//entity額外NBT紀錄
	
	//for attribute calc
	protected byte ShipType;			//ship type
	protected byte ShipID;
	//for AI calc
	protected int StartEmotion;			//表情1開始時間
	protected int StartEmotion2;		//表情2開始時間
	protected double ShipDepth;			//水深, 用於水中高度判定
	protected double ShipPrevX;			//ship posX 5 sec ago
	protected double ShipPrevY;			//ship posY 5 sec ago
	protected double ShipPrevZ;			//ship posZ 5 sec ago
	/**equip states: 0:HP 1:ATK 2:DEF 3:SPD 4:MOV 5:HIT 6:ATK_Heavy 7:ATK_AirLight 8:ATK_AirHeavy*/
	protected float[] StateEquip;
	/**final states: 0:HP 1:ATK 2:DEF 3:SPD 4:MOV 5:HIT 6:ATK_Heavy 7:ATK_AirLight 8:ATK_AirHeavy*/
	protected float[] StateFinal;
	/**minor states: 0:ShipLevel 1:Kills 2:ExpCurrent 3:ExpNext 4:NumAmmoLight 
	 * 5:NumAmmoHeavy 6:NumGrudge 7:NumAirLight 8:NumAirHeavy 9:immunity time 
	 * 10:followMin 11:followMax 12:FleeHP 13:TargetAIType*/
	protected int[] StateMinor;
	/**equip effect: 0:critical 1:doubleHit 2:tripleHit 3:baseMiss*/
	protected float[] EffectEquip;
	/**EntityState: 0:State 1:Emotion 2:Emotion2 3:HP State*/
	protected byte[] StateEmotion;
	/**EntityFlag: 0:canFloatUp 1:isMarried 2:noFuel 3:canMelee 4:canAmmoLight 5:canAmmoHeavy 6:canAirLight 7:canAirHeavy 8:headTilt(client only) 9:canRingEffect*/
	protected boolean[] StateFlag;
	/**BonusPoint: 0:HP 1:ATK 2:DEF 3:SPD 4:MOV 5:HIT*/
	protected byte[] BonusPoint;
	/**TypeModify: 0:HP 1:ATK 2:DEF 3:SPD 4:MOV 5:HIT*/
	protected float[] TypeModify;
	/**ModelPos: posX, posY, posZ, scale (in ship inventory)*/
	protected float[] ModelPos;
	
	//for GUI display
	protected String ownerName;
	
	
	public BasicEntityShip(World world) {
		super(world);
		//init value
		isImmuneToFire = true;	//set ship immune to lava
		StateEquip = new float[] {0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
		StateFinal = new float[] {0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
		StateMinor = new int[] {1, 0, 0, 40, 0, 0, 0, 0, 0, 0, 2, 14, 35, 1};
		EffectEquip = new float[] {0F, 0F, 0F, 0F};
		StateEmotion = new byte[] {0, 0, 0, 0};
		StateFlag = new boolean[] {false, false, false, false, true, true, true, true, false, true};
		BonusPoint = new byte[] {0, 0, 0, 0, 0, 0};
		TypeModify = new float[] {1F, 1F, 1F, 1F, 1F, 1F};
		ModelPos = new float[] {0F, 0F, 0F, 50F};
		//for AI
		StartEmotion = -1;		//emotion start time
		ShipDepth = 0D;			//water block above ship (within ship position)
		ShipPrevX = posX;		//ship position 5 sec ago
		ShipPrevY = posY;
		ShipPrevZ = posZ;
		ownerName = "";
	}
	
	@Override
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	public boolean isEntityInvulnerable() {
        return StateMinor[ID.N.ImmuneTime] > 0;
    }
	
	@Override
	public float getEyeHeight() {
		return this.height * 1F;
	}
	
	@Override
	public boolean hasCustomNameTag() {
		if(ConfigHandler.showTag) {
			return this.dataWatcher.getWatchableObjectString(10).length() > 0;
		}
        return false;
    }
	
	//平常音效
	protected String getLivingSound() {
		if(this.getStateFlag(ID.F.IsMarried)) {
			if(rand.nextInt(5) == 0) {
				return Reference.MOD_ID+":ship-love";
			}
			else {
				return Reference.MOD_ID+":ship-say";
			}
		}
		else {
			return Reference.MOD_ID+":ship-say";
		}
    }
	
	//受傷音效
    protected String getHurtSound() {
    	
        return Reference.MOD_ID+":ship-hurt";
    }

    //死亡音效
    protected String getDeathSound() {
    	return Reference.MOD_ID+":ship-death";
    }

    //音效大小
    protected float getSoundVolume() {
        return ConfigHandler.shipVolume;
    }
	
	//get owner name (for player owner only)
	public String getOwnerName() {
		return this.ownerName;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		return null;
	}
	
	//setup AI
	protected void setAIList() {
		this.getNavigator().setEnterDoors(true);
		this.getNavigator().setAvoidsWater(false);
		this.getNavigator().setCanSwim(true);
	}
	
	//setup target AI: par1: 0:passive 1:active
	public void setAITargetList(int par1) {	
		//passive target AI
		if(par1 == 0) {
			this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
			this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
			this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
		}
		//active target AI
		else {
			this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
			this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
			this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
			this.targetTasks.addTask(4, new EntityAIShipInRangeTarget(this, 0.4F, 1));
		}	
	}

	//clear AI
	protected void clearAITasks() {
		tasks.taskEntries.clear();
	}
	
	//clear target AI
	protected void clearAITargetTasks() {
		this.setAttackTarget(null);
		targetTasks.taskEntries.clear();
	}
	
	//getter
	public ExtendShipProps getExtProps() {
		return ExtProps;
	}
	
	abstract public int getEquipType();
	
	public int getShipLevel() {
		return StateMinor[ID.N.ShipLevel];
	}
	public byte getShipType() {
		return ShipType;
	}
	public byte getShipID() {
		return ShipID;
	}
	
	@Override
	public int getAmmoLight() {
		return this.StateMinor[ID.N.NumAmmoLight];
	}

	@Override
	public int getAmmoHeavy() {
		return this.StateMinor[ID.N.NumAmmoHeavy];
	}
	
	@Override
	public int getStartEmotion() {
		return StartEmotion;
	}
	
	@Override
	public int getStartEmotion2() {
		return StartEmotion2;
	}
	
	@Override
	public int getTickExisted() {
		return this.ticksExisted;
	}
	
	@Override
	public int getAttackTime() {
		return this.attackTime;
	}
	
	@Override
	public EntityLivingBase getTarget() {
		return this.getAttackTarget();
	}
	
	@Override
	public float getAttackDamage() {	//not used for ship
		return 0;
	}
	
	@Override
	public float getAttackSpeed() {
		return this.StateFinal[ID.SPD];
	}
	
	@Override
	public float getAttackRange() {
		return this.StateFinal[ID.HIT];
	}
	
	@Override
	public float getMoveSpeed() {
		return this.StateFinal[ID.MOV];
	}
	
	@Override
	public boolean hasAmmoLight() {
		return StateMinor[ID.N.NumAmmoLight] > 0;
	}
	
	@Override
	public boolean hasAmmoHeavy() {
		return StateMinor[ID.N.NumAmmoHeavy] > 0;
	}
	
	public double getShipDepth() {
		return ShipDepth;
	}
	
	@Override
	public boolean getStateFlag(int flag) {	//get flag (boolean)
		return StateFlag[flag];		
	}
	
	public byte getStateFlagI(int flag) {		//get flag (byte)
		if(StateFlag[flag]) {
			return 1;
		}
		else {
			return 0;
		}
	}
	public float getStateEquip(int id) {
		return StateEquip[id];
	}
	public float getStateFinal(int id) {
		return StateFinal[id];
	}
	public int getStateMinor(int id) {
		return StateMinor[id];
	}
	public float getEffectEquip(int id) {
		return EffectEquip[id];
	}
	@Override
	public byte getStateEmotion(int id) {
		return StateEmotion[id];
	}
	public byte getBonusPoint(int id) {
		return BonusPoint[id];
	}
	public float getTypeModify(int id) {
		return TypeModify[id];
	}
	public float[] getModelPos() {
		return ModelPos;
	}
	
	//replace isInWater, check water block with NO extend AABB
	private void checkDepth() {
		Block BlockCheck = checkBlockWithOffset(0);
		
		if(BlockCheck == Blocks.water || BlockCheck == Blocks.lava) {
			ShipDepth = 1;
			for(int i=1; (this.posY+i)<255D; i++) {
				BlockCheck = checkBlockWithOffset(i);
				if(BlockCheck == Blocks.water || BlockCheck == Blocks.lava) {
					ShipDepth++;
				}
				else {
					if(BlockCheck == Blocks.air) {
						setStateFlag(ID.F.CanFloatUp, true);
					}
					else {
						setStateFlag(ID.F.CanFloatUp, false);
					}
					break;
				}
			}		
			ShipDepth = ShipDepth - (this.posY - (int)this.posY);
		}
		else {
			ShipDepth = 0;
		}
	}
	
	//check block from entity posY + offset
	public Block checkBlockWithOffset(int par1) {
		int blockX = MathHelper.floor_double(this.posX);
	    int blockY = MathHelper.floor_double(this.boundingBox.minY);
	    int blockZ = MathHelper.floor_double(this.posZ);

	    return this.worldObj.getBlock(blockX, blockY + par1, blockZ);    
	}
	
	//called when entity equip changed
	//this method get equip state from slots, no input parm
	public void calcEquipAndUpdateState() {
		ItemStack itemstack = null;
		float[] equipStat = {0F,0F,0F,0F,0F,0F,0F,0F,0F,0F};
		
		//init value
		StateEquip[ID.HP] = 0F;
		StateEquip[ID.DEF] = 0F;
		StateEquip[ID.SPD] = 0F;
		StateEquip[ID.MOV] = 0F;
		StateEquip[ID.HIT] = 0F;
		StateEquip[ID.ATK] = 0F;
		StateEquip[ID.ATK_H] = 0F;
		StateEquip[ID.ATK_AL] = 0F;
		StateEquip[ID.ATK_AH] = 0F;
		EffectEquip[ID.EF_CRI] = 0;
		EffectEquip[ID.EF_DHIT] = 0;
		EffectEquip[ID.EF_THIT] = 0;
		EffectEquip[ID.EF_MISS] = 0;
		
		//calc equip slots
		for(int i=0; i<ContainerShipInventory.SLOTS_EQUIP; i++) {
			itemstack = this.ExtProps.slots[i];
			if(itemstack != null) {
				equipStat = EquipCalc.getEquipStat(this, itemstack);
				StateEquip[ID.HP] += equipStat[ID.HP];
				StateEquip[ID.DEF] += equipStat[ID.DEF];
				StateEquip[ID.SPD] += equipStat[ID.SPD];
				StateEquip[ID.MOV] += equipStat[ID.MOV];
				StateEquip[ID.HIT] += equipStat[ID.HIT];
				StateEquip[ID.ATK] += equipStat[ID.ATK];
				StateEquip[ID.ATK_H] += equipStat[ID.ATK_H];
				StateEquip[ID.ATK_AL] += equipStat[ID.ATK_AL];
				StateEquip[ID.ATK_AH] += equipStat[ID.ATK_AH];
				
				EffectEquip[ID.EF_CRI] += equipStat[ID.CRI];
				EffectEquip[ID.EF_DHIT] += equipStat[ID.DHIT];
				EffectEquip[ID.EF_THIT] += equipStat[ID.THIT];
				EffectEquip[ID.EF_MISS] += equipStat[ID.MISS];
			}	
		}
		//update value
		calcShipAttributes(this.ShipID);
	}
	
	//setter	
	//setting attributes, called at load nbt data & init mob
	public void calcShipAttributes(byte id) {
		//init or renew bonus value, for short value: discard decimal
		//HP = (base + equip + (point + 1) * level * typeModify) * config scale
		StateFinal[ID.HP] = (Values.BaseHP[id] + StateEquip[ID.HP] + (float)(BonusPoint[ID.HP]+1) * (float)StateMinor[ID.N.ShipLevel] * TypeModify[ID.HP]) * ConfigHandler.hpRatio; 
		//DEF = base + ((point + 1) * level / 3 * 0.4 + equip) * typeModify
		StateFinal[ID.DEF] = (Values.BaseDEF[id] + StateEquip[ID.DEF] + ((float)(BonusPoint[ID.DEF]+1) * ((float)StateMinor[ID.N.ShipLevel])/3F) * 0.4F * TypeModify[ID.DEF]) * ConfigHandler.defRatio;
		//SPD = base + ((point + 1) * level / 10 * 0.02 + equip) * typeModify
		StateFinal[ID.SPD] = (Values.BaseSPD[id] + StateEquip[ID.SPD] + ((float)(BonusPoint[ID.SPD]+1) * ((float)StateMinor[ID.N.ShipLevel])/10F) * 0.02F * TypeModify[ID.SPD]) * ConfigHandler.spdRatio;
		//MOV = base + ((point + 1) * level / 10 * 0.01 + equip) * typeModify
		StateFinal[ID.MOV] = (Values.BaseMOV[id] + StateEquip[ID.MOV] + ((float)(BonusPoint[ID.MOV]+1) * ((float)StateMinor[ID.N.ShipLevel])/10F) * 0.01F * TypeModify[ID.MOV]) * ConfigHandler.movRatio;
		//HIT = base + ((point + 1) * level / 10 * 0.4 + equip) * typeModify
		StateFinal[ID.HIT] = (Values.BaseHIT[id] + StateEquip[ID.HIT] + ((float)(BonusPoint[ID.HIT]+1) * ((float)StateMinor[ID.N.ShipLevel])/10F) * 0.4F * TypeModify[ID.HIT]) * ConfigHandler.hitRatio;
		//ATK = (base + equip + ((point + 1) * level / 3) * typeModify) * config scale
		float atk = Values.BaseATK[id] + ((float)(BonusPoint[ID.ATK]+1) * ((float)StateMinor[ID.N.ShipLevel])/3F) * 0.5F * TypeModify[ID.ATK];
		StateFinal[ID.ATK] = (atk + StateEquip[ID.ATK]) * ConfigHandler.atkRatio;
		StateFinal[ID.ATK_H] = (atk * 4F + StateEquip[ID.ATK_H]) * ConfigHandler.atkRatio;
		StateFinal[ID.ATK_AL] = (atk + StateEquip[ID.ATK_AL]) * ConfigHandler.atkRatio;
		StateFinal[ID.ATK_AH] = (atk * 4F + StateEquip[ID.ATK_AH]) * ConfigHandler.atkRatio;
		//KB Resistance = Level / 10 * 0.04
		float resisKB = (((float)StateMinor[ID.N.ShipLevel])/10F) * 0.067F;
		
		//min, max cap balue
		if(StateFinal[ID.DEF] > 95F) {
			StateFinal[ID.DEF] = 95F;	//max def = 95%
		}
		if(StateFinal[ID.SPD] > 4F) {
			StateFinal[ID.SPD] = 4F;	//min attack delay = 0.5 sec
		}
		if(StateFinal[ID.SPD] < 0F) {
			StateFinal[ID.SPD] = 0F;
		}
		if(StateFinal[ID.MOV] > 0.8F) {
			StateFinal[ID.MOV] = 0.8F;	//high move speed is buggy
		}
		if(StateFinal[ID.MOV] < 0F) {
			StateFinal[ID.MOV] = 0F;
		}

		//calc cri,miss,multi hit rate
		
		
		//set attribute by final value
		/**
		 * DO NOT SET ATTACK DAMAGE to non-EntityMob!!!!!
		 */
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(StateFinal[ID.HP]);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(StateFinal[ID.MOV]);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(StateFinal[ID.HIT]+16); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(resisKB);
		this.jumpMovementFactor = (1F + StateFinal[ID.MOV]) * 0.05F;
		
		//for new ship
		if(this.getHealth() == 20F) this.setHealth(this.getMaxHealth());
		
		//for server side
		if(!worldObj.isRemote) {
			sendSyncPacket();		//sync nbt data
		}
	}
	
	//set next exp value, no sync or update (for client load nbt data, gui display)
	public void setExpNext() {
		StateMinor[ID.N.ExpNext] = StateMinor[ID.N.ShipLevel] * 20 + 20;
	}
		
	//called when entity exp++
	public void addShipExp(int exp) {
		int CapLevel = getStateFlag(ID.F.IsMarried) ? 150 : 100;
		
		if(StateMinor[ID.N.ShipLevel] != CapLevel && StateMinor[ID.N.ShipLevel] < 150) {	//level is not cap level
			StateMinor[ID.N.ExpCurrent] += exp;
			if(StateMinor[ID.N.ExpCurrent] >= StateMinor[ID.N.ExpNext]) {
				//level up sound
				this.worldObj.playSoundAtEntity(this, "random.levelup", 0.75F, 1.0F);
				StateMinor[ID.N.ExpCurrent] -= StateMinor[ID.N.ExpNext];	//level up
				StateMinor[ID.N.ExpNext] = (StateMinor[ID.N.ShipLevel] + 1) * 20 + 20;
				setShipLevel(++StateMinor[ID.N.ShipLevel], true);
			}
		}	
	}
	
	//called when entity level up
	public void setShipLevel(int par1, boolean update) {
		//set level
		if(par1 < 151) {
			StateMinor[ID.N.ShipLevel] = par1;
		}
		//update attributes
		if(update) { 
			calcShipAttributes(ShipID); 
			this.setHealth(this.getMaxHealth());
		}
	}
	
	//set owner name (for player owner only)
	public void setOwnerName(String name) {
		this.ownerName = name;
	}
	
	//called when a mob die near the entity (used in event handler)
	public void addKills() {
		StateMinor[ID.N.Kills]++;
	}
	
	@Override
	public void setAmmoLight(int num) {
		this.StateMinor[ID.N.NumAmmoLight] = num;
	}
	
	@Override
	public void setAmmoHeavy(int num) {
		this.StateMinor[ID.N.NumAmmoHeavy] = num;
	}
	
	//ship attribute setter, sync packet in method: calcShipAttributes 
	public void setStateFinal(int state, float par1) {
		StateFinal[state] = par1;
	}
	
	public void setStateMinor(int state, int par1) {
		StateMinor[state] = par1;
		
		//若修改melee flag, 則reload AI
		if(state == ID.N.TargetAI) {
			clearAITargetTasks();
    		setAITargetList(par1);
		}
	}
	
	//called when GUI update
	public void setEffectEquip(int state, float par1) {
		EffectEquip[state] = par1;
	}
	
	public void setBonusPoint(int state, byte par1) {
		BonusPoint[state] = par1;
	}
	
	//called when load nbt data or GUI click
	@Override
	public void setStateFlag(int id, boolean par1) {
		this.StateFlag[id] = par1;
		
		//若修改melee flag, 則reload AI
		if(id == ID.F.UseMelee) {
			clearAITasks();
    		setAIList();
		}
	}
	
	//called when load nbt data or GUI click
	public void setEntityFlagI(int flag, int par1) {
		if(par1 == 1) {
			this.StateFlag[flag] = true;
		}
		else {
			this.StateFlag[flag] = false;
		}
		
		//若修改melee flag, 則reload AI
		if(flag == ID.F.UseMelee) {
			clearAITasks();
    		setAIList();
		}
	}
	
	//called when entity spawn, set the type modify
	public void initTypeModify() {
		TypeModify[ID.HP] = Values.ModHP[ShipID];
		TypeModify[ID.ATK] = Values.ModATK[ShipID];
		TypeModify[ID.DEF] = Values.ModDEF[ShipID];
		TypeModify[ID.SPD] = Values.ModSPD[ShipID];
		TypeModify[ID.MOV] = Values.ModMOV[ShipID];
		TypeModify[ID.HIT] = Values.ModHIT[ShipID];
	}

	@Override
	public void setStateEmotion(int id, int value, boolean sync) {
		StateEmotion[id] = (byte)value;
		if(sync && !worldObj.isRemote) {
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
			CommonProxy.channel.sendToAllAround(new S2CEntitySync(this, 1), point);
		}
	}
	
	//emotion start time (CLIENT ONLY), called from model class
	@Override
	public void setStartEmotion(int par1) {
		StartEmotion = par1;
	}
	
	@Override
	public void setStartEmotion2(int par1) {
		StartEmotion2 = par1;
	}
	
	//manual send sync packet
	public void sendSyncPacket() {
		if(!worldObj.isRemote) {
			//for owner, send all data
			if(this.getOwner() != null) {
				EntityPlayerMP player = EntityHelper.getOnlinePlayer(this.getOwner());
				//owner在附近才需要sync
				if(player != null && this.getDistanceToEntity(player) < 30F) {
					CommonProxy.channel.sendTo(new S2CEntitySync(this, 0), player);
				}	
			}
			
			//for other player, send ship state for display
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 30D);
			CommonProxy.channel.sendToAllAround(new S2CEntitySync(this, 1), point);
		}
	}
	
	//right click on ship
	@Override
	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use item
		if(itemstack != null) {
			//use repair bucket
			if(itemstack.getItem() == ModItems.BucketRepair) {	
				//hp不到max hp時可以使用bucket
				if(this.getHealth() < this.getMaxHealth()) {
					if(!player.capabilities.isCreativeMode) {  //item-1 in non-creative mode
						--itemstack.stackSize;
	                }
	
	                if(this instanceof BasicEntityShipSmall) {
	                	this.heal(this.getMaxHealth() * 0.1F + 5F);	//1 bucket = 10% hp for small ship
	                }
	                else {
	                	this.heal(this.getMaxHealth() * 0.05F + 10F);	//1 bucket = 5% hp for large ship
	                }
	                
	                if (itemstack.stackSize <= 0) {  
	                	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	                }
	                
	                return true;
	            }			
			}
			
			//use kaitai hammer
			if(itemstack.getItem() == ModItems.KaitaiHammer && !worldObj.isRemote && 
			   ((this.getOwner() != null && player.getUniqueID().equals(this.getOwner().getUniqueID())) ||
				 EntityHelper.checkOP(player))) {
				//創造模式不消耗物品
                if(!player.capabilities.isCreativeMode) {  //damage +1 in non-creative mode
 	                itemstack.setItemDamage(itemstack.getItemDamage() + 1);
                    
                    //set item amount
                    ItemStack item0, item1, item2, item3;
                                   
                    if(this instanceof BasicEntityShipLarge) {
                        item0 = new ItemStack(ModBlocks.BlockGrudge, 8 + rand.nextInt(3));
                    	item1 = new ItemStack(ModBlocks.BlockAbyssium, 8 + rand.nextInt(3));
                    	item2 = new ItemStack(ModItems.Ammo, 8 + rand.nextInt(3), 1);
                    	item3 = new ItemStack(ModBlocks.BlockPolymetal, 8 + rand.nextInt(3));
                    }
                    else {
                        item0 = new ItemStack(ModItems.Grudge, 10 + rand.nextInt(8));
                    	item1 = new ItemStack(ModItems.AbyssMetal, 10 + rand.nextInt(8), 0);
                    	item2 = new ItemStack(ModItems.Ammo, 10 + rand.nextInt(8), 0);
                    	item3 = new ItemStack(ModItems.AbyssMetal, 10 + rand.nextInt(8), 1);
                    }
                    
                    EntityItem entityItem0 = new EntityItem(worldObj, posX+0.5D, posY+0.8D, posZ+0.5D, item0);
                    EntityItem entityItem1 = new EntityItem(worldObj, posX+0.5D, posY+0.8D, posZ-0.5D, item1);
                    EntityItem entityItem2 = new EntityItem(worldObj, posX-0.5D, posY+0.8D, posZ+0.5D, item2);
                    EntityItem entityItem3 = new EntityItem(worldObj, posX-0.5D, posY+0.8D, posZ-0.5D, item3);

                    worldObj.spawnEntityInWorld(entityItem0);
                    worldObj.spawnEntityInWorld(entityItem1);
                    worldObj.spawnEntityInWorld(entityItem2);
                    worldObj.spawnEntityInWorld(entityItem3);
                    
                    playSound(Reference.MOD_ID+":ship-kaitai", ConfigHandler.shipVolume, 1.0F);
                    playSound(Reference.MOD_ID+":ship-death", ConfigHandler.shipVolume, 1.0F);
                }
                
                //物品用完時要設定為null清空該slot
                if(itemstack.getItemDamage() >= itemstack.getMaxDamage()) {  //物品耐久度用完時要設定為null清空該slot
                	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                }
                 
                this.setDead();
                
                return true;			
			}
			
			//use marriage ring
			if(itemstack.getItem() == ModItems.MarriageRing && !this.getStateFlag(ID.F.IsMarried) && 
			   player.isSneaking() && this.getOwner() != null && player.getUniqueID().equals(this.getOwner().getUniqueID())) {
				//stack-1 in non-creative mode
				if(!player.capabilities.isCreativeMode) {
                    --itemstack.stackSize;
                }

				//set marriage flag
                this.setStateFlag(ID.F.IsMarried, true);
                
                //player marriage num +1
    			ExtendPlayerProps extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);		
    			if(extProps != null) {
    				extProps.setMarriageNum(extProps.getMarriageNum() + 1);
    			}
                
                //play hearts effect
                TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
    			CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(this, 3, false), point);
                
    			//play marriage sound
    	        this.playSound(Reference.MOD_ID+":ship-love", ConfigHandler.shipVolume, 1.0F);
    	        
    	        //add 3 random bonus point
    	        for(int i = 0; i < 3; ++i) {
    	        	addRandomBonusPoint();
    	        }
    	        
    	        this.calcEquipAndUpdateState();
    	        
                if(itemstack.stackSize <= 0) {  //物品用完時要設定為null清空該slot
                	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                }
                
                return true;
			}
			
			//use modernization kit
			if(itemstack.getItem() == ModItems.ModernKit) {
				if(addRandomBonusPoint()) {	//add 1 random bonus
					if(!player.capabilities.isCreativeMode) {
	                    --itemstack.stackSize;
	                    
	                    if(itemstack.stackSize <= 0) {  //物品用完時要設定為null清空該slot
	                    	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	                    }
	                }
					
					//play marriage sound
	    	        this.playSound(Reference.MOD_ID+":ship-love", ConfigHandler.shipVolume, 1.0F);
					
					return true;
				}	
			}
			
			//use modernization kit, owner only
			if(itemstack.getItem() == ModItems.OwnerPaper && this.getOwner() != null && player.getUniqueID().equals(this.getOwner().getUniqueID())) {
				NBTTagCompound nbt = itemstack.getTagCompound();
				boolean changeOwner = false;
				
				//check sign A and B is owner and another player
				if(itemstack.hasTagCompound()) {
					if(nbt.getString("signA").equals(player.getUniqueID().toString())) {
						if(nbt.getString("signB").length() > 0) {
							this.func_152115_b(nbt.getString("signB"));
							changeOwner = true;
						}
					}
					
					if(nbt.getString("signB").equals(player.getUniqueID().toString())) {
						if(nbt.getString("signA").length() > 0) {
							this.func_152115_b(nbt.getString("signA"));
							changeOwner = true;
						}
					}
				}
				
				if(changeOwner) {
					//play marriage sound
	    	        this.playSound(Reference.MOD_ID+":ship-death", ConfigHandler.shipVolume, 1.0F);
	    	        
					player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
					return true;
				}
			}
			
			//use lead
			if(itemstack.getItem() == Items.lead && this.allowLeashing()) {	
				this.setLeashedToEntity(player, true);
				return true;
	        }
			
		}
		
		//如果已經被綑綁, 再點一下可以解除綑綁
		if(this.getLeashed() && this.getLeashedToEntity() == player) {
            this.clearLeashed(true, !player.capabilities.isCreativeMode);
            return true;
        }
	
		//shift+right click時打開GUI
		if(player.isSneaking() && this.getOwner() != null && player.getUniqueID().equals(this.getOwner().getUniqueID())) {  
			int eid = this.getEntityId();
			//player.openGui vs FMLNetworkHandler ?
    		FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.SHIPINVENTORY, this.worldObj, this.getEntityId(), 0, 0);
    		return true;
		}
		
		//click ship without shift = sit
		if(!this.worldObj.isRemote && !player.isSneaking() && this.getOwner() != null && player.getUniqueID().equals(this.getOwner().getUniqueID())) {			
			this.setSitting(!this.isSitting());
            this.isJumping = false;
            this.setPathToEntity((PathEntity)null);
            this.setTarget((Entity)null);
            this.setAttackTarget((EntityLivingBase)null);
            return true;
        }

		return false;
	}
	
	//add random bonus point, NO SYNC, server only!
	private boolean addRandomBonusPoint() {
		int bonusChoose = rand.nextInt(6);
		
		//bonus point +1 if bonus point < 3
		if(BonusPoint[bonusChoose] < 3) {
			BonusPoint[bonusChoose] = (byte) (BonusPoint[bonusChoose] + 1);
			return true;
		}
		else {	//select other bonus point
			for(int i = 0; i < BonusPoint.length; ++i) {
				if(BonusPoint[i] < 3) {
					BonusPoint[i] = (byte) (BonusPoint[i] + 1);
					return true;
				}
			}
		}
		
		return false;
	}

	/**修改移動方法, 使其water跟lava中移動時像是flying entity
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
	@Override
    public void moveEntityWithHeading(float movX, float movZ) {
        double d0;

        if(this.isInWater() || this.handleLavaMovement()) { //判定為液體中時, 不會自動下沉
            d0 = this.posY;
            this.moveFlying(movX, movZ, this.getStateFinal(ID.MOV)*0.4F); //水中的速度計算(含漂移效果)
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            //水中阻力
            this.motionX *= 0.8D;
            this.motionY *= 0.8D;
            this.motionZ *= 0.8D;
            //水中撞到東西會上升
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + d0, this.motionZ)) {
                this.motionY = 0.3D;
            }
        }
        else {									//其他移動狀態
            float f2 = 0.91F;
            
            if(this.onGround) {					//在地面移動
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            float f4;
            
            if(this.onGround) {
                f4 = this.getAIMoveSpeed() * f3;
            }
            else {								//跳躍中
                f4 = this.jumpMovementFactor;
            }
            this.moveFlying(movX, movZ, f4);
            f2 = 0.91F;
            
            if(this.onGround) {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            if(this.isOnLadder()) {				//爬樓梯中
                float f5 = 0.15F;
                //限制爬樓梯時的橫向移動速度
                if(this.motionX < (double)(-f5)) {
                    this.motionX = (double)(-f5);
                }
                if(this.motionX > (double)f5) {
                    this.motionX = (double)f5;
                }
                if(this.motionZ < (double)(-f5)) {
                    this.motionZ = (double)(-f5);
                }
                if(this.motionZ > (double)f5) {
                    this.motionZ = (double)f5;
                }

                this.fallDistance = 0.0F;
                //限制爬樓梯的落下速度
                if (this.motionY < -0.15D) {
                    this.motionY = -0.15D;
                }

                boolean flag = this.isSneaking();
                //若是爬樓梯時為sneaking, 則不會落下(卡在樓梯上)
                if(flag && this.motionY < 0D) {
                    this.motionY = 0D;
                }
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            //往樓梯推擠, 則會往上爬
            if(this.isCollidedHorizontally && this.isOnLadder()) {
                this.motionY = 0.4D;
            }
            //自然掉落
            if(this.worldObj.isRemote && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || !this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded)) {
                if (this.posY > 0.0D) {
                    this.motionY = -0.1D;	//空氣中的gravity為0.1D
                }
                else {
                    this.motionY = 0.0D;
                }
            }
            else {
                this.motionY -= 0.08D;
            }
            //空氣中的三方向阻力
            this.motionY *= 0.98D;			
            this.motionX *= (double)f2;
            this.motionZ *= (double)f2;
//            LogHelper.info("DEBUG : f2 "+f2+" ");
        }
        //計算四肢擺動值
        this.prevLimbSwingAmount = this.limbSwingAmount;
        d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f6 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        this.limbSwingAmount += (f6 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }
	
	//update entity position
	@Override
	public void onUpdate() {
		super.onUpdate();

		this.checkDepth();	//check depth every tick

		Block CheckBlock = checkBlockWithOffset(0);
		
		//client side
		if(this.worldObj.isRemote) {
			//有移動時, 產生水花特效
			if(this.getShipDepth() > 0D) {
				//(注意此entity因為設為非高速更新, client端不會更新motionX等數值, 需自行計算)
				double motX = this.posX - this.prevPosX;
				double motZ = this.posZ - this.prevPosZ;
				double parH = this.posY - (int)this.posY;
				
				if(motX != 0 || motZ != 0) {
					ParticleHelper.spawnAttackParticleAt(this.posX + motX*1.5D, this.posY + 0.4D, this.posZ + motZ*1.5D, 
							-motX*0.5D, 0D, -motZ*0.5D, (byte)15);
				}
			}
			
			if(this.ticksExisted % 10 == 0) {
				//generate HP state effect
				switch(getStateEmotion(ID.S.HPState)) {
				case ID.HPState.MINOR:
					ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
							0D, 0D, 0D, (byte)4);
					break;
				case ID.HPState.MODERATE:
					ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
							0D, 0D, 0D, (byte)5);
					break;
				case ID.HPState.HEAVY:
					ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
							0D, 0D, 0D, (byte)7);
					break;
				default:
					break;
				}
			}	
		}//end client side
	}

	//check entity state every tick
	@Override
	public void onLivingUpdate() {
        super.onLivingUpdate();
//        LogHelper.info("DEBug : tick "+this.ticksExisted);
        //server side check
        if((!worldObj.isRemote)) {
        	//decr immune time
        	if(this.StateMinor[ID.N.ImmuneTime] > 0) {
        		this.StateMinor[ID.N.ImmuneTime]--;
        	}
        	
        	//sync client and reset AI after server start 10 ticks
        	if(ticksExisted == 10) {
        		clearAITasks();
        		clearAITargetTasks();	//reset AI for get owner after loading NBT data
        		setAIList();
        		setAITargetList(getStateMinor(ID.N.TargetAI));
        		sendSyncPacket();		//sync packet to client
        	}
        	
        	//check every 10 ticks
        	if(ticksExisted % 10 == 0) {
        		//use bucket automatically
        		if((getMaxHealth() - getHealth()) > (getMaxHealth() * 0.1F + 5F)) {
	                if(decrSupplies(7)) {
	                	if(this instanceof BasicEntityShipSmall) {
		                	this.heal(this.getMaxHealth() * 0.1F + 5F);	//1 bucket = 10% hp for small ship
		                }
		                else {
		                	this.heal(this.getMaxHealth() * 0.05F + 10F);	//1 bucket = 5% hp for large ship
		                }
	                }
	            }
        	}
        	
        	//check every 100 ticks
        	if(ticksExisted % 100 == 0) {
        		float hpState = this.getHealth() / this.getMaxHealth();
        		
        		//check hp state
        		if(hpState > 0.75F) {		//normal
        			this.setStateEmotion(ID.S.HPState, ID.HPState.NORMAL, false);
        		}
        		else if(hpState > 0.5F){	//minor damage
        			this.setStateEmotion(ID.S.HPState, ID.HPState.MINOR, false);
        		}
				else if(hpState > 0.25F){	//moderate damage
					this.setStateEmotion(ID.S.HPState, ID.HPState.MODERATE, false);   			
				}
				else {						//heavy damage
					this.setStateEmotion(ID.S.HPState, ID.HPState.HEAVY, false);
				}
        		
        		//roll emtion: hungry > T_T > bored > O_O
        		if(getStateFlag(ID.F.NoFuel)) {
        			if(this.getStateEmotion(ID.S.Emotion) != ID.Emotion.HUNGRY) {
//        				LogHelper.info("DEBUG : set emotion HUNGRY");
	    				this.setStateEmotion(ID.S.Emotion, ID.Emotion.HUNGRY, false);
	    			}
        		}
        		else {
        			if(hpState < 0.5F) {
    	    			if(this.getStateEmotion(ID.S.Emotion) != ID.Emotion.T_T) {
//    	    				LogHelper.info("DEBUG : set emotion T_T");
    	    				this.setStateEmotion(ID.S.Emotion, ID.Emotion.T_T, false);
    	    			}			
    	    		}
        			else {
        				if(this.isSitting() && this.getRNG().nextInt(3) > 1) {	//30% for bored
        	    			if(this.getStateEmotion(ID.S.Emotion) != ID.Emotion.BORED) {
//        	    				LogHelper.info("DEBUG : set emotion BORED");
        	    				this.setStateEmotion(ID.S.Emotion, ID.Emotion.BORED, false);
        	    			}
        	    		}
        	    		else {	//back to normal face
        	    			if(this.getStateEmotion(ID.S.Emotion) != ID.Emotion.NORMAL) {
//        	    				LogHelper.info("DEBUG : set emotion NORMAL");
        	    				this.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
        	    			}
        	    		}
        			}
        		}
        		
        		//sync emotion every 5 sec
        		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
    			CommonProxy.channel.sendToAllAround(new S2CEntitySync(this, 1), point);

        		//set air value
        		if(this.getAir() < 300) {
                	setAir(300);
                }
        		
        		//get ammo if no ammo
        		if(!this.hasAmmoLight()) { this.decrAmmoNum(2); }
        		if(!this.hasAmmoHeavy()) { this.decrAmmoNum(3); }
        		
        		//calc move distance and eat grudge (check position 5 sec ago)
        		double distX = posX - ShipPrevX;
        		double distY = posY - ShipPrevY;
        		double distZ = posZ - ShipPrevZ;
        		ShipPrevX = posX;
        		ShipPrevY = posY;
        		ShipPrevZ = posZ;
	        	int distSqrt = (int) MathHelper.sqrt_double(distX*distX + distY*distY + distZ*distZ);
	        	decrGrudgeNum(distSqrt+5);	//eat grudge or change movement speed
        		
        	}//end every 100 ticks
        	
        	//auto recovery every 20 sec
        	if(this.ticksExisted % 400 == 0) {
        		if(this.getHealth() < this.getMaxHealth()) {
        			this.setHealth(this.getHealth() + this.getMaxHealth() * 0.01F);
        		}
        	}
        	
        	//play timekeeping sound
        	if(ConfigHandler.timeKeeping) {
        		long time = this.worldObj.provider.getWorldTime();
            	int checkTime = (int)(time % 1000L);
            	if(checkTime == 0) {
            		playTimeSound((int)(time / 1000L) % 24);
            	}
        	}
        }//end if(server side)
    }

	//timekeeping method
	private void playTimeSound(int i) {
		//play entity attack sound
        this.playSound(Reference.MOD_ID+":ship-time"+i, ConfigHandler.timeKeepingVolume, 1.0F);
	}

	//melee attack method, no ammo cost, no attack speed, damage = 12.5% atk
	@Override
	public boolean attackEntityAsMob(Entity target) {
		//get attack value
		float atk = StateFinal[ID.ATK] * 0.125F;
		//set knockback value (testing)
		float kbValue = 0.15F;
		
		//experience++
		addShipExp(1);
		
		//grudge--
		decrGrudgeNum(1);
				
	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk);

	    //play entity attack sound
        if(this.getRNG().nextInt(10) > 6) {
        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }
	    
	    //if attack success
	    if(isTargetHurt) {
	    	//calc kb effect
	        if(kbValue > 0) {
	            target.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * kbValue), 
	                   0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue));
	            motionX *= 0.6D;
	            motionZ *= 0.6D;
	        }

	        //send packet to client for display partical effect   
	        if (!worldObj.isRemote) {
	        	TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
	    		CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
			}
	    }

	    return isTargetHurt;
	}
	
	//range attack method, cost light ammo, attack delay = 20 / attack speed, damage = 100% atk 
	public boolean attackEntityWithAmmo(Entity target) {	
		//get attack value
		float atk = StateFinal[ID.ATK];
		//set knockback value (testing)
		float kbValue = 0.05F;
		//update entity look at vector (for particle spawn)
        //此方法比getLook還正確 (client sync問題)
        float distX = (float) (target.posX - this.posX);
        float distY = (float) (target.posY - this.posY);
        float distZ = (float) (target.posZ - this.posZ);
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        distX = distX / distSqrt;
        distY = distY / distSqrt;
        distZ = distZ / distSqrt;
        
        //發射者煙霧特效
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY, this.posZ, distX, distY, distZ, true), point);

		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-firesmall", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //play entity attack sound
        if(this.rand.nextInt(10) > 7) {
        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }
        
        //experience++
  		addShipExp(2);
  		
  		//grudge--
  		decrGrudgeNum(1);
        
        //light ammo -1
        if(!decrAmmoNum(0)) {		//not enough ammo
        	atk = atk * 0.125F;	//reduce damage to 12.5%
        }

        //calc miss chance, if not miss, calc cri/multi hit
        float missChance = 0.2F + 0.15F * (distSqrt / StateFinal[ID.HIT]) - 0.001F * StateMinor[ID.N.ShipLevel];
        missChance -= EffectEquip[ID.EF_MISS];		//equip miss reduce
        if(missChance > 0.35F) missChance = 0.35F;	//max miss chance
        
        if(this.rand.nextFloat() < missChance) {
        	atk = 0;	//still attack, but no damage
        	//spawn miss particle
    		CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
        }
        else {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if(this.rand.nextFloat() < EffectEquip[ID.EF_CRI]) {
        		atk *= 1.5F;
        		//spawn critical particle
        		CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(this, 11, false), point);
        	}
        	else {
        		//calc double hit
            	if(this.rand.nextFloat() < EffectEquip[ID.EF_DHIT]) {
            		atk *= 2F;
            		//spawn double hit particle
            		CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(this, 12, false), point);
            	}
            	else {
            		//calc double hit
                	if(this.rand.nextFloat() < EffectEquip[ID.EF_THIT]) {
                		atk *= 3F;
                		//spawn triple hit particle
                		CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(this, 13, false), point);
                	}
            	}
        	}
        }
        
        //vs player = 25% dmg
  		if(target instanceof EntityPlayer) {
  			atk *= 0.25F;
  			
  			//check friendly fire
    		if(!ConfigHandler.friendlyFire) {
    			atk = 0F;
    		}
    		else if(atk > 59F) {
    			atk = 59F;	//same with TNT
    		}
  		}
  		
	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk);

	    //if attack success
	    if(isTargetHurt) {
	    	//calc kb effect
	        if(kbValue > 0) {
	            target.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * kbValue), 
	                   0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue));
	            motionX *= 0.6D;
	            motionZ *= 0.6D;
	        }
	        
        	//display hit particle on target
	        TargetPoint point1 = new TargetPoint(this.dimension, target.posX, target.posY, target.posZ, 64D);
			CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(target, 9, false), point1);
        }

	    return isTargetHurt;
	}

	//range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
	public boolean attackEntityWithHeavyAmmo(Entity target) {	
		//get attack value
		float atk = StateFinal[ID.ATK_H];
		
		//set knockback value (testing)
		float kbValue = 0.15F;
		//飛彈是否採用直射
		boolean isDirect = false;
		//計算目標距離
		float tarX = (float)target.posX;	//for miss chance calc
		float tarY = (float)target.posY;
		float tarZ = (float)target.posZ;
		float distX = tarX - (float)this.posX;
		float distY = tarY - (float)this.posY;
		float distZ = tarZ - (float)this.posZ;
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        float launchPos = (float)posY + height * 0.7F;
        
        //超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
        if((distX*distX+distY*distY+distZ*distZ) < 36F) {
        	isDirect = true;
        }
        if(this.getShipDepth() > 0D) {
        	isDirect = true;
        	launchPos = (float)posY;
        }
		
		//experience++
		this.addShipExp(16);
		
		//grudge--
		decrGrudgeNum(1);
	
		//play cannon fire sound at attacker
        this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //play entity attack sound
        if(this.getRNG().nextInt(10) > 7) {
        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }
        
        //heavy ammo -1
        if(!decrAmmoNum(1)) {	//not enough ammo
        	atk = atk * 0.125F;	//reduce damage to 12.5%
        }
        
        //calc miss chance, miss: add random offset(0~6) to missile target 
        float missChance = 0.2F + 0.15F * (distSqrt / StateFinal[ID.HIT]) - 0.001F * StateMinor[ID.N.ShipLevel];
        missChance -= EffectEquip[ID.EF_MISS];	//equip miss reduce
        if(missChance > 0.35F) missChance = 0.35F;	//max miss chance = 30%
       
        if(this.rand.nextFloat() < missChance) {
        	tarX = tarX - 3F + this.rand.nextFloat() * 6F;
        	tarY = tarY + this.rand.nextFloat() * 3F;
        	tarZ = tarZ - 3F + this.rand.nextFloat() * 6F;
        	//spawn miss particle
        	TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        	CommonProxy.channel.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
        }

        //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.worldObj, this, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atk, kbValue, isDirect);
        this.worldObj.spawnEntityInWorld(missile);
        
        return true;
	}
	
	//be attacked method, 包括其他entity攻擊, anvil攻擊, arrow攻擊, fall damage都使用此方法 
	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk) {		
		//set hurt face
    	if(this.getStateEmotion(ID.S.Emotion) != ID.Emotion.O_O) {
    		this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
    	}

		//進行def計算
        float reduceAtk = atk * (1F - StateFinal[ID.DEF] / 100F);    
        if(atk < 0) { atk = 0; }
        
        //若掉到世界外, 則傳送回y=4
        if(attacker.getDamageType().equals("outOfWorld")) {
        	this.posY = 4D;
        	return false;
        }
        
        //無敵的entity傷害無效
		if(this.isEntityInvulnerable()) {	
            return false;
        }
		else if(attacker.getSourceOfDamage() != null) {
			Entity entity = attacker.getSourceOfDamage();
			
			//不會對自己造成傷害
			if(entity.equals(this)) {  
				return false;
			}
			
			//若攻擊方同樣為ship, 則傷害-95% (使ship vs ship能打久一點)
			if(entity instanceof BasicEntityShip || entity instanceof BasicEntityAirplane || entity instanceof EntityRensouhou) {
				reduceAtk *= 0.05F;
			}
			
			//若攻擊方為player, 則修正傷害
			if(entity instanceof EntityPlayer) {
				//若攻擊者為owner, 傷害 = 25%
				if(this.getOwner() != null && entity.getUniqueID().equals(this.getOwner().getUniqueID())) {
					reduceAtk *= 0.25F;
				}
				else {
					//若禁止friendlyFire, 則傷害設為0
					if(!ConfigHandler.friendlyFire) {
						return false;
					}
				}
			}

			//取消坐下動作
			this.setSitting(false);
			
			//若傷害力可能致死, 則尋找物品中有無repair goddess來取消掉此攻擊
			if(reduceAtk >= (this.getHealth() - 1F)) {
				if(this.decrSupplies(8)) {
					this.setHealth(this.getMaxHealth());
					this.StateMinor[ID.N.ImmuneTime] = 60;
					return false;
				}
			}
   
            //執行父class的被攻擊判定, 包括重置love時間, 計算火毒抗性, 計算鐵砧/掉落傷害, 
            //hurtResistantTime(0.5sec無敵時間)計算, 
            return super.attackEntityFrom(attacker, reduceAtk);
        }
		
		return false;
    }
	
	//decrese ammo number with type, or find ammo item from inventory
	protected boolean decrAmmoNum(int type) {
		switch(type) {
		case 0:  //use 1 light ammo
			if(hasAmmoLight()) { 
				--StateMinor[ID.N.NumAmmoLight];
				return true;
			}
			else {
				if(decrSupplies(0)) {  //find ammo item
					if(ConfigHandler.easyMode) {
						StateMinor[ID.N.NumAmmoLight] += 299;
					}
					else {
						StateMinor[ID.N.NumAmmoLight] += 29;
					}
					return true;
				}
				else if(decrSupplies(2)) {  //find ammo item
					if(ConfigHandler.easyMode) {
						StateMinor[ID.N.NumAmmoLight] += 2699;
					}
					else {
						StateMinor[ID.N.NumAmmoLight] += 269;
					}
					return true;
				}
				else {				   //no ammo
					return false;
				}
			}
		case 1:  //use 1 heavy ammo
			if(hasAmmoHeavy()) { 
				--StateMinor[ID.N.NumAmmoHeavy];
				return true;
			}
			else {
				if(decrSupplies(1)) {  //find ammo item
					if(ConfigHandler.easyMode) {
						StateMinor[ID.N.NumAmmoHeavy] += 149;
					}
					else {
						StateMinor[ID.N.NumAmmoHeavy] += 14;
					}
					return true;
				}
				else if(decrSupplies(3)) {  //find ammo item
					if(ConfigHandler.easyMode) {
						StateMinor[ID.N.NumAmmoHeavy] += 1349;
					}
					else {
						StateMinor[ID.N.NumAmmoHeavy] += 134;
					}
					return true;
				}
				else {				   //no ammo
					return false;
				}
			}
		case 2:	//no ammo light, use item
			if(decrSupplies(0)) {  //find ammo item
				if(ConfigHandler.easyMode) {
					StateMinor[ID.N.NumAmmoLight] += 300;
				}
				else {
					StateMinor[ID.N.NumAmmoLight] += 30;
				}
				return true;
			}
			else if(decrSupplies(2)) {  //find ammo item
				if(ConfigHandler.easyMode) {
					StateMinor[ID.N.NumAmmoLight] += 2700;
				}
				else {
					StateMinor[ID.N.NumAmmoLight] += 270;
				}
				return true;
			}
			else {				   //no ammo
				return false;
			}
		case 3:	//no ammo heavy, use item
			if(decrSupplies(1)) {  //find ammo item
				if(ConfigHandler.easyMode) {
					StateMinor[ID.N.NumAmmoHeavy] += 150;
				}
				else {
					StateMinor[ID.N.NumAmmoHeavy] += 15;
				}
				return true;
			}
			else if(decrSupplies(3)) {  //find ammo item
				if(ConfigHandler.easyMode) {
					StateMinor[ID.N.NumAmmoHeavy] += 1350;
				}
				else {
					StateMinor[ID.N.NumAmmoHeavy] += 135;
				}
				return true;
			}
			else {				   //no ammo
				return false;
			}
		case 4:  //use 4 light ammo
			if(StateMinor[ID.N.NumAmmoLight] > 3) {
				StateMinor[ID.N.NumAmmoLight] -= 4;
				return true;
			}
			else {
				if(decrSupplies(0)) {  //find ammo item
					if(ConfigHandler.easyMode) {
						StateMinor[ID.N.NumAmmoLight] += 296;
					}
					else {
						StateMinor[ID.N.NumAmmoLight] += 26;
					}
					return true;
				}
				else if(decrSupplies(2)) {  //find ammo item
					if(ConfigHandler.easyMode) {
						StateMinor[ID.N.NumAmmoLight] += 2696;
					}
					else {
						StateMinor[ID.N.NumAmmoLight] += 266;
					}
					return true;
				}
				else {				   //no ammo
					return false;
				}
			}
		case 5:  //use 2 heavy ammo
			if(StateMinor[ID.N.NumAmmoHeavy] > 1) { 
				StateMinor[ID.N.NumAmmoHeavy] -= 2;
				return true;
			}
			else {
				if(decrSupplies(1)) {  //find ammo item
					if(ConfigHandler.easyMode) {
						StateMinor[ID.N.NumAmmoHeavy] += 148;
					}
					else {
						StateMinor[ID.N.NumAmmoHeavy] += 13;
					}
					return true;
				}
				else if(decrSupplies(3)) {  //find ammo item
					if(ConfigHandler.easyMode) {
						StateMinor[ID.N.NumAmmoHeavy] += 1348;
					}
					else {
						StateMinor[ID.N.NumAmmoHeavy] += 133;
					}
					return true;
				}
				else {				   //no ammo
					return false;
				}
			}
		}
		
		return false;	//unknow attack type
	}
	
	//eat grudge and change movement speed
	protected void decrGrudgeNum(int par1) {
		LogHelper.info("DEBUG : check grudge num");
		
		if(par1 > 215) {	//max cost = 215 (calc from speed 1 moving 5 sec)
			par1 = 215;
		}
		
		if(StateMinor[ID.N.NumGrudge] >= (int)par1) { //has enough fuel
			StateMinor[ID.N.NumGrudge] -= (int)par1;
		}
		else {
			if(decrSupplies(4)) {		//find grudge
				if(ConfigHandler.easyMode) {
					StateMinor[ID.N.NumGrudge] += 3600;
				}
				else {
					StateMinor[ID.N.NumGrudge] += 1200;
				}
				StateMinor[ID.N.NumGrudge] -= (int)par1;
			}
			else if(decrSupplies(5)) {	//find grudge block
				if(ConfigHandler.easyMode) {
					StateMinor[ID.N.NumGrudge] += 32400;
				}
				else {
					StateMinor[ID.N.NumGrudge] += 10800;
				}
				StateMinor[ID.N.NumGrudge] -= (int)par1;
			}
//避免吃掉含有儲存資訊的方塊, 因此停用此方塊作為grudge補充道具
//			else if(decrSupplies(6)) {	//find grudge heavy block
//				NumGrudge += 97200;
//				NumGrudge -= (int)par1;
//			}
		}
		
		if(StateMinor[ID.N.NumGrudge] <= 0) {
			setStateFlag(ID.F.NoFuel, true);
		}
		else {
			setStateFlag(ID.F.NoFuel, false);
		}
		
		//no fuel, clear AI
		if(getStateFlag(ID.F.NoFuel)) {
			//原本有AI, 則清除之
			if(this.targetTasks.taskEntries.size() > 0) {
				LogHelper.info("DEBUG : No fuel, clear AI");
				clearAITasks();
				clearAITargetTasks();
				sendSyncPacket();
			}	
		}
		//has fuel, set AI
		else {
			if(this.targetTasks.taskEntries.size() < 2) {
				LogHelper.info("DEBUG : Get fuel, set AI");
				clearAITasks();
				clearAITargetTasks();
				setAIList();
				setAITargetList(getStateMinor(ID.N.TargetAI));
				sendSyncPacket();
			}
		}
	}
	
	//decrese ammo/grudge/repair item, return true or false(not enough item)
	protected boolean decrSupplies(int type) {
		boolean isEnoughItem = true;
		int itemNum = 1;
		ItemStack itemType = null;
		
		//find ammo
		switch(type) {
		case 0:	//use 1 light ammo
			itemType = new ItemStack(ModItems.Ammo,1,0);
			break;
		case 1: //use 1 heavy ammo
			itemType = new ItemStack(ModItems.Ammo,1,2);
			break;
		case 2:	//use 1 light ammo container
			itemType = new ItemStack(ModItems.Ammo,1,1);
			break;
		case 3: //use 1 heavy ammo container
			itemType = new ItemStack(ModItems.Ammo,1,3);
			break;
		case 4: //use 1 grudge
			itemType = new ItemStack(ModItems.Grudge,1);
			break;
		case 5: //use 1 grudge block
			itemType = new ItemStack(ModBlocks.BlockGrudge,1);
			break;
		case 6: //use 1 grudge block
			itemType = new ItemStack(ModBlocks.BlockGrudgeHeavy,1);
			break;
		case 7:	//use 1 repair bucket
			itemType = new ItemStack(ModItems.BucketRepair,1);
			break;
		case 8:	//use 1 repair goddess
			itemType = new ItemStack(ModItems.RepairGoddess,1);
			break;	
		}
		
		//search item in ship inventory
		int i = findItemInSlot(itemType);
		if(i == -1) {		//item not found
			return false;
		}
		
		//decr item stacksize
		ItemStack getItem = this.ExtProps.slots[i];

		if(getItem.stackSize >= itemNum) {
			getItem.stackSize -= itemNum;
		}
		else {	//not enough item
			getItem.stackSize = 0;
			isEnoughItem = false;
		}
				
		if(getItem.stackSize == 0) {
			getItem = null;
		}
		
		//save back itemstack
		//no need to sync because no GUI opened
		this.ExtProps.slots[i] = getItem;	
		
		return isEnoughItem;	
	}

	//find item in ship inventory
	protected int findItemInSlot(ItemStack parItem) {
		ItemStack slotitem = null;

		//search ship inventory (except equip slots)
		for(int i = ContainerShipInventory.SLOTS_EQUIP; i < ContainerShipInventory.SLOTS_TOTAL; i++) {
			slotitem = this.ExtProps.slots[i];
			if(slotitem != null && 
			    slotitem.getItem().equals(parItem.getItem()) && 
			    slotitem.getItemDamage() == parItem.getItemDamage()) {
				return i;	//found item
			}		
		}	
		
		return -1;	//item not found
	}

	
}
