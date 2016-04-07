package com.lulan.shincolle.client.render.special;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/** Beam Entity Render
 *  no render
 */
@SideOnly(Side.CLIENT)
public class RenderProjectileBeam extends Render {

	
	public RenderProjectileBeam() {}
	
	//傳入entity的都轉成abyssmissile
    @Override
	public void doRender(Entity entity, double offsetX, double offsetY, double offsetZ, float p_76986_8_, float p_76986_9_) {
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}	

	
}

