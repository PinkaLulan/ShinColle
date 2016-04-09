package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


/**EMOTION PARTICLE
 * 
 * type:
 *   0: 汗 
 *   1: 小愛心
 *   2: 
 */
@SideOnly(Side.CLIENT)
public class EntityFXEmotion extends EntityFX {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_PARTICLE+"EntityFXEmotion.png");
	private int particleType, playTimes, fadeTick, fadeState, stayTick, stayTickCount, frameSize;
	private float playSpeed, playSpeedCount, particleIconX, particleIconY;
	private float[] spawnRange;
	
    public EntityFXEmotion(World world, double posX, double posY, double posZ, float height, float par1, int type) {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);  
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.noClip = true;
        this.particleType = type;
        this.particleScale = 0.3F;
        this.particleAlpha = 0F;
        this.playSpeed = 1F;
        this.playSpeedCount = 0F;
        this.stayTick = 10;
        this.stayTickCount = 0;
        this.fadeTick = 0;
        this.fadeState = 0;  //0:fade in, 1:normal, 2:fade out, 3:set dead
        this.frameSize = 1;
        
        float angle2;
        float[] newPos;
        
        //set icon position
        switch(this.particleType) {
        case 1:   //小愛心
        	this.particleIconX = 0.0625F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 4;
        	//no stay
        	this.stayTick = 0;
			break;
        case 2:   //噴汗
        	this.particleIconX = 0.0625F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 3;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 5;
        	//no stay
        	this.stayTick = 0;
        	break;
        case 3:   //問號
        	this.particleIconX = 0.125F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
			break;
        case 4:   //驚嘆號
        	this.particleIconX = 0.125F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	//long stay
        	this.stayTick = 20;
        	break;
        case 5:   //點點點
        	this.particleIconX = 0.1875F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//long stay
        	this.stayTick = 20;
        	//slow play
        	this.playSpeed = 0.5F;
			break;
        case 6:   //冒青筋
        	this.particleIconX = 0.1875F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 1;
        	//short fade in
        	this.fadeTick = 3;
        	break;
        case 7:   //音符
        	this.particleIconX = 0.25F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 15;
        	this.playTimes = 1;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//short stay
        	this.stayTick = 3;
        	//slow play
        	this.playSpeed = 0.7F;
        	break;
        case 8:   //cry
        	this.particleIconX = 0.3125F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 3;
        	//short fade in
        	this.fadeTick = 3;
        	//no stay
        	this.stayTick = 0;
        	//slow play
        	this.playSpeed = 0.5F;
        	break;
        case 9:   //流口水
        	this.particleIconX = 0.3125F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 2;
        	//short fade in
        	this.fadeTick = 3;
        	//no stay
        	this.stayTick = 1;
        	//slow play
        	this.playSpeed = 0.5F;
        	break;
        case 10:  //混亂
        	this.particleIconX = 0.375F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 7;
        	this.playTimes = 4;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//short stay
        	this.stayTick = 1;
        	break;
        case 11:  //尋找
        	this.particleIconX = 0.375F;
        	this.particleIconY = 0.5F;
        	this.particleMaxAge = 7;
        	this.playTimes = 2;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//short stay
        	this.stayTick = 3;
        	//slow play
        	this.playSpeed = 0.5F;
        	break;
        case 12:  //驚嚇
        	this.particleIconX = 0.4375F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 14;
        	this.playTimes = 1;
        	//cancel fade in
        	this.particleAlpha = 1F;
        	this.fadeState = 1;
        	this.fadeTick = 3;
        	//long stay
        	this.stayTick = 20;
        	//slow play
        	this.playSpeed = 0.75F;
        	//large frame
        	this.frameSize = 2;
        	break;
        default:  //汗
        	this.particleIconX = 0F;
        	this.particleIconY = 0F;
        	this.particleMaxAge = 15;
        	this.playTimes = 1;
			break;
        }
        
        //get player view angle
        float angle = ClientProxy.getClientPlayer().renderYawOffset;
        
        //set particle position
        switch(this.particleType) {
        case 2:  //right side -0.75 ~ -0.5
        	angle2 = angle % 360 * Values.N.RAD_MUL;
			newPos = ParticleHelper.rotateXZByAxis(0F, this.rand.nextFloat() * 0.25F - 0.75F, angle2, 1F);
			this.posX = posX + newPos[1];
	    	this.posY = posY + this.rand.nextDouble() * height * 0.25D + height * 1.7D;
	    	this.posZ = posZ + newPos[0];
        	break;
    	default: //left side 0.6 ~ 0.85
    		angle2 = angle % 360 * Values.N.RAD_MUL;
			newPos = ParticleHelper.rotateXZByAxis(0F, this.rand.nextFloat() * 0.25F + 0.6F, angle2, 1F);
	        this.posX = posX + newPos[1];
	    	this.posY = posY + this.rand.nextDouble() * height * 0.6D + height;
	    	this.posZ = posZ + newPos[0];
    		break;
        }
    }

    @Override
	public void renderParticle(Tessellator tess, float ticks, float par3, float par4, float par5, float par6, float par7) {
    	GL11.glPushMatrix();
		
		//get texture
		if(particleType < 16) {
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		}
		else {
			
		}
		
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
//		GL11.glEnable(GL11.GL_DEPTH_TEST);	//DEPTH TEST開啟後才能使用glDepthFunc
//		GL11.glDepthFunc(GL11.GL_ALWAYS);
		
		int age = particleAge > particleMaxAge ? particleMaxAge : particleAge;

		float f6 = particleIconX;
		float f7 = f6 + 0.0625F;
		float f8 = particleIconY + age * 0.0625F;
		float f9 = f8 + 0.0625F * this.frameSize;
		
//		LogHelper.info("particle age: "+this.particleAge+" "+ticks);
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * ticks - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * ticks - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * ticks - interpPosZ);

        tess.startDrawingQuads();
        tess.setBrightness(240);
        tess.setColorRGBA_F(1F, 1F, 1F, this.particleAlpha);
        //X跟Z位置不加頭部轉動偏移, 只有Y軸會偏向玩家方向
        tess.addVertexWithUV(f11 - par3 * particleScale - par6 * particleScale, f12 - par4 * particleScale * frameSize, f13 - par5 * particleScale - par7 * particleScale, f7, f9);
        tess.addVertexWithUV(f11 - par3 * particleScale + par6 * particleScale, f12 + par4 * particleScale * frameSize, f13 - par5 * particleScale + par7 * particleScale, f7, f8);
        tess.addVertexWithUV(f11 + par3 * particleScale + par6 * particleScale, f12 + par4 * particleScale * frameSize, f13 + par5 * particleScale + par7 * particleScale, f6, f8);
        tess.addVertexWithUV(f11 + par3 * particleScale - par6 * particleScale, f12 - par4 * particleScale * frameSize, f13 + par5 * particleScale - par7 * particleScale, f6, f9);
        tess.draw();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
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
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        //fade state
        switch(this.fadeState) {
        case 0:   //fade in
        	this.fadeTick++;
        	this.particleAlpha = this.fadeTick * 0.2F;
        	
        	if(this.fadeTick > 5) this.fadeState = 1;
        	break;
        case 1:   //age++
        	this.playSpeedCount += this.playSpeed;
        	this.particleAge = this.frameSize * (int)this.playSpeedCount;
        	break;
        case 2:   //fade out
        	this.fadeTick--;
        	this.particleAlpha = this.fadeTick * 0.2F;
        	
        	if(this.fadeTick < 1) {
        		this.setDead();
        		return;
        	}
        	break;
    	default:
    		this.setDead();
    		return;
        }
        
        //stay at last frame
        if(this.particleAge >= particleMaxAge) {
    		this.particleAge = this.particleMaxAge;
    		
    		//count 10 ticks
    		if(this.stayTickCount > this.stayTick) {
    			this.particleAge = this.particleMaxAge + 1;  //change to next loop
    			this.stayTickCount = 0;
    		}
    		else {
    			this.stayTickCount += 1;
    		}
    	}

        //loop play
        if(this.particleAge > this.particleMaxAge) {
        	//loop times--
        	if(--this.playTimes <= 0) {
        		this.fadeState = 2;  //change to fade out
        	}
        	else {
        		this.particleAge = 0;
        		this.playSpeedCount = 0F;
        	}
        }
    }
    
    
}

