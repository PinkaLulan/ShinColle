package com.lulan.shincolle.client.model;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * ModelBattleshipTa - PinkaLulan 2015/3/30
 * Created using Tabula 4.1.1
 */
public class ModelBattleshipTa extends ModelBase {
    public ModelRenderer BodyMain;
    public ModelRenderer NeckCloth;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
    public ModelRenderer EquipLeft;
    public ModelRenderer EquipRight;
    public ModelRenderer Cloak01;
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
    public ModelRenderer HairR02;
    public ModelRenderer HairMidL01;
    public ModelRenderer HairMidL02;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer LegRight;
    public ModelRenderer LegLeft;
    public ModelRenderer ShoesR;
    public ModelRenderer ShoesL;
    public ModelRenderer Cloak02;
    public ModelRenderer Cloak03;
    public ModelRenderer Cloak04;
    public ModelRenderer Cloak05;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeckCloth;
    public ModelRenderer GlowHead;
    
    private int startEmo2 = 0;

    public ModelBattleshipTa() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        
        this.HairL01 = new ModelRenderer(this, 88, 100);
        this.HairL01.setRotationPoint(6.5F, 0.0F, -6.0F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.17453292519943295F, -0.17453292519943295F, -0.05235987755982988F);
        this.HairR02 = new ModelRenderer(this, 89, 103);
        this.HairR02.setRotationPoint(0.2F, 7F, 0.5F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.HairMidL01 = new ModelRenderer(this, 48, 34);
        this.HairMidL01.setRotationPoint(0.0F, 9.0F, 1.5F);
        this.HairMidL01.addBox(-7.5F, 0.0F, 0.0F, 15, 13, 9, 0.0F);
        this.setRotateAngle(HairMidL01, 0.2617993877991494F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.HairL02 = new ModelRenderer(this, 89, 103);
        this.HairL02.setRotationPoint(0.0F, 9F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(HairL02, 0.2617993877991494F, 0.0F, 0.05235987755982988F);
        this.HairMain = new ModelRenderer(this, 48, 56);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 10, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 56);
        this.ArmRight01.setRotationPoint(-7.0F, -10.5F, 0.0F);
        this.ArmRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.0F, 0.15707963267948966F);
        this.LegRight = new ModelRenderer(this, 0, 91);
        this.LegRight.setRotationPoint(-4.5F, 9.5F, -3.0F);
        this.LegRight.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegRight, -0.2617993877991494F, 0.0F, -0.05235987755982988F);
        this.EquipLeft = new ModelRenderer(this, 0, 0);
        this.EquipLeft.setRotationPoint(9.0F, -13.0F, -6.0F);
        this.EquipLeft.addBox(0.0F, 0.0F, 0.0F, 14, 6, 10, 0.0F);
        this.setRotateAngle(EquipLeft, 0.0F, -0.13962634015954636F, 0.2617993877991494F);
        this.BoobR = new ModelRenderer(this, 0, 74);
        this.BoobR.setRotationPoint(-3.8F, -9.0F, -3.5F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobR, -0.7853981633974483F, -0.17453292519943295F, -0.08726646259971647F);
        this.Butt = new ModelRenderer(this, 0, 19);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-8.0F, 4.0F, -5.5F, 16, 8, 7, 0.0F);
        this.setRotateAngle(Butt, 0.2617993877991494F, 0.0F, 0.0F);
        this.ShoesR = new ModelRenderer(this, 22, 71);
        this.ShoesR.setRotationPoint(0.0F, 7.0F, -0.2F);
        this.ShoesR.addBox(-3.5F, 0.0F, -3.5F, 7, 19, 7, 0.0F);
        this.HairR01 = new ModelRenderer(this, 88, 100);
        this.HairR01.setRotationPoint(-6.5F, 0.0F, -6.0F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.13962634015954636F, 0.17453292519943295F, 0.08726646259971647F);
        this.NeckCloth = new ModelRenderer(this, 46, 14);
        this.NeckCloth.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.NeckCloth.addBox(-7.5F, -1.5F, -4.5F, 15, 12, 8, 0.0F);
        this.Cloak01 = new ModelRenderer(this, 128, 0);
        this.Cloak01.setRotationPoint(0.0F, -10.0F, -4.4F);
        this.Cloak01.addBox(-11.5F, 0.0F, 0.0F, 23, 5, 10, 0.0F);
        this.LegLeft = new ModelRenderer(this, 0, 91);
        this.LegLeft.mirror = true;
        this.LegLeft.setRotationPoint(4.5F, 9.5F, -3.0F);
        this.LegLeft.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegLeft, -0.2617993877991494F, 0.0F, 0.05235987755982988F);
        this.Cloak02 = new ModelRenderer(this, 128, 15);
        this.Cloak02.setRotationPoint(0.0F, 5.0F, 0.3F);
        this.Cloak02.addBox(-12.0F, 0.0F, 0.0F, 24, 6, 10, 0.0F);
        this.setRotateAngle(Cloak02, 0.05235987755982988F, 0.0F, 0.0F);
        this.ShoesL = new ModelRenderer(this, 22, 71);
        this.ShoesL.mirror = true;
        this.ShoesL.setRotationPoint(0.0F, 7.0F, -0.2F);
        this.ShoesL.addBox(-3.5F, 0.0F, -3.5F, 7, 19, 7, 0.0F);
        this.Cloak05 = new ModelRenderer(this, 128, 67);
        this.Cloak05.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.Cloak05.addBox(-14.5F, 0.0F, 0.0F, 29, 9, 12, 0.0F);
        this.setRotateAngle(Cloak05, 0.08726646259971647F, 0.0F, 0.0F);
        this.NeckTie = new ModelRenderer(this, 24, 97);
        this.NeckTie.setRotationPoint(0.5F, 1.3F, -5.2F);
        this.NeckTie.addBox(-3.0F, 0.0F, 0.0F, 6, 7, 0, 0.0F);
        this.setRotateAngle(NeckTie, -0.7F, 0.13962634015954636F, 0.13962634015954636F);
        this.Cloak03 = new ModelRenderer(this, 128, 31);
        this.Cloak03.setRotationPoint(0.0F, 5.0F, 0.3F);
        this.Cloak03.addBox(-12.5F, 0.0F, 0.0F, 25, 7, 10, 0.0F);
        this.setRotateAngle(Cloak03, 0.05235987755982988F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 0, 56);
        this.ArmRight02.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.ArmRight02.addBox(-2.5F, 0.0F, -2.5F, 5, 13, 5, 0.0F);
        this.HairMidL02 = new ModelRenderer(this, 0, 34);
        this.HairMidL02.setRotationPoint(0.0F, 9.0F, 1.8F);
        this.HairMidL02.addBox(-7.0F, 0.0F, 0.0F, 14, 14, 7, 0.0F);
        this.setRotateAngle(HairMidL02, -0.08726646259971647F, 0.0F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 75);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 17, 8, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 0, 56);
        this.ArmLeft02.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.ArmLeft02.addBox(-2.5F, 0.0F, -2.5F, 5, 13, 5, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 56);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.0F, -10.5F, 0.0F);
        this.ArmLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.0F, 0.0F, -0.15707963267948966F);
        this.Ahoke = new ModelRenderer(this, 37, 101);
        this.Ahoke.setRotationPoint(0.0F, -7.3F, -7.5F);
        this.Ahoke.addBox(-4.5F, 0.0F, 0.0F, 10, 10, 0, 0.0F);
        this.setRotateAngle(Ahoke, -0.136659280431156F, -0.22759093446006054F, 0.0F);
        this.Cloak04 = new ModelRenderer(this, 128, 48);
        this.Cloak04.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Cloak04.addBox(-13.5F, 0.0F, 0.0F, 27, 8, 11, 0.0F);
        this.setRotateAngle(Cloak04, 0.08726646259971647F, 0.0F, 0.0F);  
        this.BoobL = new ModelRenderer(this, 0, 74);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.8F, -9.0F, -3.5F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobL, -0.7853981633974483F, 0.17453292519943295F, 0.08726646259971647F);
        this.EquipRight = new ModelRenderer(this, 38, 0);
        this.EquipRight.setRotationPoint(-9.0F, -12.0F, -2.0F);
        this.EquipRight.addBox(-12.0F, 0.0F, 0.0F, 12, 3, 3, 0.0F);
        this.setRotateAngle(EquipRight, 0.0F, 0.13962634015954636F, -0.17453292519943295F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Hair.addChild(this.HairL01);
        this.HairR01.addChild(this.HairR02);
        this.HairMain.addChild(this.HairMidL01);
        this.NeckCloth.addChild(this.Head);
        this.HairL01.addChild(this.HairL02);
        this.Head.addChild(this.HairMain);
        this.BodyMain.addChild(this.ArmRight01);
        this.Butt.addChild(this.LegRight);
        this.BodyMain.addChild(this.EquipLeft);
        this.BodyMain.addChild(this.BoobR);
        this.BodyMain.addChild(this.Butt);
        this.LegRight.addChild(this.ShoesR);
        this.Hair.addChild(this.HairR01);
        this.BodyMain.addChild(this.NeckCloth);
        this.BodyMain.addChild(this.Cloak01);
        this.Butt.addChild(this.LegLeft);
        this.Cloak01.addChild(this.Cloak02);
        this.LegLeft.addChild(this.ShoesL);
        this.Cloak04.addChild(this.Cloak05);
        this.NeckCloth.addChild(this.NeckTie);
        this.Cloak02.addChild(this.Cloak03);
        this.ArmRight01.addChild(this.ArmRight02);
        this.HairMidL01.addChild(this.HairMidL02);
        this.Head.addChild(this.Hair);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Hair.addChild(this.Ahoke);
        this.Cloak03.addChild(this.Cloak04);
        this.BodyMain.addChild(this.BoobL);
        this.BodyMain.addChild(this.EquipRight);
        
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
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		  
		BasicEntityShip ent = (BasicEntityShip)entity;
		  
		showEquip(ent);
		
		rollEmotion(ent);
		  
		motionHumanPos(f, f1, f2, f3, f4, ent);
		
		setGlowRotation();
    }
    
    //設定模型發光部份的rotation
    private void setGlowRotation() {
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
    
  //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, BasicEntityShip ent) {   
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleRun = MathHelper.cos(f * 0.7F) * f1 * 0.6F;
  		float angleRun2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.6F;
  		float addk1 = 0;
  		float addk2 = 0;
  		
    	GL11.glTranslatef(0F, 1.5F, 0F);
  		
  		//leg move parm
  		addk1 = angleRun - 0.35F;
	  	addk2 = angleRun2  - 0.087F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 / 57.29578F; 	//上下角度
	  	this.Head.rotateAngleY = f3 / 57.29578F;	//左右角度 角度轉成rad 即除以57.29578
	    
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = -angleX * 0.06F - 0.7854F;
  	    this.BoobR.rotateAngleX = -angleX * 0.06F - 0.7854F;
  	    this.NeckTie.rotateAngleX = -angleX * 0.1F - 0.7F;
  	    //hair
  	    this.HairMidL01.rotateAngleX = angleX * 0.1F + 0.2618F;
  	    this.HairMidL02.rotateAngleX = angleX * 0.1F - 0.087F;
	  	//Body
  	    this.Ahoke.rotateAngleZ = angleX * 0.1F - 0.06F;
	  	this.BodyMain.rotateAngleX = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = 0.35F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = 0.2618F;
	    this.ArmRight01.rotateAngleX = 0.35F;
		this.ArmRight01.rotateAngleZ = -0.2618F;
		//leg
		this.LegLeft.rotateAngleZ = 0.14F;
		this.LegRight.rotateAngleZ = -0.14F;
		//cloak
		this.EquipLeft.rotateAngleX = 0F;
		this.EquipRight.rotateAngleX = 0F;
		this.Cloak01.rotateAngleX = 0F;
		this.Cloak02.rotateAngleX = angleX * 0.05F + 0.15F;
		this.Cloak03.rotateAngleX = angleX * 0.05F + 0.18F;
		this.Cloak04.rotateAngleX = angleX * 0.05F + 0.15F;
		this.Cloak05.rotateAngleX = 0.2F;

	    if(ent.isSprinting() || f1 > 0.9F) {	//奔跑動作
	    	//leg move parm
		  	addk2 -= 0.35F;
	  	    //hair
	  	    this.HairMidL01.rotateAngleX = angleRun * 0.2F + 0.4F;
	  	    this.HairMidL02.rotateAngleX = angleRun2 * 0.2F + 0.4F;
		  	//Body
		  	this.BodyMain.rotateAngleX = 0.087F;
		  	this.BodyMain.rotateAngleY = 0F;
		    //arm 
		  	this.ArmLeft01.rotateAngleX = angleRun2;
		    this.ArmLeft01.rotateAngleZ = -0.1745F;
		    this.ArmRight01.rotateAngleX = angleRun;
			this.ArmRight01.rotateAngleZ = 0.1745F;
			//leg
			this.LegLeft.rotateAngleZ = 0.05F;
			this.LegRight.rotateAngleZ = -0.05F;
			//cloak
			this.Cloak02.rotateAngleX = angleRun * 0.05F + 0.3F;
			this.Cloak03.rotateAngleX = angleRun * 0.05F + 0.3F;
			this.Cloak04.rotateAngleX = angleRun * 0.05F + 0.35F;
			this.Cloak05.rotateAngleX = angleRun * 0.05F + 0.4F;
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
		    	
		    	if(ent.getRNG().nextInt(3) == 0) {
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
	    
	    if(ent.isSneaking()) {		//潛行, 蹲下動作
	    	//leg move parm
	    	addk1 -= 0.52F;
		  	addk2 -= 1F;
	  	    //hair
	  	    this.HairMidL01.rotateAngleX = angleX * 0.1F + 0.3F;
	  	    this.HairMidL02.rotateAngleX = angleX * 0.1F - 0.087F;
		  	//Body
		  	this.BodyMain.rotateAngleX = 0.7F;
		    //arm 
		  	this.ArmLeft01.rotateAngleX = -0.35F;
		    this.ArmLeft01.rotateAngleZ = 0.26F;
		    this.ArmRight01.rotateAngleX = -0.35F;
			this.ArmRight01.rotateAngleZ = -0.26F;
			//cloak
			this.Cloak02.rotateAngleX = angleX * 0.05F + 0.15F;
			this.Cloak03.rotateAngleX = angleX * 0.05F + 0.15F;
			this.Cloak04.rotateAngleX = angleX * 0.05F + 0.2F;
			this.Cloak05.rotateAngleX = angleX * 0.05F + 0.2F;
  		}//end if sneaking
  		
	    if(ent.isSitting() || ent.isRiding()) {  //騎乘動作
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
		    	GL11.glTranslatef(0F, 1.9F, 0F);
		    	//leg move parm
		    	addk1 = -0.087F;
			  	addk2 = 0.174F;
		  	    //hair
			  	this.Head.rotateAngleX -= 1.4F;
			  	this.Head.rotateAngleY *= 0.5F;
		  	    this.HairMidL01.rotateAngleX = angleX * 0.05F + 1.1F;
		  	    this.HairMidL02.rotateAngleX = angleX * 0.05F + 0.45F;
			  	//Body
			  	this.BodyMain.rotateAngleX = 1.4F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = -2.8F;
			    this.ArmLeft01.rotateAngleZ = -0.8727F;
			    this.ArmRight01.rotateAngleX = -2.6F;
				this.ArmRight01.rotateAngleZ = 0.35F;
				//leg
				this.LegLeft.rotateAngleZ = 0.2618F;
				this.LegRight.rotateAngleZ = -0.2618F;
				//cloak
				this.Cloak01.rotateAngleX = 0F;
				this.Cloak02.rotateAngleX = angleX * 0.01F + 0.15F;
				this.Cloak03.rotateAngleX = angleX * 0.01F + 0.18F;
				this.Cloak04.rotateAngleX = 0F;
				this.Cloak05.rotateAngleX = 0F;
	    	}
	    	else {
		    	GL11.glTranslatef(0F, 1.5F, 0F);
		    	//leg move parm
		    	addk1 = -1.0472F;
			  	addk2 = -1.3F;
		  	    //hair
			  	this.Head.rotateAngleX += 0.35F;
		  	    this.HairMidL01.rotateAngleX = angleX * 0.05F + 0.8F;
		  	    this.HairMidL02.rotateAngleX = angleX * 0.05F + 0.2F;
			  	//Body
			  	this.BodyMain.rotateAngleX = -0.7F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = 1.0472F;
			    this.ArmLeft01.rotateAngleZ = -0.2618F;
			    this.ArmRight01.rotateAngleX = 1.0472F;
				this.ArmRight01.rotateAngleZ = 0.2618F;
				//leg
				this.LegLeft.rotateAngleZ = 0.6F;
				this.LegRight.rotateAngleZ = -0.6F;
				//cloak
				this.EquipLeft.rotateAngleX = 0.7F;
				this.EquipRight.rotateAngleX = 0.7F;
				this.Cloak01.rotateAngleX = 0.7F;
				this.Cloak02.rotateAngleX = angleX * 0.03F + 0.15F;
				this.Cloak03.rotateAngleX = angleX * 0.03F + 0.15F;
				this.Cloak04.rotateAngleX = angleX * 0.03F + 0.5F;
				this.Cloak05.rotateAngleX = angleX * 0.03F + 0.2F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if(ent.attackTime > 0) {
	    	//arm
	    	this.ArmLeft01.rotateAngleX = -1.3F;
	    	this.ArmLeft01.rotateAngleY = -0.7F;
	    	this.ArmLeft01.rotateAngleZ = 0F;
	    	this.ArmRight01.rotateAngleX = 0.17F;
	    	this.ArmRight01.rotateAngleZ = 0.17F;
	    	this.EquipLeft.rotateAngleX = 0.2618F;
	    }
	    
	    //leg motion
	    this.LegLeft.rotateAngleX = addk1;
	    this.LegRight.rotateAngleX = addk2;
	    
  	}
  	
  	private void showEquip(BasicEntityShip ent) {
		if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
			this.EquipLeft.isHidden = false;
			this.EquipRight.isHidden = false;
		}
		else {
			this.EquipLeft.isHidden = true;
			this.EquipRight.isHidden = true;
		}
  	}
  	
  	//隨機抽取顯示的表情 
    private void rollEmotion(BasicEntityShip ent) { 
    	switch(ent.getStateEmotion(ID.S.Emotion)) {
    	case ID.Emotion.BLINK:	//blink
    		EmotionBlink(ent);
    		break;
    	case ID.Emotion.T_T:	//cry
    		if(ent.getStartEmotion() <= 0) { setFace(2); }
    		break;
    	case ID.Emotion.O_O:
    		EmotionStaring(ent);
			break;
    	case ID.Emotion.HUNGRY:
    		if(ent.getStartEmotion() <= 0) { setFace(4); }
			break;
    	case ID.Emotion.BORED:
    	default:						//normal face
    		//reset face to 0
    		if(ent.getStartEmotion() <= 0) setFace(0);
    		//roll emotion (3 times) every 6 sec
    		//1 tick in entity = 3 tick in model class (20 vs 60 fps)
    		if(ent.ticksExisted % 120 == 0) {
        		int emotionRand = ent.getRNG().nextInt(10);   		
        		if(emotionRand > 7) {
        			EmotionBlink(ent);
        		} 		
        	}
    		break;
    	}	
    }
    
    //眨眼動作, this emotion is CLIENT ONLY, no sync packet required
  	private void EmotionBlink(BasicEntityShip ent) {
  		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.NORMAL) {	//要在沒表情狀態才做表情		
  			ent.setStartEmotion(ent.ticksExisted);		//表情開始時間
  			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.BLINK, false);	//標記表情為blink
  		}
  		
  		int EmoTime = ent.ticksExisted - ent.getStartEmotion();
    	 		
    	if(EmoTime > 46) {	//reset face
    		setFace(0);
			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
			ent.setStartEmotion(-1);
    	}
    	else if(EmoTime > 35) {
    		setFace(1);
    	}
    	else if(EmoTime > 25) {
    		setFace(0);
    	}
    	else if(EmoTime > 1) {
    		setFace(1);
    	}		
  	}
  	
  	//瞪人表情
  	private void EmotionStaring(BasicEntityShip ent) {	
    	if(ent.getStartEmotion() == -1) {
			ent.setStartEmotion(ent.ticksExisted);		//表情開始時間
		}
    	
    	int EmoTime = ent.ticksExisted - ent.getStartEmotion();
    	
    	if(EmoTime > 41) {	//reset face
    		setFace(0);
			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
			ent.setStartEmotion(-1);
    	}
    	else if(EmoTime > 1) {
    		setFace(3);
    	}
	}
  	
    //設定顯示的臉型
  	private void setFace(int emo) {
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

