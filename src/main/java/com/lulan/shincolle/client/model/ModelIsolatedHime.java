package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.handler.EventHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

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
    public ModelRenderer Hair01;
    public ModelRenderer HatBase;
    public ModelRenderer HairU01;
    public ModelRenderer Ahoke;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer Hat01;
    public ModelRenderer Hat03;
    public ModelRenderer Hat05;
    public ModelRenderer HeadH1;
    public ModelRenderer HeadH2;
    public ModelRenderer HeadH3;
    public ModelRenderer HeadH4;
    public ModelRenderer HeadH5;
    public ModelRenderer HeadH6;
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
    public ModelRenderer EquipRdL01;
    public ModelRenderer EquipRdL02;
    public ModelRenderer EquipRdL03;
    public ModelRenderer EquipRdL04;
    public ModelRenderer EquipRdL05;
    public ModelRenderer EquipRdL06;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowHatBase;
    
    
    public ModelIsolatedHime()
    {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.scale = 0.38F;
        this.offsetY = 2.59F;
        this.offsetItem = new float[] {0.08F, 1.02F, -0.07F};
        this.offsetBlock = new float[] {0.08F, 1.02F, -0.07F};
        
        this.setDefaultFaceModel();
        
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.15707963267948966F, 0.0F, 0.08726646259971647F);
        this.LegArmor01a = new ModelRenderer(this, 0, 3);
        this.LegArmor01a.setRotationPoint(0.0F, 13.0F, -5.0F);
        this.LegArmor01a.addBox(-3.5F, -4.0F, 0.0F, 7, 4, 3, 0.0F);
        this.setRotateAngle(LegArmor01a, -0.2617993877991494F, 0.0F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 84);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.2617993877991494F, 0.0F, 0.2792526803190927F);
        this.Hat02i = new ModelRenderer(this, 30, 6);
        this.Hat02i.setRotationPoint(3.8F, -0.5F, 2.3F);
        this.Hat02i.addBox(-2.0F, -3.0F, 0.0F, 4, 2, 7, 0.0F);
        this.setRotateAngle(Hat02i, 0.05235987755982988F, -0.05235987755982988F, -3.1066860685499065F);
        this.Skirt01 = new ModelRenderer(this, 128, 0);
        this.Skirt01.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.Skirt01.addBox(-9.0F, 0.0F, -6.2F, 18, 4, 10, 0.0F);
        this.setRotateAngle(Skirt01, -0.08726646259971647F, 0.0F, 0.0F);
        this.HeadH1 = new ModelRenderer(this, 0, 0);
        this.HeadH1.mirror = true;
        this.HeadH1.setRotationPoint(-8.5F, -2.0F, 2.0F);
        this.HeadH1.addBox(-2.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.setRotateAngle(HeadH1, 0.17453292519943295F, 0.0F, 0.4363323129985824F);
        this.HeadH2 = new ModelRenderer(this, 33, 102);
        this.HeadH2.setRotationPoint(-1.8F, 0.0F, 0.0F);
        this.HeadH2.addBox(-1.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(HeadH2, 0.0F, 0.0F, 0.12217304763960307F);
        this.Hat04b = new ModelRenderer(this, 42, 10);
        this.Hat04b.setRotationPoint(3.2F, -2.0F, -0.2F);
        this.Hat04b.addBox(-2.0F, -3.0F, -10.0F, 4, 2, 10, 0.0F);
        this.setRotateAngle(Hat04b, -0.05235987755982988F, 0.08726646259971647F, -3.07177948351002F);
        this.Cloth01c2 = new ModelRenderer(this, 0, 16);
        this.Cloth01c2.mirror = true;
        this.Cloth01c2.setRotationPoint(2.0F, 1.6F, -0.7F);
        this.Cloth01c2.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Cloth01c2, -0.7330382858376184F, -0.13962634015954636F, -0.17453292519943295F);
        this.Hat02h_1 = new ModelRenderer(this, 60, 2);
        this.Hat02h_1.setRotationPoint(-6.9F, 1.2F, 2.4F);
        this.Hat02h_1.addBox(-2.0F, -3.0F, 0.0F, 4, 3, 7, 0.0F);
        this.setRotateAngle(Hat02h_1, 0.05235987755982988F, -0.06981317007977318F, -0.296705972839036F);
        this.Cloth03a = new ModelRenderer(this, 128, 50);
        this.Cloth03a.setRotationPoint(2.5F, 3.5F, -2.5F);
        this.Cloth03a.addBox(-3.0F, 0.0F, -3.0F, 6, 7, 6, 0.0F);
        this.Hat04h = new ModelRenderer(this, 60, 2);
        this.Hat04h.setRotationPoint(6.9F, 1.2F, 2.4F);
        this.Hat04h.addBox(-2.0F, -3.0F, 0.0F, 4, 3, 7, 0.0F);
        this.setRotateAngle(Hat04h, 0.05235987755982988F, 0.06981317007977318F, 0.296705972839036F);
        this.LegArmor02b = new ModelRenderer(this, 1, 0);
        this.LegArmor02b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LegArmor02b.addBox(-2.5F, 0.0F, 0.0F, 5, 4, 3, 0.0F);
        this.setRotateAngle(LegArmor02b, 0.6108652381980153F, 0.0F, 0.0F);
        this.Hat02b_1 = new ModelRenderer(this, 42, 10);
        this.Hat02b_1.setRotationPoint(4.9F, -1.5F, -0.2F);
        this.Hat02b_1.addBox(-2.0F, -3.0F, -10.0F, 4, 2, 10, 0.0F);
        this.setRotateAngle(Hat02b_1, 0.017453292519943295F, -0.017453292519943295F, -2.792526803190927F);
        this.HeadH6 = new ModelRenderer(this, 0, 900);
        this.HeadH6.setRotationPoint(0.7F, 0.0F, 0.0F);
        this.HeadH6.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(HeadH6, 0.0F, 0.08726646259971647F, -0.17453292519943295F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.HeadH4 = new ModelRenderer(this, 0, 0);
        this.HeadH4.mirror = true;
        this.HeadH4.setRotationPoint(8.5F, -2.0F, 2.0F);
        this.HeadH4.addBox(0.0F, -2.0F, -2.0F, 2, 4, 4, 0.0F);
        this.setRotateAngle(HeadH4, 0.17453292519943295F, 0.0F, -0.4363323129985824F);
        this.Hat02b = new ModelRenderer(this, 42, 10);
        this.Hat02b.setRotationPoint(4.2F, -0.7F, -0.6F);
        this.Hat02b.addBox(-2.0F, -3.0F, -10.0F, 4, 2, 10, 0.0F);
        this.setRotateAngle(Hat02b, -0.05235987755982988F, 0.017453292519943295F, -2.96705972839036F);
        this.Cloth01c = new ModelRenderer(this, 0, 16);
        this.Cloth01c.setRotationPoint(-2.0F, 1.6F, -0.7F);
        this.Cloth01c.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Cloth01c, -0.7853981633974483F, 0.13962634015954636F, 0.17453292519943295F);
        this.Hat01 = new ModelRenderer(this, 88, 23);
        this.Hat01.setRotationPoint(0.0F, -3.6F, 1.0F);
        this.Hat01.addBox(-8.5F, 0.0F, -0.5F, 17, 4, 3, 0.0F);
        this.Cloth03b = new ModelRenderer(this, 128, 50);
        this.Cloth03b.mirror = true;
        this.Cloth03b.setRotationPoint(-2.5F, 3.5F, -2.5F);
        this.Cloth03b.addBox(-3.0F, 0.0F, -3.0F, 6, 7, 6, 0.0F);
        this.Butt = new ModelRenderer(this, 82, 0);
        this.Butt.setRotationPoint(0.0F, 2.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.Hat02f = new ModelRenderer(this, 60, 2);
        this.Hat02f.setRotationPoint(0.0F, 1.7F, 2.6F);
        this.Hat02f.addBox(-2.0F, -3.0F, 0.0F, 4, 3, 7, 0.0F);
        this.setRotateAngle(Hat02f, 0.08726646259971647F, 0.0F, 0.0F);
        this.Hat02e = new ModelRenderer(this, 60, 15);
        this.Hat02e.setRotationPoint(-7.6F, 2.0F, -0.6F);
        this.Hat02e.addBox(-2.0F, -3.0F, -10.0F, 4, 3, 10, 0.0F);
        this.setRotateAngle(Hat02e, 0.05235987755982988F, 0.03490658503988659F, -0.5759586531581287F);
        this.Hat06a = new ModelRenderer(this, 60, 15);
        this.Hat06a.setRotationPoint(0.5F, 0.5F, -0.5F);
        this.Hat06a.addBox(-2.0F, -3.0F, -10.0F, 4, 3, 10, 0.0F);
        this.setRotateAngle(Hat06a, -0.08726646259971647F, 0.03490658503988659F, 0.06981317007977318F);
        this.LegRight02b = new ModelRenderer(this, 128, 63);
        this.LegRight02b.mirror = true;
        this.LegRight02b.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02b.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Cloth02c = new ModelRenderer(this, 128, 85);
        this.Cloth02c.mirror = true;
        this.Cloth02c.setRotationPoint(-0.9F, -1.5F, 0.0F);
        this.Cloth02c.addBox(-3.0F, 0.0F, -3.5F, 6, 6, 7, 0.0F);
        this.setRotateAngle(Cloth02c, 0.0F, 0.0F, -0.05235987755982988F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 84);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, -0.05235987755982988F, 0.0F, -0.2792526803190927F);
        this.LegRight02a = new ModelRenderer(this, 0, 63);
        this.LegRight02a.mirror = true;
        this.LegRight02a.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02a.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.LegArmor02c = new ModelRenderer(this, 0, 3);
        this.LegArmor02c.setRotationPoint(0.0F, 2.0F, 0.2F);
        this.LegArmor02c.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LegArmor02c, 0.6108652381980153F, 0.0F, 0.0F);
        this.Hat04d = new ModelRenderer(this, 42, 10);
        this.Hat04d.setRotationPoint(-4.9F, -1.5F, -0.2F);
        this.Hat04d.addBox(-2.0F, -3.0F, -10.0F, 4, 2, 10, 0.0F);
        this.setRotateAngle(Hat04d, 0.017453292519943295F, 0.017453292519943295F, 2.792526803190927F);
        this.LegArmor01b = new ModelRenderer(this, 12, 0);
        this.LegArmor01b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LegArmor01b.addBox(-2.5F, 0.0F, 0.0F, 5, 4, 3, 0.0F);
        this.setRotateAngle(LegArmor01b, 0.6108652381980153F, 0.0F, 0.0F);
        this.Hat02g = new ModelRenderer(this, 30, 6);
        this.Hat02g.setRotationPoint(-3.8F, -0.5F, 2.3F);
        this.Hat02g.addBox(-2.0F, -3.0F, 0.0F, 4, 2, 7, 0.0F);
        this.setRotateAngle(Hat02g, 0.05235987755982988F, 0.05235987755982988F, 3.1066860685499065F);
        this.Cloth01b2 = new ModelRenderer(this, 51, 0);
        this.Cloth01b2.setRotationPoint(0.5F, 0.3F, 0.3F);
        this.Cloth01b2.addBox(0.0F, -3.0F, -1.0F, 6, 6, 2, 0.0F);
        this.setRotateAngle(Cloth01b2, 0.08726646259971647F, 0.17453292519943295F, -0.1488765851951163F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 63);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.Hair01 = new ModelRenderer(this, 50, 30);
        this.Hair01.setRotationPoint(0.0F, -6.0F, 2.0F);
        this.Hair01.addBox(-7.5F, 0.0F, -4.0F, 15, 17, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.20943951023931953F, 0.0F, 0.0F);
        this.Hat02a = new ModelRenderer(this, 60, 15);
        this.Hat02a.setRotationPoint(0.0F, 1.5F, -0.7F);
        this.Hat02a.addBox(-2.0F, -3.0F, -10.0F, 4, 3, 10, 0.0F);
        this.setRotateAngle(Hat02a, -0.06981317007977318F, 0.0F, 0.0F);
        this.Hat02i_1 = new ModelRenderer(this, 30, 6);
        this.Hat02i_1.setRotationPoint(4.3F, -1.0F, 2.5F);
        this.Hat02i_1.addBox(-2.0F, -3.0F, 0.0F, 4, 2, 7, 0.0F);
        this.setRotateAngle(Hat02i_1, 0.0F, 0.03490658503988659F, -2.96705972839036F);
        this.LegArmor01c = new ModelRenderer(this, 0, 3);
        this.LegArmor01c.setRotationPoint(0.0F, 2.0F, 0.2F);
        this.LegArmor01c.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(LegArmor01c, 0.6108652381980153F, 0.0F, 0.0F);
        this.Hat04a = new ModelRenderer(this, 60, 15);
        this.Hat04a.setRotationPoint(-0.5F, 0.5F, -0.5F);
        this.Hat04a.addBox(-2.0F, -3.0F, -10.0F, 4, 3, 10, 0.0F);
        this.setRotateAngle(Hat04a, -0.08726646259971647F, -0.03490658503988659F, -0.06981317007977318F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.Hat04c = new ModelRenderer(this, 60, 15);
        this.Hat04c.setRotationPoint(6.4F, 1.0F, -0.1F);
        this.Hat04c.addBox(-2.0F, -3.0F, -10.0F, 4, 3, 10, 0.0F);
        this.setRotateAngle(Hat04c, 0.06981317007977318F, -0.13962634015954636F, 0.2617993877991494F);
        this.Cloth01b = new ModelRenderer(this, 51, 0);
        this.Cloth01b.setRotationPoint(-0.5F, 0.3F, 0.3F);
        this.Cloth01b.addBox(-6.0F, -3.0F, -1.0F, 6, 6, 2, 0.0F);
        this.setRotateAngle(Cloth01b, 0.08726646259971647F, -0.17453292519943295F, 0.13962634015954636F);
        this.Cloth02a = new ModelRenderer(this, 128, 99);
        this.Cloth02a.setRotationPoint(0.0F, -11.5F, -0.6F);
        this.Cloth02a.addBox(-7.0F, 0.0F, -4.0F, 14, 7, 8, 0.0F);
        this.setRotateAngle(Cloth02a, 0.05235987755982988F, 0.0F, 0.0F);
        this.Hair03 = new ModelRenderer(this, 0, 15);
        this.Hair03.setRotationPoint(0.0F, 12.5F, -0.1F);
        this.Hair03.addBox(-7.5F, 0.0F, -5.5F, 15, 15, 8, 0.0F);
        this.setRotateAngle(Hair03, -0.2617993877991494F, 0.0F, 0.0F);
        this.Hair02 = new ModelRenderer(this, 0, 38);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 2.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -6.0F, 16, 16, 9, 0.0F);
        this.setRotateAngle(Hair02, 0.12217304763960307F, 0.0F, 0.0F);
        this.Cloth02b = new ModelRenderer(this, 128, 85);
        this.Cloth02b.setRotationPoint(0.9F, -1.5F, 0.0F);
        this.Cloth02b.addBox(-3.0F, 0.0F, -3.5F, 6, 6, 7, 0.0F);
        this.setRotateAngle(Cloth02b, 0.0F, 0.0F, 0.05235987755982988F);
        this.Cloth01a = new ModelRenderer(this, 51, 2);
        this.Cloth01a.setRotationPoint(0.0F, 0.0F, -7.9F);
        this.Cloth01a.addBox(-1.0F, -2.5F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Cloth01a, 0.7853981633974483F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 63);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.Hat04e = new ModelRenderer(this, 60, 2);
        this.Hat04e.setRotationPoint(-0.2F, 1.1F, 2.8F);
        this.Hat04e.addBox(-2.0F, -3.0F, 0.0F, 4, 3, 7, 0.0F);
        this.setRotateAngle(Hat04e, 0.13962634015954636F, 0.0F, 0.03490658503988659F);
        this.LegLeft02b = new ModelRenderer(this, 128, 63);
        this.LegLeft02b.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02b.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Hat02j = new ModelRenderer(this, 60, 2);
        this.Hat02j.setRotationPoint(7.2F, 2.4F, 2.6F);
        this.Hat02j.addBox(-2.0F, -3.0F, 0.0F, 4, 3, 7, 0.0F);
        this.setRotateAngle(Hat02j, 0.08726646259971647F, 0.05235987755982988F, 0.5235987755982988F);
        this.Hat02d = new ModelRenderer(this, 42, 10);
        this.Hat02d.setRotationPoint(-4.2F, -0.7F, -0.6F);
        this.Hat02d.addBox(-2.0F, -3.0F, -10.0F, 4, 2, 10, 0.0F);
        this.setRotateAngle(Hat02d, -0.05235987755982988F, -0.017453292519943295F, 2.96705972839036F);
        this.Ahoke = new ModelRenderer(this, 106, 31);
        this.Ahoke.setRotationPoint(-0.5F, -7.0F, -6.0F);
        this.Ahoke.addBox(0.0F, -6.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.5235987755982988F, 0.6981317007977318F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 105);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 15, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 103, 35);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.Neck.addBox(-2.5F, -2.0F, -3.0F, 5, 2, 5, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.Hat03 = new ModelRenderer(this, 88, 23);
        this.Hat03.setRotationPoint(10.0F, 5.5F, 1.3F);
        this.Hat03.addBox(-8.5F, 0.0F, -0.5F, 17, 4, 3, 0.0F);
        this.setRotateAngle(Hat03, 0.0F, -0.05235987755982988F, 1.5707963267948966F);
        this.Hat02c = new ModelRenderer(this, 60, 15);
        this.Hat02c.setRotationPoint(7.6F, 2.0F, -0.6F);
        this.Hat02c.addBox(-2.0F, -3.0F, -10.0F, 4, 3, 10, 0.0F);
        this.setRotateAngle(Hat02c, 0.05235987755982988F, -0.03490658503988659F, 0.5759586531581287F);
        this.LegArmor02a = new ModelRenderer(this, 10, 0);
        this.LegArmor02a.setRotationPoint(0.0F, 13.0F, -5.0F);
        this.LegArmor02a.addBox(-3.5F, -4.0F, 0.0F, 7, 4, 3, 0.0F);
        this.setRotateAngle(LegArmor02a, -0.2617993877991494F, 0.0F, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.Hat02f_1 = new ModelRenderer(this, 60, 2);
        this.Hat02f_1.setRotationPoint(0.2F, 1.1F, 2.8F);
        this.Hat02f_1.addBox(-2.0F, -3.0F, 0.0F, 4, 3, 7, 0.0F);
        this.setRotateAngle(Hat02f_1, 0.13962634015954636F, 0.0F, 0.03490658503988659F);
        this.HeadH5 = new ModelRenderer(this, 33, 102);
        this.HeadH5.setRotationPoint(1.8F, 0.0F, 0.0F);
        this.HeadH5.addBox(0.0F, -1.5F, -1.5F, 1, 3, 3, 0.0F);
        this.setRotateAngle(HeadH5, 0.0F, 0.0F, -0.12217304763960307F);
        this.Hat02e_1 = new ModelRenderer(this, 60, 15);
        this.Hat02e_1.setRotationPoint(-6.4F, 1.0F, -0.1F);
        this.Hat02e_1.addBox(-2.0F, -3.0F, -10.0F, 4, 3, 10, 0.0F);
        this.setRotateAngle(Hat02e_1, -0.06981317007977318F, 0.13962634015954636F, -0.2617993877991494F);
        this.HatBase = new ModelRenderer(this, 0, 0);
        this.HatBase.setRotationPoint(0.0F, -14.6F, -2.0F);
        this.HatBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(HatBase, -0.05235987755982988F, 0.0F, 0.0F);
        this.Hat05 = new ModelRenderer(this, 88, 23);
        this.Hat05.setRotationPoint(-10.0F, 5.5F, 1.3F);
        this.Hat05.addBox(-8.5F, 0.0F, -0.5F, 17, 4, 3, 0.0F);
        this.setRotateAngle(Hat05, 0.0F, 0.05235987755982988F, -1.5707963267948966F);
        this.Hat04f = new ModelRenderer(this, 30, 6);
        this.Hat04f.setRotationPoint(-4.3F, -1.0F, 2.5F);
        this.Hat04f.addBox(-2.0F, -3.0F, 0.0F, 4, 2, 7, 0.0F);
        this.setRotateAngle(Hat04f, 0.0F, -0.03490658503988659F, 2.96705972839036F);
        this.Skirt02 = new ModelRenderer(this, 128, 15);
        this.Skirt02.setRotationPoint(0.0F, 2.7F, -1.0F);
        this.Skirt02.addBox(-10.5F, 0.0F, -6.0F, 21, 4, 12, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.296705972839036F, 0.0F, -0.08726646259971647F);
        this.LegLeft02a = new ModelRenderer(this, 0, 63);
        this.LegLeft02a.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02a.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Hat02h = new ModelRenderer(this, 60, 2);
        this.Hat02h.setRotationPoint(-7.2F, 2.4F, 2.6F);
        this.Hat02h.addBox(-2.0F, -3.0F, 0.0F, 4, 3, 7, 0.0F);
        this.setRotateAngle(Hat02h, 0.08726646259971647F, -0.05235987755982988F, -0.5235987755982988F);
        this.Hat02g_1 = new ModelRenderer(this, 30, 6);
        this.Hat02g_1.setRotationPoint(-3.5F, -0.6F, 2.2F);
        this.Hat02g_1.addBox(-2.0F, -3.0F, 0.0F, 4, 2, 7, 0.0F);
        this.setRotateAngle(Hat02g_1, -0.05235987755982988F, 0.03490658503988659F, 3.07177948351002F);
        this.Hat02d_1 = new ModelRenderer(this, 42, 10);
        this.Hat02d_1.setRotationPoint(-3.2F, -2.0F, -0.2F);
        this.Hat02d_1.addBox(-2.0F, -3.0F, -10.0F, 4, 2, 10, 0.0F);
        this.setRotateAngle(Hat02d_1, -0.05235987755982988F, -0.08726646259971647F, 3.07177948351002F);
        this.Skirt03 = new ModelRenderer(this, 128, 32);
        this.Skirt03.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.Skirt03.addBox(-11.5F, 0.0F, -6.5F, 23, 4, 13, 0.0F);
        this.setRotateAngle(Skirt03, -0.05235987755982988F, 0.0F, 0.0F);
        this.HeadH3 = new ModelRenderer(this, 0, 0);
        this.HeadH3.setRotationPoint(-0.7F, 0.0F, 0.0F);
        this.HeadH3.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(HeadH3, 0.0F, -0.08726646259971647F, 0.17453292519943295F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.Hat04g = new ModelRenderer(this, 30, 6);
        this.Hat04g.setRotationPoint(3.5F, -0.6F, 2.2F);
        this.Hat04g.addBox(-2.0F, -3.0F, 0.0F, 4, 2, 7, 0.0F);
        this.setRotateAngle(Hat04g, -0.05235987755982988F, -0.03490658503988659F, -3.07177948351002F);
        this.EquipRdL01 = new ModelRenderer(this, 128, 115);
        this.EquipRdL01.setRotationPoint(4.0F, -6.0F, 5.0F);
        this.EquipRdL01.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL01, 1.5707963267948966F, -0.17453292519943295F, -0.7853981633974483F);
        this.EquipRdL02 = new ModelRenderer(this, 128, 115);
        this.EquipRdL02.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL02.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL02, -0.4363323129985824F, 0.0F, 0.0F);
        this.EquipRdL03 = new ModelRenderer(this, 128, 115);
        this.EquipRdL03.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL03.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL03, -0.6981317007977318F, 0.0F, 0.0F);
        this.EquipRdL04 = new ModelRenderer(this, 128, 115);
        this.EquipRdL04.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL04.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL04, -0.3490658503988659F, 0.0F, 0.0F);
        this.EquipRdL05 = new ModelRenderer(this, 128, 115);
        this.EquipRdL05.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL05.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL05, -0.2617993877991494F, 0.0F, 0.0F);
        this.EquipRdL06 = new ModelRenderer(this, 128, 115);
        this.EquipRdL06.setRotationPoint(0.0F, 0.0F, -11.0F);
        this.EquipRdL06.addBox(-3.5F, 0.0F, -12.0F, 7, 1, 12, 0.0F);
        this.setRotateAngle(EquipRdL06, -0.17453292519943295F, 0.0F, 0.0F);
        this.Butt.addChild(this.LegLeft01);
        this.Hat01.addChild(this.Hat02d);
        this.Cloth01a.addChild(this.Cloth01b2);
        this.Hair.addChild(this.Ahoke);
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
        this.Cloth01a.addChild(this.Cloth01c);
        this.ArmRight02.addChild(this.Cloth03a);
        this.BodyMain.addChild(this.ArmLeft01);
        this.ArmLeft01.addChild(this.Cloth02b);
        this.Hat05.addChild(this.Hat02e_1);
        this.Hat05.addChild(this.Hat06a);
        this.HatBase.addChild(this.Hat03);
        this.Head.addChild(this.HairMain);
        this.Cloth01a.addChild(this.Cloth01b);
        this.Hat03.addChild(this.Hat04e);
        this.Neck.addChild(this.Head);
        this.Hat01.addChild(this.Hat02g);
        this.Hat01.addChild(this.Hat02h);
        this.LegArmor02b.addChild(this.LegArmor02c);
        this.LegArmor01a.addChild(this.LegArmor01b);
        this.BodyMain.addChild(this.Neck);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.BodyMain.addChild(this.ArmRight01);
        this.LegLeft01.addChild(this.LegLeft02a);
        this.LegArmor02a.addChild(this.LegArmor02b);
        this.Hat05.addChild(this.Hat02g_1);
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
        this.Hat05.addChild(this.Hat02i_1);
        this.HatBase.addChild(this.Hat05);
        this.LegRight01.addChild(this.LegArmor02a);
        this.BodyMain.addChild(this.Butt);
        this.Skirt01.addChild(this.Skirt02);
        this.Hat03.addChild(this.Hat04h);
        this.Hat05.addChild(this.Hat02h_1);
        this.BodyMain.addChild(this.Cloth02a);
        this.Head.addChild(this.Hair01);
        this.Hat03.addChild(this.Hat04b);
        this.Head.addChild(this.Hair);
        this.Hat05.addChild(this.Hat02f_1);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Hat01.addChild(this.Hat02f);
        this.ArmLeft02.addChild(this.Cloth03b);
        this.Hat01.addChild(this.Hat02j);
        this.Neck.addChild(this.Cloth01a);
        this.Butt.addChild(this.Skirt01);
        this.Butt.addChild(this.LegRight01);
        this.Hat03.addChild(this.Hat04c);
        this.Hat05.addChild(this.Hat02d_1);
        this.Cloth01a.addChild(this.Cloth01c2);
        this.Head.addChild(this.HatBase);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.setRotateAngle(GlowNeck, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.GlowHatBase = new ModelRenderer(this, 0, 0);
        this.GlowHatBase.setRotationPoint(0.0F, -14.6F, -2.0F);
        this.setRotateAngle(GlowHatBase, -0.05235987755982988F, 0.0F, 0.0F);
        
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
        this.GlowHead.addChild(this.GlowHatBase);
        this.GlowHatBase.addChild(this.HeadH1);
        this.HeadH1.addChild(this.HeadH2);
        this.HeadH2.addChild(this.HeadH3);
        this.GlowHatBase.addChild(this.HeadH4);
        this.HeadH4.addChild(this.HeadH5);
        this.HeadH5.addChild(this.HeadH6);
        this.GlowBodyMain.addChild(this.EquipRdL01);
        this.EquipRdL01.addChild(this.EquipRdL02);
        this.EquipRdL02.addChild(this.EquipRdL03);
        this.EquipRdL03.addChild(this.EquipRdL04);
        this.EquipRdL04.addChild(this.EquipRdL05);
        this.EquipRdL05.addChild(this.EquipRdL06);
        
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
    	
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
    }
    
	@Override
	public void showEquip(IShipEmotion ent)
	{
		int state = ent.getStateEmotion(ID.S.State);
		
		boolean flag = !EmotionHelper.checkModelState(1, state);	//hat
		this.HatBase.isHidden = flag;
		this.GlowHatBase.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(2, state);	//horn
		this.HeadH1.isHidden = flag;
		this.HeadH4.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(3, state);	//bowtie
		this.Cloth01a.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(4, state);	//shawl
		this.Cloth02a.isHidden = flag;
		this.Cloth02b.isHidden = flag;
		this.Cloth02c.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(5, state);	//shawl
		this.Cloth03a.isHidden = flag;
		this.Cloth03b.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(6, state);	//leg
		this.LegLeft02b.isHidden = flag;
		this.LegRight02b.isHidden = flag;
		this.LegLeft02a.isHidden = !flag;
		this.LegRight02a.isHidden = !flag;
		
		flag = !EmotionHelper.checkModelState(7, state);	//leg armor
		this.LegArmor01a.isHidden = flag;
		this.LegArmor02a.isHidden = flag;
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
		
		this.LegLeft02b.rotateAngleX = this.LegLeft02a.rotateAngleX;
		this.LegLeft02b.rotateAngleY = this.LegLeft02a.rotateAngleY;
		this.LegLeft02b.rotateAngleZ = this.LegLeft02a.rotateAngleZ;
		this.LegLeft02b.offsetX = this.LegLeft02a.offsetX;
		this.LegLeft02b.offsetY = this.LegLeft02a.offsetY;
		this.LegLeft02b.offsetZ = this.LegLeft02a.offsetZ;
		this.LegRight02b.rotateAngleX = this.LegRight02a.rotateAngleX;
		this.LegRight02b.rotateAngleY = this.LegRight02a.rotateAngleY;
		this.LegRight02b.rotateAngleZ = this.LegRight02a.rotateAngleZ;
		this.LegRight02b.offsetX = this.LegRight02a.offsetX;
		this.LegRight02b.offsetY = this.LegRight02a.offsetY;
		this.LegRight02b.offsetZ = this.LegRight02a.offsetZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.43F, 0F);
  		this.setFaceHungry(ent);

	  	//Body
    	this.Head.rotateAngleX = 0.5F;
    	this.Head.rotateAngleY = 0F;
    	this.Head.rotateAngleZ = 0F;
  	    this.Ahoke.rotateAngleY = 0.45F;
	  	this.BodyMain.rotateAngleX = 0.5F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
    	this.Butt.rotateAngleX = -0.85F;
    	this.Butt.offsetY = 0F;
    	this.Butt.offsetZ = 0F;
    	//cloth
	  	this.Skirt01.rotateAngleX = -0.087F;
	  	this.Skirt02.rotateAngleX = -0.087F;
	  	this.Skirt03.rotateAngleX = -0.052F;
	  	this.Cloth01a.offsetY = 0.092F;
	  	this.Cloth01a.offsetZ = 0.1F;
    	this.Cloth01c.rotateAngleX = -0.79F;
    	this.Cloth01c2.rotateAngleX = -0.73F;
    	//hair
    	this.Hair01.rotateAngleX = -0.12F;
    	this.Hair01.rotateAngleY = 0F;
    	this.Hair01.rotateAngleZ = 0F;
    	this.Hair02.rotateAngleX = -0.33F;
    	this.Hair02.rotateAngleY = 0F;
    	this.Hair02.rotateAngleZ = 0F;
    	this.Hair03.rotateAngleX = -0.38F;
    	this.Hair03.rotateAngleY = 0F;
    	this.Hair03.rotateAngleZ = 0F;
	    //arm 
		this.ArmLeft01.rotateAngleX = -1.1F;
		this.ArmLeft01.rotateAngleY = 0.39F;
		this.ArmLeft01.rotateAngleZ = -0.05F;
		this.ArmLeft02.rotateAngleX = -1.46F;
		this.ArmLeft02.rotateAngleZ = 0F;
		this.ArmLeft02.offsetX = 0F;
		this.ArmLeft02.offsetZ = 0F;
		this.ArmRight01.rotateAngleX = -1.1F;
	    this.ArmRight01.rotateAngleY = -0.39F;
		this.ArmRight01.rotateAngleZ = 0.05F;
		this.ArmRight02.rotateAngleX = -1.46F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
		this.ArmRight02.offsetZ = 0F;
		//leg
	    this.LegLeft01.rotateAngleX = -1.96F;
		this.LegLeft01.rotateAngleY = -0.6F;
		this.LegLeft01.rotateAngleZ = 1.56F;
		this.LegLeft01.offsetY = 0F;
		this.LegLeft01.offsetZ = 0F;
		this.LegLeft02a.rotateAngleX = 2.1F;
		this.LegLeft02a.rotateAngleY = 0F;
		this.LegLeft02a.rotateAngleZ = 0F;
		this.LegLeft02a.offsetX = 0F;
		this.LegLeft02a.offsetY = 0F;
		this.LegLeft02a.offsetZ = 0.37F;
		this.LegRight01.rotateAngleX = -0.96F;
		this.LegRight01.rotateAngleY = 0.36F;
		this.LegRight01.rotateAngleZ = 0.14F;
		this.LegRight01.offsetY = 0F;
		this.LegRight01.offsetZ = 0F;
		this.LegRight02a.rotateAngleX = 1.2217F;
		this.LegRight02a.rotateAngleY = -1.2217F;
		this.LegRight02a.rotateAngleZ = 1.0472F;
		this.LegRight02a.offsetX = 0F;
		this.LegRight02a.offsetY = -0.06F;
		this.LegRight02a.offsetZ = 0F;
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
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = angleAdd1 - 0.157F;
	  	addk2 = angleAdd2 - 0.296F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F; 	//上下角度
	  	this.Head.rotateAngleY = f3 * 0.01F;	//左右角度
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.35F;
    	this.Butt.offsetY = 0F;
    	this.Butt.offsetZ = 0F;
    	//cloth
	  	this.Skirt01.rotateAngleX = -0.087F;
	  	this.Skirt02.rotateAngleX = -0.087F;
	  	this.Skirt03.rotateAngleX = -0.052F;
	  	this.Cloth01a.rotateAngleX = angleX * 0.08F + 0.79F;
	  	this.Cloth01a.offsetY = 0.092F;
	  	this.Cloth01a.offsetZ = 0.1F;
    	this.Cloth01c.rotateAngleX = -angleX * 0.12F - 0.9F;
    	this.Cloth01c2.rotateAngleX = -angleX * 0.12F - 0.85F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.21F + headX;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F + 0.12F + headX;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -angleX2 * 0.07F - 0.26F;
	  	this.Hair03.rotateAngleZ = 0F;
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
		this.ArmRight01.rotateAngleZ = -angleX * 0.025F + 0.3F;
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
		this.LegLeft02a.rotateAngleX = 0F;
		this.LegLeft02a.rotateAngleY = 0F;
		this.LegLeft02a.rotateAngleZ = 0F;
		this.LegLeft02a.offsetX = 0F;
		this.LegLeft02a.offsetY = 0F;
		this.LegLeft02a.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.087F;
		this.LegRight01.offsetY = 0F;
		this.LegRight01.offsetZ = 0F;
		this.LegRight02a.rotateAngleX = 0F;
		this.LegRight02a.rotateAngleY = 0F;
		this.LegRight02a.rotateAngleZ = 0F;
		this.LegRight02a.offsetX = 0F;
		this.LegRight02a.offsetY = 0F;
		this.LegRight02a.offsetZ = 0F;
		//equip
		this.EquipRdL01.isHidden = true;
		
		//奔跑動作
	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {
	    	//hair angleX * 0.03F + 0.21F + headX
	    	this.Hair01.rotateAngleX = angleAdd1 * 0.1F + f1 * 0.4F + headX;
	    	this.Hair02.rotateAngleX += 0F;
	    	this.Hair03.rotateAngleX += 0.1F;
		    //arm 
		    this.ArmLeft01.rotateAngleZ += f1 * -0.2F;
		    this.ArmRight01.rotateAngleZ += f1 * 0.2F;
  		}
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    //潛行跟蹲下動作
	    if (ent.getIsSneaking())
	    {
	    	GlStateManager.translate(0F, 0.06F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		  	this.Skirt01.rotateAngleX = -0.35F;
		  	this.Skirt02.rotateAngleX = -0.19F;
		  	this.Skirt03.rotateAngleX = -0.24F;
		    //arm 
		  	this.ArmLeft01.rotateAngleX = -0.35F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
			this.ArmRight01.rotateAngleX = -0.35F;
			this.ArmRight01.rotateAngleZ = -0.2618F;
			//leg
			addk1 -= 1.02F;
			addk2 -= 1.02F;
			//hair
			this.Hair01.rotateAngleX += 0.37F;
			this.Hair02.rotateAngleX += 0.23F;
			this.Hair03.rotateAngleX -= 0.1F;
  		}//end if sneaking
  		
	    //坐下動作
	    if (ent.getIsSitting() && !ent.getIsRiding())
	    {
	    	if (ent.getTickExisted() % 512 > 256)
	    	{
	      		GlStateManager.translate(0F, 0.48F, 0F);
	      		this.setFaceScorn(ent);

	    	  	//Body
	        	this.Head.rotateAngleX += 0.1F;
	    	  	this.BodyMain.rotateAngleX = -0.1F;
	    	  	this.Butt.rotateAngleX = -0.4F;
	    	  	this.Butt.offsetZ = 0.19F;
	      	    this.Ahoke.rotateAngleY = 0.5236F;
	      	    this.Skirt01.rotateAngleX = -0.35F;
			  	this.Skirt02.rotateAngleX = -0.19F;
			  	this.Skirt03.rotateAngleX = -0.24F;
	    	  	//hair
	    	  	this.Hair01.rotateAngleX = 0.21F + headX;
	    	  	this.Hair02.rotateAngleX = -0.28F + headX;
	    	  	this.Hair03.rotateAngleX = -0.24F;
	    	    //arm 
	    	  	this.ArmLeft01.rotateAngleX = -1.18F;
	    	  	this.ArmLeft01.rotateAngleY = 0.27F;
	    	    this.ArmLeft01.rotateAngleZ = -0.1F;
	    	    this.ArmLeft02.rotateAngleZ = 0.92F;
	    		this.ArmRight01.rotateAngleX = -1.18F;
	    		this.ArmRight01.rotateAngleY = -0.27F;
	    		this.ArmRight01.rotateAngleZ = 0.1F;
	    		this.ArmRight02.rotateAngleZ = -1.32F;
	    		//leg
	    		addk1 = -2.57F;
	    		addk2 = -2.57F;
	    		this.LegLeft01.offsetY = 0.25F;
	    		this.LegLeft01.offsetZ = -0.2F;
	    		this.LegLeft01.rotateAngleY = 0.11F;
	    		this.LegLeft01.rotateAngleZ = -0.12F;
	    		this.LegLeft02a.rotateAngleX = 2.75F;
	    		this.LegLeft02a.rotateAngleZ = 0.02F;
	    		this.LegLeft02a.offsetZ = 0.37F;
	    		this.LegRight01.offsetY = 0.25F;
	    		this.LegRight01.offsetZ = -0.2F;
	    		this.LegRight01.rotateAngleY = -0.11F;
	    		this.LegRight01.rotateAngleZ = 0.12F;
	    		this.LegRight02a.rotateAngleX = 2.75F;
	    		this.LegRight02a.rotateAngleZ = -0.02F;
	    		this.LegRight02a.offsetZ = 0.37F;
	    	}
	    	else
	    	{
		    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
		    		GlStateManager.translate(0F, 0.27F, 0F);
			    	//Body
			    	this.Head.rotateAngleX += 0.14F;
				  	this.BodyMain.rotateAngleX = -0.4363F;
				  	this.Skirt01.rotateAngleX = -0.35F;
				  	this.Skirt02.rotateAngleX = -0.19F;
				  	this.Skirt03.rotateAngleX = -0.24F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = -0.3142F;
				    this.ArmLeft01.rotateAngleZ = 0.3490F;
				    this.ArmLeft02.rotateAngleZ = 1.15F;
					this.ArmRight01.rotateAngleX = -0.4363F;
					this.ArmRight01.rotateAngleZ = -0.2793F;
					this.ArmRight02.rotateAngleZ = -1.4F;
					//leg
					addk1 = -1.3090F;
					addk2 = -1.7F;
					this.LegLeft01.rotateAngleY = 0.3142F;
					this.LegLeft02a.rotateAngleX = 1.0472F;
					this.LegRight01.rotateAngleY = -0.35F;
					this.LegRight01.rotateAngleZ = -0.2618F;
					this.LegRight02a.rotateAngleX = 0.9F;
					//hair
					this.Hair01.rotateAngleX += 0.12F;
					this.Hair02.rotateAngleX += 0.15F;
					this.Hair03.rotateAngleX += 0.25F;
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.37F, 0F);
			    	//Body
			    	this.Head.rotateAngleX += 0.14F;
				  	this.BodyMain.rotateAngleX = -0.5236F;
				  	this.Skirt01.rotateAngleX = -0.35F;
				  	this.Skirt02.rotateAngleX = -0.19F;
				  	this.Skirt03.rotateAngleX = -0.24F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = -0.4363F;
				    this.ArmLeft01.rotateAngleZ = 0.3142F;
					this.ArmRight01.rotateAngleX = -0.4363F;
					this.ArmRight01.rotateAngleZ = -0.3142F;
					//leg
					addk1 = -1.6232F;
					addk2 = -1.5708F;
					this.LegLeft01.rotateAngleZ = -0.3142F;
					this.LegLeft02a.rotateAngleX = 1.34F;
					this.LegRight01.rotateAngleZ = 0.35F;
					this.LegRight02a.rotateAngleX = 1.13F;
					//hair
					this.Hair01.rotateAngleX += 0.09F;
					this.Hair02.rotateAngleX += 0.43F;
					this.Hair03.rotateAngleX += 0.49F;
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
	    			GlStateManager.rotate(-40F, 0F, 1F, 0F);
	    			
	    			if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    			{
	    				GlStateManager.translate(0F, 0.02F, 0F);
			      		this.setFaceScorn(ent);

			    	  	//Body
			        	this.Head.rotateAngleX += 0.1F;
			    	  	this.BodyMain.rotateAngleX = -0.1F;
			    	  	this.Butt.rotateAngleX = -0.4F;
			    	  	this.Butt.offsetZ = 0.19F;
			      	    this.Ahoke.rotateAngleY = 0.5236F;
			      	    this.Skirt01.rotateAngleX = -0.35F;
					  	this.Skirt02.rotateAngleX = -0.19F;
					  	this.Skirt03.rotateAngleX = -0.24F;
			    	  	//hair
			    	  	this.Hair01.rotateAngleX = 0.21F + headX;
			    	  	this.Hair02.rotateAngleX = -0.28F + headX;
			    	  	this.Hair03.rotateAngleX = -0.24F;
			    	    //arm 
			    	  	this.ArmLeft01.rotateAngleX = -1.18F;
			    	  	this.ArmLeft01.rotateAngleY = 0.27F;
			    	    this.ArmLeft01.rotateAngleZ = -0.1F;
			    	    this.ArmLeft02.rotateAngleZ = 0.92F;
			    		this.ArmRight01.rotateAngleX = -1.18F;
			    		this.ArmRight01.rotateAngleY = -0.27F;
			    		this.ArmRight01.rotateAngleZ = 0.1F;
			    		this.ArmRight02.rotateAngleZ = -1.32F;
			    		//leg
			    		addk1 = -2.57F;
			    		addk2 = -2.57F;
			    		this.LegLeft01.offsetY = 0.25F;
			    		this.LegLeft01.offsetZ = -0.2F;
			    		this.LegLeft01.rotateAngleY = 0.11F;
			    		this.LegLeft01.rotateAngleZ = -0.12F;
			    		this.LegLeft02a.rotateAngleX = 2.75F;
			    		this.LegLeft02a.rotateAngleZ = 0.02F;
			    		this.LegLeft02a.offsetZ = 0.37F;
			    		this.LegRight01.offsetY = 0.25F;
			    		this.LegRight01.offsetZ = -0.2F;
			    		this.LegRight01.rotateAngleY = -0.11F;
			    		this.LegRight01.rotateAngleZ = 0.12F;
			    		this.LegRight02a.rotateAngleX = 2.75F;
			    		this.LegRight02a.rotateAngleZ = -0.02F;
			    		this.LegRight02a.offsetZ = 0.37F;
			    	}
			    	else
			    	{
				    	if (ent.getTickExisted() % 512 >  256)
				    	{
				    		GlStateManager.translate(0F, 0F, 0F);
					    	//Body
					    	this.Head.rotateAngleX += 0.14F;
						  	this.BodyMain.rotateAngleX = -0.4363F;
						  	this.Skirt01.rotateAngleX = -0.35F;
						  	this.Skirt02.rotateAngleX = -0.19F;
						  	this.Skirt03.rotateAngleX = -0.24F;
						    //arm 
						  	this.ArmLeft01.rotateAngleX = -0.3142F;
						    this.ArmLeft01.rotateAngleZ = 0.3490F;
						    this.ArmLeft02.rotateAngleZ = 1.15F;
							this.ArmRight01.rotateAngleX = -0.4363F;
							this.ArmRight01.rotateAngleZ = -0.2793F;
							this.ArmRight02.rotateAngleZ = -1.4F;
							//leg
							addk1 = -1.3090F;
							addk2 = -1.7F;
							this.LegLeft01.rotateAngleY = 0.3142F;
							this.LegLeft02a.rotateAngleX = 1.0472F;
							this.LegRight01.rotateAngleY = -0.35F;
							this.LegRight01.rotateAngleZ = -0.2618F;
							this.LegRight02a.rotateAngleX = 0.9F;
							//hair
							this.Hair01.rotateAngleX += 0.12F;
							this.Hair02.rotateAngleX += 0.15F;
							this.Hair03.rotateAngleX += 0.25F;
				    	}
				    	else
				    	{
				    		GlStateManager.translate(0F, 0.03F, 0F);
					    	//Body
					    	this.Head.rotateAngleX += 0.14F;
						  	this.BodyMain.rotateAngleX = -0.5236F;
						  	this.Skirt01.rotateAngleX = -0.35F;
						  	this.Skirt02.rotateAngleX = -0.19F;
						  	this.Skirt03.rotateAngleX = -0.24F;
						    //arm 
						  	this.ArmLeft01.rotateAngleX = -0.4363F;
						    this.ArmLeft01.rotateAngleZ = 0.3142F;
							this.ArmRight01.rotateAngleX = -0.4363F;
							this.ArmRight01.rotateAngleZ = -0.3142F;
							//leg
							addk1 = -1.6232F;
							addk2 = -1.5708F;
							this.LegLeft01.rotateAngleZ = -0.3142F;
							this.LegLeft02a.rotateAngleX = 1.34F;
							this.LegRight01.rotateAngleZ = 0.35F;
							this.LegRight02a.rotateAngleX = 1.13F;
							//hair
							this.Hair01.rotateAngleX += 0.09F;
							this.Hair02.rotateAngleX += 0.43F;
							this.Hair03.rotateAngleX += 0.49F;
				    	}
			    	}
		    	}//end if sitting
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.03F, 0F);
			    	//body
			    	this.Head.rotateAngleX -= 0.7F;
			    	this.BodyMain.rotateAngleX = 0.35F;
			    	//hair
					this.Hair01.rotateAngleX += 0.5F;
					this.Hair02.rotateAngleX += 0.15F;
					this.Hair03.rotateAngleX += 0F;
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
					this.LegLeft02a.rotateAngleX = 2.1816615649929116F;
					this.LegLeft02a.rotateAngleY = 0.0F;
					this.LegLeft02a.rotateAngleZ = 0.0F;
					this.LegLeft02a.offsetX = 0F;
					this.LegLeft02a.offsetZ = 0.37F;
					this.LegRight01.rotateAngleY = 0.5235987755982988F;
					this.LegRight01.rotateAngleZ = 1.3962634015954636F;
					this.LegRight02a.rotateAngleX = 2.1816615649929116F;
					this.LegRight02a.rotateAngleY = 0.0F;
					this.LegRight02a.rotateAngleZ = 0.0F;
					this.LegRight02a.offsetX = 0F;
					this.LegRight02a.offsetZ = 0.37F;
		    	}
	    	}//end ship mount
	    	//normal mount ex: cart
	    	else
	    	{
		    	if (ent.getTickExisted() % 512 > 256)
		    	{
		      		GlStateManager.translate(0F, 0.48F, 0F);
		      		this.setFaceScorn(ent);

		    	  	//Body
		        	this.Head.rotateAngleX += 0.1F;
		    	  	this.BodyMain.rotateAngleX = -0.1F;
		    	  	this.Butt.rotateAngleX = -0.4F;
		    	  	this.Butt.offsetZ = 0.19F;
		      	    this.Ahoke.rotateAngleY = 0.5236F;
		      	    this.Skirt01.rotateAngleX = -0.35F;
				  	this.Skirt02.rotateAngleX = -0.19F;
				  	this.Skirt03.rotateAngleX = -0.24F;
		    	  	//hair
		    	  	this.Hair01.rotateAngleX = 0.21F + headX;
		    	  	this.Hair02.rotateAngleX = -0.28F + headX;
		    	  	this.Hair03.rotateAngleX = -0.24F;
		    	    //arm 
		    	  	this.ArmLeft01.rotateAngleX = -1.18F;
		    	  	this.ArmLeft01.rotateAngleY = 0.27F;
		    	    this.ArmLeft01.rotateAngleZ = -0.1F;
		    	    this.ArmLeft02.rotateAngleZ = 0.92F;
		    		this.ArmRight01.rotateAngleX = -1.18F;
		    		this.ArmRight01.rotateAngleY = -0.27F;
		    		this.ArmRight01.rotateAngleZ = 0.1F;
		    		this.ArmRight02.rotateAngleZ = -1.32F;
		    		//leg
		    		addk1 = -2.57F;
		    		addk2 = -2.57F;
		    		this.LegLeft01.offsetY = 0.25F;
		    		this.LegLeft01.offsetZ = -0.2F;
		    		this.LegLeft01.rotateAngleY = 0.11F;
		    		this.LegLeft01.rotateAngleZ = -0.12F;
		    		this.LegLeft02a.rotateAngleX = 2.75F;
		    		this.LegLeft02a.rotateAngleZ = 0.02F;
		    		this.LegLeft02a.offsetZ = 0.37F;
		    		this.LegRight01.offsetY = 0.25F;
		    		this.LegRight01.offsetZ = -0.2F;
		    		this.LegRight01.rotateAngleY = -0.11F;
		    		this.LegRight01.rotateAngleZ = 0.12F;
		    		this.LegRight02a.rotateAngleX = 2.75F;
		    		this.LegRight02a.rotateAngleZ = -0.02F;
		    		this.LegRight02a.offsetZ = 0.37F;
		    	}
		    	else
		    	{
			    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			    	{
			    		GlStateManager.translate(0F, 0.27F, 0F);
				    	//Body
				    	this.Head.rotateAngleX += 0.14F;
					  	this.BodyMain.rotateAngleX = -0.4363F;
					  	this.Skirt01.rotateAngleX = -0.35F;
					  	this.Skirt02.rotateAngleX = -0.19F;
					  	this.Skirt03.rotateAngleX = -0.24F;
					    //arm 
					  	this.ArmLeft01.rotateAngleX = -0.3142F;
					    this.ArmLeft01.rotateAngleZ = 0.3490F;
					    this.ArmLeft02.rotateAngleZ = 1.15F;
						this.ArmRight01.rotateAngleX = -0.4363F;
						this.ArmRight01.rotateAngleZ = -0.2793F;
						this.ArmRight02.rotateAngleZ = -1.4F;
						//leg
						addk1 = -1.3090F;
						addk2 = -1.7F;
						this.LegLeft01.rotateAngleY = 0.3142F;
						this.LegLeft02a.rotateAngleX = 1.0472F;
						this.LegRight01.rotateAngleY = -0.35F;
						this.LegRight01.rotateAngleZ = -0.2618F;
						this.LegRight02a.rotateAngleX = 0.9F;
						//hair
						this.Hair01.rotateAngleX += 0.12F;
						this.Hair02.rotateAngleX += 0.15F;
						this.Hair03.rotateAngleX += 0.25F;
			    	}
			    	else
			    	{
			    		GlStateManager.translate(0F, 0.37F, 0F);
				    	//Body
				    	this.Head.rotateAngleX += 0.14F;
					  	this.BodyMain.rotateAngleX = -0.5236F;
					  	this.Skirt01.rotateAngleX = -0.35F;
					  	this.Skirt02.rotateAngleX = -0.19F;
					  	this.Skirt03.rotateAngleX = -0.24F;
					    //arm 
					  	this.ArmLeft01.rotateAngleX = -0.4363F;
					    this.ArmLeft01.rotateAngleZ = 0.3142F;
						this.ArmRight01.rotateAngleX = -0.4363F;
						this.ArmRight01.rotateAngleZ = -0.3142F;
						//leg
						addk1 = -1.6232F;
						addk2 = -1.5708F;
						this.LegLeft01.rotateAngleZ = -0.3142F;
						this.LegLeft02a.rotateAngleX = 1.34F;
						this.LegRight01.rotateAngleZ = 0.35F;
						this.LegRight02a.rotateAngleX = 1.13F;
						//hair
						this.Hair01.rotateAngleX += 0.09F;
						this.Hair02.rotateAngleX += 0.43F;
						this.Hair03.rotateAngleX += 0.49F;
			    	}
		    	}
	    	}
	    }//end ridding
    
	    //攻擊動作    
	    if (ent.getAttackTick() > 0)
	    {
	    	if (ent.getAttackTick() > 25)
	    	{
	    		//arm
		    	this.ArmLeft01.rotateAngleX = -1.3F + this.Head.rotateAngleX * 0.75F;
		    	this.ArmLeft01.rotateAngleY = -0.2F;
		    	this.ArmLeft01.rotateAngleZ = 0F;
		    	this.ArmLeft02.rotateAngleX = 0F;
		    	this.ArmLeft02.rotateAngleY = 0F;
			    this.ArmLeft02.rotateAngleZ = 0F;
			    this.ArmLeft02.offsetX = 0F;
			    this.ArmLeft02.offsetZ = 0F;
	    	}
	    	
	    	//跑道顯示
	    	setRoad(ent.getAttackTick());
	    }
	    
	    //移動頭髮避免穿過身體
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	this.Hair03.rotateAngleZ = headZ;
	    
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
			this.EquipRdL01.isHidden = false;
			this.EquipRdL02.isHidden = true;
		break;
		case 49:
		case 27:
			this.EquipRdL01.isHidden = false;
			this.EquipRdL02.isHidden = false;
			this.EquipRdL03.isHidden = true;
		break;
		case 48:
		case 28:
			this.EquipRdL01.isHidden = false;
			this.EquipRdL02.isHidden = false;
			this.EquipRdL03.isHidden = false;
			this.EquipRdL04.isHidden = true;
		break;
		case 47:
		case 29:
			this.EquipRdL01.isHidden = false;
			this.EquipRdL02.isHidden = false;
			this.EquipRdL03.isHidden = false;
			this.EquipRdL04.isHidden = false;
			this.EquipRdL05.isHidden = true;
		break;
		case 46:
		case 30:
			this.EquipRdL01.isHidden = false;
			this.EquipRdL02.isHidden = false;
			this.EquipRdL03.isHidden = false;
			this.EquipRdL04.isHidden = false;
			this.EquipRdL05.isHidden = false;
			this.EquipRdL06.isHidden = true;
		break;
		default:
			if (attackTime < 46 && attackTime > 30)
			{
				this.EquipRdL01.isHidden = false;
				this.EquipRdL02.isHidden = false;
				this.EquipRdL03.isHidden = false;
				this.EquipRdL04.isHidden = false;
				this.EquipRdL05.isHidden = false;
				this.EquipRdL06.isHidden = false;
			}		
		break;
		}
	}
    
    
}