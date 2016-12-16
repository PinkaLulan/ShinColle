package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**LASER PARTICLE
 * 給定host, target -> 生成雷射特效
 * RE-CLASS, 部分姬級用
 * 
 * type: 0:持續11 ticks 
 *       1:
 */
@SideOnly(Side.CLIENT)
public class ParticleLaser extends Particle
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_PARTICLE+"ParticleLaser.png");
	private int particleType;
	private double tarX, tarY, tarZ;
	
    public ParticleLaser(World world, double posX, double posY, double posZ, double tarX, double tarY, double tarZ, float scale, int type)
    {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        this.setSize(0F, 0F);
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleType = type;
        this.tarX = tarX;
        this.tarY = tarY;
        this.tarZ = tarZ;
        this.field_190017_n = false;	//can clip = false
        
        switch (type)
        {
        case 0:		//re-class laser
        	this.particleMaxAge = 11;
        	this.particleRed = 1F;
        	this.particleGreen = 1F;
        	this.particleBlue = 1F;
        	this.particleAlpha = 1F;
        break;
        case 1:		//NGT speed blur
        	this.particleMaxAge = 11;
        	this.particleAge = 4;
        	this.particleRed = 1F;
        	this.particleGreen = 0F;
        	this.particleBlue = 0F;
        	this.particleAlpha = 1F;
        break;
        }
    }

    @Override
    public void renderParticle(VertexBuffer render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {	
    	Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.depthMask(true);
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
    	
		float minU = 0F;
		float maxU = rand.nextInt(32)+32;
		float minV = this.particleAge % 12 / 12F;
		float maxV = minV + 0.08333333F;
		
		//particle是以玩家視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
        double f11 = (float)(this.posX - interpPosX);
        double f12 = (float)(this.posY - interpPosY);
        double f13 = (float)(this.posZ - interpPosZ);
        double f21 = (float)(this.tarX - interpPosX);
        double f22 = (float)(this.tarY - interpPosY);
        double f23 = (float)(this.tarZ - interpPosZ);
      
        //start tess
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        
        //注意4個點形成的面只有正面會貼上貼圖, 若玩家在該面背面會看不到正面貼圖, 因此要畫兩面共8個點
        //要使玩家看到正面, 4個座標add順序必須為: 右下 -> 右上 -> 左上 -> 左下
        //add front plane
        render.pos(f21, f22, f23).tex(maxU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f21, f22 + particleScale * 0.3D, f23).tex(maxU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12 + particleScale * 0.3D, f13).tex(minU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12, f13).tex(minU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //add back plane
        render.pos(f11, f12, f13).tex(minU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f11, f12 + particleScale * 0.3D, f13).tex(minU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f21, f22 + particleScale * 0.3D, f23).tex(maxU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(f21, f22, f23).tex(maxU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        
        Tessellator.getInstance().draw();
        
    	GlStateManager.enableLighting();
    	GlStateManager.disableBlend();
    	GlStateManager.depthMask(false);
    	GlStateManager.popMatrix();
    }
    
    //layer: 0:particle 1:terrain 2:items 3:custom?
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
        if(this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
        }
    }
    
    
}