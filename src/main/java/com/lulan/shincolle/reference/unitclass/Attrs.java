package com.lulan.shincolle.reference.unitclass;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;

import java.util.Arrays;
import java.util.Random;

/**
 * ship basic attributes + equip and potion buffs
 * 
 * 若要新增新屬性, 需修改ID.Attrs, Attrs.AttrsLength, ConfigHandler.limitShipAttrs
 */
public class Attrs
{
	
	/** base attrs length */
	public static final int AttrsLength = 21;
	/** bonus point for raw attrs calculation, index: {@link ID.AttrsBase} */
	protected byte[] AttrsBonus;
	/** raw attrs mod by type, index: {@link ID.AttrsBase} */
	protected float[] AttrsType;
	/** raw attrs before buff/debuff, index: {@link ID.Attrs} */
	protected float[] AttrsRaw;
	/** buffed attrs, index: {@link ID.Attrs} */
	protected float[] AttrsBuffed;
	/** equip attrs, index: {@link ID.Attrs} */
	protected float[] AttrsEquip;
	/** equip attrs, index: {@link ID.Attrs} */
	protected float[] AttrsPotion;
	
	
	public Attrs()
	{
		this.initValue();
		this.initAttrsType(0);
	}
	
	public Attrs(int shipClass)
	{
		this.initValue();
		this.initAttrsType(shipClass);
	}
	
	//init all values
	public void initValue()
	{
		this.AttrsBonus = new byte[6];
		this.AttrsType = new float[6];
		this.resetAttrsRaw();
		this.resetAttrsEquip();
		this.resetAttrsPotion();
		this.resetAttrsBuffed();
	}
	
	//init AttrsType by ship class
	public void initAttrsType(int shipClass)
	{
		//null check
		if (!Values.ShipAttrMap.containsKey(shipClass)) shipClass = 0;
		
		//get attrs value
		float[] getStat = Arrays.copyOf(Values.ShipAttrMap.get(shipClass), Attrs.AttrsLength);
		
		this.AttrsType[ID.AttrsBase.HP] = getStat[ID.AttrsBase.modHP];
		this.AttrsType[ID.AttrsBase.ATK] = getStat[ID.AttrsBase.modATK];
		this.AttrsType[ID.AttrsBase.DEF] = getStat[ID.AttrsBase.modDEF];
		this.AttrsType[ID.AttrsBase.SPD] = getStat[ID.AttrsBase.modSPD];
		this.AttrsType[ID.AttrsBase.MOV] = getStat[ID.AttrsBase.modMOV];
		this.AttrsType[ID.AttrsBase.HIT] = getStat[ID.AttrsBase.modHIT];
	}
	
	//check attrs limit
	public void checkAttrsLimit()
	{
		checkAttrsLimit(this.AttrsBuffed);
	}
	
	public static void checkAttrsLimit(float[] data)
	{
		/* check max */
		//HP limit
		for (int i = 0; i < Attrs.AttrsLength; i++)
		{
			if (ConfigHandler.limitShipAttrs[i] >= 0D && data[i] > ConfigHandler.limitShipAttrs[i])
			{
				data[i] = (float) ConfigHandler.limitShipAttrs[i];
			}
		}
  		
		/* check min */
  		for (int i = 0; i < data.length; i++)
  		{
			if (data[i] < 0F) data[i] = 0F;
  		}
  		
		if (data[ID.Attrs.HP] < 1F) data[ID.Attrs.HP] = 1F;
		if (data[ID.Attrs.ATK_L] < 1F) data[ID.Attrs.ATK_L] = 1F;
		if (data[ID.Attrs.ATK_H] < 1F) data[ID.Attrs.ATK_H] = 1F;
		if (data[ID.Attrs.ATK_AL] < 1F) data[ID.Attrs.ATK_AL] = 1F;
		if (data[ID.Attrs.ATK_AH] < 1F) data[ID.Attrs.ATK_AH] = 1F;
		if (data[ID.Attrs.HIT] < 1F) data[ID.Attrs.HIT] = 1F;
		if (data[ID.Attrs.SPD] < 0.2F) data[ID.Attrs.SPD] = 0.2F;
	}
	
	public void copyRaw2Buffed()
	{
		for (int i = 0; i < AttrsLength; i++)
		{
			this.AttrsBuffed[i] = this.AttrsRaw[i];
		}
	}
	
	public void copyBuffed2Raw()
	{
		for (int i = 0; i < AttrsLength; i++)
		{
			this.AttrsRaw[i] = this.AttrsBuffed[i];
		}
	}
	
	public static float[] getResetRawValue()
	{
		return new float[] {4F, 0F, 0F, 0F, 0F,
                			0F, 0.2F, 0F, 1F, 0F,
                			0F, 0F, 0F, 0F, 0F,
                			0F, 1F, 1F, 1F, 1F,
                			0F};
	}
	
	public static float[] getResetZeroValue()
	{
		return new float[AttrsLength];
	}
	
	public void resetAttrsRaw()
	{
		this.AttrsRaw = getResetRawValue();
	}
	
	public void resetAttrsBuffed()
	{
		this.AttrsBuffed = getResetRawValue();
	}
	
	public void resetAttrsEquip()
	{
		this.AttrsEquip = new float[AttrsLength];
	}
	
	public void resetAttrsPotion()
	{
		this.AttrsPotion = new float[AttrsLength];
	}
	
	/**
	 * getter
	 */
	public float[] getAttrsRaw()
	{
		return this.AttrsRaw;
	}
	
	public float getAttrsRaw(int id)
	{
		return this.AttrsRaw[id];
	}
	
	public float[] getAttrsBuffed()
	{
		return this.AttrsBuffed;
	}
	
	public float getAttrsBuffed(int id)
	{
		return this.AttrsBuffed[id];
	}
	
	public byte[] getAttrsBonus()
	{
		return this.AttrsBonus;
	}
	
	public byte getAttrsBonus(int id)
	{
		return this.AttrsBonus[id];
	}
	
	public float[] getAttrsType()
	{
		return this.AttrsType;
	}
	
	public float getAttrsType(int id)
	{
		return this.AttrsType[id];
	}
	
	public float[] getAttrsEquip()
	{
		return this.AttrsEquip;
	}
	
	public float getAttrsEquip(int id)
	{
		return this.AttrsEquip[id];
	}
	
	public float[] getAttrsPotion()
	{
		return this.AttrsPotion;
	}
	
	public float getAttrsPotion(int id)
	{
		return this.AttrsPotion[id];
	}
	
	/* get attack damage (light attack damage) */
	public float getAttackDamage()
	{
		return this.AttrsBuffed[ID.Attrs.ATK_L];
	}
	
	public float getAttackDamageHeavy()
	{
		return this.AttrsBuffed[ID.Attrs.ATK_H];
	}
	
	public float getAttackDamageAir()
	{
		return this.AttrsBuffed[ID.Attrs.ATK_AL];
	}
	
	public float getAttackDamageAirHeavy()
	{
		return this.AttrsBuffed[ID.Attrs.ATK_AH];
	}
	
	public float getDefense()
	{
		return this.AttrsBuffed[ID.Attrs.DEF];
	}
	
	public float getAttackRange()
	{
		return this.AttrsBuffed[ID.Attrs.HIT];
	}
	
	public float getAttackSpeed()
	{
		//prevent DBZ error
		if (this.AttrsBuffed[ID.Attrs.SPD] < 0.001F) return 0.001F;
		return this.AttrsBuffed[ID.Attrs.SPD];
	}
	
	public float getMoveSpeed()
	{
		return this.AttrsBuffed[ID.Attrs.MOV];
	}
	
	public float getAttackDamage(boolean getRaw)
	{
		if (getRaw) return this.AttrsRaw[ID.Attrs.ATK_L];
		return this.AttrsBuffed[ID.Attrs.ATK_L];
	}
	
	public float getAttackDamageHeavy(boolean getRaw)
	{
		if (getRaw) return this.AttrsRaw[ID.Attrs.ATK_H];
		return this.AttrsBuffed[ID.Attrs.ATK_H];
	}
	
	public float getAttackDamageAir(boolean getRaw)
	{
		if (getRaw) return this.AttrsRaw[ID.Attrs.ATK_AL];
		return this.AttrsBuffed[ID.Attrs.ATK_AL];
	}
	
	public float getAttackDamageAirHeavy(boolean getRaw)
	{
		if (getRaw) return this.AttrsRaw[ID.Attrs.ATK_AH];
		return this.AttrsBuffed[ID.Attrs.ATK_AH];
	}
	
	public float getDefense(boolean getRaw)
	{
		if (getRaw) return this.AttrsRaw[ID.Attrs.DEF];
		return this.AttrsBuffed[ID.Attrs.DEF];
	}
	
	public float getAttackRange(boolean getRaw)
	{
		if (getRaw) return this.AttrsRaw[ID.Attrs.HIT];
		return this.AttrsBuffed[ID.Attrs.HIT];
	}
	
	public float getAttackSpeed(boolean getRaw)
	{
		if (getRaw) return this.AttrsRaw[ID.Attrs.SPD];
		return this.AttrsBuffed[ID.Attrs.SPD];
	}
	
	public float getMoveSpeed(boolean getRaw)
	{
		if (getRaw) return this.AttrsRaw[ID.Attrs.MOV];
		return this.AttrsBuffed[ID.Attrs.MOV];
	}
	
	/**
	 * setter
	 */
	public void setAttrsRaw(float[] data)
	{
		this.AttrsRaw = data;
	}
	
	public void setAttrsRaw(int id, float data)
	{
		this.AttrsRaw[id] = data;
	}
	
	public void setAttrsBuffed(float[] data)
	{
		this.AttrsBuffed = data;
	}
	
	public void setAttrsBuffed(int id, float data)
	{
		this.AttrsBuffed[id] = data;
	}
	
	public void setAttrsBonus(byte[] data)
	{
		this.AttrsBonus = data;
	}
	
	public void setAttrsBonus(int id, int data)
	{
		this.AttrsBonus[id] = (byte) data;
	}
	
	public void setAttrsType(float[] data)
	{
		this.AttrsType = data;
	}
	
	public void setAttrsEquip(float[] data)
	{
		this.AttrsEquip = data;
	}
	
	public void setAttrsEquip(int id, float data)
	{
		this.AttrsEquip[id] = data;
	}
	
	public void setAttrsPotion(float[] data)
	{
		this.AttrsPotion = data;
	}
	
	public void setAttrsPotion(int id, float data)
	{
		this.AttrsPotion[id] = data;
	}
	
	public void addAttrsBonus(int id, int data)
	{
		this.AttrsBonus[id] += (byte) data;
	}
	
	//add random bonus point
	public boolean addAttrsBonusRandom(Random rand)
	{
		int bonusChoose = rand.nextInt(6);
		
		//bonus point +1 if bonus point < limit
		if (this.AttrsBonus[bonusChoose] < ConfigHandler.modernLimit)
		{
			this.AttrsBonus[bonusChoose] += 1;
			return true;
		}
		else
		{
			//select other bonus point
			for (int i = 0; i < 6; ++i)
			{
				if (this.AttrsBonus[i] < ConfigHandler.modernLimit)
				{
					this.AttrsBonus[i] += 1;
					return true;
				}
			}
		}
		
		return false;
	}
	
	/** make a object copy */
	public static Attrs copyAttrs(Attrs attrs)
	{
		Attrs newattrs = new Attrs();
		
		newattrs.setAttrsBonus(Arrays.copyOf(attrs.getAttrsBonus(), attrs.getAttrsBonus().length));
		newattrs.setAttrsType(Arrays.copyOf(attrs.getAttrsType(), attrs.getAttrsType().length));
		newattrs.setAttrsRaw(Arrays.copyOf(attrs.getAttrsRaw(), attrs.getAttrsRaw().length));
		newattrs.setAttrsEquip(Arrays.copyOf(attrs.getAttrsEquip(), attrs.getAttrsEquip().length));
		newattrs.setAttrsPotion(Arrays.copyOf(attrs.getAttrsPotion(), attrs.getAttrsPotion().length));
		newattrs.setAttrsBuffed(Arrays.copyOf(attrs.getAttrsBuffed(), attrs.getAttrsBuffed().length));
		
		return newattrs;
	}
	
	
}