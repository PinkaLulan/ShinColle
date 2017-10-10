package com.lulan.shincolle.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.ai.EntityAIShipAttackOnCollide;
import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipFollowOwner;
import com.lulan.shincolle.ai.EntityAIShipGuarding;
import com.lulan.shincolle.ai.EntityAIShipOpenDoor;
import com.lulan.shincolle.ai.EntityAIShipWander;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.client.render.IShipCustomTexture;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

/**MOUNT ENTITY
 * mount use cannon attack, no aircraft attack
 * all states get from host ex: sitting, leashed, sprinting...
 */
abstract public class BasicEntityMount extends EntityCreature implements IShipMount, IShipCannonAttack, IShipGuardian, IShipCustomTexture
{
	
	protected static final IAttribute MAX_HP = (new RangedAttribute((IAttribute)null, "generic.maxHealth", 4D, 0D, 30000D)).setDescription("Max Health").setShouldWatch(true);
	protected Attrs shipAttrs;
	
	public BasicEntityShip host;  				//host
	protected ShipPathNavigate shipNavigator;	//水空移動用navigator
	protected ShipMoveHelper shipMoveHelper;
	protected int revengeTime;					//revenge target time
	protected int soundHurtDelay;
    
    //model display
    /**EntityState: 0:HP State 1:Emotion 2:Emotion2*/
	protected int stateEmotion;					//表情1
	protected int stateEmotion2;				//表情2
	protected int attackTime, attackTime2, startEmotion, startEmotion2;  //motion開始時間
	protected boolean headTilt;
	protected float[] seatPos;					//ship rider position
	protected float[] seatPos2;					//player rider position
	
	//AI
	protected double shipDepth;					//水深, 用於水中高度判定
	public int keyPressed;						//key(bit): 0:W 1:S 2:A 3:D 4:Jump
	public int keyTick;						//player controll key tick
	public static boolean stopAI = false;		//stop onUpdate, onLivingUpdate
	
	
    public BasicEntityMount(World world)
    {
		super(world);
		this.isImmuneToFire = true;
		this.ignoreFrustumCheck = true;	//即使不在視線內一樣render
		this.stepHeight = 3F;
		
		//AI flag
		this.keyPressed = 0;
		this.keyTick = 0;
		this.soundHurtDelay = 0;
		this.attackTime = 0;
		this.attackTime2 = 0;
        this.stateEmotion = 0;
        this.stateEmotion2 = 0;
        this.startEmotion = 0;
        this.startEmotion2 = 0;
        this.headTilt = false;
        
        this.shipAttrs = new Attrs();
	}
    
    /** init attributes */
    abstract public void initAttrs(BasicEntityShip host);
    
	//平常音效
    @Override
    @Nullable
    protected SoundEvent getAmbientSound()
    {
    	if (ConfigHandler.useWakamoto && rand.nextInt(30) == 0)
    	{
			return ModSounds.SHIP_WAKA_IDLE;
		}
		return null;
    }
    
	//受傷音效
    @Override
    @Nullable
    protected SoundEvent getHurtSound()
    {
		if (ConfigHandler.useWakamoto && rand.nextInt(30) == 0 && this.soundHurtDelay <= 0)
		{
			this.soundHurtDelay = 20 + this.getRNG().nextInt(40);
			return ModSounds.SHIP_WAKA_HURT;
		}
		return null;
    }

	//死亡音效
    @Override
    @Nullable
    protected SoundEvent getDeathSound()
    {
		if (ConfigHandler.useWakamoto)
		{
			return ModSounds.SHIP_WAKA_DEATH;
		}
		return null;
    }

	//音效大小
    @Override
	protected float getSoundVolume()
    {
		return ConfigHandler.volumeShip * 0.4F;
	}
	
    //音效音高
    @Override
	protected float getSoundPitch()
    {
    	return 1F;
    }
	
    //setup AI
	public void setAIList()
	{
		if (!this.world.isRemote)
		{
			this.clearAITasks();
			this.clearAITargetTasks();

			//high priority
			this.tasks.addTask(1, new EntityAIShipGuarding(this));				//0111
			this.tasks.addTask(2, new EntityAIShipFollowOwner(this));			//0111
			
			//use melee attack
			if (this.getStateFlag(ID.F.UseMelee))
			{
				this.tasks.addTask(12, new EntityAIShipAttackOnCollide(this, 1D));
			}
			
			//idle AI
			//moving
			this.tasks.addTask(21, new EntityAIShipOpenDoor(this, true));		//0000
			this.tasks.addTask(22, new EntityAIShipFloating(this));				//0111
			this.tasks.addTask(25, new EntityAIShipWander(this, 12, 7, 0.8D));	//0111
		}
	}
	
	//called client+server both side!!
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
			this.setDead();
			return false;
		}
		
		//set hurt face
    	this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
    	
    	//change sensitive body
  		if (this.rand.nextInt(10) == 0) this.host.randomSensitiveBody();
        
    	//若攻擊方為owner, 則直接回傳傷害, 不計def跟friendly fire
		if (source.getEntity() instanceof EntityPlayer &&
			TeamHelper.checkSameOwner(source.getEntity(), this))
		{
			this.host.setSitting(false);
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
			Entity entity = source.getEntity();
			
			//不會對自己造成傷害, 可免疫毒/掉落/窒息等傷害 (此為自己對自己造成傷害)
			if (entity.equals(this))
			{
				//取消坐下動作
				this.host.setSitting(false);
				return false;
			}
			
			//若攻擊方為player, 則檢查friendly fire
			if (entity instanceof EntityPlayer)
			{
				//若禁止friendlyFire, 則不造成傷害
				if (!ConfigHandler.friendlyFire)
				{
					return false;
				}
			}
			
			//進行dodge計算
			float dist = (float) this.getDistanceSqToEntity(entity);
			
			if (CombatHelper.canDodge(this, dist))
			{
				return false;
			}
			
			float reducedAtk = atk;
					
			//進行def計算
			if (checkDEF)
			{	//get mounts's def (this.shipAttrs), not host's def (this.getAttrs())
				reducedAtk = CombatHelper.applyDamageReduceByDEF(this.rand, this.shipAttrs, reducedAtk);
			}
			
			//ship vs ship, config傷害調整 (僅限友善船)
			if (entity instanceof IShipOwner && ((IShipOwner)entity).getPlayerUID() > 0 &&
				(entity instanceof BasicEntityShip ||
				 entity instanceof BasicEntitySummon || 
				 entity instanceof BasicEntityMount))
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
			this.host.setSitting(false);
			
	  		//show emotes
	  		if (this.rand.nextInt(5) == 0)
	  		{
				this.host.applyEmotesReaction(2);
	  		}
   
            return super.attackEntityFrom(source, reducedAtk);
        }
		
		return false;
	}
	
	//不跟rider碰撞
	@Override
  	protected void collideWithEntity(Entity target)
  	{
  		if (target.equals(this.getRidingEntity()) || this.isPassenger(target) ||
  			target instanceof BasicEntityAirplane)
  		{
  			return;
  		}
  		
  		target.applyEntityCollision(this);
    }
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand)
    {
		if (hand == EnumHand.OFF_HAND) return EnumActionResult.FAIL;
		
		//server side
		if (!this.world.isRemote)
		{
			//use item
			if (stack != null && host != null)
			{
				//caress head mode: morale +5
				if (stack.getItem() == ModItems.PointerItem && stack.getItemDamage() > 2)
				{
					//add little morale to host
					int t = this.host.ticksExisted - this.host.getMoraleTick();
					
					if (t > 3 && this.host.getMorale() < (int)(ID.Morale.L_Excited * 1.3F))
					{	//if caress > 3 ticks
						this.host.setMoraleTick(this.ticksExisted);
						this.host.addMorale(ConfigHandler.baseCaressMorale);
					}
					
					//TODO show mounts emotion
					
					return EnumActionResult.SUCCESS;
				}
				//use lead: clear path
				else if (stack.getItem() == Items.LEAD)
				{
					this.getShipNavigate().clearPathEntity();
					return EnumActionResult.SUCCESS;
		        }
			}//end use item

			//ride the mount if dist < 4 blocks, or set ship and mount sitting
			if (!player.isSneaking())
			{
				//ride mount, only for friendly player
				if (!TeamHelper.checkIsBanned(this, player) && this.getDistanceSqToEntity(player) < 16D)
				{
	  	  			player.startRiding(this, true);
	  	  			this.stateEmotion = 1;
	  	  			this.sendSyncPacket(0);
	  	  			
					return EnumActionResult.SUCCESS;
				}
				//set sitting
				else if (TeamHelper.checkSameOwner(player, this.host))
				{
					this.host.setEntitySit(!this.host.isSitting());
					this.isJumping = false;
			        this.getShipNavigate().clearPathEntity();
			        this.getNavigator().clearPathEntity();
			        this.setAttackTarget(null);
			        this.setEntityTarget(null);

		            return EnumActionResult.SUCCESS;
				}
	        }
			//shift+right click時打開host GUI
			else if (TeamHelper.checkSameOwner(player, this.host))
			{  
				int eid = this.host.getEntityId();
	    		FMLNetworkHandler.openGui(player, ShinColle.instance, ID.Gui.SHIPINVENTORY, this.world, this.host.getEntityId(), 0, 0);
	    		return EnumActionResult.SUCCESS;
			}
		}
		
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
    
    //special rotate by rider while no key typed (keyTick <= 0)
    protected void setRotationByRider()
    {
	  	//sync rotation
		List<Entity> riders = this.getPassengers();
		
		for (Entity rider : riders)
		{
			if (rider instanceof BasicEntityShip)
			{
				rider.rotationYaw = ((BasicEntityShip) rider).renderYawOffset;
			}
		}//end for sync rotation
    }
	
	@Override
    public void onLivingUpdate()
    {
		 /** server side */
    	if ((!world.isRemote))
    	{
            //clear path if player controlling
            if (this.keyTick > 0)
	      	{
				this.getShipNavigate().clearPathEntity();
	      	}
            
        	//update movement, NOTE: 1.9.4: must done before vanilla MoveHelper updating in super.onLivingUpdate()
        	EntityHelper.updateShipNavigator(this);
        	
            super.onLivingUpdate();
    	}//end server side
    	/** client side */
    	else
    	{
    		super.onLivingUpdate();
			
			//every 32 ticks
    		if (this.ticksExisted % 32 == 0)
    		{
    			//check rider pos
    			if (this.getPassengers().size() > 1)
    			{
    				setStateEmotion(ID.S.Emotion, 1, false);
    			}
    			else
    			{
    				setStateEmotion(ID.S.Emotion, 0, false);
    			}
    			
    			if ((this.ticksExisted & 127) == 0)
        		{
    				//sync exist tick with host ship (for model display)
    				if (this.ticksExisted > 250 && this.host != null)
    				{
    					this.ticksExisted = this.host.ticksExisted;
    				}
        		}//end 128 ticks
    		}//end 32 ticks
    	}//end client side
    }
	
	@Override
	public void onUpdate()
	{
		if (stopAI)
		{
			return;
		}
		
		super.onUpdate();
		
		//timer--
		if (this.soundHurtDelay > 0) this.soundHurtDelay--;
		
		//check depth
		EntityHelper.checkDepth(this);
		
		//client side
		if (this.world.isRemote)
		{
			if (shipDepth > 0D)
			{
				//水中移動時, 產生水花特效
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
					ParticleHelper.spawnAttackParticleAt(this.posX + motX*3D, this.posY + 0.6D, this.posZ + motZ*3D, 
							-motX, this.width, -motZ, (byte)47);
				}
			}
		}//end client side
		//server side
		else
		{
			//owner消失(通常是server restart)
			if (this.host == null)
			{
				this.setDead();
			}
			else
			{		
				//owner還在, 但是已經騎乘別人, 則消除此entity
				if(this.host.getRidingEntity() != this)
				{
					this.setDead();
				}
				
				//get target every 8 ticks
				if (this.ticksExisted % 8 == 0)
				{
					this.setEntityTarget(this.host.getEntityTarget());
					
					//check every 16 ticks
					if ((this.ticksExisted & 15) == 0)
					{
						//waypoint move
	            		if (EntityHelper.updateWaypointMove(this) && this.host.getStateMinor(ID.M.NumGrudge) > 0)
	            		{
	            			this.shipNavigator.tryMoveToXYZ(getGuardedPos(0), getGuardedPos(1), getGuardedPos(2), 1D);
	            			this.host.sendSyncPacket(S2CEntitySync.PID.SyncShip_Guard, true);
	            		}
	            		
	            		//check every 32 ticks
    					if ((this.ticksExisted & 31) == 0)
    					{
    						//update attribute
    						this.setupAttrs();
    						
    						//check every 128 ticks
        					if ((this.ticksExisted & 127) == 0)
        					{
        						this.sendSyncPacket(0);
        					}//end every 128 ticks
    					}//end every 32 ticks
					}//end every 16 ticks
				}//end every 8 ticks
			}//end get host
		}//end server side
		
		/* both side */
		//check every 128 ticks
		if ((this.ticksExisted & 127) == 0)
		{
			this.setAir(300);
		}//end every 128 ticks
		
		//apply movement by key pressed
		if (this.keyTick > 0)
		{
			this.keyTick--;
			
			if (this.host != null && !this.host.getStateFlag(ID.F.NoFuel))
			{
				EntityPlayer rider2 = (EntityPlayer) this.getControllingPassenger();
				
				if (rider2 != null)
				{
					float yaw = rider2.rotationYawHead % 360F * Values.N.DIV_PI_180;
					float pitch = rider2.rotationPitch % 360F * Values.N.DIV_PI_180;
					this.applyMovement(pitch, yaw);
					this.rotationYaw = rider2.rotationYaw;
					this.renderYawOffset = rider2.renderYawOffset;
				}
			}
		}
		else
		{
			//moving rotate
			if (MathHelper.abs((float) (posX - prevPosX)) > 0.001F || MathHelper.abs((float) (posZ - prevPosZ)) > 0.001F)
	  		{
	  			float[] degree = CalcHelper.getLookDegree(posX - prevPosX, posY - prevPosY, posZ - prevPosZ, true);
    			this.rotationYaw = degree[0];
    			
    		  	//sync rotation
    			List<Entity> riders = this.getPassengers();
    			
    			for (Entity rider : riders)
    			{
    				if (rider instanceof BasicEntityShip)
    				{
    					((EntityLivingBase) rider).prevRotationYawHead = this.prevRotationYawHead;
    					((EntityLivingBase) rider).rotationYawHead = this.rotationYawHead;
    					((EntityLivingBase) rider).prevRenderYawOffset = this.prevRenderYawOffset;
    					((EntityLivingBase) rider).renderYawOffset = this.renderYawOffset;
    					rider.prevRotationYaw = this.prevRenderYawOffset;
    					rider.rotationYaw = this.renderYawOffset;
    				}
    			}//end for sync rotation
			}
			//no moving and no looking rotate
			else
			{
				this.setRotationByRider();
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
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
    public boolean writeToNBTOptional(NBTTagCompound nbt)
    {
		//set rider position by mounts to prevent deleted on chunk loading
		for (Entity rider : this.getPassengers())
		{
			rider.setPosition(this.posX, rider.posY, this.posZ);
		}
		
		return super.writeToNBTOptional(nbt);
    }
	
	//set movement by key pressed, pitch/yaw is RAD not DEGREE
	private void applyMovement(float pitch, float yaw)
	{
		//calc move direction by yaw
		final float movSpeed = this.getMoveSpeed();
		float[] movez = CalcHelper.rotateXZByAxis(movSpeed, 0F, yaw, 1F);	//前後
		float[] movex = CalcHelper.rotateXZByAxis(0F, movSpeed, yaw, 1F);	//左右
		
		//apply client side jump
		if ((keyPressed & 16) > 0)	//jump (bit 5)
		{
			this.jumpHelper.setJumping();
//			this.motionY += movSpeed * 0.5F;
//			if (motionY > 1F) motionY = 1F;
		}
		
		//apply moving
		if (this.onGround || EntityHelper.checkEntityIsInLiquid(this))
		{
			//horizontal move, 至少要4 tick才能加到最高速
			//W (bit 1)
			if ((keyPressed & 1) > 0)
			{
				motionX += movez[1] * 0.25F;
				if (MathHelper.abs((float) motionX) > MathHelper.abs(movez[1])) motionX = movez[1];
				motionZ += movez[0] * 0.25F;
				if (MathHelper.abs((float) motionZ) > MathHelper.abs(movez[0])) motionZ = movez[0];
			
				//vertical move
				if (pitch > 1F)
				{
					motionY += -0.1F;
					if (motionY < -movSpeed * 0.5F) motionY = -movSpeed * 0.5F;
				}
				else if (pitch < -1F)
				{
					motionY += 0.1F;
					if (motionY > movSpeed * 0.5F) motionY = movSpeed * 0.5F;
				}
			}
			
			//S (bit 2)
			if ((keyPressed & 2) > 0)
			{
				motionX -= movez[1] * 0.25F;
				if (MathHelper.abs((float) motionX) > MathHelper.abs(movez[1])) motionX = -movez[1];
				motionZ -= movez[0] * 0.25F;
				if (MathHelper.abs((float) motionZ) > MathHelper.abs(movez[0])) motionZ = -movez[0];
			
				//vertical move
				if (pitch > 1F)
				{
					motionY += 0.1F;
					if (motionY > movSpeed * 0.5F) motionY = movSpeed * 0.5F;
				}
				else if (pitch < -1F)
				{
					motionY += -0.1F;
					if (motionY < -movSpeed * 0.5F) motionY = -movSpeed * 0.5F;
				}
			}
			
			//A (bit 3)
			if ((keyPressed & 4) > 0)
			{
				motionX += movex[1] * 0.25F;
				if (MathHelper.abs((float) motionX) > MathHelper.abs(movex[1])) motionX = movex[1];
				motionZ += movex[0] * 0.25F;
				if (MathHelper.abs((float) motionZ) > MathHelper.abs(movex[0])) motionZ = movex[0];
			}
			
			//D (bit 4)
			if ((keyPressed & 8) > 0)
			{
				motionX -= movex[1] * 0.25F;
				if (MathHelper.abs((float) motionX) > MathHelper.abs(movex[1])) motionX = -movex[1];
				motionZ -= movex[0] * 0.25F;
				if (MathHelper.abs((float) motionZ) > MathHelper.abs(movex[0])) motionZ = -movex[0];
			}
			
			//若水平撞到東西, 則嘗試往上擠
			if (this.isCollidedHorizontally)
			{
				this.motionY += 0.4D;
			}
		}
		else
		{
			//不在地面時, 各方向的空中加速度不同, 往前不變, 往後減少, 左右大幅減少
			//W (bit 1)
			if ((keyPressed & 1) > 0)
			{
				motionX += movez[1] * 0.25F;
				if (MathHelper.abs((float) motionX) > MathHelper.abs(movez[1])) motionX = movez[1];
				motionZ += movez[0] * 0.25F;
				if (MathHelper.abs((float) motionZ) > MathHelper.abs(movez[0])) motionZ = movez[0];
			}
			
			//S (bit 2)
			if ((keyPressed & 2) > 0)
			{
				motionX -= movez[1] * 0.25F;
				if (MathHelper.abs((float) motionX) > MathHelper.abs(movez[1])) motionX = -movez[1];
				motionZ -= movez[0] * 0.25F;
				if (MathHelper.abs((float) motionZ) > MathHelper.abs(movez[0])) motionZ = -movez[0];
			}
			
			//A (bit 3)
			if ((keyPressed & 4) > 0)
			{
				motionX += movex[1] * 0.03125F;
				if (MathHelper.abs((float) motionX) > MathHelper.abs(movex[1])) motionX = movex[1];
				motionZ += movex[0] * 0.03125F;
				if (MathHelper.abs((float) motionZ) > MathHelper.abs(movex[0])) motionZ = movex[0];
			}
			
			//D (bit 4)
			if ((keyPressed & 8) > 0)
			{
				motionX -= movex[1] * 0.03125F;
				if (MathHelper.abs((float) motionX) > MathHelper.abs(movex[1])) motionX = -movex[1];
				motionZ -= movex[0] * 0.03125F;
				if (MathHelper.abs((float) motionZ) > MathHelper.abs(movex[0])) motionZ = -movex[0];
			}
		}
	}

	@Override
	public int getStateEmotion(int id)
	{
		return id == ID.S.Emotion ? stateEmotion : stateEmotion2;
	}
	
	@Override
	public void setStateEmotion(int id, int value, boolean sync)
	{	
		switch (id)
		{
		case ID.S.Emotion:
			stateEmotion = value;
		break;
		case ID.S.Emotion2:
			stateEmotion2 = value;
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
	public void setShipDepth(double par1)
	{
		this.shipDepth = par1;
	}

	@Override
	public boolean getStateFlag(int flag)
	{	
		if (this.host != null) return this.host.getStateFlag(flag);
		return false;
	}

	@Override
	public void setStateFlag(int id, boolean flag)
	{
		if (this.host != null) this.host.setStateFlag(id, flag);
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
	public int getAttackTick()
	{
		return this.attackTime;
	}
  	
    //clear AI
  	protected void clearAITasks()
  	{
  		this.tasks.taskEntries.clear();
  	}
  	
  	//clear target AI
  	protected void clearAITargetTasks()
  	{
  		this.targetTasks.taskEntries.clear();
  	}
    
  	@Override
	public Entity getEntityTarget()
  	{
  		if (this.host != null) return this.host.getEntityTarget();
		return null;
	}
  	
  	@Override
	public void setEntityTarget(Entity target)
  	{
  		if (this.host != null) this.host.setEntityTarget(target);
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
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 19, this.posX, this.posY+1.5D, this.posZ, distVec.x, 1.2F, distVec.z, true), point);
  			//發送攻擊flag給host, 設定其attack time
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host, 0, true), point);
  		break;
  		case 2:  //heavy cannon
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
			//發送攻擊flag給host, 設定其attack time
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host, 0, true), point);
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
  			if (this.rand.nextInt(10) > 8)
  			{
  				this.playSound(ModSounds.SHIP_WAKA_ATTACK, this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
  		case 2:  //heavy cannon
  			this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  	        
  	        //entity sound
  	        if (this.getRNG().nextInt(10) > 8)
  	        {
  	        	this.playSound(ModSounds.SHIP_WAKA_ATTACK, this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  			this.playSound(ModSounds.SHIP_AIRCRAFT, ConfigHandler.volumeFire * 0.5F, this.getSoundPitch() * 0.85F);
  	  	break;
		default: //melee
			if (this.getRNG().nextInt(2) == 0)
			{
				this.playSound(ModSounds.SHIP_WAKA_ATTACK, this.getSoundVolume(), this.getSoundPitch());
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
  	 *  type: 0:melee, 1:light cannon, 2:heavy cannon, 3:light air, 4:heavy air
  	 */
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		//if no host = no damage
  		if (this.getAttrs() == null) return 0F;
  		
  		switch (type)
  		{
  		case 1:  //light cannon
  			return CombatHelper.modDamageByAdditionAttrs(this.host, target, this.getAttrs().getAttrsBuffed(ID.Attrs.ATK_L), 0);
  		case 2:  //heavy cannon
  			return this.getAttrs().getAttrsBuffed(ID.Attrs.ATK_H);
  		case 3:  //light aircraft
  			return this.getAttrs().getAttrsBuffed(ID.Attrs.ATK_AL);
  		case 4:  //heavy aircraft
  			return this.getAttrs().getAttrsBuffed(ID.Attrs.ATK_AH);
		default: //melee, mounts can deal 100% melee attack
			return this.getAttrs().getAttrsBuffed(ID.Attrs.ATK_L);
  		}
  	}
  	
  	//melee attack
  	@Override
  	public boolean attackEntityAsMob(Entity target)
  	{
  		//get attack value
  		float atk = getAttackBaseDamage(0, target);
  				
  		//experience++
  		this.host.addShipExp(ConfigHandler.expGain[0]);
  		
  		//attack time
  		this.host.setCombatTick(this.ticksExisted);
  		
        //calc dist to target
        Dist4d distVec = EntityHelper.getDistanceFromA2B(this, target);
  		
  		//entity attack effect
	    applySoundAtAttacker(0, target);
	    applyParticleAtAttacker(0, target, distVec);
  		
  	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
  	    //並且回傳是否成功傷害到目標
  	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk);

  	    //if attack success
  	    if (isTargetHurt)
  	    {
  	    	applySoundAtTarget(0, target);
	        applyParticleAtTarget(0, target, distVec);
			this.host.applyEmotesReaction(3);
			
			if (ConfigHandler.canFlare) this.host.flareTarget(target);
  	    }

  	    return isTargetHurt;
  	}
    
  	//light attack
    @Override
	public boolean attackEntityWithAmmo(Entity target)
    {
    	//ammo--
        if (!this.host.decrAmmoNum(0, this.host.getAmmoConsumption())) return false;
        
        //experience++
        this.host.addShipExp(ConfigHandler.expGain[1]);
  		
  		//grudge--
        this.host.decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk]);
		
  		//combat time
        this.host.setCombatTick(this.ticksExisted);
  		
  		//get attack value
		float atk = getAttackBaseDamage(1, target);
		
        //calc dist to target
        Dist4d distVec = EntityHelper.getDistanceFromA2B(this, target);
		
        //play cannon fire sound at attacker
        applySoundAtAttacker(1, target);
	    applyParticleAtAttacker(1, target, distVec);
	    
	    //roll miss, cri, dhit, thit
	    atk = CombatHelper.applyCombatRateToDamage(this, target, true, (float)distVec.distance, atk);
  		
  		//damage limit on player target
	    atk = CombatHelper.applyDamageReduceOnPlayer(target, atk);
  		
  		//check friendly fire
		if (!TeamHelper.doFriendlyFire(this, target)) atk = 0F;

	    //確認攻擊是否成功
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);
	    		
	    //if attack success
	    if (isTargetHurt)
	    {
	    	applySoundAtTarget(1, target);
	        applyParticleAtTarget(1, target, distVec);
	        this.host.applyEmotesReaction(3);
	        
	        if (ConfigHandler.canFlare) this.host.flareTarget(target);
        }
	    
	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		//ammo--
        if (!this.host.decrAmmoNum(1, this.host.getAmmoConsumption())) return false;
		
        //experience++
      	this.host.addShipExp(ConfigHandler.expGain[2]);
      		
      	//grudge--
      	this.host.decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
      	
  		//attack time
      	this.host.setCombatTick(this.ticksExisted);
		
		//get attack value
		float atk = getAttackBaseDamage(2, target);
		float kbValue = 0.15F;

		//飛彈是否採用直射
		boolean isDirect = false;
		float launchPos = (float) posY + height * 0.75F;
		
        //calc dist to target
        Dist4d distVec = EntityHelper.getDistanceFromA2B(this, target);
				
        //超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
        if (distVec.distance < 7D)
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
        EntityAbyssMissile missile = new EntityAbyssMissile(this.world, this.host, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atk, kbValue, isDirect, -1F);
        this.world.spawnEntity(missile);
  		
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
        this.host.applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) this.host.flareTarget(target);
        
        return true;
	}
	
	@Override
	public boolean updateSkillAttack(Entity target)
	{
		return false;
	}
	
	/**修改移動方法, 使其water跟lava中移動時像是flying entity
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
	@Override
    public void moveEntityWithHeading(float strafe, float forward)
	{
		EntityHelper.moveEntityWithHeading(this, strafe, forward);
    }
	
	@Override
	public int getLevel()
	{
		if (this.host != null) return this.host.getLevel();
		return 150;
	}

	@Override
	public float getMoveSpeed()
	{
		if (!this.getIsSitting() && this.getAttrs() != null)
		{
			return this.getAttrs().getMoveSpeed();
		}
			
		return 0F;
	}
	
	@Override
	public float getJumpSpeed()
	{
		return 2F;
	}
	
	@Override
	public boolean getIsLeashed()
	{
		//綁住host或者自己都算綁住
		if (this.host != null)
		{
			return this.host.getIsLeashed() || this.getLeashed();
		}
		return false;
	}
	
	@Override
	public int getStateMinor(int id)
	{
		if (this.host != null) return this.host.getStateMinor(id);
		return 0;
	}

	@Override
	public void setStateMinor(int state, int par1)
	{
		if (this.host != null) this.host.setStateMinor(state, par1);
	}
	
	@Override
	public int getAmmoLight()
	{
		if (this.host != null) return this.host.getAmmoLight();
		return 0;
	}

	@Override
	public int getAmmoHeavy()
	{
		if (this.host != null) return this.host.getAmmoHeavy();
		return 0;
	}
	
	@Override
	public boolean useAmmoLight()
	{
		if (this.host != null) return this.host.useAmmoLight();
		return false;
	}

	@Override
	public boolean useAmmoHeavy()
	{
		if (this.host != null) return this.host.useAmmoHeavy();
		return false;
	}

	@Override
	public boolean hasAmmoLight()
	{
		return this.getAmmoLight() > 0;
	}

	@Override
	public boolean hasAmmoHeavy()
	{
		return this.getAmmoHeavy() > 0;
	}

	@Override
	public void setAmmoLight(int num) {}	//no use

	@Override
	public void setAmmoHeavy(int num) {}	//no use

	@Override
	public boolean getAttackType(int par1)
	{
		if (this.host != null) return this.host.getAttackType(par1);
		return true;
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
		if (this.host != null) return this.host.getIsSitting();
		return false;
	}

	@Override
	public boolean getIsSneaking()
	{
		return false;
	}

	@Override
	public double getShipDepth()
	{
		return this.shipDepth;
	}
	
	@Override
	public double getShipDepth(int type)
	{
		switch (type)
		{
		case 1:
			return 0D;
		case 2:
			if (this.host != null)
			{
				return this.host.getShipDepth();
			}
			else
			{
				return 0D;
			}
		default:
			return this.shipDepth;
		}
	}
	
	@Override
	public boolean shouldDismountInWater(Entity rider)
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
		return isPotionActive(MobEffects.LEVITATION);
	}
	
	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}
	
	//get first player rider
    @Nullable
    @Override
    public Entity getControllingPassenger()
    {
    	for (Entity rider : this.getPassengers())
    	{
    		if (rider instanceof EntityPlayer) return rider;
    	}

        return null;
    }
	
	//dismount all rider and clear this mount entity
	public void clearRider()
	{
		List<Entity> riders = this.getPassengers();
		
		for (int i = 0; i < riders.size(); i++)
		{
			Entity rider = riders.get(i);
			if (rider != null) rider.dismountRidingEntity();
		}
		
		this.setDead();
	}
	
	//dismount rider2 only (player)
	public void clearRider2()
	{
		//reset mount emotion
		this.setStateEmotion(ID.S.Emotion, 0, false);
		
		//clear rider except host rider (ship)
		List<Entity> riders = this.getPassengers();
		
		for (Entity rider : riders)
		{
			if (rider != null) rider.dismountRidingEntity();
		}
		
		//若是server則發送sync packet
		if (!this.world.isRemote)
		{
			this.sendSyncPacket(1);
		}
	}
	
	//update attribute
	public void setupAttrs()
	{
		this.shipAttrs = AttrsAdv.copyAttrsAdv((AttrsAdv) this.host.getAttrs());
		this.shipAttrs.setAttrsBuffed(ID.Attrs.HP, this.host.getMaxHealth() * 0.5F);
		this.shipAttrs.setAttrsBuffed(ID.Attrs.DEF, this.host.getAttrs().getDefense() * 0.5F);
		
		getEntityAttribute(MAX_HP).setBaseValue(this.host.getMaxHealth() * 0.5D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(host.getAttrs().getMoveSpeed());
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(host.getAttrs().getAttrsBuffed(ID.Attrs.KB));
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
	
	@Override
	public void setEntitySit(boolean sit)
	{
		if (this.host != null) host.setEntitySit(sit);
	}
	
	/** sync packet
	 *  0: sync all rider
	 *  1: sync rotation
	 *  2: sync rotation and host rotation
	 */
	public void sendSyncPacket(int type)
	{
		if (!world.isRemote)
		{
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
			
			switch (type)
			{
			case 0:
				CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncShip_Riders), point);
			break;
			case 1:
				CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncEntity_Rot), point);
			break;
			case 2:
				CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, 0, S2CEntitySync.PID.SyncEntity_PosRot), point);
				CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this.host, 0, S2CEntitySync.PID.SyncEntity_PosRot), point);
			break;
			}
		}
	}
	
	@Override
	public Entity getGuardedEntity()
	{
		if (this.host != null) return this.host.getGuardedEntity();
		return null;
	}

	@Override
	public void setGuardedEntity(Entity entity)
	{
		if (this.host != null) this.host.setGuardedEntity(entity);
	}

	@Override
	public int getGuardedPos(int vec)
	{
		if (this.host != null) return this.host.getGuardedPos(vec);
		return -1;
	}

	@Override
	public void setGuardedPos(int x, int y, int z, int dim, int type)
	{
		if (this.host != null) this.host.setGuardedPos(x, y, z, dim, type);
	}
	
    @Override
    public float getModelRotate(int par1)
    {
    	return 0F;
    }
    
    //set model rotate angle, par1 = 0:X, 1:Y, 2:Z
    @Override
	public void setModelRotate(int par1, float par2) {}
    
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
		return this.host;
	}
	
	@Override
	public Entity getEntityRevengeTarget()
	{
		if (this.host != null) return this.host.getEntityRevengeTarget();
		return null;
	}

	@Override
	public int getEntityRevengeTime()
	{
		if (this.host != null) return this.host.getEntityRevengeTime();
		return 0;
	}

	@Override
	public void setEntityRevengeTarget(Entity target)
	{
		if (this.host != null) this.host.setEntityRevengeTarget(target);
	}
  	
  	@Override
	public void setEntityRevengeTime()
  	{
		this.revengeTime = this.ticksExisted;
	}
  	
  	@Override
    public double getMountedYOffset()
    {
  		return this.height;
    }
  	
	@Override
	public boolean canPassengerSteer()
    {
		return false;
    }
  	
  	//對第一個rider設定座位位置以及旋轉角度使其跟座騎一致, 對第二個以後rider只設定位置
  	@Override
  	public void updatePassenger(Entity passenger)
  	{
  		if (!this.isDead && passenger != null && this.isPassenger(passenger))
  		{
  			//set position for host seat
  			if (passenger instanceof BasicEntityShip)
  			{
//  				this.seatPos = new float[] {EventHandler.field2, EventHandler.field1, 0F};	//debug
  	        	float[] ridePos = CalcHelper.rotateXZByAxis(this.seatPos[0], this.seatPos[2], renderYawOffset * Values.N.DIV_PI_180, 1F);	
  	        	passenger.setPosition(this.posX + ridePos[1], this.posY + this.seatPos[1] + passenger.getYOffset(), this.posZ + ridePos[0]);
  			}
  			//set position for player seat
  			else if (passenger instanceof EntityPlayer)
  			{
//  				this.seatPos2 = new float[] {EventHandler.field2, EventHandler.field1, 0F}; //debug
  				float[] ridePos = CalcHelper.rotateXZByAxis(this.seatPos2[0], this.seatPos2[2], renderYawOffset * Values.N.DIV_PI_180, 1F);	
  	        	passenger.setPosition(this.posX + ridePos[1], this.posY + this.seatPos2[1] + passenger.getYOffset(), this.posZ + ridePos[0]);
  			}
  			//set position for other entity
  			else
  			{
  				passenger.setPosition(this.posX, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ);
  			}
  		}//end rider != null
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
	
	@Override
	public float getSwingTime(float partialTick)
	{
		return this.getSwingProgress(partialTick);
	}
	
	//get last waypoint, for waypoint loop checking
  	@Override
  	public BlockPos getLastWaypoint()
  	{
  		if (this.host != null) return this.host.getLastWaypoint();
  		return BlockPos.ORIGIN;
  	}
  	
  	@Override
  	public void setLastWaypoint(BlockPos pos)
  	{
  		if (this.host != null) this.host.setLastWaypoint(pos);
  	}
  	
	@Override
	public int getWpStayTime()
	{
		if (this.host != null) return host.getStateTimer(ID.T.WpStayTime);
		return 0;
	}

	@Override
	public int getWpStayTimeMax()
	{
		if (this.host != null) return host.getWpStayTimeMax();
		return 0;
	}

	@Override
	public void setWpStayTime(int time)
	{
		if (this.host != null) host.setStateTimer(ID.T.WpStayTime, time);
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
	public int getDamageType()
	{
		if (this.host != null) return this.host.getDamageType();
		return 0;
	}
	
	@Override
	public boolean isJumping()
	{
		return this.isJumping;
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
		return 0.3D;
	}

	@Override
	public void setShipFloatingDepth(double par1) {}
	
	@Override
	public float[] getSeatPos()
	{
		return this.seatPos;
	}
	
	@Override
	public void setSeatPos(float[] pos)
	{
		this.seatPos = pos;
	}
	
	@Override
	public float[] getSeatPos2()
	{
		return this.seatPos2;
	}
	
	@Override
	public void setSeatPos2(float[] pos)
	{
		this.seatPos2 = pos;
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
	public int getStateTimer(int id)
	{
		if (this.host != null) this.host.getStateTimer(id);
		return 0;
	}

	@Override
	public void setStateTimer(int id, int value)
	{
		if (this.host != null) this.host.setStateTimer(id, value);
	}
	
	@Override
	public HashMap<Integer, Integer> getBuffMap()
	{
		if (this.host != null) return this.host.getBuffMap();
		return new HashMap<Integer, Integer>();
	}

	@Override
	public void setBuffMap(HashMap<Integer, Integer> map) {}
	
	//return host's attrs
	@Override
	public Attrs getAttrs()
	{
		if (this.host != null) return this.host.getAttrs();
		return null;
	}
	
	@Override
	public void setAttrs(Attrs data) {}
	
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
		
		super.heal(healAmount);
    }
	
	@Override
	public void setUpdateFlag(int id, boolean value) {}

	@Override
	public boolean getUpdateFlag(int id)
	{
		return false;
	}
	
	
}