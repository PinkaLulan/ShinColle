package com.lulan.shincolle.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.ai.EntityAIShipAttackOnCollide;
import com.lulan.shincolle.ai.EntityAIShipFlee;
import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipFollowOwner;
import com.lulan.shincolle.ai.EntityAIShipGuarding;
import com.lulan.shincolle.ai.EntityAIShipLookIdle;
import com.lulan.shincolle.ai.EntityAIShipOpenDoor;
import com.lulan.shincolle.ai.EntityAIShipRangeTarget;
import com.lulan.shincolle.ai.EntityAIShipRevengeTarget;
import com.lulan.shincolle.ai.EntityAIShipSit;
import com.lulan.shincolle.ai.EntityAIShipWander;
import com.lulan.shincolle.ai.EntityAIShipWatchClosest;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.capability.CapaShipSavedValues;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.client.render.IShipCustomTexture;
import com.lulan.shincolle.crafting.EquipCalc;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityShipFishingHook;
import com.lulan.shincolle.entity.transport.EntityTransportWa;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.intermod.MetamorphHelper;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CGUIPackets;
import com.lulan.shincolle.network.S2CReactPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.Enums.BodyHeight;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.reference.unitclass.MissileData;
import com.lulan.shincolle.server.CacheDataShip;
import com.lulan.shincolle.utility.BlockHelper;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.InteractHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TaskHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

/**SHIP DATA <br>
 * Explanation in crafting/ShipCalc.class
 */
public abstract class BasicEntityShip extends EntityTameable implements IShipCannonAttack, IShipGuardian, IShipFloating, IShipCustomTexture, IShipMorph
{

	//basic attribute
	protected static final IAttribute MAX_HP = (new RangedAttribute((IAttribute)null, "generic.maxHealth", 4D, 0D, 30000D)).setDescription("Max Health").setShouldWatch(true);
	
	protected CapaShipInventory itemHandler;
	protected ShipPathNavigate shipNavigator;	//水空移動用navigator
	protected ShipMoveHelper shipMoveHelper;
	protected EntityLivingBase aiTarget;		//target for AI
	protected Entity guardedEntity;				//guarding target
	protected Entity atkTarget;					//attack target
	protected Entity rvgTarget;					//revenge target
	public EntityShipFishingHook fishHook;		//fishing hook
	
	//for AI calc
	protected double ShipDepth;			//水深, 用於水中高度判定
	protected double ShipPrevX;			//ship posX 5 sec ago
	protected double ShipPrevY;			//ship posY 5 sec ago
	protected double ShipPrevZ;			//ship posZ 5 sec ago
	/**
	 * ship attributes: hp, def, atk, ...
	 */
	protected AttrsAdv shipAttrs;
	/** minor states, index by {@link ID.M} */
	protected int[] StateMinor;
	/** timer array, index by {@link ID.T} */
	protected int[] StateTimer;
	/** EntityState, index by {@link ID.S} */
	protected int[] StateEmotion;
	/** EntityFlag, index by {@link ID.F} */
	protected boolean[] StateFlag;
	/** BodyHeightRange: */
	protected byte[] BodyHeightStand;
	protected byte[] BodyHeightSit;
	/** ModelPos: posX, posY, posZ, scale (in ship inventory) */
	protected float[] ModelPos;
	/** Update Flag, index by {@link ID.UpdateFlag} */
	protected boolean[] UpdateFlag;
	/** waypoints: 0:last wp */
	protected BlockPos[] waypoints;
	/** owner name */
	public String ownerName;
	/** unit names */
	public ArrayList<String> unitNames;
	/** attack attributes */
	protected HashMap<Integer, Integer> BuffMap;
	protected HashMap<Integer, int[]> AttackEffectMap;
	protected MissileData[] MissileData;
	
	//for model render
	protected float[] rotateAngle;		//模型旋轉角度, 用於手持物品render
	
	//for initial
	private boolean initAI, initWaitAI;	//init flag
	private boolean isUpdated;			//updated ship id/owner id tag
	private int updateTime = 16;		//update check interval
	
	//chunk loader
	private Ticket chunkTicket;
	private Set<ChunkPos> chunks;
	
	//misc flags
	public static boolean stopAI = false;  //stop onUpdate, onLivingUpdate (for all ship entity)
	
	//for inter-mod
	protected boolean isMorph = false;		//is a morph entity, for Metamorph mod
	protected EntityPlayer morphHost;
	
	
	public BasicEntityShip(World world)
	{
		super(world);
		this.ignoreFrustumCheck = true;  //即使不在視線內一樣render
		this.maxHurtResistantTime = 2;   //受傷無敵時間降為2 ticks
		this.ownerName = "";
		this.unitNames = new ArrayList<String>();
		
		//init value
		this.itemHandler = new CapaShipInventory(CapaShipInventory.SlotMax, this);
		this.isImmuneToFire = true;	//set ship immune to lava
		this.StateMinor = new int[] {1,  0,  0,  40, 0,
				                     0,  0,  0,  0,  3,
				                     3,  12, 35, 1,  -1,
				                     -1, -1, 0,  -1, 0,
				                     0,  -1, -1, -1, 0,
				                     0,  0,  0,  0,  0,
				                     60, 0,  10, 0,  0,
				                     -1, 0,  0,  0,  0,
				                     -1, -1, -1, 0,  0
				                    };
		this.StateTimer = new int[21];
		this.StateEmotion = new int[8];
		this.StateFlag = new boolean[] {false, false, false, false, true,
				                        true, true, true, false, true,
								        true, false, true, true, true,
								        true, true, true, true, false,
								        false, false, true, true, false,
								        true, false
								       };
		this.UpdateFlag = new boolean[8];
		this.BodyHeightStand = new byte[] {92, 78, 73, 58, 47, 37};
		this.BodyHeightSit = new byte[] {64, 49, 44, 29, 23, 12};
		this.ModelPos = new float[] {0F, 0F, 0F, 50F};
		this.waypoints = new BlockPos[] {BlockPos.ORIGIN};
		this.BuffMap = new HashMap<Integer, Integer>();
		this.resetMissileData();
		
		//for AI
		this.ShipDepth = 0D;				//water block above ship (within ship position)
		this.ShipPrevX = posX;				//ship position 5 sec ago
		this.ShipPrevY = posY;
		this.ShipPrevZ = posZ;
		this.stepHeight = 1F;
		
		//for render
		this.rotateAngle = new float[3];	//model rotate angle (right hand)
		
		//for init
		this.initAI = false;				//normal init
		this.initWaitAI = false;			//slow init after player entity loaded
		this.isUpdated = false;
		
		//chunk loader
		this.chunkTicket = null;
		this.chunks = null;
		
		//choice random sensitive body part
		randomSensitiveBody();
	}
	
	@Override
	protected boolean canDespawn()
    {
        return false;
    }
	
	@Override
	public boolean isEntityInvulnerable(DamageSource source)
	{
        return StateTimer[ID.T.ImmuneTime] > 0;
    }
	
	@Override
	public boolean isBurning()
	{	//display fire effect
		return this.getStateEmotion(ID.S.HPState) == ID.HPState.HEAVY;
	}
	
	@Override
	public boolean isJumping()
	{
		return this.isJumping;
	}
	
	@Override
	public float getEyeHeight()
	{
		return this.height * 0.8F;
	}
	
    /**
     * Returns true if this thing is named
     */
	@Override
    public boolean hasCustomName()
    {
		return super.hasCustomName();
    }
	
	/** init values, called in the end of constructor */
	protected void postInit()
	{
		this.shipNavigator = new ShipPathNavigate(this);
		this.shipMoveHelper = new ShipMoveHelper(this, 60F);
		this.shipAttrs = new AttrsAdv(this.getShipClass());
	}
	
	//check world time is 0~23 hour, -1 for fail
	private int getWorldHourTime()
	{
		long time = this.world.provider.getWorldTime();
    	int checkTime = (int) (time % 1000L);
    	
    	if (checkTime == 0)
    	{
    		return (int) (time / 1000L) % 24;
    	}
    	
    	return -1;
	}
	
	/**
	 * type: 0:idle, 1:hit, 2:hurt, 3:dead, 4:marry, 5:knockback, 6:item, 7:feed, 10~33:timekeep
	 */
    @Nullable
    public static SoundEvent getCustomSound(int type, BasicEntityShip ship)
    {
    	//get custom sound rate
		int key = ship.getShipClass() + 2;
		float[] rate = ConfigHandler.configSound.SOUNDRATE.get(key);
		int typeKey = key * 100 + type;
		int typeTemp = type;
		
		//if timekeeping sound
		if (type >= 10 && type <= 33)
		{
			type = 8;
		}
		
		//has custom sound
		if (rate != null && rate[type] > 0.01F)
		{
			SoundEvent sound = ModSounds.CUSTOM_SOUND.get(typeKey);
			
			if (sound != null && ship.rand.nextFloat() < rate[type])
			{
				return sound;
			}
		}
		
		//no custom sound, return default sound
		switch (typeTemp)
		{
		case 0:
			return ModSounds.SHIP_IDLE;
		case 1:
			return ModSounds.SHIP_HIT;
		case 2:
			return ModSounds.SHIP_HURT;
		case 3:
			return ModSounds.SHIP_DEATH;
		case 4:
			return ModSounds.SHIP_MARRY;
		case 5:
			return ModSounds.SHIP_KNOCKBACK;
		case 6:
			return ModSounds.SHIP_ITEM;
		case 7:
			return ModSounds.SHIP_FEED;
		case 10:
			return ModSounds.SHIP_TIME0;
		case 11:
			return ModSounds.SHIP_TIME1;
		case 12:
			return ModSounds.SHIP_TIME2;
		case 13:
			return ModSounds.SHIP_TIME3;
		case 14:
			return ModSounds.SHIP_TIME4;
		case 15:
			return ModSounds.SHIP_TIME5;
		case 16:
			return ModSounds.SHIP_TIME6;
		case 17:
			return ModSounds.SHIP_TIME7;
		case 18:
			return ModSounds.SHIP_TIME8;
		case 19:
			return ModSounds.SHIP_TIME9;
		case 20:
			return ModSounds.SHIP_TIME10;
		case 21:
			return ModSounds.SHIP_TIME11;
		case 22:
			return ModSounds.SHIP_TIME12;
		case 23:
			return ModSounds.SHIP_TIME13;
		case 24:
			return ModSounds.SHIP_TIME14;
		case 25:
			return ModSounds.SHIP_TIME15;
		case 26:
			return ModSounds.SHIP_TIME16;
		case 27:
			return ModSounds.SHIP_TIME17;
		case 28:
			return ModSounds.SHIP_TIME18;
		case 29:
			return ModSounds.SHIP_TIME19;
		case 30:
			return ModSounds.SHIP_TIME20;
		case 31:
			return ModSounds.SHIP_TIME21;
		case 32:
			return ModSounds.SHIP_TIME22;
		case 33:
			return ModSounds.SHIP_TIME23;
		}
		
		return null;
    }
    
    //音量大小
    @Override
	protected float getSoundVolume()
    {
        return ConfigHandler.volumeShip;
    }
    
    @Override
    protected float getSoundPitch()
    {
        return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F;
    }
	
    @Override
    @Nullable
    protected SoundEvent getAmbientSound()
    {
		return getCustomSound(0, this);
    }
    
    @Override
    @Nullable
    protected SoundEvent getHurtSound()
    {
    	return getCustomSound(2, this);
    }
    
    @Override
    @Nullable
    protected SoundEvent getDeathSound()
    {
        return getCustomSound(3, this);
    }
    
    /**
     * Plays living's sound at its position
     */
	@Override
    public void playLivingSound()
    {
		//30% play sound
		if (this.getStateFlag(ID.F.NoFuel) || this.rand.nextInt(10) > 3) return;
		
		//get sound event
		SoundEvent sound = null;
		
		//married ship
		if (this.getStateFlag(ID.F.IsMarried))
		{
			if (rand.nextInt(5) == 0)
			{
				sound = getCustomSound(4, this);
			}
			else
			{
				sound = getAmbientSound();
			}
		}
		//normal ship
		else
		{
			sound = getAmbientSound();
		}
		
		//play sound
		if (sound != null)
		{
			this.playSound(sound, this.getSoundVolume(), this.getSoundPitch());
		}
    }
	
	//play hurt sound with sound cooldown
	@Override
    protected void playHurtSound(DamageSource source)
    {
    	if (this.StateTimer[ID.T.SoundTime] <= 0)
    	{
    		this.StateTimer[ID.T.SoundTime] = 20 + this.getRNG().nextInt(30);
    		super.playHurtSound(source);
    	}
    }
	
	//timekeeping method
	protected void playTimeSound(int hour)
	{
		SoundEvent sound = this.getCustomSound(hour + 10, this);

		//play sound
		if (sound != null)
		{
			this.playSound(sound, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1F);
		}
	}

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
	public EntityAgeable createChild(EntityAgeable entity)
	{
		return null;
	}

	//setup AI
	protected void setAIList()
	{
		//high priority
		this.tasks.addTask(1, new EntityAIShipSit(this));						//0111
		this.tasks.addTask(2, new EntityAIShipFlee(this));						//0111
		this.tasks.addTask(3, new EntityAIShipGuarding(this));					//0111
		this.tasks.addTask(4, new EntityAIShipFollowOwner(this));				//0111
		this.tasks.addTask(5, new EntityAIShipOpenDoor(this, true));			//0000
		
		//use melee attack (mux bit: melee:0100, air:0010, ammo:0001)
		if (this.getStateFlag(ID.F.UseMelee))
		{
			this.tasks.addTask(15, new EntityAIShipAttackOnCollide(this, 1D));	//0100
		}
		
		//idle AI
		this.tasks.addTask(23, new EntityAIShipFloating(this));					//0111
		this.tasks.addTask(24, new EntityAIShipWander(this, 10, 5, 0.8D));		//0111
		this.tasks.addTask(25, new EntityAIShipWatchClosest(this,
										EntityPlayer.class, 4F, 0.06F));		//0000
		this.tasks.addTask(26, new EntityAIShipLookIdle(this));					//0000
	}
	
	//setup target AI
	public void setAITargetList()
	{
		//passive target AI
		if (this.getStateFlag(ID.F.PassiveAI))
		{
			this.targetTasks.addTask(1, new EntityAIShipRevengeTarget(this));
		}
		//active target AI
		else
		{
			this.targetTasks.addTask(1, new EntityAIShipRevengeTarget(this));
			this.targetTasks.addTask(5, new EntityAIShipRangeTarget(this, Entity.class));
		}
	}

	//clear AI
	protected void clearAITasks()
	{
		tasks.taskEntries.clear();
	}
	
	//clear target AI
	protected void clearAITargetTasks()
	{
		this.setAttackTarget(null);
		this.setEntityTarget(null);
		targetTasks.taskEntries.clear();
	}
	
	/**
	 * FIX: mounts and rider be pushed while saving and loading from disk
	 */
	@Override
    public boolean writeToNBTOptional(NBTTagCompound nbt)
    {
		//is guarding and riding, check dist to guard pos
        if (this.isRiding() && this.dimension == this.getGuardedPos(3) &&
        	this.getGuardedPos(1) > 0)
        {
        	float dx = (float) (this.posX - this.getGuardedPos(0));
        	float dy = (float) (this.posY - this.getGuardedPos(1));
        	float dz = (float) (this.posZ - this.getGuardedPos(2));
        	float dist = dx*dx+dy*dy+dz*dz;
        	
        	if (dist > 256F)
        	{
        		this.dismountRidingEntity();
        		this.setPosition(this.getGuardedPos(0)+0.5D, this.getGuardedPos(1)+0.5D, this.getGuardedPos(2)+0.5D);
        	
        		if (!this.world.isRemote) this.sendSyncPacket(S2CEntitySync.PID.SyncEntity_PosRot, true);
        	}
        }
        
        //is riding player, dismount while saving/loading
        if (this.getRidingEntity() instanceof EntityPlayer)
        {
        	this.dismountRidingEntity();
        	this.sendSyncPacketRiders();
        }
        
        return super.writeToNBTOptional(nbt);
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		//load ship attributes
		CapaShipSavedValues.loadNBTData(nbt, this);
		
		//load ship inventory
        if (nbt.hasKey(CapaShipInventory.InvName))
        {
        	itemHandler.deserializeNBT((NBTTagCompound) nbt.getTag(CapaShipInventory.InvName));
        }
        
        //change rider position on chunk loading to fix "Wrong Location!" bug
        if (this.world == null || (!this.world.isRemote && this.ticksExisted <= 0))
        {
        	if (nbt.hasKey("Passengers", Constants.NBT.TAG_LIST))
            {
        		NBTTagList list = nbt.getTagList("Passengers", Constants.NBT.TAG_COMPOUND);
        		
                for (int i = 0; i < list.tagCount(); ++i)
                {
                	NBTTagCompound rider = list.getCompoundTagAt(i);
                	NBTTagList pos = rider.getTagList("Pos", 6);
                	pos.set(0, new NBTTagDouble(this.posX));
                	pos.set(2, new NBTTagDouble(this.posZ));
                }
            }
        }
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		//load ship attributes
		CapaShipSavedValues.saveNBTData(nbt, this);
		
		//load ship inventory
		nbt.setTag(CapaShipInventory.InvName, itemHandler.serializeNBT());
		
		return nbt;
	}
	
	@Override
	public ShipPathNavigate getShipNavigate()
	{
		return shipNavigator;
	}
	
	@Override
	public ShipMoveHelper getShipMoveHelper()
	{
		return shipMoveHelper;
	}
	
	public EntityLivingBase getAITarget()
	{
		return aiTarget;
	}
	
	/** 1:cannon only, 2:both, 3:aircraft only */
	abstract public int getEquipType();

	@Override
	public int getLevel()
	{
		return StateMinor[ID.M.ShipLevel];
	}
	
	public byte getShipType()
	{
		return (byte)getStateMinor(ID.M.ShipType);
	}
	
	public short getShipClass()
	{
		return (short) getStateMinor(ID.M.ShipClass);
	}
	
	//ShipUID = ship UNIQUE ID
	public int getShipUID()
	{
		return getStateMinor(ID.M.ShipUID);
	}
	
	public int getMorale()
	{
		return this.StateMinor[ID.M.Morale];
	}
	
	//PlayerUID = player UNIQUE ID (not UUID)
	@Override
	public int getPlayerUID()
	{
		return getStateMinor(ID.M.PlayerUID);
	}
	
	@Override
	public int getAmmoLight()
	{
		return this.StateMinor[ID.M.NumAmmoLight];
	}

	@Override
	public int getAmmoHeavy()
	{
		return this.StateMinor[ID.M.NumAmmoHeavy];
	}
	
	@Override
	public int getFaceTick()
	{
		return this.StateTimer[ID.T.FaceTime];
	}
	
	@Override
	public int getHeadTiltTick()
	{
		return this.StateTimer[ID.T.HeadTilt];
	}
	
	@Override
	public int getAttackTick()
	{
		return this.StateTimer[ID.T.AttackTime];
	}
	
	@Override
	public int getAttackTick2()
	{
		return this.StateTimer[ID.T.AttackTime2];
	}
	
	//emotes CD
	public int getEmotesTick()
	{
		return this.StateTimer[ID.T.EmoteDelay];
	}
	
	//last attack time
	public int getCombatTick()
	{
		return this.StateTimer[ID.T.LastCombat];
	}
	
	/** 被pointer item點到的高度, 以百分比值表示 */
	public int getHitHeight()
	{
		return this.StateMinor[ID.M.HitHeight];
	}
	
	/** 被pointer item點到的角度, 0~-360
	 *  front: -180
	 *  back: 0/-360
	 *  right:-90
	 *  left:-270
	 */
	public int getHitAngle()
	{
		return this.StateMinor[ID.M.HitAngle];
	}
	
	@Override
	public int getTickExisted()
	{
		return this.ticksExisted;
	}
	
	@Override
	public float getSwingTime(float partialTick)
	{
		return this.getSwingProgress(partialTick);
	}
	
	@Override
	public boolean getIsRiding()
	{
		return this.isRiding();
	}
	
	@Override
	public boolean getIsLeashed()
	{
		return this.getLeashed();
	}

	@Override
	public boolean getIsSprinting()
	{
		return this.isSprinting() || this.limbSwingAmount > 0.9F;
	}

	@Override
	public boolean getIsSitting()
	{
		return this.isSitting();
	}

	@Override
	public boolean getIsSneaking()
	{
		return this.isSneaking();
	}
	
	@Override
	public Entity getEntityTarget()
	{
		return this.atkTarget;
	}
	
	@Override
	public Entity getEntityRevengeTarget()
	{
		return this.rvgTarget;
	}

	@Override
	public int getEntityRevengeTime()
	{
		return this.StateTimer[ID.T.RevengeTime];
	}
	
	@Override
	public boolean getAttackType(int par1)
	{
		return this.getStateFlag(par1);
	}
	
	@Override
    public float getAIMoveSpeed()
    {
        return getMoveSpeed();
    }
	
	@Override
	public float getMoveSpeed()
	{
		return this.shipAttrs.getMoveSpeed(false);
	}
	
	@Override
	public float getJumpSpeed()
	{
		return 1F;
	}
	
	@Override
	public boolean hasAmmoLight()
	{
		return StateMinor[ID.M.NumAmmoLight] >= StateMinor[ID.M.AmmoCon];
	}
	
	@Override
	public boolean hasAmmoHeavy()
	{
		return StateMinor[ID.M.NumAmmoHeavy] >= StateMinor[ID.M.AmmoCon];
	}

	@Override
	public boolean useAmmoLight()
	{
		return StateFlag[ID.F.UseAmmoLight];
	}

	@Override
	public boolean useAmmoHeavy()
	{
		return StateFlag[ID.F.UseAmmoHeavy];
	}
	
	@Override
	public double getShipDepth()
	{
		return ShipDepth;
	}
	
	@Override
	public double getShipDepth(int type)
	{
		switch (type)
		{
		case 1:
			if (this.getRidingEntity() instanceof IShipEmotion)
			{
				return ((IShipEmotion)this.getRidingEntity()).getShipDepth(0);
			}
			else
			{
				return this.ShipDepth;
			}
		case 2:
			return 0D;
		default:
			return this.ShipDepth;
		}
	}
	
	@Override
	public boolean getStateFlag(int flag)
	{
		//treat death as no fuel
		if (flag == ID.F.NoFuel && (this.isDead || this.deathTime > 0)) return true;
		
		return StateFlag[flag];
	}
	
	public byte getStateFlagI(int flag)
	{	//get flag (byte)
		if (StateFlag[flag])
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public int getStateMinor(int id)
	{
		return StateMinor[id];
	}

	@Override
	public boolean getUpdateFlag(int id)
	{
		return UpdateFlag[id];
	}

	@Override
	public int getStateTimer(int id)
	{
		return StateTimer[id];
	}
	
	@Override
	public int getStateEmotion(int id)
	{
		return StateEmotion[id];
	}
	
	/** get model position in GUI */
	public float[] getModelPos()
	{
		return ModelPos;
	}
	
	/** grudge consumption when IDLE */
	public int getGrudgeConsumption()
	{
		return getStateMinor(ID.M.GrudgeCon);
	}
	
	public int getAmmoConsumption()
	{
		return getStateMinor(ID.M.AmmoCon);
	}
	
	public int getFoodSaturation()
	{
		return getStateMinor(ID.M.Food);
	}
	
	public int getFoodSaturationMax()
	{
		return getStateMinor(ID.M.FoodMax);
	}
	
	public CapaShipInventory getCapaShipInventory()
	{
		return this.itemHandler;
	}
	
	/**
	 * calc ship attributes
	 * 
	 * parms:
	 *   type: bit flags: EDCBA, all = 31
	 *         A(1):  recalc raw attrs
	 *         B(2):  recalc equips
	 *         C(4):  recalc morale buff
	 *         D(8):  recalc potion buff
	 *         E(16): recalc formation buff
	 *         
	 *   sync: send sync packet to client
	 */
	public void calcShipAttributes(int flag, boolean sync)
	{
		//null check
		if (this.shipAttrs == null) this.shipAttrs = new AttrsAdv(this.getShipClass());
		
		//server side
		if (!this.world.isRemote)
		{
			//check morph
			if (this.isMorph)
			{
				//recalc raw attrs
				if ((flag & 1) == 1)
				{
					BuffHelper.updateAttrsRaw(this.shipAttrs, this.getShipClass(), this.getLevel());
					this.calcShipAttributesAddRaw();
					this.setUpdateFlag(ID.FlagUpdate.AttrsRaw, true);
					this.setUpdateFlag(ID.FlagUpdate.AttrsBonus, true);
				}
				//recalc equips
				if ((flag & 2) == 2)
				{
					EquipCalc.updateAttrsEquipOfMorph(this);
					this.calcShipAttributesAddEquip();
					this.setUpdateFlag(ID.FlagUpdate.AttrsEquip, true);
				}
				//recalc potion buff
				if ((flag & 8) == 8)
				{
					BuffHelper.updateBuffPotion(this);
					this.setUpdateFlag(ID.FlagUpdate.AttrsPotion, true);
				}
				
		  		//apply all buff to raw attrs
				BuffHelper.applyBuffOnAttrs(this);
				this.calcShipAttributesAdd();
				this.setUpdateFlag(ID.FlagUpdate.AttrsBuffed, true);
			}
			else
			{
				//recalc raw attrs
				if ((flag & 1) == 1)
				{
					BuffHelper.updateAttrsRaw(this.shipAttrs, this.getShipClass(), this.getLevel());
					this.calcShipAttributesAddRaw();
					this.setUpdateFlag(ID.FlagUpdate.AttrsRaw, true);
				}
				//recalc equips
				if ((flag & 2) == 2)
				{
					EquipCalc.updateAttrsEquip(this);
					this.calcShipAttributesAddEquip();
					this.setUpdateFlag(ID.FlagUpdate.AttrsEquip, true);
				}
				//recalc morale buff
				if ((flag & 4) == 4)
				{
					BuffHelper.updateBuffMorale(this.shipAttrs, this.getMorale());
					this.setUpdateFlag(ID.FlagUpdate.AttrsMorale, true);
				}
				//recalc potion buff
				if ((flag & 8) == 8)
				{
					BuffHelper.updateBuffPotion(this);
					this.setUpdateFlag(ID.FlagUpdate.AttrsPotion, true);
				}
				//recalc formation buff
				if ((flag & 16) == 16)
				{
					BuffHelper.updateBuffFormation(this);
					this.setUpdateFlag(ID.FlagUpdate.AttrsFormation, true);
				}
		  		//apply all buff to raw attrs
				BuffHelper.applyBuffOnAttrs(this);
				this.calcShipAttributesAdd();
				this.setUpdateFlag(ID.FlagUpdate.AttrsBuffed, true);
			}
			
//        	float[] raw = this.shipAttrs.getAttrsRaw();  //TODO debug
//        	float[] eq = this.shipAttrs.getAttrsEquip();
//        	float[] mor = this.shipAttrs.getAttrsMorale();
//        	float[] pot = this.shipAttrs.getAttrsPotion();
//        	float[] form = this.shipAttrs.getAttrsFormation();
//        	float[] buf = this.shipAttrs.getAttrsBuffed();
//        	int idd = ID.Attrs.ATK_L;
//        	LogHelper.debug("BBBBBB "+this.world.isRemote+" "+raw[idd]
//        			+" "+eq[idd]+" "+mor[idd]+" "+pot[idd]
//        			+" "+form[idd]+" "+buf[idd]);
			
			//send sync packets
			if (sync)
			{
				this.sendSyncPacketMinor();
				this.sendSyncPacketAttrs();
			}
		}
		
		//set attrs to entity
		/**
		 * DO NOT SET ATTACK DAMAGE to non-EntityMob!!!!!
		 */
		getEntityAttribute(MAX_HP).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.HP));
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.MOV));
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(this.shipAttrs.getAttrsBuffed(ID.Attrs.KB));
	
		//apply attrs to player
		if (this.morphHost != null)
		{
			this.morphHost.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D + this.shipAttrs.getAttrsBuffed(ID.Attrs.HP) * ConfigHandler.morphHPRatio);
		}
	}
	
	/** calc addition attributes */
	public void calcShipAttributesAdd() {}
	public void calcShipAttributesAddRaw() {}
	public void calcShipAttributesAddEquip() {}
	
	/** reset attack effect map */
	public void calcShipAttributesAddEffect()
	{
		this.AttackEffectMap = new HashMap<Integer, int[]>();
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
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS);
    }
	
	//set next exp value (for client load nbt data, gui display)
	public void setExpNext()
	{
		StateMinor[ID.M.ExpNext] = StateMinor[ID.M.ShipLevel] * ConfigHandler.expMod + ConfigHandler.expMod;
	}
		
	//called when entity exp++
	public void addShipExp(int exp)
	{
		int capLevel = getStateFlag(ID.F.IsMarried) ? 150 : 100;
		
		//check morph
		if (this.isMorph) exp = (int) ((float)exp * ConfigHandler.expGainPlayerSkill);
		
		//apply equip effect
		exp = (int) ((float)exp * this.shipAttrs.getAttrsBuffed(ID.Attrs.XP));
		
		//level is not cap level
		if (StateMinor[ID.M.ShipLevel] != capLevel && StateMinor[ID.M.ShipLevel] < 150)
		{
			StateMinor[ID.M.ExpCurrent] += exp;
			if (StateMinor[ID.M.ExpCurrent] >= StateMinor[ID.M.ExpNext])
			{
				
				//level up sound
				if (this.rand.nextInt(4) == 0)
				{
					this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), 0.75F, 1F);
				}
				else
				{
					this.playSound(ModSounds.SHIP_LEVEL, 0.75F, 1F);
				}
				
				StateMinor[ID.M.ExpCurrent] -= StateMinor[ID.M.ExpNext];	//level up
				StateMinor[ID.M.ExpNext] = (StateMinor[ID.M.ShipLevel] + 2) * ConfigHandler.expMod;
				setShipLevel(++StateMinor[ID.M.ShipLevel], true);
			}
		}	
	}
	
	@Override
	public void setShipDepth(double par1)
	{
		this.ShipDepth = par1;
	}
	
	//called when entity level up
	public void setShipLevel(int par1, boolean update)
	{
		//set level
		if (par1 < 151)
		{
			StateMinor[ID.M.ShipLevel] = par1;
		}
		//update attributes
		if (update)
		{
			this.calcShipAttributes(31, true);
			this.setHealth(this.getMaxHealth());
		}
	}
	
	//prevent player use name tag
	@Override
	public void setCustomNameTag(String str) {}
	
	//custom name tag method
	public void setNameTag(String str)
	{
		super.setCustomNameTag(str);
    }
	
	public void setAITarget(EntityLivingBase target)
	{
		this.aiTarget = target;
	}
	
	//called when a mob die near the entity (used in event handler)
	public void addKills()
	{
		StateMinor[ID.M.Kills]++;
	}
	
	public void addMorale(int value)
	{
		this.StateMinor[ID.M.Morale] += value;
		if (this.StateMinor[ID.M.Morale] < 0) this.StateMinor[ID.M.Morale] = 0;
		else if (this.StateMinor[ID.M.Morale] > 16000) this.StateMinor[ID.M.Morale] = 16000;
	}
	
	public void addAmmoLight(int value)
	{
		this.StateMinor[ID.M.NumAmmoLight] += value;
		if (this.StateMinor[ID.M.NumAmmoLight] < 0) this.StateMinor[ID.M.NumAmmoLight] = 0;
	}
	
	public void addAmmoHeavy(int value)
	{
		this.StateMinor[ID.M.NumAmmoHeavy] += value;
		if (this.StateMinor[ID.M.NumAmmoHeavy] < 0) this.StateMinor[ID.M.NumAmmoHeavy] = 0;
	}
	
	public void setMorale(int value)
	{
		this.StateMinor[ID.M.Morale] = value;
	}
	
	@Override
	public void setAmmoLight(int num)
	{
		this.StateMinor[ID.M.NumAmmoLight] = num;
	}
	
	@Override
	public void setAmmoHeavy(int num)
	{
		this.StateMinor[ID.M.NumAmmoHeavy] = num;
	}
	
	@Override
	public void setStateMinor(int id, int par1)
	{
		//value limit
		switch (id)
		{
		case ID.M.Morale:
			if (par1 < 0) par1 = 0;
		break;
		case ID.M.CraneState:
			//if changed to 1 or 2, check delay
			if (par1 > 0)
			{
				if (getStateTimer(ID.T.CrandDelay) > 0)
				{
					return;
				}
				else
				{
					setStateTimer(ID.T.CrandDelay, 20);
				}
			}
		break;
		}
		
		//set value
		StateMinor[id] = par1;
	}
	
	@Override
	public void setUpdateFlag(int id, boolean par1)
	{
		UpdateFlag[id] = par1;
	}
	
	@Override
	public void setEntitySit(boolean sit)
	{
		this.setSitting(sit);
		
		//若設定為坐下, 則清空路徑跟攻擊目標
		if (sit)
		{
	        this.isJumping = false;
	        this.getShipNavigate().clearPathEntity();
	        this.getNavigator().clearPathEntity();
	        this.setAttackTarget(null);
	        this.setEntityTarget(null);
		}
	}
	
	public void setRiderAndMountSit()
	{
		//set mount sitting
		if (this.getRidingEntity() instanceof BasicEntityShip)
		{
			BasicEntityShip mountShip = (BasicEntityShip) this.getRidingEntity();
			
			mountShip.setEntitySit(this.isSitting());
			
			//special mount: set all rider sit
			if (mountShip.getRidingState() > 0)
			{
				List<Entity> rider = mountShip.getPassengers();
				
				for (Entity r : rider)
				{
					if (r instanceof BasicEntityShip)
					{
						((BasicEntityShip) r).setEntitySit(this.isSitting());
					}
				}
			}
		}
        
		//set all rider sitting
		List<Entity> rider = this.getPassengers();
		
		for (Entity r : rider)
		{
			if (r instanceof BasicEntityShip)
			{
				((BasicEntityShip) r).setEntitySit(this.isSitting());
			}
		}
	}

	//called when load nbt data or GUI click
	@Override
	public void setStateFlag(int id, boolean par1)
	{
		this.StateFlag[id] = par1;
		
		//若修改melee flag, 則reload AI
		if (!this.world.isRemote)
		{
			if (id == ID.F.UseMelee)
			{
				clearAITasks();
	    		setAIList();
	    		
	    		//設定mount的AI
				if (this.getRidingEntity() instanceof BasicEntityMount)
				{
					((BasicEntityMount) this.getRidingEntity()).clearAITasks();
					((BasicEntityMount) this.getRidingEntity()).setAIList();
				}
			}
			else if (id == ID.F.PassiveAI)
			{
				clearAITargetTasks();
				setAITargetList();
			}
		}
	}
	
	//called when load nbt data or GUI click
	public void setStateFlagI(int id, int par1)
	{
		if (par1 > 0)
		{
			setStateFlag(id, true);
		}
		else
		{
			setStateFlag(id, false);
		}
	}
	
	@Override
	public void setStateTimer(int id, int value)
	{
		StateTimer[id] = value;
	}
	
	@Override
	public void setStateEmotion(int id, int value, boolean sync)
	{
		StateEmotion[id] = value;
		
		if (sync && !this.world.isRemote)
		{
			this.sendSyncPacketEmotion();
		}
	}
	
	//emotion start time (CLIENT ONLY), called from model class
	@Override
	public void setFaceTick(int par1)
	{
		this.StateTimer[ID.T.FaceTime] = par1;
	}
	
	@Override
	public void setHeadTiltTick(int par1)
	{
		this.StateTimer[ID.T.HeadTilt] = par1;
	}
	
	@Override
	public void setAttackTick(int par1)
	{
		this.StateTimer[ID.T.AttackTime] = par1;
	}
	
	@Override
	public void setAttackTick2(int par1)
	{
		this.StateTimer[ID.T.AttackTime2] = par1;
	}
	
	//emotes CD
	public void setEmotesTick(int par1)
	{
		this.StateTimer[ID.T.EmoteDelay] = par1;
	}
	
	//last attack time
	public void setCombatTick(int par1)
	{
		this.StateTimer[ID.T.LastCombat] = par1;
	}
	
	/** 被pointer item點到的高度, 以百分比值表示 */
	public void setHitHeight(int par1)
	{
		this.StateMinor[ID.M.HitHeight] = par1;
	}
	
	/** 被pointer item點到的角度, 0~-360
	 *  front: -180
	 *  back: 0/-360
	 *  right:-90
	 *  left:-270
	 */
	public void setHitAngle(int par1)
	{
		this.StateMinor[ID.M.HitAngle] = par1;
	}
	
	public void setShipUID(int par1)
	{
		this.setStateMinor(ID.M.ShipUID, par1);
	}
	
	@Override
	public void setPlayerUID(int par1)
	{
		this.setStateMinor(ID.M.PlayerUID, par1);
	}
	

  	@Override
	public void setEntityTarget(Entity target)
  	{
		this.atkTarget = target;
	}
  	
  	@Override
	public void setEntityRevengeTarget(Entity target)
  	{
		this.rvgTarget = target;
	}
  	
  	@Override
	public void setEntityRevengeTime()
  	{
  		this.StateTimer[ID.T.RevengeTime] = this.ticksExisted;
	}
  	
  	public void setGrudgeConsumption(int par1)
  	{
  		this.setStateMinor(ID.M.GrudgeCon, par1);
  	}
  	
  	public void setAmmoConsumption(int par1)
  	{
  		this.setStateMinor(ID.M.AmmoCon, par1);
  	}
  	
  	public void setFoodSaturation(int par1)
  	{
  		setStateMinor(ID.M.Food, par1);
	}
	
	public void setFoodSaturationMax(int par1)
	{
		setStateMinor(ID.M.FoodMax, par1);
	}
	
	/** send sync packet: sync all data */
	public void sendSyncPacketAll()
	{
		//set update flag
		this.setUpdateFlag(ID.FlagUpdate.AttrsBonus, true);
		this.setUpdateFlag(ID.FlagUpdate.AttrsRaw, true);
		this.setUpdateFlag(ID.FlagUpdate.AttrsEquip, true);
		this.setUpdateFlag(ID.FlagUpdate.AttrsMorale, true);
		this.setUpdateFlag(ID.FlagUpdate.AttrsPotion, true);
		this.setUpdateFlag(ID.FlagUpdate.AttrsFormation, true);
		this.setUpdateFlag(ID.FlagUpdate.AttrsBuffed, true);
		
		this.sendSyncPacket(S2CEntitySync.PID.SyncShip_AllMisc, false);
		this.sendSyncPacket(S2CEntitySync.PID.SyncShip_Attrs, false);
	}
	
	/** send sync packet: attrs */
	public void sendSyncPacketAttrs()
	{
		sendSyncPacket(S2CEntitySync.PID.SyncShip_Attrs, false);
	}
	
	/** send sync packet: attrs */
	public void sendSyncPacketMinor()
	{
		sendSyncPacket(S2CEntitySync.PID.SyncShip_Minor, false);
	}
	
	/** send sync packet: attrs */
	public void sendSyncPacketAllMisc()
	{
		sendSyncPacket(S2CEntitySync.PID.SyncShip_AllMisc, false);
	}

	/** send sync packet: sync formation data */
	public void sendSyncPacketFormationValue()
	{
		sendSyncPacket(S2CEntitySync.PID.SyncShip_Formation, false);
	}
	
	/** send sync packet: sync riders */
	public void sendSyncPacketRiders()
	{
		sendSyncPacket(S2CEntitySync.PID.SyncShip_Riders, true);
	}
	
	/** send sync packet: sync unit name */
	public void sendSyncPacketUnitName()
	{
		sendSyncPacket(S2CEntitySync.PID.SyncShip_UnitName, true);
	}
	
	/** send sync packet: sync buff map */
	public void sendSyncPacketBuffMap()
	{
		sendSyncPacket(S2CEntitySync.PID.SyncShip_Buffmap, false);
	}
	
	/** sync data for GUI display */
	public void sendSyncPacketGUI()
	{
		if (!this.world.isRemote)
		{
			if (this.getPlayerUID() > 0)
			{
				EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
				
				//owner在附近才需要sync
				if (player != null && player.dimension == this.dimension &&
					this.getDistanceToEntity(player) < 64F)
				{
					CommonProxy.channelG.sendTo(new S2CGUIPackets(this), player);
				}
			}
		}
	}
	
	/**
	 * sync data for timer display
	 * 
	 * type:
	 *   0: crane time
	 *   1: mount skill time
	 */
	public void sendSyncPacketTimer(int type)
	{
		switch (type)
		{
		case 0:
			sendSyncPacket(S2CEntitySync.PID.SyncShip_Timer, true);
		break;
		case 1:
			sendSyncPacket(S2CEntitySync.PID.SyncShip_PlayerSkillTimer, false);
		break;
		}
	}
	
	/** sync data for emotion display */
	public void sendSyncPacketEmotion()
	{
		sendSyncPacket(S2CEntitySync.PID.SyncShip_Emo, true);
	}
	
	/** sync data for flag */
	public void sendSyncPacketFlag()
	{
		sendSyncPacket(S2CEntitySync.PID.SyncShip_Flag, true);
	}
	
	/** send sync packet:
	 *  type: 0: all  1: emotion  2: flag  3: minor
	 *  send all: send packet to all around player
	 *  sync emo: sync emotion to all around player
	 */
	public void sendSyncPacket(byte type, boolean sendAll)
	{
		if (!world.isRemote)
		{
			//send to all player
			if (sendAll)
			{
				TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
				CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, type), point);
			}
			else
			{
				EntityPlayerMP player = null;
				
				//for owner, send all data
				if (this.getPlayerUID() > 0)
				{
					player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
				}
				
				//owner在附近才需要sync
				if (player != null && this.getDistanceToEntity(player) <= 64F)
				{
					CommonProxy.channelE.sendTo(new S2CEntitySync(this, type), player);
				}
			}
		}
	}
	
	/**
	 * request server to send sync packet
	 *   0: model display (StateEmotion)
	 *   1: unit name
	 *   2: buff map
	 */
	public void sendSyncRequest(int type)
	{
		if (this.world.isRemote)
		{
			switch (type)
			{
			case 0:		//model display
				if (this.morphHost != null)
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, this.morphHost.getEntityId(), this.world.provider.getDimension()));
				}
				else
				{
					CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, this.getEntityId(), this.world.provider.getDimension()));
				}
			break;
			case 1:		//unit name display
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_UnitName, this.getEntityId(), this.world.provider.getDimension()));
			break;
			case 2:		//buff map
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_Buffmap, this.getEntityId(), this.world.provider.getDimension()));
			break;
			}
		}
	}
	
	/**
	 * 1.9.4:
	 * EnumActionResult:
	 *   PASS:本方法的動作成功, 並且繼續給其他interact方法判定
	 *   FAIL:本方法的動作失敗, 並且禁止其他interact
	 *   SUCCESS:本方法的動作成功, 並且禁止其他interact
	 */
	@Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand)
    {
    	//禁用副手, 死亡時不反應, morph不反應
    	if (hand == EnumHand.OFF_HAND || !this.isEntityAlive() || this.isMorph) return EnumActionResult.FAIL;
    	
    	//server side
    	if (!this.world.isRemote)
    	{
			//use item
			if (stack != null)
			{
				//use name tag, owner only
				if (stack.getItem() == Items.NAME_TAG && TeamHelper.checkSameOwner(player, this))
				{
		            //若該name tag有取名過, 則將名字貼到entity上
					if (stack.hasDisplayName())
					{
						this.setNameTag(stack.getDisplayName());
						return EnumActionResult.SUCCESS;
		            } 
				}
				//use repair bucket
				else if (stack.getItem() == ModItems.BucketRepair)
				{
					if (InteractHelper.interactBucket(this, player, stack)) return EnumActionResult.SUCCESS;
				}
				//use owner paper, owner only
				else if (stack.getItem() == ModItems.OwnerPaper && TeamHelper.checkSameOwner(this, player) && player.isSneaking())
				{
					if (InteractHelper.interactOwnerPaper(this, player, stack)) return EnumActionResult.SUCCESS;
				}
				//use modernization kit
				else if (stack.getItem() == ModItems.ModernKit)
				{
					if (InteractHelper.interactModernKit(this, player, stack)) return EnumActionResult.SUCCESS;
				}
				//use pointer item (caress head mode server side)
				else if (stack.getItem() == ModItems.PointerItem && stack.getMetadata() > 2 && !player.isSneaking())
				{
					InteractHelper.interactPointer(this, player, stack);
					return EnumActionResult.SUCCESS;
				}
				//use kaitai hammer, OWNER and OP only
				else if (stack.getItem() == ModItems.KaitaiHammer && player.isSneaking() &&
						 (TeamHelper.checkSameOwner(this, player) || EntityHelper.checkOP(player)))
				{
					InteractHelper.interactKaitaiHammer(this, player, stack);
					return EnumActionResult.SUCCESS;
				}
				//use wedding ring
				else if (stack.getItem() == ModItems.MarriageRing && !this.getStateFlag(ID.F.IsMarried) && 
						 player.isSneaking() && TeamHelper.checkSameOwner(this, player))
				{
					InteractHelper.interactWeddingRing(this, player, stack);
					return EnumActionResult.SUCCESS;
				}
				//use book
				else if (stack.getItem() == ModItems.TrainingBook)
				{
					if (this.getLevel() < 150)
					{
						int lv = this.getLevel() + 5 + this.rand.nextInt(6);
						int lvcap = this.getStateFlag(ID.F.IsMarried) ? 150 : 100;
						if (lv > lvcap) lv = lvcap;
						
						this.setShipLevel(lv, true);
						
						//level up sound
						this.playSound(ModSounds.SHIP_LEVEL, 0.75F, 1F);
						this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), 0.75F, 1F);
						
						//item--
						if (player != null && !player.capabilities.isCreativeMode)
						{
				            --stack.stackSize;
						}
					}
					
					return EnumActionResult.SUCCESS;
				}
				//use lead: clear path
				else if (stack.getItem() == Items.LEAD)
				{
					this.getShipNavigate().clearPathEntity();
					return EnumActionResult.SUCCESS;
		        }
				//feed
				else if (InteractHelper.interactFeed(this, player, stack))
				{
					return EnumActionResult.SUCCESS;
				}
			}//end item != null
			
			//owner right click
			if (TeamHelper.checkSameOwner(this, player))
			{
				//sneak: open GUI
				if (player.isSneaking())
				{
					FMLNetworkHandler.openGui(player, ShinColle.instance, ID.Gui.SHIPINVENTORY, this.world, this.getEntityId(), 0, 0);
		    		return EnumActionResult.SUCCESS;
				}
				else
				{
					//current item = pointer
					if (EntityHelper.getPointerInUse(player) != null)
					{
						//call sitting method by PointerItem class, not here
					}
					else
					{
						sendSyncPacket(S2CEntitySync.PID.SyncEntity_Rot, true);
						this.setEntitySit(!this.isSitting());
						this.setRiderAndMountSit();
					}
					
					return EnumActionResult.SUCCESS;
				}
			}
    	}//end server side
    	//client side
    	else
    	{
    		if (stack != null)
    		{
    			//use pointer item (caress head mode CLIENT side)
    			if (stack.getItem() == ModItems.PointerItem && stack.getMetadata() > 2 && !player.isSneaking())
    			{
    				this.setHitHeight(CalcHelper.getEntityHitHeightByClientPlayer(this));
    				this.setHitAngle(CalcHelper.getEntityHitSideByClientPlayer(this));
    				this.checkCaressed();
    				
    				//send caress position to server (this method is called every 4 ticks)
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.HitHeight, this.getEntityId(), this.getHitHeight(), this.getHitAngle()));
    			}
    		}
    	}//end client side
    	
		return EnumActionResult.PASS;
    }
    
    @Override
    public boolean canBeLeashedTo(EntityPlayer player)
    {
    	if (! player.world.isRemote)
    	{
    		return TeamHelper.checkSameOwner(this, player);
    	}
        return true;	//client端只回傳true
    }
    
	/**修改移動方法, 使其water跟lava中移動時像是flying entity
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
	@Override
    public void moveEntityWithHeading(float strafe, float forward)
	{
		EntityHelper.moveEntityWithHeading(this, strafe, forward);
    }

	/** update entity 
	 *  在此用onUpdate跟onLivingUpdate區分server跟client update
	 *  for shincolle:
	 *  onUpdate = client update only
	 *  onLivingUpdate = server update only
	 */
	@Override
	public void onUpdate()
	{
		/** BOTH SIDE */
		if (stopAI)
		{
			return;
		}
		
		//check morph world (FIX: morph ship's world is different between server and client after change dim)
		if (this.morphHost != null)
		{
			this.world = this.morphHost.world;
			this.dimension = this.morphHost.dimension;
		}
		
		super.onUpdate();
		
		//get depth if in fluid block
		EntityHelper.checkDepth(this);
		
		//update arm
		updateArmSwingProgress();
		
		//update client timer
		updateBothSideTimer();
		
		//client side
		if (this.world.isRemote)
		{
			//有移動時, 產生水花特效
			if (this.getShipDepth() > 0D && !isRiding())
			{
				//(注意此entity因為設為非高速更新, client端不會更新motionX等數值, 需自行計算)
				double motX = this.posX - this.prevPosX;
				double motZ = this.posZ - this.prevPosZ;
				double parH = this.posY - (int)this.posY;
				double limit = 0.25D;
				
				if (motX > limit) motX = limit;
				else if (motX < -limit) motX = -limit;
				
				if (motZ > limit) motZ = limit;
				else if (motZ < -limit) motZ = -limit;
				
				
				if (motX != 0 || motZ != 0)
				{
					ParticleHelper.spawnAttackParticleAt(this.posX + motX*3D, this.posY + 0.4D, this.posZ + motZ*3D, 
							-motX, this.width, -motZ, (byte)47);
				}
			}
			
			//request model display sync after construction
			if (this.ticksExisted == 2)
			{
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_SyncModel, this.getEntityId(), this.world.provider.getDimension()));
			}
			
			//faster body rotateYaw update (vanilla = 12~20 ticks?)
			updateClientBodyRotate();
			
			//update searchlight, CLIENT SIDE ONLY, NO block light value sync!
			if (this.ticksExisted % ConfigHandler.searchlightCD == 0)
			{
        		if (ConfigHandler.canSearchlight && this.isEntityAlive())
        		{
        			updateSearchlight();
        		}
			}
			
			//check every 8 ticks
			if (this.ticksExisted % 8 == 0)
			{
        		//check every 16 ticks
				if (this.ticksExisted % 16 == 0)
				{
					//generate HP state effect, use parm:lookX to send width size
					switch (getStateEmotion(ID.S.HPState))
					{
					case ID.HPState.MINOR:
						ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
								this.width, 0.05D, 0D, (byte)4);
						break;
					case ID.HPState.MODERATE:
						ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
								this.width, 0.05D, 0D, (byte)5);
						break;
					case ID.HPState.HEAVY:
						ParticleHelper.spawnAttackParticleAt(this.posX, this.posY + 0.7D, this.posZ, 
								this.width, 0.05D, 0D, (byte)7);
						break;
					default:
						break;
					}
					
					//check every 32 ticks
					if (this.ticksExisted % 32 == 0)
					{
						if (!this.isMorph)
						{
							//show guard position
							if (!this.getStateFlag(ID.F.CanFollow))
							{
								//set guard entity
								if (this.getStateMinor(ID.M.GuardID) > 0)
								{
									Entity getEnt = EntityHelper.getEntityByID(this.getStateMinor(ID.M.GuardID), 0, true);
									this.setGuardedEntity(getEnt);
								}
								else
								{
									//reset guard entity
									this.setGuardedEntity(null);
								}
							}//end show pointer target effect
							
							//display circle particle, 只有owner才會接收到該ship同步的EID, 非owner讀取到的EID <= 0
							//get owner entity
							EntityPlayer player = null;
							if (this.getStateMinor(ID.M.PlayerEID) > 0)
							{
								player = EntityHelper.getEntityPlayerByID(getStateMinor(ID.M.PlayerEID), 0, true);
							}
							
							//show circle particle on ship and guard target
							if (player != null && player.dimension == this.getGuardedPos(3))
							{
								ItemStack item = EntityHelper.getPointerInUse((EntityPlayer) player);
								
								if (item != null && item.getItemDamage() < 3 || ConfigHandler.alwaysShowTeamParticle)
								{
									//show friendly particle
									ParticleHelper.spawnAttackParticleAtEntity(this, 0.3D, 7D, 0D, (byte)2);
									
									//show guard particle
									//標記在entity上
									if (this.getGuardedEntity() != null)
									{
										ParticleHelper.spawnAttackParticleAtEntity(this.getGuardedEntity(), 0.3D, 6D, 0D, (byte)2);
										ParticleHelper.spawnAttackParticleAtEntity(this, this.getGuardedEntity(), 0D, 0D, 0D, (byte)3, false);
									}
									//標記在block上
									else if (this.getGuardedPos(1) >= 0)
									{
										ParticleHelper.spawnAttackParticleAt(this.getGuardedPos(0)+0.5D, this.getGuardedPos(1), this.getGuardedPos(2)+0.5D, 0.3D, 6D, 0D, (byte)25);
										ParticleHelper.spawnAttackParticleAtEntity(this, this.getGuardedPos(0)+0.5D, this.getGuardedPos(1)+0.2D, this.getGuardedPos(2)+0.5D, (byte)8);
									}
								}
							}
							else
							{
								this.setStateMinor(ID.M.PlayerEID, -1);
							}
						}//end is not morph
					}//end every 32 ticks
				}//end every 16 ticks
			}//end  every 8 ticks
		}//end client side
	}

	/**
	 * update living entity
	 * 此方法在onUpdate中途呼叫
	 */
	@Override
	public void onLivingUpdate()
	{
		if (this.ticksExisted == 5)
		{
			//reseed random
			this.rand.setSeed((this.getShipUID() << 4) + System.currentTimeMillis());
			
			//reset flag
			this.initAI = false;
			this.initWaitAI = false;
		}
		
        /** server side */
        if ((!world.isRemote))
        {
	    	if (!this.isMorph)
    		{
	    		//update movement, NOTE: 1.9.4: must done before vanilla MoveHelper updating in super.onLivingUpdate()
	    		EntityHelper.updateShipNavigator(this);
	    		
	    		//update target
	    		TargetHelper.updateTarget(this);
	    		
	    		//update/init id
	    		updateShipCacheData(false);
    		}
	    	
	    	super.onLivingUpdate();
        	
        	//timer ticking
        	updateServerTimer();
        	
        	//pump liquid, transport ship has built-in pump equip
        	if (this.StateFlag[ID.F.AutoPump] && !this.isMorph &&
        		this.isEntityAlive() && !this.isSitting() && !this.getStateFlag(ID.F.NoFuel))
        		TaskHelper.onUpdatePumping(this);
        	
        	//check every 8 ticks
        	if ((ticksExisted & 7) == 0)
        	{
        		//init riding rotation
        		if (this.ticksExisted == 32 && this.getPassengers().size() > 0)
        		{
        			sendSyncPacket(S2CEntitySync.PID.SyncEntity_Rot, true);
        		}
        		
        		if (!this.isMorph)
        		{
        			//update formation buff (fast update)
            		if (this.getUpdateFlag(ID.FlagUpdate.FormationBuff))
            		{
            			this.calcShipAttributes(16, true);
            		}
            		
            		//reset AI and sync once
            		if (!this.initAI && ticksExisted > 10)
            		{
            			setStateFlag(ID.F.CanDrop, true);
                		clearAITasks();
                		clearAITargetTasks();		//reset AI for get owner after loading NBT data
                		setAIList();
                		setAITargetList();
                		decrGrudgeNum(0);			//check grudge
                		updateChunkLoader();
                		
                		this.initAI = true;
            		}
            		
            		//update task
        			TaskHelper.onUpdateTask(this);
        		}
        		
        		//check every 16 ticks
            	if ((ticksExisted & 15) == 0)
            	{
            		if (this.isEntityAlive())
            		{
            			if (!this.isMorph)
            			{
            				//waypoint move
                    		if (!(this.getRidingEntity() instanceof BasicEntityMount))
                    		{
                    			if (EntityHelper.updateWaypointMove(this))
                    			{
                    				sendSyncPacket(S2CEntitySync.PID.SyncShip_Guard, true);
                    			}
                    		}
                    		
                    		//cancel mounts
                    		if (this.hasShipMounts())
                    		{
                    			if (!this.canSummonMounts())
                    			{
                  	  	  			//cancel riding
                  	  	  			if (this.isRiding() && this.getRidingEntity() instanceof BasicEntityMount)
                  	  	  			{
                  	  	  				this.dismountRidingEntity();
                  	  	  			}
                  	  	  		}
                    		}
            			}
                		
                		//update potion buff
                		BuffHelper.convertPotionToBuffMap(this);
                		this.calcShipAttributes(8, true);
            		}

            		//check every 32 ticks
                	if ((ticksExisted & 31) == 0)
                	{
                		if (this.isEntityAlive() && !this.isMorph)
                		{
                			//apply potion effects
                			BuffHelper.applyBuffOnTicks(this);
                			
	                		//use bucket automatically
	                		if ((getMaxHealth() - getHealth()) > (getMaxHealth() * 0.1F + 5F))
	                		{
	        	                if (decrSupplies(7))
	        	                {
	        		                this.heal(this.getMaxHealth() * 0.08F + 15F);
	        	                
	        		                //airplane++
	        		                if (this instanceof BasicEntityShipCV)
	        		                {
	        		                	BasicEntityShipCV ship = (BasicEntityShipCV) this;
	        		                	ship.setNumAircraftLight(ship.getNumAircraftLight() + 1);
	        		                	ship.setNumAircraftHeavy(ship.getNumAircraftHeavy() + 1);
	        		                }
	        	                }
	        	            }
                		}
                		
                		//check every 64 ticks
                		if ((ticksExisted & 63) == 0)
                		{
                    		//update random emotion
                    		updateEmotionState();
                    		
                    		//sync riding state to client
                    		if (this.isRiding() && !this.isMorph)
                    		{
                    			this.sendSyncPacketRiders();
                    		}
                    		
                    		//check every 128 ticks
                        	if ((ticksExisted & 127) == 0)
                        	{
                        		//update state (slow update)
                        		this.calcShipAttributes(31, true);
                        		
                        		if (!this.isMorph)
                        		{
                            		//check chunk loader
                            		this.updateChunkLoader();
                            		
                            		//delayed init, waiting for player entity loaded
                            		if (!this.initWaitAI && ticksExisted >= 128)
                            		{
                            			//request formation buff update
                            			setUpdateFlag(ID.FlagUpdate.FormationBuff, true);
                            			sendSyncPacketAll();
                            			
                            			this.initWaitAI = true;
                            		}
                            		
                            		//check owner online
                            		if (this.getPlayerUID() > 0)
                            		{
                            			//get owner
                            			EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
                            			
                            			//owner exists (online and same world)
                            			if (player != null)
                            			{
                        					//update owner entity id (could be changed when owner change dimension or dead)
                                			this.setStateMinor(ID.M.PlayerEID, player.getEntityId());
                                			//sync guard position
                                			this.sendSyncPacket(S2CEntitySync.PID.SyncShip_Guard, false);
                                		}
                            		}
                            		
                            		if (this.isEntityAlive())
                            		{
        	                    		//use combat ration automatically
        	                    		if (EntityHelper.getMoraleLevel(this.getMorale()) >= getStateMinor(ID.M.UseCombatRation) && getFoodSaturation() < getFoodSaturationMax())
        	                    		{
        	                    			useCombatRation();
        	                    		}
        	                    		
        	                    		//update mount
        	                    		updateMountSummon();
        	                    		
        	                    		//update consume item
        	                    		updateConsumeItem();
        	                    		
        	                    		//update morale value
        	                    		if (!getStateFlag(ID.F.NoFuel)) updateMorale();
                            		}
                        		}//end is not morph
                        		
                        		//check every 256 ticks
                            	if ((this.ticksExisted & 255) == 0)
                            	{
                            		if (!this.isMorph)
                            		{
                            			if (this.isEntityAlive())
                                		{
        	                        		//show idle emotes
        	                        		if (!getStateFlag(ID.F.NoFuel)) applyEmotesReaction(4);
        	                        		
        	                        		//HP auto regen
        	                        		if (this.getHealth() < this.getMaxHealth())
        	                        		{
        	                        			this.heal(this.getMaxHealth() * 0.03F + 1F);
        	                        		}
        	                        		
        	                        		//update ship cache data
        	                        		updateShipCacheDataWithoutNewID();
                                		}
                                		
                                		//update name string
                                		EntityHelper.updateNameTag(this);
                            		}
                            		
                            		//food saturation--
                            		int f = this.getFoodSaturation();
                            		if (f > 0)
                            		{
                            			this.setFoodSaturation(--f);
                            		}
                            	}//end every 256 ticks
                        	}//end every 128 ticks
                		}//end every 64 ticks
                	}//end every 32 ticks
            	}//end every 16 ticks
        	}//end every 8 ticks
        	
        	//play timekeeping sound
        	if (!this.isMorph && ConfigHandler.timeKeeping && this.getStateFlag(ID.F.TimeKeeper) && this.isEntityAlive())
        	{
        		int checkHour = getWorldHourTime();
        		if (checkHour >= 0) playTimeSound(checkHour);
        	}//end timekeeping
        }//end server side
        /** client side */
        else
        {
        	super.onLivingUpdate();
        	
        	//update client side timer
        	updateClientTimer();
        	
        	//request sync packet
        	if (this.ticksExisted == 40 || this.ticksExisted == 120)
        	{
        		this.sendSyncRequest(1);	//request unit name sync
        	}
        	
        	//every 32 ticks
        	if ((this.ticksExisted & 31) == 0)
        	{
    			//show unit name and uid on head
        		EntityHelper.showNameTag(this);
        		
        		if ((this.ticksExisted & 63) == 0)
            	{
        			//request sync model state every X ticks
        			this.sendSyncRequest(0);	//request emotion state sync
        			
	            	//every 128 ticks
	            	if ((this.ticksExisted & 127) == 0)
	            	{
	            		//update potion buff (client side for GUI display)
	            		this.sendSyncRequest(2);	//request buff map sync
	            	}//end 128 ticks
            	}//end 64 ticks
        	}//end 32 ticks
        }//end client side
        
        /** both side */
    	//every 32 ticks
    	if ((this.ticksExisted & 15) == 0)
    	{
    		//TODO debug
//        	float[] raw = this.shipAttrs.getAttrsRaw();
//        	float[] eq = this.shipAttrs.getAttrsEquip();
//        	float[] mor = this.shipAttrs.getAttrsMorale();
//        	float[] pot = this.shipAttrs.getAttrsPotion();
//        	float[] form = this.shipAttrs.getAttrsFormation();
//        	float[] buf = this.shipAttrs.getAttrsBuffed();
//        	int idd = ID.Attrs.ATK_L;
//        	LogHelper.debug("AAAAAA "+this.world.isRemote+" "+raw[idd]
//        			+" "+eq[idd]+" "+mor[idd]+" "+pot[idd]
//        			+" "+form[idd]+" "+buf[idd]);
    		
        	if ((this.ticksExisted & 127) == 0)
        	{
        		this.setAir(300);
        	}//end 128 ticks
    	}//end 32 ticks
    }
	
	@Override
	public boolean canPassengerSteer()
    {
		return false;
    }
	
	//use combat ration
	protected void useCombatRation()
	{
		//search item in ship inventory
		int i = findItemInSlot(new ItemStack(ModItems.CombatRation), true);
		
		if (i >= 0)
		{
			//decr item stacksize
			ItemStack getItem = this.itemHandler.getStackInSlot(i);
			
			InteractHelper.interactFeed(this, null, getItem);
			
			getItem.stackSize--;
			
			if (getItem.stackSize <= 0)
			{
				getItem = null;
			}
			
			//save back itemstack
			//no need to sync because no GUI opened
			this.itemHandler.setStackInSlot(i, getItem);
		}
	}

	//melee attack method, no ammo cost, no attack speed, damage = 12.5% atk
	@Override
	public boolean attackEntityAsMob(Entity target)
	{
		//get attack value
		float atk = getAttackBaseDamage(0, target);
		
		//experience++
		addShipExp(ConfigHandler.expGain[0]);
		
		//morale--
		decrMorale(0);
		setCombatTick(this.ticksExisted);
		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
				
	    //entity attack effect
	    applySoundAtAttacker(0, target);
	    applyParticleAtAttacker(0, target, distVec);
	    
	    //是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk);

	    //target attack effect
	    if (isTargetHurt)
	    {
	    	if (!TeamHelper.checkSameOwner(this, target)) BuffHelper.applyBuffOnTarget(target, this.AttackEffectMap);
	    	applySoundAtTarget(0, target);
	        applyParticleAtTarget(0, target, distVec);
			applyEmotesReaction(3);
			
			if (ConfigHandler.canFlare)
			{
				flareTarget(target);
			}
	    }
	    
	    applyAttackPostMotion(0, target, isTargetHurt, atk);

	    return isTargetHurt;
	}
	
	//range attack method, cost light ammo, attack delay = 20 / attack speed, damage = 100% atk 
	@Override
	public boolean attackEntityWithAmmo(Entity target)
	{
		//light ammo -1
        if (!decrAmmoNum(0, this.getAmmoConsumption())) return false;
        
        //experience++
  		addShipExp(ConfigHandler.expGain[1]);
  		
  		//grudge--
  		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk]);
  		
  		//morale--
  		decrMorale(1);
  		setCombatTick(this.ticksExisted);
  		
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
	    	//check owner
	    	if (!TeamHelper.checkSameOwner(this, target)) BuffHelper.applyBuffOnTarget(target, this.AttackEffectMap);
	    	applySoundAtTarget(1, target);
	        applyParticleAtTarget(1, target, distVec);
	        applyEmotesReaction(3);
	        
	        if (ConfigHandler.canFlare) flareTarget(target);
        }
	    
	    applyAttackPostMotion(1, target, isTargetHurt, atk);

	    return isTargetHurt;
	}
	
	/**
	 * attack a position with missile, NOTE: SHIP MOUNTS REQUIRED
	 */
	public boolean attackEntityWithHeavyAmmo(BlockPos target)
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
	
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this.getPosition(), target);
        
        //play sound and particle
        applySoundAtAttacker(2, this);
	    applyParticleAtAttacker(2, this, distVec);
		
	    float tarX = (float) target.getX();
	    float tarY = (float) target.getY();
	    float tarZ = (float) target.getZ();
	    
	    //calc miss rate
        if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //get attack value
  		float atk = getAttackBaseDamage(2, null);
        
        //spawn missile
  		summonMissile(2, atk, tarX, tarY, tarZ, 1F);
        
        //play target effect
        applySoundAtTarget(2, this);
        applyParticleAtTarget(2, this, distVec);
        applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        applyAttackPostMotion(2, this, true, atk);
        
        return true;
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
	
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
        
        //play sound and particle
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
        
        //get attack value
  		float atk = getAttackBaseDamage(2, target);
        
        //spawn missile
        summonMissile(2, atk, tarX, tarY, tarZ, target.height);
        
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
        applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        applyAttackPostMotion(2, target, true, atk);
        
        return true;
	}
	
	/**
	 * spawn attack missile, used in light or heavy attack method
	 * 
	 * attackType: 0:melee, 1:light, 2:heavy
	 */
	public void summonMissile(int attackType, float atk, float tarX, float tarY, float tarZ, float targetHeight)
	{
		//missile type
		float launchPos = (float) posY + height * 0.5F;
		if (this.isMorph) launchPos += 0.5F;
		int moveType = CombatHelper.calcMissileMoveType(this, tarY, attackType);
		if (moveType == 0) launchPos = (float) posY + height * 0.3F;
		
		MissileData md = this.getMissileData(attackType);
        float[] data = new float[] {atk, 0.15F, launchPos, tarX, tarY + targetHeight * 0.1F, tarZ, 140, 0.25F, md.vel0, md.accY1, md.accY2};
        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this, md.type, moveType, data);
        this.world.spawnEntity(missile);
	}
	
	/** apply motion after attack
	 *  type: 0:melee, 1:light, 2:heavy, 3:light air, 4:heavy air
	 */
	public void applyAttackPostMotion(int type, Entity target, boolean isTargetHurt, float atk) {}

	@Override
	public boolean updateSkillAttack(Entity target)
	{
		return false;
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource source, float atk)
	{
		if (this.world.isRemote || this.morphHost != null) return false;
		
		boolean checkDEF = true;

    	//change sensitive body
  		if (this.rand.nextInt(10) == 0) randomSensitiveBody();
  		
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
			
			//set hurt face
	    	this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
	    	
    		return super.attackEntityFrom(source, atk);
		}
		//out of world
		else if (source == DamageSource.outOfWorld)
		{
			//取消坐下動作
			this.setSitting(false);
			this.dismountRidingEntity();
        	this.setPositionAndUpdate(this.posX, 4D, this.posZ);
        	this.motionX = 0D;
        	this.motionY = 1D;
        	this.motionZ = 0D;
        	return false;
		}
		
		//check attacker is potion
		float patk = BuffHelper.getPotionDamage(this, source, atk);
		
		if (patk > 0F)
		{
			atk = patk;
			checkDEF = false;
		}
        
    	//若攻擊方為owner, 則直接回傳傷害, 不計def跟friendly fire
		if (source.getEntity() instanceof EntityPlayer &&
			TeamHelper.checkSameOwner(source.getEntity(), this))
		{
			this.setSitting(false);
			
			//set hurt face
	    	this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
	    	
			return super.attackEntityFrom(source, atk);
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
			if (attacker.equals(this))
			{
				//取消坐下動作
				this.setSitting(false);
				return false;
			}
			
			//若攻擊方為player, 則檢查friendly fire
			if (attacker instanceof EntityPlayer)
			{
				//若禁止friendlyFire, 則不造成傷害
				if (!ConfigHandler.friendlyFire)
				{
					return false;
				}
			}
			
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
			
			//ship vs ship, config傷害調整 (僅限友善船)
			if (attacker instanceof IShipOwner && ((IShipOwner)attacker).getPlayerUID() > 0 &&
				(attacker instanceof BasicEntityShip ||
				 attacker instanceof BasicEntitySummon || 
				 attacker instanceof BasicEntityMount))
			{
				reducedAtk = reducedAtk * (float)ConfigHandler.dmgSvS * 0.01F;
			}
			
			//check resist potion
			reducedAtk = BuffHelper.applyBuffOnDamageByResist(this, source, reducedAtk);

			//check night vision potion
			reducedAtk = BuffHelper.applyBuffOnDamageByLight(this, source, reducedAtk);
			
			//tweak min damage
	        if (reducedAtk < 1F && reducedAtk > 0F) reducedAtk = 1F;
	        else if (reducedAtk <= 0F) reducedAtk = 0F;

			//取消坐下動作
			this.setSitting(false);
			
			//設置revenge target
			this.setEntityRevengeTarget(attacker);
			this.setEntityRevengeTime();
			
			//若傷害力可能致死, 則尋找物品中有無repair goddess來取消掉此攻擊
			if (reducedAtk >= (this.getHealth() - 1F))
			{
				if (this.decrSupplies(8))
				{
					this.setHealth(this.getMaxHealth());
					this.StateTimer[ID.T.ImmuneTime] = 120;
					
					//add repair goddess particle
					TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 13, 0D, 0.03D, 0D), point);
					
					return false;
				}
			}
			
	  		//morale--
			decrMorale(5);
	  		setCombatTick(this.ticksExisted);
	  		
	  		//set damaged body ID and show emotes
	  		if (this.rand.nextInt(5) == 0)
	  		{
				//set hit position
				this.setStateMinor(ID.M.HitHeight, CalcHelper.getEntityHitHeight(this, source.getSourceOfDamage()));
				this.setStateMinor(ID.M.HitAngle, CalcHelper.getEntityHitSide(this, source.getSourceOfDamage()));
				
				//apply emotes
				applyEmotesReaction(2);
	  		}
	  		
	  		//set hurt face
	    	this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
	    	
    		return super.attackEntityFrom(source, reducedAtk);
        }
		
		return false;
    }
	
	/** decr morale value, type: 0:melee, 1:light, 2:heavy, 3:light air, 4:light heavy, 5:damaged */
	public void decrMorale(int type)
	{
		if (this.isMorph) return;
		
		switch (type)
		{
		case 0:  //melee
			this.addMorale(-2);
		break;
		case 1:  //light
			this.addMorale(-4);
		break;
		case 2:  //heavy
			this.addMorale(-6);
		break;
		case 3:  //light air
			this.addMorale(-6);
		break;
		case 4:  //light heavy
			this.addMorale(-8);
		break;
		case 5:  //damaged
			this.addMorale(-5);
		break;
		}
	}
	
	/** decr ammo, type: 0:light, 1:heavy */
	public boolean decrAmmoNum(int type, int amount)
	{
		//check morph
		if (this.isMorph)
		{
			return MetamorphHelper.decrAmmoNum(this, type, amount);
		}
		
		int ammoType = ID.M.NumAmmoLight;
		boolean useItem = !hasAmmoLight();
		boolean showEmo = false;
		float modAmmo = this.shipAttrs.getAttrsBuffed(ID.Attrs.AMMO);
		
		switch (type)
		{
		case 1:   //use heavy ammo
			ammoType = ID.M.NumAmmoHeavy;
			useItem = !hasAmmoHeavy();
			break;
		}

		//check ammo first time
		if (StateMinor[ammoType] <= amount || useItem)
		{
			int addAmmo = 0;
			
			//use light ammo item
			if (ammoType == ID.M.NumAmmoLight)
			{
				if (decrSupplies(0))
				{  //use ammo item
					addAmmo = (int) (Values.N.BaseLightAmmo * modAmmo);
					showEmo = true;
				}
				else if (decrSupplies(2))
				{  //use ammo container item
					addAmmo = (int) (Values.N.BaseLightAmmo * 9 * modAmmo);
					showEmo = true;
				}
			}
			//use heavy ammo item
			else
			{
				if (decrSupplies(1))
				{  //use ammo item
					addAmmo = (int) (Values.N.BaseHeavyAmmo * modAmmo);
					showEmo = true;
				}
				else if (decrSupplies(3))
				{  //use ammo container item
					addAmmo = (int) (Values.N.BaseHeavyAmmo * 9 * modAmmo);
					showEmo = true;
				}
			}
			
			//check easy mode
			if (ConfigHandler.easyMode)
			{
				addAmmo *= 10;
			}
			
			StateMinor[ammoType] += addAmmo;
		}
		
		//show emotes
		if (showEmo)
		{
			if (this.getEmotesTick() <= 0)
			{
				this.setEmotesTick(40);
				
				switch (this.rand.nextInt(4))
				{
				case 1:
					applyParticleEmotion(29);  //blink
				break;
				case 2:
					applyParticleEmotion(30);  //pif
				break;
				default:
					applyParticleEmotion(9);  //hungry
				break;
				}
			}
		}
		
		//check ammo second time
		if (StateMinor[ammoType] < amount)
		{
			//show emotes
			if (this.getEmotesTick() <= 0)
			{
				this.setEmotesTick(20);
				
				switch (this.rand.nextInt(7))
				{
				case 1:
					applyParticleEmotion(0);  //drop
				break;
				case 2:
					applyParticleEmotion(2);  //panic
				break;
				case 3:
					applyParticleEmotion(5);  //...
				break;
				case 4:
					applyParticleEmotion(20);  //orz
				break;
				default:
					applyParticleEmotion(32);  //hmm
				break;
				}
			}
			
			return false;
		}
		else
		{
			StateMinor[ammoType] -= amount;
			return true;
		}
	}
	
	public int getGrudge()
	{
		return this.StateMinor[ID.M.NumGrudge];
	}
	
	//change grudge value (without buff)
	public void addGrudge(int value)
	{
		this.StateMinor[ID.M.NumGrudge] += value;
		if (this.StateMinor[ID.M.NumGrudge] <= 0) this.StateMinor[ID.M.NumGrudge] = 0;
	}
	
	//consume grudge with buff and item calculation
	public void decrGrudgeNum(int value)
	{
		//check morph
		if (this.isMorph)
		{
			MetamorphHelper.decrGrudgeNum(this, value);
			return;
		}
				
		//get grudge magnification
		float modGrudge = this.shipAttrs.getAttrsBuffed(ID.Attrs.GRUDGE);
		
		//if grudge--, check buff: hunger
		if (value > 0)
		{
			int level = BuffHelper.getPotionLevel(this, 17);
			value *= 1 + level;
		}
		//if grudge++, check buff: grudge mod
		else if (value < 0)
		{
			value *= modGrudge;
		}
		
		//check fuel flag
		if (!getStateFlag(ID.F.NoFuel))
		{
			this.addGrudge(-value);
		}
		
		//eat "ONE" grudge item
		if (this.getGrudge() <= 0)
		{
			//try to find grudge item
			if (decrSupplies(4))
			{
				if (ConfigHandler.easyMode)
				{
					this.addGrudge((int)(Values.N.BaseGrudge * 10F * modGrudge));
				}
				else
				{
					this.addGrudge((int)(Values.N.BaseGrudge * modGrudge));
				}
			}
			//try to find grudge block
			else if (decrSupplies(5))
			{
				if (ConfigHandler.easyMode)
				{
					this.addGrudge((int)(Values.N.BaseGrudge * 90F * modGrudge));
				}
				else
				{
					this.addGrudge((int)(Values.N.BaseGrudge * 9F * modGrudge));
				}
			}
			//避免吃掉含有儲存資訊的方塊, 因此不使用heavy grudge block作為補充道具
		}
		
		//check fuel again and set fuel flag
		if (StateMinor[ID.M.NumGrudge] <= 0)
		{
			setStateFlag(ID.F.NoFuel, true);
		}
		else
		{
			setStateFlag(ID.F.NoFuel, false);
		}
		
		//check fuel flag and set AI
		if (getStateFlag(ID.F.NoFuel))  //no fuel, clear AI
		{
			//原本有AI, 則清除之
			if (this.targetTasks.taskEntries.size() > 0)
			{
				updateFuelState(true);
			}	
		}
		else							//has fuel, set AI
		{
			if (this.targetTasks.taskEntries.size() < 1)
			{
				updateFuelState(false);
			}
		}
	}
	
	/**
	 * decrese ammo/grudge/repair item number
	 * return:
	 *   true = get item and item number -1
	 *   false = not enough item
	 */
	public boolean decrSupplies(int type)
	{
		int itemNum = 1;
		boolean noMeta = false;
		ItemStack itemType = null;
		
		//find ammo
		switch (type)
		{
		case 0:	//use 1 light ammo
			itemType = new ItemStack(ModItems.Ammo,1,0);
			break;
		case 1: //use 1 heavy ammo
			itemType = new ItemStack(ModItems.Ammo,1,2);
			break;
		case 2:	//use 1 light ammo container
			itemType = new ItemStack(ModItems.Ammo,1,1);
			break;
		case 3: //use 1 heavy ammo container
			itemType = new ItemStack(ModItems.Ammo,1,3);
			break;
		case 4: //use 1 grudge
			itemType = new ItemStack(ModItems.Grudge,1);
			break;
		case 5: //use 1 grudge block
			itemType = new ItemStack(ModBlocks.BlockGrudge,1);
			break;
		case 6: //use 1 grudge block
			itemType = new ItemStack(ModBlocks.BlockGrudgeHeavy,1);
			break;
		case 7:	//use 1 repair bucket
			itemType = new ItemStack(ModItems.BucketRepair,1);
			break;
		case 8:	//use 1 repair goddess
			itemType = new ItemStack(ModItems.RepairGoddess,1);
			break;
		}
		
		//search item in ship inventory
		int i = findItemInSlot(itemType, noMeta);
		
		if (i == -1)
		{	//item not found
			return false;
		}
		
		//decr item stacksize
		ItemStack getItem = this.itemHandler.getStackInSlot(i);

		if (getItem.stackSize >= itemNum)
		{
			getItem.stackSize -= itemNum;
		}
		else
		{	//not enough item, return false
			return false;
		}
				
		if (getItem.stackSize == 0)
		{
			getItem = null;
		}
		
		//save back itemstack
		//no need to sync because no GUI opened
		this.itemHandler.setStackInSlot(i, getItem);
		
		return true;	
	}

	//update AI task when no fuel
	protected void updateFuelState(boolean nofuel)
	{
		if (nofuel)
		{
			setMorale(0);
			clearAITasks();
			clearAITargetTasks();
			sendSyncPacketAllMisc();
			
			//設定mount的AI
			if (this.getRidingEntity() instanceof BasicEntityMount)
			{
				((BasicEntityMount) this.getRidingEntity()).clearAITasks();
			}
			
			//show no fuel emotes
			if (this.getEmotesTick() <= 0)
			{
				this.setEmotesTick(20);
				switch (this.rand.nextInt(7))
				{
				case 1:
					applyParticleEmotion(0);  //drop
					break;
				case 2:
					applyParticleEmotion(32);  //hmm
					break;
				case 3:
					applyParticleEmotion(2);  //panic
					break;
				case 4:
					applyParticleEmotion(12);  //omg
					break;
				case 5:
					applyParticleEmotion(5);  //...
					break;
				case 6:
					applyParticleEmotion(20);  //orz
					break;
				default:
					applyParticleEmotion(10);  //dizzy
					break;
				}
			}
		}
		else
		{
			clearAITasks();
			clearAITargetTasks();
			setAIList();
			setAITargetList();
			sendSyncPacketAllMisc();
			
			//設定mount的AI
			if (this.getRidingEntity() instanceof BasicEntityMount)
			{
				((BasicEntityMount) this.getRidingEntity()).clearAITasks();
				((BasicEntityMount) this.getRidingEntity()).setAIList();
			}
			
			//show recovery emotes
			if (this.getEmotesTick() <= 0)
			{
				this.setEmotesTick(40);
				switch (this.rand.nextInt(5))
				{
				case 1:
					applyParticleEmotion(31);  //shy
					break;
				case 2:
					applyParticleEmotion(32);  //hmm
					break;
				case 3:
					applyParticleEmotion(7);  //note
					break;
				default:
					applyParticleEmotion(1);  //love
					break;
				}
			}
		}
	}

	//find item in ship inventory
	protected int findItemInSlot(ItemStack parItem, boolean noMeta)
	{
		ItemStack slotitem = null;

		//search ship inventory (except equip slots)
		for (int i = ContainerShipInventory.SLOTS_SHIPINV; i < CapaShipInventory.SlotMax; i++)
		{
			//check inv size
			switch (getInventoryPageSize())
			{
			case 0:
				if (i >= ContainerShipInventory.SLOTS_SHIPINV + 18) return -1;
				break;
			case 1:
				if (i >= ContainerShipInventory.SLOTS_SHIPINV + 36) return -1;
				break;
			}
			
			//get item
			slotitem = this.itemHandler.getStackInSlot(i);
			
			if (slotitem != null && slotitem.getItem().equals(parItem.getItem()))
			{
				if (noMeta)
				{
					return i;	//found item
				}
				else
				{
					if (slotitem.getItemDamage() == parItem.getItemDamage())
					{
						return i;
					}
				}
				
			}		
		}	
		
		return -1;	//item not found
	}
	
	@Override
	public boolean canFly()
	{
		return isPotionActive(MobEffects.LEVITATION);
	}
	
	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}
	
	//true if host has mounts
	public boolean hasShipMounts()
	{
		return false;
	}
	
	//true if host can summon mounts
	public boolean canSummonMounts()
	{
		return (this.getStateEmotion(ID.S.State) & 1) == 1 && !this.getStateFlag(ID.F.NoFuel);
	}
	
	public BasicEntityMount summonMountEntity()
	{
		return null;
	}
	
	@Override
	public Entity getGuardedEntity()
	{
		return this.guardedEntity;
	}

	@Override
	public void setGuardedEntity(Entity entity)
	{
		if(entity != null && entity.isEntityAlive())
		{
			this.guardedEntity = entity;
			this.setStateMinor(ID.M.GuardID, entity.getEntityId());
		}
		else
		{
			this.guardedEntity = null;
			this.setStateMinor(ID.M.GuardID, -1);
		}
	}
	
	/**
	 * vec:
	 *   0:x, 1:y, 2:z, 3:dimension, 4:type
	 *   
	 *   type:
	 *     0:none, 1:guard block, 2:guard entity
	 */
	@Override
	public int getGuardedPos(int vec)
	{
		switch (vec)
		{
		case 0:
			return this.getStateMinor(ID.M.GuardX);
		case 1:
			return this.getStateMinor(ID.M.GuardY);
		case 2:
			return this.getStateMinor(ID.M.GuardZ);
		case 3:
			return this.getStateMinor(ID.M.GuardDim);
		case 4:
			return this.getStateMinor(ID.M.GuardType);
		default:
			return 0;
		}
	}

	/**
	 *  type: 0:none, 1:block, 2:entity
	 */
	@Override
	public void setGuardedPos(int x, int y, int z, int dim, int type)
	{
		this.setStateMinor(ID.M.GuardX, x);
		this.setStateMinor(ID.M.GuardY, y);
		this.setStateMinor(ID.M.GuardZ, z);
		this.setStateMinor(ID.M.GuardDim, dim);
		this.setStateMinor(ID.M.GuardType, type);
	}
	
	@Override
	public double getMountedYOffset()
	{
		return this.height;
	}
	
	@Override
	public Entity getHostEntity()
	{
		if (this.getPlayerUID() > 0)
		{
			if (this.world.isRemote)
			{
				return this.getOwner();
			}
			else
			{
				return EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
			}
		}
		else
		{
			return this.getOwner();
		}
	}
	
	@Override
	public int getDamageType()
	{
		return this.getStateMinor(ID.M.DamageType);
	}
	
	//update ship id
	public void updateShipCacheData(boolean forceUpdate)
	{
		//register or update ship id and owner id
		if (!this.isUpdated && ticksExisted % updateTime == 0 || forceUpdate)
		{
			LogHelper.debug("DEBUG: update ship: initial SID, PID  cd: "+updateTime+" force: "+forceUpdate);
			
			//update owner uid
			if (this.getPlayerUID() <= 0)
			{
				ServerProxy.updateShipOwnerID(this);
			}
			
			//update ship uid
			ServerProxy.updateShipID(this);
			
			//update success
			if (getPlayerUID() > 0 && getShipUID() > 0)
			{
				this.sendSyncPacketAll();
				this.isUpdated = true;
			}
			
			//prolong update time
			if (updateTime > 4096)
			{
				updateTime = 4096;
			}
			else
			{
				updateTime *= 2;
			}
		}//end update id
	}
	
	//update ship id
	public void updateShipCacheDataWithoutNewID()
	{
		//update ship cache
		if (!this.world.isRemote)
		{
			int uid = this.getShipUID();
			
			if (uid > 0)
			{
				//ship dupe bug checking
				BasicEntityShip ship = ServerProxy.checkShipIsDupe(this, uid);
				
				if (this.equals(ship))
				{
					CacheDataShip sdata = new CacheDataShip(this.getEntityId(), this.world.provider.getDimension(),
							this.getShipClass(), this.isDead, this.posX, this.posY, this.posZ,
							this.writeToNBT(new NBTTagCompound()));
					
					ServerProxy.setShipWorldData(uid, sdata);
				}
				else
				{
					this.setDead();
				}
			}
		}
	}
	
  	//update emotion, SERVER SIDE ONLY!
  	protected void updateEmotionState()
  	{
  		float hpState = 1F;
  		
  		if (this.morphHost != null)
  		{
  			hpState = this.morphHost.getHealth() / this.morphHost.getMaxHealth();
  		}
  		else
  		{
  			hpState = this.getHealth() / this.getMaxHealth();
  		}
		
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
				case ID.Emotion.NORMAL:		//if normal, 33% to bored
					if (this.getRNG().nextInt(3) == 0)
						this.setStateEmotion(ID.S.Emotion, ID.Emotion.BORED, false);
				break;
				default:					//other, 25% return normal
					if (this.getRNG().nextInt(4) == 0)
					{
						this.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
					}
				break;
				}
				
				//random Emotion4
				switch (this.getStateEmotion(ID.S.Emotion4))
				{
				case ID.Emotion.NORMAL:		//if normal, 33% to bored
					if (this.getRNG().nextInt(3) == 0)
						this.setStateEmotion(ID.S.Emotion4, ID.Emotion.BORED, false);
				break;
				default:					//other, 33% return normal
					if (this.getRNG().nextInt(3) == 0)
						this.setStateEmotion(ID.S.Emotion4, ID.Emotion.NORMAL, false);
				break;
				}
			}
		}
		
		//send sync packet
		if (!this.world.isRemote)
		{
			this.sendSyncPacketEmotion();
		}
  	}
  	
	//update hp state
  	protected void updateMountSummon()
  	{
    	if (this.hasShipMounts())
    	{
    		//summon mount if emotion state >= equip00
  	  		if (this.canSummonMounts() && !this.getStateFlag(ID.F.NoFuel))
  	  		{
  	  			if (!this.isRiding())
  	  			{
  	  				//summon mount entity
  	  	  			BasicEntityMount mount = this.summonMountEntity();
  	  	  			mount.initAttrs(this);
  	  	  			this.world.spawnEntity(mount);
  	  	  			
  	  	  			//clear rider
  	  	  			for (Entity p : this.getPassengers())
  	  	  			{
  	  	  				p.dismountRidingEntity();
  	  	  			}
  	  	  			
  	  	  			//set riding entity
	  	  			this.startRiding(mount, true);
	  	  			
	  	  			//sync rider
	  	  			mount.sendSyncPacket(0);
  	  			}
  	  		}
    	}
  	}
  	
  	//update consume item
  	protected void updateConsumeItem()
  	{
		//get ammo if no ammo
		if (!this.hasAmmoLight()) { this.decrAmmoNum(0, 0); }
		if (!this.hasAmmoHeavy()) { this.decrAmmoNum(1, 0); }
		
		//calc move distance
		double distX = posX - ShipPrevX;
		double distY = posY - ShipPrevY;
		double distZ = posZ - ShipPrevZ;
		
		//calc total consumption
    	int valueConsume = (int) MathHelper.sqrt(distX*distX + distY*distY + distZ*distZ);
    	if (ShipPrevY <= 0D) valueConsume = 0;  //do not decrGrudge if ShipPrev not inited
    	
    	//morale-- per 2 blocks
    	int m = (int)((float)valueConsume * 0.5F);
    	if(m < 5) m = 5;
    	if(m > 50) m = 50;
    	this.addMorale(-m);
    	
    	//moving grudge cost per block
    	valueConsume *= ConfigHandler.consumeGrudgeAction[ID.ShipConsume.Move];
    	
    	//get exp if transport
    	if(this instanceof EntityTransportWa && this.ticksExisted > 200)
    	{
    		//gain exp when moving
    		int moveExp = (int) (valueConsume * 0.2F);
    		addShipExp(moveExp);
    	}
    	
    	//add idle grudge cost
    	valueConsume += this.getGrudgeConsumption();
    	
    	//eat grudge
    	decrGrudgeNum(valueConsume);
    	
    	//update pos
    	ShipPrevX = posX;
		ShipPrevY = posY;
		ShipPrevZ = posZ;
  	}
  	
  	/** morale value
  	 *  5101 - X     Exciting
  	 *  3901 - 5100  Happy
  	 *  2101 - 3900  Normal
  	 *  901  - 2100  Tired
  	 *  0    - 900   Exhausted
  	 *  
  	 *  action:
  	 *  
  	 *  caress head:
  	 *    +X per 3 ticks
  	 *    
  	 *  feed:
  	 *    +X per item
  	 *    
  	 *  idle:
  	 *    -X if > happy
  	 *    +X if < normal * 150%
  	 *    
  	 *  attack:
  	 *    -X per hit
  	 *    +X per kill
  	 *    
  	 *  damaged:
  	 *    -X per hit
  	 *    
  	 *  move:
  	 *    -X per Y blocks
  	 *  
  	 */
  	protected void updateMorale()
  	{
  		int m = this.getMorale();
  		
  		//out of combat
  		if (EntityHelper.checkShipOutOfCombat(this))
  		{
  			if (m < ID.Morale.L_Normal + 1000)
  			{	//take 5 min from 0 to 2100
  	  			this.setStateMinor(ID.M.Morale, m + 40);
  	  		}
  	  		else if (m > ID.Morale.L_Happy)
  	  		{
  	  			this.setStateMinor(ID.M.Morale, m - 10);
  	  		}
  		}
  		//in combat
  		else
  		{
  	  		if (m < ID.Morale.L_Tired)
  	  		{
  	  			this.setStateMinor(ID.M.Morale, m - 11);
  	  		}
  	  		else if (m < ID.Morale.L_Normal)
  	  		{
  	  			this.setStateMinor(ID.M.Morale, m - 7);
  	  		}
  	  		else if (m < ID.Morale.L_Happy)
  	  		{
  	  			this.setStateMinor(ID.M.Morale, m - 5);
  	  		}
  	  		else if (m >= ID.Morale.L_Happy)
  	  		{
  	  			this.setStateMinor(ID.M.Morale, m - 11);
  	  		}
  		}
  		
  	}
  	
  	/** update searchlight effect */
  	protected void updateSearchlight()
  	{
  		if (this.getStateMinor(ID.M.LevelSearchlight) > 0)
  		{
  			BlockPos pos = new BlockPos(this);
			int light = this.world.getLight(pos, true);

			//place new light block
  			if (light < 11)
  			{
				BlockHelper.placeLightBlock(this.world, pos);
  			}
  			//search light block, renew lifespan
  			else
  			{
  				BlockHelper.updateNearbyLightBlock(this.world, pos);
  			}
  		}
  	}
  	
  	/** update client timer */
  	protected void updateClientTimer()
  	{
  		//attack motion timer
  		if (this.StateTimer[ID.T.AttackTime] > 0) this.StateTimer[ID.T.AttackTime]--;
  		
		//caress reaction time
		if (this.StateTimer[ID.T.Emotion3Time] > 0)
		{
			this.StateTimer[ID.T.Emotion3Time]--;
			
			if (this.StateTimer[ID.T.Emotion3Time] == 0)
			{
				this.setStateEmotion(ID.S.Emotion3, 0, false);
			}
		}
  	}
  	
  	/** update server timer */
  	protected void updateServerTimer()
  	{
  		//immune timer
  		if (this.StateTimer[ID.T.ImmuneTime] > 0) this.StateTimer[ID.T.ImmuneTime]--;
  		
  		//crane change state delay
  		if (this.StateTimer[ID.T.CrandDelay] > 0) this.StateTimer[ID.T.CrandDelay]--;
  		
  		//craning timer
  		if (this.getStateMinor(ID.M.CraneState) > 1) this.StateTimer[ID.T.CraneTime]++;
  		
  		//hurt sound delay
  		if (this.StateTimer[ID.T.SoundTime] > 0) this.StateTimer[ID.T.SoundTime]--;
	
  		//emotes delay
		if (this.StateTimer[ID.T.EmoteDelay] > 0) this.StateTimer[ID.T.EmoteDelay]--;
  	}
  	
  	/** update both side timer */
  	protected void updateBothSideTimer()
  	{
  		//mount skill cd
  		if (this.StateTimer[ID.T.MountSkillCD1] > 0) this.StateTimer[ID.T.MountSkillCD1]--;
  		if (this.StateTimer[ID.T.MountSkillCD2] > 0) this.StateTimer[ID.T.MountSkillCD2]--;
  		if (this.StateTimer[ID.T.MountSkillCD3] > 0) this.StateTimer[ID.T.MountSkillCD3]--;
  		if (this.StateTimer[ID.T.MountSkillCD4] > 0) this.StateTimer[ID.T.MountSkillCD4]--;
  		if (this.StateTimer[ID.T.MountSkillCD5] > 0) this.StateTimer[ID.T.MountSkillCD5]--;
  	}
  	
  	/** update rotate */
  	protected void updateClientBodyRotate()
  	{
  		if (!this.isRiding())
  		{
  			if (this.morphHost != null)
  			{
  				this.rotationYaw = this.morphHost.rotationYaw;
  			}
  			else
  			{
  				if (MathHelper.abs((float) (posX - prevPosX)) > 0.1F || MathHelper.abs((float) (posZ - prevPosZ)) > 0.1F)
  	  	  		{
  	  	  			float[] degree = CalcHelper.getLookDegree(posX - prevPosX, posY - prevPosY, posZ - prevPosZ, true);
  	  	  			this.rotationYaw = degree[0];
  	  	  		}
  			}
  		}
  	}
  	
  	/** change ship outfit */
  	public void setShipOutfit(int id)
  	{
  		if (id > this.getStateMinor(ID.M.NumState) || id < 0) id = 0;
  		this.setStateEmotion(ID.S.State, this.getStateEmotion(ID.S.State) ^ Values.N.Pow2[id], false);
  	}
  	
  	public void setSensitiveBody(int par1)
  	{
  		setStateMinor(ID.M.SensBody, par1);
  	}
  	
  	public int getSensitiveBody()
  	{
  		return getStateMinor(ID.M.SensBody);
  	}
  	
  	/** set random sensitive body id, ref: ID.Body */
  	public void randomSensitiveBody()
  	{
  		int ran = this.rand.nextInt(100);
  		int bodyid = 20;
  		
  		//first roll
  		if (ran > 80)
  		{  //20%
  			bodyid = ID.Body.UBelly;
  		}
  		else if (ran > 65)
  		{  //15%
  			bodyid = ID.Body.Chest;
  		}
  		else
  		{  //55%
  			bodyid = 3 + this.rand.nextInt(8);  //roll 3~10
  		}
  		
  		//reroll if HEAD/BACK
		if (bodyid == ID.Body.Head || bodyid == ID.Body.Back) bodyid = ID.Body.UBelly;
		if (bodyid == ID.Body.Arm || bodyid == ID.Body.Butt) bodyid = ID.Body.Chest;
  		
  		setSensitiveBody(bodyid);
  	}
  	
  	/** knockback AI target */
  	public void pushAITarget()
  	{
  		if (this.aiTarget != null)
  		{
  			//swing arm
  			this.swingArm(EnumHand.MAIN_HAND);
  			
  			//push target
  			this.aiTarget.addVelocity(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * 0.5F, 
  	               0.5D, MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * 0.5F);
  		
  			//sync target motion
  			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
  			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this.aiTarget, 0, S2CEntitySync.PID.SyncEntity_Motion), point);
  		}
	}
  	
  	//check caress state for model display, CLIENT SIDE
  	public void checkCaressed()
  	{
  		BodyHeight hit = getBodyIDFromHeight();
  		
  		//default: only top or head = caressed
  		if (hit == BodyHeight.TOP || hit == BodyHeight.HEAD ||
  			hit == BodyHeight.NECK || hit == BodyHeight.CHEST)
  		{
  			setStateEmotion(ID.S.Emotion3, ID.Emotion3.CARESS, false);
  			setStateTimer(ID.T.Emotion3Time, 80);
  		}
  	}
  	
  	/** normal emotes for head caress */
  	public void reactionNormal()
  	{
  		Random ran = new Random();
  		int m = this.getMorale();
  		int body = EntityHelper.getHitBodyID(this);
  		int baseMorale = (int) ((float)ConfigHandler.baseCaressMorale * 2.5F);
  		LogHelper.debug("DEBUG: hit ship: Morale: "+m+" BodyID: "+body+" sensitiveBodyID: "+this.getSensitiveBody()); 		
  		
  		//show emotes by morale level
		switch (EntityHelper.getMoraleLevel(m))
		{
		case 0:   //excited
			//check sensitive body
	  		if (body == getSensitiveBody())
	  		{
		  		//apply emotion
		  		this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
		  		
	  			if (this.rand.nextInt(2) == 0)
	  			{
	  				applyParticleEmotion(31);  //shy
	  			}
	  			else
	  			{
	  				applyParticleEmotion(10);  //dizzy
	  			}
	  			
	  			if (m < (int)(ID.Morale.L_Excited * 1.5F))
	  			{
	  				this.addMorale(baseMorale * 3 + this.rand.nextInt(baseMorale + 1));
	  			}
	  		}
	  		//other reaction
	  		else
	  		{
		  		//apply emotion
		  		this.setStateEmotion(ID.S.Emotion, ID.Emotion.XD, true);
	  			
				switch (body)
				{
				case ID.Body.UBelly:
				case ID.Body.Butt:
				case ID.Body.Chest:
				case ID.Body.Face:
					if (this.getStateFlag(ID.F.IsMarried))
					{
						applyParticleEmotion(15);  //kiss
					}
					else
					{
						applyParticleEmotion(1);  //heart
					}
					break;
				default:
					if (this.rand.nextInt(2) == 0)
					{
						applyParticleEmotion(1);  //heart
					}
					else
					{
						applyParticleEmotion(7);  //note
					}
					break;
				}
	  		}
			break;
		case 1:   //happy
	  		//apply emotion
	  		this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
	  		
			//check sensitive body
	  		if (body == getSensitiveBody())
	  		{
	  			if (this.getStateFlag(ID.F.IsMarried))
	  			{
	  				if (this.rand.nextInt(2) == 0)
	  				{
		  				applyParticleEmotion(31);  //shy
		  			}
		  			else
		  			{
		  				applyParticleEmotion(10);  //dizzy
		  			}
				}
				else
				{
					applyParticleEmotion(10);  //dizzy
				}
	  			
	  			this.addMorale(baseMorale + this.rand.nextInt(baseMorale + 1));
	  		}
	  		//other reaction
	  		else
	  		{
	  			switch (body)
	  			{
				case ID.Body.UBelly:
				case ID.Body.Butt:
				case ID.Body.Chest:
				case ID.Body.Face:
					if (this.getStateFlag(ID.F.IsMarried))
					{
						applyParticleEmotion(1);  //heart
					}
					else
					{
						applyParticleEmotion(16);  //haha
					}
					break;
				default:
					if (this.rand.nextInt(2) == 0)
					{
						applyParticleEmotion(1);  //heart
					}
					else
					{
						applyParticleEmotion(7);  //note
					}
					break;
				}
	  		}
			break;
		case 2:   //normal
			//check sensitive body
	  		if (body == getSensitiveBody())
	  		{
		  		//apply emotion
		  		this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
		  		
	  			if (this.getStateFlag(ID.F.IsMarried))
	  			{
	  				applyParticleEmotion(19);  //lick
				}
				else
				{
					applyParticleEmotion(18);  //sigh
				}
	  			
	  			this.addMorale(baseMorale + this.rand.nextInt(baseMorale + 1));
	  			
	  			//push target
	  			if (ran.nextInt(6) == 0)
	  			{
	  				this.pushAITarget();
	  				this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
	  			}
	  		}
	  		//other reaction
	  		else
	  		{
	  			switch (body)
	  			{
				case ID.Body.UBelly:
				case ID.Body.Butt:
				case ID.Body.Chest:
				case ID.Body.Face:
					if (this.getStateFlag(ID.F.IsMarried))
					{
						applyParticleEmotion(1);  //heart
					}
					else
					{
						applyParticleEmotion(27);  //-w-
					}
					
					//push target
		  			if (ran.nextInt(8) == 0)
		  			{
		  				this.pushAITarget();
		  				this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
		  			}
					break;
				default:
					switch (this.rand.nextInt(7))
					{
					case 1:
						applyParticleEmotion(30);  //pif
						break;
					case 3:
						applyParticleEmotion(7);  //note
						break;
					case 4:
						applyParticleEmotion(26);  //ya
						break;
					case 6:
						applyParticleEmotion(11);  //find
						break;
					default:
						applyParticleEmotion(29);  //blink
						break;
					}
					break;
				}
	  		}
			break;
		case 3:   //tired
			//check sensitive body
	  		if  (body == getSensitiveBody())
	  		{
		  		//apply emotion
		  		this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
		  		
	  			applyParticleEmotion(32);  //hmm
	  			this.addMorale(this.rand.nextInt(baseMorale + 1));
	  			
	  			//push target
	  			if (ran.nextInt(2) == 0)
	  			{
	  				this.pushAITarget();
	  				this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
	  			}
	  			else if (this.aiTarget != null && ran.nextInt(8) == 0)
	  			{
	  				switch (ran.nextInt(3))
	  				{
			    	case 0:
			    		attackEntityWithAmmo(this.aiTarget);
			    		break;
			    	case 1:
			    		attackEntityWithHeavyAmmo(this.aiTarget);
			    		break;
			    	default:
			    		attackEntityAsMob(this.aiTarget);
			    		break;
			    	}
	  			}
	  		}
	  		//other reaction
	  		else
	  		{
	  			switch (body)
	  			{
				case ID.Body.UBelly:
				case ID.Body.Butt:
				case ID.Body.Chest:
				case ID.Body.Face:
					setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
					applyParticleEmotion(32);  //hmm
					//push target
		  			if (ran.nextInt(4) == 0)
		  			{
		  				this.pushAITarget();
		  				this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
		  			}
					break;
				default:
					switch (this.rand.nextInt(5))
					{
					case 1:
						applyParticleEmotion(30);  //pif
						break;
					case 2:
						applyParticleEmotion(2);  //panic
						break;
					case 4:
						applyParticleEmotion(3);  //?
						break;
					default:
						applyParticleEmotion(0);  //sweat
						break;
					}
					break;
				}
	  		}
			break;
		default:  //exhausted
			//check sensitive body
	  		if (body == getSensitiveBody())
	  		{
		  		//apply emotion
		  		this.setStateEmotion(ID.S.Emotion, ID.Emotion.SHY, true);
		  		
	  			applyParticleEmotion(6);  //angry
	  			this.addMorale((baseMorale * 10 + this.rand.nextInt(baseMorale * 5 + 1)) * -1);
	  			
	  			//push target
  				this.pushAITarget();
  				this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
			    
			    if (this.aiTarget != null && ran.nextInt(3) == 0)
			    {
			    	switch (ran.nextInt(3))
			    	{
			    	case 0:
			    		attackEntityWithAmmo(this.aiTarget);
			    		break;
			    	case 1:
			    		attackEntityWithHeavyAmmo(this.aiTarget);
			    		break;
			    	default:
			    		attackEntityAsMob(this.aiTarget);
			    		break;
			    	}
	  			}
	  		}
	  		//other reaction
	  		else
	  		{
	  			switch (body)
	  			{
				case ID.Body.UBelly:
				case ID.Body.Butt:
				case ID.Body.Chest:
				case ID.Body.Face:
					setStateEmotion(ID.S.Emotion, ID.Emotion.T_T, true);
					
					if (this.rand.nextInt(3) == 0)
					{
						applyParticleEmotion(6);  //angry
					}
					else
					{
						applyParticleEmotion(32);  //hmm
					}
					
					//push target
		  			if (ran.nextInt(2) == 0)
		  			{
		  				this.pushAITarget();
		  				this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
		  			}
		  			else if (this.aiTarget != null && ran.nextInt(5) == 0)
		  			{
		  				switch (ran.nextInt(3))
		  				{
				    	case 0:
				    		attackEntityWithAmmo(this.aiTarget);
				    		break;
				    	case 1:
				    		attackEntityWithHeavyAmmo(this.aiTarget);
				    		break;
				    	default:
				    		attackEntityAsMob(this.aiTarget);
				    		break;
				    	}
		  			}
					break;
				default:
					switch (this.rand.nextInt(5))
					{
					case 1:
						applyParticleEmotion(8);  //cry
						break;
					case 2:
						applyParticleEmotion(2);  //panic
						break;
					case 3:
						applyParticleEmotion(20);  //orz
						break;
					case 4:
						applyParticleEmotion(5);  //...
						break;
					default:
						applyParticleEmotion(34);  //lll
						break;
					}
					break;
				}
	  		}
			break;
		}//end morale level switch
  	}
  	
  	/** stranger (not owner) emotes */
  	public void reactionStranger()
  	{
  		int body = EntityHelper.getHitBodyID(this);
  		LogHelper.debug("DEBUG: hit ship: BodyID: "+body+" sensitiveBodyID: "+this.getSensitiveBody()); 		

		//check sensitive body
  		if (body == getSensitiveBody())
  		{
	  		//apply emotion
	  		this.setStateEmotion(ID.S.Emotion, ID.Emotion.ANGRY, true);
	  		
  			if (this.rand.nextInt(2) == 0)
  			{
				applyParticleEmotion(6);  //angry
			}
			else
			{
				applyParticleEmotion(22);  //x
			}
  			
  			//push target
  			if (rand.nextInt(2) == 0)
  			{
  				this.pushAITarget();
  				this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
  			}
  			else if (this.aiTarget != null && rand.nextInt(4) == 0)
  			{
  				switch (rand.nextInt(3))
  				{
		    	case 0:
		    		attackEntityWithAmmo(this.aiTarget);
		    		break;
		    	case 1:
		    		attackEntityWithHeavyAmmo(this.aiTarget);
		    		break;
		    	default:
		    		attackEntityAsMob(this.aiTarget);
		    		break;
		    	}
  			}
  		}
  		//other reaction
  		else
  		{
	  		//apply emotion
	  		this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
	  		
  			switch (body)
  			{
			case ID.Body.UBelly:
			case ID.Body.Butt:
			case ID.Body.Chest:
			case ID.Body.Face:
				if (this.rand.nextInt(2) == 0)
				{
					applyParticleEmotion(6);  //angry
				}
				else
				{
					applyParticleEmotion(5);  //...
				}
				
				//push target
	  			if (rand.nextInt(4) == 0)
	  			{
	  				this.pushAITarget();
	  				this.playSound(this.getCustomSound(5, this), this.getSoundVolume(), 1F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
	  			}
	  			else if (this.aiTarget != null && rand.nextInt(8) == 0)
	  			{
	  				switch (rand.nextInt(3))
	  				{
			    	case 0:
			    		attackEntityWithAmmo(this.aiTarget);
			    		break;
			    	case 1:
			    		attackEntityWithHeavyAmmo(this.aiTarget);
			    		break;
			    	default:
			    		attackEntityAsMob(this.aiTarget);
			    		break;
			    	}
	  			}
				break;
			default:
				switch (this.rand.nextInt(7))
				{
				case 1:
					applyParticleEmotion(9);  //hungry
					break;
				case 2:
					applyParticleEmotion(2);  //panic
					break;
				case 3:
					applyParticleEmotion(20);  //orz
					break;
				case 4:
					applyParticleEmotion(8);  //cry
					break;
				case 5:
					applyParticleEmotion(0);  //sweat
					break;
				default:
					applyParticleEmotion(34);  //lll
					break;
				}
				break;
			}
  		}
  	}
  	
  	/** damaged emotes */
  	public void reactionAttack()
  	{
  		//show emotes by morale level
		switch (EntityHelper.getMoraleLevel(this.getMorale()))
		{
		case 0:   //excited
	  		//apply emotion
	  		this.setStateEmotion(ID.S.Emotion, ID.Emotion.XD, true);
	  		
			switch (this.rand.nextInt(8))
			{
			case 1:
				applyParticleEmotion(33);  //:p
				break;
			case 2:
				applyParticleEmotion(17);  //gg
				break;
			case 3:
				applyParticleEmotion(19);  //lick
				break;
			case 4:
				applyParticleEmotion(16);  //ha
				break;
			default:
				applyParticleEmotion(7);  //note
				break;
			}
			break;
		case 1:   //happy
		case 2:   //normal
		case 3:   //tired
		default:  //exhausted
			switch (this.rand.nextInt(8))
			{
			case 1:
				applyParticleEmotion(14);  //+_+
				break;
			case 2:
				applyParticleEmotion(30);  //pif
				break;
			case 3:
				applyParticleEmotion(7);  //note
				break;
			case 4:
				applyParticleEmotion(4);  //!
				break;
			case 5:
				applyParticleEmotion(7);  //note
				break;
			default:
				applyParticleEmotion(6);  //angry
				break;
			}
			break;
		}//end morale level switch
  	}
  	
  	/** damaged emotes */
  	public void reactionDamaged()
  	{
  		int body = EntityHelper.getHitBodyID(this);
  		
  		//show emotes by morale level
		switch (EntityHelper.getMoraleLevel(this.getMorale()))
		{
		case 0:   //excited
		case 1:   //happy
		case 2:   //normal
			//check sensitive body
	  		if (body == getSensitiveBody())
	  		{
	  			applyParticleEmotion(6);  //angry
	  		}
	  		//other reaction
	  		else
	  		{
				switch (body)
				{
				case ID.Body.UBelly:
				case ID.Body.Butt:
				case ID.Body.Chest:
				case ID.Body.Face:
					applyParticleEmotion(6);  //angry
					break;
				default:
					switch (this.rand.nextInt(7))
					{
					case 1:
						applyParticleEmotion(30);  //pif
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
					default:
						applyParticleEmotion(8);  //cry
						break;
					}
					break;
				}
	  		}
			break;
		case 3:   //tired
		default:  //exhausted
			//check sensitive body
	  		if (body == getSensitiveBody())
	  		{
	  			applyParticleEmotion(10);  //dizzy
	  		}
	  		//other reaction
	  		else
	  		{
	  			switch (body)
	  			{
				case ID.Body.UBelly:
				case ID.Body.Butt:
				case ID.Body.Chest:
				case ID.Body.Face:
					applyParticleEmotion(10);  //dizzy
					break;
				default:
					switch (this.rand.nextInt(7))
					{
					case 1:
						applyParticleEmotion(30);  //pif
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
						applyParticleEmotion(0);  //sweat
						break;
					default:
						applyParticleEmotion(8);  //cry
						break;
					}
				}
	  		}
			break;
		}//end morale level switch
  	}
  	
  	/** idle emotes */
  	public void reactionIdle()
  	{
  		//show emotes by morale level
		switch (EntityHelper.getMoraleLevel(this.getMorale()))
		{
		case 0:   //excited
		case 1:   //happy
			if (this.getStateFlag(ID.F.IsMarried) && this.rand.nextInt(2) == 0)
			{
				switch (this.rand.nextInt(3))
				{
				case 1:
					applyParticleEmotion(31);  //shy
					break;
				default:
					applyParticleEmotion(15);  //kiss
					break;
				}
				
				return;
			}
			
			switch (this.rand.nextInt(10))
			{
			case 1:
				applyParticleEmotion(33);  //:p
				break;
			case 2:
				applyParticleEmotion(17);  //gg
				break;
			case 3:
				applyParticleEmotion(19);  //lick
				break;
			case 4:
				applyParticleEmotion(9);  //hungry
				break;
			case 5:
				applyParticleEmotion(1);  //love
				break;
			case 6:
				applyParticleEmotion(15);  //kiss
				break;
			case 7:
				applyParticleEmotion(16);  //haha
				break;
			case 8:
				applyParticleEmotion(14);  //+_+
				break;
			default:
				applyParticleEmotion(7);  //note
				break;
			}
			break;
		case 2:   //normal
			if (this.getStateFlag(ID.F.IsMarried) && this.rand.nextInt(2) == 0)
			{
				switch (this.rand.nextInt(3))
				{
				case 1:
					applyParticleEmotion(1);  //love
					break;
				default:
					applyParticleEmotion(15);  //kiss
					break;
				}
				
				return;
			}
			
			switch (this.rand.nextInt(8))
			{
			case 1:
				applyParticleEmotion(11);  //find
				break;
			case 2:
				applyParticleEmotion(3);  //?
				break;
			case 3:
				applyParticleEmotion(13);  //nod
				break;
			case 4:
				applyParticleEmotion(9);  //hungry
				break;
			case 5:
				applyParticleEmotion(18);  //sigh
				break;
			case 7:
				applyParticleEmotion(16);  //haha
				break;
			default:
				applyParticleEmotion(29);  //blink
				break;
			}
			break;
		case 3:   //tired
		default:  //exhausted
			switch (this.rand.nextInt(8))
			{
			case 1:
				applyParticleEmotion(0);  //drop
				break;
			case 2:
				applyParticleEmotion(2);  //panic
				break;
			case 3:
				applyParticleEmotion(3);  //?
				break;
			case 4:
				applyParticleEmotion(8);  //cry
				break;
			case 5:
				applyParticleEmotion(10);  //dizzy
				break;
			case 6:
				applyParticleEmotion(20);  //orz
				break;
			default:
				applyParticleEmotion(32);  //hmm
				break;
			}
			break;
		}//end morale level switch
  	}
  	
  	/** command emotes */
  	public void reactionCommand()
  	{
  		//show emotes by morale level
		switch (EntityHelper.getMoraleLevel(this.getMorale()))
		{
		case 0:   //excited
		case 1:   //happy
		case 2:   //normal
			switch (this.rand.nextInt(7))
			{
			case 1:
				applyParticleEmotion(21);  //o
				break;
			case 2:
				applyParticleEmotion(4);  //!
				break;
			case 3:
				applyParticleEmotion(14);  //+_+
				break;
			case 4:
				applyParticleEmotion(11);  //find
				break;
			default:
				applyParticleEmotion(13);  //nod
				break;
			}
			break;
		case 3:   //tired
		default:  //exhausted
			switch (this.rand.nextInt(8))
			{
			case 1:
				applyParticleEmotion(0);  //drop
			case 2:
				applyParticleEmotion(33);  //:p
				break;
			case 3:
				applyParticleEmotion(3);  //?
				break;
			case 5:
				applyParticleEmotion(10);  //dizzy
				break;
			case 6:
				applyParticleEmotion(13);  //nod
				break;
			default:
				applyParticleEmotion(32);  //hmm
				break;
			}
			break;
		}//end morale level switch
  	}
  	
  	/** shock emotes */
  	public void reactionShock()
  	{
		switch (this.rand.nextInt(8))
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
  	
  	/** emotes method
  	 * 
  	 *  type:
  	 *  0: caress head (owner)
  	 *  1: caress head (other)
  	 *  2: damaged
  	 *  3: attack
  	 *  4: idle
  	 *  5: command
  	 *  6: shock
  	 */
  	public void applyEmotesReaction(int type)
  	{
  		Random ran = new Random();
  		
  		switch (type)
  		{
  		case 1:		//caress head (no fuel / not owner)
  			if (ran.nextInt(9) == 0 && this.getEmotesTick() <= 0)
  			{
				this.setEmotesTick(60);
				reactionStranger();
			}
  			break;
  		case 2:		//damaged
  			if (this.getEmotesTick() <= 10)
  			{
				this.setEmotesTick(40);
				reactionDamaged();
			}
  			break;
  		case 3:		//attack
  			if (ran.nextInt(6) == 0 && this.getEmotesTick() <= 0)
  			{
				this.setEmotesTick(60);
				reactionAttack();
			}
  			break;
  		case 4:		//idle
  			if (ran.nextInt(3) == 0 && this.getEmotesTick() <= 0)
  			{
				this.setEmotesTick(20);
				reactionIdle();
			}
  			break;
  		case 5:
  			if (ran.nextInt(3) == 0 && this.getEmotesTick() <= 0)
  			{
				this.setEmotesTick(25);
				reactionCommand();
			}
  			break;
  		case 6:
			reactionShock();
  			break;
  		default: //caress head (owner)
  			if (ran.nextInt(7) == 0 && this.getEmotesTick() <= 0)
  			{
				this.setEmotesTick(50);
				reactionNormal();
			}
  			break;
  		}
  	}
  	
  	/** spawn emotion particle */
  	public void applyParticleEmotion(int type)
  	{
  		float h = isSitting() ? this.height * 0.4F : this.height * 0.45F;
  		
  		//server side emotes
  		if (!this.world.isRemote)
  		{
  			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  	      	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 36, h, 0, type), point);
  		}
  		//client side emotes
  		else
  		{
  			ParticleHelper.spawnAttackParticleAtEntity(this, h, 0, type, (byte)36);
  		}
  	}
  	
  	/** attack particle at attacker
  	 * 
  	 *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
  	 */
  	public void applyParticleAtAttacker(int type, Entity target, Dist4d distance)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY, this.posZ, distance.x, distance.y, distance.z, true), point);
  		break;
  		case 2:  //heavy cannon
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
  		}
  	}
  	
  	/** attack particle at target
  	 * 
  	 *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
  	 */
  	public void applyParticleAtTarget(int type, Entity target, Dist4d distance)
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
  	
  	/** attack particle at attacker
  	 * 
  	 *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
  	 */
  	public void applySoundAtAttacker(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			this.playSound(ModSounds.SHIP_FIRELIGHT, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  	        
  			//entity sound
  			if (this.rand.nextInt(8) == 0)
  			{
  				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
  		case 2:  //heavy cannon
  			this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  	        
  	        //entity sound
  	        if (this.getRNG().nextInt(8) == 0)
  	        {
  	        	this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  			this.playSound(ModSounds.SHIP_AIRCRAFT, ConfigHandler.volumeFire * 0.5F, this.getSoundPitch() * 0.85F);
  	  	
  	        //entity sound
  	        if (this.getRNG().nextInt(8) == 0)
  	        {
  	        	this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
		default: //melee
			if (this.getRNG().nextInt(3) == 0)
			{
				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
	        }
		break;
  		}//end switch
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
  	
  	//get # inv pages ship have
  	public int getInventoryPageSize()
  	{
  		return this.StateMinor[ID.M.DrumState];
  	}
  	
  	//set # inv pages ship have
  	public void setInventoryPageSize(int par1)
  	{
  		this.StateMinor[ID.M.DrumState] = par1;
  	}
  	
  	/** set flare on target */
  	public void flareTarget(Entity target)
  	{
  		//server side, send flare packet
  		if (!this.world.isRemote)
  		{
  	  		if (this.getStateMinor(ID.M.LevelFlare) > 0 && target != null)
  	  		{
  	  			this.flareTarget(target.getPosition());
  	  		}
  		}
  	}
  	
  	public void flareTarget(BlockPos target)
  	{
  		//server side, send flare packet
  		if (!this.world.isRemote)
  		{
  			if (this.getStateMinor(ID.M.LevelFlare) > 0 && target != null)
  	  		{
				TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
				CommonProxy.channelI.sendToAllAround(new S2CReactPackets(S2CReactPackets.PID.FlareEffect, target.getX(), target.getY(), target.getZ()), point);
  	  		}
  		}
  	}
  	
  	/** release ticket when dead */
  	@Override
  	public void setDead()
  	{
  		//clear chunk loader
  		this.clearChunkLoader();
  		
  		super.setDead();
  		
  		//update ship cache
  		this.updateShipCacheDataWithoutNewID();
    }

  	
  	/** release chunk loader ticket */
  	public void clearChunkLoader()
  	{
  		//unforce chunk
  		if (this.chunks != null)
  		{
  	  		for(ChunkPos p : this.chunks)
  	  		{
				ForgeChunkManager.unforceChunk(this.chunkTicket, p);
			}
  		}

  		//release ticket
  		if (this.chunkTicket != null)
  		{
  	  		ForgeChunkManager.releaseTicket(this.chunkTicket);
  		}
  		
  		this.chunks = null;
  		this.chunkTicket = null;
  	}
  	
  	/** chunk loader ticking */
  	public void updateChunkLoader()
  	{
  		if (!this.world.isRemote)
  		{
  			//set ticket
  	  		setChunkLoader();
  	  		
  	  		//apply ticket
  	  		applyChunkLoader();
  		}
  	}
  	
  	/** request chunk loader ticket
  	 * 
  	 *  player must be ONLINE
  	 */
  	private void setChunkLoader()
  	{
  		//if equip chunk loader
  		if (this.getStateMinor(ID.M.LevelChunkLoader) > 0)
  		{
  			EntityPlayer player = null;
  			int uid = this.getPlayerUID();
  			
  			//check owner exists
  			if (uid > 0)
  			{
  				player = EntityHelper.getEntityPlayerByUID(uid);
  				
  				//player is online and no ticket
  				if (player != null && this.chunkTicket == null)
  				{
					//get ticket
			  		this.chunkTicket = ForgeChunkManager.requestPlayerTicket(ShinColle.instance, player.getName(), world, ForgeChunkManager.Type.ENTITY);
  				
			  		if (this.chunkTicket != null)
			  		{
			  			this.chunkTicket.bindEntity(this);
			  		}
			  		else
			  		{
			  			LogHelper.debug("DEBUG: Ship get chunk loader ticket fail.");
			  			clearChunkLoader();
			  		}
  				}
  			}//end check player
  		}//end can chunk loader
  		else
  		{
  			clearChunkLoader();
  		}
  	}
  	
  	/** force chunk load */
  	private void applyChunkLoader()
  	{
  		if (this.chunkTicket != null)
  		{
  			HashSet<ChunkPos> unloadChunks = new HashSet<ChunkPos>();
  			HashSet<ChunkPos> loadChunks = null;
  			HashSet<ChunkPos> tempChunks = new HashSet<ChunkPos>();
  			
  			//get chunk x,z
  			int cx = MathHelper.floor(this.posX) >> 4;
			int cz = MathHelper.floor(this.posZ) >> 4;
			
  			//get new chunk
			loadChunks = BlockHelper.getChunksWithinRange(world, cx, cz, ConfigHandler.chunkloaderMode);
  			
			//get unload chunk
			if (this.chunks != null)
			{
				unloadChunks.addAll(this.chunks);	//copy old chunks
			}
			unloadChunks.removeAll(loadChunks);		//remove all load chunks
			
			//get load chunk
			tempChunks.addAll(loadChunks);			//copy new chunks
			if (this.chunks != null)
			{
				loadChunks.removeAll(this.chunks);	//remove all old chunks
			}
			
			//set old chunk
			this.chunks = tempChunks;
			
  			//unforce unload chunk
			for(ChunkPos p : unloadChunks)
			{
				ForgeChunkManager.unforceChunk(this.chunkTicket, p);
			}
			
			//force load chunk
			for(ChunkPos p : loadChunks)
			{
				ForgeChunkManager.forceChunk(this.chunkTicket, p);
			}
			
			LogHelper.debug("DEBUG : ship chunk loader: "+this.chunks+" "+this);
        }
  	}
  	
  	//get last waypoint, for waypoint loop checking
  	@Override
  	public BlockPos getLastWaypoint()
  	{
  		return this.waypoints[0];
  	}
  	
  	@Override
  	public void setLastWaypoint(BlockPos pos)
  	{
  		this.waypoints[0] = pos;
  	}
  	
  	//convert wp stay time to ticks
  	public static int wpStayTime2Ticks(int wpstay)
  	{
  		switch (wpstay)
  		{
  		case 1:
  		case 2:
  		case 3:
  		case 4:
  		case 5:
  			return wpstay * 100;
  		case 6:
  		case 7:
  		case 8:
  		case 9:
  		case 10:
  			return (wpstay - 5) * 1200;
  		case 11:
  		case 12:
  		case 13:
  		case 14:
  		case 15:
  		case 16:
  			return (wpstay - 10) * 12000;
		default:
			return 0;
  		}
  	}

	@Override
	public int getWpStayTime()
	{
		return getStateTimer(ID.T.WpStayTime);
	}

	@Override
	public int getWpStayTimeMax()
	{
		return wpStayTime2Ticks(getStateMinor(ID.M.WpStay));
	}

	@Override
	public void setWpStayTime(int time)
	{
		setStateTimer(ID.T.WpStayTime, time);
	}
	
	//不跟aircraft, mount碰撞
	@Override
  	protected void collideWithEntity(Entity target)
	{
  		if (target instanceof BasicEntityAirplane)
  		{
  			return;
  		}
  		
//  		for (Entity p : this.getPassengers())
//  		{
//  			if (target.equals(p)) return;
//  		}
  		
  		target.applyEntityCollision(this);
    }
	
	@Override
    public int getPortalCooldown()
    {
        return 40;
    }
	
  	/**
  	 * get/setField為GUI container更新用
  	 * 使資料可以只用相同方法取值, 不用每個資料用各自方法取值
  	 * 方便for loop撰寫
  	 */
	public int getFieldCount()
	{
		return 35;
	}
	
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return this.StateMinor[ID.M.ExpCurrent];
		case 1:
			return this.StateMinor[ID.M.NumAmmoLight];
		case 2:
			return this.StateMinor[ID.M.NumAmmoHeavy];
		case 3:
			return this.StateMinor[ID.M.NumAirLight];
		case 4:
			return this.StateMinor[ID.M.NumAirHeavy];
		case 5:
			return this.getStateFlagI(ID.F.UseMelee);
		case 6:
			return this.getStateFlagI(ID.F.UseAmmoLight);
		case 7:
			return this.getStateFlagI(ID.F.UseAmmoHeavy);
		case 8:
			return this.getStateFlagI(ID.F.UseAirLight);
		case 9:
			return this.getStateFlagI(ID.F.UseAirHeavy);
		case 10:
			return this.getStateFlagI(ID.F.IsMarried);
		case 11:
			return this.StateMinor[ID.M.FollowMin];
		case 12:
			return this.StateMinor[ID.M.FollowMax];
		case 13:
			return this.StateMinor[ID.M.FleeHP];
		case 14:
			return this.getStateFlagI(ID.F.PassiveAI);
		case 15:
			return this.getStateFlagI(ID.F.UseRingEffect);
		case 16:
			return this.getStateFlagI(ID.F.OnSightChase);
		case 17:
			return this.getStateFlagI(ID.F.PVPFirst);
		case 18:
			return this.getStateFlagI(ID.F.AntiAir);
		case 19:
			return this.getStateFlagI(ID.F.AntiSS);
		case 20:
			return this.getStateFlagI(ID.F.TimeKeeper);
		case 21:
			return this.getMorale();
		case 22:
			return this.StateMinor[ID.M.DrumState];
		case 23:
			return this.getStateFlagI(ID.F.PickItem);
		case 24:
			return this.StateMinor[ID.M.WpStay];
		case 25:
			return this.StateMinor[ID.M.Kills];
		case 26:
			return this.StateMinor[ID.M.NumGrudge];
		case 27:
			return this.itemHandler.getInventoryPage();
		case 28:
			return this.getStateFlagI(ID.F.ShowHeldItem);
		case 29:
			return this.StateMinor[ID.M.UseCombatRation];
		case 30:
			return this.getStateFlagI(ID.F.AutoPump);
		case 31:
			return this.getStateEmotion(ID.S.State);
		case 32:
			return this.StateMinor[ID.M.Task];
		case 33:
			return this.StateMinor[ID.M.TaskSide];
		case 34:
			return this.getStateFlagI(ID.F.NoFuel);  //for morph entity
		}
		
		return 0;
	}

	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			this.StateMinor[ID.M.ExpCurrent] = value;
		break;
		case 1:
			this.StateMinor[ID.M.NumAmmoLight] = value;
		break;
		case 2:
			this.StateMinor[ID.M.NumAmmoHeavy] = value;
		break;
		case 3:
			this.StateMinor[ID.M.NumAirLight] = value;
		break;
		case 4:
			this.StateMinor[ID.M.NumAirHeavy] = value;
		break;
		case 5:
			this.setStateFlagI(ID.F.UseMelee, value);
		break;
		case 6:
			this.setStateFlagI(ID.F.UseAmmoLight, value);
		break;
		case 7:
			this.setStateFlagI(ID.F.UseAmmoHeavy, value);
		break;
		case 8:
			this.setStateFlagI(ID.F.UseAirLight, value);
		break;
		case 9:
			this.setStateFlagI(ID.F.UseAirHeavy, value);
		break;
		case 10:
			this.setStateFlagI(ID.F.IsMarried, value);
		break;
		case 11:
			this.StateMinor[ID.M.FollowMin] = value;
		break;
		case 12:
			this.StateMinor[ID.M.FollowMax] = value;
		break;
		case 13:
			this.StateMinor[ID.M.FleeHP] = value;
		break;
		case 14:
			this.setStateFlagI(ID.F.PassiveAI, value);
		break;
		case 15:
			this.setStateFlagI(ID.F.UseRingEffect, value);
		break;
		case 16:
			this.setStateFlagI(ID.F.OnSightChase, value);
		break;
		case 17:
			this.setStateFlagI(ID.F.PVPFirst, value);
		break;
		case 18:
			this.setStateFlagI(ID.F.AntiAir, value);
		break;
		case 19:
			this.setStateFlagI(ID.F.AntiSS, value);
		break;
		case 20:
			this.setStateFlagI(ID.F.TimeKeeper, value);
		break;
		case 21:
			this.StateMinor[ID.M.Morale] = value;
		break;
		case 22:
			this.StateMinor[ID.M.DrumState] = value;
		break;
		case 23:
			this.setStateFlagI(ID.F.PickItem, value);
		break;
		case 24:
			this.StateMinor[ID.M.WpStay] = value;
		break;
		case 25:
			this.StateMinor[ID.M.Kills] = value;
		break;
		case 26:
			this.StateMinor[ID.M.NumGrudge] = value;
		break;
		case 27:
			this.itemHandler.setInventoryPage(value);
		break;
		case 28:
			this.setStateFlagI(ID.F.ShowHeldItem, value);
		break;
		case 29:
			this.StateMinor[ID.M.UseCombatRation] = value;
		break;
		case 30:
			this.setStateFlagI(ID.F.AutoPump, value);
		break;
		case 31:
			this.setStateEmotion(ID.S.State, value, false);
		break;
		case 32:
			this.StateMinor[ID.M.Task] = value;
		break;
		case 33:
			this.StateMinor[ID.M.TaskSide] = value;
		break;
		case 34:
			this.setStateFlagI(ID.F.NoFuel, value);	 //for morph entity
		break;
		}
		
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
  	
	@Override
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
    public boolean shouldDismountInWater(Entity rider)
    {
        return false;
    }
	
	@Override
	public int getScaleLevel()
	{
		return 0;
	}
	
	@Override
	public void setScaleLevel(int par1) {}
  	
	@Override
	public Random getRand()
	{
		return this.rand;
	}
	
	@Override
	public double getShipFloatingDepth()
	{
		return 0.32D;
	}

	@Override
	public void setShipFloatingDepth(double par1) {}
	
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
		
		super.heal(healAmount * this.shipAttrs.getAttrsBuffed(ID.Attrs.HPRES));
    }
	
	//death animation
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
        		ParticleHelper.spawnAttackParticleAt(posX-range+this.rand.nextDouble()*range*2D, posY+0.1D+this.rand.nextDouble()*0.3D, posZ-range+this.rand.nextDouble()*range*2D, 1.5D, 0D, 0D, (byte)43);
        	}
    	}
    	
        if (this.deathTime == ConfigHandler.deathMaxTick)
        {
        	//spawn ship egg
	    	if (!this.world.isRemote && this.getStateFlag(ID.F.CanDrop))
	    	{
	    		//set flag to false to prevent multiple drop from unknown bug
	    		this.setStateFlag(ID.F.CanDrop, false);
	    		
	    		//save ship attributes to ship spawn egg
	    		ItemStack egg = new ItemStack(ModItems.ShipSpawnEgg, 1, this.getShipClass()+2);
		    	egg.setTagCompound(EntityHelper.saveShipDataToNBT(this, true));
	    		
		    	//spawn entity item
	    		BasicEntityItem entityItem2 = new BasicEntityItem(this.world, this.posX, this.posY+0.5D, this.posZ, egg);
		    	this.world.spawnEntity(entityItem2);
	    	}
        	
        	//spawn XP orb
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

            //set dead
            this.setDead();

            //spawn smoke particle
            for (int k = 0; k < 20; ++k)
            {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1, new int[0]);
            }
        }
        //clear bug entity
        else if (this.deathTime > ConfigHandler.deathMaxTick && !this.isDead)
        {
        	this.setDead();
        }
    }
	
	@Override
    protected int getExperiencePoints(EntityPlayer player)
    {
    	return 0;
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
	
	@Override
    @Nullable
    public ItemStack getHeldItemMainhand()
    {
		//show morph on player's hand
		if (this.morphHost != null)
		{
			return this.morphHost.getHeldItemMainhand();
		}
		//show item in ship's inventory
		else
		{
			if (this.itemHandler != null) return this.itemHandler.getStackInSlot(22);
			return null;
		}
    }

	@Override
    @Nullable
    public ItemStack getHeldItemOffhand()
    {
		//show morph on player's hand
		if (this.morphHost != null)
		{
			return this.morphHost.getHeldItemOffhand();
		}
		//show item in ship's inventory
		else
		{
			if (this.itemHandler != null) return this.itemHandler.getStackInSlot(23);
			return null;
		}
    }
	
	//check held item can be rendered
	public boolean canShowHeldItem()
	{
		if (!this.getStateFlag(ID.F.ShowHeldItem) || this.getAttackTick() > 0 ||
			this.getAttackTick2() > 0)
		{
			return false;
		}
		
		return true;
	}
	
	public byte[] getBodyHeightStand()
	{
		return this.BodyHeightStand;
	}
	
	public byte[] getBodyHeightSit()
	{
		return this.BodyHeightSit;
	}
	
  	public Enums.BodyHeight getBodyIDFromHeight()
  	{
  		return EntityHelper.getBodyIDFromHeight(getHitHeight(), this);
  	}
  	
  	public Enums.BodySide getHitAngleID()
  	{
  		return EntityHelper.getHitAngleID(getHitAngle());
  	}
  	
  	/**
  	 * FIX: bug that ship be deleted while teleport (dismount)
  	 */
  	@Override
    public void dismountEntity(Entity mount)
    {
  		if (mount != null)
  		{
  			this.setPositionAndUpdate(mount.posX, mount.posY + 1D, mount.posZ);
  		}
  		else
  		{
  			super.dismountEntity(mount);
  		}
    }
  	
	@Override
	public HashMap<Integer, Integer> getBuffMap()
	{
		if (this.BuffMap == null) this.BuffMap = new HashMap<Integer, Integer>();
		return this.BuffMap;
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
		if (data instanceof AttrsAdv) this.shipAttrs = (AttrsAdv) data;
	}
	
	@Override
	public HashMap<Integer, int[]> getAttackEffectMap()
	{
		if (this.AttackEffectMap == null) this.AttackEffectMap = new HashMap<Integer, int[]>();
		return this.AttackEffectMap;
	}

	@Override
	public void setAttackEffectMap(HashMap<Integer, int[]> map)
	{
		this.AttackEffectMap = map;
	}
	
	@Override
    public boolean isGlowing()
    {
		//for client side, if player is owner, show glowing effect when invisible
		if (this.world.isRemote)
		{
			if (this.isInvisible() &&
				TeamHelper.checkSameOwner(this, ClientProxy.getClientPlayer()))
			{
				return true;
			}
		}
		
		return super.isGlowing();
    }
	
	@Override
	public MissileData getMissileData(int type)
	{
		return this.MissileData[type];
	}

	@Override
	public void setMissileData(int type, MissileData data)
	{
		this.MissileData[type] = data;
	}
	
	//init missile data
	public void resetMissileData()
	{
		this.MissileData = new MissileData[5];
		for (int i = 0; i < 5; i++) this.MissileData[i] = new MissileData();
	}
	
	@Override
	public boolean isMorph()
	{
		return this.isMorph;
	}

	@Override
	public void setIsMorph(boolean par1)
	{
		this.isMorph = par1;
	}

	@Override
	public EntityPlayer getMorphHost()
	{
		return this.morphHost;
	}

	@Override
	public void setMorphHost(EntityPlayer player)
	{
		this.morphHost = player;
	}
	
	
}