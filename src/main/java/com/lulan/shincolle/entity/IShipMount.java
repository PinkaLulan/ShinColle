package com.lulan.shincolle.entity;

/**
 * for mount type entity
 */
public interface IShipMount extends IShipFloating
{

	/**
	 * rider position: ship rider
	 */
	public float[] getSeatPos();
	public void setSeatPos(float[] pos);
	
	/**
	 * rider position: other entity rider
	 */
	public float[] getSeatPos2();
	public void setSeatPos2(float[] pos);
	
	
}
