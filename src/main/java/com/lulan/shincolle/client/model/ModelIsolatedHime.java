package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;

/**
 * ModelIsolatedHime - PinkaLulan 2017/9/29
 * Created using Tabula 5.1.0
 */
public class ModelIsolatedHime extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft01;
    public ModelRenderer Cloth02a;
    public ModelRenderer Head;
    public ModelRenderer Cloth01a;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Mouth0;
    public ModelRenderer Mouth1;
    public ModelRenderer Mouth2;
    public ModelRenderer Flush0;
    public ModelRenderer Flush1;
    public ModelRenderer Hair01;
    public ModelRenderer Face0;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer HatBase;
    public ModelRenderer HairU01;
    public ModelRenderer Ahoke;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer Hat01;
    public ModelRenderer Hat03;
    public ModelRenderer Hat05;
    public ModelRenderer HeadH1;
    public ModelRenderer HeadH4;
    public ModelRenderer Hat02a;
    public ModelRenderer Hat02b;
    public ModelRenderer Hat02c;
    public ModelRenderer Hat02d;
    public ModelRenderer Hat02e;
    public ModelRenderer Hat02f;
    public ModelRenderer Hat02g;
    public ModelRenderer Hat02h;
    public ModelRenderer Hat02i;
    public ModelRenderer Hat02j;
    public ModelRenderer Hat04a;
    public ModelRenderer Hat04b;
    public ModelRenderer Hat04c;
    public ModelRenderer Hat04d;
    public ModelRenderer Hat04e;
    public ModelRenderer Hat04f;
    public ModelRenderer Hat04g;
    public ModelRenderer Hat04h;
    public ModelRenderer Hat06a;
    public ModelRenderer Hat02b_1;
    public ModelRenderer Hat02d_1;
    public ModelRenderer Hat02e_1;
    public ModelRenderer Hat02f_1;
    public ModelRenderer Hat02g_1;
    public ModelRenderer Hat02h_1;
    public ModelRenderer Hat02i_1;
    public ModelRenderer HeadH2;
    public ModelRenderer HeadH3;
    public ModelRenderer HeadH5;
    public ModelRenderer HeadH6;
    public ModelRenderer Cloth01b;
    public ModelRenderer Cloth01c;
    public ModelRenderer Cloth01b2;
    public ModelRenderer Cloth01c2;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt02;
    public ModelRenderer Skirt03;
    public ModelRenderer LegRight02a;
    public ModelRenderer LegArmor02a;
    public ModelRenderer LegRight02b;
    public ModelRenderer LegArmor02b;
    public ModelRenderer LegArmor02c;
    public ModelRenderer LegLeft02a;
    public ModelRenderer LegArmor01a;
    public ModelRenderer LegLeft02b;
    public ModelRenderer LegArmor01b;
    public ModelRenderer LegArmor01c;
    public ModelRenderer ArmRight02;
    public ModelRenderer Cloth02c;
    public ModelRenderer Cloth03a;
    public ModelRenderer ArmLeft02;
    public ModelRenderer Cloth02b;
    public ModelRenderer Cloth03b;

    
    public ModelIsolatedHime()
    {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.scale = 0.47F;
        this.offsetY = 1.75F;
        this.offsetItem = new float[] {0.08F, 1.02F, -0.07F};
        this.offsetBlock = new float[] {0.08F, 1.02F, -0.07F};
        
        this.setDefaultFaceModel();
        
        this.HeadH3 = new ModelRenderer(this, 0, 0);
        this.HeadH3.setRotationPoint(-0.7F, 0.0F, 0.0F);
        this.HeadH3.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(HeadH3, 0.0F, -0.08726646259971647F, 0.17453292519943295F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.15707963267948966F, 0.0F, 0.08726646259971647F);
        this.Hat02d = new ModelRenderer(this, 60, 15);
        this.Hat02d.setRotationPoint(-4.0F, -0.6F, -0.6F);
        this.Hat02d.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat02d, 0.0F, 0.017453292519943295F, 3.07177948351002F);
        this.Cloth01b2 = new ModelRenderer(this, 44, 0);
        this.Cloth01b2.setRotationPoint(0.5F, 0.3F, 0.3F);
        this.Cloth01b2.addBox(0.0F, -3.0F, -1.0F, 6, 6, 2, 0.0F);
        this.setRotateAngle(Cloth01b2, -0.08726646259971647F, 0.17453292519943295F, -0.13962634015954636F);
        this.Ahoke = new ModelRenderer(this, 106, 31);
        this.Ahoke.setRotationPoint(-0.5F, -7.0F, -6.0F);
        this.Ahoke.addBox(0.0F, -6.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.5235987755982988F, 0.6981317007977318F, 0.0F);
        this.HatBase = new ModelRenderer(this, 0, 0);
        this.HatBase.setRotationPoint(0.0F, -13.8F, -2.0F);
        this.HatBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(HatBase, -0.05235987755982988F, 0.0F, 0.0F);
        this.LegRight02b = new ModelRenderer(this, 128, 63);
        this.LegRight02b.mirror = true;
        this.LegRight02b.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02b.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Cloth02c = new ModelRenderer(this, 128, 85);
        this.Cloth02c.mirror = true;
        this.Cloth02c.setRotationPoint(-0.9F, -1.5F, 0.0F);
        this.Cloth02c.addBox(-3.0F, 0.0F, -3.5F, 6, 6, 7, 0.0F);
        this.setRotateAngle(Cloth02c, 0.0F, 0.0F, -0.05235987755982988F);
        this.Hair03 = new ModelRenderer(this, 0, 15);
        this.Hair03.setRotationPoint(0.0F, 12.5F, -0.1F);
        this.Hair03.addBox(-7.5F, 0.0F, -5.5F, 15, 15, 8, 0.0F);
        this.setRotateAngle(Hair03, -0.2617993877991494F, 0.0F, 0.0F);
        this.Hat04f = new ModelRenderer(this, 60, 0);
        this.Hat04f.setRotationPoint(-4.3F, 0.0F, 2.5F);
        this.Hat04f.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat04f, -0.08726646259971647F, -0.017453292519943295F, 3.07177948351002F);
        this.Hair02 = new ModelRenderer(this, 0, 38);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 2.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -6.0F, 16, 16, 9, 0.0F);
        this.setRotateAngle(Hair02, 0.12217304763960307F, 0.0F, 0.0F);
        this.Hat02a = new ModelRenderer(this, 60, 15);
        this.Hat02a.setRotationPoint(0.0F, 1.5F, -0.7F);
        this.Hat02a.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat02a, -0.13962634015954636F, 0.0F, 0.0F);
        this.Hat04g = new ModelRenderer(this, 60, 0);
        this.Hat04g.setRotationPoint(3.5F, -0.6F, 2.2F);
        this.Hat04g.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat04g, -0.05235987755982988F, 0.0F, 3.141592653589793F);
        this.Hat02b = new ModelRenderer(this, 60, 15);
        this.Hat02b.setRotationPoint(4.0F, -0.6F, -0.6F);
        this.Hat02b.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat02b, 0.0F, 0.017453292519943295F, -3.07177948351002F);
        this.Hat02b_1 = new ModelRenderer(this, 60, 15);
        this.Hat02b_1.setRotationPoint(4.0F, -1.3F, -0.2F);
        this.Hat02b_1.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat02b_1, 0.08726646259971647F, 0.06981317007977318F, -2.7576202181510405F);
        this.LegArmor01c = new ModelRenderer(this, 0, 3);
        this.LegArmor01c.setRotationPoint(0.0F, 2.0F, 0.2F);
        this.LegArmor01c.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LegArmor01c, 0.6108652381980153F, 0.0F, 0.0F);
        this.HeadH6 = new ModelRenderer(this, 0, 900);
        this.HeadH6.setRotationPoint(0.7F, 0.0F, 0.0F);
        this.HeadH6.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(HeadH6, 0.0F, 0.08726646259971647F, -0.17453292519943295F);
        this.Mouth1 = new ModelRenderer(this, 100, 58);
        this.Mouth1.setRotationPoint(0.0F, -4.2F, -6.2F);
        this.Mouth1.addBox(-3.0F, 0.0F, -0.5F, 6, 4, 1, 0.0F);
        this.Cloth01c = new ModelRenderer(this, 0, 16);
        this.Cloth01c.setRotationPoint(-2.0F, 1.6F, -0.7F);
        this.Cloth01c.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Cloth01c, -0.6108652381980153F, 0.13962634015954636F, 0.17453292519943295F);
        this.Cloth03a = new ModelRenderer(this, 128, 50);
        this.Cloth03a.setRotationPoint(2.5F, 3.5F, -2.5F);
        this.Cloth03a.addBox(-3.0F, 0.0F, -3.0F, 6, 7, 6, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 84);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, -0.05235987755982988F, 0.0F, -0.2792526803190927F);
        this.Cloth02b = new ModelRenderer(this, 128, 85);
        this.Cloth02b.setRotationPoint(0.9F, -1.5F, 0.0F);
        this.Cloth02b.addBox(-3.0F, 0.0F, -3.5F, 6, 6, 7, 0.0F);
        this.setRotateAngle(Cloth02b, 0.0F, 0.0F, 0.05235987755982988F);
        this.Face0 = new ModelRenderer(this, 98, 63);
        this.Face0.setRotationPoint(0.0F, -12.2F, -7.1F);
        this.Face0.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 89);
        this.Face2.setRotationPoint(0.0F, -12.2F, -6.1F);
        this.Face2.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Hat02e_1 = new ModelRenderer(this, 60, 15);
        this.Hat02e_1.setRotationPoint(-7.1F, 0.5F, 0.2F);
        this.Hat02e_1.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat02e_1, -0.05235987755982988F, 0.08726646259971647F, -0.08726646259971647F);
        this.Hat06a = new ModelRenderer(this, 60, 15);
        this.Hat06a.setRotationPoint(-0.3F, 0.5F, -0.4F);
        this.Hat06a.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat06a, -0.1907644872429802F, -0.03490658503988659F, 0.17453292519943295F);
        this.Hat03 = new ModelRenderer(this, 88, 23);
        this.Hat03.setRotationPoint(10.0F, 5.5F, 1.3F);
        this.Hat03.addBox(-8.5F, 0.0F, -0.5F, 17, 4, 3, 0.0F);
        this.setRotateAngle(Hat03, 0.0F, -0.05235987755982988F, 1.5707963267948966F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.Cloth01b = new ModelRenderer(this, 44, 0);
        this.Cloth01b.setRotationPoint(-0.5F, 0.3F, 0.3F);
        this.Cloth01b.addBox(-6.0F, -3.0F, -1.0F, 6, 6, 2, 0.0F);
        this.setRotateAngle(Cloth01b, -0.08726646259971647F, -0.17453292519943295F, 0.13962634015954636F);
        this.Hat04e = new ModelRenderer(this, 60, 0);
        this.Hat04e.setRotationPoint(-0.3F, 1.1F, 2.7F);
        this.Hat04e.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat04e, 0.17453292519943295F, 0.0F, -0.03490658503988659F);
        this.HeadH2 = new ModelRenderer(this, 33, 102);
        this.HeadH2.setRotationPoint(-1.8F, 0.0F, 0.0F);
        this.HeadH2.addBox(-1.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(HeadH2, 0.0F, 0.0F, 0.12217304763960307F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.Hat02g = new ModelRenderer(this, 60, 0);
        this.Hat02g.setRotationPoint(-4.0F, 0.2F, 2.3F);
        this.Hat02g.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat02g, 0.0F, 0.06981317007977318F, 3.1066860685499065F);
        this.Face1 = new ModelRenderer(this, 98, 76);
        this.Face1.setRotationPoint(0.0F, -12.2F, -6.1F);
        this.Face1.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Hat02h = new ModelRenderer(this, 60, 0);
        this.Hat02h.setRotationPoint(-8.0F, 2.8F, 2.4F);
        this.Hat02h.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat02h, 0.08726646259971647F, -0.05235987755982988F, -0.40142572795869574F);
        this.LegArmor02c = new ModelRenderer(this, 0, 3);
        this.LegArmor02c.setRotationPoint(0.0F, 2.0F, 0.2F);
        this.LegArmor02c.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LegArmor02c, 0.6108652381980153F, 0.0F, 0.0F);
        this.LegArmor01b = new ModelRenderer(this, 12, 0);
        this.LegArmor01b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LegArmor01b.addBox(-2.5F, 0.0F, 0.0F, 5, 4, 3, 0.0F);
        this.setRotateAngle(LegArmor01b, 0.6108652381980153F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 103, 35);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.Neck.addBox(-2.5F, -2.0F, -3.0F, 5, 2, 5, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 63);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 84);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.2617993877991494F, 0.0F, 0.2792526803190927F);
        this.Mouth2 = new ModelRenderer(this, 114, 53);
        this.Mouth2.setRotationPoint(0.0F, -4.2F, -6.2F);
        this.Mouth2.addBox(-3.0F, 0.0F, -0.5F, 6, 4, 1, 0.0F);
        this.LegLeft02a = new ModelRenderer(this, 0, 63);
        this.LegLeft02a.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02a.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.LegArmor02b = new ModelRenderer(this, 1, 0);
        this.LegArmor02b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LegArmor02b.addBox(-2.5F, 0.0F, 0.0F, 5, 4, 3, 0.0F);
        this.setRotateAngle(LegArmor02b, 0.6108652381980153F, 0.0F, 0.0F);
        this.Mouth0 = new ModelRenderer(this, 100, 53);
        this.Mouth0.setRotationPoint(0.0F, -4.2F, -7.2F);
        this.Mouth0.addBox(-3.0F, 0.0F, -0.5F, 6, 4, 1, 0.0F);
        this.Hat02g_1 = new ModelRenderer(this, 60, 0);
        this.Hat02g_1.setRotationPoint(-3.5F, -0.6F, 2.3F);
        this.Hat02g_1.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat02g_1, -0.05235987755982988F, 0.0F, -3.141592653589793F);
        this.Face3 = new ModelRenderer(this, 98, 102);
        this.Face3.setRotationPoint(0.0F, -12.2F, -6.1F);
        this.Face3.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Hat02e = new ModelRenderer(this, 60, 15);
        this.Hat02e.setRotationPoint(-8.0F, 2.0F, -0.6F);
        this.Hat02e.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat02e, 0.03490658503988659F, 0.0F, -0.4363323129985824F);
        this.Hat04d = new ModelRenderer(this, 60, 15);
        this.Hat04d.setRotationPoint(-4.0F, -1.3F, -0.2F);
        this.Hat04d.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat04d, 0.08726646259971647F, -0.06981317007977318F, 2.7576202181510405F);
        this.Hat02c = new ModelRenderer(this, 60, 15);
        this.Hat02c.setRotationPoint(8.0F, 2.0F, -0.6F);
        this.Hat02c.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat02c, 0.05235987755982988F, 0.0F, 0.4363323129985824F);
        this.Hat04a = new ModelRenderer(this, 60, 15);
        this.Hat04a.setRotationPoint(-0.3F, 0.5F, -0.5F);
        this.Hat04a.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat04a, -0.17453292519943295F, -0.03490658503988659F, -0.17453292519943295F);
        this.Skirt03 = new ModelRenderer(this, 128, 32);
        this.Skirt03.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.Skirt03.addBox(-11.5F, 0.0F, -6.5F, 23, 4, 13, 0.0F);
        this.setRotateAngle(Skirt03, -0.05235987755982988F, 0.0F, 0.0F);
        this.LegRight02a = new ModelRenderer(this, 0, 63);
        this.LegRight02a.mirror = true;
        this.LegRight02a.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02a.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Hat02i = new ModelRenderer(this, 60, 0);
        this.Hat02i.setRotationPoint(4.0F, 0.2F, 2.3F);
        this.Hat02i.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat02i, 0.0F, -0.06981317007977318F, -3.1066860685499065F);
        this.LegArmor01a = new ModelRenderer(this, 0, 3);
        this.LegArmor01a.setRotationPoint(0.0F, 13.0F, -5.0F);
        this.LegArmor01a.addBox(-3.5F, -4.0F, 0.0F, 7, 4, 3, 0.0F);
        this.setRotateAngle(LegArmor01a, -0.2617993877991494F, 0.0F, 0.0F);
        this.LegLeft02b = new ModelRenderer(this, 128, 63);
        this.LegLeft02b.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02b.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Hat01 = new ModelRenderer(this, 88, 23);
        this.Hat01.setRotationPoint(0.0F, -4.0F, 1.0F);
        this.Hat01.addBox(-8.5F, 0.0F, -0.5F, 17, 4, 3, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.HeadH5 = new ModelRenderer(this, 33, 102);
        this.HeadH5.setRotationPoint(1.8F, 0.0F, 0.0F);
        this.HeadH5.addBox(0.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(HeadH5, 0.0F, 0.0F, -0.12217304763960307F);
        this.HeadH4 = new ModelRenderer(this, 0, 0);
        this.HeadH4.mirror = true;
        this.HeadH4.setRotationPoint(8.5F, -2.0F, 2.0F);
        this.HeadH4.addBox(0.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.setRotateAngle(HeadH4, 0.17453292519943295F, 0.0F, -0.4363323129985824F);
        this.Hat02i_1 = new ModelRenderer(this, 60, 0);
        this.Hat02i_1.setRotationPoint(4.3F, 0.0F, 2.6F);
        this.Hat02i_1.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat02i_1, -0.08726646259971647F, 0.017453292519943295F, -3.07177948351002F);
        this.Hat05 = new ModelRenderer(this, 88, 23);
        this.Hat05.setRotationPoint(-10.0F, 5.5F, 1.3F);
        this.Hat05.addBox(-8.5F, 0.0F, -0.5F, 17, 4, 3, 0.0F);
        this.setRotateAngle(Hat05, 0.0F, 0.05235987755982988F, -1.5707963267948966F);
        this.LegArmor02a = new ModelRenderer(this, 10, 0);
        this.LegArmor02a.setRotationPoint(0.0F, 13.0F, -5.0F);
        this.LegArmor02a.addBox(-3.5F, -4.0F, 0.0F, 7, 4, 3, 0.0F);
        this.setRotateAngle(LegArmor02a, -0.2617993877991494F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 82, 0);
        this.Butt.setRotationPoint(0.0F, 2.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 128, 15);
        this.Skirt02.setRotationPoint(0.0F, 2.7F, -1.0F);
        this.Skirt02.addBox(-10.5F, 0.0F, -6.0F, 21, 4, 12, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 115);
        this.Face4.setRotationPoint(0.0F, -12.2F, -6.1F);
        this.Face4.addBox(-7.0F, 0.0F, -0.5F, 14, 12, 1, 0.0F);
        this.Hat04h = new ModelRenderer(this, 60, 0);
        this.Hat04h.setRotationPoint(7.1F, 1.0F, 2.4F);
        this.Hat04h.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat04h, 0.05235987755982988F, 0.03490658503988659F, 0.10471975511965977F);
        this.Hat02h_1 = new ModelRenderer(this, 60, 0);
        this.Hat02h_1.setRotationPoint(-7.1F, 1.0F, 2.4F);
        this.Hat02h_1.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat02h_1, 0.05235987755982988F, 0.03490658503988659F, -0.10471975511965977F);
        this.HeadH1 = new ModelRenderer(this, 0, 0);
        this.HeadH1.mirror = true;
        this.HeadH1.setRotationPoint(-8.5F, -2.0F, 2.0F);
        this.HeadH1.addBox(-2.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.setRotateAngle(HeadH1, 0.17453292519943295F, 0.0F, 0.4363323129985824F);
        this.Cloth02a = new ModelRenderer(this, 128, 99);
        this.Cloth02a.setRotationPoint(0.0F, -11.5F, -0.6F);
        this.Cloth02a.addBox(-7.0F, 0.0F, -4.0F, 14, 7, 8, 0.0F);
        this.setRotateAngle(Cloth02a, 0.05235987755982988F, 0.0F, 0.0F);
        this.Hair01 = new ModelRenderer(this, 50, 30);
        this.Hair01.setRotationPoint(0.0F, -6.0F, 2.0F);
        this.Hair01.addBox(-7.5F, 0.0F, -4.0F, 15, 17, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.20943951023931953F, 0.0F, 0.0F);
        this.Hat04b = new ModelRenderer(this, 60, 15);
        this.Hat04b.setRotationPoint(3.2F, -1.2F, -0.2F);
        this.Hat04b.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat04b, 0.08726646259971647F, 0.08726646259971647F, 3.0543261909900767F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.Hat02f_1 = new ModelRenderer(this, 60, 0);
        this.Hat02f_1.setRotationPoint(0.3F, 1.1F, 2.5F);
        this.Hat02f_1.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat02f_1, 0.17453292519943295F, 0.0F, 0.03490658503988659F);
        this.ArmRight02 = new ModelRenderer(this, 24, 63);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.Flush0 = new ModelRenderer(this, 114, 58);
        this.Flush0.setRotationPoint(-6.0F, -3.0F, -7.8F);
        this.Flush0.addBox(-1.0F, 0.0F, -0.5F, 2, 1, 0, 0.0F);
        this.Hat02f = new ModelRenderer(this, 60, 0);
        this.Hat02f.setRotationPoint(0.0F, 1.7F, 2.6F);
        this.Hat02f.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat02f, 0.13962634015954636F, 0.0F, 0.0F);
        this.Cloth03b = new ModelRenderer(this, 128, 50);
        this.Cloth03b.mirror = true;
        this.Cloth03b.setRotationPoint(-2.5F, 3.5F, -2.5F);
        this.Cloth03b.addBox(-3.0F, 0.0F, -3.0F, 6, 7, 6, 0.0F);
        this.Hat02j = new ModelRenderer(this, 60, 0);
        this.Hat02j.setRotationPoint(8.0F, 2.8F, 2.6F);
        this.Hat02j.addBox(-2.0F, -3.0F, 0.0F, 4, 5, 7, 0.0F);
        this.setRotateAngle(Hat02j, 0.08726646259971647F, 0.05235987755982988F, 0.40142572795869574F);
        this.Flush1 = new ModelRenderer(this, 114, 58);
        this.Flush1.setRotationPoint(6.0F, -3.0F, -7.8F);
        this.Flush1.addBox(-1.0F, 0.0F, -0.5F, 2, 1, 0, 0.0F);
        this.Cloth01a = new ModelRenderer(this, 51, 2);
        this.Cloth01a.setRotationPoint(0.0F, 0.0F, -6.6F);
        this.Cloth01a.addBox(-1.0F, -2.5F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Cloth01a, 0.6283185307179586F, 0.0F, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 128, 0);
        this.Skirt01.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.Skirt01.addBox(-9.0F, 0.0F, -6.2F, 18, 4, 10, 0.0F);
        this.setRotateAngle(Skirt01, -0.08726646259971647F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.296705972839036F, 0.0F, -0.08726646259971647F);
        this.Hat04c = new ModelRenderer(this, 60, 15);
        this.Hat04c.setRotationPoint(7.1F, 0.5F, -0.1F);
        this.Hat04c.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat04c, -0.05235987755982988F, -0.08726646259971647F, 0.08726646259971647F);
        this.BodyMain = new ModelRenderer(this, 0, 105);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 15, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.Hat02d_1 = new ModelRenderer(this, 60, 15);
        this.Hat02d_1.setRotationPoint(-3.2F, -1.2F, -0.1F);
        this.Hat02d_1.addBox(-2.0F, -3.0F, -10.0F, 4, 5, 10, 0.0F);
        this.setRotateAngle(Hat02d_1, 0.08726646259971647F, -0.08726646259971647F, -3.0543261909900767F);
        this.Cloth01c2 = new ModelRenderer(this, 0, 16);
        this.Cloth01c2.mirror = true;
        this.Cloth01c2.setRotationPoint(2.0F, 1.6F, -0.7F);
        this.Cloth01c2.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Cloth01c2, -0.6108652381980153F, -0.13962634015954636F, -0.17453292519943295F);
        this.HeadH2.addChild(this.HeadH3);
        this.Butt.addChild(this.LegLeft01);
        this.Hat01.addChild(this.Hat02d);
        this.Cloth01a.addChild(this.Cloth01b2);
        this.Hair.addChild(this.Ahoke);
        this.Head.addChild(this.HatBase);
        this.LegRight01.addChild(this.LegRight02b);
        this.ArmRight01.addChild(this.Cloth02c);
        this.Hair02.addChild(this.Hair03);
        this.Hat03.addChild(this.Hat04f);
        this.Hair01.addChild(this.Hair02);
        this.Hat01.addChild(this.Hat02a);
        this.Hat03.addChild(this.Hat04g);
        this.Hat01.addChild(this.Hat02b);
        this.Hat05.addChild(this.Hat02b_1);
        this.LegArmor01b.addChild(this.LegArmor01c);
        this.HeadH5.addChild(this.HeadH6);
        this.Head.addChild(this.Mouth1);
        this.Cloth01a.addChild(this.Cloth01c);
        this.ArmRight02.addChild(this.Cloth03a);
        this.BodyMain.addChild(this.ArmLeft01);
        this.ArmLeft01.addChild(this.Cloth02b);
        this.Head.addChild(this.Face0);
        this.Head.addChild(this.Face2);
        this.Hat05.addChild(this.Hat02e_1);
        this.Hat05.addChild(this.Hat06a);
        this.HatBase.addChild(this.Hat03);
        this.Head.addChild(this.HairMain);
        this.Cloth01a.addChild(this.Cloth01b);
        this.Hat03.addChild(this.Hat04e);
        this.HeadH1.addChild(this.HeadH2);
        this.Neck.addChild(this.Head);
        this.Hat01.addChild(this.Hat02g);
        this.Head.addChild(this.Face1);
        this.Hat01.addChild(this.Hat02h);
        this.LegArmor02b.addChild(this.LegArmor02c);
        this.LegArmor01a.addChild(this.LegArmor01b);
        this.BodyMain.addChild(this.Neck);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.BodyMain.addChild(this.ArmRight01);
        this.Head.addChild(this.Mouth2);
        this.LegLeft01.addChild(this.LegLeft02a);
        this.LegArmor02a.addChild(this.LegArmor02b);
        this.Head.addChild(this.Mouth0);
        this.Hat05.addChild(this.Hat02g_1);
        this.Head.addChild(this.Face3);
        this.Hat01.addChild(this.Hat02e);
        this.Hat03.addChild(this.Hat04d);
        this.Hat01.addChild(this.Hat02c);
        this.Hat03.addChild(this.Hat04a);
        this.Skirt02.addChild(this.Skirt03);
        this.LegRight01.addChild(this.LegRight02a);
        this.Hat01.addChild(this.Hat02i);
        this.LegLeft01.addChild(this.LegArmor01a);
        this.LegLeft01.addChild(this.LegLeft02b);
        this.HatBase.addChild(this.Hat01);
        this.Hair.addChild(this.HairU01);
        this.HeadH4.addChild(this.HeadH5);
        this.HatBase.addChild(this.HeadH4);
        this.Hat05.addChild(this.Hat02i_1);
        this.HatBase.addChild(this.Hat05);
        this.LegRight01.addChild(this.LegArmor02a);
        this.BodyMain.addChild(this.Butt);
        this.Skirt01.addChild(this.Skirt02);
        this.Head.addChild(this.Face4);
        this.Hat03.addChild(this.Hat04h);
        this.Hat05.addChild(this.Hat02h_1);
        this.HatBase.addChild(this.HeadH1);
        this.BodyMain.addChild(this.Cloth02a);
        this.Head.addChild(this.Hair01);
        this.Hat03.addChild(this.Hat04b);
        this.Head.addChild(this.Hair);
        this.Hat05.addChild(this.Hat02f_1);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Head.addChild(this.Flush0);
        this.Hat01.addChild(this.Hat02f);
        this.ArmLeft02.addChild(this.Cloth03b);
        this.Hat01.addChild(this.Hat02j);
        this.Head.addChild(this.Flush1);
        this.Neck.addChild(this.Cloth01a);
        this.Butt.addChild(this.Skirt01);
        this.Butt.addChild(this.LegRight01);
        this.Hat03.addChild(this.Hat04c);
        this.Hat05.addChild(this.Hat02d_1);
        this.Cloth01a.addChild(this.Cloth01c2);
        
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
//    	this.GlowBodyMain.render(f5);
    	GlStateManager.disableCull();
    	GlStateManager.enableLighting();
    	
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
    }
    
	@Override
	public void showEquip(IShipEmotion ent)
	{
//  		switch (ent.getStateEmotion(ID.S.State))
//  		{
//  		case ID.ModelState.EQUIP00:
//  			this.Hat01.isHidden = false;
//  			this.EquipBag00.isHidden = true;
//  		break;
//  		case ID.ModelState.EQUIP01:
//  			this.Hat01.isHidden = true;
//  			this.EquipBag00.isHidden = false;
//  		break;
//  		case ID.ModelState.EQUIP02:
//  			this.Hat01.isHidden = false;
//  			this.EquipBag00.isHidden = false;
//  		break;
//  		default:  //normal
//  			this.Hat01.isHidden = true;
//  			this.EquipBag00.isHidden = true;
//  		break;
//  		}
//  		
//  		switch (ent.getStateEmotion(ID.S.State2))
//  		{
//  		case ID.ModelState.EQUIP00a:
//  			this.EquipBase.isHidden = false;
//  			this.ShoeL01.isHidden = true;
//  			this.ShoeR01.isHidden = true;
//  			this.ShoeL03.isHidden = false;
//  			this.ShoeL03_1.isHidden = false;
//  		break;
//  		case ID.ModelState.EQUIP01a:
//  			this.EquipBase.isHidden = true;
//  			this.ShoeL01.isHidden = false;
//  			this.ShoeR01.isHidden = false;
//  			this.ShoeL03.isHidden = true;
//  			this.ShoeL03_1.isHidden = true;
//  		break;
//  		case ID.ModelState.EQUIP02a:
//  			this.EquipBase.isHidden = false;
//  			this.ShoeL01.isHidden = false;
//  			this.ShoeR01.isHidden = false;
//  			this.ShoeL03.isHidden = false;
//  			this.ShoeL03_1.isHidden = false;
//  		break;
//  		default:  //normal
//  			this.EquipBase.isHidden = true;
//  			this.ShoeL01.isHidden = true;
//  			this.ShoeR01.isHidden = true;
//  			this.ShoeL03.isHidden = true;
//  			this.ShoeL03_1.isHidden = true;
//  		break;
//  		}
	}

	@Override
	public void syncRotationGlowPart()
	{
//		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
//		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
//		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
//		this.GlowNeck.rotateAngleX = this.Neck.rotateAngleX;
//		this.GlowNeck.rotateAngleY = this.Neck.rotateAngleY;
//		this.GlowNeck.rotateAngleZ = this.Neck.rotateAngleZ;
//		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
//		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
//		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	float addk1 = 0F;
  		float addk2 = 0F;
  		float headX = 0F;
  		float headZ = 0F;
  		
  		GlStateManager.translate(0F, 0.55F, 0F);
  		this.setFaceHungry(ent);
  		
//  		//移動頭部使其看人
//  		this.Head.rotateAngleX = 0F;	//左右角度
//	  	this.Head.rotateAngleY = 0F;
//	  	this.Head.rotateAngleZ = 0F;
//	  	headX = this.Head.rotateAngleX * -0.5F;
//	    //正常站立動作
//	    //胸部
//  	    this.BoobL.rotateAngleX = -0.7F;
//  	    this.BoobR.rotateAngleX = -0.7F;
//	  	//Body
//  	    this.Ahoke.rotateAngleY = 0.5236F;
//	  	this.BodyMain.rotateAngleZ = 0F;
//	  	//hair
//	  	this.Hair01.rotateAngleX = 0.26F + headX;
//	  	this.Hair02.rotateAngleX = -0.08F + headX;
//	  	this.Hair03.rotateAngleX = -0.14F;
//	    //arm 
//	  	this.ArmLeft01.rotateAngleY = 0F;
//	    this.ArmLeft02.rotateAngleX = 0F;
//		this.ArmRight02.rotateAngleX = 0F;
//		//leg
//		this.LegLeft01.rotateAngleY = 0F;
//		this.LegRight01.rotateAngleY = 0F;
//		//equip
//		this.EquipRdL01.isHidden = true;
//		this.EquipRdR01.isHidden = true;
//		
//    	//Body
//    	this.Head.rotateAngleX += 0.14F;
//	  	this.BodyMain.rotateAngleX = 0.4F;
//	  	this.Butt.rotateAngleX = -0.4F;
//	  	this.Butt.offsetZ = 0.19F;
//	  	this.BoobL.rotateAngleX -= 0.2F;
//	  	this.BoobR.rotateAngleX -= 0.2F;
//	    //arm 
//	  	this.ArmLeft01.rotateAngleX = -1.3F;
//	    this.ArmLeft01.rotateAngleZ = -0.1F;
//	    this.ArmLeft02.rotateAngleZ = 1.15F;
//		this.ArmRight01.rotateAngleX = -1.3F;
//		this.ArmRight01.rotateAngleY = 0F;
//		this.ArmRight01.rotateAngleZ = 0.1F;
//		this.ArmRight02.rotateAngleZ = -1.4F;
//		//leg
//		addk1 = -2.1232F;
//		addk2 = -2.0708F;
//		this.LegLeft01.rotateAngleZ = -0.2F;
//		this.LegLeft02.rotateAngleX = 1.34F;
//		this.LegRight01.rotateAngleZ = 0.2F;
//		this.LegRight02.rotateAngleX = 1.13F;
//		//hair
//		this.Hair01.rotateAngleX -= 0.2F;
//		this.Hair02.rotateAngleX -= 0.2F;
//		this.Hair03.rotateAngleX -= 0.1F;
//		
//		//移動頭髮避免穿過身體
//	    headZ = this.Head.rotateAngleZ * -0.5F;
//	    this.Hair01.rotateAngleZ = headZ;
//	  	this.Hair02.rotateAngleZ = headZ;
//	  	this.HairL01.rotateAngleZ = headZ - 0.0F;
//	  	this.HairL02.rotateAngleZ = headZ + 0.087F;
//	  	this.HairR01.rotateAngleZ = headZ + 0.0F;
//	  	this.HairR02.rotateAngleZ = headZ - 0.052F;
//	  	
//	    headX = this.Head.rotateAngleX * -0.5F;
//	    this.HairL01.rotateAngleX = headX - 0.5F;
//	  	this.HairL02.rotateAngleX = headX - 0.1F;
//	  	this.HairR01.rotateAngleX = headX - 0.5F;
//	  	this.HairR02.rotateAngleX = headX - 0.1F;
//	  	
//	    //leg motion
//	    this.LegLeft01.rotateAngleX = addk1;
//	    this.LegRight01.rotateAngleX = addk2;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
//  		float angleX = MathHelper.cos(f2*0.08F);
//  		float angleX1 = MathHelper.cos(f2*0.08F + 0.3F + f * 0.5F);
//  		float angleX2 = MathHelper.cos(f2*0.08F + 0.6F + f * 0.5F);
//  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
//  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
//  		float addk1 = 0F;
//  		float addk2 = 0F;
//  		float headX = 0F;
//  		float headZ = 0F;
//  		
//  		//水上漂浮
//  		if (ent.getShipDepth(0) > 0D || ent.getShipDepth(1) > 0D)
//  		{
//  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
//    	}
//  		
//  		//leg move parm
//  		addk1 = angleAdd1;
//	  	addk2 = angleAdd2 - 0.2F;
//
//  	    //移動頭部使其看人
//	  	this.Head.rotateAngleX = f4 * 0.014F; 	//上下角度
//	  	this.Head.rotateAngleY = f3 * 0.01F;	//左右角度
//	  	this.Head.rotateAngleZ = 0F;
//	  	headX = this.Head.rotateAngleX * -0.5F;
//	    //正常站立動作
//	    //胸部
//  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.7F;
//  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.7F;
//	  	//Body
//  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
//	  	this.BodyMain.rotateAngleX = -0.1745F;
//	  	this.BodyMain.rotateAngleZ = 0F;
//	  	this.Butt.rotateAngleX = 0.3142F;
//	  	this.Butt.offsetZ = 0F;
//	  	//hair
//	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.26F + headX;
//	  	this.Hair01.rotateAngleZ = 0F;
//	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.08F + headX;
//	  	this.Hair02.rotateAngleZ = 0F;
//	  	this.Hair03.rotateAngleX = -angleX2 * 0.07F - 0.14F;
//	  	this.Hair03.rotateAngleZ = 0F;
//	    //arm 
//	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.8F + 0.2F;
//	  	this.ArmLeft01.rotateAngleY = 0F;
//	    this.ArmLeft01.rotateAngleZ = angleX * 0.08F - 0.2F;
//	    this.ArmLeft02.rotateAngleX = 0F;
//	    this.ArmLeft02.rotateAngleZ = 0F;
//	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.8F + 0.2F;
//		this.ArmRight01.rotateAngleZ = -angleX * 0.08F + 0.2F;
//		this.ArmRight02.rotateAngleX = 0F;
//		this.ArmRight02.rotateAngleZ = 0F;
//		//leg
//		this.LegLeft01.rotateAngleY = 0F;
//		this.LegLeft01.rotateAngleZ = 0.14F;
//		this.LegLeft02.rotateAngleX = 0F;
//		this.LegRight01.rotateAngleY = 0F;
//		this.LegRight01.rotateAngleZ = -0.14F;
//		this.LegRight02.rotateAngleX = 0F;
//		//equip
//		this.EquipRdL01.isHidden = true;
//		this.EquipRdR01.isHidden = true;
//
//	    if (ent.getIsSprinting() || f1 > 0.9F)
//	    {	//奔跑動作
//	    	//沒有特殊跑步動作
//  		}
//	    
//	    //head tilt angle
//	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
//	    
//	    //移動頭髮避免穿過身體
//	    headZ = this.Head.rotateAngleZ * -0.5F;
//	    this.Hair01.rotateAngleZ = headZ;
//	  	this.Hair02.rotateAngleZ = headZ;
//	  	this.HairL01.rotateAngleZ = headZ - 0.14F;
//	  	this.HairL02.rotateAngleZ = headZ + 0.087F;
//	  	this.HairR01.rotateAngleZ = headZ + 0.14F;
//	  	this.HairR02.rotateAngleZ = headZ - 0.052F;
//	    
//	    if (ent.getIsSneaking())
//	    {	//潛行, 蹲下動作
//	    	GlStateManager.translate(0F, 0.07F, 0F);
//	    	//Body
//	    	this.Head.rotateAngleX -= 0.6283F;
//		  	this.BodyMain.rotateAngleX = 0.8727F;
//		    //arm 
//		  	this.ArmLeft01.rotateAngleX = -0.35F;
//		    this.ArmLeft01.rotateAngleZ = 0.2618F;
//			this.ArmRight01.rotateAngleX = -0.35F;
//			this.ArmRight01.rotateAngleZ = -0.2618F;
//			//leg
//			addk1 -= 1.1F;
//			addk2 -= 1.1F;
//			//hair
//			this.Hair01.rotateAngleX += 0.37F;
//			this.Hair02.rotateAngleX += 0.23F;
//			this.Hair03.rotateAngleX -= 0.1F;
//  		}//end if sneaking
//  		
//	    if (ent.getIsSitting() && !ent.getIsRiding())
//	    {	//騎乘動作  	
//	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
//	    	{
//	    		GlStateManager.translate(0F, 0.27F, 0F);
//		    	//Body
//		    	this.Head.rotateAngleX += 0.14F;
//			  	this.BodyMain.rotateAngleX = -0.4363F;
//			  	this.BoobL.rotateAngleX -= 0.25F;
//			  	this.BoobR.rotateAngleX -= 0.25F;
//			    //arm 
//			  	this.ArmLeft01.rotateAngleX = -0.3142F;
//			    this.ArmLeft01.rotateAngleZ = 0.3490F;
//			    this.ArmLeft02.rotateAngleZ = 1.15F;
//				this.ArmRight01.rotateAngleX = -0.4363F;
//				this.ArmRight01.rotateAngleZ = -0.2793F;
//				this.ArmRight02.rotateAngleZ = -1.4F;
//				//leg
//				addk1 = -1.3090F;
//				addk2 = -1.7F;
//				this.LegLeft01.rotateAngleY = 0.3142F;
//				this.LegLeft02.rotateAngleX = 1.0472F;
//				this.LegRight01.rotateAngleY = -0.35F;
//				this.LegRight01.rotateAngleZ = -0.2618F;
//				this.LegRight02.rotateAngleX = 0.9F;
//				//hair
//				this.Hair01.rotateAngleX += 0.12F;
//				this.Hair02.rotateAngleX += 0.15F;
//				this.Hair03.rotateAngleX += 0.25F;
//	    	}
//	    	else
//	    	{
//	    		GlStateManager.translate(0F, 0.37F, 0F);
//		    	//Body
//		    	this.Head.rotateAngleX += 0.14F;
//			  	this.BodyMain.rotateAngleX = -0.5236F;
//			  	this.BoobL.rotateAngleX -= 0.2F;
//			  	this.BoobR.rotateAngleX -= 0.2F;
//			    //arm 
//			  	this.ArmLeft01.rotateAngleX = -0.4363F;
//			    this.ArmLeft01.rotateAngleZ = 0.3142F;
//				this.ArmRight01.rotateAngleX = -0.4363F;
//				this.ArmRight01.rotateAngleZ = -0.3142F;
//				//leg
//				addk1 = -1.6232F;
//				addk2 = -1.5708F;
//				this.LegLeft01.rotateAngleZ = -0.3142F;
//				this.LegLeft02.rotateAngleX = 1.34F;
//				this.LegRight01.rotateAngleZ = 0.35F;
//				this.LegRight02.rotateAngleX = 1.13F;
//				//hair
//				this.Hair01.rotateAngleX += 0.09F;
//				this.Hair02.rotateAngleX += 0.43F;
//				this.Hair03.rotateAngleX += 0.49F;
//	    	}
//  		}//end sitting
//	    
//	    if (ent.getIsRiding())
//	    {
//	    	if (((Entity)ent).getRidingEntity() instanceof BasicEntityMount)
//	    	{
//	    		if (ent.getIsSitting())
//	    		{
//	    			GlStateManager.translate(0F, 0.22F, 0.2F);
//	    			
//	    			if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
//	    			{
//				    	//Body
//				    	this.Head.rotateAngleX -= 0.3F;
//					  	this.BodyMain.rotateAngleX = -0.4363F;
//					  	this.BoobL.rotateAngleX -= 0.25F;
//					  	this.BoobR.rotateAngleX -= 0.25F;
//					    //arm
//					  	this.ArmLeft01.rotateAngleX = -0.3142F;
//					    this.ArmLeft01.rotateAngleZ = 0.3490F;
//					    this.ArmLeft02.rotateAngleZ = 1.15F;
//						this.ArmRight01.rotateAngleX = -0.4363F;
//						this.ArmRight01.rotateAngleZ = -0.2793F;
//						this.ArmRight02.rotateAngleZ = -1.4F;
//						//leg
//						addk1 = -1.3090F;
//						addk2 = -1.7F;
//						this.LegLeft01.rotateAngleY = 0.3142F;
//						this.LegLeft02.rotateAngleX = 1.0472F;
//						this.LegRight01.rotateAngleY = -0.35F;
//						this.LegRight01.rotateAngleZ = -0.2618F;
//						this.LegRight02.rotateAngleX = 0.9F;
//						//hair
//						this.Hair01.rotateAngleX += 0.12F;
//						this.Hair02.rotateAngleX += 0.15F;
//						this.Hair03.rotateAngleX += 0.25F;
//			    	}
//			    	else
//			    	{
//				    	//Body
//					  	this.BodyMain.rotateAngleX = -0.5236F;
//					  	this.BoobL.rotateAngleX -= 0.2F;
//					  	this.BoobR.rotateAngleX -= 0.2F;
//					    //arm 
//					  	this.ArmLeft01.rotateAngleX = -0.4363F;
//					    this.ArmLeft01.rotateAngleZ = 0.3142F;
//						this.ArmRight01.rotateAngleX = -0.4363F;
//						this.ArmRight01.rotateAngleZ = -0.3142F;
//						//leg
//						addk1 = -1.6232F;
//						addk2 = -1.5708F;
//						this.LegLeft01.rotateAngleZ = -0.3142F;
//						this.LegLeft02.rotateAngleX = 1.34F;
//						this.LegRight01.rotateAngleZ = 0.35F;
//						this.LegRight02.rotateAngleX = 1.13F;
//						//hair
//						this.Hair01.rotateAngleX += 0.09F;
//						this.Hair02.rotateAngleX += 0.43F;
//						this.Hair03.rotateAngleX += 0.49F;
//			    	}
//		    	}//end if sitting
//		    	else
//		    	{
//		    		//body
//		    		this.Head.rotateAngleX -= 0.1F;
//				    //arm 
//				  	this.ArmLeft01.rotateAngleX = 0.5F;
//				    this.ArmLeft01.rotateAngleZ = -1.2F;
//					this.ArmRight01.rotateAngleX = 0.5F;
//					this.ArmRight01.rotateAngleZ = 1.2F;
//					//leg
//					addk1 = -0.2618F;
//					addk2 = -0.35F;
//					this.LegRight02.rotateAngleX = 0.8727F;
//					//hair
//					this.Hair01.rotateAngleX += 0.45F;
//					this.Hair02.rotateAngleX += 0.43F;
//					this.Hair03.rotateAngleX += 0.49F;
//		    	}
//	    	}//end ship mount
//	    	else
//	    	{	//normal mount ex: cart
//	    		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
//	    		{
//	    			GlStateManager.translate(0F, 0.27F, 0F);
//			    	//Body
//			    	this.Head.rotateAngleX += 0.14F;
//				  	this.BodyMain.rotateAngleX = -0.4363F;
//				  	this.BoobL.rotateAngleX -= 0.25F;
//				  	this.BoobR.rotateAngleX -= 0.25F;
//				    //arm 
//				  	this.ArmLeft01.rotateAngleX = -0.3142F;
//				    this.ArmLeft01.rotateAngleZ = 0.3490F;
//				    this.ArmLeft02.rotateAngleZ = 1.15F;
//					this.ArmRight01.rotateAngleX = -0.4363F;
//					this.ArmRight01.rotateAngleZ = -0.2793F;
//					this.ArmRight02.rotateAngleZ = -1.4F;
//					//leg
//					addk1 = -1.3090F;
//					addk2 = -1.7F;
//					this.LegLeft01.rotateAngleY = 0.3142F;
//					this.LegLeft02.rotateAngleX = 1.0472F;
//					this.LegRight01.rotateAngleY = -0.35F;
//					this.LegRight01.rotateAngleZ = -0.2618F;
//					this.LegRight02.rotateAngleX = 0.9F;
//					//hair
//					this.Hair01.rotateAngleX += 0.12F;
//					this.Hair02.rotateAngleX += 0.15F;
//					this.Hair03.rotateAngleX += 0.25F;
//		    	}
//		    	else
//		    	{
//		    		GlStateManager.translate(0F, 0.37F, 0F);
//			    	//Body
//			    	this.Head.rotateAngleX += 0.14F;
//				  	this.BodyMain.rotateAngleX = -0.5236F;
//				  	this.BoobL.rotateAngleX -= 0.2F;
//				  	this.BoobR.rotateAngleX -= 0.2F;
//				    //arm 
//				  	this.ArmLeft01.rotateAngleX = -0.4363F;
//				    this.ArmLeft01.rotateAngleZ = 0.3142F;
//					this.ArmRight01.rotateAngleX = -0.4363F;
//					this.ArmRight01.rotateAngleZ = -0.3142F;
//					//leg
//					addk1 = -1.6232F;
//					addk2 = -1.5708F;
//					this.LegLeft01.rotateAngleZ = -0.3142F;
//					this.LegLeft02.rotateAngleX = 1.34F;
//					this.LegRight01.rotateAngleZ = 0.35F;
//					this.LegRight02.rotateAngleX = 1.13F;
//					//hair
//					this.Hair01.rotateAngleX += 0.09F;
//					this.Hair02.rotateAngleX += 0.43F;
//					this.Hair03.rotateAngleX += 0.49F;
//		    	}
//	    	}
//	    }//end ridding
//    
//	    //攻擊動作    
//	    if (ent.getAttackTick() > 0)
//	    {
//	    	if (ent.getAttackTick() > 25)
//	    	{
//		    	//jojo攻擊動作
//		    	if (ent.getStateEmotion(ID.S.State2) == ID.ModelState.EQUIP02a)
//		    	{
//		    		GlStateManager.translate(0F, 0.15F, 0F);
//			    	//Body
//			    	this.Head.rotateAngleY *= 0.8F;
//			    	this.Head.rotateAngleX = 0.4538F;
//				  	this.BodyMain.rotateAngleX = -1.0472F;
//				  	this.BodyMain.rotateAngleZ = -0.2094F;
//				    //arm 
//				  	this.ArmLeft01.rotateAngleX = -0.35F;
//				    this.ArmLeft01.rotateAngleZ = -0.35F;
//				    this.ArmLeft02.rotateAngleX = -0.5F;
//					this.ArmRight01.rotateAngleX = 1.2F;
//					this.ArmRight01.rotateAngleZ = 0.5236F;
//					this.ArmRight02.rotateAngleX = -0.35F;
//					//leg
//					addk1 = 0.5236F;
//					addk2 = 0.1745F;
//					this.LegLeft01.rotateAngleZ = 0.2618F;
//					this.LegLeft02.rotateAngleX = 0.5236F;
//					this.LegRight01.rotateAngleZ = 0.1745F;
//					this.LegRight02.rotateAngleX = 0.5236F;
//					//hair
//					this.Hair01.rotateAngleX += 0.09F;
//					this.Hair02.rotateAngleX += 0.43F;
//					this.Hair03.rotateAngleX += 0.49F;
//		    	}
//		    	else if (ent.getStateEmotion(ID.S.State2) == ID.ModelState.EQUIP01a)
//		    	{
//		    		//Body
//			    	this.Head.rotateAngleY *= 0.8F;
//			    	this.Head.rotateAngleX = 0.2094F;
//			    	this.Head.rotateAngleZ = -0.2618F;
//				  	this.BodyMain.rotateAngleX = -0.35F;
//				  	this.BodyMain.rotateAngleZ = 0.1745F;
//				    //arm 
//				  	this.ArmLeft01.rotateAngleX = -1.2217F;
//				  	this.ArmLeft01.rotateAngleY = 0.5236F;
//				    this.ArmLeft01.rotateAngleZ = -0.35F;
//				    this.ArmLeft02.rotateAngleX = -1.3963F;
//					this.ArmRight01.rotateAngleX = 0.7854F;
//					this.ArmRight01.rotateAngleZ = 0.5236F;
//					this.ArmRight02.rotateAngleX = -0.5236F;
//					//leg
//					addk1 = -0.2618F;
//					addk2 = 0.3142F;
//					this.LegLeft01.rotateAngleZ = -0.4363F;
//					this.LegLeft02.rotateAngleX = 0.2618F;
//					this.LegRight01.rotateAngleZ = 0.0873F;
//					//hair
//					this.Hair01.rotateAngleX += 0.09F;
//					this.Hair02.rotateAngleX += 0.43F;
//					this.Hair03.rotateAngleX += 0.49F;
//		    	}
//		    	else
//		    	{
//		    		//arm
//			    	this.ArmLeft01.rotateAngleX = -1.3F;
//			    	this.ArmLeft01.rotateAngleY = -0.7F;
//			    	this.ArmLeft01.rotateAngleZ = 0F;
//		    	}
//	    	}
//	    	//跑道顯示
//	    	setRoad(ent.getAttackTick());
//	    }
//	    
//	    //鬢毛調整
//	    headX = this.Head.rotateAngleX * -0.5F;
//	    this.HairL01.rotateAngleX = angleX * 0.03F + headX - 0.26F;
//	  	this.HairL02.rotateAngleX = -angleX1 * 0.04F + headX + 0.26F;
//	  	this.HairR01.rotateAngleX = angleX * 0.03F + headX - 0.26F;
//	  	this.HairR02.rotateAngleX = -angleX1 * 0.04F + headX + 0.26F;
//	  	
//	  	//swing arm
//	  	float f6 = ent.getSwingTime(f2 % 1F);
//	  	if (f6 != 0F)
//	  	{
//	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
//	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
//	        this.ArmRight01.rotateAngleX = -0.3F;
//			this.ArmRight01.rotateAngleY = 0F;
//			this.ArmRight01.rotateAngleZ = -0.1F;
//	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
//	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180;
//	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
//	        this.ArmRight02.rotateAngleX = 0F;
//	        this.ArmRight02.rotateAngleZ = 0F;
//	  	}
//	  	
//	    //leg motion
//	    this.LegLeft01.rotateAngleX = addk1;
//	    this.LegRight01.rotateAngleX = addk2;
	}
    
    
}