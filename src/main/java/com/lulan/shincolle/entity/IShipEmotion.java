package com.lulan.shincolle.entity;

import java.util.Random;

/**
 * SHIP EMOTION
 * include emtion time and state for model display
 */
public interface IShipEmotion extends IShipFlags
{
	
	/** get emotion value */
	public byte getStateEmotion(int id);
	public void setStateEmotion(int id, int value, boolean sync);
	
	/** get emotion timer */
	public int getStateTimer(int id);
	public void setStateTimer(int id, int value);
	
	/**GET/SET emotion start time
	 * emotion start time for individual entity
	 * note: these ticks are render tick (ex: 60~N FPS), NOT game tick (fixed 20 FPS)
	 */
	public int getFaceTick();
	public int getHeadTiltTick();
	public int getAttackTick();		//game tick (20 FPS) = AttackTime (1.7.10-)
	public int getAttackTick2();	//another attack timer for some animation
	public int getDeathTick();
	public void setFaceTick(int par1);
	public void setHeadTiltTick(int par1);
	public void setAttackTick(int par1);
	public void setAttackTick2(int par1);
	public void setDeathTick(int par1);
	
	/**用於手持物品render
	 * par1: 0:X 1:Y 2:Z
	 * par2: angle (rad)
	 */
	public float getModelRotate(int par1);
	public void setModelRotate(int par1, float par2);
	
	/**Get tick time for emotion count */
	public int getTickExisted();
	
	/**Get sit, run, ride state */
	public float getSwingTime(float partialTick);
	public boolean getIsRiding();
	public boolean getIsSprinting();
	public boolean getIsSitting();
	public boolean getIsSneaking();
	public boolean getIsLeashed();
	
	/** set entity sit */
	void setEntitySit(boolean sit);
	
	/** riding state for model display */
	public int getRidingState();
	public void setRidingState(int state);
	
	/** get model scale level for model rendering: 0:normal, 1~N:scale level */
	public int getScaleLevel();
	public void setScaleLevel(int par1);
	
	/** get rand for model display */
	public Random getRand();
	
	/** get depth: 0:self depth, 1:mounts depth, 2:host depth */
	public double getShipDepth(int type);
	
	
}
