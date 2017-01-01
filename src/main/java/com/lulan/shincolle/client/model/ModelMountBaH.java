package com.lulan.shincolle.client.model;

import java.util.Random;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMountBaH - PinkaLulan 2015/4/12
 * Created using Tabula 4.1.1
 */
public class ModelMountBaH extends ModelBase
{
    public ModelRenderer BodyMain;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
    public ModelRenderer Neck;
    public ModelRenderer ChestCannon01a;
    public ModelRenderer ChestCannon02a;
    public ModelRenderer ChestCannon03a;
    public ModelRenderer ChestCannon04a;
    public ModelRenderer ChestCannon05a;
    public ModelRenderer ChestCannon06;
    public ModelRenderer EquipBaseL;
    public ModelRenderer EquipBaseR;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer LefLeft02;
    public ModelRenderer Head;
    public ModelRenderer HeadTooth;
    public ModelRenderer Jaw;
    public ModelRenderer HeadBack01;
    public ModelRenderer HeadBack02;
    public ModelRenderer HeadBack03;
    public ModelRenderer JawTooth;
    public ModelRenderer Tongue;
    public ModelRenderer ChestCannon01b;
    public ModelRenderer ChestCannon02b;
    public ModelRenderer ChestCannon03b;
    public ModelRenderer ChestCannon04b;
    public ModelRenderer ChestCannon05b;
    public ModelRenderer EquipL01;
    public ModelRenderer EquipL03;
    public ModelRenderer EquipL02;
    public ModelRenderer EquipCannon01;
    public ModelRenderer EquipCannon02;
    public ModelRenderer EquipCannon03;
    public ModelRenderer ChestCannonL01a;
    public ModelRenderer ChestCannonL01b;
    public ModelRenderer EquipR01;
    public ModelRenderer EquipR03;
    public ModelRenderer EquipR02;
    public ModelRenderer EquipCannon01_1;
    public ModelRenderer EquipCannon02_1;
    public ModelRenderer EquipCannon03_1;
    public ModelRenderer ChestCannonR01a;
    public ModelRenderer ChestCannonR01b;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowEquipBaseL;
    public ModelRenderer GlowEquipBaseR;
    public ModelRenderer GlowEquipL01;
    public ModelRenderer GlowEquipL02;
    public ModelRenderer GlowEquipR01;
    public ModelRenderer GlowEquipR02;
    public ModelRenderer GlowChestCannonL01a;
    public ModelRenderer GlowChestCannonR01a;
    public ModelRenderer GlowChestCannon01a;
    public ModelRenderer GlowChestCannon02a;
    public ModelRenderer GlowChestCannon03a;
    public ModelRenderer GlowChestCannon04a;
    public ModelRenderer GlowChestCannon05a;
    public ModelRenderer GlowEquipL03;
    public ModelRenderer GlowEquipR03;

    private Random rand = new Random();
    
    
    public ModelMountBaH()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.ChestCannon06 = new ModelRenderer(this, 0, 0);
        this.ChestCannon06.setRotationPoint(-4.0F, -1.0F, -7.0F);
        this.ChestCannon06.addBox(0.0F, 0.0F, 0.0F, 9, 10, 3, 0.0F);
        this.setRotateAngle(ChestCannon06, 0.091106186954104F, 0.0F, 0.0F);
        this.HeadBack01 = new ModelRenderer(this, 45, 6);
        this.HeadBack01.setRotationPoint(6.0F, -2.0F, 4.0F);
        this.HeadBack01.addBox(-3.5F, -3.5F, 0.0F, 7, 7, 12, 0.0F);
        this.setRotateAngle(HeadBack01, 0.6829473363053812F, 0.4363323129985824F, 0.0F);
        this.ChestCannon03b = new ModelRenderer(this, 84, 0);
        this.ChestCannon03b.setRotationPoint(4.5F, 2.5F, 1.5F);
        this.ChestCannon03b.addBox(0.0F, 0.0F, -9.0F, 2, 2, 9, 0.0F);
        this.setRotateAngle(ChestCannon03b, -0.6373942428283291F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 20);
        this.Head.setRotationPoint(0.0F, -12.0F, -2.6F);
        this.Head.addBox(-9.5F, -9.0F, -16.0F, 19, 12, 19, 0.0F);
        this.setRotateAngle(Head, -0.8726646259971648F, 0.0F, 0.0F);
        this.EquipCannon02 = new ModelRenderer(this, 90, 0);
        this.EquipCannon02.setRotationPoint(-1.5F, -7.5F, -8.0F);
        this.EquipCannon02.addBox(0.0F, 0.0F, -16.0F, 3, 3, 16, 0.0F);
        this.setRotateAngle(EquipCannon02, -0.4553564018453205F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 101);
        this.LegRight01.setRotationPoint(-5.0F, 16.0F, 7.0F);
        this.LegRight01.addBox(-4.5F, 0.0F, -4.5F, 9, 18, 9, 0.0F);
        this.setRotateAngle(LegRight01, -1.6755160819145563F, 0.20943951023931953F, 0.0F);
        this.EquipR01 = new ModelRenderer(this, 0, 0);
        this.EquipR01.setRotationPoint(0.0F, -10.0F, 1.0F);
        this.EquipR01.addBox(-7.0F, 0.0F, -7.0F, 14, 4, 14, 0.0F);
        this.EquipR02 = new ModelRenderer(this, 0, 0);
        this.EquipR02.setRotationPoint(0.0F, 1.8F, 0.0F);
        this.EquipR02.addBox(-9.0F, -9.0F, -9.0F, 18, 9, 20, 0.0F);
        this.setRotateAngle(EquipR02, -0.3141592653589793F, 0.13962634015954636F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 51);
        this.BodyMain.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.BodyMain.addBox(-15.0F, -11.0F, -5.0F, 30, 20, 18, 0.0F);
        this.setRotateAngle(BodyMain, 1.0471975511965976F, 0.0F, 0.0F);
        this.EquipCannon03_1 = new ModelRenderer(this, 90, 0);
        this.EquipCannon03_1.setRotationPoint(-7.0F, -7.5F, -8.0F);
        this.EquipCannon03_1.addBox(0.0F, 0.0F, -16.0F, 3, 3, 16, 0.0F);
        this.setRotateAngle(EquipCannon03_1, -0.27314402793711257F, 0.08726646259971647F, 0.0F);
        this.ChestCannon01a = new ModelRenderer(this, 0, 0);
        this.ChestCannon01a.setRotationPoint(6.0F, -8.0F, -7.0F);
        this.ChestCannon01a.addBox(0.0F, 0.0F, 0.0F, 7, 8, 3, 0.0F);
        this.setRotateAngle(ChestCannon01a, -0.136659280431156F, -0.091106186954104F, 0.0F);
        this.ChestCannonR01b = new ModelRenderer(this, 84, 0);
        this.ChestCannonR01b.setRotationPoint(2.5F, 2.5F, 1.0F);
        this.ChestCannonR01b.addBox(0.0F, 0.0F, -9.0F, 2, 2, 9, 0.0F);
        this.setRotateAngle(ChestCannonR01b, 0.18203784098300857F, -0.18203784098300857F, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 96);
        this.Butt.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.Butt.addBox(-11.0F, 0.0F, -2.5F, 22, 18, 14, 0.0F);
        this.setRotateAngle(Butt, -0.5009094953223726F, 0.0F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 0, 92);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(15.0F, 1.0F, 2.0F);
        this.ArmLeft01.addBox(-1.0F, -7.0F, -7.0F, 14, 22, 14, 0.0F);
        this.setRotateAngle(ArmLeft01, -0.8726646259971648F, -0.20943951023931953F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 102);
        this.LegRight02.setRotationPoint(0.0F, 18.0F, -2.0F);
        this.LegRight02.addBox(-4.0F, 0.0F, -2.0F, 8, 18, 8, 0.0F);
        this.setRotateAngle(LegRight02, 1.7453292519943295F, 0.0F, 0.0F);
        this.ChestCannon01b = new ModelRenderer(this, 84, 0);
        this.ChestCannon01b.setRotationPoint(2.5F, 3.5F, 1.0F);
        this.ChestCannon01b.addBox(0.0F, 0.0F, -9.0F, 2, 2, 9, 0.0F);
        this.setRotateAngle(ChestCannon01b, -0.4553564018453205F, -0.5462880558742251F, 0.0F);
        this.Jaw = new ModelRenderer(this, 77, 25);
        this.Jaw.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.Jaw.addBox(-8.5F, 0.0F, -14.5F, 17, 9, 17, 0.0F);
        this.setRotateAngle(Jaw, 0.8726646259971648F, 0.0F, 0.0F);
        this.HeadBack02 = new ModelRenderer(this, 45, 6);
        this.HeadBack02.setRotationPoint(0.0F, -3.0F, 5.0F);
        this.HeadBack02.addBox(-3.5F, -3.5F, 0.0F, 7, 7, 12, 0.0F);
        this.setRotateAngle(HeadBack02, 0.7740535232594852F, 0.08726646259971647F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 0, 89);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(6.0F, 15.0F, 7.0F);
        this.ArmLeft02.addBox(-6.5F, 0.0F, -13.0F, 13, 26, 13, 0.0F);
        this.setRotateAngle(ArmLeft02, -0.6981317007977318F, 0.0F, 0.0F);
        this.ChestCannonR01a = new ModelRenderer(this, 0, 0);
        this.ChestCannonR01a.setRotationPoint(5.5F, 2.0F, 1.0F);
        this.ChestCannonR01a.addBox(0.0F, 0.0F, 0.0F, 7, 8, 3, 0.0F);
        this.setRotateAngle(ChestCannonR01a, -0.136659280431156F, -1.4114477660878142F, 0.0F);
        this.ChestCannon05b = new ModelRenderer(this, 84, 0);
        this.ChestCannon05b.setRotationPoint(2.5F, 2.5F, 1.0F);
        this.ChestCannon05b.addBox(0.0F, 0.0F, -9.0F, 2, 2, 9, 0.0F);
        this.setRotateAngle(ChestCannon05b, -0.7285004297824331F, 0.3389429407372988F, 0.0F);
        this.ChestCannon04a = new ModelRenderer(this, 0, 0);
        this.ChestCannon04a.setRotationPoint(-10.0F, -1.0F, -7.0F);
        this.ChestCannon04a.addBox(0.0F, 0.0F, 0.0F, 9, 7, 3, 0.0F);
        this.setRotateAngle(ChestCannon04a, 0.18203784098300857F, 0.091106186954104F, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 101);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(5.0F, 16.0F, 7.0F);
        this.LegLeft01.addBox(-4.5F, 0.0F, -4.5F, 9, 18, 9, 0.0F);
        this.setRotateAngle(LegLeft01, -1.6755160819145563F, -0.20943951023931953F, 0.0F);
        this.EquipBaseL = new ModelRenderer(this, 0, 0);
        this.EquipBaseL.setRotationPoint(20.0F, -3.0F, 2.0F);
        this.EquipBaseL.addBox(-9.0F, -6.0F, -8.5F, 18, 5, 18, 0.0F);
        this.setRotateAngle(EquipBaseL, -0.7740535232594852F, 0.0F, 0.17453292519943295F);
        this.EquipCannon01 = new ModelRenderer(this, 90, 0);
        this.EquipCannon01.setRotationPoint(4.0F, -7.5F, -8.0F);
        this.EquipCannon01.addBox(0.0F, 0.0F, -16.0F, 3, 3, 16, 0.0F);
        this.setRotateAngle(EquipCannon01, -0.136659280431156F, -0.08726646259971647F, 0.0F);
        this.EquipBaseR = new ModelRenderer(this, 0, 0);
        this.EquipBaseR.setRotationPoint(-20.0F, -3.0F, 2.0F);
        this.EquipBaseR.addBox(-9.0F, -6.0F, -8.5F, 18, 5, 18, 0.0F);
        this.setRotateAngle(EquipBaseR, -0.7740535232594852F, 0.0F, -0.17453292519943295F);
        this.ChestCannonL01b = new ModelRenderer(this, 84, 0);
        this.ChestCannonL01b.setRotationPoint(2.5F, 2.5F, 1.0F);
        this.ChestCannonL01b.addBox(0.0F, 0.0F, -9.0F, 2, 2, 9, 0.0F);
        this.setRotateAngle(ChestCannonL01b, 0.136659280431156F, 0.091106186954104F, 0.0F);
        this.ChestCannon03a = new ModelRenderer(this, 0, 0);
        this.ChestCannon03a.setRotationPoint(-5.0F, -6.4F, -7.0F);
        this.ChestCannon03a.addBox(0.0F, 0.0F, 0.0F, 12, 9, 4, 0.0F);
        this.setRotateAngle(ChestCannon03a, -0.18203784098300857F, -0.02024581932313422F, 0.0F);
        this.ChestCannon02a = new ModelRenderer(this, 0, 0);
        this.ChestCannon02a.setRotationPoint(4.6F, -2.4F, -7.0F);
        this.ChestCannon02a.addBox(0.0F, 0.0F, 0.0F, 7, 8, 3, 0.0F);
        this.setRotateAngle(ChestCannon02a, 0.091106186954104F, -0.091106186954104F, 0.0F);
        this.EquipL02 = new ModelRenderer(this, 0, 0);
        this.EquipL02.setRotationPoint(0.0F, 1.8F, 0.0F);
        this.EquipL02.addBox(-9.0F, -9.0F, -9.0F, 18, 9, 20, 0.0F);
        this.setRotateAngle(EquipL02, -0.2617993877991494F, -0.13962634015954636F, 0.0F);
        this.EquipCannon01_1 = new ModelRenderer(this, 90, 0);
        this.EquipCannon01_1.setRotationPoint(4.0F, -7.5F, -8.0F);
        this.EquipCannon01_1.addBox(0.0F, 0.0F, -16.0F, 3, 3, 16, 0.0F);
        this.setRotateAngle(EquipCannon01_1, 0.0F, -0.08726646259971647F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 0, 89);
        this.ArmRight02.setRotationPoint(-6.0F, 15.0F, 7.0F);
        this.ArmRight02.addBox(-6.5F, 0.0F, -13.0F, 13, 26, 13, 0.0F);
        this.setRotateAngle(ArmRight02, -0.6981317007977318F, 0.0F, 0.0F);
        this.EquipR03 = new ModelRenderer(this, 0, 0);
        this.EquipR03.setRotationPoint(-7.0F, -6.0F, 7.0F);
        this.EquipR03.addBox(0.0F, 0.0F, 0.0F, 5, 13, 10, 0.0F);
        this.setRotateAngle(EquipR03, 0.0F, -3.141592653589793F, 0.3141592653589793F);
        this.ArmRight01 = new ModelRenderer(this, 0, 92);
        this.ArmRight01.setRotationPoint(-15.0F, 0.0F, 2.0F);
        this.ArmRight01.addBox(-13.0F, -7.0F, -7.0F, 14, 22, 14, 0.0F);
        this.setRotateAngle(ArmRight01, -0.8726646259971648F, 0.20943951023931953F, 0.0F);
        this.ChestCannon04b = new ModelRenderer(this, 84, 0);
        this.ChestCannon04b.setRotationPoint(2.5F, 2.5F, 1.0F);
        this.ChestCannon04b.addBox(0.0F, 0.0F, -9.0F, 2, 2, 9, 0.0F);
        this.setRotateAngle(ChestCannon04b, -0.27314402793711257F, 0.22759093446006054F, 0.0F);
        this.EquipL01 = new ModelRenderer(this, 0, 0);
        this.EquipL01.setRotationPoint(0.0F, -10.0F, 1.0F);
        this.EquipL01.addBox(-7.0F, 0.0F, -7.0F, 14, 4, 14, 0.0F);
        this.HeadBack03 = new ModelRenderer(this, 45, 6);
        this.HeadBack03.setRotationPoint(-6.0F, -3.0F, 5.0F);
        this.HeadBack03.addBox(-3.5F, -3.5F, 0.0F, 7, 7, 12, 0.0F);
        this.setRotateAngle(HeadBack03, 0.5918411493512771F, -0.5009094953223726F, 0.0F);
        this.EquipCannon03 = new ModelRenderer(this, 90, 0);
        this.EquipCannon03.setRotationPoint(-7.0F, -7.5F, -8.0F);
        this.EquipCannon03.addBox(0.0F, 0.0F, -16.0F, 3, 3, 16, 0.0F);
        this.setRotateAngle(EquipCannon03, -0.27314402793711257F, 0.08726646259971647F, 0.0F);
        this.ChestCannonL01a = new ModelRenderer(this, 0, 0);
        this.ChestCannonL01a.setRotationPoint(5.5F, 2.0F, 1.0F);
        this.ChestCannonL01a.addBox(0.0F, 0.0F, 0.0F, 7, 8, 3, 0.0F);
        this.setRotateAngle(ChestCannonL01a, -0.136659280431156F, -1.4114477660878142F, 0.0F);
        this.HeadTooth = new ModelRenderer(this, 68, 91);
        this.HeadTooth.setRotationPoint(0.0F, 1.0F, -7.0F);
        this.HeadTooth.addBox(-7.5F, 0.0F, -7.5F, 15, 4, 15, 0.0F);
        this.setRotateAngle(HeadTooth, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipL03 = new ModelRenderer(this, 0, 0);
        this.EquipL03.setRotationPoint(6.0F, -6.0F, -2.0F);
        this.EquipL03.addBox(0.0F, 0.0F, 0.0F, 5, 13, 10, 0.0F);
        this.setRotateAngle(EquipL03, 0.0F, 0.0F, -0.3141592653589793F);
        this.Tongue = new ModelRenderer(this, 82, 54);
        this.Tongue.setRotationPoint(0.0F, -3.1F, 1.0F);
        this.Tongue.addBox(-5.0F, 0.0F, -13.0F, 10, 2, 13, 0.0F);
        this.ChestCannon05a = new ModelRenderer(this, 0, 0);
        this.ChestCannon05a.setRotationPoint(-13.0F, -8.0F, -6.0F);
        this.ChestCannon05a.addBox(0.0F, 0.0F, 0.0F, 9, 7, 3, 0.0F);
        this.setRotateAngle(ChestCannon05a, -0.091106186954104F, 0.136659280431156F, 0.022126174344515567F);
        this.ChestCannon02b = new ModelRenderer(this, 84, 0);
        this.ChestCannon02b.setRotationPoint(2.5F, 2.5F, 1.0F);
        this.ChestCannon02b.addBox(0.0F, 0.0F, -9.0F, 2, 2, 9, 0.0F);
        this.setRotateAngle(ChestCannon02b, 0.045553093477052F, -0.27314402793711257F, 0.0F);
        this.Neck = new ModelRenderer(this, 0, 0);
        this.Neck.setRotationPoint(0.0F, -8.0F, 6.0F);
        this.Neck.addBox(-9.0F, -18.0F, -6.0F, 18, 18, 10, 0.0F);
        this.setRotateAngle(Neck, -0.2617993877991494F, 0.0F, 0.0F);
        this.LefLeft02 = new ModelRenderer(this, 0, 102);
        this.LefLeft02.mirror = true;
        this.LefLeft02.setRotationPoint(0.0F, 18.0F, -2.0F);
        this.LefLeft02.addBox(-4.0F, 0.0F, -2.0F, 8, 18, 8, 0.0F);
        this.setRotateAngle(LefLeft02, 1.7453292519943295F, 0.0F, 0.0F);
        this.JawTooth = new ModelRenderer(this, 68, 91);
        this.JawTooth.setRotationPoint(0.0F, -1.5F, -0.5F);
        this.JawTooth.addBox(-7.5F, 0.0F, -13.0F, 15, 3, 15, 0.0F);
        this.setRotateAngle(JawTooth, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipCannon02_1 = new ModelRenderer(this, 90, 0);
        this.EquipCannon02_1.setRotationPoint(-1.5F, -7.5F, -8.0F);
        this.EquipCannon02_1.addBox(0.0F, 0.0F, -16.0F, 3, 3, 16, 0.0F);
        this.setRotateAngle(EquipCannon02_1, -0.18203784098300857F, 0.0F, 0.0F);
        this.BodyMain.addChild(this.ChestCannon06);
        this.Neck.addChild(this.Head);
        this.Butt.addChild(this.LegRight01);
        this.EquipBaseR.addChild(this.EquipR01);
        this.EquipR01.addChild(this.EquipR02);
        this.BodyMain.addChild(this.ChestCannon01a);
        this.BodyMain.addChild(this.Butt);
        this.BodyMain.addChild(this.ArmLeft01);
        this.LegRight01.addChild(this.LegRight02);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipR03.addChild(this.ChestCannonR01a);
        this.BodyMain.addChild(this.ChestCannon04a);
        this.Butt.addChild(this.LegLeft01);
        this.BodyMain.addChild(this.EquipBaseL);
        this.BodyMain.addChild(this.EquipBaseR);
        this.BodyMain.addChild(this.ChestCannon03a);
        this.BodyMain.addChild(this.ChestCannon02a);
        this.EquipL01.addChild(this.EquipL02); 
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipBaseR.addChild(this.EquipR03);
        this.BodyMain.addChild(this.ArmRight01);
        this.EquipBaseL.addChild(this.EquipL01);
        this.EquipL03.addChild(this.ChestCannonL01a);
        this.EquipBaseL.addChild(this.EquipL03);
        this.BodyMain.addChild(this.ChestCannon05a);
        this.BodyMain.addChild(this.Neck);
        this.LegLeft01.addChild(this.LefLeft02); 
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -10.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, 1.0471975511965976F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -8.0F, 6.0F);
        this.setRotateAngle(GlowNeck, -0.2617993877991494F, 0.0F, 0.0F);
        
        this.GlowEquipBaseL = new ModelRenderer(this, 0, 0);
        this.GlowEquipBaseL.setRotationPoint(20.0F, -3.0F, 2.0F);
        this.setRotateAngle(GlowEquipBaseL, -0.7740535232594852F, 0.0F, 0.17453292519943295F);
        this.GlowEquipL01 = new ModelRenderer(this, 0, 0);
        this.GlowEquipL01.setRotationPoint(0.0F, -10.0F, 1.0F);
        this.GlowEquipL02 = new ModelRenderer(this, 0, 0);
        this.GlowEquipL02.setRotationPoint(0.0F, 1.8F, 0.0F);
        this.setRotateAngle(GlowEquipL02, -0.2617993877991494F, -0.13962634015954636F, 0.0F);        
        this.GlowEquipBaseR = new ModelRenderer(this, 0, 0);
        this.GlowEquipBaseR.setRotationPoint(-20.0F, -3.0F, 2.0F);
        this.setRotateAngle(GlowEquipBaseR, -0.7740535232594852F, 0.0F, -0.17453292519943295F);
        this.GlowEquipR01 = new ModelRenderer(this, 0, 0);
        this.GlowEquipR01.setRotationPoint(0.0F, -10.0F, 1.0F);
        this.GlowEquipR02 = new ModelRenderer(this, 0, 0);
        this.GlowEquipR02.setRotationPoint(0.0F, 1.8F, 0.0F);
        this.setRotateAngle(GlowEquipR02, -0.3141592653589793F, 0.13962634015954636F, 0.0F);
        
        this.GlowChestCannonL01a = new ModelRenderer(this, 0, 0);
        this.GlowChestCannonL01a.setRotationPoint(5.5F, 2.0F, 1.0F);
        this.setRotateAngle(GlowChestCannonL01a, -0.136659280431156F, -1.4114477660878142F, 0.0F);
        this.GlowChestCannonR01a = new ModelRenderer(this, 0, 0);
        this.GlowChestCannonR01a.setRotationPoint(5.5F, 2.0F, 1.0F);
        this.setRotateAngle(GlowChestCannonR01a, -0.136659280431156F, -1.4114477660878142F, 0.0F);
        this.GlowChestCannon01a = new ModelRenderer(this, 0, 0);
        this.GlowChestCannon01a.setRotationPoint(6.0F, -8.0F, -7.0F);
        this.setRotateAngle(GlowChestCannon01a, -0.136659280431156F, -0.091106186954104F, 0.0F);
        this.GlowChestCannon02a = new ModelRenderer(this, 0, 0);
        this.GlowChestCannon02a.setRotationPoint(4.6F, -2.4F, -7.0F);
        this.setRotateAngle(GlowChestCannon02a, 0.091106186954104F, -0.091106186954104F, 0.0F);
        this.GlowChestCannon03a = new ModelRenderer(this, 0, 0);
        this.GlowChestCannon03a.setRotationPoint(-5.0F, -6.4F, -7.0F);
        this.setRotateAngle(GlowChestCannon03a, -0.18203784098300857F, -0.02024581932313422F, 0.0F);
        this.GlowChestCannon04a = new ModelRenderer(this, 0, 0);
        this.GlowChestCannon04a.setRotationPoint(-10.0F, -1.0F, -7.0F);
        this.setRotateAngle(GlowChestCannon04a, 0.18203784098300857F, 0.091106186954104F, 0.0F);
        this.GlowChestCannon05a = new ModelRenderer(this, 0, 0);
        this.GlowChestCannon05a.setRotationPoint(-13.0F, -8.0F, -6.0F);
        this.setRotateAngle(GlowChestCannon05a, -0.091106186954104F, 0.136659280431156F, 0.022126174344515567F);
        this.GlowEquipL03 = new ModelRenderer(this, 0, 0);
        this.GlowEquipL03.setRotationPoint(6.0F, -6.0F, -2.0F);
        this.setRotateAngle(GlowEquipL03, 0.0F, 0.0F, -0.3141592653589793F);
        this.GlowEquipR03 = new ModelRenderer(this, 0, 0);
        this.GlowEquipR03.setRotationPoint(-7.0F, -6.0F, 7.0F);
        this.setRotateAngle(GlowEquipR03, 0.0F, -3.141592653589793F, 0.3141592653589793F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.Head);
        this.Head.addChild(this.HeadTooth);
        this.Head.addChild(this.HeadBack01);
        this.Head.addChild(this.HeadBack02);
        this.Head.addChild(this.HeadBack03);
        this.Head.addChild(this.Jaw);
        this.Jaw.addChild(this.Tongue);
        this.Jaw.addChild(this.JawTooth);
        
        this.GlowBodyMain.addChild(this.GlowEquipBaseL);
        this.GlowBodyMain.addChild(this.GlowEquipBaseR);
        this.GlowEquipBaseL.addChild(this.GlowEquipL01);
        this.GlowEquipBaseR.addChild(this.GlowEquipR01);
        this.GlowEquipL01.addChild(this.GlowEquipL02);
        this.GlowEquipR01.addChild(this.GlowEquipR02);
        this.GlowEquipL02.addChild(this.EquipCannon01);
        this.GlowEquipL02.addChild(this.EquipCannon02);
        this.GlowEquipL02.addChild(this.EquipCannon03);
        this.GlowEquipR02.addChild(this.EquipCannon01_1);
        this.GlowEquipR02.addChild(this.EquipCannon02_1);
        this.GlowEquipR02.addChild(this.EquipCannon03_1);
        
        this.GlowBodyMain.addChild(this.GlowChestCannon01a);
        this.GlowBodyMain.addChild(this.GlowChestCannon02a);
        this.GlowBodyMain.addChild(this.GlowChestCannon03a);
        this.GlowBodyMain.addChild(this.GlowChestCannon04a);
        this.GlowBodyMain.addChild(this.GlowChestCannon05a);
        this.GlowEquipBaseL.addChild(this.GlowEquipL03);
        this.GlowEquipBaseR.addChild(this.GlowEquipR03);
        this.GlowEquipL03.addChild(this.GlowChestCannonL01a);
        this.GlowEquipR03.addChild(this.GlowChestCannonR01a);
        this.GlowChestCannonL01a.addChild(this.ChestCannonL01b);
        this.GlowChestCannonR01a.addChild(this.ChestCannonR01b);
        this.GlowChestCannon01a.addChild(this.ChestCannon01b);
        this.GlowChestCannon02a.addChild(this.ChestCannon02b);
        this.GlowChestCannon03a.addChild(this.ChestCannon03b);
        this.GlowChestCannon04a.addChild(this.ChestCannon04b);
        this.GlowChestCannon05a.addChild(this.ChestCannon05b);  
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
    	GlStateManager.scale(0.8F, 0.8F, 0.8F);
    	
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
    	GlStateManager.enableLighting();
    	
    	GlStateManager.popMatrix();
    }
    
    //for idle/run animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		IShipEmotion ent = (IShipEmotion)entity;
		  
		motionHumanPos(f, f1, f2, f3, f4, ent);
    }
    
    //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
  	{   
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleFast = MathHelper.cos(f2*1F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		
  		GlStateManager.translate(0F, 0.2F, 0F);
  		
  		//水上漂浮
  		if(((IShipFloating)ent).getShipDepth() > 0)
  		{
  			GlStateManager.translate(0F, angleX * 0.025F - 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = angleAdd1 - 1.6755F;
	  	addk2 = angleAdd2 - 1.6755F;

	    //正常站立動作
	  	//頭部煙囪
	  	this.HeadBack01.rotateAngleX = angleFast * 0.04F * rand.nextFloat() + 0.68F;
	  	this.HeadBack02.rotateAngleX = angleFast * 0.06F * rand.nextFloat() + 0.77F;
	  	this.HeadBack03.rotateAngleX = angleFast * 0.05F * rand.nextFloat() + 0.6F;
	  	//嘴巴
	  	this.Jaw.rotateAngleX = angleX * 0.12F + 0.83F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 1.2F - 0.7F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 1.2F - 0.7F;
	    //cannon
	    float headX = f4 * 0.017F;
	    float headY = f3 * 0.017F;
	    this.EquipCannon01.rotateAngleX = headX * 0.85F;
	    this.EquipCannon02.rotateAngleX = headX * 0.95F;
	    this.EquipCannon03.rotateAngleX = headX * 0.75F;
	    this.EquipCannon01_1.rotateAngleX = headX * 0.95F;
	    this.EquipCannon02_1.rotateAngleX = headX * 1.1F;
	    this.EquipCannon03_1.rotateAngleX = headX * 0.85F;
	    this.EquipL02.rotateAngleY = headY;
	    this.EquipR02.rotateAngleY = headY;
	    this.GlowEquipL02.rotateAngleY = headY;
	    this.GlowEquipR02.rotateAngleY = headY;
	    
	    if (ent.getStateEmotion(ID.S.Emotion) > 0)
	    {
	    	this.ArmRight01.rotateAngleX = -1.57F;
	    }
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}

    
}
