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
 * ModelSubmKa - PinkaLulan
 * Created using Tabula 4.1.1  2016/5/5
 */
public class ModelSubmKa extends ShipModelBaseAdv
{
	
    public ModelRenderer BodyMain;
    public ModelRenderer Butt;
    public ModelRenderer Head;
    public ModelRenderer ArmLeft01;
    public ModelRenderer ArmRight01;
    public ModelRenderer BodyMain1;
    public ModelRenderer BodyMain2;
    public ModelRenderer BoobL;
    public ModelRenderer BoobL2;
    public ModelRenderer BoobR;
    public ModelRenderer BoobR2;
    public ModelRenderer Butt1;
    public ModelRenderer Butt2;
    public ModelRenderer EquipBase;
    public ModelRenderer LegRight01;
    public ModelRenderer LegLeft01;
    public ModelRenderer LegRight02;
    public ModelRenderer LegLeft02;
    public ModelRenderer Hair;
    public ModelRenderer HairMain;
    public ModelRenderer EquipHead01;
    public ModelRenderer Ahoke;
    public ModelRenderer HairU01;
    public ModelRenderer HairL01;
    public ModelRenderer HairR01;
    public ModelRenderer HairL02;
    public ModelRenderer HairR02;
    public ModelRenderer Hair01;
    public ModelRenderer Hair02;
    public ModelRenderer Hair03;
    public ModelRenderer EquipHead02;
    public ModelRenderer EquipHead03;
    public ModelRenderer EquipHead04;
    public ModelRenderer EquipHead05;
    public ModelRenderer ArmLeft02;
    public ModelRenderer EquipT01a;
    public ModelRenderer EquipT01b;
    public ModelRenderer ArmRight02;
    public ModelRenderer EquipC01;
    public ModelRenderer EquipC02;
    public ModelRenderer EquipC02a;
    public ModelRenderer EquipC02b;
    public ModelRenderer GlowBodyMain;
    public ModelRenderer GlowHead;
    public ModelRenderer GlowArmLeft01;
    public ModelRenderer GlowArmLeft02;
    

    public ModelSubmKa()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.scale = 0.47F;
        this.offsetY = 1.78F;
        this.offsetItem = new float[] {0.08F, 0.96F, -0.08F};
        this.offsetBlock = new float[] {0.08F, 0.96F, -0.08F};
        
        this.setDefaultFaceModel();
        
        this.EquipHead03 = new ModelRenderer(this, 0, 0);
        this.EquipHead03.setRotationPoint(2.0F, 0.5F, -1.0F);
        this.EquipHead03.addBox(0.0F, -1.0F, -1.0F, 8, 2, 2, 0.0F);
        this.setRotateAngle(EquipHead03, 0.0F, -0.08726646259971647F, 0.0F);
        this.Head = new ModelRenderer(this, 44, 101);
        this.Head.setRotationPoint(0.0F, -11.8F, -0.5F);
        this.Head.addBox(-7.0F, -14.5F, -6.5F, 14, 14, 13, 0.0F);
        this.Hair = new ModelRenderer(this, 50, 81);
        this.Hair.setRotationPoint(0.0F, -7.5F, 0.4F);
        this.Hair.addBox(-8.0F, -8.0F, -7.4F, 16, 12, 8, 0.0F);
        this.HairL01 = new ModelRenderer(this, 24, 91);
        this.HairL01.setRotationPoint(-4.9F, 8.0F, -7.2F);
        this.HairL01.addBox(-2.5F, 0.0F, 0.0F, 5, 9, 2, 0.0F);
        this.setRotateAngle(HairL01, -0.17453292519943295F, 0.13962634015954636F, -0.05235987755982988F);
        this.BoobR = new ModelRenderer(this, 34, 102);
        this.BoobR.setRotationPoint(-3.2F, -8.6F, -3.9F);
        this.BoobR.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobR, -0.6981317007977318F, 0.08726646259971647F, 0.06981317007977318F);
        this.BodyMain2 = new ModelRenderer(this, 88, 0);
        this.BodyMain2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain2.addBox(-6.5F, -11.0F, -4.0F, 13, 15, 7, 0.0F);
        this.Hair02 = new ModelRenderer(this, 0, 63);
        this.Hair02.setRotationPoint(0.0F, 13.5F, 5.5F);
        this.Hair02.addBox(-8.0F, 0.0F, -5.0F, 16, 16, 8, 0.0F);
        this.setRotateAngle(Hair02, -0.08726646259971647F, 0.0F, 0.0F);
        this.ArmLeft01 = new ModelRenderer(this, 2, 88);
        this.ArmLeft01.mirror = true;
        this.ArmLeft01.setRotationPoint(7.8F, -9.7F, -0.7F);
        this.ArmLeft01.addBox(-2.0F, -1.0F, -2.5F, 5, 11, 5, 0.0F);
        this.setRotateAngle(ArmLeft01, 0.20943951023931953F, 0.0F, -0.3141592653589793F);
        this.HairR02 = new ModelRenderer(this, 24, 88);
        this.HairR02.mirror = true;
        this.HairR02.setRotationPoint(0.3F, 10.0F, 0.0F);
        this.HairR02.addBox(-1.0F, 0.0F, 0.0F, 2, 12, 5, 0.0F);
        this.setRotateAngle(HairR02, 0.24434609527920614F, 0.08726646259971647F, -0.13962634015954636F);
        this.Butt2 = new ModelRenderer(this, 82, 22);
        this.Butt2.setRotationPoint(0.0F, 2.5F, 2.8F);
        this.Butt2.addBox(-7.5F, 0.0F, -7.0F, 15, 7, 8, 0.0F);
        this.setRotateAngle(Butt2, 0.20943951023931953F, 0.0F, 0.0F);
        this.BoobL2 = new ModelRenderer(this, 65, 34);
        this.BoobL2.mirror = true;
        this.BoobL2.setRotationPoint(2.44F, -8.6F, -3.9F);
        this.BoobL2.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobL2, -0.6981317007977318F, -0.08726646259971647F, -0.06981317007977318F);
        this.BodyMain1 = new ModelRenderer(this, 0, 106);
        this.BodyMain1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyMain1.addBox(-6.5F, -11.0F, -4.0F, 13, 15, 7, 0.0F);
        this.BodyMain = new ModelRenderer(this, 0, 106);
        this.BodyMain.setRotationPoint(0.0F, -11.0F, -3.0F);
        this.BodyMain.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(BodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.LegRight02 = new ModelRenderer(this, 0, 87);
        this.LegRight02.setRotationPoint(3.0F, 12.0F, -3.0F);
        this.LegRight02.addBox(-6.0F, 0.0F, 0.0F, 6, 13, 6, 0.0F);
        this.EquipHead05 = new ModelRenderer(this, 0, 0);
        this.EquipHead05.setRotationPoint(0.0F, -0.4F, 14.0F);
        this.EquipHead05.addBox(-1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(EquipHead05, 0.0F, 0.0F, 0.5918411493512771F);
        this.LegLeft02 = new ModelRenderer(this, 0, 87);
        this.LegLeft02.mirror = true;
        this.LegLeft02.setRotationPoint(-3.0F, 12.0F, -3.0F);
        this.LegLeft02.addBox(0.0F, 0.0F, 0.0F, 6, 13, 6, 0.0F);
        this.EquipHead01 = new ModelRenderer(this, 0, 0);
        this.EquipHead01.setRotationPoint(0.0F, -1.0F, -8.0F);
        this.EquipHead01.addBox(-3.0F, -3.0F, 0.0F, 6, 6, 3, 0.0F);
        this.setRotateAngle(EquipHead01, 0.2617993877991494F, 0.0F, 0.0F);
        this.Ahoke = new ModelRenderer(this, 39, 14);
        this.Ahoke.setRotationPoint(-1.0F, -7.0F, -5.5F);
        this.Ahoke.addBox(0.0F, -5.0F, -10.5F, 0, 11, 11, 0.0F);
        this.setRotateAngle(Ahoke, 0.2617993877991494F, 0.6981317007977318F, 0.0F);
        this.ArmRight02 = new ModelRenderer(this, 2, 88);
        this.ArmRight02.setRotationPoint(-3.0F, 10.0F, 2.5F);
        this.ArmRight02.addBox(0.0F, 0.0F, -5.0F, 5, 11, 5, 0.0F);
        this.LegLeft01 = new ModelRenderer(this, 0, 87);
        this.LegLeft01.mirror = true;
        this.LegLeft01.setRotationPoint(4.4F, 6.5F, -4.0F);
        this.LegLeft01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegLeft01, -0.15707963267948966F, 0.0F, 0.10471975511965977F);
        this.EquipBase = new ModelRenderer(this, 0, 0);
        this.EquipBase.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.EquipBase.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
        this.Hair01 = new ModelRenderer(this, 0, 62);
        this.Hair01.setRotationPoint(0.0F, 8.0F, 1.1F);
        this.Hair01.addBox(-7.5F, 0.0F, 0.0F, 15, 16, 9, 0.0F);
        this.setRotateAngle(Hair01, 0.20943951023931953F, 0.0F, 0.0F);
        this.HairMain = new ModelRenderer(this, 46, 104);
        this.HairMain.setRotationPoint(0.0F, -14.8F, -3.0F);
        this.HairMain.addBox(-7.5F, 0.0F, 0.0F, 15, 11, 10, 0.0F);
        this.HairU01 = new ModelRenderer(this, 50, 44);
        this.HairU01.mirror = true;
        this.HairU01.setRotationPoint(0.0F, -6.0F, -7.7F);
        this.HairU01.addBox(-8.5F, 0.0F, 0.0F, 17, 15, 7, 0.0F);
        this.Hair03 = new ModelRenderer(this, 0, 40);
        this.Hair03.setRotationPoint(0.0F, 12.5F, 0.0F);
        this.Hair03.addBox(-8.0F, 0.0F, -4.5F, 16, 15, 7, 0.0F);
        this.setRotateAngle(Hair03, -0.13962634015954636F, 0.0F, 0.0F);
        this.EquipT01a = new ModelRenderer(this, 16, 0);
        this.EquipT01a.setRotationPoint(-6.5F, 6.5F, -1.0F);
        this.EquipT01a.addBox(-2.0F, -3.0F, -5.0F, 4, 6, 10, 0.0F);
        this.EquipC01 = new ModelRenderer(this, 0, 0);
        this.EquipC01.setRotationPoint(0.0F, -9.0F, 3.0F);
        this.EquipC01.addBox(-6.0F, 0.0F, 0.0F, 12, 10, 4, 0.0F);
        this.EquipC02b = new ModelRenderer(this, 0, 0);
        this.EquipC02b.setRotationPoint(3.0F, -2.0F, 1.0F);
        this.EquipC02b.addBox(0.0F, 0.0F, 0.0F, 9, 3, 3, 0.0F);
        this.HairR01 = new ModelRenderer(this, 24, 88);
        this.HairR01.mirror = true;
        this.HairR01.setRotationPoint(5.9F, 7.7F, -7.5F);
        this.HairR01.addBox(-1.0F, 0.0F, 0.0F, 2, 11, 5, 0.0F);
        this.setRotateAngle(HairR01, -0.17453292519943295F, 0.4363323129985824F, 0.13962634015954636F);
        this.Butt1 = new ModelRenderer(this, 52, 66);
        this.Butt1.setRotationPoint(0.0F, 2.5F, 2.8F);
        this.Butt1.addBox(-7.5F, 0.0F, -7.0F, 15, 7, 8, 0.0F);
        this.setRotateAngle(Butt1, 0.20943951023931953F, 0.0F, 0.0F);
        this.HairL02 = new ModelRenderer(this, 24, 91);
        this.HairL02.setRotationPoint(-0.3F, 7.5F, 0.1F);
        this.HairL02.addBox(-2.5F, 0.0F, 0.0F, 5, 12, 2, 0.0F);
        this.setRotateAngle(HairL02, 0.3141592653589793F, 0.17453292519943295F, 0.17453292519943295F);
        this.Butt = new ModelRenderer(this, 52, 66);
        this.Butt.setRotationPoint(0.0F, 2.5F, 2.8F);
        this.setRotateAngle(Butt, 0.20943951023931953F, 0.0F, 0.0F);
        this.LegRight01 = new ModelRenderer(this, 0, 87);
        this.LegRight01.setRotationPoint(-4.4F, 6.5F, -4.0F);
        this.LegRight01.addBox(-3.0F, 0.0F, -3.0F, 6, 12, 6, 0.0F);
        this.setRotateAngle(LegRight01, -0.03490658503988659F, 0.0F, -0.10471975511965977F);
        this.EquipHead02 = new ModelRenderer(this, 0, 0);
        this.EquipHead02.setRotationPoint(0.0F, 0.5F, -3.0F);
        this.EquipHead02.addBox(-2.0F, -2.0F, 0.0F, 4, 4, 3, 0.0F);
        this.EquipC02a = new ModelRenderer(this, 0, 0);
        this.EquipC02a.setRotationPoint(0.0F, -3.0F, 8.0F);
        this.EquipC02a.addBox(-1.0F, -11.0F, -1.0F, 2, 11, 2, 0.0F);
        this.BoobR2 = new ModelRenderer(this, 106, 37);
        this.BoobR2.setRotationPoint(-2.44F, -8.6F, -3.9F);
        this.BoobR2.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobR2, -0.6981317007977318F, 0.08726646259971647F, 0.06981317007977318F);
        this.EquipHead04 = new ModelRenderer(this, 0, 0);
        this.EquipHead04.setRotationPoint(7.0F, 0.0F, -1.0F);
        this.EquipHead04.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 15, 0.0F);
        this.setRotateAngle(EquipHead04, -0.17453292519943295F, 0.08726646259971647F, 0.0F);
        this.ArmLeft02 = new ModelRenderer(this, 2, 88);
        this.ArmLeft02.mirror = true;
        this.ArmLeft02.setRotationPoint(3.0F, 10.0F, 2.5F);
        this.ArmLeft02.addBox(-5.0F, 0.0F, -5.0F, 5, 11, 5, 0.0F);
        this.ArmRight01 = new ModelRenderer(this, 2, 88);
        this.ArmRight01.setRotationPoint(-7.8F, -9.7F, -0.7F);
        this.ArmRight01.addBox(-3.0F, -1.0F, -2.5F, 5, 11, 5, 0.0F);
        this.setRotateAngle(ArmRight01, 0.0F, 0.0F, 0.20943951023931953F);
        this.BoobL = new ModelRenderer(this, 34, 102);
        this.BoobL.mirror = true;
        this.BoobL.setRotationPoint(3.2F, -8.6F, -3.9F);
        this.BoobL.addBox(-3.0F, 0.0F, 0.0F, 6, 5, 5, 0.0F);
        this.setRotateAngle(BoobL, -0.6981317007977318F, -0.08726646259971647F, -0.06981317007977318F);
        this.EquipC02 = new ModelRenderer(this, 0, 0);
        this.EquipC02.setRotationPoint(0.0F, 5.0F, 3.0F);
        this.EquipC02.addBox(-3.5F, -3.5F, 0.0F, 7, 7, 10, 0.0F);
        this.setRotateAngle(EquipC02, -0.08726646259971647F, 0.0F, 0.0F);
        this.EquipT01b = new ModelRenderer(this, 0, 10);
        this.EquipT01b.setRotationPoint(0.0F, 0.0F, -12.9F);
        this.EquipT01b.addBox(-2.5F, -3.5F, 0.0F, 5, 7, 8, 0.0F);
        this.EquipHead01.addChild(this.EquipHead03);
        this.BodyMain.addChild(this.Head);
        this.Head.addChild(this.Hair);
        this.Hair.addChild(this.HairL01);
        this.BodyMain.addChild(this.BoobR);
        this.BodyMain.addChild(this.BodyMain2);
        this.Hair01.addChild(this.Hair02);
        this.BodyMain.addChild(this.ArmLeft01);
        this.HairR01.addChild(this.HairR02);
        this.BodyMain.addChild(this.Butt2);
        this.BodyMain.addChild(this.BoobL2);
        this.BodyMain.addChild(this.BodyMain1);
        this.LegRight01.addChild(this.LegRight02);
        this.EquipHead04.addChild(this.EquipHead05);
        this.LegLeft01.addChild(this.LegLeft02);
        this.Head.addChild(this.EquipHead01);
        this.Hair.addChild(this.Ahoke);
        this.ArmRight01.addChild(this.ArmRight02);
        this.Butt.addChild(this.LegLeft01);
        this.BodyMain.addChild(this.EquipBase);
        this.HairMain.addChild(this.Hair01);
        this.Head.addChild(this.HairMain);
        this.Hair.addChild(this.HairU01);
        this.Hair02.addChild(this.Hair03);
        this.EquipBase.addChild(this.EquipC01);
        this.EquipC02.addChild(this.EquipC02b);
        this.Hair.addChild(this.HairR01);
        this.BodyMain.addChild(this.Butt1);
        this.HairL01.addChild(this.HairL02);
        this.BodyMain.addChild(this.Butt);
        this.Butt.addChild(this.LegRight01);
        this.EquipHead01.addChild(this.EquipHead02);
        this.EquipC02.addChild(this.EquipC02a);
        this.BodyMain.addChild(this.BoobR2);
        this.EquipHead03.addChild(this.EquipHead04);
        this.ArmLeft01.addChild(this.ArmLeft02);
        this.BodyMain.addChild(this.ArmRight01);
        this.BodyMain.addChild(this.BoobL);
        this.EquipC01.addChild(this.EquipC02);
        
        //glow part
        this.GlowBodyMain = new ModelRenderer(this, 0, 0);
        this.GlowBodyMain.setRotationPoint(0.0F, -11.0F, -3.0F);
        this.setRotateAngle(GlowBodyMain, -0.10471975511965977F, 0.0F, 0.0F);
        this.GlowHead = new ModelRenderer(this, 0, 0);
        this.GlowHead.setRotationPoint(0.0F, -11.8F, -0.5F);
        this.GlowArmLeft01 = new ModelRenderer(this, 0, 0);
        this.GlowArmLeft01.setRotationPoint(7.8F, -9.7F, -0.7F);
        this.setRotateAngle(GlowArmLeft01, 0.20943951023931953F, 0.0F, -0.3141592653589793F);
        this.GlowArmLeft02 = new ModelRenderer(this, 0, 0);
        this.GlowArmLeft02.setRotationPoint(3.0F, 10.0F, 2.5F);
        
        this.GlowBodyMain.addChild(this.GlowHead);
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
        this.GlowBodyMain.addChild(this.GlowArmLeft01);
        this.GlowArmLeft01.addChild(this.GlowArmLeft02);
        this.GlowArmLeft02.addChild(this.EquipT01a);
        this.EquipT01a.addChild(this.EquipT01b);
        
     	//for held item rendering
        this.armMain = new ModelRenderer[] {this.BodyMain, this.ArmRight01, this.ArmRight02};
        this.armOff = new ModelRenderer[] {this.BodyMain, this.ArmLeft01, this.ArmLeft02};
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
    	GlStateManager.scale(this.scale, this.scale, this.scale);
    	GlStateManager.translate(0F, this.offsetY, 0F);
    	
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
		int state = ent.getStateEmotion(ID.S.State);
		
		boolean flag = !EmotionHelper.checkModelState(0, state);	//cannon
		this.EquipBase.isHidden = flag;
				
		flag = !EmotionHelper.checkModelState(1, state);			//head
		this.EquipHead01.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(2, state);			//cloth
		this.BodyMain1.isHidden = !flag;
		this.Butt1.isHidden = !flag;
		this.BoobL.isHidden = !flag;
		this.BoobR.isHidden = !flag;
		this.BodyMain2.isHidden = flag;
		this.Butt2.isHidden = flag;
		this.BoobL2.isHidden = flag;
		this.BoobR2.isHidden = flag;
		
		flag = !EmotionHelper.checkModelState(3, state);			//weapon
		this.EquipT01a.isHidden = flag;
	}

	@Override
	public void syncRotationGlowPart()
	{
    	//outfit 2
    	this.BoobL2.rotateAngleX = this.BoobL.rotateAngleX;
    	this.BoobR2.rotateAngleX = this.BoobR.rotateAngleX;
    	this.Butt1.rotateAngleX = this.Butt.rotateAngleX;
    	this.Butt2.rotateAngleX = this.Butt.rotateAngleX;
    	
    	//頭部
		this.GlowBodyMain.rotateAngleX = this.BodyMain.rotateAngleX;
		this.GlowBodyMain.rotateAngleY = this.BodyMain.rotateAngleY;
		this.GlowBodyMain.rotateAngleZ = this.BodyMain.rotateAngleZ;
		this.GlowHead.rotateAngleX = this.Head.rotateAngleX;
		this.GlowHead.rotateAngleY = this.Head.rotateAngleY;
		this.GlowHead.rotateAngleZ = this.Head.rotateAngleZ;
		this.GlowArmLeft01.rotateAngleX = this.ArmLeft01.rotateAngleX;
		this.GlowArmLeft01.rotateAngleY = this.ArmLeft01.rotateAngleY;
		this.GlowArmLeft01.rotateAngleZ = this.ArmLeft01.rotateAngleZ;
		this.GlowArmLeft02.rotateAngleX = this.ArmLeft02.rotateAngleX;
		this.GlowArmLeft02.rotateAngleY = this.ArmLeft02.rotateAngleY;
		this.GlowArmLeft02.rotateAngleZ = this.ArmLeft02.rotateAngleZ;
	}

	@Override
	public void applyDeadPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
    	float angleX = MathHelper.cos(f2 * 0.08F);
    	this.setFaceHungry(ent);
    	
    	GlStateManager.translate(0F, angleX * 0.05F, 0F);
	    //body
	    this.Head.rotateAngleX = 0.5F;
	    this.Head.rotateAngleY = 0F;
    	this.BodyMain.rotateAngleX = 1.6F;
    	//hair
    	this.Hair01.rotateAngleX = 0.1F;
    	this.Hair02.rotateAngleX = -0.5F;
    	this.Hair03.rotateAngleX = -0.5F;
    	//arm
    	this.ArmLeft01.rotateAngleX = -1.6F;
	    this.ArmLeft01.rotateAngleY = -0.15F - angleX * 0.05F;
	    this.ArmRight01.rotateAngleX = -1.6F;
	    this.ArmRight01.rotateAngleY = 0.15F + angleX * 0.05F;
	    //leg
	    this.LegLeft01.rotateAngleX = -1.6F;
	    this.LegRight01.rotateAngleX = -1.6F;
	    this.LegLeft01.rotateAngleY = -0.1F - angleX * 0.05F;
	  	this.LegRight01.rotateAngleY = 0.1F + angleX * 0.05F;
	}

	@Override
	public void applyNormalPose(float f, float f1, float f2, float f3, float f4, IShipEmotion ent)
	{
  		float angleX = MathHelper.cos(f2 * 0.08F);
  		float angleX1 = MathHelper.cos(f2*0.1F + 0.3F + f * 0.5F);
  		float angleX2 = MathHelper.cos(f2*0.1F + 0.6F + f * 0.5F);
  		float angleX3 = MathHelper.cos(f2*0.1F + 0.9F + f * 0.5F);
  		float angleAdd1 = MathHelper.cos(f * 0.7F) * f1 * 0.7F;
  		float angleAdd2 = MathHelper.cos(f * 0.7F + 3.1415927F) * f1 * 0.7F;
  		float addk1 = 0F;
  		float addk2 = 0F;
  		float headX = 0F;
  		float headZ = 0F;
  		
  		//水上漂浮
  		if (ent.getShipDepth(0) > 0D)
  		{
  			GlStateManager.translate(0F, angleX * 0.05F + 0.025F, 0F);
    	}
  		
  	    //head
	  	this.Head.rotateAngleX = f4 * 0.014F + 0.1047F;
	  	this.Head.rotateAngleY = f3 * 0.01F;
	  	this.Head.rotateAngleZ = 0F;
	  	headX = this.Head.rotateAngleX * -0.5F;
	    //boob
  	    this.BoobL.rotateAngleX = angleX * 0.08F - 0.76F;
  	    this.BoobR.rotateAngleX = angleX * 0.08F - 0.76F;
	  	//body
  	    this.Ahoke.rotateAngleY = angleX * 0.15F + 0.6F;
	  	this.BodyMain.rotateAngleX = -0.1047F;
	  	this.BodyMain.rotateAngleY = 0F;
	  	this.BodyMain.rotateAngleZ = 0F;
	  	this.Butt.rotateAngleX = 0.21F;
	  	this.Butt.offsetZ = 0F;
	  	//hair
	  	this.Hair01.rotateAngleX = 0.209F;
	  	this.Hair01.rotateAngleZ = 0F;
	  	this.Hair02.rotateAngleX = -0.087F;
	  	this.Hair02.rotateAngleZ = 0F;
	  	this.Hair03.rotateAngleX = -0.139F;
	  	this.Hair03.rotateAngleZ = 0F;
	  	this.HairL01.rotateAngleX = -0.1F;
	  	this.HairL02.rotateAngleX = 0.3142F;
	  	this.HairR01.rotateAngleX = -0.1F;
	  	this.HairR02.rotateAngleX = 0.1745F;
	  	this.HairL01.rotateAngleZ = -0.0524F;
	  	this.HairL02.rotateAngleZ = 0.1745F;
	  	this.HairR01.rotateAngleZ = 0.1396F;
	  	this.HairR02.rotateAngleZ = -0.1396F;
	    //arm 
	  	this.ArmLeft01.rotateAngleX = 0.2094F;
	  	this.ArmLeft01.rotateAngleY = 0F;
	    this.ArmLeft01.rotateAngleZ = -angleX * 0.05F - 0.3142F;
	    this.ArmLeft02.rotateAngleZ = 0F;
	    this.ArmRight01.rotateAngleX = 0F;
	  	this.ArmRight01.rotateAngleY = 0F;
	    this.ArmRight01.rotateAngleZ = angleX * 0.05F + 0.2094F;
	    this.ArmRight02.rotateAngleX = 0F;
		//leg
	    addk1 = angleAdd1 * 0.6F - 0.157F;
	  	addk2 = angleAdd2 * 0.6F - 0.035F;
	  	this.LegLeft01.rotateAngleY = 0F;
	  	this.LegLeft01.rotateAngleZ = 0.1F;
	  	this.LegRight01.rotateAngleY = 0F;
	  	this.LegRight01.rotateAngleZ = -0.1F;
	  	//equip
	  	this.EquipT01a.rotateAngleX = 0.14F;
	  	this.EquipT01a.rotateAngleZ = 0F;
	  	this.EquipT01a.offsetX = 0F;
        this.EquipT01a.offsetY = 0F;
        this.EquipT01a.offsetZ = 0F;
        this.EquipC02.rotateAngleY = this.Head.rotateAngleY;
        this.EquipC02a.rotateAngleX = this.Head.rotateAngleX;

	  	//sprinting
	    if (ent.getIsSprinting() || f1 > 0.92F)
	    {	//奔跑動作
	    	GlStateManager.translate(0F, 0.06F, 0F);
		    //body
		    this.Head.rotateAngleX -= 1.1F;
	    	this.BodyMain.rotateAngleX = 1.2566F;
	    	//arm
	    	this.ArmLeft01.rotateAngleX = -2.5133F;
		    this.ArmLeft01.rotateAngleZ = -0.22F;
		    this.ArmRight01.rotateAngleX = -2.5133F;
		    this.ArmRight01.rotateAngleZ = 0.22F;
		    //leg
		    this.LegLeft01.rotateAngleZ = 0.05F;
		  	this.LegRight01.rotateAngleZ = -0.05F;
		  	//equip
		  	this.EquipT01a.rotateAngleX = 1.2566F;
		  	this.EquipT01a.rotateAngleZ = -0.1885F;
		  	this.EquipT01a.offsetX = -0.08F;
  		}//end is sprinting
	    
	    //head tilt angle
	    this.Head.rotateAngleZ = EmotionHelper.getHeadTiltAngle(ent, f2);
	    
	    if (ent.getIsSneaking())
	    {	//潛行, 蹲下動作
	    	GlStateManager.translate(0F, 0.05F, 0F);
	    	//Body
	    	this.Head.rotateAngleX -= 1.0472F;
		  	this.BodyMain.rotateAngleX = 1.0472F;
		  	this.Butt.rotateAngleX = -0.8378F;
		  	//hair
		  	this.Hair01.rotateAngleX -= 0.1F;
		  	this.Hair02.rotateAngleX -= 0.2F;
		  	this.Hair03.rotateAngleX -= 0.5F;
		  	this.HairR01.rotateAngleZ -= 0.5F;
		  	this.HairR02.rotateAngleZ -= 0.2F;
		    //arm 
		    this.ArmLeft01.rotateAngleX = -0.7F;
		    this.ArmLeft01.rotateAngleZ = 0.2618F;
		    this.ArmRight01.rotateAngleX = -0.7F;
		    this.ArmRight01.rotateAngleZ = -0.2618F;
		    //leg
		    addk1 -= 0.1F;
		  	addk2 -= 0.1F;
  		}//end if sneaking
	    
	    //sitting riding
	    if (ent.getIsSitting() && !ent.getIsRiding())
	    {  //騎乘動作  	
	    	if (ent.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
	    	{
		  		GlStateManager.translate(0F, angleX * 0.05F + 0.18F, 0F);
			    //body
		    	this.Head.rotateAngleX -= 0.8F;
		    	this.BodyMain.rotateAngleX = 0.7854F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -0.8727F;
		    	this.ArmLeft01.rotateAngleY = -0.7854F;
		    	this.ArmLeft01.rotateAngleZ = -0.3491F;
		    	this.ArmLeft02.rotateAngleZ = 1.5708F;
		    	this.ArmRight01.rotateAngleX = -2.3562F;
		    	this.ArmRight01.rotateAngleY = 0.5236F;
		    	this.ArmRight01.rotateAngleZ = -0.2618F;
		    	this.ArmRight02.rotateAngleX = -0.7F;
		    	//leg
		    	addk1 = 0.45F + angleX * 0.1F;
		    	addk2 = 0.45F - angleX * 0.1F;
		    	//equip
		    	this.EquipT01a.isHidden = false;
		    	this.EquipT01a.rotateAngleX = 0.2618F;
	    	}
	    	else
	    	{
	    		GlStateManager.translate(0F, 0.45F, 0F);
		    	//body
		    	this.Head.rotateAngleX -= 0.7F;
		    	this.BodyMain.rotateAngleX = 0.5236F;
		    	//arm
		    	this.ArmLeft01.rotateAngleX = -0.4F;
		    	this.ArmLeft01.rotateAngleZ = 0.3146F;
		    	this.ArmRight01.rotateAngleX = -0.4F;
		    	this.ArmRight01.rotateAngleZ = -0.3146F;
		    	//leg
		    	addk1 = -2.18F;
		    	addk2 = -2.18F;
		    	this.LegLeft01.rotateAngleY = -0.3491F;
		    	this.LegRight01.rotateAngleY = 0.3491F;
	    	}
  		}//end sitting
	    
	    //attack
	    if(ent.getAttackTick() > 41)
	    {
	    	setFaceAttack(ent);
	    	//swing arm
		    float ft = (50 - ent.getAttackTick()) + (f2 - (int)f2);
		    ft *= 0.125F;
	  		float fa = MathHelper.cos(ft * ft * (float)Math.PI);
	        float fb = MathHelper.cos(MathHelper.sqrt(ft) * (float)Math.PI);
	        this.ArmLeft01.rotateAngleX += -fb * 80.0F * Values.N.DIV_PI_180 - 1.6F;
	        this.ArmLeft01.rotateAngleY += fa * 20.0F * Values.N.DIV_PI_180;
	        this.ArmLeft01.rotateAngleZ += fb * 20.0F * Values.N.DIV_PI_180 + 0.4F;
	        //equip
	        this.EquipT01a.offsetX = 0.2F;
	        this.EquipT01a.offsetY = 0.2F;
	        this.EquipT01a.offsetZ = -0.5F;
	    }//end attack
	    
	    //鬢毛調整
	    headX = this.Head.rotateAngleX * -0.5F;
	    headZ = this.Head.rotateAngleZ * -0.5F;
	    this.Hair01.rotateAngleX += angleX1 * 0.08F + headX;
	  	this.Hair02.rotateAngleX += -angleX2 * 0.08F + headX * 0.5F + 0.1F;
	  	this.Hair03.rotateAngleX += -angleX3 * 0.08F + headX * 0.5F + 0.1F;
	    this.Hair01.rotateAngleZ += headZ;
	  	this.Hair02.rotateAngleZ += headZ * 0.5F;
	  	this.Hair03.rotateAngleZ += headZ * 0.5F;
		this.HairL01.rotateAngleX += angleX * 0.04F + headX;
	  	this.HairL02.rotateAngleX += angleX * 0.05F + headX * 0.8F;
	  	this.HairR01.rotateAngleX += angleX * 0.04F + headX;
	  	this.HairR02.rotateAngleX += angleX * 0.05F + headX * 0.8F;
	  	this.HairL01.rotateAngleZ += headZ;
	  	this.HairL02.rotateAngleZ += headZ;
	  	this.HairR01.rotateAngleZ += headZ * 2.5F;
	  	this.HairR02.rotateAngleZ += headZ * 0.8F;
	  	
	  	//swing arm
	  	float f6 = ent.getSwingTime(f2 - (int)f2);
	  	if (f6 != 0F)
	  	{
	  		float f7 = MathHelper.sin(f6 * f6 * (float)Math.PI);
	        float f8 = MathHelper.sin(MathHelper.sqrt(f6) * (float)Math.PI);
	        this.ArmRight01.rotateAngleX += -f8 * 80.0F * Values.N.DIV_PI_180 - 0.3F;
	        this.ArmRight01.rotateAngleY += -f7 * 20.0F * Values.N.DIV_PI_180 + 0.4F;
	        this.ArmRight01.rotateAngleZ += -f8 * 20.0F * Values.N.DIV_PI_180;
	  	}
	  	
	    //leg motion
	    this.LegLeft01.rotateAngleX = addk1;
	    this.LegRight01.rotateAngleX = addk2;
	}
	
	
}