package com.lulan.shincolle.utility;

import java.util.Comparator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.proxy.ServerProxy;
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
        
        @Override
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
    	private Entity host;
    	
    	public Selector(Entity host) {
    		this.host = host;
    	}

		@Override
		public boolean isEntityApplicable(Entity target2) {
			//skip ship and special entity
			if(target2 instanceof BasicEntityShip || target2 instanceof BasicEntityAirplane ||
			   target2 instanceof BasicEntityMount || target2 instanceof EntityAbyssMissile) {
				return false;
			}
			
        	if((target2 instanceof EntityMob || target2 instanceof EntitySlime) &&
        	   target2.isEntityAlive() && !target2.isInvisible()) {
        		//若host有設定必須on sight, 則檢查on sight
        		if(host instanceof BasicEntityShip) {
        			if(((BasicEntityShip)host).getStateFlag(ID.F.OnSightChase)) {
            			if(!((BasicEntityShip)host).getEntitySenses().canSee(target2)) {
            				return false;
            			}
            		}
        		}//end ship host
        		//非ship類host
        		else if(host instanceof EntityLiving) {
        			if(!((EntityLiving)host).getEntitySenses().canSee(target2)) {
        				return false;
        			}
        		}//end other host
        		
        		return true;
        	}//end is target class
        	else {  //custom class for mod interact
        		if(target2 != null && target2.isEntityAlive() && !target2.isInvisible() &&
        		   host instanceof IShipAttackBase) {
        			int pid = ((IShipAttackBase) host).getPlayerUID();
        			List<String> tarList = ServerProxy.getPlayerTargetClassList(pid);
        			String tarClass = target2.getClass().getSimpleName();
        			if(tarList != null) {
        				for(String s : tarList) {
            				if(s.equals(tarClass)) {  //target class is in list
            					//if tameable entity, check owner
            					if(target2 instanceof EntityTameable) {
            						if(!EntityHelper.checkSameOwner(host, target2)) {
            							return true;
            						}
            					}
            					else {
            						return true;
            					}
            				}
            			}
        			}
        		}
        	}
        	
        	return false;
        }
    }
    
    /**PVP TARGET SELECTOR
     * select target and pvp target by class
     */
    public static class PVPSelector extends Selector {
    	private Entity host;
    	
    	public PVPSelector(Entity host) {
    		super(host);
    	}
    	
    	@Override
		public boolean isEntityApplicable(Entity target2) {
    		if(!super.isEntityApplicable(target2)) {
    			//is ship, mount or airplane
    			if(target2 instanceof BasicEntityShip || target2 instanceof BasicEntityAirplane ||
				   target2 instanceof BasicEntityMount) {
    				//check is banned team
    				if(EntityHelper.checkIsBanned(host, target2) && target2.isEntityAlive() &&
    				   !target2.isInvisible()) {
    					//check should onSight?
    					if(host instanceof BasicEntityShip) {
    	        			if(((BasicEntityShip)host).getStateFlag(ID.F.OnSightChase)) {
    	            			if(((BasicEntityShip)host).getEntitySenses().canSee(target2)) {
    	            				return true;
    	            			}
    	            			else {
    	            				return false;
    	            			}
    	            		}
    	        		}//end ship host
    	        		//非ship類host
    	        		else if(host instanceof EntityLiving) {
    	        			if(((EntityLiving)host).getEntitySenses().canSee(target2)) {
    	        				return true;
    	        			}
    	        			else {
	            				return false;
	            			}
    	        		}//end other host
    					
    					return true;
    				}//is banned team
				}//is ship, mount or airplane
    		}//filter by normal target
        	
        	return false;
        }
    }
    
    /**TARGET SELECTOR 2
     * select target by class for hostile mob
     */
    public static class SelectorForHostile implements IEntitySelector {
    	private final Entity host;
    	
    	public SelectorForHostile(Entity host) {
    		this.host = host;
    	}
    	
    	@Override
		public boolean isEntityApplicable(Entity target2) {
        	if((target2 instanceof EntityPlayer || target2 instanceof BasicEntityShip ||
        	   target2 instanceof BasicEntityAirplane || target2 instanceof BasicEntityMount) && 
        	   target2.isEntityAlive() && !target2.isInvisible()) {
        		
        		//do not attack OP player
        		if(target2 instanceof EntityPlayer) {
        			return !EntityHelper.checkOP((EntityPlayer) target2);
        		}
        		
        		return true;
        	}
        	return false;
        }

    }

}
