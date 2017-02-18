package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.handler.EventHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelDestroyerHime - PinkaLulan 2017/2/17
 * Created using Tabula 5.1.0
 */
public class ModelDestroyerHime extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft01;
    public ModelRenderer Cloth01;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Hair02;
    public ModelRenderer Hat01;
    public ModelRenderer HairU01;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer Hair01;
    public ModelRenderer Hair03;
    public ModelRenderer Hair04;
    public ModelRenderer Hair05;
    public ModelRenderer Hair06;
    public ModelRenderer Hat02a;
    public ModelRenderer Hat03;
    public ModelRenderer Hat04a;
    public ModelRenderer Hat05a;
    public ModelRenderer Hat06a;
    public ModelRenderer Hat06b;
    public ModelRenderer Hat02b;
    public ModelRenderer Hat04b;
    public ModelRenderer Hat04c;
    public ModelRenderer Hat05b;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight01;
    public ModelRenderer EquipLegL;
    public ModelRenderer EquipLegR;
    public ModelRenderer EquipBaseL;
    public ModelRenderer EquipBaseR;
    public ModelRenderer BeltBase;
    public ModelRenderer LegLeft02;
    public ModelRenderer LegRight02;
    public ModelRenderer EquipLHead;
    public ModelRenderer EquipLJaw;
    public ModelRenderer EquipLB;
    public ModelRenderer EquipLT01;
    public ModelRenderer EquipLTU;
    public ModelRenderer EquipHeadC01;
    public ModelRenderer EquipHeadC02;
    public ModelRenderer EquipLTD;
    public ModelRenderer EquipLT02a;
    public ModelRenderer EquipLT02b;
    public ModelRenderer EquipLT02c;
    public ModelRenderer EquipLT02d;
    public ModelRenderer EquipRHead;
    public ModelRenderer EquipLJaw_1;
    public ModelRenderer EquipLB_1;
    public ModelRenderer EquipLT01_1;
    public ModelRenderer EquipLTU_1;
    public ModelRenderer EquipHeadC01_1;
    public ModelRenderer EquipHeadC02_1;
    public ModelRenderer EquipLTD_1;
    public ModelRenderer EquipLT02a_1;
    public ModelRenderer EquipLT02b_1;
    public ModelRenderer EquipLT02c_1;
    public ModelRenderer EquipLT02d_1;
    public ModelRenderer Belt01;
    public ModelRenderer Belt02;
    public ModelRenderer Belt03;
    public ModelRenderer Belt04;
    public ModelRenderer Belt05;
    public ModelRenderer Belt06;
    public ModelRenderer Belt07;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight02a;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft02a;
    public ModelRenderer Cannon01;
    public ModelRenderer Cannon02;
    public ModelRenderer Cannon03;
    public ModelRenderer Cannon04;
    public ModelRenderer Cannon05;
    public ModelRenderer Cloth02;
    public ModelRenderer Skirt01;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    

    public ModelDestroyerHime()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.scale = 0.44F;
        this.offsetY = 1.9F;
        this.offsetItem = new float[] {0.07F, 1F, -0.07F};
        this.offsetBlock = new float[] {0.07F, 1F, -0.07F};
        
        this.setDefaultFaceModel();
        
        this.LegLeft02 = new ModelRenderer(this, 0, 63);
        this.LegLeft02.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.EquipRHead = new ModelRenderer(this, 0, 0);
        this.EquipRHead.setRotationPoint(-9.0F, -2.0F, 0.0F);
        this.EquipRHead.addBox(-5.5F, -6.0F, -10.0F, 11, 6, 14, 0.0F);
        this.setRotateAngle(EquipRHead, -0.13962634015954636F, 0.0F, 0.0F);
        this.Belt05 = new ModelRenderer(this, 0, 2);
        this.Belt05.setRotationPoint(0.0F, -1.0F, 4.0F);
        this.Belt05.addBox(0.0F, 0.0F, -1.0F, 9, 4, 1, 0.0F);
        this.setRotateAngle(Belt05, 0.0F, 0.10471975511965977F, 0.0F);
        this.Cloth02 = new ModelRenderer(this, 38, 47);
        this.Cloth02.setRotationPoint(0.0F, 5.4F, -4.3F);
        this.Cloth02.addBox(-4.0F, 0.0F, 0.0F, 8, 8, 0, 0.0F);
        this.setRotateAngle(Cloth02, 0.05235987755982988F, 0.0F, 0.0F);
        this.EquipLT02b = new ModelRenderer(this, 0, 0);
        this.EquipLT02b.setRotationPoint(2.0F, -0.3F, -5.8F);
        this.EquipLT02b.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.EquipLT01_1 = new ModelRenderer(this, 0, 0);
        this.EquipLT01_1.setRotationPoint(-15.0F, 0.0F, 0.0F);
        this.EquipLT01_1.addBox(-4.0F, -5.0F, -6.0F, 4, 11, 10, 0.0F);
        this.setRotateAngle(EquipLT01_1, 0.0F, 0.13962634015954636F, 0.0F);
        this.EquipLegL = new ModelRenderer(this, 19, 3);
        this.EquipLegL.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.EquipLegL.addBox(-3.5F, 0.0F, -3.5F, 7, 9, 7, 0.0F);
        this.setRotateAngle(EquipLegL, -0.2792526803190927F, 0.0F, 0.08726646259971647F);
        this.EquipLT02a_1 = new ModelRenderer(this, 0, 0);
        this.EquipLT02a_1.setRotationPoint(-2.0F, -2.6F, -5.8F);
        this.EquipLT02a_1.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 86);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.2617993877991494F, 0.0F, 0.7853981633974483F);
        this.Hat04a = new ModelRenderer(this, 0, 0);
        this.Hat04a.setRotationPoint(0.0F, -7.4F, -5.5F);
        this.Hat04a.addBox(-3.0F, -8.0F, -3.0F, 6, 6, 6, 0.0F);
        this.setRotateAngle(Hat04a, 0.2617993877991494F, 0.2617993877991494F, -0.17453292519943295F);
        this.EquipLHead = new ModelRenderer(this, 0, 0);
        this.EquipLHead.setRotationPoint(9.0F, -2.0F, 0.0F);
        this.EquipLHead.addBox(-5.5F, -6.0F, -10.0F, 11, 6, 14, 0.0F);
        this.setRotateAngle(EquipLHead, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipLT01 = new ModelRenderer(this, 0, 0);
        this.EquipLT01.setRotationPoint(15.0F, 0.0F, 0.0F);
        this.EquipLT01.addBox(0.0F, -5.0F, -6.0F, 4, 11, 10, 0.0F);
        this.setRotateAngle(EquipLT01, 0.0F, -0.13962634015954636F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 69);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipLT02d = new ModelRenderer(this, 0, 0);
        this.EquipLT02d.setRotationPoint(2.0F, 4.3F, -5.8F);
        this.EquipLT02d.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.ArmLeft02a = new ModelRenderer(this, 0, 0);
        this.ArmLeft02a.setRotationPoint(-2.5F, 6.5F, -2.4F);
        this.ArmLeft02a.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(ArmLeft02a, 0.05235987755982988F, 0.0F, 0.0F);
        this.Hair05 = new ModelRenderer(this, 40, 99);
        this.Hair05.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.Hair05.addBox(-2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F);
        this.setRotateAngle(Hair05, 0.5235987755982988F, 0.0F, 0.3490658503988659F);
        this.Skirt01 = new ModelRenderer(this, 74, 10);
        this.Skirt01.setRotationPoint(0.0F, 2.5F, -0.5F);
        this.Skirt01.addBox(-8.5F, 0.0F, -6.0F, 17, 7, 10, 0.0F);
        this.setRotateAngle(Skirt01, -0.20943951023931953F, 0.0F, 0.0F);
        this.Hat04b = new ModelRenderer(this, 0, 0);
        this.Hat04b.setRotationPoint(0.0F, -6.8F, -0.4F);
        this.Hat04b.addBox(-2.5F, -5.0F, -2.5F, 5, 5, 5, 0.0F);
        this.setRotateAngle(Hat04b, -0.5235987755982988F, 0.0F, 0.0F);
        this.HairR01 = new ModelRenderer(this, 90, 101);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-7.8F, 6.5F, -4.4F);
        this.HairR01.addBox(-0.5F, 0.0F, -1.5F, 1, 7, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.08726646259971647F, -0.08726646259971647F, -0.08726646259971647F);
        this.EquipLTD = new ModelRenderer(this, 47, 29);
        this.EquipLTD.mirror = true;
        this.EquipLTD.setRotationPoint(0.0F, 5.0F, 0.2F);
        this.EquipLTD.addBox(-4.5F, 0.0F, -9.0F, 9, 4, 9, 0.0F);
        this.setRotateAngle(EquipLTD, 0.08726646259971647F, 0.0F, 3.141592653589793F);
        this.EquipLT02b_1 = new ModelRenderer(this, 0, 0);
        this.EquipLT02b_1.setRotationPoint(-2.0F, -0.3F, -5.8F);
        this.EquipLT02b_1.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 69);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipLegR = new ModelRenderer(this, 9, 0);
        this.EquipLegR.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.EquipLegR.addBox(-3.5F, 0.0F, -3.5F, 7, 9, 7, 0.0F);
        this.setRotateAngle(EquipLegR, -0.20943951023931953F, 0.0F, -0.08726646259971647F);
        this.EquipLJaw_1 = new ModelRenderer(this, 0, 0);
        this.EquipLJaw_1.setRotationPoint(-9.0F, 0.0F, -1.2F);
        this.EquipLJaw_1.addBox(-4.0F, 4.0F, -9.0F, 8, 4, 9, 0.0F);
        this.setRotateAngle(EquipLJaw_1, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipLT02c = new ModelRenderer(this, 0, 0);
        this.EquipLT02c.setRotationPoint(2.0F, 2.0F, -5.8F);
        this.EquipLT02c.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.EquipLT02d_1 = new ModelRenderer(this, 0, 0);
        this.EquipLT02d_1.setRotationPoint(-2.0F, 4.3F, -5.8F);
        this.EquipLT02d_1.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.Hair04 = new ModelRenderer(this, 40, 99);
        this.Hair04.setRotationPoint(2.0F, 6.5F, 0.0F);
        this.Hair04.addBox(-2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F);
        this.setRotateAngle(Hair04, 0.2617993877991494F, 0.0F, -0.22759093446006054F);
        this.Belt01 = new ModelRenderer(this, 0, 8);
        this.Belt01.setRotationPoint(0.0F, -1.0F, -8.0F);
        this.Belt01.addBox(-9.0F, 0.0F, 0.0F, 9, 4, 1, 0.0F);
        this.setRotateAngle(Belt01, 0.0F, 0.10471975511965977F, 0.0F);
        this.Cannon01 = new ModelRenderer(this, 22, 21);
        this.Cannon01.setRotationPoint(-2.5F, 3.0F, -2.5F);
        this.Cannon01.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.EquipLJaw = new ModelRenderer(this, 0, 0);
        this.EquipLJaw.setRotationPoint(9.0F, 0.0F, -1.2F);
        this.EquipLJaw.addBox(-4.0F, 4.0F, -9.0F, 8, 4, 9, 0.0F);
        this.setRotateAngle(EquipLJaw, 0.17453292519943295F, 0.0F, 0.0F);
        this.Cannon03 = new ModelRenderer(this, 0, 21);
        this.Cannon03.setRotationPoint(0.0F, 2.3F, 0.0F);
        this.Cannon03.addBox(-3.5F, 0.0F, 0.0F, 7, 9, 4, 0.0F);
        this.setRotateAngle(Cannon03, 0.05235987755982988F, 0.0F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 90, 101);
        this.HairL01.setRotationPoint(7.8F, 7.0F, -4.4F);
        this.HairL01.addBox(-0.5F, 0.0F, -1.5F, 1, 7, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.13962634015954636F, 0.08726646259971647F, 0.08726646259971647F);
        this.Cannon04 = new ModelRenderer(this, 0, 0);
        this.Cannon04.setRotationPoint(-1.0F, 10.0F, 0.0F);
        this.Cannon04.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.BeltBase = new ModelRenderer(this, 0, 0);
        this.BeltBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BeltBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(BeltBase, 0.08726646259971647F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 47);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 84, 27);
        this.Cloth01.setRotationPoint(0.0F, -11.3F, 0.0F);
        this.Cloth01.addBox(-7.0F, 0.0F, -4.4F, 14, 7, 8, 0.0F);
        this.setRotateAngle(Cloth01, -0.08726646259971647F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 106, 31);
        this.Ahoke.setRotationPoint(-0.5F, -7.0F, -6.0F);
        this.Ahoke.addBox(0.0F, -6.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.20943951023931953F, 0.6981317007977318F, 0.0F);
        this.Hat05a = new ModelRenderer(this, 0, 0);
        this.Hat05a.setRotationPoint(2.0F, -10.0F, 6.0F);
        this.Hat05a.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Hat05a, -0.08726646259971647F, 0.5235987755982988F, 0.17453292519943295F);
        this.ArmRight02a = new ModelRenderer(this, 0, 0);
        this.ArmRight02a.mirror = true;
        this.ArmRight02a.setRotationPoint(2.5F, 6.5F, -2.4F);
        this.ArmRight02a.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(ArmRight02a, 0.05235987755982988F, 0.0F, 0.0F);
        this.Belt02 = new ModelRenderer(this, 0, 13);
        this.Belt02.setRotationPoint(0.0F, -1.0F, -8.0F);
        this.Belt02.addBox(0.0F, 0.0F, 0.0F, 9, 4, 1, 0.0F);
        this.setRotateAngle(Belt02, 0.0F, -0.10471975511965977F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.Hat03 = new ModelRenderer(this, 0, 0);
        this.Hat03.setRotationPoint(0.0F, -5.4F, 0.0F);
        this.Hat03.addBox(-8.5F, -6.0F, 0.0F, 17, 7, 9, 0.0F);
        this.setRotateAngle(Hat03, -0.27314402793711257F, 0.0F, 0.0F);
        this.Hat02b = new ModelRenderer(this, 0, 0);
        this.Hat02b.setRotationPoint(-0.7F, -5.0F, -2.9F);
        this.Hat02b.addBox(0.0F, -6.0F, -7.0F, 10, 7, 13, 0.0F);
        this.setRotateAngle(Hat02b, 0.17453292519943295F, -0.05235987755982988F, 0.05235987755982988F);
        this.Hat06b = new ModelRenderer(this, 44, 61);
        this.Hat06b.setRotationPoint(8.5F, -6.4F, 2.5F);
        this.Hat06b.addBox(0.0F, 0.0F, -2.0F, 0, 12, 4, 0.0F);
        this.setRotateAngle(Hat06b, 1.0471975511965976F, 0.08726646259971647F, -0.4363323129985824F);
        this.Hair01 = new ModelRenderer(this, 54, 44);
        this.Hair01.setRotationPoint(0.0F, 9.9F, 10.0F);
        this.Hair01.addBox(-7.5F, 0.0F, -7.0F, 15, 5, 7, 0.0F);
        this.setRotateAngle(Hair01, 0.15707963267948966F, 0.0F, 0.0F);
        this.EquipHeadC02 = new ModelRenderer(this, 0, 0);
        this.EquipHeadC02.setRotationPoint(0.0F, 1.5F, 0.5F);
        this.EquipHeadC02.addBox(-0.5F, -0.6F, -7.0F, 1, 1, 7, 0.0F);
        this.Cannon02 = new ModelRenderer(this, 52, 0);
        this.Cannon02.setRotationPoint(0.0F, -1.0F, 2.0F);
        this.Cannon02.addBox(-4.0F, 0.0F, -6.0F, 8, 13, 6, 0.0F);
        this.setRotateAngle(Cannon02, -0.08726646259971647F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.EquipHeadC02_1 = new ModelRenderer(this, 0, 0);
        this.EquipHeadC02_1.setRotationPoint(0.0F, 1.5F, 0.5F);
        this.EquipHeadC02_1.addBox(-0.5F, -0.6F, -7.0F, 1, 1, 7, 0.0F);
        this.Neck = new ModelRenderer(this, 0, 0);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.Neck.addBox(-3.5F, -2.0F, -4.9F, 7, 2, 8, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.Belt06 = new ModelRenderer(this, 0, 0);
        this.Belt06.setRotationPoint(0.0F, -1.0F, 4.0F);
        this.Belt06.addBox(-9.0F, 0.0F, -1.0F, 9, 4, 1, 0.0F);
        this.setRotateAngle(Belt06, 0.0F, -0.10471975511965977F, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.2792526803190927F, 0.0F, 0.08726646259971647F);
        this.Hair02 = new ModelRenderer(this, 9, 6);
        this.Hair02.setRotationPoint(6.5F, -10.0F, 3.5F);
        this.Hair02.addBox(0.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F);
        this.setRotateAngle(Hair02, 0.0F, -0.08726646259971647F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.20943951023931953F, 0.0F, -0.08726646259971647F);
        this.EquipLT02a = new ModelRenderer(this, 0, 0);
        this.EquipLT02a.setRotationPoint(2.0F, -2.6F, -5.8F);
        this.EquipLT02a.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.Hat04c = new ModelRenderer(this, 0, 0);
        this.Hat04c.setRotationPoint(0.0F, -3.9F, 0.0F);
        this.Hat04c.addBox(-2.0F, -5.0F, -2.0F, 4, 5, 4, 0.0F);
        this.setRotateAngle(Hat04c, -0.6108652381980153F, 0.0F, 0.0F);
        this.Belt03 = new ModelRenderer(this, 0, 11);
        this.Belt03.setRotationPoint(-8.9F, -1.0F, 2.8F);
        this.Belt03.addBox(0.0F, 0.0F, 0.0F, 9, 4, 1, 0.0F);
        this.setRotateAngle(Belt03, 0.0F, 1.5707963267948966F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 63);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Hair06 = new ModelRenderer(this, 40, 99);
        this.Hair06.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.Hair06.addBox(-2.0F, 0.0F, -2.0F, 4, 11, 4, 0.0F);
        this.setRotateAngle(Hair06, -0.2617993877991494F, 0.0F, 0.5235987755982988F);
        this.EquipLTU_1 = new ModelRenderer(this, 47, 29);
        this.EquipLTU_1.setRotationPoint(0.0F, -1.1F, -0.7F);
        this.EquipLTU_1.addBox(-4.5F, 0.0F, -9.0F, 9, 4, 9, 0.0F);
        this.setRotateAngle(EquipLTU_1, 0.08726646259971647F, 0.0F, 0.0F);
        this.Hat02a = new ModelRenderer(this, 0, 0);
        this.Hat02a.setRotationPoint(0.7F, -5.0F, -3.0F);
        this.Hat02a.addBox(-10.0F, -6.0F, -7.0F, 10, 7, 13, 0.0F);
        this.setRotateAngle(Hat02a, 0.17453292519943295F, 0.05235987755982988F, -0.05235987755982988F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.EquipBaseL = new ModelRenderer(this, 0, 0);
        this.EquipBaseL.setRotationPoint(7.0F, 10.0F, -3.0F);
        this.EquipBaseL.addBox(0.0F, -3.0F, -3.0F, 16, 6, 6, 0.0F);
        this.setRotateAngle(EquipBaseL, 0.05235987755982988F, -0.13962634015954636F, 0.13962634015954636F);
        this.Cannon05 = new ModelRenderer(this, 0, 0);
        this.Cannon05.setRotationPoint(1.0F, 10.0F, 0.0F);
        this.Cannon05.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 86);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.2617993877991494F, 0.0F, -0.7853981633974483F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.EquipBaseR = new ModelRenderer(this, 0, 0);
        this.EquipBaseR.setRotationPoint(-7.0F, 10.0F, -3.0F);
        this.EquipBaseR.addBox(-16.0F, -3.0F, -3.0F, 16, 6, 6, 0.0F);
        this.setRotateAngle(EquipBaseR, 0.05235987755982988F, 0.13962634015954636F, -0.13962634015954636F);
        this.EquipLTU = new ModelRenderer(this, 47, 29);
        this.EquipLTU.setRotationPoint(0.0F, -1.1F, -0.7F);
        this.EquipLTU.addBox(-4.5F, 0.0F, -9.0F, 9, 4, 9, 0.0F);
        this.setRotateAngle(EquipLTU, 0.08726646259971647F, 0.0F, 0.0F);
        this.Belt07 = new ModelRenderer(this, 0, 34);
        this.Belt07.setRotationPoint(8.8F, -2.1F, -4.0F);
        this.Belt07.addBox(0.0F, 0.0F, 0.0F, 1, 4, 4, 0.0F);
        this.setRotateAngle(Belt07, -0.5235987755982988F, 0.0F, 0.0F);
        this.Hair03 = new ModelRenderer(this, 40, 99);
        this.Hair03.setRotationPoint(1.7F, 0.0F, 0.0F);
        this.Hair03.addBox(0.0F, -3.0F, -2.0F, 4, 11, 4, 0.0F);
        this.setRotateAngle(Hair03, -0.08726646259971647F, 0.0F, -0.08726646259971647F);
        this.Belt04 = new ModelRenderer(this, 0, 6);
        this.Belt04.setRotationPoint(8.9F, -1.0F, 2.8F);
        this.Belt04.addBox(-9.0F, 0.0F, 0.0F, 9, 4, 1, 0.0F);
        this.setRotateAngle(Belt04, 0.0F, -1.5707963267948966F, 0.0F);
        this.EquipLB = new ModelRenderer(this, 0, 0);
        this.EquipLB.setRotationPoint(9.0F, -3.5F, -5.0F);
        this.EquipLB.addBox(-5.0F, 0.0F, 0.0F, 10, 10, 8, 0.0F);
        this.setRotateAngle(EquipLB, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipLT02c_1 = new ModelRenderer(this, 0, 0);
        this.EquipLT02c_1.setRotationPoint(-2.0F, 2.0F, -5.8F);
        this.EquipLT02c_1.addBox(-1.0F, -1.0F, -4.0F, 2, 2, 4, 0.0F);
        this.Hat06a = new ModelRenderer(this, 44, 61);
        this.Hat06a.mirror = true;
        this.Hat06a.setRotationPoint(8.5F, -6.0F, 2.0F);
        this.Hat06a.addBox(0.0F, 0.0F, -2.0F, 0, 12, 4, 0.0F);
        this.setRotateAngle(Hat06a, 0.6981317007977318F, 0.2617993877991494F, -0.6981317007977318F);
        this.Hat01 = new ModelRenderer(this, 0, 0);
        this.Hat01.setRotationPoint(0.0F, -11.0F, 1.0F);
        this.Hat01.addBox(-6.0F, -6.0F, -6.0F, 12, 6, 12, 0.0F);
        this.setRotateAngle(Hat01, -0.4363323129985824F, 0.0F, 0.0F);
        this.Hat05b = new ModelRenderer(this, 0, 0);
        this.Hat05b.setRotationPoint(0.0F, -4.1F, 0.3F);
        this.Hat05b.addBox(-1.5F, -4.0F, -1.5F, 3, 4, 3, 0.0F);
        this.setRotateAngle(Hat05b, 0.6108652381980153F, 0.0F, 0.0F);
        this.EquipLTD_1 = new ModelRenderer(this, 47, 29);
        this.EquipLTD_1.setRotationPoint(0.0F, 5.0F, 0.2F);
        this.EquipLTD_1.addBox(-4.5F, 0.0F, -9.0F, 9, 4, 9, 0.0F);
        this.setRotateAngle(EquipLTD_1, 0.08726646259971647F, 0.0F, 3.141592653589793F);
        this.EquipHeadC01 = new ModelRenderer(this, 0, 0);
        this.EquipHeadC01.setRotationPoint(0.0F, -8.7F, -7.0F);
        this.EquipHeadC01.addBox(-2.5F, 0.0F, 0.0F, 5, 3, 9, 0.0F);
        this.setRotateAngle(EquipHeadC01, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipHeadC01_1 = new ModelRenderer(this, 0, 0);
        this.EquipHeadC01_1.setRotationPoint(0.0F, -8.7F, -7.0F);
        this.EquipHeadC01_1.addBox(-2.5F, 0.0F, 0.0F, 5, 3, 9, 0.0F);
        this.setRotateAngle(EquipHeadC01_1, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipLB_1 = new ModelRenderer(this, 0, 0);
        this.EquipLB_1.setRotationPoint(-9.0F, -3.5F, -5.0F);
        this.EquipLB_1.addBox(-5.0F, 0.0F, 0.0F, 10, 10, 8, 0.0F);
        this.setRotateAngle(EquipLB_1, 0.08726646259971647F, 0.0F, 0.0F);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipBaseR.addChild(this.EquipRHead);
        this.BeltBase.addChild(this.Belt05);
        this.Cloth01.addChild(this.Cloth02);
        this.EquipLT01.addChild(this.EquipLT02b);
        this.EquipBaseR.addChild(this.EquipLT01_1);
        this.Butt.addChild(this.EquipLegL);
        this.EquipLT01_1.addChild(this.EquipLT02a_1);
        this.BodyMain.addChild(this.ArmRight01);
        this.Hat01.addChild(this.Hat04a);
        this.EquipBaseL.addChild(this.EquipLHead);
        this.EquipBaseL.addChild(this.EquipLT01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipLT01.addChild(this.EquipLT02d);
        this.ArmLeft02.addChild(this.ArmLeft02a);
        this.Hair04.addChild(this.Hair05);
        this.Butt.addChild(this.Skirt01);
        this.Hat04a.addChild(this.Hat04b);
        this.Hair.addChild(this.HairR01);
        this.EquipLJaw.addChild(this.EquipLTD);
        this.EquipLT01_1.addChild(this.EquipLT02b_1);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Butt.addChild(this.EquipLegR);
        this.EquipBaseR.addChild(this.EquipLJaw_1);
        this.EquipLT01.addChild(this.EquipLT02c);
        this.EquipLT01_1.addChild(this.EquipLT02d_1);
        this.Hair03.addChild(this.Hair04);
        this.BeltBase.addChild(this.Belt01);
        this.ArmLeft02.addChild(this.Cannon01);
        this.EquipBaseL.addChild(this.EquipLJaw);
        this.Cannon01.addChild(this.Cannon03);
        this.Hair.addChild(this.HairL01);
        this.Cannon01.addChild(this.Cannon04);
        this.Butt.addChild(this.BeltBase);
        this.BodyMain.addChild(this.Butt);
        this.BodyMain.addChild(this.Cloth01);
        this.Hair.addChild(this.Ahoke);
        this.Hat01.addChild(this.Hat05a);
        this.ArmRight02.addChild(this.ArmRight02a);
        this.BeltBase.addChild(this.Belt02);
        this.Head.addChild(this.Hair);
        this.Hat01.addChild(this.Hat03);
        this.Hat01.addChild(this.Hat02b);
        this.Hat01.addChild(this.Hat06b);
        this.HairMain.addChild(this.Hair01);
        this.EquipHeadC01.addChild(this.EquipHeadC02);
        this.Cannon01.addChild(this.Cannon02);
        this.Neck.addChild(this.Head);
        this.EquipHeadC01_1.addChild(this.EquipHeadC02_1);
        this.BodyMain.addChild(this.Neck);
        this.BeltBase.addChild(this.Belt06);
        this.Butt.addChild(this.LegLeft01);
        this.Head.addChild(this.Hair02);
        this.Butt.addChild(this.LegRight01);
        this.EquipLT01.addChild(this.EquipLT02a);
        this.Hat04b.addChild(this.Hat04c);
        this.BeltBase.addChild(this.Belt03);
        this.LegRight01.addChild(this.LegRight02);
        this.Hair05.addChild(this.Hair06);
        this.EquipRHead.addChild(this.EquipLTU_1);
        this.Hat01.addChild(this.Hat02a);
        this.Hair.addChild(this.HairU01);
        this.Butt.addChild(this.EquipBaseL);
        this.Cannon01.addChild(this.Cannon05);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Head.addChild(this.HairMain);
        this.Butt.addChild(this.EquipBaseR);
        this.EquipLHead.addChild(this.EquipLTU);
        this.BeltBase.addChild(this.Belt07);
        this.Hair02.addChild(this.Hair03);
        this.BeltBase.addChild(this.Belt04);
        this.EquipBaseL.addChild(this.EquipLB);
        this.EquipLT01_1.addChild(this.EquipLT02c_1);
        this.Hat01.addChild(this.Hat06a);
        this.Head.addChild(this.Hat01);
        this.Hat05a.addChild(this.Hat05b);
        this.EquipLJaw_1.addChild(this.EquipLTD_1);
        this.EquipLHead.addChild(this.EquipHeadC01);
        this.EquipRHead.addChild(this.EquipHeadC01_1);
        this.EquipBaseR.addChild(this.EquipLB_1);
        
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
    	
    	this.scale = EventHandler.field1;
    	this.offsetY = EventHandler.field2;
    	
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
  		case ID.State.EQUIP00:
  			this.Hat01.isHidden = false;
  			this.BeltBase.isHidden = true;
  			this.ArmLeft02a.isHidden = true;
  			this.ArmRight02a.isHidden = true;
  		break;
  		case ID.State.EQUIP01:
  			this.Hat01.isHidden = true;
  			this.BeltBase.isHidden = false;
  			this.ArmLeft02a.isHidden = true;
  			this.ArmRight02a.isHidden = true;
  		break;
  		case ID.State.EQUIP02:
  			this.Hat01.isHidden = true;
  			this.BeltBase.isHidden = true;
  			this.ArmLeft02a.isHidden = false;
  			this.ArmRight02a.isHidden = false;
  		break;
  		case ID.State.EQUIP03:
  			this.Hat01.isHidden = false;
  			this.BeltBase.isHidden = false;
  			this.ArmLeft02a.isHidden = true;
  			this.ArmRight02a.isHidden = true;
  		break;
  		case ID.State.EQUIP04:
  			this.Hat01.isHidden = false;
  			this.BeltBase.isHidden = true;
  			this.ArmLeft02a.isHidden = false;
  			this.ArmRight02a.isHidden = false;
  		break;
  		case ID.State.EQUIP05:
  			this.Hat01.isHidden = true;
  			this.BeltBase.isHidden = false;
  			this.ArmLeft02a.isHidden = false;
  			this.ArmRight02a.isHidden = false;
  		break;
  		case ID.State.EQUIP06:
  			this.Hat01.isHidden = false;
  			this.BeltBase.isHidden = false;
  			this.ArmLeft02a.isHidden = false;
  			this.ArmRight02a.isHidden = false;
  		break;
  		default:  //normal
  			this.Hat01.isHidden = true;
  			this.BeltBase.isHidden = true;
  			this.ArmLeft02a.isHidden = true;
  			this.ArmRight02a.isHidden = true;
  		break;
  		}
  		
  		switch (ent.getStateEmotion(ID.S.State2))
  		{
  		case ID.State.EQUIP00a:
  			this.LegLeft01.isHidden = true;
  			this.LegRight01.isHidden = true;
  			this.EquipLegL.isHidden = false;
  			this.EquipLegR.isHidden = false;
  			this.EquipBaseL.isHidden = false;
  			this.EquipBaseR.isHidden = false;
  			this.Cannon01.isHidden = true;
  		break;
  		case ID.State.EQUIP01a:
  			this.LegLeft01.isHidden = true;
  			this.LegRight01.isHidden = true;
  			this.EquipLegL.isHidden = false;
  			this.EquipLegR.isHidden = false;
  			this.EquipBaseL.isHidden = true;
  			this.EquipBaseR.isHidden = true;
  			this.Cannon01.isHidden = false;
  		break;
  		case ID.State.EQUIP02a:
  			this.LegLeft01.isHidden = true;
  			this.LegRight01.isHidden = true;
  			this.EquipLegL.isHidden = false;
  			this.EquipLegR.isHidden = false;
  			this.EquipBaseL.isHidden = false;
  			this.EquipBaseR.isHidden = false;
  			this.Cannon01.isHidden = false;
  		break;
  		case ID.State.EQUIP03a:
  			this.LegLeft01.isHidden = false;
  			this.LegRight01.isHidden = false;
  			this.EquipLegL.isHidden = true;
  			this.EquipLegR.isHidden = true;
  			this.EquipBaseL.isHidden = true;
  			this.EquipBaseR.isHidden = true;
  			this.Cannon01.isHidden = true;
  		break;
  		case ID.State.EQUIP04a:
  			this.LegLeft01.isHidden = false;
  			this.LegRight01.isHidden = false;
  			this.EquipLegL.isHidden = false;
  			this.EquipLegR.isHidden = false;
  			this.EquipBaseL.isHidden = false;
  			this.EquipBaseR.isHidden = false;
  			this.Cannon01.isHidden = true;
  		break;
  		case ID.State.EQUIP05a:
  			this.LegLeft01.isHidden = false;
  			this.LegRight01.isHidden = false;
  			this.EquipLegL.isHidden = true;
  			this.EquipLegR.isHidden = true;
  			this.EquipBaseL.isHidden = true;
  			this.EquipBaseR.isHidden = true;
  			this.Cannon01.isHidden = false;
  		break;
  		case ID.State.EQUIP06a:
  			this.LegLeft01.isHidden = false;
  			this.LegRight01.isHidden = false;
  			this.EquipLegL.isHidden = false;
  			this.EquipLegR.isHidden = false;
  			this.EquipBaseL.isHidden = false;
  			this.EquipBaseR.isHidden = false;
  			this.Cannon01.isHidden = false;
  		break;
  		default:  //normal
  			this.LegLeft01.isHidden = true;
  			this.LegRight01.isHidden = true;
  			this.EquipLegL.isHidden = false;
  			this.EquipLegR.isHidden = false;
  			this.EquipBaseL.isHidden = true;
  			this.EquipBaseR.isHidden = true;
  			this.Cannon01.isHidden = true;
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
		this.EquipLegL.rotateAngleX = this.LegLeft01.rotateAngleX;
		this.EquipLegL.rotateAngleY = this.LegLeft01.rotateAngleY;
		this.EquipLegL.rotateAngleZ = this.LegLeft01.rotateAngleZ;
		this.EquipLegR.rotateAngleX = this.LegRight01.rotateAngleX;
		this.EquipLegR.rotateAngleY = this.LegRight01.rotateAngleY;
		this.EquipLegR.rotateAngleZ = this.LegRight01.rotateAngleZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, EventHandler.field3, 0F);
		this.setFaceHungry(ent);

	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
 		float angleX = MathHelper.cos(f2*0.08F + f * 0.25F);
  		float angleX1 = MathHelper.cos(f2*0.1F + 0.3F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		float headX = 0F;
  		float headZ = 0F;
  		float t2 = ent.getTickExisted() & 511;
  		boolean spStand = false;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D || ent.getStateEmotion(ID.S.State2) < ID.State.EQUIP03a)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}

    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.28F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.5F - 0.21F;  //LegRight01
    	
  	    //head
	  	this.Head.rotateAngleX = f4 * 0.01745F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	  	//body
  	    this.Ahoke.rotateAngleY = angleX * 0.03F + 0.7F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.35F;
//		//hair
//	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.14F + headX;
//	  	this.Hair01.rotateAngleZ = 0F;
//	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.12F + headX;
//	  	this.Hair02.rotateAngleZ = 0F;
//	    //arm
//	  	if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
//	  	{
//	  		this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.3F;
//	  		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.3F;
//	  	}
//	  	else
//	  	{
//	  		this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.15F;
//	  		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.15F;
//	  	}
//	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.4F + 0.1F;
//	  	this.ArmLeft01.rotateAngleY = 0F;
//	    this.ArmLeft02.rotateAngleX = 0F;
//	    this.ArmLeft02.rotateAngleZ = 0F;
//	    this.ArmLeft02.offsetX = 0F;
//	    this.ArmLeft02.offsetZ = 0F;
//	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.4F;
//	    this.ArmRight01.rotateAngleY = 0F;
//		this.ArmRight02.rotateAngleX = 0F;
//		this.ArmRight02.rotateAngleZ = 0F;
//		this.ArmRight02.offsetX = 0F;
//		this.ArmRight02.offsetZ = 0F;
//		//leg
//		this.LegLeft01.rotateAngleY = 0F;
//		this.LegLeft01.rotateAngleZ = 0.0873F;
//		this.LegLeft02.rotateAngleX = 0F;
//		this.LegLeft02.rotateAngleY = 0F;
//		this.LegLeft02.rotateAngleZ = 0F;
//		this.LegLeft02.offsetX = 0F;
//		this.LegLeft02.offsetZ = 0F;
//		this.LegRight01.rotateAngleY = 0F;
//		this.LegRight01.rotateAngleZ = -0.0873F;
//		this.LegRight02.rotateAngleX = 0F;
//		this.LegRight02.rotateAngleY = 0F;
//		this.LegRight02.rotateAngleZ = 0F;
//		this.LegRight02.offsetX = 0F;
//		this.LegRight02.offsetY = 0F;
//		this.LegRight02.offsetZ = 0F;
//		//equip
//		this.EquipLBase.rotateAngleX = 0F;
//		this.EquipLBase.rotateAngleY = 0F;
//		this.EquipLBase.rotateAngleZ = 0F;
//		this.EquipRBase.rotateAngleX = 0F;
//		this.EquipRBase.rotateAngleY = 0F;
//		this.EquipRBase.rotateAngleZ = 0F;
//		this.EquipCB02a.rotateAngleX = this.Head.rotateAngleX * 0.9F + 0.8F;
//		this.EquipCB02b.rotateAngleX = this.Head.rotateAngleX * 0.8F + 0.9F;
//		this.EquipCB04a.rotateAngleX = this.Head.rotateAngleX * 1.1F + 0.7F;
//		this.EquipCB04b.rotateAngleX = this.Head.rotateAngleX * 0.9F + 0.8F;
//		this.EquipLC01b.rotateAngleX = this.Head.rotateAngleX * 0.9F - 0.05F;
//		this.EquipLC01c.rotateAngleX = this.Head.rotateAngleX * 0.8F - 0.08F;
//		this.EquipLC02b.rotateAngleX = this.Head.rotateAngleX * 1.1F + 0.1F;
//		this.EquipLC03b.rotateAngleX = this.Head.rotateAngleX * 0.9F + 0.05F;
//		this.EquipLC03c.rotateAngleX = this.Head.rotateAngleX * 0.8F + 0.08F;
//		this.EquipRC01b.rotateAngleX = this.Head.rotateAngleX * 0.9F - 0.05F;
//		this.EquipRC01c.rotateAngleX = this.Head.rotateAngleX * 0.8F - 0.08F;
//		this.EquipRC02b.rotateAngleX = this.Head.rotateAngleX * 1.1F + 0.1F;
//		this.EquipRC03b.rotateAngleX = this.Head.rotateAngleX * 0.9F + 0.05F;
//		this.EquipRC03c.rotateAngleX = this.Head.rotateAngleX * 0.8F + 0.08F;
//		
//		//special stand pos
//		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED &&
//			ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01 && t2 > 400)
//		{
//			spStand = true;
//			
//			setFace(1);
//			GlStateManager.translate(0F, 0.12F, 0F);
//			//body
//			this.BodyMain.rotateAngleX = 1.0471975511965976F;
//			this.BodyMain.rotateAngleY = 0.0F;
//			this.BodyMain.rotateAngleZ = 0.0F;
//			this.Head.rotateAngleX -= 0.18203784098300857F;
//			//arm
//			this.ArmLeft01.rotateAngleX = -1.0471975511965976F;
//			this.ArmLeft01.rotateAngleY = 0.0F;
//			this.ArmLeft01.rotateAngleZ = -0.3490658503988659F;
//			this.ArmRight01.rotateAngleX = -1.0471975511965976F;
//			this.ArmRight01.rotateAngleY = 0.0F;
//			this.ArmRight01.rotateAngleZ = 0.3490658503988659F;
//			//leg
//			addk1 = -1.3962634015954636F;
//			addk2 = -1.3962634015954636F;
//			this.LegLeft01.rotateAngleY = 0.0F;
//			this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
//			this.LegRight01.rotateAngleY = 0.0F;
//			this.LegRight01.rotateAngleZ = -0.08726646259971647F;
//		}
//
//	    if (ent.getIsSprinting() || f1 > 0.9F)
//	    {
//	    	if (spStand) GlStateManager.translate(0F, -0.12F, 0F);
//	    	
//			//body
//			this.BodyMain.rotateAngleX = -0.1F;
//			this.BodyMain.rotateAngleY = 0F;
//			this.BodyMain.rotateAngleZ = 0F;
//		    //arm
//		  	if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
//		  	{
//		  		this.ArmLeft01.rotateAngleX = angleAdd2 * 0.05F + 0.5F;
//		  		this.ArmRight01.rotateAngleX = angleAdd1 * 0.05F + 0.5F;
//		  	}
//		  	else
//		  	{
//		  		this.ArmLeft01.rotateAngleX = angleAdd2 * 0.9F + 0.5F;
//		  		this.ArmRight01.rotateAngleX = angleAdd1 * 0.9F + 0.5F;
//		  	}
//		  	this.ArmLeft01.rotateAngleY = 0F;
//		    this.ArmLeft02.rotateAngleX = -1F;
//		    this.ArmLeft02.rotateAngleZ = 0F;
//		    this.ArmLeft02.offsetX = 0F;
//		    this.ArmLeft02.offsetZ = 0F;
//		    this.ArmRight01.rotateAngleY = 0F;
//			this.ArmRight02.rotateAngleX = -1F;
//			this.ArmRight02.rotateAngleZ = 0F;
//			this.ArmRight02.offsetX = 0F;
//			this.ArmRight02.offsetZ = 0F;
//			//leg
//	  		addk1 = angleAdd1 * 0.7F - 0.28F;
//		  	addk2 = angleAdd2 * 0.7F - 0.21F;
//			this.LegLeft01.rotateAngleY = 0F;
//			this.LegLeft01.rotateAngleZ = 0.0873F;
//			this.LegRight01.rotateAngleY = 0F;
//			this.LegRight01.rotateAngleZ = -0.0873F;
//			//equip
//			this.EquipLBase.rotateAngleX = 0.5F;
//			this.EquipLBase.rotateAngleY = 0F;
//			this.EquipLBase.rotateAngleZ = 0F;
//			this.EquipRBase.rotateAngleX = 0.5F;
//			this.EquipRBase.rotateAngleY = 0F;
//			this.EquipRBase.rotateAngleZ = 0F;
//  		}
//	    
//	    //head tilt angle
//	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
//	    
//	    if (ent.getIsSneaking())
//	    {
//	    	if (spStand) GlStateManager.translate(0F, -0.12F, 0F);
//	    	
//	    	GlStateManager.translate(0F, 0.09F, 0F);
//	    	//Body
//	    	this.Head.rotateAngleX -= 0.6283F;
//		  	this.BodyMain.rotateAngleX = 0.8727F;
//		    //arm
//		  	if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
//		  	{
//		  		this.ArmLeft01.rotateAngleX = angleAdd2 * 0.05F + 0.5F;
//		  		this.ArmLeft01.rotateAngleZ = -0.25F;
//		  		this.ArmLeft02.rotateAngleX = -1F;
//		  		this.ArmRight01.rotateAngleX = angleAdd1 * 0.05F + 0.5F;
//		  		this.ArmRight01.rotateAngleZ = 0.25F;
//		  		this.ArmRight02.rotateAngleX = -1F;
//		  	}
//		  	else
//		  	{
//			  	this.ArmLeft01.rotateAngleX = -0.35F;
//			    this.ArmLeft01.rotateAngleZ = 0.2618F;
//			    this.ArmLeft02.rotateAngleX = 0F;
//				this.ArmRight01.rotateAngleX = -0.35F;
//				this.ArmRight01.rotateAngleZ = -0.2618F;
//				this.ArmRight02.rotateAngleX = 0F;
//		  	}
//		  	this.ArmLeft01.rotateAngleY = 0F;
//		    this.ArmLeft02.rotateAngleZ = 0F;
//		    this.ArmLeft02.offsetX = 0F;
//		    this.ArmLeft02.offsetZ = 0F;
//		    this.ArmRight01.rotateAngleY = 0F;
//			this.ArmRight02.rotateAngleZ = 0F;
//			this.ArmRight02.offsetX = 0F;
//			this.ArmRight02.offsetZ = 0F;
//			//leg
//			addk1 -= 1.1F;
//			addk2 -= 1.1F;
//			//hair
//			this.Hair01.rotateAngleX += 0.37F;
//			this.Hair02.rotateAngleX += 0.23F;
//  		}//end if sneaking
//  		
//	    //坐下動作
//	    if (ent.getIsSitting() || ent.getIsRiding())
//	    {
//	    	if (spStand) GlStateManager.translate(0F, -0.12F, 0F);
//	    	
//	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
//	    	{
//				GlStateManager.translate(0F, 0.25F, 0F);
//				//body
//				this.BodyMain.rotateAngleX = -0.10471975511965977F;
//				this.BodyMain.rotateAngleY = -0.3490658503988659F;
//				this.BodyMain.rotateAngleZ = 0.0F;
//				this.Head.rotateAngleY -= 0.5235987755982988F;
//				//arm
//				this.ArmLeft01.rotateAngleX = 0.8726646259971648F;
//				this.ArmLeft01.rotateAngleY = 0.0F;
//				this.ArmLeft01.rotateAngleZ = -0.3490658503988659F;
//				this.ArmLeft02.rotateAngleX = -0.7853981633974483F;
//				this.ArmLeft02.rotateAngleY = 0.0F;
//				this.ArmLeft02.rotateAngleZ = 0.0F;
//				this.ArmRight01.rotateAngleX = -0.4363323129985824F;
//				this.ArmRight01.rotateAngleY = 0.0F;
//				this.ArmRight01.rotateAngleZ = 0.3490658503988659F;
//				this.ArmRight02.rotateAngleX = -0.8726646259971648F;
//				this.ArmRight02.rotateAngleY = 0.0F;
//				this.ArmRight02.rotateAngleZ = 0.0F;
//				//leg
//				addk1 = -1.48352986419518F;
//				addk2 = -0.4363323129985824F;
//				this.LegLeft01.rotateAngleY = 0.0F;
//				this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
//				this.LegLeft02.rotateAngleX = 1.3962634015954636F;
//				this.LegLeft02.rotateAngleY = 0.0F;
//				this.LegLeft02.rotateAngleZ = 0.0F;
//				this.LegRight01.rotateAngleY = 0.0F;
//				this.LegRight01.rotateAngleZ = -0.08726646259971647F;
//				this.LegRight02.rotateAngleX = 1.48352986419518F;
//				this.LegRight02.rotateAngleY = 0.0F;
//				this.LegRight02.rotateAngleZ = 0.0F;
//	    	}
//	    	else if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED &&
//		    		 ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
//	    	{
//		    	GlStateManager.translate(0F, 0.52F, 0F);
//		    	//body
//		    	this.BodyMain.rotateAngleX = 0.7853981633974483F;
//		    	this.Butt.rotateAngleX = 0.2617993877991494F;
//		    	this.Head.rotateAngleX = 0.5235987755982988F;
//		    	//hair
//		    	this.Hair01.rotateAngleX = -0.3490658503988659F;
//		    	this.Hair02.rotateAngleX = -0.12217304763960307F;
//		    	//arm
//		    	this.ArmLeft01.rotateAngleX = 2.6179938779914944F;
//		    	this.ArmLeft01.rotateAngleY = 0.0F;
//		    	this.ArmLeft01.rotateAngleZ = 0.0F;
//		    	this.ArmRight01.rotateAngleX = 2.6179938779914944F;
//		    	this.ArmRight01.rotateAngleY = 0.0F;
//		    	this.ArmRight01.rotateAngleZ = 0.0F;
//		    	//leg
//		    	addk1 = 0.2617993877991494F;
//		    	addk2 = 0.2617993877991494F;
//		    	this.LegLeft01.rotateAngleY = 0.0F;
//		    	this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
//		    	this.LegLeft02.rotateAngleX = 0.2617993877991494F;
//		    	this.LegLeft02.rotateAngleY = 0.0F;
//		    	this.LegLeft02.rotateAngleZ = 0.0F;
//		    	this.LegRight01.rotateAngleY = 0.0F;
//		    	this.LegRight01.rotateAngleZ = -0.08726646259971647F;
//		    	this.LegRight02.rotateAngleX = 0.2617993877991494F;
//		    	this.LegRight02.rotateAngleY = 0.0F;
//		    	this.LegRight02.rotateAngleZ = 0.0F;
//		    	//equip
//		    	this.EquipLBase.rotateAngleX = 1.2217304763960306F;
//		    	this.EquipLBase.rotateAngleY = 0.0F;
//		    	this.EquipLBase.rotateAngleZ = 0.0F;
//		    	this.EquipRBase.rotateAngleX = 1.2217304763960306F;
//		    	this.EquipRBase.rotateAngleY = 0.0F;
//		    	this.EquipRBase.rotateAngleZ = 0.0F;
//	    	}
//	    	else if (!this.EquipLBase.isHidden)
//	    	{
//		    	GlStateManager.translate(0F, 0.2F, 0F);
//		    	//body
//		    	this.BodyMain.rotateAngleX = 0.18203784098300857F;
//		    	this.Butt.rotateAngleX = 0.2617993877991494F;
//		    	this.Head.rotateAngleX -= 0.20943951023931953F;
//		    	//arm
//		    	this.ArmLeft01.rotateAngleX = 0.13962634015954636F;
//		    	this.ArmLeft01.rotateAngleY = 0.0F;
//		    	this.ArmLeft01.rotateAngleZ = -0.3490658503988659F;
//		    	this.ArmRight01.rotateAngleX = -1.1838568316277536F;
//		    	this.ArmRight01.rotateAngleY = 0.8F;
//		    	this.ArmRight01.rotateAngleZ = 0.0F;
//		    	this.ArmRight02.rotateAngleX = -1.3089969389957472F;
//		    	this.ArmRight02.rotateAngleY = 0.0F;
//		    	this.ArmRight02.rotateAngleZ = 0.0F;
//		    	//leg
//		    	addk1 = -1.61F;
//		    	addk2 = -1.57F;
//		    	this.LegLeft01.rotateAngleY = 0.0F;
//		    	this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
//		    	this.LegLeft02.rotateAngleX = 1.5F;
//		    	this.LegLeft02.rotateAngleY = 0.0F;
//		    	this.LegLeft02.rotateAngleZ = 0.0F;
//		    	this.LegRight01.rotateAngleY = 0.0F;
//		    	this.LegRight01.rotateAngleZ = -0.08726646259971647F;
//		    	this.LegRight02.rotateAngleX = 0.6F;
//		    	this.LegRight02.rotateAngleY = 0.0F;
//		    	this.LegRight02.rotateAngleZ = 0.0F;
//		    	//equip
//		    	this.EquipLBase.rotateAngleX = 0.0F;
//		    	this.EquipLBase.rotateAngleY = -1.5707963267948966F;
//		    	this.EquipLBase.rotateAngleZ = 0.3141592653589793F;
//		    	this.EquipRBase.rotateAngleX = 0.7285004297824331F;
//		    	this.EquipRBase.rotateAngleY = 0.0F;
//		    	this.EquipRBase.rotateAngleZ = 0.0F;
//	    	}
//	    	else
//	    	{
//		    	GlStateManager.translate(0F, 0.35F, 0F);
//		    	this.EquipLBase.isHidden = true;
//		    	this.EquipRBase.isHidden = true;
//		    	//body
//		    	this.BodyMain.rotateAngleX = 0.27314402793711257F;
//		    	this.Butt.rotateAngleX = 0.2617993877991494F;
//		    	this.Head.rotateAngleX -= 0.41887902047863906F;
//		    	//arm
//		    	this.ArmLeft01.rotateAngleX = 0.091106186954104F;
//		    	this.ArmLeft01.rotateAngleY = 0.0F;
//		    	this.ArmLeft01.rotateAngleZ = -0.6373942428283291F;
//		    	this.ArmLeft02.rotateAngleX = 0.0F;
//		    	this.ArmLeft02.rotateAngleY = 0.0F;
//		    	this.ArmLeft02.rotateAngleZ = 1.3658946726107624F;
//		    	this.ArmRight01.rotateAngleX = -0.85F;
//		    	this.ArmRight01.rotateAngleY = 0.0F;
//		    	this.ArmRight01.rotateAngleZ = 0.0F;
//		    	this.ArmRight02.rotateAngleX = 0.0F;
//		    	this.ArmRight02.rotateAngleY = 0.0F;
//		    	this.ArmRight02.rotateAngleZ = -0.5009094953223726F;
//		    	//leg
//		    	addk1 = -1.2747884856566583F;
//		    	addk2 = -2.1399481958702475F;
//		    	this.LegLeft01.rotateAngleY = 0.0F;
//		    	this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
//		    	this.LegLeft02.rotateAngleX = 2.321986036853256F;
//		    	this.LegLeft02.rotateAngleY = 0.0F;
//		    	this.LegLeft02.rotateAngleZ = 0.0F;
//		    	this.LegLeft02.offsetZ = 0.375F;
//		    	this.LegRight01.rotateAngleY = 0.0F;
//		    	this.LegRight01.rotateAngleZ = -0.08726646259971647F;
//		    	this.LegRight02.rotateAngleX = 1.5707963267948966F;
//		    	this.LegRight02.rotateAngleY = 0.0F;
//		    	this.LegRight02.rotateAngleZ = 0.0F;
//	    	}
//  		}//end if sitting
//	    
//	    //攻擊動作: 設為30~50會有揮刀動作, 設為100則沒有揮刀動作
//	    if (ent.getAttackTick() > 0)
//	    {
//	    	if (spStand) GlStateManager.translate(0F, -0.12F, 0F);
//	    	
//	    	//body
//		  	this.BodyMain.rotateAngleX = -0.1047F;
//		  	this.BodyMain.rotateAngleY = 0F;
//		  	this.BodyMain.rotateAngleZ = 0F;
//		  	this.Butt.rotateAngleX = 0.35F;
//	    	//arm
//		  	if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
//		  	{
//		  		this.ArmLeft02.rotateAngleX = -0.8726646259971648F;
//		  		this.ArmRight02.rotateAngleX = -0.8726646259971648F;
//		  	}
//		  	else
//		  	{
//		  		this.ArmLeft02.rotateAngleX = 0F;
//		  		this.ArmRight02.rotateAngleX = 0F;
//		  	}
//	    	this.ArmLeft01.rotateAngleX = -0.5235987755982988F;
//	    	this.ArmLeft01.rotateAngleY = -0.5235987755982988F;
//	    	this.ArmLeft01.rotateAngleZ = -0.2617993877991494F;
//	    	this.ArmLeft02.rotateAngleY = 0.0F;
//	    	this.ArmLeft02.rotateAngleZ = 0.0F;
//	    	this.ArmRight01.rotateAngleX = -0.5235987755982988F;
//	    	this.ArmRight01.rotateAngleY = 0.5235987755982988F;
//	    	this.ArmRight01.rotateAngleZ = 0.2617993877991494F;
//	    	this.ArmRight02.rotateAngleY = 0.0F;
//	    	this.ArmRight02.rotateAngleZ = 0.0F;
//	    	//leg move
//	  		addk1 = angleAdd1 * 0.5F - 0.28F;
//		  	addk2 = angleAdd2 * 0.5F - 0.21F;
//	    	//equip
//	    	this.EquipLBase.rotateAngleX = 0.0F;
//	    	this.EquipLBase.rotateAngleY = -0.2617993877991494F;
//	    	this.EquipLBase.rotateAngleZ = 0.3490658503988659F;
//	    	this.EquipRBase.rotateAngleX = 0.0F;
//	    	this.EquipRBase.rotateAngleY = 0.2617993877991494F;
//	    	this.EquipRBase.rotateAngleZ = -0.3490658503988659F;
//	    }
//	    
//	    //swing arm
//	  	float f6 = ent.getSwingTime(f2 - (int)f2);
//	  	if (f6 != 0F)
//	  	{
//	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
//	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
//	        this.ArmRight01.rotateAngleX = -0.4F;
//	        this.ArmRight01.rotateAngleY = 0F;
//	        this.ArmRight01.rotateAngleZ = -0.2F;
//	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
//	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
//	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
//	  	}
//	  	
//	  	//頭毛左右彎曲調整
//	    headX = this.Head.rotateAngleX * -0.5F;
//	    this.Hair01.rotateAngleX += headX;
//	  	this.Hair02.rotateAngleX += headX;
//		this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.09F;
//	  	this.HairL02.rotateAngleX = -angleX1 * 0.04F + headX + 0.12F;
//	  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.09F;
//	  	this.HairR02.rotateAngleX = -angleX1 * 0.04F + headX + 0.12F;
//	  	headZ = this.Head.rotateAngleZ * -0.5F;
//	    this.Hair01.rotateAngleZ = headZ;
//	  	this.Hair02.rotateAngleZ = headZ;
//	  	this.HairL01.rotateAngleZ = headZ + 0.05F;
//	  	this.HairL02.rotateAngleZ = headZ - 0.09F;
//	  	this.HairR01.rotateAngleZ = headZ - 0.05F;
//	  	this.HairR02.rotateAngleZ = headZ + 0.09F;
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
	
}