package com.lulan.shincolle.client.render.block;

import com.lulan.shincolle.client.model.ModelLargeShipyard;
import com.lulan.shincolle.client.model.ModelVortex;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLargeShipyard extends TileEntitySpecialRenderer<BasicTileEntity>
{

	//貼圖檔路徑
	private static final ResourceLocation TEXTURE_BASE = new ResourceLocation(Reference.TEXTURES_BLOCKS+"BlockLargeShipyard.png");
	private static final ResourceLocation VORTEX_OFF = new ResourceLocation(Reference.TEXTURES_BLOCKS+"ModelVortex.png");
	private static final ResourceLocation VORTEX_ON = new ResourceLocation(Reference.TEXTURES_BLOCKS+"ModelVortexOn.png");

	private final ModelLargeShipyard model_base;
	private final ModelVortex model_vortex;
	
	
	public RenderLargeShipyard()
	{
		this.model_base = new ModelLargeShipyard();
		this.model_vortex = new ModelVortex();
		
	}
	
	@Override
	public void renderTileEntityFast(BasicTileEntity tile, double x, double y, double z, float partick, int deststage, float partial, BufferBuilder buffer)
	{
		//get blockstate: get real meta if tile exist in world, or get meta = -1
		int meta = tile.getRenderMetadata();
		if (meta <= 0) return;
		
		EntityPlayer player  = Minecraft.getMinecraft().player;
		BlockPos pos = tile.getPos();
		double distX = pos.getX() + 0.5D - player.posX;
		double distY = pos.getY() - 0.75D - player.posY;
		double distZ = pos.getZ() + 0.5D - player.posZ;
		float f1 = MathHelper.sqrt(distX * distX + distZ * distZ);
        float pitch = (float) (Math.atan2(f1, distY));
        float yaw = (float) (Math.atan2(distX, distZ));
        float angle = (-player.ticksExisted - partick) % 360F;
        
        //vortex ON, speed up rotation
        if (meta > 1)
        {
        	angle *= 5;
        }
        
//        //依照x,z軸正負向修正角度(轉180)
//        if (distZ > 0)
//        {
//        	pitch -= Math.PI * 0.5F;
//        }
//        else
//        {
        	pitch += Math.PI * 0.5F;
//        }

		//render base
		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_BASE);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.5F, (float) y - 0.2F, (float) z + 0.5F);
		GlStateManager.rotate(180F, 0F, 0F, 1F);
		GlStateManager.scale(1F, 1.2F, 1F);
		this.model_base.render(0.0625F);
		GlStateManager.popMatrix();
		
        //render vortex
		Minecraft.getMinecraft().renderEngine.bindTexture(meta > 1 ? VORTEX_ON : VORTEX_OFF);
		GlStateManager.pushMatrix();
		GlStateManager.depthMask(ConfigHandler.vortexDepth);
		GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		GlStateManager.rotate(yaw * 57.2957F, 0F, 1F, 0F);
		GlStateManager.rotate(pitch * 57.2957F, 1F, 0F, 0F);
		GlStateManager.rotate(angle, 0F, 0F, 1F);
		this.model_vortex.render(0.03125F);
		GlStateManager.depthMask(true);
		GlStateManager.popMatrix();
	}
	
	
}