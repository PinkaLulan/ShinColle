package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.handler.EventHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMountAfH - PinkaLulan 2015/5/19
 * Created using Tabula 4.1.1
 */
public class ModelMountAfH extends ModelBase
{
    public ModelRenderer BodyMain;
    public ModelRenderer ChestCannon01a;
    public ModelRenderer ChestCannon02a;
    public ModelRenderer ChestCannon03a;
    public ModelRenderer EquipBaseL;
    public ModelRenderer EquipBaseR;
    public ModelRenderer Neck;
    public ModelRenderer EquipL01;
    public ModelRenderer EquipL02;
    public ModelRenderer EquipCannonPlate;
    public ModelRenderer EquipCannon01;
    public ModelRenderer EquipCannon02;
    public ModelRenderer EquipR01;
    public ModelRenderer EquipR02;
    public ModelRenderer EquipCannonPlate_1;
    public ModelRenderer EquipCannon01_1;
    public ModelRenderer EquipCannon02_1;
    public ModelRenderer Head;
    public ModelRenderer Jaw;
    public ModelRenderer HeadBack01;
    public ModelRenderer NeckBack;
    public ModelRenderer HeadBack03;
    public ModelRenderer NeckFront;
    public ModelRenderer CannonBase;
    public ModelRenderer HeadTooth;
    public ModelRenderer HeadTooth2;
    public ModelRenderer JawTooth;
    public ModelRenderer JawTooth2;
    public ModelRenderer Cannon01;
    public ModelRenderer Cannon02;
    public ModelRenderer Cannon03;
    public ModelRenderer Cannon04;
    public ModelRenderer Cannon05;
    public ModelRenderer Cannon06;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowJaw;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowCannonBase;
    public ModelRenderer GlowCannon04;
    public ModelRenderer GlowEquipBaseL;
    public ModelRenderer GlowEquipL01;
    public ModelRenderer GlowEquipL02;
    public ModelRenderer GlowEquipBaseR;
    public ModelRenderer GlowEquipR01;

    
    public ModelMountAfH()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.EquipCannon02_1 = new ModelRenderer(this, 73, 0);
        this.EquipCannon02_1.setRotationPoint(1.5F, 4.0F, 0.5F);
        this.EquipCannon02_1.addBox(0.0F, 0.0F, -7.0F, 1, 1, 7, 0.0F);
        this.setRotateAngle(EquipCannon02_1, 0.18203784098300857F, 0.091106186954104F, 0.0F);
        this.EquipCannon02 = new ModelRenderer(this, 73, 0);
        this.EquipCannon02.setRotationPoint(1.5F, 4.0F, 0.5F);
        this.EquipCannon02.addBox(0.0F, 0.0F, -7.0F, 1, 1, 7, 0.0F);
        this.setRotateAngle(EquipCannon02, 0.0F, -0.08726646259971647F, 0.0F);
        this.EquipR02 = new ModelRenderer(this, 0, 0);
        this.EquipR02.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.EquipR02.addBox(-5.5F, 0.0F, -9.0F, 11, 4, 23, 0.0F);
        this.setRotateAngle(EquipR02, 0.05235987755982988F, 0.0F, 0.0F);
        this.Cannon01 = new ModelRenderer(this, 0, 0);
        this.Cannon01.setRotationPoint(2.0F, -9.0F, 0.0F);
        this.Cannon01.addBox(0.0F, 0.0F, -10.0F, 3, 3, 10, 0.0F);
        this.HeadTooth = new ModelRenderer(this, 62, 98);
        this.HeadTooth.setRotationPoint(0.0F, 2.0F, -15.0F);
        this.HeadTooth.addBox(-9.0F, 0.0F, -6.5F, 18, 4, 15, 0.0F);
        this.setRotateAngle(HeadTooth, 0.05235987755982988F, 0.0F, 0.0F);
        this.EquipL01 = new ModelRenderer(this, 0, 0);
        this.EquipL01.setRotationPoint(0.0F, -8.0F, 1.0F);
        this.EquipL01.addBox(-6.0F, 0.0F, -7.0F, 10, 9, 20, 0.0F);
        this.setRotateAngle(EquipL01, -0.13962634015954636F, 0.0F, 0.0F);
        this.Cannon02 = new ModelRenderer(this, 0, 0);
        this.Cannon02.setRotationPoint(-3.0F, -9.0F, 0.0F);
        this.Cannon02.addBox(0.0F, 0.0F, -10.0F, 3, 3, 10, 0.0F);
        this.JawTooth = new ModelRenderer(this, 63, 99);
        this.JawTooth.setRotationPoint(0.0F, -1.6F, -0.3F);
        this.JawTooth.addBox(-9.0F, 0.0F, -14.0F, 18, 3, 14, 0.0F);
        this.setRotateAngle(JawTooth, -0.08726646259971647F, -0.02234021442552742F, 0.0F);
        this.Cannon05 = new ModelRenderer(this, 0, 0);
        this.Cannon05.setRotationPoint(0.0F, -14.6F, 0.5F);
        this.Cannon05.addBox(0.0F, 0.0F, -10.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(Cannon05, -0.05235987755982988F, 0.0F, 0.0F);
        this.CannonBase = new ModelRenderer(this, 0, 0);
        this.CannonBase.setRotationPoint(-1.0F, -16.0F, 7.0F);
        this.CannonBase.addBox(-4.0F, -14.0F, 0.0F, 10, 14, 4, 0.0F);
        this.setRotateAngle(CannonBase, -0.5235987755982988F, 0.0F, 0.0F);
        this.ChestCannon01a = new ModelRenderer(this, 0, 0);
        this.ChestCannon01a.setRotationPoint(3.3F, 6.0F, -2.0F);
        this.ChestCannon01a.addBox(0.0F, 0.0F, 0.0F, 5, 5, 17, 0.0F);
        this.setRotateAngle(ChestCannon01a, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipBaseL = new ModelRenderer(this, 0, 10);
        this.EquipBaseL.setRotationPoint(14.5F, 2.0F, 5.0F);
        this.EquipBaseL.addBox(-6.0F, 0.0F, -10.0F, 11, 6, 21, 0.0F);
        this.setRotateAngle(EquipBaseL, 0.0F, -0.08726646259971647F, 0.0F);
        this.Cannon04 = new ModelRenderer(this, 0, 0);
        this.Cannon04.setRotationPoint(1.0F, -13.5F, 0.0F);
        this.Cannon04.addBox(0.0F, 0.0F, -13.0F, 4, 4, 13, 0.0F);
        this.setRotateAngle(Cannon04, 0.0F, 0.0F, 0.7853981633974483F);
        this.Cannon03 = new ModelRenderer(this, 0, 0);
        this.Cannon03.setRotationPoint(-3.5F, -11.3F, 0.0F);
        this.Cannon03.addBox(0.0F, 0.0F, -9.0F, 2, 2, 9, 0.0F);
        this.HeadBack03 = new ModelRenderer(this, 0, 0);
        this.HeadBack03.setRotationPoint(-4.0F, -16.1F, 14.5F);
        this.HeadBack03.addBox(0.0F, 0.0F, 0.0F, 8, 18, 8, 0.0F);
        this.setRotateAngle(HeadBack03, 0.091106186954104F, 0.0F, 0.0F);
        this.ChestCannon03a = new ModelRenderer(this, 0, 0);
        this.ChestCannon03a.setRotationPoint(-8.3F, 6.0F, -2.0F);
        this.ChestCannon03a.addBox(0.0F, 0.0F, 0.0F, 5, 5, 17, 0.0F);
        this.setRotateAngle(ChestCannon03a, 0.08726646259971647F, 0.0F, 0.0F);
        this.NeckBack = new ModelRenderer(this, 0, 0);
        this.NeckBack.setRotationPoint(-2.0F, -6.0F, 11.0F);
        this.NeckBack.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
        this.Cannon06 = new ModelRenderer(this, 74, 0);
        this.Cannon06.setRotationPoint(1.0F, 1.0F, -13.0F);
        this.Cannon06.addBox(0.0F, 0.0F, -15.0F, 2, 2, 15, 0.0F);
        this.HeadBack01 = new ModelRenderer(this, 0, 0);
        this.HeadBack01.setRotationPoint(-4.0F, -18.0F, 8.0F);
        this.HeadBack01.addBox(0.0F, 0.0F, 0.0F, 8, 8, 11, 0.0F);
        this.setRotateAngle(HeadBack01, -0.13962634015954636F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 70, 58);
        this.Neck.setRotationPoint(-29.0F, 5.0F, 6.0F);
        this.Neck.addBox(-7.5F, -15.0F, -3.0F, 15, 15, 14, 0.0F);
        this.setRotateAngle(Neck, 0.0F, 0.2617993877991494F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 94);
        this.Head.setRotationPoint(0.0F, -15.0F, 7.0F);
        this.Head.addBox(-9.5F, -7.0F, -22.0F, 19, 10, 24, 0.0F);
        this.setRotateAngle(Head, -0.20943951023931953F, 0.0F, 0.0F);
        this.EquipBaseR = new ModelRenderer(this, 0, 10);
        this.EquipBaseR.setRotationPoint(-13.5F, 2.0F, 5.0F);
        this.EquipBaseR.addBox(-6.0F, 0.0F, -10.0F, 11, 6, 21, 0.0F);
        this.setRotateAngle(EquipBaseR, 0.0F, 0.08726646259971647F, 0.0F);
        this.EquipCannon01_1 = new ModelRenderer(this, 73, 0);
        this.EquipCannon01_1.setRotationPoint(1.5F, 1.0F, 0.5F);
        this.EquipCannon01_1.addBox(0.0F, 0.0F, -7.0F, 1, 1, 7, 0.0F);
        this.setRotateAngle(EquipCannon01_1, -0.18203784098300857F, 0.136659280431156F, 0.0F);
        this.HeadTooth2 = new ModelRenderer(this, 65, 99);
        this.HeadTooth2.mirror = true;
        this.HeadTooth2.setRotationPoint(0.0F, 3.6F, -6.5F);
        this.HeadTooth2.addBox(-8.0F, 0.0F, -14.0F, 16, 3, 14, 0.0F);
        this.setRotateAngle(HeadTooth2, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipR01 = new ModelRenderer(this, 0, 0);
        this.EquipR01.setRotationPoint(0.0F, -8.0F, 1.0F);
        this.EquipR01.addBox(-5.0F, 0.0F, -7.0F, 10, 9, 20, 0.0F);
        this.setRotateAngle(EquipR01, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipCannonPlate_1 = new ModelRenderer(this, 0, 0);
        this.EquipCannonPlate_1.setRotationPoint(-2.0F, 2.0F, -8.0F);
        this.EquipCannonPlate_1.addBox(0.0F, 0.0F, 0.0F, 4, 6, 1, 0.0F);
        this.EquipCannonPlate = new ModelRenderer(this, 0, 0);
        this.EquipCannonPlate.setRotationPoint(-2.0F, 2.0F, -8.0F);
        this.EquipCannonPlate.addBox(0.0F, 0.0F, 0.0F, 4, 6, 1, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 68);
        this.Jaw.mirror = true;
        this.Jaw.setRotationPoint(0.0F, -3.0F, 0.5F);
        this.Jaw.addBox(-9.5F, 0.0F, -15.0F, 19, 7, 19, 0.0F);
        this.setRotateAngle(Jaw, 0.5462880558742251F, 0.0F, 0.0F);
        this.EquipCannon01 = new ModelRenderer(this, 73, 0);
        this.EquipCannon01.setRotationPoint(1.5F, 1.0F, 0.5F);
        this.EquipCannon01.addBox(0.0F, 0.0F, -7.0F, 1, 1, 7, 0.0F);
        this.setRotateAngle(EquipCannon01, -0.31869712141416456F, -0.08726646259971647F, 0.0F);
        this.NeckFront = new ModelRenderer(this, 0, 52);
        this.NeckFront.setRotationPoint(0.0F, -14.0F, -5.0F);
        this.NeckFront.addBox(-6.5F, 0.0F, 0.0F, 13, 14, 2, 0.0F);
        this.EquipL02 = new ModelRenderer(this, 0, 0);
        this.EquipL02.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.EquipL02.addBox(-6.5F, 0.0F, -9.0F, 11, 4, 23, 0.0F);
        this.setRotateAngle(EquipL02, 0.05235987755982988F, 0.0F, 0.0F);
        this.ChestCannon02a = new ModelRenderer(this, 0, 0);
        this.ChestCannon02a.setRotationPoint(-2.5F, 6.0F, -2.0F);
        this.ChestCannon02a.addBox(0.0F, 0.0F, 0.0F, 5, 5, 17, 0.0F);
        this.setRotateAngle(ChestCannon02a, 0.08726646259971647F, 0.0F, 0.0F);
        this.JawTooth2 = new ModelRenderer(this, 66, 100);
        this.JawTooth2.mirror = true;
        this.JawTooth2.setRotationPoint(0.0F, -2.6F, 0.0F);
        this.JawTooth2.addBox(-8.0F, 0.0F, -13.0F, 16, 3, 13, 0.0F);
        this.setRotateAngle(JawTooth2, -0.13962634015954636F, 0.0F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain.addBox(-9.0F, -7.0F, 10.0F, 18, 12, 9, 0.0F);
        this.EquipR01.addChild(this.EquipR02);
        this.CannonBase.addChild(this.Cannon01);
        this.EquipBaseL.addChild(this.EquipL01);
        this.CannonBase.addChild(this.Cannon02);
        this.CannonBase.addChild(this.Cannon05);
        this.Neck.addChild(this.CannonBase);
        this.BodyMain.addChild(this.ChestCannon01a);
        this.BodyMain.addChild(this.EquipBaseL);
        this.CannonBase.addChild(this.Cannon04);
        this.CannonBase.addChild(this.Cannon03);
        this.Neck.addChild(this.HeadBack03);
        this.BodyMain.addChild(this.ChestCannon03a);
        this.Neck.addChild(this.NeckBack);
        this.Neck.addChild(this.HeadBack01);
        this.BodyMain.addChild(this.Neck);
        this.Neck.addChild(this.Head);
        this.BodyMain.addChild(this.EquipBaseR);
        this.EquipBaseR.addChild(this.EquipR01);
        this.Neck.addChild(this.Jaw);
        this.EquipL01.addChild(this.EquipL02);
        this.BodyMain.addChild(this.ChestCannon02a);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(-29.0F, 5.0F, 6.0F);
        this.setRotateAngle(GlowNeck, 0.0F, 0.2617993877991494F, 0.0F);
        this.GlowJaw = new ModelRenderer(this, 0, 0);
        this.GlowJaw.setRotationPoint(0.0F, -3.0F, 0.5F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -15.0F, 7.0F);
        this.setRotateAngle(GlowHead, -0.20943951023931953F, 0.0F, 0.0F);
        this.GlowCannonBase = new ModelRenderer(this, 0, 0);
        this.GlowCannonBase.setRotationPoint(-1.0F, -16.0F, 7.0F);
        this.setRotateAngle(GlowCannonBase, -0.5235987755982988F, 0.0F, 0.0F);
        this.GlowCannon04 = new ModelRenderer(this, 0, 0);
        this.GlowCannon04.setRotationPoint(1.0F, -13.5F, 0.0F);
        this.setRotateAngle(GlowCannon04, 0.0F, 0.0F, 0.7853981633974483F);
        this.GlowEquipBaseL = new ModelRenderer(this, 0, 0);
        this.GlowEquipBaseL.setRotationPoint(14.5F, 2.0F, 5.0F);
        this.setRotateAngle(GlowEquipBaseL, 0.0F, -0.08726646259971647F, 0.0F);
        this.GlowEquipL01 = new ModelRenderer(this, 0, 0);
        this.GlowEquipL01.setRotationPoint(0.0F, -8.0F, 1.0F);
        this.setRotateAngle(GlowEquipL01, -0.13962634015954636F, 0.0F, 0.0F);
        this.GlowEquipL02 = new ModelRenderer(this, 0, 0);
        this.GlowEquipL02.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.setRotateAngle(GlowEquipL02, 0.05235987755982988F, 0.0F, 0.0F);
        this.GlowEquipBaseR = new ModelRenderer(this, 0, 0);
        this.GlowEquipBaseR.setRotationPoint(-13.5F, 2.0F, 5.0F);
        this.setRotateAngle(GlowEquipBaseR, 0.0F, 0.08726646259971647F, 0.0F);
        this.GlowEquipR01 = new ModelRenderer(this, 0, 0);
        this.GlowEquipR01.setRotationPoint(0.0F, -8.0F, 1.0F);
        this.setRotateAngle(GlowEquipR01, -0.13962634015954636F, 0.0F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck); 
        this.GlowNeck.addChild(this.GlowJaw);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowNeck.addChild(this.NeckFront);
        this.GlowHead.addChild(this.HeadTooth);
        this.GlowHead.addChild(this.HeadTooth2);
        this.GlowJaw.addChild(this.JawTooth);
        this.GlowJaw.addChild(this.JawTooth2);
        
        this.GlowNeck.addChild(this.GlowCannonBase);
        this.GlowCannonBase.addChild(this.GlowCannon04);
        this.GlowCannon04.addChild(this.Cannon06);
        
        this.GlowBodyMain.addChild(this.GlowEquipBaseL);
        this.GlowEquipBaseL.addChild(this.GlowEquipL01);
        this.GlowEquipL01.addChild(this.GlowEquipL02);
        this.GlowEquipL01.addChild(this.EquipCannonPlate);
        this.EquipCannonPlate.addChild(this.EquipCannon01);
        this.EquipCannonPlate.addChild(this.EquipCannon02);
        
        this.GlowBodyMain.addChild(this.GlowEquipBaseR);
        this.GlowEquipBaseR.addChild(this.GlowEquipR01);
        this.GlowEquipR01.addChild(this.EquipCannonPlate_1);
        this.EquipCannonPlate_1.addChild(this.EquipCannon01_1);
        this.EquipCannonPlate_1.addChild(this.EquipCannon02_1);
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
    	//FIX: head rotation bug while riding
    	if (f3 <= -180F) { f3 += 360F; }
    	else if (f3 >= 180F) { f3 -= 360F; }
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.scale(0.7F, 0.7F, 0.7F);
    	GlStateManager.translate(0F, 1.10F, -0.47F);
    	
    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
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
    
    //for idle/run animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		IShipEmotion ent = (IShipEmotion)entity;
		  
		motionHumanPos(f, f1, f2, f3, f4, ent);
    }
    
    //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
  	{   
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.025F + 0.025F, 0F);
    	}

	    //正常站立動作
	  	//嘴巴
	  	this.Jaw.rotateAngleX = angleX * 0.1F + 0.4F;
	  	this.GlowJaw.rotateAngleX = this.Jaw.rotateAngleX;
	    //cannon
	    this.EquipCannon01.rotateAngleX = angleX * 0.08F - 0.32F;
	    this.EquipCannon02.rotateAngleX = -angleX * 0.14F;
	    this.EquipCannon01_1.rotateAngleX = -angleX * 0.12F - 0.18F;
	    this.EquipCannon02_1.rotateAngleX = angleX * 0.08F + 0.18F;
	    
	    if (ent.getStateEmotion(ID.S.Emotion) > 0)
	    {
//	    	this.ArmRight01.rotateAngleX = -1.57F;
	    }
  	}


}