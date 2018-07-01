package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EntityHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * model state:
 *   0:head
 */
public class EntityDestroyerHa extends BasicEntityShipSmall
{

	
	public EntityDestroyerHa(World world)
	{
		super(world);
		this.setSize(0.9F, 1.7F);
		this.setStateMinor(ID.M.ShipType, ID.ShipIconType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.DDHA);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.DESTROYER);
		this.setStateMinor(ID.M.NumState, 1);
		this.setGrudgeConsumeIdle(ConfigHandler.consumeGrudgeShipIdle[ID.ShipConsume.DD]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.DD]);
		this.modelPosInGUI = new float[] {0F, 0F, 0F, 25F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
		this.initPre();
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
		
		//pick item
		this.tasks.addTask(20, new EntityAIShipPickItem(this, 4F));
	}

    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		if(!world.isRemote)
  		{
  			//add aura to master every 128 ticks
  			if (this.ticksExisted % 128 == 0 && !this.isMorph)
  			{
  				EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0 && player != null && getDistanceSqToEntity(player) < 256D)
  				{
  					//potion effect: id, time, level
  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST , 80+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 45 + 1, false, false));
  				}
  			}
  		}    
  	}

  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
  			return (double)this.height * 0.27F;
  		}
  		else
  		{
  			return (double)this.height * 0.7F;
  		}
	}
	
	
}