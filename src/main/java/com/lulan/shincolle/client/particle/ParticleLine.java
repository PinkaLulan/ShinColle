package com.lulan.shincolle.client.particle;

import com.lulan.shincolle.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;


/**
 * LINE PARTICLE
 */
@SideOnly(Side.CLIENT)
public class ParticleLine extends Particle
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_PARTICLE+"ParticleGradientLine.png");
	private int particleType;
	private float[] parms;
	
	
    public ParticleLine(World world, int type, float[] parms)
    {
        super(world, 0D, 0D, 0D);
        this.setSize(0F, 0F);
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.parms = parms;
        this.particleType = type;
        this.canCollide = false;
        
        switch (type)
        {
        /**
         * parms: height, width forward, width backward, R, G, B, A, px, py, pz, mx, my, mz
         */
        case 0:		//high speed blur
        	this.particleMaxAge = 50;
        	this.particleRed = parms[3];
        	this.particleGreen = parms[4];
        	this.particleBlue = parms[5];
        	this.particleAlpha = parms[6];
        	this.posX = parms[7];
        	this.posY = parms[8];
        	this.posZ = parms[9];
        break;
        }
        
        //init pos
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
    }

    @Override
    public void renderParticle(BufferBuilder render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {
		//particle是以玩家視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
		double px = this.posX - interpPosX;
        double py = this.posY - interpPosY;
        double pz = this.posZ - interpPosZ;
        double[] xyzh = new double[] {parms[0]*parms[10], parms[0]*parms[11], parms[0]*parms[12]};
        double[] xyzf = new double[] {parms[1]*parms[10], parms[1]*parms[11], parms[1]*parms[12]};
        double[] xyzb = new double[] {-parms[2]*parms[10], -parms[2]*parms[11], -parms[2]*parms[12]};
        
        //三種正交方式: 若vec為(x, y, z), 則三種簡單正交為(-z, 0, x), (0, -z, y), (-y, x, 0)
        //三種正交都畫出來使各種方向看過去皆有機會看到整個面
        Vec3d[] plane1 = new Vec3d[]
		{
    		new Vec3d(xyzf[0], xyzf[1] - xyzh[2], xyzf[2] + xyzh[1]),
    		new Vec3d(xyzf[0], xyzf[1] + xyzh[2], xyzf[2] - xyzh[1]),
    		new Vec3d(xyzb[0], xyzb[1] + xyzh[2], xyzb[2] - xyzh[1]),
    		new Vec3d(xyzb[0], xyzb[1] - xyzh[2], xyzb[2] + xyzh[1])
        };
        Vec3d[] plane2 = new Vec3d[]
		{
    		new Vec3d(xyzf[0] - xyzh[2], xyzf[1], xyzf[2] + xyzh[0]),
    		new Vec3d(xyzf[0] + xyzh[2], xyzf[1], xyzf[2] - xyzh[0]),
    		new Vec3d(xyzb[0] - xyzh[2], xyzb[1], xyzb[2] + xyzh[0]),
    		new Vec3d(xyzb[0] + xyzh[2], xyzb[1], xyzb[2] - xyzh[0])
        };
        Vec3d[] plane3 = new Vec3d[]
		{
    		new Vec3d(xyzf[0] - xyzh[1], xyzf[1] + xyzh[0], xyzf[2]),
    		new Vec3d(xyzf[0] + xyzh[1], xyzf[1] - xyzh[0], xyzf[2]),
    		new Vec3d(xyzb[0] - xyzh[1], xyzb[1] + xyzh[0], xyzb[2]),
    		new Vec3d(xyzb[0] + xyzh[1], xyzb[1] - xyzh[0], xyzb[2])
        };


		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.depthMask(false);
    	GlStateManager.disableLighting();
      
        //start tess
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        
        //front plane1
        render.pos(px + plane1[0].x, py + plane1[0].y, pz + plane1[0].z).tex(1D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane1[1].x, py + plane1[1].y, pz + plane1[1].z).tex(1D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane1[2].x, py + plane1[2].y, pz + plane1[2].z).tex(0D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane1[3].x, py + plane1[3].y, pz + plane1[3].z).tex(0D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //back plane1
        render.pos(px + plane1[3].x, py + plane1[3].y, pz + plane1[3].z).tex(0D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane1[2].x, py + plane1[2].y, pz + plane1[2].z).tex(0D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane1[1].x, py + plane1[1].y, pz + plane1[1].z).tex(1D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane1[0].x, py + plane1[0].y, pz + plane1[0].z).tex(1D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //front plane2
        render.pos(px + plane2[0].x, py + plane2[0].y, pz + plane2[0].z).tex(1D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane2[1].x, py + plane2[1].y, pz + plane2[1].z).tex(1D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane2[2].x, py + plane2[2].y, pz + plane2[2].z).tex(0D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane2[3].x, py + plane2[3].y, pz + plane2[3].z).tex(0D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //back plane2
        render.pos(px + plane2[3].x, py + plane2[3].y, pz + plane2[3].z).tex(0D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane2[2].x, py + plane2[2].y, pz + plane2[2].z).tex(0D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane2[1].x, py + plane2[1].y, pz + plane2[1].z).tex(1D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane2[0].x, py + plane2[0].y, pz + plane2[0].z).tex(1D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //front plane3
        render.pos(px + plane3[0].x, py + plane3[0].y, pz + plane3[0].z).tex(1D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane3[1].x, py + plane3[1].y, pz + plane3[1].z).tex(1D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane3[2].x, py + plane3[2].y, pz + plane3[2].z).tex(0D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane3[3].x, py + plane3[3].y, pz + plane3[3].z).tex(0D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        //back plane3
        render.pos(px + plane3[3].x, py + plane3[3].y, pz + plane3[3].z).tex(0D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane3[2].x, py + plane3[2].y, pz + plane3[2].z).tex(0D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane3[1].x, py + plane3[1].y, pz + plane3[1].z).tex(1D, 0D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        render.pos(px + plane3[0].x, py + plane3[0].y, pz + plane3[0].z).tex(1D, 1D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        
        Tessellator.getInstance().draw();
        
    	GlStateManager.enableLighting();
    	GlStateManager.depthMask(false);
    	GlStateManager.disableBlend();
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
        
        switch (this.particleType)
        {
        case 0:
        	this.parms[0] *= 0.88F;
        	this.parms[2] *= 0.85F;
        	this.particleAlpha *= 0.9F;
    	break;
        }
    }
    
    
}
