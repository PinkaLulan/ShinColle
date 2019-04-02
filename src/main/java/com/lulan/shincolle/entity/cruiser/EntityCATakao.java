package com.lulan.shincolle.entity.cruiser;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.MissileData;
import com.lulan.shincolle.utility.EmotionHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * model state:
 *   0:cannon, 1:bag, 2:hat, 3:shoes
 */
public class EntityCATakao extends BasicEntityShipSmall
{

	
	public EntityCATakao(World world)
	{
		super(world);
		this.setSize(0.7F, 1.75F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HEAVY_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.CATakao);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.setStateMinor(ID.M.NumState, 4);
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
			if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)))
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
	
	//slow attacker
	@Override
    public boolean attackEntityFrom(DamageSource source, float atk)
	{
		if (super.attackEntityFrom(source, atk) && source.getTrueSource() instanceof EntityLivingBase &&
			!source.getTrueSource().equals(this.getHostEntity()))
		{
			//slow attacker
			((EntityLivingBase) source.getTrueSource()).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100+this.getLevel(), this.getLevel() / 100, false, false));
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void calcShipAttributesAddEffect()
	{
		super.calcShipAttributesAddEffect();
		this.AttackEffectMap.put(2, new int[] {(int)(this.getLevel() / 100), 100+this.getLevel(), this.getLevel()});
	}
	
	@Override
	public void calcShipAttributesAddEquip()
	{
		super.calcShipAttributesAddEquip();
		
		//change missile type
		if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect))
		{
			MissileData md = this.getMissileData(2);
			if (md.type == 0) md.type = 5;
		}
	}
	
	
}