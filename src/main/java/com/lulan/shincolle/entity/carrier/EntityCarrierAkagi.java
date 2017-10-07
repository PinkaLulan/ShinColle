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

/**
 * model state:
 *   0:bow, 1:quiver, 2:deck, 3:bag, 4:armor, 5:skirt, 6:ear+tail
 */
public class EntityCarrierAkagi extends BasicEntityShipCV
{
	
	public EntityCarrierAkagi(World world)
	{
		super(world);
		this.setSize(0.6F, 1.875F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.STANDARD_CARRIER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.CarrierAkagi);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.setStateMinor(ID.M.NumState, 7);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CV]);
		this.ModelPos = new float[] {0F, 20F, 0F, 40F};
		this.launchHeight = this.height * 0.65F;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Light] = false;
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		
		//misc
		this.setFoodSaturationMax(35);
		
		this.postInit();
	}

	@Override
	public int getEquipType()
	{
		return 3;
	}

	//增加艦載機數量計算
  	@Override
  	public void calcShipAttributesAdd()
  	{
  		super.calcShipAttributesAdd();
  		
  		this.maxAircraftLight += this.getLevel() * 0.28F;
  		this.maxAircraftHeavy += this.getLevel() * 0.18F;
  	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();

		//attack AI
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
	}
    
    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//client side
  		if (this.world.isRemote)
  		{
  			if (this.ticksExisted % 128 == 0)
  			{
  				//show hungry emotes
  				if (this.rand.nextInt(4) == 0 && !this.getStateFlag(ID.F.NoFuel))
  				{
  					this.applyParticleEmotion(9);
  				}
  			}
  		}
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
  		case 4:  //heavy aircraft
  			this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, ConfigHandler.volumeFire + 0.2F, 1F / (this.rand.nextFloat() * 0.4F + 1.2F) + 0.5F);
  		
  	        //entity sound
  	        if (this.getRNG().nextInt(10) > 7)
  	        {
  	        	this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
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