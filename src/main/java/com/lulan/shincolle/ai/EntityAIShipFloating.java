package com.lulan.shincolle.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.reference.ID;

/**SHIP FLOATING ON WATER AI
 * 若在水中, 且水上一格為空氣, 則會嘗試上浮並站在水面上
 * (entity本體依然在水中)
 */
public class EntityAIShipFloating extends EntityAIBase {
	
	private IShipFloating host;
    private BasicEntityShip hostShip;
    private BasicEntityMount hostMount;
    private EntityLivingBase hostLiving;
    

    public EntityAIShipFloating(IShipFloating entity) {
    	this.host = entity;
    	this.hostLiving = (EntityLivingBase) entity;
    	
    	if(entity instanceof BasicEntityShip) {
    		this.hostShip = (BasicEntityShip) entity;
    	}
    	else if(entity instanceof BasicEntityMount) {
    		this.hostMount = (BasicEntityMount) entity;
    	}
    	
        this.setMutexBits(5);
    }

    public boolean shouldExecute() {
    	//ship類: 檢查host坐下
    	if(hostShip != null) {
    		return !this.hostShip.isSitting() && this.hostShip.getStateFlag(ID.F.CanFloatUp);
    	}
    	//mount類: 檢查mount水深 & host坐下
    	else if(hostMount != null && hostMount.getHostEntity() != null) {
			this.hostShip = (BasicEntityShip) hostMount.getHostEntity();
			
			return !this.hostShip.isSitting() && hostMount.getShipDepth() > 0.47D;
		}
    	//其他類
    	else {
    		return host.getShipDepth() > 0.47D;
    	}
    }

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
}
