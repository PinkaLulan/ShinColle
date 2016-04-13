package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHeavyCruiserNe extends BasicShipRenderer {
	
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY +
															"EntityHeavyCruiserNe.png");
	
	public RenderHeavyCruiserNe(ModelBase par1, float par2) {
		super(par1, par2);
		leashWidthMod = 0.5D;
		leashOffsetRideSit = 1.1D;
		leashOffsetRide = 1.1D;
		leashOffsetSit = 1.1D;
		leashOffsetStand = 0.85D;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	

}

