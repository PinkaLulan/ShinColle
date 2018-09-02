package com.lulan.shincolle.handler;

import com.lulan.shincolle.reference.Enums.AttrNum;
import com.lulan.shincolle.reference.dataclass.Attrs;

/**
 * state handler + Attrs data storage
 */
public class AttrStateHandler extends StateHandler
{
    
    /** attributes */
    protected Attrs attrs;
    
    
    public AttrStateHandler(IStateEntity host)
    {
        super(host);
    }
    
    @Override
    protected void initFirst()
    {
        super.initFirst();
        
        this.setNumberState(AttrNum.ShipLevel, 1);
        this.setNumberState(AttrNum.ShipClass, 0);
    }
    
    @Override
    public void initPre()
    {
        //init ship attrs
        this.attrs = new Attrs(this.getAttrClass());
    }
    
    public Attrs getAttrs()
    {
        return this.attrs;
    }
    
    public int getAttrClass()
    {
        return this.getStateInt(AttrNum.ShipClass);
    }
    
    public void setAttrClass(short value)
    {
        this.setNumberState(AttrNum.ShipClass, value);
    }
    
    public int getLevel()
    {
        return this.getStateInt(AttrNum.ShipLevel);
    }
    
    public void setLevel(int value)
    {
        this.setNumberState(AttrNum.ShipLevel, value);
    }
    
    
}