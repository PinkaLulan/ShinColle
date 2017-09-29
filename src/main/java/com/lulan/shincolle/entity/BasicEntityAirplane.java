package com.lulan.shincolle.entity;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.lulan.shincolle.ai.EntityAIShipAircraftAttack;
import com.lulan.shincolle.ai.EntityAIShipOpenDoor;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityAirplane;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

abstract public class BasicEntityAirplane extends BasicEntitySummon implements IShipFlyable
{
	
	protected boolean backHome, canFindTarget;
    protected TargetHelper.Sorter targetSorter = null;
    protected Predicate<Entity> targetSelector = null;
    protected Vec3d deadMotion = Vec3d.ZERO;
    
    
    public BasicEntityAirplane(World world)
    {
        super(world);
        this.backHome = false;
        this.canFindTarget = true;
		this.stepHeight = 7F;
    }

	//setup AI
  	protected void setAIList()
  	{
  		this.clearAITasks();
  		this.clearAITargetTasks();

  		//attack AI
  		this.tasks.addTask(1, new EntityAIShipAircraftAttack(this));
  		
  		//misc AI
  		this.tasks.addTask(11, new EntityAIShipOpenDoor(this, true));
  		
  		this.setEntityTarget(atkTarget);
  	}
  	
	//不會跟其他entity碰撞
  	@Override
  	protected void collideWithEntity(Entity target)
  	{
  		return;
    }

    //禁止任何掉落計算
    @Override
	public void fall(float distance, float damageMultiplier) {}
    
    @Override
    protected void updateFallState(double y, boolean onGround, IBlockState state, BlockPos pos) {}
    
    @Override
	public boolean isOnLadder()
    {
        return false;
    }
    
    //enable target finding AI
    @Override
    public boolean canFindTarget()
    {
    	return this.canFindTarget;
    }

	@Override
	public float getJumpSpeed()
	{
		return 2F;
	}

    //移動計算, 去除gravity部份
    @Override
	public void moveEntityWithHeading(float strafe, float forward)
    { 	
        this.moveRelative(strafe, forward, this.shipAttrs.getMoveSpeed() * 0.4F); //水中的速度計算(含漂移效果)
        this.move(this.motionX, this.motionY, this.motionZ);

        this.motionX *= 0.91D;
        this.motionY *= 0.91D;
        this.motionZ *= 0.91D;
        
        //撞到東西會上升
        if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D, this.motionZ))
        {
            this.motionY += 0.2D;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f4 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4F;

        if (f4 > 1F)
        {
            f4 = 1F;
        }

        this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

	@Override
	public void onUpdate()
	{
		//server side
		if (!this.world.isRemote)
		{
			//host check
			if (this.host == null || this.getPlayerUID() == 0 || this.getPlayerUID() == -1 || !((Entity)this.host).isEntityAlive())
			{
				//no host, or host has no owner
				this.setDead();
			}
			else
			{
				Entity hostent = (Entity) this.host;
				
				//歸宅
				if (this.backHome && this.isEntityAlive())
				{
					//no host, set dead
					if (this.host == null || !((Entity)this.host).isEntityAlive()) setDead();
					
					//get host, fly to host
					float dist = this.getDistanceToEntity(hostent);
					
					//get home path
					if (dist > 2F + hostent.height)
					{
						if (this.ticksExisted % 16 == 0)
						{
							this.getShipNavigate().tryMoveToXYZ(hostent.posX, hostent.posY + hostent.height + 1D, hostent.posZ, 1D);
						
							//host is too far away
							if (this.getShipNavigate().noPath() && this.getDistanceSqToEntity((Entity) this.host) >= 4095F)
							{
								this.returnSummonResource();
								this.setDead();
							}
						}
					}
					//get home
					else
					{	//歸還剩餘彈藥 (但是grudge不歸還)
						this.returnSummonResource();
						this.setDead();
					}
				}
				
				//前幾秒直線往目標移動
				if (this.ticksExisted < 34 && this.getEntityTarget() != null)
				{
					double distX = this.getEntityTarget().posX - this.posX;
					double distZ = this.getEntityTarget().posZ - this.posZ;
					double distSqrt = MathHelper.sqrt(distX*distX + distZ*distZ);
					
					this.motionX = distX / distSqrt * 0.375D;
					this.motionZ = distZ / distSqrt * 0.375D;
					this.motionY = 0.1D;
				}
				
				//check every 16 ticks
				if (this.ticksExisted % 16 == 0 && this.canFindTarget() && !this.backHome)
				{
					boolean findNewTarget = false;
					
					//check target alive
					if (this.ticksExisted < 1200)
					{
						if (this.getEntityTarget() == null || !this.getEntityTarget().isEntityAlive())
						{
							findNewTarget = true;
						}
					}
					
					//攻擊目標消失, 找附近目標 or 設為host目前目標
					if (this.ticksExisted >= 20 && findNewTarget)
					{
						Entity newTarget = null;
						List list = null;
						
						//if host Anti-Air, find airplane every 32 ticks
						if (this.host.getStateFlag(ID.F.AntiAir))
						{
							list = this.world.getEntitiesWithinAABB(BasicEntityAirplane.class, this.getEntityBoundingBox().expand(32D, 32D, 32D), this.targetSelector);
						}
						
						//find new target if "target dead" or "no air target"
						if (list == null || list.isEmpty())
						{
							list = this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().expand(24D, 24D, 24D), this.targetSelector);
						}
				        
				        //get target in list
						if (list != null && !list.isEmpty())
						{
							//從艦載機附近找出的目標, 判定是否要去攻擊
				        	Collections.sort(list, this.targetSorter);
				        	newTarget = (Entity) list.get(0);
						}
						
						//no target in list, get host's target
						if (list == null || list.isEmpty())
						{
				        	newTarget = this.host.getEntityTarget();
				        }

						//set attack target
				        if (newTarget != null)
				        {
				        	this.setEntityTarget(newTarget);
				        	this.backHome = false;
				        }
				        else
				        {
				        	this.setEntityTarget(null);
				        	this.backHome = true;
				        }
					}//end find new target
				}//end can find target
				
				//達到40秒時歸宅
				if (this.ticksExisted >= 1200)
				{
					this.setEntityTarget(null);
					this.backHome = true;
				}
			}	
		}
		
		if (this.ticksExisted % 2 == 0)
		{
			//面向計算 (for both side)
			float[] degree = CalcHelper.getLookDegree(posX - prevPosX, posY - prevPosY, posZ - prevPosZ, true);
			this.rotationYaw = degree[0];
			this.rotationPitch = degree[1];
		}

		super.onUpdate();
	}
	
	@Override
	public boolean isBurning()
	{
		//display fire effect
		if (this.deathTime > 30) return true;
		return false;
	}
	
	@Override
    protected void onDeathUpdate()
    {
        ++this.deathTime;

        //dead motion
        if (this.deathTime == 1)
        {
        	this.deadMotion = new Vec3d(this.motionX, this.motionY, this.motionZ);
        	this.motionX = this.deadMotion.xCoord;
        	this.motionZ = this.deadMotion.zCoord;
        }
        
        this.motionY -= 0.08D;
        
    	//spawn smoke
    	if (this.world.isRemote)
    	{
        	if ((this.ticksExisted & 1) == 0)
        	{
        		int maxpar = (int)((3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 1.8F);
        		double range = this.width * 0.5D;
        		for (int i = 0; i < maxpar; i++)
        		ParticleHelper.spawnAttackParticleAt(posX-range+this.rand.nextDouble()*range*2D, posY+this.height*0.3D+this.rand.nextDouble()*0.3D, posZ-range+this.rand.nextDouble()*range*2D, 1.5D, 0D, 0D, (byte)43);
        	}
        	
        	if (this.deathTime >= 89)
        	{
        		float ran1, ran2;
        		
        		for (int i = 0; i < 12; ++i)
     			{
     				ran1 = this.width * (this.rand.nextFloat() - 0.5F);
     				ran2 = this.width * (this.rand.nextFloat() - 0.5F);
     				this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX+ran1, this.posY+this.height*0.3D, this.posZ+ran2, 0D, 0D, 0D, new int[0]);
     			
     				if ((i & 3) == 0)
     				{
     					this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX+ran2, this.posY+this.height*0.5D, this.posZ+ran1, 0D, 0D, 0D, new int[0]);
     				}
     			}
        	}
    	}
        
        //dead particle
        if (this.deathTime == 90)
        {
        	//play sound
        	this.playSound(ModSounds.SHIP_EXPLODE, ConfigHandler.volumeFire, 0.7F / (this.rand.nextFloat() * 0.4F + 0.8F));
        	
            this.setDead();

            for (int k = 0; k < 20; ++k)
            {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1, new int[0]);
            }
        }
    }
	
	@Override
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  		case 3:  //light aircraft
  			return CombatHelper.modDamageByAdditionAttrs(this.host, target, this.shipAttrs.getAttackDamageAir(), 0);
  		case 2:  //heavy cannon
  		case 4:  //heavy aircraft
  			return this.shipAttrs.getAttackDamageAirHeavy();
		default: //melee
			return this.shipAttrs.getAttackDamageAir() * 0.125F;
  		}
  	}

	//light attack
	@Override
	public boolean attackEntityWithAmmo(Entity target)
	{
    	//host check
    	if (this.host == null)
    	{
    		this.setDead();
    		return false;
    	}
    	
		//light ammo -1
  		if (numAmmoLight > 0) numAmmoLight--;

  		//get attack value
  		float atk = getAttackBaseDamage(1, target);
  		
  		Entity host2 = (Entity) this.host;
		
        //play cannon fire sound at attacker
        applySoundAtAttacker(1, target);
	    applyParticleAtAttacker(1, target, Dist4d.ONE);
	    
        //calc dist to target
        Dist4d distVec = EntityHelper.getDistanceFromA2B(this, target);
	    
	    //roll miss, cri, dhit, thit
	    atk = CombatHelper.applyCombatRateToDamage(this, target, true, (float)distVec.distance, atk);
  		
  		//damage limit on player target
	    atk = CombatHelper.applyDamageReduceOnPlayer(target, atk);
  		
  		//check friendly fire
		if (!TeamHelper.doFriendlyFire(this, target)) atk = 0F;
		
	    //並且回傳是否成功傷害到目標
  		boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);
	    
	    //if attack success
	    if (isTargetHurt)
	    {
	    	applySoundAtTarget(1, target);
	        applyParticleAtTarget(0, target, Dist4d.ONE);
	        
	        if (ConfigHandler.canFlare && this.host instanceof BasicEntityShip) ((BasicEntityShip)this.host).flareTarget(target);
        }

	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		//ammo--
  		if (numAmmoHeavy > 0) numAmmoHeavy--;
        
		//get attack value
		float atk = getAttackBaseDamage(2, target);
		float kbValue = 0.15F;
		
        //calc dist to target
        Dist4d distVec = EntityHelper.getDistanceFromA2B(this, target);

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
        		tarX, tarY+target.height*0.2F, tarZ, (float)(this.posY-0.8F), atk, kbValue, true, -1F);
        this.world.spawnEntity(missile);
        
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
        
        if (ConfigHandler.canFlare && this.host instanceof BasicEntityShip) ((BasicEntityShip)this.host).flareTarget(target);
        
        return true;
	}

	@Override
	public boolean canFly()
	{
		return true;
	}

	@Override
	public boolean getStateFlag(int flag)
	{	//for attack AI check
		switch (flag)
		{
		case ID.F.OnSightChase:
			return false;
		default:
			return true;
		}
	}

	@Override
	public void setStateFlag(int id, boolean flag) {}

	@Override
	public int getPlayerUID()
	{
		if (this.host != null) return this.host.getPlayerUID();
		return -1;
	}

	@Override
	public void setPlayerUID(int uid) {}

	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.AIRPLANE;
	}

	@Override
	public boolean canBePushed()
	{
        return false;
    }
	
	@Override
	protected void returnSummonResource()
	{
    	//only for friendly ship
    	if (this.host instanceof BasicEntityShipCV)
    	{
    		BasicEntityShipCV ship = (BasicEntityShipCV) this.host;
    		
    		//light cost 6, plane get 9 => -3
    		this.numAmmoLight -= 3;
    		if (this.numAmmoLight < 0) this.numAmmoLight = 0;
    		
    		//heavy cost 2, plane get 3 => -1
    		this.numAmmoHeavy -= 1;
    		if (this.numAmmoHeavy < 0) this.numAmmoHeavy = 0;
    		
    		//#ammo++
    		ship.setStateMinor(ID.M.NumAmmoLight, ship.getStateMinor(ID.M.NumAmmoLight) + this.numAmmoLight * ship.getAmmoConsumption());
    		ship.setStateMinor(ID.M.NumAmmoHeavy, ship.getStateMinor(ID.M.NumAmmoHeavy) + this.numAmmoHeavy * ship.getAmmoConsumption());
    	
    		//#plane++
    		if (this instanceof EntityAirplane)
    		{
    			ship.setNumAircraftLight(ship.getNumAircraftLight() + 1);
    		}
    		else
    		{
    			ship.setNumAircraftHeavy(ship.getNumAircraftHeavy() + 1);
    		}
    	}
	}
	
	@Override
  	public void applySoundAtAttacker(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			this.playSound(ModSounds.SHIP_MACHINEGUN, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  		break;
  		case 2:  //heavy cannon
  			this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  		break;
  		}//end switch
  	}
  	
	@Override
  	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 8, false), point);
  		break;
  		}
  	}
	
	@Override
	protected void setSizeWithScaleLevel() {}

	@Override
	protected void setAttrsWithScaleLevel() {}
	
  	protected int getLifeLength()
  	{
  		return 1800;
  	}
	
	
}