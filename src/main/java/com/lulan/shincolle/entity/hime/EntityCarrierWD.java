package com.lulan.shincolle.entity.hime;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShipLarge;
import com.lulan.shincolle.entity.ExtendShipProps;
import com.lulan.shincolle.entity.mounts.EntityMountCaWD;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.CalcHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityCarrierWD extends BasicEntityShipLarge {
	
	public EntityCarrierWD(World world) {
		super(world);
		this.setSize(0.6F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DEMON);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.CarrierWD);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.ModelPos = new float[] {-6F, 15F, 0F, 40F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.initTypeModify();
		
		launchHeight = this.height * 1.2F;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Heavy] = false;
	}
	
	@Override
	public float getEyeHeight() {
		return 1.7375F;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 3;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));		   //0100
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));			   //0011
	}
	
	@Override
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null) {
			if(itemstack.getItem() == Items.cake) {
				//切換裝備顯示
				if(player.isSneaking()) {
					switch(getStateEmotion(ID.S.State2)) {
					default:
					case ID.State.NORMAL_2:
						setStateEmotion(ID.S.State2, ID.State.EQUIP00_2, true);
						break;
					case ID.State.EQUIP00_2:
						setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
						break;
					}
				}
				//切換是否騎乘座騎
				else {
					switch(getStateEmotion(ID.S.State)) {
					default:
					case ID.State.NORMAL:
						setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
						break;
					case ID.State.EQUIP00:
						setStateEmotion(ID.S.State, ID.State.NORMAL, true);
						this.setPositionAndUpdate(posX, posY + 2D, posZ);
						break;
					}
				}
				return true;
			}
		}
		
		return super.interact(player);
  	}
	
	@Override
	public int getKaitaiType() {
		return 1;
	}
	
	//輕攻擊, 特效為雙光束砲
	@Override
	public boolean attackEntityWithAmmo(Entity target) {
		//get attack value
		float atk = StateFinal[ID.ATK];
		//set knockback value (testing)
		float kbValue = 0.05F;
		
		//calc equip special dmg: AA, ASM
		atk = CalcHelper.calcDamageByEquipEffect(this, target, atk, 0);
				
        //experience++
  		addShipExp(2);
  		
  		//grudge--
  		decrGrudgeNum(1);
        
        //light ammo -1
        if(!decrAmmoNum(0)) {		//not enough ammo
        	atk = atk * 0.125F;	//reduce damage to 12.5%
        }
        
        //calc dist to target
        float distX = (float) (target.posX - this.posX);
        float distY = (float) (target.posY - this.posY);
        float distZ = (float) (target.posZ - this.posZ);
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        distX = distX / distSqrt;
        distY = distY / distSqrt;
        distZ = distZ / distSqrt;
        
        //play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-laser", 0.2F, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //play entity attack sound
        if(this.rand.nextInt(10) > 7) {
        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }
        
        //calc parrticle height
        double shotHeight = 1.85D;
        if(this.isRiding() && this.ridingEntity instanceof BasicEntityMount) {
        	shotHeight = 0.8D;
        }
        
        //發射者煙霧特效
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, target, shotHeight, 0D, 0D, 0, true), point);

        //calc miss chance, if not miss, calc cri/multi hit
        float missChance = 0.2F + 0.15F * (distSqrt / StateFinal[ID.HIT]) - 0.001F * StateMinor[ID.M.ShipLevel];
        missChance -= EffectEquip[ID.EF_MISS];		//equip miss reduce
        if(missChance > 0.35F) missChance = 0.35F;	//max miss chance
        
        //calc miss -> crit -> double -> tripple
  		if(rand.nextFloat() < missChance) {
          	atk = 0F;	//still attack, but no damage
          	//spawn miss particle
      		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
  		}
  		else {
  			//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
  			//calc critical
          	if(rand.nextFloat() < this.getEffectEquip(ID.EF_CRI)) {
          		atk *= 1.5F;
          		//spawn critical particle
          		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 11, false), point);
          	}
          	else {
          		//calc double hit
              	if(rand.nextFloat() < this.getEffectEquip(ID.EF_DHIT)) {
              		atk *= 2F;
              		//spawn double hit particle
              		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 12, false), point);
              	}
              	else {
              		//calc double hit
                  	if(rand.nextFloat() < this.getEffectEquip(ID.EF_THIT)) {
                  		atk *= 3F;
                  		//spawn triple hit particle
                  		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 13, false), point);
                  	}
              	}
          	}
  		}
  		
  		//calc damage to player
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
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);

	    //if attack success
	    if(isTargetHurt) {
	    	//calc kb effect
	        if(kbValue > 0) {
	            target.addVelocity(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * kbValue, 
	                   0.1D, MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue);
	            motionX *= 0.6D;
	            motionZ *= 0.6D;
	        }
	        
        	//display hit particle on target
	        TargetPoint point1 = new TargetPoint(this.dimension, target.posX, target.posY, target.posZ, 64D);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point1);
        }

	    return isTargetHurt;
	}
  	
  	//禁用重型攻擊
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target) {
  		return false;
  	}
	
  	//避免跟rider2碰撞
  	@Override
	public boolean canBePushed() {
        return this.ridingEntity == null;
    }
  	
  	//true if use mounts
  	@Override
  	public boolean canSummonMounts() {
  		return true;
  	}
  	
  	@Override
  	public BasicEntityMount summonMountEntity() {
		return new EntityMountCaWD(worldObj, this);
	}
  	
  	@Override
  	public float[] getModelPos() {
  		if(this.isRiding()) {
  			ModelPos[1] = -15F;
  		}
  		else {
  			ModelPos[1] = 15F;
  		}
  		
		return ModelPos;
	}
  	
  	//被騎乘的高度
  	@Override
	public double getMountedYOffset() {
  		if(this.isSitting()) {
  			return (double)this.height * 0.2F;
  		}
  		else {
  			return (double)this.height * 0.62F;
  		}
	}


}




