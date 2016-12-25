package com.lulan.shincolle.client.model;

import java.util.Random;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerAkatsuki;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerInazuma;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelDestroyerIkazuchi - PinkaLulan
 * Created using Tabula 4.1.1  2016/4/28
 */
public class ModelDestroyerIkazuchi extends ModelBase implements IModelEmotion
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Butt;
    public ModelRenderer Head;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Cloth01;
    public ModelRenderer EquipBase;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight02;
    public ModelRenderer LegRight03;
    public ModelRenderer LegLeft02;
    public ModelRenderer LegLeft03;
    public ModelRenderer Skirt02;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face0;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Ahoke;
    public ModelRenderer HairU01;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft03;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight03;
    public ModelRenderer EquipHead01;
    public ModelRenderer EquipHead02;
    public ModelRenderer EquipHead03;
    public ModelRenderer EquipHead04;
    public ModelRenderer EquipHead05;
    public ModelRenderer Cloth02;
    public ModelRenderer EquipMain01;
    public ModelRenderer EquipC01;
    public ModelRenderer EquipMain02;
    public ModelRenderer EquipMain03;
    public ModelRenderer EquipMain04;
    public ModelRenderer EquipTL02;
    public ModelRenderer EquipTL02_1;
    public ModelRenderer EquipTL02a;
    public ModelRenderer EquipTL02b;
    public ModelRenderer EquipTL02c;
    public ModelRenderer EquipTL03;
    public ModelRenderer EquipTL02d;
    public ModelRenderer EquipTL02e;
    public ModelRenderer EquipTL02f;
    public ModelRenderer EquipTL02a_1;
    public ModelRenderer EquipTL02b_1;
    public ModelRenderer EquipTL02c_1;
    public ModelRenderer EquipTL03_1;
    public ModelRenderer EquipTL02d_1;
    public ModelRenderer EquipTL02e_1;
    public ModelRenderer EquipTL02f_1;
    public ModelRenderer EquipC02;
    public ModelRenderer EquipC03;
    public ModelRenderer EquipC04a;
    public ModelRenderer EquipC05a;
    public ModelRenderer EquipC04b;
    public ModelRenderer EquipC05b;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;
    
    private Random rand = new Random();
    private int startEmo2 = 0;
    

    public ModelDestroyerIkazuchi()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.ArmRight01 = new ModelRenderer(this, 0, 88);
        this.ArmRight01.setRotationPoint(-7.3F, -9.4F, -0.7F);
        this.ArmRight01.addBox(-3.5F, -1.0F, -3.0F, 6, 11, 6, 0.0F);
        this.setRotateAngle(ArmRight01, -0.06981317007977318F, 0.0F, 0.3490658503988659F);
        this.LegRight03 = new ModelRenderer(this, 30, 76);
        this.LegRight03.mirror = true;
        this.LegRight03.setRotationPoint(-3.0F, 8.0F, 2.9F);
        this.LegRight03.addBox(-3.5F, 0.0F, -3.5F, 7, 5, 7, 0.0F);
        this.EquipHead05 = new ModelRenderer(this, 0, 0);
        this.EquipHead05.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipHead05.addBox(-1.0F, -5.0F, 0.0F, 2, 10, 2, 0.0F);
        this.EquipC01 = new ModelRenderer(this, 0, 0);
        this.EquipC01.setRotationPoint(-7.0F, -11.0F, 9.0F);
        this.EquipC01.addBox(-2.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 105);
        this.BodyMain.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 14, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipTL02_1 = new ModelRenderer(this, 0, 0);
        this.EquipTL02_1.setRotationPoint(-5.5F, 6.0F, 4.5F);
        this.EquipTL02_1.addBox(-3.0F, -4.0F, -9.0F, 3, 8, 12, 0.0F);
        this.setRotateAngle(EquipTL02_1, 0.13962634015954636F, 0.06981317007977318F, 0.0F);
        this.EquipTL02c_1 = new ModelRenderer(this, 0, 0);
        this.EquipTL02c_1.setRotationPoint(-1.3F, 2.3F, -18.8F);
        this.EquipTL02c_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F);
        this.EquipC05a = new ModelRenderer(this, 0, 0);
        this.EquipC05a.setRotationPoint(1.5F, -3.0F, 0.0F);
        this.EquipC05a.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 6, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 81);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.3F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 12, 8, 0.0F);
        this.ArmRight03 = new ModelRenderer(this, 36, 102);
        this.ArmRight03.setRotationPoint(3.0F, 6.0F, -3.0F);
        this.ArmRight03.addBox(-2.5F, 0.0F, -2.5F, 5, 5, 5, 0.0F);
        this.EquipTL02a_1 = new ModelRenderer(this, 0, 0);
        this.EquipTL02a_1.setRotationPoint(-1.3F, 0.0F, -19.8F);
        this.EquipTL02a_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.LegLeft03 = new ModelRenderer(this, 30, 76);
        this.LegLeft03.setRotationPoint(3.0F, 8.0F, 2.9F);
        this.LegLeft03.addBox(-3.5F, 0.0F, -3.5F, 7, 5, 7, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 72);
        this.LegRight02.setRotationPoint(3.0F, 12.0F, -3.0F);
        this.LegRight02.addBox(-6.0F, 0.0F, 0.0F, 6, 10, 6, 0.0F);
        this.EquipTL02c = new ModelRenderer(this, 0, 0);
        this.EquipTL02c.setRotationPoint(1.3F, 2.3F, -18.8F);
        this.EquipTL02c.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F);
        this.EquipTL02b = new ModelRenderer(this, 0, 0);
        this.EquipTL02b.setRotationPoint(1.3F, -2.3F, -18.8F);
        this.EquipTL02b.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F);
        this.EquipTL03 = new ModelRenderer(this, 36, 45);
        this.EquipTL03.setRotationPoint(3.0F, -12.0F, 3.0F);
        this.EquipTL03.addBox(0.0F, 0.0F, -8.0F, 1, 24, 7, 0.0F);
        this.setRotateAngle(EquipTL03, 0.0F, -0.3490658503988659F, -0.08726646259971647F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.EquipTL02e = new ModelRenderer(this, 0, 0);
        this.EquipTL02e.setRotationPoint(1.3F, 0.0F, 2.5F);
        this.EquipTL02e.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.EquipMain02 = new ModelRenderer(this, 46, 9);
        this.EquipMain02.setRotationPoint(0.0F, 7.7F, 0.6F);
        this.EquipMain02.addBox(-4.5F, 0.0F, 0.0F, 9, 7, 9, 0.0F);
        this.setRotateAngle(EquipMain02, 0.6283185307179586F, 0.0F, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 84, 31);
        this.Cloth01.setRotationPoint(0.0F, -11.6F, 0.0F);
        this.Cloth01.addBox(-7.0F, 0.0F, -4.4F, 14, 7, 8, 0.0F);
        this.EquipTL02d_1 = new ModelRenderer(this, 0, 0);
        this.EquipTL02d_1.setRotationPoint(-1.3F, -2.3F, 3.0F);
        this.EquipTL02d_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipHead01 = new ModelRenderer(this, 0, 0);
        this.EquipHead01.setRotationPoint(-0.5F, 3.0F, 0.0F);
        this.EquipHead01.addBox(0.0F, 0.0F, -12.0F, 2, 3, 18, 0.0F);
        this.setRotateAngle(EquipHead01, 0.3142F, 0.0F, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipTL02f = new ModelRenderer(this, 0, 0);
        this.EquipTL02f.setRotationPoint(1.3F, 2.3F, 3.0F);
        this.EquipTL02f.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 45);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.EquipMain03 = new ModelRenderer(this, 59, 15);
        this.EquipMain03.setRotationPoint(0.0F, 9.5F, 9.0F);
        this.EquipMain03.addBox(-1.0F, 0.0F, -1.5F, 2, 6, 3, 0.0F);
        this.setRotateAngle(EquipMain03, 0.5009094953223726F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 59);
        this.LegRight01.setRotationPoint(-4.4F, 5.5F, 3.2F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.03490658503988659F, 0.0F, -0.10471975511965977F);
        this.EquipC02 = new ModelRenderer(this, 0, 0);
        this.EquipC02.setRotationPoint(-2.0F, 0.5F, 0.0F);
        this.EquipC02.addBox(-3.5F, -3.0F, -3.5F, 7, 3, 7, 0.0F);
        this.setRotateAngle(EquipC02, -0.17453292519943295F, 0.6283185307179586F, 0.0F);
        this.ArmLeft03 = new ModelRenderer(this, 36, 102);
        this.ArmLeft03.mirror = true;
        this.ArmLeft03.setRotationPoint(-3.0F, 6.0F, -3.0F);
        this.ArmLeft03.addBox(-2.5F, 0.0F, -2.5F, 5, 5, 5, 0.0F);
        this.EquipC04a = new ModelRenderer(this, 0, 0);
        this.EquipC04a.setRotationPoint(-1.5F, -3.0F, 0.0F);
        this.EquipC04a.addBox(-1.0F, -1.0F, -6.0F, 2, 2, 6, 0.0F);
        this.EquipTL02d = new ModelRenderer(this, 0, 0);
        this.EquipTL02d.setRotationPoint(1.3F, -2.3F, 3.0F);
        this.EquipTL02d.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.HairR02 = new ModelRenderer(this, 88, 103);
        this.HairR02.setRotationPoint(-1.0F, 9.0F, 0.0F);
        this.HairR02.addBox(0.0F, 0.0F, 0.0F, 1, 7, 4, 0.0F);
        this.setRotateAngle(HairR02, 0.0F, 0.0F, -1.0471975511965976F);
        this.EquipMain01 = new ModelRenderer(this, 0, 0);
        this.EquipMain01.setRotationPoint(0.0F, -4.0F, 5.0F);
        this.EquipMain01.addBox(-5.5F, -1.0F, 0.0F, 11, 9, 12, 0.0F);
        this.EquipTL02a = new ModelRenderer(this, 0, 0);
        this.EquipTL02a.setRotationPoint(1.3F, 0.0F, -19.8F);
        this.EquipTL02a.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 11, 0.0F);
        this.EquipTL02b_1 = new ModelRenderer(this, 0, 0);
        this.EquipTL02b_1.setRotationPoint(-1.3F, -2.3F, -18.8F);
        this.EquipTL02b_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 10, 0.0F);
        this.HairL02 = new ModelRenderer(this, 88, 103);
        this.HairL02.setRotationPoint(1.0F, 9.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 1, 7, 4, 0.0F);
        this.setRotateAngle(HairL02, 0.0F, 0.0F, 1.0471975511965976F);
        this.EquipHead02 = new ModelRenderer(this, 0, 0);
        this.EquipHead02.setRotationPoint(1.0F, 1.5F, -15.0F);
        this.EquipHead02.addBox(-1.5F, -7.0F, 0.0F, 3, 14, 3, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 80, 16);
        this.Skirt01.setRotationPoint(0.0F, 1.7F, -0.4F);
        this.Skirt01.addBox(-7.5F, 0.0F, 0.0F, 15, 6, 9, 0.0F);
        this.setRotateAngle(Skirt01, -0.05235987755982988F, 0.0F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 88, 101);
        this.HairL01.setRotationPoint(7.0F, -1.0F, -4.7F);
        this.HairL01.addBox(0.0F, 0.0F, 0.0F, 1, 9, 4, 0.0F);
        this.setRotateAngle(HairL01, 0.5759586531581287F, 0.2617993877991494F, -0.2617993877991494F);
        this.EquipC05b = new ModelRenderer(this, 0, 0);
        this.EquipC05b.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.EquipC05b.addBox(-0.5F, -0.5F, -10.0F, 1, 1, 10, 0.0F);
        this.EquipC04b = new ModelRenderer(this, 0, 0);
        this.EquipC04b.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.EquipC04b.addBox(-0.5F, -0.5F, -10.0F, 1, 1, 10, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 76, 0);
        this.Skirt02.setRotationPoint(0.0F, 3.5F, -0.4F);
        this.Skirt02.addBox(-8.0F, 0.0F, 0.0F, 16, 6, 10, 0.0F);
        this.setRotateAngle(Skirt02, -0.05235987755982988F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 88);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.5F, 10.0F, 3.0F);
        this.ArmLeft02.addBox(-6.0F, 0.0F, -6.0F, 6, 8, 6, 0.0F);
        this.EquipTL02f_1 = new ModelRenderer(this, 0, 0);
        this.EquipTL02f_1.setRotationPoint(-1.3F, 2.3F, 3.0F);
        this.EquipTL02f_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipMain04 = new ModelRenderer(this, 0, 26);
        this.EquipMain04.setRotationPoint(0.0F, -16.5F, 9.0F);
        this.EquipMain04.addBox(-3.0F, 0.0F, -3.0F, 6, 16, 6, 0.0F);
        this.setRotateAngle(EquipMain04, -0.08726646259971647F, 0.0F, 0.0F);
        this.Cloth02 = new ModelRenderer(this, 22, 48);
        this.Cloth02.setRotationPoint(0.0F, 4.8F, -4.3F);
        this.Cloth02.addBox(-3.0F, 0.0F, 0.0F, 6, 10, 0, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 0, 88);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.3F, -9.4F, -0.7F);
        this.ArmLeft01.addBox(-2.5F, -1.0F, -3.0F, 6, 11, 6, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.20943951023931953F, 0.0F, -0.3490658503988659F);
        this.EquipTL02 = new ModelRenderer(this, 0, 0);
        this.EquipTL02.setRotationPoint(5.5F, 6.0F, 4.5F);
        this.EquipTL02.addBox(0.0F, -4.0F, -9.0F, 3, 8, 12, 0.0F);
        this.setRotateAngle(EquipTL02, 0.13962634015954636F, -0.06981317007977318F, 0.0F);
        this.Hair01 = new ModelRenderer(this, 36, 26);
        this.Hair01.setRotationPoint(0.0F, 6.8F, 1.1F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 10, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.20943951023931953F, 0.0F, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 59);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.4F, 5.5F, 3.2F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.13962634015954636F, 0.0F, 0.10471975511965977F);
        this.EquipTL03_1 = new ModelRenderer(this, 36, 45);
        this.EquipTL03_1.setRotationPoint(-3.0F, -12.0F, 3.0F);
        this.EquipTL03_1.addBox(-1.0F, 0.0F, -8.0F, 1, 24, 7, 0.0F);
        this.setRotateAngle(EquipTL03_1, 0.0F, 0.3490658503988659F, 0.08726646259971647F);
        this.Butt = new ModelRenderer(this, 54, 66);
        this.Butt.setRotationPoint(0.0F, 3.0F, -4.0F);
        this.Butt.addBox(-7.0F, 0.0F, 0.0F, 14, 7, 8, 0.0F);
        this.setRotateAngle(Butt, 0.20943951023931953F, 0.0F, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 72);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(-3.0F, 12.0F, -3.0F);
        this.LegLeft02.addBox(0.0F, 0.0F, 0.0F, 6, 10, 6, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -11.8F, -1.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.setRotateAngle(Head, 0.10471975511965977F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 0, 37);
        this.Ahoke.setRotationPoint(-1.0F, -6F, -6F);
        this.Ahoke.addBox(0.0F, -11F, -7F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 1.0471975511965976F, 1.0471975511965976F, 0.0F);
        this.EquipTL02e_1 = new ModelRenderer(this, 0, 0);
        this.EquipTL02e_1.setRotationPoint(-1.3F, 0.0F, 2.5F);
        this.EquipTL02e_1.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 2, 0.0F);
        this.EquipC03 = new ModelRenderer(this, 0, 0);
        this.EquipC03.setRotationPoint(0.0F, -5.0F, -2.0F);
        this.EquipC03.addBox(-3.0F, 0.0F, 0.0F, 6, 2, 5, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.HairR01 = new ModelRenderer(this, 88, 101);
        this.HairR01.setRotationPoint(-7.0F, -1.0F, -4.7F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 1, 9, 4, 0.0F);
        this.setRotateAngle(HairR01, 0.5759586531581287F, -0.2617993877991494F, 0.2617993877991494F);
        this.ArmRight02 = new ModelRenderer(this, 24, 88);
        this.ArmRight02.setRotationPoint(-3.5F, 10.0F, 3.0F);
        this.ArmRight02.addBox(0.0F, 0.0F, -6.0F, 6, 8, 6, 0.0F);
        this.EquipHead03 = new ModelRenderer(this, 0, 0);
        this.EquipHead03.setRotationPoint(0.0F, 4.8F, 2.5F);
        this.EquipHead03.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(EquipHead03, -0.2617993877991494F, 0.0F, 0.0F);
        this.EquipHead04 = new ModelRenderer(this, 0, 0);
        this.EquipHead04.setRotationPoint(0.0F, -4.8F, 2.5F);
        this.EquipHead04.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 6, 0.0F);
        this.setRotateAngle(EquipHead04, 0.2617993877991494F, 0.0F, 0.0F);
        this.BodyMain.addChild(this.ArmRight01);
        this.LegRight02.addChild(this.LegRight03);
        this.EquipHead02.addChild(this.EquipHead05);
        this.EquipBase.addChild(this.EquipC01);
        this.EquipMain01.addChild(this.EquipTL02_1);
        this.EquipTL02_1.addChild(this.EquipTL02c_1);
        this.EquipC02.addChild(this.EquipC05a);
        this.Head.addChild(this.Hair);
        this.ArmRight02.addChild(this.ArmRight03);
        this.EquipTL02_1.addChild(this.EquipTL02a_1);
        this.LegLeft02.addChild(this.LegLeft03);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipTL02.addChild(this.EquipTL02c);
        this.EquipTL02.addChild(this.EquipTL02b);
        this.EquipTL02.addChild(this.EquipTL03);
        this.Head.addChild(this.HairMain);
        this.EquipTL02.addChild(this.EquipTL02e);
        this.EquipMain01.addChild(this.EquipMain02);
        this.BodyMain.addChild(this.EquipBase);
        this.BodyMain.addChild(this.Cloth01);
        this.EquipTL02_1.addChild(this.EquipTL02d_1);
        this.ArmRight03.addChild(this.EquipHead01);
        this.EquipTL02.addChild(this.EquipTL02f);
        this.Hair.addChild(this.HairU01);
        this.EquipMain01.addChild(this.EquipMain03);
        this.Butt.addChild(this.LegRight01);
        this.EquipC01.addChild(this.EquipC02);
        this.ArmLeft02.addChild(this.ArmLeft03);
        this.EquipC02.addChild(this.EquipC04a);
        this.EquipTL02.addChild(this.EquipTL02d);
        this.HairR01.addChild(this.HairR02);
        this.EquipBase.addChild(this.EquipMain01);
        this.EquipTL02.addChild(this.EquipTL02a);
        this.EquipTL02_1.addChild(this.EquipTL02b_1);
        this.HairL01.addChild(this.HairL02);
        this.EquipHead01.addChild(this.EquipHead02);
        this.Butt.addChild(this.Skirt01);
        this.Hair.addChild(this.HairL01);
        this.EquipC05a.addChild(this.EquipC05b);
        this.EquipC04a.addChild(this.EquipC04b);
        this.Skirt01.addChild(this.Skirt02);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipTL02_1.addChild(this.EquipTL02f_1);
        this.EquipMain01.addChild(this.EquipMain04);
        this.Cloth01.addChild(this.Cloth02);
        this.BodyMain.addChild(this.ArmLeft01);
        this.EquipMain01.addChild(this.EquipTL02);
        this.HairMain.addChild(this.Hair01);
        this.Butt.addChild(this.LegLeft01);
        this.EquipTL02_1.addChild(this.EquipTL03_1);
        this.BodyMain.addChild(this.Butt);
        this.LegLeft01.addChild(this.LegLeft02);
        this.BodyMain.addChild(this.Head);
        this.Hair.addChild(this.Ahoke);
        this.EquipTL02_1.addChild(this.EquipTL02e_1);
        this.EquipC02.addChild(this.EquipC03);
        this.Hair.addChild(this.HairR01);
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipHead02.addChild(this.EquipHead03);
        this.EquipHead02.addChild(this.EquipHead04);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -9.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -11.8F, -1.0F);
        
        this.GlowBodyMain.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
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
    	GlStateManager.scale(0.4F, 0.4F, 0.4F);
    	GlStateManager.translate(0F, 2.25F, 0F);
    	
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
    
	//model animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		IShipEmotion ent = (IShipEmotion)entity;

		showEquip(ent);
		
		EmotionHelper.rollEmotion(this, ent);
		  
		if (ent.getStateFlag(ID.F.NoFuel))
		{
			motionStopPos(f, f1, f2, f3, f4, ent);
		}
		else
		{
			motionHumanPos(f, f1, f2, f3, f4, ent);
		}
		
		setGlowRotation();
    }
    
	//設定模型發光部份的rotation
    private void setGlowRotation()
    {
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
    }
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
    {
    	GlStateManager.translate(0F, 0.52F, 0F);
    	setFace(4);
    	
		//body
    	this.Head.rotateAngleX = 0F;
    	this.Head.rotateAngleY = 0F;
    	this.Head.rotateAngleZ = 0F;
    	this.Ahoke.rotateAngleY = 0.5236F;
    	this.BodyMain.rotateAngleX = 1.55F;
    	this.Butt.rotateAngleX = 0.21F;
    	this.Butt.offsetY = 0F;
	  	this.Skirt01.rotateAngleX = -0.052F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt02.rotateAngleX = -0.052F;
	  	this.Skirt02.offsetY = 0F;
    	//arm
    	this.ArmLeft01.rotateAngleX = -3F;
    	this.ArmLeft01.rotateAngleY = 0F;
    	this.ArmLeft01.rotateAngleZ = 0.3F;
    	this.ArmRight01.rotateAngleX = -3F;
    	this.ArmRight01.rotateAngleY = 0F;
    	this.ArmRight01.rotateAngleZ = -0.3F;
    	this.ArmLeft02.rotateAngleX = 0F;
    	this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
    	//leg
    	this.LegLeft01.rotateAngleX = -0.2618F;
    	this.LegLeft01.rotateAngleY = 0F;
    	this.LegLeft01.rotateAngleZ = 0.03F;
    	this.LegRight01.rotateAngleX = -0.2618F;
		this.LegRight01.rotateAngleY = 0F;
    	this.LegRight01.rotateAngleZ = -0.03F;
    	this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
	  	this.EquipHead01.rotateAngleY = -1.4F;
	  	this.EquipHead01.rotateAngleZ = 1.4F;
	  	this.EquipC02.rotateAngleY = 0.6F;
	  	this.EquipC04a.rotateAngleX = 0F;
	  	this.EquipC05a.rotateAngleX = -0.2F;
	  	
    }
    
	//雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
  	{   
  		float angleX = MathHelper.cos(f2*0.08F + f * 0.25F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		//水上漂浮
  		if (!ent.getIsRiding() && ((IShipFloating)ent).getShipDepth() > 0)
  		{
  			GlStateManager.translate(0F, angleX * 0.1F - 0.03F, 0F);
    	}

    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.14F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.5F - 0.03F;  //LegRight01
    	
  	    //head
	  	this.Head.rotateAngleX = f4 * 0.0174532925F + 0.1047F;
	  	this.Head.rotateAngleY = f3 * 0.013F;
	  	//body
  	    this.Ahoke.rotateAngleY = angleX * 0.2F + 0.5F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.21F;
	  	this.Butt.offsetY = 0F;
	  	this.Skirt01.rotateAngleX = -0.052F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt02.rotateAngleX = -0.052F;
	  	this.Skirt02.offsetY = 0F;
	    //arm
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F + 0.21F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.35F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.25F - 0.07F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.35F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.1047F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.1047F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//equip
	  	this.EquipHead01.rotateAngleY = 0F;
	  	this.EquipHead01.rotateAngleZ = 0F;
	  	this.EquipC02.rotateAngleY = 0.5F + this.Head.rotateAngleY * 0.5F;
	  	this.EquipC04a.rotateAngleX = -0.2F + this.Head.rotateAngleX;
	  	if (this.EquipC04a.rotateAngleX > 0F) this.EquipC04a.rotateAngleX = 0F;
	  	this.EquipC05a.rotateAngleX = this.EquipC04a.rotateAngleX;
	    
	    if (ent.getStateEmotion(ID.S.State) < ID.State.EQUIP01)
	    {
	    	this.ArmLeft01.rotateAngleZ += 0.1F;
	    	this.ArmRight01.rotateAngleZ -= 0.1F;
	    }

	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {	//奔跑動作
	    	setFace(3);
	 	    //body
	 	    this.Head.rotateAngleX -= 0.25F;
	 	    this.BodyMain.rotateAngleX = 0.1F;
	 	    this.Skirt01.rotateAngleX = -0.1F;
	 	  	this.Skirt02.rotateAngleX = -0.1885F;
	 	    //arm
	 	    this.ArmLeft01.rotateAngleX += 0.1F;
	 	    this.ArmLeft01.rotateAngleZ -= 0.3F;
	 	    this.ArmRight01.rotateAngleX = -2.2F + angleAdd1 * 0.2F;
	 	    this.ArmRight01.rotateAngleZ = -0.4712F;
	 	    //leg
	 	    addk1 -= 0.2F;
	 	  	addk2 -= 0.2F;
	 	  	//equip
	 	  	this.EquipHead01.rotateAngleY = -0.3142F;
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {	//潛行, 蹲下動作
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.4F;
		  	this.Butt.offsetY = -0.19F;
		  	this.Skirt01.rotateAngleX = -0.12F;
		  	this.Skirt02.rotateAngleX = -0.4F;
		  	this.Skirt02.offsetY = -0.1F;
		    //arm 
		    this.ArmLeft01.rotateAngleX = -0.6F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
		    this.ArmRight01.rotateAngleX = -0.6F;
		    this.ArmRight01.rotateAngleZ = -0.2618F;
		    //leg
		    addk1 -= 0.55F;
		    addk2 -= 0.55F;
  		}//end if sneaking
  		
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {
	    	//騎乘動作
	    	Entity mount = ((Entity)ent).getRidingEntity();
	    	
	    	if (mount instanceof EntityDestroyerInazuma ||
	    		mount instanceof EntityDestroyerAkatsuki)
	    	{
	    		if (((IShipEmotion) (mount)).getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    		{
		    		//Body
				  	this.BodyMain.rotateAngleX = -0.1F;
			    	this.Butt.rotateAngleX = -0.2F;
			    	this.Butt.offsetY = -0.1F;
					this.Skirt01.rotateAngleX = -0.07F;
					this.Skirt01.offsetY = -0.05F;
					this.Skirt02.rotateAngleX = -0.16F;
					this.Skirt02.offsetY = -0.08F;
					//arm 
				  	this.ArmLeft01.rotateAngleX = -0.5F;
				  	this.ArmLeft01.rotateAngleY = -0.2F;
				  	this.ArmLeft01.rotateAngleZ = 0F;
				  	this.ArmLeft02.rotateAngleX = -1.45F;
				  	this.ArmRight01.rotateAngleX = -0.5F;
				  	this.ArmRight01.rotateAngleY = 0.2F;
				  	this.ArmRight01.rotateAngleZ = 0F;
				  	this.ArmRight02.rotateAngleX = -1.45F;
				  	//leg
				  	addk1 = -0.65F;
				  	addk2 = -0.65F;
				  	this.LegLeft01.rotateAngleY = 0.0F;
					this.LegLeft01.rotateAngleZ = -0.25F;
					this.LegLeft02.offsetZ = 0.0F;
					this.LegLeft02.rotateAngleX = 0.8F;
					this.LegLeft02.rotateAngleZ = 0.0175F;
					this.LegRight01.rotateAngleY = -0.0F;
					this.LegRight01.rotateAngleZ = 0.25F;
					this.LegRight02.offsetZ = 0.0F;
					this.LegRight02.rotateAngleX = 0.8F;
					this.LegRight02.rotateAngleZ = -0.0175F;
					//equip
					this.EquipHead01.isHidden = true;
	    		}
	    		else
	    		{
		    		//Body
				  	this.Butt.rotateAngleX = -0.2F;
			    	this.Butt.offsetY = -0.1F;
					this.Skirt01.rotateAngleX = -0.07F;
					this.Skirt01.offsetY = -0.1F;
					this.Skirt02.rotateAngleX = -0.16F;
					this.Skirt02.offsetY = -0.15F;
					//arm 
				  	this.ArmLeft01.rotateAngleX = -0.3F;
				  	this.ArmLeft01.rotateAngleY = -0.2F;
				  	this.ArmLeft01.rotateAngleZ = 0F;
				  	this.ArmLeft02.rotateAngleX = -1.2F;
				  	this.ArmRight01.rotateAngleX = -1.8F;
			    	this.ArmRight01.rotateAngleY = 0.2F;
					this.ArmRight01.rotateAngleZ = 0F;
				  	//leg
				  	addk1 = -0.95F;
				  	addk2 = -0.95F;
				  	this.LegLeft01.rotateAngleY = -0.5F;
					this.LegLeft01.rotateAngleZ = -0.1F;
					this.LegLeft02.offsetZ = 0.0F;
					this.LegLeft02.rotateAngleX = 0.8F;
					this.LegLeft02.rotateAngleZ = 0.0175F;
					this.LegRight01.rotateAngleY = 0.5F;
					this.LegRight01.rotateAngleZ = 0.1F;
					this.LegRight02.offsetZ = 0.0F;
					this.LegRight02.rotateAngleX = 0.8F;
					this.LegRight02.rotateAngleZ = -0.0175F;
	    		}
	    	}
	    	else if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
	    		GlStateManager.translate(0F, 0.375F, 0F);
		    	//head
		    	this.Head.rotateAngleX -= 0.1F;
		    	//body
		    	this.BodyMain.rotateAngleX = -0.25F;
		    	this.Butt.rotateAngleX = -0.2F;
		    	this.Butt.offsetY = -0.1F;
				this.Skirt01.rotateAngleX = -0.07F;
				this.Skirt01.offsetY = -0.1F;
				this.Skirt02.rotateAngleX = -0.16F;
				this.Skirt02.offsetY = -0.15F;
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
				this.LegLeft02.offsetX = 0.32F;
				this.LegLeft02.offsetY = 0.05F;
				this.LegLeft02.offsetZ = 0.35F;
				this.LegRight01.rotateAngleZ = 0.14F;
				this.LegRight02.rotateAngleX = 1.2217F;
				this.LegRight02.rotateAngleY = -1.2217F;
				this.LegRight02.rotateAngleZ = 1.0472F;
				this.LegRight02.offsetX = -0.32F;
				this.LegRight02.offsetY = 0.05F;
				this.LegRight02.offsetZ = 0.35F;
				//equip
				this.EquipHead01.isHidden = true;
				
				//arm special
		    	float parTick = f2 - (int)f2 + (ent.getTickExisted() % 256);
		    	
		    	if (parTick < 30F)
		    	{
		    		float az = MathHelper.sin(parTick * 0.033F * 1.5708F) * 1.8F;
			    	float az1 = az * 1.6F;
			    	
			    	setFace(3);
		    		//arm 
				    this.ArmLeft01.rotateAngleZ = 0.1F + az;
				    this.ArmLeft02.rotateAngleZ = 1F - az1;
				    if(this.ArmLeft02.rotateAngleZ < 0F) this.ArmLeft02.rotateAngleZ = 0F;
					this.ArmRight01.rotateAngleZ = -0.1F - az;
					this.ArmRight02.rotateAngleZ = -1F + az1;
					if(this.ArmRight02.rotateAngleZ > 0F) this.ArmRight02.rotateAngleZ = 0F;
		    	}
		    	else if (parTick < 45F)
		    	{
		    		setFace(3);
		    		//arm 
				    this.ArmLeft01.rotateAngleZ = 1.9F;
				    this.ArmLeft02.rotateAngleZ = 0F;
					this.ArmRight01.rotateAngleZ = -1.9F;
					this.ArmRight02.rotateAngleZ = 0F;
		    	}
		    	else if (parTick < 53F)
		    	{
		    		float az = MathHelper.cos((parTick - 45F) * 0.125F * 1.5708F);
			    	float az1 = az * 1.8F;
			    	
			    	//arm 
				    this.ArmLeft01.rotateAngleZ = 0.1F + az1;
				    this.ArmLeft02.rotateAngleZ = 1F - az;
					this.ArmRight01.rotateAngleZ = -0.1F - az1;
					this.ArmRight02.rotateAngleZ = -1F + az;
		    	}
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, 0.375F, 0F);
		    	//head
		    	this.Head.rotateAngleX -= 0.1F;
		    	//body
		    	this.BodyMain.rotateAngleX = -0.25F;
		    	this.Butt.rotateAngleX = -0.2F;
		    	this.Butt.offsetY = -0.1F;
				this.Skirt01.rotateAngleX = -0.07F;
				this.Skirt01.offsetY = -0.1F;
				this.Skirt02.rotateAngleX = -0.16F;
				this.Skirt02.offsetY = -0.15F;
				//arm
				this.ArmLeft01.rotateAngleX = 0.3F;
				this.ArmLeft01.rotateAngleZ = -0.2618F;
				this.ArmRight01.rotateAngleX = 0.3F;
				this.ArmRight01.rotateAngleZ = 0.2618F;
				//leg
				addk1 = -0.9F;
				addk2 = -0.9F;
				this.LegLeft01.rotateAngleZ = -0.14F;
				this.LegLeft02.rotateAngleX = 1.2217F;
				this.LegLeft02.rotateAngleY = 1.2217F;
				this.LegLeft02.rotateAngleZ = -1.0472F;
				this.LegLeft02.offsetX = 0.32F;
				this.LegLeft02.offsetY = 0.05F;
				this.LegLeft02.offsetZ = 0.35F;
				this.LegRight01.rotateAngleZ = 0.14F;
				this.LegRight02.rotateAngleX = 1.2217F;
				this.LegRight02.rotateAngleY = -1.2217F;
				this.LegRight02.rotateAngleZ = 1.0472F;
				this.LegRight02.offsetX = -0.32F;
				this.LegRight02.offsetY = 0.05F;
				this.LegRight02.offsetZ = 0.35F;
				//equip
				this.EquipHead01.rotateAngleZ = 1.2F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if (ent.getAttackTick() > 20 && !ent.getIsRiding())
	    {
	    	setFace(3);
	 	    //body
	 	    this.Head.rotateAngleX -= 0.1F;
	 	  	//equip
	 	  	this.EquipHead01.rotateAngleY = -0.3142F;
	 	  	
	    	if (ent.getTickExisted() % 128 < 64)
	    	{
	    		//arm
	    		this.ArmLeft01.rotateAngleX = 0.2356F;
		 	    this.ArmLeft01.rotateAngleZ = -0.7854F;
		 	    this.ArmLeft02.rotateAngleZ = 1.5708F;
		 	    this.ArmLeft02.offsetX = -0.15F;
		 	    this.ArmRight01.rotateAngleX = -1.6F + angleAdd1 * 0.2F;
		 	    this.ArmRight01.rotateAngleZ = -0.4F;
	    	}
	    	else
	    	{
	    		//arm
		 	    this.ArmLeft01.rotateAngleX = 0.2356F;
		 	    this.ArmLeft01.rotateAngleZ = -0.7854F;
		 	    this.ArmLeft02.rotateAngleZ = 1.5708F;
		 	    this.ArmLeft02.offsetX = -0.15F;
		 	    this.ArmRight01.rotateAngleX = 0.2356F;
		 	    this.ArmRight01.rotateAngleZ = 0.7854F;
		 	    this.ArmRight02.rotateAngleZ = -1.5708F;
		 	    this.ArmRight02.offsetX = 0.15F;
		 	    //equip
		 	    this.EquipHead01.isHidden = true;
	    	}
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	  	}
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
  	
  	private void showEquip(IShipEmotion ent)
  	{
  		switch (ent.getStateEmotion(ID.S.State))
  		{
  		case ID.State.EQUIP00:
  			this.EquipBase.isHidden = true;
  			this.EquipHead01.isHidden = false;
  		break;
  		case ID.State.EQUIP01:
  			this.EquipBase.isHidden = false;
  			this.EquipHead01.isHidden = true;
  		break;
  		case ID.State.EQUIP02:
  			this.EquipBase.isHidden = false;
  			this.EquipHead01.isHidden = false;
  		break;
  		default:  //normal
  			this.EquipBase.isHidden = true;
  			this.EquipHead01.isHidden = true;
  		break;
  		}
  	}
  	
    //設定顯示的臉型
  	@Override
  	public void setFace(int emo)
  	{
  		switch (emo)
  		{
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