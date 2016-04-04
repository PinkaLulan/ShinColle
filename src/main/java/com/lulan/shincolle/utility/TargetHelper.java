package com.lulan.shincolle.utility;

import java.util.Comparator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;

import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipInvisible;
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
    	protected Entity host;
    	protected boolean isPVP;
    	protected boolean isAA;
    	protected boolean isASM;
    	
    	public Selector(Entity host) {
    		this.host = host;
    		
    		//PVP mode for ship
    		if(host instanceof BasicEntityShip) {
    			this.isPVP = ((BasicEntityShip) host).getStateFlag(ID.F.PVPFirst);
    			this.isAA = ((BasicEntityShip) host).getStateFlag(ID.F.AntiAir);
    			this.isASM = ((BasicEntityShip) host).getStateFlag(ID.F.AntiSS);
    		}
    		else {
    			this.isPVP = false;
    		}
    	}

		@Override
		public boolean isEntityApplicable(Entity target2) {
			//not self
			if(this.host == null || host.equals(target2)) {
				return false;
			}
			
			//check unattackable list
    		if(checkUnattackTarget(target2)) return false;
			
			//ship host should check onSight
			if(host instanceof BasicEntityShip) {
    			if(((BasicEntityShip)host).getStateFlag(ID.F.OnSightChase)) {
        			if(!((BasicEntityShip)host).getEntitySenses().canSee(target2)) {
        				return false;
        			}
        		}
    		}
			//非ship類host check onSight
			else if(host instanceof EntityLiving) {
    			if(!((EntityLiving)host).getEntitySenses().canSee(target2)) {
    				return false;
    			}
    		}
			
			if(target2.isEntityAlive() && !target2.isInvisible()) {
				//anti air target, no pvp checking
				if(target2 instanceof BasicEntityAirplane || target2 instanceof EntityAbyssMissile) {
					if(isAA && EntityHelper.checkIsBanned(host, target2)) {
						return true;
					}
					else {
						return false;
					}
				}
				
				//anti SS target
				if(target2 instanceof IShipInvisible) {
					if(isASM && EntityHelper.checkIsBanned(host, target2)) {
						return true;
					}
					else {
						return false;
					}
				}
				
				//check pvp
				if(this.isPVP && (target2 instanceof BasicEntityShip || target2 instanceof BasicEntityMount)) {
					//attack hostile team
					if(EntityHelper.checkIsBanned(host, target2)) {
						return true;
					}
				}
				
				//check mob
	        	if(target2 instanceof EntityMob || target2 instanceof EntitySlime) {
	        		return true;
	        	}
	        	
	        	//check custom target (including pet check)
	        	if(checkAttackTargetClass(host, target2)) {
	        		return true;
	        	}
			}
        	
        	return false;
        }
    }
    
    /** REVENGE TARGET SELECTOR */
    public static class RevengeSelector implements IEntitySelector {
    	protected Entity host;
    	
    	public RevengeSelector(Entity host) {
    		this.host = host;
    	}

		@Override
		public boolean isEntityApplicable(Entity target2) {
			//not self
			if(this.host == null || host.equals(target2)) {
				return false;
			}
			
			//check unattackable list
    		if(checkUnattackTarget(target2)) return false;
			
    		if(target2.isEntityAlive() && !target2.isInvisible()) {
    			//check ship target
    			if(target2 instanceof BasicEntityShip || target2 instanceof BasicEntityAirplane ||
    			   target2 instanceof BasicEntityMount || target2 instanceof EntityAbyssMissile) {
    				//do not attack ally
    				if(EntityHelper.checkIsAlly(host, target2)) {
    					return false;
    				}
    				
    				return true;
    			}
    			
    			//check mob target
            	if(target2 instanceof EntityMob || target2 instanceof EntitySlime) {
            		return true;
            	}

            	//check faction
        		if(!EntityHelper.checkSameOwner(host, target2)) {
    				return true;
    			}
    		}
        	
        	return false;
        }
    }
    
    /** TARGET SELECTOR FOR HOSTILE
     *  select target by class for hostile mob
     */
    public static class SelectorForHostile implements IEntitySelector {
    	private final Entity host;
    	
    	public SelectorForHostile(Entity host) {
    		this.host = host;
    	}
    	
    	@Override
		public boolean isEntityApplicable(Entity target2) {
    		//check unattackable list
    		if(checkUnattackTarget(target2)) return false;

			if(target2.isEntityAlive() && !target2.isInvisible()) {
				//do not attack hostile ship
        		if(target2 instanceof BasicEntityShipHostile) {
        			return false;
        		}
				
				//do not attack creative mode player
        		if(target2 instanceof EntityPlayer) {
        			return !((EntityPlayer)target2).capabilities.isCreativeMode;
        		}
        		
        		//attack ship
				if(target2 instanceof BasicEntityShip || target2 instanceof BasicEntityMount) {
	    			return true;
	        	}
				
				//check aircraft
    			if(target2 instanceof BasicEntityAirplane || target2 instanceof EntityAbyssMissile) {
    				//do not attack ally
    				if(EntityHelper.checkSameOwner(host, target2)) {
    					return false;
    				}
    				
    				return true;
    			}
			}
    		
        	return false;
        }

    }
    
    /** REVENGE SELECTOR FOR HOSTILE
     *  select target by class for hostile mob
     */
    public static class RevengeSelectorForHostile implements IEntitySelector {
    	private final Entity host;
    	
    	public RevengeSelectorForHostile(Entity host) {
    		this.host = host;
    	}
    	
    	@Override
		public boolean isEntityApplicable(Entity target2) {
    		//check unattackable list
    		if(checkUnattackTarget(target2)) return false;
			
        	if(target2.isEntityAlive() && !target2.isInvisible()) {
        		//do not attack hostile ship
        		if(target2 instanceof BasicEntityShipHostile) {
        			return false;
        		}
        		
        		//do not attack creative mode player
        		if(target2 instanceof EntityPlayer) {
        			return !((EntityPlayer)target2).capabilities.isCreativeMode;
        		}
        		
        		//attack ship
				if(target2 instanceof BasicEntityShip || target2 instanceof BasicEntityMount) {
	    			return true;
	        	}
				
				//check aircraft
    			if(target2 instanceof BasicEntityAirplane || target2 instanceof EntityAbyssMissile) {
    				//do not attack ally
    				if(EntityHelper.checkSameOwner(host, target2)) {
    					return false;
    				}
    				
    				return true;
    			}

        		//check faction
        		if(!EntityHelper.checkSameOwner(host, target2)) {
    				return true;
    			}
        	}
        	
        	return false;
        }
    }
    
    /** check target is unattackable, SERVER SIDE only */
    public static boolean checkUnattackTarget(Entity target) {
		List<String> unatklist = ServerProxy.getUnattackableTargetClassList();
		String tarClass = target.getClass().getSimpleName();
		
		if(unatklist != null) {
			for(String s : unatklist) {
				if(s.equals(tarClass)) {  //target class is in list
					return true;
				}
			}
		}
		
		return false;
    }
    
    /** check target is in attack target class list */
    public static boolean checkAttackTargetClass(Entity host, Entity target) {
    	if(target != null && host instanceof IShipAttackBase) {
 			int pid = ((IShipAttackBase) host).getPlayerUID();
 			List<String> tarList = ServerProxy.getPlayerTargetClassList(pid);
 			String tarClass = target.getClass().getSimpleName();
 			
 			if(tarList != null) {
 				for(String s : tarList) {
     				if(s.equals(tarClass)) {  //target class is in list
     					//if tameable entity, check owner
     					if(target instanceof EntityTameable) {
     						if(!EntityHelper.checkSameOwner(host, target)) {
     							return true;
     						}
     					}
     					
     					return true;
     				}
     			}
 			}
 		}
    	
    	return false;
    }
    
    
    /** update target */
	public static void updateTarget(IShipAttackBase host) {
		//clear attack target
		if(host.getEntityTarget() != null) {
			//clear dead target
			if(!host.getEntityTarget().isEntityAlive()) {
				host.setEntityTarget(null);
			}
			//clear target if target is friendly
			else if(EntityHelper.checkSameOwner((Entity)host, host.getEntityTarget())) {
				host.setEntityTarget(null);
			}
		}

		//clear revenge target
		if(host.getEntityRevengeTarget() != null) {
            if(!host.getEntityRevengeTarget().isEntityAlive()) {
            	host.setEntityRevengeTarget(null);
            }
            //clear target after 200 ticks
            else if (host.getTickExisted() - host.getEntityRevengeTime() > 200) {
            	host.setEntityRevengeTarget(null);
            }
        }
		
		//clear vanilla attack target
		if(host instanceof BasicEntityShipHostile) {
			EntityLivingBase gettar = ((BasicEntityShipHostile) host).getAttackTarget();
			
			if(gettar != null) {
				if(!gettar.isEntityAlive()) {
					((BasicEntityShipHostile) host).setAttackTarget(null);
				}
				else if(EntityHelper.checkSameOwner((Entity)host, gettar)) {
					((BasicEntityShipHostile) host).setAttackTarget(null);
				}
			}
		}
		
		//clear invisible target every 64 ticks
		if(host.getTickExisted() % 64 == 0) {
			if(host.getEntityTarget() != null && host.getEntityTarget().isInvisible()) {
				host.setEntityTarget(null);
			}
		}
	}
	
	/** set ship's revenge target around player within X blocks */
	public static void setRevengeTargetAroundPlayer(EntityPlayer player, double dist, Entity target) {
		int pid = EntityHelper.getPlayerUID(player);
		
		if(pid > 0 && target != null) {
			//get ship
			int getpid = 0;
			List<BasicEntityShip> list1 = player.worldObj.getEntitiesWithinAABB(BasicEntityShip.class,
					player.boundingBox.expand(dist, dist, dist));
			
			if(list1 != null && !list1.isEmpty()) {
				for(BasicEntityShip ship : list1) {
					getpid = ship.getPlayerUID();
					
					//check same owner
					if(!ship.equals(target) && getpid == pid) {
						ship.setEntityRevengeTarget(target);
						ship.setEntityRevengeTime();
					}
				}
			}
		}
	}
	
	/** set mob ship's revenge target within X blocks */
	public static void setRevengeTargetAroundHostileShip(BasicEntityShipHostile host, double dist, Entity target) {
		if(host != null && target != null) {
			//get hostile ship
			int getpid = 0;
			List<BasicEntityShipHostile> list1 = host.worldObj.getEntitiesWithinAABB(BasicEntityShipHostile.class,
					host.boundingBox.expand(dist, dist, dist));
			
			if(list1 != null && !list1.isEmpty()) {
				for(BasicEntityShipHostile ship : list1) {
					ship.setEntityRevengeTarget(target);
					ship.setEntityRevengeTime();
				}
			}
		}
	}
	
	

}
