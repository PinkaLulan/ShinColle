package com.lulan.shincolle.entity.cruiser;

import java.util.ArrayList;

import com.google.common.base.Predicate;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.EntityAIShipSkillAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 *   0:cannon, 1:head, 2:weapon
 */
public class EntityCLTatsuta extends BasicEntityShipSmall
{
	
	private Predicate targetSelector;
	private int remainAttack;
	private Vec3d skillMotion; 
	private ArrayList<Entity> damagedTarget;
	
	
	public EntityCLTatsuta(World world)
	{
		super(world);
		this.setSize(0.75F, 1.65F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.LIGHT_CRUISER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.LightCruiserTatsuta);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CRUISER);
		this.setStateMinor(ID.M.NumState, 3);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CL]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CL]);
		this.ModelPos = new float[] {0F, 22F, 0F, 42F};
		this.targetSelector = new TargetHelper.Selector(this);
		this.remainAttack = 0;
		this.skillMotion = Vec3d.ZERO;
		this.damagedTarget = new ArrayList<Entity>();
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(12);
		
		this.postInit();
	}
	
	@Override
	public int getEquipType()
	{
		return 1;
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
	
	//晚上時額外增加屬性
	@Override
	public void calcShipAttributesAddRaw()
	{
		super.calcShipAttributesAddRaw();
		
		if (!this.world.isDaytime())
		{
			this.getAttrs().setAttrsRaw(ID.Attrs.CRI, this.getAttrs().getAttrsRaw(ID.Attrs.CRI) + 0.15F);
			this.getAttrs().setAttrsRaw(ID.Attrs.DODGE, this.getAttrs().getAttrsRaw(ID.Attrs.DODGE) + 0.15F);
		}
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
			//apply skill effect
			this.updateSkillEffect();
		}
		
		super.onLivingUpdate();
	}
	
	private void updateSkillEffect()
	{
		if (this.StateEmotion[ID.S.Phase] == 1)
		{
			//apply motion
			this.motionX = this.skillMotion.xCoord;
			this.motionY = this.skillMotion.yCoord;
			this.motionZ = this.skillMotion.zCoord;
			
			//sync motion
			this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Motion, true);
		}
		else if (this.StateEmotion[ID.S.Phase] == 2)
		{
			//apply motion
			this.motionX = this.skillMotion.xCoord;
			this.motionY = this.skillMotion.yCoord;
			this.motionZ = this.skillMotion.zCoord;
			
			//attack on colliding
			this.damageNearbyEntity();

			//sync motion
			this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Motion, true);
		}
		else if (this.StateEmotion[ID.S.Phase] == 3)
		{
			//apply motion
			this.motionX = 0D;
			this.motionY = 0.1D;
			this.motionZ = 0D;
			
			//sync motion
			this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Motion, true);
		}
	}
	
	private void damageNearbyEntity()
	{
		float rawatk = this.getAttackBaseDamage(2, null);
		
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
			
			float atk = CombatHelper.modDamageByAdditionAttrs(this, target, rawatk, 0);
			
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
        		    //roll miss, cri, dhit, thit
        		    atk = CombatHelper.applyCombatRateToDamage(this, target, true, 1F, atk);
        	  		
        	  		//damage limit on player target
        		    atk = CombatHelper.applyDamageReduceOnPlayer(target, atk);
        	  		
        	  		//check friendly fire
        			if (!TeamHelper.doFriendlyFire(this, target)) atk = 0F;
        			
        	  		//確認攻擊是否成功
        		    if (target.attackEntityFrom(DamageSource.causeMobDamage(this), atk))
        		    {
        		    	applyParticleAtTarget(1, target, Dist4d.ONE);
        		    	
        		    	if (this.rand.nextInt(2) == 0) this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, ConfigHandler.volumeFire, this.getSoundPitch());
        		    	
        		        if (ConfigHandler.canFlare) flareTarget(target);
        		        
        		        //push target
        		        if (target.canBePushed())
        		        {
        		        	if (target instanceof IShipAttackBase)
        		        	{
                    			target.addVelocity(0F, 0.25D, 0F);
        		        	}
        		        	else
        		        	{
        		        		target.addVelocity(0F, 0.5D, 0F);
        		        	}
                 			
                 			//for other player, send ship state for display
        		        	this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Motion, true);
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
		//ammo--
        if (!decrAmmoNum(1, this.getAmmoConsumption())) return false;
        
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
  		
		if (this.StateEmotion[ID.S.Phase] == 0)
		{
			//play sound at attacker
			this.playSound(ModSounds.SHIP_AP_P1, ConfigHandler.volumeFire, 1F);
			
  			if (this.rand.nextInt(10) > 7)
  			{
  				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  			
  			applyParticleAtAttacker(2, target, Dist4d.ONE);
  			
  			//charging
  			this.StateEmotion[ID.S.Phase] = -1;
		}
		else if (this.StateEmotion[ID.S.Phase] == -1)
		{
  			//start skill attack
			this.setStateEmotion(ID.S.Phase, 1, true);
  			this.StateTimer[ID.T.AttackTime3] = 10;
  			this.remainAttack = 2 + (int)(this.getLevel() * 0.015F);
		}
		
        applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
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
			if (!target.isEntityAlive() || target.getDistanceSqToEntity(this) > (this.getAttrs().getAttackRange() * this.getAttrs().getAttackRange()))
			{
				if (this.remainAttack > 0)
				{
					ArrayList<Entity> list = EntityHelper.getEntitiesWithinAABB(this.world, Entity.class,
							this.getEntityBoundingBox().expand(16D, 16D, 16D), this.targetSelector);
			
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
		if (this.StateTimer[ID.T.AttackTime3] == 8)
		{
			Vec3d vecpos = new Vec3d(target.posX - this.posX, target.posY - this.posY, target.posZ - this.posZ);
			
			//calc motion
			this.skillMotion = vecpos.scale(0.14D);
			vecpos.normalize();
			
			//calc rotation
			float[] degree = CalcHelper.getLookDegree(vecpos.xCoord, vecpos.yCoord, vecpos.zCoord, true);
			this.rotationYaw = degree[0];
			this.rotationYawHead = degree[0];
			
			//update flag and sync
			this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Motion, true);
			this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Rot, true);
			
			//set attack time
			this.applyParticleAtAttacker(5, null, Dist4d.ONE);
		}
		else if (this.StateTimer[ID.T.AttackTime3] == 6)
		{
			//apply particle
			this.applyParticleAtTarget(5, target, new Dist4d(this.skillMotion.xCoord, this.skillMotion.yCoord, this.skillMotion.zCoord, 1D));
		}
	}

	private void updateSkillWWAttack(Entity target)
	{
		if (this.StateTimer[ID.T.AttackTime3] <= 0)
		{
			//calc motion
			this.skillMotion = new Vec3d(0D, 0.3D, 0D);
			this.StateTimer[ID.T.AttackTime3] = 25;
			
			//set attack time
			this.applyParticleAtAttacker(5, null, Dist4d.ONE);
		}
		
		//every 2 ticks
		if ((this.StateTimer[ID.T.AttackTime3] & 1) == 0)
		{
			//apply particle
			this.applyParticleAtAttacker(6, null, Dist4d.ONE);
			
			//attack-- every 8 ticks
			if ((this.StateTimer[ID.T.AttackTime3] & 7) == 0)
			{
				this.remainAttack--;
				this.damagedTarget.clear();
				
				if (this.remainAttack <= 1)
				{
					this.StateTimer[ID.T.AttackTime3] = 0;	//no remain attack, go to next phase
				}
				
				//apply sound
				this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, ConfigHandler.volumeFire, this.getSoundPitch() * 1.1F);
				this.playSound(ModSounds.SHIP_JET, ConfigHandler.volumeFire, this.getSoundPitch());
			}
		}
	}
	
	private void updateSkillFinalAttack(Entity target)
	{
		if (this.StateTimer[ID.T.AttackTime3] <= 0)
		{
			Vec3d vecpos = new Vec3d(target.posX - this.posX, target.posY - this.posY - 1D, target.posZ - this.posZ);
			
			//calc rotation
			float[] degree = CalcHelper.getLookDegree(vecpos.xCoord, vecpos.yCoord, vecpos.zCoord, true);
			this.rotationYaw = degree[0];
			this.rotationYawHead = degree[0];
			
			//calc gae bolg direction
			this.skillMotion = vecpos.normalize();
			
			//update flag and sync
			this.remainAttack = 0;
			this.StateTimer[ID.T.AttackTime3] = 15;
			this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_Rot, true);
			//set attack time
			this.applyParticleAtAttacker(5, null, Dist4d.ONE);
		}
		else if (this.StateTimer[ID.T.AttackTime3] == 6)
		{
			//shot gae bolg
			EntityProjectileBeam gaebolg = new EntityProjectileBeam(this.world);
			gaebolg.initAttrs(this, 1, (float)this.skillMotion.xCoord, (float)this.skillMotion.yCoord, (float)this.skillMotion.zCoord, this.getAttackBaseDamage(3, target), 0.15F);
			this.world.spawnEntity(gaebolg);
		}
		else if (this.StateTimer[ID.T.AttackTime3] == 4)
		{
			//apply sound and particle
			this.playSound(ModSounds.SHIP_AP_ATTACK, ConfigHandler.volumeFire * 1.1F, this.getSoundPitch() * 0.6F);
			this.applyParticleAtTarget(6, target, new Dist4d(this.skillMotion.xCoord, this.skillMotion.yCoord, this.skillMotion.zCoord, 1D));
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
			this.StateTimer[ID.T.AttackTime3] = 0;
			return false;
		}
		
		//state changing
		if (this.StateTimer[ID.T.AttackTime3] <= 0)
		{
			if (this.StateEmotion[ID.S.Phase] == 3)
			{
				this.setStateEmotion(ID.S.Phase, 0, true);
				this.remainAttack = 0;
				this.skillMotion = Vec3d.ZERO;
				this.StateTimer[ID.T.AttackTime3] = 0;
				return false;
			}
			else
			{
				this.setStateEmotion(ID.S.Phase, this.getStateEmotion(ID.S.Phase) + 1, true);
			}
		}
		
		//update state
		switch (this.StateEmotion[ID.S.Phase])
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
		
		//skill tick--
		if (this.StateTimer[ID.T.AttackTime3] > 0) this.StateTimer[ID.T.AttackTime3]--;
		
		return false;
	}
	
    @Override
	public double getMountedYOffset()
    {
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.2F;
  			}
  			else
  			{
  				return this.height * 0.27F;
  			}
  		}
  		else
  		{
  			return this.height * 0.7F;
  		}
	}
	
	@Override
  	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
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
  				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
		default: //melee
			if (this.getRNG().nextInt(2) == 0)
			{
				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
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
  			return CombatHelper.modDamageByAdditionAttrs(this, target, this.shipAttrs.getAttackDamage(), 0);
  	  	case 2:  //heavy attack: horizontal
  			return this.shipAttrs.getAttackDamageHeavy() * 0.5F;
  		case 3:  //heavy attack: final
  			return this.shipAttrs.getAttackDamageHeavy() * 1.5F;
		default: //melee
			return this.shipAttrs.getAttackDamage();
  		}
  	}
	
	@Override
  	public void applyParticleAtTarget(int type, Entity target, Dist4d distVec)
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
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 46, posX + distVec.x * 2D, posY + distVec.y * 2D + this.height * 0.7D, posZ + distVec.z * 2D, distVec.x * 1.5D, distVec.y * 1.5D, distVec.z * 1.5D, false), point);
		break;
  		case 6:  //gae bolg blur
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 45, posX + distVec.x * 10D, posY + distVec.y * 10D + this.height * 0.7D, posZ + distVec.z * 10D, distVec.x * 1.5D, distVec.y * 1.5D, distVec.z * 1.5D, true), point);
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