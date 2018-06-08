package com.lulan.shincolle.entity;

import java.util.HashMap;

import javax.annotation.Nonnull;

/**
 * store data in Number or Boolean map
 */
public class StateHandler
{
    
    protected HashMap<Short, Number> statesNumberMap;
    protected HashMap<Short, Boolean> statesFlagMap;
    
    
    public StateHandler()
    {
        this.statesNumberMap = new HashMap<Short, Number>();
        this.statesFlagMap = new HashMap<Short, Boolean>();
    }
    
    /**
     * get data from all state map<br>
     * for flags: return Byte(1) if <tt>true</tt>, else Byte(0) (include null)
     */
    @Nonnull
    public Number getState(Short key)
    {
        Number n = this.statesNumberMap.get(key);
        if (n != null) return n;
        
        Boolean b = this.statesFlagMap.get(key);
        if (b != null) return b.booleanValue() ? new Byte((byte)1) : new Byte((byte)0);
        
        return new Byte((byte)0);
    }
    
    /** get data from NUMBER state map, return Integer(0) if no such data */
    @Nonnull
    public Number getNumberState(Short key)
    {
        Number n = this.statesNumberMap.get(key);
        
        if (n != null) return n;
        else return new Integer(0);
    }
    
    /** get data from FLAGS state map, return false if no such data */
    @Nonnull
    public boolean getFlagState(Short key)
    {
        Boolean b = this.statesFlagMap.get(key);
        
        if (b != null) return b;
        else return false;
    }
    
    /**
     * get data (byte), return 0 if no data
     * NOTE: data will be truncated to byte (rightmost 8 bits)
     */
    @Nonnull
    public byte getStateByte(Short key)
    {
        Number n = this.statesNumberMap.get(key);
        
        if (n == null) return 0;
        else return n.byteValue();
    }
    
    /**
     * get data (short), return 0 if no data
     * NOTE: data will be truncated to short (rightmost 16 bits)
     */
    @Nonnull
    public short getStateShort(Short key)
    {
        Number n = this.statesNumberMap.get(key);
        
        if (n == null) return 0;
        else return n.shortValue();
    }
    
    /**
     * get data (int), return 0 if no data
     * NOTE: data will be truncated to int
     */
    @Nonnull
    public int getStateInt(Short key)
    {
        Number n = this.statesNumberMap.get(key);
        
        if (n == null) return 0;
        else return n.intValue();
    }
    
    /**
     * get data (float), return 0 if no data
     * NOTE: data will be truncated to float
     */
    @Nonnull
    public float getStateFloat(Short key)
    {
        Number n = this.statesNumberMap.get(key);
        
        if (n == null) return 0;
        else return n.floatValue();
    }
    
    /**
     * get data (long), return 0 if no data
     */
    @Nonnull
    public long getStateLong(Short key)
    {
        Number n = this.statesNumberMap.get(key);
        
        if (n == null) return 0;
        else return n.longValue();
    }
    
    /**
     * get data (double), return 0 if no data
     */
    @Nonnull
    public double getStateDouble(Short key)
    {
        Number n = this.statesNumberMap.get(key);
        
        if (n == null) return 0;
        else return n.doubleValue();
    }
    
    /** set data in NUMBER state map */
    public void setNumberState(Short key, Number value)
    {
        this.statesNumberMap.put(key, value);
    }
    
    /** set data in FLAG state map */
    public void setFlagState(Short key, boolean value)
    {
        this.statesFlagMap.put(key, value);
    }
    
    
}