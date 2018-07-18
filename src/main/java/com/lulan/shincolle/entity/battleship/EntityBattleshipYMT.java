package com.lulan.shincolle.entity.battleship;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.dataclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**特殊heavy attack:
 * 用StateEmotion[ID.S.Phase]來儲存攻擊階段
 * Phase: 0:X, 1:集氣, 2:攻擊
 * 
 * model state:
 *   0:cannon, 1:head, 2:umbrella, 3:leg equip
 */
public class EntityBattleshipYMT extends BasicEntityShipSmall
{
	
	public EntityBattleshipYMT(World world)
	{
		super(world);
		this.setSize(0.8F, 2.1F);	//碰撞大小 跟模型大小無關
		this.setStateMinor(ID.M.ShipType, ID.ShipIconType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.BBYamato);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setStateMinor(ID.M.NumState, 4);
		this.setGrudgeConsumeIdle(ConfigHandler.consumeGrudgeShipIdle[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.modelPosInGUI = new float[] {0F, 25F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(30);
		
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
	}
	
	@Override
	public void calcShipAttributesAddEffect()
	{
		super.calcShipAttributesAddEffect();
		this.AttackEffectMap.put(4, new int[] {(int)(this.getLevel() / 70), 100+this.getLevel(), this.getLevel()});
	}
    
    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//client side
  		if (world.isRemote)
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				//生成裝備冒煙特效
  				if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)) && !isSitting() && !getStateFlag(ID.F.NoFuel))
  				{
  					//計算煙霧位置
  	  				float[] partPos = CalcHelper.rotateXZByAxis(-0.63F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY + 1.65D, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
  				}
  				
  				if (this.ticksExisted % 16 == 0)
  				{
  					//spawn beam charge lightning
  	  				if (getStateEmotion(ID.S.Phase) > 0)
  	  				{
  	    	        	ParticleHelper.spawnAttackParticleAtEntity(this, 0.1D, 16D, 1D, (byte)4);
  	  				}
  	  			}//end 16 ticks
  			}//end 4 ticks
  		}
  		else
  		{
  			//every 128 ticks
  			if (this.ticksExisted % 128 == 0)
  			{
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
  								s.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE , 50+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 70, false, false));
  								s.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE , 50+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 70, false, false));
  	  						}
  						}
  					}
  				}//end married buff
  			}//end every 128 ticks
  		}
  	}
  	
  	/** Yamato Cannon
  	 *  phase: 0:X, 1:charge, 2:burst, 3:charge, 3+:attack
  	 *  
  	 *  summon a beam entity, fly directly to target
  	 *  create beam particle between target and host
  	 */
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
  	{
  		//get attack value
		float atk = CombatHelper.modDamageByAdditionAttrs(this, target, this.getAttackBaseDamage(2, target), 3);
		
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
		
		//heavy ammo--
        if (!decrAmmoNum(1, this.getAmmoConsumption())) return false;

        //play entity sound
        if (this.getRNG().nextInt(10) > 7)
        {
        	this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
        }
        
        //check phase
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
        if (getStateEmotion(ID.S.Phase) > 0)
        {	//spawn beam particle & entity
            //calc dist to target
            Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
            
        	//shot sound
        	this.playSound(ModSounds.SHIP_YAMATO_SHOT, ConfigHandler.volumeFire, 1F);
        	
        	//spawn beam entity
            EntityProjectileBeam beam = new EntityProjectileBeam(this.world);
            beam.initAttrs(this, 0, (float)distVec.x, (float)distVec.y, (float)distVec.z, atk, 0.12F);
            this.world.spawnEntity(beam);
            
            //spawn beam particle
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, beam, (float)distVec.x, (float)distVec.y, (float)distVec.z, 1, true), point);
        	
        	this.setStateEmotion(ID.S.Phase, 0, true);
        	return true;
        }
        else
        {
        	//charge sound
        	this.playSound(ModSounds.SHIP_YAMATO_READY, ConfigHandler.volumeFire, 1F);
        	
			//cannon charging particle
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 7, 1D, 0, 0), point);
        	
        	this.setStateEmotion(ID.S.Phase, 1, true);
        }
        
        //show emotes
      	applyEmotesReaction(3);
        
      	if (ConfigHandler.canFlare) this.flareTarget(target);
      	
        return false;
	}
	
	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)))
			{
				return this.height * 0.5F;
			}
			else
			{
				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
				{
					return this.height * 0.1F;
	  			}
	  			else
	  			{
	  				return this.height * 0.4F;
	  			}
			}
  		}
  		else
  		{
  			return this.height * 0.75F;
  		}
	}
	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 0.9D, 1.3D, 1.2D), point);
  		break;
  		case 2:  //heavy cannon
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
  		}
  	}


}