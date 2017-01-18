package com.lulan.shincolle.entity;

import java.util.Random;

import javax.annotation.Nullable;

import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipOpenDoor;
import com.lulan.shincolle.ai.EntityAIShipRangeTarget;
import com.lulan.shincolle.ai.EntityAIShipRevengeTarget;
import com.lulan.shincolle.ai.EntityAIShipWander;
import com.lulan.shincolle.ai.EntityAIShipWatchClosest;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.client.render.IShipCustomTexture;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CInputPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public abstract class BasicEntityShipHostile extends EntityMob implements IShipCannonAttack, IShipFloating, IShipCustomTexture
{
	
	//attributes
	protected static final IAttribute MAX_HP = (new RangedAttribute((IAttribute)null, "generic.maxHealth", 4D, 0D, 30000D)).setDescription("Max Health").setShouldWatch(true);
	protected float atk;				//damage
	protected float atkSpeed;			//attack speed
	protected float atkRange;			//attack range
	protected float defValue;			//def value
	protected float movSpeed;			//def value
    protected float kbValue;			//knockback value
    protected double ShipDepth;			//水深, 用於水中高度判定
    
    //model display
    /** emotion state: 0:State 1:Emotion 2:Emotion2 3:HPState 4:State2 5:AttackPhase 6:Emotion3 */
    protected byte[] stateEmotion;
	protected int startEmotion, startEmotion2, attackTime, attackTime2, attackTime3, emoteDelay;
	protected boolean headTilt;
	protected float[] rotateAngle;		//模型旋轉角度, 用於手持物品render
	protected int soundHurtDelay;		//hurt sound ticks
	protected short shipClass;
	public byte scaleLevel;				//mob level: 0:small mob, 1:large mob, 2:small boss, 3:large boss
	
	//misc
	protected ItemStack dropItem;
	protected BossInfoServer bossInfo;
	
	//AI
	public boolean canDrop;				//drop item flag
	public boolean initScale;
	protected ShipPathNavigate shipNavigator;	//水空移動用navigator
	protected ShipMoveHelper shipMoveHelper;
	protected Entity atkTarget;
	protected Entity rvgTarget;					//revenge target
	protected int revengeTime;					//revenge target time
	public static boolean stopAI = false;		//stop onUpdate, onLivingUpdate
		
	
	public BasicEntityShipHostile(World world)
	{
		super(world);
		this.isImmuneToFire = true;	//set ship immune to lava
		this.ignoreFrustumCheck = true;	//即使不在視線內一樣render
		this.maxHurtResistantTime = 2;
		this.stepHeight = 4F;
		this.canDrop = true;
		this.rotateAngle = new float[] {0F, 0F, 0F};
		this.scaleLevel = 0;
        this.startEmotion = 0;
        this.startEmotion2 = 0;
        this.headTilt = false;
        this.initScale = false;
        
		//model display
        this.soundHurtDelay = 0;
        this.stateEmotion = new byte[] {ID.State.EQUIP00, 0, 0, 0, 0, 0, 0};
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
	
	//display fire effect
	@Override
	public boolean isBurning()
	{
		return this.getStateEmotion(ID.S.HPState) == ID.HPState.HEAVY;
	}
	
	@Override
	public boolean isJumping()
	{
		return this.isJumping;
	}
	
	@Override
	protected boolean canDespawn()
	{
		if (this.scaleLevel > 1)
		{
			if (ConfigHandler.despawnBoss > -1)
			{
				return this.ticksExisted > ConfigHandler.despawnBoss;
			}
	        
			return false;
		}
		else
		{
			if (ConfigHandler.despawnMinion > -1)
			{
				return this.ticksExisted > ConfigHandler.despawnMinion;
			}
	        
			return false;
		}
    }
	
	//setup AI
	protected void setAIList()
	{
		this.clearAITasks();
		this.clearAITargetTasks();
		
		//idle AI
		this.tasks.addTask(21, new EntityAIShipOpenDoor(this, true));			//0000
		this.tasks.addTask(0, new EntityAIShipFloating(this));					//1000
		this.tasks.addTask(23, new EntityAIShipWander(this, 12, 1, 0.8D));		//0111
		this.tasks.addTask(24, new EntityAIShipWatchClosest(this, EntityPlayer.class, 8F, 0.1F)); //0010
		this.tasks.addTask(25, new EntityAILookIdle(this));						//0011
	}
	
	//setup target AI: par1: 0:passive 1:active
	public void setAITargetList()
	{
		this.targetTasks.addTask(1, new EntityAIShipRevengeTarget(this));
		this.targetTasks.addTask(3, new EntityAIShipRangeTarget(this, Entity.class));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
        this.scaleLevel = nbt.getByte("scaleLV");
        
        //rescale at client side
        float hp = this.getHealth();
        this.initAttrs(this.scaleLevel);
        this.setHealth(hp);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setByte("scaleLV", this.scaleLevel);
		
		return nbt;
	}
	
	//clear AI
	protected void clearAITasks()
	{
		tasks.taskEntries.clear();
	}
	
	//clear target AI
	protected void clearAITargetTasks()
	{
		this.setEntityTarget(null);
		targetTasks.taskEntries.clear();
	}
	
	//掉落egg設定
	public ItemStack getDropEgg()
	{
		switch (this.getScaleLevel())
		{
		case 0:		//20%
			return this.rand.nextInt(5) == 0 ? this.dropItem : null;
		case 1:		//33%
			return this.rand.nextInt(3) == 0 ? this.dropItem : null;
		case 2:		//90%
			return this.rand.nextInt(10) > 0 ? this.dropItem : null;
		default:	//100%
			return this.dropItem;
		}
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
	
	//set attributes
	protected void setAttrsWithScaleLevel()
	{
		float[] mods = Values.HostileShipAttrMap.get(this.getShipClass());
		double[] attrs;
		int range = 48;
		double kb = 0.2D;
		
		switch (this.getScaleLevel())
		{
		case 1:
			attrs = ConfigHandler.scaleMobLarge;
			kb = 0.5D;
		break;
		case 2:
			attrs = ConfigHandler.scaleBossSmall;
			range = 64;
			kb = 0.85D;
		break;
		case 3:
			attrs = ConfigHandler.scaleBossLarge;
			range = 64;
			kb = 1D;
		break;
		default:
			attrs = ConfigHandler.scaleMobSmall;
		break;
		}
		
		//set attrs
        this.atk = (float) attrs[ID.ATK] * mods[1];
        this.defValue = (float) attrs[ID.DEF] * mods[2];
        this.atkSpeed = (float) attrs[ID.SPD] * mods[3];
        this.movSpeed = (float) attrs[ID.MOV] * mods[4];
        this.atkRange = (float) attrs[ID.HIT] * mods[5];
        
        this.getEntityAttribute(MAX_HP).setBaseValue(attrs[ID.HP] * mods[0]);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.movSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(range);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(kb);
		
		//renew health
		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
	}
	
	/** set size with scale level */
	abstract protected void setSizeWithScaleLevel();
	
	/** set boss info */
	abstract protected void setBossInfo();
	
	/** init AI/attrs/...etc. after entity construction */
	public void initAttrs(int scaleLevel)
	{
		this.setScaleLevel(scaleLevel);
		
		//set boss info
		if (this.scaleLevel > 1)
		{
			this.setBossInfo();
		}
		
		//for server side, set AI
		if (!this.world.isRemote)
		{
			//set moving AI
			this.shipNavigator = new ShipPathNavigate(this);
			this.shipMoveHelper = new ShipMoveHelper(this, 60F);
			
			//set other AI
			this.setAIList();
			this.setAITargetList();
			
			//set drop
			this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, getStateMinor(ID.M.ShipClass)+2);
		}
	}
	
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
	
	//平常音效
    @Override
    @Nullable
    protected SoundEvent getAmbientSound()
    {
    	if (rand.nextInt(2) == 0)
    	{
			return ModSounds.SHIP_IDLE;
		}
    	
		return null;
    }
    
	//受傷音效
    @Override
    @Nullable
    protected SoundEvent getHurtSound()
    {
		if (rand.nextInt(2) == 0 && this.soundHurtDelay <= 0)
		{
			this.soundHurtDelay = 20 + this.getRNG().nextInt(40);
			return ModSounds.SHIP_HURT;
		}
		return null;
    }

	//死亡音效
    @Override
    @Nullable
    protected SoundEvent getDeathSound()
    {
		return ModSounds.SHIP_DEATH;
    }

    //音效大小
    @Override
    protected float getSoundVolume()
    {
        return ConfigHandler.volumeShip;
    }

	@Override
	public byte getStateEmotion(int id)
	{
		return stateEmotion[id];
	}

	@Override
	public void setStateEmotion(int id, int value, boolean sync)
	{
		stateEmotion[id] = (byte) value;
		
		if (sync && !world.isRemote)
		{
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncEntity_Emo), point);
		}
	}

	@Override
	public boolean getStateFlag(int flag)
	{	//hostile mob: for attack and headTile check
		switch (flag)
		{
		case ID.F.UseMelee:
			return false;
		case ID.F.HeadTilt:
			return this.headTilt;
		case ID.F.OnSightChase:
			return false;
		case ID.F.NoFuel:
			return false;
		default:
			return true;
		}
	}

	@Override
	public void setStateFlag(int id, boolean flag)
	{
		if (id == ID.F.HeadTilt) this.headTilt = flag;
	}

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
	public float getAttackDamage()
	{
		return this.atk;
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource source, float atk)
	{
		if (this.world.isRemote) return false;
		
		//set hurt face
    	if (this.getStateEmotion(ID.S.Emotion) != ID.Emotion.O_O)
    	{
    		this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
    	}
    	
		//damage disabled
		if (source == DamageSource.inWall || source == DamageSource.starve ||
			source == DamageSource.cactus || source == DamageSource.fall)
		{
			return false;
		}
		//damage ignore def value
		else if (source == DamageSource.magic || source == DamageSource.dragonBreath)
		{
			return super.attackEntityFrom(source, atk);
		}
		//out of world
		else if (source == DamageSource.outOfWorld)
		{
			//取消坐下動作
			this.setDead();
        	return false;
		}
		
        //無敵的entity傷害無效
		if (this.isEntityInvulnerable(source))
		{
            return false;
        }
		/**
		 * 這裡不抓原始attacker, 而是抓造成傷害的entity, ex: snow ball, arrow
		 * 可避免用艦載機攻擊時直接跑去對航母攻擊
		 */
		else if (source.getSourceOfDamage() != null)
		{
			Entity attacker = source.getSourceOfDamage();
			
			//不會對自己造成傷害, 可免疫毒/掉落/窒息等傷害 (此為自己對自己造成傷害)
			if (attacker.equals(this)) return false;
			
			//進行dodge計算
			float dist = (float) this.getDistanceSqToEntity(attacker);
			if (EntityHelper.canDodge(this, dist))
			{
				return false;
			}
			
			//進行def計算
			float reduceAtk = atk * (1F - (this.defValue - rand.nextInt(20) + 10F) * 0.01F);    
			
			//ship vs ship, damage type傷害調整
			if (attacker instanceof IShipAttackBase)
			{
				//get attack time for damage modifier setting (day, night or ...etc)
				int modSet = this.world.provider.isDaytime() ? 0 : 1;
				reduceAtk = CalcHelper.calcDamageByType(reduceAtk, ((IShipAttackBase) attacker).getDamageType(), this.getDamageType(), modSet);
			}
			
			//tweak min damage
	        if (reduceAtk < 1F && reduceAtk > 0F) reduceAtk = 1F;
	        else if (reduceAtk <= 0F) reduceAtk = 0F;

			//設置revenge target
			this.setEntityRevengeTarget(attacker);
			this.setEntityRevengeTime();
			
	  		//show emotes
	  		if (this.rand.nextInt(5) == 0)
	  		{
				applyEmotesReaction(2);
	  		}
   
            return super.attackEntityFrom(source, reduceAtk);
        }
		
		return false;
	}

	@Override
	public boolean attackEntityWithAmmo(Entity target)
	{
		//get attack value
		float atk = CalcHelper.calcDamageBySpecialEffect(this, target, this.atk, 0);
		
		//update entity look at vector (for particle spawn)
		float[] distVec = new float[4];  //x, y, z, dist
		distVec[0] = (float) (target.posX - this.posX);
		distVec[1] = (float) (target.posY - this.posY);
		distVec[2] = (float) (target.posZ - this.posZ);
		distVec[3] = MathHelper.sqrt(distVec[0]*distVec[0] + distVec[1]*distVec[1] + distVec[2]*distVec[2]);
        distVec[0] = distVec[0] / distVec[3];
        distVec[1] = distVec[1] / distVec[3];
        distVec[2] = distVec[2] / distVec[3];
      
        //play cannon fire sound at attacker
        applySoundAtAttacker(1, target);
	    applyParticleAtAttacker(1, target, distVec);
	    
        //calc miss chance, if not miss, calc cri/multi hit   
        if (this.rand.nextFloat() < this.getEffectEquip(ID.EF_MISS))
        {
        	atk = 0;	//still attack, but no damage
        	applyParticleSpecialEffect(0);
        }
        else
        {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if (this.rand.nextFloat() < this.getEffectEquip(ID.EF_CRI))
        	{
        		atk *= 1.5F;
        		applyParticleSpecialEffect(1);
        	}
        	else
        	{
        		//calc double hit
            	if (this.rand.nextFloat() < this.getEffectEquip(ID.EF_DHIT))
            	{
            		atk *= 2F;
            		applyParticleSpecialEffect(2);
            	}
            	else
            	{
            		//calc double hit
                	if (this.rand.nextFloat() < this.getEffectEquip(ID.EF_THIT))
                	{
                		atk *= 3F;
                		applyParticleSpecialEffect(2);
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
  		
	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);

	    //if attack success
	    if (isTargetHurt)
	    {
	    	applySoundAtTarget(1, target);
	        applyParticleAtTarget(1, target, distVec);
	        applyEmotesReaction(3);
	    }

	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		//get attack value
		float atk = getAttackBaseDamage(2, target);
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
		
		//play attack effect
        applySoundAtAttacker(2, target);
	    applyParticleAtAttacker(2, target, distVec);
        
	    //calc miss
        if (this.rand.nextFloat() < 0.2F)
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	applyParticleSpecialEffect(0);  //miss particle
        }
        
        //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atk, kbValue, isDirect, -1F);
        this.world.spawnEntity(missile);
        
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
        applyEmotesReaction(3);
        
        return true;
	}
	
	@Override
	public boolean updateSkillAttack(Entity target)
	{
		return false;
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
		return this.isNonBoss() ? 1F : 2F;
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
	public boolean hasAmmoLight()
	{
		return true;
	}

	@Override
	public boolean hasAmmoHeavy()
	{
		return true;
	}

	@Override
	public int getAmmoLight()
	{
		return 100;
	}

	@Override
	public int getAmmoHeavy()
	{
		return 100;
	}

	@Override
	public void setAmmoLight(int num) {}

	@Override
	public void setAmmoHeavy(int num) {}

	@Override
	public double getShipDepth()
	{
		return this.ShipDepth;
	}
	
	@Override
	public double getShipDepth(int type)
	{
		return this.ShipDepth;
	}
	
	@Override
    public void moveEntityWithHeading(float strafe, float forward)
	{
		EntityHelper.moveEntityWithHeading(this, strafe, forward);
    }
	
	/** send sync packet:
	 *  type: 0: scale
	 *        1: motion
	 *        2: rotation & position
	 *        3: rotation
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
			case 1:
				pid = S2CEntitySync.PID.SyncEntity_Motion;
			break;
			case 2:
				pid = S2CEntitySync.PID.SyncEntity_PosRot;
			break;
			case 3:
				pid = S2CEntitySync.PID.SyncEntity_Rot;
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
		if (stopAI)
		{
			return;
		}
		
		super.onUpdate();
		
		//both side
		this.updateArmSwingProgress();
		
		//check depth
		EntityHelper.checkDepth(this);
		
		//time --
		if (this.soundHurtDelay > 0) this.soundHurtDelay--;
		if (this.emoteDelay > 0) this.emoteDelay--;
		if (this.attackTime > 0) this.attackTime--;
		
		//client side
		if (this.world.isRemote)
		{
			if (!this.initScale)
			{
				//send scale init packet request
				CommonProxy.channelG.sendToServer(new C2SInputPackets(C2SInputPackets.PID.RequestSync_Model, this.getEntityId(), this.world.provider.getDimension()));
			}
			
			if (EntityHelper.checkEntityIsInLiquid(this))
			{
				//有移動時, 產生水花特效
				//(注意此entity因為設為非高速更新, client端不會更新motionX等數值, 需自行計算)
				double motX = this.posX - this.prevPosX;
				double motZ = this.posZ - this.prevPosZ;
				double parH = this.posY - (int)this.posY;
				
				if (motX != 0 || motZ != 0)
				{
					ParticleHelper.spawnAttackParticleAt(this.posX + motX*1.5D, this.posY, this.posZ + motZ*1.5D, 
							-motX*0.5D, 0D, -motZ*0.5D, (byte)15);
				}
			}
		}//end client side
		//server side
		else
		{
			//sync scale level on after entity construction
			if (this.ticksExisted == 1)
			{
				this.sendSyncPacket(0);
			}
		}
	}
	
	//check entity state every tick
	@Override
	public void onLivingUpdate()
	{
		//server side
        if ((!world.isRemote))
        {
        	//update movement, NOTE: 1.9.4: must done before vanilla MoveHelper updating in super.onLivingUpdate()
        	EntityHelper.updateShipNavigator(this);
            super.onLivingUpdate();
            
        	//check every 10 ticks
        	if (ticksExisted % 16 == 0)
        	{
        		//update target
            	TargetHelper.updateTarget(this);
            	
            	//get target from vanilla target AI
            	if (this.getAttackTarget() != null)
            	{
            		this.setEntityTarget(this.getAttackTarget());
            	}

            	//check every 256 ticks
            	if (this.ticksExisted % 256 == 0)
            	{
            		//set air value
            		if (this.getAir() < 300)
            		{
                    	setAir(300);
                    }
            		applyEmotesReaction(4);
            	}//end every 256 ticks
        	}//end every 16 ticks	
        }//end server side
        //client side
        else
        {
        	super.onLivingUpdate();
        	
        	if (this.ticksExisted % 16 == 0)
        	{
    			//generate HP state effect
    			switch (getStateEmotion(ID.S.HPState))
    			{
    			case ID.HPState.MINOR:
    				ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
    						this.width * 1.5D, 0.05D, 0D, (byte)4);
    				break;
    			case ID.HPState.MODERATE:
    				ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
    						this.width * 1.5D, 0.05D, 0D, (byte)5);
    				break;
    			case ID.HPState.HEAVY:
    				ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
    						this.width * 1.5D, 0.05D, 0D, (byte)7);
    				break;
    			default:
    				break;
    			}
    			
    			if (this.ticksExisted % 128 == 0)
    			{
    	        	//check hp state
    	    		float hpState = this.getHealth() / this.getMaxHealth();
    	    		
    	    		if (hpState > 0.75F)
    	    		{	//normal
    	    			this.setStateEmotion(ID.S.HPState, ID.HPState.NORMAL, false);
    	    		}
    	    		else if (hpState > 0.5F)
    	    		{	//minor damage
    	    			this.setStateEmotion(ID.S.HPState, ID.HPState.MINOR, false);
    	    		}
    				else if (hpState > 0.25F)
    				{	//moderate damage
    					this.setStateEmotion(ID.S.HPState, ID.HPState.MODERATE, false);   			
    				}
    				else
    				{	//heavy damage
    					this.setStateEmotion(ID.S.HPState, ID.HPState.HEAVY, false);
    				}
            	}//end every 128 ticks
    		}//end every 16 ticks
        }//end client side
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
	public void setShipDepth(double par1)
	{
		ShipDepth = par1;
	}

	@Override
	public boolean getIsLeashed()
	{
		return this.getLeashed();
	}

	@Override
	public int getLevel()
	{
		return 150;
	}

	@Override
	public boolean useAmmoLight()
	{
		return true;
	}

	@Override
	public boolean useAmmoHeavy()
	{
		return true;
	}

	@Override
	public int getStateMinor(int id)
	{
		switch (id)
		{
		case ID.M.ShipClass:
			return this.shipClass;
		default:
			return 0;
		}
	}

	@Override
	public void setStateMinor(int id, int value)
	{
		switch (id)
		{
		case ID.M.ShipClass:
			this.shipClass = (short) value;
			break;
		}
	}

	@Override
	public float getEffectEquip(int id)
	{	//cri rate
		switch(id)
		{
		case ID.EF_MISS:	//miss rate, not miss reduction rate!
			return 0.2F;
		case ID.EF_CRI:
		case ID.EF_DHIT:
		case ID.EF_THIT:
			return 0.15F;
		default:
			return 0F;
		}
	}

	@Override
	public float getDefValue()
	{
		return this.defValue;
	}

	@Override
	public void setEntitySit(boolean sit) {}

	//get model rotate angle, par1 = 0:X, 1:Y, 2:Z
    @Override
    public float getModelRotate(int par1)
    {
    	switch (par1)
    	{
    	default:
    		return this.rotateAngle[0];
    	case 1:
    		return this.rotateAngle[1];
    	case 2:
    		return this.rotateAngle[2];
    	}
    }
    
    //set model rotate angle, par1 = 0:X, 1:Y, 2:Z
    @Override
	public void setModelRotate(int par1, float par2)
    {
		switch (par1)
		{
    	default:
    		rotateAngle[0] = par2;
    	case 1:
    		rotateAngle[1] = par2;
    	case 2:
    		rotateAngle[2] = par2;
    	}
	}

	@Override
	public boolean getAttackType(int par1)
	{
		return true;
	}

	@Override
	public int getPlayerUID()
	{
		return -100;	//-100 for hostile mob
	}

	@Override
	public void setPlayerUID(int uid) {}
	
	@Override
	public Entity getHostEntity()
	{
		return this;
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
  	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand)
    {
		//use kaitai hammer to kill hostile ship (creative mode only)
		if (!this.world.isRemote && player.capabilities.isCreativeMode)
		{
			if (player.inventory.getCurrentItem() != null && 
				player.inventory.getCurrentItem().getItem() == ModItems.KaitaiHammer)
			{
				this.setDead();
				return EnumActionResult.SUCCESS;
			}
		}
		
        return EnumActionResult.PASS;
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
	
	/** spawn emotion particle */
  	public void applyParticleEmotion(int type)
  	{
  		float h = this.height * 0.6F;
  		
  		//server side emotes
  		if (!this.world.isRemote)
  		{
  			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  	  		
  	  		switch (type)
  	  		{
  			default:
  	      		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 36, h, 0, type), point);
  				break;
  	  		}
  		}
  		//client side emotes
  		else
  		{
  			ParticleHelper.spawnAttackParticleAtEntity(this, h, 0, type, (byte)36);
  		}
  	}
  	
  	/** emotes method
  	 * 
  	 *  type:
  	 *  2: damaged
  	 *  3: attack
  	 *  4: idle
  	 *  6: shock
  	 */
  	public void applyEmotesReaction(int type)
  	{
  		switch (type)
  		{
  		case 2:  //damaged
  			if (this.emoteDelay <= 0)
  			{
  				this.emoteDelay = 40;
				reactionDamaged();
			}
  			break;
  		case 3:  //attack
  			if (this.rand.nextInt(7) == 0 && this.emoteDelay <= 0)
  			{
  				this.emoteDelay = 60;
				reactionAttack();
			}
  			break;
  		case 6:  //shock
			reactionShock();
  			break;
  		default: //idle
  			if (this.rand.nextInt(3) == 0 && this.emoteDelay <= 0)
  			{
  				this.emoteDelay = 20;
				reactionIdle();
			}
  			break;
  		}
  	}
  	
  	/** shock emotes */
  	protected void reactionShock()
  	{
		switch (this.rand.nextInt(6))
		{
		case 1:
			applyParticleEmotion(0);  //drop
			break;
		case 2:
			applyParticleEmotion(8);  //cry
			break;
		case 3:
			applyParticleEmotion(4);  //!
			break;
		default:
			applyParticleEmotion(12);  //omg
			break;
		}
  	}
  	
  	/** idle emotes */
  	protected void reactionIdle()
  	{
		switch (this.rand.nextInt(15))
		{
		case 3:
			applyParticleEmotion(7);  //note
			break;
		case 6:
			applyParticleEmotion(3);  //?
			break;
		case 7:
			applyParticleEmotion(16);  //haha
			break;
		case 9:
			applyParticleEmotion(29);  //blink
			break;
		case 10:
			applyParticleEmotion(18);  //sigh
			break;
		default:
			applyParticleEmotion(11);  //find
			break;
		}
  	}
  	
  	/** idle emotes */
  	protected void reactionAttack()
  	{
		switch (this.rand.nextInt(15))
		{
		case 1:
			applyParticleEmotion(33);  //:p
			break;
		case 2:
			applyParticleEmotion(17);  //gg
			break;
		case 3:
			applyParticleEmotion(7);  //note
			break;
		case 4:
			applyParticleEmotion(9);  //hungry
			break;
		case 5:
			applyParticleEmotion(1);  //love
			break;
		case 7:
			applyParticleEmotion(16);  //haha
			break;
		case 8:
			applyParticleEmotion(14);  //+_+
			break;
		case 10:
			applyParticleEmotion(18);  //sigh
			break;
		default:
			applyParticleEmotion(4);  //!
			break;
		}
  	}
  	
  	/** damaged emotes */
  	protected void reactionDamaged()
  	{
  		switch (this.rand.nextInt(15))
  		{
		case 1:
			applyParticleEmotion(4);  //!
			break;
		case 2:
			applyParticleEmotion(5);  //...
			break;
		case 3:
			applyParticleEmotion(2);  //panic
			break;
		case 4:
			applyParticleEmotion(3);  //?
			break;
		case 5:
			applyParticleEmotion(8);  //cry
			break;
		case 7:
			applyParticleEmotion(10);  //dizzy
			break;
		case 8:
			applyParticleEmotion(0);  //sweat
			break;
		default:
			applyParticleEmotion(6);  //angry
			break;
		}
  	}
  	
  	@Override
	public float getSwingTime(float partialTick)
  	{
		return this.getSwingProgress(partialTick);
	}
  	
	//固定swing max tick為6, 無視藥水效果
  	@Override
    protected void updateArmSwingProgress()
  	{
        int swingMaxTick = 6;
        if (this.isSwingInProgress)
        {
            ++this.swingProgressInt;
            
            if (this.swingProgressInt >= swingMaxTick)
            {
                this.swingProgressInt = 0;
                this.isSwingInProgress = false;
            }
        }
        else
        {
            this.swingProgressInt = 0;
        }

        this.swingProgress = (float)this.swingProgressInt / (float)swingMaxTick;
    }
  	
  	/** attack base damage
  	 * 
  	 *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
  	 */
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			return CalcHelper.calcDamageBySpecialEffect(this, target, this.atk, 0);
  		case 2:  //heavy cannon
  			return this.atk * 3F;
  		case 3:  //light aircraft
  			return this.atk;
  		case 4:  //heavy aircraft
  			return this.atk * 3F;
		default: //melee
			return this.atk * 0.125F;
  		}
  	}
  	
  	public void applySoundAtAttacker(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			this.playSound(ModSounds.SHIP_FIRELIGHT, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  	        
  	        //play entity attack sound
  	        if(this.rand.nextInt(10) > 7)
  	        {
  	        	this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
  		case 2:  //heavy cannon
  			this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  	        
  	        //play entity attack sound
  	        if(this.rand.nextInt(10) > 7)
  	        {
  	        	this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  			this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, ConfigHandler.volumeFire + 0.2F, 1F / (this.rand.nextFloat() * 0.4F + 1.2F) + 0.5F);
  		break;
		default: //melee
			if (this.getRNG().nextInt(2) == 0)
			{
				this.playSound(ModSounds.SHIP_HIT, this.getSoundVolume(), this.getSoundPitch());
	        }
			break;
  		}
  	}
  	
  	/** attack particle at attacker
  	 * 
  	 *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
  	 *  vec: 0:distX, 1:distY, 2:distZ, 3:dist sqrt
  	 */
  	public void applyParticleAtAttacker(int type, Entity target, float[] vec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY, this.posZ, vec[0], vec[1], vec[2], true), point);
  			break;
  		case 2:  //heavy cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
  		case 3:  //light aircraft
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
  		case 4:  //heavy aircraft
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  			break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
			break;
  		}
  	}
  	
  	/** special particle at entity
  	 * 
  	 *  type: 0:miss, 1:critical, 2:double hit, 3:triple hit
  	 */
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
  	
  	/** attack particle at target
  	 * 
  	 *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
  	 */
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
  	
  	/** attack particle at target
  	 * 
  	 *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
  	 *  vec: 0:distX, 1:distY, 2:distZ, 3:dist sqrt
  	 */
  	public void applyParticleAtTarget(int type, Entity target, float[] vec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  		
  		switch(type)
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
  	
  	/** set flare on target */
  	public void flareTarget(Entity target)
  	{
  		//server side, send flare packet
  		if (!this.world.isRemote)
  		{
  	  		if (this.getStateMinor(ID.M.LevelFlare) > 0 && target != null)
  	  		{
  	  			BlockPos pos = new BlockPos(target);
				TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
				CommonProxy.channelG.sendToAllAround(new S2CInputPackets(S2CInputPackets.PID.FlareEffect, pos.getX(), pos.getY(), pos.getZ()), point);
  	  		}
  		}
  	}
  	
  	public short getShipClass()
  	{
		return (short) getStateMinor(ID.M.ShipClass);
	}
  	
	public int getTextureID()
	{
		return this.getShipClass();
	}
	
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
	
  	/** for boss hp bar display */
  	@Override
    public boolean isNonBoss()
    {
        return this.scaleLevel < 2;
    }
  	
  	@Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        
        if (this.bossInfo != null) this.bossInfo.addPlayer(player);
    }

  	@Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        
        if (this.bossInfo != null) this.bossInfo.removePlayer(player);
    }
  	
  	@Override
    protected void updateAITasks()
    {
  		if (this.bossInfo != null) this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }
  	
	@Override
	public Random getRand()
	{
		return this.rand;
	}
	
	@Override
	public double getShipFloatingDepth()
	{
		return 0.3D + this.scaleLevel * 0.05D;
	}

	@Override
	public void setShipFloatingDepth(double par1) {}
	
  	
}
