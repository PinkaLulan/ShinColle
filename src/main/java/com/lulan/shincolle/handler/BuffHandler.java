package com.lulan.shincolle.handler;

import java.util.HashMap;

/**
 * handle all buff, debuff, attack and damaged effect for entity
 */
public class BuffHandler
{
    /** buffs */
    protected HashMap<Integer, Integer> buffMap;
    protected HashMap<Integer, int[]> attackEffectMap;
    /** host object */
    protected IAttackEntity host;
    
    
    public BuffHandler(IAttackEntity host)
    {
        this.host = host;
        
        this.initFirst();
    }
    
    /** init data on construct */
    protected void initFirst()
    {
        this.buffMap = new HashMap<Integer, Integer>();
        this.attackEffectMap = new HashMap<Integer, int[]>();
    }
    
    public HashMap<Integer, Integer> getBuffMap()
    {
        if (this.buffMap == null) this.buffMap = new HashMap<Integer, Integer>();
        return this.buffMap;
    }
    
    public HashMap<Integer, int[]> getAttackEffectMap()
    {
        if (this.attackEffectMap == null) this.attackEffectMap = new HashMap<Integer, int[]>();
        return this.attackEffectMap;
    }
    
    public void setBuffMap(HashMap<Integer, Integer> map)
    {
        if (map == null)
        {
            this.buffMap = new HashMap<Integer, Integer>();
        }
        else
        {
            this.buffMap = map;
        }
    }
    
    public void setAttackEffectMap(HashMap<Integer, int[]> map)
    {
        if (map == null)
        {
            this.attackEffectMap = new HashMap<Integer, int[]>();
        }
        else
        {
            this.attackEffectMap = map;
        }
    }
    
    
}