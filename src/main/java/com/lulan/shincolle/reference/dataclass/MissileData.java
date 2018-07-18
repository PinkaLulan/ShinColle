package com.lulan.shincolle.reference.dataclass;

public class MissileData
{
    
    public int type;        //ammo type
    public int movetype;    //move type
    public float vel0;      //init velocity
    public float accY1;     //init accY1
    public float accY2;     //init accY1
    
    
    public MissileData()
    {
        this.resetData();
    }
    
    public void resetData()
    {
        this.type = 0;
        this.movetype = -1;
        this.vel0 = 0.5F;
        this.accY1 = 1.04F;
        this.accY2 = 1.04F;
    }
    
    
}