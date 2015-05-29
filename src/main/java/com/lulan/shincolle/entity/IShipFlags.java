package com.lulan.shincolle.entity;

/**STATE getter/setter
 */
public interface IShipFlags {
	
	/**Get state flag
	 * flag: 8:head tilt
	 */
	public boolean getStateFlag(int flag);
	
	/**Set state flag
	 * for individual entity displaying head tilt or other emotion
	 * flag: 8:head tilt
	 * sync: send sync packet to client?
	 */
	public void setStateFlag(int id, boolean flag);

	/**Get state minor
	 * state = ID.N.xxx , include level, kills, follow range...
	 */
	public int getStateMinor(int id);
	
	/**Set state minor
	 * state = ID.N.xxx , include level, kills, follow range...
	 */
	public void setStateMinor(int state, int par1);
	
}
