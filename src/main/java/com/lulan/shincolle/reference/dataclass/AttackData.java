package com.lulan.shincolle.reference.dataclass;

import com.lulan.shincolle.handler.IAttackEntity;
import com.lulan.shincolle.reference.Enums.AtkType;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

/**
 * attack data object as method params
 */
public class AttackData
{
    
    public AtkType atkType;
    public float damage;
    public MissileData missileData;
    public IAttackEntity host;
    public Entity target;
    public BlockPos targetPos;
    public Dist4d targetVec;
    public boolean isMiss;
    public boolean isCri;
    public boolean isDhit;
    public boolean isThit;
    
    
    public AttackData(AtkType atkType, IAttackEntity host, Entity target,
                      BlockPos targetPos, Dist4d targetVec, MissileData md,
                      float damage)
    {
        this.atkType = atkType;
        this.damage = damage;
        this.host = host;
        this.target = target;
        this.targetPos = targetPos;
        this.targetVec = targetVec;
        this.missileData = md;
        
        //init data
        this.isMiss = false;
        this.isCri = false;
        this.isDhit = false;
        this.isThit = false;
    }
    
    
}