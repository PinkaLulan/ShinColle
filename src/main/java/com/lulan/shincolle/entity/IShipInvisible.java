package com.lulan.shincolle.entity;

/** for submarine or invisible entity 
 * 
 *  Invisible LV:
 *  calc by distance, high level = high dodge rate
 *  entity get dodge chance from invisible level
 *  
 *  level = 0% when attack within 6 blocks
 * 
 */
public interface IShipInvisible
{

	/** invisible level */
	public float getInvisibleLevel();
	public void setInvisibleLevel(float level);

	
}