package com.lulan.shincolle.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.proxy.ClientProxy;

public class RenderPointerItem implements IItemRenderer {
	
	private int tick = 0;
	
	
	public RenderPointerItem() {}
	
	/** 設定哪些使用情況需要使用此item renderer
	 * 
	 *  ENTITY                丟在地上時
	 *  EQUIPPED              拿在手上, 第三人稱顯示時
	 *  EQUIPPED_FIRST_PERSON 拿在手上, 第一人稱顯示時
	 *  INVENTORY             在物品欄GUI顯示時
	 *  FIRST_PERSON_MAP      拿在手上, 第一人稱顯示且手已經畫出
	 */
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
		case EQUIPPED_FIRST_PERSON: //held in hand & 第一人稱視角
			//caress head mode時顯示為player hand
			return item.getItemDamage() > 2;
		default:
			return false;
	    }
	}

	/** 設定要套用哪些特殊效果
	 * 
	 *  ENTITY_ROTATION 是否套用旋轉, 主要用在block上
	 *  ENTITY_BOBBING  套用移動時會上下晃動的效果
	 *  EQUIPPED_BLOCK  true為3D畫法, false則為2D貼圖, 主要用在手上顯示時
	 *  BLOCK_3D        方塊的3D畫法
	 *  INVENTORY_BLOCK 物品欄中是否用3D block畫法
	 */
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (type) {
//		case ENTITY:				//on ground
//	        return (helper == ItemRendererHelper.ENTITY_BOBBING ||
//	                helper == ItemRendererHelper.ENTITY_ROTATION ||
//	                helper == ItemRendererHelper.BLOCK_3D);
//		case EQUIPPED:  			//held in hand
//	        return (helper == ItemRendererHelper.BLOCK_3D ||
//	                helper == ItemRendererHelper.EQUIPPED_BLOCK);
//		case EQUIPPED_FIRST_PERSON: //held in hand 第一人稱視角
//	        return (helper == ItemRendererHelper.EQUIPPED_BLOCK);
//		case INVENTORY:				//inventory GUI
//	        return (helper == ItemRendererHelper.INVENTORY_BLOCK);
//		case FIRST_PERSON_MAP:		//第一人稱視角 & 手已經畫完
		default:
	        return false;
	    }
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		//render player hand
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		Render render = RenderManager.instance.getEntityRenderObject(player);
        RenderPlayer renderplayer = (RenderPlayer) render;
        GameSettings keySet = ClientProxy.getGameSetting();  //get pressed key
        int meta = item.getItemDamage();
        
		GL11.glPushMatrix();
		//get player skin
		Minecraft.getMinecraft().getTextureManager().bindTexture(player.getLocationSkin());
        
		GL11.glTranslatef(0.5F, -0.1F, -0.6F);
		GL11.glRotatef(-30F, 0F, 0F, 1F);
		GL11.glRotatef(20F, 0F, 1F, 0F);
		GL11.glRotatef(15F, 1F, 0F, 0F);
		
		//calc caress animation
		if(keySet.keyBindUseItem.getIsKeyPressed()) {
			switch(meta) {
			case 3:  //往下, 上下撫摸
				GL11.glTranslatef(2F, 4F, 0.0F);
				GL11.glScalef(2F, 2F, 2F);
				GL11.glRotatef(MathHelper.cos(++tick * 0.125F) * -20F - 60F, 0F, 0F, 1F);
				break;
			case 4:  //側摸
			    GL11.glRotatef(70F, 0F, 1F, 0F);
				GL11.glRotatef(-20F, 0F, 0F, 1F);
				GL11.glTranslatef(-6F, 16F, 10F);
				GL11.glScalef(12F, 12F, 12F);
				GL11.glRotatef(MathHelper.cos(++tick * 0.04F) * -15F + 20F, 1F, 0F, 0F);
				break;
			case 5:  //平摸
				GL11.glTranslatef(11.5F, 12.5F, 2.5F);
				GL11.glScalef(9F, 9F, 9F);
				GL11.glRotatef(MathHelper.cos(++tick * 0.0625F) * -15F - 20F, 1F, 1F, 0F);
				break;
			}
		}
		
        //render hand
        renderplayer.renderFirstPersonArm(player);
        
        GL11.glPopMatrix();
		
	}

	
}
