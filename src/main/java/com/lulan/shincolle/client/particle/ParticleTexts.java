package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**MISS PARTICLE
 * 攻擊miss時發出文字特效 type:0:miss 1:critical 2:double hit 3:triple hit
 * tut: https://github.com/Draco18s/Artifacts/blob/master/main/java/com/draco18s/artifacts/client/RadarParticle.java
 */
@SideOnly(Side.CLIENT)
public class ParticleTexts extends Particle
{

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_PARTICLE+"ParticleTexts.png");
    private int particleType;    //0:miss 1:critical 2:double hit 3:triple hit 4:dodge

    
    public ParticleTexts(World world, double posX, double posY, double posZ, float scale, int type)
    {
        super(world, posX, posY, posZ);
        this.setSize(0F, 0F);
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0.1D;
        this.particleScale = scale;
        this.particleMaxAge = 25;
        this.particleType = type;
        this.canCollide = false;    //can clip = false
    }

    @Override
    public void renderParticle(BufferBuilder render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
        
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.disableLighting();
        
        float f6 = 0F;
        float f7 = 1F;
        float f8 = particleType / 5F;
        float f9 = (particleType + 1F) / 5F;
        
        float f10 = 0.8F;
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * ptick - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * ptick - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * ptick - interpPosZ);

        //start
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        
        //X跟Z位置不加頭部轉動偏移, 只有Y軸會偏向玩家方向
        render.pos(f11 - cosYaw * f10, f12 - cosPitch * 0.2F, f13 - sinYaw * f10).tex(f7, f9).endVertex();
        render.pos(f11 - cosYaw * f10, f12 + cosPitch * 0.2F, f13 - sinYaw * f10).tex(f7, f8).endVertex();
        render.pos(f11 + cosYaw * f10, f12 + cosPitch * 0.2F, f13 + sinYaw * f10).tex(f6, f8).endVertex();
        render.pos(f11 + cosYaw * f10, f12 - cosPitch * 0.2F, f13 + sinYaw * f10).tex(f6, f9).endVertex();
       
        //draw
        Tessellator.getInstance().draw();
        
        GlStateManager.enableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.popMatrix();
    }
    
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
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
        }

        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionY *= 0.9D;
    }
    
    
}