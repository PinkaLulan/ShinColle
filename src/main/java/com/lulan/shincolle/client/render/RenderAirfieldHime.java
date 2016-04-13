package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAirfieldHime extends BasicShipRenderer {
	
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY+"EntityAirfieldHime.png");
	
	public RenderAirfieldHime(ModelBase par1, float par2) {
		super(par1, par2);
		leashWidthMod = 0.1D;
		leashOffsetRideSit = 1.5D;
		leashOffsetRide = 1.2D;
		leashOffsetSit = 0.7D;
		leashOffsetStand = 0.2D;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	

}





