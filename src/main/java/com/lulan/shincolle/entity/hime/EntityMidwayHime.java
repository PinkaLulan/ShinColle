package com.lulan.shincolle.entity.hime;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.mounts.EntityMountMiH;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * model state:
 *   0:mounts, 1:collar
 */
public class EntityMidwayHime extends BasicEntityShipCV
{
	
	public EntityMidwayHime(World world)
	{
		super(world);
		this.setSize(0.7F, 2.0F);
		this.setStateMinor(ID.M.ShipType, ID.ShipIconType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.MidwayHime);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.AVIATION);
		this.setStateMinor(ID.M.NumState, 2);
		this.setGrudgeConsumeIdle(ConfigHandler.consumeGrudgeShipIdle[ID.ShipConsume.BBV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BBV]);
		this.modelPosInGUI = new float[] {-6F, 30F, 0F, 40F};
		this.launchHeight = this.height * 0.7F;
		
		//misc
		this.setFoodSaturationMax(35);
		
		this.initPre();
	}

	@Override
	public int getEquipType()
	{
		return 2;
	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));
	}
	
	//check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		//server side
  		if (!this.world.isRemote)
  		{
  			//every 128 ticks
        	if ((this.ticksExisted & 127) == 0)
        	{
        		//1: 增強被動回血
        		if (getStateMinor(ID.M.NumGrudge) > 0 && this.getHealth() < this.getMaxHealth())
        		{
        			this.heal(this.getMaxHealth() * 0.09F + 1F);
        		}
        		
        		//married effect
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0)
  				{
  					//apply buff to nearby ships
  					List<BasicEntityShip> shiplist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, this.getEntityBoundingBox().expand(16D, 16D, 16D));
  					if (shiplist != null && shiplist.size() > 0)
  					{
  						for (BasicEntityShip s : shiplist)
  						{
  							if (TeamHelper.checkSameOwner(this, s))
  							{
  								//potion effect: id, time, level
  								s.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION , 50+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 50, false, false));
  							}
  						}
  					}
  					
  					//apply buff to owner
  					EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  	  				if (player != null && getDistanceSqToEntity(player) < 256D && !this.isMorph)
  	  				{
  	  					//potion effect: id, time, level
  	  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION , 50+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 50, false, false));
					}
  				}//end married buff
        	}
  		}//end server side
  			
  		super.onLivingUpdate();
  	}

	@Override
	public float getAttackBaseDamage(int type, Entity target)
	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			return CombatHelper.modDamageByAdditionAttrs(this, target, this.shipAttrs.getAttackDamage(), 0);
  		case 2:  //heavy cannon
  			return this.shipAttrs.getAttackDamageHeavy();
  		case 3:  //light aircraft
  			return this.shipAttrs.getAttackDamageAir();
  		case 4:  //heavy aircraft
  			return this.shipAttrs.getAttackDamageAirHeavy();
		default: //melee
			return this.shipAttrs.getAttackDamage();
  		}
  	}

  	//true if use mounts
  	@Override
  	public boolean hasShipMounts()
  	{
  		return true;
  	}
  	
  	@Override
  	public BasicEntityMount summonMountEntity()
  	{
		return new EntityMountMiH(this.world);
	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.2F;
  			}
  			else
  			{
  				return this.height * 0.52F;
  			}
  		}
  		else
  		{
  			return this.height * 0.76F;
  		}
	}
	
	
}