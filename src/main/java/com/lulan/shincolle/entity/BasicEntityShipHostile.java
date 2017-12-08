package com.lulan.shincolle.entity;

import java.util.HashMap;
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
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CReactPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.reference.unitclass.MissileData;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
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
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public abstract class BasicEntityShipHostile extends EntityMob implements IShipCannonAttack, IShipFloating, IShipCustomTexture
{
	
	//attributes
	protected static final IAttribute MAX_HP = (new RangedAttribute((IAttribute)null, "generic.maxHealth", 4D, 0D, 30000D)).setDescription("Max Health").setShouldWatch(true);
    protected double ShipDepth;			//水深, 用於水中高度判定
	protected HashMap<Integer, Integer> BuffMap;
	protected HashMap<Integer, int[]> AttackEffectMap;
	protected MissileData MissileData;
	/**
	 * ship attributes: hp, def, atk, ...
	 */
	protected Attrs shipAttrs;
	
    //model display
    /** emotion state: 0:State 1:Emotion 2:Emotion2 3:HPState 4:State2 5:AttackPhase 6:Emotion3 */
    protected int[] stateEmotion;
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
		this.canDrop = true;
		this.rotateAngle = new float[] {0F, 0F, 0F};
		this.scaleLevel = 0;
        this.startEmotion = 0;
        this.startEmotion2 = 0;
        this.headTilt = false;
        this.initScale = false;
        this.BuffMap = new HashMap<Integer, Integer>();
		this.shipAttrs = new Attrs();
		this.MissileData = new MissileData();
        
		//model display
        this.soundHurtDelay = 0;
        this.stateEmotion = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
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
		
		//add AI
		this.tasks.addTask(0, new EntityAIShipFloating(this));					//0111
		this.tasks.addTask(21, new EntityAIShipOpenDoor(this, true));			//0000
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
		//calc ship attrs
		this.shipAttrs = new Attrs(this.getShipClass());
		this.calcShipAttributes(9);
        
		//renew health
		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
	}
	
	/**
	 * calc ship attributes
	 * 
	 * parms:
	 *   type: bit flags: EDCBA, all = 3
	 *         A(1):  recalc raw attrs
	 *         B(2):  --
	 *         C(4):  --
	 *         D(8):  recalc potion buff
	 *         E(16): --
	 *         
	 *   sync: send sync packet to client
	 */
	public void calcShipAttributes(int flag)
	{
		//null check
		if (this.shipAttrs == null) this.shipAttrs = new AttrsAdv(this.getShipClass());
		
		this.stepHeight = 1F + this.getScaleLevel();
		
		//recalc raw attrs
		if ((flag & 1) == 1)
		{
			BuffHelper.updateAttrsRawHostile(this.shipAttrs, this.getScaleLevel(), this.getShipClass());
		}
		//recalc potion buff
		if ((flag & 8) == 8)
		{
			BuffHelper.updateBuffPotion(this);
		}
		
  		//apply all buff to raw attrs
		BuffHelper.applyBuffOnAttrsHostile(this);
		
		//apply attrs to entity
        this.getEntityAttribute(MAX_HP).setBaseValue(this.shipAttrs.getAttrsRaw(ID.Attrs.HP));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.shipAttrs.getAttrsRaw(ID.Attrs.MOV));
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(this.shipAttrs.getAttrsRaw(ID.Attrs.KB));
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
			
			//set something at server side
			this.initAttrsServerPost();
			
			//set drop
			this.dropItem = new ItemStack(ModItems.ShipSpawnEgg, 1, getStateMinor(ID.M.ShipClass)+2);
		}
	}
	
	/** init misc attrs like AttackEffectMap */
	protected void initAttrsServerPost() {};
	
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
	public int getStateEmotion(int id)
	{
		return stateEmotion[id];
	}

	@Override
	public void setStateEmotion(int id, int value, boolean sync)
	{
		stateEmotion[id] = value;
		
		if (sync && !world.isRemote)
		{
			this.sendSyncPacket(4);
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
			//treat death as no fuel
			if (this.isDead || this.deathTime > 0) return true;
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
    public boolean attackEntityFrom(DamageSource source, float atk)
	{
		if (this.world.isRemote) return false;
		
		//set hurt face
    	this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
    	
    	boolean checkDEF = true;
    	
		//damage disabled
		if (source == DamageSource.inWall || source == DamageSource.starve ||
			source == DamageSource.cactus || source == DamageSource.fall)
		{
			return false;
		}
		//damage ignore def value
		else if (source == DamageSource.magic || source == DamageSource.dragonBreath)
		{
			//ignore atk < 1% max hp
			if (atk < this.getMaxHealth() * 0.01F) return false;
			return super.attackEntityFrom(source, atk);
		}
		//out of world
		else if (source == DamageSource.outOfWorld)
		{
			//取消坐下動作
			this.setDead();
        	return false;
		}
		
		//check attacker is potion
		float patk = BuffHelper.getPotionDamage(this, source, atk);
		
		if (patk > 0F)
		{
			atk = patk;
			checkDEF = false;
		}
		
        //無敵的entity傷害無效
		if (this.isEntityInvulnerable(source))
		{
            return false;
        }
		else if (source.getEntity() != null)
		{
			Entity attacker = source.getEntity();
			
			//不會對自己造成傷害, 可免疫毒/掉落/窒息等傷害 (此為自己對自己造成傷害)
			if (attacker.equals(this)) return false;
			
			//進行dodge計算
			float dist = (float) this.getDistanceSqToEntity(attacker);
			
			if (CombatHelper.canDodge(this, dist))
			{
				return false;
			}
			
			//進行def計算
			float reducedAtk = atk;
			
			if (checkDEF)
			{
				reducedAtk = CombatHelper.applyDamageReduceByDEF(this.rand, this.shipAttrs, reducedAtk);
			}
			
			//check resist potion
			reducedAtk = BuffHelper.applyBuffOnDamageByResist(this, source, reducedAtk);

			//check night vision potion
			reducedAtk = BuffHelper.applyBuffOnDamageByLight(this, source, reducedAtk);
			
			//tweak min damage
	        if (reducedAtk < 1F && reducedAtk > 0F) reducedAtk = 1F;
	        else if (reducedAtk <= 0F) reducedAtk = 0F;

			//設置revenge target
			this.setEntityRevengeTarget(attacker);
			this.setEntityRevengeTime();
			
	  		//show emotes
	  		if (this.rand.nextInt(5) == 0)
	  		{
				applyEmotesReaction(2);
	  		}
	  		
            return super.attackEntityFrom(source, reducedAtk);
        }
		
		return false;
	}

	@Override
	public boolean attackEntityWithAmmo(Entity target)
	{
		//get attack value
  		float atk = getAttackBaseDamage(1, target);
		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
      
        //play cannon fire sound at attacker
        applySoundAtAttacker(1, target);
	    applyParticleAtAttacker(1, target, distVec);
	    
	    //roll miss, cri, dhit, thit
	    atk = CombatHelper.applyCombatRateToDamage(this, target, true, (float)distVec.d, atk);
  		
  		//damage limit on player target
	    atk = CombatHelper.applyDamageReduceOnPlayer(target, atk);
  		
  		//check friendly fire
		if (!TeamHelper.doFriendlyFire(this, target)) atk = 0F;
	    
		//確認攻擊是否成功
		boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);

	    //if attack success
	    if (isTargetHurt)
	    {
	    	BuffHelper.applyBuffOnTarget(target, this.getAttackEffectMap());
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
		
		//missile type
		float launchPos = (float) posY + height * 0.5F;
		int moveType = CombatHelper.calcMissileMoveType(this, target.posY, 2);
		if (moveType == 0) launchPos = (float) posY + height * 0.3F;
		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
		
		//play attack effect
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
        MissileData md = this.getMissileData(2);
        float[] data = new float[] {atk, kbValue, launchPos, tarX, tarY+target.height*0.1F, tarZ, 160, 0.25F, md.vel0, md.accY1, md.accY2};
		EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
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
	public float getMoveSpeed()
	{
		return this.shipAttrs.getMoveSpeed();
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
		return 30000;
	}

	@Override
	public int getAmmoHeavy()
	{
		return 30000;
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
	 *        4: emotion
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
			case 4:
				pid = S2CEntitySync.PID.SyncEntity_Emo;
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
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, this.getEntityId(), this.world.provider.getDimension()));
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
					ParticleHelper.spawnAttackParticleAt(this.posX + motX*3D, this.posY + 0.4D, this.posZ + motZ*3D, 
							-motX, this.width, -motZ, (byte)47);
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
            
        	//check every 16 ticks
        	if ((ticksExisted & 15) == 0)
        	{
        		//update target
            	TargetHelper.updateTarget(this);
            	
            	//get target from vanilla target AI
            	if (this.getAttackTarget() != null)
            	{
            		this.setEntityTarget(this.getAttackTarget());
            	}
            	
        		if (this.isEntityAlive())
        		{
                	//update potion buff
            		BuffHelper.convertPotionToBuffMap(this);
            		this.calcShipAttributes(8);
        		}

            	//check every 32 ticks
            	if ((ticksExisted & 31) == 0)
            	{
            		if (this.isEntityAlive())
            		{
            			//apply potion effects on ticks
            			BuffHelper.applyBuffOnTicks(this);
            		}
            		
            		//every 64 ticks
        			if ((this.ticksExisted & 63) == 0)
        			{
        				//roll random emotion
        				updateEmotionState();
        				
                		//every 128 ticks
            			if ((this.ticksExisted & 127) == 0)
            			{
            				if (this.isEntityAlive())
                    		{
            					//update buff to attrs
            					this.calcShipAttributes(31);
                    		}
            				
            				//check every 256 ticks
                        	if ((this.ticksExisted & 255) == 0)
                        	{
                        		//set air value
                        		if (this.getAir() < 300)
                        		{
                                	setAir(300);
                                }
                        		
                        		if (this.isEntityAlive()) applyEmotesReaction(4);
                        	}//end every 256 ticks
            			}//end every 128 ticks
                	}//end every 64 ticks
            	}//end every 32 ticks
        	}//end every 16 ticks
        }//end server side
        //client side
        else
        {
        	super.onLivingUpdate();
        	
        	if ((this.ticksExisted & 15) == 0)
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
    		}//end 16 ticks
        }//end client side
	}
	
  	//update hp state, SERVER SIDE ONLY!
  	protected void updateEmotionState()
  	{
  		float hpState = this.getHealth() / this.getMaxHealth();
		
		//check hp state
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
		
		//roll emtion: hungry > T_T > bored
		if (getStateFlag(ID.F.NoFuel))
		{
			if (this.getStateEmotion(ID.S.Emotion) != ID.Emotion.HUNGRY)
			{
				this.setStateEmotion(ID.S.Emotion, ID.Emotion.HUNGRY, false);
			}
		}
		else
		{
			if (hpState < 0.35F)
			{
    			if (this.getStateEmotion(ID.S.Emotion) != ID.Emotion.T_T)
    			{
    				this.setStateEmotion(ID.S.Emotion, ID.Emotion.T_T, false);
    			}			
    		}
			else
			{
				//random Emotion
				switch (this.getStateEmotion(ID.S.Emotion))
				{
				case ID.Emotion.NORMAL:		//if normal, 25% to bored
					if (this.getRNG().nextInt(4) == 0)
						this.setStateEmotion(ID.S.Emotion, ID.Emotion.BORED, false);
				break;
				default:					//other, 50% return normal
					if (this.getRNG().nextInt(2) == 0)
						this.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
				break;
				}
				
				//random Emotion4
				switch (this.getStateEmotion(ID.S.Emotion4))
				{
				case ID.Emotion.NORMAL:		//if normal, 33% to bored
					if (this.getRNG().nextInt(3) == 0)
						this.setStateEmotion(ID.S.Emotion4, ID.Emotion.BORED, false);
				break;
				default:					//other, 50% return normal
					if (this.getRNG().nextInt(2) == 0)
						this.setStateEmotion(ID.S.Emotion4, ID.Emotion.NORMAL, false);
				break;
				}
			}
		}
		
		//send sync packet
		this.sendSyncPacket(4);
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
  			return CombatHelper.modDamageByAdditionAttrs(this, target, this.shipAttrs.getAttackDamage(), 0);
  		case 2:  //heavy cannon
  			return this.shipAttrs.getAttackDamageHeavy();
  		case 3:  //light aircraft
  			return this.shipAttrs.getAttackDamageAir();
  		case 4:  //heavy aircraft
  			return this.shipAttrs.getAttackDamageAirHeavy();
		default: //melee
			return this.shipAttrs.getAttackDamage() * 0.125F;
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
  	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY, this.posZ, distVec.x, distVec.y, distVec.z, true), point);
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
  	public void applyParticleAtTarget(int type, Entity target, Dist4d distVec)
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
				CommonProxy.channelI.sendToAllAround(new S2CReactPackets(S2CReactPackets.PID.FlareEffect, pos.getX(), pos.getY(), pos.getZ()), point);
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
	
	@Override
    protected void onDeathUpdate()
    {
        ++this.deathTime;
        
    	//spawn smoke
    	if (this.world.isRemote)
    	{
        	if ((this.ticksExisted & 3) == 0)
        	{
        		int maxpar = (int)((3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 1.8F);
        		double range = this.width * 1.2D;
        		for (int i = 0; i < maxpar; i++)
        		ParticleHelper.spawnAttackParticleAt(posX-range+this.rand.nextDouble()*range*2D, posY+0.3D+this.rand.nextDouble()*0.3D, posZ-range+this.rand.nextDouble()*range*2D, 1D+this.scaleLevel, 0.0D, 0D, (byte)43);
        	}
    	}
        
        if (this.deathTime == ConfigHandler.deathMaxTick)
        {
        	//spawn ship egg
    		if (!this.world.isRemote && this.canDrop && this.world.getGameRules().getBoolean("doMobLoot"))
    		{
    			//set drop flag to false
    			this.canDrop = false;
    			
    			ItemStack bossEgg = this.getDropEgg();
    			
    			if (bossEgg != null)
    			{
    				BasicEntityItem entityItem1 = new BasicEntityItem(this.world, this.posX, this.posY+0.5D, this.posZ, bossEgg);
    				this.world.spawnEntity(entityItem1);
    			}
    		}	
    		
    		//spawn xp orb
            if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")))
            {
                int i = this.getExperiencePoints(this.attackingPlayer);
                i = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
                while (i > 0)
                {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
                }
            }

            this.setDead();

            for (int k = 0; k < 20; ++k)
            {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1, new int[0]);
            }
        }
        else if (this.deathTime > ConfigHandler.deathMaxTick && !this.isDead)
        {
        	this.setDead();
        }
    }
	
	@Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 10 + this.scaleLevel * 30 + this.rand.nextInt(1 + this.scaleLevel * 30);
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
	
	//no use for now
	@Override
	public int getStateTimer(int id)
	{
		return 0;
	}

	//no use for now
	@Override
	public void setStateTimer(int id, int value)
	{
	}

	@Override
	public HashMap<Integer, Integer> getBuffMap()
	{
		if (this.BuffMap != null) return this.BuffMap;
		return new HashMap<Integer, Integer>();
	}

	@Override
	public void setBuffMap(HashMap<Integer, Integer> map)
	{
		if (map != null) this.BuffMap = map;
	}
	
	@Override
	public Attrs getAttrs()
	{
		return this.shipAttrs;
	}
	
	@Override
	public void setAttrs(Attrs data)
	{
		this.shipAttrs = data;
	}

	//apply heal effect
	@Override
    public void heal(float healAmount)
    {
		//server side
		if (!this.world.isRemote)
		{
			//apply heal particle, server side only
  			TargetPoint tp = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 23, 0D, 0.1D, 0D), tp);
		
  			//potion modify heal value (splash and cloud potion only)
  			healAmount = BuffHelper.applyBuffOnHeal(this, healAmount);
		}
		
		super.heal(healAmount * this.getAttrs().getAttrsBuffed(ID.Attrs.HPRES));
    }
	
	@Override
	public void setUpdateFlag(int id, boolean value) {}

	@Override
	public boolean getUpdateFlag(int id)
	{
		return false;
	}
	
	@Override
	public HashMap<Integer, int[]> getAttackEffectMap()
	{
		if (this.AttackEffectMap != null) return this.AttackEffectMap;
		return new HashMap<Integer, int[]>();
	}

	@Override
	public void setAttackEffectMap(HashMap<Integer, int[]> map)
	{
		this.AttackEffectMap = map;
	}
	
	@Override
	public MissileData getMissileData(int type)
	{
		return this.MissileData;
	}
	
	@Override
	public void setMissileData(int type, MissileData data) {}
  	
	
}