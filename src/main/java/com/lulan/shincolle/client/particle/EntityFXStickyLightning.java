package com.lulan.shincolle.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


/** NO TEXTURE STICKY LIGHTNING PARTICLE
 * 
 *  lightning will link from start point to entity point
 */
@SideOnly(Side.CLIENT)
public class EntityFXStickyLightning extends EntityFX {

	private int particleType;		//0:red white lightning
	private Entity host;
	private int numStem;			//lightning length
	private double[][] strip;	//prev lightning shape
	private float scaleXZ, scaleY;
	private double orgPosX, orgPosY, orgPosZ;
	
    public EntityFXStickyLightning(World world, Entity entity, float scale, int type) {
        super(world, entity.posX, entity.posY, entity.posZ, 0.0D, 0.0D, 0.0D);  
        this.host = entity;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.orgPosX = this.host.posX;
        this.orgPosY = this.host.posY + this.host.height * 0.5D;
        this.orgPosZ = this.host.posZ;
        this.particleScale = scale;
        this.noClip = true;
        this.particleType = type;

        switch(type) {
        default:
        	this.particleRed = 1F;
            this.particleGreen = 0.3F;
            this.particleBlue = 0.3F;
            this.particleAlpha = 0.5F;
            this.particleMaxAge = 31;
            this.numStem = 4;
            this.scaleXZ = 0.01F;
            this.scaleY = 0.12F;
            this.posY += 3D;
            this.orgPosX = 0D;
            this.orgPosY = 0D;
            this.orgPosZ = 0D;
            
//            //calc particle position for MountHbH
//        	float randx = rand.nextFloat() + 0.1F;
//        	float[] newPos = ParticleHelper.rotateXZByAxis(0.8F+rand.nextFloat()*0.2F, randx, ((EntityLivingBase)host).renderYawOffset * -0.01745F, 1F);
//            if(this.host != null) {
//            	this.posX = this.host.posX + newPos[0];
//            	this.posY = this.host.posY + 1.53D + randx * 0.25D;
//                this.posZ = this.host.posZ + newPos[1];
//            }
        	break;
        }
        
        this.strip = new double[numStem][6];	//prev lightning shape
    }

    @Override
	public void renderParticle(Tessellator tess, float ticks, float par3, float par4, float par5, float par6, float par7) {
		//stop last tess
//    	tess.draw();
    	
    	GL11.glPushMatrix();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);	//disable texture
//		GL11.glEnable(GL11.GL_DEPTH_TEST);	//DEPTH TEST開啟後才能使用glDepthFunc
//		GL11.glDepthFunc(GL11.GL_ALWAYS);
		
		float f6 = 0F;
		float f7 = 1F;
		float f8 = 0F;
		float f9 = 1F;
		
        float px = (float)(this.prevPosX + (this.posX - this.prevPosX) * ticks - interpPosX);
        float py = (float)(this.prevPosY + (this.posY - this.prevPosY) * ticks - interpPosY);
        float pz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * ticks - interpPosZ);

    	float offx = 0F;
        float offz = 0F;
        float offy = 0F;
        
        /**從起點開始
         * 越後面的step, random range越大 (閃電到後面分支分散)
         * 
         */
        for(int i = 0; i < numStem; i++) {
        	
        }
        
        //test
        
        
		
		
        
        //start tess
        tess.startDrawing(GL11.GL_QUAD_STRIP);
        tess.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
        tess.setBrightness(240);
        
    	//QUAD STRIP 必須先指定下方兩點(左下 -> 右下), 再指定上方兩點(左上 -> 右上), 該面才會朝向玩家
    	//跟 QUAD 不同 (右下 -> 右上 -> 左上 -> 左下)
        for(int i = numStem - 1; i >= 0; i--) {
        	tess.addVertex(strip[i][0],strip[i][1],strip[i][2]);
        	tess.addVertex(strip[i][3],strip[i][4],strip[i][5]);
        }
        
        //stop tess for restore texture
        tess.draw();
        
//        GL11.glDepthFunc(GL11.GL_LEQUAL);
//		GL11.glDisable(GL11.GL_DEPTH_TEST);	//DEPTH TEST關閉
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
//		GL11.glDepthMask(false);
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
    	//this is both side particle
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if(this.particleAge++ > this.particleMaxAge) {
            this.setDead();
        }
        
        //type 0: HarbourHime Mount
//        if(this.particleType == 0 && host != null) {
//        	float randx = rand.nextFloat() + 0.1F;
//        	float[] newPos = ParticleHelper.rotateXZByAxis(0.8F+rand.nextFloat()*0.2F, randx, ((EntityLivingBase)host).renderYawOffset * -0.01745F, 1F);
//
//        	//x=1 y=1.8, x=0.7 y=1.72, x=0.5 y=1.65, x=0.3 y=1.6, x=0.1 y=1.55
//            if(this.host != null) {
//            	this.posX = this.host.posX + newPos[0];
//            	this.posY = this.host.posY + 1.76D + randx * 0.25D;
//                this.posZ = this.host.posZ + newPos[1];
//            }
//        }
    }
}


