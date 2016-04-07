package com.lulan.shincolle.entity.other;

import net.minecraft.world.World;

import com.lulan.shincolle.reference.Reference;

public class EntityAirplaneT extends EntityAirplaneTakoyaki {
	
	public EntityAirplaneT(World world) {
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(this.ticksExisted == 6) {
			playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}
	}
	
	@Override
	protected void applyFlyParticle() {
//		ParticleHelper.spawnAttackParticleAt(this.posX-this.motionX*1.5D, this.posY+0.5D-this.motionY*1.5D, this.posZ-this.motionZ*1.5D, 
//          		-this.motionX*0.5D, -this.motionY*0.5D, -this.motionZ*0.5D, (byte)17);
	}


}
