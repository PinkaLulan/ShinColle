package com.lulan.shincolle.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.client.gui.inventory.ContainerDesk;
import com.lulan.shincolle.crafting.ShipCalc;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.team.TeamData;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

/** admiral's desk
 * 
 *  type:
 *  0: open GUI with TileEntity
 *  1: open GUI with radar item
 *  2: open GUI with book item
 *  
 *  function:
 *  0: no function
 *  1: radar
 *  2: book
 *  3: fleet manage
 *  4: target selector
 *  
 */
public class GuiDesk extends GuiContainer
{

	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDesk.png");
	private static final ResourceLocation guiRadar = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDeskRadar.png");
	private static final ResourceLocation guiBook = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDeskBook.png");
	private static final ResourceLocation guiBook2 = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDeskBook2.png");
	private static final ResourceLocation guiTeam = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDeskTeam.png");
	private static final ResourceLocation guiTarget = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDeskTarget.png");
	private static final ResourceLocation guiNameIcon = new ResourceLocation(Reference.TEXTURES_GUI+"GuiNameIcon.png");
	
	private TileEntityDesk tile;
	private int xClick, yClick, xMouse, yMouse, tempCD, lastXMouse, lastYMouse;
	private int tickGUI, guiFunc, type;
	private float GuiScale, GuiScaleInv;
	private int[] listNum, listClicked; //list var: 0:radar 1:team 2:target 3:teamAlly 4:teamBan
	private static final int CLICKCD = 60;
	private static final int LISTCLICK_RADAR = 0;
	private static final int LISTCLICK_TEAM = 1;
	private static final int LISTCLICK_TARGET = 2;
	private static final int LISTCLICK_ALLY = 3;
	private static final int LISTCLICK_BAN = 4;
	private static String StrGUI, StrPos, StrHeight, StrTeamID, StrBreak, StrAlly, StrOK, StrUnban,
							StrBan, StrCancel, StrAllyList, StrBanList, StrRename, StrDisband, StrCreate,
							StrNeutral, StrBelong, StrAllied, StrHostile, StrRemove;
	
	//player data
	EntityPlayer player;
	CapaTeitoku capa;
	
	//radar
	private int radar_zoomLv;			//0:256x256 1:64x64 2:16x16
	private List<ShipEntity> shipList;	//ship list
	
	//book
	private int book_chapNum;
	private int book_pageNum;
	
	//team
	private int teamState;				//team operation state
	private int listFocus;				//list focus
	private GuiTextField textField;
	private static final int TEAMSTATE_MAIN = 0;
	private static final int TEAMSTATE_CREATE = 1;
	private static final int TEAMSTATE_ALLY = 2;
	private static final int TEAMSTATE_RENAME = 3;
	private static final int TEAMSTATE_BAN = 4;
	
	//target list
	Entity targetEntity = null;					//entity for model display
	private ArrayList<String> tarList;			//target list
	private float mScale, mRotateX, mRotateY;	//target model parms
	
	//ship model
	BasicEntityShip shipModel = null;
	BasicEntityMount shipMount = null;
	private int shipType, shipClass;
	private int[][] iconXY = null;
	
	//object: ship entity + pixel position
	private class ShipEntity
	{
		public Entity ship;
		public String name;
		public double pixelx;	//included guiLeft/guiTop distance
		public double pixely;
		public double pixelz;
		
		public void setShip(Entity ship)
		{
			this.ship = ship;
			//get name
			if (((EntityLiving) ship).getCustomNameTag() != null && ((EntityLiving) ship).getCustomNameTag().length() > 0)
			{
				name = ((EntityLiving) ship).getCustomNameTag();
			}
			else 
			{
				name = I18n.format("entity.shincolle."+ship.getClass().getSimpleName()+".name");
			}
		}
	}
	
	
	public GuiDesk(EntityPlayer player, TileEntityDesk tile, int type)
	{
		super(new ContainerDesk(player, tile, type));
		
		this.type = type;
		this.tile = tile;
		this.GuiScale = 1.25F;
		this.GuiScaleInv = 1F / this.GuiScale;
		this.xSize = (int) (256 * this.GuiScale);
		this.ySize = (int) (192 * this.GuiScale);
	}
	
	private void updateDeskValue()
	{
		this.guiFunc = this.tile.getField(0);
  		this.book_chapNum = this.tile.getField(1);
  		this.book_pageNum = this.tile.getField(2);
  		this.radar_zoomLv = this.tile.getField(3);
	}
	
	@Override
	public void initGui()
	{
		super.initGui();

		this.lastXMouse = 0;
		this.lastYMouse = 0;
		this.tickGUI = 0;				//ticks in gui (not game tick)
		this.tempCD = CLICKCD;
		
		//get tile value
		//open GUI with Tile
		if (type == 0)
		{
			updateDeskValue();
		}
		//open GUI with item
		else
		{
			this.guiFunc = this.type;
	  		this.book_chapNum = 0;
	  		this.book_pageNum = 0;
	  		this.radar_zoomLv = 0;
		}
		
		//player data
		player = ClientProxy.getClientPlayer();
		capa = CapaTeitoku.getTeitokuCapability(player);
		
		//list var: 0:radar 1:team 2:target
		this.listNum = new int[] {0, 0, 0, 0, 0};
		this.listClicked = new int[] {-1, -1, -1, -1, -1};
		
		//radar
		this.shipList = new ArrayList();
		
		//team
		this.teamState = 0;
		this.listFocus = LISTCLICK_TEAM;
		
		//target
		updateTargetClassList();
		this.mScale = 30F;
		this.mRotateX = 0F;
		this.mRotateY = -30F;
		
		//ship model
		setShipModel(this.book_chapNum, this.book_pageNum);
		
        //textField: font, x, y, width, height
        this.textField = new GuiTextField(1, this.fontRendererObj, (int)((this.guiLeft+10)*this.GuiScale), (int)((this.guiTop+24)*this.GuiScale), 153, 12);
        this.textField.setTextColor(-1);					//點選文字框時文字顏色
        this.textField.setDisabledTextColour(-1);			//無點選文字框時文字顏色
        this.textField.setEnableBackgroundDrawing(true);	//畫出文字框背景
        this.textField.setMaxStringLength(64);				//接受最大文字長度
        this.textField.setEnabled(false);
        this.textField.setFocused(false);
        
        //add text input field
        Keyboard.enableRepeatEvents(true);
        
        //string
        StrGUI = I18n.format("gui.shincolle:radar.gui");
        StrPos = I18n.format("gui.shincolle:radar.position");
        StrHeight = I18n.format("gui.shincolle:radar.height");
        StrTeamID = I18n.format("gui.shincolle:team.teamid");	
        StrBreak = I18n.format("gui.shincolle:team.break");
        StrAlly = I18n.format("gui.shincolle:team.ally");
		StrOK = I18n.format("gui.shincolle:general.ok");
		StrUnban = I18n.format("gui.shincolle:team.unban");
		StrBan = I18n.format("gui.shincolle:team.ban");
		StrCancel = I18n.format("gui.shincolle:general.cancel");
		StrAllyList = I18n.format("gui.shincolle:team.allylist");
		StrBanList = I18n.format("gui.shincolle:team.banlist");
		StrRename = I18n.format("gui.shincolle:team.rename");
		StrDisband = I18n.format("gui.shincolle:team.disband");
		StrCreate = I18n.format("gui.shincolle:team.create");
		StrNeutral = I18n.format("gui.shincolle:team.neutral");
		StrBelong = I18n.format("gui.shincolle:team.belong");
		StrAllied = I18n.format("gui.shincolle:team.allied");
		StrHostile = I18n.format("gui.shincolle:team.hostile");
		StrRemove = I18n.format("gui.shincolle:target.remove");
		
	}
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		GlStateManager.pushMatrix();
		GlStateManager.scale(this.GuiScale, this.GuiScale, 1F);
		super.drawScreen(mouseX, mouseY, f);
		GlStateManager.popMatrix();
		
		//update GUI var
		xMouse = mouseX;
		yMouse = mouseY;
		tickGUI += 1;
		if (this.tempCD > 0) tempCD--;
		
		//draw GUI text input
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.disableBlend();
        
        if (this.teamState == TEAMSTATE_CREATE || this.teamState == TEAMSTATE_RENAME)
        {
        	this.textField.setEnabled(true);
        	this.textField.drawTextBox();
        }
        else
        {
        	this.textField.setEnabled(false);
        }
        
        GlStateManager.popMatrix();
	}
	
	//draw tooltip
	private void handleHoveringText()
	{
		int x2 = (int) (xMouse * this.GuiScaleInv);
		int y2 = (int) (yMouse * this.GuiScaleInv);
		int mx = x2 - guiLeft;
		int my = y2 - guiTop;
		
		//draw ship name on light spot in radar function
		switch (this.guiFunc)
		{
		case 1:  /** radar */
			List list = new ArrayList();
			
			for (ShipEntity obj : this.shipList)
			{
				Entity ship = null;
				
				if (obj != null)
				{
					//mouse point at light spot icon
					if (x2 < obj.pixelx+4 && x2 > obj.pixelx-2 &&
						y2 < obj.pixelz+4 && y2 > obj.pixelz-2)
					{
						ship = obj.ship;
					}
				}
				
				if (ship != null)
				{
					String strName = obj.name;
	    			list.add(strName);  //add string to draw list
				}
			}//end for all obj in shipList
			
			//draw string
			drawHoveringText(list, mx, my, this.fontRendererObj);
		break;  //end radar
		case 2:     /** book */
			int getbtn = GuiHelper.getButton(ID.Gui.ADMIRALDESK, 2, mx, my);

			//get chap text
        	if (getbtn > 1 && getbtn < 9)
        	{
        		getbtn -= 2;
        		String strChap = I18n.format("gui.shincolle:book.chap"+getbtn+".title");
        		List list2 = CalcHelper.stringConvNewlineToList(strChap);
        		int strLen = this.fontRendererObj.getStringWidth(strChap);
        		drawHoveringText(list2, mx-strLen-20, my, this.fontRendererObj);
        	}
        	//get item text
        	else
        	{
        		int id = GuiBook.getIndexID(this.book_chapNum, this.book_pageNum);
        		List<int[]> cont = Values.BookList.get(id);
        		
        		if (cont != null)
        		{
        			for (int[] getc : cont)
        			{
        				if (getc != null && getc.length == 5)
        				{
        					int xa = getc[2] + GuiBook.Page0LX - 1;              //at left page
        					if (getc[1] > 0) xa = getc[2] + GuiBook.Page0RX - 1;  //at right page
        					int xb = xa + 16;
        					int ya = getc[3] + GuiBook.Page0Y;
        					int yb = ya + 16;
        					ItemStack item = GuiBook.getItemStackForIcon(getc[4]);
        					
        					if (mx > xa-1 && mx < xb+1 && my > ya-1 && my < yb+1)
        					{
        						this.renderToolTip(item, mx, my);
        						break;
        					}
        				}
        			}//end for book content
        		}//end if book content
        	}
		break;  //end book
		}//end func switch
	}

	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		//draw ship data in radar function
		switch (this.guiFunc)
		{
		case 1:		//radar
			drawRadarText();
		break;  	//end radar
		case 2:		//book
			boolean canDrawText = true;
			//chap 4,5,6, check list first
			if (this.book_pageNum > 0)
			{
				if (this.book_chapNum == 4 || this.book_chapNum == 5)
				{
					//set draw model
					if (this.shipModel == null)
					{
						canDrawText = false;
					}
					
					//draw page number
					String str = "No. "+this.book_pageNum;
					int strlen = (int)(fontRendererObj.getStringWidth(str) * 0.5F);
					fontRendererObj.drawStringWithShadow(str, 55, 32, this.book_chapNum == 4 ? Enums.EnumColors.RED_DARK.getValue() : Enums.EnumColors.CYAN.getValue());
				}
				else if (this.book_chapNum == 6)
				{
					//TODO
				}
			}
			
			if (canDrawText) GuiBook.drawBookContent(this, this.fontRendererObj, this.book_chapNum, this.book_pageNum);
		break;
		case 3:		//team
			drawTeamText();
		break;		//end team
		case 4:		//target
			drawTargetText();
		break;		//end target
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
        Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture);
        
        //若gui貼圖不為256x256, 則使用以下兩個方法畫貼圖
        //func_146110_a(x, y, u, v, xSize, ySize, textXSize, textYSize)
        //func_152125_a(x, y, u, v, xSize, ySize, drawXSize, drawYSize textXSize, textYSize)
        if (this.type == 0)
        {
        	drawTexturedModalRect(guiLeft, guiTop, 0, 0, 256, 192);
        	
        	//get new value
        	updateDeskValue();
      		
      		//畫出功能按鈕
            switch (this.guiFunc)
            {
            case 1:		//radar
            	drawTexturedModalRect(guiLeft+3, guiTop+2, 0, 192, 16, 16);
            break;
            case 2:		//book
            	drawTexturedModalRect(guiLeft+22, guiTop+2, 16, 192, 16, 16);
            break;
            case 3:		//team
            	drawTexturedModalRect(guiLeft+41, guiTop+2, 32, 192, 16, 16);
            break;
            case 4:		//target
            	drawTexturedModalRect(guiLeft+60, guiTop+2, 48, 192, 16, 16);
            break;
            }
		}
        
        //畫出功能介面
        switch (this.guiFunc)
        {
        case 1:		//radar
        	//background
        	Minecraft.getMinecraft().getTextureManager().bindTexture(guiRadar);
        	drawTexturedModalRect(guiLeft, guiTop, 0, 0, 256, 192);
        	
        	//draw zoom level button
        	int texty = 192;
        	switch (this.radar_zoomLv)
        	{
        	case 1:
        		texty = 200;
        	break;
        	case 2:
        		texty = 208;
        	break;
        	}
        	drawTexturedModalRect(guiLeft+9, guiTop+160, 24, texty, 44, 8);
        	
        	//draw ship clicked circle
        	if (this.listClicked[LISTCLICK_RADAR] > -1 && this.listClicked[LISTCLICK_RADAR] < 5)
        	{
        		int cirY = 25 + this.listClicked[LISTCLICK_RADAR] * 32;
            	drawTexturedModalRect(guiLeft+142, guiTop+cirY, 68, 192, 108, 31);
        	}
        	
        	//draw radar ship icon
        	drawRadarIcon();
        	
        	//draw morale icon
        	drawMoraleIcon();
        	
        break;		//end radar
        case 2:		//book
        	//background
        	Minecraft.getMinecraft().getTextureManager().bindTexture(guiBook);
        	drawTexturedModalRect(guiLeft, guiTop, 0, 0, 256, 192);
        	
        	//if mouse on page button, change button color
    		if (xMouse < guiLeft + 137 * this.GuiScale)
    		{
    			drawTexturedModalRect(guiLeft+53, guiTop+182, 0, 192, 18, 10);
    		}
    		else
    		{
    			drawTexturedModalRect(guiLeft+175, guiTop+182, 0, 202, 18, 10);
    		}
    		
    		//draw ship model if chap = 4,5
    		if (this.book_pageNum > 0 && (this.book_chapNum == 4 || this.book_chapNum == 5))
    		{
    			drawShipModel();
    		}
    		
        break;  	//end book
        case 3:		//team
        	//background
        	Minecraft.getMinecraft().getTextureManager().bindTexture(guiTeam);
        	drawTexturedModalRect(guiLeft, guiTop, 0, 0, 256, 192);
        	drawTeamPic();
        	
        break;		//end team
        case 4:		//target
        	//background
        	Minecraft.getMinecraft().getTextureManager().bindTexture(guiTarget);
        	drawTexturedModalRect(guiLeft, guiTop, 0, 0, 256, 192);
        	
        	//draw ship clicked circle
        	if (this.listClicked[LISTCLICK_TARGET] > -1 && this.listClicked[LISTCLICK_TARGET] < 13)
        	{
        		int cirY = 25 + this.listClicked[LISTCLICK_TARGET] * 12;
            	drawTexturedModalRect(guiLeft+142, guiTop+cirY, 68, 192, 108, 31);
        	}
        	
        	//draw target model
        	drawTargetModel();
        	
        break;	//end target
        }//end switch
	}
	
	//mouse input
	@Override
	public void handleMouseInput() throws IOException
	{
		super.handleMouseInput();
		
		int wheel = Mouse.getEventDWheel();
		
		//scroll down
		if (wheel < 0)
		{
			handleWheelMove(false);
		}
		//scroll up
		else if (wheel > 0)
		{
			handleWheelMove(true);
		}
	}
	
	//handle mouse wheel move
	private void handleWheelMove(boolean isWheelUp)
	{
		//get list size
		int listSize = 0;
		int listID = -1;
		
		switch (this.guiFunc)
		{
		case 1:  //radar
			listSize = this.shipList.size();
			listID = LISTCLICK_RADAR;
		break;
		case 2:  //book
			if (isWheelUp) 
			{
				this.mScale += 2F;
				if(this.mScale > 200F) this.mScale = 200F;
			}
			else
			{
				this.mScale -= 2F;
				if(this.mScale < 5F) this.mScale = 5F;
			}
		break;
		case 3:  //team
			//right side: team list
			if (xMouse - guiLeft > 138)
			{
				if (this.capa.getPlayerTeamDataMap() != null)
				{
					listSize = this.capa.getPlayerTeamDataMap().size();
					listID = LISTCLICK_TEAM;
				}
			}
			//left side: ally list
			else
			{
				if (this.teamState == TEAMSTATE_ALLY && this.capa.getPlayerTeamAllyList() != null)
				{
					listSize = this.capa.getPlayerTeamAllyList().size();
					listID = LISTCLICK_ALLY;
				}
				else if (this.teamState == TEAMSTATE_BAN && this.capa.getPlayerTeamBannedList() != null)
				{
					listSize = this.capa.getPlayerTeamBannedList().size();
					listID = LISTCLICK_BAN;
				}
			}
			
		break;
		case 4:  //target
			if (xMouse - guiLeft > 190)
			{
				listSize = this.tarList.size();
				listID = LISTCLICK_TARGET;
			}
			else
			{
				if (isWheelUp)
				{
					this.mScale += 4F;
					if(this.mScale > 200F) this.mScale = 200F;
				}
				else
				{
					this.mScale -= 4F;
					if(this.mScale < 5F) this.mScale = 5F;
				}
			}
		break;
		}//end switch
		
		if (listID < 0) return;
		
		if (isWheelUp)
		{
			listClicked[listID]++;
			listNum[listID]--;
			
			if (listNum[listID] < 0)
			{
				listNum[listID] = 0;
				listClicked[listID]--;  //捲過頭, 補回1
			}
		}
		else
		{
			if (listSize > 0)
			{
				listClicked[listID]--;
				listNum[listID]++;
				
				if (listNum[listID] > listSize - 1)
				{
					listNum[listID] = listSize - 1;
					listClicked[listID]++;
				}
				if (listNum[listID] < 0)
				{
					listNum[listID] = 0;
					listClicked[listID]++;  //捲過頭, 補回1
				}
			}
		}
		
	}
	
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int mouseKey) throws IOException
	{
        super.mouseClicked(posX, posY, mouseKey);
        
        //set focus for textField, CHECK BEFORE GUI SCALE!!
        this.textField.mouseClicked(posX, posY, mouseKey);
        
        //gui scale
        posX = (int)(posX * this.GuiScaleInv);
        posY = (int)(posY * this.GuiScaleInv);
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        
        //match all pages
        if (this.type == 0)
        {
        	int getFunc = GuiHelper.getButton(ID.Gui.ADMIRALDESK, 0, xClick, yClick);
            setDeskFunction(getFunc);
        }
        
        //match radar page
        switch (this.guiFunc)
        {
        case 1:     //radar
        	int radarBtn = GuiHelper.getButton(ID.Gui.ADMIRALDESK, 1, xClick, yClick);
        	switch (radarBtn)
        	{
            case 0:	//radar scale
            	this.radar_zoomLv++;
            	if (this.radar_zoomLv > 2) this.radar_zoomLv = 0;
            break;
            case 1: //ship slot 0~4
            case 2:
            case 3:
            case 4:
            case 5:
            	this.listClicked[LISTCLICK_RADAR] = radarBtn - 1;
            break;
            case 6: //open ship GUI
            	this.openShipGUI();
            break;
            }//end switch
        break;  	//end radar
        case 2:     //book
        	int getbtn2 = -1;
        	
        	if (this.book_chapNum == 4 || this.book_chapNum == 5)
        	{
        		getbtn2 = GuiHelper.getButton(ID.Gui.ADMIRALDESK, 5, xClick, yClick);
        	}

        	//get no button in ch5, check ch2 button
        	if(getbtn2 < 0)
        	{
        		int getbtn = GuiHelper.getButton(ID.Gui.ADMIRALDESK, 2, xClick, yClick);
            	switch (getbtn)
            	{
                case 0:	//left
                	if (mouseKey == 0)
                	{
                		this.book_pageNum--;
                	}
                	else
                	{
                		this.book_pageNum -= 10;
                	}
                	
                	if (this.book_pageNum < 0) this.book_pageNum = 0;
    	  	  		
                	setShipModel(this.book_chapNum, this.book_pageNum);
                	LogHelper.debug("DEBUG: desk: book page: "+book_pageNum);
                break;
                case 1: //right
                	if (mouseKey == 0)
                	{
                		this.book_pageNum++;
                	}
                	else
                	{
                		this.book_pageNum += 10;
                	}
                	
                	if (this.book_pageNum > GuiBook.getMaxPageNumber(book_chapNum))
                	{
                		this.book_pageNum = GuiBook.getMaxPageNumber(book_chapNum);
                	}
    	  	  		
                	setShipModel(this.book_chapNum, this.book_pageNum);
                	LogHelper.debug("DEBUG: desk: book page: "+book_pageNum);
                break;
                case 2: //chap 0
                case 3: //chap 1
                case 4: //chap 2
                case 5: //chap 3
                case 6: //chap 4
                case 7: //chap 5
                case 8: //chap 6
                	this.book_chapNum = getbtn - 2;
                	this.book_pageNum = 0;
                	LogHelper.debug("DEBUG: desk: book chap: "+book_chapNum);
                break;
                }
        	}
        	//get ship model button
        	else
        	{
        		if (this.shipModel != null)
        		{
        			switch (getbtn2)
        			{
            		case 0:  //ship model
            		break;
            		case 1:  //cake
            			this.shipModel.setShipOutfit(false);
            			this.setShipMount();
            		break;
            		case 2:  //cake sneaking
            			this.shipModel.setShipOutfit(true);
            		break;
            		case 3:  //sit
            			this.shipModel.setSitting(!this.shipModel.isSitting());
            			
            			//roll Emotion
            			if (this.shipModel.getRNG().nextInt(2) == 0)
            			{
            				this.shipModel.setStateEmotion(ID.S.Emotion, ID.Emotion.BORED, false);
            			}
            			else
            			{
            				this.shipModel.setStateEmotion(ID.S.Emotion, ID.Emotion.NORMAL, false);
            			}
            			
            			//roll Emotion4
            			if (this.shipModel.getRNG().nextInt(2) == 0)
            			{
            				this.shipModel.setStateEmotion(ID.S.Emotion4, ID.Emotion.BORED, false);
            			}
            			else
            			{
            				this.shipModel.setStateEmotion(ID.S.Emotion4, ID.Emotion.NORMAL, false);
            			}
            		break;
            		case 4:  //run
            			this.shipModel.setSprinting(!this.shipModel.isSprinting());
            			if (this.shipMount != null) this.shipMount.setSprinting(!this.shipMount.isSprinting());
            		break;
            		case 5:  //attack
            			this.shipModel.setAttackTick(50);
            			this.shipModel.setStateEmotion(ID.S.Phase, this.shipModel.getRNG().nextInt(4), false);
            			if (this.shipMount != null) this.shipMount.setAttackTick(50);
            		break;
            		case 6:  //emotion
            			//roll Emotion4
            			if (this.shipModel.getRNG().nextInt(2) == 0)
            			{
            				this.shipModel.setStateEmotion(ID.S.Emotion4, ID.Emotion.BORED, false);
            			}
            			else
            			{
            				this.shipModel.setStateEmotion(ID.S.Emotion4, ID.Emotion.NORMAL, false);
            			}
            			
            			//roll sneaking
            			this.shipModel.setSneaking(this.shipModel.getRNG().nextInt(3) == 0);
            			
            			//roll Emotion/NoFuel
            			if (this.shipModel.getRNG().nextInt(7) == 0)
            			{
            				this.shipModel.setStateFlag(ID.F.NoFuel, true);
            			}
            			else
            			{
            				this.shipModel.setStateFlag(ID.F.NoFuel, false);
            				this.shipModel.setStateEmotion(ID.S.Emotion, this.shipModel.getRNG().nextInt(6), false);
                			if (this.shipMount != null) this.shipMount.setStateEmotion(ID.S.Emotion, this.shipMount.getRNG().nextInt(6), false);
            			}
            		break;
            		}//end switch
        		}//end get ship model
        	}//end get ship model page button
        break;  //end book
        case 3:		//team
        	int teamBtn = GuiHelper.getButton(ID.Gui.ADMIRALDESK, 3, xClick, yClick);
        	switch (teamBtn)
        	{
            case 0:	//left top button
            	switch (this.teamState)
            	{
            	case TEAMSTATE_MAIN:
            		handleClickTeamMain(0);
        		break;
            	case TEAMSTATE_ALLY:
            		handleClickTeamAlly(0);
            	break;
            	case TEAMSTATE_BAN:
            		handleClickTeamBan(0);
            	break;
            	case TEAMSTATE_CREATE:
            		handleClickTeamCreate(0);
            	break;
            	case TEAMSTATE_RENAME:
            		handleClickTeamRename(0);
            	break;
            	}//end switch
            break;
            case 1: //team slot 0~4
            case 2:
            case 3:
            case 4:
            case 5:
            	this.listFocus = LISTCLICK_TEAM;
            	this.listClicked[LISTCLICK_TEAM] = teamBtn - 1;
            break;
            case 6: //left bottom button
            	switch (this.teamState)
            	{
            	case TEAMSTATE_MAIN:
            		handleClickTeamMain(1);
        		break;
            	case TEAMSTATE_ALLY:
            		handleClickTeamAlly(1);
            	break;
            	case TEAMSTATE_BAN:
            		handleClickTeamBan(1);
            	break;
            	case TEAMSTATE_CREATE:
            		handleClickTeamCreate(1);
            	break;
            	case TEAMSTATE_RENAME:
            		handleClickTeamRename(1);
            	break;
            	}
            break;
            case 7: //right top button
            	switch (this.teamState)
            	{
            	case TEAMSTATE_MAIN:
            		handleClickTeamMain(2);
            	break;
            	}
            	break;
            case 8: //right bottom button
            	switch (this.teamState)
            	{
            	case TEAMSTATE_MAIN:
            		handleClickTeamMain(3);
        		break;
            	}
            break;
            case 9:
            case 10:
            case 11:
            	switch (this.teamState)
            	{
            	case TEAMSTATE_ALLY:
            		this.listFocus = LISTCLICK_ALLY;
                	this.listClicked[LISTCLICK_ALLY] = teamBtn - 9;
        		break;
            	case TEAMSTATE_BAN:
            		this.listFocus = LISTCLICK_BAN;
                	this.listClicked[LISTCLICK_BAN] = teamBtn - 9;
        		break;
            	}
            break;
            }//end switch
        break;	//end team
        case 4:     //target
        	//update target list
        	updateTargetClassList();
        	
        	int targetBtn = GuiHelper.getButton(ID.Gui.ADMIRALDESK, 4, xClick, yClick);
        	switch (targetBtn)
        	{
            case 0:	//remove target
            	int clicked = this.listNum[LISTCLICK_TARGET]+this.listClicked[LISTCLICK_TARGET];
            	
            	if (clicked >= 0 && clicked < this.tarList.size())
            	{
            		String tarstr = this.tarList.get(clicked);
                	LogHelper.debug("DEBUG: desk: remove target class: "+tarstr);
                	//remove clicked target
    				CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetTarClass, tarstr));
            	}
            break;
            case 1: //target slot
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            	this.listClicked[LISTCLICK_TARGET] = targetBtn - 1;
            	getEntityByClick();
            break;
            }//end switch
        break;  //end target
        }//end switch
        
        syncTileEntityC2S();
	}
	
	@Override
	protected void mouseClickMove(int mx, int my, int button, long time)
	{
		super.mouseClickMove(mx, my, button, time);
		
		int dx = mx - this.lastXMouse;
		int dy = my - this.lastYMouse;
		this.lastXMouse = mx;
		this.lastYMouse = my;
		
		if (dx > 20 || dx < -20 || dy > 20 || dy < -20) return;
		
		int gx = (int) (mx * this.GuiScaleInv - this.guiLeft);
        int gy = (int) (my * this.GuiScaleInv - this.guiTop);
        
		//func: target list
		if (this.guiFunc == 4 || this.guiFunc == 2)
		{
			if (gx > 8 && gx < 117 && gy > 47 && gy < 154)
			{
				if (dx > 0)
				{
					this.mRotateY += dx * 3F;
				}
				if (dx < 0)
				{
					this.mRotateY += dx * 3F;
				}
				
				if (dy > 0)
				{
					this.mRotateX += dy * 2F;
					if(this.mRotateX > 90F) this.mRotateX = 90F;
				}
				if (dy < 0)
				{
					this.mRotateX += dy * 2F;
					if(this.mRotateX < -90F) this.mRotateX = -90F;
				}
			}
		}
	}
	
	private void setDeskFunction(int button)
	{
		if (button >= 0)
		{
			if(this.guiFunc != button+1)
			{
	    		this.guiFunc = button+1;
	    		LogHelper.debug("DEBUG: GUI click: desk: click function button");
	    	}
	    	else
	    	{
	    		this.guiFunc = 0;
	    	}
			
			syncTileEntityC2S();
		}
	}
	
	//client to server sync
	private void syncTileEntityC2S()
	{
		if (this.type == 0)
		{
			this.tile.setField(0, this.guiFunc);
			this.tile.setField(1, this.book_chapNum);
			this.tile.setField(2, this.book_pageNum);
			this.tile.setField(3, this.radar_zoomLv);
			this.tile.sendSyncPacketC2S();
		}
	}
	
	//draw light spot in radar screen
	private void drawRadarIcon()
	{
		if (this.capa != null)
		{
			List<Integer> ships = capa.getShipEIDList();
			
			//icon setting
			GlStateManager.pushAttrib();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			
			Entity ship;
			double ox = 0D;
			double oy = 0D;
			double oz = 0D;
			int dx = 0;
			int dy = 0;
			int dz = 0;
			int id = 0;
			this.shipList = new ArrayList();
			
			//set position
			if (this.type == 0)
			{
				ox = this.tile.getPos().getX();
				oy = this.tile.getPos().getY();
				oz = this.tile.getPos().getZ();
			}
			else
			{
				ox = this.player.posX;
				oy = this.player.posY;
				oz = this.player.posZ;
			}
			
			//for all ships in ship list
			for (int eid : ships)
			{
				ShipEntity getent = new ShipEntity();
				double px = -1;
				double py = -1;
				double pz = -1;
				
				//get ship position
				if (eid > 0)
				{
					ship = EntityHelper.getEntityByID(eid, 0, true);
					if (ship != null)
					{
						getent.setShip(ship);
						px = ship.posX;
						py = ship.posY;
						pz = ship.posZ;
					}
				}
				
				//draw ship icon on the radar
				if (py > 0)
				{
					//change ship position to radar position
					//zoom lv 0: 128x128: 1 pixel = 1 block
					//zoom lv 1: 64x64:   2 pixel = 1 block
					//zoom lv 2: 32x32:   4 pixel = 1 block
					px -= ox;
					py -= oy;
					pz -= oz;
					
					//get scale factor
					float radarScale = 1F;	//256x256: scale = 0.5 pixel = 1 block
					switch (this.radar_zoomLv)
					{
					case 1:	//64x64
						radarScale = 2;
					break;
					case 2:	//16x16
						radarScale = 4;
					break;
					}
					
					//scale distance
					px *= radarScale;
					py *= radarScale;
					pz *= radarScale;
					
					//radar display distance limit = 64 pixels
					if ((int)px > 64) px = 64;
					if ((int)px < -64) px = -64;
					if ((int)pz > 64) pz = 64;
					if ((int)pz < -64) pz = -64;
					
					//add ship to shiplist
					getent.pixelx = guiLeft+69+px;
					getent.pixely = py;
					getent.pixelz = guiTop+88+pz;
					this.shipList.add(getent);
					
					//select icon
					int sIcon = 0;
					if (MathHelper.abs((int)px) > 48 || MathHelper.abs((int)pz) > 48)
					{
						sIcon = (int)(this.tickGUI * 0.125F + 6) % 8 * 3;
					}
					else if (MathHelper.abs((int)px) > 32 || MathHelper.abs((int)pz) > 32)
					{
						sIcon = (int)(this.tickGUI * 0.125F + 4) % 8 * 3;
					}
					else if (MathHelper.abs((int)px) > 16 || MathHelper.abs((int)pz) > 16)
					{
						sIcon = (int)(this.tickGUI * 0.125F + 2) % 8 * 3;
					}
					else
					{
						sIcon = (int)(this.tickGUI * 0.125F) % 8 * 3;
					}
					
					//draw icon by radar zoom level, radar center = [70,89]
					if (id == this.listNum[LISTCLICK_RADAR] + this.listClicked[LISTCLICK_RADAR])
					{
						drawTexturedModalRect(guiLeft+69+(int)px, guiTop+88+(int)pz, 0, 195, 3, 3);
					}
					else
					{
						drawTexturedModalRect(guiLeft+69+(int)px, guiTop+88+(int)pz, sIcon, 192, 3, 3);
					}
					id++;
				}//end y position > 0
			}//end for all ship
			
			GlStateManager.disableBlend();
			GlStateManager.popAttrib();
		}//end get player
		
	}//end draw radar icon
	
	//draw morale icon
	private void drawMoraleIcon()
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiNameIcon);
		
		//draw ship list
		if (this.shipList != null)
		{
			int texty = 37;
			
			for (int i = this.listNum[LISTCLICK_RADAR]; i < shipList.size() && i < this.listNum[LISTCLICK_RADAR] + 5; ++i) {
				//get ship
				ShipEntity s = shipList.get(i);
				
				if (s != null && s.ship instanceof BasicEntityShip)
				{
					BasicEntityShip s2 = (BasicEntityShip) s.ship;
					
					int ix = 44;
					
					switch (s2.getMoraleLevel())
					{
					case ID.Morale.Excited:
						ix = 0;
					break;
					case ID.Morale.Happy:
						ix = 11;
					break;
					case ID.Morale.Normal:
						ix = 22;
					break;
					case ID.Morale.Tired:
						ix = 33;
					break;
					}
    		        
    		        drawTexturedModalRect(guiLeft+237, guiTop+texty-1, ix, 240, 11, 11);
				}
				
				texty += 32;
			}
		}
	}
	
	//draw ship text in radar screen
	private void drawRadarText()
	{
		String str, str2;
		ShipEntity s;
		BasicEntityShip s2;
		int texty = 27;
		
		//draw button text
		int strlen = (int) (this.fontRendererObj.getStringWidth(StrGUI) * 0.5F);
		fontRendererObj.drawStringWithShadow(StrGUI, 32-strlen, 174, Enums.EnumColors.YELLOW.getValue());
		
		//draw ship list in radar
		for (int i = this.listNum[LISTCLICK_RADAR]; i < shipList.size() && i < this.listNum[LISTCLICK_RADAR] + 5; ++i) {
			//get ship position
			s = shipList.get(i);
			if (s != null && s.ship instanceof BasicEntityShip)
			{
				s2 = (BasicEntityShip) s.ship;
				
				//draw name
				fontRendererObj.drawString(s.name, 147, texty, Enums.EnumColors.WHITE.getValue());
				texty += 12;
				
				//draw pos
				str = "LV " + TextFormatting.YELLOW + s2.getLevel() + "   " +
						TextFormatting.GOLD + (int)s2.getHealth() + TextFormatting.RED + " / " + (int)s2.getMaxHealth();
				str2 = StrPos + " " + TextFormatting.YELLOW +
					  MathHelper.ceil(s.ship.posX) + " , " + 
					  MathHelper.ceil(s.ship.posZ) + "  " + TextFormatting.LIGHT_PURPLE +
					  StrHeight + " " + TextFormatting.YELLOW +
					  (int)(s.ship.posY);
				
				
				GlStateManager.pushMatrix();
				GlStateManager.scale(0.8F, 0.8F, 1F);
				fontRendererObj.drawString(str, 184, (int)(texty*1.25F), Enums.EnumColors.CYAN.getValue());
				texty += 9;
				fontRendererObj.drawString(str2, 184, (int)(texty*1.25F), Enums.EnumColors.PURPLE_LIGHT.getValue());
				texty += 11;
				GlStateManager.popMatrix();
			}
		}
	}
	
	//draw team text
	private void drawTeamPic()
	{
		int cirY = 0;
		//draw team select circle
    	if (this.listFocus == LISTCLICK_TEAM && this.listClicked[LISTCLICK_TEAM] > -1 && this.listClicked[LISTCLICK_TEAM] < 5)
    	{
    		cirY = 25 + this.listClicked[LISTCLICK_TEAM] * 32;
        	drawTexturedModalRect(guiLeft+142, guiTop+cirY, 0, 192, 108, 31);
    	}
    	//draw ally select circle
    	else if (this.listFocus == LISTCLICK_ALLY && this.listClicked[LISTCLICK_ALLY] > -1 && this.listClicked[LISTCLICK_ALLY] < 3)
    	{
    		cirY = 61 + this.listClicked[LISTCLICK_ALLY] * 31;
        	drawTexturedModalRect(guiLeft+6, guiTop+cirY, 109, 192, 129, 31);
    	}
    	//draw ban select circle
    	else if (this.listFocus == LISTCLICK_BAN && this.listClicked[LISTCLICK_BAN] > -1 && this.listClicked[LISTCLICK_BAN] < 3)
    	{
    		cirY = 61 + this.listClicked[LISTCLICK_BAN] * 31;
        	drawTexturedModalRect(guiLeft+6, guiTop+cirY, 109, 192, 129, 31);
    	}

	}
	
	//draw team text
	private void drawTeamText()
	{
		//null check
		if (this.capa == null) return;
		
		String str = null;
		TeamData tdata = null;
		
		//draw team name and id
		if (this.capa.hasTeam())
		{
			tdata = this.capa.getPlayerTeamData(this.capa.getPlayerUID());
			
			if (tdata != null)
			{
				GlStateManager.pushMatrix();
				GlStateManager.scale(0.8F, 0.8F, 0.8F);
				//draw team id
				str = TextFormatting.GRAY + StrTeamID +":  "+
					  TextFormatting.YELLOW + this.capa.getPlayerUID() +" : "+
					  TextFormatting.LIGHT_PURPLE + tdata.getTeamLeaderName();
				fontRendererObj.drawString(str, 11, 34, 0);  //org pos: 11,42
				
				//draw team name
				str = TextFormatting.WHITE + tdata.getTeamName();
				fontRendererObj.drawSplitString(str, 11, 44, 160, 0);
				GlStateManager.popMatrix();
			}
		}
		
		//draw button text
		String strLT = null;
		String strLB = null;
		String strRT = null;
		String strRB = null;
		int colorLT = Enums.EnumColors.WHITE.getValue();
		int colorLB = colorLT;
		int colorRT = colorLT;
		int colorRB = colorLT;
		
		//get button string
		switch (this.teamState)
		{
		case TEAMSTATE_ALLY:
			if (this.tempCD > 0)
			{
				strLT = String.valueOf((int)(tempCD * 0.05F));
				colorLT = Enums.EnumColors.GRAY_LIGHT.getValue();
			}
			else
			{
				int clicki = -1;
				List tlist = null;
				
				//clicked team list
				clicki = listClicked[LISTCLICK_TEAM] + this.listNum[LISTCLICK_TEAM];
				tlist = this.capa.getPlayerTeamDataList();
				
				//get clicked team id
				if (this.listFocus == LISTCLICK_TEAM && tlist != null && clicki >= 0 && clicki < tlist.size())
				{
					TeamData getd = (TeamData) tlist.get(clicki);
					
					if (getd != null)
					{
						//is ally, show break button
						if (this.capa.getPlayerUID() != getd.getTeamID() &&
							this.capa.isTeamAlly(getd.getTeamID()))
						{
							strLT = StrBreak;
							colorLT = Enums.EnumColors.YELLOW.getValue();
						}
						//not ally, show ally button
						else
						{
							strLT = StrAlly;
							colorLT = Enums.EnumColors.CYAN.getValue();
						}
					}
				}
				//clicked ally list?
				else if (this.listFocus == LISTCLICK_ALLY)
				{
					clicki = listClicked[LISTCLICK_ALLY] + this.listNum[LISTCLICK_ALLY];
					tlist = this.capa.getPlayerTeamAllyList();
					
					//has clicked ally
					if (tlist != null && clicki >= 0 && clicki < tlist.size())
					{
						strLT = StrBreak;
						colorLT = Enums.EnumColors.YELLOW.getValue();
					}
				}
			}//end btn cd
			
			strLB = StrOK;
			colorLB = Enums.EnumColors.WHITE.getValue();
		break;
		case TEAMSTATE_BAN:
			if (this.tempCD > 0)
			{
				strLT = String.valueOf((int)(tempCD * 0.05F));
				colorLT = Enums.EnumColors.GRAY_LIGHT.getValue();
			}
			else
			{
				int clicki2 = -1;
				List tlist2 = null;
				
				//clicked team list
				clicki2 = listClicked[LISTCLICK_TEAM] + this.listNum[LISTCLICK_TEAM];
				tlist2 = this.capa.getPlayerTeamDataList();
				
				//get clicked team id
				if (this.listFocus == LISTCLICK_TEAM && tlist2 != null && clicki2 >= 0 && clicki2 < tlist2.size())
				{
					TeamData getd = (TeamData) tlist2.get(clicki2);
					
					if (getd != null)
					{
						//is banned, show truce button
						if (this.capa.getPlayerUID() != getd.getTeamID() &&
							this.capa.isTeamBanned(getd.getTeamID()))
						{
							strLT = StrUnban;
							colorLT = Enums.EnumColors.CYAN.getValue();
						}
						//not banned, show battle button
						else
						{
							strLT = StrBan;
							colorLT = Enums.EnumColors.YELLOW.getValue();
						}
					}
				}
				//clicked ban list?
				else if (this.listFocus == LISTCLICK_BAN)
				{
					clicki2 = listClicked[LISTCLICK_BAN] + this.listNum[LISTCLICK_BAN];
					tlist2 = this.capa.getPlayerTeamBannedList();
					
					//has clicked ally
					if (tlist2 != null && clicki2 >= 0 && clicki2 < tlist2.size())
					{
						strLT = StrUnban;
						colorLT = Enums.EnumColors.CYAN.getValue();
					}
				}
			}//end btn cd
			
			strLB = StrOK;
			colorLB = Enums.EnumColors.WHITE.getValue();
			break;
		case TEAMSTATE_CREATE:
			str = TextFormatting.WHITE + StrTeamID +"  "+
				  TextFormatting.YELLOW + this.capa.getPlayerUID();  //use pUID for team ID
			fontRendererObj.drawString(str, 10, 43, 0);
			
			strLB = StrOK;
			colorLB = Enums.EnumColors.WHITE.getValue();
			strLT = StrCancel;
			colorLT = Enums.EnumColors.GRAY_LIGHT.getValue();
		break;
		case TEAMSTATE_RENAME:
			strLB = StrOK;
			colorLB = Enums.EnumColors.WHITE.getValue();
			strLT = StrCancel;
			colorLT = Enums.EnumColors.GRAY_LIGHT.getValue();
		break;
		default:  //0: main state
			//in team
			if (this.capa.hasTeam())
			{
				strLT = StrAllyList;
				colorLT = Enums.EnumColors.CYAN.getValue();
				strLB = StrBanList;
				colorLB = Enums.EnumColors.YELLOW.getValue();
				
				if (this.tempCD > 0)
				{
					strRT = String.valueOf((int)(tempCD * 0.05F));
					colorRT = Enums.EnumColors.GRAY_LIGHT.getValue();
				}
				else
				{
					strRT = StrRename;
					colorRT = Enums.EnumColors.WHITE.getValue();
				}
				
				if (this.capa.getPlayerTeamCooldown() > 0)
				{
					strRB = String.valueOf(this.capa.getPlayerTeamCooldownInSec());
					colorRB = Enums.EnumColors.GRAY_LIGHT.getValue();
				}
				else
				{
					strRB = StrDisband;
					colorRB = Enums.EnumColors.GRAY_DARK.getValue();
				}
			}
			//no team
			else
			{
				if (this.capa.getPlayerTeamCooldown() > 0)
				{
					strRB = String.valueOf(this.capa.getPlayerTeamCooldownInSec());
					colorRB = Enums.EnumColors.GRAY_LIGHT.getValue();
				}
				else
				{
					strRB = StrCreate;
					colorRB = Enums.EnumColors.CYAN.getValue();
				}
			}
		break;
		}//end switch
		
		//draw button string
		int strlen = (int) (this.fontRendererObj.getStringWidth(strLT) * 0.5F);
		fontRendererObj.drawString(strLT, 31-strlen, 160, colorLT);

		strlen = (int) (this.fontRendererObj.getStringWidth(strLB) * 0.5F);
		fontRendererObj.drawString(strLB, 31-strlen, 174, colorLB);		

		strlen = (int) (this.fontRendererObj.getStringWidth(strRT) * 0.5F);
		fontRendererObj.drawString(strRT, 110-strlen, 160, colorRT);

		strlen = (int) (this.fontRendererObj.getStringWidth(strRB) * 0.5F);
		fontRendererObj.drawString(strRB, 110-strlen, 174, colorRB);		
		
		//draw team list
		List<TeamData> tlist = this.capa.getPlayerTeamDataList();
		int texty = 33;
		
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.8F, 0.8F, 0.8F);
		for (int i = this.listNum[LISTCLICK_TEAM]; i < tlist.size() && i < this.listNum[LISTCLICK_TEAM] + 5; ++i)
		{
			//get team data
			TeamData tdata2 = tlist.get(i);
			
			if (tdata2 != null)
			{
				//get ally string
				String allyInfo = TextFormatting.WHITE +"("+ StrNeutral +")";
				if (this.capa.getPlayerUID() == tdata2.getTeamID())
				{
					allyInfo = TextFormatting.GOLD +"("+ StrBelong +")";
				}
				else if (this.capa.isTeamAlly(tdata2.getTeamID()))
				{
					allyInfo = TextFormatting.AQUA +"("+ StrAllied +")";
				}
				else if (this.capa.isTeamBanned(tdata2.getTeamID()))
				{
					allyInfo = TextFormatting.RED +"("+ StrHostile +")";
				}
				
				//draw info
				str = TextFormatting.YELLOW +""+ tdata2.getTeamID() +" : "+
					  TextFormatting.LIGHT_PURPLE + tdata2.getTeamLeaderName() +"  "+
					  allyInfo;
				//org pos: 146, texty
				fontRendererObj.drawString(str, 181, texty, Enums.EnumColors.WHITE.getValue());
				texty += 9;
				
				//draw name drawSplitString
				fontRendererObj.drawSplitString(tdata2.getTeamName(), 181, texty, 132, Enums.EnumColors.WHITE.getValue());
				texty += 31;
			}
			//get null team data, draw space to guarantee order
			else
			{
				texty += 40;
			}
		}//end draw team list
		GlStateManager.popMatrix();
		
		//draw ally or ban list
		List<Integer> tlist3 = null;
		texty = 79;
		int listID = LISTCLICK_ALLY;
		
		if (tdata != null)
		{
			if (this.teamState == TEAMSTATE_ALLY)
			{
				tlist3 = tdata.getTeamAllyList();
				listID = LISTCLICK_ALLY;
			}
			else if (this.teamState == TEAMSTATE_BAN)
			{
				tlist3 = tdata.getTeamBannedList();
				listID = LISTCLICK_BAN;
			}
			
			if (tlist3 != null)
			{
				GlStateManager.pushMatrix();
				GlStateManager.scale(0.8F, 0.8F, 0.8F);
				for (int i = this.listNum[listID]; i < tlist3.size() && i < this.listNum[listID] + 3; ++i)
				{
					//get team data
					int getid = tlist3.get(i);
					TeamData tdata3 = this.capa.getPlayerTeamData(getid);
					
					if (tdata3 != null)
					{
						//get ally string
						String allyInfo = TextFormatting.WHITE +"("+ StrNeutral +")";
						if (this.capa.getPlayerUID() == tdata3.getTeamID())
						{
							allyInfo = TextFormatting.GOLD +"("+ StrBelong +")";
						}
						else if (this.capa.isTeamAlly(tdata3.getTeamID()))
						{
							allyInfo = TextFormatting.AQUA +"("+ StrAllied +")";
						}
						else if (this.capa.isTeamBanned(tdata3.getTeamID()))
						{
							allyInfo = TextFormatting.RED +"("+ StrHostile +")";
						}
							
						//draw info
						str = TextFormatting.GRAY + StrTeamID +":  "+
							  TextFormatting.YELLOW + tdata3.getTeamID() +" : "+
							  TextFormatting.LIGHT_PURPLE + tdata3.getTeamLeaderName() +"  "+
							  allyInfo;
						//org pos: 146, texty
						fontRendererObj.drawString(str, 11, texty, 0);
						texty += 9;
						
						//draw name
						fontRendererObj.drawSplitString(tdata3.getTeamName(), 11, texty, 170, Enums.EnumColors.WHITE.getValue());
						texty += 30;
					}
					//get null team data, draw space to guarantee order
					else
					{
						texty += 39;
					}
				}//end for all team id
				GlStateManager.popMatrix();
			}
		}//end draw ally or ban list
	}
	
	//get clicked entity by entity simple name
	private void getEntityByClick()
	{
		final String tarStr;
		int clicked = this.listClicked[LISTCLICK_TARGET] + this.listNum[LISTCLICK_TARGET];
		
		//get target simple name
		if (clicked >= 0 && clicked < this.tarList.size())
		{
			tarStr = this.tarList.get(clicked);
			
			if (tarStr != null)
			{
				Map<Class<? extends Entity> ,String> entityMap = EntityList.CLASS_TO_NAME;
				
				if (entityMap != null)
				{
					entityMap.forEach((k, v) ->
					{
						if (tarStr.equals(k.getSimpleName()))
						{
							this.targetEntity = EntityList.createEntityByName(v, this.player.world);
							return;
						}
					});
				}
			}
		}
	}
	
	//draw target model
	private void drawTargetModel()
	{
		if (this.targetEntity != null)
		{
			RenderManager rm = Minecraft.getMinecraft().getRenderManager();
			int x = this.guiLeft + 72;
			int y = this.guiTop + 136;
			
			//set basic position and rotation
			GlStateManager.enableColorMaterial();
			GlStateManager.pushMatrix();
			
			GlStateManager.translate(x, y, 50F);
			GlStateManager.scale(-this.mScale, this.mScale, this.mScale);
			GlStateManager.rotate(180F, 0F, 0F, 1F);
			
			//set the light of model (face to player)
			GlStateManager.rotate(135F, 0F, 1F, 0F);
			RenderHelper.enableStandardItemLighting();
			GlStateManager.rotate(-135F, 0F, 1F, 0F);
			
			//set head look angle
			GlStateManager.rotate(this.mRotateY, 0F, 1F, 0F);
			GlStateManager.rotate(this.mRotateX, 1F, 0F, 0F);
			GlStateManager.translate(0F, this.targetEntity.getYOffset(), 0F);
			
			rm.setPlayerViewY(180.0F);
			rm.setRenderShadow(false);
			rm.doRenderEntity(this.targetEntity, 0D, 0D, 0D, 0F, 1F, false);
			rm.setRenderShadow(true);
			
	        GlStateManager.popMatrix();
	        RenderHelper.disableStandardItemLighting();
	        GlStateManager.disableRescaleNormal();
	        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.disableTexture2D();
	        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		}
	}//end target model
	
	//draw target class text in target screen
	private void drawTargetText()
	{
		//draw button text
		String str = StrRemove;
		int strlen = (int) (this.fontRendererObj.getStringWidth(str) * 0.5F);
		fontRendererObj.drawString(str, 31-strlen, 160, Enums.EnumColors.WHITE.getValue());
		
		//draw target list
		int texty = 28;
		for (int i = this.listNum[LISTCLICK_TARGET]; i < tarList.size() && i < this.listNum[LISTCLICK_TARGET] + 13; ++i)
		{
			//get ship position
			str = tarList.get(i);
			if (str != null)
			{
				//draw name
				fontRendererObj.drawString(str, 146, texty, Enums.EnumColors.WHITE.getValue());
				texty += 12;
			}
		}
	}
	
	//open ship GUI
	private void openShipGUI()
	{
		int clickid = this.listNum[LISTCLICK_RADAR] + this.listClicked[LISTCLICK_RADAR];
		
		if (clickid >= 0 && clickid < this.shipList.size())
		{
			Entity ent = this.shipList.get(clickid).ship;
			
			if (ent instanceof BasicEntityShip)
			{
				this.mc.player.closeScreen();
				//send GUI packet
				CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.OpenShipGUI, ent.getEntityId()));
			}
		}
	}
	
	//按鍵按下時執行此方法, 此方法等同key input event
	@Override
	protected void keyTyped(char input, int keyID) throws IOException
	{
        if(this.textField.textboxKeyTyped(input, keyID))
        {
            //test
        }
        else
        {
            super.keyTyped(input, keyID);
        }
    }
	
	/** btn: 0:left top, 1:left bottom, 2:right top, 3:right bottom*/
	private void handleClickTeamMain(int btn)
	{
		switch (btn)
		{
		case 0:  //left top btn: ally page
			if (this.capa.hasTeam())
			{
				this.teamState = TEAMSTATE_ALLY;
			}
		break;
		case 1:  //left bottom btn: ban page
			if (this.capa.hasTeam())
			{
				this.teamState = TEAMSTATE_BAN;
			}
		break;
		case 2:  //right top btn: rename page
			if (this.tempCD > 0)
			{
				break;
			}
			else if (this.capa.hasTeam())
			{
				this.teamState = TEAMSTATE_RENAME;
			}
		break;
		case 3:  //right bottom btn: disband or create team
//			LogHelper.info("DEBUG : desk: team cooldown "+this.capa.getTeamCooldown());
			if (this.capa.getPlayerTeamCooldown() <= 0)
			{
				//has team
				if (this.capa.hasTeam())
				{
					//disband team
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.Desk_Disband, 0));
					//return to main state
					this.teamState = TEAMSTATE_MAIN;
					this.tempCD = CLICKCD;
				}
				//no team
				else
				{
					this.teamState = TEAMSTATE_CREATE;
				}
			}
		break;
		}//end switch
	}//end btn in team main
	
	/** btn: 0:left top, 1:left bottom, 2:right top, 3:right bottom*/
	private void handleClickTeamAlly(int btn)
	{
		switch (btn)
		{
		case 0:  //left top btn: ally or break ally
			if (this.tempCD > 0)
			{
				break;
			}
			
			int clicki = -1;
			int getTeamID = 0;
			boolean isAlly = false;
			
			/** get clicked team id */
			//clicked team list
			clicki = listClicked[LISTCLICK_TEAM] + this.listNum[LISTCLICK_TEAM];
			List tlist = this.capa.getPlayerTeamDataList();
			
			if (this.listFocus == LISTCLICK_TEAM && tlist != null && clicki >= 0 && clicki < tlist.size())
			{
				TeamData getd = (TeamData) tlist.get(clicki);
				
				if (getd != null)
				{
					//get team id
					getTeamID = getd.getTeamID();
					
					//check is ally
					if (this.capa.isTeamAlly(getTeamID))
					{
						isAlly = true;
					}
				}
			}
			//clicked ally list
			else if (this.listFocus == LISTCLICK_ALLY)
			{
				clicki = listClicked[LISTCLICK_ALLY] + this.listNum[LISTCLICK_ALLY];
				tlist = this.capa.getPlayerTeamAllyList();
				
				if (tlist != null && clicki >= 0 && clicki < tlist.size())
				{
					//get team id
					getTeamID = (Integer) tlist.get(clicki);
					isAlly = true;
				}
			}
			
			/** send ally or break ally packet */
			if (getTeamID > 0)
			{
				//break ally
				if (isAlly)
				{
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.Desk_Break, getTeamID));
				}
				//ally
				else
				{
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.Desk_Ally, getTeamID));
				}
				this.tempCD = CLICKCD;
			}
			
		break;
		case 1:  //left bottom btn: OK
			//return to main state
			this.teamState = TEAMSTATE_MAIN;
		break;
		}//end switch
	}//end btn in team ally
	
	/** btn: 0:left top, 1:left bottom, 2:right top, 3:right bottom*/
	private void handleClickTeamCreate(int btn)
	{
		switch (btn)
		{
		case 0:  //left top btn: cancel
			this.teamState = TEAMSTATE_MAIN;  //return to main state
		break;
		case 1:  //left bottom btn: OK
			if (!this.capa.hasTeam())
			{
				String str = this.textField.getText();
				
				if (str != null && str.length() > 1)
				{
					LogHelper.debug("DEBUG: desk: create team: "+str);
					//change team id
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.Desk_Create, str));
					//return to main state
					this.teamState = TEAMSTATE_MAIN;
					this.tempCD = CLICKCD;
				}
			}
		break;
		}//end switch
	}//end btn in team create
	
	/** btn: 0:left top, 1:left bottom, 2:right top, 3:right bottom*/
	private void handleClickTeamRename(int btn)
	{
		switch (btn)
		{
		case 0:  //left top btn: cancel
			this.teamState = TEAMSTATE_MAIN;  //return to main state
		break;
		case 1:  //left bottom btn: OK
			if (this.capa.hasTeam())
			{
				String str = this.textField.getText();
				
				if (str != null && str.length() > 1)
				{
					LogHelper.debug("DEBUG: desk: rename team: "+str);
					//change team name
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.Desk_Rename, str));
					//return to main state
					this.teamState = TEAMSTATE_MAIN;
					this.tempCD = CLICKCD;
				}
			}
		break;
		}//end switch
	}//end btn in team rename
	
	/** btn: 0:left top, 1:left bottom, 2:right top, 3:right bottom*/
	private void handleClickTeamBan(int btn)
	{
		switch (btn)
		{
		case 0:  //left top btn:
			if (this.tempCD > 0)
			{
				break;
			}
			
			int clicki = -1;
			int getTeamID = 0;
			boolean isBanned = false;
			
			/** get clicked team id */
			//clicked team list
			clicki = listClicked[LISTCLICK_TEAM] + this.listNum[LISTCLICK_TEAM];
			List tlist = this.capa.getPlayerTeamDataList();
			
			if (this.listFocus == LISTCLICK_TEAM && tlist != null && clicki >= 0 && clicki < tlist.size())
			{
				TeamData getd = (TeamData) tlist.get(clicki);
				
				if (getd != null)
				{
					//get team id
					getTeamID = getd.getTeamID();
					
					//check is banned
					if (this.capa.isTeamBanned(getTeamID))
					{
						isBanned = true;
					}
				}
			}
			//clicked banned list
			else if (this.listFocus == LISTCLICK_BAN)
			{
				clicki = listClicked[LISTCLICK_BAN] + this.listNum[LISTCLICK_BAN];
				tlist = this.capa.getPlayerTeamBannedList();
				
				if(tlist != null && clicki >= 0 && clicki < tlist.size())
				{
					//get team id
					getTeamID = (Integer) tlist.get(clicki);
					isBanned = true;
				}
			}
			
			/** send ban or unban packet */
			if (getTeamID > 0)
			{
				//unban
				if (isBanned)
				{
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.Desk_Unban, getTeamID));
				}
				//ban
				else
				{
					CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.Desk_Ban, getTeamID));
				}
				this.tempCD = CLICKCD;
			}
		break;
		case 1:  //left bottom btn: return
			//return to main state
			this.teamState = TEAMSTATE_MAIN;
		break;
		}//end switch
	}//end btn in team ban
	
	//close gui if tile dead or too far away
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		
		this.textField.updateCursorCounter();
		
		if (this.type == 0 && this.tile == null)
		{
            this.mc.player.closeScreen();
        }
	}
	
	private void setShipModel(int chap, int page)
	{
		int classID = -1;
		String shipName = null;
		
		//clear mount
		if (this.shipModel != null)
		{
			this.shipModel.dismountRidingEntity();
	  		this.shipMount = null;
		}
		else
		{
			this.shipMount = null;
		}
		
		//get ship
		try
		{
			if (page > 0)
			{
				if (chap == 4)
				{
					classID = Values.ShipBookList.get(page - 1);
				}
				else if (chap == 5)
				{
					classID = Values.EnemyBookList.get(page - 1);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//get no ship
		if (classID < 0)
		{
			this.shipModel = null;
			return;
		}
		
		//get ship but not in colled list
		if (!EntityHelper.checkShipColled(classID, this.capa))
		{
			this.shipModel = null;
			return;
		}
		
		//get entity name
		shipName = ShipCalc.getEntityToSpawnName(classID);
		
		//set ship model
        if (EntityList.NAME_TO_CLASS.containsKey(shipName))
        {
            this.shipModel = (BasicEntityShip) EntityList.createEntityByName(shipName, player.world);
            
            if (this.shipModel != null)
            {
            	this.shipModel.setStateFlag(ID.F.NoFuel, false);
            	this.shipType = this.shipModel.getShipType();
    			this.shipClass = this.shipModel.getShipClass();
    			
    			this.iconXY = new int[2][3];
    			this.iconXY[0] = Values.ShipTypeIconMap.get((byte)this.shipType);
    			this.iconXY[1] = Values.ShipNameIconMap.get((short)this.shipClass);
            }
        }
	}
	
	private void setShipMount()
	{
		if (this.shipModel != null && this.shipModel.canSummonMounts())
		{
			//summon mount if emotion state >= equip00
  	  		if (this.shipModel.getStateEmotion(ID.S.State) >= ID.State.EQUIP00)
  	  		{
  	  			if (!this.shipModel.isRiding())
  	  			{
  	  				//summon mount entity
  	  				this.shipMount = this.shipModel.summonMountEntity();
  	  				this.shipMount.initAttrs(this.shipModel);
  	  				
  	  	  			//set riding entity
  	  	  			this.shipModel.startRiding(this.shipMount, true);
  	  			}
  	  		}
  	  		else
  	  		{
	  	  		//clear mount
	  	  		this.shipModel.dismountRidingEntity();
	  	  		this.shipMount = null;
  	  		}
		}
	}
	
	private void drawShipModel()
	{
		if (this.shipModel != null)
		{
			//draw background
			Minecraft.getMinecraft().getTextureManager().bindTexture(guiBook2);
			
			if (this.book_chapNum == 4)
			{  //shinkei
				drawTexturedModalRect(guiLeft+20, guiTop+48, 0, 0, 87, 125);
			}
			else
			{  //kanmusu
				drawTexturedModalRect(guiLeft+20, guiTop+48, 0, 125, 87, 125);
			}
	    	
	    	Minecraft.getMinecraft().getTextureManager().bindTexture(guiNameIcon);
	    	
	    	//draw name icon
        	try
        	{
        		drawTexturedModalRect(guiLeft+23, guiTop+53, this.iconXY[0][0], this.iconXY[0][1], 28, 28);

        		//use name icon file 0
        		if (iconXY[1][0] == 0)
        		{
        			drawTexturedModalRect(guiLeft+30, guiTop+94, this.iconXY[1][1], this.iconXY[1][2], 11, 59);
        		}
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}

        	//tick time
        	int modelTicking = this.tickGUI % 3;
        	if (modelTicking == 0)
        	{
        		this.shipModel.ticksExisted++;
            	if (this.shipModel.getAttackTick() > 0) this.shipModel.setAttackTick(this.shipModel.getAttackTick()-1);
            	
            	//set moving motion
            	if (this.shipModel.isSprinting())
            	{
            		this.shipModel.moveEntityWithHeading(1F, 0F);
            	}
            	else
            	{
            		this.shipModel.prevSwingProgress = 0F;
            		this.shipModel.swingProgress = 0F;
            		this.shipModel.prevLimbSwingAmount = 0F;
            		this.shipModel.limbSwingAmount = 0F;
            	}
            	
            	if (this.shipMount != null)
            	{
            		this.shipMount.ticksExisted++;
            		
                	if (this.shipMount.getAttackTick() > 0) this.shipMount.setAttackTick(this.shipMount.getAttackTick() - 1);
                	
                	//set mount moving motion
                	if (this.shipMount.isSprinting())
                	{
                		this.shipMount.moveEntityWithHeading(1F, 0F);
                	}
                	else
                	{
                		this.shipMount.prevSwingProgress = 0F;
                		this.shipMount.swingProgress = 0F;
                		this.shipMount.prevLimbSwingAmount = 0F;
                		this.shipMount.limbSwingAmount = 0F;
                	}
            	}
        	}
        	
        	RenderManager rm = Minecraft.getMinecraft().getRenderManager();
			int x = this.guiLeft + 72;
			int y = this.guiTop + 110;
			
			//set basic position and rotation
			GlStateManager.enableColorMaterial();
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y + this.mScale * 1.1F, 50F);
			GlStateManager.scale(-this.mScale, this.mScale, this.mScale);
			GlStateManager.rotate(180F, 0F, 0F, 1F);
			
			//set the light of model (face to player)
			GlStateManager.rotate(135F, 0F, 1F, 0F);
			RenderHelper.enableStandardItemLighting();
			GlStateManager.rotate(-135F, 0F, 1F, 0F);
			
			//提高旋轉中心
			GlStateManager.translate(0F, 0.7F, 0F);
			
			//set head look angle
			GlStateManager.rotate(this.mRotateY, 0F, 1F, 0F);
			GlStateManager.rotate(this.mRotateX, 1F, 0F, 0F);
			GlStateManager.translate(0F, this.shipModel.getYOffset() - 0.7F, 0F);
			
			//draw mount
			float partialTick = modelTicking * 0.33F;
			this.shipModel.rotationYawHead = 0F;
			rm.setPlayerViewY(180.0F);
			rm.setRenderShadow(false);
			
			if (this.shipMount != null)
			{
				float[] seatPos = this.shipMount.getSeatPos();
				//ship必須先畫才畫mounts
				GlStateManager.translate(seatPos[2], seatPos[1] + this.shipModel.getYOffset(), seatPos[0]);
				rm.doRenderEntity(this.shipModel, 0D, 0D, 0D, 0F, partialTick, false);
				GlStateManager.translate(-seatPos[2], -seatPos[1] - this.shipModel.getYOffset(), -seatPos[0]);
				rm.doRenderEntity(this.shipMount, 0D, 0D, 0D, 0F, partialTick, false);
			}
			else
			{
				rm.doRenderEntity(this.shipModel, 0D, 0D, 0D, 0F, partialTick, false);
			}
			
			rm.setRenderShadow(true);
			
	        GlStateManager.popMatrix();
	        RenderHelper.disableStandardItemLighting();
	        GlStateManager.disableRescaleNormal();
	        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
	        GlStateManager.disableTexture2D();
	        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		}
		//no ship
		else
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(guiBook2);
	    	drawTexturedModalRect(guiLeft+20, guiTop+48, 87, 0, 87, 125);
		}
	}
	
	private void updateTargetClassList()
	{
		this.tarList = new ArrayList<String>();
		
		if (this.capa != null)
		{
			HashMap<Integer, String> m = this.capa.getTargetClassMap();
			
			if (m != null)
			{
				m.forEach((k, v) ->
				{
					this.tarList.add(v);
				});
			}
		}
	}


}

