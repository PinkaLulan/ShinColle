package com.lulan.shincolle.client.render;

import com.lulan.shincolle.client.model.ShipModelBaseAdv;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

abstract public class RenderBasic extends RenderLiving<EntityLiving>
{
	
	/** misc model data */
	public static class MiscModel
	{
		public ModelBase model;
		public ResourceLocation texture;
		public Entity entity;
		public Vec3d scale;
		public float rotX;
		public float rotY;
		public float rotZ;
		
		public MiscModel(Entity entity, ModelBase model, ResourceLocation texture)
		{
			this.entity = entity;
			this.model = model;
			this.texture = texture;
			this.scale = new Vec3d(1D, 1D, 1D);
			this.rotX = 0F;
			this.rotY = 0F;
			this.rotZ = 0F;
		}
	}

	protected static final DynamicTexture TEXTURE_BRIGHTNESS = new DynamicTexture(16, 16);
	protected int shipClass = 0;
	protected boolean initModel = true;
	protected ArrayList<MiscModel> miscModelList;
	
	
    public RenderBasic(RenderManager rm)
    {
        super(rm, null, 1F);
    }
    
    abstract protected void setModel();
    abstract protected void setMiscModel();
    abstract protected void setShadowSize();
    
    /** get model and set shadow size on rendering */
    @Override
    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float parTick)
    {
    	//model init
    	if (this.initModel)
    	{
    		this.shipClass = ((IShipCustomTexture) entity).getTextureID();
    		this.initModel = false;
    		setModel();
    		setMiscModel();
    	}
    	
    	//for invisible model
    	if (this.mainModel == null) return;
    	
    	//set shadow size
    	setShadowSize();
    	
    	//tweak shadow size
    	if (((IShipEmotion)entity).getScaleLevel() > 0) this.shadowSize += ((IShipEmotion)entity).getScaleLevel() * 0.4F;

    	super.doRender(entity, x, y, z, yaw, parTick);
    	
    	//reset light
    	int j = entity.getBrightnessForRender();
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)k, (float)l);
    	
        //render misc models
        if (hasMiscModel() && miscModelList != null && miscModelList.size() > 0)
        {
        	for (int i = 0; i < miscModelList.size(); i++)
        	{
        		if (this.mainModel instanceof ShipModelBaseAdv &&
        			!((ShipModelBaseAdv) this.mainModel).shouldRenderMiscModel(i))
        		{
        			continue;
        		}
        		
        		doRenderMisc(entity, i, x, y, z, yaw, parTick);
        	}
        }
    }
    
    /** for more models in one entity, return true and add misc model list */
    protected boolean hasMiscModel()
    {
    	return false;
    }
    
    /** render misc model */
    protected void doRenderMisc(EntityLiving host, int miscID, double x, double y, double z, float yaw, float parTick)
    {
    	MiscModel mm = this.miscModelList.get(miscID);
    	if (mm == null) return;
    	
    	ModelBase model = mm.model;
    	Entity entity = mm.entity;
    	
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        model.swingProgress = this.getSwingProgress(host, parTick);
        boolean shouldSit = host.isRiding() && (host.getRidingEntity() != null && host.getRidingEntity().shouldRiderSit());
        model.isRiding = shouldSit;
        model.isChild = host.isChild();

        try
        {
            float f = this.interpolateRotation(host.prevRenderYawOffset, host.renderYawOffset, parTick);
            float f1 = this.interpolateRotation(host.prevRotationYawHead, host.rotationYawHead, parTick);
            float f2 = f1 - f;

            if (shouldSit && host.getRidingEntity() instanceof EntityLivingBase)
            {
                EntityLivingBase entitylivingbase = (EntityLivingBase) host.getRidingEntity();
                f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, parTick);
                f2 = f1 - f;
                float f3 = MathHelper.wrapDegrees(f2);

                if (f3 < -85.0F)
                {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F)
                {
                    f3 = 85.0F;
                }

                f = f1 - f3;

                if (f3 * f3 > 2500.0F)
                {
                    f += f3 * 0.2F;
                }
            }

            float f7 = host.prevRotationPitch + (host.rotationPitch - host.prevRotationPitch) * parTick;
            GlStateManager.translate((float)x, (float)y, (float)z);
            float f8 = host.ticksExisted + parTick;
            this.applyRotations(host, f8, f, parTick);
            GlStateManager.translate((float)entity.posX, (float)entity.posY, (float)entity.posZ);
            GlStateManager.scale((float)mm.scale.x, (float)mm.scale.y, (float)mm.scale.z);
            GlStateManager.rotate(mm.rotX, 1F, 0F, 0F);
            GlStateManager.rotate(mm.rotY, 0F, 1F, 0F);
            GlStateManager.rotate(mm.rotZ, 0F, 0F, 1F);
            float f4 = this.prepareScale(host, parTick);
            float f5 = 0.0F;
            float f6 = 0.0F;
            
            if (!entity.isRiding())
            {
                f5 = host.prevLimbSwingAmount + (host.limbSwingAmount - host.prevLimbSwingAmount) * parTick;
                f6 = host.limbSwing - host.limbSwingAmount * (1.0F - parTick);
                
                if (host.isChild())
                {
                    f6 *= 3.0F;
                }

                if (f5 > 1.0F)
                {
                    f5 = 1.0F;
                }
            }
            
            GlStateManager.enableAlpha();
            
            if (entity instanceof EntityLivingBase)
            {
            	model.setLivingAnimations((EntityLivingBase) entity, f6, f5, parTick);
            }
            
            model.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);
            
            boolean flag = this.setDoRenderBrightness(host, parTick);
            this.renderMiscModel(mm, f6, f5, f8, f2, f7, f4);

            if (flag)
            {
                this.unsetBrightness();
            }

            GlStateManager.depthMask(true);
            
            if (entity instanceof EntityLiving)
            {
                this.renderLayers((EntityLiving) entity, f6, f5, parTick, f8, f2, f7, f4);
            }

            GlStateManager.disableRescaleNormal();
        }
        catch (Exception e)
        {
            LogHelper.info("EXCEPTION : Render misc model fail: "+e);
            e.printStackTrace();
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
    
    protected void renderMiscModel(MiscModel miscModel, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
    	if (miscModel.texture == null) return;
    	
        boolean flag = !miscModel.entity.isInvisible() || this.renderOutlines;
        boolean flag1 = !flag && !miscModel.entity.isInvisibleToPlayer(Minecraft.getMinecraft().player);

        if (flag || flag1)
        {
        	this.renderManager.renderEngine.bindTexture(miscModel.texture);

            if (flag1)
            {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }

            miscModel.model.render(miscModel.entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

            if (flag1)
            {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }
    }
    
    //change death color
    @Override
    protected boolean setBrightness(EntityLiving entity, float ptick, boolean combineTextures)
    {
        float f = entity.getBrightness();
        int i = this.getColorMultiplier(entity, f, ptick);
        boolean flag = (i >> 24 & 255) > 0;
        boolean flag1 = entity.hurtTime > 0;
        boolean flag2 = entity.deathTime > 0;

        if (!flag && !flag1 && !flag2)
        {
            return false;
        }
        else if (!flag && !combineTextures)
        {
            return false;
        }
        else
        {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            this.brightnessBuffer.position(0);

            //hurt color
            if (flag1)
            {
                this.brightnessBuffer.put(1F);
                this.brightnessBuffer.put(0F);
                this.brightnessBuffer.put(0F);
                this.brightnessBuffer.put(0.1F);
            }
            //dead color
            else if (flag2)
            {
                this.brightnessBuffer.put(0.3F);
                this.brightnessBuffer.put(0F);
                this.brightnessBuffer.put(0F);
                this.brightnessBuffer.put(0.6F);
            }
            else
            {
	            float f1 = (float)(i >> 24 & 255) / 255.0F;
	            float f2 = (float)(i >> 16 & 255) / 255.0F;
	            float f3 = (float)(i >> 8 & 255) / 255.0F;
	            float f4 = (float)(i & 255) / 255.0F;
	            this.brightnessBuffer.put(f2);
	            this.brightnessBuffer.put(f3);
	            this.brightnessBuffer.put(f4);
	            this.brightnessBuffer.put(1.0F - f1);
            }

            this.brightnessBuffer.flip();
            GlStateManager.glTexEnv(8960, 8705, this.brightnessBuffer);
            GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
            GlStateManager.enableTexture2D();
            GlStateManager.bindTexture(TEXTURE_BRIGHTNESS.getGlTextureId());
            GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            return true;
        }
    }
    
	//interpolation
	protected double interp(double start, double end, double pct)
	{
        return start + (end - start) * pct;
    }

    
}