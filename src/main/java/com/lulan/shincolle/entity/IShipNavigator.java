package com.lulan.shincolle.entity;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;

/**For using ship path navigator
 */
public interface IShipNavigator {

	public ShipPathNavigate getShipNavigate();
	
	public ShipMoveHelper getShipMoveHelper();
	
	public boolean canFly();
	
	public float getMoveSpeed();
	
}
