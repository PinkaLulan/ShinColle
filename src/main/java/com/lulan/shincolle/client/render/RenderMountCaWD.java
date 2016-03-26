package com.lulan.shincolle.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.client.model.ModelMountCaWD;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMountCaWD extends RenderLiving {
	
	//貼圖檔路徑
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityMountCaWD.png");
	private ModelMountCaWD model = null;
	
	public RenderMountCaWD(ModelMountCaWD par1, float par2) {
		super(par1, par2);
		this.model = par1;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	
	//render item
	@Override
	protected void renderEquippedItems(EntityLivingBase host, float swing) {
//		//恢復正常顏色
//		GL11.glColor3f(1.0F, 1.0F, 1.0F);
//		GL11.glPushMatrix();
//		GL11.glScalef(0.5F, 0.5F, 0.5F);
//		this.model.HeadS01.postRender(1.00F);
//		GL11.glScalef(0.5F, 0.5F, 0.5F);
//		GL11.glPopMatrix();
	}
	
	//額外修改貼圖, 改顏色大小等動作
	@Override
	protected int shouldRenderPass(EntityLivingBase entity, int pass, float scale) {
//		if(pass != 0) {
			return -1;
//		}
//		else {
//			GL11.glPushMatrix();
			
//			GL11.glScalef(0.5F, 0.5F, 0.5F);
//			this.model.HeadS01.postRender(0.0125F);
//			GL11.glScalef(0.5F, 0.5F, 0.5F);
//			GL11.glPopMatrix();
//			return 1;
//		}
    }

}



