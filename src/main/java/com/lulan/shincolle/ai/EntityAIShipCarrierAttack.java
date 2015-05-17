package com.lulan.shincolle.ai;

import java.util.Random;

import com.lulan.shincolle.entity.BasicEntityShipLarge;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

/**CARRIER RANGE ATTACK AI
 * entity必須實作attackEntityWithAircraft, attackEntityWithHeavyAircraft 兩個方法
 */
public class EntityAIShipCarrierAttack extends EntityAIBase {
	
	private Random rand = new Random();
    private BasicEntityShipLarge host;  		//entity with AI
    private EntityLivingBase attackTarget;  	//entity of target
    private int delayLaunch = 0;		//aircraft launch delay
    private int maxDelayLaunch;			//max launch delay
    private boolean typeLaunch = false;	//aircraft launch type, true = light
    private float attackRange;		//attack range
    private float rangeSq;			//attack range square
    
    //直線前進用餐數
    private double distSq, distX, distY, distZ, motX, motY, motZ;	//跟目標的直線距離(的平方)
    
 
    //parm: host, move speed, p4, attack delay, p6
    public EntityAIShipCarrierAttack(BasicEntityShipLarge host) {
        if (!(host instanceof BasicEntityShipLarge)) {
            throw new IllegalArgumentException("CarrierAttack AI requires BasicEntityShipLarge");
        }
        else {
            this.host = host;
            this.setMutexBits(4);
        }
    }

    //check ai start condition
    public boolean shouldExecute() {
    	if(this.host.isSitting()) return false;
    	
    	EntityLivingBase target = this.host.getAttackTarget();
//    	LogHelper.info("DEBUG : carrier attack "+target);
        if (target != null && target.isEntityAlive() &&
        	((this.host.getStateFlag(ID.F.UseAirLight) && this.host.hasAmmoLight() && this.host.hasAirLight()) || 
        	(this.host.getStateFlag(ID.F.UseAirHeavy) && this.host.hasAmmoHeavy() && this.host.hasAirHeavy()))) {   
        	this.attackTarget = target;
        	return true;
        }
        return false;
    }
    
    //init AI parameter, call once every target
    @Override
    public void startExecuting() {
        distSq = distX = distY = distZ = motX = motY = motZ = 0D;
    }

    //判定是否繼續AI： 有target就繼續, 或者已經移動完畢就繼續
    public boolean continueExecuting() {
        return this.shouldExecute() || !this.host.getShipNavigate().noPath();
    }

    //重置AI方法
    public void resetTask() {
        this.attackTarget = null;
    }

    //進行AI
    public void updateTask() {
    	if(this.attackTarget != null) {  //for lots of NPE issue-.-
    		boolean onSight = this.host.getEntitySenses().canSee(this.attackTarget);
//    		boolean onSight = true;
    		
    		//get update attributes
        	if(this.host != null && this.host.ticksExisted % 80 == 0) {	
        		this.maxDelayLaunch = (int)(80F / (this.host.getStateFinal(ID.SPD))) + 20;
                this.attackRange = this.host.getStateFinal(ID.HIT);
                this.rangeSq = this.attackRange * this.attackRange;
        	}

    		if(this.distSq >= this.rangeSq) {
    			this.distX = this.attackTarget.posX - this.host.posX;
        		this.distY = this.attackTarget.posY - this.host.posY;
        		this.distZ = this.attackTarget.posZ - this.host.posZ;	
        		this.distSq = distX*distX + distY*distY + distZ*distZ;
    	
        		//若目標進入射程, 且目標無障礙物阻擋, 則清空AI移動的目標, 以停止繼續移動      
		        if(distSq < (double)this.rangeSq && onSight) {
		        	this.host.getShipNavigate().clearPathEntity();
		        }
		        else {	//目標移動, 則繼續追擊		        	
		        	if(host.ticksExisted % 20 == 0) {
		        		this.host.getShipNavigate().tryMoveToEntityLiving(this.attackTarget, 1D);
		        	}
	            }
    		}//end dist > range
	
	        //設定攻擊時, 頭部觀看的角度
    		this.host.getLookHelper().setLookPosition(this.attackTarget.posX, this.attackTarget.posY+2D, this.attackTarget.posZ, 40.0F, 90.0F);
	         
	        //delay time decr
	        this.delayLaunch--;

	        //若只使用單一種彈藥, 則停用typeLaunch
	        if(!this.host.getStateFlag(ID.F.UseAirLight)) {
	        	this.typeLaunch = false;
	        }
	        if(!this.host.getStateFlag(ID.F.UseAirHeavy)) {
	        	this.typeLaunch = true;
	        }
	        
	        //若attack delay倒數完了且瞄準時間夠久, 則開始攻擊, no onSight check
	        if(onSight && this.typeLaunch && this.distSq < this.rangeSq && this.delayLaunch <= 0 && this.host.hasAmmoLight() && this.host.getStateFlag(ID.F.UseAirLight) && this.host.hasAirHeavy()) {
	            this.host.attackEntityWithAircraft(this.attackTarget);
	            this.delayLaunch = this.maxDelayLaunch;
	            this.typeLaunch = !this.typeLaunch;
	        }
	        
	        //若attack delay倒數完了且瞄準時間夠久, 則開始攻擊, no onSight check
	        if(onSight && !this.typeLaunch && this.distSq < this.rangeSq && this.delayLaunch <= 0 && this.host.hasAmmoHeavy() && this.host.getStateFlag(ID.F.UseAirHeavy) && this.host.hasAirHeavy()) {	            
	            this.host.attackEntityWithHeavyAircraft(this.attackTarget);
	            this.delayLaunch = this.maxDelayLaunch;
	            this.typeLaunch = !this.typeLaunch;      
	        } 
	        
	        //若超過太久都打不到目標(或是追不到), 則重置目標
	        if(this.delayLaunch < -100) {
	        	this.resetTask();
	        	return;
	        }
    	}
    }
}
