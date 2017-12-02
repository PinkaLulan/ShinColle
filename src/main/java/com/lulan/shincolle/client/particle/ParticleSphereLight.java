package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;

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

/**
 * SPHERE LIGHT PARTICLE
 */
public class ParticleSphereLight extends Particle
{

	private static final ResourceLocation TEXTURE1 = new ResourceLocation(Reference.TEXTURES_PARTICLE+"ParticleGradientLine.png");
	private static int NumBeam = 30;
	private int particleType, beamCurrent;
	private Entity host;
	private float[][] beamPos;					//beam position: 0~1: xy, 2~5:RGBA, 6:age
	private float beamRad, beamSpd, beamThick, beamHeight;
	private RenderManager rm;
	
	
    public ParticleSphereLight(Entity entity, int type, float...parms)
    {
        super(entity.world, 0F, 0F, 0F);
        this.setSize(0F, 0F);
        this.host = entity;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleType = type;
        this.canCollide = false;
        this.beamCurrent = 0;					//new beam index
        this.rm = ClientProxy.getMineraft().getRenderManager();
        
		//particleSetting: 0:all, 1:decr, 2:min -> 3:all, 2:decr, 1:min
        NumBeam = (3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 25;
        
        switch (type)
        {
        /**
	     * type 0: light beam radiate IN
	     * type 1: light beam radiate OUT
	     * type 2: light beam radiate UP
	     * type 3: light beam radiate DOWN
	     * type 4: light beam STEADY
	     *   parms: 0:scale, 1:radius, 2:beam speed, 3:beam thickness, 4~7:RGBA
	     * 
	     * type 5: light beam radiate IN custom
	     *   parms: 0:life, 1:scale
         */
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        	this.particleScale = parms[0];
        	this.beamRad = parms[1];
        	this.beamSpd = parms[2];
        	this.beamThick = parms[3];
        	this.particleRed = parms[4];
            this.particleGreen = parms[5];
            this.particleBlue = parms[6];
            this.particleAlpha = parms[7];
            this.beamHeight = parms[8];
            this.particleMaxAge = 40;
            this.beamPos = new float[NumBeam][6];
            this.setPosition(entity.posX, entity.posY+this.beamHeight, entity.posZ);
        break;
        case 5:
        	this.particleMaxAge = (int) parms[0];
        	this.particleScale = parms[1];
        	this.beamRad = 0.5F;
        	this.beamSpd = 0.8F;
        	this.beamThick = 2F;
        	this.particleRed = 0F;
            this.particleGreen = 0F;
            this.particleBlue = 0F;
            this.particleAlpha = 0.8F;
            this.beamHeight = entity.height * 0.5F;
            this.beamPos = new float[NumBeam][6];
            this.setPosition(entity.posX, entity.posY+this.beamHeight, entity.posZ);
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
        GlStateManager.translate(x, y, z);
//        GlStateManager.glNormal3f(0F, 1F, 0F);					//光源為正上方
        GlStateManager.rotate(-rm.playerViewY, 0F, 1F, 0F);		//左右視角調整
        GlStateManager.rotate(rm.playerViewX, 1F, 0F, 0F);		//上下視角調整
        GlStateManager.scale(-0.25F, -0.25F, 0.25F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        
        for (float[] beam : this.beamPos)
        {
        	if (beam[0] == 0F && beam[1] == 0F) continue;
        	float depth = this.rand.nextFloat() * 0.1F;
	        
	        render.pos(this.particleScale * beam[0] - beam[1] * this.beamThick, this.particleScale * beam[1] + beam[0] * this.beamThick, depth).tex(1D, 1D).color(beam[2], beam[3], beam[4], beam[5]).endVertex();
	        render.pos(this.particleScale * beam[0], this.particleScale * beam[1], depth).tex(1D, 0D).color(beam[2], beam[3], beam[4], beam[5]).endVertex();
	        render.pos(beam[0], beam[1], depth).tex(0D, 0D).color(beam[2], beam[3], beam[4], beam[5]).endVertex();
	        render.pos(beam[0] - beam[1] * this.beamThick, beam[1] + beam[0] * this.beamThick, depth).tex(0D, 1D).color(beam[2], beam[3], beam[4], beam[5]).endVertex();
        }
        
        Tessellator.getInstance().draw();
        
        GlStateManager.depthMask(false);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1F, 1F, 1F, 1F);
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
        
        if (this.host != null)
        {
        	this.setPosition(this.host.posX, this.host.posY+this.beamHeight, this.host.posZ);
        }
        
        //update beam
        float[] newpos;
        
        switch (this.particleType)
        {
        case 0:		//IN
        {
        	if (this.particleAge <= 30)
        	{
        		for (int i = 0; i < (3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 3; i++)
        		{
                	//create new beam
                	newpos = CalcHelper.rotateXZByAxis(
            								this.beamRad * (this.rand.nextFloat() + 1F),
    					        			this.beamRad * (this.rand.nextFloat() + 1F),
    					        			this.rand.nextFloat() * 360F * Values.N.DIV_PI_180, 1F);
                	this.beamPos[this.beamCurrent] = new float[] {newpos[0], newpos[1], this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha};
                	this.beamCurrent++;
                	if (this.beamCurrent >= this.beamPos.length) this.beamCurrent = 0;
        		}
        	}
        	
        	//update beam pos: halve dist to (0,0,0)
        	for (int i = 0; i < this.beamPos.length; i++)
        	{
        		//move
        		this.beamPos[i][0] *= this.beamSpd;
        		this.beamPos[i][1] *= this.beamSpd;
        		
        		//min limit
        		if (this.beamPos[i][0] > 0F && this.beamPos[i][0] < 0.001F) this.beamPos[i][0] = 0.001F;
        		if (this.beamPos[i][0] < 0F && this.beamPos[i][0] > -0.001F) this.beamPos[i][0] = -0.001F;
        		if (this.beamPos[i][1] > 0F && this.beamPos[i][1] < 0.001F) this.beamPos[i][1] = 0.001F;
        		if (this.beamPos[i][1] < 0F && this.beamPos[i][1] > -0.001F) this.beamPos[i][1] = -0.001F;
        	}
        }
        break;
        case 1:		//OUT
        {
        	if (this.particleAge <= 40)
        	{
        		for (int i = 0; i < 2; i++)
        		{
                	//create new beam
                	newpos = CalcHelper.rotateXZByAxis(
            								this.beamRad * (this.rand.nextFloat() + 1F),
    					        			this.beamRad * (this.rand.nextFloat() + 1F),
    					        			this.rand.nextFloat() * 540F * Values.N.DIV_PI_180, 1F);
                	this.beamPos[this.beamCurrent] = new float[] {newpos[0], newpos[1], this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha};
                	this.beamCurrent++;
                	if (this.beamCurrent >= this.beamPos.length) this.beamCurrent = 0;
        		}
        	}
        	
        	//update beam pos: halve dist to (0,0,0)
        	for (int i = 0; i < this.beamPos.length; i++)
        	{
        		//move
        		this.beamPos[i][0] *= 1F + this.beamSpd;
        		this.beamPos[i][1] *= 1F + this.beamSpd;
        		
        		if (this.particleAge > 30) this.beamPos[i][5] *= 0.8F;
        	}
        }
        break;
        case 2:		//UP or DOWN
        case 3:		//STEADY
    	break;
        case 5:		//IN custom data
        {
        	if (this.particleAge <= this.particleMaxAge * 0.95F)
        	{
        		if (this.particleAge > this.particleMaxAge * 0.5F)
        		{
        			this.particleAlpha *= 0.8F;
        		}
        		
        		for (int i = 0; i < (3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 3; i++)
        		{
                	//create new beam
                	newpos = CalcHelper.rotateXZByAxis(
            								this.beamRad * (this.rand.nextFloat() + 1F),
    					        			this.beamRad * (this.rand.nextFloat() + 1F),
    					        			this.rand.nextFloat() * 360F * Values.N.DIV_PI_180, 1F);
                	this.beamPos[this.beamCurrent] = new float[] {newpos[0], newpos[1], this.particleRed + this.rand.nextFloat() * 0.1F, this.particleGreen, this.particleBlue + this.rand.nextFloat() * 0.2F, this.particleAlpha};
                	this.beamCurrent++;
                	if (this.beamCurrent >= this.beamPos.length) this.beamCurrent = 0;
        		}
        	}
        	
        	//update beam pos: halve dist to (0,0,0)
        	for (int i = 0; i < this.beamPos.length; i++)
        	{
        		//move
        		this.beamPos[i][0] *= this.beamSpd;
        		this.beamPos[i][1] *= this.beamSpd;
        		
        		//min limit
        		if (this.beamPos[i][0] > 0F && this.beamPos[i][0] < 0.001F) this.beamPos[i][0] = 0.001F;
        		if (this.beamPos[i][0] < 0F && this.beamPos[i][0] > -0.001F) this.beamPos[i][0] = -0.001F;
        		if (this.beamPos[i][1] > 0F && this.beamPos[i][1] < 0.001F) this.beamPos[i][1] = 0.001F;
        		if (this.beamPos[i][1] < 0F && this.beamPos[i][1] > -0.001F) this.beamPos[i][1] = -0.001F;
        	}
        }
        break;
        }
        
    }
    
    
}