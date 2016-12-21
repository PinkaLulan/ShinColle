package com.lulan.shincolle.client.model;

import java.util.Random;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelRensouhou - PinkaLulan 2015/3/27
 * Created using Tabula 4.1.1
 */
public class ModelRensouhou extends ModelBase implements IModelEmotion
{
    public ModelRenderer BodyMain;
    public ModelRenderer SwimRing;
    public ModelRenderer Head;
    public ModelRenderer ArmLeft;
    public ModelRenderer ArmRight;
    public ModelRenderer LegLeft;
    public ModelRenderer LegRight;
    public ModelRenderer Propeller;
    public ModelRenderer EarL;
    public ModelRenderer EarR;
    public ModelRenderer HeadBack;
    public ModelRenderer Radar;
    public ModelRenderer CannonL01;
    public ModelRenderer CannonR01;
    public ModelRenderer Face0;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer CannonL02;
    public ModelRenderer CannonR02;
    
    private int startEmo2 = 0;
    private Random rand = new Random();
    private float scale;
    private float offsetY;

    public ModelRensouhou()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        
        this.CannonL02 = new ModelRenderer(this, 0, 1);
        this.CannonL02.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.CannonL02.addBox(-1.5F, -1.5F, -26.0F, 3, 3, 20, 0.0F);
        this.Propeller = new ModelRenderer(this, 0, 24);
        this.Propeller.setRotationPoint(0.0F, 4.0F, 9.0F);
        this.Propeller.addBox(-2.5F, -2.5F, 0.0F, 5, 5, 2, 0.0F);
        this.LegRight = new ModelRenderer(this, 0, 0);
        this.LegRight.setRotationPoint(-4.0F, 6.0F, 0.0F);
        this.LegRight.addBox(-2.5F, 0.0F, -7.0F, 5, 2, 7, 0.0F);
        this.setRotateAngle(LegRight, 0.5235987755982988F, 0.3490658503988659F, 0.0F);
        this.Face2 = new ModelRenderer(this, 88, 0);
        this.Face2.setRotationPoint(0.0F, -8.0F, -9.1F);
        this.Face2.addBox(-8.5F, 0.0F, 0.0F, 17, 9, 0, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 0);
        this.BodyMain.setRotationPoint(0.0F, 7.0F, 0.0F);
        this.BodyMain.addBox(-5.0F, -6.0F, -5.0F, 10, 11, 10, 0.0F);
        this.ArmLeft = new ModelRenderer(this, 0, 0);
        this.ArmLeft.setRotationPoint(5.0F, -4.0F, -4.0F);
        this.ArmLeft.addBox(-2.5F, 0.0F, -8.0F, 5, 2, 8, 0.0F);
        this.setRotateAngle(ArmLeft, 1.0471975511965976F, -0.5235987755982988F, 0.0F);
        this.EarL = new ModelRenderer(this, 55, 20);
        this.EarL.setRotationPoint(7.0F, -11.0F, -9.0F);
        this.EarL.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 7, 0.0F);
        this.CannonR02 = new ModelRenderer(this, 0, 1);
        this.CannonR02.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.CannonR02.addBox(-1.5F, -1.5F, -26.0F, 3, 3, 20, 0.0F);
        this.LegLeft = new ModelRenderer(this, 0, 0);
        this.LegLeft.setRotationPoint(4.0F, 6.0F, 0.0F);
        this.LegLeft.addBox(-2.5F, 0.0F, -7.0F, 5, 2, 7, 0.0F);
        this.setRotateAngle(LegLeft, 0.5235987755982988F, -0.3490658503988659F, 0.0F);
        this.CannonR01 = new ModelRenderer(this, 54, 36);
        this.CannonR01.setRotationPoint(-2.5F, -9.0F, -2.0F);
        this.CannonR01.addBox(-2.0F, -2.0F, -6.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(CannonR01, -0.5235987755982988F, 0.03490658503988659F, 0.0F);
        this.Face1 = new ModelRenderer(this, 54, 9);
        this.Face1.setRotationPoint(0.0F, -8.0F, -9.1F);
        this.Face1.addBox(-8.5F, 0.0F, 0.0F, 17, 9, 0, 0.0F);
        this.ArmRight = new ModelRenderer(this, 0, 0);
        this.ArmRight.setRotationPoint(-5.0F, -4.0F, -4.0F);
        this.ArmRight.addBox(-2.5F, 0.0F, -8.0F, 5, 2, 8, 0.0F);
        this.setRotateAngle(ArmRight, 1.0471975511965976F, 0.5235987755982988F, 0.0F);
        this.Radar = new ModelRenderer(this, 0, 37);
        this.Radar.setRotationPoint(5.0F, -15.0F, -5.0F);
        this.Radar.addBox(0.0F, 0.0F, 0.0F, 4, 4, 5, 0.0F);
        this.CannonL01 = new ModelRenderer(this, 54, 36);
        this.CannonL01.setRotationPoint(2.5F, -9.0F, -2.0F);
        this.CannonL01.addBox(-2.0F, -2.0F, -6.0F, 4, 4, 6, 0.0F);
        this.setRotateAngle(CannonL01, -0.5235987755982988F, -0.03490658503988659F, 0.0F);
        this.HeadBack = new ModelRenderer(this, 70, 22);
        this.HeadBack.setRotationPoint(0.0F, -12.0F, -2.0F);
        this.HeadBack.addBox(-9.0F, 0.0F, 0.0F, 18, 4, 11, 0.0F);
        this.Face0 = new ModelRenderer(this, 54, 0);
        this.Face0.setRotationPoint(0.0F, -8.0F, -9.1F);
        this.Face0.addBox(-8.5F, 0.0F, 0.0F, 17, 9, 0, 0.0F);
        this.SwimRing = new ModelRenderer(this, 0, 29);
        this.SwimRing.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.SwimRing.addBox(-9.0F, 0.0F, -9.0F, 18, 7, 18, 0.0F);
        this.EarR = new ModelRenderer(this, 55, 20);
        this.EarR.mirror = true;
        this.EarR.setRotationPoint(-7.0F, -11.0F, -9.0F);
        this.EarR.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 7, 0.0F);
        this.Head = new ModelRenderer(this, 56, 37);
        this.Head.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.Head.addBox(-9.0F, -8.0F, -9.0F, 18, 9, 18, 0.0F);
        this.CannonL01.addChild(this.CannonL02);
        this.SwimRing.addChild(this.Propeller);
        this.SwimRing.addChild(this.LegRight);
        this.Head.addChild(this.Face2);
        this.BodyMain.addChild(this.ArmLeft);
        this.Head.addChild(this.EarL);
        this.CannonR01.addChild(this.CannonR02);
        this.SwimRing.addChild(this.LegLeft);
        this.Head.addChild(this.CannonR01);
        this.Head.addChild(this.Face1);
        this.BodyMain.addChild(this.ArmRight);
        this.Head.addChild(this.Radar);
        this.Head.addChild(this.CannonL01);
        this.Head.addChild(this.HeadBack);
        this.Head.addChild(this.Face0);
        this.BodyMain.addChild(this.SwimRing);
        this.Head.addChild(this.EarR);
        this.BodyMain.addChild(this.Head);
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
    	if (entity.isNonBoss())
    	{
    		scale = 0.3F;
    		offsetY = 3F;
    	}
    	else
    	{
    		scale = 1F;
        	offsetY = 0F;
    	}
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.scale(scale, scale, scale);
    	
    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
    }
    
    //for idle/run animation
    @Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		  
		IShipEmotion ent = (IShipEmotion)entity;
		
		EmotionHelper.rollEmotion(this, ent);
		  
		motionHumanPos(f, f1, f2, f3, f4, ent);
    }
    
  //雙腳移動計算
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
  	{   
  		float angleX = MathHelper.cos(f2 * 0.08F);
  		float angleRun = MathHelper.cos(f) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		GlStateManager.translate(0F, offsetY, 0F);
  		
  		//leg move parm
  		addk1 = MathHelper.cos(f * 0.7F) * f1 + 0.7F;
	  	addk2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 + 0.7F;

	    //正常站立動作
	  	//Body
	  	this.Head.rotateAngleY = f3 / 57F;
	  	this.BodyMain.rotateAngleX = 0F;
	    //arm 
	    this.ArmLeft.rotateAngleX = angleX * 0.3F + 0.9F;
		this.ArmRight.rotateAngleX = angleX * 0.3F + 0.9F;
		//cannon
		this.CannonL01.rotateAngleX = angleX * 0.05F - 0.5F;
		this.CannonR01.rotateAngleX = -angleX * 0.05F - 0.5F;
		//propeller
		this.Propeller.rotateAngleZ = (f2 / 4) % 360;

	    if (f1 > 0.9F)
	    {	//奔跑動作
	    	setFace(2);
	    	//body
	    	this.BodyMain.rotateAngleX = 0.2618F;
	    	//arm
	    	this.ArmLeft.rotateAngleX = angleRun * 0.3F + 0.9F;
	    	this.ArmRight.rotateAngleX = angleRun * 0.3F + 0.9F;
	    	//cannon
			this.CannonL01.rotateAngleX = angleRun * 0.05F - 0.5F;
			this.CannonR01.rotateAngleX = -angleRun * 0.05F - 0.5F;
			//propeller
			this.Propeller.rotateAngleZ = (f / 2) % 360;
  		}
	    
	    //攻擊動作    
	    if (ent.getAttackTick() > 0)
	    {
	    	setFace(2);
	    }
	    
	    //leg motion
	    this.LegLeft.rotateAngleX = addk1;
	    this.LegRight.rotateAngleX = addk2;    
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
  		break;
  		case 1:
  		case 4:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = false;
  			this.Face2.isHidden = true;
  		break;
  		case 2:
  		case 3:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  		break;
  		default:
  		break;
  		}
  	}

    
}