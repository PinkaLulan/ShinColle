package com.lulan.shincolle.entity.battleship;

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

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

/**特殊heavy attack:
 * 用StateEmotion[ID.S.Phase]來儲存攻擊階段
 * Phase 1:集氣 2:爆氣 3:集氣 
 */
public class EntityBattleshipNGT extends BasicEntityShipSmall {

	public EntityBattleshipNGT(World world) {
		super(world);
		this.setSize(0.7F, 2F);	//碰撞大小 跟模型大小無關
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.BattleshipNagato);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.ModelPos = new float[] {0F, 15F, 0F, 40F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(20);
		
		//set attrs modify
		this.initTypeModify();
	}
	
	//for morph
	@Override
	public float getEyeHeight() {
		return 1.7375F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 1;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();

		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
    
    //check entity state every tick
  	@Override
  	public void onLivingUpdate() {
  		super.onLivingUpdate();
  		
  		if(worldObj.isRemote) {
  			if(this.ticksExisted % 10 == 0) {
  				if(getStateEmotion(ID.S.Phase) > 0) {
   	  				//生成氣彈特效
  	  				ParticleHelper.spawnAttackParticleAtEntity(this, 0.1D, 1D, 0D, (byte)1);
  				}
  			}
			
  			if(this.ticksExisted % 4 == 0) {
  				if(getStateEmotion(ID.S.State) >= ID.State.EQUIP01 && !isSitting() && !getStateFlag(ID.F.NoFuel)) {
  					double smokeY = posY + 1.6D;
  					
  					//計算煙霧位置
  	  				float[] partPos = ParticleHelper.rotateXZByAxis(-0.55F, 0F, (this.renderYawOffset % 360) / 57.2957F, 1F);
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
  				}	
  			}
  		}    
  	}
  	
  	@Override
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null) {
			if(itemstack.getItem() == Items.cake) {
				setShipOutfit(player.isSneaking());
				return true;
			}
		}
		
		super.interact(player);
		return false;
  	}
  	
  	/**Type 91 Armor-Piercing Fist
  	 * 需要進行4階段攻擊(3階段準備), 對目標造成重攻擊的4倍傷害, 額外追加8x8範圍1倍傷害
  	 */
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target) {
  		//get attack value
		float atk1, atk2;
		
		//calc equip special dmg: AA, ASM
  		atk1 = CalcHelper.calcDamageBySpecialEffect(this, target, StateFinal[ID.ATK_H], 2);
  		atk2 = StateFinal[ID.ATK_H] * 2F;  //AE dmg without modifier
		
		boolean isTargetHurt = false;

		//計算目標距離
		float tarX = (float)target.posX;	//for miss chance calc
		float tarY = (float)target.posY;
		float tarZ = (float)target.posZ;
		float distX = tarX - (float)this.posX;
		float distY = tarY - (float)this.posY;
		float distZ = tarZ - (float)this.posZ;
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        float dX = distX / distSqrt;
        float dY = distY / distSqrt;
        float dZ = distZ / distSqrt;
		
		//experience++
		addShipExp(16);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
		
		//heavy ammo--
        if(!decrAmmoNum(1, this.getAmmoConsumption())) {
        	return false;
        }
	
		//play cannon fire sound at attacker
		int atkPhase = getStateEmotion(ID.S.Phase);
		
        switch(atkPhase) {
        case 0:
        case 2:
        	this.playSound(Reference.MOD_ID+":ship-ap_phase1", ConfigHandler.fireVolume, 1F);
        	break;
        case 1:
        	this.playSound(Reference.MOD_ID+":ship-ap_phase2", ConfigHandler.fireVolume, 1F);
        	break;
        case 3:
        	this.playSound(Reference.MOD_ID+":ship-ap_attack", ConfigHandler.fireVolume, 1F);
        	break;
    	default:
    		this.playSound(Reference.MOD_ID+":ship-ap_phase1", ConfigHandler.fireVolume, 1F);
    		break;
        }
        
        //play entity attack sound
        if(this.getRNG().nextInt(10) > 7) {
        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }
        
        //phase++
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        atkPhase++;
      
        if(atkPhase > 3) {	//攻擊準備完成, 計算攻擊傷害
        	//display hit particle on target
	        CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 21, posX, posY, posZ, target.posX, target.posY, target.posZ, true), point);
        	
        	//calc miss chance, miss: atk1 = 0, atk2 = 50%
            float missChance = 0.2F + 0.15F * (distSqrt / StateFinal[ID.HIT]) - 0.001F * StateMinor[ID.M.ShipLevel];
            missChance -= EffectEquip[ID.EF_MISS];	//equip miss reduce
            if(missChance > 0.35F) missChance = 0.35F;	//max miss chance = 30%
           
            if(this.rand.nextFloat() < missChance) {	//MISS
            	atk1 = 0F;
            	atk2 *= 0.5F;
            	//spawn miss particle
            	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
            }
            else if(this.rand.nextFloat() < EffectEquip[ID.EF_CRI]) {	//CRI
        		atk1 *= 1.5F;
        		atk2 *= 1.5F;
        		//spawn critical particle
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 11, false), point);
            }
            
            //vs player = 25% dmg
      		if(target instanceof EntityPlayer) {
      			atk1 *= 0.25F;
      			atk2 *= 0.25F;
      			
      			//check friendly fire
        		if(!ConfigHandler.friendlyFire) {
        			atk1 = 0F;
        			atk2 = 0F;
        		}
        		else if(atk2 > 40F) {
        			atk2 = 40F;		//TNT
        		}
      		}
      		
      		//對本體造成atk1傷害
      		isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk1);
      		
  			this.motionX = 0D;
  			this.motionY = 0D;
  			this.motionZ = 0D;
  			this.posX = tarX+dX*2F;
  			this.posY = tarY;
  			this.posZ = tarZ+dZ*2F;
  			this.setPosition(posX, posY, posZ);
      		
      		//對範圍造成atk2傷害
            EntityLivingBase hitEntity = null;
            AxisAlignedBB impactBox = this.boundingBox.expand(3.5D, 3.5D, 3.5D); 
            List hitList = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, impactBox);
            float atkTemp = atk2;
            
            //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
            if(hitList != null && !hitList.isEmpty()) {
                for(int i=0; i<hitList.size(); ++i) {
                	atkTemp = atk2;
                	hitEntity = (EntityLivingBase)hitList.get(i);
                	
                	//目標不能是自己 or 主人
                	if(hitEntity != this && EntityHelper.checkAttackable(hitEntity) && hitEntity.canBeCollidedWith() && EntityHelper.checkNotSameEntityID(this, hitEntity)) {
                		//calc miss and cri
                		if(this.rand.nextFloat() < missChance) {	//MISS
                        	atkTemp *= 0.5F;
                        }
                        else if(this.rand.nextFloat() < EffectEquip[ID.EF_CRI]) {	//CRI
                    		atkTemp *= 1.5F;
                        }
                		
                		//若攻擊到同陣營entity (ex: owner), 則傷害設為0 (但是依然觸發擊飛特效)
                		if(EntityHelper.checkSameOwner(this, hitEntity)) {
                			atkTemp = 0F;
                    	}
                		
                		//若攻擊到玩家, 最大傷害固定為TNT傷害 (non-owner)
                    	if(hitEntity instanceof EntityPlayer) {
                    		atkTemp *= 0.25F;
                    		
                    		if(atkTemp > 59F) {
                    			atkTemp = 59F;	//same with TNT
                    		}
                    	}
                    	
                    	//check friendly fire
                		if(!EntityHelper.doFriendlyFire(this, hitEntity)) {
                			atkTemp = 0F;
                		}

                		//attack
                	    hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), atkTemp);
                	}//end can be collided with
                }//end hit target list for loop
            }//end hit target list != null
        	
        	this.setStateEmotion(ID.S.Phase, 0, true);
        }
        else {
        	if(atkPhase == 2) {
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 23, this.posX, this.posY, this.posZ, 1D, 0D, 0D, true), point);
        	}
        	else {
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 22, this.posX, this.posY, this.posZ, 1D, 0D, 0D, true), point);
        	}
    		
        	this.setStateEmotion(ID.S.Phase, atkPhase, true);
        }
        
        //show emotes
      	applyEmotesReaction(3);
        
        return isTargetHurt;
	}

	@Override
	public int getKaitaiType() {
		return 3;
	}
	
	@Override
	public double getMountedYOffset() {
		if(this.isSitting()) {
			if(getStateEmotion(ID.S.State) > ID.State.NORMAL) {
				return (double)this.height * 0.4F;
			}
			else {
				if(getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
					return (double)this.height * -0.1F;
	  			}
	  			else {
	  				return (double)this.height * 0.3F;
	  			}
			}
  		}
  		else {
  			return (double)this.height * 0.8F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking) {
		switch(getStateEmotion(ID.S.State)) {
		case ID.State.NORMAL:
			setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
		case ID.State.EQUIP00:
			setStateEmotion(ID.S.State, ID.State.EQUIP01, true);
			break;
		case ID.State.EQUIP01:
			setStateEmotion(ID.S.State, ID.State.EQUIP02, true);
			break;
		default:
			setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
		}
	}
	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, float[] vec) {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch(type) {
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 19, this.posX, this.posY+0.3D, this.posZ, vec[0], 1F, vec[2], true), point);
  			break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
			break;
  		}
  	}


}


