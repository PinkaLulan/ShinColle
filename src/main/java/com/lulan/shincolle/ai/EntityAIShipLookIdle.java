package com.lulan.shincolle.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import com.lulan.shincolle.entity.BasicEntityShip;

public class EntityAIShipLookIdle extends EntityAIBase
{
    /** The entity that is looking idle. */
    private EntityLiving host;
    /** X offset to look at */
    private double lookX;
    /** Z offset to look at */
    private double lookZ;
    /** A decrementing tick that stops the entity from being idle once it reaches 0. */
    private int idleTime;

    
    public EntityAIShipLookIdle(EntityLiving entity)
    {
        this.host = entity;
        this.setMutexBits(3);
    }

    public boolean shouldExecute()
    {
    	if (host.ridingEntity instanceof BasicEntityShip)
    	{
    		return false;
    	}
    	
        return this.host.getRNG().nextFloat() < 0.02F;
    }

    public boolean continueExecuting()
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
        this.host.getLookHelper().setLookPosition(this.host.posX + this.lookX, this.host.posY + (double)this.host.getEyeHeight(), this.host.posZ + this.lookZ, 10.0F, (float)this.host.getVerticalFaceSpeed());
    }
}
