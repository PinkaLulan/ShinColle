package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelFloatingFort - PinkaLulan
 * Created using Tabula 4.1.1
 */
public class ModelFloatingFort extends ModelBase
{
    public ModelRenderer BodyMain;
    public ModelRenderer Body1;
    public ModelRenderer Body2;
    public ModelRenderer Body3;
    public ModelRenderer EarL;
    public ModelRenderer EarR;
    public ModelRenderer JawMain;
    public ModelRenderer Jaw1;
    public ModelRenderer Jaw2;
    public ModelRenderer Jaw3;

    public ModelFloatingFort()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.Jaw2 = new ModelRenderer(this, 1, 3);
        this.Jaw2.setRotationPoint(0.0F, -1.0F, -10.0F);
        this.Jaw2.addBox(-7.5F, 0.0F, 0.0F, 15, 4, 9, 0.0F);
        this.BodyMain = new ModelRenderer(this, 76, 43);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -6.4F, -6.5F, 13, 8, 13, 0.0F);
        this.setRotateAngle(BodyMain, 0.22759093446006054F, 0.0F, 0.0F);
        this.Body1 = new ModelRenderer(this, 76, 20);
        this.Body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body1.addBox(-5.0F, -4.3F, -8.0F, 10, 6, 16, 0.0F);
        this.Jaw3 = new ModelRenderer(this, 42, 0);
        this.Jaw3.setRotationPoint(0.0F, -0.1F, -9.5F);
        this.Jaw3.addBox(-5.0F, 4.0F, 0.0F, 10, 1, 9, 0.0F);
        this.Body3 = new ModelRenderer(this, 76, 2);
        this.Body3.setRotationPoint(0.0F, -0.5F, 0.0F);
        this.Body3.addBox(-8.0F, -5.0F, -5.0F, 16, 7, 10, 0.0F);
        this.JawMain = new ModelRenderer(this, 1, 39);
        this.JawMain.setRotationPoint(0.0F, 2.8F, 6.0F);
        this.JawMain.addBox(-6.0F, -1.1F, -11.5F, 12, 5, 12, 0.0F);
        this.setRotateAngle(JawMain, 0.5235987755982988F, 0.0F, 0.0F);
        this.Body2 = new ModelRenderer(this, 54, 19);
        this.Body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body2.addBox(-5.0F, -8.5F, -4.5F, 10, 2, 9, 0.0F);
        this.setRotateAngle(Body2, 0.0F, 0.0F, -0.01361356816555577F);
        this.EarR = new ModelRenderer(this, 114, 20);
        this.EarR.setRotationPoint(-5.0F, -5.5F, 0.7F);
        this.EarR.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(EarR, -0.17453292519943295F, -0.7853981633974483F, -0.08726646259971647F);
        this.Jaw1 = new ModelRenderer(this, 1, 18);
        this.Jaw1.setRotationPoint(0.0F, -1.2F, -13.0F);
        this.Jaw1.addBox(-4.5F, 0.0F, 0.0F, 9, 4, 15, 0.0F);
        this.EarL = new ModelRenderer(this, 114, 20);
        this.EarL.mirror = true;
        this.EarL.setRotationPoint(5.0F, -5.5F, 0.7F);
        this.EarL.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(EarL, -0.17453292519943295F, 0.7853981633974483F, 0.08726646259971647F);
        this.JawMain.addChild(this.Jaw2);
        this.BodyMain.addChild(this.Body1);
        this.JawMain.addChild(this.Jaw3);
        this.BodyMain.addChild(this.Body3);
        this.BodyMain.addChild(this.JawMain);
        this.BodyMain.addChild(this.Body2);
        this.BodyMain.addChild(this.EarR);
        this.JawMain.addChild(this.Jaw1);
        this.BodyMain.addChild(this.EarL);
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	GlStateManager.pushMatrix();
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.scale(0.3F, 0.3F, 0.3F);
    	GlStateManager.translate(0F, 4.2F, 0F);
    	
    	//main body
    	this.BodyMain.render(f5);
    	
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
    }

    //for idle/run animation
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {  
    	this.BodyMain.rotateAngleY = f3 * 0.0174533F;	//左右角度
    	this.BodyMain.rotateAngleX = f4 * 0.0174533F; 	//上下角度
    	this.JawMain.rotateAngleZ = 0F;
    	this.JawMain.rotateAngleX = MathHelper.cos(f2) * 0.25F + 0.375F;
    }
    
    
}