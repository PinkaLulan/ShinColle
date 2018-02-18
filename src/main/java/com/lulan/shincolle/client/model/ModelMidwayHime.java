package com.lulan.shincolle.client.model;

import java.util.ArrayList;

import com.lulan.shincolle.client.render.RenderBasic.MiscModel;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.handler.EventHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMidwayHime - PinkaLulan
 * Created using Tabula 5.1.0  2017/11/2
 */
public class ModelMidwayHime extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft01;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer EquipSR01;
    public ModelRenderer EquipSR01b;
    public ModelRenderer EquipSR01c;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer HeadHL;
    public ModelRenderer HeadHR;
    public ModelRenderer HairU01;
    public ModelRenderer Ahoke;
    public ModelRenderer HairR01;
    public ModelRenderer HairL01;
    public ModelRenderer HairR02;
    public ModelRenderer HairL02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer HeadHL2;
    public ModelRenderer HeadHL3;
    public ModelRenderer HeadHR2;
    public ModelRenderer HeadHR3;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft02;
    public ModelRenderer Skirt02;
    public ModelRenderer Skirt03;
    public ModelRenderer LegRight02;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight02a;
    public ModelRenderer ArmRight02b;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft02a;
    public ModelRenderer ArmLeft02b;
    public ModelRenderer Collar02;
    public ModelRenderer Collar03a1;
    public ModelRenderer Collar03a2;
    public ModelRenderer Collar03a3;
    public ModelRenderer Collar03a3_1;
    public ModelRenderer Collar03a4;
    public ModelRenderer Collar03a5;
    public ModelRenderer Collar03a6;
    public ModelRenderer Collar03a7;
    public ModelRenderer Collar03a8;
    public ModelRenderer Collar03a9;
    public ModelRenderer Collar03a10;
    public ModelRenderer Collar03a11;
    public ModelRenderer Collar03a12;
    public ModelRenderer Collar03a13;
    public ModelRenderer Collar03a14;
    public ModelRenderer Collar03a15;
    public ModelRenderer Collar03b1;
    public ModelRenderer Collar03b2;
    public ModelRenderer Collar03b3;
    public ModelRenderer Collar03b3_1;
    public ModelRenderer Collar03b4;
    public ModelRenderer Collar03b5;
    public ModelRenderer Collar03b6;
    public ModelRenderer Collar03b7;
    public ModelRenderer Collar03b8;
    public ModelRenderer Collar03b9;
    public ModelRenderer Collar03b10;
    public ModelRenderer Collar03b11;
    public ModelRenderer Collar03b12;
    public ModelRenderer Collar03b13;
    public ModelRenderer Collar03b14;
    public ModelRenderer Collar03b15;
    public ModelRenderer EquipSR02;
    public ModelRenderer EquipSR03;
    public ModelRenderer EquipSR04;
    public ModelRenderer EquipSR05;
    public ModelRenderer EquipSR02b;
    public ModelRenderer EquipSR03b;
    public ModelRenderer EquipSR04b;
    public ModelRenderer EquipSR02c;
    public ModelRenderer EquipSR03c;
    public ModelRenderer EquipSR04c;
    public ModelRenderer EquipSR05c;
    public ModelRenderer Collar01;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowBodyMain2;
    public ModelRenderer GlowBodyMain2a;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ArrayList<MiscModel> miscModelList;
    public boolean renderTako;
    public MiscModel tako1;
    
    
    public ModelMidwayHime()
    {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.scale = 0.48F;
        this.offsetY = 1.62F;
        this.offsetItem = new float[] {0.08F, 1.02F, -0.07F};
        this.offsetBlock = new float[] {0.08F, 1.02F, -0.07F};
        this.renderTako = false;
        
        this.setDefaultFaceModel();
        
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.Collar03b7 = new ModelRenderer(this, 0, 0);
        this.Collar03b7.setRotationPoint(0.0F, 4.0F, 1.5F);
        this.Collar03b7.addBox(-1.5F, 0.0F, -3.0F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03b7, -0.8377580409572781F, 0.0F, 0.0F);
        this.Collar03b2 = new ModelRenderer(this, 0, 0);
        this.Collar03b2.setRotationPoint(0.0F, 4.0F, 1.5F);
        this.Collar03b2.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Collar03b2, -0.8726646259971648F, 0.0F, 0.0F);
        this.EquipSR04c = new ModelRenderer(this, 108, 25);
        this.EquipSR04c.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR04c.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 71);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, -0.08726646259971647F, 0.0F, 0.2617993877991494F);
        this.EquipSR01c = new ModelRenderer(this, 108, 25);
        this.EquipSR01c.setRotationPoint(-12.0F, 30.0F, -19.0F);
        this.EquipSR01c.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.setRotateAngle(EquipSR01c, 0.5585053606381855F, -0.3490658503988659F, -2.530727415391778F);
        this.HeadHL = new ModelRenderer(this, 40, 104);
        this.HeadHL.mirror = true;
        this.HeadHL.setRotationPoint(6.4F, -10.6F, 0.8F);
        this.HeadHL.addBox(0.0F, -2.5F, -2.5F, 3, 5, 5, 0.0F);
        this.setRotateAngle(HeadHL, -0.7853981633974483F, -0.17453292519943295F, -0.3839724354387525F);
        this.BoobL = new ModelRenderer(this, 0, 35);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.4F, -8.5F, -3.7F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 6, 6, 0.0F);
        this.setRotateAngle(BoobL, -0.8726646259971648F, -0.08726646259971647F, -0.06981317007977318F);
        this.Collar03b15 = new ModelRenderer(this, 0, 0);
        this.Collar03b15.mirror = true;
        this.Collar03b15.setRotationPoint(0.0F, 4.0F, 1.5F);
        this.Collar03b15.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Collar03b15, -0.8377580409572781F, 0.0F, 0.0F);
        this.EquipSR03 = new ModelRenderer(this, 108, 25);
        this.EquipSR03.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR03.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.EquipSR01 = new ModelRenderer(this, 108, 25);
        this.EquipSR01.setRotationPoint(40.0F, -11.0F, 13.0F);
        this.EquipSR01.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.setRotateAngle(EquipSR01, 0.0F, 0.5235987755982988F, 1.5707963267948966F);
        this.Collar03a3_1 = new ModelRenderer(this, 0, 0);
        this.Collar03a3_1.mirror = true;
        this.Collar03a3_1.setRotationPoint(-4.3F, 0.5F, -3.5F);
        this.Collar03a3_1.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(Collar03a3_1, -2.007128639793479F, 0.20943951023931953F, -0.06981317007977318F);
        this.Collar03a6 = new ModelRenderer(this, 0, 0);
        this.Collar03a6.setRotationPoint(4.6F, 0.1F, 1.7F);
        this.Collar03a6.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(Collar03a6, -1.7453292519943295F, -1.605702911834783F, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 47);
        this.LegLeft02.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.ArmRight02a = new ModelRenderer(this, 75, 47);
        this.ArmRight02a.mirror = true;
        this.ArmRight02a.setRotationPoint(2.5F, 3.0F, -2.5F);
        this.ArmRight02a.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6, 0.0F);
        this.setRotateAngle(ArmRight02a, 0.05235987755982988F, 0.0F, 0.0F);
        this.Collar03a5 = new ModelRenderer(this, 0, 0);
        this.Collar03a5.mirror = true;
        this.Collar03a5.setRotationPoint(4.6F, 0.1F, -1.3F);
        this.Collar03a5.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03a5, -1.7453292519943295F, -1.4311699866353502F, 0.0F);
        this.ArmRight02b = new ModelRenderer(this, 78, 37);
        this.ArmRight02b.mirror = true;
        this.ArmRight02b.setRotationPoint(0.0F, 1.9F, 0.2F);
        this.ArmRight02b.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.EquipSR02c = new ModelRenderer(this, 108, 25);
        this.EquipSR02c.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR02c.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.Collar03b4 = new ModelRenderer(this, 0, 0);
        this.Collar03b4.setRotationPoint(0.0F, 4.0F, 1.5F);
        this.Collar03b4.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Collar03b4, -0.8377580409572781F, 0.0F, 0.0F);
        this.EquipSR04 = new ModelRenderer(this, 108, 25);
        this.EquipSR04.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR04.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.ArmLeft02a = new ModelRenderer(this, 75, 47);
        this.ArmLeft02a.setRotationPoint(-2.5F, 3.0F, -2.5F);
        this.ArmLeft02a.addBox(-3.0F, 0.0F, -3.0F, 6, 2, 6, 0.0F);
        this.setRotateAngle(ArmLeft02a, 0.05235987755982988F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 68);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.08726646259971647F, 0.0F, -0.08726646259971647F);
        this.Skirt02 = new ModelRenderer(this, 128, 17);
        this.Skirt02.setRotationPoint(0.0F, 4.5F, -2.7F);
        this.Skirt02.addBox(-10.5F, 0.0F, -6.5F, 21, 6, 13, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.Collar03a2 = new ModelRenderer(this, 0, 0);
        this.Collar03a2.setRotationPoint(-1.3F, 0.6F, -3.5F);
        this.Collar03a2.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03a2, -1.8325957145940461F, 0.12217304763960307F, -0.03490658503988659F);
        this.Collar03a8 = new ModelRenderer(this, 0, 0);
        this.Collar03a8.mirror = true;
        this.Collar03a8.setRotationPoint(3.5F, 0.2F, 3.4F);
        this.Collar03a8.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(Collar03a8, -1.7453292519943295F, -2.6179938779914944F, 0.05235987755982988F);
        this.Collar01 = new ModelRenderer(this, 66, 25);
        this.Collar01.setRotationPoint(0.0F, 1.9F, -1.2F);
        this.Collar01.addBox(-6.0F, -2.0F, -4.0F, 12, 2, 8, 0.0F);
        this.setRotateAngle(Collar01, 0.035F, 0.0F, 0.0F);
        this.Collar03a7 = new ModelRenderer(this, 0, 0);
        this.Collar03a7.setRotationPoint(5.4F, 0.1F, 2.7F);
        this.Collar03a7.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03a7, -1.7453292519943295F, -2.2689280275926285F, 0.0F);
        this.Collar03a15 = new ModelRenderer(this, 0, 0);
        this.Collar03a15.mirror = true;
        this.Collar03a15.setRotationPoint(-5.0F, 0.2F, -2.1F);
        this.Collar03a15.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03a15, -1.6580627893946132F, 0.8028514559173915F, 0.08726646259971647F);
        this.Collar03b11 = new ModelRenderer(this, 0, 0);
        this.Collar03b11.setRotationPoint(0.0F, 5.0F, 1.5F);
        this.Collar03b11.addBox(-1.5F, 0.0F, -3.0F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03b11, -0.767944870877505F, 0.0F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 0, 10);
        this.HairL01.setRotationPoint(7.0F, 3.0F, -5.5F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairL01, -0.19198621771937624F, -0.17453292519943295F, -0.08726646259971647F);
        this.Collar03b10 = new ModelRenderer(this, 0, 0);
        this.Collar03b10.mirror = true;
        this.Collar03b10.setRotationPoint(0.0F, 5.0F, 1.5F);
        this.Collar03b10.addBox(-1.5F, 0.0F, -3.0F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03b10, -0.6981317007977318F, 0.0F, 0.0F);
        this.EquipSR04b = new ModelRenderer(this, 108, 25);
        this.EquipSR04b.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR04b.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 71);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.3490658503988659F, 0.0F, -0.2617993877991494F);
        this.Collar03b5 = new ModelRenderer(this, 0, 0);
        this.Collar03b5.mirror = true;
        this.Collar03b5.setRotationPoint(0.0F, 4.0F, 1.5F);
        this.Collar03b5.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Collar03b5, -0.8377580409572781F, 0.0F, 0.0F);
        this.Collar02 = new ModelRenderer(this, 128, 60);
        this.Collar02.setRotationPoint(0.0F, -2.5F, -1.0F);
        this.Collar02.addBox(-7.0F, -1.5F, -5.7F, 14, 3, 11, 0.0F);
        this.Hair03 = new ModelRenderer(this, 26, 28);
        this.Hair03.setRotationPoint(0.0F, 12.5F, -0.1F);
        this.Hair03.addBox(-8.0F, 0.0F, -4.5F, 16, 15, 7, 0.0F);
        this.setRotateAngle(Hair03, -0.05235987755982988F, 0.0F, 0.0F);
        this.ArmLeft02b = new ModelRenderer(this, 78, 37);
        this.ArmLeft02b.setRotationPoint(0.0F, 1.9F, 0.2F);
        this.ArmLeft02b.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.Collar03b1 = new ModelRenderer(this, 0, 0);
        this.Collar03b1.mirror = true;
        this.Collar03b1.setRotationPoint(0.0F, 4.0F, 1.5F);
        this.Collar03b1.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Collar03b1, -0.8726646259971648F, 0.0F, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.Collar03b3_1 = new ModelRenderer(this, 0, 0);
        this.Collar03b3_1.mirror = true;
        this.Collar03b3_1.setRotationPoint(0.0F, 5.0F, 1.5F);
        this.Collar03b3_1.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Collar03b3_1, -0.9599310885968813F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 54);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.Collar03a9 = new ModelRenderer(this, 0, 0);
        this.Collar03a9.setRotationPoint(1.4F, 0.4F, 2.6F);
        this.Collar03a9.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(Collar03a9, -1.7453292519943295F, -3.036872898470133F, 0.05235987755982988F);
        this.Collar03a10 = new ModelRenderer(this, 0, 0);
        this.Collar03a10.mirror = true;
        this.Collar03a10.setRotationPoint(-1.4F, 0.4F, 2.6F);
        this.Collar03a10.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(Collar03a10, -1.7453292519943295F, 3.036872898470133F, -0.05235987755982988F);
        this.EquipSR03b = new ModelRenderer(this, 108, 25);
        this.EquipSR03b.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR03b.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.HeadHL2 = new ModelRenderer(this, 0, 0);
        this.HeadHL2.setRotationPoint(3.0F, 0.0F, 0.0F);
        this.HeadHL2.addBox(0.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
        this.Collar03a12 = new ModelRenderer(this, 0, 0);
        this.Collar03a12.mirror = true;
        this.Collar03a12.setRotationPoint(-5.4F, 0.1F, 2.7F);
        this.Collar03a12.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03a12, -1.7453292519943295F, 2.2689280275926285F, 0.0F);
        this.Collar03b14 = new ModelRenderer(this, 0, 0);
        this.Collar03b14.setRotationPoint(0.0F, 4.0F, 1.5F);
        this.Collar03b14.addBox(-1.5F, 0.0F, -3.0F, 3, 3, 3, 0.0F);
        this.setRotateAngle(Collar03b14, -0.8377580409572781F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -0.8F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.Collar03b13 = new ModelRenderer(this, 0, 0);
        this.Collar03b13.mirror = true;
        this.Collar03b13.setRotationPoint(0.0F, 5.0F, 1.5F);
        this.Collar03b13.addBox(-1.5F, 0.0F, -3.0F, 3, 6, 3, 0.0F);
        this.setRotateAngle(Collar03b13, -0.9948376736367678F, 0.0F, 0.0F);
        this.Collar03b8 = new ModelRenderer(this, 0, 0);
        this.Collar03b8.mirror = true;
        this.Collar03b8.setRotationPoint(0.0F, 5.0F, 1.5F);
        this.Collar03b8.addBox(-1.5F, 0.0F, -3.0F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03b8, -0.767944870877505F, 0.0F, 0.0F);
        this.Collar03b9 = new ModelRenderer(this, 0, 0);
        this.Collar03b9.setRotationPoint(0.0F, 5.0F, 1.5F);
        this.Collar03b9.addBox(-1.5F, 0.0F, -3.0F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03b9, -0.6981317007977318F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 0, 35);
        this.BoobR.setRotationPoint(-3.5F, -8.5F, -3.8F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 6, 6, 0.0F);
        this.setRotateAngle(BoobR, -0.8726646259971648F, 0.08726646259971647F, 0.06981317007977318F);
        this.EquipSR02b = new ModelRenderer(this, 108, 25);
        this.EquipSR02b.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR02b.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.Hair02 = new ModelRenderer(this, 62, 0);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 5.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 16, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.08726646259971647F, 0.0F, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 128, 0);
        this.Skirt01.setRotationPoint(0.0F, 3.0F, 1.5F);
        this.Skirt01.addBox(-8.5F, 0.0F, -8.5F, 17, 6, 11, 0.0F);
        this.setRotateAngle(Skirt01, -0.08726646259971647F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 24, 80);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.Neck.addBox(-2.5F, -3.0F, -2.9F, 5, 3, 5, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.EquipSR01b = new ModelRenderer(this, 108, 25);
        this.EquipSR01b.setRotationPoint(-33.0F, -9.0F, 13.7F);
        this.EquipSR01b.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.setRotateAngle(EquipSR01b, -0.5918411493512771F, -0.3665191429188092F, -0.5918411493512771F);
        this.Collar03a11 = new ModelRenderer(this, 0, 0);
        this.Collar03a11.setRotationPoint(-3.5F, 0.2F, 3.4F);
        this.Collar03a11.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(Collar03a11, -1.7453292519943295F, 2.6179938779914944F, -0.05235987755982988F);
        this.Collar03a13 = new ModelRenderer(this, 0, 0);
        this.Collar03a13.mirror = true;
        this.Collar03a13.setRotationPoint(-4.6F, 0.1F, 1.7F);
        this.Collar03a13.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(Collar03a13, -1.7453292519943295F, 1.605702911834783F, 0.0F);
        this.Collar03b12 = new ModelRenderer(this, 0, 0);
        this.Collar03b12.mirror = true;
        this.Collar03b12.setRotationPoint(0.0F, 4.0F, 1.5F);
        this.Collar03b12.addBox(-1.5F, 0.0F, -3.0F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03b12, -0.8377580409572781F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 106, 31);
        this.Ahoke.setRotationPoint(-0.5F, -7.0F, -6.0F);
        this.Ahoke.addBox(0.0F, -6.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.20943951023931953F, 0.6981317007977318F, 0.0F);
        this.Collar03a1 = new ModelRenderer(this, 0, 0);
        this.Collar03a1.mirror = true;
        this.Collar03a1.setRotationPoint(1.3F, 0.6F, -3.5F);
        this.Collar03a1.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03a1, -1.8325957145940461F, -0.12217304763960307F, 0.03490658503988659F);
        this.Collar03a4 = new ModelRenderer(this, 0, 0);
        this.Collar03a4.setRotationPoint(5.0F, 0.2F, -2.1F);
        this.Collar03a4.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03a4, -1.6580627893946132F, -0.8028514559173915F, -0.08726646259971647F);
        this.LegRight02 = new ModelRenderer(this, 0, 47);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.EquipSR03c = new ModelRenderer(this, 108, 25);
        this.EquipSR03c.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR03c.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 54);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.Collar03a3 = new ModelRenderer(this, 0, 0);
        this.Collar03a3.setRotationPoint(4.3F, 0.5F, -3.5F);
        this.Collar03a3.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(Collar03a3, -2.007128639793479F, -0.20943951023931953F, 0.06981317007977318F);
        this.Collar03a14 = new ModelRenderer(this, 0, 0);
        this.Collar03a14.setRotationPoint(-4.6F, 0.1F, -1.3F);
        this.Collar03a14.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Collar03a14, -1.7453292519943295F, 1.4311699866353502F, 0.0F);
        this.EquipSR02 = new ModelRenderer(this, 108, 25);
        this.EquipSR02.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR02.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.HairL02 = new ModelRenderer(this, 0, 10);
        this.HairL02.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairL02, 0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.HeadHR2 = new ModelRenderer(this, 0, 0);
        this.HeadHR2.setRotationPoint(-3.0F, 0.0F, 0.0F);
        this.HeadHR2.addBox(-1.0F, -2.0F, -2.0F, 1, 4, 4, 0.0F);
        this.HairR01 = new ModelRenderer(this, 0, 10);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-7.0F, 3.0F, -5.5F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairR01, -0.19198621771937624F, 0.17453292519943295F, 0.08726646259971647F);
        this.EquipSR05 = new ModelRenderer(this, 108, 25);
        this.EquipSR05.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR05.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.HeadHR3 = new ModelRenderer(this, 44, 70);
        this.HeadHR3.setRotationPoint(-1.0F, 0.0F, 0.0F);
        this.HeadHR3.addBox(-1.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.HeadHL3 = new ModelRenderer(this, 44, 70);
        this.HeadHL3.setRotationPoint(1.0F, 0.0F, 0.0F);
        this.HeadHL3.addBox(0.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.EquipSR05c = new ModelRenderer(this, 108, 25);
        this.EquipSR05c.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.EquipSR05c.addBox(-4.5F, 0.0F, -0.5F, 9, 16, 1, 0.0F);
        this.Collar03b6 = new ModelRenderer(this, 0, 0);
        this.Collar03b6.setRotationPoint(0.0F, 5.0F, 1.5F);
        this.Collar03b6.addBox(-1.5F, 0.0F, -3.0F, 3, 6, 3, 0.0F);
        this.setRotateAngle(Collar03b6, -0.9948376736367678F, 0.0F, 0.0F);
        this.Hair01 = new ModelRenderer(this, 14, 0);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 17, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.2617993877991494F, 0.0F, 0.0F);
        this.HeadHR = new ModelRenderer(this, 40, 104);
        this.HeadHR.setRotationPoint(-6.4F, -10.6F, 0.8F);
        this.HeadHR.addBox(-3.0F, -2.5F, -2.5F, 3, 5, 5, 0.0F);
        this.setRotateAngle(HeadHR, -0.7853981633974483F, 0.17453292519943295F, 0.3839724354387525F);
        this.HairR02 = new ModelRenderer(this, 0, 10);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 10.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.Collar03b3 = new ModelRenderer(this, 0, 0);
        this.Collar03b3.setRotationPoint(0.0F, 5.0F, 1.5F);
        this.Collar03b3.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Collar03b3, -0.9599310885968813F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 88);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.Skirt03 = new ModelRenderer(this, 128, 37);
        this.Skirt03.setRotationPoint(0.0F, 4.5F, 0.3F);
        this.Skirt03.addBox(-13.0F, 0.0F, -7.5F, 26, 6, 15, 0.0F);
        this.setRotateAngle(Skirt03, -0.05235987755982988F, 0.0F, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 68);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.2792526803190927F, 0.0F, 0.08726646259971647F);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowBodyMain2 = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain2.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain2, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowBodyMain2a = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain2a.setRotationPoint(10F, -14F, -39F);
        this.setRotateAngle(GlowBodyMain2a, 0F, 0F, 0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.setRotateAngle(GlowNeck, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -0.8F, -0.7F);
        
        this.GlowNeck.addChild(this.Collar01);
        this.Collar01.addChild(this.Collar02);
        this.Collar02.addChild(this.Collar03a2);
        this.Collar02.addChild(this.Collar03a8);
        this.Collar02.addChild(this.Collar03a7);
        this.Collar02.addChild(this.Collar03a15);
        this.Collar02.addChild(this.Collar03a9);
        this.Collar02.addChild(this.Collar03a10);
        this.Collar02.addChild(this.Collar03a12);
        this.Collar02.addChild(this.Collar03a11);
        this.Collar02.addChild(this.Collar03a13);
        this.Collar02.addChild(this.Collar03a1);
        this.Collar02.addChild(this.Collar03a4);
        this.Collar02.addChild(this.Collar03a3);
        this.Collar02.addChild(this.Collar03a14);
        this.Collar02.addChild(this.Collar03a3_1);
        this.Collar02.addChild(this.Collar03a6);
        this.Collar02.addChild(this.Collar03a5);
        this.Collar03a1.addChild(this.Collar03b1);
        this.Collar03a3_1.addChild(this.Collar03b3_1);
        this.Collar03a14.addChild(this.Collar03b14);
        this.Collar03a13.addChild(this.Collar03b13);
        this.Collar03a8.addChild(this.Collar03b8);
        this.Collar03a9.addChild(this.Collar03b9);
        this.Collar03a12.addChild(this.Collar03b12);
        this.Collar03a6.addChild(this.Collar03b6);
        this.Collar03a3.addChild(this.Collar03b3);
        this.Collar03a11.addChild(this.Collar03b11);
        this.Collar03a4.addChild(this.Collar03b4);
        this.Collar03a15.addChild(this.Collar03b15);
        this.Collar03a7.addChild(this.Collar03b7);
        this.Collar03a2.addChild(this.Collar03b2);
        this.Collar03a10.addChild(this.Collar03b10);
        this.Collar03a5.addChild(this.Collar03b5);
        
        this.EquipSR03c.addChild(this.EquipSR04c);
        this.BodyMain.addChild(this.ArmRight01);
        this.BodyMain.addChild(this.BoobL);
        this.EquipSR02.addChild(this.EquipSR03);
        this.LegLeft01.addChild(this.LegLeft02);
        this.ArmRight02.addChild(this.ArmRight02a);
        this.ArmRight02a.addChild(this.ArmRight02b);
        this.EquipSR01c.addChild(this.EquipSR02c);
        this.Head.addChild(this.Hair);
        this.EquipSR03.addChild(this.EquipSR04);
        this.ArmLeft02.addChild(this.ArmLeft02a);
        this.Butt.addChild(this.LegRight01);
        this.Skirt01.addChild(this.Skirt02);
        this.Hair.addChild(this.HairL01);
        this.EquipSR03b.addChild(this.EquipSR04b);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Hair02.addChild(this.Hair03);
        this.ArmLeft02a.addChild(this.ArmLeft02b);
        this.Head.addChild(this.HairMain);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipSR02b.addChild(this.EquipSR03b);
        this.HeadHL.addChild(this.HeadHL2);
        this.Neck.addChild(this.Head);
        this.BodyMain.addChild(this.BoobR);
        this.EquipSR01b.addChild(this.EquipSR02b);
        this.Hair01.addChild(this.Hair02);
        this.Butt.addChild(this.Skirt01);
        this.BodyMain.addChild(this.Neck);
        this.Hair.addChild(this.Ahoke);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipSR02c.addChild(this.EquipSR03c);
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipSR01.addChild(this.EquipSR02);
        this.Hair.addChild(this.HairU01);
        this.HairL01.addChild(this.HairL02);
        this.Hair.addChild(this.HairR01);
        this.EquipSR04.addChild(this.EquipSR05);
        this.HeadHR.addChild(this.HeadHR2);
        this.HeadHR2.addChild(this.HeadHR3);
        this.HeadHL2.addChild(this.HeadHL3);
        this.EquipSR04c.addChild(this.EquipSR05c);
        this.HairMain.addChild(this.Hair01);
        this.HairR01.addChild(this.HairR02);
        this.BodyMain.addChild(this.Butt);
        this.Skirt02.addChild(this.Skirt03);
        this.Butt.addChild(this.LegLeft01);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowBodyMain2.addChild(this.GlowBodyMain2a);
        this.GlowBodyMain2a.addChild(this.EquipSR01);
        this.GlowBodyMain2a.addChild(this.EquipSR01b);
        this.GlowBodyMain2a.addChild(this.EquipSR01c);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        this.GlowHead.addChild(this.Flush0);
        this.GlowHead.addChild(this.Flush1);
        this.GlowHead.addChild(this.Mouth0);
        this.GlowHead.addChild(this.Mouth1);
        this.GlowHead.addChild(this.Mouth2);
        this.GlowHead.addChild(this.HeadHL);
        this.GlowHead.addChild(this.HeadHR);
        
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
    	float light = 80F + MathHelper.cos(f2 * 0.075F) * 80F;
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, light, light);
    	this.GlowBodyMain2.render(f5);
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
		
		this.EquipSR01.isHidden = true;
		this.EquipSR01b.isHidden = true;
		this.EquipSR01c.isHidden = true;
		
		boolean flag = !EmotionHelper.checkModelState(1, state);
		this.Collar01.isHidden = flag;
		
		this.renderTako = false;
	}

	@Override
	public void syncRotationGlowPart()
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

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.59F, 0F);
  		this.setFaceHungry(ent);
    	
		//頭部
	  	this.Head.rotateAngleX = -0.2618F;
	  	this.Head.rotateAngleY = 0F;
	  	this.Head.rotateAngleZ = 0F;
	  	//Body
  	    this.Ahoke.rotateAngleY = -1.0F;
	  	this.BodyMain.rotateAngleX = 1.2217F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 1.2217F;
	  	this.Butt.rotateAngleX = -0.05F;
	  	this.Skirt01.rotateAngleX = -0.34F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt01.offsetZ = 0F;
	  	this.Skirt02.rotateAngleX = -0.27F;
	  	this.Skirt02.offsetY = 0F;
	  	this.Skirt03.rotateAngleX = -0.22F;
	  	this.Skirt03.offsetY = 0F;
	  	this.Collar01.rotateAngleX = 0.035F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.2F;
	  	this.Hair01.rotateAngleZ = -0.2F;
	  	this.Hair02.rotateAngleX = 0.2F;
	  	this.Hair02.rotateAngleZ = -0.15F;
	  	this.HairL01.rotateAngleZ = 0.0873F;
	  	this.HairL02.rotateAngleZ = -0.3142F;
	  	this.HairR01.rotateAngleZ = -0.0873F;
	  	this.HairR02.rotateAngleZ = -1.2217F;
		this.HairL01.rotateAngleX = - 0.28F;
	  	this.HairL02.rotateAngleX = 0.15F;
	  	this.HairR01.rotateAngleX = -0.35F;
	  	this.HairR02.rotateAngleX = 0.18F;
	    //胸部
  	    this.BoobL.rotateAngleX = -1.0F;
  	    this.BoobL.rotateAngleZ = -0.12F;
  	    this.BoobR.rotateAngleX = -0.7F;
  	    this.BoobR.rotateAngleZ = -0.12F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -0.35F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = -3F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetY = 0F;
	    this.ArmLeft02.offsetZ = 0F;
    	this.ArmRight01.rotateAngleX = -0.5F;
	    this.ArmRight01.rotateAngleY = 0.3F;
		this.ArmRight01.rotateAngleZ = -0.5F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = -0.8727F;
		this.ArmRight02.offsetX = 0F;
		this.ArmRight02.offsetY = 0F;
		this.ArmRight02.offsetZ = 0F;
		//leg
		this.LegLeft01.rotateAngleX = -0.14F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.09F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleX = -1.2217F;
		this.LegRight01.rotateAngleY = -0.5236F;
		this.LegRight01.rotateAngleZ = 0F;
		this.LegRight02.rotateAngleX = 1.0472F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.08F + 0.6F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.5F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.5F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		float headX = 0F;
  		float headZ = 0F;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D || ent.getShipDepth(1) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.025F + 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = angleAdd1 * 0.6F - 0.27F;
	  	addk2 = angleAdd2 * 0.6F - 0.19F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F; 	//上下角度
	  	this.Head.rotateAngleY = f3 * 0.006F;	//左右角度
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.15F + 0.6F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.35F;
    	this.Butt.offsetY = 0F;
    	this.Butt.offsetZ = 0F;
    	this.BoobL.rotateAngleX = angleX * 0.08F - 0.76F;
    	this.BoobL.rotateAngleZ = 0.08F;
  	    this.BoobR.rotateAngleX = angleX * 0.08F - 0.76F;
  	    this.BoobR.rotateAngleZ = -0.08F;
  	    this.Collar01.rotateAngleX = 0.035F;
  	    this.Collar01.rotateAngleX += this.Head.rotateAngleX * 0.8F;
    	//cloth
	  	this.Skirt01.rotateAngleX = -0.087F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt01.offsetZ = 0F;
	  	this.Skirt02.rotateAngleX = angleX1 * 0.015F - 0.087F;
	  	this.Skirt02.offsetY = 0F;
	  	this.Skirt03.rotateAngleX = -angleX2 * 0.04F - 0.052F;
    	this.Skirt03.offsetY = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.26F + headX;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.087F + headX;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -angleX2 * 0.07F - 0.052F;
	  	this.Hair03.rotateAngleZ = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -0.26F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = 0.28F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = 0F;
	    this.ArmRight01.rotateAngleX = -0.26F;
	  	this.ArmRight01.rotateAngleY = 0F;
	    this.ArmRight01.rotateAngleZ = -0.28F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleY = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
	    this.ArmRight02.offsetZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.087F;
		this.LegLeft01.offsetY = 0F;
		this.LegLeft01.offsetZ = 0F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.087F;
		this.LegRight01.offsetY = 0F;
		this.LegRight01.offsetZ = 0F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		
		//奔跑動作
	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {
	    	//hair angleX * 0.03F + 0.21F + headX
	    	this.Hair01.rotateAngleX = angleAdd1 * 0.1F + f1 * 0.4F + headX;
	    	this.Hair02.rotateAngleX += 0F;
	    	this.Hair03.rotateAngleX += 0.1F;
			//胸部
	  	    this.BoobL.rotateAngleX = angleAdd2 * 0.1F - 0.83F;
	  	    this.BoobL.rotateAngleZ = -0.07F;
	  	    this.BoobR.rotateAngleX = angleAdd1 * 0.1F - 0.83F;
	  	    this.BoobR.rotateAngleZ = 0.07F;
	    	//arm 
		  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.8F + 0.1745F;
		  	this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft01.rotateAngleZ = -0.35F;
		    this.ArmRight01.rotateAngleX = angleAdd1 * 0.8F + 0.1745F;
		  	this.ArmRight01.rotateAngleY = 0F;
		    this.ArmRight01.rotateAngleZ = 0.35F;
	    }
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    //潛行跟蹲下動作
	    if (ent.getIsSneaking())
	    {
	    	GlStateManager.translate(0F, 0.09F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		  	this.Skirt01.rotateAngleX = -0.34F;
		  	this.Skirt01.offsetY = -0.2F;
		  	this.Skirt01.offsetZ = 0.03F;
		  	this.Skirt02.rotateAngleX = -0.27F;
		  	this.Skirt03.rotateAngleX = -0.22F;
		  	this.Collar01.rotateAngleX -= 0.35F;
			//胸部
	  	    this.BoobL.rotateAngleX -= 0.2F;
	  	    this.BoobL.rotateAngleZ = -0.04F;
	  	    this.BoobR.rotateAngleX -= 0.2F;
	  	    this.BoobR.rotateAngleZ = 0.04F;
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
			this.Hair03.rotateAngleX -= 0.1F;
  		}//end if sneaking
  		
	    //坐下動作
	    if (ent.getIsSitting() && !ent.getIsRiding())
	    {
	    	if (ent.getTickExisted() % 512 > 256)
	    	{
	    		GlStateManager.translate(0F, 0.51F, 0F);
		    	//body
		    	this.Head.rotateAngleX -= 0.7F;
		    	this.BodyMain.rotateAngleX = 0.35F;
		    	this.Skirt01.rotateAngleX = -0.23F;
		    	this.Skirt01.offsetY = -0.23F;
		    	this.Skirt02.rotateAngleX = -0.2F;
		    	this.Skirt02.offsetY = -0.17F;
		    	this.Skirt03.rotateAngleX = -0.2F;
		    	this.Skirt03.offsetY = -0.15F;
		    	this.Collar01.rotateAngleX -= 0.35F;
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
				//hair
				this.Hair01.rotateAngleX += 0.2F;
				this.Hair02.rotateAngleX += 0.5F;
				this.Hair03.rotateAngleX += 0.4F;
	    	}
	    	else
	    	{
		    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
			    	GlStateManager.translate(0F, 0.43F, 0F);
			    	//Body
				  	this.Head.rotateAngleX -= 0.1F;
				  	this.BodyMain.rotateAngleX = 0F;
			    	this.Butt.rotateAngleX = -0.2F;
			    	this.Butt.offsetY = 0F;
			    	this.BoobL.rotateAngleX -= 0.1F;
			    	this.BoobL.rotateAngleZ = 0.16F;
			    	this.BoobR.rotateAngleX -= 0.1F;
			    	this.BoobR.rotateAngleZ = -0.16F;
			    	//skirt
			    	this.Skirt01.rotateAngleX = -0.05F;
			    	this.Skirt01.offsetY = -0.1F;
			    	this.Skirt02.rotateAngleX = -0.15F;
			    	this.Skirt02.offsetY = -0.1F;
			    	this.Skirt03.rotateAngleX = -0.1F;
			    	this.Skirt03.offsetY = -0.1F;
			    	//arm
				  	this.ArmLeft01.rotateAngleX = -0.6F;
				  	this.ArmLeft01.rotateAngleZ = 0.1F;
				  	this.ArmLeft02.rotateAngleZ = 0.39F;
			    	this.ArmRight01.rotateAngleX = -0.6F;
					this.ArmRight01.rotateAngleZ = -0.1F;
					this.ArmRight02.rotateAngleZ = -0.39F;
				  	//leg
				  	addk1 = -0.9F;
				  	addk2 = -0.9F;
				  	this.LegLeft01.rotateAngleY = 0.19F;
					this.LegLeft01.rotateAngleZ = 0F;
					this.LegLeft02.rotateAngleX = 2.67F;
					this.LegLeft02.rotateAngleZ = 0.0175F;
					this.LegLeft02.offsetZ = 0.375F;
					this.LegRight01.rotateAngleY = -0.19F;
					this.LegRight01.rotateAngleZ = 0F;
					this.LegRight02.rotateAngleX = 2.67F;
					this.LegRight02.rotateAngleZ = -0.0175F;
					this.LegRight02.offsetZ = 0.375F;
			    	//tako
			    	this.renderTako = true;
			    	tako1 = this.miscModelList.get(0);
			    	tako1.entity.ticksExisted = ent.getTickExisted();
			    	tako1.entity.posY = 0.34F;
		    	}
		    	else
		    	{
			    	GlStateManager.translate(0F, 0.43F, 0F);
			    	//Body
				  	this.Head.rotateAngleX -= 0.1F;
				  	this.BodyMain.rotateAngleX = 0F;
			    	this.Butt.rotateAngleX = -0.2F;
			    	this.Butt.offsetY = 0F;
			    	this.BoobL.rotateAngleX -= 0.1F;
			    	this.BoobL.rotateAngleZ = 0.16F;
			    	this.BoobR.rotateAngleX -= 0.1F;
			    	this.BoobR.rotateAngleZ = -0.16F;
			    	//skirt
			    	this.Skirt01.rotateAngleX = -0.05F;
			    	this.Skirt01.offsetY = -0.1F;
			    	this.Skirt02.rotateAngleX = -0.15F;
			    	this.Skirt02.offsetY = -0.1F;
			    	this.Skirt03.rotateAngleX = -0.1F;
			    	this.Skirt03.offsetY = -0.1F;
			    	//arm
				  	this.ArmLeft01.rotateAngleX = -0.46F;
				  	this.ArmLeft01.rotateAngleZ = 0.35F;
			    	this.ArmRight01.rotateAngleX = -0.46F;
					this.ArmRight01.rotateAngleZ = -0.35F;
				  	//leg
				  	addk1 = -0.9F;
				  	addk2 = -0.9F;
				  	this.LegLeft01.rotateAngleY = 0.19F;
					this.LegLeft01.rotateAngleZ = 0F;
					this.LegLeft02.rotateAngleX = 2.67F;
					this.LegLeft02.rotateAngleZ = 0.0175F;
					this.LegLeft02.offsetZ = 0.375F;
					this.LegRight01.rotateAngleY = -0.19F;
					this.LegRight01.rotateAngleZ = 0F;
					this.LegRight02.rotateAngleX = 2.67F;
					this.LegRight02.rotateAngleZ = -0.0175F;
					this.LegRight02.offsetZ = 0.375F;
		    	}
	    	}
  		}//end sitting
	    
	    //騎乘專屬坐騎動作
	    if (ent.getIsRiding())
	    {
	    	if (((Entity)ent).getRidingEntity() instanceof BasicEntityMount)
	    	{
	    		if (ent.getIsSitting())
	    		{
	    			if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    			{
	    				GlStateManager.translate(0F, 1.43F, 0F);
	    				
				    	//Body
					  	this.Head.rotateAngleX -= 0.1F;
					  	this.BodyMain.rotateAngleX = 0F;
				    	this.Butt.rotateAngleX = -0.2F;
				    	this.Butt.offsetY = 0F;
				    	this.BoobL.rotateAngleX -= 0.1F;
				    	this.BoobL.rotateAngleZ = 0.16F;
				    	this.BoobR.rotateAngleX -= 0.1F;
				    	this.BoobR.rotateAngleZ = -0.16F;
				    	//skirt
				    	this.Skirt01.rotateAngleX = -0.05F;
				    	this.Skirt01.offsetY = -0.1F;
				    	this.Skirt02.rotateAngleX = -0.15F;
				    	this.Skirt02.offsetY = -0.1F;
				    	this.Skirt03.rotateAngleX = -0.1F;
				    	this.Skirt03.offsetY = -0.1F;
				    	//arm
					  	this.ArmLeft01.rotateAngleX = -0.6F;
					  	this.ArmLeft01.rotateAngleZ = 0.1F;
					  	this.ArmLeft02.rotateAngleZ = 0.39F;
				    	this.ArmRight01.rotateAngleX = -0.6F;
						this.ArmRight01.rotateAngleZ = -0.1F;
						this.ArmRight02.rotateAngleZ = -0.39F;
					  	//leg
					  	addk1 = -0.9F;
					  	addk2 = -0.9F;
					  	this.LegLeft01.rotateAngleY = 0.19F;
						this.LegLeft01.rotateAngleZ = 0F;
						this.LegLeft02.rotateAngleX = 2.67F;
						this.LegLeft02.rotateAngleZ = 0.0175F;
						this.LegLeft02.offsetZ = 0.375F;
						this.LegRight01.rotateAngleY = -0.19F;
						this.LegRight01.rotateAngleZ = 0F;
						this.LegRight02.rotateAngleX = 2.67F;
						this.LegRight02.rotateAngleZ = -0.0175F;
						this.LegRight02.offsetZ = 0.375F;
				    	//tako
				    	this.renderTako = true;
				    	tako1 = this.miscModelList.get(0);
				    	tako1.entity.ticksExisted = ent.getTickExisted();
				    	tako1.entity.posY = -1.12F;
			    	}
			    	else
			    	{
			    		GlStateManager.translate(0F, 1.43F, 0F);
			    		
				    	//Body
					  	this.Head.rotateAngleX -= 0.1F;
					  	this.BodyMain.rotateAngleX = 0F;
				    	this.Butt.rotateAngleX = -0.2F;
				    	this.Butt.offsetY = 0F;
				    	this.BoobL.rotateAngleX -= 0.1F;
				    	this.BoobL.rotateAngleZ = 0.16F;
				    	this.BoobR.rotateAngleX -= 0.1F;
				    	this.BoobR.rotateAngleZ = -0.16F;
				    	//skirt
				    	this.Skirt01.rotateAngleX = -0.05F;
				    	this.Skirt01.offsetY = -0.1F;
				    	this.Skirt02.rotateAngleX = -0.15F;
				    	this.Skirt02.offsetY = -0.1F;
				    	this.Skirt03.rotateAngleX = -0.1F;
				    	this.Skirt03.offsetY = -0.1F;
				    	//arm
					  	this.ArmLeft01.rotateAngleX = -0.46F;
					  	this.ArmLeft01.rotateAngleZ = 0.35F;
				    	this.ArmRight01.rotateAngleX = -0.46F;
						this.ArmRight01.rotateAngleZ = -0.35F;
					  	//leg
					  	addk1 = -0.9F;
					  	addk2 = -0.9F;
					  	this.LegLeft01.rotateAngleY = 0.19F;
						this.LegLeft01.rotateAngleZ = 0F;
						this.LegLeft02.rotateAngleX = 2.67F;
						this.LegLeft02.rotateAngleZ = 0.0175F;
						this.LegLeft02.offsetZ = 0.375F;
						this.LegRight01.rotateAngleY = -0.19F;
						this.LegRight01.rotateAngleZ = 0F;
						this.LegRight02.rotateAngleX = 2.67F;
						this.LegRight02.rotateAngleZ = -0.0175F;
						this.LegRight02.offsetZ = 0.375F;
			    	}
		    	}//end if sitting
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.08F, 0F);
		    		
			    	//body
			    	this.Head.rotateAngleX -= 0.7F;
			    	this.BodyMain.rotateAngleX = 0.35F;
			    	this.Skirt01.rotateAngleX = -0.23F;
			    	this.Skirt01.offsetY = -0.23F;
			    	this.Skirt02.rotateAngleX = -0.2F;
			    	this.Skirt02.offsetY = -0.17F;
			    	this.Skirt03.rotateAngleX = -0.2F;
			    	this.Skirt03.offsetY = -0.15F;
			    	this.Collar01.rotateAngleX -= 0.35F;
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
					//hair
					if (ent.getIsSprinting() || f1 > 0.9F)
				    {
						this.Hair01.rotateAngleX += 0.5F;
						this.Hair02.rotateAngleX += 0.4F;
						this.Hair03.rotateAngleX += 0.2F;
				    }
					else
					{
						this.Hair01.rotateAngleX += 0.2F;
						this.Hair02.rotateAngleX += 0.4F;
						this.Hair03.rotateAngleX += 0.2F;
					}
		    	}
	    	}//end ship mount
	    	//normal mount ex: cart
	    	else
	    	{
	    		if (ent.getIsSitting())
	    		{
	    			if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			    	{
	    				GlStateManager.translate(0F, 0.43F, 0F);
	    				
				    	//Body
					  	this.Head.rotateAngleX -= 0.1F;
					  	this.BodyMain.rotateAngleX = 0F;
				    	this.Butt.rotateAngleX = -0.2F;
				    	this.Butt.offsetY = 0F;
				    	this.BoobL.rotateAngleX -= 0.1F;
				    	this.BoobL.rotateAngleZ = 0.16F;
				    	this.BoobR.rotateAngleX -= 0.1F;
				    	this.BoobR.rotateAngleZ = -0.16F;
				    	//skirt
				    	this.Skirt01.rotateAngleX = -0.05F;
				    	this.Skirt01.offsetY = -0.1F;
				    	this.Skirt02.rotateAngleX = -0.15F;
				    	this.Skirt02.offsetY = -0.1F;
				    	this.Skirt03.rotateAngleX = -0.1F;
				    	this.Skirt03.offsetY = -0.1F;
				    	//arm
					  	this.ArmLeft01.rotateAngleX = -0.6F;
					  	this.ArmLeft01.rotateAngleZ = 0.1F;
					  	this.ArmLeft02.rotateAngleZ = 0.39F;
				    	this.ArmRight01.rotateAngleX = -0.6F;
						this.ArmRight01.rotateAngleZ = -0.1F;
						this.ArmRight02.rotateAngleZ = -0.39F;
					  	//leg
					  	addk1 = -0.9F;
					  	addk2 = -0.9F;
					  	this.LegLeft01.rotateAngleY = 0.19F;
						this.LegLeft01.rotateAngleZ = 0F;
						this.LegLeft02.rotateAngleX = 2.67F;
						this.LegLeft02.rotateAngleZ = 0.0175F;
						this.LegLeft02.offsetZ = 0.375F;
						this.LegRight01.rotateAngleY = -0.19F;
						this.LegRight01.rotateAngleZ = 0F;
						this.LegRight02.rotateAngleX = 2.67F;
						this.LegRight02.rotateAngleZ = -0.0175F;
						this.LegRight02.offsetZ = 0.375F;
				    	//tako
				    	this.renderTako = true;
				    	tako1 = this.miscModelList.get(0);
				    	tako1.entity.ticksExisted = ent.getTickExisted();
				    	tako1.entity.posY = 0.34F;
			    	}
			    	else
			    	{
			    		GlStateManager.translate(0F, 0.43F, 0F);
			    		
				    	//Body
					  	this.Head.rotateAngleX -= 0.1F;
					  	this.BodyMain.rotateAngleX = 0F;
				    	this.Butt.rotateAngleX = -0.2F;
				    	this.Butt.offsetY = 0F;
				    	this.BoobL.rotateAngleX -= 0.1F;
				    	this.BoobL.rotateAngleZ = 0.16F;
				    	this.BoobR.rotateAngleX -= 0.1F;
				    	this.BoobR.rotateAngleZ = -0.16F;
				    	//skirt
				    	this.Skirt01.rotateAngleX = -0.05F;
				    	this.Skirt01.offsetY = -0.1F;
				    	this.Skirt02.rotateAngleX = -0.15F;
				    	this.Skirt02.offsetY = -0.1F;
				    	this.Skirt03.rotateAngleX = -0.1F;
				    	this.Skirt03.offsetY = -0.1F;
				    	//arm
					  	this.ArmLeft01.rotateAngleX = -0.46F;
					  	this.ArmLeft01.rotateAngleZ = 0.35F;
				    	this.ArmRight01.rotateAngleX = -0.46F;
						this.ArmRight01.rotateAngleZ = -0.35F;
					  	//leg
					  	addk1 = -0.9F;
					  	addk2 = -0.9F;
					  	this.LegLeft01.rotateAngleY = 0.19F;
						this.LegLeft01.rotateAngleZ = 0F;
						this.LegLeft02.rotateAngleX = 2.67F;
						this.LegLeft02.rotateAngleZ = 0.0175F;
						this.LegLeft02.offsetZ = 0.375F;
						this.LegRight01.rotateAngleY = -0.19F;
						this.LegRight01.rotateAngleZ = 0F;
						this.LegRight02.rotateAngleX = 2.67F;
						this.LegRight02.rotateAngleZ = -0.0175F;
						this.LegRight02.offsetZ = 0.375F;
			    	}
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.51F, 0F);
		    		
			    	//body
			    	this.Head.rotateAngleX -= 0.7F;
			    	this.BodyMain.rotateAngleX = 0.35F;
			    	this.Skirt01.rotateAngleX = -0.23F;
			    	this.Skirt01.offsetY = -0.23F;
			    	this.Skirt02.rotateAngleX = -0.2F;
			    	this.Skirt02.offsetY = -0.17F;
			    	this.Skirt03.rotateAngleX = -0.2F;
			    	this.Skirt03.offsetY = -0.15F;
			    	this.Collar01.rotateAngleX -= 0.35F;
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
					//hair
					this.Hair01.rotateAngleX += 0.2F;
					this.Hair02.rotateAngleX += 0.5F;
					this.Hair03.rotateAngleX += 0.4F;
		    	}
	    	}
	    }//end ridding
    
	    //攻擊動作    
	    if (ent.getAttackTick() > 0)
	    {
	    	if (ent.getAttackTick() > 20)
	    	{
	    		//arm
		    	this.ArmLeft01.rotateAngleX = -1.7F + this.Head.rotateAngleX * 0.75F;
		    	this.ArmLeft01.rotateAngleY = -0.2F;
		    	this.ArmLeft01.rotateAngleZ = 0F;
		    	this.ArmLeft02.rotateAngleX = 0F;
		    	this.ArmLeft02.rotateAngleY = 0F;
			    this.ArmLeft02.rotateAngleZ = 0F;
			    this.ArmLeft02.offsetX = 0F;
			    this.ArmLeft02.offsetZ = 0F;
	    	}
	    	
	    	this.GlowBodyMain2a.rotateAngleX = this.ArmLeft01.rotateAngleX * -0.3F;
	    	
	    	//跑道顯示
	    	setRoad(ent.getAttackTick());
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
	    headX = this.Head.rotateAngleX * -0.5F;
		this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.19F;
	  	this.HairL02.rotateAngleX = -angleX1 * 0.04F + headX + 0.17F;
	  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.19F;
	  	this.HairR02.rotateAngleX = -angleX1 * 0.04F + headX + 0.17F;
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	this.Hair03.rotateAngleZ = headZ;
	  	this.HairL01.rotateAngleZ = headZ - 0.087F;
	  	this.HairL02.rotateAngleZ = headZ + 0.087F;
	  	this.HairR01.rotateAngleZ = headZ + 0.087F;
	  	this.HairR02.rotateAngleZ = headZ - 0.052F;
	  	
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
    private void setRoad(int attackTime)
    {
		switch (attackTime)
		{
		case 50:
		case 26:
			this.EquipSR01.isHidden = false;
			this.EquipSR02.isHidden = true;
			this.EquipSR01b.isHidden = false;
			this.EquipSR02b.isHidden = true;
			this.EquipSR01c.isHidden = false;
			this.EquipSR02c.isHidden = true;
			this.EquipSR01.offsetZ = 0.7F;
			this.EquipSR01b.offsetZ = 0.7F;
			this.EquipSR01c.offsetZ = 0.7F;
		break;
		case 49:
		case 27:
			this.EquipSR01.isHidden = false;
			this.EquipSR02.isHidden = false;
			this.EquipSR03.isHidden = true;
			this.EquipSR01b.isHidden = false;
			this.EquipSR02b.isHidden = false;
			this.EquipSR03b.isHidden = true;
			this.EquipSR01c.isHidden = false;
			this.EquipSR02c.isHidden = false;
			this.EquipSR03c.isHidden = true;
			this.EquipSR01.offsetZ = 0.45F;
			this.EquipSR01b.offsetZ = 0.45F;
			this.EquipSR01c.offsetZ = 0.45F;
			this.EquipSR02.offsetZ = 0.25F;
			this.EquipSR02b.offsetZ = 0.25F;
			this.EquipSR02c.offsetZ = 0.25F;
		break;
		case 48:
		case 28:
			this.EquipSR01.isHidden = false;
			this.EquipSR02.isHidden = false;
			this.EquipSR03.isHidden = false;
			this.EquipSR04.isHidden = true;
			this.EquipSR01b.isHidden = false;
			this.EquipSR02b.isHidden = false;
			this.EquipSR03b.isHidden = false;
			this.EquipSR04b.isHidden = true;
			this.EquipSR01c.isHidden = false;
			this.EquipSR02c.isHidden = false;
			this.EquipSR03c.isHidden = false;
			this.EquipSR04c.isHidden = true;
			this.EquipSR01.offsetZ = 0.25F;
			this.EquipSR01b.offsetZ = 0.25F;
			this.EquipSR01c.offsetZ = 0.25F;
			this.EquipSR02.offsetZ = 0.2F;
			this.EquipSR02b.offsetZ = 0.2F;
			this.EquipSR02c.offsetZ = 0.2F;
			this.EquipSR03.offsetZ = 0.25F;
			this.EquipSR03b.offsetZ = 0.25F;
			this.EquipSR03c.offsetZ = 0.25F;
		break;
		case 47:
		case 29:
			this.EquipSR01.isHidden = false;
			this.EquipSR02.isHidden = false;
			this.EquipSR03.isHidden = false;
			this.EquipSR04.isHidden = false;
			this.EquipSR05.isHidden = true;
			this.EquipSR01b.isHidden = false;
			this.EquipSR02b.isHidden = false;
			this.EquipSR03b.isHidden = false;
			this.EquipSR04b.isHidden = false;
			this.EquipSR01c.isHidden = false;
			this.EquipSR02c.isHidden = false;
			this.EquipSR03c.isHidden = false;
			this.EquipSR04c.isHidden = false;
			this.EquipSR05c.isHidden = true;
			this.EquipSR01.offsetZ = 0.1F;
			this.EquipSR01b.offsetZ = 0.1F;
			this.EquipSR01c.offsetZ = 0.1F;
			this.EquipSR02.offsetZ = 0.15F;
			this.EquipSR02b.offsetZ = 0.15F;
			this.EquipSR02c.offsetZ = 0.15F;
			this.EquipSR03.offsetZ = 0.2F;
			this.EquipSR03b.offsetZ = 0.2F;
			this.EquipSR03c.offsetZ = 0.2F;
			this.EquipSR04.offsetZ = 0.25F;
			this.EquipSR04b.offsetZ = 0.25F;
			this.EquipSR04c.offsetZ = 0.25F;
		break;
		case 46:
		case 30:
			this.EquipSR01.isHidden = false;
			this.EquipSR02.isHidden = false;
			this.EquipSR03.isHidden = false;
			this.EquipSR04.isHidden = false;
			this.EquipSR05.isHidden = false;
			this.EquipSR01b.isHidden = false;
			this.EquipSR02b.isHidden = false;
			this.EquipSR03b.isHidden = false;
			this.EquipSR04b.isHidden = false;
			this.EquipSR01c.isHidden = false;
			this.EquipSR02c.isHidden = false;
			this.EquipSR03c.isHidden = false;
			this.EquipSR04c.isHidden = false;
			this.EquipSR05c.isHidden = false;
			this.EquipSR01.offsetZ = 0F;
			this.EquipSR01b.offsetZ = 0F;
			this.EquipSR01c.offsetZ = 0F;
			this.EquipSR02.offsetZ = 0.1F;
			this.EquipSR02b.offsetZ = 0.1F;
			this.EquipSR02c.offsetZ = 0.1F;
			this.EquipSR03.offsetZ = 0.15F;
			this.EquipSR03b.offsetZ = 0.15F;
			this.EquipSR03c.offsetZ = 0.15F;
			this.EquipSR04.offsetZ = 0.2F;
			this.EquipSR04b.offsetZ = 0.2F;
			this.EquipSR04c.offsetZ = 0.2F;
			this.EquipSR05.offsetZ = 0.25F;
			this.EquipSR05c.offsetZ = 0.25F;
		break;
		case 45:
		case 31:
			this.EquipSR01.isHidden = false;
			this.EquipSR02.isHidden = false;
			this.EquipSR03.isHidden = false;
			this.EquipSR04.isHidden = false;
			this.EquipSR05.isHidden = false;
			this.EquipSR01b.isHidden = false;
			this.EquipSR02b.isHidden = false;
			this.EquipSR03b.isHidden = false;
			this.EquipSR04b.isHidden = false;
			this.EquipSR01c.isHidden = false;
			this.EquipSR02c.isHidden = false;
			this.EquipSR03c.isHidden = false;
			this.EquipSR04c.isHidden = false;
			this.EquipSR05c.isHidden = false;
			this.EquipSR01.offsetZ = 0F;
			this.EquipSR01b.offsetZ = 0F;
			this.EquipSR01c.offsetZ = 0F;
			this.EquipSR02.offsetZ = 0F;
			this.EquipSR02b.offsetZ = 0F;
			this.EquipSR02c.offsetZ = 0F;
			this.EquipSR03.offsetZ = 0.1F;
			this.EquipSR03b.offsetZ = 0.1F;
			this.EquipSR03c.offsetZ = 0.1F;
			this.EquipSR04.offsetZ = 0.15F;
			this.EquipSR04b.offsetZ = 0.15F;
			this.EquipSR04c.offsetZ = 0.15F;
			this.EquipSR05.offsetZ = 0.2F;
			this.EquipSR05c.offsetZ = 0.2F;
		break;
		case 44:
		case 32:
			this.EquipSR01.isHidden = false;
			this.EquipSR02.isHidden = false;
			this.EquipSR03.isHidden = false;
			this.EquipSR04.isHidden = false;
			this.EquipSR05.isHidden = false;
			this.EquipSR01b.isHidden = false;
			this.EquipSR02b.isHidden = false;
			this.EquipSR03b.isHidden = false;
			this.EquipSR04b.isHidden = false;
			this.EquipSR01c.isHidden = false;
			this.EquipSR02c.isHidden = false;
			this.EquipSR03c.isHidden = false;
			this.EquipSR04c.isHidden = false;
			this.EquipSR05c.isHidden = false;
			this.EquipSR01.offsetZ = 0F;
			this.EquipSR01b.offsetZ = 0F;
			this.EquipSR01c.offsetZ = 0F;
			this.EquipSR02.offsetZ = 0F;
			this.EquipSR02b.offsetZ = 0F;
			this.EquipSR02c.offsetZ = 0F;
			this.EquipSR03.offsetZ = 0F;
			this.EquipSR03b.offsetZ = 0F;
			this.EquipSR03c.offsetZ = 0F;
			this.EquipSR04.offsetZ = 0.1F;
			this.EquipSR04b.offsetZ = 0.1F;
			this.EquipSR04c.offsetZ = 0.1F;
			this.EquipSR05.offsetZ = 0.15F;
			this.EquipSR05c.offsetZ = 0.15F;
		break;
		case 43:
		case 33:
			this.EquipSR01.isHidden = false;
			this.EquipSR02.isHidden = false;
			this.EquipSR03.isHidden = false;
			this.EquipSR04.isHidden = false;
			this.EquipSR05.isHidden = false;
			this.EquipSR01b.isHidden = false;
			this.EquipSR02b.isHidden = false;
			this.EquipSR03b.isHidden = false;
			this.EquipSR04b.isHidden = false;
			this.EquipSR01c.isHidden = false;
			this.EquipSR02c.isHidden = false;
			this.EquipSR03c.isHidden = false;
			this.EquipSR04c.isHidden = false;
			this.EquipSR05c.isHidden = false;
			this.EquipSR01.offsetZ = 0F;
			this.EquipSR01b.offsetZ = 0F;
			this.EquipSR01c.offsetZ = 0F;
			this.EquipSR02.offsetZ = 0F;
			this.EquipSR02b.offsetZ = 0F;
			this.EquipSR02c.offsetZ = 0F;
			this.EquipSR03.offsetZ = 0F;
			this.EquipSR03b.offsetZ = 0F;
			this.EquipSR03c.offsetZ = 0F;
			this.EquipSR04.offsetZ = 0F;
			this.EquipSR04b.offsetZ = 0F;
			this.EquipSR04c.offsetZ = 0F;
			this.EquipSR05.offsetZ = 0.1F;
			this.EquipSR05c.offsetZ = 0.1F;
		break;
		default:
			if (attackTime < 47 && attackTime > 29)
			{
				this.EquipSR01.isHidden = false;
				this.EquipSR02.isHidden = false;
				this.EquipSR03.isHidden = false;
				this.EquipSR04.isHidden = false;
				this.EquipSR05.isHidden = false;
				this.EquipSR01b.isHidden = false;
				this.EquipSR02b.isHidden = false;
				this.EquipSR03b.isHidden = false;
				this.EquipSR04b.isHidden = false;
				this.EquipSR01c.isHidden = false;
				this.EquipSR02c.isHidden = false;
				this.EquipSR03c.isHidden = false;
				this.EquipSR04c.isHidden = false;
				this.EquipSR05c.isHidden = false;
				this.EquipSR01.offsetZ = 0F;
				this.EquipSR01b.offsetZ = 0F;
				this.EquipSR01c.offsetZ = 0F;
				this.EquipSR02.offsetZ = 0F;
				this.EquipSR02b.offsetZ = 0F;
				this.EquipSR02c.offsetZ = 0F;
				this.EquipSR03.offsetZ = 0F;
				this.EquipSR03b.offsetZ = 0F;
				this.EquipSR03c.offsetZ = 0F;
				this.EquipSR04.offsetZ = 0F;
				this.EquipSR04b.offsetZ = 0F;
				this.EquipSR04c.offsetZ = 0F;
				this.EquipSR05.offsetZ = 0F;
				this.EquipSR05c.offsetZ = 0F;
			}		
		break;
		}
	}
    
    @Override
    public boolean shouldRenderMiscModel(int miscID)
	{
		return this.renderTako;
	}
    
    
}