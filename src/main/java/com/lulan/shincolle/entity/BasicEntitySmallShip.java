package com.lulan.shincolle.entity;

import com.lulan.shincolle.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class BasicEntitySmallShip extends BasicEntityShip {

	public BasicEntitySmallShip(World world) {
		super(world);
	}

	@Override
	abstract public void setAIList();


}
