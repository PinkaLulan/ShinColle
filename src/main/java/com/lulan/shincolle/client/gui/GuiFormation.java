package com.lulan.shincolle.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.lulan.shincolle.client.gui.inventory.ContainerFormation;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.FormationHelper;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

/** Formation GUI
 *
 */
public class GuiFormation extends GuiContainer {

	private static final ResourceLocation guiFormat = new ResourceLocation(Reference.TEXTURES_GUI+"GuiFormation.png");
	
	private int xClick, yClick, xMouse, yMouse;
	private int tickGUI, tickTooltip, tickWaitSync;
	private int listClicked, teamClicked, formatClicked;
	private int strLen;
	private String attrAmmoL, attrAmmoH, attrAirL, attrAirH, attrDEF, attrMOV, attrMISS, attrDODGE,
				   attrCRI, attrDHIT, attrTHIT, attrAA, attrASM, attrTotalFP;
	private String strPos, strMOV, strMOVBuff, strErr01;
	private List mouseoverList;
	
	//player data
	EntityPlayer player;
	ExtendPlayerProps extProps;
	
	//animation
	private int[][] spotPos;		//formation position spot current [x, y][pos]
	private int[][] spotPosFinal;	//formation position spot final [x, y][pos]
	private float[] buffBar;		//formation buff bar length [length]
	private float[] buffBarFinal;	//formation buff bar final length [length]
	BasicEntityShip[] shipList;		//ship list
	String[] shipName;				//ship name
	
	
	public GuiFormation(InventoryPlayer par1) {
		super(new ContainerFormation(par1, ClientProxy.getClientPlayer()));
		this.xSize = 256;
		this.ySize = 192;
		
		this.tickGUI = 0;				//ticks in gui (not game tick)
		this.tickTooltip = 0;			//tooltip display time
		this.tickWaitSync = 0;			//wait time for sync packet
		
		//player data
		this.player = ClientProxy.getClientPlayer();
		this.extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
		//formation spot init
		this.spotPos = new int[2][6];
		this.spotPosFinal = new int[2][6];
		this.buffBar = new float[13];
		this.buffBarFinal = new float[13];
		this.shipName = new String[6];
		
		for(int i = 0; i < 6; i++) {
			this.spotPos[0][i] = 25;
			this.spotPos[1][i] = 25;
			this.spotPosFinal[0][i] = 25;
			this.spotPosFinal[1][i] = 25;
		}
		
		//buff bar init
		for(int i = 0; i < 13; i++) {
			this.buffBar[i] = 0F;
			this.buffBarFinal[i] = 0F;
		}
				
		//other var
		this.listClicked = 0;
		
		if(this.extProps != null) {
			this.teamClicked = this.extProps.getPointerTeamID();
			this.formatClicked = this.extProps.getFormatIDCurrentTeam();
			setShipList(this.teamClicked);
			setFormationSpotPos(this.formatClicked);
			setFormationBuffBar(this.formatClicked, this.listClicked);
			setShipName();
		}
		
		//string
		attrAmmoL = EnumChatFormatting.RED+I18n.format("gui.shincolle:firepower1");
		attrAmmoH = EnumChatFormatting.GREEN+I18n.format("gui.shincolle:torpedo");
		attrAirL = EnumChatFormatting.RED+I18n.format("gui.shincolle:airfirepower");
		attrAirH = EnumChatFormatting.GREEN+I18n.format("gui.shincolle:airtorpedo");
		attrDEF = EnumChatFormatting.WHITE+I18n.format("gui.shincolle:armor");
		attrMOV = EnumChatFormatting.GRAY+I18n.format("gui.shincolle:movespeed");
		attrMISS = EnumChatFormatting.RED+I18n.format("gui.shincolle:missreduce");
		attrDODGE = EnumChatFormatting.GOLD+I18n.format("gui.shincolle:dodge");
		attrCRI = EnumChatFormatting.AQUA+I18n.format("gui.shincolle:critical");
		attrDHIT = EnumChatFormatting.YELLOW+I18n.format("gui.shincolle:doublehit");
		attrTHIT = EnumChatFormatting.GOLD+I18n.format("gui.shincolle:triplehit");
		attrAA = EnumChatFormatting.YELLOW+I18n.format("gui.shincolle:antiair");
		attrASM = EnumChatFormatting.AQUA+I18n.format("gui.shincolle:antiss");
		attrTotalFP = EnumChatFormatting.LIGHT_PURPLE+I18n.format("gui.shincolle:formation.totalfirepower");
		
		strPos = I18n.format("gui.shincolle:formation.position");
		strErr01 = I18n.format("gui.shincolle:formation.notenough");
		
		mouseoverList = new ArrayList();
	}
	
	@Override
	public void initGui() {
        super.initGui();
        
        //get max string length
		strLen = fontRendererObj.getStringWidth(attrAmmoL);
		int temp = fontRendererObj.getStringWidth(attrAmmoH);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrAirL);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrAirH);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrAA);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrASM);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrTotalFP);
		if(temp > strLen) strLen = temp;
    }
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		//update GUI var
		xMouse = mouseX;
		yMouse = mouseY;
		
		this.tickGUI++;
		
		if(this.tickTooltip > 0) this.tickTooltip--;
		if(this.tickWaitSync > 0) {
			this.tickWaitSync--;
			
			//update ship list
			if(this.tickWaitSync == 1) {
				setShipList(this.teamClicked);
				setShipName();
			}
		}
	}
	
	//draw tooltip
	private void handleHoveringText() {
		int mx = xMouse - guiLeft;
		int my = yMouse - guiTop;
		int len = 0;
		String str = null;
		float totalFPL = 0F;
		float totalFPH = 0F;
		float totalFPAL = 0F;
		float totalFPAH = 0F;
		float totalFPAA = 0F;
		float totalFPASM = 0F;
		
		//reset text
		mouseoverList.clear();
		
		//draw attributes
		if(shipList[listClicked] != null && shipList[listClicked].getStateMinor(ID.M.FormatType) > 0 &&
		   mx > 3 && mx < 138 && my > 43 && my < 145) {
			//right half
			if(mx < 73) {
				if(my < 59) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.ATK_L)+
						  "% : "+EnumChatFormatting.GRAY+
						  String.format("%.1f",shipList[listClicked].getStateFinalBU(ID.ATK))+
						  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
						  String.format("%.1f",shipList[listClicked].getStateFinal(ID.ATK));
					if(shipList[listClicked].getEffectFormation(ID.Formation.ATK_L) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 74) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.ATK_AL)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getStateFinalBU(ID.ATK_AL))+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getStateFinal(ID.ATK_AL));
					if(shipList[listClicked].getEffectFormation(ID.Formation.ATK_AL) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 89) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.DEF)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getStateFinalBU(ID.DEF))+"%"+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getStateFinal(ID.DEF))+"%";
					if(shipList[listClicked].getEffectFormation(ID.Formation.DEF) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 104) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.CRI)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getEffectEquipBU(ID.EF_CRI)*100F)+"%"+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getEffectEquip(ID.EF_CRI)*100F)+"%";
					if(shipList[listClicked].getEffectFormation(ID.Formation.CRI) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 119) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.DHIT)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getEffectEquipBU(ID.EF_DHIT)*100F)+"%"+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getEffectEquip(ID.EF_DHIT)*100F)+"%";
					if(shipList[listClicked].getEffectFormation(ID.Formation.DHIT) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 134) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.AA)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getEffectEquipBU(ID.EF_AA))+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getEffectEquip(ID.EF_AA));
					if(shipList[listClicked].getEffectFormation(ID.Formation.AA) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
			}
			//left half
			else {
				if(my < 59) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.ATK_H)+
						  "% : "+EnumChatFormatting.GRAY+
						  String.format("%.1f",shipList[listClicked].getStateFinalBU(ID.ATK_H))+
						  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
						  String.format("%.1f",shipList[listClicked].getStateFinal(ID.ATK_H));
					if(shipList[listClicked].getEffectFormation(ID.Formation.ATK_H) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 74) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.ATK_AH)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getStateFinalBU(ID.ATK_AH))+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getStateFinal(ID.ATK_AH));
					if(shipList[listClicked].getEffectFormation(ID.Formation.ATK_AH) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 89) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.MISS)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getEffectEquipBU(ID.EF_MISS))+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getEffectEquip(ID.EF_MISS));
					if(shipList[listClicked].getEffectFormation(ID.Formation.MISS) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 104) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.DODGE)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getEffectEquipBU(ID.EF_DODGE))+"%"+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getEffectEquip(ID.EF_DODGE))+"%";
					if(shipList[listClicked].getEffectFormation(ID.Formation.DODGE) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 119) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.THIT)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getEffectEquipBU(ID.EF_THIT)*100F)+"%"+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getEffectEquip(ID.EF_THIT)*100F)+"%";
					if(shipList[listClicked].getEffectFormation(ID.Formation.THIT) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
				else if(my < 134) {
					str = shipList[listClicked].getEffectFormation(ID.Formation.ASM)+
							  "% : "+EnumChatFormatting.GRAY+
							  String.format("%.1f",shipList[listClicked].getEffectEquipBU(ID.EF_ASM))+
							  EnumChatFormatting.WHITE+" -> "+EnumChatFormatting.YELLOW+
							  String.format("%.1f",shipList[listClicked].getEffectEquip(ID.EF_ASM));
					if(shipList[listClicked].getEffectFormation(ID.Formation.ASM) > 0F) {
						str = "+"+str;
					}
					mouseoverList.add(str);
				}
			}
			
			this.drawHoveringText(mouseoverList, mx, my+10, this.fontRendererObj);
		}
		
		//draw total damage
		if(mx > 45 && mx < 138 && my > 3 && my < 43) {
			//calc total firepower
			for(int i = 0; i < 6; i++) {
				if(this.shipList[i] != null) {
					totalFPL += this.shipList[i].getStateFinal(ID.ATK);
					totalFPH += this.shipList[i].getStateFinal(ID.ATK_H);
					totalFPAL += this.shipList[i].getStateFinal(ID.ATK_AL);
					totalFPAH += this.shipList[i].getStateFinal(ID.ATK_AH);
					totalFPAA += this.shipList[i].getEffectEquip(ID.EF_AA);
					totalFPASM += this.shipList[i].getEffectEquip(ID.EF_ASM);
				}
			}

			mouseoverList.clear();
			mouseoverList.add(attrTotalFP);
			mouseoverList.add(attrAmmoL);
			mouseoverList.add(attrAmmoH);
			mouseoverList.add(attrAirL);
			mouseoverList.add(attrAirH);
			mouseoverList.add(attrAA);
			mouseoverList.add(attrASM);
			
			this.drawHoveringText(mouseoverList, mx, my+10, this.fontRendererObj);
			
			mouseoverList.clear();
			mouseoverList.add("");
			str = EnumChatFormatting.RED+String.format("%.1f",totalFPL);
			mouseoverList.add(str);
			str = EnumChatFormatting.GREEN+String.format("%.1f",totalFPH);
			mouseoverList.add(str);
			str = EnumChatFormatting.RED+String.format("%.1f",totalFPAL);
			mouseoverList.add(str);
			str = EnumChatFormatting.GREEN+String.format("%.1f",totalFPAH);
			mouseoverList.add(str);
			str = EnumChatFormatting.YELLOW+String.format("%.1f",totalFPAA);
			mouseoverList.add(str);
			str = EnumChatFormatting.AQUA+String.format("%.1f",totalFPASM);
			mouseoverList.add(str);
			
			this.drawHoveringText(mouseoverList, mx+strLen+6, my+10, this.fontRendererObj);
		}
	}

	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		//draw button wait time
		if(this.tickWaitSync > 0) {
			String str = String.format("%.1f", this.tickWaitSync * 0.05F);
			fontRendererObj.drawString(str, 190, 171, Values.Color.YELLOW);
		}
		
		//draw string
		drawFormationText();
		
		//draw tooltip
		handleHoveringText();
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);	//RGBA

    	//background
    	Minecraft.getMinecraft().getTextureManager().bindTexture(guiFormat);
    	drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    	
    	//draw ship clicked circle
    	if(this.listClicked > -1 && this.listClicked < 6) {
    		int cirY = 5 + this.listClicked * 27;
        	drawTexturedModalRect(guiLeft+142, guiTop+cirY, 3, 192, 108, 27);
    	}
    	
    	//draw team button
    	int drawX = 18 + this.teamClicked * 12;  //draw = 18+x,167
    	int iconX = 111 + this.teamClicked * 9;  //icon = 111+x,207
    	drawTexturedModalRect(guiLeft+drawX, guiTop+167, iconX, 207, 9, 11);
    	
    	//draw formation button
    	drawX = 18 + this.formatClicked * 18;   //draw = 18+x,149
    	iconX = 111 + this.formatClicked * 15;  //icon = 111+x,192
    	drawTexturedModalRect(guiLeft+drawX, guiTop+149, iconX, 192, 15, 15);
    	
    	drawFormationPosSpot();
    	
    	drawFormationBuffBar();
	}
	
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int mouseKey) {
        super.mouseClicked(posX, posY, mouseKey);
        
        //get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;

    	int btn = GuiHelper.getButton(4, 0, xClick, yClick); //formation button array ID = 4
    	
    	switch(btn) {
        case 0:	 //formation button
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        	this.formatClicked = btn;
        	setFormationSpotPos(this.formatClicked);
        	
        	//set formation id
			CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetFormation, this.formatClicked));
			break;
        case 6:  //team button
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        	this.teamClicked = btn - 6;
        	//set current team id
			CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetShipTeamID, this.teamClicked, -1));
        	//get ship list
			setShipList(this.teamClicked);
    		this.formatClicked = this.extProps.getFormatID(this.teamClicked);
    		setFormationSpotPos(formatClicked);
    		setShipName();
        	break;
        case 15: //ship list
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        	this.listClicked = btn - 15;
        	break;
        case 21: //down button
        	if(this.tickWaitSync <= 0) changeFormationPos(false);
        	break;
        case 22: //up button
        	if(this.tickWaitSync <= 0) changeFormationPos(true);
        	break;
        }
    	
    	//set buff bar value
    	if(btn >= 0) {
			this.setFormationBuffBar(this.formatClicked, this.listClicked);
    	}
	}
	
	//send position change packet
	private void changeFormationPos(boolean isUp) {
		if(extProps != null && shipList != null) {
			int target = this.listClicked - 1;
			
			//go UP, swap clicked and clicked - 1
			if(isUp) {
				if(target < 0) target = 5;
			}
			//go DOWN, swap clicked and clicked + 1
			else {
				target = this.listClicked + 1;
				if(target > 5) target = 0;
			}
			
			//send swap packet
			CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SwapShip, this.teamClicked, this.listClicked, target));
		
			this.listClicked = target;
			this.tickWaitSync = 40;
		}
	}
	
	//draw formation light spot with animation
	private void drawFormationPosSpot() {
		//calc spot position
		int dist = 0;
		for(int j = 0; j < 6; j++) {
			if(spotPosFinal[0][j] != spotPos[0][j]) {
				dist = spotPosFinal[0][j] - spotPos[0][j];
				spotPos[0][j] = dist > 0 ? spotPos[0][j] + 1 : spotPos[0][j] - 1;
			}
			
			if(spotPosFinal[1][j] != spotPos[1][j]) {
				dist = spotPosFinal[1][j] - spotPos[1][j];
				spotPos[1][j] = dist > 0 ? spotPos[1][j] + 1 : spotPos[1][j] - 1;
			}
		}
		
    	//draw spot animation
    	int spotIconY = 192;
		for(int i = 0; i < 6; i++) {
			//set spot color
			if(i == this.listClicked) {
				spotIconY = 195;
			}
			else {
				spotIconY = 192;
			}
			
			drawTexturedModalRect(guiLeft+spotPos[0][i], guiTop+spotPos[1][i], 0, spotIconY, 3, 3);
		}
	}
	
	//draw formation buff color bar: iconY = PINK:220 BLUE:225 RED:230
	private void drawFormationBuffBar() {
		//draw basic bar
		drawTexturedModalRect(guiLeft+9, guiTop+54, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+73, guiTop+54, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+9, guiTop+69, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+73, guiTop+69, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+9, guiTop+84, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+73, guiTop+84, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+9, guiTop+99, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+73, guiTop+99, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+9, guiTop+114, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+73, guiTop+114, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+9, guiTop+129, 0, 220, 25, 4);
		drawTexturedModalRect(guiLeft+73, guiTop+129, 0, 220, 25, 4);
		
		//animate bar length++
		int len = 0;
		int bx = 0;		//bar pos x
		int by = 0;		//bar pos y
		int icony = 0;	//bar icon y
		
		for(int i = 0; i < 13; i++) {
			//bar length--
			if((int)this.buffBar[i] > (int)this.buffBarFinal[i]) {
				this.buffBar[i] = this.buffBar[i] - 0.3F;
			}
			//bar length++
			else if((int)this.buffBar[i] < (int)this.buffBarFinal[i]) {
				this.buffBar[i] = this.buffBar[i] + 0.3F;
			}
			
			//get draw position
			switch(i) {
			case 0:   //ATK_L
				bx = 34;
				by = 54;
				break;
			case 1:   //ATK_H
				bx = 98;
				by = 54;
				break;
			case 2:   //ATK_AL
				bx = 34;
				by = 69;
				break;
			case 3:   //ATK_AH
				bx = 98;
				by = 69;
				break;
			case 4:   //DEF
				bx = 34;
				by = 84;
				break;
			case 6:   //MISS
				bx = 98;
				by = 84;
				break;
			case 7:   //DODGE
				bx = 98;
				by = 99;
				break;
			case 8:   //CRI
				bx = 34;
				by = 99;
				break;
			case 9:   //DHIT
				bx = 34;
				by = 114;
				break;
			case 10:  //THIT
				bx = 98;
				by = 114;
				break;
			case 11:  //AA
				bx = 34;
				by = 129;
				break;
			case 12:  //ASM
				bx = 98;
				by = 129;
				break;
			}
			
			//draw red bar
			if(this.buffBar[i] > 0) {
				len = (int)this.buffBar[i];
				icony = 230;
				drawTexturedModalRect(guiLeft+bx, guiTop+by, 0, 230, len, 4);
			}
			//draw blue bar
			else if(this.buffBar[i] < 0) {
				len = (int)(-this.buffBar[i]);
				icony = 225;
				drawTexturedModalRect(guiLeft+bx-len, guiTop+by, 0, 225, len, 4);
			}
		}
	}
	
	//draw ship text in radar screen
	private void drawFormationText() {
		String str = null;
		int len = 0;
		float mov = 0F;
		float movBuff = 0F;
		
		//draw ship name
    	if(this.extProps != null) {
			//icon setting
    		GL11.glPushMatrix();
    		GL11.glScalef(0.75F, 0.75F, 0.75F);
    		
    		//draw ship list
    		if(this.shipList != null) {
    			int texty = 14;
    			
    			for(int i = 0; i < 6; i++) {
        			if(shipList[i] != null) {
        				//draw name
        				fontRendererObj.drawString(shipName[i], 195, texty, Values.Color.WHITE);
        				texty += 14;
        				//draw pos
        				str = EnumChatFormatting.AQUA + "LV " + EnumChatFormatting.YELLOW +
        					  shipList[i].getLevel() + "   " + EnumChatFormatting.GOLD +
        					  (int)shipList[i].getHealth() + " / " + EnumChatFormatting.RED +
        					  (int)shipList[i].getMaxHealth();
        				fontRendererObj.drawString(str, 195, texty, 0);
        				texty += 22;
        				//get mov speed
        				mov = shipList[i].getStateFinal(ID.MOV);
        			}
        			else {
        				str = EnumChatFormatting.DARK_RED+""+EnumChatFormatting.OBFUSCATED+I18n.format("gui.shincolle:formation.nosignal");
        				fontRendererObj.drawString(str, 195, texty, 0);
        				texty += 36;
        			}
        		}
    		}
    		
    		//draw formation name
    		str = EnumChatFormatting.YELLOW+I18n.format("gui.shincolle:formation.format"+this.formatClicked);
    		len = (int) (fontRendererObj.getStringWidth(str) * 0.5F);
    		fontRendererObj.drawString(str, 115-len, 18, Values.Color.WHITE);
    		
    		str = EnumChatFormatting.LIGHT_PURPLE+strPos+" "+EnumChatFormatting.WHITE+(this.listClicked+1);
    		len = (int) (fontRendererObj.getStringWidth(str) * 0.5F);
    		fontRendererObj.drawString(str, 115-len, 30, Values.Color.WHITE);
    		
    		//draw attribute text
    		fontRendererObj.drawStringWithShadow(attrAmmoL, 12, 60, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrAmmoH, 98, 60, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrAirL, 12, 80, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrAirH, 98, 80, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrDEF, 12, 100, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrMISS, 98, 100, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrCRI, 12, 120, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrDODGE, 98, 120, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrDHIT, 12, 140, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrTHIT, 98, 140, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrAA, 12, 160, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrASM, 98, 160, Values.Color.WHITE);
    		fontRendererObj.drawStringWithShadow(attrMOV, 12, 180, Values.Color.WHITE);
    		
    		//draw attribute value text
    		str = this.strMOVBuff + EnumChatFormatting.WHITE + " (" + this.strMOV + ")";
    		fontRendererObj.drawStringWithShadow(str, 97, 180, Values.Color.WHITE);
    		
    		GL11.glPopMatrix();
		}//draw ship name
	}
	
	private void setFormationSpotPos(int fid) {
		switch(fid) {
    	case 1:   //line ahead
    		spotPosFinal[0][0] = 25;  //pos 0
    		spotPosFinal[1][0] = 9;
    		spotPosFinal[0][1] = 25;  //pos 1
    		spotPosFinal[1][1] = 15;
    		spotPosFinal[0][2] = 25;  //pos 2
    		spotPosFinal[1][2] = 21;
    		spotPosFinal[0][3] = 25;  //pos 3
    		spotPosFinal[1][3] = 27;
    		spotPosFinal[0][4] = 25;  //pos 4
    		spotPosFinal[1][4] = 33;
    		spotPosFinal[0][5] = 25;  //pos 5
    		spotPosFinal[1][5] = 39;
    		break;
    	case 2:   //double line
    		spotPosFinal[0][0] = 21;  //pos 0
    		spotPosFinal[1][0] = 25;
    		spotPosFinal[0][1] = 29;  //pos 1
    		spotPosFinal[1][1] = 25;
    		spotPosFinal[0][2] = 21;  //pos 2
    		spotPosFinal[1][2] = 16;
    		spotPosFinal[0][3] = 29;  //pos 3
    		spotPosFinal[1][3] = 16;
    		spotPosFinal[0][4] = 21;  //pos 4
    		spotPosFinal[1][4] = 34;
    		spotPosFinal[0][5] = 29;  //pos 5
    		spotPosFinal[1][5] = 34;
    		break;
    	case 3:   //diamond
    		spotPosFinal[0][0] = 25;  //pos 0
    		spotPosFinal[1][0] = 29;
    		spotPosFinal[0][1] = 25;  //pos 1
    		spotPosFinal[1][1] = 15;
    		spotPosFinal[0][2] = 15;  //pos 2
    		spotPosFinal[1][2] = 26;
    		spotPosFinal[0][3] = 35;  //pos 3
    		spotPosFinal[1][3] = 26;
    		spotPosFinal[0][4] = 25;  //pos 4
    		spotPosFinal[1][4] = 36;
    		spotPosFinal[0][5] = 25;  //pos 5
    		spotPosFinal[1][5] = 23;
    		break;
    	case 4:   //echelon
    		spotPosFinal[0][0] = 40;  //pos 0
    		spotPosFinal[1][0] = 9;
    		spotPosFinal[0][1] = 34;  //pos 1
    		spotPosFinal[1][1] = 15;
    		spotPosFinal[0][2] = 28;  //pos 2
    		spotPosFinal[1][2] = 21;
    		spotPosFinal[0][3] = 22;  //pos 3
    		spotPosFinal[1][3] = 27;
    		spotPosFinal[0][4] = 16;  //pos 4
    		spotPosFinal[1][4] = 33;
    		spotPosFinal[0][5] = 10;  //pos 5
    		spotPosFinal[1][5] = 39;
    		break;
    	case 5:   //line abreast
    		spotPosFinal[0][0] = 40;  //pos 0
    		spotPosFinal[1][0] = 25;
    		spotPosFinal[0][1] = 34;  //pos 1
    		spotPosFinal[1][1] = 25;
    		spotPosFinal[0][2] = 28;  //pos 2
    		spotPosFinal[1][2] = 25;
    		spotPosFinal[0][3] = 22;  //pos 3
    		spotPosFinal[1][3] = 25;
    		spotPosFinal[0][4] = 16;  //pos 4
    		spotPosFinal[1][4] = 25;
    		spotPosFinal[0][5] = 10;  //pos 5
    		spotPosFinal[1][5] = 25;
    		break;
    	default:  //no formation
    		spotPosFinal[0][0] = 25;  //pos 0
    		spotPosFinal[1][0] = 25;
    		spotPosFinal[0][1] = 25;  //pos 1
    		spotPosFinal[1][1] = 25;
    		spotPosFinal[0][2] = 25;  //pos 2
    		spotPosFinal[1][2] = 25;
    		spotPosFinal[0][3] = 25;  //pos 3
    		spotPosFinal[1][3] = 25;
    		spotPosFinal[0][4] = 25;  //pos 4
    		spotPosFinal[1][4] = 25;
    		spotPosFinal[0][5] = 25;  //pos 5
    		spotPosFinal[1][5] = 25;
    		break;
    	}//end switch formation spot
	}
	
	private void setFormationBuffBar(int fid, int pos) {
		//get buff value
		float[] value = FormationHelper.getFormationBuffValue(fid, pos);
		
		if(value[ID.Formation.MOV] >= 0) {
			this.strMOVBuff = EnumChatFormatting.RED+String.format("%.2f", value[ID.Formation.MOV]);
		}
		else {
			this.strMOVBuff = EnumChatFormatting.AQUA+String.format("%.2f", value[ID.Formation.MOV]);
		}
		
		//calc bar length
		for(int i = 0; i < 13; i++) {
			//calc buff bar
			this.buffBarFinal[i] = getValueBarLength(value[i]);
		}
	}
	
	//formation buff value to bar length, 100% = 25 pixels
	private float getValueBarLength(float value) {
		return value * 0.25F;
	}
	
	private void setShipList(int tid) {
		try {
			//has formation, set ship pos by formatPos
			if(this.extProps.getFormatID(tid) > 0) {
				this.shipList = new BasicEntityShip[6];
				BasicEntityShip[] temp = this.extProps.getShipEntityAll(this.teamClicked);
				
				if(temp != null) {
					for(BasicEntityShip s : temp) {
						this.shipList[s.getStateMinor(ID.M.FormatPos)] = s;
					}
				}
			}
			//no formation
			else {
				this.shipList = this.extProps.getShipEntityAll(this.teamClicked);
			}
		}
		catch(Exception e) {
			LogHelper.info("EXCEPTION : formation GUI get ship fail: "+e);
		}
	}
	
	private void setShipName() {
		//reset mov string
		this.strMOV = "0";
		
		//get ship name
		if(shipList != null) {
			for(int i = 0; i < 6; i++) {
				if(shipList[i] != null) {
					//get name
        			if(shipList[i].getCustomNameTag() != null && shipList[i].getCustomNameTag().length() > 0) {
        				shipName[i] = shipList[i].getCustomNameTag();
        			}
        			else {
        				shipName[i] = I18n.format("entity.shincolle."+shipList[i].getClass().getSimpleName()+".name");
        			}
        			
        			//get MOV
        			this.strMOV = String.format("%.2f", shipList[i].getStateFinal(ID.MOV));
				}
				else {
					shipName[i] = null;
				}
    		}
		}
	}


}



