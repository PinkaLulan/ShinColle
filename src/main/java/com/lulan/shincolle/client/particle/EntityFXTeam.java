package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


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
public class EntityFXTeam extends EntityFX {

	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_PARTICLE+"ParticleTeam.png");
	private int particleType;	//0:green 1:cyan 2:red 3:yellow
	private double height;
	private float particleAlphaA, particleAlphaC;  //arrow alpha, circle alpha
	private Entity host;
	
	
	//mark at entity
    public EntityFXTeam(World world, Entity host, float scale, int type) {
        super(world, host.posX, host.posY, host.posZ, 0.0D, 0.0D, 0.0D);  
        this.host = host;
        this.height = host.height;
        this.motionX = 0D;
        this.motionY = 0D;
        this.motionZ = 0D;
        this.particleScale = scale;
        this.particleAlphaA = 1F;
        this.particleAlphaC = 0.8F;
        this.noClip = true;
        this.particleType = type;
        
        switch(type) {
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
        }
    }
    
    //mark at block
    public EntityFXTeam(World world, float scale, int type, double x, double y, double z) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        this.motionX = 0D;
        this.motionY = 0D;
        this.motionZ = 0D;
        this.height = 1.5D;
        this.particleScale = scale;
        this.particleAlphaA = 1F;
        this.particleAlphaC = 0.5F;
        this.noClip = true;
        this.particleType = type;
        this.setPosition(x, y, z);
        
        switch(type) {
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
        }
    }

    @Override
	public void renderParticle(Tessellator tess, float ticks, float par3, float par4, float par5, float par6, float par7) {
		GL11.glPushMatrix();
		//使用自帶的貼圖檔
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		float xmin = 0F;
		float xmax = 1F;
		float y1min = 0F;	//箭頭圖案
		float y1max = 0.5F;
		float y2min = 0.5F;	//圈圈圖案
		float y2max = 1F;
		float halfScale = particleScale * 0.5F;
		
		//particle是以玩家視野來render, 因此座標要扣掉interpPos轉換為玩家視野座標
//		double f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)ticks - interpPosX);
//		double f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)ticks - interpPosY + this.height + 1.3D);
//		double f12b = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)ticks - interpPosY + 0.3D);
//		double f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)ticks - interpPosZ);
 
		double f11 = this.posX - interpPosX;
		double f12 = this.posY - interpPosY + this.height + 1.3D;
		double f12b = this.posY - interpPosY + 0.3D;
		double f13 = this.posZ - interpPosZ;
		
        //start tess
        tess.startDrawingQuads();
        tess.setBrightness(240);
        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaA);
        //畫出箭頭
        //X跟Z位置不加頭部轉動偏移, 只有Y軸會偏向玩家方向
        tess.addVertexWithUV(f11 - par3 * particleScale, f12 - par4 * particleScale * 2.0F, f13 - par5 * particleScale, xmax, y1max);
        tess.addVertexWithUV(f11 - par3 * particleScale, f12 + par4 * particleScale * 2.0F, f13 - par5 * particleScale, xmax, y1min);
        tess.addVertexWithUV(f11 + par3 * particleScale, f12 + par4 * particleScale * 2.0F, f13 + par5 * particleScale, xmin, y1min);
        tess.addVertexWithUV(f11 + par3 * particleScale, f12 - par4 * particleScale * 2.0F, f13 + par5 * particleScale, xmin, y1max);

        halfScale = particleScale * 3F;
        
        tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlphaC);
        //畫出圈圈(朝上)
        tess.addVertexWithUV(f11 + halfScale, f12b, f13 + halfScale, xmax, y2max);
        tess.addVertexWithUV(f11 + halfScale, f12b, f13 - halfScale, xmax, y2min);
        tess.addVertexWithUV(f11 - halfScale, f12b, f13 - halfScale, xmin, y2min);
        tess.addVertexWithUV(f11 - halfScale, f12b, f13 + halfScale, xmin, y2max);

        //畫出圈圈(朝下)
        tess.addVertexWithUV(f11 + halfScale, f12b, f13 - halfScale, xmax, y2max);
        tess.addVertexWithUV(f11 + halfScale, f12b, f13 + halfScale, xmax, y2min);
        tess.addVertexWithUV(f11 - halfScale, f12b, f13 + halfScale, xmin, y2min);
        tess.addVertexWithUV(f11 - halfScale, f12b, f13 - halfScale, xmin, y2max);
        
        //stop tess for restore texture
        tess.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
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
    	//check host position
    	if(host != null) {
    		switch(this.particleType) {
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
    	else {
    		if(particleType < 4) {
    			this.setDead();
    		}
    	}
    	
    	//special effect
    	switch(this.particleType) {
    	case 4:
    	case 5:
    	case 6:
    		//fade out effect
        	if(particleAge > 10) {
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
    	}
    	
        if(this.particleAge++ > this.particleMaxAge) {
            this.setDead();
        }
    }
    
    
}

