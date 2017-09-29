package com.lulan.shincolle.ai;

import java.util.Random;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipAircraftAttack;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**CARRIER RANGE ATTACK AI
 * entity必須實作IUseAircraft
 */
public class EntityAIShipCarrierAttack extends EntityAIBase
{
	
	private Random rand = new Random();
    private IShipAircraftAttack host;  	//entity with AI
    private EntityLiving host2;
    private Entity target;  	//entity of target
    private int launchDelay;			//aircraft launch delay
    private int launchDelayMax;			//max launch delay
    private boolean launchType;			//airplane type, true = light
    private float range;			//attack range
    private float rangeSq;				//attack range square
    
    //直線前進用餐數
    private double distSq, distX, distY, distZ;	//跟目標的直線距離(的平方)
    
 
    //parm: host, move speed, p4, attack delay, p6
    public EntityAIShipCarrierAttack(IShipAircraftAttack host)
    {
        if (!(host instanceof IShipAircraftAttack))
        {
            throw new IllegalArgumentException("CarrierAttack AI requires IShipAircraftAttack");
        }
        else
        {
            this.host = host;
            this.host2 = (EntityLiving) host;
            this.setMutexBits(2);
            
            //init value
            this.launchDelay = 20;
            this.launchDelayMax = 40;
            this.launchType = false;
        }
    }

    //check ai start condition
    @Override
	public boolean shouldExecute()
    {
    	//坐下, 裝載中不攻擊
    	if (host.getIsSitting() || host.getStateMinor(ID.M.CraneState) > 0)
    	{
    		return false;
    	}
    	
    	//若騎乘ship類座騎, 則攻擊交給mount判定
		if (this.host.getIsRiding())
		{
			if (this.host2.getRidingEntity() instanceof BasicEntityMount)
			{
				return false;
			}
		}
    	
    	Entity target = this.host.getEntityTarget();
    	
        if ((target != null && target.isEntityAlive()) &&
        	((this.host.getAttackType(ID.F.AtkType_AirLight) && this.host.getStateFlag(ID.F.UseAirLight) && this.host.hasAmmoLight() && this.host.hasAirLight()) || 
        	 (this.host.getAttackType(ID.F.AtkType_AirHeavy) && this.host.getStateFlag(ID.F.UseAirHeavy) && this.host.hasAmmoHeavy() && this.host.hasAirHeavy())))
        {   
        	this.target = target;
        	return true;
        }
        
        return false;
    }
    
    //init AI parameter, call once every target, DO NOT reset delay time here
    @Override
    public void startExecuting()
    {
        distSq = distX = distY = distZ = 0D;
    }

    //判定是否繼續AI： 有target就繼續, 或者還在移動中則繼續
    @Override
	public boolean continueExecuting()
    {
    	if (host != null)
    	{
    		if (target != null && target.isEntityAlive() && !this.host.getShipNavigate().noPath())
    		{
        		return true;
        	}
        	
            return this.shouldExecute();
    	}
    	
    	return false;
    }

    //重置AI方法, DO NOT reset delay time here
    @Override
	public void resetTask()
    {
        this.target = null;
        
//        if(host != null) {
//        	this.host2.setAttackTarget(null);
//        	this.host.setEntityTarget(null);
//        	this.host.getShipNavigate().clearPathEntity();
//        }
    }

    //進行AI
    @Override
	public void updateTask()
    {
    	if (this.target != null && host != null)
    	{  //for lots of NPE issue-.-
    		boolean onSight = this.host2.getEntitySenses().canSee(this.target);
    		
    		//若不在視線內, 檢查flag
    		if (!onSight)
    		{
    			if (host.getStateFlag(ID.F.OnSightChase))
    			{
	            	this.resetTask();
	            	return;
	            }
    		}
    		
    		//get update attributes
        	if (this.host2.ticksExisted % 64 == 0)
        	{	
        		this.launchDelayMax = (int)(ConfigHandler.baseAttackSpeed[3] / (this.host.getAttrs().getAttackSpeed())) + ConfigHandler.fixedAttackDelay[3];
                this.range = this.host.getAttrs().getAttackRange();
                this.rangeSq = this.range * this.range;
        	}

    		if (this.distSq >= this.rangeSq)
    		{
    			this.distX = this.target.posX - this.host2.posX;
        		this.distY = this.target.posY - this.host2.posY;
        		this.distZ = this.target.posZ - this.host2.posZ;	
        		this.distSq = distX*distX + distY*distY + distZ*distZ;
    	
        		//若目標進入射程, 且目標無障礙物阻擋, 則清空AI移動的目標, 以停止繼續移動      
		        if (distSq < this.rangeSq && onSight && !host.getStateFlag(ID.F.UseMelee))
		        {
		        	this.host.getShipNavigate().clearPathEntity();
		        }
		        else
		        {	//目標移動, 則繼續追擊		        	
		        	if (host2.ticksExisted % 32 == 0)
		        	{
		        		this.host.getShipNavigate().tryMoveToEntityLiving(this.target, 1D);
		        	}
	            }
    		}//end dist > range
	
	        //設定攻擊時, 頭部觀看的角度
    		this.host2.getLookHelper().setLookPosition(this.target.posX, this.target.posY+2D, this.target.posZ, 30.0F, 60.0F);
	         
	        //delay time decr
	        this.launchDelay--;

	        //若只使用單一種彈藥, 則停用型態切換, 只發射同一種飛機
	        if (!this.host.getStateFlag(ID.F.UseAirLight))
	        {
	        	this.launchType = false;
	        }
	        if (!this.host.getStateFlag(ID.F.UseAirHeavy))
	        {
	        	this.launchType = true;
	        }
	        
	        //若attack delay倒數完了, 則開始攻擊
	        if (onSight && this.distSq <= this.rangeSq && this.launchDelay <= 0)
	        {
	        	//使用輕攻擊
	        	if (this.launchType && this.host.hasAmmoLight() && this.host.hasAirLight())
	        	{
	        		this.host.attackEntityWithAircraft(this.target);
		            this.launchDelay = this.launchDelayMax;	//reset delay
	        	}
	        	
	        	//使用重攻擊
	        	if (!this.launchType && this.host.hasAmmoHeavy() && this.host.hasAirHeavy())
	        	{
	        		this.host.attackEntityWithHeavyAircraft(this.target);
	        		this.launchDelay = this.launchDelayMax;	//reset delay
	        	}
	        	
	        	this.launchType = !this.launchType;		//change type
	        }
	        
	        //若超過太久都打不到目標(或是追不到), 則重置目標
	        if (this.launchDelay < -80)
	        {
	        	this.launchDelay = 20;
	        	this.resetTask();
	        	return;
	        }
    	}
    }
    
    
}