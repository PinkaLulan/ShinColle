package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class EntityAIShipWander extends EntityAIBase
{
	
	private IShipAttackBase host;
    private EntityCreature host2;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    private int ranXZ, ranY;

    
    public EntityAIShipWander(EntityCreature host, int rangeXZ, int rangeY, double speed)
    {
    	//host must be ship type
    	if (host instanceof IShipAttackBase)
    	{
    		this.host = (IShipAttackBase) host;
    		this.host2 = host;
    	}
    	else
    	{
    		LogHelper.debug("DEBUG: wander AI: host is not ship!");
    		this.host = null;
    		this.host2 = null;
    	}
        
    	this.ranXZ = rangeXZ;
    	this.ranY = rangeY;
        this.speed = speed;
        this.setMutexBits(7);
    }

    /** should begin checking */
    public boolean shouldExecute()
    {
    	//平均每 180 tick 會發動一次
        if (host.getIsRiding() || host.getIsSitting() || host.getStateMinor(ID.M.CraneState) > 0 ||
           host2.getRNG().nextInt(180) != 0)
        {
            return false;
        }
        else
        {
        	//check fishing
        	if (host instanceof BasicEntityShip)
        	{
        		if (((BasicEntityShip) host).fishHook != null) return false;
        	}
        	else if (host instanceof BasicEntityMount)
        	{
        		if (((BasicEntityMount) host).getHostEntity() != null &&
        			((BasicEntityShip)((BasicEntityMount) host).getHostEntity()).fishHook != null) return false;
        	}
        	
        	//get random position
            Vec3d vec3 = RandomPositionGenerator.findRandomTarget(this.host2, ranXZ, ranY);

            if (vec3 == null)
            {
                return false;
            }
            else
            {
                this.xPosition = vec3.x;
                this.yPosition = vec3.y;
                this.zPosition = vec3.z;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting()
    {
        return !this.host.getShipNavigate().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.host.getShipNavigate().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
    
    
}