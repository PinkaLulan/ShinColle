package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityRensouhouMob;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.Dist4d;
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
 *   0:rensouhou type, 1:cannon, 2:hair anchor, 3:hat1, 4:hat2, 5:hat3
 *   if 3 & 4 false = no hat
 */
public class EntityDestroyerShimakazeMob extends BasicEntityShipHostile
{

	public int numRensouhou;
	
	
	public EntityDestroyerShimakazeMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.DDShimakaze);
		this.numRensouhou = 10;
		
		//model display
        this.setStateEmotion(ID.S.State, rand.nextInt(64), false);
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(1.7F, 6.4F);
		break;
		case 2:
			this.setSize(1.3F, 4.8F);
		break;
		case 1:
			this.setSize(0.9F, 3.2F);
		break;
		default:
			this.setSize(0.5F, 1.6F);
		break;
		}
	}

	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_10);
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
  		
  		if (!world.isRemote)
  		{
  			//add aura to master every N ticks
  			if (this.ticksExisted % 128 == 0 && !this.isMorph)
  			{
  				//add num of rensouhou
  				if (this.numRensouhou < 10) numRensouhou++;
  			}
  		}    
  	}
	
	//招喚連裝砲進行攻擊
  	@Override
  	public boolean attackEntityWithAmmo(Entity target)
  	{
  		//check num rensouhou
  		if (this.numRensouhou <= 0)
  		{
  			return false;
  		}
  		else
  		{
  			this.numRensouhou--;
  		}
  		
  		//play attack sound
		if (this.rand.nextInt(10) > 7)
		{
			this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
        }

        //發射者煙霧特效 (招喚連裝砲不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  		
		//spawn rensouhou
    	EntityRensouhouMob rensoho = new EntityRensouhouMob(this.world);
    	rensoho.initAttrs(this, target, this.scaleLevel);
        this.world.spawnEntity(rensoho);
        
        //show emotes
		applyEmotesReaction(3);
		
        return true;
	}
  	
  	//五連裝酸素魚雷
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
  	{	
		//get attack value
		float atkHeavy = this.getAttackBaseDamage(2, target) * 0.9F;
		float kbValue = 0.08F;
		
		//missile type
		float launchPos = (float) posY + height * 0.7F;
		int moveType = CombatHelper.calcMissileMoveType(this, target.posY, 2);
		if (moveType == 1) moveType = 0;
		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
        
		//play attack sound
        this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
        this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.8F);
        this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.9F);
        
        //entity sound
        if (this.getRNG().nextInt(10) > 7)
        {
        	this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
        }
        
	    float tarX = (float) target.posX;
	    float tarY = (float) target.posY;
	    float tarZ = (float) target.posZ;
	    
	    //if too close, extend target position
	    if (distVec.d < 6D)
	    {
	    	tarX += distVec.x * (6D - distVec.d);
	    	tarY += distVec.y * (6D - distVec.d);
	    	tarZ += distVec.z * (6D - distVec.d);
	    }
	    
	    //calc miss rate
        if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //發射者煙霧特效 (不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		
		float spread = 3F + this.scaleLevel * 0.5F;
		
        //spawn missile
		float[] data = new float[] {atkHeavy, kbValue, launchPos, tarX, tarY+target.height*0.1F, tarZ, 160, 0.25F, 0.5F, 1.04F, 1.04F};
		EntityAbyssMissile missile1 = new EntityAbyssMissile(this.world, this, 0, moveType, data);
		data = new float[] {atkHeavy, kbValue, launchPos, tarX+spread, tarY+target.height*0.1F, tarZ+spread, 160, 0.25F, 0.5F, 1.04F, 1.04F};
		EntityAbyssMissile missile2 = new EntityAbyssMissile(this.world, this, 0, moveType, data);
		data = new float[] {atkHeavy, kbValue, launchPos, tarX+spread, tarY+target.height*0.1F, tarZ-spread, 160, 0.25F, 0.5F, 1.04F, 1.04F};
		EntityAbyssMissile missile3 = new EntityAbyssMissile(this.world, this, 0, moveType, data);
		data = new float[] {atkHeavy, kbValue, launchPos, tarX-spread, tarY+target.height*0.1F, tarZ+spread, 160, 0.25F, 0.5F, 1.04F, 1.04F};
		EntityAbyssMissile missile4 = new EntityAbyssMissile(this.world, this, 0, moveType, data);
		data = new float[] {atkHeavy, kbValue, launchPos, tarX-spread, tarY+target.height*0.1F, tarZ-spread, 160, 0.25F, 0.5F, 1.04F, 1.04F};
		EntityAbyssMissile missile5 = new EntityAbyssMissile(this.world, this, 0, moveType, data);

        this.world.spawnEntity(missile1);
        this.world.spawnEntity(missile2);
        this.world.spawnEntity(missile3);
        this.world.spawnEntity(missile4);
        this.world.spawnEntity(missile5);
        
        //show emotes
		applyEmotesReaction(3);
		
        return true;
	}
  	
  	@Override
	public int getDamageType()
  	{
		return ID.ShipDmgType.DESTROYER;
	}
  	
  	
}