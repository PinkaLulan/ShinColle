package com.lulan.shincolle.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMountCaWD extends BasicShipRenderer {
	
	private static final ResourceLocation mobTextures = new ResourceLocation(Reference.TEXTURES_ENTITY +
															"EntityMountCaWD.png");
	
	public RenderMountCaWD(ModelBase par1, float par2) {
		super(par1, par2);
		leashWidthMod = 0.3D;
		leashOffsetRideSit = 0.4D;
		leashOffsetRide = 0.4D;
		leashOffsetSit = 0.4D;
		leashOffsetStand = 0.4D;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return mobTextures;
	}
	

}



