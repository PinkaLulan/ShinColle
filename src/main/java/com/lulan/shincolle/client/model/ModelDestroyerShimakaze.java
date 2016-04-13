package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EmotionHelper;

/**
 * ModelDestroyerShimakaze - PinkaLulan 2015/3/27
 * Created using Tabula 4.1.1
 */
public class ModelDestroyerShimakaze extends ModelBase implements IModelEmotion {
    public ModelRenderer BodyMain;
    public ModelRenderer NeckCloth;
    public ModelRenderer ArmLeft;
    public ModelRenderer ArmRight;
    public ModelRenderer Butt;
    public ModelRenderer EquipBase;
    public ModelRenderer Head;
    public ModelRenderer NeckTie;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Face0;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairAnchor;
    public ModelRenderer HairR02;
    public ModelRenderer HairMidL01;
    public ModelRenderer HairMidR01;
    public ModelRenderer EarBase;
    public ModelRenderer HairMidL02;
    public ModelRenderer HairMidR02;
    public ModelRenderer EarL01;
    public ModelRenderer EarL02;
    public ModelRenderer EarR01;
    public ModelRenderer EarR02;
    public ModelRenderer LegRight;
    public ModelRenderer LegLeft;
    public ModelRenderer Skirt;
    public ModelRenderer ShoesR;
    public ModelRenderer ShoesL;
    public ModelRenderer EquipHead;
    public ModelRenderer EquipT01;
    public ModelRenderer EquipT02;
    public ModelRenderer EquipT03;
    public ModelRenderer EquipT04;
    public ModelRenderer EquipT05;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeckCloth;
    public ModelRenderer GlowHead;
    
    private int startEmo2 = 0;

    public ModelDestroyerShimakaze() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.Head = new ModelRenderer(this, 24, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.HairMain = new ModelRenderer(this, 23, 61);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 10, 0.0F);
        this.Hair = new ModelRenderer(this, 24, 80);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -7.5F, -8.0F, 16, 12, 8, 0.0F);
        this.HairMidR01 = new ModelRenderer(this, 42, 40);
        this.HairMidR01.mirror = true;
        this.HairMidR01.setRotationPoint(-2.5F, 9.0F, 2.5F);
        this.HairMidR01.addBox(-4.5F, 0.0F, 0.0F, 9, 13, 8, 0.0F);
        this.setRotateAngle(HairMidR01, 0.13962634015954636F, -0.08726646259971647F, 0.2617993877991494F);
        this.HairMidL02 = new ModelRenderer(this, 46, 21);
        this.HairMidL02.setRotationPoint(0.0F, 12.0F, 3.0F);
        this.HairMidL02.addBox(-4.5F, 0.0F, 0.0F, 9, 14, 5, 0.0F);
        this.setRotateAngle(HairMidL02, 0.13962634015954636F, 0.0F, -0.13962634015954636F);
        this.HairMidL01 = new ModelRenderer(this, 42, 40);
        this.HairMidL01.setRotationPoint(2.5F, 9.0F, 2.5F);
        this.HairMidL01.addBox(-4.5F, 0.0F, 0.0F, 9, 13, 8, 0.0F);
        this.setRotateAngle(HairMidL01, 0.13962634015954636F, 0.08726646259971647F, -0.2617993877991494F);
        this.HairMidR02 = new ModelRenderer(this, 46, 21);
        this.HairMidR02.mirror = true;
        this.HairMidR02.setRotationPoint(0.0F, 12.0F, 3.0F);
        this.HairMidR02.addBox(-4.5F, 0.0F, 0.0F, 9, 14, 5, 0.0F);
        this.setRotateAngle(HairMidR02, 0.13962634015954636F, 0.0F, 0.13962634015954636F);
        this.Skirt = new ModelRenderer(this, 50, 0);
        this.Skirt.setRotationPoint(0.0F, 5.5F, 0.0F);
        this.Skirt.addBox(-8.5F, 0.0F, -6.0F, 17, 6, 9, 0.0F);
        this.setRotateAngle(Skirt, -0.17453292519943295F, 0.0F, 0.0F);     
        this.HairL02 = new ModelRenderer(this, 103, 1);
        this.HairL02.setRotationPoint(-0.2F, 8.5F, 0.5F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 3, 0.0F);
        this.setRotateAngle(HairL02, 0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.EarBase = new ModelRenderer(this, 80, 113);
        this.EarBase.setRotationPoint(-2.0F, -2.0F, 2.0F);
        this.EarBase.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4, 0.0F);
        this.EquipT05 = new ModelRenderer(this, 85, 65);
        this.EquipT05.setRotationPoint(-8.1F, -8.0F, 1.0F);
        this.EquipT05.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.HairR01 = new ModelRenderer(this, 102, 0);
        this.HairR01.setRotationPoint(-5.5F, 0.0F, -3.0F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairR01, -0.2617993877991494F, 0.17453292519943295F, 0.2617993877991494F);
        this.EquipT01 = new ModelRenderer(this, 85, 65);
        this.EquipT01.setRotationPoint(5.1F, -8.0F, 1.0F);
        this.EquipT01.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.EarR01 = new ModelRenderer(this, 83, 113);
        this.EarR01.setRotationPoint(0.0F, 2.5F, 2.0F);
        this.EarR01.addBox(-1.5F, -10.0F, -1.0F, 3, 10, 2, 0.0F);
        this.EarR02 = new ModelRenderer(this, 82, 113);
        this.EarR02.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.EarR02.addBox(-2.0F, -13.0F, -1.0F, 4, 13, 2, 0.0F);
        this.EarL01 = new ModelRenderer(this, 83, 113);
        this.EarL01.setRotationPoint(4.0F, 2.5F, 2.0F);
        this.EarL01.addBox(-1.5F, -10.0F, -1.0F, 3, 10, 2, 0.0F);
        this.EarL02 = new ModelRenderer(this, 82, 113);
        this.EarL02.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.EarL02.addBox(-2.0F, -13.0F, -1.0F, 4, 13, 2, 0.0F);  
        this.NeckCloth = new ModelRenderer(this, 0, 0);
        this.NeckCloth.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.NeckCloth.addBox(-7.5F, -1.5F, -4.5F, 15, 12, 8, 0.0F);
        this.Ahoke = new ModelRenderer(this, 65, 88);
        this.Ahoke.setRotationPoint(0.0F, -14.0F, -4.0F);
        this.Ahoke.addBox(0.0F, 0.0F, -12.0F, 0, 13, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.5235987755982988F, 0.0F);
        this.ShoesR = new ModelRenderer(this, 88, 15);
        this.ShoesR.setRotationPoint(0.0F, 19.0F, -0.2F);
        this.ShoesR.addBox(-3.5F, 0.0F, -3.5F, 7, 7, 7, 0.0F);
        this.HairL01 = new ModelRenderer(this, 102, 0);
        this.HairL01.setRotationPoint(5.5F, 0.0F, -3.0F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairL01, -0.2617993877991494F, -0.17453292519943295F, -0.2617993877991494F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ShoesL = new ModelRenderer(this, 88, 15);
        this.ShoesL.setRotationPoint(0.0F, 19.0F, -0.2F);
        this.ShoesL.addBox(-3.5F, 0.0F, -3.5F, 7, 7, 7, 0.0F);
        this.EquipT04 = new ModelRenderer(this, 85, 65);
        this.EquipT04.setRotationPoint(-4.8F, -8.0F, 1.0F);
        this.EquipT04.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 22);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-8.0F, 4.0F, -5.4F, 16, 8, 7, 0.0F);
        this.setRotateAngle(Butt, 0.2617993877991494F, 0.0F, 0.0F);
        this.EquipT03 = new ModelRenderer(this, 85, 65);
        this.EquipT03.setRotationPoint(-1.5F, -8.0F, 1.0F);
        this.EquipT03.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.EquipBase = new ModelRenderer(this, 76, 33);
        this.EquipBase.setRotationPoint(2.0F, -5.0F, 7.0F);
        this.EquipBase.addBox(-7.0F, 0.0F, -3.7F, 14, 8, 12, 0.0F);
        this.setRotateAngle(EquipBase, 0.13962634015954636F, 0.0F, 0.5235987755982988F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipHead = new ModelRenderer(this, 77, 29);
        this.EquipHead.setRotationPoint(0.0F, -3.0F, -0.3F);
        this.EquipHead.addBox(-9.0F, 0.0F, 0.0F, 18, 17, 7, 0.0F);
        this.LegRight = new ModelRenderer(this, 0, 96);
        this.LegRight.setRotationPoint(-4.5F, 9.5F, -3.0F);
        this.LegRight.addBox(-3.0F, 0.0F, -3.0F, 6, 19, 6, 0.0F);
        this.setRotateAngle(LegRight, -0.2617993877991494F, 0.0F, -0.05235987755982988F);
        this.HairAnchor = new ModelRenderer(this, 112, 7);
        this.HairAnchor.setRotationPoint(0.2F, 8.0F, -1.0F);
        this.HairAnchor.addBox(-1.5F, 0.0F, 0.0F, 2, 5, 6, 0.0F);
        this.setRotateAngle(HairAnchor, 0.08726646259971647F, 0.0F, 0.136659280431156F);
        this.EquipT02 = new ModelRenderer(this, 85, 65);
        this.EquipT02.setRotationPoint(1.8F, -8.0F, 1.0F);
        this.EquipT02.addBox(0.0F, 0.0F, 0.0F, 3, 31, 3, 0.0F);
        this.LegLeft = new ModelRenderer(this, 0, 96);
        this.LegLeft.mirror = true;
        this.LegLeft.setRotationPoint(4.5F, 9.5F, -3.0F);
        this.LegLeft.addBox(-3.0F, 0.0F, -3.0F, 6, 19, 6, 0.0F);
        this.setRotateAngle(LegLeft, -0.2617993877991494F, 0.0F, 0.05235987755982988F);
        this.BodyMain = new ModelRenderer(this, 0, 37);
        this.BodyMain.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.BodyMain.addBox(-7.0F, -11.0F, -4.0F, 14, 17, 7, 0.0F);
        this.NeckTie = new ModelRenderer(this, 39, 0);
        this.NeckTie.setRotationPoint(0.0F, 2.5F, -4.7F);
        this.NeckTie.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 0, 0.0F);
        this.setRotateAngle(NeckTie, -0.13962634015954636F, 0.0F, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ArmRight = new ModelRenderer(this, 0, 61);
        this.ArmRight.setRotationPoint(-7.0F, -10.5F, 0.0F);
        this.ArmRight.addBox(-2.5F, 0.0F, -2.5F, 5, 22, 5, 0.0F);
        this.setRotateAngle(ArmRight, 0.0F, 0.0F, 0.4363323129985824F);
        this.ArmLeft = new ModelRenderer(this, 0, 61);
        this.ArmLeft.mirror = true;
        this.ArmLeft.setRotationPoint(7.0F, -10.5F, 0.0F);
        this.ArmLeft.addBox(-2.5F, 0.0F, -2.5F, 5, 22, 5, 0.0F);
        this.setRotateAngle(ArmLeft, 0.0F, 0.0F, -0.3490658503988659F);
        this.HairR02 = new ModelRenderer(this, 103, 1);
        this.HairR02.setRotationPoint(0.2F, 8.5F, 0.5F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 3, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.0F, -0.17453292519943295F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.HairMain.addChild(this.HairMidR01);
        this.HairMain.addChild(this.HairMidL01);
        this.EarL01.addChild(this.EarL02);
        this.EarR01.addChild(this.EarR02);
        this.HairL01.addChild(this.HairL02);
        this.HairMain.addChild(this.EarBase);
        this.EquipBase.addChild(this.EquipT05);
        this.Hair.addChild(this.HairR01);
        this.EquipBase.addChild(this.EquipT01); 
        this.BodyMain.addChild(this.NeckCloth);
        this.Hair.addChild(this.Ahoke);
        this.LegRight.addChild(this.ShoesR);
        this.Hair.addChild(this.HairL01);
        this.LegLeft.addChild(this.ShoesL);
        this.EquipBase.addChild(this.EquipT04);
        this.BodyMain.addChild(this.Butt);
        this.EquipBase.addChild(this.EquipT03);
        this.BodyMain.addChild(this.EquipBase);
        this.NeckCloth.addChild(this.Head);
        this.Head.addChild(this.HairMain);
        this.EquipBase.addChild(this.EquipHead);
        this.Butt.addChild(this.LegRight);
        this.HairL02.addChild(this.HairAnchor);
        this.EquipBase.addChild(this.EquipT02);
        this.Butt.addChild(this.LegLeft);
        this.NeckCloth.addChild(this.NeckTie);
        this.EarBase.addChild(this.EarL01);
        this.EarBase.addChild(this.EarR01);
        this.BodyMain.addChild(this.ArmRight);
        this.BodyMain.addChild(this.ArmLeft);
        this.HairR01.addChild(this.HairR02);
        this.Butt.addChild(this.Skirt);   
        this.Head.addChild(this.Hair);
        this.HairMidL01.addChild(this.HairMidL02);
        this.HairMidR01.addChild(this.HairMidR02);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.GlowNeckCloth = new ModelRenderer(this, 0, 0);
        this.GlowNeckCloth.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.5F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeckCloth);
        this.GlowNeckCloth.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
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
    	GL11.glTranslatef(0F, 2.2F, 0F);
    	
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
		  
		BasicEntityShip ent = (BasicEntityShip)entity;
		  
		showEquip(ent);
		
		EmotionHelper.rollEmotion(this, ent);
		
		if(ent.getStateFlag(ID.F.NoFuel)) {
			motionStopPos(f, f1, f2, f3, f4, ent);
		}
		else {
			motionHumanPos(f, f1, f2, f3, f4, ent);
		}
		
		setGlowRotation();
    }
    
    //設定模型發光部份的rotation
    private void setGlowRotation() {
    	//頭部
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowNeckCloth.rotateAngleX = this.NeckCloth.rotateAngleX;
		this.GlowNeckCloth.rotateAngleY = this.NeckCloth.rotateAngleY;
		this.GlowNeckCloth.rotateAngleZ = this.NeckCloth.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
    }
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, BasicEntityShip ent) {
    	GL11.glTranslatef(0F, 2.0F, 0F);
    	setFace(4);
    	
  	    //ear
  	    this.EarL01.rotateAngleX = 1F;
  	    this.EarL01.rotateAngleY = -0.4F;
	    this.EarR01.rotateAngleX = 1F;
	    this.EarR01.rotateAngleY = 1.0472F;
  	    this.EarL02.rotateAngleX = -0.8F;
  	    this.EarL02.rotateAngleZ = 0F;
  	    this.EarR02.rotateAngleX = -0.2F;
  	    this.EarR02.rotateAngleY = -0.2F;
  	    this.EarR02.rotateAngleZ = 0F;
		//equip
		this.EquipBase.rotateAngleZ = 0.52F;
		//body
    	this.Head.rotateAngleX = 0F;
    	this.Head.rotateAngleY = 0F;
    	this.Head.rotateAngleZ = 0F;
    	this.Ahoke.rotateAngleY = 0.5236F;
	  	this.BodyMain.rotateAngleY = 0F;
    	this.BodyMain.rotateAngleX = 1.4835F;
    	this.HairMidL01.rotateAngleX = -0.05F;
    	this.HairMidR01.rotateAngleX = -0.05F;
    	this.HairMidL02.rotateAngleX = -0.1F;
    	this.HairMidR02.rotateAngleX = -0.1F;
    	//arm
    	this.ArmLeft.rotateAngleX = -0.12F;
    	this.ArmLeft.rotateAngleZ = -0.2F;
    	this.ArmRight.rotateAngleX = -0.12F;
    	this.ArmRight.rotateAngleZ = 0.2F;
    	//leg
    	this.LegLeft.rotateAngleX = -0.2618F;
    	this.LegRight.rotateAngleX = -0.2618F;
    	this.LegLeft.rotateAngleY = 0F;
		this.LegRight.rotateAngleY = 0F;
    	this.LegLeft.rotateAngleZ = 0.03F;
    	this.LegRight.rotateAngleZ = -0.03F;
    }
    
    //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, BasicEntityShip ent) {   
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleRun = MathHelper.cos(f * 1.5F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		//leg move parm
  		addk1 = MathHelper.cos(f * 0.7F) * f1 - 0.2618F;
	  	addk2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 - 0.2618F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 / 57.29578F; 	//上下角度
	  	this.Head.rotateAngleY = f3 / 57.29578F;	//左右角度 角度轉成rad 即除以57.29578
	    
	    //正常站立動作
  	    //ear
  	    this.EarL01.rotateAngleX = angleX * 0.1F - 0.8727F;
  	    this.EarL01.rotateAngleY = 1.0472F;
	    this.EarR01.rotateAngleX = angleX * 0.1F + 0.5236F;
	    this.EarR01.rotateAngleY = 1.0472F;
  	    this.EarL02.rotateAngleX = angleX * 0.2F + 0.7F;
  	    this.EarL02.rotateAngleZ = 0F;
  	    this.EarR02.rotateAngleX = angleX * 0.2F + 0.7F;
  	    this.EarR02.rotateAngleY = -0.5236F;
  	    this.EarR02.rotateAngleZ = 0F;
  	    //hair
  	    this.HairMidL01.rotateAngleX = angleX * 0.1F + 0.14F;
  	    this.HairMidL02.rotateAngleX = this.HairMidL01.rotateAngleX;
  	    this.HairMidR01.rotateAngleX = this.HairMidL01.rotateAngleX;
  	    this.HairMidR02.rotateAngleX = this.HairMidL01.rotateAngleX;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
	  	this.BodyMain.rotateAngleX = 0F;
	  	this.BodyMain.rotateAngleY = 0F;
	    //arm 
	  	this.ArmLeft.rotateAngleX = 0F;
	    this.ArmLeft.rotateAngleZ = angleX * 0.1F - 0.5236F;
	    this.ArmRight.rotateAngleX = 0F;
		this.ArmRight.rotateAngleZ = -angleX * 0.1F + 0.5236F;
		//leg
		this.LegLeft.rotateAngleY = 0F;
		this.LegLeft.rotateAngleZ = 0.05F;
		this.LegRight.rotateAngleY = 0F;
		this.LegRight.rotateAngleZ = -0.05F;
		//equip
		this.EquipBase.rotateAngleZ = 0.52F;

	    if(ent.isSprinting() || f1 > 0.9F) {	//奔跑動作
	    	setFace(3);
	    	//body
	    	this.Head.rotateAngleX -= 0.2618F;
	    	this.BodyMain.rotateAngleX = 0.2618F;
	    	this.HairMidL01.rotateAngleX = 0.7F;
	    	this.HairMidR01.rotateAngleX = 0.7F;
	    	this.HairMidL02.rotateAngleX = 0.5F;
	    	this.HairMidR02.rotateAngleX = 0.5F;
	    	//arm
	    	this.ArmLeft.rotateAngleX = 0.7F;
	    	this.ArmLeft.rotateAngleZ = -1.0472F;
	    	this.ArmRight.rotateAngleX = 0.7F;
	    	this.ArmRight.rotateAngleZ = 1.0472F;
	    	//leg
	    	addk1 = MathHelper.cos(f * 2F) * f1 * 1.5F - 0.5F;
		  	addk2 = MathHelper.cos(f * 2F + 3.1415927F) * f1 * 1.5F - 0.5F;
	    	this.LegLeft.rotateAngleY = 0F;
	    	this.LegLeft.rotateAngleZ = 0.05F;
	    	this.LegRight.rotateAngleY = 0F;
	    	this.LegRight.rotateAngleZ = -0.05F;
	    	//ear
	    	this.EarL01.rotateAngleX = -angleRun * 0.08F - 0.8727F;
	    	this.EarL01.rotateAngleY = 0.5F;
	    	this.EarL02.rotateAngleX = -angleRun * 0.1F - 0.5F;
	    	this.EarL02.rotateAngleZ = -0.5F;
	    	this.EarR01.rotateAngleX = angleRun * 0.08F - 0.8727F;
	    	this.EarR01.rotateAngleY = -0.5F;
	    	this.EarR02.rotateAngleX = angleRun * 0.1F - 0.5F;
	    	this.EarR02.rotateAngleY = 0F;
	    	this.EarR02.rotateAngleZ = 0.5F;
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if(ent.isSneaking()) {		//潛行, 蹲下動作
//	    	GL11.glTranslatef(0F, 1.0F, 0F);
	    	//body
	    	this.Head.rotateAngleX -= 0.7854F;
	    	this.BodyMain.rotateAngleX = 0.7854F;
	    	this.HairMidL01.rotateAngleX = 0.35F;
	    	this.HairMidR01.rotateAngleX = 0.35F;
	    	this.HairMidL02.rotateAngleX = 0.35F;
	    	this.HairMidR02.rotateAngleX = 0.35F;
	    	//arm
	    	this.ArmLeft.rotateAngleZ = -0.5F;
	    	this.ArmRight.rotateAngleZ = 0.5F;
	    	//leg
	    	addk1 -= 0.8F;
	    	addk2 -= 0.8F;
	    	
  		}//end if sneaking
  		
	    if(ent.isSitting() || ent.isRiding()) {  //騎乘動作
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {	
	    		GL11.glTranslatef(0F, 2.0F, 0F);
		    	//body
		    	this.Head.rotateAngleX = -1.48F;
		    	this.Head.rotateAngleY = 0F;
		    	this.Head.rotateAngleZ = 0F;
		    	this.BodyMain.rotateAngleX = 1.4835F;
		    	this.HairMidL01.rotateAngleX = 0.7F;
		    	this.HairMidR01.rotateAngleX = 0.7F;
		    	this.HairMidL02.rotateAngleX = 0.7F;
		    	this.HairMidR02.rotateAngleX = 0.7F;
		    	//arm
		    	this.ArmLeft.rotateAngleX = -3.0543F;
		    	this.ArmLeft.rotateAngleZ = -0.7F;
		    	this.ArmRight.rotateAngleX = -2.8F;
		    	this.ArmRight.rotateAngleZ = 0.35F;
		    	//leg
		    	addk1 = 0F;
		    	addk2 = -0.2618F;
		    	this.LegLeft.rotateAngleZ = 0.1745F;
		    	this.LegRight.rotateAngleZ = -0.35F;
	    	}
	    	else {
		    	GL11.glTranslatef(0F, 1.5F, 0F);
		    	//body
		    	this.Head.rotateAngleX -= 0.7F;
		    	this.BodyMain.rotateAngleX = 0.5236F;
		    	this.HairMidL01.rotateAngleX += 0.2F;
		    	this.HairMidR01.rotateAngleX += 0.2F;
		    	//arm
		    	this.ArmLeft.rotateAngleX = -0.5236F;
		    	this.ArmLeft.rotateAngleZ = 0.3146F;
		    	this.ArmRight.rotateAngleX = -0.5236F;
		    	this.ArmRight.rotateAngleZ = -0.3146F;
		    	//leg
		    	addk1 = -2.2689F;
		    	addk2 = -2.2689F;
		    	this.LegLeft.rotateAngleY = -0.3491F;
		    	this.LegRight.rotateAngleY = 0.3491F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if(ent.attackTime > 0) {
	    	GL11.glTranslatef(0F, 0.5F, 0F);
	    	//body
	    	this.Head.rotateAngleX = -0.8727F;
	    	this.Head.rotateAngleY = 1.0472F;
	    	this.Head.rotateAngleZ = -0.7F;
	    	this.BodyMain.rotateAngleX = 1.3F;
	    	this.BodyMain.rotateAngleY = -1.57F;
	    	//arm
	    	this.ArmLeft.rotateAngleX = 0F;
	    	this.ArmLeft.rotateAngleZ = -0.5F;
	    	this.ArmRight.rotateAngleX = 0F;
	    	this.ArmRight.rotateAngleZ = 1.57F;
	    	//leg
	    	addk1 = -1.75F;
	    	addk2 = -1.92F;
	    	//equip
	    	this.EquipBase.rotateAngleZ = 1.57F;
	    }
	    
	    //leg motion
	    this.LegLeft.rotateAngleX = addk1;
	    this.LegRight.rotateAngleX = addk2;
	    
  	}
  	
  	private void showEquip(BasicEntityShip ent) {
		if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
			this.EquipBase.isHidden = false;
			this.HairAnchor.isHidden = false;
		}
		else {
			this.EquipBase.isHidden = true;
			this.HairAnchor.isHidden = true;
		}
  	}

    //設定顯示的臉型
  	@Override
  	public void setFace(int emo) {
  		switch(emo) {
  		case 0:
  			this.Face0.isHidden = false;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 1:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = false;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 2:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 3:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face4.isHidden = true;
  			break;
  		case 4:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = false;
  			break;
  		default:
  			break;
  		}
  	}

    
}

