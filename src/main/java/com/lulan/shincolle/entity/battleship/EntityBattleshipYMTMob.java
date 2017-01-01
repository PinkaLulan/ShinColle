package com.lulan.shincolle.entity.battleship;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityBattleshipYMTMob extends BasicEntityShipHostile
{
	
	private float smokeX, smokeY;
	

	public EntityBattleshipYMTMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.Ship.BattleshipYamato);
		this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, getStateMinor(ID.M.ShipClass)+2);
        this.smokeX = 0F;
        this.smokeY = 0F;
        
		//model display
		this.setStateEmotion(ID.S.State2, ID.State.EQUIP02_2, false);
		this.setStateEmotion(ID.S.State, ID.State.EQUIP02, false);
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(1.8F, 7.7F);
			this.smokeX = -2.3F;
			this.smokeY = 5.5F;
		break;
		case 2:
			this.setSize(1.4F, 5.5F);
			this.smokeX = -0.42F;
			this.smokeY = 1.4F;
		break;
		case 1:
			this.setSize(0.95F, 3.3F);
			this.smokeX = -0.42F;
			this.smokeY = 1.4F;
		break;
		default:
			this.setSize(0.8F, 2.1F);
			this.smokeX = -0.55F;
			this.smokeY = 1.75F;
		break;
		}
	}
	
	@Override
	protected float[] getAttrsMod()
	{                     //HP    ATK   DEF   SPD   MOV   HIT
		return new float[] {1.2F, 1.2F, 1F,   1.2F, 0.8F, 1.2F};
	}
	
	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS);
	}

	//setup AI
	@Override
	protected void setAIList()
	{
		super.setAIList();

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
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
  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY+this.smokeY, posZ+partPos[0], 1D+this.scaleLevel*0.3D, 0D, 0D, (byte)43);
  			
  				if (this.ticksExisted % 16 == 0)
  				{
  					//spawn beam charge lightning
  	  				if (getStateEmotion(ID.S.Phase) > 0)
  	  				{
  	    	        	ParticleHelper.spawnAttackParticleAtEntity(this, 1D, 16, 1D, (byte)4);
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
  		float atk = CalcHelper.calcDamageBySpecialEffect(this, target, this.atk * 3F, 3);
		
		//計算目標距離
		float tarX = (float)target.posX;	//for miss chance calc
		float tarY = (float)target.posY;
		float tarZ = (float)target.posZ;
		float distX = tarX - (float)this.posX;
		float distY = tarY - (float)(this.posY + this.height * 0.5F);
		float distZ = tarZ - (float)this.posZ;
        float distSqrt = MathHelper.sqrt(distX*distX + distY*distY + distZ*distZ);
        if (distSqrt < 0.001F) distSqrt = 0.001F; //prevent large dXYZ
        float dX = distX / distSqrt;
        float dY = distY / distSqrt;
        float dZ = distZ / distSqrt;

        //play entity attack sound
        if (this.getRNG().nextInt(10) > 7)
        {
        	this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
        }
        
        //check phase
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
        if (getStateEmotion(ID.S.Phase) > 0)
        {	//spawn beam particle & entity
        	//shot sound
        	this.playSound(ModSounds.SHIP_YAMATO_SHOT, ConfigHandler.volumeFire, 1F);
        	
        	//spawn beam entity
            EntityProjectileBeam beam = new EntityProjectileBeam(this.world);
            beam.initAttrs(this, 0, dX, dY, dZ, atk, 0.12F);
            this.world.spawnEntity(beam);
            
            //spawn beam particle
            CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, beam, dX, dY, dZ, 2, true), point);
        	
        	this.setStateEmotion(ID.S.Phase, 0, true);
        	return true;
        }
        else
        {
        	//charge sound
        	this.playSound(ModSounds.SHIP_YAMATO_READY, ConfigHandler.volumeFire, 1F);
        	
			//cannon charging particle
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 7, 2D, 0, 0), point);
        	
        	this.setStateEmotion(ID.S.Phase, 1, true);
        }
        
        //show emotes
		applyEmotesReaction(3);
        
        return false;
	}
  	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, float[] vec)
	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			//double smoke
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 3.4D, 3.2D, 3D), point);
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 3.4D, 3.2D, 3D), point);
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