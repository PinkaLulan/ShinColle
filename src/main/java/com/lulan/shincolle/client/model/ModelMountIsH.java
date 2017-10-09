package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.handler.EventHandler;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelMountIsH - PinkaLulan
 * Created using Tabula 5.1.0
 */
public class ModelMountIsH extends ModelBase
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Cannon01a;
    public ModelRenderer Cannon01b;
    public ModelRenderer Body01;
    public ModelRenderer Body04;
    public ModelRenderer Body05;
    public ModelRenderer LegFL01;
    public ModelRenderer LegFR01;
    public ModelRenderer Head;
    public ModelRenderer Jaw;
    public ModelRenderer NeckFront;
    public ModelRenderer HeadTooth;
    public ModelRenderer HeadCannon;
    public ModelRenderer TopCannonBase;
    public ModelRenderer TopCannon01a;
    public ModelRenderer TopCannon01b;
    public ModelRenderer TopCannonBase02;
    public ModelRenderer TopCannon02a;
    public ModelRenderer TopCannon03a;
    public ModelRenderer TopCannon04a;
    public ModelRenderer TopCannon02b;
    public ModelRenderer TopCannon03b;
    public ModelRenderer TopCannon04b;
    public ModelRenderer JawTooth;
    public ModelRenderer Tongue01;
    public ModelRenderer Tongue02;
    public ModelRenderer Tongue03;
    public ModelRenderer Cannon02a;
    public ModelRenderer Cannon03a;
    public ModelRenderer Cannon02b;
    public ModelRenderer Cannon03b;
    public ModelRenderer Body02;
    public ModelRenderer Body03;
    public ModelRenderer LegBR01;
    public ModelRenderer LegBL01;
    public ModelRenderer LegBR02;
    public ModelRenderer LegBR03;
    public ModelRenderer LegFR02;
    public ModelRenderer LegFR03;
    public ModelRenderer LegFL02;
    public ModelRenderer LegFL03;
    public ModelRenderer LegBL02;
    public ModelRenderer LegBL03;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowJaw;
    public ModelRenderer GlowTopCannonBase;
    

    public ModelMountIsH()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.LegBR01 = new ModelRenderer(this, 0, 0);
        this.LegBR01.setRotationPoint(-6.0F, 4.0F, 5.0F);
        this.LegBR01.addBox(0.0F, -4.5F, -9.0F, 3, 9, 12, 0.0F);
        this.setRotateAngle(LegBR01, 1.0471975511965976F, 3.001966313430247F, -0.05235987755982988F);
        this.Cannon01b = new ModelRenderer(this, 0, 0);
        this.Cannon01b.setRotationPoint(-7.0F, 2.0F, -10.0F);
        this.Cannon01b.addBox(-7.0F, -2.5F, -2.5F, 7, 5, 5, 0.0F);
        this.NeckFront = new ModelRenderer(this, 46, 39);
        this.NeckFront.setRotationPoint(0.0F, -8.5F, -16.0F);
        this.NeckFront.addBox(-6.5F, 0.0F, 0.0F, 13, 14, 2, 0.0F);
        this.LegFL02 = new ModelRenderer(this, 3, 5);
        this.LegFL02.setRotationPoint(1.6F, -2.0F, -8.5F);
        this.LegFL02.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(LegFL02, -0.2617993877991494F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 7, 0);
        this.Jaw.setRotationPoint(0.0F, 4.0F, -11.0F);
        this.Jaw.addBox(-9.5F, 0.0F, -15.0F, 19, 7, 19, 0.0F);
        this.setRotateAngle(Jaw, 0.2617993877991494F, 0.0F, 0.0F);
        this.TopCannon01a = new ModelRenderer(this, 0, 0);
        this.TopCannon01a.setRotationPoint(3.2F, -4.0F, -6.7F);
        this.TopCannon01a.addBox(-1.5F, -1.5F, -6.0F, 3, 4, 6, 0.0F);
        this.setRotateAngle(TopCannon01a, -0.3490658503988659F, 0.0F, 0.0F);
        this.TopCannon03b = new ModelRenderer(this, 120, 0);
        this.TopCannon03b.setRotationPoint(0.0F, 0.0F, -5.9F);
        this.TopCannon03b.addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2, 0.0F);
        this.setRotateAngle(TopCannon03b, -1.5707963267948966F, 0.0F, 0.0F);
        this.HeadTooth = new ModelRenderer(this, 62, 45);
        this.HeadTooth.setRotationPoint(0.0F, 2.5F, -15.0F);
        this.HeadTooth.addBox(-9.0F, 0.0F, -6.5F, 18, 4, 15, 0.0F);
        this.setRotateAngle(HeadTooth, 0.05235987755982988F, 0.0F, 0.0F);
        this.TopCannon02b = new ModelRenderer(this, 0, 0);
        this.TopCannon02b.setRotationPoint(0.0F, 0.8F, -7.0F);
        this.TopCannon02b.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 7, 0.0F);
        this.TopCannon03a = new ModelRenderer(this, 120, 0);
        this.TopCannon03a.setRotationPoint(0.0F, 0.0F, -5.9F);
        this.TopCannon03a.addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2, 0.0F);
        this.setRotateAngle(TopCannon03a, -1.5707963267948966F, 0.0F, 0.0F);
        this.LegBL03 = new ModelRenderer(this, 8, 0);
        this.LegBL03.mirror = true;
        this.LegBL03.setRotationPoint(0.0F, 10.2F, 1.0F);
        this.LegBL03.addBox(-0.5F, -6.0F, 0.0F, 1, 11, 3, 0.0F);
        this.setRotateAngle(LegBL03, -1.0471975511965976F, 0.0F, 0.0F);
        this.Cannon03a = new ModelRenderer(this, 98, 0);
        this.Cannon03a.setRotationPoint(4.0F, 0.0F, -7.0F);
        this.Cannon03a.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(Cannon03a, -1.7453292519943295F, 0.0F, 0.0F);
        this.TopCannon04b = new ModelRenderer(this, 0, 0);
        this.TopCannon04b.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.TopCannon04b.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, -8F, 5F);
        this.BodyMain.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Body04 = new ModelRenderer(this, 7, 0);
        this.Body04.setRotationPoint(0.0F, 3.0F, -3.0F);
        this.Body04.addBox(-7.5F, -6.0F, 0.0F, 15, 15, 12, 0.0F);
        this.setRotateAngle(Body04, -0.3490658503988659F, 0.0F, 0.0F);
        this.LegBL01 = new ModelRenderer(this, 5, 0);
        this.LegBL01.mirror = true;
        this.LegBL01.setRotationPoint(6.0F, 4.0F, 5.0F);
        this.LegBL01.addBox(-3.0F, -4.5F, -9.0F, 3, 9, 12, 0.0F);
        this.setRotateAngle(LegBL01, 1.0471975511965976F, -3.001966313430247F, 0.05235987755982988F);
        this.TopCannon02a = new ModelRenderer(this, 0, 0);
        this.TopCannon02a.setRotationPoint(0.0F, 0.8F, -7.0F);
        this.TopCannon02a.addBox(-0.5F, 0.0F, 0.0F, 1, 3, 7, 0.0F);
        this.LegBR02 = new ModelRenderer(this, 0, 17);
        this.LegBR02.setRotationPoint(1.6F, -2.0F, -8.5F);
        this.LegBR02.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(LegBR02, -0.2617993877991494F, 0.0F, 0.0F);
        this.LegBL02 = new ModelRenderer(this, 0, 0);
        this.LegBL02.mirror = true;
        this.LegBL02.setRotationPoint(-1.6F, -2.0F, -8.5F);
        this.LegBL02.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(LegBL02, -0.2617993877991494F, 0.0F, 0.0F);
        this.LegFR02 = new ModelRenderer(this, 0, 0);
        this.LegFR02.mirror = true;
        this.LegFR02.setRotationPoint(-1.6F, -2.0F, -8.5F);
        this.LegFR02.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(LegFR02, -0.2617993877991494F, 0.0F, 0.0F);
        this.HeadCannon = new ModelRenderer(this, 107, 0);
        this.HeadCannon.setRotationPoint(0.0F, -2.9F, -21.0F);
        this.HeadCannon.addBox(-1.5F, 0.0F, -1.5F, 3, 16, 3, 0.0F);
        this.setRotateAngle(HeadCannon, -1.6580627893946132F, 0.0F, 0.0F);
        this.TopCannonBase02 = new ModelRenderer(this, 0, 0);
        this.TopCannonBase02.setRotationPoint(0.0F, -0.5F, -0.7F);
        this.TopCannonBase02.addBox(-5.0F, -6.0F, 0.0F, 10, 6, 6, 0.0F);
        this.setRotateAngle(TopCannonBase02, -0.08726646259971647F, 0.0F, 0.0F);
        this.JawTooth = new ModelRenderer(this, 63, 46);
        this.JawTooth.setRotationPoint(0.0F, -1.7F, -0.3F);
        this.JawTooth.addBox(-9.0F, 0.0F, -14.0F, 18, 3, 14, 0.0F);
        this.setRotateAngle(JawTooth, -0.08726646259971647F, -0.02234021442552742F, 0.0F);
        this.LegFL03 = new ModelRenderer(this, 9, 0);
        this.LegFL03.setRotationPoint(0.0F, 10.2F, 1.0F);
        this.LegFL03.addBox(-0.5F, -6.0F, 0.0F, 1, 11, 3, 0.0F);
        this.setRotateAngle(LegFL03, -1.0471975511965976F, 0.0F, 0.0F);
        this.Body01 = new ModelRenderer(this, 12, 0);
        this.Body01.setRotationPoint(0.0F, -1.0F, -0.5F);
        this.Body01.addBox(-8.5F, -12.0F, -6.0F, 17, 12, 12, 0.0F);
        this.setRotateAngle(Body01, -0.17453292519943295F, 0.0F, 0.0F);
        this.Cannon02b = new ModelRenderer(this, 65, 0);
        this.Cannon02b.mirror = true;
        this.Cannon02b.setRotationPoint(-3.0F, -0.5F, 2.0F);
        this.Cannon02b.addBox(-8.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(Cannon02b, 0.20943951023931953F, 0.17453292519943295F, 0.0F);
        this.Cannon03b = new ModelRenderer(this, 98, 0);
        this.Cannon03b.setRotationPoint(-4.0F, 0.0F, -7.0F);
        this.Cannon03b.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(Cannon03b, -1.7453292519943295F, 0.0F, 0.0F);
        this.LegFL01 = new ModelRenderer(this, 34, 7);
        this.LegFL01.setRotationPoint(9.0F, 13.0F, -19.0F);
        this.LegFL01.addBox(0.0F, -4.5F, -9.0F, 3, 9, 12, 0.0F);
        this.setRotateAngle(LegFL01, 0.8726646259971648F, -0.13962634015954636F, 0.05235987755982988F);
        this.LegFR01 = new ModelRenderer(this, 0, 11);
        this.LegFR01.mirror = true;
        this.LegFR01.setRotationPoint(-9.0F, 13.0F, -19.0F);
        this.LegFR01.addBox(-3.0F, -4.5F, -9.0F, 3, 9, 12, 0.0F);
        this.setRotateAngle(LegFR01, 0.8726646259971648F, 0.13962634015954636F, -0.05235987755982988F);
        this.LegFR03 = new ModelRenderer(this, 19, 0);
        this.LegFR03.mirror = true;
        this.LegFR03.setRotationPoint(0.0F, 10.2F, 1.0F);
        this.LegFR03.addBox(-0.5F, -6.0F, 0.0F, 1, 11, 3, 0.0F);
        this.setRotateAngle(LegFR03, -1.0471975511965976F, 0.0F, 0.0F);
        this.Body03 = new ModelRenderer(this, 18, 0);
        this.Body03.setRotationPoint(0.0F, 0.5F, 10.0F);
        this.Body03.addBox(-7.5F, -12.0F, -6.0F, 15, 12, 8, 0.0F);
        this.setRotateAngle(Body03, 0.3490658503988659F, 0.0F, 0.0F);
        this.Cannon02a = new ModelRenderer(this, 65, 0);
        this.Cannon02a.setRotationPoint(3.0F, -0.5F, 2.0F);
        this.Cannon02a.addBox(0.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.setRotateAngle(Cannon02a, 0.20943951023931953F, -0.17453292519943295F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 3);
        this.Head.setRotationPoint(0.0F, -9.0F, -4.0F);
        this.Head.addBox(-9.5F, -7.0F, -22.0F, 19, 10, 24, 0.0F);
        this.setRotateAngle(Head, -0.20943951023931953F, 0.0F, 0.0F);
        this.TopCannon01b = new ModelRenderer(this, 0, 0);
        this.TopCannon01b.setRotationPoint(-3.2F, -4.0F, -6.7F);
        this.TopCannon01b.addBox(-1.5F, -1.5F, -6.0F, 3, 4, 6, 0.0F);
        this.setRotateAngle(TopCannon01b, -0.3490658503988659F, 0.0F, 0.0F);
        this.TopCannonBase = new ModelRenderer(this, 3, 0);
        this.TopCannonBase.setRotationPoint(0.0F, -6.0F, -5.0F);
        this.TopCannonBase.addBox(-7.5F, -8.0F, -8.0F, 15, 8, 8, 0.0F);
        this.setRotateAngle(TopCannonBase, -0.08726646259971647F, 0.0F, 0.0F);
        this.Body05 = new ModelRenderer(this, 0, 0);
        this.Body05.setRotationPoint(0.0F, 9.4F, 4.5F);
        this.Body05.addBox(-6.5F, -6.0F, 0.0F, 13, 12, 12, 0.0F);
        this.setRotateAngle(Body05, 0.08726646259971647F, 0.0F, 0.0F);
        this.Neck = new ModelRenderer(this, 0, 0);
        this.Neck.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Neck.addBox(-7.5F, -7.5F, -14.0F, 15, 15, 14, 0.0F);
        this.setRotateAngle(Neck, 0.18203784098300857F, 0.0F, 0.0F);
        this.Cannon01a = new ModelRenderer(this, 0, 0);
        this.Cannon01a.setRotationPoint(7.0F, 2.0F, -10.0F);
        this.Cannon01a.addBox(0.0F, -2.5F, -2.5F, 7, 5, 5, 0.0F);
        this.Body02 = new ModelRenderer(this, 6, 3);
        this.Body02.setRotationPoint(0.0F, 2.0F, 7.0F);
        this.Body02.addBox(-8.0F, -12.0F, -6.0F, 16, 12, 12, 0.0F);
        this.setRotateAngle(Body02, -0.2617993877991494F, 0.0F, 0.0F);
        this.Tongue03 = new ModelRenderer(this, 0, 51);
        this.Tongue03.setRotationPoint(0.0F, -0.2F, -6.7F);
        this.Tongue03.addBox(-5.0F, -0.3F, -6.0F, 10, 2, 6, 0.0F);
        this.setRotateAngle(Tongue03, 0.6981317007977318F, 0.0F, 0.0F);
        this.TopCannon04a = new ModelRenderer(this, 0, 0);
        this.TopCannon04a.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.TopCannon04a.addBox(-1.5F, 0.0F, -1.5F, 3, 4, 3, 0.0F);
        this.Tongue01 = new ModelRenderer(this, 0, 50);
        this.Tongue01.mirror = true;
        this.Tongue01.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.Tongue01.addBox(-7.0F, 0.0F, -10.0F, 14, 4, 10, 0.0F);
        this.setRotateAngle(Tongue01, -0.3839724354387525F, 0.3490658503988659F, -0.05235987755982988F);
        this.Tongue02 = new ModelRenderer(this, 8, 52);
        this.Tongue02.setRotationPoint(0.0F, 1.0F, -10.0F);
        this.Tongue02.addBox(-6.0F, -0.7F, -7.0F, 12, 3, 7, 0.0F);
        this.setRotateAngle(Tongue02, 0.5235987755982988F, 0.0F, 0.0F);
        this.LegBR03 = new ModelRenderer(this, 0, 0);
        this.LegBR03.setRotationPoint(0.0F, 10.2F, 1.0F);
        this.LegBR03.addBox(-0.5F, -6.0F, 0.0F, 1, 11, 3, 0.0F);
        this.setRotateAngle(LegBR03, -1.0471975511965976F, 0.0F, 0.0F);
        this.Body05.addChild(this.LegBR01);
        this.LegFL01.addChild(this.LegFL02);
        this.Neck.addChild(this.Jaw);
        this.LegBL02.addChild(this.LegBL03);
        this.BodyMain.addChild(this.Body04);
        this.Body05.addChild(this.LegBL01);
        this.LegBR01.addChild(this.LegBR02);
        this.LegFR01.addChild(this.LegBL02);
        this.LegBL01.addChild(this.LegFR02);
        this.TopCannonBase.addChild(this.TopCannonBase02);
        this.LegFL02.addChild(this.LegFL03);
        this.BodyMain.addChild(this.Body01);
        this.BodyMain.addChild(this.LegFL01);
        this.BodyMain.addChild(this.LegFR01);
        this.LegFR02.addChild(this.LegFR03);
        this.Body02.addChild(this.Body03);
        this.Neck.addChild(this.Head);
        this.Head.addChild(this.TopCannonBase);
        this.BodyMain.addChild(this.Body05);
        this.BodyMain.addChild(this.Neck);
        this.Body01.addChild(this.Body02);
        this.LegBR02.addChild(this.LegBR03);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -8F, 5F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.setRotateAngle(GlowNeck, 0.18203784098300857F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -9.0F, -4.0F);
        this.setRotateAngle(GlowHead, -0.20943951023931953F, 0.0F, 0.0F);
        this.GlowJaw = new ModelRenderer(this, 0, 0);
        this.GlowJaw.setRotationPoint(0.0F, 4.0F, -11.0F);
        this.setRotateAngle(GlowJaw, 0.2617993877991494F, 0.0F, 0.0F);
        this.GlowTopCannonBase = new ModelRenderer(this, 0, 0);
        this.GlowTopCannonBase.setRotationPoint(0.0F, -6.0F, -5.0F);
        this.setRotateAngle(GlowTopCannonBase, -0.08726646259971647F, 0.0F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowNeck.addChild(this.GlowJaw);
        this.GlowNeck.addChild(this.NeckFront);
        this.GlowHead.addChild(this.HeadTooth);
        this.GlowJaw.addChild(this.JawTooth);
        this.GlowJaw.addChild(this.Tongue01);
        this.Tongue01.addChild(this.Tongue02);
        this.Tongue02.addChild(this.Tongue03);
        this.GlowHead.addChild(this.HeadCannon);
        this.GlowBodyMain.addChild(this.Cannon01a);
        this.Cannon01a.addChild(this.Cannon02a);
        this.Cannon02a.addChild(this.Cannon03a);
        this.GlowBodyMain.addChild(this.Cannon01b);
        this.Cannon01b.addChild(this.Cannon02b);
        this.Cannon02b.addChild(this.Cannon03b);
        this.GlowHead.addChild(this.GlowTopCannonBase);
        this.GlowTopCannonBase.addChild(this.TopCannon01a);
        this.TopCannon01a.addChild(this.TopCannon02a);
        this.TopCannon01a.addChild(this.TopCannon03a);
        this.TopCannon03a.addChild(this.TopCannon04a);
        this.GlowTopCannonBase.addChild(this.TopCannon01b);
        this.TopCannon01b.addChild(this.TopCannon02b);
        this.TopCannon01b.addChild(this.TopCannon03b);
        this.TopCannon03b.addChild(this.TopCannon04b);
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
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
    	GlStateManager.scale(0.7F, 0.7F, 0.7F);
    	GlStateManager.translate(0F, 0.7F, 0F);
    	
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
    
    //for idle/run animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		IShipEmotion ent = (IShipEmotion)entity;
		  
		motionHumanPos(f, f1, f2, f3, f4, ent);
    }
    
    //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
  	{   
  		float angleX = MathHelper.cos(f2 * 0.08F);
  		float angleX2 = MathHelper.cos(f2 * 0.08F + 0.1F);
  		float angleX3 = MathHelper.cos(f2 * 0.08F + 0.2F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
  		float addk1 = angleAdd1 * 0.6F;
  		float addk2 = angleAdd2 * 0.6F;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.025F + 0.025F, 0F);
    	}

  		//leg
  		this.LegFL01.rotateAngleX = addk1 + 1.04F;
  		this.LegFR01.rotateAngleX = addk1 + 1.04F;
  		this.LegBL01.rotateAngleX = addk1 + 1.04F;
  		this.LegBR01.rotateAngleX = addk1 + 1.04F;
	    //idle
	  	this.Jaw.rotateAngleX = angleX * 0.1F + 0.26F;
	  	this.GlowJaw.rotateAngleX = this.Jaw.rotateAngleX;
	  	this.Tongue01.rotateAngleX = angleX * 0.05F - 0.38F;
	  	this.Tongue02.rotateAngleX = angleX2 * 0.05F + 0.52F;
	  	this.Tongue03.rotateAngleX = angleX3 * 0.05F + 0.69F;
	    //cannon
	    this.HeadCannon.rotateAngleX = f4 * 0.01F - 1.68F;
	    this.Cannon03a.rotateAngleX = f4 * 0.01F - 1.74F;
	    this.Cannon03b.rotateAngleX = f4 * 0.01F - 1.7F;
	    this.TopCannonBase.rotateAngleX = f3 * 0.01F;
	    this.GlowTopCannonBase.rotateAngleX = this.TopCannonBase.rotateAngleX;
  	}
  	
  	
}