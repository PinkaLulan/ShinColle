package com.lulan.shincolle.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

/**
 * teitoku data capability storage
 * tut: http://www.planetminecraft.com/blog/forge-tutorial-capability-system/
 *
 * 負責將capability data存到nbt使其保存到硬碟
 */
public class CapaTeitokuStorage implements IStorage<ICapaTeitoku>
{

	
	/** save data to nbt */
	@Override
	public NBTBase writeNBT(Capability<ICapaTeitoku> capability, ICapaTeitoku instance, EnumFacing side)
	{
		return instance.saveNBTData(new NBTTagCompound());
	}

	/** load data from nbt */
	@Override
	public void readNBT(Capability<ICapaTeitoku> capability, ICapaTeitoku instance, EnumFacing side, NBTBase nbt)
	{
		instance.loadNBTData((NBTTagCompound) nbt);
	}
	
	
}
