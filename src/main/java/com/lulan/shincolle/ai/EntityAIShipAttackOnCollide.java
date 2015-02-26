package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityShip;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**ATTACK ON COLLIDE SHIP VERSION
 * 加上null檢查, 防止NPE出現
 */
public class EntityAIShipAttackOnCollide extends EntityAIBase {
	
    World worldObj;
    BasicEntityShip host;
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

    public EntityAIShipAttackOnCollide(BasicEntityShip host, Class classTarget, double speed, boolean longMemory) {
        this(host, speed, longMemory);
        this.classTarget = classTarget;
    }

    public EntityAIShipAttackOnCollide(BasicEntityShip host, double speed, boolean longMemory) {
        this.host = host;
        this.worldObj = host.worldObj;
        this.speedTowardsTarget = speed;
        this.longMemory = longMemory;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.host.getAttackTarget();

        //無目標 or 目標死亡 or 正在坐下時 不啟動AI
        if(entitylivingbase == null || !entitylivingbase.isEntityAlive() || this.host.isSitting()) {
            return false;
        }
        else if(this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass())) {
            return false;
        }
        else {
            if(-- this.delayAttack <= 0) {
                this.entityPathEntity = this.host.getNavigator().getPathToEntityLiving(entitylivingbase);
                this.delayAttack = 4 + this.host.getRNG().nextInt(7);
                return this.entityPathEntity != null;
            }
            else {
                return true;
            }
        }
    }

    public boolean continueExecuting() {
        EntityLivingBase entitylivingbase = this.host.getAttackTarget();
        return (entitylivingbase == null || !entitylivingbase.isEntityAlive()) ? false : 
        	   (!this.longMemory ? !this.host.getNavigator().noPath() : 
        	   this.host.isWithinHomeDistance(MathHelper.floor_double(entitylivingbase.posX), 
        			   							  MathHelper.floor_double(entitylivingbase.posY), 
        			   							  MathHelper.floor_double(entitylivingbase.posZ)));
    }

    public void startExecuting() {
        this.host.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.delayAttack = 0;
    }

    public void resetTask() {
        this.host.getNavigator().clearPathEntity();
    }

    public void updateTask() {
        EntityLivingBase entitylivingbase = this.host.getAttackTarget();
        
        //null check for target continue set null bug (set target -> clear target in one tick)
        if(entitylivingbase == null) return;
        
        this.host.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
        
        double distTarget = this.host.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
        double distAttack = (double)(this.host.width * 2.0F * this.host.width * 2.0F + entitylivingbase.width);
        
        --this.delayAttack;

        if((this.longMemory || this.host.getEntitySenses().canSee(entitylivingbase)) && this.delayAttack <= 0 && (this.tarX == 0.0D && this.tarY == 0.0D && this.tarZ == 0.0D || entitylivingbase.getDistanceSq(this.tarX, this.tarY, this.tarZ) >= 1.0D || this.host.getRNG().nextFloat() < 0.05F)) {
            this.tarX = entitylivingbase.posX;
            this.tarY = entitylivingbase.boundingBox.minY;
            this.tarZ = entitylivingbase.posZ;
            this.delayAttack = failedPathFindingPenalty + 4 + this.host.getRNG().nextInt(7);

            if(this.host.getNavigator().getPath() != null) {
                PathPoint finalPathPoint = this.host.getNavigator().getPath().getFinalPathPoint();
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

            if(!this.host.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget)) {
                this.delayAttack += 15;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);

        if(distTarget <= distAttack && this.attackTick <= 20) {
            this.attackTick = 20;

            if(this.host.getHeldItem() != null) {
                this.host.swingItem();
            }

            this.host.attackEntityAsMob(entitylivingbase);
        }
    }
}