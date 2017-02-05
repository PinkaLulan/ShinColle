package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

/**
 * ModelNorthernHime - PinkaLulan 2015/6/13
 * Created using Tabula 4.1.1
 */
public class ModelNorthernHime extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
    public ModelRenderer EquipBase;
    public ModelRenderer Cloth01;
    public ModelRenderer Neck;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft03;
    public ModelRenderer ArmLeft04;
    public ModelRenderer ArmLeft05;
    public ModelRenderer ArmLeft06;
    public ModelRenderer EquipUmbre01a;
    public ModelRenderer EquipUmbre01b;
    public ModelRenderer EquipUmbre02;
    public ModelRenderer EquipUmbre01c;
    public ModelRenderer EquipUmbre02a;
    public ModelRenderer EquipUmbre03a;
    public ModelRenderer EquipUmbre02b;
    public ModelRenderer EquipUmbre03b;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight03;
    public ModelRenderer ArmRight04;
    public ModelRenderer ArmRight05;
    public ModelRenderer ArmRight06;
    public ModelRenderer LegRight01;
    public ModelRenderer ArmRightItem;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer ShoesR;
    public ModelRenderer LegLeft02;
    public ModelRenderer ShoesL2;
    public ModelRenderer ShoesL;
    public ModelRenderer EquipRT01;
    public ModelRenderer EquipLT01;
    public ModelRenderer EquipRT02;
    public ModelRenderer HeadBase;
    public ModelRenderer TailJaw1;
    public ModelRenderer TailHead1;
    public ModelRenderer TailHeadCL1;
    public ModelRenderer TailHeadCR1;
    public ModelRenderer EquipRoad01;
    public ModelRenderer TailJawT01;
    public ModelRenderer TailHead2;
    public ModelRenderer TailHeadT01;
    public ModelRenderer TailHeadC2;
    public ModelRenderer TailHeadC3;
    public ModelRenderer EquipRoad02;
    public ModelRenderer EquipRoad03;
    public ModelRenderer EquipLT02;
    public ModelRenderer EquipLT03;
    public ModelRenderer EquipLT04;
    public ModelRenderer EquipLT05;
    public ModelRenderer EquipLT06;
    public ModelRenderer EquipLHead;
    public ModelRenderer EquipLHead01;
    public ModelRenderer EquipLHead02;
    public ModelRenderer EquipLHead03;
    public ModelRenderer Cloth02;
    public ModelRenderer Cloth03;
    public ModelRenderer SantaCloth01;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer HeadHL;
    public ModelRenderer HeadHR;
    public ModelRenderer SantaHat01;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer HeadHL2;
    public ModelRenderer HeadHL3;
    public ModelRenderer HeadHR2;
    public ModelRenderer HeadHR3;
    public ModelRenderer SantaHat02;
    public ModelRenderer SantaHat03;
    public ModelRenderer SantaHat04;
    public ModelRenderer SantaHat05;
    public ModelRenderer HairS01a;
    public ModelRenderer HairS01b;
    public ModelRenderer HairS02a;
    public ModelRenderer HairS02b;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowEquipBase;
    public ModelRenderer GlowEquipRT01;
    public ModelRenderer GlowEquipRT02;
    public ModelRenderer GlowHeadBase;
    public ModelRenderer GlowTailHead1;
    public ModelRenderer GlowTailJaw1;
    public ModelRenderer GlowTailHead2;
    public ModelRenderer GlowEquipLT01;
    public ModelRenderer GlowEquipLT02;
    public ModelRenderer GlowEquipLT03;
    public ModelRenderer GlowEquipLT04;
    public ModelRenderer GlowEquipLT05;
    public ModelRenderer GlowEquipLT06;
    
    protected float[] offsetItem2 = new float[] {0.05F, 1F, -0.14F};
    protected float[] offsetBlock2 = new float[] {0.1F, 1.13F, 0.1F};
    protected float[] rotateItem2 = new float[] {-30F, 30F, -60F};
    

    public ModelNorthernHime()
    {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.scale = 0.34F;
        this.offsetY = 3.08F;
        this.offsetItem = new float[] {0.07F, 1.02F, -0.05F};
        this.offsetBlock = new float[] {0.1F, 1.03F, 0F};
        
        this.setDefaultFaceModel();
        
        this.Cloth01 = new ModelRenderer(this, 128, 75);
        this.Cloth01.setRotationPoint(0.0F, -5.0F, -4.4F);
        this.Cloth01.addBox(-7.0F, 0.0F, 0.0F, 14, 4, 8, 0.0F);
        this.EquipUmbre03b = new ModelRenderer(this, 54, 0);
        this.EquipUmbre03b.setRotationPoint(1.5F, 2.0F, 2.9F);
        this.EquipUmbre03b.addBox(-2.0F, -6.0F, 0.0F, 5, 12, 11, 0.0F);
        this.setRotateAngle(EquipUmbre03b, -0.091106186954104F, 0.6829473363053812F, 0.136659280431156F);
        this.ShoesR = new ModelRenderer(this, 80, 45);
        this.ShoesR.setRotationPoint(0.0F, 4.0F, 2.5F);
        this.ShoesR.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6, 0.0F);
        this.EquipLT06 = new ModelRenderer(this, 0, 45);
        this.EquipLT06.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.EquipLT06.addBox(0.0F, -2.5F, -2.5F, 6, 5, 5, 0.0F);
        this.setRotateAngle(EquipLT06, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        this.HairMain = new ModelRenderer(this, 48, 55);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 12, 10, 0.0F);
        this.ShoesL = new ModelRenderer(this, 80, 45);
        this.ShoesL.mirror = true;
        this.ShoesL.setRotationPoint(0.0F, 4.0F, 2.5F);
        this.ShoesL.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6, 0.0F);
        this.EquipLT02 = new ModelRenderer(this, 0, 45);
        this.EquipLT02.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.EquipLT02.addBox(0.0F, -2.5F, -2.5F, 6, 5, 5, 0.0F);
        this.setRotateAngle(EquipLT02, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        this.TailJawT01 = new ModelRenderer(this, 0, 56);
        this.TailJawT01.setRotationPoint(0.0F, -3.0F, 4.0F);
        this.TailJawT01.addBox(-5.5F, 0.0F, 0.0F, 11, 5, 9, 0.0F);
        this.setRotateAngle(TailJawT01, 0.17453292519943295F, 0.0F, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 99);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0.0F, 8F, -2.5F);
        this.LegLeft02.addBox(-2.5F, 0.0F, 0.0F, 5, 9, 5, 0.0F);
        this.EquipLT05 = new ModelRenderer(this, 0, 45);
        this.EquipLT05.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.EquipLT05.addBox(0.0F, -2.5F, -2.5F, 6, 5, 5, 0.0F);
        this.setRotateAngle(EquipLT05, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        this.SantaCloth01 = new ModelRenderer(this, 128, 114);
        this.SantaCloth01.setRotationPoint(0.0F, 3.0F, -0.3F);
        this.SantaCloth01.addBox(-8.5F, 0.0F, 0.0F, 17, 2, 11, 0.0F);
        this.ArmLeft05 = new ModelRenderer(this, 20, 100);
        this.ArmLeft05.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.ArmLeft05.addBox(-3.0F, 0.0F, -3.5F, 6, 7, 7, 0.0F);
        this.ArmRight06 = new ModelRenderer(this, 20, 100);
        this.ArmRight06.setRotationPoint(2.0F, 1.0F, -1.5F);
        this.ArmRight06.addBox(-1.5F, 0.0F, -3.0F, 3, 4, 3, 0.0F);
        this.setRotateAngle(ArmRight06, -0.08726646259971647F, -0.08726646259971647F, -0.17453292519943295F);
        this.EquipLT01 = new ModelRenderer(this, 0, 45);
        this.EquipLT01.setRotationPoint(2.0F, 4.0F, 2.5F);
        this.EquipLT01.addBox(0.0F, -2.5F, -2.5F, 6, 5, 5, 0.0F);
        this.setRotateAngle(EquipLT01, 0.0F, -1.0471975511965976F, -0.2617993877991494F);
        this.Hair01 = new ModelRenderer(this, 1, 70);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 2.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 12, 8, 0.0F);
        this.setRotateAngle(Hair01, 0.3490658503988659F, 0.0F, 0.0F);
        this.HairS01a = new ModelRenderer(this, 38, 19);
        this.HairS01a.setRotationPoint(7.5F, -1F, 3.5F);
        this.HairS01a.addBox(0F, 0F, -2F, 0, 7, 4, 0F);
        this.setRotateAngle(HairS01a, 0.087F, 0F, -0.2618F);
        this.HairS01b = new ModelRenderer(this, 46, 26);
        this.HairS01b.setRotationPoint(0F, 7F, 0F);
        this.HairS01b.addBox(0F, 0F, -2F, 0, 7, 4, 0F);
        this.setRotateAngle(HairS01b, 0F, 0F, -0.2618F);
        this.HairS02a = new ModelRenderer(this, 38, 19);
        this.HairS02a.setRotationPoint(-7.5F, 3F, 2.5F);
        this.HairS02a.addBox(0F, 0F, -2F, 0, 7, 4, 0F);
        this.setRotateAngle(HairS02a, 0.087F, 0F, 0.35F);
        this.HairS02b = new ModelRenderer(this, 38, 25);
        this.HairS02b.setRotationPoint(0F, 7F, 0F);
        this.HairS02b.addBox(0F, 0F, -2F, 0, 7, 4, 0F);
        this.setRotateAngle(HairS02b, 0F, 0F, 0.35F);
        this.EquipUmbre03a = new ModelRenderer(this, 0, 0);
        this.EquipUmbre03a.setRotationPoint(0.0F, 0.0F, -14.4F);
        this.EquipUmbre03a.addBox(0.0F, -7.0F, 0.0F, 13, 17, 3, 0.0F);
        this.setRotateAngle(EquipUmbre03a, 0.0F, -0.2617993877991494F, 0.36425021489121656F);
        this.EquipRoad03 = new ModelRenderer(this, 46, 41);
        this.EquipRoad03.setRotationPoint(0.0F, 0.0F, 12.0F);
        this.EquipRoad03.addBox(0.0F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.ArmLeft04 = new ModelRenderer(this, 72, 43);
        this.ArmLeft04.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.ArmLeft04.addBox(-4.0F, 0.0F, -4.0F, 8, 2, 8, 0.0F);
        this.EquipLHead03 = new ModelRenderer(this, 24, 48);
        this.EquipLHead03.setRotationPoint(-11.5F, 0.0F, 0.0F);
        this.EquipLHead03.addBox(-5.0F, -1.5F, -1.0F, 6, 3, 2, 0.0F);
        this.setRotateAngle(EquipLHead03, 0.0F, 0.31869712141416456F, 0.0F);
        this.SantaHat02 = new ModelRenderer(this, 58, 24);
        this.SantaHat02.setRotationPoint(0.0F, 3.0F, -0.5F);
        this.SantaHat02.addBox(-4.5F, -8.0F, -4.5F, 9, 7, 8, 0.0F);
        this.setRotateAngle(SantaHat02, -0.5235987755982988F, 0.17453292519943295F, 0.0F);
        this.Neck = new ModelRenderer(this, 129, 58);
        this.Neck.setRotationPoint(0.0F, -11.3F, -0.5F);
        this.Neck.addBox(-7.0F, -2.0F, -6.0F, 14, 3, 12, 0.0F);
        this.setRotateAngle(Neck, 0.05235987755982988F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 2, 100);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(1.0F, 4.0F, 2.0F);
        this.ArmLeft02.addBox(-2.0F, 0.0F, -4.0F, 4, 4, 4, 0.0F);
        this.EquipLT03 = new ModelRenderer(this, 0, 45);
        this.EquipLT03.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.EquipLT03.addBox(0.0F, -2.5F, -2.5F, 6, 5, 5, 0.0F);
        this.setRotateAngle(EquipLT03, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        this.HeadHL3 = new ModelRenderer(this, 30, 90);
        this.HeadHL3.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.HeadHL3.addBox(0.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.HairR02 = new ModelRenderer(this, 86, 102);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 7.5F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairR02, 0.2617993877991494F, 0.0F, -0.05235987755982988F);
        this.SantaHat04 = new ModelRenderer(this, 67, 28);
        this.SantaHat04.setRotationPoint(0.5F, -4.5F, 0.0F);
        this.SantaHat04.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 4, 0.0F);
        this.setRotateAngle(SantaHat04, -1.1383037381507017F, -0.27314402793711257F, 0.0F);
        this.EquipUmbre01b = new ModelRenderer(this, 0, 0);
        this.EquipUmbre01b.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.EquipUmbre01b.addBox(-1.0F, -1.0F, -12.0F, 2, 2, 12, 0.0F);
        this.SantaHat03 = new ModelRenderer(this, 65, 27);
        this.SantaHat03.setRotationPoint(0.0F, -5.0F, -1.0F);
        this.SantaHat03.addBox(-2.5F, -6.0F, -2.5F, 6, 6, 6, 0.0F);
        this.setRotateAngle(SantaHat03, -0.27314402793711257F, 0.0F, -0.5009094953223726F);
        this.EquipUmbre01c = new ModelRenderer(this, 0, 0);
        this.EquipUmbre01c.setRotationPoint(0.0F, 0.0F, -12.0F);
        this.EquipUmbre01c.addBox(-1.0F, -1.0F, -12.0F, 2, 2, 12, 0.0F);
        this.EquipUmbre02a = new ModelRenderer(this, 0, 0);
        this.EquipUmbre02a.setRotationPoint(-3.0F, 0.0F, -12.0F);
        this.EquipUmbre02a.addBox(-16.0F, -9.0F, -2.0F, 20, 18, 3, 0.0F);
        this.setRotateAngle(EquipUmbre02a, 0.0F, 0.17453292519943295F, 0.5235987755982988F);
        this.TailHead1 = new ModelRenderer(this, 0, 0);
        this.TailHead1.setRotationPoint(0.0F, -9.5F, 4.0F);
        this.TailHead1.addBox(-7.0F, -0.2F, -5.6F, 14, 8, 10, 0.0F);
        this.setRotateAngle(TailHead1, 0.17453292519943295F, 0.0F, 0.0F);
        this.HeadHR = new ModelRenderer(this, 30, 90);
        this.HeadHR.mirror = true;
        this.HeadHR.setRotationPoint(-5.9F, -10.8F, 1.0F);
        this.HeadHR.addBox(-3.0F, -2.5F, -2.5F, 3, 5, 5, 0.0F);
        this.setRotateAngle(HeadHR, -0.7853981633974483F, 0.17453292519943295F, 0.3141592653589793F);
        this.TailHeadC2 = new ModelRenderer(this, 0, 13);
        this.TailHeadC2.setRotationPoint(3.2F, 3.2F, 10.5F);
        this.TailHeadC2.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 10, 0.0F);
        this.setRotateAngle(TailHeadC2, 0.08726646259971647F, 0.08726646259971647F, 0.017627825445142728F);
        this.HeadHL = new ModelRenderer(this, 30, 90);
        this.HeadHL.mirror = true;
        this.HeadHL.setRotationPoint(5.9F, -10.9F, 1.0F);
        this.HeadHL.addBox(0.0F, -2.5F, -2.5F, 3, 5, 5, 0.0F);
        this.setRotateAngle(HeadHL, -0.7853981633974483F, -0.17453292519943295F, -0.3141592653589793F);
        this.ArmLeft06 = new ModelRenderer(this, 20, 100);
        this.ArmLeft06.setRotationPoint(-2.0F, 1.0F, -1.5F);
        this.ArmLeft06.addBox(-1.5F, 0.0F, -3.0F, 3, 4, 3, 0.0F);
        this.setRotateAngle(ArmLeft06, -0.08726646259971647F, 0.08726646259971647F, 0.17453292519943295F);
        this.LegRight01 = new ModelRenderer(this, 0, 99);
        this.LegRight01.setRotationPoint(-3.2F, 5.5F, 2.4F);
        this.LegRight01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(LegRight01, -0.17453292519943295F, 0.0F, 0.05235987755982988F);
        this.HeadBase = new ModelRenderer(this, 0, 0);
        this.HeadBase.setRotationPoint(-14.0F, -3.0F, 0.0F);
        this.HeadBase.addBox(-6.0F, -8.0F, 2.0F, 12, 15, 8, 0.0F);
        this.setRotateAngle(HeadBase, -0.4363323129985824F, -2.792526803190927F, -0.13962634015954636F);
        this.Hair02 = new ModelRenderer(this, 0, 70);
        this.Hair02.setRotationPoint(0.0F, 9.5F, 7.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -8.0F, 16, 12, 8, 0.0F);
        this.setRotateAngle(Hair02, 0.136659280431156F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 92, 28);
        this.Butt.setRotationPoint(0.0F, -2.0F, -4.0F);
        this.Butt.addBox(-5.5F, 0.0F, 0.0F, 11, 6, 7, 0.0F);
        this.setRotateAngle(Butt, 0.2617993877991494F, 0.0F, 0.0F);
        this.TailHeadCR1 = new ModelRenderer(this, 0, 0);
        this.TailHeadCR1.mirror = true;
        this.TailHeadCR1.setRotationPoint(-6.0F, -5.0F, 12.0F);
        this.TailHeadCR1.addBox(-1.0F, 0.0F, -3.0F, 2, 11, 6, 0.0F);
        this.setRotateAngle(TailHeadCR1, 0.0F, -0.05235987755982988F, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Cloth02 = new ModelRenderer(this, 128, 87);
        this.Cloth02.setRotationPoint(0.0F, 3.0F, -0.3F);
        this.Cloth02.addBox(-7.5F, 0.0F, 0.0F, 15, 4, 9, 0.0F);
        this.setRotateAngle(Cloth02, 0.13962634015954636F, 0.0F, 0.0F);
        this.EquipRoad01 = new ModelRenderer(this, 46, 41);
        this.EquipRoad01.setRotationPoint(6.0F, -11.5F, -3.0F);
        this.EquipRoad01.addBox(0.0F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.setRotateAngle(EquipRoad01, -0.20943951023931953F, 0.08726646259971647F, 0.0F);
        this.TailHeadC3 = new ModelRenderer(this, 0, 13);
        this.TailHeadC3.setRotationPoint(-3.2F, 3.2F, 10.5F);
        this.TailHeadC3.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 10, 0.0F);
        this.setRotateAngle(TailHeadC3, 0.08726646259971647F, -0.08726646259971647F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.HairR01 = new ModelRenderer(this, 86, 102);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-6.5F, 3.0F, -4.5F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairR01, -0.2617993877991494F, 0.17453292519943295F, 0.13962634015954636F);
        this.EquipUmbre02b = new ModelRenderer(this, 54, 0);
        this.EquipUmbre02b.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.EquipUmbre02b.addBox(-11.0F, -8.0F, 0.0F, 13, 16, 5, 0.0F);
        this.setRotateAngle(EquipUmbre02b, -0.05235987755982988F, -0.08726646259971647F, 0.0F);
        this.TailHead2 = new ModelRenderer(this, 0, 0);
        this.TailHead2.setRotationPoint(0.0F, -1.0F, 4.5F);
        this.TailHead2.addBox(-7.0F, 0.0F, 0.0F, 14, 8, 11, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 16, 8, 0.0F);
        this.TailJaw1 = new ModelRenderer(this, 0, 0);
        this.TailJaw1.setRotationPoint(0.0F, 3.0F, 5.0F);
        this.TailJaw1.addBox(-6.5F, 0.0F, 0.0F, 13, 5, 14, 0.0F);
        this.setRotateAngle(TailJaw1, -0.27314402793711257F, 0.0F, 0.0F);
        this.TailHeadT01 = new ModelRenderer(this, 0, 55);
        this.TailHeadT01.setRotationPoint(0.0F, 4.5F, 4.5F);
        this.TailHeadT01.addBox(-6.0F, 0.0F, 0.0F, 12, 5, 10, 0.0F);
        this.setRotateAngle(TailHeadT01, -0.17453292519943295F, 0.0F, 0.0F);
        this.TailHeadCL1 = new ModelRenderer(this, 0, 0);
        this.TailHeadCL1.setRotationPoint(6.0F, -5.0F, 12.0F);
        this.TailHeadCL1.addBox(-1.0F, 0.0F, -3.0F, 2, 11, 6, 0.0F);
        this.setRotateAngle(TailHeadCL1, 0.0F, 0.05235987755982988F, 0.0F);
        this.ShoesL2 = new ModelRenderer(this, 80, 45);
        this.ShoesL2.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.ShoesL2.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6, 0.0F);
        this.ArmRight04 = new ModelRenderer(this, 72, 43);
        this.ArmRight04.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.ArmRight04.addBox(-4.0F, 0.0F, -4.0F, 8, 2, 8, 0.0F);
        this.SantaHat05 = new ModelRenderer(this, 0, 0);
        this.SantaHat05.setRotationPoint(2.0F, -5.8F, 2.0F);
        this.SantaHat05.addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(SantaHat05, 0.6108652381980153F, 0.6981317007977318F, -0.5235987755982988F);
        this.EquipRT01 = new ModelRenderer(this, 0, 0);
        this.EquipRT01.setRotationPoint(0.0F, 4.0F, 4.0F);
        this.EquipRT01.addBox(-16.0F, -2.0F, -2.0F, 16, 4, 4, 0.0F);
        this.setRotateAngle(EquipRT01, 0.0F, 0.7853981633974483F, 0.3490658503988659F);
        this.EquipLHead = new ModelRenderer(this, 0, 29);
        this.EquipLHead.setRotationPoint(5.0F, 0.0F, -1.0F);
        this.EquipLHead.addBox(0.0F, -3.5F, -5.0F, 10, 7, 9, 0.0F);
        this.setRotateAngle(EquipLHead, 0.0F, -0.6981317007977318F, -0.17453292519943295F);
        this.EquipLHead02 = new ModelRenderer(this, 0, 0);
        this.EquipLHead02.setRotationPoint(-11.5F, 0.0F, 0.0F);
        this.EquipLHead02.addBox(-12.0F, -1.0F, 0.0F, 12, 2, 0, 0.0F);
        this.setRotateAngle(EquipLHead02, 0.0F, 0.5235987755982988F, -0.2617993877991494F);
        this.EquipUmbre02 = new ModelRenderer(this, 38, 57);
        this.EquipUmbre02.setRotationPoint(0.0F, 0.0F, 6.0F);
        this.EquipUmbre02.addBox(-2.5F, -1.0F, 0.0F, 5, 2, 5, 0.0F);
        this.Cloth03 = new ModelRenderer(this, 128, 100);
        this.Cloth03.setRotationPoint(0.0F, 3.0F, -0.2F);
        this.Cloth03.addBox(-8.0F, 0.0F, 0.0F, 16, 4, 10, 0.0F);
        this.setRotateAngle(Cloth03, 0.13962634015954636F, 0.0F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 114);
        this.BodyMain.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 7, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.08726646259971647F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0F, -5.0F, -5.0F);
        this.Ahoke.addBox(0.0F, -12.0F, -6.0F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.35F, 2.1F, 0.0F);
        this.HeadHR2 = new ModelRenderer(this, 30, 90);
        this.HeadHR2.setRotationPoint(-3.0F, 0.0F, 0.0F);
        this.HeadHR2.addBox(-1.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
        this.ArmRight05 = new ModelRenderer(this, 20, 100);
        this.ArmRight05.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.ArmRight05.addBox(-3.0F, 0.0F, -3.5F, 6, 7, 7, 0.0F);
        this.EquipLT04 = new ModelRenderer(this, 0, 45);
        this.EquipLT04.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.EquipLT04.addBox(0.0F, -2.5F, -2.5F, 6, 5, 5, 0.0F);
        this.setRotateAngle(EquipLT04, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        this.ArmRight02 = new ModelRenderer(this, 2, 100);
        this.ArmRight02.setRotationPoint(-1.0F, 4.0F, 2.0F);
        this.ArmRight02.addBox(-2.0F, 0.0F, -4.0F, 4, 4, 4, 0.0F);
        this.EquipUmbre01a = new ModelRenderer(this, 0, 0);
        this.EquipUmbre01a.setRotationPoint(-1.0F, 4.0F, -1.0F);
        this.EquipUmbre01a.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 12, 0.0F);
        this.HairL02 = new ModelRenderer(this, 86, 102);
        this.HairL02.setRotationPoint(-0.2F, 7.5F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairL02, 0.2617993877991494F, 0.0F, 0.08726646259971647F);
        this.HairL01 = new ModelRenderer(this, 86, 102);
        this.HairL01.setRotationPoint(6.5F, 3.0F, -4.5F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairL01, -0.2617993877991494F, -0.17453292519943295F, -0.13962634015954636F);
        this.EquipLHead01 = new ModelRenderer(this, 0, 0);
        this.EquipLHead01.setRotationPoint(4.0F, 0.0F, -4.0F);
        this.EquipLHead01.addBox(-12.0F, -1.0F, 0.0F, 12, 2, 0, 0.0F);
        this.setRotateAngle(EquipLHead01, 0.0F, -0.5235987755982988F, -0.3490658503988659F);
        this.LegLeft01 = new ModelRenderer(this, 0, 99);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(3.2F, 5.5F, 2.4F);
        this.LegLeft01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(LegLeft01, -0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.HeadHR3 = new ModelRenderer(this, 30, 90);
        this.HeadHR3.setRotationPoint(-1.0F, 0.0F, 0.0F);
        this.HeadHR3.addBox(-1.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.EquipRoad02 = new ModelRenderer(this, 46, 41);
        this.EquipRoad02.setRotationPoint(0.0F, 0.0F, 12.0F);
        this.EquipRoad02.addBox(0.0F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.HeadHL2 = new ModelRenderer(this, 30, 90);
        this.HeadHL2.setRotationPoint(3.0F, 0.0F, 0.0F);
        this.HeadHL2.addBox(0.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
        this.EquipRT02 = new ModelRenderer(this, 0, 0);
        this.EquipRT02.setRotationPoint(-16.0F, 0.0F, 2.0F);
        this.EquipRT02.addBox(-16.0F, -2.0F, -4.0F, 16, 4, 4, 0.0F);
        this.setRotateAngle(EquipRT02, 0.0F, -1.0471975511965976F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 99);
        this.LegRight02.setRotationPoint(0.0F, 8F, -2.5F);
        this.LegRight02.addBox(-2.5F, 0.0F, 0.0F, 5, 9, 5, 0.0F);
        this.SantaHat01 = new ModelRenderer(this, 0, 0);
        this.SantaHat01.setRotationPoint(4.0F, -16.5F, 3.0F);
        this.SantaHat01.addBox(-6.5F, 0.0F, -6.5F, 13, 3, 13, 0.0F);
        this.setRotateAngle(SantaHat01, -0.4363323129985824F, 0.8726646259971648F, -0.13962634015954636F);
        this.ArmRight01 = new ModelRenderer(this, 2, 100);
        this.ArmRight01.setRotationPoint(-6.0F, -9.8F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(ArmRight01, 0.2617993877991494F, 0.0F, 0.5235987755982988F);
        this.ArmLeft03 = new ModelRenderer(this, 0, 90);
        this.ArmLeft03.setRotationPoint(0.0F, 1.0F, -2.0F);
        this.ArmLeft03.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 2, 100);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(6.0F, -9.8F, -0.7F);
        this.ArmLeft01.addBox(-1.0F, -1.0F, -2.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(ArmLeft01, -0.27314402793711257F, 0.0F, -0.5235987755982988F);
        this.ArmRight03 = new ModelRenderer(this, 0, 90);
        this.ArmRight03.setRotationPoint(0.0F, 1.0F, -2.0F);
        this.ArmRight03.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
        this.ArmRightItem = new ModelRenderer(this, 0, 0);
        this.ArmRightItem.setRotationPoint(0F, 0F, 0F);
        this.BodyMain.addChild(this.Cloth01);
        this.ArmRight05.addChild(this.ArmRightItem);
        this.EquipUmbre03a.addChild(this.EquipUmbre03b);
        this.LegRight02.addChild(this.ShoesR);
        this.EquipLT05.addChild(this.EquipLT06);
        this.Head.addChild(this.HairMain);
        this.LegLeft02.addChild(this.ShoesL);
        this.EquipLT01.addChild(this.EquipLT02);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipLT04.addChild(this.EquipLT05);
        this.Cloth03.addChild(this.SantaCloth01);
        this.ArmLeft04.addChild(this.ArmLeft05);
        this.ArmRight05.addChild(this.ArmRight06);
        this.EquipBase.addChild(this.EquipLT01);
        this.HairMain.addChild(this.Hair01);
        this.EquipUmbre01c.addChild(this.EquipUmbre03a);
        this.ArmLeft03.addChild(this.ArmLeft04);
        this.EquipLHead02.addChild(this.EquipLHead03);
        this.SantaHat01.addChild(this.SantaHat02);
        this.BodyMain.addChild(this.Neck);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipLT02.addChild(this.EquipLT03);
        this.HeadHL2.addChild(this.HeadHL3);
        this.HairR01.addChild(this.HairR02);
        this.SantaHat03.addChild(this.SantaHat04);
        this.EquipUmbre01a.addChild(this.EquipUmbre01b);
        this.SantaHat02.addChild(this.SantaHat03);
        this.EquipUmbre01b.addChild(this.EquipUmbre01c);
        this.EquipUmbre01c.addChild(this.EquipUmbre02a);
        this.HeadBase.addChild(this.TailHead1);
        this.ArmLeft05.addChild(this.ArmLeft06);
        this.Butt.addChild(this.LegRight01);
        this.EquipRT02.addChild(this.HeadBase);
        this.Hair01.addChild(this.Hair02);
        this.Hair01.addChild(this.HairS01a);
        this.Hair01.addChild(this.HairS02a);
        this.HairS01a.addChild(this.HairS01b);
        this.HairS02a.addChild(this.HairS02b);
        this.BodyMain.addChild(this.Butt);
        this.HeadBase.addChild(this.TailHeadCR1);
        this.BodyMain.addChild(this.EquipBase);
        this.Cloth01.addChild(this.Cloth02);
        this.Neck.addChild(this.Head);
        this.Hair.addChild(this.HairR01);
        this.EquipUmbre02a.addChild(this.EquipUmbre02b);
        this.TailHead1.addChild(this.TailHead2);
        this.Head.addChild(this.Hair);
        this.HeadBase.addChild(this.TailJaw1);
        this.HeadBase.addChild(this.TailHeadCL1);
        this.LegLeft01.addChild(this.ShoesL2);
        this.ArmRight03.addChild(this.ArmRight04);
        this.SantaHat04.addChild(this.SantaHat05);
        this.EquipBase.addChild(this.EquipRT01);
        this.EquipLHead01.addChild(this.EquipLHead02);
        this.EquipUmbre01a.addChild(this.EquipUmbre02);
        this.Cloth02.addChild(this.Cloth03);
        this.Hair.addChild(this.Ahoke);
        this.HeadHR.addChild(this.HeadHR2);
        this.ArmRight04.addChild(this.ArmRight05);
        this.EquipLT03.addChild(this.EquipLT04);
        this.ArmRight01.addChild(this.ArmRight02);
        this.ArmLeft05.addChild(this.EquipUmbre01a);
        this.HairL01.addChild(this.HairL02);
        this.Hair.addChild(this.HairL01);
        this.EquipLHead.addChild(this.EquipLHead01);
        this.Butt.addChild(this.LegLeft01);
        this.HeadHR2.addChild(this.HeadHR3);
        this.HeadHL.addChild(this.HeadHL2);
        this.EquipRT01.addChild(this.EquipRT02);
        this.LegRight01.addChild(this.LegRight02);
        this.Head.addChild(this.SantaHat01);
        this.BodyMain.addChild(this.ArmRight01);
        this.ArmLeft02.addChild(this.ArmLeft03);
        this.BodyMain.addChild(this.ArmLeft01);
        this.ArmRight02.addChild(this.ArmRight03);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 114);
        this.GlowBodyMain.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.08726646259971647F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 129, 58);
        this.GlowNeck.setRotationPoint(0.0F, -11.3F, -0.5F);
        this.setRotateAngle(GlowNeck, 0.05235987755982988F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 44, 101);
        this.GlowHead.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.GlowEquipBase = new ModelRenderer(this, 0, 0);
        this.GlowEquipBase.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.GlowEquipRT01 = new ModelRenderer(this, 0, 0);
        this.GlowEquipRT01.setRotationPoint(0.0F, 4.0F, 4.0F);
        this.setRotateAngle(GlowEquipRT01, 0.0F, 0.7853981633974483F, 0.3490658503988659F);
        this.GlowEquipRT02 = new ModelRenderer(this, 0, 0);
        this.GlowEquipRT02.setRotationPoint(-16.0F, 0.0F, 2.0F);
        this.setRotateAngle(GlowEquipRT02, 0.0F, -1.0471975511965976F, 0.0F);
        this.GlowHeadBase = new ModelRenderer(this, 0, 0);
        this.GlowHeadBase.setRotationPoint(-14.0F, -3.0F, 0.0F);
        this.setRotateAngle(GlowHeadBase, -0.4363323129985824F, -2.792526803190927F, -0.13962634015954636F);
        this.GlowTailHead1 = new ModelRenderer(this, 0, 0);
        this.GlowTailHead1.setRotationPoint(0.0F, -9.5F, 4.0F);
        this.setRotateAngle(GlowTailHead1, 0.17453292519943295F, 0.0F, 0.0F);
        this.GlowTailJaw1 = new ModelRenderer(this, 0, 0);
        this.GlowTailJaw1.setRotationPoint(0.0F, 3.0F, 5.0F);
        this.setRotateAngle(GlowTailJaw1, -0.27314402793711257F, 0.0F, 0.0F);
        this.GlowTailHead2 = new ModelRenderer(this, 0, 0);
        this.GlowTailHead2.setRotationPoint(0.0F, -1.0F, 4.5F);
        this.GlowEquipLT01 = new ModelRenderer(this, 0, 45);
        this.GlowEquipLT01.setRotationPoint(2.0F, 4.0F, 2.5F);
        this.setRotateAngle(GlowEquipLT01, 0.0F, -1.0471975511965976F, -0.2617993877991494F);
        this.GlowEquipLT02 = new ModelRenderer(this, 0, 45);
        this.GlowEquipLT02.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.setRotateAngle(GlowEquipLT02, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        this.GlowEquipLT03 = new ModelRenderer(this, 0, 45);
        this.GlowEquipLT03.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.setRotateAngle(GlowEquipLT03, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        this.GlowEquipLT04 = new ModelRenderer(this, 0, 45);
        this.GlowEquipLT04.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.setRotateAngle(GlowEquipLT04, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        this.GlowEquipLT05 = new ModelRenderer(this, 0, 45);
        this.GlowEquipLT05.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.setRotateAngle(GlowEquipLT05, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        this.GlowEquipLT06 = new ModelRenderer(this, 0, 45);
        this.GlowEquipLT06.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.setRotateAngle(GlowEquipLT06, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        this.GlowHead.addChild(this.Mouth0);
        this.GlowHead.addChild(this.Mouth1);
        this.GlowHead.addChild(this.Mouth2);
        this.GlowHead.addChild(this.Flush0);
        this.GlowHead.addChild(this.Flush1);
        this.GlowHead.addChild(this.HeadHR);
        this.GlowHead.addChild(this.HeadHL);
        
        this.GlowBodyMain.addChild(this.GlowEquipBase);
        this.GlowEquipBase.addChild(this.GlowEquipRT01);
        this.GlowEquipRT01.addChild(this.GlowEquipRT02);
        this.GlowEquipRT02.addChild(this.GlowHeadBase);
        this.GlowHeadBase.addChild(this.GlowTailHead1);
        this.GlowTailHead1.addChild(this.TailHeadT01);
        this.GlowHeadBase.addChild(this.GlowTailJaw1);
        this.GlowTailJaw1.addChild(this.TailJawT01);
        
        this.GlowHeadBase.addChild(this.EquipRoad01);
        this.EquipRoad01.addChild(this.EquipRoad02);
        this.EquipRoad02.addChild(this.EquipRoad03);
        
        this.GlowTailHead1.addChild(this.GlowTailHead2);
        this.GlowTailHead2.addChild(this.TailHeadC2);
        this.GlowTailHead2.addChild(this.TailHeadC3);
        
        this.GlowEquipBase.addChild(this.GlowEquipLT01);
        this.GlowEquipLT01.addChild(this.GlowEquipLT02);
        this.GlowEquipLT02.addChild(this.GlowEquipLT03);
        this.GlowEquipLT03.addChild(this.GlowEquipLT04);
        this.GlowEquipLT04.addChild(this.GlowEquipLT05);
        this.GlowEquipLT05.addChild(this.GlowEquipLT06);
        this.GlowEquipLT06.addChild(this.EquipLHead);
        
     	//for held item rendering
        this.armMain = new ModelRenderer[] {this.BodyMain, this.ArmRight01, this.ArmRight02, this.ArmRight03, this.ArmRight04, this.ArmRight05};
        this.armOff = new ModelRenderer[] {this.BodyMain, this.ArmLeft01, this.ArmLeft02, this.ArmLeft03, this.ArmLeft04, this.ArmLeft05};
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
    	GlStateManager.scale(0.36F, 0.34F, 0.36F);
    	GlStateManager.translate(0F, 3.08F, 0F);
    	
    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	//light part
    	GlStateManager.disableLighting();
    	GlStateManager.enableCull();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GlStateManager.disableCull();
    	GlStateManager.enableLighting();
    	
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
    }

	@Override
	public void showEquip(IShipEmotion ent)
	{
		switch (ent.getStateEmotion(ID.S.State))
		{
		default:
			this.GlowEquipBase.isHidden = true;
			this.EquipBase.isHidden = true;
			this.ShoesL.isHidden = true;
			this.ShoesL2.isHidden = true;
			this.ShoesR.isHidden = true;
		break;
		case ID.State.EQUIP00:
			this.GlowEquipBase.isHidden = true;
			this.EquipBase.isHidden = true;
			this.ShoesL.isHidden = false;
			this.ShoesL2.isHidden = false;
			this.ShoesR.isHidden = false;
		break;
		case ID.State.EQUIP01:
			this.GlowEquipBase.isHidden = false;
			this.EquipBase.isHidden = false;
			this.ShoesL.isHidden = true;
			this.ShoesL2.isHidden = true;
			this.ShoesR.isHidden = true;
		break;
		case ID.State.EQUIP02:
			this.GlowEquipBase.isHidden = false;
			this.EquipBase.isHidden = false;
			this.ShoesL.isHidden = false;
			this.ShoesL2.isHidden = false;
			this.ShoesR.isHidden = false;
		break;
		}
		
		switch (ent.getStateEmotion(ID.S.State2))
		{
		default:
			this.SantaCloth01.isHidden = true;
			this.SantaHat01.isHidden = true;
			this.EquipUmbre01a.isHidden = true;
		break;
		case ID.State.EQUIP00a:
			this.SantaCloth01.isHidden = false;
			this.SantaHat01.isHidden = false;
			this.EquipUmbre01a.isHidden = true;
		break;
		case ID.State.EQUIP01a:
			this.SantaCloth01.isHidden = true;
			this.SantaHat01.isHidden = true;
			this.EquipUmbre01a.isHidden = false;
		break;
		}
	}

	@Override
	public void syncRotationGlowPart()
	{
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowNeck.rotateAngleX = this.Neck.rotateAngleX;
		this.GlowNeck.rotateAngleY = this.Neck.rotateAngleY;
		this.GlowNeck.rotateAngleZ = this.Neck.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
		this.GlowEquipBase.rotateAngleX = this.EquipBase.rotateAngleX;
		this.GlowTailJaw1.rotateAngleX = this.TailJaw1.rotateAngleX;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.24F, 0F);
    	this.setFaceHungry(ent);
    	
  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = 0.5F; 	//上下角度
	  	this.Head.rotateAngleY = 0F;	//左右角度 角度轉成rad 即除以57.29578
	  	//body
	  	this.BodyMain.rotateAngleX = -0.087F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.2F;
	  	this.Hair02.rotateAngleX = -0.3F;
	  	this.HairL01.rotateAngleX = -0.26F;
	  	this.HairL02.rotateAngleX = 0.26F;
	  	this.HairR01.rotateAngleX = -0.26F;
	  	this.HairR02.rotateAngleX = 0.26F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = 0.2618F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	  	this.ArmLeft01.rotateAngleZ = -0.57F;
	  	this.ArmLeft02.rotateAngleX = 0F;
	  	this.ArmLeft02.offsetY = 0F;
	  	this.ArmLeft02.offsetZ = 0F;
	  	this.ArmLeft04.rotateAngleY = 0F;
	    this.ArmRight01.rotateAngleX = 0.2618F;
	    this.ArmRight01.rotateAngleZ = 0.57F;
	    this.ArmRight02.rotateAngleX = 0F;
		//leg
    	this.LegLeft01.rotateAngleX = -1.66F;
    	this.LegLeft01.rotateAngleY = -0.2618F;
    	this.LegLeft01.rotateAngleZ = -0.05F;
    	this.LegLeft02.rotateAngleX = 0F;
    	this.LegRight01.rotateAngleX = -1.66F;
    	this.LegRight01.rotateAngleY = 0.2618F;
    	this.LegRight01.rotateAngleZ = 0.05F;
    	this.LegRight02.rotateAngleX = 0F;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2*0.08F);
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
  		
  		//leg move parm
  		addk1 = angleAdd1 - 0.1745F;
	  	addk2 = angleAdd2 - 0.1745F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F; 	//上下角度
	  	this.Head.rotateAngleY = f3 * 0.01F;	//左右角度
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	  	//body
  	    this.Ahoke.rotateAngleX = angleX * 0.25F + 0.35F;
	  	this.BodyMain.rotateAngleX = -0.087F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.02F + 0.35F + headX;
	  	this.Hair02.rotateAngleX = angleX * 0.04F + 0.14F + headX;
	  	this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.26F;
	  	this.HairL02.rotateAngleX = angleX * 0.02F + headX + 0.26F;
	  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.26F;
	  	this.HairR02.rotateAngleX = angleX * 0.02F + headX + 0.26F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 + 0.2618F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	  	this.ArmLeft01.rotateAngleZ = -angleX * 0.1F - 0.5235F;
	  	this.ArmLeft02.rotateAngleX = 0F;
	  	this.ArmLeft02.offsetZ = 0F;
	  	this.ArmLeft04.rotateAngleY = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 + 0.2618F;
	    this.ArmRight01.rotateAngleY = 0F;
	    this.ArmRight01.rotateAngleZ = angleX * 0.1F + 0.5235F;
	    this.ArmRight02.rotateAngleX = 0F;
	    this.ArmRight04.rotateAngleY = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = -0.05F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = 0.05F;
    	this.LegRight02.rotateAngleX = 0F;
    	//equip
    	if (ent.getStateEmotion(ID.S.State) > ID.State.EQUIP00)
    	{
    		this.EquipBase.rotateAngleX = 0F;
    		this.TailJaw1.rotateAngleX = angleX * 0.08F - 0.15F;
    		this.TailHeadC2.rotateAngleX = angleX * 0.12F;
    		this.TailHeadC3.rotateAngleX = -angleX * 0.08F + 0.1F;
        	this.EquipLHead01.rotateAngleY = angleX * 0.1F - 0.5F;
        	this.EquipLHead01.rotateAngleZ = -angleX * 0.1F - 0.1F;
        	this.EquipLHead02.rotateAngleY = angleX * 0.3F + 0.1F;
        	this.EquipLHead02.rotateAngleZ = -angleX * 0.3F;
    	}
    	//umbrella
    	if (ent.getStateEmotion(ID.S.State2) == ID.State.EQUIP01a)
    	{
    		this.ArmLeft01.rotateAngleX = 0F;
    		this.ArmLeft01.rotateAngleY = -0.26F;
    		this.ArmLeft01.rotateAngleZ = -0.52F;
    		this.ArmLeft02.offsetY = 0.25F;
    		this.ArmLeft02.rotateAngleX = -1.57F;
    		this.ArmLeft04.rotateAngleY = -0.52F;
    		this.EquipUmbre03b.rotateAngleY = angleX * 0.3F + 0.7F;
    	}
    	
	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {	//奔跑動作
	    	setFace(3);
	    	//arm
	    	this.ArmLeft01.rotateAngleZ = -1F;
	    	this.ArmRight01.rotateAngleX = -2.9F;
	    	this.ArmRight01.rotateAngleZ = -0.7F;
	    	
	    	if (ent.getStateEmotion(ID.S.State2) == ID.State.EQUIP01a)
	    	{
	    		this.ArmLeft04.rotateAngleY = -1F;
	    	}
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
	  	
	    if (ent.getIsSneaking())
	    {	//潛行, 蹲下動作
	    	GlStateManager.translate(0F, 0.02F, 0F);
	    	//body
	    	this.Head.rotateAngleX -= 0.8727F;
	    	this.BodyMain.rotateAngleX = 1.0472F;
		  	//hair
		  	this.Hair01.rotateAngleX += 0.2236F;
		  	//leg
		  	addk1 -= 1.2F;
		  	addk2 -= 1.2F;
		  	//equip
		  	this.EquipBase.rotateAngleX -= 0.8727F;
		  	
		  	if (ent.getStateEmotion(ID.S.State2) == ID.State.EQUIP01a)
		  	{
		  		this.ArmLeft01.rotateAngleY = -1.05F;
		  		this.ArmLeft02.rotateAngleX = -2.01F;
		  		this.ArmLeft04.rotateAngleY = -1.05F;
		  	}
  		}//end if sneaking
  		
	    if (ent.getIsSitting() && !ent.getIsRiding())
	    {	//坐下動作
	    	GlStateManager.translate(0F, 0.24F, 0F);
	    	
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
		    	//body
		    	this.Head.rotateAngleX -= 0.15F;
		    	this.BodyMain.rotateAngleX = -0.3142F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -2F;
		    	this.ArmLeft01.rotateAngleY = -0.35F;
		    	this.ArmLeft01.rotateAngleZ = 0.35F;
		    	this.ArmRight01.rotateAngleX = -2.9F;
		    	this.ArmRight01.rotateAngleY = 0.35F;
		    	this.ArmRight01.rotateAngleZ = -0.35F;
		    	//leg
		    	addk1 = -1.4F;
		    	addk2 = -1.4F;
		    	this.LegLeft01.rotateAngleY = -0.2618F;
		    	this.LegRight01.rotateAngleY = 0.2618F;
	
	    		this.ArmLeft02.offsetY = 0F;
	    		this.ArmLeft02.rotateAngleX = 0F;
	    		this.ArmLeft04.rotateAngleY = 0F;
	    	}
	    	else
	    	{
		    	//arm
		    	this.ArmLeft01.rotateAngleZ -= 0.05F;
		    	this.ArmRight01.rotateAngleZ += 0.05F;
		    	//leg
		    	addk1 = -1.66F;
		    	addk2 = -1.66F;
		    	this.LegLeft01.rotateAngleY = -0.2618F;
		    	this.LegRight01.rotateAngleY = 0.2618F;
		    	this.ArmLeft02.offsetY = 0F;
	    	}
  		}//end if sitting
	    
	    if (ent.getIsRiding())
	    {	//騎乘動作
	    	GlStateManager.translate(0F, 0.24F, 0.27F);
	    	
	    	if (ent.getIsSitting())
	    	{
	    		//arm
		    	this.ArmLeft01.rotateAngleX = -0.8F;
		    	this.ArmLeft01.rotateAngleZ = -0.35F;
		    	this.ArmRight01.rotateAngleX = -0.8F;
		    	this.ArmRight01.rotateAngleZ = 0.35F;
		    	//leg
		    	addk1 = -1.66F;
		    	addk2 = -1.66F;
		    	this.LegLeft01.rotateAngleY = -0.5F;
		    	this.LegRight01.rotateAngleY = 0.5F;
		    	
		    	if (ent.getStateEmotion(ID.S.State2) == ID.State.EQUIP01a)
		    	{
		    		this.ArmLeft02.offsetY = 0F;
		    		this.ArmLeft02.rotateAngleX = -0.8F;
		    		this.ArmLeft04.rotateAngleY = -0.4F;
		    	}
	    	}
	    	else
	    	{
	    		setFace(3);
	    		//head
	    		this.Head.rotateAngleX -= 0.25F;
	    		//arm
		    	this.ArmLeft01.rotateAngleX = -1.2F;
		    	this.ArmLeft01.rotateAngleY = -0.2F;
		    	this.ArmLeft01.rotateAngleZ = -0.2F;
		    	this.ArmRight01.rotateAngleX = -2.53F;
		    	this.ArmRight01.rotateAngleZ = -0.7F;
		    	//leg
		    	addk1 = -1.66F;
		    	addk2 = -1.66F;
		    	this.LegLeft01.rotateAngleY = -0.5F;
		    	this.LegRight01.rotateAngleY = 0.5F;
		    	
		    	if (ent.getStateEmotion(ID.S.State2) == ID.State.EQUIP01a)
		    	{
		    		this.ArmLeft02.offsetY = 0F;
		    		this.ArmLeft02.rotateAngleX = -0.2F;
		    		this.ArmLeft04.rotateAngleY = -0.4F;
		    	}
	    	}
	    }//end if riding
	    
	    //攻擊動作
	    if (ent.getAttackTick() > 49)
	    {
	    	this.ArmRight01.rotateAngleX = -3.5F;
	    	this.ArmRight01.rotateAngleY = 0F;
	    	this.ArmRight01.rotateAngleZ = -0.35F;
	    	this.ArmRight04.rotateAngleY = -1.57F;
	    }
	    else if (ent.getAttackTick() > 46)
	    {
	    	this.ArmRight01.rotateAngleX = (46F - ent.getAttackTick() + (f2 - (int)f2)) * 0.75F - 0.5F;
	    	this.ArmRight01.rotateAngleY = 0F;
	    	this.ArmRight01.rotateAngleZ = -0.35F;
	    	this.ArmRight04.rotateAngleY = -1.57F;
	    }
	    else if (ent.getAttackTick() > 35)
	    {
	    	this.ArmRight01.rotateAngleX = -0.5F;
	    	this.ArmRight01.rotateAngleY = 0F;
	    	this.ArmRight01.rotateAngleZ = 0.5F;
	    	this.ArmRight04.rotateAngleY = -1.57F;
	    }
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
	@Override
	public void setFaceNormal(IShipEmotion ent)
	{
		this.setFace(0);
		
		if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED && (ent.getTickExisted() & 255) > 200)
		{
			this.setMouth(0);
		}
		else
		{
			this.setMouth(3);
		}
	}

	@Override
	public void setFaceBlink0(IShipEmotion ent)
	{
		this.setFace(0);
	}

	@Override
	public void setFaceBlink1(IShipEmotion ent)
	{
		this.setFace(1);
	}

	@Override
	public void setFaceCry(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 255;
		
		if (t < 128)
		{
			this.setFace(6);
			
			if (t < 64)
			{
				this.setMouth(2);
			}
			else
			{
				this.setMouth(5);
			}
		}
		else
		{
			this.setFace(7);
			this.setMouth(5);
		}
	}

	@Override
	public void setFaceAttack(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 511;
		
		if (t < 160)
		{
			this.setFace(0);
			
			if (t < 80)
			{
				this.setMouth(4);
			}
			else
			{
				this.setMouth(3);
			}
		}
		else if (t < 320)
		{
			this.setFace(2);
			
			if (t < 220)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(3);
			}
		}
		else if (t < 410)
		{
			this.setFace(3);
			
			if (t < 360)
			{
				this.setMouth(5);
			}
			else
			{
				this.setMouth(4);
			}
		}
		else
		{
			this.setFace(5);
			
			if (t < 470)
			{
				this.setMouth(3);
			}
			else
			{
				this.setMouth(4);
			}
		}
	}
	
	@Override
	public void setFaceDamaged(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 511;
		
		if (t < 200)
		{
			this.setFace(6);
			
			if (t < 60)
			{
				this.setMouth(4);
			}
			else
			{
				this.setMouth(5);
			}
		}
		else if (t < 400)
		{
			this.setFace(3);
			
			if (t < 250)
			{
				this.setMouth(3);
			}
			else
			{
				this.setMouth(5);
			}
		}
		else
		{
			this.setFace(9);
			
			if (t < 450)
			{
				this.setMouth(2);
			}
			else
			{
				this.setMouth(3);
			}
		}
	}
	
	@Override
	public void setFaceScorn(IShipEmotion ent)
	{
		this.setFace(2);
		this.setMouth(3);
	}

	@Override
	public void setFaceHungry(IShipEmotion ent)
	{
		this.setFace(4);
		this.setMouth(3);
	}

	@Override
	public void setFaceAngry(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 255;
		
		if (t < 128)
		{
			this.setFace(1);
			
			if (t < 64)
			{
				this.setMouth(3);
			}
			else
			{
				this.setMouth(1);
			}
		}
		else
		{
			this.setFace(2);

			if (t < 170)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(3);
			}
		}
	}

	@Override
	public void setFaceBored(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 511;
		
		if (t < 170)
		{
			this.setFace(1);
			
			if (t < 80)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(3);
			}
		}
		else if (t < 340)
		{
			this.setFace(5);

			if (t < 250)
			{
				this.setMouth(4);
			}
			else
			{
				this.setMouth(3);
			}
		}
		else
		{
			this.setFace(0);

			if (t < 420)
			{
				this.setMouth(4);
			}
			else
			{
				this.setMouth(3);
			}
		}
	}
	
	@Override
	public void setFaceShy(IShipEmotion ent)
	{
		this.setFlush(true);
		
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 255;
		
		this.setFace(0);
		
		if (t < 150)
		{
			this.setMouth(3);
		}
		else
		{
			this.setMouth(2);
		}
	}
	
	@Override
	public void setFaceHappy(IShipEmotion ent)
	{
		this.setFlush(true);
		
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 255;
		
		if (t < 140)
		{
			this.setFace(3);
			
			if (t < 80)
			{
				this.setMouth(4);
			}
			else
			{
				this.setMouth(3);
			}
		}
		else
		{
			this.setFace(8);
			this.setMouth(3);
		}
	}
	
	@Override
    public float[] getHeldItemOffset(IShipEmotion ent, EnumHandSide side, int type)
    {
		if (side == EnumHandSide.RIGHT && (ent.getIsSprinting() || ent.getIsRiding()))
		{
			if (ent.getIsSprinting())
			{
				this.offsetItem2[1] = 1.05F;
				this.offsetItem2[2] = -0.14F;
			}
			else if (ent.getIsRiding())
			{
				this.offsetItem2[1] = 1.14F;
				this.offsetItem2[2] = 0F;
			}
			
			return type == 0 ? this.offsetItem2 : this.offsetBlock2;
		}
		
    	return type == 0 ? this.offsetItem : this.offsetBlock;
    }
    
	@Override
    public float[] getHeldItemRotate(IShipEmotion ent, EnumHandSide side, int type)
    {
		if (side == EnumHandSide.RIGHT && (ent.getIsSprinting() || ent.getIsRiding()))
		{
			return type == 0 ? this.rotateItem2 : this.rotateBlock;
		}
		
    	return type == 0 ? this.rotateItem : this.rotateBlock;
    }

    
}