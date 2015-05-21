package com.lulan.shincolle.entity;

public interface IShipFlags {
	
	/**Get state flag
	 * flag: 8:head tilt
	 */
	abstract public boolean getStateFlag(int flag);
	
	/**Set state flag
	 * for individual entity displaying head tilt or other emotion
	 * flag: 8:head tilt
	 * sync: send sync packet to client?
	 */
	abstract public void setStateFlag(int id, boolean flag);

}
