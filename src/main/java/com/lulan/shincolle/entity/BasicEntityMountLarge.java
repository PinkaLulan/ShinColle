package com.lulan.shincolle.entity;

import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**LARGE MOUNT = Use Aircraft
 */
abstract public class BasicEntityMountLarge extends BasicEntityMount implements IShipAircraftAttack
{
	
	
	public BasicEntityMountLarge(World world)
	{
		super(world);
	}

	@Override
	public int getNumAircraftLight()
	{
		if (host != null) return host.getStateMinor(ID.M.NumAirLight);
		return 0;
	}

	@Override
	public int getNumAircraftHeavy()
	{
		if (host != null) return host.getStateMinor(ID.M.NumAirHeavy);
		return 0;
	}

	@Override
	public boolean hasAirLight()
	{
		if (host != null) return host.getStateMinor(ID.M.NumAirLight) > 0;
		return false;
	}

	@Override
	public boolean hasAirHeavy()
	{
		if (host != null) return host.getStateMinor(ID.M.NumAirHeavy) > 0;
		return false;
	}

	@Override
	public void setNumAircraftLight(int par1) {}	//收回飛機由host執行, mount不須收回

	@Override
	public void setNumAircraftHeavy(int par1) {}	//收回飛機由host執行, mount不須收回

	//飛機攻擊, 由host執行
	@Override
	public boolean attackEntityWithAircraft(Entity target)
	{
		return ((IShipAircraftAttack)host).attackEntityWithAircraft(target);
	}
	//飛機攻擊, 由host執行
	@Override
	public boolean attackEntityWithHeavyAircraft(Entity target)
	{
		return ((IShipAircraftAttack)host).attackEntityWithHeavyAircraft(target);
	}
	
	
}
