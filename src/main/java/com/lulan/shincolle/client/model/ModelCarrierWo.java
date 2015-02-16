package com.lulan.shincolle.client.model;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.AttrValues;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * EntityCarrierWO - PinkaLulan 2015/2/15
 * Created using Tabula 4.1.1
 */
public class ModelCarrierWo extends ModelBase {
    public ModelRenderer BodyMain;
    public ModelRenderer Butt;
    public ModelRenderer ArmLeft;
    public ModelRenderer ArmRight;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer CloakNeck;
    public ModelRenderer LegRight;
    public ModelRenderer LegLeft;
    public ModelRenderer ShoesRight;
    public ModelRenderer ShoesLeft;
    public ModelRenderer Staff;
    public ModelRenderer StaffHead;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer EquipBase;
    public ModelRenderer Equip01;
    public ModelRenderer Equip02;
    public ModelRenderer Equip03;
    public ModelRenderer Equip04;
    public ModelRenderer EquipEye01;
    public ModelRenderer EquipEye02;
    public ModelRenderer EquipT01L;
    public ModelRenderer EquipT01R;
    public ModelRenderer Equip05;
    public ModelRenderer Equip06;
    public ModelRenderer EquipLC01;
    public ModelRenderer EquipRC01;
    public ModelRenderer EquipTB01L;
    public ModelRenderer EquipTB01R;
    public ModelRenderer EquipTooth01;
    public ModelRenderer EquipTooth02;
    public ModelRenderer EquipTooth03;
    public ModelRenderer EquipT02L;
    public ModelRenderer EquipT03L;
    public ModelRenderer EquipT02R;
    public ModelRenderer EquipT03R;
    public ModelRenderer EquipLC02;
    public ModelRenderer EquipLC03;
    public ModelRenderer EquipRC02;
    public ModelRenderer EquipRC03;
    public ModelRenderer EquipTB02L;
    public ModelRenderer EquipTB03L;
    public ModelRenderer EquipTB02R;
    public ModelRenderer EquipTB03R;
    public ModelRenderer Cloak01;
    public ModelRenderer Cloak02;
    public ModelRenderer Cloak03;
    
    public int HeadCooldown = 0;
    public boolean HeadTilt = false;

    public ModelCarrierWo() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        
        this.Head = new ModelRenderer(this, 43, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 1.5F);
        this.Head.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 13, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipLC02 = new ModelRenderer(this, 128, 0);
        this.EquipLC02.setRotationPoint(-1.0F, -2.0F, -7.0F);
        this.EquipLC02.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.setRotateAngle(EquipLC02, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipTB03R = new ModelRenderer(this, 21, 56);
        this.EquipTB03R.setRotationPoint(0.0F, 13.5F, 0.0F);
        this.EquipTB03R.addBox(-2.0F, -2.0F, -2.0F, 4, 15, 4, 0.0F);
        this.setRotateAngle(EquipTB03R, 0.6981317007977318F, 0.0F, -0.7853981633974483F);
        this.BoobL = new ModelRenderer(this, 26, 26);
        this.BoobL.setRotationPoint(2.6F, -9.0F, -3.2F);
        this.BoobL.addBox(-4.0F, 0.0F, -1.0F, 8, 6, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.7853981633974483F, -0.08726646259971647F, -0.17453292519943295F);
        this.EquipLC03 = new ModelRenderer(this, 128, 0);
        this.EquipLC03.setRotationPoint(0.0F, 2.0F, -7.0F);
        this.EquipLC03.addBox(-1.5F, -1.5F, -16.0F, 3, 3, 16, 0.0F);
        this.setRotateAngle(EquipLC03, 0.10471975511965977F, 0.0F, 0.0F);
        this.EquipT02L = new ModelRenderer(this, 21, 56);
        this.EquipT02L.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.EquipT02L.addBox(-3.0F, -2.0F, -3.0F, 6, 22, 6, 0.0F);
        this.setRotateAngle(EquipT02L, -0.17453292519943295F, 0.0F, -0.2617993877991494F);
        this.ShoesRight = new ModelRenderer(this, 0, 109);
        this.ShoesRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ShoesRight.addBox(-4.0F, 16.5F, -4.0F, 8, 9, 8, 0.0F);
        this.Equip05 = new ModelRenderer(this, 104, 0);
        this.Equip05.setRotationPoint(0.0F, -5.0F, 2.5F);
        this.Equip05.addBox(-24.0F, -18.0F, -15.0F, 48, 18, 28, 0.0F);
        this.setRotateAngle(Equip05, 0.03490658503988659F, 0.0F, 0.0F);
        this.EquipTB03L = new ModelRenderer(this, 21, 56);
        this.EquipTB03L.setRotationPoint(0.0F, 13.5F, 0.0F);
        this.EquipTB03L.addBox(-2.0F, -2.0F, -2.0F, 4, 15, 4, 0.0F);
        this.setRotateAngle(EquipTB03L, 0.6981317007977318F, 0.0F, 0.7853981633974483F);
        this.Equip03 = new ModelRenderer(this, 112, 0);
        this.Equip03.setRotationPoint(0.0F, -5.5F, 4.0F);
        this.Equip03.addBox(-16.0F, -18.0F, -20.0F, 32, 18, 40, 0.0F);
        this.setRotateAngle(Equip03, 0.06981317007977318F, 0.0F, 0.0F);
        this.Hair = new ModelRenderer(this, 128, 62);
        this.Hair.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -8.0F, 16, 20, 16, 0.0F);
        this.EquipT03L = new ModelRenderer(this, 21, 56);
        this.EquipT03L.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.EquipT03L.addBox(-2.5F, -2.0F, -2.5F, 5, 20, 5, 0.0F);
        this.setRotateAngle(EquipT03L, 1.0471975511965976F, 0.0F, 0.7853981633974483F);
        this.LegLeft = new ModelRenderer(this, 0, 85);
        this.LegLeft.mirror = true;
        this.LegLeft.setRotationPoint(5.0F, 5.0F, -0.5F);
        this.LegLeft.addBox(-3.5F, 0.0F, -3.5F, 7, 17, 7, 0.0F);
        this.setRotateAngle(LegLeft, 0.0F, 0.0F, 0.05235987755982988F);
        this.EquipTooth03 = new ModelRenderer(this, 128, 99);
        this.EquipTooth03.mirror = true;
        this.EquipTooth03.setRotationPoint(-12.4F, -17.0F, -20.3F);
        this.EquipTooth03.addBox(-14.0F, 0.0F, 0.0F, 14, 8, 4, 0.0F);
        this.setRotateAngle(EquipTooth03, 0.06981317007977318F, 0.5235987755982988F, -0.05235987755982988F);
        this.EquipRC02 = new ModelRenderer(this, 128, 0);
        this.EquipRC02.setRotationPoint(1.0F, -2.0F, -7.0F);
        this.EquipRC02.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.setRotateAngle(EquipRC02, -0.10471975511965977F, 0.0F, 0.0F);
        this.ShoesLeft = new ModelRenderer(this, 0, 109);
        this.ShoesLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ShoesLeft.addBox(-4.0F, 16.5F, -4.0F, 8, 9, 8, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, -10.0F, -3.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipBase, 0.08726646259971647F, 0.0F, 0.0F);
        this.Equip04 = new ModelRenderer(this, 112, 0);
        this.Equip04.setRotationPoint(0.0F, -7.0F, 5.5F);
        this.Equip04.addBox(-12.0F, -15.0F, -24.0F, 24, 15, 46, 0.0F);
        this.setRotateAngle(Equip04, 0.10471975511965977F, 0.0F, 0.0F);
        this.EquipLC01 = new ModelRenderer(this, 128, 0);
        this.EquipLC01.setRotationPoint(30.0F, -7.0F, 4.0F);
        this.EquipLC01.addBox(-3.5F, -5.5F, -7.5F, 7, 11, 15, 0.0F);
        this.setRotateAngle(EquipLC01, -0.17453292519943295F, -0.2617993877991494F, 0.17453292519943295F);
        this.EquipTB01L = new ModelRenderer(this, 128, 0);
        this.EquipTB01L.setRotationPoint(15.0F, -6.0F, 10.0F);
        this.EquipTB01L.addBox(-3.0F, -2.0F, -3.0F, 6, 10, 6, 0.0F);
        this.setRotateAngle(EquipTB01L, 0.17453292519943295F, 0.0F, -0.3490658503988659F);
        this.EquipEye01 = new ModelRenderer(this, 44, 0);
        this.EquipEye01.setRotationPoint(-14.5F, -21F, -8.0F);
        this.EquipEye01.addBox(-7.5F, -6.0F, 0.0F, 15, 6, 14, 0.0F);
        this.setRotateAngle(EquipEye01, 0.13962634015954636F, 0.13962634015954636F, -0.2617993877991494F);
        this.EquipEye02 = new ModelRenderer(this, 44, 0);
        this.EquipEye02.setRotationPoint(14.5F, -21F, -8.0F);
        this.EquipEye02.addBox(-7.5F, -6.0F, 0.0F, 15, 6, 14, 0.0F);
        this.setRotateAngle(EquipEye02, 0.13962634015954636F, -0.13962634015954636F, 0.2617993877991494F);
        this.StaffHead = new ModelRenderer(this, 52, 20);
        this.StaffHead.setRotationPoint(-0.5F, -15.0F, -1.0F);
        this.StaffHead.addBox(0.0F, -13.0F, 0.0F, 4, 13, 8, 0.0F);
        this.Equip06 = new ModelRenderer(this, 96, 0);
        this.Equip06.setRotationPoint(0.0F, -7.0F, 4.5F);
        this.Equip06.addBox(-29.0F, -13.0F, -13.0F, 58, 13, 22, 0.0F);
        this.setRotateAngle(Equip06, 0.06981317007977318F, 0.0F, 0.0F);
        this.EquipT02R = new ModelRenderer(this, 21, 56);
        this.EquipT02R.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.EquipT02R.addBox(-3.0F, -2.0F, -3.0F, 6, 22, 6, 0.0F);
        this.setRotateAngle(EquipT02R, -0.17453292519943295F, 0.0F, 0.2617993877991494F);
        this.Equip02 = new ModelRenderer(this, 120, 0);
        this.Equip02.setRotationPoint(0.0F, -3.0F, 2.0F);
        this.Equip02.addBox(-18.0F, -22.0F, -15.0F, 36, 22, 32, 0.0F);
        this.setRotateAngle(Equip02, 0.03490658503988659F, 0.0F, 0.0F);
        this.EquipTB02L = new ModelRenderer(this, 21, 56);
        this.EquipTB02L.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.EquipTB02L.addBox(-2.5F, -2.0F, -2.5F, 5, 16, 5, 0.0F);
        this.setRotateAngle(EquipTB02L, 0.4363323129985824F, 0.0F, -0.3490658503988659F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipT01R = new ModelRenderer(this, 128, 0);
        this.EquipT01R.setRotationPoint(-17.0F, -7.0F, -8.0F);
        this.EquipT01R.addBox(-4.0F, 0.0F, -4.0F, 8, 10, 8, 0.0F);
        this.setRotateAngle(EquipT01R, -0.2617993877991494F, 0.0F, 0.2617993877991494F);
        this.EquipTB01R = new ModelRenderer(this, 128, 0);
        this.EquipTB01R.setRotationPoint(-15.0F, -6.0F, 10.0F);
        this.EquipTB01R.addBox(-3.0F, -2.0F, -3.0F, 6, 10, 6, 0.0F);
        this.setRotateAngle(EquipTB01R, 0.17453292519943295F, 0.0F, 0.3490658503988659F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipRC01 = new ModelRenderer(this, 128, 0);
        this.EquipRC01.setRotationPoint(-30.0F, -7.0F, 4.0F);
        this.EquipRC01.addBox(-3.5F, -5.5F, -7.5F, 7, 11, 15, 0.0F);
        this.setRotateAngle(EquipRC01, -0.17453292519943295F, 0.2617993877991494F, -0.17453292519943295F);
        this.EquipT03R = new ModelRenderer(this, 21, 56);
        this.EquipT03R.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.EquipT03R.addBox(-2.5F, -2.0F, -2.5F, 5, 20, 5, 0.0F);
        this.setRotateAngle(EquipT03R, 1.0471975511965976F, 0.0F, -0.7853981633974483F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipTooth02 = new ModelRenderer(this, 128, 99);
        this.EquipTooth02.setRotationPoint(12.4F, -17.0F, -20.3F);
        this.EquipTooth02.addBox(0.0F, 0.0F, 0.0F, 14, 8, 4, 0.0F);
        this.setRotateAngle(EquipTooth02, 0.10471975511965977F, -0.5235987755982988F, 0.05235987755982988F);
        this.EquipTB02R = new ModelRenderer(this, 21, 56);
        this.EquipTB02R.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.EquipTB02R.addBox(-2.5F, -2.0F, -2.5F, 5, 16, 5, 0.0F);
        this.setRotateAngle(EquipTB02R, 0.4363323129985824F, 0.0F, 0.3490658503988659F);
        this.Cloak01 = new ModelRenderer(this, 200, 73);
        this.Cloak01.setRotationPoint(0.0F, 3.0F, 6.0F);
        this.Cloak01.addBox(-12.0F, 0.0F, 0.0F, 24, 15, 0, 0.0F);
        this.setRotateAngle(Cloak01, 0.7853981633974483F, 0.0F, 0.0F);
        this.CloakNeck = new ModelRenderer(this, 128, 0);
        this.CloakNeck.setRotationPoint(0.0F, -13.0F, -1.5F);
        this.CloakNeck.addBox(-11.0F, 0.0F, -6.0F, 22, 4, 12, 0.0F);
        this.setRotateAngle(CloakNeck, 0.17453292519943295F, 0.0F, 0.0F);
        this.ArmRight = new ModelRenderer(this, 0, 54);
        this.ArmRight.setRotationPoint(-7.0F, -10.0F, 0.0F);
        this.ArmRight.addBox(-5.0F, 0.0F, -3.0F, 5, 25, 5, 0.0F);
        this.setRotateAngle(ArmRight, -0.6981317007977318F, -0.5235987755982988F, 0.0F);
        this.LegRight = new ModelRenderer(this, 0, 85);
        this.LegRight.setRotationPoint(-5.0F, 5.0F, -0.5F);
        this.LegRight.addBox(-3.5F, 0.0F, -3.5F, 7, 17, 7, 0.0F);
        this.setRotateAngle(LegRight, 0.0F, 0.0F, -0.05235987755982988F);
        this.EquipT01L = new ModelRenderer(this, 128, 0);
        this.EquipT01L.setRotationPoint(17.0F, -7.0F, -8.0F);
        this.EquipT01L.addBox(-4.0F, 0.0F, -4.0F, 8, 10, 8, 0.0F);
        this.setRotateAngle(EquipT01L, -0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, -11.0F, 0.0F);
        this.BodyMain.addBox(-7.0F, -12.0F, -4.0F, 14, 18, 8, 0.0F);
        this.setRotateAngle(BodyMain, -0.3490658503988659F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 52, 41);
        this.Neck.setRotationPoint(0.0F, -12.0F, -2.0F);
        this.Neck.addBox(-7.5F, -1.5F, -6.0F, 15, 3, 13, 0.0F);
        this.setRotateAngle(Neck, 0.3490658503988659F, 0.0F, 0.0F);
        this.Equip01 = new ModelRenderer(this, 128, 0);
        this.Equip01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Equip01.addBox(-9.0F, -28.5F, -7.0F, 18, 27, 22, 0.0F);
        this.setRotateAngle(Equip01, 0.0873F, 0.0F, 0.0F);
        this.ArmLeft = new ModelRenderer(this, 0, 54);
        this.ArmLeft.mirror = true;
        this.ArmLeft.setRotationPoint(7.0F, -10.0F, 0.0F);
        this.ArmLeft.addBox(0.0F, 0.0F, -3.0F, 5, 25, 5, 0.0F);
        this.setRotateAngle(ArmLeft, -0.6981317007977318F, 0.5235987755982988F, 0.0F);
        this.Staff = new ModelRenderer(this, 128, 0);
        this.Staff.setRotationPoint(8.0F, 35.0F, 21.0F);
        this.Staff.addBox(0.0F, -15.0F, 0.0F, 3, 28, 4, 0.0F);
        this.setRotateAngle(Staff, 1.1838568316277536F, -0.18203784098300857F, -1.2292353921796064F);
        this.Cloak02 = new ModelRenderer(this, 196, 88);
        this.Cloak02.setRotationPoint(0.0F, 15.0F, 0.0F);
        this.Cloak02.addBox(-14.0F, 0.0F, 0.0F, 28, 20, 0, 0.0F);
        this.setRotateAngle(Cloak02, -0.4553564018453205F, 0.0F, 0.0F);
        this.EquipRC03 = new ModelRenderer(this, 128, 0);
        this.EquipRC03.setRotationPoint(0.0F, 2.0F, -7.0F);
        this.EquipRC03.addBox(-1.5F, -1.5F, -16.0F, 3, 3, 16, 0.0F);
        this.setRotateAngle(EquipRC03, 0.10471975511965977F, 0.0F, 0.0F);
        this.EquipTooth01 = new ModelRenderer(this, 128, 112);
        this.EquipTooth01.setRotationPoint(0.0F, -19.3F, -20.6F);
        this.EquipTooth01.addBox(-12.0F, 0.0F, 0.0F, 24, 15, 1, 0.0F);
        this.setRotateAngle(EquipTooth01, 0.10471975511965977F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 0, 26);
        this.BoobR.setRotationPoint(-2.6F, -9.0F, -3.2F);
        this.BoobR.addBox(-4.0F, 0.0F, -1.0F, 8, 6, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.7853981633974483F, 0.08726646259971647F, 0.17453292519943295F);
        this.Butt = new ModelRenderer(this, 0, 37);
        this.Butt.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.Butt.addBox(-8.5F, -2.0F, -4.1F, 17, 8, 9, 0.0F);
        this.setRotateAngle(Butt, 0.5235987755982988F, 0.0F, 0.0F);
        this.Cloak03 = new ModelRenderer(this, 192, 108);
        this.Cloak03.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.Cloak03.addBox(-16.0F, 0.0F, 0.0F, 32, 20, 0, 0.0F);
        this.setRotateAngle(Cloak03, 0.3490658503988659F, 0.0F, 0.0F);
        this.Neck.addChild(this.Head);
        this.Head.addChild(this.Face4);
        this.EquipLC01.addChild(this.EquipLC02);
        this.EquipTB02R.addChild(this.EquipTB03R);
        this.BodyMain.addChild(this.BoobL);
        this.EquipLC01.addChild(this.EquipLC03);
        this.EquipT01L.addChild(this.EquipT02L);
        this.LegRight.addChild(this.ShoesRight);
        this.EquipBase.addChild(this.Equip05);
        this.EquipTB02L.addChild(this.EquipTB03L);
        this.EquipBase.addChild(this.Equip03);
        this.Head.addChild(this.Hair);
        this.EquipT02L.addChild(this.EquipT03L);
        this.Butt.addChild(this.LegLeft);
        this.EquipBase.addChild(this.EquipTooth03);
        this.EquipRC01.addChild(this.EquipRC02);
        this.LegLeft.addChild(this.ShoesLeft);
        this.Head.addChild(this.EquipBase);
        this.EquipBase.addChild(this.Equip04);
        this.EquipBase.addChild(this.EquipLC01);
        this.EquipBase.addChild(this.EquipTB01L);
        this.EquipBase.addChild(this.EquipEye01);
        this.EquipBase.addChild(this.EquipEye02);
        this.Staff.addChild(this.StaffHead);
        this.EquipBase.addChild(this.Equip06);
        this.EquipT01R.addChild(this.EquipT02R);
        this.EquipBase.addChild(this.Equip02);
        this.EquipTB01L.addChild(this.EquipTB02L);
        this.Head.addChild(this.Face2);
        this.EquipBase.addChild(this.EquipT01R);
        this.EquipBase.addChild(this.EquipTB01R);
        this.Head.addChild(this.Face1);
        this.EquipBase.addChild(this.EquipRC01);
        this.EquipT02R.addChild(this.EquipT03R);
        this.Head.addChild(this.Face3);
        this.EquipBase.addChild(this.EquipTooth02);
        this.EquipTB01R.addChild(this.EquipTB02R);
        this.CloakNeck.addChild(this.Cloak01);
        this.BodyMain.addChild(this.CloakNeck);
        this.BodyMain.addChild(this.ArmRight);
        this.Butt.addChild(this.LegRight);
        this.EquipBase.addChild(this.EquipT01L);
        this.BodyMain.addChild(this.Neck);
        this.EquipBase.addChild(this.Equip01);
        this.BodyMain.addChild(this.ArmLeft);
        this.ArmRight.addChild(this.Staff);
        this.Cloak01.addChild(this.Cloak02);
        this.EquipRC01.addChild(this.EquipRC03);
        this.EquipBase.addChild(this.EquipTooth01);
        this.BodyMain.addChild(this.BoobR);
        this.BodyMain.addChild(this.Butt);
        this.Cloak02.addChild(this.Cloak03);
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {    
        GL11.glPushMatrix();
    	GL11.glEnable(GL11.GL_BLEND);			//開啟透明度模式
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glTranslatef(0F, 0.8F, 0F);
    	GL11.glScalef(0.5F, 0.5F, 0.5F); 	
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	
    	this.BodyMain.render(f5);
    	GL11.glDisable(GL11.GL_BLEND);			//開啟透明度模式
    	GL11.glPopMatrix();
    }
    
    //for idle/run animation
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  
      BasicEntityShip ent = (BasicEntityShip)entity;
      
      showEquip(ent);
      
      rollEmotion(ent);
      
      motionHumanPos(f, f1, f2, f3, f4, ent);

    }
    
    //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, BasicEntityShip ent) {   
  		float angleZ = MathHelper.cos(f2*0.08F);
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		//leg move parm
  		addk1 = MathHelper.cos(f * 0.4F) * 0.5F * f1;
	  	addk2 = MathHelper.cos(f * 0.4F + 3.1415927F) * 0.5F * f1;

  	    //移動頭部 使其看人, 不看人時持續擺動頭部
	    this.Neck.rotateAngleY = f3 / 57.29578F;	//左右角度 角度轉成rad 即除以57.29578
	    this.Neck.rotateAngleX = (f4 / 57.29578F) * 0.7F; 	//上下角度
//	    this.Neck.rotateAngleX = 0F;
	    
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = -angleZ * 0.06F - 0.73F;
  	    this.BoobR.rotateAngleX = -angleZ * 0.06F - 0.73F;
  	    //手臂晃動 
	  	this.ArmLeft.rotateAngleX = -0.26F;
  	    this.ArmRight.rotateAngleX = -0.26F;
	    this.ArmLeft.rotateAngleY = 0F;
		this.ArmRight.rotateAngleY = 0F;
		this.ArmLeft.rotateAngleZ = 0.26F;
		this.ArmRight.rotateAngleZ = -0.26F;
		//身體角度
		this.BodyMain.rotateAngleX = -0.1745F;
		this.BodyMain.rotateAngleY = 0F;
		this.BodyMain.rotateAngleZ = 0F;
		//腿擺動
		addk1 += -0.349F;
		addk2 += -0.349F;
		this.LegLeft.rotateAngleZ = 0.052F;
		this.LegRight.rotateAngleZ = -0.052F;
		this.LegLeft.rotateAngleY = 0F;
		this.LegRight.rotateAngleY = 0F;
		//披風擺動
		this.Cloak01.rotateAngleX = angleZ * 0.05F + 0.2618F;
		this.Cloak02.rotateAngleX = angleZ * 0.1F + 0.1745F;
		this.Cloak03.rotateAngleX = angleZ * 0.15F + 0.2618F;
		//杖位置
		this.Staff.rotateAngleX = 0F;
		this.Staff.rotateAngleY = 0F;
		this.Staff.rotateAngleZ = 1.8326F;
		this.Staff.offsetX = -0.9F;
		this.Staff.offsetY = -0.9F;
		this.Staff.offsetZ = -1.4F;
		//觸手晃動 (equip only)
		if(ent.getEntityState(AttrID.State) >= AttrValues.State.EQUIP) {
			this.EquipT01L.rotateAngleX = angleZ * 0.05F + -0.2618F;
			this.EquipT01L.rotateAngleZ = angleZ * 0.05F + -0.2618F;
			this.EquipT02L.rotateAngleX = angleZ * 0.1F;
			this.EquipT02L.rotateAngleZ = angleZ * 0.1F;
			this.EquipT03L.rotateAngleX = angleZ * 0.25F;
			this.EquipT03L.rotateAngleZ = angleZ * 0.25F;
			
			this.EquipT01R.rotateAngleX = angleZ * 0.05F + -0.2618F;
			this.EquipT01R.rotateAngleZ = -angleZ * 0.05F + 0.2618F;
			this.EquipT02R.rotateAngleX = angleZ * 0.1F;
			this.EquipT02R.rotateAngleZ = -angleZ * 0.1F;
			this.EquipT03R.rotateAngleX = angleZ * 0.25F;
			this.EquipT03R.rotateAngleZ = -angleZ * 0.25F;

			this.EquipTB01L.rotateAngleX = -angleZ * 0.05F + 0.2618F;
			this.EquipTB01L.rotateAngleZ = angleZ * 0.05F + -0.2618F;
			this.EquipTB02L.rotateAngleX = -angleZ * 0.1F;
			this.EquipTB02L.rotateAngleZ = angleZ * 0.1F;
			this.EquipTB03L.rotateAngleX = -angleZ * 0.25F;
			this.EquipTB03L.rotateAngleZ = angleZ * 0.25F;
			
			this.EquipTB01R.rotateAngleX = -angleZ * 0.05F + 0.2618F;
			this.EquipTB01R.rotateAngleZ = -angleZ * 0.05F + 0.2618F;
			this.EquipTB02R.rotateAngleX = -angleZ * 0.1F;
			this.EquipTB02R.rotateAngleZ = -angleZ * 0.1F;
			this.EquipTB03R.rotateAngleX = -angleZ * 0.25F;
			this.EquipTB03R.rotateAngleZ = -angleZ * 0.25F;
		}

	    if(ent.isSprinting() || f1 > 0.99F) {	//奔跑動作
			float angleZFast = MathHelper.cos(f2*0.3F);
	    	//高度
//		    GL11.glTranslatef(0F, -0.2F, 0F);
	  	    //手臂晃動 
		  	this.ArmLeft.rotateAngleX = -0.6981F;
	  	    this.ArmRight.rotateAngleX = -0.6981F;
		    this.ArmLeft.rotateAngleY = 0.5236F;
			this.ArmRight.rotateAngleY = -0.5236F;
			this.ArmLeft.rotateAngleZ = 0F;
			this.ArmRight.rotateAngleZ = 0F;
			//身體角度
			this.BodyMain.rotateAngleX = -0.349F;
			//腿擺動
			addk1 = 0F;
			addk2 = 0F;
			this.LegLeft.rotateAngleY = 0F;
			this.LegRight.rotateAngleY = 0F;
			this.LegLeft.rotateAngleZ = 0.05236F;
			this.LegRight.rotateAngleZ = -0.05236F;
			//披風擺動
			this.Cloak01.rotateAngleX = angleZFast * 0.1F + 1.2F;
			this.Cloak02.rotateAngleX = angleZFast * 0.25F;
			this.Cloak03.rotateAngleX = angleZFast * 0.15F;
			//杖位置
			this.Staff.rotateAngleX = 1.1839F;
			this.Staff.rotateAngleY = -0.1820F;
			this.Staff.rotateAngleZ = -1.2292F;
			this.Staff.offsetX = 0F;
			this.Staff.offsetY = 0F;
			this.Staff.offsetZ = 0F;
			//觸手晃動 (equip only)
			if(ent.getEntityState(AttrID.State) >= AttrValues.State.EQUIP) {
				this.EquipT01L.rotateAngleX = angleZFast * 0.05F + 0.2618F;
				this.EquipT01L.rotateAngleZ = -0.2618F;
				this.EquipT02L.rotateAngleX = angleZFast * 0.15F + 0.2618F;
				this.EquipT02L.rotateAngleZ = -0.2618F;
				this.EquipT03L.rotateAngleX = angleZFast * 0.45F + 0.5236F;
				this.EquipT03L.rotateAngleZ = -0.2618F;
				
				this.EquipT01R.rotateAngleX = angleZFast * 0.05F + 0.2618F;
				this.EquipT01R.rotateAngleZ = 0.2618F;
				this.EquipT02R.rotateAngleX = angleZFast * 0.15F + 0.2618F;
				this.EquipT02R.rotateAngleZ = 0.2618F;
				this.EquipT03R.rotateAngleX = angleZFast * 0.45F + 0.5236F;
				this.EquipT03R.rotateAngleZ = 0.2618F;

				this.EquipTB01L.rotateAngleX = angleZFast * 0.05F + 0.349F;
				this.EquipTB01L.rotateAngleZ = -0.349F;
				this.EquipTB02L.rotateAngleX = angleZFast * 0.15F + 0.5236F;
				this.EquipTB02L.rotateAngleZ = 0.1745F;
				this.EquipTB03L.rotateAngleX = angleZFast * 0.45F + 0.5236F;
				this.EquipTB03L.rotateAngleZ = 0.1745F;
				
				this.EquipTB01R.rotateAngleX = angleZFast * 0.05F + 0.349F;
				this.EquipTB01R.rotateAngleZ = 0.349F;
				this.EquipTB02R.rotateAngleX = angleZFast * 0.15F + 0.5236F;
				this.EquipTB02R.rotateAngleZ = -0.1745F;
				this.EquipTB03R.rotateAngleX = angleZFast * 0.45F + 0.5236F;
				this.EquipTB03R.rotateAngleZ = -0.1745F;
			}
  		}
	    else {
	    	//頭部傾斜動作, 只在奔跑以外時roll
		    if(--this.HeadCooldown < 0) {
		    	this.HeadCooldown = 360;	//cd = 6sec
		    	
		    	if(ent.getRNG().nextInt(3) > 1) {
		    		this.HeadTilt = true;    		
		    	}
		    	else {
		    		this.HeadTilt = false;
		    	}
		    }
	    }
	    
	    if(this.HeadTilt) {    	
	    	if(this.Neck.rotateAngleZ > -0.24F) {
	    		this.Neck.rotateAngleZ -= 0.03F;
	    	}
	    }
	    else {
	    	if(this.Neck.rotateAngleZ < 0F) {
	    		this.Neck.rotateAngleZ += 0.03F;
	    	}
	    }
  		
	    if(ent.isSneaking()) {		//潛行, 蹲下動作
  			this.ArmLeft.rotateAngleX = 0.7F;
  			this.ArmRight.rotateAngleX = 0.7F;
  			this.BodyMain.rotateAngleX = 0.5F;
  			this.Neck.rotateAngleX -= 0.5F;
  			addk1 -= 0.66F;
			addk2 -= 0.66F;
  		}
	    else {
			this.Neck.rotateAngleX += 0.2F;
	    }
  		
	    if(ent.isSitting() || ent.isRiding()) {  //騎乘動作 			
//  			if(ent.getEntityState(AttrID.Emotion) == AttrValues.Emotion.BORED) {
//		    	GL11.glTranslatef(0F, 1.4F, 0F);
//				this.ArmLeft.rotateAngleX = 0.6F;
//	  			this.ArmRight.rotateAngleX = 0.6F;
//	  			this.ArmLeft.rotateAngleZ = -0.6F;
//	  			this.ArmRight.rotateAngleZ = 0.6F;
//				this.BodyMain.rotateAngleX = -0.6F;
//				this.Neck.rotateAngleX -= 0.35F;
//				addk1 = -2F;
//				addk2 = -2F;		
//				this.LegLeft.rotateAngleZ = 1.2F;
//				this.LegRight.rotateAngleZ = -1.2F;
//				this.LegLeft.rotateAngleY = -0.75F;
//				this.LegRight.rotateAngleY = 0.75F;
//  			}
//  			else {
  				//高度
//  			GL11.glTranslatef(0F, -0.2F, 0F);
  		  	    //手臂晃動 
  			  	this.ArmLeft.rotateAngleX = 0.4F;
  			    this.ArmLeft.rotateAngleY = 0F;
  			    this.ArmLeft.rotateAngleZ = -0.2618F;
  			    this.ArmRight.rotateAngleX = 0.34F;
  				this.ArmRight.rotateAngleY = 0F;
  				this.ArmRight.rotateAngleZ = 0.5236F;
  				//身體角度
  				this.BodyMain.rotateAngleX = -0.349F;
  				this.BodyMain.rotateAngleY = -1.57F;
  				this.BodyMain.rotateAngleZ = -0.0873F;
  				//脖子角度
  				this.Neck.rotateAngleX += -0.2F;
  				this.Neck.rotateAngleY += 1.0472F;
  				this.Neck.rotateAngleZ += 0F;
  				//腿擺動
  				addk1 = angleZ * 0.3F + -1.0472F;
  				addk2 = -angleZ * 0.3F + -1.0472F;
  				this.LegLeft.rotateAngleY = 0F;
  				this.LegRight.rotateAngleY = 0F;
  				this.LegLeft.rotateAngleZ = 0.05236F;
  				this.LegRight.rotateAngleZ = -0.05236F;
  				//披風擺動
  				this.Cloak01.rotateAngleX = angleZ * 0.1F + 1.2F;
  				this.Cloak02.rotateAngleX = angleZ * 0.25F;
  				this.Cloak03.rotateAngleX = angleZ * 0.15F;
  				//杖位置
  				this.Staff.rotateAngleX = 0.2F;
  				this.Staff.rotateAngleY = 0F;
  				this.Staff.rotateAngleZ = -2.0944F;
  				this.Staff.offsetX = 0.9F;
  				this.Staff.offsetY = -1.4F;
  				this.Staff.offsetZ = -1.2F;
  				//觸手晃動 (equip only)
  				if(ent.getEntityState(AttrID.State) >= AttrValues.State.EQUIP) {
  					this.EquipT01L.rotateAngleX = angleZ * 0.05F + 0.2618F;
  					this.EquipT01L.rotateAngleZ = -0.2618F;
  					this.EquipT02L.rotateAngleX = angleZ * 0.15F + 0.2618F;
  					this.EquipT02L.rotateAngleZ = -0.2618F;
  					this.EquipT03L.rotateAngleX = angleZ * 0.45F + 0.5236F;
  					this.EquipT03L.rotateAngleZ = -0.2618F;
  					
  					this.EquipT01R.rotateAngleX = angleZ * 0.05F + 0.2618F;
  					this.EquipT01R.rotateAngleZ = 0.2618F;
  					this.EquipT02R.rotateAngleX = angleZ * 0.15F + 0.2618F;
  					this.EquipT02R.rotateAngleZ = 0.2618F;
  					this.EquipT03R.rotateAngleX = angleZ * 0.45F + 0.5236F;
  					this.EquipT03R.rotateAngleZ = 0.2618F;

  					this.EquipTB01L.rotateAngleX = angleZ * 0.05F + 0.349F;
  					this.EquipTB01L.rotateAngleZ = -0.349F;
  					this.EquipTB02L.rotateAngleX = angleZ * 0.15F + 0.5236F;
  					this.EquipTB02L.rotateAngleZ = 0.1745F;
  					this.EquipTB03L.rotateAngleX = angleZ * 0.45F + 0.5236F;
  					this.EquipTB03L.rotateAngleZ = 0.1745F;
  					
  					this.EquipTB01R.rotateAngleX = angleZ * 0.05F + 0.349F;
  					this.EquipTB01R.rotateAngleZ = 0.349F;
  					this.EquipTB02R.rotateAngleX = angleZ * 0.15F + 0.5236F;
  					this.EquipTB02R.rotateAngleZ = -0.1745F;
  					this.EquipTB03R.rotateAngleX = angleZ * 0.45F + 0.5236F;
  					this.EquipTB03R.rotateAngleZ = -0.1745F;
//  				}				
  			}			
  		}
	    
	    //leg motion
	    this.LegLeft.rotateAngleX = addk1;
	    this.LegRight.rotateAngleX = addk2;
	    
	    //攻擊時順便將左手指向對方	    
	    if(ent.attackTime > 0) {
	    	this.ArmLeft.rotateAngleX = f4 / 57.29578F - 1.5F;
	    	this.ArmRight.rotateAngleZ = 0.7F; 
	    	this.ArmRight.rotateAngleX = 0.4F; 
	    }
	}
    
    private void showEquip(BasicEntityShip ent) {
		if(ent.getEntityState(AttrID.State) >= AttrValues.State.EQUIP) {
			this.EquipBase.isHidden = false;
//			this.Staff.isHidden = false;
		}
		else {
			this.EquipBase.isHidden = true;
//			this.Staff.isHidden = true;
		}
  	}
    
    //隨機抽取顯示的表情 
    private void rollEmotion(BasicEntityShip ent) { 
    	switch(ent.getEntityState(AttrID.Emotion)) {
    	case AttrValues.Emotion.BLINK:	//blink
    		EmotionBlink(ent);
    		break;
    	case AttrValues.Emotion.T_T:	//cry
    		if(ent.getStartEmotion() <= 0) { setFace(2); }
    		break;
    	case AttrValues.Emotion.O_O:
    		EmotionStaring(ent);
			break;
    	case AttrValues.Emotion.HUNGRY:
    		if(ent.getStartEmotion() <= 0) { setFace(4); }
			break;
    	case AttrValues.Emotion.BORED:
    	default:						//normal face
    		//reset face to 0
    		if(ent.getStartEmotion() <= 0) setFace(0); 			    
    		//roll emotion (3 times) every 6 sec
    		//1 tick in entity = 3 tick in model class (20 vs 60 fps)
    		if(ent.ticksExisted % 120 == 0) {  			
        		int emotionRand = ent.getRNG().nextInt(10);   		
        		if(emotionRand > 7) {
        			EmotionBlink(ent);
        		} 		
        	}
    		break;
    	}	
    }
    
    //眨眼動作, this emotion is CLIENT ONLY, no sync packet required
  	private void EmotionBlink(BasicEntityShip ent) {
  		if(ent.getEntityState(AttrID.Emotion) == AttrValues.Emotion.NORMAL) {	//要在沒表情狀態才做表情		
  			ent.setStartEmotion(ent.ticksExisted);		//表情開始時間
  			ent.setEntityEmotion(AttrValues.Emotion.BLINK, false);	//標記表情為blink
  		}
  		
  		int EmoTime = ent.ticksExisted - ent.getStartEmotion();
    	 		
    	if(EmoTime > 61) {	//reset face
    		setFace(0);
			ent.setEntityEmotion(AttrValues.Emotion.NORMAL, false);
			ent.setStartEmotion(-1);
    	}
    	else if(EmoTime > 45) {
    		setFace(1);
    	}
    	else if(EmoTime > 35) {
    		setFace(0);
    	}
    	else if(EmoTime > 1) {
    		setFace(1);
    	}		
  	}
  	
  	private void EmotionStaring(BasicEntityShip ent) {	
    	if(ent.getStartEmotion() == -1) {
			ent.setStartEmotion(ent.ticksExisted);		//表情開始時間
		}
    	
    	int EmoTime = ent.ticksExisted - ent.getStartEmotion();
    	
    	if(EmoTime > 41) {	//reset face
    		setFace(0);
			ent.setEntityEmotion(AttrValues.Emotion.NORMAL, false);
			ent.setStartEmotion(-1);
    	}
    	else if(EmoTime > 1) {
    		setFace(3);
    	}
	}
  	
    //設定顯示的臉型
  	private void setFace(int emo) {
  		switch(emo) {
  		case 0:
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 1:
  			this.Face1.isHidden = false;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 2:
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 3:
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face4.isHidden = true;
  			break;
  		case 4:
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = false;
  			break;
  		default:
  			break;
  		}
  	}
    
    
    
}

