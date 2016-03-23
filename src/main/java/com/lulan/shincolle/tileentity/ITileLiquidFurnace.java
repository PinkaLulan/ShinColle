package com.lulan.shincolle.tileentity;

import net.minecraftforge.fluids.FluidStack;

/** Liquid Fuel Methods for tile entity INTERNAL operation
 * 
 *  for EXTERNAL liquid method, use IFluidHandler
 */
public interface ITileLiquidFurnace extends ITileFurnace {

	/** get liquid amount */
	public int getFluidFuelAmount();
	
	/** drain liquid */
	public FluidStack drainFluidFuel(int amount);
	
	
}
