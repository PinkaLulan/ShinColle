package com.lulan.shincolle.entity;

public interface IShipEmotion {
	
	/**Get emotion value
	 * id = 0: return damaged state
	 * id = 1: return 0:NORMAL 1:BLINK 2:T_T 3:O_O 4:BORED 5:HUNGRY
	 * id = 2: return attack emotion / tilt state
	 */
	abstract public byte getStateEmotion(int id);
	
	/**Set emotion value
	 * id: 0:damage state, 1:emotion1, 2:emotion2
	 * value: emotion type
	 * sync: send sync packet to client?
	 */
	abstract public void setStateEmotion(int id, int value, boolean sync);
	
	/**Get state flag
	 * flag: 8:head tilt
	 */
	abstract public boolean getStateFlag(int flag);
	
	/**Set state flag
	 * for individual entity displaying head tilt or other emotion
	 * flag: 8:head tilt
	 * sync: send sync packet to client?
	 */
	abstract public void setStateFlag(int id, boolean flag);
	
	/**GET/SET emotion start time
	 * emotion start time for individual entity
	 */
	abstract public int getStartEmotion();
	abstract public int getStartEmotion2();
	abstract public void setStartEmotion(int par1);
	abstract public void setStartEmotion2(int par1);
	
	/**Get tick time for emotion count
	 */
	abstract public int getTickExisted();
	
	/**Get attack time for model display
	 */
	abstract public int getAttackTime();

}
