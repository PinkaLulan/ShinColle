package com.lulan.shincolle.client.render;

import com.lulan.shincolle.entity.IShipEmotion;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;

abstract public class RenderBasic extends RenderLiving<EntityLiving>
{

	//parm
	protected int shipClass = 0;
	protected boolean initModel = true;
	
    public RenderBasic(RenderManager rm)
    {
        super(rm, null, 1F);
    }
    
    /** set mainModel, shadowSize, scale */
    abstract protected void setModel();
    
    /** set shadow size */
    abstract protected void setShadowSize();
    
    /** get model and set shadow size on rendering */
    @Override
    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float parTick)
    {
    	//model init
    	if (this.initModel)
    	{
    		this.shipClass = ((IShipCustomTexture) entity).getTextureID();
    		this.initModel = false;
    		setModel();
    	}
    	
    	//for invisible model
    	if (this.mainModel == null) return;
    	
    	//set shadow size
    	setShadowSize();
    	
    	//tweak shadow size
    	if (((IShipEmotion)entity).getScaleLevel() > 0) this.shadowSize += ((IShipEmotion)entity).getScaleLevel() * 0.4F;
    	
    	super.doRender(entity, x, y, z, yaw, parTick);
    }
    
	//interpolation
	protected double interp(double start, double end, double pct)
	{
        return start + (end - start) * pct;
    }

    
}