package com.lulan.shincolle.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**SPRAY PARTICLE
 * 從cloud修改來, 用於液體中移動的特效
 */
@SideOnly(Side.CLIENT)
public class ParticleSpray extends Particle
{
	
    private int ptype;
    private float pScale;
    private double speedLimit; 
    
    
    public ParticleSpray(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int type)
    {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        
        if (type < 13 || type == 15)
        {
        	this.motionX *= 0.1D;
            this.motionY *= 0.1D;
            this.motionZ *= 0.1D;
            this.motionX += motionX;
            this.motionY += motionY;
            this.motionZ += motionZ;
        }
        else
        {
        	this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
        }
        
        this.ptype = type;
        
        switch (this.ptype)
        {
        case 1:   //white
        	this.speedLimit = 0.25D;
        	this.particleRed = 1F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 1F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
        	break;
        case 2:   //cyan
        	this.speedLimit = 0.3D;
        	this.particleRed = 0.5F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 1F;
            this.particleScale *= 1.5F;
            this.pScale = (float) (this.particleScale * motionZ);
            this.particleMaxAge = 40;
            this.motionX = 0D;
            this.motionZ = 0D;
        	break;
        case 3:   //green
        	this.speedLimit = 0.3D;
        	this.particleRed = 0.2F;
            this.particleGreen = 1F;
            this.particleBlue = 0.6F;
            this.particleAlpha = 0.7F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 10;
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
        	break;
        case 12:   //transparent white
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 0.5F;
            this.particleScale *= 0.75F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 50;
        	break;
        case 13:   //next waypoint
        	this.speedLimit = 2D;
        	this.particleRed = 1F;
            this.particleGreen = 0F;
            this.particleBlue = 0F;
            this.particleAlpha = 0.5F;
            this.particleScale *= 3F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 100;
            this.setSize(0F, 0F);
        	break;
        case 14:   //paired chest
        	this.speedLimit = 2D;
        	this.particleRed = 0.5F;
            this.particleGreen = 0F;
            this.particleBlue = 0.5F;
            this.particleAlpha = 0.5F;
            this.particleScale *= 3F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 100;
            this.setSize(0F, 0F);
        	break;
        case 15:   //transparent cyan
        	this.speedLimit = 0.3D;
        	this.particleRed = 0.7F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 0.75F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
        	break;
        default:  //default = type 0 = 1A red
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 0F;
            this.particleBlue = 0F;
            this.particleAlpha = 1F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 10;
        	break;
        }
        
        //speed limit
        if (this.motionX > this.speedLimit) this.motionX = this.speedLimit;
        if (this.motionY > this.speedLimit) this.motionY = this.speedLimit;
        if (this.motionZ > this.speedLimit) this.motionZ = this.speedLimit;
        if (this.motionX < -this.speedLimit) this.motionX = -this.speedLimit;
        if (this.motionY < -this.speedLimit) this.motionY = -this.speedLimit;
        if (this.motionZ < -this.speedLimit) this.motionZ = -this.speedLimit;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float ptick)
    {
        return 240;
    }

    @Override
    public void renderParticle(VertexBuffer render, Entity entity, float ptick, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ)
    {
        float f6 = (this.particleAge + ptick) / this.particleMaxAge * 32F;

        if (f6 < 0F) f6 = 0F;
        if (f6 > 1F) f6 = 1F;
        this.particleScale = this.pScale * f6;
        
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        
        super.renderParticle(render, entity, ptick, rotX, rotZ, rotYZ, rotXY, rotXZ);
        
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate()
    {
    	//update pos
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        //set dead if max age
        if (this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
            return;
        }
        
        //waypoint indicator
        if (this.ptype >= 13 && this.ptype != 15)
        {
        	this.setParticleTextureIndex(7 - this.particleAge * 4 / this.particleMaxAge);
        	
        	this.posX += this.motionX;
        	this.posY += this.motionY;
        	this.posZ += this.motionZ;
        }
        //other spray
        else
        {
        	this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
            this.motionX *= 0.96D;
            this.motionY *= 0.96D;
            this.motionZ *= 0.96D;
            
            if (this.isCollided)
            {
                this.motionX *= 0.7D;
                this.motionZ *= 0.7D;
            }
            
            this.move(this.motionX, this.motionY, this.motionZ);
        }

    }
    
    
}