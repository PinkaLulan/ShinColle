package com.lulan.shincolle.client.gui;

import java.util.Arrays;
import java.util.List;

import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

/** draw book text
 *  
 *  book range: left:15,32 ~ 115,172  right:135,32 ~ 235,172
 *  title: center: left:64 right:185 y:38
 *  text: left:13,48 right:135,48 width:100
 *  
 *  for recipe picture:
 *  material pos: (3,-3) (23,-3) (43,-3)
 *                (3,17) (23,17) (43,17)     (81,17)
 *                (3,37) (23,37) (43,37)
 */
public class GuiBook
{
	
	//book picture
	private static final ResourceLocation guiBookPic01 = new ResourceLocation(Reference.TEXTURES_GUI+"book/BookPic01.png");
//	private static final ResourceLocation guiBookPic02 = new ResourceLocation(Reference.TEXTURES_GUI+"book/BookPic02.png");
	
	private static TextureManager tm = ClientProxy.getMineraft().getTextureManager();
	private static GuiContainer gui;
	private static FontRenderer font;
	private static RenderItem itemRender;
	private static int numChap;
	private static int numPage;
	public static int PageWidth = 135; //page width, no scale = 106
	public static int Page0LX = 13;    //left page X pos, no scale = 13
	public static int Page0RX = 133;   //right page X pos, no scale = 133
	public static int Page0Y = 48;     //page Y pos, no scale = 48
	public static int PageTLX = 13;    //left page X pos for text
	public static int PageTRX = 162;   //right page X pos for text
	public static int PageTY = 58;     //page Y pos for text
	public static final int[] PageLimit = new int[] {1,23,5,15,19,11,3};  //max page number
	
	public GuiBook() {}
	
	/** draw book content */
	public static void drawBookContent(GuiContainer par1, FontRenderer par2, int chap, int page)
	{
		itemRender = Minecraft.getMinecraft().getRenderItem();
		
		/** Content Array:
		 *  0:c.type  1:pageL/R  2:posX  3:posY  4:add content
		 */
		int index = getIndexID(chap, page);
		List<int[]> cont = Values.BookList.get(index);
		
		gui = par1;
		font = par2;
		numChap = chap;
		numPage = page;
		
//		/***********   DEBUG: test page      *********/
//		if(numChap == 3 && numPage == 13) {
//			cont =  Arrays.asList(new int[] {0, 0, 0, 0},
//					new int[] {0, 1, 0, 0},
//					new int[] {1, 0, 0, 120, 0, 100, 245, 100, 11}
//		);}
//		PageWidth = 135;  //page width, no scale = 106
//		Page0LX = 13;	  //left page start X pos, no scale = 13
//		Page0RX = 133;	  //right page start X pos, no scale = 133
//		Page0Y = 48;	  //left page start Y pos, no scale = 48
//		PageTLX = 13;	  //left page start X pos, no scale = 13
//		PageTRX = 162;	  //right page start X pos, no scale = 133
//		PageTY = 58;	  //left page start Y pos, no scale = 48
		
		/** set default text */
		if (cont == null)
		{
			cont =  Arrays.asList(new int[] {0, 0, 0, 0},
								  new int[] {0, 1, 0, 0});
		}
		
		if (cont != null)
		{
			for (int[] getc : cont)
			{
				//check content existence
				if (getc != null && getc.length > 3)
				{
					//get draw type
					switch (getc[0])
					{
					case 0:    //text
						drawBookText(getc[1], getc[2], getc[3]);
						break;
					case 1:    //picture
						drawBookPic(getc);
						break;
					case 2:    //icon
						drawBookIcon(getc[1], getc[2], getc[3], getc[4]);
						break;
					}
				}
			}//for every item in list
		}//end list null check
	}
	
	/** draw book text
	 *  pageSide: 0:left  1:right
	 *  offXY: x,y offset
	 */
	private static void drawBookText(int pageSide, int offX, int offY)
	{
		//draw title
		drawTitleText();
		
		//draw page text
		drawPageText(pageSide, offX, offY);
	}
	
	/** draw title text */
	private static void drawTitleText()
	{
		//get title string
		String str = null;
		switch (numChap)
		{
		case 0:
			str = I18n.format("gui.shincolle:book.chap"+numChap+".title");
			break;
		default:
			str = I18n.format("gui.shincolle:book.chap"+numChap+".title"+numPage);
			break;
		}
		
		int strlen = (int) (font.getStringWidth(str) * 0.5F);
		str = TextFormatting.UNDERLINE + str;
		//draw title
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.8F, 0.8F, 0.8F);
		font.drawString(str, 82-strlen, 40, Enums.EnumColors.RED_DARK.getValue());
		GlStateManager.popMatrix();
	}
	
	/** draw page text */
	private static void drawPageText(int pageSide, int offX, int offY)
	{
		//set x, y offset
		int picY = PageTY + offY - 4;        //add y offset
		int picX = PageTLX;               //left page
		if(pageSide > 0) picX = PageTRX;  //right page
		picX += offX;                     //add x offset
		
		//get text string
		String str = I18n.format("gui.shincolle:book.chap"+numChap+".text"+numPage+"d"+pageSide);
		
		//draw text
		drawStringWithSpecialSymbol(str, picX, picY);
	}
	
	/** draw string with new line or other special symbol
	 *  page: 0:left 1:right
	 */
	private static void drawStringWithSpecialSymbol(String str, int x, int y)
	{
		String[] strArray = CalcHelper.stringConvNewlineToArray(str);
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.8F, 0.8F, 0.8F);
		
		int newY = y;
		for (String s : strArray)
		{
			//drawSplitString(string, x, y, split width, color)
			font.drawSplitString(s, x, newY, PageWidth, 0);
			newY += font.splitStringWidth(s, PageWidth);
		}
		
		GlStateManager.popMatrix();
	}
	
	/** draw book picture 
	 *  parms:
	 *  0:c.type  1:page pos  2:posX  3:posY  4:picID  5:picU  6:picV  7:sizeX  8:sizeY
	 */
	private static void drawBookPic(int[] parms)
	{
		int pageSide, posX, posY, picID, picU, picV, sizeX, sizeY;
		
		//null check
		if (parms != null && parms.length == 9)
		{
			pageSide = parms[1];
			posX = parms[2];
			posY = parms[3];
			picID = parms[4];
			picU = parms[5];
			picV = parms[6];
			sizeX = parms[7];
			sizeY = parms[8];
		}
		else
		{
			return;
		}
		
		//set x, y offset
		int picY = Page0Y + posY;			//add y offset
		int picX = Page0LX;					//left page
		if (pageSide > 0) picX = Page0RX;	//right page
		picX += posX;						//add x offset
		
		//set picture texture
		switch (picID)
		{
		case 0:
			tm.bindTexture(guiBookPic01);
			break;
		case 1:
//			tm.bindTexture(guiBookPic02); TODO
			break;
		}
		
		//draw picture
		GlStateManager.color(1F, 1F, 1F, 1F);	//reset RGBA
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		gui.drawTexturedModalRect(picX, picY, picU, picV, sizeX, sizeY);
		GlStateManager.disableBlend();
	}
	
	/** draw book item icon */
	private static void drawBookIcon(int pageSide, int offX, int offY, int iconID)
	{
		//set x, y offset
		int picY = Page0Y + offY;			//add y offset
		int picX = Page0LX;					//left page
		if(pageSide > 0) picX = Page0RX;	//right page
		picX += offX;						//add x offset
		
		drawItemIcon(getItemStackForIcon(iconID), picX, picY, false);
	}
	
	//draw item icon
	private static void drawItemIcon(ItemStack item, int x, int y, boolean effect)
	{
		if (item != null)
		{
			itemRender.renderItemAndEffectIntoGUI(item, x, y);
		}
	}
	
	//get max page number
	public static int getMaxPageNumber(int chap)
	{
		if (chap < PageLimit.length) return PageLimit[chap];
		return 0;
	}
	
	//get index id for Values.BookList
	public static int getIndexID(int ch, int pg)
	{
		return ch * 1000 + pg;
	}
	
	//get itemstack for icon
	public static ItemStack getItemStackForIcon(int itemID)
	{
		return Values.ItemIconMap.get((byte)itemID);
	}
	
	
}
