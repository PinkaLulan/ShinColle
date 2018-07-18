package com.lulan.shincolle.reference.dataclass;

import java.util.ArrayList;

import com.lulan.shincolle.reference.Enums.ParType;

/**
 * data class for particle methods
 * 
 * fixed value:
 *   boolean:
 *     0:isMorphEntity
 */
public class ParticleData
{
    
    protected ParType type;
    protected ArrayList<Boolean> booleanData;
    protected ArrayList<Integer> intData;
    protected ArrayList<Float> floatData;
    protected ArrayList<String> stringData;
    
    
    public ParticleData(ParType type)
    {
        this.type = type;
    }
    
    public ParType getType()
    {
        return this.type;
    }
    
    public void setIntData(int data)
    {
        if (this.intData == null) this.intData = new ArrayList<Integer>();
        this.intData.add(data);
    }
    
    public void setIntData(ArrayList<Integer> data)
    {
        this.intData = data;
    }
    
    public int getIntData(int index)
    {
        Integer value = this.intData.get(index);
        if (value != null) return value.intValue();
        return 0;
    }
    
    public ArrayList<Integer> getIntData()
    {
        return this.intData;
    }
    
    public void setFloatData(float data)
    {
        if (this.floatData == null) this.floatData = new ArrayList<Float>();
        this.floatData.add(data);
    }
    
    public void setFloatData(ArrayList<Float> data)
    {
        this.floatData = data;
    }
    
    public float getFloatData(int index)
    {
        Float value = this.floatData.get(index);
        if (value != null) return value.floatValue();
        return 0F;
    }
    
    public ArrayList<Float> getFloatData()
    {
        return this.floatData;
    }
    
    public void setStringData(String data)
    {
        if (this.stringData == null) this.stringData = new ArrayList<String>();
        this.stringData.add(data);
    }
    
    public void setStringData(ArrayList<String> data)
    {
        this.stringData = data;
    }
    
    public String getStringData(int index)
    {
        String value = this.stringData.get(index);
        if (value != null) return value;
        return "";
    }
    
    public ArrayList<String> getStringData()
    {
        return this.stringData;
    }
    
    public void setBooleanData(boolean data)
    {
        if (this.booleanData == null) this.booleanData = new ArrayList<Boolean>();
        this.booleanData.add(data);
    }
    
    public void setBooleanData(ArrayList<Boolean> data)
    {
        this.booleanData = data;
    }
    
    public boolean getBooleanData(int index)
    {
        Boolean value = this.booleanData.get(index);
        if (value != null) return value.booleanValue();
        return false;
    }
    public ArrayList<Boolean> getBooleanData()
    {
        return this.booleanData;
    }
    
    
}