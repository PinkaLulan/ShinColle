package com.lulan.shincolle.utility;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class NBTHelper
{

	
	public NBTHelper() {}
	
	public static void saveIntListToNBT(NBTTagCompound save, String tagName, List<Integer> ilist)
	{
		if (save != null)
		{
			if (ilist != null && ilist.size() > 0)
			{
		    	int[] intary = CalcHelper.intListToArray(ilist);
		    	save.setIntArray(tagName, intary);
		    }
			else
			{
				save.setIntArray(tagName, new int[] {});
			}
		}
		else
		{
			LogHelper.debug("DEBUG: NBT helper: save nbt fail: tag is null ");
		}
	}
	
	/**
	 * get fluid stack from item stack, used for 1.7.10 fluid container without capability
	 */
	public static FluidStack getFluidStackFromItemStack(ItemStack stack)
	{
		FluidStack fs = null;
		
		try
		{
			NBTTagCompound tag = stack.getTagCompound();
			NBTTagCompound tag2 = null;
			
			if (tag != null)
			{
				if (tag.hasKey("fluid")) tag2 = tag.getCompoundTag("fluid");
				else if (tag.hasKey("Fluid")) tag2 = tag.getCompoundTag("Fluid");
				
				if (tag2 != null)
				{
					fs = new FluidStack(FluidRegistry.getFluid(tag2.getString("FluidName")),
										tag2.getInteger("Amount"));
				}
			}
		}
		catch (Exception e)
		{
			return null;
		}
		
		return fs;
	}
	
	
}
