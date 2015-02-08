package com.lulan.shincolle.ai;

import java.util.Random;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IEntityShip;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

/**ENTITY RANGE ATTACK AI
 * 從骨弓的射箭AI修改而來
 * entity必須實作attackEntityWithAmmo, attackEntityWithHeavyAmmo 兩個方法
 */
public class EntityAIRangeAttack extends EntityAIBase {
	
	private Random rand = new Random();
    private BasicEntityShip entityHost;  	//entity with AI
    private EntityLivingBase attackTarget;  //entity of target
    private int delayLight = 0;			//light attack delay (attack when time 0)
    private int maxDelayLight;	    //light attack max delay (calc from ship attack speed)
    private int delayHeavy = 0;			//heavy attack delay
    private int maxDelayHeavy;	    //heavy attack max delay (= light delay x5)    
    private double entityMoveSpeed;	//move speed when finding attack path
    private int onSightTime;		//target on sight time
    private float attackRange;		//attack range
    private float rangeSq;			//attack range square
    private int aimTime;			//time before fire
    
 
    //parm: host, move speed, p4, attack delay, p6
    public EntityAIRangeAttack(BasicEntityShip host) {

        if (!(host instanceof BasicEntityShip)) {
            throw new IllegalArgumentException("RangeAttack AI requires BasicEntityShip with attackEntityWithAmmo");
        }
        else {
            this.entityHost = host;
            this.setMutexBits(3);
        }
    }

    //check ai start condition
    public boolean shouldExecute() {
    	EntityLivingBase target = this.entityHost.getAttackTarget();
    	
        if (target != null && (this.entityHost.hasAmmoLight() || this.entityHost.hasAmmoHeavy())) {   
        	this.attackTarget = target;
            return true;
        }       
        
        return false;
    }
    
    //init AI parameter, call once every target
    @Override
    public void startExecuting() {
    	this.entityMoveSpeed = 1D; 	
    	this.maxDelayLight = (int)(20F / (this.entityHost.getFinalSPD()));
    	this.maxDelayHeavy = (int)(100F / (this.entityHost.getFinalSPD()));    	
    	this.aimTime = (int) (20F * (float)( 150 - this.entityHost.getShipLevel() ) / 150F) + 10;        
    	
    	//if target changed, check the delay time from prev attack
    	if(this.delayLight <= this.aimTime) {
    		this.delayLight = this.aimTime;
    	}
    	if(this.delayHeavy <= this.aimTime * 2) {
    		this.delayHeavy = this.aimTime * 2;
    	}
    	
        this.attackRange = this.entityHost.getFinalHIT()+1;
        this.rangeSq = this.attackRange * this.attackRange;
       
    }

    //判定是否繼續AI： 有target就繼續, 或者已經移動完畢就繼續
    public boolean continueExecuting() {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    //重置AI方法
    public void resetTask() {
        this.attackTarget = null;
        this.onSightTime = 0;
        this.delayLight = this.aimTime;
        this.delayHeavy = this.aimTime;
    }

    //進行AI
    public void updateTask() {   	
    	double distSq = 0;			//跟目標的直線距離(的平方)
    	boolean onSight = false;	//判定直射是否無障礙物
    	  	
    	if(this.attackTarget != null) {  //for lots of NPE issue-.-	
    		distSq = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.boundingBox.minY, this.attackTarget.posZ);       	
            onSight = this.entityHost.getEntitySenses().canSee(this.attackTarget);  

	        //可直視, 則parf++, 否則重置為0
	        if(onSight) {
	            ++this.onSightTime;
	        }
	        else {
	            this.onSightTime = 0;
	        }
	        
	        //若直視太久(每12秒檢查一次), 有50%機率清除目標, 使其重新選一次目標
	        if((this.onSightTime > 240) && (this.onSightTime % 240 == 0)) {
	        	if(rand.nextInt(2) > 0)  {
	        		this.resetTask();
	        		return;
	        	}
	        }
	
	        //若目標進入射程, 且目標無障礙物阻擋, 則清空AI移動的目標, 以停止繼續移動      
	        if(distSq < (double)this.rangeSq && onSight) {
	            this.entityHost.getNavigator().clearPathEntity();
	        }
	        else {	//目標移動, 則繼續追擊
	            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
	        }
	
	        //設定攻擊時, 頭部觀看的角度
	        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
	        
	        //delay time decr
	        this.delayLight--;
	        this.delayHeavy--;
	        
	        //若attack delay倒數完了且瞄準時間夠久, 則開始攻擊
	        if(this.delayLight <= 0 && this.onSightTime >= this.aimTime && this.entityHost.hasAmmoLight()) {
	        	//若目標跑出範圍 or 目標被阻擋, 則停止攻擊, 進行下一輪ai判定
	            if(distSq > (double)this.rangeSq || !onSight) { return; }
	            
	            //使用entity的attackEntityWithAmmo進行傷害計算
	            this.entityHost.attackEntityWithAmmo(this.attackTarget);
	            this.delayLight = this.maxDelayLight;
	        }
	        
	        //若attack delay倒數完了且瞄準時間夠久, 則開始攻擊
	        if(this.delayHeavy <= 0 && this.onSightTime >= this.aimTime && this.entityHost.hasAmmoHeavy()) {
	        	//若目標跑出範圍 or 目標被阻擋 or 距離太近, 則停止攻擊, 進行下一輪ai判定
	            if(distSq > (double)this.rangeSq || distSq < 4D || !onSight) { return; }
	            
	            //使用entity的attackEntityWithAmmo進行傷害計算
	            this.entityHost.attackEntityWithHeavyAmmo(this.attackTarget);
	            this.delayHeavy = this.maxDelayHeavy;
	        } 
	        
	        //若超過太久都打不到目標(或是追不到), 則重置目標
	        if(this.delayHeavy < -200 || this.delayLight < -200) {
	        	this.resetTask();
	        	return;
	        }
    	}
    }
}
