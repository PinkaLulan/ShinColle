package com.lulan.shincolle.ai;

import java.util.Collections;
import java.util.List;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TargetHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;

/** SHIP PICK ITEM AI
 * 
 *  for BasicEntityShip / BasicEntityMount only
 */
public class EntityAIShipPickItem extends EntityAIBase
{
	
	protected TargetHelper.Sorter targetSorter;
	private IShipEmotion host;
    private BasicEntityShip hostShip;
    private BasicEntityMount hostMount;
    private EntityLivingBase hostLiving;
    private Entity entItem;
    private int pickDelay, pickDelayMax;
    private float pickRange, pickRangeBase;
    

    public EntityAIShipPickItem(IShipEmotion entity, float pickRangeBase)
    {
    	this.setMutexBits(7);
    	
    	this.host = entity;
    	this.hostLiving = (EntityLivingBase) entity;
    	this.pickRangeBase = pickRangeBase;
    	this.pickDelay = 0;
    	
    	if (entity instanceof BasicEntityShip)
    	{
    		this.hostShip = (BasicEntityShip) entity;
    		this.targetSorter = new TargetHelper.Sorter(hostShip);
    	}
    	else if (entity instanceof BasicEntityMount)
    	{
    		this.hostMount = (BasicEntityMount) entity;
    		this.hostShip = (BasicEntityShip) this.hostMount.getHostEntity();
    		this.targetSorter = new TargetHelper.Sorter(hostMount);
    	}
    	
    	//init values
    	updateShipParms();
    }

    @Override
	public boolean shouldExecute()
    {
    	//ship類
    	if (this.hostShip != null)
    	{
    		//check fishing
    		if (hostShip.fishHook != null) return false;
    		
    		//騎乘中, 坐下, 沒開啟特殊能力: 禁止AI
    		if (hostShip.isRiding() || hostShip.isSitting() || !hostShip.getStateFlag(ID.F.PickItem) ||
    			hostShip.getStateMinor(ID.M.CraneState) > 0 || hostShip.getStateFlag(ID.F.NoFuel))
    		{
    			return false;
    		}
    		
    		//check inventory space
    		if (this.hostShip.getCapaShipInventory().getFirstSlotForItem() > 0) return true;
    	}
    	//mount類
    	else if (this.hostMount != null && this.hostShip != null)
    	{
    		//check fishing
    		if (((BasicEntityMount) hostMount).getHostEntity() != null &&
        		((BasicEntityShip)((BasicEntityMount) hostMount).getHostEntity()).fishHook != null) return false;
    		
			//ship坐下, 沒開啟特殊能力: 禁止AI
			if (hostShip.isSitting() || !hostShip.getStateFlag(ID.F.PickItem) ||
				hostShip.getStateMinor(ID.M.CraneState) > 0 || hostShip.getStateFlag(ID.F.NoFuel))
			{
    			return false;
    		}
			
			//check inventory space
    		if(this.hostShip.getCapaShipInventory().getFirstSlotForItem() > 0) return true;
		}

    	return false;
    }

    @Override
	public void updateTask()
    {
    	if (hostShip != null)
    	{
    		//cd--
    		this.pickDelay--;
    		
			//check every 16 ticks
    		if (this.hostShip.ticksExisted % 16 == 0)
    		{
    			//update parms
    			updateShipParms();
    			
    			//check item on ground
    			this.entItem = getNearbyEntityItem();
    			
    			if (this.entItem != null && this.entItem.isEntityAlive())
    			{
    				//go to entity
    				if (this.hostMount != null)
    				{
    					this.hostMount.getShipNavigate().tryMoveToEntityLiving(this.entItem, 1D);
    				}
    				else if (this.hostShip != null)
    				{
    					this.hostShip.getShipNavigate().tryMoveToEntityLiving(this.entItem, 1D);
    				}
    			}
    		}//end every 16 ticks
			
    		//pick up nearby item
    		if (this.pickDelay <= 0 && this.entItem != null)
    		{
    			this.pickDelay = this.pickDelayMax;
    			
    			//get item if close to 9D (3 blocks)
    			if ((this.hostMount != null && this.hostMount.getDistanceSqToEntity(this.entItem) < 9D) ||
    				(this.hostShip != null && this.hostShip.getDistanceSqToEntity(this.entItem) < 9D))
    			{
    				//add item to inventory
    				EntityItem entitem = (EntityItem) this.entItem;
    				ItemStack itemstack = entitem.getEntityItem();
    				int i = itemstack.stackSize;
    				
    				if (!entitem.cannotPickup() &&
    					this.hostShip.getCapaShipInventory().addItemStackToInventory(itemstack))
    				{
    					//play pick item sound
    					this.hostShip.world.playSound(null, hostShip.posX, hostShip.posY, hostShip.posZ,
    							SoundEvents.ENTITY_ITEM_PICKUP, hostShip.getSoundCategory(), ConfigHandler.volumeShip,
    							((this.hostShip.getRNG().nextFloat() - this.hostShip.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
        	           
        				//play entity sound
        				if (this.hostShip.getStateTimer(ID.T.SoundTime) <= 0 && this.hostShip.getRNG().nextInt(2) == 0)
        		    	{
        					this.hostShip.setStateTimer(ID.T.SoundTime, 40 + this.hostShip.getRNG().nextInt(10));
        					this.hostShip.playSound(this.hostShip.getCustomSound(6, this.hostShip), ConfigHandler.volumeShip, 1F);
            			}
        				
    					//send item pickup sync packet
    					this.hostShip.onItemPickup(entitem, i);
    					
    					//send attack time packet
    					this.hostShip.applyParticleAtAttacker(0, null, null);
    					
    					//add exp
    					this.hostShip.addShipExp(ConfigHandler.expGain[6]);
    					
    					//clear entity item if no leftover item
    	                if (itemstack.stackSize <= 0)
    	                {
    	                	entitem.setDead();
    	                	this.entItem = null;
    	                }
    				}
    				
    				//clear path
    				this.hostShip.getShipNavigate().clearPathEntity();
    				if(this.hostMount != null) this.hostMount.getShipNavigate().clearPathEntity();
    			}
    		}//end pick up item
    	}//end ship not null
    }
    
    //get random item entity on ground
    private EntityItem getNearbyEntityItem()
    {
    	//get entity item
    	EntityItem getitem = null;
        List<EntityItem> getlist = null;
        
        getlist = this.hostShip.world.getEntitiesWithinAABB(EntityItem.class, 
        			this.hostShip.getEntityBoundingBox().expand(this.pickRange, this.pickRange * 0.5F + 1F, this.pickRange));
        
        //get random item
        if (getlist != null && !getlist.isEmpty())
        {
        	//sort by dist
        	Collections.sort(getlist, this.targetSorter);
        	getitem = (EntityItem) getlist.get(0);
        }
        
        return getitem;
    }
    
    //update parms
    private void updateShipParms()
    {
    	float speed = this.hostShip.getAttrs().getAttackSpeed();
    	if(speed < 1F) speed = 1F;
    	
    	this.pickDelayMax = (int) (10F / speed);
    	
    	float tempran = this.pickRangeBase + this.hostShip.getStateMinor(ID.M.FollowMax);
    	this.pickRange = this.pickRangeBase + this.hostShip.getAttrs().getAttackRange() * 0.5F;
    	this.pickRange = Math.min(tempran, this.pickRange);
    }
    
    
}