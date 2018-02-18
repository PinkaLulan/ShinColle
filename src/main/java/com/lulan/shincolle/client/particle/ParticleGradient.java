package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ParticleGradient extends Particle
{
	
	private static final ResourceLocation TEXTURE1 = new ResourceLocation(Reference.TEXTURES_PARTICLE+"ParticleGradient.png");
	private int particleType, gradCurrent, gradSpace;
	private Entity host;
	private float[][] gradPos;					//grad position: 0: rad, 1: prev rad, 2~5:RGBA, 6:age
	private float gradRad, gradSpd, gradFad, gradHFad, gradSlope;
	private RenderManager rm;
	
	
    public ParticleGradient(Entity entity, int type, float...parms)
    {
        super(entity.world, 0F, 0F, 0F);
        this.setSize(0F, 0F);
        this.host = entity;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleType = type;
        this.canCollide = false;
        this.gradCurrent = 0;					//new grad index
        this.rm = ClientProxy.getMineraft().getRenderManager();
        
        switch (type)
        {
        /**
         * type 0: gradient radiate IN
	     * type 1: gradient radiate OUT
	     * type 2: gradient radiate OUT and posY updated with host
         */
        case 0:
        case 1:
        	this.particleScale = parms[0];
        	this.gradRad = 0F;
        	this.gradFad = parms[1];
        	this.gradSpd = parms[2];
        	this.gradHFad = 20F;
        	this.gradSlope = 1.5F;
        	this.gradSpace = (int) parms[3];
        	if (this.gradSpace <= 1) this.gradSpace = 1;
        	this.particleRed = parms[4];
            this.particleGreen = parms[5];
            this.particleBlue = parms[6];
            this.particleAlpha = parms[7];
            this.particleMaxAge = 80;
            this.gradPos = new float[20][7];
            this.setPosition(entity.posX, entity.posY, entity.posZ);
        break;
        case 2:
        	this.particleScale = parms[0];
        	this.gradRad = 0F;
        	this.gradFad = parms[1];
        	this.gradSpd = parms[2];
        	this.gradSpace = (int) parms[3];
        	if (this.gradSpace <= 1) this.gradSpace = 1;
        	this.particleRed = parms[4];
            this.particleGreen = parms[5];
            this.particleBlue = parms[6];
            this.particleAlpha = parms[7];
            this.gradHFad = parms[8];
            this.gradSlope = parms[9];
            this.particleMaxAge = 80;
            this.gradPos = new float[20][7];
            this.setPosition(entity.posX, entity.posY, entity.posZ);
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
    	Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE1);
    	
        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * ptick - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * ptick - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * ptick - interpPosZ);
        
    	GlStateManager.pushMatrix();
    	GlStateManager.depthMask(false);
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		
        for (float[] grad : this.gradPos)
        {
        	
        	//too far away, skip
        	if (grad[0] > 6F && grad[0] <= 0F || grad[5] < 0.05F) continue;
        	
        	float rad = grad[1] + (grad[0] - grad[1]) * ptick;
        	float h = (this.gradHFad - grad[6]) / this.gradHFad;
        	if (h < 0.1F) h = 0.1F;
        	
        	render.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_TEX_COLOR);
        	
        	//正面
	        render.pos(x + rad, y, z + rad).tex(0D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad * this.gradSlope, y + this.particleScale * h, z + rad * this.gradSlope).tex(0D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x - rad, y, z + rad).tex(1D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x - rad * this.gradSlope, y + this.particleScale * h, z + rad * this.gradSlope).tex(1D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x - rad, y, z - rad).tex(0D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x - rad * this.gradSlope, y + this.particleScale * h, z - rad * this.gradSlope).tex(0D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad, y, z - rad).tex(1D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad * this.gradSlope, y + this.particleScale * h, z - rad * this.gradSlope).tex(1D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad, y, z + rad).tex(0D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad * this.gradSlope, y + this.particleScale * h, z + rad * this.gradSlope).tex(0D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        
	        //反面
	        render.pos(x + rad, y, z + rad).tex(0D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad * this.gradSlope, y + this.particleScale * h, z + rad * this.gradSlope).tex(0D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad, y, z - rad).tex(1D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad * this.gradSlope, y + this.particleScale * h, z - rad * this.gradSlope).tex(1D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x - rad, y, z - rad).tex(0D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x - rad * this.gradSlope, y + this.particleScale * h, z - rad * this.gradSlope).tex(0D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x - rad, y, z + rad).tex(1D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x - rad * this.gradSlope, y + this.particleScale * h, z + rad * this.gradSlope).tex(1D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad, y, z + rad).tex(0D, 1D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        render.pos(x + rad * this.gradSlope, y + this.particleScale * h, z + rad * this.gradSlope).tex(0D, 0D).color(grad[2], grad[3], grad[4], grad[5]).endVertex();
	        
	        Tessellator.getInstance().draw();
        }
        
        //draw text
    	GlStateManager.enableLighting();
    	GlStateManager.disableBlend();
    	GlStateManager.depthMask(true);
    	GlStateManager.popMatrix();
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
        
        //update grad
        switch (this.particleType)
        {
        case 0:		//IN
    	break;
        case 1:		//OUT
        {
            if (this.host != null)
            {
            	this.setPosition(this.host.posX, this.host.posY, this.host.posZ);
            }
            
        	if (this.particleAge <= 40 && this.particleAge % this.gradSpace == 0)
        	{
            	this.gradPos[this.gradCurrent] = new float[] {0F, 0F, this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha, 0F};
            	this.gradCurrent++;
            	if (this.gradCurrent >= this.gradPos.length) this.gradCurrent = 0;
        	}
        	
        	//update grad pos: halve dist to (0,0,0)
        	for (int i = 0; i < this.gradPos.length; i++)
        	{
        		//move
        		this.gradPos[i][1] = this.gradPos[i][0];	//prev rad for interpolation
        		this.gradPos[i][0] += this.gradSpd;
        		
        		//age++
        		this.gradPos[i][6] += 1;
        		
        		//alpha--
        		if (this.particleAge % 2 == 0) this.gradPos[i][5] *= this.gradFad;
        	}
        }
        break;
        case 2:		//OUT and update posY
        {
        	if (this.particleAge == 1) this.gradPos[0] = new float[] {0F, 0F, this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha, 0F};
        	
    		//move
    		this.gradPos[0][1] = this.gradPos[0][0];	//prev rad for interpolation
    		this.gradPos[0][0] += this.gradSpd;
    		
    		//age++
    		this.gradPos[0][6] += 1;
    		
    		//alpha--
    		if ((this.particleAge & 1) == 0) this.gradPos[0][5] *= this.gradFad;
        }
        break;
        }
        
    }
    

}