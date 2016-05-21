package com.lulan.shincolle.client.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.LogHelper;

/**
 * ModelSubmYo - PinkaLulan
 * Created using Tabula 4.1.1  2016/5/5
 */
public class ModelSubmYo extends ModelBase implements IModelEmotionAdv {
	
    public ModelRenderer BodyMain;
    public ModelRenderer Butt;
    public ModelRenderer Head;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer BodyMain1;
    public ModelRenderer BodyMain2;
    public ModelRenderer BoobL;
    public ModelRenderer BoobL2;
    public ModelRenderer BoobR;
    public ModelRenderer BoobR2;
    public ModelRenderer Butt1;
    public ModelRenderer Butt2;
    public ModelRenderer EquipBase;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer LegLeft02;
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
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer EquipBody00;
    public ModelRenderer EquipJaw00;
    public ModelRenderer EquipHeadBack00;
    public ModelRenderer EquipBody01;
    public ModelRenderer EquipBody02;
    public ModelRenderer EquipJaw00a;
    public ModelRenderer EquipT01;
    public ModelRenderer EquipJaw01;
    public ModelRenderer EquipJaw02;
    public ModelRenderer EquipJaw03;
    public ModelRenderer EquipJaw04;
    public ModelRenderer EquipJaw01a;
    public ModelRenderer EquipJaw02a;
    public ModelRenderer EquipJaw03a;
    public ModelRenderer EquipJaw04a;
    public ModelRenderer EquipT01a;
    public ModelRenderer EquipT01b;
    public ModelRenderer EquipT01c;
    public ModelRenderer EquipHeadBack00a;
    public ModelRenderer EquipHead00;
    public ModelRenderer EquipT02;
    public ModelRenderer EquipHead00a;
    public ModelRenderer EquipHead00b;
    public ModelRenderer EquipHead00c;
    public ModelRenderer Eye01;
    public ModelRenderer Eye02;
    public ModelRenderer Eye03;
    public ModelRenderer EquipHead01;
    public ModelRenderer EquipHead02;
    public ModelRenderer EquipHead03;
    public ModelRenderer EquipHead04;
    public ModelRenderer EquipHead01a;
    public ModelRenderer EquipHead02a;
    public ModelRenderer EquipHead03a;
    public ModelRenderer EquipHead04a;
    public ModelRenderer EquipE01a;
    public ModelRenderer EquipE01b;
    public ModelRenderer EquipE01c;
    public ModelRenderer EquipE01d;
    public ModelRenderer EquipT02a;
    public ModelRenderer EquipT02b;
    public ModelRenderer EquipT02c;
    public ModelRenderer EquipS02a;
    public ModelRenderer EquipS02b;
    public ModelRenderer EquipS02c;
    public ModelRenderer EquipS02d;
    public ModelRenderer EquipS01a;
    public ModelRenderer EquipS01b;
    public ModelRenderer EquipS01c;
    public ModelRenderer EquipS01d;
    public ModelRenderer EquipT03;
    public ModelRenderer EquipT04;
    public ModelRenderer EquipT03a;
    public ModelRenderer EquipT03b;
    public ModelRenderer EquipT03c;
    public ModelRenderer EquipT04a;
    public ModelRenderer EquipT04b;
    public ModelRenderer EquipT04c;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowEquipBase;
    public ModelRenderer GlowEquipBody00;
    public ModelRenderer GlowEquipHeadBack00;
    public ModelRenderer GlowEquipHeadBack00a;
    public ModelRenderer GlowEquipHead00;
    public ModelRenderer GlowEquipBody01;

    private Random rand = new Random();
    private int startEmo2 = 0;

    
    public ModelSubmYo() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.LegLeft02 = new ModelRenderer(this, 0, 87);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(-3.0F, 12.0F, -3.0F);
        this.LegLeft02.addBox(0.0F, 0.0F, 0.0F, 6, 13, 6, 0.0F);
        this.EquipT02c = new ModelRenderer(this, 70, 15);
        this.EquipT02c.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.EquipT02c.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.0F);
        this.setRotateAngle(EquipT02c, 0.41887902047863906F, 0.0F, 0.0F);
        this.EquipS02b = new ModelRenderer(this, 22, 32);
        this.EquipS02b.setRotationPoint(9.0F, 9.0F, 12.0F);
        this.EquipS02b.addBox(-4.5F, -2.5F, -1.0F, 9, 5, 2, 0.0F);
        this.setRotateAngle(EquipS02b, -0.7853981633974483F, -1.7453292519943295F, 0.0F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face2.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.EquipT03c = new ModelRenderer(this, 70, 15);
        this.EquipT03c.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.EquipT03c.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.0F);
        this.setRotateAngle(EquipT03c, -0.3490658503988659F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 2, 88);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.0F, 10.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 11, 5, 0.0F);
        this.EquipE01b = new ModelRenderer(this, 22, 32);
        this.EquipE01b.setRotationPoint(-0.3F, -5.0F, 0.0F);
        this.EquipE01b.addBox(-4.5F, -2.5F, -1.0F, 9, 5, 2, 0.0F);
        this.setRotateAngle(EquipE01b, -2.2689280275926285F, 1.5707963267948966F, 0.0F);
        this.EquipE01d = new ModelRenderer(this, 22, 32);
        this.EquipE01d.setRotationPoint(-0.5F, 0.0F, 5.0F);
        this.EquipE01d.addBox(-4.5F, -2.5F, -1.0F, 9, 5, 2, 0.0F);
        this.setRotateAngle(EquipE01d, 0.5235987755982988F, 0.0F, 1.5707963267948966F);
        this.EquipJaw04 = new ModelRenderer(this, 18, 5);
        this.EquipJaw04.setRotationPoint(-6.8F, 1.2F, -2.7F);
        this.EquipJaw04.addBox(-6.0F, -15.0F, -4.0F, 12, 15, 4, 0.0F);
        this.setRotateAngle(EquipJaw04, 0.13962634015954636F, 1.5707963267948966F, 0.0F);
        this.Eye01 = new ModelRenderer(this, 70, 0);
        this.Eye01.setRotationPoint(11.0F, -8.5F, -6.0F);
        this.Eye01.addBox(-1.0F, 0.0F, -3.0F, 2, 7, 7, 0.0F);
        this.setRotateAngle(Eye01, 0.0F, -0.10471975511965977F, -0.17453292519943295F);
        this.EquipT02 = new ModelRenderer(this, 20, 3);
        this.EquipT02.setRotationPoint(0.0F, -9.0F, -10.0F);
        this.EquipT02.addBox(-3.5F, 0.0F, -3.5F, 7, 7, 7, 0.0F);
        this.setRotateAngle(EquipT02, 2.6179938779914944F, 0.0F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 2, 88);
        this.ArmRight01.setRotationPoint(-7.8F, -9.7F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 11, 5, 0.0F);
        this.setRotateAngle(ArmRight01, -1.2217304763960306F, 0.0F, 0.8726646259971648F);
        this.LegRight02 = new ModelRenderer(this, 0, 87);
        this.LegRight02.setRotationPoint(3.0F, 12.0F, -3.0F);
        this.LegRight02.addBox(-6.0F, 0.0F, 0.0F, 6, 13, 6, 0.0F);
        this.EquipHead01 = new ModelRenderer(this, 8, 6);
        this.EquipHead01.setRotationPoint(-5.1F, -4.0F, -4.0F);
        this.EquipHead01.addBox(-6.0F, 0.0F, -4.0F, 12, 15, 4, 0.0F);
        this.setRotateAngle(EquipHead01, -0.17453292519943295F, 0.13962634015954636F, 0.0F);
        this.BodyMain2 = new ModelRenderer(this, 88, 0);
        this.BodyMain2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain2.addBox(-6.5F, -11.0F, -4.0F, 13, 15, 7, 0.0F);
        this.BoobL2 = new ModelRenderer(this, 65, 34);
        this.BoobL2.mirror = true;
        this.BoobL2.setRotationPoint(2.44F, -8.6F, -3.9F);
        this.BoobL2.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobL2, -0.6981317007977318F, -0.08726646259971647F, -0.06981317007977318F);
        this.EquipHead04a = new ModelRenderer(this, 22, 25);
        this.EquipHead04a.setRotationPoint(0.0F, 14.5F, -3.0F);
        this.EquipHead04a.addBox(-6.0F, 0.0F, 0.0F, 12, 5, 2, 0.0F);
        this.setRotateAngle(EquipHead04a, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipT02b = new ModelRenderer(this, 68, 14);
        this.EquipT02b.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.EquipT02b.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.setRotateAngle(EquipT02b, -0.5235987755982988F, 0.0F, 0.0F);
        this.BoobR2 = new ModelRenderer(this, 106, 37);
        this.BoobR2.setRotationPoint(-2.44F, -8.6F, -3.9F);
        this.BoobR2.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobR2, -0.6981317007977318F, 0.08726646259971647F, 0.06981317007977318F);
        this.HairR02 = new ModelRenderer(this, 24, 88);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.3F, 10.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairR02, 0.17453292519943295F, 0.08726646259971647F, -0.13962634015954636F);
        this.EquipS01b = new ModelRenderer(this, 41, 35);
        this.EquipS01b.setRotationPoint(-6.5F, 11.0F, 3.0F);
        this.EquipS01b.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
        this.setRotateAngle(EquipS01b, -0.2617993877991494F, 0.0F, 0.2617993877991494F);
        this.EquipBody00 = new ModelRenderer(this, 1, 0);
        this.EquipBody00.setRotationPoint(0.0F, 5.0F, 7.5F);
        this.EquipBody00.addBox(-10.0F, -10.0F, 1.0F, 20, 12, 13, 0.0F);
        this.setRotateAngle(EquipBody00, -0.5235987755982988F, 0.0F, 0.0F);
        this.EquipS02a = new ModelRenderer(this, 22, 32);
        this.EquipS02a.setRotationPoint(9.0F, 9.0F, 4.0F);
        this.EquipS02a.addBox(-4.5F, -2.5F, -1.0F, 9, 5, 2, 0.0F);
        this.setRotateAngle(EquipS02a, -0.7853981633974483F, -1.3962634015954636F, 0.0F);
        this.EquipT01b = new ModelRenderer(this, 68, 14);
        this.EquipT01b.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.EquipT01b.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.setRotateAngle(EquipT01b, 0.5235987755982988F, 0.0F, 0.0F);
        this.EquipT03a = new ModelRenderer(this, 68, 14);
        this.EquipT03a.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.EquipT03a.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.setRotateAngle(EquipT03a, -0.2617993877991494F, 0.0F, 0.0F);
        this.EquipT03 = new ModelRenderer(this, 24, 7);
        this.EquipT03.setRotationPoint(7.0F, 9.5F, 8.5F);
        this.EquipT03.addBox(-3.5F, 0.0F, -3.5F, 7, 7, 7, 0.0F);
        this.setRotateAngle(EquipT03, 1.3962634015954636F, 0.17453292519943295F, 0.0F);
        this.Hair03 = new ModelRenderer(this, 0, 40);
        this.Hair03.setRotationPoint(0.0F, 12.5F, 0.0F);
        this.Hair03.addBox(-8.0F, 0.0F, -4.5F, 16, 15, 7, 0.0F);
        this.setRotateAngle(Hair03, 1.7453292519943295F, 0.0F, 0.0F);
        this.EquipT03b = new ModelRenderer(this, 68, 14);
        this.EquipT03b.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.EquipT03b.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.setRotateAngle(EquipT03b, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipS01d = new ModelRenderer(this, 41, 35);
        this.EquipS01d.setRotationPoint(-6.5F, 11.0F, 11.0F);
        this.EquipS01d.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
        this.setRotateAngle(EquipS01d, 0.2617993877991494F, 0.0F, 0.2617993877991494F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -11.8F, -0.5F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.setRotateAngle(Head, -0.5235987755982988F, 0.0F, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face0.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.HairL01 = new ModelRenderer(this, 24, 91);
        this.HairL01.setRotationPoint(-4.9F, 8.0F, -7.2F);
        this.HairL01.addBox(-2.5F, 0.0F, 0.0F, 5, 9, 2, 0.0F);
        this.setRotateAngle(HairL01, 0.08726646259971647F, 0.13962634015954636F, -0.05235987755982988F);
        this.EquipT04b = new ModelRenderer(this, 68, 14);
        this.EquipT04b.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.EquipT04b.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.setRotateAngle(EquipT04b, -0.5235987755982988F, 0.0F, 0.0F);
        this.HairR01 = new ModelRenderer(this, 24, 88);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(5.7F, 7.9F, -7.5F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairR01, -0.13962634015954636F, 0.4363323129985824F, 0.13962634015954636F);
        this.EquipHead02 = new ModelRenderer(this, 32, 0);
        this.EquipHead02.setRotationPoint(5.1F, -4.0F, -4.0F);
        this.EquipHead02.addBox(-6.0F, 0.0F, -4.0F, 12, 15, 4, 0.0F);
        this.setRotateAngle(EquipHead02, -0.17453292519943295F, -0.13962634015954636F, 0.0F);
        this.EquipS02c = new ModelRenderer(this, 22, 32);
        this.EquipS02c.setRotationPoint(-9.0F, 9.0F, 12.0F);
        this.EquipS02c.addBox(-4.5F, -2.5F, -1.0F, 9, 5, 2, 0.0F);
        this.setRotateAngle(EquipS02c, -0.7853981633974483F, 1.7453292519943295F, 0.0F);
        this.HairL02 = new ModelRenderer(this, 24, 91);
        this.HairL02.setRotationPoint(-0.3F, 7.5F, 0.1F);
        this.HairL02.addBox(-2.5F, 0.0F, 0.0F, 5, 12, 2, 0.0F);
        this.setRotateAngle(HairL02, 0.3141592653589793F, 0.17453292519943295F, 0.17453292519943295F);
        this.EquipJaw00a = new ModelRenderer(this, 0, 0);
        this.EquipJaw00a.setRotationPoint(0.0F, 11.0F, -10.0F);
        this.EquipJaw00a.addBox(-10.0F, -2.0F, -6.0F, 20, 4, 9, 0.0F);
        this.setRotateAngle(EquipJaw00a, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipT04a = new ModelRenderer(this, 68, 14);
        this.EquipT04a.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.EquipT04a.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.setRotateAngle(EquipT04a, 0.17453292519943295F, 0.0F, 0.0F);
        this.Butt1 = new ModelRenderer(this, 52, 66);
        this.Butt1.setRotationPoint(0.0F, 2.5F, 2.8F);
        this.Butt1.addBox(-7.5F, 0.0F, -7.0F, 15, 7, 8, 0.0F);
        this.setRotateAngle(Butt1, 0.20943951023931953F, 0.0F, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 87);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.4F, 6.5F, -4.0F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegLeft01, 1.5707963267948966F, 0.0F, 0.10471975511965977F);
        this.Ahoke = new ModelRenderer(this, 0, 18);
        this.Ahoke.setRotationPoint(-1.0F, -7.0F, -5.5F);
        this.Ahoke.addBox(0.0F, -5.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.2617993877991494F, 0.6981317007977318F, 0.0F);
        this.EquipHead00b = new ModelRenderer(this, 0, 0);
        this.EquipHead00b.setRotationPoint(-10.0F, -2.0F, -2.0F);
        this.EquipHead00b.addBox(-1.5F, 0.0F, -3.5F, 3, 10, 7, 0.0F);
        this.setRotateAngle(EquipHead00b, 0.20943951023931953F, -0.06981317007977318F, 0.13962634015954636F);
        this.EquipBody01 = new ModelRenderer(this, 5, 0);
        this.EquipBody01.setRotationPoint(0.0F, -2.4F, 3.0F);
        this.EquipBody01.addBox(-8.5F, 0.0F, 0.0F, 17, 12, 13, 0.0F);
        this.setRotateAngle(EquipBody01, 0.45378560551852565F, 0.0F, 0.0F);
        this.EquipHead04 = new ModelRenderer(this, 34, 5);
        this.EquipHead04.setRotationPoint(-6.8F, -4.2F, -2.6F);
        this.EquipHead04.addBox(-6.0F, 0.0F, -4.0F, 12, 15, 4, 0.0F);
        this.setRotateAngle(EquipHead04, -0.13962634015954636F, 1.5707963267948966F, 0.0F);
        this.Hair01 = new ModelRenderer(this, 0, 62);
        this.Hair01.setRotationPoint(0.0F, 8.0F, 1.1F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 16, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.5759586531581287F, 0.0F, 0.0F);
        this.EquipE01c = new ModelRenderer(this, 22, 32);
        this.EquipE01c.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipE01c.addBox(-4.5F, -2.5F, -1.0F, 9, 5, 2, 0.0F);
        this.setRotateAngle(EquipE01c, -1.0471975511965976F, 0.0F, 1.5707963267948966F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.Butt = new ModelRenderer(this, 0, 0);
        this.Butt.setRotationPoint(0.0F, 2.5F, 2.8F);
        this.setRotateAngle(Butt, 0.20943951023931953F, 0.0F, 0.0F);
        this.EquipHead00c = new ModelRenderer(this, 17, 5);
        this.EquipHead00c.setRotationPoint(10.0F, -2.0F, -2.0F);
        this.EquipHead00c.addBox(-1.5F, 0.0F, -3.5F, 3, 10, 7, 0.0F);
        this.setRotateAngle(EquipHead00c, 0.20943951023931953F, 0.06981317007977318F, -0.13962634015954636F);
        this.EquipHead01a = new ModelRenderer(this, 22, 25);
        this.EquipHead01a.setRotationPoint(0.0F, 14.5F, -3.0F);
        this.EquipHead01a.addBox(-6.0F, 0.0F, 0.0F, 12, 5, 2, 0.0F);
        this.setRotateAngle(EquipHead01a, 0.17453292519943295F, 0.0F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 106);
        this.BodyMain.setRotationPoint(0.0F, -11.0F, -3.0F);
        this.BodyMain.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(BodyMain, 0.6981317007977318F, 0.0F, 0.0F);
        this.EquipT01c = new ModelRenderer(this, 70, 15);
        this.EquipT01c.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.EquipT01c.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.0F);
        this.setRotateAngle(EquipT01c, -0.3490658503988659F, 0.0F, 0.0F);
        this.BodyMain1 = new ModelRenderer(this, 0, 106);
        this.BodyMain1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain1.addBox(-6.5F, -11.0F, -4.0F, 13, 15, 7, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face3.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.EquipJaw02a = new ModelRenderer(this, 22, 25);
        this.EquipJaw02a.setRotationPoint(0.0F, -15.5F, -3.0F);
        this.EquipJaw02a.addBox(-6.0F, -5.0F, 0.0F, 12, 5, 2, 0.0F);
        this.setRotateAngle(EquipJaw02a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipS01c = new ModelRenderer(this, 41, 35);
        this.EquipS01c.setRotationPoint(6.5F, 11.0F, 11.0F);
        this.EquipS01c.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
        this.setRotateAngle(EquipS01c, 0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.EquipS02d = new ModelRenderer(this, 22, 32);
        this.EquipS02d.setRotationPoint(-9.0F, 9.0F, 4.0F);
        this.EquipS02d.addBox(-4.5F, -2.5F, -1.0F, 9, 5, 2, 0.0F);
        this.setRotateAngle(EquipS02d, -0.7853981633974483F, 1.3962634015954636F, 0.0F);
        this.BoobL = new ModelRenderer(this, 34, 102);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.2F, -8.6F, -3.9F);
        this.BoobL.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, -0.08726646259971647F, -0.06981317007977318F);
        this.EquipHead00 = new ModelRenderer(this, 1, 0);
        this.EquipHead00.setRotationPoint(0.0F, -1.0F, -10.0F);
        this.EquipHead00.addBox(-10.0F, -12.0F, -11.0F, 20, 12, 13, 0.0F);
        this.setRotateAngle(EquipHead00, 0.20943951023931953F, 0.0F, 0.0F);
        this.EquipJaw03 = new ModelRenderer(this, 0, 0);
        this.EquipJaw03.setRotationPoint(6.8F, 1.2F, -2.7F);
        this.EquipJaw03.addBox(-6.0F, -15.0F, -4.0F, 12, 15, 4, 0.0F);
        this.setRotateAngle(EquipJaw03, 0.13962634015954636F, -1.5707963267948966F, 0.0F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face1.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.EquipT01a = new ModelRenderer(this, 68, 14);
        this.EquipT01a.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.EquipT01a.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.setRotateAngle(EquipT01a, 0.31869712141416456F, 0.0F, 0.0F);
        this.EquipHead03a = new ModelRenderer(this, 22, 25);
        this.EquipHead03a.setRotationPoint(0.0F, 14.5F, -3.0F);
        this.EquipHead03a.addBox(-6.0F, 0.0F, 0.0F, 12, 5, 2, 0.0F);
        this.setRotateAngle(EquipHead03a, 0.17453292519943295F, 0.0F, 0.0F);
        this.EquipT01 = new ModelRenderer(this, 38, 0);
        this.EquipT01.setRotationPoint(0.0F, 15.0F, -8.0F);
        this.EquipT01.addBox(-3.5F, -3.5F, -3.5F, 7, 7, 7, 0.0F);
        this.setRotateAngle(EquipT01, 0.6283185307179586F, 0.0F, 0.0F);
        this.EquipT02a = new ModelRenderer(this, 68, 14);
        this.EquipT02a.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.EquipT02a.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
        this.setRotateAngle(EquipT02a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 87);
        this.LegRight01.setRotationPoint(-4.4F, 6.5F, -4.0F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegRight01, 1.5707963267948966F, 0.0F, -0.10471975511965977F);
        this.Eye03 = new ModelRenderer(this, 70, 0);
        this.Eye03.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.Eye03.addBox(-1.0F, -3.5F, -3.5F, 2, 7, 7, 0.0F);
        this.setRotateAngle(Eye03, 0.0F, 0.8726646259971648F, 1.5707963267948966F);
        this.EquipS01a = new ModelRenderer(this, 41, 35);
        this.EquipS01a.setRotationPoint(6.5F, 11.0F, 3.0F);
        this.EquipS01a.addBox(-3.0F, 0.0F, -3.0F, 6, 3, 6, 0.0F);
        this.setRotateAngle(EquipS01a, -0.2617993877991494F, 0.0F, -0.2617993877991494F);
        this.EquipJaw00 = new ModelRenderer(this, 1, 0);
        this.EquipJaw00.setRotationPoint(0.0F, -3.0F, 5.0F);
        this.EquipJaw00.addBox(-10.0F, 0.0F, -11.0F, 20, 12, 13, 0.0F);
        this.setRotateAngle(EquipJaw00, 0.13962634015954636F, 0.0F, 0.0F);
        this.EquipBody02 = new ModelRenderer(this, 7, 0);
        this.EquipBody02.setRotationPoint(0.0F, -12.0F, 11.0F);
        this.EquipBody02.addBox(-8.0F, 0.0F, 0.0F, 16, 12, 13, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 2, 88);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -9.7F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 11, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, -1.2217304763960306F, 0.0F, -0.8726646259971648F);
        this.EquipJaw01a = new ModelRenderer(this, 22, 25);
        this.EquipJaw01a.setRotationPoint(0.0F, -15.5F, -3.0F);
        this.EquipJaw01a.addBox(-6.0F, -5.0F, 0.0F, 12, 5, 2, 0.0F);
        this.setRotateAngle(EquipJaw01a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipHead00a = new ModelRenderer(this, 0, 0);
        this.EquipHead00a.setRotationPoint(0.0F, -8.0F, -12.0F);
        this.EquipHead00a.addBox(-10.0F, -4.0F, -5.5F, 20, 4, 9, 0.0F);
        this.setRotateAngle(EquipHead00a, 0.20943951023931953F, 0.0F, 0.0F);
        this.EquipJaw01 = new ModelRenderer(this, 0, 0);
        this.EquipJaw01.setRotationPoint(-5.1F, 2.0F, -4.0F);
        this.EquipJaw01.addBox(-6.0F, -16.0F, -4.0F, 12, 15, 4, 0.0F);
        this.setRotateAngle(EquipJaw01, 0.17453292519943295F, 0.13962634015954636F, 0.0F);
        this.EquipJaw03a = new ModelRenderer(this, 22, 25);
        this.EquipJaw03a.setRotationPoint(0.0F, -14.5F, -3.0F);
        this.EquipJaw03a.addBox(-6.0F, -5.0F, 0.0F, 12, 5, 2, 0.0F);
        this.setRotateAngle(EquipJaw03a, -0.17453292519943295F, 0.0F, 0.0F);
        this.Eye02 = new ModelRenderer(this, 70, 0);
        this.Eye02.setRotationPoint(-11.0F, -8.5F, -6.0F);
        this.Eye02.addBox(-1.0F, 0.0F, -3.0F, 2, 7, 7, 0.0F);
        this.setRotateAngle(Eye02, 0.0F, 0.10471975511965977F, 0.17453292519943295F);
        this.Butt2 = new ModelRenderer(this, 82, 22);
        this.Butt2.setRotationPoint(0.0F, 2.5F, 2.8F);
        this.Butt2.addBox(-7.5F, 0.0F, -7.0F, 15, 7, 8, 0.0F);
        this.setRotateAngle(Butt2, 0.20943951023931953F, 0.0F, 0.0F);
        this.EquipT04c = new ModelRenderer(this, 70, 15);
        this.EquipT04c.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.EquipT04c.addBox(-2.0F, 0.0F, -2.0F, 4, 9, 4, 0.0F);
        this.setRotateAngle(EquipT04c, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipHead02a = new ModelRenderer(this, 22, 25);
        this.EquipHead02a.setRotationPoint(0.0F, 14.5F, -3.0F);
        this.EquipHead02a.addBox(-6.0F, 0.0F, 0.0F, 12, 5, 2, 0.0F);
        this.setRotateAngle(EquipHead02a, 0.17453292519943295F, 0.0F, 0.0F);
        this.HairU01 = new ModelRenderer(this, 50, 44);
        this.HairU01.mirror = true;
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.7F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 7, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 81);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.4F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 12, 8, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -6.1F);
        this.Face4.addBox(-7.0F, -14.2F, -0.5F, 14, 14, 1, 0.0F);
        this.BoobR = new ModelRenderer(this, 34, 102);
        this.BoobR.setRotationPoint(-3.2F, -8.6F, -3.9F);
        this.BoobR.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, 0.08726646259971647F, 0.06981317007977318F);
        this.EquipJaw02 = new ModelRenderer(this, 35, 0);
        this.EquipJaw02.setRotationPoint(5.1F, 2.0F, -4.0F);
        this.EquipJaw02.addBox(-6.0F, -16.0F, -4.0F, 12, 15, 4, 0.0F);
        this.setRotateAngle(EquipJaw02, 0.17453292519943295F, -0.13962634015954636F, 0.0F);
        this.EquipHeadBack00a = new ModelRenderer(this, 6, 0);
        this.EquipHeadBack00a.setRotationPoint(0.0F, -4.0F, -3.0F);
        this.EquipHeadBack00a.addBox(-8.0F, -11.0F, -11.0F, 16, 11, 13, 0.0F);
        this.EquipHeadBack00 = new ModelRenderer(this, 1, 0);
        this.EquipHeadBack00.setRotationPoint(0.0F, -8.0F, 9.0F);
        this.EquipHeadBack00.addBox(-9.0F, -10.0F, -10.0F, 18, 12, 13, 0.0F);
        this.setRotateAngle(EquipHeadBack00, -1.3962634015954636F, 0.0F, 0.0F);
        this.EquipE01a = new ModelRenderer(this, 22, 32);
        this.EquipE01a.setRotationPoint(-0.3F, 5.0F, 0.0F);
        this.EquipE01a.addBox(-4.5F, -2.5F, -1.0F, 9, 5, 2, 0.0F);
        this.setRotateAngle(EquipE01a, 2.2689280275926285F, 1.5707963267948966F, 0.0F);
        this.Hair02 = new ModelRenderer(this, 0, 63);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 5.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 16, 8, 0.0F);
        this.setRotateAngle(Hair02, 0.3490658503988659F, 0.0F, 0.0F);
        this.EquipJaw04a = new ModelRenderer(this, 22, 25);
        this.EquipJaw04a.setRotationPoint(0.0F, -14.5F, -3.0F);
        this.EquipJaw04a.addBox(-6.0F, -5.0F, 0.0F, 12, 5, 2, 0.0F);
        this.setRotateAngle(EquipJaw04a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipT04 = new ModelRenderer(this, 0, 7);
        this.EquipT04.setRotationPoint(-7.0F, 9.5F, 8.5F);
        this.EquipT04.addBox(-3.5F, 0.0F, -3.5F, 7, 7, 7, 0.0F);
        this.setRotateAngle(EquipT04, 1.3962634015954636F, -0.17453292519943295F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 2, 88);
        this.ArmRight02.setRotationPoint(-3.0F, 10.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 11, 5, 0.0F);
        this.EquipHead03 = new ModelRenderer(this, 0, 4);
        this.EquipHead03.setRotationPoint(6.8F, -4.2F, -2.6F);
        this.EquipHead03.addBox(-6.0F, 0.0F, -4.0F, 12, 15, 4, 0.0F);
        this.setRotateAngle(EquipHead03, -0.13962634015954636F, -1.5707963267948966F, 0.0F);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipT02b.addChild(this.EquipT02c);
        this.EquipBody01.addChild(this.EquipS02b);
        this.EquipT03b.addChild(this.EquipT03c);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.Eye03.addChild(this.EquipE01b);
        this.Eye03.addChild(this.EquipE01d);
        this.EquipJaw00a.addChild(this.EquipJaw04);
        this.EquipHeadBack00a.addChild(this.EquipT02);
        this.BodyMain.addChild(this.ArmRight01);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipHead00a.addChild(this.EquipHead01);
        this.BodyMain.addChild(this.BodyMain2);
        this.BodyMain.addChild(this.BoobL2);
        this.EquipHead04.addChild(this.EquipHead04a);
        this.EquipT02a.addChild(this.EquipT02b);
        this.BodyMain.addChild(this.BoobR2);
        this.HairR01.addChild(this.HairR02);
        this.EquipBase.addChild(this.EquipBody00);
        this.EquipBody01.addChild(this.EquipS02a);
        this.EquipT01a.addChild(this.EquipT01b);
        this.EquipT03.addChild(this.EquipT03a);
        this.EquipBody02.addChild(this.EquipT03);
        this.Hair02.addChild(this.Hair03);
        this.EquipT03a.addChild(this.EquipT03b);
        this.BodyMain.addChild(this.Head);
        this.Hair.addChild(this.HairL01);
        this.EquipT04a.addChild(this.EquipT04b);
        this.Hair.addChild(this.HairR01);
        this.EquipHead00a.addChild(this.EquipHead02);
        this.EquipBody01.addChild(this.EquipS02c);
        this.HairL01.addChild(this.HairL02);
        this.EquipJaw00.addChild(this.EquipJaw00a);
        this.EquipT04.addChild(this.EquipT04a);
        this.BodyMain.addChild(this.Butt1);
        this.Butt.addChild(this.LegLeft01);
        this.Hair.addChild(this.Ahoke);
        this.EquipHead00.addChild(this.EquipHead00b);
        this.EquipBody00.addChild(this.EquipBody01);
        this.EquipHead00a.addChild(this.EquipHead04);
        this.HairMain.addChild(this.Hair01);
        this.Eye03.addChild(this.EquipE01c);
        this.Head.addChild(this.HairMain);
        this.BodyMain.addChild(this.Butt);
        this.EquipHead00.addChild(this.EquipHead00c);
        this.EquipHead01.addChild(this.EquipHead01a);
        this.EquipT01b.addChild(this.EquipT01c);
        this.BodyMain.addChild(this.BodyMain1);
        this.EquipJaw02.addChild(this.EquipJaw02a);
        this.BodyMain.addChild(this.BoobL);
        this.EquipHeadBack00a.addChild(this.EquipHead00);
        this.EquipJaw00a.addChild(this.EquipJaw03);
        this.EquipT01.addChild(this.EquipT01a);
        this.EquipHead03.addChild(this.EquipHead03a);
        this.EquipJaw00.addChild(this.EquipT01);
        this.EquipT02.addChild(this.EquipT02a);
        this.BodyMain.addChild(this.EquipBase);
        this.Butt.addChild(this.LegRight01);
        this.EquipBody01.addChild(this.EquipS02d);
        this.EquipBody00.addChild(this.EquipJaw00);
        this.EquipBody00.addChild(this.EquipBody02);
        this.BodyMain.addChild(this.ArmLeft01);
        this.EquipJaw01.addChild(this.EquipJaw01a);
        this.EquipHead00.addChild(this.EquipHead00a);
        this.EquipJaw00a.addChild(this.EquipJaw01);
        this.EquipJaw03.addChild(this.EquipJaw03a);
        this.BodyMain.addChild(this.Butt2);
        this.EquipT04b.addChild(this.EquipT04c);
        this.EquipHead02.addChild(this.EquipHead02a);
        this.Hair.addChild(this.HairU01);
        this.Head.addChild(this.Hair);
        this.BodyMain.addChild(this.BoobR);
        this.EquipJaw00a.addChild(this.EquipJaw02);
        this.EquipHeadBack00.addChild(this.EquipHeadBack00a);
        this.EquipBody00.addChild(this.EquipHeadBack00);
        this.Eye03.addChild(this.EquipE01a);
        this.Hair01.addChild(this.Hair02);
        this.EquipJaw04.addChild(this.EquipJaw04a);
        this.EquipBody02.addChild(this.EquipT04);
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipHead00a.addChild(this.EquipHead03);
        
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -11.0F, -3.0F);
        this.setRotateAngle(GlowBodyMain, 0.6981317007977318F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -11.8F, -0.5F);
        this.setRotateAngle(GlowHead, -0.5235987755982988F, 0.0F, 0.0F);
        this.GlowEquipBase = new ModelRenderer(this, 0, 0);
        this.GlowEquipBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GlowEquipBody00 = new ModelRenderer(this, 0, 0);
        this.GlowEquipBody00.setRotationPoint(0.0F, 5.0F, 7.5F);
        this.setRotateAngle(GlowEquipBody00, -0.5235987755982988F, 0.0F, 0.0F);
        this.GlowEquipHeadBack00 = new ModelRenderer(this, 0, 0);
        this.GlowEquipHeadBack00.setRotationPoint(0.0F, -8.0F, 9.0F);
        this.setRotateAngle(GlowEquipHeadBack00, -1.3962634015954636F, 0.0F, 0.0F);
        this.GlowEquipHeadBack00a = new ModelRenderer(this, 0, 0);
        this.GlowEquipHeadBack00a.setRotationPoint(0.0F, -4.0F, -3.0F);
        this.GlowEquipHead00 = new ModelRenderer(this, 0, 0);
        this.GlowEquipHead00.setRotationPoint(0.0F, -1.0F, -10.0F);
        this.setRotateAngle(GlowEquipHead00, 0.20943951023931953F, 0.0F, 0.0F);
        this.GlowEquipBody01 = new ModelRenderer(this, 0, 0);
        this.GlowEquipBody01.setRotationPoint(0.0F, -2.4F, 3.0F);
        this.setRotateAngle(GlowEquipBody01, 0.45378560551852565F, 0.0F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        
        this.GlowBodyMain.addChild(this.GlowEquipBase);
        this.GlowEquipBase.addChild(this.GlowEquipBody00);
        this.GlowEquipBody00.addChild(this.GlowEquipHeadBack00);
        this.GlowEquipHeadBack00.addChild(this.GlowEquipHeadBack00a);
        this.GlowEquipHeadBack00a.addChild(this.GlowEquipHead00);
        this.GlowEquipHead00.addChild(this.Eye01);
        this.GlowEquipHead00.addChild(this.Eye02);
        this.GlowEquipHead00.addChild(this.Eye03);
        
        this.GlowEquipBody00.addChild(this.GlowEquipBody01);
        this.GlowEquipBody01.addChild(this.EquipS01a);
        this.GlowEquipBody01.addChild(this.EquipS01b);
        this.GlowEquipBody01.addChild(this.EquipS01c);
        this.GlowEquipBody01.addChild(this.EquipS01d);
        
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
    	GL11.glScalef(0.47F, 0.47F, 0.47F);
    	GL11.glTranslatef(0F, 1.78F, 0F);
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	GL11.glEnable(GL11.GL_CULL_FACE);  //disable drawing back face
    	GL11.glDisable(GL11.GL_LIGHTING);
    	
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	
    	GL11.glEnable(GL11.GL_LIGHTING);
    	GL11.glDisable(GL11.GL_CULL_FACE);
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glPopMatrix();
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) { 	
    	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		IShipEmotion ent = (IShipEmotion)entity;
		
		showEquip(ent);
		
		EmotionHelper.rollEmotionAdv(this, ent);

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
    	//outfit 2
    	this.BoobL2.rotateAngleX = this.BoobL.rotateAngleX;
    	this.BoobR2.rotateAngleX = this.BoobR.rotateAngleX;
    	this.Butt1.rotateAngleX = this.Butt.rotateAngleX;
    	this.Butt2.rotateAngleX = this.Butt.rotateAngleX;
    	
    	//頭部
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
		this.GlowEquipHeadBack00.rotateAngleX = this.EquipHeadBack00.rotateAngleX;
		this.GlowEquipHeadBack00.rotateAngleY = this.EquipHeadBack00.rotateAngleY;
		this.GlowEquipHeadBack00.rotateAngleZ = this.EquipHeadBack00.rotateAngleZ;
    }
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {
    	float angleX = MathHelper.cos(f2 * 0.08F);
    	setFaceHungry(ent);
    	
    	this.EquipBase.isHidden = false;
		this.GlowEquipBase.isHidden = false;
		this.Head.isHidden = true;
		this.GlowHead.isHidden = true;
		this.LegLeft01.isHidden = true;
		this.LegRight01.isHidden = true;

	  	//boob
  	    this.BoobL.rotateAngleX = -0.76F;
  	    this.BoobR.rotateAngleX = -0.76F;
  	    //body
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.21F;
	  	this.Butt.offsetZ = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.209F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -0.087F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -0.139F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	this.HairL01.rotateAngleX = -0.1F;
	  	this.HairL02.rotateAngleX = 0.3142F;
	  	this.HairR01.rotateAngleX = -0.1F;
	  	this.HairR02.rotateAngleX = 0.1745F;
	  	this.HairL01.rotateAngleZ = -0.0524F;
	  	this.HairL02.rotateAngleZ = 0.1745F;
	  	this.HairR01.rotateAngleZ = 0.1396F;
	  	this.HairR02.rotateAngleZ = -0.1396F;
  		
    	GL11.glTranslatef(0F, 1.3F, -0.1F);
	  	//body
	  	this.BodyMain.rotateAngleX = 0.2F;
	  	//arm
	  	this.ArmLeft01.rotateAngleX = -0.25F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = 0.2618F;
	    this.ArmRight01.rotateAngleX = -0.25F;
	    this.ArmRight01.rotateAngleY = 0F;
	    this.ArmRight01.rotateAngleZ = -0.2618F;
	    //equip
	    this.EquipHeadBack00.rotateAngleX = -0.15F;
	    this.EquipT01a.rotateAngleX = 0.5F;
	    this.EquipT01b.rotateAngleX = 0.5F;
	    this.EquipT01c.rotateAngleX = 0.5F;
	    this.EquipT02a.rotateAngleX = -0.7F;
	    this.EquipT02b.rotateAngleX = -0.5F;
	    this.EquipT02c.rotateAngleX = -0.5F;
	    this.EquipT03a.rotateAngleX = 0F;
	    this.EquipT03b.rotateAngleX = 0F;
	    this.EquipT03c.rotateAngleX = 0F;
	    this.EquipT04a.rotateAngleX = 0F;
	    this.EquipT04b.rotateAngleX = 0F;
	    this.EquipT04c.rotateAngleX = 0F;
	    this.EquipT03a.rotateAngleZ = 0.5F;
	    this.EquipT03b.rotateAngleZ = 0.6F;
	    this.EquipT03c.rotateAngleZ = 0.7F;
	    this.EquipT04a.rotateAngleZ = 0.3F;
	    this.EquipT04b.rotateAngleZ = 0.3F;
	    this.EquipT04c.rotateAngleZ = 0.3F;
	    this.EquipS01a.rotateAngleX = 0.2F;
	    this.EquipS01b.rotateAngleX = 0.3F;
	    this.EquipS01c.rotateAngleX = 0.2F;
	    this.EquipS01d.rotateAngleX = 0.5F;

    }
    
    private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
    {   
  		float angleX = MathHelper.cos(f2 * 0.08F);
  		float angleX1 = MathHelper.cos(f2*0.1F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.1F + 0.6F + f * 0.5F);
  		float angleX3 = MathHelper.cos(f2*0.1F + 0.9F + f * 0.5F);
  		float angleX4 = MathHelper.cos(f2*0.3F + 2F + f * 0.5F);
  		float angleX5 = MathHelper.cos(f2*0.3F + 4F + f * 0.5F);
  		float angleX6 = MathHelper.cos(f2*0.3F + 6F + f * 0.5F);
  		float angleX7 = MathHelper.sin(f2);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		float headX = 0F;
  		float headZ = 0F;
  		
  		//水上漂浮
  		if(((IShipFloating)ent).getShipDepth() > 0)
  		{
    		GL11.glTranslatef(0F, angleX * 0.1F, 0F);
    	}
  		
  		//head
  		this.Head.isHidden = false;
		this.GlowHead.isHidden = false;
	  	this.Head.rotateAngleX = f4 * 0.0143F;
	  	this.Head.rotateAngleY = f3 * 0.0143F;
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	  	//boob
  	    this.BoobL.rotateAngleX = angleX * 0.08F - 0.76F;
  	    this.BoobR.rotateAngleX = angleX * 0.08F - 0.76F;
  	    //body
  	    this.Ahoke.rotateAngleY = angleX * 0.15F + 0.6F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.21F;
	  	this.Butt.offsetZ = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.209F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -0.087F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -0.139F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	this.HairL01.rotateAngleX = -0.1F;
	  	this.HairL02.rotateAngleX = 0.3142F;
	  	this.HairR01.rotateAngleX = -0.1F;
	  	this.HairR02.rotateAngleX = 0.1745F;
	  	this.HairL01.rotateAngleZ = -0.0524F;
	  	this.HairL02.rotateAngleZ = 0.1745F;
	  	this.HairR01.rotateAngleZ = 0.1396F;
	  	this.HairR02.rotateAngleZ = -0.1396F;
  		
  		if (ent.getStateEmotion(ID.S.State) > ID.State.NORMAL)
  		{
  	    	GL11.glTranslatef(0F, angleX * 0.075F + 0.8F, -0.1F);
  			//head
  		  	this.Head.rotateAngleX -= 0.7F;
  		  	//body
  		  	this.BodyMain.rotateAngleX = 0.7F;
  		  	//arm 
  		  	this.ArmLeft01.rotateAngleX = -angleX * 0.1F - 1.0472F;
  		  	this.ArmLeft01.rotateAngleY = 0F;
  		    this.ArmLeft01.rotateAngleZ = -angleX * 0.1F - 0.7F;
  		    this.ArmRight01.rotateAngleX = -angleX * 0.1F - 1.0472F;
  		  	this.ArmRight01.rotateAngleY = 0F;
  		    this.ArmRight01.rotateAngleZ = angleX * 0.1F + 0.7F;
  		    //equip
  		    this.EquipHeadBack00.rotateAngleX = angleX * 0.05F - 1.7F;
  		    this.EquipT01a.rotateAngleX = angleX6 * 0.22F + 0.5F;
  		    this.EquipT01b.rotateAngleX = angleX5 * 0.44F;
  		    this.EquipT01c.rotateAngleX = angleX4 * 0.66F;
  		    this.EquipT02a.rotateAngleX = -angleX6 * 0.22F;
		    this.EquipT02b.rotateAngleX = -angleX5 * 0.44F;
		    this.EquipT02c.rotateAngleX = -angleX4 * 0.66F;
		    this.EquipT03a.rotateAngleX = 0F;
  		    this.EquipT03b.rotateAngleX = 0F;
  		    this.EquipT03c.rotateAngleX = 0F;
  		    this.EquipT04a.rotateAngleX = 0F;
		    this.EquipT04b.rotateAngleX = 0F;
		    this.EquipT04c.rotateAngleX = 0F;
		    this.EquipT03a.rotateAngleZ = angleX6 * 0.25F;
  		    this.EquipT03b.rotateAngleZ = angleX5 * 0.5F;
  		    this.EquipT03c.rotateAngleZ = angleX4 * 0.75F;
  		    this.EquipT04a.rotateAngleZ = -angleX6 * 0.25F;
		    this.EquipT04b.rotateAngleZ = -angleX5 * 0.5F;
		    this.EquipT04c.rotateAngleZ = -angleX4 * 0.75F;
		    this.EquipS01a.rotateAngleX = angleX7 * 0.05F * rand.nextFloat() - 0.2618F;
		    this.EquipS01b.rotateAngleX = angleX7 * 0.05F * rand.nextFloat() - 0.2618F;
		    this.EquipS01c.rotateAngleX = -angleX7 * 0.05F * rand.nextFloat() + 0.2618F;
		    this.EquipS01d.rotateAngleX = -angleX7 * 0.05F * rand.nextFloat() + 0.2618F;
  		}
  		else
  		{
  			//head
  		  	this.Head.rotateAngleX += 0.1F;
  		  	//body
  		  	this.BodyMain.rotateAngleX = -0.1047F;
  		    //arm 
  		  	this.ArmLeft01.rotateAngleX = 0.2094F;
  		  	this.ArmLeft01.rotateAngleY = 0F;
  		    this.ArmLeft01.rotateAngleZ = -angleX * 0.05F - 0.3142F;
  		    this.ArmRight01.rotateAngleX = 0F;
  		  	this.ArmRight01.rotateAngleY = 0F;
  		    this.ArmRight01.rotateAngleZ = angleX * 0.05F + 0.2094F;
  			//leg
  		    addk1 = angleAdd1 * 0.6F - 0.157F;
  		  	addk2 = angleAdd2 * 0.6F - 0.035F;
  		  	this.LegLeft01.rotateAngleY = 0F;
  		  	this.LegLeft01.rotateAngleZ = 0.1F;
  		  	this.LegRight01.rotateAngleY = 0F;
  		  	this.LegRight01.rotateAngleZ = -0.1F;
  		}

	  	//sprinting
	    if (ent.getIsSprinting() || f1 > 0.92F)
	    {	//奔跑動作
	    	GL11.glTranslatef(0F, 0.2F, 0F);
	    	
	    	if (ent.getStateEmotion(ID.S.State) > ID.State.NORMAL)
	  		{
	    		this.Head.rotateAngleX += 0.6F;
	  		}
	    	
		    //body
		    this.Head.rotateAngleX -= 1.1F;
	    	this.BodyMain.rotateAngleX = 1.1F;
	    	//arm
	    	this.ArmLeft01.rotateAngleX = -2.5133F;
		    this.ArmLeft01.rotateAngleZ = -0.22F;
		    this.ArmRight01.rotateAngleX = -2.5133F;
		    this.ArmRight01.rotateAngleZ = 0.22F;
		    //leg
		    this.LegLeft01.rotateAngleZ = 0.05F;
		  	this.LegRight01.rotateAngleZ = -0.05F;
  		}//end is sprinting
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {		//潛行, 蹲下動作
	    	GL11.glTranslatef(0F, 0.2F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.8378F;
		  	//hair
		  	this.Hair01.rotateAngleX -= 0.1F;
		  	this.Hair02.rotateAngleX -= 0.2F;
		  	this.Hair03.rotateAngleX -= 0.5F;
		    //arm 
		    this.ArmLeft01.rotateAngleX = -0.7F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
		    this.ArmRight01.rotateAngleX = -0.7F;
		    this.ArmRight01.rotateAngleZ = -0.2618F;
		    //leg
		    addk1 -= 0.1F;
		  	addk2 -= 0.1F;
		  	
		  	if (ent.getStateEmotion(ID.S.State) > ID.State.NORMAL)
	  		{
		  		this.Head.rotateAngleX += 0.8F;
		  		this.ArmLeft01.rotateAngleX = -0.25F;
		  		this.ArmRight01.rotateAngleX = -0.25F;
		  		this.EquipHeadBack00.rotateAngleX += 0.4F;
	  		}
  		}//end if sneaking
	    
	    //sitting riding
	    if (ent.getIsSitting() && !ent.getIsRiding())
	    {
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
		    	this.setFaceDamaged(ent);
	    		//潛水深度
		  		if (((IShipFloating)ent).getShipDepth() <= 0)
		  		{
		    		GL11.glTranslatef(0F, -angleX * 0.075F, 0F);
		    	}
			    //body
		    	this.Head.rotateAngleX *= 0.5F;
		    	this.Head.rotateAngleY *= 0.75F;
			    this.Head.rotateAngleX += 0.5F;
		    	this.BodyMain.rotateAngleX = 1.6F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -1.6F;
		    	this.ArmLeft01.rotateAngleZ = -2.3F;
		    	this.ArmRight01.rotateAngleX = -1.6F;
		    	this.ArmRight01.rotateAngleZ = 2.3F;
			    //leg
			    addk1 = -1.6F;
		    	addk2 = -1.6F;
			    this.LegLeft01.rotateAngleY = -0.1F - angleX * 0.05F;
			  	this.LegRight01.rotateAngleY = 0.1F + angleX * 0.05F;
			  	
			  	if (ent.getStateEmotion(ID.S.State) > ID.State.NORMAL)
		  		{
			  		GL11.glTranslatef(0F, 0.8F, 0F);
			  		float ax = MathHelper.cos(f2 * 0.5F) * 0.5F;
			  		
			  		this.ArmLeft01.rotateAngleX = ax + 0.1F;
			    	this.ArmRight01.rotateAngleX = -ax + 0.1F;
			    	//equip
			    	this.EquipHeadBack00.rotateAngleX = -0.7F;
		  		}
	    	}
	    	else
	    	{
		    	//body
		    	this.Head.rotateAngleX -= 0.7F;
		    	this.BodyMain.rotateAngleX = 0.5236F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -0.4F;
		    	this.ArmLeft01.rotateAngleZ = 0.3146F;
		    	this.ArmRight01.rotateAngleX = -0.4F;
		    	this.ArmRight01.rotateAngleZ = -0.3146F;
		    	//leg
		    	addk1 = -2.18F;
		    	addk2 = -2.18F;
		    	this.LegLeft01.rotateAngleY = -0.3491F;
		    	this.LegRight01.rotateAngleY = 0.3491F;
		    	
		    	if (ent.getStateEmotion(ID.S.State) > ID.State.NORMAL)
		  		{
		    		//body
		    		this.Head.rotateAngleX += 0.7F;
		    		this.BodyMain.rotateAngleX = 0.3F;
		    		//arm
			    	this.ArmLeft01.rotateAngleX = -0.27F;
			    	this.ArmLeft01.rotateAngleZ = 0.3146F;
			    	this.ArmRight01.rotateAngleX = -0.27F;
			    	this.ArmRight01.rotateAngleZ = -0.3146F;
			    	//equip
			    	this.EquipHeadBack00.rotateAngleX += 0.45F;
		  		}
	    		else
	    		{
	    			GL11.glTranslatef(0F, 1.4F, 0F);
	    		}
	    	}
  		}//end sitting
	    
	    //attack
	    if (ent.getAttackTime() > 41)
	    {
	    	setFaceAttack(ent);
	    	//swing arm
		    float ft = (50 - ent.getAttackTime()) + (f2 - (int)f2);
		    ft *= 0.125F;
	  		float fa = MathHelper.sin(ft * ft * (float)Math.PI);
	        float fb = MathHelper.sin(MathHelper.sqrt_float(ft) * (float)Math.PI);
	        this.ArmLeft01.rotateAngleX += -fb * 80.0F * Values.N.RAD_MUL - 0.3F;
	        this.ArmLeft01.rotateAngleY += fa * 20.0F * Values.N.RAD_MUL - 0.4F;
	        this.ArmLeft01.rotateAngleZ += fb * 20.0F * Values.N.RAD_MUL;
	        this.ArmRight01.rotateAngleX += -fb * 80.0F * Values.N.RAD_MUL - 0.3F;
	        this.ArmRight01.rotateAngleY += -fa * 20.0F * Values.N.RAD_MUL + 0.4F;
	        this.ArmRight01.rotateAngleZ += -fb * 20.0F * Values.N.RAD_MUL;
	    }//end attack
	    
	    //鬢毛調整
	    headX = this.Head.rotateAngleX * -0.5F;
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleX += angleX1 * 0.08F + headX;
	  	this.Hair02.rotateAngleX += -angleX2 * 0.08F + headX * 0.5F + 0.1F;
	  	this.Hair03.rotateAngleX += -angleX3 * 0.08F + headX * 0.5F + 0.1F;
	    this.Hair01.rotateAngleZ += headZ;
	  	this.Hair02.rotateAngleZ += headZ * 0.5F;
	  	this.Hair03.rotateAngleZ += headZ * 0.5F;
		this.HairL01.rotateAngleX += angleX * 0.04F + headX;
	  	this.HairL02.rotateAngleX += angleX * 0.05F + headX * 0.8F;
	  	this.HairR01.rotateAngleX += angleX * 0.04F + headX;
	  	this.HairR02.rotateAngleX += angleX * 0.05F + headX * 0.8F;
	  	this.HairL01.rotateAngleZ += headZ;
	  	this.HairL02.rotateAngleZ += headZ;
	  	this.HairR01.rotateAngleZ += headZ * 2.5F;
	  	this.HairR02.rotateAngleZ += headZ * 0.8F;
	  	
	  	//swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if(f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.RAD_MUL - 0.3F;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.RAD_MUL + 0.4F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.RAD_MUL;
	  	}
	  	
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
    
    private void showEquip(IShipEmotion ent) {
    	//head equip
  		switch(ent.getStateEmotion(ID.S.State)) {
  		case ID.State.EQUIP00:
  			this.EquipBase.isHidden = false;
  			this.GlowEquipBase.isHidden = false;
  			this.Hair03.isHidden = true;
  			this.LegLeft01.isHidden = true;
  			this.LegRight01.isHidden = true;
  			break;
  		default:  //normal
  			this.EquipBase.isHidden = true;
  			this.GlowEquipBase.isHidden = true;
  			this.Hair03.isHidden = false;
  			this.LegLeft01.isHidden = false;
  			this.LegRight01.isHidden = false;
  			break;
  		}
  		
  		//outfit and torpedo
  		switch(ent.getStateEmotion(ID.S.State2)) {
  		case ID.State.EQUIP00_2:
  			this.BodyMain1.isHidden = true;
  			this.Butt1.isHidden = true;
  			this.BoobL.isHidden = true;
  			this.BoobR.isHidden = true;
  			this.BodyMain2.isHidden = false;
  			this.Butt2.isHidden = false;
  			this.BoobL2.isHidden = false;
  			this.BoobR2.isHidden = false;
  			break;
  		default:  //normal
  			this.BodyMain1.isHidden = false;
  			this.Butt1.isHidden = false;
  			this.BoobL.isHidden = false;
  			this.BoobR.isHidden = false;
  			this.BodyMain2.isHidden = true;
  			this.Butt2.isHidden = true;
  			this.BoobL2.isHidden = true;
  			this.BoobR2.isHidden = true;
  			break;
  		}
  	}
  	
    //設定顯示的臉型
  	@Override
  	public void setFace(int emo) {
  		switch(emo) {
  		case 0:
  			this.Face0.isHidden = false;
  			this.Face0.rotateAngleY = 0F;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 1:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = false;
  			this.Face1.rotateAngleY = 0F;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 2:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face2.rotateAngleY = 0F;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 3:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face3.rotateAngleY = 0F;
  			this.Face4.isHidden = true;
  			break;
  		case 4:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = false;
  			this.Face4.rotateAngleY = 0F;
  			break;
  		case 5:
  			this.Face0.isHidden = false;
  			this.Face0.rotateAngleY = 3.14159F;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 6:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = false;
  			this.Face1.rotateAngleY = 3.14159F;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 7:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face2.rotateAngleY = 3.14159F;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 8:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face3.rotateAngleY = 3.14159F;
  			this.Face4.isHidden = true;
  			break;
  		case 9:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = false;
  			this.Face4.rotateAngleY = 3.14159F;
  			break;
  		default:
  			break;
  		}
  	}

	@Override
	public void setFaceNormal(IShipEmotion ent) {
		setFace(0);
	}

	@Override
	public void setFaceBlink0(IShipEmotion ent) {
		setFace(0);		
	}

	@Override
	public void setFaceBlink1(IShipEmotion ent) {
		setFace(1);
	}

	@Override
	public void setFaceCry(IShipEmotion ent) {
		if (ent.getTickExisted() % 16 > 7)
		{
			setFace(7);
		}
		else
		{
			setFace(8);
		}
	}

	@Override
	public void setFaceAttack(IShipEmotion ent) {
		if (ent.getTickExisted() % 64 > 32)
		{
			setFace(3);
		}
		else
		{
			setFace(5);
		}
	}

	@Override
	public void setFaceDamaged(IShipEmotion ent) {
		int t = ent.getTickExisted() % 256;
		
		if (t < 80)
		{
			setFace(6);
		}
		else if (t < 160)
		{
			setFace(2);
		}
		else
		{
			setFace(3);
		}
	}

	@Override
	public void setFaceHungry(IShipEmotion ent) {
		setFace(4);		
	}

	@Override
	public void setFaceAngry(IShipEmotion ent) {
		if (ent.getTickExisted() % 64 > 32)
		{
			setFace(1);
		}
		else
		{
			setFace(2);
		}
	}

	@Override
	public void setFaceScorn(IShipEmotion ent) {
		setFace(2);		
	}
	
	@Override
	public void setFaceBored(IShipEmotion ent) {
	}
	
	@Override
	public void setFaceShy(IShipEmotion ent)
	{
		
	}
	
	@Override
	public void setFaceHappy(IShipEmotion ent)
	{
		
	}
	
	
}

