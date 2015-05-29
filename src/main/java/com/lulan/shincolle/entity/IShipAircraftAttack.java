package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;

/**USE AIRFORCE TO ATTACK
 * include aircraft attack method, airplane getter/setter
 */
public interface IShipAircraftAttack extends IShipAttackBase {
	
	/**get amount of airplane*/
	public int getNumAircraftLight();
	public int getNumAircraftHeavy();
	
	/**check has airplane*/
	public boolean hasAirLight();
	public boolean hasAirHeavy();
	
	/**set amount of airplane*/
	public void setNumAircraftLight(int par1);
	public void setNumAircraftHeavy(int par1);
	
	/**airplane attack*/
	public boolean attackEntityWithAircraft(Entity target);
	public boolean attackEntityWithHeavyAircraft(Entity target);

}
