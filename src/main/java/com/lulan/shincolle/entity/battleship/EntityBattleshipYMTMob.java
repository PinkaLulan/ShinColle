package com.lulan.shincolle.entity.battleship;

import java.util.HashMap;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 *   0:cannon, 1:head, 2:umbrella, 3:leg equip
 */
public class EntityBattleshipYMTMob extends BasicEntityShipHostile
{
	
	private float smokeX, smokeY;
	

	public EntityBattleshipYMTMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.BattleshipYamato);
        this.smokeX = 0F;
        this.smokeY = 0F;
        
		//model display
		this.setStateEmotion(ID.S.State, 15, false);
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(2.3F, 8.4F);
			this.smokeX = -2.52F;
			this.smokeY = 6.6F;
		break;
		case 2:
			this.setSize(1.8F, 6.3F);
			this.smokeX = -1.89F;
			this.smokeY = 4.95F;
		break;
		case 1:
			this.setSize(1.3F, 4.2F);
			this.smokeX = -1.26F;
			this.smokeY = 3.3F;
		break;
		default:
			this.setSize(0.8F, 2.1F);
			this.smokeX = -0.63F;
			this.smokeY = 1.65F;
		break;
		}
	}

	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.PINK, BossInfo.Overlay.NOTCHED_10);
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
	public void initAttrsServerPost()
	{
		super.initAttrsServerPost();
		
		//add attack effects
		if (this.AttackEffectMap == null) this.AttackEffectMap = new HashMap<Integer, int[]>();
		this.AttackEffectMap.put(4, new int[] {(int)(this.getScaleLevel() / 1.5), 100+this.getScaleLevel()*50, 25+this.getScaleLevel()*25});
	}
	
	//num rensouhou++
	@Override
  	public void onLivingUpdate()
	{
  		super.onLivingUpdate();
  		
  		//client side
  		if (this.world.isRemote)
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
				//計算煙霧位置, 生成裝備冒煙特效
  				float[] partPos = CalcHelper.rotateXZByAxis(this.smokeX, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY+this.smokeY, posZ+partPos[0], 1D+this.scaleLevel*1D, 0D, 0D, (byte)43);
  			
  				if (this.ticksExisted % 16 == 0)
  				{
  					//spawn beam charge lightning
  	  				if (getStateEmotion(ID.S.Phase) > 0)
  	  				{
  	    	        	ParticleHelper.spawnAttackParticleAtEntity(this, 0.1D+this.scaleLevel*1D, 16D, 1D, (byte)4);
  	  				}
  	  			}//end 16 ticks
  			}//end 4 ticks
  		}
  	}
  	
  	//TYPE 91 AP FIST
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
  	{	
  		//get attack value
  		float atk = CombatHelper.modDamageByAdditionAttrs(this, target, this.getAttackBaseDamage(2, target), 3);
  		
        //play entity attack sound
        if (this.getRNG().nextInt(10) > 7)
        {
        	this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
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
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, beam, (float)distVec.x, (float)distVec.y, (float)distVec.z, 2, true), point);
        	
        	this.setStateEmotion(ID.S.Phase, 0, true);
        	return true;
        }
        else
        {
        	//charge sound
        	this.playSound(ModSounds.SHIP_YAMATO_READY, ConfigHandler.volumeFire, 1F);
        	
			//cannon charging particle
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 7, 1D+this.scaleLevel*0.4D, 0, 0), point);
        	
        	this.setStateEmotion(ID.S.Phase, 1, true);
        }
        
        //show emotes
		applyEmotesReaction(3);
        
        return false;
	}
  	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			//double smoke
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 1D*(this.scaleLevel+1), 1.0D*(this.scaleLevel+1), 1.6D*(this.scaleLevel+1)), point);
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 0.9D*(this.scaleLevel+1), 1.2D*(this.scaleLevel+1), 1.0D*(this.scaleLevel+1)), point);
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 1.1D*(this.scaleLevel+1), 1.1D*(this.scaleLevel+1), 0.5D*(this.scaleLevel+1)), point);
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