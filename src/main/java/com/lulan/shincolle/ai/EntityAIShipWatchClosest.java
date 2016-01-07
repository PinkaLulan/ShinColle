package com.lulan.shincolle.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

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
    	EntityPlayer target = this.host.worldObj.getClosestPlayerToEntity(this.host, this.range);
    
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
//    		LogHelper.info("DEBUG : look AI: update");
//			LogHelper.info("DEBUG : mount 1 "+this.host.worldObj.isRemote+" "+((EntityLivingBase)this.host.ridingEntity).rotationYawHead);
//			LogHelper.info("DEBUG : host  1 "+this.host.worldObj.isRemote+" "+this.host.rotationYawHead);
//			LogHelper.info("DEBUG : mount 2 "+this.host.worldObj.isRemote+" "+((EntityLivingBase)this.host.ridingEntity).prevRotationYaw);
//			LogHelper.info("DEBUG : host  2 "+this.host.worldObj.isRemote+" "+this.host.prevRotationYaw);
//			LogHelper.info("DEBUG : mount 3 "+this.host.worldObj.isRemote+" "+((EntityLivingBase)this.host.ridingEntity).rotationYaw);
//			LogHelper.info("DEBUG : host  3 "+this.host.worldObj.isRemote+" "+this.host.rotationYaw);
//			LogHelper.info("DEBUG : mount 4 "+this.host.worldObj.isRemote+" "+((EntityLivingBase)this.host.ridingEntity).renderYawOffset);
//			LogHelper.info("DEBUG : host  4 "+this.host.worldObj.isRemote+" "+this.host.renderYawOffset);
    		if(((BasicEntityMount)this.host.ridingEntity).getShipNavigate().noPath()) {
    			((BasicEntityMount)this.host.ridingEntity).getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, 40F);
//        		((BasicEntityMount)this.host.ridingEntity).rotationYawHead = this.host.rotationYawHead;
//    			((BasicEntityMount)this.host.ridingEntity).prevRotationYaw = this.host.prevRotationYaw;
//    			((BasicEntityMount)this.host.ridingEntity).rotationYaw = this.host.rotationYaw;
//    			((BasicEntityMount)this.host.ridingEntity).renderYawOffset = this.host.renderYawOffset;
    		}
    		
//			if(this.host.ticksExisted % 8 == 0) {
//				//sync rotation
//		  		TargetPoint point = new TargetPoint(host.dimension, host.posX, host.posY, host.posZ, 24D);
//		  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(host, 0, S2CEntitySync.PID.SyncEntity_Rot), point);
//			}
		}
    }
    
    
}
