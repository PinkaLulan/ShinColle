package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.handler.EventHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelCarrierWDemon - PinkaLulan 2015/6/29
 * Created using Tabula 4.1.1
 */
public class ModelCarrierWDemon extends ModelBase implements IModelEmotion
{
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer Butt;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Cloth01;
    public ModelRenderer EquipBase;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Face0;
    public ShipModelRenderer HeadS01;
    public ModelRenderer HeadS04;
    public ModelRenderer HeadS05;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer HeadS02;
    public ModelRenderer HeadS03;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight02;
    public ModelRenderer ShoesR01;
    public ModelRenderer ShoesR02;
    public ModelRenderer ShoesR03;
    public ModelRenderer ShoesR04;
    public ModelRenderer LegLeft02;
    public ModelRenderer ShoesL01;
    public ModelRenderer ShoesL02;
    public ModelRenderer ShoesL03;
    public ModelRenderer ShoesL04;
    public ModelRenderer Skirt02;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft03;
    public ModelRenderer ArmLeft04;
    public ModelRenderer ArmLeft05;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight03;
    public ModelRenderer ArmRight04;
    public ModelRenderer ArmRight05;
    public ModelRenderer EquipL01;
    public ModelRenderer EquipR01;
    public ModelRenderer EquipL02;
    public ModelRenderer EquipL03;
    public ModelRenderer EquipL04;
    public ModelRenderer EquipL05;
    public ModelRenderer EquipR02;
    public ModelRenderer EquipR03;
    public ModelRenderer EquipR04;
    public ModelRenderer EquipR05;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowBodyMain2;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;


    public ModelCarrierWDemon()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.Cloth01 = new ModelRenderer(this, 0, 9);
        this.Cloth01.setRotationPoint(0.5F, -4.7F, -6.3F);
        this.Cloth01.addBox(-3.5F, 0.0F, 0.0F, 6, 7, 0, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 16, 8, 0.0F);
        this.HeadS05 = new ModelRenderer(this, 0, 0);
        this.HeadS05.setRotationPoint(-8.1F, -7.5F, -6.7F);
        this.HeadS05.addBox(0.0F, 0.0F, 0.0F, 0, 3, 3, 0.0F);
        this.setRotateAngle(HeadS05, 0.7853981633974483F, 0.0F, 0.0F);
        this.ArmLeft03 = new ModelRenderer(this, 100, 14);
        this.ArmLeft03.mirror = true;
        this.ArmLeft03.setRotationPoint(2.0F, 10.0F, 3.0F);
        this.ArmLeft03.addBox(-5.5F, 0.0F, -6.5F, 7, 8, 7, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 95);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 3, 6, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, -23.0F, 0.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.EquipL04 = new ModelRenderer(this, 67, 14);
        this.EquipL04.mirror = true;
        this.EquipL04.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.EquipL04.addBox(0.0F, 0.0F, -6.5F, 2, 9, 13, 0.0F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0.0F, -9.0F, -5.5F);
        this.Ahoke.addBox(0.0F, -4.0F, -11.5F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.6981317007977318F, 0.0F);
        this.HairR02 = new ModelRenderer(this, 86, 101);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 7.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.Skirt02 = new ModelRenderer(this, 42, 47);
        this.Skirt02.setRotationPoint(0.0F, 2.8F, -0.5F);
        this.Skirt02.addBox(-9.0F, 0.0F, -6.0F, 18, 4, 10, 0.0F);
        this.EquipL02 = new ModelRenderer(this, 0, 0);
        this.EquipL02.mirror = true;
        this.EquipL02.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.EquipL02.addBox(-0.5F, 0.0F, -10.0F, 3, 2, 20, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 2, 85);
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 5, 5, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 25, 85);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(0.5F, 3.0F, 0.0F);
        this.ArmLeft02.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.HeadS03 = new ModelRenderer(this, 0, 0);
        this.HeadS03.setRotationPoint(-2.5F, 2.9F, 0.0F);
        this.HeadS03.addBox(0.0F, 0.0F, 0.0F, 3, 3, 0, 0.0F);
        this.ShoesL04 = new ModelRenderer(this, 32, 0);
        this.ShoesL04.mirror = true;
        this.ShoesL04.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.ShoesL04.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.setRotateAngle(ShoesL04, -0.12F, 0.0F, 0.0F);
        this.HeadS02 = new ModelRenderer(this, 0, 0);
        this.HeadS02.setRotationPoint(2.9F, -2.5F, 0.0F);
        this.HeadS02.addBox(0.0F, 0.0F, 0.0F, 3, 3, 0, 0.0F);
        this.ShoesR04 = new ModelRenderer(this, 32, 0);
        this.ShoesR04.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.ShoesR04.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.setRotateAngle(ShoesR04, -0.12F, 0.0F, 0.0F);
        this.ShoesL02 = new ModelRenderer(this, 98, 0);
        this.ShoesL02.mirror = true;
        this.ShoesL02.setRotationPoint(0.0F, 3.0F, -0.7F);
        this.ShoesL02.addBox(-3.5F, 0.0F, -4.0F, 7, 4, 8, 0.0F);
        this.ArmLeft05 = new ModelRenderer(this, 72, 36);
        this.ArmLeft05.mirror = true;
        this.ArmLeft05.setRotationPoint(0.0F, 0.5F, 0.0F);
        this.ArmLeft05.addBox(-2.5F, 0.0F, -3.0F, 5, 5, 6, 0.0F);
        this.EquipL01 = new ModelRenderer(this, 0, 0);
        this.EquipL01.mirror = true;
        this.EquipL01.setRotationPoint(30.0F, 0.0F, 0.0F);
        this.EquipL01.addBox(0.0F, 0.0F, -3.5F, 2, 2, 7, 0.0F);
        this.ArmLeft04 = new ModelRenderer(this, 54, 49);
        this.ArmLeft04.mirror = true;
        this.ArmLeft04.setRotationPoint(-2.0F, 8.0F, -3.0F);
        this.ArmLeft04.addBox(-4.0F, 0.0F, -4.0F, 8, 4, 8, 0.0F);
        this.setRotateAngle(ArmLeft04, 0.0F, -0.08726646259971647F, 0.0F);
        this.HairR01 = new ModelRenderer(this, 86, 101);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-7.0F, 3.0F, -5.5F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairR01, -0.13962634015954636F, 0.17453292519943295F, 0.08726646259971647F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.HairL01 = new ModelRenderer(this, 86, 101);
        this.HairL01.setRotationPoint(7.0F, 3.0F, -5.5F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairL01, -0.13962634015954636F, -0.17453292519943295F, -0.08726646259971647F);
        this.HairL02 = new ModelRenderer(this, 86, 101);
        this.HairL02.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairL02, 0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.Hair02 = new ModelRenderer(this, 0, 58);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 5.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 16, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.08726646259971647F, 0.0F, 0.0F);
        this.ShoesR03 = new ModelRenderer(this, 66, 0);
        this.ShoesR03.setRotationPoint(0.0F, 4.0F, -0.9F);
        this.ShoesR03.addBox(-4.0F, 0.0F, -4.0F, 8, 4, 8, 0.0F);
        this.EquipL03 = new ModelRenderer(this, 43, 0);
        this.EquipL03.mirror = true;
        this.EquipL03.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.EquipL03.addBox(0.0F, 0.0F, -8.5F, 2, 9, 17, 0.0F);
        this.EquipR01 = new ModelRenderer(this, 0, 0);
        this.EquipR01.setRotationPoint(-30.0F, 0.0F, 0.0F);
        this.EquipR01.addBox(-2.0F, 0.0F, -3.5F, 2, 2, 7, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 2, 85);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 5, 5, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.EquipL05 = new ModelRenderer(this, 46, 29);
        this.EquipL05.mirror = true;
        this.EquipL05.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.EquipL05.addBox(0.0F, 0.0F, -4.5F, 2, 9, 9, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 0, 22);
        this.Skirt01.setRotationPoint(0.0F, 2.9F, 0.0F);
        this.Skirt01.addBox(-8.5F, 0.0F, -6.0F, 17, 4, 9, 0.0F);
        this.EquipR05 = new ModelRenderer(this, 46, 29);
        this.EquipR05.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.EquipR05.addBox(-2.0F, 0.0F, -4.5F, 2, 9, 9, 0.0F);
        this.HairMain = new ModelRenderer(this, 0, 57);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 12, 10, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.HeadS01 = new ShipModelRenderer(this, 0, 0);
        this.HeadS01.setRotationPoint(0.0F, -14.0F, -7.4F);
        this.HeadS01.addBox(0.0F, 0.0F, 0.0F, 3, 3, 0, 0.0F);
        this.HeadS01.tweakModel = true;
        this.HeadS01.scale2x = 1.0F;
  		this.HeadS01.scale2y = 0.7F;
  		this.HeadS01.scale2z = 1F;
  		this.HeadS01.trans2x = 0F;
  		this.HeadS01.trans2y = -0.25F;
  		this.HeadS01.trans2z = 0F;
        this.setRotateAngle(HeadS01, 0.0F, 0.0F, 0.7853981633974483F);
        this.LegRight02 = new ModelRenderer(this, 0, 95);
        this.LegRight02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 3, 6, 0.0F);
        this.Hair01 = new ModelRenderer(this, 0, 57);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 17, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.20943951023931953F, 0.0F, 0.0F);
        this.EquipR03 = new ModelRenderer(this, 43, 0);
        this.EquipR03.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.EquipR03.addBox(-2.0F, 0.0F, -8.5F, 2, 9, 17, 0.0F);
        this.Butt = new ModelRenderer(this, 52, 61);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipR04 = new ModelRenderer(this, 67, 14);
        this.EquipR04.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.EquipR04.addBox(-2.0F, 0.0F, -6.5F, 2, 9, 13, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 25, 85);
        this.ArmRight02.setRotationPoint(-0.5F, 3.0F, 0.0F);
        this.ArmRight02.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.ArmRight05 = new ModelRenderer(this, 72, 36);
        this.ArmRight05.setRotationPoint(0.0F, 0.5F, 0.0F);
        this.ArmRight05.addBox(-2.5F, 0.0F, -3.0F, 5, 5, 6, 0.0F);
        this.Hair03 = new ModelRenderer(this, 0, 35);
        this.Hair03.setRotationPoint(0.0F, 12.5F, -0.1F);
        this.Hair03.addBox(-8.0F, 0.0F, -4.5F, 16, 15, 7, 0.0F);
        this.setRotateAngle(Hair03, -0.13962634015954636F, 0.0F, 0.0F);
        this.BoobL = new ModelRenderer(this, 33, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.7F, -8.1F, -3.7F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, 0.13962634015954636F, 0.08726646259971647F);
        this.ShoesL03 = new ModelRenderer(this, 66, 0);
        this.ShoesL03.mirror = true;
        this.ShoesL03.setRotationPoint(0.0F, 4.0F, -0.9F);
        this.ShoesL03.addBox(-4.0F, 0.0F, -4.0F, 8, 4, 8, 0.0F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-3.7F, -8.1F, -3.7F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, -0.13962634015954636F, -0.08726646259971647F);
        this.ShoesR01 = new ModelRenderer(this, 99, 1);
        this.ShoesR01.setRotationPoint(0.0F, 1.0F, 2.6F);
        this.ShoesR01.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.setRotateAngle(ShoesR01, 0.2617993877991494F, 0.0F, 0.0F);
        this.ShoesL01 = new ModelRenderer(this, 99, 1);
        this.ShoesL01.mirror = true;
        this.ShoesL01.setRotationPoint(0.0F, 1.0F, 2.6F);
        this.ShoesL01.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(ShoesL01, 0.2617993877991494F, 0.0F, 0.0F);
        this.HeadS04 = new ModelRenderer(this, 0, 0);
        this.HeadS04.setRotationPoint(8.1F, -7.5F, -6.7F);
        this.HeadS04.addBox(0.0F, 0.0F, 0.0F, 0, 3, 3, 0.0F);
        this.setRotateAngle(HeadS04, 0.7853981633974483F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 88, 29);
        this.Neck.setRotationPoint(0.0F, -10.3F, -0.2F);
        this.Neck.addBox(-5.5F, -2.0F, -5.0F, 11, 3, 9, 0.0F);
        this.ArmRight04 = new ModelRenderer(this, 54, 49);
        this.ArmRight04.setRotationPoint(2.0F, 8.0F, -3.0F);
        this.ArmRight04.addBox(-4.0F, 0.0F, -4.0F, 8, 4, 8, 0.0F);
        this.setRotateAngle(ArmRight04, 0.0F, 0.08726646259971647F, 0.0F);
        this.ArmRight03 = new ModelRenderer(this, 100, 14);
        this.ArmRight03.setRotationPoint(-2.0F, 10.0F, 3.0F);
        this.ArmRight03.addBox(-1.5F, 0.0F, -6.5F, 7, 8, 7, 0.0F);
        this.EquipR02 = new ModelRenderer(this, 0, 0);
        this.EquipR02.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.EquipR02.addBox(-2.5F, 0.0F, -10.0F, 3, 2, 20, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ShoesR02 = new ModelRenderer(this, 98, 0);
        this.ShoesR02.setRotationPoint(0.0F, 3.0F, -0.7F);
        this.ShoesR02.addBox(-3.5F, 0.0F, -4.0F, 7, 4, 8, 0.0F);
        this.BodyMain.addChild(this.Cloth01);
        this.Head.addChild(this.Hair);
        this.Head.addChild(this.HeadS05);
        this.ArmLeft02.addChild(this.ArmLeft03);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipL03.addChild(this.EquipL04);
        this.Hair.addChild(this.Ahoke);
        this.HairR01.addChild(this.HairR02);
        this.Skirt01.addChild(this.Skirt02);
        this.EquipL01.addChild(this.EquipL02);
        this.BodyMain.addChild(this.ArmRight01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.HeadS01.addChild(this.HeadS03);
        this.ShoesL03.addChild(this.ShoesL04);
        this.HeadS01.addChild(this.HeadS02);
        this.ShoesR03.addChild(this.ShoesR04);
        this.ShoesL01.addChild(this.ShoesL02);
        this.ArmLeft04.addChild(this.ArmLeft05);
        this.ArmLeft03.addChild(this.ArmLeft04);
        this.Hair.addChild(this.HairR01);
        this.Neck.addChild(this.Head);
        this.Hair.addChild(this.HairL01);
        this.HairL01.addChild(this.HairL02);
        this.Hair01.addChild(this.Hair02);
        this.ShoesR02.addChild(this.ShoesR03);
        this.EquipL02.addChild(this.EquipL03);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Butt.addChild(this.LegLeft01);
        this.EquipL04.addChild(this.EquipL05);
        this.Butt.addChild(this.Skirt01);
        this.EquipR04.addChild(this.EquipR05);
        this.Head.addChild(this.HairMain);
        this.Head.addChild(this.HeadS01);
        this.LegRight01.addChild(this.LegRight02);
        this.HairMain.addChild(this.Hair01);
        this.EquipR02.addChild(this.EquipR03);
        this.BodyMain.addChild(this.Butt);
        this.EquipR03.addChild(this.EquipR04);
        this.ArmRight01.addChild(this.ArmRight02);
        this.ArmRight04.addChild(this.ArmRight05);
        this.Hair02.addChild(this.Hair03);
        this.BodyMain.addChild(this.BoobL);
        this.ShoesL02.addChild(this.ShoesL03);
        this.BodyMain.addChild(this.BoobR);
        this.LegRight02.addChild(this.ShoesR01);
        this.LegLeft02.addChild(this.ShoesL01);
        this.Head.addChild(this.HeadS04);
        this.BodyMain.addChild(this.Neck);
        this.ArmRight03.addChild(this.ArmRight04);
        this.ArmRight02.addChild(this.ArmRight03);
        this.EquipR01.addChild(this.EquipR02);
        this.Butt.addChild(this.LegRight01);
        this.ShoesR01.addChild(this.ShoesR02);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, -0.2F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.5F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        
        //發光支架2
        this.GlowBodyMain2 = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain2.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain2, -0.10471975511965977F, 0.0F, 0.0F);
        
        this.GlowBodyMain2.addChild(this.EquipBase);
        this.EquipBase.addChild(this.EquipL01);
        this.EquipBase.addChild(this.EquipR01);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
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
    	GlStateManager.scale(0.47F, 0.47F, 0.47F);
    	GlStateManager.translate(0F, 1.7F, 0F);
    	
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
    	float light = 80F + MathHelper.cos(f2 * 0.075F) * 80F;
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, light, light);
    	this.GlowBodyMain2.render(f5);
    	GlStateManager.enableLighting();
    	
    	GlStateManager.popMatrix();
    }
    
	//for idle/run animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    { 	
    	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	
		IShipEmotion ent = (IShipEmotion)entity;
		
		showEquip(ent);
		
		EmotionHelper.rollEmotion(this, ent);
		  
		if (ent.getStateFlag(ID.F.NoFuel))
		{
			motionStopPos(f, f1, f2, f3, f4, ent);
		}
		else
		{
			motionHumanPos(f, f1, f2, f3, f4, ent);
		}
		
		setGlowRotation();
    }
    
    private void showEquip(IShipEmotion ent)
    {
    	//equip display
    	if (ent.getStateEmotion(ID.S.State2) > ID.State.NORMAL_2)
    	{
    		this.EquipBase.isHidden = false;
    	}
    	else
    	{
    		this.EquipBase.isHidden = true;
    	}
  	}
    
    //設定模型發光部份的rotation
    private void setGlowRotation()
    {
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowBodyMain2.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain2.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain2.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowNeck.rotateAngleX = this.Neck.rotateAngleX;
		this.GlowNeck.rotateAngleY = this.Neck.rotateAngleY;
		this.GlowNeck.rotateAngleZ = this.Neck.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
    }
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
    {
    	GlStateManager.translate(0F, 0.48F, 0F);
  		setFace(4);
  		
  	    //頭部
	  	this.Head.rotateAngleX = 0F; 	//上下角度
	  	this.Head.rotateAngleY = 0F;	//左右角度
	  	this.Head.rotateAngleZ = 0F;
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = -0.7F;
  	    this.BoobR.rotateAngleX = -0.7F;
	  	//Body
  	    this.Ahoke.rotateAngleY = 0.7F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.21F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -0.09F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -0.14F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	//鬢毛調整
	    this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.HairL01.rotateAngleZ = 0.087F;
	  	this.HairL02.rotateAngleZ = 0.087F;
	  	this.HairR01.rotateAngleZ = 0.087F;
	  	this.HairR02.rotateAngleZ = -0.052F;
		this.HairL01.rotateAngleX = -0.65F;
	  	this.HairL02.rotateAngleX = 0.17F;
	  	this.HairR01.rotateAngleX = -0.65F;
	  	this.HairR02.rotateAngleX = 0.17F;
	    //arm 
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft05.rotateAngleZ = 0.2618F;
	  	this.ArmRight01.rotateAngleY = 0F;
	    this.ArmRight03.rotateAngleX = 0F;
	    this.ArmRight03.rotateAngleZ = 0F;
		//leg
	    this.LegLeft01.rotateAngleX = -1.0472F;
		this.LegLeft01.rotateAngleY = 0F;
		this.ShoesL04.rotateAngleX = -0.1F;
		this.LegRight01.rotateAngleX = -1.0472F;
		this.LegRight01.rotateAngleY = 0F;
		//equip
		this.EquipBase.rotateAngleX = 0F;
		this.EquipL01.offsetX = 0F;
		this.EquipL01.offsetY = 0F;
		this.EquipL01.offsetZ = 0F;
		this.EquipL01.rotateAngleX = 0.2618F;
    	this.EquipL01.rotateAngleY = 0.1745F;
    	this.EquipL01.rotateAngleZ = 0F;
    	this.EquipL05.rotateAngleZ = 0F;
		this.EquipR01.offsetX = 0F;
		this.EquipR01.offsetY = 0F;
		this.EquipR01.offsetZ = 0F;
    	this.EquipR01.rotateAngleX = 0.2618F;
    	this.EquipR01.rotateAngleY = -0.1745F;
    	this.EquipR01.rotateAngleZ = 0f;
  		
    	//head
    	this.Head.rotateAngleX = 0.55F;
    	this.Head.rotateAngleY = 0F;
    	this.Head.rotateAngleZ = 0F;
    	//hair 動到headX, 需重新調整hairX
    	this.Hair01.rotateAngleX = -0.1F;
    	this.Hair02.rotateAngleX = -0.2F;
    	//body
    	this.Neck.rotateAngleX = 0.3F;
    	this.Butt.rotateAngleX = -0.14F;
		this.Skirt01.rotateAngleX = -0.1745F;
		this.Skirt02.rotateAngleX = -0.2618F;
		//arm
		this.ArmLeft01.rotateAngleX = 0.4F;
		this.ArmLeft01.rotateAngleZ = -0.2618F;
		this.ArmLeft03.rotateAngleX = 0F;
		this.ArmLeft03.rotateAngleZ = 0F;
		this.ArmRight01.rotateAngleX = 0.4F;
		this.ArmRight01.rotateAngleZ = 0.2618F;
		//leg 
		this.LegLeft01.rotateAngleZ = -0.14F;
		this.LegLeft02.rotateAngleX = 1.2217F;
		this.LegLeft02.rotateAngleY = 1.2217F;
		this.LegLeft02.rotateAngleZ = -1.0472F;
		this.LegLeft02.offsetX = 0.175F;
		this.LegLeft02.offsetY = -0.02F;
		this.LegLeft02.offsetZ = 0.1635F;
		this.LegRight01.rotateAngleZ = 0.14F;
		this.LegRight02.rotateAngleX = 1.2217F;
		this.LegRight02.rotateAngleY = -1.2217F;
		this.LegRight02.rotateAngleZ = 1.0472F;
		this.LegRight02.offsetX = -0.175F;
		this.LegRight02.offsetY = -0.05F;
		this.LegRight02.offsetZ = 0.1635F;
    	//equip
    	this.EquipL01.offsetY = 0.6F;
    	this.EquipR01.offsetY = 0.6F;
    }
    
    private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
    {   
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
  		if (ent.getShipDepth(0) > 0D || ent.getShipDepth(1) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = angleAdd1 * 0.6F - 0.35F;
	  	addk2 = angleAdd2 * 0.6F - 0.07F;
	  	
  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F; 	//上下角度
	  	this.Head.rotateAngleY = f3 * 0.01F;	//左右角度
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = angleX * 0.08F - 0.7F;
  	    this.BoobR.rotateAngleX = angleX * 0.08F - 0.7F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.15F + 0.7F;
  	    this.Neck.rotateAngleX = 0.1F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.Butt.rotateAngleX = 0.3142F;
	  	this.Skirt01.rotateAngleX = -0.14F;
	  	this.Skirt02.rotateAngleX = -0.087F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.21F + headX;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.09F + headX;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -angleX2 * 0.07F - 0.14F;
	  	this.Hair03.rotateAngleZ = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = 0.2618F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = -0.7F;
	    this.ArmLeft03.rotateAngleX = -0.14F;
	    this.ArmLeft03.rotateAngleZ = 1.4835F;
	    this.ArmLeft05.rotateAngleZ = 0.2618F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.375F + 0.2618F;
	  	this.ArmRight01.rotateAngleY = 0F;
	    this.ArmRight01.rotateAngleZ = 0.2618F;
	    this.ArmRight03.rotateAngleX = 0F;
	    this.ArmRight03.rotateAngleZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.14F;
		this.LegLeft02.offsetX = 0;
		this.LegLeft02.offsetY = 0;
		this.LegLeft02.offsetZ = 0F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.ShoesL04.rotateAngleX = -0.1F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.14F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		//equip
		this.EquipBase.rotateAngleX = 0F;
		this.EquipL01.offsetX = 0F;
		this.EquipL01.offsetY = angleX * 0.125F;
		this.EquipL01.offsetZ = 0F;
		this.EquipL01.rotateAngleX = 0.2618F;
    	this.EquipL01.rotateAngleY = 0.1745F;
    	this.EquipL01.rotateAngleZ = 0F;
    	this.EquipL05.rotateAngleZ = 0F;
		this.EquipR01.offsetX = 0F;
		this.EquipR01.offsetY = -angleX * 0.125F;
		this.EquipR01.offsetZ = 0F;
    	this.EquipR01.rotateAngleX = 0.2618F;
    	this.EquipR01.rotateAngleY = -0.1745F;
    	this.EquipR01.rotateAngleZ = 0f;

	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {	//奔跑動作
	    	//hair
			this.Hair01.rotateAngleX += 0.09F;
			this.Hair02.rotateAngleX += 0.43F;
			this.Hair03.rotateAngleX += 0.49F;
			//胸部
	  	    this.BoobL.rotateAngleX = angleAdd2 * 0.1F - 0.83F;
	  	    this.BoobR.rotateAngleX = angleAdd1 * 0.1F - 0.83F;
	    	//arm 
		  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.6F + 0.2618F;
		  	this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft01.rotateAngleZ = -0.3F;
		    this.ArmLeft03.rotateAngleX = 0F;
		    this.ArmLeft03.rotateAngleZ = 0F;
		    this.ArmLeft05.rotateAngleZ = 0F;
		    this.ArmRight01.rotateAngleX = angleAdd1 * 0.6F + 0.2618F;
		  	this.ArmRight01.rotateAngleY = 0F;
		    this.ArmRight01.rotateAngleZ = 0.3F;
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {	//潛行, 蹲下動作
	    	GlStateManager.translate(0F, 0.05F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		  	this.Butt.rotateAngleX = -0.6283F;
			this.Skirt01.rotateAngleX = -0.1745F;
			this.Skirt02.rotateAngleX = -0.2618F;
		    //arm
		  	this.ArmLeft01.rotateAngleX = 0.2618F;
		  	this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
		  	this.ArmLeft03.rotateAngleX = 0F;
		  	this.ArmLeft03.rotateAngleZ = 0F;
		  	this.ArmLeft05.rotateAngleZ = 0F;
			this.ArmRight01.rotateAngleX = 0.2618F;
			this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight01.rotateAngleZ = -0.2618F;
			this.ArmRight05.rotateAngleZ = 0F;
			//hair
			this.Hair01.rotateAngleX += 0.37F;
			this.Hair02.rotateAngleX += 0.23F;
			this.Hair03.rotateAngleX -= 0.1F;
  		}//end if sneaking
  		
	    if (ent.getIsSitting() && !ent.getIsRiding())
	    {  //騎乘動作  	
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
		    	setFace(1);
		    	GlStateManager.translate(0F, 0.48F, 0F);
		    	//head
		    	int nodf2 = (int)f2 % 60;
		    	this.Head.rotateAngleX = 0.3F;
		    	if (nodf2 < 30)
		    	{
		    		if (nodf2 < 6)
		    		{
		    			this.Head.rotateAngleX = nodf2 * 0.02F + 0.3F;
		    		}
		    		else if (nodf2 < 11)
		    		{
		    			this.Head.rotateAngleX = (nodf2 - 5) * 0.03F + 0.4F;
		    		}
		    		else if (nodf2 < 14)
		    		{
		    			this.Head.rotateAngleX = (nodf2 - 10) * -0.09F + 0.55F;
		    		}
		    	}
		    	this.Head.rotateAngleY = 0F;
		    	this.Head.rotateAngleZ = 0F;
		    	//hair 動到headX, 需重新調整hairX
		    	headX = this.Head.rotateAngleX * -0.5F;
		    	this.Hair01.rotateAngleX = angleX * 0.012F + 0.21F + headX;
		    	this.Hair02.rotateAngleX = angleX * 0.015F - 0.09F + headX;
		    	//body
		    	this.Neck.rotateAngleX = 0.3F;
		    	this.Butt.rotateAngleX = -0.14F;
				this.Skirt01.rotateAngleX = -0.1745F;
				this.Skirt02.rotateAngleX = -0.2618F;
				//arm
				this.ArmLeft01.rotateAngleX = 0.4F;
				this.ArmLeft01.rotateAngleZ = -0.2618F;
				this.ArmLeft03.rotateAngleX = 0F;
				this.ArmLeft03.rotateAngleZ = 0F;
				this.ArmRight01.rotateAngleX = 0.4F;
				this.ArmRight01.rotateAngleZ = 0.2618F;
				//leg
				addk1 = -1.0472F;
				addk2 = -1.0472F;
				this.LegLeft01.rotateAngleZ = -0.14F;
				this.LegLeft02.rotateAngleX = 1.2217F;
				this.LegLeft02.rotateAngleY = 1.2217F;
				this.LegLeft02.rotateAngleZ = -1.0472F;
				this.LegLeft02.offsetX = 0.175F;
				this.LegLeft02.offsetY = -0.02F;
				this.LegLeft02.offsetZ = 0.1635F;
				this.LegRight01.rotateAngleZ = 0.14F;
				this.LegRight02.rotateAngleX = 1.2217F;
				this.LegRight02.rotateAngleY = -1.2217F;
				this.LegRight02.rotateAngleZ = 1.0472F;
				this.LegRight02.offsetX = -0.175F;
				this.LegRight02.offsetY = -0.05F;
				this.LegRight02.offsetZ = 0.1635F;
		    	//equip
		    	this.EquipL01.offsetY = 0.6F;
		    	this.EquipR01.offsetY = 0.6F;
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, 0.4F, 0F);
		    	//body
		    	this.Neck.rotateAngleX = 0.35F;
		    	this.BodyMain.rotateAngleX = -0.6283F;
		    	this.Butt.rotateAngleX = -0.6283F;
				this.Skirt01.rotateAngleX = -0.1745F;
				this.Skirt02.rotateAngleX = -0.2618F;
				//arm
				this.ArmRight01.rotateAngleX = angleX * 0.125F + 0.5236F;
				//leg
				addk1 = -0.8727F;
				addk2 = -0.35F;
				this.LegLeft01.rotateAngleZ = 0.4363F;
				this.LegLeft02.rotateAngleX = 0.7854F;
				this.LegRight01.rotateAngleZ = -0.35F;
				this.LegRight02.rotateAngleX = 0.8727F;
		    	this.ShoesL04.rotateAngleX = angleX * 0.25F - 0.1F;
		    	//equip
		    	this.EquipL01.offsetX = -1.9F;
		    	this.EquipL01.offsetY = 0.6F;
		    	this.EquipL01.offsetZ = 0.4F;
		    	this.EquipL01.rotateAngleX = 0F;
		    	this.EquipL01.rotateAngleY = 1.57F;
		    	this.EquipL05.rotateAngleZ = -1F;
		    	this.EquipR01.offsetX = 1.9F;
		    	this.EquipR01.offsetY = -1.0F;
		    	this.EquipR01.offsetZ = -0.4F;
		    	this.EquipR01.rotateAngleX = -1.5708F;
		    	this.EquipR01.rotateAngleY = 0.6F;
		    	this.EquipR01.rotateAngleZ = -1.5708F;
	    	}
  		}//end sitting
	    
	    if (ent.getIsRiding())
	    {
	    	if (((Entity)ent).getRidingEntity() instanceof BasicEntityMount)
	    	{
	    		this.EquipBase.isHidden = false;
	    		
	    		if (ent.getIsSitting())
	    		{
	    			if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    			{
	    				setFace(1);
	    				GlStateManager.translate(0F, 0.48F, 0F);
				    	//head
				    	int nodf2 = (int)f2 % 60;
				    	this.Head.rotateAngleX = 0.3F;
				    	if (nodf2 < 30)
				    	{
				    		if (nodf2 < 6)
				    		{
				    			this.Head.rotateAngleX = nodf2 * 0.02F + 0.3F;
				    		}
				    		else if (nodf2 < 11)
				    		{
				    			this.Head.rotateAngleX = (nodf2 - 5) * 0.03F + 0.4F;
				    		}
				    		else if (nodf2 < 14)
				    		{
				    			this.Head.rotateAngleX = (nodf2 - 10) * -0.09F + 0.55F;
				    		}
				    	}
				    	this.Head.rotateAngleY = 0F;
				    	this.Head.rotateAngleZ = 0F;
				    	//hair 動到headX, 需重新調整hairX
				    	headX = this.Head.rotateAngleX * -0.5F;
				    	this.Hair01.rotateAngleX = angleX * 0.012F + 0.21F + headX;
				    	this.Hair02.rotateAngleX = angleX * 0.015F - 0.09F + headX;
				    	//body
				    	this.Neck.rotateAngleX = 0.3F;
				    	this.Butt.rotateAngleX = -0.14F;
						this.Skirt01.rotateAngleX = -0.1745F;
						this.Skirt02.rotateAngleX = -0.2618F;
						//arm
						this.ArmLeft01.rotateAngleX = 0.4F;
						this.ArmLeft01.rotateAngleZ = -0.2618F;
						this.ArmLeft03.rotateAngleX = 0F;
						this.ArmLeft03.rotateAngleZ = 0F;
						this.ArmRight01.rotateAngleX = 0.4F;
						this.ArmRight01.rotateAngleZ = 0.2618F;
						//leg
						addk1 = -1.0472F;
						addk2 = -1.0472F;
						this.LegLeft01.rotateAngleZ = -0.14F;
						this.LegLeft02.rotateAngleX = 1.2217F;
						this.LegLeft02.rotateAngleY = 1.2217F;
						this.LegLeft02.rotateAngleZ = -1.0472F;
						this.LegLeft02.offsetX = 0.175F;
						this.LegLeft02.offsetY = -0.02F;
						this.LegLeft02.offsetZ = 0.1635F;
						this.LegRight01.rotateAngleZ = 0.14F;
						this.LegRight02.rotateAngleX = 1.2217F;
						this.LegRight02.rotateAngleY = -1.2217F;
						this.LegRight02.rotateAngleZ = 1.0472F;
						this.LegRight02.offsetX = -0.175F;
						this.LegRight02.offsetY = -0.05F;
						this.LegRight02.offsetZ = 0.1635F;
				    	//equip
						this.EquipBase.rotateAngleX = -0.4F;
						this.EquipL01.offsetX = -0.3F;
				    	this.EquipL01.offsetY = 0.6F;
				    	this.EquipL01.offsetZ = 0.6F;
				    	this.EquipL01.rotateAngleY = 1.4F;
				    	this.EquipR01.offsetX = 0.3F;
				    	this.EquipR01.offsetY = 0.6F;
				    	this.EquipR01.offsetZ = 0.6F;
				    	this.EquipR01.rotateAngleY = -1.4F;
			    	}
			    	else
			    	{
			    		GlStateManager.translate(0F, 0.64F, -0.11F);
				    	//body
				    	this.Neck.rotateAngleX = 0.35F;
				    	this.BodyMain.rotateAngleX = -0.6283F;
				    	this.Butt.rotateAngleX = -0.6283F;
						this.Skirt01.rotateAngleX = -0.1745F;
						this.Skirt02.rotateAngleX = -0.2618F;
						//arm
						this.ArmRight01.rotateAngleX = angleX * 0.125F + 0.5236F;
						this.ArmRight01.rotateAngleZ = 0.45F;
						this.ArmRight03.rotateAngleX = -0.5F;
						//leg
						addk1 = -0.8727F;
						addk2 = -0.35F;
						this.LegLeft01.rotateAngleZ = 0.4363F;
						this.LegLeft02.rotateAngleX = 0.7854F;
						this.LegRight01.rotateAngleZ = -0.35F;
						this.LegRight02.rotateAngleX = 0.8727F;
				    	this.ShoesL04.rotateAngleX = angleX * 0.25F - 0.1F;
				    	//equip
				    	this.EquipBase.rotateAngleX = 0.2F;
				    	this.EquipL01.offsetX = -0.25F;
				    	this.EquipL01.offsetY = 0.1F;
				    	this.EquipL01.rotateAngleY = 1.4F;
				    	this.EquipR01.offsetX = 0.25F;
				    	this.EquipR01.offsetY = 0.1F;
				    	this.EquipR01.rotateAngleY = -1.4F;
			    	}
		    	}//end if sitting
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.64F, -0.11F);
			    	//body
			    	this.Neck.rotateAngleX = 0.35F;
			    	this.BodyMain.rotateAngleX = -0.6283F;
			    	this.Butt.rotateAngleX = -0.6283F;
					this.Skirt01.rotateAngleX = -0.1745F;
					this.Skirt02.rotateAngleX = -0.2618F;
					//arm
					this.ArmRight01.rotateAngleX = angleX * 0.125F + 0.5236F;
					this.ArmRight01.rotateAngleZ = 0.45F;
					this.ArmRight03.rotateAngleX = -0.5F;
					//leg
					addk1 = -0.8727F;
					addk2 = -0.35F;
					this.LegLeft01.rotateAngleZ = 0.4363F;
					this.LegLeft02.rotateAngleX = 0.7854F;
					this.LegRight01.rotateAngleZ = -0.35F;
					this.LegRight02.rotateAngleX = 0.8727F;
			    	this.ShoesL04.rotateAngleX = angleX * 0.25F - 0.1F;
			    	//equip
			    	this.EquipBase.rotateAngleX = -0.9F;
			    	this.EquipL01.offsetX = -0.25F;
			    	this.EquipL01.offsetY = 0.1F;
			    	this.EquipL01.offsetZ = 0.45F;
			    	this.EquipL01.rotateAngleY = 1.4F;
			    	this.EquipR01.offsetX = 0.25F;
			    	this.EquipR01.offsetY = 0.1F;
			    	this.EquipR01.offsetZ = 0.45F;
			    	this.EquipR01.rotateAngleY = -1.4F;
		    	}
	    	}//end ship mount
	    	else
	    	{	//normal mount ex: cart
	    		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    		{
	    			setFace(1);
	    			GlStateManager.translate(0F, 0.48F, 0F);
			    	//head
			    	int nodf2 = (int)f2 % 60;
			    	this.Head.rotateAngleX = 0.2F;
			    	if (nodf2 < 30)
			    	{
			    		if (nodf2 < 6)
			    		{
			    			this.Head.rotateAngleX = nodf2 * 0.02F + 0.2F;
			    		}
			    		else if (nodf2 < 11)
			    		{
			    			this.Head.rotateAngleX = (nodf2 - 5) * 0.03F + 0.3F;
			    		}
			    		else if (nodf2 < 14)
			    		{
			    			this.Head.rotateAngleX = (nodf2 - 10) * -0.09F + 0.45F;
			    		}
			    	}
			    	this.Head.rotateAngleY = 0F;
			    	this.Head.rotateAngleZ = 0F;
			    	//hair 動到headX, 需重新調整hairX
			    	headX = this.Head.rotateAngleX * -0.5F;
			    	this.Hair01.rotateAngleX = angleX * 0.012F + 0.21F + headX;
			    	this.Hair02.rotateAngleX = angleX * 0.015F - 0.09F + headX;
			    	//body
			    	this.Neck.rotateAngleX = 0.3F;
			    	this.Butt.rotateAngleX = -0.14F;
					this.Skirt01.rotateAngleX = -0.1745F;
					this.Skirt02.rotateAngleX = -0.2618F;
					//arm
					this.ArmLeft01.rotateAngleX = 0.4F;
					this.ArmLeft01.rotateAngleZ = -0.2618F;
					this.ArmLeft03.rotateAngleX = 0F;
					this.ArmLeft03.rotateAngleZ = 0F;
					this.ArmRight01.rotateAngleX = 0.4F;
					this.ArmRight01.rotateAngleZ = 0.2618F;
					//leg
					addk1 = -1.0472F;
					addk2 = -1.0472F;
					this.LegLeft01.rotateAngleZ = -0.14F;
					this.LegLeft02.rotateAngleX = 1.2217F;
					this.LegLeft02.rotateAngleY = 1.2217F;
					this.LegLeft02.rotateAngleZ = -1.0472F;
					this.LegLeft02.offsetX = 0.175F;
					this.LegLeft02.offsetY = -0.02F;
					this.LegLeft02.offsetZ = 0.1635F;
					this.LegRight01.rotateAngleZ = 0.14F;
					this.LegRight02.rotateAngleX = 1.2217F;
					this.LegRight02.rotateAngleY = -1.2217F;
					this.LegRight02.rotateAngleZ = 1.0472F;
					this.LegRight02.offsetX = -0.175F;
					this.LegRight02.offsetY = -0.05F;
					this.LegRight02.offsetZ = 0.1635F;
			    	//equip
			    	this.EquipL01.offsetY = 0.6F;
			    	this.EquipR01.offsetY = 0.6F;
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.39F, 0F);
			    	//body
			    	this.Neck.rotateAngleX = 0.35F;
			    	this.BodyMain.rotateAngleX = -0.6283F;
			    	this.Butt.rotateAngleX = -0.6283F;
					this.Skirt01.rotateAngleX = -0.1745F;
					this.Skirt02.rotateAngleX = -0.2618F;
					//arm
					this.ArmRight01.rotateAngleX = angleX * 0.125F + 0.5236F;
					//leg
					addk1 = -0.8727F;
					addk2 = -0.35F;
					this.LegLeft01.rotateAngleZ = 0.4363F;
					this.LegLeft02.rotateAngleX = 0.7854F;
					this.LegRight01.rotateAngleZ = -0.35F;
					this.LegRight02.rotateAngleX = 0.8727F;
			    	this.ShoesL04.rotateAngleX = angleX * 0.25F - 0.1F;
			    	//equip
			    	this.EquipL01.offsetX = -1.9F;
			    	this.EquipL01.offsetY = 0.6F;
			    	this.EquipL01.offsetZ = 0.4F;
			    	this.EquipL01.rotateAngleX = 0F;
			    	this.EquipL01.rotateAngleY = 1.57F;
			    	this.EquipL05.rotateAngleZ = -1F;
			    	this.EquipR01.offsetX = 1.9F;
			    	this.EquipR01.offsetY = -1.0F;
			    	this.EquipR01.offsetZ = -0.4F;
			    	this.EquipR01.rotateAngleX = -1.5708F;
			    	this.EquipR01.rotateAngleY = 0.6F;
			    	this.EquipR01.rotateAngleZ = -1.5708F;
		    	}
	    	}
	    }//end ridding
    
	    //攻擊動作    
	    if (ent.getAttackTick() > 0)
	    {
	    	if (ent.getIsRiding())
	    	{
	    		//arm
		    	this.ArmRight01.rotateAngleX = -1.1F;
		    	this.ArmRight03.rotateAngleX = 0F;
		    	//equip
		    	this.EquipBase.isHidden = false;
		    	this.EquipBase.rotateAngleX = -1.2F + f4 * Values.N.DIV_PI_180;
		    	this.EquipL01.rotateAngleX = -0.1F;
		    	this.EquipR01.rotateAngleX = 0.1F;
	    	}
	    	else
	    	{
	    		//arm
		    	this.ArmRight01.rotateAngleX = -1.5F;
		    	//equip
		    	this.EquipBase.isHidden = false;
		    	this.EquipBase.rotateAngleX = -1.6F + f4 * Values.N.DIV_PI_180;
		    	this.EquipL01.offsetY = 0F;
		    	this.EquipR01.offsetY = 0F;
	    	}
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.4F;
	        this.ArmRight01.rotateAngleY = 0F;
	        this.ArmRight01.rotateAngleZ = -0.2F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	  	}
	    
	    //鬢毛調整
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	this.HairL01.rotateAngleZ = headZ - 0.087F;
	  	this.HairL02.rotateAngleZ = headZ + 0.087F;
	  	this.HairR01.rotateAngleZ = headZ + 0.087F;
	  	this.HairR02.rotateAngleZ = headZ - 0.052F;
	    headX = this.Head.rotateAngleX * -0.5F;
		this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
	  	this.HairL02.rotateAngleX = angleX * 0.02F + headX + 0.17F;
	  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.14F;
	  	this.HairR02.rotateAngleX = angleX * 0.02F + headX + 0.17F;
	  	
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
  	
    //設定顯示的臉型
  	@Override
  	public void setFace(int emo)
  	{
  		switch (emo)
  		{
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

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void setField(int id, float value)
	{
	}

	@Override
	public float getField(int id)
	{
		return 0;
	}
    
   
}