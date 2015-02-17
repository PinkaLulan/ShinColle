package com.lulan.shincolle.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.inventory.ContainerSmallShipyard;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.GUIs;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSmallShipyard extends GuiContainer {

	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.TEXTURES_GUI+"GuiSmallShipyard.png");
	private TileEntitySmallShipyard tile;
	private int xClick, yClick, xMouse, yMouse;
	private String errorMsg;
	
	public GuiSmallShipyard(InventoryPlayer par1, TileEntitySmallShipyard par2) {
		super(new ContainerSmallShipyard(par1, par2));
		tile = par2;
		
		this.xSize = 176;
		this.ySize = 164;
	}
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		xMouse = mouseX;
		yMouse = mouseY;
	}
	
	//draw tooltip
	private void handleHoveringText() {		
		//畫出fuel存量 (8,19,22,84)
		if(xMouse > 9+guiLeft && xMouse < 17+guiLeft && yMouse > 23+guiTop && yMouse < 49+guiTop) {
			List list = new ArrayList();
			String strFuel = String.valueOf(tile.remainedPower);
			int strLen = this.fontRendererObj.getStringWidth(strFuel) / 2;
			list.add(strFuel);
			this.drawHoveringText(list, 4-strLen, 40, this.fontRendererObj);
		}	
	}
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//取得gui顯示名稱
		String name = I18n.format("container.shincolle:SmallShipyard");
		String time = this.tile.getBuildTimeString();
		
		//畫出字串 parm: string, x, y, color, (是否dropShadow)
		//畫出該方塊名稱, 位置: x=gui寬度的一半扣掉字串長度一半, y=6, 顏色為4210752
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		//畫出倒數時間
		this.fontRendererObj.drawString(time, 71 - this.fontRendererObj.getStringWidth(time) / 2, 51, 4210752);
		//畫出提示訊息
		if(tile.goalPower <= 0) {
			errorMsg = I18n.format("gui.shincolle:nomaterial");
			this.fontRendererObj.drawString(errorMsg, 80 - this.fontRendererObj.getStringWidth(errorMsg) / 2, 61, 16724787);
		}
		else if(!tile.hasRemainedPower()) {
			errorMsg = I18n.format("gui.shincolle:nofuel");
			this.fontRendererObj.drawString(errorMsg, 80 - this.fontRendererObj.getStringWidth(errorMsg) / 2, 61, 16724787);
		}
		
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
        if(tile.remainedPower > 0) {
            scaleBar = tile.getPowerRemainingScaled(31);	//彩色進度條長度31	
            drawTexturedModalRect(guiLeft+10, guiTop+48-scaleBar, 176, 47-scaleBar, 12, scaleBar);
        }
        
        //畫出type選擇框
        if(tile.buildType == 1) {
        	drawTexturedModalRect(guiLeft+123, guiTop+17, 176, 47, 18, 18);
        }
        else if(tile.buildType == 2) {
        	drawTexturedModalRect(guiLeft+143, guiTop+17, 176, 47, 18, 18);
        }
	
	}
	
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int mouseKey) {
        super.mouseClicked(posX, posY, mouseKey);
            
        //get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        
        //match all pages
        int buttonValue = this.tile.buildType;
        switch(GuiHelper.getButton(GUIs.SMALLSHIPYARD, 0, xClick, yClick)) {
        case 0:	
        	if(buttonValue == 1) {
        		buttonValue = 0;	//原本點ship 又點一次 則歸0
        	}
        	else {
        		buttonValue = 1;	//原本點其他按鈕, 則設成ship
        	}
        	LogHelper.info("DEBUG : GUI click: build small ship: ship "+buttonValue);
        	CommonProxy.channel.sendToServer(new C2SGUIPackets(this.tile, AttrID.B_Shipyard_Type, buttonValue, 0));
        	break;
        case 1:
        	if(buttonValue == 2) {
        		buttonValue = 0;	//原本點equip 又點一次 則歸0
        	}
        	else {
        		buttonValue = 2;	//原本點其他按鈕, 則設成equip
        	}
        	LogHelper.info("DEBUG : GUI click: build small ship: equip "+buttonValue);
        	CommonProxy.channel.sendToServer(new C2SGUIPackets(this.tile, AttrID.B_Shipyard_Type, buttonValue, 0));
        	break;
        }
	}

}
