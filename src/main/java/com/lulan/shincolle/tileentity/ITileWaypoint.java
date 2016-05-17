package com.lulan.shincolle.tileentity;

public interface ITileWaypoint {
	
	/** set last waypoint */
	public void setLastWaypoint(int[] next);
	
	/** get last waypoint */
	public int[] getLastWaypoint();

	/** set next waypoint */
	public void setNextWaypoint(int[] next);
	
	/** get next waypoint */
	public int[] getNextWaypoint();
	
	/** set waypoint stay time */
	public void setWpStayTime(int time);
	
	/** get waypoint stay time */
	public int getWpStayTime();
	
	
}
