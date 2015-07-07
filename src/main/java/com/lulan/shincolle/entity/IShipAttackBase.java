package com.lulan.shincolle.entity;

import net.minecraft.entity.EntityLivingBase;

/**SHIP ATTACK BASE
 * include attacker states getter/setter
 */
public interface IShipAttackBase extends IShipNavigator, IShipEmotion {
	
	/**Get owner for friendly fire check, can be any entity */
	public EntityLivingBase getOwner();
	
	/**Get player owner for team check, should be EntityPlayer or hostile entity */
	public EntityLivingBase getPlayerOwner();
	
	/**Get attack target, if entity is EntityLivingBase, return super.getAttackTarget() */
	public EntityLivingBase getTarget();

	/**Get attack attributes */
	public float getAttackDamage();			//for non-ship entity
	public float getAttackSpeed();			
	public float getAttackRange();
	
	public boolean getAttackType(int par1);	//check available attack type
	
	public float getDefValue();
	
	public float getEffectEquip(int id);	//double hit, triple hit, crit, miss
	
	public boolean hasAmmoLight();
	public boolean hasAmmoHeavy();
	
	public int getAmmoLight();
	public int getAmmoHeavy();
	
	public void setAmmoLight(int num);
	public void setAmmoHeavy(int num);
	
	public int getLevel();
	

}
