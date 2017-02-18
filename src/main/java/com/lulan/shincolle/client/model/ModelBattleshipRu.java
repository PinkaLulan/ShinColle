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
 * ModelBattleshipRu - PinkaLulan 2017/1/17
 * Created using Tabula 5.1.0
 */
public class ModelBattleshipRu extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft01;
    public ModelRenderer EquipBase;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft02;
    public ModelRenderer Shoe01;
    public ModelRenderer LegRight02;
    public ModelRenderer Shoe02;
    public ModelRenderer ArmRight02;
    public ModelRenderer EquipRBase;
    public ModelRenderer EquipR01;
    public ModelRenderer EquipRC01a;
    public ModelRenderer EquipRC02a;
    public ModelRenderer EquipRC03a;
    public ModelRenderer EquipRC04a;
    public ModelRenderer EquipR02a;
    public ModelRenderer EquipR03a;
    public ModelRenderer EquipR04a;
    public ModelRenderer EquipR05a;
    public ModelRenderer EquipR06a;
    public ModelRenderer EquipR07;
    public ModelRenderer EquipR02b;
    public ModelRenderer EquipR03b;
    public ModelRenderer EquipR04b;
    public ModelRenderer EquipR05b;
    public ModelRenderer EquipR06b;
    public ModelRenderer EquipR08;
    public ModelRenderer EquipR09;
    public ModelRenderer EquipR10;
    public ModelRenderer EquipRC01b;
    public ModelRenderer EquipRC01c;
    public ModelRenderer EquipRC02b;
    public ModelRenderer EquipRC03b;
    public ModelRenderer EquipRC03c;
    public ModelRenderer ArmLeft02;
    public ModelRenderer EquipLBase;
    public ModelRenderer EquipL01;
    public ModelRenderer EquipLC01a;
    public ModelRenderer EquipLC02a;
    public ModelRenderer EquipLC03a;
    public ModelRenderer EquipLC04a;
    public ModelRenderer EquipL02a;
    public ModelRenderer EquipL03a;
    public ModelRenderer EquipL04a;
    public ModelRenderer EquipL05a;
    public ModelRenderer EquipL06a;
    public ModelRenderer EquipL07;
    public ModelRenderer EquipL02b;
    public ModelRenderer EquipL03b;
    public ModelRenderer EquipL04b;
    public ModelRenderer EquipL05b;
    public ModelRenderer EquipL06b;
    public ModelRenderer EquipL08;
    public ModelRenderer EquipL09;
    public ModelRenderer EquipL10;
    public ModelRenderer EquipLC01b;
    public ModelRenderer EquipLC01c;
    public ModelRenderer EquipLC02b;
    public ModelRenderer EquipLC03b;
    public ModelRenderer EquipLC03c;
    public ModelRenderer Equip01a;
    public ModelRenderer Equip01b;
    public ModelRenderer Equip02;
    public ModelRenderer Equip03a;
    public ModelRenderer EquipCB01;
    public ModelRenderer Equip03b;
    public ModelRenderer EquipCB03;
    public ModelRenderer EquipCB02a;
    public ModelRenderer EquipCB02b;
    public ModelRenderer EquipCB04a;
    public ModelRenderer EquipCB04b;
    public ModelRenderer GloveR;
    public ModelRenderer GloveL;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer Skirt01;
    

    public ModelBattleshipRu()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.scale = 0.44F;
        this.offsetY = 1.9F;
        this.offsetItem = new float[] {0.07F, 1F, -0.07F};
        this.offsetBlock = new float[] {0.07F, 1F, -0.07F};
        
        this.setDefaultFaceModel();
        
        this.GloveL = new ModelRenderer(this, 2, 34);
        this.GloveL.setRotationPoint(-3F, 0F, -3F);
        this.GloveL.addBox(-2.5F, 5.5F, -2.5F, 6, 7, 6, 0F);
        this.GloveR = new ModelRenderer(this, 2, 34);
        this.GloveR.setRotationPoint(-3F, 0F, -3F);
        this.GloveR.addBox(2.5F, 5.5F, -2.5F, 6, 7, 6, 0F);
        this.LegRight01 = new ModelRenderer(this, 0, 84);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.13962634015954636F, 0.0F, -0.08726646259971647F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipL04a = new ModelRenderer(this, 0, 0);
        this.EquipL04a.setRotationPoint(-7.5F, 1.0F, 1.2F);
        this.EquipL04a.addBox(0.0F, 0.0F, 0.0F, 1, 14, 16, 0.0F);
        this.setRotateAngle(EquipL04a, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipRC02a = new ModelRenderer(this, 0, 0);
        this.EquipRC02a.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.EquipRC02a.addBox(-4.0F, 0.0F, 0.0F, 8, 11, 9, 0.0F);
        this.EquipLC01a = new ModelRenderer(this, 0, 0);
        this.EquipLC01a.setRotationPoint(0.0F, 2.0F, -6.0F);
        this.EquipLC01a.addBox(-4.5F, 0.0F, 0.0F, 9, 12, 7, 0.0F);
        this.setRotateAngle(EquipLC01a, 0.13962634015954636F, 0.0F, 0.0F);
        this.EquipL06b = new ModelRenderer(this, 0, 0);
        this.EquipL06b.mirror = true;
        this.EquipL06b.setRotationPoint(9.6F, -3.3F, 24.2F);
        this.EquipL06b.addBox(-1.0F, 0.0F, 0.0F, 1, 14, 8, 0.0F);
        this.setRotateAngle(EquipL06b, 0.3490658503988659F, -0.6981317007977318F, -0.2617993877991494F);
        this.Shoe02 = new ModelRenderer(this, 0, 33);
        this.Shoe02.setRotationPoint(3.0F, 9.0F, 3.0F);
        this.Shoe02.addBox(-3.5F, 0.0F, -3.5F, 7, 6, 7, 0.0F);
        this.Neck = new ModelRenderer(this, 54, 121);
        this.Neck.setRotationPoint(0.0F, -10.3F, 0.5F);
        this.Neck.addBox(-2.5F, -2.0F, -3.6F, 5, 2, 5, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.EquipCB02a = new ModelRenderer(this, 13, 8);
        this.EquipCB02a.mirror = true;
        this.EquipCB02a.setRotationPoint(3.0F, 1.0F, 5.0F);
        this.EquipCB02a.addBox(-1.0F, -9.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(EquipCB02a, 0.5235987755982988F, 0.0F, 0.0F);
        this.EquipCB04b = new ModelRenderer(this, 11, 8);
        this.EquipCB04b.setRotationPoint(-3.0F, 1.0F, 5.0F);
        this.EquipCB04b.addBox(-1.0F, -9.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(EquipCB04b, 0.6108652381980153F, 0.0F, 0.0F);
        this.EquipLC02a = new ModelRenderer(this, 0, 0);
        this.EquipLC02a.setRotationPoint(0.0F, 1.5F, 0.0F);
        this.EquipLC02a.addBox(-4.0F, 0.0F, 0.0F, 8, 11, 9, 0.0F);
        this.Shoe01 = new ModelRenderer(this, 0, 33);
        this.Shoe01.setRotationPoint(-3.0F, 9.0F, 3.0F);
        this.Shoe01.addBox(-3.5F, 0.0F, -3.5F, 7, 6, 7, 0.0F);
        this.EquipR04a = new ModelRenderer(this, 0, 0);
        this.EquipR04a.setRotationPoint(-7.5F, 1.0F, 1.2F);
        this.EquipR04a.addBox(0.0F, 0.0F, 0.0F, 1, 14, 16, 0.0F);
        this.setRotateAngle(EquipR04a, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipL08 = new ModelRenderer(this, 0, 0);
        this.EquipL08.setRotationPoint(0.0F, -1.0F, -10.0F);
        this.EquipL08.addBox(-4.5F, 0.0F, 0.0F, 9, 13, 8, 0.0F);
        this.setRotateAngle(EquipL08, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipRC03b = new ModelRenderer(this, 0, 0);
        this.EquipRC03b.setRotationPoint(-1.8F, 10.0F, 3.5F);
        this.EquipRC03b.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipRC03b, 0.10471975511965977F, 0.0F, 0.0F);
        this.Equip03b = new ModelRenderer(this, 66, 0);
        this.Equip03b.setRotationPoint(-10.0F, -2.0F, 0.5F);
        this.Equip03b.addBox(-10.0F, 0.0F, 0.0F, 10, 5, 7, 0.0F);
        this.setRotateAngle(Equip03b, -0.4363323129985824F, 0.2617993877991494F, -0.7853981633974483F);
        this.LegLeft02 = new ModelRenderer(this, 0, 84);
        this.LegLeft02.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-6.0F, 0.0F, 0.0F, 6, 10, 6, 0.0F);
        this.EquipCB03 = new ModelRenderer(this, 66, 0);
        this.EquipCB03.mirror = true;
        this.EquipCB03.setRotationPoint(-10.0F, -4.0F, 1.0F);
        this.EquipCB03.addBox(-10.0F, 0.0F, 0.0F, 10, 5, 7, 0.0F);
        this.setRotateAngle(EquipCB03, -0.3490658503988659F, 0.3490658503988659F, -0.4363323129985824F);
        this.EquipR03a = new ModelRenderer(this, 0, 0);
        this.EquipR03a.setRotationPoint(-7.0F, -2.4F, -3.0F);
        this.EquipR03a.addBox(0.0F, 0.0F, 0.0F, 1, 17, 9, 0.0F);
        this.setRotateAngle(EquipR03a, -0.22689280275926282F, -0.13962634015954636F, 0.0F);
        this.EquipR02a = new ModelRenderer(this, 0, 0);
        this.EquipR02a.setRotationPoint(-5.0F, -4.0F, -9.0F);
        this.EquipR02a.addBox(-0.5F, 0.0F, 0.0F, 1, 21, 11, 0.0F);
        this.setRotateAngle(EquipR02a, -0.5235987755982988F, -0.17453292519943295F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.EquipCB01 = new ModelRenderer(this, 66, 0);
        this.EquipCB01.setRotationPoint(10.0F, -4.0F, 1.0F);
        this.EquipCB01.addBox(0.0F, 0.0F, 0.0F, 10, 5, 7, 0.0F);
        this.setRotateAngle(EquipCB01, -0.3490658503988659F, -0.3490658503988659F, 0.4363323129985824F);
        this.EquipR06a = new ModelRenderer(this, 0, 0);
        this.EquipR06a.setRotationPoint(-9.6F, -3.3F, 24.2F);
        this.EquipR06a.addBox(0.0F, 0.0F, 0.0F, 1, 14, 8, 0.0F);
        this.setRotateAngle(EquipR06a, 0.3490658503988659F, 0.6981317007977318F, 0.2617993877991494F);
        this.Skirt01 = new ModelRenderer(this, 46, 41);
        this.Skirt01.setRotationPoint(0.0F, 2.9F, 0.0F);
        this.Skirt01.addBox(-8.5F, 0.0F, -6.0F, 17, 4, 9, 0.0F);
        this.setRotateAngle(Skirt01, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipL04b = new ModelRenderer(this, 0, 0);
        this.EquipL04b.mirror = true;
        this.EquipL04b.setRotationPoint(7.5F, 1.0F, 1.2F);
        this.EquipL04b.addBox(-1.0F, 0.0F, 0.0F, 1, 14, 16, 0.0F);
        this.setRotateAngle(EquipL04b, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipL05b = new ModelRenderer(this, 0, 0);
        this.EquipL05b.mirror = true;
        this.EquipL05b.setRotationPoint(6.5F, 0.0F, 13.0F);
        this.EquipL05b.addBox(-1.0F, 0.0F, 0.0F, 1, 14, 12, 0.0F);
        this.setRotateAngle(EquipL05b, 0.2617993877991494F, 0.2617993877991494F, 0.0F);
        this.EquipLC01b = new ModelRenderer(this, 0, 0);
        this.EquipLC01b.setRotationPoint(-1.8F, 11.0F, 2.0F);
        this.EquipLC01b.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipLC01b, -0.05235987755982988F, 0.0F, 0.0F);
        this.EquipR04b = new ModelRenderer(this, 0, 0);
        this.EquipR04b.mirror = true;
        this.EquipR04b.setRotationPoint(7.5F, 1.0F, 1.2F);
        this.EquipR04b.addBox(-1.0F, 0.0F, 0.0F, 1, 14, 16, 0.0F);
        this.setRotateAngle(EquipR04b, 0.08726646259971647F, 0.0F, 0.0F);
        this.EquipLC03a = new ModelRenderer(this, 0, 0);
        this.EquipLC03a.setRotationPoint(0.0F, 1.0F, 9.0F);
        this.EquipLC03a.addBox(-4.5F, 0.0F, 0.0F, 9, 11, 7, 0.0F);
        this.setRotateAngle(EquipLC03a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipR02b = new ModelRenderer(this, 0, 0);
        this.EquipR02b.mirror = true;
        this.EquipR02b.setRotationPoint(5.0F, -4.0F, -9.0F);
        this.EquipR02b.addBox(-0.5F, 0.0F, 0.0F, 1, 21, 11, 0.0F);
        this.setRotateAngle(EquipR02b, -0.5235987755982988F, 0.17453292519943295F, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.EquipLC02b = new ModelRenderer(this, 0, 0);
        this.EquipLC02b.setRotationPoint(0.0F, 10.0F, 4.0F);
        this.EquipLC02b.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipLC02b, 0.03490658503988659F, 0.0F, 0.0F);
        this.EquipCB02b = new ModelRenderer(this, 13, 8);
        this.EquipCB02b.setRotationPoint(6.0F, 1.0F, 5.0F);
        this.EquipCB02b.addBox(-1.0F, -9.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(EquipCB02b, 0.8726646259971648F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 114, 45);
        this.Ahoke.setRotationPoint(0.7F, -6.0F, -7.5F);
        this.Ahoke.addBox(-1.5F, 0.0F, 0.0F, 5, 7, 0, 0.0F);
        this.setRotateAngle(Ahoke, -0.08726646259971647F, 0.0F, 0.136659280431156F);
        this.EquipR01 = new ModelRenderer(this, 0, 0);
        this.EquipR01.setRotationPoint(1.0F, -2.0F, 0.0F);
        this.EquipR01.addBox(-4.0F, 0.0F, 0.0F, 8, 5, 1, 0.0F);
        this.setRotateAngle(EquipR01, 0.0F, -1.5707963267948966F, 0.0F);
        this.HairR02 = new ModelRenderer(this, 88, 100);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 10.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 3, 0.0F);
        this.setRotateAngle(HairR02, 0.08726646259971647F, 0.0F, 0.08726646259971647F);
        this.EquipR09 = new ModelRenderer(this, 0, 0);
        this.EquipR09.setRotationPoint(0.0F, -0.2F, 20.6F);
        this.EquipR09.addBox(-6.0F, 0.0F, 0.0F, 12, 12, 8, 0.0F);
        this.setRotateAngle(EquipR09, 0.3141592653589793F, 0.0F, 0.0F);
        this.EquipL06a = new ModelRenderer(this, 0, 0);
        this.EquipL06a.setRotationPoint(-9.6F, -3.3F, 24.2F);
        this.EquipL06a.addBox(0.0F, 0.0F, 0.0F, 1, 14, 8, 0.0F);
        this.setRotateAngle(EquipL06a, 0.3490658503988659F, 0.6981317007977318F, 0.2617993877991494F);
        this.EquipR05a = new ModelRenderer(this, 0, 0);
        this.EquipR05a.setRotationPoint(-6.5F, 0.0F, 13.0F);
        this.EquipR05a.addBox(0.0F, 0.0F, 0.0F, 1, 14, 12, 0.0F);
        this.setRotateAngle(EquipR05a, 0.2617993877991494F, -0.2617993877991494F, 0.0F);
        this.Hair02 = new ModelRenderer(this, 0, 63);
        this.Hair02.setRotationPoint(0.0F, 12.5F, 6.5F);
        this.Hair02.addBox(-7.0F, 0.0F, -5.0F, 14, 12, 7, 0.0F);
        this.setRotateAngle(Hair02, -0.12217304763960307F, 0.0F, 0.0F);
        this.EquipRC02b = new ModelRenderer(this, 0, 0);
        this.EquipRC02b.setRotationPoint(0.0F, 10.0F, 4.0F);
        this.EquipRC02b.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipRC02b, 0.03490658503988659F, 0.0F, 0.0F);
        this.EquipRC01a = new ModelRenderer(this, 0, 0);
        this.EquipRC01a.setRotationPoint(0.0F, 2.0F, -6.0F);
        this.EquipRC01a.addBox(-4.5F, 0.0F, 0.0F, 9, 12, 7, 0.0F);
        this.setRotateAngle(EquipRC01a, 0.13962634015954636F, 0.0F, 0.0F);
        this.EquipR05b = new ModelRenderer(this, 0, 0);
        this.EquipR05b.mirror = true;
        this.EquipR05b.setRotationPoint(6.5F, 0.0F, 13.0F);
        this.EquipR05b.addBox(-1.0F, 0.0F, 0.0F, 1, 14, 12, 0.0F);
        this.setRotateAngle(EquipR05b, 0.2617993877991494F, 0.2617993877991494F, 0.0F);
        this.EquipRC01b = new ModelRenderer(this, 0, 0);
        this.EquipRC01b.setRotationPoint(-1.8F, 11.0F, 2.0F);
        this.EquipRC01b.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipRC01b, -0.05235987755982988F, 0.0F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 88, 100);
        this.HairL01.setRotationPoint(6.8F, 6.5F, -6.3F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.08726646259971647F, -0.07F, 0.05235987755982988F);
        this.EquipL10 = new ModelRenderer(this, 0, 0);
        this.EquipL10.setRotationPoint(6.7F, 1.0F, 14.0F);
        this.EquipL10.addBox(0.0F, 0.0F, 0.0F, 4, 8, 8, 0.0F);
        this.setRotateAngle(EquipL10, 0.17453292519943295F, 0.17453292519943295F, 0.0F);
        this.EquipL02a = new ModelRenderer(this, 0, 0);
        this.EquipL02a.setRotationPoint(-5.0F, -4.0F, -9.0F);
        this.EquipL02a.addBox(-0.5F, 0.0F, 0.0F, 1, 21, 11, 0.0F);
        this.setRotateAngle(EquipL02a, -0.5235987755982988F, -0.17453292519943295F, 0.0F);
        this.EquipRC03c = new ModelRenderer(this, 0, 0);
        this.EquipRC03c.setRotationPoint(1.8F, 10.0F, 3.5F);
        this.EquipRC03c.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipRC03c, 0.13962634015954636F, 0.0F, 0.0F);
        this.EquipLC03b = new ModelRenderer(this, 0, 0);
        this.EquipLC03b.setRotationPoint(-1.8F, 10.0F, 3.5F);
        this.EquipLC03b.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipLC03b, 0.10471975511965977F, 0.0F, 0.0F);
        this.Equip02 = new ModelRenderer(this, 4, 4);
        this.Equip02.setRotationPoint(0.0F, -7.0F, 3.0F);
        this.Equip02.addBox(-6.0F, 0.0F, 0.0F, 12, 5, 5, 0.0F);
        this.EquipRC03a = new ModelRenderer(this, 0, 0);
        this.EquipRC03a.setRotationPoint(0.0F, 1.0F, 9.0F);
        this.EquipRC03a.addBox(-4.5F, 0.0F, 0.0F, 9, 11, 7, 0.0F);
        this.setRotateAngle(EquipRC03a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipL07 = new ModelRenderer(this, 0, 0);
        this.EquipL07.setRotationPoint(0.0F, -4.0F, 29.0F);
        this.EquipL07.addBox(-5.0F, 0.0F, 0.0F, 10, 13, 13, 0.0F);
        this.setRotateAngle(EquipL07, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipCB04a = new ModelRenderer(this, 9, 4);
        this.EquipCB04a.setRotationPoint(-6.0F, 1.0F, 5.0F);
        this.EquipCB04a.addBox(-1.0F, -9.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(EquipCB04a, 0.7853981633974483F, 0.0F, 0.0F);
        this.BoobL = new ModelRenderer(this, 33, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.2F, -9.0F, -3.4F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, 0.08726646259971647F, 0.08726646259971647F);
        this.EquipLC03c = new ModelRenderer(this, 0, 0);
        this.EquipLC03c.setRotationPoint(1.8F, 10.0F, 3.5F);
        this.EquipLC03c.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipLC03c, 0.13962634015954636F, 0.0F, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, -9.0F, 1.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.HairL02 = new ModelRenderer(this, 88, 100);
        this.HairL02.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 3, 0.0F);
        this.setRotateAngle(HairL02, 0.12217304763960307F, 0.0F, -0.08726646259971647F);
        this.ArmRight01 = new ModelRenderer(this, 24, 84);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.4363323129985824F, 0.3490658503988659F);
        this.EquipR06b = new ModelRenderer(this, 0, 0);
        this.EquipR06b.mirror = true;
        this.EquipR06b.setRotationPoint(9.6F, -3.3F, 24.2F);
        this.EquipR06b.addBox(-1.0F, 0.0F, 0.0F, 1, 14, 8, 0.0F);
        this.setRotateAngle(EquipR06b, 0.3490658503988659F, -0.6981317007977318F, -0.2617993877991494F);
        this.LegLeft01 = new ModelRenderer(this, 0, 84);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.2792526803190927F, 0.0F, 0.08726646259971647F);
        this.EquipR10 = new ModelRenderer(this, 0, 0);
        this.EquipR10.setRotationPoint(-6.7F, 1.0F, 14.0F);
        this.EquipR10.addBox(-4.0F, 0.0F, 0.0F, 4, 8, 8, 0.0F);
        this.setRotateAngle(EquipR10, 0.17453292519943295F, -0.17453292519943295F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 84);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(0.0F, 0.0F, 0.0F, 6, 10, 6, 0.0F);
        this.EquipL09 = new ModelRenderer(this, 0, 0);
        this.EquipL09.setRotationPoint(0.0F, -0.2F, 20.6F);
        this.EquipL09.addBox(-6.0F, 0.0F, 0.0F, 12, 12, 8, 0.0F);
        this.setRotateAngle(EquipL09, 0.3141592653589793F, 0.0F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 84);
        this.ArmLeft01.setRotationPoint(7.8F, -9F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.22759093446006054F, -0.4363323129985824F, -0.3490658503988659F);
        this.Hair01 = new ModelRenderer(this, 50, 54);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 14, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.13962634015954636F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 84);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.HairR01 = new ModelRenderer(this, 88, 101);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-6.8F, 6.5F, -6.3F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.08726646259971647F, 0.07F, -0.05235987755982988F);
        this.EquipL01 = new ModelRenderer(this, 0, 0);
        this.EquipL01.setRotationPoint(1.0F, -2.0F, 0.0F);
        this.EquipL01.addBox(-4.0F, 0.0F, 0.0F, 8, 5, 1, 0.0F);
        this.setRotateAngle(EquipL01, 0.0F, -1.5707963267948966F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 84);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 47);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipLC04a = new ModelRenderer(this, 0, 0);
        this.EquipLC04a.setRotationPoint(0.0F, 2.0F, 12.5F);
        this.EquipLC04a.addBox(-5.0F, 0.0F, 0.0F, 10, 13, 11, 0.0F);
        this.setRotateAngle(EquipLC04a, 0.13962634015954636F, 0.0F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 15, 8, 0.0F);
        this.EquipR08 = new ModelRenderer(this, 0, 0);
        this.EquipR08.setRotationPoint(0.0F, -1.0F, -10.0F);
        this.EquipR08.addBox(-4.5F, 0.0F, 0.0F, 9, 13, 8, 0.0F);
        this.setRotateAngle(EquipR08, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipR07 = new ModelRenderer(this, 0, 0);
        this.EquipR07.setRotationPoint(0.0F, -4.0F, 29.0F);
        this.EquipR07.addBox(-5.0F, 0.0F, 0.0F, 10, 13, 13, 0.0F);
        this.setRotateAngle(EquipR07, -0.08726646259971647F, 0.0F, 0.0F);
        this.Equip03a = new ModelRenderer(this, 66, 0);
        this.Equip03a.setRotationPoint(10.0F, -2.0F, 0.5F);
        this.Equip03a.addBox(0.0F, 0.0F, 0.0F, 10, 5, 7, 0.0F);
        this.setRotateAngle(Equip03a, -0.4363323129985824F, -0.2617993877991494F, 0.7853981633974483F);
        this.EquipRC01c = new ModelRenderer(this, 0, 0);
        this.EquipRC01c.setRotationPoint(1.8F, 11.0F, 2.0F);
        this.EquipRC01c.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipRC01c, 0.03490658503988659F, 0.0F, 0.0F);
        this.EquipL05a = new ModelRenderer(this, 0, 0);
        this.EquipL05a.setRotationPoint(-6.5F, 0.0F, 13.0F);
        this.EquipL05a.addBox(0.0F, 0.0F, 0.0F, 1, 14, 12, 0.0F);
        this.setRotateAngle(EquipL05a, 0.2617993877991494F, -0.2617993877991494F, 0.0F);
        this.EquipR03b = new ModelRenderer(this, 46, 0);
        this.EquipR03b.mirror = true;
        this.EquipR03b.setRotationPoint(7.0F, -2.4F, -3.0F);
        this.EquipR03b.addBox(-1.0F, 0.0F, 0.0F, 1, 17, 9, 0.0F);
        this.setRotateAngle(EquipR03b, -0.22689280275926282F, 0.13962634015954636F, 0.0F);
        this.EquipLBase = new ModelRenderer(this, 0, 0);
        this.EquipLBase.setRotationPoint(-3.0F, 12.0F, -3.0F);
        this.EquipLBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.EquipL03a = new ModelRenderer(this, 46, 0);
        this.EquipL03a.setRotationPoint(-7.0F, -2.4F, -3.0F);
        this.EquipL03a.addBox(0.0F, 0.0F, 0.0F, 1, 17, 9, 0.0F);
        this.setRotateAngle(EquipL03a, -0.22689280275926282F, -0.13962634015954636F, 0.0F);
        this.Equip01b = new ModelRenderer(this, 66, 0);
        this.Equip01b.mirror = true;
        this.Equip01b.setRotationPoint(-6.0F, -3.0F, -5.5F);
        this.Equip01b.addBox(-10.0F, 0.0F, 0.0F, 10, 5, 7, 0.0F);
        this.setRotateAngle(Equip01b, 0.0F, 0.17453292519943295F, 0.3490658503988659F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-3.2F, -9.0F, -3.4F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, -0.08726646259971647F, -0.08726646259971647F);
        this.EquipL02b = new ModelRenderer(this, 0, 0);
        this.EquipL02b.mirror = true;
        this.EquipL02b.setRotationPoint(5.0F, -4.0F, -9.0F);
        this.EquipL02b.addBox(-0.5F, 0.0F, 0.0F, 1, 21, 11, 0.0F);
        this.setRotateAngle(EquipL02b, -0.5235987755982988F, 0.17453292519943295F, 0.0F);
        this.Equip01a = new ModelRenderer(this, 66, 0);
        this.Equip01a.setRotationPoint(6.0F, -3.0F, -5.5F);
        this.Equip01a.addBox(0.0F, 0.0F, 0.0F, 10, 5, 7, 0.0F);
        this.setRotateAngle(Equip01a, 0.0F, -0.17453292519943295F, -0.3490658503988659F);
        this.EquipRBase = new ModelRenderer(this, 0, 0);
        this.EquipRBase.setRotationPoint(2.0F, 12.0F, -3.0F);
        this.EquipRBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.EquipLC01c = new ModelRenderer(this, 0, 0);
        this.EquipLC01c.setRotationPoint(1.8F, 11.0F, 2.0F);
        this.EquipLC01c.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, 0.0F);
        this.setRotateAngle(EquipLC01c, 0.03490658503988659F, 0.0F, 0.0F);
        this.EquipL03b = new ModelRenderer(this, 0, 0);
        this.EquipL03b.mirror = true;
        this.EquipL03b.setRotationPoint(7.0F, -2.4F, -3.0F);
        this.EquipL03b.addBox(-1.0F, 0.0F, 0.0F, 1, 17, 9, 0.0F);
        this.setRotateAngle(EquipL03b, -0.22689280275926282F, 0.13962634015954636F, 0.0F);
        this.EquipRC04a = new ModelRenderer(this, 0, 0);
        this.EquipRC04a.setRotationPoint(0.0F, 2.0F, 12.5F);
        this.EquipRC04a.addBox(-5.0F, 0.0F, 0.0F, 10, 13, 11, 0.0F);
        this.setRotateAngle(EquipRC04a, 0.13962634015954636F, 0.0F, 0.0F);
        this.Butt.addChild(this.LegRight01);
        this.EquipLBase.addChild(this.EquipL04a);
        this.EquipRBase.addChild(this.EquipRC02a);
        this.EquipLBase.addChild(this.EquipLC01a);
        this.EquipLBase.addChild(this.EquipL06b);
        this.LegRight02.addChild(this.Shoe02);
        this.BodyMain.addChild(this.Neck);
        this.EquipCB01.addChild(this.EquipCB02a);
        this.EquipCB03.addChild(this.EquipCB04b);
        this.EquipLBase.addChild(this.EquipLC02a);
        this.LegLeft02.addChild(this.Shoe01);
        this.EquipRBase.addChild(this.EquipR04a);
        this.EquipLBase.addChild(this.EquipL08);
        this.EquipRC03a.addChild(this.EquipRC03b);
        this.Equip02.addChild(this.Equip03b);
        this.LegLeft01.addChild(this.LegLeft02);
        this.Equip02.addChild(this.EquipCB03);
        this.EquipRBase.addChild(this.EquipR03a);
        this.EquipRBase.addChild(this.EquipR02a);
        this.Neck.addChild(this.Head);
        this.Equip02.addChild(this.EquipCB01);
        this.EquipRBase.addChild(this.EquipR06a);
        this.Butt.addChild(this.Skirt01);
        this.EquipLBase.addChild(this.EquipL04b);
        this.EquipLBase.addChild(this.EquipL05b);
        this.EquipLC01a.addChild(this.EquipLC01b);
        this.EquipRBase.addChild(this.EquipR04b);
        this.EquipLBase.addChild(this.EquipLC03a);
        this.EquipRBase.addChild(this.EquipR02b);
        this.Head.addChild(this.HairMain);
        this.EquipLC02a.addChild(this.EquipLC02b);
        this.EquipCB01.addChild(this.EquipCB02b);
        this.Hair.addChild(this.Ahoke);
        this.EquipRBase.addChild(this.EquipR01);
        this.HairR01.addChild(this.HairR02);
        this.EquipRBase.addChild(this.EquipR09);
        this.EquipLBase.addChild(this.EquipL06a);
        this.EquipRBase.addChild(this.EquipR05a);
        this.Hair01.addChild(this.Hair02);
        this.EquipRC02a.addChild(this.EquipRC02b);
        this.EquipRBase.addChild(this.EquipRC01a);
        this.EquipRBase.addChild(this.EquipR05b);
        this.EquipRC01a.addChild(this.EquipRC01b);
        this.Hair.addChild(this.HairL01);
        this.EquipLBase.addChild(this.EquipL10);
        this.EquipLBase.addChild(this.EquipL02a);
        this.EquipRC03a.addChild(this.EquipRC03c);
        this.EquipLC03a.addChild(this.EquipLC03b);
        this.EquipBase.addChild(this.Equip02);
        this.EquipRBase.addChild(this.EquipRC03a);
        this.EquipLBase.addChild(this.EquipL07);
        this.EquipCB03.addChild(this.EquipCB04a);
        this.BodyMain.addChild(this.BoobL);
        this.EquipLC03a.addChild(this.EquipLC03c);
        this.BodyMain.addChild(this.EquipBase);
        this.HairL01.addChild(this.HairL02);
        this.BodyMain.addChild(this.ArmRight01);
        this.EquipRBase.addChild(this.EquipR06b);
        this.Butt.addChild(this.LegLeft01);
        this.EquipRBase.addChild(this.EquipR10);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipLBase.addChild(this.EquipL09);
        this.BodyMain.addChild(this.ArmLeft01);
        this.HairMain.addChild(this.Hair01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.Hair.addChild(this.HairR01);
        this.EquipLBase.addChild(this.EquipL01);
        this.ArmRight01.addChild(this.ArmRight02);
        this.BodyMain.addChild(this.Butt);
        this.EquipLBase.addChild(this.EquipLC04a);
        this.Head.addChild(this.Hair);
        this.EquipRBase.addChild(this.EquipR08);
        this.EquipRBase.addChild(this.EquipR07);
        this.Equip02.addChild(this.Equip03a);
        this.EquipRC01a.addChild(this.EquipRC01c);
        this.EquipLBase.addChild(this.EquipL05a);
        this.EquipRBase.addChild(this.EquipR03b);
        this.ArmLeft02.addChild(this.EquipLBase);
        this.EquipLBase.addChild(this.EquipL03a);
        this.EquipBase.addChild(this.Equip01b);
        this.BodyMain.addChild(this.BoobR);
        this.EquipLBase.addChild(this.EquipL02b);
        this.EquipBase.addChild(this.Equip01a);
        this.ArmRight02.addChild(this.EquipRBase);
        this.EquipLC01a.addChild(this.EquipLC01c);
        this.EquipLBase.addChild(this.EquipL03b);
        this.EquipRBase.addChild(this.EquipRC04a);
        this.ArmLeft02.addChild(this.GloveL);
        this.ArmRight02.addChild(this.GloveR);
        
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
  			this.GloveL.isHidden = false;
  			this.GloveR.isHidden = false;
  			this.EquipLBase.isHidden = true;
  			this.EquipRBase.isHidden = true;
  		break;
  		case ID.State.EQUIP01:
  			this.GloveL.isHidden = false;
  			this.GloveR.isHidden = false;
  			this.EquipLBase.isHidden = false;
  			this.EquipRBase.isHidden = false;
  		break;
  		default:  //normal
  			this.GloveL.isHidden = true;
  			this.GloveR.isHidden = true;
  			this.EquipLBase.isHidden = true;
  			this.EquipRBase.isHidden = true;
  		break;
  		}
  		
  		switch (ent.getStateEmotion(ID.S.State2))
  		{
  		case ID.State.EQUIP00a:
  			this.EquipBase.isHidden = false;
  		break;
  		default:  //normal
  			this.EquipBase.isHidden = true;
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
    	GlStateManager.translate(0F, 0.66F, 0F);
		this.setFaceHungry(ent);

		//body
    	this.Head.rotateAngleX = 0F;
    	this.Head.rotateAngleY = 0F;
    	this.Head.rotateAngleZ = 0F;
    	this.BodyMain.rotateAngleX = 1.4F;
    	this.Butt.rotateAngleX = 0.21F;
    	//arm
    	this.ArmLeft01.rotateAngleX = -2.9F;
    	this.ArmLeft01.rotateAngleY = 0F;
    	this.ArmLeft01.rotateAngleZ = 1.2F;
    	this.ArmLeft02.rotateAngleX = 0F;
    	this.ArmLeft02.rotateAngleZ = 0.6F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = 0F;
    	this.ArmRight01.rotateAngleX = -2.9F;
    	this.ArmRight01.rotateAngleY = 0F;
    	this.ArmRight01.rotateAngleZ = -1.2F;
	    this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = -0.6F;
		this.ArmRight02.offsetX = 0F;
		this.ArmRight02.offsetZ = 0F;
    	//leg
    	this.LegLeft01.rotateAngleX = -0.05F;
    	this.LegLeft01.rotateAngleY = 0F;
    	this.LegLeft01.rotateAngleZ = -0.4F;
    	this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0.8F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
    	this.LegRight01.rotateAngleX = -0.05F;
		this.LegRight01.rotateAngleY = 0F;
    	this.LegRight01.rotateAngleZ = 0.4F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = -0.8F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
		this.EquipLBase.rotateAngleX = 0.3F;
		this.EquipLBase.rotateAngleY = 1.8F;
		this.EquipLBase.rotateAngleZ = 0F;
		this.EquipRBase.rotateAngleX = 0.3F;
		this.EquipRBase.rotateAngleY = -1.8F;
		this.EquipRBase.rotateAngleZ = 0F;
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
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}

    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.28F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.5F - 0.21F;  //LegRight01
    	
  	    //head
	  	this.Head.rotateAngleX = f4 * 0.01745F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	    //boob
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.67F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.67F;
	  	//body
  	    this.Ahoke.rotateAngleZ = angleX * 0.03F + 0.3F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.35F;
		//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.14F + headX;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.12F + headX;
	  	this.Hair02.rotateAngleZ = 0F;
	    //arm
	  	if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
	  	{
	  		this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.3F;
	  		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.3F;
	  	}
	  	else
	  	{
	  		this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.15F;
	  		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.15F;
	  	}
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.4F + 0.1F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.4F;
	    this.ArmRight01.rotateAngleY = 0F;
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
		this.EquipLBase.rotateAngleX = 0F;
		this.EquipLBase.rotateAngleY = 0F;
		this.EquipLBase.rotateAngleZ = 0F;
		this.EquipRBase.rotateAngleX = 0F;
		this.EquipRBase.rotateAngleY = 0F;
		this.EquipRBase.rotateAngleZ = 0F;
		this.EquipCB02a.rotateAngleX = this.Head.rotateAngleX * 0.9F + 0.8F;
		this.EquipCB02b.rotateAngleX = this.Head.rotateAngleX * 0.8F + 0.9F;
		this.EquipCB04a.rotateAngleX = this.Head.rotateAngleX * 1.1F + 0.7F;
		this.EquipCB04b.rotateAngleX = this.Head.rotateAngleX * 0.9F + 0.8F;
		this.EquipLC01b.rotateAngleX = this.Head.rotateAngleX * 0.9F - 0.05F;
		this.EquipLC01c.rotateAngleX = this.Head.rotateAngleX * 0.8F - 0.08F;
		this.EquipLC02b.rotateAngleX = this.Head.rotateAngleX * 1.1F + 0.1F;
		this.EquipLC03b.rotateAngleX = this.Head.rotateAngleX * 0.9F + 0.05F;
		this.EquipLC03c.rotateAngleX = this.Head.rotateAngleX * 0.8F + 0.08F;
		this.EquipRC01b.rotateAngleX = this.Head.rotateAngleX * 0.9F - 0.05F;
		this.EquipRC01c.rotateAngleX = this.Head.rotateAngleX * 0.8F - 0.08F;
		this.EquipRC02b.rotateAngleX = this.Head.rotateAngleX * 1.1F + 0.1F;
		this.EquipRC03b.rotateAngleX = this.Head.rotateAngleX * 0.9F + 0.05F;
		this.EquipRC03c.rotateAngleX = this.Head.rotateAngleX * 0.8F + 0.08F;
		
		//special stand pos
		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED &&
			ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01 && t2 > 400)
		{
			spStand = true;
			
			setFace(1);
			GlStateManager.translate(0F, 0.12F, 0F);
			//body
			this.BodyMain.rotateAngleX = 1.0471975511965976F;
			this.BodyMain.rotateAngleY = 0.0F;
			this.BodyMain.rotateAngleZ = 0.0F;
			this.Head.rotateAngleX -= 0.18203784098300857F;
			//arm
			this.ArmLeft01.rotateAngleX = -1.0471975511965976F;
			this.ArmLeft01.rotateAngleY = 0.0F;
			this.ArmLeft01.rotateAngleZ = -0.3490658503988659F;
			this.ArmRight01.rotateAngleX = -1.0471975511965976F;
			this.ArmRight01.rotateAngleY = 0.0F;
			this.ArmRight01.rotateAngleZ = 0.3490658503988659F;
			//leg
			addk1 = -1.3962634015954636F;
			addk2 = -1.3962634015954636F;
			this.LegLeft01.rotateAngleY = 0.0F;
			this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
			this.LegRight01.rotateAngleY = 0.0F;
			this.LegRight01.rotateAngleZ = -0.08726646259971647F;
		}

	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {
	    	if (spStand) GlStateManager.translate(0F, -0.12F, 0F);
	    	
			//body
			this.BodyMain.rotateAngleX = -0.1F;
			this.BodyMain.rotateAngleY = 0F;
			this.BodyMain.rotateAngleZ = 0F;
		    //arm
		  	if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
		  	{
		  		this.ArmLeft01.rotateAngleX = angleAdd2 * 0.05F + 0.5F;
		  		this.ArmRight01.rotateAngleX = angleAdd1 * 0.05F + 0.5F;
		  	}
		  	else
		  	{
		  		this.ArmLeft01.rotateAngleX = angleAdd2 * 0.9F + 0.5F;
		  		this.ArmRight01.rotateAngleX = angleAdd1 * 0.9F + 0.5F;
		  	}
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
	  		addk1 = angleAdd1 * 0.7F - 0.28F;
		  	addk2 = angleAdd2 * 0.7F - 0.21F;
			this.LegLeft01.rotateAngleY = 0F;
			this.LegLeft01.rotateAngleZ = 0.0873F;
			this.LegRight01.rotateAngleY = 0F;
			this.LegRight01.rotateAngleZ = -0.0873F;
			//equip
			this.EquipLBase.rotateAngleX = 0.5F;
			this.EquipLBase.rotateAngleY = 0F;
			this.EquipLBase.rotateAngleZ = 0F;
			this.EquipRBase.rotateAngleX = 0.5F;
			this.EquipRBase.rotateAngleY = 0F;
			this.EquipRBase.rotateAngleZ = 0F;
  		}
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {
	    	if (spStand) GlStateManager.translate(0F, -0.12F, 0F);
	    	
	    	GlStateManager.translate(0F, 0.09F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		    //arm
		  	if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
		  	{
		  		this.ArmLeft01.rotateAngleX = angleAdd2 * 0.05F + 0.5F;
		  		this.ArmLeft01.rotateAngleZ = -0.25F;
		  		this.ArmLeft02.rotateAngleX = -1F;
		  		this.ArmRight01.rotateAngleX = angleAdd1 * 0.05F + 0.5F;
		  		this.ArmRight01.rotateAngleZ = 0.25F;
		  		this.ArmRight02.rotateAngleX = -1F;
		  	}
		  	else
		  	{
			  	this.ArmLeft01.rotateAngleX = -0.35F;
			    this.ArmLeft01.rotateAngleZ = 0.2618F;
			    this.ArmLeft02.rotateAngleX = 0F;
				this.ArmRight01.rotateAngleX = -0.35F;
				this.ArmRight01.rotateAngleZ = -0.2618F;
				this.ArmRight02.rotateAngleX = 0F;
		  	}
		  	this.ArmLeft01.rotateAngleY = 0F;
		    this.ArmLeft02.rotateAngleZ = 0F;
		    this.ArmLeft02.offsetX = 0F;
		    this.ArmLeft02.offsetZ = 0F;
		    this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight02.rotateAngleZ = 0F;
			this.ArmRight02.offsetX = 0F;
			this.ArmRight02.offsetZ = 0F;
			//leg
			addk1 -= 1.1F;
			addk2 -= 1.1F;
			//hair
			this.Hair01.rotateAngleX += 0.37F;
			this.Hair02.rotateAngleX += 0.23F;
  		}//end if sneaking
  		
	    //坐下動作
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {
	    	if (spStand) GlStateManager.translate(0F, -0.12F, 0F);
	    	
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
				GlStateManager.translate(0F, 0.25F, 0F);
				//body
				this.BodyMain.rotateAngleX = -0.10471975511965977F;
				this.BodyMain.rotateAngleY = -0.3490658503988659F;
				this.BodyMain.rotateAngleZ = 0.0F;
				this.Head.rotateAngleY -= 0.5235987755982988F;
				//arm
				this.ArmLeft01.rotateAngleX = 0.8726646259971648F;
				this.ArmLeft01.rotateAngleY = 0.0F;
				this.ArmLeft01.rotateAngleZ = -0.3490658503988659F;
				this.ArmLeft02.rotateAngleX = -0.7853981633974483F;
				this.ArmLeft02.rotateAngleY = 0.0F;
				this.ArmLeft02.rotateAngleZ = 0.0F;
				this.ArmRight01.rotateAngleX = -0.4363323129985824F;
				this.ArmRight01.rotateAngleY = 0.0F;
				this.ArmRight01.rotateAngleZ = 0.3490658503988659F;
				this.ArmRight02.rotateAngleX = -0.8726646259971648F;
				this.ArmRight02.rotateAngleY = 0.0F;
				this.ArmRight02.rotateAngleZ = 0.0F;
				//leg
				addk1 = -1.48352986419518F;
				addk2 = -0.4363323129985824F;
				this.LegLeft01.rotateAngleY = 0.0F;
				this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
				this.LegLeft02.rotateAngleX = 1.3962634015954636F;
				this.LegLeft02.rotateAngleY = 0.0F;
				this.LegLeft02.rotateAngleZ = 0.0F;
				this.LegRight01.rotateAngleY = 0.0F;
				this.LegRight01.rotateAngleZ = -0.08726646259971647F;
				this.LegRight02.rotateAngleX = 1.48352986419518F;
				this.LegRight02.rotateAngleY = 0.0F;
				this.LegRight02.rotateAngleZ = 0.0F;
	    	}
	    	else if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED &&
		    		 ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
	    	{
		    	GlStateManager.translate(0F, 0.52F, 0F);
		    	//body
		    	this.BodyMain.rotateAngleX = 0.7853981633974483F;
		    	this.Butt.rotateAngleX = 0.2617993877991494F;
		    	this.Head.rotateAngleX = 0.5235987755982988F;
		    	//hair
		    	this.Hair01.rotateAngleX = -0.3490658503988659F;
		    	this.Hair02.rotateAngleX = -0.12217304763960307F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = 2.6179938779914944F;
		    	this.ArmLeft01.rotateAngleY = 0.0F;
		    	this.ArmLeft01.rotateAngleZ = 0.0F;
		    	this.ArmRight01.rotateAngleX = 2.6179938779914944F;
		    	this.ArmRight01.rotateAngleY = 0.0F;
		    	this.ArmRight01.rotateAngleZ = 0.0F;
		    	//leg
		    	addk1 = 0.2617993877991494F;
		    	addk2 = 0.2617993877991494F;
		    	this.LegLeft01.rotateAngleY = 0.0F;
		    	this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
		    	this.LegLeft02.rotateAngleX = 0.2617993877991494F;
		    	this.LegLeft02.rotateAngleY = 0.0F;
		    	this.LegLeft02.rotateAngleZ = 0.0F;
		    	this.LegRight01.rotateAngleY = 0.0F;
		    	this.LegRight01.rotateAngleZ = -0.08726646259971647F;
		    	this.LegRight02.rotateAngleX = 0.2617993877991494F;
		    	this.LegRight02.rotateAngleY = 0.0F;
		    	this.LegRight02.rotateAngleZ = 0.0F;
		    	//equip
		    	this.EquipLBase.rotateAngleX = 1.2217304763960306F;
		    	this.EquipLBase.rotateAngleY = 0.0F;
		    	this.EquipLBase.rotateAngleZ = 0.0F;
		    	this.EquipRBase.rotateAngleX = 1.2217304763960306F;
		    	this.EquipRBase.rotateAngleY = 0.0F;
		    	this.EquipRBase.rotateAngleZ = 0.0F;
	    	}
	    	else if (!this.EquipLBase.isHidden)
	    	{
		    	GlStateManager.translate(0F, 0.2F, 0F);
		    	//body
		    	this.BodyMain.rotateAngleX = 0.18203784098300857F;
		    	this.Butt.rotateAngleX = 0.2617993877991494F;
		    	this.Head.rotateAngleX -= 0.20943951023931953F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = 0.13962634015954636F;
		    	this.ArmLeft01.rotateAngleY = 0.0F;
		    	this.ArmLeft01.rotateAngleZ = -0.3490658503988659F;
		    	this.ArmRight01.rotateAngleX = -1.1838568316277536F;
		    	this.ArmRight01.rotateAngleY = 0.8F;
		    	this.ArmRight01.rotateAngleZ = 0.0F;
		    	this.ArmRight02.rotateAngleX = -1.3089969389957472F;
		    	this.ArmRight02.rotateAngleY = 0.0F;
		    	this.ArmRight02.rotateAngleZ = 0.0F;
		    	//leg
		    	addk1 = -1.61F;
		    	addk2 = -1.57F;
		    	this.LegLeft01.rotateAngleY = 0.0F;
		    	this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
		    	this.LegLeft02.rotateAngleX = 1.5F;
		    	this.LegLeft02.rotateAngleY = 0.0F;
		    	this.LegLeft02.rotateAngleZ = 0.0F;
		    	this.LegRight01.rotateAngleY = 0.0F;
		    	this.LegRight01.rotateAngleZ = -0.08726646259971647F;
		    	this.LegRight02.rotateAngleX = 0.6F;
		    	this.LegRight02.rotateAngleY = 0.0F;
		    	this.LegRight02.rotateAngleZ = 0.0F;
		    	//equip
		    	this.EquipLBase.rotateAngleX = 0.0F;
		    	this.EquipLBase.rotateAngleY = -1.5707963267948966F;
		    	this.EquipLBase.rotateAngleZ = 0.3141592653589793F;
		    	this.EquipRBase.rotateAngleX = 0.7285004297824331F;
		    	this.EquipRBase.rotateAngleY = 0.0F;
		    	this.EquipRBase.rotateAngleZ = 0.0F;
	    	}
	    	else
	    	{
		    	GlStateManager.translate(0F, 0.35F, 0F);
		    	this.EquipLBase.isHidden = true;
		    	this.EquipRBase.isHidden = true;
		    	//body
		    	this.BodyMain.rotateAngleX = 0.27314402793711257F;
		    	this.Butt.rotateAngleX = 0.2617993877991494F;
		    	this.Head.rotateAngleX -= 0.41887902047863906F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = 0.091106186954104F;
		    	this.ArmLeft01.rotateAngleY = 0.0F;
		    	this.ArmLeft01.rotateAngleZ = -0.6373942428283291F;
		    	this.ArmLeft02.rotateAngleX = 0.0F;
		    	this.ArmLeft02.rotateAngleY = 0.0F;
		    	this.ArmLeft02.rotateAngleZ = 1.3658946726107624F;
		    	this.ArmRight01.rotateAngleX = -0.85F;
		    	this.ArmRight01.rotateAngleY = 0.0F;
		    	this.ArmRight01.rotateAngleZ = 0.0F;
		    	this.ArmRight02.rotateAngleX = 0.0F;
		    	this.ArmRight02.rotateAngleY = 0.0F;
		    	this.ArmRight02.rotateAngleZ = -0.5009094953223726F;
		    	//leg
		    	addk1 = -1.2747884856566583F;
		    	addk2 = -2.1399481958702475F;
		    	this.LegLeft01.rotateAngleY = 0.0F;
		    	this.LegLeft01.rotateAngleZ = 0.08726646259971647F;
		    	this.LegLeft02.rotateAngleX = 2.321986036853256F;
		    	this.LegLeft02.rotateAngleY = 0.0F;
		    	this.LegLeft02.rotateAngleZ = 0.0F;
		    	this.LegLeft02.offsetZ = 0.375F;
		    	this.LegRight01.rotateAngleY = 0.0F;
		    	this.LegRight01.rotateAngleZ = -0.08726646259971647F;
		    	this.LegRight02.rotateAngleX = 1.5707963267948966F;
		    	this.LegRight02.rotateAngleY = 0.0F;
		    	this.LegRight02.rotateAngleZ = 0.0F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作: 設為30~50會有揮刀動作, 設為100則沒有揮刀動作
	    if (ent.getAttackTick() > 0)
	    {
	    	if (spStand) GlStateManager.translate(0F, -0.12F, 0F);
	    	
	    	//body
		  	this.BodyMain.rotateAngleX = -0.1047F;
		  	this.BodyMain.rotateAngleY = 0F;
		  	this.BodyMain.rotateAngleZ = 0F;
		  	this.Butt.rotateAngleX = 0.35F;
	    	//arm
		  	if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP01)
		  	{
		  		this.ArmLeft02.rotateAngleX = -0.8726646259971648F;
		  		this.ArmRight02.rotateAngleX = -0.8726646259971648F;
		  	}
		  	else
		  	{
		  		this.ArmLeft02.rotateAngleX = 0F;
		  		this.ArmRight02.rotateAngleX = 0F;
		  	}
	    	this.ArmLeft01.rotateAngleX = -0.5235987755982988F;
	    	this.ArmLeft01.rotateAngleY = -0.5235987755982988F;
	    	this.ArmLeft01.rotateAngleZ = -0.2617993877991494F;
	    	this.ArmLeft02.rotateAngleY = 0.0F;
	    	this.ArmLeft02.rotateAngleZ = 0.0F;
	    	this.ArmRight01.rotateAngleX = -0.5235987755982988F;
	    	this.ArmRight01.rotateAngleY = 0.5235987755982988F;
	    	this.ArmRight01.rotateAngleZ = 0.2617993877991494F;
	    	this.ArmRight02.rotateAngleY = 0.0F;
	    	this.ArmRight02.rotateAngleZ = 0.0F;
	    	//leg move
	  		addk1 = angleAdd1 * 0.5F - 0.28F;
		  	addk2 = angleAdd2 * 0.5F - 0.21F;
	    	//equip
	    	this.EquipLBase.rotateAngleX = 0.0F;
	    	this.EquipLBase.rotateAngleY = -0.2617993877991494F;
	    	this.EquipLBase.rotateAngleZ = 0.3490658503988659F;
	    	this.EquipRBase.rotateAngleX = 0.0F;
	    	this.EquipRBase.rotateAngleY = 0.2617993877991494F;
	    	this.EquipRBase.rotateAngleZ = -0.3490658503988659F;
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
	  	
	  	//頭毛左右彎曲調整
	    headX = this.Head.rotateAngleX * -0.5F;
	    this.Hair01.rotateAngleX += headX;
	  	this.Hair02.rotateAngleX += headX;
		this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.09F;
	  	this.HairL02.rotateAngleX = -angleX1 * 0.04F + headX + 0.12F;
	  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.09F;
	  	this.HairR02.rotateAngleX = -angleX1 * 0.04F + headX + 0.12F;
	  	headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	this.HairL01.rotateAngleZ = headZ + 0.05F;
	  	this.HairL02.rotateAngleZ = headZ - 0.09F;
	  	this.HairR01.rotateAngleZ = headZ - 0.05F;
	  	this.HairR02.rotateAngleZ = headZ + 0.09F;
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
	
}