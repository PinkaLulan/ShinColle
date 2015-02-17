package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;

/**SHIP FLOATING ON WATER AI
 * 若在水中, 且水上一格為空氣, 則會嘗試上浮並站在水面上
 * (entity本體依然在水中)
 */
public class EntityAIShipFloating extends EntityAIBase {
	
    private BasicEntityShip theEntity;

    public EntityAIShipFloating(BasicEntityShip entity) {
        this.theEntity = entity;
        this.setMutexBits(6);
        entity.getNavigator().setCanSwim(true);
    }

    public boolean shouldExecute() {
//    	LogHelper.info("DEBUG : floating cond? "+this.theEntity.getEntityFlag(AttrID.F_CanFloatUp));
        return !this.theEntity.isSitting() && this.theEntity.getEntityFlag(AttrID.F_CanFloatUp);
    }

    public void updateTask() {
    	//上浮到指定高度 (本體仍在水中)
    	if(this.theEntity.getShipDepth() > 4D) {
    		this.theEntity.motionY += 0.025D;
    		return;
    	}
    	
    	if(this.theEntity.getShipDepth() > 1D) {
    		this.theEntity.motionY += 0.015D;
//    		LogHelper.info("DEBUG : floating "+this.theEntity+" "+this.theEntity.getShipDepth());
    		return;
    	}
    	
    	if(this.theEntity.getShipDepth() > 0.7D) {
    		this.theEntity.motionY += 0.01D;
    		return;
    	}
    	
    	if(this.theEntity.getShipDepth() > 0.45D) {
    		this.theEntity.motionY += 0.003D;
    		return;
    	}
    	   	
    }
}
