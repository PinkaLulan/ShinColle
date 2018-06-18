package com.lulan.shincolle.handler;

import java.util.HashMap;

/**
 * handle all buff, debuff, attack and damaged effect for entity
 */
public class BuffHandler
{
    /** buffs */
    protected HashMap<Integer, Integer> BuffMap;
    protected HashMap<Integer, int[]> AttackEffectMap;
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public HashMap<Integer, Integer> getBuffMap()
    {
        if (this.BuffMap == null) this.BuffMap = new HashMap<Integer, Integer>();
        return this.BuffMap;
    }

    @Override
    public void setBuffMap(HashMap<Integer, Integer> map)
    {
        if (map != null) this.BuffMap = map;
    }
    
    @Override
    public HashMap<Integer, int[]> getAttackEffectMap()
    {
        if (this.AttackEffectMap == null) this.AttackEffectMap = new HashMap<Integer, int[]>();
        return this.AttackEffectMap;
    }

    @Override
    public void setAttackEffectMap(HashMap<Integer, int[]> map)
    {
        this.AttackEffectMap = map;
    }
}