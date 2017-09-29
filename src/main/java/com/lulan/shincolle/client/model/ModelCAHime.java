package com.lulan.shincolle.client.model;

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
 * ModelCAHime - PinkaLulan
 * Created using Tabula 5.1.0
 */
public class ModelCAHime extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight01;
    public ModelRenderer Neck;
    public ModelRenderer Head;
    public ModelRenderer TailBase;
    public ModelRenderer Band01;
    public ModelRenderer Band02;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer LegLeft02;
    public ModelRenderer LegRight02;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Ear01;
    public ModelRenderer Ear02;
    public ModelRenderer Horn01;
    public ModelRenderer Horn02;
    public ModelRenderer HatBase;
    public ModelRenderer Ahoke;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02a;
    public ModelRenderer Hair02b;
    public ModelRenderer Hair03a;
    public ModelRenderer Hair03b;
    public ModelRenderer Horn03;
    public ModelRenderer HatL;
    public ModelRenderer HatR;
    public ModelRenderer HatEyeL;
    public ModelRenderer HatEyeR;
    public ModelRenderer Tail01;
    public ModelRenderer Tail01_1;
    public ModelRenderer Tail02;
    public ModelRenderer Tail03;
    public ModelRenderer Tail04;
    public ModelRenderer Tail05;
    public ModelRenderer Tail06;
    public ModelRenderer Tail07;
    public ModelRenderer Tail08;
    public ModelRenderer Tail09;
    public ModelRenderer TailHead01;
    public ModelRenderer TailJaw01;
    public ModelRenderer TailC01;
    public ModelRenderer TailC02;
    public ModelRenderer Tail02_1;
    public ModelRenderer Tail03_1;
    public ModelRenderer Tail04_1;
    public ModelRenderer Tail05_1;
    public ModelRenderer Tail06_1;
    public ModelRenderer Tail07_1;
    public ModelRenderer Tail08_1;
    public ModelRenderer Tail09_1;
    public ModelRenderer TailHead01_1;
    public ModelRenderer TailJaw01_1;
    public ModelRenderer TailC01_1;
    public ModelRenderer TailC02_1;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;
    

    public ModelCAHime()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.scale = 0.45F;
        this.offsetY = 2.22F;
        this.offsetItem = new float[] {0.07F, 0.99F, -0.09F};
        this.offsetBlock = new float[] {0.07F, 0.99F, -0.09F};
        
        this.Hair01 = new ModelRenderer(this, 0, 40);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.6F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 7, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.3490658503988659F, 0.0F, 0.0F);
        this.Hair03a = new ModelRenderer(this, 90, 32);
        this.Hair03a.setRotationPoint(6.4F, 9.8F, 5.5F);
        this.Hair03a.addBox(-1.5F, 0.0F, -3.0F, 3, 12, 4, 0.0F);
        this.setRotateAngle(Hair03a, -0.20943951023931953F, -0.13962634015954636F, 0.06981317007977318F);
        this.LegLeft02 = new ModelRenderer(this, 46, 105);
        this.LegLeft02.setRotationPoint(0.0F, 8.0F, -2.5F);
        this.LegLeft02.addBox(-2.5F, 0.0F, 0.0F, 5, 7, 5, 0.0F);
        this.Neck = new ModelRenderer(this, 0, 78);
        this.Neck.setRotationPoint(0.0F, -2.0F, -9.4F);
        this.Neck.addBox(-5.0F, -4.0F, -4.5F, 10, 5, 9, 0.0F);
        this.setRotateAngle(Neck, 0.41887902047863906F, 0.0F, 0.0F);
        this.Horn03 = new ModelRenderer(this, 40, 39);
        this.Horn03.setRotationPoint(1.5F, 1.5F, -6.0F);
        this.Horn03.addBox(-3.0F, -3.0F, -6.0F, 3, 3, 6, 0.0F);
        this.setRotateAngle(Horn03, -0.6981317007977318F, 0.0F, 0.0F);
        this.HatEyeR = new ModelRenderer(this, 22, 28);
        this.HatEyeR.mirror = true;
        this.HatEyeR.setRotationPoint(-9.6F, -6.0F, 5.3F);
        this.HatEyeR.addBox(-1.0F, -3.0F, -3.0F, 1, 6, 6, 0.0F);
        this.setRotateAngle(HatEyeR, 0.08726646259971647F, 0.05235987755982988F, 0.05235987755982988F);
        this.TailHead01_1 = new ModelRenderer(this, 40, 0);
        this.TailHead01_1.setRotationPoint(0.0F, -1.8F, 3.5F);
        this.TailHead01_1.addBox(-4.5F, 0.0F, 0.0F, 9, 6, 10, 0.0F);
        this.setRotateAngle(TailHead01_1, -0.17453292519943295F, 0.0F, 0.0F);
        this.Tail03 = new ModelRenderer(this, 54, 16);
        this.Tail03.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail03.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail03, 0.6108652381980153F, -0.08726646259971647F, 0.0F);
        this.HatL = new ModelRenderer(this, 0, 0);
        this.HatL.setRotationPoint(-1.3F, 2.1F, -2.9F);
        this.HatL.addBox(0.0F, -14.0F, -1.0F, 10, 16, 10, 0.0F);
        this.setRotateAngle(HatL, 0.5235987755982988F, 0.08726646259971647F, 0.06981317007977318F);
        this.Tail05_1 = new ModelRenderer(this, 58, 19);
        this.Tail05_1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail05_1.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail05_1, 0.5235987755982988F, 0.3490658503988659F, 0.0F);
        this.Tail06 = new ModelRenderer(this, 83, 0);
        this.Tail06.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail06.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail06, 0.3490658503988659F, 0.0F, 0.0F);
        this.Tail09_1 = new ModelRenderer(this, 96, 0);
        this.Tail09_1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail09_1.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail09_1, -0.08726646259971647F, 0.4363323129985824F, 0.0F);
        this.Tail08_1 = new ModelRenderer(this, 83, 0);
        this.Tail08_1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail08_1.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail08_1, -0.5235987755982988F, 0.3490658503988659F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 66, 92);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.0F, 3.0F, 8.3F);
        this.LegRight01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(LegRight01, -0.13962634015954636F, 0.0F, -0.17453292519943295F);
        this.Tail02_1 = new ModelRenderer(this, 56, 17);
        this.Tail02_1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail02_1.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail02_1, 0.3490658503988659F, 0.2617993877991494F, 0.0F);
        this.Hair02b = new ModelRenderer(this, 81, 116);
        this.Hair02b.setRotationPoint(-6.9F, 4.7F, 0.0F);
        this.Hair02b.addBox(-1.5F, 0.0F, -3.3F, 3, 7, 5, 0.0F);
        this.setRotateAngle(Hair02b, 0.0F, 0.0F, 0.08726646259971647F);
        this.LegLeft01 = new ModelRenderer(this, 46, 92);
        this.LegLeft01.setRotationPoint(4.0F, 3.0F, 8.3F);
        this.LegLeft01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(LegLeft01, 0.13962634015954636F, 0.0F, 0.17453292519943295F);
        this.Band02 = new ModelRenderer(this, 40, 39);
        this.Band02.setRotationPoint(-4.5F, 1.7F, -12.0F);
        this.Band02.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 0, 0.0F);
        this.setRotateAngle(Band02, -0.08726646259971647F, 0.0F, 0.0F);
        this.Hair02a = new ModelRenderer(this, 81, 116);
        this.Hair02a.setRotationPoint(6.9F, 4.7F, 0.0F);
        this.Hair02a.addBox(-1.5F, 0.0F, -3.3F, 3, 7, 5, 0.0F);
        this.setRotateAngle(Hair02a, 0.0F, 0.0F, -0.08726646259971647F);
        this.HairMain = new ModelRenderer(this, 0, 56);
        this.HairMain.setRotationPoint(0.0F, -11.5F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 12, 10, 0.0F);
        this.HatEyeL = new ModelRenderer(this, 22, 28);
        this.HatEyeL.setRotationPoint(9.6F, -6.0F, 5.3F);
        this.HatEyeL.addBox(0.0F, -3.0F, -3.0F, 1, 6, 6, 0.0F);
        this.setRotateAngle(HatEyeL, 0.08726646259971647F, -0.05235987755982988F, -0.05235987755982988F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(2.0F, -4.0F, -7.6F);
        this.Ahoke.addBox(0.0F, 0.0F, -12.0F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, -0.2617993877991494F, 1.48352986419518F, -0.2617993877991494F);
        this.Tail02 = new ModelRenderer(this, 58, 17);
        this.Tail02.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail02.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail02, 0.6108652381980153F, -0.08726646259971647F, 0.0F);
        this.Tail05 = new ModelRenderer(this, 53, 16);
        this.Tail05.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail05.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail05, 0.5235987755982988F, 0.0F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 0, 92);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-4.0F, 3.0F, -6.0F);
        this.ArmRight01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.13962634015954636F, 0.0F, -0.20943951023931953F);
        this.TailBase = new ModelRenderer(this, 57, 21);
        this.TailBase.setRotationPoint(0.0F, 7.0F, -2.0F);
        this.TailBase.addBox(-4.0F, -2.0F, 0.0F, 8, 5, 7, 0.0F);
        this.Tail07 = new ModelRenderer(this, 86, 0);
        this.Tail07.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail07.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail07, 0.17453292519943295F, 0.08726646259971647F, 0.0F);
        this.TailHead01 = new ModelRenderer(this, 40, 0);
        this.TailHead01.setRotationPoint(0.0F, -1.8F, 3.5F);
        this.TailHead01.addBox(-4.5F, 0.0F, 0.0F, 9, 6, 10, 0.0F);
        this.setRotateAngle(TailHead01, -0.17453292519943295F, 0.0F, 0.0F);
        this.Ear01 = new ModelRenderer(this, 0, 26);
        this.Ear01.setRotationPoint(4.2F, -11.0F, 6.8F);
        this.Ear01.addBox(-2.0F, 0.0F, -7.0F, 4, 7, 7, 0.0F);
        this.setRotateAngle(Ear01, -0.8377580409572781F, -0.12217304763960307F, 0.17453292519943295F);
        this.Tail06_1 = new ModelRenderer(this, 85, 2);
        this.Tail06_1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail06_1.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail06_1, 0.08726646259971647F, 0.2617993877991494F, 0.0F);
        this.TailJaw01 = new ModelRenderer(this, 90, 18);
        this.TailJaw01.setRotationPoint(0.0F, -0.7F, 3.3F);
        this.TailJaw01.addBox(-4.5F, -4.0F, 0.0F, 9, 4, 10, 0.0F);
        this.setRotateAngle(TailJaw01, 0.2617993877991494F, 0.0F, 0.0F);
        this.Hair03b = new ModelRenderer(this, 90, 32);
        this.Hair03b.setRotationPoint(-6.4F, 9.8F, 5.5F);
        this.Hair03b.addBox(-1.5F, 0.0F, -3.0F, 3, 12, 4, 0.0F);
        this.setRotateAngle(Hair03b, -0.20943951023931953F, 0.13962634015954636F, -0.06981317007977318F);
        this.Tail08 = new ModelRenderer(this, 83, 0);
        this.Tail08.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail08.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail08, 0.08726646259971647F, 0.2617993877991494F, 0.0F);
        this.Tail07_1 = new ModelRenderer(this, 86, 0);
        this.Tail07_1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail07_1.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail07_1, -0.3490658503988659F, 0.3490658503988659F, 0.0F);
        this.Horn02 = new ModelRenderer(this, 40, 39);
        this.Horn02.setRotationPoint(3.3F, -7.5F, -6.0F);
        this.Horn02.addBox(-1.5F, -1.5F, -6.0F, 3, 3, 6, 0.0F);
        this.setRotateAngle(Horn02, -0.8726646259971648F, -0.4363323129985824F, 0.2617993877991494F);
        this.BodyMain = new ModelRenderer(this, 0, 93);
        this.BodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain.addBox(-5.5F, -4.5F, -12.0F, 11, 10, 24, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 66, 105);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(0.0F, 8.0F, -2.5F);
        this.LegRight02.addBox(-2.5F, 0.0F, 0.0F, 5, 7, 5, 0.0F);
        this.Tail09 = new ModelRenderer(this, 96, 0);
        this.Tail09.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail09.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail09, -0.08726646259971647F, 0.4363323129985824F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 40);
        this.Hair.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.2F, 16, 17, 8, 0.0F);
        this.TailC02_1 = new ModelRenderer(this, 100, 8);
        this.TailC02_1.setRotationPoint(-2.0F, 4.5F, 9.5F);
        this.TailC02_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
        this.setRotateAngle(TailC02_1, -0.13962634015954636F, -0.03490658503988659F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 65);
        this.Head.setRotationPoint(0.0F, -6.0F, -13.0F);
        this.Head.addBox(-7.0F, -11.0F, -6.5F, 14, 14, 13, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 0, 92);
        this.ArmLeft01.setRotationPoint(4.0F, 3.0F, -6.0F);
        this.ArmLeft01.addBox(-2.5F, 0.0F, -2.5F, 5, 8, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, -0.13962634015954636F, 0.0F, 0.20943951023931953F);
        this.Band01 = new ModelRenderer(this, 40, 39);
        this.Band01.setRotationPoint(4.5F, 1.7F, -12.0F);
        this.Band01.addBox(-0.5F, 0.0F, 0.0F, 1, 6, 0, 0.0F);
        this.setRotateAngle(Band01, -0.17453292519943295F, 0.0F, 0.0F);
        this.TailJaw01_1 = new ModelRenderer(this, 90, 18);
        this.TailJaw01_1.setRotationPoint(0.0F, -0.7F, 2.7F);
        this.TailJaw01_1.addBox(-4.5F, -4.0F, 0.0F, 9, 4, 10, 0.0F);
        this.setRotateAngle(TailJaw01_1, 0.17453292519943295F, 0.0F, 0.0F);
        this.HatBase = new ModelRenderer(this, 0, 0);
        this.HatBase.setRotationPoint(0.0F, -3.1F, 5.8F);
        this.HatBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Tail01 = new ModelRenderer(this, 58, 16);
        this.Tail01.setRotationPoint(1.5F, 0.0F, 3.0F);
        this.Tail01.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail01, 0.2617993877991494F, 1.5707963267948966F, 0.0F);
        this.TailC01 = new ModelRenderer(this, 100, 8);
        this.TailC01.setRotationPoint(2.0F, 4.5F, 9.5F);
        this.TailC01.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
        this.setRotateAngle(TailC01, -0.13962634015954636F, 0.03490658503988659F, 0.0F);
        this.Tail04 = new ModelRenderer(this, 54, 19);
        this.Tail04.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail04.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail04, 0.5235987755982988F, 0.0F, 0.0F);
        this.Tail04_1 = new ModelRenderer(this, 53, 18);
        this.Tail04_1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail04_1.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail04_1, 0.3490658503988659F, 0.4363323129985824F, 0.0F);
        this.Tail01_1 = new ModelRenderer(this, 54, 16);
        this.Tail01_1.setRotationPoint(-1.5F, 0.0F, 3.0F);
        this.Tail01_1.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail01_1, 0.6981317007977318F, -1.5707963267948966F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 0, 105);
        this.ArmLeft02.setRotationPoint(2.5F, 8.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 7, 5, 0.0F);
        this.HatR = new ModelRenderer(this, 0, 0);
        this.HatR.mirror = true;
        this.HatR.setRotationPoint(1.3F, 2.1F, -2.9F);
        this.HatR.addBox(-10.0F, -14.0F, -1.0F, 10, 16, 10, 0.0F);
        this.setRotateAngle(HatR, 0.5235987755982988F, -0.08726646259971647F, -0.06981317007977318F);
        this.Tail03_1 = new ModelRenderer(this, 58, 16);
        this.Tail03_1.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Tail03_1.addBox(-4.0F, -3.5F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(Tail03_1, 0.4363323129985824F, 0.3490658503988659F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 0, 105);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-2.5F, 8.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 7, 5, 0.0F);
        this.Ear02 = new ModelRenderer(this, 0, 26);
        this.Ear02.mirror = true;
        this.Ear02.setRotationPoint(-4.2F, -11.0F, 6.8F);
        this.Ear02.addBox(-2.0F, 0.0F, -7.0F, 4, 7, 7, 0.0F);
        this.setRotateAngle(Ear02, -0.8377580409572781F, 0.12217304763960307F, -0.17453292519943295F);
        this.TailC01_1 = new ModelRenderer(this, 100, 8);
        this.TailC01_1.setRotationPoint(2.0F, 4.5F, 9.5F);
        this.TailC01_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
        this.setRotateAngle(TailC01_1, -0.13962634015954636F, 0.03490658503988659F, 0.0F);
        this.TailC02 = new ModelRenderer(this, 100, 8);
        this.TailC02.setRotationPoint(-2.0F, 4.5F, 9.5F);
        this.TailC02.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
        this.setRotateAngle(TailC02, -0.13962634015954636F, -0.03490658503988659F, 0.0F);
        this.Horn01 = new ModelRenderer(this, 40, 39);
        this.Horn01.setRotationPoint(-3.0F, -7.5F, -6.0F);
        this.Horn01.addBox(-1.5F, -1.5F, -6.0F, 3, 3, 6, 0.0F);
        this.setRotateAngle(Horn01, -0.8726646259971648F, 0.4363323129985824F, -0.5235987755982988F);
        this.HairMain.addChild(this.Hair01);
        this.HairMain.addChild(this.Hair03a);
        this.LegLeft01.addChild(this.LegLeft02);
        this.BodyMain.addChild(this.Neck);
        this.Horn02.addChild(this.Horn03);
        this.HatR.addChild(this.HatEyeR);
        this.Tail09_1.addChild(this.TailHead01_1);
        this.Tail02.addChild(this.Tail03);
        this.HatBase.addChild(this.HatL);
        this.Tail04_1.addChild(this.Tail05_1);
        this.Tail05.addChild(this.Tail06);
        this.Tail08_1.addChild(this.Tail09_1);
        this.Tail07_1.addChild(this.Tail08_1);
        this.BodyMain.addChild(this.LegRight01);
        this.Tail01_1.addChild(this.Tail02_1);
        this.HairMain.addChild(this.Hair02b);
        this.BodyMain.addChild(this.LegLeft01);
        this.BodyMain.addChild(this.Band02);
        this.HairMain.addChild(this.Hair02a);
        this.Head.addChild(this.HairMain);
        this.HatL.addChild(this.HatEyeL);
        this.Hair.addChild(this.Ahoke);
        this.Tail01.addChild(this.Tail02);
        this.Tail04.addChild(this.Tail05);
        this.BodyMain.addChild(this.ArmRight01);
        this.BodyMain.addChild(this.TailBase);
        this.Tail06.addChild(this.Tail07);
        this.Tail09.addChild(this.TailHead01);
        this.Head.addChild(this.Ear01);
        this.Tail05_1.addChild(this.Tail06_1);
        this.Tail09.addChild(this.TailJaw01);
        this.HairMain.addChild(this.Hair03b);
        this.Tail07.addChild(this.Tail08);
        this.Tail06_1.addChild(this.Tail07_1);
        this.Head.addChild(this.Horn02);
        this.LegRight01.addChild(this.LegRight02);
        this.Tail08.addChild(this.Tail09);
        this.Head.addChild(this.Hair);
        this.TailHead01_1.addChild(this.TailC02_1);
        this.BodyMain.addChild(this.Head);
        this.BodyMain.addChild(this.ArmLeft01);
        this.BodyMain.addChild(this.Band01);
        this.Tail09_1.addChild(this.TailJaw01_1);
        this.Head.addChild(this.HatBase);
        this.TailBase.addChild(this.Tail01);
        this.TailHead01.addChild(this.TailC01);
        this.Tail03.addChild(this.Tail04);
        this.Tail03_1.addChild(this.Tail04_1);
        this.TailBase.addChild(this.Tail01_1);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.HatBase.addChild(this.HatR);
        this.Tail02_1.addChild(this.Tail03_1);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Head.addChild(this.Ear02);
        this.TailHead01_1.addChild(this.TailC01_1);
        this.TailHead01.addChild(this.TailC02);
        this.Head.addChild(this.Horn01);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -6.0F, -13.0F);
        //change face offset
        this.Face0 = new ModelRenderer(this, 98, 63);
        this.Face0.setRotationPoint(0.0F, -8.5F, -6.1F);
        this.Face0.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 76);
        this.Face1.setRotationPoint(0.0F, -8.5F, -6.1F);
        this.Face1.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 89);
        this.Face2.setRotationPoint(0.0F, -8.5F, -6.1F);
        this.Face2.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 102);
        this.Face3.setRotationPoint(0.0F, -8.5F, -6.1F);
        this.Face3.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 115);
        this.Face4.setRotationPoint(0.0F, -8.5F, -6.1F);
        this.Face4.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Mouth0 = new ModelRenderer(this, 100, 53);
        this.Mouth0.setRotationPoint(0.0F, -0.7F, -6.2F);
        this.Mouth0.addBox(-3.0F, 0.0F, -0.5F, 6, 4, 1, 0.0F);
        this.Mouth1 = new ModelRenderer(this, 100, 58);
        this.Mouth1.setRotationPoint(0.0F, -0.7F, -6.2F);
        this.Mouth1.addBox(-3.0F, 0.0F, -0.5F, 6, 4, 1, 0.0F);
        this.Mouth2 = new ModelRenderer(this, 114, 53);
        this.Mouth2.setRotationPoint(0.0F, -0.7F, -6.2F);
        this.Mouth2.addBox(-3.0F, 0.0F, -0.5F, 6, 4, 1, 0.0F);
        this.Flush0 = new ModelRenderer(this, 114, 58);
        this.Flush0.setRotationPoint(-6F, 0.7F, -6.8F);
        this.Flush0.addBox(-1.0F, 0.0F, -0.5F, 2, 1, 0, 0.0F);
        this.Flush1 = new ModelRenderer(this, 114, 58);
        this.Flush1.setRotationPoint(6F, 0.7F, -6.8F);
        this.Flush1.addBox(-1.0F, 0.0F, -0.5F, 2, 1, 0, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowHead);
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
  		case ID.ModelState.EQUIP00:
  			this.HatBase.isHidden = false;
  			this.Hair01.isHidden = true;
  			this.Horn01.isHidden = true;
  			this.Horn02.isHidden = true;
  			this.Ear01.isHidden = true;
  			this.Ear02.isHidden = true;
  		break;
  		case ID.ModelState.EQUIP02:
  			this.HatBase.isHidden = true;
  			this.Hair01.isHidden = false;
  			this.Horn01.isHidden = false;
  			this.Horn02.isHidden = false;
  			this.Ear01.isHidden = false;
  			this.Ear02.isHidden = false;
  		break;
  		default:  //normal
  			this.HatBase.isHidden = false;
  			this.Hair01.isHidden = true;
  			this.Horn01.isHidden = false;
  			this.Horn02.isHidden = false;
  			this.Ear01.isHidden = false;
  			this.Ear02.isHidden = false;
  		break;
  		}
  		
  		switch (ent.getStateEmotion(ID.S.State2))
  		{
  		case ID.ModelState.EQUIP02a:
  			this.TailBase.isHidden = true;
  		break;
  		default:  //normal
  			this.TailBase.isHidden = false;
  		break;
  		}
	}

	@Override
	public void syncRotationGlowPart()
	{
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.2F, 0F);
    	this.setFaceHungry(ent);

  	    //頭部
	  	this.Head.rotateAngleX = 0.7853F;
	  	this.Head.rotateAngleY = 0F;
	  	//Body
  	    this.Ahoke.rotateAngleX = -0.2618F;
	  	this.BodyMain.rotateAngleX = 0F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = -1.4835F;
	  	this.Head.offsetY = 0F;
	  	this.GlowHead.offsetY = 0F;
	  	//arm
	  	this.ArmLeft01.rotateAngleX = -0.4F;
	  	this.ArmLeft01.rotateAngleZ = 0.4537F;
	  	this.ArmLeft01.offsetZ = 0F;
	  	this.ArmLeft02.rotateAngleZ = 0F;
	  	this.ArmRight01.rotateAngleX = -0.8F;
	  	this.ArmRight01.rotateAngleZ = -0.05F;
	  	this.ArmRight01.offsetZ = 0F;
	  	this.ArmRight02.rotateAngleZ = 0F;
		//leg
	  	this.LegLeft01.rotateAngleX = 0.5F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.4537F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegRight01.rotateAngleX = 0.8F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.05F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		//equip: hat position
  		switch (ent.getStateEmotion(ID.S.State))
  		{
  		case ID.ModelState.EQUIP00:
  			this.HatBase.rotateAngleX = 1.37F;
  			this.HatBase.offsetY = -0.45F;
  			this.HatBase.offsetZ = -0.2F;
  		break;
  		case ID.ModelState.EQUIP01:
  			this.HatBase.rotateAngleX = -1.8F;
  			this.HatBase.offsetY = 0.6F;
  			this.HatBase.offsetZ = 0.07F;
  		break;
  		default:  //normal
  			this.HatBase.rotateAngleX = 0F;
  			this.HatBase.offsetY = 0F;
  			this.HatBase.offsetZ = 0F;
  		break;
  		}
		//tail head
		this.TailHead01.rotateAngleX = -0.17F;
		this.TailJaw01.rotateAngleX = 0.26F;
		this.TailHead01_1.rotateAngleX = 0F;
		this.TailJaw01_1.rotateAngleX = 0.2F;
		//tail body
		this.TailBase.isHidden = false;
		this.Tail01.rotateAngleX = -1.4F;
		this.Tail01.rotateAngleY = 1.57F;
		this.Tail02.rotateAngleX = -0.3F;
		this.Tail02.rotateAngleY = 0.2F;
		this.Tail03.rotateAngleX = -0.3F;
		this.Tail03.rotateAngleY = 0.3F;
		this.Tail04.rotateAngleX = 0.2F;
		this.Tail04.rotateAngleY = 0.4F;
		this.Tail05.rotateAngleX = 0.1F;
		this.Tail05.rotateAngleY = 0.5F;
		this.Tail06.rotateAngleX = -0.1F;
		this.Tail06.rotateAngleY = 0.4F;
		this.Tail07.rotateAngleX = -0.1F;
		this.Tail07.rotateAngleY = 0.3F;
		this.Tail08.rotateAngleX = 0.1F;
		this.Tail08.rotateAngleY = 0.2F;
		this.Tail09.rotateAngleX = 0F;
		this.Tail09.rotateAngleY = 0.1F;
		this.Tail01_1.rotateAngleX = -1.4F;
		this.Tail01_1.rotateAngleY = -1.7F;
		this.Tail02_1.rotateAngleX = -0.2F;
		this.Tail02_1.rotateAngleY = 0.2F;
		this.Tail03_1.rotateAngleX = -0.1F;
		this.Tail03_1.rotateAngleY = 0.3F;
		this.Tail04_1.rotateAngleX = 0F;
		this.Tail04_1.rotateAngleY = 0.4F;
		this.Tail05_1.rotateAngleX = 0F;
		this.Tail05_1.rotateAngleY = 0.5F;
		this.Tail06_1.rotateAngleX = -0.1F;
		this.Tail06_1.rotateAngleY = 0.4F;
		this.Tail07_1.rotateAngleX = -0.1F;
		this.Tail07_1.rotateAngleY = 0.3F;
		this.Tail08_1.rotateAngleX = 0.2F;
		this.Tail08_1.rotateAngleY = 0.2F;
		this.Tail09_1.rotateAngleX = -0.2F;
		this.Tail09_1.rotateAngleY = 0.3F;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2*0.08F + f * 0.25F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.08F + 0.6F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
  		
    	//leg move
  		addk1 = angleAdd1 * 0.35F - 0.14F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.35F + 0.14F;  //LegRight01
	  	this.ArmRight01.rotateAngleX = addk1;
    	this.ArmLeft01.rotateAngleX = addk2;

  	    //head
	  	this.Head.rotateAngleX = f4 * 0.014F; 	//上下角度
	  	this.Head.rotateAngleY = f3 * 0.01F;	//左右角度 角度轉成rad 即除以57.29578
	  	//body
	  	this.Ahoke.rotateAngleX = angleX * 0.05F - 0.2618F;
	  	this.BodyMain.rotateAngleX = 0F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Head.offsetY = 0F;
	  	this.GlowHead.offsetY = 0F;
	  	//arm
	  	this.ArmLeft01.rotateAngleY = 0F;
	  	this.ArmLeft01.rotateAngleZ = 0.21F;
	  	this.ArmLeft01.offsetZ = 0F;
	  	this.ArmLeft02.rotateAngleZ = 0F;
	  	this.ArmRight01.rotateAngleY = 0F;
	  	this.ArmRight01.rotateAngleZ = -0.21F;
	  	this.ArmRight01.offsetZ = 0F;
	  	this.ArmRight02.rotateAngleZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.1745F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.1745F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		//equip: hat position
  		switch (ent.getStateEmotion(ID.S.State))
  		{
  		case ID.ModelState.EQUIP00:
  			this.HatBase.rotateAngleX = 1.37F;
  			this.HatBase.offsetY = -0.45F;
  			this.HatBase.offsetZ = -0.2F;
  		break;
  		case ID.ModelState.EQUIP01:
  			this.HatBase.rotateAngleX = -0.85F;
  			this.HatBase.offsetY = 0.33F;
  			this.HatBase.offsetZ = 0.07F;
  		break;
  		default:  //normal
  			this.HatBase.rotateAngleX = 0F;
  			this.HatBase.offsetY = 0F;
  			this.HatBase.offsetZ = 0F;
  		break;
  		}
  		
  		//equip: tail position
  		float[] cosf2 = new float[9];
  		for (int i = 0; i < 9; i++)
  		{
  			cosf2[i] = MathHelper.cos(f2 * 0.1F + f * 0.25F + 0.8F * i);
  		}
  		
		//tail head
		this.TailHead01.rotateAngleX = -angleX * 0.075F - 0.1F;
		this.TailJaw01.rotateAngleX = angleX * 0.1F + 0.18F;
		this.TailHead01_1.rotateAngleX = -angleX2 * 0.12F - 0.1F;
		this.TailJaw01_1.rotateAngleX = angleX2 * 0.15F + 0.26F;
		this.TailC01.rotateAngleX = angleX1 * 0.3F - 0.2F;
		this.TailC02.rotateAngleX = angleX2 * 0.3F - 0.2F;
		this.TailC01_1.rotateAngleX = angleX1 * 0.3F - 0.2F;
		this.TailC02_1.rotateAngleX = angleX2 * 0.3F - 0.2F;
  		
		//tail body
  		switch (ent.getStateEmotion(ID.S.State2))
  		{
  		case ID.ModelState.EQUIP00a:
  			this.TailBase.offsetY = -0.15F;
  			this.TailBase.offsetZ = 0F;
  			this.Tail01.rotateAngleX = -0.17F + cosf2[0] * 0.03F;
  			this.Tail01.rotateAngleY = 1.3F + cosf2[0] * 0.03F;
  			this.Tail02.rotateAngleX = 0.26F + cosf2[1] * 0.03F;
  			this.Tail02.rotateAngleY = -0.52F + cosf2[1] * 0.03F;
  			this.Tail03.rotateAngleX = 0.35F + cosf2[2] * 0.03F;
  			this.Tail03.rotateAngleY = -0.52F + cosf2[2] * 0.03F;
  			this.Tail04.rotateAngleX = 0.52F + cosf2[3] * 0.03F;
  			this.Tail04.rotateAngleY = -0.44F + cosf2[3] * 0.03F;
  			this.Tail05.rotateAngleX = 0.52F + cosf2[4] * 0.04F;
  			this.Tail05.rotateAngleY = -0.17F + cosf2[4] * 0.04F;
  			this.Tail06.rotateAngleX = 0.35F + cosf2[5] * 0.05F;
  			this.Tail06.rotateAngleY = 0.35F + cosf2[5] * 0.05F;
  			this.Tail07.rotateAngleX = 0.44F + cosf2[6] * 0.06F;
  			this.Tail07.rotateAngleY = 0.17F + cosf2[6] * 0.06F;
  			this.Tail08.rotateAngleX = 0.52F + cosf2[7] * 0.08F;
  			this.Tail08.rotateAngleY = 0.17F + cosf2[7] * 0.08F;
  			this.Tail09.rotateAngleX = 0.52F + cosf2[8] * 0.15F;
  			this.Tail09.rotateAngleY = 0.17F + cosf2[8] * 0.15F;
  			this.Tail01_1.rotateAngleX = -0.17F + cosf2[0] * 0.03F;
  			this.Tail01_1.rotateAngleY = -1.3F + cosf2[0] * 0.03F;
  			this.Tail02_1.rotateAngleX = 0.26F + cosf2[1] * 0.03F;
  			this.Tail02_1.rotateAngleY = 0.52F + cosf2[1] * 0.03F;
  			this.Tail03_1.rotateAngleX = 0.35F + cosf2[2] * 0.03F;
  			this.Tail03_1.rotateAngleY = 0.52F + cosf2[2] * 0.03F;
  			this.Tail04_1.rotateAngleX = 0.52F + cosf2[3] * 0.03F;
  			this.Tail04_1.rotateAngleY = 0.44F + cosf2[3] * 0.03F;
  			this.Tail05_1.rotateAngleX = 0.52F + cosf2[4] * 0.04F;
  			this.Tail05_1.rotateAngleY = 0.17F + cosf2[4] * 0.04F;
  			this.Tail06_1.rotateAngleX = 0.35F + cosf2[5] * 0.05F;
  			this.Tail06_1.rotateAngleY = -0.35F + cosf2[5] * 0.05F;
  			this.Tail07_1.rotateAngleX = 0.44F + cosf2[6] * 0.06F;
  			this.Tail07_1.rotateAngleY = -0.17F + cosf2[6] * 0.06F;
  			this.Tail08_1.rotateAngleX = 0.52F + cosf2[7] * 0.08F;
  			this.Tail08_1.rotateAngleY = -0.17F + cosf2[7] * 0.08F;
  			this.Tail09_1.rotateAngleX = 0.52F + cosf2[8] * 0.15F;
  			this.Tail09_1.rotateAngleY = -0.17F + cosf2[8] * 0.15F;
  		break;
  		case ID.ModelState.EQUIP01a:
  			this.TailBase.offsetY = -0.54F;
  			this.TailBase.offsetZ = 0.86F;
  			this.Tail01.rotateAngleX = -0.17F + cosf2[0] * 0.03F;
  			this.Tail01.rotateAngleY = 1.3F + cosf2[0] * 0.03F;
  			this.Tail02.rotateAngleX = 0.26F + cosf2[1] * 0.03F;
  			this.Tail02.rotateAngleY = -0.52F + cosf2[1] * 0.03F;
  			this.Tail03.rotateAngleX = 0.35F + cosf2[2] * 0.03F;
  			this.Tail03.rotateAngleY = -0.52F + cosf2[2] * 0.03F;
  			this.Tail04.rotateAngleX = 0.52F + cosf2[3] * 0.03F;
  			this.Tail04.rotateAngleY = -0.44F + cosf2[3] * 0.03F;
  			this.Tail05.rotateAngleX = 0.52F + cosf2[4] * 0.04F;
  			this.Tail05.rotateAngleY = -0.17F + cosf2[4] * 0.04F;
  			this.Tail06.rotateAngleX = 0.35F + cosf2[5] * 0.05F;
  			this.Tail06.rotateAngleY = 0.35F + cosf2[5] * 0.05F;
  			this.Tail07.rotateAngleX = 0.44F + cosf2[6] * 0.06F;
  			this.Tail07.rotateAngleY = 0.17F + cosf2[6] * 0.06F;
  			this.Tail08.rotateAngleX = 0.52F + cosf2[7] * 0.08F;
  			this.Tail08.rotateAngleY = 0.17F + cosf2[7] * 0.08F;
  			this.Tail09.rotateAngleX = 0.52F + cosf2[8] * 0.15F;
  			this.Tail09.rotateAngleY = 0.17F + cosf2[8] * 0.15F;
  			this.Tail01_1.rotateAngleX = -0.17F + cosf2[0] * 0.03F;
  			this.Tail01_1.rotateAngleY = -1.3F + cosf2[0] * 0.03F;
  			this.Tail02_1.rotateAngleX = 0.26F + cosf2[1] * 0.03F;
  			this.Tail02_1.rotateAngleY = 0.52F + cosf2[1] * 0.03F;
  			this.Tail03_1.rotateAngleX = 0.35F + cosf2[2] * 0.03F;
  			this.Tail03_1.rotateAngleY = 0.52F + cosf2[2] * 0.03F;
  			this.Tail04_1.rotateAngleX = 0.52F + cosf2[3] * 0.03F;
  			this.Tail04_1.rotateAngleY = 0.44F + cosf2[3] * 0.03F;
  			this.Tail05_1.rotateAngleX = 0.52F + cosf2[4] * 0.04F;
  			this.Tail05_1.rotateAngleY = 0.17F + cosf2[4] * 0.04F;
  			this.Tail06_1.rotateAngleX = 0.35F + cosf2[5] * 0.05F;
  			this.Tail06_1.rotateAngleY = -0.35F + cosf2[5] * 0.05F;
  			this.Tail07_1.rotateAngleX = 0.44F + cosf2[6] * 0.06F;
  			this.Tail07_1.rotateAngleY = -0.17F + cosf2[6] * 0.06F;
  			this.Tail08_1.rotateAngleX = 0.52F + cosf2[7] * 0.08F;
  			this.Tail08_1.rotateAngleY = -0.17F + cosf2[7] * 0.08F;
  			this.Tail09_1.rotateAngleX = 0.52F + cosf2[8] * 0.15F;
  			this.Tail09_1.rotateAngleY = -0.17F + cosf2[8] * 0.15F;
  		break;
  		default:  //normal
  			this.TailBase.offsetY = -0.15F;
  			this.TailBase.offsetZ = 0F;
  			this.Tail01.rotateAngleX = 0.26F;
  			this.Tail01.rotateAngleY = 1.7F + cosf2[0] * 0.015F;
  			this.Tail02.rotateAngleX = 0.61F;
  			this.Tail02.rotateAngleY = -0.09F + cosf2[1] * 0.02F;
  			this.Tail03.rotateAngleX = 0.61F;
  			this.Tail03.rotateAngleY = -0.09F + cosf2[2] * 0.025F;
  			this.Tail04.rotateAngleX = 0.52F;
  			this.Tail04.rotateAngleY = 0F + cosf2[3] * 0.03F;
  			this.Tail05.rotateAngleX = 0.52F;
  			this.Tail05.rotateAngleY = 0F + cosf2[4] * 0.04F;
  			this.Tail06.rotateAngleX = 0.35F;
  			this.Tail06.rotateAngleY = 0F + cosf2[5] * 0.05F;
  			this.Tail07.rotateAngleX = 0.17F;
  			this.Tail07.rotateAngleY = 0.1F + cosf2[6] * 0.06F;
  			this.Tail08.rotateAngleX = 0.09F;
  			this.Tail08.rotateAngleY = 0.1F + cosf2[7] * 0.08F;
  			this.Tail09.rotateAngleX = -0.09F;
  			this.Tail09.rotateAngleY = 0.5F + cosf2[8] * 0.15F;
  			this.Tail01_1.rotateAngleX = 0.7F;
  			this.Tail01_1.rotateAngleY = -1.57F + cosf2[0] * 0.02F;
  			this.Tail02_1.rotateAngleX = 0.35F;
  			this.Tail02_1.rotateAngleY = 0.26F + cosf2[1] * 0.03F;
  			this.Tail03_1.rotateAngleX = 0.44F;
  			this.Tail03_1.rotateAngleY = 0.35F + cosf2[2] * 0.04F;
  			this.Tail04_1.rotateAngleX = 0.35F;
  			this.Tail04_1.rotateAngleY = 0.44F + cosf2[3] * 0.05F;
  			this.Tail05_1.rotateAngleX = 0.52F;
  			this.Tail05_1.rotateAngleY = 0.35F + cosf2[4] * 0.06F;
  			this.Tail06_1.rotateAngleX = 0.09F;
  			this.Tail06_1.rotateAngleY = 0.26F + cosf2[5] * 0.07F;
  			this.Tail07_1.rotateAngleX = -0.35F;
  			this.Tail07_1.rotateAngleY = 0.35F + cosf2[6] * 0.08F;
  			this.Tail08_1.rotateAngleX = -0.52F;
  			this.Tail08_1.rotateAngleY = 0.35F + cosf2[7] * 0.09F;
  			this.Tail09_1.rotateAngleX = -0.09F;
  			this.Tail09_1.rotateAngleY = 0.44F + cosf2[8] * 0.12F;
  		break;
  		}
		
		//ear
		float modf2 = f2 % 128F;
		if (modf2 < 6F)
		{
			//total 10 ticks, loop twice in 20 ticks
			if(modf2 >= 3F) modf2 -= 3F;
			float anglef2 = MathHelper.sin(modf2 * 1.0472F) * 0.25F;
			this.Ear01.rotateAngleZ = anglef2 + 0.1745F;
			this.Ear02.rotateAngleZ = -anglef2 - 0.1745F;
		}
		else
		{
			this.Ear01.rotateAngleZ = 0.1745F;
			this.Ear02.rotateAngleZ = -0.1745F;
		}
		
		//奔跑動作
	    if (ent.getIsSprinting() || f1 > 0.8F)
	    {
	    	//leg
	    	addk1 *= 2F;
	    	addk2 *= 2F;
	    	this.ArmRight01.rotateAngleX = addk1;
	    	this.ArmLeft01.rotateAngleX = addk2;
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    //潛行, 蹲下動作
	    if (ent.getIsSneaking())
	    {
	    	//head
	    	this.Head.offsetY = 0.2F;
	    	this.GlowHead.offsetY = 0.2F;
  		}//end if sneaking
  		
	    //坐下, 騎乘動作
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
	    		//equip: hat position
	      		switch (ent.getStateEmotion(ID.S.State))
	      		{
	      		case ID.ModelState.EQUIP00:
	      			this.HatBase.rotateAngleX = 1.37F;
	      			this.HatBase.offsetY = -0.45F;
	      			this.HatBase.offsetZ = -0.2F;
	      		break;
	      		case ID.ModelState.EQUIP01:
	      			this.HatBase.rotateAngleX = -0.85F;
	      			this.HatBase.offsetY = 0.1F;
	      			this.HatBase.offsetZ = 0.07F;
	      		break;
	      		default:  //normal
	      			this.HatBase.rotateAngleX = 0F;
	      			this.HatBase.offsetY = 0F;
	      			this.HatBase.offsetZ = 0F;
	      		break;
	      		}
	      		
	    		GlStateManager.translate(0F, 0.21F, 0F);
		    	//body
	    		this.Head.rotateAngleX -= 0.2F;
	    		this.Head.rotateAngleZ -= 0.09F;
	    		this.BodyMain.rotateAngleZ = 0.09F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -1.31F;
		    	this.ArmLeft01.rotateAngleY = 0.17F;
		    	this.ArmLeft01.rotateAngleZ = 0F;
		    	this.ArmLeft01.offsetZ = 0F;
		    	this.ArmLeft02.rotateAngleZ = 0F;
		    	this.ArmRight01.rotateAngleX = -1.22F;
		    	this.ArmRight01.rotateAngleY = 1.05F;
		    	this.ArmRight01.rotateAngleZ = 0F;
		    	this.ArmRight01.offsetZ = 0F;
		    	this.ArmRight02.rotateAngleZ = 0F;
		    	//leg
		    	addk1 = 1.31F;
		    	addk2 = 1.22F;
		    	this.LegLeft01.rotateAngleY = -0.7F;
		    	this.LegLeft01.rotateAngleZ = 0F;
		    	this.LegRight01.rotateAngleY = -0.87F;
		    	this.LegRight01.rotateAngleZ = 0F;
	    	}
	    	else if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED)
	    	{
	    		GlStateManager.translate(0F, 0.22F, 0F);
		    	//head
		    	this.Head.rotateAngleX = 1.5359F;
		    	this.Head.offsetY = 0.25F;
		    	this.GlowHead.rotateAngleX = 1.5359F;
		    	this.GlowHead.offsetY = 0.25F;
		    	//arm
		    	addk1 = 1.5359F;
		    	addk2 = 1.5359F;
		    	this.ArmLeft01.rotateAngleX = -1.5359F;
		    	this.ArmLeft01.rotateAngleZ = 0F;
		    	this.ArmLeft01.offsetZ = -0.18F;
		    	this.ArmRight01.rotateAngleX = -1.5359F;
		    	this.ArmRight01.rotateAngleZ = 0F;
		    	this.ArmRight01.offsetZ = -0.18F;
	    	}
	    	else
	    	{
	    		//equip: hat position
	      		switch (ent.getStateEmotion(ID.S.State))
	      		{
	      		case ID.ModelState.EQUIP00:
	      			this.HatBase.rotateAngleX = 1.37F;
	      			this.HatBase.offsetY = -0.45F;
	      			this.HatBase.offsetZ = -0.2F;
	      		break;
	      		case ID.ModelState.EQUIP01:
	      			this.HatBase.rotateAngleX = -0.85F;
	      			this.HatBase.offsetY = 0F;
	      			this.HatBase.offsetZ = 0.07F;
	      		break;
	      		default:  //normal
	      			this.HatBase.rotateAngleX = 0F;
	      			this.HatBase.offsetY = 0F;
	      			this.HatBase.offsetZ = 0F;
	      		break;
	      		}
	      		
	    		GlStateManager.translate(0F, 0.22F, 0F);
		    	//head
		    	this.Head.rotateAngleX -= 0.5F;
		    	this.GlowHead.rotateAngleX -= 0.5F;
		    	this.Head.offsetY = 0.25F;
		    	this.GlowHead.offsetY = 0.25F;
		    	//arm
		    	addk1 = 1.5359F;
		    	addk2 = 1.5359F;
		    	this.ArmLeft01.rotateAngleX = -1.5359F;
		    	this.ArmLeft01.rotateAngleZ = 0F;
		    	this.ArmLeft01.offsetZ = -0.18F;
		    	this.ArmLeft02.rotateAngleZ = 1.1868F;
		    	this.ArmRight01.rotateAngleX = -1.5359F;
		    	this.ArmRight01.rotateAngleZ = 0F;
		    	this.ArmRight01.offsetZ = -0.18F;
		    	this.ArmRight02.rotateAngleZ = -1.1868F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if (ent.getAttackTick() > 30)
	    {
			//tail head
			this.TailHead01.rotateAngleX = -0.6F;
			this.TailJaw01.rotateAngleX = 0.5F;
			this.TailHead01_1.rotateAngleX = -0.6F;
			this.TailJaw01_1.rotateAngleX = 0.5F;
			this.TailC01.rotateAngleX = -0.1F;
			this.TailC02.rotateAngleX = -0.1F;
			this.TailC01_1.rotateAngleX = -0.1F;
			this.TailC02_1.rotateAngleX = -0.1F;
			//tail body
  			this.Tail01.rotateAngleX = 0.2F;
  			this.Tail01.rotateAngleY = 1.2F;
  			this.Tail02.rotateAngleX = 0.4F;
  			this.Tail02.rotateAngleY = -0.5F;
  			this.Tail03.rotateAngleX = 0.4F;
  			this.Tail03.rotateAngleY = -0.32F;
  			this.Tail04.rotateAngleX = 0.4F;
  			this.Tail04.rotateAngleY = 0.4F;
  			this.Tail05.rotateAngleX = 0.2F;
  			this.Tail05.rotateAngleY = 0.4F;
  			this.Tail06.rotateAngleX = 0.3F;
  			this.Tail06.rotateAngleY = 0.4F;
  			this.Tail07.rotateAngleX = 0.2F;
  			this.Tail07.rotateAngleY = 0.4F;
  			this.Tail08.rotateAngleX = 0.1F;
  			this.Tail08.rotateAngleY = 0.3F;
  			this.Tail09.rotateAngleX = 0.1F;
  			this.Tail09.rotateAngleY = 0.3F;
  			this.Tail01_1.rotateAngleX = -0.17F;
  			this.Tail01_1.rotateAngleY = -1.5F;
  			this.Tail02_1.rotateAngleX = 0.26F;
  			this.Tail02_1.rotateAngleY = 0.52F;
  			this.Tail03_1.rotateAngleX = 0.35F;
  			this.Tail03_1.rotateAngleY = 0.52F;
  			this.Tail04_1.rotateAngleX = 0.52F;
  			this.Tail04_1.rotateAngleY = 0.3F;
  			this.Tail05_1.rotateAngleX = 0.52F;
  			this.Tail05_1.rotateAngleY = 0.17F;
  			this.Tail06_1.rotateAngleX = 0.35F;
  			this.Tail06_1.rotateAngleY = -0.35F;
  			this.Tail07_1.rotateAngleX = 0.2F;
  			this.Tail07_1.rotateAngleY = -0.17F;
  			this.Tail08_1.rotateAngleX = 0.3F;
  			this.Tail08_1.rotateAngleY = -0.17F;
  			this.Tail09_1.rotateAngleX = 0.5F;
  			this.Tail09_1.rotateAngleY = -0.17F;
  			
  			float ptick = ent.getAttackTick() + (1 - f2 + (int)f2);
			if (ent.getAttackTick() > 47)
			{
				this.TailHead01.rotateAngleX = (ptick - 50) * 0.3F - 0.1F;
				this.TailJaw01.rotateAngleX = (50 - ptick) * 0.3F + 0.1F;
			}
			else if (ent.getAttackTick() > 39)
			{
				this.TailHead01.rotateAngleX = -0.7F + (47 - ptick) * 0.06F;
				this.TailJaw01.rotateAngleX = 0.7F - (47 - ptick) * 0.06F;
			}
			else
			{
				this.TailHead01.rotateAngleX = -0.25F;
				this.TailJaw01.rotateAngleX = 0.25F;
			}
			
			this.TailHead01_1.rotateAngleX = this.TailHead01.rotateAngleX;
			this.TailJaw01_1.rotateAngleX = this.TailJaw01.rotateAngleX;
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.6F - f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY = 0F - f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
	        this.ArmRight01.rotateAngleZ = 0.2F - -f8 * 20.0F * Values.N.DIV_PI_180;
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
				this.setMouth(1);
			}
		}
		else
		{
			this.setFace(7);
			this.setMouth(2);
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
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
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
				this.setMouth(1);
			}
		}
		else if (t < 410)
		{
			this.setFace(3);
			
			if (t < 360)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(4);
			}
		}
		else
		{
			this.setFace(8);
			
			if (t < 470)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
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
				this.setMouth(2);
			}
			else
			{
				this.setMouth(1);
			}
		}
		else if (t < 400)
		{
			this.setFace(3);
			
			if (t < 250)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(3);
			}
		}
		else
		{
			this.setFace(9);
			
			if (t < 450)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
			}
		}
	}
	
	@Override
	public void setFaceScorn(IShipEmotion ent)
	{
		this.setFace(2);
		this.setMouth(1);
	}

	@Override
	public void setFaceHungry(IShipEmotion ent)
	{
		this.setFace(4);	
		this.setMouth(2);
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
				this.setMouth(1);
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
			this.setFace(8);

			if (t < 250)
			{
				this.setMouth(0);
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
				this.setMouth(0);
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
				this.setMouth(3);
			}
			else
			{
				this.setMouth(4);
			}
		}
		else
		{
			this.setFace(8);
			this.setMouth(0);
		}
	}
	
	
}