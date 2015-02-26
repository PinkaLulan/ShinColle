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
    private EntityLivingBase attackTarget2;  	//for target continue reset to null bug
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
            this.setMutexBits(3);
        }
    }

    //check ai start condition
    public boolean shouldExecute() {
    	if(this.host.isSitting()) return false;
    	
    	EntityLivingBase target = this.host.getAttackTarget();
//    	LogHelper.info("DEBUG : carrier attack "+target);
        if (((target != null && target.isEntityAlive()) || 
        	  this.attackTarget2 != null && this.attackTarget2.isEntityAlive()) &&
        	((this.host.getStateFlag(ID.F.UseAirLight) && this.host.hasAmmoLight() && this.host.hasAirLight()) || 
        	(this.host.getStateFlag(ID.F.UseAirHeavy) && this.host.hasAmmoHeavy() && this.host.hasAirHeavy()))) {   
        	this.attackTarget = target;
        	this.attackTarget2 = target;
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
        return this.shouldExecute() || !this.host.getNavigator().noPath();
    }

    //重置AI方法
    public void resetTask() {
        this.attackTarget = null;
        this.delayLaunch = this.maxDelayLaunch;
    }

    //進行AI
    public void updateTask() {
    	boolean onSight = false;	//判定直射是否無障礙物
    	//get update attributes
    	if(this.host != null && this.host.ticksExisted % 80 == 0) {	
    		this.maxDelayLaunch = (int)(80F / (this.host.getStateFinal(ID.SPD))) + 20;
            this.attackRange = this.host.getStateFinal(ID.HIT);
            this.rangeSq = this.attackRange * this.attackRange;
    	}
    	
    	if(this.attackTarget != null) {  //for lots of NPE issue-.-
    		if(this.distSq >= this.rangeSq) {
    			this.distX = this.attackTarget.posX - this.host.posX;
        		this.distY = this.attackTarget.posY - this.host.posY;
        		this.distZ = this.attackTarget.posZ - this.host.posZ;	
        		this.distSq = distX*distX + distY*distY + distZ*distZ;
    	
    	        //若目標進入射程, 且目標無障礙物阻擋, 則清空AI移動的目標, 以停止繼續移動      
    	        if(distSq < (double)this.rangeSq && onSight) {
    	            this.host.getNavigator().clearPathEntity();
    	        }
    	        else {	//目標移動, 則繼續追擊	
    	        	//在液體中, 採直線前進
    	        	if(this.host.getShipDepth() > 0D) {
    	        		//額外加上y軸速度, getPathToXYZ對空氣跟液體方塊無效, 因此y軸速度要另外加
    	        		if(this.distY > 1.5D && this.host.getShipDepth() > 1.5D) {  //避免水面彈跳
    	        			this.motY = 0.2F;
    	        		}
    	        		else if(this.distY < -1D) {
    	        			this.motY = -0.2F;
    	        		}
    	  		
    	        		//若水平撞到東西, 則嘗試跳跳
    	        		if(this.host.isCollidedHorizontally) {
    	        			this.host.setPosition(this.host.posX, this.host.posY + 0.3D, this.host.posZ);
    	        		}
    	        		
    	        		//若直線可視, 則直接直線移動
    	        		if(this.host.getEntitySenses().canSee(this.attackTarget)) {
    	        			double speed = this.host.getStateFinal(ID.MOV);
    	        			this.motX = (this.distX / this.distSq) * speed * 6D;
    	        			this.motZ = (this.distZ / this.distSq) * speed * 6D;

    	        			this.host.motionY = this.motY;
    	        			this.host.getMoveHelper().setMoveTo(this.host.posX+this.motX, this.host.posY+this.motY, this.host.posZ+this.motZ, 1D);
    	        		}
    	           	}
                	else {	//非液體中, 採用一般尋找路徑法
                		this.host.getNavigator().tryMoveToEntityLiving(this.attackTarget, 1D);
                	}
                }
    		}//end dist > range
	
	        //設定攻擊時, 頭部觀看的角度
	        this.host.getLookHelper().setLookPosition(this.attackTarget.posX, this.attackTarget.posY+30D, this.attackTarget.posZ, 40.0F, 90.0F);
	        
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
	        if(this.typeLaunch && this.distSq < this.rangeSq && this.delayLaunch <= 0 && this.host.hasAmmoLight() && this.host.getStateFlag(ID.F.UseAirLight) && this.host.hasAirHeavy()) {
	            this.host.attackEntityWithAircraft(this.attackTarget);
	            this.delayLaunch = this.maxDelayLaunch;
	            this.typeLaunch = !this.typeLaunch;
	        }
	        
	        //若attack delay倒數完了且瞄準時間夠久, 則開始攻擊, no onSight check
	        if(!this.typeLaunch && this.distSq < this.rangeSq && this.delayLaunch <= 0 && this.host.hasAmmoHeavy() && this.host.getStateFlag(ID.F.UseAirHeavy) && this.host.hasAirHeavy()) {	            
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
