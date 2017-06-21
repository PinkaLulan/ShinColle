package com.lulan.shincolle.entity;

/** Attributes
 *  cri, miss, AA, ASM, etc.
 */
public interface IShipAttrs
{
	
	/** attrs: double hit, triple hit, crit, miss, AA, ASM */
	public float getEffectEquip(int id);
	public float[] getEffectEquip();
	public void setEffectEquip(int id, float value);
	public void setEffectEquip(float[] array);
	
	/** attrs: hp, atk, def, mov, spd, range */
	public float getStateFinal(int id);
	public float[] getStateFinal();
	public void setStateFinal(int id, float value);
	public void setStateFinal(float[] array);
	
	
}