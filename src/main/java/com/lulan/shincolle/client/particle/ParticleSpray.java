package com.lulan.shincolle.client.particle;

import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
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
        super(world, posX, posY, posZ);

        this.ptype = type;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

        //color, speed, life setting
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
            this.particleMaxAge = 50;
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
        case 16:   //XZ moving white, motionY = scale
        	this.motionY = 0D;
        	this.speedLimit = 0.25D;
        	this.particleRed = 1F;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 1F;
            this.particleScale = (float)motionY * 3F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
            this.posX += (rand.nextDouble() - 0.5D) * motionY;
            this.posY += (rand.nextDouble() - 0.5D) * motionY * 0.15D;
            this.posZ += (rand.nextDouble() - 0.5D) * motionY;
            this.motionX *= 1.5D;
            this.motionZ *= 1.5D;
        break;
        default:  //default = type 0 = 1A red
        	this.speedLimit = 0.3D;
        	this.particleRed = 1F;
            this.particleGreen = 0F;
            this.particleBlue = 0F;
            this.particleAlpha = 0.7F;
            this.particleScale *= 1.5F;
            this.pScale = 15F;
            this.particleMaxAge = 40;
            this.motionY = 0D;
        break;
        }
        
        //speed limit
        double motsq = this.motionX*this.motionX+this.motionY*this.motionY+this.motionZ*this.motionZ;
        
        if (motsq > this.speedLimit * this.speedLimit)
        {
        	motsq = MathHelper.sqrt(motsq);
        	this.motionX = this.speedLimit * this.motionX / motsq;
        	this.motionY = this.speedLimit * this.motionY / motsq;
        	this.motionZ = this.speedLimit * this.motionZ / motsq;
        }
        
    	//reset pos
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
    }
    
    /**type:
     *   0:missile spray, data: 0:vel0, 1:index
     * 
     */
    public ParticleSpray(Entity host, int type, double[] data)
    {
        super(host.world, host.posX, host.posY, host.posZ);
        this.ptype = type;
        
        //color, speed, life setting
        switch (this.ptype)
        {
        case 1:   //transparent cyan
        {
            this.posX = host.posX + host.motionX * 2D - host.motionX * 1.5D * data[1];
            this.posY = host.posY + host.motionY * 2D - host.motionY * 1.5D * data[1] + 0.5D;
            this.posZ = host.posZ + host.motionZ * 2D - host.motionZ*1.5D*data[1];
            this.motionX = -host.motionX * 0.1D;
            this.motionY = -host.motionY * 0.1D;
            this.motionZ = -host.motionZ * 0.1D;
        	this.speedLimit = 2D;
        	
        	float velred = 1.4F - (float)data[0];
        	if (velred > 1F) velred = 1F;
        	else if (velred < 0F) velred = 0F;
        	
        	this.particleRed = velred;
            this.particleGreen = 1F;
            this.particleBlue = 1F;
            this.particleAlpha = 0.75F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
        }
        break;
        case 2:   //transparent red
        {
            this.posX = host.posX + host.motionX * 2D - host.motionX * 1.5D * data[1];
            this.posY = host.posY + host.motionY * 2D - host.motionY * 1.5D * data[1] + 0.5D;
            this.posZ = host.posZ + host.motionZ * 2D - host.motionZ*1.5D*data[1];
            this.motionX = -host.motionX * 0.1D;
            this.motionY = -host.motionY * 0.1D;
            this.motionZ = -host.motionZ * 0.1D;
        	this.speedLimit = 2D;
        	
        	float velgb = ((float)data[0] - 0.2F) * 3.333F;
        	if (velgb > 1F) velgb = 1F;
        	else if (velgb < 0F) velgb = 0F;
        	
        	this.particleRed = 1F;
            this.particleGreen = velgb;
            this.particleBlue = velgb;
            this.particleAlpha = 0.75F;
            this.particleScale *= 1.5F;
            this.pScale = this.particleScale;
            this.particleMaxAge = 40;
        }
        break;
        }
        
        //speed limit
        double motsq = this.motionX*this.motionX+this.motionY*this.motionY+this.motionZ*this.motionZ;
        
        if (motsq > this.speedLimit * this.speedLimit)
        {
        	motsq = MathHelper.sqrt(motsq);
        	this.motionX = this.speedLimit * this.motionX / motsq;
        	this.motionY = this.speedLimit * this.motionY / motsq;
        	this.motionZ = this.speedLimit * this.motionZ / motsq;
        }
        
    	//reset pos
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
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
    	if (this.particleAge == 1) return;
    	
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
        this.setPosition(this.posX, this.posY, this.posZ);

        //set dead if max age
        if (this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
            return;
        }
        
        switch (this.ptype)
        {
        case 13:
        case 14:
        {
        	this.setParticleTextureIndex(7 - this.particleAge * 4 / this.particleMaxAge);
        	
        	this.posX += this.motionX;
        	this.posY += this.motionY;
        	this.posZ += this.motionZ;
        }
    	break;
    	default:
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
		break;
        }
    }
    
    
}