package com.lulan.shincolle.tileentity;

import com.lulan.shincolle.entity.IShipOwner;

import net.minecraft.util.math.BlockPos;

public interface ITileWaypoint extends IShipOwner, ITileGuardPoint
{
	
	
	/** last waypoint */
	public void setLastWaypoint(BlockPos pos);
	public BlockPos getLastWaypoint();

	/** next waypoint */
	public void setNextWaypoint(BlockPos pos);
	public BlockPos getNextWaypoint();
	
	/** waypoint stay time */
	public void setWpStayTime(int time);
	public int getWpStayTime();
	
	
}