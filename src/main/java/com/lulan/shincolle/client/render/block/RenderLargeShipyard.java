package com.lulan.shincolle.client.render.block;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.model.ModelLargeShipyard;
import com.lulan.shincolle.entity.renderentity.BasicRenderEntity;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderLargeShipyard extends Render {

	//貼圖檔路徑
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_BLOCKS+"BlockLargeShipyard.png");

	private ModelBase model;
			
	public RenderLargeShipyard() {
		this.model = new ModelLargeShipyard();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TEXTURE;
	}
	
	@Override
	public boolean isStaticEntity() {
		return ConfigHandler.staticMode;
	}
	
	//傳入entity的都轉成abyssmissile
    @Override
	public void doRender(Entity entity, double offsetX, double offsetY, double offsetZ, float p_76986_8_, float p_76986_9_) {
        this.doRender((BasicRenderEntity)entity, offsetX, offsetY, offsetZ, p_76986_8_, p_76986_9_);
    }

	public void doRender(BasicRenderEntity entity, double offsetX, double offsetY, double offsetZ, float f3, float f4) {
		EntityPlayer player  = Minecraft.getMinecraft().thePlayer;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
		GL11.glPushMatrix();
			GL11.glTranslatef((float)offsetX+0.375F, (float)offsetY+0.8F, (float)offsetZ+0.375F);
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			GL11.glScalef(1F, 1.2F, 1F);
			this.model.render(entity, 0F, 0F, 0F, 0F, 0F, 0.0625F);
		GL11.glPopMatrix();
		
	}

}
