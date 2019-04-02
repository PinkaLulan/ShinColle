package com.lulan.shincolle.entity.battleship;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipSummonAttack;
import com.lulan.shincolle.entity.other.EntityRensouhou;
import com.lulan.shincolle.entity.other.EntityRensouhouS;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.TeamHelper;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

/**
 * model state:
 *   0:rensouhou type, 1:cape, 2:armor
 */
public class EntityBattleshipTa extends BasicEntityShip implements IShipSummonAttack
{
	
	public int numRensouhou;
	
	
	public EntityBattleshipTa(World world)
	{
		super(world);
		this.setSize(0.7F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.BBTA);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setStateMinor(ID.M.NumState, 3);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.ModelPos = new float[] {0F, 25F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
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
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));
	}
	
	//check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//server side
  		if (!world.isRemote)
  		{
  			//every 128 ticks
  			if (this.ticksExisted % 128 == 0)
  			{
  				//add num of rensouhou
  				if (this.numRensouhou < 6) numRensouhou++;
  				
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
  								s.addPotionEffect(new PotionEffect(MobEffects.SPEED , 50+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 80, false, false));
  							}
  						}
  					}
  				}//end married buff
  			}//end every 128 ticks
  		}//end server side
  	}

	//Ta級額外增加屬性
	@Override
	public void calcShipAttributesAddRaw()
	{
		super.calcShipAttributesAddRaw();
		
		this.getAttrs().setAttrsRaw(ID.Attrs.CRI, this.getAttrs().getAttrsRaw(ID.Attrs.CRI) + 0.1F);
		this.getAttrs().setAttrsRaw(ID.Attrs.MISS, this.getAttrs().getAttrsRaw(ID.Attrs.MISS) + 0.1F);
	}
	
	@Override
	//range attack method, cost light ammo, attack delay = 20 / attack speed, damage = 100% atk 
	public boolean attackEntityWithAmmo(Entity target)
	{
		//light ammo--
        if (!decrAmmoNum(0, 4 * this.getAmmoConsumption())) return false;
        
		//check num rensouhou
  		if (this.numRensouhou <= 0)
  		{
  			return false;
  		}
  		else
  		{
  			this.numRensouhou--;
  		}
  		
        //play entity attack sound
		if (this.rand.nextInt(10) > 7)
		{
			this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
        }
        
        //experience++
  		addShipExp(ConfigHandler.expGain[1] * 2);
  		
  		//grudge--
  		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk] * 4);
  		
  		//morale--
  		decrMorale(1);
  		setCombatTick(this.ticksExisted);

        //發射者煙霧特效 (招喚連裝砲不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  		
		//spawn airplane
    	if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)))
    	{
    		EntityRensouhou rensoho1 = new EntityRensouhou(this.world);
    		rensoho1.initAttrs(this, target, 0);
            this.world.spawnEntity(rensoho1);
    	}
    	else
    	{
    		EntityRensouhouS rensoho2 = new EntityRensouhouS(this.world);
    		rensoho2.initAttrs(this, target, 0);
            this.world.spawnEntity(rensoho2);
    	}
    	
    	//show emotes
      	applyEmotesReaction(3);
      	
      	if (ConfigHandler.canFlare) this.flareTarget(target);
      	
        return true;
	}

	@Override
	public int getNumServant()
	{
		return this.numRensouhou;
	}

	@Override
	public void setNumServant(int num)
	{
		this.numRensouhou = num;
	}
	
	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
  				return 0F;
  			}
  			else
  			{
  				return this.height * 0.47F;
  			}
  		}
  		else
  		{
  			return this.height * 0.76F;
  		}
	}
	
	
}