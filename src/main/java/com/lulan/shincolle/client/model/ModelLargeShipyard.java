package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;


/**
 * ModelLargeShipyard - PinkaLulan 0215/2/10
 * Created using Tabula 4.1.1
 */
public class ModelLargeShipyard extends ModelBase {
    public ModelRenderer BodyMain;
    public ModelRenderer Body01;
    public ModelRenderer Body02;
    public ModelRenderer Body03;
    public ModelRenderer Body04;
    public ModelRenderer Body05;
    public ModelRenderer Body06;
    public ModelRenderer Body07;
    public ModelRenderer Body08;
    public ModelRenderer Base00;
    public ModelRenderer Base01;
    public ModelRenderer Base02;
    public ModelRenderer Base03;
    public ModelRenderer Base04;
    public ModelRenderer Base05;
    public ModelRenderer Base06;
    public ModelRenderer Base07;
    public ModelRenderer Base08;
    public ModelRenderer Pillar01a;
    public ModelRenderer Pillar01b;
    public ModelRenderer Pillar02a;
    public ModelRenderer Pillar02b;
    public ModelRenderer Pillar03a;
    public ModelRenderer Pillar03b;
    public ModelRenderer Pillar01a_1;
    public ModelRenderer Pillar01b_1;

    public ModelLargeShipyard() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Body06 = new ModelRenderer(this, 0, 0);
        this.Body06.setRotationPoint(-10.0F, -6.0F, -23.0F);
        this.Body06.addBox(0.0F, 0.0F, 0.0F, 20, 6, 10, 0.0F);
        this.Body07 = new ModelRenderer(this, 0, 0);
        this.Body07.setRotationPoint(10.0F, -7.0F, -20.0F);
        this.Body07.addBox(0.0F, 0.0F, 0.0F, 12, 7, 14, 0.0F);
        this.Base06 = new ModelRenderer(this, 0, 0);
        this.Base06.setRotationPoint(-24.0F, -1.0F, 8.0F);
        this.Base06.addBox(0.0F, 0.0F, 0.0F, 16, 7, 16, 0.0F);
        this.Body03 = new ModelRenderer(this, 0, 0);
        this.Body03.setRotationPoint(-20.6F, -7.0F, 8.0F);
        this.Body03.addBox(0.0F, 0.0F, 0.0F, 15, 7, 13, 0.0F);
        this.Body08 = new ModelRenderer(this, 0, 0);
        this.Body08.setRotationPoint(15.0F, -5.0F, -10.0F);
        this.Body08.addBox(0.0F, 0.0F, 0.0F, 8, 5, 18, 0.0F);
        this.Pillar01b_1 = new ModelRenderer(this, 0, 0);
        this.Pillar01b_1.setRotationPoint(0.0F, -9.0F, 0.5F);
        this.Pillar01b_1.addBox(-3.0F, -8.0F, -3.0F, 6, 8, 6, 0.0F);
        this.setRotateAngle(Pillar01b_1, -0.17453292519943295F, 0.0F, -0.17453292519943295F);
        this.Pillar03b = new ModelRenderer(this, 0, 0);
        this.Pillar03b.setRotationPoint(0.0F, -8.5F, 0.0F);
        this.Pillar03b.addBox(-3.0F, -6.0F, -3.0F, 6, 8, 6, 0.0F);
        this.setRotateAngle(Pillar03b, -0.17453292519943295F, 0.0F, 0.17453292519943295F);
        this.Body02 = new ModelRenderer(this, 0, 0);
        this.Body02.setRotationPoint(-7.0F, -1.5F, 11.0F);
        this.Body02.addBox(0.0F, -3.4F, 0.0F, 18, 5, 11, 0.0F);
        this.Pillar02a = new ModelRenderer(this, 0, 0);
        this.Pillar02a.setRotationPoint(8.0F, 2.0F, 6.0F);
        this.Pillar02a.addBox(-5.5F, -10.0F, -4.5F, 11, 10, 9, 0.0F);
        this.setRotateAngle(Pillar02a, 0.17453292519943295F, 0.0F, 0.17453292519943295F);
        this.Base02 = new ModelRenderer(this, 0, 0);
        this.Base02.setRotationPoint(8.0F, -1.0F, -24.0F);
        this.Base02.addBox(0.0F, 0.0F, 0.0F, 16, 7, 16, 0.0F);
        this.Base01 = new ModelRenderer(this, 0, 0);
        this.Base01.setRotationPoint(-8.0F, -2.0F, -24.0F);
        this.Base01.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16, 0.0F);
        this.Base05 = new ModelRenderer(this, 0, 0);
        this.Base05.setRotationPoint(-8.0F, -2.0F, 8.0F);
        this.Base05.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16, 0.0F);
        this.Body05 = new ModelRenderer(this, 0, 0);
        this.Body05.setRotationPoint(-20.0F, -5.0F, -22.0F);
        this.Body05.addBox(0.0F, 0.0F, 0.0F, 12, 5, 15, 0.0F);
        this.Pillar01a_1 = new ModelRenderer(this, 0, 0);
        this.Pillar01a_1.setRotationPoint(5.0F, 2.0F, 6.0F);
        this.Pillar01a_1.addBox(-4.0F, -10.0F, -4.0F, 9, 10, 9, 0.0F);
        this.setRotateAngle(Pillar01a_1, -0.17453292519943295F, 0.0F, -0.17453292519943295F);
        this.Pillar02b = new ModelRenderer(this, 0, 0);
        this.Pillar02b.setRotationPoint(2.0F, -10.6F, 0.5F);
        this.Pillar02b.addBox(-5.5F, -6.0F, -4.0F, 8, 8, 7, 0.0F);
        this.setRotateAngle(Pillar02b, 0.17453292519943295F, 0.0F, 0.17453292519943295F);
        this.Pillar01a = new ModelRenderer(this, 0, 0);
        this.Pillar01a.setRotationPoint(7.0F, 2.0F, 7.0F);
        this.Pillar01a.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, 0.0F);
        this.setRotateAngle(Pillar01a, 0.17453292519943295F, 0.0F, -0.17453292519943295F);
        this.Base03 = new ModelRenderer(this, 0, 0);
        this.Base03.setRotationPoint(8.0F, 0.0F, -8.0F);
        this.Base03.addBox(0.0F, 0.0F, 0.0F, 16, 6, 16, 0.0F);
        this.Base04 = new ModelRenderer(this, 0, 0);
        this.Base04.setRotationPoint(8.0F, -3.0F, 8.0F);
        this.Base04.addBox(0.0F, 0.0F, 0.0F, 16, 9, 16, 0.0F);
        this.Body04 = new ModelRenderer(this, 0, 0);
        this.Body04.setRotationPoint(-22.0F, -4.0F, -10.0F);
        this.Body04.addBox(0.0F, 0.0F, 0.0F, 12, 4, 20, 0.0F);
        this.Pillar01b = new ModelRenderer(this, 0, 0);
        this.Pillar01b.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Pillar01b.addBox(-3.0F, -13.3F, -3.0F, 7, 8, 7, 0.0F);
        this.setRotateAngle(Pillar01b, 0.17453292519943295F, 0.0F, -0.17453292519943295F);
        this.Body01 = new ModelRenderer(this, 0, 0);
        this.Body01.setRotationPoint(7.0F, -6.0F, 6.0F);
        this.Body01.addBox(0.0F, 0.0F, 0.0F, 14, 6, 14, 0.0F);
        this.Base07 = new ModelRenderer(this, 0, 0);
        this.Base07.setRotationPoint(-24.0F, -2.0F, -8.0F);
        this.Base07.addBox(0.0F, 0.0F, 0.0F, 16, 8, 16, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.BodyMain.addBox(0F, 0.0F, 0F, 0, 0, 0, 0.0F);
        this.Base00 = new ModelRenderer(this, 0, 0);
        this.Base00.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Base00.addBox(-24.0F, 0.0F, -24.0F, 16, 6, 16, 0.0F);
        this.Base08 = new ModelRenderer(this, 0, 0);
        this.Base08.setRotationPoint(-8.0F, 1.0F, -8.0F);
        this.Base08.addBox(0.0F, 0.0F, 0.0F, 16, 5, 16, 0.0F);
        this.Pillar03a = new ModelRenderer(this, 0, 0);
        this.Pillar03a.setRotationPoint(6.0F, 1.0F, 7.0F);
        this.Pillar03a.addBox(-4.5F, -8.0F, -5.0F, 9, 9, 10, 0.0F);
        this.setRotateAngle(Pillar03a, -0.17453292519943295F, 0.0F, 0.17453292519943295F);
        this.BodyMain.addChild(this.Base00);
        this.BodyMain.addChild(this.Body06);
        this.BodyMain.addChild(this.Body07);
        this.BodyMain.addChild(this.Base06);
        this.BodyMain.addChild(this.Body03);
        this.BodyMain.addChild(this.Body08);
        this.Pillar01a_1.addChild(this.Pillar01b_1);
        this.Pillar03a.addChild(this.Pillar03b);
        this.BodyMain.addChild(this.Body02);
        this.Body03.addChild(this.Pillar02a);
        this.BodyMain.addChild(this.Base02);
        this.BodyMain.addChild(this.Base01);
        this.BodyMain.addChild(this.Base05);
        this.BodyMain.addChild(this.Body05);
        this.Body07.addChild(this.Pillar01a_1);
        this.Pillar02a.addChild(this.Pillar02b);
        this.Body01.addChild(this.Pillar01a);
        this.BodyMain.addChild(this.Base03);
        this.BodyMain.addChild(this.Base04);
        this.BodyMain.addChild(this.Body04);
        this.Pillar01a.addChild(this.Pillar01b);
        this.BodyMain.addChild(this.Body01);
        this.BodyMain.addChild(this.Base07);
        this.BodyMain.addChild(this.Base08);
        this.Body05.addChild(this.Pillar03a);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
//    	GL11.glPushMatrix();		
//		GL11.glEnable(GL11.GL_BLEND);
//		GL11.glDisable(GL11.GL_LIGHTING);
//		GL11.glDepthMask(false);
//    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		GL11.glDisable(GL11.GL_CULL_FACE);
		
    	this.BodyMain.render(f5);
    	
//    	GL11.glDisable(GL11.GL_CULL_FACE);
//        GL11.glDepthMask(true);
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_BLEND);
//        GL11.glPopMatrix();
    }
    
    public void renderModel(float f5) {	
    	this.BodyMain.render(f5);
  
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
