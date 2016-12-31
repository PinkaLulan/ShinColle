package com.lulan.shincolle.client.model;

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
 * ModelMountCaH - PinkaLulan
 * Created using Tabula 4.1.1  2016/5/15
 */
public class ModelMountCaH extends ModelBase
{
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Seat01;
    public ModelRenderer Back01;
    public ModelRenderer Back02;
    public ModelRenderer Back03;
    public ModelRenderer Back04;
    public ModelRenderer WingL02;
    public ModelRenderer WingR02;
    public ModelRenderer CannonL01;
    public ModelRenderer CannonR01;
    public ModelRenderer Tube01a;
    public ModelRenderer Tube02a;
    public ModelRenderer Cannon01a;
    public ModelRenderer Cannon01a_1;
    public ModelRenderer Cannon01a_2;
    public ModelRenderer Cannon01a_3;
    public ModelRenderer Cannon01a_4;
    public ModelRenderer Cannon01a_5;
    public ModelRenderer Cannon01a_6;
    public ModelRenderer Cannon01a_7;
    public ModelRenderer Tube01a_1;
    public ModelRenderer Tube01a_2;
    public ModelRenderer Head01;
    public ModelRenderer Jaw01;
    public ModelRenderer Head02;
    public ModelRenderer HeadTooth01;
    public ModelRenderer HeadTooth02;
    public ModelRenderer Jaw02;
    public ModelRenderer JawTooth01;
    public ModelRenderer JawTooth02;
    public ModelRenderer CannonL02;
    public ModelRenderer CannonR02;
    public ModelRenderer Tube01b;
    public ModelRenderer Tube02b;
    public ModelRenderer Cannon01b;
    public ModelRenderer Cannon01c;
    public ModelRenderer Cannon01b_1;
    public ModelRenderer Cannon01c_1;
    public ModelRenderer Cannon01b_2;
    public ModelRenderer Cannon01c_2;
    public ModelRenderer Cannon01b_3;
    public ModelRenderer Cannon01c_3;
    public ModelRenderer Cannon01b_4;
    public ModelRenderer Cannon01c_4;
    public ModelRenderer Cannon01b_5;
    public ModelRenderer Cannon01c_5;
    public ModelRenderer Cannon01b_6;
    public ModelRenderer Cannon01c_6;
    public ModelRenderer Cannon01b_7;
    public ModelRenderer Cannon01c_7;
    public ModelRenderer Tube01b_1;
    public ModelRenderer Tube01b_2;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowBodyMain2;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowJaw01;
    public ModelRenderer GlowHead01;
    public ModelRenderer GlowCannonL01;
    public ModelRenderer GlowCannonR01;
    
    public ModelMountCaH()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.CannonR02 = new ModelRenderer(this, 0, 9);
        this.CannonR02.setRotationPoint(0.0F, -3.2F, -7.5F);
        this.CannonR02.addBox(-1.0F, -1.0F, -12.0F, 2, 2, 12, 0.0F);
        this.setRotateAngle(CannonR02, -0.13962634015954636F, 0.0F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.BodyMain.addBox(-6.5F, 0.0F, 0.0F, 13, 12, 8, 0.0F);
        this.Back02 = new ModelRenderer(this, 0, 0);
        this.Back02.setRotationPoint(0.0F, -7.0F, 6.0F);
        this.Back02.addBox(-5.0F, 0.0F, 0.0F, 10, 14, 4, 0.0F);
        this.Cannon01a_7 = new ModelRenderer(this, 20, 8);
        this.Cannon01a_7.setRotationPoint(-12.5F, 7.5F, 5.0F);
        this.Cannon01a_7.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Cannon01a_7, -0.17453292519943295F, 0.5235987755982988F, 0.0F);
        this.Cannon01c = new ModelRenderer(this, 4, 8);
        this.Cannon01c.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Cannon01c.addBox(-0.5F, -0.5F, -7.0F, 1, 1, 7, 0.0F);
        this.WingL02 = new ModelRenderer(this, 0, 41);
        this.WingL02.setRotationPoint(6.0F, -2.0F, 6.0F);
        this.WingL02.addBox(0.0F, -3.0F, -14.0F, 4, 6, 17, 0.0F);
        this.setRotateAngle(WingL02, 0.0F, -0.10471975511965977F, 0.0F);
        this.Cannon01b_3 = new ModelRenderer(this, 0, 0);
        this.Cannon01b_3.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.Cannon01b_3.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 3, 0.0F);
        this.Seat01 = new ModelRenderer(this, 0, 0);
        this.Seat01.setRotationPoint(0.0F, -10.5F, 0.3F);
        this.Seat01.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 2, 0.0F);
        this.setRotateAngle(Seat01, -0.10471975511965977F, 0.0F, 0.0F);
        this.HeadTooth02 = new ModelRenderer(this, 0, 23);
        this.HeadTooth02.setRotationPoint(0.0F, 0.0F, -6.4F);
        this.HeadTooth02.addBox(-4.5F, 0.0F, -4.5F, 9, 4, 9, 0.0F);
        this.setRotateAngle(HeadTooth02, -0.07504915783575616F, 0.7853981633974483F, -0.05235987755982988F);
        this.Cannon01a = new ModelRenderer(this, 19, 0);
        this.Cannon01a.setRotationPoint(8.0F, 12.0F, 4.0F);
        this.Cannon01a.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Cannon01a, 0.20943951023931953F, -0.2617993877991494F, 0.0F);
        this.Cannon01b_1 = new ModelRenderer(this, 0, 0);
        this.Cannon01b_1.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.Cannon01b_1.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 3, 0.0F);
        this.Cannon01c_2 = new ModelRenderer(this, 8, 12);
        this.Cannon01c_2.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Cannon01c_2.addBox(-0.5F, -0.5F, -7.0F, 1, 1, 7, 0.0F);
        this.Tube02a = new ModelRenderer(this, 0, 0);
        this.Tube02a.setRotationPoint(-3.0F, 2.0F, 9.0F);
        this.Tube02a.addBox(-1.0F, -7.0F, -1.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube02a, -0.7853981633974483F, -0.13962634015954636F, -0.2617993877991494F);
        this.Cannon01a_6 = new ModelRenderer(this, 20, 8);
        this.Cannon01a_6.setRotationPoint(-9.0F, 8.0F, 4.0F);
        this.Cannon01a_6.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Cannon01a_6, -0.13962634015954636F, 0.2617993877991494F, 0.0F);
        this.Jaw02 = new ModelRenderer(this, 0, 0);
        this.Jaw02.setRotationPoint(0.0F, -0.1F, -15.0F);
        this.Jaw02.addBox(-5.0F, 0.0F, -5.0F, 10, 6, 10, 0.0F);
        this.setRotateAngle(Jaw02, 0.0F, 0.7853981633974483F, 0.0F);
        this.CannonR01 = new ModelRenderer(this, 9, 0);
        this.CannonR01.setRotationPoint(-4.0F, -6.0F, 9.0F);
        this.CannonR01.addBox(-3.5F, -5.0F, -8.0F, 7, 5, 8, 0.0F);
        this.setRotateAngle(CannonR01, -0.6981317007977318F, 0.10471975511965977F, 0.0F);
        this.Cannon01b_6 = new ModelRenderer(this, 0, 0);
        this.Cannon01b_6.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.Cannon01b_6.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 3, 0.0F);
        this.Cannon01a_1 = new ModelRenderer(this, 20, 8);
        this.Cannon01a_1.setRotationPoint(12.0F, 11.0F, 5.0F);
        this.Cannon01a_1.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Cannon01a_1, 0.13962634015954636F, -0.41887902047863906F, 0.0F);
        this.Cannon01c_5 = new ModelRenderer(this, 12, 12);
        this.Cannon01c_5.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Cannon01c_5.addBox(-0.5F, -0.5F, -7.0F, 1, 1, 7, 0.0F);
        this.Head01 = new ModelRenderer(this, 0, 0);
        this.Head01.setRotationPoint(0.0F, 5.8F, 5.0F);
        this.Head01.addBox(-7.0F, -6.0F, -15.0F, 14, 6, 13, 0.0F);
        this.Cannon01a_5 = new ModelRenderer(this, 20, 8);
        this.Cannon01a_5.setRotationPoint(-12.0F, 11.0F, 5.0F);
        this.Cannon01a_5.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Cannon01a_5, 0.20943951023931953F, 0.3141592653589793F, 0.0F);
        this.Cannon01c_7 = new ModelRenderer(this, 16, 12);
        this.Cannon01c_7.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Cannon01c_7.addBox(-0.5F, -0.5F, -7.0F, 1, 1, 7, 0.0F);
        this.Tube01b_2 = new ModelRenderer(this, 0, 0);
        this.Tube01b_2.setRotationPoint(0.0F, -7.0F, 1.0F);
        this.Tube01b_2.addBox(0.0F, -7.0F, -1.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube01b_2, 1.3962634015954636F, 0.0F, 0.0F);
        this.CannonL01 = new ModelRenderer(this, 9, 0);
        this.CannonL01.setRotationPoint(4.0F, -6.0F, 9.0F);
        this.CannonL01.addBox(-3.5F, -5.0F, -8.0F, 7, 5, 8, 0.0F);
        this.setRotateAngle(CannonL01, -0.6981317007977318F, -0.10471975511965977F, 0.0F);
        this.Cannon01a_4 = new ModelRenderer(this, 20, 8);
        this.Cannon01a_4.setRotationPoint(-8.0F, 12.0F, 4.0F);
        this.Cannon01a_4.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Cannon01a_4, 0.20943951023931953F, 0.20943951023931953F, 0.0F);
        this.Tube01a_1 = new ModelRenderer(this, 0, 0);
        this.Tube01a_1.setRotationPoint(11.0F, 8.0F, 7.0F);
        this.Tube01a_1.addBox(0.0F, -7.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube01a_1, -0.6981317007977318F, 0.0F, -0.3490658503988659F);
        this.Neck = new ModelRenderer(this, 54, 0);
        this.Neck.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Neck.addBox(-6.0F, 0.0F, 0.0F, 12, 12, 5, 0.0F);
        this.Cannon01b_2 = new ModelRenderer(this, 0, 0);
        this.Cannon01b_2.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.Cannon01b_2.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 3, 0.0F);
        this.Head02 = new ModelRenderer(this, 0, 0);
        this.Head02.setRotationPoint(0.0F, -5.9F, -15.0F);
        this.Head02.addBox(-5.0F, 0.0F, -5.0F, 10, 6, 10, 0.0F);
        this.setRotateAngle(Head02, 0.0F, 0.7853981633974483F, 0.0F);
        this.Tube01b = new ModelRenderer(this, 0, 0);
        this.Tube01b.setRotationPoint(0.0F, -7.0F, 1.0F);
        this.Tube01b.addBox(0.0F, -7.0F, -1.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube01b, 1.3962634015954636F, 0.0F, 0.0F);
        this.Cannon01c_1 = new ModelRenderer(this, 20, 12);
        this.Cannon01c_1.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Cannon01c_1.addBox(-0.5F, -0.5F, -7.0F, 1, 1, 7, 0.0F);
        this.HeadTooth01 = new ModelRenderer(this, 24, 24);
        this.HeadTooth01.setRotationPoint(0.0F, 1.9F, -7.5F);
        this.HeadTooth01.addBox(-6.5F, 0.0F, -6.5F, 13, 4, 12, 0.0F);
        this.setRotateAngle(HeadTooth01, -0.13962634015954636F, 0.0F, 3.141592653589793F);
        this.Cannon01b_5 = new ModelRenderer(this, 0, 0);
        this.Cannon01b_5.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.Cannon01b_5.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 3, 0.0F);
        this.Cannon01a_3 = new ModelRenderer(this, 20, 8);
        this.Cannon01a_3.setRotationPoint(12.5F, 7.5F, 5.0F);
        this.Cannon01a_3.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Cannon01a_3, -0.05235987755982988F, -0.5235987755982988F, 0.0F);
        this.Back01 = new ModelRenderer(this, 0, 0);
        this.Back01.setRotationPoint(0.0F, -9.0F, 1.0F);
        this.Back01.addBox(-6.0F, 0.0F, 0.0F, 12, 9, 5, 0.0F);
        this.Back04 = new ModelRenderer(this, 0, 0);
        this.Back04.setRotationPoint(0.0F, 7.0F, 6.0F);
        this.Back04.addBox(-8.0F, 0.0F, 0.0F, 16, 2, 3, 0.0F);
        this.Cannon01b_4 = new ModelRenderer(this, 0, 0);
        this.Cannon01b_4.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.Cannon01b_4.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 3, 0.0F);
        this.Tube02b = new ModelRenderer(this, 0, 0);
        this.Tube02b.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.Tube02b.addBox(-1.0F, -7.0F, -1.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube02b, 1.3962634015954636F, 0.0F, 0.0F);
        this.Cannon01c_3 = new ModelRenderer(this, 24, 12);
        this.Cannon01c_3.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Cannon01c_3.addBox(-0.5F, -0.5F, -7.0F, 1, 1, 7, 0.0F);
        this.Cannon01b_7 = new ModelRenderer(this, 0, 0);
        this.Cannon01b_7.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.Cannon01b_7.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 3, 0.0F);
        this.Cannon01a_2 = new ModelRenderer(this, 20, 8);
        this.Cannon01a_2.setRotationPoint(9.0F, 8.0F, 4.0F);
        this.Cannon01a_2.addBox(-2.0F, -2.0F, -3.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(Cannon01a_2, -0.13962634015954636F, -0.3141592653589793F, 0.0F);
        this.WingR02 = new ModelRenderer(this, 0, 41);
        this.WingR02.mirror = true;
        this.WingR02.setRotationPoint(-6.0F, -2.0F, 6.0F);
        this.WingR02.addBox(-4.0F, -3.0F, -14.0F, 4, 6, 17, 0.0F);
        this.setRotateAngle(WingR02, 0.0F, 0.10471975511965977F, 0.0F);
        this.Cannon01b = new ModelRenderer(this, 0, 0);
        this.Cannon01b.setRotationPoint(0.0F, 0.0F, -1.8F);
        this.Cannon01b.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 3, 0.0F);
        this.Tube01a_2 = new ModelRenderer(this, 0, 0);
        this.Tube01a_2.setRotationPoint(-12.0F, 8.0F, 6.7F);
        this.Tube01a_2.addBox(0.0F, -7.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube01a_2, -0.6981317007977318F, 0.0F, 0.3490658503988659F);
        this.Tube01b_1 = new ModelRenderer(this, 0, 0);
        this.Tube01b_1.setRotationPoint(0.0F, -7.0F, 1.0F);
        this.Tube01b_1.addBox(0.0F, -7.0F, -1.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube01b_1, 1.3962634015954636F, 0.0F, 0.0F);
        this.JawTooth02 = new ModelRenderer(this, 0, 23);
        this.JawTooth02.setRotationPoint(0.0F, 0.0F, -13.9F);
        this.JawTooth02.addBox(-4.5F, 0.0F, -4.5F, 9, 4, 9, 0.0F);
        this.setRotateAngle(JawTooth02, -0.07504915783575616F, 0.7853981633974483F, -0.05235987755982988F);
        this.CannonL02 = new ModelRenderer(this, 0, 9);
        this.CannonL02.setRotationPoint(0.0F, -3.2F, -7.5F);
        this.CannonL02.addBox(-1.0F, -1.0F, -12.0F, 2, 2, 12, 0.0F);
        this.setRotateAngle(CannonL02, -0.2617993877991494F, 0.0F, 0.0F);
        this.Jaw01 = new ModelRenderer(this, 0, 0);
        this.Jaw01.mirror = true;
        this.Jaw01.setRotationPoint(0.0F, 7.0F, 6.0F);
        this.Jaw01.addBox(-7.0F, 0.0F, -15.0F, 14, 6, 13, 0.0F);
        this.setRotateAngle(Jaw01, 0.3141592653589793F, 0.0F, 0.0F);
        this.Cannon01c_6 = new ModelRenderer(this, 28, 12);
        this.Cannon01c_6.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Cannon01c_6.addBox(-0.5F, -0.5F, -7.0F, 1, 1, 7, 0.0F);
        this.JawTooth01 = new ModelRenderer(this, 24, 24);
        this.JawTooth01.setRotationPoint(0.0F, -0.8F, -0.8F);
        this.JawTooth01.addBox(-6.5F, 0.0F, -14.0F, 13, 4, 12, 0.0F);
        this.setRotateAngle(JawTooth01, -0.13962634015954636F, 0.0F, 0.0F);
        this.Back03 = new ModelRenderer(this, 0, 0);
        this.Back03.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.Back03.addBox(-9.0F, 0.0F, 0.0F, 18, 14, 7, 0.0F);
        this.Tube01a = new ModelRenderer(this, 0, 0);
        this.Tube01a.setRotationPoint(2.0F, 1.0F, 9.0F);
        this.Tube01a.addBox(0.0F, -7.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube01a, -0.7853981633974483F, 0.8726646259971648F, 0.2617993877991494F);
        this.Cannon01c_4 = new ModelRenderer(this, 32, 12);
        this.Cannon01c_4.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Cannon01c_4.addBox(-0.5F, -0.5F, -7.0F, 1, 1, 7, 0.0F);
        this.BodyMain.addChild(this.Back02);
        this.BodyMain.addChild(this.Cannon01a_7);
        this.Cannon01b.addChild(this.Cannon01c);
        this.Cannon01a_3.addChild(this.Cannon01b_3);
        this.BodyMain.addChild(this.Seat01);
        this.BodyMain.addChild(this.Cannon01a);
        this.Cannon01a_1.addChild(this.Cannon01b_1);
        this.Cannon01b_2.addChild(this.Cannon01c_2);
        this.BodyMain.addChild(this.Tube02a);
        this.BodyMain.addChild(this.Cannon01a_6);
        this.Jaw01.addChild(this.Jaw02);
        this.BodyMain.addChild(this.CannonR01);
        this.Cannon01a_6.addChild(this.Cannon01b_6);
        this.BodyMain.addChild(this.Cannon01a_1);
        this.Cannon01b_5.addChild(this.Cannon01c_5);
        this.Neck.addChild(this.Head01);
        this.BodyMain.addChild(this.Cannon01a_5);
        this.Cannon01b_7.addChild(this.Cannon01c_7);
        this.Tube01a_2.addChild(this.Tube01b_2);
        this.BodyMain.addChild(this.CannonL01);
        this.BodyMain.addChild(this.Cannon01a_4);
        this.BodyMain.addChild(this.Tube01a_1);
        this.BodyMain.addChild(this.Neck);
        this.Cannon01a_2.addChild(this.Cannon01b_2);
        this.Head01.addChild(this.Head02);
        this.Tube01a.addChild(this.Tube01b);
        this.Cannon01b_1.addChild(this.Cannon01c_1);
        this.Cannon01a_5.addChild(this.Cannon01b_5);
        this.BodyMain.addChild(this.Cannon01a_3);
        this.BodyMain.addChild(this.Back01);
        this.BodyMain.addChild(this.Back04);
        this.Cannon01a_4.addChild(this.Cannon01b_4);
        this.Tube02a.addChild(this.Tube02b);
        this.Cannon01b_3.addChild(this.Cannon01c_3);
        this.Cannon01a_7.addChild(this.Cannon01b_7);
        this.BodyMain.addChild(this.Cannon01a_2);
        this.Cannon01a.addChild(this.Cannon01b);
        this.BodyMain.addChild(this.Tube01a_2);
        this.Tube01a_1.addChild(this.Tube01b_1);
        this.Neck.addChild(this.Jaw01);
        this.Cannon01b_6.addChild(this.Cannon01c_6);
        this.BodyMain.addChild(this.Back03);
        this.BodyMain.addChild(this.Tube01a);
        this.Cannon01b_4.addChild(this.Cannon01c_4);
        
        //發光支架1
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.GlowJaw01 = new ModelRenderer(this, 0, 0);
        this.GlowJaw01.setRotationPoint(0.0F, 7.0F, 6.0F);
        this.setRotateAngle(GlowJaw01, 0.3141592653589793F, 0.0F, 0.0F);
        this.GlowHead01 = new ModelRenderer(this, 0, 0);
        this.GlowHead01.setRotationPoint(0.0F, 5.8F, 5.0F);
        this.GlowCannonL01 = new ModelRenderer(this, 9, 0);
        this.GlowCannonL01.setRotationPoint(4.0F, -6.0F, 9.0F);
        this.setRotateAngle(GlowCannonL01, -0.6981317007977318F, -0.10471975511965977F, 0.0F);
        this.GlowCannonR01 = new ModelRenderer(this, 9, 0);
        this.GlowCannonR01.setRotationPoint(-4.0F, -6.0F, 9.0F);
        this.setRotateAngle(GlowCannonR01, -0.6981317007977318F, 0.10471975511965977F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowJaw01);
        this.GlowJaw01.addChild(this.JawTooth01);
        this.JawTooth01.addChild(this.JawTooth02);
        this.GlowNeck.addChild(this.GlowHead01);
        this.GlowHead01.addChild(this.HeadTooth01);
        this.HeadTooth01.addChild(this.HeadTooth02);
        
        this.GlowBodyMain.addChild(this.GlowCannonL01);
        this.GlowCannonL01.addChild(this.CannonL02);
        this.GlowBodyMain.addChild(this.GlowCannonR01);
        this.GlowCannonR01.addChild(this.CannonR02);
        
        //發光支架2
        this.GlowBodyMain2 = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain2.setRotationPoint(0.0F, 0.0F, 8.0F);
        
        this.GlowBodyMain2.addChild(this.WingL02);
        this.GlowBodyMain2.addChild(this.WingR02);

    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
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
    	GlStateManager.scale(1.1F, 1.1F, 1.1F);
    	
    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	GlStateManager.disableBlend();
    	
    	//light part
    	GlStateManager.disableLighting();
    	GlStateManager.enableCull();
    	
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	
    	float light = 80F + MathHelper.cos(f2 * 0.075F) * 80F;
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, light, light);
    	this.GlowBodyMain2.render(f5);
    	
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
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		
  		//水上漂浮
  		if(((IShipFloating) ent).getShipDepth() > 0)
  		{
  			GlStateManager.translate(0F, angleX * 0.025F + 0.025F, 0F);
    	}
  		
  		GlStateManager.translate(0F, -0.25F, -0.1F);

	    //正常站立動作
	  	//嘴巴
	  	this.Jaw01.rotateAngleX = angleX * 0.025F + 0.32F;
	    //cannon
	  	this.CannonL02.rotateAngleX = angleX * 0.05F - 0.3F;
	  	this.CannonR02.rotateAngleX = -angleX * 0.05F;
	    
    	//seat2 有載人動作
	    if (ent.getStateEmotion(ID.S.Emotion) > 0)
	    {
	    	this.Jaw01.rotateAngleX = 1.0F;
	    }
	    
	    //發光支架
	    this.GlowJaw01.rotateAngleX = this.Jaw01.rotateAngleX;
  	}
  	
  	
}