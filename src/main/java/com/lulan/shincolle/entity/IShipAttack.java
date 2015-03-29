package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public interface IShipAttack {
	
	/**Use light ammo to attack
	 */
	abstract public boolean attackEntityWithAmmo(Entity target);
	
	/**Use heavy ammo to attack
	 */
	abstract public boolean attackEntityWithHeavyAmmo(Entity target);
	
	/**Get owner for friendly fire check
	 */
	abstract public EntityLivingBase getOwner();
	
	/**Get attack target, if entity is EntityLivingBase, return super.getAttackTarget()
	 */
	abstract public EntityLivingBase getTarget();

	/**Get attack attributes
	 */
	abstract public float getAttackDamage();
	abstract public float getAttackSpeed();
	abstract public float getAttackRange();
	abstract public float getMoveSpeed();
	abstract public boolean hasAmmoLight();
	abstract public boolean hasAmmoHeavy();
	abstract public int getAmmoLight();
	abstract public int getAmmoHeavy();
	abstract public void setAmmoLight(int num);
	abstract public void setAmmoHeavy(int num);
	

}
