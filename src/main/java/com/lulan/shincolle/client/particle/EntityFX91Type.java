package com.lulan.shincolle.client.particle;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;


/**91TYPE PARTICLE
 * 攻擊文字特效
 */
@SideOnly(Side.CLIENT)
public class EntityFX91Type extends EntityFX {

	private static final ResourceLocation TEXTURE1 = new ResourceLocation(Reference.TEXTURES_PARTICLE+"EntityFX91Type.png");
	private int partAge;
	private int fadeTime = 16;
	private int middTime = 60;
	private int totalTime = 2 * fadeTime + middTime;
	private float minu, maxu ,x , y, z, scale, alpha;
	private float fadeCoef = 1F / fadeTime;
	
    public EntityFX91Type(World world, double posX, double posY, double posZ, float scale) {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);  
        this.motionX = 0D;
        this.motionZ = 0D;
        this.motionY = 0D;
        this.particleScale = scale;
        this.particleMaxAge = 136;
        this.noClip = true;
    }

    //par3,4,5為玩家視角Yaw參數, 用於調整x,y,z值對應到玩家水平視角的水平跟垂直移動
    //par6,7為玩家視角Pitch參數, 用於調整x,z值對應到玩家垂直視角的水平移動
    @Override
	public void renderParticle(Tessellator tess, float ticks, float par3, float par4, float par5, float par6, float par7) {
    	
    	//stop last tess for bind texture
//    	tess.draw();
    	
		GL11.glPushMatrix();
		//先畫出文字
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
//		GL11.glEnable(GL11.GL_DEPTH_TEST);	//DEPTH TEST開啟後才能使用glDepthFunc
//		GL11.glDepthFunc(GL11.GL_ALWAYS);
        
        //start tess
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE1);
        tess.startDrawingQuads();

        /**draw text
         * age: 0  ~ 15: color fade in (RGB=0% -> RGB=100%)
         *      16 ~ 55: no change
         *      56 ~ 70: alpha fade out (ALPHA=100% -> ALPHA=0%)
         */

        for(int i = 0; i < 6; ++i) {
        	partAge = this.particleAge - i * 8;

        	if(partAge > -1 && partAge < totalTime) {
        		minu = 1F / 6F * i;
        		maxu = 1F / 6F * (i + 1);
        		x = (float)(this.posX - interpPosX - (i - 2.5F) * this.particleScale * 2F * par3);
                y = (float)(this.posY - interpPosY);
                z = (float)(this.posZ - interpPosZ - (i - 2.5F) * this.particleScale * 2F * par5);
        	
                if(partAge < fadeTime) {					//0~10: color fade in
                	scale = this.particleScale * (3F - 2F * fadeCoef * partAge);
                	alpha = fadeCoef * partAge;
                }
                else if(partAge >= (fadeTime + middTime)) {	//71~80: alpha fade out
                	partAge -= (fadeTime + middTime);
                	scale = this.particleScale * (1F + 2F * fadeCoef * partAge);
                	alpha = 1F - fadeCoef * partAge;
                }
                else {										//other
                	scale = this.particleScale;
                	alpha = 1F;
                }
                
                tess.setColorRGBA_F(1F, 1F, 1F, alpha);
                tess.setBrightness(240);
            	addQuad(tess, scale, x, y, z, par3, par4, par5, minu, maxu, 0F, 1F);
        	}
        }
  
        //stop tess
        tess.draw();
        
//        GL11.glDepthFunc(GL11.GL_LEQUAL);
//		GL11.glDisable(GL11.GL_DEPTH_TEST);	//DEPTH TEST關閉
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
//		GL11.glDepthMask(false);
		GL11.glPopMatrix();
		
//		//start tess for other particle
//		tess.startDrawingQuads();
    }

    //add quad with size
	private void addQuad(Tessellator tess, float scale, float x, float y, float z, float offx, float offy, float offz, float minu, float maxu, float minv, float maxv) {
        float offsetX = offx * scale;
        float offsetY = offy * scale;
        float offsetZ = offz * scale;
        
		tess.addVertexWithUV(x - offsetX, y - offsetY, z - offsetZ, maxu, maxv);
        tess.addVertexWithUV(x - offsetX, y + offsetY, z - offsetZ, maxu, minv);
        tess.addVertexWithUV(x + offsetX, y + offsetY, z + offsetZ, minu, minv);
        tess.addVertexWithUV(x + offsetX, y - offsetY, z + offsetZ, minu, maxv);	
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

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY *= 0.9D;
    }
}

