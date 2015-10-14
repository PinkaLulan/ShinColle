package com.lulan.shincolle.utility;

import java.util.Comparator;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

/** some targeting class/method
 */
public class TargetHelper {
	
	
	public TargetHelper() {}
	
	/**SORTER CLASS
	 * sort by distance (item 0 = nearest)
     */
    public static class Sorter implements Comparator {
        private final Entity targetEntity;

        public Sorter(Entity entity) {
            this.targetEntity = entity;
        }
        
        public int compare(Object target1, Object target2) {
            return this.compare((Entity)target1, (Entity)target2);
        }

        //負值會排在list前面, 值越大越後面, list(0)會是距離最近的目標
        public int compare(Entity target1, Entity target2) {
            double d0 = this.targetEntity.getDistanceSqToEntity(target1);
            double d1 = this.targetEntity.getDistanceSqToEntity(target2);
            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }       
    }//end sorter class
    
    /**TARGET SELECTOR
     * select target by class
     */
    public static class Selector implements IEntitySelector {
    	private final EntityLiving host;
    	
    	public Selector(EntityLiving host) {
    		this.host = host;
    	}
    	
    	public boolean isEntityApplicable(Entity target2) {
        	if((target2 instanceof EntityMob || target2 instanceof EntitySlime ||
        	   target2 instanceof EntityBat || target2 instanceof EntityDragon || 
        	   target2 instanceof EntityDragonPart ||
        	   target2 instanceof EntityFlying || target2 instanceof EntityWaterMob) &&
        	   target2.isEntityAlive() && !target2.isInvisible()) {
        		
        		//若host有設定必須on sight, 則檢查on sight
        		if(host instanceof BasicEntityShip) {
        			if(((BasicEntityShip)host).getStateFlag(ID.F.OnSightChase)) {
            			if(!host.getEntitySenses().canSee(target2)) {
            				return false;
            			}
            		}
        		}//end ship host
        		//非ship類host
        		else {
        			if(!host.getEntitySenses().canSee(target2)) {
        				return false;
        			}
        		}//end other host
        		
        		return true;
        	}//end is target class
        	
        	return false;
        }
    }
    
    /**TARGET SELECTOR 2
     * select target by class for hostile mob
     */
    public static class SelectorForHostile implements IEntitySelector {
    	private final EntityLiving host;
    	
    	public SelectorForHostile(EntityLiving host) {
    		this.host = host;
    	}
    	
    	public boolean isEntityApplicable(Entity target2) {
        	if((target2 instanceof EntityPlayer || target2 instanceof BasicEntityShip ||
        	   target2 instanceof BasicEntityAirplane || target2 instanceof BasicEntityMount) && 
        	   target2.isEntityAlive() && !target2.isInvisible()) {
        		return true;
        	}
        	return false;
        }

    }

}
