package com.lulan.shincolle.entity.mounts;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMountLarge;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.hime.EntityHarbourHime;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;

public class EntityMountHbH extends BasicEntityMountLarge {
	
    public EntityMountHbH(World world) {	//client side
		super(world);
		this.setSize(1.9F, 1.3F);
		this.isImmuneToFire = true;
		this.ridePos = new float[] {-1.4F, 0.4F, 1.5F};
	}
    
    public EntityMountHbH(World world, BasicEntityShip host) {	//server side
		super(world);
        this.host = host;
        this.isImmuneToFire = true;
        this.ridePos = new float[] {-1.4F, 0.4F, 1.5F};
        
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
    	return this.height;
    }

	@Override
	public void onUpdate() {
		super.onUpdate();
//		LogHelper.info("DEBUG : mount depth "+this.ShipDepth);
		
		//client side
		if(this.worldObj.isRemote) {
			if(this.ticksExisted % 20 == 0) {
				//railgun particle
				ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 0D, 0D, (byte)3);
				if(rand.nextInt(3) == 0)
				ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 0D, 0D, (byte)3);
				if(rand.nextInt(3) == 0)
				ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 0D, 0D, (byte)3);
				if(rand.nextInt(3) == 0)
				ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 0D, 0D, (byte)3);
			}
		}
	}
	
	//use host's railgun
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target) {
		if(this.host instanceof EntityHarbourHime) {
			return ((EntityHarbourHime) host).attackEntityWithSpecialAmmo(target);
		}
		else {
			return super.attackEntityWithHeavyAmmo(target);
		}
	}

	@Override
	public int getDamageType() {
		return ID.ShipDmgType.AVIATION;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(10, new EntityAIShipCarrierAttack(this));		   //0100
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));			   //0011
	}


}


