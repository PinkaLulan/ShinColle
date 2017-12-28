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
 * ModelBBKongou - PinkaLulan
 * Created using Tabula 6.0.0  2017/12/27
 */
public class ModelBBKongou extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Butt;
    public ModelRenderer Ahoke00;
    public ModelRenderer ArmLeft01;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer ArmRight01;
    public ModelRenderer EquipBase;
    public ModelRenderer Cloth03a1;
    public ModelRenderer Cloth03a2;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Ahoke01;
    public ModelRenderer EquipHeadBase;
    public ModelRenderer HairU01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL01;
    public ModelRenderer HairCBase;
    public ModelRenderer HairCBaseB;
    public ModelRenderer HairS01;
    public ModelRenderer HairS02;
    public ModelRenderer HairR02;
    public ModelRenderer HairL02;
    public ModelRenderer HairC01;
    public ModelRenderer HairC02;
    public ModelRenderer HairC03;
    public ModelRenderer HairC04;
    public ModelRenderer HairC05;
    public ModelRenderer HairC01b;
    public ModelRenderer HairC02b;
    public ModelRenderer HairC03b;
    public ModelRenderer HairC04b;
    public ModelRenderer HairC05b;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Ahoke02;
    public ModelRenderer Ahoke03;
    public ModelRenderer Ahoke04;
    public ModelRenderer EquipHead01;
    public ModelRenderer EquipHead01a;
    public ModelRenderer EquipHead02;
    public ModelRenderer EquipHead03;
    public ModelRenderer EquipHead02a;
    public ModelRenderer EquipHead03a;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight01;
    public ModelRenderer SkirtB01;
    public ModelRenderer LegLeft02;
    public ModelRenderer Skirt02;
    public ModelRenderer LegRight02;
    public ModelRenderer Cloth01a;
    public ModelRenderer Cloth02a1;
    public ModelRenderer Cloth02b1;
    public ModelRenderer Cloth02c1;
    public ModelRenderer Cloth02c1_1;
    public ModelRenderer Cloth01b;
    public ModelRenderer Cloth01c;
    public ModelRenderer Cloth01b2;
    public ModelRenderer Cloth01c2;
    public ModelRenderer Cloth02a2;
    public ModelRenderer Cloth02a3;
    public ModelRenderer Cloth02b2;
    public ModelRenderer Cloth02b3;
    public ModelRenderer Cloth02c2;
    public ModelRenderer Cloth02c3;
    public ModelRenderer Cloth02c4;
    public ModelRenderer Cloth02c2_1;
    public ModelRenderer Cloth02c3_1;
    public ModelRenderer Cloth02c4_1;
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
    public ModelRenderer EquipB01;
    public ModelRenderer EquipB00a;
    public ModelRenderer EquipB00a_1;
    public ModelRenderer EquipB02;
    public ModelRenderer EquipB01a;
    public ModelRenderer EquipB01b00;
    public ModelRenderer EquipB04;
    public ModelRenderer EquipB04_1;
    public ModelRenderer EquipB03;
    public ModelRenderer EquipB02a;
    public ModelRenderer EquipB01c;
    public ModelRenderer EquipB01b01a;
    public ModelRenderer EquipB01b01b;
    public ModelRenderer EquipB01b01c;
    public ModelRenderer EquipB01b02;
    public ModelRenderer EquipB01b03;
    public ModelRenderer EquipB01b04;
    public ModelRenderer EquipB01b05;
    public ModelRenderer EquipB01b06;
    public ModelRenderer EquipB05;
    public ModelRenderer EquipB06a;
    public ModelRenderer EquipB06b;
    public ModelRenderer EquipB06c;
    public ModelRenderer EquipB06d;
    public ModelRenderer EquipB06e;
    public ModelRenderer EquipB06f;
    public ModelRenderer EquipCL1Base01;
    public ModelRenderer EquipCL1Base02;
    public ModelRenderer EquipCL1a1;
    public ModelRenderer EquipCL1a1_1;
    public ModelRenderer EquipCL1a2;
    public ModelRenderer EquipCL1a2_1;
    public ModelRenderer EquipB05_1;
    public ModelRenderer EquipB07a1;
    public ModelRenderer EquipB07b1;
    public ModelRenderer EquipB07c1;
    public ModelRenderer EquipB07d1;
    public ModelRenderer EquipCL1Base01_1;
    public ModelRenderer EquipCL1Base02_1;
    public ModelRenderer EquipCL1a1_2;
    public ModelRenderer EquipCL1a1_3;
    public ModelRenderer EquipCL1a2_2;
    public ModelRenderer EquipCL1a2_3;
    public ModelRenderer EquipB07a2;
    public ModelRenderer EquipB07b2;
    public ModelRenderer EquipB07c2;
    public ModelRenderer EquipB07d2;
    public ModelRenderer EquipB07d3;
    public ModelRenderer EquipB05_2;
    public ModelRenderer EquipB06a_1;
    public ModelRenderer EquipB06b_1;
    public ModelRenderer EquipB06c_1;
    public ModelRenderer EquipB06d_1;
    public ModelRenderer EquipB06e_1;
    public ModelRenderer EquipB06f_1;
    public ModelRenderer EquipCL1Base01_2;
    public ModelRenderer EquipCL1Base02_2;
    public ModelRenderer EquipCL1a1_4;
    public ModelRenderer EquipCL1a1_5;
    public ModelRenderer EquipCL1a2_4;
    public ModelRenderer EquipCL1a2_5;
    public ModelRenderer EquipB05_3;
    public ModelRenderer EquipB07a1_1;
    public ModelRenderer EquipB07b1_1;
    public ModelRenderer EquipB07c1_1;
    public ModelRenderer EquipB07d1_1;
    public ModelRenderer EquipCL1Base01_3;
    public ModelRenderer EquipCL1Base02_3;
    public ModelRenderer EquipCL1a1_6;
    public ModelRenderer EquipCL1a1_7;
    public ModelRenderer EquipCL1a2_6;
    public ModelRenderer EquipCL1a2_7;
    public ModelRenderer EquipB07a2_1;
    public ModelRenderer EquipB07b2_1;
    public ModelRenderer EquipB07c2_1;
    public ModelRenderer EquipB07d2_1;
    public ModelRenderer EquipB07d3_1;
    public ModelRenderer EquipB00b;
    public ModelRenderer EquipB00c;
    public ModelRenderer EquipB00d;
    public ModelRenderer EquipB00b_1;
    public ModelRenderer EquipB00c_1;
    public ModelRenderer EquipB00d_1;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowNeck;
    
    
    public ModelBBKongou()
    {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.offsetItem = new float[] {0.1F, 0.86F, -0.12F};
        this.offsetBlock = new float[] {0.1F, 0.86F, -0.12F};
        
        this.setDefaultFaceModel();
        
        this.HairC02 = new ModelRenderer(this, 40, 0);
        this.HairC02.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.HairC02.addBox(0.0F, 0.0F, 0.0F, 2, 10, 0, 0.0F);
        this.setRotateAngle(HairC02, -1.3439035240356336F, 0.0F, 0.0F);
        this.EquipB06b_1 = new ModelRenderer(this, 0, 0);
        this.EquipB06b_1.setRotationPoint(-11.4F, 4.0F, 0.5F);
        this.EquipB06b_1.addBox(-3.0F, 0.0F, 0.0F, 3, 6, 11, 0.0F);
        this.EquipB01b00 = new ModelRenderer(this, 0, 0);
        this.EquipB01b00.setRotationPoint(0.0F, 0.1F, 9.8F);
        this.EquipB01b00.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.EquipCL1a2_3 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_3.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_3.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_3, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipB05 = new ModelRenderer(this, 0, 0);
        this.EquipB05.setRotationPoint(9.4F, -3.8F, 6.0F);
        this.EquipB05.addBox(-4.5F, 0.0F, -4.5F, 9, 8, 9, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 68);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.19198621771937624F, 0.0F, -0.08726646259971647F);
        this.EquipCL1a2_7 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_7.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_7.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_7, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipB07a2_1 = new ModelRenderer(this, 0, 0);
        this.EquipB07a2_1.setRotationPoint(-12.0F, 0.0F, 0.5F);
        this.EquipB07a2_1.addBox(-5.0F, -2.0F, -1.0F, 5, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07a2_1, 0.0F, -1.0733774899765127F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.ClothB01 = new ModelRenderer(this, 25, 37);
        this.ClothB01.setRotationPoint(2.9F, 4.6F, 1.6F);
        this.ClothB01.addBox(-4.5F, 0.0F, 0.0F, 9, 7, 0, 0.0F);
        this.setRotateAngle(ClothB01, 0.9599310885968813F, -0.006806784082777885F, 0.09477137838329208F);
        this.EquipB00d_1 = new ModelRenderer(this, 0, 0);
        this.EquipB00d_1.setRotationPoint(0.0F, 0.0F, 1.1F);
        this.EquipB00d_1.addBox(-0.5F, -4.0F, -0.5F, 1, 8, 1, 0.0F);
        this.EquipCL1Base01 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base01.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.EquipCL1Base01.addBox(-4.5F, -4.0F, -1.5F, 9, 4, 8, 0.0F);
        this.setRotateAngle(EquipCL1Base01, 0.0F, -1.5707963267948966F, 0.0F);
        this.EquipB05_2 = new ModelRenderer(this, 0, 0);
        this.EquipB05_2.setRotationPoint(-9.4F, -3.8F, 6.0F);
        this.EquipB05_2.addBox(-4.5F, 0.0F, -4.5F, 9, 8, 9, 0.0F);
        this.Cloth02c4 = new ModelRenderer(this, 58, 7);
        this.Cloth02c4.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Cloth02c4.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 0, 0.0F);
        this.ClothA02a = new ModelRenderer(this, 128, 49);
        this.ClothA02a.setRotationPoint(2.5F, 0.0F, -2.5F);
        this.ClothA02a.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
        this.EquipB00b_1 = new ModelRenderer(this, 0, 0);
        this.EquipB00b_1.setRotationPoint(-6.0F, -0.1F, 2.0F);
        this.EquipB00b_1.addBox(-8.0F, 0.0F, -2.0F, 8, 4, 2, 0.0F);
        this.setRotateAngle(EquipB00b_1, 0.0F, -1.3089969389957472F, 0.0F);
        this.EquipB01b01b = new ModelRenderer(this, 0, 0);
        this.EquipB01b01b.setRotationPoint(1.0F, -7.9F, 0.0F);
        this.EquipB01b01b.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(EquipB01b01b, -0.08726646259971647F, 0.0F, -0.12217304763960307F);
        this.Ahoke03 = new ModelRenderer(this, 44, 0);
        this.Ahoke03.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Ahoke03.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke03, 1.48352986419518F, 0.0F, 0.0F);
        this.Cloth02c2 = new ModelRenderer(this, 58, 7);
        this.Cloth02c2.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.Cloth02c2.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 0, 0.0F);
        this.setRotateAngle(Cloth02c2, -0.7853981633974483F, 0.0F, 0.0F);
        this.EquipB06e_1 = new ModelRenderer(this, 0, 0);
        this.EquipB06e_1.setRotationPoint(-25.1F, 4.0F, 0.5F);
        this.EquipB06e_1.addBox(0.0F, 0.0F, 0.0F, 4, 9, 11, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 68);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.296705972839036F, 0.0F, 0.08726646259971647F);
        this.EquipCL1Base02 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base02.mirror = true;
        this.EquipCL1Base02.setRotationPoint(0.0F, 0.3F, -2.8F);
        this.EquipCL1Base02.addBox(-4.5F, -4.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(EquipCL1Base02, 0.17453292519943295F, 0.0F, 0.0F);
        this.BoobL = new ModelRenderer(this, 25, 44);
        this.BoobL.setRotationPoint(3.5F, -8.2F, -3.7F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 4, 0.0F);
        this.setRotateAngle(BoobL, -0.8726646259971648F, 0.08726646259971647F, 0.06981317007977318F);
        this.ArmRight01 = new ModelRenderer(this, 24, 71);
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, -0.08726646259971647F, 0.0F, 0.3141592653589793F);
        this.EquipB01b02 = new ModelRenderer(this, 0, 0);
        this.EquipB01b02.setRotationPoint(0.0F, -8.7F, 0.6F);
        this.EquipB01b02.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4, 0.0F);
        this.HairC04 = new ModelRenderer(this, 40, 0);
        this.HairC04.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.HairC04.addBox(0.0F, 0.0F, 0.0F, 2, 11, 0, 0.0F);
        this.setRotateAngle(HairC04, 1.0471975511965976F, 0.0F, 0.0F);
        this.Hair02 = new ModelRenderer(this, 52, 35);
        this.Hair02.setRotationPoint(0.0F, 10.5F, 5.7F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 13, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipB05_1 = new ModelRenderer(this, 0, 0);
        this.EquipB05_1.setRotationPoint(4.8F, -1.9F, 5.0F);
        this.EquipB05_1.addBox(-4.5F, 0.0F, -4.5F, 9, 2, 9, 0.0F);
        this.EquipB07c1 = new ModelRenderer(this, 153, 49);
        this.EquipB07c1.setRotationPoint(-1.0F, 2.4F, 12.4F);
        this.EquipB07c1.addBox(0.0F, -2.0F, -0.5F, 12, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07c1, 0.0F, -0.08726646259971647F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 54);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipB01 = new ModelRenderer(this, 185, 0);
        this.EquipB01.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.EquipB01.addBox(-5.5F, 0.0F, 0.0F, 11, 10, 9, 0.0F);
        this.EquipB00a = new ModelRenderer(this, 0, 0);
        this.EquipB00a.setRotationPoint(2.2F, -5.5F, -1.0F);
        this.EquipB00a.addBox(0.0F, 0.0F, 0.0F, 6, 4, 2, 0.0F);
        this.setRotateAngle(EquipB00a, 0.0F, 0.2617993877991494F, 0.0F);
        this.EquipB07d3 = new ModelRenderer(this, 51, 0);
        this.EquipB07d3.setRotationPoint(0.0F, -1.7F, 0.0F);
        this.EquipB07d3.addBox(0.0F, -7.0F, -0.5F, 2, 7, 1, 0.0F);
        this.setRotateAngle(EquipB07d3, 0.0F, 0.0F, -0.08726646259971647F);
        this.EquipB00d = new ModelRenderer(this, 0, 0);
        this.EquipB00d.setRotationPoint(0.0F, 0.0F, 1.1F);
        this.EquipB00d.addBox(-0.5F, -4.0F, -0.5F, 1, 8, 1, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 128, 17);
        this.Skirt02.setRotationPoint(0.0F, 3.5F, -2.7F);
        this.Skirt02.addBox(-9.5F, 0.0F, -6.5F, 19, 5, 13, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.Cloth02c2_1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c2_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.Cloth02c2_1.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 0, 0.0F);
        this.setRotateAngle(Cloth02c2_1, -0.7853981633974483F, 0.0F, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 47);
        this.LegLeft02.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.EquipCL1a1_4 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_4.setRotationPoint(2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_4.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_4, -0.20943951023931953F, 0.0F, 0.0F);
        this.Cloth01c2 = new ModelRenderer(this, 73, 5);
        this.Cloth01c2.mirror = true;
        this.Cloth01c2.setRotationPoint(2.0F, -0.4F, -0.7F);
        this.Cloth01c2.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth01c2, -0.2617993877991494F, -0.13962634015954636F, -0.17453292519943295F);
        this.Cloth02b1 = new ModelRenderer(this, 59, 0);
        this.Cloth02b1.setRotationPoint(-4.0F, 1.8F, -4.9F);
        this.Cloth02b1.addBox(-1.5F, 0.0F, 0.0F, 3, 5, 0, 0.0F);
        this.setRotateAngle(Cloth02b1, -0.5585053606381855F, 0.0F, 0.06981317007977318F);
        this.Cloth02a2 = new ModelRenderer(this, 59, 0);
        this.Cloth02a2.setRotationPoint(0.0F, 4.9F, 0.0F);
        this.Cloth02a2.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth02a2, 0.17453292519943295F, 0.0F, 0.05235987755982988F);
        this.EquipB06e = new ModelRenderer(this, 0, 0);
        this.EquipB06e.setRotationPoint(21.1F, 4.0F, 0.5F);
        this.EquipB06e.addBox(0.0F, 0.0F, 0.0F, 4, 9, 11, 0.0F);
        this.Cloth02b2 = new ModelRenderer(this, 59, 0);
        this.Cloth02b2.setRotationPoint(0.0F, 4.9F, 0.0F);
        this.Cloth02b2.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth02b2, 0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.EquipB05_3 = new ModelRenderer(this, 0, 0);
        this.EquipB05_3.setRotationPoint(-4.8F, -1.9F, 5.0F);
        this.EquipB05_3.addBox(-4.5F, 0.0F, -4.5F, 9, 2, 9, 0.0F);
        this.EquipB01b01a = new ModelRenderer(this, 0, 0);
        this.EquipB01b01a.setRotationPoint(0.0F, -7.9F, 1.2F);
        this.EquipB01b01a.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(EquipB01b01a, 0.12217304763960307F, 0.0F, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.EquipCL1Base01_3 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base01_3.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.EquipCL1Base01_3.addBox(-4.5F, -4.0F, -1.5F, 9, 4, 8, 0.0F);
        this.Cloth01b2 = new ModelRenderer(this, 65, 0);
        this.Cloth01b2.setRotationPoint(0.0F, 0.5F, 0.3F);
        this.Cloth01b2.addBox(0.0F, -3.0F, -1.0F, 6, 3, 2, 0.0F);
        this.setRotateAngle(Cloth01b2, 0.08726646259971647F, 0.17453292519943295F, 0.3490658503988659F);
        this.EquipB00b = new ModelRenderer(this, 0, 0);
        this.EquipB00b.setRotationPoint(6.0F, -0.1F, 2.0F);
        this.EquipB00b.addBox(0.0F, 0.0F, -2.0F, 8, 4, 2, 0.0F);
        this.setRotateAngle(EquipB00b, 0.0F, 1.3089969389957472F, 0.0F);
        this.EquipHead03a = new ModelRenderer(this, 40, 105);
        this.EquipHead03a.setRotationPoint(0.2F, 0.9F, 0.5F);
        this.EquipHead03a.addBox(-7.0F, 0.0F, 0.0F, 7, 2, 1, 0.0F);
        this.EquipCL1Base01_2 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base01_2.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.EquipCL1Base01_2.addBox(-4.5F, -4.0F, -1.5F, 9, 4, 8, 0.0F);
        this.setRotateAngle(EquipCL1Base01_2, 0.0F, 1.5707963267948966F, 0.0F);
        this.EquipB07d3_1 = new ModelRenderer(this, 51, 0);
        this.EquipB07d3_1.setRotationPoint(0.0F, -1.7F, 0.0F);
        this.EquipB07d3_1.addBox(-2.0F, -7.0F, -0.5F, 2, 7, 1, 0.0F);
        this.setRotateAngle(EquipB07d3_1, 0.0F, 0.0F, 0.08726646259971647F);
        this.EquipCL1a2_1 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_1.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_1.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_1, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipCL1Base02_3 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base02_3.setRotationPoint(0.0F, 0.3F, -2.8F);
        this.EquipCL1Base02_3.addBox(-4.5F, -4.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(EquipCL1Base02_3, 0.17453292519943295F, 0.0F, 0.0F);
        this.HairC03b = new ModelRenderer(this, 40, 0);
        this.HairC03b.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.HairC03b.addBox(-2.0F, 0.0F, 0.0F, 2, 7, 0, 0.0F);
        this.setRotateAngle(HairC03b, 0.5009094953223726F, 0.0F, 0.8726646259971648F);
        this.Cloth01c = new ModelRenderer(this, 73, 5);
        this.Cloth01c.setRotationPoint(-2.0F, -0.4F, -0.7F);
        this.Cloth01c.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth01c, -0.2617993877991494F, 0.13962634015954636F, 0.17453292519943295F);
        this.ClothA02 = new ModelRenderer(this, 128, 49);
        this.ClothA02.setRotationPoint(-2.5F, 0.0F, -2.5F);
        this.ClothA02.addBox(-3.0F, 0.0F, -3.0F, 6, 9, 6, 0.0F);
        this.EquipB00c = new ModelRenderer(this, 0, 0);
        this.EquipB00c.setRotationPoint(5.7F, 2.0F, -0.3F);
        this.EquipB00c.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(EquipB00c, 0.0F, 0.0F, 0.6108652381980153F);
        this.EquipB06d = new ModelRenderer(this, 0, 0);
        this.EquipB06d.setRotationPoint(17.2F, 4.0F, 0.5F);
        this.EquipB06d.addBox(0.0F, 0.0F, 0.0F, 4, 9, 11, 0.0F);
        this.Cloth03b_1 = new ModelRenderer(this, 161, 80);
        this.Cloth03b_1.setRotationPoint(0.6F, -0.8F, -0.1F);
        this.Cloth03b_1.addBox(-1.0F, 0.0F, 0.0F, 2, 5, 5, 0.0F);
        this.setRotateAngle(Cloth03b_1, 0.0F, 0.0F, -0.08726646259971647F);
        this.EquipB02a = new ModelRenderer(this, 0, 30);
        this.EquipB02a.setRotationPoint(0.0F, -4.9F, 4.6F);
        this.EquipB02a.addBox(-2.0F, 0.0F, -1.0F, 4, 5, 4, 0.0F);
        this.EquipHeadBase = new ModelRenderer(this, 33, 16);
        this.EquipHeadBase.setRotationPoint(0.0F, -11.8F, -7.6F);
        this.EquipHeadBase.addBox(-8.0F, 0.0F, 0.0F, 16, 2, 15, 0.0F);
        this.HairS01 = new ModelRenderer(this, 110, 22);
        this.HairS01.setRotationPoint(-8.8F, 3.1F, 3.3F);
        this.HairS01.addBox(-1.5F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.setRotateAngle(HairS01, 0.0F, 0.05235987755982988F, 0.0F);
        this.HairL02 = new ModelRenderer(this, 90, 103);
        this.HairL02.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.HairL02.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 3, 0.0F);
        this.setRotateAngle(HairL02, 0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.EquipB07d1 = new ModelRenderer(this, 153, 49);
        this.EquipB07d1.setRotationPoint(-1.0F, -1.6F, 12.4F);
        this.EquipB07d1.addBox(0.0F, -2.0F, -0.5F, 12, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07d1, 0.0F, -0.08726646259971647F, 0.0F);
        this.Cloth03a1 = new ModelRenderer(this, 159, 55);
        this.Cloth03a1.setRotationPoint(4.1F, -11.1F, -4.1F);
        this.Cloth03a1.addBox(-1.0F, 0.0F, 0.0F, 2, 18, 7, 0.0F);
        this.Cloth01a = new ModelRenderer(this, 81, 0);
        this.Cloth01a.setRotationPoint(0.0F, 2.3F, -5.0F);
        this.Cloth01a.addBox(-1.0F, -2.5F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Cloth01a, -0.2617993877991494F, 0.0F, 0.0F);
        this.EquipB00a_1 = new ModelRenderer(this, 0, 0);
        this.EquipB00a_1.setRotationPoint(-2.2F, -5.5F, -1.0F);
        this.EquipB00a_1.addBox(-6.0F, 0.0F, 0.0F, 6, 4, 2, 0.0F);
        this.setRotateAngle(EquipB00a_1, 0.0F, -0.2617993877991494F, 0.0F);
        this.HairCBase = new ModelRenderer(this, 0, 0);
        this.HairCBase.setRotationPoint(6.0F, 1.0F, -1.6F);
        this.HairCBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(HairCBase, 0.0F, 0.0F, -0.3141592653589793F);
        this.Cloth02c4_1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c4_1.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Cloth02c4_1.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 0, 0.0F);
        this.EquipB01a = new ModelRenderer(this, 0, 0);
        this.EquipB01a.setRotationPoint(0.0F, -3.9F, 4.8F);
        this.EquipB01a.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.Cloth02c3_1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c3_1.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.Cloth02c3_1.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 0, 0.0F);
        this.setRotateAngle(Cloth02c3_1, -0.13962634015954636F, 0.0F, -0.03490658503988659F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, 6.5F, 9.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.EquipHead01a = new ModelRenderer(this, 36, 108);
        this.EquipHead01a.setRotationPoint(-7.5F, 0.2F, 7.0F);
        this.EquipHead01a.addBox(-8.0F, 0.0F, 0.0F, 8, 1, 2, 0.0F);
        this.setRotateAngle(EquipHead01a, 0.0F, 0.0F, 0.08726646259971647F);
        this.Cloth03a2 = new ModelRenderer(this, 159, 55);
        this.Cloth03a2.mirror = true;
        this.Cloth03a2.setRotationPoint(-4.1F, -11.1F, -4.1F);
        this.Cloth03a2.addBox(-1.0F, 0.0F, 0.0F, 2, 18, 7, 0.0F);
        this.HairC05 = new ModelRenderer(this, 40, 0);
        this.HairC05.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.HairC05.addBox(0.0F, 0.0F, 0.0F, 2, 5, 0, 0.0F);
        this.setRotateAngle(HairC05, 1.7453292519943295F, 0.0F, 0.0F);
        this.EquipB06d_1 = new ModelRenderer(this, 0, 0);
        this.EquipB06d_1.setRotationPoint(-17.2F, 4.0F, 0.5F);
        this.EquipB06d_1.addBox(-4.0F, 0.0F, 0.0F, 4, 9, 11, 0.0F);
        this.EquipCL1Base01_1 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base01_1.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.EquipCL1Base01_1.addBox(-4.5F, -4.0F, -1.5F, 9, 4, 8, 0.0F);
        this.EquipHead03 = new ModelRenderer(this, 40, 105);
        this.EquipHead03.setRotationPoint(0.2F, 0.9F, 0.5F);
        this.EquipHead03.addBox(0.0F, 0.0F, 0.0F, 7, 2, 1, 0.0F);
        this.ClothA03 = new ModelRenderer(this, 128, 65);
        this.ClothA03.setRotationPoint(0.1F, -0.1F, -2.2F);
        this.ClothA03.addBox(-2.5F, 0.0F, 0.0F, 5, 9, 6, 0.0F);
        this.EquipB07a1_1 = new ModelRenderer(this, 153, 49);
        this.EquipB07a1_1.mirror = true;
        this.EquipB07a1_1.setRotationPoint(1.2F, 8.7F, 12.0F);
        this.EquipB07a1_1.addBox(-12.0F, -2.0F, -0.5F, 12, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07a1_1, -0.20943951023931953F, 0.08726646259971647F, -0.12217304763960307F);
        this.EquipHead02a = new ModelRenderer(this, 44, 82);
        this.EquipHead02a.setRotationPoint(-0.4F, -1.9F, 0.0F);
        this.EquipHead02a.addBox(-7.0F, 0.0F, 1.0F, 7, 2, 0, 0.0F);
        this.EquipCL1a2_6 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_6.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_6.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_6, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipB07b2_1 = new ModelRenderer(this, 0, 0);
        this.EquipB07b2_1.setRotationPoint(-12.0F, 0.0F, 0.5F);
        this.EquipB07b2_1.addBox(-6.0F, -2.0F, -1.0F, 6, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07b2_1, 0.0F, -1.0471975511965976F, 0.0F);
        this.BoobR = new ModelRenderer(this, 0, 39);
        this.BoobR.setRotationPoint(-3.5F, -8.2F, -3.8F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 4, 0.0F);
        this.setRotateAngle(BoobR, -0.8726646259971648F, -0.08726646259971647F, -0.06981317007977318F);
        this.HairR02 = new ModelRenderer(this, 90, 103);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 8.0F, 0.0F);
        this.HairR02.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 3, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.EquipCL1a2_4 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_4.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_4.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_4, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipB01c = new ModelRenderer(this, 0, 20);
        this.EquipB01c.setRotationPoint(0.0F, -4.9F, 0.5F);
        this.EquipB01c.addBox(-2.5F, 0.0F, -2.5F, 5, 5, 5, 0.0F);
        this.EquipB07d1_1 = new ModelRenderer(this, 153, 49);
        this.EquipB07d1_1.mirror = true;
        this.EquipB07d1_1.setRotationPoint(1.0F, -1.6F, 12.4F);
        this.EquipB07d1_1.addBox(-12.0F, -2.0F, -0.5F, 12, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07d1_1, 0.0F, 0.08726646259971647F, 0.0F);
        this.EquipHead01 = new ModelRenderer(this, 36, 108);
        this.EquipHead01.setRotationPoint(7.5F, 0.2F, 7.0F);
        this.EquipHead01.addBox(0.0F, 0.0F, 0.0F, 8, 1, 2, 0.0F);
        this.setRotateAngle(EquipHead01, 0.0F, 0.0F, -0.08726646259971647F);
        this.EquipCL1a1_2 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_2.setRotationPoint(2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_2.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_2, -0.20943951023931953F, 0.0F, 0.0F);
        this.Cloth01b = new ModelRenderer(this, 65, 0);
        this.Cloth01b.setRotationPoint(0.0F, 0.5F, 0.3F);
        this.Cloth01b.addBox(-6.0F, -3.0F, -1.0F, 6, 3, 2, 0.0F);
        this.setRotateAngle(Cloth01b, 0.08726646259971647F, -0.17453292519943295F, -0.3490658503988659F);
        this.EquipCL1a1_7 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_7.setRotationPoint(-2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_7.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_7, -0.20943951023931953F, 0.0F, 0.0F);
        this.HairC03 = new ModelRenderer(this, 40, 0);
        this.HairC03.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.HairC03.addBox(0.0F, 0.0F, 0.0F, 2, 7, 0, 0.0F);
        this.setRotateAngle(HairC03, 0.5009094953223726F, 0.0F, -0.8726646259971648F);
        this.EquipCL1a2_5 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_5.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_5.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_5, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipHead02 = new ModelRenderer(this, 44, 82);
        this.EquipHead02.setRotationPoint(0.4F, -1.9F, 0.0F);
        this.EquipHead02.addBox(0.0F, 0.0F, 1.0F, 7, 2, 0, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 54);
        this.ArmRight02.setRotationPoint(-3F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0F, 0F, -5F, 5, 12, 5, 0.0F);
        this.EquipCL1Base02_2 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base02_2.setRotationPoint(0.0F, 0.3F, -2.8F);
        this.EquipCL1Base02_2.addBox(-4.5F, -4.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(EquipCL1Base02_2, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipCL1a2 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2, -1.5707963267948966F, 0.0F, 0.0F);
        this.EquipB06a = new ModelRenderer(this, 0, 0);
        this.EquipB06a.setRotationPoint(2.5F, 3.9F, 0.5F);
        this.EquipB06a.addBox(0.0F, 0.0F, 0.0F, 9, 4, 11, 0.0F);
        this.ClothA05a = new ModelRenderer(this, 128, 96);
        this.ClothA05a.setRotationPoint(0.0F, 1.9F, 0.8F);
        this.ClothA05a.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 6, 0.0F);
        this.HairC02b = new ModelRenderer(this, 40, 0);
        this.HairC02b.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.HairC02b.addBox(-2.0F, 0.0F, 0.0F, 2, 10, 0, 0.0F);
        this.setRotateAngle(HairC02b, -1.3439035240356336F, 0.0F, 0.0F);
        this.Cloth02c3 = new ModelRenderer(this, 58, 7);
        this.Cloth02c3.setRotationPoint(0.0F, 6.9F, 0.0F);
        this.Cloth02c3.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 0, 0.0F);
        this.setRotateAngle(Cloth02c3, -0.13962634015954636F, 0.0F, 0.03490658503988659F);
        this.HairC04b = new ModelRenderer(this, 40, 0);
        this.HairC04b.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.HairC04b.addBox(-2.0F, 0.0F, 0.0F, 2, 11, 0, 0.0F);
        this.setRotateAngle(HairC04b, 1.0471975511965976F, 0.0F, 0.0F);
        this.EquipB00c_1 = new ModelRenderer(this, 0, 0);
        this.EquipB00c_1.setRotationPoint(-5.7F, 2.0F, -0.3F);
        this.EquipB00c_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.setRotateAngle(EquipB00c_1, 0.0F, 0.0F, -0.6108652381980153F);
        this.EquipB07b1 = new ModelRenderer(this, 153, 49);
        this.EquipB07b1.setRotationPoint(-1.0F, 5.6F, 12.4F);
        this.EquipB07b1.addBox(0.0F, -2.0F, -0.5F, 12, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07b1, 0.0F, -0.08726646259971647F, 0.05235987755982988F);
        this.ClothA03a = new ModelRenderer(this, 128, 65);
        this.ClothA03a.setRotationPoint(-0.1F, -0.1F, -2.2F);
        this.ClothA03a.addBox(-2.5F, 0.0F, 0.0F, 5, 9, 6, 0.0F);
        this.EquipB02 = new ModelRenderer(this, 226, 0);
        this.EquipB02.mirror = true;
        this.EquipB02.setRotationPoint(0.0F, 0.0F, 8.9F);
        this.EquipB02.addBox(-4.5F, 0.0F, 0.0F, 9, 8, 5, 0.0F);
        this.EquipB06b = new ModelRenderer(this, 0, 0);
        this.EquipB06b.setRotationPoint(11.4F, 4.0F, 0.5F);
        this.EquipB06b.addBox(0.0F, 0.0F, 0.0F, 3, 6, 11, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.EquipB07c2 = new ModelRenderer(this, 0, 0);
        this.EquipB07c2.setRotationPoint(12.0F, 0.0F, 0.5F);
        this.EquipB07c2.addBox(0.0F, -2.0F, -1.0F, 7, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07c2, 0.0F, 1.0471975511965976F, 0.0F);
        this.EquipCL1a1_5 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_5.setRotationPoint(-2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_5.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_5, -0.20943951023931953F, 0.0F, 0.0F);
        this.EquipB07a1 = new ModelRenderer(this, 153, 49);
        this.EquipB07a1.setRotationPoint(-1.2F, 8.7F, 12.0F);
        this.EquipB07a1.addBox(0.0F, -2.0F, -0.5F, 12, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07a1, -0.20943951023931953F, -0.08726646259971647F, 0.12217304763960307F);
        this.EquipCL1a1_6 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_6.setRotationPoint(2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_6.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_6, -0.20943951023931953F, 0.0F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 90, 103);
        this.HairL01.setRotationPoint(6.5F, 1.5F, -4.5F);
        this.HairL01.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.19198621771937624F, -0.17453292519943295F, -0.08726646259971647F);
        this.ClothA01_1 = new ModelRenderer(this, 128, 109);
        this.ClothA01_1.setRotationPoint(-0.5F, 5.1F, 0.0F);
        this.ClothA01_1.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.EquipB04_1 = new ModelRenderer(this, 0, 0);
        this.EquipB04_1.setRotationPoint(-5.0F, -2.0F, -0.5F);
        this.EquipB04_1.addBox(-5.0F, 0.0F, 0.0F, 5, 4, 12, 0.0F);
        this.EquipB06a_1 = new ModelRenderer(this, 0, 0);
        this.EquipB06a_1.setRotationPoint(-2.5F, 3.9F, 0.5F);
        this.EquipB06a_1.addBox(-9.0F, 0.0F, 0.0F, 9, 4, 11, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 88);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipB04 = new ModelRenderer(this, 0, 0);
        this.EquipB04.setRotationPoint(5.0F, -2.0F, -0.5F);
        this.EquipB04.addBox(0.0F, 0.0F, 0.0F, 5, 4, 12, 0.0F);
        this.EquipB07d2 = new ModelRenderer(this, 0, 0);
        this.EquipB07d2.setRotationPoint(12.0F, 0.0F, 0.5F);
        this.EquipB07d2.addBox(0.0F, -2.0F, -1.0F, 6, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07d2, 0.0F, 1.0471975511965976F, 0.0F);
        this.EquipCL1Base02_1 = new ModelRenderer(this, 0, 0);
        this.EquipCL1Base02_1.setRotationPoint(0.0F, 0.3F, -2.8F);
        this.EquipCL1Base02_1.addBox(-4.5F, -4.0F, -2.0F, 9, 4, 4, 0.0F);
        this.setRotateAngle(EquipCL1Base02_1, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipB07c1_1 = new ModelRenderer(this, 153, 49);
        this.EquipB07c1_1.mirror = true;
        this.EquipB07c1_1.setRotationPoint(1.0F, 2.4F, 12.4F);
        this.EquipB07c1_1.addBox(-12.0F, -2.0F, -0.5F, 12, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07c1_1, 0.0F, 0.08726646259971647F, 0.0F);
        this.EquipB01b05 = new ModelRenderer(this, 0, 0);
        this.EquipB01b05.setRotationPoint(0.0F, -33.4F, 0.3F);
        this.EquipB01b05.addBox(-0.6F, 0.0F, -0.5F, 1, 18, 1, 0.0F);
        this.EquipB01b06 = new ModelRenderer(this, 0, 0);
        this.EquipB01b06.setRotationPoint(0.0F, -29F, -0.1F);
        this.EquipB01b06.addBox(-5.5F, 0F, 0F, 11, 1, 1, 0.0F);
        this.HairR01 = new ModelRenderer(this, 90, 103);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-6.5F, 1.5F, -4.5F);
        this.HairR01.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.19198621771937624F, 0.17453292519943295F, 0.08726646259971647F);
        this.HairC01b = new ModelRenderer(this, 40, 0);
        this.HairC01b.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairC01b.addBox(-2.0F, 0.0F, 0.0F, 2, 4, 0, 0.0F);
        this.setRotateAngle(HairC01b, 1.3962634015954636F, 0.13962634015954636F, 0.0F);
        this.Ahoke04 = new ModelRenderer(this, 44, 0);
        this.Ahoke04.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.Ahoke04.addBox(-1.5F, 0.0F, 0.0F, 3, 4, 0, 0.0F);
        this.setRotateAngle(Ahoke04, 0.9599310885968813F, 0.0F, 0.0F);
        this.HairC01 = new ModelRenderer(this, 40, 0);
        this.HairC01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairC01.addBox(0.0F, 0.0F, 0.0F, 2, 4, 0, 0.0F);
        this.setRotateAngle(HairC01, 1.3962634015954636F, -0.13962634015954636F, 0.0F);
        this.EquipB07b2 = new ModelRenderer(this, 0, 0);
        this.EquipB07b2.setRotationPoint(12.0F, 0.0F, 0.5F);
        this.EquipB07b2.addBox(0.0F, -2.0F, -1.0F, 6, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07b2, 0.0F, 1.0471975511965976F, 0.0F);
        this.SkirtB01 = new ModelRenderer(this, 128, 36);
        this.SkirtB01.setRotationPoint(0.0F, 0.5F, -1.9F);
        this.SkirtB01.addBox(-8.0F, 0.0F, -4.5F, 16, 2, 9, 0.0F);
        this.setRotateAngle(SkirtB01, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipB07b1_1 = new ModelRenderer(this, 153, 49);
        this.EquipB07b1_1.mirror = true;
        this.EquipB07b1_1.setRotationPoint(1.0F, 5.6F, 12.4F);
        this.EquipB07b1_1.addBox(-12.0F, -2.0F, -0.5F, 12, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07b1_1, 0.0F, 0.08726646259971647F, -0.05235987755982988F);
        this.Cloth03b = new ModelRenderer(this, 161, 80);
        this.Cloth03b.mirror = true;
        this.Cloth03b.setRotationPoint(-0.6F, -0.8F, -0.1F);
        this.Cloth03b.addBox(-1.0F, 0.0F, 0.0F, 2, 5, 5, 0.0F);
        this.setRotateAngle(Cloth03b, 0.0F, 0.0F, 0.08726646259971647F);
        this.LegRight02 = new ModelRenderer(this, 0, 47);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.EquipCL1a2_2 = new ModelRenderer(this, 151, 67);
        this.EquipCL1a2_2.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipCL1a2_2.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotateAngle(EquipCL1a2_2, -1.5707963267948966F, 0.0F, 0.0F);
        this.ClothA04 = new ModelRenderer(this, 128, 81);
        this.ClothA04.setRotationPoint(0.0F, 0.9F, 0.8F);
        this.ClothA04.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.ClothA01 = new ModelRenderer(this, 128, 109);
        this.ClothA01.setRotationPoint(0.5F, 5.1F, 0.0F);
        this.ClothA01.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.EquipCL1a1_1 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_1.setRotationPoint(-2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_1.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_1, -0.20943951023931953F, 0.0F, 0.0F);
        this.EquipCL1a1 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1.setRotationPoint(2.0F, -2.3F, -2.5F);
        this.EquipCL1a1.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1, -0.20943951023931953F, 0.0F, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 128, 0);
        this.Skirt01.setRotationPoint(0.0F, 3.5F, 1.5F);
        this.Skirt01.addBox(-8.5F, 0.0F, -8.5F, 17, 5, 11, 0.0F);
        this.setRotateAngle(Skirt01, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipB06f_1 = new ModelRenderer(this, 0, 0);
        this.EquipB06f_1.setRotationPoint(-27.0F, 4.0F, 0.5F);
        this.EquipB06f_1.addBox(0.0F, 0.0F, 0.0F, 2, 8, 11, 0.0F);
        this.Ahoke00 = new ModelRenderer(this, 100, 28);
        this.Ahoke00.setRotationPoint(-0.6F, -13F, -4F);
        this.Ahoke00.addBox(0F, -9F, 0F, 0, 12, 12, 0F);
        this.setRotateAngle(Ahoke00, 0.6632F, 0.5236F, 0F);
        this.Ahoke01 = new ModelRenderer(this, 44, 0);
        this.Ahoke01.setRotationPoint(0.0F, -15.0F, -5.0F);
        this.Ahoke01.addBox(-1.5F, 0.0F, 0.0F, 3, 5, 0, 0.0F);
        this.setRotateAngle(Ahoke01, 2.705260340591211F, -2.8797932657906435F, 0.0F);
        this.EquipB06c = new ModelRenderer(this, 0, 0);
        this.EquipB06c.setRotationPoint(14.3F, 4.0F, 0.5F);
        this.EquipB06c.addBox(0.0F, 0.0F, 0.0F, 3, 8, 11, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 71);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.17453292519943295F, 0.0F, -0.3141592653589793F);
        this.EquipB01b04 = new ModelRenderer(this, 0, 0);
        this.EquipB01b04.setRotationPoint(0.0F, -15.5F, 0.5F);
        this.EquipB01b04.addBox(-1.5F, 0.0F, -1.5F, 3, 1, 3, 0.0F);
        this.Neck = new ModelRenderer(this, 24, 63);
        this.Neck.setRotationPoint(0.0F, -9.6F, 0.5F);
        this.Neck.addBox(-2.5F, -3.0F, -2.9F, 5, 3, 5, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.ClothA05 = new ModelRenderer(this, 128, 96);
        this.ClothA05.setRotationPoint(0.0F, 1.9F, 0.8F);
        this.ClothA05.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 6, 0.0F);
        this.HairCBaseB = new ModelRenderer(this, 0, 0);
        this.HairCBaseB.setRotationPoint(-6.0F, 1.0F, -1.6F);
        this.HairCBaseB.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(HairCBaseB, 0.0F, 0.0F, 0.3141592653589793F);
        this.EquipB07d2_1 = new ModelRenderer(this, 0, 0);
        this.EquipB07d2_1.setRotationPoint(-12.0F, 0.0F, 0.5F);
        this.EquipB07d2_1.addBox(-6.0F, -2.0F, -1.0F, 6, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07d2_1, 0.0F, -1.0471975511965976F, 0.0F);
        this.EquipB03 = new ModelRenderer(this, 185, 20);
        this.EquipB03.mirror = true;
        this.EquipB03.setRotationPoint(0.0F, 0.0F, 4.9F);
        this.EquipB03.addBox(-3.0F, 0.0F, 0.0F, 6, 4, 6, 0.0F);
        this.EquipB01b01c = new ModelRenderer(this, 0, 0);
        this.EquipB01b01c.setRotationPoint(-1.0F, -7.9F, 0.0F);
        this.EquipB01b01c.addBox(-0.5F, 0.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(EquipB01b01c, -0.08726646259971647F, 0.0F, 0.12217304763960307F);
        this.Cloth02a1 = new ModelRenderer(this, 59, 0);
        this.Cloth02a1.setRotationPoint(4.0F, 1.8F, -4.9F);
        this.Cloth02a1.addBox(-1.5F, 0.0F, 0.0F, 3, 5, 0, 0.0F);
        this.setRotateAngle(Cloth02a1, -0.5585053606381855F, 0.0F, -0.06981317007977318F);
        this.EquipB07c2_1 = new ModelRenderer(this, 0, 0);
        this.EquipB07c2_1.setRotationPoint(-12.0F, 0.0F, 0.5F);
        this.EquipB07c2_1.addBox(-7.0F, -2.0F, -1.0F, 7, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07c2_1, 0.0F, -1.0471975511965976F, 0.0F);
        this.Cloth02a3 = new ModelRenderer(this, 59, 0);
        this.Cloth02a3.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Cloth02a3.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth02a3, 0.0F, 0.0F, 0.05235987755982988F);
        this.EquipCL1a1_3 = new ModelRenderer(this, 19, 29);
        this.EquipCL1a1_3.setRotationPoint(-2.0F, -2.3F, -2.5F);
        this.EquipCL1a1_3.addBox(-1.5F, -1.5F, -5.0F, 3, 3, 5, 0.0F);
        this.setRotateAngle(EquipCL1a1_3, -0.16984426090695579F, 0.0F, 0.0F);
        this.Cloth02c1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c1.setRotationPoint(2.6F, 1.9F, 4.4F);
        this.Cloth02c1.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 0, 0.0F);
        this.setRotateAngle(Cloth02c1, 0.6283185307179586F, 0.0F, -0.08726646259971647F);
        this.EquipB01b03 = new ModelRenderer(this, 0, 0);
        this.EquipB01b03.setRotationPoint(0.0F, -14.5F, 0.5F);
        this.EquipB01b03.addBox(-1.0F, 0.0F, -1.0F, 2, 6, 2, 0.0F);
        this.EquipB06c_1 = new ModelRenderer(this, 0, 0);
        this.EquipB06c_1.setRotationPoint(-14.3F, 4.0F, 0.5F);
        this.EquipB06c_1.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 11, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0F);
        this.Cloth02c1_1 = new ModelRenderer(this, 58, 7);
        this.Cloth02c1_1.setRotationPoint(-2.6F, 1.9F, 4.4F);
        this.Cloth02c1_1.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 0, 0.0F);
        this.setRotateAngle(Cloth02c1_1, 0.6283185307179586F, 0.0F, 0.08726646259971647F);
        this.Ahoke02 = new ModelRenderer(this, 44, 0);
        this.Ahoke02.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.Ahoke02.addBox(-1.5F, 0.0F, 0.0F, 3, 7, 0, 0.0F);
        this.setRotateAngle(Ahoke02, 1.2217304763960306F, 0.0F, 0.0F);
        this.Cloth02b3 = new ModelRenderer(this, 59, 0);
        this.Cloth02b3.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Cloth02b3.addBox(-1.5F, 0.0F, 0.0F, 3, 6, 0, 0.0F);
        this.setRotateAngle(Cloth02b3, 0.0F, 0.0F, -0.05235987755982988F);
        this.EquipB06f = new ModelRenderer(this, 0, 0);
        this.EquipB06f.setRotationPoint(25.0F, 4.0F, 0.5F);
        this.EquipB06f.addBox(0.0F, 0.0F, 0.0F, 2, 8, 11, 0.0F);
        this.HairC05b = new ModelRenderer(this, 40, 0);
        this.HairC05b.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.HairC05b.addBox(-2.0F, 0.0F, 0.0F, 2, 5, 0, 0.0F);
        this.setRotateAngle(HairC05b, 1.7453292519943295F, 0.0F, 0.0F);
        this.EquipB07a2 = new ModelRenderer(this, 0, 0);
        this.EquipB07a2.setRotationPoint(12.0F, 0.0F, 0.5F);
        this.EquipB07a2.addBox(0.0F, -2.0F, -1.0F, 5, 4, 1, 0.0F);
        this.setRotateAngle(EquipB07a2, 0.0F, 1.0733774899765127F, 0.0F);
        this.ClothA04a = new ModelRenderer(this, 128, 81);
        this.ClothA04a.setRotationPoint(0.0F, 0.9F, 0.8F);
        this.ClothA04a.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 6, 0.0F);
        this.Hair01 = new ModelRenderer(this, 80, 0);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 13, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.2617993877991494F, 0.0F, 0.0F);
        this.HairS02 = new ModelRenderer(this, 110, 22);
        this.HairS02.setRotationPoint(8.8F, 3.1F, 3.3F);
        this.HairS02.addBox(-1.5F, -3.0F, -3.0F, 3, 6, 6, 0.0F);
        this.setRotateAngle(HairS02, 0.0F, -0.05235987755982988F, 0.0F);
        this.HairC01.addChild(this.HairC02);
        this.EquipB04_1.addChild(this.EquipB06b_1);
        this.EquipB01.addChild(this.EquipB01b00);
        this.EquipCL1a1_3.addChild(this.EquipCL1a2_3);
        this.EquipB04.addChild(this.EquipB05);
        this.Butt.addChild(this.LegRight01);
        this.EquipCL1a1_7.addChild(this.EquipCL1a2_7);
        this.EquipB07a1_1.addChild(this.EquipB07a2_1);
        this.Head.addChild(this.Hair);
        this.BoobR.addChild(this.ClothB01);
        this.EquipB00c_1.addChild(this.EquipB00d_1);
        this.EquipB05.addChild(this.EquipCL1Base01);
        this.EquipB04_1.addChild(this.EquipB05_2);
        this.Cloth02c3.addChild(this.Cloth02c4);
        this.ArmRight02.addChild(this.ClothA02a);
        this.EquipB00a_1.addChild(this.EquipB00b_1);
        this.EquipB01b00.addChild(this.EquipB01b01b);
        this.Ahoke02.addChild(this.Ahoke03);
        this.Cloth02c1.addChild(this.Cloth02c2);
        this.EquipB04_1.addChild(this.EquipB06e_1);
        this.Butt.addChild(this.LegLeft01);
        this.EquipCL1Base01.addChild(this.EquipCL1Base02);
        this.BodyMain.addChild(this.BoobL);
        this.BodyMain.addChild(this.ArmRight01);
        this.EquipB01b00.addChild(this.EquipB01b02);
        this.HairC03.addChild(this.HairC04);
        this.Hair01.addChild(this.Hair02);
        this.EquipB06d.addChild(this.EquipB05_1);
        this.EquipB06d.addChild(this.EquipB07c1);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipBase.addChild(this.EquipB01);
        this.EquipBase.addChild(this.EquipB00a);
        this.EquipB07d1.addChild(this.EquipB07d3);
        this.EquipB00c.addChild(this.EquipB00d);
        this.Skirt01.addChild(this.Skirt02);
        this.Cloth02c1_1.addChild(this.Cloth02c2_1);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipCL1Base01_2.addChild(this.EquipCL1a1_4);
        this.Cloth01a.addChild(this.Cloth01c2);
        this.SkirtB01.addChild(this.Cloth02b1);
        this.Cloth02a1.addChild(this.Cloth02a2);
        this.EquipB04.addChild(this.EquipB06e);
        this.Cloth02b1.addChild(this.Cloth02b2);
        this.EquipB06d_1.addChild(this.EquipB05_3);
        this.EquipB01b00.addChild(this.EquipB01b01a);
        this.Hair.addChild(this.HairU01);
        this.EquipB05_3.addChild(this.EquipCL1Base01_3);
        this.Cloth01a.addChild(this.Cloth01b2);
        this.EquipB00a.addChild(this.EquipB00b);
        this.EquipHead01a.addChild(this.EquipHead03a);
        this.EquipB05_2.addChild(this.EquipCL1Base01_2);
        this.EquipB07d1_1.addChild(this.EquipB07d3_1);
        this.EquipCL1a1_1.addChild(this.EquipCL1a2_1);
        this.EquipCL1Base01_3.addChild(this.EquipCL1Base02_3);
        this.HairC02b.addChild(this.HairC03b);
        this.Cloth01a.addChild(this.Cloth01c);
        this.ArmLeft02.addChild(this.ClothA02);
        this.EquipB00b.addChild(this.EquipB00c);
        this.EquipB04.addChild(this.EquipB06d);
        this.BoobL.addChild(this.Cloth03b_1);
        this.EquipB02.addChild(this.EquipB02a);
        this.Head.addChild(this.EquipHeadBase);
        this.Hair.addChild(this.HairS01);
        this.HairL01.addChild(this.HairL02);
        this.EquipB06d.addChild(this.EquipB07d1);
        this.BodyMain.addChild(this.Cloth03a1);
        this.SkirtB01.addChild(this.Cloth01a);
        this.EquipBase.addChild(this.EquipB00a_1);
        this.Hair.addChild(this.HairCBase);
        this.Cloth02c3_1.addChild(this.Cloth02c4_1);
        this.EquipB01.addChild(this.EquipB01a);
        this.Cloth02c2_1.addChild(this.Cloth02c3_1);
        this.BodyMain.addChild(this.EquipBase);
        this.EquipHeadBase.addChild(this.EquipHead01a);
        this.BodyMain.addChild(this.Cloth03a2);
        this.HairC04.addChild(this.HairC05);
        this.EquipB04_1.addChild(this.EquipB06d_1);
        this.EquipB05_1.addChild(this.EquipCL1Base01_1);
        this.EquipHead01.addChild(this.EquipHead03);
        this.ClothA02.addChild(this.ClothA03);
        this.EquipB06d_1.addChild(this.EquipB07a1_1);
        this.EquipHead01a.addChild(this.EquipHead02a);
        this.EquipCL1a1_6.addChild(this.EquipCL1a2_6);
        this.EquipB07b1_1.addChild(this.EquipB07b2_1);
        this.BodyMain.addChild(this.BoobR);
        this.HairR01.addChild(this.HairR02);
        this.EquipCL1a1_4.addChild(this.EquipCL1a2_4);
        this.EquipB01a.addChild(this.EquipB01c);
        this.EquipB06d_1.addChild(this.EquipB07d1_1);
        this.EquipHeadBase.addChild(this.EquipHead01);
        this.EquipCL1Base01_1.addChild(this.EquipCL1a1_2);
        this.Cloth01a.addChild(this.Cloth01b);
        this.EquipCL1Base01_3.addChild(this.EquipCL1a1_7);
        this.HairC02.addChild(this.HairC03);
        this.EquipCL1a1_5.addChild(this.EquipCL1a2_5);
        this.EquipHead01.addChild(this.EquipHead02);
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipCL1Base01_2.addChild(this.EquipCL1Base02_2);
        this.EquipCL1a1.addChild(this.EquipCL1a2);
        this.EquipB04.addChild(this.EquipB06a);
        this.ClothA04a.addChild(this.ClothA05a);
        this.HairC01b.addChild(this.HairC02b);
        this.Cloth02c2.addChild(this.Cloth02c3);
        this.HairC03b.addChild(this.HairC04b);
        this.EquipB00b_1.addChild(this.EquipB00c_1);
        this.EquipB06d.addChild(this.EquipB07b1);
        this.ClothA02a.addChild(this.ClothA03a);
        this.EquipB01.addChild(this.EquipB02);
        this.EquipB04.addChild(this.EquipB06b);
        this.Head.addChild(this.HairMain);
        this.EquipB07c1.addChild(this.EquipB07c2);
        this.EquipCL1Base01_2.addChild(this.EquipCL1a1_5);
        this.EquipB06d.addChild(this.EquipB07a1);
        this.EquipCL1Base01_3.addChild(this.EquipCL1a1_6);
        this.Hair.addChild(this.HairL01);
        this.ArmRight01.addChild(this.ClothA01_1);
        this.EquipB01.addChild(this.EquipB04_1);
        this.EquipB04_1.addChild(this.EquipB06a_1);
        this.BodyMain.addChild(this.Butt);
        this.EquipB01.addChild(this.EquipB04);
        this.EquipB07d1.addChild(this.EquipB07d2);
        this.EquipCL1Base01_1.addChild(this.EquipCL1Base02_1);
        this.EquipB06d_1.addChild(this.EquipB07c1_1);
        this.EquipB01b00.addChild(this.EquipB01b05);
        this.EquipB01b00.addChild(this.EquipB01b06);
        this.Hair.addChild(this.HairR01);
        this.HairCBaseB.addChild(this.HairC01b);
        this.Ahoke03.addChild(this.Ahoke04);
        this.HairCBase.addChild(this.HairC01);
        this.EquipB07b1.addChild(this.EquipB07b2);
        this.Butt.addChild(this.SkirtB01);
        this.EquipB06d_1.addChild(this.EquipB07b1_1);
        this.BoobR.addChild(this.Cloth03b);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipCL1a1_2.addChild(this.EquipCL1a2_2);
        this.ClothA03.addChild(this.ClothA04);
        this.ArmLeft01.addChild(this.ClothA01);
        this.Neck.addChild(this.Head);
        this.EquipCL1Base01.addChild(this.EquipCL1a1_1);
        this.EquipCL1Base01.addChild(this.EquipCL1a1);
        this.Butt.addChild(this.Skirt01);
        this.EquipB04_1.addChild(this.EquipB06f_1);
        this.Head.addChild(this.Ahoke00);
        this.Head.addChild(this.Ahoke01);
        this.EquipB04.addChild(this.EquipB06c);
        this.BodyMain.addChild(this.ArmLeft01);
        this.EquipB01b00.addChild(this.EquipB01b04);
        this.BodyMain.addChild(this.Neck);
        this.ClothA04.addChild(this.ClothA05);
        this.Hair.addChild(this.HairCBaseB);
        this.EquipB07d1_1.addChild(this.EquipB07d2_1);
        this.EquipB02.addChild(this.EquipB03);
        this.EquipB01b00.addChild(this.EquipB01b01c);
        this.SkirtB01.addChild(this.Cloth02a1);
        this.EquipB07c1_1.addChild(this.EquipB07c2_1);
        this.Cloth02a2.addChild(this.Cloth02a3);
        this.EquipCL1Base01_1.addChild(this.EquipCL1a1_3);
        this.SkirtB01.addChild(this.Cloth02c1);
        this.EquipB01b00.addChild(this.EquipB01b03);
        this.EquipB04_1.addChild(this.EquipB06c_1);
        this.SkirtB01.addChild(this.Cloth02c1_1);
        this.Ahoke01.addChild(this.Ahoke02);
        this.Cloth02b2.addChild(this.Cloth02b3);
        this.EquipB04.addChild(this.EquipB06f);
        this.HairC04b.addChild(this.HairC05b);
        this.EquipB07a1.addChild(this.EquipB07a2);
        this.ClothA03a.addChild(this.ClothA04a);
        this.HairMain.addChild(this.Hair01);
        this.Hair.addChild(this.HairS02);
        
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
    	
    	//TODO
    	scale = 0.45F;
    	offsetY = 1.79F;
    	
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
		
		flag = !EmotionHelper.checkModelState(2, state);			//hair
		this.HairS01.isHidden = flag;
		this.HairS02.isHidden = flag;
		this.HairCBase.isHidden = flag;
		this.HairCBaseB.isHidden = flag;
		
		flag = EmotionHelper.checkModelState(3, state);				//ahoke
		this.Ahoke00.isHidden = !flag;
		this.Ahoke01.isHidden = flag;
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
    		GlStateManager.translate(0F, 1.42F, 0F);
		break;
    	case 2:
    		GlStateManager.translate(0F, 1.29F, 0F);
		break;
    	case 1:
        	GlStateManager.translate(0F, 1.05F, 0F);
		break;
    	default:
    		GlStateManager.translate(0F, 0.7F, 0F);
		break;
    	}
		
		this.setFaceHungry(ent);

		//body
    	this.Head.rotateAngleX = 0F;
    	this.Head.rotateAngleY = 0F;
    	this.Head.rotateAngleZ = 0F;
    	this.BodyMain.rotateAngleX = 1.4F;
    	this.Butt.rotateAngleX = 0.21F;
    	this.Butt.offsetY = 0F;
	  	this.Butt.offsetZ = 0F;
    	//boob
    	this.BoobL.rotateAngleX = -0.8F;
  	    this.BoobR.rotateAngleX = -0.8F;
    	this.ClothB01.rotateAngleX = 0.96F;
    	//cloth
    	this.Skirt01.rotateAngleX = -0.087F;
	  	this.Skirt02.rotateAngleX = -0.087F;
    	this.Skirt01.offsetY = 0F;
	  	this.Skirt01.offsetZ = 0F;
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
		this.ClothA03.offsetY = 0F;
	  	this.ClothA04.offsetY = 0F;
	  	this.ClothA05.offsetY = 0F;
	  	this.ClothA03.offsetZ = 0F;
	  	this.ClothA04.offsetZ = 0F;
	  	this.ClothA05.offsetZ = 0F;
	  	this.ClothA03a.offsetY = 0F;
	  	this.ClothA04a.offsetY = 0F;
	  	this.ClothA05a.offsetY = 0F;
	  	this.ClothA03a.offsetZ = 0F;
	  	this.ClothA04a.offsetZ = 0F;
	  	this.ClothA05a.offsetZ = 0F;
    	//hair
    	this.Ahoke00.rotateAngleX = 0.6632F;
    	this.Ahoke00.rotateAngleY = 0.523F;
    	this.Ahoke00.rotateAngleZ = 0F;
	  	this.Ahoke01.rotateAngleX = 2.7F;
  	    this.Ahoke02.rotateAngleX = 1.22F;
  	    this.Ahoke03.rotateAngleX = 1.48F;
  	    this.Ahoke04.rotateAngleX = 0.96F;
    	this.Hair01.rotateAngleX = 0.1F;
    	this.Hair01.rotateAngleY = 0F;
    	this.Hair01.rotateAngleZ = 0F;
    	this.Hair02.rotateAngleX = -0.3F;
    	this.Hair02.rotateAngleY = 0F;
    	this.Hair02.rotateAngleZ = 0F;
    	//arm
    	this.ArmLeft01.rotateAngleX = -2.8F;
    	this.ArmLeft01.rotateAngleY = 0.1F;
    	this.ArmLeft01.rotateAngleZ = 0.84F;
    	this.ArmLeft02.rotateAngleX = 0F;
    	this.ArmLeft02.rotateAngleZ = 1.0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = 0F;
    	this.ArmRight01.rotateAngleX = 0F;
    	this.ArmRight01.rotateAngleY = 0F;
    	this.ArmRight01.rotateAngleZ = 0.2F;
	    this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
		this.ArmRight02.offsetZ = 0F;
    	//leg
    	this.LegLeft01.rotateAngleX = -0.12F;
    	this.LegLeft01.rotateAngleY = 0F;
    	this.LegLeft01.rotateAngleZ = -0.05F;
    	this.LegLeft01.offsetY = 0F;
		this.LegLeft01.offsetZ = 0F;
    	this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
    	this.LegRight01.rotateAngleX = -0.12F;
		this.LegRight01.rotateAngleY = 0F;
    	this.LegRight01.rotateAngleZ = 0.26F;
    	this.LegRight01.offsetY = 0F;
		this.LegRight01.offsetZ = 0F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = -0.4F;
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
  		float headX = 0F;
  		float headZ = 0F;
  		float addHL1 = 0F;
  		float addHR1 = 0F;
  		float addHL2 = 0F;
  		float addHR2 = 0F;
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
	  	this.Ahoke00.rotateAngleX = angleX2 * 0.05F + 0.66F;
	  	this.Ahoke00.rotateAngleY = -angleX * 0.15F + 0.53F;
	  	this.Ahoke01.rotateAngleX = -angleX1 * 0.09F + 2.7F;
  	    this.Ahoke02.rotateAngleX = angleX2 * 0.15F + 1.22F;
  	    this.Ahoke03.rotateAngleX = -angleX3 * 0.10F + 1.48F;
  	    this.Ahoke04.rotateAngleX = -angleX4 * 0.10F + 0.96F;
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
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.26F + headX;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.17F + headX;
	  	this.Hair02.rotateAngleZ = 0F;
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
		this.EquipCL1Base01.rotateAngleY = this.Head.rotateAngleY * 0.5F - 0.9F;
		this.EquipCL1Base01_1.rotateAngleY = this.Head.rotateAngleY * 0.75F;
		this.EquipCL1Base01_2.rotateAngleY = this.Head.rotateAngleY * 0.5F + 0.9F;
		this.EquipCL1Base01_3.rotateAngleY = this.Head.rotateAngleY * 0.75F;
		
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
	    	this.Hair01.rotateAngleX += 0.2F;
	    	this.Hair02.rotateAngleX += 0.2F;
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
			//hair
			this.Hair01.rotateAngleX = this.Hair01.rotateAngleX * 0.5F + 0.4F;
			this.Hair02.rotateAngleX = this.Hair02.rotateAngleX * 0.75F + 0.25F;
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
		    	  	//hair
		    	  	this.Hair01.rotateAngleX = 0.21F + headX;
		    	  	this.Hair02.rotateAngleX = -0.28F + headX;
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
		    		GlStateManager.translate(0F, 0.63F, 0F);
				break;
		    	case 2:
		    		GlStateManager.translate(0F, 0.56F, 0F);
				break;
		    	case 1:
		        	GlStateManager.translate(0F, 0.46F, 0F);
				break;
		    	default:
		    		GlStateManager.translate(0F, 0.31F, 0F);
				break;
		    	}
		    	
		    	//Body
		    	this.Head.rotateAngleX += 0.14F;
			  	this.BodyMain.rotateAngleX = -0.4363F;
			  	//cloth
			  	this.Skirt01.rotateAngleX = -0.35F;
			  	this.Skirt02.rotateAngleX = -0.19F;
			  	this.SkirtB01.rotateAngleX = -0.12F;
			  	this.Cloth02a2.rotateAngleX += 0.32F;
			  	this.Cloth02a3.rotateAngleX += 0.4F;
			  	this.Cloth02b2.rotateAngleX += 0.32F;
			  	this.Cloth02b3.rotateAngleX += 0.4F;
			  	this.Cloth02c1.rotateAngleX += 0.45F;
			  	this.Cloth02c2.rotateAngleX += 0.1F;
			  	this.Cloth02c1_1.rotateAngleX += 0.45F;
			  	this.Cloth02c2_1.rotateAngleX += 0.1F;
			  	this.ClothA03.rotateAngleY = 1.29F;
			  	this.ClothA03a.rotateAngleY = -1.13F;
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
				this.LegLeft02.rotateAngleX = 1.0472F;
				this.LegRight01.rotateAngleY = -0.35F;
				this.LegRight01.rotateAngleZ = -0.2618F;
				this.LegRight02.rotateAngleX = 0.9F;
				//hair
				this.Hair01.rotateAngleX += 0.12F;
				this.Hair02.rotateAngleX += 0.15F;
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
  		  	this.ArmLeft01.rotateAngleX = -1.57F;
  		  	this.ArmLeft01.rotateAngleY = -0.26F;
  		  	this.ArmLeft01.rotateAngleZ = 0F;
  			this.ArmRight01.rotateAngleX = 0F;
  			this.ArmRight01.rotateAngleZ = 0.87F;
  			this.ArmRight02.rotateAngleZ = -1.57F;
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
  		  	this.ArmLeft01.rotateAngleX = -1.57F;
  		  	this.ArmLeft01.rotateAngleY = -0.26F;
  		  	this.ArmLeft01.rotateAngleZ = 0F;
  			this.ArmRight01.rotateAngleX = 0F;
  			this.ArmRight01.rotateAngleZ = 0.87F;
  			this.ArmRight02.rotateAngleZ = -1.57F;
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
	  	this.ClothA03.offsetY = HandLc * 0.1F;
	  	this.ClothA04.offsetY = HandLc * 0.2F;
	  	this.ClothA05.offsetY = HandLc * 0.25F;
	  	this.ClothA03.offsetZ = HandLs * -0.32F;
	  	this.ClothA04.offsetZ = HandLs * -0.32F;
	  	this.ClothA05.offsetZ = HandLs * -0.32F;
	  	this.ClothA03a.offsetY = HandRc * 0.1F;
	  	this.ClothA04a.offsetY = HandRc * 0.2F;
	  	this.ClothA05a.offsetY = HandRc * 0.25F;
	  	this.ClothA03a.offsetZ = HandRs * -0.32F;
	  	this.ClothA04a.offsetZ = HandRs * -0.32F;
	  	this.ClothA05a.offsetZ = HandRs * -0.32F;
	  	
	  	//移動頭髮避免穿過身體
	    headX = this.Head.rotateAngleX * -0.5F;
		this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.19F + addHL1;
	  	this.HairL02.rotateAngleX = -angleX1 * 0.04F + headX + 0.17F + addHL2;
	  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.19F + addHR1;
	  	this.HairR02.rotateAngleX = -angleX1 * 0.04F + headX + 0.17F + addHR2;
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	this.HairL01.rotateAngleZ = headZ - 0.087F;
	  	this.HairL02.rotateAngleZ = headZ + 0.087F;
	  	this.HairR01.rotateAngleZ = headZ + 0.087F;
	  	this.HairR02.rotateAngleZ = headZ - 0.052F;
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
    
    
}