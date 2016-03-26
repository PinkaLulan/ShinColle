package com.lulan.shincolle.client.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;

/**
 * ModelMountHbH - PinkaLulan 2015/6/8
 * Created using Tabula 4.1.1
 */
public class ModelMountHbH extends ModelBase {
    public ModelRenderer BodyMain;
    public ModelRenderer EquipBaseR;
    public ModelRenderer Back01;
    public ModelRenderer Back02;
    public ModelRenderer EquipBaseL;
    public ModelRenderer EquipR01;
    public ModelRenderer Back01b;
    public ModelRenderer Back02b;
    public ModelRenderer Back02c;
    public ModelRenderer Back02d;
    public ModelRenderer Back02e;
    public ModelRenderer Neck;
    public ModelRenderer Head;
    public ModelRenderer Jaw;
    public ModelRenderer HeadTooth;
    public ModelRenderer Road01;
    public ModelRenderer Road02;
    public ModelRenderer Road03;
    public ModelRenderer Road04;
    public ModelRenderer Road05;
    public ModelRenderer JawTooth;
    public ModelRenderer EquipL01;
    public ModelRenderer EquipCannonPlate;
    public ModelRenderer CanonBase;
    public ModelRenderer EquipCannon01;
    public ModelRenderer Neck_1;
    public ModelRenderer Head_1;
    public ModelRenderer Jaw_1;
    public ModelRenderer Road01u;
    public ModelRenderer Road01v;
    public ModelRenderer HeadTooth_1;
    public ModelRenderer JawTooth_1;
    public ModelRenderer Road02u;
    public ModelRenderer Road03u;
    public ModelRenderer Road02v;
    public ModelRenderer Road03v;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowEquipBaseL;
    public ModelRenderer GlowEquipL01;
    public ModelRenderer GlowEquipCannonPlate;
    public ModelRenderer GlowBack02;
    public ModelRenderer GlowBack02b;
    public ModelRenderer GlowBack02c;
    public ModelRenderer GlowBack02d;
    public ModelRenderer GlowBack02e;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowJaw;
    public ModelRenderer GlowCanonBase;
    public ModelRenderer GlowNeck_1;
    public ModelRenderer GlowHead_1;
    public ModelRenderer GlowJaw_1;
    
    private Random rand = new Random();
    private int startEmo2 = 0;

    public ModelMountHbH() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.Neck = new ModelRenderer(this, 0, 37);
        this.Neck.setRotationPoint(0.0F, 0.0F, -10.0F);
        this.Neck.addBox(-4.0F, -4.0F, -7.0F, 8, 8, 9, 0.0F);
        this.setRotateAngle(Neck, -0.17453292519943295F, 0.08726646259971647F, -0.08726646259971647F);
        this.Back02e = new ModelRenderer(this, 0, 0);
        this.Back02e.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.Back02e.addBox(-4.0F, -4.0F, -9.0F, 8, 8, 9, 0.0F);
        this.setRotateAngle(Back02e, 0.3490658503988659F, -0.3490658503988659F, 0.0F);
        this.JawTooth_1 = new ModelRenderer(this, 22, 46);
        this.JawTooth_1.setRotationPoint(0.1F, -0.6F, -0.3F);
        this.JawTooth_1.addBox(-4.5F, 0.0F, -14.0F, 9, 3, 12, 0.0F);
        this.setRotateAngle(JawTooth_1, -0.20943951023931953F, 0.0F, 0.0F);
        this.Jaw_1 = new ModelRenderer(this, 0, 0);
        this.Jaw_1.mirror = true;
        this.Jaw_1.setRotationPoint(0.0F, -5.0F, 3.0F);
        this.Jaw_1.addBox(-5.0F, -1.0F, -15.0F, 10, 4, 13, 0.0F);
        this.setRotateAngle(Jaw_1, 0.8726646259971648F, 0.0F, 0.0F);
        this.Back01 = new ModelRenderer(this, 29, 22);
        this.Back01.setRotationPoint(1.0F, -7.0F, 19.0F);
        this.Back01.addBox(0.0F, 0.0F, 0.0F, 13, 10, 13, 0.0F);
        this.setRotateAngle(Back01, 0.0F, 0.13962634015954636F, 0.0F);
        this.CanonBase = new ModelRenderer(this, 0, 21);
        this.CanonBase.setRotationPoint(-3.5F, -9.0F, 3.0F);
        this.CanonBase.addBox(0.0F, 0.0F, 0.0F, 7, 9, 7, 0.0F);
        this.Road03v = new ModelRenderer(this, 86, 16);
        this.Road03v.setRotationPoint(0.0F, 0.0F, -12.0F);
        this.Road03v.addBox(-4.5F, 0.0F, -12.0F, 9, 1, 12, 0.0F);
        this.Road03 = new ModelRenderer(this, 55, 0);
        this.Road03.setRotationPoint(0.4F, 0.1F, 12.0F);
        this.Road03.addBox(-5.5F, 0.0F, 0.0F, 11, 1, 14, 0.0F);
        this.setRotateAngle(Road03, 0.08726646259971647F, -0.36425021489121656F, -0.017453292519943295F);
        this.HeadTooth = new ModelRenderer(this, 22, 46);
        this.HeadTooth.setRotationPoint(0.0F, -2.0F, -8.0F);
        this.HeadTooth.addBox(-4.5F, 0.0F, -6.5F, 9, 4, 12, 0.0F);
        this.setRotateAngle(HeadTooth, 0.17453292519943295F, 0.0F, 0.0F);
        this.Road02u = new ModelRenderer(this, 86, 16);
        this.Road02u.setRotationPoint(0.0F, 0.0F, -12.0F);
        this.Road02u.addBox(-4.5F, 0.0F, -12.0F, 9, 1, 12, 0.0F);
        this.Back01b = new ModelRenderer(this, 29, 22);
        this.Back01b.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.Back01b.addBox(0.0F, 0.0F, 0.0F, 13, 10, 13, 0.0F);
        this.Back02b = new ModelRenderer(this, 29, 22);
        this.Back02b.setRotationPoint(-14.0F, -10.0F, 0.0F);
        this.Back02b.addBox(0.0F, 0.0F, 0.0F, 13, 10, 13, 0.0F);
        this.Neck_1 = new ModelRenderer(this, 0, 37);
        this.Neck_1.setRotationPoint(3.5F, -1.0F, 3.0F);
        this.Neck_1.addBox(-4.0F, -6.0F, -0.5F, 8, 8, 9, 0.0F);
        this.setRotateAngle(Neck_1, -0.2617993877991494F, -0.08726646259971647F, 0.0F);
        this.Head_1 = new ModelRenderer(this, 0, 0);
        this.Head_1.setRotationPoint(0.1F, -2.5F, 3.0F);
        this.Head_1.addBox(-5.0F, -4.0F, -17.0F, 10, 4, 17, 0.0F);
        this.setRotateAngle(Head_1, -0.36425021489121656F, 0.0F, 0.0F);
        this.EquipBaseL = new ModelRenderer(this, 64, 30);
        this.EquipBaseL.setRotationPoint(14.5F, 2.0F, 5.0F);
        this.EquipBaseL.addBox(-6.0F, -4.0F, -7.0F, 11, 11, 21, 0.0F);
        this.setRotateAngle(EquipBaseL, 0.0F, -0.05235987755982988F, 0.0F);
        this.Road01u = new ModelRenderer(this, 86, 16);
        this.Road01u.setRotationPoint(0.0F, -4.7F, -3.0F);
        this.Road01u.addBox(-4.5F, 0.0F, -12.0F, 9, 1, 12, 0.0F);
        this.setRotateAngle(Road01u, 0F, 0.0F, 0.0F);
        this.EquipCannon01 = new ModelRenderer(this, 47, 0);
        this.EquipCannon01.setRotationPoint(1.5F, 1.0F, 0.5F);
        this.EquipCannon01.addBox(0.0F, 0.0F, -7.0F, 1, 1, 7, 0.0F);
        this.setRotateAngle(EquipCannon01, -0.31869712141416456F, -0.08726646259971647F, 0.0F);
        this.Road01v = new ModelRenderer(this, 86, 16);
        this.Road01v.setRotationPoint(0.0F, 3.2F, -2.4F);
        this.Road01v.addBox(-4.5F, 0.0F, -12.0F, 9, 1, 12, 0.0F);
        this.setRotateAngle(Road01v, -0.0349F, 0.0F, -3.141592653589793F);
        this.EquipCannonPlate = new ModelRenderer(this, 0, 0);
        this.EquipCannonPlate.setRotationPoint(-3.0F, 1.8F, -7.5F);
        this.EquipCannonPlate.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
        this.EquipL01 = new ModelRenderer(this, 66, 31);
        this.EquipL01.setRotationPoint(0.5F, -8.0F, 1.0F);
        this.EquipL01.addBox(-6.0F, 0.0F, -7.0F, 10, 4, 20, 0.0F);
        this.Back02 = new ModelRenderer(this, 29, 22);
        this.Back02.setRotationPoint(0.0F, -7.0F, 19.0F);
        this.Back02.addBox(-14.0F, 0.0F, 0.0F, 13, 10, 13, 0.0F);
        this.setRotateAngle(Back02, 0.0F, -0.13962634015954636F, 0.0F);
        this.Road02 = new ModelRenderer(this, 55, 0);
        this.Road02.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.Road02.addBox(-5.5F, 0.0F, 0.0F, 11, 1, 14, 0.0F);
        this.EquipBaseR = new ModelRenderer(this, 64, 30);
        this.EquipBaseR.setRotationPoint(-13.5F, 2.0F, 5.0F);
        this.EquipBaseR.addBox(-6.0F, -4.0F, -7.0F, 11, 11, 21, 0.0F);
        this.setRotateAngle(EquipBaseR, 0.0F, 0.05235987755982988F, 0.0F);
        this.EquipR01 = new ModelRenderer(this, 66, 31);
        this.EquipR01.setRotationPoint(-0.5F, -8.0F, 1.0F);
        this.EquipR01.addBox(-5.0F, 0.0F, -7.0F, 10, 4, 20, 0.0F);
        this.Road05 = new ModelRenderer(this, 55, 0);
        this.Road05.setRotationPoint(0.0F, 0.0F, 14.0F);
        this.Road05.addBox(-5.5F, 0.0F, 0.0F, 11, 1, 14, 0.0F);
        this.setRotateAngle(Road05, -0.6981317007977318F, 0.0F, 0.0F);
        this.Road01 = new ModelRenderer(this, 55, 0);
        this.Road01.setRotationPoint(0.0F, -5.0F, -23.0F);
        this.Road01.addBox(-5.5F, 0.0F, 0.0F, 11, 1, 14, 0.0F);
        this.Back02d = new ModelRenderer(this, 0, 0);
        this.Back02d.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.Back02d.addBox(-4.0F, -4.0F, -9.0F, 8, 8, 9, 0.0F);
        this.setRotateAngle(Back02d, 0.5235987755982988F, -0.6981317007977318F, -0.2617993877991494F);
        this.Back02c = new ModelRenderer(this, 0, 0);
        this.Back02c.setRotationPoint(3.5F, 2.0F, 8.0F);
        this.Back02c.addBox(-4.0F, -4.0F, -9.0F, 8, 8, 9, 0.0F);
        this.setRotateAngle(Back02c, -0.44F, 1.22F, 0.0F);
        this.Road02v = new ModelRenderer(this, 86, 16);
        this.Road02v.setRotationPoint(0.0F, 0.0F, -12.0F);
        this.Road02v.addBox(-4.5F, 0.0F, -12.0F, 9, 1, 12, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain.addBox(-9.0F, -2.0F, 14.0F, 18, 10, 9, 0.0F);
        this.JawTooth = new ModelRenderer(this, 22, 46);
        this.JawTooth.setRotationPoint(0.0F, -1.6F, -0.3F);
        this.JawTooth.addBox(-4.5F, 0.0F, -14.0F, 9, 3, 12, 0.0F);
        this.setRotateAngle(JawTooth, -0.17453292519943295F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -1.0F, -4.0F);
        this.Head.addBox(-5.0F, -4.0F, -17.0F, 10, 4, 17, 0.0F);
        this.setRotateAngle(Head, 0.08726646259971647F, 0.0F, 0.0F);
        this.HeadTooth_1 = new ModelRenderer(this, 22, 46);
        this.HeadTooth_1.setRotationPoint(0.0F, -2.0F, -8.0F);
        this.HeadTooth_1.addBox(-4.5F, 0.0F, -6.5F, 9, 4, 12, 0.0F);
        this.setRotateAngle(HeadTooth_1, 0.17453292519943295F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 0, 0);
        this.Jaw.mirror = true;
        this.Jaw.setRotationPoint(0.0F, -0.8F, -3.0F);
        this.Jaw.addBox(-5.0F, -1.0F, -15.0F, 10, 4, 14, 0.0F);
        this.setRotateAngle(Jaw, 0.6283185307179586F, 0.0F, 0.0F);
        this.Road04 = new ModelRenderer(this, 55, 0);
        this.Road04.setRotationPoint(-2.6F, 0.1F, 10.0F);
        this.Road04.addBox(-5.5F, 0.0F, 0.0F, 10, 1, 14, 0.0F);
        this.setRotateAngle(Road04, 0.03839724354387525F, 0.8651597102135892F, 0.013962634015954637F);
        this.Road03u = new ModelRenderer(this, 86, 16);
        this.Road03u.setRotationPoint(0.0F, 0.0F, -12.0F);
        this.Road03u.addBox(-4.5F, 0.0F, -12.0F, 9, 1, 12, 0.0F);
        this.Neck_1.addChild(this.Jaw_1);
        this.BodyMain.addChild(this.Back01);
        this.EquipL01.addChild(this.CanonBase);
        this.Back01.addChild(this.Back01b);
        this.Back02.addChild(this.Back02b);
        this.CanonBase.addChild(this.Neck_1);
        this.Neck_1.addChild(this.Head_1);
        this.BodyMain.addChild(this.EquipBaseL);
        this.EquipL01.addChild(this.EquipCannonPlate);
        this.EquipBaseL.addChild(this.EquipL01);
        this.BodyMain.addChild(this.Back02);
        this.BodyMain.addChild(this.EquipBaseR);
        this.EquipBaseR.addChild(this.EquipR01);
        this.Back02b.addChild(this.Back02c);
        this.Back02c.addChild(this.Back02d);
        this.Back02d.addChild(this.Back02e);
        this.Back02e.addChild(this.Neck);
        this.Neck.addChild(this.Head);
        this.Neck.addChild(this.Jaw);
        
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GlowEquipBaseL = new ModelRenderer(this, 64, 30);
        this.GlowEquipBaseL.setRotationPoint(14.5F, 2.0F, 5.0F);
        this.setRotateAngle(GlowEquipBaseL, 0.0F, -0.05235987755982988F, 0.0F);
        this.GlowEquipL01 = new ModelRenderer(this, 0, 0);
        this.GlowEquipL01.setRotationPoint(0.5F, -8.0F, 1.0F);
        this.GlowEquipCannonPlate = new ModelRenderer(this, 0, 0);
        this.GlowEquipCannonPlate.setRotationPoint(-3.0F, 1.8F, -7.5F);
        this.GlowEquipCannonPlate.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
        this.GlowBack02 = new ModelRenderer(this, 0, 0);
        this.GlowBack02.setRotationPoint(0.0F, -7.0F, 19.0F);
        this.setRotateAngle(GlowBack02, 0.0F, -0.13962634015954636F, 0.0F);
        this.GlowBack02b = new ModelRenderer(this, 0, 0);
        this.GlowBack02b.setRotationPoint(-14.0F, -10.0F, 0.0F);
        this.GlowBack02c = new ModelRenderer(this, 0, 0);
        this.GlowBack02c.setRotationPoint(3.5F, 2.0F, 8.0F);
        this.setRotateAngle(GlowBack02c, -0.44F, 1.22F, 0.0F);
        this.GlowBack02d = new ModelRenderer(this, 0, 0);
        this.GlowBack02d.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.setRotateAngle(GlowBack02d, 0.5235987755982988F, -0.6981317007977318F, -0.2617993877991494F);
        this.GlowBack02e = new ModelRenderer(this, 0, 0);
        this.GlowBack02e.setRotationPoint(0.0F, 0.0F, -8.0F);
        this.setRotateAngle(GlowBack02e, 0.3490658503988659F, -0.3490658503988659F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, 0.0F, -10.0F);
        this.setRotateAngle(GlowNeck, -0.17453292519943295F, 0.08726646259971647F, -0.08726646259971647F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.0F, -4.0F);
        this.setRotateAngle(GlowHead, 0.08726646259971647F, 0.0F, 0.0F);
        this.GlowJaw = new ModelRenderer(this, 0, 0);
        this.GlowJaw.setRotationPoint(0.0F, -0.8F, -3.0F);
        this.setRotateAngle(GlowJaw, 0.6283185307179586F, 0.0F, 0.0F);
        this.GlowCanonBase = new ModelRenderer(this, 0, 0);
        this.GlowCanonBase.setRotationPoint(-3.5F, -9.0F, 3.0F);
        this.GlowNeck_1 = new ModelRenderer(this, 0, 0);
        this.GlowNeck_1.setRotationPoint(3.5F, -1.0F, 3.0F);
        this.setRotateAngle(GlowNeck_1, -0.2617993877991494F, -0.08726646259971647F, 0.0F);
        this.GlowHead_1 = new ModelRenderer(this, 0, 0);
        this.GlowHead_1.setRotationPoint(0.1F, -2.5F, 3.0F);
        this.setRotateAngle(GlowHead_1, -0.36425021489121656F, 0.0F, 0.0F);
        this.GlowJaw_1 = new ModelRenderer(this, 0, 0);
        this.GlowJaw_1.setRotationPoint(0.0F, -5.0F, 3.0F);
        this.setRotateAngle(GlowJaw_1, 0.8726646259971648F, 0.0F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowEquipBaseL);
        this.GlowEquipBaseL.addChild(this.GlowEquipL01);
        this.GlowEquipL01.addChild(this.GlowEquipCannonPlate);
        this.GlowEquipCannonPlate.addChild(this.EquipCannon01);
        
        this.GlowBodyMain.addChild(this.GlowBack02);
        this.GlowBack02.addChild(this.GlowBack02b);
        this.GlowBack02b.addChild(this.GlowBack02c);
        this.GlowBack02c.addChild(this.GlowBack02d);
        this.GlowBack02d.addChild(this.GlowBack02e);
        this.GlowBack02e.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowHead.addChild(this.HeadTooth);
        this.GlowNeck.addChild(this.GlowJaw);
        this.GlowJaw.addChild(this.JawTooth);
        
        this.GlowHead.addChild(this.Road01);
        this.Road01.addChild(this.Road02);
        this.Road02.addChild(this.Road03);
        this.Road03.addChild(this.Road04);
        this.Road04.addChild(this.Road05);
        
        this.GlowEquipBaseL.addChild(this.GlowEquipL01);
        this.GlowEquipL01.addChild(this.GlowCanonBase);
        this.GlowCanonBase.addChild(this.GlowNeck_1);
        this.GlowNeck_1.addChild(this.GlowHead_1);
        this.GlowHead_1.addChild(this.HeadTooth_1);
        this.GlowNeck_1.addChild(this.GlowJaw_1);
        this.GlowJaw_1.addChild(this.JawTooth_1);
        
        this.GlowNeck_1.addChild(this.Road01u);
        this.Road01u.addChild(this.Road02u);
        this.Road02u.addChild(this.Road03u);
        
        this.GlowNeck_1.addChild(this.Road01v);
        this.Road01v.addChild(this.Road02v);
        this.Road02v.addChild(this.Road03v);
        
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
    	GL11.glScalef(0.8F, 0.8F, 0.8F);

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
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		IShipEmotion ent = (IShipEmotion)entity;
		  
		motionHumanPos(f, f1, f2, f3, f4, ent);

    }
    
  //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {   
  		float angleX = MathHelper.cos(f2*0.08F);
  		
  		if(((IShipFloating)ent).getShipDepth() > 0) {
//  			LogHelper.info("DEBUG : mount depth "+((IShipFloating)ent).getShipDepth());
    		GL11.glTranslatef(0F, -0.2F, 0F);
    	}
  		
  		if(ent.getIsSitting()) {
    		GL11.glTranslatef(0F, 1.3F, 0F);
    	}
    	else {
    		GL11.glTranslatef(0F, 1.0F, 0F);
    	}

	    //正常站立動作
	  	//嘴巴
	  	this.Jaw.rotateAngleX = angleX * 0.1F + 0.7F;
	  	this.GlowJaw.rotateAngleX = this.Jaw.rotateAngleX;
	    //cannon
	    this.EquipCannon01.rotateAngleX = angleX * 0.08F - 0.32F;
	    
//	    if(ent.getStateEmotion(ID.S.Emotion) > 0) {
//	    	this.ArmRight01.rotateAngleX = -1.57F;
//	    }
  	}

    
}

