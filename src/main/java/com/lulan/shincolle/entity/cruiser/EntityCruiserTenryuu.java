package com.lulan.shincolle.entity.cruiser;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityCruiserTenryuu extends BasicEntityShipSmall
{
	
	
	public EntityCruiserTenryuu(World world)
	{
		super(world);
		this.setSize(0.75F, 1.6F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.LIGHT_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.LightCruiserTenryuu);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CL]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CL]);
		this.ModelPos = new float[] {0F, 10F, 0F, 30F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(12);
		
		this.postInit();
	}
	
	@Override
	public int getEquipType()
	{
		return 1;
	}

	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
	
	//晚上時額外增加屬性
	@Override
	public void calcShipAttributes()
	{
		if (!this.world.isDaytime())
		{
			EffectEquip[ID.EF_CRI] = EffectEquip[ID.EF_CRI] + 0.15F;
			EffectEquip[ID.EF_DODGE] = EffectEquip[ID.EF_DODGE] + 0.15F;
		}
		
		super.calcShipAttributes();	
	}
	
	//range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		return false;
	}
	
    @Override
	public double getMountedYOffset()
    {
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.22F;
  			}
  			else
  			{
  				return this.height * 0.33F;
  			}
  		}
  		else
  		{
  			return this.height * 0.72F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			int i = getStateEmotion(ID.S.State2) + 1;
			if (i > ID.State.EQUIP04a) i = ID.State.NORMALa;
			setStateEmotion(ID.S.State2, i, true);
		}
		else
		{
			int i = getStateEmotion(ID.S.State) + 1;
			if (i > ID.State.EQUIP02) i = ID.State.NORMAL;
			setStateEmotion(ID.S.State, i, true);
		}
	}


}