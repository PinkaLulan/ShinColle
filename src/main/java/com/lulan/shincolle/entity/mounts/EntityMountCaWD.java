package com.lulan.shincolle.entity.mounts;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMountLarge;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

public class EntityMountCaWD extends BasicEntityMountLarge {
	
    public EntityMountCaWD(World world) {	//client side
		super(world);
		this.setSize(1.9F, 1.8F);
		this.isImmuneToFire = true;
		this.ridePos = new float[] {0.0F, 0F, 0.5F};
	}
    
    public EntityMountCaWD(World world, BasicEntityShip host) {	//server side
		super(world);
        this.host = host;
        this.isImmuneToFire = true;
        this.ridePos = new float[] {0.0F, 0F, 0.5F};
        
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
	
	//輕攻擊, 特效為雙光束砲
	@Override
	public boolean attackEntityWithAmmo(Entity target) {
		if(this.host != null) {
			return host.attackEntityWithAmmo(target);
		}
		
		return false;
	}
	
	//停用重攻擊
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target) {
		return false;
	}

	@Override
	public int getDamageType() {
		return ID.ShipDmgType.CARRIER;
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(10, new EntityAIShipCarrierAttack(this));		   //0100
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));			   //0011
	}
	

}





