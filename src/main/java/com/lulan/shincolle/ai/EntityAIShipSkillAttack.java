package com.lulan.shincolle.ai;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

/**
 * ENTITY SKILL ATTACK AI
 * 若ID.S.Phase不為0, 則持續以updateSkillAttack(target)來更新skill state
 */
public class EntityAIShipSkillAttack extends EntityAIBase
{
	
    private IShipAttackBase host;  	//AI host entity
    private EntityLiving host2;
    
    
    //parm: host, move speed, p4, attack delay, p6
    public EntityAIShipSkillAttack(IShipAttackBase host)
    {
        if (!(host instanceof IShipAttackBase))
        {
            throw new IllegalArgumentException("RangeAttack AI requires interface IShipCannonAttack");
        }
        else
        {
            this.host = host;
            this.host2 = (EntityLiving) host;
            this.setMutexBits(15);
        }
    }

    //check ai start condition
    @Override
	public boolean shouldExecute()
    {
    	//for entity ship
    	if (this.host != null)
    	{
    		//坐下, 裝載中不攻擊
    		if (this.host.getIsSitting() || this.host.getStateMinor(ID.M.CraneState) > 0)
    		{
    			//reset phase
    			if (this.host.getStateEmotion(ID.S.Phase) > 0) this.host.setStateEmotion(ID.S.Phase, 0, true);
    			return false;
    		}
    		
    		//若騎乘ship類座騎, 則攻擊交給mount判定
    		if (this.host.getIsRiding())
    		{
    			if (this.host2.getRidingEntity() instanceof BasicEntityMount)
    			{
    				return false;
    			}
    		}
        	
        	Entity target = this.host.getEntityTarget();
        	
            if (this.host.getStateEmotion(ID.S.Phase) > 0)
            {   
                return true;
            }
    	}       
        
        return false;
    }
    
    //init AI parameter, call once every target
    @Override
    public void startExecuting()
    {    
    }

    //判定是否繼續AI： 有target就繼續, 或者已經移動完畢就繼續
    @Override
	public boolean shouldContinueExecuting()
    {
        return this.shouldExecute();
    }

    //重置AI方法
    @Override
	public void resetTask()
    {
    }

    //進行AI
    @Override
	public void updateTask()
    {
    	if (this.host != null)
    	{
    		this.host.updateSkillAttack(this.host.getEntityTarget());
    	}
    }
    
    
}