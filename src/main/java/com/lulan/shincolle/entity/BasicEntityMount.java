package com.lulan.shincolle.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.ai.EntityAIMountFollowOwner;
import com.lulan.shincolle.ai.EntityAIMountRangeAttack;
import com.lulan.shincolle.ai.EntityAIShipAttackOnCollide;
import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipWatchClosest;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BasicEntityMount extends EntityCreature implements IShipEmotion, IShipAttack, IShipMount, IShipFloating {
	
	protected BasicEntityShip host;  			//host
	public EntityMountSeat seat2;				//seat 2
	public EntityLivingBase riddenByEntity2;	//second rider
	protected World world;
    
    //attributes
	protected float atkRange;			//attack range
	protected float movSpeed;			//def value
    
    //model display
    /**EntityState: 0:HP State 1:Emotion 2:Emotion2*/
	protected byte StateEmotion;		//表情1
	protected byte StateEmotion2;		//表情2
	protected int StartEmotion;			//表情1 開始時間
	protected int StartEmotion2;		//表情2 開始時間
	protected boolean headTilt;
	
	//AI
	protected double ShipDepth;			//水深, 用於水中高度判定
	public int keyPressed;				//key(bit): 0:W 1:S 2:A 3:D 4:Jump
	
    public BasicEntityMount(World world) {	//client side
		super(world);
		isImmuneToFire = true;
		stepHeight = 3F;
		keyPressed = 0;
	}
    
    //平常音效
    @Override
	protected String getLivingSound() {
		if(ConfigHandler.useWakamoto && rand.nextInt(5) == 0) {
			return Reference.MOD_ID+":ship-waka_idle";
		}
		return null;
	}
  	
  	//受傷音效
    @Override
	protected String getHurtSound() {
		if(ConfigHandler.useWakamoto && rand.nextInt(5) == 0) {
			return Reference.MOD_ID+":ship-waka_hurt";
		}
		return null;
	}

	//死亡音效
    @Override
	protected String getDeathSound() {
		if(ConfigHandler.useWakamoto) {
			return Reference.MOD_ID+":ship-waka_death";
		}
		return null;
	}

	//音效大小
    @Override
	protected float getSoundVolume() {
		return ConfigHandler.shipVolume * 0.4F;
	}
	
    //音效音高
    @Override
	protected float getSoundPitch() {
    	return 1F;
    }
	
    //setup AI
	public void setAIList() {
		if(!this.worldObj.isRemote) {
			this.clearAITasks();
			this.clearAITargetTasks();

			this.getNavigator().setEnterDoors(true);
			this.getNavigator().setAvoidsWater(false);
			this.getNavigator().setCanSwim(true);

			//high priority
			this.tasks.addTask(1, new EntityAIMountFollowOwner(this));	   		   //0111
			
			//use range attack
			this.tasks.addTask(2, new EntityAIMountRangeAttack(this));			   //0011
			
			//use melee attack
			if(this.getStateFlag(ID.F.UseMelee)) {
				this.tasks.addTask(12, new EntityAIShipAttackOnCollide(this, 1D, true));   //0011
				this.tasks.addTask(13, new EntityAIMoveTowardsTarget(this, 1D, 48F));  //0001
			}
			
			//idle AI
			//moving
			this.tasks.addTask(21, new EntityAIOpenDoor(this, true));			   //0000
			this.tasks.addTask(22, new EntityAIShipFloating(this));				   //0101
			this.tasks.addTask(24, new EntityAIWander(this, 0.8D));				   //0001
			this.tasks.addTask(25, new EntityAILookIdle(this));					   //0011
		}
	}
	
	//called client+server both side!!
	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk) {
		//disalbe inWall damage
		if(attacker.getDamageType() == "inWall") {
			return false;
		}
		
		if(attacker.getDamageType() == "fall") {
			return false;
		}
     
        //無敵的entity傷害無效
  		if(this.isEntityInvulnerable()) {	
        	return false;
        }
		
		//server side
		if(!this.worldObj.isRemote) {
			if(host == null) {
				this.setDead();
				return false;
			}
			
			//set hurt face
	    	if(this.getStateEmotion(ID.S.Emotion) != ID.Emotion.O_O) {
	    		this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
	    	}
	    	
	    	//進行def計算
	        float reduceAtk = atk * (1F - host.getStateFinal(ID.DEF) / 100F);
	        if(atk < 0) { atk = 0; }
	        
	        if(attacker.getSourceOfDamage() != null) {
	  			Entity entity = attacker.getSourceOfDamage();
	  			
	  			//不會對自己造成傷害
	  			if(entity.equals(this)) {  
	  				return false;
	  			}
	  			
	  			//若攻擊方為player, 則修正傷害
	  			if(entity instanceof EntityPlayer) {
					//若禁止friendlyFire, 則傷害設為0
					if(!ConfigHandler.friendlyFire) {
						return false;
					}
	  			}
	  		}
	        
	        //取消host的坐下動作
	        if(host != null) {
	        	this.host.setSitting(false);
	        }
			
//LogHelper.info("DEBUG : def "+atk+" "+reduceAtk);	        
	        return super.attackEntityFrom(attacker, reduceAtk);
		}
		
		return false;
	}
	
	//BUG: NOT WORKING
	@Override
	public boolean canBePushed() {
        return false;
    }
	
	//replace isInWater, check water block with NO extend AABB
	private void checkDepth() {
		Block BlockCheck = checkBlockWithOffset(0);
		
		if(BlockCheck == Blocks.water || BlockCheck == Blocks.lava) {
			ShipDepth = 1;
			for(int i=1; (this.posY+i)<255D; i++) {
				BlockCheck = checkBlockWithOffset(i);
				if(BlockCheck == Blocks.water || BlockCheck == Blocks.lava) {
					ShipDepth++;
				}
			}		
			ShipDepth = ShipDepth - (this.posY - (int)this.posY);
		}
		else {
			ShipDepth = 0;
		}
	}
	
	//check block from entity posY + offset
	public Block checkBlockWithOffset(int par1) {
		int blockX = MathHelper.floor_double(this.posX);
	    int blockY = MathHelper.floor_double(this.boundingBox.minY);
	    int blockZ = MathHelper.floor_double(this.posZ);

	    return this.worldObj.getBlock(blockX, blockY + par1, blockZ);    
	}
	
	@Override
  	public boolean interact(EntityPlayer player) {	
		ItemStack itemstack = player.inventory.getCurrentItem();  //get item in hand
		
		//use cake to change state
		if(itemstack != null && host != null) {
			//use cake
			if(itemstack.getItem() == Items.cake) {
				switch(host.getStateEmotion(ID.S.State)) {
				case ID.State.NORMAL:
					host.setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
					break;
				case ID.State.EQUIP00:
					host.setStateEmotion(ID.S.State, ID.State.NORMAL, true);
					break;
				default:
					host.setStateEmotion(ID.S.State, ID.State.NORMAL, true);
					break;
				}
				return true;
			}
			
			//use lead
			if(itemstack.getItem() == Items.lead && this.allowLeashing()) {	
				this.setLeashedToEntity(player, true);
				return true;
	        }
		}
		
		//如果已經被綑綁, 再點一下可以解除綑綁
		if(this.getLeashed() && this.getLeashedToEntity() == player) {
            this.clearLeashed(true, !player.capabilities.isCreativeMode);
            return true;
        }

		//click without shift = host set sitting
		if(!this.worldObj.isRemote && !player.isSneaking()) {			
			//若座位二已經有人, 則右鍵改為坐下
			if(this.riddenByEntity2 != null) {
				//seat2已經沒人騎, 則rider 2設為null
				if(this.seat2 == null) {
					//summon seat entity
  	  	  			EntityMountSeat seat = new EntityMountSeat(worldObj, this);
  	  	  			seat.posX = this.posX;
  	  	  			seat.posY = this.posY;
  	  	  			seat.posZ = this.posZ;
  	  	  			this.seat2 = seat;
  	  	  			this.worldObj.spawnEntityInWorld(seat);
	  	  	  			
					//set riding
	  	  			player.mountEntity(seat2);
	  	  			this.riddenByEntity2 = player;
	  	  			this.StateEmotion = 1;
	  	  			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
	  	  			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, 5), point);
					return true;
				}
				
				//只有主人可以設為坐下
				if(EntityHelper.checkSameOwner(player, this.getOwner())) {
					this.host.setSitting(!this.host.isSitting());
		            this.isJumping = false;
		            this.setPathToEntity((PathEntity)null);
		            this.setTarget((Entity)null);
		            this.host.setTarget((Entity)null);
		            this.setAttackTarget((EntityLivingBase)null);
		            this.host.setAttackTarget((EntityLivingBase)null);
		            return true;
				}	
			}
			//座位二沒人, 則騎上座騎 (所有玩家皆適用)
			else {
				if(this.seat2 == null) {
					//summon seat entity
  	  	  			EntityMountSeat seat = new EntityMountSeat(worldObj, this);
  	  	  			seat.posX = this.posX;
	  	  			seat.posY = this.posY;
	  	  			seat.posZ = this.posZ;
  	  	  			this.seat2 = seat;
  	  	  			this.worldObj.spawnEntityInWorld(seat);
				}
				
  	  			//set riding entity
  	  			player.mountEntity(seat2);
  	  			this.riddenByEntity2 = player;
  	  			this.StateEmotion = 1;
  				TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  	  			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, 5), point);
				return true;
			}
        }
		
		//shift+right click時打開host GUI
		if(player.isSneaking() && EntityHelper.checkSameOwner(player, this.getOwner())) {  
			int eid = this.host.getEntityId();
			//player.openGui vs FMLNetworkHandler ?
    		FMLNetworkHandler.openGui(player, ShinColle.instance, ID.G.SHIPINVENTORY, this.worldObj, this.host.getEntityId(), 0, 0);
    		return true;
		}
		
		return false;
  	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//apply movement by key pressed
		if(this.seat2 != null && this.seat2.riddenByEntity != null && this.keyPressed > 0) {
			EntityLivingBase rider2 = (EntityLivingBase)this.seat2.riddenByEntity;
			float yaw = rider2.rotationYawHead % 360F / 57.2958F;
			float pitch = rider2.rotationPitch % 360F / 57.2958F;
			
			this.applyMovement(pitch, yaw);
			this.rotationYaw = rider2.rotationYaw;
		}
			
		if(this.isInWater() || this.handleLavaMovement()) {
			this.checkDepth();	//check depth every tick
		}
		else {
			this.ShipDepth = 0D;
		}
//LogHelper.info("DEBUG : mount state "+this.worldObj.isRemote+" "+this.keyPressed);
		//client side
		if(this.worldObj.isRemote) {
			if(this.isInWater() || this.handleLavaMovement()) {
				//水中移動時, 產生水花特效
				//(注意此entity因為設為非高速更新, client端不會更新motionX等數值, 需自行計算)
				double motX = this.posX - this.prevPosX;
				double motZ = this.posZ - this.prevPosZ;
				double parH = this.posY - (int)this.posY;
				
				if(motX != 0 || motZ != 0) {
					ParticleHelper.spawnAttackParticleAt(this.posX + motX*1.5D, this.posY, this.posZ + motZ*1.5D, 
							-motX*0.5D, 0D, -motZ*0.5D, (byte)15);
				}
			}
			
			if(this.riddenByEntity != null) {
//				LogHelper.info("DEBUG : rider2 move "+((EntityLivingBase)riddenByEntity).prevRotationYawHead+" "+this.prevRotationYawHead); 
				((EntityLivingBase)this.riddenByEntity).rotationYawHead = this.rotationYawHead;
				((EntityLivingBase)this.riddenByEntity).prevRotationYawHead = this.prevRotationYawHead;
				((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
			}
		}
		//server side
		else {
			//clear target if target is self/host
			if(this.getTarget() == this || this.getTarget() == host) {
				this.setAttackTarget(null);
			}

			//owner消失(通常是server restart)
			if(this.getOwner() == null) {
				this.setDead();
			}
			else {		
				//owner還在, 但是已經騎乘別人, 則消除此entity
				if(this.getOwner().ridingEntity != this) {
					LogHelper.info("DEBUG : ride change "+this.riddenByEntity); 
					this.setDead();
				}
				
				//LogHelper.info("DEBUG : get rider "+riddenByEntity+" "+this.riddenByEntity2);		
				if(this.ticksExisted % 100 == 0) {
					//get new status every 5 second
					this.atkRange = host.getStateFinal(ID.HIT);
					
					//speed reduce to zero if host sitting
					if(this.host.isSitting()) {
						this.movSpeed = 0F;
					}
					else {
						this.movSpeed = host.getStateFinal(ID.MOV);
					}

			        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(host.getStateFinal(ID.HP) * 0.5D);
					getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movSpeed);
					getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(host.getStateFinal(ID.HIT) + 16); //此為找目標, 路徑的範圍
					getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue((double)host.getShipLevel() / 150D);
				
					//防止溺死
					if(this.isInWater()) {
						this.setAir(300);
					}
				}
				
				//get target every 10 ticks
				if(this.ticksExisted % 10 == 0) {
					this.setAttackTarget(this.host.getAttackTarget());
					
					//clear target if target dead
					if(this.getAttackTarget() != null  && this.getAttackTarget().isDead) {
						this.setAttackTarget(null);
					}
				}
			}
		}
	}
	
	//set movement by key pressed, pitch/yaw is RAD not DEGREE
	private void applyMovement(float pitch, float yaw) {
		//calc move direction by yaw
		float[] movez = ParticleHelper.rotateParticleByAxis(movSpeed, 0F, yaw, 1F);	//前後
		float[] movex = ParticleHelper.rotateParticleByAxis(0F, movSpeed, yaw, 1F);	//左右
		
		if(this.onGround || this.isInWater() || this.handleLavaMovement()) {
			//horizontal move, 至少要4 tick才能加到最高速
			//W (bit 1)
			if((keyPressed & 1) > 0) {
				motionX += movez[1] / 4F;
				if(MathHelper.abs((float) motionX) > MathHelper.abs(movez[1])) motionX = movez[1];
				motionZ += movez[0] / 4F;
				if(MathHelper.abs((float) motionZ) > MathHelper.abs(movez[0])) motionZ = movez[0];
			}
			
			//S (bit 2)
			if((keyPressed & 2) > 0) {
				motionX -= movez[1] / 4F;
				if(MathHelper.abs((float) motionX) > MathHelper.abs(movez[1])) motionX = -movez[1];
				motionZ -= movez[0] / 4F;
				if(MathHelper.abs((float) motionZ) > MathHelper.abs(movez[0])) motionZ = -movez[0];
			}
			
			//A (bit 3)
			if((keyPressed & 4) > 0) {
				motionX += movex[1] / 4F;
				if(MathHelper.abs((float) motionX) > MathHelper.abs(movex[1])) motionX = movex[1];
				motionZ += movex[0] / 4F;
				if(MathHelper.abs((float) motionZ) > MathHelper.abs(movex[0])) motionZ = movex[0];
			}
			
			//D (bit 4)
			if((keyPressed & 8) > 0) {
				motionX -= movex[1] / 4F;
				if(MathHelper.abs((float) motionX) > MathHelper.abs(movex[1])) motionX = -movex[1];
				motionZ -= movex[0] / 4F;
				if(MathHelper.abs((float) motionZ) > MathHelper.abs(movex[0])) motionZ = -movex[0];
			}
			
			//vertical move
			if(pitch > 0.5F) {	//move down
				motionY += -0.1F;
				if(motionY < -movSpeed / 2F) motionY = -movSpeed / 2F;			
			}
			
			if(pitch < -1F) {	//move up
				motionY += 0.1F;
				if(motionY > movSpeed / 2F) motionY = movSpeed / 2F;
			}
			
			//若水平撞到東西, 則嘗試跳跳
			if(this.isCollidedHorizontally) {
				this.motionY += 0.4D;
			}
			
			//jump (bit 5)
			if((keyPressed & 16) > 0) {
				this.motionY += this.movSpeed * 2F;
				if(motionY > 1F) motionY = 1F;
				//reset jump flag
				keyPressed -= 16;
			}	
		}
		else {
			//不在地面時, 各方向的空中加速度不同, 往前不變, 往後減少, 左右大幅減少
			//W (bit 1)
			if((keyPressed & 1) > 0) {
				motionX += movez[1] / 4F;
				if(MathHelper.abs((float) motionX) > MathHelper.abs(movez[1])) motionX = movez[1];
				motionZ += movez[0] / 4F;
				if(MathHelper.abs((float) motionZ) > MathHelper.abs(movez[0])) motionZ = movez[0];
			}
			
			//S (bit 2)
			if((keyPressed & 2) > 0) {
				motionX -= movez[1] / 16F;
				if(MathHelper.abs((float) motionX) > MathHelper.abs(movez[1])) motionX = -movez[1];
				motionZ -= movez[0] / 16F;
				if(MathHelper.abs((float) motionZ) > MathHelper.abs(movez[0])) motionZ = -movez[0];
			}
			
			//A (bit 3)
			if((keyPressed & 4) > 0) {
				motionX += movex[1] / 32F;
				if(MathHelper.abs((float) motionX) > MathHelper.abs(movex[1])) motionX = movex[1];
				motionZ += movex[0] / 32F;
				if(MathHelper.abs((float) motionZ) > MathHelper.abs(movex[0])) motionZ = movex[0];
			}
			
			//D (bit 4)
			if((keyPressed & 8) > 0) {
				motionX -= movex[1] / 32F;
				if(MathHelper.abs((float) motionX) > MathHelper.abs(movex[1])) motionX = -movex[1];
				motionZ -= movex[0] / 32F;
				if(MathHelper.abs((float) motionZ) > MathHelper.abs(movex[0])) motionZ = -movex[0];
			}
		}
	}

	@Override
	public byte getStateEmotion(int id) {
		return id == ID.S.Emotion ? StateEmotion : StateEmotion2;
	}
	
	@Override
	public void setStateEmotion(int id, int value, boolean sync) {	
		switch(id) {
		case ID.S.Emotion:
			StateEmotion = (byte) value;
			break;
		case ID.S.Emotion2:
			StateEmotion2 = (byte) value;
			break;
		default:
			break;
		}
		
		if(sync && !worldObj.isRemote) {
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, 4), point);
		}
	}

	@Override
	public boolean getStateFlag(int flag) {
		return this.host.getStateFlag(flag);
	}

	@Override
	public void setStateFlag(int id, boolean flag) {
		this.host.setStateFlag(id, flag);
	}

	@Override
	public int getStartEmotion() {
		return this.StartEmotion;
	}

	@Override
	public int getStartEmotion2() {
		return this.StartEmotion2;
	}

	@Override
	public void setStartEmotion(int par1) {
		this.StartEmotion = par1;
	}

	@Override
	public void setStartEmotion2(int par1) {
		this.StartEmotion2 = par1;
	}

	@Override
	public int getTickExisted() {
		return this.ticksExisted;
	}

	@Override
	public int getAttackTime() {
		return this.attackTime;
	}
	
    @Override
	public boolean isAIEnabled() {
		return true;
	}
  	
    //clear AI
  	protected void clearAITasks() {
  	   tasks.taskEntries.clear();
  	}
  	
  	//clear target AI
  	protected void clearAITargetTasks() {
  	   targetTasks.taskEntries.clear();
  	}
  	
  	@Override
    public EntityLivingBase getOwner() {
        return this.host;
    }
    
  	@Override
	public EntityLivingBase getTarget() {
		return this.getAttackTarget();
	}
  	
  	//change melee damage to 100%
  	@Override
  	public boolean attackEntityAsMob(Entity target) {	
  		//get attack value
  		float atk = host.getStateFinal(ID.ATK);
  		//set knockback value (testing)
  		float kbValue = 0.15F;
  				
  	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
  	    //並且回傳是否成功傷害到目標
  	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk);

  	    //play entity attack sound
  	    if(this.getRNG().nextInt(10) > 8) {
  	    	this.playSound(Reference.MOD_ID+":ship-waka_attack", ConfigHandler.shipVolume * 0.5F, 1F);
  	    }
  	    
  	    //if attack success
  	    if(isTargetHurt) {
  	    	//calc kb effect
  	        if(kbValue > 0) {
  	            target.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * kbValue), 
  	                   0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue));
  	            motionX *= 0.6D;
  	            motionZ *= 0.6D;
  	        }

  	        //send packet to client for display partical effect   
  	        if (!worldObj.isRemote) {
  	        	TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  	    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
  			}
  	    }

  	    return isTargetHurt;
  	}
    
  	//light attack
    @Override
	public boolean attackEntityWithAmmo(Entity target) {
		float atkLight = this.host.getStateFinal(ID.ATK);
		float kbValue = 0.03F;

		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-firesmall", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //play entity attack sound
        if(this.rand.nextInt(10) > 8) {
        	this.playSound(Reference.MOD_ID+":ship-waka_attack", ConfigHandler.shipVolume * 0.5F, 1F);
        }
        
        //此方法比getLook還正確 (client sync問題)
        float distX = (float) (target.posX - this.posX);
        float distY = (float) (target.posY - this.posY);
        float distZ = (float) (target.posZ - this.posZ);
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        distX = distX / distSqrt;
        distY = distY / distSqrt;
        distZ = distZ / distSqrt;
        
        //experience++
  		host.addShipExp(2);
  		
  		//grudge--
  		host.decrGrudgeNum(1);
  		
  		//light ammo -1
        if(!host.decrAmmoNum(0)) {			//not enough ammo
        	atkLight = atkLight * 0.125F;	//reduce damage to 12.5%
        }
		
		//發射者煙霧特效
        TargetPoint point0 = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 19, this.posX, this.posY+1.5D, this.posZ, distX, 1.2F, distZ, true), point0);

		//calc miss chance, if not miss, calc cri/multi hit
		TargetPoint point = new TargetPoint(this.dimension, this.host.posX, this.host.posY, this.host.posZ, 64D);
        float missChance = 0.2F + 0.15F * (distSqrt / host.getStateFinal(ID.HIT)) - 0.001F * host.getShipLevel();
        missChance -= this.host.getEffectEquip(ID.EF_MISS);		//equip miss reduce
        if(missChance > 0.35F) missChance = 0.35F;	//max miss chance
  		
        //calc miss chance
        if(this.rand.nextFloat() < missChance) {
        	atkLight = 0;	//still attack, but no damage
        	//spawn miss particle
        	
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host, 10, false), point);
        }
        else {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if(this.rand.nextFloat() < this.host.getEffectEquip(ID.EF_CRI)) {
        		atkLight *= 1.5F;
        		//spawn critical particle
            	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host, 11, false), point);
        	}
        	else {
        		//calc double hit
            	if(this.rand.nextFloat() < this.host.getEffectEquip(ID.EF_DHIT)) {
            		atkLight *= 2F;
            		//spawn double hit particle
            		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host, 12, false), point);
            	}
            	else {
            		//calc double hit
                	if(this.rand.nextFloat() < this.host.getEffectEquip(ID.EF_THIT)) {
                		atkLight *= 3F;
                		//spawn triple hit particle
                		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host, 13, false), point);
                	}
            	}
        	}
        }
        
        //vs player = 25% dmg
  		if(target instanceof EntityPlayer) {
  			atkLight *= 0.25F;
  			
  			//check friendly fire
    		if(!ConfigHandler.friendlyFire) {
    			atkLight = 0F;
    		}
    		else if(atkLight > 59F) {
    			atkLight = 59F;	//same with TNT
    		}
  		}

	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atkLight);

	    //if attack success
	    if(isTargetHurt) {
	    	//calc kb effect
	        if(kbValue > 0) {
	            target.addVelocity((double)(-MathHelper.sin(rotationYaw * (float)Math.PI / 180.0F) * kbValue), 
	                   0.1D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue));
	        }
	        
	        //display hit particle on target
	        TargetPoint point1 = new TargetPoint(this.dimension, target.posX, target.posY, target.posZ, 64D);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point1);
        }

	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target) {
		//get attack value
		float atkHeavy = this.host.getStateFinal(ID.ATK_H);
		//set knockback value (testing)
		float kbValue = 0.08F;

		//play cannon fire sound at attacker
        this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //play entity attack sound
        if(this.rand.nextInt(10) > 8) {
        	this.playSound(Reference.MOD_ID+":ship-waka_attack", ConfigHandler.shipVolume * 0.5F, 1F);
        }
        
        //飛彈是否採用直射
  		boolean isDirect = false;
  		//計算目標距離
  		float tarX = (float)target.posX;	//for miss chance calc
  		float tarY = (float)target.posY;
  		float tarZ = (float)target.posZ;
  		float distX = tarX - (float)this.posX;
  		float distY = tarY - (float)this.posY;
  		float distZ = tarZ - (float)this.posZ;
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        float launchPos = (float)posY + height * 0.7F;
          
        //超過一定距離/水中 , 則採用拋物線,  在水中時發射高度較低
        if((distX*distX+distY*distY+distZ*distZ) < 36F) {
        	isDirect = true;
        }
        
        if(this.isInWater()) {
          	isDirect = true;
          	launchPos = (float)posY;
        }
        
        //experience++
      	host.addShipExp(16);
      		
      	//grudge--
      	host.decrGrudgeNum(1);
      	
      	//heavy ammo -1
        if(!host.decrAmmoNum(1)) {	//not enough ammo
        	atkHeavy = atkHeavy * 0.125F;	//reduce damage to 12.5%
        }
        
        //calc miss chance, miss: add random offset(0~6) to missile target 
        float missChance = 0.2F + 0.15F * (distSqrt / host.getEffectEquip(ID.EF_DHIT)) - 0.001F * host.getShipLevel();
        missChance -= this.host.getEffectEquip(ID.EF_MISS);	//equip miss reduce
        if(missChance > 0.35F) missChance = 0.35F;	//max miss chance = 30%
		
        //calc miss chance
        if(this.rand.nextFloat() < missChance) {
        	atkHeavy = 0;	//still attack, but no damage
        	//spawn miss particle
        	TargetPoint point = new TargetPoint(this.dimension, this.host.posX, this.host.posY, this.host.posZ, 64D);
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host, 10, false), point);
        }

        //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.worldObj, this, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atkHeavy, kbValue, isDirect, -1F);
        this.worldObj.spawnEntityInWorld(missile);
  		
        return true;
	}
    
    //水中跟岩漿中不會下沉
    @Override
    public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_) {
        double d0;

        if(this.isInWater() || this.handleLavaMovement()) {
            this.moveFlying(p_70612_1_, p_70612_2_, 0.04F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8D;
            this.motionY *= 0.8D;
            this.motionZ *= 0.8D;
        }
        else {
            float f2 = 0.91F;

            if (this.onGround)
            {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            float f4;

            if (this.onGround)
            {
                f4 = this.getAIMoveSpeed() * f3;
            }
            else
            {
                f4 = this.jumpMovementFactor;
            }

            this.moveFlying(p_70612_1_, p_70612_2_, f4);
            f2 = 0.91F;

            if (this.onGround)
            {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            if (this.isOnLadder())
            {
                float f5 = 0.15F;

                if (this.motionX < (double)(-f5))
                {
                    this.motionX = (double)(-f5);
                }

                if (this.motionX > (double)f5)
                {
                    this.motionX = (double)f5;
                }

                if (this.motionZ < (double)(-f5))
                {
                    this.motionZ = (double)(-f5);
                }

                if (this.motionZ > (double)f5)
                {
                    this.motionZ = (double)f5;
                }

                this.fallDistance = 0.0F;

                if (this.motionY < -0.15D)
                {
                    this.motionY = -0.15D;
                }

                boolean flag = this.isSneaking();

                if (flag && this.motionY < 0.0D)
                {
                    this.motionY = 0.0D;
                }
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            if (this.isCollidedHorizontally && this.isOnLadder())
            {
                this.motionY = 0.2D;
            }

            if (this.worldObj.isRemote && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || !this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded))
            {
                if (this.posY > 0.0D)
                {
                    this.motionY = -0.1D;
                }
                else
                {
                    this.motionY = 0.0D;
                }
            }
            else
            {
                this.motionY -= 0.08D;
            }

            this.motionY *= 0.9800000190734863D;
            this.motionX *= (double)f2;
            this.motionZ *= (double)f2;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f6 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        this.limbSwingAmount += (f6 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

	@Override
	public float getAttackSpeed() {
		return this.host.getStateFinal(ID.SPD);
	}

	@Override
	public float getAttackRange() {
		return this.host.getStateFinal(ID.HIT);
	}
	
	@Override
	public float getMoveSpeed() {
		return this.host.getStateFinal(ID.MOV);
	}
	
	@Override
	public int getAmmoLight() {
		return this.host.getStateMinor(ID.N.NumAmmoLight);
	}

	@Override
	public int getAmmoHeavy() {
		return this.host.getStateMinor(ID.N.NumAmmoHeavy);
	}

	@Override
	public boolean hasAmmoLight() {
		return this.getAmmoLight() > 0;
	}

	@Override
	public boolean hasAmmoHeavy() {
		return this.getAmmoHeavy() > 0;
	}

	@Override
	public void setAmmoLight(int num) {}	//not used

	@Override
	public void setAmmoHeavy(int num) {}	//not used

	@Override
	public float getAttackDamage() {		//not used
		return 0F;
	}

	@Override
	public boolean getIsRiding() {
		return false;
	}

	@Override
	public boolean getIsSprinting() {
		return false;
	}

	@Override
	public boolean getIsSitting() {
		return false;
	}

	@Override
	public boolean getIsSneaking() {
		return false;
	}

	@Override
	public double getShipDepth() {
		return ShipDepth;
	}

	@Override
	public EntityLivingBase getRiddenByEntity() {
		return (EntityLivingBase) this.riddenByEntity;
	}
	
	@Override
	public boolean shouldDismountInWater(Entity rider) {
        return false;
    }
	

}

