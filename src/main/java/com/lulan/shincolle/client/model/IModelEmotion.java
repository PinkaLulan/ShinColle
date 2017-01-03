package com.lulan.shincolle.client.model;

/**model interface for EmotionHelper
 * 
 */
public interface IModelEmotion
{

	/** set display face (usually 0~4) */
	public void setFace(int par1);
	
	/** for debug usage */
	public int getFieldCount();
	public void setField(int id, float value);
	public float getField(int id);
	
	
}
