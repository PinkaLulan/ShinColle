package com.lulan.shincolle.client.render;

import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;


@SideOnly(Side.CLIENT)
public class RenderDestroyerShimakazeBoss extends RenderLiving {
	
	//貼圖檔路徑
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityDestroyerShimakaze.png");

	public RenderDestroyerShimakazeBoss(ModelBase par1, float par2) {
		super(par1, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	
	//顯示boss血條
	@Override
    protected void preRenderCallback(EntityLivingBase entity, float f1) {
		BossStatus.setBossStatus((IBossDisplayData) entity, true);
	}

}



