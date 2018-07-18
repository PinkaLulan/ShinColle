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
 * ModelCruiserTatsuta - PinkaLulan 2017/1/14
 * Created using Tabula 5.1.0
 */
public class ModelCruiserTatsuta extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft01;
    public ModelRenderer Cloth01;
    public ModelRenderer Equip00;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer CirBase;
    public ModelRenderer HairU01;
    public ModelRenderer Ahoke;
    public ModelRenderer Hair01;
    public ModelRenderer Cir00;
    public ModelRenderer Cir01;
    public ModelRenderer Cir02;
    public ModelRenderer Cir03;
    public ModelRenderer Cir04;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft02;
    public ModelRenderer Skirt02;
    public ModelRenderer LegRight02;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmLeft02;
    public ModelRenderer EquipSL00;
    public ModelRenderer EquipSL01;
    public ModelRenderer EquipSL04;
    public ModelRenderer EquipSL02;
    public ModelRenderer EquipSL03a;
    public ModelRenderer EquipSL03b;
    public ModelRenderer EquipSL03c;
    public ModelRenderer EquipSL05;
    public ModelRenderer Equip01a;
    public ModelRenderer Equip01b;
    public ModelRenderer Equip01c;
    public ModelRenderer Equip02a;
    public ModelRenderer Equip01d;
    public ModelRenderer Equip03L;
    public ModelRenderer Equip03R;
    public ModelRenderer EquipCL01;
    public ModelRenderer EquipCL02;
    public ModelRenderer EquipCL03a;
    public ModelRenderer EquipCL03b;
    public ModelRenderer EquipCL03c;
    public ModelRenderer EquipCR01;
    public ModelRenderer EquipCR02;
    public ModelRenderer EquipCR03a;
    public ModelRenderer EquipCR03b;
    public ModelRenderer EquipCR03c;
    public ModelRenderer Equip02b;
    public ModelRenderer Equip02c;
    public ModelRenderer Equip02d;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowBodyMain2;
    public ModelRenderer GlowNeck2;
    public ModelRenderer GlowHead2;
    public ModelRenderer GlowEquip00;
    public ModelRenderer GlowEquip01a;
    public ModelRenderer GlowEquip02a;

    
    public ModelCruiserTatsuta()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.offsetItem = new float[] {0.06F, 1.07F, -0.06F};
        this.offsetBlock = new float[] {0.06F, 1.07F, -0.06F};
        
        this.setDefaultFaceModel();
        
        this.EquipCL03b = new ModelRenderer(this, 0, 27);
        this.EquipCL03b.setRotationPoint(1.9F, -0.5F, 0.5F);
        this.EquipCL03b.addBox(-1.0F, -7.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(EquipCL03b, 0.0F, 0.3490658503988659F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 84);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, -0.17453292519943295F, 0.0F, 0.2617993877991494F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-2.1F, -8.0F, -3.6F);
        this.BoobR.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, 0.10471975511965977F, 0.13962634015954636F);
        this.Equip01a = new ModelRenderer(this, 26, 0);
        this.Equip01a.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.Equip01a.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 5, 0.0F);
        this.BoobL = new ModelRenderer(this, 34, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(2.0F, -8.0F, -3.7F);
        this.BoobL.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, -0.09250245035569946F, -0.13962634015954636F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.08726646259971647F, 0.0F, -0.08726646259971647F);
        this.Skirt01 = new ModelRenderer(this, 46, 43);
        this.Skirt01.setRotationPoint(0.0F, 2.9F, 0.0F);
        this.Skirt01.addBox(-8.5F, 0.0F, -6.0F, 17, 4, 9, 0.0F);
        this.setRotateAngle(Skirt01, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipSL00 = new ModelRenderer(this, 106, 0);
        this.EquipSL00.setRotationPoint(-2.5F, 10.0F, -2.0F);
        this.EquipSL00.addBox(-0.5F, -6.0F, -0.5F, 1, 12, 1, 0.0F);
        this.setRotateAngle(EquipSL00, -1.5707963267948966F, -0.08726646259971647F, 0.5235987755982988F);
        this.Neck = new ModelRenderer(this, 0, 18);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.Neck.addBox(-2.5F, -2.0F, -3.6F, 5, 2, 5, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 106, 31);
        this.Ahoke.setRotationPoint(-2.5F, -4.0F, -7.5F);
        this.Ahoke.addBox(0.0F, -1.0F, -5.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.2617993877991494F, 1.8325957145940461F, 0.2617993877991494F);
        this.Equip02c = new ModelRenderer(this, 0, 0);
        this.Equip02c.setRotationPoint(0.0F, 3.0F, 4.0F);
        this.Equip02c.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 1, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 63);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipCR03c = new ModelRenderer(this, 0, 27);
        this.EquipCR03c.setRotationPoint(-1.9F, -0.5F, 2.7F);
        this.EquipCR03c.addBox(-1.0F, -7.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(EquipCR03c, 0.0F, -0.3490658503988659F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 63);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.Equip02b = new ModelRenderer(this, 104, 24);
        this.Equip02b.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Equip02b.addBox(-4.0F, 0.0F, 0.0F, 8, 6, 4, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 84);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.3141592653589793F, 0.0F, -0.5235987755982988F);
        this.EquipCR01 = new ModelRenderer(this, 0, 0);
        this.EquipCR01.mirror = true;
        this.EquipCR01.setRotationPoint(-3.5F, 3.5F, 2.0F);
        this.EquipCR01.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.Cir01 = new ModelRenderer(this, 20, 12);
        this.Cir01.setRotationPoint(0.0F, 0.0F, -5.5F);
        this.Cir01.addBox(-6.0F, 0.0F, -0.5F, 12, 3, 1, 0.0F);
        this.setRotateAngle(Cir01, 0.0F, 3.141592653589793F, 0.0F);
        this.Equip01d = new ModelRenderer(this, 50, 0);
        this.Equip01d.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Equip01d.addBox(-5.5F, 0.0F, 0.0F, 11, 11, 5, 0.0F);
        this.Cir02 = new ModelRenderer(this, 20, 12);
        this.Cir02.setRotationPoint(0.0F, 0.0F, 5.5F);
        this.Cir02.addBox(-6.0F, 0.0F, -0.5F, 12, 3, 1, 0.0F);
        this.Equip03R = new ModelRenderer(this, 86, 104);
        this.Equip03R.mirror = true;
        this.Equip03R.setRotationPoint(-5.0F, 1.5F, 4.5F);
        this.Equip03R.addBox(-4.0F, 0.0F, 0.0F, 4, 8, 2, 0.0F);
        this.EquipSL02 = new ModelRenderer(this, 110, 0);
        this.EquipSL02.setRotationPoint(0.0F, 11.9F, 0.0F);
        this.EquipSL02.addBox(-0.5F, 0.0F, -0.5F, 1, 12, 1, 0.0F);
        this.Equip00 = new ModelRenderer(this, 0, 0);
        this.Equip00.setRotationPoint(0.0F, 4.0F, 5.0F);
        this.Equip00.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(Equip00, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipSL04 = new ModelRenderer(this, 106, 0);
        this.EquipSL04.setRotationPoint(0.0F, -17.9F, 0.0F);
        this.EquipSL04.addBox(-0.5F, 0.0F, -0.5F, 1, 12, 1, 0.0F);
        this.EquipCL01 = new ModelRenderer(this, 0, 0);
        this.EquipCL01.setRotationPoint(3.5F, 3.5F, 2.0F);
        this.EquipCL01.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.EquipCR02 = new ModelRenderer(this, 0, 2);
        this.EquipCR02.mirror = true;
        this.EquipCR02.setRotationPoint(-1.9F, 0.0F, 0.0F);
        this.EquipCR02.addBox(-1.0F, -3.0F, -4.0F, 1, 7, 9, 0.0F);
        this.Equip02a = new ModelRenderer(this, 0, 0);
        this.Equip02a.setRotationPoint(0.0F, -0.4F, 10.0F);
        this.Equip02a.addBox(-4.5F, 0.0F, 0.0F, 9, 7, 4, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.7F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.EquipCR03b = new ModelRenderer(this, 0, 27);
        this.EquipCR03b.setRotationPoint(-1.9F, -0.5F, 0.5F);
        this.EquipCR03b.addBox(-1.0F, -7.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(EquipCR03b, 0.0F, -0.3490658503988659F, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 0, 33);
        this.Skirt02.setRotationPoint(0.0F, 2.8F, -0.5F);
        this.Skirt02.addBox(-9.0F, 0.0F, -6.0F, 18, 4, 10, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 47);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 112, 34);
        this.Cloth01.setRotationPoint(0.0F, -9.6F, -3.8F);
        this.Cloth01.addBox(-4.0F, 0.0F, 0.0F, 8, 7, 0, 0.0F);
        this.setRotateAngle(Cloth01, -0.5759586531581287F, 0.0F, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 63);
        this.LegLeft02.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 63);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Cir04 = new ModelRenderer(this, 20, 12);
        this.Cir04.setRotationPoint(-5.5F, 0.0F, 0.0F);
        this.Cir04.addBox(-6.0F, 0.0F, -0.5F, 12, 3, 1, 0.0F);
        this.setRotateAngle(Cir04, 0.0F, -1.5707963267948966F, 0.0F);
        this.EquipCL03a = new ModelRenderer(this, 0, 27);
        this.EquipCL03a.setRotationPoint(1.9F, -0.5F, -1.7F);
        this.EquipCL03a.addBox(-1.0F, -7.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(EquipCL03a, 0.0F, 0.3490658503988659F, 0.0F);
        this.Equip02d = new ModelRenderer(this, 0, 49);
        this.Equip02d.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.Equip02d.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6F, -6.9F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.EquipCL03c = new ModelRenderer(this, 0, 27);
        this.EquipCL03c.setRotationPoint(1.9F, -0.5F, 2.7F);
        this.EquipCL03c.addBox(-1.0F, -7.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(EquipCL03c, 0.0F, 0.3490658503988659F, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.2792526803190927F, 0.0F, 0.08726646259971647F);
        this.CirBase = new ModelRenderer(this, 0, 0);
        this.CirBase.setRotationPoint(0.0F, -21.0F, 4.0F);
        this.CirBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(CirBase, -0.2617993877991494F, 0.0F, 0.0F);
        this.Equip03L = new ModelRenderer(this, 86, 104);
        this.Equip03L.setRotationPoint(5.0F, 1.5F, 4.5F);
        this.Equip03L.addBox(0.0F, 0.0F, 0.0F, 4, 8, 2, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.Equip01b = new ModelRenderer(this, 50, 0);
        this.Equip01b.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Equip01b.addBox(-5.5F, 0.0F, 0.0F, 11, 11, 5, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipSL05 = new ModelRenderer(this, 106, 0);
        this.EquipSL05.setRotationPoint(0.0F, -11.9F, 0.0F);
        this.EquipSL05.addBox(-0.5F, 0.0F, -0.5F, 1, 12, 1, 0.0F);
        this.EquipCL02 = new ModelRenderer(this, 0, 2);
        this.EquipCL02.setRotationPoint(1.9F, 0.0F, 0.0F);
        this.EquipCL02.addBox(0.0F, -3.0F, -4.0F, 1, 7, 9, 0.0F);
        this.EquipSL03c = new ModelRenderer(this, 114, 0);
        this.EquipSL03c.setRotationPoint(-0.1F, 13.9F, 3.1F);
        this.EquipSL03c.addBox(-0.5F, -7.0F, -2.0F, 1, 8, 2, 0.0F);
        this.setRotateAngle(EquipSL03c, -0.03490658503988659F, 0.0F, 0.0F);
        this.EquipSL03a = new ModelRenderer(this, 120, 0);
        this.EquipSL03a.setRotationPoint(0.0F, 11.9F, -0.4F);
        this.EquipSL03a.addBox(-0.5F, 0.0F, -0.5F, 1, 14, 3, 0.0F);
        this.Equip01c = new ModelRenderer(this, 26, 0);
        this.Equip01c.setRotationPoint(0.0F, 0.0F, 5.0F);
        this.Equip01c.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 5, 0.0F);
        this.EquipSL01 = new ModelRenderer(this, 106, 0);
        this.EquipSL01.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.EquipSL01.addBox(-0.5F, 0.0F, -0.5F, 1, 12, 1, 0.0F);
        this.Cir03 = new ModelRenderer(this, 20, 12);
        this.Cir03.setRotationPoint(5.5F, 0.0F, 0.0F);
        this.Cir03.addBox(-6.0F, 0.0F, -0.5F, 12, 3, 1, 0.0F);
        this.setRotateAngle(Cir03, 0.0F, 1.5707963267948966F, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.Hair01 = new ModelRenderer(this, 10, 16);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 8.2F);
        this.Hair01.addBox(-8.0F, 0.0F, -8.0F, 16, 7, 10, 0.0F);
        this.setRotateAngle(Hair01, 0.17453292519943295F, 0.0F, 0.0F);
        this.Cir00 = new ModelRenderer(this, 0, 0);
        this.Cir00.mirror = true;
        this.Cir00.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Cir00.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.EquipSL03b = new ModelRenderer(this, 102, 0);
        this.EquipSL03b.setRotationPoint(-0.1F, 25.7F, 2.1F);
        this.EquipSL03b.addBox(-0.5F, -11.0F, -1.0F, 1, 11, 1, 0.0F);
        this.setRotateAngle(EquipSL03b, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipCR03a = new ModelRenderer(this, 0, 27);
        this.EquipCR03a.setRotationPoint(-1.9F, -0.5F, -1.7F);
        this.EquipCR03a.addBox(-1.0F, -7.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(EquipCR03a, 0.0F, -0.3490658503988659F, 0.0F);
        this.EquipCL02.addChild(this.EquipCL03b);
        this.BodyMain.addChild(this.ArmRight01);
        this.BodyMain.addChild(this.BoobR);
        this.Equip00.addChild(this.Equip01a);
        this.BodyMain.addChild(this.BoobL);
        this.Butt.addChild(this.LegRight01);
        this.Butt.addChild(this.Skirt01);
        this.ArmLeft02.addChild(this.EquipSL00);
        this.BodyMain.addChild(this.Neck);
        this.Hair.addChild(this.Ahoke);
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipCR02.addChild(this.EquipCR03c);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Equip03R.addChild(this.EquipCR01);
        this.Equip01c.addChild(this.Equip01d);
        this.Equip01d.addChild(this.Equip03R);
        this.EquipSL01.addChild(this.EquipSL02);
        this.BodyMain.addChild(this.Equip00);
        this.EquipSL00.addChild(this.EquipSL04);
        this.Equip03L.addChild(this.EquipCL01);
        this.EquipCR01.addChild(this.EquipCR02);
        this.Equip01a.addChild(this.Equip02a);
        this.Head.addChild(this.Hair);
        this.EquipCR02.addChild(this.EquipCR03b);
        this.Skirt01.addChild(this.Skirt02);
        this.BodyMain.addChild(this.Butt);
        this.BodyMain.addChild(this.Cloth01);
        this.LegLeft01.addChild(this.LegLeft02);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipCL02.addChild(this.EquipCL03a);
        this.Hair.addChild(this.HairU01);
        this.EquipCL02.addChild(this.EquipCL03c);
        this.Butt.addChild(this.LegLeft01);
        this.Equip01d.addChild(this.Equip03L);
        this.Neck.addChild(this.Head);
        this.Equip01a.addChild(this.Equip01b);
        this.EquipSL04.addChild(this.EquipSL05);
        this.EquipCL01.addChild(this.EquipCL02);
        this.EquipSL02.addChild(this.EquipSL03c);
        this.EquipSL02.addChild(this.EquipSL03a);
        this.Equip01a.addChild(this.Equip01c);
        this.EquipSL00.addChild(this.EquipSL01);
        this.Head.addChild(this.HairMain);
        this.HairMain.addChild(this.Hair01);
        this.EquipSL02.addChild(this.EquipSL03b);
        this.EquipCR02.addChild(this.EquipCR03a);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.setRotateAngle(GlowNeck, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.0F, -0.7F);
        
        this.GlowBodyMain2 = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain2.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain2, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowNeck2 = new ModelRenderer(this, 0, 0);
        this.GlowNeck2.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.setRotateAngle(GlowNeck2, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead2 = new ModelRenderer(this, 0, 0);
        this.GlowHead2.setRotationPoint(0.0F, -1.0F, -0.7F);
        
        this.GlowEquip00 = new ModelRenderer(this, 0, 0);
        this.GlowEquip00.setRotationPoint(0.0F, 4.0F, 5.0F);
        this.setRotateAngle(GlowEquip00, 0.08726646259971647F, 0.0F, 0.0F);
        this.GlowEquip01a = new ModelRenderer(this, 0, 0);
        this.GlowEquip01a.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.GlowEquip02a = new ModelRenderer(this, 0, 0);
        this.GlowEquip02a.setRotationPoint(0.0F, -0.4F, 10.0F);
        
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
        this.GlowBodyMain2.addChild(this.GlowNeck2);
        this.GlowNeck2.addChild(this.GlowHead2);
        this.GlowHead2.addChild(this.CirBase);
        this.CirBase.addChild(this.Cir00);
        this.Cir00.addChild(this.Cir01);
        this.Cir00.addChild(this.Cir02);
        this.Cir00.addChild(this.Cir03);
        this.Cir00.addChild(this.Cir04);
        
        this.GlowBodyMain.addChild(this.GlowEquip00);
        this.GlowEquip00.addChild(this.GlowEquip01a);
        this.GlowEquip01a.addChild(this.GlowEquip02a);
        this.GlowEquip02a.addChild(this.Equip02b);
        this.Equip02b.addChild(this.Equip02c);
        this.Equip02c.addChild(this.Equip02d);
        
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
    		scale = 1.64F;
        	offsetY = -0.58F;
		break;
    	case 2:
    		scale = 1.23F;
        	offsetY = -0.27F;
		break;
    	case 1:
    		scale = 0.82F;
        	offsetY = 0.35F;
		break;
    	default:
    		scale = 0.41F;
        	offsetY = 2.17F;
		break;
    	}
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.scale(scale, scale, scale);
    	GlStateManager.translate(0F, offsetY, 0F);
    	
    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	//light part
    	GlStateManager.disableLighting();
    	GlStateManager.enableCull();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GlStateManager.disableBlend();
    	
    	float light = 80F + MathHelper.cos(f2 * 0.125F) * 120F;
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, light, light);
    	this.GlowBodyMain2.render(f5);
    	
    	GlStateManager.disableCull();
    	GlStateManager.enableLighting();
    	
    	GlStateManager.popMatrix();
    }

	@Override
	public void showEquip(IShipEmotion ent)
	{
		int state = ent.getStateEmotion(ID.S.State);
		
		boolean flag = !EmotionHelper.checkModelState(0, state);	//cannon
		this.Equip00.isHidden = flag;
		this.GlowEquip00.isHidden = flag;
				
		flag = !EmotionHelper.checkModelState(1, state);	//head
		this.CirBase.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(2, state);	//weapon
		this.EquipSL00.isHidden = flag;
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
		this.GlowBodyMain2.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain2.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain2.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowHead2.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead2.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead2.rotateAngleZ = this.Head.rotateAngleZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.51F + 0.26F * ent.getScaleLevel(), 0F);
		this.setFaceHungry(ent);

		//body
		this.Head.rotateAngleX = 0.9599310885968813F;
		this.Head.rotateAngleY = 0.0F;
		this.Head.rotateAngleZ = 0.0F;
		this.Ahoke.rotateAngleX = 0.2617993877991494F;
		this.Ahoke.rotateAngleY = 1.8325957145940461F;
		this.Ahoke.rotateAngleZ = 0.2617993877991494F;
		this.BodyMain.rotateAngleX = -0.2617993877991494F;
    	this.Butt.rotateAngleX = -0.2617993877991494F;
    	this.Butt.offsetY = 0F;
    	this.Skirt01.rotateAngleX = -0.17453292519943295F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt02.rotateAngleX = -0.20943951023931953F;
	  	this.Skirt02.offsetY = 0F;
    	//arm
		this.ArmLeft01.rotateAngleX = 0.4141592653589793F;
		this.ArmLeft01.rotateAngleY = 0.0F;
		this.ArmLeft01.rotateAngleZ = -0.4363323129985824F;
		this.ArmLeft02.rotateAngleX = -0.10471975511965977F;
		this.ArmLeft02.rotateAngleY = 0.0F;
		this.ArmLeft02.rotateAngleZ = 0.0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = 0F;
		this.ArmRight01.rotateAngleX = 0.3617993877991494F;
		this.ArmRight01.rotateAngleY = 0.0F;
		this.ArmRight01.rotateAngleZ = 0.27314402793711257F;
		this.ArmRight02.rotateAngleX = -0.27314402793711257F;
		this.ArmRight02.rotateAngleY = 0.0F;
		this.ArmRight02.rotateAngleZ = 0.0F;
		this.ArmRight02.offsetX = 0F;
		this.ArmRight02.offsetZ = 0F;
		//equip
		this.EquipSL00.rotateAngleX = -1.68352986419518F;
		this.EquipSL00.rotateAngleY = 0F;
		this.EquipSL00.rotateAngleZ = -1.1F;
		this.EquipCL02.rotateAngleX = 1.63F;
		this.EquipCR02.rotateAngleX = 1.63F;
		this.Cir00.rotateAngleY = 0F;
		this.CirBase.offsetY = 0.26F;
    	//leg
		this.LegLeft01.rotateAngleX = -1.7453292519943295F;
		this.LegLeft01.rotateAngleY = -0.5462880558742251F;
		this.LegLeft01.rotateAngleZ = 1.48352986419518F;
		this.LegLeft02.rotateAngleX = 0.4363323129985824F;
		this.LegLeft02.rotateAngleY = 0.0F;
		this.LegLeft02.rotateAngleZ = 0.0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleX = -1.5707963267948966F;
		this.LegRight01.rotateAngleY = 0.08726646259971647F;
		this.LegRight01.rotateAngleZ = -0.17453292519943295F;
		this.LegRight02.rotateAngleX = 1.1344640137963142F;
		this.LegRight02.rotateAngleY = 0.0F;
		this.LegRight02.rotateAngleZ = 0.0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
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
  		boolean spcStand = false;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}

    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.28F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.5F - 0.21F;  //LegRight01
    	
  	    //head
	  	this.Head.rotateAngleX = f4 * 0.014F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	    //boob
	  	this.Cloth01.rotateAngleX = angleX * 0.06F - 0.7F;
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.8F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.8F;
	  	//body
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.35F;
	  	this.Skirt01.rotateAngleX = -0.14F;
	  	this.Skirt02.rotateAngleX = -0.09F;
	  	this.Skirt02.offsetY = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F + 0.2F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.25F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = 0F;
	    this.ArmRight01.rotateAngleX = 0F;
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
		this.Cir00.rotateAngleY = 0F;
		this.CirBase.offsetY = 0F;
		this.EquipSL00.rotateAngleX = -1.1F;
		this.EquipSL00.rotateAngleY = 0.4F;
		this.EquipSL00.rotateAngleZ = 0F;
		this.EquipSL00.offsetX = 0F;
		this.EquipSL00.offsetY = 0F;
		this.EquipSL00.offsetZ = 0F;
		this.EquipCL02.rotateAngleX = f4 * 0.015F + 0.7F;
		this.EquipCR02.rotateAngleX = f4 * 0.015F + 0.7F;
		
		//special stand pos
		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		{
			spcStand = true;
			
			if (t2 > 320)
			{
				setFace(8);
			}
			else if (t2 > 160)
			{
				setFace(0);
			}
			else
			{
				setFace(1);
			}
			
			this.Head.rotateAngleY = 0F;
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
			//equip
			this.EquipSL00.rotateAngleX = -1.17F;
			this.EquipSL00.rotateAngleY = 1.45F;
			this.EquipSL00.rotateAngleZ = 0.0F;
			
			if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED)
			{
				//arm
				this.ArmLeft01.rotateAngleX = 0.6981317007977318F;
				this.ArmLeft01.rotateAngleY = -1.0471975511965976F;
				this.ArmLeft01.rotateAngleZ = -2.443460952792061F;
				this.ArmLeft02.rotateAngleX = -1.3962634015954636F;
				this.ArmLeft02.rotateAngleY = 0.0F;
				this.ArmLeft02.rotateAngleZ = 0.0F;
				//equip
				this.EquipSL00.rotateAngleX = -1.5707963267948966F;
				this.EquipSL00.rotateAngleY = 0.9F;
				this.EquipSL00.rotateAngleZ = 0.0F;
			}
		}

	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {
	    	//奔跑動作
	    	GlStateManager.translate(0F, this.scale * 0.1F, 0F);
	    	//body
	 	    this.Head.rotateAngleX -= 0.6F;
	 	    this.BodyMain.rotateAngleX = 0.9F;
	 	    this.Butt.rotateAngleX -= 0.7F;
	 	    this.Skirt01.rotateAngleX = -0.15F;
	 	  	this.Skirt02.rotateAngleX = -0.32F;
	 	  	//arm
	    	this.ArmLeft01.rotateAngleX = 0.7F;
	    	this.ArmLeft01.rotateAngleY = -1.1F;
	    	this.ArmLeft01.rotateAngleZ = -1F;
		    this.ArmRight01.rotateAngleX = 0.7F;
	    	this.ArmRight01.rotateAngleY = 1.1F;
	    	this.ArmRight01.rotateAngleZ = 1F;
		  	this.ArmRight02.rotateAngleZ = 0F;
	    	//leg
	  		addk1 = angleAdd1 * 1F - 0.28F;  //LegLeft01
		  	addk2 = angleAdd2 * 1F - 0.21F;  //LegRight01
		  	//equip
			this.EquipSL00.rotateAngleX = -1.5F;
			this.EquipSL00.rotateAngleY = 0.2F;
			this.EquipSL00.rotateAngleZ = 0F;
  		}
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {
	    	//潛行, 蹲下動作
	    	GlStateManager.translate(0F, this.scale * 0.06F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.4F;
		  	this.Skirt01.rotateAngleX = -0.12F;
		  	this.Skirt02.rotateAngleX = -0.16F;
		  	this.Skirt02.offsetY = -0.1F;
		    //arm
		  	if (this.EquipSL00.isHidden)
		  	{
		  		this.ArmLeft01.rotateAngleX = -0.6F;
			    this.ArmLeft01.rotateAngleZ = 0.2618F;
			    this.ArmRight01.rotateAngleX = -0.6F;
			    this.ArmRight01.rotateAngleZ = -0.2618F;
		  	}
		  	else
		  	{
		  		this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F - 0.1F;
		    	this.ArmLeft01.rotateAngleY = -0.7F;
		    	this.ArmLeft01.rotateAngleZ = -0.3F;
			    this.ArmRight01.rotateAngleX = angleAdd1 * 0.25F - 0.1F;
		    	this.ArmRight01.rotateAngleY = 0.7F;
		    	this.ArmRight01.rotateAngleZ = 0.3F;
		  	}
		    //leg
		    addk1 -= 0.4F;
		    addk2 -= 0.4F;
		    //equip
		    this.Cir00.rotateAngleY = f2 * 0.025F;
  		}//end if sneaking
  		
	    //坐下動作
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
		    	GlStateManager.translate(0F, 0.5F, 0F);
		    	//body
		    	this.Head.rotateAngleX = 0.0F;
		    	this.Head.rotateAngleY += 1.2217304763960306F;
		    	this.Head.rotateAngleZ = -0.08726646259971647F;
		    	this.BodyMain.rotateAngleX = -0.35F;
		    	this.BodyMain.rotateAngleY = -1.4486232791552935F;
		    	this.Butt.rotateAngleX = -0.3839724354387525F;
		    	this.Skirt01.rotateAngleX = -0.17453292519943295F;
				this.Skirt02.rotateAngleX = -0.2617993877991494F;
				//arm
				this.ArmLeft01.rotateAngleX = -1.22F;
				this.ArmLeft01.rotateAngleY = 0.3141592653589793F;
				this.ArmLeft01.rotateAngleZ = 0.0F;
		    	this.ArmLeft02.rotateAngleX = 0.0F;
		    	this.ArmLeft02.rotateAngleZ = 0.0F;
				this.ArmLeft02.offsetX = 0F;
				this.ArmLeft02.offsetZ = 0F;
		    	this.ArmRight01.rotateAngleX = -0.17453292519943295F;
		    	this.ArmRight01.rotateAngleY = 0.0F;
		    	this.ArmRight01.rotateAngleZ = 0.2617993877991494F;
		    	this.ArmRight02.rotateAngleX = 0.0F;
		    	this.ArmRight02.rotateAngleZ = 0.0F;
				this.ArmRight02.offsetX = 0F;
				this.ArmRight02.offsetZ = 0F;
				//leg
				addk1 = -1.57F;
				addk2 = -1.4F;
		    	this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
		    	this.LegLeft02.rotateAngleX = 0.6108652381980153F;
		    	this.LegLeft02.rotateAngleY = 0.0F;
		    	this.LegLeft02.rotateAngleZ = 0.0F;
		    	this.LegRight01.rotateAngleY = 0.0F;
		    	this.LegRight01.rotateAngleZ = -0.08726646259971647F;
		    	this.LegRight02.rotateAngleX = 1.48352986419518F;
		    	this.LegRight02.rotateAngleY = 0.0F;
		    	this.LegRight02.rotateAngleZ = 0.0F;
				//equip
		    	this.Cir00.rotateAngleY = f2 * 0.025F;
		    	this.EquipSL00.rotateAngleX = 1.42F;
		    	this.EquipSL00.rotateAngleY = -0.18F;
		    	this.EquipSL00.rotateAngleZ = 0.0F;
		    	this.EquipSL00.offsetY = 0.15F;
	    	}
	    	else
	    	{
		    	GlStateManager.translate(0F, 0.47F, 0F);
		    	//body
		    	this.BodyMain.rotateAngleX = -0.3F;
		    	this.Butt.rotateAngleX = -0.2F;
				this.Skirt01.rotateAngleX = -0.26F;
				this.Skirt02.rotateAngleX = -0.45F;
				//leg
				addk1 = -0.9F;
				addk2 = -0.9F;
				this.LegLeft01.rotateAngleZ = -0.14F;
				this.LegLeft02.rotateAngleX = 1.2217F;
				this.LegLeft02.rotateAngleY = 1.2217F;
				this.LegLeft02.rotateAngleZ = -1.0472F;
				this.LegLeft02.offsetX = 0F;
				this.LegLeft02.offsetY = -0.06F;
				this.LegLeft02.offsetZ = 0F;
				this.LegRight01.rotateAngleZ = 0.14F;
				this.LegRight02.rotateAngleX = 1.2217F;
				this.LegRight02.rotateAngleY = -1.2217F;
				this.LegRight02.rotateAngleZ = 1.0472F;
				this.LegRight02.offsetX = 0F;
				this.LegRight02.offsetY = -0.06F;
				this.LegRight02.offsetZ = 0F;
				//equip
				this.Cir00.rotateAngleY = f2 * 0.025F;
				this.EquipSL00.rotateAngleX = -1.06F;
				this.EquipSL00.rotateAngleY = 0.02F;
				this.EquipSL00.rotateAngleZ = -1.29F;
				
				if (spcStand)
				{
					//arm
					this.ArmRight01.rotateAngleX += 0.3F;
					
					if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED)
					{
						//equip
						this.EquipSL00.rotateAngleX = -1.5707963267948966F;
						this.EquipSL00.rotateAngleY = 1.2F;
						this.EquipSL00.rotateAngleZ = 0.0F;
					}
					else
					{
						this.EquipSL00.isHidden = true;
					}
				}
				else
				{
					//arm
					this.ArmLeft01.rotateAngleX += 0.1F;
					this.ArmLeft01.rotateAngleY = 0F;
					this.ArmLeft01.rotateAngleZ = -0.25F;
					this.ArmRight01.rotateAngleX += 0.3F;
					this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = 0.25F;
				}
	    	}
  		}//end if sitting
	    
	    //攻擊動作: 設為30~50會有揮刀動作, 設為100則沒有揮刀動作
	    if (ent.getAttackTick() > 30)
	    {
	    	//reset attack tick (for particle type 12)
	    	if (ent.getAttackTick() == 60) ent.setAttackTick(0);
	    	
	    	if (ent.getStateEmotion(ID.S.Phase) != 1)
	    	{
	    		GlStateManager.translate(0F, 0.05F + ent.getScaleLevel() * 0.02F, 0F);
		    	//body
			    this.BodyMain.rotateAngleX = 0.17453292519943295F;
			    this.BodyMain.rotateAngleY = 0.0F;
			    this.BodyMain.rotateAngleZ = 0.0F;
			    this.Butt.rotateAngleX = 0.0F;
			    this.Head.rotateAngleX = -0.1F;
			    this.Skirt01.rotateAngleX = -0.13962634015954636F;
			    this.Skirt02.rotateAngleX = -0.08726646259971647F;
		    	//arm
			    this.ArmLeft01.rotateAngleX = -1.6755160819145563F;
			    this.ArmLeft01.rotateAngleY = 0.5235987755982988F;
			    this.ArmLeft01.rotateAngleZ = 0.0F;
			    this.ArmLeft02.rotateAngleX = 0.0F;
			    this.ArmLeft02.rotateAngleY = 0.0F;
			    this.ArmLeft02.rotateAngleZ = 0.0F;
				this.ArmLeft02.offsetX = 0F;
				this.ArmLeft02.offsetZ = 0F;
			    this.ArmRight01.rotateAngleX = 0.5235987755982988F;
			    this.ArmRight01.rotateAngleY = 0.0F;
			    this.ArmRight01.rotateAngleZ = 0.5235987755982988F;
			    this.ArmRight02.rotateAngleX = 0.0F;
			    this.ArmRight02.rotateAngleY = 0.0F;
			    this.ArmRight02.rotateAngleZ = 0.0F;
				this.ArmRight02.offsetX = 0F;
				this.ArmRight02.offsetZ = 0F;
		    	//leg
		    	addk1 = -0.5235987755982988F;
		    	addk2 = 0.2617993877991494F;
			    this.LegLeft01.rotateAngleY = 0.0F;
			    this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
			    this.LegLeft02.rotateAngleX = 0.36425021489121656F;
			    this.LegLeft02.rotateAngleY = 0.0F;
			    this.LegLeft02.rotateAngleZ = 0.0F;
				this.LegLeft02.offsetX = 0F;
				this.LegLeft02.offsetY = 0F;
				this.LegLeft02.offsetZ = 0F;
			    this.LegRight01.rotateAngleY = 0.0F;
			    this.LegRight01.rotateAngleZ = -0.08726646259971647F;
			    this.LegRight02.rotateAngleX = 0F;
				this.LegRight02.rotateAngleY = 0F;
				this.LegRight02.rotateAngleZ = 0F;
				this.LegRight02.offsetX = 0F;
				this.LegRight02.offsetY = 0F;
				this.LegRight02.offsetZ = 0F;
		    	//equip
				this.EquipSL00.isHidden = false;
			    this.EquipSL00.rotateAngleX = -0.136659280431156F;
			    this.EquipSL00.rotateAngleY = 1.5707963267948966F;
			    this.EquipSL00.rotateAngleZ = 0.136659280431156F;
		    	//swing left hand
		    	if (ent.getAttackTick() < 51)
		    	{
			    	if (ent.getAttackTick() > 45)
			    	{
				    	int tick = 4 - (ent.getAttackTick() - 46);
				    	float parTick = f2 - (int)f2 + tick;
			    		//arm
			    		this.ArmLeft01.rotateAngleY = 0.52F - 0.524F * parTick;
			    	}
			    	else
			    	{
			    		//arm
			    		this.ArmLeft01.rotateAngleY = -2.1F;
			    	}
		    	}
	    	}
	    	else
	    	{
		    	//奔跑動作
		    	GlStateManager.translate(0F, this.scale * 0.1F, 0F);
		    	//body
		 	    this.Head.rotateAngleX -= 0.6F;
		 	    this.BodyMain.rotateAngleX = 0.9F;
		 	    this.Butt.rotateAngleX -= 0.7F;
		 	    this.Skirt01.rotateAngleX = -0.15F;
		 	  	this.Skirt02.rotateAngleX = -0.32F;
		 	  	//arm
		    	this.ArmLeft01.rotateAngleX = 0.7F;
		    	this.ArmLeft01.rotateAngleY = -1.1F;
		    	this.ArmLeft01.rotateAngleZ = -1F;
			    this.ArmLeft02.rotateAngleX = 0.0F;
			    this.ArmLeft02.rotateAngleY = 0.0F;
			    this.ArmLeft02.rotateAngleZ = 0.0F;
				this.ArmLeft02.offsetX = 0F;
				this.ArmLeft02.offsetZ = 0F;
			    this.ArmRight01.rotateAngleX = 0.7F;
		    	this.ArmRight01.rotateAngleY = 1.1F;
		    	this.ArmRight01.rotateAngleZ = 1F;
			  	this.ArmRight02.rotateAngleZ = 0F;
			    this.ArmRight02.rotateAngleX = 0.0F;
			    this.ArmRight02.rotateAngleY = 0.0F;
			    this.ArmRight02.rotateAngleZ = 0.0F;
				this.ArmRight02.offsetX = 0F;
				this.ArmRight02.offsetZ = 0F;
		    	//leg
		  		addk1 = angleAdd1 * 1F - 0.28F;  //LegLeft01
			  	addk2 = angleAdd2 * 1F - 0.21F;  //LegRight01
			    this.LegLeft01.rotateAngleY = 0F;
			    this.LegLeft01.rotateAngleZ = 0F;
			  	this.LegLeft02.rotateAngleX = 0F;
			    this.LegLeft02.rotateAngleY = 0F;
			    this.LegLeft02.rotateAngleZ = 0F;
				this.LegLeft02.offsetX = 0F;
				this.LegLeft02.offsetY = 0F;
				this.LegLeft02.offsetZ = 0F;
			    this.LegRight01.rotateAngleY = 0F;
			    this.LegRight01.rotateAngleZ = 0F;
			    this.LegRight02.rotateAngleX = 0F;
				this.LegRight02.rotateAngleY = 0F;
				this.LegRight02.rotateAngleZ = 0F;
				this.LegRight02.offsetX = 0F;
				this.LegRight02.offsetY = 0F;
				this.LegRight02.offsetZ = 0F;
			  	//equip
				this.EquipSL00.rotateAngleX = -1.5F;
				this.EquipSL00.rotateAngleY = 0.2F;
				this.EquipSL00.rotateAngleZ = 0F;
	    	}
	    	
	    	//final attack
	    	if (ent.getStateEmotion(ID.S.Phase) == 2)
	    	{
	    		//body
	    		this.Head.rotateAngleX = -0.2617993877991494F;
	    		this.BodyMain.rotateAngleX = 0F;
	    		this.BodyMain.rotateAngleY = f2 * -2F;
	    		//arm
	    		this.ArmLeft01.rotateAngleX = -1.6755160819145563F;
	    		this.ArmLeft01.rotateAngleY = -1.3962634015954636F;
	    		this.ArmLeft01.rotateAngleZ = 0.0F;
	    		this.ArmRight01.rotateAngleX = 0.17453292519943295F;
	    		this.ArmRight01.rotateAngleY = 0.0F;
	    		this.ArmRight01.rotateAngleZ = 1.6755160819145563F;
	    		//leg
	    		addk1 = -0.5235987755982988F;
	    		addk2 = 0.13962634015954636F;
	    		this.LegLeft01.rotateAngleY = 0.0F;
	    		this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
	    		this.LegLeft02.rotateAngleX = 1.0471975511965976F;
	    		this.LegLeft02.rotateAngleY = 0.0F;
	    		this.LegLeft02.rotateAngleZ = 0.0F;
	    		this.LegRight01.rotateAngleY = 0.0F;
	    		this.LegRight01.rotateAngleZ = -0.08726646259971647F;
	    	}
	    	else if (ent.getStateEmotion(ID.S.Phase) == 3)
	    	{
	    		//body
	    		this.Head.rotateAngleX = -0.7853981633974483F;
	    		this.BodyMain.rotateAngleX = 1.3962634015954636F;
	    		this.Butt.rotateAngleX = -0.8726646259971648F;
	    		//arm
	    		this.ArmLeft01.rotateAngleX = -2.35F;
	    		this.ArmLeft01.rotateAngleY = 0.2617993877991494F;
	    		this.ArmLeft01.rotateAngleZ = 0.0F;
	    		this.ArmRight01.rotateAngleX = 0.6981317007977318F;
	    		this.ArmRight01.rotateAngleY = 0.0F;
	    		this.ArmRight01.rotateAngleZ = 0.6981317007977318F;
	    		//leg
	    		addk1 = 0.2617993877991494F;
	    		addk2 = -0.5235987755982988F;
	    		this.LegLeft01.rotateAngleY = 0.0F;
	    		this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
	    		this.LegLeft02.rotateAngleX = 0.2617993877991494F;
	    		this.LegLeft02.rotateAngleY = 0.0F;
	    		this.LegLeft02.rotateAngleZ = 0.0F;
	    		this.LegRight01.rotateAngleY = 0.0F;
	    		this.LegRight01.rotateAngleZ = -0.08726646259971647F;
	    		this.LegRight02.rotateAngleX = 1.3962634015954636F;
	    		this.LegRight02.rotateAngleY = 0.0F;
	    		this.LegRight02.rotateAngleZ = 0.0F;
	    		//equip
	    		this.EquipSL00.rotateAngleX = 0.0F;
	    		this.EquipSL00.rotateAngleY = 0.0F;
	    		this.EquipSL00.rotateAngleZ = -0.17453292519943295F;
	    		this.EquipSL00.offsetX = 0.32F + (50 - ent.getAttackTick()) * 0.22F;
	    		this.EquipSL00.offsetY = 2F + (50 - ent.getAttackTick()) * 5F;
	    		this.EquipSL00.offsetZ = -0.08F;
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
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
	
}