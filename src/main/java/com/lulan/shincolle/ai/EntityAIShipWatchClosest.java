package com.lulan.shincolle.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;

public class EntityAIShipWatchClosest extends EntityAIWatchClosest {

	private EntityLiving host;
	private IShipEmotion host2;
	private float range;
	
    public EntityAIShipWatchClosest(EntityLiving entity, Class target, float range, float rate) {
        super(entity, target, range, rate);
        this.setMutexBits(8);
        
        this.host = entity;
        this.range = range;
        
        if(host instanceof IShipEmotion) {
        	this.host2 = (IShipEmotion) entity;
        }
        
    }
    
    //stop watching if target is riding
    @Override
    public boolean shouldExecute() {
    	EntityPlayer target = this.host.worldObj.getClosestPlayerToEntity(this.host, this.range);
    
    	if(host2 != null) {
    		if(host2.getStateFlag(ID.F.NoFuel)) {
    			return false;
    		}
    	}
    	
    	if(target != null && target.isRiding()) {
    		return false;
    	}
    	else {
    		return super.shouldExecute();
    	}
    }
    
    @Override
    public void updateTask() {
    	super.updateTask();
    	
    	//sync riding entity look position
    	if(this.host != null && this.host.ridingEntity instanceof BasicEntityMount) {
    		if(((BasicEntityMount)this.host.ridingEntity).getShipNavigate().noPath()) {
    			((BasicEntityMount)this.host.ridingEntity).getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, 40F);
    		}
    		
//			if(this.host.ticksExisted % 8 == 0) {
//				//sync rotation
//		  		TargetPoint point = new TargetPoint(host.dimension, host.posX, host.posY, host.posZ, 24D);
//		  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(host, 0, S2CEntitySync.PID.SyncEntity_Rot), point);
//			}
		}
    }
    
    
}
