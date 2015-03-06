package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;

public interface IEntityShip {
	
	/**
	 * ammo check for attackEntityWithAmmo
	 */
	boolean hasAmm = false;
	
	/**
	 * heavy ammo check for attackEntityWithHeavyAmmo
	 */
	boolean hasHeavyAmmo = false;
	
	/**
     * ship range light attack method for EntityAIRangeAttack
     */
	boolean attackEntityWithAmmo(Entity target);

	/**
     * ship range heavy attack method for EntityAIRangeAttack
     */
	boolean attackEntityWithHeavyAmmo(Entity target);


}
