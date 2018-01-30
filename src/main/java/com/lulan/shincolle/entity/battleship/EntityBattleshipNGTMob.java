package com.lulan.shincolle.entity.battleship;

import java.util.HashMap;
import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 *   0:head , 1:equip
 */
public class EntityBattleshipNGTMob extends BasicEntityShipHostile
{
	
	private float smokeX, smokeY;
	

	public EntityBattleshipNGTMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.BattleshipNagato);
        this.smokeX = 0F;
        this.smokeY = 0F;
        
		//model display
		this.setStateEmotion(ID.S.State, 3, false);
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(2.2F, 8F);
			this.smokeX = -2.24F;
			this.smokeY = 6F;
		break;
		case 2:
			this.setSize(1.7F, 6F);
			this.smokeX = -1.68F;
			this.smokeY = 4.5F;
		break;
		case 1:
			this.setSize(1.2F, 4F);
			this.smokeX = -1.12F;
			this.smokeY = 3F;
		break;
		default:
			this.setSize(0.7F, 2F);
			this.smokeX = -0.56F;
			this.smokeY = 1.5F;
		break;
		}
	}
	
	@Override
	public void initAttrsServerPost()
	{
		super.initAttrsServerPost();
		
		//add attack effects
		if (this.AttackEffectMap == null) this.AttackEffectMap = new HashMap<Integer, int[]>();
		this.AttackEffectMap.put(19, new int[] {(int)(this.getScaleLevel() / 2), 60+this.getScaleLevel()*40, 25+this.getScaleLevel()*25});
	}

	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.NOTCHED_10);
	}

	//setup AI
	@Override
	protected void setAIList()
	{
		super.setAIList();

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
	}
	
	@Override
  	public void onLivingUpdate()
	{
  		super.onLivingUpdate();

  		//client side
  		if (this.world.isRemote)
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				//生成裝備冒煙特效
  				if (EmotionHelper.checkModelState(1, this.getStateEmotion(ID.S.State)))
  				{
  	  				float[] partPos = CalcHelper.rotateXZByAxis(this.smokeX, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY+this.smokeY, posZ+partPos[0], 1D+this.scaleLevel*1D, 0D, 0D, (byte)43);
  				}
  				
  				if (this.ticksExisted % 8 == 0)
  	  			{
  					//生成氣彈特效
  	  				if (getStateEmotion(ID.S.Phase) == 1 || getStateEmotion(ID.S.Phase) == 3)
  	  				{
  	  	  				ParticleHelper.spawnAttackParticleAtEntity(this, 0.12D+0.1D*this.scaleLevel, 1D, 0D, (byte)1);
  	  				}
  	  			}//end 8 ticks
  			}//end 4 ticks
  		}//end client side
  	}
  	
  	//TYPE 91 AP FIST
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
  	{	
  		//get attack value
		float atk1 = CombatHelper.modDamageByAdditionAttrs(this, target, this.shipAttrs.getAttackDamageHeavy(), 2);
		float atk2 = atk1 * 0.5F;
		
		boolean isTargetHurt = false;
		
		//play cannon fire sound at attacker
		int atkPhase = getStateEmotion(ID.S.Phase);
		
        switch (atkPhase)
        {
        case 1:
        	this.playSound(ModSounds.SHIP_AP_P2, ConfigHandler.volumeFire, 1F);
        break;
        case 3:
        	this.playSound(ModSounds.SHIP_AP_ATTACK, ConfigHandler.volumeFire, 1F);
        break;
    	default:
    		this.playSound(ModSounds.SHIP_AP_P1, ConfigHandler.volumeFire, 1F);
    	break;
        }
		
        //play entity attack sound
        if (this.getRNG().nextInt(10) > 7)
        {
        	this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
        }
        
        //phase++
        atkPhase++;
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
      
        if (atkPhase > 3)
        {	//攻擊準備完成, 計算攻擊傷害
            //calc dist to target
            Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
            
        	//display hit particle on target
	        CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 21, posX, posY, posZ, target.posX, target.posY, target.posZ, true), point);
        	
		    //roll miss, cri, dhit, thit
		    atk1 = CombatHelper.applyCombatRateToDamage(this, target, false, (float)distVec.d, atk1);
	  		
	  		//damage limit on player target
		    atk1 = CombatHelper.applyDamageReduceOnPlayer(target, atk1);
	  		
	  		//check friendly fire
			if (!TeamHelper.doFriendlyFire(this, target)) atk1 = 0F;
			
			//aoe damage
			atk2 = atk1 * 0.5F;
	        
      		//對本體造成atk1傷害
      		isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk1);
      		
      		//move host to target
  			this.motionX = 0D;
  			this.motionY = 0D;
  			this.motionZ = 0D;
  			this.posX = target.posX + distVec.x * 2F;
  			this.posY = target.posY;
  			this.posZ = target.posZ + distVec.z * 2F;
  			this.setPosition(posX, posY, posZ);
  			
      		//對範圍造成atk2傷害
  			Entity hitEntity = null;
            double range = 3.5D + this.scaleLevel * 0.5D;
            AxisAlignedBB impactBox = this.getEntityBoundingBox().expand(range, range, range); 
            List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class, impactBox);
            float atkTemp = atk2;
            
            //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
            if (hitList != null && !hitList.isEmpty())
            {
                for (int i = 0; i < hitList.size(); ++i)
                {
                	atkTemp = atk2;
                	hitEntity = hitList.get(i);
                	
                	//目標不能是自己 or 主人
                	if (hitEntity != this && !TargetHelper.isEntityInvulnerable(hitEntity) && hitEntity.canBeCollidedWith())
                	{
                		//若攻擊到同陣營entity (ex: owner), 則傷害設為0 (但是依然觸發擊飛特效)
                		if (TeamHelper.checkSameOwner(this, hitEntity))
                		{
                			atkTemp = 0F;
                    	}
                		
            		    //roll miss, cri, dhit, thit
                		atkTemp = CombatHelper.applyCombatRateToDamage(this, hitEntity, false, (float)distVec.d, atkTemp);
            	  		
            	  		//damage limit on player target
                		atkTemp = CombatHelper.applyDamageReduceOnPlayer(hitEntity, atkTemp);
            	  		
            	  		//check friendly fire
            			if (!TeamHelper.doFriendlyFire(this, hitEntity)) atkTemp = 0F;
                		
                		//attack
                	    hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), atkTemp);
                	}//end can be collided with
                }//end hit target list for loop
            }//end hit target list != null
        	
        	this.setStateEmotion(ID.S.Phase, 0, true);
        }
        else
        {
        	if (atkPhase == 2)
        	{
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 23, this.posX, this.posY, this.posZ, 0.35D, 0.3D, 0D, true), point);
        	}
        	else
        	{
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 22, this.posX, this.posY, this.posZ, 2D*(this.scaleLevel+1), 1D*(this.scaleLevel+1), 0D, true), point);
        	}
    		
        	this.setStateEmotion(ID.S.Phase, atkPhase, true);
        }
        
        //show emotes
		applyEmotesReaction(3);
        
        return isTargetHurt;
	}
  	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 0.9D*(this.scaleLevel+1), 1D*(this.scaleLevel+1), 1.1D*(this.scaleLevel+1)), point);
  		break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
  		}
  	}

	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.BATTLESHIP;
	}
	

}