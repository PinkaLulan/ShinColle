package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**TEAM CIRCLE PARTICLE
 * 顯示team圈選的目標, 點擊的目標, 能控制的目標
 * type:
 * 0: 綠色, 目前所有在隊伍中的至少都會顯示綠色圈選圖
 * 1: 青色, pointer為single mode時會控制的目標
 * 2: 紅色, pointer為group mode會控制的目標
 * 3: 黃色, pointer為formation mode會控制的目標
 * 4: 綠色, moving target (show 20 ticks), alpha fade out
 * 5: 紅色, attack target (show 20 ticks), alpha fade out
 */
@SideOnly(Side.CLIENT)
public class ParticleTeam extends Particle
{

	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_PARTICLE+"ParticleTeam.png");
	private int particleType;	//0:green 1:cyan 2:red 3:yellow
	private double height;
	private float particleAlphaA, particleAlphaC;  //arrow alpha, circle alpha
	private Entity host;
	
	
	//mark at entity
    public ParticleTeam(World world, Entity host, float scale, int type)
    {
        super(world, host.posX, host.posY, host.posZ, 0.0D, 0.0D, 0.0D);  
        this.setSize(0F, 0F);
        this.host = host;
        this.height = host.height;
        this.motionX = 0D;
        this.motionY = 0D;
        this.motionZ = 0D;
        this.particleScale = scale;
        this.particleAlphaA = 1F;
        this.particleAlphaC = 0.8F;
        this.particleType = type;
        this.field_190017_n = false;	//can clip = false
        
        switch (type)
        {
        default:	//green, normal mode
        	this.particleRed = 0F;
        	this.particleGreen = 1F;
        	this.particleBlue = 0F;
        	this.particleMaxAge = 31;
        break;
        case 1:		//cyan, single mode
        	this.particleRed = 0F;
        	this.particleGreen = 1F;
        	this.particleBlue = 1F;
        	this.particleMaxAge = 31;
        break;
        case 2:		//red, group mode
        	this.particleRed = 1F;
        	this.particleGreen = 0F;
        	this.particleBlue = 1F;
        	this.particleMaxAge = 31;
        break;
        case 3:		//yellow, formation mode
        	this.particleRed = 1F;
        	this.particleGreen = 0.9F;
        	this.particleBlue = 0F;
        	this.particleMaxAge = 31;
        break;
        case 4:		//green, moving target
        	this.particleRed = 0F;
        	this.particleGreen = 1F;
        	this.particleBlue = 0F;
        	this.particleMaxAge = 30;
        break;
        case 5:		//red, attack target
        	this.particleRed = 1F;
        	this.particleGreen = 0F;
        	this.particleBlue = 0F;
        	this.particleMaxAge = 30;
        break;
        case 6:		//white, guard target
        	this.particleRed = 1F;
        	this.particleGreen = 1F;
        	this.particleBlue = 1F;
        	this.particleMaxAge = 30;
        break;
        case 7:		//translucent green, friendly target
        	this.particleRed = 0F;
        	this.particleGreen = 1F;
        	this.particleBlue = 0F;
        	this.particleMaxAge = 31;
        	this.particleAlphaA = 0F;
            this.particleAlphaC = 0.35F;
        break;
        }//end switch
    }
    
    //mark at block
    public ParticleTeam(World world, float scale, int type, double x, double y, double z)
    {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.setSize(0F, 0F);
        this.setPosition(x, y, z);
        this.motionX = 0D;
        this.motionY = 0D;
        this.motionZ = 0D;
        this.height = 1.5D;
        this.particleScale = scale;
        this.particleAlphaA = 1F;
        this.particleAlphaC = 0.5F;
        this.particleType = type;
        this.field_190017_n = false;	//can clip = false
        
        switch (type)
        {
        default:	//green, normal mode
        case 4:		//green, moving target
        	this.particleRed = 0F;
        	this.particleGreen = 1F;
        	this.particleBlue = 0F;
        	this.particleMaxAge = 30;
        break;
        case 5:		//red, attack target
        	this.particleRed = 1F;
        	this.particleGreen = 0F;
        	this.particleBlue = 0F;
        	this.particleMaxAge = 30;
        break;
        case 6:		//white, guard target
        	this.particleRed = 1F;
        	this.particleGreen = 1F;
        	this.particleBlue = 1F;
        	this.particleMaxAge = 30;
        break;
        case 8:		//waypoint
        	this.particleRed = 1F;
        	this.particleGreen = 0F;
        	this.particleBlue = 0F;
        	this.particleMaxAge = 31;
        	this.particleAlphaA = 0.8F;
            this.particleAlphaC = 0.9F;
        break;
        case 9:		//waypoint
        	this.particleRed = 1F;
        	this.particleGreen = 0F;
        	this.particleBlue = 0F;
        	this.particleMaxAge = 31;
        	this.particleAlphaA = 0F;
            this.particleAlphaC = 0.9F;
        break;
        }//end switch
    }

    @Override
    public void renderParticle(VertexBuffer render, Entity entity, float ptick, float cosYaw, float cosPitch, float sinYaw, float sinYawsinPitch, float cosYawsinPitch)
    {
    	Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
    	
    	GlStateManager.pushMatrix();
    	GlStateManager.depthMask(true);
    	GlStateManager.enableBlend();
    	GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
    	GlStateManager.disableLighting();
    	
		float xmin = 0F;
		float xmax = 1F;
		float y1min = 0F;	//箭頭圖案
		float y1max = 0.5F;
		float y2min = 0.5F;	//圈圈圖案
		float y2max = 1F;
		float halfScale = particleScale * 0.5F;
		
		//particle是以玩家視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
		double f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)ptick - interpPosX);
		double f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)ptick - interpPosY + this.height + 1.3D);
		double f12b = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)ptick - interpPosY + 0.3D);
		double f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)ptick - interpPosZ);

        //start
		render.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		
        //畫出箭頭
        //X跟Z位置不加頭部轉動偏移, 只有Y軸會偏向玩家方向
        render.pos(f11 - cosYaw * particleScale, f12 - cosPitch * particleScale * 2.0F, f13 - sinYaw * particleScale).tex(xmax, y1max).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaA).endVertex();
        render.pos(f11 - cosYaw * particleScale, f12 + cosPitch * particleScale * 2.0F, f13 - sinYaw * particleScale).tex(xmax, y1min).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaA).endVertex();
        render.pos(f11 + cosYaw * particleScale, f12 + cosPitch * particleScale * 2.0F, f13 + sinYaw * particleScale).tex(xmin, y1min).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaA).endVertex();
        render.pos(f11 + cosYaw * particleScale, f12 - cosPitch * particleScale * 2.0F, f13 + sinYaw * particleScale).tex(xmin, y1max).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaA).endVertex();

        halfScale = particleScale * 3F;
        
        //畫出圈圈(朝上)
        render.pos(f11 + halfScale, f12b, f13 + halfScale).tex(xmax, y2max).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaC).endVertex();
        render.pos(f11 + halfScale, f12b, f13 - halfScale).tex(xmax, y2min).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaC).endVertex();
        render.pos(f11 - halfScale, f12b, f13 - halfScale).tex(xmin, y2min).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaC).endVertex();
        render.pos(f11 - halfScale, f12b, f13 + halfScale).tex(xmin, y2max).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaC).endVertex();

        //畫出圈圈(朝下)
        render.pos(f11 + halfScale, f12b, f13 - halfScale).tex(xmax, y2max).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaC).endVertex();
        render.pos(f11 + halfScale, f12b, f13 + halfScale).tex(xmax, y2min).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaC).endVertex();
        render.pos(f11 - halfScale, f12b, f13 + halfScale).tex(xmin, y2min).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaC).endVertex();
        render.pos(f11 - halfScale, f12b, f13 - halfScale).tex(xmin, y2max).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaC).endVertex();
        
        //draw
        Tessellator.getInstance().draw();
        
    	GlStateManager.enableLighting();
    	GlStateManager.disableBlend();
    	GlStateManager.depthMask(false);
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
    	//check host position
    	if (host != null)
    	{
    		switch (this.particleType)
    		{
    		case 7:
        		//set interpolation position
        		this.prevPosX = this.posX;
                this.prevPosY = this.posY;
                this.prevPosZ = this.posZ;
    			this.setPosition(host.posX, host.posY, host.posZ);
        	break;
    		default:
    			//set interpolation position
        		this.prevPosX = this.posX;
                this.prevPosY = this.posY;
                this.prevPosZ = this.posZ;
    			this.setPosition(host.posX, host.posY+0.02D, host.posZ);
    		break;
    		}
    	}
    	else
    	{
    		if (particleType < 4)
    		{
    			this.setExpired();
    		}
    	}
    	
    	//special effect
    	switch (this.particleType)
    	{
    	case 4:
    	case 5:
    	case 6:
    		//fade out effect
        	if (particleAge > 10)
        	{
        		this.particleAlphaA = 1F - ((particleAge - 10F) / 20F);
        		this.particleAlphaC = this.particleAlphaA * 0.5F;
        	}
    	break;
    	case 9:  //waypoint
    		this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
			this.setPosition(this.posX, this.posY + this.particleAge * 0.002D, this.posZ);
			
			this.particleAlphaC = 0.9F - this.particleAge * 0.027F;
		break;
    	}//end switch
    	
        if (this.particleAge++ > this.particleMaxAge)
        {
            this.setExpired();
        }
    }
    
    
}

