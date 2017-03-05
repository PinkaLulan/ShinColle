package com.lulan.shincolle.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.client.gui.inventory.ContainerFormation;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.FormationHelper;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

/** Formation GUI
 *
 */
public class GuiFormation extends GuiContainer
{

	private static final ResourceLocation guiFormat = new ResourceLocation(Reference.TEXTURES_GUI+"GuiFormation.png");
	private static final ResourceLocation guiNameIcon = new ResourceLocation(Reference.TEXTURES_GUI+"GuiNameIcon.png");
	
	private int xClick, yClick, xMouse, yMouse,
				tickGUI, tickTooltip, tickWaitSync,
				listClicked, teamClicked, formatClicked,
				strLen, unitNameState;
	private static String attrAmmoL, attrAmmoH, attrAirL, attrAirH, attrDEF, attrMOV, attrMISS,
						attrDODGE, attrCRI, attrDHIT, attrTHIT, attrAA, attrASM, attrTotalFP,
						strPos, strErr01, strRadar, strNoSig;
	private String strMOV, strMOVBuff, totalFPL, totalFPH, totalFPAL, totalFPAH, totalFPAA,
					totalFPASM, strUnitName;
	private List mouseoverList;
	private GuiTextField textField;

	//player data
	EntityPlayer player;
	CapaTeitoku capa;
	
	//animation
	private int[][] spotPos;		//formation position spot current [x, y][pos]
	private int[][] spotPosFinal;	//formation position spot final [x, y][pos]
	private float[] buffBar;		//formation buff bar length [length]
	private float[] buffBarFinal;	//formation buff bar final length [length]
	BasicEntityShip[] shipList;		//ship list
	String[] shipName;				//ship name
	
	
	public GuiFormation(EntityPlayer par1)
	{
		super(new ContainerFormation());
		this.player = par1;
		this.xSize = 256;
		this.ySize = 192;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		this.tickGUI = 0;				//ticks in gui (not game tick)
		this.tickTooltip = 0;			//tooltip display time
		this.tickWaitSync = 0;			//wait time for sync packet
		
		//player data
		this.capa = CapaTeitoku.getTeitokuCapability(this.player);
		
		//formation spot init
		this.spotPos = new int[2][6];
		this.spotPosFinal = new int[2][6];
		this.buffBar = new float[13];
		this.buffBarFinal = new float[13];
		this.shipName = new String[6];
		this.shipList = new BasicEntityShip[6];
		
		for (int i = 0; i < 6; i++)
		{
			this.spotPos[0][i] = 25;
			this.spotPos[1][i] = 25;
			this.spotPosFinal[0][i] = 25;
			this.spotPosFinal[1][i] = 25;
		}
		
		//buff bar init
		for (int i = 0; i < 13; i++)
		{
			this.buffBar[i] = 0F;
			this.buffBarFinal[i] = 0F;
		}
				
		//other var
		this.listClicked = 0;
		
		if (this.capa != null)
		{
			this.teamClicked = this.capa.getCurrentTeamID();
			this.formatClicked = this.capa.getFormatIDCurrentTeam();
			setShipList(this.teamClicked);
			setFormationSpotPos(this.formatClicked);
			setFormationBuffBar(this.formatClicked, this.listClicked);
			setShipName();
		}
		
		this.mouseoverList = new ArrayList();
		
        //textField: font, x, y, width, height
        this.textField = new GuiTextField(1, this.fontRendererObj, this.guiLeft + 100, this.guiTop + 180, 150, 12);
        this.textField.setTextColor(Enums.EnumColors.YELLOW.getValue());					//點選文字框時文字顏色
        this.textField.setDisabledTextColour(-1);			//無點選文字框時文字顏色
        this.textField.setEnableBackgroundDrawing(true);	//畫出文字框背景
        this.textField.setMaxStringLength(250);				//接受最大文字長度
        this.textField.setEnabled(false);
        this.textField.setFocused(false);
        this.unitNameState = -1;
		
		//string
		attrAmmoL = TextFormatting.RED+I18n.format("gui.shincolle:firepower1");
		attrAmmoH = TextFormatting.GREEN+I18n.format("gui.shincolle:torpedo");
		attrAirL = TextFormatting.RED+I18n.format("gui.shincolle:airfirepower");
		attrAirH = TextFormatting.GREEN+I18n.format("gui.shincolle:airtorpedo");
		attrDEF = TextFormatting.WHITE+I18n.format("gui.shincolle:armor");
		attrMOV = TextFormatting.GRAY+I18n.format("gui.shincolle:movespeed");
		attrMISS = TextFormatting.RED+I18n.format("gui.shincolle:missreduce");
		attrDODGE = TextFormatting.GOLD+I18n.format("gui.shincolle:dodge");
		attrCRI = TextFormatting.AQUA+I18n.format("gui.shincolle:critical");
		attrDHIT = TextFormatting.YELLOW+I18n.format("gui.shincolle:doublehit");
		attrTHIT = TextFormatting.GOLD+I18n.format("gui.shincolle:triplehit");
		attrAA = TextFormatting.YELLOW+I18n.format("gui.shincolle:antiair");
		attrASM = TextFormatting.AQUA+I18n.format("gui.shincolle:antiss");
		attrTotalFP = TextFormatting.LIGHT_PURPLE+I18n.format("gui.shincolle:formation.totalfirepower");
		strPos = I18n.format("gui.shincolle:formation.position");
		strErr01 = I18n.format("gui.shincolle:formation.notenough");
		strRadar = I18n.format("gui.shincolle:radar.tname");
		strNoSig = TextFormatting.DARK_RED+""+TextFormatting.OBFUSCATED+I18n.format("gui.shincolle:formation.nosignal");
		
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
		
		//update string value
		updateString();
	}
	
	//get new mouseX,Y and redraw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float parTick)
	{
		super.drawScreen(mouseX, mouseY, parTick);

		//update GUI var
		xMouse = mouseX;
		yMouse = mouseY;
		
		this.tickGUI++;
		
		if (this.tickTooltip > 0) this.tickTooltip--;

		if (this.tickWaitSync > 0)
		{
			this.tickWaitSync--;
			
			//update ship list
			if (this.tickWaitSync == 1)
			{
				setShipList(this.teamClicked);
				setShipName();
			}
		}
		
		//draw text field
        if (this.unitNameState >= 0)
        {
    		GlStateManager.pushMatrix();
    		GlStateManager.disableLighting();
    		GlStateManager.disableBlend();
        	this.textField.setEnabled(true);
        	this.textField.drawTextBox();
        	GlStateManager.popMatrix();
        }
        else
        {
        	this.textField.setEnabled(false);
        }
		
		//update string
		if (tickGUI % 32 == 0)
		{
			updateString();
		}
	}
	
	private String getAttributeString(byte formatID, byte stateID)
	{
		String str = shipList[listClicked].getEffectFormation(formatID) + "% : " + TextFormatting.GRAY;
		String valueOrg = "";
		String valueBuff = "";
		
		//add "%"
		switch (formatID)
		{
		case ID.Formation.DEF:
		case ID.Formation.DODGE:
			valueOrg = String.format("%.1f",shipList[listClicked].getStateFinalBU(stateID)) + "%";
			valueBuff = String.format("%.1f",shipList[listClicked].getStateFinal(stateID)) + "%";
		break;
		case ID.Formation.CRI:
		case ID.Formation.DHIT:
		case ID.Formation.THIT:
			valueOrg = String.format("%.1f",shipList[listClicked].getEffectEquipBU(stateID) * 100F) + "%";
			valueBuff = String.format("%.1f",shipList[listClicked].getEffectEquip(stateID) * 100F) + "%";
		break;
		default:
			valueOrg = String.format("%.1f",shipList[listClicked].getStateFinalBU(stateID));
			valueBuff = String.format("%.1f",shipList[listClicked].getStateFinal(stateID));
		break;
		}
		
		//combine string
		str = str + valueOrg + TextFormatting.WHITE + " -> " + TextFormatting.YELLOW + valueBuff;
		
		//if value is positive, add "+"
		if (shipList[listClicked].getEffectFormation(formatID) > 0F)
		{
			str = "+"+str;
		}
		
		return str;
	}
	
	private void updateString()
	{
		float floatFPL = 0F;
		float floatFPH = 0F;
		float floatFPAL = 0F;
		float floatFPAH = 0F;
		float floatFPAA = 0F;
		float floatFPASM = 0F;
		
		//calc float firepower
		for (int i = 0; i < 6; i++)
		{
			if (this.shipList[i] != null)
			{
				floatFPL += this.shipList[i].getStateFinal(ID.ATK);
				floatFPH += this.shipList[i].getStateFinal(ID.ATK_H);
				floatFPAL += this.shipList[i].getStateFinal(ID.ATK_AL);
				floatFPAH += this.shipList[i].getStateFinal(ID.ATK_AH);
				floatFPAA += this.shipList[i].getEffectEquip(ID.EquipEffect.AA);
				floatFPASM += this.shipList[i].getEffectEquip(ID.EquipEffect.ASM);
			}
		}
		
		this.totalFPL = TextFormatting.RED + String.format("%.1f",floatFPL);
		this.totalFPH = TextFormatting.GREEN + String.format("%.1f",floatFPH);
		this.totalFPAL = TextFormatting.RED + String.format("%.1f",floatFPAL);
		this.totalFPAH = TextFormatting.GREEN + String.format("%.1f",floatFPAH);
		this.totalFPAA = TextFormatting.YELLOW + String.format("%.1f",floatFPAA);
		this.totalFPASM = TextFormatting.AQUA + String.format("%.1f",floatFPASM);
		
		this.strUnitName = "\"" + this.capa.getUnitName(this.teamClicked) + "\"";
	}
	
	//draw tooltip
	private void handleHoveringText()
	{
		int mx = xMouse - guiLeft;
		int my = yMouse - guiTop;
		int len = 0;
		String str = null;
		
		//reset text
		mouseoverList.clear();
		
		//draw attributes
		if (shipList[listClicked] != null && shipList[listClicked].getStateMinor(ID.M.FormatType) > 0 &&
		   mx > 3 && mx < 138 && my > 43 && my < 145)
		{
			//right half
			if (mx < 73)
			{
				if (my < 59)
				{
					str = getAttributeString(ID.Formation.ATK_L, ID.ATK);
					mouseoverList.add(str);
				}
				else if (my < 74)
				{
					str = getAttributeString(ID.Formation.ATK_AL, ID.ATK_AL);
					mouseoverList.add(str);
				}
				else if (my < 89)
				{
					str = getAttributeString(ID.Formation.DEF, ID.DEF);
					mouseoverList.add(str);
				}
				else if (my < 104)
				{
					str = getAttributeString(ID.Formation.CRI, ID.EquipEffect.CRI);
					mouseoverList.add(str);
				}
				else if (my < 119)
				{
					str = getAttributeString(ID.Formation.DHIT, ID.EquipEffect.DHIT);
					mouseoverList.add(str);
				}
				else if (my < 134)
				{
					str = getAttributeString(ID.Formation.AA, ID.EquipEffect.AA);
					mouseoverList.add(str);
				}
			}
			//left half
			else
			{
				if (my < 59)
				{
					str = getAttributeString(ID.Formation.ATK_H, ID.ATK_H);
					mouseoverList.add(str);
				}
				else if (my < 74)
				{
					str = getAttributeString(ID.Formation.ATK_AH, ID.ATK_AH);
					mouseoverList.add(str);
				}
				else if (my < 89)
				{
					str = getAttributeString(ID.Formation.MISS, ID.EquipEffect.MISS);
					mouseoverList.add(str);
				}
				else if (my < 104)
				{
					str = getAttributeString(ID.Formation.DODGE, ID.EquipEffect.DODGE);
					mouseoverList.add(str);
				}
				else if (my < 119)
				{
					str = getAttributeString(ID.Formation.THIT, ID.EquipEffect.THIT);
					mouseoverList.add(str);
				}
				else if (my < 134)
				{
					str = getAttributeString(ID.Formation.ASM, ID.EquipEffect.ASM);
					mouseoverList.add(str);
				}
			}
			
			this.drawHoveringText(mouseoverList, mx, my+10, this.fontRendererObj);
		}
		
		//draw total damage
		if (mx > 45 && mx < 138 && my > 3 && my < 43)
		{
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
			mouseoverList.add(this.totalFPL);
			mouseoverList.add(this.totalFPH);
			mouseoverList.add(this.totalFPAL);
			mouseoverList.add(this.totalFPAH);
			mouseoverList.add(this.totalFPAA);
			mouseoverList.add(this.totalFPASM);
			
			this.drawHoveringText(mouseoverList, mx+strLen+6, my+10, this.fontRendererObj);
		}
	}

	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		//draw button wait time
		if (this.tickWaitSync > 0)
		{
			String str = String.format("%.1f", this.tickWaitSync * 0.05F);
			fontRendererObj.drawString(str, 190, 171, Enums.EnumColors.YELLOW.getValue());
		}
		
		//draw unit name
		if (this.strUnitName != null)
		{
			fontRendererObj.drawStringWithShadow(this.strUnitName, 100, 182, Enums.EnumColors.YELLOW.getValue());
		}
		
		//draw string
		drawFormationText();
		
		//draw tooltip
		handleHoveringText();
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3)
	{
		//reset color
		GlStateManager.color(1F, 1F, 1F, 1F);

    	//background
    	Minecraft.getMinecraft().getTextureManager().bindTexture(guiFormat);
    	drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    	
    	//draw ship clicked circle
    	if (this.listClicked > -1 && this.listClicked < 6)
    	{
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
    	
    	drawMoraleIcon();
	}
	
	//draw morale icon
	private void drawMoraleIcon()
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(guiNameIcon);
		
		//draw ship list
		if (this.shipList != null)
		{
			int texty = 9;
			
			for (int i = 0; i < 6; i++)
			{
    			if (shipList[i] != null)
    			{
    				int ix = 44;
    				
    				switch (EntityHelper.getMoraleLevel(shipList[i].getStateMinor(ID.M.Morale)))
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
    				}//end switch
    		        
    		        drawTexturedModalRect(guiLeft+145, guiTop+texty-1, ix, 240, 11, 11);
    			}
    			
    			texty += 27;
			}
		}
	}
	
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int mouseKey) throws IOException
	{
        super.mouseClicked(posX, posY, mouseKey);
        
        //set focus for textField
        this.textField.mouseClicked(posX, posY, mouseKey);
        
        //get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;

    	int btn = GuiHelper.getButton(4, 0, xClick, yClick); //formation button array ID = 4
    	
    	switch (btn)
    	{
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
        	if (this.unitNameState < 0)
        	{
            	this.teamClicked = btn - 6;
            	//set current team id
    			CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetShipTeamID, this.teamClicked, -1));
            	
    			//get ship list
    			setShipList(this.teamClicked);
        		this.formatClicked = this.capa.getFormatID(this.teamClicked);
        		setFormationSpotPos(formatClicked);
        		setShipName();
        		
        		//delay update again
    			this.tickWaitSync = 60;
        	}
        break;
        case 15: //ship list
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        	int oldClick = this.listClicked;
        	this.listClicked = btn - 15;
        	
        	//double click ship list will open ship GUI
        	if (oldClick == this.listClicked) openShipGUI();
        break;
        case 21: //down button
        	if(this.tickWaitSync <= 0) changeFormationPos(false);
        break;
        case 22: //up button
        	if(this.tickWaitSync <= 0) changeFormationPos(true);
        break;
        case 23: //rename team
        	//not naming state -> enter naming state
        	if (this.unitNameState < 0)
        	{
        		this.unitNameState = this.teamClicked;
        	}
        	//naming completed, send string packet
        	else
        	{
    			String str = this.textField.getText();
    			LogHelper.debug("DEBUG: send unit name: team: "+this.teamClicked+" name: "+str);
    			
    			//send string packet
    			CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetUnitName, str));
    			
    			this.unitNameState = -1;
        	}
        break;
        }//end switch
    	
    	//update value
    	if (btn >= 0)
    	{
			this.setFormationBuffBar(this.formatClicked, this.listClicked);
			this.updateString();
    	}
	}
	
	//open ship GUI
	private void openShipGUI()
	{
		if (this.shipList != null && this.shipList[listClicked] != null)
		{
			this.mc.player.closeScreen();
			CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.OpenShipGUI, shipList[listClicked].getEntityId()));
		}
	}
	
	//send position change packet
	private void changeFormationPos(boolean isUp)
	{
		if (this.capa != null && this.shipList != null)
		{
			int target = this.listClicked - 1;
			
			//go UP, swap clicked and clicked - 1
			if (isUp)
			{
				if (target < 0) target = 5;
			}
			//go DOWN, swap clicked and clicked + 1
			else
			{
				target = this.listClicked + 1;
				if (target > 5) target = 0;
			}
			
			//send swap packet
			CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SwapShip, this.teamClicked, this.listClicked, target));
		
			this.listClicked = target;
			this.tickWaitSync = 40;
		}
	}
	
	//draw formation light spot with animation
	private void drawFormationPosSpot()
	{
		//calc spot position
		int dist = 0;
		for (int j = 0; j < 6; j++)
		{
			if (spotPosFinal[0][j] != spotPos[0][j])
			{
				dist = spotPosFinal[0][j] - spotPos[0][j];
				spotPos[0][j] = dist > 0 ? spotPos[0][j] + 1 : spotPos[0][j] - 1;
			}
			
			if (spotPosFinal[1][j] != spotPos[1][j])
			{
				dist = spotPosFinal[1][j] - spotPos[1][j];
				spotPos[1][j] = dist > 0 ? spotPos[1][j] + 1 : spotPos[1][j] - 1;
			}
		}
		
    	//draw spot animation
    	int spotIconY = 192;
		for (int i = 0; i < 6; i++)
		{
			//set spot color
			if (i == this.listClicked)
			{
				spotIconY = 195;
			}
			else
			{
				spotIconY = 192;
			}
			
			drawTexturedModalRect(guiLeft+spotPos[0][i], guiTop+spotPos[1][i], 0, spotIconY, 3, 3);
		}
	}
	
	//draw formation buff color bar: iconY = PINK:220 BLUE:225 RED:230
	private void drawFormationBuffBar()
	{
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
		
		for (int i = 0; i < 13; i++)
		{
			//bar length--
			if ((int)this.buffBar[i] > (int)this.buffBarFinal[i])
			{
				this.buffBar[i] = this.buffBar[i] - 0.3F;
			}
			//bar length++
			else if ((int)this.buffBar[i] < (int)this.buffBarFinal[i])
			{
				this.buffBar[i] = this.buffBar[i] + 0.3F;
			}
			
			//get draw position
			switch (i)
			{
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
			if (this.buffBar[i] > 0)
			{
				len = (int)this.buffBar[i];
				icony = 230;
				drawTexturedModalRect(guiLeft+bx, guiTop+by, 0, 230, len, 4);
			}
			//draw blue bar
			else if (this.buffBar[i] < 0)
			{
				len = (int)(-this.buffBar[i]);
				icony = 225;
				drawTexturedModalRect(guiLeft+bx-len, guiTop+by, 0, 225, len, 4);
			}
		}
	}
	
	//draw ship text in radar screen
	private void drawFormationText()
	{
		String str = null;
		int len = 0;
		float mov = 0F;
		float movBuff = 0F;
		
		//draw button text
		len = (int) (this.fontRendererObj.getStringWidth(strRadar) * 0.5F);
		fontRendererObj.drawStringWithShadow(strRadar, 70-len, 182, Enums.EnumColors.YELLOW.getValue());
		
		//draw ship name
    	if (this.capa != null)
    	{
			//icon setting
    		GlStateManager.pushMatrix();
    		GlStateManager.scale(0.75F, 0.75F, 0.75F);
    		
    		//draw ship list
    		if (this.shipList != null)
    		{
    			int texty = 14;
    			
    			for (int i = 0; i < 6; i++)
    			{
        			if (shipList[i] != null)
        			{
        				//draw name
        				fontRendererObj.drawString(shipName[i], 210, texty, Enums.EnumColors.WHITE.getValue());
        				texty += 14;
        				
        				//draw pos
        				str = TextFormatting.AQUA + "LV " + TextFormatting.YELLOW +
        					  shipList[i].getLevel() + "   " + TextFormatting.GOLD +
        					  (int)shipList[i].getHealth() + " / " + TextFormatting.RED +
        					  (int)shipList[i].getMaxHealth();
        				fontRendererObj.drawString(str, 195, texty, 0);
        				texty += 22;
        				//get mov speed
        				mov = shipList[i].getStateFinal(ID.MOV);
        			}
        			else
        			{
        				str = strNoSig + TextFormatting.GRAY + " UID: " + this.capa.getSID(this.capa.getCurrentTeamID(), i);
        				fontRendererObj.drawString(str, 195, texty, 0);
        				texty += 36;
        			}
        		}
    		}
    		
    		//draw formation name
    		str = TextFormatting.YELLOW+I18n.format("gui.shincolle:formation.format"+this.formatClicked);
    		len = (int) (fontRendererObj.getStringWidth(str) * 0.5F);
    		fontRendererObj.drawString(str, 115-len, 18, Enums.EnumColors.WHITE.getValue());
    		
    		str = TextFormatting.LIGHT_PURPLE+strPos+" "+TextFormatting.WHITE+(this.listClicked+1);
    		len = (int) (fontRendererObj.getStringWidth(str) * 0.5F);
    		fontRendererObj.drawString(str, 115-len, 30, Enums.EnumColors.WHITE.getValue());
    		
    		//draw attribute text
    		fontRendererObj.drawStringWithShadow(attrAmmoL, 12, 60, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrAmmoH, 98, 60, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrAirL, 12, 80, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrAirH, 98, 80, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrDEF, 12, 100, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrMISS, 98, 100, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrCRI, 12, 120, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrDODGE, 98, 120, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrDHIT, 12, 140, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrTHIT, 98, 140, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrAA, 12, 160, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrASM, 98, 160, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrMOV, 12, 180, Enums.EnumColors.WHITE.getValue());
    		
    		//draw attribute value text
    		str = this.strMOVBuff + TextFormatting.WHITE + " (" + this.strMOV + ")";
    		fontRendererObj.drawStringWithShadow(str, 97, 180, Enums.EnumColors.WHITE.getValue());
    		
    		GlStateManager.popMatrix();
		}//draw ship name
	}
	
	private void setFormationSpotPos(int fid)
	{
		switch (fid)
		{
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
	
	private void setFormationBuffBar(int fid, int pos)
	{
		//get buff value
		float[] value = FormationHelper.getFormationBuffValue(fid, pos);
		
		if (value[ID.Formation.MOV] >= 0)
		{
			this.strMOVBuff = TextFormatting.RED+String.format("%.2f", value[ID.Formation.MOV]);
		}
		else
		{
			this.strMOVBuff = TextFormatting.AQUA+String.format("%.2f", value[ID.Formation.MOV]);
		}
		
		//calc bar length
		for (int i = 0; i < 13; i++)
		{
			//calc buff bar
			this.buffBarFinal[i] = getValueBarLength(value[i]);
		}
	}
	
	//formation buff value to bar length, 100% = 25 pixels
	private float getValueBarLength(float value)
	{
		return value * 0.25F;
	}
	
	private void setShipList(int tid)
	{
		try
		{
			//has formation, set ship pos by formatPos
			if (this.capa.getFormatID(tid) > 0)
			{
				this.shipList = new BasicEntityShip[6];
				BasicEntityShip[] temp = this.capa.getShipEntityAll(this.teamClicked);

				if (temp != null)
				{
					for (BasicEntityShip s : temp)
					{
						if (s != null) this.shipList[s.getStateMinor(ID.M.FormatPos)] = s;
					}
				}
			}
			//no formation
			else
			{
				this.shipList = this.capa.getShipEntityAll(this.teamClicked);
			}
		}
		catch (Exception e)
		{
			LogHelper.info("EXCEPTION: formation GUI get ship fail: "+e);
			e.printStackTrace();
		}
	}
	
	private void setShipName()
	{
		//reset mov string
		this.strMOV = "0";
		
		//get ship name
		if (shipList != null)
		{
			for (int i = 0; i < 6; i++)
			{
				if (shipList[i] != null)
				{
					//get name
        			if (shipList[i].getCustomNameTag() != null && shipList[i].getCustomNameTag().length() > 0) {
        				shipName[i] = shipList[i].getCustomNameTag();
        			}
        			else
        			{
        				shipName[i] = shipList[i].getName();
        			}
        			
        			//get MOV
        			this.strMOV = String.format("%.2f", shipList[i].getStateFinal(ID.MOV));
				}
				else
				{
					shipName[i] = null;
				}
    		}
		}
	}
	
	//按鍵按下時執行此方法, 此方法等同key input event
	@Override
	protected void keyTyped(char input, int keyID) throws IOException
	{
		//enter
		if (keyID == 28)
		{
			String str = this.textField.getText();
			LogHelper.debug("DEBUG: send unit name: team: "+this.teamClicked+" name: "+str);
			
			//send string packet
			CommonProxy.channelG.sendToServer(new C2SGUIPackets(player, C2SGUIPackets.PID.SetUnitName, str));
			
			this.unitNameState = -1;
		}
		//esc
		else if (keyID == 1)
		{
			this.unitNameState = -1;
		}
		
		/**
		 * key code:
		 * enter(28) = send string packet
		 * esc(1) = cancel naming
		 */
		//send key input to text field
		if (this.textField.textboxKeyTyped(input, keyID))
		{
		}
		else
		{
			super.keyTyped(input, keyID);
		}
    }
	
	//close gui if tile dead or too far away
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		this.textField.updateCursorCounter();
	}


}