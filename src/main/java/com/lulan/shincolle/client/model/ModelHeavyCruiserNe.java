package com.lulan.shincolle.client.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EmotionHelper;

/**
 * ModelHeavyCruiserNe - PinkaLulan
 * Created using Tabula 4.1.1 2016/2/14
 */
public class ModelHeavyCruiserNe extends ModelBase implements IModelEmotion {
    public ModelRenderer BodyMain;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight01;
    public ModelRenderer Neck;
    public ModelRenderer Head;
    public ModelRenderer Cloth01;
    public ModelRenderer TailBase;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer LegLeft02;
    public ModelRenderer LegRight02;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Ear01;
    public ModelRenderer Ear02;
    public ModelRenderer Face0;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Ahoke;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer TailL01;
    public ModelRenderer TailR01;
    public ModelRenderer TailL02;
    public ModelRenderer TailL03;
    public ModelRenderer TailL04;
    public ModelRenderer TailL05;
    public ModelRenderer TailL06;
    public ModelRenderer TailLHead01;
    public ModelRenderer TailLHead02;
    public ModelRenderer TailLC01;
    public ModelRenderer TailLC02;
    public ModelRenderer TailLC03;
    public ModelRenderer TailR02;
    public ModelRenderer TailR03;
    public ModelRenderer TailR04;
    public ModelRenderer TailR05;
    public ModelRenderer TailR06;
    public ModelRenderer TailRHead01;
    public ModelRenderer TailRHead02;
    public ModelRenderer TailRC01;
    public ModelRenderer TailRC02;
    public ModelRenderer TailRC03;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;

    private Random rand = new Random();
    private int startEmo2 = 0;
    
    
    public ModelHeavyCruiserNe() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.TailRC02 = new ModelRenderer(this, 0, 0);
        this.TailRC02.setRotationPoint(-3.0F, 2.0F, 13.5F);
        this.TailRC02.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.setRotateAngle(TailRC02, -0.091106186954104F, -0.08726646259971647F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 40);
        this.Hair.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 17, 8, 0.0F);
        this.TailLC01 = new ModelRenderer(this, 0, 0);
        this.TailLC01.setRotationPoint(0.0F, 2.2F, 13.5F);
        this.TailLC01.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.setRotateAngle(TailLC01, -0.18203784098300857F, 0.0F, 0.0F);
        this.Hair02 = new ModelRenderer(this, 78, 92);
        this.Hair02.setRotationPoint(-6.3F, 4.7F, 2.0F);
        this.Hair02.addBox(-2.0F, 0.0F, -3.5F, 3, 10, 7, 0.0F);
        this.setRotateAngle(Hair02, 0.20943951023931953F, 0.0F, 0.17453292519943295F);
        this.TailRC01 = new ModelRenderer(this, 0, 0);
        this.TailRC01.setRotationPoint(0.0F, 2.2F, 13.5F);
        this.TailRC01.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.setRotateAngle(TailRC01, -0.18203784098300857F, 0.0F, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 48, 92);
        this.LegLeft01.setRotationPoint(4.0F, 3.0F, 8.3F);
        this.LegLeft01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(LegLeft01, 0.13962634015954636F, 0.0F, 0.17453292519943295F);
        this.LegLeft02 = new ModelRenderer(this, 48, 105);
        this.LegLeft02.setRotationPoint(0.0F, 8.0F, -2.5F);
        this.LegLeft02.addBox(-2.5F, 0.0F, 0F, 5, 7, 5, 0.0F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0.0F, -8.5F, -5.0F);
        this.Ahoke.addBox(0.0F, -4.0F, -11.5F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.5235987755982988F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 93);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain.addBox(-5.5F, -4.5F, -12.0F, 11, 10, 24, 0.0F);
        this.Neck = new ModelRenderer(this, 0, 78);
        this.Neck.setRotationPoint(0.0F, -4.0F, -9.4F);
        this.Neck.addBox(-5.0F, -2.0F, -4.5F, 10, 5, 9, 0.0F);
        this.setRotateAngle(Neck, 0.41887902047863906F, 0.0F, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 3.5F, -0.1F);
        this.Face0.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 0, 92);
        this.ArmLeft01.setRotationPoint(4.0F, 3.0F, -6.0F);
        this.ArmLeft01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, -0.13962634015954636F, 0.0F, 0.20943951023931953F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 3.5F, -0.1F);
        this.Face3.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.TailBase = new ModelRenderer(this, 98, 0);
        this.TailBase.setRotationPoint(0.0F, -0.5F, 9.0F);
        this.TailBase.addBox(-4.0F, -4.0F, 0.0F, 8, 8, 7, 0.0F);
        this.setRotateAngle(TailBase, 0.7853981633974483F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 0, 105);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-2.5F, 8.0F, 2.5F);
        this.ArmRight02.addBox(0F, 0.0F, -5F, 5, 7, 5, 0.0F);
        this.Ear02 = new ModelRenderer(this, 0, 26);
        this.Ear02.mirror = true;
        this.Ear02.setRotationPoint(-4.2F, -11.0F, 6.8F);
        this.Ear02.addBox(-2.0F, 0.0F, -7.0F, 4, 7, 7, 0.0F);
        this.setRotateAngle(Ear02, -0.8378F, 0.1222F, -0.1745F);
        this.TailRC03 = new ModelRenderer(this, 0, 0);
        this.TailRC03.setRotationPoint(3.0F, 2.0F, 13.5F);
        this.TailRC03.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.setRotateAngle(TailRC03, -0.136659280431156F, 0.08726646259971647F, 0.0F);
        this.Hair03 = new ModelRenderer(this, 80, 109);
        this.Hair03.setRotationPoint(0.2F, 7.5F, -0.3F);
        this.Hair03.addBox(-2.0F, 0.0F, -3.0F, 3, 12, 6, 0.0F);
        this.setRotateAngle(Hair03, -0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.LegRight01 = new ModelRenderer(this, 48, 92);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.0F, 3.0F, 8.3F);
        this.LegRight01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(LegRight01, -0.13962634015954636F, 0.0F, -0.17453292519943295F);
        this.Ear01 = new ModelRenderer(this, 0, 26);
        this.Ear01.setRotationPoint(4.2F, -11.0F, 6.8F);
        this.Ear01.addBox(-2.0F, 0.0F, -7.0F, 4, 7, 7, 0.0F);
        this.setRotateAngle(Ear01, -0.8378F, -0.1222F, 0.1745F);
        this.ArmLeft02 = new ModelRenderer(this, 0, 105);
        this.ArmLeft02.setRotationPoint(2.5F, 8.0F, 2.5F);
        this.ArmLeft02.addBox(-5F, 0.0F, -5F, 5, 7, 5, 0.0F);
        this.TailL04 = new ModelRenderer(this, 97, 3);
        this.TailL04.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailL04.addBox(-3.5F, -3.5F, 0.0F, 7, 7, 7, 0.0F);
        this.setRotateAngle(TailL04, 0.2617993877991494F, 0.20943951023931953F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 0, 92);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-4.0F, 3.0F, -6.0F);
        this.ArmRight01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.13962634015954636F, 0.0F, -0.20943951023931953F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 3.5F, -0.1F);
        this.Face2.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.TailRHead01 = new ModelRenderer(this, 76, 18);
        this.TailRHead01.mirror = true;
        this.TailRHead01.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.TailRHead01.addBox(-5.5F, -2.0F, 0.0F, 11, 6, 15, 0.0F);
        this.setRotateAngle(TailRHead01, -0.12217304763960307F, 0.0F, 0.0F);
        this.TailL05 = new ModelRenderer(this, 95, 2);
        this.TailL05.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailL05.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 7, 0.0F);
        this.setRotateAngle(TailL05, 0.2617993877991494F, 0.13962634015954636F, 0.0F);
        this.TailL06 = new ModelRenderer(this, 89, 0);
        this.TailL06.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailL06.addBox(-4.5F, -3.5F, 0.0F, 9, 7, 10, 0.0F);
        this.setRotateAngle(TailL06, 0.2617993877991494F, 0.06981317007977318F, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 3.5F, -0.1F);
        this.Face1.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 3.5F, -0.1F);
        this.Face4.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.TailL03 = new ModelRenderer(this, 95, 1);
        this.TailL03.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailL03.addBox(-3.5F, -3.5F, 0.0F, 7, 7, 7, 0.0F);
        this.setRotateAngle(TailL03, 0.2617993877991494F, 0.24434609527920614F, 0.0F);
        this.TailLC02 = new ModelRenderer(this, 0, 0);
        this.TailLC02.setRotationPoint(-3.0F, 2.0F, 13.5F);
        this.TailLC02.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.setRotateAngle(TailLC02, -0.091106186954104F, -0.08726646259971647F, 0.0F);
        this.TailR04 = new ModelRenderer(this, 100, 2);
        this.TailR04.mirror = true;
        this.TailR04.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailR04.addBox(-3.5F, -3.5F, 0.0F, 7, 7, 7, 0.0F);
        this.setRotateAngle(TailR04, 0.41887902047863906F, 0.13962634015954636F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 48, 105);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(0.0F, 8.0F, -2.5F);
        this.LegRight02.addBox(-2.5F, 0.0F, 0F, 5, 7, 5, 0.0F);
        this.Hair01 = new ModelRenderer(this, 0, 40);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.6F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 7, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.3490658503988659F, 0.0F, 0.0F);
        this.TailLHead01 = new ModelRenderer(this, 76, 18);
        this.TailLHead01.setRotationPoint(0.0F, 0.0F, -2.5F);
        this.TailLHead01.addBox(-5.5F, -2.0F, 0.0F, 11, 6, 15, 0.0F);
        this.setRotateAngle(TailLHead01, -0.12217304763960307F, 0.0F, 0.0F);
        this.TailR06 = new ModelRenderer(this, 89, 1);
        this.TailR06.mirror = true;
        this.TailR06.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailR06.addBox(-4.5F, -3.5F, 0.0F, 9, 7, 10, 0.0F);
        this.setRotateAngle(TailR06, 0.2617993877991494F, 0.13962634015954636F, 0.0F);
        this.TailL01 = new ModelRenderer(this, 98, 0);
        this.TailL01.setRotationPoint(1.5F, 0.0F, 6.0F);
        this.TailL01.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 7, 0.0F);
        this.setRotateAngle(TailL01, 0.2617993877991494F, 0.41887902047863906F, 0.0F);
        this.TailLHead02 = new ModelRenderer(this, 22, 27);
        this.TailLHead02.setRotationPoint(0.0F, 0.0F, 1.5F);
        this.TailLHead02.addBox(-5.0F, -4.0F, 0.0F, 10, 3, 9, 0.0F);
        this.setRotateAngle(TailLHead02, 0.08726646259971647F, 0.0F, 0.0F);
        this.TailR03 = new ModelRenderer(this, 97, 2);
        this.TailR03.mirror = true;
        this.TailR03.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailR03.addBox(-3.5F, -3.5F, 0.0F, 7, 7, 7, 0.0F);
        this.setRotateAngle(TailR03, 0.3141592653589793F, 0.06981317007977318F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 65);
        this.Head.setRotationPoint(0.0F, -6.0F, -13.0F);
        this.Head.addBox(-7.0F, -11.0F, -6.5F, 14, 14, 13, 0.0F);
        this.TailRHead02 = new ModelRenderer(this, 22, 27);
        this.TailRHead02.mirror = true;
        this.TailRHead02.setRotationPoint(0.0F, 0.0F, 1.5F);
        this.TailRHead02.addBox(-5.0F, -4.0F, 0.0F, 10, 3, 9, 0.0F);
        this.setRotateAngle(TailRHead02, 0.08726646259971647F, 0.0F, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 42, 39);
        this.Cloth01.setRotationPoint(0.0F, -1.0F, -13.0F);
        this.Cloth01.addBox(-4.0F, 0.0F, 0.0F, 8, 9, 0, 0.0F);
        this.setRotateAngle(Cloth01, -0.08726646259971647F, 0.0F, 0.0F);
        this.HairMain = new ModelRenderer(this, 0, 56);
        this.HairMain.setRotationPoint(0.0F, -11.5F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 12, 10, 0.0F);
        this.TailR01 = new ModelRenderer(this, 101, 0);
        this.TailR01.mirror = true;
        this.TailR01.setRotationPoint(-1.5F, 0.0F, 6.0F);
        this.TailR01.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 7, 0.0F);
        this.setRotateAngle(TailR01, 0.2617993877991494F, -0.06981317007977318F, 0.0F);
        this.TailR02 = new ModelRenderer(this, 102, 3);
        this.TailR02.mirror = true;
        this.TailR02.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailR02.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 7, 0.0F);
        this.setRotateAngle(TailR02, 0.2617993877991494F, 0.0F, 0.0F);
        this.TailL02 = new ModelRenderer(this, 95, 3);
        this.TailL02.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailL02.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 7, 0.0F);
        this.setRotateAngle(TailL02, 0.2617993877991494F, 0.3141592653589793F, 0.0F);
        this.TailLC03 = new ModelRenderer(this, 0, 0);
        this.TailLC03.setRotationPoint(3.0F, 2.0F, 13.5F);
        this.TailLC03.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.setRotateAngle(TailLC03, -0.136659280431156F, 0.08726646259971647F, 0.0F);
        this.TailR05 = new ModelRenderer(this, 97, 0);
        this.TailR05.mirror = true;
        this.TailR05.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.TailR05.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 7, 0.0F);
        this.setRotateAngle(TailR05, 0.5235987755982988F, 0.13962634015954636F, 0.0F);
        this.TailRHead01.addChild(this.TailRC02);
        this.Head.addChild(this.Hair);
        this.TailLHead01.addChild(this.TailLC01);
        this.HairMain.addChild(this.Hair02);
        this.TailRHead01.addChild(this.TailRC01);
        this.BodyMain.addChild(this.LegLeft01);
        this.LegLeft01.addChild(this.LegLeft02);
        this.Hair.addChild(this.Ahoke);
        this.BodyMain.addChild(this.Neck);
        this.BodyMain.addChild(this.ArmLeft01);
        this.BodyMain.addChild(this.TailBase);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Head.addChild(this.Ear02);
        this.TailRHead01.addChild(this.TailRC03);
        this.Hair02.addChild(this.Hair03);
        this.BodyMain.addChild(this.LegRight01);
        this.Head.addChild(this.Ear01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.TailL03.addChild(this.TailL04);
        this.BodyMain.addChild(this.ArmRight01);
        this.TailR06.addChild(this.TailRHead01);
        this.TailL04.addChild(this.TailL05);
        this.TailL05.addChild(this.TailL06);
        this.TailL02.addChild(this.TailL03);
        this.TailLHead01.addChild(this.TailLC02);
        this.TailR03.addChild(this.TailR04);
        this.LegRight01.addChild(this.LegRight02);
        this.HairMain.addChild(this.Hair01);
        this.TailL06.addChild(this.TailLHead01);
        this.TailR05.addChild(this.TailR06);
        this.TailBase.addChild(this.TailL01);
        this.TailL06.addChild(this.TailLHead02);
        this.TailR02.addChild(this.TailR03);
        this.BodyMain.addChild(this.Head);
        this.TailR06.addChild(this.TailRHead02);
        this.BodyMain.addChild(this.Cloth01);
        this.Head.addChild(this.HairMain);
        this.TailBase.addChild(this.TailR01);
        this.TailR01.addChild(this.TailR02);
        this.TailL01.addChild(this.TailL02);
        this.TailLHead01.addChild(this.TailLC03);
        this.TailR04.addChild(this.TailR05);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -6.0F, -13.0F);
        
        this.GlowBodyMain.addChild(this.GlowHead);
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

    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	GL11.glDisable(GL11.GL_BLEND);
    	
    	//light part:eye, cannon, ...etc
    	GL11.glDisable(GL11.GL_LIGHTING);
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	
    	GL11.glPopMatrix();
    }
    
	//model animation
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
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
    }
    
	//雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {   
  		float angleX = MathHelper.cos(f2*0.08F + f * 0.25F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.08F + 0.6F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		
    	GL11.glTranslatef(0F, 2.63F, 0F);
  		
    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.14F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.5F + 0.14F;  //LegRight01
	  	this.ArmRight01.rotateAngleX = addk1;
    	this.ArmLeft01.rotateAngleX = addk2;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.0175F; 	//上下角度
	  	this.Head.rotateAngleY = f3 * 0.0175F;	//左右角度 角度轉成rad 即除以57.29578
	    
	    //正常站立動作
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.45F;
	  	this.BodyMain.rotateAngleX = 0F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Head.offsetY = 0F;
	  	this.GlowHead.offsetY = 0F;
	  	//hair
	  	this.Hair02.rotateAngleX = angleX1 * 0.04F + 0.21F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = angleX2 * 0.07F - 0.2618F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	//arm
	  	this.ArmLeft01.rotateAngleZ = 0.21F;
	  	this.ArmLeft01.offsetZ = 0F;
	  	this.ArmLeft02.rotateAngleZ = 0F;
	  	this.ArmRight01.rotateAngleZ = -0.21F;
	  	this.ArmRight01.offsetZ = 0F;
	  	this.ArmRight02.rotateAngleZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.1745F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.1745F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleZ = 0F;
	  	//tail
		this.TailBase.rotateAngleX = 0.8F;
	  	this.TailL01.rotateAngleX = 0.2618F;
		this.TailL01.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 0.7F) * 0.2F + 0.5F;
		this.TailL01.rotateAngleZ = this.TailL01.rotateAngleY * 0.25F;
		this.TailL02.rotateAngleX = 0.2618F;
		this.TailL02.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 1.4F) * 0.25F;
		this.TailL02.rotateAngleZ = this.TailL02.rotateAngleY * 0.25F;
		this.TailL03.rotateAngleX = 0.2618F;
		this.TailL03.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.1F) * 0.3F;
		this.TailL03.rotateAngleZ = this.TailL03.rotateAngleY * 0.25F;
		this.TailL04.rotateAngleX = 0.35F;
		this.TailL04.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.8F) * 0.35F;
		this.TailL04.rotateAngleZ = this.TailL04.rotateAngleY * 0.25F;
		this.TailL05.rotateAngleX = 0.4F;
		this.TailL05.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 3.5F) * 0.4F;
		this.TailL05.rotateAngleZ = this.TailL05.rotateAngleY * 0.25F;
		this.TailL06.rotateAngleX = 0.45F;
		this.TailL06.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 4.2F) * 0.35F;
		this.TailL06.rotateAngleZ = this.TailL06.rotateAngleY * 0.25F;
		this.TailR01.rotateAngleX = 0.2618F;
		this.TailR01.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 0.7F) * 0.2F - 0.5F;
		this.TailR01.rotateAngleZ = this.TailR01.rotateAngleY * 0.25F;
		this.TailR02.rotateAngleX = 0.2618F;
		this.TailR02.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 1.4F) * 0.25F;
		this.TailR02.rotateAngleZ = this.TailR02.rotateAngleY * 0.25F;
		this.TailR03.rotateAngleX = 0.2618F;
		this.TailR03.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.1F) * 0.3F;
		this.TailR03.rotateAngleZ = this.TailR03.rotateAngleY * 0.25F;
		this.TailR04.rotateAngleX = 0.35F;
		this.TailR04.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.8F) * 0.35F;
		this.TailR04.rotateAngleZ = this.TailR04.rotateAngleY * 0.25F;
		this.TailR05.rotateAngleX = 0.4F;
		this.TailR05.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 3.5F) * 0.4F;
		this.TailR05.rotateAngleZ = this.TailR05.rotateAngleY * 0.25F;
		this.TailR06.rotateAngleX = 0.45F;
		this.TailR06.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 4.2F) * 0.45F;
		this.TailR06.rotateAngleZ = this.TailR06.rotateAngleY * 0.25F;

	    if(ent.getIsSprinting() || f1 > 0.8F) {	//奔跑動作
	    	//leg
	    	addk1 *= 2F;
	    	addk2 *= 2F;
	    	this.ArmRight01.rotateAngleX = addk1;
	    	this.ArmLeft01.rotateAngleX = addk2;
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
	    
	    if(ent.getIsSneaking()) {		//潛行, 蹲下動作
	    	//head
	    	this.Head.offsetY = 0.2F;
	    	this.GlowHead.offsetY = 0.2F;
  		}//end if sneaking
  		
	    if(ent.getIsSitting() || ent.getIsRiding()) {  //騎乘動作
	    	//change riding height
	    	if(ent.getIsRiding()) {
	    		GL11.glTranslatef(0F, -0.6F, 0F);
	    	}
	    	
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
		    	GL11.glTranslatef(0F, 0.8F, 0F);
		    	//head
		    	this.Head.rotateAngleX = 1.5359F;
		    	this.Head.offsetY = 0.25F;
		    	this.GlowHead.rotateAngleX = 1.5359F;
		    	this.GlowHead.offsetY = 0.25F;
		    	//arm
		    	addk1 = 1.5359F;
		    	addk2 = 1.5359F;
		    	this.ArmLeft01.rotateAngleX = -1.5359F;
		    	this.ArmLeft01.rotateAngleZ = 0F;
		    	this.ArmLeft01.offsetZ = -0.18F;
		    	this.ArmRight01.rotateAngleX = -1.5359F;
		    	this.ArmRight01.rotateAngleZ = 0F;
		    	this.ArmRight01.offsetZ = -0.18F;
		    	//tail
		    	this.TailBase.rotateAngleX = 0.0873F;
			  	this.TailL01.rotateAngleX = 0.02618F;
			  	this.TailL01.rotateAngleY *= 0.5F;
				this.TailL02.rotateAngleX = -0.02618F;
				this.TailL02.rotateAngleY *= 0.5F;
				this.TailL03.rotateAngleX = -0.02618F;
				this.TailL03.rotateAngleY *= 0.5F;
				this.TailL04.rotateAngleX = -0.035F;
				this.TailL04.rotateAngleY *= 0.5F;
				this.TailL05.rotateAngleX = -0.04F;
				this.TailL05.rotateAngleY *= 0.5F;
				this.TailL06.rotateAngleX = -0.045F;
				this.TailL06.rotateAngleY *= 0.5F;
				this.TailR01.rotateAngleX = -0.02618F;
				this.TailR01.rotateAngleY *= 0.5F;
				this.TailR02.rotateAngleX = -0.02618F;
				this.TailR02.rotateAngleY *= 0.5F;
				this.TailR03.rotateAngleX = -0.02618F;
				this.TailR03.rotateAngleY *= 0.5F;
				this.TailR04.rotateAngleX = -0.035F;
				this.TailR04.rotateAngleY *= 0.5F;
				this.TailR05.rotateAngleX = -0.04F;
				this.TailR05.rotateAngleY *= 0.5F;
				this.TailR06.rotateAngleX = -0.045F;
				this.TailR06.rotateAngleY *= 0.5F;
	    	}
	    	else {
		    	GL11.glTranslatef(0F, 0.8F, 0F);
		    	//head
		    	this.Head.rotateAngleX -= 0.5F;
		    	this.GlowHead.rotateAngleX -= 0.5F;
		    	this.Head.offsetY = 0.25F;
		    	this.GlowHead.offsetY = 0.25F;
		    	//arm
		    	addk1 = 1.5359F;
		    	addk2 = 1.5359F;
		    	this.ArmLeft01.rotateAngleX = -1.5359F;
		    	this.ArmLeft01.rotateAngleZ = 0F;
		    	this.ArmLeft01.offsetZ = -0.18F;
		    	this.ArmLeft02.rotateAngleZ = 1.1868F;
		    	this.ArmRight01.rotateAngleX = -1.5359F;
		    	this.ArmRight01.rotateAngleZ = 0F;
		    	this.ArmRight01.offsetZ = -0.18F;
		    	this.ArmRight02.rotateAngleZ = -1.1868F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if(ent.getAttackTime() > 0) {
	    	//tail
		  	this.TailL01.rotateAngleX = 0.2618F;
		  	this.TailL01.rotateAngleY = 0.2618F;
		  	this.TailL01.rotateAngleZ = 0F;
		  	this.TailL02.rotateAngleX = 0.35F;
		  	this.TailL02.rotateAngleY = 0.1748F;
		  	this.TailL02.rotateAngleZ = 0F;
		  	this.TailL03.rotateAngleX = 0.4363F;
		  	this.TailL03.rotateAngleY = 0.14F;
		  	this.TailL03.rotateAngleZ = 0F;
		  	this.TailL04.rotateAngleX = 0.5236F;
		  	this.TailL04.rotateAngleY = 0.14F;
		  	this.TailL04.rotateAngleZ = 0F;
		  	this.TailL05.rotateAngleX = 0.6109F;
		  	this.TailL05.rotateAngleY = 0.1745F;
		  	this.TailL05.rotateAngleZ = 0F;
		  	this.TailL06.rotateAngleX = 0.35F;
		  	this.TailL06.rotateAngleY = 0F;
		  	this.TailL06.rotateAngleZ = 0F;
		  	this.TailR01.rotateAngleX = 0.2618F;
		  	this.TailR01.rotateAngleY = -0.2618F;
		  	this.TailR01.rotateAngleZ = 0F;
		  	this.TailR02.rotateAngleX = 0.35F;
		  	this.TailR02.rotateAngleY = -0.1748F;
		  	this.TailR02.rotateAngleZ = 0F;
		  	this.TailR03.rotateAngleX = 0.35F;
		  	this.TailR03.rotateAngleY = -0.14F;
		  	this.TailR03.rotateAngleZ = 0F;
		  	this.TailR04.rotateAngleX = 0.4363F;
		  	this.TailR04.rotateAngleY = -0.14F;
		  	this.TailR04.rotateAngleZ = 0F;
		  	this.TailR05.rotateAngleX = 0.4363F;
		  	this.TailR05.rotateAngleY = -0.14F;
		  	this.TailR05.rotateAngleZ = 0F;
		  	this.TailR06.rotateAngleX = 0.35F;
		  	this.TailR06.rotateAngleY = 0F;
		  	this.TailR06.rotateAngleZ = 0F;
	    }
	    
	    //鬢毛調整
	    float headZ = this.Head.rotateAngleZ * -0.5F;
	    float headX = this.Head.rotateAngleX * -0.5F - 0.05F;
	  	this.Hair02.rotateAngleX += headX * 0.5F;
	  	this.Hair03.rotateAngleX += headX * 0.2F;
	  	this.Hair02.rotateAngleZ += headZ * 0.8F;
	  	this.Hair03.rotateAngleZ += headZ * 0.4F;
	    
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
