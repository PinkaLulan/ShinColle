package com.lulan.shincolle.client.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerIkazuchi;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;

/**
 * ModelDestroyerHibiki - PinkaLulan
 * Created using Tabula 4.1.1  2016/5/14
 */
public class ModelDestroyerHibiki extends ModelBase implements IModelEmotionAdv
{
    public ModelRenderer BodyMain;
    public ModelRenderer Butt;
    public ModelRenderer Head;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Cloth01;
    public ModelRenderer EquipBase;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight02;
    public ModelRenderer LegRight03;
    public ModelRenderer LegLeft02;
    public ModelRenderer LegLeft03;
    public ModelRenderer Skirt02;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Ahoke;
    public ModelRenderer HairU01;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer HatBase;
    public ModelRenderer Hair02f1;
    public ModelRenderer Hair02a1;
    public ModelRenderer Hair02b1;
    public ModelRenderer Hair02c1;
    public ModelRenderer Hair02d1;
    public ModelRenderer Hair02e1;
    public ModelRenderer Hair02a2;
    public ModelRenderer Hair02b2;
    public ModelRenderer Hair02c2;
    public ModelRenderer Hair02d2;
    public ModelRenderer Hair02e2;
    public ModelRenderer Hat01a;
    public ModelRenderer Hat01b;
    public ModelRenderer Hat01c;
    public ModelRenderer Hat01d;
    public ModelRenderer Hat02a;
    public ModelRenderer Hat03a;
    public ModelRenderer Hat03b;
    public ModelRenderer Hat03c;
    public ModelRenderer Hat03d;
    public ModelRenderer Hat02b;
    public ModelRenderer HatBase2;
    public ModelRenderer Hat201_01;
    public ModelRenderer Hat201_02;
    public ModelRenderer Hat201_03;
    public ModelRenderer Hat201_04;
    public ModelRenderer Hat201_05;
    public ModelRenderer Hat201_06;
    public ModelRenderer Hat201_07;
    public ModelRenderer Hat201_08;
    public ModelRenderer Hat201_09;
    public ModelRenderer Hat201_10;
    public ModelRenderer Hat201_11;
    public ModelRenderer Hat201_12;
    public ModelRenderer Hat202a;
    public ModelRenderer Hat202b;
    public ModelRenderer Hair02f2;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft03;
    public ModelRenderer EquipTL03;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight03;
    public ModelRenderer Cloth02;
    public ModelRenderer EquipMain01;
    public ModelRenderer EquipC01;
    public ModelRenderer EquipMain02;
    public ModelRenderer EquipMain03;
    public ModelRenderer EquipMain04;
    public ModelRenderer EquipTL02;
    public ModelRenderer EquipTR02;
    public ModelRenderer EquipHead01;
    public ModelRenderer EquipHead02;
    public ModelRenderer EquipHead03;
    public ModelRenderer EquipHead04;
    public ModelRenderer EquipHead05;
    public ModelRenderer EquipTL02a;
    public ModelRenderer EquipTL02b;
    public ModelRenderer EquipTL02c;
    public ModelRenderer EquipTL02d;
    public ModelRenderer EquipTL02e;
    public ModelRenderer EquipTL02f;
    public ModelRenderer EquipTR02a;
    public ModelRenderer EquipTR02b;
    public ModelRenderer EquipTR02c;
    public ModelRenderer EquipTR02d;
    public ModelRenderer EquipTR02e;
    public ModelRenderer EquipTR02f;
    public ModelRenderer EquipC02;
    public ModelRenderer EquipC03;
    public ModelRenderer EquipC04a;
    public ModelRenderer EquipC05a;
    public ModelRenderer EquipC04b;
    public ModelRenderer EquipC05b;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;

    private Random rand = new Random();
    private int startEmo2 = 0;
    
    public ModelDestroyerHibiki()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.EquipTR02f = new ModelRenderer(this, 0, 0);
        this.EquipTR02f.setRotationPoint(-1.3F, 2.3F, 2.5F);
        this.EquipTR02f.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 59);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.4F, 5.5F, 3.2F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.13962634015954636F, 0.0F, 0.10471975511965977F);
        this.Cloth01 = new ModelRenderer(this, 84, 31);
        this.Cloth01.setRotationPoint(0.0F, -11.6F, 0.0F);
        this.Cloth01.addBox(-7.0F, 0.0F, -4.4F, 14, 7, 8, 0.0F);
        this.EquipTR02a = new ModelRenderer(this, 0, 0);
        this.EquipTR02a.setRotationPoint(-1.3F, 0.0F, -19.8F);
        this.EquipTR02a.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.EquipMain04 = new ModelRenderer(this, 0, 26);
        this.EquipMain04.setRotationPoint(0.0F, -16.5F, 9.0F);
        this.EquipMain04.addBox(-3.0F, 0.0F, -3.0F, 6, 16, 6, 0.0F);
        this.setRotateAngle(EquipMain04, -0.08726646259971647F, 0.0F, 0.0F);
        this.Cloth02 = new ModelRenderer(this, 24, 73);
        this.Cloth02.setRotationPoint(0.0F, 4.8F, -4.3F);
        this.Cloth02.addBox(-3.0F, 0.0F, 0.0F, 6, 10, 0, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 88);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.5F, 10.0F, 3.0F);
        this.ArmLeft02.addBox(-6.0F, 0.0F, -6.0F, 6, 8, 6, 0.0F);
        this.EquipTL02d = new ModelRenderer(this, 0, 0);
        this.EquipTL02d.setRotationPoint(1.3F, -2.3F, 3.0F);
        this.EquipTL02d.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.Hair02b1 = new ModelRenderer(this, 24, 26);
        this.Hair02b1.setRotationPoint(-4.0F, 7.0F, -2.4F);
        this.Hair02b1.addBox(-2.0F, 0.0F, 0.0F, 4, 7, 0, 0.0F);
        this.setRotateAngle(Hair02b1, 0.2617993877991494F, -0.17453292519943295F, 0.2617993877991494F);
        this.Hat03c = new ModelRenderer(this, 23, 43);
        this.Hat03c.setRotationPoint(-0.5F, 2.0F, 0.0F);
        this.Hat03c.addBox(0.0F, -4.0F, 0.0F, 5, 3, 5, 0.0F);
        this.setRotateAngle(Hat03c, -0.03490658503988659F, 0.0F, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face2.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.EquipHead04 = new ModelRenderer(this, 0, 0);
        this.EquipHead04.setRotationPoint(0.0F, -4.8F, 2.5F);
        this.EquipHead04.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(EquipHead04, 0.2617993877991494F, 0.0F, 0.0F);
        this.EquipMain01 = new ModelRenderer(this, 0, 0);
        this.EquipMain01.setRotationPoint(0.0F, -4.0F, 5.0F);
        this.EquipMain01.addBox(-5.5F, -1.0F, 0.0F, 11, 9, 12, 0.0F);
        this.EquipTR02e = new ModelRenderer(this, 0, 0);
        this.EquipTR02e.setRotationPoint(-1.3F, 0.0F, 2.2F);
        this.EquipTR02e.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.EquipHead01 = new ModelRenderer(this, 0, 0);
        this.EquipHead01.setRotationPoint(0.0F, 6.5F, -0.5F);
        this.EquipHead01.addBox(0.0F, -1.5F, -12.0F, 2, 3, 18, 0.0F);
        this.setRotateAngle(EquipHead01, 3.141592653589793F, -1.8325957145940461F, -1.5707963267948966F);
        this.EquipTL02b = new ModelRenderer(this, 0, 0);
        this.EquipTL02b.setRotationPoint(1.3F, -2.3F, -18.8F);
        this.EquipTL02b.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 45);
        this.HairU01.setRotationPoint(0.0F, -6.2F, -7.1F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.Hat01a = new ModelRenderer(this, 46, 0);
        this.Hat01a.setRotationPoint(-0.7F, 0.0F, 0.0F);
        this.Hat01a.addBox(0.0F, 0.0F, -6.0F, 6, 2, 6, 0.0F);
        this.Hair02a1 = new ModelRenderer(this, 24, 26);
        this.Hair02a1.setRotationPoint(0.0F, 10.0F, -2.2F);
        this.Hair02a1.addBox(-2.0F, 0.0F, 0.0F, 4, 7, 0, 0.0F);
        this.setRotateAngle(Hair02a1, 0.2617993877991494F, 0.0F, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face3.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.EquipHead05 = new ModelRenderer(this, 0, 0);
        this.EquipHead05.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipHead05.addBox(-1.0F, -5.0F, 0.0F, 2, 10, 2, 0.0F);
        this.EquipMain03 = new ModelRenderer(this, 63, 13);
        this.EquipMain03.setRotationPoint(0.0F, 9.5F, 9.0F);
        this.EquipMain03.addBox(-1.0F, 0.0F, -1.5F, 2, 6, 3, 0.0F);
        this.setRotateAngle(EquipMain03, 0.5009094953223726F, 0.0F, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 76, 0);
        this.Skirt02.setRotationPoint(0.0F, 3.5F, -0.4F);
        this.Skirt02.addBox(-8.0F, 0.0F, 0.0F, 16, 6, 10, 0.0F);
        this.setRotateAngle(Skirt02, -0.05235987755982988F, 0.0F, 0.0F);
        this.EquipTR02d = new ModelRenderer(this, 0, 0);
        this.EquipTR02d.setRotationPoint(-1.3F, -2.3F, 3.0F);
        this.EquipTR02d.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.Hat01b = new ModelRenderer(this, 46, 0);
        this.Hat01b.setRotationPoint(-0.7F, 0.0F, 0.0F);
        this.Hat01b.addBox(0.0F, 0.0F, 0.0F, 6, 2, 6, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 72);
        this.LegRight02.setRotationPoint(3.0F, 12.0F, -3.0F);
        this.LegRight02.addBox(-6.0F, 0.0F, 0.0F, 6, 10, 6, 0.0F);
        this.EquipC03 = new ModelRenderer(this, 0, 0);
        this.EquipC03.setRotationPoint(0.0F, -5.0F, -2.0F);
        this.EquipC03.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 5, 0.0F);
        this.Hat01c = new ModelRenderer(this, 46, 0);
        this.Hat01c.setRotationPoint(0.7F, 0.0F, 0.0F);
        this.Hat01c.addBox(-6.0F, 0.0F, 0.0F, 6, 2, 6, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 88);
        this.ArmRight02.setRotationPoint(-3.5F, 10.0F, 3.0F);
        this.ArmRight02.addBox(0.0F, 0.0F, -6.0F, 6, 8, 6, 0.0F);
        this.EquipC05a = new ModelRenderer(this, 0, 0);
        this.EquipC05a.setRotationPoint(1.5F, -3.0F, 0.0F);
        this.EquipC05a.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 6, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face1.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.EquipHead03 = new ModelRenderer(this, 0, 0);
        this.EquipHead03.setRotationPoint(0.0F, 4.8F, 2.5F);
        this.EquipHead03.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(EquipHead03, -0.2617993877991494F, 0.0F, 0.0F);
        this.Hair02f2 = new ModelRenderer(this, 26, 68);
        this.Hair02f2.mirror = true;
        this.Hair02f2.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.Hair02f2.addBox(-1.5F, 0.0F, 0.0F, 3, 4, 0, 0.0F);
        this.setRotateAngle(Hair02f2, 0.6283185307179586F, 0.0F, 0.0F);
        this.ArmLeft03 = new ModelRenderer(this, 36, 102);
        this.ArmLeft03.mirror = true;
        this.ArmLeft03.setRotationPoint(-3.0F, 6.0F, -3.0F);
        this.ArmLeft03.addBox(-2.5F, 0.0F, -2.5F, 5, 5, 5, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 72);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(-3.0F, 12.0F, -3.0F);
        this.LegLeft02.addBox(0.0F, 0.0F, 0.0F, 6, 10, 6, 0.0F);
        this.Butt = new ModelRenderer(this, 54, 66);
        this.Butt.setRotationPoint(0.0F, 3.0F, -4.0F);
        this.Butt.addBox(-7.0F, 0.0F, 0.0F, 14, 7, 8, 0.0F);
        this.setRotateAngle(Butt, 0.20943951023931953F, 0.0F, 0.0F);
        this.EquipTL02e = new ModelRenderer(this, 0, 0);
        this.EquipTL02e.setRotationPoint(1.3F, 0.0F, 2.2F);
        this.EquipTL02e.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 0, 88);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.3F, -9.4F, -0.7F);
        this.ArmLeft01.addBox(-2.5F, -1.0F, -3.0F, 6, 11, 6, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.17453292519943295F, 0.0F, -0.3141592653589793F);
        this.Hair02b2 = new ModelRenderer(this, 24, 66);
        this.Hair02b2.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Hair02b2.addBox(-2.0F, 0.0F, 0.0F, 4, 7, 0, 0.0F);
        this.setRotateAngle(Hair02b2, -0.4363323129985824F, 0.0F, 0.0F);
        this.Hair02e1 = new ModelRenderer(this, 24, 22);
        this.Hair02e1.setRotationPoint(7.4F, -4.0F, -5.5F);
        this.Hair02e1.addBox(0.0F, 0.0F, -2.0F, 0, 7, 4, 0.0F);
        this.setRotateAngle(Hair02e1, 0.05235987755982988F, 0.0F, -0.6108652381980153F);
        this.BodyMain = new ModelRenderer(this, 0, 105);
        this.BodyMain.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 14, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipC04a = new ModelRenderer(this, 0, 0);
        this.EquipC04a.setRotationPoint(-1.5F, -3.0F, 0.0F);
        this.EquipC04a.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 6, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 80, 16);
        this.Skirt01.setRotationPoint(0.0F, 1.7F, -0.4F);
        this.Skirt01.addBox(-7.5F, 0.0F, 0.0F, 15, 6, 9, 0.0F);
        this.setRotateAngle(Skirt01, -0.05235987755982988F, 0.0F, 0.0F);
        this.Hat03d = new ModelRenderer(this, 23, 43);
        this.Hat03d.setRotationPoint(0.5F, 2.0F, 0.0F);
        this.Hat03d.addBox(-5.0F, -4.0F, 0.0F, 5, 3, 5, 0.0F);
        this.setRotateAngle(Hat03d, -0.03490658503988659F, 0.0F, 0.0F);
        this.HairR01 = new ModelRenderer(this, 89, 102);
        this.HairR01.setRotationPoint(-8.0F, 2.6F, -4.7F);
        this.HairR01.addBox(-0.5F, 0.0F, 0.0F, 1, 9, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.2617993877991494F, 0.08726646259971647F, -0.08726646259971647F);
        this.EquipTL02a = new ModelRenderer(this, 0, 0);
        this.EquipTL02a.setRotationPoint(1.3F, 0.0F, -19.8F);
        this.EquipTL02a.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.Hat02b = new ModelRenderer(this, 0, 24);
        this.Hat02b.setRotationPoint(4.8F, -1.7F, -2.0F);
        this.Hat02b.addBox(0.0F, 0.0F, 0.0F, 0, 2, 2, 0.0F);
        this.setRotateAngle(Hat02b, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipTR02 = new ModelRenderer(this, 0, 0);
        this.EquipTR02.setRotationPoint(-5.5F, 6.0F, 4.5F);
        this.EquipTR02.addBox(-3.0F, -4.0F, -9.0F, 3, 9, 12, 0.0F);
        this.setRotateAngle(EquipTR02, 0.13962634015954636F, 0.06981317007977318F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 0, 37);
        this.Ahoke.setRotationPoint(-4.5F, -7.4F, -7.0F);
        this.Ahoke.addBox(0.0F, 0.0F, -11.0F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, -0.5235987755982988F, 1.2217304763960306F, 0.0F);
        this.EquipTL03 = new ModelRenderer(this, 36, 45);
        this.EquipTL03.setRotationPoint(0.5F, 4.0F, -3.0F);
        this.EquipTL03.addBox(0.0F, -12.0F, -3.5F, 1, 24, 7, 0.0F);
        this.setRotateAngle(EquipTL03, -0.13962634015954636F, -0.10471975511965977F, -0.05235987755982988F);
        this.LegLeft03 = new ModelRenderer(this, 30, 76);
        this.LegLeft03.setRotationPoint(3.0F, 8.0F, 2.9F);
        this.LegLeft03.addBox(-3.5F, 0.0F, -3.5F, 7, 5, 7, 0.0F);
        this.Hair02d2 = new ModelRenderer(this, 28, 62);
        this.Hair02d2.mirror = true;
        this.Hair02d2.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Hair02d2.addBox(0.0F, 0.0F, -2.0F, 0, 7, 4, 0.0F);
        this.setRotateAngle(Hair02d2, 0.0F, 0.0F, -0.5235987755982988F);
        this.EquipMain02 = new ModelRenderer(this, 52, 8);
        this.EquipMain02.setRotationPoint(0.0F, 6.9F, 1.2F);
        this.EquipMain02.addBox(-4.0F, 0.0F, 0.0F, 8, 7, 8, 0.0F);
        this.setRotateAngle(EquipMain02, 0.6283185307179586F, 0.0F, 0.0F);
        this.HatBase = new ModelRenderer(this, 0, 0);
        this.HatBase.setRotationPoint(-1.0F, -2.4F, 1.5F);
        this.HatBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(HatBase, -0.10471975511965977F, 0.7853981633974483F, 0.0F);
        this.Hat01d = new ModelRenderer(this, 46, 0);
        this.Hat01d.setRotationPoint(0.7F, 0.0F, 0.0F);
        this.Hat01d.addBox(-6.0F, 0.0F, -6.0F, 6, 2, 6, 0.0F);
        this.EquipC02 = new ModelRenderer(this, 0, 0);
        this.EquipC02.setRotationPoint(-2.0F, 0.5F, 0.0F);
        this.EquipC02.addBox(-3.5F, -3.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(EquipC02, -0.17453292519943295F, 0.6283185307179586F, 0.0F);
        this.Hair02d1 = new ModelRenderer(this, 28, 22);
        this.Hair02d1.setRotationPoint(-7.4F, 0.0F, -5.5F);
        this.Hair02d1.addBox(0.0F, 0.0F, -2.0F, 0, 7, 4, 0.0F);
        this.setRotateAngle(Hair02d1, 0.2617993877991494F, 0.0F, 0.3490658503988659F);
        this.EquipTL02 = new ModelRenderer(this, 0, 0);
        this.EquipTL02.setRotationPoint(5.5F, 6.0F, 4.5F);
        this.EquipTL02.addBox(0.0F, -4.0F, -9.0F, 3, 9, 12, 0.0F);
        this.setRotateAngle(EquipTL02, 0.13962634015954636F, -0.06981317007977318F, 0.0F);
        this.Hair01 = new ModelRenderer(this, 38, 23);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 12.0F);
        this.Hair01.addBox(-7.5F, 0.0F, -10.0F, 15, 12, 8, 0.0F);
        this.setRotateAngle(Hair01, 0.2617993877991494F, 0.0F, 0.0F);
        this.Hair02e2 = new ModelRenderer(this, 24, 62);
        this.Hair02e2.mirror = true;
        this.Hair02e2.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Hair02e2.addBox(0.0F, 0.0F, -2.0F, 0, 7, 4, 0.0F);
        this.setRotateAngle(Hair02e2, 0.0F, 0.0F, 0.8726646259971648F);
        this.Hat03a = new ModelRenderer(this, 23, 43);
        this.Hat03a.mirror = true;
        this.Hat03a.setRotationPoint(-0.3F, 2.0F, 0.0F);
        this.Hat03a.addBox(0.0F, -4.0F, -5.0F, 5, 3, 5, 0.0F);
        this.setRotateAngle(Hat03a, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipC01 = new ModelRenderer(this, 0, 0);
        this.EquipC01.setRotationPoint(-7.0F, -11.0F, 9.0F);
        this.EquipC01.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.EquipTR02b = new ModelRenderer(this, 0, 0);
        this.EquipTR02b.setRotationPoint(-1.3F, -2.3F, -18.8F);
        this.EquipTR02b.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F);
        this.Hair02c1 = new ModelRenderer(this, 24, 26);
        this.Hair02c1.setRotationPoint(4.2F, 6.0F, -2.4F);
        this.Hair02c1.addBox(-2.0F, 0.0F, 0.0F, 4, 7, 0, 0.0F);
        this.setRotateAngle(Hair02c1, 0.17453292519943295F, 0.17453292519943295F, -0.40142572795869574F);
        this.Hair02c2 = new ModelRenderer(this, 24, 66);
        this.Hair02c2.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Hair02c2.addBox(-2.0F, 0.0F, 0.0F, 4, 7, 0, 0.0F);
        this.setRotateAngle(Hair02c2, -0.3490658503988659F, 0.0F, 0.0F);
        this.Hat03b = new ModelRenderer(this, 23, 43);
        this.Hat03b.setRotationPoint(0.3F, 2.0F, 0.0F);
        this.Hat03b.addBox(-5.0F, -4.0F, -5.0F, 5, 3, 5, 0.0F);
        this.setRotateAngle(Hat03b, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipTL02c = new ModelRenderer(this, 0, 0);
        this.EquipTL02c.setRotationPoint(1.3F, 2.3F, -19.5F);
        this.EquipTL02c.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.Hair02f1 = new ModelRenderer(this, 25, 26);
        this.Hair02f1.setRotationPoint(5.0F, 1.0F, 9.5F);
        this.Hair02f1.addBox(-1.5F, 0.0F, 0.0F, 3, 5, 0, 0.0F);
        this.setRotateAngle(Hair02f1, 0.7853981633974483F, 0.3490658503988659F, -0.13962634015954636F);
        this.HairL01 = new ModelRenderer(this, 89, 102);
        this.HairL01.setRotationPoint(8.0F, 2.5F, -4.4F);
        this.HairL01.addBox(-0.5F, 0.0F, 0.0F, 1, 9, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.2617993877991494F, -0.08726646259971647F, 0.08726646259971647F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -2.8F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face4.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.Hair02a2 = new ModelRenderer(this, 24, 32);
        this.Hair02a2.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Hair02a2.addBox(-2.0F, 0.0F, 0.0F, 4, 7, 0, 0.0F);
        this.setRotateAngle(Hair02a2, -0.2617993877991494F, 0.0F, 0.0F);
        this.EquipTL02f = new ModelRenderer(this, 0, 0);
        this.EquipTL02f.setRotationPoint(1.3F, 2.3F, 2.5F);
        this.EquipTL02f.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.HairR02 = new ModelRenderer(this, 88, 104);
        this.HairR02.setRotationPoint(0.1F, 8.5F, 0.0F);
        this.HairR02.addBox(-0.5F, 0.0F, 0.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(HairR02, 0.20943951023931953F, 0.0F, -0.08726646259971647F);
        this.LegRight03 = new ModelRenderer(this, 30, 76);
        this.LegRight03.mirror = true;
        this.LegRight03.setRotationPoint(-3.0F, 8.0F, 2.9F);
        this.LegRight03.addBox(-3.5F, 0.0F, -3.5F, 7, 5, 7, 0.0F);
        this.EquipHead02 = new ModelRenderer(this, 0, 0);
        this.EquipHead02.setRotationPoint(1.0F, 0.0F, -15.0F);
        this.EquipHead02.addBox(-1.5F, -7.0F, 0.0F, 3, 14, 3, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 81);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.3F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 12, 8, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -11.8F, -1.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.setRotateAngle(Head, 0.10471975511965977F, 0.0F, 0.0F);
        this.Hat02a = new ModelRenderer(this, 55, 0);
        this.Hat02a.setRotationPoint(0.0F, 2.0F, -6.0F);
        this.Hat02a.addBox(-4.5F, 0.0F, -6.0F, 9, 0, 6, 0.0F);
        this.setRotateAngle(Hat02a, 0.17453292519943295F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 59);
        this.LegRight01.setRotationPoint(-4.4F, 5.5F, 3.2F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.05235987755982988F, 0.0F, -0.10471975511965977F);
        this.EquipTR02c = new ModelRenderer(this, 0, 0);
        this.EquipTR02c.setRotationPoint(-1.3F, 2.3F, -19.5F);
        this.EquipTR02c.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.HairL02 = new ModelRenderer(this, 88, 104);
        this.HairL02.setRotationPoint(-0.1F, 8.5F, 0.0F);
        this.HairL02.addBox(-0.5F, 0.0F, 0.0F, 1, 7, 3, 0.0F);
        this.setRotateAngle(HairL02, 0.3141592653589793F, 0.0F, 0.08726646259971647F);
        this.ArmRight01 = new ModelRenderer(this, 0, 88);
        this.ArmRight01.setRotationPoint(-7.3F, -9.4F, -0.7F);
        this.ArmRight01.addBox(-3.5F, -1.0F, -3.0F, 6, 11, 6, 0.0F);
        this.setRotateAngle(ArmRight01, 0.17453292519943295F, 0.0F, 0.3141592653589793F);
        this.EquipC04b = new ModelRenderer(this, 0, 0);
        this.EquipC04b.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.EquipC04b.addBox(-0.5F, -0.5F, -10.0F, 1, 1, 10, 0.0F);
        this.EquipC05b = new ModelRenderer(this, 0, 0);
        this.EquipC05b.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.EquipC05b.addBox(-0.5F, -0.5F, -10.0F, 1, 1, 10, 0.0F);
        this.ArmRight03 = new ModelRenderer(this, 36, 102);
        this.ArmRight03.setRotationPoint(3.0F, 6.0F, -3.0F);
        this.ArmRight03.addBox(-2.5F, 0.0F, -2.5F, 5, 5, 5, 0.0F);
        this.HatBase2 = new ModelRenderer(this, 0, 0);
        this.HatBase2.setRotationPoint(0.0F, -8F, 0.0F);
        this.HatBase2.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(HatBase2, -0.2618F, 0.0F, 0.0F);
        this.Hat201_01 = new ModelRenderer(this, 98, 58);
        this.Hat201_01.mirror = true;
        this.Hat201_01.setRotationPoint(0.0F, 0.0F, -9.0F);
        this.Hat201_01.addBox(0.0F, -9.0F, 0.0F, 9, 9, 1, 0.0F);
        this.Hat201_02 = new ModelRenderer(this, 98, 58);
        this.Hat201_02.setRotationPoint(0.0F, 0.0F, -9.0F);
        this.Hat201_02.addBox(-9.0F, -9.0F, 0.0F, 9, 9, 1, 0.0F);
        this.Hat201_03 = new ModelRenderer(this, 98, 58);
        this.Hat201_03.mirror = true;
        this.Hat201_03.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.Hat201_03.addBox(0.0F, -9.0F, 0.0F, 9, 9, 1, 0.0F);
        this.Hat201_04 = new ModelRenderer(this, 98, 58);
        this.Hat201_04.setRotationPoint(0.0F, 0.0F, 8.0F);
        this.Hat201_04.addBox(-9.0F, -9.0F, 0.0F, 9, 9, 1, 0.0F);
        this.Hat201_05 = new ModelRenderer(this, 98, 58);
        this.Hat201_05.mirror = true;
        this.Hat201_05.setRotationPoint(8.0F, 0.0F, 0.0F);
        this.Hat201_05.addBox(0.0F, -9.0F, 0.0F, 9, 9, 1, 0.0F);
        this.setRotateAngle(Hat201_05, 0.0F, 1.5707963267948966F, 0.0F);
        this.Hat201_06 = new ModelRenderer(this, 98, 58);
        this.Hat201_06.setRotationPoint(8.0F, 0.0F, 9.0F);
        this.Hat201_06.addBox(0.0F, -9.0F, 0.0F, 9, 9, 1, 0.0F);
        this.setRotateAngle(Hat201_06, 0.0F, 1.5707963267948966F, 0.0F);
        this.Hat201_07 = new ModelRenderer(this, 98, 58);
        this.Hat201_07.mirror = true;
        this.Hat201_07.setRotationPoint(-9.0F, 0.0F, 0.0F);
        this.Hat201_07.addBox(0.0F, -9.0F, 0.0F, 9, 9, 1, 0.0F);
        this.setRotateAngle(Hat201_07, 0.0F, 1.5707963267948966F, 0.0F);
        this.Hat201_08 = new ModelRenderer(this, 98, 58);
        this.Hat201_08.setRotationPoint(-9.0F, 0.0F, 9.0F);
        this.Hat201_08.addBox(0.0F, -9.0F, 0.0F, 9, 9, 1, 0.0F);
        this.setRotateAngle(Hat201_08, 0.0F, 1.5707963267948966F, 0.0F);
        this.Hat201_09 = new ModelRenderer(this, 98, 58);
        this.Hat201_09.mirror = true;
        this.Hat201_09.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.Hat201_09.addBox(0.0F, 0.0F, 0.0F, 9, 9, 1, 0.0F);
        this.setRotateAngle(Hat201_09, -1.5707963267948966F, 0.0F, 0.0F);
        this.Hat201_10 = new ModelRenderer(this, 98, 58);
        this.Hat201_10.setRotationPoint(-9.0F, -9.0F, 0.0F);
        this.Hat201_10.addBox(0.0F, 0.0F, 0.0F, 9, 9, 1, 0.0F);
        this.setRotateAngle(Hat201_10, -1.5707963267948966F, 0.0F, 0.0F);
        this.Hat201_11 = new ModelRenderer(this, 98, 58);
        this.Hat201_11.mirror = true;
        this.Hat201_11.setRotationPoint(0.0F, -9.0F, 9.0F);
        this.Hat201_11.addBox(0.0F, 0.0F, 0.0F, 9, 9, 1, 0.0F);
        this.setRotateAngle(Hat201_11, -1.5707963267948966F, 0.0F, 0.0F);
        this.Hat201_12 = new ModelRenderer(this, 98, 58);
        this.Hat201_12.setRotationPoint(-9.0F, -9.0F, 9.0F);
        this.Hat201_12.addBox(0.0F, 0.0F, 0.0F, 9, 9, 1, 0.0F);
        this.setRotateAngle(Hat201_12, -1.5707963267948966F, 0.0F, 0.0F);
        this.Hat202a = new ModelRenderer(this, 98, 46);
        this.Hat202a.setRotationPoint(9.0F, -2.5F, 0.0F);
        this.Hat202a.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6, 0.0F);
        this.setRotateAngle(Hat202a, 0.0F, 0.0F, -0.08726646259971647F);
        this.Hat202b = new ModelRenderer(this, 98, 46);
        this.Hat202b.setRotationPoint(-10.0F, -2.5F, 0.0F);
        this.Hat202b.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6, 0.0F);
        this.setRotateAngle(Hat202b, 0.0F, 0.0F, 0.08726646259971647F);
        
        this.EquipTR02.addChild(this.EquipTR02f);
        this.Butt.addChild(this.LegLeft01);
        this.BodyMain.addChild(this.Cloth01);
        this.EquipTR02.addChild(this.EquipTR02a);
        this.EquipMain01.addChild(this.EquipMain04);
        this.Cloth01.addChild(this.Cloth02);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipTL02.addChild(this.EquipTL02d);
        this.Hair01.addChild(this.Hair02b1);
        this.HatBase.addChild(this.Hat03c);
        this.EquipHead02.addChild(this.EquipHead04);
        this.EquipBase.addChild(this.EquipMain01);
        this.EquipTR02.addChild(this.EquipTR02e);
        this.EquipMain03.addChild(this.EquipHead01);
        this.EquipTL02.addChild(this.EquipTL02b);
        this.Hair.addChild(this.HairU01);
        this.HatBase.addChild(this.Hat01a);
        this.Hair01.addChild(this.Hair02a1);
        this.EquipHead02.addChild(this.EquipHead05);
        this.EquipMain01.addChild(this.EquipMain03);
        this.Skirt01.addChild(this.Skirt02);
        this.EquipTR02.addChild(this.EquipTR02d);
        this.HatBase.addChild(this.Hat01b);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipC02.addChild(this.EquipC03);
        this.HatBase.addChild(this.Hat01c);
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipC02.addChild(this.EquipC05a);
        this.EquipHead02.addChild(this.EquipHead03);
        this.Hair02f1.addChild(this.Hair02f2);
        this.ArmLeft02.addChild(this.ArmLeft03);
        this.LegLeft01.addChild(this.LegLeft02);
        this.BodyMain.addChild(this.Butt);
        this.EquipTL02.addChild(this.EquipTL02e);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Hair02b1.addChild(this.Hair02b2);
        this.Hair01.addChild(this.Hair02e1);
        this.EquipC02.addChild(this.EquipC04a);
        this.Butt.addChild(this.Skirt01);
        this.HatBase.addChild(this.Hat03d);
        this.Hair.addChild(this.HairR01);
        this.EquipTL02.addChild(this.EquipTL02a);
        this.HatBase.addChild(this.Hat02b);
        this.EquipMain01.addChild(this.EquipTR02);
        this.Hair.addChild(this.Ahoke);
        this.ArmLeft02.addChild(this.EquipTL03);
        this.LegLeft02.addChild(this.LegLeft03);
        this.Hair02d1.addChild(this.Hair02d2);
        this.EquipMain01.addChild(this.EquipMain02);
        this.HairMain.addChild(this.HatBase);
        this.HatBase.addChild(this.Hat01d);
        this.EquipC01.addChild(this.EquipC02);
        this.Hair01.addChild(this.Hair02d1);
        this.EquipMain01.addChild(this.EquipTL02);
        this.HairMain.addChild(this.Hair01);
        this.Hair02e1.addChild(this.Hair02e2);
        this.HatBase.addChild(this.Hat03a);
        this.EquipBase.addChild(this.EquipC01);
        this.EquipTR02.addChild(this.EquipTR02b);
        this.Hair01.addChild(this.Hair02c1);
        this.Hair02c1.addChild(this.Hair02c2);
        this.HatBase.addChild(this.Hat03b);
        this.EquipTL02.addChild(this.EquipTL02c);
        this.HairMain.addChild(this.Hair02f1);
        this.Hair.addChild(this.HairL01);
        this.Head.addChild(this.HairMain);
        this.BodyMain.addChild(this.EquipBase);
        this.Hair02a1.addChild(this.Hair02a2);
        this.EquipTL02.addChild(this.EquipTL02f);
        this.HairR01.addChild(this.HairR02);
        this.LegRight02.addChild(this.LegRight03);
        this.EquipHead01.addChild(this.EquipHead02);
        this.Head.addChild(this.Hair);
        this.BodyMain.addChild(this.Head);
        this.HatBase.addChild(this.Hat02a);
        this.Butt.addChild(this.LegRight01);
        this.EquipTR02.addChild(this.EquipTR02c);
        this.HairL01.addChild(this.HairL02);
        this.BodyMain.addChild(this.ArmRight01);
        this.EquipC04a.addChild(this.EquipC04b);
        this.EquipC05a.addChild(this.EquipC05b);
        this.ArmRight02.addChild(this.ArmRight03);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -11.8F, -1.0F);
        
        this.GlowBodyMain.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        this.GlowHead.addChild(this.HatBase2);
        this.HatBase2.addChild(this.Hat201_01);
        this.HatBase2.addChild(this.Hat201_02);
        this.HatBase2.addChild(this.Hat201_03);
        this.HatBase2.addChild(this.Hat201_04);
        this.HatBase2.addChild(this.Hat201_05);
        this.HatBase2.addChild(this.Hat201_06);
        this.HatBase2.addChild(this.Hat201_07);
        this.HatBase2.addChild(this.Hat201_08);
        this.HatBase2.addChild(this.Hat201_09);
        this.HatBase2.addChild(this.Hat201_10);
        this.HatBase2.addChild(this.Hat201_11);
        this.HatBase2.addChild(this.Hat201_12);
        this.HatBase2.addChild(this.Hat202a);
        this.HatBase2.addChild(this.Hat202b);
    }

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
    	GL11.glScalef(0.4F, 0.4F, 0.4F);
    	GL11.glTranslatef(0F, 2.25F, 0F);
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	//light part
    	GL11.glDisable(GL11.GL_LIGHTING);
    	GL11.glEnable(GL11.GL_CULL_FACE);  //disable drawing back face
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	GL11.glDisable(GL11.GL_CULL_FACE);
    	
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glPopMatrix();
    }
    
	//model animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		IShipEmotion ent = (IShipEmotion)entity;

		showEquip(ent);
		
		EmotionHelper.rollEmotionAdv(this, ent);
		  
//		if(ent.getStateFlag(ID.F.NoFuel)) {
//			motionStopPos(f, f1, f2, f3, f4, ent);
//		}
//		else {
			motionHumanPos(f, f1, f2, f3, f4, ent);
//		}
		
		setGlowRotation();
    }
    
	//設定模型發光部份的rotation
    private void setGlowRotation() {
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
    }
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {
    	GL11.glTranslatef(0F, 1.8F, 0F);
    	setFace(4);
    	
		//body
    	this.Head.rotateAngleX = 0F;
    	this.Head.rotateAngleY = 0F;
    	this.Head.rotateAngleZ = 0F;
    	this.Ahoke.rotateAngleY = 0.5236F;
    	this.BodyMain.rotateAngleX = 1.4F;
    	this.Butt.rotateAngleX = 0.21F;
    	this.Butt.offsetY = 0F;
	  	this.Skirt01.rotateAngleX = -0.052F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt02.rotateAngleX = -0.052F;
	  	this.Skirt02.offsetY = 0F;
	  	this.Hair01.rotateAngleX = -0.07F;
    	this.Hair01.offsetY = -0.2F;
    	//arm
    	this.ArmLeft01.rotateAngleX = -2.8F;
    	this.ArmLeft01.rotateAngleY = 0F;
    	this.ArmLeft01.rotateAngleZ = 0.7F;
    	this.ArmRight01.rotateAngleX = -2.8F;
    	this.ArmRight01.rotateAngleY = 0F;
    	this.ArmRight01.rotateAngleZ = -0.7F;
    	this.ArmLeft02.rotateAngleZ = 1.0F;
	    this.ArmLeft02.offsetX = 0F;
		this.ArmRight02.rotateAngleZ = -1.0F;
		this.ArmRight02.offsetX = 0F;
    	//leg
    	this.LegLeft01.rotateAngleX = 0.1F;
    	this.LegLeft01.rotateAngleY = 3.1415F;
    	this.LegLeft01.rotateAngleZ = -0.1F;
    	this.LegRight01.rotateAngleX = 0.1F;
		this.LegRight01.rotateAngleY = 3.1415F;
    	this.LegRight01.rotateAngleZ = 0.1F;
    	this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
    }
    
	//雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {   
  		float angleX = MathHelper.cos(f2*0.08F + f * 0.25F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		float headX = 0F;
  		float headZ = 0F;
  		
  		//水上漂浮
  		if(((IShipFloating)ent).getShipDepth() > 0) {
    		GL11.glTranslatef(0F, angleX * 0.1F - 0.025F, 0F);
    	}

    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.14F;	//LegLeft01
	  	addk2 = angleAdd2 * 0.5F - 0.0523F;	//LegRight01
    	
  	    //head
	  	this.Head.rotateAngleX = f4 * 0.0174532925F + 0.11F;
	  	this.Head.rotateAngleY = f3 * 0.013F;
	  	//body
  	    this.Ahoke.rotateAngleY = angleX * 0.2F + 1.2F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.21F;
	  	this.Butt.offsetY = 0F;
	  	this.Skirt01.rotateAngleX = -0.052F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt02.rotateAngleX = -0.052F;
	  	this.Skirt02.offsetY = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.26F;
    	this.Hair01.rotateAngleZ = 0F;
    	this.Hair01.offsetY = 0F;
    	this.Hair02a1.rotateAngleX = angleX * 0.03F + 0.26F;
    	this.Hair02a1.rotateAngleZ = 0F;
    	this.Hair02b1.rotateAngleX = angleX * 0.03F + 0.26F;
    	this.Hair02b1.rotateAngleZ = 0.26F;
    	this.Hair02c1.rotateAngleX = angleX * 0.03F + 0.17F;
    	this.Hair02c1.rotateAngleZ = -0.4F;
    	this.Hair02d1.rotateAngleX = 0.2618F;
    	this.Hair02d1.rotateAngleZ = angleX * 0.03F + 0.35F;
    	this.Hair02e1.rotateAngleX = 0.05F;
    	this.Hair02e1.rotateAngleZ = -angleX * 0.03F - 0.6F;
    	this.HairL01.rotateAngleX = -0.2618F;
    	this.HairL01.rotateAngleZ = 0.087F;
	  	this.HairL02.rotateAngleX = 0.3142F;
	  	this.HairL02.rotateAngleZ = 0.0873F;
	  	this.HairR01.rotateAngleX = -0.2618F;
	  	this.HairR01.rotateAngleZ = -0.0873F;
	  	this.HairR02.rotateAngleX = 0.21F;
	  	this.HairR02.rotateAngleZ = -0.0873F;
	    //arm
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F + 0.1745F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.3F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.25F - 0.0523F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.3F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.1047F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.1047F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
	  	this.EquipHead01.rotateAngleZ = angleX * 0.2F -  1.5708F;
	  	this.EquipC02.rotateAngleY = 0.5F + this.Head.rotateAngleY * 0.5F;
	  	this.EquipC04a.rotateAngleX = -0.2F + this.Head.rotateAngleX;
	  	if(this.EquipC04a.rotateAngleX > 0F) this.EquipC04a.rotateAngleX = 0F;
	  	this.EquipC05a.rotateAngleX = this.EquipC04a.rotateAngleX;
	    
	    if(ent.getStateEmotion(ID.S.State) < ID.State.EQUIP00) {
	    	this.ArmLeft01.rotateAngleZ += 0.1F;
	    	this.ArmRight01.rotateAngleZ -= 0.1F;
	    }

	    if(ent.getIsSprinting() || f1 > 0.95F) {  //奔跑動作
	 	    //body
	 	    this.Head.rotateAngleX -= 0.25F;
	 	    this.BodyMain.rotateAngleX = 0.1F;
	 	    this.Skirt01.rotateAngleX = -0.1F;
	 	  	this.Skirt02.rotateAngleX = -0.1885F;
	 	  	//arm
	    	this.ArmLeft01.rotateAngleX = 0.35F;
	    	this.ArmLeft01.rotateAngleZ = -0.5F;
		    this.ArmRight01.rotateAngleX = 0.35F;
	    	this.ArmRight01.rotateAngleZ = 0.5F;
	 	    //leg
	 	    addk1 -= 0.2F;
	 	  	addk2 -= 0.2F;
  		}
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if(ent.getIsSneaking()) {		//潛行, 蹲下動作
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.4F;
		  	this.Butt.offsetY = -0.19F;
		  	this.Skirt01.rotateAngleX = -0.12F;
		  	this.Skirt02.rotateAngleX = -0.4F;
		  	this.Skirt02.offsetY = -0.1F;
		    //arm 
		    this.ArmLeft01.rotateAngleX = -0.6F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
		    this.ArmRight01.rotateAngleX = -0.6F;
		    this.ArmRight01.rotateAngleZ = -0.2618F;
		    //leg
		    addk1 -= 0.55F;
		    addk2 -= 0.55F;
  		}//end if sneaking
	    
	    //騎乘動作
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
	    		GL11.glTranslatef(0F, 1.3F, 0F);
		    	setFace(2);
		    	//head
		    	this.Head.rotateAngleY -= 0.4F;
		    	this.Head.rotateAngleZ += 0.2F;
		    	//body
		    	this.BodyMain.rotateAngleX = -0.25F;
		    	this.Butt.rotateAngleX = -0.2F;
		    	this.Butt.offsetY = -0.1F;
				this.Skirt01.rotateAngleX = -0.07F;
				this.Skirt01.offsetY = -0.1F;
				this.Skirt02.rotateAngleX = -0.16F;
				this.Skirt02.offsetY = -0.15F;
				//arm
				this.ArmLeft01.rotateAngleX = -2.7F;
				this.ArmLeft01.rotateAngleZ = 0.4F;
				this.ArmRight01.rotateAngleX = -2.7F;
				this.ArmRight01.rotateAngleZ = -0.4F;
				//leg
				addk1 = -0.9F;
				addk2 = -0.9F;
				this.LegLeft01.rotateAngleZ = -0.14F;
				this.LegLeft02.rotateAngleX = 1.2217F;
				this.LegLeft02.rotateAngleY = 1.2217F;
				this.LegLeft02.rotateAngleZ = -1.0472F;
				this.LegLeft02.offsetX = 0.32F;
				this.LegLeft02.offsetY = 0.05F;
				this.LegLeft02.offsetZ = 0.35F;
				this.LegRight01.rotateAngleZ = 0.14F;
				this.LegRight02.rotateAngleX = 1.2217F;
				this.LegRight02.rotateAngleY = -1.2217F;
				this.LegRight02.rotateAngleZ = 1.0472F;
				this.LegRight02.offsetX = -0.32F;
				this.LegRight02.offsetY = 0.05F;
				this.LegRight02.offsetZ = 0.35F;
	    	}
	    	else {
	    		GL11.glTranslatef(0F, 1F, 0F);
		    	//Body
			  	this.Head.rotateAngleX -= 0.1F;
			  	this.BodyMain.rotateAngleX = 0F;
		    	this.Butt.rotateAngleX = -0.2F;
		    	this.Butt.offsetY = -0.1F;
				this.Skirt01.rotateAngleX = -0.07F;
				this.Skirt01.offsetY = -0.05F;
				this.Skirt02.rotateAngleX = -0.16F;
				this.Skirt02.offsetY = -0.08F;
				//arm 
			  	this.ArmLeft01.rotateAngleX = -0.4F;
			  	this.ArmLeft01.rotateAngleZ = 0.15F;
		    	this.ArmRight01.rotateAngleX = -0.4F;
				this.ArmRight01.rotateAngleZ = -0.15F;
			  	//leg
			  	addk1 = -0.65F;
			  	addk2 = -0.65F;
			  	this.LegLeft01.rotateAngleY = 0.2F;
				this.LegLeft01.rotateAngleZ = 0F;
				this.LegLeft02.offsetZ = 0.375F;
				this.LegLeft02.rotateAngleX = 2.45F;
				this.LegLeft02.rotateAngleZ = 0.0175F;
				this.LegRight01.rotateAngleY = -0.2F;
				this.LegRight01.rotateAngleZ = 0F;
				this.LegRight02.offsetZ = 0.375F;
				this.LegRight02.rotateAngleX = 2.45F;
				this.LegRight02.rotateAngleZ = -0.0175F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if(ent.getAttackTime() > 30) {
	    	setFaceAttack(ent);
	    	//arm
		    this.ArmLeft01.rotateAngleX = -1.55F;
	    	this.ArmLeft01.rotateAngleY = 0.3F;
	    	this.ArmLeft01.rotateAngleZ = 0F;
	    	this.ArmLeft02.rotateAngleZ = 0.7F;
	    	this.ArmRight01.rotateAngleX = -1.7F;
	    	this.ArmRight01.rotateAngleY = -0.1F;
	    	this.ArmRight01.rotateAngleZ = 1.5F;
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if(f6 != 0F) {
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.RAD_MUL;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.RAD_MUL + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.RAD_MUL;
	  	}
	  	
	  	//鬢毛調整
	    headX = this.Head.rotateAngleX * -0.5F;
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleX += headX;
	    this.Hair01.rotateAngleZ += headZ;
	    this.Hair02a1.rotateAngleX += headX;
	  	this.Hair02a1.rotateAngleZ += headZ;
	  	this.Hair02b1.rotateAngleX += headX;
	  	this.Hair02b1.rotateAngleZ += headZ;
	  	this.Hair02c1.rotateAngleX += headX;
	  	this.Hair02c1.rotateAngleZ += headZ;
	  	this.Hair02d1.rotateAngleX += headX;
	  	this.Hair02d1.rotateAngleZ += headZ;
	  	this.Hair02e1.rotateAngleX += headX;
	  	this.Hair02e1.rotateAngleZ += headZ;
	  	this.HairL01.rotateAngleZ += headZ;
	  	this.HairL02.rotateAngleZ += headZ;
	  	this.HairR01.rotateAngleZ += headZ;
	  	this.HairR02.rotateAngleZ += headZ * 2F;
		this.HairL01.rotateAngleX += headX;
	  	this.HairL02.rotateAngleX += headX;
	  	this.HairR01.rotateAngleX += headX;
	  	this.HairR02.rotateAngleX += headX;
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
  	
  	private void showEquip(IShipEmotion ent) {
  		switch(ent.getStateEmotion(ID.S.State)) {
  		case ID.State.EQUIP00:
  			this.EquipBase.isHidden = false;
  			this.EquipTL03.isHidden = false;
  			break;
  		default:  //normal
  			this.EquipBase.isHidden = true;
  			this.EquipTL03.isHidden = true;
  			break;
  		}
  		
  		switch(ent.getStateEmotion(ID.S.State2)) {
  		case ID.State.EQUIP00_2:
  			this.HatBase.isHidden = false;
  			this.Hair02f1.isHidden = false;
  			this.HatBase2.isHidden = true;
  			break;
  		case ID.State.EQUIP01_2:
  			this.HatBase.isHidden = true;
  			this.Hair02f1.isHidden = true;
  			this.HatBase2.isHidden = false;
  			this.HatBase2.rotateAngleX = -0.2618F;
  			this.HatBase2.offsetY = 0F;
  			this.HatBase2.offsetZ = 0F;
  			break;
  		case ID.State.EQUIP02_2:
  			this.HatBase.isHidden = true;
  			this.Hair02f1.isHidden = true;
  			this.HatBase2.isHidden = false;
  			this.HatBase2.rotateAngleX = -0.7F;
  			this.HatBase2.offsetY = -0.06F;
  			this.HatBase2.offsetZ = 0.06F;
  			break;
  		default:  //normal
  			this.HatBase.isHidden = true;
  			this.Hair02f1.isHidden = false;
  			this.HatBase2.isHidden = true;
  			break;
  		}
  	}
  	
    //設定顯示的臉型
  	@Override
  	public void setFace(int emo) {
  		switch(emo) {
  		case 1:
  			this.Face1.isHidden = false;
  			this.Face1.rotateAngleY = 0F;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 2:
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face2.rotateAngleY = 0F;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 3:
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face3.rotateAngleY = 0F;
  			this.Face4.isHidden = true;
  			break;
  		case 4:
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = false;
  			this.Face4.rotateAngleY = 0F;
  			break;
  		case 0:
  		case 5:
  		case 6:
  			this.Face1.isHidden = false;
  			this.Face1.rotateAngleY = 3.14159F;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 7:
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face2.rotateAngleY = 3.14159F;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 8:
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face3.rotateAngleY = 3.14159F;
  			this.Face4.isHidden = true;
  			break;
  		case 9:
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
		if (ent.getTickExisted() % 2048 > 1024)
		{
			setFace(6);
		}
		else
		{
			setFace(7);
		}
	}

	@Override
	public void setFaceBlink0(IShipEmotion ent)
	{
		setFace(2);
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
			setFace(3);
		}
		else
		{
			setFace(9);
		}
	}

	@Override
	public void setFaceAttack(IShipEmotion ent)
	{
		int t = ent.getTickExisted() % 128;
		
		if (t < 45)
		{
			setFace(2);
		}
		else if (t < 90)
		{
			setFace(3);
		}
		else
		{
			setFace(8);
		}
	}

	@Override
	public void setFaceDamaged(IShipEmotion ent)
	{
		int t = ent.getTickExisted() % 128;
		
		if (t < 64)
		{
			setFace(8);
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
			setFace(7);
		}
		else
		{
			setFace(8);
		}
	}

	@Override
	public void setFaceScorn(IShipEmotion ent)
	{
		if (ent.getTickExisted() % 128 > 64)
		{
			setFace(7);
		}
		else
		{
			setFace(8);
		}
	}
	
	@Override
	public void setFaceBored(IShipEmotion ent)
	{
//		int t = ent.getTickExisted() % 1024;
//		
//		if (t < 330)
//		{
//			setFace(1);
//		}
//		else if (t < 660)
//		{
//			setFace(2);
//		}
//		else
//		{
//			setFace(7);
//		}
	}
	
	
}

