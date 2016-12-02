package com.lulan.shincolle.capability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface ICapaTeitoku
{

	/** init method */
	public void init(EntityPlayer entity);
	
	/** save data to nbt */
	public NBTTagCompound saveNBTData(NBTTagCompound nbt);
	
	/** load data from nbt */
	public void loadNBTData(NBTTagCompound nbt);
	
	
}
