package com.lulan.shincolle.client.model;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * BlockHeavyGrudge - PinkaLulan  2015/3/29
 * Created using Tabula 4.1.1
 */
public class ModelBasicEntityItem extends ModelBase {
    
	public ModelRenderer shape1;
    
    public float scale = 1F;

    public ModelBasicEntityItem(float scale) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.scale = scale;
        
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 10.1F, 0.0F);
        this.shape1.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
        this.setRotateAngle(shape1, 1.2747884856566583F, 0.5918411493512771F, 0.7740535232594852F);
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	GL11.glPushMatrix();
     
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glDisable(GL11.GL_LIGHTING);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

    	GL11.glScalef(scale, scale, scale);

    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	
    	this.shape1.render(f5);
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	GL11.glPopMatrix();
    }

    //for idle/run animation
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
      
      this.shape1.rotateAngleX = f % 360F / 5F;
      this.shape1.rotateAngleY = f % 360F / 5F;
    }
}

