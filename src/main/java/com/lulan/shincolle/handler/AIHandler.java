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
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;

public class AIHandler
{
    
    protected EntityLiving host;
    
    
    public AIHandler(EntityLiving host)
    {
        this.host = host;
    }
    
    public boolean hasAI()
    {
        if (this.host.tasks != null && !this.host.tasks.taskEntries.isEmpty()) return false;
        
        return true;
    }
    
    public boolean hasTargetAI()
    {
        if (this.host.targetTasks != null && !this.host.targetTasks.taskEntries.isEmpty()) return false;
        
        return true;
    }
    
    public EntityAITasks getAITasks()
    {
        return this.host.tasks;
    }
    
    public EntityAITasks getTargetAITasks()
    {
        return this.host.targetTasks;
    }
    
    /**
     * set basic AI for BasicEntityShip
     */
    public void setShipAI()
    {
        if (!(this.host instanceof BasicEntityShip)) return;
        
        BasicEntityShip ship = (BasicEntityShip) this.host;
        EntityAITasks ais = this.getAITasks();
        
        //high priority
        ais.addTask(1, new EntityAIShipSit(ship));                        //0111
        ais.addTask(2, new EntityAIShipFlee(ship));                        //0111
        ais.addTask(3, new EntityAIShipGuarding(ship));                    //0111
        ais.addTask(4, new EntityAIShipFollowOwner(ship));                //0111
        ais.addTask(5, new EntityAIShipOpenDoor(ship, true));            //0000
        
        //use melee attack (mux bit: melee:0100, air:0010, ammo:0001)
        if (this.getStateFlag(ID.F.UseMelee))
        {
            this.tasks.addTask(15, new EntityAIShipAttackOnCollide(ship, 1D));    //0100
        }
        
        //idle AI
        ais.addTask(23, new EntityAIShipFloating(ship));                    //0111
        ais.addTask(24, new EntityAIShipWander(ship, 10, 5, 0.8D));        //0111
        ais.addTask(25, new EntityAIShipWatchClosest(ship,
                                        EntityPlayer.class, 4F, 0.06F));        //0000
        ais.addTask(26, new EntityAIShipLookIdle(ship));                    //0000
    }
    
    
    
    /******** TODO refactoring *******/
    
    //setup AI
    
    
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