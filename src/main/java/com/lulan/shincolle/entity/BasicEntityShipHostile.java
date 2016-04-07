package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipRangeTarget;
import com.lulan.shincolle.ai.EntityAIShipRevengeTarget;
import com.lulan.shincolle.ai.EntityAIShipWander;
import com.lulan.shincolle.ai.EntityAIShipWatchClosest;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public abstract class BasicEntityShipHostile extends EntityMob implements IShipCannonAttack, IShipFloating {

	//attributes
	protected float atk;				//damage
	protected float atkSpeed;			//attack speed
	protected float atkRange;			//attack range
	protected float defValue;			//def value
	protected float movSpeed;			//def value
    protected float kbValue;			//knockback value
    protected double ShipDepth;			//水深, 用於水中高度判定
    
    //model display
    /**EntityState: 0:HP State 1:Emotion 2:Emotion2*/
	protected byte[] StateEmotion;		//表情1
	protected int StartEmotion, StartEmotion2, StartEmotion3;		//表情2 開始時間
	protected boolean headTilt;
	protected float[] rotateAngle;		//模型旋轉角度, 用於手持物品render
	protected int StartSoundHurt;		//hurt sound ticks
	
	//misc
	protected ItemStack dropItem;
	
	//AI
	public boolean canDrop;				//drop item flag
	protected ShipPathNavigate shipNavigator;	//水空移動用navigator
	protected ShipMoveHelper shipMoveHelper;
	protected Entity atkTarget;
	protected Entity rvgTarget;					//revenge target
	protected int revengeTime;					//revenge target time
		
	
	public BasicEntityShipHostile(World world) {
		super(world);
		isImmuneToFire = true;	//set ship immune to lava
		ignoreFrustumCheck = true;	//即使不在視線內一樣render
		maxHurtResistantTime = 2;
		stepHeight = 4F;
		canDrop = true;
		shipNavigator = new ShipPathNavigate(this, worldObj);
		shipMoveHelper = new ShipMoveHelper(this, 15F);
		rotateAngle = new float[] {0F, 0F, 0F};
		
		//model display
		StartSoundHurt = 0;
		StateEmotion = new byte[] {ID.State.EQUIP00, 0, 0, 0, 0, 0};
	}
	
	@Override
	public boolean isAIEnabled() {
		return true;
	}
	
	@Override
	public boolean isBurning() {	//display fire effect
		return this.getStateEmotion(ID.S.HPState) == ID.HPState.HEAVY;
	}
	
	//setup AI
	protected void setAIList() {
		this.clearAITasks();
		this.clearAITargetTasks();
		
		this.getNavigator().setEnterDoors(true);
		this.getNavigator().setAvoidsWater(false);
		this.getNavigator().setCanSwim(true);
		
		//idle AI
		//moving
		this.tasks.addTask(21, new EntityAIOpenDoor(this, true));			   //0000
		this.tasks.addTask(22, new EntityAIShipFloating(this));				   //0111
		this.tasks.addTask(23, new EntityAIShipWatchClosest(this, EntityPlayer.class, 6F, 0.1F)); //0010
		this.tasks.addTask(24, new EntityAIShipWander(this, 0.8D));			   //0111
		this.tasks.addTask(25, new EntityAILookIdle(this));					   //0011

	}
	
	//setup target AI: par1: 0:passive 1:active
	public void setAITargetList() {
		this.targetTasks.addTask(1, new EntityAIShipRevengeTarget(this));
		this.targetTasks.addTask(3, new EntityAIShipRangeTarget(this, Entity.class));
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk) {
		//disable 
		if(attacker.getDamageType() == "inWall") {
			return false;
		}
		
		if(attacker.getDamageType() == "outOfWorld") {
			this.setDead();
			return true;
		}
				
		//set hurt face
    	if(this.getStateEmotion(ID.S.Emotion) != ID.Emotion.O_O) {
    		this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
    	}
        
        //無敵的entity傷害無效
  		if(this.isEntityInvulnerable()) {	
        	return false;
        }
  		
  		if(attacker.getSourceOfDamage() != null) {
  			Entity entity = attacker.getSourceOfDamage();
  			
  			//不會對自己造成傷害
  			if(entity.equals(this)) {  
  				return false;
  			}
  			
  			//若掉到世界外, 則直接使該entity消失
  	        if(attacker.getDamageType().equals("outOfWorld")) {
  	        	this.setDead();
  	        	return false;
  	        }
  	        
  	        //設置revenge target
			this.setEntityRevengeTarget(entity);
			this.setEntityRevengeTime();

  	        //def calc
  			float reduceAtk = atk;
  			
  			reduceAtk = atk * (1F - this.getDefValue() * 0.01F);
  			
  			//ship vs ship, damage type傷害調整
  			if(entity instanceof IShipAttackBase) {
  				//get attack time for damage modifier setting (day, night or ...etc)
  				int modSet = this.worldObj.provider.isDaytime() ? 0 : 1;
  				reduceAtk = CalcHelper.calcDamageByType(reduceAtk, ((IShipAttackBase) entity).getDamageType(), this.getDamageType(), modSet);
  			}
  			
  	        if(reduceAtk < 1) reduceAtk = 1;
  	        
  	        return super.attackEntityFrom(attacker, reduceAtk);
  		}
    	
    	return false;
	}
	
	//clear AI
	protected void clearAITasks() {
		tasks.taskEntries.clear();
	}
	
	//clear target AI
	protected void clearAITargetTasks() {
		this.setAttackTarget(null);
		this.setEntityTarget(null);
		targetTasks.taskEntries.clear();
	}
	
	//掉落egg設定
	public ItemStack getDropEgg() {
		return this.dropItem;
	}
	
	//平常音效
	@Override
	protected String getLivingSound() {
		return Reference.MOD_ID+":ship-say";
    }
	
	//受傷音效
	@Override
    protected String getHurtSound() {
		if(this.StartSoundHurt <= 0) {
    		this.StartSoundHurt = 20 + this.getRNG().nextInt(40);
    		return Reference.MOD_ID+":ship-hurt";
    	}
    	return null;
    }

    //死亡音效
    @Override
    protected String getDeathSound() {
    	return Reference.MOD_ID+":ship-death";
    }

    //音效大小
    @Override
    protected float getSoundVolume() {
        return ConfigHandler.shipVolume;
    }

	@Override
	public byte getStateEmotion(int id) {
		return StateEmotion[id];
	}

	@Override
	public void setStateEmotion(int id, int value, boolean sync) {
		StateEmotion[id] = (byte) value;
		
		if(sync && !worldObj.isRemote) {
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, 4), point);
		}
	}

	@Override
	public boolean getStateFlag(int flag) {		//hostile mob: for attack and headTile check
		switch(flag) {
		default:
			return true;
		case ID.F.HeadTilt:
			return this.headTilt;
		case ID.F.OnSightChase:
			return false;
		}
	}

	@Override
	public void setStateFlag(int id, boolean flag) {
		if(id == ID.F.HeadTilt) this.headTilt = flag;
	}

	@Override
	public int getFaceTick() {
		return this.StartEmotion;
	}

	@Override
	public int getHeadTiltTick() {
		return this.StartEmotion2;
	}

	@Override
	public void setFaceTick(int par1) {
		this.StartEmotion = par1;
	}

	@Override
	public void setHeadTiltTick(int par1) {
		this.StartEmotion2 = par1;
	}

	@Override
	public int getTickExisted() {
		return this.ticksExisted;
	}
	
	@Override
	public float getAttackDamage() {
		return this.atk;
	}

	@Override
	public int getAttackTime() {
		return this.attackTime;
	}

	@Override
	public boolean attackEntityWithAmmo(Entity target) {
		//get attack value
		float atk = CalcHelper.calcDamageBySpecialEffect(this, target, this.atk, 0);
		
		//update entity look at vector (for particle spawn)
        //此方法比getLook還正確 (client sync問題)
        float distX = (float) (target.posX - this.posX);
        float distY = (float) (target.posY - this.posY);
        float distZ = (float) (target.posZ - this.posZ);
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        distX = distX / distSqrt;
        distY = distY / distSqrt;
        distZ = distZ / distSqrt;
      
        //發射者煙霧特效
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY+3.5D, this.posZ, distX, 2.8D, distZ, true), point);

		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-firesmall", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        //play entity attack sound
        if(this.rand.nextInt(10) > 7) {
        	this.playSound(Reference.MOD_ID+":ship-hitsmall", ConfigHandler.shipVolume, 1F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }

        //calc miss chance, if not miss, calc cri/multi hit   
        if(this.rand.nextFloat() < 0.2F) {
        	atk = 0;	//still attack, but no damage
        	//spawn miss particle
    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, false), point);
        }
        else {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if(this.rand.nextFloat() < 0.15F) {
        		atk *= 1.5F;
        		//spawn critical particle
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 11, false), point);
        	}
        	else {
        		//calc double hit
            	if(this.rand.nextFloat() < 0.15F) {
            		atk *= 2F;
            		//spawn double hit particle
            		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 12, false), point);
            	}
            	else {
            		//calc double hit
                	if(this.rand.nextFloat() < 0.15F) {
                		atk *= 3F;
                		//spawn triple hit particle
                		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 13, false), point);
                	}
            	}
        	}
        }
        
        //vs player = 25% dmg
  		if(target instanceof EntityPlayer) {
  			atk *= 0.25F;
  			
    		if(atk > 159F) {
    			atk = 159F;	//same with TNT
    		}
  		}
  		
	    //將atk跟attacker傳給目標的attackEntityFrom方法, 在目標class中計算傷害
	    //並且回傳是否成功傷害到目標
	    boolean isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this).setProjectile(), atk);

	    //if attack success
	    if(isTargetHurt) { 
        	//display hit particle on target
	        TargetPoint point1 = new TargetPoint(this.dimension, target.posX, target.posY, target.posZ, 64D);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point1);
        }

	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target) {
		return false;
	}
	
	@Override
	public float getAttackSpeed() {
		return this.atkSpeed;
	}

	@Override
	public float getAttackRange() {
		return this.atkRange;
	}

	@Override
	public float getMoveSpeed() {
		return this.movSpeed;
	}
	
	@Override
	public Entity getEntityTarget() {
		return this.atkTarget;
	}
  	
  	@Override
	public void setEntityTarget(Entity target) {
		this.atkTarget = target;
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
	public boolean hasAmmoLight() {
		return true;
	}

	@Override
	public boolean hasAmmoHeavy() {
		return true;
	}

	@Override
	public int getAmmoLight() {
		return 100;
	}

	@Override
	public int getAmmoHeavy() {
		return 100;
	}

	@Override
	public void setAmmoLight(int num) {}

	@Override
	public void setAmmoHeavy(int num) {}

	@Override
	public double getShipDepth() {
		return ShipDepth;
	}
	
	@Override
    public void moveEntityWithHeading(float movX, float movZ) {
        double d0;

        if(this.isInWater() || this.handleLavaMovement()) { //判定為液體中時, 不會自動下沉
            d0 = this.posY;
            this.moveFlying(movX, movZ, this.movSpeed*0.4F); //水中的速度計算(含漂移效果)
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            //水中阻力
            this.motionX *= 0.8D;
            this.motionY *= 0.8D;
            this.motionZ *= 0.8D;
            //水中撞到東西會上升
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + d0, this.motionZ)) {
                this.motionY = 0.3D;
            }
        }
        else {									//其他移動狀態
            float f2 = 0.91F;
            
            if(this.onGround) {					//在地面移動
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            float f4;
            
            if(this.onGround) {
                f4 = this.getAIMoveSpeed() * f3;
            }
            else {								//跳躍中
                f4 = this.jumpMovementFactor;
            }
            this.moveFlying(movX, movZ, f4);
            f2 = 0.91F;
            
            if(this.onGround) {
                f2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.91F;
            }

            if(this.isOnLadder()) {				//爬樓梯中
                float f5 = 0.15F;
                //限制爬樓梯時的橫向移動速度
                if(this.motionX < (-f5)) {
                    this.motionX = (-f5);
                }
                if(this.motionX > f5) {
                    this.motionX = f5;
                }
                if(this.motionZ < (-f5)) {
                    this.motionZ = (-f5);
                }
                if(this.motionZ > f5) {
                    this.motionZ = f5;
                }

                this.fallDistance = 0.0F;
                //限制爬樓梯的落下速度
                if (this.motionY < -0.15D) {
                    this.motionY = -0.15D;
                }

                boolean flag = this.isSneaking();
                //若是爬樓梯時為sneaking, 則不會落下(卡在樓梯上)
                if(flag && this.motionY < 0D) {
                    this.motionY = 0D;
                }
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            //往樓梯推擠, 則會往上爬
            if(this.isCollidedHorizontally && this.isOnLadder()) {
                this.motionY = 0.4D;
            }
            //自然掉落
            if(this.worldObj.isRemote && (!this.worldObj.blockExists((int)this.posX, 0, (int)this.posZ) || !this.worldObj.getChunkFromBlockCoords((int)this.posX, (int)this.posZ).isChunkLoaded)) {
                if (this.posY > 0.0D) {
                    this.motionY = -0.1D;	//空氣中的gravity為0.1D
                }
                else {
                    this.motionY = 0.0D;
                }
            }
            else {
                this.motionY -= 0.08D;
            }
            //空氣中的三方向阻力
            this.motionY *= 0.98D;			
            this.motionX *= f2;
            this.motionZ *= f2;
        }
        //計算四肢擺動值
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
	public void onUpdate() {
		super.onUpdate();
		
		//both side
		//check depth
		EntityHelper.checkDepth(this);
		
		//time --
		if(this.StartSoundHurt > 0) this.StartSoundHurt--;
		
		//client side
		if(this.worldObj.isRemote && this.isInWater()) {
			//有移動時, 產生水花特效
			//(注意此entity因為設為非高速更新, client端不會更新motionX等數值, 需自行計算)
			double motX = this.posX - this.prevPosX;
			double motZ = this.posZ - this.prevPosZ;
			double parH = this.posY - (int)this.posY;
			
			if(motX != 0 || motZ != 0) {
				ParticleHelper.spawnAttackParticleAt(this.posX + motX*1.5D, this.posY, this.posZ + motZ*1.5D, 
						-motX*0.5D, 0D, -motZ*0.5D, (byte)15);
			}
		}
	}
	
	//check entity state every tick
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		//server side
        if((!worldObj.isRemote)) {      	
        	//check every 10 ticks
        	if(ticksExisted % 10 == 0) {
        		//set air value
        		if(this.getAir() < 300) {
                	setAir(300);
                }
        		
        		//update target
            	TargetHelper.updateTarget(this);
            	
            	//get target from vanilla target AI
            	if(this.getAttackTarget() != null) {
            		this.setEntityTarget(this.getAttackTarget());
            	}
            	
//      			LogHelper.info("DEBUG: update hostile ship: "+this.getEntityTarget());
//      			LogHelper.info("DEBUG: taget:   "+this.getEntityTarget()+"      "+this.getAttackTarget());
        	}//end every 10 ticks	
        }
        //client side
        else {
        	if(this.ticksExisted % 100 == 0) {
	        	//check hp state
	    		float hpState = this.getHealth() / this.getMaxHealth();
	    		if(hpState > 0.75F) {		//normal
	    			this.setStateEmotion(ID.S.HPState, ID.HPState.NORMAL, false);
	    		}
	    		else if(hpState > 0.5F){	//minor damage
	    			this.setStateEmotion(ID.S.HPState, ID.HPState.MINOR, false);
	    		}
				else if(hpState > 0.25F){	//moderate damage
					this.setStateEmotion(ID.S.HPState, ID.HPState.MODERATE, false);   			
				}
				else {						//heavy damage
					this.setStateEmotion(ID.S.HPState, ID.HPState.HEAVY, false);
				}
        	}	
    		
        	if(this.ticksExisted % 20 == 0) {
    			//generate HP state effect
    			switch(getStateEmotion(ID.S.HPState)) {
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
    		}
        }
	}
	
	@Override
	public ShipPathNavigate getShipNavigate() {
		return this.shipNavigator;
	}

	@Override
	public ShipMoveHelper getShipMoveHelper() {
		return this.shipMoveHelper;
	}
	
	//update ship move helper
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		
        EntityHelper.updateShipNavigator(this);
    }
	
	@Override
	public boolean canFly() {
		return false;
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void setShipDepth(double par1) {
		ShipDepth = par1;
	}

	@Override
	public boolean getIsLeashed() {
		return false;
	}

	@Override
	public int getLevel() {
		return 150;
	}

	@Override
	public boolean useAmmoLight() {
		return true;
	}

	@Override
	public boolean useAmmoHeavy() {
		return true;
	}

	@Override
	public int getStateMinor(int id) {
		return 0;
	}

	@Override
	public void setStateMinor(int state, int par1) {}

	@Override
	public float getEffectEquip(int id) {	//cri rate
		switch(id) {
		case ID.EF_CRI:
			return 0.15F;
		default:
			return 0F;
		}
	}

	@Override
	public float getDefValue() {
		return defValue;
	}

	@Override
	public void setEntitySit() {}

	//get model rotate angle, par1 = 0:X, 1:Y, 2:Z
    @Override
    public float getModelRotate(int par1) {
    	switch(par1) {
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
	public void setModelRotate(int par1, float par2) {
		switch(par1) {
    	default:
    		rotateAngle[0] = par2;
    	case 1:
    		rotateAngle[1] = par2;
    	case 2:
    		rotateAngle[2] = par2;
    	}
	}

	@Override
	public boolean getAttackType(int par1) {
		return true;
	}

	@Override
	public int getPlayerUID() {
		return -100;	//-100 for hostile mob
	}

	@Override
	public void setPlayerUID(int uid) {}
	
	@Override
	public Entity getHostEntity() {
		return this;
	}
	
	@Override
	public Entity getEntityRevengeTarget() {
		return this.rvgTarget;
	}

	@Override
	public int getEntityRevengeTime() {
		return this.revengeTime;
	}

	@Override
	public void setEntityRevengeTarget(Entity target) {
		this.rvgTarget = target;
	}
  	
  	@Override
	public void setEntityRevengeTime() {
		this.revengeTime = this.ticksExisted;
	}
  	
  	@Override
	protected boolean interact(EntityPlayer player) {
		//use kaitai hammer to kill hostile ship (creative mode only)
		if(!this.worldObj.isRemote && player.capabilities.isCreativeMode) {
			if(player.inventory.getCurrentItem() != null && 
			   player.inventory.getCurrentItem().getItem() == ModItems.KaitaiHammer) {
				this.setDead();
			}
		}
		
        return false;
    }
  	
  	@Override
	public int getAttackAniTick() {
		return this.StartEmotion3;
	}

	@Override
	public void setAttackAniTick(int par1) {
		this.StartEmotion3 = par1;
	}


}
