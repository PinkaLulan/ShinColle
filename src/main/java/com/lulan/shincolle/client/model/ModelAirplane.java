package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

/**
 * ModelAirplane - PinkaLulan 2015/2/18
 * Created using Tabula 4.1.1
 */
public class ModelAirplane extends ModelBase
{
    public ModelRenderer BodyMain;
    public ModelRenderer EyeL;
    public ModelRenderer EyeR;
    public ModelRenderer AirfoilL;
    public ModelRenderer AirfoilR;
    public ModelRenderer Head;
    public ModelRenderer BodyFront;
    public ModelRenderer Tail;
    public ModelRenderer Tongue;
    public ModelRenderer BombL;
    public ModelRenderer BombR;
    public ModelRenderer GunBase;
    public ModelRenderer Gun;
    public ModelRenderer GlowBodyMain;

    public ModelAirplane()
    {
        this.textureWidth = 32;
        this.textureHeight = 32;
        
        this.Head = new ModelRenderer(this, 8, 24);
        this.Head.setRotationPoint(0.0F, 0.0F, -6.2F);
        this.Head.addBox(-2.0F, -2.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(Head, 0.0F, 0.7853981633974483F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 3, 18);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain.addBox(-3.0F, -3.0F, -1.0F, 6, 7, 7, 0.0F);
        this.BombR = new ModelRenderer(this, 0, 0);
        this.BombR.setRotationPoint(-6.0F, 2.3F, -1.0F);
        this.BombR.addBox(0.0F, 0.0F, 0.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(BombR, 0.0F, 0.0F, 0.7853981633974483F);
        this.AirfoilL = new ModelRenderer(this, 0, 17);
        this.AirfoilL.setRotationPoint(3.5F, 0.0F, 0.0F);
        this.AirfoilL.addBox(-2.5F, -2.0F, -6.0F, 5, 4, 11, 0.0F);
        this.setRotateAngle(AirfoilL, 0.0F, 0.5235987755982988F, 0.12217304763960307F);
        this.AirfoilR = new ModelRenderer(this, 0, 17);
        this.AirfoilR.setRotationPoint(-3.5F, 0.0F, 0.0F);
        this.AirfoilR.addBox(-2.5F, -2.0F, -6.0F, 5, 4, 11, 0.0F);
        this.setRotateAngle(AirfoilR, 0.0F, -0.5235987755982988F, -0.12217304763960307F);
        this.BombL = new ModelRenderer(this, 0, 0);
        this.BombL.setRotationPoint(6.0F, 2.3F, -1.0F);
        this.BombL.addBox(0.0F, 0.0F, 0.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(BombL, 0.0F, 0.0F, 0.7853981633974483F);
        this.EyeL = new ModelRenderer(this, 16, 0);
        this.EyeL.setRotationPoint(3.7F, -3.2F, 2.0F);
        this.EyeL.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
        this.setRotateAngle(EyeL, 0.0F, 0.7853981633974483F, 0.17453292519943295F);
        this.EyeR = new ModelRenderer(this, 16, 0);
        this.EyeR.setRotationPoint(-3.7F, -3.2F, 2.0F);
        this.EyeR.addBox(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
        this.setRotateAngle(EyeR, 0.0F, -2.356194490192345F, -0.17453292519943295F);
        this.GunBase = new ModelRenderer(this, 10, 24);
        this.GunBase.setRotationPoint(0.0F, 4.0F, 2.5F);
        this.GunBase.addBox(-1.5F, 0.0F, 0.0F, 3, 4, 3, 0.0F);
        this.Tail = new ModelRenderer(this, 0, 19);
        this.Tail.setRotationPoint(0.0F, 0.0F, 4.3F);
        this.Tail.addBox(-4.0F, -2.5F, -4.0F, 8, 5, 8, 0.0F);
        this.setRotateAngle(Tail, 0.0F, 0.7853981633974483F, 0.0F);
        this.BodyFront = new ModelRenderer(this, 12, 6);
        this.BodyFront.setRotationPoint(0.0F, 0.0F, -3.2F);
        this.BodyFront.addBox(-2.5F, -2.6F, -2.5F, 5, 6, 5, 0.0F);
        this.setRotateAngle(BodyFront, 0.08726646259971647F, 0.0F, 0.0F);
        this.Tongue = new ModelRenderer(this, 0, 13);
        this.Tongue.setRotationPoint(0.0F, 2.3F, -3.5F);
        this.Tongue.addBox(-1.5F, 0.0F, -3.0F, 3, 1, 3, 0.0F);
        this.setRotateAngle(Tongue, 1.6580627893946132F, 0.0F, 0.0F);
        this.Gun = new ModelRenderer(this, 0, 0);
        this.Gun.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.Gun.addBox(-0.5F, 0.0F, -8.0F, 1, 1, 8, 0.0F);
        this.setRotateAngle(Gun, 0.05235987755982988F, 0.0F, 0.0F);
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain.addChild(this.Head);
        this.BodyMain.addChild(this.BombR);
        this.BodyMain.addChild(this.AirfoilL);
        this.BodyMain.addChild(this.AirfoilR);
        this.BodyMain.addChild(this.BombL);
        this.BodyMain.addChild(this.GunBase);
        this.BodyMain.addChild(this.Tail);
        this.BodyMain.addChild(this.BodyFront);
        this.BodyMain.addChild(this.Tongue);
        this.GunBase.addChild(this.Gun);
        this.GlowBodyMain.addChild(this.EyeL);
        this.GlowBodyMain.addChild(this.EyeR);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
    	GlStateManager.pushMatrix();
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.scale(0.5F, 0.5F, 0.5F);
    	GlStateManager.translate(0F, 2.5F, 0F);
    	
    	//main body
    	this.BodyMain.render(f5);
    	
    	GlStateManager.disableBlend();
    	
    	//light part
    	GlStateManager.disableLighting();
    	GlStateManager.enableCull();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GlStateManager.disableCull();
    	GlStateManager.enableLighting();
    	
    	GlStateManager.popMatrix();
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
    
    //for idle/run animation
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {  
    	this.BodyMain.rotateAngleY = f3 / 57F;	//左右角度
    	this.BodyMain.rotateAngleX = f4 / 57F; 	//上下角度
    	this.GlowBodyMain.rotateAngleY = f3 / 57F;
    	this.GlowBodyMain.rotateAngleX = f4 / 57F;
    }
    
    
}
