package com.lulan.shincolle.handler;

import java.util.HashMap;

import javax.annotation.Nonnull;

/**
 * store data in Number or Boolean map
 */
public class StateHandler
{
    
    /** host entity */
    protected IStateEntity host;
    
    protected HashMap<Short, String> statesStringMap;
    protected HashMap<Short, Number> statesNumberMap;
    protected HashMap<Short, Boolean> statesFlagMap;
    
    
    public StateHandler(IStateEntity host)
    {
        this.host = host;
        
        //null check
        if (this.host == null) throw new NullPointerException("StateHandler: host is null.");
        
        //init
        this.initFirst();
    }
    
    /** init data on new object */
    protected void initFirst()
    {
        this.statesStringMap = new HashMap<Short, String>();
        this.statesNumberMap = new HashMap<Short, Number>();
        this.statesFlagMap = new HashMap<Short, Boolean>();
    }
    
    public void initPre()
    {
    }
    
    public void initPost()
    {
    }
    
    /**
     * get data from number or flag state map<br>
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
    
    /** get data from STRING state map, return empty string if no such data */
    @Nonnull
    public String getStringState(Short key)
    {
        String s = this.statesStringMap.get(key);
        
        if (s != null) return s;
        else return "";
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
    public boolean getBooleanState(Short key)
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
    public void setBooleanState(Short key, boolean value)
    {
        this.statesFlagMap.put(key, value);
    }
    
    /** set data in STRING state map */
    public void setStringState(Short key, String value)
    {
        this.statesStringMap.put(key, value);
    }
    
    /** add value to number state */
    public void addNumberState(Short key, int value)
    {
        this.statesNumberMap.put(key, this.statesNumberMap.get(key).intValue() + value);
    }
    
    /** inverse boolean state */
    public void inverseBooleanState(Short key)
    {
        this.statesFlagMap.put(key, !this.statesFlagMap.get(key));
    }
    
    
}