package com.lulan.shincolle.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * teitoku data capability provider
 * tut: http://www.planetminecraft.com/blog/forge-tutorial-capability-system/
 *
 * capability副本提供器
 */
public class CapaTeitokuProvider implements ICapabilitySerializable<NBTBase>
{

	@CapabilityInject(ICapaTeitoku.class)
	public static final Capability<ICapaTeitoku> TeitokuCapability = null;
	
	private ICapaTeitoku instance = TeitokuCapability.getDefaultInstance();
	 
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == CapaTeitokuProvider.TeitokuCapability;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapaTeitokuProvider.TeitokuCapability)
		{
			return TeitokuCapability.cast(this.instance);
		}
		
		return null;
	}

	/** save data to nbt */
	@Override
	public NBTBase serializeNBT()
	{
		return TeitokuCapability.getStorage().writeNBT(TeitokuCapability, this.instance, null);
	}

	/** load data from nbt */
	@Override
	public void deserializeNBT(NBTBase nbt)
	{
		TeitokuCapability.getStorage().readNBT(TeitokuCapability, this.instance, null, nbt);
	}
	
	
}
