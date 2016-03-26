package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;

/**
 * ModelMountCaWD - PinkaLulan	2015/7/5
 * Created using Tabula 4.1.1
 */
public class ModelMountCaWD extends ModelBase {
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer WingL01a;
    public ModelRenderer WingR01a;
    public ModelRenderer Seat01;
    public ModelRenderer Back01;
    public ModelRenderer Back02;
    public ModelRenderer WingL03;
    public ModelRenderer WingR03;
    public ModelRenderer WingL04;
    public ModelRenderer WingR04;
    public ModelRenderer Back03;
    public ModelRenderer Back04;
    public ModelRenderer WingL02;
    public ModelRenderer WingR02;
    public ModelRenderer CannonL01;
    public ModelRenderer CannonR01;
    public ModelRenderer Tube01a;
    public ModelRenderer Tube02a;
    public ModelRenderer CannonM01;
    public ModelRenderer Head01;
    public ModelRenderer Jaw01;
    public ModelRenderer Head02;
    public ModelRenderer HeadTooth01;
    public ModelRenderer HeadTooth02;
    public ModelRenderer Jaw02;
    public ModelRenderer JawTooth01;
    public ModelRenderer JawTooth02;
    public ModelRenderer WingL01b;
    public ModelRenderer WingL01c;
    public ModelRenderer WingL01Fire;
    public ModelRenderer WingR01b;
    public ModelRenderer WingR01c;
    public ModelRenderer WingR01Fire;
    public ModelRenderer Seat02;
    public ModelRenderer Seat03;
    public ModelRenderer CannonL02;
    public ModelRenderer CannonR02;
    public ModelRenderer Tube01b;
    public ModelRenderer Tube02b;
    public ModelRenderer CannonM02;
    public ModelRenderer CannonM04;
    public ModelRenderer CannonM03;
    public ModelRenderer CannonM05;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowBodyMain2;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowJaw01;
    public ModelRenderer GlowHead01;
    public ModelRenderer GlowWingL01a;
    public ModelRenderer GlowWingL01a2;
    public ModelRenderer GlowWingL01b;
    public ModelRenderer GlowWingR01a;
    public ModelRenderer GlowWingR01a2;
    public ModelRenderer GlowWingR01b;
    public ModelRenderer GlowCannonL01;
    public ModelRenderer GlowCannonR01;
    public ModelRenderer GlowCannonM01;
    public ModelRenderer GlowCannonM02;
    public ModelRenderer GlowCannonM04;

    public ModelMountCaWD() {
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.BodyMain.addBox(-6.5F, 0.0F, 0.0F, 13, 12, 8, 0.0F);
        this.Neck = new ModelRenderer(this, 54, 0);
        this.Neck.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Neck.addBox(-6.0F, 0.0F, 0.0F, 12, 12, 5, 0.0F);
        this.WingL03 = new ModelRenderer(this, 30, 40);
        this.WingL03.setRotationPoint(7.5F, -0.5F, 11.0F);
        this.WingL03.addBox(0.0F, 0.0F, -20.0F, 2, 4, 20, 0.0F);
        this.setRotateAngle(WingL03, 0.20943951023931953F, -0.2617993877991494F, 0.0F);
        this.Tube01a = new ModelRenderer(this, 0, 0);
        this.Tube01a.setRotationPoint(4.0F, 1.0F, 9.0F);
        this.Tube01a.addBox(0.0F, -7.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube01a, -0.7853981633974483F, 0.8726646259971648F, 0.2617993877991494F);
        this.WingR04 = new ModelRenderer(this, 0, 47);
        this.WingR04.mirror = true;
        this.WingR04.setRotationPoint(-8.0F, 6.0F, 9.0F);
        this.WingR04.addBox(-2.0F, 0.0F, -10.0F, 2, 5, 12, 0.0F);
        this.setRotateAngle(WingR04, 0.20943951023931953F, 0.3490658503988659F, -0.17453292519943295F);
        this.Head02 = new ModelRenderer(this, 0, 0);
        this.Head02.setRotationPoint(0.0F, -5.9F, -15.0F);
        this.Head02.addBox(-5.0F, 0.0F, -5.0F, 10, 6, 10, 0.0F);
        this.setRotateAngle(Head02, 0.0F, 0.7853981633974483F, 0.0F);
        this.WingR03 = new ModelRenderer(this, 30, 40);
        this.WingR03.mirror = true;
        this.WingR03.setRotationPoint(-7.5F, -0.5F, 11.0F);
        this.WingR03.addBox(-2.0F, 0.0F, -20.0F, 2, 4, 20, 0.0F);
        this.setRotateAngle(WingR03, 0.20943951023931953F, 0.2617993877991494F, 0.0F);
        this.Seat03 = new ModelRenderer(this, 0, 0);
        this.Seat03.setRotationPoint(-6.2F, 1.0F, 0.5F);
        this.Seat03.addBox(-2.0F, 0.0F, -9.0F, 2, 10, 9, 0.0F);
        this.setRotateAngle(Seat03, 0.10471975511965977F, 0.10471975511965977F, -0.10471975511965977F);
        this.Seat01 = new ModelRenderer(this, 0, 0);
        this.Seat01.setRotationPoint(0.0F, -10.5F, 0.3F);
        this.Seat01.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 2, 0.0F);
        this.setRotateAngle(Seat01, -0.10471975511965977F, 0.0F, 0.0F);
        this.Tube02b = new ModelRenderer(this, 0, 0);
        this.Tube02b.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.Tube02b.addBox(-1.0F, -7.0F, -1.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube02b, 1.3962634015954636F, 0.0F, 0.0F);
        this.WingR01a = new ModelRenderer(this, 0, 0);
        this.WingR01a.setRotationPoint(-6.0F, 13.5F, -4.0F);
        this.WingR01a.addBox(-7.0F, 0.0F, 0.0F, 7, 2, 2, 0.0F);
        this.setRotateAngle(WingR01a, 0.0F, 0.3490658503988659F, -0.5235987755982988F);
        this.Back01 = new ModelRenderer(this, 0, 0);
        this.Back01.setRotationPoint(0.0F, -9.0F, 1.0F);
        this.Back01.addBox(-6.0F, 0.0F, 0.0F, 12, 9, 5, 0.0F);
        this.JawTooth02 = new ModelRenderer(this, 54, 46);
        this.JawTooth02.setRotationPoint(0.0F, 0.0F, -13.9F);
        this.JawTooth02.addBox(-4.5F, 0.0F, -4.5F, 9, 4, 9, 0.0F);
        this.setRotateAngle(JawTooth02, -0.07504915783575616F, 0.7853981633974483F, -0.05235987755982988F);
        this.HeadTooth01 = new ModelRenderer(this, 78, 48);
        this.HeadTooth01.setRotationPoint(0.0F, 1.9F, -7.5F);
        this.HeadTooth01.addBox(-6.5F, 0.0F, -6.5F, 13, 4, 12, 0.0F);
        this.setRotateAngle(HeadTooth01, -0.13962634015954636F, 0.0F, 3.141592653589793F);
        this.WingL01c = new ModelRenderer(this, 0, 53);
        this.WingL01c.setRotationPoint(3.0F, 0.0F, 0.0F);
        this.WingL01c.addBox(-3.0F, 0.0F, -6.0F, 3, 5, 6, 0.0F);
        this.setRotateAngle(WingL01c, 0.0F, 0.5235987755982988F, 0.0F);
        this.Back03 = new ModelRenderer(this, 0, 0);
        this.Back03.setRotationPoint(0.0F, -5.0F, 0.0F);
        this.Back03.addBox(-9.0F, 0.0F, 0.0F, 18, 14, 7, 0.0F);
        this.CannonM02 = new ModelRenderer(this, 0, 0);
        this.CannonM02.setRotationPoint(1.3F, -1.7F, -3.5F);
        this.CannonM02.addBox(-0.5F, -1.0F, -2.0F, 1, 2, 2, 0.0F);
        this.Head01 = new ModelRenderer(this, 0, 0);
        this.Head01.setRotationPoint(0.0F, 5.8F, 5.0F);
        this.Head01.addBox(-7.0F, -6.0F, -15.0F, 14, 6, 13, 0.0F);
        this.HeadTooth02 = new ModelRenderer(this, 54, 46);
        this.HeadTooth02.setRotationPoint(0.0F, 0.0F, -6.4F);
        this.HeadTooth02.addBox(-4.5F, 0.0F, -4.5F, 9, 4, 9, 0.0F);
        this.setRotateAngle(HeadTooth02, -0.07504915783575616F, 0.7853981633974483F, -0.05235987755982988F);
        this.CannonM01 = new ModelRenderer(this, 0, 0);
        this.CannonM01.setRotationPoint(0.0F, -8.5F, 7.0F);
        this.CannonM01.addBox(-3.0F, -3.0F, -4.0F, 6, 3, 6, 0.0F);
        this.setRotateAngle(CannonM01, -0.8726646259971648F, 0.0F, 0.0F);
        this.CannonM04 = new ModelRenderer(this, 0, 0);
        this.CannonM04.setRotationPoint(-1.3F, -1.7F, -3.5F);
        this.CannonM04.addBox(-0.5F, -1.0F, -2.0F, 1, 2, 2, 0.0F);
        this.WingR02 = new ModelRenderer(this, 0, 35);
        this.WingR02.mirror = true;
        this.WingR02.setRotationPoint(-6.0F, -5.0F, 6.0F);
        this.WingR02.addBox(-4.0F, -3.0F, -14.0F, 4, 6, 17, 0.0F);
        this.setRotateAngle(WingR02, 0.0F, 0.17453292519943295F, 0.0F);
        this.WingL01b = new ModelRenderer(this, 25, 39);
        this.WingL01b.setRotationPoint(6.5F, -1.5F, -4.0F);
        this.WingL01b.addBox(0.0F, 0.0F, 0.0F, 3, 5, 8, 0.0F);
        this.setRotateAngle(WingL01b, -0.08726646259971647F, -0.08726646259971647F, 0.0F);
        this.CannonM03 = new ModelRenderer(this, 28, 15);
        this.CannonM03.setRotationPoint(-0.5F, -0.7F, -2.0F);
        this.CannonM03.addBox(0.0F, 0.0F, -6.0F, 1, 1, 6, 0.0F);
        this.WingR01Fire = new ModelRenderer(this, 116, 48);
        this.WingR01Fire.setRotationPoint(-1.5F, 2.5F, 8.1F);
        this.WingR01Fire.addBox(-1.0F, -2.0F, 0.0F, 2, 4, 4, 0.0F);
        this.Jaw02 = new ModelRenderer(this, 0, 0);
        this.Jaw02.setRotationPoint(0.0F, -0.1F, -15.0F);
        this.Jaw02.addBox(-5.0F, 0.0F, -5.0F, 10, 6, 10, 0.0F);
        this.setRotateAngle(Jaw02, 0.0F, 0.7853981633974483F, 0.0F);
        this.WingL01Fire = new ModelRenderer(this, 116, 48);
        this.WingL01Fire.setRotationPoint(1.5F, 2.5F, 8.1F);
        this.WingL01Fire.addBox(-1.0F, -2.0F, 0.0F, 2, 4, 4, 0.0F);
        this.CannonL02 = new ModelRenderer(this, 0, 9);
        this.CannonL02.setRotationPoint(0.0F, -3.2F, -7.5F);
        this.CannonL02.addBox(-1.0F, -1.0F, -12.0F, 2, 2, 12, 0.0F);
        this.setRotateAngle(CannonL02, -0.2617993877991494F, 0.0F, 0.0F);
        this.WingL04 = new ModelRenderer(this, 0, 47);
        this.WingL04.setRotationPoint(8.0F, 6.0F, 9.0F);
        this.WingL04.addBox(0.0F, 0.0F, -10.0F, 2, 5, 12, 0.0F);
        this.setRotateAngle(WingL04, 0.20943951023931953F, -0.3490658503988659F, 0.17453292519943295F);
        this.CannonR01 = new ModelRenderer(this, 9, 0);
        this.CannonR01.setRotationPoint(-8.0F, -6.0F, 9.0F);
        this.CannonR01.addBox(-3.5F, -5.0F, -8.0F, 7, 5, 8, 0.0F);
        this.setRotateAngle(CannonR01, -0.5235987755982988F, 0.5235987755982988F, 0.0F);
        this.WingL01a = new ModelRenderer(this, 0, 0);
        this.WingL01a.setRotationPoint(6.0F, 13.5F, -4.0F);
        this.WingL01a.addBox(0.0F, 0.0F, 0.0F, 7, 2, 2, 0.0F);
        this.setRotateAngle(WingL01a, 0.0F, -0.3490658503988659F, 0.5235987755982988F);
        this.WingL02 = new ModelRenderer(this, 0, 35);
        this.WingL02.setRotationPoint(6.0F, -5.0F, 6.0F);
        this.WingL02.addBox(0.0F, -3.0F, -14.0F, 4, 6, 17, 0.0F);
        this.setRotateAngle(WingL02, 0.0F, -0.17453292519943295F, 0.0F);
        this.Jaw01 = new ModelRenderer(this, 0, 0);
        this.Jaw01.mirror = true;
        this.Jaw01.setRotationPoint(0.0F, 7.0F, 6.0F);
        this.Jaw01.addBox(-7.0F, 0.0F, -15.0F, 14, 6, 13, 0.0F);
        this.setRotateAngle(Jaw01, 0.3141592653589793F, 0.0F, 0.0F);
        this.Seat02 = new ModelRenderer(this, 0, 0);
        this.Seat02.setRotationPoint(6.2F, 1.0F, 0.5F);
        this.Seat02.addBox(0.0F, 0.0F, -9.0F, 2, 10, 9, 0.0F);
        this.setRotateAngle(Seat02, 0.10471975511965977F, -0.10471975511965977F, 0.10471975511965977F);
        this.WingR01c = new ModelRenderer(this, 0, 53);
        this.WingR01c.mirror = true;
        this.WingR01c.setRotationPoint(-3.0F, 0.0F, 0.0F);
        this.WingR01c.addBox(0.0F, 0.0F, -6.0F, 3, 5, 6, 0.0F);
        this.setRotateAngle(WingR01c, 0.0F, -0.5235987755982988F, 0.0F);
        this.Tube01b = new ModelRenderer(this, 0, 0);
        this.Tube01b.setRotationPoint(0.0F, -7.0F, 1.0F);
        this.Tube01b.addBox(0.0F, -7.0F, -1.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube01b, 1.3962634015954636F, 0.0F, 0.0F);
        this.Back02 = new ModelRenderer(this, 0, 0);
        this.Back02.setRotationPoint(0.0F, -7.0F, 6.0F);
        this.Back02.addBox(-5.0F, 0.0F, 0.0F, 10, 14, 4, 0.0F);
        this.Back04 = new ModelRenderer(this, 0, 0);
        this.Back04.setRotationPoint(0.0F, 7.0F, 6.0F);
        this.Back04.addBox(-8.0F, 0.0F, 0.0F, 16, 2, 3, 0.0F);
        this.CannonL01 = new ModelRenderer(this, 9, 0);
        this.CannonL01.setRotationPoint(8.0F, -6.0F, 9.0F);
        this.CannonL01.addBox(-3.5F, -5.0F, -8.0F, 7, 5, 8, 0.0F);
        this.setRotateAngle(CannonL01, -0.5235987755982988F, -0.5235987755982988F, 0.0F);
        this.CannonR02 = new ModelRenderer(this, 0, 9);
        this.CannonR02.setRotationPoint(0.0F, -3.2F, -7.5F);
        this.CannonR02.addBox(-1.0F, -1.0F, -12.0F, 2, 2, 12, 0.0F);
        this.CannonM05 = new ModelRenderer(this, 28, 15);
        this.CannonM05.setRotationPoint(-0.5F, -0.7F, -2.0F);
        this.CannonM05.addBox(0.0F, 0.0F, -6.0F, 1, 1, 6, 0.0F);
        this.JawTooth01 = new ModelRenderer(this, 78, 48);
        this.JawTooth01.setRotationPoint(0.0F, -0.8F, -0.8F);
        this.JawTooth01.addBox(-6.5F, 0.0F, -14.0F, 13, 4, 12, 0.0F);
        this.setRotateAngle(JawTooth01, -0.13962634015954636F, 0.0F, 0.0F);
        this.Tube02a = new ModelRenderer(this, 0, 0);
        this.Tube02a.setRotationPoint(-5.0F, 2.0F, 9.0F);
        this.Tube02a.addBox(-1.0F, -7.0F, -1.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(Tube02a, -0.7853981633974483F, -0.13962634015954636F, -0.2617993877991494F);
        this.WingR01b = new ModelRenderer(this, 25, 39);
        this.WingR01b.mirror = true;
        this.WingR01b.setRotationPoint(-6.5F, -1.5F, -4.0F);
        this.WingR01b.addBox(-3.0F, 0.0F, 0.0F, 3, 5, 8, 0.0F);
        this.setRotateAngle(WingR01b, -0.08726646259971647F, 0.08726646259971647F, 0.0F);
        this.BodyMain.addChild(this.Neck);
        this.BodyMain.addChild(this.Tube01a);
        this.Head01.addChild(this.Head02);
        this.Seat01.addChild(this.Seat03);
        this.BodyMain.addChild(this.Seat01);
        this.Tube02a.addChild(this.Tube02b);
        this.BodyMain.addChild(this.WingR01a);
        this.BodyMain.addChild(this.Back01);
        this.BodyMain.addChild(this.Back03);
        this.CannonM01.addChild(this.CannonM02);
        this.Neck.addChild(this.Head01);
        this.BodyMain.addChild(this.CannonM01);
        this.CannonM01.addChild(this.CannonM04);
        this.WingL01a.addChild(this.WingL01b);
        this.Jaw01.addChild(this.Jaw02);
        this.BodyMain.addChild(this.CannonR01);
        this.BodyMain.addChild(this.WingL01a);
        this.Neck.addChild(this.Jaw01);
        this.Seat01.addChild(this.Seat02);
        this.Tube01a.addChild(this.Tube01b);
        this.BodyMain.addChild(this.Back02);
        this.BodyMain.addChild(this.Back04);
        this.BodyMain.addChild(this.CannonL01);
        this.BodyMain.addChild(this.Tube02a);
        
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
        this.GlowWingL01a = new ModelRenderer(this, 0, 0);
        this.GlowWingL01a.setRotationPoint(6.0F, 13.5F, -4.0F);
        this.setRotateAngle(GlowWingL01a, 0.0F, -0.3490658503988659F, 0.5235987755982988F);
        this.GlowWingL01b = new ModelRenderer(this, 0, 0);
        this.GlowWingL01b.setRotationPoint(6.5F, -1.5F, -4.0F);
        this.setRotateAngle(GlowWingL01b, -0.08726646259971647F, -0.08726646259971647F, 0.0F);
        this.GlowWingR01a = new ModelRenderer(this, 0, 0);
        this.GlowWingR01a.setRotationPoint(-6.0F, 13.5F, -4.0F);
        this.setRotateAngle(GlowWingR01a, 0.0F, 0.3490658503988659F, -0.5235987755982988F);
        this.GlowWingR01b = new ModelRenderer(this, 0, 0);
        this.GlowWingR01b.setRotationPoint(-6.5F, -1.5F, -4.0F);
        this.setRotateAngle(GlowWingR01b, -0.08726646259971647F, 0.08726646259971647F, 0.0F);
        this.GlowCannonL01 = new ModelRenderer(this, 0, 0);
        this.GlowCannonL01.setRotationPoint(8.0F, -6.0F, 9.0F);
        this.setRotateAngle(GlowCannonL01, -0.5235987755982988F, -0.5235987755982988F, 0.0F);
        this.GlowCannonR01 = new ModelRenderer(this, 0, 0);
        this.GlowCannonR01.setRotationPoint(-8.0F, -6.0F, 9.0F);
        this.setRotateAngle(GlowCannonR01, -0.5235987755982988F, 0.5235987755982988F, 0.0F);
        this.GlowCannonM01 = new ModelRenderer(this, 0, 0);
        this.GlowCannonM01.setRotationPoint(0.0F, -8.5F, 7.0F);
        this.setRotateAngle(GlowCannonM01, -0.8726646259971648F, 0.0F, 0.0F);
        this.GlowCannonM02 = new ModelRenderer(this, 0, 0);
        this.GlowCannonM02.setRotationPoint(1.3F, -1.7F, -3.5F);
        this.GlowCannonM04 = new ModelRenderer(this, 0, 0);
        this.GlowCannonM04.setRotationPoint(-1.3F, -1.7F, -3.5F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowJaw01);
        this.GlowJaw01.addChild(this.JawTooth01);
        this.JawTooth01.addChild(this.JawTooth02);
        this.GlowNeck.addChild(this.GlowHead01);
        this.GlowHead01.addChild(this.HeadTooth01);
        this.HeadTooth01.addChild(this.HeadTooth02);
        
        this.GlowBodyMain.addChild(this.GlowWingL01a);
        this.GlowWingL01a.addChild(this.GlowWingL01b);
        this.GlowWingL01b.addChild(this.WingL01Fire);
        this.GlowBodyMain.addChild(this.GlowWingR01a);
        this.GlowWingR01a.addChild(this.GlowWingR01b);
        this.GlowWingR01b.addChild(this.WingR01Fire);
        
        this.GlowBodyMain.addChild(this.GlowCannonL01);
        this.GlowCannonL01.addChild(this.CannonL02);
        this.GlowBodyMain.addChild(this.GlowCannonR01);
        this.GlowCannonR01.addChild(this.CannonR02);
        this.GlowBodyMain.addChild(this.GlowCannonM01);
        this.GlowCannonM01.addChild(this.GlowCannonM02);
        this.GlowCannonM02.addChild(this.CannonM03);
        this.GlowCannonM01.addChild(this.GlowCannonM04);
        this.GlowCannonM04.addChild(this.CannonM05);
        
        //發光支架2
        this.GlowBodyMain2 = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain2.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.GlowWingL01a2 = new ModelRenderer(this, 0, 0);
        this.GlowWingL01a2.setRotationPoint(6.0F, 13.5F, -4.0F);
        this.setRotateAngle(GlowWingL01a2, 0.0F, -0.3490658503988659F, 0.5235987755982988F);
        this.GlowWingR01a2 = new ModelRenderer(this, 0, 0);
        this.GlowWingR01a2.setRotationPoint(-6.0F, 13.5F, -4.0F);
        this.setRotateAngle(GlowWingR01a2, 0.0F, 0.3490658503988659F, -0.5235987755982988F);
        
        this.GlowBodyMain2.addChild(this.WingL02);
        this.GlowBodyMain2.addChild(this.WingR02);
        this.GlowBodyMain2.addChild(this.WingL03);
        this.GlowBodyMain2.addChild(this.WingR03);
        this.GlowBodyMain2.addChild(this.WingL04);
        this.GlowBodyMain2.addChild(this.WingR04);
        
        this.GlowBodyMain2.addChild(this.GlowWingL01a2);
        this.GlowWingL01a2.addChild(this.WingL01b);
        this.WingL01b.addChild(this.WingL01c);
        this.GlowBodyMain2.addChild(this.GlowWingR01a2);
        this.GlowWingR01a2.addChild(this.WingR01b);
        this.WingR01b.addChild(this.WingR01c);
        
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
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
    	GL11.glScalef(1.1F, 1.1F, 1.1F);
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	GL11.glDisable(GL11.GL_BLEND);
    	
    	//亮度設為240
    	GL11.glDisable(GL11.GL_LIGHTING);
    	
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	
    	float light = 80F + MathHelper.cos(f2 * 0.075F) * 80F;
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, light, light);
    	this.GlowBodyMain2.render(f5);
    	
    	GL11.glEnable(GL11.GL_LIGHTING);
    	
    	GL11.glPopMatrix();
    }
    
  //for idle/run animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		IShipEmotion ent = (IShipEmotion)entity;
		  
		motionHumanPos(f, f1, f2, f3, f4, ent);

    }
    
    //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {   
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		
    	GL11.glTranslatef(0F, -0.25F, 0F);

	    //正常站立動作
	  	//嘴巴
	  	this.Jaw01.rotateAngleX = angleX * 0.025F + 0.32F;
	    //cannon
	  	this.CannonL02.rotateAngleX = angleX * 0.05F - 0.3F;
	  	this.CannonR02.rotateAngleX = -angleX * 0.05F;
	  	this.CannonM03.rotateAngleX = -angleX * 0.05F;
	  	this.CannonM05.rotateAngleX = angleX * 0.05F;
	    
    	//seat2 有載人動作
	    if(ent.getStateEmotion(ID.S.Emotion) > 0) {
	    	this.Jaw01.rotateAngleX = 1.0F;
	    }
	    
	    //移動時顯示推進器火焰
	    if(f1 > 0.2F) {
	    	this.WingL01Fire.isHidden = false;
	    	this.WingR01Fire.isHidden = false;
	    }
	    else {
	    	this.WingL01Fire.isHidden = true;
	    	this.WingR01Fire.isHidden = true;
	    }
	    
	    //發光支架
	    this.GlowJaw01.rotateAngleX = this.Jaw01.rotateAngleX;
  	}

    
}

