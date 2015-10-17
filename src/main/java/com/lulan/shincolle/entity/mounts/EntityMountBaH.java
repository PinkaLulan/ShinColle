package com.lulan.shincolle.entity.mounts;

import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

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


}

