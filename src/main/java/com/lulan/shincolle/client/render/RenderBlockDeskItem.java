package com.lulan.shincolle.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockDeskItem implements IItemRenderer  {
	
	TileEntitySpecialRenderer tesr;
	private static TileEntity entity;
	
	public RenderBlockDeskItem(TileEntitySpecialRenderer tesr, TileEntity entity) {
		this.tesr = tesr;
		this.entity = entity;
		this.entity.blockMetadata = -2;	//distinguish itemblock(-2)/block(0~7)/non-init state(-1)
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		//type:ENTITY=丟在地上, EQUIPPED=拿在手上, EQUIPPED_FIRST_PERSON=拿在手上第一人稱
		//     INVENTORY=在物品欄中, FIRST_PERSON_MAP=地圖類型物品
		if(type == IItemRenderer.ItemRenderType.ENTITY) {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		}
		if(type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslatef(0F, 0F, 0F);
		}
		//畫出model
		this.tesr.renderTileEntityAt(this.entity, 0D, 0D, 0D, 0F);

	}

}

