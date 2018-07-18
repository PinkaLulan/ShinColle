package com.lulan.shincolle.handler;

import com.lulan.shincolle.client.render.ICustomTexture;
import com.lulan.shincolle.reference.ID;

public class RenderHandler implements ICustomTexture
{
    
    protected int textureID = 0;
    
    
    @Override
    public int getTextureID()
    {
        return textureID;
    }
    
    
    
    
    
    
    
    /***** old method refactoring ******/
    
    
    public int getDeathTick()
    {
        return this.deathTime;
    }

    public void setDeathTick(int par1)
    {
        this.deathTime = par1;
    }
    
    //get model rotate angle, par1 = 0:X, 1:Y, 2:Z
    @Override
    public float getModelRotate(int par1)
    {
        switch (par1)
        {
        default:
            return this.rotateAngle[0];
        case 1:
            return this.rotateAngle[1];
        case 2:
            return this.rotateAngle[2];
        }
    }
    
    //set model rotate angle, par1 = 0:X, 1:Y, 2:Z
    @Override
    public void setModelRotate(int par1, float par2)
    {
        switch (par1)
        {
        default:
            rotateAngle[0] = par2;
        case 1:
            rotateAngle[1] = par2;
        case 2:
            rotateAngle[2] = par2;
        }
    }
    
    @Override
    public int getTextureID()
    {
        return this.getAttrClass();
    }
    
  //check held item can be rendered
    public boolean canShowHeldItem()
    {
        if (!this.getStateFlag(ID.F.ShowHeldItem) || this.getAttackTick() > 0 ||
            this.getAttackTick2() > 0)
        {
            return false;
        }
        
        return true;
    }
    
    
}