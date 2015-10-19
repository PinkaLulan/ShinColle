package com.lulan.shincolle.entity;

import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.world.World;

public abstract class BasicEntityShipBoss extends BasicEntityShipHostile implements IBossDisplayData {

	public BasicEntityShipBoss(World world) {
		super(world);
	}

}
