package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelBattleshipYamato - PinkaLulan
 * Created using Tabula 4.1.1
 */
public class ModelBattleshipYamato extends ModelBase implements IModelEmotion
{
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer Butt;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Cloth01;
    public ModelRenderer EquipBaseBelt;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Face0;
    public ModelRenderer EquipHeadBase;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairU01;
    public ModelRenderer HairL02;
    public ModelRenderer HairL03;
    public ModelRenderer HairR02;
    public ModelRenderer HairR03;
    public ModelRenderer HairBase;
    public ModelRenderer Hair00;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer Hair04;
    public ModelRenderer HeadEquip01a;
    public ModelRenderer HeadEquip02a;
    public ModelRenderer HeadEquip01b;
    public ModelRenderer HeadEquip01c;
    public ModelRenderer HeadEquip01d;
    public ModelRenderer HeadEquip01b2;
    public ModelRenderer HeadEquip02b;
    public ModelRenderer HeadEquip02c;
    public ModelRenderer HeadEquip02d;
    public ModelRenderer HeadEquip02b2;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt01;
    public ModelRenderer AnchorL;
    public ModelRenderer AnchorR;
    public ModelRenderer LegRight02;
    public ModelRenderer EquipLegR01;
    public ModelRenderer ShoesR01;
    public ModelRenderer EquipLegR02a;
    public ModelRenderer EquipLegR02b;
    public ModelRenderer EquipLegR02c;
    public ModelRenderer LegLeft02;
    public ModelRenderer EquipLegL01;
    public ModelRenderer ShoesL01;
    public ModelRenderer EquipLegL02a;
    public ModelRenderer EquipLegL02b;
    public ModelRenderer EquipLegL02c;
    public ModelRenderer Skirt02;
    public ModelRenderer ArmLeft01a;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer EquipU01;
    public ModelRenderer EquipU01a;
    public ModelRenderer EquipU01b;
    public ModelRenderer EquipU02;
    public ModelRenderer EquipU03a;
    public ModelRenderer EquipU04a;
    public ModelRenderer EquipU05a;
    public ModelRenderer EquipU06;
    public ModelRenderer EquipU09a;
    public ModelRenderer EquipU09b;
    public ModelRenderer EquipU09c;
    public ModelRenderer EquipU03b;
    public ModelRenderer EquipU03c;
    public ModelRenderer EquipU03d;
    public ModelRenderer EquipU04b;
    public ModelRenderer EquipU04c;
    public ModelRenderer EquipU04d;
    public ModelRenderer EquipU05b;
    public ModelRenderer EquipU05c;
    public ModelRenderer EquipU05d;
    public ModelRenderer EquipU07;
    public ModelRenderer EquipU08;
    public ModelRenderer Cloth02a;
    public ModelRenderer Cloth02b;
    public ModelRenderer EquipRotateBase;
    public ModelRenderer EquipBaseBelt2;
    public ModelRenderer EquipBaseM01a;
    public ModelRenderer EquipBaseM01b;
    public ModelRenderer EquipL01;
    public ModelRenderer EquipR01;
    public ModelRenderer EquipBaseM02;
    public ModelRenderer EquipL02;
    public ModelRenderer EquipL03;
    public ModelRenderer EquipL04;
    public ModelRenderer EquipLCBase01;
    public ModelRenderer EquipL05;
    public ModelRenderer EquipLC2Base01;
    public ModelRenderer EquipLC3Base01;
    public ModelRenderer EquipLC2Base02;
    public ModelRenderer EquipLC201a;
    public ModelRenderer EquipLC202a;
    public ModelRenderer EquipLC203a;
    public ModelRenderer EquipLC2Radar01;
    public ModelRenderer EquipLC2Radar02;
    public ModelRenderer EquipLC201b;
    public ModelRenderer EquipLC202b;
    public ModelRenderer EquipLC203b;
    public ModelRenderer EquipLC3Base02;
    public ModelRenderer EquipLC301a;
    public ModelRenderer EquipLC302a;
    public ModelRenderer EquipLC303a;
    public ModelRenderer EquipLC3Radar01;
    public ModelRenderer EquipLC3Radar02;
    public ModelRenderer EquipLC301b;
    public ModelRenderer EquipLC302b;
    public ModelRenderer EquipLC303b;
    public ModelRenderer EquipLCBase02;
    public ModelRenderer EquipLC01a;
    public ModelRenderer EquipLC02a;
    public ModelRenderer EquipLC03a;
    public ModelRenderer EquipLCRadar01;
    public ModelRenderer EquipLCRadar02;
    public ModelRenderer EquipLC01b;
    public ModelRenderer EquipLC02b;
    public ModelRenderer EquipLC03b;
    public ModelRenderer EquipR02;
    public ModelRenderer EquipMCBase01a;
    public ModelRenderer EquipMCBase01b;
    public ModelRenderer EquipR03;
    public ModelRenderer EquipRCBase01;
    public ModelRenderer EquipR04;
    public ModelRenderer EquipRCBase02;
    public ModelRenderer EquipRC01a;
    public ModelRenderer EquipRC02a;
    public ModelRenderer EquipRC03a;
    public ModelRenderer EquipRCRadar01;
    public ModelRenderer EquipRCRadar02;
    public ModelRenderer EquipRC01b;
    public ModelRenderer EquipRC02b;
    public ModelRenderer EquipRC03b;
    public ModelRenderer EquipR05;
    public ModelRenderer EquipRC2Base01;
    public ModelRenderer EquipRC3Base01;
    public ModelRenderer EquipRC2Base02;
    public ModelRenderer EquipRC201a;
    public ModelRenderer EquipRC202a;
    public ModelRenderer EquipRC203a;
    public ModelRenderer EquipRC2Radar01;
    public ModelRenderer EquipRC2Radar02;
    public ModelRenderer EquipRC201b;
    public ModelRenderer EquipRC202b;
    public ModelRenderer EquipRC203b;
    public ModelRenderer EquipRC3Base02;
    public ModelRenderer EquipRC301a;
    public ModelRenderer EquipRC302a;
    public ModelRenderer EquipRC303a;
    public ModelRenderer EquipRC3Radar01;
    public ModelRenderer EquipRC3Radar02;
    public ModelRenderer EquipRC301b;
    public ModelRenderer EquipRC302b;
    public ModelRenderer EquipRC303b;
    public ModelRenderer EquipLCBase01_1;
    public ModelRenderer EquipLCBase02_1;
    public ModelRenderer EquipLC01a_1;
    public ModelRenderer EquipLC02a_1;
    public ModelRenderer EquipLC03a_1;
    public ModelRenderer EquipMCRadar01;
    public ModelRenderer EquipMCRadar02;
    public ModelRenderer EquipLC01b_1;
    public ModelRenderer EquipLC02b_1;
    public ModelRenderer EquipLC03b_1;
    public ModelRenderer EquipBaseM03;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;

    private float scale;
    private float offsetY;
    
    
    public ModelBattleshipYamato()
    {
        this.textureWidth = 256;
        this.textureHeight = 128;
        
        this.EquipLC2Base01 = new ModelRenderer(this, 211, 23);
        this.EquipLC2Base01.setRotationPoint(2.5F, -4.0F, -10.5F);
        this.EquipLC2Base01.addBox(-4.0F, 0.0F, 0.0F, 8, 6, 7, 0.0F);
        this.EquipLC02a = new ModelRenderer(this, 128, 118);
        this.EquipLC02a.setRotationPoint(0.0F, -4.5F, -6.0F);
        this.EquipLC02a.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(EquipLC02a, -0.2617993877991494F, 0.0F, 0.0F);
        this.EquipRC3Base01 = new ModelRenderer(this, 211, 23);
        this.EquipRC3Base01.setRotationPoint(-7.0F, 5.0F, -12.0F);
        this.EquipRC3Base01.addBox(-4.0F, 0.0F, 0.0F, 8, 2, 7, 0.0F);
        this.setRotateAngle(EquipRC3Base01, 0.0F, 0.0F, -1.5707963267948966F);
        this.LegLeft01 = new ModelRenderer(this, 0, 63);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.8F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.2792526803190927F, 0.0F, 0.13962634015954636F);
        this.Cloth02a = new ModelRenderer(this, 21, 62);
        this.Cloth02a.setRotationPoint(0.0F, 3.2F, -4.0F);
        this.Cloth02a.addBox(-3.5F, 0.0F, 0.0F, 7, 4, 0, 0.0F);
        this.setRotateAngle(Cloth02a, -0.6981317007977318F, 0.0F, 0.0F);
        this.EquipRC2Radar02 = new ModelRenderer(this, 128, 38);
        this.EquipRC2Radar02.mirror = true;
        this.EquipRC2Radar02.setRotationPoint(-6.4F, -4.0F, -1.0F);
        this.EquipRC2Radar02.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.EquipRCRadar02 = new ModelRenderer(this, 58, 0);
        this.EquipRCRadar02.mirror = true;
        this.EquipRCRadar02.setRotationPoint(-13.3F, -7.0F, 5.0F);
        this.EquipRCRadar02.addBox(0.0F, 0.0F, 0.0F, 5, 4, 6, 0.0F);
        this.Skirt01 = new ModelRenderer(this, 0, 48);
        this.Skirt01.setRotationPoint(0.0F, 2.3F, 0.0F);
        this.Skirt01.addBox(-8.5F, 0.0F, -6.0F, 17, 4, 9, 0.0F);
        this.setRotateAngle(Skirt01, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipLegR01 = new ModelRenderer(this, 133, 8);
        this.EquipLegR01.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.EquipLegR01.addBox(-3.5F, 0.0F, -3.5F, 7, 2, 7, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 226, 83);
        this.LegRight01.setRotationPoint(-4.8F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.13962634015954636F, 0.0F, -0.13962634015954636F);
        this.EquipRotateBase = new ModelRenderer(this, 128, 0);
        this.EquipRotateBase.setRotationPoint(0.0F, 0.0F, 10.0F);
        this.EquipRotateBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 20, 29);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.0F, 13.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 14, 5, 0.0F);
        this.HeadEquip01d = new ModelRenderer(this, 91, 64);
        this.HeadEquip01d.setRotationPoint(3.5F, 0.2F, -1.0F);
        this.HeadEquip01d.addBox(0.0F, 0.0F, 0.0F, 0, 4, 2, 0.0F);
        this.EquipLC3Radar02 = new ModelRenderer(this, 128, 38);
        this.EquipLC3Radar02.mirror = true;
        this.EquipLC3Radar02.setRotationPoint(-6.4F, -4.0F, -1.0F);
        this.EquipLC3Radar02.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.EquipRC302a = new ModelRenderer(this, 128, 122);
        this.EquipRC302a.setRotationPoint(0.0F, -3.0F, -6.0F);
        this.EquipRC302a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipRC302a, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipLC302b = new ModelRenderer(this, 163, 30);
        this.EquipLC302b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipLC302b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.EquipRC3Radar02 = new ModelRenderer(this, 128, 38);
        this.EquipRC3Radar02.mirror = true;
        this.EquipRC3Radar02.setRotationPoint(-6.4F, -4.0F, -1.0F);
        this.EquipRC3Radar02.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.EquipU03d = new ModelRenderer(this, 128, 0);
        this.EquipU03d.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.EquipU03d.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.EquipRCBase02 = new ModelRenderer(this, 128, 0);
        this.EquipRCBase02.setRotationPoint(-0.5F, -4.5F, -2.0F);
        this.EquipRCBase02.addBox(-8.5F, -8.0F, -7.0F, 17, 8, 21, 0.0F);
        this.setRotateAngle(EquipRCBase02, -0.05235987755982988F, 0.0F, 0.0F);
        this.EquipRC2Radar01 = new ModelRenderer(this, 128, 38);
        this.EquipRC2Radar01.setRotationPoint(4.4F, -4.0F, -1.0F);
        this.EquipRC2Radar01.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.EquipLCRadar02 = new ModelRenderer(this, 58, 0);
        this.EquipLCRadar02.mirror = true;
        this.EquipLCRadar02.setRotationPoint(-13.3F, -7.0F, 5.0F);
        this.EquipLCRadar02.addBox(0.0F, 0.0F, 0.0F, 5, 4, 6, 0.0F);
        this.BoobL = new ModelRenderer(this, 33, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.7F, -8.5F, -3.5F);
        this.BoobL.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, 0.13962634015954636F, 0.08726646259971647F);
        this.EquipU01a = new ModelRenderer(this, 128, 0);
        this.EquipU01a.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.EquipU01a.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.EquipLCBase01 = new ModelRenderer(this, 196, 16);
        this.EquipLCBase01.setRotationPoint(3.0F, 3.0F, -5.5F);
        this.EquipLCBase01.addBox(-7.5F, -5.0F, -7.0F, 16, 9, 14, 0.0F);
        this.EquipLCRadar01 = new ModelRenderer(this, 58, 0);
        this.EquipLCRadar01.setRotationPoint(8.3F, -7.0F, 5.0F);
        this.EquipLCRadar01.addBox(0.0F, 0.0F, 0.0F, 5, 4, 6, 0.0F);
        this.EquipR01 = new ModelRenderer(this, 128, 0);
        this.EquipR01.setRotationPoint(0.0F, 5.5F, 3.0F);
        this.EquipR01.addBox(-16.0F, 0.0F, 0.0F, 16, 8, 5, 0.0F);
        this.setRotateAngle(EquipR01, -0.8726646259971648F, 0.0F, 0.0F);
        this.EquipLC3Base02 = new ModelRenderer(this, 128, 0);
        this.EquipLC3Base02.setRotationPoint(0.0F, 0.5F, 4.0F);
        this.EquipLC3Base02.addBox(-4.5F, -5.0F, -5.5F, 9, 5, 10, 0.0F);
        this.setRotateAngle(EquipLC3Base02, 0.05235987755982988F, 0.136659280431156F, 0.0F);
        this.EquipU09a = new ModelRenderer(this, 128, 0);
        this.EquipU09a.setRotationPoint(-5.0F, -23.0F, 6.0F);
        this.EquipU09a.addBox(0.0F, 0.0F, 0.0F, 10, 1, 1, 0.0F);
        this.setRotateAngle(EquipU09a, -0.2617993877991494F, 0.0F, 0.0F);
        this.EquipLegR02a = new ModelRenderer(this, 0, 84);
        this.EquipLegR02a.setRotationPoint(-3.2F, -0.7F, -2.5F);
        this.EquipLegR02a.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(EquipLegR02a, 0.0F, 0.0F, 0.05235987755982988F);
        this.EquipRC01b = new ModelRenderer(this, 204, 39);
        this.EquipRC01b.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipRC01b.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.EquipRC03b = new ModelRenderer(this, 204, 39);
        this.EquipRC03b.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipRC03b.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.EquipLegL01 = new ModelRenderer(this, 154, 12);
        this.EquipLegL01.mirror = true;
        this.EquipLegL01.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.EquipLegL01.addBox(-3.5F, 0.0F, -3.5F, 7, 2, 7, 0.0F);
        this.EquipRC3Base02 = new ModelRenderer(this, 128, 0);
        this.EquipRC3Base02.setRotationPoint(0.0F, 0.5F, 4.0F);
        this.EquipRC3Base02.addBox(-4.5F, -5.0F, -5.5F, 9, 5, 10, 0.0F);
        this.setRotateAngle(EquipRC3Base02, -0.05235987755982988F, -0.18203784098300857F, 0.0F);
        this.EquipU02 = new ModelRenderer(this, 222, 32);
        this.EquipU02.setRotationPoint(0.5F, -15.0F, 0.5F);
        this.EquipU02.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
        this.EquipBaseBelt2 = new ModelRenderer(this, 210, 0);
        this.EquipBaseBelt2.setRotationPoint(0.0F, -8.7F, 2.5F);
        this.EquipBaseBelt2.addBox(-7.0F, 0.0F, -4.0F, 14, 6, 8, 0.0F);
        this.EquipBaseM01a = new ModelRenderer(this, 128, 0);
        this.EquipBaseM01a.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.EquipBaseM01a.addBox(2.5F, 0.0F, -1.0F, 5, 4, 9, 0.0F);
        this.setRotateAngle(EquipBaseM01a, 0.8726646259971648F, 0.0F, 0.0F);
        this.EquipRC303b = new ModelRenderer(this, 163, 30);
        this.EquipRC303b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipRC303b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.HairL03 = new ModelRenderer(this, 86, 101);
        this.HairL03.setRotationPoint(-0.1F, 9.0F, 0.1F);
        this.HairL03.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(HairL03, 0.17453292519943295F, 0.0F, 0.22689280275926282F);
        this.EquipMCBase01b = new ModelRenderer(this, 128, 0);
        this.EquipMCBase01b.setRotationPoint(-8.0F, 8.0F, 0.0F);
        this.EquipMCBase01b.addBox(-4.0F, 0.0F, 0.0F, 4, 10, 5, 0.0F);
        this.setRotateAngle(EquipMCBase01b, 1.0471975511965976F, 0.0F, 0.0F);
        this.EquipLegL02c = new ModelRenderer(this, 0, 84);
        this.EquipLegL02c.setRotationPoint(3.2F, -0.7F, -2.5F);
        this.EquipLegL02c.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(EquipLegL02c, 0.0F, 0.0F, -0.05235987755982988F);
        this.EquipU04b = new ModelRenderer(this, 128, 0);
        this.EquipU04b.setRotationPoint(-0.5F, -16.0F, -0.5F);
        this.EquipU04b.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.HeadEquip02d = new ModelRenderer(this, 91, 64);
        this.HeadEquip02d.setRotationPoint(-3.5F, 0.2F, -1.0F);
        this.HeadEquip02d.addBox(0.0F, 0.0F, 0.0F, 0, 4, 2, 0.0F);
        this.ShoesR01 = new ModelRenderer(this, 18, 80);
        this.ShoesR01.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.ShoesR01.addBox(-3.5F, 0.0F, -0.5F, 7, 2, 7, 0.0F);
        this.Hair01 = new ModelRenderer(this, 166, 78);
        this.Hair01.setRotationPoint(0.0F, 0.7F, 1.3F);
        this.Hair01.addBox(-4.0F, -1.0F, -0.2F, 8, 20, 9, 0.0F);
        this.setRotateAngle(Hair01, -0.7285004297824331F, 0.0F, -0.36425021489121656F);
        this.EquipLC3Radar01 = new ModelRenderer(this, 128, 38);
        this.EquipLC3Radar01.setRotationPoint(4.4F, -4.0F, -1.0F);
        this.EquipLC3Radar01.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.EquipU06 = new ModelRenderer(this, 166, 60);
        this.EquipU06.setRotationPoint(0.0F, -31.1F, 5.5F);
        this.EquipU06.addBox(-8.0F, 0.0F, -8.0F, 16, 1, 16, 0.0F);
        this.setRotateAngle(EquipU06, -0.13788101090755206F, 0.7853981633974483F, -0.09599310885968812F);
        this.EquipRC201b = new ModelRenderer(this, 163, 30);
        this.EquipRC201b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipRC201b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.EquipU05b = new ModelRenderer(this, 128, 0);
        this.EquipU05b.setRotationPoint(-0.5F, -16.0F, -0.5F);
        this.EquipU05b.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.EquipRC203a = new ModelRenderer(this, 128, 122);
        this.EquipRC203a.setRotationPoint(2.6F, -3.0F, -6.0F);
        this.EquipRC203a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipRC203a, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipLCBase01_1 = new ModelRenderer(this, 196, 16);
        this.EquipLCBase01_1.setRotationPoint(-8.0F, 7.0F, 3.0F);
        this.EquipLCBase01_1.addBox(-8.0F, -5.0F, -7.0F, 16, 8, 14, 0.0F);
        this.setRotateAngle(EquipLCBase01_1, -2.5953045977155678F, 0.0F, 0.0F);
        this.EquipLC3Base01 = new ModelRenderer(this, 211, 23);
        this.EquipLC3Base01.setRotationPoint(7.0F, 5.0F, -12.0F);
        this.EquipLC3Base01.addBox(-4.0F, 0.0F, 0.0F, 8, 2, 7, 0.0F);
        this.setRotateAngle(EquipLC3Base01, 0.0F, 0.0F, 1.5707963267948966F);
        this.EquipLC303a = new ModelRenderer(this, 128, 122);
        this.EquipLC303a.setRotationPoint(2.6F, -3.0F, -6.0F);
        this.EquipLC303a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipLC303a, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipU09b = new ModelRenderer(this, 128, 0);
        this.EquipU09b.setRotationPoint(0.0F, -24.0F, -3.0F);
        this.EquipU09b.addBox(-0.4F, 0.0F, 0.0F, 1, 1, 11, 0.0F);
        this.setRotateAngle(EquipU09b, 0.0F, 0.5061454830783556F, 0.2617993877991494F);
        this.EquipLC203a = new ModelRenderer(this, 128, 122);
        this.EquipLC203a.setRotationPoint(2.6F, -3.0F, -6.0F);
        this.EquipLC203a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipLC203a, -0.13962634015954636F, 0.0F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 0, 29);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -9.7F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 14, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.20943951023931953F, 0.0F, -0.2617993877991494F);
        this.HeadEquip02a = new ModelRenderer(this, 128, 0);
        this.HeadEquip02a.setRotationPoint(-8.0F, 0.2F, 5.0F);
        this.HeadEquip02a.addBox(-2.0F, 0.0F, -2.0F, 2, 3, 4, 0.0F);
        this.HeadEquip01c = new ModelRenderer(this, 43, 82);
        this.HeadEquip01c.setRotationPoint(0.0F, -3.5F, 0.5F);
        this.HeadEquip01c.addBox(0.0F, 0.0F, 0.0F, 7, 4, 0, 0.0F);
        this.EquipL05 = new ModelRenderer(this, 174, 36);
        this.EquipL05.setRotationPoint(6.0F, -2.5F, -13.0F);
        this.EquipL05.addBox(-5.0F, 0.0F, -10.0F, 5, 13, 10, 0.0F);
        this.setRotateAngle(EquipL05, 0.0F, 0.7853981633974483F, 0.0F);
        this.EquipLC2Radar02 = new ModelRenderer(this, 128, 38);
        this.EquipLC2Radar02.mirror = true;
        this.EquipLC2Radar02.setRotationPoint(-6.4F, -4.0F, -1.0F);
        this.EquipLC2Radar02.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.EquipMCRadar02 = new ModelRenderer(this, 58, 0);
        this.EquipMCRadar02.mirror = true;
        this.EquipMCRadar02.setRotationPoint(-13.3F, -7.0F, 2.0F);
        this.EquipMCRadar02.addBox(0.0F, 0.0F, 0.0F, 5, 4, 6, 0.0F);
        this.EquipLC2Radar01 = new ModelRenderer(this, 128, 38);
        this.EquipLC2Radar01.setRotationPoint(4.4F, -4.0F, -1.0F);
        this.EquipLC2Radar01.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.EquipLC02b_1 = new ModelRenderer(this, 204, 39);
        this.EquipLC02b_1.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC02b_1.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.HairR02 = new ModelRenderer(this, 86, 101);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 8.0F, 0.3F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(HairR02, 0.296705972839036F, 0.0F, 0.3141592653589793F);
        this.EquipU04a = new ModelRenderer(this, 128, 0);
        this.EquipU04a.setRotationPoint(0.2F, 0.1F, 0.3F);
        this.EquipU04a.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(EquipU04a, -0.2617993877991494F, 0.0F, 0.20943951023931953F);
        this.EquipLegL02a = new ModelRenderer(this, 0, 84);
        this.EquipLegL02a.setRotationPoint(3.4F, -0.9F, -0.9F);
        this.EquipLegL02a.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(EquipLegL02a, 0.0F, 0.0F, -0.05235987755982988F);
        this.EquipR03 = new ModelRenderer(this, 128, 29);
        this.EquipR03.setRotationPoint(-10.5F, -2.5F, -1.0F);
        this.EquipR03.addBox(-6.0F, 0.0F, -14.0F, 6, 22, 17, 0.0F);
        this.setRotateAngle(EquipR03, 0.0F, 0.6981317007977318F, 0.0F);
        this.EquipU03a = new ModelRenderer(this, 128, 0);
        this.EquipU03a.setRotationPoint(0.0F, 0.1F, -0.3F);
        this.EquipU03a.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(EquipU03a, 0.10471975511965977F, 0.0F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipRCRadar01 = new ModelRenderer(this, 58, 0);
        this.EquipRCRadar01.setRotationPoint(8.3F, -7.0F, 5.0F);
        this.EquipRCRadar01.addBox(0.0F, 0.0F, 0.0F, 5, 4, 6, 0.0F);
        this.EquipLC02b = new ModelRenderer(this, 204, 39);
        this.EquipLC02b.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC02b.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.Hair04 = new ModelRenderer(this, 209, 108);
        this.Hair04.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.Hair04.addBox(-3.0F, 0.0F, -3.2F, 6, 15, 5, 0.0F);
        this.setRotateAngle(Hair04, -0.3490658503988659F, 0.0F, 0.27314402793711257F);
        this.EquipLegR02c = new ModelRenderer(this, 0, 84);
        this.EquipLegR02c.setRotationPoint(-3.2F, -0.7F, 0.7F);
        this.EquipLegR02c.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(EquipLegR02c, 0.0F, 0.0F, 0.05235987755982988F);
        this.EquipLCBase02 = new ModelRenderer(this, 128, 0);
        this.EquipLCBase02.setRotationPoint(0.5F, -4.5F, -2.0F);
        this.EquipLCBase02.addBox(-8.5F, -8.0F, -7.0F, 17, 8, 21, 0.0F);
        this.setRotateAngle(EquipLCBase02, -0.05235987755982988F, 0.0F, 0.0F);
        this.EquipL02 = new ModelRenderer(this, 128, 0);
        this.EquipL02.setRotationPoint(13.5F, -0.5F, 0.6F);
        this.EquipL02.addBox(0.0F, 0.0F, 0.0F, 13, 10, 5, 0.0F);
        this.setRotateAngle(EquipL02, 0.0F, 0.5235987755982988F, 0.0F);
        this.EquipLC201b = new ModelRenderer(this, 163, 30);
        this.EquipLC201b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipLC201b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.EquipRC202a = new ModelRenderer(this, 128, 122);
        this.EquipRC202a.setRotationPoint(0.0F, -3.0F, -6.0F);
        this.EquipRC202a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipRC202a, -0.13962634015954636F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-3.7F, -8.5F, -3.5F);
        this.BoobR.addBox(-3.5F, 0.0F, 0.0F, 7, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, -0.13962634015954636F, -0.08726646259971647F);
        this.EquipRC202b = new ModelRenderer(this, 163, 30);
        this.EquipRC202b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipRC202b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.EquipU04d = new ModelRenderer(this, 128, 0);
        this.EquipU04d.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.EquipU04d.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.EquipRC203b = new ModelRenderer(this, 163, 30);
        this.EquipRC203b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipRC203b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.HeadEquip01a = new ModelRenderer(this, 128, 0);
        this.HeadEquip01a.setRotationPoint(8.0F, 0.2F, 5.0F);
        this.HeadEquip01a.addBox(0.0F, 0.0F, -2.0F, 2, 3, 4, 0.0F);
        this.Skirt02 = new ModelRenderer(this, 42, 51);
        this.Skirt02.setRotationPoint(0.0F, 2.9F, -0.4F);
        this.Skirt02.addBox(-9.0F, 0.0F, -6.0F, 18, 4, 10, 0.0F);
        this.setRotateAngle(Skirt02, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipU07 = new ModelRenderer(this, 214, 66);
        this.EquipU07.setRotationPoint(-4.5F, 0.0F, -4.5F);
        this.EquipU07.addBox(0.0F, -1.0F, 0.0F, 9, 1, 9, 0.0F);
        this.EquipLC01b = new ModelRenderer(this, 204, 39);
        this.EquipLC01b.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC01b.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.EquipRC02a = new ModelRenderer(this, 128, 118);
        this.EquipRC02a.setRotationPoint(0.0F, -4.5F, -6.0F);
        this.EquipRC02a.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(EquipRC02a, -0.08726646259971647F, 0.0F, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 83);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.setRotateAngle(Head, -0.10471975511965977F, 0.0F, 0.0F);
        this.EquipRC03a = new ModelRenderer(this, 128, 118);
        this.EquipRC03a.setRotationPoint(5.0F, -4.5F, -6.0F);
        this.EquipRC03a.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(EquipRC03a, -0.3490658503988659F, 0.0F, 0.0F);
        this.EquipU05a = new ModelRenderer(this, 128, 0);
        this.EquipU05a.setRotationPoint(-0.2F, 0.1F, 0.3F);
        this.EquipU05a.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(EquipU05a, -0.2617993877991494F, 0.0F, -0.20943951023931953F);
        this.EquipBaseM01b = new ModelRenderer(this, 128, 0);
        this.EquipBaseM01b.setRotationPoint(0.0F, 0.0F, -1.0F);
        this.EquipBaseM01b.addBox(-7.5F, 0.0F, -1.0F, 5, 4, 9, 0.0F);
        this.setRotateAngle(EquipBaseM01b, 0.8726646259971648F, 0.0F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 201, 83);
        this.LegRight02.setRotationPoint(0.0F, 14.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 15, 6, 0.0F);
        this.EquipLC2Base02 = new ModelRenderer(this, 128, 0);
        this.EquipLC2Base02.setRotationPoint(0.0F, 0.5F, 4.0F);
        this.EquipLC2Base02.addBox(-4.5F, -5.0F, -5.5F, 9, 5, 10, 0.0F);
        this.setRotateAngle(EquipLC2Base02, -0.05235987755982988F, -0.27314402793711257F, 0.0F);
        this.EquipLC03b = new ModelRenderer(this, 204, 39);
        this.EquipLC03b.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC03b.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.Cloth01 = new ModelRenderer(this, 0, 0);
        this.Cloth01.setRotationPoint(0.0F, -11.3F, -0.3F);
        this.Cloth01.addBox(-6.0F, 0.0F, -4.0F, 12, 4, 8, 0.0F);
        this.EquipBaseBelt = new ModelRenderer(this, 66, 0);
        this.EquipBaseBelt.setRotationPoint(0.0F, 2.0F, -2.5F);
        this.EquipBaseBelt.addBox(-8.0F, 0.7F, -2.0F, 16, 4, 14, 0.0F);
        this.setRotateAngle(EquipBaseBelt, 0.10471975511965977F, 0.0F, 0.0F);
        this.EquipU05c = new ModelRenderer(this, 128, 0);
        this.EquipU05c.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.EquipU05c.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.EquipLC02a_1 = new ModelRenderer(this, 128, 118);
        this.EquipLC02a_1.setRotationPoint(0.0F, -4.5F, -9.0F);
        this.EquipLC02a_1.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(EquipLC02a_1, -0.2617993877991494F, 0.0F, 0.0F);
        this.HeadEquip02b = new ModelRenderer(this, 128, 0);
        this.HeadEquip02b.setRotationPoint(-2.0F, 1.5F, 0.5F);
        this.HeadEquip02b.addBox(-4.0F, -1.0F, -1.0F, 4, 1, 2, 0.0F);
        this.EquipR02 = new ModelRenderer(this, 128, 0);
        this.EquipR02.setRotationPoint(-13.5F, -0.5F, 0.6F);
        this.EquipR02.addBox(-13.0F, 0.0F, 0.0F, 13, 10, 5, 0.0F);
        this.setRotateAngle(EquipR02, 0.0F, -0.5235987755982988F, 0.0F);
        this.EquipRCBase01 = new ModelRenderer(this, 196, 16);
        this.EquipRCBase01.setRotationPoint(-3.0F, 3.0F, -5.5F);
        this.EquipRCBase01.addBox(-8.5F, -5.0F, -7.0F, 16, 9, 14, 0.0F);
        this.EquipR04 = new ModelRenderer(this, 128, 70);
        this.EquipR04.setRotationPoint(0.0F, 11.0F, -12.7F);
        this.EquipR04.addBox(-6.0F, 0.0F, -13.0F, 6, 11, 13, 0.0F);
        this.setRotateAngle(EquipR04, 0.0F, -0.20943951023931953F, 0.0F);
        this.EquipRC2Base01 = new ModelRenderer(this, 211, 23);
        this.EquipRC2Base01.setRotationPoint(-2.5F, -4.0F, -10.5F);
        this.EquipRC2Base01.addBox(-4.0F, 0.0F, 0.0F, 8, 6, 7, 0.0F);
        this.EquipU05d = new ModelRenderer(this, 128, 0);
        this.EquipU05d.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.EquipU05d.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.EquipLC03a = new ModelRenderer(this, 128, 118);
        this.EquipLC03a.setRotationPoint(5.0F, -4.5F, -6.0F);
        this.EquipLC03a.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(EquipLC03a, -0.13962634015954636F, 0.0F, 0.0F);
        this.Hair03 = new ModelRenderer(this, 170, 81);
        this.Hair03.setRotationPoint(0.0F, 15.0F, 1F);
        this.Hair03.addBox(-3.5F, 0.0F, -4.0F, 7, 16, 6, 0.0F);
        this.setRotateAngle(Hair03, 0.2617993877991494F, 0.0F, 0.36425021489121656F);
        this.HeadEquip02b2 = new ModelRenderer(this, 128, 0);
        this.HeadEquip02b2.setRotationPoint(-4.0F, 0.0F, -2.0F);
        this.HeadEquip02b2.addBox(-1.5F, -1.5F, 0.0F, 3, 2, 3, 0.0F);
        this.EquipU01 = new ModelRenderer(this, 128, 0);
        this.EquipU01.setRotationPoint(1.5F, 13.0F, -5F);
        this.EquipU01.addBox(0.0F, -4.0F, 0.0F, 1, 7, 1, 0.0F);
        this.setRotateAngle(EquipU01, -1.7453F, 2.4086F, -1.9199F);
        this.EquipLC302a = new ModelRenderer(this, 128, 122);
        this.EquipLC302a.setRotationPoint(0.0F, -3.0F, -6.0F);
        this.EquipLC302a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipLC302a, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipLegR02b = new ModelRenderer(this, 0, 84);
        this.EquipLegR02b.setRotationPoint(-3.4F, -0.8F, -0.9F);
        this.EquipLegR02b.addBox(-1.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(EquipLegR02b, 0.0F, 0.0F, 0.05235987755982988F);
        this.EquipHeadBase = new ModelRenderer(this, 128, 0);
        this.EquipHeadBase.setRotationPoint(0.0F, -9.5F, 0.0F);
        this.EquipHeadBase.addBox(-8.0F, 0.0F, 0.0F, 16, 4, 9, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 0, 29);
        this.ArmRight01.setRotationPoint(-7.8F, -9.7F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 14, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.2617993877991494F, 0.0F, 0.20943951023931953F);
        this.EquipRC303a = new ModelRenderer(this, 128, 122);
        this.EquipRC303a.setRotationPoint(2.6F, -3.0F, -6.0F);
        this.EquipRC303a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipRC303a, -0.13962634015954636F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 0, 16);
        this.Neck.setRotationPoint(0.0F, -10.7F, -0.2F);
        this.Neck.addBox(-4.5F, -2.0F, -5.0F, 9, 3, 9, 0.0F);
        this.setRotateAngle(Neck, 0.20943951023931953F, 0.0F, 0.0F);
        this.ShoesL01 = new ModelRenderer(this, 18, 80);
        this.ShoesL01.mirror = true;
        this.ShoesL01.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.ShoesL01.addBox(-3.5F, 0.0F, -0.5F, 7, 2, 7, 0.0F);
        this.HairBase = new ModelRenderer(this, 102, 35);
        this.HairBase.setRotationPoint(0.0F, -0.5F, 5.5F);
        this.HairBase.addBox(-5.0F, 0.0F, -0.7F, 10, 3, 3, 0.0F);
        this.setRotateAngle(HairBase, 0.8726646259971648F, 0.0F, 0.0F);
        this.EquipLC203b = new ModelRenderer(this, 163, 30);
        this.EquipLC203b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipLC203b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.EquipU09c = new ModelRenderer(this, 128, 0);
        this.EquipU09c.setRotationPoint(0.0F, -24.0F, -3.0F);
        this.EquipU09c.addBox(-0.6F, 0.0F, 0.0F, 1, 1, 11, 0.0F);
        this.setRotateAngle(EquipU09c, 0.0F, -0.5061454830783556F, -0.2617993877991494F);
        this.EquipRC02b = new ModelRenderer(this, 204, 39);
        this.EquipRC02b.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipRC02b.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.AnchorL = new ModelRenderer(this, 24, 90);
        this.AnchorL.mirror = true;
        this.AnchorL.setRotationPoint(7.7F, 2.0F, -2.0F);
        this.AnchorL.addBox(0.0F, 0.0F, -3.0F, 1, 7, 6, 0.0F);
        this.setRotateAngle(AnchorL, 0.0F, 0.0F, -0.3490658503988659F);
        this.EquipRC3Radar01 = new ModelRenderer(this, 128, 38);
        this.EquipRC3Radar01.setRotationPoint(4.4F, -4.0F, -1.0F);
        this.EquipRC3Radar01.addBox(0.0F, 0.0F, 0.0F, 2, 2, 4, 0.0F);
        this.EquipU03b = new ModelRenderer(this, 128, 0);
        this.EquipU03b.setRotationPoint(-0.5F, -16.0F, -0.5F);
        this.EquipU03b.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.EquipL04 = new ModelRenderer(this, 128, 70);
        this.EquipL04.setRotationPoint(0.0F, 11.0F, -12.7F);
        this.EquipL04.addBox(0.0F, 0.0F, -13.0F, 6, 11, 13, 0.0F);
        this.setRotateAngle(EquipL04, 0.0F, 0.20943951023931953F, 0.0F);
        this.HeadEquip01b2 = new ModelRenderer(this, 128, 0);
        this.HeadEquip01b2.setRotationPoint(4.0F, 0.0F, -2.0F);
        this.HeadEquip01b2.addBox(-1.5F, -1.5F, 0.0F, 3, 2, 3, 0.0F);
        this.EquipU04c = new ModelRenderer(this, 128, 0);
        this.EquipU04c.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.EquipU04c.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.AnchorR = new ModelRenderer(this, 24, 90);
        this.AnchorR.setRotationPoint(-7.7F, 2.0F, -2.0F);
        this.AnchorR.addBox(-1.0F, 0.0F, -3.0F, 1, 7, 6, 0.0F);
        this.setRotateAngle(AnchorR, 0.0F, 0.0F, 0.3490658503988659F);
        this.EquipLegL02b = new ModelRenderer(this, 0, 84);
        this.EquipLegL02b.setRotationPoint(3.2F, -0.7F, 0.7F);
        this.EquipLegL02b.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.setRotateAngle(EquipLegL02b, 0.0F, 0.0F, -0.05235987755982988F);
        this.ArmLeft01a = new ModelRenderer(this, 25, 69);
        this.ArmLeft01a.mirror = true;
        this.ArmLeft01a.setRotationPoint(0.5F, 5.5F, 0.0F);
        this.ArmLeft01a.addBox(-3.0F, 0.0F, -3.0F, 6, 4, 6, 0.0F);
        this.Hair00 = new ModelRenderer(this, 170, 81);
        this.Hair00.setRotationPoint(0.0F, 0.2F, 2.5F);
        this.Hair00.addBox(-3.5F, 0.0F, -4.0F, 7, 7, 6, 0.0F);
        this.EquipRC302b = new ModelRenderer(this, 163, 30);
        this.EquipRC302b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipRC302b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.HeadEquip01b = new ModelRenderer(this, 128, 0);
        this.HeadEquip01b.mirror = true;
        this.HeadEquip01b.setRotationPoint(2.0F, 1.5F, 0.5F);
        this.HeadEquip01b.addBox(0.0F, -1.0F, -1.0F, 4, 1, 2, 0.0F);
        this.EquipLC01a_1 = new ModelRenderer(this, 128, 118);
        this.EquipLC01a_1.setRotationPoint(-5.0F, -4.5F, -9.0F);
        this.EquipLC01a_1.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(EquipLC01a_1, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipLC301b = new ModelRenderer(this, 163, 30);
        this.EquipLC301b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipLC301b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.HairR01 = new ModelRenderer(this, 40, 89);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(-7.0F, 1.0F, -3.0F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.40142572795869574F, 0.17453292519943295F, -0.08726646259971647F);
        this.EquipLC201a = new ModelRenderer(this, 128, 122);
        this.EquipLC201a.setRotationPoint(-2.6F, -3.0F, -6.0F);
        this.EquipLC201a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipLC201a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipLC03b_1 = new ModelRenderer(this, 204, 39);
        this.EquipLC03b_1.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC03b_1.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.HairL02 = new ModelRenderer(this, 86, 101);
        this.HairL02.setRotationPoint(-0.2F, 8.0F, 0.3F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(HairL02, 0.22689280275926282F, 0.0F, -0.3141592653589793F);
        this.EquipL01 = new ModelRenderer(this, 128, 0);
        this.EquipL01.setRotationPoint(0.0F, 5.5F, 3.0F);
        this.EquipL01.addBox(0.0F, 0.0F, 0.0F, 16, 8, 5, 0.0F);
        this.setRotateAngle(EquipL01, -0.8726646259971648F, 0.0F, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 81);
        this.Hair.setRotationPoint(0.0F, -7.2F, 0.0F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 12, 8, 0.0F);
        this.HeadEquip02c = new ModelRenderer(this, 43, 82);
        this.HeadEquip02c.mirror = true;
        this.HeadEquip02c.setRotationPoint(-7.0F, -3.5F, 0.5F);
        this.HeadEquip02c.addBox(0.0F, 0.0F, 0.0F, 7, 4, 0, 0.0F);
        this.EquipRC01a = new ModelRenderer(this, 128, 118);
        this.EquipRC01a.setRotationPoint(-5.0F, -4.5F, -6.0F);
        this.EquipRC01a.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(EquipRC01a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipRC201a = new ModelRenderer(this, 128, 122);
        this.EquipRC201a.setRotationPoint(-2.6F, -3.0F, -6.0F);
        this.EquipRC201a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipRC201a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipMCBase01a = new ModelRenderer(this, 128, 0);
        this.EquipMCBase01a.setRotationPoint(8.0F, 8.0F, 0.0F);
        this.EquipMCBase01a.addBox(0.0F, 0.0F, 0.0F, 4, 10, 5, 0.0F);
        this.setRotateAngle(EquipMCBase01a, 1.0471975511965976F, 0.0F, 0.0F);
        this.EquipLC03a_1 = new ModelRenderer(this, 128, 118);
        this.EquipLC03a_1.setRotationPoint(5.0F, -4.5F, -9.0F);
        this.EquipLC03a_1.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(EquipLC03a_1, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipLC202b = new ModelRenderer(this, 163, 30);
        this.EquipLC202b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipLC202b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.EquipRC301b = new ModelRenderer(this, 163, 30);
        this.EquipRC301b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipRC301b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.EquipL03 = new ModelRenderer(this, 128, 29);
        this.EquipL03.setRotationPoint(10.5F, -2.5F, -1.0F);
        this.EquipL03.addBox(0.0F, 0.0F, -14.0F, 6, 22, 17, 0.0F);
        this.setRotateAngle(EquipL03, 0.0F, -0.6981317007977318F, 0.0F);
        this.EquipBaseM03 = new ModelRenderer(this, 128, 95);
        this.EquipBaseM03.setRotationPoint(0.0F, 6.0F, -2.5F);
        this.EquipBaseM03.addBox(-3.5F, -15.0F, 0.0F, 7, 15, 7, 0.0F);
        this.setRotateAngle(EquipBaseM03, -0.6981317007977318F, 0.0F, 0.0F);
        this.HairMain = new ModelRenderer(this, 159, 107);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.EquipU03c = new ModelRenderer(this, 128, 0);
        this.EquipU03c.setRotationPoint(0.0F, -8.0F, 0.0F);
        this.EquipU03c.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.HairU01 = new ModelRenderer(this, 56, 23);
        this.HairU01.setRotationPoint(0.0F, -8.8F, -5.7F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 6, 0.0F);
        this.EquipLC01b_1 = new ModelRenderer(this, 204, 39);
        this.EquipLC01b_1.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.EquipLC01b_1.addBox(-1.5F, -1.5F, -17.0F, 3, 3, 17, 0.0F);
        this.EquipMCRadar01 = new ModelRenderer(this, 58, 0);
        this.EquipMCRadar01.setRotationPoint(8.3F, -7.0F, 2.0F);
        this.EquipMCRadar01.addBox(0.0F, 0.0F, 0.0F, 5, 4, 6, 0.0F);
        this.Hair02 = new ModelRenderer(this, 169, 80);
        this.Hair02.mirror = true;
        this.Hair02.setRotationPoint(0.0F, 16.5F, 5.0F);
        this.Hair02.addBox(-3.5F, 0.0F, -3.5F, 7, 18, 7, 0.0F);
        this.setRotateAngle(Hair02, -0.3490658503988659F, 0.0F, -0.27314402793711257F);
        this.EquipLC01a = new ModelRenderer(this, 128, 118);
        this.EquipLC01a.setRotationPoint(-5.0F, -4.5F, -6.0F);
        this.EquipLC01a.addBox(-2.0F, -2.0F, -5.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(EquipLC01a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipRC301a = new ModelRenderer(this, 128, 122);
        this.EquipRC301a.setRotationPoint(-2.6F, -3.0F, -6.0F);
        this.EquipRC301a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipRC301a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipU08 = new ModelRenderer(this, 214, 61);
        this.EquipU08.setRotationPoint(2.5F, 0.0F, 2.5F);
        this.EquipU08.addBox(0.0F, -2.0F, 0.0F, 4, 1, 4, 0.0F);
        this.EquipLC202a = new ModelRenderer(this, 128, 122);
        this.EquipLC202a.setRotationPoint(0.0F, -3.0F, -6.0F);
        this.EquipLC202a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipLC202a, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipRC2Base02 = new ModelRenderer(this, 128, 0);
        this.EquipRC2Base02.setRotationPoint(0.0F, 0.5F, 4.0F);
        this.EquipRC2Base02.addBox(-4.5F, -5.0F, -5.5F, 9, 5, 10, 0.0F);
        this.setRotateAngle(EquipRC2Base02, -0.05235987755982988F, 0.0F, 0.0F);
        this.Cloth02b = new ModelRenderer(this, 24, 66);
        this.Cloth02b.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.Cloth02b.addBox(-1.0F, 0.0F, 0.0F, 2, 3, 0, 0.0F);
        this.setRotateAngle(Cloth02b, 0.9424777960769379F, 0.0F, 0.0F);
        this.EquipLC303b = new ModelRenderer(this, 163, 30);
        this.EquipLC303b.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.EquipLC303b.addBox(-0.5F, -0.5F, -9.0F, 1, 1, 9, 0.0F);
        this.EquipU01b = new ModelRenderer(this, 128, 0);
        this.EquipU01b.setRotationPoint(0.0F, 3.0F, 0.0F);
        this.EquipU01b.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
        this.EquipLCBase02_1 = new ModelRenderer(this, 128, 0);
        this.EquipLCBase02_1.setRotationPoint(0.5F, -4.5F, 0.0F);
        this.EquipLCBase02_1.addBox(-8.5F, -8.0F, -10.0F, 17, 8, 21, 0.0F);
        this.setRotateAngle(EquipLCBase02_1, -0.05235987755982988F, 3.141592653589793F, 0.0F);
        this.Butt = new ModelRenderer(this, 52, 65);
        this.Butt.setRotationPoint(0.0F, 4.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 8, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3141592653589793F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 20, 29);
        this.ArmRight02.setRotationPoint(-3.0F, 13.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 14, 5, 0.0F);
        this.setRotateAngle(ArmRight02, -1.48352986419518F, 0.0F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 40, 89);
        this.HairL01.setRotationPoint(7.0F, 1.0F, -3.0F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 9, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.3665191429188092F, -0.17453292519943295F, 0.08726646259971647F);
        this.EquipLC301a = new ModelRenderer(this, 128, 122);
        this.EquipLC301a.setRotationPoint(-2.6F, -3.0F, -6.0F);
        this.EquipLC301a.addBox(-1.0F, -1.0F, -2.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(EquipLC301a, -0.17453292519943295F, 0.0F, 0.0F);
        this.EquipBaseM02 = new ModelRenderer(this, 128, 0);
        this.EquipBaseM02.setRotationPoint(0.0F, -1.3F, 7.7F);
        this.EquipBaseM02.addBox(-9.0F, 0.0F, 0.0F, 18, 10, 4, 0.0F);
        this.setRotateAngle(EquipBaseM02, -0.5918411493512771F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0.0F, -9.0F, -5.5F);
        this.Ahoke.addBox(0.0F, -4.0F, -11.5F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.17453292519943295F, 0.6981317007977318F, 0.0F);
        this.HairR03 = new ModelRenderer(this, 86, 101);
        this.HairR03.mirror = true;
        this.HairR03.setRotationPoint(0.1F, 9.0F, 0.1F);
        this.HairR03.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(HairR03, 0.13962634015954636F, 0.0F, -0.22689280275926282F);
        this.EquipR05 = new ModelRenderer(this, 174, 36);
        this.EquipR05.setRotationPoint(-6.0F, -2.5F, -13.0F);
        this.EquipR05.addBox(0.0F, 0.0F, -10.0F, 5, 13, 10, 0.0F);
        this.setRotateAngle(EquipR05, 0.0F, -0.7853981633974483F, 0.0F);
        this.EquipL04.addChild(this.EquipLC2Base01);
        this.EquipLCBase02.addChild(this.EquipLC02a);
        this.EquipR04.addChild(this.EquipRC3Base01);
        this.Butt.addChild(this.LegLeft01);
        this.Cloth01.addChild(this.Cloth02a);
        this.EquipRC2Base02.addChild(this.EquipRC2Radar02);
        this.EquipRCBase02.addChild(this.EquipRCRadar02);
        this.Butt.addChild(this.Skirt01);
        this.LegRight01.addChild(this.EquipLegR01);
        this.Butt.addChild(this.LegRight01);
        this.EquipBaseBelt.addChild(this.EquipRotateBase);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.HeadEquip01a.addChild(this.HeadEquip01d);
        this.EquipLC3Base02.addChild(this.EquipLC3Radar02);
        this.EquipRC3Base02.addChild(this.EquipRC302a);
        this.EquipLC302a.addChild(this.EquipLC302b);
        this.EquipRC3Base02.addChild(this.EquipRC3Radar02);
        this.EquipU03c.addChild(this.EquipU03d);
        this.EquipRCBase01.addChild(this.EquipRCBase02);
        this.EquipRC2Base02.addChild(this.EquipRC2Radar01);
        this.EquipLCBase02.addChild(this.EquipLCRadar02);
        this.BodyMain.addChild(this.BoobL);
        this.EquipU01.addChild(this.EquipU01a);
        this.EquipL03.addChild(this.EquipLCBase01);
        this.EquipLCBase02.addChild(this.EquipLCRadar01);
        this.EquipBaseM01b.addChild(this.EquipR01);
        this.EquipLC3Base01.addChild(this.EquipLC3Base02);
        this.EquipU02.addChild(this.EquipU09a);
        this.EquipLegR01.addChild(this.EquipLegR02a);
        this.EquipRC01a.addChild(this.EquipRC01b);
        this.EquipRC03a.addChild(this.EquipRC03b);
        this.LegLeft01.addChild(this.EquipLegL01);
        this.EquipRC3Base01.addChild(this.EquipRC3Base02);
        this.EquipU01.addChild(this.EquipU02);
        this.EquipBaseBelt.addChild(this.EquipBaseBelt2);
        this.EquipRotateBase.addChild(this.EquipBaseM01a);
        this.EquipRC303a.addChild(this.EquipRC303b);
        this.HairL02.addChild(this.HairL03);
        this.EquipR01.addChild(this.EquipMCBase01b);
        this.EquipLegL01.addChild(this.EquipLegL02c);
        this.EquipU04a.addChild(this.EquipU04b);
        this.HeadEquip02a.addChild(this.HeadEquip02d);
        this.LegRight02.addChild(this.ShoesR01);
        this.Hair00.addChild(this.Hair01);
        this.EquipLC3Base02.addChild(this.EquipLC3Radar01);
        this.EquipU02.addChild(this.EquipU06);
        this.EquipRC201a.addChild(this.EquipRC201b);
        this.EquipU05a.addChild(this.EquipU05b);
        this.EquipRC2Base02.addChild(this.EquipRC203a);
        this.EquipMCBase01a.addChild(this.EquipLCBase01_1);
        this.EquipL04.addChild(this.EquipLC3Base01);
        this.EquipLC3Base02.addChild(this.EquipLC303a);
        this.EquipU02.addChild(this.EquipU09b);
        this.EquipLC2Base02.addChild(this.EquipLC203a);
        this.BodyMain.addChild(this.ArmLeft01);
        this.EquipHeadBase.addChild(this.HeadEquip02a);
        this.HeadEquip01a.addChild(this.HeadEquip01c);
        this.EquipL04.addChild(this.EquipL05);
        this.EquipLC2Base02.addChild(this.EquipLC2Radar02);
        this.EquipLCBase02_1.addChild(this.EquipMCRadar02);
        this.EquipLC2Base02.addChild(this.EquipLC2Radar01);
        this.EquipLC02a_1.addChild(this.EquipLC02b_1);
        this.HairR01.addChild(this.HairR02);
        this.EquipU02.addChild(this.EquipU04a);
        this.EquipLegL01.addChild(this.EquipLegL02a);
        this.EquipR02.addChild(this.EquipR03);
        this.EquipU02.addChild(this.EquipU03a);
        this.EquipRCBase02.addChild(this.EquipRCRadar01);
        this.EquipLC02a.addChild(this.EquipLC02b);
        this.Hair03.addChild(this.Hair04);
        this.EquipLegR01.addChild(this.EquipLegR02c);
        this.EquipLCBase01.addChild(this.EquipLCBase02);
        this.EquipL01.addChild(this.EquipL02);
        this.EquipLC201a.addChild(this.EquipLC201b);
        this.EquipRC2Base02.addChild(this.EquipRC202a);
        this.BodyMain.addChild(this.BoobR);
        this.EquipRC202a.addChild(this.EquipRC202b);
        this.EquipU04c.addChild(this.EquipU04d);
        this.EquipRC203a.addChild(this.EquipRC203b);
        this.EquipHeadBase.addChild(this.HeadEquip01a);
        this.Skirt01.addChild(this.Skirt02);
        this.EquipU06.addChild(this.EquipU07);
        this.EquipLC01a.addChild(this.EquipLC01b);
        this.EquipRCBase02.addChild(this.EquipRC02a);
        this.LegLeft01.addChild(this.LegLeft02);
        this.Neck.addChild(this.Head);
        this.EquipRCBase02.addChild(this.EquipRC03a);
        this.EquipU02.addChild(this.EquipU05a);
        this.EquipRotateBase.addChild(this.EquipBaseM01b);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipLC2Base01.addChild(this.EquipLC2Base02);
        this.EquipLC03a.addChild(this.EquipLC03b);
        this.BodyMain.addChild(this.Cloth01);
        this.BodyMain.addChild(this.EquipBaseBelt);
        this.EquipU05b.addChild(this.EquipU05c);
        this.EquipLCBase02_1.addChild(this.EquipLC02a_1);
        this.HeadEquip02a.addChild(this.HeadEquip02b);
        this.EquipR01.addChild(this.EquipR02);
        this.EquipR03.addChild(this.EquipRCBase01);
        this.EquipR03.addChild(this.EquipR04);
        this.EquipR04.addChild(this.EquipRC2Base01);
        this.EquipU05c.addChild(this.EquipU05d);
        this.EquipLCBase02.addChild(this.EquipLC03a);
        this.Hair02.addChild(this.Hair03);
        this.HeadEquip02b.addChild(this.HeadEquip02b2);
        this.ArmRight02.addChild(this.EquipU01);
        this.EquipLC3Base02.addChild(this.EquipLC302a);
        this.EquipLegR01.addChild(this.EquipLegR02b);
        this.Head.addChild(this.EquipHeadBase);
        this.BodyMain.addChild(this.ArmRight01);
        this.EquipRC3Base02.addChild(this.EquipRC303a);
        this.BodyMain.addChild(this.Neck);
        this.LegLeft02.addChild(this.ShoesL01);
        this.HairMain.addChild(this.HairBase);
        this.EquipLC203a.addChild(this.EquipLC203b);
        this.EquipU02.addChild(this.EquipU09c);
        this.EquipRC02a.addChild(this.EquipRC02b);
        this.Butt.addChild(this.AnchorL);
        this.EquipRC3Base02.addChild(this.EquipRC3Radar01);
        this.EquipU03a.addChild(this.EquipU03b);
        this.EquipL03.addChild(this.EquipL04);
        this.HeadEquip01b.addChild(this.HeadEquip01b2);
        this.EquipU04b.addChild(this.EquipU04c);
        this.Butt.addChild(this.AnchorR);
        this.EquipLegL01.addChild(this.EquipLegL02b);
        this.ArmLeft01.addChild(this.ArmLeft01a);
        this.HairBase.addChild(this.Hair00);
        this.EquipRC302a.addChild(this.EquipRC302b);
        this.HeadEquip01a.addChild(this.HeadEquip01b);
        this.EquipLCBase02_1.addChild(this.EquipLC01a_1);
        
        this.EquipLC301a.addChild(this.EquipLC301b);
        this.Hair.addChild(this.HairR01);
        this.EquipLC2Base02.addChild(this.EquipLC201a);
        this.EquipLC03a_1.addChild(this.EquipLC03b_1);
        this.HairL01.addChild(this.HairL02);
        this.EquipBaseM01b.addChild(this.EquipL01);
        this.Head.addChild(this.Hair);
        this.HeadEquip02a.addChild(this.HeadEquip02c);
        this.EquipRCBase02.addChild(this.EquipRC01a);
        this.EquipRC2Base02.addChild(this.EquipRC201a);
        this.EquipR01.addChild(this.EquipMCBase01a);
        this.EquipLCBase02_1.addChild(this.EquipLC03a_1);
        this.EquipLC202a.addChild(this.EquipLC202b);
        this.EquipRC301a.addChild(this.EquipRC301b);
        this.EquipL02.addChild(this.EquipL03);
        this.EquipBaseM02.addChild(this.EquipBaseM03);
        this.Head.addChild(this.HairMain);
        this.EquipU03b.addChild(this.EquipU03c);
        this.Hair.addChild(this.HairU01);
        this.EquipLC01a_1.addChild(this.EquipLC01b_1);
        this.EquipLCBase02_1.addChild(this.EquipMCRadar01);
        this.Hair01.addChild(this.Hair02);
        this.EquipLCBase02.addChild(this.EquipLC01a);
        this.EquipRC3Base02.addChild(this.EquipRC301a);
        this.EquipU07.addChild(this.EquipU08);
        this.EquipLC2Base02.addChild(this.EquipLC202a);
        this.EquipRC2Base01.addChild(this.EquipRC2Base02);
        this.Cloth02a.addChild(this.Cloth02b);
        this.EquipLC303a.addChild(this.EquipLC303b);
        this.EquipU01.addChild(this.EquipU01b);
        this.EquipLCBase01_1.addChild(this.EquipLCBase02_1);
        this.BodyMain.addChild(this.Butt);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Hair.addChild(this.HairL01);
        this.EquipLC3Base02.addChild(this.EquipLC301a);
        this.EquipBaseM01b.addChild(this.EquipBaseM02);
        this.Hair.addChild(this.Ahoke);
        this.HairR02.addChild(this.HairR03);
        this.EquipR04.addChild(this.EquipR05);
        
        
        //glow cube
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -15.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.7F, -0.2F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.0F, 0.0F);
        
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
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
        
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
    	
    	switch (((IShipEmotion)entity).getScaleLevel())
    	{
    	case 3:
    		scale = 2F;
        	offsetY = -0.73F;
		break;
    	case 2:
    		scale = 1.5F;
        	offsetY = -0.48F;
		break;
    	case 1:
    		scale = 1F;
        	offsetY = 0.02F;
		break;
    	default:
    		scale = 0.5F;
        	offsetY = 1.53F;
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
    	GlStateManager.disableBlend();
    	
    	//light part
    	GlStateManager.disableLighting();
    	GlStateManager.enableCull();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GlStateManager.disableCull();
    	GlStateManager.enableLighting();
    	
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
    	//頭部
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
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
    {
    	GlStateManager.translate(0F, 0.58F, 0F);
    	setFace(4);
    
    	//頭部
	  	this.Head.rotateAngleX = -0.2618F;
	  	this.Head.rotateAngleY = 0F;
	  	this.Head.rotateAngleZ = 0F;
	    //胸部
  	    this.BoobL.rotateAngleX = -1.0F;
  	    this.BoobR.rotateAngleX = -1.0F;
  	    this.Cloth02a.rotateAngleX = -1.0F;
	  	//Body
  	    this.Ahoke.rotateAngleY = -1.0F;
	  	this.BodyMain.rotateAngleX = 1.2217F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 1.2217F;
	  	this.Butt.rotateAngleX = -0.05F;
	  	//hair
	  	this.Hair01.rotateAngleX = -0.72F;
	  	this.Hair01.rotateAngleZ = -0.36F;
	  	this.Hair02.rotateAngleX = -0.35F;
	  	this.Hair02.rotateAngleZ = -0.15F;
	  	this.Hair03.rotateAngleX = 0.26F;
	  	this.Hair03.rotateAngleZ = 0.36F;
	  	this.Hair04.rotateAngleX = -0.35F;
	  	this.Hair04.rotateAngleZ = 0.1F;
	  	this.HairL01.rotateAngleZ = 0.0873F;
	  	this.HairL02.rotateAngleZ = -0.3142F;
	  	this.HairL03.rotateAngleZ = 0.18F;
	  	this.HairR01.rotateAngleZ = -0.0873F;
	  	this.HairR02.rotateAngleZ = -1.2217F;
	  	this.HairR03.rotateAngleZ = -0.15F;
		this.HairL01.rotateAngleX = - 0.28F;
	  	this.HairL02.rotateAngleX = 0.15F;
	  	this.HairL03.rotateAngleX = 0.05F;
	  	this.HairR01.rotateAngleX = -0.35F;
	  	this.HairR02.rotateAngleX = 0.18F;
	  	this.HairR03.rotateAngleX = 0.02F;
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
		this.AnchorL.rotateAngleX = -0.2F;
		this.AnchorR.rotateAngleX = -0.2F;
		this.AnchorR.rotateAngleZ = 0.35F;
		//equip
		this.EquipU01.isHidden = true;
		this.EquipBaseBelt.isHidden = true;
		
    }
    
	//雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
  	{   
  		float angleX = MathHelper.cos(f2*0.08F + f * 0.25F);
  		float angleX1 = MathHelper.cos(f2*0.08F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.08F + 0.6F + f * 0.5F);
  		float angleX3 = MathHelper.cos(f2*0.08F + 0.9F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
  		
    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.2793F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.5F - 0.1396F;  //LegRight01
	  	
  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F - 0.1047F; 	//上下角度
	  	this.Head.rotateAngleY = f3 * 0.01F;				//左右角度
	    
	    //正常站立動作
	    //胸部
  	    this.BoobL.rotateAngleX = angleX * 0.06F - 0.75F;
  	    this.BoobR.rotateAngleX = angleX * 0.06F - 0.75F;
  	    this.Cloth02a.rotateAngleX = angleX * 0.06F - 0.7F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.45F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.3142F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.03F - 0.7F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -angleX1 * 0.04F - 0.11F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -angleX2 * 0.07F - 0.05F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	this.Hair04.rotateAngleX = -angleX3 * 0.10F - 0.02F;
	  	this.Hair04.rotateAngleZ = 0F;
	  	this.HairL01.rotateAngleZ = 0.0873F;
	  	this.HairL02.rotateAngleZ = -0.3142F;
	  	this.HairL03.rotateAngleZ = 0.18F;
	  	this.HairR01.rotateAngleZ = -0.0873F;
	  	this.HairR02.rotateAngleZ = 0.25F;
	  	this.HairR03.rotateAngleZ = -0.15F;
		this.HairL01.rotateAngleX = - 0.28F;
	  	this.HairL02.rotateAngleX = 0.15F;
	  	this.HairL03.rotateAngleX = 0.05F;
	  	this.HairR01.rotateAngleX = -0.35F;
	  	this.HairR02.rotateAngleX = 0.18F;
	  	this.HairR03.rotateAngleX = 0.02F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F + 0.18F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.26F;
	    this.ArmLeft02.rotateAngleX = 0F;
	    //equipU
	    this.EquipU01.rotateAngleY = 2.4F;
	    
	    if (ent.getStateEmotion(ID.S.State2) > ID.State.EQUIP00_2)
	    {
	    	this.ArmRight01.rotateAngleX = -f1 * 0.4F + 0.1745F;
		    this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight01.rotateAngleZ = 0.1571F;
			this.ArmRight02.rotateAngleX = -1.4835F;
			this.ArmRight02.rotateAngleZ = 0F;
	    }
	    else
	    {
	    	this.ArmRight01.rotateAngleX = angleAdd1 * 0.25F + 0.18F;
		    this.ArmRight01.rotateAngleY = 0F;
			this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.26F;
			this.ArmRight02.rotateAngleX = 0F;
			this.ArmRight02.rotateAngleZ = 0F;
	    }
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.1396F;
		this.LegLeft02.rotateAngleX = 0F;
		this.LegLeft02.rotateAngleZ = 0F;
		this.LegLeft02.offsetZ = 0F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.1396F;
		this.LegRight02.rotateAngleX = 0F;
		this.LegRight02.rotateAngleZ = 0F;
		this.LegRight02.offsetZ = 0F;
		this.AnchorL.rotateAngleX = f1 * 0.5F - 0.2F;
		this.AnchorR.rotateAngleX = f1 * 0.5F - 0.2F;
		this.AnchorR.rotateAngleZ = 0.35F;
		//cannon
		if (ent.getStateEmotion(ID.S.State) > ID.State.EQUIP00)
		{
			this.EquipRotateBase.rotateAngleX = 0F;
			this.EquipLCBase02_1.rotateAngleY = 3.1415F;
			
			if (this.Head.rotateAngleX <= 0F)
			{
				this.EquipLC01a.rotateAngleX = this.Head.rotateAngleX * 0.7F;
				this.EquipLC02a.rotateAngleX = this.Head.rotateAngleX;
				this.EquipLC03a.rotateAngleX = this.Head.rotateAngleX * 0.8F;
				this.EquipLC201a.rotateAngleX = this.Head.rotateAngleX * 1.2F;
				this.EquipLC202a.rotateAngleX = this.Head.rotateAngleX;
				this.EquipLC203a.rotateAngleX = this.Head.rotateAngleX * 0.9F;
				
				this.EquipRC01a.rotateAngleX = this.Head.rotateAngleX * 0.9F;
				this.EquipRC02a.rotateAngleX = this.Head.rotateAngleX;
				this.EquipRC03a.rotateAngleX = this.Head.rotateAngleX * 0.75F;
				this.EquipRC201a.rotateAngleX = this.Head.rotateAngleX * 0.85F;
				this.EquipRC202a.rotateAngleX = this.Head.rotateAngleX * 1.1F;
				this.EquipRC203a.rotateAngleX = this.Head.rotateAngleX;
			}
			
			this.EquipLCBase02.rotateAngleY = this.Head.rotateAngleY * 1.3F;
			this.EquipLC2Base02.rotateAngleY = this.Head.rotateAngleY * 1.45F;
			this.EquipLC3Base02.rotateAngleY = -this.Head.rotateAngleX;
			
			this.EquipRCBase02.rotateAngleY = this.Head.rotateAngleY * 1.3F;
			this.EquipRC2Base02.rotateAngleY = this.Head.rotateAngleY * 1.45F;
			this.EquipRC3Base02.rotateAngleY = this.Head.rotateAngleX;
			
			//hair in equip mode
			this.Hair01.rotateAngleX = -0.7F;
		  	this.Hair01.rotateAngleZ = -0.35F;
		  	this.Hair02.rotateAngleX = -0.35F;
		  	this.Hair02.rotateAngleZ = -0.3142F;
		  	this.Hair03.rotateAngleX = 0.2618F;
		  	this.Hair03.rotateAngleZ = 0.4363F;
		  	this.Hair04.rotateAngleX = -0.3491F;
		  	this.Hair04.rotateAngleZ = 0.2618F;
		}


	    if (ent.getIsSprinting() || f1 > 0.1F)
	    {	//奔跑動作
	    	//hair
	    	this.Hair01.rotateAngleX += f1 * 0.25F;
		    //arm 
		    this.ArmLeft01.rotateAngleZ += f1 * -0.25F;
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {	//潛行, 蹲下動作
	    	GlStateManager.translate(0F, 0.07F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.8378F;
		    //arm 
		    this.ArmLeft01.rotateAngleX = -0.7F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
			if (ent.getStateEmotion(ID.S.State2) > ID.State.EQUIP00_2)
			{
				this.ArmRight01.rotateAngleX -= 1.0472F;
		    }
		    else
		    {
		    	this.ArmRight01.rotateAngleX = -0.7F;
			    this.ArmRight01.rotateAngleY = 0F;
				this.ArmRight01.rotateAngleZ = -0.2618F;
				this.ArmRight02.rotateAngleX = 0F;
		    }
			//hair
			this.Hair01.rotateAngleX = -1.2109F;
			this.Hair01.rotateAngleZ = -0.4363F;
			this.Hair02.rotateAngleX = -0.5236F;
			this.Hair02.rotateAngleZ = -0.3491F;
			this.Hair03.rotateAngleX = 0F;
			this.Hair03.rotateAngleZ = 0.4363F;
			this.Hair04.rotateAngleX = -0.3491F;
			this.Hair04.rotateAngleZ = 0.2618F;
			//cannon
			if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00)
			{
				this.EquipRotateBase.rotateAngleX -= 1.0472F;
			}
  		}//end if sneaking
  		
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {	//騎乘動作
	    	if (ent.getStateEmotion(ID.S.State) > ID.State.EQUIP00)
	    	{
	    		GlStateManager.translate(0F, 0.4F, 0F);
		    	//Body
			  	this.Head.rotateAngleX -= 0.2F;
		    	this.BodyMain.rotateAngleX = -0.1396F;
			  	this.Butt.rotateAngleX = 0.1396F;
				//arm 
			  	this.ArmLeft01.rotateAngleX = -0.2094F;
			  	this.ArmLeft01.rotateAngleZ = 0.2618F;
			  	if (ent.getStateEmotion(ID.S.State2) > ID.State.EQUIP00_2)
			  	{
			    	this.ArmRight01.rotateAngleX = 0.1745F;
				    this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = 0.1571F;
					this.ArmRight02.rotateAngleX = -1.4835F;
			    }
			    else
			    {
			    	this.ArmRight01.rotateAngleX = -0.2094F;
				    this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = -0.2618F;
					this.ArmRight02.rotateAngleX = 0F;
			    }
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
				this.EquipLCBase02_1.rotateAngleY = 0F;
			}
	    	else if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
	    		GlStateManager.translate(0F, 0.5F, 0F);
		    	//Body
			  	this.Head.rotateAngleX -= 0.21F;
			  	this.Head.rotateAngleY -= 0.4363F;
		    	this.BodyMain.rotateAngleX = 0.2618F;
		    	this.BodyMain.rotateAngleY = 0.35F;
		    	this.BodyMain.rotateAngleZ = 0.4363F;
		    	//hair
				this.Hair01.rotateAngleX = -0.95F;
				this.Hair01.rotateAngleZ = -0.2618F;
				this.Hair02.rotateAngleX = -0.3491F;
				this.Hair02.rotateAngleZ = -0.3491F;
				this.Hair03.rotateAngleX = -0.3491F;
				this.Hair03.rotateAngleZ = -0.3491F;
				this.Hair04.rotateAngleX = -0.4363F;
				this.Hair04.rotateAngleZ = -0.4363F;
				//arm 
			  	this.ArmLeft01.rotateAngleX = -0.35F;
			  	this.ArmLeft01.rotateAngleY = -0.5236F;
			  	this.ArmLeft01.rotateAngleZ = -0.2618F;
			  	this.ArmLeft02.rotateAngleX = -0.5236F;
			  	if (ent.getStateEmotion(ID.S.State2) > ID.State.EQUIP00_2)
			  	{
			    	this.ArmRight01.rotateAngleX = 0F;
				    this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = -0.0524F;
					this.ArmRight02.rotateAngleX = -1.0472F;
			    }
			    else
			    {
			    	this.ArmRight01.rotateAngleX = 0.0873F;
				    this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = 0.0873F;
					this.ArmRight02.rotateAngleX = -0.5236F;
			    }
			  	//leg
			  	addk1 = -0.0873F;
			  	addk2 = -0.4363F;
			  	this.LegLeft01.rotateAngleY = 0F;
				this.LegLeft01.rotateAngleZ = 1.0472F;
				this.LegLeft02.rotateAngleX = 0.4363F;
				this.LegRight01.rotateAngleY = 0F;
				this.LegRight01.rotateAngleZ = 0.9250F;
				this.LegRight02.rotateAngleX = 0.5236F;
				//equipU
			    this.EquipU01.rotateAngleY = 2.15F;
			    this.EquipU01.rotateAngleZ = -1.85F;
			    this.AnchorR.rotateAngleZ = 0.7F;
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, 0.54F, 0F);
		    	//Body
			  	this.Head.rotateAngleX += 0.1047F;
		    	this.BodyMain.rotateAngleX = -0.1396F;
			  	this.Butt.rotateAngleX = 0.1396F;
			  	//hair
				this.Hair01.rotateAngleX = -0.6108F;
				this.Hair01.rotateAngleZ = -0.2618F;
				this.Hair02.rotateAngleX = -0.4363F;
				this.Hair02.rotateAngleZ = 0.4363F;
				this.Hair03.rotateAngleX = -0.3491F;
				this.Hair03.rotateAngleZ = 0.4363F;
				this.Hair04.rotateAngleX = -0.5236F;
				this.Hair04.rotateAngleZ = 0.5236F;
				//arm 
			  	this.ArmLeft01.rotateAngleX = -0.2094F;
			  	this.ArmLeft01.rotateAngleZ = 0.2618F;
			  	if (ent.getStateEmotion(ID.S.State2) > ID.State.EQUIP00_2)
			  	{
			    	this.ArmRight01.rotateAngleX = 0.1745F;
				    this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = 0.1571F;
					this.ArmRight02.rotateAngleX = -1.4835F;
			    }
			    else
			    {
			    	this.ArmRight01.rotateAngleX = -0.2094F;
				    this.ArmRight01.rotateAngleY = 0F;
					this.ArmRight01.rotateAngleZ = -0.2618F;
					this.ArmRight02.rotateAngleX = 0F;
			    }
			  	//leg
			  	addk1 = -1.4835F;
			  	addk2 = -1.4835F;
			  	this.LegLeft01.rotateAngleY = 0.0524F;
				this.LegLeft01.rotateAngleZ = -1.4835F;
				this.LegLeft02.offsetZ = 0.38F;
				this.LegLeft02.rotateAngleX = 2.1F;
				this.LegLeft02.rotateAngleZ = 0.0175F;
				this.LegRight01.rotateAngleY = -0.0524F;
				this.LegRight01.rotateAngleZ = 1.4835F;
				this.LegRight02.offsetZ = 0.38F;
				this.LegRight02.rotateAngleX = 1.9199F;
				this.LegRight02.rotateAngleZ = -0.0175F;
	    	}
  		}//end if sitting
	    
	    //攻擊動作    
	    if (ent.getAttackTick() > 0)
	    {
	    	this.ArmLeft01.rotateAngleX = -1.5708F;
		    this.ArmLeft01.rotateAngleY = -0.2F + this.Head.rotateAngleY;
		  	this.ArmLeft01.rotateAngleZ = 0F;
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.2F;
	        this.ArmRight01.rotateAngleY = 0F;
	        this.ArmRight01.rotateAngleZ = -0.1F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	        this.ArmRight02.rotateAngleX = 0F;
	        this.ArmRight02.rotateAngleY = 0F;
	        this.ArmRight02.rotateAngleZ = 0F;
	  	}
	    
	    //鬢毛調整
	    float headZ = this.Head.rotateAngleZ * -0.5F;
	    float headX = this.Head.rotateAngleX * -0.5F - 0.05F;
	    this.Hair01.rotateAngleX += headX;
	  	this.Hair02.rotateAngleX += headX * 0.5F;
	  	this.Hair03.rotateAngleX += headX * 0.2F;
	  	this.Hair04.rotateAngleX += headX * 0.2F;
	    this.Hair01.rotateAngleZ += angleAdd1 * 0.04F + headZ * 1.0F;
	  	this.Hair02.rotateAngleZ += angleAdd2 * 0.06F + headZ * 0.8F;
	  	this.Hair03.rotateAngleZ += angleAdd2 * 0.08F + headZ * 0.4F;
	  	this.Hair04.rotateAngleZ += angleAdd2 * 0.10F + headZ * 0.4F;
	  	this.HairL01.rotateAngleZ += headZ;
	  	this.HairL02.rotateAngleZ += headZ * 0.8F;
	  	this.HairL03.rotateAngleZ += headZ * 0.4F;
	  	this.HairR01.rotateAngleZ += headZ;
	  	this.HairR02.rotateAngleZ += headZ * 0.8F;
	  	this.HairR03.rotateAngleZ += headZ * 0.4F;
		this.HairL01.rotateAngleX += angleX * 0.04F + headX;
	  	this.HairL02.rotateAngleX += angleX1 * 0.05F + headX * 0.8F;
	  	this.HairL03.rotateAngleX += angleX2 * 0.07F + headX * 0.4F;
	  	this.HairR01.rotateAngleX += angleX * 0.04F + headX;
	  	this.HairR02.rotateAngleX += angleX1 * 0.05F + headX * 0.8F;
	  	this.HairR03.rotateAngleX += angleX2 * 0.07F + headX * 0.4F;
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
  	
  	private void showEquip(IShipEmotion ent)
  	{
  		switch (ent.getStateEmotion(ID.S.State))
  		{
  		case ID.State.EQUIP00:
  			this.EquipBaseBelt.isHidden = true;
  			this.EquipHeadBase.isHidden = false;
  		break;
  		case ID.State.EQUIP01:
  			this.EquipBaseBelt.isHidden = false;
  			this.EquipHeadBase.isHidden = true;
  		break;
  		case ID.State.EQUIP02:
  			this.EquipBaseBelt.isHidden = false;
  			this.EquipHeadBase.isHidden = false;
  		break;
  		default:  //normal
  			this.EquipBaseBelt.isHidden = true;
  			this.EquipHeadBase.isHidden = true;
  		break;
  		}
  		
  		switch (ent.getStateEmotion(ID.S.State2))
  		{
  		case ID.State.EQUIP00_2:
  			this.EquipU01.isHidden = true;
  			this.EquipLegR01.isHidden = false;
  			this.EquipLegL01.isHidden = false;
  		break;
  		case ID.State.EQUIP01_2:
  			this.EquipU01.isHidden = false;
  			this.EquipLegR01.isHidden = true;
  			this.EquipLegL01.isHidden = true;
  		break;
  		case ID.State.EQUIP02_2:
  			this.EquipU01.isHidden = false;
  			this.EquipLegR01.isHidden = false;
  			this.EquipLegL01.isHidden = false;
  		break;
  		default:  //normal
  			this.EquipU01.isHidden = true;
  			this.EquipLegR01.isHidden = true;
  			this.EquipLegL01.isHidden = true;
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
  	
	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void setField(int id, float value)
	{
	}

	@Override
	public float getField(int id)
	{
		return 0;
	}
   
    
}