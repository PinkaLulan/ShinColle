package com.lulan.shincolle.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.IShipAttack;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.utility.LogHelper;

/**ATTACK ON COLLIDE SHIP VERSION
 * host必須實作IShipAttack跟IShipEmotion, 且extend EntityCreature
 */
public class EntityAIShipAttackOnCollide extends EntityAIBase {
	
    World worldObj;
    IShipAttack host;
    EntityCreature host2;
    /** An amount of decrementing ticks that allows the entity to attack once the tick reaches 0. */
    int attackTick;
    /** The speed with which the mob will approach the target */
    double speedTowardsTarget;
    /** When true, the mob will continue chasing its target, even if it can't find a path to them right now. */
    boolean longMemory;
    /** The PathEntity of our entity. */
    PathEntity entityPathEntity;
    Class classTarget;
    private int delayAttack;
    private double tarX;
    private double tarY;
    private double tarZ;

    private int failedPathFindingPenalty;

    public EntityAIShipAttackOnCollide(IShipAttack host, Class classTarget, double speed, boolean longMemory) {
        this(host, speed, longMemory);
        this.classTarget = classTarget;
    }

    public EntityAIShipAttackOnCollide(IShipAttack host, double speed, boolean longMemory) {
        this.host = host;
        this.host2 = (EntityCreature) host;
        this.worldObj = ((Entity)host).worldObj;
        this.speedTowardsTarget = speed;
        this.longMemory = longMemory;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
    	if(this.host2.isRiding()) {
    		return false;
    	}
    	
        EntityLivingBase entitylivingbase = this.host.getTarget();

        //無目標 or 目標死亡 or 正在坐下時 不啟動AI
        if(entitylivingbase == null || ((IShipEmotion)host).getIsSitting()) {
            return false;
        }
        else if(entitylivingbase != null && entitylivingbase.isDead) {
        	return false;
        }
        else if(this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass())) {
            return false;
        }
        else {
            if(-- this.delayAttack <= 0) {
                this.entityPathEntity = this.host2.getNavigator().getPathToEntityLiving(entitylivingbase);
                this.delayAttack = 4 + this.host2.getRNG().nextInt(7);
                return this.entityPathEntity != null;
            }
            else {
                return true;
            }
        }
    }

    public boolean continueExecuting() {
    	if(this.host2.isRiding()) {
    		return false;
    	}
    	
        EntityLivingBase entitylivingbase = this.host.getTarget();
        
        return (entitylivingbase == null || !entitylivingbase.isEntityAlive()) ? false : 
        	   (!this.longMemory ? !this.host2.getNavigator().noPath() : 
        	   this.host2.isWithinHomeDistance(MathHelper.floor_double(entitylivingbase.posX), 
        			   							  MathHelper.floor_double(entitylivingbase.posY), 
        			   							  MathHelper.floor_double(entitylivingbase.posZ)));
    }

    public void startExecuting() {
        this.host2.getNavigator().setPath(this.entityPathEntity, speedTowardsTarget);
        this.host2.getNavigator().setCanSwim(true);
        this.delayAttack = 0;
    }

    public void resetTask() {
        this.host2.getNavigator().clearPathEntity();
        this.host2.setAttackTarget(null);
    }

    public void updateTask() {
    	if(this.host2.isRiding()) {
    		return;
    	}
    	
        EntityLivingBase entitylivingbase = this.host.getTarget();
        
        //null check for target continue set null bug (set target -> clear target in one tick)
        if(entitylivingbase == null || entitylivingbase.isDead) {
        	resetTask();
        	return;
        }
        
        this.host2.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        
        double distTarget = this.host2.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
        double distAttack = (double)(this.host2.width * this.host2.width * 10F + entitylivingbase.width * 3F);
        
        --this.delayAttack;

        //官方內建的水平移動AI
        if((this.longMemory || this.host2.getEntitySenses().canSee(entitylivingbase)) && this.delayAttack <= 0 && (this.tarX == 0.0D && this.tarY == 0.0D && this.tarZ == 0.0D || entitylivingbase.getDistanceSq(this.tarX, this.tarY, this.tarZ) >= 1.0D || this.host2.getRNG().nextFloat() < 0.1F)) {
            this.tarX = entitylivingbase.posX;
            this.tarY = entitylivingbase.boundingBox.minY;
            this.tarZ = entitylivingbase.posZ;
            this.delayAttack = failedPathFindingPenalty + 4 + this.host2.getRNG().nextInt(7);

            if(this.host2.getNavigator().getPath() != null) {
                PathPoint finalPathPoint = this.host2.getNavigator().getPath().getFinalPathPoint();
                if(finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1) {
                    failedPathFindingPenalty = 0;
                }
                else {
                    failedPathFindingPenalty += 10;
                }
            }
            else {
                failedPathFindingPenalty += 10;
            }

            if(distTarget > 1024.0D) {
                this.delayAttack += 10;
            }
            else if (distTarget > 256.0D) {
                this.delayAttack += 5;
            }

            if(!this.host2.getNavigator().tryMoveToEntityLiving(entitylivingbase, speedTowardsTarget)) {
                this.delayAttack += 10;
            }
        }
        
        //在水中時, 根據目標位置上下移動
        if(this.host2.isInWater()) {
//        	LogHelper.info("DEBUG : melee water move");
        	double distY = this.tarY - this.host2.posY;
        	
        	if(distY > 1D) {
        		this.host2.motionY = 0.15D;
        	}
        	else if(distY < -1D) {
        		this.host2.motionY = -0.15D;
        	}
        	else {
        		this.host2.motionY = 0D;
        	}
        	
        	//若水平撞到東西, 則嘗試跳跳
    		if(this.host2.isCollidedHorizontally) {
    			this.host2.motionY += 0.25D;
    		}
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);

        if(distTarget <= distAttack && this.attackTick <= 20) {
            this.attackTick = 20;

            if(this.host2.getHeldItem() != null) {
                this.host2.swingItem();
            }

            this.host2.attackEntityAsMob(entitylivingbase);
        }
    }
}