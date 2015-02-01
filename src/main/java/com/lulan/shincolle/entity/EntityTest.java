package com.lulan.shincolle.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityTest extends EntityLiving {

	public EntityTest(World world) {
		super(world);
		this.setSize(1F, 1.5F);	//碰撞大小 跟模型大小無關
	}

}
