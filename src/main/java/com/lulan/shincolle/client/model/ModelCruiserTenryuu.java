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
 * ModelCruiserTenryuu - PinkaLulan 2017/1/3
 * Created using Tabula 5.1.0
 */
public class ModelCruiserTenryuu extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft01;
    public ModelRenderer Cloth01;
    public ModelRenderer EquipSR01;
    public ModelRenderer Equip00;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer EarL01;
    public ModelRenderer EarR01;
    public ModelRenderer EyeMask;
    public ModelRenderer HairU01;
    public ModelRenderer Ahoke;
    public ModelRenderer Hair01;
    public ModelRenderer EarL02;
    public ModelRenderer EarL03;
    public ModelRenderer EarL04;
    public ModelRenderer EarR02;
    public ModelRenderer EarR03;
    public ModelRenderer EarR04;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft02;
    public ModelRenderer ShoeL01;
    public ModelRenderer ShoeL00;
    public ModelRenderer ShoeL02;
    public ModelRenderer Skirt02;
    public ModelRenderer LegRight02;
    public ModelRenderer ShoeR01;
    public ModelRenderer ShoeR00;
    public ModelRenderer ShoeR02;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight02a;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft02a;
    public ModelRenderer EquipSL00;
    public ModelRenderer EquipSL00a;
    public ModelRenderer EquipSL00b;
    public ModelRenderer EquipSL01;
    public ModelRenderer EquipSL02;
    public ModelRenderer EquipSL02a;
    public ModelRenderer EquipSL03;
    public ModelRenderer EquipSL03a;
    public ModelRenderer EquipSR02;
    public ModelRenderer EquipSR03;
    public ModelRenderer Equip01a;
    public ModelRenderer Equip01b;
    public ModelRenderer Equip01c;
    public ModelRenderer Equip02a;
    public ModelRenderer Equip01d;
    public ModelRenderer Equip03L;
    public ModelRenderer Equip03R;
    public ModelRenderer EquipCL01;
    public ModelRenderer EquipCL02;
    public ModelRenderer EquipCL03;
    public ModelRenderer EquipCL04;
    public ModelRenderer EquipCL05;
    public ModelRenderer EquipCR01;
    public ModelRenderer EquipCR02;
    public ModelRenderer EquipCR03;
    public ModelRenderer EquipCR04;
    public ModelRenderer EquipCR05;
    public ModelRenderer Equip02b;
    public ModelRenderer Equip02c;
    public ModelRenderer Equip02d;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowEquip00;
    public ModelRenderer GlowEquip01a;
    public ModelRenderer GlowEquip02a;
    
    
    public ModelCruiserTenryuu()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.offsetItem = new float[] {0.06F, 1.07F, -0.06F};
        this.offsetBlock = new float[] {0.06F, 1.07F, -0.06F};
        
        this.setDefaultFaceModel();
        
        this.ArmRight01 = new ModelRenderer(this, 24, 84);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.0F, 0.3490658503988659F);
        this.LegLeft02 = new ModelRenderer(this, 0, 63);
        this.LegLeft02.setRotationPoint(3F, 14F, -3F);
        this.LegLeft02.addBox(-6F, 0F, 0F, 6, 15, 6, 0.0F);
        this.EquipSL02 = new ModelRenderer(this, 90, 0);
        this.EquipSL02.setRotationPoint(0.0F, 11.9F, 0.0F);
        this.EquipSL02.addBox(-2.5F, 0.0F, -0.5F, 3, 11, 1, 0.0F);
        this.setRotateAngle(EquipSL02, 0.0F, 0.0F, 0.10471975511965977F);
        this.EarR04 = new ModelRenderer(this, 74, 34);
        this.EarR04.mirror = true;
        this.EarR04.setRotationPoint(0.0F, -5.0F, 0.3F);
        this.EarR04.addBox(0.0F, -4.0F, 0.0F, 2, 4, 5, 0.0F);
        this.EquipSL03 = new ModelRenderer(this, 90, 0);
        this.EquipSL03.setRotationPoint(0.0F, 10.9F, 0.0F);
        this.EquipSL03.addBox(-2.5F, 0.0F, -0.5F, 3, 8, 1, 0.0F);
        this.setRotateAngle(EquipSL03, 0.0F, 0.0F, 0.13962634015954636F);
        this.ArmRight02a = new ModelRenderer(this, 104, 33);
        this.ArmRight02a.mirror = true;
        this.ArmRight02a.setRotationPoint(2.5F, 1.3F, -2.4F);
        this.ArmRight02a.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
        this.setRotateAngle(ArmRight02a, 0.06981317007977318F, 0.0F, 0.0F);
        this.Equip01b = new ModelRenderer(this, 52, 0);
        this.Equip01b.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Equip01b.addBox(-5.5F, 0.0F, 0.0F, 11, 11, 5, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 16, 22);
        this.Cloth01.setRotationPoint(0.0F, -11.7F, -0.2F);
        this.Cloth01.addBox(-4.0F, 0.0F, -4.0F, 8, 2, 6, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.20943951023931953F, 0.0F, -0.08726646259971647F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 84);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.10471975511965977F, 0.0F, -0.3490658503988659F);
        this.Equip03R = new ModelRenderer(this, 86, 104);
        this.Equip03R.mirror = true;
        this.Equip03R.setRotationPoint(-5.0F, 1.5F, 4.5F);
        this.Equip03R.addBox(-4.0F, 0.0F, 0.0F, 4, 8, 2, 0.0F);
        this.EarL04 = new ModelRenderer(this, 74, 34);
        this.EarL04.setRotationPoint(0.0F, -5.0F, 0.3F);
        this.EarL04.addBox(0.0F, -4.0F, 0.0F, 2, 4, 5, 0.0F);
        this.EarL02 = new ModelRenderer(this, 88, 41);
        this.EarL02.setRotationPoint(-1.0F, -2.5F, -1.0F);
        this.EarL02.addBox(0.0F, -4.0F, -3.5F, 2, 4, 7, 0.0F);
        this.EquipSL02a = new ModelRenderer(this, 46, 62);
        this.EquipSL02a.mirror = true;
        this.EquipSL02a.setRotationPoint(-4.3F, -3.0F, 0.0F);
        this.EquipSL02a.addBox(0.0F, 0.0F, -0.5F, 2, 11, 1, 0.0F);
        this.setRotateAngle(EquipSL02a, -0.017453292519943295F, 0.0F, -0.05235987755982988F);
        this.EquipSL00b = new ModelRenderer(this, 66, 40);
        this.EquipSL00b.setRotationPoint(-0.1F, -3.8F, 0.0F);
        this.EquipSL00b.addBox(0.0F, -2.0F, -0.5F, 3, 2, 1, 0.0F);
        this.setRotateAngle(EquipSL00b, 0.0F, 0.0F, 0.13962634015954636F);
        this.Neck = new ModelRenderer(this, 29, 12);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.Neck.addBox(-2.5F, -2.0F, -3.6F, 5, 2, 5, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 46, 43);
        this.Skirt01.setRotationPoint(0.0F, 2.9F, 0.0F);
        this.Skirt01.addBox(-8.5F, 0.0F, -6.0F, 17, 4, 9, 0.0F);
        this.setRotateAngle(Skirt01, -0.13962634015954636F, 0.0F, 0.0F);
        this.Equip02d = new ModelRenderer(this, 0, 28);
        this.Equip02d.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.Equip02d.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 2, 0.0F);
        this.EarL03 = new ModelRenderer(this, 88, 31);
        this.EarL03.setRotationPoint(0.0F, -3.0F, -3.2F);
        this.EarL03.addBox(0.0F, -5.0F, 0.0F, 2, 4, 6, 0.0F);
        this.EquipCR02 = new ModelRenderer(this, 0, 0);
        this.EquipCR02.mirror = true;
        this.EquipCR02.setRotationPoint(-1.9F, 0.0F, 0.0F);
        this.EquipCR02.addBox(-5.0F, -3.0F, -2.0F, 5, 7, 7, 0.0F);
        this.EquipSR02 = new ModelRenderer(this, 108, 0);
        this.EquipSR02.setRotationPoint(0.0F, 10.0F, 1.5F);
        this.EquipSR02.addBox(-1.0F, 0.0F, -3.0F, 2, 12, 3, 0.0F);
        this.setRotateAngle(EquipSR02, -0.05235987755982988F, 0.0F, 0.0F);
        this.Equip01d = new ModelRenderer(this, 52, 0);
        this.Equip01d.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.Equip01d.addBox(-5.5F, 0.0F, 0.0F, 11, 11, 5, 0.0F);
        this.EquipCR05 = new ModelRenderer(this, 0, 0);
        this.EquipCR05.mirror = true;
        this.EquipCR05.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipCR05.addBox(-1.0F, -13.6F, -1.0F, 2, 8, 2, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 63);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(-3F, 14F, -3F);
        this.LegRight02.addBox(0F, 0F, 0F, 6, 15, 6, 0.0F);
        this.BoobL = new ModelRenderer(this, 33, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.5F, -8.1F, -3.7F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, 0.08726646259971647F, 0.08726646259971647F);
        this.ShoeR01 = new ModelRenderer(this, 0, 0);
        this.ShoeR01.mirror = true;
        this.ShoeR01.setRotationPoint(3F, 10.5F, 3F);
        this.ShoeR01.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.Equip01c = new ModelRenderer(this, 28, 0);
        this.Equip01c.setRotationPoint(0.0F, 0.0F, 5.0F);
        this.Equip01c.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 5, 0.0F);
        this.Equip02a = new ModelRenderer(this, 0, 0);
        this.Equip02a.setRotationPoint(0.0F, -0.4F, 10.0F);
        this.Equip02a.addBox(-4.5F, 0.0F, 0.0F, 9, 7, 4, 0.0F);
        this.EarR03 = new ModelRenderer(this, 88, 31);
        this.EarR03.mirror = true;
        this.EarR03.setRotationPoint(0.0F, -3.0F, -3.2F);
        this.EarR03.addBox(0.0F, -5.0F, 0.0F, 2, 4, 6, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 47);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 106, 31);
        this.Ahoke.setRotationPoint(-0.5F, -7.0F, -6.0F);
        this.Ahoke.addBox(0.0F, -6.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.20943951023931953F, 0.6981317007977318F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 63);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.2792526803190927F, 0.0F, 0.08726646259971647F);
        this.Hair01 = new ModelRenderer(this, 46, 21);
        this.Hair01.setRotationPoint(0.0F, 11.5F, 3.3F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 5, 6, 0.0F);
        this.setRotateAngle(Hair01, 0.2617993877991494F, 0.0F, 0.0F);
        this.EquipCL01 = new ModelRenderer(this, 0, 0);
        this.EquipCL01.setRotationPoint(3.5F, 3.5F, 2.0F);
        this.EquipCL01.addBox(0.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.EquipCL05 = new ModelRenderer(this, 0, 0);
        this.EquipCL05.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipCL05.addBox(-1.0F, -13.6F, -1.0F, 2, 8, 2, 0.0F);
        this.EquipSR01 = new ModelRenderer(this, 118, 0);
        this.EquipSR01.setRotationPoint(-9.0F, 5.5F, -5.0F);
        this.EquipSR01.addBox(-1.0F, -2.0F, -1.5F, 2, 12, 3, 0.0F);
        this.setRotateAngle(EquipSR01, 1.3089969389957472F, -0.13962634015954636F, -0.13962634015954636F);
        this.Equip00 = new ModelRenderer(this, 0, 0);
        this.Equip00.setRotationPoint(0.0F, 4.0F, 5.0F);
        this.Equip00.addBox(-1.5F, -1.5F, -2.0F, 3, 3, 2, 0.0F);
        this.setRotateAngle(Equip00, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipSL01 = new ModelRenderer(this, 90, 0);
        this.EquipSL01.setRotationPoint(2.1F, 4.7F, 0.0F);
        this.EquipSL01.addBox(-2.5F, 0.0F, -0.5F, 3, 12, 1, 0.0F);
        this.setRotateAngle(EquipSL01, 0.0F, 0.0F, 0.06981317007977318F);
        this.Equip01a = new ModelRenderer(this, 28, 0);
        this.Equip01a.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.Equip01a.addBox(-3.5F, 0.0F, 0.0F, 7, 7, 5, 0.0F);
        this.ShoeR00 = new ModelRenderer(this, 6, 5);
        this.ShoeR00.mirror = true;
        this.ShoeR00.setRotationPoint(3F, 4.2F, 3F);
        this.ShoeR00.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.setRotateAngle(ShoeR00, 0.0F, -3.141592653589793F, 0.0F);
        this.ShoeL01 = new ModelRenderer(this, 0, 0);
        this.ShoeL01.setRotationPoint(-3F, 10.5F, 3F);
        this.ShoeL01.addBox(-3.5F, 0.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(ShoeL01, 0.08726646259971647F, 0.0F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.EquipCR03 = new ModelRenderer(this, 0, 18);
        this.EquipCR03.mirror = true;
        this.EquipCR03.setRotationPoint(0.0F, -7.0F, -1.5F);
        this.EquipCR03.addBox(-5.0F, 0.0F, 0.0F, 5, 4, 6, 0.0F);
        this.EquipSL00 = new ModelRenderer(this, 98, 27);
        this.EquipSL00.setRotationPoint(-2.0F, 9.3F, -3.0F);
        this.EquipSL00.addBox(0.0F, -4.0F, -0.5F, 2, 8, 1, 0.0F);
        this.setRotateAngle(EquipSL00, -1.5707963267948966F, -0.13962634015954636F, 1.5707963267948966F);
        this.EarL01 = new ModelRenderer(this, 43, 75);
        this.EarL01.setRotationPoint(9.0F, -11.0F, 4.0F);
        this.EarL01.addBox(-1.0F, -2.5F, -2.5F, 2, 5, 5, 0.0F);
        this.setRotateAngle(EarL01, 0.08726646259971647F, -0.17453292519943295F, -0.08726646259971647F);
        this.EquipCR04 = new ModelRenderer(this, 46, 36);
        this.EquipCR04.mirror = true;
        this.EquipCR04.setRotationPoint(-2.5F, 3.0F, 3.0F);
        this.EquipCR04.addBox(-1.5F, -5.8F, -1.5F, 3, 4, 3, 0.0F);
        this.EquipCL02 = new ModelRenderer(this, 0, 0);
        this.EquipCL02.setRotationPoint(1.9F, 0.0F, 0.0F);
        this.EquipCL02.addBox(0.0F, -3.0F, -2.0F, 5, 7, 7, 0.0F);
        this.Equip02b = new ModelRenderer(this, 104, 23);
        this.Equip02b.setRotationPoint(0.0F, 0.0F, 4.0F);
        this.Equip02b.addBox(-4.0F, 0.0F, 0.0F, 8, 6, 4, 0.0F);
        this.ShoeL00 = new ModelRenderer(this, 6, 5);
        this.ShoeL00.setRotationPoint(-3F, 4.2F, 3F);
        this.ShoeL00.addBox(-3.6F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.setRotateAngle(ShoeL00, 0.0F, -3.141592653589793F, 0.0F);
        this.ArmLeft02a = new ModelRenderer(this, 104, 33);
        this.ArmLeft02a.setRotationPoint(-2.5F, 1.3F, -2.4F);
        this.ArmLeft02a.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
        this.setRotateAngle(ArmLeft02a, 0.06981317007977318F, 0.0F, 0.0F);
        this.EquipCL03 = new ModelRenderer(this, 0, 18);
        this.EquipCL03.setRotationPoint(0.0F, -7.0F, -1.5F);
        this.EquipCL03.addBox(0.0F, 0.0F, 0.0F, 5, 4, 6, 0.0F);
        this.EarR02 = new ModelRenderer(this, 88, 41);
        this.EarR02.mirror = true;
        this.EarR02.setRotationPoint(-1.0F, -2.5F, -1.0F);
        this.EarR02.addBox(0.0F, -4.0F, -3.5F, 2, 4, 7, 0.0F);
        this.EquipSL00a = new ModelRenderer(this, 67, 35);
        this.EquipSL00a.setRotationPoint(-0.7F, 3.9F, 0.0F);
        this.EquipSL00a.addBox(0.0F, 0.0F, -1.0F, 4, 1, 2, 0.0F);
        this.Equip03L = new ModelRenderer(this, 86, 104);
        this.Equip03L.setRotationPoint(5.0F, 1.5F, 4.5F);
        this.Equip03L.addBox(0.0F, 0.0F, 0.0F, 4, 8, 2, 0.0F);
        this.EquipCR01 = new ModelRenderer(this, 0, 0);
        this.EquipCR01.mirror = true;
        this.EquipCR01.setRotationPoint(-3.5F, 3.5F, 2.0F);
        this.EquipCR01.addBox(-2.0F, -1.0F, -1.0F, 2, 2, 2, 0.0F);
        this.EyeMask = new ModelRenderer(this, 114, 17);
        this.EyeMask.setRotationPoint(2.7F, -8.4F, -6.7F);
        this.EyeMask.addBox(0.0F, 0.0F, 0.0F, 6, 5, 1, 0.0F);
        this.setRotateAngle(EyeMask, 0.0F, 0.0F, 0.4363323129985824F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.ShoeL02 = new ModelRenderer(this, 74, 6);
        this.ShoeL02.setRotationPoint(0.0F, -0.3F, -2.5F);
        this.ShoeL02.addBox(-0.5F, 0.0F, -10.0F, 1, 3, 10, 0.0F);
        this.setRotateAngle(ShoeL02, -0.17453292519943295F, 0.0F, 0.0F);
        this.Equip02c = new ModelRenderer(this, 0, 0);
        this.Equip02c.setRotationPoint(0.0F, 3.0F, 4.0F);
        this.Equip02c.addBox(-3.5F, 0.0F, 0.0F, 7, 8, 1, 0.0F);
        this.EarR01 = new ModelRenderer(this, 43, 75);
        this.EarR01.mirror = true;
        this.EarR01.setRotationPoint(-9.0F, -11.0F, 4.0F);
        this.EarR01.addBox(-1.0F, -2.5F, -2.5F, 2, 5, 5, 0.0F);
        this.setRotateAngle(EarR01, 0.08726646259971647F, 0.17453292519943295F, 0.08726646259971647F);
        this.ShoeR02 = new ModelRenderer(this, 74, 6);
        this.ShoeR02.mirror = true;
        this.ShoeR02.setRotationPoint(0.0F, -0.3F, -2.5F);
        this.ShoeR02.addBox(-0.5F, 0.0F, -10.0F, 1, 3, 10, 0.0F);
        this.setRotateAngle(ShoeR02, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipCL04 = new ModelRenderer(this, 46, 36);
        this.EquipCL04.setRotationPoint(2.5F, 3.0F, 3.0F);
        this.EquipCL04.addBox(-1.5F, -5.8F, -1.5F, 3, 4, 3, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 0, 33);
        this.Skirt02.setRotationPoint(0.0F, 2.8F, -0.5F);
        this.Skirt02.addBox(-9.0F, 0.0F, -6.0F, 18, 4, 10, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 63);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipSL03a = new ModelRenderer(this, 46, 62);
        this.EquipSL03a.setRotationPoint(-1.7F, -3.0F, -0.2F);
        this.EquipSL03a.addBox(-2.5F, 0.0F, -0.5F, 2, 11, 1, 0.0F);
        this.setRotateAngle(EquipSL03a, 0.017453292519943295F, 0.0F, -0.15707963267948966F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-3.5F, -8.1F, -3.7F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, -0.08726646259971647F, -0.08726646259971647F);
        this.EquipSR03 = new ModelRenderer(this, 98, 0);
        this.EquipSR03.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.EquipSR03.addBox(-1.0F, 0.0F, -3.0F, 2, 12, 3, 0.0F);
        this.setRotateAngle(EquipSR03, -0.05235987755982988F, 0.0F, 0.0F);
        this.BodyMain.addChild(this.ArmRight01);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipSL01.addChild(this.EquipSL02);
        this.EquipSL02.addChild(this.EquipSL03);
        this.ArmRight02.addChild(this.ArmRight02a);
        this.Equip01a.addChild(this.Equip01b);
        this.BodyMain.addChild(this.Cloth01);
        this.Hair.addChild(this.HairU01);
        this.Butt.addChild(this.LegRight01);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Equip01d.addChild(this.Equip03R);
        this.EquipSL02.addChild(this.EquipSL02a);
        this.EquipSL00.addChild(this.EquipSL00b);
        this.BodyMain.addChild(this.Neck);
        this.Butt.addChild(this.Skirt01);
        this.EquipCR01.addChild(this.EquipCR02);
        this.EquipSR01.addChild(this.EquipSR02);
        this.Equip01c.addChild(this.Equip01d);
        this.EquipCR04.addChild(this.EquipCR05);
        this.LegRight01.addChild(this.LegRight02);
        this.BodyMain.addChild(this.BoobL);
        this.LegRight02.addChild(this.ShoeR01);
        this.Equip01a.addChild(this.Equip01c);
        this.Equip01a.addChild(this.Equip02a);
        this.BodyMain.addChild(this.Butt);
        this.Hair.addChild(this.Ahoke);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Butt.addChild(this.LegLeft01);
        this.HairMain.addChild(this.Hair01);
        this.Equip03L.addChild(this.EquipCL01);
        this.EquipCL04.addChild(this.EquipCL05);
        this.BodyMain.addChild(this.EquipSR01);
        this.BodyMain.addChild(this.Equip00);
        this.EquipSL00.addChild(this.EquipSL01);
        this.Equip00.addChild(this.Equip01a);
        this.LegRight02.addChild(this.ShoeR00);
        this.LegLeft02.addChild(this.ShoeL01);
        this.Head.addChild(this.Hair);
        this.EquipCR02.addChild(this.EquipCR03);
        this.ArmLeft02.addChild(this.EquipSL00);
        this.EquipCR03.addChild(this.EquipCR04);
        this.EquipCL01.addChild(this.EquipCL02);
        
        this.LegLeft02.addChild(this.ShoeL00);
        this.ArmLeft02.addChild(this.ArmLeft02a);
        this.EquipCL02.addChild(this.EquipCL03);
        this.EquipSL00.addChild(this.EquipSL00a);
        this.Equip01d.addChild(this.Equip03L);
        this.Equip03R.addChild(this.EquipCR01);
        this.Head.addChild(this.EyeMask);
        this.Neck.addChild(this.Head);
        this.Head.addChild(this.HairMain);
        this.ShoeL01.addChild(this.ShoeL02);

        this.ShoeR01.addChild(this.ShoeR02);
        this.EquipCL03.addChild(this.EquipCL04);
        this.Skirt01.addChild(this.Skirt02);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipSL03.addChild(this.EquipSL03a);
        this.BodyMain.addChild(this.BoobR);
        this.EquipSR02.addChild(this.EquipSR03);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.setRotateAngle(GlowNeck, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.0F, -0.7F);
        
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
        this.GlowHead.addChild(this.EarL01);
        this.EarL01.addChild(this.EarL02);
        this.EarL02.addChild(this.EarL03);
        this.EarL03.addChild(this.EarL04);
        this.GlowHead.addChild(this.EarR01);
        this.EarR01.addChild(this.EarR02);
        this.EarR02.addChild(this.EarR03);
        this.EarR03.addChild(this.EarR04);
        
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
		this.Equip00.isHidden = flag;
		this.GlowEquip00.isHidden = flag;
				
		flag = !EmotionHelper.checkModelState(1, state);	//head
		this.EarL01.isHidden = flag;
		this.EarR01.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(2, state);	//sword
		this.EquipSL00.isHidden = flag;
		this.EquipSR01.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(3, state);	//eye mask
		this.EyeMask.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(4, state);	//shoes
		this.ShoeL02.isHidden = flag;
		this.ShoeR02.isHidden = flag;
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
    	GlStateManager.translate(0F, 0.53F + 0.26F * ent.getScaleLevel(), 0F);
		this.setFaceHungry(ent);

		//body
		this.Ahoke.rotateAngleX = 0.20943951023931953F;
		this.Ahoke.rotateAngleY = 0.6981317007977318F;
		this.Ahoke.rotateAngleZ = 0.0F;
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
	  	this.Skirt01.rotateAngleX = 0F;
	  	this.Skirt01.offsetY = 0F;
		this.Skirt02.rotateAngleX = -0.08726646259971647F;
		this.Skirt02.rotateAngleY = 0.0F;
		this.Skirt02.rotateAngleZ = 0.0F;
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
		//leg
		this.LegLeft01.rotateAngleX = -0.6981317007977318F;
		this.LegLeft01.rotateAngleY = -0.6981317007977318F;
		this.LegLeft01.rotateAngleZ = -0.2617993877991494F;
		this.LegLeft02.rotateAngleX = 1.5707963267948966F;
		this.LegLeft02.rotateAngleY = 0.0F;
		this.LegLeft02.rotateAngleZ = 0.0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleX = 0.0F;
		this.LegRight01.rotateAngleY = -0.7853981633974483F;
		this.LegRight01.rotateAngleZ = -0.5759586531581287F;
		this.LegRight02.rotateAngleX = 1.3089969389957472F;
		this.LegRight02.rotateAngleY = 0.0F;
		this.LegRight02.rotateAngleZ = 0.0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
		this.EquipSL00.isHidden = true;
		this.Equip00.rotateAngleX = 0.08726646259971647F;
		this.Equip00.rotateAngleY = 0.0F;
		this.Equip00.rotateAngleZ = 0.0F;
		this.EquipSR01.rotateAngleX = -0.1F;
		this.EquipSR01.rotateAngleY = -0.13962634015954636F;
		this.EquipSR01.rotateAngleZ = -0.13962634015954636F;
		this.EarL01.rotateAngleX = 0.6F;
		this.EarL01.rotateAngleY = -0.17453292519943295F;
		this.EarL01.rotateAngleZ = -0.08726646259971647F;
		this.EarR01.rotateAngleX = 0.6F;
		this.EarR01.rotateAngleY = 0.17453292519943295F;
		this.EarR01.rotateAngleZ = 0.08726646259971647F;
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
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.8F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.8F;
	  	//body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.7F;
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
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.0873F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
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
		this.EquipSL00.rotateAngleX = -1.57F;
		this.EquipSL00.rotateAngleY = -0.14F;
		this.EquipSL00.rotateAngleZ = 1.57F;
		this.EquipSR01.rotateAngleX = 1.3F;
		this.EquipCL02.rotateAngleX = f4 * 0.008F + 0.7F;
		this.EquipCR02.rotateAngleX = f4 * 0.008F + 0.7F;
		this.EquipCL04.rotateAngleX = f4 * 0.008F;
		this.EquipCR04.rotateAngleX = f4 * 0.008F;
		this.EarL01.rotateAngleX = angleX * 0.1F + 0.0873F;
		this.EarR01.rotateAngleX = angleX * 0.1F + 0.0873F;
		
		float modf2 = f2 % 128F;
		if (modf2 < 6F)
		{
			//total 3 ticks, loop twice in 6 ticks
			if (modf2 >= 3F) modf2 -= 3F;
			float anglef2 = MathHelper.sin(modf2 * 1.0472F) * 0.08F;
			this.EarL01.rotateAngleZ = -anglef2 - 0.0873F;
			this.EarR01.rotateAngleZ = anglef2 + 0.0873F;
		}
		else
		{
			this.EarL01.rotateAngleZ = -0.0873F;
			this.EarR01.rotateAngleZ = 0.0873F;
		}
		
		//special stand pos
		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		{
			if (t2 > 180)
			{
				//arm
				this.ArmLeft01.rotateAngleX = 0.44F;
				this.ArmLeft01.rotateAngleY = -0.14F;
				this.ArmLeft01.rotateAngleZ = -0.52F;
				this.ArmRight01.rotateAngleX = -0.17F;
				this.ArmRight01.rotateAngleY = 0F;
				this.ArmRight01.rotateAngleZ = 0.7F;
				this.ArmRight02.rotateAngleZ = -1.22F;
				//leg
		  		addk1 = angleAdd1 * 0.5F - 0.35F;  //LegLeft01
			  	addk2 = angleAdd2 * 0.5F - 0.09F;  //LegRight01
			  	
			  	if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED)
			  	{
					//arm
					this.ArmLeft01.rotateAngleX = -1.4F;
					this.ArmLeft01.rotateAngleY = -1.4F;
					this.ArmLeft01.rotateAngleZ = 0.87F;
					this.ArmLeft02.rotateAngleX = -2.1F;
					this.ArmLeft02.offsetZ = -0.32F;
					//equip
					this.EquipSL00.rotateAngleX = -1.83F;
					this.EquipSL00.rotateAngleY = 0.35F;
					this.EquipSL00.rotateAngleZ = 1.57F;
			  	}
			}
			else
			{
				this.setFace(8);
				//body
				this.BodyMain.rotateAngleX = -0.44F;
				this.Head.rotateAngleX = 0.52F;
				this.Head.rotateAngleY = 0F;
				this.Head.rotateAngleZ = 0F;
				//arm
				this.ArmLeft01.rotateAngleX = -1.05F;
				this.ArmLeft01.rotateAngleY = -1.05F;
				this.ArmLeft01.rotateAngleZ = 1.4F;
				this.ArmLeft02.rotateAngleZ = 2.1F;
				this.ArmLeft02.offsetX = -0.32F;
				this.ArmLeft02.offsetZ = 0F;
				this.ArmRight01.rotateAngleX = -1.57F;
				this.ArmRight01.rotateAngleY = -1.31F;
				this.ArmRight01.rotateAngleZ = 1.22F;
				this.ArmRight02.rotateAngleX = -0.96F;
				//leg
		  		addk1 = angleAdd1 * 0.5F + 0.4F;	//LegLeft01
			  	addk2 = angleAdd2 * 0.5F + 0.09F;	//LegRight01
			  	this.LegLeft01.rotateAngleY = 0F;
			  	this.LegLeft01.rotateAngleZ = f1 > 0.1F ? 0.05F : 0.26F;
			  	this.LegRight01.rotateAngleY = 0F;
			  	this.LegRight01.rotateAngleZ = f1 > 0.1F ? -0.05F : -0.26F;
			  	//skirt
			  	this.Skirt01.rotateAngleX = 0F;
			  	this.Skirt02.rotateAngleX = 0.09F;
			  	//equip
			  	this.EquipSL00.isHidden = true;
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
		  	if (this.EquipSR01.isHidden)
		  	{
		  		this.ArmRight02.rotateAngleZ = 0F;
		  	}
		  	else if (t2 > 300)
		  	{
		  		this.ArmRight02.rotateAngleZ = -1.1F;
		  	}
	    	//leg
	  		addk1 = angleAdd1 * 1F - 0.28F;  //LegLeft01
		  	addk2 = angleAdd2 * 1F - 0.21F;  //LegRight01
		  	//equip
		  	this.EquipSR01.rotateAngleX = 0.7F;
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
		    this.EquipSR01.rotateAngleX = 0F;
  		}//end if sneaking
  		
	    //坐下動作
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
	    		GlStateManager.translate(0F, 0.41F, 0F);
		    	//body
		    	this.BodyMain.rotateAngleX = 0.7F;
		    	this.Butt.rotateAngleX = -0.79F;
		    	this.Head.rotateAngleX -= 1.2F;
		    	//arm
			  	if (!this.EquipSL00.isHidden && ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED)
			  	{
					//arm
					this.ArmLeft01.rotateAngleX = -2.44F;
					this.ArmLeft01.rotateAngleY = 1.05F;
					this.ArmLeft01.rotateAngleZ = 2.44F;
					this.ArmLeft02.rotateAngleX = 0F;
					this.ArmLeft02.rotateAngleZ = 1.92F;
					this.ArmLeft02.offsetX = -0.32F;
					this.ArmLeft02.offsetZ = 0F;
				    this.ArmRight01.rotateAngleX = -1.13F;
			    	this.ArmRight01.rotateAngleY = 0.44F;
			    	this.ArmRight01.rotateAngleZ = 0.52F;
			    	this.ArmRight02.rotateAngleX = 0F;
			    	this.ArmRight02.rotateAngleZ = -0.52F;
					//equip
					this.EquipSL00.rotateAngleX = -0.3F;
					this.EquipSL00.rotateAngleY = -0.22F;
					this.EquipSL00.rotateAngleZ = 1.77F;
					this.EquipSR01.rotateAngleX = 0.81F;
			  	}
			  	else
			  	{
			  		this.ArmLeft01.rotateAngleX = -1.13F;
			    	this.ArmLeft01.rotateAngleY = -0.44F;
			    	this.ArmLeft01.rotateAngleZ = -0.52F;
			    	this.ArmLeft02.rotateAngleX = 0F;
			    	this.ArmLeft02.rotateAngleZ = 0.52F;
					this.ArmLeft02.offsetX = 0F;
					this.ArmLeft02.offsetZ = 0F;
				    this.ArmRight01.rotateAngleX = -1.13F;
			    	this.ArmRight01.rotateAngleY = 0.44F;
			    	this.ArmRight01.rotateAngleZ = 0.52F;
			    	this.ArmRight02.rotateAngleX = 0F;
			    	this.ArmRight02.rotateAngleZ = -0.52F;
					//equip
					this.EquipSL00.rotateAngleX = -0.2F;
					this.EquipSL00.rotateAngleY = -0.1F;
					this.EquipSL00.rotateAngleZ = 1.4F;
					this.EquipSR01.rotateAngleX = 0.81F;
			  	}
			  	
		    	//leg
		    	addk1 = -2.1F;
		    	addk2 = -2.1F;
		    	this.LegLeft01.rotateAngleY = -0.58F;
		    	this.LegLeft01.rotateAngleZ = 0.05F;
		    	this.LegLeft02.rotateAngleX = 2.44F;
		    	this.LegLeft02.offsetZ = 0.38F;
		    	this.LegRight01.rotateAngleY = 0.58F;
		    	this.LegRight01.rotateAngleZ = -0.05F;
		    	this.LegRight02.rotateAngleX = 2.44F;
		    	this.LegRight02.offsetZ = 0.38F;
			  	//skirt
			  	this.Skirt01.rotateAngleX = -0.17F;
			  	this.Skirt02.rotateAngleX = -0.26F;
	    	}
	    	else
	    	{
		    	GlStateManager.translate(0F, 0.46F, 0F);
		    	//body
			  	this.BodyMain.rotateAngleX = 0.08726646259971647F;
			  	this.Butt.rotateAngleX = -0.17453292519943295F;
		    	this.Head.rotateAngleX -= 0.2F;
		    	//arm
			  	this.ArmLeft01.rotateAngleX = 0.2617993877991494F;
			  	this.ArmLeft01.rotateAngleY = 0.0F;
			  	this.ArmLeft01.rotateAngleZ = -0.2617993877991494F;
			  	this.ArmLeft02.rotateAngleX = 0F;
			  	this.ArmLeft02.rotateAngleY = 0F;
			  	this.ArmLeft02.rotateAngleZ = 0F;
				this.ArmLeft02.offsetX = 0F;
				this.ArmLeft02.offsetZ = 0F;
			  	this.ArmRight01.rotateAngleX = -1.1344640137963142F;
			  	this.ArmRight01.rotateAngleY = 0.0F;
			  	this.ArmRight01.rotateAngleZ = 0.0F;
			  	this.ArmRight02.rotateAngleX = 0.0F;
			  	this.ArmRight02.rotateAngleZ = -1.2217304763960306F;
		    	//leg
		    	addk1 = -1.45F;
		    	addk2 = -2.1F;
			  	this.LegLeft01.rotateAngleX = -1.4486232791552935F;
			  	this.LegLeft01.rotateAngleY = 0.08726646259971647F;
			  	this.LegLeft01.rotateAngleZ = 0.0F;
		    	this.LegLeft02.rotateAngleX = 0F;
		    	this.LegLeft02.rotateAngleZ = 0F;
		    	this.LegRight01.rotateAngleX = -2.0943951023931953F;
		    	this.LegRight01.rotateAngleY = 0.091106186954104F;
		    	this.LegRight01.rotateAngleZ = 0.17453292519943295F;
		    	this.LegRight02.rotateAngleX = 1.3962634015954636F;
		    	this.LegRight02.rotateAngleZ = 0.0F;
			  	//skirt
			  	this.Skirt01.rotateAngleX = -0.17F;
			  	this.Skirt02.rotateAngleX = -0.26F;
			  	//equip
			  	this.EquipSL00.rotateAngleX = -1.6755160819145563F;
			  	this.EquipSL00.rotateAngleY = 0.17453292519943295F;
			  	this.EquipSL00.rotateAngleZ = 0.8726646259971648F;
			  	this.EquipSR01.rotateAngleX = 1.3089969389957472F;
			  	this.EquipSR01.rotateAngleY = -0.13962634015954636F;
			  	this.EquipSR01.rotateAngleZ = -0.13962634015954636F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作: 設為30~50會有揮刀動作, 設為100則沒有揮刀動作
	    if (ent.getAttackTick() > 30)
	    {
	    	//reset attack tick
	    	if (ent.getAttackTick() == 60) ent.setAttackTick(0);
	    	GlStateManager.translate(0F, 0.22F + ent.getScaleLevel() * 0.12F, 0F);
	    	//body
	    	this.Head.rotateAngleX = -0.4363323129985824F;
	    	this.Head.rotateAngleY = 0.0F;
	    	this.Head.rotateAngleZ = 0.0F;
	    	this.BodyMain.rotateAngleX = 1.0471975511965976F;
	    	this.BodyMain.rotateAngleY = 0.2617993877991494F;
	    	this.BodyMain.rotateAngleZ = 0.0F;
	    	this.Butt.rotateAngleX = -0.5235987755982988F;
	    	this.Butt.rotateAngleY = 0.0F;
	    	this.Butt.rotateAngleZ = 0.0F;
	    	//arm
	    	this.ArmLeft01.rotateAngleX = -0.7853981633974483F;
	    	this.ArmLeft01.rotateAngleY = 0.2617993877991494F;
	    	this.ArmLeft01.rotateAngleZ = 0.5235987755982988F;
	    	this.ArmLeft02.rotateAngleX = 0.0F;
	    	this.ArmLeft02.rotateAngleY = 0.0F;
	    	this.ArmLeft02.rotateAngleZ = 0.7853981633974483F;
			this.ArmLeft02.offsetX = 0F;
			this.ArmLeft02.offsetZ = 0F;
	    	this.ArmRight01.rotateAngleX = 0.5235987755982988F;
	    	this.ArmRight01.rotateAngleY = -0.3490658503988659F;
	    	this.ArmRight01.rotateAngleZ = 0.17453292519943295F;
	    	this.ArmRight02.rotateAngleX = -1.3089969389957472F;
	    	this.ArmRight02.rotateAngleY = 0.0F;
	    	this.ArmRight02.rotateAngleZ = 0.0F;
			this.ArmRight02.offsetX = 0F;
			this.ArmRight02.offsetZ = 0F;
	    	//leg
	    	addk1 = 0.31F;
	    	addk2 = -1.57F;
	    	this.LegLeft01.rotateAngleY = -0.17453292519943295F;
	    	this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
	    	this.LegLeft02.rotateAngleX = 0.13F;
	    	this.LegLeft02.rotateAngleY = 0.0F;
	    	this.LegLeft02.rotateAngleZ = 0.0F;
			this.LegLeft02.offsetX = 0F;
			this.LegLeft02.offsetZ = 0F;
	    	this.LegRight01.rotateAngleY = 0.0F;
	    	this.LegRight01.rotateAngleZ = 0.13962634015954636F;
	    	this.LegRight02.rotateAngleX = 1.2292353921796064F;
	    	this.LegRight02.rotateAngleY = 0.0F;
	    	this.LegRight02.rotateAngleZ = 0.0F;
			this.LegRight02.offsetX = 0F;
			this.LegRight02.offsetZ = 0F;
	    	//equip
			this.EquipSL00.isHidden = false;
	    	this.EquipSR01.rotateAngleX = 0.8651597102135892F;
	    	this.EquipSR01.rotateAngleY = -0.13962634015954636F;
	    	this.EquipSR01.rotateAngleZ = -0.13962634015954636F;
	    	this.EquipSL00.rotateAngleX = 1.593485607070823F;
	    	this.EquipSL00.rotateAngleY = 0.18203784098300857F;
	    	this.EquipSL00.rotateAngleZ = 1.5707963267948966F;
	    	
	    	//swing sword
	    	if (ent.getAttackTick() < 51)
	    	{
		    	if (ent.getAttackTick() > 45)
		    	{
			    	int tick = 4 - (ent.getAttackTick() - 46);
			    	float parTick = f2 - (int)f2 + tick;
		    		//arm
		    		this.ArmLeft01.rotateAngleX = -0.785F - 0.644F * parTick;
		    		this.ArmLeft02.rotateAngleZ = 0.785F - 0.157F * parTick;
			    	//equip
			    	this.EquipSL00.rotateAngleY = 0.182F + 0.278F * parTick;
		    	}
		    	else
		    	{
		    		//arm
		    		this.ArmLeft01.rotateAngleX = -4.1F;
		    		this.ArmLeft02.rotateAngleZ = 0F;
			    	//equip
			    	this.EquipSL00.rotateAngleY = 1.57F;
		    	}
	    	}
	    	
	    	//final attack
	    	if (ent.getStateEmotion(ID.S.Phase) == 3)
	    	{
	    		//body
		    	this.BodyMain.rotateAngleX = 2.1F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -1.92F;
		    	this.ArmLeft01.rotateAngleY = 0.4F;
		    	this.ArmLeft01.rotateAngleZ = 0.26F;
		    	this.ArmLeft02.rotateAngleZ = 0F;
		    	this.ArmRight01.rotateAngleX = -1.92F;
		    	this.ArmRight01.rotateAngleY = -0.4F;
		    	this.ArmRight01.rotateAngleZ = 0.26F;
		    	this.ArmRight02.rotateAngleX = 0F;
		    	//equip
		    	this.EquipSL00.rotateAngleX = -1.4F;
		    	this.EquipSL00.rotateAngleY = -0.14F;
		    	this.EquipSL00.rotateAngleZ = 1.57F;
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