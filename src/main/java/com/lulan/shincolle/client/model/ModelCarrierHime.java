package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelCarrierHime - PinkaLulan
 * Created using Tabula 4.1.1  2016/5/15
 */
public class ModelCarrierHime extends ModelBase implements IModelEmotionAdv
{
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft01;
    public ModelRenderer Cloth01;
    public ModelRenderer Cloth02;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face0;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair04;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer Hair05;
    public ModelRenderer Hair06;
    public ModelRenderer Hair07;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft02;
    public ModelRenderer LegLeft01a;
    public ModelRenderer LegLeft01b;
    public ModelRenderer ShoesL01;
    public ModelRenderer ShoesL02;
    public ModelRenderer ShoesL03;
    public ModelRenderer ShoesL04;
    public ModelRenderer Skirt02;
    public ModelRenderer LegRight01a;
    public ModelRenderer LegRight01b;
    public ModelRenderer LegRight02;
    public ModelRenderer ShoesR01;
    public ModelRenderer ShoesR02;
    public ModelRenderer ShoesR03;
    public ModelRenderer ShoesR04;
    public ModelRenderer ArmRight01a;
    public ModelRenderer ArmRight01b;
    public ModelRenderer ArmRight02;
    public ModelRenderer EquipSR01;
    public ModelRenderer EquipSR02;
    public ModelRenderer EquipSR04;
    public ModelRenderer EquipSR03;
    public ModelRenderer EquipSR05;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft01a;
    public ModelRenderer ArmLeft01b;
    public ModelRenderer EquipSL01;
    public ModelRenderer EquipSL02;
    public ModelRenderer EquipSL04;
    public ModelRenderer EquipSL03;
    public ModelRenderer EquipSL05;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowBodyMain2;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowArmLeft01;
    public ModelRenderer GlowArmLeft02;
    public ModelRenderer GlowArmRight01;
    public ModelRenderer GlowArmRight02;

    
    public ModelCarrierHime()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.LegLeft02 = new ModelRenderer(this, 92, 2);
        this.LegLeft02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 3, 6, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face3.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.ShoesL03 = new ModelRenderer(this, 95, 0);
        this.ShoesL03.setRotationPoint(0.0F, 2.4F, -0.7F);
        this.ShoesL03.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(ShoesL03, -0.13962634015954636F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 52, 61);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 103, 0);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 3, 6, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face4.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 16, 8, 0.0F);
        this.ArmLeft01b = new ModelRenderer(this, 90, 6);
        this.ArmLeft01b.mirror = true;
        this.ArmLeft01b.setRotationPoint(0.5F, 9.0F, -0.1F);
        this.ArmLeft01b.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmLeft01b, 0.20943951023931953F, 0.0F, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face2.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.ShoesR04 = new ModelRenderer(this, 104, 13);
        this.ShoesR04.mirror = true;
        this.ShoesR04.setRotationPoint(0.0F, 2.2F, -0.3F);
        this.ShoesR04.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(ShoesR04, -0.20943951023931953F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 83);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.17453292519943295F, 0.0F, -0.10471975511965977F);
        this.LegLeft01a = new ModelRenderer(this, 92, 2);
        this.LegLeft01a.setRotationPoint(0.0F, 8.6F, -0.2F);
        this.LegLeft01a.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(LegLeft01a, 0.20943951023931953F, 0.0F, 0.0F);
        this.ShoesL04 = new ModelRenderer(this, 104, 13);
        this.ShoesL04.setRotationPoint(0.0F, 2.2F, -0.3F);
        this.ShoesL04.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(ShoesL04, -0.20943951023931953F, 0.0F, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 46, 34);
        this.Skirt01.setRotationPoint(0.0F, 2.9F, 0.0F);
        this.Skirt01.addBox(-8.5F, 0.0F, -6.0F, 17, 4, 9, 0.0F);
        this.setRotateAngle(Skirt01, -0.13962634015954636F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 106, 31);
        this.Ahoke.setRotationPoint(0.0F, -7.0F, -6.0F);
        this.Ahoke.addBox(0.0F, -6.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.20943951023931953F, 0.6981317007977318F, 0.0F);
        this.EquipSR03 = new ModelRenderer(this, 0, 0);
        this.EquipSR03.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR03.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.EquipSL01 = new ModelRenderer(this, 0, 0);
        this.EquipSL01.setRotationPoint(-3.0F, 10.5F, -6.0F);
        this.EquipSL01.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.setRotateAngle(EquipSL01, -1.5707963267948966F, 0.0F, 1.5707963267948966F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 0, 22);
        this.Cloth01.setRotationPoint(0.0F, -11.5F, -0.3F);
        this.Cloth01.addBox(-7.0F, 0.0F, -4.0F, 14, 5, 8, 0.0F);
        this.HairR02 = new ModelRenderer(this, 86, 101);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 7.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.ShoesL01 = new ModelRenderer(this, 97, 2);
        this.ShoesL01.setRotationPoint(0.0F, 2.0F, 3.0F);
        this.ShoesL01.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.setRotateAngle(ShoesL01, 0.13962634015954636F, 0.0F, 0.0F);
        this.BoobL = new ModelRenderer(this, 33, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.5F, -8.1F, -3.7F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, 0.08726646259971647F, 0.08726646259971647F);
        this.EquipSR04 = new ModelRenderer(this, 107, 0);
        this.EquipSR04.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipSR04.addBox(-0.5F, -9.0F, -0.5F, 1, 9, 1, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 4, 85);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.3490658503988659F, 0.0F, -0.2617993877991494F);
        this.ArmRight02 = new ModelRenderer(this, 93, 0);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 12.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.HairMain = new ModelRenderer(this, 0, 57);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 12, 10, 0.0F);
        this.Cloth02 = new ModelRenderer(this, 36, 93);
        this.Cloth02.setRotationPoint(0.3F, -4.5F, -6.5F);
        this.Cloth02.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 0, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 42, 47);
        this.Skirt02.setRotationPoint(0.0F, 2.8F, -0.5F);
        this.Skirt02.addBox(-9.0F, 0.0F, -6.0F, 18, 4, 10, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.HairR01 = new ModelRenderer(this, 86, 101);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-7.0F, 3.0F, -5.5F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairR01, -0.13962634015954636F, 0.17453292519943295F, 0.08726646259971647F);
        this.Hair02 = new ModelRenderer(this, 0, 58);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 5.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 16, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.08726646259971647F, 0.0F, 0.0F);
        this.ShoesR01 = new ModelRenderer(this, 100, 0);
        this.ShoesR01.mirror = true;
        this.ShoesR01.setRotationPoint(0.0F, 2.0F, 3.0F);
        this.ShoesR01.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.setRotateAngle(ShoesR01, 0.13962634015954636F, 0.0F, 0.0F);
        this.EquipSR01 = new ModelRenderer(this, 0, 0);
        this.EquipSR01.setRotationPoint(3.0F, 10.5F, -6.0F);
        this.EquipSR01.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.setRotateAngle(EquipSR01, -1.5707963267948966F, 0.0F, 1.5707963267948966F);
        this.Hair06 = new ModelRenderer(this, 109, 28);
        this.Hair06.setRotationPoint(2.5F, 4.0F, 0.0F);
        this.Hair06.addBox(-2.0F, 0.0F, -2.5F, 4, 7, 5, 0.0F);
        this.setRotateAngle(Hair06, 0.20943951023931953F, 0.0F, 0.13962634015954636F);
        this.Hair03 = new ModelRenderer(this, 0, 35);
        this.Hair03.setRotationPoint(0.0F, 12.5F, -0.1F);
        this.Hair03.addBox(-8.0F, 0.0F, -4.5F, 16, 15, 7, 0.0F);
        this.setRotateAngle(Hair03, -0.13962634015954636F, 0.0F, 0.0F);
        this.LegRight01a = new ModelRenderer(this, 95, 0);
        this.LegRight01a.mirror = true;
        this.LegRight01a.setRotationPoint(0.0F, 8.6F, -0.2F);
        this.LegRight01a.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(LegRight01a, 0.20943951023931953F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 100, 2);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.Neck.addBox(-3.0F, -2.0F, -3.5F, 6, 2, 6, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.ShoesR02 = new ModelRenderer(this, 90, 0);
        this.ShoesR02.mirror = true;
        this.ShoesR02.setRotationPoint(0.0F, 2.5F, -0.7F);
        this.ShoesR02.addBox(-4.0F, 0.0F, -4.0F, 8, 3, 9, 0.0F);
        this.setRotateAngle(ShoesR02, 0.20943951023931953F, 0.0F, 0.0F);
        this.EquipSL02 = new ModelRenderer(this, 0, 0);
        this.EquipSL02.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSL02.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.HairL01 = new ModelRenderer(this, 86, 101);
        this.HairL01.setRotationPoint(7.0F, 3.0F, -5.5F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairL01, -0.13962634015954636F, -0.17453292519943295F, -0.08726646259971647F);
        this.EquipSR05 = new ModelRenderer(this, 100, 0);
        this.EquipSR05.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.EquipSR05.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 96, 2);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.0F, 12.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 83);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.3490658503988659F, 0.0F, 0.10471975511965977F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.HairL02 = new ModelRenderer(this, 86, 101);
        this.HairL02.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairL02, 0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.Hair01 = new ModelRenderer(this, 0, 57);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 17, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.20943951023931953F, 0.0F, 0.0F);
        this.EquipSL04 = new ModelRenderer(this, 109, 0);
        this.EquipSL04.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipSL04.addBox(-0.5F, -9.0F, -0.5F, 1, 9, 1, 0.0F);
        this.Hair05 = new ModelRenderer(this, 108, 28);
        this.Hair05.setRotationPoint(1.5F, -1.0F, 0.0F);
        this.Hair05.addBox(0.0F, -2.5F, -2.5F, 5, 9, 5, 0.0F);
        this.setRotateAngle(Hair05, 0.10471975511965977F, -0.08726646259971647F, -0.17453292519943295F);
        this.ArmLeft01a = new ModelRenderer(this, 90, 9);
        this.ArmLeft01a.mirror = true;
        this.ArmLeft01a.setRotationPoint(0.5F, 5.5F, -0.2F);
        this.ArmLeft01a.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmLeft01a, 0.20943951023931953F, 0.0F, 0.0F);
        this.LegLeft01b = new ModelRenderer(this, 93, 3);
        this.LegLeft01b.setRotationPoint(0.0F, 11.6F, -0.1F);
        this.LegLeft01b.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(LegLeft01b, 0.20943951023931953F, 0.0F, 0.0F);
        this.ShoesR03 = new ModelRenderer(this, 100, 3);
        this.ShoesR03.mirror = true;
        this.ShoesR03.setRotationPoint(0.0F, 2.4F, -0.7F);
        this.ShoesR03.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(ShoesR03, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipSR02 = new ModelRenderer(this, 0, 0);
        this.EquipSR02.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR02.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.EquipSL05 = new ModelRenderer(this, 100, 0);
        this.EquipSL05.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.EquipSL05.addBox(-1.0F, -2.0F, -1.0F, 2, 2, 2, 0.0F);
        this.EquipSL03 = new ModelRenderer(this, 0, 0);
        this.EquipSL03.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSL03.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.ShoesL02 = new ModelRenderer(this, 90, 0);
        this.ShoesL02.setRotationPoint(0.0F, 2.5F, -0.7F);
        this.ShoesL02.addBox(-4.0F, 0.0F, -4.0F, 8, 3, 9, 0.0F);
        this.setRotateAngle(ShoesL02, 0.20943951023931953F, 0.0F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 0, 85);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.0F, 0.2617993877991494F);
        this.Hair07 = new ModelRenderer(this, 110, 29);
        this.Hair07.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Hair07.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.setRotateAngle(Hair07, -0.2617993877991494F, 0.0F, 0.13962634015954636F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-3.5F, -8.1F, -3.7F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, -0.08726646259971647F, -0.08726646259971647F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face1.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.Hair04 = new ModelRenderer(this, 108, 0);
        this.Hair04.setRotationPoint(6.5F, 3.5F, 6.0F);
        this.Hair04.addBox(0.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.setRotateAngle(Hair04, 0.0F, -0.08726646259971647F, -0.08726646259971647F);
        this.LegRight01b = new ModelRenderer(this, 96, 2);
        this.LegRight01b.mirror = true;
        this.LegRight01b.setRotationPoint(0.0F, 11.6F, -0.1F);
        this.LegRight01b.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(LegRight01b, 0.20943951023931953F, 0.0F, 0.0F);
        this.ArmRight01a = new ModelRenderer(this, 92, 3);
        this.ArmRight01a.setRotationPoint(-0.5F, 5.5F, -0.2F);
        this.ArmRight01a.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmRight01a, 0.20943951023931953F, 0.0F, 0.0F);
        this.ArmRight01b = new ModelRenderer(this, 92, 0);
        this.ArmRight01b.setRotationPoint(-0.5F, 9.0F, -0.1F);
        this.ArmRight01b.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmRight01b, 0.20943951023931953F, 0.0F, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -7.1F);
        this.Face0.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.LegLeft01.addChild(this.LegLeft02);
        this.ShoesL02.addChild(this.ShoesL03);
        this.BodyMain.addChild(this.Butt);
        this.LegRight01.addChild(this.LegRight02);
        this.Head.addChild(this.Hair);
        this.ArmLeft01.addChild(this.ArmLeft01b);
        this.ShoesR03.addChild(this.ShoesR04);
        this.Butt.addChild(this.LegRight01);
        this.LegLeft01.addChild(this.LegLeft01a);
        this.ShoesL03.addChild(this.ShoesL04);
        this.Butt.addChild(this.Skirt01);
        this.Hair.addChild(this.Ahoke);
        this.BodyMain.addChild(this.Cloth01);
        this.HairR01.addChild(this.HairR02);
        this.LegLeft02.addChild(this.ShoesL01);
        this.BodyMain.addChild(this.BoobL);
        this.BodyMain.addChild(this.ArmLeft01);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Head.addChild(this.HairMain);
        this.BodyMain.addChild(this.Cloth02);
        this.Skirt01.addChild(this.Skirt02);
        this.Hair.addChild(this.HairR01);
        this.Hair01.addChild(this.Hair02);
        this.LegRight02.addChild(this.ShoesR01);
        this.Hair05.addChild(this.Hair06);
        this.Hair02.addChild(this.Hair03);
        this.LegRight01.addChild(this.LegRight01a);
        this.BodyMain.addChild(this.Neck);
        this.ShoesR01.addChild(this.ShoesR02);
        this.Hair.addChild(this.HairL01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.Butt.addChild(this.LegLeft01);
        this.Neck.addChild(this.Head);
        this.HairL01.addChild(this.HairL02);
        this.HairMain.addChild(this.Hair01);
        this.Hair04.addChild(this.Hair05);
        this.ArmLeft01.addChild(this.ArmLeft01a);
        this.LegLeft01.addChild(this.LegLeft01b);
        this.ShoesR02.addChild(this.ShoesR03);
        this.ShoesL01.addChild(this.ShoesL02);
        this.BodyMain.addChild(this.ArmRight01);
        this.Hair06.addChild(this.Hair07);
        this.BodyMain.addChild(this.BoobR);
        this.HairMain.addChild(this.Hair04);
        this.LegRight01.addChild(this.LegRight01b);
        this.ArmRight01.addChild(this.ArmRight01a);
        this.ArmRight01.addChild(this.ArmRight01b);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowBodyMain2 = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain2.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain2, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.setRotateAngle(GlowNeck, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1F, -0.7F);
        this.GlowArmLeft01 = new ModelRenderer(this, 0, 0);
        this.GlowArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.setRotateAngle(GlowArmLeft01, 0.3490658503988659F, 0.0F, -0.2617993877991494F);
        this.GlowArmLeft02 = new ModelRenderer(this, 0, 0);
        this.GlowArmLeft02.setRotationPoint(3.0F, 12.0F, 2.5F);
        this.GlowArmRight01 = new ModelRenderer(this, 0, 0);
        this.GlowArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.setRotateAngle(GlowArmRight01, 0.0F, 0.0F, 0.2617993877991494F);
        this.GlowArmRight02 = new ModelRenderer(this, 0, 0);
        this.GlowArmRight02.setRotationPoint(-3.0F, 12.0F, 2.5F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        
        this.GlowBodyMain2.addChild(this.GlowArmLeft01);
        this.GlowArmLeft01.addChild(this.GlowArmLeft02);
        this.GlowArmLeft02.addChild(this.EquipSL01);
        this.EquipSL01.addChild(this.EquipSL02);
        this.EquipSL02.addChild(this.EquipSL03);
        this.EquipSL01.addChild(this.EquipSL04);
        this.EquipSL04.addChild(this.EquipSL05);
        
        this.GlowBodyMain2.addChild(this.GlowArmRight01);
        this.GlowArmRight01.addChild(this.GlowArmRight02);
        this.GlowArmRight02.addChild(this.EquipSR01);
        this.EquipSR01.addChild(this.EquipSR02);
        this.EquipSR02.addChild(this.EquipSR03);
        this.EquipSR01.addChild(this.EquipSR04);
        this.EquipSR04.addChild(this.EquipSR05);
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
    
	//model animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		IShipEmotion ent = (IShipEmotion)entity;

		showEquip(ent);
		
		EmotionHelper.rollEmotionAdv(this, ent);
		  
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
    
	//設定模型發光部份的rotation
    private void setGlowRotation()
    {
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowBodyMain2.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain2.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain2.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
		this.GlowArmLeft01.rotateAngleX = this.ArmLeft01.rotateAngleX;
		this.GlowArmLeft01.rotateAngleY = this.ArmLeft01.rotateAngleY;
		this.GlowArmLeft01.rotateAngleZ = this.ArmLeft01.rotateAngleZ;
		this.GlowArmLeft02.rotateAngleX = this.ArmLeft02.rotateAngleX;
		this.GlowArmLeft02.rotateAngleY = this.ArmLeft02.rotateAngleY;
		this.GlowArmLeft02.rotateAngleZ = this.ArmLeft02.rotateAngleZ;
		this.GlowArmRight01.rotateAngleX = this.ArmRight01.rotateAngleX;
		this.GlowArmRight01.rotateAngleY = this.ArmRight01.rotateAngleY;
		this.GlowArmRight01.rotateAngleZ = this.ArmRight01.rotateAngleZ;
		this.GlowArmRight02.rotateAngleX = this.ArmRight02.rotateAngleX;
		this.GlowArmRight02.rotateAngleY = this.ArmRight02.rotateAngleY;
		this.GlowArmRight02.rotateAngleZ = this.ArmRight02.rotateAngleZ;
    }
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
    {
    	GlStateManager.translate(0F, 0.49F, 0F);
  		setFace(4);
  		
  	    //head
  		this.Head.rotateAngleX = 0.65F;
	  	this.Head.rotateAngleY = 0F;
	  	this.Head.rotateAngleZ = 0F;
	  	//胸部
  	    this.BoobL.rotateAngleX = -0.75F;
  	    this.BoobR.rotateAngleX = -0.75F;
	  	//body
  	    this.Ahoke.rotateAngleY = 0.7F;
	  	this.BodyMain.rotateAngleX = -0.2F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = -0.14F;
	  	this.Skirt01.rotateAngleX = -0.1745F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt02.rotateAngleX = -0.2618F;
	  	this.Skirt02.offsetY = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = -0.1F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -0.2F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -0.14F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	this.Hair05.rotateAngleX = -0.4F;
	  	this.Hair05.rotateAngleZ = 0F;
	  	this.Hair06.rotateAngleX = 0.14F;
	  	this.Hair06.rotateAngleZ = 0F;
	  	this.Hair07.rotateAngleX = -0.2F;
	  	this.Hair07.rotateAngleZ = 0F;
	  	this.HairL01.rotateAngleX = -0.14F;
	  	this.HairL01.rotateAngleZ = 0F;
	  	this.HairL02.rotateAngleX = 0.17F;
	  	this.HairL02.rotateAngleZ = 0F;
	  	this.HairR01.rotateAngleX = -0.14F;
	  	this.HairR01.rotateAngleZ = 0F;
	  	this.HairR02.rotateAngleX = 0.17F;
	  	this.HairR02.rotateAngleZ = 0F;
	  	//arm
	  	this.ArmLeft01.rotateAngleX = 0.2F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = -0.2618F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.offsetY = 0F;
	    this.ArmLeft02.offsetZ = 0F;
	    this.ArmRight01.rotateAngleX = 0.2F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = 0.2618F;
		this.ArmRight02.rotateAngleY = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.offsetY = 0F;
	    this.ArmRight02.offsetZ = 0F;
		//leg
		this.LegLeft01.rotateAngleX = -0.9F;
		this.LegLeft01.rotateAngleZ = -0.14F;
		this.LegLeft02.rotateAngleX = 1.2217F;
		this.LegLeft02.rotateAngleY = 1.2217F;
		this.LegLeft02.rotateAngleZ = -1.0472F;
		this.LegLeft02.offsetX = 0.22F;
		this.LegLeft02.offsetY = -0.03F;
		this.LegLeft02.offsetZ = 0.2F;
		this.LegRight01.rotateAngleX = -0.9F;
		this.LegRight01.rotateAngleZ = 0.14F;
		this.LegRight02.rotateAngleX = 1.2217F;
		this.LegRight02.rotateAngleY = -1.2217F;
		this.LegRight02.rotateAngleZ = 1.0472F;
		this.LegRight02.offsetX = -0.22F;
		this.LegRight02.offsetY = -0.03F;
		this.LegRight02.offsetZ = 0.2F;
		//equip
		this.GlowBodyMain2.isHidden = true;
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
    }
    
	//雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
  	{   
  		float angleX = MathHelper.cos(f2*0.08F + f * 0.25F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.08F + 0.6F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		float headX = 0F;
  		float headZ = 0F;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D || ent.getShipDepth(1) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}

    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.35F;
	  	addk2 = angleAdd2 * 0.5F - 0.1745F;
    	
  	    //head
	  	this.Head.rotateAngleX = f4 * 0.014F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	  	//胸部
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.75F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.75F;
	  	//body
  	    this.Ahoke.rotateAngleY = angleX * 0.2F + 0.7F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.35F;
	  	this.Skirt01.rotateAngleX = -0.14F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt02.rotateAngleX = -0.087F;
	  	this.Skirt02.offsetY = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.21F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.087F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -angleX2 * 0.07F - 0.14F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	this.Hair05.rotateAngleX = angleX * 0.06F + 0.1F;
	  	this.Hair05.rotateAngleZ = 0F;
	  	this.Hair06.rotateAngleX = -angleX1 * 0.08F + 0.14F;
	  	this.Hair06.rotateAngleZ = 0F;
	  	this.Hair07.rotateAngleX = -angleX2 * 0.1F - 0.2F;
	  	this.Hair07.rotateAngleZ = 0F;
	  	this.HairL01.rotateAngleX = angleX * 0.04F - 0.14F;
	  	this.HairL01.rotateAngleZ = 0F;
	  	this.HairL02.rotateAngleX = -angleX1 * 0.06F + 0.17F;
	  	this.HairL02.rotateAngleZ = 0F;
	  	this.HairR01.rotateAngleX = angleX * 0.04F - 0.14F;
	  	this.HairR01.rotateAngleZ = 0F;
	  	this.HairR02.rotateAngleX = -angleX1 * 0.06F + 0.17F;
	  	this.HairR02.rotateAngleZ = 0F;
	  	//arm
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F + 0.35F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.26F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.offsetY = 0F;
	    this.ArmLeft02.offsetZ = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.25F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.26F;
		this.ArmRight02.rotateAngleY = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.offsetY = 0F;
	    this.ArmRight02.offsetZ = 0F;
		//leg
		this.LegLeft01.rotateAngleZ = 0.1F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleZ = -0.1F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
		this.EquipSL01.rotateAngleX = -1.57F;
		this.EquipSL01.rotateAngleY = 0F;
		this.EquipSL01.rotateAngleZ = 1.57F;
		this.EquipSL01.offsetX = 0F;
		this.EquipSL01.offsetY = 0F;
		this.EquipSL01.offsetZ = 0F;
		this.EquipSR01.rotateAngleX = -1.57F;
		this.EquipSR01.rotateAngleY = 0F;
		this.EquipSR01.rotateAngleZ = 1.57F;
		this.EquipSR01.offsetX = 0F;
		this.EquipSR01.offsetY = 0F;
		this.EquipSR01.offsetZ = 0F;
		
	  	//奔跑動作
	    if(ent.getIsSprinting() || f1 > 0.95F)
	    {
	    	GlStateManager.translate(0F, 0.05F, 0F);
	    	//body
	 	    this.Head.rotateAngleX -= 0.4F;
	 	    this.BodyMain.rotateAngleX = 0.7F;
	 	    this.Butt.rotateAngleX -= 0.7F;
	 	    this.Skirt01.rotateAngleX = -0.15F;
	 	  	this.Skirt02.rotateAngleX = -0.32F;
	 	  	//hair
	 	  	this.Hair01.rotateAngleX += 0.3F;
	 	  	//arm
	    	this.ArmLeft01.rotateAngleX = 0.4F;
	    	this.ArmLeft01.rotateAngleY = -0.5F;
	    	this.ArmLeft01.rotateAngleZ = -0.7F;
		    this.ArmRight01.rotateAngleX = 0.4F;
	    	this.ArmRight01.rotateAngleY = 0.5F;
	    	this.ArmRight01.rotateAngleZ = 0.7F;
  		}
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    //潛行, 蹲下動作
	    if(ent.getIsSneaking())
	    {
	    	GlStateManager.translate(0F, 0.05F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.4F;
		  	this.Skirt01.rotateAngleX = -0.12F;
		  	this.Skirt02.rotateAngleX = -0.16F;
		  	this.Skirt02.offsetY = -0.1F;
		  	//hair
		  	this.Hair02.rotateAngleX -= 0.3F;
		  	this.Hair03.rotateAngleX -= 0.3F;
		    //arm
		  	if (ent.getStateEmotion(ID.S.State2) > ID.State.NORMAL_2)
		  	{
		  		this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F - 0.1F;
		    	this.ArmLeft01.rotateAngleY = -0.7F;
		    	this.ArmLeft01.rotateAngleZ = -0.3F;
			    this.ArmRight01.rotateAngleX = angleAdd1 * 0.25F - 0.1F;
		    	this.ArmRight01.rotateAngleY = 0.7F;
		    	this.ArmRight01.rotateAngleZ = 0.3F;
		  	}
		  	else
		  	{
		  		this.ArmLeft01.rotateAngleX = -0.6F;
			    this.ArmLeft01.rotateAngleZ = 0.2618F;
			    this.ArmRight01.rotateAngleX = -0.6F;
			    this.ArmRight01.rotateAngleZ = -0.2618F;
		  	}
		    //leg
		    addk1 -= 0.4F;
		    addk2 -= 0.4F;
  		}//end if sneaking
	    
	    //騎乘動作
    	if (((Entity)ent).getRidingEntity() instanceof BasicEntityMount)
    	{
    		if (ent.getIsSitting())
			{
    			if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
    				GlStateManager.translate(0F, 0.65F, -0.27F);
			    	//Body
			    	this.Head.rotateAngleX = -1.2217F;
			    	this.Head.rotateAngleY = 0F;
			    	this.Head.rotateAngleZ = 0F;
				  	this.BodyMain.rotateAngleX = 1.2217F;
				  	//hair
				  	this.Hair02.rotateAngleX += 0.2F;
				  	this.Hair03.rotateAngleX += 0.2F;
				  	this.Hair05.rotateAngleX -= 0.6F;
				  	this.Hair06.rotateAngleX -= 0.5F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = -2F;
				  	this.ArmLeft01.rotateAngleY = -0.1F;
				  	this.ArmLeft01.rotateAngleZ = -0.1F;
				    this.ArmLeft02.rotateAngleX = -2.5F;
				    this.ArmLeft02.offsetY = 0.1F;
				    this.ArmLeft02.offsetZ = -0.3F;
					this.ArmRight01.rotateAngleX = -2F;
					this.ArmRight01.rotateAngleY = 0.1F;
					this.ArmRight01.rotateAngleZ = 0.1F;
					this.ArmRight02.rotateAngleX = -2.5F;
					this.ArmRight02.offsetY = 0.1F;
				    this.ArmRight02.offsetZ = -0.3F;
					//leg
					addk1 = 0F;
					addk2 = 0F;
					this.LegLeft02.rotateAngleX = angleX * 0.4F + 0.8F;
					this.LegRight02.rotateAngleX = -angleX * 0.4F + 0.8F;
					//equip
					this.GlowBodyMain2.isHidden = true;
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.51F, 0F);
			    	//head
			    	this.Head.rotateAngleY -= 0.4F;
			    	this.Head.rotateAngleZ += 0.2F;
			    	//body
			    	this.BodyMain.rotateAngleX = -0.25F;
			    	this.Butt.rotateAngleX = -0.2F;
					this.Skirt01.rotateAngleX = -0.13F;
					this.Skirt01.offsetY = -0.05F;
					this.Skirt02.rotateAngleX = -0.13F;
					this.Skirt02.offsetY = -0.05F;
					//arm
					this.ArmLeft01.rotateAngleX = 0.35F;
					this.ArmLeft01.rotateAngleZ = -0.2618F;
					this.ArmRight01.rotateAngleX = -0.4F;
					this.ArmRight01.rotateAngleZ = 0.4F;
					//leg
					addk1 = -0.9F;
					addk2 = -0.9F;
					this.LegLeft01.rotateAngleZ = -0.14F;
					this.LegLeft02.rotateAngleX = 1.2217F;
					this.LegLeft02.rotateAngleY = 1.2217F;
					this.LegLeft02.rotateAngleZ = -1.0472F;
					this.LegLeft02.offsetX = 0.22F;
					this.LegLeft02.offsetY = -0.03F;
					this.LegLeft02.offsetZ = 0.2F;
					this.LegRight01.rotateAngleZ = 0.14F;
					this.LegRight02.rotateAngleX = 1.2217F;
					this.LegRight02.rotateAngleY = -1.2217F;
					this.LegRight02.rotateAngleZ = 1.0472F;
					this.LegRight02.offsetX = -0.22F;
					this.LegRight02.offsetY = -0.03F;
					this.LegRight02.offsetZ = 0.2F;
					//equip
					this.EquipSL01.rotateAngleX -= 0.06F;
					this.EquipSL01.rotateAngleZ -= 1.2F;
					this.EquipSR01.rotateAngleX -= 1.2F;
		    	}
			}
    		else
    		{
    			GlStateManager.translate(0F, 0.56F, 0F);
		    	//body
		    	this.BodyMain.rotateAngleX = -0.45F;
		    	this.Butt.rotateAngleX = -0.2F;
				this.Skirt01.rotateAngleX = -0.13F;
				this.Skirt01.offsetY = -0.05F;
				this.Skirt02.rotateAngleX = -0.13F;
				this.Skirt02.offsetY = -0.05F;
				//arm
				this.ArmLeft01.rotateAngleX = 0.2F;
				this.ArmLeft01.rotateAngleZ = -1.1F;
				this.ArmRight01.rotateAngleX = 0.2F;
				this.ArmRight01.rotateAngleZ = 1.1F;
				//leg
				addk1 = -0.8F;
				addk2 = -1.2F;
				this.LegLeft01.rotateAngleZ = -0.14F;
				this.LegLeft02.rotateAngleX = 1.2217F;
				this.LegLeft02.rotateAngleY = 1.2217F;
				this.LegLeft02.rotateAngleZ = -1.0472F;
				this.LegLeft02.offsetX = 0.22F;
				this.LegLeft02.offsetY = -0.03F;
				this.LegLeft02.offsetZ = 0.2F;
				this.LegRight01.rotateAngleZ = 0.14F;
				this.LegRight02.rotateAngleX = 0.9F;
				//equip
				this.EquipSL01.rotateAngleX = -1.2F;
				this.EquipSL01.rotateAngleY = 0.1F;
				this.EquipSL01.rotateAngleZ = 1F;
				this.EquipSL01.offsetX = 0.24F;
				this.EquipSL01.offsetY = -0.5F;
				this.EquipSL01.offsetZ = 1F;
				this.EquipSR01.rotateAngleX = -1.2F;
				this.EquipSR01.rotateAngleY = -0.1F;
				this.EquipSR01.rotateAngleZ = -1F;
				this.EquipSR01.offsetX = -0.24F;
				this.EquipSR01.offsetY = -0.5F;
				this.EquipSR01.offsetZ = 1F;
    		}
    	}
    	else if (ent.getIsSitting() || ent.getIsRiding())
    	{
    		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
    			GlStateManager.translate(0F, 0.65F, 0F);
		    	//Body
		    	this.Head.rotateAngleX = -1.2217F;
		    	this.Head.rotateAngleY = 0F;
		    	this.Head.rotateAngleZ = 0F;
			  	this.BodyMain.rotateAngleX = 1.2217F;
			  	//hair
			  	this.Hair02.rotateAngleX += 0.2F;
			  	this.Hair03.rotateAngleX += 0.2F;
			  	this.Hair05.rotateAngleX -= 0.6F;
			  	this.Hair06.rotateAngleX -= 0.5F;
			    //arm 
			  	this.ArmLeft01.rotateAngleX = -2F;
			  	this.ArmLeft01.rotateAngleY = -0.1F;
			  	this.ArmLeft01.rotateAngleZ = -0.1F;
			    this.ArmLeft02.rotateAngleX = -2.5F;
			    this.ArmLeft02.offsetY = 0.1F;
			    this.ArmLeft02.offsetZ = -0.3F;
				this.ArmRight01.rotateAngleX = -2F;
				this.ArmRight01.rotateAngleY = 0.1F;
				this.ArmRight01.rotateAngleZ = 0.1F;
				this.ArmRight02.rotateAngleX = -2.5F;
				this.ArmRight02.offsetY = 0.1F;
			    this.ArmRight02.offsetZ = -0.3F;
				//leg
				addk1 = 0F;
				addk2 = 0F;
				this.LegLeft02.rotateAngleX = angleX * 0.4F + 0.8F;
				this.LegRight02.rotateAngleX = -angleX * 0.4F + 0.8F;
				//equip
				this.GlowBodyMain2.isHidden = true;
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, 0.51F, 0F);
		    	//head
		    	this.Head.rotateAngleY -= 0.4F;
		    	this.Head.rotateAngleZ += 0.2F;
		    	//body
		    	this.BodyMain.rotateAngleX = -0.25F;
		    	this.Butt.rotateAngleX = -0.2F;
				this.Skirt01.rotateAngleX = -0.13F;
				this.Skirt01.offsetY = -0.05F;
				this.Skirt02.rotateAngleX = -0.13F;
				this.Skirt02.offsetY = -0.05F;
				//arm
				this.ArmLeft01.rotateAngleX = 0.35F;
				this.ArmLeft01.rotateAngleZ = -0.2618F;
				this.ArmRight01.rotateAngleX = -0.4F;
				this.ArmRight01.rotateAngleZ = 0.4F;
				//leg
				addk1 = -0.9F;
				addk2 = -0.9F;
				this.LegLeft01.rotateAngleZ = -0.14F;
				this.LegLeft02.rotateAngleX = 1.2217F;
				this.LegLeft02.rotateAngleY = 1.2217F;
				this.LegLeft02.rotateAngleZ = -1.0472F;
				this.LegLeft02.offsetX = 0.22F;
				this.LegLeft02.offsetY = -0.03F;
				this.LegLeft02.offsetZ = 0.2F;
				this.LegRight01.rotateAngleZ = 0.14F;
				this.LegRight02.rotateAngleX = 1.2217F;
				this.LegRight02.rotateAngleY = -1.2217F;
				this.LegRight02.rotateAngleZ = 1.0472F;
				this.LegRight02.offsetX = -0.22F;
				this.LegRight02.offsetY = -0.03F;
				this.LegRight02.offsetZ = 0.2F;
				//equip
				this.EquipSL01.rotateAngleX -= 0.06F;
				this.EquipSL01.rotateAngleZ -= 1.2F;
				this.EquipSR01.rotateAngleX -= 1.2F;
	    	}
    	}
	    
	    if (!(((Entity)ent).getRidingEntity() instanceof BasicEntityMount))
    	{
	    	//攻擊動作
		    int atktime = ent.getAttackTick();
		    if (atktime > 41)
		    {
		    	setFaceAttack(ent);
		    	//swing arm
			    float ft = (50 - ent.getAttackTick()) + (f2 - (int)f2);
			    ft *= 0.125F;
		  		float fa = MathHelper.cos(ft * ft * (float)Math.PI);
		        float fb = MathHelper.cos(MathHelper.sqrt(ft) * (float)Math.PI);
		        this.ArmLeft01.rotateAngleX += -fb * 120.0F * Values.N.DIV_PI_180 - 1.5F;
		        this.ArmLeft01.rotateAngleY += fa * 20.0F * Values.N.DIV_PI_180;
		        this.ArmLeft01.rotateAngleZ += fb * 20.0F * Values.N.DIV_PI_180 + 0.26F;
		    }
		    if (atktime > 36 && atktime <  45)
		    {
		    	setFaceAttack(ent);
		    	//swing arm
			    float ft = (45 - ent.getAttackTick()) + (f2 - (int)f2);
			    ft *= 0.125F;
		  		float fa = MathHelper.cos(ft * ft * (float)Math.PI);
		        float fb = MathHelper.cos(MathHelper.sqrt(ft) * (float)Math.PI);
		        this.ArmRight01.rotateAngleX += -fb * 120.0F * Values.N.DIV_PI_180 - 1.5F;
		        this.ArmRight01.rotateAngleY += -fa * 20.0F * Values.N.DIV_PI_180;
		        this.ArmRight01.rotateAngleZ += -fb * 20.0F * Values.N.DIV_PI_180 - 0.26F;
		    }
		    
		    //swing arm
		  	float f6 = ent.getSwingTime(f2 - (int)f2);
		  	if (f6 != 0F)
		  	{
		  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
		        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
		        this.ArmRight01.rotateAngleX += -f8 * 95.0F * Values.N.DIV_PI_180;
		        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
		        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
		  	}
    	}
	  	
	  	//鬢毛調整
	    headX = this.Head.rotateAngleX * -0.5F;
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleX += headX;
	    this.Hair01.rotateAngleZ += headZ;
	    this.Hair02.rotateAngleX += headX * 0.5F;
	    this.Hair02.rotateAngleZ += headZ * 0.5F;
	    this.Hair03.rotateAngleX += headX * 0.5F;
	    this.Hair03.rotateAngleZ += headZ * 0.5F;
	    this.Hair05.rotateAngleX += headX;
	    this.Hair05.rotateAngleZ += headZ;
	    this.Hair06.rotateAngleX += headX;
	    this.Hair06.rotateAngleZ += headZ;
	  	this.HairL01.rotateAngleZ += headZ;
	  	this.HairL02.rotateAngleZ += headZ;
	  	this.HairR01.rotateAngleZ += headZ;
	  	this.HairR02.rotateAngleZ += headZ;
		this.HairL01.rotateAngleX += headX;
	  	this.HairL02.rotateAngleX += headX;
	  	this.HairR01.rotateAngleX += headX;
	  	this.HairR02.rotateAngleX += headX;
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
  	
  	private void showEquip(IShipEmotion ent)
  	{
  		switch (ent.getStateEmotion(ID.S.State2))
  		{
  		case ID.State.EQUIP00_2:
  			this.GlowBodyMain2.isHidden = false;
  		break;
  		default:  //normal
  			this.GlowBodyMain2.isHidden = true;
  		break;
  		}
  	}
  	
    //設定顯示的臉型
  	@Override
  	public void setFace(int emo)
  	{
  		switch (emo)
  		{
  		case 0:
  			this.Face0.isHidden = false;
  			this.Face0.rotateAngleY = 0F;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  		break;
  		case 1:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = false;
  			this.Face1.rotateAngleY = 0F;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  		break;
  		case 2:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face2.rotateAngleY = 0F;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  		break;
  		case 3:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face3.rotateAngleY = 0F;
  			this.Face4.isHidden = true;
  		break;
  		case 4:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = false;
  			this.Face4.rotateAngleY = 0F;
  		break;
  		case 5:
  			this.Face0.isHidden = false;
  			this.Face0.rotateAngleY = 3.14159F;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  		break;
  		case 6:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = false;
  			this.Face1.rotateAngleY = 3.14159F;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  		break;
  		case 7:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face2.rotateAngleY = 3.14159F;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  		break;
  		case 8:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face3.rotateAngleY = 3.14159F;
  			this.Face4.isHidden = true;
  		break;
  		case 9:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = false;
  			this.Face4.rotateAngleY = 3.14159F;
  		break;
  		default:
  		break;
  		}
  	}

	@Override
	public void setFaceNormal(IShipEmotion ent)
	{
		setFace(0);
	}

	@Override
	public void setFaceBlink0(IShipEmotion ent)
	{
		setFace(0);		
	}

	@Override
	public void setFaceBlink1(IShipEmotion ent)
	{
		setFace(1);
	}

	@Override
	public void setFaceCry(IShipEmotion ent)
	{
		if (ent.getTickExisted() % 256 > 128)
		{
			setFace(6);
		}
		else
		{
			setFace(7);
		}
	}

	@Override
	public void setFaceAttack(IShipEmotion ent)
	{
		int t = ent.getTickExisted() % 512;
		
		if (t < 160)
		{
			setFace(2);
		}
		else if (t < 320)
		{
			setFace(5);
		}
		else
		{
			setFace(3);
		}
	}

	@Override
	public void setFaceDamaged(IShipEmotion ent)
	{
		int t = ent.getTickExisted() % 128;
		
		if (t < 64)
		{
			setFace(6);
		}
		else
		{
			setFace(3);
		}
	}

	@Override
	public void setFaceHungry(IShipEmotion ent)
	{
		setFace(4);		
	}

	@Override
	public void setFaceAngry(IShipEmotion ent)
	{
		if (ent.getTickExisted() % 64 > 32)
		{
			setFace(1);
		}
		else
		{
			setFace(2);
		}
	}

	@Override
	public void setFaceScorn(IShipEmotion ent)
	{
		setFace(2);		
	}
	
	@Override
	public void setFaceBored(IShipEmotion ent)
	{
		int t = ent.getTickExisted() % 512;
		
		if (t < 256)
		{
			setFace(1);
		}
		else
		{
			setFace(3);
		}
	}
	
	@Override
	public void setFaceShy(IShipEmotion ent)
	{
		
	}
	
	@Override
	public void setFaceHappy(IShipEmotion ent)
	{
		
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