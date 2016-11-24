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

/**
 * ModelAirfieldHime - PinkaLulan 2015/5/16
 * Created using Tabula 4.1.1
 */
public class ModelAirfieldHime extends ModelBase implements IModelEmotion {
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
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
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer HeadHL2;
    public ModelRenderer HeadHL3;
    public ModelRenderer HeadHR2;
    public ModelRenderer HeadHR3;
    public ModelRenderer ArmLeft02;
    public ModelRenderer EquipHand01;
    public ModelRenderer ArmRight02;
    public ModelRenderer EquipHand02;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer ShoesR;
    public ModelRenderer LegLeft02;
    public ModelRenderer ShoesL;
    public ModelRenderer EquipRdL01;
    public ModelRenderer EquipRdR01;
    public ModelRenderer EquipRdL02;
    public ModelRenderer EquipRdL03;
    public ModelRenderer EquipRdL04;
    public ModelRenderer EquipRdL05;
    public ModelRenderer EquipRdL06;
    public ModelRenderer EquipRdR02;
    public ModelRenderer EquipRdR03;
    public ModelRenderer EquipRdR04;
    public ModelRenderer EquipRdR05;
    public ModelRenderer EquipRdR06;
    public ModelRenderer GlowEquipBase;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    
    private Random rand = new Random();
    private int startEmo2 = 0;

    public ModelAirfieldHime() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.HairR02 = new ModelRenderer(this, 25, 18);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 10.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairR02, 0.2617993877991494F, 0.0F, -0.05235987755982988F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.17453292519943295F, 0.0F, 0.0F);
        this.Hair02 = new ModelRenderer(this, 0, 59);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 5.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 16, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.08726646259971647F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-3.7F, -8.6F, -3.5F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, -0.13962634015954636F, -0.08726646259971647F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0.0F, -10.5F, -5.0F);
        this.Ahoke.addBox(0.0F, -4.0F, -11.5F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.5235987755982988F, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.7F, 9.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, 0.0F, 0.0F, 0.14F);
        this.HeadHR3 = new ModelRenderer(this, 43, 30);
        this.HeadHR3.setRotationPoint(-1.0F, 0.0F, 0.0F);
        this.HeadHR3.addBox(-1.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 83);
        this.ArmRight02.setRotationPoint(-3.0F, 12.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 13, 5, 0.0F);
        this.HairMain = new ModelRenderer(this, 48, 55);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 12, 10, 0.0F);
        this.HairR01 = new ModelRenderer(this, 25, 18);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-6.5F, 3.0F, -3.0F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairR01, -0.2617993877991494F, 0.17453292519943295F, 0.13962634015954636F);
        this.BoobL = new ModelRenderer(this, 33, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.7F, -8.6F, -3.5F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, 0.13962634015954636F, 0.08726646259971647F);
        this.Hair01 = new ModelRenderer(this, 46, 29);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 17, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.2617993877991494F, 0.0F, 0.0F);
        this.EquipHand02 = new ModelRenderer(this, 0, 17);
        this.EquipHand02.setRotationPoint(2.5F, -0.5F, -2.5F);
        this.EquipHand02.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.HairL02 = new ModelRenderer(this, 25, 18);
        this.HairL02.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairL02, 0.2617993877991494F, 0.0F, 0.08726646259971647F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.HeadHR = new ModelRenderer(this, 39, 28);
        this.HeadHR.mirror = true;
        this.HeadHR.setRotationPoint(-6.4F, -10.6F, 0.8F);
        this.HeadHR.addBox(-3.0F, -2.5F, -2.5F, 3, 5, 5, 0.0F);
        this.setRotateAngle(HeadHR, -0.7853981633974483F, 0.17453292519943295F, 0.3141592653589793F);
        this.ShoesR = new ModelRenderer(this, 87, 0);
        this.ShoesR.setRotationPoint(0.0F, 7.0F, 3.0F);
        this.ShoesR.addBox(-3.5F, 0.0F, -3.5F, 7, 8, 7, 0.0F);
        this.Butt = new ModelRenderer(this, 39, 0);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-7.5F, 4.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3141592653589793F, 0.0F, 0.0F);
        this.HeadHL2 = new ModelRenderer(this, 47, 56);
        this.HeadHL2.setRotationPoint(3.0F, 0.0F, 0.0F);
        this.HeadHL2.addBox(0.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
        this.HeadHR2 = new ModelRenderer(this, 47, 56);
        this.HeadHR2.setRotationPoint(-3.0F, 0.0F, 0.0F);
        this.HeadHR2.addBox(-1.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 84);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 7, 6, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 2, 85);
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 13, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.20943951023931953F, 0.0F, 0.20943951023931953F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.setRotationPoint(-4.7F, 9.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.10471975511965977F, 0.0F, -0.14F);
        this.LegRight02 = new ModelRenderer(this, 0, 84);
        this.LegRight02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 7, 6, 0.0F);
        this.HeadHL3 = new ModelRenderer(this, 43, 30);
        this.HeadHL3.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.HeadHL3.addBox(0.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ShoesL = new ModelRenderer(this, 87, 0);
        this.ShoesL.mirror = true;
        this.ShoesL.setRotationPoint(0.0F, 7.0F, 3.0F);
        this.ShoesL.addBox(-3.5F, 0.0F, -3.5F, 7, 8, 7, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 83);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.0F, 12.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 13, 5, 0.0F);
        this.HeadHL = new ModelRenderer(this, 39, 28);
        this.HeadHL.mirror = true;
        this.HeadHL.setRotationPoint(6.4F, -10.6F, 0.8F);
        this.HeadHL.addBox(0.0F, -2.5F, -2.5F, 3, 5, 5, 0.0F);
        this.setRotateAngle(HeadHL, -0.7853981633974483F, -0.17453292519943295F, -0.3141592653589793F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        
        this.EquipRdL01 = new ModelRenderer(this, 0, 0);
        this.EquipRdL01.setRotationPoint(5.0F, 0.0F, 6.0F);
        this.EquipRdL01.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL01, 1.4F, -0.3490658503988659F, -0.3490658503988659F);    
        this.EquipRdR01 = new ModelRenderer(this, 0, 0);
        this.EquipRdR01.setRotationPoint(-5.0F, 0.0F, 6.0F);
        this.EquipRdR01.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdR01, 1.4F, 0.3490658503988659F, 0.3490658503988659F);
        this.EquipRdL02 = new ModelRenderer(this, 0, 0);
        this.EquipRdL02.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL02.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL02, -0.3490658503988659F, 0.0F, 0.0F);
        this.EquipRdR02 = new ModelRenderer(this, 0, 0);
        this.EquipRdR02.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdR02.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdR02, -0.3490658503988659F, 0.0F, 0.0F);
        this.EquipRdL03 = new ModelRenderer(this, 0, 0);
        this.EquipRdL03.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL03.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL03, -0.4363F, 0.0F, 0.0F);
        this.EquipRdR03 = new ModelRenderer(this, 0, 0);
        this.EquipRdR03.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdR03.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdR03, -0.4363F, 0.0F, 0.0F);
        this.EquipRdL04 = new ModelRenderer(this, 0, 0);
        this.EquipRdL04.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL04.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL04, -0.3491F, 0.0F, 0.0F);
        this.EquipRdR04 = new ModelRenderer(this, 0, 0);
        this.EquipRdR04.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdR04.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdR04, -0.3491F, 0.0F, 0.0F);
        this.EquipRdL05 = new ModelRenderer(this, 0, 0);
        this.EquipRdL05.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL05.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL05, -0.2618F, 0.0F, 0.0F);
        this.EquipRdR05 = new ModelRenderer(this, 0, 0);
        this.EquipRdR05.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdR05.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdR05, -0.2618F, 0.0F, 0.0F);
        this.EquipRdL06 = new ModelRenderer(this, 0, 0);
        this.EquipRdL06.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL06.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL06, -0.1745F, 0.0F, 0.0F);
        this.EquipRdR06 = new ModelRenderer(this, 0, 0);
        this.EquipRdR06.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdR06.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdR06, -0.1745F, 0.0F, 0.0F);
       
        this.EquipHand01 = new ModelRenderer(this, 0, 17);
        this.EquipHand01.setRotationPoint(-0.5F, 7.5F, 0.0F);
        this.EquipHand01.addBox(-3.0F, 0.0F, -3.0F, 6, 5, 6, 0.0F);
        this.Neck = new ModelRenderer(this, 88, 26);
        this.Neck.setRotationPoint(0.0F, -10.3F, -0.5F);
        this.Neck.addBox(-5.5F, -2.0F, -5.0F, 11, 3, 9, 0.0F);
        this.setRotateAngle(Neck, 0.20943951023931953F, 0.0F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 2, 85);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 13, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.20943951023931953F, 0.0F, -0.20943951023931953F);
        this.Hair03 = new ModelRenderer(this, 0, 37);
        this.Hair03.setRotationPoint(0.0F, 12.5F, -0.1F);
        this.Hair03.addBox(-8.0F, 0.0F, -4.5F, 16, 15, 7, 0.0F);
        this.setRotateAngle(Hair03, -0.13962634015954636F, 0.0F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 25, 18);
        this.HairL01.setRotationPoint(6.5F, 3.0F, -3.0F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairL01, -0.2617993877991494F, -0.17453292519943295F, -0.13962634015954636F);
        this.Hair = new ModelRenderer(this, 45, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 16, 8, 0.0F);
        this.HairR01.addChild(this.HairR02);
        this.Hair01.addChild(this.Hair02);
        this.BodyMain.addChild(this.BoobR);
        this.LegRight01.addChild(this.LegRight02);
        this.Hair.addChild(this.Ahoke);
        this.Butt.addChild(this.LegLeft01);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Head.addChild(this.HairMain);
        this.Hair.addChild(this.HairR01);
        this.BodyMain.addChild(this.BoobL);
        this.HairMain.addChild(this.Hair01);
        this.ArmRight02.addChild(this.EquipHand02);
        this.HairL01.addChild(this.HairL02);
        this.LegRight02.addChild(this.ShoesR);
        this.BodyMain.addChild(this.Butt);
        this.LegLeft01.addChild(this.LegLeft02);
        this.BodyMain.addChild(this.ArmRight01);
        this.Butt.addChild(this.LegRight01);
        this.LegLeft02.addChild(this.ShoesL);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.Neck.addChild(this.Head);
        this.ArmRight01.addChild(this.EquipHand01);
        this.BodyMain.addChild(this.Neck);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Hair02.addChild(this.Hair03);
        this.Hair.addChild(this.HairL01);
        this.Head.addChild(this.Hair);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 104);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.17453292519943295F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 88, 26);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, -0.5F);
        this.setRotateAngle(GlowNeck, 0.20943951023931953F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 44, 101);
        this.GlowHead.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.GlowEquipBase = new ModelRenderer(this, 0, 0);
        this.GlowEquipBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowBodyMain.addChild(this.GlowEquipBase);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4); 
        this.GlowEquipBase.addChild(this.EquipRdL01);
        this.GlowEquipBase.addChild(this.EquipRdR01);
        this.EquipRdL01.addChild(this.EquipRdL02);
        this.EquipRdL02.addChild(this.EquipRdL03);
        this.EquipRdL03.addChild(this.EquipRdL04);
        this.EquipRdL04.addChild(this.EquipRdL05);
        this.EquipRdL05.addChild(this.EquipRdL06);
        this.EquipRdR01.addChild(this.EquipRdR02);
        this.EquipRdR02.addChild(this.EquipRdR03);
        this.EquipRdR03.addChild(this.EquipRdR04);
        this.EquipRdR04.addChild(this.EquipRdR05);
        this.EquipRdR05.addChild(this.EquipRdR06);
        this.GlowHead.addChild(this.HeadHL);
        this.GlowHead.addChild(this.HeadHR);
        this.HeadHL.addChild(this.HeadHL2);
        this.HeadHR.addChild(this.HeadHR2);
        this.HeadHL2.addChild(this.HeadHL3);
        this.HeadHR2.addChild(this.HeadHR3);
        
        
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
    	GL11.glScalef(0.47F, 0.47F, 0.47F);
    	GL11.glTranslatef(0F, 1.75F, 0F);
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	//亮度設為240
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
    
    //裝備模型顯示
    private void showEquip(IShipEmotion ent) {
		if(ent.getStateEmotion(ID.S.State2) == ID.State.EQUIP00_2) {
			this.EquipHand01.isHidden = false;
			this.EquipHand02.isHidden = false;
		}
		else {
			this.EquipHand01.isHidden = true;
			this.EquipHand02.isHidden = true;
		}
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
    	float addk1 = 0F;
  		float addk2 = 0F;
  		float headX = 0F;
  		float headZ = 0F;
  		
  		GL11.glTranslatef(0F, 1.7F, 0F);
  		setFace(4);
  		
  		if(((Entity)ent).ridingEntity instanceof BasicEntityMount) {
  			GL11.glTranslatef(0F, 1.4F, 0F);
  		}
  		
  		//移動頭部使其看人
	  	this.Head.rotateAngleX = f4 / 70F; 	//上下角度
	  	this.Head.rotateAngleY = f3 / 80F;	//左右角度 角度轉成rad 即除以57.29578
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = -0.7F;
  	    this.BoobR.rotateAngleX = -0.7F;
	  	//Body
  	    this.Ahoke.rotateAngleY = 0.5236F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.26F + headX;
	  	this.Hair02.rotateAngleX = -0.08F + headX;
	  	this.Hair03.rotateAngleX = -0.14F;
	    //arm 
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleX = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleY = 0F;
		//equip
		this.EquipRdL01.isHidden = true;
		this.EquipRdR01.isHidden = true;
		
    	//Body
    	this.Head.rotateAngleX += 0.14F;
	  	this.BodyMain.rotateAngleX = 0.4F;
	  	this.Butt.rotateAngleX = -0.4F;
	  	this.Butt.offsetZ = 0.19F;
	  	this.BoobL.rotateAngleX -= 0.2F;
	  	this.BoobR.rotateAngleX -= 0.2F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -1.3F;
	    this.ArmLeft01.rotateAngleZ = -0.1F;
	    this.ArmLeft02.rotateAngleZ = 1.15F;
		this.ArmRight01.rotateAngleX = -1.3F;
		this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = 0.1F;
		this.ArmRight02.rotateAngleZ = -1.4F;
		//leg
		addk1 = -2.1232F;
		addk2 = -2.0708F;
		this.LegLeft01.rotateAngleZ = -0.2F;
		this.LegLeft02.rotateAngleX = 1.34F;
		this.LegRight01.rotateAngleZ = 0.2F;
		this.LegRight02.rotateAngleX = 1.13F;
		//hair
		this.Hair01.rotateAngleX -= 0.2F;
		this.Hair02.rotateAngleX -= 0.2F;
		this.Hair03.rotateAngleX -= 0.1F;
		
		//移動頭髮避免穿過身體
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	this.HairL01.rotateAngleZ = headZ - 0.0F;
	  	this.HairL02.rotateAngleZ = headZ + 0.087F;
	  	this.HairR01.rotateAngleZ = headZ + 0.0F;
	  	this.HairR02.rotateAngleZ = headZ - 0.052F;
	  	
	    headX = this.Head.rotateAngleX * -0.5F;
	    this.HairL01.rotateAngleX = headX - 0.5F;
	  	this.HairL02.rotateAngleX = headX - 0.1F;
	  	this.HairR01.rotateAngleX = headX - 0.5F;
	  	this.HairR02.rotateAngleX = headX - 0.1F;
	  	
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
    }
    
    private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {
  		float angleX = MathHelper.cos(f2*0.08F);
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
  		addk1 = angleAdd1;
	  	addk2 = angleAdd2 - 0.2F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 / 70F; 	//上下角度
	  	this.Head.rotateAngleY = f3 / 80F;	//左右角度 角度轉成rad 即除以57.29578
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.7F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.7F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
	  	this.BodyMain.rotateAngleX = -0.1745F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.3142F;
	  	this.Butt.offsetZ = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.26F + headX;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.08F + headX;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -angleX2 * 0.07F - 0.14F;
	  	this.Hair03.rotateAngleZ = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.8F + 0.2F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.08F - 0.2F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.8F + 0.2F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.08F + 0.2F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.14F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.14F;
		this.LegRight02.rotateAngleX = 0F;
		//equip
		this.EquipRdL01.isHidden = true;
		this.EquipRdR01.isHidden = true;

	    if(ent.getIsSprinting() || f1 > 0.9F) {	//奔跑動作
	    	//沒有特殊跑步動作
  		}
	    
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
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		    //arm 
		  	this.ArmLeft01.rotateAngleX = -0.35F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
			this.ArmRight01.rotateAngleX = -0.35F;
			this.ArmRight01.rotateAngleZ = -0.2618F;
			//leg
			addk1 -= 1.1F;
			addk2 -= 1.1F;
			//hair
			this.Hair01.rotateAngleX += 0.37F;
			this.Hair02.rotateAngleX += 0.23F;
			this.Hair03.rotateAngleX -= 0.1F;
  		}//end if sneaking
  		
	    if(ent.getIsSitting() && !ent.getIsRiding()) {  //騎乘動作  	
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
	    		GL11.glTranslatef(0F, 1.0F, 0F);
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
	    	else {
	    		GL11.glTranslatef(0F, 1.2F, 0F);
		    	//Body
		    	this.Head.rotateAngleX += 0.14F;
			  	this.BodyMain.rotateAngleX = -0.5236F;
			  	this.BoobL.rotateAngleX -= 0.2F;
			  	this.BoobR.rotateAngleX -= 0.2F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = -0.4363F;
			    this.ArmLeft01.rotateAngleZ = 0.3142F;
				this.ArmRight01.rotateAngleX = -0.4363F;
				this.ArmRight01.rotateAngleZ = -0.3142F;
				//leg
				addk1 = -1.6232F;
				addk2 = -1.5708F;
				this.LegLeft01.rotateAngleZ = -0.3142F;
				this.LegLeft02.rotateAngleX = 1.34F;
				this.LegRight01.rotateAngleZ = 0.35F;
				this.LegRight02.rotateAngleX = 1.13F;
				//hair
				this.Hair01.rotateAngleX += 0.09F;
				this.Hair02.rotateAngleX += 0.43F;
				this.Hair03.rotateAngleX += 0.49F;
	    	}
  		}//end sitting
	    
	    if(ent.getIsRiding()) {
	    	if(((Entity)ent).ridingEntity instanceof BasicEntityMount) {
	    		if(((IShipFloating)((Entity)ent).ridingEntity).getShipDepth() > 0) {
	        		GL11.glTranslatef(0F, -0.3F, 0F);
	        	}
	    		
	    		if(ent.getIsSitting()) {
	    			if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
			    		GL11.glTranslatef(0F, 2.9F, 0F);
				    	//Body
				    	this.Head.rotateAngleX -= 0.15F;
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
			    	else {
				    	GL11.glTranslatef(0F, 2.9F, 0F);
				    	//Body
				    	this.Head.rotateAngleX -= 0.15F;
					  	this.BodyMain.rotateAngleX = -0.5236F;
					  	this.BoobL.rotateAngleX -= 0.2F;
					  	this.BoobR.rotateAngleX -= 0.2F;
					    //arm 
					  	this.ArmLeft01.rotateAngleX = -0.4363F;
					    this.ArmLeft01.rotateAngleZ = 0.3142F;
						this.ArmRight01.rotateAngleX = -0.4363F;
						this.ArmRight01.rotateAngleZ = -0.3142F;
						//leg
						addk1 = -1.6232F;
						addk2 = -1.5708F;
						this.LegLeft01.rotateAngleZ = -0.3142F;
						this.LegLeft02.rotateAngleX = 1.34F;
						this.LegRight01.rotateAngleZ = 0.35F;
						this.LegRight02.rotateAngleX = 1.13F;
						//hair
						this.Hair01.rotateAngleX += 0.09F;
						this.Hair02.rotateAngleX += 0.43F;
						this.Hair03.rotateAngleX += 0.49F;
			    	}
		    	}//end if sitting
		    	else {
		    		GL11.glTranslatef(0F, 2.4F, -0.5F);
		    		//body
		    		this.Head.rotateAngleX -= 0.35F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = 0.5F;
				    this.ArmLeft01.rotateAngleZ = -1.2F;
					this.ArmRight01.rotateAngleX = 0.5F;
					this.ArmRight01.rotateAngleZ = 1.2F;
					//leg
					addk1 = -0.2618F;
					addk2 = -0.35F;
					this.LegRight02.rotateAngleX = 0.8727F;
					//hair
					this.Hair01.rotateAngleX += 0.45F;
					this.Hair02.rotateAngleX += 0.43F;
					this.Hair03.rotateAngleX += 0.49F;
		    	}
	    	}//end ship mount
	    	else {	//normal mount ex: cart
	    		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
		    		GL11.glTranslatef(0F, 1.0F, 0F);
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
		    	else {
			    	GL11.glTranslatef(0F, 0.8F, 0F);
			    	//Body
			    	this.Head.rotateAngleX += 0.14F;
				  	this.BodyMain.rotateAngleX = -0.5236F;
				  	this.BoobL.rotateAngleX -= 0.2F;
				  	this.BoobR.rotateAngleX -= 0.2F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = -0.4363F;
				    this.ArmLeft01.rotateAngleZ = 0.3142F;
					this.ArmRight01.rotateAngleX = -0.4363F;
					this.ArmRight01.rotateAngleZ = -0.3142F;
					//leg
					addk1 = -1.6232F;
					addk2 = -1.5708F;
					this.LegLeft01.rotateAngleZ = -0.3142F;
					this.LegLeft02.rotateAngleX = 1.34F;
					this.LegRight01.rotateAngleZ = 0.35F;
					this.LegRight02.rotateAngleX = 1.13F;
					//hair
					this.Hair01.rotateAngleX += 0.09F;
					this.Hair02.rotateAngleX += 0.43F;
					this.Hair03.rotateAngleX += 0.49F;
		    	}
	    	}
	    }//end ridding
    
	    //攻擊動作    
	    if(ent.getAttackTime() > 0) {
	    	if(ent.getAttackTime() > 25) {
		    	//jojo攻擊動作
		    	if(ent.getStateEmotion(ID.S.State2) == ID.State.EQUIP02_2) {
		    		GL11.glTranslatef(0F, 0.4F, 0F);
			    	//Body
			    	this.Head.rotateAngleY *= 0.8F;
			    	this.Head.rotateAngleX = 0.4538F;
				  	this.BodyMain.rotateAngleX = -1.0472F;
				  	this.BodyMain.rotateAngleZ = -0.2094F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = -0.35F;
				    this.ArmLeft01.rotateAngleZ = -0.35F;
				    this.ArmLeft02.rotateAngleX = -0.5F;
					this.ArmRight01.rotateAngleX = 1.2F;
					this.ArmRight01.rotateAngleZ = 0.5236F;
					this.ArmRight02.rotateAngleX = -0.35F;
					//leg
					addk1 = 0.5236F;
					addk2 = 0.1745F;
					this.LegLeft01.rotateAngleZ = 0.2618F;
					this.LegLeft02.rotateAngleX = 0.5236F;
					this.LegRight01.rotateAngleZ = 0.1745F;
					this.LegRight02.rotateAngleX = 0.5236F;
					//hair
					this.Hair01.rotateAngleX += 0.09F;
					this.Hair02.rotateAngleX += 0.43F;
					this.Hair03.rotateAngleX += 0.49F;
		    	}
		    	else if(ent.getStateEmotion(ID.S.State2) == ID.State.EQUIP01_2) {
		    		//Body
			    	this.Head.rotateAngleY *= 0.8F;
			    	this.Head.rotateAngleX = 0.2094F;
			    	this.Head.rotateAngleZ = -0.2618F;
				  	this.BodyMain.rotateAngleX = -0.35F;
				  	this.BodyMain.rotateAngleZ = 0.1745F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = -1.2217F;
				  	this.ArmLeft01.rotateAngleY = 0.5236F;
				    this.ArmLeft01.rotateAngleZ = -0.35F;
				    this.ArmLeft02.rotateAngleX = -1.3963F;
					this.ArmRight01.rotateAngleX = 0.7854F;
					this.ArmRight01.rotateAngleZ = 0.5236F;
					this.ArmRight02.rotateAngleX = -0.5236F;
					//leg
					addk1 = -0.2618F;
					addk2 = 0.3142F;
					this.LegLeft01.rotateAngleZ = -0.4363F;
					this.LegLeft02.rotateAngleX = 0.2618F;
					this.LegRight01.rotateAngleZ = 0.0873F;
					//hair
					this.Hair01.rotateAngleX += 0.09F;
					this.Hair02.rotateAngleX += 0.43F;
					this.Hair03.rotateAngleX += 0.49F;
		    	}
		    	else {
		    		//arm
			    	this.ArmLeft01.rotateAngleX = -1.3F;
			    	this.ArmLeft01.rotateAngleY = -0.7F;
			    	this.ArmLeft01.rotateAngleZ = 0F;
		    	}
	    	}
	    	//跑道顯示
	    	setRoad(ent.getAttackTime());
	    }
	    
	    //鬢毛調整
	    headX = this.Head.rotateAngleX * -0.5F;
	    this.HairL01.rotateAngleX = angleX * 0.03F + headX - 0.26F;
	  	this.HairL02.rotateAngleX = -angleX1 * 0.04F + headX + 0.26F;
	  	this.HairR01.rotateAngleX = angleX * 0.03F + headX - 0.26F;
	  	this.HairR02.rotateAngleX = -angleX1 * 0.04F + headX + 0.26F;
	  	
	  	//swing arm
	  	float f6 = ent.getSwingTime(f2 % 1F);
	  	if(f6 != 0F) {
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.3F;
			this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight01.rotateAngleZ = -0.1F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.RAD_MUL;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.RAD_MUL;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.RAD_MUL;
	        this.ArmRight02.rotateAngleX = 0F;
	        this.ArmRight02.rotateAngleZ = 0F;
	  	}
	  	
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
    
    private void setRoad(int attackTime) {
		switch(attackTime) {
		case 50:
		case 26:
			this.EquipRdL01.isHidden = false;
			this.EquipRdR01.isHidden = false;
			this.EquipRdL02.isHidden = true;
			this.EquipRdR02.isHidden = true;
			break;
		case 49:
		case 27:
			this.EquipRdL01.isHidden = false;
			this.EquipRdR01.isHidden = false;
			this.EquipRdL02.isHidden = false;
			this.EquipRdR02.isHidden = false;
			this.EquipRdL03.isHidden = true;
			this.EquipRdR03.isHidden = true;
			break;
		case 48:
		case 28:
			this.EquipRdL01.isHidden = false;
			this.EquipRdR01.isHidden = false;
			this.EquipRdL02.isHidden = false;
			this.EquipRdR02.isHidden = false;
			this.EquipRdL03.isHidden = false;
			this.EquipRdR03.isHidden = false;
			this.EquipRdL04.isHidden = true;
			this.EquipRdR04.isHidden = true;
			break;
		case 47:
		case 29:
			this.EquipRdL01.isHidden = false;
			this.EquipRdR01.isHidden = false;
			this.EquipRdL02.isHidden = false;
			this.EquipRdR02.isHidden = false;
			this.EquipRdL03.isHidden = false;
			this.EquipRdR03.isHidden = false;
			this.EquipRdL04.isHidden = false;
			this.EquipRdR04.isHidden = false;
			this.EquipRdL05.isHidden = true;
			this.EquipRdR05.isHidden = true;
			break;
		case 46:
		case 30:
			this.EquipRdL01.isHidden = false;
			this.EquipRdR01.isHidden = false;
			this.EquipRdL02.isHidden = false;
			this.EquipRdR02.isHidden = false;
			this.EquipRdL03.isHidden = false;
			this.EquipRdR03.isHidden = false;
			this.EquipRdL04.isHidden = false;
			this.EquipRdR04.isHidden = false;
			this.EquipRdL05.isHidden = false;
			this.EquipRdR05.isHidden = false;
			this.EquipRdL06.isHidden = true;
			this.EquipRdR06.isHidden = true;
			break;
		default:
			if(attackTime < 46 && attackTime > 30) {
				this.EquipRdL01.isHidden = false;
				this.EquipRdR01.isHidden = false;
				this.EquipRdL02.isHidden = false;
				this.EquipRdR02.isHidden = false;
				this.EquipRdL03.isHidden = false;
				this.EquipRdR03.isHidden = false;
				this.EquipRdL04.isHidden = false;
				this.EquipRdR04.isHidden = false;
				this.EquipRdL05.isHidden = false;
				this.EquipRdR05.isHidden = false;
				this.EquipRdL06.isHidden = false;
				this.EquipRdR06.isHidden = false;
			}		
			break;
		}//end attack time > 0
	}//end pose
  	
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

