package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;

/**CANNON ATTACK METHOD
 * 
 */
public interface IShipCannonAttack extends IShipAttackBase
{
	
	/** Use light ammo to attack */
	abstract public boolean attackEntityWithAmmo(Entity target);
	
	/** Use heavy ammo to attack */
	abstract public boolean attackEntityWithHeavyAmmo(Entity target);

	/** Use ammo type */
	public boolean useAmmoLight();
	public boolean useAmmoHeavy();
	
	
}