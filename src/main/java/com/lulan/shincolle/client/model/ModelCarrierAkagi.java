package com.lulan.shincolle.client.model;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * ModelCarrierAkagi - PinkaLulan  2016/4/13
 * Created using Tabula 4.1.1 
 */
public class ModelCarrierAkagi extends ModelBase implements IModelEmotion {
    public ModelRenderer BodyMain;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer Butt;
    public ModelRenderer Head;
    public ModelRenderer Cloth01;
    public ModelRenderer Cloth02;
    public ModelRenderer Cloth05;
    public ModelRenderer Cloth06;
    public ModelRenderer EquipB01;
    public ModelRenderer EquipC01;
    public ModelRenderer EquipABase;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer ClothBody01;
    public ModelRenderer ClothBody02;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer Tail01;
    public ModelRenderer LegRight02;
    public ModelRenderer EquipSR01;
    public ModelRenderer LegLeft02;
    public ModelRenderer EquipSL01;
    public ModelRenderer Skirt02;
    public ModelRenderer Cloth07;
    public ModelRenderer Cloth08;
    public ModelRenderer Cloth09;
    public ModelRenderer EquipS01;
    public ModelRenderer Tail02;
    public ModelRenderer Tail03;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Face0;
    public ModelRenderer Ear01;
    public ModelRenderer Ear02;
    public ModelRenderer Ahoke;
    public ModelRenderer HairU01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL01;
    public ModelRenderer HairR02;
    public ModelRenderer HairL02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Cloth03;
    public ModelRenderer Cloth04;
    public ModelRenderer EquipC02;
    public ModelRenderer EquipABelt01;
    public ModelRenderer EquipABody01;
    public ModelRenderer EquipABody04;
    public ModelRenderer EquipABody02;
    public ModelRenderer EquipABody03;
    public ModelRenderer EquipABody05;
    public ModelRenderer EquipAArr01a;
    public ModelRenderer EquipAArr02a;
    public ModelRenderer EquipAArr03a;
    public ModelRenderer EquipABody05b;
    public ModelRenderer EquipABody05c;
    public ModelRenderer EquipABelt02;
    public ModelRenderer EquipAArr01b;
    public ModelRenderer EquipAArr02b;
    public ModelRenderer EquipAArr03b;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ClothHL01;
    public ModelRenderer EquipE01;
    public ModelRenderer EquipE02;
    public ModelRenderer EquipE04;
    public ModelRenderer EquipE03;
    public ModelRenderer EquipE05;
    public ModelRenderer EquipE06;
    public ModelRenderer ClothHL02;
    public ModelRenderer ClothHL03;
    public ModelRenderer ArmRight02;
    public ModelRenderer ClothHL01_1;
    public ModelRenderer EquipD01;
    public ModelRenderer EquipGlove;
    public ModelRenderer ClothHL02_1;
    public ModelRenderer ClothHL03_1;
    public ModelRenderer EquipD02;
    public ModelRenderer EquipD03;
    public ModelRenderer EquipD04;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;
    
    private Random rand = new Random();
    private int startEmo2 = 0;
    private float scale;
    private float offsetY;
    

    public ModelCarrierAkagi(int scaleType) {
        this.textureWidth = 256;
        this.textureHeight = 128;
        
        switch(scaleType) {  //type 1: boss scale
        case 1:
        	scale = 1.6F;
        	offsetY = -2.1F;
        	break;
        default:
        	scale = 0.45F;
        	offsetY = 0.3F;
        	break;
        }
        
        this.ClothHL01 = new ModelRenderer(this, 43, 1);
        this.ClothHL01.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.ClothHL01.addBox(-2.5F, 0.0F, -3.0F, 6, 5, 6, 0.0F);
        this.Ear02 = new ModelRenderer(this, 20, 0);
        this.Ear02.setRotationPoint(3.8F, -14.5F, 5.7F);
        this.Ear02.addBox(-1.5F, 0.0F, -6.0F, 3, 6, 6, 0.0F);
        this.setRotateAngle(Ear02, -0.7853981633974483F, -0.2617993877991494F, 0.13962634015954636F);
        this.ClothBody02 = new ModelRenderer(this, 0, 113);
        this.ClothBody02.setRotationPoint(-6.0F, -3.8F, -2.3F);
        this.ClothBody02.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 5, 0.0F);
        this.setRotateAngle(ClothBody02, 0.2617993877991494F, 0.0F, 0.2617993877991494F);
        this.EquipAArr01a = new ModelRenderer(this, 4, 47);
        this.EquipAArr01a.setRotationPoint(-0.5F, 0.7F, 0.0F);
        this.EquipAArr01a.addBox(0.0F, -4.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(EquipAArr01a, 0.0F, 0.0F, 0.05235987755982988F);
        this.EquipD03 = new ModelRenderer(this, 153, 21);
        this.EquipD03.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipD03.addBox(-5.5F, -26.0F, 0.0F, 11, 26, 1, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 63);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.2792526803190927F, 0.0F, 0.13962634015954636F);
        this.LegLeft02 = new ModelRenderer(this, 0, 83);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(-1.0F, -9.0F, -5.5F);
        this.Ahoke.addBox(0.0F, -4.0F, -11.5F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.08726646259971647F, 0.6981317007977318F, 0.0F);
        this.Cloth08 = new ModelRenderer(this, 24, 80);
        this.Cloth08.setRotationPoint(-0.5F, 0.5F, -7.0F);
        this.Cloth08.addBox(-3.0F, 0.0F, 0.0F, 3, 8, 1, 0.0F);
        this.setRotateAngle(Cloth08, -0.15707963267948966F, -0.10471975511965977F, 0.17453292519943295F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.HairR01 = new ModelRenderer(this, 86, 101);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-7.0F, 3.0F, -5.5F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairR01, -0.13962634015954636F, 0.17453292519943295F, 0.08726646259971647F);
        this.EquipSR01 = new ModelRenderer(this, 24, 90);
        this.EquipSR01.setRotationPoint(0.0F, 15.0F, 3.0F);
        this.EquipSR01.addBox(-3.0F, 0.0F, -3.5F, 6, 4, 7, 0.0F);
        this.EquipAArr03b = new ModelRenderer(this, 0, 48);
        this.EquipAArr03b.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.EquipAArr03b.addBox(-0.5F, -2.7F, 0.5F, 2, 4, 0, 0.0F);
        this.HairR02 = new ModelRenderer(this, 86, 101);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 7.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.Tail02 = new ModelRenderer(this, 63, 36);
        this.Tail02.setRotationPoint(0.0F, 0.0F, 7.5F);
        this.Tail02.addBox(-1.0F, -1.0F, -0.3F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Tail02, 0.6981317007977318F, 0.0F, 0.0F);
        this.Cloth09 = new ModelRenderer(this, 34, 80);
        this.Cloth09.setRotationPoint(0.0F, 0.2F, -6.8F);
        this.Cloth09.addBox(-3.0F, 0.0F, 0.0F, 6, 10, 0, 0.0F);
        this.setRotateAngle(Cloth09, -0.13962634015954636F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -11.8F, -1.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.setRotateAngle(Head, 0.10471975511965977F, 0.0F, 0.0F);
        this.BoobL = new ModelRenderer(this, 33, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(2.8F, -8.5F, -3.5F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, -0.10471975511965977F, -0.08726646259971647F);
        this.EquipAArr02a = new ModelRenderer(this, 4, 47);
        this.EquipAArr02a.setRotationPoint(-1.5F, 0.3F, -1.1F);
        this.EquipAArr02a.addBox(0.0F, -4.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(EquipAArr02a, 0.05235987755982988F, -0.31869712141416456F, -0.05235987755982988F);
        this.Butt = new ModelRenderer(this, 52, 65);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3141592653589793F, 0.0F, 0.0F);
        this.EquipABody01 = new ModelRenderer(this, 86, 55);
        this.EquipABody01.setRotationPoint(-12.5F, -2.5F, 0.0F);
        this.EquipABody01.addBox(-5.0F, -5.5F, -1.0F, 4, 8, 2, 0.0F);
        this.setRotateAngle(EquipABody01, 0.0F, 0.0F, -0.7853981633974483F);
        this.EquipABase = new ModelRenderer(this, 44, 35);
        this.EquipABase.setRotationPoint(-1.0F, -8.0F, 3.6F);
        this.EquipABase.addBox(-0.5F, -1.0F, -0.3F, 3, 2, 1, 0.0F);
        this.setRotateAngle(EquipABase, 0.0F, 0.13962634015954636F, 0.0F);
        this.EquipAArr01b = new ModelRenderer(this, 0, 48);
        this.EquipAArr01b.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.EquipAArr01b.addBox(-0.5F, -2.7F, 0.5F, 2, 4, 0, 0.0F);
        this.EquipD01 = new ModelRenderer(this, 150, 13);
        this.EquipD01.setRotationPoint(0.3F, 2.0F, 0.0F);
        this.EquipD01.addBox(-3.0F, 0.0F, -3.5F, 8, 1, 7, 0.0F);
        this.setRotateAngle(EquipD01, 0.0F, 3.141592653589793F, 0.0F);
        this.EquipABody02 = new ModelRenderer(this, 128, 37);
        this.EquipABody02.setRotationPoint(-3.5F, -11.4F, 0.0F);
        this.EquipABody02.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
        this.Cloth02 = new ModelRenderer(this, 44, 19);
        this.Cloth02.mirror = true;
        this.Cloth02.setRotationPoint(5.8F, -7.9F, 0.0F);
        this.Cloth02.addBox(0.0F, -3.5F, -4.6F, 1, 7, 8, 0.0F);
        this.setRotateAngle(Cloth02, 0.08726646259971647F, -0.13962634015954636F, -0.13962634015954636F);
        this.ArmRight01 = new ModelRenderer(this, 0, 8);
        this.ArmRight01.setRotationPoint(-7.8F, -8.7F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.0F, 0.3141592653589793F);
        this.LegRight01 = new ModelRenderer(this, 0, 63);
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.13962634015954636F, 0.0F, -0.13962634015954636F);
        this.EquipABody03 = new ModelRenderer(this, 128, 34);
        this.EquipABody03.setRotationPoint(-3.5F, -6.5F, 0.0F);
        this.EquipABody03.addBox(-3.5F, -0.5F, -0.5F, 7, 1, 1, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 0, 28);
        this.Skirt01.setRotationPoint(0.0F, 2.3F, 0.0F);
        this.Skirt01.addBox(-8.5F, 0.0F, -6.3F, 17, 6, 10, 0.0F);
        this.setRotateAngle(Skirt01, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipE05 = new ModelRenderer(this, 131, 77);
        this.EquipE05.setRotationPoint(0.0F, 0.0F, 14.7F);
        this.EquipE05.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 12, 0.0F);
        this.setRotateAngle(EquipE05, 0.45378560551852565F, 0.0F, 0.0F);
        this.ClothHL03_1 = new ModelRenderer(this, 40, 0);
        this.ClothHL03_1.setRotationPoint(-1.0F, 4.0F, 0.0F);
        this.ClothHL03_1.addBox(-3.5F, 0.0F, -3.5F, 8, 5, 7, 0.0F);
        this.EquipS01 = new ModelRenderer(this, 58, 55);
        this.EquipS01.setRotationPoint(0.0F, 0.3F, -7.3F);
        this.EquipS01.addBox(-5.5F, 0.0F, 0.0F, 11, 9, 1, 0.0F);
        this.setRotateAngle(EquipS01, -0.2792526803190927F, 0.0F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 86, 101);
        this.HairL01.setRotationPoint(7.0F, 3.0F, -5.5F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 4, 0.0F);
        this.setRotateAngle(HairL01, -0.13962634015954636F, -0.17453292519943295F, -0.08726646259971647F);
        this.EquipSL01 = new ModelRenderer(this, 24, 90);
        this.EquipSL01.mirror = true;
        this.EquipSL01.setRotationPoint(0.0F, 15.0F, 3.0F);
        this.EquipSL01.addBox(-3.0F, 0.0F, -3.5F, 6, 4, 7, 0.0F);
        this.EquipABody04 = new ModelRenderer(this, 128, 28);
        this.EquipABody04.setRotationPoint(-8.0F, 0.0F, 0.0F);
        this.EquipABody04.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 81);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.2F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 12, 8, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 98, 31);
        this.Cloth01.setRotationPoint(0.0F, -12.1F, -0.6F);
        this.Cloth01.addBox(-4.0F, 0.0F, -4.0F, 8, 3, 7, 0.0F);
        this.setRotateAngle(Cloth01, 0.17453292519943295F, 0.0F, 0.0F);
        this.HairU01 = new ModelRenderer(this, 82, 0);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -6.5F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 14, 6, 0.0F);
        this.EquipD04 = new ModelRenderer(this, 128, 90);
        this.EquipD04.setRotationPoint(0.0F, -37.0F, 0.0F);
        this.EquipD04.addBox(-4.5F, 0.0F, 0.0F, 9, 11, 1, 0.0F);
        this.HairL02 = new ModelRenderer(this, 86, 101);
        this.HairL02.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 4, 0.0F);
        this.setRotateAngle(HairL02, 0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.EquipABody05 = new ModelRenderer(this, 128, 13);
        this.EquipABody05.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.EquipABody05.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.EquipB01 = new ModelRenderer(this, 62, 22);
        this.EquipB01.setRotationPoint(0.0F, -4.2F, 0.7F);
        this.EquipB01.addBox(-7.0F, -6.0F, -6.0F, 14, 6, 6, 0.0F);
        this.setRotateAngle(EquipB01, 0.6981317007977318F, 0.0F, 0.0F);
        this.EquipABody05c = new ModelRenderer(this, 128, 13);
        this.EquipABody05c.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.EquipABody05c.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 83);
        this.LegRight02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Cloth03 = new ModelRenderer(this, 0, 64);
        this.Cloth03.setRotationPoint(0.0F, 0.5F, -4.6F);
        this.Cloth03.addBox(0.0F, -4.0F, -1.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Cloth03, 0.13962634015954636F, -0.3141592653589793F, 0.13962634015954636F);
        this.EquipGlove = new ModelRenderer(this, 128, 103);
        this.EquipGlove.setRotationPoint(2.5F, 6.3F, -2.5F);
        this.EquipGlove.addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 0, 8);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.ClothBody01 = new ModelRenderer(this, 0, 113);
        this.ClothBody01.setRotationPoint(6.0F, -3.8F, -2.3F);
        this.ClothBody01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 5, 0.0F);
        this.setRotateAngle(ClothBody01, 0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.Hair02 = new ModelRenderer(this, 192, 25);
        this.Hair02.setRotationPoint(0.0F, 12.5F, 6.2F);
        this.Hair02.addBox(-7.0F, 0.0F, -4.5F, 14, 13, 7, 0.0F);
        this.setRotateAngle(Hair02, -0.10471975511965977F, 0.0F, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 0, 44);
        this.Skirt02.setRotationPoint(0.0F, 4.0F, -0.6F);
        this.Skirt02.addBox(-9.0F, 0.0F, -6.0F, 18, 8, 11, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipABelt02 = new ModelRenderer(this, 0, 27);
        this.EquipABelt02.setRotationPoint(3.0F, 2.0F, 0.0F);
        this.EquipABelt02.addBox(0.0F, 0.0F, -0.5F, 17, 0, 1, 0.0F);
        this.setRotateAngle(EquipABelt02, 0.0F, 0.0F, -0.7740535232594852F);
        this.ArmLeft01 = new ModelRenderer(this, 0, 8);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -8.7F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.20943951023931953F, 0.0F, -0.20943951023931953F);
        this.Cloth04 = new ModelRenderer(this, 0, 64);
        this.Cloth04.setRotationPoint(-0.1F, 0.6F, -4.5F);
        this.Cloth04.addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(Cloth04, -0.13962634015954636F, -0.3490658503988659F, -0.20943951023931953F);
        this.ClothHL03 = new ModelRenderer(this, 40, 0);
        this.ClothHL03.setRotationPoint(-1.0F, 4.0F, 0.0F);
        this.ClothHL03.addBox(-2.5F, 0.0F, -3.5F, 8, 5, 7, 0.0F);
        this.EquipABelt01 = new ModelRenderer(this, 0, 27);
        this.EquipABelt01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipABelt01.addBox(-12.0F, 0.0F, -0.5F, 12, 0, 1, 0.0F);
        this.setRotateAngle(EquipABelt01, 0.3490658503988659F, 0.13962634015954636F, -0.5235987755982988F);
        this.EquipD02 = new ModelRenderer(this, 58, 55);
        this.EquipD02.setRotationPoint(5.6F, 3.0F, 0.0F);
        this.EquipD02.addBox(-5.5F, 0.0F, 0.0F, 11, 9, 1, 0.0F);
        this.setRotateAngle(EquipD02, -0.03490658503988659F, 1.4660765716752369F, 3.141592653589793F);
        this.Cloth06 = new ModelRenderer(this, 104, 21);
        this.Cloth06.setRotationPoint(-6.0F, -11.6F, 3.2F);
        this.Cloth06.addBox(0.0F, 0.0F, 0.0F, 12, 7, 0, 0.0F);
        this.setRotateAngle(Cloth06, 0.06981317007977318F, 0.0F, 0.0F);
        this.EquipC02 = new ModelRenderer(this, 64, 7);
        this.EquipC02.setRotationPoint(-8.0F, -0.5F, 1.5F);
        this.EquipC02.addBox(-2.5F, 0.0F, -3.0F, 3, 9, 6, 0.0F);
        this.setRotateAngle(EquipC02, 0.17453292519943295F, 0.0F, 0.3490658503988659F);
        this.Hair01 = new ModelRenderer(this, 189, 0);
        this.Hair01.setRotationPoint(0.0F, 9.5F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 14, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.20943951023931953F, 0.0F, 0.0F);
        this.Cloth07 = new ModelRenderer(this, 24, 80);
        this.Cloth07.setRotationPoint(0.5F, 0.5F, -7.0F);
        this.Cloth07.addBox(0.0F, 0.0F, 0.0F, 3, 8, 1, 0.0F);
        this.setRotateAngle(Cloth07, -0.17453292519943295F, -0.13962634015954636F, -0.20943951023931953F);
        this.EquipE01 = new ModelRenderer(this, 128, 37);
        this.EquipE01.setRotationPoint(-2.8F, 10.5F, -3.0F);
        this.EquipE01.addBox(-0.5F, -0.5F, -20.0F, 1, 1, 20, 0.0F);
        this.setRotateAngle(EquipE01, 0.05235987755982988F, 0.0F, 0.0F);
        this.ClothHL01_1 = new ModelRenderer(this, 43, 1);
        this.ClothHL01_1.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.ClothHL01_1.addBox(-3.5F, 0.0F, -3.0F, 6, 5, 6, 0.0F);
        this.Tail01 = new ModelRenderer(this, 63, 36);
        this.Tail01.setRotationPoint(0.0F, 6.5F, 1.0F);
        this.Tail01.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 8, 0.0F);
        this.setRotateAngle(Tail01, -0.8726646259971648F, 0.0F, 0.0F);
        this.ClothHL02_1 = new ModelRenderer(this, 42, 1);
        this.ClothHL02_1.setRotationPoint(0.0F, 4.5F, 0.0F);
        this.ClothHL02_1.addBox(-4.0F, 0.0F, -3.0F, 7, 5, 6, 0.0F);
        this.EquipABody05b = new ModelRenderer(this, 128, 13);
        this.EquipABody05b.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.EquipABody05b.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.EquipAArr02b = new ModelRenderer(this, 0, 48);
        this.EquipAArr02b.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.EquipAArr02b.addBox(-0.5F, -2.7F, 0.5F, 2, 4, 0, 0.0F);
        this.EquipE02 = new ModelRenderer(this, 128, 74);
        this.EquipE02.setRotationPoint(0.0F, 0.0F, -19.7F);
        this.EquipE02.addBox(-0.5F, -0.5F, -15.0F, 1, 1, 15, 0.0F);
        this.setRotateAngle(EquipE02, -0.4886921905584123F, 0.0F, 0.0F);
        this.Ear01 = new ModelRenderer(this, 20, 0);
        this.Ear01.mirror = true;
        this.Ear01.setRotationPoint(-3.8F, -14.5F, 5.7F);
        this.Ear01.addBox(-1.5F, 0.0F, -6.0F, 3, 6, 6, 0.0F);
        this.setRotateAngle(Ear01, -0.7853981633974483F, 0.2617993877991494F, -0.13962634015954636F);
        this.ClothHL02 = new ModelRenderer(this, 42, 1);
        this.ClothHL02.setRotationPoint(0.0F, 4.5F, 0.0F);
        this.ClothHL02.addBox(-3.0F, 0.0F, -3.0F, 7, 5, 6, 0.0F);
        this.EquipE06 = new ModelRenderer(this, 135, 81);
        this.EquipE06.setRotationPoint(0.0F, 0.0F, 11.7F);
        this.EquipE06.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 8, 0.0F);
        this.setRotateAngle(EquipE06, -0.2792526803190927F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 0, 8);
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipC01 = new ModelRenderer(this, 128, 0);
        this.EquipC01.setRotationPoint(-1.2F, 6.4F, -0.8F);
        this.EquipC01.addBox(-9.0F, 0.0F, -4.0F, 18, 1, 12, 0.0F);
        this.setRotateAngle(EquipC01, 0.0F, 0.08726646259971647F, -0.18203784098300857F);
        this.EquipE04 = new ModelRenderer(this, 133, 58);
        this.EquipE04.setRotationPoint(0.0F, 0.0F, -0.2F);
        this.EquipE04.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 15, 0.0F);
        this.setRotateAngle(EquipE04, -0.13962634015954636F, 0.0F, 0.0F);
        this.Tail03 = new ModelRenderer(this, 63, 36);
        this.Tail03.setRotationPoint(0.0F, 0.0F, 7.5F);
        this.Tail03.addBox(-1.0F, -1.0F, -0.4F, 2, 2, 6, 0.0F);
        this.setRotateAngle(Tail03, 0.5235987755982988F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-2.8F, -8.5F, -3.5F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, 0.10471975511965977F, 0.08726646259971647F);
        this.EquipE03 = new ModelRenderer(this, 134, 80);
        this.EquipE03.setRotationPoint(0.0F, 0.0F, -14.7F);
        this.EquipE03.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.setRotateAngle(EquipE03, 0.3141592653589793F, 0.0F, 0.0F);
        this.HairMain = new ModelRenderer(this, 48, 34);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.EquipAArr03a = new ModelRenderer(this, 4, 47);
        this.EquipAArr03a.setRotationPoint(-1.6F, 0.0F, 0.4F);
        this.EquipAArr03a.addBox(0.0F, -4.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(EquipAArr03a, -0.03490658503988659F, -0.2617993877991494F, 0.0F);
        this.Cloth05 = new ModelRenderer(this, 44, 19);
        this.Cloth05.setRotationPoint(-5.8F, -7.9F, 0.0F);
        this.Cloth05.addBox(-1.0F, -3.5F, -4.6F, 1, 7, 8, 0.0F);
        this.setRotateAngle(Cloth05, 0.08726646259971647F, 0.13962634015954636F, 0.13962634015954636F);
        this.ArmLeft01.addChild(this.ClothHL01);
        this.Head.addChild(this.Ear02);
        this.BodyMain.addChild(this.ClothBody02);
        this.EquipABody04.addChild(this.EquipAArr01a);
        this.EquipD02.addChild(this.EquipD03);
        this.Butt.addChild(this.LegLeft01);
        this.LegLeft01.addChild(this.LegLeft02);
        this.Hair.addChild(this.Ahoke);
        this.Skirt01.addChild(this.Cloth08);
        this.Hair.addChild(this.HairR01);
        this.LegRight02.addChild(this.EquipSR01);
        this.EquipAArr03a.addChild(this.EquipAArr03b);
        this.HairR01.addChild(this.HairR02);
        this.Tail01.addChild(this.Tail02);
        this.Skirt01.addChild(this.Cloth09);
        this.BodyMain.addChild(this.Head);
        this.BodyMain.addChild(this.BoobL);
        this.EquipABody04.addChild(this.EquipAArr02a);
        this.BodyMain.addChild(this.Butt);
        this.EquipABelt01.addChild(this.EquipABody01);
        this.BodyMain.addChild(this.EquipABase);
        this.EquipAArr01a.addChild(this.EquipAArr01b);
        this.ArmRight01.addChild(this.EquipD01);
        this.EquipABody01.addChild(this.EquipABody02);
        this.BodyMain.addChild(this.Cloth02);
        this.BodyMain.addChild(this.ArmRight01);
        this.Butt.addChild(this.LegRight01);
        this.EquipABody01.addChild(this.EquipABody03);
        this.Butt.addChild(this.Skirt01);
        this.EquipE04.addChild(this.EquipE05);
        this.ClothHL02_1.addChild(this.ClothHL03_1);
        this.Skirt01.addChild(this.EquipS01);
        this.Hair.addChild(this.HairL01);
        this.LegLeft02.addChild(this.EquipSL01);
        this.EquipABody01.addChild(this.EquipABody04);
        this.Head.addChild(this.Hair);
        this.BodyMain.addChild(this.Cloth01);
        this.Hair.addChild(this.HairU01);
        this.EquipD03.addChild(this.EquipD04);
        this.HairL01.addChild(this.HairL02);
        this.EquipABody04.addChild(this.EquipABody05);
        this.BodyMain.addChild(this.EquipB01);
        this.EquipABody05b.addChild(this.EquipABody05c);
        this.LegRight01.addChild(this.LegRight02);
        this.Cloth02.addChild(this.Cloth03);
        this.ArmRight02.addChild(this.EquipGlove);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.BodyMain.addChild(this.ClothBody01);
        this.Hair01.addChild(this.Hair02);
        this.Skirt01.addChild(this.Skirt02);
        this.EquipABody05c.addChild(this.EquipABelt02);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Cloth02.addChild(this.Cloth04);
        this.ClothHL02.addChild(this.ClothHL03);
        this.EquipABase.addChild(this.EquipABelt01);
        this.EquipD01.addChild(this.EquipD02);
        this.BodyMain.addChild(this.Cloth06);
        this.EquipC01.addChild(this.EquipC02);
        this.HairMain.addChild(this.Hair01);
        this.Skirt01.addChild(this.Cloth07);
        this.ArmLeft02.addChild(this.EquipE01);
        this.ArmRight01.addChild(this.ClothHL01_1);
        this.Butt.addChild(this.Tail01);
        this.ClothHL01_1.addChild(this.ClothHL02_1);
        this.EquipABody05.addChild(this.EquipABody05b);
        this.EquipAArr02a.addChild(this.EquipAArr02b);
        this.EquipE01.addChild(this.EquipE02);
        this.Head.addChild(this.Ear01);
        this.ClothHL01.addChild(this.ClothHL02);
        this.EquipE05.addChild(this.EquipE06);
        this.ArmRight01.addChild(this.ArmRight02);
        this.BodyMain.addChild(this.EquipC01);
        this.EquipE01.addChild(this.EquipE04);
        this.Tail02.addChild(this.Tail03);
        this.BodyMain.addChild(this.BoobR);
        this.EquipE02.addChild(this.EquipE03);
        this.Head.addChild(this.HairMain);
        this.EquipABody04.addChild(this.EquipAArr03a);
        this.BodyMain.addChild(this.Cloth05);
        
      //glow cube
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -11.8F, -1.0F);
        this.setRotateAngle(GlowHead, 0.10471975511965977F, 0.0F, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        
        //glow bone
        this.GlowBodyMain.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        
    }

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
    	GL11.glScalef(scale, scale, scale);
    	GL11.glTranslatef(0F, offsetY + 1.55F, 0F);
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	//light part:eye, cannon, ...etc
    	GL11.glDisable(GL11.GL_LIGHTING);
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glPopMatrix();
    }
    
	//model animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		IShipEmotion ent = (IShipEmotion)entity;

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
    	//頭部
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
    }
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {
    	GL11.glTranslatef(0F, 1.5F, 0F);
		setFace(4);
	  	
	  	if(((IShipFloating)ent).getShipDepth() > 0) {
	  		this.EquipSL01.isHidden = false;
	    	this.EquipSR01.isHidden = false;
    	}
    	else {
    		this.EquipSL01.isHidden = true;
        	this.EquipSR01.isHidden = true;
    	}
    	
	  	//Body
	  	this.Skirt01.rotateAngleX = -0.2F;
	  	this.Skirt02.rotateAngleX = -0.3F;
	    //arm 
		this.ArmRight02.offsetX = 0F;
	    
	    if(ent.getStateEmotion(ID.S.State) > ID.State.EQUIP00) {
	    	this.ArmRight01.rotateAngleZ += 0.15F;
	    }
	    
	    if(ent.getStateEmotion(ID.S.State) > ID.State.EQUIP02) {
	    	this.ArmLeft01.rotateAngleZ -= 0.15F;
	    	
	    	//tail
	    	this.Tail01.rotateAngleX = -1.85F;
	    	this.Tail02.rotateAngleX = -0.6F;
	    	this.Tail03.rotateAngleX = -0.6F;
	    }
	    
		//leg
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		
		//equip
		this.EquipE01.rotateAngleX = 0.05F;
		this.EquipE01.rotateAngleY = -0.2F;
		this.EquipE01.rotateAngleZ = 0F;
		this.EquipE01.offsetX = 0F;
		this.EquipE02.rotateAngleX = -0.4887F;
		this.EquipE05.rotateAngleX = 0.4538F;
		this.EquipD02.rotateAngleX = 0.25F;
		this.EquipD02.rotateAngleY = 1.6755F;
		this.EquipD02.rotateAngleZ = 3.1416F;
		this.EquipD02.offsetY = 0F;
		this.EquipS01.rotateAngleX = -0.95F;
		
		//頭部
	  	this.Head.rotateAngleX = -0.2618F;
	  	this.Head.rotateAngleY = 0F;
	  	this.Head.rotateAngleZ = 0F;
	    //胸部
  	    this.BoobL.rotateAngleX = -1.0F;
  	    this.BoobR.rotateAngleX = -1.0F;
	  	//Body
  	    this.Ahoke.rotateAngleY = -1.0F;
	  	this.BodyMain.rotateAngleX = 1.2217F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 1.2217F;
	  	this.Butt.rotateAngleX = -0.05F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.2F;
	  	this.Hair01.rotateAngleZ = -0.36F;
	  	this.Hair02.rotateAngleX = 0.2F;
	  	this.Hair02.rotateAngleZ = -0.15F;
	  	this.HairL01.rotateAngleZ = 0.0873F;
	  	this.HairL02.rotateAngleZ = -0.3142F;
	  	this.HairR01.rotateAngleZ = -0.0873F;
	  	this.HairR02.rotateAngleZ = -1.2217F;
		this.HairL01.rotateAngleX = - 0.28F;
	  	this.HairL02.rotateAngleX = 0.15F;
	  	this.HairR01.rotateAngleX = -0.35F;
	  	this.HairR02.rotateAngleX = 0.18F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -0.35F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = -3F;
	    this.ArmLeft02.rotateAngleX = 0F;
    	this.ArmRight01.rotateAngleX = -0.35F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -0.35F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = -0.8727F;
		//leg
		this.LegLeft01.rotateAngleX = -0.14F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.09F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleX = -1.2217F;
		this.LegRight01.rotateAngleY = -0.5236F;
		this.LegRight01.rotateAngleZ = 0F;
		this.LegRight02.rotateAngleX = 1.0472F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetZ = 0F;
    }
    
	//雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {   
  		float angleX = MathHelper.cos(f2*0.08F + f * 0.25F);
  		float angleX1 = MathHelper.cos(f2*0.1F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.1F + 0.6F + f * 0.5F);
  		float angleX3 = MathHelper.cos(f2*0.1F + 0.9F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		float headX = 0F;
  		float headZ = 0F;

    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.2793F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.5F - 0.1396F;  //LegRight01
	  	
	  	if(((IShipFloating)ent).getShipDepth() > 0) {
	  		this.EquipSL01.isHidden = false;
	    	this.EquipSR01.isHidden = false;
    	}
    	else {
    		this.EquipSL01.isHidden = true;
        	this.EquipSR01.isHidden = true;
    	}
    	
  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.0174532925F + 0.1047F;
	  	this.Head.rotateAngleY = f3 * 0.008F;
	    
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.8F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.8F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.45F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.3142F;
	  	this.Skirt01.rotateAngleX = -0.14F;
	  	this.Skirt02.rotateAngleX = -0.0873F;
	  	//cloth
	  	this.ClothHL02_1.offsetY = 0F;
    	this.ClothHL03_1.offsetY = 0F;
	  	//hair
    	this.Hair01.rotateAngleX = angleX * 0.03F + 0.23F;
    	this.Hair01.rotateAngleZ = 0F;
    	this.Hair02.rotateAngleX = -angleX * 0.03F - 0.1F;
    	this.Hair02.rotateAngleZ = 0F;
    	this.HairL01.rotateAngleX = -0.16F;
	  	this.HairL02.rotateAngleX = 0.1745F;
	  	this.HairR01.rotateAngleX = -0.14F;
	  	this.HairR02.rotateAngleX = 0.174F;
	  	this.HairL01.rotateAngleZ = -0.0873F;
	  	this.HairL02.rotateAngleZ = 0.087F;
	  	this.HairR01.rotateAngleZ = 0.0873F;
	  	this.HairR02.rotateAngleZ = -0.053F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F + 0.21F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.21F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.25F + 0.05F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.21F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
	    
	    if(ent.getStateEmotion(ID.S.State) > ID.State.EQUIP00) {
	    	this.ArmRight01.rotateAngleZ += 0.15F;
	    }
	    
	    if(ent.getStateEmotion(ID.S.State) > ID.State.EQUIP02) {
	    	this.ArmLeft01.rotateAngleZ -= 0.15F;
	    	
	    	//tail
	    	this.Tail01.rotateAngleX = angleX1 * 0.5F - 0.7F;
	    	this.Tail02.rotateAngleX = -angleX2 * 0.5F;
	    	this.Tail03.rotateAngleX = -angleX3 * 0.5F;
	    }
	    
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.1396F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.1396F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		
		//equip
		this.EquipE01.rotateAngleX = 0.05F;
		this.EquipE01.rotateAngleY = 0F;
		this.EquipE01.rotateAngleZ = 0F;
		this.EquipE01.offsetX = 0F;
		this.EquipE02.rotateAngleX = -0.4887F;
		this.EquipE05.rotateAngleX = 0.4538F;
		this.EquipD01.rotateAngleX = 0F;
		this.EquipD02.rotateAngleX = -0.05F;
		this.EquipD02.rotateAngleY = 1.6755F;
		this.EquipD02.rotateAngleZ = 3.1416F;
		this.EquipD02.offsetY = 0F;
		this.EquipS01.rotateAngleX = -0.28F;

	    if(ent.getIsSprinting() || f1 > 0.1F) {	//奔跑動作
	    	//hair
	    	this.Hair01.rotateAngleX = angleAdd1 * 0.1F + f1 * 0.4F;
	    	this.Hair02.rotateAngleX += 0.5F;
		    //arm 
		    this.ArmLeft01.rotateAngleZ += f1 * -0.2F;
		    this.ArmRight01.rotateAngleZ += f1 * 0.2F;
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if(ent.getIsSneaking()) {		//潛行, 蹲下動作
	    	GL11.glTranslatef(0F, 0.1F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.8378F;
		    //arm 
		    this.ArmLeft01.rotateAngleX = -0.7F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
		    this.ArmRight01.rotateAngleX = -0.7F;
		    this.ArmRight01.rotateAngleZ = -0.2618F;
		    //equip
		    this.EquipD02.rotateAngleX = 0.15F;
		    this.EquipE01.rotateAngleY = 1.3F;
		    //tail
		    this.Tail01.rotateAngleX += 1.3F;
  		}//end if sneaking
  		
	    if(ent.getIsSitting() || ent.getIsRiding()) {  //騎乘動作
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
		    	setFace(1);
		    	GL11.glTranslatef(0F, 1.6F, 0F);
		    	//head
		    	int nodf2 = (int)f2 % 60;
		    	this.Head.rotateAngleX = 0.4F;
		    	if(nodf2 < 30) {
		    		if(nodf2 < 6) {
		    			this.Head.rotateAngleX = nodf2 * 0.02F + 0.4F;
		    		}
		    		else if(nodf2 < 11) {
		    			this.Head.rotateAngleX = (nodf2 - 5) * 0.03F + 0.5F;
		    		}
		    		else if(nodf2 < 14) {
		    			this.Head.rotateAngleX = (nodf2 - 10) * -0.09F + 0.65F;
		    		}
		    	}
		    	this.Head.rotateAngleY = 0F;
		    	this.Head.rotateAngleZ = 0F;
		    	//body
		    	this.Butt.rotateAngleX = -0.2F;
				this.Skirt01.rotateAngleX = -0.26F;
				this.Skirt02.rotateAngleX = -0.45F;
				//arm
				this.ArmLeft01.rotateAngleX = 0.4F;
				this.ArmLeft01.rotateAngleZ = -0.2618F;
				this.ArmRight01.rotateAngleX = 0.4F;
				this.ArmRight01.rotateAngleZ = 0.2618F;
				//leg
				addk1 = -0.9F;
				addk2 = -0.9F;
				this.LegLeft01.rotateAngleZ = -0.14F;
				this.LegLeft02.rotateAngleX = 1.2217F;
				this.LegLeft02.rotateAngleY = 1.2217F;
				this.LegLeft02.rotateAngleZ = -1.0472F;
				this.LegLeft02.offsetX = 0.17F;
				this.LegLeft02.offsetY = -0.03F;
				this.LegLeft02.offsetZ = 0.2F;
				this.LegRight01.rotateAngleZ = 0.14F;
				this.LegRight02.rotateAngleX = 1.2217F;
				this.LegRight02.rotateAngleY = -1.2217F;
				this.LegRight02.rotateAngleZ = 1.0472F;
				this.LegRight02.offsetX = -0.17F;
				this.LegRight02.offsetY = -0.03F;
				this.LegRight02.offsetZ = 0.2F;
				//tail
				this.Tail01.rotateAngleX += 1.7F;
				this.Tail02.rotateAngleX += 0.15F;
				this.Tail03.rotateAngleX += 0.15F;
				this.Tail01.rotateAngleX *= 0.2F;
				this.Tail02.rotateAngleX *= 0.2F;
				this.Tail03.rotateAngleX *= 0.2F;
				//equip
				this.EquipE01.rotateAngleY = 1.7F;
				this.EquipE01.rotateAngleZ = 0.15F;
				this.EquipD02.rotateAngleX = 0.2F;
				this.EquipD02.offsetY = -0.5F;
	    	}
	    	else {
		    	GL11.glTranslatef(0F, offsetY + 0.9F, 0F);
		    	//Body
			  	this.Head.rotateAngleX += 0.1047F;
		    	this.BodyMain.rotateAngleX = -0.1396F;
			  	this.Butt.rotateAngleX = 0.1396F;
				//arm 
			  	this.ArmLeft01.rotateAngleX = -0.4F;
			  	this.ArmLeft01.rotateAngleZ = 0.2618F;
		    	this.ArmRight01.rotateAngleX = -0.4F;
				this.ArmRight01.rotateAngleZ = -0.2618F;
			  	//leg
			  	addk1 = -1.0472F;
			  	addk2 = -1.0472F;
			  	this.LegLeft01.rotateAngleY = 0.0524F;
				this.LegLeft01.rotateAngleZ = 0F;
				this.LegLeft02.offsetZ = 0.38F;
				this.LegLeft02.rotateAngleX = 2.5831F;
				this.LegLeft02.rotateAngleZ = 0.0175F;
				this.LegRight01.rotateAngleY = -0.0524F;
				this.LegRight01.rotateAngleZ = 0F;
				this.LegRight02.offsetZ = 0.38F;
				this.LegRight02.rotateAngleX = 2.5831F;
				this.LegRight02.rotateAngleZ = -0.0175F;
				//tail
				this.Tail01.rotateAngleX += 1F;
				this.Tail02.rotateAngleX += 0.15F;
				this.Tail03.rotateAngleX += 0.15F;
				//equip
				this.EquipE01.rotateAngleY = 1.7F;
				this.EquipE01.rotateAngleZ = -0.2F;
				this.EquipD02.rotateAngleX = 0.2F;
				this.EquipD02.offsetY = -0.5F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if(ent.getAttackTime() > 20) {
	    	//set start time
	    	if(ent.getAttackTime() >= 49) ent.setAttackAniTick(0);
	    	int tick = ent.getAttackAniTick();
	    	float parTick = f2 - (int)f2 + tick;
	    	
	    	//head
		    this.Head.rotateAngleX = 0F;
	    	this.Head.rotateAngleY = -1.31F;
	    	//body
	    	this.BodyMain.rotateAngleX = -0.05F;
	    	this.BodyMain.rotateAngleY = 1.4F;
	    	//cloth
	    	this.ClothHL02_1.offsetY = -0.17F;
	    	this.ClothHL03_1.offsetY = -0.2F;
	    	//arm
	    	this.ArmLeft01.rotateAngleX = -1.5708F;
	    	this.ArmLeft01.rotateAngleY = -1.35F;
		  	this.ArmLeft01.rotateAngleZ = 0F;
	    	this.ArmRight01.rotateAngleX = 0F;
	    	this.ArmRight01.rotateAngleY = 2.1817F;
			this.ArmRight01.rotateAngleZ = 1.5708F;
			this.ArmRight02.rotateAngleZ = -2.44F + 0.15F * parTick;  //-2.44~-1.57
			if(this.ArmRight02.rotateAngleZ > -1.57F) this.ArmRight02.rotateAngleZ = -1.57F;
			this.ArmRight02.offsetX = 0.31F;
			//leg
			addk1 = -0.35F;
		  	addk2 = -0.23F;
			this.LegLeft01.rotateAngleZ = -0.14F;
			this.LegRight01.rotateAngleZ = 0.14F;
			//equip
			this.EquipE01.isHidden = false;
			this.EquipD01.rotateAngleX = 1.3F;
			this.EquipD02.rotateAngleX = -1.15F;
			this.EquipD02.rotateAngleY = -2.0F;
			this.EquipD02.rotateAngleZ = 1.7453F;
			this.EquipE01.rotateAngleX = 0.2618F;
			this.EquipE01.rotateAngleZ = -0.23F;
			this.EquipE01.offsetX = -0.15F;
		    this.EquipE02.rotateAngleX = -0.7F + 0.1F * parTick;  //-0.7~-0.49
		    if(this.EquipE02.rotateAngleX > -0.49F) this.EquipE02.rotateAngleX = -0.49F;
		    this.EquipE05.rotateAngleX = 0.7F - 0.1F * parTick;  //0.7~0.45
		    if(this.EquipE05.rotateAngleX < 0.45F) this.EquipE05.rotateAngleX = 0.45F;
		    if(tick > 5 && tick < 12) {
		    	this.EquipE01.rotateAngleX -= 0.36F * MathHelper.sin(parTick * 0.2244F);
		    	this.EquipE01.rotateAngleZ -= 5F * MathHelper.sin(parTick * 0.2244F);
		    }
		    if(tick >= 12) {
		    	this.EquipE01.rotateAngleX = -0.1F;
		    	this.EquipE01.rotateAngleZ = -3.3F;
		    }
		    
		    //save tick
		    ent.setAttackAniTick(++tick);
	    }
	    
	    //鬢毛調整
	    headX = this.Head.rotateAngleX * -0.5F;
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleX += headX;
	  	this.Hair02.rotateAngleX += headX * 0.1F;
	    this.Hair01.rotateAngleZ += headZ;
	  	this.Hair02.rotateAngleZ += headZ * 0.7F;
	  	this.HairL01.rotateAngleZ += headZ;
	  	this.HairL02.rotateAngleZ += headZ * 0.8F;
	  	this.HairR01.rotateAngleZ += headZ;
	  	this.HairR02.rotateAngleZ += headZ * 0.8F;
		this.HairL01.rotateAngleX += angleX * 0.04F + headX;
	  	this.HairL02.rotateAngleX += angleX1 * 0.05F + headX * 0.8F;
	  	this.HairR01.rotateAngleX += angleX * 0.04F + headX;
	  	this.HairR02.rotateAngleX += angleX1 * 0.05F + headX * 0.8F;
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
  	
  	private void showEquip(IShipEmotion ent) {
  		switch(ent.getStateEmotion(ID.S.State)) {
  		case ID.State.EQUIP00:
  			this.EquipC01.isHidden = true;  //水袋
  			this.EquipB01.isHidden = false;  //胸甲
  			this.EquipS01.isHidden = false;  //裙甲
  			this.Ear01.isHidden = true;
  			this.Ear02.isHidden = true;
  			this.Tail01.isHidden = true;
  			break;
  		case ID.State.EQUIP01:
  			this.EquipC01.isHidden = false;  //水袋
  			this.EquipB01.isHidden = true;  //胸甲
  			this.EquipS01.isHidden = true;  //裙甲
  			this.Ear01.isHidden = true;
  			this.Ear02.isHidden = true;
  			this.Tail01.isHidden = true;
  			break;
  		case ID.State.EQUIP02:
  			this.EquipC01.isHidden = false;  //水袋
  			this.EquipB01.isHidden = false;  //胸甲
  			this.EquipS01.isHidden = false;  //裙甲
  			this.Ear01.isHidden = true;
  			this.Ear02.isHidden = true;
  			this.Tail01.isHidden = true;
  			break;
  		case ID.State.EQUIP03:
  			this.EquipC01.isHidden = true;  //水袋
  			this.EquipB01.isHidden = true;  //胸甲
  			this.EquipS01.isHidden = true;  //裙甲
  			this.Ear01.isHidden = false;
  			this.Ear02.isHidden = false;
  			this.Tail01.isHidden = false;
  			break;
  		case ID.State.EQUIP04:
  			this.EquipC01.isHidden = true;  //水袋
  			this.EquipB01.isHidden = false;  //胸甲
  			this.EquipS01.isHidden = false;  //裙甲
  			this.Ear01.isHidden = false;
  			this.Ear02.isHidden = false;
  			this.Tail01.isHidden = false;
  			break;
  		case ID.State.EQUIP05:
  			this.EquipC01.isHidden = false;  //水袋
  			this.EquipB01.isHidden = true;  //胸甲
  			this.EquipS01.isHidden = true;  //裙甲
  			this.Ear01.isHidden = false;
  			this.Ear02.isHidden = false;
  			this.Tail01.isHidden = false;
  			break;
  		case ID.State.EQUIP06:
  			this.EquipC01.isHidden = false;  //水袋
  			this.EquipB01.isHidden = false;  //胸甲
  			this.EquipS01.isHidden = false;  //裙甲
  			this.Ear01.isHidden = false;
  			this.Ear02.isHidden = false;
  			this.Tail01.isHidden = false;
  			break;
  		default:  //normal
  			this.EquipC01.isHidden = true;  //水袋
  			this.EquipB01.isHidden = true;  //胸甲
  			this.EquipS01.isHidden = true;  //裙甲
  			this.Ear01.isHidden = true;
  			this.Ear02.isHidden = true;
  			this.Tail01.isHidden = true;
  			break;
  		}
  		
  		switch(ent.getStateEmotion(ID.S.State2)) {
  		case ID.State.EQUIP00_2:
  			this.EquipABase.isHidden = true;//箭袋
  			this.EquipD01.isHidden = true;  //甲板
  			this.EquipE01.isHidden = false;  //弓
  			this.EquipGlove.isHidden = false;//手套
  			break;
  		case ID.State.EQUIP01_2:
  			this.EquipABase.isHidden = false;//箭袋
  			this.EquipD01.isHidden = false;  //甲板
  			this.EquipE01.isHidden = true;  //弓
  			this.EquipGlove.isHidden = true;//手套
  			break;
  		case ID.State.EQUIP02_2:
  			this.EquipABase.isHidden = false;//箭袋
  			this.EquipD01.isHidden = true;  //甲板
  			this.EquipE01.isHidden = false;  //弓
  			this.EquipGlove.isHidden = false;//手套
  			break;
  		case ID.State.EQUIP03_2:
  			this.EquipABase.isHidden = false;//箭袋
  			this.EquipD01.isHidden = false;  //甲板
  			this.EquipE01.isHidden = false;  //弓
  			this.EquipGlove.isHidden = false;//手套
  			break;
  		default:  //normal
  			this.EquipABase.isHidden = true;//箭袋
  			this.EquipD01.isHidden = true;  //甲板
  			this.EquipE01.isHidden = true;  //弓
  			this.EquipGlove.isHidden = true;//手套
  			break;
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
