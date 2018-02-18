package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;

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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**LASER NO TEXTURE PARTICLE
 * 給定host, target -> 生成雷射特效
 * 此為柱狀3D雷射(即六面長方體)
 * 
 * type:
 *   0: 雙紅雷射: par1為X軸位置(分左右光炮), par2為發射高度
 *   1: 大和波動砲: 主砲光束
 *   2: 守衛目標線: 指示船艦到守衛目標的連結線標示
 *   
 */
@SideOnly(Side.CLIENT)
public class ParticleLaserNoTexture extends Particle
{

	private int particleType;
	private float shotYaw, shotPitch, scaleOut, scaleIn, alphaOut, alphaIn;
	private double tarX, tarY, tarZ, par1, par2, par3;
	private double[][] vt, vt2;				//cube vertex
	private EntityLivingBase host;
	private Entity target;
	
	
    public ParticleLaserNoTexture(World world, EntityLivingBase host, Entity target, double par1, double par2, double par3, float scale, int type)
    {
        super(world, host.posX, host.posY, host.posZ);
        this.setSize(0F, 0F);
        this.host = host;
        this.target = target;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleType = type;
        this.tarX = target.posX;
        this.tarY = target.posY + target.height * 0.75D;
        this.tarZ = target.posZ;
        this.par1 = par1;
        this.par2 = par2;
        this.par3 = par3;
        this.vt = new double[8][3];
        this.vt2 = new double[8][3];
        this.canCollide = false;	//can clip = false
        
        float[] lookDeg;
        float[] posOffset;
        
        switch (type)
        {
        case 1:		//大和波動砲
        	this.particleMaxAge = 30;
        	this.particleRed = 1F;
        	this.particleGreen = 0.8F;
        	this.particleBlue = 0.9F;
        break;
        case 2:		//守衛標示線: entity類
        	lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
        	this.shotYaw = lookDeg[0];
        	this.shotPitch = lookDeg[1];
        	this.particleMaxAge = 8;
        	this.particleRed = 1F;
        	this.particleGreen = 1F;
        	this.particleBlue = 1F;
        	this.scaleOut = this.particleScale * 0.5F;
        	this.scaleIn = this.particleScale * 0.125F;
        	this.alphaOut = 0.1F;
        	this.alphaIn = 0.2F;
        break;
        case 4:		//補給標示線
        	lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
        	this.shotYaw = lookDeg[0];
        	this.shotPitch = lookDeg[1];
        	this.tarX = target.posX;
    		this.tarY = target.posY + target.height * 0.5D;
    		this.tarZ = target.posZ;
        	this.particleMaxAge = 12;
        	this.particleRed = 1F;
        	this.particleGreen = 0.75F;
        	this.particleBlue = 1F;
        	this.scaleOut = this.particleScale * 0.5F;
        	this.scaleIn = this.particleScale * 0.125F;
        	this.alphaOut = 0.1F;
        	this.alphaIn = 0.2F;
        break;
        case 5:		//位置標示線
        	lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
        	this.shotYaw = lookDeg[0];
        	this.shotPitch = lookDeg[1];
        	this.tarX = target.posX;
    		this.tarY = target.posY + 0.2D;
    		this.tarZ = target.posZ;
        	this.particleMaxAge = 64;
        	this.particleRed = 1F;
        	this.particleGreen = 0.6F;
        	this.particleBlue = 1F;
        	this.scaleOut = this.particleScale * 0.5F;
        	this.scaleIn = this.particleScale * 0.125F;
        	this.alphaOut = 0.6F;
        	this.alphaIn = 0.8F;
        break;
        case 6:		//紫色可調粗細光束
        	lookDeg = CalcHelper.getLookDegree(tarX-posX, (tarY+target.height*0.5D)-(posY+this.par1), tarZ-posZ, false);
        	this.shotYaw = lookDeg[0];
        	this.shotPitch = lookDeg[1];
        	this.tarX = target.posX;
    		this.tarY = target.posY + target.height * 0.5D;
    		this.tarZ = target.posZ;
        	this.particleMaxAge = 16;
        	this.particleRed = 0.5F;
        	this.particleGreen = 0F;
        	this.particleBlue = 1F;
        	this.scaleOut = (float) this.par2;
        	this.scaleIn = (float) this.par3;
        break;
        default:	//紅光束砲
        	lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
        	posOffset = CalcHelper.rotateXYZByYawPitch((float)par1, 0F, 0.78F, lookDeg[0], lookDeg[1], 1F);
        	this.shotYaw = lookDeg[0];
        	this.shotPitch = lookDeg[1];
        	this.posX += posOffset[0];
        	this.posY += (par2 + posOffset[1]);
        	this.posZ += posOffset[2];
        	this.particleMaxAge = 8;
        	this.particleRed = 1F;
        	this.particleGreen = 0F;
        	this.particleBlue = 0F;
        	this.scaleOut = this.particleScale * 0.5F;
        	this.scaleIn = this.particleScale * 0.125F;
        	this.alphaOut = 0.1F;
        	this.alphaIn = 0.2F;
        break;
        }//end switch
    }
    
    public ParticleLaserNoTexture(World world, EntityLivingBase host, double tarX, double tarY, double tarZ, float scale, int type)
    {
        super(world, host.posX, host.posY, host.posZ);
        this.setSize(0F, 0F);
        this.host = host;
        this.target = host;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleType = type;
        this.tarX = tarX;
        this.tarY = tarY;
        this.tarZ = tarZ;
        this.par1 = 0D;
        this.par2 = 0D;
        this.par3 = 0D;
        this.vt = new double[8][3];
        this.vt2 = new double[8][3];
        this.canCollide = false;	//can clip = false
        
        float[] lookDeg;
        float[] posOffset;
        
        switch (type)
        {
        case 3:		//守衛標示線: block類
        	lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
        	this.shotYaw = lookDeg[0];
        	this.shotPitch = lookDeg[1];
        	this.particleMaxAge = 8;
        	this.particleRed = 1F;
        	this.particleGreen = 1F;
        	this.particleBlue = 1F;
        	this.scaleOut = this.particleScale * 0.5F;
        	this.scaleIn = this.particleScale * 0.125F;
        	this.alphaOut = 0.1F;
        	this.alphaIn = 0.2F;
        break;
        }
    }

    @Override
    public void renderParticle(VertexBuffer render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {
    	if (this.particleAge <= 1) return;
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.depthMask(true);
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
    	GlStateManager.disableTexture2D();	//NO texture
		
		//out
		float[] v1 = CalcHelper.rotateXYZByYawPitch(1F, -1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v2 = CalcHelper.rotateXYZByYawPitch(1F, 1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v3 = CalcHelper.rotateXYZByYawPitch(-1F, 1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v4 = CalcHelper.rotateXYZByYawPitch(-1F, -1F, -1F, shotYaw, shotPitch, this.scaleOut);
		//in
		float[] v5 = CalcHelper.rotateXYZByYawPitch(1F, -1F, 0F, shotYaw, shotPitch, this.scaleIn);
		float[] v6 = CalcHelper.rotateXYZByYawPitch(1F, 1F, 0F, shotYaw, shotPitch, this.scaleIn);
		float[] v7 = CalcHelper.rotateXYZByYawPitch(-1F, 1F, 0F, shotYaw, shotPitch, this.scaleIn);
		float[] v8 = CalcHelper.rotateXYZByYawPitch(-1F, -1F, 0F, shotYaw, shotPitch, this.scaleIn);
		
		//particle是以client端視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
		double hx = this.prevPosX + (this.posX - this.prevPosX) * (double)ptick - interpPosX;
        double hy = this.prevPosY + (this.posY - this.prevPosY) * (double)ptick - interpPosY;
        double hz = this.prevPosZ + (this.posZ - this.prevPosZ) * (double)ptick - interpPosZ;
        double tx = this.tarX - interpPosX;
        double ty = this.tarY - interpPosY;
        double tz = this.tarZ - interpPosZ;
        
        //計算外層紅色的8個vertex
        vt[0][0] = hx+v1[0];	vt[0][1] = hy+v1[1];	vt[0][2] = hz+v1[2];
        vt[1][0] = hx+v2[0];	vt[1][1] = hy+v2[1];	vt[1][2] = hz+v2[2];
        vt[2][0] = hx+v3[0];	vt[2][1] = hy+v3[1];	vt[2][2] = hz+v3[2];
        vt[3][0] = hx+v4[0];	vt[3][1] = hy+v4[1];	vt[3][2] = hz+v4[2];
        vt[4][0] = tx+v1[0];	vt[4][1] = ty+v1[1];	vt[4][2] = tz+v1[2];
        vt[5][0] = tx+v2[0];	vt[5][1] = ty+v2[1];	vt[5][2] = tz+v2[2];
        vt[6][0] = tx+v3[0];	vt[6][1] = ty+v3[1];	vt[6][2] = tz+v3[2];
        vt[7][0] = tx+v4[0];	vt[7][1] = ty+v4[1];	vt[7][2] = tz+v4[2];
        //計算內層白色的8個vertex
        vt2[0][0] = hx+v5[0];	vt2[0][1] = hy+v5[1];	vt2[0][2] = hz+v5[2];
        vt2[1][0] = hx+v6[0];	vt2[1][1] = hy+v6[1];	vt2[1][2] = hz+v6[2];
        vt2[2][0] = hx+v7[0];	vt2[2][1] = hy+v7[1];	vt2[2][2] = hz+v7[2];
        vt2[3][0] = hx+v8[0];	vt2[3][1] = hy+v8[1];	vt2[3][2] = hz+v8[2];
        vt2[4][0] = tx+v5[0];	vt2[4][1] = ty+v5[1];	vt2[4][2] = tz+v5[2];
        vt2[5][0] = tx+v6[0];	vt2[5][1] = ty+v6[1];	vt2[5][2] = tz+v6[2];
        vt2[6][0] = tx+v7[0];	vt2[6][1] = ty+v7[1];	vt2[6][2] = tz+v7[2];
        vt2[7][0] = tx+v8[0];	vt2[7][1] = ty+v8[1];	vt2[7][2] = tz+v8[2];

        //start
        render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        
        //內層白色
        render.pos(vt2[3][0], vt2[3][1], vt2[3][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[2][0], vt2[2][1], vt2[2][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[1][0], vt2[1][1], vt2[1][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[0][0], vt2[0][1], vt2[0][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        
        render.pos(vt2[0][0], vt2[0][1], vt2[0][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[1][0], vt2[1][1], vt2[1][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[5][0], vt2[5][1], vt2[5][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[4][0], vt2[4][1], vt2[4][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        
        render.pos(vt2[4][0], vt2[4][1], vt2[4][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[5][0], vt2[5][1], vt2[5][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[6][0], vt2[6][1], vt2[6][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[7][0], vt2[7][1], vt2[7][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        
        render.pos(vt2[7][0], vt2[7][1], vt2[7][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[6][0], vt2[6][1], vt2[6][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[2][0], vt2[2][1], vt2[2][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[3][0], vt2[3][1], vt2[3][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        
        render.pos(vt2[1][0], vt2[1][1], vt2[1][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[2][0], vt2[2][1], vt2[2][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[6][0], vt2[6][1], vt2[6][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[5][0], vt2[5][1], vt2[5][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        
        render.pos(vt2[3][0], vt2[3][1], vt2[3][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[0][0], vt2[0][1], vt2[0][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[4][0], vt2[4][1], vt2[4][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        render.pos(vt2[7][0], vt2[7][1], vt2[7][2]).color(1F, 1F, 1F, this.alphaIn).endVertex();
        
        //外層紅色
        render.pos(vt[3][0], vt[3][1], vt[3][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[2][0], vt[2][1], vt[2][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[1][0], vt[1][1], vt[1][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[0][0], vt[0][1], vt[0][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        
        render.pos(vt[0][0], vt[0][1], vt[0][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[1][0], vt[1][1], vt[1][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[5][0], vt[5][1], vt[5][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[4][0], vt[4][1], vt[4][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        
        render.pos(vt[4][0], vt[4][1], vt[4][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[5][0], vt[5][1], vt[5][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[6][0], vt[6][1], vt[6][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[7][0], vt[7][1], vt[7][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        
        render.pos(vt[7][0], vt[7][1], vt[7][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[6][0], vt[6][1], vt[6][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[2][0], vt[2][1], vt[2][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[3][0], vt[3][1], vt[3][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        
        render.pos(vt[1][0], vt[1][1], vt[1][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[2][0], vt[2][1], vt[2][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[6][0], vt[6][1], vt[6][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[5][0], vt[5][1], vt[5][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        
        render.pos(vt[3][0], vt[3][1], vt[3][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[0][0], vt[0][1], vt[0][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[4][0], vt[4][1], vt[4][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        render.pos(vt[7][0], vt[7][1], vt[7][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut).endVertex();
        
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
    	this.prevPosX = this.posX;
    	this.prevPosY = this.posY;
    	this.prevPosZ = this.posZ;
    	
    	//null check
    	if (host == null || target == null)
    	{
    		this.setExpired();
    	}
    	//update pos
    	else
    	{
    		float[] lookDeg;
    		float[] posOffset;
    		
    		switch (this.particleType)
    		{
    		case 1:		//yamato cannon beam
    			lookDeg = CalcHelper.getLookDegree(this.par1, this.par2, this.par3, false);
            	posOffset = CalcHelper.rotateXYZByYawPitch(0F, 0F, host.width * 2F, lookDeg[0], lookDeg[1], 1F);
            	
        		this.posX = host.posX + posOffset[0];
            	this.posY = host.posY + host.height * 0.6D;
            	this.posZ = host.posZ + posOffset[2];
            	this.shotYaw = lookDeg[0];
            	this.shotPitch = lookDeg[1];
        		this.tarX = target.posX;
        		this.tarY = target.posY + target.height * 0.5F;
        		this.tarZ = target.posZ;
        		
        		//change alpha
        		if (this.particleAge > 20)
        		{
        			this.alphaIn = 1F + (20 - particleAge) * 0.1F;
        			this.alphaOut = this.alphaIn * 0.25F;
        		}
        		else if (this.particleAge < 4)
        		{
        			this.alphaIn = 0.2F + particleAge * 0.2F;
        			this.alphaOut = this.alphaIn * 0.25F;
        		}
        		else
        		{
        			this.alphaIn = 1F;
        			this.alphaOut = 0.1F + this.rand.nextFloat() * 0.25F;
        		}
        		
        		//change scale
        		if (this.particleAge > 20)
        		{
        			this.scaleOut = this.particleScale * (1F + (particleAge - 20));
                	this.scaleIn = this.particleScale * 0.35F  * (1F - (particleAge - 20) * 0.1F);
        		}
        		else if (this.particleAge < 8)
        		{
        			this.scaleOut = this.particleScale * 0.3F * (particleAge * 0.3F);
                	this.scaleIn = this.particleScale * 0.35F * (particleAge * 0.125F);
        		}
        		else
        		{
        			this.scaleOut = this.particleScale * 1F;
                	this.scaleIn = this.particleScale * 0.35F;
        		}
        		
        		//random scale effect
	        	this.scaleOut += this.rand.nextFloat() * 0.2F - 0.05F;
	        	this.scaleIn += this.rand.nextFloat() * 0.08F - 0.04F;
	        	
        	break;
    		case 2:		//守衛標示線: entity類
    			this.tarX = target.posX;
        		this.tarY = target.posY;
        		this.tarZ = target.posZ;
    		case 3:		//守衛標示線: block類
    			this.posX = host.posX;
            	this.posY = host.posY;
            	this.posZ = host.posZ;
            	
    			lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
            	this.shotYaw = lookDeg[0];
            	this.shotPitch = lookDeg[1];
            	
        		if (this.particleAge > 4)
        		{
        			this.alphaIn = 1.0F + (4 - particleAge) * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
        		else
        		{
        			this.alphaIn = 0.2F + particleAge * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
    		break;
    		case 4:		//補給標示線
    			this.tarX = target.posX;
        		this.tarY = target.posY + target.height * 0.5D;
        		this.tarZ = target.posZ;
        		this.posX = host.posX;
            	this.posY = host.posY;
            	this.posZ = host.posZ;
            	
    			lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
            	this.shotYaw = lookDeg[0];
            	this.shotPitch = lookDeg[1];
            	
        		if (this.particleAge > 4)
        		{
        			this.alphaIn = 1.0F + (4 - particleAge) * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
        		else
        		{
        			this.alphaIn = 0.2F + particleAge * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
    		break;
    		case 5:		//位置標示線
    			this.tarX = target.posX;
        		this.tarY = target.posY + 0.2D;
        		this.tarZ = target.posZ;
        		this.posX = host.posX;
            	this.posY = host.posY + 0.65D;
            	this.posZ = host.posZ;
            	
    			lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
            	this.shotYaw = lookDeg[0];
            	this.shotPitch = lookDeg[1];
            	
        		if (this.particleAge > 56)
        		{
        			this.alphaIn *= 0.6F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
    		break;
    		case 6:		//紫色可調粗細光束
	        	lookDeg = CalcHelper.getLookDegree(tarX-posX, (tarY+target.height*0.5D)-(posY+this.par1), tarZ-posZ, false);
	        	this.shotYaw = lookDeg[0];
	        	this.shotPitch = lookDeg[1];
        		this.posX = host.posX;
            	this.posY = host.posY + this.par1;
            	this.posZ = host.posZ;
	        	this.tarX = target.posX;
	    		this.tarY = target.posY + target.height * 0.5D;
	    		this.tarZ = target.posZ;
	    		
        		if (this.particleAge < 8)
        		{
                	this.scaleIn = (float)this.par3 * (1F + 0.6F * this.particleAge);
        			this.alphaIn = 0.2F + particleAge * 0.1F;
        			this.alphaOut = this.alphaIn * 0.25F;
        		}
        		else if (this.particleAge > 10)
        		{
        			this.scaleIn = this.scaleIn * 0.75F;
        			this.alphaIn = 1F + (10 - particleAge) * 0.15F;
        			this.alphaOut = this.alphaIn * 0.25F;
        		}
        		else
        		{
        			this.alphaIn = 1F;
        			this.alphaOut = 0.25F;
        		}
        	break;
    		default:	//red laser
    			//force host look vector
    			this.host.renderYawOffset = shotYaw * Values.N.DIV_180_PI;
    			
    			lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
            	posOffset = CalcHelper.rotateXYZByYawPitch((float)par1, 0F, 0.78F, lookDeg[0], lookDeg[1], 1F);
            	this.shotYaw = lookDeg[0];
            	this.shotPitch = lookDeg[1];
            	this.posX = host.posX + posOffset[0];
            	this.posY = host.posY + par2 + posOffset[1];
            	this.posZ = host.posZ + posOffset[2];
        		this.tarX = target.posX;
        		this.tarY = target.posY + target.height * 0.75D;
        		this.tarZ = target.posZ;
        		
        		if (this.particleAge > 4)
        		{
        			this.alphaIn = 1.0F + (4 - particleAge) * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
        		else
        		{
        			this.alphaIn = 0.2F + particleAge * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
    		break;
    		}//end switch
    	}
    	
        if(this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
        }
    }
    
    
}