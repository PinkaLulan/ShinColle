package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.reference.ID;

import net.minecraft.world.World;

public class EntityAirplaneT extends EntityAirplaneTakoyaki
{
	
	public EntityAirplaneT(World world)
	{
		super(world);
		this.setSize(0.5F, 0.5F);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.ticksExisted == 6)
		{
			this.playSound(ModSounds.SHIP_AIRCRAFT, 0.4F, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}
	}
	
	@Override
	protected void applyFlyParticle() {}
	
	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.AirplaneT;
	}


}
