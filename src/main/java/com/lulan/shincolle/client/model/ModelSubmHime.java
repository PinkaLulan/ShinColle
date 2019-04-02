package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.BasicEntityMount;
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
 * ModelSubmHime - PinkaLulan
 * Created using Tabula 5.1.0  2017/12/10
 */
public class ModelSubmHime extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Butt;
    public ModelRenderer ArmRight01;
    public ModelRenderer ArmLeft01;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer EquipBack;
    public ModelRenderer Head;
    public ModelRenderer Collar01;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Ahoke01;
    public ModelRenderer Ahoke01a;
    public ModelRenderer HairU01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL01;
    public ModelRenderer HairR02;
    public ModelRenderer HairL02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer Ahoke02;
    public ModelRenderer Ahoke03;
    public ModelRenderer Ahoke04;
    public ModelRenderer Ahoke05;
    public ModelRenderer Ahoke06;
    public ModelRenderer Ahoke02a;
    public ModelRenderer Ahoke03a;
    public ModelRenderer Ahoke04a;
    public ModelRenderer Ahoke05a;
    public ModelRenderer Ahoke06a;
    public ModelRenderer Collar02;
    public ModelRenderer Collar03;
    public ModelRenderer Collar04;
    public ModelRenderer Collar05;
    public ModelRenderer Collar05a;
    public ModelRenderer Collar05b;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft02;
    public ModelRenderer Skirt02;
    public ModelRenderer LegRight02;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmLeft02;
    public ModelRenderer EquipTube00;
    public ModelRenderer EquipTube00_1;
    public ModelRenderer EquipTube01;
    public ModelRenderer EquipTube01a;
    public ModelRenderer EquipTube02;
    public ModelRenderer EquipTube02a;
    public ModelRenderer EquipTube03;
    public ModelRenderer EquipTube03a;
    public ModelRenderer EquipTube04;
    public ModelRenderer EquipTube04a;
    public ModelRenderer EquipTube05;
    public ModelRenderer EquipTube05a;
    public ModelRenderer EquipTBase;
    public ModelRenderer EquipT01;
    public ModelRenderer EquipT02;
    public ModelRenderer EquipT03;
    public ModelRenderer EquipT04;
    public ModelRenderer EquipT05;
    public ModelRenderer EquipT06;
    public ModelRenderer EquipT07;
    public ModelRenderer EquipT02a;
    public ModelRenderer EquipT02b;
    public ModelRenderer EquipT02c;
    public ModelRenderer EquipT02d;
    public ModelRenderer EquipTJaw01;
    public ModelRenderer EquipTJaw02;
    public ModelRenderer EquipTEyeA;
    public ModelRenderer EquipTEyeB;
    public ModelRenderer EquipTube01_1;
    public ModelRenderer EquipTube01a_1;
    public ModelRenderer EquipTube02_1;
    public ModelRenderer EquipTube02a_1;
    public ModelRenderer EquipTube03_1;
    public ModelRenderer EquipTube03a_1;
    public ModelRenderer EquipTube04_1;
    public ModelRenderer EquipTube04a_1;
    public ModelRenderer EquipTube05_1;
    public ModelRenderer EquipTube05a_1;
    public ModelRenderer EquipTBase_1;
    public ModelRenderer EquipT01_1;
    public ModelRenderer EquipT03_1;
    public ModelRenderer EquipT05_1;
    public ModelRenderer EquipT06_1;
    public ModelRenderer EquipT07_1;
    public ModelRenderer EquipT02_1;
    public ModelRenderer EquipT04_1;
    public ModelRenderer EquipT02a_1;
    public ModelRenderer EquipT02b_1;
    public ModelRenderer EquipT02c_1;
    public ModelRenderer EquipT02d_1;
    public ModelRenderer EquipTJaw01_1;
    public ModelRenderer EquipTJaw02_1;
    public ModelRenderer EquipTEyeA_1;
    public ModelRenderer EquipTEyeB_1;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowEquipBase;
    

    public ModelSubmHime()
    {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.scale = 0.48F;
        this.offsetY = 1.62F;
        this.offsetItem = new float[] {0.08F, 1.02F, -0.07F};
        this.offsetBlock = new float[] {0.08F, 1.02F, -0.07F};
        
        this.setDefaultFaceModel();
        
        this.EquipTJaw01_1 = new ModelRenderer(this, 59, 25);
        this.EquipTJaw01_1.setRotationPoint(0.0F, 6.3F, 0.5F);
        this.EquipTJaw01_1.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 3, 0.0F);
        this.setRotateAngle(EquipTJaw01_1, 0.2617993877991494F, 0.0F, 0.0F);
        this.EquipT05_1 = new ModelRenderer(this, 0, 0);
        this.EquipT05_1.setRotationPoint(0.0F, 14.0F, 0.0F);
        this.EquipT05_1.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.EquipTube05a = new ModelRenderer(this, 44, 67);
        this.EquipTube05a.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube05a.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
        this.EquipTJaw02_1 = new ModelRenderer(this, 59, 25);
        this.EquipTJaw02_1.mirror = true;
        this.EquipTJaw02_1.setRotationPoint(0.0F, 4.6F, 1.0F);
        this.EquipTJaw02_1.addBox(-3.5F, 0.0F, -2.5F, 7, 4, 3, 0.0F);
        this.setRotateAngle(EquipTJaw02_1, 1.48352986419518F, 0.0F, 3.141592653589793F);
        this.EquipT01 = new ModelRenderer(this, 0, 0);
        this.EquipT01.setRotationPoint(0.0F, -19.0F, 0.0F);
        this.EquipT01.addBox(-2.0F, 0.0F, -2.0F, 4, 3, 4, 0.0F);
        this.HairR02 = new ModelRenderer(this, 0, 18);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 10.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.Ahoke06a = new ModelRenderer(this, 42, 89);
        this.Ahoke06a.mirror = true;
        this.Ahoke06a.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke06a.addBox(-2.0F, 0.0F, 0.0F, 4, 7, 0, 0.0F);
        this.setRotateAngle(Ahoke06a, -0.5235987755982988F, 0.08726646259971647F, 0.0F);
        this.EquipT04_1 = new ModelRenderer(this, 0, 0);
        this.EquipT04_1.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.EquipT04_1.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.EquipT02c = new ModelRenderer(this, 0, 0);
        this.EquipT02c.setRotationPoint(0.0F, 0.5F, 5.9F);
        this.EquipT02c.addBox(0.0F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
        this.setRotateAngle(EquipT02c, 0.0F, 1.5707963267948966F, 0.0F);
        this.EquipTEyeB_1 = new ModelRenderer(this, 0, 14);
        this.EquipTEyeB_1.setRotationPoint(-3.2F, 10.9F, 3.0F);
        this.EquipTEyeB_1.addBox(0.0F, 0.0F, 0.0F, 0, 2, 2, 0.0F);
        this.setRotateAngle(EquipTEyeB_1, -2.0943951023931953F, 0.0F, 0.0F);
        this.EquipTube03a_1 = new ModelRenderer(this, 44, 67);
        this.EquipTube03a_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube03a_1.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 128, 17);
        this.Skirt02.setRotationPoint(0.0F, 3.5F, -2.7F);
        this.Skirt02.addBox(-10.5F, 0.0F, -6.5F, 21, 5, 13, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 47);
        this.LegRight02.mirror = true;
        this.LegRight02.setRotationPoint(-3.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(0.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.EquipTube02 = new ModelRenderer(this, 0, 0);
        this.EquipTube02.setRotationPoint(0.0F, 7.0F, -1.0F);
        this.EquipTube02.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube02, 0.5235987755982988F, 0.0F, 0.0F);
        this.Ahoke05a = new ModelRenderer(this, 50, 77);
        this.Ahoke05a.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke05a.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke05a, -0.2617993877991494F, 0.08726646259971647F, 0.0F);
        this.Ahoke01a = new ModelRenderer(this, 50, 79);
        this.Ahoke01a.setRotationPoint(0.0F, -15.0F, -1.5F);
        this.Ahoke01a.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke01a, -2.2689280275926285F, -2.6179938779914944F, 0.0F);
        this.EquipTBase_1 = new ModelRenderer(this, 0, 0);
        this.EquipTBase_1.setRotationPoint(0.0F, 26.0F, 1.0F);
        this.EquipTBase_1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTBase_1, 0F, -0.61F, 0F);
        this.EquipT02a = new ModelRenderer(this, 0, 0);
        this.EquipT02a.setRotationPoint(2.9F, 0.5F, 0.0F);
        this.EquipT02a.addBox(0.0F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
        this.EquipTube00_1 = new ModelRenderer(this, 0, 0);
        this.EquipTube00_1.setRotationPoint(-0.5F, 3.0F, 1.4F);
        this.EquipTube00_1.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(EquipTube00_1, 0.2617993877991494F, -0.61F, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 47);
        this.LegLeft02.setRotationPoint(3.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-6.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.EquipTube05a_1 = new ModelRenderer(this, 44, 67);
        this.EquipTube05a_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube05a_1.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
        this.EquipT02a_1 = new ModelRenderer(this, 0, 0);
        this.EquipT02a_1.setRotationPoint(2.9F, 0.5F, 0.0F);
        this.EquipT02a_1.addBox(0.0F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
        this.Hair03 = new ModelRenderer(this, 26, 32);
        this.Hair03.setRotationPoint(0.0F, 12.5F, -0.1F);
        this.Hair03.addBox(-8.0F, 0.0F, -4.5F, 16, 15, 7, 0.0F);
        this.setRotateAngle(Hair03, -0.05235987755982988F, 0.0F, 0.0F);
        this.EquipT07_1 = new ModelRenderer(this, 0, 0);
        this.EquipT07_1.setRotationPoint(0.0F, 24.7F, 0.0F);
        this.EquipT07_1.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4, 0.0F);
        this.EquipT01_1 = new ModelRenderer(this, 0, 0);
        this.EquipT01_1.setRotationPoint(0.0F, -19.0F, 0.0F);
        this.EquipT01_1.addBox(-2.0F, 0.0F, -2.0F, 4, 3, 4, 0.0F);
        this.EquipTEyeB = new ModelRenderer(this, 0, 14);
        this.EquipTEyeB.setRotationPoint(-3.2F, 10.9F, 3.0F);
        this.EquipTEyeB.addBox(0.0F, 0.0F, 0.0F, 0, 2, 2, 0.0F);
        this.setRotateAngle(EquipTEyeB, -2.0943951023931953F, 0.0F, 0.0F);
        this.EquipT02b_1 = new ModelRenderer(this, 0, 0);
        this.EquipT02b_1.setRotationPoint(-2.9F, 0.5F, 0.0F);
        this.EquipT02b_1.addBox(-3.0F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
        this.EquipT02_1 = new ModelRenderer(this, 0, 0);
        this.EquipT02_1.setRotationPoint(0.0F, -16.0F, 0.0F);
        this.EquipT02_1.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.EquipTJaw01 = new ModelRenderer(this, 59, 25);
        this.EquipTJaw01.setRotationPoint(0.0F, 6.3F, 0.5F);
        this.EquipTJaw01.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 3, 0.0F);
        this.setRotateAngle(EquipTJaw01, 0.2617993877991494F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 0, 36);
        this.BoobR.setRotationPoint(-3.2F, -8.5F, -3.8F);
        this.BoobR.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.8726646259971648F, 0.08726646259971647F, 0.06981317007977318F);
        this.ArmRight01 = new ModelRenderer(this, 24, 71);
        this.ArmRight01.mirror = true;
        this.ArmRight01.setRotationPoint(-7.8F, -9.3F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.0F, 0.2617993877991494F);
        this.EquipT02d = new ModelRenderer(this, 0, 0);
        this.EquipT02d.setRotationPoint(0.0F, 0.5F, -5.9F);
        this.EquipT02d.addBox(0.0F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
        this.setRotateAngle(EquipT02d, 0.0F, -1.5707963267948966F, 0.0F);
        this.Ahoke04a = new ModelRenderer(this, 50, 77);
        this.Ahoke04a.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke04a.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke04a, 0.4886921905584123F, 0.05235987755982988F, 0.0F);
        this.EquipTBase = new ModelRenderer(this, 0, 0);
        this.EquipTBase.setRotationPoint(0.0F, 26.0F, 1.0F);
        this.EquipTBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTBase, 0F, 0.61F, 0.0F);
        this.Collar05 = new ModelRenderer(this, 0, 0);
        this.Collar05.setRotationPoint(0.0F, 4.0F, -0.2F);
        this.Collar05.addBox(-2.5F, 0.0F, -1.0F, 5, 2, 2, 0.0F);
        this.EquipT07 = new ModelRenderer(this, 0, 0);
        this.EquipT07.setRotationPoint(0.0F, 24.7F, 0.0F);
        this.EquipT07.addBox(-2.0F, 0.0F, -2.0F, 4, 1, 4, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 128, 0);
        this.Skirt01.setRotationPoint(0.0F, 3.0F, 1.5F);
        this.Skirt01.addBox(-8.5F, 0.0F, -8.5F, 17, 5, 11, 0.0F);
        this.setRotateAngle(Skirt01, -0.08726646259971647F, 0.0F, 0.0F);
        this.HairR01 = new ModelRenderer(this, 0, 18);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-7.0F, 3.0F, -5.5F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairR01, -0.19198621771937624F, 0.17453292519943295F, 0.08726646259971647F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 54);
        this.ArmLeft02.setRotationPoint(3.0F, 11.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.EquipBack = new ModelRenderer(this, 17, 31);
        this.EquipBack.setRotationPoint(0.0F, -0.7F, 4.4F);
        this.EquipBack.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4, 0.0F);
        this.setRotateAngle(EquipBack, -0.7853981633974483F, 0.0F, 0.0F);
        this.EquipTube04a_1 = new ModelRenderer(this, 44, 67);
        this.EquipTube04a_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube04a_1.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
        this.EquipT02 = new ModelRenderer(this, 0, 0);
        this.EquipT02.setRotationPoint(0.0F, -16.0F, 0.0F);
        this.EquipT02.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.Collar01 = new ModelRenderer(this, 0, 0);
        this.Collar01.setRotationPoint(0.0F, -1.1F, -1.2F);
        this.Collar01.addBox(-6.0F, -2.0F, -4.0F, 12, 3, 8, 0.0F);
        this.setRotateAngle(Collar01, 0.03490658503988659F, 0.0F, 0.0F);
        this.EquipTube04 = new ModelRenderer(this, 0, 0);
        this.EquipTube04.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.EquipTube04.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube04, 0.6981317007977318F, 0.0F, 0.0F);
        this.EquipT03_1 = new ModelRenderer(this, 0, 0);
        this.EquipT03_1.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.EquipT03_1.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.EquipT02c_1 = new ModelRenderer(this, 0, 0);
        this.EquipT02c_1.setRotationPoint(0.0F, 0.5F, 5.9F);
        this.EquipT02c_1.addBox(0.0F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
        this.setRotateAngle(EquipT02c_1, 0.0F, 1.5707963267948966F, 0.0F);
        this.EquipTube01a_1 = new ModelRenderer(this, 44, 67);
        this.EquipTube01a_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube01a_1.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
        this.EquipTube03 = new ModelRenderer(this, 0, 0);
        this.EquipTube03.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.EquipTube03.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube03, 0.6108652381980153F, 0.0F, 0.0F);
        this.EquipTEyeA_1 = new ModelRenderer(this, 0, 14);
        this.EquipTEyeA_1.setRotationPoint(3.2F, 10.9F, 3.0F);
        this.EquipTEyeA_1.addBox(0.0F, 0.0F, 0.0F, 0, 2, 2, 0.0F);
        this.setRotateAngle(EquipTEyeA_1, -2.0943951023931953F, 0.0F, 0.0F);
        this.EquipT06_1 = new ModelRenderer(this, 0, 0);
        this.EquipT06_1.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.EquipT06_1.addBox(-2.5F, 0.0F, -2.5F, 5, 1, 5, 0.0F);
        this.EquipTube00 = new ModelRenderer(this, 0, 0);
        this.EquipTube00.setRotationPoint(0.5F, 3.0F, 1.4F);
        this.EquipTube00.addBox(-1.5F, 0.0F, -1.5F, 3, 5, 3, 0.0F);
        this.setRotateAngle(EquipTube00, 0.2617993877991494F, 0.61F, 0.0F);
        this.EquipT04 = new ModelRenderer(this, 0, 0);
        this.EquipT04.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.EquipT04.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.EquipTube04_1 = new ModelRenderer(this, 0, 0);
        this.EquipTube04_1.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.EquipTube04_1.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube04_1, 0.6981317007977318F, 0.0F, 0.0F);
        this.Collar04 = new ModelRenderer(this, 0, 0);
        this.Collar04.setRotationPoint(0.0F, 2.5F, 0.0F);
        this.Collar04.addBox(-0.5F, 0.0F, -1.0F, 1, 5, 2, 0.0F);
        this.setRotateAngle(Collar04, 0.4553564018453205F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 88);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipTube01a = new ModelRenderer(this, 44, 67);
        this.EquipTube01a.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube01a.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2, 0.0F);
        this.Ahoke06 = new ModelRenderer(this, 42, 90);
        this.Ahoke06.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke06.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke06, -0.4363323129985824F, 0.08726646259971647F, 0.0F);
        this.Ahoke03a = new ModelRenderer(this, 50, 79);
        this.Ahoke03a.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke03a.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke03a, 1.0471975511965976F, 0.05235987755982988F, 0.0F);
        this.EquipTube03_1 = new ModelRenderer(this, 0, 0);
        this.EquipTube03_1.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.EquipTube03_1.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube03_1, 0.6108652381980153F, 0.0F, 0.0F);
        this.Ahoke04 = new ModelRenderer(this, 50, 77);
        this.Ahoke04.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke04.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke04, 0.4363323129985824F, 0.05235987755982988F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 71);
        this.ArmLeft01.setRotationPoint(7.8F, -9.3F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.20943951023931953F, 0.0F, -0.2617993877991494F);
        this.EquipTube03a = new ModelRenderer(this, 44, 67);
        this.EquipTube03a.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube03a.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
        this.EquipT02b = new ModelRenderer(this, 0, 0);
        this.EquipT02b.setRotationPoint(-2.9F, 0.5F, 0.0F);
        this.EquipT02b.addBox(-3.0F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
        this.EquipTJaw02 = new ModelRenderer(this, 59, 25);
        this.EquipTJaw02.mirror = true;
        this.EquipTJaw02.setRotationPoint(0.0F, 4.6F, 1.0F);
        this.EquipTJaw02.addBox(-3.5F, 0.0F, -2.5F, 7, 4, 3, 0.0F);
        this.setRotateAngle(EquipTJaw02, 1.48352986419518F, 0.0F, 3.141592653589793F);
        this.Neck = new ModelRenderer(this, 24, 63);
        this.Neck.setRotationPoint(0.0F, -9.6F, 0.5F);
        this.Neck.addBox(-2.5F, -3.0F, -2.9F, 5, 3, 5, 0.0F);
        this.setRotateAngle(Neck, 0.10471975511965977F, 0.0F, 0.0F);
        this.HairL02 = new ModelRenderer(this, 0, 18);
        this.HairL02.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairL02, 0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.Ahoke03 = new ModelRenderer(this, 50, 79);
        this.Ahoke03.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke03.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke03, 0.7853981633974483F, 0.05235987755982988F, 0.0F);
        this.Ahoke05 = new ModelRenderer(this, 50, 79);
        this.Ahoke05.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke05.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke05, -0.17453292519943295F, 0.08726646259971647F, 0.0F);
        this.Collar03 = new ModelRenderer(this, 0, 0);
        this.Collar03.setRotationPoint(0.0F, 3.5F, 0.0F);
        this.Collar03.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Collar03, 0.2617993877991494F, 0.0F, 0.0F);
        this.Collar05a = new ModelRenderer(this, 0, 0);
        this.Collar05a.setRotationPoint(2.5F, 2.0F, 0.0F);
        this.Collar05a.addBox(0.0F, -2.0F, -0.5F, 3, 2, 1, 0.0F);
        this.setRotateAngle(Collar05a, 0.0F, -0.08726646259971647F, -0.3490658503988659F);
        this.EquipT06 = new ModelRenderer(this, 0, 0);
        this.EquipT06.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.EquipT06.addBox(-2.5F, 0.0F, -2.5F, 5, 1, 5, 0.0F);
        this.Ahoke02 = new ModelRenderer(this, 50, 77);
        this.Ahoke02.setRotationPoint(0.0F, 7.9F, 0.0F);
        this.Ahoke02.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke02, 1.0471975511965976F, -0.05235987755982988F, 0.0F);
        this.Hair02 = new ModelRenderer(this, 72, 29);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 5.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 16, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipTube05_1 = new ModelRenderer(this, 0, 0);
        this.EquipTube05_1.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.EquipTube05_1.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube05_1, 0.6108652381980153F, 0.0F, 0.0F);
        this.Ahoke02a = new ModelRenderer(this, 50, 79);
        this.Ahoke02a.setRotationPoint(0.0F, 5.9F, 0.0F);
        this.Ahoke02a.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
        this.setRotateAngle(Ahoke02a, 0.7853981633974483F, -0.05235987755982988F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 68);
        this.LegRight01.mirror = true;
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.19198621771937624F, 0.0F, -0.08726646259971647F);
        this.HairL01 = new ModelRenderer(this, 0, 18);
        this.HairL01.setRotationPoint(7.0F, 3.0F, -5.5F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairL01, -0.19198621771937624F, -0.17453292519943295F, -0.08726646259971647F);
        this.EquipTube01_1 = new ModelRenderer(this, 0, 0);
        this.EquipTube01_1.setRotationPoint(0.0F, 4.5F, 0.0F);
        this.EquipTube01_1.addBox(-1.0F, 0.0F, -1.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube01_1, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipTube02a = new ModelRenderer(this, 44, 67);
        this.EquipTube02a.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube02a.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
        this.EquipTube05 = new ModelRenderer(this, 0, 0);
        this.EquipTube05.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.EquipTube05.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube05, 0.6108652381980153F, 0.0F, 0.0F);
        this.EquipTEyeA = new ModelRenderer(this, 0, 14);
        this.EquipTEyeA.setRotationPoint(3.2F, 10.9F, 3.0F);
        this.EquipTEyeA.addBox(0.0F, 0.0F, 0.0F, 0, 2, 2, 0.0F);
        this.setRotateAngle(EquipTEyeA, -2.0943951023931953F, 0.0F, 0.0F);
        this.EquipTube04a = new ModelRenderer(this, 44, 67);
        this.EquipTube04a.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube04a.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 77);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.1F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 16, 8, 0.0F);
        this.EquipT03 = new ModelRenderer(this, 0, 0);
        this.EquipT03.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.EquipT03.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 54);
        this.ArmRight02.mirror = true;
        this.ArmRight02.setRotationPoint(-3.0F, 11.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.Collar02 = new ModelRenderer(this, 0, 0);
        this.Collar02.setRotationPoint(0.0F, -0.6F, -3.2F);
        this.Collar02.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Collar02, -0.6981317007977318F, 0.0F, 0.0F);
        this.EquipTube02a_1 = new ModelRenderer(this, 44, 67);
        this.EquipTube02a_1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipTube02a_1.addBox(-1.0F, 0.0F, 0.0F, 2, 7, 2, 0.0F);
        this.Hair01 = new ModelRenderer(this, 80, 0);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.0F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 17, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.2617993877991494F, 0.0F, 0.0F);
        this.HairU01 = new ModelRenderer(this, 52, 56);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.0F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.BoobL = new ModelRenderer(this, 0, 36);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.2F, -8.5F, -3.7F);
        this.BoobL.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.8726646259971648F, -0.08726646259971647F, -0.06981317007977318F);
        this.EquipT02d_1 = new ModelRenderer(this, 0, 0);
        this.EquipT02d_1.setRotationPoint(0.0F, 0.5F, -5.9F);
        this.EquipT02d_1.addBox(0.0F, 0.0F, -0.5F, 3, 7, 1, 0.0F);
        this.setRotateAngle(EquipT02d_1, 0.0F, -1.5707963267948966F, 0.0F);
        this.EquipTube01 = new ModelRenderer(this, 0, 0);
        this.EquipTube01.setRotationPoint(0.0F, 4.5F, 0.0F);
        this.EquipTube01.addBox(-1.0F, 0.0F, -1.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube01, 0.3490658503988659F, 0.0F, 0.0F);
        this.Collar05b = new ModelRenderer(this, 0, 0);
        this.Collar05b.setRotationPoint(-2.5F, 2.0F, 0.0F);
        this.Collar05b.addBox(-3.0F, -2.0F, -0.5F, 3, 2, 1, 0.0F);
        this.setRotateAngle(Collar05b, 0.0F, 0.08726646259971647F, 0.3490658503988659F);
        this.LegLeft01 = new ModelRenderer(this, 0, 68);
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.296705972839036F, 0.0F, 0.08726646259971647F);
        this.EquipT05 = new ModelRenderer(this, 0, 0);
        this.EquipT05.setRotationPoint(0.0F, 14.0F, 0.0F);
        this.EquipT05.addBox(-3.0F, 0.0F, -3.0F, 6, 10, 6, 0.0F);
        this.EquipTube02_1 = new ModelRenderer(this, 0, 0);
        this.EquipTube02_1.setRotationPoint(0.0F, 7.0F, -1.0F);
        this.EquipTube02_1.addBox(-1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipTube02_1, 0.5235987755982988F, 0.0F, 0.0F);
        this.Ahoke01 = new ModelRenderer(this, 50, 77);
        this.Ahoke01.setRotationPoint(-1.0F, -15.0F, 0.0F);
        this.Ahoke01.addBox(-2.0F, 0.0F, 0.0F, 4, 8, 0, 0.0F);
        this.setRotateAngle(Ahoke01, -2.007128639793479F, 0.5235987755982988F, 0.0F);
        this.EquipT04_1.addChild(this.EquipTJaw01_1);
        this.EquipTBase_1.addChild(this.EquipT05_1);
        this.EquipTube05.addChild(this.EquipTube05a);
        this.EquipT04_1.addChild(this.EquipTJaw02_1);
        this.EquipTBase.addChild(this.EquipT01);
        this.HairR01.addChild(this.HairR02);
        this.Ahoke05a.addChild(this.Ahoke06a);
        this.EquipTBase_1.addChild(this.EquipT04_1);
        this.EquipT02.addChild(this.EquipT02c);
        this.EquipT04_1.addChild(this.EquipTEyeB_1);
        this.EquipTube03_1.addChild(this.EquipTube03a_1);
        this.Skirt01.addChild(this.Skirt02);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipTube01.addChild(this.EquipTube02);
        this.Ahoke04a.addChild(this.Ahoke05a);
        this.Head.addChild(this.Ahoke01a);
        this.EquipTube05a_1.addChild(this.EquipTBase_1);
        this.EquipT02.addChild(this.EquipT02a);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipTube05_1.addChild(this.EquipTube05a_1);
        this.EquipT02_1.addChild(this.EquipT02a_1);
        this.Hair02.addChild(this.Hair03);
        this.EquipTBase_1.addChild(this.EquipT07_1);
        this.EquipTBase_1.addChild(this.EquipT01_1);
        this.EquipT04.addChild(this.EquipTEyeB);
        this.EquipT02_1.addChild(this.EquipT02b_1);
        this.EquipTBase_1.addChild(this.EquipT02_1);
        this.EquipT04.addChild(this.EquipTJaw01);
        this.BodyMain.addChild(this.BoobR);
        this.BodyMain.addChild(this.ArmRight01);
        this.EquipT02.addChild(this.EquipT02d);
        this.Ahoke03a.addChild(this.Ahoke04a);
        this.EquipTube05a.addChild(this.EquipTBase);
        this.Collar04.addChild(this.Collar05);
        this.EquipTBase.addChild(this.EquipT07);
        this.Butt.addChild(this.Skirt01);
        this.Hair.addChild(this.HairR01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipTube04_1.addChild(this.EquipTube04a_1);
        this.EquipTBase.addChild(this.EquipT02);
        this.Neck.addChild(this.Collar01);
        this.EquipTube03.addChild(this.EquipTube04);
        this.EquipTBase_1.addChild(this.EquipT03_1);
        this.EquipT02_1.addChild(this.EquipT02c_1);
        this.EquipTube01_1.addChild(this.EquipTube01a_1);
        this.EquipTube02.addChild(this.EquipTube03);
        this.EquipT04_1.addChild(this.EquipTEyeA_1);
        this.EquipTBase_1.addChild(this.EquipT06_1);
        this.EquipTBase.addChild(this.EquipT04);
        this.EquipTube03_1.addChild(this.EquipTube04_1);
        this.Collar03.addChild(this.Collar04);
        this.BodyMain.addChild(this.Butt);
        this.EquipTube01.addChild(this.EquipTube01a);
        this.Ahoke05.addChild(this.Ahoke06);
        this.Ahoke02a.addChild(this.Ahoke03a);
        this.EquipTube02_1.addChild(this.EquipTube03_1);
        this.Ahoke03.addChild(this.Ahoke04);
        this.Neck.addChild(this.Head);
        this.Head.addChild(this.HairMain);
        this.BodyMain.addChild(this.ArmLeft01);
        this.EquipTube03.addChild(this.EquipTube03a);
        this.EquipT02.addChild(this.EquipT02b);
        this.EquipT04.addChild(this.EquipTJaw02);
        this.BodyMain.addChild(this.Neck);
        this.HairL01.addChild(this.HairL02);
        this.Ahoke02.addChild(this.Ahoke03);
        this.Ahoke04.addChild(this.Ahoke05);
        this.Collar02.addChild(this.Collar03);
        this.Collar05.addChild(this.Collar05a);
        this.EquipTBase.addChild(this.EquipT06);
        this.Ahoke01.addChild(this.Ahoke02);
        this.Hair01.addChild(this.Hair02);
        this.EquipTube04_1.addChild(this.EquipTube05_1);
        this.Ahoke01a.addChild(this.Ahoke02a);
        this.Butt.addChild(this.LegRight01);
        this.Hair.addChild(this.HairL01);
        this.EquipTube00_1.addChild(this.EquipTube01_1);
        this.EquipTube02.addChild(this.EquipTube02a);
        this.EquipTube04.addChild(this.EquipTube05);
        this.EquipT04.addChild(this.EquipTEyeA);
        this.EquipTube04.addChild(this.EquipTube04a);
        this.Head.addChild(this.Hair);
        this.EquipTBase.addChild(this.EquipT03);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Collar01.addChild(this.Collar02);
        this.EquipTube02_1.addChild(this.EquipTube02a_1);
        this.HairMain.addChild(this.Hair01);
        this.Hair.addChild(this.HairU01);
        this.BodyMain.addChild(this.BoobL);
        this.EquipT02_1.addChild(this.EquipT02d_1);
        this.EquipTube00.addChild(this.EquipTube01);
        this.Collar05.addChild(this.Collar05b);
        this.Butt.addChild(this.LegLeft01);
        this.EquipTBase.addChild(this.EquipT05);
        this.EquipTube01_1.addChild(this.EquipTube02_1);
        this.Head.addChild(this.Ahoke01);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 104);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 24, 63);
        this.GlowNeck.setRotationPoint(0.0F, -9.6F, 0.5F);
        this.setRotateAngle(GlowNeck, 0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 44, 101);
        this.GlowHead.setRotationPoint(0.0F, -1.0F, -0.7F);
        this.GlowEquipBase = new ModelRenderer(this, 0, 0);
        this.GlowEquipBase.setRotationPoint(0.0F, 8.0F, 3.0F);
        this.GlowEquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        
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
        this.GlowBodyMain.addChild(this.EquipBack);
        this.GlowBodyMain.addChild(this.GlowEquipBase);
        this.GlowEquipBase.addChild(this.EquipTube00);
        this.GlowEquipBase.addChild(this.EquipTube00_1);
        
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
    	GlStateManager.scale(0.46F, 0.46F, 0.46F);
    	GlStateManager.translate(0F, 1.77F, 0F);
    	
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
    	
    	//reset light
    	int j = entity.getBrightnessForRender();
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k, (float)l);
    	
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
    }
    
	@Override
	public void showEquip(IShipEmotion ent)
	{
		int state = ent.getStateEmotion(ID.S.State);
		
		boolean flag = !EmotionHelper.checkModelState(1, state);
		this.Collar01.isHidden = flag;
	}

	@Override
	public void syncRotationGlowPart()
	{
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowNeck.rotateAngleX = this.Neck.rotateAngleX;
		this.GlowNeck.rotateAngleY = this.Neck.rotateAngleY;
		this.GlowNeck.rotateAngleZ = this.Neck.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.62F, 0F);
  		this.setFaceHungry(ent);

  	    //頭部
	  	this.Head.rotateAngleX = -0.15F;
	  	this.Head.rotateAngleY = 0F;
	  	this.Head.rotateAngleZ = 0F;
	    //胸部
  	    this.BoobL.rotateAngleX = -0.76F;
  	    this.BoobR.rotateAngleX = -0.76F;
	  	//Body
	  	this.BodyMain.rotateAngleX = 1.6F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 1.2F;
	  	this.Butt.offsetY = -0.2F;
	  	this.Butt.offsetZ = -0.14F;
	  	this.Skirt01.rotateAngleX = -0.94F;
	  	this.Skirt01.offsetY = 0.09F;
	  	this.Skirt01.offsetZ = -0.03F;
	  	this.Skirt02.rotateAngleX = -0.3F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.35F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -0.2F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -0.35F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	this.HairL01.rotateAngleX = -0.14F;
	  	this.HairL02.rotateAngleX = 0.17F;
	  	this.HairR01.rotateAngleX = -0.14F;
	  	this.HairR02.rotateAngleX = 0.17F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -2.9F;
	  	this.ArmLeft01.rotateAngleY = -0.6981F;
	    this.ArmLeft01.rotateAngleZ = 0.08F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmRight01.rotateAngleX = -2.9F;
	  	this.ArmRight01.rotateAngleY = 0.6981F;
	    this.ArmRight01.rotateAngleZ = -0.08F;
	    this.ArmRight02.rotateAngleX = 0F;
	    this.ArmRight02.rotateAngleY = 0F;
	    this.ArmRight02.rotateAngleZ = 0F;
		//leg
    	this.LegLeft01.rotateAngleX = -1.9F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.05F;
		this.LegLeft02.rotateAngleX = 0.64F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleX = -1.9F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.05F;
		this.LegRight02.rotateAngleX = 0.64F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//tails
		this.GlowEquipBase.isHidden = true;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.4F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.08F + 0.8F + f * 0.5F);
  		float angleX3 = MathHelper.cos(f2*0.08F + 1.2F + f * 0.5F);
  		float angleX4 = MathHelper.cos(f2*0.08F + 1.6F + f * 0.5F);
  		float angleX5 = MathHelper.cos(f2*0.08F + 2.0F + f * 0.5F);
  		float angleX6 = MathHelper.cos(f2*0.08F + 2.4F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.5F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.5F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		float headX = 0F;
  		float headZ = 0F;
  		float addHL1 = 0F;
  		float addHR1 = 0F;
  		float addHL2 = 0F;
  		float addHR2 = 0F;
  		int state = ent.getStateEmotion(ID.S.State);
  		boolean collar = EmotionHelper.checkModelState(1, state);
  		boolean tails = EmotionHelper.checkModelState(2, state);
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D || ent.getShipDepth(1) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.025F + 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = angleAdd1 * 0.6F - 0.3F;
	  	addk2 = angleAdd2 * 0.6F - 0.2F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F; 	//上下角度
	  	this.Head.rotateAngleY = f3 * 0.006F;	//左右角度
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //正常站立動作
	  	//Body
  	    this.Ahoke01.rotateAngleX = angleX1 * 0.07F - 2.01F;
  	    this.Ahoke01.rotateAngleY = 0.52F;
  	    this.Ahoke01.rotateAngleZ = 0F;
  	    this.Ahoke02.rotateAngleX = -angleX2 * 0.09F + 1.04F;
  	    this.Ahoke03.rotateAngleX = angleX3 * 0.15F + 0.78F;
  	    this.Ahoke04.rotateAngleX = -angleX4 * 0.10F + 0.44F;
  	    this.Ahoke05.rotateAngleX = -angleX5 * 0.15F - 0.17F;
  	    this.Ahoke06.rotateAngleX = angleX6 * 0.18F - 0.31F;
  	    this.Ahoke01a.rotateAngleX = angleX1 * 0.07F - 2.27F;
  	    this.Ahoke01a.rotateAngleY = -2.62F;
  	    this.Ahoke01a.rotateAngleZ = 0F;
	    this.Ahoke02a.rotateAngleX = -angleX2 * 0.09F + 0.79F;
	    this.Ahoke03a.rotateAngleX = angleX3 * 0.15F + 1.05F;
	    this.Ahoke04a.rotateAngleX = -angleX4 * 0.10F + 0.41F;
	    this.Ahoke05a.rotateAngleX = -angleX5 * 0.15F - 0.3F;
	    this.Ahoke06a.rotateAngleX = angleX6 * 0.18F - 0.25F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.35F;
    	this.Butt.offsetY = 0F;
    	this.Butt.offsetZ = 0F;
    	this.BoobL.rotateAngleX = angleX * 0.06F - 0.76F;
    	this.BoobL.rotateAngleY = -0.087F;
    	this.BoobL.rotateAngleZ = -0.07F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.76F;
  	    this.BoobR.rotateAngleY = 0.087F;
  	    this.BoobR.rotateAngleZ = 0.07F;
  	    
    	if (collar)
    	{
    		this.BoobL.offsetX = 0F;
    		this.BoobR.offsetX = 0F;
    	}
    	else
    	{
    		this.BoobL.offsetX = -0.05F;
    		this.BoobR.offsetX = 0.05F;
    	}
    	
    	this.Collar01.rotateAngleX = 0.035F;
  	    this.Collar03.rotateAngleX = angleX * 0.08F + 0.26F;
  	    this.Collar04.rotateAngleX = -angleX * 0.08F + 0.45F;
    	//cloth
	  	this.Skirt01.rotateAngleX = -0.087F;
	  	this.Skirt01.offsetY = 0F;
	  	this.Skirt01.offsetZ = 0F;
	  	this.Skirt02.rotateAngleX = -0.087F;
	  	this.Skirt02.offsetY = 0F;
	  	this.Skirt02.offsetZ = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F + 0.26F + headX;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.087F + headX;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -angleX2 * 0.07F - 0.052F;
	  	this.Hair03.rotateAngleZ = 0F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.8F - 0.05F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.025F - 0.3F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    this.ArmLeft02.rotateAngleY = 0F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmLeft02.offsetX = 0F;
	    this.ArmLeft02.offsetZ = 0F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.8F + 0.26F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.025F + 0.3F;
		this.ArmRight02.rotateAngleX = 0F;
		this.ArmRight02.rotateAngleY = 0F;
		this.ArmRight02.rotateAngleZ = 0F;
		this.ArmRight02.offsetX = 0F;
	    this.ArmRight02.offsetZ = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.087F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleY = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetX = 0F;
		this.LegLeft02.offsetY = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.087F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleY = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetX = 0F;
		this.LegRight02.offsetY = 0F;
		this.LegRight02.offsetZ = 0F;
		//tails
		if (tails)
		{
			this.EquipTBase.isHidden = true;
			this.EquipTBase_1.isHidden = true;
			this.GlowEquipBase.rotateAngleX = 0.3F;
			this.EquipTube00.rotateAngleX = 0.2618F;
			this.EquipTube00.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 0.7F) * 0.1F + 0.61F;
			this.EquipTube00.rotateAngleZ = this.EquipTube00.rotateAngleY * 0.125F;
			this.EquipTube01.rotateAngleX = 0.35F;
			this.EquipTube01.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 1.4F) * 0.125F;
			this.EquipTube01.rotateAngleZ = this.EquipTube01.rotateAngleY * 0.125F;
			this.EquipTube02.rotateAngleX = 0.5235F;
			this.EquipTube02.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.1F) * 0.15F;
			this.EquipTube02.rotateAngleZ = this.EquipTube02.rotateAngleY * 0.125F;
			this.EquipTube03.rotateAngleX = 0.61F;
			this.EquipTube03.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 2.8F) * 0.175F;
			this.EquipTube03.rotateAngleZ = this.EquipTube03.rotateAngleY * 0.125F;
			this.EquipTube04.rotateAngleX = 0.6981F;
			this.EquipTube04.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 3.5F) * 0.2F;
			this.EquipTube04.rotateAngleZ = this.EquipTube04.rotateAngleY * 0.125F;
			this.EquipTube05.rotateAngleX = 0.61F;
			this.EquipTube05.rotateAngleY = MathHelper.cos(-f2 * 0.1F + 4.2F) * 0.175F;
			this.EquipTube05.rotateAngleZ = this.EquipTube05.rotateAngleY * 0.125F;
			this.EquipTube00_1.rotateAngleX = this.EquipTube00.rotateAngleX;
			this.EquipTube00_1.rotateAngleY = -this.EquipTube00.rotateAngleY;
			this.EquipTube00_1.rotateAngleZ = this.EquipTube00.rotateAngleZ;
			this.EquipTube01_1.rotateAngleX = this.EquipTube01.rotateAngleX;
			this.EquipTube01_1.rotateAngleY = this.EquipTube01.rotateAngleY;
			this.EquipTube01_1.rotateAngleZ = this.EquipTube01.rotateAngleZ;
			this.EquipTube02_1.rotateAngleX = this.EquipTube02.rotateAngleX;
			this.EquipTube02_1.rotateAngleY = this.EquipTube02.rotateAngleY;
			this.EquipTube02_1.rotateAngleZ = this.EquipTube02.rotateAngleZ;
			this.EquipTube03_1.rotateAngleX = this.EquipTube03.rotateAngleX;
			this.EquipTube03_1.rotateAngleY = this.EquipTube03.rotateAngleY;
			this.EquipTube03_1.rotateAngleZ = this.EquipTube03.rotateAngleZ;
			this.EquipTube04_1.rotateAngleX = this.EquipTube04.rotateAngleX;
			this.EquipTube04_1.rotateAngleY = this.EquipTube04.rotateAngleY;
			this.EquipTube04_1.rotateAngleZ = this.EquipTube04.rotateAngleZ;
			this.EquipTube05_1.rotateAngleX = this.EquipTube05.rotateAngleX;
			this.EquipTube05_1.rotateAngleY = this.EquipTube05.rotateAngleY;
			this.EquipTube05_1.rotateAngleZ = this.EquipTube05.rotateAngleZ;
		}
		
		//奔跑動作
	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {
	    	if (ent.getIsRiding())
	    	{
	    		if (f1 > 0.5F)
	    		{
	    			this.Head.rotateAngleX += 0.4F;
		    		this.Hair01.rotateAngleX += 0.1F;
				  	this.Hair02.rotateAngleX -= 0.2F;
				  	this.Hair03.rotateAngleX -= 0.2F;
	    		}
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, 0.06F, 0F);
	    		this.Head.rotateAngleX -= 1.1F;
	    		this.Hair01.rotateAngleX += 0.6F;
			  	this.Hair02.rotateAngleX += 0.5F;
			  	this.Hair03.rotateAngleX += 0.2F;
			  	this.Ahoke01.rotateAngleX += 0.38F;
			  	this.Ahoke01.rotateAngleY = 0.7F;
			  	this.Ahoke01.rotateAngleZ = 0.4F;
			  	this.Ahoke01a.rotateAngleY = -2.5F;
			  	this.Ahoke01a.rotateAngleZ = -0.2F;
	    	}
	    	
		    //body
	    	this.BodyMain.rotateAngleX = 1.2566F;
		  	//胸部
	  	    this.BoobL.rotateAngleX = angleAdd2 * 0.1F - 0.83F;
	  	    this.BoobL.rotateAngleZ = -0.07F;
	  	    this.BoobR.rotateAngleX = angleAdd2 * 0.1F - 0.83F;
	  	    this.BoobR.rotateAngleZ = 0.07F;
	  	    this.Collar03.rotateAngleX += angleAdd2 * 0.1F;
	  	    this.Collar04.rotateAngleX += angleAdd2 * 0.1F;
	    	//arm
	    	this.ArmLeft01.rotateAngleX = -2.7F;
		    this.ArmLeft01.rotateAngleZ = -0.22F;
		    this.ArmRight01.rotateAngleX = -2.7F;
		    this.ArmRight01.rotateAngleZ = 0.22F;
		    //leg
		    this.LegLeft01.rotateAngleZ = 0.05F;
		  	this.LegRight01.rotateAngleZ = -0.05F;
		  	//tails
			if (tails)
			{
				this.EquipTBase.isHidden = true;
				this.EquipTBase_1.isHidden = true;
				this.GlowEquipBase.rotateAngleX = 0.3F;
				this.EquipTube00.rotateAngleX = MathHelper.cos(-f2 * 0.4F + 0.7F) * 0.1F + 0.4F;
				this.EquipTube00.rotateAngleY = MathHelper.cos(-f2 * 0.4F + 0.7F) * 0.1F + 0.9F;
				this.EquipTube00.rotateAngleZ = this.EquipTube00.rotateAngleY * 0.125F;
				this.EquipTube01.rotateAngleX = MathHelper.cos(-f2 * 0.4F + 1.4F) * 0.125F;
				this.EquipTube01.rotateAngleY = MathHelper.cos(-f2 * 0.4F + 1.4F) * 0.125F;
				this.EquipTube01.rotateAngleZ = this.EquipTube01.rotateAngleY * 0.125F;
				this.EquipTube02.rotateAngleX = MathHelper.cos(-f2 * 0.4F + 2.1F) * 0.15F;
				this.EquipTube02.rotateAngleY = MathHelper.cos(-f2 * 0.4F + 2.1F) * 0.15F;
				this.EquipTube02.rotateAngleZ = this.EquipTube02.rotateAngleY * 0.125F;
				this.EquipTube03.rotateAngleX = MathHelper.cos(-f2 * 0.4F + 2.8F) * 0.175F;
				this.EquipTube03.rotateAngleY = MathHelper.cos(-f2 * 0.4F + 2.8F) * 0.175F;
				this.EquipTube03.rotateAngleZ = this.EquipTube03.rotateAngleY * 0.125F;
				this.EquipTube04.rotateAngleX = MathHelper.cos(-f2 * 0.4F + 3.5F) * 0.2F;
				this.EquipTube04.rotateAngleY = MathHelper.cos(-f2 * 0.4F + 3.5F) * 0.2F;
				this.EquipTube04.rotateAngleZ = this.EquipTube04.rotateAngleY * 0.125F;
				this.EquipTube05.rotateAngleX = MathHelper.cos(-f2 * 0.4F + 4.2F) * 0.175F;
				this.EquipTube05.rotateAngleY = MathHelper.cos(-f2 * 0.4F + 4.2F) * 0.175F;
				this.EquipTube05.rotateAngleZ = this.EquipTube05.rotateAngleY * 0.125F;
				this.EquipTube00_1.rotateAngleX = this.EquipTube00.rotateAngleX;
				this.EquipTube00_1.rotateAngleY = -this.EquipTube00.rotateAngleY;
				this.EquipTube00_1.rotateAngleZ = -this.EquipTube00.rotateAngleZ;
				this.EquipTube01_1.rotateAngleX = this.EquipTube01.rotateAngleX;
				this.EquipTube01_1.rotateAngleY = this.EquipTube01.rotateAngleY;
				this.EquipTube01_1.rotateAngleZ = this.EquipTube01.rotateAngleZ;
				this.EquipTube02_1.rotateAngleX = this.EquipTube02.rotateAngleX;
				this.EquipTube02_1.rotateAngleY = this.EquipTube02.rotateAngleY;
				this.EquipTube02_1.rotateAngleZ = this.EquipTube02.rotateAngleZ;
				this.EquipTube03_1.rotateAngleX = this.EquipTube03.rotateAngleX;
				this.EquipTube03_1.rotateAngleY = this.EquipTube03.rotateAngleY;
				this.EquipTube03_1.rotateAngleZ = this.EquipTube03.rotateAngleZ;
				this.EquipTube04_1.rotateAngleX = this.EquipTube04.rotateAngleX;
				this.EquipTube04_1.rotateAngleY = this.EquipTube04.rotateAngleY;
				this.EquipTube04_1.rotateAngleZ = this.EquipTube04.rotateAngleZ;
				this.EquipTube05_1.rotateAngleX = this.EquipTube05.rotateAngleX;
				this.EquipTube05_1.rotateAngleY = this.EquipTube05.rotateAngleY;
				this.EquipTube05_1.rotateAngleZ = this.EquipTube05.rotateAngleZ;
			}
	    }
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    //潛行跟蹲下動作
	    if (ent.getIsSneaking())
	    {
	    	GlStateManager.translate(0F, 0.09F, 0F);
	    	
	    	//Body
	    	this.Head.rotateAngleX -= 0.6283F;
		  	this.BodyMain.rotateAngleX = 0.8727F;
		  	this.Skirt01.rotateAngleX = -0.34F;
		  	this.Skirt01.offsetY = -0.2F;
		  	this.Skirt01.offsetZ = 0.03F;
		  	this.Skirt02.rotateAngleX = -0.27F;
		  	this.Collar01.rotateAngleX -= 0.35F;
		  	this.Collar03.rotateAngleX -= 0.3F;
		  	this.Collar04.rotateAngleX -= 0.35F;
			//胸部
	  	    this.BoobL.rotateAngleX -= 0.2F;
	  	    this.BoobL.rotateAngleZ = -0.04F;
	  	    this.BoobR.rotateAngleX -= 0.2F;
	  	    this.BoobR.rotateAngleZ = 0.04F;
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
			this.Hair03.rotateAngleX -= 0.1F;
			//tails
			this.GlowEquipBase.rotateAngleX = -0.2F;
  		}//end if sneaking
  		
	    //坐下動作
	    if (ent.getIsSitting() && !ent.getIsRiding())
	    {
	    	if (ent.getTickExisted() % 512 > 256)
	    	{
	    		this.setFaceDamaged(ent);
		  		GlStateManager.translate(0F, -angleX * 0.05F + 0.1F, 0F);
			    //body
		    	this.Head.rotateAngleX *= 0.5F;
		    	this.Head.rotateAngleY *= 0.75F;
			    this.Head.rotateAngleX += 0.5F;
		    	this.BodyMain.rotateAngleX = 1.6F;
		    	this.Skirt01.rotateAngleX = -0.33F;
		    	this.Skirt01.offsetY = -0.23F;
		    	this.Skirt02.rotateAngleX = -0.12F;
		    	this.Skirt02.offsetY = -0.16F;
		    	this.Ahoke01.rotateAngleX += 0.38F;
			  	this.Ahoke01.rotateAngleY = 0.8F;
			  	this.Ahoke01.rotateAngleZ = 0.4F;
			  	this.Hair01.rotateAngleX -= 0.2F;
		    	this.Hair02.rotateAngleX -= 0.25F;
		    	this.Hair03.rotateAngleX -= 0.3F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -1.5F;
		    	this.ArmLeft01.rotateAngleZ = -2.3F;
		    	this.ArmRight01.rotateAngleX = -1.5F;
		    	this.ArmRight01.rotateAngleZ = 2.3F;
			    //leg
			    addk1 = -1.8F;
		    	addk2 = -1.8F;
			    this.LegLeft01.rotateAngleY = -0.1F - angleX * 0.02F;
			  	this.LegRight01.rotateAngleY = 0.1F + angleX * 0.02F;
	    	}
	    	else
	    	{
		    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
			    	GlStateManager.translate(0F, 0.52F, 0F);
			    	
			    	this.setFaceDamaged(ent);
			    	
			    	//body
			    	this.Head.rotateAngleX = 0.4F;
			    	this.Skirt01.rotateAngleX = -0.64F;
			    	this.Skirt01.offsetY = -0.17F;
			    	this.Skirt01.offsetZ = 0F;
			    	this.Skirt02.rotateAngleX = 0.29F;
			    	this.Skirt02.offsetY = -0.04F;
			    	this.Skirt02.offsetZ = 0.02F;
			    	this.Hair01.rotateAngleX -= 0.2F;
			    	this.Hair02.rotateAngleX -= 0.15F;
			    	this.Hair03.rotateAngleX -= 0.1F;
			    	this.Ahoke01.rotateAngleX -= 0.1F;
			    	//arm
			    	this.ArmLeft01.rotateAngleX = 0.4F;
			    	this.ArmLeft01.rotateAngleY = -2.96705972839036F;
			    	this.ArmLeft01.rotateAngleZ = -2.62F;
			    	this.ArmLeft02.rotateAngleX = 0.0F;
			    	this.ArmLeft02.rotateAngleY = 0.0F;
			    	this.ArmLeft02.rotateAngleZ = 1F;
			    	this.ArmLeft02.offsetX = 0F;
			    	this.ArmLeft02.offsetZ = 0F;
			    	this.ArmRight01.rotateAngleX = 0.5235987755982988F;
			    	this.ArmRight01.rotateAngleY = 2.96705972839036F;
			    	this.ArmRight01.rotateAngleZ = 2.62F;
			    	this.ArmRight02.rotateAngleX = 0.0F;
			    	this.ArmRight02.rotateAngleY = 0.0F;
			    	this.ArmRight02.rotateAngleZ = -1F;
			    	this.ArmRight02.offsetX = 0F;
			    	this.ArmRight02.offsetZ = 0F;
			    	//leg
			    	addk1 = -2.41309222380736F;
			    	addk2 = -2.2689280275926285F;
			    	this.LegLeft01.rotateAngleY = 0.0F;
			    	this.LegLeft01.rotateAngleZ = -0.27314402793711257F;
			    	this.LegLeft02.rotateAngleX = 1.4570008595648662F;
			    	this.LegLeft02.rotateAngleY = 0.0F;
			    	this.LegLeft02.rotateAngleZ = 0.0F;
			    	this.LegRight01.rotateAngleY = 0.0F;
			    	this.LegRight01.rotateAngleZ = 0.22759093446006054F;
			    	this.LegRight02.rotateAngleX = 1.0471975511965976F;
			    	this.LegRight02.rotateAngleY = 0.0F;
			    	this.LegRight02.rotateAngleZ = 0.0F;
		    	}
		    	else
		    	{
	    			GlStateManager.translate(0F, 0.495F, 0F);
	    	
			    	//body
			    	this.Head.rotateAngleX -= 0.7F;
			    	this.BodyMain.rotateAngleX = 0.35F;
			    	this.Hair01.rotateAngleX += 0.3F;
			    	this.Hair02.rotateAngleX += 0.3F;
			    	this.Hair03.rotateAngleX += 0.3F;
			    	this.Skirt01.rotateAngleX = -0.32F;
			    	this.Skirt01.offsetY = -0.05F;
			    	this.Skirt02.rotateAngleX = -0.21F;
			    	this.Collar01.rotateAngleX += 0.1F;
				  	this.Collar03.rotateAngleX += 0.1F;
			    	//arm
					this.ArmLeft01.rotateAngleX = -0.5235987755982988F;
					this.ArmLeft01.rotateAngleY = 0.0F;
					this.ArmLeft01.rotateAngleZ = 0.3490658503988659F;
				    this.ArmLeft02.rotateAngleX = 0F;
				    this.ArmLeft02.rotateAngleZ = 0F;
				    this.ArmLeft02.offsetX = 0F;
				    this.ArmLeft02.offsetZ = 0F;
					this.ArmRight01.rotateAngleX = -0.5235987755982988F;
					this.ArmRight01.rotateAngleY = 0.0F;
					this.ArmRight01.rotateAngleZ = -0.3490658503988659F;
					this.ArmRight02.rotateAngleX = 0F;
					this.ArmRight02.rotateAngleZ = 0F;
					this.ArmRight02.offsetX = 0F;
					this.ArmRight02.offsetZ = 0F;
			    	//leg
			    	addk1 = -1.4486232791552935F;
			    	addk2 = -1.4486232791552935F;
					this.LegLeft01.rotateAngleY = -0.5235987755982988F;
					this.LegLeft01.rotateAngleZ = -1.3962634015954636F;
					this.LegLeft02.rotateAngleX = 2.1816615649929116F;
					this.LegLeft02.rotateAngleY = 0.0F;
					this.LegLeft02.rotateAngleZ = 0.0F;
					this.LegLeft02.offsetX = 0F;
					this.LegLeft02.offsetZ = 0.37F;
					this.LegRight01.rotateAngleY = 0.5235987755982988F;
					this.LegRight01.rotateAngleZ = 1.3962634015954636F;
					this.LegRight02.rotateAngleX = 2.1816615649929116F;
					this.LegRight02.rotateAngleY = 0.0F;
					this.LegRight02.rotateAngleZ = 0.0F;
					this.LegRight02.offsetX = 0F;
					this.LegRight02.offsetZ = 0.37F;
		    	}
	    	}
  		}//end sitting
	    
	    //騎乘專屬坐騎動作
	    if (ent.getIsRiding())
	    {
	    	if (((Entity)ent).getRidingEntity() instanceof BasicEntityMount)
	    	{
	    		if (ent.getIsSitting())
	    		{
	    			GlStateManager.translate(0F, 0.4F, 0F);
			    	
			    	//body
			    	this.Head.rotateAngleX -= 0.7F;
			    	this.BodyMain.rotateAngleX = 0.35F;
			    	this.Hair01.rotateAngleX += 0.3F;
			    	this.Hair02.rotateAngleX += 0.3F;
			    	this.Hair03.rotateAngleX += 0.3F;
			    	this.Skirt01.rotateAngleX = -0.32F;
			    	this.Skirt01.offsetY = -0.05F;
			    	this.Skirt02.rotateAngleX = -0.21F;
			    	this.Collar01.rotateAngleX += 0.1F;
				  	this.Collar03.rotateAngleX += 0.1F;
			    	//arm
					this.ArmLeft01.rotateAngleX = -0.8F;
					this.ArmLeft01.rotateAngleY = 0.0F;
					this.ArmLeft01.rotateAngleZ = -0.2F;
				    this.ArmLeft02.rotateAngleX = 0F;
				    this.ArmLeft02.rotateAngleZ = 0F;
				    this.ArmLeft02.offsetX = 0F;
				    this.ArmLeft02.offsetZ = 0F;
					this.ArmRight01.rotateAngleX = -0.8F;
					this.ArmRight01.rotateAngleY = 0.0F;
					this.ArmRight01.rotateAngleZ = 0.2F;
					this.ArmRight02.rotateAngleX = 0F;
					this.ArmRight02.rotateAngleZ = 0F;
					this.ArmRight02.offsetX = 0F;
					this.ArmRight02.offsetZ = 0F;
			    	//leg
			    	addk1 = -1.4486232791552935F;
			    	addk2 = -1.4486232791552935F;
					this.LegLeft01.rotateAngleY = -0.5235987755982988F;
					this.LegLeft01.rotateAngleZ = -0.2F;
					this.LegLeft02.rotateAngleX = 0.8F;
					this.LegRight01.rotateAngleY = 0.5235987755982988F;
					this.LegRight01.rotateAngleZ = 0.2F;
					this.LegRight02.rotateAngleX = 0.8F;
		    	}//end if sitting
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.22F, 0F);
				    //body
			    	this.Head.rotateAngleX *= 0.5F;
			    	this.Head.rotateAngleY *= 0.75F;
				    this.Head.rotateAngleX -= 1.0F;
			    	this.BodyMain.rotateAngleX = 1.0F;
			    	this.Skirt01.rotateAngleX = -0.33F;
			    	this.Skirt01.offsetY = -0.23F;
			    	this.Skirt02.rotateAngleX = -0.12F;
			    	this.Skirt02.offsetY = -0.16F;
			    	this.Collar01.rotateAngleX -= 0.5F;
				  	this.Collar03.rotateAngleX -= 0.5F;
				  	this.Collar04.rotateAngleX -= 0.5F;
			    	//hair
			    	this.Ahoke01.rotateAngleX += 0.38F;
				  	this.Ahoke01.rotateAngleY = 0.8F;
				  	this.Ahoke01.rotateAngleZ = 0.4F;
				  	this.Hair01.rotateAngleX += 0.5F;
			    	this.Hair02.rotateAngleX += 0.65F;
			    	this.Hair03.rotateAngleX += 0.5F;
			    	addHL1 = -0.6F;
			    	addHR1 = -0.6F;
			    	addHL2 = -0.5F;
			    	addHR2 = -0.5F;
			    	//arm
			    	this.ArmLeft01.rotateAngleX = -1.4F;
			    	this.ArmLeft01.rotateAngleY = -0.0F;
			    	this.ArmRight01.rotateAngleX = -1.4F;
			    	this.ArmRight01.rotateAngleY = 0.0F;
				    //leg
				    addk1 = -1.7F;
			    	addk2 = -1.7F;
				    this.LegLeft01.rotateAngleY = -0.2F;
				  	this.LegRight01.rotateAngleY = 0.2F;
		    	}
	    	}//end ship mount
	    	//normal mount ex: cart
	    	else
	    	{
	    		if (ent.getIsSitting())
	    		{
	    			if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			    	{
	    				this.setFaceDamaged(ent);
	    		  		GlStateManager.translate(0F, -angleX * 0.05F + 0.1F, 0F);
	    			    //body
	    		    	this.Head.rotateAngleX *= 0.5F;
	    		    	this.Head.rotateAngleY *= 0.75F;
	    			    this.Head.rotateAngleX += 0.5F;
	    		    	this.BodyMain.rotateAngleX = 1.6F;
	    		    	this.Skirt01.rotateAngleX = -0.33F;
	    		    	this.Skirt01.offsetY = -0.23F;
	    		    	this.Skirt02.rotateAngleX = -0.12F;
	    		    	this.Skirt02.offsetY = -0.16F;
	    		    	this.Ahoke01.rotateAngleX += 0.38F;
	    			  	this.Ahoke01.rotateAngleY = 0.8F;
	    			  	this.Ahoke01.rotateAngleZ = 0.4F;
	    			  	this.Hair01.rotateAngleX -= 0.2F;
	    		    	this.Hair02.rotateAngleX -= 0.25F;
	    		    	this.Hair03.rotateAngleX -= 0.3F;
	    		    	//arm
	    		    	this.ArmLeft01.rotateAngleX = -1.5F;
	    		    	this.ArmLeft01.rotateAngleZ = -2.3F;
	    		    	this.ArmRight01.rotateAngleX = -1.5F;
	    		    	this.ArmRight01.rotateAngleZ = 2.3F;
	    			    //leg
	    			    addk1 = -1.8F;
	    		    	addk2 = -1.8F;
	    			    this.LegLeft01.rotateAngleY = -0.1F - angleX * 0.02F;
	    			  	this.LegRight01.rotateAngleY = 0.1F + angleX * 0.02F;
			    	}
			    	else
			    	{
			    		GlStateManager.translate(0F, 0.52F, 0F);
				    	
				    	this.setFaceDamaged(ent);
				    	
				    	//body
				    	this.Head.rotateAngleX = 0.4F;
				    	this.Skirt01.rotateAngleX = -0.64F;
				    	this.Skirt01.offsetY = -0.17F;
				    	this.Skirt01.offsetZ = 0F;
				    	this.Skirt02.rotateAngleX = 0.29F;
				    	this.Skirt02.offsetY = -0.04F;
				    	this.Skirt02.offsetZ = 0.02F;
				    	this.Hair01.rotateAngleX -= 0.2F;
				    	this.Hair02.rotateAngleX -= 0.15F;
				    	this.Hair03.rotateAngleX -= 0.1F;
				    	this.Ahoke01.rotateAngleX -= 0.1F;
				    	//arm
				    	this.ArmLeft01.rotateAngleX = 0.4F;
				    	this.ArmLeft01.rotateAngleY = -2.96705972839036F;
				    	this.ArmLeft01.rotateAngleZ = -2.62F;
				    	this.ArmLeft02.rotateAngleX = 0.0F;
				    	this.ArmLeft02.rotateAngleY = 0.0F;
				    	this.ArmLeft02.rotateAngleZ = 1F;
				    	this.ArmLeft02.offsetX = 0F;
				    	this.ArmLeft02.offsetZ = 0F;
				    	this.ArmRight01.rotateAngleX = 0.5235987755982988F;
				    	this.ArmRight01.rotateAngleY = 2.96705972839036F;
				    	this.ArmRight01.rotateAngleZ = 2.62F;
				    	this.ArmRight02.rotateAngleX = 0.0F;
				    	this.ArmRight02.rotateAngleY = 0.0F;
				    	this.ArmRight02.rotateAngleZ = -1F;
				    	this.ArmRight02.offsetX = 0F;
				    	this.ArmRight02.offsetZ = 0F;
				    	//leg
				    	addk1 = -2.41309222380736F;
				    	addk2 = -2.2689280275926285F;
				    	this.LegLeft01.rotateAngleY = 0.0F;
				    	this.LegLeft01.rotateAngleZ = -0.27314402793711257F;
				    	this.LegLeft02.rotateAngleX = 1.4570008595648662F;
				    	this.LegLeft02.rotateAngleY = 0.0F;
				    	this.LegLeft02.rotateAngleZ = 0.0F;
				    	this.LegRight01.rotateAngleY = 0.0F;
				    	this.LegRight01.rotateAngleZ = 0.22759093446006054F;
				    	this.LegRight02.rotateAngleX = 1.0471975511965976F;
				    	this.LegRight02.rotateAngleY = 0.0F;
				    	this.LegRight02.rotateAngleZ = 0.0F;
			    	}
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.495F, 0F);
			    	
			    	//body
			    	this.Head.rotateAngleX -= 0.7F;
			    	this.BodyMain.rotateAngleX = 0.35F;
			    	this.Hair01.rotateAngleX += 0.3F;
			    	this.Hair02.rotateAngleX += 0.3F;
			    	this.Hair03.rotateAngleX += 0.3F;
			    	this.Skirt01.rotateAngleX = -0.32F;
			    	this.Skirt01.offsetY = -0.05F;
			    	this.Skirt02.rotateAngleX = -0.21F;
			    	this.Collar01.rotateAngleX += 0.1F;
				  	this.Collar03.rotateAngleX += 0.1F;
			    	//arm
					this.ArmLeft01.rotateAngleX = -0.5235987755982988F;
					this.ArmLeft01.rotateAngleY = 0.0F;
					this.ArmLeft01.rotateAngleZ = 0.3490658503988659F;
				    this.ArmLeft02.rotateAngleX = 0F;
				    this.ArmLeft02.rotateAngleZ = 0F;
				    this.ArmLeft02.offsetX = 0F;
				    this.ArmLeft02.offsetZ = 0F;
					this.ArmRight01.rotateAngleX = -0.5235987755982988F;
					this.ArmRight01.rotateAngleY = 0.0F;
					this.ArmRight01.rotateAngleZ = -0.3490658503988659F;
					this.ArmRight02.rotateAngleX = 0F;
					this.ArmRight02.rotateAngleZ = 0F;
					this.ArmRight02.offsetX = 0F;
					this.ArmRight02.offsetZ = 0F;
			    	//leg
			    	addk1 = -1.4486232791552935F;
			    	addk2 = -1.4486232791552935F;
					this.LegLeft01.rotateAngleY = -0.5235987755982988F;
					this.LegLeft01.rotateAngleZ = -1.3962634015954636F;
					this.LegLeft02.rotateAngleX = 2.1816615649929116F;
					this.LegLeft02.rotateAngleY = 0.0F;
					this.LegLeft02.rotateAngleZ = 0.0F;
					this.LegLeft02.offsetX = 0F;
					this.LegLeft02.offsetZ = 0.37F;
					this.LegRight01.rotateAngleY = 0.5235987755982988F;
					this.LegRight01.rotateAngleZ = 1.3962634015954636F;
					this.LegRight02.rotateAngleX = 2.1816615649929116F;
					this.LegRight02.rotateAngleY = 0.0F;
					this.LegRight02.rotateAngleZ = 0.0F;
					this.LegRight02.offsetX = 0F;
					this.LegRight02.offsetZ = 0.37F;
		    	}
	    	}
	    }//end ridding
    
	    //攻擊動作    
	    if (ent.getAttackTick() > 0)
	    {
	    	if (ent.getAttackTick() > 14)
	    	{
	    		if (ent.getIsRiding())
	    		{
	    			GlStateManager.translate(0F, 0.02F, 0F);
	    			
	    			//body
	    			this.Head.rotateAngleX *= 0.5F;
			    	this.Head.rotateAngleY *= 0.75F;
				    this.Head.rotateAngleX -= 0.5F;
			    	this.BodyMain.rotateAngleX = 1.1F;
			    	this.Collar01.rotateAngleX -= 0.2F;
			    	//hair
			    	this.Ahoke01.rotateAngleX += 0.38F;
				  	this.Ahoke01.rotateAngleY = 0.8F;
				  	this.Ahoke01.rotateAngleZ = 0.4F;
				  	this.Hair01.rotateAngleX += 0.2F;
			    	this.Hair02.rotateAngleX += -0.1F;
			    	this.Hair03.rotateAngleX += -0.1F;
			    	addHL1 = -0.6F;
			    	addHR1 = -0.6F;
			    	addHL2 = -0.5F;
			    	addHR2 = -0.5F;
			    	//leg
				    addk1 = -1.8F;
			    	addk2 = -1.8F;
				    this.LegLeft01.rotateAngleY = -0.1F;
				  	this.LegRight01.rotateAngleY = 0.1F;
				  	//equip
				  	this.GlowEquipBase.rotateAngleX = 0.5F;
	    		}
	    		else
	    		{
	    			GlStateManager.translate(0F, 0.22F, 0F);
	    			
	    			//body
	    			this.Head.rotateAngleX *= 0.5F;
			    	this.Head.rotateAngleY *= 0.75F;
				    this.Head.rotateAngleX -= 1.6F;
			    	this.BodyMain.rotateAngleX = 1.6F;
			    	this.Collar01.rotateAngleX -= 0.5F;
				  	this.Collar03.rotateAngleX -= 0.5F;
				  	this.Collar04.rotateAngleX -= 0.5F;
				  	//hair
			    	this.Ahoke01.rotateAngleX += 0.38F;
				  	this.Ahoke01.rotateAngleY = 0.8F;
				  	this.Ahoke01.rotateAngleZ = 0.4F;
				  	this.Hair01.rotateAngleX += 1.0F;
			    	this.Hair02.rotateAngleX += 0.6F;
			    	this.Hair03.rotateAngleX += 0.7F;
			    	addHL1 = -0.6F;
			    	addHR1 = -0.6F;
			    	addHL2 = -0.5F;
			    	addHR2 = -0.5F;
			    	//leg
				    addk1 = -2.2F;
			    	addk2 = -2.2F;
				    this.LegLeft01.rotateAngleY = -0.1F;
				  	this.LegRight01.rotateAngleY = 0.1F;
				  	//equip
				  	this.GlowEquipBase.rotateAngleX = 0F;
	    		}
		  		
			    //body
		    	this.Skirt01.rotateAngleX = -0.33F;
		    	this.Skirt01.offsetY = -0.23F;
		    	this.Skirt02.rotateAngleX = -0.12F;
		    	this.Skirt02.offsetY = -0.16F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -1.6F;
		    	this.ArmLeft01.rotateAngleY = -0.2F;
		    	this.ArmRight01.rotateAngleX = -1.2F;
		    	this.ArmRight01.rotateAngleY = 1.2F;
	    	}
	    }
	    
	    //跑道顯示
    	setTorpedo(ent.getAttackTick(), tails);
	    
	  	//swing arm
	  	float f6 = ent.getSwingTime(f2 % 1F);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.3F;
			this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight01.rotateAngleZ = -0.1F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	        this.ArmRight02.rotateAngleX = 0F;
	        this.ArmRight02.rotateAngleZ = 0F;
	  	}
	  	
	    //移動頭髮避免穿過身體
	    headX = this.Head.rotateAngleX * -0.5F;
		this.HairL01.rotateAngleX = angleX * 0.02F + headX - 0.19F + addHL1;
	  	this.HairL02.rotateAngleX = -angleX1 * 0.04F + headX + 0.17F + addHL2;
	  	this.HairR01.rotateAngleX = angleX * 0.02F + headX - 0.19F + addHR1;
	  	this.HairR02.rotateAngleX = -angleX1 * 0.04F + headX + 0.17F + addHR2;
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleZ = headZ;
	  	this.Hair02.rotateAngleZ = headZ;
	  	this.Hair03.rotateAngleZ = headZ;
	  	this.HairL01.rotateAngleZ = headZ - 0.087F;
	  	this.HairL02.rotateAngleZ = headZ + 0.087F;
	  	this.HairR01.rotateAngleZ = headZ + 0.087F;
	  	this.HairR02.rotateAngleZ = headZ - 0.052F;
	  	
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
    private void setTorpedo(int attackTime, boolean showTails)
    {
    	if (attackTime <= 14)
    	{
    		this.GlowEquipBase.isHidden = !showTails;
    		this.EquipTBase.isHidden = true;
    		this.EquipTBase_1.isHidden = true;
    		return;
    	}
    	
    	//set rotation
    	this.EquipTube00.rotateAngleX = 0.2618F;
		this.EquipTube00.rotateAngleY = 0.61F;
		this.EquipTube00.rotateAngleZ = 0F;
		this.EquipTube01.rotateAngleX = 0.35F;
		this.EquipTube01.rotateAngleY = 0F;
		this.EquipTube01.rotateAngleZ = 0F;
		this.EquipTube02.rotateAngleX = 0.5235F;
		this.EquipTube02.rotateAngleY = 0F;
		this.EquipTube02.rotateAngleZ = 0F;
		this.EquipTube03.rotateAngleX = 0.61F;
		this.EquipTube03.rotateAngleY = 0F;
		this.EquipTube03.rotateAngleZ = 0F;
		this.EquipTube04.rotateAngleX = 0.6981F;
		this.EquipTube04.rotateAngleY = 0F;
		this.EquipTube04.rotateAngleZ = 0F;
		this.EquipTube05.rotateAngleX = 0.61F;
		this.EquipTube05.rotateAngleY = 0F;
		this.EquipTube05.rotateAngleZ = 0F;
		this.EquipTube00_1.rotateAngleX = 0.2618F;
		this.EquipTube00_1.rotateAngleY = -0.61F;
		this.EquipTube00_1.rotateAngleZ = 0F;
		this.EquipTube01_1.rotateAngleX = 0.35F;
		this.EquipTube01_1.rotateAngleY = 0F;
		this.EquipTube01_1.rotateAngleZ = 0F;
		this.EquipTube02_1.rotateAngleX = 0.5235F;
		this.EquipTube02_1.rotateAngleY = 0F;
		this.EquipTube02_1.rotateAngleZ = 0F;
		this.EquipTube03_1.rotateAngleX = 0.61F;
		this.EquipTube03_1.rotateAngleY = 0F;
		this.EquipTube03_1.rotateAngleZ = 0F;
		this.EquipTube04_1.rotateAngleX = 0.6981F;
		this.EquipTube04_1.rotateAngleY = 0F;
		this.EquipTube04_1.rotateAngleZ = 0F;
		this.EquipTube05_1.rotateAngleX = 0.61F;
		this.EquipTube05_1.rotateAngleY = 0F;
		this.EquipTube05_1.rotateAngleZ = 0F;
    	
    	//show torpedo
		this.EquipTBase.isHidden = false;
		this.EquipTBase_1.isHidden = false;
    	this.GlowEquipBase.isHidden = false;
    	
    	switch (attackTime)
		{
		case 50:
			this.EquipTBase.offsetY = -2.73F;
			this.EquipTBase_1.offsetY = -2.73F;
		break;
		case 49:
			this.EquipTBase.offsetY = -2.71F;
			this.EquipTBase_1.offsetY = -2.71F;
		break;
		case 48:
			this.EquipTBase.offsetY = -2.69F;
			this.EquipTBase_1.offsetY = -2.69F;
		break;
		case 47:
			this.EquipTBase.offsetY = -2.375F;
			this.EquipTBase_1.offsetY = -2.375F;
		break;
		case 46:
			this.EquipTBase.offsetY = -2.06F;
			this.EquipTBase_1.offsetY = -2.06F;
		break;
		case 45:
			this.EquipTBase.offsetY = -1.75F;
			this.EquipTBase_1.offsetY = -1.75F;
		break;
		case 44:
			this.EquipTBase.offsetY = -1.44F;
			this.EquipTBase_1.offsetY = -1.44F;
		break;
		case 43:
			this.EquipTBase.offsetY = -1.125F;
			this.EquipTBase_1.offsetY = -1.125F;
		break;
		case 42:
			this.EquipTBase.offsetY = -0.81F;
			this.EquipTBase_1.offsetY = -0.81F;
		break;
		case 41:
			this.EquipTBase.offsetY = -0.5F;
			this.EquipTBase_1.offsetY = -0.5F;
		break;
		case 40:
			this.EquipTBase.offsetY = -0.19F;
			this.EquipTBase_1.offsetY = -0.19F;
		break;
		case 39:
			this.EquipTBase.offsetY = -0.095F;
			this.EquipTBase_1.offsetY = -0.095F;
		break;
		default:
			this.EquipTBase.offsetY = 0F;
			this.EquipTBase_1.offsetY = 0F;
		break;
		}
    	
		switch (attackTime)
		{
		case 50:
		case 49:
			this.EquipT07.isHidden = false;
			this.EquipT07_1.isHidden = false;
			this.EquipT06.isHidden = true;
			this.EquipT06_1.isHidden = true;
			this.EquipT05.isHidden = true;
			this.EquipT05_1.isHidden = true;
			this.EquipT04.isHidden = true;
			this.EquipT04_1.isHidden = true;
			this.EquipT03.isHidden = true;
			this.EquipT03_1.isHidden = true;
			this.EquipT02.isHidden = true;
			this.EquipT02_1.isHidden = true;
			this.EquipT01.isHidden = true;
			this.EquipT01_1.isHidden = true;
		break;
		case 48:
		case 47:
			this.EquipT07.isHidden = false;
			this.EquipT07_1.isHidden = false;
			this.EquipT06.isHidden = false;
			this.EquipT06_1.isHidden = false;
			this.EquipT05.isHidden = true;
			this.EquipT05_1.isHidden = true;
			this.EquipT04.isHidden = true;
			this.EquipT04_1.isHidden = true;
			this.EquipT03.isHidden = true;
			this.EquipT03_1.isHidden = true;
			this.EquipT02.isHidden = true;
			this.EquipT02_1.isHidden = true;
			this.EquipT01.isHidden = true;
			this.EquipT01_1.isHidden = true;
		break;
		case 46:
		case 45:
			this.EquipT07.isHidden = false;
			this.EquipT07_1.isHidden = false;
			this.EquipT06.isHidden = false;
			this.EquipT06_1.isHidden = false;
			this.EquipT05.isHidden = false;
			this.EquipT05_1.isHidden = false;
			this.EquipT04.isHidden = true;
			this.EquipT04_1.isHidden = true;
			this.EquipT03.isHidden = true;
			this.EquipT03_1.isHidden = true;
			this.EquipT02.isHidden = true;
			this.EquipT02_1.isHidden = true;
			this.EquipT01.isHidden = true;
			this.EquipT01_1.isHidden = true;
		break;
		case 44:
		case 43:
			this.EquipT07.isHidden = false;
			this.EquipT07_1.isHidden = false;
			this.EquipT06.isHidden = false;
			this.EquipT06_1.isHidden = false;
			this.EquipT05.isHidden = false;
			this.EquipT05_1.isHidden = false;
			this.EquipT04.isHidden = false;
			this.EquipT04_1.isHidden = false;
			this.EquipT03.isHidden = true;
			this.EquipT03_1.isHidden = true;
			this.EquipT02.isHidden = true;
			this.EquipT02_1.isHidden = true;
			this.EquipT01.isHidden = true;
			this.EquipT01_1.isHidden = true;
		break;
		case 42:
		case 41:
			this.EquipT07.isHidden = false;
			this.EquipT07_1.isHidden = false;
			this.EquipT06.isHidden = false;
			this.EquipT06_1.isHidden = false;
			this.EquipT05.isHidden = false;
			this.EquipT05_1.isHidden = false;
			this.EquipT04.isHidden = false;
			this.EquipT04_1.isHidden = false;
			this.EquipT03.isHidden = false;
			this.EquipT03_1.isHidden = false;
			this.EquipT02.isHidden = true;
			this.EquipT02_1.isHidden = true;
			this.EquipT01.isHidden = true;
			this.EquipT01_1.isHidden = true;
		break;
		case 40:
		case 39:
			this.EquipT07.isHidden = false;
			this.EquipT07_1.isHidden = false;
			this.EquipT06.isHidden = false;
			this.EquipT06_1.isHidden = false;
			this.EquipT05.isHidden = false;
			this.EquipT05_1.isHidden = false;
			this.EquipT04.isHidden = false;
			this.EquipT04_1.isHidden = false;
			this.EquipT03.isHidden = false;
			this.EquipT03_1.isHidden = false;
			this.EquipT02.isHidden = false;
			this.EquipT02_1.isHidden = false;
			this.EquipT01.isHidden = true;
			this.EquipT01_1.isHidden = true;
		break;
		default:
			if (attackTime < 39)
			{
				this.EquipT07.isHidden = false;
				this.EquipT07_1.isHidden = false;
				this.EquipT06.isHidden = false;
				this.EquipT06_1.isHidden = false;
				this.EquipT05.isHidden = false;
				this.EquipT05_1.isHidden = false;
				this.EquipT04.isHidden = false;
				this.EquipT04_1.isHidden = false;
				this.EquipT03.isHidden = false;
				this.EquipT03_1.isHidden = false;
				this.EquipT02.isHidden = false;
				this.EquipT02_1.isHidden = false;
				this.EquipT01.isHidden = false;
				this.EquipT01_1.isHidden = false;
			}
		break;
		}
	}
    
    
}