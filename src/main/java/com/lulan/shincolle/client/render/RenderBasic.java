package com.lulan.shincolle.client.render;

import com.lulan.shincolle.entity.IShipEmotion;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.EntityLiving;

abstract public class RenderBasic extends RenderLiving<EntityLiving>
{

	protected static final DynamicTexture TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);
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
    
    //change death color
    @Override
    protected boolean setBrightness(EntityLiving entity, float ptick, boolean combineTextures)
    {
        float f = entity.getBrightness(ptick);
        int i = this.getColorMultiplier(entity, f, ptick);
        boolean flag = (i >> 24 & 255) > 0;
        boolean flag1 = entity.hurtTime > 0;
        boolean flag2 = entity.deathTime > 0;

        if (!flag && !flag1 && !flag2)
        {
            return false;
        }
        else if (!flag && !combineTextures)
        {
            return false;
        }
        else
        {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            this.brightnessBuffer.position(0);

            //hurt color
            if (flag1)
            {
                this.brightnessBuffer.put(1F);
                this.brightnessBuffer.put(0F);
                this.brightnessBuffer.put(0F);
                this.brightnessBuffer.put(0.1F);
            }
            //dead color
            else if (flag2)
            {
                this.brightnessBuffer.put(0.3F);
                this.brightnessBuffer.put(0F);
                this.brightnessBuffer.put(0F);
                this.brightnessBuffer.put(0.6F);
            }
            else
            {
	            float f1 = (float)(i >> 24 & 255) / 255.0F;
	            float f2 = (float)(i >> 16 & 255) / 255.0F;
	            float f3 = (float)(i >> 8 & 255) / 255.0F;
	            float f4 = (float)(i & 255) / 255.0F;
	            this.brightnessBuffer.put(f2);
	            this.brightnessBuffer.put(f3);
	            this.brightnessBuffer.put(f4);
	            this.brightnessBuffer.put(1.0F - f1);
            }

            this.brightnessBuffer.flip();
            GlStateManager.glTexEnv(8960, 8705, this.brightnessBuffer);
            GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(TEXTURE_BRIGHTNESS.getGlTextureId());
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            return true;
        }
    }
    
	//interpolation
	protected double interp(double start, double end, double pct)
	{
        return start + (end - start) * pct;
    }

    
}