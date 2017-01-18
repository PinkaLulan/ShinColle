package com.lulan.shincolle.entity;


public interface IShipFloating extends IShipFlags
{
	
	/** getter/setter for depth value for floating AI */
	abstract public double getShipDepth();
	
	abstract public void setShipDepth(double par1);
	
	/** the min depth for ship floating */
	abstract public double getShipFloatingDepth();
	
	abstract public void setShipFloatingDepth(double par1);
	
}
