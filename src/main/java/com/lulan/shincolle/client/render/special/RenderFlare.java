package com.lulan.shincolle.client.render.special;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.entity.renderentity.EntityRenderFlare;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderFlare extends Render {
    

    public RenderFlare() {}
    
    @Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

    public void doRender(EntityRenderFlare entity, double offsetX, double offsetY, double offsetZ, float f1, float parTick) {

    }

    //傳入entity的都轉成abyssmissile
    @Override
	public void doRender(Entity entity, double offsetX, double offsetY, double offsetZ, float f1, float parTick) {
        this.doRender((EntityRenderFlare)entity, offsetX, offsetY, offsetZ, f1, parTick);
    }
    
    
}
