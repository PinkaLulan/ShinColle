package com.lulan.shincolle.entity;

import net.minecraft.entity.player.EntityPlayer;

/**
 * inter-mod support interface for Metamorph
 */
public interface IShipMorph
{
	
	
	/** entity is morph */
	public boolean isMorph();
	public void setIsMorph(boolean par1);
	
	/** morph host */
	public EntityPlayer getMorphHost();
	public void setMorphHost(EntityPlayer player);
	
	
}