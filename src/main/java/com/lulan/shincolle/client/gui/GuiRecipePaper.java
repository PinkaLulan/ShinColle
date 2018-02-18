package com.lulan.shincolle.client.gui;

import com.lulan.shincolle.client.gui.inventory.ContainerRecipePaper;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiRecipePaper extends GuiContainer
{

	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.TEXTURES_GUI+"GuiRecipePaper.png");
	
	
	public GuiRecipePaper(EntityPlayer player, ItemStack stack)
	{
		super(new ContainerRecipePaper(player.world, player.inventory, stack));
		
		xSize = 176;
		ySize = 166;
	}
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3)
	{
		//reset color
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GlStateManager.disableBlend();
	}
	
	
}