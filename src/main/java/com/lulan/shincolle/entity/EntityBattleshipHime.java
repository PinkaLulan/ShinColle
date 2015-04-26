package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipAttackOnCollide;
import com.lulan.shincolle.ai.EntityAIShipFlee;
import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipFollowOwner;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.EntityAIShipSit;
import com.lulan.shincolle.ai.EntityAIShipWatchClosest;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityBattleshipHime extends BasicEntityShip {
	
	public EntityBattleshipHime(World world) {
		super(world);
		this.setSize(0.8F, 1.6F);
//		this.setCustomNameTag(StatCollector.translateToLocal("entity.shincolle.EntityBattleshipHime.name"));
		this.ShipType = ID.ShipType.HIME;
		this.ShipID = ID.S_BattleshipHime;
		this.ModelPos = new float[] {-6F, 15F, 0F, 40F};
		ExtProps = (ExtendShipProps) getExtendedProperties(ExtendShipProps.SHIP_EXTPROP_NAME);	
		this.initTypeModify();
	}
	
	@Override
	public float getEyeHeight() {
		return this.height;
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType() {
		return 1;
	}
	
	public void setAIList() {
		super.setAIList();
		
		//high priority
		this.tasks.addTask(1, new EntityAIShipSit(this));	   				   //0101
		this.tasks.addTask(2, new EntityAIShipFlee(this));					   //0111
		this.tasks.addTask(3, new EntityAIShipFollowOwner(this));	   		   //0111
		
		//use range attack
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));			   //0011
		
		//use melee attack
		if(this.getStateFlag(ID.F.UseMelee)) {
			this.tasks.addTask(12, new EntityAIShipAttackOnCollide(this, 1D, true));   //0011
			this.tasks.addTask(13, new EntityAIMoveTowardsTarget(this, 1D, 48F));  //0001
		}
		
		//idle AI
		//moving
		this.tasks.addTask(21, new EntityAIOpenDoor(this, true));			   //0000
		this.tasks.addTask(23, new EntityAIShipFloating(this));				   //0101
		this.tasks.addTask(24, new EntityAIShipWatchClosest(this, EntityPlayer.class, 6F, 0.05F)); //0010
		this.tasks.addTask(25, new EntityAIWander(this, 0.8D));				   //0001
		this.tasks.addTask(25, new EntityAILookIdle(this));					   //0011

	}
	
	//check entity state every tick
  	@Override
  	public void onLivingUpdate() {
  		//server side
  		if(!worldObj.isRemote) {
  			//check every second
  			if(this.ticksExisted % 20 == 0) {
  				//summon mount if emotion state = equip00
  	  	  		if(getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
  	  	  			if(!this.isRiding()) {
  	  	  				LogHelper.info("DEBUG : ship summon mount");
  	  	  				//summon mount entity
  	  	  	  			EntityMountBaH mount = new EntityMountBaH(worldObj, this);
  	  	  	  			this.worldObj.spawnEntityInWorld(mount);
  	  	  	  			
  	  	  	  			//set riding entity
	  	  	  			this.mountEntity(mount);
  	  	  			}
  	  	  		}
  	  	  		else {
  	  	  			//cancel riding
  	  	  			if(this.isRiding() && this.ridingEntity instanceof EntityMountBaH) {
  	  	  				this.ridingEntity.setDead();
  	  	  				this.ridingEntity = null;
  	  	  			}
  	  	  		}
  			}	
  		}	
  			
  		super.onLivingUpdate();
  	}
	
	@Override
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null) {
			if(itemstack.getItem() == Items.cake) {
				switch(getStateEmotion(ID.S.State)) {
				case ID.State.NORMAL:
					setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
					break;
				case ID.State.EQUIP00:
					setStateEmotion(ID.S.State, ID.State.NORMAL, true);
					break;
				default:
					setStateEmotion(ID.S.State, ID.State.NORMAL, true);
					break;
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
	
	//change melee damage to 100%
	@Override
	public boolean attackEntityAsMob(Entity target) {
		//check riding
  		if(this.isRiding()) {
  			//stop attack if riding ship mount
  			if(this.ridingEntity instanceof EntityMountBaH) {
  				return false;
  			}
  		}
  		
		//get attack value
		float atk = StateFinal[ID.ATK];
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
	    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
			}
	    }

	    return isTargetHurt;
	}
	
	//修改煙霧特效 & 檢查是否riding
  	@Override
  	public boolean attackEntityWithAmmo(Entity target) {
  		//check riding
  		if(this.isRiding()) {
  			//stop attack if riding ship mount
  			if(this.ridingEntity instanceof EntityMountBaH) {
  				return false;
  			}
  		}
  		
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
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 19, this.posX, this.posY+0.3D, this.posZ, distX, 1F, distZ, true), point);

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
    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
        }
        else {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if(this.rand.nextFloat() < EffectEquip[ID.EF_CRI]) {
        		atk *= 1.5F;
        		//spawn critical particle
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 11, false), point);
        	}
        	else {
        		//calc double hit
            	if(this.rand.nextFloat() < EffectEquip[ID.EF_DHIT]) {
            		atk *= 2F;
            		//spawn double hit particle
            		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 12, false), point);
            	}
            	else {
            		//calc double hit
                	if(this.rand.nextFloat() < EffectEquip[ID.EF_THIT]) {
                		atk *= 3F;
                		//spawn triple hit particle
                		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 13, false), point);
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
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point1);
        }

	    return isTargetHurt;
	}
  	
  	//檢查是否riding
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target) {
  	//check riding
  		if(this.isRiding()) {
  			//stop attack if riding ship mount
  			if(this.ridingEntity instanceof EntityMountBaH) {
  				return false;
  			}
  		}
  		
  		return super.attackEntityWithHeavyAmmo(target);
  	}
	
  	//BUG: NOT WORKING
  	@Override
	public boolean canBePushed() {
        return this.ridingEntity == null;
    }
	
}


