package com.lulan.shincolle.client.render.block;

import com.lulan.shincolle.client.model.ModelSmallShipyard;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.BasicTileEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSmallShipyard extends TileEntitySpecialRenderer<BasicTileEntity>
{

	//貼圖檔路徑
	private static final ResourceLocation textureOn = new ResourceLocation(Reference.TEXTURES_BLOCKS+"BlockSmallShipyardOn.png");
	private static final ResourceLocation textureOff = new ResourceLocation(Reference.TEXTURES_BLOCKS+"BlockSmallShipyardOff.png");
		
	private final ModelSmallShipyard model;

	
	public RenderSmallShipyard()
	{
		this.model = new ModelSmallShipyard();
	}
	
	@Override
	public void renderTileEntityFast(BasicTileEntity tile, double x, double y, double z, float partick, int deststage, float partial, BufferBuilder buffer)
	{
		//get blockstate: get real meta if tile exist in world, or get meta = -1
		int meta = tile.getRenderMetadata();
		
		ResourceLocation textures = (meta <= -1 || meta > 7 ? textureOn : textureOff);
		float angle = 0;	//0=north 90=east 180=south -90=west
		
		switch (meta)
		{
		case 5:
		case 13:	//case 1,5 = block face east
			angle = 90;
			break;
		case 3:
		case 11:	//case 2,6 = block face south
			angle = 180;
			break;
		case 4:
		case 12:	//case 3,7 = block face west
			angle = -90;
			break;		
		}
		
		this.bindTexture(textures);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x+0.5F, (float)y+1.5F, (float)z+0.5F);
		GlStateManager.rotate(180F, 0F, 0F, 1F);
		GlStateManager.rotate(angle, 0F, 1F, 0F);
		this.model.renderModel(0.0625F);  //避免renderModel裡面有平移旋轉  必須push pop一次以免不正常位移
		GlStateManager.popMatrix();
		
	}


}
