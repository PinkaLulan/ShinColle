package com.lulan.shincolle.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


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
public class EntityFXLaserNoTexture extends EntityFX {

	private int particleType;
	private float shotYaw, shotPitch, scaleOut, scaleIn, alphaOut, alphaIn;
	private double tarX, tarY, tarZ, par1, par2, par3;
	private double[][] vt, vt2;				//cube vertex
	private EntityLivingBase host;
	private Entity target;
	
	
    public EntityFXLaserNoTexture(World world, EntityLivingBase host, Entity target, double par1, double par2, double par3, float scale, int type) {
        super(world, host.posX, host.posY, host.posZ, 0.0D, 0.0D, 0.0D);
        this.host = host;
        this.target = target;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleType = type;
        this.noClip = true;
        this.tarX = target.posX;
        this.tarY = target.posY + target.height * 0.75D;
        this.tarZ = target.posZ;
        this.par1 = par1;
        this.par2 = par2;
        this.par3 = par3;
        this.vt = new double[8][3];
        this.vt2 = new double[8][3];
        
        float[] lookDeg;
        float[] posOffset;
        
        switch(type) {
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
        default:	//紅光束砲
        	lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
        	posOffset = ParticleHelper.rotateXYZByYawPitch((float)par1, 0F, 0.78F, lookDeg[0], lookDeg[1], 1F);
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
        }
    }
    
    public EntityFXLaserNoTexture(World world, EntityLivingBase host, double tarX, double tarY, double tarZ, float scale, int type) {
        super(world, host.posX, host.posY, host.posZ, 0.0D, 0.0D, 0.0D);
        this.host = host;
        this.target = host;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleType = type;
        this.noClip = true;
        this.tarX = tarX;
        this.tarY = tarY;
        this.tarZ = tarZ;
        this.par1 = 0D;
        this.par2 = 0D;
        this.par3 = 0D;
        this.vt = new double[8][3];
        this.vt2 = new double[8][3];
        
        float[] lookDeg;
        float[] posOffset;
        
        switch(type) {
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

    //par3 = Yaw的cos值, par4 = Pitch的cos值, par5 = Yaw的sin值
    //par6 = Yaw的sin值乘上-Pitch的sin值, par7 = Yaw的cos值乘上Pitch的sin值
    @Override
	public void renderParticle(Tessellator tess, float ticks, float par3, float par4, float par5, float par6, float par7) {	
		GL11.glPushMatrix();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);	//disable texture
		
		//out
		float[] v1 = ParticleHelper.rotateXYZByYawPitch(1F, -1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v2 = ParticleHelper.rotateXYZByYawPitch(1F, 1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v3 = ParticleHelper.rotateXYZByYawPitch(-1F, 1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v4 = ParticleHelper.rotateXYZByYawPitch(-1F, -1F, -1F, shotYaw, shotPitch, this.scaleOut);
		//in
		float[] v5 = ParticleHelper.rotateXYZByYawPitch(1F, -1F, 0F, shotYaw, shotPitch, this.scaleIn);
		float[] v6 = ParticleHelper.rotateXYZByYawPitch(1F, 1F, 0F, shotYaw, shotPitch, this.scaleIn);
		float[] v7 = ParticleHelper.rotateXYZByYawPitch(-1F, 1F, 0F, shotYaw, shotPitch, this.scaleIn);
		float[] v8 = ParticleHelper.rotateXYZByYawPitch(-1F, -1F, 0F, shotYaw, shotPitch, this.scaleIn);
		
		//particle是以client端視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
        double hx = this.posX - interpPosX;
        double hy = this.posY - interpPosY;
        double hz = this.posZ - interpPosZ;
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

        //start tess
        tess.startDrawingQuads();
        
        //內層白色
        tess.setColorRGBA_F(1F, 1F, 1F, this.alphaIn);
        tess.setBrightness(240);
        
        tess.addVertex(vt2[3][0], vt2[3][1], vt2[3][2]);
        tess.addVertex(vt2[2][0], vt2[2][1], vt2[2][2]);
        tess.addVertex(vt2[1][0], vt2[1][1], vt2[1][2]);
        tess.addVertex(vt2[0][0], vt2[0][1], vt2[0][2]);
        
        tess.addVertex(vt2[0][0], vt2[0][1], vt2[0][2]);
        tess.addVertex(vt2[1][0], vt2[1][1], vt2[1][2]);
        tess.addVertex(vt2[5][0], vt2[5][1], vt2[5][2]);
        tess.addVertex(vt2[4][0], vt2[4][1], vt2[4][2]);
        
        tess.addVertex(vt2[4][0], vt2[4][1], vt2[4][2]);
        tess.addVertex(vt2[5][0], vt2[5][1], vt2[5][2]);
        tess.addVertex(vt2[6][0], vt2[6][1], vt2[6][2]);
        tess.addVertex(vt2[7][0], vt2[7][1], vt2[7][2]);
        
        tess.addVertex(vt2[7][0], vt2[7][1], vt2[7][2]);
        tess.addVertex(vt2[6][0], vt2[6][1], vt2[6][2]);
        tess.addVertex(vt2[2][0], vt2[2][1], vt2[2][2]);
        tess.addVertex(vt2[3][0], vt2[3][1], vt2[3][2]);
        
        tess.addVertex(vt2[1][0], vt2[1][1], vt2[1][2]);
        tess.addVertex(vt2[2][0], vt2[2][1], vt2[2][2]);
        tess.addVertex(vt2[6][0], vt2[6][1], vt2[6][2]);
        tess.addVertex(vt2[5][0], vt2[5][1], vt2[5][2]);
        
        tess.addVertex(vt2[3][0], vt2[3][1], vt2[3][2]);
        tess.addVertex(vt2[0][0], vt2[0][1], vt2[0][2]);
        tess.addVertex(vt2[4][0], vt2[4][1], vt2[4][2]);
        tess.addVertex(vt2[7][0], vt2[7][1], vt2[7][2]);
        
        //外層紅色
        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut);
//        tess.setBrightness(240);
        tess.addVertex(vt[3][0], vt[3][1], vt[3][2]);
        tess.addVertex(vt[2][0], vt[2][1], vt[2][2]);
        tess.addVertex(vt[1][0], vt[1][1], vt[1][2]);
        tess.addVertex(vt[0][0], vt[0][1], vt[0][2]);
        
        tess.addVertex(vt[0][0], vt[0][1], vt[0][2]);
        tess.addVertex(vt[1][0], vt[1][1], vt[1][2]);
        tess.addVertex(vt[5][0], vt[5][1], vt[5][2]);
        tess.addVertex(vt[4][0], vt[4][1], vt[4][2]);
        
        tess.addVertex(vt[4][0], vt[4][1], vt[4][2]);
        tess.addVertex(vt[5][0], vt[5][1], vt[5][2]);
        tess.addVertex(vt[6][0], vt[6][1], vt[6][2]);
        tess.addVertex(vt[7][0], vt[7][1], vt[7][2]);
        
        tess.addVertex(vt[7][0], vt[7][1], vt[7][2]);
        tess.addVertex(vt[6][0], vt[6][1], vt[6][2]);
        tess.addVertex(vt[2][0], vt[2][1], vt[2][2]);
        tess.addVertex(vt[3][0], vt[3][1], vt[3][2]);
        
        tess.addVertex(vt[1][0], vt[1][1], vt[1][2]);
        tess.addVertex(vt[2][0], vt[2][1], vt[2][2]);
        tess.addVertex(vt[6][0], vt[6][1], vt[6][2]);
        tess.addVertex(vt[5][0], vt[5][1], vt[5][2]);
        
        tess.addVertex(vt[3][0], vt[3][1], vt[3][2]);
        tess.addVertex(vt[0][0], vt[0][1], vt[0][2]);
        tess.addVertex(vt[4][0], vt[4][1], vt[4][2]);
        tess.addVertex(vt[7][0], vt[7][1], vt[7][2]);
        
        //stop tess for restore texture
        tess.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);
		GL11.glPopMatrix();
    }
    
    //layer: 0:particle 1:terrain 2:items 3:custom?
    @Override
    public int getFXLayer() {
        return 3;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate() {
    	//null check
    	if(host == null || target == null) {
    		this.setDead();
    	}
    	//update pos
    	else {
    		float[] lookDeg;
    		float[] posOffset;
    		
    		switch(this.particleType) {
    		case 1:		//yamato cannon beam
    			lookDeg = CalcHelper.getLookDegree(this.par1, this.par2, this.par3, false);
            	posOffset = ParticleHelper.rotateXYZByYawPitch(0F, 0F, host.width * 2F, lookDeg[0], lookDeg[1], 1F);
            	
        		this.posX = host.posX + posOffset[0];
            	this.posY = host.posY + host.height * 0.6D;
            	this.posZ = host.posZ + posOffset[2];
            	this.shotYaw = lookDeg[0];
            	this.shotPitch = lookDeg[1];
        		this.tarX = target.posX;
        		this.tarY = target.posY + target.height * 0.5F;
        		this.tarZ = target.posZ;
        		
        		//change alpha
        		if(this.particleAge > 20) {
        			this.alphaIn = 1F + (20 - particleAge) * 0.1F;
        			this.alphaOut = this.alphaIn * 0.25F;
        		}
        		else if(this.particleAge < 4) {
        			this.alphaIn = 0.2F + particleAge * 0.2F;
        			this.alphaOut = this.alphaIn * 0.25F;
        		}
        		else {
        			this.alphaIn = 1F;
        			this.alphaOut = 0.1F + this.rand.nextFloat() * 0.25F;
        		}
        		
        		//change scale
        		if(this.particleAge > 20) {
        			this.scaleOut = this.particleScale * (1F + (particleAge - 20));
                	this.scaleIn = this.particleScale * 0.35F  * (1F - (particleAge - 20) * 0.1F);
        		}
        		else if(this.particleAge < 8) {
        			this.scaleOut = this.particleScale * 0.3F * (particleAge * 0.3F);
                	this.scaleIn = this.particleScale * 0.35F * (particleAge * 0.125F);
        		}
        		else {
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
            	
        		if(this.particleAge > 4) {
        			this.alphaIn = 1.0F + (4 - particleAge) * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
        		else {
        			this.alphaIn = 0.2F + particleAge * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
    			break;
    		default:	//red laser
    			//force host look vector
    			this.host.renderYawOffset = shotYaw * Values.N.RAD_DIV;
    			
    			lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
            	posOffset = ParticleHelper.rotateXYZByYawPitch((float)par1, 0F, 0.78F, lookDeg[0], lookDeg[1], 1F);
            	this.shotYaw = lookDeg[0];
            	this.shotPitch = lookDeg[1];
            	this.posX = host.posX + posOffset[0];
            	this.posY = host.posY + par2 + posOffset[1];
            	this.posZ = host.posZ + posOffset[2];
        		this.tarX = target.posX;
        		this.tarY = target.posY;
        		this.tarZ = target.posZ;
        		if(this.particleAge > 4) {
        			this.alphaIn = 1.0F + (4 - particleAge) * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
        		else {
        			this.alphaIn = 0.2F + particleAge * 0.2F;
        			this.alphaOut = this.alphaIn * 0.5F;
        		}
    			break;
    		}
    	}
    	
        if(this.particleAge++ > this.particleMaxAge) {
            this.setDead();
        }
    }
}


