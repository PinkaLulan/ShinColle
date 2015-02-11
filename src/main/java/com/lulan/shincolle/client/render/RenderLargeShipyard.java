package com.lulan.shincolle.client.render;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.model.ModelLargeShipyard;
import com.lulan.shincolle.entity.EntityAbyssMissile;
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
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderLargeShipyard extends Render {

	//貼圖檔路徑
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_BLOCKS+"BlockLargeShipyard.png");

	private final ModelLargeShipyard model;
			
	public RenderLargeShipyard() {
		this.model = new ModelLargeShipyard();
	}
	
	//傳入entity的都轉成abyssmissile
    public void doRender(Entity entity, double offsetX, double offsetY, double offsetZ, float p_76986_8_, float p_76986_9_) {
        this.doRender((BasicRenderEntity)entity, offsetX, offsetY, offsetZ, p_76986_8_, p_76986_9_);
    }

	public void doRender(BasicRenderEntity entity, double offsetX, double offsetY, double offsetZ, float f3, float f4) {
//		this.bindEntityTexture(entity);	
		EntityPlayer player  = Minecraft.getMinecraft().thePlayer;
		GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
			GL11.glTranslatef((float)offsetX, (float)offsetY+0.5F, (float)offsetZ);
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
//			GL11.glRotatef(angle, 0F, 1F, 0F);
//			GL11.glPushMatrix();			
			this.model.render(entity, 0F, 0F, 0F, 0F, 0F, 0.0625F);
//			GL11.glPopMatrix();
		GL11.glPopMatrix();
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TEXTURE;
	}
	
	@Override
	public boolean isStaticEntity() {
		return true;
	}

}
