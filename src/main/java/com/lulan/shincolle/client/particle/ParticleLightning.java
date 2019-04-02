package com.lulan.shincolle.client.particle;

import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipFloating;
import com.lulan.shincolle.utility.CalcHelper;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;


/** NO TEXTURE LIGHTNING PARTICLE
 * 
 *  shape: stem with increase wide & same Y length
 * 
 *  parms: world, host, scale, type
 */
@SideOnly(Side.CLIENT)
public class ParticleLightning extends Particle
{

	private int particleType;		//0:red white lightning
	private Entity host;
	private int numStem;			//lightning length
	private double[][] prevShape;	//prev lightning shape
	private float scaleXZ, scaleY;
	
	
    public ParticleLightning(World world, Entity entity, float scale, int type)
    {
        super(world, 0D, 0D, 0D);
        this.setSize(0F, 0F);
        this.host = entity;
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleType = type;
        this.canCollide = false;	//can clip = false

        switch (type)
        {
        default:
        	this.particleRed = 1F;
            this.particleGreen = 0.4F + this.rand.nextFloat() * 0.3F;
            this.particleBlue = 0.4F + this.rand.nextFloat() * 0.3F;
            this.particleAlpha = 1F;
            this.particleMaxAge = 20;
            this.numStem = 4;
            this.scaleXZ = 0.01F;
            this.scaleY = 0.12F;
            this.posY = host.posY + 1.5D;
            
            //calc particle position for MountHbH
        	float randx = rand.nextFloat() + 0.1F;
        	float[] newPos = CalcHelper.rotateXZByAxis(0.8F+rand.nextFloat()*0.2F, randx, ((EntityLivingBase)host).renderYawOffset * -0.01745F, 1F);

        	if (this.host != null)
            {
            	this.posX = this.host.posX + newPos[0];
            	this.posY = this.host.posY + 1.53D + randx * 0.25D;
                this.posZ = this.host.posZ + newPos[1];
            }
        break;
        }
        
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.prevShape = new double[numStem][6];	//prev lightning shape
    }

    @Override
    public void renderParticle(BufferBuilder render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {
    	GlStateManager.pushMatrix();
    	GlStateManager.depthMask(true);
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
    	GlStateManager.disableTexture2D();	//NO texture
    	
        float px = (float)(this.prevPosX + (this.posX - this.prevPosX) * ptick - interpPosX);
        float py = (float)(this.prevPosY + (this.posY - this.prevPosY) * ptick - interpPosY);
        float pz = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * ptick - interpPosZ);

        //create lightning shape
    	float offx = 0F;
        float offz = 0F;
        float offy = 0F;
        
        //越後面的step, random range越大 (閃電到後面分支分散)
        for (int i = 0; i < numStem; i++)
        {
        	//越後面的枝幹分散範圍越大
            offx = (rand.nextFloat() - 0.5F) * 0.1F * (i + 1);
        	offz = (rand.nextFloat() - 0.5F) * 0.1F * (i + 1);
        	offy = rand.nextFloat() * 0.2F + 0.5F;
        	
        	//起始y高度 (y往下長)
        	if (i == 0)
        	{
        		prevShape[i][1] = py + cosPitch * scaleY;
        		prevShape[i][4] = prevShape[i][1];
        	}
        	else
        	{
        		prevShape[i][1] = py + cosPitch * scaleY - i * scaleY;
        		prevShape[i][4] = prevShape[i][1];
        	}
        	
        	//從後面的step開始存: x1, y1, z1, x2, y2, z2
        	prevShape[i][0] = px + offx + cosYaw * scaleXZ;
        	prevShape[i][2] = pz + offz + sinYaw * scaleXZ;
        	prevShape[i][3] = px + offx - cosYaw * scaleXZ;
        	prevShape[i][5] = pz + offz - sinYaw * scaleXZ;
        }
            
        //start
        render.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        
    	//quad strip必須先指定下方兩點(左下 -> 右下), 再指定上方兩點(左上 -> 右上), 該面才會朝向玩家
    	//跟quad不同 (右下 -> 右上 -> 左上 -> 左下)
        for (int i = numStem - 1; i >= 0; i--)
        {
        	render.pos(prevShape[i][0],prevShape[i][1],prevShape[i][2]).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        	render.pos(prevShape[i][3],prevShape[i][4],prevShape[i][5]).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).endVertex();
        }
        
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

        if (this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
        }
        
        //type 0: HarbourHime Mount
        if (this.particleType == 0 && host != null)
        {
        	float randx = rand.nextFloat() + 0.1F;
        	float[] newPos = CalcHelper.rotateXZByAxis(0.8F+rand.nextFloat()*0.2F, randx, ((EntityLivingBase)host).renderYawOffset * -0.01745F, 1F);

        	//x=1 y=1.8, x=0.7 y=1.72, x=0.5 y=1.65, x=0.3 y=1.6, x=0.1 y=1.55
            if (this.host != null)
            {
            	this.posX = this.host.posX + newPos[0];
            	this.posY = this.host.posY + 1.76D + randx * 0.25D;
                this.posZ = this.host.posZ + newPos[1];
            }
            
            if (((IShipFloating)host).getShipDepth() > 0D)
            {
            	this.posY -= 0.08D;
            }
            
            if (((IShipEmotion)host).getIsSitting())
            {
            	this.posY -= 0.23D;
            }
        }
    }
    
    
}