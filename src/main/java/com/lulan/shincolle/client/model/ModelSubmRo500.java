package com.lulan.shincolle.client.model;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.EmotionHelper;

/**
 * ModelSubmRO500 - PinkaLulan 2015/5/20
 * Created using Tabula 4.1.1
 */
public class ModelSubmRo500 extends ModelBase implements IModelEmotion {
    public ModelRenderer BodyMain;
    public ModelRenderer Neck;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer Butt;
    public ModelRenderer Cloth01;
    public ModelRenderer EquipBase1;
    public ModelRenderer EquipBase2;
    public ModelRenderer Head;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer Face1;
    public ModelRenderer Face2;
    public ModelRenderer Face3;
    public ModelRenderer Face4;
    public ModelRenderer Face0;
    public ModelRenderer FlowerBase;
    public ModelRenderer Ahoke;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Flower1;
    public ModelRenderer Flower2;
    public ModelRenderer Flower3;
    public ModelRenderer Flower4;
    public ModelRenderer ArmLeft02;
    public ModelRenderer ArmRight02;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer LegLeft02;
    public ModelRenderer Equip101;
    public ModelRenderer Equip102;
    public ModelRenderer Equip103;
    public ModelRenderer Equip104;
    public ModelRenderer Equip201;
    public ModelRenderer Equip202;
    public ModelRenderer Equip203;
    public ModelRenderer Equip204;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowNeck;
    public ModelRenderer GlowHead;
    
    private Random rand = new Random();
    private int startEmo2 = 0;

    public ModelSubmRo500() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        
        this.ArmRight02 = new ModelRenderer(this, 24, 86);
        this.ArmRight02.setRotationPoint(-2.0F, 11.5F, 2.5F);
        this.ArmRight02.addBox(-2.5F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.LegLeft02 = new ModelRenderer(this, 0, 65);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(0.0F, 13.0F, -3.0F);
        this.LegLeft02.addBox(-3.0F, 0.0F, 0.0F, 6, 14, 6, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 75);
        this.Hair.setRotationPoint(0.0F, -7.5F, -0.5F);
        this.Hair.addBox(-8.0F, -8.0F, -6.8F, 16, 17, 8, 0.0F);
        this.EquipBase1 = new ModelRenderer(this, 0, 0);
        this.EquipBase1.setRotationPoint(0.0F, 7.0F, 18.0F);
        this.EquipBase1.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Butt = new ModelRenderer(this, 82, 18);
        this.Butt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Butt.addBox(-7.5F, 4.8F, -5.6F, 15, 7, 8, 0.0F);
        this.setRotateAngle(Butt, 0.2617993877991494F, 0.0F, 0.0F);
        this.Face3 = new ModelRenderer(this, 98, 98);
        this.Face3.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face3.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 85);
        this.LegLeft01.setRotationPoint(4.2F, 11.0F, -2.2F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 13, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.12217304763960307F, 0.0F, -0.03490658503988659F);
        this.Equip103 = new ModelRenderer(this, 24, 73);
        this.Equip103.setRotationPoint(-22.0F, 0.0F, 0.0F);
        this.Equip103.addBox(0.0F, -1.0F, -3.0F, 7, 2, 6, 0.0F);
        this.setRotateAngle(Equip103, 0.7853981633974483F, 0.0F, 0.0F);
        this.Equip204 = new ModelRenderer(this, 0, 10);
        this.Equip204.setRotationPoint(-9.0F, 0.0F, -14.0F);
        this.Equip204.addBox(0.0F, 0.0F, 0.0F, 24, 6, 6, 0.0F);
        this.EquipBase2 = new ModelRenderer(this, 0, 0);
        this.EquipBase2.setRotationPoint(0.0F, 8.0F, -2.0F);
        this.EquipBase2.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(EquipBase2, 0.3141592653589793F, 0.0F, 0.0F);
        this.Equip203 = new ModelRenderer(this, 46, 10);
        this.Equip203.setRotationPoint(9.0F, 6.0F, 16.0F);
        this.Equip203.addBox(0.0F, 0.0F, 0.0F, 6, 6, 24, 0.0F);
        this.setRotateAngle(Equip203, -3.141592653589793F, 0.0F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 65);
        this.LegRight02.setRotationPoint(0.0F, 13.0F, -3.0F);
        this.LegRight02.addBox(-3.0F, 0.0F, 0.0F, 6, 14, 6, 0.0F);
        this.Hair01 = new ModelRenderer(this, 49, 47);
        this.Hair01.setRotationPoint(0.0F, 9.0F, 1.1F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 18, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.3490658503988659F, 0.0F, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 104);
        this.BodyMain.setRotationPoint(0.0F, -13.5F, 0.0F);
        this.BodyMain.addBox(-6.5F, -11.0F, -4.0F, 13, 17, 7, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.Equip202 = new ModelRenderer(this, 0, 10);
        this.Equip202.mirror = true;
        this.Equip202.setRotationPoint(-15.0F, 0.0F, 10.0F);
        this.Equip202.addBox(0.0F, 0.0F, 0.0F, 24, 6, 6, 0.0F);
        this.Neck = new ModelRenderer(this, 0, 22);
        this.Neck.setRotationPoint(0.0F, -10.5F, 0.0F);
        this.Neck.addBox(-3.0F, -2.0F, -3.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Neck, 0.05235987755982988F, 0.0F, 0.0F);
        this.Face0 = new ModelRenderer(this, 98, 53);
        this.Face0.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face0.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Equip101 = new ModelRenderer(this, 0, 0);
        this.Equip101.setRotationPoint(0.0F, -6.0F, -9.5F);
        this.Equip101.addBox(-15.0F, -2.5F, -2.5F, 36, 5, 5, 0.0F);
        this.setRotateAngle(Equip101, 0.5235987755982988F, 0.05235987755982988F, 0.13962634015954636F);
        this.Equip104 = new ModelRenderer(this, 54, 10);
        this.Equip104.setRotationPoint(21.0F, 0.0F, 0.0F);
        this.Equip104.addBox(0.0F, -1.5F, -1.5F, 2, 3, 3, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -1.5F, 0.0F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.HairR01 = new ModelRenderer(this, 88, 100);
        this.HairR01.setRotationPoint(-6.5F, 0.0F, -4.0F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairR01, -0.17453292519943295F, 0.17453292519943295F, 0.13962634015954636F);
        this.ArmRight01 = new ModelRenderer(this, 24, 81);
        this.ArmRight01.setRotationPoint(-7.0F, -10.0F, -0.5F);
        this.ArmRight01.addBox(-4.5F, -0.5F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.15707963267948966F, 0.0F, 0.3839724354387525F);
        this.HairL01 = new ModelRenderer(this, 88, 100);
        this.HairL01.mirror = true;
        this.HairL01.setRotationPoint(6.5F, 0.0F, -4.0F);
        this.HairL01.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairL01, -0.17453292519943295F, -0.17453292519943295F, -0.13962634015954636F);
        this.Flower1 = new ModelRenderer(this, 0, 7);
        this.Flower1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Flower1.addBox(0.0F, 0.0F, -1.5F, 0, 4, 3, 0.0F);
        this.setRotateAngle(Flower1, 1.3089969389957472F, -0.08726646259971647F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 24, 56);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(2.0F, 10.5F, 2.5F);
        this.ArmLeft02.addBox(-2.5F, 0.0F, -5.0F, 5, 12, 5, 0.0F);
        this.HairMain = new ModelRenderer(this, 48, 47);
        this.HairMain.setRotationPoint(0.0F, -15.0F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 9, 10, 0.0F);
        this.Flower2 = new ModelRenderer(this, 0, 7);
        this.Flower2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Flower2.addBox(0.0F, 0.0F, -1.5F, 0, 4, 3, 0.0F);
        this.setRotateAngle(Flower2, 2.530727415391778F, 0.0F, -0.08726646259971647F);
        this.Face1 = new ModelRenderer(this, 98, 68);
        this.Face1.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face1.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.Flower3 = new ModelRenderer(this, 0, 7);
        this.Flower3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Flower3.addBox(0.0F, 0.0F, -1.5F, 0, 4, 3, 0.0F);
        this.setRotateAngle(Flower3, -2.6179938779914944F, 0.0F, -0.08726646259971647F);
        this.Cloth01 = new ModelRenderer(this, 84, 0);
        this.Cloth01.setRotationPoint(0.0F, -11.3F, 0.0F);
        this.Cloth01.addBox(-7.0F, 0.0F, -4.5F, 14, 10, 8, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 85);
        this.LegRight01.setRotationPoint(-4.2F, 11.0F, -2.2F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 13, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.12217304763960307F, 0.0F, 0.03490658503988659F);
        this.Equip201 = new ModelRenderer(this, 46, 10);
        this.Equip201.setRotationPoint(-15.0F, 0.0F, -14.0F);
        this.Equip201.addBox(0.0F, 0.0F, 0.0F, 6, 6, 24, 0.0F);
        this.FlowerBase = new ModelRenderer(this, 0, 7);
        this.FlowerBase.setRotationPoint(8.8F, -12.0F, -4.0F);
        this.FlowerBase.addBox(0.0F, 0.0F, -1.5F, 0, 4, 3, 0.0F);
        this.setRotateAngle(FlowerBase, -0.6981317007977318F, 0.08726646259971647F, -0.08726646259971647F);
        this.HairL02 = new ModelRenderer(this, 88, 100);
        this.HairL02.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.HairL02.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairL02, -0.17453292519943295F, 0.0F, 0.08726646259971647F);
        this.Face2 = new ModelRenderer(this, 98, 83);
        this.Face2.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face2.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 24, 81);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.0F, -9.0F, -0.5F);
        this.ArmLeft01.addBox(-0.5F, -1.5F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.15707963267948966F, 0.0F, -0.3839724354387525F);
        this.Ahoke = new ModelRenderer(this, 104, 29);
        this.Ahoke.setRotationPoint(0.0F, -8.5F, -5.0F);
        this.Ahoke.addBox(0.0F, -5.0F, -12.0F, 0, 12, 12, 0.0F);
        this.setRotateAngle(Ahoke, 0.0F, 0.5235987755982988F, 0.0F);
        this.HairR02 = new ModelRenderer(this, 88, 100);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.2F, 6.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 8, 3, 0.0F);
        this.setRotateAngle(HairR02, -0.17453292519943295F, 0.0F, -0.05235987755982988F);
        this.Flower4 = new ModelRenderer(this, 0, 7);
        this.Flower4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Flower4.addBox(0.0F, 0.0F, -1.5F, 0, 4, 3, 0.0F);
        this.setRotateAngle(Flower4, -1.2217304763960306F, 0.0F, 0.0F);
        this.Equip102 = new ModelRenderer(this, 28, 73);
        this.Equip102.setRotationPoint(-22.0F, 0.0F, 0.0F);
        this.Equip102.addBox(0.0F, -3.0F, -1.0F, 7, 6, 2, 0.0F);
        this.setRotateAngle(Equip102, 0.7853981633974483F, 0.0F, 0.0F);
        this.Face4 = new ModelRenderer(this, 98, 113);
        this.Face4.setRotationPoint(0.0F, 0.0F, -0.1F);
        this.Face4.addBox(-7.0F, -14.2F, -6.5F, 14, 14, 1, 0.0F);
        this.ArmRight01.addChild(this.ArmRight02);
        this.LegLeft01.addChild(this.LegLeft02);
        this.Head.addChild(this.Hair);
        this.BodyMain.addChild(this.EquipBase1);
        this.BodyMain.addChild(this.Butt);
        this.Butt.addChild(this.LegLeft01);
        this.Equip101.addChild(this.Equip103);
        this.EquipBase2.addChild(this.Equip204);
        this.BodyMain.addChild(this.EquipBase2);
        this.EquipBase2.addChild(this.Equip203);
        this.LegRight01.addChild(this.LegRight02);
        this.HairMain.addChild(this.Hair01);
        this.EquipBase2.addChild(this.Equip202);
        this.BodyMain.addChild(this.Neck);
        this.EquipBase1.addChild(this.Equip101);
        this.Equip101.addChild(this.Equip104);
        this.Neck.addChild(this.Head);
        this.Hair.addChild(this.HairR01);
        this.BodyMain.addChild(this.ArmRight01);
        this.Hair.addChild(this.HairL01);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.Head.addChild(this.HairMain);
        this.BodyMain.addChild(this.Cloth01);
        this.Butt.addChild(this.LegRight01);
        this.EquipBase2.addChild(this.Equip201);
        this.HairL01.addChild(this.HairL02);
        this.BodyMain.addChild(this.ArmLeft01);
        this.Hair.addChild(this.Ahoke);
        this.HairR01.addChild(this.HairR02);
        this.Equip101.addChild(this.Equip102);
        
        //發光支架
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -13.5F, 0.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
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
        this.GlowHead.addChild(this.FlowerBase);
        this.FlowerBase.addChild(this.Flower1);
        this.FlowerBase.addChild(this.Flower2);
        this.FlowerBase.addChild(this.Flower3);
        this.FlowerBase.addChild(this.Flower4);
        
    }
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	GL11.glPushMatrix();       
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glScalef(0.36F, 0.36F, 0.36F);
    	GL11.glTranslatef(0F, 2.7F, 0F);
    	
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    	this.BodyMain.render(f5);
    	
    	GL11.glDisable(GL11.GL_LIGHTING);
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
    	this.GlowBodyMain.render(f5);
    	GL11.glEnable(GL11.GL_LIGHTING);
    	
    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glPopMatrix();
    }
    
    //for idle/run animation
    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) { 	
    	super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		IShipEmotion ent = (IShipEmotion)entity;
		
		showEquip(ent);
		
		EmotionHelper.rollEmotion(this, ent);

		if(ent.getStateFlag(ID.F.NoFuel)) {
			motionStopPos(f, f1, f2, f3, f4, ent);
		}
		else {
			motionHumanPos(f, f1, f2, f3, f4, ent);
		}
		
		setGlowRotation();
    }
    
    //設定模型發光部份的rotation
    private void setGlowRotation() {
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
    
    private void motionStopPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {
    	GL11.glTranslatef(0F, 2.1F, 0F);
    	setFace(4);
    	
  	    //頭部
	  	this.Head.rotateAngleX = -0.35F;
	  	this.Head.rotateAngleY = 0F;
	  	//body
  	    this.Ahoke.rotateAngleY = 0.5236F;
  	    this.BodyMain.rotateAngleX = -1.6F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.3F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = 3.1F;
    	this.ArmLeft01.rotateAngleZ = 0.7F;
		this.ArmRight01.rotateAngleX = 3.1F;
    	this.ArmRight01.rotateAngleZ = -0.7F;
    	this.ArmLeft02.rotateAngleX = 0F;
    	this.ArmRight02.rotateAngleX = 0F;
		//leg
    	this.LegLeft01.rotateAngleX = -0.2F;
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = -0.1F;
		this.LegRight01.rotateAngleX = -0.2F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = 0.1F;
		this.LegLeft02.rotateAngleX = 0F;
    	this.LegRight02.rotateAngleX = 0F;
    	//equip
    	this.EquipBase1.offsetZ = 0F;
    	this.EquipBase2.offsetY = 0F;
    	this.EquipBase2.rotateAngleX = 0.3142F;
		
    }
    
    //pose
  	private void motionHumanPos(float f, float f1, float f2, float f3, float f4, IShipEmotion ent) {   
  		float angleX = MathHelper.cos(f2*0.08F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1;
  		float addk1 = 0;
  		float addk2 = 0;
  		
  		//leg move parm
  		addk1 = angleAdd1 - 0.122F;
	  	addk2 = angleAdd2 - 0.122F;

  	    //移動頭部使其看人
	  	this.Head.rotateAngleX = f4 / 57.29578F; 	//上下角度
	  	this.Head.rotateAngleY = f3 / 57.29578F;	//左右角度 角度轉成rad 即除以57.29578
	    
	    //正常站立動作
	  	//body
  	    this.Ahoke.rotateAngleY = angleX * 0.25F + 0.5236F;
	  	this.BodyMain.rotateAngleX = -0.1F;
	  	//hair
	  	this.Hair01.rotateAngleX = angleX * 0.06F + 0.3F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = 0.157F;
	    this.ArmRight01.rotateAngleX = 0.157F;
    	this.ArmLeft01.rotateAngleZ = -0.384F;
    	this.ArmRight01.rotateAngleZ = 0.384F;
    	this.ArmLeft02.rotateAngleX = 0F;
    	this.ArmRight02.rotateAngleX = 0F;
		//leg
		this.LegLeft01.rotateAngleY = 0F;
		this.LegLeft01.rotateAngleZ = -0.035F;
		this.LegRight01.rotateAngleY = 0F;
		this.LegRight01.rotateAngleZ = 0.035F;
		this.LegLeft02.rotateAngleX = 0F;
    	this.LegRight02.rotateAngleX = 0F;
    	//equip
    	this.EquipBase1.offsetZ = 0F;
    	this.EquipBase2.offsetY = 0F;
    	this.EquipBase2.rotateAngleX = 0.3142F;

	    if(ent.getIsSprinting() || f1 > 0.9F) {	//奔跑動作
	    	setFace(3);
	    	//Body
			this.BodyMain.rotateAngleX = 0.1745F;
			this.Head.rotateAngleX -= 0.35F;
			//leg move parm
	  		addk1 -= 0.25F;
		  	addk2 -= 0.25F;
		  	
	    	//change run type base on tickExisted
			if(ent.getTickExisted() % 256 > 128) {			//run type 1
				//arm 
				this.ArmLeft01.rotateAngleX = 2.6F;
		    	this.ArmLeft01.rotateAngleZ = 0.7F;
				this.ArmRight01.rotateAngleX = 2.6F;
		    	this.ArmRight01.rotateAngleZ = -0.7F;	
			}
			else {	
			  	//arm 
			    this.ArmRight01.rotateAngleX = -2.8F;
		    	this.ArmRight01.rotateAngleZ = -0.7F;
			}	
  		}

	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if(ent.getIsSneaking()) {		//潛行, 蹲下動作
	    	//body
	    	this.Head.rotateAngleX -= 0.8727F;
	    	this.BodyMain.rotateAngleX = 1.0472F;
		  	//hair
		  	this.Hair01.rotateAngleX += 0.2236F;
		  	//leg
		  	addk1 -= 1.0472F;
		  	addk2 -= 1.0472F;
  		}//end if sneaking
  		
	    if(ent.getIsSitting() || ent.getIsRiding()) {  //騎乘動作
	    	if(ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED) {
	    		if(((IShipFloating)ent).getShipDepth() > 0) {
		    		GL11.glTranslatef(0F, 1F, 0F);
		    	}
		    	else {
		    		GL11.glTranslatef(0F, 1.7F, 0F);
		    	}
		    	//body
		    	this.Head.rotateAngleX += 0.35F;
		    	this.BodyMain.rotateAngleX = -0.7F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = 0.5236F;
		    	this.ArmLeft01.rotateAngleZ = -0.5236F;
		    	this.ArmLeft02.rotateAngleX = -1.0472F;
		    	this.ArmRight01.rotateAngleX = 0.7F;
		    	this.ArmRight01.rotateAngleZ = 0.5236F;
		    	this.ArmRight02.rotateAngleX = -1.0472F;
		    	//leg
		    	addk1 = -2.1F;
		    	addk2 = -angleX * 0.1F - 2.2F;
		    	this.LegLeft02.rotateAngleX = angleX * 0.1F + 1.2F;
		    	this.LegRight02.rotateAngleX = -angleX * 0.4F + 0.8F;
		    	//equip
		    	this.EquipBase1.offsetZ = -0.9F;
		    	this.EquipBase2.isHidden = false;
		    	this.EquipBase2.rotateAngleX = 0.7F;
	    	}
	    	else {
	    		if(((IShipFloating)ent).getShipDepth() > 0) {
		    		GL11.glTranslatef(0F, 0.9F, 0F);
		    	}
		    	else {
		    		GL11.glTranslatef(0F, 1.5F, 0F);
		    	}	
		    	//body
		    	this.Head.rotateAngleX += 0.2F;
		    	this.BodyMain.rotateAngleX = -0.7F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = 0.95F;
		    	this.ArmLeft01.rotateAngleZ = -0.3146F;
		    	this.ArmRight01.rotateAngleX = 0.95F;
		    	this.ArmRight01.rotateAngleZ = 0.3146F;
		    	//leg
		    	addk1 = -1.1F;
		    	addk2 = -1.1F;
		    	this.LegLeft01.rotateAngleY = -0.3491F;
		    	this.LegRight01.rotateAngleY = 0.3491F;
		    	//equip
		    	this.EquipBase1.offsetZ = -0.15F;
		    	this.EquipBase2.offsetY = -0.15F;
	    	}    	
  		}//end if sitting
	    
	    //攻擊動作
	    if(ent.getAttackTime() > 0) {
	    	setFace(3);
	    	//arm
	    	this.ArmLeft01.rotateAngleX = -2.27F;
	    	this.ArmLeft01.rotateAngleZ = 0.87F;
		    this.ArmRight01.rotateAngleX = -2.27F;
	    	this.ArmRight01.rotateAngleZ = -0.87F;
	    }
	    
	    //swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if(f6 != 0F) {
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX = -0.4F;
	        this.ArmRight01.rotateAngleY = 0F;
	        this.ArmRight01.rotateAngleZ = -0.2F;
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.RAD_MUL;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.RAD_MUL + 0.2F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.RAD_MUL;
	  	}
	    
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
  	}
  	
  	private void showEquip(IShipEmotion ent) {
		switch(ent.getStateEmotion(ID.S.State)) {
		case ID.State.EQUIP00:
			this.EquipBase1.isHidden = false;
			this.EquipBase2.isHidden = true;
			break;
		case ID.State.EQUIP01:
			this.EquipBase1.isHidden = true;
			this.EquipBase2.isHidden = false;
			break;
		case ID.State.EQUIP02:
			this.EquipBase1.isHidden = false;
			this.EquipBase2.isHidden = false;
			break;
		default:
			this.EquipBase1.isHidden = true;
			this.EquipBase2.isHidden = true;
			break;
		}
  	}
  	
    //設定顯示的臉型
  	@Override
  	public void setFace(int emo) {
  		switch(emo) {
  		case 0:
  			this.Face0.isHidden = false;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 1:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = false;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 2:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = false;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = true;
  			break;
  		case 3:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = false;
  			this.Face4.isHidden = true;
  			break;
  		case 4:
  			this.Face0.isHidden = true;
  			this.Face1.isHidden = true;
  			this.Face2.isHidden = true;
  			this.Face3.isHidden = true;
  			this.Face4.isHidden = false;
  			break;
  		default:
  			break;
  		}
  	}

    
}
