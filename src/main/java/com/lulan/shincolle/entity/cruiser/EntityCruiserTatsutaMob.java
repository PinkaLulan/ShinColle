package com.lulan.shincolle.entity.cruiser;

import java.util.ArrayList;

import com.google.common.base.Predicate;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.EntityAIShipSkillAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityCruiserTatsutaMob extends BasicEntityShipHostile
{
	
	private Predicate targetSelector;
	private int remainAttack;
	private Vec3d skillMotion; 
	private ArrayList<Entity> damagedTarget;
	
	
	public EntityCruiserTatsutaMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.Ship.LightCruiserTatsuta);
		this.targetSelector = new TargetHelper.SelectorForHostile(this);
		this.remainAttack = 0;
		this.skillMotion = Vec3d.ZERO;
		this.damagedTarget = new ArrayList<Entity>();
		
		//model display
		this.setStateEmotion(ID.S.State, this.rand.nextInt(4), false);
		this.setStateEmotion(ID.S.State2, this.rand.nextInt(6), false);
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
			this.setSize(0.75F, 1.65F);
		break;
		}
	}

	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_10);
	}
	
	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.CRUISER;
	}

	@Override
    public boolean canBePushed()
    {
		if (this.getStateEmotion(ID.S.Phase) > 0) return false;
        return super.canBePushed();
    }
	
	@Override
	public boolean canFly()
	{
		if (this.getStateEmotion(ID.S.Phase) > 0) return false;
		return super.canFly();
	}

	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//skill attack
		this.tasks.addTask(0, new EntityAIShipSkillAttack(this));
		
		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
	
	@Override
	public void onLivingUpdate()
	{
		//client side
		if (this.world.isRemote)
		{
		}
		//server side
		else
		{
			//skill tick--
			if (this.attackTime3 > 0) this.attackTime3--;
			
			//apply skill effect
			this.updateSkillEffect();
		}
		
		super.onLivingUpdate();
	}
	
	private void updateSkillEffect()
	{
		if (this.stateEmotion[ID.S.Phase] == 1)
		{
			//apply motion
			this.motionX = this.skillMotion.xCoord;
			this.motionY = this.skillMotion.yCoord;
			this.motionZ = this.skillMotion.zCoord;
			
			//sync motion
			this.sendSyncPacket(1);
		}
		else if (this.stateEmotion[ID.S.Phase] == 2)
		{
			//apply motion
			this.motionX = this.skillMotion.xCoord;
			this.motionY = this.skillMotion.yCoord;
			this.motionZ = this.skillMotion.zCoord;
			
			//attack on colliding
			this.damageNearbyEntity();

			//sync motion
			this.sendSyncPacket(1);
		}
	}
	
	private void damageNearbyEntity()
	{
		float rawatk = this.getAttackBaseDamage(2, null);
		
		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		ArrayList<Entity> list = EntityHelper.getEntitiesWithinAABB(this.world, Entity.class,
				this.getEntityBoundingBox().expand(4D, 3D, 4D), this.targetSelector);

		for (Entity target : list)
		{
			boolean attacked = false;
			
			//check target was not attacked before
			for (Entity ent : this.damagedTarget)
			{
				if (ent.equals(target))
				{
					attacked = true;
					break;
				}
			}
			
			if (attacked)
			{
				continue;							//attacked, skip to next
			}
			else
			{
				this.damagedTarget.add(target);		//not attacked, add to attacked list
			}
			
			float atk = CalcHelper.calcDamageBySpecialEffect(this, target, rawatk, 0);
			
        	//目標不能是自己 or 主人, 且可以被碰撞
        	if (target.canBeCollidedWith() && EntityHelper.isNotHost(this, target))
        	{
        		//若owner相同, 則傷害設為0 (但是依然觸發擊飛特效)
        		if (TeamHelper.checkSameOwner(this, target))
        		{
        			atk = 0F;
            	}
        		else
        		{
        	        //calc miss -> crit -> double -> tripple
        	  		if (rand.nextFloat() < this.getEffectEquip(ID.EquipEffect.MISS))
        	  		{
        	          	atk = 0F;	//still attack, but no damage
        	          	applyParticleSpecialEffect(0);
        	  		}
        	  		else
        	  		{
        	  			//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	  			//calc critical
        	          	if (rand.nextFloat() < this.getEffectEquip(ID.EquipEffect.CRI))
        	          	{
        	          		atk *= 1.5F;
        	          		applyParticleSpecialEffect(1);
        	          	}
        	          	else
        	          	{
        	          		//calc double hit
        	              	if (rand.nextFloat() < this.getEffectEquip(ID.EquipEffect.DHIT))
        	              	{
        	              		atk *= 2F;
        	              		applyParticleSpecialEffect(2);
        	              	}
        	              	else
        	              	{
        	              		//calc triple hit
        	                  	if (rand.nextFloat() < this.getEffectEquip(ID.EquipEffect.THIT))
        	                  	{
        	                  		atk *= 3F;
        	                  		applyParticleSpecialEffect(3);
        	                  	}
        	              	}
        	          	}
        	  		}
        	  		
        	  		//calc damage to player
        	  		if (target instanceof EntityPlayer)
        	  		{
        	  			atk *= 0.25F;
        	  			if (atk > 59F) atk = 59F;	//same with TNT
        	  		}
        	  		
        	  		//check friendly fire
        			if (!TeamHelper.doFriendlyFire(this, target)) atk = 0F;
        			
        	  		//確認攻擊是否成功
        		    if (target.attackEntityFrom(DamageSource.causeMobDamage(this), atk))
        		    {
        		    	applyParticleAtTarget(1, target, new float[0]);
        		    	if (this.rand.nextInt(2) == 0) this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, ConfigHandler.volumeFire, this.getSoundPitch());
        		    	
        		        //push target
        		        if (target.canBePushed())
        		        {
        		        	if (target instanceof IShipAttackBase)
        		        	{
                    			target.addVelocity(-MathHelper.sin(rotationYaw * Values.N.DIV_PI_180) * 0.02F, 
                   	                   0.2D, MathHelper.cos(rotationYaw * Values.N.DIV_PI_180) * 0.02F);
        		        	}
        		        	else
        		        	{
                    			target.addVelocity(-MathHelper.sin(rotationYaw * Values.N.DIV_PI_180) * 0.05F, 
                   	                   0.4D, MathHelper.cos(rotationYaw * Values.N.DIV_PI_180) * 0.05F);
        		        	}
                 			
                 			//for other player, send ship state for display
        		        	this.sendSyncPacket(1);
        		        }
        	        }
        		}//end not same owner
        	}//end can collide
		}//end for all target
	}
	
	//range attack method, cost heavy ammo, attack delay = 100 / attack speed, damage = 500% atk
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		if (this.stateEmotion[ID.S.Phase] == 0)
		{
			//play sound at attacker
			this.playSound(ModSounds.SHIP_AP_P1, ConfigHandler.volumeFire, 1F);
			
  			if (this.rand.nextInt(10) > 7)
  			{
  				this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
  	        }
  			
  			applyParticleAtAttacker(2, target, new float[0]);
  			
  			//charging
  			this.stateEmotion[ID.S.Phase] = -1;
		}
		else if (this.stateEmotion[ID.S.Phase] == -1)
		{
  			//start skill attack
  			this.setStateEmotion(ID.S.Phase, 1, true);
			this.remainAttack = 1 + this.scaleLevel;
  			this.attackTime3 = 7;
		}
		
        applyEmotesReaction(3);
        
        return true;
	}
	
	private Entity checkSkillTarget(Entity target)
	{
		//target null
		if (target == null)
		{
			return null;
		}
		//target exist
		else
		{
			//if target dead or too far away, find new target
			if (!target.isEntityAlive() || target.getDistanceSqToEntity(this) > (this.getAttackRange() * this.getAttackRange()))
			{
				if (this.remainAttack > 0)
				{
					ArrayList<Entity> list = EntityHelper.getEntitiesWithinAABB(this.world, Entity.class,
							this.getEntityBoundingBox().expand(13D, 13D, 13D), this.targetSelector);
			
					if (list.size() > 0)
					{
						target = list.get(this.rand.nextInt(list.size()));
						this.setEntityTarget(target);
						return target;
					}
				}
				
				return null;
			}
		}
		
		return target;
	}

	private void updateSkillCharge(Entity target)
	{
		if (this.attackTime3 == 5)
		{
			Vec3d vecpos = new Vec3d((target.posX - this.posX) * 0.19D, (target.posY - this.posY) * 0.19D, (target.posZ - this.posZ) * 0.19D);
			vecpos.normalize();
			
			//calc motion
			this.skillMotion = vecpos;
			
			//calc rotation
			float[] degree = CalcHelper.getLookDegree(this.skillMotion.xCoord, this.skillMotion.yCoord, this.skillMotion.zCoord, true);
			this.rotationYaw = degree[0];
			this.rotationYawHead = degree[0];
			
			//update flag and sync
			this.sendSyncPacket(1);
			this.sendSyncPacket(3);
			
			//set attack time
			this.applyParticleAtAttacker(5, null, new float[0]);
		}
		else if (this.attackTime3 == 3)
		{
			//apply particle
			this.applyParticleAtTarget(5, target, new float[] {(float)this.skillMotion.xCoord, (float)this.skillMotion.yCoord, (float)this.skillMotion.zCoord});
		}
	}

	private void updateSkillWWAttack(Entity target)
	{
		if (this.attackTime3 <= 0)
		{
			//calc motion
			this.skillMotion = new Vec3d(0D, 0.6D, 0D);
			this.attackTime3 = 16;
			
			//set attack time
			this.applyParticleAtAttacker(5, null, new float[0]);
		}
		
		//every 2 ticks
		if ((this.attackTime3 & 1) == 0)
		{
			//apply particle
			this.applyParticleAtAttacker(6, null, new float[0]);
			
			//attack-- every 4 ticks
			if ((this.attackTime3 & 3) == 0)
			{
				this.remainAttack--;
				this.damagedTarget.clear();
				
				if (this.remainAttack == 1)
				{
					this.attackTime3 = 0;	//no remain attack, go to next phase
				}
				
				//apply sound
				this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, ConfigHandler.volumeFire, this.getSoundPitch() * 1.1F);
				this.playSound(ModSounds.SHIP_JET, ConfigHandler.volumeFire, this.getSoundPitch());
			}
		}
	}
	
	private void updateSkillFinalAttack(Entity target)
	{
		if (this.attackTime3 <= 0)
		{
			Vec3d vecpos = new Vec3d(target.posX - this.posX, target.posY - this.posY, target.posZ - this.posZ);
			
			//calc rotation
			float[] degree = CalcHelper.getLookDegree(vecpos.xCoord, vecpos.yCoord, vecpos.zCoord, true);
			this.rotationYaw = degree[0];
			this.rotationYawHead = degree[0];
			
			//find gae bolg target
			vecpos = new Vec3d(vecpos.xCoord * 10D, -20D - this.scaleLevel * 8D, vecpos.zCoord * 10D);
			vecpos = vecpos.normalize();
			this.skillMotion = vecpos;
			
			//update flag and sync
			this.remainAttack = 0;
			this.attackTime3 = 10;
			this.sendSyncPacket(3);
		}
		else if (this.attackTime3 == 6)
		{
			//shot gae bolg
			EntityProjectileBeam gaebolg = new EntityProjectileBeam(this.world);
			gaebolg.initAttrs(this, 1, (float)this.skillMotion.xCoord, (float)this.skillMotion.yCoord, (float)this.skillMotion.zCoord, this.getAttackBaseDamage(3, target), 0.15F);
			this.world.spawnEntity(gaebolg);
		}
		else if (this.attackTime3 == 4)
		{
			//apply sound and particle
			this.playSound(ModSounds.SHIP_AP_ATTACK, ConfigHandler.volumeFire * 1.1F, this.getSoundPitch() * 0.6F);
			this.applyParticleAtTarget(6, target, new float[] {(float)this.skillMotion.xCoord, (float)this.skillMotion.yCoord, (float)this.skillMotion.zCoord});
		}
	}
	
	/**
	 * Skill Phase:
	 * 
	 * -1: skill ready to enter phase 1
	 * 0: none
	 * 1: charge to target
	 * 2: horizontal attack
	 * 3: final attack
	 * 
	 * Process:
	 * 
	 * 0 -> -1 -> 1 -> 2 -> 3 -> 0
	 */
	@Override
	public boolean updateSkillAttack(Entity target)
	{
		//check target
		target = this.checkSkillTarget(target);
		
		//no target, reset phase
		if (target == null)
		{
			this.setStateEmotion(ID.S.Phase, 0, true);
			this.remainAttack = 0;
			this.skillMotion = Vec3d.ZERO;
			this.attackTime3 = 0;
			return false;
		}
		
		//state changing
		if (this.attackTime3 <= 0)
		{
			if (this.stateEmotion[ID.S.Phase] == 3)
			{
				this.setStateEmotion(ID.S.Phase, 0, true);
				this.remainAttack = 0;
				this.skillMotion = Vec3d.ZERO;
				this.attackTime3 = 0;
				return false;
			}
			else
			{
				this.setStateEmotion(ID.S.Phase, this.getStateEmotion(ID.S.Phase) + 1, true);
			}
		}
		
		//update state
		switch (this.stateEmotion[ID.S.Phase])
		{
		case 1:		//charge to target
			this.updateSkillCharge(target);
		break;
		case 2:		//WW attack
			this.updateSkillWWAttack(target);
		break;
		case 3:		//final attack
			this.updateSkillFinalAttack(target);
		break;
		}
		
		return false;
	}
	
	
	@Override
  	public void applyParticleAtAttacker(int type, Entity target, float[] vec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 16, 1D, 0.85D, 1D), point);
  		break;
  		case 2:  //heavy cannon
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 11, 1D, 0.8D, 1D), point);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 12, 1D, 0.8D, 1D), point);
		break;
  		case 5:  //for attack time setting
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
  		case 6:  //WW wave
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 14, 1D, 0.7D, 1D), point);
		break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 16, 1D, 0.95D, 1D), point);
		break;
  		}
  	}
	
	@Override
  	public void applySoundAtAttacker(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, ConfigHandler.volumeFire * 1.2F, this.getSoundPitch() * 0.85F);
  	        
  			//entity sound
  			if (this.rand.nextInt(10) > 7)
  			{
  				this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
		default: //melee
			if (this.getRNG().nextInt(2) == 0)
			{
				this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
	        }
		break;
  		}//end switch
  	}
	
	@Override
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light attack
  			return CalcHelper.calcDamageBySpecialEffect(this, target, this.atk, 0);
  		case 2:  //heavy attack: horizontal
  			return this.atk * 3F * 0.5F;
  		case 3:  //heavy attack: final
  			return this.atk * 3F * 1.5F;
		default: //melee
			return this.atk;
  		}
  	}
	
	@Override
  	public void applyParticleAtTarget(int type, Entity target, float[] vec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  		
  		switch (type)
  		{
  		case 1:  //light cannon
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point);
  		break;
  		case 2:  //heavy cannon
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  		break;
  		case 5:  //high speed blur
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 46, posX + vec[0] * 2D, posY + vec[1] * 2D + this.height * 0.7D, posZ + vec[2] * 2D, vec[0] * 0.6D, vec[1] * 0.6D, vec[2] * 0.6D, false), point);
		break;
  		case 6:  //gae bolg blur
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 45, posX + vec[0] * 10D, posY + vec[1] * 10D + this.height * 0.7D, posZ + vec[2] * 10D, vec[0] * 1.5D, vec[1] * 1.5D, vec[2] * 1.5D, true), point);
		break;
		default: //melee
    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
		break;
  		}
  	}
	
	@Override
  	public void applySoundAtTarget(int type, Entity target)
  	{
  		switch (type)
  		{
  		
  		case 2:  //heavy cannon
  			this.playSound(ModSounds.SHIP_EXPLODE, ConfigHandler.volumeFire, this.getSoundPitch());
  		break;
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  		break;
  		case 1:  //light cannon
		default: //melee
			if (target instanceof IShipEmotion)
			{
				this.playSound(ModSounds.SHIP_HITMETAL, ConfigHandler.volumeFire, this.getSoundPitch());
			}
			else
			{
				this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, ConfigHandler.volumeFire, this.getSoundPitch());
			}
		break;
  		}
  	}


}
