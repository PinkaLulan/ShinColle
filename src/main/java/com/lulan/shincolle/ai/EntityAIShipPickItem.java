package com.lulan.shincolle.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipGuardian;
import com.lulan.shincolle.reference.ID;

/**SHIP FLOATING ON WATER AI
 * 若在水中, 且水上一格為空氣, 則會嘗試上浮並站在水面上
 * (entity本體依然在水中)
 */
public class EntityAIShipPickItem extends EntityAIBase {
	
	private IShipEmotion host;
    private BasicEntityShip hostShip;
    private BasicEntityMount hostMount;
    private EntityLivingBase hostLiving;
    private int pickDelay;
    private int pickRange;
    

    public EntityAIShipPickItem(IShipEmotion entity, int basePickRange) {
    	this.host = entity;
    	this.hostLiving = (EntityLivingBase) entity;
    	
    	if(entity instanceof BasicEntityShip) {
    		this.hostShip = (BasicEntityShip) entity;
    	}
    	else if(entity instanceof BasicEntityMount) {
    		this.hostMount = (BasicEntityMount) entity;
    		this.hostShip = (BasicEntityShip) this.hostMount.getHostEntity();
    	}
    	
        this.setMutexBits(7);
    }

    @Override
	public boolean shouldExecute() {
    	//ship類
    	if(hostShip != null) {
    		//騎乘中, 守衛中, 坐下: 禁止AI
    		if(hostShip.isRiding() || isInGuardPosition(hostShip) || this.hostShip.isSitting()) {
    			return false;
    		}
    		
    		//其他情況
    		return true;
    	}
    	//mount類: 檢查mount水深 & host坐下
    	else if(hostMount != null && this.hostShip != null) {
			//守衛中, 坐下: 禁止AI
			if(isInGuardPosition(hostMount) || this.hostShip.isSitting()) {
    			return false;
    		}
			
			return true;
		}
    	//其他類
    	else {
    		return true;
    	}
    }

    @Override
	public void updateTask() {
    	//上浮到指定高度 (本體仍在水中)
    	if(this.host.getShipDepth() > 4D) {
    		this.hostLiving.motionY += 0.025D;
    		return;
    	}
    	
    	if(this.host.getShipDepth() > 1D) {
    		this.hostLiving.motionY += 0.015D;
    		return;
    	}
    	
    	if(this.host.getShipDepth() > 0.7D) {
    		this.hostLiving.motionY += 0.007D;
    		return;
    	}
    	
    	if(this.host.getShipDepth() > 0.47D) {
    		this.hostLiving.motionY += 0.0012D;
    		return;
    	}
    	   	
    }
    
    //check is in guard position
    public boolean isInGuardPosition(IShipGuardian entity) {
    	//若guard中, 則檢查是否達到guard距離
		if(!entity.getStateFlag(ID.F.CanFollow)) {
			float fMin = entity.getStateMinor(ID.M.FollowMin) + ((Entity)entity).width * 0.5F;
			fMin = fMin * fMin;
			
			//若守衛entity, 檢查entity距離
			if(entity.getGuardedEntity() != null) {
				double distSq = ((Entity)entity).getDistanceSqToEntity(entity.getGuardedEntity());
				if(distSq < fMin) return true;
			}
			//若守衛某地點, 則檢查與該點距離
			else if(entity.getStateMinor(ID.M.GuardY) > 0) {
				double distSq = ((Entity)entity).getDistanceSq(entity.getStateMinor(ID.M.GuardX), entity.getStateMinor(ID.M.GuardY), entity.getStateMinor(ID.M.GuardZ));
				if(distSq < fMin && ((Entity)entity).posY >= entity.getStateMinor(ID.M.GuardY)) return true;
			}
		}
		
		return false;
    }
    
    
}

