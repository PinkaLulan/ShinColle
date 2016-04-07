package com.lulan.shincolle.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.utility.LogHelper;

/**ATTACK ON COLLIDE SHIP VERSION
 * host必須實作IShipAttack跟IShipEmotion, 且extend EntityCreature
 */
public class EntityAIShipAttackOnCollide extends EntityAIBase {
	
	private IShipAttackBase host;
    private EntityLiving host2;
    private Entity target;
    private double moveSpeed;
    private int delayAttack, delayMax;
    private double tarX;
    private double tarY;
    private double tarZ;

    
    public EntityAIShipAttackOnCollide(IShipAttackBase host, double speed) {
        this.host = host;
        this.host2 = (EntityLiving) host;
        this.moveSpeed = speed;
        this.delayMax = 20;
        this.delayAttack = 20;
        this.setMutexBits(4);
    }

    @Override
	public boolean shouldExecute() {
    	//騎乘跟坐下不執行
    	if(this.host2.isRiding() || host.getIsSitting()) {
    		return false;
    	}
    	
        this.target = this.host.getEntityTarget();

        //確認有活的target才執行
        if(target == null) {
            return false;
        }
        else if(target != null && !target.isEntityAlive()) {
        	return false;
        }
        else {
        	return true;
        }
    }

    @Override
	public boolean continueExecuting() {
    	return shouldExecute();
    }

    @Override
	public void startExecuting() {}

    @Override
	public void resetTask() {}

    @Override
	public void updateTask() {
        //null check
        if(host2 == null || target == null || !target.isEntityAlive()) {
        	resetTask();
        	return;
        }
        
        //look target
        this.host2.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        
        //calc dist
        double distTarget = this.host2.getDistanceSq(target.posX, target.boundingBox.minY, target.posZ);
        double distAttack = this.host2.width * this.host2.width * 10F + target.width * 3F;
        
        if(host2.ticksExisted % 16 == 0) {
        	//update attrs
            if(this.host != null) {
            	this.delayMax = (int)(80F / this.host.getAttackSpeed());
            	this.delayMax = (int)(this.delayMax + this.host2.getRNG().nextInt(this.delayMax) * 0.5F);
            }
            
            //move to target every 32 ticks
            if(host2.ticksExisted % 32 == 0) {
            	if(distTarget > distAttack) {
            		this.host.getShipNavigate().tryMoveToEntityLiving(this.target, this.moveSpeed);
            	}
            	else {
            		this.host.getShipNavigate().clearPathEntity();
            	}
            }
        }

        //attack target
        if(distTarget <= distAttack && --this.delayAttack == 0) {
        	this.delayAttack = this.delayMax;

            if(this.host2.getHeldItem() != null) {
                this.host2.swingItem();
            }

            this.host2.attackEntityAsMob(target);
        }
    }
}