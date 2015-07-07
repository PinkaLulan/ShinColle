package com.lulan.shincolle.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIShipWatchClosest extends EntityAIWatchClosest {

	public EntityLiving host;
	public float range;
	
    public EntityAIShipWatchClosest(EntityLiving entity, Class target, float range, float rate) {
        super(entity, target, range, rate);
        this.setMutexBits(2);
        this.host = entity;
        this.range = range;
    }
    
    //stop watching if target is riding
    @Override
    public boolean shouldExecute() {
    	EntityPlayer target = this.host.worldObj.getClosestPlayerToEntity(this.host, (double)this.range);
    
    	if(target != null && target.isRiding()) {
    		return false;
    	}
    	else {
    		return super.shouldExecute();
    	}
    }
}
