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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderHelper;
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
	private static GuiDesk gui;
	private static FontRenderer font;
	private static RenderItem itemRender;
	private static int numChap;
	private static int numPage;
	public static int PageLeftCurrent = 0;  //current page for random icon counting
	public static int PageRightCurrent = 0; //current page for random icon counting
	public static int PageWidth = 135;		//page width, no scale = 106
	public static int Page0LX = 13;			//left page X pos, no scale = 13
	public static int Page0RX = 133;		//right page X pos, no scale = 133
	public static int Page0Y = 48;			//page Y pos, no scale = 48
	public static int PageTLX = 13;			//left page X pos for text
	public static int PageTRX = 162;		//right page X pos for text
	public static int PageTY = 58;			//page Y pos for text
	public static final int[] PageLimit = new int[] {1,25,6,19,23,15,4};  //max page number
	
	public GuiBook() {}
	
	/** draw book content */
	public static void drawBookContent(GuiDesk par1, FontRenderer par2, int chap, int page)
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
		
		/***********   DEBUG: test page      *********/
//		if (numChap == 1 && numPage == 25)
//		{
//			cont =  Arrays.asList(
//					new int[] {3, 6, 1},
//					new int[] {0, 0, 0, 0},
//					new int[] {0, 1, 0, 0},
//					new int[] {1, 0, 0, -6, 0, 100, 72, 100, 62},
//					new int[] {2, 0, 3,  -3, ID.Icon.PolymIG},
//					new int[] {2, 0, 23, -3, ID.Icon.Cauldron},
//					new int[] {2, 0, 43, -3, ID.Icon.PolymIG},
//					new int[] {2, 0, 3,  17, ID.Icon.PolymIG},
//					new int[] {2, 0, 23, 17, ID.Icon.Cauldron},
//					new int[] {2, 0, 43, 17, ID.Icon.PolymIG},
//					new int[] {2, 0, 3,  37, ID.Icon.PolymIG},
//					new int[] {2, 0, 23, 37, ID.Icon.Cauldron},
//					new int[] {2, 0, 43, 37, ID.Icon.PolymIG},
//					new int[] {2, 0, 81, 17, ID.Icon.Tank0},
//					new int[] {1, 2, 0, -6, 0, 100, 72, 100, 62},
//					new int[] {2, 2, 3,  -3, ID.Icon.ObsidianB},
//					new int[] {2, 2, 23, -3, ID.Icon.Tank0},
//					new int[] {2, 2, 43, -3, ID.Icon.ObsidianB},
//					new int[] {2, 2, 3,  17, ID.Icon.ObsidianB},
//					new int[] {2, 2, 23, 17, ID.Icon.Tank0},
//					new int[] {2, 2, 43, 17, ID.Icon.ObsidianB},
//					new int[] {2, 2, 3,  37, ID.Icon.ObsidianB},
//					new int[] {2, 2, 23, 37, ID.Icon.Tank0},
//					new int[] {2, 2, 43, 37, ID.Icon.ObsidianB},
//					new int[] {2, 2, 81, 17, ID.Icon.Tank1},
//					new int[] {1, 4, 0, -6, 0, 100, 72, 100, 62},
//					new int[] {2, 4, 3,  -3, ID.Icon.AbyssB},
//					new int[] {2, 4, 23, -3, ID.Icon.Tank1},
//					new int[] {2, 4, 43, -3, ID.Icon.AbyssB},
//					new int[] {2, 4, 3,  17, ID.Icon.AbyssB},
//					new int[] {2, 4, 23, 17, ID.Icon.Tank1},
//					new int[] {2, 4, 43, 17, ID.Icon.AbyssB},
//					new int[] {2, 4, 3,  37, ID.Icon.AbyssB},
//					new int[] {2, 4, 23, 37, ID.Icon.Tank1},
//					new int[] {2, 4, 43, 37, ID.Icon.AbyssB},
//					new int[] {2, 4, 81, 17, ID.Icon.Tank2},
//					new int[] {1, 6, 0, -6, 0, 100, 72, 100, 62},
//					new int[] {2, 6, 3,  -3, ID.Icon.GrudgeBH},
//					new int[] {2, 6, 23, -3, ID.Icon.Tank2},
//					new int[] {2, 6, 43, -3, ID.Icon.GrudgeBH},
//					new int[] {2, 6, 3,  17, ID.Icon.GrudgeBH},
//					new int[] {2, 6, 23, 17, ID.Icon.Tank2},
//					new int[] {2, 6, 43, 17, ID.Icon.GrudgeBH},
//					new int[] {2, 6, 3,  37, ID.Icon.GrudgeBH},
//					new int[] {2, 6, 23, 37, ID.Icon.Tank2},
//					new int[] {2, 6, 43, 37, ID.Icon.GrudgeBH},
//					new int[] {2, 6, 81, 17, ID.Icon.Tank3}
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
		
		drawBookContent(cont);
	}
	
	//draw book content
	private static void drawBookContent(List<int[]> cont)
	{
		if (cont != null)
		{
			int leftRand = 0;
			int rightRand = 1;
			
			//page++ every X ms
			if ((gui.tickGUI & 127) == 0)
			{
				PageLeftCurrent += 2;
				PageRightCurrent += 2;
			}
			
			//draw content
			for (int[] getc : cont)
			{
				//check content existence
				if (getc != null)
				{
					//get draw type
					switch (getc[0])
					{
					case 0:		//text
						drawBookText(getc[1], getc[2], getc[3]);
					break;
					case 1:		//picture
						if (PageLeftCurrent > leftRand) PageLeftCurrent = 0;
						if (PageRightCurrent > rightRand) PageRightCurrent = 1;
						
						if (getc[1] == PageLeftCurrent || getc[1] == PageRightCurrent)
							drawBookPic(getc);
					break;
					case 2:		//icon
						if (PageLeftCurrent > leftRand) PageLeftCurrent = 0;
						if (PageRightCurrent > rightRand) PageRightCurrent = 1;
						
						if (getc[1] == PageLeftCurrent || getc[1] == PageRightCurrent)
							drawBookIcon(getc[1], getc[2], getc[3], getc[4]);
					break;
					/** page setting: random page number, ex:
					 *    cont = {3, 2, 3} => left page will be 0 or 2, right page will be 1 or 3
					 *    cont = {3, 8, 7} => left = 0,2,4,6,8, right = 1,3,5,7
					 */
					case 3:		//
						leftRand = getc[1];
						rightRand = getc[2];
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
		int picY = Page0Y + posY;					//add y offset
		int picX = Page0LX;							//left page
		if ((pageSide & 1) == 1) picX = Page0RX;	//right page
		picX += posX;								//add x offset
		
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
	
	/**
	 * draw book item icon
	 * 
	 * pageSide: 0:left, 1:right, 2:left random, 3:right random
	 */
	private static void drawBookIcon(int pageSide, int offX, int offY, int iconID)
	{
		//set x, y offset
		int picY = Page0Y + offY;					//add y offset
		int picX = Page0LX;							//left page
		if ((pageSide & 1) == 1) picX = Page0RX;	//right page
		picX += offX;								//add x offset
		
		drawItemIcon(getItemStackForIcon(iconID), picX, picY, false);
	}
	
	//draw item icon
	private static void drawItemIcon(ItemStack item, int x, int y, boolean effect)
	{
		if (item != null)
		{
			RenderHelper.enableGUIStandardItemLighting();
			itemRender.renderItemIntoGUI(item, x, y);
			RenderHelper.disableStandardItemLighting();
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
		return Values.ItemIconMap.get((short)itemID);
	}
	
	
}
