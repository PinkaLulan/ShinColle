package com.lulan.shincolle.item;

import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BasicEntityItem extends EntityItem {

	public BasicEntityItem(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.isImmuneToFire = true;
    }

    public BasicEntityItem(World world, double x, double y, double z, ItemStack item) {
    	super(world, x, y, z, item);
    	this.isImmuneToFire = true;
    }
	
	//can not damage this item
	@Override
	public boolean attackEntityFrom(DamageSource attacker, float dmg) {
		return false;
	}
	
	//immune to fire and lava
	@Override
	protected void dealFireDamage(int fire) {}
	@Override
	public void setFire(int time) {}
	@Override
	protected void setOnFireFromLava() {}
	@Override
	public boolean handleLavaMovement() {
		return false;
	}
	

}
