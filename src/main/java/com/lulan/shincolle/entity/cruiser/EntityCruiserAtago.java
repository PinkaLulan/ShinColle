package com.lulan.shincolle.entity.cruiser;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCruiserAtago extends BasicEntityShipSmall
{

	
	public EntityCruiserAtago(World world)
	{
		super(world);
		this.setSize(0.7F, 1.75F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HEAVY_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.HeavyCruiserAtago);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CA]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CA]);
		this.ModelPos = new float[] {0F, 25F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(16);
		
		this.postInit();
	}
	
	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
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

	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (getStateEmotion(ID.S.State) > ID.State.NORMAL)
			{
				return this.height * 0.42F;
			}
			else
			{
				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
				{
					return 0F;
	  			}
	  			else
	  			{
	  				return this.height * 0.35F;
	  			}
			}
  		}
  		else
  		{
  			return this.height * 0.75F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			int i = getStateEmotion(ID.S.State2) + 1;
			if (i > ID.State.EQUIP02a) i = ID.State.NORMALa;
			setStateEmotion(ID.S.State2, i, true);
		}
		else
		{
			int i = getStateEmotion(ID.S.State) + 1;
			if (i > ID.State.EQUIP02) i = ID.State.NORMAL;
			setStateEmotion(ID.S.State, i, true);
		}
	}
	
	//
	@Override
    public boolean attackEntityFrom(DamageSource source, float atk)
	{
		if (super.attackEntityFrom(source, atk))
		{
			//TODO slow attacker
			
			return true;
		}
		
		return false;
	}


}