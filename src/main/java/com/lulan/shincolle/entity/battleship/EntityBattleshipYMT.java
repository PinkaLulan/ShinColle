package com.lulan.shincolle.entity.battleship;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**特殊heavy attack:
 * 用StateEmotion[ID.S.Phase]來儲存攻擊階段
 * Phase: 0:X, 1:集氣, 2:攻擊
 */
public class EntityBattleshipYMT extends BasicEntityShipSmall
{
	
	public EntityBattleshipYMT(World world)
	{
		super(world);
		this.setSize(0.8F, 2.1F);	//碰撞大小 跟模型大小無關
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.BattleshipYamato);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.ModelPos = new float[] {0F, 15F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(30);
		
		this.postInit();
	}
	
	//for morph
	@Override
	public float getEyeHeight()
	{
		return 1.7375F;
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
  				if (getStateEmotion(ID.S.State) >= ID.State.EQUIP01 && !isSitting() && !getStateFlag(ID.F.NoFuel))
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
		float atk = CalcHelper.calcDamageBySpecialEffect(this, target, StateFinal[ID.ATK_H], 3);
		
		//計算目標距離
		float tarX = (float)target.posX;	//for miss chance calc
		float tarY = (float)(target.posY + target.height * 0.5F);
		float tarZ = (float)target.posZ;
		float[] distVec = new float[4];
		
		distVec[0] = tarX - (float)this.posX;
		distVec[1] = tarY - (float)this.posY;
		distVec[2] = tarZ - (float)this.posZ;
		distVec[3] = MathHelper.sqrt(distVec[0]*distVec[0] + distVec[1]*distVec[1] + distVec[2]*distVec[2]);
        if (distVec[3] < 0.001F) distVec[3] = 0.001F; //prevent large dXYZ
        
        distVec[0] = distVec[0] / distVec[3];
        distVec[1] = distVec[1] / distVec[3];
        distVec[2] = distVec[2] / distVec[3];
		
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
        	//shot sound
        	this.playSound(ModSounds.SHIP_YAMATO_SHOT, ConfigHandler.volumeFire, 1F);
        	
        	//spawn beam entity
            EntityProjectileBeam beam = new EntityProjectileBeam(this.world);
            beam.initAttrs(this, 0, distVec[0], distVec[1], distVec[2], atk, 0.12F);
            this.world.spawnEntity(beam);
            
            //spawn beam particle
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, beam, distVec[0], distVec[1], distVec[2], 1, true), point);
        	
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
	public int getKaitaiType()
	{
		return 3;
	}
	
	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (getStateEmotion(ID.S.State) > ID.State.NORMAL)
			{
				return (double)this.height * 0.4F;
			}
			else
			{
				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
				{
					return (double)this.height * -0.1F;
	  			}
	  			else
	  			{
	  				return (double)this.height * 0.3F;
	  			}
			}
  		}
  		else
  		{
  			return (double)this.height * 0.8F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			switch (getStateEmotion(ID.S.State2))
			{
			case ID.State.NORMAL_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00_2, true);
			break;
			case ID.State.EQUIP00_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP01_2, true);
			break;
			case ID.State.EQUIP01_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP02_2, true);
			break;
			default:
				setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
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
			case ID.State.EQUIP01:
				setStateEmotion(ID.S.State, ID.State.EQUIP02, true);
			break;
			default:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
			}
		}
	}
	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, float[] vec)
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