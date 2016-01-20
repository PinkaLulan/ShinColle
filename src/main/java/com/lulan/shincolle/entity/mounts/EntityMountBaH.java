package com.lulan.shincolle.entity.mounts;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.hime.EntityBattleshipHime;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.ParticleHelper;

public class EntityMountBaH extends BasicEntityMount {
	
    public EntityMountBaH(World world) {	//client side
		super(world);
		this.setSize(1.9F, 2.7F);
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
	
	//use host's cluster bomb
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target) {
		if(this.host instanceof EntityBattleshipHime) {
			return ((EntityBattleshipHime) host).attackEntityWithSpecialAmmo(target);
		}
		else {
			return super.attackEntityWithHeavyAmmo(target);
		}
	}
	
	@Override
	public void setAIList() {
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));			   //0011
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
	


}

