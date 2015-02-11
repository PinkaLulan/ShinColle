package com.lulan.shincolle.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * NewProject - Undefined
 * Created using Tabula 4.1.1
 */
public class ModelVortex extends ModelBase {
	
    public ModelRenderer Vortex;

    public ModelVortex() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.Vortex = new ModelRenderer(this, 0, 0);
        this.Vortex.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Vortex.addBox(-64.0F, -64.0F, 0.0F, 128, 128, 0, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	
//    	GL11.glPushMatrix();		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
//		GL11.glDepthMask(false);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//    	GL11.glPushMatrix();
//    	GL11.glRotatef(angle, x, y, z);
//    	GL11.glDisable(GL11.GL_CULL_FACE);
//    	GL11.glDisable(GL11.GL_DEPTH_TEST);
    	this.Vortex.render(f5);
//    	GL11.glEnable(GL11.GL_DEPTH_TEST);
//    	GL11.glEnable(GL11.GL_CULL_FACE);
//    	GL11.glPopMatrix();
//        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
