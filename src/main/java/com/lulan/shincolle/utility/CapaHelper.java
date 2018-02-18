package com.lulan.shincolle.utility;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * helper for capability
 *
 */
public class CapaHelper
{
	
	
	/**
	 * get item handler
	 *   side: -1:check all side, 0~5:DUNSWE
	 */
	public static IItemHandler getCapaInventory(ICapabilityProvider host, int side)
	{
		return getCapaHandler(host, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
	}
	
	/**
	 * check item handler
	 *   side: -1:check all side, 0~5:DUNSWE
	 */
	public static boolean hasCapaInventory(ICapabilityProvider host, int side)
	{
		return getCapaHandler(host, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side) != null;
	}
	
	/**
	 * get fluid handler
	 *   side: -1:check all side, 0~5:DUNSWE
	 */
	public static IFluidHandler getCapaFluid(ICapabilityProvider host, int side)
	{
		return getCapaHandler(host, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
	}
	
	/**
	 * check fluid handler
	 *   side: -1:check all side, 0~5:DUNSWE
	 */
	public static boolean hasCapaFluid(ICapabilityProvider host, int side)
	{
		return getCapaHandler(host, CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side) != null;
	}
	
	/**
	 * get capability handler
	 *   side: -1:check all side, 0~5:DUNSWE
	 */
	public static <T> T getCapaHandler(ICapabilityProvider host, Capability<T> capa, int side)
	{
		if (host != null)
		{
			//check all sides
			if (side < 0)
			{
				for (int i = 0; i < 6; i++)
				{
					if (host.hasCapability(capa, EnumFacing.getFront(i)))
					{
						return (T) host.getCapability(capa, EnumFacing.getFront(i));
					}
				}
			}
			//check spec side
			else
			{
				return (T) host.getCapability(capa, EnumFacing.getFront(side));
			}
		}
		
		return null;
	}
	
	
}