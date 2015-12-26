package com.lulan.shincolle.ai;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayerMP;

import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TargetHelper;


/**GET TARGET WITHIN SPECIFIC RANGE
 * mode: 
 * 0:target between range1 and range2 only (只打外圈)
 * 1:target < range1 => < range2 (先打內圈, 再打外圈)
 * 2:target between range1 and range2 => < range1 (先打外圈, 再打內圈)
 * 
 * @parm host, range proportion, mode
 */
public class EntityAIShipRangeTarget extends EntityAIBase {
	
    protected Class targetClass;
    protected TargetHelper.Sorter targetSorter;
    protected IEntitySelector targetSelector;
    protected IShipAttackBase host;
    protected EntityLiving host2;
    protected Entity targetEntity;
    protected int range1;
    protected int range2;
    protected int targetMode;
    protected float rangeMod;
    

    //將maxRange 乘上一個比例當作range1
    public EntityAIShipRangeTarget(IShipAttackBase host, float rangeMod, int mode) {
    	this.setMutexBits(1);
    	this.host = host;
    	this.host2 = (EntityLiving) host;
    	this.targetClass = Entity.class;
        this.targetSorter = new TargetHelper.Sorter(host2);
        
        if(host instanceof BasicEntityShipHostile) {
        	this.targetSelector = new TargetHelper.SelectorForHostile(host2);
        }
        else {
        	this.targetSelector = new TargetHelper.Selector(host2);
        }

        //範圍指定
        this.rangeMod = rangeMod;
        this.range2 = (int)this.host.getAttackRange();
        this.range1 = (int)(this.rangeMod * this.range2);
        this.targetMode = mode;
        
        //檢查範圍, 使range2 > range1 > 1
        if(this.range1 < 1) {
        	this.range1 = 1;
        }
        if(this.range2 <= this.range1) {
        	this.range2 = this.range1 + 1;
        }
 
    }

    @Override
    public boolean shouldExecute() {
    	//update range
    	if(this.host != null) {
//    		LogHelper.info("DEBUG : target AI: should exec: "+host);
    		
    		//check if not sitting and every 8 ticks
        	if(this.host.getIsSitting() || this.host.getTickExisted() % 8 != 0) return false;
    		
    		this.range2 = (int)this.host.getAttackRange();
            this.range1 = (int)(this.rangeMod * this.range2);
            //檢查範圍, 使range2 > range1 > 1
            if(this.range1 < 1) {
            	this.range1 = 1;
            }
            if(this.range2 <= this.range1) {
            	this.range2 = this.range1 + 1;
            }

        	//entity list < range1
            List list1 = this.host2.worldObj.selectEntitiesWithinAABB(this.targetClass, 
            		this.host2.boundingBox.expand(this.range1, this.range1 * 0.6D, this.range1), this.targetSelector);
            //entity list < range2
            List list2 = this.host2.worldObj.selectEntitiesWithinAABB(this.targetClass, 
            		this.host2.boundingBox.expand(this.range2, this.range2 * 0.6D, this.range2), this.targetSelector);
            
            //對目標做distance sort, id=0為nearest target
            Collections.sort(list1, this.targetSorter);
            Collections.sort(list2, this.targetSorter);
            
            switch(this.targetMode) {
            case 0:  //mode 0:target between range1 and range2 only
            	list2.removeAll(list1);	 //list2排除range1以內的目標
            	if(list2.isEmpty()) {
                    return false;
                }
                else {
                	this.targetEntity = (Entity) list2.get(0);
                	if(list2.size() > 2) {
                		this.targetEntity = (Entity) list2.get(this.host2.worldObj.rand.nextInt(3));
                	}             
                    return true;
                }
    		case 1:  //mode 1:target < range1 => < range2
    			if(list1.isEmpty()) {	//range1以內沒有目標, 則找range2
    				if(list2.isEmpty()) {
    	                return false;
    	            }
    				else {				//range2以內有目標
    					this.targetEntity = (Entity)list2.get(0);
    					if(list2.size() > 2) {
    	            		this.targetEntity = (Entity)list2.get(this.host2.worldObj.rand.nextInt(3));
    	            	}
    	                return true;
    				}
                }
                else {					//range1以內有目標
                    this.targetEntity = (Entity)list1.get(0);
                    if(list1.size() > 2) {
                		this.targetEntity = (Entity)list1.get(this.host2.worldObj.rand.nextInt(3));
                	}
                    return true;
                }
            case 2:  //mode 2:target between range1 and range2 => < range1
            	list2.removeAll(list1);	 //list2排除range1以內的目標
            	if(list2.isEmpty()) {	 //range2~range1中沒有目標, 改找range1以內
            		if(list1.isEmpty()) {
                        return false;
                    }
            		else {				 //range1以內有目標
            			this.targetEntity = (Entity)list1.get(0);
            			if(list1.size() > 2) {
                    		this.targetEntity = (Entity)list1.get(this.host2.worldObj.rand.nextInt(3));
                    	}
                        return true;
            		}
                }
                else {					 //range2以內有目標
                    this.targetEntity = (Entity)list2.get(0);
                    if(list2.size() > 2) {
                		this.targetEntity = (Entity)list2.get(this.host2.worldObj.rand.nextInt(3));
                	}
                    return true;
                }
            }
    	}
    	
    	return false;
    }
    
    @Override
    public void resetTask() {
//    	LogHelper.info("DEBUG : target AI: reset "+this.host);
    }

    @Override
    public void startExecuting() {
//    	LogHelper.info("DEBUG : target AI: "+this.host+"   "+this.targetEntity);
        this.host.setEntityTarget(this.targetEntity);
    }
    
    @Override
    public boolean continueExecuting() {
//    	LogHelper.info("DEBUG : target AI: cont exec: "+this.host);
        Entity target = this.host.getEntityTarget();

        if(target == null || !target.isEntityAlive()) {
            return false;
        }
        else {
            double d0 = this.range2 * this.range2;

            if(this.host2.getDistanceSqToEntity(target) > d0) {  //超出攻擊距離
                return false;
            }
            //若target是玩家, 檢查是否在OP mode
            return !(target instanceof EntityPlayerMP) || !((EntityPlayerMP)target).theItemInWorldManager.isCreative();
        }
    }

  
}
