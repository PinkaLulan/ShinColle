package com.lulan.shincolle.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.block.BlockSmallShipyard;
import com.lulan.shincolle.client.model.ModelSmallShipyard;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSmallShipyard extends TileEntitySpecialRenderer {

	//貼圖檔路徑
	private static final ResourceLocation textureOn = new ResourceLocation(Reference.TEXTURES_BLOCKS+"BlockSmallShipyardOn.png");
	private static final ResourceLocation textureOff = new ResourceLocation(Reference.TEXTURES_BLOCKS+"BlockSmallShipyardOff.png");
		
	private final ModelSmallShipyard model;
	
	public RenderSmallShipyard() {
		this.model = new ModelSmallShipyard();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float scale) {

		int meta = tileentity.getBlockMetadata();
		
		ResourceLocation textures = (meta>3 ? textureOn : textureOff);
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
			//榜定model貼圖
			Minecraft.getMinecraft().renderEngine.bindTexture(textures);	//client side only
			//this.bindTexture(textures);
			GL11.glPushMatrix();			
				this.model.renderModel(0.0625F);  //避免renderModel裡面有平移旋轉  必須push pop一次以免不正常位移
			GL11.glPopMatrix();
		GL11.glPopMatrix();
		
	}

}
