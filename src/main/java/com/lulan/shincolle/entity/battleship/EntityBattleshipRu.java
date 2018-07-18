package com.lulan.shincolle.entity.battleship;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.Dist4d;
import com.lulan.shincolle.reference.dataclass.MissileData;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 *   0:weapon , 1:armor, 2:glove, 3:eye effect
 */
public class EntityBattleshipRu extends BasicEntityShip
{
	
	private int remainAttack;
	private BlockPos skillTarget;
	
	
	public EntityBattleshipRu(World world)
	{
		super(world);
		this.setSize(0.7F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipIconType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.BBRU);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setStateMinor(ID.M.NumState, 4);
		this.setGrudgeConsumeIdle(ConfigHandler.consumeGrudgeShipIdle[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.modelPosInGUI = new float[] {0F, 25F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		this.remainAttack = 0;
		this.skillTarget = BlockPos.ORIGIN;
		
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

		//use range attack
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));
	}

	//Ta級額外增加屬性
	@Override
	public void calcShipAttributesAddRaw()
	{
		super.calcShipAttributesAddRaw();
		
		this.getAttrs().setAttrsRaw(ID.Attrs.CRI, this.getAttrs().getAttrsRaw(ID.Attrs.CRI) + 0.05F);
		this.getAttrs().setAttrsRaw(ID.Attrs.DHIT, this.getAttrs().getAttrsRaw(ID.Attrs.DHIT) + 0.05F);
		this.getAttrs().setAttrsRaw(ID.Attrs.THIT, this.getAttrs().getAttrsRaw(ID.Attrs.THIT) + 0.05F);
	}
	
	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		
		if (this.world.isRemote)
		{
			//every 16 ticks
			if ((this.ticksExisted & 15) == 0)
			{
				if (EmotionHelper.checkModelState(3, this.getStateEmotion(ID.S.State)) &&
					(this.ticksExisted & 511) < 400 && !this.isSitting() && !this.isRiding())
  	  			{
					ParticleHelper.spawnAttackParticleAtEntity(this, 1.34D, 0.12D, 0.17D, (byte)17);
				}
				
				//every 64 ticks
				if ((this.ticksExisted & 63) == 0)
				{
					//顯示流汗表情
					if (this.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED &&
						EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)) &&
						(this.ticksExisted & 511) > 400)
					{
						ParticleHelper.spawnAttackParticleAtEntity(this, 0.5D, 0D, this.rand.nextInt(2) == 0 ? 0 : 2, (byte)36);
					}
					
					//every 128 ticks
		    		if ((this.ticksExisted & 127) == 0)
		    		{
			    		//apply potion effect in the night
			        	if (this.world.isDaytime() && this.getStateFlag(ID.F.UseRingEffect))
			        	{	
		        			this.addPotionEffect(new PotionEffect(MobEffects.LUCK, 150, getStateMinor(ID.M.ShipLevel) / 140, false, false));
		        		}
		        	}//end every 128 ticks
				}//end every 64 ticks
			}//end every 16 ticks
		}//end client side
		//server side
		else
		{
			//update heavy attack effect
			if (this.StateEmotion[ID.S.Phase] > 0)
			{
				this.updateSkillEffect();
			}
		}//end server side
	}
	
	private void updateSkillEffect()
	{
		if (this.remainAttack > 0)
		{
			//every X ticks
			if ((this.ticksExisted & 3) == 0)
			{
				//attack--
				this.remainAttack--;
				
				//spawn missile
				MissileData md = this.getMissileData(2);
				float[] data = new float[] {getAttackBaseDamage(2, null), 0.15F, (float)this.posY + this.height * 0.4F,
						this.skillTarget.getX() + this.rand.nextFloat() * 8F - 4F,
						this.skillTarget.getY() + this.rand.nextFloat() * 4F - 2F,
						this.skillTarget.getZ() + this.rand.nextFloat() * 8F - 4F, 160, 0.35F, md.vel0, md.accY1, md.accY2};
				EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, md.type, 1, data);
		        this.world.spawnEntity(missile);
		        
		        //apply sound
		        this.applySoundAtAttacker(2, null);
			}
		}
		else
		{
			//reset attack state
			this.StateEmotion[ID.S.Phase] = 0;
			this.remainAttack = 0;
		}
	}
	
	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)))
			{
				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
				{
	  				return this.height * 0.51F;
	  			}
				else if (getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED)
				{
	  				return 0F;
	  			}
	  			else
	  			{
	  				return this.height * 0.55F;
	  			}
			}
			else
			{
				return this.height * 0.45F;
			}
  		}
  		else
  		{
  			return this.height * 0.72F;
  		}
	}
	
	public boolean attackEntityWithHeavyAmmo(BlockPos target)
	{
		//ammo--
        if (!decrAmmoNum(1, this.getAmmoConsumption())) return false;
        
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
	
		//飛彈是否採用直射
		boolean isDirect = false;
		float launchPos = (float) posY + height * 0.75F;
		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this.getPosition(), target);
		
	    float tarX = (float) target.getX();
	    float tarY = (float) target.getY();
	    float tarZ = (float) target.getZ();
	    
	    //if miss
	    if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
	    {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
		if (this.StateEmotion[ID.S.Phase] == 0)
		{
			//play sound at attacker
			this.playSound(ModSounds.SHIP_HITMETAL, ConfigHandler.volumeFire, 1F);
			
  			if (this.rand.nextInt(10) > 7)
  			{
  				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  			
  			applyParticleAtAttacker(2, this, Dist4d.ONE);
  			
  			//charging
  			this.StateEmotion[ID.S.Phase] = 1;
  			this.remainAttack += 5 + (int)(this.getLevel() * 0.035F);
  			this.skillTarget = new BlockPos(tarX, tarY + 0.5D, tarZ);
		}
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        return true;
	}
	
	//shot a lotsssss missiles
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		//ammo--
        if (!decrAmmoNum(1, this.getAmmoConsumption())) return false;
        
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
	
		//飛彈是否採用直射
		boolean isDirect = false;
		float launchPos = (float) posY + height * 0.75F;
		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
		
	    float tarX = (float) target.posX;
	    float tarY = (float) target.posY;
	    float tarZ = (float) target.posZ;
	    
	    //if miss
	    if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
	    {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
		if (this.StateEmotion[ID.S.Phase] == 0)
		{
			//play sound at attacker
			this.playSound(ModSounds.SHIP_HITMETAL, ConfigHandler.volumeFire, 1F);
			
  			if (this.rand.nextInt(10) > 7)
  			{
  				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  			
  			applyParticleAtAttacker(2, target, Dist4d.ONE);
  			
  			//charging
  			this.StateEmotion[ID.S.Phase] = 1;
  			this.remainAttack += 5 + (int)(this.getLevel() * 0.035F);
  			this.skillTarget = new BlockPos(tarX, tarY + target.height * 0.35D, tarZ);
		}
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        return true;
	}
	
	@Override
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			return CombatHelper.modDamageByAdditionAttrs(this, target, this.shipAttrs.getAttackDamage(), 0);
  	  	case 2:  //heavy cannon
  			return this.shipAttrs.getAttackDamageHeavy() * 0.2F;
  		case 3:  //light aircraft
  			return this.shipAttrs.getAttackDamageAir();
  		case 4:  //heavy aircraft
  			return this.shipAttrs.getAttackDamageAirHeavy();
		default: //melee
			return this.shipAttrs.getAttackDamage() * 2F;
  		}
  	}
	
    //change light cannon particle
    @Override
    public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
    {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 19, this.posX, this.posY, this.posZ, distVec.x, 0.7F, distVec.z, true), point);
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