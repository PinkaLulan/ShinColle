package com.lulan.shincolle.client.gui;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSmallShipyard extends GuiContainer {

	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.TEXTURES_GUI+"GuiSmallShipyard.png");
	private TileEntitySmallShipyard teSmallShipyard;
	
	public GuiSmallShipyard(InventoryPlayer par1, TileEntitySmallShipyard par2) {
		super(new ContainerSmallShipyard(par1, par2));
		teSmallShipyard = par2;
		
		this.xSize = 176;
		this.ySize = 164;
	}
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//取得gui顯示名稱
		String name = this.teSmallShipyard.hasCustomInventoryName() ? this.teSmallShipyard.getInventoryName() : I18n.format(this.teSmallShipyard.getInventoryName());
		String time = this.teSmallShipyard.getBuildTimeString();
		
		//畫出字串 parm: string, x, y, color, (是否dropShadow)
		//畫出該方塊名稱, 位置: x=gui寬度的一半扣掉字串長度一半, y=6, 顏色為4210752
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		//畫出玩家背包名稱
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 87, 4210752);
		//畫出倒數時間
		this.fontRendererObj.drawString(time, 51, 51, 4210752);
		
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);	//RGBA
        Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture); //GUI圖檔
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);	//GUI大小設定
       
        int scaleBar;
        //畫出build進度條          
        scaleBar = teSmallShipyard.getBuildProgressScaled(24);	//彩色進度條長度24
        drawTexturedModalRect(guiLeft+113, guiTop+29, 176, 0, scaleBar+1, 16);
        
        //畫出fuel存量條
        if(teSmallShipyard.remainedPower > 0) {
            scaleBar = teSmallShipyard.getPowerRemainingScaled(31);	//彩色進度條長度31	
            drawTexturedModalRect(guiLeft+10, guiTop+48-scaleBar, 176, 46-scaleBar, 12, scaleBar);
        }
	
	}

}
