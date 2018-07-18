package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

/**
 * ModelSubmHimeNew - PinkaLulan 2018/2/9
 * Created using Tabula 6.0.0
 */
public class ModelSSNH extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
    public ModelRenderer EquipBase;
    public ModelRenderer Cloth01;
    public ModelRenderer Neck;
    public ModelRenderer Cloth00;
    public ModelRenderer RingBase;
    public ModelRenderer ArmLeft02;
    public ModelRenderer EquipTBase;
    public ModelRenderer EqyuipT01;
    public ModelRenderer EqyuipT02;
    public ModelRenderer EqyuipT04;
    public ModelRenderer EqyuipT03;
    public ModelRenderer EquipT03a;
    public ModelRenderer EqyuipT05;
    public ModelRenderer EquipT05a;
    public ModelRenderer EquipT05b;
    public ModelRenderer EquipT05c;
    public ModelRenderer EquipT05d;
    public ModelRenderer EquipTBase_2;
    public ModelRenderer EqyuipT01_2;
    public ModelRenderer EqyuipT02_2;
    public ModelRenderer EqyuipT04_2;
    public ModelRenderer EqyuipT03_2;
    public ModelRenderer EquipT03a_2;
    public ModelRenderer EqyuipT05_2;
    public ModelRenderer EquipT05a_2;
    public ModelRenderer EquipT05b_2;
    public ModelRenderer EquipT05c_2;
    public ModelRenderer EquipT05d_2;
    public ModelRenderer ArmRight02;
    public ModelRenderer EquipHandRing;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer LegLeft02;
    public ModelRenderer Cloth02;
    public ModelRenderer Cloth03;
    public ModelRenderer Cloth04;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Ahoke01;
    public ModelRenderer Ahoke01a;
    public ModelRenderer HairU01;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Ahoke02;
    public ModelRenderer Ahoke03;
    public ModelRenderer Ahoke04;
    public ModelRenderer Ahoke05;
    public ModelRenderer Ahoke06;
    public ModelRenderer Ahoke02a;
    public ModelRenderer Ahoke03a;
    public ModelRenderer Ahoke04a;
    public ModelRenderer Ahoke05a;
    public ModelRenderer Ahoke06a;
    public ModelRenderer Ring01;
    public ModelRenderer Ring02;
    public ModelRenderer Ring03Base;
    public ModelRenderer Ring03a;
    public ModelRenderer Ring03b;
    public ModelRenderer Ring03c;
    public ModelRenderer Ring03d;
    public ModelRenderer Ring03e;
    public ModelRenderer Ring03f;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    
    
    public ModelSSNH()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.scale = 0.32F;
        this.offsetY = 3.21F;
        this.offsetItem = new float[] {0.08F, 1.02F, -0.07F};
        this.offsetBlock = new float[] {0.08F, 1.02F, -0.07F};
        
        this.setDefaultFaceModel();
        
        this.EqyuipT02 = new ModelRenderer(this, 4, 0);
        this.EqyuipT02.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.EqyuipT02.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.EqyuipT02_2 = new ModelRenderer(this, 4, 0);
        this.EqyuipT02_2.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.EqyuipT02_2.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.Ring03Base = new ModelRenderer(this, 0, 0);
        this.Ring03Base.setRotationPoint(-2.0F, -10.0F, 1.7F);
        this.Ring03Base.addBox(2.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Ring03Base, -0.6108652381980153F, 0.17453292519943295F, -0.10471975511965977F);
        this.BodyMain = new ModelRenderer(this, 0, 113);
        this.BodyMain.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.BodyMain.addBox(-5.5F, -11.0F, -3.5F, 11, 9, 6, 0.0F);
        this.setRotateAngle(BodyMain, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipT03a = new ModelRenderer(this, 0, 0);
        this.EquipT03a.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.EquipT03a.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
        this.EquipT03a_2 = new ModelRenderer(this, 0, 0);
        this.EquipT03a_2.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.EquipT03a_2.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
        this.Cloth04 = new ModelRenderer(this, 0, 26);
        this.Cloth04.setRotationPoint(0.0F, 2.0F, -0.3F);
        this.Cloth04.addBox(-9.0F, 0.0F, 0.0F, 18, 3, 11, 0.0F);
        this.setRotateAngle(Cloth04, -0.05235987755982988F, 0.0F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.6F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.EquipHandRing = new ModelRenderer(this, 0, 91);
        this.EquipHandRing.setRotationPoint(0.0F, 4.0F, -2.0F);
        this.EquipHandRing.addBox(-2.5F, 0.0F, -2.5F, 5, 2, 5, 0.0F);
        this.Ring02 = new ModelRenderer(this, 62, 13);
        this.Ring02.setRotationPoint(0.3F, 8.5F, 0.2F);
        this.Ring02.addBox(-4.0F, -9.0F, -0.5F, 8, 9, 1, 0.0F);
        this.setRotateAngle(Ring02, 0.22759093446006054F, -0.03874630939427412F, -2.792526803190927F);
        this.Butt = new ModelRenderer(this, 0, 78);
        this.Butt.setRotationPoint(0.0F, -2.0F, -4.0F);
        this.Butt.addBox(-5.5F, 0.0F, 0.0F, 11, 6, 7, 0.0F);
        this.setRotateAngle(Butt, 0.20943951023931953F, 0.0F, 0.0F);
        this.Ring03b = new ModelRenderer(this, 36, 0);
        this.Ring03b.setRotationPoint(-1.9F, 2.0F, 0.0F);
        this.Ring03b.addBox(0.0F, -2.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(Ring03b, 0.0F, 0.0F, -1.5707963267948966F);
        this.Ring01 = new ModelRenderer(this, 62, 0);
        this.Ring01.setRotationPoint(4.5F, 4.3F, 0.0F);
        this.Ring01.addBox(-4.0F, 0.0F, -0.5F, 8, 10, 1, 0.0F);
        this.setRotateAngle(Ring01, -0.8203047484373349F, 1.5009831567151235F, 0.0F);
        this.Ahoke03 = new ModelRenderer(this, 50, 79);
        this.Ahoke03.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke03.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke03, 0.7853981633974483F, 0.05235987755982988F, 0.0F);
        this.Ahoke06a = new ModelRenderer(this, 42, 89);
        this.Ahoke06a.mirror = true;
        this.Ahoke06a.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke06a.addBox(-2.0F, 0.0F, 0.0F, 4, 7, 0, 0.0F);
        this.setRotateAngle(Ahoke06a, -0.5235987755982988F, 0.08726646259971647F, 0.0F);
        this.Ahoke05a = new ModelRenderer(this, 50, 77);
        this.Ahoke05a.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke05a.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke05a, -0.2617993877991494F, 0.08726646259971647F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, -1.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.Ahoke04 = new ModelRenderer(this, 50, 77);
        this.Ahoke04.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke04.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke04, 0.4363323129985824F, 0.05235987755982988F, 0.0F);
        this.Ahoke02a = new ModelRenderer(this, 50, 79);
        this.Ahoke02a.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke02a.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke02a, 0.7853981633974483F, -0.05235987755982988F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 2, 99);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(6.0F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-1.0F, -1.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.13962634015954636F, 0.0F, -0.2617993877991494F);
        this.Ahoke03a = new ModelRenderer(this, 50, 79);
        this.Ahoke03a.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke03a.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke03a, 1.0471975511965976F, 0.05235987755982988F, 0.0F);
        this.Ring03c = new ModelRenderer(this, 36, 0);
        this.Ring03c.setRotationPoint(-4.0F, -8.9F, 0.0F);
        this.Ring03c.addBox(0.0F, -2.0F, -2.0F, 9, 4, 4, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 98);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(3.2F, 5.5F, 2.4F);
        this.LegLeft01.addBox(-2.5F, 0.0F, -2.5F, 5, 9, 5, 0.0F);
        this.setRotateAngle(LegLeft01, -0.10471975511965977F, 0.0F, -0.05235987755982988F);
        this.EqyuipT05 = new ModelRenderer(this, 2, 3);
        this.EqyuipT05.setRotationPoint(0.0F, -6.9F, 0.0F);
        this.EqyuipT05.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.setRotateAngle(EqyuipT05, 0.0F, 0.0F, 0.02142916587671676F);
        this.EqyuipT05_2 = new ModelRenderer(this, 2, 3);
        this.EqyuipT05_2.setRotationPoint(0.0F, -6.9F, 0.0F);
        this.EqyuipT05_2.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.setRotateAngle(EqyuipT05_2, 0.0F, 0.0F, 0.02142916587671676F);
        this.Ring03a = new ModelRenderer(this, 36, 0);
        this.Ring03a.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Ring03a.addBox(0.0F, -2.0F, -2.0F, 9, 4, 4, 0.0F);
        this.Ring03e = new ModelRenderer(this, 36, 0);
        this.Ring03e.setRotationPoint(-2.0F, -10.8F, 0.0F);
        this.Ring03e.addBox(0.0F, -2.0F, -1.0F, 1, 2, 2, 0.0F);
        this.setRotateAngle(Ring03e, 0.0F, 0.0F, 0.08726646259971647F);
        this.Hair01 = new ModelRenderer(this, 80, 0);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 12, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.2617993877991494F, 0.0F, 0.0F);
        this.EquipT05b = new ModelRenderer(this, 14, 4);
        this.EquipT05b.setRotationPoint(-1.9F, 1.0F, 0.0F);
        this.EquipT05b.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
        this.setRotateAngle(EquipT05b, 0.0F, -1.5707963267948966F, 0.0F);
        this.EquipT05b_2 = new ModelRenderer(this, 14, 4);
        this.EquipT05b_2.setRotationPoint(-1.9F, 1.0F, 0.0F);
        this.EquipT05b_2.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
        this.setRotateAngle(EquipT05b_2, 0.0F, -1.5707963267948966F, 0.0F);
        this.Cloth00 = new ModelRenderer(this, 56, 41);
        this.Cloth00.setRotationPoint(0.0F, -11.3F, -1.0F);
        this.Cloth00.addBox(-6.0F, 0.0F, -2.9F, 12, 8, 7, 0.0F);
        this.Ahoke01 = new ModelRenderer(this, 50, 77);
        this.Ahoke01.setRotationPoint(-1.0F, -15.0F, 0.0F);
        this.Ahoke01.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke01, -2.007128639793479F, 0.5235987755982988F, 0.0F);
        this.EqyuipT04 = new ModelRenderer(this, 3, 4);
        this.EqyuipT04.setRotationPoint(0.0F, -6.9F, 0.0F);
        this.EqyuipT04.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.EqyuipT04_2 = new ModelRenderer(this, 3, 4);
        this.EqyuipT04_2.setRotationPoint(0.0F, -6.9F, 0.0F);
        this.EqyuipT04_2.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.EquipT05c = new ModelRenderer(this, 0, 4);
        this.EquipT05c.setRotationPoint(1.9F, 1.0F, 0.0F);
        this.EquipT05c.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
        this.setRotateAngle(EquipT05c, 0.0F, 1.5707963267948966F, 0.0F);
        this.EquipT05c_2 = new ModelRenderer(this, 0, 4);
        this.EquipT05c_2.setRotationPoint(1.9F, 1.0F, 0.0F);
        this.EquipT05c_2.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
        this.setRotateAngle(EquipT05c_2, 0.0F, 1.5707963267948966F, 0.0F);
        this.Neck = new ModelRenderer(this, 2, 99);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.2F);
        this.Neck.addBox(-2.0F, -2.0F, -2.0F, 4, 2, 4, 0.0F);
        this.setRotateAngle(Neck, 0.08726646259971647F, 0.0F, 0.0F);
        this.EqyuipT03 = new ModelRenderer(this, 0, 0);
        this.EqyuipT03.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.EqyuipT03.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.EqyuipT03_2 = new ModelRenderer(this, 0, 0);
        this.EqyuipT03_2.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.EqyuipT03_2.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.EquipT05a = new ModelRenderer(this, 8, 7);
        this.EquipT05a.setRotationPoint(0.0F, 1.0F, 1.9F);
        this.EquipT05a.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
        this.EquipT05a_2 = new ModelRenderer(this, 8, 7);
        this.EquipT05a_2.setRotationPoint(0.0F, 1.0F, 1.9F);
        this.EquipT05a_2.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 2, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Ahoke04a = new ModelRenderer(this, 50, 77);
        this.Ahoke04a.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke04a.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke04a, 0.4886921905584123F, 0.05235987755982988F, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 0, 66);
        this.Cloth01.setRotationPoint(0.0F, -3.3F, -4.3F);
        this.Cloth01.addBox(-6.0F, 0.0F, 0.0F, 12, 4, 8, 0.0F);
        this.setRotateAngle(Cloth01, 0.05235987755982988F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 2, 99);
        this.ArmRight02.setRotationPoint(-3.0F, 7.0F, 2.0F);
        this.ArmRight02.addBox(0.0F, 0.0F, -4.0F, 4, 9, 4, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 2, 99);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.0F, 7.0F, 2.0F);
        this.ArmLeft02.addBox(-4.0F, 0.0F, -4.0F, 4, 9, 4, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 2, 99);
        this.ArmRight01.setRotationPoint(-6.0F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.0F, 4, 8, 4, 0.0F);
        this.setRotateAngle(ArmRight01, 0.13962634015954636F, 0.0F, 0.6108652381980153F);
        this.Hair02 = new ModelRenderer(this, 80, 22);
        this.Hair02.setRotationPoint(0.0F, 9.5F, 5.8F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 11, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.08726646259971647F, 0.0F, 0.0F);
        this.Ring03d = new ModelRenderer(this, 36, 0);
        this.Ring03d.setRotationPoint(7.0F, -1.9F, 0.0F);
        this.Ring03d.addBox(0.0F, -2.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(Ring03d, 0.0F, 0.0F, -1.5707963267948966F);
        this.RingBase = new ModelRenderer(this, 0, 0);
        this.RingBase.setRotationPoint(0.0F, -16.0F, -0.4F);
        this.RingBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.Cloth03 = new ModelRenderer(this, 0, 40);
        this.Cloth03.setRotationPoint(0.0F, 2.3F, -0.2F);
        this.Cloth03.addBox(-8.0F, 0.0F, 0.0F, 16, 3, 10, 0.0F);
        this.setRotateAngle(Cloth03, 0.08726646259971647F, 0.0F, 0.0F);
        this.Ahoke06 = new ModelRenderer(this, 42, 90);
        this.Ahoke06.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke06.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke06, -0.4363323129985824F, 0.08726646259971647F, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 98);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0.0F, 9.0F, -2.5F);
        this.LegLeft02.addBox(-2.5F, 0.0F, 0.0F, 5, 10, 5, 0.0F);
        this.Cloth02 = new ModelRenderer(this, 0, 53);
        this.Cloth02.setRotationPoint(0.0F, 3.0F, -0.3F);
        this.Cloth02.addBox(-7.0F, 0.0F, 0.0F, 14, 3, 9, 0.0F);
        this.setRotateAngle(Cloth02, 0.08726646259971647F, 0.0F, 0.0F);
        this.Ahoke01a = new ModelRenderer(this, 50, 79);
        this.Ahoke01a.setRotationPoint(0.0F, -15.0F, -1.5F);
        this.Ahoke01a.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke01a, -2.2689280275926285F, -2.6179938779914944F, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.EquipT05d = new ModelRenderer(this, 0, 8);
        this.EquipT05d.setRotationPoint(0.0F, 1.0F, -1.9F);
        this.EquipT05d.addBox(-0.5F, 0.0F, -2.0F, 1, 6, 2, 0.0F);
        this.EquipT05d_2 = new ModelRenderer(this, 0, 8);
        this.EquipT05d_2.setRotationPoint(0.0F, 1.0F, -1.9F);
        this.EquipT05d_2.addBox(-0.5F, 0.0F, -2.0F, 1, 6, 2, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 98);
        this.LegRight01.setRotationPoint(-3.2F, 5.5F, 2.4F);
        this.LegRight01.addBox(-2.5F, 0.0F, -2.5F, 5, 9, 5, 0.0F);
        this.setRotateAngle(LegRight01, -0.10471975511965977F, 0.0F, 0.05235987755982988F);
        this.EqyuipT01 = new ModelRenderer(this, 5, 0);
        this.EqyuipT01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EqyuipT01.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.setRotateAngle(EqyuipT01, -1.5707963267948966F, 0.0F, 0.0F);
        this.EqyuipT01_2 = new ModelRenderer(this, 5, 0);
        this.EqyuipT01_2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EqyuipT01_2.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4, 0.0F);
        this.setRotateAngle(EqyuipT01_2, -1.5707963267948966F, 0.0F, 0.0F);
        this.Ahoke02 = new ModelRenderer(this, 50, 77);
        this.Ahoke02.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke02.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke02, 1.0471975511965976F, -0.05235987755982988F, 0.0F);
        this.Ahoke05 = new ModelRenderer(this, 50, 79);
        this.Ahoke05.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke05.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke05, -0.17453292519943295F, 0.08726646259971647F, 0.0F);
        this.Ring03f = new ModelRenderer(this, 36, 0);
        this.Ring03f.setRotationPoint(7.0F, -10.8F, 0.0F);
        this.Ring03f.addBox(-1.0F, -2.0F, -1.0F, 1, 2, 2, 0.0F);
        this.setRotateAngle(Ring03f, 0.0F, 0.0F, -0.08726646259971647F);
        this.EquipTBase = new ModelRenderer(this, 0, 0);
        this.EquipTBase.setRotationPoint(-2.6F, 9.0F, -2.0F);
        this.EquipTBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTBase, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipTBase_2 = new ModelRenderer(this, 0, 0);
        this.EquipTBase_2.setRotationPoint(2.6F, 9.0F, -2.0F);
        this.EquipTBase_2.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTBase_2, 0.08726646259971647F, 0.0F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 98);
        this.LegRight02.setRotationPoint(0.0F, 9.0F, -2.5F);
        this.LegRight02.addBox(-2.5F, 0.0F, 0.0F, 5, 10, 5, 0.0F);
        this.EqyuipT01.addChild(this.EqyuipT02);
        this.Ring02.addChild(this.Ring03Base);
        this.EqyuipT03.addChild(this.EquipT03a);
        this.Cloth03.addChild(this.Cloth04);
        this.Head.addChild(this.Hair);
        this.ArmRight02.addChild(this.EquipHandRing);
        this.Ring01.addChild(this.Ring02);
        this.BodyMain.addChild(this.Butt);
        this.Ring03Base.addChild(this.Ring03b);
        this.RingBase.addChild(this.Ring01);
        this.Ahoke02.addChild(this.Ahoke03);
        this.Ahoke05a.addChild(this.Ahoke06a);
        this.Ahoke04a.addChild(this.Ahoke05a);
        this.Neck.addChild(this.Head);
        this.Ahoke03.addChild(this.Ahoke04);
        this.Ahoke01a.addChild(this.Ahoke02a);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Ahoke02a.addChild(this.Ahoke03a);
        this.Ring03Base.addChild(this.Ring03c);
        this.Butt.addChild(this.LegLeft01);
        this.EqyuipT04.addChild(this.EqyuipT05);
        this.Ring03Base.addChild(this.Ring03a);
        this.Ring03Base.addChild(this.Ring03e);
        this.HairMain.addChild(this.Hair01);
        this.EqyuipT05.addChild(this.EquipT05b);
        this.BodyMain.addChild(this.Cloth00);
        this.Head.addChild(this.Ahoke01);
        this.EqyuipT01.addChild(this.EqyuipT04);
        this.EqyuipT05.addChild(this.EquipT05c);
        this.BodyMain.addChild(this.Neck);
        this.EqyuipT02.addChild(this.EqyuipT03);
        this.EqyuipT05.addChild(this.EquipT05a);
        this.BodyMain.addChild(this.EquipBase);
        this.Ahoke03a.addChild(this.Ahoke04a);
        this.BodyMain.addChild(this.Cloth01);
        this.ArmRight01.addChild(this.ArmRight02);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.BodyMain.addChild(this.ArmRight01);
        this.Hair01.addChild(this.Hair02);
        this.Ring03Base.addChild(this.Ring03d);
        this.BodyMain.addChild(this.RingBase);
        this.Hair.addChild(this.HairU01);
        this.Cloth02.addChild(this.Cloth03);
        this.Ahoke05.addChild(this.Ahoke06);
        this.LegLeft01.addChild(this.LegLeft02);
        this.Cloth01.addChild(this.Cloth02);
        this.Head.addChild(this.Ahoke01a);
        this.Head.addChild(this.HairMain);
        this.EqyuipT05.addChild(this.EquipT05d);
        this.Butt.addChild(this.LegRight01);
        this.EquipTBase.addChild(this.EqyuipT01);
        this.Ahoke01.addChild(this.Ahoke02);
        this.Ahoke04.addChild(this.Ahoke05);
        this.Ring03Base.addChild(this.Ring03f);
        this.ArmLeft02.addChild(this.EquipTBase);
        this.LegRight01.addChild(this.LegRight02);
        this.ArmRight02.addChild(this.EquipTBase_2);
        this.EquipTBase_2.addChild(this.EqyuipT01_2);
        this.EqyuipT01_2.addChild(this.EqyuipT02_2);
        this.EqyuipT01_2.addChild(this.EqyuipT04_2);
        this.EqyuipT02_2.addChild(this.EqyuipT03_2);
        this.EqyuipT03_2.addChild(this.EquipT03a_2);
        this.EqyuipT04_2.addChild(this.EqyuipT05_2);
        this.EqyuipT05_2.addChild(this.EquipT05a_2);
        this.EqyuipT05_2.addChild(this.EquipT05b_2);
        this.EqyuipT05_2.addChild(this.EquipT05c_2);
        this.EqyuipT05_2.addChild(this.EquipT05d_2);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.08726646259971647F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, 0.2F);
        this.setRotateAngle(GlowNeck, 0.08726646259971647F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.5F, -1.0F);
        
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
        
        //for held item rendering
        this.armMain = new ModelRenderer[] {this.BodyMain, this.ArmRight01, this.ArmRight02};
        this.armOff = new ModelRenderer[] {this.BodyMain, this.ArmLeft01, this.ArmLeft02};
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
    	GlStateManager.scale(this.scale, this.scale, this.scale);
    	GlStateManager.translate(0F, this.offsetY, 0F);
    	
    	//main body
    	this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	//light part
    	GlStateManager.disableLighting();
    	GlStateManager.enableCull();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GlStateManager.disableCull();
    	GlStateManager.enableLighting();
    	
    	//reset light
    	int j = entity.getBrightnessForRender(f5);
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k, (float)l);
    	
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
    }
    
	@Override
	public void showEquip(IShipEmotion ent)
	{
		int state = ent.getStateEmotion(ID.S.State);
		
		boolean flag = !EmotionHelper.checkModelState(0, state);	//wrist
		this.EquipHandRing.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(1, state);	//ring
		this.RingBase.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(2, state);	//torpedo
		this.EquipTBase.isHidden = flag;
		
		//hide torpedo 2
		this.EquipTBase_2.isHidden = true;
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
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.27F, 0F);
  		this.setFaceHungry(ent);

  	    //頭部
	  	this.Head.rotateAngleX = -0.15F;
	  	this.Head.rotateAngleY = 0F;
	  	this.Head.rotateAngleZ = 0F;
	  	//Body
	  	this.BodyMain.rotateAngleX = 1.6F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.21F;
    	this.Butt.offsetY = 0F;
    	this.Butt.offsetZ = 0F;
    	//cloth
	  	this.Cloth03.rotateAngleX = 0.087F;
	  	this.Cloth03.offsetY = 0F;
	  	this.Cloth03.offsetZ = 0F;
	  	this.Cloth04.rotateAngleX = -0.052F;
	  	this.Cloth04.offsetY = 0F;
	  	this.Cloth04.offsetZ = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.35F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -0.2F;
	  	this.Hair02.rotateAngleZ = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -3.0F;
	  	this.ArmLeft01.rotateAngleY = -0.6981F;
	    this.ArmLeft01.rotateAngleZ = 0.08F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmRight01.rotateAngleX = -3.0F;
	  	this.ArmRight01.rotateAngleY = 0.6981F;
	    this.ArmRight01.rotateAngleZ = -0.08F;
	    this.ArmRight02.rotateAngleX = 0F;
	    this.ArmRight02.rotateAngleY = 0F;
	    this.ArmRight02.rotateAngleZ = 0F;
		//leg
    	this.LegLeft01.rotateAngleX = -0.3F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = -0.05F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleX = -0.3F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = 0.05F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
		this.EquipTBase.rotateAngleX = 0.8F;
		this.EquipTBase.rotateAngleY = 0F;
		this.EquipTBase.rotateAngleZ = 1.2F;
		this.EquipTBase.offsetX = 0F;
		this.EquipTBase.offsetY = 0F;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.4F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.08F + 0.8F + f * 0.5F);
  		float angleX3 = MathHelper.cos(f2*0.08F + 1.2F + f * 0.5F);
  		float angleX4 = MathHelper.cos(f2*0.08F + 1.6F + f * 0.5F);
  		float angleX5 = MathHelper.cos(f2*0.08F + 2.0F + f * 0.5F);
  		float angleX6 = MathHelper.cos(f2*0.08F + 2.4F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.5F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.5F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		float headX = 0F;
  		float headZ = 0F;
  		int state = ent.getStateEmotion(ID.S.State);
  		boolean showTorpedo = EmotionHelper.checkModelState(2, state);
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D || ent.getShipDepth(1) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.025F + 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = angleAdd1 * 0.6F - 0.1F;
	  	addk2 = angleAdd2 * 0.6F - 0.1F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F; 	//上下角度
	  	this.Head.rotateAngleY = f3 * 0.006F;	//左右角度
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	  	//Body
  	    this.Ahoke01.rotateAngleX = angleX1 * 0.07F - 2.01F;
  	    this.Ahoke01.rotateAngleY = 0.52F;
  	    this.Ahoke01.rotateAngleZ = 0F;
  	    this.Ahoke02.rotateAngleX = -angleX2 * 0.09F + 1.04F;
  	    this.Ahoke03.rotateAngleX = angleX3 * 0.15F + 0.78F;
  	    this.Ahoke04.rotateAngleX = -angleX4 * 0.10F + 0.44F;
  	    this.Ahoke05.rotateAngleX = -angleX5 * 0.15F - 0.17F;
  	    this.Ahoke06.rotateAngleX = angleX6 * 0.18F - 0.31F;
  	    this.Ahoke01a.rotateAngleX = angleX1 * 0.07F - 2.27F;
  	    this.Ahoke01a.rotateAngleY = -2.62F;
  	    this.Ahoke01a.rotateAngleZ = 0F;
	    this.Ahoke02a.rotateAngleX = -angleX2 * 0.09F + 0.79F;
	    this.Ahoke03a.rotateAngleX = angleX3 * 0.15F + 1.05F;
	    this.Ahoke04a.rotateAngleX = -angleX4 * 0.10F + 0.41F;
	    this.Ahoke05a.rotateAngleX = -angleX5 * 0.15F - 0.3F;
	    this.Ahoke06a.rotateAngleX = angleX6 * 0.18F - 0.25F;
	  	this.BodyMain.rotateAngleX = -0.0873F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.21F;
    	this.Butt.offsetY = 0F;
    	this.Butt.offsetZ = 0F;
    	//cloth
    	this.Cloth02.rotateAngleX = 0.087F;
	  	this.Cloth02.offsetY = 0F;
	  	this.Cloth02.offsetZ = 0F;
	  	this.Cloth03.rotateAngleX = 0.087F;
	  	this.Cloth03.offsetY = 0F;
	  	this.Cloth03.offsetZ = 0F;
	  	this.Cloth04.rotateAngleX = -0.052F;
	  	this.Cloth04.offsetY = 0F;
	  	this.Cloth04.offsetZ = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.26F + headX;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.087F + headX;
	  	this.Hair02.rotateAngleZ = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.8F - 0.05F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.025F - 0.3F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.8F + 0.26F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.025F + 0.4F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleY = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
	    this.ArmRight02.offsetZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = -0.035F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = 0.035F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
		this.EquipTBase.rotateAngleX = 0.15F;
		this.EquipTBase.rotateAngleY = 0F;
		this.EquipTBase.rotateAngleZ = 0F;
		this.EquipTBase.offsetX = -0.13F;
		this.EquipTBase.offsetY = 0F;
		this.EquipTBase_2.rotateAngleX = 0.15F;
		this.EquipTBase_2.rotateAngleY = 0F;
		this.EquipTBase_2.rotateAngleZ = 0F;
		this.EquipTBase_2.offsetX = 0F;
		this.EquipTBase_2.offsetY = 0F;
		
		//奔跑動作
	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {
	    	if (ent.getIsRiding())
	    	{
	    		if (f1 > 0.5F)
	    		{
	    			this.Head.rotateAngleX += 0.4F;
		    		this.Hair01.rotateAngleX += 0.1F;
				  	this.Hair02.rotateAngleX -= 0.2F;
	    		}
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, -0.06F, 0F);
	    		this.Head.rotateAngleX -= 1.3F;
	    		this.Hair01.rotateAngleX += 0.6F;
			  	this.Hair02.rotateAngleX += 0.5F;
			  	this.Ahoke01.rotateAngleX += 0.38F;
			  	this.Ahoke01.rotateAngleY = 0.7F;
			  	this.Ahoke01.rotateAngleZ = 0.4F;
			  	this.Ahoke01a.rotateAngleY = -2.5F;
			  	this.Ahoke01a.rotateAngleZ = -0.2F;
	    	}
	    	
		    //body
	    	this.BodyMain.rotateAngleX = 1.5F;
	    	//arm
	    	this.ArmLeft01.rotateAngleX = -2.9F;
		    this.ArmLeft01.rotateAngleZ = -0.22F;
		    this.ArmRight01.rotateAngleX = -2.9F;
		    this.ArmRight01.rotateAngleZ = 0.22F;
		    //leg
		    this.LegLeft01.rotateAngleZ = 0.05F;
		  	this.LegRight01.rotateAngleZ = -0.05F;
		  	//equip
		  	if (showTorpedo)
		  	{
		  		this.EquipTBase.rotateAngleX = 1.42F;
				this.EquipTBase.rotateAngleY = 0F;
				this.EquipTBase.rotateAngleZ = -0.22F;
				this.EquipTBase.offsetX = 0.17F;
				this.EquipTBase.offsetY = 0.64F;
				this.EquipTBase_2.isHidden = false;
				this.EquipTBase_2.rotateAngleX = 1.42F;
				this.EquipTBase_2.rotateAngleY = 0F;
				this.EquipTBase_2.rotateAngleZ = 0.22F;
				this.EquipTBase_2.offsetX = -0.17F;
				this.EquipTBase_2.offsetY = 0.64F;
		  	}
	    }
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    //潛行跟蹲下動作
	    if (ent.getIsSneaking())
	    {
	    	GlStateManager.translate(0F, 0.01F, 0F);
	    	
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		  	this.Cloth03.rotateAngleX = -0.34F;
		  	this.Cloth03.offsetY = -0.2F;
		  	this.Cloth03.offsetZ = 0.03F;
		  	this.Cloth04.rotateAngleX = -0.27F;
		    //arm 
		  	this.ArmLeft01.rotateAngleX = -0.35F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
			this.ArmRight01.rotateAngleX = -0.35F;
			this.ArmRight01.rotateAngleZ = -0.2618F;
			//leg
			addk1 -= 0.94F;
			addk2 -= 0.94F;
			this.LegLeft01.rotateAngleZ = 0.2F;
			this.LegRight01.rotateAngleZ = -0.2F;
			//hair
			this.Hair01.rotateAngleX = this.Hair01.rotateAngleX * 0.5F + 0.4F;
			this.Hair02.rotateAngleX = this.Hair02.rotateAngleX * 0.75F + 0.25F;
			//equip
			this.EquipTBase.rotateAngleX = 0.48F;
			this.EquipTBase.rotateAngleY = 1.55F;
			this.EquipTBase.rotateAngleZ = 0F;
			this.EquipTBase.offsetX = 0F;
			this.EquipTBase.offsetY = 0F;
  		}//end if sneaking
  		
	    //坐下動作
	    if (ent.getIsSitting() && !ent.getIsRiding())
	    {
	    	if (ent.getTickExisted() % 512 > 256)
	    	{
	    		this.setFaceDamaged(ent);
		  		GlStateManager.translate(0F, -angleX * 0.05F - 0.1F, 0F);
			    //body
		    	this.Head.rotateAngleX *= 0.5F;
		    	this.Head.rotateAngleY *= 0.75F;
			    this.Head.rotateAngleX += 0.5F;
		    	this.BodyMain.rotateAngleX = 1.6F;
		    	this.Cloth03.rotateAngleX = -0.33F;
		    	this.Cloth03.offsetY = -0.23F;
		    	this.Cloth04.rotateAngleX = -0.12F;
		    	this.Cloth04.offsetY = -0.16F;
		    	this.Ahoke01.rotateAngleX += 0.38F;
			  	this.Ahoke01.rotateAngleY = 0.8F;
			  	this.Ahoke01.rotateAngleZ = 0.4F;
			  	this.Hair01.rotateAngleX -= 0.2F;
		    	this.Hair02.rotateAngleX -= 0.25F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -1.5F;
		    	this.ArmLeft01.rotateAngleZ = -2.3F;
		    	this.ArmRight01.rotateAngleX = -1.5F;
		    	this.ArmRight01.rotateAngleZ = 2.3F;
			    //leg
			    addk1 = -1.8F;
		    	addk2 = -1.8F;
			    this.LegLeft01.rotateAngleY = -0.1F - angleX * 0.02F;
			  	this.LegRight01.rotateAngleY = 0.1F + angleX * 0.02F;
	    	}
	    	else
	    	{
		    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
			    	GlStateManager.translate(0F, 0.26F, 0F);
			    	
			    	this.setFaceDamaged(ent);
			    	
			    	//body
			    	this.Head.rotateAngleX = 0.4F;
			    	this.Cloth03.rotateAngleX = -0.64F;
			    	this.Cloth03.offsetY = -0.17F;
			    	this.Cloth03.offsetZ = 0F;
			    	this.Cloth04.rotateAngleX = 0.29F;
			    	this.Cloth04.offsetY = -0.04F;
			    	this.Cloth04.offsetZ = 0.02F;
			    	this.Hair01.rotateAngleX -= 0.2F;
			    	this.Hair02.rotateAngleX -= 0.15F;
			    	this.Ahoke01.rotateAngleX -= 0.1F;
			    	//arm
			    	this.ArmLeft01.rotateAngleX = 0.4F;
			    	this.ArmLeft01.rotateAngleY = -2.96705972839036F;
			    	this.ArmLeft01.rotateAngleZ = -2.62F;
			    	this.ArmLeft02.rotateAngleX = 0.0F;
			    	this.ArmLeft02.rotateAngleY = 0.0F;
			    	this.ArmLeft02.rotateAngleZ = 1F;
			    	this.ArmLeft02.offsetX = 0F;
			    	this.ArmLeft02.offsetZ = 0F;
			    	this.ArmRight01.rotateAngleX = 0.5235987755982988F;
			    	this.ArmRight01.rotateAngleY = 2.96705972839036F;
			    	this.ArmRight01.rotateAngleZ = 2.62F;
			    	this.ArmRight02.rotateAngleX = 0.0F;
			    	this.ArmRight02.rotateAngleY = 0.0F;
			    	this.ArmRight02.rotateAngleZ = -1F;
			    	this.ArmRight02.offsetX = 0F;
			    	this.ArmRight02.offsetZ = 0F;
			    	//leg
			    	addk1 = -2.41309222380736F;
			    	addk2 = -2.2689280275926285F;
			    	this.LegLeft01.rotateAngleY = 0.0F;
			    	this.LegLeft01.rotateAngleZ = -0.27314402793711257F;
			    	this.LegLeft02.rotateAngleX = 1.4570008595648662F;
			    	this.LegLeft02.rotateAngleY = 0.0F;
			    	this.LegLeft02.rotateAngleZ = 0.0F;
			    	this.LegRight01.rotateAngleY = 0.0F;
			    	this.LegRight01.rotateAngleZ = 0.22759093446006054F;
			    	this.LegRight02.rotateAngleX = 1.0471975511965976F;
			    	this.LegRight02.rotateAngleY = 0.0F;
			    	this.LegRight02.rotateAngleZ = 0.0F;
			    	//equip
			    	this.EquipTBase.isHidden = true;
		    	}
		    	else
		    	{
	    			GlStateManager.translate(0F, 0.24F, 0F);
	    	
			    	//body
			    	this.Head.rotateAngleX -= 0.7F;
			    	this.BodyMain.rotateAngleX = 0.35F;
			    	this.Hair01.rotateAngleX += 0.3F;
			    	this.Hair02.rotateAngleX += 0.3F;
			    	this.Cloth03.rotateAngleX = -0.32F;
			    	this.Cloth03.offsetY = -0.05F;
			    	this.Cloth04.rotateAngleX = -0.21F;
			    	//arm
					this.ArmLeft01.rotateAngleX = -0.5235987755982988F;
					this.ArmLeft01.rotateAngleY = 0.0F;
					this.ArmLeft01.rotateAngleZ = 0.3490658503988659F;
				    this.ArmLeft02.rotateAngleX = 0F;
				    this.ArmLeft02.rotateAngleZ = 0F;
				    this.ArmLeft02.offsetX = 0F;
				    this.ArmLeft02.offsetZ = 0F;
					this.ArmRight01.rotateAngleX = -0.5235987755982988F;
					this.ArmRight01.rotateAngleY = 0.0F;
					this.ArmRight01.rotateAngleZ = -0.3490658503988659F;
					this.ArmRight02.rotateAngleX = 0F;
					this.ArmRight02.rotateAngleZ = 0F;
					this.ArmRight02.offsetX = 0F;
					this.ArmRight02.offsetZ = 0F;
			    	//leg
			    	addk1 = -1.4486232791552935F;
			    	addk2 = -1.4486232791552935F;
					this.LegLeft01.rotateAngleY = -0.5235987755982988F;
					this.LegLeft01.rotateAngleZ = -1.3962634015954636F;
					this.LegLeft02.rotateAngleX = 2.1816615649929116F;
					this.LegLeft02.rotateAngleY = 0.0F;
					this.LegLeft02.rotateAngleZ = 0.0F;
					this.LegLeft02.offsetX = 0F;
					this.LegLeft02.offsetZ = 0.37F;
					this.LegRight01.rotateAngleY = 0.5235987755982988F;
					this.LegRight01.rotateAngleZ = 1.3962634015954636F;
					this.LegRight02.rotateAngleX = 2.1816615649929116F;
					this.LegRight02.rotateAngleY = 0.0F;
					this.LegRight02.rotateAngleZ = 0.0F;
					this.LegRight02.offsetX = 0F;
					this.LegRight02.offsetZ = 0.37F;
		    	}
	    	}
  		}//end sitting
	    
	    //騎乘專屬坐騎動作
	    if (ent.getIsRiding())
	    {
	    	//player mount
	    	if (((Entity)ent).getRidingEntity() instanceof EntityPlayer)
	    	{
	    		//body
    			this.Head.rotateAngleY *= 0.25F;
    			
	    		if (ent.getIsSitting())
	    		{
	    			if (((Entity)ent).getRidingEntity().isSneaking())
	    			{
	    				GlStateManager.translate(0F, 0.33F, 0.27F);
	    			}
	    			else
	    			{
	    				GlStateManager.translate(0F, 0.24F, 0.27F);
	    			}
	    	    	
	    			//cloth
	    			this.Cloth02.rotateAngleX = -0.13F;
	    			this.Cloth02.offsetY = -0.11F;
	    			this.Cloth03.rotateAngleX = -0.07F;
	    			this.Cloth03.offsetY = -0.11F;
	    			this.Cloth04.rotateAngleX = -0.11F;
	    			this.Cloth04.offsetY = -0.08F;
	    			//arm
	    	    	this.ArmLeft01.rotateAngleX = -1.2F;
	    	    	this.ArmLeft01.rotateAngleY = -0.3F;
	    	    	this.ArmRight01.rotateAngleX = -1.2F;
	    	    	this.ArmRight01.rotateAngleY = 0.3F;
	    	    	//leg
	    	    	addk1 = -1.66F;
	    	    	addk2 = -1.66F;
	    	    	this.LegLeft01.rotateAngleY = -0.6F;
	    	    	this.LegRight01.rotateAngleY = 0.6F;
		    	}//end if sitting
		    	else
		    	{
		    		if (((Entity)ent).getRidingEntity().isSneaking())
	    			{
		    			GlStateManager.translate(0F, 0.16F, 0.17F);
	    			}
	    			else
	    			{
	    				GlStateManager.translate(0F, 0.07F, 0.17F);
	    			}
		    		
		    	    //body
		        	this.Head.rotateAngleX *= 0.5F;
		        	this.Head.rotateAngleY *= 0.75F;
		    	    this.Head.rotateAngleX -= 1.1F;
		        	this.BodyMain.rotateAngleX = 1.5F;
		    		this.Cloth02.offsetY = -0.11F;
		    		this.Cloth03.rotateAngleX = -0.07F;
		    		this.Cloth03.offsetY = -0.11F;
		    		this.Cloth04.rotateAngleX = -0.11F;
		    		this.Cloth04.offsetY = -0.08F;
		        	//hair
		        	this.Ahoke01.rotateAngleX += 0.38F;
		    	  	this.Ahoke01.rotateAngleY = 0.8F;
		    	  	this.Ahoke01.rotateAngleZ = 0.4F;
		    	  	this.Hair01.rotateAngleX += 0.4F;
		        	this.Hair02.rotateAngleX += 0.2F;
		        	//arm
		        	this.ArmLeft01.rotateAngleX = -1.39F;
		        	this.ArmLeft01.rotateAngleY = -1.09F;
		        	this.ArmLeft02.rotateAngleX = -1.18F;
		        	this.ArmRight01.rotateAngleX = -1.39F;
		        	this.ArmRight01.rotateAngleY = 1.09F;
		        	this.ArmRight02.rotateAngleX = -1.18F;
		    	    //leg
		    	    addk1 = -1.7F;
		        	addk2 = -1.7F;
		    	    this.LegLeft01.rotateAngleY = -0.2F;
		    	  	this.LegRight01.rotateAngleY = 0.2F;
		    	  	//equip
		    	  	this.EquipTBase.rotateAngleX = 1.29F;
		    	}
	    	}
	    	//normal mount
	    	else
	    	{
	    		if (ent.getIsSitting())
	    		{
	    			if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			    	{
	    				this.setFaceDamaged(ent);
	    		  		GlStateManager.translate(0F, -angleX * 0.05F - 0.16F, 0F);
	    			    //body
	    		    	this.Head.rotateAngleX *= 0.5F;
	    		    	this.Head.rotateAngleY *= 0.75F;
	    			    this.Head.rotateAngleX += 0.5F;
	    		    	this.BodyMain.rotateAngleX = 1.6F;
	    		    	this.Cloth03.rotateAngleX = -0.33F;
	    		    	this.Cloth03.offsetY = -0.23F;
	    		    	this.Cloth04.rotateAngleX = -0.12F;
	    		    	this.Cloth04.offsetY = -0.16F;
	    		    	this.Ahoke01.rotateAngleX += 0.38F;
	    			  	this.Ahoke01.rotateAngleY = 0.8F;
	    			  	this.Ahoke01.rotateAngleZ = 0.4F;
	    			  	this.Hair01.rotateAngleX -= 0.2F;
	    		    	this.Hair02.rotateAngleX -= 0.25F;
	    		    	//arm
	    		    	this.ArmLeft01.rotateAngleX = -1.5F;
	    		    	this.ArmLeft01.rotateAngleZ = -2.3F;
	    		    	this.ArmRight01.rotateAngleX = -1.5F;
	    		    	this.ArmRight01.rotateAngleZ = 2.3F;
	    			    //leg
	    			    addk1 = -1.8F;
	    		    	addk2 = -1.8F;
	    			    this.LegLeft01.rotateAngleY = -0.1F - angleX * 0.02F;
	    			  	this.LegRight01.rotateAngleY = 0.1F + angleX * 0.02F;
			    	}
			    	else
			    	{
			    		GlStateManager.translate(0F, 0.24F, 0F);
				    	
				    	this.setFaceDamaged(ent);
				    	
				    	//body
				    	this.Head.rotateAngleX = 0.4F;
				    	this.Cloth03.rotateAngleX = -0.64F;
				    	this.Cloth03.offsetY = -0.17F;
				    	this.Cloth03.offsetZ = 0F;
				    	this.Cloth04.rotateAngleX = 0.29F;
				    	this.Cloth04.offsetY = -0.04F;
				    	this.Cloth04.offsetZ = 0.02F;
				    	this.Hair01.rotateAngleX -= 0.2F;
				    	this.Hair02.rotateAngleX -= 0.15F;
				    	this.Ahoke01.rotateAngleX -= 0.1F;
				    	//arm
				    	this.ArmLeft01.rotateAngleX = 0.4F;
				    	this.ArmLeft01.rotateAngleY = -2.96705972839036F;
				    	this.ArmLeft01.rotateAngleZ = -2.62F;
				    	this.ArmLeft02.rotateAngleX = 0.0F;
				    	this.ArmLeft02.rotateAngleY = 0.0F;
				    	this.ArmLeft02.rotateAngleZ = 1F;
				    	this.ArmLeft02.offsetX = 0F;
				    	this.ArmLeft02.offsetZ = 0F;
				    	this.ArmRight01.rotateAngleX = 0.5235987755982988F;
				    	this.ArmRight01.rotateAngleY = 2.96705972839036F;
				    	this.ArmRight01.rotateAngleZ = 2.62F;
				    	this.ArmRight02.rotateAngleX = 0.0F;
				    	this.ArmRight02.rotateAngleY = 0.0F;
				    	this.ArmRight02.rotateAngleZ = -1F;
				    	this.ArmRight02.offsetX = 0F;
				    	this.ArmRight02.offsetZ = 0F;
				    	//leg
				    	addk1 = -2.41309222380736F;
				    	addk2 = -2.2689280275926285F;
				    	this.LegLeft01.rotateAngleY = 0.0F;
				    	this.LegLeft01.rotateAngleZ = -0.27314402793711257F;
				    	this.LegLeft02.rotateAngleX = 1.4570008595648662F;
				    	this.LegLeft02.rotateAngleY = 0.0F;
				    	this.LegLeft02.rotateAngleZ = 0.0F;
				    	this.LegRight01.rotateAngleY = 0.0F;
				    	this.LegRight01.rotateAngleZ = 0.22759093446006054F;
				    	this.LegRight02.rotateAngleX = 1.0471975511965976F;
				    	this.LegRight02.rotateAngleY = 0.0F;
				    	this.LegRight02.rotateAngleZ = 0.0F;
			    	}
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.26F, 0F);
			    	
			    	//body
			    	this.Head.rotateAngleX -= 0.7F;
			    	this.BodyMain.rotateAngleX = 0.35F;
			    	this.Hair01.rotateAngleX += 0.3F;
			    	this.Hair02.rotateAngleX += 0.3F;
			    	this.Cloth03.rotateAngleX = -0.32F;
			    	this.Cloth03.offsetY = -0.05F;
			    	this.Cloth04.rotateAngleX = -0.21F;
			    	//arm
					this.ArmLeft01.rotateAngleX = -0.5235987755982988F;
					this.ArmLeft01.rotateAngleY = 0.0F;
					this.ArmLeft01.rotateAngleZ = 0.3490658503988659F;
				    this.ArmLeft02.rotateAngleX = 0F;
				    this.ArmLeft02.rotateAngleZ = 0F;
				    this.ArmLeft02.offsetX = 0F;
				    this.ArmLeft02.offsetZ = 0F;
					this.ArmRight01.rotateAngleX = -0.5235987755982988F;
					this.ArmRight01.rotateAngleY = 0.0F;
					this.ArmRight01.rotateAngleZ = -0.3490658503988659F;
					this.ArmRight02.rotateAngleX = 0F;
					this.ArmRight02.rotateAngleZ = 0F;
					this.ArmRight02.offsetX = 0F;
					this.ArmRight02.offsetZ = 0F;
			    	//leg
			    	addk1 = -1.4486232791552935F;
			    	addk2 = -1.4486232791552935F;
					this.LegLeft01.rotateAngleY = -0.5235987755982988F;
					this.LegLeft01.rotateAngleZ = -1.3962634015954636F;
					this.LegLeft02.rotateAngleX = 2.1816615649929116F;
					this.LegLeft02.rotateAngleY = 0.0F;
					this.LegLeft02.rotateAngleZ = 0.0F;
					this.LegLeft02.offsetX = 0F;
					this.LegLeft02.offsetZ = 0.37F;
					this.LegRight01.rotateAngleY = 0.5235987755982988F;
					this.LegRight01.rotateAngleZ = 1.3962634015954636F;
					this.LegRight02.rotateAngleX = 2.1816615649929116F;
					this.LegRight02.rotateAngleY = 0.0F;
					this.LegRight02.rotateAngleZ = 0.0F;
					this.LegRight02.offsetX = 0F;
					this.LegRight02.offsetZ = 0.37F;
		    	}
	    	}
	    }//end ridding
	    
	    //攻擊動作    
	    int atktime = ent.getAttackTick();
	    if (atktime > 41)
	    {
	    	this.EquipTBase.isHidden = false;
	    	this.EquipTBase_2.isHidden = false;
	    	this.EquipTBase.offsetX = 0F;
			this.EquipTBase.offsetY = 0F;
	    	
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
	    	this.EquipTBase.isHidden = false;
	    	this.EquipTBase_2.isHidden = false;
	    	this.EquipTBase.offsetX = 0F;
			this.EquipTBase.offsetY = 0F;
	    	
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
	  	float f6 = ent.getSwingTime(f2 % 1F);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.3F;
			this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight01.rotateAngleZ = -0.1F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	        this.ArmRight02.rotateAngleX = 0F;
	        this.ArmRight02.rotateAngleZ = 0F;
	  	}
	  	
	    //移動頭髮避免穿過身體
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
	
}