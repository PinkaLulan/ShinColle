package com.lulan.shincolle.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.gui.inventory.ContainerVolCore;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntityVolCore;

public class GuiVolCore extends GuiContainer {

	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.TEXTURES_GUI+"GuiVolCore.png");
	private TileEntityVolCore tile;
	private int xClick, yClick, xMouse, yMouse;
	private float tickGUI;
	private String conName;
	
	
	public GuiVolCore(InventoryPlayer par1, TileEntityVolCore par2) {
		super(new ContainerVolCore(par1, par2));
		tile = par2;
		tickGUI = 0F;
		xSize = 176;
		ySize = 166;
		
		//string
		conName = I18n.format("tile.shincolle:BlockVolCore.name");
		
	}
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		xMouse = mouseX;
		yMouse = mouseY;
		tickGUI += 0.125F;
		
	}
	
	//draw tooltip
	private void handleHoveringText() {		
		//畫出fuel存量 (8,19,22,84)
		if(xMouse > 36+guiLeft && xMouse < 52+guiLeft && yMouse > 27+guiTop && yMouse < 61+guiTop) {
			List list = new ArrayList();
			String strFuel = String.valueOf(tile.getPowerRemained());
			int strLen = this.fontRendererObj.getStringWidth(strFuel) / 2;
			list.add(strFuel);
			this.drawHoveringText(list, 10-strLen, 52, this.fontRendererObj);
		}	
	}
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//畫出字串 parm: string, x, y, color, (是否dropShadow)
		//畫出該方塊名稱, 位置: x=gui寬度的一半扣掉字串長度一半, y=6, 顏色為4210752
		this.fontRendererObj.drawString(conName, this.xSize / 2 - this.fontRendererObj.getStringWidth(conName) / 2, 6, 4210752);
		
		//畫出tooltip
		handleHoveringText();
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);	//RGBA
        Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture); //GUI圖檔
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);	//GUI大小設定
       
        //畫出fuel存量條
        int scaleBar; 
        if(tile.getPowerRemained() > 0) {
            scaleBar = tile.getPowerRemainingScaled(31);	//彩色進度條長度31	
            drawTexturedModalRect(guiLeft+38, guiTop+59-scaleBar, 0, 197-scaleBar, 12, scaleBar);
        }
	}
	
//	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
//	@Override
//	protected void mouseClicked(int posX, int posY, int mouseKey) {
//        super.mouseClicked(posX, posY, mouseKey);
//            
//        //get click position
//        xClick = posX - this.guiLeft;
//        yClick = posY - this.guiTop;
//        
//	}

}

