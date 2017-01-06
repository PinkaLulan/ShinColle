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
 * ModelDestroyerNi - PinkaLulan 2015/3/11
 * Created using Tabula 4.1.1
 */
public class ModelDestroyerNi extends ModelBase implements IModelEmotion
{
    public ModelRenderer Back;
    public ModelRenderer NeckBack;
    public ModelRenderer Body;
    public ModelRenderer TailBack;
    public ModelRenderer Head;
    public ModelRenderer NeckBody;
    public ModelRenderer EquipBase;
    public ModelRenderer ArmLeft;
    public ModelRenderer ArmRight;
    public ModelRenderer k00;
    public ModelRenderer ToothU;
    public ModelRenderer Face00;
    public ModelRenderer Face01;
    public ModelRenderer Face02;
    public ModelRenderer k01;
    public ModelRenderer k02;
    public ModelRenderer k03;
    public ModelRenderer Equip01;
    public ModelRenderer Equip02;
    public ModelRenderer Equip03;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer TailEnd1;
    public ModelRenderer GlowBack;
    public ModelRenderer GlowNeckBack;
    public ModelRenderer GlowHead;

    
    public ModelDestroyerNi()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.Back = new ModelRenderer(this, 14, 76);
        this.Back.setRotationPoint(0.0F, -40.0F, 0.0F);
        this.Back.addBox(-12.0F, -12.0F, -14.0F, 24, 21, 26, 0.0F);
        this.setRotateAngle(Back, 0.7853981633974483F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 0, 33);
        this.Body.setRotationPoint(0.0F, 11.0F, -14.0F);
        this.Body.addBox(-10.0F, 0.0F, 0.0F, 20, 12, 24, 0.0F);
        this.setRotateAngle(Body, 0.36425021489121656F, 0.0F, 0.0F);
        this.Face02 = new ModelRenderer(this, 68, 0);
        this.Face02.setRotationPoint(0.0F, -14.1F, -27.0F);
        this.Face02.addBox(-10.0F, 0.0F, 0.0F, 20, 0, 20, 0.0F);
        this.Face00 = new ModelRenderer(this, 68, 40);
        this.Face00.setRotationPoint(0.0F, -14.3F, -27.0F);
        this.Face00.addBox(-10.0F, 0.0F, 0.0F, 20, 0, 20, 0.0F);
        this.k01 = new ModelRenderer(this, 106, 76);
        this.k01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.k01.addBox(1.0F, -18.5F, 1.0F, 3, 18, 8, 0.0F);
        this.setRotateAngle(k01, -0.5235987755982988F, 0.0F, 0.0F);
        this.NeckBack = new ModelRenderer(this, 10, 76);
        this.NeckBack.setRotationPoint(0.0F, -2.5F, -14.0F);
        this.NeckBack.addBox(-14.0F, -10.0F, -20.0F, 28, 25, 26, 0.0F);
        this.setRotateAngle(NeckBack, 0.08726646259971647F, 0.0F, 0.0F);
        this.Equip02 = new ModelRenderer(this, 54, 64);
        this.Equip02.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.Equip02.addBox(0.0F, 0.0F, 0.0F, 0, 28, 5, 0.0F);
        this.setRotateAngle(Equip02, 0.0F, 0.0F, 1.3089969389957472F);
        this.k02 = new ModelRenderer(this, 106, 76);
        this.k02.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.k02.addBox(0.8F, -25.0F, -0.7F, 3, 18, 8, 0.0F);
        this.setRotateAngle(k02, -1.3962634015954636F, 0.0F, 0.0F);
        this.TailEnd1 = new ModelRenderer(this, 28, 82);
        this.TailEnd1.setRotationPoint(0.0F, 0.0F, 19.0F);
        this.TailEnd1.addBox(-8.0F, -3.0F, 0.0F, 16, 13, 20, 0.0F);
        this.setRotateAngle(TailEnd1, -0.17453292519943295F, 0.0F, 0.0F);
        this.ToothU = new ModelRenderer(this, 0, 0);
        this.ToothU.setRotationPoint(0.0F, 7.0F, -29.0F);
        this.ToothU.addBox(-11.0F, 0.0F, 0.0F, 22, 9, 22, 0.0F);
        this.setRotateAngle(ToothU, 0.13962634015954636F, 0.0F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 2, 32);
        this.ArmLeft01.setRotationPoint(0.0F, 28.0F, 0.0F);
        this.ArmLeft01.addBox(-3.5F, 0.0F, -3.5F, 7, 30, 7, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.0F, 0.0F, 1.3962634015954636F);
        this.TailBack = new ModelRenderer(this, 22, 80);
        this.TailBack.setRotationPoint(0.0F, -7.0F, 9.0F);
        this.TailBack.addBox(-10.0F, -4.0F, 0.0F, 20, 17, 22, 0.0F);
        this.setRotateAngle(TailBack, -0.17453292519943295F, 0.0F, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 2, 32);
        this.ArmRight01.setRotationPoint(0.0F, 28.0F, 0.0F);
        this.ArmRight01.addBox(-3.5F, 0.0F, -3.5F, 7, 30, 7, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.0F, -1.3962634015954636F);
        this.NeckBody = new ModelRenderer(this, 1, 36);
        this.NeckBody.setRotationPoint(0.0F, 13.0F, -4.0F);
        this.NeckBody.addBox(-11.0F, 0.0F, -9.0F, 22, 10, 21, 0.0F);
        this.setRotateAngle(NeckBody, -0.31869712141416456F, 0.0F, 0.0F);
        this.k03 = new ModelRenderer(this, 106, 76);
        this.k03.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.k03.addBox(0.6F, -24.5F, -2.5F, 3, 18, 8, 0.0F);
        this.setRotateAngle(k03, -2.0943951023931953F, 0.0F, 0.0F);
        this.Equip03 = new ModelRenderer(this, 54, 64);
        this.Equip03.setRotationPoint(0.0F, 28.0F, 0.0F);
        this.Equip03.addBox(0.0F, 0.0F, 0.0F, 0, 32, 5, 0.0F);
        this.setRotateAngle(Equip03, 0.0F, 0.0F, -1.0471975511965976F);
        this.ArmRight = new ModelRenderer(this, 0, 31);
        this.ArmRight.setRotationPoint(-13.0F, 15.0F, -9.0F);
        this.ArmRight.addBox(-4.0F, 0.0F, -4.0F, 8, 30, 8, 0.0F);
        this.setRotateAngle(ArmRight, -0.5235987755982988F, 0.6981317007977318F, 1.0471975511965976F);
        this.Equip01 = new ModelRenderer(this, 54, 64);
        this.Equip01.setRotationPoint(18.0F, 13.0F, 9.0F);
        this.Equip01.addBox(0.0F, 0.0F, 0.0F, 0, 24, 5, 0.0F);
        this.setRotateAngle(Equip01, 1.0471975511965976F, 0.7853981633974483F, 0.0F);
        this.EquipBase = new ModelRenderer(this, 11, 89);
        this.EquipBase.setRotationPoint(0.0F, 11.0F, -26.0F);
        this.EquipBase.addBox(-20.0F, 0.0F, 0.0F, 40, 13, 13, 0.0F);
        this.k00 = new ModelRenderer(this, 100, 60);
        this.k00.setRotationPoint(14.0F, -12.0F, 0.0F);
        this.k00.addBox(0.0F, 0.0F, 0.0F, 5, 8, 8, 0.0F);
        this.setRotateAngle(k00, -0.3490658503988659F, 0.2617993877991494F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 70);
        this.Head.setRotationPoint(0.0F, 3.0F, -19.0F);
        this.Head.addBox(-16.0F, -14.0F, -28.0F, 32, 22, 32, 0.0F);
        this.setRotateAngle(Head, 0.08726646259971647F, 0.0F, 0.0F);
        this.ArmLeft = new ModelRenderer(this, 0, 31);
        this.ArmLeft.setRotationPoint(13.0F, 15.0F, -9.0F);
        this.ArmLeft.addBox(-4.0F, 0.0F, -4.0F, 8, 30, 8, 0.0F);
        this.setRotateAngle(ArmLeft, -0.5235987755982988F, -0.6981317007977318F, -1.0471975511965976F);
        this.Face01 = new ModelRenderer(this, 68, 20);
        this.Face01.setRotationPoint(0.0F, -14.2F, -27.0F);
        this.Face01.addBox(-10.0F, 0.0F, 0.0F, 20, 0, 20, 0.0F);
        this.Back.addChild(this.Body);
        this.Back.addChild(this.NeckBack);
        this.Equip01.addChild(this.Equip02);
        this.TailBack.addChild(this.TailEnd1);
        this.Head.addChild(this.ToothU);
        this.ArmLeft.addChild(this.ArmLeft01);
        this.Back.addChild(this.TailBack);
        this.ArmRight.addChild(this.ArmRight01);
        this.NeckBack.addChild(this.NeckBody);
        this.Equip02.addChild(this.Equip03);
        this.NeckBack.addChild(this.ArmRight);
        this.EquipBase.addChild(this.Equip01);
        this.NeckBack.addChild(this.EquipBase);
        this.NeckBack.addChild(this.Head);
        this.NeckBack.addChild(this.ArmLeft);
        
        //發光支架
        this.GlowBack = new ModelRenderer(this, 0, 0);
        this.GlowBack.setRotationPoint(0.0F, -40.0F, 0.0F);
        this.setRotateAngle(GlowBack, 0.7853981633974483F, 0.0F, 0.0F);
        this.GlowNeckBack = new ModelRenderer(this, 0, 0);
        this.GlowNeckBack.setRotationPoint(0.0F, -2.5F, -14.0F);
        this.setRotateAngle(GlowNeckBack, 0.08726646259971647F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, 3.0F, -19.0F);
        this.setRotateAngle(GlowHead, 0.08726646259971647F, 0.0F, 0.0F);
        
        this.GlowBack.addChild(this.GlowNeckBack);
        this.GlowNeckBack.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face00);
        this.GlowHead.addChild(this.Face01);
        this.GlowHead.addChild(this.Face02);
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
    	GlStateManager.translate(0F, 1.1F, 0F);
    	GlStateManager.scale(0.35F, 0.35F, 0.35F);
    	
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
			Back.rotateAngleX = 0.7854F;	       
			ArmLeft.rotateAngleX = -0.5F;
			ArmLeft.rotateAngleY = -0.7F;
			ArmLeft.rotateAngleZ = -1.2217F;
			ArmRight.rotateAngleX = -0.5F;
			ArmRight.rotateAngleY = 0.7F;
			ArmRight.rotateAngleZ = 1.2217F;
			ArmLeft01.rotateAngleX = 0F;
			ArmLeft01.rotateAngleZ = 1.4F;
			ArmRight01.rotateAngleX = 0F;
			ArmRight01.rotateAngleZ = -1.4F;
	  		Equip01.rotateAngleX = 1F;
			
			isKisaragi(ent);   
			rollEmotion(ent);    
			motionWatch(f3, f4, angleX);	//include watch head & normal head
			motionTail(angleX);
			
			if (ent.isSitting())
			{
				motionSit(ent, angleX);
			}
			else
			{
				motionLeg(f, f1);
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
    	GlStateManager.translate(0F, 0.75F, 0F);
    	setFace(2);
    	isKisaragi(ent);
    	
    	//org pose
  		NeckBack.rotateAngleX = 0.3F;
	    NeckBack.rotateAngleY = 0F;
	    Head.rotateAngleX = 0.3F;
	    Head.rotateAngleY = 0F;

	    Equip01.rotateAngleY = 0.5F;
		Equip02.rotateAngleZ = 1F;
		Equip03.rotateAngleZ = -0.8F;
		
  		Back.rotateAngleX = -0.3236F;
		ArmLeft.rotateAngleX = -1.4F;
		ArmLeft.rotateAngleY = -0.7F;
		ArmLeft.rotateAngleZ = -0.2618F;
		ArmRight.rotateAngleX = -1.4F;
		ArmRight.rotateAngleY = 0.9F;
		ArmRight.rotateAngleZ = 0.2618F;
		ArmLeft01.rotateAngleX = 0F;
		ArmLeft01.rotateAngleZ = 1.2F;
		ArmRight01.rotateAngleX = 0F;
		ArmRight01.rotateAngleZ = -0.8F;
		TailBack.rotateAngleX = -0.1F;
	    TailEnd1.rotateAngleX = 0.05F;
  		Equip01.rotateAngleX = 2F;
    }
    
    private void motionLeg(float f, float f1)
    {
    	float angle1 = MathHelper.cos(f * 0.6662F) * 1.1F * f1;
	    
    	ArmLeft.rotateAngleX = angle1 - 0.5F;
    	ArmRight.rotateAngleX = -angle1 - 0.5F;
    }
    
    //坐下動作
  	private void motionSit(BasicEntityShip ent, float angleX)
  	{
  		if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
  		{
  			GlStateManager.translate(0F, angleX * 0.2F - 0.05F, angleX * 0.2F);
	  		ArmLeft.rotateAngleZ = -angleX * 0.6F - 1.0472F;
	  		ArmLeft01.rotateAngleZ = angleX * 0.5F + 1.2F;
			ArmRight.rotateAngleZ = angleX * 0.6F + 1.0472F;
			ArmRight01.rotateAngleZ = -angleX * 0.5F - 1.2F;
			TailBack.rotateAngleX = angleX * 0.1F + 0.2F;
		    TailEnd1.rotateAngleX = angleX * 0.1F + 0.2F;
  		}
  		else
  		{
  			GlStateManager.translate(0F, 0.75F, 0F);
	  		Back.rotateAngleX = -0.5236F;
			ArmLeft.rotateAngleX = -0.6981F;
			ArmLeft.rotateAngleY = -0.2618F;
			ArmLeft.rotateAngleZ = -0.2618F;
			ArmRight.rotateAngleX = -0.6981F;
			ArmRight.rotateAngleY = 0.2618F;
			ArmRight.rotateAngleZ = 0.2618F;
			ArmLeft01.rotateAngleX = -1.9199F;
			ArmLeft01.rotateAngleZ = -0.6981F;
			ArmRight01.rotateAngleX = -1.9199F;
			ArmRight01.rotateAngleZ = 0.6981F;
			TailBack.rotateAngleX = angleX * 0.1F + 0.2F;
		    TailEnd1.rotateAngleX = angleX * 0.1F + 0.2F;
	  		Equip01.rotateAngleX = 2F;
  		}
  	}
    
    //常時擺動尾巴
  	private void motionTail(float angleX)
  	{
  		TailBack.rotateAngleX = angleX * 0.2F;
	    TailEnd1.rotateAngleX = angleX * 0.2F;
	    Equip01.rotateAngleY = angleX * 0.2F + 0.5F;
		Equip02.rotateAngleZ = angleX * 0.3F + 1F;
		Equip03.rotateAngleZ = angleX * 0.4F - 0.8F;
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
			Face00.isHidden = false;
			Face01.isHidden = true;
			Face02.isHidden = true;
		break;
		case 1:
			Face00.isHidden = true;
			Face01.isHidden = false;
			Face02.isHidden = true;
		break;
		case 2:
			Face00.isHidden = true;
			Face01.isHidden = true;
			Face02.isHidden = false;
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