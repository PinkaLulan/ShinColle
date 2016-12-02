package com.lulan.shincolle.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.lulan.shincolle.client.gui.inventory.ContainerSmallShipyard;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

public class GuiSmallShipyard extends GuiContainer
{

	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.TEXTURES_GUI+"GuiSmallShipyard.png");
	private TileEntitySmallShipyard tile;
	private int xClick, yClick, xMouse, yMouse;
	private float tickGUI;
	private String errorMsg1, errorMsg2, conName;
	
	
	public GuiSmallShipyard(InventoryPlayer par1, TileEntitySmallShipyard par2)
	{
		super(new ContainerSmallShipyard(par1, par2));
		tile = par2;
		tickGUI = 0F;
		this.xSize = 176;
		this.ySize = 164;
		
		//string
		conName = I18n.format("tile.shincolle:BlockSmallShipyard.name");
		errorMsg1 = I18n.format("gui.shincolle:nomaterial");
		errorMsg2 = I18n.format("gui.shincolle:nofuel");
	}
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		xMouse = mouseX;
		yMouse = mouseY;
		tickGUI += 0.125F;
		
	}
	
	//draw tooltip
	private void handleHoveringText()
	{		
		//畫出fuel存量 (8,19,22,84)
		if (xMouse > 9 + guiLeft && xMouse < 23 + guiLeft && yMouse > 17 + guiTop && yMouse < 49 + guiTop)
		{
			List list = new ArrayList();
			String strFuel = String.valueOf(tile.getPowerRemained());
			int strLen = this.fontRendererObj.getStringWidth(strFuel) / 2;
			list.add(strFuel);
			this.drawHoveringText(list, 4-strLen, 40, this.fontRendererObj);
		}	
	}
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		//取得gui顯示名稱
		String time = this.tile.getBuildTimeString();
		
		//畫出字串 parm: string, x, y, color, (是否dropShadow)
		//畫出該方塊名稱, 位置: x=gui寬度的一半扣掉字串長度一半, y=6, 顏色為4210752
		this.fontRendererObj.drawString(conName, this.xSize / 2 - this.fontRendererObj.getStringWidth(conName) / 2, 6, 4210752);
		
		//畫出倒數時間
		this.fontRendererObj.drawString(time, 71 - this.fontRendererObj.getStringWidth(time) / 2, 51, 4210752);
		
		//畫出提示訊息
		if (tile.getPowerGoal() <= 0)
		{
			this.fontRendererObj.drawString(errorMsg1, 80 - this.fontRendererObj.getStringWidth(errorMsg1) / 2, 67, 16724787);
		}
		else if (!tile.hasRemainedPower())
		{
			this.fontRendererObj.drawString(errorMsg2, 80 - this.fontRendererObj.getStringWidth(errorMsg2) / 2, 67, 16724787);
		}
		
		//畫出tooltip
		handleHoveringText();
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3)
	{
		//reset color
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(guiTexture); //GUI圖檔
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);	//GUI大小設定
       
        //畫出fuel存量條
        int scaleBar; 
        if (tile.getPowerRemained() > 0)
        {
            scaleBar = tile.getPowerRemainingScaled(31);	//彩色進度條長度31	
            drawTexturedModalRect(guiLeft+10, guiTop+48-scaleBar, 176, 47-scaleBar, 12, scaleBar);
        }
        
        //畫出type選擇框
        switch (tile.getBuildType())
        {
        case ID.Build.SHIP:
        	drawTexturedModalRect(guiLeft+123, guiTop+17, 176, 47, 18, 18);
        	break;
        case ID.Build.SHIP_LOOP:
        	//draw animate
        	switch((int)tickGUI % 6)
        	{
        	default:
        	case 0:
        		drawTexturedModalRect(guiLeft+123, guiTop+17, 176, 65, 18, 18);
        		break;
        	case 1:
        		drawTexturedModalRect(guiLeft+123, guiTop+17, 176, 83, 18, 18);
        		break;
        	case 2:
        		drawTexturedModalRect(guiLeft+123, guiTop+17, 176, 101, 18, 18);
        		break;
        	case 3:
        		drawTexturedModalRect(guiLeft+123, guiTop+17, 176, 119, 18, 18);
        		break;
        	case 4:
        		drawTexturedModalRect(guiLeft+123, guiTop+17, 176, 137, 18, 18);
        		break;
        	case 5:
        		drawTexturedModalRect(guiLeft+123, guiTop+17, 176, 155, 18, 18);
        		break;
        	}
        	break;
        case ID.Build.EQUIP:
        	drawTexturedModalRect(guiLeft+143, guiTop+17, 176, 47, 18, 18);
        	break;
        case ID.Build.EQUIP_LOOP:
        	//draw animate
        	switch((int)tickGUI % 6)
        	{
        	default:
        	case 0:
        		drawTexturedModalRect(guiLeft+143, guiTop+17, 176, 65, 18, 18);
        		break;
        	case 1:
        		drawTexturedModalRect(guiLeft+143, guiTop+17, 176, 83, 18, 18);
        		break;
        	case 2:
        		drawTexturedModalRect(guiLeft+143, guiTop+17, 176, 101, 18, 18);
        		break;
        	case 3:
        		drawTexturedModalRect(guiLeft+143, guiTop+17, 176, 119, 18, 18);
        		break;
        	case 4:
        		drawTexturedModalRect(guiLeft+143, guiTop+17, 176, 137, 18, 18);
        		break;
        	case 5:
        		drawTexturedModalRect(guiLeft+143, guiTop+17, 176, 155, 18, 18);
        		break;
        	}
        	break;
        }
	}
	
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int mouseKey) throws IOException
	{
        super.mouseClicked(posX, posY, mouseKey);
            
        //get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        
        //match all pages
        int buttonValue = this.tile.getBuildType();
        
        switch (GuiHelper.getButton(ID.Gui.SMALLSHIPYARD, 0, xClick, yClick))
        {
        case 0:
        	//change button value
        	switch (buttonValue)
        	{
        	default:
        	case ID.Build.NONE:
        		buttonValue = ID.Build.SHIP;
        		break;
        	case ID.Build.SHIP:
        		buttonValue = ID.Build.SHIP_LOOP;
        		break;
        	case ID.Build.SHIP_LOOP:
        		buttonValue = ID.Build.NONE;
        		break;
        	}
        	
        	//send packet
        	LogHelper.info("DEBUG : GUI click: build small ship: ship "+buttonValue);
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Shipyard_Type, buttonValue, 0));
        	break;
        case 1:
        	//change button value
        	switch (buttonValue)
        	{
        	default:
        	case ID.Build.NONE:
        		buttonValue = ID.Build.EQUIP;
        		break;
        	case ID.Build.EQUIP:
        		buttonValue = ID.Build.EQUIP_LOOP;
        		break;
        	case ID.Build.EQUIP_LOOP:
        		buttonValue = ID.Build.NONE;
        		break;
        	}
        	
        	//send packet
        	LogHelper.info("DEBUG : GUI click: build small ship: equip "+buttonValue);
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Shipyard_Type, buttonValue, 0));
        	break;
        }
	}
	
	
}
