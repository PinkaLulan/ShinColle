package com.lulan.shincolle.entity;

import java.util.Random;
import java.util.UUID;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.inventory.ContainerShipInventory;
import com.lulan.shincolle.network.createPacketS2C;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.AttrValues;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**SHIP DATA <br>
 * Explanation in crafting/ShipCalc.class
 */
public abstract class BasicEntityShip extends EntityTameable {

	public ExtendShipProps ExtProps;		//entity額外NBT紀錄
	//for attribute calc
	public short ShipLevel;				//ship level
	public int Kills;					//kill mobs (= exp)
	public byte ShipType;				//ship type
	public byte ShipID;
	//for AI calc
	public int StartEmotion;			//表情開始時間
	public String BlockUnderName;		//腳下方塊名稱, 不需要存NBT
	public ItemStack ammotype;	//for inventory check
	public boolean hasAmmo;				//檢查有無彈藥
	public boolean hasHeavyAmmo;		//檢查有無重型彈藥
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
		hasAmmo = false;
		hasHeavyAmmo = false;
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
		//DEF = base + ((point + 1) * level / 5 * 0.6 + equip) * typeModify
		AttrFinalShort[AttrID.DEF] = (short) ((float)AttrValues.BaseDEF[id] + ((float)(BonusPoint[AttrID.DEF]+1) * (((float)ShipLevel)/5F) * 0.6F + (float)AttrEquipShort[AttrID.DEF]) * TypeModify[AttrID.DEF]);
		//SPD = base + ((point + 1) * level / 10 * 0.02 + equip) * typeModify
		AttrFinalFloat[AttrID.SPD] = AttrValues.BaseSPD[id] + ((float)(BonusPoint[AttrID.SPD+3]+1) * (((float)ShipLevel)/10F) * 0.02F + AttrEquipFloat[AttrID.SPD]) * TypeModify[AttrID.SPD+3];
		//MOV = base + ((point + 1) * level / 10 * 0.01 + equip) * typeModify
		AttrFinalFloat[AttrID.MOV] = AttrValues.BaseMOV[id] + ((float)(BonusPoint[AttrID.MOV+3]+1) * (((float)ShipLevel)/10F) * 0.01F + AttrEquipFloat[AttrID.MOV]) * TypeModify[AttrID.MOV+3];
		//HIT = base + ((point + 1) * level / 10 * 0.8 + equip) * typeModify
		AttrFinalFloat[AttrID.HIT] = AttrValues.BaseHIT[id] + ((float)(BonusPoint[AttrID.HIT+3]+1) * (((float)ShipLevel)/10F) * 0.8F + AttrEquipFloat[AttrID.HIT]) * TypeModify[AttrID.HIT+3];
		//KB Resistance = Level / 10 * 0.04
		float resisKB = (((float)ShipLevel)/10F) * 0.04F;
		
		if(AttrFinalFloat[AttrID.SPD] > 2F) {
			AttrFinalFloat[AttrID.SPD] = 2F;	//delay < 0.5sec is meaningless
		}
		if(AttrFinalFloat[AttrID.MOV] > 1F) {
			AttrFinalFloat[AttrID.MOV] = 1F;	//move speed > 1 is buggy
		}
		
		//set attribute by final value
		/**
		 * DO NOT set ATTACK DAMAGE to non-EntityMob!!!!!
		 */
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
		if(par1 < 151) {
			ShipLevel = par1;
		}
		else {	//max level = 150
			ShipLevel = 150;
		}	
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
	
	//right click on ship
	@Override
	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//如果已經被綑綁, 再點一下可以解除綑綁
		if(this.getLeashed() && this.getLeashedToEntity() == player) {
            this.clearLeashed(true, !player.capabilities.isCreativeMode);
            return true;
        }
		
		//use repair bucket
		if(itemstack != null) {
			if(itemstack.getItem() == ModItems.BucketRepair) {	//使用修復桶
				//hp不到max hp時可以使用bucket
				if(this.getHealth() < this.getMaxHealth()) {
	                if (!player.capabilities.isCreativeMode) {  //stack-1 in non-creative mode
	                    --itemstack.stackSize;
	                }
	
	                if(this instanceof BasicEntitySmallShip) {
	                	this.heal(this.getMaxHealth() * 0.1F);	 //1 bucket = 10% hp for small ship
	                }
	                else {
	                	this.heal(this.getMaxHealth() * 0.05F);	 //1 bucket = 5% hp for large ship
	                }
	                
	
	                if (itemstack.stackSize <= 0) {  //物品用完時要設定為null清空該slot
	                	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	                }
	                
	                return true;
	            }			
			}	
			//use lead
			else if(itemstack.getItem() == Items.lead && this.allowLeashing()) {
				this.setLeashedToEntity(player, true);
				return true;
	        }
		}

		return false;
	}
	
	//check entity state every tick
	@Override
	public void onLivingUpdate() {
        super.onLivingUpdate();

        //server side check
        if((!this.worldObj.isRemote)) {
        	//check ammo every 100 ticks
        	if(this.ticksExisted % 100 == 0) {
	        	//set ammo flag to default
	        	this.hasAmmo = false;
	        	this.hasHeavyAmmo = false;
	        	
	        	//search ship inventory
	        	for(int i=ContainerShipInventory.SLOTS_EQUIP; i<ContainerShipInventory.SLOTS_TOTAL; i++) {
	    			ammotype = this.ExtProps.slots[i];
	        		
	    			if(ammotype != null) {
	    				if(ammotype.getItem() == ModItems.Ammo) this.hasAmmo = true;
	    				if(ammotype.getItem() == ModItems.HeavyAmmo) this.hasHeavyAmmo = true;
	    			}		
	    		}	        	
        	}     	
        }//end if(server side)
        
    }
	
	//melee attack method, no ammo cost, no attack speed, damage = 12.5% atk
	@Override
	public boolean attackEntityAsMob(Entity target) {
		//get attack value
		float atk = ((float)AttrFinalShort[AttrID.ATK]) * 0.125F;
		//set knockback value (testing)
		float kbValue = 0.15F;
		
	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk);

	    //play entity attack sound
        if(this.getRNG().nextInt(10) > 6) {
        	this.playSound(Reference.MOD_ID_LOW+":ship-hitsmall", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
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
				createPacketS2C.sendS2CAttackParticle(target, 1);     
			}
	    }

	    return isTargetHurt;
	}
	
	//range attack method, cost light ammo, attack delay = 20 / attack speed, damage = 100% atk 
	public boolean attackEntityWithAmmo(Entity target) {	
		//get attack value
		float atk = (float) AttrFinalShort[AttrID.ATK];
		//set knockback value (testing)
		float kbValue = 0.05F;
		
		//play cannon fire sound at attacker
        this.playSound(Reference.MOD_ID_LOW+":ship-firesmall", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //play entity attack sound
        if(this.getRNG().nextInt(10) > 6) {
        	this.playSound(Reference.MOD_ID_LOW+":ship-hitsmall", 1F, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }
        
        //light ammo -1
        if(!decrAmmo(0)) {		//not enough ammo
        	atk = atk * 0.125F;	//reduce damage to 12.5%
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
	        	         
        	//send packet to client for display partical effect  
        	createPacketS2C.sendS2CAttackParticle(target, 9);  
	    }

	    return isTargetHurt;
	}

	//range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
	public boolean attackEntityWithHeavyAmmo(Entity target) {	
	/*	EntityArrow entityarrow = new EntityArrow(this.worldObj, this, p_82196_1_, 1.6F, (float)(14 - this.worldObj.difficultySetting.getDifficultyId() * 4));
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        entityarrow.setDamage((double)(p_82196_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.difficultySetting.getDifficultyId() * 0.11F));

        if (i > 0)
        {
            entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            entityarrow.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == 1)
        {
            entityarrow.setFire(100);
        }

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(entityarrow);
	*/	
		
		return false;
	}
	
	//be attacked method, 包括其他entity攻擊, anvil攻擊, arrow攻擊, fall damage都使用此方法 
	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk) {
		//進行def計算
        float reduceAtk = atk * (1F - (float)this.AttrFinalShort[AttrID.DEF] / 100F);
        //無敵的entity傷害無效
		if(this.isEntityInvulnerable()) {	
            return false;
        }
        else {
            Entity entity = attacker.getEntity();
            this.aiSit.setSitting(false);
   
            //執行父class的被攻擊判定, 包括重置love時間, 計算火毒抗性, 計算鐵砧/掉落傷害, 
            //hurtResistantTime(0.5sec無敵時間)計算, 
            return super.attackEntityFrom(attacker, reduceAtk);
        }
    }
	
	//decresse ammo amount with type, return true or false(not enough item)
	private boolean decrAmmo(int type) {
		boolean isEnoughAmmo = true;
		int ammonum = 0;
		Item ammotype = null;
		
		//find ammo
		switch(type) {
		case 0:	//use 1 light ammo
			ammonum = 1;
			ammotype = ModItems.Ammo;
			break;
		case 1: //use 1 heavy ammo
			ammonum = 1;
			ammotype = ModItems.HeavyAmmo;
			break;
		case 2: //use 2 light ammo
			ammonum = 2;
			ammotype = ModItems.Ammo;
			break;
		case 3: //use 2 heavy ammo
			ammonum = 2;
			ammotype = ModItems.HeavyAmmo;
			break;
		}
		
		//search item in ship inventory
		int i = findItemInSlot(ammotype);
		if(i == -1) {		//item not found
			return false;
		}
		ItemStack ammo = this.ExtProps.slots[i];
		
		//decr item stacksize		
		if(ammo.stackSize >= ammonum) {
			ammo.stackSize -= ammonum;
		}
		else {	//not enough ammo, damage will reduce
			ammo.stackSize = 0;
			isEnoughAmmo = false;
		}
				
		if(ammo.stackSize == 0) {
			ammo = null;
		}
		
		//save back itemstack
		//no need to sync because no GUI opened
		this.ExtProps.slots[i] = ammo;	
		
		return isEnoughAmmo;	
	}

	//find item in ship inventory
	private int findItemInSlot(Item parItem) {
		ItemStack slotitem = null;

		//search ship inventory (except equip slots)
		for(int i=ContainerShipInventory.SLOTS_EQUIP; i<ContainerShipInventory.SLOTS_TOTAL; i++) {
			slotitem = this.ExtProps.slots[i];
			if(slotitem != null && slotitem.getItem() == parItem) {
				return i;	//found item
			}		
		}	
		
		return -1;	//item not found
	}	
	

	
}
