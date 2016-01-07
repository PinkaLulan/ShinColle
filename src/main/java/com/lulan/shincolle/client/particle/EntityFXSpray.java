package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;


/**SPRAY PARTICLE
 * 從cloud修改來, 用於液體中移動的特效
 */
@SideOnly(Side.CLIENT)
public class EntityFXSpray extends EntityFX {
	
    private int ptype;
    private float pScale;
    private double speedLimit; 
    
    
    public EntityFXSpray(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int type) {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.1D;
        this.motionZ *= 0.1D;
        this.motionY *= 0.1D;
        this.motionX += motionX;
        this.motionZ += motionZ;
        this.motionY += motionY;
        
        this.ptype = type;
        
        switch(this.ptype) {
        case 1:   //white
        	this.speedLimit = 0.25D;
        	this.particleRed = 1F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 1F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        case 2:   //cyan
        	this.speedLimit = 0.3D;
        	this.particleRed = 0.5F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 1F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        case 3:   //green
        	this.speedLimit = 0.3D;
        	this.particleRed = 0.2F;
            this.particleGreen = 1F;
            this.particleBlue = 0.6F;
            this.particleAlpha = 0.7F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        case 4:   //0.8A red
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 0F;
            this.particleBlue = 0F;
            this.particleAlpha = 0.8F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        case 5:   //0.5A white
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 0.5F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        case 6:   //0.5A LARGE white
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 0.5F;
            this.particleScale *= 1.5F;
            this.pScale = 15F;
            this.particleMaxAge = 40;
            this.noClip = false;
            this.motionY = 0D;
        	break;
        case 7:   //light cyan
        	this.speedLimit = 0.3D;
        	this.particleRed = 0.7F;
            this.particleGreen = 0.94F;
            this.particleBlue = 1F;
            this.particleAlpha = 1F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        case 8:   //yellow
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 1F;
            this.particleBlue = 0.6F;
            this.particleAlpha = 1F;
            this.particleScale *= 3F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 20;
            this.noClip = false;
        	break;
        case 9:   //orange
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 0.35F;
            this.particleBlue = 0F;
            this.particleAlpha = 0.8F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        case 10:   //transparent cyan
        	this.speedLimit = 0.3D;
        	this.particleRed = 0.5F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 0.2F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        case 11:   //transparent red
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 0F;
            this.particleBlue = 0F;
            this.particleAlpha = 0.2F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        default:  //default = type 0 = 1A red
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 0F;
            this.particleBlue = 0F;
            this.particleAlpha = 1F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.noClip = false;
        	break;
        }
        
        //speed limit
        if(this.motionX > this.speedLimit) this.motionX = this.speedLimit;
        if(this.motionY > this.speedLimit) this.motionY = this.speedLimit;
        if(this.motionZ > this.speedLimit) this.motionZ = this.speedLimit;
        if(this.motionX < -this.speedLimit) this.motionX = -this.speedLimit;
        if(this.motionY < -this.speedLimit) this.motionY = -this.speedLimit;
        if(this.motionZ < -this.speedLimit) this.motionZ = -this.speedLimit;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_) {
        return 240;
    }

    @Override
	public void renderParticle(Tessellator tess, float ticks, float par3, float par4, float par5, float par6, float par7) {
        float f6 = (this.particleAge + ticks) / this.particleMaxAge * 32.0F;

        if(f6 < 0.0F) {
            f6 = 0.0F;
        }

        if(f6 > 1.0F) {
            f6 = 1.0F;
        }
//        LogHelper.info("DEBUG : par "+par5+" "+par7);
        this.particleScale = this.pScale * f6;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);	//防止water block蓋過particle
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        super.renderParticle(tess, ticks, par3, par4, par5, par6, par7);
//        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
//        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate() {
    	//this particle is CLIENT ONLY
    	if(this.worldObj.isRemote) {
    		this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;

            if(this.particleAge++ > this.particleMaxAge) {
                this.setDead();
                return;
            }
            
        	this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge); 
            this.motionX *= 0.96D;
            this.motionY *= 0.96D;
            this.motionZ *= 0.96D;

            if(this.onGround) {
                this.motionX *= 0.7D;
                this.motionZ *= 0.7D;
            }
            
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
    	}  
    }
}