package com.lulan.shincolle.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class TargetHandler
{
    
    protected EntityLivingBase aiTarget;       //target for some AI
    protected Entity atkTarget;                //attack target
    protected Entity rvgTarget;                //revenge target
    protected EntityPlayer owner;
    protected BlockPos posTarget;
    
    
    public EntityLivingBase getAITarget()
    {
        return aiTarget;
    }
    
    
    
    
    
    /************* TODO refactoring ***********/
    
    
    @Override
    public Entity getEntityTarget()
    {
        return this.atkTarget;
    }
    
    @Override
    public Entity getEntityRevengeTarget()
    {
        return this.rvgTarget;
    }
    
    public void setAITarget(EntityLivingBase target)
    {
        this.aiTarget = target;
    }
    
    @Override
    public void setEntityTarget(Entity target)
    {
        this.atkTarget = target;
    }
      
    @Override
    public void setEntityRevengeTarget(Entity target)
    {
        this.rvgTarget = target;
    }
}