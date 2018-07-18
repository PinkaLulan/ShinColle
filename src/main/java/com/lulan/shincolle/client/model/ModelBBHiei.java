package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.proxy.ClientProxy;
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
 * ModelBBHiei - PinkaLulan 2018/2/10
 * Created using Tabula 6.0.0
 */
public class ModelBBHiei extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Butt;
    public ModelRenderer ArmLeft01;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer ArmRight01;
    public ModelRenderer Cloth03a1;
    public ModelRenderer Cloth03a2;
    public ModelRenderer EquipBase;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer EquipHeadBase;
    public ModelRenderer HairU01;
    public ModelRenderer Ahoke;
    public ModelRenderer Hair01;
    public ModelRenderer Hair01a;
    public ModelRenderer Hair02a;
    public ModelRenderer EquipHead01;
    public ModelRenderer EquipHead00;
    public ModelRenderer EquipHead01_1;
    public ModelRenderer EquipHead02;
    public ModelRenderer EquipHead03;
    public ModelRenderer EquipHead02_1;
    public ModelRenderer EquipHead03_1;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight01;
    public ModelRenderer SkirtB01;
    public ModelRenderer Cloth02a1;
    public ModelRenderer Cloth02b1;
    public ModelRenderer LegLeft02;
    public ModelRenderer Skirt02;
    public ModelRenderer LegRight02;
    public ModelRenderer Cloth01a;
    public ModelRenderer Cloth02c1;
    public ModelRenderer Cloth02c1_1;
    public ModelRenderer Cloth01b;
    public ModelRenderer Cloth01c;
    public ModelRenderer Cloth01b2;
    public ModelRenderer Cloth01c2;
    public ModelRenderer Cloth02c2;
    public ModelRenderer Cloth02c3;
    public ModelRenderer Cloth02c4;
    public ModelRenderer Cloth02c2_1;
    public ModelRenderer Cloth02c3_1;
    public ModelRenderer Cloth02c4_1;
    public ModelRenderer Cloth02a2;
    public ModelRenderer Cloth02a3;
    public ModelRenderer Cloth02b2;
    public ModelRenderer Cloth02b3;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ClothA01;
    public ModelRenderer ClothA02;
    public ModelRenderer ClothA03;
    public ModelRenderer ClothA04;
    public ModelRenderer ClothA05;
    public ModelRenderer Cloth03b;
    public ModelRenderer ClothB01;
    public ModelRenderer Cloth03b_1;
    public ModelRenderer ArmRight02;
    public ModelRenderer ClothA01_1;
    public ModelRenderer ClothA02a;
    public ModelRenderer ClothA03a;
    public ModelRenderer ClothA04a;
    public ModelRenderer ClothA05a;
    public ModelRenderer EquipD01a;
    public ModelRenderer EquipD02a;
    public ModelRenderer EquipD02b;
    public ModelRenderer EquipD01b;
    public ModelRenderer EquipD02c;
    public ModelRenderer EquipD02d;
    public ModelRenderer EquipD03a1;
    public ModelRenderer EquipD03b1;
    public ModelRenderer EquipD03c1;
    public ModelRenderer EquipD03d1;
    public ModelRenderer EquipD01aa;
    public ModelRenderer EquipD01ba;
    public ModelRenderer EquipD01bb;
    public ModelRenderer EquipD03a2;
    public ModelRenderer EquipD03aa;
    public ModelRenderer EquipD03ab;
    public ModelRenderer EquipD03a3;
    public ModelRenderer EquipD03a4;
    public ModelRenderer EquipB05;
    public ModelRenderer EquipCL1Base01L2;
    public ModelRenderer EquipCL1Base02;
    public ModelRenderer EquipCL1a1;
    public ModelRenderer EquipCL1a1_1;
    public ModelRenderer EquipCL1Base01a;
    public ModelRenderer EquipCL1a2;
    public ModelRenderer EquipCL1a2_1;
    public ModelRenderer EquipD03a2_1;
    public ModelRenderer EquipD03aa_1;
    public ModelRenderer EquipD03ab_1;
    public ModelRenderer EquipD03a3_1;
    public ModelRenderer EquipD03a4_1;
    public ModelRenderer EquipB05_1;
    public ModelRenderer EquipCL1Base01R2;
    public ModelRenderer EquipCL1Base02_1;
    public ModelRenderer EquipCL1a1_2;
    public ModelRenderer EquipCL1a1_3;
    public ModelRenderer EquipCL1Base01a_1;
    public ModelRenderer EquipCL1a2_2;
    public ModelRenderer EquipCL1a2_3;
    public ModelRenderer EquipD03c1a;
    public ModelRenderer EquipD03c1b;
    public ModelRenderer EquipD03c2;
    public ModelRenderer EquipD03c2a;
    public ModelRenderer EquipD03c3;
    public ModelRenderer EquipD03c3a;
    public ModelRenderer EquipB05_2;
    public ModelRenderer EquipCL1Base01L1;
    public ModelRenderer EquipCL1Base02_2;
    public ModelRenderer EquipCL1a1_4;
    public ModelRenderer EquipCL1a1_5;
    public ModelRenderer EquipCL1Base01a_2;
    public ModelRenderer EquipCL1Base01b;
    public ModelRenderer EquipCL1a2_4;
    public ModelRenderer EquipCL1a2_5;
    public ModelRenderer EquipD03c1a_1;
    public ModelRenderer EquipD03c1b_1;
    public ModelRenderer EquipD03c2_1;
    public ModelRenderer EquipD03c2a_1;
    public ModelRenderer EquipD03c3_1;
    public ModelRenderer EquipD03c3a_1;
    public ModelRenderer EquipB05_3;
    public ModelRenderer EquipCL1Base01R1;
    public ModelRenderer EquipCL1Base02_3;
    public ModelRenderer EquipCL1a1_6;
    public ModelRenderer EquipCL1a1_7;
    public ModelRenderer EquipCL1Base01a_3;
    public ModelRenderer EquipCL1Base01b_1;
    public ModelRenderer EquipCL1a2_6;
    public ModelRenderer EquipCL1a2_7;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowNeck;
    
    
    public ModelBBHiei()
    {
    	this.textureWidth = 256;
        this.textureHeight = 128;
        this.offsetItem = new float[] {0.1F, 0.86F, -0.12F};
        this.offsetBlock = new float[] {0.1F, 0.86F, -0.12F};
        
        this.setDefaultFaceModel();
        
        this.Hair02a = new ModelRenderer(this, 49, 40);
        this.Hair02a.mirror = true;
        this.Hair02a.setRotationPoint(-2.5F, 12.0F, 4.7F);
        this.Hair02a.addBox(-6.0F, -6.0F, 0.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(Hair02a, 0.12217304763960307F, 0.20943951023931953F, -0.9599310885968813F);
        this.EquipCL1Base01b_1 = new ModelRenderer(this, 109, 24);
        this.EquipCL1Base01b_1.setRotationPoint(0.0F, -5.6F, 2.0F);
        this.EquipCL1Base01b_1.addBox(-5.5F, 0.0F, 0.0F, 11, 2, 2, 0.0F);
        this.Cloth02a1 = new ModelRenderer(this, 59, 0);
        this.Cloth02a1.setRotationPoint(4.0F, 2.3F, -6.8F);
        this.Cloth02a1.addBox(-1.5F, 0.0F, 0.0F, 3, 5, 0, 0.0F);
        this.setRotateAngle(Cloth02a1, -0.4363323129985824F, 0.0F, -0.06981317007977318F);
        this.EquipCL1Base01R2 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base01R2.setRotationPoint(0.2F, 0.1F, 0.0F);
        this.EquipCL1Base01R2.addBox(-4.5F, -4.0F, -1.5F, 9, 4, 8, 0.0F);
        this.ClothA01 = new ModelRenderer(this, 128, 109);
        this.ClothA01.setRotationPoint(0.5F, 5.1F, 0.0F);
        this.ClothA01.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.EquipCL1Base02_1 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base02_1.setRotationPoint(0.0F, 0.3F, -2.8F);
        this.EquipCL1Base02_1.addBox(-4.5F, -4.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(EquipCL1Base02_1, 0.17453292519943295F, 0.0F, 0.0F);
        this.Hair01a = new ModelRenderer(this, 49, 40);
        this.Hair01a.setRotationPoint(2.5F, 12.0F, 4.7F);
        this.Hair01a.addBox(0.0F, -6.0F, 0.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(Hair01a, 0.12217304763960307F, -0.20943951023931953F, 0.9599310885968813F);
        this.Cloth02a2 = new ModelRenderer(this, 59, 0);
        this.Cloth02a2.setRotationPoint(0.0F, 4.9F, 0.0F);
        this.Cloth02a2.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth02a2, 0.24434609527920614F, 0.0F, 0.05235987755982988F);
        this.EquipD02d = new ModelRenderer(this, 0, 0);
        this.EquipD02d.setRotationPoint(-5.9F, 5.0F, 5.9F);
        this.EquipD02d.addBox(0.0F, 0.0F, 0.0F, 6, 12, 4, 0.0F);
        this.EquipCL1a2_1 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_1.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_1.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_1, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipCL1Base02_3 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base02_3.setRotationPoint(0.0F, 0.3F, -2.8F);
        this.EquipCL1Base02_3.addBox(-4.5F, -4.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(EquipCL1Base02_3, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipHead02 = new ModelRenderer(this, 45, 106);
        this.EquipHead02.setRotationPoint(2.4F, 0.8F, 1.2F);
        this.EquipHead02.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.EquipHead02_1 = new ModelRenderer(this, 43, 107);
        this.EquipHead02_1.setRotationPoint(-2.4F, 0.8F, 1.2F);
        this.EquipHead02_1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.EquipD02a = new ModelRenderer(this, 0, 0);
        this.EquipD02a.mirror = true;
        this.EquipD02a.setRotationPoint(3.4F, 5.0F, 1.7F);
        this.EquipD02a.addBox(-3.5F, 0.0F, -0.6F, 7, 14, 5, 0.0F);
        this.EquipD03d1 = new ModelRenderer(this, 0, 0);
        this.EquipD03d1.setRotationPoint(-5.0F, 5.5F, 7.0F);
        this.EquipD03d1.addBox(0.0F, -1.5F, 0.0F, 8, 1, 3, 0.0F);
        this.setRotateAngle(EquipD03d1, 0.0F, 3.141592653589793F, 0.3490658503988659F);
        this.Neck = new ModelRenderer(this, 24, 63);
        this.Neck.setRotationPoint(0.0F, -9.6F, 0.5F);
        this.Neck.addBox(-2.5F, -3.0F, -2.9F, 5, 3, 5, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 47);
        this.LegLeft02.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.EquipHead00 = new ModelRenderer(this, 45, 33);
        this.EquipHead00.setRotationPoint(0.0F, -4.1F, 5.0F);
        this.EquipHead00.addBox(-8.5F, 0.0F, 0.0F, 17, 5, 2, 0.0F);
        this.setRotateAngle(EquipHead00, 0.10471975511965977F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 54);
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipD01bb = new ModelRenderer(this, 22, 22);
        this.EquipD01bb.setRotationPoint(0.0F, 4.9F, 6.0F);
        this.EquipD01bb.addBox(-1.5F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
        this.Cloth02c1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c1.setRotationPoint(2.6F, 1.9F, 4.4F);
        this.Cloth02c1.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 0, 0.0F);
        this.setRotateAngle(Cloth02c1, 0.6283185307179586F, 0.0F, -0.08726646259971647F);
        this.ClothA05a = new ModelRenderer(this, 128, 96);
        this.ClothA05a.setRotationPoint(0.0F, 1.9F, 0.8F);
        this.ClothA05a.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 6, 0.0F);
        this.EquipD03b1 = new ModelRenderer(this, 107, 13);
        this.EquipD03b1.setRotationPoint(-5.0F, 5.8F, 7.5F);
        this.EquipD03b1.addBox(0.5F, -1.0F, -2.5F, 6, 2, 9, 0.0F);
        this.setRotateAngle(EquipD03b1, 0.0F, 3.141592653589793F, -0.5235987755982988F);
        this.EquipHead01 = new ModelRenderer(this, 43, 105);
        this.EquipHead01.setRotationPoint(6.7F, 0.2F, 5.7F);
        this.EquipHead01.addBox(0.0F, -0.7F, -0.3F, 2, 3, 3, 0.0F);
        this.ClothA03a = new ModelRenderer(this, 128, 65);
        this.ClothA03a.setRotationPoint(-0.1F, 1.9F, -2.2F);
        this.ClothA03a.addBox(-2.5F, 0.0F, 0.0F, 5, 9, 6, 0.0F);
        this.Cloth03a2 = new ModelRenderer(this, 159, 55);
        this.Cloth03a2.mirror = true;
        this.Cloth03a2.setRotationPoint(-4.1F, -11.1F, -4.1F);
        this.Cloth03a2.addBox(-1.0F, 0.0F, 0.0F, 2, 18, 7, 0.0F);
        this.EquipB05 = new ModelRenderer(this, 100, 30);
        this.EquipB05.setRotationPoint(1.5F, -0.4F, 4.5F);
        this.EquipB05.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 68);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.19198621771937624F, 0.0F, -0.08726646259971647F);
        this.ClothA04a = new ModelRenderer(this, 128, 81);
        this.ClothA04a.setRotationPoint(0.0F, 0.9F, 0.8F);
        this.ClothA04a.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.EquipCL1Base01a_1 = new ModelRenderer(this, 0, 9);
        this.EquipCL1Base01a_1.setRotationPoint(0.0F, -5.4F, -1.0F);
        this.EquipCL1Base01a_1.addBox(-2.5F, 0.0F, 0.0F, 5, 2, 1, 0.0F);
        this.setRotateAngle(EquipCL1Base01a_1, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipB05_1 = new ModelRenderer(this, 100, 30);
        this.EquipB05_1.setRotationPoint(1.5F, -0.4F, 4.5F);
        this.EquipB05_1.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(EquipB05_1, 0.0F, 3.141592653589793F, 0.0F);
        this.EquipHeadBase = new ModelRenderer(this, 40, 23);
        this.EquipHeadBase.setRotationPoint(0.0F, -11.8F, -7.6F);
        this.EquipHeadBase.addBox(-8.0F, 0.0F, 7.0F, 16, 2, 8, 0.0F);
        this.ClothA02a = new ModelRenderer(this, 128, 49);
        this.ClothA02a.setRotationPoint(2.5F, -0.1F, -2.5F);
        this.ClothA02a.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
        this.setRotateAngle(ClothA02a, 0.0F, 0.012808717561550659F, 0.0F);
        this.EquipD03c2a = new ModelRenderer(this, 0, 0);
        this.EquipD03c2a.setRotationPoint(1.5F, 0.1F, 0.0F);
        this.EquipD03c2a.addBox(0.0F, -1.5F, -4.5F, 8, 3, 9, 0.0F);
        this.EquipD03c2a_1 = new ModelRenderer(this, 0, 0);
        this.EquipD03c2a_1.setRotationPoint(1.5F, 0.1F, 0.0F);
        this.EquipD03c2a_1.addBox(0.0F, -1.5F, -4.5F, 8, 3, 9, 0.0F);
        this.BoobR = new ModelRenderer(this, 0, 39);
        this.BoobR.setRotationPoint(-3.5F, -8.2F, -3.8F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 4, 0.0F);
        this.setRotateAngle(BoobR, -0.8726646259971648F, -0.08726646259971647F, -0.06981317007977318F);
        this.ClothA02 = new ModelRenderer(this, 128, 49);
        this.ClothA02.setRotationPoint(-2.5F, -0.1F, -2.5F);
        this.ClothA02.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
        this.EquipCL1a1_2 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_2.setRotationPoint(2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_2.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_2, -0.18203784098300857F, 0.0F, 0.0F);
        this.EquipB05_2 = new ModelRenderer(this, 100, 30);
        this.EquipB05_2.setRotationPoint(6.3F, -2.0F, 0.0F);
        this.EquipB05_2.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.EquipCL1a2_4 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_4.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_4.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_4, -1.5707963267948966F, 0.0F, 0.0F);
        this.Cloth02c2 = new ModelRenderer(this, 58, 7);
        this.Cloth02c2.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.Cloth02c2.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 0, 0.0F);
        this.setRotateAngle(Cloth02c2, -0.7853981633974483F, 0.0F, 0.0F);
        this.EquipD03a2_1 = new ModelRenderer(this, 107, 13);
        this.EquipD03a2_1.setRotationPoint(6.4F, -1.0F, -2.5F);
        this.EquipD03a2_1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 9, 0.0F);
        this.EquipD03a1 = new ModelRenderer(this, 107, 13);
        this.EquipD03a1.setRotationPoint(5.0F, 5.8F, 3.5F);
        this.EquipD03a1.addBox(0.5F, -1.0F, -2.5F, 6, 2, 9, 0.0F);
        this.setRotateAngle(EquipD03a1, 0.0F, 0.0F, 0.5235987755982988F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 54);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipD01ba = new ModelRenderer(this, 22, 22);
        this.EquipD01ba.setRotationPoint(0.0F, 1.0F, 6.0F);
        this.EquipD01ba.addBox(-1.5F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
        this.EquipCL1a2_3 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_3.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_3.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_3, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipCL1a1_6 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_6.setRotationPoint(2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_6.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_6, -0.18203784098300857F, 0.0F, 0.0F);
        this.Cloth02c2_1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c2_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.Cloth02c2_1.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 0, 0.0F);
        this.setRotateAngle(Cloth02c2_1, -0.7853981633974483F, 0.0F, 0.0F);
        this.EquipCL1Base01a = new ModelRenderer(this, 0, 9);
        this.EquipCL1Base01a.setRotationPoint(0.0F, -5.4F, -1.0F);
        this.EquipCL1Base01a.addBox(-2.5F, 0.0F, 0.0F, 5, 2, 1, 0.0F);
        this.setRotateAngle(EquipCL1Base01a, -0.08726646259971647F, 0.0F, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 128, 17);
        this.Skirt02.setRotationPoint(0.0F, 3.5F, -2.7F);
        this.Skirt02.addBox(-9.5F, 0.0F, -6.5F, 19, 5, 13, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipD03a4 = new ModelRenderer(this, 107, 13);
        this.EquipD03a4.setRotationPoint(5.9F, 0.0F, 0.0F);
        this.EquipD03a4.addBox(0.0F, 0.0F, 0.0F, 6, 2, 9, 0.0F);
        this.EquipHead03_1 = new ModelRenderer(this, 33, 105);
        this.EquipHead03_1.setRotationPoint(-0.2F, -1.5F, 0.0F);
        this.EquipHead03_1.addBox(-5.0F, 0.0F, 0.0F, 5, 3, 0, 0.0F);
        this.Cloth02c3 = new ModelRenderer(this, 58, 7);
        this.Cloth02c3.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.Cloth02c3.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 0, 0.0F);
        this.setRotateAngle(Cloth02c3, -0.13962634015954636F, 0.0F, 0.03490658503988659F);
        this.EquipB05_3 = new ModelRenderer(this, 100, 30);
        this.EquipB05_3.setRotationPoint(6.3F, -2.0F, 0.0F);
        this.EquipB05_3.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.setRotateAngle(EquipB05_3, 0.0F, 3.141592653589793F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 71);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.27314402793711257F, 0.0F, -0.3141592653589793F);
        this.Cloth03a1 = new ModelRenderer(this, 159, 55);
        this.Cloth03a1.setRotationPoint(4.1F, -11.1F, -4.1F);
        this.Cloth03a1.addBox(-1.0F, 0.0F, 0.0F, 2, 18, 7, 0.0F);
        this.EquipD03c3a_1 = new ModelRenderer(this, 0, 0);
        this.EquipD03c3a_1.setRotationPoint(2.3F, -1.4F, -4.5F);
        this.EquipD03c3a_1.addBox(0.0F, 0.0F, 0.0F, 8, 3, 9, 0.0F);
        this.EquipD03aa = new ModelRenderer(this, 100, 30);
        this.EquipD03aa.setRotationPoint(-0.5F, -1.5F, -3.4F);
        this.EquipD03aa.addBox(0.0F, 0.0F, 0.0F, 9, 3, 1, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.EquipCL1a1_1 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_1.setRotationPoint(-2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_1.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_1, -0.16982053621904827F, 0.0F, 0.0F);
        this.Cloth02c1_1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c1_1.setRotationPoint(-2.6F, 1.9F, 4.4F);
        this.Cloth02c1_1.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 0, 0.0F);
        this.setRotateAngle(Cloth02c1_1, 0.6283185307179586F, 0.0F, 0.08726646259971647F);
        this.Cloth02b2 = new ModelRenderer(this, 59, 0);
        this.Cloth02b2.setRotationPoint(0.0F, 4.9F, 0.0F);
        this.Cloth02b2.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth02b2, 0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.ClothA05 = new ModelRenderer(this, 128, 96);
        this.ClothA05.setRotationPoint(0.0F, 1.9F, 0.8F);
        this.ClothA05.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 6, 0.0F);
        this.EquipD03c2 = new ModelRenderer(this, 100, 30);
        this.EquipD03c2.setRotationPoint(8.0F, -0.2F, 1.5F);
        this.EquipD03c2.addBox(0.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
        this.setRotateAngle(EquipD03c2, 0.0F, 0.0F, -0.2617993877991494F);
        this.ArmRight01 = new ModelRenderer(this, 24, 71);
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, -0.08726646259971647F, 0.0F, 0.3141592653589793F);
        this.Skirt01 = new ModelRenderer(this, 128, 0);
        this.Skirt01.setRotationPoint(0.0F, 3.5F, 1.5F);
        this.Skirt01.addBox(-8.5F, 0.0F, -8.5F, 17, 5, 11, 0.0F);
        this.setRotateAngle(Skirt01, -0.08726646259971647F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.4F, 14, 14, 13, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipD01a = new ModelRenderer(this, 0, 0);
        this.EquipD01a.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.EquipD01a.addBox(-4.0F, 0.0F, 0.0F, 8, 10, 6, 0.0F);
        this.setRotateAngle(EquipD01a, 0.06981317007977318F, 0.0F, 0.0F);
        this.Hair01 = new ModelRenderer(this, 84, 0);
        this.Hair01.setRotationPoint(0.0F, 11.2F, 3.8F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 6, 7, 0.0F);
        this.setRotateAngle(Hair01, 0.5061454830783556F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 106, 31);
        this.Ahoke.setRotationPoint(-1.0F, -8.0F, -4.5F);
        this.Ahoke.addBox(0.0F, -5.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.7853981633974483F, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.EquipHead01_1 = new ModelRenderer(this, 43, 105);
        this.EquipHead01_1.setRotationPoint(-6.7F, 0.2F, 5.7F);
        this.EquipHead01_1.addBox(-2.0F, -0.7F, -0.3F, 2, 3, 3, 0.0F);
        this.Cloth02a3 = new ModelRenderer(this, 59, 0);
        this.Cloth02a3.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Cloth02a3.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth02a3, 0.0F, 0.0F, 0.05235987755982988F);
        this.EquipCL1a1_5 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_5.setRotationPoint(-2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_5.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_5, -0.16982053621904827F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 88);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipCL1a1_7 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_7.setRotationPoint(-2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_7.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_7, -0.16982053621904827F, 0.0F, 0.0F);
        this.Cloth02c4 = new ModelRenderer(this, 58, 7);
        this.Cloth02c4.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Cloth02c4.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 0, 0.0F);
        this.Cloth01b = new ModelRenderer(this, 65, 0);
        this.Cloth01b.setRotationPoint(0.0F, 0.5F, 0.3F);
        this.Cloth01b.addBox(-6.0F, -3.0F, -1.0F, 6, 3, 2, 0.0F);
        this.setRotateAngle(Cloth01b, 0.08726646259971647F, -0.17453292519943295F, -0.3490658503988659F);
        this.SkirtB01 = new ModelRenderer(this, 128, 36);
        this.SkirtB01.setRotationPoint(0.0F, 0.5F, -1.9F);
        this.SkirtB01.addBox(-8.0F, 0.0F, -4.5F, 16, 2, 9, 0.0F);
        this.setRotateAngle(SkirtB01, 0.08726646259971647F, 0.0F, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 68);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.296705972839036F, 0.0F, 0.08726646259971647F);
        this.EquipCL1a2_7 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_7.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_7.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_7, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipCL1Base01R1 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base01R1.setRotationPoint(0.2F, 0.1F, 0.0F);
        this.EquipCL1Base01R1.addBox(-4.5F, -4.0F, -1.5F, 9, 4, 8, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, 7.5F, 5.5F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.EquipD03c2_1 = new ModelRenderer(this, 100, 30);
        this.EquipD03c2_1.setRotationPoint(8.0F, -0.2F, 1.5F);
        this.EquipD03c2_1.addBox(0.0F, -1.5F, -1.5F, 3, 3, 3, 0.0F);
        this.setRotateAngle(EquipD03c2_1, 0.0F, 0.0F, -0.2617993877991494F);
        this.EquipD02b = new ModelRenderer(this, 0, 0);
        this.EquipD02b.setRotationPoint(-3.4F, 5.0F, 1.7F);
        this.EquipD02b.addBox(-3.5F, 0.0F, -0.6F, 7, 14, 5, 0.0F);
        this.Cloth02b3 = new ModelRenderer(this, 59, 0);
        this.Cloth02b3.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Cloth02b3.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth02b3, 0.0F, 0.0F, -0.05235987755982988F);
        this.EquipD03a3_1 = new ModelRenderer(this, 107, 13);
        this.EquipD03a3_1.setRotationPoint(5.9F, 0.0F, 0.0F);
        this.EquipD03a3_1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 9, 0.0F);
        this.ClothB01 = new ModelRenderer(this, 25, 37);
        this.ClothB01.setRotationPoint(2.9F, 4.6F, 1.6F);
        this.ClothB01.addBox(-4.5F, 0.0F, 0.0F, 9, 7, 0, 0.0F);
        this.setRotateAngle(ClothB01, 0.9599310885968813F, -0.006806784082777885F, 0.09477137838329208F);
        this.EquipD03c1b_1 = new ModelRenderer(this, 100, 30);
        this.EquipD03c1b_1.setRotationPoint(7.2F, -1.0F, 1.5F);
        this.EquipD03c1b_1.addBox(-0.5F, 0.0F, -0.5F, 2, 2, 1, 0.0F);
        this.EquipCL1Base01L2 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base01L2.setRotationPoint(0.2F, 0.1F, 0.0F);
        this.EquipCL1Base01L2.addBox(-4.5F, -4.0F, -1.5F, 9, 4, 8, 0.0F);
        this.EquipCL1Base01L1 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base01L1.setRotationPoint(0.2F, 0.1F, 0.0F);
        this.EquipCL1Base01L1.addBox(-4.5F, -4.0F, -1.5F, 9, 4, 8, 0.0F);
        this.Cloth03b = new ModelRenderer(this, 161, 80);
        this.Cloth03b.mirror = true;
        this.Cloth03b.setRotationPoint(-0.6F, -0.8F, -0.1F);
        this.Cloth03b.addBox(-1.0F, 0.0F, 0.0F, 2, 5, 5, 0.0F);
        this.setRotateAngle(Cloth03b, 0.0F, 0.0F, 0.08726646259971647F);
        this.ClothA04 = new ModelRenderer(this, 128, 81);
        this.ClothA04.setRotationPoint(0.0F, 0.9F, 0.8F);
        this.ClothA04.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.EquipCL1Base01a_3 = new ModelRenderer(this, 0, 9);
        this.EquipCL1Base01a_3.setRotationPoint(0.0F, -5.4F, -1.0F);
        this.EquipCL1Base01a_3.addBox(-2.5F, 0.0F, 0.0F, 5, 2, 1, 0.0F);
        this.setRotateAngle(EquipCL1Base01a_3, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipCL1Base01b = new ModelRenderer(this, 109, 24);
        this.EquipCL1Base01b.setRotationPoint(0.0F, -5.6F, 2.0F);
        this.EquipCL1Base01b.addBox(-5.5F, 0.0F, 0.0F, 11, 2, 2, 0.0F);
        this.EquipCL1a1_3 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_3.setRotationPoint(-2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_3.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_3, -0.16982053621904827F, 0.0F, 0.0F);
        this.EquipD03c3a = new ModelRenderer(this, 0, 0);
        this.EquipD03c3a.setRotationPoint(2.3F, -1.4F, -4.5F);
        this.EquipD03c3a.addBox(0.0F, 0.0F, 0.0F, 8, 3, 9, 0.0F);
        this.EquipD03c1a_1 = new ModelRenderer(this, 0, 0);
        this.EquipD03c1a_1.setRotationPoint(0.0F, 0.2F, 0.0F);
        this.EquipD03c1a_1.addBox(0.0F, 0.0F, 0.0F, 8, 1, 3, 0.0F);
        this.Cloth02c3_1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c3_1.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.Cloth02c3_1.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 0, 0.0F);
        this.setRotateAngle(Cloth02c3_1, -0.13962634015954636F, 0.0F, -0.03490658503988659F);
        this.ClothA01_1 = new ModelRenderer(this, 128, 109);
        this.ClothA01_1.setRotationPoint(-0.5F, 5.1F, 0.0F);
        this.ClothA01_1.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.EquipD03c1b = new ModelRenderer(this, 100, 30);
        this.EquipD03c1b.setRotationPoint(7.2F, -1.0F, 1.5F);
        this.EquipD03c1b.addBox(-0.5F, 0.0F, -0.5F, 2, 2, 1, 0.0F);
        this.Cloth01c2 = new ModelRenderer(this, 73, 5);
        this.Cloth01c2.mirror = true;
        this.Cloth01c2.setRotationPoint(2.0F, -0.4F, -0.7F);
        this.Cloth01c2.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth01c2, -0.2617993877991494F, -0.13962634015954636F, -0.17453292519943295F);
        this.EquipD03c3_1 = new ModelRenderer(this, 100, 30);
        this.EquipD03c3_1.setRotationPoint(7.9F, 0.0F, 0.0F);
        this.EquipD03c3_1.addBox(0.0F, -1.5F, -1.5F, 4, 3, 3, 0.0F);
        this.setRotateAngle(EquipD03c3_1, 0.0F, 0.0F, 0.6108652381980153F);
        this.Cloth01c = new ModelRenderer(this, 73, 5);
        this.Cloth01c.setRotationPoint(-2.0F, -0.4F, -0.7F);
        this.Cloth01c.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth01c, -0.2617993877991494F, 0.13962634015954636F, 0.17453292519943295F);
        this.Cloth01a = new ModelRenderer(this, 81, 0);
        this.Cloth01a.setRotationPoint(0.0F, 2.3F, -5.0F);
        this.Cloth01a.addBox(-1.0F, -2.5F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Cloth01a, -0.2617993877991494F, 0.0F, 0.0F);
        this.EquipCL1Base02 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base02.setRotationPoint(0.0F, 0.3F, -2.8F);
        this.EquipCL1Base02.addBox(-4.5F, -4.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(EquipCL1Base02, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipD01b = new ModelRenderer(this, 0, 0);
        this.EquipD01b.setRotationPoint(0.0F, 0.4F, 5.9F);
        this.EquipD01b.addBox(-4.0F, 0.0F, 0.0F, 8, 9, 6, 0.0F);
        this.EquipD03a3 = new ModelRenderer(this, 107, 13);
        this.EquipD03a3.setRotationPoint(5.9F, 0.0F, 0.0F);
        this.EquipD03a3.addBox(0.0F, 0.0F, 0.0F, 6, 2, 9, 0.0F);
        this.Cloth03b_1 = new ModelRenderer(this, 161, 80);
        this.Cloth03b_1.setRotationPoint(0.6F, -0.8F, -0.1F);
        this.Cloth03b_1.addBox(-1.0F, 0.0F, 0.0F, 2, 5, 5, 0.0F);
        this.setRotateAngle(Cloth03b_1, 0.0F, 0.0F, -0.08726646259971647F);
        this.BoobL = new ModelRenderer(this, 25, 44);
        this.BoobL.setRotationPoint(3.5F, -8.2F, -3.7F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 4, 0.0F);
        this.setRotateAngle(BoobL, -0.8726646259971648F, 0.08726646259971647F, 0.06981317007977318F);
        this.EquipCL1a2_2 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_2.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_2.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_2, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipD02c = new ModelRenderer(this, 0, 0);
        this.EquipD02c.mirror = true;
        this.EquipD02c.setRotationPoint(3.3F, 5.0F, 5.9F);
        this.EquipD02c.addBox(-3.5F, 0.0F, 0.0F, 6, 12, 4, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.Cloth02c4_1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c4_1.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Cloth02c4_1.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 0, 0.0F);
        this.Cloth01b2 = new ModelRenderer(this, 65, 0);
        this.Cloth01b2.setRotationPoint(0.0F, 0.5F, 0.3F);
        this.Cloth01b2.addBox(0.0F, -3.0F, -1.0F, 6, 3, 2, 0.0F);
        this.setRotateAngle(Cloth01b2, 0.08726646259971647F, 0.17453292519943295F, 0.3490658503988659F);
        this.EquipD03c3 = new ModelRenderer(this, 100, 30);
        this.EquipD03c3.setRotationPoint(7.9F, 0.0F, 0.0F);
        this.EquipD03c3.addBox(0.0F, -1.5F, -1.5F, 4, 3, 3, 0.0F);
        this.setRotateAngle(EquipD03c3, 0.0F, 0.0F, 0.6108652381980153F);
        this.EquipCL1Base02_2 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base02_2.setRotationPoint(0.0F, 0.3F, -2.8F);
        this.EquipCL1Base02_2.addBox(-4.5F, -4.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(EquipCL1Base02_2, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipD03ab = new ModelRenderer(this, 100, 30);
        this.EquipD03ab.setRotationPoint(-0.5F, -1.5F, 6.4F);
        this.EquipD03ab.addBox(0.0F, 0.0F, 0.0F, 9, 3, 1, 0.0F);
        this.EquipD03aa_1 = new ModelRenderer(this, 100, 30);
        this.EquipD03aa_1.setRotationPoint(-0.5F, -1.5F, -3.4F);
        this.EquipD03aa_1.addBox(0.0F, 0.0F, 0.0F, 9, 3, 1, 0.0F);
        this.EquipCL1a1_4 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_4.setRotationPoint(2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_4.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_4, -0.18203784098300857F, 0.0F, 0.0F);
        this.EquipCL1Base01a_2 = new ModelRenderer(this, 0, 9);
        this.EquipCL1Base01a_2.setRotationPoint(0.0F, -5.4F, -1.0F);
        this.EquipCL1Base01a_2.addBox(-2.5F, 0.0F, 0.0F, 5, 2, 1, 0.0F);
        this.setRotateAngle(EquipCL1Base01a_2, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipHead03 = new ModelRenderer(this, 33, 105);
        this.EquipHead03.setRotationPoint(0.2F, -1.5F, 0.0F);
        this.EquipHead03.addBox(0.0F, 0.0F, 0.0F, 5, 3, 0, 0.0F);
        this.EquipD03a2 = new ModelRenderer(this, 107, 13);
        this.EquipD03a2.setRotationPoint(6.4F, -1.0F, -2.5F);
        this.EquipD03a2.addBox(0.0F, 0.0F, 0.0F, 6, 2, 9, 0.0F);
        this.Cloth02b1 = new ModelRenderer(this, 59, 0);
        this.Cloth02b1.setRotationPoint(-4.0F, 2.3F, -6.8F);
        this.Cloth02b1.addBox(-1.5F, 0.0F, 0.0F, 3, 5, 0, 0.0F);
        this.setRotateAngle(Cloth02b1, -0.4363323129985824F, 0.0F, 0.06981317007977318F);
        this.EquipD03a4_1 = new ModelRenderer(this, 107, 13);
        this.EquipD03a4_1.setRotationPoint(5.9F, 0.0F, 0.0F);
        this.EquipD03a4_1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 9, 0.0F);
        this.EquipD03c1 = new ModelRenderer(this, 0, 0);
        this.EquipD03c1.setRotationPoint(5.0F, 5.5F, 4.0F);
        this.EquipD03c1.addBox(0.0F, -1.5F, 0.0F, 8, 1, 3, 0.0F);
        this.setRotateAngle(EquipD03c1, 0.0F, 0.0F, -0.3490658503988659F);
        this.EquipD01aa = new ModelRenderer(this, 0, 0);
        this.EquipD01aa.setRotationPoint(0.0F, 9.5F, -4.0F);
        this.EquipD01aa.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 6, 0.0F);
        this.setRotateAngle(EquipD01aa, 0.22689280275926282F, 0.0F, 0.0F);
        this.EquipD03ab_1 = new ModelRenderer(this, 100, 30);
        this.EquipD03ab_1.setRotationPoint(-0.5F, -1.5F, 6.4F);
        this.EquipD03ab_1.addBox(0.0F, 0.0F, 0.0F, 9, 3, 1, 0.0F);
        this.EquipD03c1a = new ModelRenderer(this, 0, 0);
        this.EquipD03c1a.setRotationPoint(0.0F, 0.2F, 0.0F);
        this.EquipD03c1a.addBox(0.0F, 0.0F, 0.0F, 8, 1, 3, 0.0F);
        this.EquipCL1a2_6 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_6.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_6.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_6, -1.5707963267948966F, 0.0F, 0.0F);
        this.ClothA03 = new ModelRenderer(this, 128, 65);
        this.ClothA03.setRotationPoint(0.1F, 1.9F, -2.2F);
        this.ClothA03.addBox(-2.5F, 0.0F, 0.0F, 5, 9, 6, 0.0F);
        this.EquipCL1a2 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipCL1a1 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1.setRotationPoint(2.0F, -2.3F, -2.5F);
        this.EquipCL1a1.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1, -0.18203784098300857F, 0.0F, 0.0F);
        this.EquipCL1a2_5 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_5.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_5.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_5, -1.5707963267948966F, 0.0F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 47);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.HairMain.addChild(this.Hair02a);
        this.EquipCL1Base01R1.addChild(this.EquipCL1Base01b_1);
        this.Butt.addChild(this.Cloth02a1);
        this.EquipB05_1.addChild(this.EquipCL1Base01R2);
        this.ArmLeft01.addChild(this.ClothA01);
        this.EquipCL1Base01R2.addChild(this.EquipCL1Base02_1);
        this.HairMain.addChild(this.Hair01a);
        this.Cloth02a1.addChild(this.Cloth02a2);
        this.EquipD01a.addChild(this.EquipD02d);
        this.EquipCL1a1_1.addChild(this.EquipCL1a2_1);
        this.EquipCL1Base01R1.addChild(this.EquipCL1Base02_3);
        this.EquipHead01.addChild(this.EquipHead02);
        this.EquipHead01_1.addChild(this.EquipHead02_1);
        this.EquipD01a.addChild(this.EquipD02a);
        this.EquipD01a.addChild(this.EquipD03d1);
        this.BodyMain.addChild(this.Neck);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipHeadBase.addChild(this.EquipHead00);
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipD01b.addChild(this.EquipD01bb);
        this.SkirtB01.addChild(this.Cloth02c1);
        this.ClothA04a.addChild(this.ClothA05a);
        this.EquipD01a.addChild(this.EquipD03b1);
        this.EquipHeadBase.addChild(this.EquipHead01);
        this.ClothA02a.addChild(this.ClothA03a);
        this.BodyMain.addChild(this.Cloth03a2);
        this.EquipD03a4.addChild(this.EquipB05);
        this.Butt.addChild(this.LegRight01);
        this.ClothA03a.addChild(this.ClothA04a);
        this.EquipCL1Base01R2.addChild(this.EquipCL1Base01a_1);
        this.EquipD03a4_1.addChild(this.EquipB05_1);
        this.Head.addChild(this.EquipHeadBase);
        this.ArmRight02.addChild(this.ClothA02a);
        this.EquipD03c2.addChild(this.EquipD03c2a);
        this.EquipD03c2_1.addChild(this.EquipD03c2a_1);
        this.BodyMain.addChild(this.BoobR);
        this.ArmLeft02.addChild(this.ClothA02);
        this.EquipCL1Base01R2.addChild(this.EquipCL1a1_2);
        this.EquipD03c3.addChild(this.EquipB05_2);
        this.EquipCL1a1_4.addChild(this.EquipCL1a2_4);
        this.Cloth02c1.addChild(this.Cloth02c2);
        this.EquipD03b1.addChild(this.EquipD03a2_1);
        this.EquipD01a.addChild(this.EquipD03a1);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipD01b.addChild(this.EquipD01ba);
        this.EquipCL1a1_3.addChild(this.EquipCL1a2_3);
        this.EquipCL1Base01R1.addChild(this.EquipCL1a1_6);
        this.Cloth02c1_1.addChild(this.Cloth02c2_1);
        this.EquipCL1Base01L2.addChild(this.EquipCL1Base01a);
        this.Skirt01.addChild(this.Skirt02);
        this.EquipD03a3.addChild(this.EquipD03a4);
        this.EquipHead02_1.addChild(this.EquipHead03_1);
        this.Cloth02c2.addChild(this.Cloth02c3);
        this.EquipD03c3_1.addChild(this.EquipB05_3);
        this.BodyMain.addChild(this.ArmLeft01);
        this.BodyMain.addChild(this.Cloth03a1);
        this.EquipD03c3_1.addChild(this.EquipD03c3a_1);
        this.EquipD03a1.addChild(this.EquipD03aa);
        this.Head.addChild(this.Hair);
        this.EquipCL1Base01L2.addChild(this.EquipCL1a1_1);
        this.SkirtB01.addChild(this.Cloth02c1_1);
        this.Cloth02b1.addChild(this.Cloth02b2);
        this.ClothA04.addChild(this.ClothA05);
        this.EquipD03c1.addChild(this.EquipD03c2);
        this.BodyMain.addChild(this.ArmRight01);
        this.Butt.addChild(this.Skirt01);
        this.Neck.addChild(this.Head);
        this.EquipBase.addChild(this.EquipD01a);
        this.HairMain.addChild(this.Hair01);
        this.Hair.addChild(this.Ahoke);
        this.Head.addChild(this.HairMain);
        this.EquipHeadBase.addChild(this.EquipHead01_1);
        this.Cloth02a2.addChild(this.Cloth02a3);
        this.EquipCL1Base01L1.addChild(this.EquipCL1a1_5);
        this.BodyMain.addChild(this.Butt);
        this.EquipCL1Base01R1.addChild(this.EquipCL1a1_7);
        this.Cloth02c3.addChild(this.Cloth02c4);
        this.Cloth01a.addChild(this.Cloth01b);
        this.Butt.addChild(this.SkirtB01);
        this.Butt.addChild(this.LegLeft01);
        this.EquipCL1a1_7.addChild(this.EquipCL1a2_7);
        this.EquipB05_3.addChild(this.EquipCL1Base01R1);
        this.BodyMain.addChild(this.EquipBase);
        this.EquipD03d1.addChild(this.EquipD03c2_1);
        this.EquipD01a.addChild(this.EquipD02b);
        this.Cloth02b2.addChild(this.Cloth02b3);
        this.EquipD03a2_1.addChild(this.EquipD03a3_1);
        this.BoobR.addChild(this.ClothB01);
        this.EquipD03d1.addChild(this.EquipD03c1b_1);
        this.EquipB05.addChild(this.EquipCL1Base01L2);
        this.EquipB05_2.addChild(this.EquipCL1Base01L1);
        this.BoobR.addChild(this.Cloth03b);
        this.ClothA03.addChild(this.ClothA04);
        this.EquipCL1Base01R1.addChild(this.EquipCL1Base01a_3);
        this.EquipCL1Base01L1.addChild(this.EquipCL1Base01b);
        this.EquipCL1Base01R2.addChild(this.EquipCL1a1_3);
        this.EquipD03c3.addChild(this.EquipD03c3a);
        this.EquipD03d1.addChild(this.EquipD03c1a_1);
        this.Cloth02c2_1.addChild(this.Cloth02c3_1);
        this.ArmRight01.addChild(this.ClothA01_1);
        this.EquipD03c1.addChild(this.EquipD03c1b);
        this.Cloth01a.addChild(this.Cloth01c2);
        this.EquipD03c2_1.addChild(this.EquipD03c3_1);
        this.Cloth01a.addChild(this.Cloth01c);
        this.SkirtB01.addChild(this.Cloth01a);
        this.EquipCL1Base01L2.addChild(this.EquipCL1Base02);
        this.EquipD01a.addChild(this.EquipD01b);
        this.EquipD03a2.addChild(this.EquipD03a3);
        this.BoobL.addChild(this.Cloth03b_1);
        this.BodyMain.addChild(this.BoobL);
        this.EquipCL1a1_2.addChild(this.EquipCL1a2_2);
        this.EquipD01a.addChild(this.EquipD02c);
        this.Hair.addChild(this.HairU01);
        this.Cloth02c3_1.addChild(this.Cloth02c4_1);
        this.Cloth01a.addChild(this.Cloth01b2);
        this.EquipD03c2.addChild(this.EquipD03c3);
        this.EquipCL1Base01L1.addChild(this.EquipCL1Base02_2);
        this.EquipD03a1.addChild(this.EquipD03ab);
        this.EquipD03b1.addChild(this.EquipD03aa_1);
        this.EquipCL1Base01L1.addChild(this.EquipCL1a1_4);
        this.EquipCL1Base01L1.addChild(this.EquipCL1Base01a_2);
        this.EquipHead02.addChild(this.EquipHead03);
        this.EquipD03a1.addChild(this.EquipD03a2);
        this.Butt.addChild(this.Cloth02b1);
        this.EquipD03a3_1.addChild(this.EquipD03a4_1);
        this.EquipD01a.addChild(this.EquipD03c1);
        this.EquipD01a.addChild(this.EquipD01aa);
        this.EquipD03b1.addChild(this.EquipD03ab_1);
        this.EquipD03c1.addChild(this.EquipD03c1a);
        this.EquipCL1a1_6.addChild(this.EquipCL1a2_6);
        this.ClothA02.addChild(this.ClothA03);
        this.EquipCL1a1.addChild(this.EquipCL1a2);
        this.EquipCL1Base01L2.addChild(this.EquipCL1a1);
        this.EquipCL1a1_5.addChild(this.EquipCL1a2_5);
        this.LegRight01.addChild(this.LegRight02);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 104);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0F);
        this.GlowNeck = new ModelRenderer(this, 24, 63);
        this.GlowNeck.setRotationPoint(0.0F, -9.6F, 0.5F);
        this.setRotateAngle(GlowNeck, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 44, 101);
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
    		scale = 1.8F;
        	offsetY = -0.69F;
		break;
    	case 2:
    		scale = 1.35F;
        	offsetY = -0.41F;
		break;
    	case 1:
    		scale = 0.9F;
        	offsetY = 0.14F;
		break;
    	default:
    		scale = 0.45F;
        	offsetY = 1.79F;
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
		int state = ent.getStateEmotion(ID.S.State);
		
		boolean flag = !EmotionHelper.checkModelState(0, state);	//cannon
		this.EquipBase.isHidden = flag;
				
		flag = !EmotionHelper.checkModelState(1, state);			//head equip
		this.EquipHeadBase.isHidden = flag;
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
		switch (ent.getScaleLevel())
    	{
    	case 3:
    		GlStateManager.translate(0F, 1.22F, 0F);
		break;
    	case 2:
    		GlStateManager.translate(0F, 1.1F, 0F);
		break;
    	case 1:
        	GlStateManager.translate(0F, 0.89F, 0F);
		break;
    	default:
    		GlStateManager.translate(0F, 0.6F, 0F);
		break;
    	}

		this.setFaceHungry(ent);
		
		//hair
    	this.Ahoke.rotateAngleX = 0F;
    	this.Ahoke.rotateAngleY = 0.78F;
    	this.Ahoke.rotateAngleZ = 0F;
		//body
		this.Head.rotateAngleX = 0.15F;
		this.Head.rotateAngleY = 0.0F;
		this.Head.rotateAngleZ = 0.0F;
		this.BodyMain.rotateAngleX = 1.7453292519943295F;
		this.BodyMain.rotateAngleY = 0.0F;
		this.BodyMain.rotateAngleZ = -0.5235987755982988F;
		this.Butt.rotateAngleX = -0.7853981633974483F;
		this.Butt.rotateAngleY = 0.0F;
		this.Butt.rotateAngleZ = 0.0F;
		this.Butt.offsetY = 0F;
	  	this.Butt.offsetZ = 0F;
		this.BoobL.rotateAngleX = -0.8F;
  	    this.BoobR.rotateAngleX = -0.8F;
    	this.ClothB01.rotateAngleX = 0.96F;
		//cloth
    	this.Skirt01.rotateAngleX = -0.087F;
    	this.Skirt01.offsetY = 0F;
	  	this.Skirt01.offsetZ = 0F;
	  	this.Skirt02.rotateAngleX = -0.087F;
    	this.SkirtB01.rotateAngleX = 0.087F;
    	this.ClothA03.rotateAngleY = 0F;
	  	this.ClothA03a.rotateAngleY = 0F;
    	this.Cloth02a1.rotateAngleX = -0.5585F;
	  	this.Cloth02b1.rotateAngleX = -0.5585F;
	  	this.Cloth02c1.rotateAngleX = 0.6283F;
		this.Cloth02c1_1.rotateAngleX = 0.6283F;
		this.Cloth02c2.rotateAngleX = -0.7854F;
		this.Cloth02c2_1.rotateAngleX = -0.7854F;
		this.Cloth02c3.rotateAngleX = -0.1396F;
		this.Cloth02c3_1.rotateAngleX = -0.1396F;
		this.Cloth02c4.rotateAngleX = 0F;
		this.Cloth02c4_1.rotateAngleX = 0F;
		this.Cloth02a2.rotateAngleX = 0.1745F;
		this.Cloth02b2.rotateAngleX = 0.1745F;
		this.Cloth02a3.rotateAngleX = 0F;
		this.Cloth02b3.rotateAngleX = 0F;
		this.ClothA03.offsetX = 0F;
		this.ClothA03.offsetY = 0F;
		this.ClothA03.offsetZ = 0F;
	  	this.ClothA04.offsetY = 0F;
	  	this.ClothA04.offsetZ = 0F;
	  	this.ClothA05.offsetY = 0F;
	  	this.ClothA05.offsetZ = 0F;
	  	this.ClothA03a.offsetX = 0F;
	  	this.ClothA03a.offsetY = 0F;
	  	this.ClothA03a.offsetZ = 0F;
	  	this.ClothA04a.offsetY = 0F;
	  	this.ClothA04a.offsetZ = 0F;
	  	this.ClothA05a.offsetY = 0F;
	  	this.ClothA05a.offsetZ = 0F;
		//arm
		this.ArmLeft01.rotateAngleX = -1.3962634015954636F;
		this.ArmLeft01.rotateAngleY = -0.3490658503988659F;
		this.ArmLeft01.rotateAngleZ = -0.17453292519943295F;
		this.ArmLeft02.rotateAngleX = -1.48352986419518F;
		this.ArmLeft02.rotateAngleY = 0.0F;
		this.ArmLeft02.rotateAngleZ = 0.0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = -0.2F;
		this.ArmRight01.rotateAngleX = -1.3089969389957472F;
		this.ArmRight01.rotateAngleY = -0.8726646259971648F;
		this.ArmRight01.rotateAngleZ = 0.0F;
		this.ArmRight02.rotateAngleX = 0.0F;
		this.ArmRight02.rotateAngleY = 0.0F;
		this.ArmRight02.rotateAngleZ = -0.17453292519943295F;
		this.ArmRight02.offsetX = 0F;
		this.ArmRight02.offsetZ = 0F;
		//leg
		this.LegLeft01.rotateAngleX = -0.6981317007977318F;
		this.LegLeft01.rotateAngleY = -0.6981317007977318F;
		this.LegLeft01.rotateAngleZ = -0.2617993877991494F;
		this.LegLeft01.offsetX = 0F;
		this.LegLeft01.offsetY = 0F;
		this.LegLeft01.offsetZ = 0F;
		this.LegLeft02.rotateAngleX = 1.5707963267948966F;
		this.LegLeft02.rotateAngleY = 0.0F;
		this.LegLeft02.rotateAngleZ = 0.0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleX = 0.0F;
		this.LegRight01.rotateAngleY = -0.7853981633974483F;
		this.LegRight01.rotateAngleZ = -0.5759586531581287F;
		this.LegRight01.offsetY = 0F;
		this.LegRight01.offsetZ = 0F;
		this.LegRight02.rotateAngleX = 1.3089969389957472F;
		this.LegRight02.rotateAngleY = 0.0F;
		this.LegRight02.rotateAngleZ = 0.0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
		this.EquipBase.isHidden = true;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2 * 0.08F + f * 0.25F);
  		float angleX1 = MathHelper.cos(f2 * 0.1F + 0.35F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2 * 0.1F + 0.70F + f * 0.5F);
  		float angleX3 = MathHelper.cos(f2 * 0.1F + 1.05F + f * 0.5F);
  		float angleX4 = MathHelper.cos(f2 * 0.1F + 1.40F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		float addCA031 = 0;
  		float addCA032 = 0;
  		float t2 = ent.getTickExisted() & 511;
  		boolean spcStand = ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED;
  		
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
  	    this.ClothB01.rotateAngleX = 0.96F - angleX * 0.08F;
	  	//body
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.35F;
	  	this.Butt.offsetY = 0F;
	  	this.Butt.offsetZ = 0F;
	  	this.Skirt01.rotateAngleX = -0.087F;
	  	this.Skirt02.rotateAngleX = -0.087F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt01.offsetZ = 0F;
	  	//cloth
	  	this.ClothA03.rotateAngleY = 0F;
	  	this.ClothA03a.rotateAngleY = 0F;
	  	this.SkirtB01.rotateAngleX = 0.087F;
	  	this.Cloth02a1.rotateAngleX = -0.5585F;
	  	this.Cloth02b1.rotateAngleX = -0.5585F;
	  	this.Cloth02c1.rotateAngleX = 0.6283F;
		this.Cloth02c1_1.rotateAngleX = 0.6283F;
		this.Cloth02c2.rotateAngleX = -0.7854F;
		this.Cloth02c2_1.rotateAngleX = -0.7854F;
		this.Cloth02c3.rotateAngleX = -0.1396F + angleX1 * 0.06F;
		this.Cloth02c3_1.rotateAngleX = -0.1396F + angleX1 * 0.06F;
		this.Cloth02c4.rotateAngleX = -angleX2 * 0.06F;
		this.Cloth02c4_1.rotateAngleX = -angleX2 * 0.06F;
		this.Cloth02a2.rotateAngleX = 0.12F + angleX1 * 0.06F;
		this.Cloth02b2.rotateAngleX = 0.12F + angleX1 * 0.06F;
		this.Cloth02a3.rotateAngleX = -angleX2 * 0.06F;
		this.Cloth02b3.rotateAngleX = -angleX2 * 0.06F;
		this.ClothA03.offsetX = 0F;
		this.ClothA03.offsetY = 0F;
		this.ClothA03.offsetZ = 0F;
	  	this.ClothA04.offsetY = 0F;
	  	this.ClothA04.offsetZ = 0F;
	  	this.ClothA05.offsetY = 0F;
	  	this.ClothA05.offsetZ = 0F;
	  	this.ClothA03a.offsetX = 0F;
	  	this.ClothA03a.offsetY = 0F;
	  	this.ClothA03a.offsetZ = 0F;
	  	this.ClothA04a.offsetY = 0F;
	  	this.ClothA04a.offsetZ = 0F;
	  	this.ClothA05a.offsetY = 0F;
	  	this.ClothA05a.offsetZ = 0F;
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
		this.LegLeft01.offsetY = 0F;
		this.LegLeft01.offsetZ = 0F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.0873F;
		this.LegRight01.offsetY = 0F;
		this.LegRight01.offsetZ = 0F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
		this.EquipCL1a1.rotateAngleX = this.Head.rotateAngleX * 0.8F - 0.21F;
		this.EquipCL1a1_1.rotateAngleX = this.Head.rotateAngleX * 0.7F - 0.23F;
		this.EquipCL1a1_2.rotateAngleX = this.Head.rotateAngleX * 0.85F - 0.20F;
		this.EquipCL1a1_3.rotateAngleX = this.Head.rotateAngleX * 0.75F - 0.25F;
		this.EquipCL1a1_4.rotateAngleX = this.Head.rotateAngleX * 0.8F - 0.20F;
		this.EquipCL1a1_5.rotateAngleX = this.Head.rotateAngleX * 0.85F - 0.19F;
		this.EquipCL1a1_6.rotateAngleX = this.Head.rotateAngleX * 0.75F - 0.21F;
		this.EquipCL1a1_7.rotateAngleX = this.Head.rotateAngleX * 0.88F - 0.19F;
		this.EquipD03c1.rotateAngleZ = -0.35F + this.Head.rotateAngleX * 0.5F;
		this.EquipD03c2.rotateAngleZ = -0.26F + this.Head.rotateAngleX * 0.5F;
		this.EquipD03c3.rotateAngleZ = 0.61F - this.Head.rotateAngleX * 1F;
		this.EquipD03d1.rotateAngleZ = -this.EquipD03c1.rotateAngleZ;
		this.EquipD03c2_1.rotateAngleZ = this.EquipD03c2.rotateAngleZ;
		this.EquipD03c3_1.rotateAngleZ = this.EquipD03c3.rotateAngleZ;
		this.EquipD03a1.rotateAngleZ = 0.52F + this.Head.rotateAngleX * 0.5F;
		this.EquipD03b1.rotateAngleZ = -this.EquipD03a1.rotateAngleZ;
		this.EquipCL1Base01L1.rotateAngleY = this.Head.rotateAngleY * 0.75F;
		this.EquipCL1Base01L2.rotateAngleY = this.Head.rotateAngleY * 0.75F;
		this.EquipCL1Base01R1.rotateAngleY = this.Head.rotateAngleY * 0.75F;
		this.EquipCL1Base01R2.rotateAngleY = this.Head.rotateAngleY * 0.75F;
		
		//run
	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {
	    	spcStand = false;
	    	
	    	if (ent.getTickExisted() % 256 > 128)
	    	{
	    		this.setFace(3);
				this.setMouth(5);
	    	}
	    	//body
	    	this.BodyMain.rotateAngleX = 0.2F;
	    	this.Skirt01.rotateAngleX = -0.4F;
	    	this.Skirt02.rotateAngleX = -0.1F;
	    	this.SkirtB01.rotateAngleX = -0.13F;
	    	this.Cloth02c1.rotateAngleX = 1.17F;
	    	this.Cloth02c1_1.rotateAngleX = 1.17F;
	    	this.Cloth02c2.rotateAngleX = -0.63F;
	    	this.Cloth02c2_1.rotateAngleX = -0.63F;
	    	//arm
	    	this.ArmLeft01.rotateAngleX = angleAdd2 * 1.2F + 0.5F;
	  		this.ArmRight01.rotateAngleX = angleAdd1 * 1.2F + 0.5F;
		  	this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft02.rotateAngleX = -1F;
		    this.ArmLeft02.rotateAngleZ = 0F;
		    this.ArmLeft02.offsetX = 0F;
		    this.ArmLeft02.offsetZ = 0F;
		    this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight02.rotateAngleX = -1F;
			this.ArmRight02.rotateAngleZ = 0F;
			this.ArmRight02.offsetX = 0F;
			this.ArmRight02.offsetZ = 0F;
			//leg
			addk1 = angleAdd1 * 0.7F - 0.48F;
		  	addk2 = angleAdd2 * 0.7F - 0.41F;
			this.LegLeft01.rotateAngleY = 0F;
			this.LegLeft01.rotateAngleZ = 0.0873F;
			this.LegRight01.rotateAngleY = 0F;
			this.LegRight01.rotateAngleZ = -0.0873F;
  		}
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    //sneak
	    if (ent.getIsSneaking())
	    {
	    	spcStand = false;
	    	
	    	switch (ent.getScaleLevel())
	    	{
	    	case 3:
	    		GlStateManager.translate(0F, 0.28F, 0F);
			break;
	    	case 2:
	    		GlStateManager.translate(0F, 0.24F, 0F);
			break;
	    	case 1:
	        	GlStateManager.translate(0F, 0.2F, 0F);
			break;
	    	default:
	    		GlStateManager.translate(0F, 0.14F, 0F);
			break;
	    	}
	    	
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		  	this.Skirt01.rotateAngleX = -0.34F;
		  	this.Skirt01.offsetY = -0.2F;
		  	this.Skirt01.offsetZ = 0.03F;
		  	this.Skirt02.rotateAngleX = -0.27F;
		  	this.Cloth02a1.rotateAngleX = -1.23F;
		  	this.Cloth02b1.rotateAngleX = -1.23F;
		  	this.Cloth02c2.rotateAngleX -= 0.35F;
		  	this.Cloth02c2_1.rotateAngleX -= 0.35F;
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
  		}//end if sneaking
  		
	    //sit
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {
	    	spcStand = false;
	    	
	    	if (ent.getTickExisted() % 512 > 256)
	    	{
		    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
		    		switch (ent.getScaleLevel())
			    	{
			    	case 3:
			    		GlStateManager.translate(0F, 1.39F, 0F);
					break;
			    	case 2:
			    		GlStateManager.translate(0F, 1.22F, 0F);
					break;
			    	case 1:
			        	GlStateManager.translate(0F, 1F, 0F);
					break;
			    	default:
			    		GlStateManager.translate(0F, 0.69F, 0F);
					break;
			    	}
			    	
			  	    //頭部
				  	this.Head.rotateAngleX = -0.35F;
				  	this.Head.rotateAngleY = 0F;
				  	//body
			  	    this.BodyMain.rotateAngleX = -1.6F;
				    //arm 
				  	this.ArmLeft01.rotateAngleX = 3.0F;
				  	this.ArmLeft01.rotateAngleY = 0F;
			    	this.ArmLeft01.rotateAngleZ = 0.7F;
					this.ArmRight01.rotateAngleX = 3.0F;
					this.ArmRight01.rotateAngleY = 0F;
			    	this.ArmRight01.rotateAngleZ = -0.7F;
			    	this.ArmLeft02.rotateAngleX = 0F;
			    	this.ArmRight02.rotateAngleX = 0F;
					//leg
			    	this.LegLeft01.rotateAngleX = -0.2F;
					this.LegLeft01.rotateAngleY = 0F;
					this.LegLeft01.rotateAngleZ = -0.1F;
					this.LegLeft02.rotateAngleX = 0F;
					this.LegRight01.rotateAngleX = -0.2F;
					this.LegRight01.rotateAngleY = 0F;
					this.LegRight01.rotateAngleZ = 0.1F;
			    	this.LegRight02.rotateAngleX = 0F;
			    	//equip
			    	this.EquipBase.isHidden = true;
		    	}
		    	else
		    	{
			    	switch (ent.getScaleLevel())
			    	{
			    	case 3:
			    		GlStateManager.translate(0F, 1.09F, 0F);
					break;
			    	case 2:
			    		GlStateManager.translate(0F, 1F, 0F);
					break;
			    	case 1:
			        	GlStateManager.translate(0F, 0.82F, 0F);
					break;
			    	default:
			    		GlStateManager.translate(0F, 0.55F, 0F);
					break;
			    	}
	    	
		      		this.setFaceScorn(ent);

		    	  	//Body
		        	this.Head.rotateAngleX += 0.1F;
		    	  	this.BodyMain.rotateAngleX = -0.1F;
		    	  	this.Butt.rotateAngleX = -0.4F;
		    	  	this.Butt.offsetZ = 0.19F;
		      	    this.Skirt01.rotateAngleX = -0.35F;
				  	this.Skirt02.rotateAngleX = -0.19F;
				  	this.Cloth02a1.rotateAngleX = 0.2F;
				  	this.Cloth02b1.rotateAngleX = 0.2F;
				  	this.Cloth02c1.rotateAngleX = 1.5F;
				  	this.Cloth02c2.rotateAngleX = 0.35F;
				  	this.Cloth02c3.rotateAngleX = 0.05F;
				  	this.Cloth02c4.rotateAngleX = 0.0F;
				  	this.Cloth02c1_1.rotateAngleX = 1.5F;
				  	this.Cloth02c2_1.rotateAngleX = 0.35F;
				  	this.Cloth02c3_1.rotateAngleX = 0.05F;
				  	this.Cloth02c4_1.rotateAngleX = 0.0F;
				  	this.ClothA03.rotateAngleY = 0.2F;
				  	this.ClothA03a.rotateAngleY = -0.2F;
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
		    		this.LegLeft02.rotateAngleX = 2.75F;
		    		this.LegLeft02.rotateAngleZ = 0.02F;
		    		this.LegLeft02.offsetZ = 0.37F;
		    		this.LegRight01.offsetY = 0.25F;
		    		this.LegRight01.offsetZ = -0.2F;
		    		this.LegRight01.rotateAngleY = -0.11F;
		    		this.LegRight01.rotateAngleZ = 0.12F;
		    		this.LegRight02.rotateAngleX = 2.75F;
		    		this.LegRight02.rotateAngleZ = -0.02F;
		    		this.LegRight02.offsetZ = 0.37F;
		    	}
	    	}
	    	else
	    	{
		    	switch (ent.getScaleLevel())
		    	{
		    	case 3:
		    		GlStateManager.translate(0F, 1.11F, 0F);
				break;
		    	case 2:
		    		GlStateManager.translate(0F, 0.99F, 0F);
				break;
		    	case 1:
		        	GlStateManager.translate(0F, 0.81F, 0F);
				break;
		    	default:
		    		GlStateManager.translate(0F, 0.53F, 0F);
				break;
		    	}
		    	
		    	//head
		    	this.Head.rotateAngleX -= 0.1F;
		    	//body
		    	this.BodyMain.rotateAngleX = -0.25F;
		    	this.Butt.rotateAngleX = -0.2F;
		    	this.Butt.offsetY = -0.1F;
				this.Skirt01.rotateAngleX = -0.07F;
				this.Skirt01.offsetY = -0.03F;
				this.Skirt02.rotateAngleX = -0.16F;
				this.Skirt02.offsetY = 0F;
				this.SkirtB01.rotateAngleX = -0.1F;
				this.Cloth02a1.rotateAngleX = -0.84F;
			  	this.Cloth02b1.rotateAngleX = -0.84F;
			  	this.Cloth02c1.rotateAngleX = 1.64F;
			  	this.Cloth02c2.rotateAngleX = -0.3F;
			  	this.Cloth02c3.rotateAngleX = 0.7F;
			  	this.Cloth02c4.rotateAngleX = 0.1F;
			  	this.Cloth02c1_1.rotateAngleX = 1.64F;
			  	this.Cloth02c2_1.rotateAngleX = -0.3F;
			  	this.Cloth02c3_1.rotateAngleX = 0.7F;
			  	this.Cloth02c4_1.rotateAngleX = 0.1F;
			  	this.ClothA03.rotateAngleY = 0.7F;
				this.ClothA03.offsetX = -0.2F;
			  	this.ClothA03a.rotateAngleY = -0.7F;
			  	this.ClothA03a.offsetX = 0.2F;
			  	addCA031 = 0F;
		    	addCA032 = 0F;
				//arm 
				this.ArmLeft01.rotateAngleX = 2.5F;
			    this.ArmLeft01.rotateAngleZ = 0.1F;
			    this.ArmLeft02.rotateAngleZ = 1F;
			    this.ArmRight01.rotateAngleX = 2.5F;
				this.ArmRight01.rotateAngleZ = -0.1F;
				this.ArmRight02.rotateAngleZ = -1F;
				//leg
				addk1 = -0.9F;
				addk2 = -0.9F;
				this.LegLeft01.rotateAngleZ = -0.14F;
				this.LegLeft02.rotateAngleX = 1.2217F;
				this.LegLeft02.rotateAngleY = 1.2217F;
				this.LegLeft02.rotateAngleZ = -1.0472F;
				this.LegLeft02.offsetX = 0F;
				this.LegLeft02.offsetY = 0F;
				this.LegLeft02.offsetZ = 0F;
				this.LegRight01.rotateAngleZ = 0.14F;
				this.LegRight02.rotateAngleX = 1.2217F;
				this.LegRight02.rotateAngleY = -1.2217F;
				this.LegRight02.rotateAngleZ = 1.0472F;
				this.LegRight02.offsetX = 0F;
				this.LegRight02.offsetY = 0F;
				this.LegRight02.offsetZ = 0F;
				
				//arm special
		    	float parTick = f2 - (int)f2 + (ent.getTickExisted() % 256);
		    	
		    	if (parTick < 30F)
		    	{
		    		float az = MathHelper.sin(parTick * 0.033F * 1.5708F);
			    	
			    	setFace(3);
		    		//arm 
				    this.ArmLeft01.rotateAngleZ = 0.1F + az * 1.8F;
				    this.ArmLeft02.rotateAngleZ = 1F - az * 2.88F;
				    if(this.ArmLeft02.rotateAngleZ < 0F) this.ArmLeft02.rotateAngleZ = 0F;
					this.ArmRight01.rotateAngleZ = -this.ArmLeft01.rotateAngleZ;
					this.ArmRight02.rotateAngleZ = -this.ArmLeft02.rotateAngleZ;
					//cloth
					this.ClothA03.rotateAngleY = 0.7F + az * -2.1F;
					this.ClothA03.offsetX = -0.2F + az * 0.73F;
				  	this.ClothA03a.rotateAngleY = -this.ClothA03.rotateAngleY;
				  	this.ClothA03a.offsetX = -this.ClothA03.offsetX;
				  	addCA031 = 0F;
			    	addCA032 = 0F + az * 0.3F;
		    	}
		    	else if (parTick < 45F)
		    	{
		    		setFace(3);
		    		//arm 
				    this.ArmLeft01.rotateAngleZ = 1.9F;
				    this.ArmLeft02.rotateAngleZ = 0F;
					this.ArmRight01.rotateAngleZ = -1.9F;
					this.ArmRight02.rotateAngleZ = 0F;
					//cloth
					this.ClothA03.rotateAngleY = -1.45F;
					this.ClothA03.offsetX = 0.53F;
				  	this.ClothA03a.rotateAngleY = 1.45F;
				  	this.ClothA03a.offsetX = -0.53F;
				  	addCA031 = 0F;
			    	addCA032 = 0.3F;
		    	}
		    	else if (parTick < 53F)
		    	{
		    		float az = MathHelper.cos((parTick - 45F) * 0.125F * 1.5708F);
			    	
			    	//arm 
				    this.ArmLeft01.rotateAngleZ = 0.1F + az * 1.8F;
				    this.ArmLeft02.rotateAngleZ = 1F - az;
					this.ArmRight01.rotateAngleZ = -this.ArmLeft01.rotateAngleZ;
					this.ArmRight02.rotateAngleZ = -this.ArmLeft02.rotateAngleZ;
					//cloth
					this.ClothA03.rotateAngleY = 0.7F + az * -2F;
					this.ClothA03.offsetX = -0.2F + az * 0.73F;
				  	this.ClothA03a.rotateAngleY = -this.ClothA03.rotateAngleY;
				  	this.ClothA03a.offsetX = -this.ClothA03.offsetX;
				  	addCA031 = 0F;
			    	addCA032 = 0F + az * 0.3F;
		    	}
	    	}
  		}//end if sitting
	    
	    //attack
	    if (ent.getAttackTick() > 20)
	    {
	    	spcStand = false;
	    	
	    	this.setFace(3);
			this.setMouth(5);
				
	    	//Body
  		  	this.BodyMain.rotateAngleX = -0.17F;
  		    //arm 
  		  	this.ArmLeft01.rotateAngleX = 0.17F;
  		  	this.ArmLeft01.rotateAngleY = 0F;
  		  	this.ArmLeft01.rotateAngleZ = -0.35F;
  		  	this.ArmLeft02.rotateAngleX = -1.57F;
  		  	this.ArmLeft02.rotateAngleY = 0F;
  		  	this.ArmLeft02.rotateAngleZ = 0F;
  			this.ArmRight01.rotateAngleX = 0.17F;
  			this.ArmRight01.rotateAngleY = 0F;
  			this.ArmRight01.rotateAngleZ = 0.35F;
  			this.ArmRight02.rotateAngleX = -1.57F;
  		  	this.ArmRight02.rotateAngleY = 0F;
  		  	this.ArmRight02.rotateAngleZ = 0F;
  			//leg
  			addk1 += 0.14F;
  			addk2 += 0.07F;
  			this.LegLeft01.rotateAngleY = 0F;
  			this.LegLeft01.rotateAngleZ = -0.17F;
  			this.LegRight01.rotateAngleY = 0F;
  			this.LegRight01.rotateAngleZ = 0.17F;
	    }
	    
	    //special stand pose
  		if (spcStand)
  		{
  			//Body
  		  	this.BodyMain.rotateAngleX = -0.17F;
  		  	//arm 
  		  	this.ArmLeft01.rotateAngleX = 0.17F;
  		  	this.ArmLeft01.rotateAngleY = 0F;
  		  	this.ArmLeft01.rotateAngleZ = -0.35F;
  		  	this.ArmLeft02.rotateAngleX = -1.57F;
  		  	this.ArmLeft02.rotateAngleY = 0F;
  		  	this.ArmLeft02.rotateAngleZ = 0F;
  			this.ArmRight01.rotateAngleX = 0.17F;
  			this.ArmRight01.rotateAngleY = 0F;
  			this.ArmRight01.rotateAngleZ = 0.35F;
  			this.ArmRight02.rotateAngleX = -1.57F;
  		  	this.ArmRight02.rotateAngleY = 0F;
  		  	this.ArmRight02.rotateAngleZ = 0F;
  			//leg
  			addk1 += 0.14F;
  			addk2 += 0.07F;
  			this.LegLeft01.rotateAngleY = 0F;
  			this.LegLeft01.rotateAngleZ = -0.17F;
  			this.LegRight01.rotateAngleY = 0F;
  			this.LegRight01.rotateAngleZ = 0.17F;
  			
  			if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED)
  			{
  				this.setFace(3);
  				this.setMouth(5);
  			}
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
	  	
	  	//sleeves
	  	float HandL = this.BodyMain.rotateAngleX + this.ArmLeft01.rotateAngleX + this.ArmLeft02.rotateAngleX;
	  	float HandR = this.BodyMain.rotateAngleX + this.ArmRight01.rotateAngleX + this.ArmRight02.rotateAngleX;
	  	float HandLc = MathHelper.cos(HandL);
	  	float HandLs = MathHelper.sin(HandL);
	  	float HandRc = MathHelper.cos(HandR);
	  	float HandRs = MathHelper.sin(HandR);
	  	this.ClothA03.offsetY = HandLc * 0.1F + addCA031;
	  	this.ClothA04.offsetY = HandLc * 0.2F;
	  	this.ClothA05.offsetY = HandLc * 0.25F;
	  	this.ClothA03.offsetZ = HandLs * -0.32F + addCA032;
	  	this.ClothA04.offsetZ = HandLs * -0.32F;
	  	this.ClothA05.offsetZ = HandLs * -0.32F;
	  	this.ClothA03a.offsetY = HandRc * 0.1F - addCA031;
	  	this.ClothA04a.offsetY = HandRc * 0.2F;
	  	this.ClothA05a.offsetY = HandRc * 0.25F;
	  	this.ClothA03a.offsetZ = HandRs * -0.32F + addCA032;
	  	this.ClothA04a.offsetZ = HandRs * -0.32F;
	  	this.ClothA05a.offsetZ = HandRs * -0.32F;
	  	
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
	
}