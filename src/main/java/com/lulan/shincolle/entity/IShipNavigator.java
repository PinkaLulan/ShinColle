package com.lulan.shincolle.entity;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;


/**
 * Path navigator for ships
 */
public interface IShipNavigator
{

	/** ship navigator */
	public ShipPathNavigate getShipNavigate();
	
	/** ship move helper */
	public ShipMoveHelper getShipMoveHelper();
	
	/** can entity fly flag */
	public boolean canFly();
	
	/** entity is jumping */
	public boolean isJumping();
	
	/** move speed */
	public float getMoveSpeed();
	
	/** jump strength */
	public float getJumpSpeed();
	
	
}