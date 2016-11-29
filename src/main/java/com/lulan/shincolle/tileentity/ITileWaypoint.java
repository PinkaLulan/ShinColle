package com.lulan.shincolle.tileentity;

import net.minecraft.util.math.BlockPos;

public interface ITileWaypoint
{
	
	/** set last waypoint */
	public void setLastWaypoint(BlockPos pos);
	
	/** get last waypoint */
	public BlockPos getLastWaypoint();

	/** set next waypoint */
	public void setNextWaypoint(BlockPos pos);
	
	/** get next waypoint */
	public BlockPos getNextWaypoint();
	
	/** set waypoint stay time */
	public void setWpStayTime(int time);
	
	/** get waypoint stay time */
	public int getWpStayTime();
	
	
}
