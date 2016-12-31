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


/**91TYPE PARTICLE
 * 攻擊文字特效
 */
@SideOnly(Side.CLIENT)
public class Particle91Type extends Particle
{

	private static final ResourceLocation TEXTURE1 = new ResourceLocation(Reference.TEXTURES_PARTICLE+"Particle91Type.png");
	private int partAge;
	private int fadeTime = 16;
	private int middTime = 60;
	private int totalTime = 2 * fadeTime + middTime;
	private float minu, maxu ,x , y, z, scale, alpha;
	private float fadeCoef = 1F / fadeTime;
	
	
    public Particle91Type(World world, double posX, double posY, double posZ, float scale)
    {
        super(world, posX, posY, posZ);
        this.setSize(0F, 0F);
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleMaxAge = 136;
        this.canCollide = false;	//can clip = false
    }

    /**
     * parms:
     * VertexBuffer, particle entity, cos(Yaw), cos(Pitch), -sin(Yaw) * sin(Pitch), cos(Yaw) * sin(Pitch)
     */
    @Override
    public void renderParticle(VertexBuffer render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {
    	Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE1);
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.depthMask(true);
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
        
        /**draw text
         * age: 0  ~ 15: color fade in (RGB=0% -> RGB=100%)
         *      16 ~ 55: no change
         *      56 ~ 70: alpha fade out (ALPHA=100% -> ALPHA=0%)
         */
		render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		
        for (int i = 0; i < 6; ++i)
        {
        	partAge = this.particleAge - i * 8;

        	if (partAge > -1 && partAge < totalTime)
        	{
        		//particle是以玩家視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
            	float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)ptick - interpPosX);
            	float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)ptick - interpPosY);
            	float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)ptick - interpPosZ);

        		minu = 1F / 6F * i;
        		maxu = 1F / 6F * (i + 1);
        		x = f11 - (i - 2.5F) * this.particleScale * 2F * cosYaw;
                y = f12;
                z = f13 - (i - 2.5F) * this.particleScale * 2F * sinYaw;
//                x = (float)(this.posX - interpPosX - (i - 2.5F) * this.particleScale * 2F * cosYaw);
//                y = (float)(this.posY - interpPosY);
//                z = (float)(this.posZ - interpPosZ - (i - 2.5F) * this.particleScale * 2F * sinYaw);
        	
                if (partAge < fadeTime)
                {	//0~10: color fade in
                	scale = this.particleScale * (3F - 2F * fadeCoef * partAge);
                	alpha = fadeCoef * partAge;
                }
                else if (partAge >= (fadeTime + middTime))
                {	//71~80: alpha fade out
                	partAge -= (fadeTime + middTime);
                	scale = this.particleScale * (1F + 2F * fadeCoef * partAge);
                	alpha = 1F - fadeCoef * partAge;
                }
                else
                {	//other
                	scale = this.particleScale;
                	alpha = 1F;
                }
                
            	addQuad(render, scale, x, y, z, cosYaw, cosPitch, sinYaw, minu, maxu, 0F, 1F);
        	}
        }
  
        Tessellator.getInstance().draw();
        
    	GlStateManager.enableLighting();
    	GlStateManager.disableBlend();
    	GlStateManager.depthMask(false);
    	GlStateManager.popMatrix();
    }

    //add quad with size
	private void addQuad(VertexBuffer render, float scale, float x, float y, float z, float offx, float offy, float offz, float minu, float maxu, float minv, float maxv)
	{
        float offsetX = offx * scale;
        float offsetY = offy * scale;
        float offsetZ = offz * scale;
        
        render.pos(x - offsetX, y - offsetY, z - offsetZ).tex(maxu, maxv).color(1F, 1F, 1F, alpha).endVertex();
        render.pos(x - offsetX, y + offsetY, z - offsetZ).tex(maxu, minv).color(1F, 1F, 1F, alpha).endVertex();
        render.pos(x + offsetX, y + offsetY, z + offsetZ).tex(minu, minv).color(1F, 1F, 1F, alpha).endVertex();
        render.pos(x + offsetX, y - offsetY, z + offsetZ).tex(minu, maxv).color(1F, 1F, 1F, alpha).endVertex();	
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
    	//this is both side particle
//		this.prevPosX = this.posX;
//        this.prevPosY = this.posY;
//        this.prevPosZ = this.posZ;

        if (this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
        }

//        this.move(this.motionX, this.motionY, this.motionZ);
//        this.motionY *= 0.9D;
    }
    
    
}

