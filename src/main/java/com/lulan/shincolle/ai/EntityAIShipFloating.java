package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.entity.IShipGuardian;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

/**SHIP FLOATING ON WATER AI
 * 若在水中, 且水上一格為空氣, 則會嘗試上浮並站在水面上
 * (entity本體依然在水中)
 */
public class EntityAIShipFloating extends EntityAIBase
{
	
	private IShipFloating host;
    private BasicEntityShip hostShip;
    private BasicEntityMount hostMount;
    private EntityLivingBase hostLiving;
    

    public EntityAIShipFloating(IShipFloating entity)
    {
    	this.host = entity;
    	this.hostLiving = (EntityLivingBase) entity;
    	
    	if (entity instanceof BasicEntityShip)
    	{
    		this.hostShip = (BasicEntityShip) entity;
    	}
    	else if (entity instanceof BasicEntityMount)
    	{
    		this.hostMount = (BasicEntityMount) entity;
    	}
    	
        this.setMutexBits(8);
    }

    @Override
	public boolean shouldExecute()
    {
    	//ship類: 檢查host坐下
    	if (hostShip != null)
    	{
    		//騎乘, 守衛, 移動, 坐下, 裝載中: 禁止上浮
    		if (hostShip.isRiding() || hostShip.isSitting() || hostShip.getStateMinor(ID.M.CraneState) > 0 ||
    			!hostShip.getShipNavigate().noPath() || isInGuardPosition(hostShip))
    		{
    			return false;
    		}
    		
    		//其他情況
    		return hostShip.getStateFlag(ID.F.CanFloatUp) && hostShip.getShipDepth() > hostShip.getShipFloatingDepth();
    	}
    	//mount類: 檢查mount水深 & host坐下
    	else if (hostMount != null && hostMount.getHostEntity() != null)
    	{
    		BasicEntityShip host = (BasicEntityShip) hostMount.getHostEntity();
			
			//騎乘, 守衛, 移動, 坐下, 裝載中: 禁止上浮
    		if (host.isSitting() || host.getStateMinor(ID.M.CraneState) > 0 ||
    			!host.getShipNavigate().noPath() || isInGuardPosition(host))
    		{
    			return false;
    		}
			
			//騎乘中, 守衛中, 移動中: 禁止上浮
			if (!hostMount.getShipNavigate().noPath() || isInGuardPosition(hostMount))
			{
    			return false;
    		}
			
			return hostMount.getShipDepth() > hostMount.getShipFloatingDepth();
		}
//    	//其他類
//    	else
//    	{
    		return host.getShipDepth() > host.getShipFloatingDepth();
//    	}
    }

    @Override
	public void updateTask()
    {
    	//submarine
    	if (host instanceof IShipInvisible)
    	{
    		if (this.host.getShipDepth() > 4D)
        	{
        		this.hostLiving.motionY += 0.025D;
        		return;
        	}
        	
        	if (this.host.getShipDepth() > 2D)
        	{
        		this.hostLiving.motionY += 0.015D;
        		return;
        	}
        	
        	if (this.host.getShipDepth() > 1.3D)
        	{
        		this.hostLiving.motionY += 0.007D;
        		return;
        	}
        	
        	if (this.host.getShipDepth() > 1.1D)
        	{
        		this.hostLiving.motionY += 0.003D;
        		return;
        	}
    	}
    	//other ship
    	else
    	{
        	if (this.host.getShipDepth() > 4D)
        	{
        		this.hostLiving.motionY += 0.025D;
        		return;
        	}
        	
        	if (this.host.getShipDepth() > 1D)
        	{
        		this.hostLiving.motionY += 0.015D;
        		return;
        	}
        	
        	if (this.host.getShipDepth() > 0.7D)
        	{
        		this.hostLiving.motionY += 0.007D;
        		return;
        	}
        	
        	if (this.host.getShipDepth() > 0.47D)
        	{
        		this.hostLiving.motionY += 0.003D;
        		return;
        	}
        	
        	if (this.host.getShipDepth() > 0.15D)
        	{
        		this.hostLiving.motionY += 0.0015D;
        		return;
        	}
    	}
    	   	
    }
    
    //check is in guard position
    public boolean isInGuardPosition(IShipGuardian entity)
    {
    	//若guard中, 則檢查是否達到guard距離
		if (!entity.getStateFlag(ID.F.CanFollow))
		{
			float fMin = entity.getStateMinor(ID.M.FollowMin) + ((Entity)entity).width * 0.5F;
			fMin = fMin * fMin;
			
			//若守衛entity, 檢查entity距離
			if (entity.getGuardedEntity() != null)
			{
				double distSq = ((Entity)entity).getDistanceSqToEntity(entity.getGuardedEntity());
				if (distSq < fMin) return true;
			}
			//若守衛某地點, 則檢查與該點距離
			else if (entity.getStateMinor(ID.M.GuardY) > 0)
			{
				double distSq = ((Entity)entity).getDistanceSq(entity.getStateMinor(ID.M.GuardX), entity.getStateMinor(ID.M.GuardY), entity.getStateMinor(ID.M.GuardZ));
				if (distSq < fMin && ((Entity)entity).posY >= entity.getStateMinor(ID.M.GuardY)) return true;
			}
		}
		
		return false;
    }
    
    
}
