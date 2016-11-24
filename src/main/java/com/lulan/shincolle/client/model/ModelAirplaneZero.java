package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

/**
 * ModelAirplaneZero - PinkaLulan
 * Created using Tabula 4.1.1
 */
public class ModelAirplaneZero extends ModelBase {
	
    public ModelRenderer BodyMain;
    public ModelRenderer Tail01;
    public ModelRenderer Wing01;
    public ModelRenderer Wing02;
    public ModelRenderer BodyU;
    public ModelRenderer Propeller;
    public ModelRenderer Prop02;
    public ModelRenderer Tank;
    public ModelRenderer Tail02;
    public ModelRenderer Tail03;
    public ModelRenderer Tail04;
    public ModelRenderer GlowBodyMain;
    
    private float scale;
    private float offsetY;
    
    
    public ModelAirplaneZero(int scaleType) {
        this.textureWidth = 64;
        this.textureHeight = 32;
        
        switch(scaleType) {  //type 1: boss scale
        case 1:
        	scale = 0.8F;
        	offsetY = -2.1F;
        	break;
        default:
        	scale = 0.4F;
        	offsetY = 0F;
        	break;
        }
        
        this.BodyMain = new ModelRenderer(this, 0, 17);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain.addBox(-2.0F, -3.0F, -6.0F, 4, 4, 11, 0.0F);
        this.Tail03 = new ModelRenderer(this, 0, 17);
        this.Tail03.setRotationPoint(0.0F, -2.2F, 4.5F);
        this.Tail03.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 3, 0.0F);
        this.setRotateAngle(Tail03, -1.0471975511965976F, 0.0F, 0.0F);
        this.Propeller = new ModelRenderer(this, 0, 6);
        this.Propeller.setRotationPoint(0.0F, -1.0F, -6.5F);
        this.Propeller.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 0, 0.0F);
        this.Wing02 = new ModelRenderer(this, 0, 0);
        this.Wing02.setRotationPoint(-2.0F, -0.4F, -3.2F);
        this.Wing02.addBox(-13.0F, 0.0F, 0.0F, 13, 1, 5, 0.0F);
        this.setRotateAngle(Wing02, 0.0F, 0.0F, 0.06981317007977318F);
        this.Prop02 = new ModelRenderer(this, 0, 24);
        this.Prop02.setRotationPoint(0.0F, -1.0F, -7.5F);
        this.Prop02.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.Tail04 = new ModelRenderer(this, 0, 13);
        this.Tail04.setRotationPoint(0.0F, 0.2F, 2.0F);
        this.Tail04.addBox(-6.5F, 0.0F, 0.0F, 13, 1, 3, 0.0F);
        this.Tail01 = new ModelRenderer(this, 30, 25);
        this.Tail01.setRotationPoint(0.0F, -2.8F, 5.0F);
        this.Tail01.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
        this.Wing01 = new ModelRenderer(this, 0, 0);
        this.Wing01.mirror = true;
        this.Wing01.setRotationPoint(2.0F, -0.4F, -3.2F);
        this.Wing01.addBox(0.0F, 0.0F, 0.0F, 13, 1, 5, 0.0F);
        this.setRotateAngle(Wing01, 0.0F, 0.0F, -0.06981317007977318F);
        this.Tail02 = new ModelRenderer(this, 46, 24);
        this.Tail02.setRotationPoint(0.0F, 0.1F, 4.0F);
        this.Tail02.addBox(-1.5F, 0.0F, 0.0F, 3, 2, 6, 0.0F);
        this.BodyU = new ModelRenderer(this, 19, 17);
        this.BodyU.setRotationPoint(0.0F, -4.9F, -1.8F);
        this.BodyU.addBox(-1.5F, 0.0F, 0.0F, 3, 2, 6, 0.0F);
        this.setRotateAngle(BodyU, -0.3141592653589793F, 0.0F, 0.0F);
        this.Tank = new ModelRenderer(this, 14, 7);
        this.Tank.setRotationPoint(0.0F, 0.5F, -3.0F);
        this.Tank.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.Tail02.addChild(this.Tail03);
        this.BodyMain.addChild(this.Propeller);
        this.BodyMain.addChild(this.Wing02);
        this.BodyMain.addChild(this.Prop02);
        this.Tail02.addChild(this.Tail04);
        this.BodyMain.addChild(this.Tail01);
        this.BodyMain.addChild(this.Wing01);
        this.Tail01.addChild(this.Tail02);
        this.BodyMain.addChild(this.BodyU);
        this.BodyMain.addChild(this.Tank);
        
        //glow
        this.GlowBodyMain = new ModelRenderer(this, 0, 17);
        this.GlowBodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        
        this.GlowBodyMain.addChild(this.BodyU);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	if(entity.ticksExisted > 6) {
	    	GL11.glPushMatrix();
	    	GL11.glEnable(GL11.GL_BLEND);
	    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	    	GL11.glScalef(scale, scale, scale);
	    	
	    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	        this.BodyMain.render(f5);
	    	GL11.glDisable(GL11.GL_BLEND);
	    	
	    	//亮度設為240
	    	GL11.glDisable(GL11.GL_LIGHTING);
	    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
	    	this.GlowBodyMain.render(f5);
	    	GL11.glEnable(GL11.GL_LIGHTING);
	    	
	    	GL11.glPopMatrix();
    	}
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {  
    	GL11.glTranslatef(0F, offsetY + 3.3F, 0F);
    	
    	this.BodyMain.rotateAngleY = f3 / 57F;	//左右角度
    	this.BodyMain.rotateAngleX = f4 / 57F; 	//上下角度
    	this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
    	this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
    }
    
    
}

