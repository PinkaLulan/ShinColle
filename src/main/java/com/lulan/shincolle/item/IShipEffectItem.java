package com.lulan.shincolle.item;

import java.util.Map;

public interface IShipEffectItem
{
	
	
	/**
	 * get potion effect on attack
	 * return map<potion id, data>
	 *   potion id: potion effect id
	 *   data: 0:ampLv, 1:during, 2:chance
	 */
	public Map<Integer, int[]> getEffectOnAttack(int meta);
	
	/** change missile type */
	public int getMissileType(int meta);
	
	/** change missile move type, return -1 if no specific type */
	public int getMissileMoveType(int meta);
	
	/** change missile speed level */
	public int getMissileSpeedLevel(int meta);
	
	
}