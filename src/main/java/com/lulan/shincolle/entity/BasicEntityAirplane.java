package com.lulan.shincolle.entity;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.lulan.shincolle.ai.EntityAIShipAircraftAttack;
import com.lulan.shincolle.ai.EntityAIShipOpenDoor;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityAirplane;
import com.lulan.shincolle.entity.other.EntityRensouhou;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

abstract public class BasicEntityAirplane extends EntityLiving implements IShipCannonAttack, IShipFlyable
{

	protected IShipAircraftAttack host;  		//host
	protected ShipPathNavigate shipNavigator;	//水空移動用navigator
	protected ShipMoveHelper shipMoveHelper;
	protected Entity atkTarget;
	protected Entity rvgTarget;					//revenge target
	protected int revengeTime;					//revenge target time
	
    //attributes
    public float atk;				//damage
    public float atkSpeed;			//attack speed
    public float movSpeed;			//move speed
    public float def;				//def value
    public float kbValue;			//knockback value
    
    //AI flag
    protected int numAmmoLight;
    protected int numAmmoHeavy;
    protected boolean useAmmoLight;
    protected boolean useAmmoHeavy;
    protected boolean backHome;  	//can back to carrier
    protected boolean canFindTarget;//can find target
    protected int deadTick;			//setDead count time
    
    //target selector
    protected TargetHelper.Sorter targetSorter = null;
    protected Predicate<Entity> targetSelector = null;
	
    
    public BasicEntityAirplane(World world)
    {
        super(world);
        this.maxHurtResistantTime = 2;
        this.backHome = false;
        this.canFindTarget = true;
        this.isImmuneToFire = true;
        this.shipNavigator = new ShipPathNavigate(this, worldObj);
        this.shipMoveHelper = new ShipMoveHelper(this, 30F);
		this.stepHeight = 7F;
		this.deadTick = 0;
		
    }
    
    //parms: host entity, target entity, launch height
    abstract public void initAttrs(IShipAircraftAttack host, Entity target, double launchPos);

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
  	
	//不跟ship碰撞
  	protected void collideWithEntity(Entity target)
  	{
  		if (target instanceof BasicEntityShip || target instanceof BasicEntityMount)
  		{
  			return;
  		}
  		
  		target.applyEntityCollision(this);
    }
  	
    //clear AI
  	protected void clearAITasks()
  	{
  	   tasks.taskEntries.clear();
  	}
  	
  	//clear target AI
  	protected void clearAITargetTasks()
  	{
  	   targetTasks.taskEntries.clear();
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
    
	@Override
	public boolean isJumping()
	{
		return false;
	}

	@Override
	public int getAttackTime()
	{
		return 0;
	}
    
    //enable next target AI
    public boolean canFindTarget()
    {
    	return this.canFindTarget;
    }
    
    @Override
	public float getAttackDamage()
    {
		return this.atk;
	}
    
    @Override
    public float getAttackSpeed()
    {
    	return this.atkSpeed;
    }
    
    @Override
	public float getAttackRange()
    {
    	return 6F;
    }
    
    @Override
	public float getMoveSpeed()
    {
		return this.movSpeed;
	}
    
    @Override
   	public int getAmmoLight()
    {
   		return this.numAmmoLight;
   	}

   	@Override
   	public int getAmmoHeavy()
   	{
   		return this.numAmmoHeavy;
   	}
    
    @Override
	public boolean hasAmmoLight()
    {
    	return this.numAmmoLight > 0;
    }
    
    @Override
	public boolean hasAmmoHeavy()
    {
    	return this.numAmmoHeavy > 0;
    }

	@Override
	public void setAmmoLight(int num)
	{
		this.numAmmoLight = num;
	}

	@Override
	public void setAmmoHeavy(int num)
	{
		this.numAmmoHeavy = num;
	}

    //移動計算, 去除gravity部份
    @Override
	public void moveEntityWithHeading(float strafe, float forward)
    { 	
        this.moveRelative(strafe, forward, this.movSpeed * 0.4F); //水中的速度計算(含漂移效果)
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

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
        float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4F;

        if (f4 > 1F)
        {
            f4 = 1F;
        }

        this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }
    
	//ammo recycle
    protected void recycleAmmo()
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
	public void onUpdate()
	{
		//server side
		if (!this.worldObj.isRemote)
		{
			//dead tick++ if no movement
			if (Math.abs(this.posX + this.posY + this.posZ - this.prevPosX - this.prevPosY - this.prevPosZ) < 0.01D)
			{
				this.deadTick++;
			}
			else
			{
				this.deadTick = 0;
			}
			
			//host check
			if (this.getPlayerUID() == 0 || this.getPlayerUID() == -1 || this.deadTick > 100)
			{
				//no host, or host has no owner
				this.setDead();
			}
			else
			{
				if (this.host instanceof EntityLivingBase)
				{
					EntityLivingBase hostent = (EntityLivingBase) this.host;
					
					//歸宅
					if (this.backHome && this.isEntityAlive())
					{
						float dist = this.getDistanceToEntity(hostent);
						
						//get home path
						if (dist > 2.7F)
						{
							if (this.ticksExisted % 16 == 0)
							{
								this.getShipNavigate().tryMoveToXYZ(hostent.posX, hostent.posY + 2.3D, hostent.posZ, 1D);
							}
						}
						//get home
						else
						{	//歸還剩餘彈藥 (但是grudge不歸還)
							this.recycleAmmo();
							this.setDead();
						}
					}
					
					//前幾秒直線往目標移動
					if (this.ticksExisted < 34 && this.getEntityTarget() != null)
					{
						double distX = this.getEntityTarget().posX - this.posX;
						double distZ = this.getEntityTarget().posZ - this.posZ;
						double distSqrt = MathHelper.sqrt_double(distX*distX + distZ*distZ);
						
						this.motionX = distX / distSqrt * 0.375D;
						this.motionZ = distZ / distSqrt * 0.375D;
						this.motionY = 0.1D;
					}
				}
				
				//check every 16 ticks
				if (this.ticksExisted % 16 == 0 && this.canFindTarget)
				{
					//change backhome
					if (this.ticksExisted < 900)
					{
						if (this.getEntityTarget() == null)
						{
							this.backHome = true;
						}
						else
						{
							if (this.getEntityTarget().isEntityAlive())
							{
								this.backHome = false;
							}
							else
							{
								this.backHome = true;
							}
						}
					}
					
					//攻擊目標消失, 找附近目標 or 設為host目前目標
					if (this.ticksExisted >= 20)
					{
						Entity newTarget = null;
						List list = null;
						
						//if host Anti-Air, find airplane every 32 ticks
						if (this.host.getStateFlag(ID.F.AntiAir))
						{
							list = this.worldObj.getEntitiesWithinAABB(BasicEntityAirplane.class, this.getEntityBoundingBox().expand(24D, 24D, 24D), this.targetSelector);
						}
						
						//find new target if "target dead" or "no air target"
						if (list == null || list.isEmpty())
						{
							list = this.worldObj.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().expand(16D, 16D, 16D), this.targetSelector);
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
				        }
					}
				}//end can find target

				//避免窒息
				if (this.isInWater() && this.ticksExisted % 256 == 0)
				{
					this.setAir(300);
				}
				
				//超過90秒自動消失
				if (this.ticksExisted > 1800)
				{
					this.recycleAmmo();
					this.setDead();
				}
				
				//達到45秒時歸宅
				if (this.ticksExisted >= 900)
				{
					this.backHome = true;
					this.canFindTarget = false;
					this.setEntityTarget(null);
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
	public boolean attackEntityFrom(DamageSource source, float atk)
	{
		//disable special damage
		if (source.getDamageType() == "inWall")
		{
			return false;
		}
		
		if (source.getDamageType() == "outOfWorld")
		{
			this.setDead();
			return false;
		}
		
		//calc dodge
		if (source.getEntity() != null)
		{
			float dist = (float) this.getDistanceSqToEntity(source.getEntity());
			if (EntityHelper.canDodge(this, dist))
			{
				return false;
			}
		}
		
		//def calc
		float reduceAtk = atk;
		
		if (host != null)
		{
			reduceAtk = atk * (1F - this.getDefValue() * 0.01F);
		}
		
		//ship vs ship, config傷害調整
		Entity entity = source.getEntity();
		
		if (entity instanceof BasicEntityShip || entity instanceof BasicEntityAirplane || 
			entity instanceof EntityRensouhou || entity instanceof BasicEntityMount)
		{
			reduceAtk = reduceAtk * (float)ConfigHandler.dmgSummon * 0.01F;
		}
		
		//ship vs ship, damage type傷害調整
		if (entity instanceof IShipAttackBase)
		{
			//get attack time for damage modifier setting (day, night or ...etc)
			int modSet = this.worldObj.provider.isDaytime() ? 0 : 1;
			reduceAtk = CalcHelper.calcDamageByType(reduceAtk, ((IShipAttackBase) entity).getDamageType(), this.getDamageType(), modSet);
		}
		
        if (reduceAtk < 1) reduceAtk = 1;
        
        return super.attackEntityFrom(source, reduceAtk);
    }

	//light attack
	@Override
	public boolean attackEntityWithAmmo(Entity target)
	{
		float atkLight = CalcHelper.calcDamageBySpecialEffect(this, target, this.getAttackDamage(), 0);
		EntityLivingBase hostent = null;
		
		//only for EntityLivingBase
		if (this.host instanceof EntityLivingBase)
		{
			hostent = (EntityLivingBase) this.host;
		}
		else
		{
			return false;
		}
		
//		//TODO sound event
//		//play cannon fire sound at attacker
//        playSound(Reference.MOD_ID+":ship-machinegun", ConfigHandler.volumeFire, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //attack particle
        TargetPoint point0 = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 8, false), point0);
		
		//calc miss chance, if not miss, calc cri/multi hit
		TargetPoint point = new TargetPoint(this.dimension, hostent.posX, hostent.posY, hostent.posZ, 64D);
		float missChance = 0.25F - 0.001F * this.host.getStateMinor(ID.M.ShipLevel);
        missChance -= this.host.getEffectEquip(ID.EF_MISS);	//equip miss reduce
        if (missChance > 0.35F) missChance = 0.35F;
  		
        //calc miss chance
        if (this.rand.nextFloat() < missChance)
        {
        	atkLight = 0;	//still attack, but no damage
        	//spawn miss particle
        	
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(hostent, 10, false), point);
        }
        else
        {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if (this.rand.nextFloat() < this.host.getEffectEquip(ID.EF_CRI))
        	{
        		atkLight *= 1.5F;
        		//spawn critical particle
            	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(hostent, 11, false), point);
        	}
        	else
        	{
        		//calc double hit
            	if (this.rand.nextFloat() < this.host.getEffectEquip(ID.EF_DHIT))
            	{
            		atkLight *= 2F;
            		//spawn double hit particle
            		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(hostent, 12, false), point);
            	}
            	else
            	{
            		//calc double hit
                	if (this.rand.nextFloat() < this.host.getEffectEquip(ID.EF_THIT))
                	{
                		atkLight *= 3F;
                		//spawn triple hit particle
                		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(hostent, 13, false), point);
                	}
            	}
        	}
        }
        
        //vs player = 25% dmg
  		if (target instanceof EntityPlayer)
  		{
  			atkLight *= 0.25F;
  		}
  		
  		//check friendly fire
		if (!TeamHelper.doFriendlyFire(this.host, target))
		{
			atkLight = 0F;
		}
		
		//set max damage
		if (atkLight > 59F)
		{
			atkLight = 59F;	//same with TNT
		}

	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
  		boolean isTargetHurt = false;
  		
  		if (this.host != null)
  		{
  			isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(hostent).setProjectile(), atkLight);
  		}
	    
	    //if attack success
	    if (isTargetHurt)
	    {
        	//send packet to client for display partical effect  
	        TargetPoint point1 = new TargetPoint(this.dimension, target.posX, target.posY, target.posZ, 64D);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 0, false), point1);
        }
	    
	    //消耗彈藥計算
  		if (numAmmoLight > 0)
  		{
  			numAmmoLight--;
  		}

	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		//get attack value
		float atkHeavy = this.getAttackDamage();
		EntityLivingBase hostent = null;
		
		//only for EntityLivingBase
		if (this.host instanceof EntityLivingBase)
		{
			hostent = (EntityLivingBase) this.host;
		}
		else
		{
			return false;
		}

//		//TODO sound event
//		//play cannon fire sound at attacker
//        this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.volumeFire, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
		//calc miss chance, if not miss, calc cri/multi hit
        float missChance = 0.25F - 0.001F * this.host.getStateMinor(ID.M.ShipLevel);
        missChance -= this.host.getEffectEquip(ID.EF_MISS);	//equip miss reduce
        if (missChance > 0.35F) missChance = 0.35F;
		
        //calc miss chance
        if (this.rand.nextFloat() < missChance)
        {
        	atkHeavy = 0;	//still attack, but no damage
        	//spawn miss particle
        	TargetPoint point = new TargetPoint(this.dimension, hostent.posX, hostent.posY, hostent.posZ, 64D);
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(hostent, 10, false), point);
        }

        //spawn missile
    	EntityAbyssMissile missile = new EntityAbyssMissile(this.worldObj, this, 
        		(float)target.posX, (float)(target.posY+target.height*0.2F), (float)target.posZ, (float)(this.posY-0.8F), atkHeavy, kbValue, true, -1F);
        this.worldObj.spawnEntityInWorld(missile);
        
        //消耗彈藥計算
  		if (numAmmoHeavy > 0)
  		{
  			numAmmoHeavy--;
  		}
  		
        return true;
	}
	
	@Override
	public Entity getEntityTarget()
	{
		return this.atkTarget;
	}
  	
  	@Override
	public void setEntityTarget(Entity target)
  	{
		this.atkTarget = target;
	}
	
	@Override
	public ShipPathNavigate getShipNavigate()
	{
		return this.shipNavigator;
	}

	@Override
	public ShipMoveHelper getShipMoveHelper()
	{
		return this.shipMoveHelper;
	}
	
	//update ship move helper
	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
		
        EntityHelper.updateShipNavigator(this);
    }
	
	@Override
	public boolean canFly()
	{
		return true;
	}
	
	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}
	
	@Override
	public byte getStateEmotion(int id)
	{
		return 0;
	}

	@Override
	public void setStateEmotion(int id, int value, boolean sync) {}

	@Override
	public int getFaceTick()
	{
		return 0;
	}

	@Override
	public int getHeadTiltTick()
	{
		return 0;
	}

	@Override
	public void setFaceTick(int par1) {}

	@Override
	public void setHeadTiltTick(int par1) {}

	@Override
	public int getTickExisted()
	{
		return this.ticksExisted;
	}
	
	@Override
	public boolean useAmmoLight()
	{
		return this.useAmmoLight;
	}

	@Override
	public boolean useAmmoHeavy()
	{
		return this.useAmmoHeavy;
	}
	
	@Override
	public boolean getIsRiding()
	{
		return false;
	}

	@Override
	public boolean getIsSprinting()
	{
		return false;
	}

	@Override
	public boolean getIsSitting()
	{
		return false;
	}

	@Override
	public boolean getIsSneaking()
	{
		return false;
	}

	@Override
	public boolean getIsLeashed()
	{
		return false;
	}

	@Override
	public boolean getStateFlag(int flag)
	{	//for attack AI check
		switch (flag)
		{
		default:
			return true;
		case ID.F.OnSightChase:
			return false;
		}
	}

	@Override
	public void setStateFlag(int id, boolean flag) {}
	
	@Override
	public int getLevel()
	{
		if(this.host != null) return this.host.getLevel();
		return 150;
	}
	
	@Override
	public int getStateMinor(int id)
	{
		return 0;
	}

	@Override
	public void setStateMinor(int state, int par1) {}
	
	@Override
	public float getEffectEquip(int id)
	{
		if (this.host != null) return host.getEffectEquip(id);
		return 0F;
	}
	
	@Override
	public float getDefValue()
	{
		return def;
	}
	
	@Override
	public void setEntitySit() {}
	
    @Override
    public float getModelRotate(int par1)
    {
    	return 0F;
    }
    
    @Override
	public void setModelRotate(int par1, float par2) {}
    
    @Override
	public boolean getAttackType(int par1)
    {
		return true;
	}

	@Override
	public int getPlayerUID()
	{
		if (this.host != null) return this.host.getPlayerUID();
		return -1;
	}

	@Override
	public void setPlayerUID(int uid) {}
	
	@Override
	public Entity getHostEntity()
	{
		if (this.host instanceof Entity) return (Entity) this.host;
		return null;
	}
    
	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.AIRPLANE;
	}
	
	@Override
	public Entity getEntityRevengeTarget()
	{
		return this.rvgTarget;
	}

	@Override
	public int getEntityRevengeTime()
	{
		return this.revengeTime;
	}

	@Override
	public void setEntityRevengeTarget(Entity target)
	{
		this.rvgTarget = target;
	}
  	
  	@Override
	public void setEntityRevengeTime()
  	{
		this.revengeTime = this.ticksExisted;
	}
  	
  	@Override
	public int getAttackAniTick()
  	{
		return 0;
	}

	@Override
	public void setAttackAniTick(int par1) {}

	@Override
	public boolean canBePushed()
	{
        return false;
    }
	
	@Override
	public float getSwingTime(float partialTick)
	{
		return 0;
	}
	
	
}