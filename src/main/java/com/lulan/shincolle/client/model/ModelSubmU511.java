package com.lulan.shincolle.client.model;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelSubmU511 - PinkaLulan 2015/4/24
 * Created using Tabula 4.1.1
 */
public class ModelSubmU511 extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
    public ModelRenderer Cloth01;
    public ModelRenderer EquipBase;
    public ModelRenderer Head;
    public ModelRenderer Pipe;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Hat01;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Hat02;
    public ModelRenderer Ear1;
    public ModelRenderer Ear2;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmLeft03;
    public ModelRenderer ArmRight02;
    public ModelRenderer ArmRight03;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer Skirt;
    public ModelRenderer LegRight02;
    public ModelRenderer LegLeft02;
    public ModelRenderer EquipMid;
    public ModelRenderer EquipL;
    public ModelRenderer EquipR;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    

    public ModelSubmU511()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.setDefaultFaceModel();
        
        this.Cloth01 = new ModelRenderer(this, 84, 0);
        this.Cloth01.setRotationPoint(0.0F, -11.5F, 0.0F);
        this.Cloth01.addBox(-7.0F, 0.0F, -4.5F, 14, 11, 8, 0.0F);
        this.HairL02 = new ModelRenderer(this, 88, 100);
        this.HairL02.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairL02, -0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.Hat01 = new ModelRenderer(this, 30, 24);
        this.Hat01.setRotationPoint(0.0F, -15.0F, -6.0F);
        this.Hat01.addBox(-3.0F, -6.0F, 0.5F, 6, 6, 13, 0.0F);
        this.setRotateAngle(Hat01, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipL = new ModelRenderer(this, 0, 23);
        this.EquipL.mirror = true;
        this.EquipL.setRotationPoint(11.5F, 0.0F, 4.0F);
        this.EquipL.addBox(0.0F, 0.0F, -20.0F, 5, 13, 20, 0.0F);
        this.setRotateAngle(EquipL, -0.3141592653589793F, -0.17453292519943295F, 0.0F);
        this.HairL01 = new ModelRenderer(this, 88, 100);
        this.HairL01.mirror = true;
        this.HairL01.setRotationPoint(6.5F, 0.0F, -4.0F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.17453292519943295F, -0.17453292519943295F, -0.13962634015954636F);
        this.Neck = new ModelRenderer(this, 0, 0);
        this.Neck.setRotationPoint(0.0F, -10.5F, 0.0F);
        this.Neck.addBox(-4.5F, -2.0F, -6.0F, 9, 4, 10, 0.0F);
        this.setRotateAngle(Neck, 0.05235987755982988F, 0.0F, 0.0F);
        this.ArmRight03 = new ModelRenderer(this, 28, 78);
        this.ArmRight03.setRotationPoint(0.0F, 3.0F, 1.0F);
        this.ArmRight03.addBox(-2.5F, 0.0F, -4.0F, 5, 11, 5, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -13.0F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 21, 7, 0.0F);
        this.HairR02 = new ModelRenderer(this, 88, 100);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 6.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairR02, -0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.Butt = new ModelRenderer(this, 80, 19);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-8.0F, 5.0F, -5.0F, 16, 9, 8, 0.0F);
        this.setRotateAngle(Butt, 0.2617993877991494F, 0.0F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 24, 95);
        this.ArmRight02.setRotationPoint(-0.8F, 7F, 0.5F);
        this.ArmRight02.addBox(-2.5F, 0.0F, -3.0F, 5, 3, 5, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 24, 67);
        this.ArmRight01.setRotationPoint(-7.2F, -9F, -0.7F);
        this.ArmRight01.addBox(-4.5F, -1.0F, -3.5F, 7, 8, 7, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.0F, 0.10471975511965977F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 67);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.2F, -9F, -0.7F);
        this.ArmLeft01.addBox(-2.5F, -1.0F, -3.5F, 7, 8, 7, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.0F, 0.0F, -0.10471975511965977F);
        this.HairR01 = new ModelRenderer(this, 88, 100);
        this.HairR01.setRotationPoint(-6.5F, 0.0F, -4.0F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.17453292519943295F, 0.17453292519943295F, 0.13962634015954636F);
        this.Skirt = new ModelRenderer(this, 80, 19);
        this.Skirt.setRotationPoint(0.0F, 5.0F, -2.0F);
        this.Skirt.addBox(-8.0F, 0.0F, -4.5F, 16, 9, 8, 0.0F);
        this.setRotateAngle(Skirt, 0.3490658503988659F, -3.141592653589793F, 0.0F);
        this.EquipR = new ModelRenderer(this, 0, 23);
        this.EquipR.setRotationPoint(-11.5F, 0.0F, 4.0F);
        this.EquipR.addBox(-5.0F, 0.0F, -20.0F, 5, 13, 20, 0.0F);
        this.setRotateAngle(EquipR, -0.3141592653589793F, 0.17453292519943295F, 0.0F);
        this.ArmLeft03 = new ModelRenderer(this, 28, 78);
        this.ArmLeft03.mirror = true;
        this.ArmLeft03.setRotationPoint(0.0F, 3.0F, 1.0F);
        this.ArmLeft03.addBox(-2.5F, 0.0F, -4.0F, 5, 11, 5, 0.0F);
        this.EquipMid = new ModelRenderer(this, 0, 0);
        this.EquipMid.setRotationPoint(0.0F, -5.0F, 2.0F);
        this.EquipMid.addBox(-13.0F, 0.0F, 0.0F, 26, 12, 5, 0.0F);
        this.setRotateAngle(EquipMid, 0.13962634015954636F, 0.0F, 0.0F);
        this.Ear2 = new ModelRenderer(this, 4, 18);
        this.Ear2.setRotationPoint(-8.0F, -1.0F, 0.0F);
        this.Ear2.addBox(0.0F, 0.0F, -4.0F, 0, 8, 5, 0.0F);
        this.setRotateAngle(Ear2, 0.0F, 0.0F, 0.2617993877991494F);
        this.Hair = new ModelRenderer(this, 50, 75);
        this.Hair.setRotationPoint(0.0F, -7.5F, -0.5F);
        this.Hair.addBox(-8.0F, -8.0F, -6.8F, 16, 17, 8, 0.0F);
        this.Hair01 = new ModelRenderer(this, 49, 47);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.1F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 18, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.2617993877991494F, 0.0F, 0.0F);
        this.HairMain = new ModelRenderer(this, 48, 47);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 10, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 67);
        this.LegRight02.setRotationPoint(0.0F, 13.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 14, 6, 0.0F);
        this.Hat02 = new ModelRenderer(this, 4, 17);
        this.Hat02.setRotationPoint(0.0F, 0.5F, 8.4F);
        this.Hat02.addBox(-8.0F, 0.0F, 0.5F, 16, 1, 5, 0.0F);
        this.setRotateAngle(Hat02, 0.3141592653589793F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0.0F, -8.0F, -5.0F);
        this.Ahoke.addBox(0.0F, -4.0F, -11.0F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.5235987755982988F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 67);
        this.LegLeft02.setRotationPoint(0.0F, 13.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 14, 6, 0.0F);
        this.Pipe = new ModelRenderer(this, 0, 17);
        this.Pipe.setRotationPoint(7.0F, -1.0F, -3.5F);
        this.Pipe.addBox(0.0F, -26.0F, 0.0F, 1, 25, 1, 0.0F);
        this.setRotateAngle(Pipe, -0.08726646259971647F, 0.0F, 0.08726646259971647F);
        this.Ear1 = new ModelRenderer(this, 4, 18);
        this.Ear1.mirror = true;
        this.Ear1.setRotationPoint(8.0F, -1.0F, 0.0F);
        this.Ear1.addBox(0.0F, 0.0F, -4.0F, 0, 8, 5, 0.0F);
        this.setRotateAngle(Ear1, 0.0F, 0.0F, -0.2617993877991494F);
        this.LegRight01 = new ModelRenderer(this, 0, 85);
        this.LegRight01.setRotationPoint(-3.8F, 9.5F, -2.7F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 13, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.2618F, 0.0F, -0.03490658503988659F);
        this.LegLeft01 = new ModelRenderer(this, 0, 85);
        this.LegLeft01.setRotationPoint(3.8F, 9.5F, -2.7F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 13, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.2618F, 0.0F, 0.03490658503988659F);
        this.EquipBase = new ModelRenderer(this, 60, 0);
        this.EquipBase.setRotationPoint(0.0F, 8.0F, 3.0F);
        this.EquipBase.addBox(-3.0F, 0.0F, 1.0F, 6, 16, 6, 0.0F);
        this.setRotateAngle(EquipBase, 0.4363323129985824F, 0.0F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 95);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(0.8F, 7F, 0.5F);
        this.ArmLeft02.addBox(-2.5F, 0.0F, -3.0F, 5, 3, 5, 0.0F);
        this.BodyMain.addChild(this.Cloth01);
        this.HairL01.addChild(this.HairL02);
        this.Head.addChild(this.Hat01);
        this.EquipMid.addChild(this.EquipL);
        this.Hair.addChild(this.HairL01);
        this.BodyMain.addChild(this.Neck);
        this.ArmRight02.addChild(this.ArmRight03);
        this.HairR01.addChild(this.HairR02);
        this.BodyMain.addChild(this.Butt);
        this.ArmRight01.addChild(this.ArmRight02);
        this.BodyMain.addChild(this.ArmRight01);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Hair.addChild(this.HairR01);
        this.Butt.addChild(this.Skirt);
        this.EquipMid.addChild(this.EquipR);
        this.ArmLeft02.addChild(this.ArmLeft03);
        this.EquipBase.addChild(this.EquipMid);
        this.Hat02.addChild(this.Ear2);
        this.Head.addChild(this.Hair);
        this.HairMain.addChild(this.Hair01);
        this.Head.addChild(this.HairMain);
        this.LegRight01.addChild(this.LegRight02);
        this.Hat01.addChild(this.Hat02);
        this.Hair.addChild(this.Ahoke);
        this.Neck.addChild(this.Head);
        this.LegLeft01.addChild(this.LegLeft02);
        this.Neck.addChild(this.Pipe);
        this.Hat02.addChild(this.Ear1);
        this.Butt.addChild(this.LegRight01);
        this.Butt.addChild(this.LegLeft01);
        this.BodyMain.addChild(this.EquipBase);
        this.ArmLeft01.addChild(this.ArmLeft02);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -13.0F, 0.0F);
        this.GlowNeck = new ModelRenderer(this, 0, 0);
        this.GlowNeck.setRotationPoint(0.0F, -10.5F, 0.0F);
        this.setRotateAngle(GlowNeck, 0.05235987755982988F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -1.5F, 0.0F);
        
        this.GlowBodyMain.addChild(this.GlowNeck);
        this.GlowNeck.addChild(this.GlowHead);
        this.GlowHead.addChild(this.Face0);
        this.GlowHead.addChild(this.Face1);
        this.GlowHead.addChild(this.Face2);
        this.GlowHead.addChild(this.Face3);
        this.GlowHead.addChild(this.Face4);
        this.GlowHead.addChild(this.Mouth0);
        this.GlowHead.addChild(this.Mouth1);
        this.GlowHead.addChild(this.Mouth2);
        this.GlowHead.addChild(this.Flush0);
        this.GlowHead.addChild(this.Flush1);
        
     	//for held item rendering
        this.armMain = new ModelRenderer[] {this.BodyMain, this.ArmRight01, this.ArmRight02, this.ArmRight03};
        this.armOff = new ModelRenderer[] {this.BodyMain, this.ArmLeft01, this.ArmLeft02, this.ArmLeft03};
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	//FIX: head rotation bug while riding
    	if (f3 <= -180F) { f3 += 360F; }
    	else if (f3 >= 180F) { f3 -= 360F; }

    	switch (((IShipEmotion)entity).getScaleLevel())
    	{
    	case 3:
    		scale = 1.44F;
        	offsetY = -0.45F;
		break;
    	case 2:
    		scale = 1.08F;
        	offsetY = -0.06F;
		break;
    	case 1:
    		scale = 0.72F;
        	offsetY = 0.66F;
		break;
    	default:
    		scale = 0.36F;
        	offsetY = 2.86F;
		break;
    	}
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.scale(scale, scale * 0.95F, scale);
    	GlStateManager.translate(0F, offsetY, 0F);
    	
    	//main body
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	//light part
    	GlStateManager.disableLighting();
    	GlStateManager.enableCull();
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GlStateManager.disableCull();
    	GlStateManager.enableLighting();
    	
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
    }

	@Override
	public void showEquip(IShipEmotion ent)
	{
		if (ent.getStateEmotion(ID.S.State) >= ID.State.EQUIP00)
		{
			this.EquipBase.isHidden = false;
		}
		else
		{
			this.EquipBase.isHidden = true;
		}
	}

	@Override
	public void syncRotationGlowPart()
	{
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowNeck.rotateAngleX = this.Neck.rotateAngleX;
		this.GlowNeck.rotateAngleY = this.Neck.rotateAngleY;
		this.GlowNeck.rotateAngleZ = this.Neck.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	GlStateManager.translate(0F, 0.41F + 0.19F * ent.getScaleLevel(), 0F);
    	this.setFaceHungry(ent);
    	
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.035F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.035F;
		this.LegLeft01.rotateAngleX = -2.8F;
    	this.LegLeft02.rotateAngleX = 1.4F;
    	this.LegRight01.rotateAngleX = -2.8F;
    	this.LegRight02.rotateAngleX = 1.4F;
		//equip
	  	this.Pipe.rotateAngleX = -0.0873F;
    	//body
	  	this.Ahoke.rotateAngleY = 0.5236F;
    	this.Head.rotateAngleX = 0.2618F;
    	this.Head.rotateAngleY = 0F;
    	this.BodyMain.rotateAngleX = 0.35F;
    	//arm
    	this.ArmLeft01.rotateAngleX = -0.7F;
    	this.ArmLeft01.rotateAngleY = 0F;
    	this.ArmLeft01.rotateAngleZ = -0.12F;
    	this.ArmRight01.rotateAngleX = -0.96F;
    	this.ArmRight01.rotateAngleY = -0.35F;
    	this.ArmRight01.rotateAngleZ = 0.12F;
    	this.ArmRight03.rotateAngleZ = -1.57F;
    	this.ArmRight03.offsetX = -0.153F;
    	this.ArmRight03.offsetY = 0.1F;
    	//hair
    	this.Hair01.rotateAngleX = 0.05F;
	  	this.Ear1.rotateAngleZ = -0.2618F;
	  	this.Ear2.rotateAngleZ = 0.2618F;
	  	//skirt
	  	this.Skirt.rotateAngleX = 2.618F;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.5F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.5F;
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
  		
  		//leg move parm
  		addk1 = angleAdd1 - 0.2118F;
	  	addk2 = angleAdd2 - 0.1118F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 * 0.014F + 0.1F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	    
	    //正常站立動作
	  	//Body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
	  	this.BodyMain.rotateAngleX = -0.1F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.06F + 0.3F;
	    this.Hair01.rotateAngleZ = 0F;
		this.HairL01.rotateAngleX = -0.17F;
	  	this.HairL02.rotateAngleX = 0.17F;
	  	this.HairR01.rotateAngleX = -0.17F;
	  	this.HairR02.rotateAngleX = 0.17F;
	  	this.HairL01.rotateAngleZ = -0.14F;
	  	this.HairL02.rotateAngleZ = 0.08F;
	  	this.HairR01.rotateAngleZ = 0.14F;
	  	this.HairR02.rotateAngleZ = -0.05F;
	  	this.Ear1.rotateAngleZ = angleX * 0.1F - 0.2618F;
	  	this.Ear2.rotateAngleZ = angleX * 0.1F + 0.2618F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = angleAdd2 * 0.5F + 0.15F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	  	this.ArmLeft01.rotateAngleZ = -angleX * 0.06F - 0.16F;
	    this.ArmRight01.rotateAngleX = angleAdd1 * 0.5F;
	    this.ArmRight01.rotateAngleY = 0F;
    	this.ArmRight01.rotateAngleZ = angleX * 0.06F + 0.16F;
    	this.ArmRight03.rotateAngleZ = 0F;
    	this.ArmRight03.offsetX = 0F;
    	this.ArmRight03.offsetY = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = 0.035F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = -0.035F;
		this.LegLeft02.rotateAngleX = 0F;
    	this.LegRight02.rotateAngleX = 0F;
		//equip
	  	this.Pipe.rotateAngleX = -0.0873F;
	  	//skirt
	  	this.Skirt.rotateAngleX = 0.35F;

	    if (ent.getIsSprinting() || f1 > 0.9F)
	    {	//奔跑動作
	    	//無特殊奔跑動作
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {	//潛行, 蹲下動作
	    	GlStateManager.translate(0F, 0.1F, 0F);
	    	//body
	    	this.Head.rotateAngleX -= 0.8727F;
	    	this.BodyMain.rotateAngleX = 1.0472F;
		  	//hair
		  	this.Hair01.rotateAngleX += 0.2236F;
		  	//leg
		  	addk1 -= 1.2F;
		  	addk2 -= 1.2F;
		  	//equip
		  	this.Pipe.rotateAngleX = -0.7854F;
		  	//skirt
		  	this.Skirt.rotateAngleX = 0.8727F;
  		}//end if sneaking
  		
	    if (ent.getIsSitting() || ent.getIsRiding())
	    {  //騎乘動作
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
	    		GlStateManager.translate(0F, 0.41F, 0F);
		    	//body
		    	this.Head.rotateAngleX += 0.2618F;
		    	this.BodyMain.rotateAngleX = 0.35F;
		    	//hair
		    	this.HairL01.rotateAngleX -= 0.2F;
		    	this.HairR01.rotateAngleX -= 0.2F;
		    	this.HairL02.rotateAngleX -= 0.2F;
		    	this.HairR02.rotateAngleX -= 0.2F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -angleX * 0.2F - 0.7F;
		    	this.ArmRight01.rotateAngleX = -0.96F;
		    	this.ArmRight01.rotateAngleY = -0.35F;
		    	this.ArmRight03.rotateAngleZ = -1.57F;
		    	this.ArmRight03.offsetX = -0.153F;
		    	this.ArmRight03.offsetY = 0.1F;
		    	//hair
		    	this.Hair01.rotateAngleX -= 0.25F;
		    	//leg
		    	addk1 = -2.8F;
		    	addk2 = -2.8F;
		    	this.LegLeft02.rotateAngleX = 1.4F;
		    	this.LegRight02.rotateAngleX = 1.4F;
			  	//skirt
			  	this.Skirt.rotateAngleX = 2.618F;
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, 0.4F, 0F);
		    	//body
		    	this.Head.rotateAngleX -= 0.7F;
		    	this.BodyMain.rotateAngleX = 0.5236F;
		    	//hair
		    	this.HairL01.rotateAngleX -= 0.3F;
		    	this.HairR01.rotateAngleX -= 0.3F;
		    	this.HairL02.rotateAngleX -= 0.3F;
		    	this.HairR02.rotateAngleX -= 0.3F;
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
			  	this.Pipe.rotateAngleX = -0.7854F;
			  	//skirt
			  	this.Skirt.rotateAngleX = 0.8727F;
	    	}
  		}//end if sitting
	    
	    //attack
	    if (ent.getAttackTick() > 43)
	    {
	    	//swing arm
		    float ft = (50 - ent.getAttackTick()) + (f2 - (int)f2);
		    ft *= 0.08F;
	  		float fa = MathHelper.cos(ft * ft * (float)Math.PI);
	        float fb = MathHelper.cos(MathHelper.sqrt(ft) * (float)Math.PI);
	        this.ArmLeft01.rotateAngleX += -fb * 80.0F * Values.N.DIV_PI_180 - 0.9F;
	        this.ArmLeft01.rotateAngleY += fa * 20.0F * Values.N.DIV_PI_180 - 0.3F;
	        this.ArmLeft01.rotateAngleZ += fb * 10.0F * Values.N.DIV_PI_180;
	    }//end attack
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.4F;
	        this.ArmRight01.rotateAngleY = 0F;
	        this.ArmRight01.rotateAngleZ = -0.2F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	  	}
	  	
	  	//鬢毛調整
	    float headX = this.Head.rotateAngleX * -0.5F;
	    float headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleX += headX;
	    this.Hair01.rotateAngleZ += headZ;
	  	this.HairL01.rotateAngleZ += headZ;
	  	this.HairL02.rotateAngleZ += headZ;
	  	this.HairR01.rotateAngleZ += headZ;
	  	this.HairR02.rotateAngleZ += headZ;
		this.HairL01.rotateAngleX += headX;
	  	this.HairL02.rotateAngleX += headX;
	  	this.HairR01.rotateAngleX += headX;
	  	this.HairR02.rotateAngleX += headX;
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
	@Override
	public void setFaceNormal(IShipEmotion ent)
	{
		this.setFace(0);
		
		if (ent.getStateEmotion(ID.S.Emotion4) == ID.Emotion.BORED && (ent.getTickExisted() & 255) > 200)
		{
			this.setMouth(0);
		}
		else
		{
			this.setMouth(3);
		}
	}

	@Override
	public void setFaceBlink0(IShipEmotion ent)
	{
		this.setFace(0);
	}

	@Override
	public void setFaceBlink1(IShipEmotion ent)
	{
		this.setFace(1);
	}

	@Override
	public void setFaceCry(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 255;
		
		if (t < 128)
		{
			this.setFace(6);
			
			if (t < 64)
			{
				this.setMouth(2);
			}
			else
			{
				this.setMouth(1);
			}
		}
		else
		{
			this.setFace(7);
			this.setMouth(2);
		}
	}

	@Override
	public void setFaceAttack(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 511;
		
		if (t < 160)
		{
			this.setFace(0);
			
			if (t < 80)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
			}
		}
		else if (t < 320)
		{
			this.setFace(2);
			
			if (t < 220)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
			}
		}
		else if (t < 410)
		{
			this.setFace(3);
			
			if (t < 360)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(4);
			}
		}
		else
		{
			this.setFace(8);
			
			if (t < 470)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
			}
		}
	}
	
	@Override
	public void setFaceDamaged(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 511;
		
		if (t < 200)
		{
			this.setFace(6);
			
			if (t < 60)
			{
				this.setMouth(2);
			}
			else
			{
				this.setMouth(1);
			}
		}
		else if (t < 400)
		{
			this.setFace(3);
			
			if (t < 250)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(3);
			}
		}
		else
		{
			this.setFace(9);
			
			if (t < 450)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(1);
			}
		}
	}
	
	@Override
	public void setFaceScorn(IShipEmotion ent)
	{
		this.setFace(2);
		this.setMouth(1);
	}

	@Override
	public void setFaceHungry(IShipEmotion ent)
	{
		this.setFace(4);	
		this.setMouth(2);
	}

	@Override
	public void setFaceAngry(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 255;
		
		if (t < 128)
		{
			this.setFace(1);
			
			if (t < 64)
			{
				this.setMouth(3);
			}
			else
			{
				this.setMouth(1);
			}
		}
		else
		{
			this.setFace(2);

			if (t < 170)
			{
				this.setMouth(1);
			}
			else
			{
				this.setMouth(3);
			}
		}
	}

	@Override
	public void setFaceBored(IShipEmotion ent)
	{
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 511;
		
		if (t < 170)
		{
			this.setFace(1);
			
			if (t < 80)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(3);
			}
		}
		else if (t < 340)
		{
			this.setFace(8);

			if (t < 250)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(3);
			}
		}
		else
		{
			this.setFace(0);

			if (t < 420)
			{
				this.setMouth(0);
			}
			else
			{
				this.setMouth(3);
			}
		}
	}
	
	@Override
	public void setFaceShy(IShipEmotion ent)
	{
		this.setFlush(true);
		
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 255;
		
		this.setFace(0);
		
		if (t < 150)
		{
			this.setMouth(3);
		}
		else
		{
			this.setMouth(2);
		}
	}
	
	@Override
	public void setFaceHappy(IShipEmotion ent)
	{
		this.setFlush(true);
		
		int t = (ent.getTickExisted() + (ent.getStateMinor(ID.M.ShipUID) << 7)) & 255;
		
		if (t < 140)
		{
			this.setFace(3);
			
			if (t < 80)
			{
				this.setMouth(3);
			}
			else
			{
				this.setMouth(4);
			}
		}
		else
		{
			this.setFace(8);
			this.setMouth(0);
		}
	}

    
}