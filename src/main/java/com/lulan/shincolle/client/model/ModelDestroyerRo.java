package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelDestroyerRo - PinkaLulan 2015/3/9
 * Created using Tabula 4.1.1
 */
public class ModelDestroyerRo extends ModelBase implements IModelEmotion
{
	public ModelRenderer Back;
    public ModelRenderer NeckBack;
    public ModelRenderer Body;
    public ModelRenderer TailBack;
    public ModelRenderer LegLeftFront;
    public ModelRenderer LegRightFront;
    public ModelRenderer BodyTurbine;
    public ModelRenderer Head;
    public ModelRenderer NeckBody;
    public ModelRenderer HeadD03;
    public ModelRenderer HeadU01;
    public ModelRenderer HeadD01;
    public ModelRenderer FaceL00;
    public ModelRenderer FaceL01;
    public ModelRenderer FaceL02;
    public ModelRenderer FaceR00;
    public ModelRenderer FaceR01;
    public ModelRenderer FaceR02;
    public ModelRenderer k00;
    public ModelRenderer HeadD04;
    public ModelRenderer UpperTooth;
    public ModelRenderer HeadU02;
    public ModelRenderer LowerTooth;
    public ModelRenderer k01;
    public ModelRenderer k02;
    public ModelRenderer k03;
    public ModelRenderer tube01;
    public ModelRenderer tube02;
    public ModelRenderer tube03;
    public ModelRenderer TailEnd;
    public ModelRenderer TailBack01;
    public ModelRenderer TailBack02;
    public ModelRenderer LegLeftEnd;
    public ModelRenderer LegRightEnd;
    public ModelRenderer GlowBack;
    public ModelRenderer GlowNeckBack;
    public ModelRenderer GlowHead;

    
    public ModelDestroyerRo()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.LowerTooth = new ModelRenderer(this, 0, 0);
        this.LowerTooth.setRotationPoint(0.0F, 9.5F, -5.5F);
        this.LowerTooth.addBox(-12.0F, 0.0F, 0.0F, 24, 10, 20, 0.0F);
        this.LowerTooth.mirror = true;
        this.setRotateAngle(LowerTooth, -3.490658503988659F, 0.0F, 0.0F);
        this.k02 = new ModelRenderer(this, 72, 102);
        this.k02.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.k02.addBox(0.8F, -25.0F, -0.7F, 3, 18, 8, 0.0F);
        this.setRotateAngle(k02, -1.3962634015954636F, 0.0F, 0.0F);
        this.TailBack = new ModelRenderer(this, 12, 38);
        this.TailBack.setRotationPoint(0.0F, -2.0F, 11.0F);
        this.TailBack.addBox(-10.0F, -8.0F, 0.0F, 20, 14, 22, 0.0F);
        this.setRotateAngle(TailBack, -0.08726646259971647F, 0.0F, 0.0F);
        this.LegRightEnd = new ModelRenderer(this, 24, 106);
        this.LegRightEnd.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.LegRightEnd.addBox(-3.0F, -3.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegRightEnd, 0.5235987755982988F, 0.0F, 0.0F);
        this.TailBack02 = new ModelRenderer(this, 30, 40);
        this.TailBack02.setRotationPoint(-8.0F, 0.0F, 15.0F);
        this.TailBack02.addBox(-2.0F, 0.0F, 0.0F, 4, 10, 20, 0.0F);
        this.setRotateAngle(TailBack02, -1.0471975511965976F, 0.0F, -0.40142572795869574F);
        this.LegRightFront = new ModelRenderer(this, 20, 104);
        this.LegRightFront.setRotationPoint(-7.8F, 12.0F, -3.0F);
        this.LegRightFront.addBox(-4.0F, -4.0F, -4.0F, 8, 16, 8, 0.0F);
        this.setRotateAngle(LegRightFront, 0.7853981633974483F, 0.0F, 0.0F);
        this.TailBack01 = new ModelRenderer(this, 30, 40);
        this.TailBack01.setRotationPoint(8.0F, 0.0F, 15.0F);
        this.TailBack01.addBox(-2.0F, 0.0F, 0.0F, 4, 10, 20, 0.0F);
        this.setRotateAngle(TailBack01, -1.0471975511965976F, 0.0F, 0.40142572795869574F);
        this.k03 = new ModelRenderer(this, 72, 102);
        this.k03.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.k03.addBox(0.6F, -24.5F, -2.5F, 3, 18, 8, 0.0F);
        this.setRotateAngle(k03, -2.0943951023931953F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 6, 42);
        this.Head.setRotationPoint(0.0F, 0.0F, -17.5F);
        this.Head.addBox(-15.0F, -12.0F, -16.0F, 30, 27, 18, 0.0F);
        this.setRotateAngle(Head, 0.2617993877991494F, 0.0F, 0.0F);
        this.HeadD03 = new ModelRenderer(this, 2, 94);
        this.HeadD03.setRotationPoint(0.0F, 10.3F, -23.0F);
        this.HeadD03.addBox(-8.5F, 0.0F, 0.0F, 17, 12, 11, 0.0F);
        this.setRotateAngle(HeadD03, -0.05235987755982988F, 0.0F, 0.0F);
        this.tube03 = new ModelRenderer(this, 24, 32);
        this.tube03.setRotationPoint(-1.0F, 1.5F, 18.0F);
        this.tube03.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 28, 0.0F);
        this.setRotateAngle(tube03, 1.0471975511965976F, -0.13962634015954636F, 0.0F);
        this.k00 = new ModelRenderer(this, 54, 94);
        this.k00.setRotationPoint(12.0F, -10.0F, 0.0F);
        this.k00.addBox(0.0F, 0.0F, 0.0F, 5, 8, 8, 0.0F);
        this.setRotateAngle(k00, 0.0F, 0.17453292519943295F, 0.0F);
        this.HeadD04 = new ModelRenderer(this, 2, 94);
        this.HeadD04.setRotationPoint(0.0F, 7.0F, -15.0F);
        this.HeadD04.addBox(-8.0F, 0.0F, 0.0F, 16, 12, 18, 0.0F);
        this.setRotateAngle(HeadD04, -0.2617993877991494F, 0.0F, 0.0F);
        this.HeadU02 = new ModelRenderer(this, 6, 40);
        this.HeadU02.setRotationPoint(0.0F, -20.0F, -23.0F);
        this.HeadU02.addBox(-14.0F, 0.0F, 0.0F, 28, 15, 20, 0.0F);
        this.setRotateAngle(HeadU02, 0.08726646259971647F, 0.0F, 0.0F);
        this.LegLeftEnd = new ModelRenderer(this, 24, 106);
        this.LegLeftEnd.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.LegLeftEnd.addBox(-3.0F, -3.0F, -3.0F, 6, 14, 6, 0.0F);
        this.setRotateAngle(LegLeftEnd, 0.5235987755982988F, 0.0F, 0.0F);
        this.NeckBody = new ModelRenderer(this, 0, 94);
        this.NeckBody.setRotationPoint(0.0F, 7.0F, -9.0F);
        this.NeckBody.addBox(-9.0F, 0.0F, -9.0F, 18, 14, 18, 0.0F);
        this.setRotateAngle(NeckBody, 0.3490658503988659F, 0.0F, 0.0F);
        this.tube01 = new ModelRenderer(this, 31, 40);
        this.tube01.setRotationPoint(0.0F, 12.0F, 3.0F);
        this.tube01.addBox(-1.5F, 0.0F, 0.0F, 3, 3, 20, 0.0F);
        this.setRotateAngle(tube01, -0.8726646259971648F, 0.0F, 0.0F);
        this.Back = new ModelRenderer(this, 2, 32);
        this.Back.setRotationPoint(0.0F, -16.0F, 0.0F);
        this.Back.addBox(-12.0F, -12.0F, -14.0F, 24, 22, 28, 0.0F);
        this.setRotateAngle(Back, -0.2617993877991494F, 0.0F, 0.0F);
        this.HeadU01 = new ModelRenderer(this, 6, 40);
        this.HeadU01.setRotationPoint(0.0F, 7.0F, -19.0F);
        this.HeadU01.addBox(-14.0F, -21.0F, -9.0F, 28, 16, 20, 0.0F);
        this.setRotateAngle(HeadU01, -0.08726646259971647F, 0.0F, 0.0F);
        this.TailEnd = new ModelRenderer(this, 14, 36);
        this.TailEnd.setRotationPoint(0.0F, 0.0F, 19.0F);
        this.TailEnd.addBox(-8.0F, -6.5F, 0.0F, 16, 10, 24, 0.0F);
        this.setRotateAngle(TailEnd, -0.08726646259971647F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 4, 96);
        this.Body.setRotationPoint(0.0F, 8.0F, -10.0F);
        this.Body.addBox(-8.0F, 0.0F, 0.0F, 16, 7, 16, 0.0F);
        this.setRotateAngle(Body, 0.5235987755982988F, 0.0F, 0.0F);
        this.LegLeftFront = new ModelRenderer(this, 20, 104);
        this.LegLeftFront.setRotationPoint(7.8F, 12.0F, -3.0F);
        this.LegLeftFront.addBox(-4.0F, -4.0F, -4.0F, 8, 16, 8, 0.0F);
        this.setRotateAngle(LegLeftFront, 0.7853981633974483F, 0.0F, 0.0F);
        this.HeadD01 = new ModelRenderer(this, 0, 34);
        this.HeadD01.setRotationPoint(0.0F, 1.0F, -10.3F);
        this.HeadD01.addBox(-13.0F, 1.5F, -25.0F, 26, 10, 28, 0.0F);
        this.setRotateAngle(HeadD01, 0.6981317007977318F, 0.0F, 0.0F);
        this.NeckBack = new ModelRenderer(this, 8, 40);
        this.NeckBack.setRotationPoint(0.0F, -3.0F, -12.0F);
        this.NeckBack.addBox(-13.0F, -11.0F, -20.0F, 26, 26, 22, 0.0F);
        this.setRotateAngle(NeckBack, 0.08726646259971647F, 0.0F, 0.0F);
        this.k01 = new ModelRenderer(this, 72, 102);
        this.k01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.k01.addBox(1.0F, -18.5F, 1.0F, 3, 18, 8, 0.0F);
        this.setRotateAngle(k01, -0.5235987755982988F, 0.0F, 0.0F);
        this.tube02 = new ModelRenderer(this, 24, 32);
        this.tube02.setRotationPoint(1.0F, 1.5F, 18.0F);
        this.tube02.addBox(-1.0F, 0.0F, 0.0F, 2, 2, 28, 0.0F);
        this.setRotateAngle(tube02, 1.0471975511965976F, 0.13962634015954636F, 0.0F);
        this.BodyTurbine = new ModelRenderer(this, 86, 89);
        this.BodyTurbine.setRotationPoint(0.0F, 7.0F, -2.0F);
        this.BodyTurbine.addBox(-4.5F, 0.0F, 0.0F, 9, 9, 12, 0.0F);
        this.setRotateAngle(BodyTurbine, -0.5235987755982988F, 0.0F, 0.0F);
        this.UpperTooth = new ModelRenderer(this, 0, 0);
        this.UpperTooth.setRotationPoint(0.0F, -6.0F, -15.0F);
        this.UpperTooth.addBox(-12.0F, 0.0F, 0.0F, 24, 10, 20, 0.0F);
        this.setRotateAngle(UpperTooth, 0.3490658503988659F, 0.0F, 0.0F);
        this.FaceL00 = new ModelRenderer(this, 96, 96);
        this.FaceL00.setRotationPoint(15.1F, -8.0F, -16.0F);
        this.FaceL00.addBox(0.0F, 0.0F, 0.0F, 0, 16, 16, 0.0F);
        this.FaceL01 = new ModelRenderer(this, 96, 0);
        this.FaceL01.setRotationPoint(15.1F, -8.0F, -16.0F);
        this.FaceL01.addBox(0.0F, 0.0F, 0.0F, 0, 16, 16, 0.0F);
        this.FaceL02 = new ModelRenderer(this, 96, 16);
        this.FaceL02.setRotationPoint(15.1F, -8.0F, -16.0F);
        this.FaceL02.addBox(0.0F, 0.0F, 0.0F, 0, 16, 16, 0.0F);
        this.FaceR00 = new ModelRenderer(this, 96, 96);
        this.FaceR00.setRotationPoint(-15.1F, -8.0F, -16.0F);
        this.FaceR00.addBox(0.0F, 0.0F, 0.0F, 0, 16, 16, 0.0F);
        this.FaceR01 = new ModelRenderer(this, 96, 0);
        this.FaceR01.setRotationPoint(-15.1F, -8.0F, -16.0F);
        this.FaceR01.addBox(0.0F, 0.0F, 0.0F, 0, 16, 16, 0.0F);
        this.FaceR02 = new ModelRenderer(this, 96, 16);
        this.FaceR02.setRotationPoint(-15.1F, -8.0F, -16.0F);
        this.FaceR02.addBox(0.0F, 0.0F, 0.0F, 0, 16, 16, 0.0F);
        this.HeadD01.addChild(this.LowerTooth);
        this.Back.addChild(this.TailBack);
        this.LegRightFront.addChild(this.LegRightEnd);
        this.TailBack.addChild(this.TailBack02);
        this.Back.addChild(this.LegRightFront);
        this.TailBack.addChild(this.TailBack01);
        this.NeckBack.addChild(this.Head);
        this.NeckBack.addChild(this.HeadD03);
        this.tube01.addChild(this.tube03);
        this.Head.addChild(this.HeadD04);
        this.HeadU01.addChild(this.HeadU02);
        this.LegLeftFront.addChild(this.LegLeftEnd);
        this.NeckBack.addChild(this.NeckBody);
        this.NeckBody.addChild(this.tube01);
        this.Head.addChild(this.HeadU01);
        this.TailBack.addChild(this.TailEnd);
        this.Back.addChild(this.Body);
        this.Back.addChild(this.LegLeftFront);
        this.Head.addChild(this.HeadD01);
        this.Back.addChild(this.NeckBack);
        this.tube01.addChild(this.tube02);
        this.Back.addChild(this.BodyTurbine);
        this.HeadU01.addChild(this.UpperTooth);
        
        //以下為發光模型支架, 此部份模型整個亮度設為240
        this.GlowBack = new ModelRenderer(this, 0, 0);
        this.GlowBack.setRotationPoint(0.0F, -16.0F, 0.0F);
        this.setRotateAngle(GlowBack, -0.2618F, 0.0F, 0.0F);
        this.GlowNeckBack = new ModelRenderer(this, 0, 0);
        this.GlowNeckBack.setRotationPoint(0.0F, -3.0F, -12.0F);
        this.setRotateAngle(GlowNeckBack, 0.0873F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, 0.0F, -17.5F);
        this.setRotateAngle(GlowHead, 0.2618F, 0.0F, 0.0F);
        
        this.GlowBack.addChild(this.GlowNeckBack);
        this.GlowNeckBack.addChild(this.GlowHead);
        this.GlowHead.addChild(this.FaceL00);
        this.GlowHead.addChild(this.FaceL01);
        this.GlowHead.addChild(this.FaceL02);
        this.GlowHead.addChild(this.FaceR00);
        this.GlowHead.addChild(this.FaceR01);
        this.GlowHead.addChild(this.FaceR02);
        this.GlowHead.addChild(this.k00);
        this.k00.addChild(this.k01);
        this.k00.addChild(this.k02);
        this.k00.addChild(this.k03);
        
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
    	GlStateManager.pushMatrix();
    	GlStateManager.scale(0.45F, 0.45F, 0.45F);
    	GlStateManager.translate(0F, 2.1F, 0F);
        
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.Back.render(f5);
    	
    	GlStateManager.disableLighting();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBack.render(f5);
    	GlStateManager.enableLighting();
    	
    	GlStateManager.popMatrix();
    }
    
    //model animation
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		float angleX = MathHelper.cos(f2*0.125F);
		     
		BasicEntityShip ent = (BasicEntityShip) entity;
		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
		  
		if (ent.getStateFlag(ID.F.NoFuel))
		{
			motionStopPos(f, f1, f2, f3, f4, ent);
		}
		else
		{
			//org pose
			Back.rotateAngleX = -0.2618F;
			Back.rotateAngleY = 0F;
			Back.rotateAngleZ = 0F;
			NeckBack.rotateAngleX = 0.0873F;
			Head.rotateAngleX = 0.3F;
			LegRightFront.rotateAngleY = 0F;
			LegLeftFront.rotateAngleY = 0F;
			
			isKisaragi(ent);   
			rollEmotion(ent);    
			motionWatch(f3, f4, angleX);	//include watch head & normal head
			  
			if (ent.isSitting())
			{
				motionSit(ent, angleX);
			}
			else
			{
			  	motionLeg(ent, f, f1, angleX);
			  	motionTail(angleX);
			}
		}
		
		setGlowRotation();
    }
    
    //設定模型發光部份的rotation
    private void setGlowRotation()
    {
		this.GlowBack.rotateAngleX = this.Back.rotateAngleX;
		this.GlowBack.rotateAngleY = this.Back.rotateAngleY;
		this.GlowBack.rotateAngleZ = this.Back.rotateAngleZ;
		this.GlowNeckBack.rotateAngleX = this.NeckBack.rotateAngleX;
		this.GlowNeckBack.rotateAngleY = this.NeckBack.rotateAngleY;
		this.GlowNeckBack.rotateAngleZ = this.NeckBack.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
	}
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, BasicEntityShip ent)
    {
    	GlStateManager.translate(0F, 0.45F, 0F);
    	isKisaragi(ent);
    	setFace(1);
    	
    	//head
  		HeadD01.rotateAngleX = 0.7F;
    	NeckBack.rotateAngleX = 0F;
    	NeckBack.rotateAngleY = 0.1F;
    	Head.rotateAngleX = 0.1F;
    	Head.rotateAngleY = 0.1F;
    	//body
    	Back.rotateAngleX = 0F;
		Back.rotateAngleY = 3.1415F;
		Back.rotateAngleZ = 3.1415F;
		//leg
    	LegRightFront.rotateAngleX = 1.57F;
    	LegRightFront.rotateAngleY = -0.52F;
	    LegLeftFront.rotateAngleX = 1.57F;
	    LegLeftFront.rotateAngleY = 0.52F;
	    LegRightEnd.rotateAngleX = 1F;
	    LegLeftEnd.rotateAngleX = 1F;
	    //tail
	    TailBack.rotateAngleX = 0.1F;
	    TailBack.rotateAngleY = -0.15F;
  	    TailEnd.rotateAngleX = 0.1F;
  	    TailEnd.rotateAngleY = -0.15F;
  	    tube01.rotateAngleX = -0.8F;
  	    tube01.rotateAngleY = -0.12F;
    }
    
    //坐下動作
  	private void motionSit(BasicEntityShip ent, float angleX)
  	{
  		GlStateManager.translate(0F, 0.45F, 0F);
  		
  		if  (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
  		{
			setFace(2);
			Back.rotateAngleX = 0F;
			Back.rotateAngleY = 3.1415F;
			Back.rotateAngleZ = 3.1415F;
			Head.rotateAngleX = angleX * 0.08F + 0.35F;
	    	LegRightFront.rotateAngleX = angleX * 0.3F + 0.5F;
		    LegLeftFront.rotateAngleX = -angleX * 0.3F + 0.5F;
		    LegRightEnd.rotateAngleX = angleX * 0.3F + 0.5F;
		    LegLeftEnd.rotateAngleX = -angleX * 0.3F + 0.5F;
		    TailBack.rotateAngleX = -0.3F;
		    TailBack.rotateAngleY = angleX * 0.3F;
	  	    TailEnd.rotateAngleX = -0.3F;
	  	    TailEnd.rotateAngleY = angleX * 0.5F;
	  	    tube01.rotateAngleX = -0.8F;
  		}
  		else
  		{
  			Back.rotateAngleX = -0.7F;
  			Head.rotateAngleX = angleX * 0.08F + 0.35F;
  	    	LegRightFront.rotateAngleX = -0.6981F;
  		    LegLeftFront.rotateAngleX = -0.6981F;
  		    LegRightEnd.rotateAngleX = 0.1745F;
  		    LegLeftEnd.rotateAngleX = 0.1745F;
  		    TailBack.rotateAngleX = 0.5F;
  		    TailBack.rotateAngleY = angleX * 0.3F;
  	  	    TailEnd.rotateAngleX = 0.6F;
  	  	    TailEnd.rotateAngleY = angleX * 0.5F;
  	  	    tube01.rotateAngleX = -0.6F;
  		}
  	}
    
    //常時擺動尾巴
  	private void motionTail(float angleX)
  	{
  		TailBack.rotateAngleX = angleX * 0.1F - 0.1F;
	    TailEnd.rotateAngleX = angleX * 0.25F - 0.1F;
  	}
    
    //雙腳移動計算
  	private void motionLeg(BasicEntityShip ent, float f, float f1, float angleX)
  	{
		if (ent.isSprinting() || f1 > 0.9F)
		{
			LegRightFront.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.4F * f1 + 1F;
		    LegLeftFront.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.1415927F) * 0.4F * f1 + 1F;
		    LegRightEnd.rotateAngleX = MathHelper.sin(f * 0.6662F) * f1 + 0.5F;
		    LegLeftEnd.rotateAngleX = MathHelper.sin(f * 0.6662F + 3.1415927F) * f1 + 0.5F;
		}
		else
		{
			LegRightFront.rotateAngleX = angleX * 0.3F + 0.8F;
		    LegLeftFront.rotateAngleX = -angleX * 0.3F + 0.8F;
		    LegRightEnd.rotateAngleX = angleX * 0.3F + 0.5F;
		    LegLeftEnd.rotateAngleX = -angleX * 0.3F + 0.5F;
		}  
	}
    
    //頭部看人轉動計算
  	private void motionWatch(float f3, float f4, float angleX)
  	{
  		//移動頭部 使其看人, 不看人時持續擺動頭部
  	    if (f4 != 0)
  	    {
  	    	NeckBack.rotateAngleX = f4 * 0.005F; 	//上下角度
  		    NeckBack.rotateAngleY = f3 * 0.005F;	//左右角度 角度轉成rad 即除以57.29578
  		    Head.rotateAngleX = f4 * 0.005F;
  		    Head.rotateAngleY = f3 * 0.005F;
  		    TailBack.rotateAngleX = 0.1F;
  		    TailBack.rotateAngleY = f3 * -0.005F;	//尾巴以反方向擺動
  		    TailEnd.rotateAngleX = 0.1F;
		    TailEnd.rotateAngleY = f3 * -0.005F;
		    tube01.rotateAngleX = f4 * -0.005F - 0.8727F;
		    tube01.rotateAngleY = f3 * -0.005F;
  	    }
  	    else
  	    {
  	    	Head.rotateAngleX = angleX * 0.08F + 0.3F;
  	  		HeadD01.rotateAngleX = angleX * 0.05F + 0.7F;
  	    	NeckBack.rotateAngleX = 0.0873F; 	//上下角度
  	    	NeckBack.rotateAngleY = 0;			//左右角度 角度轉成rad 即除以57.29578
  	    	Head.rotateAngleY = 0;
  	    	TailBack.rotateAngleY = 0;
  	    	TailEnd.rotateAngleY = 0;
  	    	tube01.rotateAngleX = -0.8727F;
  	    	tube01.rotateAngleY = 0;
  	    }	
  	}
    
    private void isKisaragi(BasicEntityShip ent)
    {
		if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00)
		{
			k00.isHidden = false;
		}
		else
		{
			k00.isHidden = true;
		}
  	}
    
  //隨機抽取顯示的表情 
    private void rollEmotion(BasicEntityShip ent)
    {   	
    	switch (ent.getStateEmotion(ID.S.Emotion))
    	{
    	case ID.Emotion.BLINK:	//blink
    		EmotionHelper.EmotionBlink(this, ent);
    	break;
    	case ID.Emotion.T_T:	//cry
    	case ID.Emotion.O_O:
    	case ID.Emotion.HUNGRY:
    		if (ent.getFaceTick() <= 0) setFace(2);
    	break;
    	case ID.Emotion.BORED:	//cry
    		if (ent.getFaceTick() <= 0) setFace(1);
    	break;
    	default:						//normal face
    		//reset face to 0 or blink if emotion time > 0
    		if (ent.getFaceTick() <= 0)
    		{
    			setFace(0);
    		}
    		else
    		{
    			EmotionHelper.EmotionBlink(this, ent);
    		}
    		//roll emotion (3 times) every 6 sec
    		//1 tick in entity = 3 tick in model class (20 vs 60 fps)
    		if (ent.ticksExisted % 120 == 0)
    		{  			
        		int emotionRand = ent.getRNG().nextInt(10);   		
        		if (emotionRand > 7)
        		{
        			EmotionHelper.EmotionBlink(this, ent);
        		} 		
        	}
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
			FaceL00.isHidden = false;
			FaceR00.isHidden = false;
			FaceL01.isHidden = true;
			FaceR01.isHidden = true;
			FaceL02.isHidden = true;
			FaceR02.isHidden = true;
		break;
		case 1:
			FaceL00.isHidden = true;
			FaceR00.isHidden = true;
			FaceL01.isHidden = false;
			FaceR01.isHidden = false;
			FaceL02.isHidden = true;
			FaceR02.isHidden = true;
		break;
		case 2:
			FaceL00.isHidden = true;
			FaceR00.isHidden = true;
			FaceL01.isHidden = true;
			FaceR01.isHidden = true;
			FaceL02.isHidden = false;
			FaceR02.isHidden = false;
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