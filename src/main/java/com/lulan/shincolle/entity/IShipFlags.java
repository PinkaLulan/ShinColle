package com.lulan.shincolle.entity;

/**STATE getter/setter
 */
public interface IShipFlags
{
	
	/**
	 * minor states like level, kills, ammo...
	 */
	public int getStateMinor(int id);
	public void setStateMinor(int state, int par1);
	
	/**
	 * state flags
	 */
	public boolean getStateFlag(int flag);
	public void setStateFlag(int id, boolean flag);

	/**
	 * update flag
	 */
	public void setUpdateFlag(int id, boolean value);
	public boolean getUpdateFlag(int id);
	
	
}