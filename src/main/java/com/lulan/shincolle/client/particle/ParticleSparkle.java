package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

/**
 * SPHERE LIGHT PARTICLE
 */
public class ParticleSparkle extends Particle
{
	
	private static int NumBeam = 30;
	private int particleType, beamCurrent;
	private Entity host;
	private float[][] beamPos;					//beam position: 0~1: xy, 2~5:RGBA, 6:age
	private float beamFad, beamSpd, beamThick, beamHeight;
	
	
    public ParticleSparkle(Entity entity, int type, float...parms)
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
        
        switch (type)
        {
        /**
	     * light quad sparkle around point
	     *   0: red random
	     *   2: green random
	     *   3: blue random
	     *   4: RG random
	     *   5: RB random
	     *   6: GB random
	     *   7: RGB random
	     *   8: R only
	     *   9: G only
	     *   10: B only
	     * parms: 0:scale, 1:radius, 2:beam speed, 3:beam thickness, 4~6:motionXYZ, 7:alpha, 8:height
         */
        case 0:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 9:
        case 10:
        	this.particleScale = parms[0];
        	this.beamFad = parms[1];
        	this.beamSpd = parms[2];
        	this.beamThick = parms[3];
        	this.particleRed = parms[4];
            this.particleGreen = parms[5];
            this.particleBlue = parms[6];
            this.particleAlpha = parms[7];
            this.beamHeight = parms[8];
            this.particleMaxAge = 20;
            this.NumBeam = (3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 15;
            this.beamPos = new float[NumBeam][8];
            this.setPosition(entity.posX, entity.posY+this.beamHeight, entity.posZ);
        break;	
        case 8:
        	this.particleScale = parms[0];
        	this.beamFad = parms[1];
        	this.beamSpd = parms[2];
        	this.beamThick = parms[3];
        	this.particleRed = parms[4];
            this.particleGreen = parms[5];
            this.particleBlue = parms[6];
            this.particleAlpha = parms[7];
            this.beamHeight = parms[8];
            this.particleMaxAge = 120;
            this.NumBeam = (3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 30;
            this.beamPos = new float[NumBeam][8];
            this.setPosition(entity.posX, entity.posY+this.beamHeight, entity.posZ);
        break;
        /**
	     * type 1: blue eye fire particle
	     * parms: 0:height, 1:eye x, 2:eye z, 3~6:RGBA
         */
        case 1:
        	this.particleScale = 0.018F;
        	this.beamHeight = parms[0];
        	this.beamFad = parms[1];
        	this.beamSpd = parms[2];
        	this.particleRed = parms[3];
            this.particleGreen = parms[4];
            this.particleBlue = parms[5];
            this.particleAlpha = parms[6];
            this.particleMaxAge = 50;
            this.NumBeam = (3 - ClientProxy.getMineraft().gameSettings.particleSetting) * 15;
            this.beamPos = new float[NumBeam][11];
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
        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * ptick - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * ptick - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * ptick - interpPosZ);
        
        Vec3d[] avec3d = new Vec3d[] {
        		new Vec3d((double)(-cosYaw * this.particleScale - sinYawsinPitch * this.particleScale),
        				  (double)(-cosPitch * this.particleScale),
        				  (double)(-sinYaw * this.particleScale - cosYawsinPitch * this.particleScale)),
        		new Vec3d((double)(-cosYaw * this.particleScale + sinYawsinPitch * this.particleScale),
        				  (double)(cosPitch * this.particleScale),
        				  (double)(-sinYaw * this.particleScale + cosYawsinPitch * this.particleScale)),
        		new Vec3d((double)(cosYaw * this.particleScale + sinYawsinPitch * this.particleScale),
        				  (double)(cosPitch * this.particleScale),
        				  (double)(sinYaw * this.particleScale + cosYawsinPitch * this.particleScale)),
        		new Vec3d((double)(cosYaw * this.particleScale - sinYawsinPitch * this.particleScale),
        				  (double)(-cosPitch * this.particleScale),
        				  (double)(sinYaw * this.particleScale - cosYawsinPitch * this.particleScale))};

    	GlStateManager.pushMatrix();
    	GlStateManager.depthMask(false);
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
    	GlStateManager.disableTexture2D();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		
        for (float[] beam : this.beamPos)
        {
        	if (beam[3] == 0F || beam[4] == 0F || beam[5] == 0F || beam[6] == 0F) continue;
        	float size = (20F - beam[7]) * 0.05F;
	        
	        render.pos((double)x + beam[0] + avec3d[0].xCoord * size, (double)y + beam[1] + avec3d[0].yCoord * size, (double)z + beam[2] + avec3d[0].zCoord * size).color(beam[3], beam[4], beam[5], beam[6]).endVertex();
	        render.pos((double)x + beam[0] + avec3d[1].xCoord * size, (double)y + beam[1] + avec3d[1].yCoord * size, (double)z + beam[2] + avec3d[1].zCoord * size).color(beam[3], beam[4], beam[5], beam[6]).endVertex();
	        render.pos((double)x + beam[0] + avec3d[2].xCoord * size, (double)y + beam[1] + avec3d[2].yCoord * size, (double)z + beam[2] + avec3d[2].zCoord * size).color(beam[3], beam[4], beam[5], beam[6]).endVertex();
	        render.pos((double)x + beam[0] + avec3d[3].xCoord * size, (double)y + beam[1] + avec3d[3].yCoord * size, (double)z + beam[2] + avec3d[3].zCoord * size).color(beam[3], beam[4], beam[5], beam[6]).endVertex();
        }
        
        Tessellator.getInstance().draw();
        
    	GlStateManager.enableTexture2D();
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
        if (this.particleAge++ > this.particleMaxAge || this.host == null)
        {
            this.setExpired();
            return;
        }
        
    	//update movement
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        //update beam
        switch (this.particleType)
        {
        /**
	     * light quad sparkle around point
	     *   0: red random
	     *   2: green random
	     *   3: blue random
	     *   4: RG random
	     *   5: RB random
	     *   6: GB random
	     *   7: RGB random
	     *   8: R only
	     *   9: G only
	     *   10: B only
         */
        case 0:		//light sparkle around point
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        {
        	float red = 1F;
        	float green = 1F;
        	float blue = 1F;
        	
        	for (int i = 0; i < (4 - ClientProxy.getMineraft().gameSettings.particleSetting); i++)
        	{
        		//random color
        		switch (this.particleType)
        		{
                case 0:
                	red += this.rand.nextFloat() * 1.2F - 0.5F;
            	break;
                case 2:
                	green += this.rand.nextFloat() * 1.2F - 0.3F;
            	break;
                case 3:
                	blue += this.rand.nextFloat() * 1.2F - 0.5F;
            	break;
                case 4:
                	red += this.rand.nextFloat() * 1.2F - 0.5F;
                	green += this.rand.nextFloat() * 1.2F - 0.5F;
            	break;
                case 5:
                	red += this.rand.nextFloat() * 1.2F - 0.5F;
                	blue += this.rand.nextFloat() * 1.2F - 0.5F;
            	break;
                case 6:
                	green += this.rand.nextFloat() * 1.2F - 0.5F;
                	blue += this.rand.nextFloat() * 1.2F - 0.5F;
            	break;
                case 7:
                	red += this.rand.nextFloat() * 1.2F - 0.5F;
                	green += this.rand.nextFloat() * 1.2F - 0.5F;
                	blue += this.rand.nextFloat() * 1.2F - 0.5F;
            	break;
                case 8:
                	red += this.rand.nextFloat() * 1.2F - 0.5F;
                	green = 0.001F;
                	blue = 0.001F;
            	break;
                case 9:
                	red = 0.001F;
                	green += this.rand.nextFloat() * 1.2F - 0.5F;
                	blue = 0.001F;
            	break;
                case 10:
                	red = 0.001F;
                	green = 0.001F;
                	blue += this.rand.nextFloat() * 1.2F - 0.5F;
            	break;
        		}
        		
            	//create new beam
            	this.beamPos[this.beamCurrent] = new float[]
            	{
            		(float) (this.host.posX - this.posX) + (this.rand.nextFloat() - 0.5F) * this.beamFad,
            		(float) (this.host.posY - this.posY) + this.beamHeight + (this.rand.nextFloat() - 0.5F) * this.beamFad,
            		(float) (this.host.posZ - this.posZ) + (this.rand.nextFloat() - 0.5F) * this.beamFad,
            		red,
        			green,
        			blue,
        			this.particleAlpha, 0F
        		};
            	this.beamCurrent++;
            	if (this.beamCurrent >= this.beamPos.length) this.beamCurrent = 0;
        	}
        	
        	//update beam pos: halve dist to (0,0,0)
        	for (int i = 0; i < this.beamPos.length; i++)
        	{
        		//move
        		this.beamPos[i][0] += this.particleRed;
				this.beamPos[i][1] += this.particleGreen;
				this.beamPos[i][2] += this.particleBlue;
        		
        		//age++
        		this.beamPos[i][7] += 1F;
        		
        		//alpha random
        		this.beamPos[i][6] = this.rand.nextFloat() + 0.1F;
        		if (this.beamPos[i][6] > 1F) this.beamPos[i][6] = 1F;
        	}
        }
        break;
        case 1:		//blue eye fire
        {
        	//update pos
        	this.setPosition(this.host.posX, this.host.posY+this.beamHeight, this.host.posZ);
        	
        	float eyex = ((IShipEmotion) this.host).getStateFlag(ID.F.HeadTilt) ? this.beamFad - 0.05F : this.beamFad;
        	float eyeh = ((IShipEmotion) this.host).getStateFlag(ID.F.HeadTilt) ? 0.02F : 0F;
        	float[] headpos =  CalcHelper.rotateXYZByYawPitch(eyex, 0.19F + eyeh, this.beamSpd,
        			this.host.getRotationYawHead() * Values.N.DIV_PI_180,
        			this.host.rotationPitch * Values.N.DIV_PI_180, 1F);
        	float[] headmov =  CalcHelper.rotateXZByAxis(1F, 1F,
        			((EntityLivingBase)this.host).rotationYawHead * Values.N.DIV_PI_180, 0.025F);
        	
        	for (int i = 0; i < (4 - ClientProxy.getMineraft().gameSettings.particleSetting); i++)
        	{
            	//create new beam
            	this.beamPos[this.beamCurrent] = new float[]
            	{
            		(float) headpos[0] + (this.rand.nextFloat() - 0.5F) * 0.1F,	//xyz pos
            		(float) headpos[1] + (this.rand.nextFloat() - 0.5F) * 0.1F,
            		(float) headpos[2] + (this.rand.nextFloat() - 0.5F) * 0.1F,
            		this.particleRed + this.rand.nextFloat() + 0.4F,			//rgba
        			this.particleGreen,
        			this.particleBlue,
        			this.particleAlpha,
        			0F,															//age
        			headmov[1], 0.01F, headmov[0]								//motion
        		};
            	this.beamCurrent++;
            	if (this.beamCurrent >= this.beamPos.length) this.beamCurrent = 0;
        	}
        	
        	//update beam pos: halve dist to (0,0,0)
        	for (int i = 0; i < this.beamPos.length; i++)
        	{
        		//move
        		this.beamPos[i][8] *=  0.99F;
        		this.beamPos[i][9] *=  1.08F;
        		this.beamPos[i][10] *=  0.99F;
        		this.beamPos[i][0] += this.beamPos[i][8];
        		this.beamPos[i][1] += this.beamPos[i][9];
        		this.beamPos[i][2] += this.beamPos[i][10];
        		
        		//age++
        		this.beamPos[i][7] += 1F;
        		
        		//alpha random
        		this.beamPos[i][6] *= 0.92F;
        		if (this.beamPos[i][6] > 1F) this.beamPos[i][6] = 1F;
        	}
        }
        break;
        }
        
    }
    
    
}