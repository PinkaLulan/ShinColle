package com.lulan.shincolle.entity.cruiser;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityHeavyCruiserRi extends BasicEntityShipSmall
{
	
	
	public EntityHeavyCruiserRi(World world)
	{
		super(world);
		this.setSize(0.75F, 1.7F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HEAVY_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.HeavyCruiserRI);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
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
	public void calcShipAttributes()
	{
		if (!this.world.isDaytime())
		{
			EffectEquip[ID.EF_CRI] = EffectEquip[ID.EF_CRI] + 0.2F;
			EffectEquip[ID.EF_MISS] = EffectEquip[ID.EF_MISS] + 0.2F;
		}
		
		super.calcShipAttributes();	
	}

    @Override
    public void onLivingUpdate()
    {
    	//check server side
    	if (!this.world.isRemote)
    	{
    		//check every 5 sec
    		if (this.ticksExisted % 100 == 0)
    		{
	    		//apply potion effect in the night
	        	if (!this.world.isDaytime() && this.getStateFlag(ID.F.UseRingEffect))
	        	{	
        			this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 300, 1));
        			this.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 300, 2));
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

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			switch (getStateEmotion(ID.S.State2))
			{
			case ID.State.NORMALa:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00a, true);
			break;
			case ID.State.EQUIP00a:
				setStateEmotion(ID.S.State2, ID.State.NORMALa, true);
			break;	
			default:
				setStateEmotion(ID.S.State2, ID.State.NORMALa, true);
			break;
			}
		}
		else
		{
			switch(getStateEmotion(ID.S.State))
			{
			case ID.State.NORMAL:
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
			case ID.State.EQUIP00:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
			default:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
			}
		}
	}


}