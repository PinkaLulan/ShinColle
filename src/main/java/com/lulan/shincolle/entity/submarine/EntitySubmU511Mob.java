package com.lulan.shincolle.entity.submarine;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

/**
 * model state:
 *   0:cannon, 1:hat, 2:tube
 */
public class EntitySubmU511Mob extends BasicEntityShipHostile implements IShipInvisible
{
	
	
	public EntitySubmU511Mob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.SSU511);
        
		//model display
        this.setStateEmotion(ID.S.State, rand.nextInt(8), false);
	}
	
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(1.5F, 5.6F);
		break;
		case 2:
			this.setSize(1.2F, 4.2F);
		break;
		case 1:
			this.setSize(0.9F, 2.8F);
		break;
		default:
			this.setSize(0.6F, 1.4F);
		break;
		}
	}

	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.NOTCHED_10);
	}

	//setup AI
	@Override
	protected void setAIList()
	{
		super.setAIList();

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
	}
	
	//set invisible
	@Override
  	public void onLivingUpdate()
	{
  		super.onLivingUpdate();
          
  		if (!this.world.isRemote)
  		{
  			//every 128 ticks
  			if ((this.ticksExisted & 127) == 0 && !this.isMorph)
  			{
  				if (this.rand.nextInt(2) == 0)
  				{
  					//self invisible
  					this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 40+this.getScaleLevel()*10, 0, false, false));
  				}
  			}//end 128 ticks
  		}    
  	}
	
	//招喚高速魚雷
  	@Override
  	public boolean attackEntityWithAmmo(Entity target)
  	{
		//get attack value
		float atk = this.getAttrs().getAttackDamage();
		float kbValue = 0.15F;
		
		//missile type
		float launchPos = (float) posY + height * 0.5F;
		int moveType = CombatHelper.calcMissileMoveType(this, target.posY, 1);
		if (moveType == 0) launchPos = (float) posY + height * 0.3F;
		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
        
        //play sound and particle
        applySoundAtAttacker(2, target);
	    applyParticleAtAttacker(2, target, distVec);
		
	    float tarX = (float) target.posX;
	    float tarY = (float) target.posY;
	    float tarZ = (float) target.posZ;
	    
	    //calc miss rate
        if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //spawn missile
        float[] data = new float[] {atk, kbValue, launchPos, tarX, tarY+target.height*0.1F, tarZ, 160, 0.25F, 0.8F, 1.1F, 1.1F};
		EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, 1, moveType, data);
        this.world.spawnEntity(missile);
        
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
        applyEmotesReaction(3);
        
        return true;
	}
  	
  	@Override
	public int getDamageType()
  	{
		return ID.ShipDmgType.SUBMARINE;
	}
  	
  	@Override
	public float getInvisibleLevel()
  	{
		return 0.3F;
	}
	
	@Override
	public void setInvisibleLevel(float level) {}
	
	@Override
	public double getEntityFloatingDepth()
	{
		return 1.1D + this.scaleLevel * 0.3D;
	}
  	

}