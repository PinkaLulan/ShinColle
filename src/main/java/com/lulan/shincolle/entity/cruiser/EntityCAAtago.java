package com.lulan.shincolle.entity.cruiser;

import javax.annotation.Nullable;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityCAAtago extends BasicEntityShipSmall
{

	
	public EntityCAAtago(World world)
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
	
	//slow attacker
	@Override
    public boolean attackEntityFrom(DamageSource source, float atk)
	{
		if (super.attackEntityFrom(source, atk) && source.getEntity() instanceof EntityLivingBase &&
			!source.getEntity().equals(this.getHostEntity()))
		{
			//slow attacker
			((EntityLivingBase) source.getEntity()).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, getStateMinor(ID.M.ShipLevel) / 50));
			
			return true;
		}
		
		return false;
	}
	
  	//reset emotion4
  	protected void updateEmotionState()
  	{
  		float hpState = this.getHealth() / this.getMaxHealth();
		
		//check hp state
		if (hpState > 0.75F)
		{	//normal
			this.setStateEmotion(ID.S.HPState, ID.HPState.NORMAL, false);
		}
		else if (hpState > 0.5F)
		{	//minor damage
			this.setStateEmotion(ID.S.HPState, ID.HPState.MINOR, false);
		}
		else if (hpState > 0.25F)
		{	//moderate damage
			this.setStateEmotion(ID.S.HPState, ID.HPState.MODERATE, false);   			
		}
		else
		{	//heavy damage
			this.setStateEmotion(ID.S.HPState, ID.HPState.HEAVY, false);
		}
		
		//roll emtion: hungry > T_T > bored
		if (getStateFlag(ID.F.NoFuel))
		{
			if (this.getStateEmotion(ID.S.Emotion) != ID.Emotion.HUNGRY)
			{
				this.setStateEmotion(ID.S.Emotion, ID.Emotion.HUNGRY, false);
			}
		}
		else
		{
			if (hpState < 0.35F)
			{
    			if (this.getStateEmotion(ID.S.Emotion) != ID.Emotion.T_T)
    			{
    				this.setStateEmotion(ID.S.Emotion, ID.Emotion.T_T, false);
    			}			
    		}
			else
			{
				//random Emotion
				switch (this.getStateEmotion(ID.S.Emotion))
				{
				case ID.Emotion.NORMAL:		//if normal, 33% to bored
					if (this.getRNG().nextInt(3) == 0)
						this.setStateEmotion(ID.S.Emotion, ID.Emotion.BORED, false);
				break;
				default:					//other, 25% return normal
					if (this.getRNG().nextInt(4) == 0)
					{
						this.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
					}
				break;
				}
				
				//reset Emotion4
				switch (this.getStateEmotion(ID.S.Emotion4))
				{
				default:					//other, 33% return normal
						this.setStateEmotion(ID.S.Emotion4, ID.Emotion.NORMAL, false);
				break;
				}
			}
		}
  	}
  	
  	//play custom sound and set Emotion4 to BORED (sync only, server will reset to NORMAL)
    @Override
    @Nullable
    protected SoundEvent getAmbientSound()
    {
    	SoundEvent event = getCustomSound(0, this);
    	
    	if (event == ModSounds.CUSTOM_SOUND.get((this.getShipClass() + 2) * 100))
    	{
    		this.StateEmotion[ID.S.Emotion4] = ID.Emotion.BORED;
    		
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncEntity_Emo), point);
    		
			this.StateEmotion[ID.S.Emotion4] = ID.Emotion.NORMAL;
    	}
    	
		return event;
    }


}