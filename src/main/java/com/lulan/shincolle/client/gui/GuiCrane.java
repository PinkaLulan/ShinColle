package com.lulan.shincolle.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.client.gui.inventory.ContainerCrane;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntityCrane;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.GuiHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCrane extends GuiContainer
{

	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.TEXTURES_GUI+"GuiCrane.png");
	private TileEntityCrane tile;
	private int xClick, yClick, xMouse, yMouse;
	private int btnMode, btnRedMode;
	private boolean btnPower, btnMeta, btnDict, btnNbt, btnLoad, btnUnload, slotMode;
	private float tickGUI;
	private static String strLoad, strUnload, strMeta, strDict, strNbt, strNowait, strWaitfev, strNowait1,
						strWaitfev1, strWaitfev2, strRed0, strRed1, strRed2;
	
	
	public GuiCrane(InventoryPlayer par1, TileEntityCrane par2)
	{
		super(new ContainerCrane(par1, par2));
		tile = par2;
		xSize = 176;
		ySize = 193;
		
		//string
		strLoad = I18n.format("gui.shincolle:crane.toship");
		strUnload = I18n.format("gui.shincolle:crane.tochest");
		strMeta = I18n.format("gui.shincolle:crane.usemeta");
		strDict = I18n.format("gui.shincolle:crane.useoredict");
		strNbt = I18n.format("gui.shincolle:crane.usenbt");
		strNowait = I18n.format("gui.shincolle:crane.nowait");
		strWaitfev = I18n.format("gui.shincolle:crane.waitforever");
		strNowait1 = I18n.format("gui.shincolle:crane.nowait1");
		strWaitfev1 = I18n.format("gui.shincolle:crane.waitforever1");
		strWaitfev2 = I18n.format("gui.shincolle:crane.waitforever2");
		strRed0 = I18n.format("gui.shincolle:crane.red0");
		strRed1 = I18n.format("gui.shincolle:crane.red1");
		strRed2 = I18n.format("gui.shincolle:crane.red2");
		
		//init value
		updateButton();
		
	}
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		xMouse = mouseX;
		yMouse = mouseY;
		
	}
	
	//draw tooltip
	private void handleHoveringText()
	{
		int mx = xMouse - guiLeft;
		int my = yMouse - guiTop;
		int len = 0;
		List list = new ArrayList();
		
		//(22,21,35,34) meta (36,21,49,34) dict
		if (my > 21 && my < 34)
		{
			if (mx > 22 && mx < 35)
			{
				list.add(strMeta);
			}
			else if (mx > 36 && mx < 49)
			{
				list.add(strDict);
			}
			else if (mx > 50 && mx < 63)
			{
				list.add(strNbt);
			}
			else if (mx > 64 && mx < 77)
			{
				switch (btnRedMode)
				{
				case 1:
					list.add(strRed1);
					break;
				case 2:
					list.add(strRed2);
					break;
				default:
					list.add(strRed0);
					break;
				}
			}
			
			this.drawHoveringText(list, mx, my+10, this.fontRendererObj);
		}
		//draw wait mode
		else if (mx > 22 && mx < 91 && my > 5 && my < 20)
		{
			list.clear();
			
			switch (btnMode)
			{
			case 0:
				list.add(strNowait1);
				break;
			case 1:
				list.add(strWaitfev1);
				list.add(strWaitfev2);
				break;
			}
			
			this.drawHoveringText(list, -50, 37, this.fontRendererObj);
		}
	}
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		//draw mode string
		String str = null;
		String strnum = null;
		int len = 0;
		
		switch (btnMode)
		{
		case 0:
			str = strNowait;
			break;
		case 1:
			str = strWaitfev;
			break;
		default:
			strnum = String.valueOf(tile.getWaitTimeInMin(btnMode));
			str = I18n.format("gui.shincolle:crane.waitmin", strnum);
			break;
		}
		
		len = (int) (fontRendererObj.getStringWidth(str) * 0.5F);
		fontRendererObj.drawStringWithShadow(str, 57 - len, 9, Enums.EnumColors.YELLOW.getValue());
		
		//draw slot string
		fontRendererObj.drawString(strLoad, 21, 46, Enums.EnumColors.RED_LIGHT.getValue());
		fontRendererObj.drawString(strUnload, 21, 77, Enums.EnumColors.GRAY_LIGHT.getValue());
		
		//draw ship info
		if (tile.getShip() != null)
		{
			//draw ship wait time
			str = String.valueOf(CalcHelper.getTimeFormated((int) (tile.getShip().getStateTimer(ID.T.CraneTime) * 0.05F)));
			len = (int) (fontRendererObj.getStringWidth(str) * 0.5F);
			fontRendererObj.drawString(str, 133 - len, 10, Enums.EnumColors.GRAY_LIGHT.getValue());
			
			//draw ship name
			if (tile.getShip().getCustomNameTag() != null && tile.getShip().getCustomNameTag().length() > 0)
			{
				str = tile.getShip().getCustomNameTag();
			}
			else
			{
				str = I18n.format("entity.shincolle."+tile.getShip().getClass().getSimpleName()+".name");
			}
			len = fontRendererObj.getStringWidth(str);
			fontRendererObj.drawStringWithShadow(str, 170 - len, 24, Enums.EnumColors.WHITE.getValue());
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
		GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
       
        updateButton();
        
        //draw button
        if (this.btnPower)
        {
        	drawTexturedModalRect(guiLeft+7, guiTop+6, 176, 0, 13, 13);
        }
        
        if (this.btnMeta)
        {
        	drawTexturedModalRect(guiLeft+23, guiTop+22, 176, 13, 11, 11);
        }
        
        if (this.btnDict)
        {
        	drawTexturedModalRect(guiLeft+37, guiTop+22, 176, 24, 11, 11);
        }
        
        if (this.btnNbt)
        {
        	drawTexturedModalRect(guiLeft+51, guiTop+22, 176, 46, 11, 11);
        }
        
        if (!this.btnLoad)
        {
        	drawTexturedModalRect(guiLeft+7, guiTop+44, 176, 35, 11, 11);
        	drawTexturedModalRect(guiLeft+8, guiTop+57, 0, 193, 160, 16);
        }
        
        if (!this.btnUnload)
        {
        	drawTexturedModalRect(guiLeft+7, guiTop+75, 176, 35, 11, 11);
        	drawTexturedModalRect(guiLeft+8, guiTop+88, 0, 193, 160, 16);
        }
        
        switch (this.btnRedMode)
        {
        case 1:
        	drawTexturedModalRect(guiLeft+65, guiTop+22, 176, 57, 11, 11);
        	break;
        case 2:
        	drawTexturedModalRect(guiLeft+65, guiTop+22, 176, 68, 11, 11);
        	break;
        }
        
        //check loading slot mode
        for (int i = 0; i < 18; i++)
        {
        	slotMode = this.tile.getItemMode(i);
        	
        	if (slotMode)
        	{
        		if (i >= 9)
        		{
        			drawTexturedModalRect(guiLeft+7+(i-9)*18, guiTop+87, 0, 209, 18, 18);
        		}
        		else
        		{
        			drawTexturedModalRect(guiLeft+7+i*18, guiTop+56, 0, 209, 18, 18);
        		}
        	}
        }

        GlStateManager.disableBlend();
	}
	
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int key) throws IOException
	{
        super.mouseClicked(posX, posY, key);
            
        //get click position
        xClick = posX - guiLeft;
        yClick = posY - guiTop;
        
        updateButton();
        
        switch (GuiHelper.getButton(5, 0, xClick, yClick))
        {
        case 0:  //power
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Crane_Power, !btnPower ? 1 : 0, 0));
        	break;
        case 1:  //mode
        	if (key == 0)
        	{
        		if(btnMode == 1)
        		{
        			btnMode = 6;
        		}
        		else
        		{
        			btnMode++;
        		}
        		
        		if (btnMode > 25) btnMode = 25;
        	}
        	else
        	{
        		if (btnMode == 6)
        		{
        			btnMode = 1;
        		}
        		else
        		{
        			btnMode--;
        		}
        		
        		if (btnMode < 0) btnMode = 0;
        	}
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Crane_Mode, btnMode, 0));
        	break;
        case 2:  //meta
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Crane_Meta, !btnMeta ? 1 : 0, 0));
        	break;
        case 3:  //dict
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Crane_Dict, !btnDict ? 1 : 0, 0));
        	break;
        case 4:  //loading
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Crane_Load, !btnLoad ? 1 : 0, 0));
        	break;
        case 5:  //unloading
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Crane_Unload, !btnUnload ? 1 : 0, 0));
        	break;
        case 6:  //unloading
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Crane_Nbt, !btnNbt ? 1 : 0, 0));
        	break;
        case 7:  //redstone signal mode
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.tile, C2SGUIPackets.PID.TileBtn, ID.B.Crane_Red, btnRedMode + 1, 0));
        	break;
        }
	}
	
	private void updateButton()
	{
		btnPower = tile.getField(2) > 0 ? true : false;
		btnMeta = tile.getField(3) > 0 ? true : false;
		btnDict = tile.getField(4) > 0 ? true : false;
		btnMode = tile.getField(5);
		btnLoad = tile.getField(6) > 0 ? true : false;
		btnUnload = tile.getField(7) > 0 ? true : false;
		btnNbt = tile.getField(8) > 0 ? true : false;
		btnRedMode = tile.getField(10);
	}

	
}


