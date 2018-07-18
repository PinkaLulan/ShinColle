package com.lulan.shincolle.entity.carrier;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.other.EntityAirplaneT;
import com.lulan.shincolle.entity.other.EntityAirplaneZero;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * model state:
 *   0:bow, 1:quiver, 2:deck, 3:bag, 4:armor, 5:skirt, 6:ear+tail, 7:shoes
 */
public class EntityCarrierKaga extends BasicEntityShipCV
{
	
	public EntityCarrierKaga(World world)
	{
		super(world);
		this.setSize(0.6F, 1.875F);
		this.setStateMinor(ID.M.ShipType, ID.ShipIconType.STANDARD_CARRIER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.CVKaga);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.setStateMinor(ID.M.NumState, 8);
		this.setGrudgeConsumeIdle(ConfigHandler.consumeGrudgeShipIdle[ID.ShipConsume.CV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CV]);
		this.modelPosInGUI = new float[] {0F, 20F, 0F, 40F};
		this.launchHeight = this.height * 0.65F;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Light] = false;
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		
		//misc
		this.setFoodSaturationMax(30);
		
		this.initPre();
	}
	
	@Override
	public int getEquipType()
	{
		return 3;
	}
	
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//client side
  		if (this.world.isRemote)
  		{
  		}
  		//server side
  		else
  		{
  			//every 128 ticks
  			if (this.ticksExisted % 128 == 0)
  			{
  				//married effect: apply str buff to nearby ships
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0)
  				{
  					List<BasicEntityShip> shiplist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, this.getEntityBoundingBox().expand(16D, 16D, 16D));
  	  	  			
  					if (shiplist != null && shiplist.size() > 0)
  					{
  						for (BasicEntityShip s : shiplist)
  						{
  							if (TeamHelper.checkSameOwner(this, s))
  							{
  								//potion effect: id, time, level
  								s.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST , 50+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 85, false, false));
  							}
  						}
  					}
  				}//end married buff
  			}//end every 128 ticks
  		}
  	}

	//增加艦載機數量計算
  	@Override
  	public void calcShipAttributesAddRaw()
  	{
  		super.calcShipAttributesAddRaw();
  		
  		this.maxAircraftLight += this.getLevel() * 0.4F;
  		this.maxAircraftHeavy += this.getLevel() * 0.2F;
	}
  	
	@Override
	public void calcShipAttributesAddEffect()
	{
		super.calcShipAttributesAddEffect();
		this.AttackEffectMap.put(17, new int[] {(int)(this.getLevel() / 120), 100+this.getLevel(), this.getLevel()});
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
	public BasicEntityAirplane getAttackAirplane(boolean isLightAirplane)
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