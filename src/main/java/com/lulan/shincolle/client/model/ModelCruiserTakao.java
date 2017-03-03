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
import net.minecraft.util.math.MathHelper;

/**
 * ModelCruiserTakao - PinkaLulan 2017/3/3
 * Created using Tabula 5.1.0
 */
public class ModelCruiserTakao extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft01;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer Cloth01;
    public ModelRenderer EquipBase;
    public ModelRenderer EquipBag00;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Hat01;
    public ModelRenderer HairU01;
    public ModelRenderer Ahoke;
    public ModelRenderer Hair01;
    public ModelRenderer Hat02;
    public ModelRenderer Hat03;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft02;
    public ModelRenderer ShoeL03;
    public ModelRenderer ShoeL01;
    public ModelRenderer ShoeL02;
    public ModelRenderer ShoeL04;
    public ModelRenderer Belt01;
    public ModelRenderer LegRight02;
    public ModelRenderer ShoeL03_1;
    public ModelRenderer ShoeR01;
    public ModelRenderer ShoeR02;
    public ModelRenderer ShoeL04_1;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight02a;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft02a;
    public ModelRenderer Equip00;
    public ModelRenderer EquipCannonBase;
    public ModelRenderer EquipLIn01;
    public ModelRenderer EquipRIn01;
    public ModelRenderer EquipOut01;
    public ModelRenderer EquipOut01_1;
    public ModelRenderer EquipC01a;
    public ModelRenderer EquipLIn02;
    public ModelRenderer EquipLIn03;
    public ModelRenderer EquipLIn07;
    public ModelRenderer EquipLIn08;
    public ModelRenderer EquipLIn09;
    public ModelRenderer EquipLIn04;
    public ModelRenderer EquipLIn06a;
    public ModelRenderer EquipLIn05;
    public ModelRenderer EquipLIn06b;
    public ModelRenderer EquipRIn02;
    public ModelRenderer EquipRIn03;
    public ModelRenderer EquipRIn07;
    public ModelRenderer EquipRIn08;
    public ModelRenderer EquipRIn09;
    public ModelRenderer EquipRIn04;
    public ModelRenderer EquipRIn06a;
    public ModelRenderer EquipRIn05;
    public ModelRenderer EquipRIn06b;
    public ModelRenderer EquipOut02;
    public ModelRenderer EquipOut03;
    public ModelRenderer EquipOut04;
    public ModelRenderer EquipOut05;
    public ModelRenderer EquipOut02_1;
    public ModelRenderer EquipOut03_1;
    public ModelRenderer EquipOut04_1;
    public ModelRenderer EquipOut05_1;
    public ModelRenderer EquipC01b;
    public ModelRenderer EquipC01c;
    public ModelRenderer EquipC01e;
    public ModelRenderer EquipC01d;
    public ModelRenderer EquipC01f;
    public ModelRenderer EquipC01a_1;
    public ModelRenderer EquipC01a_2;
    public ModelRenderer EquipC01b_1;
    public ModelRenderer EquipC01c_1;
    public ModelRenderer EquipC01e_1;
    public ModelRenderer EquipC01d_1;
    public ModelRenderer EquipC01f_1;
    public ModelRenderer EquipC01b_2;
    public ModelRenderer EquipC01c_2;
    public ModelRenderer EquipC01e_2;
    public ModelRenderer EquipC01d_2;
    public ModelRenderer EquipC01f_2;
    public ModelRenderer EquipBag01;
    public ModelRenderer EquipBag02;
    public ModelRenderer EquipBag03;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    

    public ModelCruiserTakao()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.offsetItem = new float[] {0.06F, 1.07F, -0.06F};
        this.offsetBlock = new float[] {0.06F, 1.07F, -0.06F};
        
        this.setDefaultFaceModel();
        
        this.EquipRIn02 = new ModelRenderer(this, 0, 0);
        this.EquipRIn02.setRotationPoint(7.0F, 0.0F, -1.0F);
        this.EquipRIn02.addBox(0.0F, 0.0F, 0.0F, 8, 2, 1, 0.0F);
        this.setRotateAngle(EquipRIn02, 0.0F, -1.1344640137963142F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.296705972839036F, 0.0F, -0.08726646259971647F);
        this.LegRight02 = new ModelRenderer(this, 0, 63);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.EquipOut01_1 = new ModelRenderer(this, 30, 0);
        this.EquipOut01_1.setRotationPoint(-11.2F, -1.0F, 0.2F);
        this.EquipOut01_1.addBox(0.0F, 0.0F, -0.5F, 12, 8, 1, 0.0F);
        this.setRotateAngle(EquipOut01_1, -0.2617993877991494F, 1.48352986419518F, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.ShoeL04 = new ModelRenderer(this, 0, 0);
        this.ShoeL04.setRotationPoint(0.0F, 3.0F, -0.7F);
        this.ShoeL04.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        this.EquipLIn09 = new ModelRenderer(this, 6, 22);
        this.EquipLIn09.setRotationPoint(2.9F, -3.2F, -0.5F);
        this.EquipLIn09.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.EquipC01c_1 = new ModelRenderer(this, 30, 9);
        this.EquipC01c_1.setRotationPoint(2.0F, -8.0F, -2.2F);
        this.EquipC01c_1.addBox(-1.5F, -5.0F, -1.5F, 3, 5, 3, 0.0F);
        this.EquipOut05 = new ModelRenderer(this, 0, 28);
        this.EquipOut05.setRotationPoint(3.5F, 2.5F, -0.5F);
        this.EquipOut05.addBox(0.0F, 0.0F, -1.0F, 1, 6, 2, 0.0F);
        this.setRotateAngle(EquipOut05, 0.0F, 0.0F, 0.3490658503988659F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.ShoeL02 = new ModelRenderer(this, 24, 80);
        this.ShoeL02.setRotationPoint(4.0F, 3.0F, 0.1F);
        this.ShoeL02.addBox(0.0F, -3.0F, 0.0F, 10, 3, 1, 0.0F);
        this.setRotateAngle(ShoeL02, 0.0F, 0.0F, -0.6981317007977318F);
        this.ArmLeft02a = new ModelRenderer(this, 104, 32);
        this.ArmLeft02a.setRotationPoint(-2.5F, 5.5F, -2.4F);
        this.ArmLeft02a.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmLeft02a, 0.05235987755982988F, 0.0F, 0.0F);
        this.EquipC01b = new ModelRenderer(this, 0, 0);
        this.EquipC01b.setRotationPoint(-5.9F, 0.0F, 0.0F);
        this.EquipC01b.addBox(0.0F, -9.0F, -4.5F, 4, 13, 9, 0.0F);
        this.setRotateAngle(EquipC01b, 0.0F, 0.0F, 0.08726646259971647F);
        this.Ahoke = new ModelRenderer(this, 106, 31);
        this.Ahoke.setRotationPoint(-0.5F, -7.0F, -6.0F);
        this.Ahoke.addBox(0.0F, -6.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.20943951023931953F, 0.6981317007977318F, 0.0F);
        this.EquipLIn02 = new ModelRenderer(this, 0, 0);
        this.EquipLIn02.setRotationPoint(7.0F, 0.0F, 1.0F);
        this.EquipLIn02.addBox(0.0F, 0.0F, -1.0F, 8, 2, 1, 0.0F);
        this.setRotateAngle(EquipLIn02, 0.0F, 1.1344640137963142F, 0.0F);
        this.EquipOut05_1 = new ModelRenderer(this, 0, 28);
        this.EquipOut05_1.setRotationPoint(3.5F, 2.5F, 0.5F);
        this.EquipOut05_1.addBox(0.0F, 0.0F, -1.0F, 1, 6, 2, 0.0F);
        this.setRotateAngle(EquipOut05_1, 0.0F, 0.0F, 0.3490658503988659F);
        this.EquipC01f = new ModelRenderer(this, 14, 22);
        this.EquipC01f.setRotationPoint(0.0F, -14.9F, 0.0F);
        this.EquipC01f.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 63);
        this.LegLeft02.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.EquipOut02 = new ModelRenderer(this, 33, 0);
        this.EquipOut02.mirror = true;
        this.EquipOut02.setRotationPoint(12.0F, 0.0F, 0.5F);
        this.EquipOut02.addBox(0.0F, 0.0F, -1.0F, 9, 8, 1, 0.0F);
        this.setRotateAngle(EquipOut02, 0.0F, 0.5235987755982988F, 0.0F);
        this.EquipC01a_1 = new ModelRenderer(this, 0, 0);
        this.EquipC01a_1.setRotationPoint(20.0F, 5.0F, 0.0F);
        this.EquipC01a_1.addBox(-2.0F, -1.5F, -1.5F, 4, 3, 3, 0.0F);
        this.EquipRIn04 = new ModelRenderer(this, 0, 22);
        this.EquipRIn04.setRotationPoint(1.5F, 1.9F, 1.3F);
        this.EquipRIn04.addBox(0.0F, 0.0F, -1.0F, 2, 5, 1, 0.0F);
        this.setRotateAngle(EquipRIn04, 0.0F, -0.2617993877991494F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 63);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipC01d_2 = new ModelRenderer(this, 14, 22);
        this.EquipC01d_2.setRotationPoint(0.0F, -14.9F, 0.0F);
        this.EquipC01d_2.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.EquipC01a = new ModelRenderer(this, 0, 0);
        this.EquipC01a.setRotationPoint(-13.0F, 0.5F, 0.0F);
        this.EquipC01a.addBox(-2.0F, -1.5F, -1.5F, 4, 3, 3, 0.0F);
        this.EquipOut01 = new ModelRenderer(this, 30, 0);
        this.EquipOut01.setRotationPoint(-11.2F, -1.0F, -0.2F);
        this.EquipOut01.addBox(0.0F, 0.0F, -0.5F, 12, 8, 1, 0.0F);
        this.setRotateAngle(EquipOut01, 0.2617993877991494F, -1.48352986419518F, 0.0F);
        this.Belt01 = new ModelRenderer(this, 56, 0);
        this.Belt01.setRotationPoint(-2.6F, 0.9F, -1.0F);
        this.Belt01.addBox(-5.5F, 0.0F, 0.0F, 11, 7, 1, 0.0F);
        this.setRotateAngle(Belt01, -1.1344640137963142F, 1.5707963267948966F, 0.0F);
        this.ShoeL03 = new ModelRenderer(this, 20, 33);
        this.ShoeL03.setRotationPoint(2.9F, 9.5F, 0.0F);
        this.ShoeL03.addBox(0.0F, 0.0F, -2.2F, 1, 3, 5, 0.0F);
        this.ShoeL04_1 = new ModelRenderer(this, 0, 0);
        this.ShoeL04_1.setRotationPoint(0.0F, 3.0F, -0.7F);
        this.ShoeL04_1.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        this.EquipOut04 = new ModelRenderer(this, 36, 0);
        this.EquipOut04.mirror = true;
        this.EquipOut04.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.EquipOut04.addBox(0.0F, 0.0F, -1.0F, 6, 8, 1, 0.0F);
        this.setRotateAngle(EquipOut04, 0.0F, 0.6981317007977318F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.EquipC01a_2 = new ModelRenderer(this, 0, 0);
        this.EquipC01a_2.setRotationPoint(-20.0F, 5.0F, 0.0F);
        this.EquipC01a_2.addBox(-2.0F, -1.5F, -1.5F, 4, 3, 3, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 84);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.20943951023931953F, 0.0F, 0.2617993877991494F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.15707963267948966F, 0.0F, 0.08726646259971647F);
        this.EquipC01f_1 = new ModelRenderer(this, 14, 22);
        this.EquipC01f_1.setRotationPoint(0.0F, -14.9F, 0.0F);
        this.EquipC01f_1.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.EquipC01f_2 = new ModelRenderer(this, 14, 22);
        this.EquipC01f_2.setRotationPoint(0.0F, -14.9F, 0.0F);
        this.EquipC01f_2.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 63);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.Neck = new ModelRenderer(this, 98, 22);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.Neck.addBox(-3.5F, -2.0F, -4.9F, 7, 2, 8, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.EquipRIn06a = new ModelRenderer(this, 0, 0);
        this.EquipRIn06a.setRotationPoint(4.3F, -0.5F, 1.5F);
        this.EquipRIn06a.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(EquipRIn06a, 0.0F, -0.6981317007977318F, 0.0F);
        this.EquipRIn05 = new ModelRenderer(this, 0, 0);
        this.EquipRIn05.setRotationPoint(0.5F, 4.7F, -0.4F);
        this.EquipRIn05.addBox(-2.0F, 0.0F, -1.0F, 7, 3, 1, 0.0F);
        this.setRotateAngle(EquipRIn05, 0.0F, -0.2617993877991494F, 0.08726646259971647F);
        this.EquipC01d_1 = new ModelRenderer(this, 14, 22);
        this.EquipC01d_1.setRotationPoint(0.0F, -14.9F, 0.0F);
        this.EquipC01d_1.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.EquipBag03 = new ModelRenderer(this, 6, 22);
        this.EquipBag03.setRotationPoint(3.0F, -2.9F, 0.0F);
        this.EquipBag03.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.EquipBag00 = new ModelRenderer(this, 32, 27);
        this.EquipBag00.setRotationPoint(-6.9F, -10.9F, -0.7F);
        this.EquipBag00.addBox(-3.0F, 0.0F, 0.0F, 6, 14, 1, 0.0F);
        this.setRotateAngle(EquipBag00, -0.17453292519943295F, 1.7453292519943295F, 0.08726646259971647F);
        this.BoobL = new ModelRenderer(this, 0, 35);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.5F, -8.7F, -3.8F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 6, 6, 0.0F);
        this.setRotateAngle(BoobL, -0.8726646259971648F, -0.05235987755982988F, 0.08726646259971647F);
        this.ShoeR01 = new ModelRenderer(this, 19, 63);
        this.ShoeR01.mirror = true;
        this.ShoeR01.setRotationPoint(2.0F, 12.5F, 3.6F);
        this.ShoeR01.addBox(-4.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
        this.setRotateAngle(ShoeR01, -0.6981317007977318F, 0.13962634015954636F, 0.6981317007977318F);
        this.Hair01 = new ModelRenderer(this, 51, 41);
        this.Hair01.setRotationPoint(0.0F, 7.0F, 10.9F);
        this.Hair01.addBox(-8.0F, 0.0F, -8.0F, 16, 7, 7, 0.0F);
        this.setRotateAngle(Hair01, 0.13962634015954636F, 0.0F, 0.0F);
        this.EquipOut03_1 = new ModelRenderer(this, 36, 0);
        this.EquipOut03_1.setRotationPoint(9.0F, 0.0F, 0.0F);
        this.EquipOut03_1.addBox(0.0F, 0.0F, 0.0F, 6, 8, 1, 0.0F);
        this.setRotateAngle(EquipOut03_1, 0.0F, -0.6981317007977318F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipC01b_2 = new ModelRenderer(this, 0, 0);
        this.EquipC01b_2.setRotationPoint(-1.9F, 0.0F, 0.0F);
        this.EquipC01b_2.addBox(-4.0F, -9.0F, -4.5F, 4, 13, 9, 0.0F);
        this.setRotateAngle(EquipC01b_2, 0.0F, 0.0F, -0.08726646259971647F);
        this.EquipBag02 = new ModelRenderer(this, 0, 0);
        this.EquipBag02.setRotationPoint(5.0F, -1.9F, -0.5F);
        this.EquipBag02.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.EquipRIn09 = new ModelRenderer(this, 6, 22);
        this.EquipRIn09.setRotationPoint(2.9F, -3.2F, 0.5F);
        this.EquipRIn09.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 74, 0);
        this.Skirt01.setRotationPoint(0.0F, 3.1F, 0.0F);
        this.Skirt01.addBox(-8.5F, 0.0F, -6.2F, 17, 7, 10, 0.0F);
        this.setRotateAngle(Skirt01, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipLIn01 = new ModelRenderer(this, 0, 0);
        this.EquipLIn01.setRotationPoint(-2.0F, -1.0F, 1.0F);
        this.EquipLIn01.addBox(0.0F, 0.0F, 0.0F, 7, 2, 1, 0.0F);
        this.setRotateAngle(EquipLIn01, 0.08726646259971647F, -1.2217304763960306F, 0.0F);
        this.Equip00 = new ModelRenderer(this, 0, 0);
        this.Equip00.setRotationPoint(0.0F, 3.0F, 3.0F);
        this.Equip00.addBox(-11.5F, -1.0F, -1.5F, 12, 3, 3, 0.0F);
        this.setRotateAngle(Equip00, 0.0F, 1.5707963267948966F, 0.0F);
        this.EquipRIn03 = new ModelRenderer(this, 0, 0);
        this.EquipRIn03.setRotationPoint(8.0F, 0.0F, 0.0F);
        this.EquipRIn03.addBox(0.0F, 0.0F, 0.0F, 4, 2, 1, 0.0F);
        this.setRotateAngle(EquipRIn03, 0.0F, -0.8726646259971648F, 0.0F);
        this.BoobR = new ModelRenderer(this, 0, 35);
        this.BoobR.setRotationPoint(-3.5F, -8.7F, -3.8F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 6, 6, 0.0F);
        this.setRotateAngle(BoobR, -0.8726646259971648F, 0.05235987755982988F, -0.08726646259971647F);
        this.EquipLIn07 = new ModelRenderer(this, 0, 0);
        this.EquipLIn07.setRotationPoint(8.5F, -0.7F, -0.5F);
        this.EquipLIn07.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
        this.setRotateAngle(EquipLIn07, 0.0F, 0.5235987755982988F, 0.0F);
        this.EquipCannonBase = new ModelRenderer(this, 0, 0);
        this.EquipCannonBase.setRotationPoint(0.0F, 4.0F, 5.0F);
        this.EquipCannonBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipCannonBase, 0.2617993877991494F, 0.0F, 0.0F);
        this.EquipOut02_1 = new ModelRenderer(this, 33, 0);
        this.EquipOut02_1.mirror = true;
        this.EquipOut02_1.setRotationPoint(12.0F, 0.0F, -0.5F);
        this.EquipOut02_1.addBox(0.0F, 0.0F, 0.0F, 9, 8, 1, 0.0F);
        this.setRotateAngle(EquipOut02_1, 0.0F, -0.5235987755982988F, 0.0F);
        this.EquipBag01 = new ModelRenderer(this, 0, 0);
        this.EquipBag01.setRotationPoint(-2.5F, 13.5F, 0.5F);
        this.EquipBag01.addBox(-1.0F, 0.0F, -1.5F, 8, 4, 3, 0.0F);
        this.setRotateAngle(EquipBag01, 0.0F, 0.0F, 0.08726646259971647F);
        this.EquipRIn08 = new ModelRenderer(this, 0, 0);
        this.EquipRIn08.setRotationPoint(5.2F, -1.9F, 1.3F);
        this.EquipRIn08.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 47);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 0, 48);
        this.Cloth01.setRotationPoint(0.0F, -10.5F, -3.5F);
        this.Cloth01.addBox(-1.5F, 0.0F, -0.5F, 3, 6, 1, 0.0F);
        this.setRotateAngle(Cloth01, -0.6981317007977318F, 0.0F, 0.0F);
        this.Hat03 = new ModelRenderer(this, 42, 7);
        this.Hat03.setRotationPoint(-4.6F, 1.6F, 2.0F);
        this.Hat03.addBox(0.0F, 0.0F, -1.0F, 0, 5, 2, 0.0F);
        this.setRotateAngle(Hat03, 0.2617993877991494F, 0.13962634015954636F, 0.5235987755982988F);
        this.EquipLIn06b = new ModelRenderer(this, 6, 22);
        this.EquipLIn06b.setRotationPoint(-0.3F, 0.0F, 0.0F);
        this.EquipLIn06b.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.EquipRIn06b = new ModelRenderer(this, 6, 22);
        this.EquipRIn06b.setRotationPoint(-0.3F, 0.0F, 0.0F);
        this.EquipRIn06b.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.EquipC01e_2 = new ModelRenderer(this, 30, 9);
        this.EquipC01e_2.setRotationPoint(-2.0F, -8.0F, 2.2F);
        this.EquipC01e_2.addBox(-1.5F, -5.0F, -1.5F, 3, 5, 3, 0.0F);
        this.EquipRIn01 = new ModelRenderer(this, 0, 0);
        this.EquipRIn01.setRotationPoint(-2.0F, -1.0F, -1.0F);
        this.EquipRIn01.addBox(0.0F, 0.0F, -1.0F, 7, 2, 1, 0.0F);
        this.setRotateAngle(EquipRIn01, -0.08726646259971647F, 1.2217304763960306F, 0.0F);
        this.ArmRight02a = new ModelRenderer(this, 104, 32);
        this.ArmRight02a.mirror = true;
        this.ArmRight02a.setRotationPoint(2.5F, 5.5F, -2.4F);
        this.ArmRight02a.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(ArmRight02a, 0.05235987755982988F, 0.0F, 0.0F);
        this.EquipC01b_1 = new ModelRenderer(this, 0, 0);
        this.EquipC01b_1.setRotationPoint(1.9F, 0.0F, 0.0F);
        this.EquipC01b_1.addBox(0.0F, -9.0F, -4.5F, 4, 13, 9, 0.0F);
        this.setRotateAngle(EquipC01b_1, 0.0F, 0.0F, 0.08726646259971647F);
        this.ShoeL01 = new ModelRenderer(this, 19, 63);
        this.ShoeL01.setRotationPoint(-2.0F, 12.5F, 3.6F);
        this.ShoeL01.addBox(0.0F, 0.0F, 0.0F, 4, 3, 1, 0.0F);
        this.setRotateAngle(ShoeL01, -0.6981317007977318F, -0.13962634015954636F, -0.6981317007977318F);
        this.ShoeR02 = new ModelRenderer(this, 24, 80);
        this.ShoeR02.mirror = true;
        this.ShoeR02.setRotationPoint(-4.0F, 3.0F, 0.1F);
        this.ShoeR02.addBox(-10.0F, -3.0F, 0.0F, 10, 3, 1, 0.0F);
        this.setRotateAngle(ShoeR02, 0.0F, 0.0F, 0.6981317007977318F);
        this.EquipC01d = new ModelRenderer(this, 14, 22);
        this.EquipC01d.setRotationPoint(0.0F, -14.9F, 0.0F);
        this.EquipC01d.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
        this.EquipRIn07 = new ModelRenderer(this, 0, 0);
        this.EquipRIn07.setRotationPoint(8.5F, -0.7F, 0.5F);
        this.EquipRIn07.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
        this.setRotateAngle(EquipRIn07, 0.0F, -0.5235987755982988F, 0.0F);
        this.EquipLIn04 = new ModelRenderer(this, 0, 22);
        this.EquipLIn04.setRotationPoint(1.5F, 1.9F, -1.3F);
        this.EquipLIn04.addBox(0.0F, 0.0F, 0.0F, 2, 5, 1, 0.0F);
        this.setRotateAngle(EquipLIn04, 0.0F, 0.2617993877991494F, 0.0F);
        this.Hat01 = new ModelRenderer(this, 22, 17);
        this.Hat01.setRotationPoint(-2.6F, -15.4F, 3.2F);
        this.Hat01.addBox(-4.0F, 0.0F, -4.0F, 8, 2, 8, 0.0F);
        this.setRotateAngle(Hat01, -0.17453292519943295F, 0.0F, -0.08726646259971647F);
        this.EquipLIn05 = new ModelRenderer(this, 0, 0);
        this.EquipLIn05.setRotationPoint(0.5F, 4.7F, 0.4F);
        this.EquipLIn05.addBox(-2.0F, 0.0F, 0.0F, 7, 3, 1, 0.0F);
        this.setRotateAngle(EquipLIn05, 0.0F, 0.2617993877991494F, 0.08726646259971647F);
        this.EquipLIn08 = new ModelRenderer(this, 0, 0);
        this.EquipLIn08.setRotationPoint(5.2F, -1.9F, -1.3F);
        this.EquipLIn08.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2, 0.0F);
        this.Hat02 = new ModelRenderer(this, 47, 10);
        this.Hat02.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Hat02.addBox(-4.5F, 0.0F, -4.5F, 9, 3, 9, 0.0F);
        this.EquipOut03 = new ModelRenderer(this, 36, 0);
        this.EquipOut03.setRotationPoint(9.0F, 0.0F, 0.0F);
        this.EquipOut03.addBox(0.0F, 0.0F, -1.0F, 6, 8, 1, 0.0F);
        this.setRotateAngle(EquipOut03, 0.0F, 0.6981317007977318F, 0.0F);
        this.EquipLIn06a = new ModelRenderer(this, 0, 0);
        this.EquipLIn06a.setRotationPoint(4.3F, -0.5F, -1.5F);
        this.EquipLIn06a.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(EquipLIn06a, 0.0F, 0.6981317007977318F, 0.0F);
        this.ShoeL03_1 = new ModelRenderer(this, 20, 33);
        this.ShoeL03_1.setRotationPoint(-3.9F, 9.5F, 0.0F);
        this.ShoeL03_1.addBox(0.0F, 0.0F, -2.2F, 1, 3, 5, 0.0F);
        this.EquipLIn03 = new ModelRenderer(this, 0, 0);
        this.EquipLIn03.setRotationPoint(8.0F, 0.0F, 0.0F);
        this.EquipLIn03.addBox(0.0F, 0.0F, -1.0F, 4, 2, 1, 0.0F);
        this.setRotateAngle(EquipLIn03, 0.0F, 0.8726646259971648F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 84);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.0F, 0.0F, -0.2617993877991494F);
        this.EquipC01c_2 = new ModelRenderer(this, 30, 9);
        this.EquipC01c_2.setRotationPoint(-2.0F, -8.0F, -2.2F);
        this.EquipC01c_2.addBox(-1.5F, -5.0F, -1.5F, 3, 5, 3, 0.0F);
        this.EquipC01c = new ModelRenderer(this, 30, 9);
        this.EquipC01c.setRotationPoint(2.0F, -8.0F, -2.2F);
        this.EquipC01c.addBox(-1.5F, -5.0F, -1.5F, 3, 5, 3, 0.0F);
        this.EquipOut04_1 = new ModelRenderer(this, 36, 0);
        this.EquipOut04_1.mirror = true;
        this.EquipOut04_1.setRotationPoint(6.0F, 0.0F, 0.0F);
        this.EquipOut04_1.addBox(0.0F, 0.0F, 0.0F, 6, 8, 1, 0.0F);
        this.setRotateAngle(EquipOut04_1, 0.0F, -0.6981317007977318F, 0.0F);
        this.EquipC01e = new ModelRenderer(this, 30, 9);
        this.EquipC01e.setRotationPoint(2.0F, -8.0F, 2.2F);
        this.EquipC01e.addBox(-1.5F, -5.0F, -1.5F, 3, 5, 3, 0.0F);
        this.EquipC01e_1 = new ModelRenderer(this, 30, 9);
        this.EquipC01e_1.setRotationPoint(2.0F, -8.0F, 2.2F);
        this.EquipC01e_1.addBox(-1.5F, -5.0F, -1.5F, 3, 5, 3, 0.0F);
        this.EquipRIn01.addChild(this.EquipRIn02);
        this.Butt.addChild(this.LegRight01);
        this.LegRight01.addChild(this.LegRight02);
        this.Hair.addChild(this.HairU01);
        this.Equip00.addChild(this.EquipOut01_1);
        this.Head.addChild(this.HairMain);
        this.ShoeL03.addChild(this.ShoeL04);
        this.EquipLIn02.addChild(this.EquipLIn09);
        this.EquipC01b_1.addChild(this.EquipC01c_1);
        this.EquipOut04.addChild(this.EquipOut05);
        this.Neck.addChild(this.Head);
        this.BodyMain.addChild(this.EquipBase);
        this.ShoeL01.addChild(this.ShoeL02);
        this.ArmLeft02.addChild(this.ArmLeft02a);
        this.EquipC01a.addChild(this.EquipC01b);
        this.Hair.addChild(this.Ahoke);
        this.EquipLIn01.addChild(this.EquipLIn02);
        this.EquipOut04_1.addChild(this.EquipOut05_1);
        this.EquipC01e.addChild(this.EquipC01f);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipOut01.addChild(this.EquipOut02);
        this.EquipCannonBase.addChild(this.EquipC01a_1);
        this.EquipRIn03.addChild(this.EquipRIn04);
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipC01c_2.addChild(this.EquipC01d_2);
        this.Equip00.addChild(this.EquipC01a);
        this.Equip00.addChild(this.EquipOut01);
        this.Skirt01.addChild(this.Belt01);
        this.LegLeft01.addChild(this.ShoeL03);
        this.ShoeL03_1.addChild(this.ShoeL04_1);
        this.EquipOut03.addChild(this.EquipOut04);
        this.Head.addChild(this.Hair);
        this.EquipCannonBase.addChild(this.EquipC01a_2);
        this.BodyMain.addChild(this.ArmRight01);
        this.Butt.addChild(this.LegLeft01);
        this.EquipC01e_1.addChild(this.EquipC01f_1);
        this.EquipC01e_2.addChild(this.EquipC01f_2);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.BodyMain.addChild(this.Neck);
        this.EquipRIn03.addChild(this.EquipRIn06a);
        this.EquipRIn04.addChild(this.EquipRIn05);
        this.EquipC01c_1.addChild(this.EquipC01d_1);
        this.EquipBag01.addChild(this.EquipBag03);
        this.BodyMain.addChild(this.EquipBag00);
        this.BodyMain.addChild(this.BoobL);
        this.LegRight02.addChild(this.ShoeR01);
        this.HairMain.addChild(this.Hair01);
        this.EquipOut02_1.addChild(this.EquipOut03_1);
        this.EquipC01a_2.addChild(this.EquipC01b_2);
        this.EquipBag01.addChild(this.EquipBag02);
        this.EquipRIn02.addChild(this.EquipRIn09);
        this.Butt.addChild(this.Skirt01);
        this.Equip00.addChild(this.EquipLIn01);
        this.EquipBase.addChild(this.Equip00);
        this.EquipRIn02.addChild(this.EquipRIn03);
        this.BodyMain.addChild(this.BoobR);
        this.EquipLIn02.addChild(this.EquipLIn07);
        this.EquipBase.addChild(this.EquipCannonBase);
        this.EquipOut01_1.addChild(this.EquipOut02_1);
        this.EquipBag00.addChild(this.EquipBag01);
        this.EquipRIn02.addChild(this.EquipRIn08);
        this.BodyMain.addChild(this.Butt);
        this.BodyMain.addChild(this.Cloth01);
        this.Hat02.addChild(this.Hat03);
        this.EquipLIn06a.addChild(this.EquipLIn06b);
        this.EquipRIn06a.addChild(this.EquipRIn06b);
        this.EquipC01b_2.addChild(this.EquipC01e_2);
        this.Equip00.addChild(this.EquipRIn01);
        this.ArmRight02.addChild(this.ArmRight02a);
        this.EquipC01a_1.addChild(this.EquipC01b_1);
        this.LegLeft02.addChild(this.ShoeL01);
        this.ShoeR01.addChild(this.ShoeR02);
        this.EquipC01c.addChild(this.EquipC01d);
        this.EquipRIn02.addChild(this.EquipRIn07);
        this.EquipLIn03.addChild(this.EquipLIn04);
        this.Head.addChild(this.Hat01);
        this.EquipLIn04.addChild(this.EquipLIn05);
        this.EquipLIn02.addChild(this.EquipLIn08);
        this.Hat01.addChild(this.Hat02);
        this.EquipOut02.addChild(this.EquipOut03);
        this.EquipLIn03.addChild(this.EquipLIn06a);
        this.LegRight01.addChild(this.ShoeL03_1);
        this.EquipLIn02.addChild(this.EquipLIn03);
        this.BodyMain.addChild(this.ArmLeft01);
        this.EquipC01b_2.addChild(this.EquipC01c_2);
        this.EquipC01b.addChild(this.EquipC01c);
        this.EquipOut03_1.addChild(this.EquipOut04_1);
        this.EquipC01b.addChild(this.EquipC01e);
        this.EquipC01b_1.addChild(this.EquipC01e_1);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.setRotateAngle(GlowNeck, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.0F, -0.7F);
        
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
    	
    	switch (((IShipEmotion)entity).getScaleLevel())
    	{
    	case 3:
    		scale = 1.72F;
        	offsetY = -0.58F;
		break;
    	case 2:
    		scale = 1.29F;
        	offsetY = -0.27F;
		break;
    	case 1:
    		scale = 0.86F;
        	offsetY = 0.35F;
		break;
    	default:
    		scale = 0.43F;
        	offsetY = 2F;
		break;
    	}
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.scale(scale, scale, scale);
    	GlStateManager.translate(0F, offsetY, 0F);
    	
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
  		switch (ent.getStateEmotion(ID.S.State))
  		{
  		case ID.State.EQUIP00:
  			this.Hat01.isHidden = false;
  			this.EquipBag00.isHidden = true;
  		break;
  		case ID.State.EQUIP01:
  			this.Hat01.isHidden = true;
  			this.EquipBag00.isHidden = false;
  		break;
  		case ID.State.EQUIP02:
  			this.Hat01.isHidden = false;
  			this.EquipBag00.isHidden = false;
  		break;
  		default:  //normal
  			this.Hat01.isHidden = true;
  			this.EquipBag00.isHidden = true;
  		break;
  		}
  		
  		switch (ent.getStateEmotion(ID.S.State2))
  		{
  		case ID.State.EQUIP00a:
  			this.EquipBase.isHidden = false;
  			this.ShoeL01.isHidden = true;
  			this.ShoeR01.isHidden = true;
  			this.ShoeL03.isHidden = false;
  			this.ShoeL03_1.isHidden = false;
  		break;
  		case ID.State.EQUIP01a:
  			this.EquipBase.isHidden = true;
  			this.ShoeL01.isHidden = false;
  			this.ShoeR01.isHidden = false;
  			this.ShoeL03.isHidden = true;
  			this.ShoeL03_1.isHidden = true;
  		break;
  		case ID.State.EQUIP02a:
  			this.EquipBase.isHidden = false;
  			this.ShoeL01.isHidden = false;
  			this.ShoeR01.isHidden = false;
  			this.ShoeL03.isHidden = false;
  			this.ShoeL03_1.isHidden = false;
  		break;
  		default:  //normal
  			this.EquipBase.isHidden = true;
  			this.ShoeL01.isHidden = true;
  			this.ShoeR01.isHidden = true;
  			this.ShoeL03.isHidden = true;
  			this.ShoeL03_1.isHidden = true;
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
    	GlStateManager.translate(0F, 0.58F + 0.26F * ent.getScaleLevel(), 0F);
		this.setFaceHungry(ent);

    	//body
    	this.Head.rotateAngleX = 0.55F;
	  	this.Head.rotateAngleY = -0.2F;
    	this.BodyMain.rotateAngleX = -0.7F;
	  	this.BodyMain.rotateAngleY = -0.2618F;
	  	this.BodyMain.rotateAngleZ = -0.5236F;
	  	this.Butt.rotateAngleX = -0.2618F;
	  	this.Cloth01.rotateAngleX = 0.3F;
	  	//skirt
	  	this.Skirt01.rotateAngleX = -0.2443F;
    	//arm
	  	this.ArmLeft01.rotateAngleX = -0.2618F;
	  	this.ArmLeft01.rotateAngleY = 0.7F;
	    this.ArmLeft01.rotateAngleZ = -0.5236F;
	    this.ArmLeft02.rotateAngleX = -2.1F;
	    this.ArmLeft02.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetZ = -0.31F;
		this.ArmRight01.rotateAngleX = 0.7F;
		this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = 0.5236F;
		this.ArmRight02.rotateAngleX = -1.45F;
		this.ArmRight02.rotateAngleY = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
    	//leg
		this.LegLeft01.rotateAngleX = -0.79F;
		this.LegLeft01.rotateAngleY = 0F;
    	this.LegLeft01.rotateAngleZ = -0.14F;
    	this.LegLeft02.rotateAngleX = 1.4F;
    	this.LegRight01.rotateAngleX = -0.7F;
    	this.LegRight01.rotateAngleY = -0.4363F;
    	this.LegRight01.rotateAngleZ = 0F;
    	this.LegRight02.rotateAngleX = 0.7F;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2*0.08F + f * 0.25F);
  		float angleX1 = MathHelper.cos(f2*0.1F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.1F + 0.6F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		float headX = 0F;
  		float headZ = 0F;
  		float t2 = ent.getTickExisted() & 511;
  		boolean spcStand = false;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}

    	//leg move
  		addk1 = angleAdd1 * 0.3F - 0.28F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.3F - 0.21F;  //LegRight01
    	
  	    //head
	  	this.Head.rotateAngleX = f4 * 0.014F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	  	this.Ahoke.rotateAngleY = angleX * 0.15F + 0.65F;
	    //boob
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.8F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.8F;
	  	//body
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.35F;
	  	this.Skirt01.rotateAngleX = -0.07F;
	  	//cloth
	  	this.Hat03.rotateAngleX = angleX * 0.05F + 0.26F;
	  	this.Cloth01.rotateAngleX = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F + 0.3F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.25F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.25F - 0.087F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.25F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
		this.ArmRight02.offsetZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.0873F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.0873F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
		this.EquipCannonBase.rotateAngleY = this.Head.rotateAngleY * 0.35F;
		this.EquipC01b.rotateAngleX = this.Head.rotateAngleY;
		this.EquipC01b_1.rotateAngleX = this.Head.rotateAngleX + 1.2F;
		this.EquipC01b_2.rotateAngleX = this.Head.rotateAngleX + 1.2F;
		this.EquipC01c_1.rotateAngleZ = -this.Head.rotateAngleY * 0.5F;
		this.EquipC01e_1.rotateAngleZ = -this.Head.rotateAngleY * 0.5F;
		this.EquipC01c_2.rotateAngleZ = -this.Head.rotateAngleY * 0.5F;
		this.EquipC01e_2.rotateAngleZ = -this.Head.rotateAngleY * 0.5F;
		
		//special stand pos
		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		{
			spcStand = true;

			this.Head.rotateAngleY *= 0.25F;
			this.ArmLeft01.rotateAngleX = -0.3490658503988659F;
			this.ArmLeft01.rotateAngleY = 0.0F;
			this.ArmLeft01.rotateAngleZ = 0.4553564018453205F;
			this.ArmLeft02.rotateAngleX = 0.0F;
			this.ArmLeft02.rotateAngleY = 0.0F;
			this.ArmLeft02.rotateAngleZ = 1.0471975511965976F;
			this.ArmRight01.rotateAngleX = -0.5462880558742251F;
			this.ArmRight01.rotateAngleY = -0.2617993877991494F;
			this.ArmRight01.rotateAngleZ = -0.13962634015954636F;
			this.ArmRight02.rotateAngleX = -2.530727415391778F;
			this.ArmRight02.rotateAngleZ = 0.0F;
			this.ArmRight02.offsetZ = -0.32F;
			
			if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED)
			{
				this.setFace(8);
			}
		}
		else if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED)
		{
			spcStand = true;
			this.setFaceHappy(ent);
			
			//arm
			this.ArmLeft01.rotateAngleX = -3.14F;
			this.ArmLeft01.rotateAngleY = 0.0F;
			this.ArmLeft01.rotateAngleZ = 0.52F;
			this.ArmLeft02.rotateAngleX = 0F;
			this.ArmLeft02.rotateAngleY = 0F;
			this.ArmLeft02.rotateAngleZ = 0F;
			this.ArmLeft02.offsetX = 0F;
			this.ArmLeft02.offsetZ = 0F;
			this.ArmRight01.rotateAngleX = -3.14F;
			this.ArmRight01.rotateAngleY = 0.0F;
			this.ArmRight01.rotateAngleZ = -0.52F;
			this.ArmRight02.rotateAngleX = 0F;
			this.ArmRight02.rotateAngleY = 0F;
			this.ArmRight02.rotateAngleZ = 0F;
			this.ArmRight02.offsetX = 0F;
			this.ArmRight02.offsetZ = 0F;
		}

	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {
	    	//無跑步動作
  		}
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {
	    	//潛行, 蹲下動作
	    	GlStateManager.translate(0F, 0.03F + this.scale * 0.06F, 0F);
	    	
	    	//body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.4F;
		  	//skirt
		  	this.Skirt01.rotateAngleX = -0.24F;
		  	//arm
	  		this.ArmLeft01.rotateAngleX = -0.6F;
	  		this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
			this.ArmLeft02.rotateAngleX = 0F;
			this.ArmLeft02.rotateAngleY = 0F;
			this.ArmLeft02.rotateAngleZ = 0F;
			this.LegLeft02.offsetX = 0F;
			this.LegLeft02.offsetY = 0F;
			this.LegLeft02.offsetZ = 0F;
		    this.ArmRight01.rotateAngleX = -0.6F;
		    this.ArmRight01.rotateAngleY = 0F;
		    this.ArmRight01.rotateAngleZ = -0.2618F;
			this.ArmRight02.rotateAngleX = 0F;
			this.ArmRight02.rotateAngleZ = 0F;
			this.ArmRight02.rotateAngleZ = 0F;
			this.ArmRight02.offsetX = 0F;
			this.ArmRight02.offsetY = 0F;
			this.ArmRight02.offsetZ = 0F;
		    //leg
		    addk1 -= 0.4F;
		    addk2 -= 0.4F;
  		}//end if sneaking
  		
	    //坐下動作
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {
	    	//if caressing
	    	if (ent.getStateEmotion(ID.S.Emotion3) == ID.Emotion3.CARESS)
	    	{
		    	GlStateManager.translate(0F, 0.34F, 0F);
		    	//body
		    	this.Head.rotateAngleX -= 0.91F;
		    	this.BodyMain.rotateAngleX = 0.7F;
		    	this.BodyMain.rotateAngleY = 0F;
		    	this.BodyMain.rotateAngleZ = 0F;
		    	//skirt
		    	this.Skirt01.rotateAngleX = -0.24F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -0.45F;
		    	this.ArmLeft01.rotateAngleY = 0.0F;
		    	this.ArmLeft01.rotateAngleZ = 0.21F;
		    	this.ArmRight01.rotateAngleX = -0.45F;
		    	this.ArmRight01.rotateAngleY = 0.0F;
		    	this.ArmRight01.rotateAngleZ = -0.21F;
		    	//leg
		    	addk1 = -1.59F;
		    	addk2 = -1.59F;
		    	this.LegLeft01.rotateAngleY = 0.0F;
		    	this.LegLeft01.rotateAngleZ = 0.09F;
		    	this.LegLeft02.rotateAngleX = 2.1F;
		    	this.LegLeft02.rotateAngleY = 0.0F;
		    	this.LegLeft02.rotateAngleZ = 0.0F;
		    	this.LegLeft02.offsetZ = 0.37F;
		    	this.LegRight01.rotateAngleY = 0.0F;
		    	this.LegRight01.rotateAngleZ = -0.09F;
		    	this.LegRight02.rotateAngleX = 2.1F;
		    	this.LegRight02.rotateAngleY = 0F;
		    	this.LegRight02.rotateAngleZ = 0F;
		    	this.LegRight02.offsetZ = 0.37F;
	    	}
	    	else
	    	{
		    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
			    	GlStateManager.translate(0F, 0.58F, 0F);
			    	this.setFlush(true);
					//body
					this.Head.rotateAngleX = 0.55F;
				  	this.Head.rotateAngleY = -0.2F;
					this.BodyMain.rotateAngleX = -0.7F;
				  	this.BodyMain.rotateAngleY = -0.2618F;
				  	this.BodyMain.rotateAngleZ = -0.5236F;
				  	this.Butt.rotateAngleX = -0.2618F;
				  	this.Cloth01.rotateAngleX = 0.3F;
				  	//skirt
				  	this.Skirt01.rotateAngleX = -0.2443F;
					//arm
				  	this.ArmLeft01.rotateAngleX = -0.2618F;
				  	this.ArmLeft01.rotateAngleY = 0.7F;
				    this.ArmLeft01.rotateAngleZ = -0.5236F;
				    this.ArmLeft02.rotateAngleX = -2.1F;
				    this.ArmLeft02.rotateAngleY = 0F;
				    this.ArmLeft02.rotateAngleZ = 0F;
				    this.ArmLeft02.offsetZ = -0.31F;
					this.ArmRight01.rotateAngleX = 0.7F;
					this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = 0.5236F;
					this.ArmRight02.rotateAngleX = -1.45F;
					this.ArmRight02.rotateAngleY = 0F;
					this.ArmRight02.rotateAngleZ = 0F;
					//leg
					addk1 = -0.79F;
					addk2 = -0.7F;
					this.LegLeft01.rotateAngleY = 0F;
					this.LegLeft01.rotateAngleZ = -0.14F;
					this.LegLeft02.rotateAngleX = 1.4F;
					this.LegRight01.rotateAngleY = -0.4363F;
					this.LegRight01.rotateAngleZ = 0F;
					this.LegRight02.rotateAngleX = 0.7F;
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.35F, 0F);
		
			    	//Body
				  	this.Head.rotateAngleX -= 0.1F;
				  	this.BodyMain.rotateAngleX = 0F;
			    	this.Butt.rotateAngleX = -0.2F;
			    	this.Butt.offsetY = 0F;
			    	//skirt
			    	this.Skirt01.rotateAngleX = -0.15F;
			    	//arm
			    	if (!spcStand)
			    	{
					  	this.ArmLeft01.rotateAngleX = -0.4F;
					  	this.ArmLeft01.rotateAngleZ = 0.2618F;
				    	this.ArmRight01.rotateAngleX = -0.4F;
						this.ArmRight01.rotateAngleZ = -0.2618F;
			    	}
				  	//leg
				  	addk1 = -0.65F;
				  	addk2 = -0.65F;
				  	this.LegLeft01.rotateAngleY = 0.1F;
					this.LegLeft01.rotateAngleZ = 0F;
					this.LegLeft02.offsetZ = 0.375F;
					this.LegLeft02.rotateAngleX = 2.45F;
					this.LegLeft02.rotateAngleZ = 0.0175F;
					this.LegRight01.rotateAngleY = -0.1F;
					this.LegRight01.rotateAngleZ = 0F;
					this.LegRight02.offsetZ = 0.375F;
					this.LegRight02.rotateAngleX = 2.45F;
					this.LegRight02.rotateAngleZ = -0.0175F;
		    	}
	    	}
  		}//end if sitting
	    
	    //攻擊動作: 設為30~50會有揮刀動作, 設為100則沒有揮刀動作
	    if (ent.getAttackTick() > 30)
	    {
			//arm
			this.ArmLeft01.rotateAngleX = -3.14F;
			this.ArmLeft01.rotateAngleY = 0.0F;
			this.ArmLeft01.rotateAngleZ = 0.52F;
			this.ArmLeft02.rotateAngleX = 0F;
			this.ArmLeft02.rotateAngleY = 0F;
			this.ArmLeft02.rotateAngleZ = 0F;
			this.ArmLeft02.offsetX = 0F;
			this.ArmLeft02.offsetZ = 0F;
			this.ArmRight01.rotateAngleX = -3.14F;
			this.ArmRight01.rotateAngleY = 0.0F;
			this.ArmRight01.rotateAngleZ = -0.52F;
			this.ArmRight02.rotateAngleX = 0F;
			this.ArmRight02.rotateAngleY = 0F;
			this.ArmRight02.rotateAngleZ = 0F;
			this.ArmRight02.offsetX = 0F;
			this.ArmRight02.offsetZ = 0F;
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.4F;
	        this.ArmRight01.rotateAngleY = 0F;
	        this.ArmRight01.rotateAngleZ = -0.2F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	  	}
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
	@Override
	public void setFaceNormal(IShipEmotion ent)
	{
		this.setFace(0);
		this.setMouth(0);
	}
	
	@Override
	public void setFaceBored(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 511;
		
		if (t < 170)
		{
			this.setFace(5);
			
			if (t < 80)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(2);
			}
		}
		else if (t < 340)
		{
			this.setFace(8);
			this.setMouth(0);
		}
		else
		{
			this.setFace(0);
			this.setMouth(0);
		}
	}
	
	@Override
	public void setFaceShy(IShipEmotion ent)
	{
		this.setFlush(true);
		
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 511;
		
		if (t < 180)
		{
			this.setFace(0);
			
			if (t < 80)
			{
				this.setMouth(3);
			}
			else
			{
				this.setMouth(2);
			}
		}
		else if (t < 360)
		{
			this.setFace(8);
			this.setMouth(0);
		}
		else
		{
			this.setFace(5);
			this.setMouth(2);
		}
	}
    
    
}