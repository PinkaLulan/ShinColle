package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;

public interface IShipOwner
{

	/**
	 * get player UID for owner checking
	 * UID: -1, 0: no owner, 1~N: player owner, -2~-N: hostile mob
	 */
	public int getPlayerUID();
	public void setPlayerUID(int uid);

	/** get host entity */
	public Entity getHostEntity();
	
	
}