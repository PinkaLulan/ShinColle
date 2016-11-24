package com.lulan.shincolle.utility;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

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
			LogHelper.info("DEBUG : NBT helper: save nbt fail: tag is null ");
		}
	}
	
	
}
