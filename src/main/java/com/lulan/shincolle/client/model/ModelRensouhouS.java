package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;

/**
 * ModelRensouhouS - PinkaLulan 2015/3/30
 * Created using Tabula 4.1.1
 */
public class ModelRensouhouS extends ModelBase {
    public ModelRenderer BodyMain;
    public ModelRenderer HeadBase;
    public ModelRenderer TailJaw1;
    public ModelRenderer Head;
    public ModelRenderer TailHeadCL1;
    public ModelRenderer TailHeadCR1;
    public ModelRenderer Tooth02;
    public ModelRenderer Tube01;
    public ModelRenderer Tube02;
    public ModelRenderer Tube03;
    public ModelRenderer TailHead2;
    public ModelRenderer Tooth01;
    public ModelRenderer HeadCannon1;
    public ModelRenderer HeadCannon2;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHeadBase;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowTailJaw1;
    public ModelRenderer GlowTailHead2;

    public ModelRensouhouS() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        
        this.Tube03 = new ModelRenderer(this, 0, 0);
        this.Tube03.setRotationPoint(-5.5F, 4.6F, 22.0F);
        this.Tube03.addBox(0.0F, 0.0F, 0.0F, 11, 1, 1, 0.0F);
        this.HeadBase = new ModelRenderer(this, 0, 0);
        this.HeadBase.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.HeadBase.addBox(-6.0F, -8.0F, 2.0F, 12, 15, 8, 0.0F);
        this.setRotateAngle(HeadBase, -0.13962634015954636F, -3.141592653589793F, 0.0F);
        this.Tube01 = new ModelRenderer(this, 0, 0);
        this.Tube01.setRotationPoint(-4.5F, 3.0F, 13.0F);
        this.Tube01.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 10, 0.0F);
        this.setRotateAngle(Tube01, -0.17453292519943295F, -0.05235987755982988F, 0.0F);
        this.HeadCannon1 = new ModelRenderer(this, 26, 6);
        this.HeadCannon1.setRotationPoint(3.2F, 3.5F, 12.0F);
        this.HeadCannon1.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 15, 0.0F);
        this.setRotateAngle(HeadCannon1, 0.08726646259971647F, 0.08726646259971647F, 0.017627825445142728F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.TailJaw1 = new ModelRenderer(this, 0, 0);
        this.TailJaw1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailJaw1.addBox(-6.5F, 0.0F, 0.0F, 13, 5, 16, 0.0F);
        this.setRotateAngle(TailJaw1, -0.3142F, 0.0F, 0.0F);
        this.Tooth02 = new ModelRenderer(this, 2, 42);
        this.Tooth02.setRotationPoint(0.0F, -3.0F, 4.0F);
        this.Tooth02.addBox(-5.5F, 0.0F, 0.0F, 11, 5, 11, 0.0F);
        this.setRotateAngle(Tooth02, 0.17453292519943295F, 0.0F, 0.0F);
        this.TailHead2 = new ModelRenderer(this, 0, 0);
        this.TailHead2.setRotationPoint(0.0F, -1.0F, 6.5F);
        this.TailHead2.addBox(-7.0F, 0.0F, 0.0F, 14, 8, 13, 0.0F);
        this.Tooth01 = new ModelRenderer(this, 0, 25);
        this.Tooth01.setRotationPoint(0.0F, 4.5F, 4.5F);
        this.Tooth01.addBox(-6.0F, 0.0F, 0.0F, 12, 5, 12, 0.0F);
        this.setRotateAngle(Tooth01, -0.17453292519943295F, 0.0F, 0.0F);
        this.Tube02 = new ModelRenderer(this, 0, 0);
        this.Tube02.setRotationPoint(4.5F, 3.0F, 13.0F);
        this.Tube02.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 10, 0.0F);
        this.setRotateAngle(Tube02, -0.17453292519943295F, 0.05235987755982988F, 0.0F);
        this.HeadCannon2 = new ModelRenderer(this, 26, 6);
        this.HeadCannon2.setRotationPoint(-3.2F, 3.5F, 12.0F);
        this.HeadCannon2.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 15, 0.0F);
        this.setRotateAngle(HeadCannon2, 0.08726646259971647F, -0.08726646259971647F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -8.5F, 4.0F);
        this.Head.addBox(-7.0F, -0.2F, -3.6F, 14, 8, 10, 0.0F);
        this.setRotateAngle(Head, 0.17453292519943295F, 0.0F, 0.0F);
        this.TailHeadCR1 = new ModelRenderer(this, 36, 25);
        this.TailHeadCR1.mirror = true;
        this.TailHeadCR1.setRotationPoint(-5.5F, 0.0F, 9.0F);
        this.TailHeadCR1.addBox(-3.0F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.setRotateAngle(TailHeadCR1, 0.7853981633974483F, -0.13962634015954636F, 0.0F);
        this.TailHeadCL1 = new ModelRenderer(this, 36, 25);
        this.TailHeadCL1.setRotationPoint(5.5F, 0.0F, 9.0F);
        this.TailHeadCL1.addBox(0.0F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.setRotateAngle(TailHeadCL1, 0.7853981633974483F, 0.13962634015954636F, 0.0F);
        this.TailJaw1.addChild(this.Tube03);
        this.BodyMain.addChild(this.HeadBase);
        this.TailJaw1.addChild(this.Tube01);
        this.HeadBase.addChild(this.TailJaw1);  
        this.Head.addChild(this.TailHead2);  
        this.TailJaw1.addChild(this.Tube02);     
        this.HeadBase.addChild(this.Head);
        this.HeadBase.addChild(this.TailHeadCR1);
        this.HeadBase.addChild(this.TailHeadCL1);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GlowHeadBase = new ModelRenderer(this, 0, 0);
        this.GlowHeadBase.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.setRotateAngle(GlowHeadBase, -0.13962634015954636F, -3.141592653589793F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -8.5F, 4.0F);
        this.setRotateAngle(GlowHead, 0.17453292519943295F, 0.0F, 0.0F);
        this.GlowTailJaw1 = new ModelRenderer(this, 0, 0);
        this.GlowTailJaw1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.setRotateAngle(GlowTailJaw1, -0.3142F, 0.0F, 0.0F);
        this.GlowTailHead2 = new ModelRenderer(this, 0, 0);
        this.GlowTailHead2.setRotationPoint(0.0F, -1.0F, 6.5F);
        
        this.GlowBodyMain.addChild(this.GlowHeadBase);
        this.GlowHeadBase.addChild(this.GlowHead);
        this.GlowHeadBase.addChild(this.GlowTailJaw1);
        this.GlowHead.addChild(this.GlowTailHead2);
        this.GlowHead.addChild(this.Tooth01);
        this.GlowTailJaw1.addChild(this.Tooth02);
        this.GlowTailHead2.addChild(this.HeadCannon1);
        this.GlowTailHead2.addChild(this.HeadCannon2);
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	GL11.glPushMatrix();
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glScalef(0.4F, 0.4F, 0.4F);

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
    
    //for idle/run animation
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		  
		IShipEmotion ent = (IShipEmotion)entity;
		  
		GL11.glTranslatef(0F, 1F, 0F);
		float angleX = MathHelper.cos(f2 * 0.1F);
			
		//jaw
		this.TailJaw1.rotateAngleX = angleX * 0.05F - 0.3142F;
		//cannon
		this.HeadCannon1.rotateAngleX = angleX * 0.1F + 0.15F;
		this.HeadCannon2.rotateAngleX = -angleX * 0.1F + 0.15F;
		
		//攻擊動作    
	    if(ent.getAttackTime() > 0) {
	    	this.TailJaw1.rotateAngleX = angleX * 0.3F - 0.8F;
	    }
	    
	    this.GlowTailJaw1.rotateAngleX = this.TailJaw1.rotateAngleX;
    }
    
}

