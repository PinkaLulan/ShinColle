package com.lulan.shincolle.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * ModelTakoyaki - PinkaLulan 2015/2/18
 * Created using Tabula 4.1.1
 */
public class ModelTakoyaki extends ModelBase {
    public ModelRenderer BodyMain;
    public ModelRenderer JawMain;
    public ModelRenderer EyeL;
    public ModelRenderer Body1;
    public ModelRenderer Body2;
    public ModelRenderer Body3;
    public ModelRenderer EarL;
    public ModelRenderer EarR;
    public ModelRenderer Jaw1;
    public ModelRenderer Jaw2;
    public ModelRenderer Jaw3;
    public ModelRenderer Tongue;
    public ModelRenderer GlowBodyMain;

    public ModelTakoyaki() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.JawMain = new ModelRenderer(this, 0, 38);
        this.JawMain.setRotationPoint(0.0F, 3.5F, 3F);
        this.JawMain.addBox(-6.5F, -1.1F, -8.0F, 13, 6, 13, 0.0F);
        this.setRotateAngle(JawMain, 1.3F, 0.0F, 0.0F);
        this.Jaw1 = new ModelRenderer(this, 0, 17);
        this.Jaw1.setRotationPoint(0.0F, -1.2F, -9.5F);
        this.Jaw1.addBox(-5.0F, 0.0F, 0.0F, 10, 5, 16, 0.0F);
        this.EyeL = new ModelRenderer(this, 65, 50);
        this.EyeL.setRotationPoint(8.1F, -3.3F, 0.5F);
        this.EyeL.addBox(0.0F, -3.0F, -3.0F, 0, 5, 5, 0.0F);
        this.setRotateAngle(EyeL, -0.17453292519943295F, 0.0F, 0.0F);
        this.Body1 = new ModelRenderer(this, 76, 19);
        this.Body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body1.addBox(-5.0F, -4.3F, -8.0F, 10, 7, 16, 0.0F);
        this.Body3 = new ModelRenderer(this, 76, 1);
        this.Body3.setRotationPoint(0.0F, -0.5F, 0.0F);
        this.Body3.addBox(-8.0F, -5.0F, -5.0F, 16, 8, 10, 0.0F);
        this.Jaw2 = new ModelRenderer(this, 0, 2);
        this.Jaw2.setRotationPoint(0.0F, -1.0F, -6.5F);
        this.Jaw2.addBox(-8.0F, 0.0F, 0.0F, 16, 5, 10, 0.0F);
        this.Jaw3 = new ModelRenderer(this, 42, 0);
        this.Jaw3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Jaw3.addBox(-5.0F, 5.0F, -5.5F, 10, 2, 9, 0.0F);
        this.BodyMain = new ModelRenderer(this, 76, 42);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -6.4F, -6.5F, 13, 9, 13, 0.0F);
        this.setRotateAngle(BodyMain, -0.3490658503988659F, 0.0F, 0.0F);
        this.Body2 = new ModelRenderer(this, 54, 19);
        this.Body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body2.addBox(-5.0F, -8.5F, -4.5F, 10, 2, 9, 0.0F);
        this.setRotateAngle(Body2, 0.0F, 0.0F, -0.01361356816555577F);
        this.EarL = new ModelRenderer(this, 114, 20);
        this.EarL.mirror = true;
        this.EarL.setRotationPoint(5.5F, -4.5F, 3.0F);
        this.EarL.addBox(-2.0F, -8.0F, -1.5F, 4, 8, 3, 0.0F);
        this.setRotateAngle(EarL, -0.5235987755982988F, -0.5235987755982988F, 0.7853981633974483F);
        this.Tongue = new ModelRenderer(this, 50, 39);
        this.Tongue.setRotationPoint(0.0F, -2.0F, 0.5F);
        this.Tongue.addBox(-4.5F, 0.0F, -7.0F, 9, 3, 7, 0.0F);
        this.setRotateAngle(Tongue, -0.08726646259971647F, 0.0F, 0.0F);
        this.EarR = new ModelRenderer(this, 114, 20);
        this.EarR.setRotationPoint(-5.5F, -4.5F, 3.0F);
        this.EarR.addBox(-2.0F, -8.0F, -1.5F, 4, 8, 3, 0.0F);
        this.setRotateAngle(EarR, -0.5235987755982988F, 0.5235987755982988F, -0.7853981633974483F);
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.JawMain.addChild(this.Jaw1);
        this.BodyMain.addChild(this.Body1);
        this.BodyMain.addChild(this.JawMain);
        this.BodyMain.addChild(this.Body3);
        this.JawMain.addChild(this.Jaw2);
        this.JawMain.addChild(this.Jaw3);
        this.BodyMain.addChild(this.Body2);
        this.BodyMain.addChild(this.EarL);
        this.JawMain.addChild(this.Tongue);
        this.BodyMain.addChild(this.EarR);
        this.GlowBodyMain.addChild(this.EyeL);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {  
        GL11.glPushMatrix();
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glScalef(0.45F, 0.45F, 0.45F);
    	GL11.glTranslatef(0F, 2.7F, 0F);

        this.BodyMain.render(f5);
    	GL11.glDisable(GL11.GL_BLEND);
    	
    	//亮度設為240
    	GL11.glDisable(GL11.GL_LIGHTING);
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);	
    	GL11.glEnable(GL11.GL_LIGHTING);
    	
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
    
    //for idle/run animation
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {  
    	this.BodyMain.rotateAngleY = f3 / 57F;	//左右角度
    	this.BodyMain.rotateAngleX = f4 / 57F; 	//上下角度
    	this.GlowBodyMain.rotateAngleY = f3 / 57F;
    	this.GlowBodyMain.rotateAngleX = f4 / 57F;
    	this.JawMain.rotateAngleZ = 0F;
    	this.JawMain.rotateAngleX = MathHelper.cos(f2*0.125F) * 0.2F + 1.1F;
    }
}

