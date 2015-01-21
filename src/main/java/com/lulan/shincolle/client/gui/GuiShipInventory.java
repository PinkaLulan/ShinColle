package com.lulan.shincolle.client.gui;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.inventory.ContainerShipInventory;
import com.lulan.shincolle.inventory.ShipInventory;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiShipInventory extends GuiContainer {

	private BasicEntityShip entity;
	private InventoryPlayer player;
	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.TEXTURES_GUI+"GuiShipInventory.png");
	
	public GuiShipInventory(InventoryPlayer invPlayer, BasicEntityShip entity1) {
		super(new ContainerShipInventory(invPlayer, entity1));
		this.entity = entity1;
		this.player = invPlayer;
		this.xSize = 176;
		this.ySize = 237;
	}
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//取得gui顯示名稱
		String titlename = entity.getCustomNameTag();	//get type name from nbt
		//畫出字串 parm: string, x, y, color, (是否dropShadow)
		//title: draw entity name
		this.fontRendererObj.drawString(titlename, this.xSize / 2 - this.fontRendererObj.getStringWidth(titlename) / 2, 6, 16711680);

	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);	//RGBA
        Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture); //GUI圖檔
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);	//GUI大小設定
	
	}


}
