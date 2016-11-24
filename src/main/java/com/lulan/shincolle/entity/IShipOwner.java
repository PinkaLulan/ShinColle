package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;

public interface IShipOwner
{

	/** get player UID for owner checking */
	public int getPlayerUID();
	public void setPlayerUID(int uid);

	/** get host entity */
	public Entity getHostEntity();
	
	
}
