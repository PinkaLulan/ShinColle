package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.util.Constants;

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
	
	//load string tag, return array list
	public static ArrayList<String> loadStringTagArrayList(NBTTagCompound nbt, String tagName)
	{
		//load unit name
		NBTTagList nameTags = nbt.getTagList(tagName, Constants.NBT.TAG_STRING);
		ArrayList<String> nameList = new ArrayList<String>();
		
		for (int i = 0; i < nameTags.tagCount(); ++i)
		{
			String str = nameTags.getStringTagAt(i);

			if (str != null && str.length() > 0)
			{
				nameList.add(str);
			}
		}
		
		return nameList;
	}
	
	//load string tag, return array list
	public static NBTTagCompound saveStringTagArrayList(NBTTagCompound nbt, String tagName, ArrayList<String> strs)
	{
		//save unit name
		if (strs != null)
		{
	    	NBTTagList tagList = new NBTTagList();
			
	    	for (String name : strs)
	    	{
	    		tagList.appendTag(new NBTTagString(name));
	    	}
			
	    	nbt.setTag(tagName, tagList);
		}
		
		return nbt;
	}
	
	
}