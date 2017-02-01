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
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

abstract public class BasicEntityAirplane extends BasicEntitySummon implements IShipFlyable
{
	
	//AI flag
	protected boolean backHome, canFindTarget;

    //target selector
    protected TargetHelper.Sorter targetSorter = null;
    protected Predicate<Entity> targetSelector = null;
	
    
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
        this.moveRelative(strafe, forward, this.movSpeed * 0.4F); //水中的速度計算(含漂移效果)
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
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		return CalcHelper.calcDamageBySpecialEffect(this.host, target, this.atk, 0);
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
  		float atkLight = getAttackBaseDamage(1, target);
  		
  		Entity host2 = (Entity) this.host;
		
        //play cannon fire sound at attacker
        applySoundAtAttacker(1, target);
	    applyParticleAtAttacker(1, target, new float[4]);
	    
		//calc miss chance, if not miss, calc cri/multi hit
		float missChance = 0.25F - 0.001F * this.host.getStateMinor(ID.M.ShipLevel);
        missChance -= this.host.getEffectEquip(ID.EquipEffect.MISS);	//equip miss reduce
        if (missChance > 0.35F) missChance = 0.35F;
  		
        //calc miss chance
        if (this.rand.nextFloat() < missChance)
        {
        	atkLight = 0;	//still attack, but no damage
        	applyParticleSpecialEffect(0);        	
        }
        else
        {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if (this.rand.nextFloat() < this.host.getEffectEquip(ID.EquipEffect.CRI))
        	{
        		atkLight *= 1.5F;
        		applyParticleSpecialEffect(1);
        	}
        	else
        	{
        		//calc double hit
            	if (this.rand.nextFloat() < this.host.getEffectEquip(ID.EquipEffect.DHIT))
            	{
            		atkLight *= 2F;
            		applyParticleSpecialEffect(2);
            	}
            	else
            	{
            		//calc double hit
                	if (this.rand.nextFloat() < this.host.getEffectEquip(ID.EquipEffect.THIT))
                	{
                		atkLight *= 3F;
                		applyParticleSpecialEffect(3);
                	}
            	}
        	}
        }
        
  		//calc damage to player
  		if (target instanceof EntityPlayer)
  		{
  			atkLight *= 0.25F;
  			if (atkLight > 59F) atkLight = 59F;	//same with TNT
  		}
  		
  		//check friendly fire
		if (!TeamHelper.doFriendlyFire(this, target)) atkLight = 0F;

	    //並且回傳是否成功傷害到目標
  		boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atkLight);
	    
	    //if attack success
	    if (isTargetHurt)
	    {
	    	applySoundAtTarget(1, target);
	        applyParticleAtTarget(0, target, new float[4]);
	        
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
		
		//計算目標距離
		float[] distVec = new float[4];
		float tarX = (float) target.posX;
		float tarY = (float) target.posY;
		float tarZ = (float) target.posZ;
		
		distVec[0] = tarX - (float) this.posX;
        distVec[1] = tarY - (float) this.posY;
        distVec[2] = tarZ - (float) this.posZ;
		distVec[3] = MathHelper.sqrt(distVec[0]*distVec[0] + distVec[1]*distVec[1] + distVec[2]*distVec[2]);
        
        //play sound and particle
        applySoundAtAttacker(2, target);
	    applyParticleAtAttacker(2, target, distVec);
		
        //calc miss chance
	    float missChance = 0.25F - 0.001F * this.host.getStateMinor(ID.M.ShipLevel);
        missChance -= this.host.getEffectEquip(ID.EquipEffect.MISS);	//equip miss reduce
        if (missChance > 0.35F) missChance = 0.35F;
       
        if (this.rand.nextFloat() < missChance)
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	applyParticleSpecialEffect(0);  //miss particle
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
  	public void applyParticleAtAttacker(int type, Entity target, float[] vec)
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