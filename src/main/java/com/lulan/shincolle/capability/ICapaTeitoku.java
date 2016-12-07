package com.lulan.shincolle.capability;

import net.minecraft.nbt.NBTTagCompound;

public interface ICapaTeitoku
{

	//save data to nbt
	public NBTTagCompound saveNBTData(NBTTagCompound nbt);
	
	//load data from nbt
	public void loadNBTData(NBTTagCompound nbt);
	
}
