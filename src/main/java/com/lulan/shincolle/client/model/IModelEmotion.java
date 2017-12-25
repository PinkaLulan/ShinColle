package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;

/**
 * model interface for EmotionHelper
 */
public interface IModelEmotion
{

	
	/** set display face */
	public void setFace(int par1);
	
	/** show/hide equip */
	public void showEquip(IShipEmotion ent);
	
	/** sync rotation to glow part, mainly for face0~face4 */
	public void syncRotationGlowPart();
	
	/** normal and dead pose */
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent);
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent);
	
	/** for debug or packet usage */
	public int getFieldCount();
	public void setField(int id, float value);
	public float getField(int id);
	
	
}