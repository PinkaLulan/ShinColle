package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.entity.IShipEmotion;

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
	public int getFaceTick() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeadTiltTick() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFaceTick(int par1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeadTiltTick(int par1) {
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

	@Override
	public void setEntitySit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getModelRotate(int par1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setModelRotate(int par1, float par2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAttackAniTick() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAttackAniTick(int par1) {
		// TODO Auto-generated method stub
		
	}

}
