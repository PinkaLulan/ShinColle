package com.lulan.shincolle.entity.submarine;

import javax.annotation.Nullable;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

/**
 * model state:
 *   0:equip1, 1:equip2, 2:flower
 */
public class EntitySubmRo500Mob extends BasicEntityShipHostile implements IShipInvisible
{

	
	public EntitySubmRo500Mob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.SubmarineRo500);
        
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
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_10);
	}

	//平常音效新增: garuru
    @Override
    @Nullable
    protected SoundEvent getAmbientSound()
    {
    	if (rand.nextInt(8) == 0)
    	{
			return ModSounds.SHIP_GARURU;
		}
		else
		{
			return super.getAmbientSound();
		}
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
  			//add aura to master every N ticks
  			if (this.ticksExisted % 256 == 0)
  			{
  				if (this.rand.nextInt(2) == 0)
  				{
  					this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 140));
  				}
  			}
  		}    
  	}
	
	//招喚高速魚雷
  	@Override
  	public boolean attackEntityWithAmmo(Entity target)
  	{
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
        	launchPos = (float) posY + height * 0.2F;
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
        		tarX, tarY, tarZ, launchPos, atk, kbValue, isDirect, 0.08F);
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
		return 0.35F;
	}
	
	@Override
	public void setInvisibleLevel(float level) {}
	
	@Override
	public double getShipFloatingDepth()
	{
		return 1.2D + this.scaleLevel * 0.3D;
	}
  	
	
}