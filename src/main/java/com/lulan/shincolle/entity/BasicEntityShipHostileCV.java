package com.lulan.shincolle.entity;

import com.lulan.shincolle.entity.other.EntityAirplaneTMob;
import com.lulan.shincolle.entity.other.EntityAirplaneZeroMob;
import com.lulan.shincolle.reference.dataclass.Dist4d;
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

	
	
	
}