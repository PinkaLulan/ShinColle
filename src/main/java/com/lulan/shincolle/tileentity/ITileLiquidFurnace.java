package com.lulan.shincolle.tileentity;

/**
 * Fluid Fuel Methods for tile entity
 */
public interface ITileLiquidFurnace extends ITileFurnace
{
	
	
	/** get fuel amount */
	public int getFluidFuelAmount();
	
	/** consume fuel, return consume amount */
	public int consumeFluidFuel(int amount);
	
	
}