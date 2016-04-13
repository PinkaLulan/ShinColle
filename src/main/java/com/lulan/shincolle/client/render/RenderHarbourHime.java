package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHarbourHime extends BasicShipRenderer {
	
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY +
															"EntityHarbourHime.png");
	
	public RenderHarbourHime(ModelBase par1, float par2) {
		super(par1, par2);
		leashWidthMod = 0D;
		leashOffsetRideSit = 2.1D;
		leashOffsetRide = 1.5D;
		leashOffsetSit = 1.1D;
		leashOffsetStand = 0.15D;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	

}






