package com.lulan.shincolle.handler;

import com.lulan.shincolle.ai.EntityAIShipAttackOnCollide;
import com.lulan.shincolle.ai.EntityAIShipFlee;
import com.lulan.shincolle.ai.EntityAIShipFloating;
import com.lulan.shincolle.ai.EntityAIShipFollowOwner;
import com.lulan.shincolle.ai.EntityAIShipGuarding;
import com.lulan.shincolle.ai.EntityAIShipLookIdle;
import com.lulan.shincolle.ai.EntityAIShipOpenDoor;
import com.lulan.shincolle.ai.EntityAIShipRangeTarget;
import com.lulan.shincolle.ai.EntityAIShipRevengeTarget;
import com.lulan.shincolle.ai.EntityAIShipSit;
import com.lulan.shincolle.ai.EntityAIShipWander;
import com.lulan.shincolle.ai.EntityAIShipWatchClosest;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AIHandler
{
    
    
    
    
    
    
    
    //setup AI
    protected void setAIList()
    {
        //high priority
        this.tasks.addTask(1, new EntityAIShipSit(this));                        //0111
        this.tasks.addTask(2, new EntityAIShipFlee(this));                        //0111
        this.tasks.addTask(3, new EntityAIShipGuarding(this));                    //0111
        this.tasks.addTask(4, new EntityAIShipFollowOwner(this));                //0111
        this.tasks.addTask(5, new EntityAIShipOpenDoor(this, true));            //0000
        
        //use melee attack (mux bit: melee:0100, air:0010, ammo:0001)
        if (this.getStateFlag(ID.F.UseMelee))
        {
            this.tasks.addTask(15, new EntityAIShipAttackOnCollide(this, 1D));    //0100
        }
        
        //idle AI
        this.tasks.addTask(23, new EntityAIShipFloating(this));                    //0111
        this.tasks.addTask(24, new EntityAIShipWander(this, 10, 5, 0.8D));        //0111
        this.tasks.addTask(25, new EntityAIShipWatchClosest(this,
                                        EntityPlayer.class, 4F, 0.06F));        //0000
        this.tasks.addTask(26, new EntityAIShipLookIdle(this));                    //0000
    }
    
    //setup target AI
    public void setAITargetList()
    {
        //passive target AI
        if (this.getStateFlag(ID.F.PassiveAI))
        {
            this.targetTasks.addTask(1, new EntityAIShipRevengeTarget(this));
        }
        //active target AI
        else
        {
            this.targetTasks.addTask(1, new EntityAIShipRevengeTarget(this));
            this.targetTasks.addTask(5, new EntityAIShipRangeTarget(this, Entity.class));
        }
    }

    //clear AI
    protected void clearAITasks()
    {
        tasks.taskEntries.clear();
    }
    
    //clear target AI
    protected void clearAITargetTasks()
    {
        this.setAttackTarget(null);
        this.setEntityTarget(null);
        targetTasks.taskEntries.clear();
    }
    
    public void resetShipAI()
    {
        if (!this.ship.world.isRemote)
        {
            this.ship.clearAITasks();
            this.ship.setAIList();
            
            //設定mount的AI
            if (this.ship.getRidingEntity() instanceof BasicEntityMount)
            {
                ((BasicEntityMount) this.getRidingEntity()).clearAITasks();
                ((BasicEntityMount) this.getRidingEntity()).setAIList();
            }
        }
    }
    
}