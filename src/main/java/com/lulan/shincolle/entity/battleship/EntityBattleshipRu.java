package com.lulan.shincolle.entity.battleship;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityBattleshipRu extends BasicEntityShip
{
	
	private int remainAttack;
	private BlockPos skillTarget;
	
	
	public EntityBattleshipRu(World world)
	{
		super(world);
		this.setSize(0.7F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.BattleshipRU);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.ModelPos = new float[] {0F, 25F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		this.remainAttack = 0;
		this.skillTarget = BlockPos.ORIGIN;
		
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

		//use range attack
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));
	}

	//Ta級額外增加屬性
	@Override
	public void calcShipAttributes()
	{
		EffectEquip[ID.EquipEffect.DHIT] += 0.15F;
		EffectEquip[ID.EquipEffect.THIT] += 0.15F;
		
		super.calcShipAttributes();	
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
				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
					(this.ticksExisted & 511) < 400 &&
					!this.isSitting() && !this.isRiding())
  	  			{
					ParticleHelper.spawnAttackParticleAtEntity(this, 1.34D, 0.12D, 0.17D, (byte)17);
				}
				
				//every 64 ticks
				if ((this.ticksExisted & 63) == 0)
				{
					//顯示流汗表情
					if (this.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED &&
						this.getStateEmotion(ID.S.State) >= ID.State.EQUIP01 &&
						(this.ticksExisted & 511) > 400)
					{
						ParticleHelper.spawnAttackParticleAtEntity(this, 0.5D, 0D, this.rand.nextInt(2) == 0 ? 0 : 2, (byte)36);
					}
				}
			}
		}
		//server side
		else
		{
			//update heavy attack effect
			if (this.StateEmotion[ID.S.Phase] > 0)
			{
				this.updateSkillEffect();
			}
		}
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
		        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, 
		        		this.skillTarget.getX() + this.rand.nextFloat() * 8F - 4F,
		        		this.skillTarget.getY() + this.rand.nextFloat() * 4F - 2F,
		        		this.skillTarget.getZ() + this.rand.nextFloat() * 8F - 4F,
		        		(float)this.posY + this.height, getAttackBaseDamage(2, null), 0.15F,
		        		false, 0.03F);
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
			if (this.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
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

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			int i = getStateEmotion(ID.S.State2) + 1;
			if (i > ID.State.EQUIP00a) i = ID.State.NORMALa;
			setStateEmotion(ID.S.State2, i, true);
		}
		else
		{
			int i = getStateEmotion(ID.S.State) + 1;
			if (i > ID.State.EQUIP01) i = ID.State.NORMAL;
			setStateEmotion(ID.S.State, i, true);
		}
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
		
		//計算目標距離
		float[] distVec = new float[4];
		float tarX = (float) target.posX;
		float tarY = (float) target.posY;
		float tarZ = (float) target.posZ;
		
		distVec[0] = tarX - (float) this.posX;
        distVec[1] = tarY - (float) this.posY;
        distVec[2] = tarZ - (float) this.posZ;
		distVec[3] = MathHelper.sqrt(distVec[0]*distVec[0] + distVec[1]*distVec[1] + distVec[2]*distVec[2]);
        
        if (distVec[3] > 1.0E-4D)
        {
            distVec[0] = distVec[0] / distVec[3];
            distVec[1] = distVec[1] / distVec[3];
            distVec[2] = distVec[2] / distVec[3];
        }
        
        //超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
        if (distVec[3] < 5F)
        {
        	isDirect = true;
        }
        
        if (getShipDepth() > 0D)
        {
        	isDirect = true;
        	launchPos = (float) posY;
        }
        
        //calc miss chance, miss: add random offset(0~6) to missile target 
        float missChance = 0.2F + 0.15F * (distVec[3] / StateFinal[ID.HIT]) - 0.001F * StateMinor[ID.M.ShipLevel];
        missChance -= EffectEquip[ID.EquipEffect.MISS];	//equip miss reduce
        if (missChance > 0.35F) missChance = 0.35F;	//max miss chance = 30%
       
        if (this.rand.nextFloat() < missChance)
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	applyParticleSpecialEffect(0);  //miss particle
        }
        
		if (this.StateEmotion[ID.S.Phase] == 0)
		{
			//play sound at attacker
			this.playSound(ModSounds.SHIP_HITMETAL, ConfigHandler.volumeFire, 1F);
			
  			if (this.rand.nextInt(10) > 7)
  			{
  				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  			
  			applyParticleAtAttacker(2, target, new float[0]);
  			
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
  			return CalcHelper.calcDamageBySpecialEffect(this, target, StateFinal[ID.ATK], 0);
  		case 2:  //heavy cannon
  			return StateFinal[ID.ATK_H] * 0.2F;
  		case 3:  //light aircraft
  			return StateFinal[ID.ATK_AL];
  		case 4:  //heavy aircraft
  			return StateFinal[ID.ATK_AH];
		default: //melee
			return StateFinal[ID.ATK] * 2F;
  		}
  	}
	
    //change light cannon particle
    @Override
    public void applyParticleAtAttacker(int type, Entity target, float[] vec)
    {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 19, this.posX, this.posY, this.posZ, vec[0], 0.7F, vec[2], true), point);
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
