package com.lulan.shincolle.ai;

import net.minecraft.entity.Entity;

import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.utility.LogHelper;

/** Set attack target by revenge target
 * 
 *  1. revenge target existed
 *  2. get revenge time != old revenge time
 *  3. set revenge target to attack target
 */
public class EntityAIShipRevengeTarget extends EntityAIShipRangeTarget {

	private int oldRevengeTime;

    
    public EntityAIShipRevengeTarget(IShipAttackBase host) {
        super(host, 0.4F, 1, 0, Entity.class);
        this.setMutexBits(1);
        
        this.oldRevengeTime = 0;
    }

    @Override
    public boolean shouldExecute() {
//    	LogHelper.info("DEBUG : revenge target: should exec  "+this.host);
    	//if revenge time is different from old time and revenge target existed
        if(this.oldRevengeTime != this.host.getEntityRevengeTime() && this.host.getEntityRevengeTarget() != null) {
        	return this.targetSelector.isEntityApplicable(this.host.getEntityRevengeTarget());
        }
        
        return false;
    }

    @Override
    public void startExecuting() {
//    	LogHelper.info("DEBUG : revenge target: start exec  "+this.host);
    	//set attack target and time
    	this.host.setEntityTarget(this.host.getEntityRevengeTarget());
    	this.oldRevengeTime = this.host.getEntityRevengeTime();  //update time in AI
    	
    	//clear revenge target
    	this.host.setEntityRevengeTarget(null);
    }

    
}
