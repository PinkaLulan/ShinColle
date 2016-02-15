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


/** CUBE PARTICLE
 *  生成兩層方塊, 外殼半透明, 方塊可變大小跟震動等
 * 
 * type:
 *   0: 大和波動砲集氣特效: 震動雙層方塊
 *   
 */
@SideOnly(Side.CLIENT)
public class EntityFXCube extends EntityFX {

	private int particleType;
	private float shotYaw, shotPitch, scaleOut, scaleIn, alphaOut, alphaIn;
	private double par1, par2, par3;
	private double[][] vt, vt2;				//cube vertex
	private EntityLivingBase host;
	
	
    public EntityFXCube(World world, EntityLivingBase host, double par1, double par2, double par3, float scale, int type) {
        super(world, host.posX, host.posY, host.posZ, 0.0D, 0.0D, 0.0D);
        this.host = host;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleType = type;
        this.noClip = true;
        this.par1 = par1;
        this.par2 = par2;
        this.par3 = par3;
        this.vt = new double[8][3];
        this.vt2 = new double[8][3];
        
        float[] lookDeg;
        float[] posOffset;
        
        switch(type) {
        case 1:		//yamato beam head
        	this.particleMaxAge = 30;
        	this.particleRed = 1F;
        	this.particleGreen = 0.8F;
        	this.particleBlue = 0.9F;
        	break;
        default:	//yamato cannon charging
        	this.particleScale = (float) par1;  //par1 as new scale
        	this.particleMaxAge = 40;
        	this.particleRed = 1F;
        	this.particleGreen = 0.8F;
        	this.particleBlue = 0.9F;
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
		
		//calc rotate vector
		//out
		float[] v1 = ParticleHelper.rotateXYZByYawPitch(-1F, -1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v2 = ParticleHelper.rotateXYZByYawPitch(-1F, 1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v3 = ParticleHelper.rotateXYZByYawPitch(1F, 1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v4 = ParticleHelper.rotateXYZByYawPitch(1F, -1F, -1F, shotYaw, shotPitch, this.scaleOut);
		float[] v5 = ParticleHelper.rotateXYZByYawPitch(-1F, -1F, 1F, shotYaw, shotPitch, this.scaleOut);
		float[] v6 = ParticleHelper.rotateXYZByYawPitch(-1F, 1F, 1F, shotYaw, shotPitch, this.scaleOut);
		float[] v7 = ParticleHelper.rotateXYZByYawPitch(1F, 1F, 1F, shotYaw, shotPitch, this.scaleOut);
		float[] v8 = ParticleHelper.rotateXYZByYawPitch(1F, -1F, 1F, shotYaw, shotPitch, this.scaleOut);
		//in
		float[] t1 = ParticleHelper.rotateXYZByYawPitch(-1F, -1F, -1F, shotYaw, shotPitch, this.scaleIn);
		float[] t2 = ParticleHelper.rotateXYZByYawPitch(-1F, 1F, -1F, shotYaw, shotPitch, this.scaleIn);
		float[] t3 = ParticleHelper.rotateXYZByYawPitch(1F, 1F, -1F, shotYaw, shotPitch, this.scaleIn);
		float[] t4 = ParticleHelper.rotateXYZByYawPitch(1F, -1F, -1F, shotYaw, shotPitch, this.scaleIn);
		float[] t5 = ParticleHelper.rotateXYZByYawPitch(-1F, -1F, 1F, shotYaw, shotPitch, this.scaleIn);
		float[] t6 = ParticleHelper.rotateXYZByYawPitch(-1F, 1F, 1F, shotYaw, shotPitch, this.scaleIn);
		float[] t7 = ParticleHelper.rotateXYZByYawPitch(1F, 1F, 1F, shotYaw, shotPitch, this.scaleIn);
		float[] t8 = ParticleHelper.rotateXYZByYawPitch(1F, -1F, 1F, shotYaw, shotPitch, this.scaleIn);
		
		//particle是以client視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
		double hx = this.posX - interpPosX;
        double hy = this.posY - interpPosY;
        double hz = this.posZ - interpPosZ;
        
        //out
        vt[0][0] = hx+v1[0];	vt[0][1] = hy+v1[1];	vt[0][2] = hz+v1[2];
        vt[1][0] = hx+v2[0];	vt[1][1] = hy+v2[1];	vt[1][2] = hz+v2[2];
        vt[2][0] = hx+v3[0];	vt[2][1] = hy+v3[1];	vt[2][2] = hz+v3[2];
        vt[3][0] = hx+v4[0];	vt[3][1] = hy+v4[1];	vt[3][2] = hz+v4[2];
        vt[4][0] = hx+v5[0];	vt[4][1] = hy+v5[1];	vt[4][2] = hz+v5[2];
        vt[5][0] = hx+v6[0];	vt[5][1] = hy+v6[1];	vt[5][2] = hz+v6[2];
        vt[6][0] = hx+v7[0];	vt[6][1] = hy+v7[1];	vt[6][2] = hz+v7[2];
        vt[7][0] = hx+v8[0];	vt[7][1] = hy+v8[1];	vt[7][2] = hz+v8[2];
        //in
        vt2[0][0] = hx+t1[0];	vt2[0][1] = hy+t1[1];	vt2[0][2] = hz+t1[2];
        vt2[1][0] = hx+t2[0];	vt2[1][1] = hy+t2[1];	vt2[1][2] = hz+t2[2];
        vt2[2][0] = hx+t3[0];	vt2[2][1] = hy+t3[1];	vt2[2][2] = hz+t3[2];
        vt2[3][0] = hx+t4[0];	vt2[3][1] = hy+t4[1];	vt2[3][2] = hz+t4[2];
        vt2[4][0] = hx+t5[0];	vt2[4][1] = hy+t5[1];	vt2[4][2] = hz+t5[2];
        vt2[5][0] = hx+t6[0];	vt2[5][1] = hy+t6[1];	vt2[5][2] = hz+t6[2];
        vt2[6][0] = hx+t7[0];	vt2[6][1] = hy+t7[1];	vt2[6][2] = hz+t7[2];
        vt2[7][0] = hx+t8[0];	vt2[7][1] = hy+t8[1];	vt2[7][2] = hz+t8[2];

        //start tess
        tess.startDrawingQuads();
        
        //in
        tess.setColorRGBA_F(1F, 1F, 1F, this.alphaIn);
        tess.setBrightness(240);

        tess.addVertex(vt2[7][0], vt2[7][1], vt2[7][2]);
        tess.addVertex(vt2[6][0], vt2[6][1], vt2[6][2]);
        tess.addVertex(vt2[5][0], vt2[5][1], vt2[5][2]);
        tess.addVertex(vt2[4][0], vt2[4][1], vt2[4][2]);
        
        tess.addVertex(vt2[3][0], vt2[3][1], vt2[3][2]);
        tess.addVertex(vt2[2][0], vt2[2][1], vt2[2][2]);
        tess.addVertex(vt2[6][0], vt2[6][1], vt2[6][2]);
        tess.addVertex(vt2[7][0], vt2[7][1], vt2[7][2]);

        tess.addVertex(vt2[0][0], vt2[0][1], vt2[0][2]);
        tess.addVertex(vt2[1][0], vt2[1][1], vt2[1][2]);
        tess.addVertex(vt2[2][0], vt2[2][1], vt2[2][2]);
        tess.addVertex(vt2[3][0], vt2[3][1], vt2[3][2]);
        
        tess.addVertex(vt2[4][0], vt2[4][1], vt2[4][2]);
        tess.addVertex(vt2[5][0], vt2[5][1], vt2[5][2]);
        tess.addVertex(vt2[1][0], vt2[1][1], vt2[1][2]);
        tess.addVertex(vt2[0][0], vt2[0][1], vt2[0][2]);
        
        tess.addVertex(vt2[2][0], vt2[2][1], vt2[2][2]);
        tess.addVertex(vt2[1][0], vt2[1][1], vt2[1][2]);
        tess.addVertex(vt2[5][0], vt2[5][1], vt2[5][2]);
        tess.addVertex(vt2[6][0], vt2[6][1], vt2[6][2]);
        
        tess.addVertex(vt2[3][0], vt2[3][1], vt2[3][2]);
        tess.addVertex(vt2[7][0], vt2[7][1], vt2[7][2]);
        tess.addVertex(vt2[4][0], vt2[4][1], vt2[4][2]);
        tess.addVertex(vt2[0][0], vt2[0][1], vt2[0][2]);
        
        //out
        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.alphaOut);
        tess.setBrightness(240);
        
        tess.addVertex(vt[7][0], vt[7][1], vt[7][2]);
        tess.addVertex(vt[6][0], vt[6][1], vt[6][2]);
        tess.addVertex(vt[5][0], vt[5][1], vt[5][2]);
        tess.addVertex(vt[4][0], vt[4][1], vt[4][2]);
        
        tess.addVertex(vt[3][0], vt[3][1], vt[3][2]);
        tess.addVertex(vt[2][0], vt[2][1], vt[2][2]);
        tess.addVertex(vt[6][0], vt[6][1], vt[6][2]);
        tess.addVertex(vt[7][0], vt[7][1], vt[7][2]);

        tess.addVertex(vt[0][0], vt[0][1], vt[0][2]);
        tess.addVertex(vt[1][0], vt[1][1], vt[1][2]);
        tess.addVertex(vt[2][0], vt[2][1], vt[2][2]);
        tess.addVertex(vt[3][0], vt[3][1], vt[3][2]);
        
        tess.addVertex(vt[4][0], vt[4][1], vt[4][2]);
        tess.addVertex(vt[5][0], vt[5][1], vt[5][2]);
        tess.addVertex(vt[1][0], vt[1][1], vt[1][2]);
        tess.addVertex(vt[0][0], vt[0][1], vt[0][2]);
        
        tess.addVertex(vt[2][0], vt[2][1], vt[2][2]);
        tess.addVertex(vt[1][0], vt[1][1], vt[1][2]);
        tess.addVertex(vt[5][0], vt[5][1], vt[5][2]);
        tess.addVertex(vt[6][0], vt[6][1], vt[6][2]);
        
        tess.addVertex(vt[3][0], vt[3][1], vt[3][2]);
        tess.addVertex(vt[7][0], vt[7][1], vt[7][2]);
        tess.addVertex(vt[4][0], vt[4][1], vt[4][2]);
        tess.addVertex(vt[0][0], vt[0][1], vt[0][2]);
        
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
    	if(host == null) {
    		this.setDead();
    	}
    	//update pos
    	else {
    		float[] lookDeg;
    		float[] posOffset;
    		
    		switch(this.particleType) {
    		case 1:		//yamato beam head
    			//particle position
    			lookDeg = CalcHelper.getLookDegree(this.par1, this.par2, this.par3, false);
            	posOffset = ParticleHelper.rotateXYZByYawPitch(0F, 0F, host.width * 2F, lookDeg[0], lookDeg[1], 1F);
            	
        		this.posX = this.host.posX + posOffset[0];
            	this.posY = this.host.posY + host.height * 0.6D;
                this.posZ = this.host.posZ + posOffset[2];
            	this.shotYaw = lookDeg[0];
            	this.shotPitch = lookDeg[1];
            	
            	//change alpha
        		if(this.particleAge > 20) {
        			this.alphaIn = 1F + (20 - particleAge) * 0.1F;
        		}
        		else if(this.particleAge < 4) {
        			this.alphaIn = 0.2F + particleAge * 0.2F;
        		}
        		else {
        			this.alphaIn = 0.95F;
        		}
        		this.alphaOut = 0F;
        		
        		//change scale
        		if(this.particleAge > 20) {
        			this.scaleOut = this.particleScale * (1F + (particleAge - 20));
                	this.scaleIn = this.particleScale * 0.4F  * (1F - (particleAge - 20) * 0.1F);
        		}
        		else if(this.particleAge < 8) {
        			this.scaleOut = this.particleScale * 0.3F * (particleAge * 0.3F);
                	this.scaleIn = this.particleScale * 0.4F * (particleAge * 0.125F);
        		}
        		else {
        			this.scaleOut = this.particleScale * 1F;
                	this.scaleIn = this.particleScale * 0.4F;
        		}
        		
        		//random scale effect
	        	this.scaleOut += this.rand.nextFloat() * 0.04F - 0.01F;
	        	this.scaleIn += this.rand.nextFloat() * 0.04F - 0.005F;
    			break;
    		default:	//yamato cannon charging
    			//particle position
    			posOffset = ParticleHelper.rotateXZByAxis(host.width * 2F, 0F, (host.renderYawOffset % 360) * Values.N.RAD_MUL, 1F);
            	
				this.posX = this.host.posX + posOffset[1];
            	this.posY = this.host.posY + host.height * 0.6D;
                this.posZ = this.host.posZ + posOffset[0];
    			this.shotYaw = (host.renderYawOffset % 360) * Values.N.RAD_MUL;
            	this.shotPitch = (host.rotationPitch % 360) * Values.N.RAD_MUL;
            	
        		//change alpha
            	if(this.particleAge < 32) {
            		this.alphaIn = this.rand.nextFloat() * 0.5F + 0.75F;
            	}
            	else {
            		this.alphaIn = (this.particleMaxAge - this.particleAge) * 0.1F + 0.2F;
            	}
            	this.alphaOut = this.alphaIn * 0.25F;
        		
        		//change scale
    			this.scaleOut = this.particleScale * this.particleAge * ((MathHelper.cos(this.particleAge) + 1F) * 0.005F + 0.015F);
            	this.scaleIn = this.scaleOut * 0.75F;
            	
        		//random scale effect
	        	this.scaleOut += this.rand.nextFloat() * 0.04F - 0.01F;
	        	this.scaleIn += this.rand.nextFloat() * 0.04F - 0.005F;
        		break;
    		}
    	}
    	
        if(this.particleAge++ > this.particleMaxAge) {
            this.setDead();
        }
    }
    
    
}