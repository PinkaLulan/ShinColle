package com.lulan.shincolle.reference.unitclass;

import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.FormationHelper;

import scala.actors.threadpool.Arrays;

/**
 * ship basic attributes + equip, potion, formation and morale buffs
 */
public class AttrsAdv extends Attrs
{
	
	/** formation effect, index: {@link ID.Attrs} */
	protected float[] AttrsFormation;
	/** morale buff, index: {@link ID.Attrs} */
	protected float[] AttrsMorale;
	/** min MOV in formation team */
	protected float MinMOV;
	
	
	public AttrsAdv()
	{
		super();
	}
	
	public AttrsAdv(int shipClass)
	{
		super(shipClass);
	}
	
	@Override
	public void initValue()
	{
		super.initValue();
		this.resetAttrsMorale();
		this.resetAttrsFormation();
	}
	
	public static float[] getResetFormationValue()
	{
		return new float[] {0F, 1F, 1F, 1F, 1F,
							1F, 1F, 0F, 0F, 1F,
							1F, 1F, 1F, 1F, 1F,
							0F, 0F, 0F, 0F, 0F,
							0F};
	}
	
	public static float[] getResetMoraleValue()
	{
		return new float[] {0F, 1F, 1F, 1F, 1F,
							0F, 1F, 0F, 0F, 1F,
							1F, 1F, 1F, 1F, 1F,
							0F, 0F, 0F, 0F, 0F,
							0F};
	}
	
	/* reset formation buff to zero */
	public void resetAttrsFormation()
	{
		this.AttrsFormation = getResetFormationValue();
		this.MinMOV = 0F;
	}
	
	public void resetAttrsMorale()
	{
		this.AttrsMorale = getResetMoraleValue();
	}
	
	/**
	 * getter
	 */
	public float[] getAttrsFormation()
	{
		return this.AttrsFormation;
	}
	
	public float getAttrsFormation(int id)
	{
		return this.AttrsFormation[id];
	}
	
	public float getMinMOV()
	{
		return this.MinMOV;
	}

	public float[] getAttrsMorale()
	{
		return this.AttrsMorale;
	}
	
	public float getAttrsMorale(int id)
	{
		return this.AttrsMorale[id];
	}
	
	/**
	 * setter
	 */
	public void setAttrsFormation(float[] data)
	{
		this.AttrsFormation = data;
	}
	
	public void setAttrsFormation(int id, float data)
	{
		this.AttrsFormation[id] = data;
	}
	
	public void setMinMOV(float data)
	{
		this.MinMOV = data;
	}
	
	/* set formation buff by formation id and slot */
	public void setAttrsFormation(int formatID, int formatSlot)
	{
		this.AttrsFormation = FormationHelper.getFormationBuffValue(formatID, formatSlot);
	}
	
	public void setAttrsMorale(float[] data)
	{
		this.AttrsMorale = data;
	}
	
	/** make a object copy */
	public static AttrsAdv copyAttrsAdv(AttrsAdv attrs)
	{
		AttrsAdv newattrs = new AttrsAdv();
		
		newattrs.setAttrsBonus(Arrays.copyOf(attrs.getAttrsBonus(), attrs.getAttrsBonus().length));
		newattrs.setAttrsType(Arrays.copyOf(attrs.getAttrsType(), attrs.getAttrsType().length));
		newattrs.setAttrsRaw(Arrays.copyOf(attrs.getAttrsRaw(), attrs.getAttrsRaw().length));
		newattrs.setAttrsEquip(Arrays.copyOf(attrs.getAttrsEquip(), attrs.getAttrsEquip().length));
		newattrs.setAttrsPotion(Arrays.copyOf(attrs.getAttrsPotion(), attrs.getAttrsPotion().length));
		newattrs.setAttrsBuffed(Arrays.copyOf(attrs.getAttrsBuffed(), attrs.getAttrsBuffed().length));
		newattrs.setAttrsMorale(Arrays.copyOf(attrs.getAttrsMorale(), attrs.getAttrsMorale().length));
		newattrs.setAttrsFormation(Arrays.copyOf(attrs.getAttrsFormation(), attrs.getAttrsFormation().length));
		newattrs.setMinMOV(attrs.getMinMOV());
		
		return newattrs;
	}
	
	
}