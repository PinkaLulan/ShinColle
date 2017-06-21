package com.lulan.shincolle.entity;

import java.util.HashMap;

import net.minecraft.entity.Entity;

/**SHIP ATTACK BASE
 * include attacker states getter/setter
 */
public interface IShipAttackBase extends IShipNavigator, IShipEmotion, IShipOwner, IShipAttrs
{
	
	/** get attack target */
	public Entity getEntityTarget();
	public void setEntityTarget(Entity target);
	
	/** get revenge target for next attack target */
	public Entity getEntityRevengeTarget();
	public void setEntityRevengeTarget(Entity target);
	public int getEntityRevengeTime();
	public void setEntityRevengeTime();

	/** get attack attributes */
	public float getAttackDamage();			//attack damage
	public float getAttackSpeed();			//attack speed
	public float getAttackRange();			//attack range
	
	/** damage type, ref: ID.ShipDmgType */
	public int getDamageType();
	
	/** available attack method: light, heavy, air light, air heavy */
	public boolean getAttackType(int par1);
	
	public float getDefValue();				//defence value (%)
	
	public int getAmmoLight();				//get ammo info
	public int getAmmoHeavy();	
	public void setAmmoLight(int num);
	public void setAmmoHeavy(int num);
	public boolean hasAmmoLight();
	public boolean hasAmmoHeavy();
	
	public int getLevel();					//get ship level
	
	/** skill attack method for skill attack AI */
	public boolean updateSkillAttack(Entity target);
	
	/** buffs map, map<buff id, buff level> */
	public HashMap<Byte, Byte> getBuffMap();
	public void setBuffMap(HashMap<Byte, Byte> map);
	

}