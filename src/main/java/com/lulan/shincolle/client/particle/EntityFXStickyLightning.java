package com.lulan.shincolle.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


/** NO TEXTURE LIGHTNING PARTICLE
 * 
 *  WITHOUT rotate to player viewing angle
 *  
 *  shape: all stem with random wide and random Y length
 *  
 *  parms: world, host, scale, type
 */
@SideOnly(Side.CLIENT)
public class EntityFXStickyLightning extends EntityFX {

	private int particleType;		//0:red white lightning
	private Entity host;
	private int numStem;			//lightning length number
	private double[][] prevShape;	//prev lightning shape
	private float scaleX, scaleZ, scaleY, stemWidth;
	
	
    public EntityFXStickyLightning(World world, Entity entity, float scale, int life, int type) {
        super(world, entity.posX, entity.posY, entity.posZ, 0.0D, 0.0D, 0.0D);  
        this.host = entity;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.noClip = true;
        this.particleType = type;

        switch(type) {
        case 1:  //yamato cannon charge steady state
        	this.particleRed = 1F;
            this.particleGreen = 0.5F;
            this.particleBlue = 0.7F;
            this.particleAlpha = 1F;
            this.particleMaxAge = life;
            this.numStem = 4;
            this.scaleX = 0.5F + host.width * 0.5F;
            this.scaleY = 0.5F + host.width * 0.5F;
            this.scaleZ = 0.5F + host.width * 0.5F;
            this.stemWidth = 0.01F * host.width;
            
            //random position
            this.posX = this.host.posX + rand.nextFloat() * 2F - 1F;
        	this.posY = this.host.posY + host.height * 0.6D;
            this.posZ = this.host.posZ + rand.nextFloat() * 2F - 1F;
            break;
        case 2:  //yamato cannon charging state IN, EntityLivingBase ONLY
        	this.particleRed = 1F;
            this.particleGreen = 0.5F;
            this.particleBlue = 0.7F;
            this.particleAlpha = 1F;
            this.particleMaxAge = life;
            this.numStem = 12;
            this.scaleX = 0.25F;
            this.scaleY = 0.25F;
            this.scaleZ = 0.25F;
            this.stemWidth = 0.005F;
            
            //particle position
			float[] partPos = ParticleHelper.rotateXZByAxis(1F, 0F, (((EntityLivingBase)host).renderYawOffset % 360) * Values.N.RAD_MUL, 1F);
        	
            this.posX = this.host.posX + partPos[1];
        	this.posY = this.host.posY + host.height * 0.8D;
            this.posZ = this.host.posZ + partPos[0];
            break;
        case 3:  //yamato cannon charging state OUT, EntityLivingBase ONLY
        	this.particleRed = 1F;
            this.particleGreen = 0.5F;
            this.particleBlue = 0.7F;
            this.particleAlpha = 1F;
            this.particleMaxAge = life;
            this.numStem = 4;
            this.scaleX = 1F;
            this.scaleY = 1F;
            this.scaleZ = 1F;
            this.stemWidth = 0.025F;
            break;
        case 4:  //railgun
        	this.particleRed = 0F;
            this.particleGreen = 0.7F;
            this.particleBlue = 1F;
            this.particleAlpha = 1F;
            this.particleMaxAge = life;
            this.numStem = 8;
            this.scaleX = 0.75F;
            this.scaleY = 0.75F;
            this.scaleZ = 0.75F;
            this.stemWidth = 0.005F;
            
            //random position
            this.posX = this.host.posX + rand.nextFloat() * 0.25F - 0.125F;
        	this.posY = this.host.posY + host.height * 0.5D + rand.nextFloat() * 0.25F - 0.125F;
            this.posZ = this.host.posZ + rand.nextFloat() * 0.25F - 0.125F;
            break;
        default:
        	this.particleRed = 1F;
            this.particleGreen = 0.5F;
            this.particleBlue = 0.7F;
            this.particleAlpha = 1F;
            this.particleMaxAge = life;
            this.numStem = 8;
            this.scaleX = 1.75F;
            this.scaleY = 1.75F;
            this.scaleZ = 1.75F;
            this.stemWidth = 0.006F;
            
            //random position
            this.posX = this.host.posX + rand.nextFloat() * 2F - 1F;
        	this.posY = this.host.posY + host.height * 0.5D + rand.nextFloat() * 2F - 1F;
            this.posZ = this.host.posZ + rand.nextFloat() * 2F - 1F;
            break;
        }
        
        this.prevShape = new double[numStem][6];	//prev lightning shape
    }

    @Override
	public void renderParticle(Tessellator tess, float ticks, float par3, float par4, float par5, float par6, float par7) {
		//stop last tess
//    	tess.draw();
    	
    	GL11.glPushMatrix();
		//使用自帶的貼圖檔
//		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);	//disable texture
//		GL11.glEnable(GL11.GL_DEPTH_TEST);	//DEPTH TEST開啟後才能使用glDepthFunc
//		GL11.glDepthFunc(GL11.GL_ALWAYS);
		
        float px = (float)(this.prevPosX + (this.posX - this.prevPosX) * ticks - interpPosX);
        float py = (float)(this.prevPosY + (this.posY - this.prevPosY) * ticks - interpPosY);
        float pz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * ticks - interpPosZ);
    	float offx = 0F;
        float offz = 0F;
        float offy = 0F;
        
        if(this.particleAge % 2 == 0) {
	        //越後面的step, random range越大 (閃電到後面分支分散)
	        for(int i = 0; i < numStem; i++) {
	        	//stem random position
	            offx = (rand.nextFloat() - 0.5F) * this.scaleX;
	        	offz = (rand.nextFloat() - 0.5F) * this.scaleZ;
	        	offy = (rand.nextFloat() - 0.5F) * this.scaleY;
	        	
	        	//xyz position: 0:x1, 1:y1, 2:z1, 3:x2, 4:y2, 5:z2
	        	if(i == 0) {  //first stem
	        		prevShape[i][0] = px + offx;
	        		prevShape[i][1] = py + offy;
	        		prevShape[i][2] = pz + offz;
	        		prevShape[i][3] = prevShape[i][0];
	        		prevShape[i][4] = prevShape[i][1];
	            	prevShape[i][5] = prevShape[i][2];
	        	}
	        	else if(i == numStem - 1) {  //last stem
	        		prevShape[i][0] = prevShape[i-1][0] + offx;
	        		prevShape[i][1] = prevShape[i-1][1] + offy;
	        		prevShape[i][2] = prevShape[i-1][2] + offz;
	        		prevShape[i][3] = prevShape[i][0];
	        		prevShape[i][4] = prevShape[i][1];
	            	prevShape[i][5] = prevShape[i][2];
	        	}
	        	else {   //middle stem
	        		prevShape[i][0] = prevShape[i-1][0] + offx;
	        		prevShape[i][1] = prevShape[i-1][1] + offy;
	        		prevShape[i][2] = prevShape[i-1][2] + offz;
	        		prevShape[i][3] = prevShape[i-1][3] + offx + this.stemWidth;
	        		prevShape[i][4] = prevShape[i-1][4] + offy + this.stemWidth;
	            	prevShape[i][5] = prevShape[i-1][5] + offz + this.stemWidth;
	        	}
	        }
        }
  
        //quad strip必須先指定下方兩點(左下 -> 右下), 再指定上方兩點(左上 -> 右上), 該面才會朝向玩家
    	//跟quad不同 (右下 -> 右上 -> 左上 -> 左下)
        //畫出正面
        tess.startDrawing(GL11.GL_QUAD_STRIP);
        tess.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
        tess.setBrightness(240);
        for(int i = numStem - 1; i >= 0; i--) {
        	tess.addVertex(prevShape[i][0],prevShape[i][1],prevShape[i][2]);
        	tess.addVertex(prevShape[i][3],prevShape[i][4],prevShape[i][5]);
        }
        tess.draw();
        
        //畫出反面
        tess.startDrawing(GL11.GL_QUAD_STRIP);
        tess.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
        tess.setBrightness(240);
        for(int i = numStem - 1; i >= 0; i--) {
        	tess.addVertex(prevShape[i][3],prevShape[i][4],prevShape[i][5]);
        	tess.addVertex(prevShape[i][0],prevShape[i][1],prevShape[i][2]);
        }
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
        this.setPosition(this.posX, this.posY, this.posZ);

        if(this.particleAge++ > this.particleMaxAge) {
            this.setDead();
        }
        
        //change position
        switch(this.particleType) {
        case 3:   //yamato cannon charging out
	        //particle position
			float[] partPos2 = ParticleHelper.rotateXZByAxis(this.host.width * 2F, 0F, (((EntityLivingBase)host).renderYawOffset % 360) * Values.N.RAD_MUL, 1F);
	    	
	        this.posX = this.host.posX + partPos2[1];
	    	this.posY = this.host.posY + host.height * 0.6D;
	        this.posZ = this.host.posZ + partPos2[0];
	        break;
        }
        
        //change color
        switch(this.particleType) {
        case 4:   //railgun
        	if(this.particleMaxAge - this.particleAge < 6 ) {
        		this.particleAlpha = (this.particleMaxAge - this.particleAge) * 0.15F + 0.2F;
        	}
        	
        	this.particleGreen = 0.6F + rand.nextFloat() * 0.6F;
        	this.particleRed = this.particleGreen - 0.3F;
        	break;
        case 1:   //yamato cannon charge lightning
        case 2:   //yamato cannon charging in
        case 3:   //yamato cannon charging out
        default:  //yamato cannon beam lightning
        	if(this.particleMaxAge - this.particleAge < 6 ) {
        		this.particleAlpha = (this.particleMaxAge - this.particleAge) * 0.15F + 0.2F;
        	}
        	
        	this.particleGreen = 0.4F + rand.nextFloat() * 0.75F;
        	this.particleBlue = 0.1F + this.particleGreen;
        	break;
        }
        
    }
    
    
}

