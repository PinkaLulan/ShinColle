package com.lulan.shincolle.entity.hime;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.entity.other.EntityFloatingFort;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityNorthernHime extends BasicEntityShipCV {
	
	private int goRidingTicks;		//騎乘目標尋找時間
	private boolean goRiding;		//是否要找目標騎乘
	private Entity goRideEntity;	//騎乘目標
	
	public EntityNorthernHime(World world) {
		super(world);
		this.setSize(0.6F, 1.2F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.NorthernHime);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.AVIATION);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BBV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BBV]);
		this.ModelPos = new float[] {-6F, 8F, 0F, 50F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.initTypeModify();
		
		goRidingTicks = 0;
		goRideEntity = null;
		goRiding = false;
		launchHeight = this.height;
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		
	}
	
	@Override
	public float getEyeHeight() {
		return 1.0F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 2;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));
	}
	
	//check entity state every tick
  	@Override
  	public void onLivingUpdate() {
  		
  		//server side
  		if(!worldObj.isRemote) {
  			//every 80 ticks
        	if(this.ticksExisted % 64 == 0) {
        		//1: 增強被動回血
        		if(getStateMinor(ID.M.NumGrudge) > 0 && this.getHealth() < this.getMaxHealth()) {
        			this.setHealth(this.getHealth() + this.getMaxHealth() * 0.015625F);
        		}
        		
        		//2: 結婚後, 周圍某一目標回血, 包括玩家, 回血目標依等級提昇
				if(getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) && !getStateFlag(ID.F.NoFuel)) {
					//判定bounding box內是否有可以回血的目標
					int healCount = this.getLevel() / 25 + 1;
		            EntityLivingBase hitEntity = null;
		            List hitList = null;
		            hitList = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(8D, 8D, 8D));
		           
		            for(int i = 0; i < hitList.size(); i++) {
		            	//補血名額沒了, break
		            	if(healCount <= 0) break;
		            	
		            	hitEntity = (EntityLivingBase) hitList.get(i);
		            	
		            	//抓可以補血的目標, 不包含自己
		            	if(hitEntity != this && hitEntity.getHealth() / hitEntity.getMaxHealth() < 0.98F) {
	            			if(hitEntity instanceof EntityPlayer) {
	            				hitEntity.heal(1F + this.getLevel() * 0.02F);
		            			healCount--;
		            		}
		            		else if(hitEntity instanceof BasicEntityShip && EntityHelper.checkIsAlly(this, hitEntity)) {
		            			hitEntity.heal(1F + hitEntity.getMaxHealth() * 0.02F + this.getLevel() * 0.1F);
		            			healCount--;
			            	}
		            	}
		            }
				}//end heal ability
        	}//end 80 ticks
        	
        	//every 256 ticks
        	if(this.ticksExisted % 25 == 0) {
        		int roll = this.rand.nextInt(5);
//        		LogHelper.info("DEBUG : hoppo go riding "+roll);
        		//每一段時間檢查是否要騎乘其他entity
        		if(roll == 0) {
        			this.checkRiding();
        		}
        	}
        	
        	//若要找騎乘目標
        	if(this.goRiding) {
        		this.goRidingTicks++;
        		
        		//找太久, 放棄騎乘目標
        		if(this.goRidingTicks > 200) {
        			this.cancelGoRiding();
        		}
        		
        		float distRiding = 0F;
        		if(goRideEntity != null) {
        			distRiding = this.getDistanceToEntity(this.goRideEntity);
        		}
        		
        		//每32 tick找一次路徑
        		if(this.ticksExisted % 32 == 0) {
        			if(distRiding > 2F) {
        				this.getShipNavigate().tryMoveToEntityLiving(this.goRideEntity, 1D);
        			}
        		}
        		
        		//距離2格內則騎乘目標
        		if(distRiding <= 2F) {
        			if(goRideEntity != null && !goRideEntity.isRiding() && this.riddenByEntity == null) {
        				this.mountEntity(goRideEntity);
        				this.getShipNavigate().clearPathEntity();
        				this.cancelGoRiding();
        			}
        		}
        	}
        	
        	//騎乘中
        	if(this.isRiding()) { 	
        		//若騎乘目標sneaking, 則取消期程目標
    			if(this.ridingEntity.isSneaking()) {
        			this.mountEntity(null);
        		}
        	}
         	
  		}//end server side
  		//client side
  		else {
  			//drip water effect
  			if(this.ticksExisted % 10 == 0) {
  				if(getStateEmotion(ID.S.State2) == ID.State.EQUIP01_2 && !getStateFlag(ID.F.NoFuel)) {
  					if(this.isSitting() || this.isRiding()) {
  						ParticleHelper.spawnAttackParticleAt(this.posX, this.posY+0.9D, this.posZ, 0D, 0D, 0D, (byte)28);
  					}
  					else {
  						ParticleHelper.spawnAttackParticleAt(this.posX, this.posY+1.1D, this.posZ, 0D, 0D, 0D, (byte)28);
  					}
  				}
  			}
  			
  			//同步騎乘方向
  			if(this.isRiding() && this.ridingEntity instanceof EntityLivingBase) {
  				this.renderYawOffset = ((EntityLivingBase)ridingEntity).renderYawOffset;
  				this.rotationYaw = ridingEntity.rotationYaw;
  			}
  		}
  			
  		super.onLivingUpdate();
  	}
  	
  	//cancel go riding entity
  	private void cancelGoRiding() {
  		goRidingTicks = 0;
		goRideEntity = null;
		goRiding = false;
  	}
	
  	//mount other entity
	private void checkRiding() {
		this.cancelGoRiding();
		if(this.isSitting() || this.getLeashed() || this.getStateFlag(ID.F.NoFuel)) {
			return;
		}
		
		//已經在騎乘, 則機率下坐騎
		if(this.isRiding()) {
			this.mountEntity(null);
		}
		else {
			EntityLivingBase getEnt = null;
	        AxisAlignedBB impactBox = this.boundingBox.expand(6D, 4D, 6D); 
	        List hitList = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, impactBox);
	        List<EntityLivingBase> canRideList = new ArrayList();
	        
	        //搜尋list, 找出第一個可以騎乘的目標
	        if(hitList != null && !hitList.isEmpty()) {
	            for(int i = 0; i < hitList.size(); ++i) {
	            	getEnt = (EntityLivingBase)hitList.get(i);
	            	
	            	//只騎乘同主人的棲艦或者主人
	            	if(getEnt instanceof BasicEntityShip || getEnt instanceof EntityPlayer) {
	            		if(getEnt != this && !getEnt.isRiding() && getEnt.riddenByEntity == null &&
	            		   EntityHelper.checkSameOwner(this, getEnt)) {
	            			canRideList.add(getEnt);
	            		 }
	            	}
	            }
	        }
	        
	        //從可騎乘目標中挑出一個目標騎乘
	        if(canRideList.size() > 0) {
	        	this.goRideEntity = canRideList.get(rand.nextInt(canRideList.size()));
	        	this.goRidingTicks = 0;
    			this.goRiding = true;
	        }
		}//end not riding
	}

	@Override
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null) {
			if(itemstack.getItem() == Items.cake) {
				this.setShipOutfit(player.isSneaking());
				return true;
			}
		}
		
		return super.interact(player);
  	}
	
	@Override
	public int getKaitaiType() {
		return 1;
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk) {
		//若騎乘別的ship, 則取消騎乘
		if(this.isRiding() && (ridingEntity instanceof BasicEntityShip || ridingEntity instanceof EntityPlayer)) {
			this.mountEntity(null);
		}
		
		return super.attackEntityFrom(attacker, atk);
	}
  	
  	//貓章魚燒攻擊
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target) {
		//計算目標距離
		float tarX = (float)target.posX;	//for miss chance calc
		float tarY = (float)target.posY;
		float tarZ = (float)target.posZ;
		float[] distVec = new float[4];
		float launchPos = (float)posY + height;
		
		distVec[0] = tarX - (float)this.posX;
		distVec[1] = tarY - (float)this.posY;
		distVec[2] = tarZ - (float)this.posZ;
		distVec[3] = MathHelper.sqrt_float(distVec[0]*distVec[0] + distVec[1]*distVec[1] + distVec[2]*distVec[2]);

        if(getShipDepth() > 0D) {
        	launchPos += 0.2D;
        }
		
		//experience++
		addShipExp(16);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
  		this.setStateMinor(ID.M.Morale, this.getStateMinor(ID.M.Morale) - 5);
		
		//play attack effect
        applySoundAtAttacker(2, target);
	    applyParticleAtAttacker(2, target, distVec);

        //heavy ammo--
        if(!decrAmmoNum(1, this.getAmmoConsumption())) {
        	return false;
        }
        
        //spawn missile
        EntityFloatingFort ffort = new EntityFloatingFort(this.worldObj);
        ffort.setAttrs(this.worldObj, this, target, launchPos);
        this.worldObj.spawnEntityInWorld(ffort);
        
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
      	applyEmotesReaction(3);
        
        return true;
  	}
  	
  	@Override
	public double getMountedYOffset() {
  		if(this.isSitting()) {
			if(getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
				return (double)this.height * 0.0F;
  			}
  			else {
  				return (double)this.height * 0.0F;
  			}
  		}
  		else {
  			return (double)this.height * 0.3F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking) {
		//切換裝備顯示
		if(isSneaking) {
			switch(getStateEmotion(ID.S.State2)) {
			case ID.State.EQUIP00_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP01_2, true);
				break;
			case ID.State.EQUIP01_2:
				setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
				break;
			default:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00_2, true);
				break;
			}
		}
		else {
			switch(getStateEmotion(ID.S.State)) {
			case ID.State.EQUIP00:
				setStateEmotion(ID.S.State, ID.State.EQUIP01, true);
				break;
			case ID.State.EQUIP01:
				setStateEmotion(ID.S.State, ID.State.EQUIP02, true);
				break;
			case ID.State.EQUIP02:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
				break;
			default:
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
				break;
			}
		}
	}
	
	@Override
	protected void applyParticleAtAttacker(int type, Entity target, float[] vec) {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch(type) {
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 31, this.posX, this.posY, this.posZ, vec[0], vec[1], vec[2], true), point);
  			break;
  		case 2:  //heavy cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
  		case 3:  //light aircraft
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
  		case 4:  //heavy aircraft
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
			break;
  		}
  	}
  	
	@Override
  	protected void applyParticleAtTarget(int type, Entity target, float[] vec) {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  		
  		switch(type) {
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 30, false), point);
  	        break;
  		case 2:  //heavy cannon
  			break;
  		case 3:  //light aircraft
  			break;
  		case 4:  //heavy aircraft
  			break;
		default: //melee
    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
			break;
  		}
  	}
	
	@Override
	protected void applySoundAtAttacker(int type, Entity target) {
  		switch(type) {
  		case 1:  //light cannon
  			//fire sound
  	      	playSound("random.bow", ConfigHandler.fireVolume * 1.3F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
  	        
  			//entity sound
  			if(this.rand.nextInt(10) > 7) {
  	        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  	        }
  			break;
  		case 2:  //heavy cannon
  			//fire sound
  			playSound("random.bow", ConfigHandler.fireVolume * 1.3F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
  	        
  	        //entity sound
  	        if(this.getRNG().nextInt(10) > 7) {
  	        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  	        }
  			break;
  		case 3:  //light aircraft
  	        playSound(Reference.MOD_ID+":ship-aircraft", ConfigHandler.fireVolume * 0.5F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  			break;
  		case 4:  //heavy aircraft
  	        playSound(Reference.MOD_ID+":ship-aircraft", ConfigHandler.fireVolume * 0.5F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
  			break;
		default: //melee
			if(this.getRNG().nextInt(10) > 6) {
	        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
	        }
			break;
  		}
  	}
	
	@Override
	public float getAttackBaseDamage(int type, Entity target) {
  		switch(type) {
  		case 1:  //light cannon
  			return CalcHelper.calcDamageBySpecialEffect(this, target, StateFinal[ID.ATK], 0);
  		case 2:  //heavy cannon
  			return StateFinal[ID.ATK_H] * 0.75F;
  		case 3:  //light aircraft
  			return StateFinal[ID.ATK_AL];
  		case 4:  //heavy aircraft
  			return StateFinal[ID.ATK_AH];
		default: //melee
			return StateFinal[ID.ATK];
  		}
  	}


}




