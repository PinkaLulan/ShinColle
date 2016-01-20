package com.lulan.shincolle.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


/**LASER NO TEXTURE PARTICLE
 * 給定host, target -> 生成雷射特效
 * 此為柱狀3D雷射(即六面長方體)
 * 
 * type: 0: par1為X軸位置(分左右光炮), par2為發射高度
 */
@SideOnly(Side.CLIENT)
public class EntityFXLaserNoTexture extends EntityFX {

	private int particleType;
	private float shotYaw, shotPitch;
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
        this.tarY = target.posY + target.height * 0.5D;
        this.tarZ = target.posZ;
        this.par1 = par1;
        this.par2 = par2;
        this.par3 = par3;
        this.vt = new double[8][3];
        this.vt2 = new double[8][3];
        
        switch(type) {
        default:
        case 0:		//紅光束砲
        	float[] lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
        	float[] posOffset = ParticleHelper.rotateXYZByYawPitch((float)par1, 0F, 0.78F, lookDeg[0], lookDeg[1], 1F);
        	this.shotYaw = lookDeg[0];
        	this.shotPitch = lookDeg[1];
        	this.posX += posOffset[0];
        	this.posY += (par2 + posOffset[1]);
        	this.posZ += posOffset[2];
        	this.particleMaxAge = 8;
        	this.particleRed = 1F;
        	this.particleGreen = 0F;
        	this.particleBlue = 0F;
        	this.particleAlpha = 0.2F;
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
		
		//計算角度造成的位移
		float s = this.particleScale * 0.5F;
		//外層紅色
		float[] v1 = ParticleHelper.rotateXYZByYawPitch(1F, -1F, 0F, shotYaw, shotPitch, s);
		float[] v2 = ParticleHelper.rotateXYZByYawPitch(1F, 1F, 0F, shotYaw, shotPitch, s);
		float[] v3 = ParticleHelper.rotateXYZByYawPitch(-1F, 1F, 0F, shotYaw, shotPitch, s);
		float[] v4 = ParticleHelper.rotateXYZByYawPitch(-1F, -1F, 0F, shotYaw, shotPitch, s);
		//內層白色
		float[] v5 = ParticleHelper.rotateXYZByYawPitch(0.25F, -0.25F, 0F, shotYaw, shotPitch, s);
		float[] v6 = ParticleHelper.rotateXYZByYawPitch(0.25F, 0.25F, 0F, shotYaw, shotPitch, s);
		float[] v7 = ParticleHelper.rotateXYZByYawPitch(-0.25F, 0.25F, 0F, shotYaw, shotPitch, s);
		float[] v8 = ParticleHelper.rotateXYZByYawPitch(-0.25F, -0.25F, 0F, shotYaw, shotPitch, s);
		
		this.host.renderYawOffset = shotYaw * Values.N.RAD_DIV;
		//particle是以玩家視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
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
        tess.setColorRGBA_F(1F, 1F, 1F, this.particleAlpha + 0.2F);
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
        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha * 0.5F);
        tess.setBrightness(240);
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
    		switch(this.particleType) {
    		case 0:	//紅光砲
    			float[] lookDeg = CalcHelper.getLookDegree(tarX-posX, tarY-posY, tarZ-posZ, false);
            	float[] posOffset = ParticleHelper.rotateXYZByYawPitch((float)par1, 0F, 0.78F, lookDeg[0], lookDeg[1], 1F);
            	this.shotYaw = lookDeg[0];
            	this.shotPitch = lookDeg[1];
            	this.posX = host.posX + posOffset[0];
            	this.posY = host.posY + par2 + posOffset[1];
            	this.posZ = host.posZ + posOffset[2];
        		this.tarX = target.posX;
        		this.tarY = target.posY;
        		this.tarZ = target.posZ;
        		if(this.particleAge > 4) {
        			this.particleAlpha = 1.0F + (4 - particleAge) * 0.2F;
        		}
        		else {
        			this.particleAlpha = 0.2F + particleAge * 0.2F;
        		}
    			break;
    		}
    	}
    	
        if(this.particleAge++ > this.particleMaxAge) {
            this.setDead();
        }
    }
}


