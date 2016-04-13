package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSubmU511 extends BasicShipRenderer {
	
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY +
																			"EntitySubmU511.png");
	
	public RenderSubmU511(ModelBase par1, float par2) {
		super(par1, par2);
		leashOffsetRideSit = 1D;
		leashOffsetRide = 1D;
		leashOffsetSit = 1D;
		leashOffsetStand = 0.35D;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	

}



