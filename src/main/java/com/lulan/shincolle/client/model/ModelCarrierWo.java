package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;

/**
 * EntityCarrierWO - PinkaLulan 2015/2/15
 * Created using Tabula 4.1.1
 */
public class ModelCarrierWo extends ModelBase implements IModelEmotion {
    public ModelRenderer BodyMain;
    public ModelRenderer Butt;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer Neck;
    public ModelRenderer Neck02;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer CloakNeck;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer LegLeft02;
    public ModelRenderer ShoesRight;
    public ModelRenderer ShoesLeft;
    public ModelRenderer Staff;
    public ModelRenderer StaffHead;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer Hair00a;
    public ModelRenderer Hair00b;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
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
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowEquipBase;
    public ModelRenderer Face0;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    
    private int startEmo2 = 0;

    public ModelCarrierWo() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        
        this.Head = new ModelRenderer(this, 43, 101);
        this.Head.setRotationPoint(0.0F, -13.5F, -0.5F);
        this.Head.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 13, 0.0F);
        this.EquipLC02 = new ModelRenderer(this, 128, 0);
        this.EquipLC02.setRotationPoint(-1.0F, -2.0F, -7.0F);
        this.EquipLC02.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.setRotateAngle(EquipLC02, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipTB03R = new ModelRenderer(this, 21, 56);
        this.EquipTB03R.setRotationPoint(0.0F, 13.5F, 0.0F);
        this.EquipTB03R.addBox(-2.0F, -2.0F, -2.0F, 4, 15, 4, 0.0F);
        this.setRotateAngle(EquipTB03R, 0.6981317007977318F, 0.0F, -0.7853981633974483F);
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
        this.ShoesRight.addBox(-3.5F, 4.5F, -0.5F, 7, 9, 7, 0.0F);
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
        this.Hair = new ModelRenderer(this, 128, 61);
        this.Hair.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -8.0F, 16, 14, 7, 0.0F);
        this.HairR01 = new ModelRenderer(this, 175, 61);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-6.0F, 0.0F, -2.0F);
        this.HairR01.addBox(-1.0F, 0.0F, -2.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairR01, -0.5235987755982988F, 0.17453292519943295F, 0.3141592653589793F);
        this.HairR02 = new ModelRenderer(this, 176, 74);
        this.HairR02.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, -2.2F, 2, 9, 3, 0.0F);
        this.setRotateAngle(HairR02, 0.3490658503988659F, 0.0F, -0.2617993877991494F);
        this.HairL01 = new ModelRenderer(this, 175, 61);
        this.HairL01.setRotationPoint(6.0F, 0.0F, -2.0F);
        this.HairL01.addBox(-1.0F, 0.0F, -2.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairL01, -0.5235987755982988F, -0.17453292519943295F, -0.3141592653589793F);
        this.HairL02 = new ModelRenderer(this, 176, 74);
        this.HairL02.mirror = true;
        this.HairL02.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, -2.2F, 2, 9, 3, 0.0F);
        this.setRotateAngle(HairL02, 0.3490658503988659F, 0.0F, 0.2617993877991494F);
        this.Hair00a = new ModelRenderer(this, 128, 82);
        this.Hair00a.setRotationPoint(0.0F, 0.0F, -0.5F);
        this.Hair00a.addBox(-7.5F, -7.5F, -1.0F, 15, 8, 9, 0.0F);
        this.Hair00b = new ModelRenderer(this, 43, 21);
        this.Hair00b.setRotationPoint(0.0F, 0.3F, -2.5F);
        this.Hair00b.addBox(-7.5F, 0.0F, 0.0F, 15, 10, 10, 0.0F);
        this.setRotateAngle(Hair00b, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipT03L = new ModelRenderer(this, 21, 56);
        this.EquipT03L.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.EquipT03L.addBox(-2.5F, -2.0F, -2.5F, 5, 20, 5, 0.0F);
        this.setRotateAngle(EquipT03L, 1.0471975511965976F, 0.0F, 0.7853981633974483F);
        this.LegLeft01 = new ModelRenderer(this, 0, 88);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.2F, 5.0F, -1F);
        this.LegLeft01.addBox(-3F, 0F, -3F, 6, 12, 6, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 1, 110);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0F, 12F, -3F);
        this.LegLeft02.addBox(-3F, 0F, 0F, 6, 7, 6, 0.0F);
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
        this.ShoesLeft.addBox(-3.5F, 4.5F, -0.5F, 7, 9, 7, 0.0F);
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
        this.StaffHead = new ModelRenderer(this, 38, 80);
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
        this.EquipT01R = new ModelRenderer(this, 128, 0);
        this.EquipT01R.setRotationPoint(-17.0F, -7.0F, -8.0F);
        this.EquipT01R.addBox(-4.0F, 0.0F, -4.0F, 8, 10, 8, 0.0F);
        this.setRotateAngle(EquipT01R, -0.2617993877991494F, 0.0F, 0.2617993877991494F);
        this.EquipTB01R = new ModelRenderer(this, 128, 0);
        this.EquipTB01R.setRotationPoint(-15.0F, -6.0F, 10.0F);
        this.EquipTB01R.addBox(-3.0F, -2.0F, -3.0F, 6, 10, 6, 0.0F);
        this.setRotateAngle(EquipTB01R, 0.17453292519943295F, 0.0F, 0.3490658503988659F);
        this.EquipRC01 = new ModelRenderer(this, 128, 0);
        this.EquipRC01.setRotationPoint(-30.0F, -7.0F, 4.0F);
        this.EquipRC01.addBox(-3.5F, -5.5F, -7.5F, 7, 11, 15, 0.0F);
        this.setRotateAngle(EquipRC01, -0.17453292519943295F, 0.2617993877991494F, -0.17453292519943295F);
        this.EquipT03R = new ModelRenderer(this, 21, 56);
        this.EquipT03R.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.EquipT03R.addBox(-2.5F, -2.0F, -2.5F, 5, 20, 5, 0.0F);
        this.setRotateAngle(EquipT03R, 1.0471975511965976F, 0.0F, -0.7853981633974483F);
        this.EquipTooth02 = new ModelRenderer(this, 128, 99);
        this.EquipTooth02.setRotationPoint(12.4F, -17.0F, -20.3F);
        this.EquipTooth02.addBox(0.0F, 0.0F, 0.0F, 14, 8, 4, 0.0F);
        this.setRotateAngle(EquipTooth02, 0.10471975511965977F, -0.5235987755982988F, 0.05235987755982988F);
        this.EquipTB02R = new ModelRenderer(this, 21, 56);
        this.EquipTB02R.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.EquipTB02R.addBox(-2.5F, -2.0F, -2.5F, 5, 16, 5, 0.0F);
        this.setRotateAngle(EquipTB02R, 0.4363323129985824F, 0.0F, 0.3490658503988659F);
        this.Cloak01 = new ModelRenderer(this, 216, 85);
        this.Cloak01.setRotationPoint(0.0F, 6.5F, 6.0F);
        this.Cloak01.addBox(-10.0F, 0.0F, 0.0F, 20, 12, 0, 0.0F);
        this.setRotateAngle(Cloak01, 0.5F, 0.0F, 0.0F);
        this.CloakNeck = new ModelRenderer(this, 192, 61);
        this.CloakNeck.setRotationPoint(0.0F, -12.0F, -1.5F);
        this.CloakNeck.addBox(-10.0F, 0.0F, -6.0F, 20, 7, 12, 0.0F);
        this.setRotateAngle(CloakNeck, 0.31416F, 0.0F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 0, 54);
        this.ArmRight01.setRotationPoint(-4.7F, -9F, 0F);
        this.ArmRight01.addBox(-5F, -1F, -2F, 5, 12, 5, 0F);
        this.ArmRight02 = new ModelRenderer(this, 0, 71);
        this.ArmRight02.setRotationPoint(-5F, 11F, 2F);
        this.ArmRight02.addBox(0F, 0F, -4F, 5, 12, 5, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 88);
        this.LegRight01.setRotationPoint(-4.2F, 5F, -1F);
        this.LegRight01.addBox(-3F, 0F, -3F, 6, 12, 6, 0.0F);
        this.LegRight02 = new ModelRenderer(this,1, 110);
        this.LegRight02.setRotationPoint(0F, 12F, -3F);
        this.LegRight02.addBox(-3F, 0F, 0F, 6, 7, 6, 0.0F);
        this.EquipT01L = new ModelRenderer(this, 128, 0);
        this.EquipT01L.setRotationPoint(17.0F, -7.0F, -8.0F);
        this.EquipT01L.addBox(-4.0F, 0.0F, -4.0F, 8, 10, 8, 0.0F);
        this.setRotateAngle(EquipT01L, -0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, -11.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -12.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.3490658503988659F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 52, 41);
        this.Neck.setRotationPoint(0.0F, -13F, -2F);
        this.Neck.addBox(-7.5F, -1.5F, -7F, 15, 4, 13, 0.0F);
        this.setRotateAngle(Neck, 0.41888F, 0.0F, 0.0F);
        this.Neck02 = new ModelRenderer(this, 128, 0);
        this.Neck02.setRotationPoint(0F, 2F, -5F);
        this.Neck02.addBox(-1.5F, 0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(Neck02, -0.52F, 0.0F, 0.0F);
        this.Equip01 = new ModelRenderer(this, 128, 0);
        this.Equip01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Equip01.addBox(-9.0F, -28.5F, -7.0F, 18, 27, 22, 0.0F);
        this.setRotateAngle(Equip01, 0.0873F, 0.0F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 0, 54);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(4.7F, -9F, 0F);
        this.ArmLeft01.addBox(0F, -1F, -2F, 5, 12, 5, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 0, 71);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(5F, 11F, 3F);
        this.ArmLeft02.addBox(-5F, 0F, -5F, 5, 12, 5, 0.0F);
        this.Staff = new ModelRenderer(this, 128, 0);
        this.Staff.setRotationPoint(8.0F, 35.0F, 21.0F);
        this.Staff.addBox(0.0F, -15.0F, 0.0F, 3, 28, 4, 0.0F);
        this.setRotateAngle(Staff, 1.1838568316277536F, -0.18203784098300857F, -1.2292353921796064F);
        this.Cloak02 = new ModelRenderer(this, 208, 97);
        this.Cloak02.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.Cloak02.addBox(-12.0F, 0.0F, 0.0F, 24, 16, 0, 0.0F);
        this.setRotateAngle(Cloak02, -0.4553564018453205F, 0.0F, 0.0F);
        this.EquipRC03 = new ModelRenderer(this, 128, 0);
        this.EquipRC03.setRotationPoint(0.0F, 2.0F, -7.0F);
        this.EquipRC03.addBox(-1.5F, -1.5F, -16.0F, 3, 3, 16, 0.0F);
        this.setRotateAngle(EquipRC03, 0.10471975511965977F, 0.0F, 0.0F);
        this.EquipTooth01 = new ModelRenderer(this, 128, 112);
        this.EquipTooth01.setRotationPoint(0.0F, -19.3F, -20.6F);
        this.EquipTooth01.addBox(-12.0F, 0.0F, 0.0F, 24, 15, 1, 0.0F);
        this.setRotateAngle(EquipTooth01, 0.10471975511965977F, 0.0F, 0.0F);
        this.BoobL = new ModelRenderer(this, 3, 27);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.8F, -9.0F, -3.2F);
        this.BoobL.addBox(-3.5F, 0.0F, -1.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobL, -0.7853981633974483F, 0.08726646259971647F, 0.14F); 
        this.BoobR = new ModelRenderer(this, 3, 27);
        this.BoobR.setRotationPoint(-3.8F, -9.0F, -3.2F);
        this.BoobR.addBox(-3.5F, 0.0F, -1.0F, 7, 5, 4, 0.0F);
        this.setRotateAngle(BoobR, -0.7853981633974483F, -0.08726646259971647F, -0.14F);
        this.Butt = new ModelRenderer(this, 0, 38);
        this.Butt.setRotationPoint(0.0F, 4.7F, 0.5F);
        this.Butt.addBox(-7.5F, -2.0F, -4.1F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.5235987755982988F, 0.0F, 0.0F);
        this.Cloak03 = new ModelRenderer(this, 196, 113);
        this.Cloak03.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.Cloak03.addBox(-15.0F, 0.0F, 0.0F, 30, 15, 0, 0.0F);
        this.setRotateAngle(Cloak03, 0.3490658503988659F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 31, 89);
        this.Ahoke.setRotationPoint(0F, 0F, -4F);
        this.Ahoke.addBox(0F, -14F, -12F, 0, 13, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0F, 0.7F, 0F);
        this.Face0 = new ModelRenderer(this, 68, 68);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.0F, -6.5F, 14, 14, 1, 0.0F);
        this.BodyMain.addChild(this.Head);
        this.EquipLC01.addChild(this.EquipLC02);
        this.EquipTB02R.addChild(this.EquipTB03R);
        this.BodyMain.addChild(this.BoobL);
        this.EquipLC01.addChild(this.EquipLC03);
        this.EquipT01L.addChild(this.EquipT02L);
        this.LegRight02.addChild(this.ShoesRight);
        this.EquipBase.addChild(this.Equip05);
        this.EquipTB02L.addChild(this.EquipTB03L);
        this.EquipBase.addChild(this.Equip03);
        this.Head.addChild(this.Hair);
        this.Hair.addChild(this.Ahoke);
        this.Hair.addChild(this.HairR01);
        this.HairR01.addChild(this.HairR02);
        this.Hair.addChild(this.HairL01);
        this.HairL01.addChild(this.HairL02);
        this.Hair.addChild(this.Hair00a);
        this.Hair.addChild(this.Hair00b);
        this.EquipT02L.addChild(this.EquipT03L);
        this.Butt.addChild(this.LegLeft01);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipBase.addChild(this.EquipTooth03);
        this.EquipRC01.addChild(this.EquipRC02);
        this.LegLeft02.addChild(this.ShoesLeft);
        this.Head.addChild(this.EquipBase);
        this.EquipBase.addChild(this.Equip04);
        this.EquipBase.addChild(this.EquipLC01);
        this.EquipBase.addChild(this.EquipTB01L);
        this.Staff.addChild(this.StaffHead);
        this.EquipBase.addChild(this.Equip06);
        this.EquipT01R.addChild(this.EquipT02R);
        this.EquipBase.addChild(this.Equip02);
        this.EquipTB01L.addChild(this.EquipTB02L);
        this.EquipBase.addChild(this.EquipT01R);
        this.EquipBase.addChild(this.EquipTB01R);
        this.EquipBase.addChild(this.EquipRC01);
        this.EquipT02R.addChild(this.EquipT03R);
        this.EquipBase.addChild(this.EquipTooth02);
        this.EquipTB01R.addChild(this.EquipTB02R);
        this.CloakNeck.addChild(this.Cloak01);
        this.BodyMain.addChild(this.CloakNeck);
        this.BodyMain.addChild(this.ArmRight01);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Butt.addChild(this.LegRight01);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipBase.addChild(this.EquipT01L);
        this.BodyMain.addChild(this.Neck);
        this.Neck.addChild(this.Neck02);
        this.EquipBase.addChild(this.Equip01);
        this.BodyMain.addChild(this.ArmLeft01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.ArmRight02.addChild(this.Staff);
        this.Cloak01.addChild(this.Cloak02);
        this.EquipRC01.addChild(this.EquipRC03);
        this.EquipBase.addChild(this.EquipTooth01);
        this.BodyMain.addChild(this.BoobR);
        this.BodyMain.addChild(this.Butt);
        this.Cloak02.addChild(this.Cloak03);
        
        //以下為發光模型支架, 此部份模型整個亮度設為240
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -11.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.3490658503988659F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -13.5F, -0.5F);
        this.GlowEquipBase = new ModelRenderer(this, 0, 0);
        this.GlowEquipBase.setRotationPoint(0.0F, -10.0F, -3.0F);
        this.setRotateAngle(GlowEquipBase, 0.08726646259971647F, 0.0F, 0.0F);
        this.GlowBodyMain.addChild(this.GlowHead);
        this.GlowHead.addChild(this.GlowEquipBase);
        this.GlowEquipBase.addChild(this.EquipEye01);
        this.GlowEquipBase.addChild(this.EquipEye02);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
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
        
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glScalef(0.5F, 0.5F, 0.5F); 	
    	GL11.glTranslatef(0F, 1.5F, 0F);
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity); 	
    	this.BodyMain.render(f5);
    	
    	GL11.glDisable(GL11.GL_LIGHTING);
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5); 	
    	GL11.glEnable(GL11.GL_LIGHTING);
    	
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glPopMatrix();
    }
    
    //for idle/run animation
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	
    	BasicEntityShip ent = (BasicEntityShip)entity;

    	showEquip(ent);

    	EmotionHelper.rollEmotion(this, ent);

    	if(ent.getStateFlag(ID.F.NoFuel)) {
    		motionStopPos(f, f1, f2, f3, f4, ent);
		}
		else {
			motionHumanPos(f, f1, f2, f3, f4, ent);
		}

    	setGlowRotation();
    }
    
    //設定模型發光部份的rotation
    private void setGlowRotation() {
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowEquipBase.rotateAngleX = this.EquipBase.rotateAngleX;
		this.GlowEquipBase.rotateAngleY = this.EquipBase.rotateAngleY;
		this.GlowEquipBase.rotateAngleZ = this.EquipBase.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
	}
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {
    	GL11.glTranslatef(0F, 1.35F, 0F);
  		setFace(4);
  		
  		//頭部
	  	this.Head.rotateAngleY = 0F;	//左右角度 角度轉成rad 即除以57.29578
	    this.Head.rotateAngleX = 0F; 	//上下角度
	    //胸部
  	    this.BoobL.rotateAngleX = -0.63F;
  	    this.BoobR.rotateAngleX = -0.63F;
  	    //呆毛
  	    this.Ahoke.rotateAngleY = 0.5236F;
  	    //手臂晃動 
	    this.ArmRight02.rotateAngleY = 0F;
		//身體角度
		this.Butt.offsetY = 0F;
    	//身體角度
		this.BodyMain.rotateAngleX = 0.2094F;
		this.BodyMain.rotateAngleY = 0F;
		this.BodyMain.rotateAngleZ = 0F;
		this.Butt.rotateAngleX = -0.4189F;
		this.Butt.offsetZ = -0.12F;
    	//手臂
	  	this.ArmLeft01.rotateAngleX = -1.0472F;
	    this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = 0.4189F;
	    this.ArmLeft02.rotateAngleX = -0.1396F;
	    this.ArmLeft02.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleZ = 1.2915F;
	    this.ArmRight01.rotateAngleX = -0.8727F;
		this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -0.0873F;
		this.ArmRight02.rotateAngleZ = -1.1345F;
		//腿擺動
		this.LegLeft01.rotateAngleX = -2.2689F;
		this.LegLeft01.rotateAngleY = -0.2094F;
		this.LegLeft01.rotateAngleZ = -0.2094F;
		this.LegLeft02.rotateAngleX = 1.7454F;
		this.LegLeft02.offsetZ = 0.3F;
		this.LegRight01.rotateAngleX = -2.2689F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = 0.0873F;
		this.LegRight02.rotateAngleX = 1.5708F;
		this.LegRight02.offsetZ = 0.3F;
		//披風擺動
		this.Cloak01.rotateAngleX = 0.2618F;
		this.Cloak02.rotateAngleX = -1.3963F;
		this.Cloak03.rotateAngleX = -0.9425F;
		//杖位置
		this.Staff.rotateAngleX = 1.309F;
		this.Staff.rotateAngleY = -0.5934F;
		this.Staff.rotateAngleZ = -0.2094F;
		this.Staff.offsetX = -0.3F;
		this.Staff.offsetY = -1.5F;
		this.Staff.offsetZ = -1.7F;
		//觸手晃動 (equip only)
		if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
			this.EquipLC01.rotateAngleX = this.Head.rotateAngleX;
			this.EquipRC01.rotateAngleX = this.Head.rotateAngleX;
			
			this.EquipT01L.rotateAngleX = - 0.2618F;
			this.EquipT01L.rotateAngleZ = -0.2618F;
			this.EquipT02L.rotateAngleX = -0.3491F;
			this.EquipT02L.rotateAngleZ = 0.2618F;
			this.EquipT03L.rotateAngleX = 1.0472F;
			this.EquipT03L.rotateAngleZ = 1.0472F;
			
			this.EquipT01R.rotateAngleX = -0.2618F;
			this.EquipT01R.rotateAngleZ = 0.2618F;
			this.EquipT02R.rotateAngleX = -0.3491F;
			this.EquipT02R.rotateAngleZ = -0.2618F;
			this.EquipT03R.rotateAngleX = 1.0472F;
			this.EquipT03R.rotateAngleZ = -1.0472F;

			this.EquipTB01L.rotateAngleX = 0.1745F;
			this.EquipTB01L.rotateAngleZ = -0.3491F;
			this.EquipTB02L.rotateAngleX = -0.6981F;
			this.EquipTB02L.rotateAngleZ = 0.3491F;
			this.EquipTB03L.rotateAngleX = 0.1745F;
			this.EquipTB03L.rotateAngleZ = 0.2618F;
			
			this.EquipTB01R.rotateAngleX = 0.1745F;
			this.EquipTB01R.rotateAngleZ = 0.3491F;
			this.EquipTB02R.rotateAngleX = -0.6981F;
			this.EquipTB02R.rotateAngleZ = -0.3491F;
			this.EquipTB03R.rotateAngleX = 0.1745F;
			this.EquipTB03R.rotateAngleZ = -0.2618F;
		}
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
	  	this.Head.rotateAngleY = f3 / 57.29578F;	//左右角度 角度轉成rad 即除以57.29578
	    this.Head.rotateAngleX = (f4 / 57.29578F) * 0.7F; 	//上下角度
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = -angleZ * 0.06F - 0.63F;
  	    this.BoobR.rotateAngleX = -angleZ * 0.06F - 0.63F;
  	    //呆毛
  	    this.Ahoke.rotateAngleY = angleZ * 0.25F + 0.5236F;
  	    //手臂晃動 
	  	this.ArmLeft01.rotateAngleX = -0.3F;
  	    this.ArmRight01.rotateAngleX = -0.3F;
	    this.ArmLeft01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleY = 0F;
		this.ArmLeft01.rotateAngleZ = 0.24F;
		this.ArmRight01.rotateAngleZ = -0.24F;
		this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmRight02.rotateAngleY = 0F;
		this.ArmLeft02.rotateAngleZ = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		//身體角度
		this.BodyMain.rotateAngleX = -0.1745F;
		this.BodyMain.rotateAngleY = 0F;
		this.BodyMain.rotateAngleZ = 0F;
		this.Butt.rotateAngleX = 0.5236F;
		this.Butt.offsetY = 0F;
		this.Butt.offsetZ = 0F;
		//腿擺動
		addk1 += -0.349F;
		addk2 += -0.349F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.052F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.052F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.offsetZ = 0F;
		//披風擺動
		this.Cloak01.rotateAngleX = angleZ * 0.05F + 0.2618F;
		this.Cloak02.rotateAngleX = angleZ * 0.1F + 0.1745F;
		this.Cloak03.rotateAngleX = angleZ * 0.15F + 0.2618F;
		//杖位置
		this.Staff.rotateAngleX = 0F;
		this.Staff.rotateAngleY = 0F;
		this.Staff.rotateAngleZ = 1.8326F;
		this.Staff.offsetX = -0.7F;
		this.Staff.offsetY = -1.7F;
		this.Staff.offsetZ = -1.4F;
		//觸手晃動 (equip only)
		if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
			this.EquipLC01.rotateAngleX = this.Head.rotateAngleX;
			this.EquipRC01.rotateAngleX = this.Head.rotateAngleX;
			
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

	    if(ent.isSprinting() || f1 > 0.9F) {	//奔跑動作
			float angleZFast = MathHelper.cos(f2*0.3F);
	  	    //手臂晃動 
		  	this.ArmLeft01.rotateAngleX = -0.6981F;
	  	    this.ArmRight01.rotateAngleX = -0.6981F;
		    this.ArmLeft01.rotateAngleY = 0.4F;
			this.ArmRight01.rotateAngleY = -0.4F;
			this.ArmLeft01.rotateAngleZ = 0F;
			this.ArmRight01.rotateAngleZ = 0F;
			//身體角度
			this.BodyMain.rotateAngleX = -0.349F;
			//腿擺動
			addk1 = 0F;
			addk2 = 0F;
			this.LegLeft01.rotateAngleY = 0F;
			this.LegRight01.rotateAngleY = 0F;
			this.LegLeft01.rotateAngleZ = 0.05236F;
			this.LegRight01.rotateAngleZ = -0.05236F;
			//披風擺動
			this.Cloak01.rotateAngleX = angleZFast * 0.1F + 1.2F;
			this.Cloak02.rotateAngleX = angleZFast * 0.25F;
			this.Cloak03.rotateAngleX = angleZFast * 0.15F;
			//杖位置
			this.Staff.rotateAngleX = 1.3F;
			this.Staff.rotateAngleY = -0.1820F;
			this.Staff.rotateAngleZ = -1.2292F;
			this.Staff.offsetX = 0.2F;
			this.Staff.offsetY = -1F;
			this.Staff.offsetZ = -0.1F;
			//觸手晃動 (equip only)
			if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
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

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);

  		
	    if(ent.isSneaking()) {		//潛行, 蹲下動作
  			this.ArmLeft01.rotateAngleX = 0.7F;
  			this.ArmRight01.rotateAngleX = 0.7F;
  			this.BodyMain.rotateAngleX = 0.5F;
  			this.Head.rotateAngleX -= 0.5F;
  			this.Cloak01.rotateAngleX = angleZ * 0.02F + 0.34F;
  			addk1 -= 0.66F;
			addk2 -= 0.66F;
  		}
	    else {
			this.Head.rotateAngleX += 0.2F;
	    }
  		
	    if(ent.isSitting() || ent.isRiding()) {  //騎乘動作
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
		    	GL11.glTranslatef(0F, 1.35F, 0F);
		    	//身體角度
				this.BodyMain.rotateAngleX = 0.2094F;
				this.BodyMain.rotateAngleY = 0F;
				this.BodyMain.rotateAngleZ = 0F;
				this.Butt.rotateAngleX = -0.4189F;
				this.Butt.offsetZ = -0.12F;
				//頭
				this.Head.rotateAngleY *= 0.5F;
		    	//手臂
			  	this.ArmLeft01.rotateAngleX = -1.0472F;
			    this.ArmLeft01.rotateAngleY = 0F;
			    this.ArmLeft01.rotateAngleZ = 0.4189F;
			    this.ArmLeft02.rotateAngleX = -0.1396F;
			    this.ArmLeft02.rotateAngleY = 0F;
			    this.ArmLeft02.rotateAngleZ = 1.2915F;
			    this.ArmRight01.rotateAngleX = -0.8727F;
				this.ArmRight01.rotateAngleY = 0F;
				this.ArmRight01.rotateAngleZ = -0.0873F;
				this.ArmRight02.rotateAngleZ = -1.1345F;
				//腿擺動
				addk1 = -2.2689F;
				addk2 = -2.2689F;
				this.LegLeft01.rotateAngleY = -0.2094F;
				this.LegLeft01.rotateAngleZ = -0.2094F;
				this.LegLeft02.rotateAngleX = 1.7454F;
				this.LegLeft02.offsetZ = 0.3F;
				this.LegRight01.rotateAngleY = 0F;
				this.LegRight01.rotateAngleZ = 0.0873F;
				this.LegRight02.rotateAngleX = 1.5708F;
				this.LegRight02.offsetZ = 0.3F;
				//披風擺動
				this.Cloak01.rotateAngleX = 0.2618F;
				this.Cloak02.rotateAngleX = -1.3963F;
				this.Cloak03.rotateAngleX = -0.9425F;
				//杖位置
				this.Staff.rotateAngleX = 1.309F;
				this.Staff.rotateAngleY = -0.5934F;
				this.Staff.rotateAngleZ = -0.2094F;
				this.Staff.offsetX = -0.3F;
				this.Staff.offsetY = -1.5F;
				this.Staff.offsetZ = -1.7F;
				//觸手晃動 (equip only)
				if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
					this.EquipT01L.rotateAngleX = angleZ * 0.01F - 0.2618F;
					this.EquipT01L.rotateAngleZ = -0.2618F;
					this.EquipT02L.rotateAngleX = angleZ * 0.03F - 0.3491F;
					this.EquipT02L.rotateAngleZ = 0.2618F;
					this.EquipT03L.rotateAngleX = -angleZ * 0.1F + 1.0472F;
					this.EquipT03L.rotateAngleZ = 1.0472F;
					
					this.EquipT01R.rotateAngleX = -angleZ * 0.01F - 0.2618F;
					this.EquipT01R.rotateAngleZ = 0.2618F;
					this.EquipT02R.rotateAngleX = -angleZ * 0.03F - 0.3491F;
					this.EquipT02R.rotateAngleZ = -0.2618F;
					this.EquipT03R.rotateAngleX = angleZ * 0.1F + 1.0472F;
					this.EquipT03R.rotateAngleZ = -1.0472F;

					this.EquipTB01L.rotateAngleX = angleZ * 0.01F + 0.1745F;
					this.EquipTB01L.rotateAngleZ = -0.3491F;
					this.EquipTB02L.rotateAngleX = angleZ * 0.03F - 0.6981F;
					this.EquipTB02L.rotateAngleZ = 0.3491F;
					this.EquipTB03L.rotateAngleX = angleZ * 0.05F + 0.1745F;
					this.EquipTB03L.rotateAngleZ = 0.2618F;
					
					this.EquipTB01R.rotateAngleX = -angleZ * 0.01F + 0.1745F;
					this.EquipTB01R.rotateAngleZ = 0.3491F;
					this.EquipTB02R.rotateAngleX = -angleZ * 0.03F - 0.6981F;
					this.EquipTB02R.rotateAngleZ = -0.3491F;
					this.EquipTB03R.rotateAngleX = -angleZ * 0.05F + 0.1745F;
					this.EquipTB03R.rotateAngleZ = -0.2618F;
	  			}
	    	}
	    	else {
	    		//手臂晃動 
			  	this.ArmLeft01.rotateAngleX = 0.4F;
			    this.ArmLeft01.rotateAngleY = 0F;
			    this.ArmLeft01.rotateAngleZ = -0.32F;
			    this.ArmRight01.rotateAngleX = 0.34F;
				this.ArmRight01.rotateAngleY = 0F;
				this.ArmRight01.rotateAngleZ = 0.5236F;
				//身體角度
				this.BodyMain.rotateAngleX = -0.349F;
				this.BodyMain.rotateAngleY = -1.57F;
				this.BodyMain.rotateAngleZ = -0.0873F;
				//脖子角度
				this.Head.rotateAngleX += -0.25F;
				this.Head.rotateAngleY += 0.4F;
				this.Head.rotateAngleZ += 0F;
				//腿擺動
				addk1 = angleZ * 0.3F + -1.0472F;
				addk2 = -angleZ * 0.3F + -1.0472F;
				this.LegLeft01.rotateAngleY = 0F;
				this.LegRight01.rotateAngleY = 0F;
				this.LegLeft01.rotateAngleZ = 0.05236F;
				this.LegRight01.rotateAngleZ = -0.05236F;
				//披風擺動
				this.Cloak01.rotateAngleX = angleZ * 0.1F + 0.4F;
				this.Cloak02.rotateAngleX = angleZ * 0.15F;
				this.Cloak03.rotateAngleX = angleZ * 0.15F;
				//杖位置
				this.Staff.rotateAngleX = 0.2F;
				this.Staff.rotateAngleY = 0F;
				this.Staff.rotateAngleZ = -2.0F;
				this.Staff.offsetX = 1.1F;
				this.Staff.offsetY = -1.95F;
				this.Staff.offsetZ = -1.4F;
				//觸手晃動 (equip only)
				if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
					this.EquipT01L.rotateAngleX = -angleZ * 0.05F + 0.2618F;
					this.EquipT01L.rotateAngleZ = -0.2618F;
					this.EquipT02L.rotateAngleX = -angleZ * 0.15F + 0.2618F;
					this.EquipT02L.rotateAngleZ = -0.1618F;
					this.EquipT03L.rotateAngleX = -angleZ * 0.45F + 0F;
					this.EquipT03L.rotateAngleZ = -0.2618F;
					
					this.EquipT01R.rotateAngleX = angleZ * 0.05F + 0.2618F;
					this.EquipT01R.rotateAngleZ = 0.2618F;
					this.EquipT02R.rotateAngleX = angleZ * 0.15F + 0.2618F;
					this.EquipT02R.rotateAngleZ = 0.1618F;
					this.EquipT03R.rotateAngleX = angleZ * 0.45F + 0F;
					this.EquipT03R.rotateAngleZ = 0.2618F;
	
					this.EquipTB01L.rotateAngleX = angleZ * 0.05F + 0.349F;
					this.EquipTB01L.rotateAngleZ = -0.349F;
					this.EquipTB02L.rotateAngleX = angleZ * 0.15F + 0.2236F;
					this.EquipTB02L.rotateAngleZ = 0.1745F;
					this.EquipTB03L.rotateAngleX = angleZ * 0.45F + 0.1236F;
					this.EquipTB03L.rotateAngleZ = 0.1745F;
					
					this.EquipTB01R.rotateAngleX = -angleZ * 0.05F + 0.349F;
					this.EquipTB01R.rotateAngleZ = 0.349F;
					this.EquipTB02R.rotateAngleX = -angleZ * 0.15F + 0.2236F;
					this.EquipTB02R.rotateAngleZ = -0.1745F;
					this.EquipTB03R.rotateAngleX = -angleZ * 0.45F + 0.1236F;
					this.EquipTB03R.rotateAngleZ = -0.1745F;
	  			}
	    	}
  		}
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	    
	    //攻擊時順便將左手指向對方	    
	    if(ent.attackTime > 0) {
	    	this.ArmLeft01.rotateAngleX = f4 / 57.29578F - 1.5F;
	    	this.ArmRight01.rotateAngleZ = 0.7F;
	    	this.ArmRight01.rotateAngleX = 0.4F;
	    	//杖位置
			this.Staff.rotateAngleX = 1.5F;
			this.Staff.rotateAngleY = 0F;
			this.Staff.rotateAngleZ = -1.2F;
			this.Staff.offsetX = -0.2F;
			this.Staff.offsetY = -1.2F;
			this.Staff.offsetZ = -1.0F;
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if(f6 != 0F) {
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.2F;
	        this.ArmRight01.rotateAngleY = 0F;
	        this.ArmRight01.rotateAngleZ = -0.1F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.RAD_MUL;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.RAD_MUL + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.RAD_MUL;
	        this.ArmRight02.rotateAngleX = 0F;
	        this.ArmRight02.rotateAngleY = 0F;
	        this.ArmRight02.rotateAngleZ = 0F;
	  	}
	}
    
    private void showEquip(BasicEntityShip ent) {
		if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
			this.EquipBase.isHidden = false;
			this.EquipEye01.isHidden = false;
			this.EquipEye02.isHidden = false;
		}
		else {
			this.EquipBase.isHidden = true;
			this.EquipEye01.isHidden = true;
			this.EquipEye02.isHidden = true;
		}
  	}
  	
    //設定顯示的臉型
  	@Override
  	public void setFace(int emo) {
  		switch(emo) {
  		case 0:
  			this.Face0.isHidden = false;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 1:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = false;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 2:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 3:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face4.isHidden = true;
  			break;
  		case 4:
  			this.Face0.isHidden = true;
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

