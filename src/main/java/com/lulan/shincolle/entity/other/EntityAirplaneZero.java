package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.reference.ID;

import net.minecraft.world.World;

public class EntityAirplaneZero extends EntityAirplane
{
	
	public EntityAirplaneZero(World world)
	{
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if(this.ticksExisted == 6)
		{
//			//TODO sound event
//			playSound(Reference.MOD_ID+":ship-aircraft", 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}
	}
	
	@Override
	protected void applyFlyParticle()
	{
//		ParticleHelper.spawnAttackParticleAt(this.posX-this.motionX*1.5D, this.posY+0.5D-this.motionY*1.5D, this.posZ-this.motionZ*1.5D, 
//          		-this.motionX*0.5D, -this.motionY*0.5D, -this.motionZ*0.5D, (byte)17);
	}
	
	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.AirplaneZ;
	}


}