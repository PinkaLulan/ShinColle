package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCarrierWo extends BasicShipRenderer {
	
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY +
															"EntityCarrierWo.png");
	
	public RenderCarrierWo(ModelBase par1, float par2) {
		super(par1, par2);
		leashOffsetRideSit = 0.2D;
		leashOffsetRide = 0.2D;
		leashOffsetSit = 0.2D;
		leashOffsetStand = 0.15D;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	

}

