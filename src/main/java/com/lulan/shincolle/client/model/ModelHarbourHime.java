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
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.LogHelper;

/**
 * ModelHarbourHime - PinkaLulan 2015/6/7
 * Created using Tabula 4.1.1
 */
public class ModelHarbourHime extends ModelBase implements IModelEmotion {
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer ArmLeft01;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Face0;
    public ModelRenderer HeadH;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer HeadH2;
    public ModelRenderer HeadH3;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft03;
    public ModelRenderer ArmLeft04;
    public ModelRenderer ArmLeft05;
    public ModelRenderer ArmLeft06;
    public ModelRenderer ArmLeft07;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt;
    public ModelRenderer LegRight02;
    public ModelRenderer ShoesR;
    public ModelRenderer LegLeft02;
    public ModelRenderer ShoesL;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight03;
    public ModelRenderer ArmRight04;
    public ModelRenderer ArmRight05;
    public ModelRenderer ArmRight06;
    public ModelRenderer ArmRight07;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    
    private Random rand = new Random();
    private int startEmo2 = 0;

    public ModelHarbourHime() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.ArmRight05 = new ModelRenderer(this, 46, 0);
        this.ArmRight05.mirror = true;
        this.ArmRight05.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.ArmRight05.addBox(-5.5F, -0.2F, -6.5F, 11, 4, 13, 0.0F);
        this.HairL02 = new ModelRenderer(this, 24, 84);
        this.HairL02.setRotationPoint(-0.1F, 10.0F, 0.1F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairL02, 0.1745F, 0.0F, 0.08726646259971647F);
        this.Hair02 = new ModelRenderer(this, 0, 58);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 5.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 16, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.08726646259971647F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.setRotationPoint(-5.2F, 9.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.20943951023931953F, 0.0F, -0.05235987755982988F);
        this.ArmLeft04 = new ModelRenderer(this, 50, 60);
        this.ArmLeft04.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.ArmLeft04.addBox(-5.0F, 0.0F, -5.5F, 10, 6, 11, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 2, 85);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, -0.2617993877991494F, 0.6981317007977318F, 0.0F);
        this.ArmRight03 = new ModelRenderer(this, 46, 46);
        this.ArmRight03.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.ArmRight03.addBox(-4.0F, 0.0F, -4.5F, 8, 5, 9, 0.0F);
        this.setRotateAngle(ArmRight03, 0.0F, -0.3490658503988659F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 72, 38);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(0.5F, 4.0F, 0.0F);
        this.ArmLeft02.addBox(-3.0F, 0.0F, -3.5F, 6, 10, 7, 0.0F);
        this.HeadH = new ModelRenderer(this, 33, 101);
        this.HeadH.mirror = true;
        this.HeadH.setRotationPoint(0.0F, -10.0F, -6.5F);
        this.HeadH.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(HeadH, -0.3141592653589793F, 0.0F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 2, 85);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(ArmRight01, -0.2617993877991494F, -0.6981317007977318F, 0.0F);
        this.Hair = new ModelRenderer(this, 45, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 16, 8, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(5.2F, 9.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.20943951023931953F, 0.0F, 0.05235987755982988F);
        this.HeadH3 = new ModelRenderer(this, 45, 105);
        this.HeadH3.setRotationPoint(0.0F, 0.0F, -3.8F);
        this.HeadH3.addBox(-1.0F, -1.2F, -3.0F, 2, 2, 4, 0.0F);
        this.setRotateAngle(HeadH3, -0.13962634015954636F, 0.0F, 0.0F);
        this.Skirt = new ModelRenderer(this, 0, 19);
        this.Skirt.setRotationPoint(0.0F, 6.9F, -6.0F);
        this.Skirt.addBox(-8.5F, 0.0F, 0.0F, 17, 6, 9, 0.0F);
        this.setRotateAngle(Skirt, -0.13962634015954636F, 0.0F, 0.0F);
        this.Hair01 = new ModelRenderer(this, 0, 57);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 17, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.20943951023931953F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 46, 33);
        this.BoobR.setRotationPoint(-3.9F, -8.1F, -4.0F);
        this.BoobR.addBox(-4.0F, 0.0F, 0.0F, 8, 6, 6, 0.0F);
        this.setRotateAngle(BoobR, -0.8726646259971648F, 0.08726646259971647F, -0.08726646259971647F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 0);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-7.5F, 4.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3141592653589793F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ArmRight04 = new ModelRenderer(this, 50, 60);
        this.ArmRight04.mirror = true;
        this.ArmRight04.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.ArmRight04.addBox(-5.0F, 0.0F, -5.5F, 10, 6, 11, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.BoobL = new ModelRenderer(this, 46, 33);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.9F, -8.1F, -4.0F);
        this.BoobL.addBox(-4.0F, 0.0F, 0.0F, 8, 6, 6, 0.0F);
        this.setRotateAngle(BoobL, -0.8726646259971648F, -0.08726646259971647F, 0.08726646259971647F);
        this.Hair03 = new ModelRenderer(this, 0, 35);
        this.Hair03.setRotationPoint(0.0F, 12.5F, -0.1F);
        this.Hair03.addBox(-8.0F, 0.0F, -4.5F, 16, 15, 7, 0.0F);
        this.setRotateAngle(Hair03, -0.13962634015954636F, 0.0F, 0.0F);
        this.HairR02 = new ModelRenderer(this, 24, 84);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.1F, 10.0F, 0.1F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairR02, 0.1745F, 0.0F, -0.05235987755982988F);
        this.LegLeft02 = new ModelRenderer(this, 0, 84);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
        this.ArmLeft06 = new ModelRenderer(this, 68, 17);
        this.ArmLeft06.setRotationPoint(-2.0F, 1.0F, 0.5F);
        this.ArmLeft06.addBox(0.0F, 0.0F, -4.2F, 5, 9, 9, 0.0F);
        this.setRotateAngle(ArmLeft06, 0.08726646259971647F, 0.13962634015954636F, 0.2617993877991494F);
        this.ArmRight06 = new ModelRenderer(this, 68, 17);
        this.ArmRight06.mirror = true;
        this.ArmRight06.setRotationPoint(1.0F, 1.0F, 0.5F);
        this.ArmRight06.addBox(-4.0F, 0.0F, -4.2F, 5, 9, 9, 0.0F);
        this.setRotateAngle(ArmRight06, 0.08726646259971647F, -0.13962634015954636F, -0.2617993877991494F);
        this.ShoesL = new ModelRenderer(this, 100, 0);
        this.ShoesL.mirror = true;
        this.ShoesL.setRotationPoint(0.0F, 1.0F, 3.0F);
        this.ShoesL.addBox(-3.5F, 0.0F, -3.5F, 7, 14, 7, 0.0F);
        this.setRotateAngle(ShoesL, 0.0F, 0.0F, 0.03647738136668149F);
        this.ArmLeft03 = new ModelRenderer(this, 46, 46);
        this.ArmLeft03.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.ArmLeft03.addBox(-4.0F, 0.0F, -4.5F, 8, 5, 9, 0.0F);
        this.setRotateAngle(ArmLeft03, 0.0F, 0.3490658503988659F, 0.0F);
        this.ArmLeft07 = new ModelRenderer(this, 43, 18);
        this.ArmLeft07.setRotationPoint(-1.0F, 0.0F, -2.0F);
        this.ArmLeft07.addBox(0.0F, 0.0F, -3.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(ArmLeft07, -0.2617993877991494F, 0.0F, 0.17453292519943295F);
        this.ArmRight07 = new ModelRenderer(this, 43, 18);
        this.ArmRight07.setRotationPoint(1.0F, 0.0F, -2.0F);
        this.ArmRight07.addBox(-3.0F, 0.0F, -3.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(ArmRight07, -0.2617993877991494F, 0.0F, -0.17453292519943295F);
        this.Neck = new ModelRenderer(this, 88, 26);
        this.Neck.setRotationPoint(0.0F, -10.3F, -0.2F);
        this.Neck.addBox(-5.5F, -2.0F, -5.0F, 11, 3, 9, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 72, 38);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-0.5F, 4.0F, 0.0F);
        this.ArmRight02.addBox(-3.0F, 0.0F, -3.5F, 6, 10, 7, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.HairL01 = new ModelRenderer(this, 24, 84);
        this.HairL01.setRotationPoint(7F, 3.0F, -5.5F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairL01, -0.14F, -0.1745F, -0.0873F);
        this.ShoesR = new ModelRenderer(this, 100, 0);
        this.ShoesR.setRotationPoint(0.0F, 1.0F, 3.0F);
        this.ShoesR.addBox(-3.5F, 0.0F, -3.5F, 7, 14, 7, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 84);
        this.LegRight02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 6, 6, 0.0F);
        this.HeadH2 = new ModelRenderer(this, 84, 64);
        this.HeadH2.setRotationPoint(0.0F, -0.2F, -3.7F);
        this.HeadH2.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 4, 0.0F);
        this.setRotateAngle(HeadH2, -0.13962634015954636F, 0.0F, 0.0F);
        this.HairR01 = new ModelRenderer(this, 24, 84);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-7F, 3.0F, -5.5F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairR01, -0.14F, 0.1745F, 0.0873F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0.0F, -9.0F, -5.5F);
        this.Ahoke.addBox(0.0F, -4.0F, -11.5F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, -0.17453292519943295F, 0.6981317007977318F, 0.0F);
        this.HairMain = new ModelRenderer(this, 0, 57);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 12, 10, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ArmLeft05 = new ModelRenderer(this, 46, 0);
        this.ArmLeft05.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.ArmLeft05.addBox(-5.5F, -0.2F, -6.5F, 11, 4, 13, 0.0F);
        this.ArmRight04.addChild(this.ArmRight05);
        this.HairL01.addChild(this.HairL02);
        this.Hair01.addChild(this.Hair02);
        this.Butt.addChild(this.LegRight01);
        this.ArmLeft03.addChild(this.ArmLeft04);
        this.BodyMain.addChild(this.ArmLeft01);
        this.ArmRight02.addChild(this.ArmRight03);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.BodyMain.addChild(this.ArmRight01);
        this.Head.addChild(this.Hair);
        this.Butt.addChild(this.LegLeft01);
        this.Butt.addChild(this.Skirt);
        this.HairMain.addChild(this.Hair01);
        this.BodyMain.addChild(this.BoobR);
        this.BodyMain.addChild(this.Butt);
        this.Neck.addChild(this.Head);
        this.ArmRight03.addChild(this.ArmRight04);
        this.BodyMain.addChild(this.BoobL);
        this.Hair02.addChild(this.Hair03);
        this.HairR01.addChild(this.HairR02);
        this.LegLeft01.addChild(this.LegLeft02);
        this.ArmLeft05.addChild(this.ArmLeft06);
        this.ArmRight05.addChild(this.ArmRight06);
        this.LegLeft02.addChild(this.ShoesL);
        this.ArmLeft02.addChild(this.ArmLeft03);
        this.ArmLeft06.addChild(this.ArmLeft07);
        this.ArmRight06.addChild(this.ArmRight07);
        this.BodyMain.addChild(this.Neck);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Hair.addChild(this.HairL01);
        this.LegRight02.addChild(this.ShoesR);
        this.LegRight01.addChild(this.LegRight02);
        
        this.Hair.addChild(this.HairR01);
        this.Hair.addChild(this.Ahoke);
        this.Head.addChild(this.HairMain);
        this.ArmLeft04.addChild(this.ArmLeft05);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, -0.2F);
        this.setRotateAngle(GlowNeck, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.5F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        this.GlowHead.addChild(this.HeadH);
        this.HeadH.addChild(this.HeadH2);
        this.HeadH2.addChild(this.HeadH3);
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
    	GL11.glScalef(0.53F, 0.53F, 0.53F);
    	GL11.glTranslatef(0F, 1.35F, 0F);
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	GL11.glDisable(GL11.GL_LIGHTING);
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glPopMatrix();
    }

	//for idle/run animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) { 	
    	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		IShipEmotion ent = (IShipEmotion)entity;
		
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
		this.GlowNeck.rotateAngleX = this.Neck.rotateAngleX;
		this.GlowNeck.rotateAngleY = this.Neck.rotateAngleY;
		this.GlowNeck.rotateAngleZ = this.Neck.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
    }
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {
    	GL11.glTranslatef(0F, 2.0F, 0F);
    	setFace(4);
    	
    	if(((Entity)ent).ridingEntity instanceof BasicEntityMount) {
  			GL11.glTranslatef(0F, 2.5F, 0F);
  		}
    	
  	    //頭部
	  	this.Head.rotateAngleX = -0.35F;
	  	this.Head.rotateAngleY = 0F;
	  	this.Head.rotateAngleZ = 0F;
	    //胸部
  	    this.BoobL.rotateAngleX = -0.76F;
  	    this.BoobR.rotateAngleX = -0.76F;
	  	//Body
  	    this.Ahoke.rotateAngleY = 0.6F;
	  	this.BodyMain.rotateAngleX = 1.4835F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 1.0472F;
	  	this.Butt.offsetZ = -0.05F;
	  	this.Skirt.offsetY = -0.1F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.35F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = 0.2F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -0.3F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	this.HairL01.rotateAngleX = -0.14F;
	  	this.HairL02.rotateAngleX = 0.17F;
	  	this.HairR01.rotateAngleX = -0.14F;
	  	this.HairR02.rotateAngleX = 0.17F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -2.967F;
	  	this.ArmLeft01.rotateAngleY = -0.6981F;
	    this.ArmLeft01.rotateAngleZ = 0.08F;
	    this.ArmLeft03.rotateAngleX = 0F;
	    this.ArmLeft03.rotateAngleY = 0.35F;
	    this.ArmLeft03.rotateAngleZ = 0F;
	    this.ArmLeft06.rotateAngleX = 0.0873F;
	  	this.ArmLeft06.rotateAngleY = 0.14F;
	    this.ArmLeft06.rotateAngleZ = 0.26F;
	    this.ArmLeft07.rotateAngleX = -0.2618F;
	    this.ArmRight01.rotateAngleX = -2.967F;
	  	this.ArmRight01.rotateAngleY = 0.6981F;
	    this.ArmRight01.rotateAngleZ = -0.08F;
	    this.ArmRight03.rotateAngleX = 0F;
	    this.ArmRight03.rotateAngleY = -0.35F;
	    this.ArmRight03.rotateAngleZ = 0F;
	    this.ArmRight06.rotateAngleZ = -0.26F;
	    this.ArmRight07.rotateAngleX = -0.2618F;
		//leg
	    this.LegLeft02.offsetZ = 0F;
    	this.LegRight02.offsetZ = 0F;
    	this.LegLeft01.rotateAngleX = -1.7F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.05F;
		this.LegLeft02.rotateAngleX = 0.7F;
		this.LegRight01.rotateAngleX = -1.7F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.05F;
		this.LegRight02.rotateAngleX = 0.7F;
    }
    
    private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {   
  		float angleX = MathHelper.cos(f2 * 0.08F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.08F + 0.6F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		float headX = 0F;
  		float headZ = 0F;
  		
  		//水上漂浮
  		if(((IShipFloating)ent).getShipDepth() > 0) {
    		GL11.glTranslatef(0F, angleX * 0.1F - 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = angleAdd1 * 0.6F - 0.21F;
	  	addk2 = angleAdd2 * 0.6F - 0.21F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 / 70F; 	//上下角度
	  	this.Head.rotateAngleY = f3 / 80F;	//左右角度 角度轉成rad 即除以57.29578
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = angleX * 0.08F - 0.76F;
  	    this.BoobR.rotateAngleX = angleX * 0.08F - 0.76F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.15F + 0.6F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.3142F;
	  	this.Butt.offsetZ = 0F;
	  	this.Skirt.offsetY = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.21F + headX;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.08F + headX;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -angleX2 * 0.07F - 0.14F;
	  	this.Hair03.rotateAngleZ = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -0.2618F;
	  	this.ArmLeft01.rotateAngleY = 0.7F;
	    this.ArmLeft01.rotateAngleZ = 0F;
	    this.ArmLeft03.rotateAngleX = 0F;
	    this.ArmLeft03.rotateAngleY = 0.35F;
	    this.ArmLeft03.rotateAngleZ = 0F;
	    this.ArmLeft06.rotateAngleX = 0.0873F;
	  	this.ArmLeft06.rotateAngleY = 0.14F;
	    this.ArmLeft06.rotateAngleZ = angleX * 0.1F + 0.26F;
	    this.ArmLeft07.rotateAngleX = -0.2618F;
	    this.ArmRight01.rotateAngleX = -0.2618F;
	  	this.ArmRight01.rotateAngleY = -0.7F;
	    this.ArmRight01.rotateAngleZ = 0F;
	    this.ArmRight03.rotateAngleX = 0F;
	    this.ArmRight03.rotateAngleY = -0.35F;
	    this.ArmRight03.rotateAngleZ = 0F;
	    this.ArmRight06.rotateAngleZ = -angleX * 0.1F - 0.26F;
	    this.ArmRight07.rotateAngleX = -0.2618F;
		//leg
	    this.LegLeft02.offsetZ = 0F;
    	this.LegRight02.offsetZ = 0F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.05F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.05F;
		this.LegRight02.rotateAngleX = 0F;

	    if(ent.getIsSprinting() || f1 > 0.9F) {	//奔跑動作
	    	//hair
			this.Hair01.rotateAngleX += 0.09F;
			this.Hair02.rotateAngleX += 0.43F;
			this.Hair03.rotateAngleX += 0.49F;
			//胸部
	  	    this.BoobL.rotateAngleX = angleAdd2 * 0.1F - 0.83F;
	  	    this.BoobR.rotateAngleX = angleAdd1 * 0.1F - 0.83F;
	    	//arm 
		  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.8F + 0.1745F;
		  	this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft01.rotateAngleZ = -0.35F;
		    this.ArmLeft03.rotateAngleY = 0F;
		    this.ArmRight01.rotateAngleX = angleAdd1 * 0.8F + 0.1745F;
		  	this.ArmRight01.rotateAngleY = 0F;
		    this.ArmRight01.rotateAngleZ = 0.35F;
		    this.ArmRight03.rotateAngleY = 0F;
  		}
//	    else {
//	    	startEmo2 = ent.getHeadTiltTick();
//	    	
//	    	//頭部傾斜動作, 只在奔跑以外時roll
//	    	if(startEmo2 > 0) {
//	    		--startEmo2;
//	    		ent.setHeadTiltTick(startEmo2);
//	    	}
//	    	
//		    if(startEmo2 <= 0) {
//		    	startEmo2 = 80;
//		    	ent.setHeadTiltTick(startEmo2);	//cd = 6sec  	
//		    	
//		    	if(rand.nextInt(3) == 0) {
//		    		ent.setStateFlag(ID.F.HeadTilt, true);
//		    	}
//		    	else {
//		    		ent.setStateFlag(ID.F.HeadTilt, false);
//		    	}
//		    }
//	    }//end if sprint
//	    //roll頭部傾斜表情
//	    if(ent.getStateFlag(ID.F.HeadTilt)) {
//	    	if(ent.getStateEmotion(ID.S.Emotion2) == 1) {	//之前已經傾斜, 則繼續傾斜
//	    		this.Head.rotateAngleZ = -0.24F;
//	    	}
//	    	else {
//		    	this.Head.rotateAngleZ = (80 - startEmo2) * -0.03F;
//		    	
//		    	if(this.Head.rotateAngleZ < -0.24F) {
//		    		ent.setStateEmotion(ID.S.Emotion2, 1, false);
//		    		this.Head.rotateAngleZ = -0.24F;
//		    	}
//	    	}	
//	    }
//	    else {
//	    	if(ent.getStateEmotion(ID.S.Emotion2) == 0) {	//維持之前角度
//	    		this.Head.rotateAngleZ = 0F;
//	    	}
//	    	else {
//		    	this.Head.rotateAngleZ = -0.24F + (80 - startEmo2) * 0.03F;
//		    	
//		    	if(this.Head.rotateAngleZ > 0F) {
//		    		this.Head.rotateAngleZ = 0F;
//		    		ent.setStateEmotion(ID.S.Emotion2, 0, false);
//		    	}
//	    	}
//	    }

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    //移動頭髮避免穿過身體
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	this.HairL01.rotateAngleZ = headZ - 0.14F;
	  	this.HairL02.rotateAngleZ = headZ + 0.087F;
	  	this.HairR01.rotateAngleZ = headZ + 0.14F;
	  	this.HairR02.rotateAngleZ = headZ - 0.052F;
	    
	    if(ent.getIsSneaking()) {		//潛行, 蹲下動作
	    	GL11.glTranslatef(0F, 0.1F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		    //arm
		  	this.ArmLeft01.rotateAngleX = -0.61F;
		  	this.ArmLeft01.rotateAngleY = 0.35F;
		    this.ArmLeft01.rotateAngleZ = -0.14F;
		  	this.ArmLeft03.rotateAngleY = 0.7F;
		  	this.ArmLeft06.rotateAngleZ = -0.35F;
			this.ArmRight01.rotateAngleX = -0.61F;
			this.ArmRight01.rotateAngleY = -0.35F;
			this.ArmRight01.rotateAngleZ = 0.14F;
			this.ArmRight03.rotateAngleY = -0.7F;
			this.ArmRight06.rotateAngleZ = 0.35F;
			//leg
			addk1 -= 1.0F;
			addk2 -= 1.0F;
			//hair
			this.Hair01.rotateAngleX += 0.37F;
			this.Hair02.rotateAngleX += 0.23F;
			this.Hair03.rotateAngleX -= 0.1F;
  		}//end if sneaking
  		
	    if(ent.getIsSitting() && !ent.getIsRiding()) {  //騎乘動作  	
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
		    	setFace(2);
		    	GL11.glTranslatef(0F, 1.6F, 0F);
		    	//body
		    	this.Head.rotateAngleX = this.Head.rotateAngleX * 0.5F + 0.55F;
			  	this.Head.rotateAngleY = this.Head.rotateAngleY * 0.5F - 0.2F;
		    	this.BodyMain.rotateAngleX = -0.61F;
			  	this.BodyMain.rotateAngleY = -0.2618F;
			  	this.BodyMain.rotateAngleZ = -0.5236F;
		    	//arm
			  	this.ArmLeft01.rotateAngleX = 1.3F;
			  	this.ArmLeft01.rotateAngleY = 0.7F;
			    this.ArmLeft01.rotateAngleZ = -0.1745F;
			    this.ArmLeft03.rotateAngleX = -2.53F;
			  	this.ArmLeft03.rotateAngleY = -0.7F;
			  	this.ArmLeft06.rotateAngleX = -0.5236F;
			  	this.ArmLeft06.rotateAngleY = -0.5236F;
			  	this.ArmLeft06.rotateAngleZ = 0.7F;
				this.ArmRight01.rotateAngleX = 0.7F;
				this.ArmRight01.rotateAngleY = 0F;
				this.ArmRight01.rotateAngleZ = 0.5236F;
				this.ArmRight03.rotateAngleX = -1.57F;
				this.ArmRight03.rotateAngleY = 0.14F;
				this.ArmRight03.rotateAngleZ = 1.7453F;
				this.ArmRight06.rotateAngleZ = -0.5236F;
		    	//leg
		    	addk1 = -1.05F;
		    	addk2 = -1.31F;
		    	this.LegLeft01.rotateAngleZ = -0.5236F;
		    	this.LegLeft02.rotateAngleX = 1.05F;
		    	this.LegRight01.rotateAngleY = -0.4363F;
		    	this.LegRight02.rotateAngleX = 0.7F;
		    	//hair
		    	this.Hair01.rotateAngleX -= 0.12F;
		    	this.Hair01.rotateAngleZ = -0.09F;
			  	this.Hair02.rotateAngleX -= 0.18F;
			  	this.Hair02.rotateAngleZ = -0.26F;
			  	this.Hair03.rotateAngleX -= 0.21F;
			  	this.Hair03.rotateAngleZ = -0.35F;
	    	}
	    	else {
		    	GL11.glTranslatef(0F, 1.15F, 0F);
		    	//arm
			  	this.ArmLeft01.rotateAngleX = -0.44F;
			  	this.ArmLeft01.rotateAngleY = 0.44F;
			    this.ArmLeft01.rotateAngleZ = 0F;
			  	this.ArmLeft03.rotateAngleY = 0.87F;
			  	this.ArmLeft06.rotateAngleZ = 0.1F;
				this.ArmRight01.rotateAngleX = -0.44F;
				this.ArmRight01.rotateAngleY = -0.44F;
				this.ArmRight01.rotateAngleZ = 0F;
				this.ArmRight03.rotateAngleY = -0.87F;
				this.ArmRight06.rotateAngleZ = -0.1F;
		    	//leg
		    	addk1 = -1.2217F;
		    	addk2 = -1.2217F;
		    	this.LegLeft02.offsetZ = 0.37F;
		    	this.LegRight02.offsetZ = 0.37F;
		    	this.LegLeft01.rotateAngleY = 0.14F;
		    	this.LegRight01.rotateAngleY = -0.14F;
		    	this.LegLeft02.rotateAngleX = 2.53F;
		    	this.LegRight02.rotateAngleX = 2.53F;
	    	}
  		}//end sitting
	    
	    if(ent.getIsRiding()) {
	    	if(((Entity)ent).ridingEntity instanceof BasicEntityMount) {
	    		if(((IShipFloating)((Entity)ent).ridingEntity).getShipDepth() > 0) {
	        		GL11.glTranslatef(0F, -0.4F, 0F);
	        	}
	    		
	    		if(ent.getIsSitting()) {
	    			if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
		    			setFace(2);
		    			GL11.glTranslatef(0F, 4.1F, 0F);
		    			//body
				    	this.Head.rotateAngleX = this.Head.rotateAngleX * 0.5F + 0.55F;
					  	this.Head.rotateAngleY = this.Head.rotateAngleY * 0.5F - 0.2F;
				    	this.BodyMain.rotateAngleX = -0.61F;
					  	this.BodyMain.rotateAngleY = -0.2618F;
					  	this.BodyMain.rotateAngleZ = -0.5236F;
				    	//arm
					  	this.ArmLeft01.rotateAngleX = 1.3F;
					  	this.ArmLeft01.rotateAngleY = 0.7F;
					    this.ArmLeft01.rotateAngleZ = -0.1745F;
					    this.ArmLeft03.rotateAngleX = -2.53F;
					  	this.ArmLeft03.rotateAngleY = -0.7F;
					  	this.ArmLeft06.rotateAngleX = -0.5236F;
					  	this.ArmLeft06.rotateAngleY = -0.5236F;
					  	this.ArmLeft06.rotateAngleZ = 0.7F;
						this.ArmRight01.rotateAngleX = 0.7F;
						this.ArmRight01.rotateAngleY = 0F;
						this.ArmRight01.rotateAngleZ = 0.5236F;
						this.ArmRight03.rotateAngleX = -1.57F;
						this.ArmRight03.rotateAngleY = 0.14F;
						this.ArmRight03.rotateAngleZ = 1.7453F;
						this.ArmRight06.rotateAngleZ = -0.5236F;
				    	//leg
				    	addk1 = -1.05F;
				    	addk2 = -1.31F;
				    	this.LegLeft01.rotateAngleZ = -0.5236F;
				    	this.LegLeft02.rotateAngleX = 1.05F;
				    	this.LegRight01.rotateAngleY = -0.4363F;
				    	this.LegRight02.rotateAngleX = 0.7F;
				    	//hair
				    	this.Hair01.rotateAngleX -= 0.12F;
				    	this.Hair01.rotateAngleZ = -0.09F;
					  	this.Hair02.rotateAngleX -= 0.18F;
					  	this.Hair02.rotateAngleZ = -0.26F;
					  	this.Hair03.rotateAngleX -= 0.21F;
					  	this.Hair03.rotateAngleZ = -0.35F;
			    	}
			    	else {
			    		GL11.glTranslatef(0F, 3.6F, 0F);
			    		//body
				    	this.Head.rotateAngleX -= 0.35F;
				    	//hair
				    	this.Hair01.rotateAngleX += 0.35F;
					    //arm 
					  	this.ArmLeft01.rotateAngleX = 0.2F;
					  	this.ArmLeft01.rotateAngleY = 0F;
					    this.ArmLeft01.rotateAngleZ = -1.1F;
					    this.ArmLeft03.rotateAngleY = 0F;
					    this.ArmLeft03.rotateAngleZ = -0.4F;
						this.ArmRight01.rotateAngleX = 0.2F;
						this.ArmRight01.rotateAngleY = 0F;
						this.ArmRight01.rotateAngleZ = 1.1F;
						this.ArmRight03.rotateAngleZ = 0.4F;
						//leg
				    	addk1 = -1.2217F;
				    	addk2 = -1.2217F;
				    	this.LegLeft02.offsetZ = 0.37F;
				    	this.LegRight02.offsetZ = 0.37F;
				    	this.LegLeft01.rotateAngleY = 0.14F;
				    	this.LegRight01.rotateAngleY = -0.14F;
				    	this.LegLeft02.rotateAngleX = 2.53F;
				    	this.LegRight02.rotateAngleX = 2.53F;
			    	}
		    	}//end if sitting
		    	else {
		    		GL11.glTranslatef(0F, 2.5F, 0.3F);
		    		//body
			    	this.Head.rotateAngleX -= 0.35F;
			    	//hair
			    	this.Hair01.rotateAngleX += 0.35F;
		    		//arm 
				  	this.ArmLeft01.rotateAngleX = 0.5F;
				  	this.ArmLeft01.rotateAngleY = 0F;
				    this.ArmLeft01.rotateAngleZ = -0.7F;
				    this.ArmLeft03.rotateAngleX = -0.5F;
				    this.ArmLeft03.rotateAngleY = 0F;
				    this.ArmLeft03.rotateAngleZ = -0.4F;
				    this.ArmLeft06.rotateAngleZ = 0.4F;
				    this.ArmLeft07.rotateAngleX = -1.2F;
					this.ArmRight01.rotateAngleX = 0.5F;
					this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = 0.7F;
					this.ArmRight03.rotateAngleX = -0.5F;
					this.ArmRight03.rotateAngleY = 0F;
					this.ArmRight03.rotateAngleZ = 0.4F;
					this.ArmRight06.rotateAngleZ = -0.4F;
					this.ArmRight07.rotateAngleX = -1.2F;
		    	}
	    	}//end ship mount
	    	else {	//normal mount ex: cart
	    		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
	    			setFace(2);
	    			GL11.glTranslatef(0F, 1.6F, 0F);
			    	//body
			    	this.Head.rotateAngleX = this.Head.rotateAngleX * 0.5F + 0.55F;
				  	this.Head.rotateAngleY = this.Head.rotateAngleY * 0.5F - 0.2F;
			    	this.BodyMain.rotateAngleX = -0.61F;
				  	this.BodyMain.rotateAngleY = -0.2618F;
				  	this.BodyMain.rotateAngleZ = -0.5236F;
			    	//arm
				  	this.ArmLeft01.rotateAngleX = 1.3F;
				  	this.ArmLeft01.rotateAngleY = 0.7F;
				    this.ArmLeft01.rotateAngleZ = -0.1745F;
				    this.ArmLeft03.rotateAngleX = -2.53F;
				  	this.ArmLeft03.rotateAngleY = -0.7F;
				  	this.ArmLeft06.rotateAngleX = -0.5236F;
				  	this.ArmLeft06.rotateAngleY = -0.5236F;
				  	this.ArmLeft06.rotateAngleZ = 0.7F;
					this.ArmRight01.rotateAngleX = 0.7F;
					this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = 0.5236F;
					this.ArmRight03.rotateAngleX = -1.57F;
					this.ArmRight03.rotateAngleY = 0.14F;
					this.ArmRight03.rotateAngleZ = 1.7453F;
					this.ArmRight06.rotateAngleZ = -0.5236F;
			    	//leg
			    	addk1 = -1.05F;
			    	addk2 = -1.31F;
			    	this.LegLeft01.rotateAngleZ = -0.5236F;
			    	this.LegLeft02.rotateAngleX = 1.05F;
			    	this.LegRight01.rotateAngleY = -0.4363F;
			    	this.LegRight02.rotateAngleX = 0.7F;
			    	//hair
			    	this.Hair01.rotateAngleX -= 0.12F;
			    	this.Hair01.rotateAngleZ = -0.09F;
				  	this.Hair02.rotateAngleX -= 0.18F;
				  	this.Hair02.rotateAngleZ = -0.26F;
				  	this.Hair03.rotateAngleX -= 0.21F;
				  	this.Hair03.rotateAngleZ = -0.35F;
		    	}
		    	else {
		    		GL11.glTranslatef(0F, 1.15F, 0F);
			    	//arm
				  	this.ArmLeft01.rotateAngleX = -0.44F;
				  	this.ArmLeft01.rotateAngleY = 0.44F;
				    this.ArmLeft01.rotateAngleZ = 0F;
				  	this.ArmLeft03.rotateAngleY = 0.87F;
				  	this.ArmLeft06.rotateAngleZ = 0.1F;
					this.ArmRight01.rotateAngleX = -0.44F;
					this.ArmRight01.rotateAngleY = -0.44F;
					this.ArmRight01.rotateAngleZ = 0F;
					this.ArmRight03.rotateAngleY = -0.87F;
					this.ArmRight06.rotateAngleZ = -0.1F;
			    	//leg
			    	addk1 = -1.2217F;
			    	addk2 = -1.2217F;
			    	this.LegLeft02.offsetZ = 0.37F;
			    	this.LegRight02.offsetZ = 0.37F;
			    	this.LegLeft01.rotateAngleY = 0.14F;
			    	this.LegRight01.rotateAngleY = -0.14F;
			    	this.LegLeft02.rotateAngleX = 2.53F;
			    	this.LegRight02.rotateAngleX = 2.53F;
		    	}
	    	}
	    }//end ridding
    
	    //攻擊動作    
	    if(ent.getAttackTime() > 0) {
	    	if(ent.getAttackTime() > 25) setFace(3);
	    	//arm
	    	this.ArmLeft01.rotateAngleX = -1.4F;
	    	this.ArmLeft01.rotateAngleY = -0.14F;
	    	this.ArmLeft01.rotateAngleZ = 0F;
	    	this.ArmLeft06.rotateAngleZ = -0.96F;
	    	this.ArmRight01.rotateAngleX = -1.4F;
	    	this.ArmRight01.rotateAngleY = 0.14F;
	    	this.ArmRight01.rotateAngleZ = 0F;
	    	this.ArmRight06.rotateAngleZ = 0.96F;
	    }
	  	
	  	//swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.RAD_MUL;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.RAD_MUL + 1.0F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.RAD_MUL;
	  	}
	  	
	  	//caress
	  	if (ent.getStateEmotion(ID.S.Emotion3) == ID.Emotion3.CARESS)
	  	{
	  		//body
	  		this.Head.rotateAngleX += 0.2F;
	  	}
	  	
	  	//鬢毛調整
	    headX = this.Head.rotateAngleX * -0.5F;
		this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
	  	this.HairL02.rotateAngleX = -angleX1 * 0.04F + headX + 0.17F;
	  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
	  	this.HairR02.rotateAngleX = -angleX1 * 0.04F + headX + 0.17F;
	  	
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

