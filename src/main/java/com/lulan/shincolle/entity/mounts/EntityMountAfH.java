package com.lulan.shincolle.entity.mounts;

import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityMountLarge;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;

public class EntityMountAfH extends BasicEntityMountLarge {
	
    public EntityMountAfH(World world) {	//client side
		super(world);
		this.setSize(1.9F, 1.3F);
		this.isImmuneToFire = true;
		this.ridePos = new float[] {-1F, -1F, 1.5F};
	}
    
    public EntityMountAfH(World world, BasicEntityShip host) {	//server side
		super(world);
        this.host = host;
        this.isImmuneToFire = true;
        this.ridePos = new float[] {-1F, -1F, 1.5F};
        
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
			if(this.ticksExisted % 8 == 0) {
				//嘴巴冒紅煙特效
				float[] partPos1 = ParticleHelper.rotateXZByAxis(0F, -1.0F, this.renderYawOffset / 57.2958F, 1F);
				float[] partPos2 = ParticleHelper.rotateXZByAxis(0F, -1.8F, this.renderYawOffset / 57.2958F, 1F);
				ParticleHelper.spawnAttackParticleAt(this.posX + partPos1[1], this.posY + 0.9F, this.posZ + partPos1[0], 
							0D, 0.1D, 0D, (byte)18);
				ParticleHelper.spawnAttackParticleAt(this.posX + partPos2[1], this.posY + 0.9F, this.posZ + partPos2[0], 
						0D, 0.1D, 0D, (byte)18);
			}
		}
	}


}


