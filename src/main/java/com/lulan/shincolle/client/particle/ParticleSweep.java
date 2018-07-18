package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.handler.EventHandler;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

/**
 * SPHERE LIGHT PARTICLE
 */
public class ParticleSweep extends Particle
{
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation("textures/entity/sweep.png");
    private int particleType;
	private Entity host;
	private float swpFad, swpSpd, swpScale1, swpScale2, swpScale3, swpAngle;
	
	
    public ParticleSweep(Entity entity, int type, float...parms)
    {
        super(entity.world, 0F, 0F, 0F);
        this.setSize(0F, 0F);
        this.host = entity;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleType = type;
        this.canCollide = false;
        
        switch (type)
        {
        /**
	     * type 0: sword sweep right to left
	     * parms: 0:scale1, 1:scale2, 2:fade, 3:speed , 3~6:RGBA
	     *   scale1: 高度
	     *   scale2: 前後寬度
	     *   scale3: 左右寬度, 使特效由垂直變斜
         */
        case 0:
        	this.swpScale1 = parms[0];
        	this.swpScale2 = parms[1];
        	this.swpScale3 = parms[2];
        	this.swpFad = parms[3];
        	this.particleMaxAge = (int) parms[4];
        	this.particleRed = parms[5];
            this.particleGreen = parms[6];
            this.particleBlue = parms[7];
            this.particleAlpha = parms[8];
            this.setPosition(entity.posX, entity.posY + entity.height * 0.6F, entity.posZ);
            
        	if (this.host instanceof EntityLivingBase)
        	{
        		this.swpAngle = ((EntityLivingBase) this.host).renderYawOffset;
        	}
        	else
        	{
        		this.swpAngle = this.host.rotationYaw;
        	}
        break;
        }
        
        //init pos
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
    }
    
    @Override
    public void renderParticle(VertexBuffer render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {
    	int i = (int) ((float)(this.particleAge + ptick) / (float)this.particleMaxAge * 8F);
    	if (i >= 8) return;
    	
    	Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE1);
    	
        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)ptick - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)ptick - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)ptick - interpPosZ);
        float minU = (float)(i % 4) * 0.25F;
        float maxU = minU + 0.24975F;
        float minV = (float)(i / 4) * 0.5F;
        float maxV = minV + 0.4995F;
//        float minU = 0.75F;
//        float maxU = minU + 0.24975F;
//        float minV = 0F;
//        float maxV = minV + 0.4995F;
        float[] pos1 = CalcHelper.rotateXZByAxis(entity.width * 0.35F, 0F, this.swpAngle * Values.N.DIV_PI_180, 1F);
        
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.disableLighting();
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        
        //正面
        render.pos(x + pos1[1] - this.swpScale3 * pos1[0], y - this.swpScale1 * 0.5F, z + pos1[0] + this.swpScale3 * pos1[1]).tex((double)maxU, (double)maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] * this.swpScale2 - this.swpScale3 * pos1[0], y - this.swpScale1 * 0.5F, z + pos1[0] * this.swpScale2 + this.swpScale3 * pos1[1]).tex((double)maxU, (double)minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] * this.swpScale2 * 1.25F + this.swpScale3 * pos1[0], y + this.swpScale1 * 0.8F, z + pos1[0] * this.swpScale2 * 1.25F - this.swpScale3 * pos1[1]).tex((double)minU, (double)minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] + this.swpScale3 * pos1[0], y + this.swpScale1 * 0.8F, z + pos1[0] - this.swpScale3 * pos1[1]).tex((double)minU, (double)maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //反面
        render.pos(x + pos1[1] + this.swpScale3 * pos1[0], y + this.swpScale1 * 0.8F, z + pos1[0] - this.swpScale3 * pos1[1]).tex((double)minU, (double)maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] * this.swpScale2 * 1.25F + this.swpScale3 * pos1[0], y + this.swpScale1 * 0.8F, z + pos1[0] * this.swpScale2 * 1.25F - this.swpScale3 * pos1[1]).tex((double)minU, (double)minV).color(this.particleRed, this.particleGreen, this.particleBlue * 0.5F, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] * this.swpScale2 - this.swpScale3 * pos1[0], y - this.swpScale1 * 0.5F, z + pos1[0] * this.swpScale2 + this.swpScale3 * pos1[1]).tex((double)maxU, (double)minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] - this.swpScale3 * pos1[0], y - this.swpScale1 * 0.5F, z + pos1[0] + this.swpScale3 * pos1[1]).tex((double)maxU, (double)maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        
        pos1 = CalcHelper.rotateXZByAxis(entity.width * 0.3F, 0F, this.swpAngle * Values.N.DIV_PI_180 - 0.001F, 1F);
        
        //正面
        render.pos(x + pos1[1] - this.swpScale3 * pos1[0], y - this.swpScale1 * 0.5F, z + pos1[0] + this.swpScale3 * pos1[1]).tex((double)maxU, (double)maxV).color(this.particleRed * 0.5F, this.particleGreen * 0.5F, this.particleBlue * 0.5F, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] * this.swpScale2 - this.swpScale3 * pos1[0], y - this.swpScale1 * 0.5F, z + pos1[0] * this.swpScale2 + this.swpScale3 * pos1[1]).tex((double)maxU, (double)minV).color(this.particleRed * 0.5F, this.particleGreen * 0.5F, this.particleBlue * 0.5F, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] * this.swpScale2 * 1.25F + this.swpScale3 * 1.25F * pos1[0], y + this.swpScale1 * 0.8F, z + pos1[0] * this.swpScale2 * 1.25F - this.swpScale3 * 1.25F * pos1[1]).tex((double)minU, (double)minV).color(this.particleRed * 0.5F, this.particleGreen * 0.5F, this.particleBlue * 0.5F, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] + this.swpScale3 * 1.25F * pos1[0], y + this.swpScale1 * 0.8F, z + pos1[0] - this.swpScale3 * 1.25F * pos1[1]).tex((double)minU, (double)maxV).color(this.particleRed * 0.5F, this.particleGreen * 0.5F, this.particleBlue * 0.5F, this.particleAlpha).endVertex();
        //反面
        render.pos(x + pos1[1] + this.swpScale3 * 1.25F * pos1[0], y + this.swpScale1 * 0.8F, z + pos1[0] - this.swpScale3 * 1.25F * pos1[1]).tex((double)minU, (double)maxV).color(this.particleRed * 0.5F, this.particleGreen * 0.5F, this.particleBlue * 0.5F, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] * this.swpScale2 * 1.25F + this.swpScale3 * 1.25F * pos1[0], y + this.swpScale1 * 0.8F, z + pos1[0] * this.swpScale2 * 1.25F - this.swpScale3 * 1.25F * pos1[1]).tex((double)minU, (double)minV).color(this.particleRed, this.particleGreen * 0.5F, this.particleBlue * 0.5F, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] * this.swpScale2 - this.swpScale3 * pos1[0], y - this.swpScale1 * 0.5F, z + pos1[0] * this.swpScale2 + this.swpScale3 * pos1[1]).tex((double)maxU, (double)minV).color(this.particleRed * 0.5F, this.particleGreen * 0.5F, this.particleBlue * 0.5F, this.particleAlpha).endVertex();
        render.pos(x + pos1[1] - this.swpScale3 * pos1[0], y - this.swpScale1 * 0.5F, z + pos1[0] + this.swpScale3 * pos1[1]).tex((double)maxU, (double)maxV).color(this.particleRed * 0.5F, this.particleGreen * 0.5F, this.particleBlue * 0.5F, this.particleAlpha).endVertex();
        
        Tessellator.getInstance().draw();
        
        GlStateManager.disableBlend();
    	GlStateManager.enableLighting();
    	GlStateManager.popMatrix();
    }
    
    @Override
    public int getBrightnessForRender(float p_189214_1_)
    {
        return 61680;
    }
    
    //layer: 0:particle 1:terrain 2:items 3:custom
    @Override
    public int getFXLayer()
    {
        return 3;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate()
    {
        //update age
        if (this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
        }
        
    	//update movement
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.setPosition(this.host.posX, this.host.posY + this.host.height * 0.6F, this.host.posZ);

        //update beam
        switch (this.particleType)
        {
        case 0:		//out from host's back
        {
        	//angle
        	if (this.host instanceof EntityLivingBase)
        	{
        		this.swpAngle = ((EntityLivingBase) this.host).renderYawOffset;
        	}
        	else
        	{
        		this.swpAngle = this.host.rotationYaw;
        	}
        	
        	//alpha
        	float age = (float)this.particleAge / (float)this.particleMaxAge;
        	this.particleAlpha *= 0.6F;
        }
        break;
        }
        
    }
    
    
}

