package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;

/**SHIP FLOATING ON WATER AI
 * 若在水中, 且水上一格為空氣, 則會嘗試上浮並站在水面上
 * (entity本體依然在水中)
 */
public class EntityAIShipFloating extends EntityAIBase {
	
	private IShipFloating host;
    private BasicEntityShip hostShip;
    private EntityLivingBase hostLiving;
    

    public EntityAIShipFloating(IShipFloating entity) {
    	this.host = entity;
    	this.hostLiving = (EntityLivingBase) entity;
    	
    	if(entity instanceof BasicEntityShip) {
    		this.hostShip = (BasicEntityShip) entity;
    	}
    	
        this.setMutexBits(5);
    }

    public boolean shouldExecute() {
    	if(hostShip != null) {
    		return !this.hostShip.isSitting() && this.hostShip.getStateFlag(ID.F.CanFloatUp);
    	}
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
    		this.hostLiving.motionY += 0.0015D;
    		return;
    	}
    	   	
    }
}
