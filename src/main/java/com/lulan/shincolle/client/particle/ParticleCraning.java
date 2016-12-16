package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/** CRANING PARTICLE
 *   
 */
@SideOnly(Side.CLIENT)
public class ParticleCraning extends Particle
{

	private int particleType;
	private float par1, lenMax, len;
	private double[][] vt1, vt2;				//cube vertex
	
	
    public ParticleCraning(World world, double x, double y, double z, double lengthMax, double par1, double scale, int type)
    {
        super(world, x, y, z, 0D, 0D, 0D);
        this.setSize(0F, 0F);
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.lenMax = (float) lengthMax;
        this.par1 = (float) par1;
        this.particleScale = (float) scale;
        this.particleType = type;
        this.vt1 = new double[8][3];
        this.vt2 = new double[8][3];
        this.field_190017_n = false;	//can clip = false
        
        float[] lookDeg;
        float[] posOffset;
        
        switch (type)
        {
        default:	//craning
        	this.particleMaxAge = 127;
        	this.particleRed = 0.6F;
        	this.particleGreen = 0F;
        	this.particleBlue = 0F;
        	this.len = 0F;
        break;
        }
    }

    //par3 = Yaw的cos值, par4 = Pitch的cos值, par5 = Yaw的sin值
    //par6 = Yaw的sin值乘上-Pitch的sin值, par7 = Yaw的cos值乘上Pitch的sin值
    @Override
    public void renderParticle(VertexBuffer render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {
    	GlStateManager.pushMatrix();
    	GlStateManager.depthMask(true);
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
    	GlStateManager.disableTexture2D();	//NO texture
		
		float sizeHead = this.particleScale * 1F;
		float sizeChain = this.particleScale * 0.25F;
		
		//out
		float[] v1 = new float[] {sizeHead * 0.75F, -sizeHead, -sizeHead};
		float[] v2 = new float[] {sizeHead * 0.75F, sizeHead, -sizeHead};
		float[] v3 = new float[] {-sizeHead * 0.75F, sizeHead, -sizeHead};
		float[] v4 = new float[] {-sizeHead * 0.75F, -sizeHead, -sizeHead};
		//in
		float[] v5 = new float[] {sizeChain, -sizeChain * 1.5F, -sizeChain};
		float[] v6 = new float[] {sizeChain, sizeChain * 1.5F, -sizeChain};
		float[] v7 = new float[] {-sizeChain, sizeChain * 1.5F, -sizeChain};
		float[] v8 = new float[] {-sizeChain, -sizeChain * 1.5F, -sizeChain};
		
		//particle是以client端視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
        double hx = this.posX - interpPosX;
        double hy = this.posY - interpPosY - len + this.particleScale * 5D;
        double hz = this.posZ - interpPosZ + this.particleScale * 0.5D;
        double z1 = this.particleScale * 0.8D;
        double z2 = this.particleScale * 0.25D;
        double y1 = this.particleScale * 1D;
        
        //crane head
        vt1[0][0] = hx+v1[0];	vt1[0][1] = hy+v1[1];	vt1[0][2] = hz+v1[2];
        vt1[1][0] = hx+v2[0];	vt1[1][1] = hy+v2[1];	vt1[1][2] = hz+v2[2];
        vt1[2][0] = hx+v3[0];	vt1[2][1] = hy+v3[1];	vt1[2][2] = hz+v3[2];
        vt1[3][0] = hx+v4[0];	vt1[3][1] = hy+v4[1];	vt1[3][2] = hz+v4[2];
        vt1[4][0] = hx+v1[0];	vt1[4][1] = hy+v1[1];	vt1[4][2] = hz+v1[2]+z1;
        vt1[5][0] = hx+v2[0];	vt1[5][1] = hy+v2[1];	vt1[5][2] = hz+v2[2]+z1;
        vt1[6][0] = hx+v3[0];	vt1[6][1] = hy+v3[1];	vt1[6][2] = hz+v3[2]+z1;
        vt1[7][0] = hx+v4[0];	vt1[7][1] = hy+v4[1];	vt1[7][2] = hz+v4[2]+z1;
        
        hz -= this.particleScale * 0.47D;
        
        //crane chain
        vt2[0][0] = hx+v5[0];	vt2[0][1] = hy+v5[1]+y1;	vt2[0][2] = hz+v5[2];
        vt2[1][0] = hx+v6[0];	vt2[1][1] = hy+v6[1]+y1;	vt2[1][2] = hz+v6[2];
        vt2[2][0] = hx+v7[0];	vt2[2][1] = hy+v7[1]+y1;	vt2[2][2] = hz+v7[2];
        vt2[3][0] = hx+v8[0];	vt2[3][1] = hy+v8[1]+y1;	vt2[3][2] = hz+v8[2];
        vt2[4][0] = hx+v5[0];	vt2[4][1] = hy+v5[1]+y1;	vt2[4][2] = hz+v5[2]+z2;
        vt2[5][0] = hx+v6[0];	vt2[5][1] = hy+v6[1]+y1;	vt2[5][2] = hz+v6[2]+z2;
        vt2[6][0] = hx+v7[0];	vt2[6][1] = hy+v7[1]+y1;	vt2[6][2] = hz+v7[2]+z2;
        vt2[7][0] = hx+v8[0];	vt2[7][1] = hy+v8[1]+y1;	vt2[7][2] = hz+v8[2]+z2;

        //start tess
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 30F, 30F);
        
        //crane chain
        for (float clen = 0F; clen < len; clen += this.particleScale * 1D)
        {
        	float ny = (float) hy + clen;
            
            //crane chain
            vt2[0][0] = hx+v5[0];	vt2[0][1] = ny+v5[1]+y1;	vt2[0][2] = hz+v5[2];
            vt2[1][0] = hx+v6[0];	vt2[1][1] = ny+v6[1]+y1;	vt2[1][2] = hz+v6[2];
            vt2[2][0] = hx+v7[0];	vt2[2][1] = ny+v7[1]+y1;	vt2[2][2] = hz+v7[2];
            vt2[3][0] = hx+v8[0];	vt2[3][1] = ny+v8[1]+y1;	vt2[3][2] = hz+v8[2];
            vt2[4][0] = hx+v5[0];	vt2[4][1] = ny+v5[1]+y1;	vt2[4][2] = hz+v5[2]+z2;
            vt2[5][0] = hx+v6[0];	vt2[5][1] = ny+v6[1]+y1;	vt2[5][2] = hz+v6[2]+z2;
            vt2[6][0] = hx+v7[0];	vt2[6][1] = ny+v7[1]+y1;	vt2[6][2] = hz+v7[2]+z2;
            vt2[7][0] = hx+v8[0];	vt2[7][1] = ny+v8[1]+y1;	vt2[7][2] = hz+v8[2]+z2;
            
            render.pos(vt2[3][0], vt2[3][1], vt2[3][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[2][0], vt2[2][1], vt2[2][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[1][0], vt2[1][1], vt2[1][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[0][0], vt2[0][1], vt2[0][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            
            render.pos(vt2[0][0], vt2[0][1], vt2[0][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[1][0], vt2[1][1], vt2[1][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[5][0], vt2[5][1], vt2[5][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[4][0], vt2[4][1], vt2[4][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            
            render.pos(vt2[4][0], vt2[4][1], vt2[4][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[5][0], vt2[5][1], vt2[5][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[6][0], vt2[6][1], vt2[6][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[7][0], vt2[7][1], vt2[7][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            
            render.pos(vt2[7][0], vt2[7][1], vt2[7][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[6][0], vt2[6][1], vt2[6][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[2][0], vt2[2][1], vt2[2][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[3][0], vt2[3][1], vt2[3][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            
            render.pos(vt2[1][0], vt2[1][1], vt2[1][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[2][0], vt2[2][1], vt2[2][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[6][0], vt2[6][1], vt2[6][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[5][0], vt2[5][1], vt2[5][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            
            render.pos(vt2[3][0], vt2[3][1], vt2[3][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[0][0], vt2[0][1], vt2[0][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[4][0], vt2[4][1], vt2[4][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
            render.pos(vt2[7][0], vt2[7][1], vt2[7][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        }
        
        //crane head
//        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1F);
        
        render.pos(vt1[3][0], vt1[3][1], vt1[3][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[2][0], vt1[2][1], vt1[2][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[1][0], vt1[1][1], vt1[1][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[0][0], vt1[0][1], vt1[0][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        
        render.pos(vt1[0][0], vt1[0][1], vt1[0][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[1][0], vt1[1][1], vt1[1][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[5][0], vt1[5][1], vt1[5][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[4][0], vt1[4][1], vt1[4][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        
        render.pos(vt1[4][0], vt1[4][1], vt1[4][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[5][0], vt1[5][1], vt1[5][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[6][0], vt1[6][1], vt1[6][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[7][0], vt1[7][1], vt1[7][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        
        render.pos(vt1[7][0], vt1[7][1], vt1[7][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[6][0], vt1[6][1], vt1[6][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[2][0], vt1[2][1], vt1[2][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[3][0], vt1[3][1], vt1[3][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        
        render.pos(vt1[1][0], vt1[1][1], vt1[1][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[2][0], vt1[2][1], vt1[2][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[6][0], vt1[6][1], vt1[6][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[5][0], vt1[5][1], vt1[5][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        
        render.pos(vt1[3][0], vt1[3][1], vt1[3][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[0][0], vt1[0][1], vt1[0][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[4][0], vt1[4][1], vt1[4][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        render.pos(vt1[7][0], vt1[7][1], vt1[7][2]).color(this.particleRed, this.particleGreen, this.particleBlue, 1F).endVertex();
        
        //draw
        Tessellator.getInstance().draw();
        
    	GlStateManager.enableLighting();
    	GlStateManager.disableBlend();
    	GlStateManager.depthMask(false);
    	GlStateManager.enableTexture2D();
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
    	//update pos
    	switch (particleType)
    	{
    	default:
    		//伸長
    		float half = particleMaxAge * 0.45F;
    		float half2 = particleMaxAge - half;
    		
    		if (particleAge <= half)
    		{
    			len = particleAge / half * lenMax;
    		}
    		//停滯
    		else if (particleAge > half && particleAge <= half2)
    		{
    			len = lenMax;
    		}
    		//縮回
    		else if (particleAge > half2)
    		{
    			len = (particleMaxAge - particleAge) / half * lenMax;
    		}
    	break;
    	}
    	
        if (this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
        }
    }
    
    
}
