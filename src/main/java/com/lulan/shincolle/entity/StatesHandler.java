package com.lulan.shincolle.entity;

import java.util.HashMap;

import javax.annotation.Nonnull;

public class StatesHandler
{
    
    protected HashMap<Short, Number> statesMap;
    
    
    public StatesHandler()
    {
        this.statesMap = new HashMap<Short, Number>();
    }
    
    /** get data in state map */
    public Number getState(Short key)
    {
        return this.statesMap.get(key);
    }
    
    /**
     * get data (boolean), return false if 0 or no data
     * NOTE: data will be truncated to byte (rightmost 8 bits)
     */
    @Nonnull
    public boolean getStateBoolean(Short key)
    {
        Number n = this.statesMap.get(key);
        
        if (n == null) return false;
        else
        {
            return n.byteValue() != 0;
        }
    }
    
    /**
     * get data (byte), return 0 if no data
     * NOTE: data will be truncated to byte (rightmost 8 bits)
     */
    @Nonnull
    public byte getStateByte(Short key)
    {
        Number n = this.statesMap.get(key);
        
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
        Number n = this.statesMap.get(key);
        
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
        Number n = this.statesMap.get(key);
        
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
        Number n = this.statesMap.get(key);
        
        if (n == null) return 0;
        else return n.floatValue();
    }
    
    /**
     * get data (long), return 0 if no data
     */
    @Nonnull
    public long getStateLong(Short key)
    {
        Number n = this.statesMap.get(key);
        
        if (n == null) return 0;
        else return n.longValue();
    }
    
    /**
     * get data (double), return 0 if no data
     */
    @Nonnull
    public double getStateDouble(Short key)
    {
        Number n = this.statesMap.get(key);
        
        if (n == null) return 0;
        else return n.doubleValue();
    }
    
    /** set data in state map */
    public void setState(Short key, Number value)
    {
        this.statesMap.put(key, value);
    }
    
    /**
     * set data in state map
     * NOTE: data will save as Byte 
     */
    public void setStateBoolean(Short key, boolean b)
    {
        this.statesMap.put(key, b ? new Byte((byte)1) : new Byte((byte)0));
    }
    
    
}