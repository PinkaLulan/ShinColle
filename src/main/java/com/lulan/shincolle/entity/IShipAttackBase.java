package com.lulan.shincolle.entity;

import java.util.HashMap;

import com.lulan.shincolle.handler.IMoveShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.MissileData;

import net.minecraft.entity.Entity;

/**SHIP ATTACK BASE
 * include attacker states getter/setter
 */
public interface IShipAttackBase extends IMoveShip, IShipEmotion, IShipOwner, IShipAttrs
{
	
	/** get attack target */
	public Entity getEntityTarget();
	public void setEntityTarget(Entity target);
	
	/** get revenge target for next attack target */
	public Entity getEntityRevengeTarget();
	public void setEntityRevengeTarget(Entity target);
	public int getEntityRevengeTime();
	public void setEntityRevengeTime();
	
	/** damage type, index: {@link ID.ShipDmgType} */
	public int getDamageType();
	
	/** available attack method: light, heavy, air light, air heavy */
	public boolean getAttackType(int par1);
	
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
	public HashMap<Integer ,Integer> getBuffMap();
	public void setBuffMap(HashMap<Integer, Integer> map);

	/** attack effect map, map<potion id, potion data[ampLevel, ticks, chance(0~100)]> */
	public HashMap<Integer, int[]> getAttackEffectMap();
	public void setAttackEffectMap(HashMap<Integer, int[]> map);
	
	/** missile data
	 *  type: 0:melee, 1:light, 2:heavy, 3:air-light, 4:air-heavy */
	public MissileData getMissileData(int type);
	public void setMissileData(int type, MissileData data);
	
	
}