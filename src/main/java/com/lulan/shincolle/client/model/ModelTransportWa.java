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
 * ModelTransportWa - PinkaLulan  2016/4/18
 * Created using Tabula 4.1.1
 */
public class ModelTransportWa extends ModelBase implements IModelEmotion
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer BoobR;
    public ModelRenderer BoobL;
    public ModelRenderer Butt;
    public ModelRenderer Head;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Cloth03;
    public ModelRenderer EquipBase;
    public ModelRenderer Cloth01b;
    public ModelRenderer Cloth01a;
    public ModelRenderer Cloth2b;
    public ModelRenderer Cloth2a;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer LegLeft02;
    public ModelRenderer ClothLeg;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Face0;
    public ModelRenderer EquipHeadBase;
    public ModelRenderer Ahoke;
    public ModelRenderer HairU01;
    public ModelRenderer Hair01;
    public ModelRenderer ClothHead;
    public ModelRenderer EquipHead01;
    public ModelRenderer EquipHead02;
    public ModelRenderer EquipHead03;
    public ModelRenderer EquipHead04;
    public ModelRenderer EquipHead05;
    public ModelRenderer EquipHead06;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer Cloth04;
    public ModelRenderer Cloth00a;
    public ModelRenderer Cloth00b;
    public ModelRenderer Cloth00c;
    public ModelRenderer Cloth00d;
    public ModelRenderer EquipBack01a;
    public ModelRenderer EquipBack01b;
    public ModelRenderer EquipBack01c;
    public ModelRenderer EquipBack01d;
    public ModelRenderer EquipBack01e;
    public ModelRenderer EquipBack01f;
    public ModelRenderer EquipBack01g;
    public ModelRenderer EquipBack01h;
    public ModelRenderer EquipBack01i;
    public ModelRenderer EquipBack01j;
    public ModelRenderer EquipBack01k;
    public ModelRenderer EquipBack01l;
    public ModelRenderer EquipBack01m;
    public ModelRenderer EquipBack01n;
    public ModelRenderer EquipBack01o;
    public ModelRenderer EquipBack01p;
    public ModelRenderer EquipBack01q;
    public ModelRenderer EquipBack01r;
    public ModelRenderer EquipTubeR01;
    public ModelRenderer EquipTubeL01;
    public ModelRenderer EquipBack01s;
    public ModelRenderer EquipBack01t;
    public ModelRenderer EquipBack01u;
    public ModelRenderer EquipBack01v;
    public ModelRenderer EquipBack01w;
    public ModelRenderer EquipBack01x;
    public ModelRenderer EquipBack01y;
    public ModelRenderer EquipBack01z;
    public ModelRenderer EquipBack01za;
    public ModelRenderer EquipBack01zb;
    public ModelRenderer EquipBack01zc;
    public ModelRenderer EquipBack01zd;
    public ModelRenderer EquipTubeR02;
    public ModelRenderer EquipTubeR03;
    public ModelRenderer EquipTubeL02;
    public ModelRenderer EquipTubeL03;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowEquipBase;
    public ModelRenderer GlowEquipTubeL01;
    public ModelRenderer GlowEquipTubeL02;
    public ModelRenderer GlowEquipTubeR01;
    public ModelRenderer GlowEquipTubeR02;
    
    
    public ModelTransportWa()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.Cloth00b = new ModelRenderer(this, 19, 79);
        this.Cloth00b.setRotationPoint(0.0F, 0.5F, 3.3F);
        this.Cloth00b.addBox(-7.0F, -2.0F, 0.0F, 7, 4, 2, 0.0F);
        this.setRotateAngle(Cloth00b, -0.20943951023931953F, 0.2617993877991494F, 0.17453292519943295F);
        this.EquipBack01t = new ModelRenderer(this, 0, 0);
        this.EquipBack01t.mirror = true;
        this.EquipBack01t.setRotationPoint(0.0F, -10.0F, 32.0F);
        this.EquipBack01t.addBox(0.0F, 0.0F, 0.0F, 10, 10, 6, 0.0F);
        this.EquipBack01m = new ModelRenderer(this, 21, 6);
        this.EquipBack01m.setRotationPoint(16.0F, -10.0F, 16.0F);
        this.EquipBack01m.addBox(0.0F, 0.0F, 0.0F, 6, 10, 10, 0.0F);
        this.EquipHeadBase = new ModelRenderer(this, 0, 0);
        this.EquipHeadBase.setRotationPoint(0.0F, -13.8F, 0.0F);
        this.EquipHeadBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.EquipBack01v = new ModelRenderer(this, 0, 0);
        this.EquipBack01v.setRotationPoint(-10.0F, 0.0F, 32.0F);
        this.EquipBack01v.addBox(0.0F, 0.0F, 0.0F, 10, 10, 6, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -11.8F, -1.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.setRotateAngle(Head, 0.10471975511965977F, 0.0F, 0.0F);
        this.HairU01 = new ModelRenderer(this, 50, 45);
        this.HairU01.setRotationPoint(0.0F, -6.0F, -6.5F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 14, 7, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 105);
        this.BodyMain.setRotationPoint(0.0F, -11.0F, -3.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 16, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.Cloth01b = new ModelRenderer(this, 96, 19);
        this.Cloth01b.mirror = true;
        this.Cloth01b.setRotationPoint(-5.6F, -11.6F, -0.6F);
        this.Cloth01b.addBox(-4.0F, 0.0F, -4.0F, 8, 14, 8, 0.0F);
        this.setRotateAngle(Cloth01b, 0.03490658503988659F, 0.0F, -0.08726646259971647F);
        this.EquipBack01j = new ModelRenderer(this, 0, 0);
        this.EquipBack01j.setRotationPoint(-16.0F, -16.0F, 16.0F);
        this.EquipBack01j.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
        this.EquipBack01q = new ModelRenderer(this, 0, 0);
        this.EquipBack01q.mirror = true;
        this.EquipBack01q.setRotationPoint(-22.0F, 0.0F, 16.0F);
        this.EquipBack01q.addBox(0.0F, 0.0F, 0.0F, 6, 10, 10, 0.0F);
        this.EquipBack01x = new ModelRenderer(this, 0, 0);
        this.EquipBack01x.mirror = true;
        this.EquipBack01x.setRotationPoint(0.0F, -22.0F, 16.0F);
        this.EquipBack01x.addBox(0.0F, 0.0F, 0.0F, 10, 6, 10, 0.0F);
        this.EquipBack01zb = new ModelRenderer(this, 0, 14);
        this.EquipBack01zb.mirror = true;
        this.EquipBack01zb.setRotationPoint(-10.0F, 16.0F, 6.0F);
        this.EquipBack01zb.addBox(0.0F, 0.0F, 0.0F, 10, 6, 10, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 83);
        this.LegRight02.setRotationPoint(3.0F, 12.0F, -3.0F);
        this.LegRight02.addBox(-6.0F, 0.0F, 0.0F, 6, 13, 6, 0.0F);
        this.EquipBack01n = new ModelRenderer(this, 0, 0);
        this.EquipBack01n.setRotationPoint(16.0F, 0.0F, 16.0F);
        this.EquipBack01n.addBox(0.0F, 0.0F, 0.0F, 6, 10, 10, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipBack01a = new ModelRenderer(this, 0, 0);
        this.EquipBack01a.setRotationPoint(0.0F, -16.0F, 0.0F);
        this.EquipBack01a.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
        this.EquipBack01p = new ModelRenderer(this, 7, 6);
        this.EquipBack01p.setRotationPoint(-22.0F, -10.0F, 6.0F);
        this.EquipBack01p.addBox(0.0F, 0.0F, 0.0F, 6, 10, 10, 0.0F);
        this.EquipBack01i = new ModelRenderer(this, 0, 0);
        this.EquipBack01i.mirror = true;
        this.EquipBack01i.setRotationPoint(-16.0F, 0.0F, 16.0F);
        this.EquipBack01i.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
        this.EquipBack01d = new ModelRenderer(this, 0, 0);
        this.EquipBack01d.mirror = true;
        this.EquipBack01d.setRotationPoint(-16.0F, 0.0F, 0.0F);
        this.EquipBack01d.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
        this.EquipBack01w = new ModelRenderer(this, 0, 0);
        this.EquipBack01w.mirror = true;
        this.EquipBack01w.setRotationPoint(0.0F, -22.0F, 6.0F);
        this.EquipBack01w.addBox(0.0F, 0.0F, 0.0F, 10, 6, 10, 0.0F);
        this.Hair01 = new ModelRenderer(this, 0, 32);
        this.Hair01.setRotationPoint(0.0F, 7.2F, 1.1F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.08726646259971647F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 2, 84);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.0F, 10.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 11, 5, 0.0F);
        this.EquipBack01o = new ModelRenderer(this, 26, 12);
        this.EquipBack01o.setRotationPoint(-22.0F, 0.0F, 6.0F);
        this.EquipBack01o.addBox(0.0F, 0.0F, 0.0F, 6, 10, 10, 0.0F);
        this.EquipHead03 = new ModelRenderer(this, 0, 0);
        this.EquipHead03.setRotationPoint(0.0F, -4.3F, -7.0F);
        this.EquipHead03.addBox(-8.0F, 0.0F, -9.0F, 16, 10, 16, 0.0F);
        this.setRotateAngle(EquipHead03, 0.2617993877991494F, 0.0F, 0.0F);
        this.EquipTubeL02 = new ModelRenderer(this, 12, 0);
        this.EquipTubeL02.setRotationPoint(0.0F, 16.0F, 4.0F);
        this.EquipTubeL02.addBox(-4.5F, 0.0F, -8.5F, 9, 16, 9, 0.0F);
        this.setRotateAngle(EquipTubeL02, -0.9560913642424937F, 0.0F, 0.0F);
        this.Butt = new ModelRenderer(this, 52, 66);
        this.Butt.setRotationPoint(0.0F, 3.0F, 1.3F);
        this.Butt.addBox(-7.5F, 0.0F, -5.7F, 15, 7, 8, 0.0F);
        this.setRotateAngle(Butt, 0.3141592653589793F, 0.0F, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 83);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.4F, 5.5F, -2.6F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.24434609527920614F, 0.0F, 0.10471975511965977F);
        this.Cloth01a = new ModelRenderer(this, 96, 19);
        this.Cloth01a.setRotationPoint(5.6F, -11.6F, -0.6F);
        this.Cloth01a.addBox(-4.0F, 0.0F, -4.0F, 8, 14, 8, 0.0F);
        this.setRotateAngle(Cloth01a, 0.03490658503988659F, 0.0F, 0.08726646259971647F);
        this.Cloth04 = new ModelRenderer(this, 70, 21);
        this.Cloth04.setRotationPoint(0.0F, 2.5F, -4.7F);
        this.Cloth04.addBox(-6.5F, 0.0F, 0.0F, 13, 11, 0, 0.0F);
        this.setRotateAngle(Cloth04, -0.20943951023931953F, 0.0F, -0.08726646259971647F);
        this.EquipTubeR01 = new ModelRenderer(this, 0, 0);
        this.EquipTubeR01.setRotationPoint(-18.0F, 3.0F, 28.0F);
        this.EquipTubeR01.addBox(-4.0F, 0.0F, -4.0F, 8, 16, 8, 0.0F);
        this.setRotateAngle(EquipTubeR01, -0.3490658503988659F, 0.13962634015954636F, 0.13962634015954636F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 83);
        this.LegRight01.setRotationPoint(-4.4F, 5.5F, -2.6F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.13962634015954636F, 0.0F, -0.10471975511965977F);
        this.EquipBack01g = new ModelRenderer(this, 0, 0);
        this.EquipBack01g.setRotationPoint(0.0F, 0.0F, 16.0F);
        this.EquipBack01g.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
        this.EquipHead04 = new ModelRenderer(this, 0, 0);
        this.EquipHead04.setRotationPoint(0.0F, -5.2F, -2.8F);
        this.EquipHead04.addBox(0.0F, 0.0F, 0.0F, 16, 9, 16, 0.0F);
        this.setRotateAngle(EquipHead04, -0.5009094953223726F, -0.7213445798492564F, 0.34487706019407954F);
        this.EquipTubeL01 = new ModelRenderer(this, 0, 0);
        this.EquipTubeL01.setRotationPoint(18.0F, 3.0F, 28.0F);
        this.EquipTubeL01.addBox(-4.0F, 0.0F, -4.0F, 8, 16, 8, 0.0F);
        this.setRotateAngle(EquipTubeL01, -0.3490658503988659F, -0.13962634015954636F, -0.13962634015954636F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, -4.5F, 7.5F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipBase, 0.05235987755982988F, 0.0F, 0.0F);
        this.EquipBack01zc = new ModelRenderer(this, 7, 13);
        this.EquipBack01zc.mirror = true;
        this.EquipBack01zc.setRotationPoint(-10.0F, 16.0F, 16.0F);
        this.EquipBack01zc.addBox(0.0F, 0.0F, 0.0F, 10, 6, 10, 0.0F);
        this.Cloth00d = new ModelRenderer(this, 88, 101);
        this.Cloth00d.setRotationPoint(-0.8F, 1.0F, 4.5F);
        this.Cloth00d.addBox(-2.5F, 0.0F, 0.0F, 5, 12, 0, 0.0F);
        this.setRotateAngle(Cloth00d, 0.3490658503988659F, -0.13962634015954636F, 0.3141592653589793F);
        this.LegLeft02 = new ModelRenderer(this, 0, 83);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(-3.0F, 12.0F, -3.0F);
        this.LegLeft02.addBox(0.0F, 0.0F, 0.0F, 6, 13, 6, 0.0F);
        this.EquipBack01b = new ModelRenderer(this, 0, 0);
        this.EquipBack01b.mirror = true;
        this.EquipBack01b.setRotationPoint(-16.0F, -16.0F, 0.0F);
        this.EquipBack01b.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
        this.EquipTubeL03 = new ModelRenderer(this, 92, 0);
        this.EquipTubeL03.setRotationPoint(0.0F, 16.1F, -4.0F);
        this.EquipTubeL03.addBox(-4.5F, 0.0F, -4.5F, 9, 3, 9, 0.0F);
        this.EquipBack01e = new ModelRenderer(this, 0, 0);
        this.EquipBack01e.mirror = true;
        this.EquipBack01e.setRotationPoint(-10.0F, -6.0F, -4.0F);
        this.EquipBack01e.addBox(0.0F, 0.0F, 0.0F, 10, 16, 4, 0.0F);
        this.EquipTubeR03 = new ModelRenderer(this, 92, 0);
        this.EquipTubeR03.setRotationPoint(0.0F, 16.1F, -4.0F);
        this.EquipTubeR03.addBox(-4.5F, 0.0F, -4.5F, 9, 3, 9, 0.0F);
        this.EquipBack01h = new ModelRenderer(this, 0, 0);
        this.EquipBack01h.mirror = true;
        this.EquipBack01h.setRotationPoint(0.0F, -16.0F, 16.0F);
        this.EquipBack01h.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
        this.EquipBack01s = new ModelRenderer(this, 0, 0);
        this.EquipBack01s.mirror = true;
        this.EquipBack01s.setRotationPoint(0.0F, 0.0F, 32.0F);
        this.EquipBack01s.addBox(0.0F, 0.0F, 0.0F, 10, 10, 6, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 2, 84);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -9.7F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 11, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.0F, 0.0F, -0.20943951023931953F);
        this.Cloth03 = new ModelRenderer(this, 58, 32);
        this.Cloth03.setRotationPoint(0.2F, 1.3F, -0.5F);
        this.Cloth03.addBox(-7.0F, 0.0F, -4.7F, 14, 4, 9, 0.0F);
        this.setRotateAngle(Cloth03, 0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.Cloth00a = new ModelRenderer(this, 19, 79);
        this.Cloth00a.mirror = true;
        this.Cloth00a.setRotationPoint(0.5F, 0.5F, 3.5F);
        this.Cloth00a.addBox(0.0F, -2.0F, 0.0F, 7, 4, 2, 0.0F);
        this.setRotateAngle(Cloth00a, -0.091106186954104F, -0.2617993877991494F, -0.17453292519943295F);
        this.EquipBack01r = new ModelRenderer(this, 0, 0);
        this.EquipBack01r.mirror = true;
        this.EquipBack01r.setRotationPoint(-22.0F, -10.0F, 16.0F);
        this.EquipBack01r.addBox(0.0F, 0.0F, 0.0F, 6, 10, 10, 0.0F);
        this.EquipHead05 = new ModelRenderer(this, 0, 63);
        this.EquipHead05.setRotationPoint(0.0F, 7.3F, -12.0F);
        this.EquipHead05.addBox(-7.0F, 0.0F, 0.0F, 14, 5, 6, 0.0F);
        this.setRotateAngle(EquipHead05, 0.3141592653589793F, 0.0F, 0.0F);
        this.ClothHead = new ModelRenderer(this, 48, 0);
        this.ClothHead.setRotationPoint(0.0F, -1.1F, 1.5F);
        this.ClothHead.addBox(-8.0F, 0.0F, 0.0F, 16, 10, 6, 0.0F);
        this.setRotateAngle(ClothHead, -0.06981317007977318F, 0.0F, 0.0F);
        this.EquipHead06 = new ModelRenderer(this, 0, 0);
        this.EquipHead06.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipHead06.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.EquipBack01f = new ModelRenderer(this, 0, 0);
        this.EquipBack01f.setRotationPoint(0.0F, -6.0F, -4.0F);
        this.EquipBack01f.addBox(0.0F, 0.0F, 0.0F, 10, 16, 4, 0.0F);
        this.ClothLeg = new ModelRenderer(this, 30, 78);
        this.ClothLeg.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.ClothLeg.addBox(-3.5F, 0.0F, -3.5F, 7, 4, 7, 0.0F);
        this.EquipBack01k = new ModelRenderer(this, 0, 0);
        this.EquipBack01k.mirror = true;
        this.EquipBack01k.setRotationPoint(16.0F, 0.0F, 6.0F);
        this.EquipBack01k.addBox(0.0F, 0.0F, 0.0F, 6, 10, 10, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0.0F, -7.0F, -4.5F);
        this.Ahoke.addBox(0.0F, -12.0F, -6.5F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 1.2F, 0.6981317007977318F, 0.0F);
        this.EquipBack01l = new ModelRenderer(this, 0, 11);
        this.EquipBack01l.mirror = true;
        this.EquipBack01l.setRotationPoint(16.0F, -10.0F, 6.0F);
        this.EquipBack01l.addBox(0.0F, 0.0F, 0.0F, 6, 10, 10, 0.0F);
        this.EquipHead02 = new ModelRenderer(this, 0, 0);
        this.EquipHead02.setRotationPoint(-4.0F, -3.0F, -12.0F);
        this.EquipHead02.addBox(-15.0F, 0.0F, 0.0F, 15, 9, 16, 0.0F);
        this.setRotateAngle(EquipHead02, 0.0F, 0.3490658503988659F, -0.20943951023931953F);
        this.ArmRight01 = new ModelRenderer(this, 2, 84);
        this.ArmRight01.setRotationPoint(-7.8F, -9.7F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 11, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.20943951023931953F, 0.0F, 0.20943951023931953F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.EquipBack01za = new ModelRenderer(this, 0, 16);
        this.EquipBack01za.setRotationPoint(0.0F, 16.0F, 6.0F);
        this.EquipBack01za.addBox(0.0F, 0.0F, 0.0F, 10, 6, 10, 0.0F);
        this.HairMain = new ModelRenderer(this, 47, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 2, 84);
        this.ArmRight02.setRotationPoint(-3.0F, 10.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 11, 5, 0.0F);
        this.EquipTubeR02 = new ModelRenderer(this, 10, 0);
        this.EquipTubeR02.setRotationPoint(0.0F, 16.0F, 4.0F);
        this.EquipTubeR02.addBox(-4.5F, 0.0F, -8.5F, 9, 16, 9, 0.0F);
        this.setRotateAngle(EquipTubeR02, -0.9560913642424937F, 0.0F, 0.0F);
        this.BoobR = new ModelRenderer(this, 33, 101);
        this.BoobR.setRotationPoint(-4.1F, -8.5F, -3.7F);
        this.BoobR.addBox(-3F, 0F, 0F, 6, 5, 5, 0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, -0.0785F, -0.0785F);
        this.BoobL = new ModelRenderer(this, 33, 101);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(4.1F, -8.5F, -3.7F);
        this.BoobL.addBox(-3F, 0F, 0F, 6, 5, 5, 0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, 0.0785F, 0.0785F);
        this.Cloth2a = new ModelRenderer(this, 26, 89);
        this.Cloth2a.setRotationPoint(-1.2F, -0.5F, -0.7F);
        this.Cloth2a.addBox(0F, 0F, 0F, 5, 6, 6, 0.0F);
        this.setRotateAngle(Cloth2a, 0F, -0.0785F, -0.0785F);
        this.Cloth2b = new ModelRenderer(this, 26, 89);
        this.Cloth2b.mirror = true;
        this.Cloth2b.setRotationPoint(1.2F, -0.5F, -0.7F);
        this.Cloth2b.addBox(-5F, 0F, 0F, 5, 6, 6, 0F);
        this.setRotateAngle(Cloth2b, 0F, 0.0785F, 0.0785F);
        this.Cloth00c = new ModelRenderer(this, 88, 101);
        this.Cloth00c.mirror = true;
        this.Cloth00c.setRotationPoint(1.3F, 1.0F, 4.5F);
        this.Cloth00c.addBox(-2.5F, 0.0F, 0.0F, 5, 12, 0, 0.0F);
        this.setRotateAngle(Cloth00c, 0.3141592653589793F, 0.13962634015954636F, -0.3490658503988659F);
        this.Hair = new ModelRenderer(this, 50, 81);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.4F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 12, 8, 0.0F);
        this.EquipHead01 = new ModelRenderer(this, 0, 0);
        this.EquipHead01.setRotationPoint(4.0F, -3.0F, -12.0F);
        this.EquipHead01.addBox(0.0F, 0.0F, 0.0F, 15, 9, 16, 0.0F);
        this.setRotateAngle(EquipHead01, 0.0F, -0.3490658503988659F, 0.20943951023931953F);
        this.EquipBack01z = new ModelRenderer(this, 0, 0);
        this.EquipBack01z.setRotationPoint(-10.0F, -22.0F, 6.0F);
        this.EquipBack01z.addBox(0.0F, 0.0F, 0.0F, 10, 6, 10, 0.0F);
        this.EquipBack01zd = new ModelRenderer(this, 0, 6);
        this.EquipBack01zd.setRotationPoint(0.0F, 16.0F, 16.0F);
        this.EquipBack01zd.addBox(0.0F, 0.0F, 0.0F, 10, 6, 10, 0.0F);
        this.EquipBack01u = new ModelRenderer(this, 0, 0);
        this.EquipBack01u.setRotationPoint(-10.0F, -10.0F, 32.0F);
        this.EquipBack01u.addBox(0.0F, 0.0F, 0.0F, 10, 10, 6, 0.0F);
        this.EquipBack01y = new ModelRenderer(this, 0, 0);
        this.EquipBack01y.setRotationPoint(-10.0F, -22.0F, 16.0F);
        this.EquipBack01y.addBox(0.0F, 0.0F, 0.0F, 10, 6, 10, 0.0F);
        this.EquipBack01c = new ModelRenderer(this, 0, 0);
        this.EquipBack01c.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipBack01c.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Cloth03.addChild(this.Cloth00b);
        this.EquipBase.addChild(this.EquipBack01t);
        this.EquipBase.addChild(this.EquipBack01m);
        this.Head.addChild(this.EquipHeadBase);
        this.EquipBase.addChild(this.EquipBack01v);
        this.BodyMain.addChild(this.Head);
        this.Hair.addChild(this.HairU01);
        this.BodyMain.addChild(this.Cloth01b);
        this.EquipBase.addChild(this.EquipBack01j);
        this.EquipBase.addChild(this.EquipBack01q);
        this.EquipBase.addChild(this.EquipBack01x);
        this.EquipBase.addChild(this.EquipBack01zb);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipBase.addChild(this.EquipBack01n);
        this.EquipBase.addChild(this.EquipBack01a);
        this.EquipBase.addChild(this.EquipBack01p);
        this.EquipBase.addChild(this.EquipBack01i);
        this.EquipBase.addChild(this.EquipBack01d);
        this.EquipBase.addChild(this.EquipBack01w);
        this.HairMain.addChild(this.Hair01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.EquipBase.addChild(this.EquipBack01o);
        this.EquipHeadBase.addChild(this.EquipHead03);
        this.EquipTubeL01.addChild(this.EquipTubeL02);
        this.BodyMain.addChild(this.Butt);
        this.Butt.addChild(this.LegLeft01);
        this.BodyMain.addChild(this.Cloth01a);
        this.Cloth03.addChild(this.Cloth04);
        this.EquipBase.addChild(this.EquipTubeR01);
        this.Butt.addChild(this.LegRight01);
        this.EquipBase.addChild(this.EquipBack01g);
        this.EquipHeadBase.addChild(this.EquipHead04);
        this.EquipBase.addChild(this.EquipTubeL01);
        this.BoobL.addChild(this.Cloth2a);
        this.BodyMain.addChild(this.EquipBase);
        this.EquipBase.addChild(this.EquipBack01zc);
        this.Cloth03.addChild(this.Cloth00d);
        this.LegLeft01.addChild(this.LegLeft02);
        this.EquipBase.addChild(this.EquipBack01b);
        this.EquipBase.addChild(this.EquipBack01e);
        this.EquipBase.addChild(this.EquipBack01h);
        this.EquipBase.addChild(this.EquipBack01s);
        this.BodyMain.addChild(this.ArmLeft01);
        this.BodyMain.addChild(this.Cloth03);
        this.Cloth03.addChild(this.Cloth00a);
        this.EquipBase.addChild(this.EquipBack01r);
        this.BodyMain.addChild(this.BoobL);
        this.EquipHeadBase.addChild(this.EquipHead05);
        this.HairMain.addChild(this.ClothHead);
        this.EquipHeadBase.addChild(this.EquipHead06);
        this.EquipBase.addChild(this.EquipBack01f);
        this.LegLeft01.addChild(this.ClothLeg);
        this.EquipBase.addChild(this.EquipBack01k);
        this.Hair.addChild(this.Ahoke);
        this.EquipBase.addChild(this.EquipBack01l);
        this.EquipHeadBase.addChild(this.EquipHead02);
        this.BodyMain.addChild(this.ArmRight01);
        this.EquipBase.addChild(this.EquipBack01za);
        this.Head.addChild(this.HairMain);
        this.ArmRight01.addChild(this.ArmRight02);
        this.EquipTubeR01.addChild(this.EquipTubeR02);
        this.BodyMain.addChild(this.BoobR);
        this.BoobR.addChild(this.Cloth2b);
        this.Cloth03.addChild(this.Cloth00c);
        this.Head.addChild(this.Hair);
        this.EquipHeadBase.addChild(this.EquipHead01);
        this.EquipBase.addChild(this.EquipBack01z);
        this.EquipBase.addChild(this.EquipBack01zd);
        this.EquipBase.addChild(this.EquipBack01u);
        this.EquipBase.addChild(this.EquipBack01y);
        this.EquipBase.addChild(this.EquipBack01c);
        
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 105);
        this.GlowBodyMain.setRotationPoint(0.0F, -11.0F, -3.0F);
        this.GlowHead = new ModelRenderer(this, 44, 101);
        this.GlowHead.setRotationPoint(0.0F, -11.8F, -1.0F);
        this.GlowEquipBase = new ModelRenderer(this, 0, 0);
        this.GlowEquipBase.setRotationPoint(0.0F, -4.5F, 7.5F);
        this.GlowEquipTubeL01 = new ModelRenderer(this, 0, 0);
        this.GlowEquipTubeL01.setRotationPoint(18.0F, 3.0F, 28.0F);
        this.GlowEquipTubeL02 = new ModelRenderer(this, 12, 0);
        this.GlowEquipTubeL02.setRotationPoint(0.0F, 16.0F, 4.0F);
        this.setRotateAngle(GlowEquipTubeL02, -0.9560913642424937F, 0.0F, 0.0F);
        this.GlowEquipTubeR01 = new ModelRenderer(this, 0, 0);
        this.GlowEquipTubeR01.setRotationPoint(-18.0F, 3.0F, 28.0F);
        this.GlowEquipTubeR02 = new ModelRenderer(this, 10, 0);
        this.GlowEquipTubeR02.setRotationPoint(0.0F, 16.0F, 4.0F);
        this.setRotateAngle(GlowEquipTubeR02, -0.9560913642424937F, 0.0F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face4);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        
        this.GlowBodyMain.addChild(this.GlowEquipBase);
        this.GlowEquipBase.addChild(this.GlowEquipTubeL01);
        this.GlowEquipTubeL01.addChild(this.GlowEquipTubeL02);
        this.GlowEquipTubeL02.addChild(this.EquipTubeL03);
        this.GlowEquipBase.addChild(this.GlowEquipTubeR01);
        this.GlowEquipTubeR01.addChild(this.GlowEquipTubeR02);
        this.GlowEquipTubeR02.addChild(this.EquipTubeR03);
        
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
    	GlStateManager.translate(0F, 2.35F, 0F);
    	
    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	GlStateManager.disableBlend();
    	
    	//light part
    	GlStateManager.disableLighting();
//    	GlStateManager.enableCull();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
//    	GlStateManager.disableCull();
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
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
		this.GlowEquipBase.rotateAngleX = this.EquipBase.rotateAngleX;
		this.GlowEquipBase.rotateAngleY = this.EquipBase.rotateAngleY;
		this.GlowEquipBase.rotateAngleZ = this.EquipBase.rotateAngleZ;
		this.GlowEquipBase.offsetY = this.EquipBase.offsetY;
		this.GlowEquipBase.offsetZ = this.EquipBase.offsetZ;
		this.GlowEquipTubeL01.rotateAngleX = this.EquipTubeL01.rotateAngleX;
		this.GlowEquipTubeL01.rotateAngleY = this.EquipTubeL01.rotateAngleY;
		this.GlowEquipTubeL01.rotateAngleZ = this.EquipTubeL01.rotateAngleZ;
		this.GlowEquipTubeR01.rotateAngleX = this.EquipTubeR01.rotateAngleX;
		this.GlowEquipTubeR01.rotateAngleY = this.EquipTubeR01.rotateAngleY;
		this.GlowEquipTubeR01.rotateAngleZ = this.EquipTubeR01.rotateAngleZ;
    }
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
    {
    	GlStateManager.translate(0F, 0.12F, 0F);
		setFace(4);
    	
  	    //頭部
	  	this.Head.rotateAngleX = 0.3F;
	  	this.Head.rotateAngleY = 0F;
	    //胸部
  	    this.BoobL.rotateAngleX = -0.75F;
  	    this.BoobR.rotateAngleX = -0.75F;
	  	//Body
  	    this.Ahoke.rotateAngleY = 0.7F;
	  	this.BodyMain.rotateAngleX = 2.8F;
	  	this.Cloth03.rotateAngleX = 0.17F;
	  	this.Cloth04.rotateAngleX = -0.8F;
	  	this.Butt.rotateAngleX = -1.1F;
	  	this.Butt.offsetZ = 0.1F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = -0.35F;
	    this.ArmLeft01.rotateAngleZ = -2.6F;
	    this.ArmRight01.rotateAngleX = -0.35F;
		this.ArmRight01.rotateAngleZ = 2.6F;
		//leg
		this.LegLeft01.rotateAngleX = -0.24F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.1047F;
		this.LegRight01.rotateAngleX = -0.14F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.1047F;
	    //equip
		this.EquipBase.isHidden = false;
		this.EquipBase.offsetY = 0.45F;
		this.EquipBase.offsetZ = -0.85F;
		this.EquipBase.rotateAngleX = -3.1F;
	    this.EquipTubeL01.rotateAngleX = -0.3F;
	  	this.EquipTubeR01.rotateAngleX = -0.3F;
	  	
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
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}

    	//leg move
  		addk1 = angleAdd1 * 0.5F - 0.24F;  //LegLeft01
	  	addk2 = angleAdd2 * 0.5F - 0.14F;  //LegRight01
    	
  	    //頭部
	  	this.Head.rotateAngleX = f4 * 0.014F + 0.1047F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	    //胸部
  	    this.BoobL.rotateAngleX = angleX * 0.05F - 0.75F;
  	    this.BoobR.rotateAngleX = angleX * 0.05F - 0.75F;
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.7F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.Butt.rotateAngleX = 0.3142F;
	  	this.Butt.offsetY = 0F;
	  	this.Butt.offsetZ = 0F;
	  	this.Cloth03.rotateAngleX = 0.1745F;
	  	this.Cloth04.rotateAngleX = angleX * 0.05F - 0.15F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.25F + 0.21F;
	    this.ArmLeft01.rotateAngleZ = angleX * 0.03F - 0.21F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.25F + 0.05F;
	    this.ArmRight01.rotateAngleY = 0F;
		this.ArmRight01.rotateAngleZ = -angleX * 0.03F + 0.21F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.1047F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.1047F;
	    //equip
	    this.EquipBase.rotateAngleX = 0.05236F;
	    this.EquipBase.offsetY = 0F;
		this.EquipBase.offsetZ = 0F;
	    this.EquipTubeL01.rotateAngleX = angleX * 0.08F - 0.35F;
	  	this.EquipTubeR01.rotateAngleX = -angleX * 0.08F - 0.35F;
	    
	    //fly mode
	    if (ent.getStateEmotion(ID.S.State2) > ID.State.NORMALa)
	    {
	    	//body
	    	this.Cloth04.rotateAngleX += 0.23F;
		  	this.Butt.rotateAngleX = 0.7F;
		  	this.Butt.offsetY = -0.1F;
		  	this.Butt.offsetZ = -0.05F;
		  	//arm
		  	this.ArmLeft01.rotateAngleX += 0.2F;
		  	this.ArmLeft01.rotateAngleZ -= 0.3F;
		  	this.ArmRight01.rotateAngleX += 0.2F;
		  	this.ArmRight01.rotateAngleZ += 0.3F;
		  	//equip
		  	this.EquipBase.rotateAngleX = -0.4F;
		  	this.EquipTubeL01.rotateAngleX += 0.35F;
		  	this.EquipTubeR01.rotateAngleX += 0.35F;
	    }

	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {	//奔跑動作
	    	//head
	    	this.Head.rotateAngleX -= 0.2 ;
	    	//body
	    	this.BodyMain.rotateAngleX = 0.35F;
	    	this.Cloth04.rotateAngleX -= 0.4F;
	    	//arm
	    	this.ArmLeft01.rotateAngleZ -= 0.2F + f1 * 0.25F;
	    	this.ArmRight01.rotateAngleZ += 0.2F + f1 * 0.25F;
	    	//leg
	    	addk1 -= 0.45F;
	    	addk2 -= 0.45F;
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {		//潛行, 蹲下動作
	    	GlStateManager.translate(0F, 0.05F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.8378F;
		  	this.Cloth03.rotateAngleX -= 0.7F;
		  	this.Cloth04.rotateAngleX -= 0.45F;
		    //arm 
		    this.ArmLeft01.rotateAngleX = -0.7F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
		    this.ArmRight01.rotateAngleX = -0.7F;
		    this.ArmRight01.rotateAngleZ = -0.2618F;
  		}//end if sneaking
  		
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {  //騎乘動作
	    	float ax = MathHelper.cos(f2 * 0.5F) * 0.5F;
	    	
	    	//fly mode
		    if (ent.getStateEmotion(ID.S.State2) > ID.State.NORMALa)
		    {
		    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
		    		GlStateManager.translate(0F, 0.54F, 0F);
			    	setFace(3);
			    	
				  	//body
			    	this.Head.rotateAngleX = -0.9F;
			    	this.Head.rotateAngleY = 0F;
			    	this.Head.rotateAngleZ = 0F;
			    	this.Ahoke.rotateAngleY = 0.5236F;
			    	this.BodyMain.rotateAngleX = 1.4835F;
			    	//arm
			    	this.ArmLeft01.rotateAngleX = ax + 0.25F;
			    	this.ArmLeft01.rotateAngleZ = -2.3F;
			    	this.ArmRight01.rotateAngleX = -ax + 0.25F;
			    	this.ArmRight01.rotateAngleZ = 2.3F;
			    	//leg
			    	this.LegLeft01.rotateAngleY = 0F;
			    	this.LegLeft01.rotateAngleZ = 0.03F;
			    	this.LegRight01.rotateAngleY = 0F;
			    	this.LegRight01.rotateAngleZ = -0.03F;
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, -0.17F, 0F);
			    	setFace(1);
			    	
				  	//body
			    	this.Head.rotateAngleX = -0.7F;
			    	this.Head.rotateAngleY = 0F;
			    	this.Head.rotateAngleZ = 0F;
			    	this.Ahoke.rotateAngleY = 0.5236F;
			    	this.BodyMain.rotateAngleX = -1.7453F;
			    	this.Cloth04.rotateAngleX = 0.4F;
			    	//arm
			    	this.ArmLeft01.rotateAngleX = 0.85F;
			    	this.ArmLeft01.rotateAngleZ = -2.3F;
			    	this.ArmRight01.rotateAngleX = 0.85F;
			    	this.ArmRight01.rotateAngleZ = 2.3F;
			    	//leg
			    	this.LegLeft01.rotateAngleY = 0F;
			    	this.LegLeft01.rotateAngleZ = 0.03F;
			    	this.LegRight01.rotateAngleY = 0F;
			    	this.LegRight01.rotateAngleZ = -0.03F;
			    	//equip
				  	this.EquipTubeL01.rotateAngleX = 1.3F;
				  	this.EquipTubeR01.rotateAngleX = 1.3F;
		    	}
		    }
		    else
		    {
		    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
		    	{
		    		GlStateManager.translate(0F, 0.53F, 0F);
			    	setFace(3);
			    	
				  	//body
			    	this.Head.rotateAngleX = -0.7F;
			    	this.Head.rotateAngleY = 0F;
			    	this.Head.rotateAngleZ = 0F;
			    	this.Ahoke.rotateAngleY = 0.5236F;
			    	this.BodyMain.rotateAngleX = 1.4835F;
			    	//arm
			    	this.ArmLeft01.rotateAngleX = ax + 0.25F;
			    	this.ArmLeft01.rotateAngleZ = -2.3F;
			    	this.ArmRight01.rotateAngleX = -ax + 0.25F;
			    	this.ArmRight01.rotateAngleZ = 2.3F;
			    	//leg
			    	addk1 = -ax + 0.2F;
			    	addk2 = ax + 0.2F;
			    	this.LegLeft01.rotateAngleY = 0F;
			    	this.LegLeft01.rotateAngleZ = 0.03F;
			    	this.LegRight01.rotateAngleY = 0F;
			    	this.LegRight01.rotateAngleZ = -0.03F;
		    	}
		    	else
		    	{
		    		GlStateManager.translate(0F, 0.42F, 0F);
			    	//body
			    	this.Head.rotateAngleX -= 0.7F;
			    	this.BodyMain.rotateAngleX = 0.5236F;
			    	//arm
			    	this.ArmLeft01.rotateAngleX = -0.5236F;
			    	this.ArmLeft01.rotateAngleZ = 0.3146F;
			    	this.ArmRight01.rotateAngleX = -0.5236F;
			    	this.ArmRight01.rotateAngleZ = -0.3146F;
			    	//leg
			    	addk1 = -2.2689F;
			    	addk2 = -2.2689F;
			    	this.LegLeft01.rotateAngleY = -0.3491F;
			    	this.LegRight01.rotateAngleY = 0.3491F;
			    	//equip
				  	this.EquipBase.rotateAngleX = -0.4F;
				  	this.EquipTubeL01.rotateAngleX = 0.9F;
				  	this.EquipTubeR01.rotateAngleX = 0.9F;
		    	}
		    }
  		}//end if sitting
	    
	    //攻擊動作    
	    if (ent.getAttackTick() > 40)
	    {
	    	GlStateManager.translate(0F, 0.08F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.7F;
		  	this.Butt.rotateAngleX = -0.8378F;
		  	this.Cloth03.rotateAngleX -= 0.7F;
		  	this.Cloth04.rotateAngleX -= 1.1F;
		    //arm 
		    this.ArmLeft01.rotateAngleX = -0.9F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
		    this.ArmRight01.rotateAngleX = -1.9F;
		    this.ArmRight01.rotateAngleZ = -0.2618F;
		    //equip
		    this.EquipBase.rotateAngleX = -1.4F;
		    //leg
		    addk1 -= 0.7F;
		    addk2 -= 0.7F;
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 % 1F);
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
  	
  	private void shaaowEquip(IShipEmotion ent)
  	{
  		switch (ent.getStateEmotion(ID.S.State))
  		{
  		case ID.State.EQUIP00:
  			this.EquipBase.isHidden = true;
  			this.GlowEquipBase.isHidden = true;
  			this.EquipHeadBase.isHidden = false;
  			this.Ahoke.isHidden = true;
  		break;
  		case ID.State.EQUIP01:
  			this.EquipBase.isHidden = false;
  			this.GlowEquipBase.isHidden = false;
  			this.EquipHeadBase.isHidden = true;
  			this.Ahoke.isHidden = false;
  		break;
  		case ID.State.EQUIP02:
  			this.EquipBase.isHidden = false;
  			this.GlowEquipBase.isHidden = false;
  			this.EquipHeadBase.isHidden = false;
  			this.Ahoke.isHidden = true;
  		break;
  		default:  //normal
  			this.EquipBase.isHidden = true;
  			this.GlowEquipBase.isHidden = true;
  			this.EquipHeadBase.isHidden = true;
  			this.Ahoke.isHidden = false;
  		break;
  		}
  		
  		switch (ent.getStateEmotion(ID.S.State2))
  		{
  		case ID.State.EQUIP00a:
  			this.EquipBase.isHidden = false;
  			this.GlowEquipBase.isHidden = false;
  			this.LegLeft01.isHidden = true;
  			this.LegRight01.isHidden = true;
  		break;
  		default:  //normal
  			this.LegLeft01.isHidden = false;
  			this.LegRight01.isHidden = false;
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

	@Override
	public void showEquip(IShipEmotion ent)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void syncRotationGlowPart()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
		// TODO Auto-generated method stub
		
	}
    
    
}