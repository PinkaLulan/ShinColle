package com.lulan.shincolle.model;


import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import com.lulan.shincolle.entity.EntityDestroyerI;

public class ModelDestroyerI extends ModelBase {
    //fields
	public ModelRenderer PBack;
	public ModelRenderer PNeck;
	public ModelRenderer PHead;
	public ModelRenderer PEyeLightLeft1;
	public ModelRenderer PEyeLightRight1;
	public ModelRenderer PEyeLightLeft2;
	public ModelRenderer PEyeLightRight2;
	public ModelRenderer PEyeLightLeft3;
	public ModelRenderer PEyeLightRight3;
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
	
	private static final int cooldown = 300;
	public float scale = 1F;			//預設大小為1.0倍
	public boolean onEmotion = false;	//是否正在表情中
	public int cdEmotion = cooldown;	//表情cooldown
    public int remainEmotion = 0;		//表情剩餘時間
	
    public ModelDestroyerI() {
    textureWidth = 256;
    textureHeight = 128;
    
    setTextureOffset("PBack.Back", 128, 8);
    setTextureOffset("PNeck.NeckBack", 128, 0);
    setTextureOffset("PNeck.NeckNeck", 128, 28);
    setTextureOffset("PNeck.NeckBody", 0, 70);
    setTextureOffset("PHead.Head", 0, 0);
    setTextureOffset("PEyeLightLeft1.LeftEye1", 138, 64);
    setTextureOffset("PEyeLightRight1.RightEye1", 138, 64);
    setTextureOffset("PEyeLightLeft2.LeftEye2", 138, 84);
    setTextureOffset("PEyeLightRight2.RightEye2", 138, 84);
    setTextureOffset("PEyeLightLeft3.LeftEye3", 138, 104);
    setTextureOffset("PEyeLightRight3.RightEye3", 138, 104);
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
    
    PBack = new ModelRenderer(this, "PBack");
    PBack.setRotationPoint(-8F, -16F, 0F);
    setRotation(PBack, 0F, 0F, -0.31F);
    PBack.mirror = true;
      PBack.addBox("Back", -12F, -10F, -12F, 28, 20, 24);
    PNeck = new ModelRenderer(this, "PNeck");
    PNeck.setRotationPoint(15F, 0F, 0F);
    setRotation(PNeck, 0F, 0F, 0.2F);
    PNeck.mirror = true;
      PNeck.addBox("NeckBack", -3F, -11F, -13F, 30, 26, 26);
      PNeck.addBox("NeckNeck", 6F, 15F, -10F, 21, 4, 20);
      PNeck.addBox("NeckBody", -8F, 7F, -9F, 18, 14, 18);
    PHead = new ModelRenderer(this, "PHead");
    PHead.setRotationPoint(26F, 0F, 0F);
    setRotation(PHead, 0F, 0F, 0.3F);
    PHead.mirror = true;
      PHead.addBox("Head", -3F, -12F, -16F, 32, 32, 32);
      PHead.addBox("ToothTopMid", 14.5F, 20F, -6F, 4, 6, 12);
      PHead.addBox("ToothTopRight", 0F, 20F, -10F, 18, 6, 4);
      PHead.addBox("ToothTopLeft", 0F, 20F, 6F, 18, 6, 4);
      PHead.addBox("JawTop", -3F, 20F, -11F, 22, 2, 22);
    PEyeLightLeft1 = new ModelRenderer(this, "PEyeLightLeft1");
    PEyeLightRight1 = new ModelRenderer(this, "PEyeLightRight1");
    PEyeLightLeft1.addBox("LeftEye1", -3F, 0F, 16.1F, 24, 20, 0);
    PEyeLightRight1.addBox("RightEye1", -3F, 0F, -16.1F, 24, 20, 0);
    PEyeLightLeft2 = new ModelRenderer(this, "PEyeLightLeft2");
    PEyeLightRight2 = new ModelRenderer(this, "PEyeLightRight2");
    PEyeLightLeft2.addBox("LeftEye2", -3F, 0F, 16.1F, 24, 20, 0).isHidden = true;
    PEyeLightRight2.addBox("RightEye2", -3F, 0F, -16.1F, 24, 20, 0).isHidden = true;
    PEyeLightLeft3 = new ModelRenderer(this, "PEyeLightLeft3");
    PEyeLightRight3 = new ModelRenderer(this, "PEyeLightRight3");
    PEyeLightLeft3.addBox("LeftEye3", -3F, 0F, 16.1F, 24, 20, 0).isHidden = true;
    PEyeLightRight3.addBox("RightEye3", -3F, 0F, -16.1F, 24, 20, 0).isHidden = true;
    PJawBottom = new ModelRenderer(this, "PJawBottom");
    PJawBottom.setRotationPoint(-6F, 18F, 0F);
    setRotation(PJawBottom, 0F, 0F, -0.2F);
    PJawBottom.mirror = true;
      PJawBottom.addBox("JawBottom", -3F, 0F, -10F, 3, 18, 20);
      PJawBottom.addBox("ToothBottomLeft", -1F, 7.5F, 6F, 4, 10, 3);
      PJawBottom.addBox("ToothBottomRight", -1F, 7.5F, -9F, 4, 10, 3);
      PJawBottom.addBox("ToothBottomMid", -1F, 14.5F, -6F, 4, 3, 12);
      PHead.addChild(PJawBottom);
      PHead.addChild(PEyeLightLeft1);
      PHead.addChild(PEyeLightRight1);
      PHead.addChild(PEyeLightLeft2);
      PHead.addChild(PEyeLightRight2);
      PHead.addChild(PEyeLightLeft3);
      PHead.addChild(PEyeLightRight3);
      PNeck.addChild(PHead);
      PBack.addChild(PNeck);
    PBody = new ModelRenderer(this, "PBody");
    PBody.setRotationPoint(0F, 0F, 0F);
//    setRotation(PBody, 0F, 0F, 0F);
    PBody.mirror = true;
      PBody.addBox("Body", -10F, 10F, -11F, 24, 16, 22);
    PLegLeft = new ModelRenderer(this, "PLegLeft");
    PLegLeft.setRotationPoint(-3F, 24F, 6F);
//    setRotation(PLegLeft, 0F, 0F, 0F);
    PLegLeft.mirror = true;
      PLegLeft.addBox("LegLeftFront", -3F, -4F, -1F, 8, 14, 8);
    PLegLeftEnd = new ModelRenderer(this, "PLegLeftEnd");
    PLegLeftEnd.setRotationPoint(1F, 8F, 4F);
//    setRotation(PLegLeftEnd, 0F, 0F, 14F);
    PLegLeftEnd.mirror = true;
      PLegLeftEnd.addBox("LegLeftEnd", -12F, -3F, -4F, 12, 6, 6);
      PLegLeft.addChild(PLegLeftEnd);
      PBody.addChild(PLegLeft);
    PLegRight = new ModelRenderer(this, "PLegRight");
    PLegRight.setRotationPoint(-3F, 24F, -8F);
 //   setRotation(PLegRight, 0F, 0F, 0F);
    PLegRight.mirror = true;
      PLegRight.addBox("LegRightFront", -3F, -4F, -5F, 8, 14, 8);
    PLegRightEnd = new ModelRenderer(this, "PLegRightEnd");
    PLegRightEnd.setRotationPoint(1F, 8F, -1F);
 //   setRotation(PLegRightEnd, 0F, 0F, 14F);
    PLegRightEnd.mirror = true;
      PLegRightEnd.addBox("LegRightEnd", -12F, -3F, -3F, 12, 6, 6);
      PLegRight.addChild(PLegRightEnd);
      PBody.addChild(PLegRight);
      PBack.addChild(PBody);
    PTail = new ModelRenderer(this, "PTail");
    PTail.setRotationPoint(-12F, -2F, 0F);
    setRotation(PTail, 0F, 0F, 0.25F);
    PTail.mirror = true;
      PTail.addBox("TailBack", -22F, -6F, -10F, 26, 16, 20);
      PTail.addBox("TailBody", -8F, 2F, -8F, 18, 18, 14);
    PTailLeft = new ModelRenderer(this, "PTailLeft");
    PTailLeft.setRotationPoint(-12F, 4F, 8F);
    setRotation(PTailLeft, 0.5F, 0F, 0.25F);
    PTailLeft.mirror = true;
      PTailLeft.addBox("TailLeftFront", -8F, -4F, 0F, 12, 18, 6);
    PTailLeftEnd = new ModelRenderer(this, "PTailLeftEnd");
    PTailLeftEnd.setRotationPoint(0F, 9F, 5F);
   setRotation(PTailLeftEnd, 0F, 0F, -0.4F);
    PTailLeftEnd.mirror = true;
      PTailLeftEnd.addBox("TailLeftEnd", -24F, -4F, -2F, 24, 12, 4);
      PTailLeft.addChild(PTailLeftEnd);
      PTail.addChild(PTailLeft);
    PTailRight = new ModelRenderer(this, "PTailRight");
    PTailRight.setRotationPoint(-12F, 4F, -8F);
    setRotation(PTailRight, -0.5F, 0F, 0.25F);
    PTailRight.mirror = true;
      PTailRight.addBox("TailRightFront", -8F, -4F, -6F, 12, 18, 6);
    PTailRightEnd = new ModelRenderer(this, "PTailRightEnd");
    PTailRightEnd.setRotationPoint(0F, 9F, -5F);
    setRotation(PTailRightEnd, 0F, 0F, -0.4F);
    PTailRightEnd.mirror = true;
      PTailRightEnd.addBox("TailRightEnd", -24F, -4F, -2F, 24, 12, 4);
      PTailRight.addChild(PTailRightEnd);
      PTail.addChild(PTailRight);
    PTailEnd = new ModelRenderer(this, "PTailEnd");
    PTailEnd.setRotationPoint(-22F, 2F, 0F);
    setRotation(PTailEnd, 0F, 0F, 0.3F);
    PTailEnd.mirror = true;
      PTailEnd.addBox("TailEnd", -20F, -6F, -8F, 24, 10, 16);
      PTail.addChild(PTailEnd);
      PBack.addChild(PTail);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
	super.render(entity, f, f1, f2, f3, f4, f5);
	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	
	// Scale, Translate, Rotate
	// GL11.glScalef(this.scale, this.scale, this.scale);
	GL11.glScalef(0.5F, 0.45F, 0.4F);	//debug用
	GL11.glTranslatef(0F, 2F, 0F);		//大小0.45時設2.3   大小0.3時設3
	GL11.glRotatef(90F, 0F, 1F, 0F);	//此模型頭部方向錯誤 因此render時調整回來
	PBack.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  //for idle/run animation
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

    float angelZ = MathHelper.cos(f2*0.125F);
    
    //依照emotion rand 隨機抽取顯示的表情, face: (normal)64 (-_-)84 (cry)104
    //setTextureOffset("PHead.EyeLightLeft", 138, 64);
    //setTextureOffset("PHead.EyeLightRight", 138, 64);
    int emotionRand;
   
    if(onEmotion) {
    	MotionBlink();
    }
    else {
    	cdEmotion--;
    	
    	if(cdEmotion < 0) {  		
    		emotionRand = entity.worldObj.rand.nextInt(100);
    		LogHelper.info("DEBUG : rand "+emotionRand);
    		
    		if(emotionRand > 50) {
    			LogHelper.info("DEBUG : into emo!");
    			MotionBlink();
    		}
    		else {
    			cdEmotion = cooldown;
    		}
    	}
    }

    //移動雙腳 此模型方向設錯 因此改成轉Z
    PLegRight.rotateAngleZ = MathHelper.cos(f * 0.6662F) * 1.4F * f1 - 0.6F;
    PLegLeft.rotateAngleZ = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1 - 0.6F;
    PLegRightEnd.rotateAngleZ = MathHelper.sin(f * 0.6662F) * f1 - 0.4F;
    PLegLeftEnd.rotateAngleZ = MathHelper.sin(f * 0.6662F + 3.1415927F) * f1 - 0.4F;
    
    //移動頭部 使其看人  轉動角度太小時則忽略不轉
    if(MathHelper.abs(f4) > 1) {
	    PNeck.rotateAngleY = f3 / 128F;	//左右角度 角度轉成rad 即除以57.29578
	    PNeck.rotateAngleZ = f4 / 110F; //上下角度
	    PHead.rotateAngleY = f3 / 128F;
	    PHead.rotateAngleZ = f4 / 110F;
	    PTail.rotateAngleY = f3 / 128F;	//尾巴以反方向擺動
    }
    else {
    	PNeck.rotateAngleY = 0;			//左右角度 角度轉成rad 即除以57.29578
	    PNeck.rotateAngleZ = 0.2F; 		//上下角度
	    PHead.rotateAngleY = 0;
	    PHead.rotateAngleZ = angelZ * 0.2F + 0.3F;
	    PTail.rotateAngleY = 0;  	
    }
    
    //常時擺動尾巴跟下巴
    PTail.rotateAngleZ = angelZ * 0.2F;
    PTailEnd.rotateAngleZ = angelZ * 0.3F;
    PJawBottom.rotateAngleZ = -angelZ * 0.1F - 0.3F;
  }

    //眨眼動作
	private void MotionBlink() {
		if(!onEmotion) {	//表情cd到了, 可以做表情
			remainEmotion = 101;	//倒數開始5 sec
			onEmotion = true;
		}	
		else {
			remainEmotion--;
			
			switch(remainEmotion) {
			case 99:
				setFace(2);
				break;
			case 54:
				setFace(1);
				break;
			case 17:
				setFace(2);
				break;
			case 1:
				setFace(1);
				onEmotion = false;
				cdEmotion = cooldown;
				break;
			}
		}
	}
	
	//設定顯示的臉型
	private void setFace(int emo) {
		switch(emo) {
		case 1:
			PEyeLightLeft1.isHidden = false;
			PEyeLightRight1.isHidden = false;
			PEyeLightLeft2.isHidden = true;
			PEyeLightRight2.isHidden = true;
			PEyeLightLeft3.isHidden = true;
			PEyeLightRight3.isHidden = true;
			break;
		case 2:
			PEyeLightLeft1.isHidden = true;
			PEyeLightRight1.isHidden = true;
			PEyeLightLeft2.isHidden = false;
			PEyeLightRight2.isHidden = false;
			PEyeLightLeft3.isHidden = true;
			PEyeLightRight3.isHidden = true;
			break;
		case 3:
			PEyeLightLeft1.isHidden = true;
			PEyeLightRight1.isHidden = true;
			PEyeLightLeft2.isHidden = true;
			PEyeLightRight2.isHidden = true;
			PEyeLightLeft3.isHidden = false;
			PEyeLightRight3.isHidden = false;
			break;
		default:
			break;
		}
	}

}
