package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityShip;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;

/**
 * SPHERE LIGHT PARTICLE
 */
public class ParticleDebugPlane extends Particle
{
    
    private int particleType, bodyID;
    private Entity host;
    private float[] parms;
    private float hostWidth, yTop, yBottom, red2, green2, blue2, alpha2;
    
    
    public ParticleDebugPlane(Entity entity, int type, float...parms)
    {
        super(entity.world, 0F, 0F, 0F);
        this.setSize(0F, 0F);
        this.host = entity;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleType = type;
        this.parms = parms;
        this.canCollide = false;
        
        switch (type)
        {
        /**
         * type 0: caress position, a plane indicator to show the caressed position
         */
        case 0:
            if (this.host != null) this.hostWidth = this.host.width * 0.5F;
            this.particleMaxAge = 2;
            this.red2 = 0F;
            this.green2 = 1F;
            this.blue2 = 0F;
            this.alpha2 = 0.6F;
            this.yTop = this.parms[0];
            this.yBottom = this.parms[0];
            this.setPosition(entity.posX, entity.posY, entity.posZ);
        break;
        /**
         * type 1: body cube position, a cube indicator
         */
        case 1:
        case 2:
        {
            if (this.host != null)
            {
                this.hostWidth = this.host.width * 0.5F;
                
                //set top and bottom color
                if (this.host instanceof BasicEntityShip)
                {
                    //hit sensitive
                    if (this.particleType == 2)
                    {
                        this.red2 = 1F;
                        this.green2 = 0.6F;
                        this.blue2 = 1F;
                        this.alpha2 = 0.6F;
                    }
                    else
                    {
                        this.red2 = 1F;
                        this.green2 = 1F;
                        this.blue2 = 1F;
                        this.alpha2 = 0.15F;
                    }
                    
                    //set side color
                    switch ((int)this.parms[2])
                    {
                    case 0:        //top
                        this.particleRed = 1F;
                        this.particleGreen = 1F;
                        this.particleBlue = 0F;
                        this.particleAlpha = 0.15F;
                    break;
                    case 1:        //head
                        this.particleRed = 0F;
                        this.particleGreen = 1F;
                        this.particleBlue = 0F;
                        this.particleAlpha = 0.15F;
                    break;
                    case 2:        //neck
                        this.particleRed = 1F;
                        this.particleGreen = 0F;
                        this.particleBlue = 1F;
                        this.particleAlpha = 0.15F;
                    break;
                    case 3:        //chest
                        this.particleRed = 1F;
                        this.particleGreen = 1F;
                        this.particleBlue = 1F;
                        this.particleAlpha = 0.15F;
                    break;
                    case 4:        //belly
                        this.particleRed = 0F;
                        this.particleGreen = 1F;
                        this.particleBlue = 1F;
                        this.particleAlpha = 0.15F;
                    break;
                    case 5:        //ubelly
                        this.particleRed = 1F;
                        this.particleGreen = 0F;
                        this.particleBlue = 0F;
                        this.particleAlpha = 0.15F;
                    break;
                    default:    //leg
                        this.particleRed = 0F;
                        this.particleGreen = 0F;
                        this.particleBlue = 1F;
                        this.particleAlpha = 0.15F;
                    break;
                    }
                }
            }
            
            this.particleMaxAge = 2;
            this.yTop = this.parms[0];
            this.yBottom = this.parms[1];
            this.setPosition(entity.posX, entity.posY, entity.posZ);
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
        if (this.particleAge <= 1) return;
        
        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * ptick - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * ptick - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * ptick - interpPosZ);
        
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        
        //draw top plane, front and back 
        render.pos((double)x + hostWidth, (double)y+yTop, (double)z - hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
        render.pos((double)x + hostWidth, (double)y+yTop, (double)z + hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
        render.pos((double)x - hostWidth, (double)y+yTop, (double)z + hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
        render.pos((double)x - hostWidth, (double)y+yTop, (double)z - hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
        render.pos((double)x - hostWidth, (double)y+yTop, (double)z - hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
        render.pos((double)x - hostWidth, (double)y+yTop, (double)z + hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
        render.pos((double)x + hostWidth, (double)y+yTop, (double)z + hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
        render.pos((double)x + hostWidth, (double)y+yTop, (double)z - hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
        
        //draw side plane
        if (yTop != yBottom)
        {
            //draw bottom plane, front and back 
            render.pos((double)x + hostWidth, (double)y+yBottom, (double)z - hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
            render.pos((double)x + hostWidth, (double)y+yBottom, (double)z + hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
            render.pos((double)x - hostWidth, (double)y+yBottom, (double)z + hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
            render.pos((double)x - hostWidth, (double)y+yBottom, (double)z - hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
            render.pos((double)x - hostWidth, (double)y+yBottom, (double)z - hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
            render.pos((double)x - hostWidth, (double)y+yBottom, (double)z + hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
            render.pos((double)x + hostWidth, (double)y+yBottom, (double)z + hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
            render.pos((double)x + hostWidth, (double)y+yBottom, (double)z - hostWidth).color(this.red2, this.green2, this.blue2, this.alpha2).endVertex();
            //draw side1, front and back 
            render.pos((double)x + hostWidth, (double)y+yBottom, (double)z - hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x + hostWidth, (double)y+yTop, (double)z - hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x + hostWidth, (double)y+yTop, (double)z + hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x + hostWidth, (double)y+yBottom, (double)z + hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            //draw side2, front and back 
            render.pos((double)x - hostWidth, (double)y+yBottom, (double)z - hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x - hostWidth, (double)y+yTop, (double)z - hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x + hostWidth, (double)y+yTop, (double)z - hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x + hostWidth, (double)y+yBottom, (double)z - hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            //draw side3, front and back 
            render.pos((double)x - hostWidth, (double)y+yBottom, (double)z + hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x - hostWidth, (double)y+yTop, (double)z + hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x - hostWidth, (double)y+yTop, (double)z - hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x - hostWidth, (double)y+yBottom, (double)z - hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            //draw side4, front and back 
            render.pos((double)x + hostWidth, (double)y+yBottom, (double)z + hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x + hostWidth, (double)y+yTop, (double)z + hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x - hostWidth, (double)y+yTop, (double)z + hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
            render.pos((double)x - hostWidth, (double)y+yBottom, (double)z + hostWidth).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
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
        case 0:        //type 0: caress indicator
            this.setPosition(this.host.posX, this.host.posY, this.host.posZ);
        break;
        }
    }
    
    
}