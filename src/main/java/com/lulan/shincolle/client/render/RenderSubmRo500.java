package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSubmRo500 extends BasicShipRenderer {
	
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY +
																			"EntitySubmRo500.png");
	
	public RenderSubmRo500(ModelBase par1, float par2) {
		super(par1, par2);
		leashOffsetRideSit = 0.9D;
		leashOffsetRide = 0.9D;
		leashOffsetSit = 0.9D;
		leashOffsetStand = 0.35D;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	

}




