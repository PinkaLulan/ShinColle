package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIShipLookIdle extends EntityAIBase
{
    /** The entity that is looking idle. */
    private EntityLiving host;
    private IShipEmotion host2;
    /** X offset to look at */
    private double lookX;
    /** Z offset to look at */
    private double lookZ;
    /** A decrementing tick that stops the entity from being idle once it reaches 0. */
    private int idleTime;

    
    public EntityAIShipLookIdle(EntityLiving entity)
    {
        this.host = entity;
        this.host2 = (IShipEmotion) entity;
        this.setMutexBits(0);
    }

    public boolean shouldExecute()
    {
    	if (host2.getStateFlag(ID.F.NoFuel) || host.getRidingEntity() instanceof BasicEntityShip)
    	{
    		return false;
    	}
    	
        return this.host.getRNG().nextFloat() < 0.02F;
    }
    
    @Override
    public boolean shouldContinueExecuting()
    {
        return this.idleTime >= 0;
    }

    public void startExecuting()
    {
    	double d0 = (Math.PI * 2D) * this.host.getRNG().nextDouble();
        this.lookX = Math.cos(d0);
        this.lookZ = Math.sin(d0);
        this.idleTime = 20 + this.host.getRNG().nextInt(20);
    }

    public void updateTask()
    {
        --this.idleTime;
        this.host.getLookHelper().setLookPosition(this.host.posX + this.lookX, this.host.posY + (double)this.host.getEyeHeight(), this.host.posZ + this.lookZ, (float)this.host.getHorizontalFaceSpeed(), (float)this.host.getVerticalFaceSpeed());
    }
    
    
}