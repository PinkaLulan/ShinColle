package com.lulan.shincolle.entity.submarine;

import javax.annotation.Nullable;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntitySubmRo500Mob extends BasicEntityShipHostile implements IShipInvisible
{

	private static float ilevel = 35F;
	
	
	public EntitySubmRo500Mob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.Ship.SubmarineRo500);
        
		//model display
        this.setStateEmotion(ID.S.State, rand.nextInt(4), false);
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
		float atk = this.getAttackDamage();
		float kbValue = 0.15F;
		
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
        
        //play sound and particle
        applySoundAtAttacker(2, target);
	    applyParticleAtAttacker(2, target, distVec);
		
        //calc miss chance, miss: add random offset(0~6) to missile target 
        if (this.rand.nextFloat() < this.getEffectEquip(ID.EquipEffect.MISS))
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	applyParticleSpecialEffect(0);  //miss particle
        }
        
        //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atk, kbValue, isDirect, 0.08F);
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
		return this.ilevel;
	}
	
	@Override
	public void setInvisibleLevel(float level)
	{
		this.ilevel = level;
	}
  	

}