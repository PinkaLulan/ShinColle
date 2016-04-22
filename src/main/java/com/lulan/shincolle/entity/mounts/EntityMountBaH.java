package com.lulan.shincolle.entity.mounts;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.hime.EntityBattleshipHime;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityMountBaH extends BasicEntityMount {
	
    public EntityMountBaH(World world) {	//client side
		super(world);
		this.setSize(1.9F, 3.1F);
		this.isImmuneToFire = true;
		this.ridePos = new float[] {1.2F, -1.3F, 1.1F};
	}
    
    public EntityMountBaH(World world, BasicEntityShip host) {	//server side
		super(world);
        this.host = host;
        this.isImmuneToFire = true;
        this.ridePos = new float[] {1.2F, -1.3F, 1.1F};
        
        //basic attr
        this.atkRange = host.getStateFinal(ID.HIT);
        this.movSpeed = host.getStateFinal(ID.MOV);
        
        //AI flag
        this.StateEmotion = 0;
        this.StateEmotion2 = 0;
        this.StartEmotion = 0;
        this.StartEmotion2 = 0;
        this.headTilt = false;
           
        //設定位置
        this.posX = host.posX;
        this.posY = host.posY;
        this.posZ = host.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
 
        //設定基本屬性
        setupAttrs();
        
		if(this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
	}
    
    @Override
	public float getEyeHeight() {
    	return 1.7F;
	}
    
    @Override
    public double getMountedYOffset() {
        return 2.754D;
    }

	@Override
	public int getDamageType() {
		return ID.ShipDmgType.BATTLESHIP;
	}
	
	//change melee damage to 100%
  	@Override
  	public boolean attackEntityAsMob(Entity target) {
  		//get attack value
  		float atk = host.getStateFinal(ID.ATK) * 3F;
  				
  	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
  	    //並且回傳是否成功傷害到目標
  	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk);

  	    //play entity attack sound
  	    if(this.getRNG().nextInt(10) > 8) {
  	    	this.playSound(Reference.MOD_ID+":ship-waka_attack", ConfigHandler.shipVolume * 0.5F, 1F);
  	    }
  	    
  	    //if attack success
  	    if(isTargetHurt) {
  	        //send packet to client for display partical effect   
  	        if (!worldObj.isRemote) {
  	        	TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  	    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
  			}
  	        
  		    //show emotes
  	  	    if(host != null) {
  	  	    	host.applyEmotesReaction(3);
  	  	    	
  	  	    	if(ConfigHandler.canFlare) {
  	  				host.flareTarget(target);
  	  			}
  	  	    }
  	    }

  	    return isTargetHurt;
  	}
	
	//use host's cluster bomb
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target) {
		if(this.host instanceof EntityBattleshipHime) {
			return ((EntityBattleshipHime) host).attackEntityWithHeavyAmmo(target);
		}
		else {
			return super.attackEntityWithHeavyAmmo(target);
		}
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
	
	//special rider position
	@Override
  	public void updateRiderPosition() {
        super.updateRiderPosition();
        
        //sync rotate angle
  		if(this.riddenByEntity != null && this.host != null) {
  			float[] ridePos = ParticleHelper.rotateXZByAxis(1.05F, 0F, this.renderYawOffset * Values.N.RAD_MUL, 1F);	
			this.riddenByEntity.setPosition(this.host.posX + ridePos[1], this.host.posY + 0D, this.host.posZ + ridePos[0]);
  		}
    }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//client side
		if(this.worldObj.isRemote) {
			//add smoke particle
			if(this.ticksExisted % 5 == 0) {
  				//生成裝備冒煙特效
  				ParticleHelper.spawnAttackParticleAt(posX, posY + 3D, posZ, 0D, 0D, 0D, (byte)20);
  			}
		}
	}


}

