package com.lulan.shincolle.entity;

/**SHIP EMOTION
 * include emtion time and state
 */
public interface IShipEmotion extends IShipFlags {
	
	/**Get emotion value
	 * id = 0: return damaged state
	 * id = 1: return 0:NORMAL 1:BLINK 2:T_T 3:O_O 4:BORED 5:HUNGRY
	 * id = 2: return attack emotion / tilt state
	 */
	public byte getStateEmotion(int id);
	
	/**Set emotion value
	 * id: 0:damage state, 1:emotion1, 2:emotion2
	 * value: emotion type
	 * sync: send sync packet to client?
	 */
	public void setStateEmotion(int id, int value, boolean sync);
	
	/**GET/SET emotion start time
	 * emotion start time for individual entity
	 * 
	 * note: attack ani tick is render tick (60FPS), NOT game tick (20FPS)
	 */
	public int getFaceTick();
	public int getHeadTiltTick();
	public int getAttackAniTick();
	public void setFaceTick(int par1);
	public void setHeadTiltTick(int par1);
	public void setAttackAniTick(int par1);
	
	/**GET/SET model rotation XYZ (for client model postRender)
	 * 在model class中設定值, 使render class能抓到該值並且做進一步post render
	 * 目前用於手持物品render
	 * par1: 0:X 1:Y 2:Z
	 * par2: angle (rad)
	 */
	public float getModelRotate(int par1);
	public void setModelRotate(int par1, float par2);
	
	/**Get tick time for emotion count */
	public int getTickExisted();
	
	/**Get attack, sit, run state */
	public int getAttackTime();
	public float getSwingTime(float partialTick);
	public boolean getIsRiding();
	public boolean getIsSprinting();
	public boolean getIsSitting();
	public boolean getIsSneaking();
	public boolean getIsLeashed();
	
	/**Set states, sitting */
	void setEntitySit();
	
	

}
