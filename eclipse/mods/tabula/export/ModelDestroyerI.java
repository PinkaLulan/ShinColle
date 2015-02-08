package com;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelDestroyerI - Either Mojang or a mod author
 * Created using Tabula 4.1.1
 */
public class ModelDestroyerI extends ModelBase {
    public ModelRenderer Back;
    public ModelRenderer NeckBack;
    public ModelRenderer NeckNeck;
    public ModelRenderer NeckBody;
    public ModelRenderer Body;
    public ModelRenderer TailBack;
    public ModelRenderer TailBody;
    public ModelRenderer Head;
    public ModelRenderer ToothTopMid;
    public ModelRenderer ToothTopRight;
    public ModelRenderer ToothTopLeft;
    public ModelRenderer JawTop;
    public ModelRenderer JawBottom;
    public ModelRenderer ToothBottomLeft;
    public ModelRenderer ToothBottomRight;
    public ModelRenderer ToothBottomMid;
    public ModelRenderer LeftEye;
    public ModelRenderer RightEye;
    public ModelRenderer LegLeftFront;
    public ModelRenderer LegRightFront;
    public ModelRenderer LegLeftEnd;
    public ModelRenderer LegRightEnd;
    public ModelRenderer TailLeftFront;
    public ModelRenderer TailRightFront;
    public ModelRenderer TailEnd;
    public ModelRenderer TailLeftEnd;
    public ModelRenderer TailRightEnd;

    public ModelDestroyerI() {
        this.textureWidth = 256;
        this.textureHeight = 128;
        this.Back = new ModelRenderer(this, 128, 8);
        this.Back.mirror = true;
        this.Back.setRotationPoint(-8.0F, -16.0F, 0.0F);
        this.Back.addBox(-12.0F, -10.0F, -12.0F, 28, 20, 24, 0.0F);
        this.setRotateAngle(Back, 0.0F, 0.0F, -0.31000000238418574F);
        this.ToothTopMid = new ModelRenderer(this, 96, 0);
        this.ToothTopMid.mirror = true;
        this.ToothTopMid.setRotationPoint(26.0F, 0.0F, 0.0F);
        this.ToothTopMid.addBox(14.5F, 20.0F, -6.0F, 4, 6, 12, 0.0F);
        this.setRotateAngle(ToothTopMid, 0.0F, 0.0F, 0.30000001192092896F);
        this.TailRightEnd = new ModelRenderer(this, 128, 36);
        this.TailRightEnd.mirror = true;
        this.TailRightEnd.setRotationPoint(0.0F, 9.0F, -5.0F);
        this.TailRightEnd.addBox(-24.0F, -4.0F, -2.0F, 24, 12, 4, 0.0F);
        this.setRotateAngle(TailRightEnd, 0.0F, 0.0F, -0.40000000596046453F);
        this.ToothTopLeft = new ModelRenderer(this, 128, 54);
        this.ToothTopLeft.mirror = true;
        this.ToothTopLeft.setRotationPoint(26.0F, 0.0F, 0.0F);
        this.ToothTopLeft.addBox(0.0F, 20.0F, 6.0F, 18, 6, 4, 0.0F);
        this.setRotateAngle(ToothTopLeft, 0.0F, 0.0F, 0.30000001192092896F);
        this.NeckBody = new ModelRenderer(this, 0, 70);
        this.NeckBody.mirror = true;
        this.NeckBody.setRotationPoint(15.0F, 0.0F, 0.0F);
        this.NeckBody.addBox(-8.0F, 7.0F, -9.026666641235352F, 18, 14, 18, 0.0F);
        this.setRotateAngle(NeckBody, 0.0F, 0.0F, 0.20000000298023227F);
        this.JawTop = new ModelRenderer(this, 0, 102);
        this.JawTop.mirror = true;
        this.JawTop.setRotationPoint(26.0F, 0.0F, 0.0F);
        this.JawTop.addBox(-3.0F, 20.0F, -11.0F, 22, 2, 22, 0.0F);
        this.setRotateAngle(JawTop, 0.0F, 0.0F, 0.30000001192092896F);
        this.LeftEye = new ModelRenderer(this, 138, 84);
        this.LeftEye.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftEye.addBox(-3.0F, 0.0F, 16.1F, 24, 20, 0, 0.0F);
        this.LegRightFront = new ModelRenderer(this, 0, 80);
        this.LegRightFront.mirror = true;
        this.LegRightFront.setRotationPoint(-3.0F, 24.0F, -8.0F);
        this.LegRightFront.addBox(-3.0F, -4.0F, -5.0F, 8, 14, 8, 0.0F);
        this.TailBody = new ModelRenderer(this, 0, 68);
        this.TailBody.mirror = true;
        this.TailBody.setRotationPoint(-12.0F, -2.0F, 0.0F);
        this.TailBody.addBox(-8.0F, 2.0F, -8.0F, 18, 18, 14, 0.0F);
        this.setRotateAngle(TailBody, 0.0F, 0.0F, 0.25F);
        this.TailBack = new ModelRenderer(this, 128, 16);
        this.TailBack.mirror = true;
        this.TailBack.setRotationPoint(-12.0F, -2.0F, 0.0F);
        this.TailBack.addBox(-22.0F, -6.0F, -10.0F, 26, 16, 20, 0.0F);
        this.setRotateAngle(TailBack, 0.0F, 0.0F, 0.25F);
        this.ToothBottomRight = new ModelRenderer(this, 96, 19);
        this.ToothBottomRight.mirror = true;
        this.ToothBottomRight.setRotationPoint(-6.0F, 18.0F, 0.0F);
        this.ToothBottomRight.addBox(-1.0F, 7.5F, -9.0F, 4, 10, 3, 0.0F);
        this.setRotateAngle(ToothBottomRight, 0.0F, 0.0F, -0.20000000298023227F);
        this.ToothBottomMid = new ModelRenderer(this, 0, 0);
        this.ToothBottomMid.mirror = true;
        this.ToothBottomMid.setRotationPoint(-6.0F, 18.0F, 0.0F);
        this.ToothBottomMid.addBox(-1.0F, 14.5F, -6.0F, 4, 3, 12, 0.0F);
        this.setRotateAngle(ToothBottomMid, 0.0F, 0.0F, -0.20000000298023227F);
        this.Body = new ModelRenderer(this, 0, 64);
        this.Body.mirror = true;
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-10.0F, 10.0F, -11.0F, 24, 16, 22, 0.0F);
        this.LegRightEnd = new ModelRenderer(this, 0, 90);
        this.LegRightEnd.mirror = true;
        this.LegRightEnd.setRotationPoint(1.0F, 8.0F, -1.0F);
        this.LegRightEnd.addBox(-12.0F, -3.0F, -3.0F, 12, 6, 6, 0.0F);
        this.ToothTopRight = new ModelRenderer(this, 128, 54);
        this.ToothTopRight.mirror = true;
        this.ToothTopRight.setRotationPoint(26.0F, 0.0F, 0.0F);
        this.ToothTopRight.addBox(0.0F, 20.0F, -10.0F, 18, 6, 4, 0.0F);
        this.setRotateAngle(ToothTopRight, 0.0F, 0.0F, 0.30000001192092896F);
        this.JawBottom = new ModelRenderer(this, 92, 64);
        this.JawBottom.mirror = true;
        this.JawBottom.setRotationPoint(-6.0F, 18.0F, 0.0F);
        this.JawBottom.addBox(-3.0F, 0.0F, -10.0F, 3, 18, 20, 0.0F);
        this.setRotateAngle(JawBottom, 0.0F, 0.0F, -0.20000000298023227F);
        this.LegLeftEnd = new ModelRenderer(this, 0, 90);
        this.LegLeftEnd.mirror = true;
        this.LegLeftEnd.setRotationPoint(1.0F, 8.0F, 4.0F);
        this.LegLeftEnd.addBox(-12.0F, -3.0F, -4.0F, 12, 6, 6, 0.0F);
        this.NeckNeck = new ModelRenderer(this, 128, 28);
        this.NeckNeck.mirror = true;
        this.NeckNeck.setRotationPoint(15.0F, 0.0F, 0.0F);
        this.NeckNeck.addBox(6.0F, 15.0F, -10.0F, 21, 4, 20, 0.0F);
        this.setRotateAngle(NeckNeck, 0.0F, 0.0F, 0.20000000298023227F);
        this.NeckBack = new ModelRenderer(this, 128, 0);
        this.NeckBack.mirror = true;
        this.NeckBack.setRotationPoint(15.0F, 0.0F, 0.0F);
        this.NeckBack.addBox(-3.0F, -11.0F, -13.0F, 30, 26, 26, 0.0F);
        this.setRotateAngle(NeckBack, 0.0F, 0.0F, 0.20000000298023227F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.mirror = true;
        this.Head.setRotationPoint(26.0F, 0.0F, 0.0F);
        this.Head.addBox(-3.0F, -12.0F, -16.0F, 32, 32, 32, 0.0F);
        this.setRotateAngle(Head, 0.0F, 0.0F, 0.30000001192092896F);
        this.RightEye = new ModelRenderer(this, 138, 84);
        this.RightEye.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightEye.addBox(-3.0F, 0.0F, -16.1F, 24, 20, 0, 0.0F);
        this.ToothBottomLeft = new ModelRenderer(this, 96, 19);
        this.ToothBottomLeft.mirror = true;
        this.ToothBottomLeft.setRotationPoint(-6.0F, 18.0F, 0.0F);
        this.ToothBottomLeft.addBox(-1.0F, 7.5F, 6.0F, 4, 10, 3, 0.0F);
        this.setRotateAngle(ToothBottomLeft, 0.0F, 0.0F, -0.20000000298023227F);
        this.LegLeftFront = new ModelRenderer(this, 0, 80);
        this.LegLeftFront.mirror = true;
        this.LegLeftFront.setRotationPoint(-3.0F, 24.0F, 6.0F);
        this.LegLeftFront.addBox(-3.0F, -4.0F, -1.0F, 8, 14, 8, 0.0F);
        this.TailLeftEnd = new ModelRenderer(this, 128, 36);
        this.TailLeftEnd.mirror = true;
        this.TailLeftEnd.setRotationPoint(0.0F, 9.0F, 5.0F);
        this.TailLeftEnd.addBox(-24.0F, -4.0F, -2.0F, 24, 12, 4, 0.0F);
        this.setRotateAngle(TailLeftEnd, 0.0F, 0.0F, -0.40000000596046453F);
        this.TailRightFront = new ModelRenderer(this, 128, 28);
        this.TailRightFront.mirror = true;
        this.TailRightFront.setRotationPoint(-12.0F, 4.0F, -8.0F);
        this.TailRightFront.addBox(-8.0F, -4.0F, -6.0F, 12, 18, 6, 0.0F);
        this.setRotateAngle(TailRightFront, -0.5F, 0.0F, 0.25F);
        this.TailLeftFront = new ModelRenderer(this, 128, 28);
        this.TailLeftFront.mirror = true;
        this.TailLeftFront.setRotationPoint(-12.0F, 4.0F, 8.0F);
        this.TailLeftFront.addBox(-8.0F, -4.0F, 0.0F, 12, 18, 6, 0.0F);
        this.setRotateAngle(TailLeftFront, 0.5F, 0.0F, 0.25F);
        this.TailEnd = new ModelRenderer(this, 128, 26);
        this.TailEnd.mirror = true;
        this.TailEnd.setRotationPoint(-22.0F, 2.0F, 0.0F);
        this.TailEnd.addBox(-20.0F, -6.0F, -8.0F, 24, 10, 16, 0.0F);
        this.setRotateAngle(TailEnd, 0.0F, 0.0F, 0.30000001192092896F);
        this.NeckBack.addChild(this.ToothTopMid);
        this.TailRightFront.addChild(this.TailRightEnd);
        this.NeckBack.addChild(this.ToothTopLeft);
        this.Back.addChild(this.NeckBody);
        this.NeckBack.addChild(this.JawTop);
        this.Head.addChild(this.LeftEye);
        this.Body.addChild(this.LegRightFront);
        this.Back.addChild(this.TailBody);
        this.Back.addChild(this.TailBack);
        this.Head.addChild(this.ToothBottomRight);
        this.Head.addChild(this.ToothBottomMid);
        this.Back.addChild(this.Body);
        this.LegRightFront.addChild(this.LegRightEnd);
        this.NeckBack.addChild(this.ToothTopRight);
        this.Head.addChild(this.JawBottom);
        this.LegLeftFront.addChild(this.LegLeftEnd);
        this.Back.addChild(this.NeckNeck);
        this.Back.addChild(this.NeckBack);
        this.NeckBack.addChild(this.Head);
        this.Head.addChild(this.RightEye);
        this.Head.addChild(this.ToothBottomLeft);
        this.Body.addChild(this.LegLeftFront);
        this.TailLeftFront.addChild(this.TailLeftEnd);
        this.TailBack.addChild(this.TailRightFront);
        this.TailBack.addChild(this.TailLeftFront);
        this.TailBack.addChild(this.TailEnd);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Back.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
