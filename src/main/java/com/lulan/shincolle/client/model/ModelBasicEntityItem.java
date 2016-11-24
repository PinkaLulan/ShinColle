package com.lulan.shincolle.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * BlockHeavyGrudge - PinkaLulan  2015/3/29
 * Created using Tabula 4.1.1
 */
public class ModelBasicEntityItem extends ModelBase
{
    
	public ModelRenderer shape1;
    
    public ModelBasicEntityItem()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 10.1F, 0.0F);
        this.shape1.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
        this.setRotateAngle(shape1, 1.275F, 0.592F, 0.774F);
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	GlStateManager.pushAttrib();
    	GlStateManager.pushMatrix();
     
    	GlStateManager.enableBlend();
    	GlStateManager.disableLighting();
    	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	
    	float f6 = MathHelper.cos(f * 0.025F) * 0.5F;
    	float f7 = (f6 < 0F) ? 0.9F + f6 : 0.9F - f6;
    	float f8 = (f6 < 0F) ? 0.25F - f6 * 0.5F : 0.25F + f6 * 1.25F;
    	
    	GlStateManager.scale(f8, f8, f8);
    	GlStateManager.color(1F, 1F, 1F, f7);
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.shape1.render(f5);
    	
    	GlStateManager.enableLighting();
    	GlStateManager.disableBlend();
    	GlStateManager.popAttrib();
    	GlStateManager.popMatrix();

    }

    //for idle/run animation
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      
      this.shape1.rotateAngleX = f % 360F * 0.1F;
      this.shape1.rotateAngleY = f % 360F * 0.1F;
    }
    
    
}

