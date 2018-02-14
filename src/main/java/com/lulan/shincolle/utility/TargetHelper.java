package com.lulan.shincolle.utility;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.lulan.shincolle.entity.BasicEntityAirplane;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.BasicEntitySummon;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;

/** some targeting class/method
 * 
 *  1.9.4:
 *  changed IEntitySelector to Predicate
 */
public class TargetHelper
{
	
	
	public TargetHelper() {}
	
	/**SORTER CLASS
	 * sort by distance (item 0 = nearest)
     */
    public static class Sorter implements Comparator<Entity>
    {
        private final Entity targetEntity;

        public Sorter(Entity entity)
        {
            this.targetEntity = entity;
        }
        
        //負值會排在list前面, 值越大越後面, list(0)會是距離最近的目標
		@Override
        public int compare(Entity target1, Entity target2)
        {
            double d0 = this.targetEntity.getDistanceSqToEntity(target1);
            double d1 = this.targetEntity.getDistanceSqToEntity(target2);
            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }       
    }//end sorter class
    
    /**TARGET SELECTOR
     * select target by class
     */
    public static class Selector implements Predicate<Entity>
    {
    	protected Entity host;
    	protected boolean isPVP;
    	protected boolean isAA;
    	protected boolean isASM;
    	
    	public Selector(Entity host)
    	{
    		this.host = host;
    	}
    	
		@Override
		public boolean apply(Entity target2)
		{
    		//update flag
    		if (host instanceof BasicEntityShip)
    		{
    			this.isPVP = ((BasicEntityShip) host).getStateFlag(ID.F.PVPFirst);
    			this.isAA = ((BasicEntityShip) host).getStateFlag(ID.F.AntiAir);
    			this.isASM = ((BasicEntityShip) host).getStateFlag(ID.F.AntiSS);
    		}
    		else
    		{
    			this.isPVP = false;
    		}
    		
			//null check
			if (target2 == null || !target2.isEntityAlive() || this.host == null || host.equals(target2))
			{
				return false;
			}
			
            //is player 
            if (target2 instanceof EntityPlayer)
            {
            	//不打無敵目標, ex: OP/觀察者
            	if (((EntityPlayer) target2).capabilities.disableDamage)
            	{
            		return false;
            	}
            	
                //check team
            	switch (ConfigHandler.shipAttackPlayer)
            	{
            	case 0:  //ship dont attack player automatically, do nothing
        		break;
            	case 1:  //ship attack hostile player
            		if (TeamHelper.checkIsBanned(host, target2))
            		{
            			return true;
            		}
        		break;
            	case 2:  //ship attack hostile and neutral player
            		if (!TeamHelper.checkIsAlly(host, target2))
            		{
            			return true;
            		}
        		break;
            	case 3:  //ship attkac all player except owner
            		if (!TeamHelper.checkSameOwner(host, target2))
            		{
            			return true;
            		}
        		break;
            	}
            }
			
            //dont attack these entity
        	if (isEntityInvulnerable(target2)) return false;
    		
    		//check invisible
    		if (target2.isInvisible())
    		{
    			//if host is ship
    			if (host instanceof BasicEntityShip)
    			{
    				//if ship have no flare or searchlight, return false
    				if (((BasicEntityShip) host).getStateMinor(ID.M.LevelFlare) < 1 &&
    					((BasicEntityShip) host).getStateMinor(ID.M.LevelSearchlight) < 1)
					{
						return false;
					}
    			}
    			//if host is summons
    			else if (host instanceof IShipOwner &&
    					 ((IShipOwner)host).getHostEntity() instanceof BasicEntityShip)
    			{
    				//get host's host
    				BasicEntityShip host2 = (BasicEntityShip) ((IShipOwner)host).getHostEntity();
    			
    				//if host's host have no flare or searchlight, return false
    				if (host2.getStateMinor(ID.M.LevelFlare) < 1 &&
    					host2.getStateMinor(ID.M.LevelSearchlight) < 1)
					{
						return false;
					}
    			}
    		}
			
			//ship host should check onSight
			if (host instanceof BasicEntityShip)
			{
    			if (((BasicEntityShip)host).getStateFlag(ID.F.OnSightChase))
    			{
        			if (!((BasicEntityShip)host).getEntitySenses().canSee(target2))
        			{
        				return false;
        			}
        		}
    		}
			//非ship類host check onSight
			else if (host instanceof EntityLiving)
			{
    			if (!((EntityLiving)host).getEntitySenses().canSee(target2))
    			{
    				return false;
    			}
    		}
			
			//anti air target, no pvp checking
			if (target2 instanceof BasicEntityAirplane || target2 instanceof EntityAbyssMissile)
			{
				if (isAA && TeamHelper.checkIsBanned(host, target2))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			
			//anti SS target
			if (target2 instanceof IShipInvisible)
			{
				if (isASM && TeamHelper.checkIsBanned(host, target2))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			
			//check pvp
			if (this.isPVP && (target2 instanceof BasicEntityShip ||
							   target2 instanceof BasicEntityMount))
			{
				//attack hostile team
				if (TeamHelper.checkIsBanned(host, target2))
				{
					return true;
				}
			}
			
			//check mob
        	if(target2 instanceof EntityMob || target2 instanceof EntitySlime)
        	{
        		return true;
        	}
        	
        	//check custom target (including pet check)
        	if (checkAttackTargetList(host, target2))
        	{
        		return true;
        	}
        	
        	return false;
        }

    }
    
    /** REVENGE TARGET SELECTOR */
    public static class RevengeSelector implements Predicate<Entity>
    {
    	protected Entity host;
    	
    	public RevengeSelector(Entity host)
    	{
    		this.host = host;
    	}

		@Override
		public boolean apply(Entity target2)
		{
			//null check
			if (target2 == null || !target2.isEntityAlive() || this.host == null || host.equals(target2))
			{
				return false;
			}
			
            //若target是玩家, 則不打無敵目標, ex: OP/觀察者
            if (target2 instanceof EntityPlayer && ((EntityPlayer) target2).capabilities.disableDamage)
            {
                return false;
            }
			
            //dont attack these entity
        	if (isEntityInvulnerable(target2)) return false;
    		
    		//check invisible
    		if (target2.isInvisible())
    		{
    			//if host is ship
    			if (host instanceof BasicEntityShip)
    			{
    				//if ship have no flare or searchlight, return false
    				if (((BasicEntityShip) host).getStateMinor(ID.M.LevelFlare) < 1 &&
    					((BasicEntityShip) host).getStateMinor(ID.M.LevelSearchlight) < 1)
					{
						return false;
					}
    			}
    			//if host is summons
    			else if (host instanceof IShipOwner &&
    					 ((IShipOwner)host).getHostEntity() instanceof BasicEntityShip)
    			{
    				//get host's host
    				BasicEntityShip host2 = (BasicEntityShip) ((IShipOwner)host).getHostEntity();
    			
    				//if host's host have no flare or searchlight, return false
    				if (host2.getStateMinor(ID.M.LevelFlare) < 1 &&
    					host2.getStateMinor(ID.M.LevelSearchlight) < 1)
					{
						return false;
					}
    			}
    		}
    		
			//target is ship or summons, check ally state
			if (target2 instanceof IShipOwner)
			{
				//do not attack ally
				if (TeamHelper.checkIsAlly(host, target2))
				{
					return false;
				}
				
				return true;
			}
			
        	//check faction
    		if (!TeamHelper.checkSameOwner(host, target2))
    		{
				return true;
			}

        	return false;
        }
    }
    
    /** TARGET SELECTOR FOR HOSTILE
     *  select target by class for hostile mob
     */
    public static class SelectorForHostile implements Predicate<Entity>
    {
    	private final Entity host;
    	
    	public SelectorForHostile(Entity host)
    	{
    		this.host = host;
    	}
    	
    	@Override
		public boolean apply(Entity target2)
    	{
			//null check
			if (target2 == null || !target2.isEntityAlive() || this.host == null || host.equals(target2))
			{
				return false;
			}
			
            //if target player
            if (target2 instanceof EntityPlayer)
            {
            	//dont attack OP/OB
            	if (((EntityPlayer) target2).capabilities.disableDamage) return false;
            	else if (ConfigHandler.mobAttackPlayer) return true;
            	else return false;
            }
    		
            //dont attack these entity
        	if (isEntityInvulnerable(target2)) return false;

			if (!target2.isInvisible())
			{
				//do not attack hostile ship
        		if (target2 instanceof BasicEntityShipHostile)
        		{
        			return false;
        		}
        		
        		//attack ship
				if (target2 instanceof BasicEntityShip || target2 instanceof BasicEntityMount)
				{
	    			return true;
	        	}
				
				//if target is ship or summons, check owner
    			if (target2 instanceof IShipOwner)
    			{
    				//do not attack ally
    				if (TeamHelper.checkSameOwner(host, target2))
    				{
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
    public static class RevengeSelectorForHostile implements Predicate<Entity>
    {
    	private final Entity host;
    	
    	public RevengeSelectorForHostile(Entity host)
    	{
    		this.host = host;
    	}
    	
    	@Override
		public boolean apply(Entity target2)
    	{
			//null check
			if (target2 == null || this.host == null || host.equals(target2))
			{
				return false;
			}
			
            //if target player
            if (target2 instanceof EntityPlayer)
            {
            	//dont attack OP/OB
            	if (((EntityPlayer) target2).capabilities.disableDamage) return false;
            	return true;
            }
    		
            //dont attack these entity
        	if (isEntityInvulnerable(target2)) return false;
			
        	if (target2.isEntityAlive() && !target2.isInvisible())
        	{
        		//do not attack hostile ship
        		if (target2 instanceof BasicEntityShipHostile)
        		{
        			return false;
        		}
        		
        		//attack ship
				if (target2 instanceof BasicEntityShip)
				{
	    			return true;
	        	}
				
				//if target is summons, check owner
    			if (target2 instanceof IShipOwner)
    			{
    				//do not attack ally
    				if (TeamHelper.checkSameOwner(host, target2))
    				{
    					return false;
    				}
    				
    				return true;
    			}
    			
        		//check faction
        		if (!TeamHelper.checkSameOwner(host, target2))
        		{
    				return true;
    			}
        	}
        	
        	return false;
        }
    }
    
    /** check target is in server's unattackable target class list, SERVER SIDE only */
    public static boolean checkUnattackTargetList(Entity target)
    {
		HashMap<Integer, String> unatklist = ServerProxy.getUnattackableTargetClass();
		
		if (target != null && unatklist != null)
		{
			String tarClass = target.getClass().getSimpleName();
			return unatklist.containsKey(tarClass.hashCode());
		}
		
		return false;
    }
    
    /** check target is in player's attack target class list */
    public static boolean checkAttackTargetList(Entity host, Entity target)
    {
    	if (target != null && host instanceof IShipAttackBase)
    	{
 			int pid = ((IShipAttackBase) host).getPlayerUID();
 			HashMap<Integer, String> tarList = ServerProxy.getPlayerTargetClass(pid);
 			
 			if (tarList != null)
 			{
 				String tarClass = target.getClass().getSimpleName();
 				
 				//target class is in list
 				if (tarList.containsKey(tarClass.hashCode()))
 				{
 					//if tameable entity, check owner
 					if (target instanceof IEntityOwnable)
 					{
 						if (!TeamHelper.checkSameOwner(host, target))
 						{
 							return true;
 						}
 					}
 					
 					return true;
 				}
 			}
 		}
    	
    	return false;
    }
    
    
    /** update AI target */
	public static void updateTarget(IShipAttackBase host)
	{
		//clear attack target
		if (host.getEntityTarget() != null)
		{
			//clear dead target
			if (!host.getEntityTarget().isEntityAlive()) 
			{
				host.setEntityTarget(null);
			}
			//clear target if target is friendly
			else if (TeamHelper.checkSameOwner((Entity)host, host.getEntityTarget()))
			{
				host.setEntityTarget(null);
			}
		}

		//clear revenge target
		if (host.getEntityRevengeTarget() != null)
		{
            if (!host.getEntityRevengeTarget().isEntityAlive())
            {
            	host.setEntityRevengeTarget(null);
            }
            //clear target after 200 ticks
            else if (host.getTickExisted() - host.getEntityRevengeTime() > 200)
            {
            	host.setEntityRevengeTarget(null);
            }
        }
		
		//clear vanilla attack target
		if (host instanceof BasicEntityShipHostile)
		{
			BasicEntityShipHostile host2 = (BasicEntityShipHostile) host;
			EntityLivingBase gettar = host2.getAttackTarget();
			
			if (gettar != null)
			{
				if (!gettar.isEntityAlive())
				{
					host2.setAttackTarget(null);
				}
				else if (TeamHelper.checkSameOwner((Entity) host, gettar))
				{
					host2.setAttackTarget(null);
				}
			}
		}
		
		//clear invisible target every 64 ticks
		if ((host.getTickExisted() & 63) == 0)
		{
			if (host.getEntityTarget() != null && host.getEntityTarget().isInvisible() &&
				host.getStateMinor(ID.M.LevelFlare) < 1 &&
				host.getStateMinor(ID.M.LevelSearchlight) < 1)
			{
				host.setEntityTarget(null);
			}
		}
	}
	
	/** set ship's revenge target around player within X blocks */
	public static void setRevengeTargetAroundPlayer(EntityPlayer player, double dist, Entity target)
	{
		if (player != null && target != null)
		{
			//get ship
			List<BasicEntityShip> list1 = player.world.getEntitiesWithinAABB(BasicEntityShip.class,
					player.getEntityBoundingBox().expand(dist, dist, dist));
			
			if (list1 != null && !list1.isEmpty())
			{
				for (BasicEntityShip ship : list1)
				{
					//check same owner
					if (!ship.equals(target) && TeamHelper.checkSameOwner(player, ship))
					{
						ship.setEntityRevengeTarget(target);
						ship.setEntityRevengeTime();
					}
				}
			}
		}
	}
	
	/** set mob ship's revenge target within X blocks */
	public static void setRevengeTargetAroundHostileShip(BasicEntityShipHostile host, double dist, Entity target)
	{
		if (host != null && target != null)
		{
			//get hostile ship
			List<BasicEntityShipHostile> list1 = host.world.getEntitiesWithinAABB(BasicEntityShipHostile.class,
					host.getEntityBoundingBox().expand(dist, dist, dist));
			
			if (list1 != null && !list1.isEmpty())
			{
				for (BasicEntityShipHostile ship : list1)
				{
					ship.setEntityRevengeTarget(target);
					ship.setEntityRevengeTime();
				}
			}
		}
	}
	
	/** check special case: invulnerable entity and unattackable entity */
	public static boolean isEntityInvulnerable(Entity target)
	{
		//check special case
		if (target instanceof IProjectile ||
			target instanceof EntityFireball ||
			target instanceof EntityFireworkRocket ||
			target instanceof EntityFishHook ||
			target instanceof EntityHanging ||
			target instanceof EntityAreaEffectCloud)
    	{
    		return true;
    	}
		
		//check unattackable entity
		if (target.world != null && !target.world.isRemote)
		{
			return checkUnattackTargetList(target);
		}
		
		return false;
	}
	
	/** handle key event for OPTool */
	public static void handleOPToolKeyInput()
	{
		if (ClientProxy.debugCooldown > 0) return;
		
		EntityPlayer player = ClientProxy.getClientPlayer();
		GameSettings keySet = ClientProxy.getGameSetting();
		ItemStack optool = player.inventory.getCurrentItem();
		
		//key press
		if (optool != null && optool.getItem() == ModItems.OPTool)
		{
			//numpad 1: add/remove target to unattackable list
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1))
			{
				ClientProxy.debugCooldown = 5;
				RayTraceResult hitObj = EntityHelper.getMouseOverEntity(ClientProxy.getMineraft().getRenderViewEntity(), 32D, 1F, null, false, false);
				
				//hit entity
				if (hitObj != null && hitObj.entityHit != null)
				{
					//target != ship
					if (!(hitObj.entityHit instanceof BasicEntityShip ||
						  hitObj.entityHit instanceof BasicEntityShipHostile ||
						  hitObj.entityHit instanceof BasicEntitySummon ||
						  hitObj.entityHit instanceof BasicEntityMount))
					{
						String tarName = hitObj.entityHit.getClass().getSimpleName();
						LogHelper.debug("DEBUG: target wrench get class: "+tarName);
						
						//send packet to server
						CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetUnatkClass, tarName));
						return;
					}//end not ship
				}//end hit != null
			}
			//numpad 2: show unattackable list
			else if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2))
			{
				ClientProxy.debugCooldown = 20;
				//send packet to server
				CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.ShowUnatkClass));
			}
		}
	}
	
	
}