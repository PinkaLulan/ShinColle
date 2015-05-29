package com.lulan.shincolle.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class EntityTest extends EntityLiving implements IShipEmotion {

	public EntityTest(World world) {
		super(world);
		this.setSize(1F, 1.5F);	//碰撞大小 跟模型大小無關
	}

	@Override
	public byte getStateEmotion(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStateEmotion(int id, int value, boolean sync) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getStateFlag(int flag) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setStateFlag(int flag, boolean sync) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStartEmotion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStartEmotion2() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setStartEmotion(int par1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStartEmotion2(int par1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTickExisted() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAttackTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getIsRiding() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getIsSprinting() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getIsSitting() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getIsSneaking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getIsLeashed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getStateMinor(int id) {
		return 0;
	}

	@Override
	public void setStateMinor(int state, int par1) {}

}
