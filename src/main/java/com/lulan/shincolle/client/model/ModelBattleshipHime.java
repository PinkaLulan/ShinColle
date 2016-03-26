package com.lulan.shincolle.client.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EmotionHelper;

/**
 * ModelBattleshipHime - PinkaLulan 2015/4/12
 * Created using Tabula 4.1.1
 */
public class ModelBattleshipHime extends ModelBase implements IModelEmotion {
	public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
    public ModelRenderer Cloth01;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Face0;
    public ModelRenderer HeadHL;
    public ModelRenderer HeadHR;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairL03;
    public ModelRenderer HairR02;
    public ModelRenderer HairR03;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer ClothR02;
    public ModelRenderer ClothR03;
    public ModelRenderer LegLeft02;
    public ModelRenderer ClothL02;
    public ModelRenderer ClothL03;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    
    private Random rand = new Random();
    private int startEmo2 = 0;

    public ModelBattleshipHime() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.HairL02 = new ModelRenderer(this, 88, 100);
        this.HairL02.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 3, 0.0F);
        this.setRotateAngle(HairL02, 0.05235987755982988F, 0.0F, 0.08726646259971647F);
        this.HairR02 = new ModelRenderer(this, 88, 100);
        this.HairR02.setRotationPoint(0.2F, 10.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 3, 0.0F);
        this.setRotateAngle(HairR02, 0.08726646259971647F, 0.0F, -0.05235987755982988F);
        this.ClothR02 = new ModelRenderer(this, 10, 1);
        this.ClothR02.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.ClothR02.addBox(-3.6F, 0.0F, -3.7F, 8, 3, 9, 0.0F);
        this.setRotateAngle(ClothR02, 0.13962634015954636F, 0.0F, 0.0F);
        this.ClothL02 = new ModelRenderer(this, 10, 1);
        this.ClothL02.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.ClothL02.addBox(-4.4F, 0.0F, -3.7F, 8, 3, 9, 0.0F);
        this.setRotateAngle(ClothL02, 0.13962634015954636F, 0.0F, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.5F, 9.5F, -2.7F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.20943951023931953F, 0.0F, 0.05235987755982988F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-3.7F, -9.0F, -3.5F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, -0.13962634015954636F, -0.08726646259971647F);
        this.ArmLeft01 = new ModelRenderer(this, 15, 80);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.0F, 0.0F, -0.20943951023931953F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.BoobL = new ModelRenderer(this, 33, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.7F, -9.0F, -3.5F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, 0.13962634015954636F, 0.08726646259971647F);
        this.Hair02 = new ModelRenderer(this, 2, 47);
        this.Hair02.setRotationPoint(0.0F, 12.0F, 5.7F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 15, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.05235987755982988F, 0.0F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 75);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 17, 8, 0.0F);
        this.HeadHL = new ModelRenderer(this, 120, 29);
        this.HeadHL.mirror = true;
        this.HeadHL.setRotationPoint(3.5F, -7.5F, -3.3F);
        this.HeadHL.addBox(-1.0F, -9.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(HeadHL, 0.6981317007977318F, 0.0F, 0.13962634015954636F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.05235987755982988F, 0.0F, 0.0F);
        this.Hair03 = new ModelRenderer(this, 3, 24);
        this.Hair03.setRotationPoint(0.0F, 12.0F, 0.2F);
        this.Hair03.addBox(-8.0F, 0.0F, -5.5F, 16, 13, 8, 0.0F);
        this.setRotateAngle(Hair03, -0.08970992355250852F, 0.0F, 0.016231562043547264F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ClothL03 = new ModelRenderer(this, 8, 0);
        this.ClothL03.setRotationPoint(0.0F, 1.5F, 0.1F);
        this.ClothL03.addBox(-4.5F, 0.0F, -3.8F, 9, 5, 10, 0.0F);
        this.setRotateAngle(ClothL03, 0.08726646259971647F, 0.0F, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Hair01 = new ModelRenderer(this, 50, 46);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 17, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.17453292519943295F, 0.0F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 24, 80);
        this.LegRight02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.setRotationPoint(-4.5F, 9.5F, -2.7F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.20943951023931953F, 0.0F, -0.05235987755982988F);
        this.HairR01 = new ModelRenderer(this, 88, 100);
        this.HairR01.setRotationPoint(-6.5F, 0.0F, -6.0F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.13962634015954636F, 0.17453292519943295F, 0.13962634015954636F);
        this.Butt = new ModelRenderer(this, 82, 13);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-7.5F, 4.0F, -5.5F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.2617993877991494F, 0.0F, 0.0F);
        this.HairMain = new ModelRenderer(this, 2, 0);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 10, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 24, 80);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.HairL03 = new ModelRenderer(this, 88, 100);
        this.HairL03.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.HairL03.addBox(-1.0F, 0.0F, 0.0F, 2, 13, 3, 0.0F);
        this.setRotateAngle(HairL03, 0.13962634015954636F, 0.0F, 0.08726646259971647F);
        this.ArmLeft02 = new ModelRenderer(this, 0, 71);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(0.5F, 11.0F, 0.5F);
        this.ArmLeft02.addBox(-2.5F, 0F, -3F, 5, 13, 5, 0.0F);
        this.HairR03 = new ModelRenderer(this, 88, 100);
        this.HairR03.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.HairR03.addBox(-1.0F, 0.0F, 0.0F, 2, 13, 3, 0.0F);
        this.setRotateAngle(HairR03, 0.13962634015954636F, 0.0F, -0.05235987755982988F);
        this.Cloth01 = new ModelRenderer(this, 84, 0);
        this.Cloth01.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.Cloth01.addBox(-7.0F, 0.0F, -4.5F, 14, 5, 8, 0.0F);
        this.setRotateAngle(Cloth01, 0.13962634015954636F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 0, 71);
        this.ArmRight02.setRotationPoint(-0.5F, 11.0F, 0.5F);
        this.ArmRight02.addBox(-2.5F, 0F, -3F, 5, 13, 5, 0.0F);
        this.Neck = new ModelRenderer(this, 44, 0);
        this.Neck.setRotationPoint(0.0F, -10.0F, -0.5F);
        this.Neck.addBox(-4.5F, -2.0F, -4.0F, 9, 1, 8, 0.0F);
        this.setRotateAngle(Neck, 0.05235987755982988F, 0.0F, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Ahoke = new ModelRenderer(this, 108, 41);
        this.Ahoke.setRotationPoint(0.0F, -5.3F, -7.2F);
        this.Ahoke.addBox(-2.0F, 0.0F, 0.0F, 10, 12, 0, 0.0F);
        this.setRotateAngle(Ahoke, -0.05235987755982988F, 0.0F, -0.03490658503988659F);
        this.ClothR03 = new ModelRenderer(this, 8, 0);
        this.ClothR03.setRotationPoint(0.0F, 1.5F, 0.1F);
        this.ClothR03.addBox(-4.5F, 0.0F, -3.8F, 9, 5, 10, 0.0F);
        this.setRotateAngle(ClothR03, 0.08726646259971647F, 0.0F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 15, 80);
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.10471975511965977F, 0.0F, 0.20943951023931953F);
        this.HeadHR = new ModelRenderer(this, 120, 29);
        this.HeadHR.setRotationPoint(-3.5F, -7.5F, -3.3F);
        this.HeadHR.addBox(-1.0F, -9.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(HeadHR, 0.6981317007977318F, 0.0F, -0.13962634015954636F);
        this.HairL01 = new ModelRenderer(this, 88, 100);
        this.HairL01.setRotationPoint(6.5F, 0.0F, -6.0F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.13962634015954636F, -0.17453292519943295F, -0.13962634015954636F);
        this.HairL01.addChild(this.HairL02);
        this.HairR01.addChild(this.HairR02);
        this.LegRight01.addChild(this.ClothR02);
        this.LegLeft01.addChild(this.ClothL02);
        this.Butt.addChild(this.LegLeft01);
        this.BodyMain.addChild(this.BoobR);
        this.BodyMain.addChild(this.ArmLeft01);
        this.BodyMain.addChild(this.BoobL);
        this.Hair01.addChild(this.Hair02);
        this.Head.addChild(this.Hair);
        this.Head.addChild(this.HeadHL);
        this.Hair02.addChild(this.Hair03);
        this.Neck.addChild(this.Head);
        this.LegLeft01.addChild(this.ClothL03);
        this.HairMain.addChild(this.Hair01);
        this.LegRight01.addChild(this.LegRight02);
        this.Butt.addChild(this.LegRight01);
        this.Hair.addChild(this.HairR01);
        this.BodyMain.addChild(this.Butt);
        this.Head.addChild(this.HairMain);
        this.LegLeft01.addChild(this.LegLeft02);
        this.HairL02.addChild(this.HairL03);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.HairR02.addChild(this.HairR03);
        this.BodyMain.addChild(this.Cloth01);
        this.ArmRight01.addChild(this.ArmRight02);
        this.BodyMain.addChild(this.Neck);
        this.Hair.addChild(this.Ahoke);
        this.LegRight01.addChild(this.ClothR03);
        this.BodyMain.addChild(this.ArmRight01);
        this.Head.addChild(this.HeadHR);
        this.Hair.addChild(this.HairL01);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.05235987755982988F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.0F, -0.5F);
        this.setRotateAngle(GlowNeck, 0.05235987755982988F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.5F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
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
    	GL11.glScalef(0.5F, 0.5F, 0.5F);

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
		
		EmotionHelper.rollEmotion(this, ent);
		  
		motionHumanPos(f, f1, f2, f3, f4, ent);
		
		setGlowRotation();
    }
    
    //設定模型發光部份的rotation
    private void setGlowRotation() {
    	//頭部
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowNeck.rotateAngleX = this.Neck.rotateAngleX;
		this.GlowNeck.rotateAngleY = this.Neck.rotateAngleY;
		this.GlowNeck.rotateAngleZ = this.Neck.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
    }
    
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {   
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		float headX = 0F;
  		float headZ = 0F;
  		
    	GL11.glTranslatef(0F, 1.5F, 0F);
  		
  		//leg move parm
  		addk1 = angleAdd1 - 0.2618F;
	  	addk2 = angleAdd2 - 0.2618F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 / 70F; 	//上下角度
	  	this.Head.rotateAngleY = f3 / 80F;	//左右角度 角度轉成rad 即除以57.29578
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.7F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.7F;
	  	//Body
  	    this.Ahoke.rotateAngleZ = angleX * 0.02F - 0.02F;
	  	this.BodyMain.rotateAngleX = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.012F + 0.15F + headX;
	  	this.Hair02.rotateAngleX = angleX * 0.015F - 0.05F + headX;
	  	this.Hair03.rotateAngleX = angleX * 0.018F - 0.08F;
	  	this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
	  	this.HairL02.rotateAngleX = angleX * 0.02F + headX + 0.052F;
	  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
	  	this.HairR02.rotateAngleX = angleX * 0.02F + headX + 0.087F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.8F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.08F - 0.2F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.8F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.08F + 0.2F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.05F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.05F;
		this.LegRight02.rotateAngleX = 0F;

	    if(ent.getIsSprinting() || f1 > 0.9F) {	//奔跑動作
	    	//沒有特殊跑步動作
  		}
	    else {
	    	startEmo2 = ent.getStartEmotion2();
	    	
	    	//頭部傾斜動作, 只在奔跑以外時roll
	    	if(startEmo2 > 0) {
	    		--startEmo2;
	    		ent.setStartEmotion2(startEmo2);
	    	}
	    	
		    if(startEmo2 <= 0) {
		    	startEmo2 = 360;
		    	ent.setStartEmotion2(startEmo2);	//cd = 6sec  	
		    	
		    	if(rand.nextInt(3) == 0) {
		    		ent.setStateFlag(ID.F.HeadTilt, true);
		    	}
		    	else {
		    		ent.setStateFlag(ID.F.HeadTilt, false);
		    	}
		    }
	    }//end if sprint
	    
	    //roll頭部傾斜表情
	    if(ent.getStateFlag(ID.F.HeadTilt)) {
	    	if(ent.getStateEmotion(ID.S.Emotion2) == 1) {	//之前已經傾斜, 則繼續傾斜
	    		this.Head.rotateAngleZ = -0.24F;
	    	}
	    	else {
		    	this.Head.rotateAngleZ = (360 - startEmo2) * -0.03F;
		    	
		    	if(this.Head.rotateAngleZ < -0.24F) {
		    		ent.setStateEmotion(ID.S.Emotion2, 1, false);
		    		this.Head.rotateAngleZ = -0.24F;
		    	}
	    	}	
	    }
	    else {
	    	if(ent.getStateEmotion(ID.S.Emotion2) == 0) {	//維持之前角度
	    		this.Head.rotateAngleZ = 0F;
	    	}
	    	else {
		    	this.Head.rotateAngleZ = -0.24F + (360 - startEmo2) * 0.03F;
		    	
		    	if(this.Head.rotateAngleZ > 0F) {
		    		this.Head.rotateAngleZ = 0F;
		    		ent.setStateEmotion(ID.S.Emotion2, 0, false);
		    	}
	    	}
	    }
	    
	    //移動頭髮避免穿過身體
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	this.HairL01.rotateAngleZ = headZ - 0.14F;
	  	this.HairL02.rotateAngleZ = headZ + 0.087F;
	  	this.HairR01.rotateAngleZ = headZ + 0.14F;
	  	this.HairR02.rotateAngleZ = headZ - 0.052F;
	    
	    if(ent.getIsSneaking()) {		//潛行, 蹲下動作
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		    //arm 
		  	this.ArmLeft01.rotateAngleX = -0.35F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
			this.ArmRight01.rotateAngleX = -0.35F;
			this.ArmRight01.rotateAngleZ = -0.2618F;
			//leg
			addk1 -= 0.88F;
			addk2 -= 0.88F;
			//hair
			this.Hair01.rotateAngleX += 0.37F;
			this.Hair02.rotateAngleX += 0.23F;
			this.Hair03.rotateAngleX -= 0.1F;
  		}//end if sneaking
  		
	    if(ent.getIsSitting() && !ent.getIsRiding()) {  //騎乘動作  	
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
		    	GL11.glTranslatef(0F, 2F, 0F);
		    	//Body
		    	this.Head.rotateAngleX = -1.2217F;
		    	this.Head.rotateAngleY = this.Head.rotateAngleY / 2F;
			  	this.BodyMain.rotateAngleX = 1.2217F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = -1.9199F;
			  	this.ArmLeft01.rotateAngleZ = -0.1745F;
			    this.ArmLeft02.rotateAngleX = -2.31F;
				this.ArmRight01.rotateAngleX = -1.9199F;
				this.ArmRight01.rotateAngleZ = 0.1745F;
				this.ArmRight02.rotateAngleX = -2.31F;
				//leg
				addk1 = 0F;
				addk2 = 0F;
				this.LegLeft02.rotateAngleX = angleX * 0.2F + 0.8F;
				this.LegRight02.rotateAngleX = -angleX * 0.2F + 0.8F;
				//hair
				headX = 0F;
				this.Hair01.rotateAngleX = angleX * 0.012F + 0.15F + headX;
			  	this.Hair02.rotateAngleX = angleX * 0.015F - 0.05F + headX;
			  	this.Hair03.rotateAngleX = angleX * 0.018F - 0.08F;
			  	this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
			  	this.HairL02.rotateAngleX = angleX * 0.02F + headX + 0.052F;
			  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
			  	this.HairR02.rotateAngleX = angleX * 0.02F + headX + 0.087F;
				this.Hair01.rotateAngleX += 0.6354F;
				this.Hair02.rotateAngleX += 0.5736F;  		
	    	}
	    	else {
	    		GL11.glTranslatef(0F, 1.2F, 0F);
		    	//Body
		    	this.Head.rotateAngleX += 0.14F;
			  	this.BodyMain.rotateAngleX = -0.4363F;
			  	this.BoobL.rotateAngleX -= 0.25F;
			  	this.BoobR.rotateAngleX -= 0.25F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = -0.3142F;
			    this.ArmLeft01.rotateAngleZ = 0.3490F;
			    this.ArmLeft02.rotateAngleZ = 1.15F;
				this.ArmRight01.rotateAngleX = -0.4363F;
				this.ArmRight01.rotateAngleZ = -0.2793F;
				this.ArmRight02.rotateAngleZ = -1.4F;
				//leg
				addk1 = -1.3090F;
				addk2 = -1.7F;
				this.LegLeft01.rotateAngleY = 0.3142F;
				this.LegLeft02.rotateAngleX = 1.0472F;
				this.LegRight01.rotateAngleY = -0.35F;
				this.LegRight01.rotateAngleZ = -0.2618F;
				this.LegRight02.rotateAngleX = 0.9F;
				//hair
				this.Hair01.rotateAngleX += 0.12F;
				this.Hair02.rotateAngleX += 0.15F;
				this.Hair03.rotateAngleX += 0.25F;
	    	}
  		}//end sitting
	    
	    if(ent.getIsRiding()) {
	    	if(((Entity)ent).ridingEntity instanceof BasicEntityMount) {
	    		if(ent.getIsSitting()) {
		    		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
				    	GL11.glTranslatef(0F, 1.8F, 0F); 
				    	//Body
				    	this.Head.rotateAngleX = -1.2217F;
				    	this.Head.rotateAngleY = this.Head.rotateAngleY / 2F;
					  	this.BodyMain.rotateAngleX = 1.2217F;
					    //arm 
					  	this.ArmLeft01.rotateAngleX = -1.9199F;
					  	this.ArmLeft01.rotateAngleZ = -0.1745F;
					    this.ArmLeft02.rotateAngleX = -2.31F;
						this.ArmRight01.rotateAngleX = -1.9199F;
						this.ArmRight01.rotateAngleZ = 0.1745F;
						this.ArmRight02.rotateAngleX = -2.31F;
						//leg
						addk1 = 0F;
						addk2 = 0F;
						this.LegLeft02.rotateAngleX = angleX * 0.2F + 0.8F;
						this.LegRight02.rotateAngleX = -angleX * 0.2F + 0.8F;
						//hair
						headX = 0F;
						this.Hair01.rotateAngleX = angleX * 0.012F + 0.15F + headX;
					  	this.Hair02.rotateAngleX = angleX * 0.015F - 0.05F + headX;
					  	this.Hair03.rotateAngleX = angleX * 0.018F - 0.08F;
					  	this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
					  	this.HairL02.rotateAngleX = angleX * 0.02F + headX + 0.052F;
					  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
					  	this.HairR02.rotateAngleX = angleX * 0.02F + headX + 0.087F;
						this.Hair01.rotateAngleX += 0.6354F;
						this.Hair02.rotateAngleX += 0.5736F;
			    	}
			    	else {
			    		GL11.glTranslatef(0F, 1.3F, 0F);
			    		//Body
				    	this.Head.rotateAngleX += 0.14F;
					  	this.BodyMain.rotateAngleX = -0.4363F;
					  	this.BoobL.rotateAngleX -= 0.25F;
					  	this.BoobR.rotateAngleX -= 0.25F;
					    //arm 
					  	this.ArmLeft01.rotateAngleX = -0.3142F;
					    this.ArmLeft01.rotateAngleZ = 0.3490F;
					    this.ArmLeft02.rotateAngleZ = 1.15F;
						this.ArmRight01.rotateAngleX = -0.4363F;
						this.ArmRight01.rotateAngleZ = -0.2793F;
						this.ArmRight02.rotateAngleZ = -1.4F;
						//leg
						addk1 = -1.3090F;
						addk2 = -1.7F;
						this.LegLeft01.rotateAngleY = 0.3142F;
						this.LegLeft02.rotateAngleX = 1.0472F;
						this.LegRight01.rotateAngleY = -0.35F;
						this.LegRight01.rotateAngleZ = -0.2618F;
						this.LegRight02.rotateAngleX = 0.9F;
						//hair
						this.Hair01.rotateAngleX += 0.12F;
						this.Hair02.rotateAngleX += 0.15F;
						this.Hair03.rotateAngleX += 0.25F;
			    	}
		    	}//end if sitting
		    	else {
	    			GL11.glTranslatef(0F, 0.32F, 0F);
			    	//Body
			    	this.Head.rotateAngleX += 0.1745F;
				  	this.BodyMain.rotateAngleX = -0.35F;
				    //arm
				  	this.ArmLeft01.rotateAngleX = -0.3142F;
				    this.ArmLeft01.rotateAngleZ = 0.3490F;
				    this.ArmLeft02.rotateAngleZ = 1.15F;
					this.ArmRight01.rotateAngleX = -0.4363F;
					this.ArmRight01.rotateAngleZ = -0.2793F;
					this.ArmRight02.rotateAngleZ = -1.4F;
					//leg
					addk1 = 0.1745F;
					addk2 = -0.8727F;
					this.LegLeft01.rotateAngleZ = -0.1F;
					this.LegRight01.rotateAngleZ = 0.1F;
					this.LegRight02.rotateAngleX = 1.0472F;
					//hair
					this.Hair01.rotateAngleX += 0.12F;
					this.Hair02.rotateAngleX += 0.22F;
					this.Hair03.rotateAngleX += 0.25F;    		
		    	}
	    	}//end ship mount
	    	else {	//normal mount ex: cart
	    		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
			    	GL11.glTranslatef(0F, 1.3F, 0F);
			    	//Body
			    	this.Head.rotateAngleX = -1.2217F;
			    	this.Head.rotateAngleY = this.Head.rotateAngleY / 2F;
				  	this.BodyMain.rotateAngleX = 1.2217F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = -1.9199F;
				  	this.ArmLeft01.rotateAngleZ = -0.1745F;
				    this.ArmLeft02.rotateAngleX = -2.31F;
					this.ArmRight01.rotateAngleX = -1.9199F;
					this.ArmRight01.rotateAngleZ = 0.1745F;
					this.ArmRight02.rotateAngleX = -2.31F;
					//leg
					addk1 = 0F;
					addk2 = 0F;
					this.LegLeft02.rotateAngleX = angleX * 0.2F + 0.8F;
					this.LegRight02.rotateAngleX = -angleX * 0.2F + 0.8F;
					//hair
					this.Hair01.rotateAngleX += 0.6354F;
					this.Hair02.rotateAngleX += 0.5736F;  		
		    	}
		    	else {
		    		GL11.glTranslatef(0F, 0.8F, 0F);
			    	//Body
			    	this.Head.rotateAngleX += 0.14F;
				  	this.BodyMain.rotateAngleX = -0.4363F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = -0.3142F;
				    this.ArmLeft01.rotateAngleZ = 0.3490F;
				    this.ArmLeft02.rotateAngleZ = 1.15F;
					this.ArmRight01.rotateAngleX = -0.4363F;
					this.ArmRight01.rotateAngleZ = -0.2793F;
					this.ArmRight02.rotateAngleZ = -1.4F;
					//leg
					addk1 = -1.3090F;
					addk2 = -1.7F;
					this.LegLeft01.rotateAngleY = 0.3142F;
					this.LegLeft02.rotateAngleX = 1.0472F;
					this.LegRight01.rotateAngleY = -0.35F;
					this.LegRight01.rotateAngleZ = -0.2618F;
					this.LegRight02.rotateAngleX = 0.9F;
					//hair
					this.Hair01.rotateAngleX += 0.12F;
					this.Hair02.rotateAngleX += 0.15F;
					this.Hair03.rotateAngleX += 0.25F;
		    	}
	    	}
	    }//end ridding
    
	    //攻擊動作    
	    if(ent.getAttackTime() > 0) {
	    	//arm
		  	this.ArmLeft01.rotateAngleX = -1.6F;
		  	this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft01.rotateAngleZ = 0.21F;
		    this.ArmLeft02.rotateAngleX = 0F;
		    this.ArmLeft02.rotateAngleZ = 0F;
	    }
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
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

