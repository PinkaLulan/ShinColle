package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIShipWatchClosest extends EntityAIWatchClosest
{

	private EntityLiving host;
	private IShipEmotion host2;
	private float range;
	
    public EntityAIShipWatchClosest(EntityLiving entity, Class target, float range, float rate)
    {
        super(entity, target, range, rate);
        this.setMutexBits(0);
        
        this.host = entity;
        this.host2 = (IShipEmotion) entity;
        this.range = range;
    }
    
    //stop watching if target is riding
    @Override
    public boolean shouldExecute()
    {
    	EntityPlayer target = this.host.world.getClosestPlayerToEntity(this.host, this.range);
    
    	if (host2 != null)
    	{
    		if (host2.getStateFlag(ID.F.NoFuel) || host.getRidingEntity() instanceof BasicEntityShip)
    		{
    			return false;
    		}
    	}
    	
    	if (target != null &&
    		(target.isRiding() ||
    		 target.isInvisible()))
    	{
    		return false;
    	}
    	else
    	{
    		return super.shouldExecute();
    	}
    }
    
    @Override
    public void updateTask()
    {
    	super.updateTask();
    	
//    	//sync riding entity look position
//    	if (this.host != null)
//    	{
//    		if (this.host.getRidingEntity() instanceof BasicEntityMount)
//    		{
//    			if (((IShipNavigator)this.host.getRidingEntity()).getShipNavigate().noPath())
//    			{
//    				((EntityLiving)this.host.getRidingEntity()).getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, 40F);
//    			}
//    		}
//		}
    }
    
    
}