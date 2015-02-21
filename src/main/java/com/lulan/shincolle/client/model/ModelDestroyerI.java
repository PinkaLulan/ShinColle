package com.lulan.shincolle.client.model;


import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.AttrValues;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import com.lulan.shincolle.entity.EntityDestroyerI;

/**Created in 2015/1/9
 * 注意: model class內所有方法都是fps 60模式
 *      也就是全部方法一秒都會call 60次, 跟entity內部一秒是20次的時間變化速度不同
 */
public class ModelDestroyerI extends ModelBase {
    //fields
	public ModelRenderer PBack;
	public ModelRenderer PNeck;
	public ModelRenderer PHead;
	public ModelRenderer[] PEyeLightL = new ModelRenderer[3];
	public ModelRenderer[] PEyeLightR = new ModelRenderer[3];
	public ModelRenderer PJawBottom;
	public ModelRenderer PBody;
	public ModelRenderer PLegLeft;
	public ModelRenderer PLegLeftEnd;
	public ModelRenderer PLegRight;
	public ModelRenderer PLegRightEnd;
	public ModelRenderer PTail;
	public ModelRenderer PTailLeft;
	public ModelRenderer PTailLeftEnd;
	public ModelRenderer PTailRight;
	public ModelRenderer PTailRightEnd;
	public ModelRenderer PTailEnd;
	//add Kisaragi
	public ModelRenderer PKisaragi00;
	public ModelRenderer PKisaragi01;
	public ModelRenderer PKisaragi02;
	public ModelRenderer PKisaragi03;
	//add glow model
	public ModelRenderer GlowPBack;
	public ModelRenderer GlowPNeck;
	public ModelRenderer GlowPHead;
	
	private int EntityEmotion;	//目前表情
	private int EntityFace;		//目前使用的頭部model
	private int EmotionTime;	//目前表情
	public float Scale;			//預設大小為1.0倍
	
  public ModelDestroyerI() {

    EntityEmotion = 0;
    EntityFace = 0;
    Scale = 1F;
    EmotionTime = 0;

    textureWidth = 256;
    textureHeight = 128;

    setTextureOffset("PBack.Back", 128, 8);
    setTextureOffset("PNeck.NeckBack", 128, 0);
    setTextureOffset("PNeck.NeckNeck", 128, 28);
    setTextureOffset("PNeck.NeckBody", 0, 70);
    setTextureOffset("PHead.Head", 0, 0);
    setTextureOffset("PHead.ToothTopMid", 96, 0);
    setTextureOffset("PHead.ToothTopRight", 128, 54);
    setTextureOffset("PHead.ToothTopLeft", 128, 54);
    setTextureOffset("PHead.JawTop", 0, 102);
    setTextureOffset("PJawBottom.JawBottom", 92, 64);
    setTextureOffset("PJawBottom.ToothBottomLeft", 96, 19);
    setTextureOffset("PJawBottom.ToothBottomRight", 96, 19);
    setTextureOffset("PJawBottom.ToothBottomMid", 0, 0);
    setTextureOffset("PBody.Body", 0, 64);
    setTextureOffset("PLegLeft.LegLeftFront", 0, 80);
    setTextureOffset("PLegLeftEnd.LegLeftEnd", 0, 90);
    setTextureOffset("PLegRight.LegRightFront", 0, 80);
    setTextureOffset("PLegRightEnd.LegRightEnd", 0, 90);
    setTextureOffset("PTail.TailBack", 128, 16);
    setTextureOffset("PTail.TailBody", 0, 68);
    setTextureOffset("PTailLeft.TailLeftFront", 128, 28);
    setTextureOffset("PTailLeftEnd.TailLeftEnd", 128, 36);
    setTextureOffset("PTailRight.TailRightFront", 128, 28);
    setTextureOffset("PTailRightEnd.TailRightEnd", 128, 36);
    setTextureOffset("PTailEnd.TailEnd", 128, 26);
    setTextureOffset("Eye01L", 138, 64);
    setTextureOffset("Eye02L", 138, 85);
    setTextureOffset("Eye03L", 138, 106);
    setTextureOffset("Eye01R", 138, 64);
    setTextureOffset("Eye02R", 138, 85);
    setTextureOffset("Eye03R", 138, 106);
    //add Kisaragi
    setTextureOffset("PKisaragi00.k00", 66, 102);
    setTextureOffset("PKisaragi01.k01", 114, 102);
    setTextureOffset("PKisaragi02.k02", 92, 102);
    setTextureOffset("PKisaragi03.k03", 92, 102);
    
    PBack = new ModelRenderer(this, "PBack");
    PBack.setRotationPoint(-8F, -16F, 0F);
    setRotation(PBack, 0F, 0F, -0.31F);
      PBack.addBox("Back", -12F, -10F, -12F, 28, 20, 24);
    PNeck = new ModelRenderer(this, "PNeck");
    PNeck.setRotationPoint(15F, 0F, 0F);
    setRotation(PNeck, 0F, 0F, 0.2F);
      PNeck.addBox("NeckBack", -3F, -11F, -13F, 30, 26, 26);
      PNeck.addBox("NeckNeck", 6F, 15F, -10F, 21, 4, 20);
      PNeck.addBox("NeckBody", -8F, 7F, -9F, 18, 14, 18);
    PHead = new ModelRenderer(this, "PHead");
    PHead.setRotationPoint(26F, 0F, 0F);
    setRotation(PHead, 0F, 0F, 0.3F);
      PHead.addBox("Head", -3F, -12F, -16F, 32, 32, 32);
      PHead.addBox("ToothTopMid", 14.5F, 20F, -6F, 4, 6, 12);
      PHead.addBox("ToothTopRight", 0F, 20F, -10F, 18, 6, 4);
      PHead.addBox("ToothTopLeft", 0F, 20F, 6F, 18, 6, 4);
      PHead.addBox("JawTop", -3F, 20F, -11F, 22, 2, 22);
      
    //3 emotion eye
    PEyeLightL[0] = new ModelRenderer(this, 138, 64);
    PEyeLightL[0].mirror = true;
    PEyeLightL[0].addBox(-3F, 0F, 15.1F, 24, 20, 1);
    PEyeLightR[0] = new ModelRenderer(this, 138, 64);
    PEyeLightR[0].addBox(-3F, 0F, -16.1F, 24, 20, 1);
    PEyeLightL[1] = new ModelRenderer(this, 138, 85);
    PEyeLightL[1].mirror = true;
    PEyeLightL[1].addBox(-3F, 0F, 15.1F, 24, 20, 1).isHidden = true;
    PEyeLightR[1] = new ModelRenderer(this, 138, 85);
    PEyeLightR[1].addBox(-3F, 0F, -16.1F, 24, 20, 1).isHidden = true;
    PEyeLightL[2] = new ModelRenderer(this, 138, 106);
    PEyeLightL[2].mirror = true;
    PEyeLightL[2].addBox(-3F, 0F, 15.1F, 24, 20, 1).isHidden = true;
    PEyeLightR[2] = new ModelRenderer(this, 138, 106);
    PEyeLightR[2].addBox(-3F, 0F, -16.1F, 24, 20, 1).isHidden = true;
    
    //add Kisaragi
    PKisaragi00 = new ModelRenderer(this, "PKisaragi00");
    PKisaragi01 = new ModelRenderer(this, "PKisaragi01");
    PKisaragi02 = new ModelRenderer(this, "PKisaragi02");
    PKisaragi03 = new ModelRenderer(this, "PKisaragi03");
    PKisaragi00.setRotationPoint(-7F, -9F, 14F);
    PKisaragi01.setRotationPoint(-7F, -9F, 14F);
    PKisaragi02.setRotationPoint(-7F, -9F, 14F);
    PKisaragi03.setRotationPoint(-7F, -9F, 14F);
    PKisaragi00.addBox("k00", 0F, 0F, 0F, 8, 8, 5);
    PKisaragi01.addBox("k01", -2F, -16F, 1F, 8, 20, 3);
    PKisaragi02.addBox("k02", -7F, -17F, 0.8F, 8, 18, 3);
    PKisaragi03.addBox("k03", -9F, -18F, 0.6F, 8, 18, 3);
    setRotation(PKisaragi01, 0F, 0F, -0.524F);
    setRotation(PKisaragi02, 0F, 0F, -1.396F);
    setRotation(PKisaragi03, 0F, 0F, -2.094F);
    
    PJawBottom = new ModelRenderer(this, "PJawBottom");
    PJawBottom.setRotationPoint(-6F, 18F, 0F);
    setRotation(PJawBottom, 0F, 0F, -0.2F);
      PJawBottom.addBox("JawBottom", -3F, 0F, -10F, 3, 18, 20);
      PJawBottom.addBox("ToothBottomLeft", -1F, 7.5F, 6F, 4, 10, 3);
      PJawBottom.addBox("ToothBottomRight", -1F, 7.5F, -9F, 4, 10, 3);
      PJawBottom.addBox("ToothBottomMid", -1F, 14.5F, -6F, 4, 3, 12);
      PHead.addChild(PJawBottom);
      PNeck.addChild(PHead);
      PBack.addChild(PNeck);
    PBody = new ModelRenderer(this, "PBody");
    PBody.setRotationPoint(0F, 0F, 0F);
      PBody.addBox("Body", -10F, 10F, -11F, 24, 16, 22);
    PLegLeft = new ModelRenderer(this, "PLegLeft");
    PLegLeft.setRotationPoint(-3F, 24F, 6F);
      PLegLeft.addBox("LegLeftFront", -3F, -4F, -1F, 8, 14, 8);
    PLegLeftEnd = new ModelRenderer(this, "PLegLeftEnd");
    PLegLeftEnd.setRotationPoint(1F, 8F, 4F);
      PLegLeftEnd.addBox("LegLeftEnd", -12F, -3F, -4F, 12, 6, 6);
      PLegLeft.addChild(PLegLeftEnd);
      PBody.addChild(PLegLeft);
    PLegRight = new ModelRenderer(this, "PLegRight");
    PLegRight.setRotationPoint(-3F, 24F, -8F);
      PLegRight.addBox("LegRightFront", -3F, -4F, -5F, 8, 14, 8);
    PLegRightEnd = new ModelRenderer(this, "PLegRightEnd");
    PLegRightEnd.setRotationPoint(1F, 8F, -1F);
      PLegRightEnd.addBox("LegRightEnd", -12F, -3F, -3F, 12, 6, 6);
      PLegRight.addChild(PLegRightEnd);
      PBody.addChild(PLegRight);
      PBack.addChild(PBody);
    PTail = new ModelRenderer(this, "PTail");
    PTail.setRotationPoint(-12F, -2F, 0F);
    setRotation(PTail, 0F, 0F, 0.25F);
      PTail.addBox("TailBack", -22F, -6F, -10F, 26, 16, 20);
      PTail.addBox("TailBody", -8F, 2F, -8F, 18, 18, 14);
    PTailLeft = new ModelRenderer(this, "PTailLeft");
    PTailLeft.setRotationPoint(-12F, 4F, 8F);
    setRotation(PTailLeft, 0.5F, 0F, 0.25F);
      PTailLeft.addBox("TailLeftFront", -8F, -4F, 0F, 12, 18, 6);
    PTailLeftEnd = new ModelRenderer(this, "PTailLeftEnd");
    PTailLeftEnd.setRotationPoint(0F, 9F, 5F);
   setRotation(PTailLeftEnd, 0F, 0F, -0.4F);
      PTailLeftEnd.addBox("TailLeftEnd", -24F, -4F, -2F, 24, 12, 4);
      PTailLeft.addChild(PTailLeftEnd);
      PTail.addChild(PTailLeft);
    PTailRight = new ModelRenderer(this, "PTailRight");
    PTailRight.setRotationPoint(-12F, 4F, -8F);
    setRotation(PTailRight, -0.5F, 0F, 0.25F);
      PTailRight.addBox("TailRightFront", -8F, -4F, -6F, 12, 18, 6);
    PTailRightEnd = new ModelRenderer(this, "PTailRightEnd");
    PTailRightEnd.setRotationPoint(0F, 9F, -5F);
    setRotation(PTailRightEnd, 0F, 0F, -0.4F);
      PTailRightEnd.addBox("TailRightEnd", -24F, -4F, -2F, 24, 12, 4);
      PTailRight.addChild(PTailRightEnd);
      PTail.addChild(PTailRight);
    PTailEnd = new ModelRenderer(this, "PTailEnd");
    PTailEnd.setRotationPoint(-22F, 2F, 0F);
    setRotation(PTailEnd, 0F, 0F, 0.3F);
      PTailEnd.addBox("TailEnd", -20F, -6F, -8F, 24, 10, 16);
      PTail.addChild(PTailEnd);
      PBack.addChild(PTail);
      
      //發光模型支架, 亮度為240
      GlowPBack = new ModelRenderer(this, "GlowPBack");
      GlowPBack.setRotationPoint(-8F, -16F, 0F);
      setRotation(GlowPBack, 0F, 0F, -0.31F);   
      GlowPNeck = new ModelRenderer(this, "GlowPNeck");
      GlowPNeck.setRotationPoint(15F, 0F, 0F);
      setRotation(GlowPNeck, 0F, 0F, 0.2F);
      GlowPHead = new ModelRenderer(this, "GlowPHead");
      GlowPHead.setRotationPoint(26F, 0F, 0F);
      setRotation(GlowPHead, 0F, 0F, 0.3F);
      
      GlowPHead.addChild(PEyeLightL[0]);
      GlowPHead.addChild(PEyeLightR[0]);
      GlowPHead.addChild(PEyeLightL[1]);
      GlowPHead.addChild(PEyeLightR[1]);
      GlowPHead.addChild(PEyeLightL[2]);
      GlowPHead.addChild(PEyeLightR[2]);
      GlowPHead.addChild(PKisaragi00);
      GlowPHead.addChild(PKisaragi01);
      GlowPHead.addChild(PKisaragi02);
      GlowPHead.addChild(PKisaragi03);
      GlowPNeck.addChild(GlowPHead);
      GlowPBack.addChild(GlowPNeck);  
      
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
	setRotationAngles(f, f1, f2, f3, f4, f5, entity);

	GL11.glPushMatrix();
//  Scale, Translate, Rotate
//  GL11.glScalef(this.scale, this.scale, this.scale);
//	GL11.glEnable(GL11.GL_BLEND);			//開啟透明度模式
//	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	GL11.glScalef(0.5F, 0.45F, 0.45F);	//debug用
	GL11.glRotatef(90F, 0F, 1F, 0F);	//此模型頭部方向錯誤 因此render時調整回來
	
	PBack.render(f5);
	
	//亮度設為240
	GL11.glDisable(GL11.GL_LIGHTING);
	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
	this.GlowPBack.render(f5);
	GL11.glEnable(GL11.GL_LIGHTING);
	
//	GL11.glDisable(GL11.GL_BLEND);		//關閉透明度模式
	GL11.glPopMatrix();
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  //for idle/run animation
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

    float angleZ = MathHelper.cos(f2*0.125F);
       
    EntityDestroyerI ent = (EntityDestroyerI)entity;
    
    isKisaragi(ent);   
    rollEmotion(ent);    
    motionWatch(f3,f4,angleZ);	//include watch head & normal head
    
    if(ent.isSitting()) {
    	motionSit(ent, angleZ);
    }
    else {
    	motionLeg(f,f1);
    	motionTail(angleZ);
    	
    	//reset sit pose
    	PBack.rotateAngleZ = -0.31F;
    	GL11.glTranslatef(0F, 0.8F, 0F);
    }   

    setGlowRotation();
  }

  	//設定發光模型rrotation
	private void setGlowRotation() {
		this.GlowPBack.rotateAngleX = this.PBack.rotateAngleX;
		this.GlowPBack.rotateAngleY = this.PBack.rotateAngleY;
		this.GlowPBack.rotateAngleZ = this.PBack.rotateAngleZ;
		this.GlowPHead.rotateAngleX = this.PHead.rotateAngleX;
		this.GlowPHead.rotateAngleY = this.PHead.rotateAngleY;
		this.GlowPHead.rotateAngleZ = this.PHead.rotateAngleZ;
		this.GlowPNeck.rotateAngleX = this.PNeck.rotateAngleX;
		this.GlowPNeck.rotateAngleY = this.PNeck.rotateAngleY;
		this.GlowPNeck.rotateAngleZ = this.PNeck.rotateAngleZ;
	}

	private void isKisaragi(EntityDestroyerI ent) {
		if(ent.getEntityState(AttrID.State) >= AttrValues.State.EQUIP) {
			PKisaragi00.isHidden = false;
			PKisaragi01.isHidden = false;
			PKisaragi02.isHidden = false;
			PKisaragi03.isHidden = false;
		}
		else {
			PKisaragi00.isHidden = true;
			PKisaragi01.isHidden = true;
			PKisaragi02.isHidden = true;
			PKisaragi03.isHidden = true;
		}
	
  	}
	
	//坐下動作
  	private void motionSit(EntityDestroyerI ent, float angleZ) {		
  		if(ent.getEntityState(AttrID.Emotion) == AttrValues.Emotion.BORED) {
  			GL11.glTranslatef(0F, 0.9F, 0F);	//1.4
  			PBack.rotateAngleZ = 0.6F;
  	  		PNeck.rotateAngleZ = -0.25F;
  	  	    PHead.rotateAngleZ = -0.3F;
  	    	PLegRight.rotateAngleZ = -1F;
  		    PLegLeft.rotateAngleZ = -1F;
  		    PLegRightEnd.rotateAngleZ = -1.1F;
  		    PLegLeftEnd.rotateAngleZ = -1.1F;
  		    PTail.rotateAngleZ = -0.6F;
  	  	    PTailEnd.rotateAngleZ = -0.6F;
  	  	    PJawBottom.rotateAngleZ = -0.7F; 	  	    
  		}
  		else {
  			GL11.glTranslatef(0F, 1.4F, 0F);	//1.4
  			PBack.rotateAngleZ = -0.8F;
  	  		PNeck.rotateAngleZ = -0.3F;
  	    	PLegRight.rotateAngleZ = -0.8F;
  		    PLegLeft.rotateAngleZ = -0.8F;
  		    PLegRightEnd.rotateAngleZ = -1.4F;
  		    PLegLeftEnd.rotateAngleZ = -1.4F;
  		    PTail.rotateAngleZ = 0.4F;
  	  	    PTailEnd.rotateAngleZ = angleZ * 0.2F + 0.4F;
  	  	    PJawBottom.rotateAngleZ = angleZ * 0.05F -0.3F;
  	  	    PHead.rotateAngleZ = angleZ * 0.02F + 0.4F;
  		}
  	}

	//常時擺動尾巴跟下巴
  	private void motionTail(float angleZ) { 	
  	    PTail.rotateAngleZ = angleZ * 0.2F;
  	    PTailEnd.rotateAngleZ = angleZ * 0.3F;
  	    PJawBottom.rotateAngleZ = angleZ * 0.2F -0.3F;
  	}

	//雙腳移動計算
  	private void motionLeg(float f, float f1) {
		//移動雙腳 此模型方向設錯 因此改成轉Z
	    PLegRight.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1 - 0.6F;
	    PLegLeft.rotateAngleZ = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1 - 0.6F;
	    PLegRightEnd.rotateAngleZ = MathHelper.sin(f * 0.6662F) * f1 - 0.4F;
	    PLegLeftEnd.rotateAngleZ = MathHelper.sin(f * 0.6662F + 3.1415927F) * f1 - 0.4F;	
	}

  	//頭部看人轉動計算
	private void motionWatch(float f3, float f4, float angleZ) {
		//移動頭部 使其看人, 不看人時持續擺動頭部
	    if(f4 != 0) {
		    PNeck.rotateAngleY = f3 / 160F;		//左右角度 角度轉成rad 即除以57.29578
		    PNeck.rotateAngleZ = f4 / 130F; 	//上下角度
		    PHead.rotateAngleY = f3 / 160F;
		    PHead.rotateAngleZ = f4 / 130F;
		    PTail.rotateAngleY = f3 / -130F;	//尾巴以反方向擺動
	    }
	    else {
	    	PNeck.rotateAngleY = 0;			//左右角度 角度轉成rad 即除以57.29578
		    PNeck.rotateAngleZ = 0.2F; 		//上下角度
		    PHead.rotateAngleY = 0;
		    PHead.rotateAngleZ = angleZ * 0.15F + 0.2F;
		    PTail.rotateAngleY = 0;  	
	    }	
	}

	//隨機抽取顯示的表情 
    private void rollEmotion(EntityDestroyerI ent) {   	
    	switch(ent.getEntityState(AttrID.Emotion)) {
    	case AttrValues.Emotion.BLINK:	//blink
    		EmotionBlink(ent);
    		break;
    	case AttrValues.Emotion.T_T:	//cry
    	case AttrValues.Emotion.O_O:
    	case AttrValues.Emotion.HUNGRY:
    		if(ent.getStartEmotion() <= 0) setFace(2);
    		break;
    	case AttrValues.Emotion.BORED:	//cry
    		if(ent.getStartEmotion() <= 0) setFace(1);
    		break;
    	default:						//normal face
    		if(ent.getStartEmotion() <= 0) setFace(0); 			    //reset face to 0
    		//roll emotion (3 times) every 6 sec
    		//1 tick in entity = 3 tick in model class (20 vs 60 fps)
    		if(ent.ticksExisted % 120 == 0) {  			
        		int emotionRand = ent.getRNG().nextInt(10);   		
        		if(emotionRand > 7) {
        			EmotionBlink(ent);
        		} 		
        	}
    		break;
    	}	
    }

	//眨眼動作, this emotion is CLIENT ONLY, no sync packet required
	private void EmotionBlink(EntityDestroyerI ent) {
		if(ent.getEntityState(AttrID.Emotion) == AttrValues.Emotion.NORMAL) {	//要在沒表情狀態才做表情		
			ent.setStartEmotion(ent.ticksExisted);		//表情開始時間
			ent.setEntityState(AttrID.Emotion, AttrValues.Emotion.BLINK, false);	//標記表情為blink
		}
		
		int EmoTime = ent.ticksExisted - ent.getStartEmotion();
 		
    	if(EmoTime > 61) {	//reset face
    		setFace(0);
			ent.setEntityState(AttrID.Emotion, AttrValues.Emotion.NORMAL, false);
			ent.setStartEmotion(-1);
    	}
    	else if(EmoTime > 45) {
    		setFace(1);
    	}
    	else if(EmoTime > 35) {
    		setFace(0);
    	}
    	else if(EmoTime > 1) {
    		setFace(1);
    	}
	}
	
	//設定顯示的臉型
	private void setFace(int emo) {
		switch(emo) {
		case 0:
			PEyeLightL[0].isHidden = false;
			PEyeLightR[0].isHidden = false;
			PEyeLightL[1].isHidden = true;
			PEyeLightR[1].isHidden = true;
			PEyeLightL[2].isHidden = true;
			PEyeLightR[2].isHidden = true;
			break;
		case 1:
			PEyeLightL[0].isHidden = true;
			PEyeLightR[0].isHidden = true;
			PEyeLightL[1].isHidden = false;
			PEyeLightR[1].isHidden = false;
			PEyeLightL[2].isHidden = true;
			PEyeLightR[2].isHidden = true;
			break;
		case 2:
			PEyeLightL[0].isHidden = true;
			PEyeLightR[0].isHidden = true;
			PEyeLightL[1].isHidden = true;
			PEyeLightR[1].isHidden = true;
			PEyeLightL[2].isHidden = false;
			PEyeLightR[2].isHidden = false;
			break;
		default:
			break;
		}
	}

}
