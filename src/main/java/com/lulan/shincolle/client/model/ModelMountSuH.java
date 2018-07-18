package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
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
 * ModelMountSuH - PinkaLulan
 * Created using Tabula 6.0.0
 */
public class ModelMountSuH extends ModelBase implements IModelEmotion
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer Head01;
    public ModelRenderer Jaw;
    public ModelRenderer NeckFront;
    public ModelRenderer Body01;
    public ModelRenderer Head02;
    public ModelRenderer Head03;
    public ModelRenderer Head04;
    public ModelRenderer Head05;
    public ModelRenderer Head06;
    public ModelRenderer Head07a;
    public ModelRenderer HeadTooth;
    public ModelRenderer Eye01a;
    public ModelRenderer Eye01b;
    public ModelRenderer Eye02a;
    public ModelRenderer Eye02b;
    public ModelRenderer Eye03a;
    public ModelRenderer Eye03b;
    public ModelRenderer JawTooth;
    public ModelRenderer Jaw02;
    public ModelRenderer Body02;
    public ModelRenderer Body01a;
    public ModelRenderer Body02a;
    public ModelRenderer Body02b;
    public ModelRenderer Body03;
    public ModelRenderer Body03a;
    public ModelRenderer Body03b;
    public ModelRenderer Body04;
    public ModelRenderer Body04a;
    public ModelRenderer Body04b;
    public ModelRenderer Bridge02;
    public ModelRenderer Bridge01;
    public ModelRenderer Head07b;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowJaw;
    public ModelRenderer GlowHead01;

    
    public ModelMountSuH()
    {
    	
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.Eye03a = new ModelRenderer(this, 77, 16);
        this.Eye03a.setRotationPoint(9.6F, -9.0F, -15.0F);
        this.Eye03a.addBox(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
        this.Body04a = new ModelRenderer(this, 0, 0);
        this.Body04a.setRotationPoint(0.0F, -1.0F, 9.5F);
        this.Body04a.addBox(-2.5F, -6.0F, 0.0F, 5, 6, 5, 0.0F);
        this.Head07b = new ModelRenderer(this, 0, 4);
        this.Head07b.setRotationPoint(-0.7F, 5.5F, 0.7F);
        this.Head07b.addBox(-5.0F, 0.0F, -5.0F, 10, 12, 10, 0.0F);
        this.Body01a = new ModelRenderer(this, 0, 0);
        this.Body01a.setRotationPoint(0.0F, -20.7F, 0.0F);
        this.Body01a.addBox(-7.0F, 0.0F, 0.0F, 14, 9, 11, 0.0F);
        this.Head04 = new ModelRenderer(this, 0, 3);
        this.Head04.setRotationPoint(0.0F, -23.9F, -29.9F);
        this.Head04.addBox(-9.5F, 0.0F, 0.0F, 19, 8, 12, 0.0F);
        this.Head06 = new ModelRenderer(this, 0, 3);
        this.Head06.setRotationPoint(0.0F, -12.1F, -40.8F);
        this.Head06.addBox(-7.5F, 0.0F, 0.0F, 15, 6, 11, 0.0F);
        this.JawTooth = new ModelRenderer(this, 57, 46);
        this.JawTooth.mirror = true;
        this.JawTooth.setRotationPoint(0.0F, -1.7F, -2.0F);
        this.JawTooth.addBox(-6.5F, 0.0F, -14.0F, 13, 3, 14, 0.0F);
        this.setRotateAngle(JawTooth, -0.08726646259971647F, -0.02234021442552742F, 0.0F);
        this.Bridge02 = new ModelRenderer(this, 0, 0);
        this.Bridge02.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.Bridge02.addBox(-3.0F, 0.0F, 0.0F, 6, 10, 7, 0.0F);
        this.setRotateAngle(Bridge02, 1.5707963267948966F, 0.0F, 0.0F);
        this.HeadTooth = new ModelRenderer(this, 56, 45);
        this.HeadTooth.setRotationPoint(0.0F, 2.5F, -15.0F);
        this.HeadTooth.addBox(-6.5F, 0.0F, -6.5F, 13, 4, 15, 0.0F);
        this.setRotateAngle(HeadTooth, 0.05235987755982988F, 0.0F, 0.0F);
        this.Body03 = new ModelRenderer(this, 0, 0);
        this.Body03.setRotationPoint(0.0F, -3.0F, 8.0F);
        this.Body03.addBox(-8.0F, -10.0F, 0.0F, 16, 10, 12, 0.0F);
        this.setRotateAngle(Body03, -0.17453292519943295F, 0.0F, 0.0F);
        this.Eye03b = new ModelRenderer(this, 77, 16);
        this.Eye03b.setRotationPoint(-9.6F, -9.0F, -15.0F);
        this.Eye03b.addBox(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
        this.Body03b = new ModelRenderer(this, 0, 0);
        this.Body03b.setRotationPoint(0.0F, -19.9F, -2.0F);
        this.Body03b.addBox(-5.5F, 0.0F, 0.0F, 11, 10, 12, 0.0F);
        this.Bridge01 = new ModelRenderer(this, 0, 44);
        this.Bridge01.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.Bridge01.addBox(-3.5F, 0.0F, 0.0F, 7, 12, 8, 0.0F);
        this.setRotateAngle(Bridge01, 1.5707963267948966F, 0.0F, 0.0F);
        this.Body03a = new ModelRenderer(this, 0, 0);
        this.Body03a.setRotationPoint(0.0F, 4.0F, 4.5F);
        this.Body03a.addBox(-6.0F, -6.0F, 0.0F, 12, 6, 11, 0.0F);
        this.setRotateAngle(Body03a, 0.4363323129985824F, 0.0F, 0.0F);
        this.Body04b = new ModelRenderer(this, 0, 0);
        this.Body04b.setRotationPoint(0.0F, -15.6F, 6.0F);
        this.Body04b.addBox(-3.5F, 0.0F, 0.0F, 7, 9, 12, 0.0F);
        this.setRotateAngle(Body04b, -0.4363323129985824F, 0.0F, 0.0F);
        this.NeckFront = new ModelRenderer(this, 30, 48);
        this.NeckFront.setRotationPoint(0.0F, -8.5F, -15.0F);
        this.NeckFront.addBox(-5.5F, 0.0F, 0.0F, 11, 14, 2, 0.0F);
        this.Head02 = new ModelRenderer(this, 0, 3);
        this.Head02.setRotationPoint(0.0F, -16.0F, -29.9F);
        this.Head02.addBox(-9.5F, 0.0F, 0.0F, 19, 10, 12, 0.0F);
        this.Head07a = new ModelRenderer(this, 0, 4);
        this.Head07a.setRotationPoint(0.0F, -23.8F, -41.7F);
        this.Head07a.addBox(-6.0F, 0.0F, -6.0F, 12, 12, 12, 0.0F);
        this.setRotateAngle(Head07a, 0.0F, 0.7853981633974483F, 0.0F);
        this.Eye02b = new ModelRenderer(this, 77, 8);
        this.Eye02b.setRotationPoint(-9.6F, -9.0F, -15.0F);
        this.Eye02b.addBox(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
        this.Head03 = new ModelRenderer(this, 0, 3);
        this.Head03.setRotationPoint(0.0F, -23.9F, -18.0F);
        this.Head03.addBox(-9.5F, 0.0F, 0.0F, 19, 8, 12, 0.0F);
        this.Eye02a = new ModelRenderer(this, 77, 8);
        this.Eye02a.setRotationPoint(9.6F, -9.0F, -15.0F);
        this.Eye02a.addBox(0.0F, 0.0F, 0.1F, 0, 8, 8, 0.0F);
        this.Head05 = new ModelRenderer(this, 0, 3);
        this.Head05.setRotationPoint(0.0F, -24.0F, -41.8F);
        this.Head05.addBox(-8.5F, 0.0F, 0.0F, 17, 12, 12, 0.0F);
        this.Body04 = new ModelRenderer(this, 0, 0);
        this.Body04.setRotationPoint(0.0F, -1.0F, 11.0F);
        this.Body04.addBox(-4.5F, -8.0F, 0.0F, 9, 8, 10, 0.0F);
        this.setRotateAngle(Body04, 0.4363323129985824F, 0.0F, 0.0F);
        this.Jaw02 = new ModelRenderer(this, 0, 0);
        this.Jaw02.setRotationPoint(0.0F, 0.8F, -14.8F);
        this.Jaw02.addBox(-5.5F, 0.0F, -5.5F, 11, 5, 11, 0.0F);
        this.setRotateAngle(Jaw02, -0.33161255787892263F, 0.7853981633974483F, -0.2408554367752175F);
        this.Jaw = new ModelRenderer(this, 0, 0);
        this.Jaw.setRotationPoint(0.0F, 2.0F, -11.6F);
        this.Jaw.addBox(-7.5F, 0.0F, -16.0F, 15, 7, 16, 0.0F);
        this.setRotateAngle(Jaw, 0.2617993877991494F, 0.0F, 0.0F);
        this.Eye01b = new ModelRenderer(this, 77, 0);
        this.Eye01b.setRotationPoint(-9.6F, -9.0F, -15.0F);
        this.Eye01b.addBox(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
        this.Body01 = new ModelRenderer(this, 0, 0);
        this.Body01.setRotationPoint(0.0F, -3.0F, -8.3F);
        this.Body01.addBox(-8.5F, -12.0F, 0.0F, 17, 12, 12, 0.0F);
        this.Body02b = new ModelRenderer(this, 0, 3);
        this.Body02b.setRotationPoint(0.0F, -21.8F, -2.0F);
        this.Body02b.addBox(-4.5F, 0.0F, 0.0F, 9, 7, 12, 0.0F);
        this.Body02 = new ModelRenderer(this, 0, 3);
        this.Body02.setRotationPoint(0.0F, 5.0F, 6.0F);
        this.Body02.addBox(-7.0F, -15.0F, 0.0F, 14, 15, 12, 0.0F);
        this.setRotateAngle(Body02, -0.2617993877991494F, 0.0F, 0.0F);
        this.Body02a = new ModelRenderer(this, 0, 0);
        this.Body02a.setRotationPoint(0.0F, -2.1F, 0.0F);
        this.Body02a.addBox(-6.5F, 0.0F, 0.0F, 13, 7, 12, 0.0F);
        this.setRotateAngle(Body02a, 0.2617993877991494F, 0.0F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, 10.0F, 8.0F);
        this.BodyMain.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Eye01a = new ModelRenderer(this, 77, 0);
        this.Eye01a.setRotationPoint(9.6F, -9.0F, -15.0F);
        this.Eye01a.addBox(0.0F, 0.0F, 0.0F, 0, 8, 8, 0.0F);
        this.Head01 = new ModelRenderer(this, 0, 3);
        this.Head01.setRotationPoint(0.0F, -9.0F, -7.0F);
        this.Head01.addBox(-9.5F, -7.0F, -11.0F, 19, 10, 12, 0.0F);
        this.Neck = new ModelRenderer(this, 0, 0);
        this.Neck.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Neck.addBox(-7.0F, -7.5F, -14.0F, 14, 15, 14, 0.0F);
        this.Body04.addChild(this.Body04a);
        this.Head07a.addChild(this.Head07b);
        this.Body01.addChild(this.Body01a);
        this.Neck.addChild(this.Head04);
        this.Neck.addChild(this.Head06);
        this.Body01a.addChild(this.Bridge02);
        this.Body02.addChild(this.Body03);
        this.Body03.addChild(this.Body03b);
        this.Head03.addChild(this.Bridge01);
        this.Body03.addChild(this.Body03a);
        this.Body04.addChild(this.Body04b);
        this.Neck.addChild(this.Head02);
        this.Neck.addChild(this.Head07a);
        this.Neck.addChild(this.Head03);
        this.Neck.addChild(this.Head05);
        this.Body03.addChild(this.Body04);
        this.Jaw.addChild(this.Jaw02);
        this.Neck.addChild(this.Jaw);
        this.Neck.addChild(this.Body01);
        this.Body02.addChild(this.Body02b);
        this.Body01.addChild(this.Body02);
        this.Body02.addChild(this.Body02a);
        this.Neck.addChild(this.Head01);
        this.BodyMain.addChild(this.Neck);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, 10.0F, 8.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.GlowJaw = new ModelRenderer(this, 0, 0);
        this.GlowJaw.setRotationPoint(0.0F, 2.0F, -11.6F);
        this.setRotateAngle(GlowJaw, 0.2617993877991494F, 0.0F, 0.0F);
        this.GlowHead01 = new ModelRenderer(this, 0, 3);
        this.GlowHead01.setRotationPoint(0.0F, -9.0F, -7.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowJaw);
        this.GlowJaw.addChild(this.JawTooth);
        this.GlowNeck.addChild(this.GlowHead01);
        this.GlowHead01.addChild(this.HeadTooth);
        this.GlowNeck.addChild(this.NeckFront);
        this.GlowHead01.addChild(this.Eye01a);
        this.GlowHead01.addChild(this.Eye01b);
        this.GlowHead01.addChild(this.Eye02a);
        this.GlowHead01.addChild(this.Eye02b);
        this.GlowHead01.addChild(this.Eye03a);
        this.GlowHead01.addChild(this.Eye03b);
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
    	GlStateManager.scale(0.6F, 0.6F, 0.6F);
    	GlStateManager.translate(0F, 1F, 0F);
    	
    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	GlStateManager.disableBlend();
    	
    	//light part
    	GlStateManager.disableLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GlStateManager.enableLighting();
    	
    	GlStateManager.popMatrix();
    }
    
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		  
		IShipEmotion ent = (IShipEmotion)entity;
		
		EmotionHelper.rollEmotion(this, ent);
		  
		motionHumanPos(f, f1, f2, f3, f4, ent);
		
		syncRotationGlowPart();
    }
    
    //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
  	{   
  		float angleX = MathHelper.cos(f2 * 0.08F);
  		float angleX2 = MathHelper.cos(-f * 0.8F + 0.7F);
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.025F + 0.025F, 0F);
    	}

	    //idle
	  	this.Jaw.rotateAngleX = angleX * 0.075F + 0.26F;
	  	this.Body04.rotateAngleY = angleX * 0.15F;
	  	
	  	//running
	  	if (ent.getIsSprinting() || f1 > 0.9F)
	  	{
	  		this.Body03.rotateAngleY = angleX2 * 0.075F;
	  		this.Body04.rotateAngleY = angleX2 * 0.15F;
	  	}
  	}
  	
  	@Override
	public void syncRotationGlowPart()
	{
		//sync rotate
	    this.GlowJaw.rotateAngleX = this.Jaw.rotateAngleX;
	}
  	
	//設定顯示的臉型
  	@Override
   	public void setFace(int emo)
   	{
   		switch (emo)
   		{
   		case 0: // O_O
   			this.Eye01a.isHidden = true;
   			this.Eye01b.isHidden = true;
   			this.Eye02a.isHidden = false;
   			this.Eye02b.isHidden = false;
   			this.Eye03a.isHidden = true;
   			this.Eye03b.isHidden = true;
   		break;
   		case 1: // -_-
   			this.Eye01a.isHidden = true;
   			this.Eye01b.isHidden = true;
   			this.Eye02a.isHidden = true;
   			this.Eye02b.isHidden = true;
   			this.Eye03a.isHidden = false;
   			this.Eye03b.isHidden = false;
   		break;
   		default:// ><
   			this.Eye01a.isHidden = false;
   			this.Eye01b.isHidden = false;
   			this.Eye02a.isHidden = true;
   			this.Eye02b.isHidden = true;
   			this.Eye03a.isHidden = true;
   			this.Eye03b.isHidden = true;
   		break;
   		}
   	}

	@Override
	public void showEquip(IShipEmotion ent)
	{
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
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