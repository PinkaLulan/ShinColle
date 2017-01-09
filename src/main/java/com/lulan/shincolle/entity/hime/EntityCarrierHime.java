package com.lulan.shincolle.entity.hime;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.mounts.EntityMountCaH;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityCarrierHime extends BasicEntityShipCV
{
	
	public EntityCarrierHime(World world)
	{
		super(world);
		this.setSize(0.7F, 1.9F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.CarrierHime);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CV]);
		this.ModelPos = new float[] {-6F, 15F, 0F, 40F};
		this.launchHeight = this.height * 0.9F;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Light] = false;
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		
		//misc
		this.setFoodSaturationMax(24);
		
		this.postInit();
	}

	@Override
	public int getEquipType()
	{
		return 3;
	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
	}

  	//true if use mounts
  	@Override
  	public boolean canSummonMounts()
  	{
  		return true;
  	}
  	
  	@Override
  	public BasicEntityMount summonMountEntity()
  	{
		return new EntityMountCaH(this.world);
	}
  	
  	@Override
  	public float[] getModelPos()
  	{
  		if (this.isRiding())
  		{
  			ModelPos[1] = -15F;
  		}
  		else
  		{
  			ModelPos[1] = 15F;
  		}
  		
		return ModelPos;
	}
  	
  	//被騎乘的高度
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0F;
  			}
  			else
  			{
  				return this.height * 0.33F;
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
		//切換裝備顯示
		if (isSneaking)
		{
			switch (getStateEmotion(ID.S.State2))
			{
			case ID.State.EQUIP00a:
				setStateEmotion(ID.S.State2, ID.State.NORMALa, true);
			break;
			case ID.State.NORMALa:
			default:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00a, true);
			break;
			}
		}
		//切換是否騎乘座騎
		else
		{
			switch (getStateEmotion(ID.S.State))
			{
			case ID.State.EQUIP00:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
				this.setPositionAndUpdate(posX, posY + 2D, posZ);
			break;
			case ID.State.NORMAL:
			default:
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
			}
		}
	}
	
	//AoE melee attack
	@Override
	public boolean attackEntityAsMob(Entity target)
	{
		//get attack value
		float atk = StateFinal[ID.ATK_AL];
		
		//experience++
		addShipExp(ConfigHandler.expGain[0]);
		
		//morale--
		decrMorale(0);
		setCombatTick(this.ticksExisted);
				
	    //entity attack effect
	    applySoundAtAttacker(0, target);
	    applyParticleAtAttacker(0, target, new float[4]);
	    
	    //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, 
        		(float)target.posX, (float)target.posY + target.height * 0.2F, (float)target.posZ,
        		1.3F, atk, 0F, true, -1F);
        this.world.spawnEntity(missile);
	    
	    applyEmotesReaction(3);
	    
	    if (ConfigHandler.canFlare) flareTarget(target);

	    return true;
	}


}