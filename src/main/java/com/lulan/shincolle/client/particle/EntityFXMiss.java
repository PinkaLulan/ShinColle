package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


/**MISS PARTICLE
 * 攻擊miss時發出文字特效
 * tut: https://github.com/Draco18s/Artifacts/blob/master/main/java/com/draco18s/artifacts/client/RadarParticle.java
 */
@SideOnly(Side.CLIENT)
public class EntityFXMiss extends EntityFX {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityFXMiss.png");
 
    public EntityFXMiss(World world, double posX, double posY, double posZ, float scale) {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0.1D;
        this.particleScale = scale;
        this.particleMaxAge = 25;
        this.noClip = true;

    }

    public void renderParticle(Tessellator tess, float ticks, float par3, float par4, float par5, float par6, float par7) {
    	
    	//stop last tess for bind texture
    	tess.draw();	
    	
		GL11.glPushMatrix();
		//使用自帶的貼圖檔
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		GL11.glDepthMask(true);
//		GL11.glDisable(GL11.GL_BLEND);
//		GL11.glDisable(GL11.GL_LIGHTING);
//		GL11.glEnable(GL11.GL_DEPTH_TEST);	//DEPTH TEST開啟後才能使用glDepthFunc
//		GL11.glDepthFunc(GL11.GL_ALWAYS);
		
		float f6 = 0F;
		float f7 = 1F;
		float f8 = 0F;
		float f9 = 1F;
		float f10 = 0.5F;

        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)ticks - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)ticks - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)ticks - interpPosZ);
        
        //start tess
        tess.startDrawingQuads();
//        tess.setColorRGBA_F(1F, 1F, 1F, 1F);
//        tess.addVertexWithUV((double)(f11 - par3 * f10 - par6 * f10), (double)(f12 - par4 * 0.2F), (double)(f13 - par5 * f10 - par7 * f10), 1D, 1D);
//        tess.addVertexWithUV((double)(f11 - par3 * f10 + par6 * f10), (double)(f12 + par4 * 0.2F), (double)(f13 - par5 * f10 + par7 * f10), 1D, 0D);
//        tess.addVertexWithUV((double)(f11 + par3 * f10 + par6 * f10), (double)(f12 + par4 * 0.2F), (double)(f13 + par5 * f10 + par7 * f10), 0D, 0D);
//        tess.addVertexWithUV((double)(f11 + par3 * f10 - par6 * f10), (double)(f12 - par4 * 0.2F), (double)(f13 + par5 * f10 - par7 * f10), 0D, 1D);
        //X跟Z位置不加頭部轉動偏移, 只有Y軸會偏向玩家方向
        tess.addVertexWithUV((double)(f11 - par3 * f10), (double)(f12 - par4 * 0.2F), (double)(f13 - par5 * f10), 1D, 1D);
        tess.addVertexWithUV((double)(f11 - par3 * f10), (double)(f12 + par4 * 0.2F), (double)(f13 - par5 * f10), 1D, 0D);
        tess.addVertexWithUV((double)(f11 + par3 * f10), (double)(f12 + par4 * 0.2F), (double)(f13 + par5 * f10), 0D, 0D);
        tess.addVertexWithUV((double)(f11 + par3 * f10), (double)(f12 - par4 * 0.2F), (double)(f13 + par5 * f10), 0D, 1D);
        //stop tess for restore texture
        tess.draw();

        //restore texture, 將貼圖檔回復為官方用的particles.png
        try {
        	Minecraft.getMinecraft().renderEngine.bindTexture((ResourceLocation)ReflectionHelper.getPrivateValue(EffectRenderer.class, null, new String[] { "particleTextures", "b", "field_110737_b" })); 
		} 
        catch (Exception e) {
        	LogHelper.info("DEBUG : particle restore default texture fail");
        }
        
//        GL11.glDepthFunc(GL11.GL_LEQUAL);
//		GL11.glDisable(GL11.GL_DEPTH_TEST);	//DEPTH TEST關閉
//      GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_BLEND);
//		GL11.glDepthMask(false);
		GL11.glPopMatrix();
		
		//start tess for other particle
		tess.startDrawingQuads();
    }
    
    //layer: 0:particle 1:terrain 2:items
    @Override
    public int getFXLayer() {
        return 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if(this.particleAge++ > this.particleMaxAge) {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY *= 0.9D;

    }
}
