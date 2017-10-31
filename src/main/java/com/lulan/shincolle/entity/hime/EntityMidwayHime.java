package com.lulan.shincolle.entity.hime;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.mounts.EntityMountIsH;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CombatHelper;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * model state:
 *   0:mounts, 1:hat, 2:horn, 3:bowtie, 4:shawl, 5:hand, 6:leg, 7:leg armor
 */
public class EntityMidwayHime extends BasicEntityShipCV
{
	
	public EntityMidwayHime(World world)
	{
		super(world);
		this.setSize(0.6F, 1.6F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.MidwayHime);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.AVIATION);
		this.setStateMinor(ID.M.NumState, 8);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BBV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BBV]);
		this.ModelPos = new float[] {-6F, 30F, 0F, 40F};
		this.launchHeight = this.height * 0.7F;
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = false; //TODO NYI
		
		//misc
		this.setFoodSaturationMax(35);
		
		this.postInit();
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
  			//heal effect
        	if (this.ticksExisted % 128 == 0)
        	{
        		//1: 增強被動回血
        		if (getStateMinor(ID.M.NumGrudge) > 0 && this.getHealth() < this.getMaxHealth())
        		{
        			this.heal(this.getMaxHealth() * 0.06F + 1F);//TODO
        		}
        		
        		//2: 結婚後, 
				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) && getStateMinor(ID.M.NumGrudge) > 50)
				{
					//TODO
				}//end heal ability
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

	/**
	 * TODO
	 */
	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		return super.attackEntityWithHeavyAmmo(target);
//  		//get attack value
//  		float atk = this.getAttackBaseDamage(2, target);
//  		float launchPos = (float)posY + 0.4F;
//		
//        //calc dist to target
//        Dist4d distVec = EntityHelper.getDistanceFromA2B(this, target);
//  		
//  		if (this.getRidingEntity() instanceof BasicEntityMount)
//  		{
//  			launchPos = (float)posY - 1.2F;
//  		}
//  		
//  		//experience++
//  		addShipExp(ConfigHandler.expGain[2]);
//  		
//  		//grudge--
//  		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
//  		
//  		//morale--
//  		decrMorale(2);
//  		setCombatTick(this.ticksExisted);
//  	
//  		//play attack effect
//        applySoundAtAttacker(2, target);
//	    applyParticleAtAttacker(2, target, distVec);
//          
//  		//heavy ammo--
//  		if(!decrAmmoNum(1, this.getAmmoConsumption())) return false;
//  		
//	    float tarX = (float) target.posX;
//	    float tarY = (float) target.posY;
//	    float tarZ = (float) target.posZ;
//	    
//	    //if miss
//        if (CombatHelper.applyCombatRateToDamage(this, target, false, (float)distVec.distance, atk) <= 0F)
//        {
//        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
//        	tarY = tarY + this.rand.nextFloat() * 5F;
//        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
//        	
//        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
//        }
//  		
//  		//spawn missile
//  		EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, 
//          		tarX, tarY+target.height*0.35F, tarZ, launchPos, atk, 0.15F, true, 0.3F);
//  		this.world.spawnEntity(missile);
//  		
//  		//play target effect
//        applySoundAtTarget(2, target);
//        applyParticleAtTarget(2, target, distVec);
//      	applyEmotesReaction(3);
//      	
//      	if (ConfigHandler.canFlare) flareTarget(target);
//      	
//  		return true;
  	}

  	//true if use mounts
  	@Override
  	public boolean hasShipMounts()
  	{
  		return false;  //TODO
  	}
  	
  	@Override
  	public BasicEntityMount summonMountEntity()
  	{
		return new EntityMountIsH(this.world); //TODO
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