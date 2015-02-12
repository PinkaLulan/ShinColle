package com.lulan.shincolle.client.render;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.model.ModelLargeShipyard;
import com.lulan.shincolle.client.model.ModelVortex;
import com.lulan.shincolle.entity.renderentity.BasicRenderEntity;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderVortex extends Render {

	//貼圖檔路徑
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_ENTITY+"ModelVortex.png");

	private final ModelVortex model;
			
	public RenderVortex() {
		this.model = new ModelVortex();
	}
	
	//傳入entity的都轉成abyssmissile
    public void doRender(Entity entity, double offsetX, double offsetY, double offsetZ, float p_76986_8_, float p_76986_9_) {
        this.doRender((BasicRenderEntity)entity, offsetX, offsetY, offsetZ, p_76986_8_, p_76986_9_);
    }
	
	public void doRender(BasicRenderEntity entity, double offsetX, double offsetY, double offsetZ, float f3, float f4) {
		EntityPlayer player  = Minecraft.getMinecraft().thePlayer;
		double distX = entity.posX - player.posX;
		double distY = entity.posY - player.posY;
		double distZ = entity.posZ - player.posZ;

		float f1 = MathHelper.sqrt_double(distX*distX + distZ*distZ);
        float pitch = (float)(Math.atan2(distY, (double)f1));
        float yaw = (float)(Math.atan2(distX, distZ));    
        
        //依照x,z軸正負向修正角度(轉180)
        if(distZ > 0) {
        	pitch -= (Math.PI / 2F);
        }
        else {
        	pitch += (Math.PI / 2F);
        }
        
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		GL11.glPushMatrix();
			GL11.glTranslatef((float)offsetX, (float)offsetY+0.5F, (float)offsetZ);
//			GL11.glRotatef(pitch * 57.2957F, 1F, 0F, 0F);
			GL11.glRotatef(yaw * 57.2957F, 0F, 1F, 0F);
			GL11.glRotatef(-entity.ticksExisted%360F, 0F, 0F, 1F);
			GL11.glScalef(0.5F, 0.5F, 0.5F);

			this.model.render(entity, 0F, 0F, 0F, yaw, pitch, 0.0625F);

		GL11.glPopMatrix();
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TEXTURE;
	}	

	
}
