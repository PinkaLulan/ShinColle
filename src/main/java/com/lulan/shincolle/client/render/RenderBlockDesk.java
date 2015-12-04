package com.lulan.shincolle.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.model.ModelBlockDesk;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockDesk extends TileEntitySpecialRenderer {

	//∂Kπœ¿…∏ÙÆ|
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.TEXTURES_BLOCKS+"BlockDesk.png");

	private ModelBlockDesk body;
	
	public RenderBlockDesk() {
		this.body = new ModelBlockDesk();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float scale) {
		int meta = tileentity.getBlockMetadata();
		float angle = 0;	//0=north 90=east 180=south -90=west
		
		switch(meta) {
		case 1:
		case 5:	//case 1,5 = block face east
			angle = 90;
			break;
		case 2:
		case 6:	//case 2,6 = block face south
			angle = 180;
			break;
		case 3:
		case 7:	//case 3,7 = block face west
			angle = -90;
			break;		
		}
		
		GL11.glPushMatrix();
			GL11.glTranslatef((float)x+0.5F, (float)y+1.5F, (float)z+0.5F);
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(angle, 0F, 1F, 0F);
			//draw desk body
			Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
			this.body.render(0.0625F);
		GL11.glPopMatrix();
		
	}

}