package com.lulan.shincolle.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.client.gui.inventory.ContainerLargeShipyard;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums.EnumColors;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**SLOT POSITION
 * output(168,51) fuel bar(9,83 height=63) fuel color bar(208,64)
 * ship button(157,24) equip button(177,24) inv(25,116)
 * player inv(25,141) action bar(25,199)
 */
public class GuiLargeShipyard extends GuiContainer
{

	private static final ResourceLocation TEXTURE_BG = new ResourceLocation(Reference.TEXTURES_GUI+"GuiLargeShipyard.png");
	private TileMultiGrudgeHeavy tile;
	private int xClick, yClick, selectMat, buildType, invMode, xMouse, yMouse;
	private String time, errorMsg1, errorMsg2, matBuild0, matBuild1, matBuild2, matBuild3, matStock0, matStock1, matStock2, matStock3;
	private float tickGUI;
	
	
	public GuiLargeShipyard(InventoryPlayer par1, TileMultiGrudgeHeavy par2)
	{
		super(new ContainerLargeShipyard(par1, par2));
		
		this.tile = par2;
		this.xSize = 208;
		this.ySize = 223;
		
		this.tickGUI = 0F;
		
		//string
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
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		//取得gui顯示名稱
		time = this.tile.getBuildTimeString();
		matBuild0 = String.valueOf(this.tile.getMatBuild(0));
		matBuild1 = String.valueOf(this.tile.getMatBuild(1));
		matBuild2 = String.valueOf(this.tile.getMatBuild(2));
		matBuild3 = String.valueOf(this.tile.getMatBuild(3));
		matStock0 = String.valueOf(this.tile.getMatStock(0));
		matStock1 = String.valueOf(this.tile.getMatStock(1));
		matStock2 = String.valueOf(this.tile.getMatStock(2));
		matStock3 = String.valueOf(this.tile.getMatStock(3));
		
//		//畫出字串 parm: string, x, y, color, (是否dropShadow)
//		//畫出該方塊名稱, 位置: x=gui寬度的一半扣掉字串長度一半, y=6, 顏色為4210752
//		this.fontRendererObj.drawString(conName, this.xSize / 2 - this.fontRendererObj.getStringWidth(conName) / 2, 6, EnumColors.GRAY_MIDDLE.getValue());
		
		//畫出倒數時間
		this.fontRendererObj.drawString(time, 176 - this.fontRendererObj.getStringWidth(time) / 2, 77, EnumColors.GRAY_MIDDLE.getValue());
		
		//畫出提示訊息
		if (tile.getPowerGoal() <= 0 && tile.getBuildType() != 0)
		{
			this.fontRendererObj.drawString(errorMsg1, 105 - this.fontRendererObj.getStringWidth(errorMsg1) / 2, 99, EnumColors.RED_LIGHT.getValue());
		}
		else if (!tile.hasPowerRemained())
		{
			this.fontRendererObj.drawString(errorMsg2, 105 - this.fontRendererObj.getStringWidth(errorMsg2) / 2, 99, EnumColors.RED_LIGHT.getValue());
		}
		
		//畫出數字
		int colorNum = 0;
		if (this.tile.getMatBuild(0) < 100) colorNum = EnumColors.RED_LIGHT.getValue();
		else if (this.tile.getMatBuild(0) == 1000) colorNum = EnumColors.YELLOW.getValue();
		else colorNum = EnumColors.WHITE.getValue();
		this.fontRendererObj.drawString(matBuild0, 73 - this.fontRendererObj.getStringWidth(matBuild0) / 2, 20, colorNum);
		
		if (this.tile.getMatBuild(1) < 100) colorNum = EnumColors.RED_LIGHT.getValue();
		else if (this.tile.getMatBuild(1) == 1000) colorNum = EnumColors.YELLOW.getValue();
		else colorNum = EnumColors.WHITE.getValue();
		this.fontRendererObj.drawString(matBuild1, 73 - this.fontRendererObj.getStringWidth(matBuild1) / 2, 39, colorNum);
		
		if (this.tile.getMatBuild(2) < 100) colorNum = EnumColors.RED_LIGHT.getValue();
		else if (this.tile.getMatBuild(2) == 1000) colorNum = EnumColors.YELLOW.getValue();
		else colorNum = EnumColors.WHITE.getValue();
		this.fontRendererObj.drawString(matBuild2, 73 - this.fontRendererObj.getStringWidth(matBuild2) / 2, 58, colorNum);
		
		if (this.tile.getMatBuild(3) < 100) colorNum = EnumColors.RED_LIGHT.getValue();
		else if (this.tile.getMatBuild(3) == 1000) colorNum = EnumColors.YELLOW.getValue();
		else colorNum = EnumColors.WHITE.getValue();
		this.fontRendererObj.drawString(matBuild3, 73 - this.fontRendererObj.getStringWidth(matBuild3) / 2, 77, colorNum);
		
		this.fontRendererObj.drawString(matStock0, 125 - this.fontRendererObj.getStringWidth(matStock0) / 2, 20, EnumColors.YELLOW.getValue());
		this.fontRendererObj.drawString(matStock1, 125 - this.fontRendererObj.getStringWidth(matStock1) / 2, 39, EnumColors.YELLOW.getValue());
		this.fontRendererObj.drawString(matStock2, 125 - this.fontRendererObj.getStringWidth(matStock2) / 2, 58, EnumColors.YELLOW.getValue());
		this.fontRendererObj.drawString(matStock3, 125 - this.fontRendererObj.getStringWidth(matStock3) / 2, 77, EnumColors.YELLOW.getValue());
	
		handleHoveringText();
		
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3)
	{
		GlStateManager.color(1F, 1F, 1F, 1F);  //reset color
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_BG);  //load gui background
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);  //set gui size
       
        //畫出fuel存量條
        int scaleBar; 
        if (tile.hasPowerRemained())
        {
            scaleBar = tile.getPowerRemainingScaled(64);	//彩色進度條長度64	
            drawTexturedModalRect(guiLeft+9, guiTop+83-scaleBar, 208, 64-scaleBar, 12, scaleBar);
        }

        //畫出type選擇框
        switch (tile.getBuildType())
        {
        case ID.Build.SHIP:
        	drawTexturedModalRect(guiLeft+157, guiTop+24, 208, 64, 18, 18);
        	break;
        case ID.Build.SHIP_LOOP:
        	//draw animate
        	switch ((int)tickGUI % 6)
        	{
        	default:
        	case 0:
        		drawTexturedModalRect(guiLeft+157, guiTop+24, 208, 103, 18, 18);
        		break;
        	case 1:
        		drawTexturedModalRect(guiLeft+157, guiTop+24, 208, 121, 18, 18);
        		break;
        	case 2:
        		drawTexturedModalRect(guiLeft+157, guiTop+24, 208, 139, 18, 18);
        		break;
        	case 3:
        		drawTexturedModalRect(guiLeft+157, guiTop+24, 208, 157, 18, 18);
        		break;
        	case 4:
        		drawTexturedModalRect(guiLeft+157, guiTop+24, 208, 175, 18, 18);
        		break;
        	case 5:
        		drawTexturedModalRect(guiLeft+157, guiTop+24, 208, 193, 18, 18);
        		break;
        	}
        	break;
        case ID.Build.EQUIP:
        	drawTexturedModalRect(guiLeft+177, guiTop+24, 208, 64, 18, 18);
        	break;
        case ID.Build.EQUIP_LOOP:
        	//draw animate
        	switch ((int)tickGUI % 6)
        	{
        	default:
        	case 0:
        		drawTexturedModalRect(guiLeft+177, guiTop+24, 208, 103, 18, 18);
        		break;
        	case 1:
        		drawTexturedModalRect(guiLeft+177, guiTop+24, 208, 121, 18, 18);
        		break;
        	case 2:
        		drawTexturedModalRect(guiLeft+177, guiTop+24, 208, 139, 18, 18);
        		break;
        	case 3:
        		drawTexturedModalRect(guiLeft+177, guiTop+24, 208, 157, 18, 18);
        		break;
        	case 4:
        		drawTexturedModalRect(guiLeft+177, guiTop+24, 208, 175, 18, 18);
        		break;
        	case 5:
        		drawTexturedModalRect(guiLeft+177, guiTop+24, 208, 193, 18, 18);
        		break;
        	}
        	break;
        }
        
        //畫出資材數量按鈕 (50,8)
        drawTexturedModalRect(guiLeft+50, guiTop+8+tile.getSelectMat()*19, 0, 223, 48, 30);
        
        //畫出資材選擇框 (27,14)
        drawTexturedModalRect(guiLeft+27, guiTop+14+tile.getSelectMat()*19, 208, 64, 18, 18);
	
        //畫出inventory mode按鈕 (23,92)
        if (tile.getInvMode() == 1)
        {	//iutput mode
        	drawTexturedModalRect(guiLeft+23, guiTop+92, 208, 82, 25, 20);
        }   
	}
	
	//draw tooltip
	private void handleHoveringText()
	{		
		//畫出fuel存量 (8,19,22,84)
		if (xMouse > 8+guiLeft && xMouse < 22+guiLeft && yMouse > 19+guiTop && yMouse < 84+guiTop)
		{
			List list = new ArrayList();
			String strFuel = String.valueOf(tile.getPowerRemained());
			int strLen = this.fontRendererObj.getStringWidth(strFuel) / 2;
			list.add(strFuel);
			this.drawHoveringText(list, 3-strLen, 58, this.fontRendererObj);
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
        
        //build type button
        buildType = this.tile.getBuildType();
        invMode = this.tile.getInvMode();
        selectMat = this.tile.getSelectMat(); 
        
        //page 0 button
        int buttonClicked = GuiHelper.getButton(ID.Gui.LARGESHIPYARD, 0, xClick, yClick);
        switch (buttonClicked)
        {
        case 0:	//build ship
        	//change button value
        	switch (buildType)
        	{
        	default:
        	case ID.Build.NONE:
        		buildType = ID.Build.SHIP;
        		break;
        	case ID.Build.SHIP:
        		buildType = ID.Build.SHIP_LOOP;
        		break;
        	case ID.Build.SHIP_LOOP:
        		buildType = ID.Build.NONE;
        		break;
        	}
        	
        	//send packet
        	LogHelper.debug("DEBUG: GUI click: build large ship: ship "+buildType);
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Shipyard_Type, buildType, 0));
        	break;
        case 1:	//build equip
        	//change button value
        	switch (buildType)
        	{
        	default:
        	case ID.Build.NONE:
        		buildType = ID.Build.EQUIP;
        		break;
        	case ID.Build.EQUIP:
        		buildType = ID.Build.EQUIP_LOOP;
        		break;
        	case ID.Build.EQUIP_LOOP:
        		buildType = ID.Build.NONE;
        		break;
        	}
        	
        	//send packet
        	LogHelper.debug("DEBUG: GUI click: build large ship: equip "+buildType);
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Shipyard_Type, buildType, 0));
        	break;
        case 2:	//inventory mode
        	if (invMode == 0)
        	{
        		invMode = 1;	//原本為input mode, 改為output mode
        	}
        	else
        	{
        		invMode = 0;
        	}
        	LogHelper.debug("DEBUG: GUI click: build large ship: invMode "+invMode);
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Shipyard_InvMode, invMode, 0));
        	break;
        case 3:	//select material grudge
        case 4: //abyssium
        case 5: //ammo
        case 6: //polymetal
        	selectMat = buttonClicked - 3;
        	LogHelper.debug("DEBUG: GUI click: build large ship: select mats "+selectMat);
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Shipyard_SelectMat, selectMat, 0));
        	break;
        case 7:	//select material grudge num
        case 8: //abyssium num
        case 9: //ammo num
        case 10://polymetal num
        	selectMat = buttonClicked - 7;
        	LogHelper.debug("DEBUG: GUI click: build large ship: select mats (num) "+selectMat);
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Shipyard_SelectMat, selectMat, 0));
        	break;
        }//end page 0 button switch
        
        //other page button
        buttonClicked = GuiHelper.getButton(ID.Gui.LARGESHIPYARD, selectMat+1, xClick, yClick);
        switch (buttonClicked)
        {
        case 0:	//build mat +1000
        case 1:	//build mat +100
        case 2:	//build mat +10
        case 3:	//build mat +1
        case 4:	//build mat -1000
        case 5:	//build mat -100
        case 6:	//build mat -10
        case 7:	//build mat -1
        	LogHelper.debug("DEBUG: GUI click: build large ship: inc/dec build materials "+(selectMat+1)+" "+buttonClicked);
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Shipyard_INCDEC, selectMat, buttonClicked));
        	break;	
        }//end other page button switch
        
	}
	
	
}