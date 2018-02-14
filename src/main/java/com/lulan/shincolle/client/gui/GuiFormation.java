package com.lulan.shincolle.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.client.gui.inventory.ContainerFormation;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;
import com.lulan.shincolle.utility.BuffHelper;
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
	private static final ResourceLocation guiNameIcon = new ResourceLocation(Reference.TEXTURES_GUI+"GuiNameIcon0.png");
	
	private int xClick, yClick, xMouse, yMouse,
				tickGUI, tickTooltip, tickWaitSync,
				listClicked, teamClicked, formatClicked,
				strLen, unitNameState;
	private static int[] barRows = new int[] {54, 69, 84, 99, 114, 129};
	private static int[] barCols = new int[] {9, 52, 95};
	private static int barLength = 20;
	private static String attrHP, attrATKL, attrATKH, attrAIRL, attrAIRH, attrDEF, attrMOV,
						attrSPD, attrHIT, attrMISS, attrDODGE, attrCRI, attrDHIT, attrTHIT,
						attrAA, attrASM, attrXP, attrGRUDGE, attrAMMO, attrHPRES, attrKB,
						attrTotalFP, strPos, strErr01, strRadar, strNoSig;
	private String totalFPL, totalFPH, totalFPAL, totalFPAH, totalFPAA, totalFPASM, strUnitName;
	private List mouseoverList;
	private GuiTextField textField;
	private AttrsAdv attrs;
	private float[] unbuffedAttrs;

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
		
		this.tickGUI = 0;				//ticks in gui (not game tick)
		this.tickTooltip = 0;			//tooltip display time
		this.tickWaitSync = 0;			//wait time for sync packet
		
		//player data
		this.capa = CapaTeitoku.getTeitokuCapability(this.player);
		
		//formation spot init
		this.spotPos = new int[2][6];
		this.spotPosFinal = new int[2][6];
		this.buffBar = new float[Attrs.AttrsLength];
		this.buffBarFinal = new float[Attrs.AttrsLength];
		this.shipName = new String[6];
		this.shipList = new BasicEntityShip[6];
		this.attrs = null;
		this.unbuffedAttrs = null;
		
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
		
		//string
        attrHP = TextFormatting.YELLOW+I18n.format("gui.shincolle:hp");
		attrATKL = TextFormatting.RED+I18n.format("gui.shincolle:firepower1");
		attrATKH = TextFormatting.GREEN+I18n.format("gui.shincolle:torpedo");
		attrAIRL = TextFormatting.RED+I18n.format("gui.shincolle:airfirepower");
		attrAIRH = TextFormatting.GREEN+I18n.format("gui.shincolle:airtorpedo");
		attrDEF = TextFormatting.WHITE+I18n.format("gui.shincolle:armor");
		attrSPD = TextFormatting.WHITE+I18n.format("gui.shincolle:attackspeed");
		attrMOV = TextFormatting.GRAY+I18n.format("gui.shincolle:movespeed");
		attrHIT = TextFormatting.LIGHT_PURPLE+I18n.format("gui.shincolle:range");
		attrMISS = TextFormatting.RED+I18n.format("gui.shincolle:missreduce");
		attrDODGE = TextFormatting.GOLD+I18n.format("gui.shincolle:dodge");
		attrCRI = TextFormatting.AQUA+I18n.format("gui.shincolle:critical");
		attrDHIT = TextFormatting.YELLOW+I18n.format("gui.shincolle:doublehit");
		attrTHIT = TextFormatting.GOLD+I18n.format("gui.shincolle:triplehit");
		attrAA = TextFormatting.YELLOW+I18n.format("gui.shincolle:antiair");
		attrASM = TextFormatting.AQUA+I18n.format("gui.shincolle:antiss");
		attrXP = TextFormatting.GREEN+I18n.format("gui.shincolle:equip.xp");
		attrGRUDGE = TextFormatting.DARK_PURPLE+I18n.format("gui.shincolle:equip.grudge");
		attrAMMO = TextFormatting.DARK_AQUA+I18n.format("gui.shincolle:equip.ammo");
		attrHPRES = TextFormatting.DARK_GREEN+I18n.format("gui.shincolle:equip.hpres");
		attrKB = TextFormatting.YELLOW+I18n.format("gui.shincolle:equip.kb");
		attrTotalFP = TextFormatting.LIGHT_PURPLE+I18n.format("gui.shincolle:formation.totalfirepower");
		strPos = I18n.format("gui.shincolle:formation.position");
		strErr01 = I18n.format("gui.shincolle:formation.notenough");
		strRadar = I18n.format("gui.shincolle:radar.tname");
		strNoSig = TextFormatting.DARK_RED+""+TextFormatting.OBFUSCATED+I18n.format("gui.shincolle:formation.nosignal");
		
		//update string value
		updateString();
	}
	
	//有用到fontRendererObj的必須放在此init
	@Override
	public void initGui()
    {
		super.initGui();
		
        //textField: font, x, y, width, height
        this.textField = new GuiTextField(1, this.fontRendererObj, this.guiLeft + 100, this.guiTop + 180, 150, 12);
        this.textField.setTextColor(Enums.EnumColors.YELLOW.getValue());					//點選文字框時文字顏色
        this.textField.setDisabledTextColour(-1);			//無點選文字框時文字顏色
        this.textField.setEnableBackgroundDrawing(true);	//畫出文字框背景
        this.textField.setMaxStringLength(250);				//接受最大文字長度
        this.textField.setEnabled(false);
        this.textField.setFocused(false);
        this.unitNameState = -1;
        
		//get max string length
		strLen = fontRendererObj.getStringWidth(attrTotalFP);
		int temp = fontRendererObj.getStringWidth(attrATKL);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrATKH);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrAIRL);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrAIRH);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrAA);
		if(temp > strLen) strLen = temp;
		temp = fontRendererObj.getStringWidth(attrASM);
		if(temp > strLen) strLen = temp;
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
	
	private String getAttributeString(byte attrid)
	{
		String str = "";
		String valueOrg = "";
		String valueBuff = "";
		
		//null check
		if (this.attrs == null) return str;
		
		/*  multiplication (add "%")
		 *    ATK, DEF, SPD, CRI, DHIT, THIT, MISS, AA, ASM 
		 *  addition (without "%")
		 *    HP, MOV, HIT, DODGE, XP, GRUDGE, AMMO, HPRES
		 */
		switch (attrid)
		{
		case ID.Attrs.ATK_L:
		case ID.Attrs.ATK_H:
		case ID.Attrs.ATK_AL:
		case ID.Attrs.ATK_AH:
		case ID.Attrs.SPD:
		case ID.Attrs.AA:
		case ID.Attrs.ASM:
			str = String.format("%.0f", (this.attrs.getAttrsFormation(attrid) - 1F) * 100F) + "% : " + TextFormatting.GRAY;
			valueOrg = String.format("%.1f", this.unbuffedAttrs[attrid]);
			valueBuff = String.format("%.1f", this.attrs.getAttrsBuffed(attrid));
			if (attrs.getAttrsFormation(attrid) > 1F) str = "+"+str;
		break;
		case ID.Attrs.DEF:
		case ID.Attrs.CRI:
		case ID.Attrs.DHIT:
		case ID.Attrs.THIT:
		case ID.Attrs.MISS:
			str = String.format("%.0f", (this.attrs.getAttrsFormation(attrid) - 1F) * 100F) + "% : " + TextFormatting.GRAY;
			valueOrg = String.format("%.1f", this.unbuffedAttrs[attrid] * 100F) + "%";
			valueBuff = String.format("%.1f", this.attrs.getAttrsBuffed(attrid) * 100F) + "%";
			if (attrs.getAttrsFormation(attrid) > 1F) str = "+"+str;
		break;
		case ID.Attrs.HIT:
			str = String.format("%.2f", this.attrs.getAttrsFormation(attrid)) + " : " + TextFormatting.GRAY;
			valueOrg = String.format("%.2f", this.unbuffedAttrs[attrid]);
			valueBuff = String.format("%.2f", this.attrs.getAttrsBuffed(attrid));
			if (attrs.getAttrsFormation(attrid) > 0F) str = "+"+str;
		break;
		case ID.Attrs.MOV:
			float mov = this.attrs.getMinMOV() + this.attrs.getAttrsFormation(attrid) * (float)ConfigHandler.scaleShip[ID.AttrsBase.MOV];
			if (mov > (float)ConfigHandler.limitShipAttrs[ID.Attrs.MOV]) mov = (float)ConfigHandler.limitShipAttrs[ID.Attrs.MOV];
			else if (mov < 0F) mov = 0F;
			
			str = String.format("%.2f", this.attrs.getAttrsFormation(attrid)) + " : " + TextFormatting.GRAY;
			valueOrg = String.format("%.2f", this.unbuffedAttrs[attrid]);
			valueBuff = String.format("%.2f", mov);
			if (attrs.getAttrsFormation(attrid) > 0F) str = "+"+str;
		break;
		case ID.Attrs.DODGE:
			str = String.format("%.0f", this.attrs.getAttrsFormation(attrid) * 100F) + "% : " + TextFormatting.GRAY;
			valueOrg = String.format("%.1f", this.unbuffedAttrs[attrid] * 100F) + "%";
			valueBuff = String.format("%.1f", this.attrs.getAttrsBuffed(attrid) * 100F) + "%";
			if (attrs.getAttrsFormation(attrid) > 0F) str = "+"+str;
		break;
		case ID.Attrs.GRUDGE:
		case ID.Attrs.HPRES:
			str = String.format("%.0f", this.attrs.getAttrsFormation(attrid) * 100F) + "% : " + TextFormatting.GRAY;
			valueOrg = String.format("%.0f", (this.unbuffedAttrs[attrid] - 1F) * 100F) + "%";
			valueBuff = String.format("%.0f", (this.attrs.getAttrsBuffed(attrid) - 1F) * 100F) + "%";
			if (attrs.getAttrsFormation(attrid) > 0F) str = "+"+str;
		break;
		default:
			str = String.format("%.0f", this.attrs.getAttrsFormation(attrid) * 100F) + "% : " + TextFormatting.GRAY;
			valueOrg = String.format("%.0f", this.unbuffedAttrs[attrid] * 100F) + "%";
			valueBuff = String.format("%.0f", this.attrs.getAttrsBuffed(attrid) * 100F) + "%";
			if (attrs.getAttrsFormation(attrid) > 0F) str = "+"+str;
		break;
		}
		
		//combine string
		str = str + valueOrg + TextFormatting.WHITE + " -> " + TextFormatting.YELLOW + valueBuff;
		
		return str;
	}
	
	private void updateString()
	{
		//update formation attrs
		if (shipList[listClicked] != null)
		{
			this.attrs = (AttrsAdv) shipList[listClicked].getAttrs();
			//calc attrs before formation buff
			this.unbuffedAttrs = BuffHelper.calcAttrsWithoutBuff(this.attrs, 4);
		}
		else
		{
			this.attrs = null;
			this.unbuffedAttrs = Attrs.getResetZeroValue();
		}
				
		float floatFPL = 0F;
		float floatFPH = 0F;
		float floatFPAL = 0F;
		float floatFPAH = 0F;
		float floatFPAA = 0F;
		float floatFPASM = 0F;
		
		//update attrs
		for (int i = 0; i < 6; i++)
		{
			if (this.shipList[i] != null)
			{
				floatFPL += this.shipList[i].getAttrs().getAttrsBuffed(ID.Attrs.ATK_L);
				floatFPH += this.shipList[i].getAttrs().getAttrsBuffed(ID.Attrs.ATK_H);
				floatFPAL += this.shipList[i].getAttrs().getAttrsBuffed(ID.Attrs.ATK_AL);
				floatFPAH += this.shipList[i].getAttrs().getAttrsBuffed(ID.Attrs.ATK_AH);
				floatFPAA += this.shipList[i].getAttrs().getAttrsBuffed(ID.Attrs.AA);
				floatFPASM += this.shipList[i].getAttrs().getAttrsBuffed(ID.Attrs.ASM);
			}
		}
		
		this.totalFPL = TextFormatting.RED + String.format("%.1f",floatFPL);
		this.totalFPH = TextFormatting.GREEN + String.format("%.1f",floatFPH);
		this.totalFPAL = TextFormatting.RED + String.format("%.1f",floatFPAL);
		this.totalFPAH = TextFormatting.GREEN + String.format("%.1f",floatFPAH);
		this.totalFPAA = TextFormatting.YELLOW + String.format("%.1f",floatFPAA);
		this.totalFPASM = TextFormatting.AQUA + String.format("%.1f",floatFPASM);
		
		//update unit name
		this.strUnitName = "\"" + this.capa.getUnitName(this.teamClicked) + "\"";
	}
	
	//draw tooltip
	private void handleHoveringText()
	{
		int mx = xMouse - guiLeft;
		int my = yMouse - guiTop;
		int len = 0;
		int range = 5;
		String str = null;
		
		//reset text
		mouseoverList.clear();
		
		//draw attributes
		if (shipList[listClicked] != null && shipList[listClicked].getStateMinor(ID.M.FormatType) > 0 &&
		   mx > 3 && mx < 138 && my > 43 && my < 145)
		{
			//column 1
			if (mx < 51)
			{
				if (my < this.barRows[0] + range)
				{
					str = getAttributeString(ID.Attrs.ATK_L);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[1] + range)
				{
					str = getAttributeString(ID.Attrs.ATK_H);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[2] + range)
				{
					str = getAttributeString(ID.Attrs.ATK_AL);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[3] + range)
				{
					str = getAttributeString(ID.Attrs.ATK_AH);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[4] + range)
				{
					str = getAttributeString(ID.Attrs.SPD);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[5] + range)
				{
					str = getAttributeString(ID.Attrs.HIT);
					mouseoverList.add(str);
				}
			}
			//column 2
			else if (mx < 94)
			{
				if (my < this.barRows[0] + range)
				{
					str = getAttributeString(ID.Attrs.CRI);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[1] + range)
				{
					str = getAttributeString(ID.Attrs.DHIT);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[2] + range)
				{
					str = getAttributeString(ID.Attrs.THIT);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[3] + range)
				{
					str = getAttributeString(ID.Attrs.MISS);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[4] + range)
				{
					str = getAttributeString(ID.Attrs.AA);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[5] + range)
				{
					str = getAttributeString(ID.Attrs.ASM);
					mouseoverList.add(str);
				}
			}
			//column 3
			else
			{
				if (my < this.barRows[0] + range)
				{
					str = getAttributeString(ID.Attrs.DEF);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[1] + range)
				{
					str = getAttributeString(ID.Attrs.DODGE);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[2] + range)
				{
					str = getAttributeString(ID.Attrs.GRUDGE);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[3] + range)
				{
					str = getAttributeString(ID.Attrs.HPRES);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[4] + range)
				{
					str = getAttributeString(ID.Attrs.KB);
					mouseoverList.add(str);
				}
				else if (my < this.barRows[5] + range)
				{
					str = getAttributeString(ID.Attrs.MOV);
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
			mouseoverList.add(attrATKL);
			mouseoverList.add(attrATKH);
			mouseoverList.add(attrAIRL);
			mouseoverList.add(attrAIRH);
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
    				
    				switch (EntityHelper.getMoraleLevel(shipList[i].getMorale()))
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
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				drawTexturedModalRect(guiLeft+barCols[i], guiTop+barRows[j], 0, 220, barLength, 4);
			}
		}
		
		//bar animation
		int len = 0;
		int bx = 0;		//bar pos x
		int by = 0;		//bar pos y
		int icony = 0;	//bar icon y
		int cols1 = barCols[0] + barLength;
		int cols2 = barCols[1] + barLength;
		int cols3 = barCols[2] + barLength;
		
		for (int i = 0; i < Attrs.AttrsLength; i++)
		{
			//bar length--
			if ((int)this.buffBar[i] > (int)this.buffBarFinal[i])
			{
				this.buffBar[i] -= 0.3F;
			}
			//bar length++
			else if ((int)this.buffBar[i] < (int)this.buffBarFinal[i])
			{
				this.buffBar[i] += 0.3F;
			}
			
			//get draw position
			switch (i)
			{
			case ID.Attrs.HP:
			case ID.Attrs.XP:
			case ID.Attrs.AMMO:  //ignore case
				continue;
			case ID.Attrs.ATK_L: bx = cols1; by = barRows[0];
			break;
			case ID.Attrs.ATK_H: bx = cols1; by = barRows[1];
			break;
			case ID.Attrs.ATK_AL: bx = cols1; by = barRows[2];
			break;
			case ID.Attrs.ATK_AH: bx = cols1; by = barRows[3];
			break;
			case ID.Attrs.SPD: bx = cols1; by = barRows[4];
			break;
			case ID.Attrs.HIT: bx = cols1; by = barRows[5];
			break;
			
			case ID.Attrs.CRI: bx = cols2; by = barRows[0];
			break;
			case ID.Attrs.DHIT: bx = cols2; by = barRows[1];
			break;
			case ID.Attrs.THIT: bx = cols2; by = barRows[2];
			break;
			case ID.Attrs.MISS: bx = cols2; by = barRows[3];
			break;
			case ID.Attrs.AA: bx = cols2; by = barRows[4];
			break;
			case ID.Attrs.ASM: bx = cols2; by = barRows[5];
			break;
			
			case ID.Attrs.DEF: bx = cols3; by = barRows[0];
			break;
			case ID.Attrs.DODGE: bx = cols3; by = barRows[1];
			break;
			case ID.Attrs.GRUDGE: bx = cols3; by = barRows[2];
			break;
			case ID.Attrs.HPRES: bx = cols3; by = barRows[3];
			break;
			case ID.Attrs.KB: bx = cols3; by = barRows[4];
			break;
			case ID.Attrs.MOV: bx = cols3; by = barRows[5];
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
    		fontRendererObj.drawStringWithShadow(attrATKL, 12, 60, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrATKH, 12, 80, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrAIRL, 12, 100, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrAIRH, 12, 120, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrSPD, 12, 140, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrHIT, 12, 160, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrCRI, 69, 60, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrDHIT, 69, 80, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrTHIT, 69, 100, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrMISS, 69, 120, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrAA, 69, 140, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrASM, 69, 160, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrDEF, 126, 60, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrDODGE, 126, 80, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrGRUDGE, 126, 100, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrHPRES, 126, 120, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrKB, 126, 140, Enums.EnumColors.WHITE.getValue());
    		fontRendererObj.drawStringWithShadow(attrMOV, 126, 160, Enums.EnumColors.WHITE.getValue());
    		
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
	
	//set buff bar length
	private void setFormationBuffBar(int fid, int pos)
	{
		//get buff value
		float[] value = FormationHelper.getFormationBuffValue(fid, pos);
		float lenModify = 20F;
		
		//calc bar length, discard HP, XP, AMMO
		this.buffBarFinal[ID.Attrs.ATK_L] = (value[ID.Attrs.ATK_L] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.ATK_H] = (value[ID.Attrs.ATK_H] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.ATK_AL] = (value[ID.Attrs.ATK_AL] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.ATK_AH] = (value[ID.Attrs.ATK_AH] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.DEF] = (value[ID.Attrs.DEF] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.SPD] = (value[ID.Attrs.SPD] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.MOV] = value[ID.Attrs.MOV] / 0.5F * lenModify;
		this.buffBarFinal[ID.Attrs.HIT] = value[ID.Attrs.HIT] / 10F * lenModify;
		this.buffBarFinal[ID.Attrs.CRI] = (value[ID.Attrs.CRI] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.DHIT] = (value[ID.Attrs.DHIT] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.THIT] = (value[ID.Attrs.THIT] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.MISS] = (value[ID.Attrs.MISS] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.AA] = (value[ID.Attrs.AA] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.ASM] = (value[ID.Attrs.ASM] - 1F) * lenModify;
		this.buffBarFinal[ID.Attrs.DODGE] = value[ID.Attrs.DODGE] * lenModify;
		this.buffBarFinal[ID.Attrs.GRUDGE] = value[ID.Attrs.GRUDGE] * lenModify;
		this.buffBarFinal[ID.Attrs.HPRES] = value[ID.Attrs.HPRES] * lenModify;
		this.buffBarFinal[ID.Attrs.KB] = value[ID.Attrs.KB] * lenModify;
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