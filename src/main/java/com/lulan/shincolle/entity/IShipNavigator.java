package com.lulan.shincolle.entity;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;


/**
 * Path navigator for ships
 */
public interface IShipNavigator
{

	public ShipPathNavigate getShipNavigate();
	
	public ShipMoveHelper getShipMoveHelper();
	
	public boolean canFly();
	
	public boolean isJumping();
	
	public float getMoveSpeed();
	
	
}
