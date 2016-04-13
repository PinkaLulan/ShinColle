package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMountBaH extends BasicShipRenderer {
	
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY +
															"EntityMountBaH.png");
	
	public RenderMountBaH(ModelBase par1, float par2) {
		super(par1, par2);
		leashWidthMod = 0.4D;
		leashOffsetRideSit = 0.5D;
		leashOffsetRide = 0.5D;
		leashOffsetSit = 0.5D;
		leashOffsetStand = 0.5D;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	

}

