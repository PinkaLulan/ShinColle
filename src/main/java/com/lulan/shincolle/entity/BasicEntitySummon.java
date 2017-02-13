package com.lulan.shincolle.entity;

import java.util.Random;

import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.client.render.IShipCustomTexture;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

abstract public class BasicEntitySummon extends EntityLiving implements IShipCannonAttack, IShipCustomTexture
{
	
    //attributes
	protected static final IAttribute MAX_HP = (new RangedAttribute((IAttribute)null, "generic.maxHealth", 4D, 0D, 30000D)).setDescription("Max Health").setShouldWatch(true);
	protected float atk;				//damage
	protected float atkSpeed;			//attack speed
	protected float atkRange;			//attack range
	protected float defValue;			//def value
	protected float movSpeed;			//def value
    protected float kbValue;			//knockback value
    
    //AI
	protected IShipAttackBase host;  	//host target
	protected ShipPathNavigate shipNavigator;
	protected ShipMoveHelper shipMoveHelper;
	protected Entity atkTarget;
	protected Entity rvgTarget;					//revenge target
    protected int numAmmoLight;
    protected int numAmmoHeavy;
    protected int revengeTime;			//revenge target time
    
    //model display
    /**EntityState: 0:HP State 1:Emotion 2:Emotion2*/
	protected byte stateEmotion, stateEmotion2, scaleLevel;					//表情type
	protected int startEmotion, startEmotion2, attackTime, attackTime2;		//表情timer
	protected boolean headTilt;
	public boolean initScale;

	
    public BasicEntitySummon(World world)
    {
		super(world);
		
        //AI flag
		this.maxHurtResistantTime = 2;
        this.numAmmoLight = 6;
        this.numAmmoHeavy = 0;
        this.stateEmotion = 0;
        this.stateEmotion2 = 0;
        this.startEmotion = 0;
        this.startEmotion2 = 0;
        this.headTilt = false;
        this.isImmuneToFire = true;
        this.initScale = false;
        
        //model
        this.scaleLevel = 0;
	}
    
    //init attrs
    abstract public void initAttrs(IShipAttackBase host, Entity target, int scaleLevel, float...par2);

    /** set size with scale level */
	abstract protected void setSizeWithScaleLevel();
	
	/** set attrs with scale level */
	abstract protected void setAttrsWithScaleLevel();
	
	@Override
	public void setScaleLevel(int par1)
	{
		this.scaleLevel = (byte) par1;
		
		//set size
		this.setSizeWithScaleLevel();
		
		//set attrs
		this.setAttrsWithScaleLevel();
		
		this.initScale = true;
		
		//sync to client
		if (!this.world.isRemote) this.sendSyncPacket(0);
	}
	
	@Override
    public IAttributeInstance getEntityAttribute(IAttribute attribute)
    {
		if (attribute == SharedMonsterAttributes.MAX_HEALTH)
		{
			this.getAttributeMap().getAttributeInstance(MAX_HP); 
		}
		
        return this.getAttributeMap().getAttributeInstance(attribute);
    }
    
	@Override
    protected void applyEntityAttributes()
    {
        this.getAttributeMap().registerAttribute(MAX_HP);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
    }
	
	//setup AI
	abstract protected void setAIList();
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
        this.scaleLevel = nbt.getByte("scaleLV");
        
        //rescale at client side
        float hp = this.getHealth();
        this.setScaleLevel(this.scaleLevel);
        this.setHealth(hp);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setByte("scaleLV", this.scaleLevel);
		
		return nbt;
	}
	
	/**
	 * packet type:
	 *   0: sync scale
	 */
	public void sendSyncPacket(int type)
	{
		if (!world.isRemote)
		{
			byte pid = 0;
			
			switch (type)
			{
			case 0:
				pid = S2CEntitySync.PID.SyncShip_Scale;
			break;
			default:
				return;
			}
			
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, pid), point);
		}
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		//both side
		if (this.attackTime > 0) this.attackTime--;
		
		//server side
		if (!this.world.isRemote)
		{
			if (this.ticksExisted == 1)
			{
				//sync scale level on after entity construction
				this.sendSyncPacket(0);
			}
			
			boolean setdead = false;
			
			//owner消失(通常是server restart) or host dead
			if (this.host == null ||
				(this.host instanceof EntityLivingBase && !((EntityLivingBase)this.host).isEntityAlive()))
			{
				setdead = true;
			}
			else
			{
				//超過60秒自動消失
				if (this.ticksExisted > this.getLifeLength())
				{
					setdead = true;
				}

				//target is dead
				if (!canFindTarget() && (this.getEntityTarget() == null || this.getEntityTarget().isDead))
				{
					//get target by host
					if (this.host.getEntityTarget() != null && this.host.getEntityTarget().isEntityAlive())
					{
						this.atkTarget = this.host.getEntityTarget();
					}
					//no target, setDead
					else
					{
						setdead = true;
					}	
				}
				
				//防止溺死
				if (this.isInWater() && this.ticksExisted % 128 == 0)
				{
					this.setAir(300);
				}
			}
			
			//is done
			if (setdead)
			{
				//歸還彈藥
				if (this.host != null)
				{
					this.returnSummonResource();
				}
				
				this.setDead();
			}
		}
		//client side
		else
		{
			//sync scale level
			if (!this.initScale)
			{
				//send scale init packet request
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, this.getEntityId(), this.world.provider.getDimension()));
			}
		}
	}
	
	@Override
    public void onLivingUpdate()
    {
    	if ((!world.isRemote))
    	{
        	//update movement, NOTE: 1.9.4: must done before vanilla MoveHelper updating in super.onLivingUpdate()
        	EntityHelper.updateShipNavigator(this);
            super.onLivingUpdate();
    	}
    	else
    	{
    		super.onLivingUpdate();
    	}
    }
	
	/** 歸還召喚消耗的彈藥等 */
	abstract protected void returnSummonResource();

	@Override
	public byte getStateEmotion(int id)
	{
		return id == 1 ? stateEmotion : stateEmotion2;
	}
	
	@Override
	public void setStateEmotion(int id, int value, boolean sync)
	{	
		switch (id)
		{
		case 1:
			stateEmotion = (byte) value;
			break;
		case 2:
			stateEmotion2 = (byte) value;
			break;
		default:
			break;
		}
		
		if (sync && !world.isRemote)
		{
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncEntity_Emo), point);
		}
	}

	@Override
	abstract public boolean getStateFlag(int flag);

	@Override
	abstract public void setStateFlag(int id, boolean flag);

	@Override
	public int getFaceTick()
	{
		return this.startEmotion;
	}

	@Override
	public int getHeadTiltTick()
	{
		return this.startEmotion2;
	}

	@Override
	public void setFaceTick(int par1)
	{
		this.startEmotion = par1;
	}

	@Override
	public void setHeadTiltTick(int par1)
	{
		this.startEmotion2 = par1;
	}

	@Override
	public int getTickExisted()
	{
		return this.ticksExisted;
	}

	@Override
	public int getAttackTick()
	{
		return this.attackTime;
	}
	
  	@Override
	public int getAttackTick2()
  	{
		return this.attackTime2;
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
    public void moveEntityWithHeading(float strafe, float forward)
    {
    	EntityHelper.moveEntityWithHeading(this, strafe, forward);
    }

	@Override
	public float getAttackSpeed()
	{
		return this.atkSpeed;
	}

	@Override
	public float getAttackRange()
	{
		return this.atkRange;
	}
	
	@Override
	public float getMoveSpeed()
	{
		return this.movSpeed;
	}
	
	@Override
	public float getJumpSpeed()
	{
		return 1F;
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

	@Override
	public float getAttackDamage()
	{
		return this.atk;
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
	public ShipPathNavigate getShipNavigate()
	{
		return this.shipNavigator;
	}

	@Override
	public ShipMoveHelper getShipMoveHelper()
	{
		return this.shipMoveHelper;
	}
	
	@Override
	public boolean canFly()
	{
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}

	@Override
	public boolean getIsLeashed()
	{
		return false;
	}

	@Override
	public int getLevel()
	{
		if (this.host != null) return this.host.getLevel();
		return 150;
	}

	@Override
	abstract public boolean useAmmoLight();

	@Override
	abstract public boolean useAmmoHeavy();
	
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
		return this.defValue;
	}

	@Override
	public void setEntitySit(boolean sit) {}

	@Override
	public float getModelRotate(int par1)
	{
		return 0;
	}

	@Override
	public void setModelRotate(int par1, float par2) {}

	//ignore attack type checking
	@Override
	public boolean getAttackType(int par1)
	{
		return true;
	}

	@Override
	abstract public int getPlayerUID();

	@Override
	abstract public void setPlayerUID(int uid);
	
	@Override
	public Entity getHostEntity()
	{
		return (Entity) this.host;
	}

	@Override
	abstract public int getDamageType();
	
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
	public void setAttackTick(int par1) 
	{
		this.attackTime = par1;
	}

	@Override
	public void setAttackTick2(int par1)
	{
		this.attackTime2 = par1;
	}

	@Override
	public float getSwingTime(float partialTick)
	{
		return 0;
	}

	@Override
	public boolean isJumping()
	{
		return this.isJumping;
	}
	
	@Override
	abstract public int getTextureID();
	
	//for model display
	@Override
	public int getRidingState()
	{
		return 0;
	}
	
	@Override
	public void setRidingState(int state) {}

	@Override
	public int getScaleLevel()
	{
		return this.scaleLevel;
	}
	
  	abstract public float getAttackBaseDamage(int type, Entity target);
  	
	@Override
	public boolean attackEntityFrom(DamageSource source, float atk)
	{
		if (this.world.isRemote) return false;
		
		//null check
		if (this.host == null)
		{
			this.setDead();
			return false;
		}
		
		boolean checkDEF = true;
		
		//damage disabled
		if (source == DamageSource.inWall || source == DamageSource.starve ||
			source == DamageSource.cactus || source == DamageSource.fall)
		{
			return false;
		}
		//damage ignore def value
		else if (source == DamageSource.magic || source == DamageSource.wither ||
				 source == DamageSource.dragonBreath)
		{
			checkDEF = false;
		}
		//out of world
		else if (source == DamageSource.outOfWorld)
		{
			this.returnSummonResource();
			this.setDead();
			return false;
		}
		
		//set hurt face
    	if (this.getStateEmotion(ID.S.Emotion) != ID.Emotion.O_O)
    	{
    		this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
    	}
    	
    	//若攻擊方為owner, 則直接回傳傷害, 不計def跟friendly fire
		if (source.getEntity() instanceof EntityPlayer &&
			TeamHelper.checkSameOwner(source.getEntity(), this))
		{
			return super.attackEntityFrom(source, atk);
		}
        
        //無敵的entity傷害無效
		if (this.isEntityInvulnerable(source))
		{
            return false;
        }
		//只對entity damage類有效
		else if (source.getEntity() != null)
		{
			Entity attacker = source.getEntity();
			
			//不會對自己造成傷害, 可免疫毒/掉落/窒息等傷害 (此為自己對自己造成傷害)
			if (attacker.equals(this))
			{
				return false;
			}
			
			//若攻擊方為player, 則檢查friendly fire
			if (this.getPlayerUID() > 0 && attacker instanceof EntityPlayer)
			{
				//若禁止friendlyFire, 則不造成傷害
				if (!ConfigHandler.friendlyFire)
				{
					return false;
				}
			}
			
			//進行dodge計算
			float dist = (float) this.getDistanceSqToEntity(attacker);
			if (EntityHelper.canDodge(this, dist))
			{
				return false;
			}
			
			float reducedAtk = atk;
			
			//進行def計算
			if (checkDEF)
			{
				reducedAtk = atk * (1F - (this.getDefValue() + rand.nextInt(50) - 25F) * 0.01F);
			}
			
			//ship vs ship, config傷害調整 (僅限友善船)
			if (this.getPlayerUID() > 0 && attacker instanceof IShipOwner &&
				((IShipOwner)attacker).getPlayerUID() > 0 &&
				(attacker instanceof BasicEntityShip ||
				 attacker instanceof BasicEntitySummon ||
				 attacker instanceof BasicEntityMount))
			{
				reducedAtk = reducedAtk * (float)ConfigHandler.dmgSvS * 0.01F;
			}
			
			//ship vs ship, damage type傷害調整
			if (attacker instanceof IShipAttackBase)
			{
				//get attack time for damage modifier setting (day, night or ...etc)
				int modSet = this.world.provider.isDaytime() ? 0 : 1;
				reducedAtk = CalcHelper.calcDamageByType(reducedAtk, ((IShipAttackBase) attacker).getDamageType(), this.getDamageType(), modSet);
			}
			
			//tweak min damage
	        if (reducedAtk < 1F && reducedAtk > 0F) reducedAtk = 1F;
	        else if (reducedAtk <= 0F) reducedAtk = 0F;

            return super.attackEntityFrom(source, reducedAtk);
        }//end is entity damage source
		
		return false;
    }
	
	@Override
	public boolean updateSkillAttack(Entity target)
	{
		return false;
	}
	
  	public void applySoundAtAttacker(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			this.playSound(ModSounds.SHIP_FIRELIGHT, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  		break;
  		case 2:  //heavy cannon
  			this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  		break;
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  			this.playSound(ModSounds.SHIP_AIRCRAFT, ConfigHandler.volumeFire * 0.5F, this.getSoundPitch() * 0.85F);
  	  	break;
		default: //melee
		break;
  		}//end switch
  	}
  	
  	public void applyParticleAtAttacker(int type, Entity target, float[] vec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY, this.posZ, vec[0], vec[1], vec[2], true), point);
  		break;
  		case 2:  //heavy cannon
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
  		}
  	}
  	
  	protected void applyParticleSpecialEffect(int type)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  		
  		switch (type)
  		{
  		case 1:  //critical
      		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 11, false), point);
  			break;
  		case 2:  //double hit
      		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 12, false), point);
  			break;
  		case 3:  //triple hit
      		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 13, false), point);
  			break;
		default: //miss
      		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
			break;
  		}
  	}
  	
  	public void applySoundAtTarget(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  		break;
  		case 2:  //heavy cannon
  		break;
  		case 3:  //light aircraft
  		break;
  		case 4:  //heavy aircraft
  		break;
		default: //melee
		break;
  		}
  	}
  	
  	public void applyParticleAtTarget(int type, Entity target, float[] vec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  		
  		switch (type)
  		{
  		case 1:  //light cannon
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point);
  		break;
  		case 2:  //heavy cannon
  		break;
  		case 3:  //light aircraft
  		break;
  		case 4:  //heavy aircraft
  		break;
		default: //melee
    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
		break;
  		}
  	}
  	
  	//life time
  	protected int getLifeLength()
  	{
  		return 1200;
  	}
  	
    //enable target finding AI
  	protected boolean canFindTarget()
    {
    	return false;
    }
  	
	@Override
	public Random getRand()
	{
		return this.rand;
	}
	
	@Override
	public double getShipDepth(int type)
	{
		return 0D;
	}
	
	@Override
	public int getDeathTick()
	{
		return this.deathTime;
	}

	@Override
	public void setDeathTick(int par1)
	{
		this.deathTime = par1;
	}


}