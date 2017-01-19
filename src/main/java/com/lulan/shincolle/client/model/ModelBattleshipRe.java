package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.BasicEntityShip;
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
 * EntityBattleshipRe - PinkaLulan 2015/2/28
 * Created using Tabula 4.1.1
 */
public class ModelBattleshipRe extends ModelBase implements IModelEmotion
{
	public ModelRenderer BodyMain;
    public ModelRenderer Cloth;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer BagMain;
    public ModelRenderer TailBase;
    public ModelRenderer Butt;
    public ModelRenderer Cloth2;
    public ModelRenderer Head;
    public ModelRenderer Ear01;
    public ModelRenderer Ear02;
    public ModelRenderer Hair;
    public ModelRenderer Hair01;
    public ModelRenderer HairU01;
    public ModelRenderer Cap;
    public ModelRenderer Cap2;
    public ModelRenderer Face0;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Ahoke;
    public ModelRenderer BoobM;
    public ModelRenderer PalmLeft;
    public ModelRenderer PalmRight;
    public ModelRenderer BagMain2;
    public ModelRenderer BagStrap1;
    public ModelRenderer BagStrap2;
    public ModelRenderer Tail1;
    public ModelRenderer TailBack0;
    public ModelRenderer Tail2;
    public ModelRenderer TailBack1;
    public ModelRenderer Tail3;
    public ModelRenderer TailBack2;
    public ModelRenderer Tail4;
    public ModelRenderer TailBack3;
    public ModelRenderer Tail5;
    public ModelRenderer TailBack4;
    public ModelRenderer Tail6;
    public ModelRenderer TailBack5;
    public ModelRenderer TailHeadBase;
    public ModelRenderer TailBack6;
    public ModelRenderer TailJaw1;
    public ModelRenderer TailHead1;
    public ModelRenderer TailHeadCL1;
    public ModelRenderer TailHeadCR1;
    public ModelRenderer TailJawT01;
    public ModelRenderer TailJaw2;
    public ModelRenderer TailJaw3;
    public ModelRenderer TailHead2;
    public ModelRenderer TailHeadT01;
    public ModelRenderer TailHeadC1;
    public ModelRenderer TailHead3;
    public ModelRenderer TailHeadC2;
    public ModelRenderer TailHeadC3;
    public ModelRenderer TailHeadC4;
    public ModelRenderer TailHeadCL2;
    public ModelRenderer TailHeadCL3;
    public ModelRenderer TailHeadCR2;
    public ModelRenderer TailHeadCR3;
    public ModelRenderer LegRight;
    public ModelRenderer LegLeft;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowTailBase;
    public ModelRenderer GlowTail1;
    public ModelRenderer GlowTail2;
    public ModelRenderer GlowTail3;
    public ModelRenderer GlowTail4;
    public ModelRenderer GlowTail5;
    public ModelRenderer GlowTail6;
    public ModelRenderer GlowTailHeadBase;
    public ModelRenderer GlowTailHead1;
    public ModelRenderer GlowTailJaw1;

    
    public ModelBattleshipRe()
    {
        this.textureWidth = 256;
        this.textureHeight = 128;
        
        this.Face2 = new ModelRenderer(this, 80, 66);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 0, 57);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(4.5F, -8.5F, -0.5F);
        this.ArmLeft01.addBox(0.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.2617993877991494F, 0.0F, -0.4363323129985824F);
        this.ArmLeft02 = new ModelRenderer(this, 0, 57);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(6F, 10F, 3F);
        this.ArmLeft02.addBox(-6F, 0.0F, -6F, 6, 7, 6, 0.0F);
        this.TailHeadC1 = new ModelRenderer(this, 201, 78);
        this.TailHeadC1.setRotationPoint(0.0F, -3.5F, 0.0F);
        this.TailHeadC1.addBox(-4.5F, 0.0F, 0.0F, 9, 5, 9, 0.0F);
        this.setRotateAngle(TailHeadC1, 0.3490658503988659F, 0.0F, 0.0F);
        this.Cloth2 = new ModelRenderer(this, 50, 0);
        this.Cloth2.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.Cloth2.addBox(-8.5F, 0.0F, -5.0F, 17, 12, 10, 0.0F);
        this.setRotateAngle(Cloth2, -0.05235987755982988F, 0.0F, 0.0F);
        this.TailHead1 = new ModelRenderer(this, 191, 70);
        this.TailHead1.setRotationPoint(0.0F, -8.5F, 4.0F);
        this.TailHead1.addBox(-5.5F, 0.0F, -0.5F, 11, 8, 17, 0.0F);
        this.setRotateAngle(TailHead1, 0.13962634015954636F, 0.0F, 0.0F);
        this.Tail5 = new ModelRenderer(this, 208, 103);
        this.Tail5.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.Tail5.addBox(-6.0F, -6.0F, 0.0F, 12, 12, 12, 0.0F);
        this.setRotateAngle(Tail5, -0.5235987755982988F, 0.0F, 0.0F);
        this.BoobL = new ModelRenderer(this, 0, 80);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.5F, -9.5F, -3.0F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobL, -0.7853981633974483F, -0.12217304763960307F, -0.08726646259971647F);
        this.PalmLeft = new ModelRenderer(this, 0, 89);
        this.PalmLeft.mirror = true;
        this.PalmLeft.setRotationPoint(-3F, 7F, -3F);
        this.PalmLeft.addBox(-2.5F, 0.0F, -2.5F, 5, 4, 5, 0.0F);
        this.TailHeadCR2 = new ModelRenderer(this, 207, 77);
        this.TailHeadCR2.setRotationPoint(-2.0F, 0.5F, 7.0F);
        this.TailHeadCR2.addBox(-2.0F, 0.0F, 0.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(TailHeadCR2, 0.08726646259971647F, -0.17453292519943295F, 0.0F);
        this.TailHeadC2 = new ModelRenderer(this, 207, 77);
        this.TailHeadC2.setRotationPoint(0.0F, 1.0F, 8.5F);
        this.TailHeadC2.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(TailHeadC2, 0.13962634015954636F, 0.0F, 0.0F);
        this.TailHeadCR3 = new ModelRenderer(this, 207, 77);
        this.TailHeadCR3.setRotationPoint(-2.0F, 3.5F, 7.0F);
        this.TailHeadCR3.addBox(-2.0F, 0.0F, 0.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(TailHeadCR3, -0.05235987755982988F, -0.17453292519943295F, 0.0F);
        this.Tail6 = new ModelRenderer(this, 208, 103);
        this.Tail6.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.Tail6.addBox(-5.5F, -6.5F, 0.0F, 11, 13, 12, 0.0F);
        this.setRotateAngle(Tail6, -0.5235987755982988F, 0.0F, 0.0F);
        this.Face1 = new ModelRenderer(this, 80, 51);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.TailHeadCL1 = new ModelRenderer(this, 207, 80);
        this.TailHeadCL1.setRotationPoint(6.0F, -6.0F, 5.0F);
        this.TailHeadCL1.addBox(0.0F, 0.0F, 0.0F, 5, 6, 7, 0.0F);
        this.setRotateAngle(TailHeadCL1, 0.08726646259971647F, 0.17453292519943295F, 0.0F);
        this.BagMain = new ModelRenderer(this, 37, 23);
        this.BagMain.setRotationPoint(3.0F, -13.0F, 6.5F);
        this.BagMain.addBox(-8.0F, 0.0F, 0.0F, 14, 12, 7, 0.0F);
        this.setRotateAngle(BagMain, -0.2617993877991494F, 0.0F, 0.08726646259971647F);
        this.BagStrap1 = new ModelRenderer(this, 103, 16);
        this.BagStrap1.setRotationPoint(3.5F, 1.0F, 0.5F);
        this.BagStrap1.addBox(0.0F, 0.0F, -11.0F, 3, 10, 11, 0.0F);
        this.setRotateAngle(BagStrap1, 0.2617993877991494F, -0.13962634015954636F, -0.17453292519943295F);
        this.BagStrap2 = new ModelRenderer(this, 82, 24);
        this.BagStrap2.setRotationPoint(-5.0F, 1.0F, 2.0F);
        this.BagStrap2.addBox(-3.0F, 0.0F, -15.0F, 3, 10, 15, 0.0F);
        this.setRotateAngle(BagStrap2, 0.3490658503988659F, 0.3490658503988659F, 0.13962634015954636F);
        this.Ahoke = new ModelRenderer(this, 28, 90);
        this.Ahoke.setRotationPoint(0F, -7F, -4F);
        this.Ahoke.addBox(0F, -6F, -11F, 0, 11, 11, 0F);
        this.setRotateAngle(Ahoke, -0.1742F, 0.5235987755982988F, 0.0F);
        this.Cap = new ModelRenderer(this, 104, 101);
        this.Cap.setRotationPoint(0.0F, 0.6F, 2.0F);
        this.Cap.addBox(-8.0F, -17.0F, -2.0F, 16, 17, 10, 0.0F);
        this.setRotateAngle(Cap, 0.2F, 0.0F, 0.0F);
        this.Cap2 = new ModelRenderer(this, 106, 103);
        this.Cap2.setRotationPoint(0F, -2F, -3F);
        this.Cap2.addBox(-8F, -15F, 0F, 16, 15, 8, 0.0F);
        this.setRotateAngle(Cap2, -1.4F, 0.0F, 0.0F);
        this.TailHead3 = new ModelRenderer(this, 200, 80);
        this.TailHead3.setRotationPoint(0.0F, 4.0F, 14.5F);
        this.TailHead3.addBox(-6.0F, 0.0F, 0.0F, 12, 4, 7, 0.0F);
        this.setRotateAngle(TailHead3, 0.5235987755982988F, 0.0F, 0.0F);
        this.TailHead2 = new ModelRenderer(this, 182, 68);
        this.TailHead2.setRotationPoint(0.0F, -1.5F, 4.5F);
        this.TailHead2.addBox(-9.0F, 0.0F, 0.0F, 18, 8, 19, 0.0F);
        this.setRotateAngle(TailHead2, 0.13962634015954636F, 0.0F, 0.0F);
        this.TailHeadCL2 = new ModelRenderer(this, 207, 77);
        this.TailHeadCL2.setRotationPoint(2.0F, 0.5F, 7.0F);
        this.TailHeadCL2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(TailHeadCL2, 0.08726646259971647F, 0.17453292519943295F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 0, 57);
        this.ArmRight01.setRotationPoint(-4.5F, -8.5F, -0.5F);
        this.ArmRight01.addBox(-6.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.setRotateAngle(ArmRight01, 0.2617993877991494F, 0.0F, 0.4363323129985824F);
        this.ArmRight02 = new ModelRenderer(this, 0, 57);
        this.ArmRight02.setRotationPoint(-6F, 10F, 3F);
        this.ArmRight02.addBox(0F, 0F, -6F, 6, 7, 6, 0.0F);
        this.TailBack4 = new ModelRenderer(this, 163, 70);
        this.TailBack4.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.TailBack4.addBox(-3.5F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.setRotateAngle(TailBack4, 0.17453292519943295F, 0.0F, 0.0F);
        this.TailJaw2 = new ModelRenderer(this, 197, 77);
        this.TailJaw2.setRotationPoint(0.0F, 2.0F, 8.0F);
        this.TailJaw2.addBox(-5.0F, 0.0F, 0.0F, 10, 5, 10, 0.0F);
        this.setRotateAngle(TailJaw2, -0.17453292519943295F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 0, 80);
        this.BoobR.setRotationPoint(-3.5F, -9.5F, -3.0F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobR, -0.7853981633974483F, 0.12217304763960307F, 0.08726646259971647F);
        this.TailHeadT01 = new ModelRenderer(this, 115, 60);
        this.TailHeadT01.setRotationPoint(0.0F, 4.5F, 4.5F);
        this.TailHeadT01.addBox(-6.0F, 0.0F, 0.0F, 12, 5, 12, 0.0F);
        this.setRotateAngle(TailHeadT01, -0.17453292519943295F, 0.0F, 0.0F);
        this.Tail3 = new ModelRenderer(this, 208, 103);
        this.Tail3.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.Tail3.addBox(-6.0F, -6.0F, 0.0F, 12, 12, 12, 0.0F);
        this.setRotateAngle(Tail3, 0.5235987755982988F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 21, 85);
        this.Neck.setRotationPoint(0.0F, -11.5F, 0.5F);
        this.Neck.addBox(-7.5F, -1.5F, -7.0F, 15, 5, 11, 0.0F);
        this.setRotateAngle(Neck, 0.2617993877991494F, 0.0F, 0.0F);
        this.TailBack2 = new ModelRenderer(this, 163, 70);
        this.TailBack2.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.TailBack2.addBox(-3.5F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.setRotateAngle(TailBack2, 0.17453292519943295F, 0.0F, 0.0F);
        this.LegLeft = new ModelRenderer(this, 0, 98);
        this.LegLeft.mirror = true;
        this.LegLeft.setRotationPoint(4.5F, 11.0F, -2.0F);
        this.LegLeft.addBox(-3.5F, 0.0F, -3.5F, 7, 22, 7, 0.0F);
        this.setRotateAngle(LegLeft, -0.22689280275926282F, 0.0F, 0.05235987755982988F);
        this.BodyMain = new ModelRenderer(this, 0, 34);
        this.BodyMain.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.BodyMain.addBox(-7.0F, -9.0F, -4.0F, 14, 15, 8, 0.0F);
        this.setRotateAngle(BodyMain, 0.05235987755982988F, 0.0F, 0.0F);
        this.TailJaw3 = new ModelRenderer(this, 207, 80);
        this.TailJaw3.setRotationPoint(0.0F, 4.0F, 15.5F);
        this.TailJaw3.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 7, 0.0F);
        this.setRotateAngle(TailJaw3, -0.10035643198967394F, 0.0F, 0.0F);
        this.TailBase = new ModelRenderer(this, 208, 103);
        this.TailBase.setRotationPoint(0.0F, 7.5F, 0.0F);
        this.TailBase.addBox(-6.0F, -6.0F, 0.0F, 12, 12, 12, 0.0F);
        this.setRotateAngle(TailBase, -0.2617993877991494F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 106, 0);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-8.0F, 4.0F, -5.0F, 16, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.17453292519943295F, 0.0F, 0.0F);
        this.TailBack5 = new ModelRenderer(this, 163, 70);
        this.TailBack5.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.TailBack5.addBox(-3.5F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.setRotateAngle(TailBack5, 0.17453292519943295F, 0.0F, 0.0F);
        this.LegRight = new ModelRenderer(this, 0, 98);
        this.LegRight.setRotationPoint(-4.5F, 11.0F, -2.0F);
        this.LegRight.addBox(-3.5F, 0.0F, -3.5F, 7, 22, 7, 0.0F);
        this.setRotateAngle(LegRight, -0.22689280275926282F, 0.0F, -0.05235987755982988F);
        this.TailBack0 = new ModelRenderer(this, 163, 70);
        this.TailBack0.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.TailBack0.addBox(-3.5F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.setRotateAngle(TailBack0, 0.17453292519943295F, 0.0F, 0.0F);
        this.Cloth = new ModelRenderer(this, 0, 0);
        this.Cloth.setRotationPoint(0.0F, -8.5F, 0.0F);
        this.Cloth.addBox(-8.0F, 0.0F, -4.5F, 16, 14, 9, 0.0F);
        this.TailHeadC3 = new ModelRenderer(this, 207, 77);
        this.TailHeadC3.setRotationPoint(-2.8F, 1.0F, 8.5F);
        this.TailHeadC3.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(TailHeadC3, 0.13962634015954636F, -0.05235987755982988F, 0.0F);
        this.Tail1 = new ModelRenderer(this, 208, 103);
        this.Tail1.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.Tail1.addBox(-6.0F, -6.0F, 0.0F, 12, 12, 12, 0.0F);
        this.setRotateAngle(Tail1, 0.2617993877991494F, 0.0F, 0.0F);
        this.TailHeadBase = new ModelRenderer(this, 157, 96);
        this.TailHeadBase.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.Hair = new ModelRenderer(this, 24, 61);
        this.Hair.setRotationPoint(0.0F, -7.3F, 0.0F);
        this.Hair.addBox(-7.5F, -8.0F, -8.0F, 15, 16, 8, 0.0F);
        this.Hair01 = new ModelRenderer(this, 186, 0);
        this.Hair01.setRotationPoint(0.0F, -9.5F, 9.5F);
        this.Hair01.addBox(-7F, 0F, -12F, 14, 9, 9, 0F);
        this.setRotateAngle(Hair01, 0.1257F, 0.0F, 0.0F);
        this.HairU01 = new ModelRenderer(this, 189, 19);
        this.HairU01.setRotationPoint(0F, -0.2F, -7.2F);
        this.HairU01.addBox(-8F, -14.7F, 0F, 16, 15, 6, 0F);
        this.TailJaw1 = new ModelRenderer(this, 194, 106);
        this.TailJaw1.setRotationPoint(0.0F, 3.0F, 5.0F);
        this.TailJaw1.addBox(-6.5F, 0.0F, 0.0F, 13, 5, 16, 0.0F);
        this.setRotateAngle(TailJaw1, -0.17453292519943295F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 39, 101);
        this.Head.setRotationPoint(0.0F, -0.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.setRotateAngle(Head, -0.17453292519943295F, 0.0F, 0.0F);
        this.TailHeadCR1 = new ModelRenderer(this, 207, 80);
        this.TailHeadCR1.setRotationPoint(-6.0F, -6.0F, 5.0F);
        this.TailHeadCR1.addBox(-5.0F, 0.0F, 0.0F, 5, 6, 7, 0.0F);
        this.setRotateAngle(TailHeadCR1, 0.08726646259971647F, -0.17453292519943295F, 0.0F);
        this.TailBack1 = new ModelRenderer(this, 163, 70);
        this.TailBack1.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.TailBack1.addBox(-3.5F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.setRotateAngle(TailBack1, 0.17453292519943295F, 0.0F, 0.0F);
        this.TailHeadC4 = new ModelRenderer(this, 207, 77);
        this.TailHeadC4.setRotationPoint(2.8F, 1.0F, 8.5F);
        this.TailHeadC4.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(TailHeadC4, 0.13962634015954636F, 0.05235987755982988F, 0.0F);
        this.PalmRight = new ModelRenderer(this, 0, 89);
        this.PalmRight.setRotationPoint(3F, 7F, -3F);
        this.PalmRight.addBox(-2.5F, 0.0F, -2.5F, 5, 4, 5, 0.0F);
        this.setRotateAngle(PalmRight, 0.0F, 0.02530727415391778F, 0.0F);
        this.Tail2 = new ModelRenderer(this, 208, 103);
        this.Tail2.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.Tail2.addBox(-6.0F, -6.0F, 0.0F, 12, 12, 12, 0.0F);
        this.setRotateAngle(Tail2, 0.5235987755982988F, 0.0F, 0.0F);
        this.TailJawT01 = new ModelRenderer(this, 117, 78);
        this.TailJawT01.setRotationPoint(0.0F, -3.0F, 4.0F);
        this.TailJawT01.addBox(-5.5F, 0.0F, 0.0F, 11, 5, 11, 0.0F);
        this.setRotateAngle(TailJawT01, 0.17453292519943295F, 0.0F, 0.0F);
        this.Tail4 = new ModelRenderer(this, 208, 103);
        this.Tail4.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.Tail4.addBox(-6.0F, -6.0F, 0.0F, 12, 12, 12, 0.0F);
        this.setRotateAngle(Tail4, 0.2617993877991494F, 0.0F, 0.0F);
        this.Face0 = new ModelRenderer(this, 44, 42);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.TailHeadCL3 = new ModelRenderer(this, 207, 77);
        this.TailHeadCL3.setRotationPoint(2.0F, 3.5F, 7.0F);
        this.TailHeadCL3.addBox(0.0F, 0.0F, 0.0F, 2, 2, 10, 0.0F);
        this.setRotateAngle(TailHeadCL3, -0.05235987755982988F, 0.17453292519943295F, 0.0F);
        this.BoobM = new ModelRenderer(this, 0, 0);
        this.BoobM.setRotationPoint(4.2F, 4.5F, 0.3F);
        this.BoobM.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 0, 0.0F);
        this.setRotateAngle(BoobM, 0.7853981633974483F, 0.0F, -0.08726646259971647F);
        this.TailBack6 = new ModelRenderer(this, 163, 70);
        this.TailBack6.setRotationPoint(0.0F, -7.5F, 0.0F);
        this.TailBack6.addBox(-3.5F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.setRotateAngle(TailBack6, 0.17453292519943295F, 0.0F, 0.0F);
        this.TailBack3 = new ModelRenderer(this, 163, 70);
        this.TailBack3.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.TailBack3.addBox(-3.5F, 0.0F, 0.0F, 7, 2, 12, 0.0F);
        this.setRotateAngle(TailBack3, 0.17453292519943295F, 0.0F, 0.0F);
        this.Face3 = new ModelRenderer(this, 80, 81);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.Face4 = new ModelRenderer(this, 80, 96);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.BagMain2 = new ModelRenderer(this, 36, 23);
        this.BagMain2.setRotationPoint(-0.5F, 11.0F, -0.5F);
        this.BagMain2.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 8, 0.0F);
        this.setRotateAngle(BagMain2, 0.6981317007977318F, 0.0F, -0.2617993877991494F);
        this.Ear01 = new ModelRenderer(this, 136, 17);
        this.Ear01.mirror = true;
        this.Ear01.setRotationPoint(-3.5F, -14.5F, 5.7F);
        this.Ear01.addBox(-1.5F, 0F, -6F, 3, 6, 6, 0.0F);
        this.setRotateAngle(Ear01, -0.6981F, 0.2618F, -0.1396F);
        this.Ear02 = new ModelRenderer(this, 136, 17);
        this.Ear02.setRotationPoint(3.5F, -14.5F, 5.7F);
        this.Ear02.addBox(-1.5F, 0F, -6F, 3, 6, 6, 0.0F);
        this.setRotateAngle(Ear02, -0.6981F, -0.2618F, 0.1396F);
        this.BodyMain.addChild(this.ArmLeft01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.TailHead1.addChild(this.TailHeadC1);
        this.Cloth.addChild(this.Cloth2);
        this.BodyMain.addChild(this.BoobL);
        this.ArmLeft02.addChild(this.PalmLeft);
        this.TailHeadCR1.addChild(this.TailHeadCR2);
        this.TailHeadC1.addChild(this.TailHeadC2);
        this.TailHeadCR1.addChild(this.TailHeadCR3);
        this.TailHeadBase.addChild(this.TailHeadCL1);
        this.BodyMain.addChild(this.BagMain);
        this.BagMain.addChild(this.BagStrap2);
        this.Hair.addChild(this.Ahoke);
        this.Head.addChild(this.Cap);
        this.Head.addChild(this.Ear01);
        this.Head.addChild(this.Ear02);
        this.TailHead1.addChild(this.TailHead3);
        this.TailHead1.addChild(this.TailHead2);
        this.TailHeadCL1.addChild(this.TailHeadCL2);
        this.BodyMain.addChild(this.ArmRight01);
        this.ArmRight01.addChild(this.ArmRight02);
        this.TailJaw1.addChild(this.TailJaw2);
        this.BodyMain.addChild(this.BoobR);
        this.Butt.addChild(this.LegLeft);
        this.TailJaw1.addChild(this.TailJaw3);
        this.BodyMain.addChild(this.Butt);
        this.Butt.addChild(this.LegRight);
        this.BodyMain.addChild(this.Cloth);
        this.TailHeadC1.addChild(this.TailHeadC3);
        this.BagMain.addChild(this.BagStrap1);  
        this.Head.addChild(this.Hair);
        this.Head.addChild(this.Hair01);
        this.Head.addChild(this.HairU01);
        this.TailHeadBase.addChild(this.TailHeadCR1);
        this.TailHeadC1.addChild(this.TailHeadC4);
        this.ArmRight02.addChild(this.PalmRight);
        this.TailHeadCL1.addChild(this.TailHeadCL3);
        this.BoobR.addChild(this.BoobM);
        this.BagMain.addChild(this.BagMain2);
        this.BodyMain.addChild(this.Neck);
        this.Neck.addChild(this.Head);
        this.Neck.addChild(this.Cap2);
        this.BodyMain.addChild(this.TailBase);
        this.TailBase.addChild(this.Tail1);
        this.Tail1.addChild(this.Tail2);
        this.Tail2.addChild(this.Tail3);
        this.Tail3.addChild(this.Tail4);
        this.Tail4.addChild(this.Tail5);
        this.Tail5.addChild(this.Tail6);
        this.Tail6.addChild(this.TailHeadBase);
        this.TailHeadBase.addChild(this.TailHead1);
        this.TailHeadBase.addChild(this.TailJaw1);
        
        //以下為發光模型支架, 此部份模型整個亮度設為240
        //臉部支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -11.5F, 0.5F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -0.5F, 0.0F);
        //尾巴支架
        this.GlowTailBase = new ModelRenderer(this, 0, 0);
        this.GlowTailBase.setRotationPoint(0.0F, 7.5F, 0.0F);
        this.GlowTail1 = new ModelRenderer(this, 0, 0);
        this.GlowTail1.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.GlowTail2 = new ModelRenderer(this, 0, 0);
        this.GlowTail2.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.GlowTail3 = new ModelRenderer(this, 0, 0);
        this.GlowTail3.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.GlowTail4 = new ModelRenderer(this, 0, 0);
        this.GlowTail4.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.GlowTail5 = new ModelRenderer(this, 0, 0);
        this.GlowTail5.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.GlowTail6 = new ModelRenderer(this, 0, 0);
        this.GlowTail6.setRotationPoint(0.0F, 0.0F, 9.0F);
        //尾巴牙齒支架
        this.GlowTailHeadBase = new ModelRenderer(this, 157, 96);
        this.GlowTailHeadBase.setRotationPoint(0.0F, 0.0F, 9.0F);
        this.GlowTailHeadBase.addBox(-5.0F, -7.0F, 0.0F, 10, 14, 12, 0.0F);
        this.GlowTailHead1 = new ModelRenderer(this, 0, 0);
        this.GlowTailHead1.setRotationPoint(0.0F, -8.5F, 4.0F);
        this.GlowTailJaw1 = new ModelRenderer(this, 0, 0);
        this.GlowTailJaw1.setRotationPoint(0.0F, 3.0F, 5.0F);
        //臉部
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        //尾巴
        this.GlowBodyMain.addChild(this.GlowTailBase);
        this.GlowTailBase.addChild(this.GlowTail1);
        this.GlowTail1.addChild(this.GlowTail2);
        this.GlowTail2.addChild(this.GlowTail3);
        this.GlowTail3.addChild(this.GlowTail4);
        this.GlowTail4.addChild(this.GlowTail5);
        this.GlowTail5.addChild(this.GlowTail6);
        //尾巴鱗片
        this.GlowTailBase.addChild(this.TailBack0);
        this.GlowTail1.addChild(this.TailBack1);
        this.GlowTail2.addChild(this.TailBack2);
        this.GlowTail3.addChild(this.TailBack3);
        this.GlowTail4.addChild(this.TailBack4);
        this.GlowTail5.addChild(this.TailBack5);
        this.GlowTail6.addChild(this.TailBack6);
        //尾巴牙齒
        this.GlowTail6.addChild(this.GlowTailHeadBase);
        this.GlowTailHeadBase.addChild(this.GlowTailHead1);
        this.GlowTailHead1.addChild(this.TailHeadT01);
        this.GlowTailHeadBase.addChild(this.GlowTailJaw1);
        this.GlowTailJaw1.addChild(this.TailJawT01);
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
    	GlStateManager.scale(0.4F, 0.4F, 0.4F);
    	
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
      
    	BasicEntityShip ent = (BasicEntityShip)entity;

    	EmotionHelper.rollEmotion(this, ent);

    	showEquip(ent);
    	
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
		//尾巴
		this.GlowTailBase.rotateAngleX = this.TailBase.rotateAngleX;
		this.GlowTailBase.rotateAngleY = this.TailBase.rotateAngleY;
		this.GlowTailBase.rotateAngleZ = this.TailBase.rotateAngleZ;
		this.GlowTail1.rotateAngleX = this.Tail1.rotateAngleX;
		this.GlowTail1.rotateAngleY = this.Tail1.rotateAngleY;
		this.GlowTail1.rotateAngleZ = this.Tail1.rotateAngleZ;
		this.GlowTail2.rotateAngleX = this.Tail2.rotateAngleX;
		this.GlowTail2.rotateAngleY = this.Tail2.rotateAngleY;
		this.GlowTail2.rotateAngleZ = this.Tail2.rotateAngleZ;
		this.GlowTail3.rotateAngleX = this.Tail3.rotateAngleX;
		this.GlowTail3.rotateAngleY = this.Tail3.rotateAngleY;
		this.GlowTail3.rotateAngleZ = this.Tail3.rotateAngleZ;
		this.GlowTail4.rotateAngleX = this.Tail4.rotateAngleX;
		this.GlowTail4.rotateAngleY = this.Tail4.rotateAngleY;
		this.GlowTail4.rotateAngleZ = this.Tail4.rotateAngleZ;
		this.GlowTail5.rotateAngleX = this.Tail5.rotateAngleX;
		this.GlowTail5.rotateAngleY = this.Tail5.rotateAngleY;
		this.GlowTail5.rotateAngleZ = this.Tail5.rotateAngleZ;
		this.GlowTail6.rotateAngleX = this.Tail6.rotateAngleX;
		this.GlowTail6.rotateAngleY = this.Tail6.rotateAngleY;
		this.GlowTail6.rotateAngleZ = this.Tail6.rotateAngleZ;
		//尾巴牙齒
		this.GlowTailHeadBase.rotateAngleX = this.TailHeadBase.rotateAngleX;
		this.GlowTailHeadBase.rotateAngleY = this.TailHeadBase.rotateAngleY;
		this.GlowTailHeadBase.rotateAngleZ = this.TailHeadBase.rotateAngleZ;
		this.GlowTailHead1.rotateAngleX = this.TailHead1.rotateAngleX;
		this.GlowTailHead1.rotateAngleY = this.TailHead1.rotateAngleY;
		this.GlowTailHead1.rotateAngleZ = this.TailHead1.rotateAngleZ;
		this.GlowTailJaw1.rotateAngleX = this.TailJaw1.rotateAngleX;
		this.GlowTailJaw1.rotateAngleY = this.TailJaw1.rotateAngleY;
		this.GlowTailJaw1.rotateAngleZ = this.TailJaw1.rotateAngleZ;
	}
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
    {
    	GlStateManager.translate(0F, 1.13F, 0F);
    	setFace(4);
    
    	//頭部
	  	this.Head.rotateAngleX = 0F;
	  	this.Head.rotateAngleY = 0F;
	    //胸部
  	    this.BoobL.rotateAngleX = -0.73F;
  	    this.BoobR.rotateAngleX = -0.73F;
	  	//Body
  	    this.Ahoke.rotateAngleY = 0.5236F;
	  	this.Head.rotateAngleX -= 0.5236F;
	  	this.BodyMain.rotateAngleY = 0F;
    	this.BodyMain.rotateAngleX = 1.5708F;
    	this.Cloth2.rotateAngleX = -0.0524F;
  	    //arm 
	  	this.ArmLeft01.rotateAngleX = -2.9671F;
	    this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = 0.0349F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmRight01.rotateAngleX = -2.9671F;
		this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -0.0349F;
		this.ArmRight02.rotateAngleZ = 0F;
		//bag
		this.BagStrap1.rotateAngleX = 0.2618F;
		this.BagStrap1.rotateAngleY = -0.1396F;
		this.BagStrap1.rotateAngleZ = -0.1745F;
		this.BagStrap2.rotateAngleX = 0.3491F;
		this.BagStrap2.rotateAngleY = 0.3491F;
		//leg
		this.LegLeft.rotateAngleX = -0.3491F;
		this.LegRight.rotateAngleX = -0.3491F;
		this.LegLeft.rotateAngleY = 0F;
		this.LegRight.rotateAngleY = 0F;
		//tail
		this.TailBase.rotateAngleX = -0.4F;
		this.TailBase.rotateAngleY = -0.8F;//MathHelper.cos(-f2 * 0.1F) * 0.1F;
		this.TailBase.rotateAngleZ = 0F;//MathHelper.cos(-f2 * 0.1F) * 0.05F;
		this.Tail1.rotateAngleX = -0.3F;
		this.Tail1.rotateAngleY = -0.35F;//MathHelper.cos(-f2 * 0.1F + 0.7F) * 0.2F;
		this.Tail1.rotateAngleZ = 0F;//-MathHelper.cos(-f2 * 0.1F + 0.7F) * 0.05F;
		this.Tail2.rotateAngleX = -0.35F;
		this.Tail2.rotateAngleY = -0.3F;//MathHelper.cos(-f2 * 0.1F + 1.4F) * 0.3F;
		this.Tail2.rotateAngleZ = 0F;//-MathHelper.cos(-f2 * 0.1F + 1.4F) * 0.05F;
		this.Tail3.rotateAngleX = -0.4F;
		this.Tail3.rotateAngleY = -0.2F;//MathHelper.cos(-f2 * 0.1F + 2.1F) * 0.4F;
		this.Tail3.rotateAngleZ = 0F;//-MathHelper.cos(-f2 * 0.1F + 2.1F) * 0.05F;
		this.Tail4.rotateAngleX = -0.25F;
		this.Tail4.rotateAngleY = 0.2F;//MathHelper.cos(-f2 * 0.1F + 2.8F) * 0.5F;
		this.Tail4.rotateAngleZ = 0F;//MathHelper.cos(-f2 * 0.1F + 2.8F) * 0.025F;
		this.Tail5.rotateAngleX = 0.25F;
		this.Tail5.rotateAngleY = 0.2F;//MathHelper.cos(-f2 * 0.1F + 3.5F) * 0.55F;
		this.Tail5.rotateAngleZ = 0F;//MathHelper.cos(-f2 * 0.1F + 3.5F) * 0.05F;
		this.Tail6.rotateAngleX = 0.35F;
		this.Tail6.rotateAngleY = 0.2F;//MathHelper.cos(-f2 * 0.1F + 4.2F) * 0.6F;
		this.Tail6.rotateAngleZ = 0F;//MathHelper.cos(-f2 * 0.1F + 4.2F) * 0.05F;
		this.TailHeadBase.rotateAngleX = 0.4F;
		this.TailHeadBase.rotateAngleY = 0F;//MathHelper.cos(-f2 * 0.1F + 4.9F) * 0.65F;
		this.TailHeadBase.rotateAngleZ = 0F;//MathHelper.cos(-f2 * 0.1F + 4.9F) * 0.025F;
		this.TailHead1.rotateAngleX = 0.2618F;
		this.TailJaw1.rotateAngleX = -0.7F;
		this.Hair01.isHidden = true;
		this.Ear01.isHidden = true;
		this.Ear02.isHidden = true;
    }
    
	//雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, BasicEntityShip ent)
  	{
  		float angleX = MathHelper.cos(f2*0.08F);
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		GlStateManager.translate(0F, 0.63F, 0F);
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = MathHelper.cos(f * 0.7F) * f1;
	  	addk2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;

  	    //頭部
	  	this.Head.rotateAngleX = f4 * 0.014F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	    //胸部
  	    this.BoobL.rotateAngleX = -angleX * 0.06F - 0.73F;
  	    this.BoobR.rotateAngleX = -angleX * 0.06F - 0.73F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
	  	this.Head.rotateAngleX -= 0.5236F;
	  	this.Cap2.rotateAngleX = -1.4F;
	  	this.BodyMain.rotateAngleX = 0.0873F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.Cloth2.rotateAngleX = -0.0524F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = 0.2618F;
	    this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.1F - 0.5236F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmRight01.rotateAngleX = 0.2618F;
		this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.1F + 0.5236F;
		this.ArmRight02.rotateAngleZ = 0F;
		//bag
		this.BagStrap1.rotateAngleX = 0.2618F;
		this.BagStrap1.rotateAngleY = -0.1396F;
		this.BagStrap1.rotateAngleZ = -0.1745F;
		this.BagStrap2.rotateAngleX = 0.3491F;
		this.BagStrap2.rotateAngleY = 0.3491F;
		//leg
		addk1 -= 0.2618F;
		addk2 -= 0.2618F;
		this.LegLeft.rotateAngleY = 0F;
		this.LegRight.rotateAngleY = 0F;
		//tail
		this.TailBase.rotateAngleX = -0.5236F;
		this.TailBase.rotateAngleY = MathHelper.cos(-f2 * 0.1F) * 0.1F;
		this.TailBase.rotateAngleZ = 0F;//MathHelper.cos(-f2 * 0.1F) * 0.1F;
		this.Tail1.rotateAngleX = 0.5236F;
		this.Tail1.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 0.7F) * 0.1F;
		this.Tail1.rotateAngleZ = 0F;//-MathHelper.cos(-f2 * 0.1F + 0.7F) * 0.1F;
		this.Tail2.rotateAngleX = 0.5236F;
		this.Tail2.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 1.4F) * 0.15F;
		this.Tail2.rotateAngleZ = 0F;//-MathHelper.cos(-f2 * 0.1F + 1.4F) * 0.1F;
		this.Tail3.rotateAngleX = 0.5236F;
		this.Tail3.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.1F) * 0.2F;
		this.Tail3.rotateAngleZ = 0F;//-MathHelper.cos(-f2 * 0.1F + 2.1F) * 0.1F;
		this.Tail4.rotateAngleX = 0.5236F;
		this.Tail4.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.8F) * 0.25F;
		this.Tail4.rotateAngleZ = 0F;//-MathHelper.cos(-f2 * 0.1F + 2.8F) * 0.1F;
		this.Tail5.rotateAngleX = -0.5236F;
		this.Tail5.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 3.5F) * 0.3F;
		this.Tail5.rotateAngleZ = 0F;//MathHelper.cos(-f2 * 0.1F + 3.5F) * 0.1F;
		this.Tail6.rotateAngleX = -0.5236F;
		this.Tail6.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 4.2F) * 0.35F;
		this.Tail6.rotateAngleZ = 0F;//MathHelper.cos(-f2 * 0.1F + 4.2F) * 0.1F;
		this.TailHeadBase.rotateAngleX = -0.5236F;
		this.TailHeadBase.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 4.9F) * 0.4F;
		this.TailHeadBase.rotateAngleZ = 0F;//MathHelper.cos(-f2 * 0.1F + 4.9F) * 0.1F;
		this.TailHead1.rotateAngleX = 0.1745F;
		this.TailJaw1.rotateAngleX = angleX * 0.1F - 0.15F;
		
		//ear
		float modf2 = f2 % 128F;
		if (modf2 < 6F)
		{
			//total 3 ticks, loop twice in 6 ticks
			if(modf2 >= 3F) modf2 -= 3F;
			float anglef2 = MathHelper.sin(modf2 * 1.0472F) * 0.25F;
			this.Ear01.rotateAngleZ = -anglef2 - 0.14F;
			this.Ear02.rotateAngleZ = anglef2 + 0.14F;
		}
		else
		{
			this.Ear01.rotateAngleZ = -0.14F;
			this.Ear02.rotateAngleZ = 0.14F;
		}

	    if (ent.isSprinting() || f1 > 0.9F)
	    {
	    	//奔跑動作
	    	setFace(3);
	    	float t2 = ent.ticksExisted & 1023;
			//change run type base on tickExisted
			if (t2 > 700)
			{	//run type 1
				//高度
				GlStateManager.translate(0F, 0.05F, 0F);
		  	    //手臂晃動
			  	this.ArmLeft01.rotateAngleX = MathHelper.cos(f * 0.8F) * 0.1F -2.0944F;
			    this.ArmLeft01.rotateAngleY = -0.5236F;
			    this.ArmLeft01.rotateAngleZ = 0F;
			    this.ArmRight01.rotateAngleX = -MathHelper.cos(f * 0.8F) * 0.1F -2.0944F;
				this.ArmRight01.rotateAngleY = 0.5236F;
				this.ArmRight01.rotateAngleZ = 0F;
				//頭部角度
				this.Head.rotateAngleX *= 0.75F;
				this.Head.rotateAngleX -= 0.5236F;
				this.Cap2.rotateAngleX = -1.74F;
				//身體角度
				this.BodyMain.rotateAngleX = 0.5236F;
				this.BodyMain.rotateAngleY = 3.1416F;
				this.Cloth2.rotateAngleX = -0.7854F;
				//腿擺動
				addk1 = addk1 * 0.1F - 1.2708F;
				addk2 = addk2 * 0.1F - 1.2708F;
				this.LegLeft.rotateAngleY = -0.2618F;
				this.LegRight.rotateAngleY = 0.2618F;
				//bag
				this.BagStrap1.rotateAngleX = 0.0872F;
				this.BagStrap1.rotateAngleY = 0F;
				this.BagStrap1.rotateAngleZ = -0.1745F;
				this.BagStrap2.rotateAngleX = 0.0872F;
				this.BagStrap2.rotateAngleY = 0.3491F;
				//tail
				//X旋轉過, 要繼續轉Y時, 就要補上Z修正
				//X越大, Z修正要越大, 且跟X角度反號, 具體角度需自行觀察
				this.TailBase.rotateAngleX = -1.3F;
				this.TailBase.rotateAngleY = -MathHelper.cos(f * 0.25F - 5.0F) * 0.2F * f1;
				this.TailBase.rotateAngleZ = MathHelper.cos(f * 0.25F - 5.0F) * 0.4F * f1;
				this.Tail1.rotateAngleX = 0.2618F;
				this.Tail1.rotateAngleY = -MathHelper.cos(f * 0.25F - 4.2F) * 0.3F * f1;
				this.Tail1.rotateAngleZ = -MathHelper.cos(f * 0.25F - 4.2F) * 0.1F * f1;
				this.Tail2.rotateAngleX = 0.2618F;
				this.Tail2.rotateAngleY = -MathHelper.cos(f * 0.25F - 3.5F) * 0.4F * f1;
				this.Tail2.rotateAngleZ = -MathHelper.cos(f * 0.25F - 3.5F) * 0.1F * f1;
				this.Tail3.rotateAngleX = 0.1745F;
				this.Tail3.rotateAngleY = -MathHelper.cos(f * 0.25F - 2.8F) * 0.5F * f1;
				this.Tail3.rotateAngleZ = 0F;//MathHelper.cos(f * 0.3F - 2.8F) * 0.05F * f1;
				this.Tail4.rotateAngleX = 0.1745F;
				this.Tail4.rotateAngleY = -MathHelper.cos(f * 0.25F - 2.1F) * 0.5F * f1;
				this.Tail4.rotateAngleZ = 0F;//MathHelper.cos(f * 0.3F - 2.1F) * 0.05F * f1;
				this.Tail5.rotateAngleX = 0.0873F;
				this.Tail5.rotateAngleY = -MathHelper.cos(f * 0.25F - 1.4F) * 0.4F * f1;
				this.Tail5.rotateAngleZ = 0F;//MathHelper.cos(f * 0.3F - 1.4F) * 0.02F * f1;
				this.Tail6.rotateAngleX = 0.0873F;
				this.Tail6.rotateAngleY = -MathHelper.cos(f * 0.25F - 0.7F) * 0.3F * f1;
				this.Tail6.rotateAngleZ = 0F;//MathHelper.cos(f * 0.3F - 0.7F) * 0.02F * f1;
				this.TailHeadBase.rotateAngleX = -0.0873F;
				this.TailHeadBase.rotateAngleY = -MathHelper.cos(f * 0.25F) * 0.2F * f1;
				this.TailHeadBase.rotateAngleZ = 0F;//MathHelper.cos(f * 0.3F) * 0.02F * f1;
				this.TailHead1.rotateAngleX = 0.3F;
				this.TailJaw1.rotateAngleX = angleX * 0.2F - 0.3F;
			}
			else if (t2 > 400)
			{	//run type 2
				//高度
				GlStateManager.translate(0F, 0.05F, 0F);
		  	    //手臂晃動 
			  	this.ArmLeft01.rotateAngleX = -1.0472F;
			    this.ArmLeft01.rotateAngleY = 0.2618F;
			    this.ArmLeft01.rotateAngleZ = 0F;
			    this.ArmRight01.rotateAngleX = -2.7925F;
				this.ArmRight01.rotateAngleY = 0F;
				this.ArmRight01.rotateAngleZ = f3 / -57F;
				//頭部角度
				this.Head.rotateAngleX *= 0.75F;
				this.Head.rotateAngleX -= 1.2217F;
				this.Cap2.rotateAngleX = -1.74F;
				//身體角度
				this.BodyMain.rotateAngleX = 1.2217F;
				this.Cloth2.rotateAngleX = -0.3491F;
				//腿擺動
				addk1 = -1.0472F;
				addk2 =-1.0472F;
				this.LegLeft.rotateAngleY = -0.3491F;
				this.LegRight.rotateAngleY = 0.3491F;
				//bag
				this.BagStrap1.rotateAngleX = 0.2618F;
				this.BagStrap1.rotateAngleY = 0F;
				this.BagStrap1.rotateAngleZ = 0F;
				this.BagStrap2.rotateAngleX = 0.3491F;
				this.BagStrap2.rotateAngleY = 0.3491F;
				//tail
				this.TailBase.rotateAngleX = 1.0472F;
				this.TailBase.rotateAngleY = 0F;
				this.TailBase.rotateAngleZ = 3.1415F;
				this.Tail1.rotateAngleX = 0.7854F;
				this.Tail1.rotateAngleY = 0F;
				this.Tail1.rotateAngleZ = 0F;
				this.Tail2.rotateAngleX = 0.7854F;
				this.Tail2.rotateAngleY = 0F;
				this.Tail2.rotateAngleZ = 0F;
				this.Tail3.rotateAngleX = 0.7854F;
				this.Tail3.rotateAngleY = 0F;
				this.Tail3.rotateAngleZ = 0F;
				this.Tail4.rotateAngleX = 0.7854F;
				this.Tail4.rotateAngleY = 0F;
				this.Tail4.rotateAngleZ = 0F;
				this.Tail5.rotateAngleX = 0.5236F;
				this.Tail5.rotateAngleY = 0F;
				this.Tail5.rotateAngleZ = 0F;
				this.Tail6.rotateAngleX = -0.2618F;
				this.Tail6.rotateAngleY = 0F;
				this.Tail6.rotateAngleZ = 0F;
				this.TailHeadBase.rotateAngleX = 0F;
				this.TailHeadBase.rotateAngleY = 0F;
				this.TailHeadBase.rotateAngleZ = 0F;
				this.TailHead1.rotateAngleX = 0.1745F;
				this.TailJaw1.rotateAngleX = angleX * 0.15F - 0.3F;
			}
			else
			{	//run type 3
				//高度
				GlStateManager.translate(0F, 0.1F, 0F);
		  	    //手臂晃動 
			  	this.ArmLeft01.rotateAngleX = MathHelper.cos(f * 0.8F) * 0.1F + 0.6981F;
			    this.ArmLeft01.rotateAngleY = 0F;
			    this.ArmLeft01.rotateAngleZ = -0.6981F;
			    this.ArmRight01.rotateAngleX = MathHelper.cos(f * 0.8F) * 0.1F + 0.6981F;
				this.ArmRight01.rotateAngleY = 0F;
				this.ArmRight01.rotateAngleZ = 0.6981F;
				//頭部角度
				this.Head.rotateAngleX *= 0.75F;
				this.Head.rotateAngleX -= 1.0472F;
				this.Cap2.rotateAngleX = -1.74F;
				//身體角度
				this.BodyMain.rotateAngleX = 0.8727F;
				this.Cloth2.rotateAngleX = -0.5236F;
				//腿擺動
				addk1 -= 0.5F;
				addk2 -= 0.5F;
				this.LegLeft.rotateAngleY = 0F;
				this.LegRight.rotateAngleY = 0F;
				//bag
				this.BagStrap1.rotateAngleX = 0.15F;
				this.BagStrap1.rotateAngleY = -1.0472F;
				this.BagStrap1.rotateAngleZ = 0F;
				this.BagStrap2.rotateAngleX = 0.3491F;
				this.BagStrap2.rotateAngleY = 1.0472F;
				//tail
				this.TailBase.rotateAngleX = -0.7F;
				this.TailBase.rotateAngleY = -MathHelper.cos(-f * 0.3F) * 0.2F * f1;
				this.TailBase.rotateAngleZ = MathHelper.cos(-f * 0.3F) * 0.3F * f1;
				this.Tail1.rotateAngleX = 0.2618F;
				this.Tail1.rotateAngleY = -MathHelper.cos(-f * 0.3F + 0.7F) * 0.2F * f1;
				this.Tail1.rotateAngleZ = -MathHelper.cos(-f * 0.3F + 0.7F) * 0.1F * f1;
				this.Tail2.rotateAngleX = 0.2618F;
				this.Tail2.rotateAngleY = -MathHelper.cos(-f * 0.3F + 1.4F) * 0.3F * f1;
				this.Tail2.rotateAngleZ = -MathHelper.cos(-f * 0.3F + 1.4F) * 0.1F * f1;
				this.Tail3.rotateAngleX = -0.2618F;
				this.Tail3.rotateAngleY = -MathHelper.cos(-f * 0.3F + 2.2F) * 0.3F * f1;
				this.Tail3.rotateAngleZ = MathHelper.cos(-f * 0.3F + 2.2F) * 0.1F * f1;
				this.Tail4.rotateAngleX = -0.2618F;
				this.Tail4.rotateAngleY = -MathHelper.cos(-f * 0.3F + 2.8F) * 0.4F * f1;
				this.Tail4.rotateAngleZ = MathHelper.cos(-f * 0.3F + 2.8F) * 0.1F * f1;
				this.Tail5.rotateAngleX = -0.2618F;
				this.Tail5.rotateAngleY = -MathHelper.cos(-f * 0.3F + 3.5F) * 0.4F * f1;
				this.Tail5.rotateAngleZ = MathHelper.cos(-f * 0.3F + 3.5F) * 0.1F * f1;
				this.Tail6.rotateAngleX = -0.2618F;
				this.Tail6.rotateAngleY = -MathHelper.cos(-f * 0.3F + 4.2F) * 0.5F * f1;
				this.Tail6.rotateAngleZ = MathHelper.cos(-f * 0.3F + 4.2F) * 0.1F * f1;
				this.TailHeadBase.rotateAngleX = 0.2618F;
				this.TailHeadBase.rotateAngleY = -MathHelper.cos(-f * 0.3F + 4.9F) * 0.6F * f1;
				this.TailHeadBase.rotateAngleZ = -MathHelper.cos(-f * 0.3F + 4.9F) * 0.1F * f1;
				this.TailHead1.rotateAngleX = 0.1745F;
				this.TailJaw1.rotateAngleX = angleX * 0.15F - 0.3F;
			}		
  		}
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
  		
	    if (ent.isSneaking())
	    {	//潛行, 蹲下動作
  			//高度
	    	GlStateManager.translate(0F, 0.1F, 0F);
	  	    //手臂晃動 
		  	this.ArmLeft01.rotateAngleX = 0.5236F;
		    this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft01.rotateAngleZ = -0.5236F;
		    this.ArmRight01.rotateAngleX = 0.5236F;
			this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight01.rotateAngleZ = 0.5236F;
			//頭部角度
			this.Head.rotateAngleX = -1.2217F;
			//身體角度
			this.BodyMain.rotateAngleX = 1.0472F;
			this.Cloth2.rotateAngleX = -0.5236F;
			//腿擺動
			addk1 = addk1 - 0.95F;
			addk2 = addk2 - 0.95F;
			this.LegLeft.rotateAngleY = 0F;
			this.LegRight.rotateAngleY = 0F;
			//bag
			this.BagStrap1.rotateAngleX = 0.15F;
			this.BagStrap1.rotateAngleY = -1.0472F;
			this.BagStrap1.rotateAngleZ = 0F;
			this.BagStrap2.rotateAngleX = 0.3491F;
			this.BagStrap2.rotateAngleY = 1.0472F;
			//tail
			this.TailBase.rotateAngleX = 0.7F;
			this.TailBase.rotateAngleY = 0F;
			this.TailBase.rotateAngleZ = 3.1416F;
			this.Tail1.rotateAngleX = -0.2618F;
			this.Tail1.rotateAngleY = 0F;
			this.Tail1.rotateAngleZ = 0F;
			this.Tail2.rotateAngleX = -0.5236F;
			this.Tail2.rotateAngleY = 0F;
			this.Tail2.rotateAngleZ = 0F;
			this.Tail3.rotateAngleX = -0.2618F;
			this.Tail3.rotateAngleY = 0F;
			this.Tail3.rotateAngleZ = 0F;
			this.Tail4.rotateAngleX = -0.2618F;
			this.Tail4.rotateAngleY = 0F;
			this.Tail4.rotateAngleZ = 0F;
			this.Tail5.rotateAngleX = -0.5236F;
			this.Tail5.rotateAngleY = 0F;
			this.Tail5.rotateAngleZ = 0F;
			this.Tail6.rotateAngleX = -0.5236F;
			this.Tail6.rotateAngleY = 0F;
			this.Tail6.rotateAngleZ = 0F;
			this.TailHeadBase.rotateAngleX = -0.2618F;
			this.TailHeadBase.rotateAngleY = 0F;
			this.TailHeadBase.rotateAngleZ = 0F;
			this.TailHead1.rotateAngleX = 0.1745F;
			this.TailJaw1.rotateAngleX = -0.2F;
  		}//end if sneaking
  		
	    //騎乘動作
	    if (ent.isSitting() || ent.isRiding())
	    {
	    	this.Cap2.isHidden = true;
	    	
	    	if (ent.ticksExisted % 1024 > 512)
	    	{
	    		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
	    			GlStateManager.translate(0F, 0.13F, 0F);
			    	//Body
					this.Head.rotateAngleX += 0.3F;
			    	this.BodyMain.rotateAngleX = -0.3F;
			    	this.Cloth2.rotateAngleX = -0.3F;
			  	    //arm 
					this.ArmLeft01.rotateAngleX = 2.3F;
				    this.ArmLeft01.rotateAngleY = 0F;
				    this.ArmLeft01.rotateAngleZ = 0.2F;
				    this.ArmLeft02.rotateAngleZ = 1F;
				    this.ArmRight01.rotateAngleX = 2.3F;
					this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = -0.2F;
					this.ArmRight02.rotateAngleZ = -1F;
					
					//arm special
			    	float parTick = f2 - (int)f2 + (ent.getTickExisted() % 256);
			    	
			    	if (parTick < 30F)
			    	{
			    		float az = MathHelper.sin(parTick * 0.033F * 1.5708F) * 1.6F;
				    	float az1 = az * 1.6F;
				    	
				    	setFace(3);
			    		//arm 
					    this.ArmLeft01.rotateAngleZ = 0.2F + az;
					    this.ArmLeft02.rotateAngleZ = 1F - az1;
					    if(this.ArmLeft02.rotateAngleZ < 0F) this.ArmLeft02.rotateAngleZ = 0F;
						this.ArmRight01.rotateAngleZ = -0.2F - az;
						this.ArmRight02.rotateAngleZ = -1F + az1;
						if(this.ArmRight02.rotateAngleZ > 0F) this.ArmRight02.rotateAngleZ = 0F;
			    	}
			    	else if (parTick < 45F)
			    	{
			    		setFace(3);
			    		//arm 
					    this.ArmLeft01.rotateAngleZ = 1.8F;
					    this.ArmLeft02.rotateAngleZ = 0F;
						this.ArmRight01.rotateAngleZ = -1.8F;
						this.ArmRight02.rotateAngleZ = 0F;
			    	}
			    	else if (parTick < 53F)
			    	{
			    		float az = MathHelper.cos((parTick - 45F) * 0.125F * 1.5708F);
				    	float az1 = az * 1.6F;
				    	
				    	//arm 
					    this.ArmLeft01.rotateAngleZ = 0.2F + az1;
					    this.ArmLeft02.rotateAngleZ = 1F - az;
						this.ArmRight01.rotateAngleZ = -0.2F - az1;
						this.ArmRight02.rotateAngleZ = -1F + az;
			    	}
			    	
					//bag
					this.BagStrap1.rotateAngleX = 0.6F;
					this.BagStrap1.rotateAngleY = 0F;
					this.BagStrap1.rotateAngleZ = 0F;
					this.BagStrap2.rotateAngleX = 1.0472F;
					this.BagStrap2.rotateAngleY = 1.3963F;
					//leg
					addk1 = angleX*0.1F -0.9F;
					addk2 = -angleX*0.1F -0.9F;
					this.LegLeft.rotateAngleY = -0.2F;
					this.LegRight.rotateAngleY = 0.2F;
					//tail
					this.TailBase.rotateAngleX = -1.0F;
					this.TailBase.rotateAngleY = 0.2618F;
					this.TailBase.rotateAngleZ = 0F;
					this.Tail1.rotateAngleX = 0.6981F;
					this.Tail1.rotateAngleY = 0.0872F;
					this.Tail1.rotateAngleZ = 0F;
					this.Tail2.rotateAngleX = 0.5236F;
					this.Tail2.rotateAngleY = 0.0872F;
					this.Tail2.rotateAngleZ = 0.1745F;
					this.Tail3.rotateAngleX = 0F;
					this.Tail3.rotateAngleY = 0.6981F;
					this.Tail3.rotateAngleZ = 0F;
					this.Tail4.rotateAngleX = 0F;
					this.Tail4.rotateAngleY = 0.6981F;
					this.Tail4.rotateAngleZ = 0F;
					this.Tail5.rotateAngleX = 0F;
					this.Tail5.rotateAngleY = 0.5236F;
					this.Tail5.rotateAngleZ = 0F;
					this.Tail6.rotateAngleX = 0F;
					this.Tail6.rotateAngleY = 0.5236F;
					this.Tail6.rotateAngleZ = 0F;
					this.TailHeadBase.rotateAngleX = 0.2618F;
					this.TailHeadBase.rotateAngleY = 0.5236F;
					this.TailHeadBase.rotateAngleZ = 0F;
					this.TailHead1.rotateAngleX = 0.2618F;
					this.TailJaw1.rotateAngleX = angleX * 0.1F - 0.2618F;
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.51F, 0F);
			    	//Body
		    		this.Head.rotateAngleX *= 0.8F;
			    	this.Head.rotateAngleX -= 1.8F;
			    	this.Head.rotateAngleY *= 0.5F;
			    	this.BodyMain.rotateAngleX = 1.5708F;
			    	this.Cloth2.rotateAngleX = -0.0524F;
			  	    //arm 
				  	this.ArmLeft01.rotateAngleX = -2.9671F;
				    this.ArmLeft01.rotateAngleY = 0F;
				    this.ArmLeft01.rotateAngleZ = 0.0349F;
				    this.ArmLeft02.rotateAngleZ = 1.3962F;
				    this.ArmRight01.rotateAngleX = -2.9671F;
					this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = -0.0349F;
					this.ArmRight02.rotateAngleZ = -1.3962F;
					//bag
					this.BagStrap1.rotateAngleX = 0.2618F;
					this.BagStrap1.rotateAngleY = -0.1396F;
					this.BagStrap1.rotateAngleZ = -0.1745F;
					this.BagStrap2.rotateAngleX = 0.3491F;
					this.BagStrap2.rotateAngleY = 0.3491F;
					//leg
					addk1 = -0.3491F;
					addk2 = -0.3491F;
					this.LegLeft.rotateAngleY = 0F;
					this.LegRight.rotateAngleY = 0F;
					//tail
					this.TailBase.rotateAngleX = -0.7F;
					this.TailBase.rotateAngleY = MathHelper.cos(-f2 * 0.1F) * 0.1F;
					this.TailBase.rotateAngleZ = MathHelper.cos(-f2 * 0.1F) * 0.05F;
					this.Tail1.rotateAngleX = 0.35F;
					this.Tail1.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 0.7F) * 0.2F;
					this.Tail1.rotateAngleZ = -MathHelper.cos(-f2 * 0.1F + 0.7F) * 0.05F;
					this.Tail2.rotateAngleX = 0.35F;
					this.Tail2.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 1.4F) * 0.3F;
					this.Tail2.rotateAngleZ = -MathHelper.cos(-f2 * 0.1F + 1.4F) * 0.05F;
					this.Tail3.rotateAngleX = 0.35F;
					this.Tail3.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.1F) * 0.4F;
					this.Tail3.rotateAngleZ = -MathHelper.cos(-f2 * 0.1F + 2.1F) * 0.05F;
					this.Tail4.rotateAngleX = -0.2618F;
					this.Tail4.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.8F) * 0.5F;
					this.Tail4.rotateAngleZ = MathHelper.cos(-f2 * 0.1F + 2.8F) * 0.025F;
					this.Tail5.rotateAngleX = -0.35F;
					this.Tail5.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 3.5F) * 0.55F;
					this.Tail5.rotateAngleZ = MathHelper.cos(-f2 * 0.1F + 3.5F) * 0.05F;
					this.Tail6.rotateAngleX = -0.35F;
					this.Tail6.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 4.2F) * 0.6F;
					this.Tail6.rotateAngleZ = MathHelper.cos(-f2 * 0.1F + 4.2F) * 0.05F;
					this.TailHeadBase.rotateAngleX = -0.15F;
					this.TailHeadBase.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 4.9F) * 0.65F;
					this.TailHeadBase.rotateAngleZ = MathHelper.cos(-f2 * 0.1F + 4.9F) * 0.025F;
					this.TailHead1.rotateAngleX = 0.2618F;
					this.TailJaw1.rotateAngleX = angleX * 0.1F - 0.15F;
		    	}
	    	}
	    	else
	    	{
	    		setFace(1);
		    	//高度
	    		GlStateManager.translate(0F, 0.17F, 0F);
		  	    //手臂晃動 
			  	this.ArmLeft01.rotateAngleX = -1.7F;
			    this.ArmLeft01.rotateAngleY = -0.1F;
			    this.ArmLeft01.rotateAngleZ = 0F;
			    this.ArmRight01.rotateAngleX = -1.8F;
				this.ArmRight01.rotateAngleY = 0.1F;
				this.ArmRight01.rotateAngleZ = 0F;
				//頭部角度
				this.Head.rotateAngleX = -1.5F;
				this.Head.rotateAngleY = 0F;
				this.Head.rotateAngleZ = 0.7F;
				this.Cap2.rotateAngleX = -1.74F;
				//身體角度
				this.BodyMain.rotateAngleX = 1.8F;
				this.Cloth2.rotateAngleX = -0.3491F;
				//腿擺動
				addk1 = -1.8F;
				addk2 =-1.8F;
				this.LegLeft.rotateAngleY = -0.23F;
				this.LegRight.rotateAngleY = 0.23F;
				//bag
				this.BagStrap1.rotateAngleX = 0.2618F;
				this.BagStrap1.rotateAngleY = 0F;
				this.BagStrap1.rotateAngleZ = 0F;
				this.BagStrap2.rotateAngleX = 0.3491F;
				this.BagStrap2.rotateAngleY = 0.3491F;
				//tail
				this.TailBase.rotateAngleX = 1.6F;
				this.TailBase.rotateAngleY = 0F;
				this.TailBase.rotateAngleZ = 3.1415F;
				this.Tail1.rotateAngleX = 0.8F;
				this.Tail1.rotateAngleY = 0F;
				this.Tail1.rotateAngleZ = 0F;
				this.Tail2.rotateAngleX = 0.8F;
				this.Tail2.rotateAngleY = 0F;
				this.Tail2.rotateAngleZ = 0F;
				this.Tail3.rotateAngleX = 0.9F;
				this.Tail3.rotateAngleY = 0F;
				this.Tail3.rotateAngleZ = 0F;
				this.Tail4.rotateAngleX = 0.9F;
				this.Tail4.rotateAngleY = 0F;
				this.Tail4.rotateAngleZ = 0F;
				this.Tail5.rotateAngleX = 0.4F;
				this.Tail5.rotateAngleY = 0F;
				this.Tail5.rotateAngleZ = 0F;
				this.Tail6.rotateAngleX = -0.4F;
				this.Tail6.rotateAngleY = 0F;
				this.Tail6.rotateAngleZ = 0F;
				this.TailHeadBase.rotateAngleX = -0.3F;
				this.TailHeadBase.rotateAngleY = 0F;
				this.TailHeadBase.rotateAngleZ = 0.8F;
				this.TailHead1.rotateAngleX = 0.1745F;
				this.TailJaw1.rotateAngleX = -0.5F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if (ent.getAttackTick() > 0)
	    {
	    	//高度
	    	GlStateManager.translate(0F, 0.13F, 0F);
	  	    //手臂晃動 
		  	this.ArmLeft01.rotateAngleX = 0.5236F;
		    this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft01.rotateAngleZ = -0.5236F;
		    this.ArmRight01.rotateAngleX = -2.7925F;
			this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight01.rotateAngleZ = -0.2618F;
			//頭部角度
			this.Head.rotateAngleX = -1.2217F;
			this.Head.rotateAngleY = 0F;
			//身體角度
			this.BodyMain.rotateAngleX = 1.0472F;
			this.Cloth2.rotateAngleX = -0.5236F;
			//腿擺動
			addk1 = addk1 - 1.48F;
			addk2 = addk2 - 0.26F;
			this.LegLeft.rotateAngleY = 0F;
			this.LegRight.rotateAngleY = 0F;
			//bag
			this.BagStrap1.rotateAngleX = 0.15F;
			this.BagStrap1.rotateAngleY = -1.0472F;
			this.BagStrap1.rotateAngleZ = 0F;
			this.BagStrap2.rotateAngleX = 0.3491F;
			this.BagStrap2.rotateAngleY = 0.3491F;
			//tail
			this.TailBase.rotateAngleX = 0.6F;
			this.TailBase.rotateAngleY = 0F;
			this.TailBase.rotateAngleZ = 3.1416F;
			this.Tail1.rotateAngleX = -0.2618F;
			this.Tail1.rotateAngleY = 0F;
			this.Tail1.rotateAngleZ = 0F;
			this.Tail2.rotateAngleX = -0.5236F;
			this.Tail2.rotateAngleY = 0F;
			this.Tail2.rotateAngleZ = 0F;
			this.Tail3.rotateAngleX = -0.2618F;
			this.Tail3.rotateAngleY = 0F;
			this.Tail3.rotateAngleZ = 0F;
			this.Tail4.rotateAngleX = -0.2618F;
			this.Tail4.rotateAngleY = 0F;
			this.Tail4.rotateAngleZ = 0F;
			this.Tail5.rotateAngleX = -0.5236F;
			this.Tail5.rotateAngleY = 0F;
			this.Tail5.rotateAngleZ = 0F;
			this.Tail6.rotateAngleX = -0.5236F;
			this.Tail6.rotateAngleY = 0F;
			this.Tail6.rotateAngleZ = 0F;
			this.TailHeadBase.rotateAngleX = -0.2618F;
			this.TailHeadBase.rotateAngleY = 0F;
			this.TailHeadBase.rotateAngleZ = 0F;
			
			if (ent.getAttackTick() > 47)
			{
				this.TailHead1.rotateAngleX = (50 - ent.getAttackTick()) * 0.15F + 0.4F;
				this.TailJaw1.rotateAngleX = (ent.getAttackTick() - 50) * 0.15F - 0.4F;
			}
			else if (ent.getAttackTick() > 39)
			{
				this.TailHead1.rotateAngleX = 0.76F - (46 - ent.getAttackTick()) * 0.06F;
				this.TailJaw1.rotateAngleX = -0.76F + (46 - ent.getAttackTick()) * 0.06F;
			}
			else
			{
				this.TailHead1.rotateAngleX = 0.4F;
				this.TailJaw1.rotateAngleX = -0.4F;
			}
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.6F;
	        this.ArmRight01.rotateAngleY = 0F;
	        this.ArmRight01.rotateAngleZ = 0.2F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	  	}
	    
	    //leg motion
	    this.LegLeft.rotateAngleX = addk1;
	    this.LegRight.rotateAngleX = addk2;
	}
  	
	//裝備模型顯示
    private void showEquip(IShipEmotion ent)
    {
		if (ent.getStateEmotion(ID.S.State) > ID.State.NORMAL)
		{
			this.Hair01.isHidden = false;
			this.HairU01.isHidden = false;
			this.Ear01.isHidden = false;
			this.Ear02.isHidden = false;
			this.Cap.isHidden = true;
			this.Cap2.isHidden = false;
		}
		else
		{
			this.Hair01.isHidden = true;
			this.HairU01.isHidden = true;
			this.Ear01.isHidden = true;
			this.Ear02.isHidden = true;
			this.Cap.isHidden = false;
			this.Cap2.isHidden = true;
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