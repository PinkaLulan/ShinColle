package com.lulan.shincolle.handler;

import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class GuardHandler
{
    
    /** last waypoint */
    protected BlockPos lastWaypoint;
    
 
    
    
    
    
    
    
    public int getWpStayTime()
    {
        return this.getStateInt(ID.Keys.WpStayTime);
    }
    
    public void setWpStayTime(int value)
    {
        this.setNumberState(ID.Keys.WpStayTime, value);
    }
    
    public int getWpStayTimeMax()
    {
        return this.wpStayTime2Ticks(this.getStateByte(ID.Keys.WpStay));
    }
    
    /** convert waypoint stay time to ticks */
    public static int wpStayTime2Ticks(byte wpstay)
    {
        switch (wpstay)
        {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
            return wpstay * 100;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
            return (wpstay - 5) * 1200;
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
            return (wpstay - 10) * 12000;
        default:
            return 0;
        }
    }
    
    @Override
    public Entity getGuardedEntity()
    {
        return this.guardedEntity;
    }

    @Override
    public void setGuardedEntity(Entity entity)
    {
        if(entity != null && entity.isEntityAlive())
        {
            this.guardedEntity = entity;
            this.setStateMinor(ID.M.GuardID, entity.getEntityId());
        }
        else
        {
            this.guardedEntity = null;
            this.setStateMinor(ID.M.GuardID, -1);
        }
    }
    
    /**
     * vec:
     *   0:x, 1:y, 2:z, 3:dimension, 4:type
     *   
     *   type:
     *     0:none, 1:guard block, 2:guard entity
     */
    @Override
    public int getGuardedPos(int vec)
    {
        switch (vec)
        {
        case 0:
            return this.getStateMinor(ID.M.GuardX);
        case 1:
            return this.getStateMinor(ID.M.GuardY);
        case 2:
            return this.getStateMinor(ID.M.GuardZ);
        case 3:
            return this.getStateMinor(ID.M.GuardDim);
        case 4:
            return this.getStateMinor(ID.M.GuardType);
        default:
            return 0;
        }
    }

    /**
     *  type: 0:none, 1:block, 2:entity
     */
    @Override
    public void setGuardedPos(int x, int y, int z, int dim, int type)
    {
        this.setStateMinor(ID.M.GuardX, x);
        this.setStateMinor(ID.M.GuardY, y);
        this.setStateMinor(ID.M.GuardZ, z);
        this.setStateMinor(ID.M.GuardDim, dim);
        this.setStateMinor(ID.M.GuardType, type);
    }
    
    
}