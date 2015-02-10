package com.lulan.shincolle.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * BlockHeavyGrudge - PinkaLulan
 * Created using Tabula 4.1.1
 */
public class ModelHeavyGrudgeOff extends ModelBase {
	
    public ModelRenderer shape1;
    public int rotateAngle;

    public ModelHeavyGrudgeOff() {
    	rotateAngle = 0;
    	
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0F, 0F, 0F);
        this.shape1.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
        this.setRotateAngle(shape1, 1.27F, 0.59F, 0.77F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
//    	float angleZ = MathHelper.cos(f2*0.08F);
//    	this.shape1.rotateAngleX = angleZ * 1.5F;
//    	this.shape1.rotateAngleY = angleZ * 1.5F;
//    	this.shape1.rotateAngleZ = angleZ * 1.5F;
//    	
//    	GL11.glPushMatrix();
//    	GL11.glEnable(GL11.GL_BLEND);			//秨币硓家Α
//    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//    	GL11.glScalef(1.5F, 1.5F, 1.5F); 		
//    	this.shape1.render(f5);
//    	GL11.glDisable(GL11.GL_BLEND);			//秨币硓家Α
//    	GL11.glPopMatrix(); 	
    }
    
	public void renderModel(float f5, int rotate) {
		rotateAngle += rotate;
		
		GL11.glPushMatrix();
		GL11.glRotatef(rotateAngle, 1F, 1F, 1F);
    	GL11.glEnable(GL11.GL_BLEND);			//秨币硓家Α
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glScalef(0.5F, 0.5F, 0.5F); 		
    	this.shape1.render(f5);
    	GL11.glDisable(GL11.GL_BLEND);			//秨币硓家Α
    	GL11.glPopMatrix();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

