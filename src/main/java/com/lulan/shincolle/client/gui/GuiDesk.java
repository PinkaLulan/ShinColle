package com.lulan.shincolle.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.gui.inventory.ContainerDesk;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

/** admiral's desk
 * 
 *  function:
 *  0: no function
 *  1: radar
 *  2: book
 * 
 *
 */
public class GuiDesk extends GuiContainer {

	private static final ResourceLocation guiTexture = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDesk.png");
	private static final ResourceLocation guiRadar = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDeskRadar.png");
	private static final ResourceLocation guiBook = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDeskBook.png");
	private static final ResourceLocation guiTarget = new ResourceLocation(Reference.TEXTURES_GUI+"GuiDeskTarget.png");
	
	private TileEntityDesk tile;
	private int xClick, yClick, xMouse, yMouse;
	private int tickGUI, guiFunc;
	private String errorMsg;
	
	//player data
	EntityPlayer player;
	ExtendPlayerProps extProps;
	
	//radar
	private int radar_zoomLv;			//0:256x256 1:64x64 2:16x16
	private int radar_listNum;			//list index number
	private int radar_clicked;			//clicked ship
	private List<ShipEntity> shipList;
	
	//book
	private int book_chapNum;
	private int book_pageNum;
	
	//target list
	private int target_listNum;
	private int target_clicked;
	private List<String> tarList;
	
	
	//object: ship entity + pixel position
	private class ShipEntity {
		public Entity ship;
		public String name;
		public double pixelx;	//included guiLeft/guiTop distance
		public double pixely;
		public double pixelz;
		
		public void setShip(Entity ship) {
			this.ship = ship;
			//get name
			if(((EntityLiving) ship).getCustomNameTag() != null && ((EntityLiving) ship).getCustomNameTag().length() > 0) {
				name = ((EntityLiving) ship).getCustomNameTag();
			}
			else {
				name = I18n.format("entity.shincolle."+ship.getClass().getSimpleName()+".name");
			}
		}
	}
	
	
	public GuiDesk(InventoryPlayer par1, TileEntityDesk par2) {
		super(new ContainerDesk(par1, par2));
		this.xSize = 256;
		this.ySize = 192;
		
		this.tile = par2;
		this.tickGUI = 0;				//ticks in gui (not game tick)
		
		//player data
		player = ClientProxy.getClientPlayer();
		extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		//radar
		this.shipList = new ArrayList();
		this.radar_listNum = 0;
		this.radar_clicked = -1;
		
		//target
		this.tarList = new ArrayList();
		this.target_listNum = 0;
		this.target_clicked = -1;
	}
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		xMouse = mouseX;
		yMouse = mouseY;
		
		tickGUI += 1;
	}
	
	//draw tooltip
	private void handleHoveringText() {
		int mx = xMouse - guiLeft;
		int my = yMouse - guiTop;
		
		//draw ship name on light spot in radar function
		switch(this.guiFunc) {
		case 1:  /** radar */
			List list = new ArrayList();
			
			for(ShipEntity obj : this.shipList) {
				Entity ship = null;
				
				if(obj != null) {
					//mouse point at light spot icon
					if(xMouse < obj.pixelx+4 && xMouse > obj.pixelx-2 &&
					   yMouse < obj.pixelz+4 && yMouse > obj.pixelz-2) {
						ship = obj.ship;
					}
				}
				
				if(ship != null && ship instanceof BasicEntityShip) {
					String strName = obj.name;
	    			list.add(strName);  //add string to draw list
				}
			}//end for all obj in shipList
			
			//draw string
			drawHoveringText(list, xMouse-guiLeft, yMouse-guiTop, this.fontRendererObj);
			break;  //end radar
		case 2:     /** book */
			int getbtn = GuiHelper.getButton(ID.G.ADMIRALDESK, 2, mx, my);

			//get chap text
        	if(getbtn > 1 && getbtn < 9) {
        		getbtn -= 2;
        		String strChap = I18n.format("gui.shincolle:book.chap"+getbtn+".title");
        		List list2 = CalcHelper.stringConvNewlineToList(strChap);
        		int strLen = this.fontRendererObj.getStringWidth(strChap);
        		drawHoveringText(list2, mx-strLen-20, my, this.fontRendererObj);
        	}
        	else {
        		int id = GuiBook.getIndexID(this.book_chapNum, this.book_pageNum);
        		List<int[]> cont = Values.BookList.get(id);
        		
        		if(cont != null) {
        			for(int[] getc : cont) {
        				if(getc != null && getc.length == 5) {
        					int xa = getc[2] + GuiBook.Page0LX;              //at left page
        					if(getc[1] > 0) xa = getc[2] + GuiBook.Page0RX;  //at right page
        					int xb = xa + 16;
        					int ya = getc[3] + GuiBook.Page0LY;
        					int yb = ya + 16;
        					ItemStack item = GuiBook.getItemStackForIcon(getc[4]);
        					
        					if(mx > xa && mx < xb && my > ya && my < yb) {
        						this.renderToolTip(item, mx, my);
        						break;
        					}
        				}
        			}//end for book content
        		}//end if book content
        	}
        	
			break;  //end book
//		case 3:		//team
//			break;	//end team
//		case 4:		//target
//			
//			break;	//end target
		}//end func switch
	}

	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//draw ship data in radar function
		switch(this.guiFunc) {
		case 1:  //radar
			drawRadarText();
			break;  //end radar
		case 2:  //book
			GuiBook.drawBookContent(this, this.fontRendererObj, this.book_chapNum, this.book_pageNum);
			break;
		case 3:		//team
			break;	//end team
		case 4:		//target
			drawTargetText();
			break;	//end target
		}
		
		//畫出tooltip
		handleHoveringText();
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);	//RGBA
        Minecraft.getMinecraft().getTextureManager().bindTexture(guiTexture); //GUI圖檔
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
       
        //get new value
  		this.guiFunc = this.tile.guiFunc;
  		this.radar_zoomLv = this.tile.radar_zoomLv;
  		this.book_chapNum = this.tile.book_chap;
  		this.book_pageNum = this.tile.book_page;
      		
        //畫出功能按鈕
        switch(this.guiFunc) {
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
        
        //畫出功能介面
        switch(this.guiFunc) {
        case 1:		//radar
        	//background
        	Minecraft.getMinecraft().getTextureManager().bindTexture(guiRadar);
        	drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        	
        	//draw zoom level button
        	int texty = 192;
        	switch(this.radar_zoomLv) {
        	case 1:
        		texty = 200;
        		break;
        	case 2:
        		texty = 208;
        		break;
        	}
        	drawTexturedModalRect(guiLeft+9, guiTop+160, 24, texty, 44, 8);
        	
        	//draw ship clicked circle
        	if(this.radar_clicked > -1 && this.radar_clicked < 5) {
        		int cirY = 25 + this.radar_clicked * 32;
            	drawTexturedModalRect(guiLeft+142, guiTop+cirY, 68, 192, 108, 31);
        	}
        	
        	//draw radar ship icon
        	drawRadarIcon();
        	break;	//end radar
        case 2:		//book
        	//background
        	Minecraft.getMinecraft().getTextureManager().bindTexture(guiBook);
        	drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        	
        	//if mouse on page button, change button color
        	if(yMouse > guiTop+179 && yMouse < guiTop+193) {
        		if(xMouse > guiLeft+51 && xMouse < guiLeft+73) {
        			drawTexturedModalRect(guiLeft+53, guiTop+182, 0, 192, 18, 10);
        		}
        		else if(xMouse > guiLeft+173 && xMouse < guiLeft+195) {
        			drawTexturedModalRect(guiLeft+175, guiTop+182, 0, 202, 18, 10);
        		}
        	}
        	
        	break;  //end book
        case 3:		//team
        	break;	//end team
        case 4:		//target
        	//background
        	Minecraft.getMinecraft().getTextureManager().bindTexture(guiTarget);
        	drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        	
        	//draw ship clicked circle
        	if(this.target_clicked > -1 && this.target_clicked < 13) {
        		int cirY = 25 + this.target_clicked * 12;
            	drawTexturedModalRect(guiLeft+142, guiTop+cirY, 68, 192, 108, 31);
        	}
        	
        	//draw target model
        	drawTargetModel();
        	break;	//end target
        }
	}
	
	//mouse input
	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		
		int wheel = Mouse.getEventDWheel();
		if(wheel < 0) {			//scroll down
			if(this.shipList.size() > 0) {
				radar_clicked--;
				radar_listNum++;
				
				if(radar_listNum > this.shipList.size()-1) {
					radar_listNum = this.shipList.size()-1;
					radar_clicked++;
				}
				if(radar_listNum < 0) {
					radar_listNum = 0;
					radar_clicked++;  //捲過頭, 補回1
				}
			}
			
			if(this.tarList.size() > 0) {
				target_clicked--;
				target_listNum++;
				
				if(target_listNum > this.tarList.size()-1) {
					target_listNum = this.tarList.size()-1;
					target_clicked++;
				}
				if(target_listNum < 0) {
					target_listNum = 0;
					target_clicked++;  //捲過頭, 補回1
				}
			}
		}
		else if(wheel > 0) {	//scroll up
			radar_clicked++;
			target_clicked++;
			radar_listNum--;
			target_listNum--;
			
			if(radar_listNum < 0) {
				radar_listNum = 0;
				radar_clicked--;  //捲過頭, 補回1
			}
			
			if(target_listNum < 0) {
				target_listNum = 0;
				target_clicked--;  //捲過頭, 補回1
			}
		}
	}
	
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int mouseKey) {
        super.mouseClicked(posX, posY, mouseKey);
        LogHelper.info("DEBUG : click mouse "+mouseKey);
        //get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        
        //match all pages
        int getFunc = GuiHelper.getButton(ID.G.ADMIRALDESK, 0, xClick, yClick);
        setDeskFunction(getFunc);
        
        //match radar page
        switch(this.guiFunc) {
        case 1:     //radar
        	int radarBtn = GuiHelper.getButton(ID.G.ADMIRALDESK, 1, xClick, yClick);
        	switch(radarBtn) {
            case 0:	//radar scale
            	this.radar_zoomLv++;
            	if(this.radar_zoomLv > 2) this.radar_zoomLv = 0;
            	break;
            case 1: //ship slot 0~4
            case 2:
            case 3:
            case 4:
            case 5:
            	this.radar_clicked = radarBtn - 1;
            	break;
            }
        	break;  //end radar
        case 2:     //book
        	int getbtn = GuiHelper.getButton(ID.G.ADMIRALDESK, 2, xClick, yClick);
        	switch(getbtn) {
            case 0:	//left
            	this.book_pageNum--;
            	if(this.book_pageNum < 0) this.book_pageNum = 0;
            	LogHelper.info("DEBUG : desk: book page: "+book_pageNum);
            	break;
            case 1: //right
            	this.book_pageNum++;
            	if(this.book_pageNum > GuiBook.getMaxPageNumber(book_chapNum)) {
            		this.book_pageNum = GuiBook.getMaxPageNumber(book_chapNum);
            	}
            	LogHelper.info("DEBUG : desk: book page: "+book_pageNum);
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
            	LogHelper.info("DEBUG : desk: book chap: "+book_chapNum);
            	break;
            }
        	break;  //end book
        case 3:		//team
        	break;	//end team
        case 4:     //target
        	int targetBtn = GuiHelper.getButton(ID.G.ADMIRALDESK, 4, xClick, yClick);
        	switch(targetBtn) {
            case 0:	//remove target
            	///////////////////////NNNNNNNYYYYYYYYYYYYYYIIIIIIIIIIII
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
            	this.target_clicked = targetBtn - 1;
            	break;
            }
        	break;  //end target
        }
        
        syncTileEntityC2S();
	}
	
//	@Override
//	protected void mouseClickMove(int mx, int my, int button, long time) {
//		super.mouseClickMove(mx, my, button, time);
//	}
	
	private void setDeskFunction(int button) {
		if(button >= 0) {
			if(this.guiFunc != button+1) {
	    		this.guiFunc = button+1;
	    		LogHelper.info("DEBUG : GUI click: desk: click function button");
	    	}
	    	else {
	    		this.guiFunc = 0;
	    	}
			
			syncTileEntityC2S();
		}
	}
	
	//client to server sync
	private void syncTileEntityC2S() {
		this.tile.guiFunc = this.guiFunc;
		this.tile.radar_zoomLv = this.radar_zoomLv;
		this.tile.book_chap = this.book_chapNum;
		this.tile.book_page = this.book_pageNum;
		this.tile.sendSyncPacketC2S();
	}
	
	//draw light spot in radar screen
	private void drawRadarIcon() {
		if(extProps != null) {
			List<Integer> ships = extProps.getShipEIDList();
			
			//icon setting
	    	GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			Entity ship;
			double ox = this.tile.xCoord;
			double oy = this.tile.yCoord;
			double oz = this.tile.zCoord;
			int dx = 0;
			int dy = 0;
			int dz = 0;
			int id = 0;
			this.shipList = new ArrayList();
			
			//for all ships in ship list
			for(int eid : ships) {
				ShipEntity getent = new ShipEntity();
				double px = -1;
				double py = -1;
				double pz = -1;
				
				//get ship position
				if(eid > 0) {
					ship = EntityHelper.getEntityByID(eid, 0, true);
					if(ship != null) {
						getent.setShip(ship);
						px = ship.posX;
						py = ship.posY;
						pz = ship.posZ;
					}
				}
				
				//draw ship icon on the radar
				if(py > 0) {
					//change ship position to radar position
					//zoom lv 0: 128x128: 1 pixel = 1 block
					//zoom lv 1: 64x64:   2 pixel = 1 block
					//zoom lv 2: 32x32:   4 pixel = 1 block
					px -= ox;
					py -= oy;
					pz -= oz;
					
					//get scale factor
					float radarScale = 1F;	//256x256: scale = 0.5 pixel = 1 block
					switch(this.radar_zoomLv) {
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
					if((int)px > 64) px = 64;
					if((int)px < -64) px = -64;
					if((int)pz > 64) pz = 64;
					if((int)pz < -64) pz = -64;
					
					//add ship to shiplist
					getent.pixelx = guiLeft+69+px;
					getent.pixely = py;
					getent.pixelz = guiTop+88+pz;
					this.shipList.add(getent);
					
					//select icon
					int sIcon = 0;
					if(MathHelper.abs_int((int)px) > 48 || MathHelper.abs_int((int)pz) > 48) {
						sIcon = (int)(this.tickGUI * 0.125F + 6) % 8 * 3;
					}
					else if(MathHelper.abs_int((int)px) > 32 || MathHelper.abs_int((int)pz) > 32) {
						sIcon = (int)(this.tickGUI * 0.125F + 4) % 8 * 3;
					}
					else if(MathHelper.abs_int((int)px) > 16 || MathHelper.abs_int((int)pz) > 16) {
						sIcon = (int)(this.tickGUI * 0.125F + 2) % 8 * 3;
					}
					else {
						sIcon = (int)(this.tickGUI * 0.125F) % 8 * 3;
					}
					
					//draw icon by radar zoom level, radar center = [70,89]
					if(id == this.radar_listNum + this.radar_clicked) {
						drawTexturedModalRect(guiLeft+69+(int)px, guiTop+88+(int)pz, 0, 195, 3, 3);
					}
					else {
						drawTexturedModalRect(guiLeft+69+(int)px, guiTop+88+(int)pz, sIcon, 192, 3, 3);
					}
					id++;
				}//end y position > 0
			}//end for all ship
			GL11.glDisable(GL11.GL_BLEND);
		}//end get player
	}//end draw radar icon
	
	//draw ship text in radar screen
	private void drawRadarText() {
		//draw ship list in radar
		String str;
		ShipEntity s;
		int texty = 31;
		
		for(int i = this.radar_listNum; i < shipList.size() && i < this.radar_listNum + 5; ++i) {
			//get ship position
			s = shipList.get(i);
			if(s != null && s.ship != null) {
				//draw name
				fontRendererObj.drawString(s.name, 146, texty, GuiHelper.pickColor(GuiHelper.pickColorName.WHITE.ordinal()));
				texty += 12;
				//draw pos
				str = I18n.format("gui.shincolle:radar.position") + " " + 
					  MathHelper.ceiling_double_int(s.ship.posX) + " , " + 
					  MathHelper.ceiling_double_int(s.ship.posZ) + "  " +
					  I18n.format("gui.shincolle:radar.height") + " " +
					  (int)(s.ship.posY);
				fontRendererObj.drawString(str, 146, texty, GuiHelper.pickColor(GuiHelper.pickColorName.YELLOW.ordinal()));
				texty += 20;
			}
		}
	}
	
	//draw target model
	private void drawTargetModel() {

	}//end target model
	
	//draw target class text in target screen
	private void drawTargetText() {
		//draw button text
		String str = I18n.format("gui.shincolle:target.remove");
		int strlen = (int) (this.fontRendererObj.getStringWidth(str) * 0.5F);
		fontRendererObj.drawString(str, 31-strlen, 160, GuiHelper.pickColor(GuiHelper.pickColorName.WHITE.ordinal()));
		
		//draw target list
		this.tarList = this.extProps.getTargetClassList();
		int texty = 28;
//		LogHelper.info("DEBUG : gui desk: get list "+tarList.size());
		for(int i = this.target_listNum; i < tarList.size() && i < this.target_listNum + 13; ++i) {
			//get ship position
			str = tarList.get(i);
			if(str != null) {
				//draw name
				fontRendererObj.drawString(str, 146, texty, GuiHelper.pickColor(GuiHelper.pickColorName.WHITE.ordinal()));
				texty += 12;
			}
		}
	}


}

