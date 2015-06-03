package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;

public interface IShipGuardian extends IShipAttackBase {

	/** get entity be guarded */
	public Entity getGuarded();
	
	/** set entity be guarded */
	public void setGuarded(Entity entity);
	
	/** get position be guarded vec: 0:x 1:y 2:z 3:dim */
	public int getGuardedPos(int vec);
	
	/** set position be guarded */
	public void setGuardedPos(int x, int y, int z, int dim);
	
}
