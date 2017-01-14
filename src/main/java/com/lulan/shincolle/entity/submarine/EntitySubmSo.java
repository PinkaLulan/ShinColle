package com.lulan.shincolle.entity.submarine;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntitySubmSo extends BasicEntityShipSmall implements IShipInvisible
{
	
	private static float ilevel = 25F;
	

	public EntitySubmSo(World world)
	{
		super(world);
		this.setSize(0.6F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.SUBMARINE);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.SubmarineSO);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.SUBMARINE);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.SS]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.SS]);
		this.ModelPos = new float[] {0F, 25F, 0F, 45F};
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
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
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
		
		//pick item
		this.tasks.addTask(20, new EntityAIShipPickItem(this, 4F));
	}

    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		if (!this.world.isRemote)
  		{
  			//add aura to master every 128 ticks
  			if (this.ticksExisted % 128 == 0)
  			{
  				if (getStateFlag(ID.F.UseRingEffect))
  				{
  					//apply ability to player
  					EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  	  				if (getStateFlag(ID.F.IsMarried) && getStateMinor(ID.M.NumGrudge) > 0 && player != null && getDistanceSqToEntity(player) < 256D)
  	  				{
  	  					//potion effect: id, time, level
  	  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 100 + getLevel() * 2));
  	  				}
  				}
  				
  				if (this.ticksExisted % 256 == 0)
  				{
  	  				if (getStateFlag(ID.F.UseRingEffect) && getStateMinor(ID.M.NumGrudge) > 0)
  	  				{
  	  					//apply ability to ship
  	  					this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 46 + getLevel()));
  	  				}
  	  			}//end 256 ticks
  			}//end 128 ticks
  		}    
  	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.48F;
  			}
  			else
  			{
  				return 0F;
  			}
  		}
  		else
  		{
  			return this.height * 0.69F;
  		}
	}

	@Override
	public float getInvisibleLevel()
	{
		return this.ilevel;
	}
	
	@Override
	public void setInvisibleLevel(float level)
	{
		this.ilevel = level;
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
				setStateEmotion(ID.S.State2, ID.State.EQUIP01a, true);
			break;
			case ID.State.EQUIP01a:
				setStateEmotion(ID.S.State2, ID.State.EQUIP02a, true);
			break;
			default:
				setStateEmotion(ID.S.State2, ID.State.NORMALa, true);
			break;
			}
		}
		else
		{
			switch (getStateEmotion(ID.S.State))
			{
			case ID.State.NORMAL:
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
			case ID.State.EQUIP00:
				setStateEmotion(ID.S.State, ID.State.EQUIP01, true);
			break;
			default:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
			}
		}
	}
  	

}