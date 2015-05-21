package com.lulan.shincolle.entity;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathEntity;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.ai.path.ShipPathPoint;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityRensouhouBoss extends EntityMob implements IShipEmotion, IShipAttack {
	
	protected IShipAttack host;  		//host target
	protected EntityLivingBase host2;
	protected EntityLivingBase target;	//onImpact target (for entity)
	protected World world;
	protected ShipPathNavigate shipNavigator;	//水空移動用navigator
	protected ShipMoveHelper shipMoveHelper;
    
    //attributes
	protected float atk;				//damage
	protected float atkSpeed;			//attack speed
	protected float atkRange;			//attack range
	protected float defValue;			//def value
	protected float movSpeed;			//def value
    protected float kbValue;			//knockback value
    
    //AI flag
    protected int numAmmoLight;
    protected int numAmmoHeavy;
    
    //model display
    /**EntityState: 0:HP State 1:Emotion 2:Emotion2*/
	protected byte StateEmotion;		//表情1
	protected byte StateEmotion2;		//表情2
	protected int StartEmotion;			//表情1 開始時間
	protected int StartEmotion2;		//表情2 開始時間
	protected boolean headTilt;

	
    public EntityRensouhouBoss(World world) {
		super(world);
		this.setSize(0.9F, 1.7F);
		this.isImmuneToFire = true;
	}
    
    public EntityRensouhouBoss(World world, IShipAttack host, EntityLivingBase target) {
		super(world);
		this.world = world;
        this.host = host;
        this.host2 = (EntityLivingBase) host;
        this.target = target;
        this.isImmuneToFire = true;
        shipNavigator = new ShipPathNavigate(this, worldObj);
		shipMoveHelper = new ShipMoveHelper(this);
        
        //basic attr
        this.atk = 30F;
        this.atkSpeed = 0.8F;
        this.atkRange = host.getAttackRange();
        this.defValue = 70F;
        this.movSpeed = 0.6F;
        
        //AI flag
        this.numAmmoLight = 6;
        this.numAmmoHeavy = 0;
        this.StateEmotion = 0;
        this.StateEmotion2 = 0;
        this.StartEmotion = 0;
        this.StartEmotion2 = 0;
        this.headTilt = false;
           
        //設定發射位置
        this.posX = host2.posX + rand.nextDouble() * 6D - 3D;
        this.posY = host2.posY + 0.5D;
        this.posZ = host2.posZ + rand.nextDouble() * 6D - 3D;
        
        //check the place is safe to summon
    	if(!EntityHelper.checkBlockSafe(world, (int)posX, (int)posY, (int)posZ)) {
    		this.posX = host2.posX;
            this.posY = host2.posY;
            this.posZ = host2.posZ;
    	}
        
        this.setPosition(this.posX, this.posY, this.posZ);
 
	    //設定基本屬性
	    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(this.movSpeed);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(this.atkRange + 16); //此為找目標, 路徑的範圍
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2D);
		if(this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
	}
    
    @Override
	public float getEyeHeight() {
		return this.height;
	}
	
	//setup AI
	protected void setAIList() {
		this.clearAITasks();
		this.clearAITargetTasks();

		this.getNavigator().setEnterDoors(true);
		this.getNavigator().setAvoidsWater(false);
		this.getNavigator().setCanSwim(true);
		
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
		this.setAttackTarget(target);
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk) {
		//disable 
		if(attacker.getDamageType() == "inWall") {
			return false;
		}
				
		//set hurt face
    	if(this.getStateEmotion(ID.S.Emotion) != ID.Emotion.O_O) {
    		this.setStateEmotion(ID.S.Emotion, ID.Emotion.O_O, true);
    	}
    	
    	//進行def計算
        float reduceAtk = atk * (1F - this.defValue / 100F);    
        if(atk < 0) { atk = 0; }
        
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
  		}
    	
    	return super.attackEntityFrom(attacker, reduceAtk);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//client side
		if(this.worldObj.isRemote) {
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
		//server side
		else {
			boolean setdead = false;
			
			//owner消失(通常是server restart)
			if(this.getOwner() == null) {
				setdead = true;
			}
			else {
				//超過60秒自動消失
				if(this.ticksExisted > 1200) {
					setdead = true;
				}

				//target is dead
				if(this.getAttackTarget() == null || this.getAttackTarget().isDead) {
					//change target
					if(this.host != null && this.host.getTarget() != null &&
					   this.host.getTarget().isEntityAlive()) {
						this.target = this.host.getTarget();
					}
					else {
						setdead = true;
					}	
				}
				
				//防止溺死
				if(this.isInWater() && this.ticksExisted % 100 == 0) {
					this.setAir(300);
				}
			}
			
			//is done
			if(setdead) {
				this.setDead();
			}
		}
	}

	@Override
	public byte getStateEmotion(int id) {
		return id == 1 ? StateEmotion : StateEmotion2;
	}
	
	@Override
	public void setStateEmotion(int id, int value, boolean sync) {
		switch(id) {
		case 1:
			StateEmotion = (byte) value;
			break;
		case 2:
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
		return this.headTilt;
	}

	@Override
	public void setStateFlag(int id, boolean flag) {
		this.headTilt = flag;
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
        return this.host2;
    }
    
    @Override
    public EntityLivingBase getAttackTarget() {
        return this.target;
    }
    
    @Override
	//light attack
	public boolean attackEntityWithAmmo(Entity target) {
		float atkLight = this.atk;
		float kbValue = 0.001F;

		//play cannon fire sound at attacker
        playSound(Reference.MOD_ID+":ship-firesmall", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
        //此方法比getLook還正確 (client sync問題)
        float distX = (float) (target.posX - this.posX);
        float distY = (float) (target.posY - this.posY);
        float distZ = (float) (target.posZ - this.posZ);
        float distSqrt = MathHelper.sqrt_float(distX*distX + distY*distY + distZ*distZ);
        distX = distX / distSqrt;
        distY = distY / distSqrt;
        distZ = distZ / distSqrt;
		
		//發射者煙霧特效
        TargetPoint point0 = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 6, this.posX, this.posY+1.5D, this.posZ, distX, distY, distZ, true), point0);
		
		//calc miss chance, if not miss, calc cri/multi hit
		TargetPoint point = new TargetPoint(this.dimension, this.host2.posX, this.host2.posY, this.host2.posZ, 64D);
        float missChance = 0.25F;
  		
        //calc miss chance
        if(this.rand.nextFloat() < missChance) {
        	atkLight = 0;	//still attack, but no damage
        	//spawn miss particle
        	
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 10, false), point);
        }
        else {
        	//roll cri -> roll double hit -> roll triple hit (triple hit more rare)
        	//calc critical
        	if(this.rand.nextFloat() < 0.1F) {
        		atkLight *= 1.5F;
        		//spawn critical particle
            	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 11, false), point);
        	}
        	else {
        		//calc double hit
            	if(this.rand.nextFloat() < 0.1F) {
            		atkLight *= 2F;
            		//spawn double hit particle
            		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 12, false), point);
            	}
            	else {
            		//calc double hit
                	if(this.rand.nextFloat() < 0.1F) {
                		atkLight *= 3F;
                		//spawn triple hit particle
                		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 13, false), point);
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
	                   0.02D, (double)(MathHelper.cos(rotationYaw * (float)Math.PI / 180.0F) * kbValue));
	        }
	        
	        //display hit particle on target
	        TargetPoint point1 = new TargetPoint(this.dimension, target.posX, target.posY, target.posZ, 64D);
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 9, false), point1);
        }
	    
	    //消耗彈藥計算
  		if(numAmmoLight > 0) {
  			numAmmoLight--;
  			
  			if(numAmmoLight <= 0) {
  				this.setDead();
  			}
  		}

	    return isTargetHurt;
	}

	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target) {
		//get attack value
		float atkHeavy = this.atk;
		//set knockback value (testing)
		float kbValue = 0.08F;

		//play cannon fire sound at attacker
        this.playSound(Reference.MOD_ID+":ship-fireheavy", ConfigHandler.fireVolume, 0.7F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        
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
        
        //calc miss chance, miss: add random offset(0~6) to missile target 
        float missChance = 0.25F;	//max miss chance = 30%
		
        //calc miss chance
        if(this.rand.nextFloat() < missChance) {
        	atkHeavy = 0;	//still attack, but no damage
        	//spawn miss particle
        	TargetPoint point = new TargetPoint(this.dimension, this.host2.posX, this.host2.posY, this.host2.posZ, 64D);
        	CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host2, 10, false), point);
        }

        //spawn missile
        EntityAbyssMissile missile = new EntityAbyssMissile(this.worldObj, this, 
        		tarX, tarY+target.height*0.2F, tarZ, launchPos, atkHeavy, kbValue, isDirect, -1F);
        this.worldObj.spawnEntityInWorld(missile);
        
        //消耗彈藥計算
  		if(numAmmoHeavy > 0) {
  			numAmmoHeavy--;
  			
  			if(numAmmoHeavy <= 0) {
  				this.setDead();
  			}
  		}
  		
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
	public int getAmmoLight() {
		return this.numAmmoLight;
	}

	@Override
	public int getAmmoHeavy() {
		return this.numAmmoHeavy;
	}

	@Override
	public boolean hasAmmoLight() {
		return this.numAmmoLight > 0;
	}

	@Override
	public boolean hasAmmoHeavy() {
		return false;
	}

	@Override
	public void setAmmoLight(int num) {
		this.numAmmoLight = num;
	}

	@Override
	public void setAmmoHeavy(int num) {
		this.numAmmoHeavy = num;
	}

	@Override
	public float getAttackDamage() {	//not used for rensouhou
		return 0;
	}

	@Override
	public EntityLivingBase getTarget() {
		return this.getAttackTarget();
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
        
        //若有水空path, 則更新ship navigator
        if(this.getShipNavigate() != null && !this.getShipNavigate().noPath()) {
        	//若同時有官方ai的路徑, 則清除官方ai路徑
        	if(!this.getNavigator().noPath()) {
        		this.getNavigator().clearPathEntity();
        	}
			//用particle顯示path point
			if(this.ticksExisted % 20 == 0) {
				ShipPathEntity pathtemp = this.getShipNavigate().getPath();
				ShipPathPoint pointtemp;
				
				for(int i = 0; i < pathtemp.getCurrentPathLength(); i++) {
					pointtemp = pathtemp.getPathPointFromIndex(i);
					//發射者煙霧特效
			        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
					//路徑點畫紅色, 目標點畫綠色
					if(i == pathtemp.getCurrentPathIndex()) {
						CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 16, pointtemp.xCoord, pointtemp.yCoord+0.5D, pointtemp.zCoord, 0F, 0F, 0F, false), point);
					}
					else {
						CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 18, pointtemp.xCoord, pointtemp.yCoord+0.5D, pointtemp.zCoord, 0F, 0F, 0F, false), point);
					}
				}
			}

			this.worldObj.theProfiler.startSection("ship navi");
	        this.shipNavigator.onUpdateNavigation();
	        this.worldObj.theProfiler.endSection();
	        this.worldObj.theProfiler.startSection("ship move");
	        this.shipMoveHelper.onUpdateMoveHelper();
	        this.worldObj.theProfiler.endSection();
		}
    }
	
	@Override
	public boolean canFly() {
		return false;
	}
	

}

