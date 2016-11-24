package com.lulan.shincolle.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.EmotionHelper;

/**
 * ModelDestroyerHa - PinkaLulan 2015/3/10
 * Created using Tabula 4.1.1
 */
public class ModelDestroyerHa extends ModelBase implements IModelEmotion {
	public ModelRenderer Back;
    public ModelRenderer NeckBack;
    public ModelRenderer Body;
    public ModelRenderer TailBack;
    public ModelRenderer Head;
    public ModelRenderer NeckBody;
    public ModelRenderer HeadD01;
    public ModelRenderer k00;
    public ModelRenderer ToothU;
    public ModelRenderer Face00;
    public ModelRenderer Face01;
    public ModelRenderer Face02;
    public ModelRenderer HeadD02;
    public ModelRenderer ToothL;
    public ModelRenderer HeadD03;
    public ModelRenderer k01;
    public ModelRenderer k02;
    public ModelRenderer k03;
    public ModelRenderer LegLeftFront;
    public ModelRenderer LegRightFront;
    public ModelRenderer LegLeftEnd;
    public ModelRenderer LegRightEnd;
    public ModelRenderer TailEnd1;
    public ModelRenderer TailEnd2;
    public ModelRenderer GlowBack;
    public ModelRenderer GlowNeckBack;
    public ModelRenderer GlowHead;

    public ModelDestroyerHa() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.TailBack = new ModelRenderer(this, 30, 79);
        this.TailBack.setRotationPoint(0.0F, -7.0F, 9.0F);
        this.TailBack.addBox(-10.0F, -4.0F, 0.0F, 20, 17, 22, 0.0F);
        this.setRotateAngle(TailBack, 0.08726646259971647F, 0.0F, 0.0F);
        this.ToothL = new ModelRenderer(this, 0, 0);
        this.ToothL.setRotationPoint(0.0F, 1.0F, 0.5F);
        this.ToothL.addBox(-11.0F, 0.0F, -22.0F, 22, 7, 22, 0.0F);
        this.ToothL.mirror = true;
        this.setRotateAngle(ToothL, -3.089232776029963F, -3.141592653589793F, 0.0F);
        this.k01 = new ModelRenderer(this, 90, 0);
        this.k01.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.k01.addBox(1.0F, -18.5F, 1.0F, 3, 18, 8, 0.0F);
        this.setRotateAngle(k01, -0.5235987755982988F, 0.0F, 0.0F);
        this.LegRightFront = new ModelRenderer(this, 66, 46);
        this.LegRightFront.setRotationPoint(-12.0F, 7.0F, 14.0F);
        this.LegRightFront.addBox(-5.0F, -4.0F, -5.0F, 10, 16, 10, 0.0F);
        this.setRotateAngle(LegRightFront, -0.5235987755982988F, 0.0F, 0.0F);
        this.k02 = new ModelRenderer(this, 90, 0);
        this.k02.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.k02.addBox(0.8F, -25.0F, -0.7F, 3, 18, 8, 0.0F);
        this.setRotateAngle(k02, -1.3962634015954636F, 0.0F, 0.0F);
        this.NeckBack = new ModelRenderer(this, 24, 79);
        this.NeckBack.setRotationPoint(0.0F, -2.5F, -11.0F);
        this.NeckBack.addBox(-13.0F, -10.0F, -20.0F, 26, 26, 22, 0.0F);
        this.setRotateAngle(NeckBack, -0.0873F, 0.0F, 0.0F);
        this.LegLeftFront = new ModelRenderer(this, 66, 46);
        this.LegLeftFront.setRotationPoint(12.0F, 7.0F, 14.0F);
        this.LegLeftFront.addBox(-5.0F, -4.0F, -5.0F, 10, 16, 10, 0.0F);
        this.setRotateAngle(LegLeftFront, -0.5235987755982988F, 0.0F, 0.0F);
        this.LegLeftEnd = new ModelRenderer(this, 70, 48);
        this.LegLeftEnd.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.LegLeftEnd.addBox(-4.0F, -3.0F, -4.0F, 8, 16, 8, 0.0F);
        this.setRotateAngle(LegLeftEnd, 0.6981317007977318F, 0.0F, 0.0F);
        this.Back = new ModelRenderer(this, 20, 73);
        this.Back.setRotationPoint(0.0F, -22.0F, 0.0F);
        this.Back.addBox(-12.0F, -12.0F, -14.0F, 24, 22, 28, 0.0F);
        this.k03 = new ModelRenderer(this, 90, 0);
        this.k03.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.k03.addBox(0.6F, -24.5F, -2.5F, 3, 18, 8, 0.0F);
        this.setRotateAngle(k03, -2.0943951023931953F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 16, 75);
        this.Head.setRotationPoint(0.0F, 3.0F, -13.0F);
        this.Head.addBox(-13.5F, -14.0F, -28.0F, 27, 27, 26, 0.0F);
        this.setRotateAngle(Head, -0.17453292519943295F, 0.0F, 0.0F);
        this.ToothU = new ModelRenderer(this, 0, 0);
        this.ToothU.setRotationPoint(0.0F, 12.5F, -28.5F);
        this.ToothU.addBox(-11.0F, 0.0F, 0.0F, 22, 7, 22, 0.0F);
        this.setRotateAngle(ToothU, 0.05235987755982988F, 0.0F, 0.0F);
        this.Body = new ModelRenderer(this, 44, 32);
        this.Body.setRotationPoint(0.0F, 11.0F, -18.0F);
        this.Body.addBox(-9.0F, 0.0F, 0.0F, 18, 14, 24, 0.0F);
        this.setRotateAngle(Body, 0.17453292519943295F, 0.0F, 0.0F);
        this.TailEnd1 = new ModelRenderer(this, 36, 81);
        this.TailEnd1.setRotationPoint(0.0F, 0.0F, 19.0F);
        this.TailEnd1.addBox(-8.0F, -3.0F, 0.0F, 16, 12, 20, 0.0F);
        this.setRotateAngle(TailEnd1, 0.17453292519943295F, 0.0F, 0.0F);
        this.HeadD01 = new ModelRenderer(this, 45, 94);
        this.HeadD01.setRotationPoint(0.0F, 12.0F, -3.0F);
        this.HeadD01.addBox(-12.0F, 0.0F, -3.0F, 24, 16, 7, 0.0F);
        this.setRotateAngle(HeadD01, -0.13962634015954636F, 0.0F, 0.0F);
        this.k00 = new ModelRenderer(this, 102, 84);
        this.k00.setRotationPoint(13.0F, -8.0F, -10.0F);
        this.k00.addBox(0.0F, 0.0F, 0.0F, 5, 8, 8, 0.0F);
        this.setRotateAngle(k00, 0.0F, 0.17453292519943295F, 0.0F);
        this.NeckBody = new ModelRenderer(this, 46, 34);
        this.NeckBody.setRotationPoint(0.0F, 15.0F, -8.0F);
        this.NeckBody.addBox(-9.0F, 0.0F, -9.0F, 18, 11, 22, 0.0F);
        this.HeadD02 = new ModelRenderer(this, 27, 77);
        this.HeadD02.setRotationPoint(0.0F, 9.5F, -1.5F);
        this.HeadD02.addBox(-10.5F, 0.0F, -21.0F, 21, 8, 24, 0.0F);
        this.setRotateAngle(HeadD02, 0.3490658503988659F, 0.0F, 0.0F);
        this.HeadD03 = new ModelRenderer(this, 44, 83);
        this.HeadD03.setRotationPoint(0.0F, 5.0F, -28.0F);
        this.HeadD03.addBox(-5.0F, 0.0F, 0.0F, 10, 10, 18, 0.0F);
        this.setRotateAngle(HeadD03, 0.3490658503988659F, 0.0F, 0.0F);
        this.LegRightEnd = new ModelRenderer(this, 70, 48);
        this.LegRightEnd.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.LegRightEnd.addBox(-4.0F, -3.0F, -4.0F, 8, 16, 8, 0.0F);
        this.setRotateAngle(LegRightEnd, 0.6981317007977318F, 0.0F, 0.0F);
        this.TailEnd2 = new ModelRenderer(this, 42, 85);
        this.TailEnd2.setRotationPoint(0.0F, 8.0F, 20.0F);
        this.TailEnd2.addBox(-7.0F, -5.0F, 0.0F, 14, 10, 16, 0.0F);
        this.setRotateAngle(TailEnd2, -0.5235987755982988F, 0.0F, 0.0F);
        this.Face00 = new ModelRenderer(this, 0, 81);
        this.Face00.setRotationPoint(0.0F, -12.0F, -28.1F);
        this.Face00.addBox(-10.0F, 0.0F, 0.0F, 20, 20, 0, 0.0F);
        this.Face01 = new ModelRenderer(this, 0, 61);
        this.Face01.setRotationPoint(0.0F, -12.0F, -28.2F);
        this.Face01.addBox(-10.0F, 0.0F, 0.0F, 20, 20, 0, 0.0F);
        this.Face02 = new ModelRenderer(this, 0, 41);
        this.Face02.setRotationPoint(0.0F, -12.0F, -28.3F);
        this.Face02.addBox(-10.0F, 0.0F, 0.0F, 20, 20, 0, 0.0F);
        this.Back.addChild(this.TailBack);
        this.HeadD02.addChild(this.ToothL);
        this.Body.addChild(this.LegRightFront);
        this.Back.addChild(this.NeckBack);
        this.Body.addChild(this.LegLeftFront);
        this.LegLeftFront.addChild(this.LegLeftEnd);
        this.NeckBack.addChild(this.Head);
        this.Head.addChild(this.ToothU);
        this.Back.addChild(this.Body);
        this.TailBack.addChild(this.TailEnd1);
        this.Head.addChild(this.HeadD01);
        this.NeckBack.addChild(this.NeckBody);
        this.HeadD01.addChild(this.HeadD02);
        this.HeadD02.addChild(this.HeadD03);
        this.LegRightFront.addChild(this.LegRightEnd);
        this.TailBack.addChild(this.TailEnd2);
        
        this.GlowBack = new ModelRenderer(this, 0, 0);
        this.GlowBack.setRotationPoint(0.0F, -22.0F, 0.0F);
        this.GlowNeckBack = new ModelRenderer(this, 0, 0);
        this.GlowNeckBack.setRotationPoint(0.0F, -2.5F, -11.0F);
        this.setRotateAngle(GlowNeckBack, -0.0873F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, 3.0F, -13.0F);
        this.setRotateAngle(GlowHead, -0.17453292519943295F, 0.0F, 0.0F);
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
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);

    	GL11.glPushMatrix();
    	GL11.glTranslatef(0F, 1F, 0F);
    	GL11.glScalef(0.45F, 0.45F, 0.45F);	//debug用
    	
    	this.Back.render(f5);
    	
    	//亮度設為240
    	GL11.glDisable(GL11.GL_LIGHTING);
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBack.render(f5);
    	GL11.glEnable(GL11.GL_LIGHTING);

    	GL11.glPopMatrix();
    }
    
  //model animation
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		float angleX = MathHelper.cos(f2*0.125F);
		     
		BasicEntityShip ent = (BasicEntityShip) entity;
		
  		//水上漂浮
  		if(((IShipFloating)ent).getShipDepth() > 0) {
    		GL11.glTranslatef(0F, angleX * 0.1F - 0.025F, 0F);
    	}
		
		if(ent.getStateFlag(ID.F.NoFuel)) {
			motionStopPos(f, f1, f2, f3, f4, ent);
		}
		else {
			//org pose
			Back.rotateAngleX = -0.1F;
			Back.rotateAngleZ = 0F;
			NeckBack.rotateAngleX = -0.15F;
			NeckBack.rotateAngleY = 0;
			Head.rotateAngleX = -0.2F;
			Head.rotateAngleY = 0;
			LegLeftFront.rotateAngleZ = 0F;
			LegLeftEnd.rotateAngleZ = 0F;
			LegRightFront.rotateAngleZ = 0F;
			
			isKisaragi(ent);   
			rollEmotion(ent);    
			motionWatch(f3, f4, angleX);	//include watch head & normal head
			  
			if(ent.isSitting()) {
				motionSit(ent, f2);
			}
			else {
			  	motionTail(angleX);
			  	motionLeg(ent, f, f1);
			}
		}

		setGlowRotation();
    }
    
    //設定模型發光部份的rotation
    private void setGlowRotation() {
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
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, BasicEntityShip ent) {
    	GL11.glTranslatef(0F, 0.8F, 0F);
    	isKisaragi(ent);
		setFace(2);
		
		//body
		Back.rotateAngleX = 0F;
  		Back.rotateAngleZ = -1.66F;
  		NeckBack.rotateAngleX = 0.1745F;
		NeckBack.rotateAngleY = 0;
		Head.rotateAngleX = 0.1745F;
		Head.rotateAngleY = 0;
		HeadD01.rotateAngleX = 0.1745F;
		//tail
		TailBack.rotateAngleX = 0.4F;
	    TailBack.rotateAngleY = 0F;
	    TailEnd1.rotateAngleX = 0.4F;
	    TailEnd1.rotateAngleY = 0F;
		//leg
	    LegLeftFront.rotateAngleX = 0.35F;
	    LegLeftFront.rotateAngleZ = 0.52F;
	    LegLeftEnd.rotateAngleX = 0F;
	    LegLeftEnd.rotateAngleZ = 0.52F;
		LegRightFront.rotateAngleX = -0.2F;
		LegRightFront.rotateAngleZ = 0.087F;
		LegRightEnd.rotateAngleX = 0.52F;
    }
    
    //坐下動作
  	private void motionSit(BasicEntityShip ent, float f2) {
  		float angle1 = MathHelper.cos(f2 * 1F);
  		
  		if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
			setFace(1);
	  		GL11.glTranslatef(0F, 0.6F, 0F);
	  		Back.rotateAngleX = -0.8F;
	  		NeckBack.rotateAngleX = -0.2618F;
			Head.rotateAngleX = -0.2618F;
			HeadD01.rotateAngleX = -angle1 * 0.05F + 0.2618F;
			LegRightFront.rotateAngleX = -0.7F;
		    LegLeftFront.rotateAngleX = angle1 * 0.5F - 2.5F;
		    LegRightEnd.rotateAngleX = 0.35F;
		    LegLeftEnd.rotateAngleX = angle1 * 0.3F + 0.7F;
		    TailBack.rotateAngleX = 0.35F;
		    TailEnd1.rotateAngleX = 0.35F;
  		}
  		else {
  			GL11.glTranslatef(0F, 0.7F, 0F);
	  		Back.rotateAngleX = 0F;
	  		Back.rotateAngleZ = -1.5708F;
	  		NeckBack.rotateAngleX = 0.1745F;
			Head.rotateAngleX = 0.1745F;
			HeadD01.rotateAngleX = 0.1745F;
			LegRightFront.rotateAngleX = 0F;
		    LegLeftFront.rotateAngleX = 0.5F;
		    LegRightEnd.rotateAngleX = 1.7F;
		    LegLeftEnd.rotateAngleX = 1.5F;
		    TailBack.rotateAngleX = -0.7F;
		    TailEnd1.rotateAngleX = -0.5F;
  		}
  		
  	}
    
    //常時擺動尾巴
  	private void motionTail(float angleX) {
  		TailBack.rotateAngleX = angleX * 0.05F + 0.1745F;
	    TailEnd1.rotateAngleX = angleX * 0.1F + 0.2618F;
  	}
    
    //雙腳移動計算
  	private void motionLeg(BasicEntityShip ent, float f, float f1) {
  		float angle1 = MathHelper.cos(f * 0.6662F) * 0.5F * f1;
  		float angle2 = MathHelper.sin(f * 0.6662F) * 0.5F * f1;
  		
  		LegRightFront.rotateAngleX = angle1 - 0.5F;
	    LegLeftFront.rotateAngleX = -angle1 - 0.5F;
	    LegRightEnd.rotateAngleX = angle2 + 1F;
	    LegLeftEnd.rotateAngleX = -angle2 + 1F;
	}
    
    //頭部看人轉動計算
  	private void motionWatch(float f3, float f4, float angleX) {
  		//移動頭部 使其看人, 不看人時持續擺動頭部
  	    if(f4 != 0) {
  	    	NeckBack.rotateAngleX = f4 / 200F; 	//上下角度
  		    NeckBack.rotateAngleY = f3 / 200F;	//左右角度 角度轉成rad 即除以57.29578
  		    Head.rotateAngleX = f4 / 200F;
  		    Head.rotateAngleY = f3 / 200F;
  		    HeadD01.rotateAngleX = angleX * 0.05F - 0.05F;
  		    TailBack.rotateAngleX = 0.15F;
  		    TailBack.rotateAngleY = f3 / -200F;	//尾巴以反方向擺動
  		    TailEnd1.rotateAngleX = 0.2F;
		    TailEnd1.rotateAngleY = f3 / -200F;
  	    }
  	    else {
  	    	HeadD01.rotateAngleX = angleX * 0.05F - 0.05F;
  	    }	
  	}
    
    private void isKisaragi(BasicEntityShip ent) {
		if(ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00) {
			k00.isHidden = false;
		}
		else {
			k00.isHidden = true;
		}
  	}
    
    //隨機抽取顯示的表情 
    private void rollEmotion(BasicEntityShip ent) {   	
    	switch(ent.getStateEmotion(ID.S.Emotion)) {
    	case ID.Emotion.BLINK:	//blink
    		EmotionHelper.EmotionBlink(this, ent);
    		break;
    	case ID.Emotion.T_T:	//cry
    	case ID.Emotion.O_O:
    	case ID.Emotion.HUNGRY:
    		if(ent.getFaceTick() <= 0) setFace(2);
    		break;
    	case ID.Emotion.BORED:	//cry
    		if(ent.getFaceTick() <= 0) setFace(1);
    		break;
    	default:						//normal face
    		//reset face to 0 or blink if emotion time > 0
    		if(ent.getFaceTick() <= 0) {
    			setFace(0);
    		}
    		else {
    			EmotionHelper.EmotionBlink(this, ent);
    		}
    		//roll emotion (3 times) every 6 sec
    		//1 tick in entity = 3 tick in model class (20 vs 60 fps)
    		if(ent.ticksExisted % 120 == 0) {  			
        		int emotionRand = ent.getRNG().nextInt(10);   		
        		if(emotionRand > 7) {
        			EmotionHelper.EmotionBlink(this, ent);
        		} 		
        	}
    		break;
    	}	
    }
	
	//設定顯示的臉型
    @Override
  	public void setFace(int emo) {
		switch(emo) {
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
    
    
    
}

