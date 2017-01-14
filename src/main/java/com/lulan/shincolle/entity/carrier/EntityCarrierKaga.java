package com.lulan.shincolle.entity.carrier;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.other.EntityAirplaneT;
import com.lulan.shincolle.entity.other.EntityAirplaneZero;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;


public class EntityCarrierKaga extends BasicEntityShipCV
{
	
	public EntityCarrierKaga(World world)
	{
		super(world);
		this.setSize(0.6F, 1.875F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.STANDARD_CARRIER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.CarrierKaga);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CV]);
		this.ModelPos = new float[] {0F, 20F, 0F, 40F};
		this.launchHeight = this.height * 0.65F;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Light] = false;
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		
		//misc
		this.setFoodSaturationMax(30);
		
		this.postInit();
	}
	
	@Override
	public int getEquipType()
	{
		return 3;
	}

	//增加艦載機數量計算
  	@Override
  	public void calcShipAttributes()
  	{
  		super.calcShipAttributes();
  		
  		this.maxAircraftLight += this.getLevel() * 0.4F;
  		this.maxAircraftHeavy += this.getLevel() * 0.2F;
  	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();

		//attack AI
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
	}
	
	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return (double)this.height * 0.35F;
  			}
  			else
  			{
  				return (double)this.height * 0.45F;
  			}
  		}
  		else
  		{
  			return (double)this.height * 0.72F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			int i = getStateEmotion(ID.S.State2) + 1;
			if (i > ID.State.EQUIP03a) i = ID.State.NORMALa;
			setStateEmotion(ID.S.State2, i, true);
		}
		else
		{
			int i = getStateEmotion(ID.S.State) + 1;
			if (i > ID.State.EQUIP06) i = ID.State.NORMAL;
			setStateEmotion(ID.S.State, i, true);
		}
	}
	
	@Override
	protected BasicEntityAirplane getAttackAirplane(boolean isLightAirplane)
	{
		if (isLightAirplane)
		{
			return new EntityAirplaneZero(this.world);
		}
		else
		{
			return new EntityAirplaneT(this.world);
		}
	}
	
	/** change aircraft launch sound to bow sound */
	@Override
	public void applySoundAtAttacker(int type, Entity target)
	{
  		switch (type)
  		{
  		case 1:  //light cannon
  		case 2:  //heavy cannon
  			break;
  		case 3:  //light aircraft
  			this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, ConfigHandler.volumeFire + 0.2F, 1F / (this.rand.nextFloat() * 0.4F + 1.2F) + 0.5F);
  			break;
  		case 4:  //heavy aircraft
  			this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, ConfigHandler.volumeFire + 0.2F, 1F / (this.rand.nextFloat() * 0.4F + 1.2F) + 0.5F);
  			break;
		default: //melee
			if (this.getRNG().nextInt(2) == 0)
			{
				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
	        }
			break;
  		}
  	}


}