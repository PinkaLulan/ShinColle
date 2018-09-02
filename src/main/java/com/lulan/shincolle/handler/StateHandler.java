package com.lulan.shincolle.handler;

import java.util.EnumMap;
import java.util.EnumSet;

import javax.annotation.Nonnull;

import com.lulan.shincolle.reference.Enums.AttrBoo;
import com.lulan.shincolle.reference.Enums.AttrNum;
import com.lulan.shincolle.reference.Enums.AttrStr;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;

/**
 * store data in Number or Boolean map
 */
public class StateHandler
{
    
    /** host entity */
    protected IStateEntity host;
    
    //data map for data storage
    protected EnumMap<AttrStr, String> statesString;
    protected EnumMap<AttrNum, Number> statesNumber;
    protected EnumMap<AttrBoo, Boolean> statesFlag;
    
    //dirty set for packet sync
    protected EnumSet<AttrStr> syncString;
    protected EnumSet<AttrNum> syncNumber;
    protected EnumSet<AttrBoo> syncFlag;
    
    
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
        this.statesString = new EnumMap<AttrStr, String>(AttrStr.class);
        this.statesNumber = new EnumMap<AttrNum, Number>(AttrNum.class);
        this.statesFlag = new EnumMap<AttrBoo, Boolean>(AttrBoo.class);
        
        this.syncString = EnumSet.<AttrStr>noneOf(AttrStr.class);
        this.syncNumber = EnumSet.<AttrNum>noneOf(AttrNum.class);
        this.syncFlag = EnumSet.<AttrBoo>noneOf(AttrBoo.class);
        
        this.setNumberState(AttrNum.LastCombatTime, 0);
        this.setNumberState(AttrNum.AttackTime, 0);
        this.setNumberState(AttrNum.AttackTime2, 0);
        this.setNumberState(AttrNum.AttackTime3, 0);
    }
    
    /** init data at the end of entity's initPre() */
    public void initPre()
    {
    }
    
    /** init data at the end of entity's initPost() */
    public void initPost()
    {
    }
    
    public EnumSet<AttrStr> getSyncString()
    {
        return this.syncString;
    }
    
    public EnumSet<AttrNum> getSyncNumber()
    {
        return this.syncNumber;
    }
    
    public EnumSet<AttrBoo> getSyncFlag()
    {
        return this.syncFlag;
    }
    
    public void clearSyncString()
    {
        this.syncString.clear();
    }
    
    public void clearSyncNumber()
    {
        this.syncNumber.clear();
    }
    
    public void clearSyncFlag()
    {
        this.syncFlag.clear();
    }
    
    public EnumMap<AttrStr, String> getAllString()
    {
        return this.statesString;
    }
    
    public EnumMap<AttrNum, Number> getAllNumber()
    {
        return this.statesNumber;
    }
    
    public EnumMap<AttrBoo, Boolean> getAllBoolean()
    {
        return this.statesFlag;
    }
    
    public void setSyncString(AttrStr key)
    {
        this.syncString.add(key);
    }
    
    public void setSyncNumber(AttrNum key)
    {
        this.syncNumber.add(key);
    }
    
    public void setSyncFlag(AttrBoo key)
    {
        this.syncFlag.add(key);
    }
    
    public void setAllString(EnumMap<AttrStr, String> map)
    {
        this.statesString = map;
    }
    
    public void setAllNumber(EnumMap<AttrNum, Number> map)
    {
        this.statesNumber = map;
    }
    
    public void setAllboolaen(EnumMap<AttrBoo, Boolean> map)
    {
        this.statesFlag = map;
    }
    
    /** get data from STRING state map, return empty string if no such data */
    @Nonnull
    public String getStringState(AttrStr key)
    {
        String s = this.statesString.get(key);
        
        if (s != null) return s;
        else return "";
    }
    
    /** get data from NUMBER state map, return Integer(0) if no such data */
    @Nonnull
    public Number getNumberState(AttrNum key)
    {
        Number n = this.statesNumber.get(key);
        
        if (n != null) return n;
        else return new Integer(0);
    }
    
    /** get data from FLAGS state map, return false if no such data */
    @Nonnull
    public boolean getBooleanState(AttrBoo key)
    {
        Boolean b = this.statesFlag.get(key);
        
        if (b != null) return b;
        else return false;
    }
    
    /**
     * get data (byte), return 0 if no data
     * NOTE: data will be truncated to byte (rightmost 8 bits)
     */
    @Nonnull
    public byte getStateByte(AttrNum key)
    {
        Number n = this.statesNumber.get(key);
        
        if (n == null) return 0;
        else return n.byteValue();
    }
    
    /**
     * get data (short), return 0 if no data
     * NOTE: data will be truncated to short (rightmost 16 bits)
     */
    @Nonnull
    public short getStateShort(AttrNum key)
    {
        Number n = this.statesNumber.get(key);
        
        if (n == null) return 0;
        else return n.shortValue();
    }
    
    /**
     * get data (int), return 0 if no data
     * NOTE: data will be truncated to int
     */
    @Nonnull
    public int getStateInt(AttrNum key)
    {
        Number n = this.statesNumber.get(key);
        
        if (n == null) return 0;
        else return n.intValue();
    }
    
    /**
     * get data (float), return 0 if no data
     * NOTE: data will be truncated to float
     */
    @Nonnull
    public float getStateFloat(AttrNum key)
    {
        Number n = this.statesNumber.get(key);
        
        if (n == null) return 0;
        else return n.floatValue();
    }
    
    /**
     * get data (long), return 0 if no data
     */
    @Nonnull
    public long getStateLong(AttrNum key)
    {
        Number n = this.statesNumber.get(key);
        
        if (n == null) return 0;
        else return n.longValue();
    }
    
    /**
     * get data (double), return 0 if no data
     */
    @Nonnull
    public double getStateDouble(AttrNum key)
    {
        Number n = this.statesNumber.get(key);
        
        if (n == null) return 0;
        else return n.doubleValue();
    }
    
    /** set data in NUMBER state map */
    public void setNumberState(AttrNum key, Number value)
    {
        this.statesNumber.put(key, value);
        this.syncNumber.add(key);
    }
    
    /** set data in FLAG state map */
    public void setBooleanState(AttrBoo key, boolean value)
    {
        this.statesFlag.put(key, value);
        this.syncFlag.add(key);
    }
    
    /** set data in STRING state map */
    public void setStringState(AttrStr key, String value)
    {
        this.statesString.put(key, value);
        this.syncString.add(key);
    }
    
    /** add value to number state */
    public void addIntegerState(AttrNum key, int value)
    {
        this.statesNumber.put(key, this.statesNumber.get(key).intValue() + value);
        this.syncNumber.add(key);
    }
    
    /** inverse boolean state */
    public void inverseBooleanState(AttrBoo key)
    {
        this.statesFlag.put(key, !this.statesFlag.get(key));
        this.syncFlag.add(key);
    }
    
    /** last combat time */
    public int getLastCombatTick()
    {
        return this.getStateInt(AttrNum.LastCombatTime);
    }
    
    /** @see #getLastCombatTick() */
    public void setLastCombatTick(int value)
    {
        this.setNumberState(AttrNum.LastCombatTime, value);
    }
    
    public int getAttackTick()
    {
        return this.getStateInt(AttrNum.AttackTime);
    }
    
    public void setAttackTick(int value)
    {
        this.setNumberState(AttrNum.AttackTime, value);
    }
    
    public int getAttackTick2()
    {
        return this.getStateInt(AttrNum.AttackTime2);
    }
    
    public void setAttackTick2(int value)
    {
        this.setNumberState(AttrNum.AttackTime2, value);
    }
    
    public int getAttackTick3()
    {
        return this.getStateInt(AttrNum.AttackTime3);
    }
    
    public void setAttackTick3(int value)
    {
        this.setNumberState(AttrNum.AttackTime3, value);
    }
    
    public void setIsSitting(boolean value)
    {
        if (host instanceof EntityTameable)
        {
            ((EntityTameable) host).setSitting(value);
        }
    }
    
    public void setIsSprinting(boolean value)
    {
        if (host instanceof Entity)
        {
            ((Entity) host).setSprinting(value);
        }
    }
    
    public void setIsSneaking(boolean value)
    {
        if (host instanceof Entity)
        {
            ((Entity) host).setSneaking(value);
        }
    }
    
    public boolean isSitting()
    {
        if (host instanceof EntityTameable)
        {
            return ((EntityTameable) host).isSitting();
        }
        
        return false;
    }
    
    public boolean isSprinting()
    {
        if (host instanceof Entity)
        {
            return ((Entity) host).isSprinting();
        }
        
        return false;
    }
    
    public boolean isSneaking()
    {
        if (host instanceof Entity)
        {
            return ((Entity) host).isSneaking();
        }
        
        return false;
    }
    
    
}