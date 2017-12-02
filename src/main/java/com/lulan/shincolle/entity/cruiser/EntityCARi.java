package com.lulan.shincolle.entity.cruiser;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * model state:
 *   0:cannon left, 1:cannon right, 2:cloak, 3:hair
 */
public class EntityCARi extends BasicEntityShipSmall
{
	
	
	public EntityCARi(World world)
	{
		super(world);
		this.setSize(0.75F, 1.7F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HEAVY_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.HeavyCruiserRI);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.setStateMinor(ID.M.NumState, 4);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CA]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CA]);
		this.ModelPos = new float[] {0F, 20F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(14);
		
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
	public void calcShipAttributesAddRaw()
	{
		super.calcShipAttributesAddRaw();
		
		if (!this.world.isDaytime())
		{
			this.getAttrs().setAttrsRaw(ID.Attrs.CRI, this.getAttrs().getAttrsRaw(ID.Attrs.CRI) + 0.15F);
			this.getAttrs().setAttrsRaw(ID.Attrs.MISS, this.getAttrs().getAttrsRaw(ID.Attrs.MISS) + 0.15F);
		}
	}

    @Override
    public void onLivingUpdate()
    {
    	//check server side
    	if (!this.world.isRemote)
    	{
    		//every 128 ticks
    		if (this.ticksExisted % 128 == 0)
    		{
	    		//apply potion effect in the night
	        	if (!this.world.isDaytime() && this.getStateFlag(ID.F.UseRingEffect))
	        	{	
        			this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 150, getStateMinor(ID.M.ShipLevel) / 70, false, false));
        			this.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 150, getStateMinor(ID.M.ShipLevel) / 60, false, false));
        		}
        	}
    	}
    	
    	super.onLivingUpdate();
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


}