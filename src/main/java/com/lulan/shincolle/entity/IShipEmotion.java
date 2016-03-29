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
	 */
	public int getStartEmotion();
	public int getStartEmotion2();
	public void setStartEmotion(int par1);
	public void setStartEmotion2(int par1);
	
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
	
	/**Get attack time, sit, run state */
	public int getAttackTime();
	public boolean getIsRiding();
	public boolean getIsSprinting();
	public boolean getIsSitting();
	public boolean getIsSneaking();
	public boolean getIsLeashed();
	
	/**Set states, sitting */
	void setEntitySit();
	
	

}
