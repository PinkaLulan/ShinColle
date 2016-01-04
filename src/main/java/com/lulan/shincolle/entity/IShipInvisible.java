package com.lulan.shincolle.entity;

/** for submarine or invisible entity 
 * 
 *  Invisible LV:
 *  calc by distance, high level = hard to find even get close
 *  entity get dodge chance from invisible level
 *  
 *  ex: level = 0.45F = 45% dodge
 *  
 *  dodge = 0% when attack within 6 blocks
 * 
 */
public interface IShipInvisible {

	/** invisible level */
	public float getInvisibleLevel();
	
	public void setInvisibleLevel(float level);
	
	
}
