package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public interface IShipGuardian extends IShipAttackBase
{

	/** get entity be guarded */
	public Entity getGuardedEntity();
	
	/** set entity be guarded */
	public void setGuardedEntity(Entity entity);
	
	/** get position be guarded vec: 0:x 1:y 2:z 3:dim 4:type */
	public int getGuardedPos(int vec);
	
	/** set position be guarded */
	public void setGuardedPos(int x, int y, int z, int dim, int type);
	
	/** get last waypoint */
	public BlockPos getLastWaypoint();
	
	/** set last waypoint */
	public void setLastWaypoint(BlockPos pos);
	
	/** get waypoint stay time */
	public int getWpStayTime();
	public int getWpStayTimeMax();
	
	/** set waypoint stay time */
	public void setWpStayTime(int time);
	
	
}
