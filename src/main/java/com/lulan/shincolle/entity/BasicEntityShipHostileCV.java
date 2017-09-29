package com.lulan.shincolle.entity;

import com.lulan.shincolle.entity.other.EntityAirplaneTMob;
import com.lulan.shincolle.entity.other.EntityAirplaneZeroMob;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.BlockHelper;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

abstract public class BasicEntityShipHostileCV extends BasicEntityShipHostile implements IShipAircraftAttack
{

	protected double launchHeight;		//airplane launch height
	
	
	public BasicEntityShipHostileCV(World world)
	{
		super(world);
	}

	@Override
	abstract public int getDamageType();

	@Override
	public int getNumAircraftLight()
	{
		return 10;
	}

	@Override
	public int getNumAircraftHeavy()
	{
		return 10;
	}

	@Override
	public boolean hasAirLight()
	{
		return true;
	}

	@Override
	public boolean hasAirHeavy()
	{
		return true;
	}

	@Override
	public void setNumAircraftLight(int par1) {}

	@Override
	public void setNumAircraftHeavy(int par1) {}

	@Override
	public boolean attackEntityWithAircraft(Entity target)
	{
		//play attack effect
        applySoundAtAttacker(3, target);
	    applyParticleAtAttacker(3, target, Dist4d.ONE);
        
    	float summonHeight = (float)(posY + launchHeight);
    	
    	//check the summon block
    	if (!BlockHelper.checkBlockSafe(world, (int)posX, (int)(posY+launchHeight), (int)(posZ)))
    	{
    		summonHeight = (float)posY + height * 0.75F;
    	}
    	
    	BasicEntityAirplane plane = new EntityAirplaneZeroMob(this.world);
        plane.initAttrs(this, target, this.scaleLevel, summonHeight);
    	this.world.spawnEntity(plane);
    	
        //show emotes
		applyEmotesReaction(3);
		
        return true;
	}

	@Override
	public boolean attackEntityWithHeavyAircraft(Entity target)
	{
		//play attack effect
        applySoundAtAttacker(4, target);
	    applyParticleAtAttacker(4, target, Dist4d.ONE);
	    
    	float summonHeight = (float) (posY + launchHeight);
    	
    	//check the summon block
    	if (!BlockHelper.checkBlockSafe(world, (int)posX, (int)(posY+launchHeight), (int)(posZ)))
    	{
    		summonHeight = (float)posY + height * 0.75F;
    	}
    	
    	BasicEntityAirplane plane = new EntityAirplaneTMob(this.world);
        plane.initAttrs(this, target, this.scaleLevel, summonHeight);
    	this.world.spawnEntity(plane);
    	
        //show emotes
		applyEmotesReaction(3);
		
        return true;
	}
	
	
}