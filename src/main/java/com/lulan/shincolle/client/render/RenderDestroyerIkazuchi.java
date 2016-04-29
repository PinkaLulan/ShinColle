package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDestroyerIkazuchi extends BasicShipRenderer {
	
	//貼圖檔路徑
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY+
															"EntityDestroyerIkazuchi.png");

	public RenderDestroyerIkazuchi(ModelBase par1, float par2) {
		super(par1, par2);
		leashWidthMod = 0.1D;
		leashOffsetRideSit = 0.8D;
		leashOffsetRide = 0.8D;
		leashOffsetSit = 0.8D;
		leashOffsetStand = 0.3D;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}

	
}








