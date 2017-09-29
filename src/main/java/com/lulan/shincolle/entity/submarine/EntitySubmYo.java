package com.lulan.shincolle.entity.submarine;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntitySubmYo extends BasicEntityShipSmall implements IShipInvisible
{
	

	public EntitySubmYo(World world)
	{
		super(world);
		this.setSize(0.6F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.SUBMARINE);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.SubmarineYO);
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
  		}//end server
  		//client side
  		else
  		{
  			if(this.ticksExisted % 4 ==  0)
  			{
    			//若顯示裝備時, 則生成眼睛煙霧特效 (client only)
    			if (getStateEmotion(ID.S.State) > ID.ModelState.NORMAL && !getStateFlag(ID.F.NoFuel) &&
    				(isSitting() && getStateEmotion(ID.S.Emotion) != ID.Emotion.BORED || !isSitting()))
    			{
    				//set origin position
    				float[] eyePosL;
    				float[] eyePosR;
    				float radYaw = this.renderYawOffset * Values.N.DIV_PI_180;
    				float radPitch = this.rotationPitch * Values.N.DIV_PI_180;
    				
    				//坐下位置計算
    				if (this.isSitting())
    				{
    					eyePosL = new float[] {0.35F, 1.5F, -0.5F};
        				eyePosR = new float[] {-0.35F, 1.5F, -0.5F};
    				}
    				else
    				{
    					eyePosL = new float[] {0.35F, 1.8F, -0.35F};
        				eyePosR = new float[] {-0.35F, 1.8F, -0.35F};
    				}

    				//依照新位置, 繼續旋轉Y軸
    				eyePosL = CalcHelper.rotateXYZByYawPitch(eyePosL[0], eyePosL[1], eyePosL[2], radYaw, 0F, 1F);
    				eyePosR = CalcHelper.rotateXYZByYawPitch(eyePosR[0], eyePosR[1], eyePosR[2], radYaw, 0F, 1F);		
    				
    				//旋轉完三軸, 生成特效
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosL[0], this.posY+eyePosL[1], this.posZ+eyePosL[2], 
                    		0D, 0.05D, 0.5D, (byte)16);
    				
    				ParticleHelper.spawnAttackParticleAt(this.posX+eyePosR[0], this.posY+eyePosR[1], this.posZ+eyePosR[2], 
                    		0D, 0.05D, 0.5D, (byte)16);
    			}
    		}//end every 8 ticks
  		}//end client side
  	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (getStateEmotion(ID.S.State) > ID.ModelState.NORMAL)
  		{
  			if (this.isSitting())
  	  		{
  				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
  				{
  					return this.height * 0.55F;
  	  			}
  	  			else
  	  			{
  	  				return this.height * 0.42F;
  	  			}
  	  		}
  	  		else
  	  		{
  	  			return this.height * 0.58F;
  	  		}
  		}
  		else
  		{
  			if (this.isSitting())
  	  		{
  				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
  				{
  					return height * 0.48F;
  	  			}
  	  			else
  	  			{
  	  				return 0F;
  	  			}
  	  		}
  	  		else
  	  		{
  	  			return height * 0.69F;
  	  		}
  		}
	}

	@Override
	public float getInvisibleLevel()
	{
		return 0.2F;
	}
	
	@Override
	public void setInvisibleLevel(float level) {}

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			switch (getStateEmotion(ID.S.State2))
			{
			case ID.ModelState.NORMALa:
				setStateEmotion(ID.S.State2, ID.ModelState.EQUIP00a, true);
			break;
			default:
				setStateEmotion(ID.S.State2, ID.ModelState.NORMALa, true);
			break;
			}
		}
		else
		{
			switch (getStateEmotion(ID.S.State))
			{
			case ID.ModelState.NORMAL:
				setStateEmotion(ID.S.State, ID.ModelState.EQUIP00, true);
			break;
			default:
				setStateEmotion(ID.S.State, ID.ModelState.NORMAL, true);
			break;
			}
		}
	}
	
	//潛艇的輕攻擊一樣使用飛彈
  	@Override
  	//range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
  	public boolean attackEntityWithAmmo(Entity target)
  	{	
		//ammo--
        if (!decrAmmoNum(0, this.getAmmoConsumption())) return false;
        
		//experience++
		addShipExp(ConfigHandler.expGain[1]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk]);
		
  		//morale--
  		decrMorale(1);
  		setCombatTick(this.ticksExisted);
	
		//get attack value
		float atk = this.getAttrs().getAttackDamage();
		float kbValue = 0.15F;
		
		//飛彈是否採用直射
		boolean isDirect = false;
		float launchPos = (float) posY + height * 0.75F;
		
        //calc dist to target
        Dist4d distVec = EntityHelper.getDistanceFromA2B(this, target);
        
        //超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
        if (distVec.distance < 5D)
        {
        	isDirect = true;
        }
        
        if (getShipDepth() > 0D)
        {
        	isDirect = true;
        	launchPos = (float) posY;
        }
        
        //play sound and particle
        applySoundAtAttacker(2, target);
	    applyParticleAtAttacker(2, target, distVec);
		
	    float tarX = (float) target.posX;
	    float tarY = (float) target.posY;
	    float tarZ = (float) target.posZ;
	    
	    //if miss
        if (CombatHelper.applyCombatRateToDamage(this, target, false, (float)distVec.distance, atk) <= 0F)
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atk, kbValue, isDirect, 0.08F);
        this.world.spawnEntity(missile);
        
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
        applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        return true;
  	}
  	

}