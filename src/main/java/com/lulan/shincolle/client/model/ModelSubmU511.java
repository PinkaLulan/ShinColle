package com.lulan.shincolle.client.model;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

/**
 * ModelSubmU511 - PinkaLulan 2015/4/24
 * Created using Tabula 4.1.1
 */
public class ModelSubmU511 extends ModelBase {
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
    public ModelRenderer Cloth01;
    public ModelRenderer EquipBase;
    public ModelRenderer Head;
    public ModelRenderer Pipe;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Face0;
    public ModelRenderer Hat01;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Hat02;
    public ModelRenderer Ear1;
    public ModelRenderer Ear2;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft03;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight03;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt;
    public ModelRenderer LegRight02;
    public ModelRenderer LegLeft02;
    public ModelRenderer EquipMid;
    public ModelRenderer EquipL;
    public ModelRenderer EquipR;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    
    private Random rand = new Random();
    private int startEmo2 = 0;

    public ModelSubmU511() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.Cloth01 = new ModelRenderer(this, 84, 0);
        this.Cloth01.setRotationPoint(0.0F, -11.5F, 0.0F);
        this.Cloth01.addBox(-7.0F, 0.0F, -4.5F, 14, 11, 8, 0.0F);
        this.HairL02 = new ModelRenderer(this, 88, 100);
        this.HairL02.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairL02, -0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.Hat01 = new ModelRenderer(this, 30, 24);
        this.Hat01.setRotationPoint(0.0F, -15.0F, -6.0F);
        this.Hat01.addBox(-3.0F, -6.0F, 0.5F, 6, 6, 13, 0.0F);
        this.setRotateAngle(Hat01, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipL = new ModelRenderer(this, 0, 23);
        this.EquipL.mirror = true;
        this.EquipL.setRotationPoint(11.5F, 0.0F, 4.0F);
        this.EquipL.addBox(0.0F, 0.0F, -20.0F, 5, 13, 20, 0.0F);
        this.setRotateAngle(EquipL, -0.3141592653589793F, -0.17453292519943295F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 88, 100);
        this.HairL01.mirror = true;
        this.HairL01.setRotationPoint(6.5F, 0.0F, -4.0F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.17453292519943295F, -0.17453292519943295F, -0.13962634015954636F);
        this.Neck = new ModelRenderer(this, 0, 0);
        this.Neck.setRotationPoint(0.0F, -10.5F, 0.0F);
        this.Neck.addBox(-4.5F, -2.0F, -6.0F, 9, 4, 10, 0.0F);
        this.setRotateAngle(Neck, 0.05235987755982988F, 0.0F, 0.0F);
        this.BoobL = new ModelRenderer(this, 33, 103);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.3F, -8.8F, -2.7F);
        this.BoobL.addBox(-3.0F, 0.0F, 0.0F, 6, 4, 4, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, 0.08726646259971647F, 0.08726646259971647F);
        this.ArmRight03 = new ModelRenderer(this, 28, 78);
        this.ArmRight03.setRotationPoint(0.0F, 3.0F, 1.0F);
        this.ArmRight03.addBox(-2.5F, 0.0F, -4.0F, 5, 12, 5, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -13.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 21, 7, 0.0F);
        this.HairR02 = new ModelRenderer(this, 88, 100);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 6.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairR02, -0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.Butt = new ModelRenderer(this, 80, 19);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-8.0F, 5.0F, -5.0F, 16, 9, 8, 0.0F);
        this.setRotateAngle(Butt, 0.2617993877991494F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 95);
        this.ArmRight02.setRotationPoint(-0.8F, 8.0F, 0.5F);
        this.ArmRight02.addBox(-2.5F, 0.0F, -3.0F, 5, 3, 5, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 67);
        this.ArmRight01.setRotationPoint(-7.7F, -10.3F, -0.7F);
        this.ArmRight01.addBox(-4.5F, -1.0F, -3.5F, 7, 9, 7, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.0F, 0.10471975511965977F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 67);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.7F, -10.3F, -0.7F);
        this.ArmLeft01.addBox(-2.5F, -1.0F, -3.5F, 7, 9, 7, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.0F, 0.0F, -0.10471975511965977F);
        this.HairR01 = new ModelRenderer(this, 88, 100);
        this.HairR01.setRotationPoint(-6.5F, 0.0F, -4.0F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.17453292519943295F, 0.17453292519943295F, 0.13962634015954636F);
        this.Skirt = new ModelRenderer(this, 80, 19);
        this.Skirt.setRotationPoint(0.0F, 5.0F, -2.0F);
        this.Skirt.addBox(-8.0F, 0.0F, -4.5F, 16, 9, 8, 0.0F);
        this.setRotateAngle(Skirt, 0.3490658503988659F, -3.141592653589793F, 0.0F);
        this.BoobR = new ModelRenderer(this, 33, 103);
        this.BoobR.setRotationPoint(-3.3F, -8.8F, -2.7F);
        this.BoobR.addBox(-3.0F, 0.0F, 0.0F, 6, 4, 4, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, -0.08726646259971647F, -0.08726646259971647F);
        this.EquipR = new ModelRenderer(this, 0, 23);
        this.EquipR.setRotationPoint(-11.5F, 0.0F, 4.0F);
        this.EquipR.addBox(-5.0F, 0.0F, -20.0F, 5, 13, 20, 0.0F);
        this.setRotateAngle(EquipR, -0.3141592653589793F, 0.17453292519943295F, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ArmLeft03 = new ModelRenderer(this, 28, 78);
        this.ArmLeft03.mirror = true;
        this.ArmLeft03.setRotationPoint(0.0F, 3.0F, 1.0F);
        this.ArmLeft03.addBox(-2.5F, 0.0F, -4.0F, 5, 12, 5, 0.0F);
        this.EquipMid = new ModelRenderer(this, 0, 0);
        this.EquipMid.setRotationPoint(0.0F, -5.0F, 2.0F);
        this.EquipMid.addBox(-13.0F, 0.0F, 0.0F, 26, 12, 5, 0.0F);
        this.setRotateAngle(EquipMid, 0.13962634015954636F, 0.0F, 0.0F);
        this.Ear2 = new ModelRenderer(this, 4, 18);
        this.Ear2.setRotationPoint(-8.0F, -1.0F, 0.0F);
        this.Ear2.addBox(0.0F, 0.0F, -4.0F, 0, 8, 5, 0.0F);
        this.setRotateAngle(Ear2, 0.0F, 0.0F, 0.2617993877991494F);
        this.Hair = new ModelRenderer(this, 50, 75);
        this.Hair.setRotationPoint(0.0F, -7.5F, -0.5F);
        this.Hair.addBox(-8.0F, -8.0F, -6.8F, 16, 17, 8, 0.0F);
        this.Hair01 = new ModelRenderer(this, 49, 47);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.1F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 18, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.2617993877991494F, 0.0F, 0.0F);
        this.HairMain = new ModelRenderer(this, 48, 47);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 10, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 67);
        this.LegRight02.setRotationPoint(0.0F, 13.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 14, 6, 0.0F);
        this.Hat02 = new ModelRenderer(this, 4, 17);
        this.Hat02.setRotationPoint(0.0F, 0.5F, 8.4F);
        this.Hat02.addBox(-8.0F, 0.0F, 0.5F, 16, 1, 5, 0.0F);
        this.setRotateAngle(Hat02, 0.3141592653589793F, 0.0F, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0.0F, -8.0F, -5.0F);
        this.Ahoke.addBox(0.0F, -4.0F, -11.0F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.5235987755982988F, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 67);
        this.LegLeft02.setRotationPoint(0.0F, 13.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 14, 6, 0.0F);
        this.Pipe = new ModelRenderer(this, 0, 17);
        this.Pipe.setRotationPoint(7.0F, -1.0F, -3.5F);
        this.Pipe.addBox(0.0F, -26.0F, 0.0F, 1, 25, 1, 0.0F);
        this.setRotateAngle(Pipe, -0.08726646259971647F, 0.0F, 0.08726646259971647F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Ear1 = new ModelRenderer(this, 4, 18);
        this.Ear1.mirror = true;
        this.Ear1.setRotationPoint(8.0F, -1.0F, 0.0F);
        this.Ear1.addBox(0.0F, 0.0F, -4.0F, 0, 8, 5, 0.0F);
        this.setRotateAngle(Ear1, 0.0F, 0.0F, -0.2617993877991494F);
        this.LegRight01 = new ModelRenderer(this, 0, 85);
        this.LegRight01.setRotationPoint(-3.8F, 9.5F, -2.7F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 13, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.2618F, 0.0F, -0.03490658503988659F);
        this.LegLeft01 = new ModelRenderer(this, 0, 85);
        this.LegLeft01.setRotationPoint(3.8F, 9.5F, -2.7F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 13, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.2618F, 0.0F, 0.03490658503988659F);
        this.EquipBase = new ModelRenderer(this, 60, 0);
        this.EquipBase.setRotationPoint(0.0F, 8.0F, 3.0F);
        this.EquipBase.addBox(-3.0F, 0.0F, 1.0F, 6, 16, 6, 0.0F);
        this.setRotateAngle(EquipBase, 0.4363323129985824F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 95);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(0.8F, 8.0F, 0.5F);
        this.ArmLeft02.addBox(-2.5F, 0.0F, -3.0F, 5, 3, 5, 0.0F);
        this.BodyMain.addChild(this.Cloth01);
        this.HairL01.addChild(this.HairL02);
        this.Head.addChild(this.Hat01);
        this.EquipMid.addChild(this.EquipL);
        this.Hair.addChild(this.HairL01);
        this.BodyMain.addChild(this.Neck);
        this.BodyMain.addChild(this.BoobL);
        this.ArmRight02.addChild(this.ArmRight03);
        this.HairR01.addChild(this.HairR02);
        this.BodyMain.addChild(this.Butt);
        this.ArmRight01.addChild(this.ArmRight02);
        this.BodyMain.addChild(this.ArmRight01);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Hair.addChild(this.HairR01);
        this.Butt.addChild(this.Skirt);
        this.BodyMain.addChild(this.BoobR);
        this.EquipMid.addChild(this.EquipR);
        this.ArmLeft02.addChild(this.ArmLeft03);
        this.EquipBase.addChild(this.EquipMid);
        this.Hat02.addChild(this.Ear2);
        this.Head.addChild(this.Hair);
        this.HairMain.addChild(this.Hair01);
        this.Head.addChild(this.HairMain);
        this.LegRight01.addChild(this.LegRight02);
        this.Hat01.addChild(this.Hat02);
        this.Hair.addChild(this.Ahoke);
        this.Neck.addChild(this.Head);
        this.LegLeft01.addChild(this.LegLeft02);
        this.Neck.addChild(this.Pipe);
        this.Hat02.addChild(this.Ear1);
        this.Butt.addChild(this.LegRight01);
        this.Butt.addChild(this.LegLeft01);
        this.BodyMain.addChild(this.EquipBase);
        this.ArmLeft01.addChild(this.ArmLeft02);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -13.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.5F, 0.0F);
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
    	GL11.glScalef(0.36F, 0.36F, 0.36F);

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
		this.GlowNeck.rotateAngleX = this.Neck.rotateAngleX;
		this.GlowNeck.rotateAngleY = this.Neck.rotateAngleY;
		this.GlowNeck.rotateAngleZ = this.Neck.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
    }
    
    //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {   
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		
    	GL11.glTranslatef(0F, 2.7F, 0F);
  		
  		//leg move parm
  		addk1 = angleAdd1 - 0.2618F;
	  	addk2 = angleAdd2 - 0.2618F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 / 57.29578F; 	//上下角度
	  	this.Head.rotateAngleY = f3 / 57.29578F;	//左右角度 角度轉成rad 即除以57.29578
	    
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.7F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.7F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
	  	this.BodyMain.rotateAngleX = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.06F + 0.3F;
	  	this.Ear1.rotateAngleZ = angleX * 0.1F - 0.2618F;
	  	this.Ear2.rotateAngleZ = angleX * 0.1F + 0.2618F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.5F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.5F;
    	this.ArmLeft01.rotateAngleZ = -angleX * 0.06F - 0.12F;
    	this.ArmRight01.rotateAngleZ = angleX * 0.06F + 0.12F;
    	this.ArmRight01.rotateAngleY = 0F;
    	this.ArmRight02.rotateAngleZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.035F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.035F;
		this.LegLeft02.rotateAngleX = 0F;
    	this.LegRight02.rotateAngleX = 0F;
		//equip
	  	this.Pipe.rotateAngleX = -0.0873F;
	  	//skirt
	  	this.Skirt.rotateAngleX = 0.35F;

	    if(ent.getIsSprinting() || f1 > 0.9F) {	//奔跑動作
	    	//無特殊奔跑動作
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
		    	
		    	if(((EntityLivingBase)ent).getRNG().nextInt(3) == 0) {
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
	    
	    if(ent.getIsSneaking()) {		//潛行, 蹲下動作
	    	//body
	    	this.Head.rotateAngleX -= 0.8727F;
	    	this.BodyMain.rotateAngleX = 1.0472F;
		  	//hair
		  	this.Hair01.rotateAngleX += 0.2236F;
		  	//leg
		  	addk1 -= 1.0472F;
		  	addk2 -= 1.0472F;
		  	//equip
		  	this.Pipe.rotateAngleX = -0.7854F;
		  	//skirt
		  	this.Skirt.rotateAngleX = 0.8727F;
  		}//end if sneaking
  		
	    if(ent.getIsSitting() || ent.getIsRiding()) {  //騎乘動作
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
		    	GL11.glTranslatef(0F, 1.5F, 0F);
		    	//body
		    	this.Head.rotateAngleX += 0.2618F;
		    	this.BodyMain.rotateAngleX = 0.35F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -angleX * 0.2F - 0.7F;
		    	this.ArmRight01.rotateAngleX = -0.96F;
		    	this.ArmRight01.rotateAngleY = -0.35F;
		    	this.ArmRight02.rotateAngleZ = -1.57F;
		    	//hair
		    	this.Hair01.rotateAngleX -= 0.25F;
		    	//leg
		    	addk1 = -2.8F;
		    	addk2 = -2.8F;
		    	this.LegLeft02.rotateAngleX = 1.4F;
		    	this.LegRight02.rotateAngleX = 1.4F;
			  	//skirt
			  	this.Skirt.rotateAngleX = 2.618F;
	    	}
	    	else {
		    	GL11.glTranslatef(0F, 1.5F, 0F);
		    	//body
		    	this.Head.rotateAngleX -= 0.7F;
		    	this.BodyMain.rotateAngleX = 0.5236F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -0.5236F;
		    	this.ArmLeft01.rotateAngleZ = 0.3146F;
		    	this.ArmRight01.rotateAngleX = -0.5236F;
		    	this.ArmRight01.rotateAngleZ = -0.3146F;
		    	//leg
		    	addk1 = -2.2689F;
		    	addk2 = -2.2689F;
		    	this.LegLeft01.rotateAngleY = -0.3491F;
		    	this.LegRight01.rotateAngleY = 0.3491F;
		    	//equip
			  	this.Pipe.rotateAngleX = -0.7854F;
			  	//skirt
			  	this.Skirt.rotateAngleX = 0.8727F;
	    	}
  		}//end if sitting
	    
//	    //攻擊動作    
//	    if(ent.getAttackTime() > 0) {
//	    	
//	    }
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
  	
  	private void showEquip(IShipEmotion ent) {
		if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
			this.EquipBase.isHidden = false;
		}
		else {
			this.EquipBase.isHidden = true;
		}
  	}
  	
  	//隨機抽取顯示的表情 
    private void rollEmotion(IShipEmotion ent) { 
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
    		//reset face to 0 or blink if emotion time > 0
    		if(ent.getStartEmotion() <= 0) {
    			setFace(0);
    		}
    		else {
    			EmotionBlink(ent);
    		}
    		//roll emotion (3 times) every 6 sec
    		//1 tick in entity = 3 tick in model class (20 vs 60 fps)
    		if(ent.getTickExisted() % 120 == 0) {
        		int emotionRand = ((EntityLivingBase)ent).getRNG().nextInt(10);   		
        		if(emotionRand > 7) {
        			EmotionBlink(ent);
        		} 		
        	}
    		break;
    	}	
    }
    
    //眨眼動作, this emotion is CLIENT ONLY, no sync packet required
    private void EmotionBlink(IShipEmotion ent) {
  		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.NORMAL) {	//要在沒表情狀態才做表情		
  			ent.setStartEmotion(ent.getTickExisted());		//表情開始時間
  			ent.setStateEmotion(ID.S.Emotion, ID.Emotion.BLINK, false);	//標記表情為blink
  			setFace(1);
  		}
  		
  		int EmoTime = ent.getTickExisted() - ent.getStartEmotion();
    	 		
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
    	else if(EmoTime > -1) {
    		setFace(1);
    	}
  	}
  	
  	//瞪人表情
  	private void EmotionStaring(IShipEmotion ent) {	
    	if(ent.getStartEmotion() == -1) {
			ent.setStartEmotion(ent.getTickExisted());		//表情開始時間
		}
    	
    	int EmoTime = ent.getTickExisted() - ent.getStartEmotion();
    	
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

