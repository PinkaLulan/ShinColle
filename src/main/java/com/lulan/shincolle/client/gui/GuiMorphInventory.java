package com.lulan.shincolle.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.client.gui.inventory.ContainerMorphInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

/**
 * morph inventory gui
 */
public class GuiMorphInventory extends GuiContainer
{

	public BasicEntityShip entity;
	public CapaTeitoku capa;
	public InventoryPlayer invPlayer;
	private BasicEntityShip[] shipRiding = new BasicEntityShip[3];	//0:host, 1:rider, 2:mount
	private AttrsAdv attrs;
	private static final ResourceLocation TEXTURE_BG = new ResourceLocation(Reference.TEXTURES_GUI+"GuiShipMorph.png");
	private static final ResourceLocation TEXTURE_ICON = new ResourceLocation(Reference.TEXTURES_GUI+"GuiNameIcon.png");
	private static String lvMark, hpMark, strAttrATK, strAttrAIR, strAttrDEF, strAttrSPD, strAttrMOV,
	  strAttrHIT, strAttrCri, strAttrDHIT, strAttrTHIT, strAttrAA, strAttrASM, strAttrMiss,
	  strAttrMissR, strAttrDodge, strAttrFPos, strAttrFormat, strAttrWedding,
	  strAttrWedTrue, strAttrWedFalse, strMiKills, strMiExp, strMiAirL, strMiAirH, strMiAmmoL,
	  strMiAmmoH, strMiGrudge, canMelee, canLATK, canHATK, canALATK, canAHATK, auraEffect,
	  strTimeKeep, strNofuel,
	  strAttrModern, strAttrXP, strAttrGrudge, strAttrAmmo, strAttrHPRES,
	  strShowHeld, strAttrKB, strAttrHP, strAppear;
	private static int widthHoveringText1, widthHoveringText2, widthHoveringText3;
	
	private List mouseoverList;
	private String titlename, shiplevel, 
	               strATK, strAATK, strLATK, strHATK, strALATK, strAHATK, strDEF, strSPD, strMOV, strHIT, 
	               Kills, Exp, Grudge, Owner, AmmoLight, AmmoHeavy, AirLight, AirHeavy, overText, marriage,
	               Formation;
	private int hpCurrent, hpMax, color, showPage, showPageAI, pageIndicator, pageIndicatorAI, showAttack,
				shipType, shipClass, showPageInv, xClick, yClick, maxBtn;
	private float xMouse, yMouse;
	private boolean[] switchPage1a, switchPage1b, switchPage3, switchPage4, switchPage6;
	private int[][] iconXY;  //icon array:  [ship type, ship name][file,x,y]
	
	
	public GuiMorphInventory(CapaTeitoku capa, InventoryPlayer invPlayer, BasicEntityShip entity)
	{
		super(new ContainerMorphInventory(capa, invPlayer, entity));
		
		this.capa = capa;
		this.entity = entity;
		this.invPlayer = invPlayer;
		this.xSize = 256;
		this.ySize = 214;
		
    	this.mouseoverList = new ArrayList();			
		this.showPage = 1;			//show page 1
		this.showPageAI = 1;		//show AI control page 1
		this.showPageInv = 0;		//get inventory number
		this.showAttack = 1;		//show attack 1
		this.maxBtn = 0;
		this.switchPage1a = new boolean[6];	//page 1 button value
		this.switchPage1b = new boolean[6];	//page 1 item value
		this.switchPage3 = new boolean[6];	//page 3 button value
		this.switchPage4 = new boolean[6];	//page 4 button value
		this.switchPage6 = new boolean[17];	//page 6 button value
		
		if (this.entity != null)
		{
			this.attrs = (AttrsAdv) this.entity.getAttrs();
			this.shipType = this.entity.getShipType();
			this.shipClass = this.entity.getShipClass();
			this.shipRiding[0] = this.entity;
			this.maxBtn = this.entity.getStateMinor(ID.M.NumState);
			this.iconXY = new int[2][3];
			this.iconXY[0] = Values.ShipTypeIconMap.get((byte)this.shipType);
			this.iconXY[1] = Values.ShipNameIconMap.get(this.shipClass);
		}
		else
		{
			this.attrs = new AttrsAdv();
		}
		
		//general string
		lvMark = I18n.format("gui.shincolle:level");
		hpMark = I18n.format("gui.shincolle:hp");
		
		//attrs string
		strAttrHP = I18n.format("gui.shincolle:hp");
		strAttrATK = I18n.format("gui.shincolle:firepower1");
		strAttrAIR = I18n.format("gui.shincolle:firepower2");
		strAttrDEF = I18n.format("gui.shincolle:armor");
		strAttrSPD = I18n.format("gui.shincolle:attackspeed");
		strAttrMOV = I18n.format("gui.shincolle:movespeed");
		strAttrHIT = I18n.format("gui.shincolle:range");
		strAttrCri = I18n.format("gui.shincolle:critical");
		strAttrDHIT = I18n.format("gui.shincolle:doublehit");
		strAttrTHIT = I18n.format("gui.shincolle:triplehit");
		strAttrAA = I18n.format("gui.shincolle:antiair");
		strAttrASM = I18n.format("gui.shincolle:antiss");
		strAttrMiss = I18n.format("gui.shincolle:missrate");
		strAttrMissR = I18n.format("gui.shincolle:missreduce");
		strAttrDodge = I18n.format("gui.shincolle:dodge");
		strAttrFPos = I18n.format("gui.shincolle:formation.position");
		strAttrFormat = I18n.format("gui.shincolle:formation.formation");
		strAttrWedding = I18n.format("gui.shincolle:marriage");
		strAttrWedTrue = I18n.format("gui.shincolle:married");
		strAttrWedFalse = I18n.format("gui.shincolle:unmarried");
		strAttrModern = I18n.format("gui.shincolle:modernlevel");
		strAttrXP = I18n.format("gui.shincolle:equip.xp");
		strAttrGrudge = I18n.format("gui.shincolle:equip.grudge");
		strAttrAmmo = I18n.format("gui.shincolle:equip.ammo");
		strAttrHPRES = I18n.format("gui.shincolle:equip.hpres");
		strAttrKB = I18n.format("gui.shincolle:equip.kb");
		
		//minor string
		strMiKills = I18n.format("gui.shincolle:kills");
		strMiExp = I18n.format("gui.shincolle:exp");
		strMiAmmoL = I18n.format("gui.shincolle:ammolight");
		strMiAmmoH = I18n.format("gui.shincolle:ammoheavy");
		strMiGrudge = I18n.format("gui.shincolle:grudge");
		strMiAirL = I18n.format("gui.shincolle:airplanelight");
		strMiAirH = I18n.format("gui.shincolle:airplaneheavy");
		
		//AI string
		canMelee = I18n.format("gui.shincolle:canmelee");
		canLATK = I18n.format("gui.shincolle:canlightattack");
		canHATK = I18n.format("gui.shincolle:canheavyattack");
		canALATK = I18n.format("gui.shincolle:canairlightattack");
		canAHATK = I18n.format("gui.shincolle:canairheavyattack");
		auraEffect = I18n.format("gui.shincolle:auraeffect");
		strTimeKeep = I18n.format("gui.shincolle:ai.timekeeper");
		strShowHeld = I18n.format("gui.shincolle:showhelditem");
		strAppear = I18n.format("gui.shincolle:appearance");
		strNofuel = I18n.format("gui.shincolle:morph.nofuel");
	}
	
	//有用到fontRendererObj的必須放在此init
	@Override
	public void initGui()
    {
		super.initGui();
		
		//get max string width for hovering text drawing
		int temp = this.fontRendererObj.getStringWidth(strAttrATK);
		this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrAIR);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrCri);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrDHIT);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrTHIT);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrAA);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrASM);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrModern);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrXP);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrGrudge);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrAmmo);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrHPRES);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrMiss);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrDodge);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrKB);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrHP);
		if (temp > this.widthHoveringText1) this.widthHoveringText1 = temp;
		
		temp = this.fontRendererObj.getStringWidth(strAttrFPos);
		this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrATK);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrAIR);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrDEF);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrDodge);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrMissR);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrCri);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrDHIT);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrTHIT);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrAA);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrASM);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
		temp = this.fontRendererObj.getStringWidth(strAttrMOV);
		if (temp > this.widthHoveringText3) this.widthHoveringText3 = temp;
    }
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		//取得gui顯示名稱
		titlename = entity.getCustomNameTag();	//get type name from nbt
		
		//畫出字串 parm: string, x, y, color, (是否dropShadow)
		//draw entity name (title) 
		this.fontRendererObj.drawString(titlename, 8, 6, 0);

		drawAttributes();	
		
//		handleHoveringText(); TODO
	}

	//GUI背景: 背景圖片
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3)
	{
		//reset color & draw background
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_BG);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        //draw banned inventory icon
        switch (this.entity.getInventoryPageSize())
        {
        case 2:
        	break;
        case 1:
        	drawTexturedModalRect(guiLeft+62, guiTop+90, 80, 214, 6, 34);
        	break;
    	default:
    		drawTexturedModalRect(guiLeft+62, guiTop+54, 80, 214, 6, 34);
    		drawTexturedModalRect(guiLeft+62, guiTop+90, 80, 214, 6, 34);
    		break;
        }
        
        //draw inventory page indicator
        this.showPageInv = this.entity.getCapaShipInventory().getInventoryPage();
        
        switch (this.showPageInv)
        {
        case 1:  //page 1
        	this.pageIndicator = 54;
        	break;
        case 2:  //page 2
        	this.pageIndicator = 90;
        	break;
        default: //page 0
        	this.pageIndicator = 18;
        	break;
        }
        drawTexturedModalRect(guiLeft+62, guiTop+this.pageIndicator, 74, 214, 6, 34);
        
        //draw page indicator
        switch (this.showPage)
        {
        case 1:	//page 1
        	this.pageIndicator = 18;
        	break;
        case 2:	//page 2
        	this.pageIndicator = 54;
        	break;
        case 3:	//page 3
        	this.pageIndicator = 90;
        	break;
        }
        drawTexturedModalRect(guiLeft+135, guiTop+this.pageIndicator, 74, 214, 6, 34);
        
        //draw AI page indicator
        switch (this.showPageAI)
        {
        case 1:
        {	//page 1
        	this.pageIndicator = 239;
        	this.pageIndicatorAI = 131;
        	
        	//get button value
        	this.switchPage1a[0] = this.entity.getStateFlag(ID.F.NoFuel);
//        	this.switchPage1a[1] = this.entity.getStateFlag(ID.F.UseAmmoLight);
//            this.switchPage1a[2] = this.entity.getStateFlag(ID.F.UseAmmoHeavy);
//            this.switchPage1a[3] = this.entity.getStateFlag(ID.F.UseAirLight);
//            this.switchPage1a[4] = this.entity.getStateFlag(ID.F.UseAirHeavy);
//            this.switchPage1a[5] = this.entity.getStateFlag(ID.F.UseRingEffect);
            
            //get button display flag
        	this.switchPage1b[0] = true;
        	this.switchPage1b[1] = false;
            this.switchPage1b[2] = false;
            this.switchPage1b[3] = false;
            this.switchPage1b[4] = false;
            this.switchPage1b[5] = false;
            
            //draw button
            int iconY = 131;
            
            for (int i = 0; i < 6; i++)
            {
            	if (switchPage1b[i])		//can draw button
            	{
                	if (switchPage1a[i])	//draw button
                	{
                		drawTexturedModalRect(guiLeft + 174, guiTop + iconY, 0, 214, 11, 11);
                	}
                	else
                	{
                		drawTexturedModalRect(guiLeft + 174, guiTop + iconY, 11, 214, 11, 11);
                	}
            	}
            	
            	iconY += 13;
            }
            
            break;
        }	
        case 2:
        {	//page 2
        	this.pageIndicator = 239;
        	this.pageIndicatorAI = 144;
        	break;
        }
        case 3:
        {	//page 3
        	this.pageIndicator = 239;
    		this.pageIndicatorAI = 157;
    		break;
    	}
        case 4:
        {	//page 4
        	this.pageIndicator = 239;
    		this.pageIndicatorAI = 170;
    		break;
        }
        case 5:
        {	//page 5
        	this.pageIndicator = 239;
        	this.pageIndicatorAI = 183;
        	break;
        }
        case 6:
        {	//page 6
        	this.pageIndicator = 239;
    		this.pageIndicatorAI = 196;
    		
    		//get button value
    		this.switchPage6[0] = this.entity.getStateFlag(ID.F.ShowHeldItem);
    		
    		int modelstate = this.entity.getStateEmotion(ID.S.State);
    		int maxstate = this.entity.getStateMinor(ID.M.NumState);
    		
    		for (int i = 0; i < maxstate; i++)
    		{
    			this.switchPage6[i + 1] = (modelstate & Values.N.Pow2[i]) == Values.N.Pow2[i] ? true : false;
    		}
    		
            //draw button 0
    		if (this.switchPage6[0])
        	{
        		drawTexturedModalRect(guiLeft + 174, guiTop + 131, 0, 214, 11, 11);
        	}
        	else
        	{
        		drawTexturedModalRect(guiLeft + 174, guiTop + 131, 11, 214, 11, 11);
        	}
    		
    		//draw button 1~16
    		int numbtn = 0;
    		
            for (int i = 0; i < 4; i++)
            {
            	//check button number
        		if (numbtn > this.maxBtn) break;
        		
            	for (int j = 0; j < 4; j++)
                {
            		//check button number
            		if (++numbtn > this.maxBtn) break;
            		
            		if (this.switchPage6[i * 4 + j + 1])
                	{
                		drawTexturedModalRect(guiLeft + 176 + j * 16, guiTop + 157 + i * 13, 0, 214, 11, 11);
                	}
                	else
                	{
                		drawTexturedModalRect(guiLeft + 176 + j * 16, guiTop + 157 + i * 13, 11, 214, 11, 11);
                	}
                }
            }
    		
    		break;
        }
        case 7:  //page 7
        {
        	this.pageIndicator = 246;
    		this.pageIndicatorAI = 131;
        }
        break;
        case 8:  //page 8
        {
        	this.pageIndicator = 246;
    		this.pageIndicatorAI = 144;
    		
    		//get button value
    		int tside = this.entity.getStateMinor(ID.M.TaskSide);
    		
    		//draw button
    		drawTexturedModalRect(guiLeft + 173, guiTop + 144, 151, 214, 66, 11);
    		drawTexturedModalRect(guiLeft + 173, guiTop + 170, 151, 214, 66, 11);
    		drawTexturedModalRect(guiLeft + 173, guiTop + 196, 151, 214, 66, 11);
    		
    		for (int i = 0; i < 18; i++)
    		{
    			if ((tside & Values.N.Pow2[i]) == Values.N.Pow2[i])
    			{
    				int dx = i % 6 * 11;
    				int dy = i / 6 * 26;
    				drawTexturedModalRect(guiLeft + 173 + dx, guiTop + 144 + dy, 151 + dx, 225, 11, 11);
    			}
    		}
        }
        break;
        case 9:  //page 9
        {
        	this.pageIndicator = 246;
    		this.pageIndicatorAI = 157;
        }
        break;
        case 10:  //page 10
        {
        	this.pageIndicator = 246;
    		this.pageIndicatorAI = 170;
        }
        break;
        case 11:  //page 11
        {
        	this.pageIndicator = 246;
    		this.pageIndicatorAI = 183;
        }
        break;
        case 12:  //page 12
        {
        	this.pageIndicator = 246;
    		this.pageIndicatorAI = 196;
        }
        break;
        }//end AI page switch
        
        //draw AI page indicator
        drawTexturedModalRect(guiLeft + this.pageIndicator, guiTop + this.pageIndicatorAI, 74, 214, 6, 11);
        
        //draw level, ship type/name icon
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_ICON);
        
        if (entity.getStateMinor(ID.M.ShipLevel) > 99)
        {
        	//draw level background
        	drawTexturedModalRect(guiLeft+165, guiTop+18, 0, 0, 40, 42);
        	
        	try
        	{
        		//draw ship type icon
        		drawTexturedModalRect(guiLeft+167, guiTop+22, this.iconXY[0][0], this.iconXY[0][1], 28, 28);

        		//use name icon file 0
        		if (iconXY[1][0] == 0)
        		{
        			//draw ship name icon
        			drawTexturedModalRect(guiLeft+176, guiTop+63, this.iconXY[1][1], this.iconXY[1][2], 11, 59);
        		}
        	}
        	catch (Exception e)
        	{
        		LogHelper.info("Exception : get name icon fail "+e);
        		e.printStackTrace();
        	}
        }
        else
        {
        	//draw level background
        	drawTexturedModalRect(guiLeft+165, guiTop+18, 0, 43, 30, 30);
        	
        	try
        	{
        		//draw ship type icon
        		drawTexturedModalRect(guiLeft+165, guiTop+18, this.iconXY[0][0], this.iconXY[0][1], 28, 28);
        		
        		//use name icon file 0
        		if (iconXY[1][0] == 0)
        		{
        			//draw ship name icon
        			drawTexturedModalRect(guiLeft+176, guiTop+63, this.iconXY[1][1], this.iconXY[1][2], 11, 59);
        		}
        	}
        	catch (Exception e)
        	{
        		LogHelper.info("Exception : get name icon fail "+e);
        		e.printStackTrace();
        	}
        }
        
        //draw entity model                                            guiLeft + 200 - xMouse  guiTop + 50 - yMouse
        drawEntityModel(guiLeft+218, guiTop+100, entity.getModelPos(), guiLeft+215-xMouse, guiTop+60-yMouse, this.shipRiding);
        
        GlStateManager.disableBlend();
	}
	
	//draw tooltip
	private void handleHoveringText()
	{
		String str, str2;
		int temp;
		
		//reset text
		mouseoverList.clear();
		
		//HP tooltip
		if (xMouse >= 145+guiLeft && xMouse < 202+guiLeft && yMouse >= 4+guiTop && yMouse < 15+guiTop)
		{
			mouseoverList.add(strAttrModern+" "+attrs.getAttrsBonus(ID.AttrsBase.HP));
			this.drawHoveringText(mouseoverList, 145, 32, this.fontRendererObj);
		}
		//Attrs tooltip
		else if (xMouse >= 73+guiLeft && xMouse < 134+guiLeft)
		{
			if (showPage == 2)
			{
				//ATK tooltip
				if (yMouse >= 18+guiTop && yMouse < 41+guiTop)
				{
					//draw attack text
					mouseoverList.add(strAttrModern);
					mouseoverList.add(TextFormatting.RED + strAttrATK);
					mouseoverList.add(TextFormatting.RED + strAttrAIR);
					mouseoverList.add(TextFormatting.AQUA + strAttrCri);
					mouseoverList.add(TextFormatting.YELLOW + strAttrDHIT);
					mouseoverList.add(TextFormatting.GOLD + strAttrTHIT);
					mouseoverList.add(TextFormatting.YELLOW + strAttrAA);
					mouseoverList.add(TextFormatting.AQUA + strAttrASM);
					mouseoverList.add(TextFormatting.RED + strAttrMiss);
					mouseoverList.add(TextFormatting.GOLD + strAttrDodge);
					mouseoverList.add(TextFormatting.GREEN + strAttrXP);
					mouseoverList.add(TextFormatting.DARK_PURPLE + strAttrGrudge);
					mouseoverList.add(TextFormatting.DARK_AQUA + strAttrAmmo);
					mouseoverList.add(TextFormatting.DARK_GREEN + strAttrHPRES);
					mouseoverList.add(TextFormatting.DARK_RED + strAttrKB);
					this.drawHoveringText(mouseoverList, 55, 57, this.fontRendererObj);
					
					//draw attack value
					mouseoverList.clear();
					mouseoverList.add(String.valueOf(attrs.getAttrsBonus(ID.AttrsBase.ATK)));
					mouseoverList.add(strATK);
					mouseoverList.add(strAATK);
					overText = String.valueOf((int)(attrs.getAttrsBuffed(ID.Attrs.CRI) * 100F)) + " %";
					mouseoverList.add(overText);
					overText = String.valueOf((int)(attrs.getAttrsBuffed(ID.Attrs.DHIT) * 100F)) + " %";
					mouseoverList.add(overText);
					overText = String.valueOf((int)(attrs.getAttrsBuffed(ID.Attrs.THIT) * 100F)) + " %";
					mouseoverList.add(overText);
					overText = String.valueOf((int)(attrs.getAttrsBuffed(ID.Attrs.AA)));
					mouseoverList.add(overText);
					overText = String.valueOf((int)(attrs.getAttrsBuffed(ID.Attrs.ASM)));
					mouseoverList.add(overText);
					
					//calc miss
					temp = (int) (CombatHelper.calcMissRate(this.entity, 0F) * 100F);
					str = String.valueOf(temp);
					temp = (int) (CombatHelper.calcMissRate(this.entity, attrs.getAttackRange()) * 100F);
					str2 = String.valueOf(temp);
					overText = str + " ~ " + str2 + " %";
					mouseoverList.add(overText);
					
					//calc dodge for submarine
					if (this.entity instanceof IShipInvisible)
					{
						float temp2 = attrs.getAttrsBuffed(ID.Attrs.DODGE) + ((IShipInvisible)this.entity).getInvisibleLevel();
						if (temp2 > ConfigHandler.limitShipAttrs[ID.Attrs.DODGE]) temp2 = (float) ConfigHandler.limitShipAttrs[ID.Attrs.DODGE];
						overText = String.format("%.0f", temp2 * 100F) + " %";
					}
					//calc dodge for normal ship
					else
					{
						overText = String.format("%.0f", attrs.getAttrsBuffed(ID.Attrs.DODGE) * 100F) + " %";
					}
					mouseoverList.add(overText);
					
					overText = String.valueOf((int)((attrs.getAttrsBuffed(ID.Attrs.XP) - 1F) * 100F)) + " %";
					mouseoverList.add(overText);
					overText = String.valueOf((int)((attrs.getAttrsBuffed(ID.Attrs.GRUDGE) - 1F) * 100F)) + " %";
					mouseoverList.add(overText);
					overText = String.valueOf((int)((attrs.getAttrsBuffed(ID.Attrs.AMMO) - 1F) * 100F)) + " %";
					mouseoverList.add(overText);
					overText = String.valueOf((int)((attrs.getAttrsBuffed(ID.Attrs.HPRES) - 1F) * 100F)) + " %";
					mouseoverList.add(overText);
					overText = String.valueOf((int)(attrs.getAttrsBuffed(ID.Attrs.KB) * 100F)) + " %";
					mouseoverList.add(overText);
					this.drawHoveringText(mouseoverList, 61 + this.widthHoveringText1, 57, this.fontRendererObj);
				}
				//DEF tooltip
				else if (yMouse >= 41+guiTop && yMouse < 62+guiTop)
				{
					mouseoverList.add(strAttrModern+" "+attrs.getAttrsBonus(ID.AttrsBase.DEF));
					this.drawHoveringText(mouseoverList, 55, 78, this.fontRendererObj);
				}
				//SPD tooltip
				else if (yMouse >= 62+guiTop && yMouse < 83+guiTop)
				{
					mouseoverList.add(strAttrModern+" "+attrs.getAttrsBonus(ID.AttrsBase.SPD));
					this.drawHoveringText(mouseoverList, 55, 99, this.fontRendererObj);
				}
				//MOV tooltip
				else if (yMouse >= 83+guiTop && yMouse < 104+guiTop)
				{
					mouseoverList.add(strAttrModern+" "+attrs.getAttrsBonus(ID.AttrsBase.MOV));
					this.drawHoveringText(mouseoverList, 55, 120, this.fontRendererObj);
				}
				//RANGE tooltip
				else if (yMouse >= 104+guiTop && yMouse < 126+guiTop)
				{
					mouseoverList.add(strAttrModern+" "+attrs.getAttrsBonus(ID.AttrsBase.HIT));
					this.drawHoveringText(mouseoverList, 55, 142, this.fontRendererObj);
				}
			}//end page 2
			else if (showPage == 3)
			{
				//show text at FORMATION
				if (yMouse >= 40+guiTop && yMouse < 62+guiTop && this.entity.getStateMinor(ID.M.FormatType) >= 1)
				{
					mouseoverList.add(TextFormatting.LIGHT_PURPLE + strAttrFPos);
					mouseoverList.add(TextFormatting.RED + strAttrATK);
					mouseoverList.add(TextFormatting.RED + strAttrAIR);
					mouseoverList.add(TextFormatting.WHITE + strAttrSPD);
					mouseoverList.add(TextFormatting.LIGHT_PURPLE + strAttrHIT);
					mouseoverList.add(TextFormatting.AQUA + strAttrCri);
					mouseoverList.add(TextFormatting.YELLOW + strAttrDHIT);
					mouseoverList.add(TextFormatting.GOLD + strAttrTHIT);
					mouseoverList.add(TextFormatting.RED + strAttrMissR);
					mouseoverList.add(TextFormatting.YELLOW + strAttrAA);
					mouseoverList.add(TextFormatting.AQUA + strAttrASM);
					mouseoverList.add(TextFormatting.WHITE + strAttrDEF);
					mouseoverList.add(TextFormatting.GOLD + strAttrDodge);
					mouseoverList.add(TextFormatting.DARK_PURPLE + strAttrGrudge);
					mouseoverList.add(TextFormatting.DARK_GREEN + strAttrHPRES);
					mouseoverList.add(TextFormatting.DARK_RED + strAttrKB);
					mouseoverList.add(TextFormatting.GRAY + strAttrMOV);
					this.drawHoveringText(mouseoverList, 128, 35, this.fontRendererObj);
					
					//draw value
					mouseoverList.clear();
					
					overText = String.valueOf(this.entity.getStateMinor(ID.M.FormatPos) + 1);
					mouseoverList.add(overText);
					
					str = String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.ATK_L) * 100F) + " %";
					str2 = String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.ATK_H) * 100F) + " %";
					overText = "x " + str + " / " + str2;
					mouseoverList.add(overText);
					
					str = String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.ATK_AL) * 100F) + " %";
					str2 = String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.ATK_AH) * 100F) + " %";
					overText = "x " + str + " / " + str2;
					mouseoverList.add(overText);
					
					overText = "x " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.SPD) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "+ " + String.format("%.1f", attrs.getAttrsFormation(ID.Attrs.HIT));
					mouseoverList.add(overText);
					overText = "x " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.CRI) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "x " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.DHIT) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "x " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.THIT) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "x " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.MISS) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "x " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.AA) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "x " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.ASM) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "x " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.DEF) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "+ " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.DODGE) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "+ " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.GRUDGE) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "+ " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.HPRES) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "+ " + String.format("%.0f", attrs.getAttrsFormation(ID.Attrs.KB) * 100F) + " %";
					mouseoverList.add(overText);
					overText = "+ " + String.format("%.2f", attrs.getAttrsFormation(ID.Attrs.MOV));
					mouseoverList.add(overText);
					
					this.drawHoveringText(mouseoverList, 134 + this.widthHoveringText3, 35, this.fontRendererObj);
				}//end formation
			}//end page 3
		}
	}
	
	//get new mouseX,Y and draw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f)
	{
		super.drawScreen(mouseX, mouseY, f);
		
		xMouse = mouseX;
		yMouse = mouseY;
	}
	
	//draw entity model, copy from player inventory class
	public static void drawEntityModel(int x, int y, float[] modelPos, float yaw, float pitch, BasicEntityShip[] entity)
	{
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		
		//set basic position and rotation
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
		GlStateManager.translate(x + modelPos[0], y + modelPos[1], 50.0F + modelPos[2]);
		GlStateManager.scale(-modelPos[3], modelPos[3], modelPos[3]);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        
		float f2 = entity[0].renderYawOffset;
		float f3 = entity[0].rotationYaw;
		float f4 = entity[0].rotationPitch;
		float f5 = entity[0].prevRotationYawHead;
		float f6 = entity[0].rotationYawHead;
		
		//set the light of model (face to player)
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		
		//set head look angle
		GlStateManager.rotate(-((float) Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		
		entity[0].renderYawOffset = (float) Math.atan(yaw / 40.0F) * 20.0F;
		entity[0].rotationYaw = (float) Math.atan(yaw / 40.0F) * 40.0F;
		entity[0].rotationPitch = -((float) Math.atan(pitch / 40.0F)) * 20.0F;
		entity[0].rotationYawHead = entity[0].rotationYaw;
		entity[0].prevRotationYawHead = entity[0].rotationYaw;
		
		//get mount or rider
		if (entity[2] != null)
		{
			entity[2].renderYawOffset = entity[0].renderYawOffset;
			entity[2].rotationYaw = entity[0].rotationYaw;
			entity[2].rotationPitch = entity[0].rotationPitch;
			entity[2].rotationYawHead = entity[0].rotationYawHead;
			entity[2].prevRotationYawHead = entity[0].prevRotationYawHead;
		}
		else if (entity[1] != null)
		{
			entity[1].renderYawOffset = entity[0].renderYawOffset;
			entity[1].rotationYaw = entity[0].rotationYaw;
			entity[1].rotationPitch = entity[0].rotationPitch;
			entity[1].rotationYawHead = entity[0].rotationYawHead;
			entity[1].prevRotationYawHead = entity[0].prevRotationYawHead;
		}
		
		GlStateManager.translate(0.0F, entity[0].getYOffset(), 0.0F);
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		
		//draw rider ot mounts
		if (entity[1] != null)
		{
			float specialOffset = 0F;
			
			//special case
			if (entity[1].getShipClass() == ID.ShipClass.DestroyerIkazuchi)
			{
				if (entity[0].getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
				{
					specialOffset = entity[0].isSitting() ? -0.34F : -0.57F;
				}
				else
				{
					specialOffset = entity[0].isSitting() ? -0.55F : -0.45F;
				}
			}
				
			//ship必須先畫才畫mounts
			float[] partPos = CalcHelper.rotateXZByAxis(-0.2F, 0F, (entity[0].renderYawOffset % 360) / 57.2957F, 1F);
			GlStateManager.translate(partPos[1], (float)entity[0].getMountedYOffset() + specialOffset, partPos[0]);
			rendermanager.doRenderEntity((Entity) entity[1], 0D, 0D, 0D, 0F, 1F, false);
			GlStateManager.translate(-partPos[1], -((float)entity[0].getMountedYOffset() + specialOffset), -partPos[0]);
			rendermanager.doRenderEntity((Entity) entity[0], 0D, 0D, 0D, 0F, 1F, false);
		}
		else if (entity[2] != null)
		{
			float specialOffset = 0F;
			
			//special case
			if (entity[2].getShipClass() == ID.ShipClass.DestroyerInazuma)
			{
				if (entity[2].getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
				{
					specialOffset = entity[2].isSitting() ? -0.34F : -0.57F;
				}
				else
				{
					specialOffset = entity[2].isSitting() ? -0.55F : -0.45F;
				}
			}
			
			//ship必須先畫才畫mounts
			float[] partPos = CalcHelper.rotateXZByAxis(-0.2F, 0F, (entity[0].renderYawOffset % 360) / 57.2957F, 1F);
			GlStateManager.translate(partPos[1], (float)entity[2].getMountedYOffset() + specialOffset, partPos[0]);
			rendermanager.doRenderEntity((Entity) entity[0], 0D, 0D, 0D, 0F, 1F, false);
			GlStateManager.translate(-partPos[1], -((float)entity[2].getMountedYOffset() + specialOffset), -partPos[0]);
			rendermanager.doRenderEntity((Entity) entity[2], 0D, 0D, 0D, 0F, 1F, false);
		}
		else
		{
			rendermanager.doRenderEntity((Entity) entity[0], 0D, 0D, 0D, 0F, 1F, false);
		}
		
//		entity.renderYawOffset = f2;
//		entity.rotationYaw = f3;
//		entity.rotationPitch = f4;
//		entity.prevRotationYawHead = f5;
//		entity.rotationYawHead = f6;
		rendermanager.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	//draw level,hp,atk,def...
	private void drawAttributes()
	{
		//draw hp, level TODO
//		shiplevel = String.valueOf(entity.getStateMinor(ID.M.ShipLevel));
//		hpCurrent = MathHelper.ceil(entity.getHealth());
//		hpMax = MathHelper.ceil(entity.getMaxHealth());
//		color = 0;
//
//		//draw lv/hp name
//		this.fontRendererObj.drawStringWithShadow(lvMark, 231-this.fontRendererObj.getStringWidth(lvMark), 6, 65535);
//		this.fontRendererObj.drawStringWithShadow(hpMark, 145-this.fontRendererObj.getStringWidth(hpMark), 6, 65535);
//		
//		//draw level: 150->gold other->white
//		if (entity.getStateMinor(ID.M.ShipLevel) < 150)
//		{
//			color = 16777215;  //white
//		}
//		else
//		{
//			color = 16766720;  //gold
//		}
//		this.fontRendererObj.drawStringWithShadow(shiplevel, xSize-6-this.fontRendererObj.getStringWidth(shiplevel), 6, color);
//
//		//draw maxhp
//		color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.HP));
//		this.fontRendererObj.drawStringWithShadow("/"+String.valueOf(hpMax), 148 + this.fontRendererObj.getStringWidth(String.valueOf(hpCurrent)), 6, color);
//		
//		//draw current hp, if currHP < maxHP, use darker color
//		if (hpCurrent < hpMax) color = GuiHelper.getDarkerColor(color, 0.8F);
//		this.fontRendererObj.drawStringWithShadow(String.valueOf(hpCurrent), 147, 6, color);	
//				
//		//draw string in different page
//		switch (this.showPage)
//		{
//		case 2:
//		{	//page 2: attribute page
//			strLATK = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.ATK_L));
//			strHATK = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.ATK_H));
//			strATK = strLATK + " / " + strHATK;
//			strALATK = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.ATK_AL));
//			strAHATK = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.ATK_AH));
//			strAATK = strALATK + " / " + strAHATK;
//			strDEF = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.DEF) * 100F)+"%";
//			strSPD = String.format("%.2f", attrs.getAttrsBuffed(ID.Attrs.SPD));
//			strMOV = String.format("%.2f", attrs.getAttrsBuffed(ID.Attrs.MOV));
//			strHIT = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.HIT));
//			
//			//draw ATK, DEF, SPD, MOV, HIT
//			if (this.showAttack == 1)
//			{	//show cannon attack
//				this.fontRendererObj.drawString(strAttrATK, 75, 20, 0);
//				color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.ATK));
//				this.fontRendererObj.drawStringWithShadow(strATK, 133-this.fontRendererObj.getStringWidth(strATK), 30, color);
//			}
//			else
//			{	//show aircraft attack
//				this.fontRendererObj.drawString(strAttrAIR, 75, 20, 0);
//				color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.ATK));
//				this.fontRendererObj.drawStringWithShadow(strAATK, 133-this.fontRendererObj.getStringWidth(strAATK), 30, color);
//			}
//			
//			this.fontRendererObj.drawString(strAttrDEF, 75, 41, 0);
//			this.fontRendererObj.drawString(strAttrSPD, 75, 62, 0);
//			this.fontRendererObj.drawString(strAttrMOV, 75, 83, 0);
//			this.fontRendererObj.drawString(strAttrHIT, 75, 104, 0);
//			
//			//draw armor
//			color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.DEF));
//			this.fontRendererObj.drawStringWithShadow(strDEF, 133-this.fontRendererObj.getStringWidth(strDEF), 51, color);
//			
//			//draw attack speed
//			color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.SPD));
//			this.fontRendererObj.drawStringWithShadow(strSPD, 133-this.fontRendererObj.getStringWidth(strSPD), 72, color);
//			
//			//draw movement speed
//			color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.MOV));
//			this.fontRendererObj.drawStringWithShadow(strMOV, 133-this.fontRendererObj.getStringWidth(strMOV), 93, color);
//					
//			//draw range
//			color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.HIT));
//			this.fontRendererObj.drawStringWithShadow(strHIT, 133-this.fontRendererObj.getStringWidth(strHIT), 114, color);
//			break;
//		}
//		case 1:
//		{	//page 1: exp, kills, L&H ammo, fuel
//			//draw string
//			this.fontRendererObj.drawString(strMiKills, 75, 20, 0);
//			this.fontRendererObj.drawString(strMiExp, 75, 41, 0);
//			this.fontRendererObj.drawString(strMiAmmoL, 75, 62, 0);
//			this.fontRendererObj.drawString(strMiAmmoH, 75, 83, 0);
//			this.fontRendererObj.drawString(strMiGrudge, 75, 104, 0);
//			
//			//draw value
//			entity.setExpNext();  //update exp value
//			Exp = String.valueOf(this.entity.getStateMinor(ID.M.ExpCurrent))+"/"+String.valueOf(this.entity.getStateMinor(ID.M.ExpNext));
//			Kills = String.valueOf(this.entity.getStateMinor(ID.M.Kills));
//			AmmoLight = String.valueOf(this.entity.getStateMinor(ID.M.NumAmmoLight));
//			AmmoHeavy = String.valueOf(this.entity.getStateMinor(ID.M.NumAmmoHeavy));
//			Grudge = String.valueOf(this.entity.getStateMinor(ID.M.NumGrudge));
//				
//			this.fontRendererObj.drawStringWithShadow(Kills, 133-this.fontRendererObj.getStringWidth(Kills), 30, Enums.EnumColors.WHITE.getValue());
//			this.fontRendererObj.drawStringWithShadow(Exp, 133-this.fontRendererObj.getStringWidth(Exp), 51, Enums.EnumColors.WHITE.getValue());
//			this.fontRendererObj.drawStringWithShadow(AmmoLight, 133-this.fontRendererObj.getStringWidth(AmmoLight), 72, Enums.EnumColors.WHITE.getValue());
//			this.fontRendererObj.drawStringWithShadow(AmmoHeavy, 133-this.fontRendererObj.getStringWidth(AmmoHeavy), 93, Enums.EnumColors.WHITE.getValue());
//			this.fontRendererObj.drawStringWithShadow(Grudge, 133-this.fontRendererObj.getStringWidth(Grudge), 114, Enums.EnumColors.WHITE.getValue());
//						
//			break;
//		}
//		case 3:
//		{	//page 3: light/heavy airplane, marriage
//			//draw string
//			this.fontRendererObj.drawString(strAttrWedding, 75, 20, 0);
//			this.fontRendererObj.drawString(strAttrFormat, 75, 41, 0);
//			
//			//draw value
//			//draw marriage
//			if (this.entity.getStateFlag(ID.F.IsMarried))
//			{
//				marriage = strAttrWedTrue;
//			}
//			else
//			{
//				marriage = strAttrWedFalse;
//			}
//			
//			//draw formation
//			int ftype = this.entity.getStateMinor(ID.M.FormatType);
//			this.Formation = I18n.format("gui.shincolle:formation.format"+ftype);
//			this.fontRendererObj.drawStringWithShadow(Formation, 133-this.fontRendererObj.getStringWidth(Formation), 51, Enums.EnumColors.WHITE.getValue());
//			
//			//大型艦, 顯示艦載機數量
//			if (this.entity instanceof BasicEntityShipCV)
//			{
//				this.fontRendererObj.drawString(strMiAirL, 75, 83, 0);
//				this.fontRendererObj.drawString(strMiAirH, 75, 104, 0);
//				AirLight = String.valueOf(((BasicEntityShipCV)this.entity).getNumAircraftLight());
//				AirHeavy = String.valueOf(((BasicEntityShipCV)this.entity).getNumAircraftHeavy());
//				this.fontRendererObj.drawStringWithShadow(AirLight, 133-this.fontRendererObj.getStringWidth(AirLight), 93, Enums.EnumColors.YELLOW.getValue());
//				this.fontRendererObj.drawStringWithShadow(AirHeavy, 133-this.fontRendererObj.getStringWidth(AirHeavy), 114, Enums.EnumColors.YELLOW.getValue());	
//			}
//			
//			this.fontRendererObj.drawStringWithShadow(marriage, 133-this.fontRendererObj.getStringWidth(marriage), 30, Enums.EnumColors.YELLOW.getValue());
//			
//			break;
//		}//end case 3
//		}//end page switch
		
		//draw AI page
		switch (this.showPageAI)
		{
		case 1:
		{	//AI page 1
			//draw string
			this.fontRendererObj.drawString(strNofuel, 187, 133, 0);
//			this.fontRendererObj.drawString(strOnSight, 187, 146, 0);
//			this.fontRendererObj.drawString(strPVP, 187, 159, 0);
//			this.fontRendererObj.drawString(strAA, 187, 172, 0);
//			this.fontRendererObj.drawString(strASM, 187, 185, 0);
//			this.fontRendererObj.drawString(strTimeKeep, 187, 198, 0);
		}
		break;
		case 6:		//AI page 6
		{	//AI page 6
			//draw string
			this.fontRendererObj.drawString(strShowHeld, 187, 133, 0);
			this.fontRendererObj.drawString(strAppear, 177, 146, 0);
		}
		break;
		}//end AI page switch
	}
	
    private static int[] btCols = new int[] {189, 205, 222};
    
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int mouseKey) throws IOException
	{
        super.mouseClicked(posX, posY, mouseKey);
        
        //get click position
        this.xClick = posX - this.guiLeft;
        this.yClick = posY - this.guiTop;
        
        //check button
        switch(GuiHelper.getButton(ID.Gui.SHIPINVENTORY, 0, xClick, yClick))
        {
        case 0:	//page 1 button
        	this.showPage = 1;
        break;
        case 1:	//page 2 button
        	this.showPage = 2;
        break;
        case 2:	//page 3 button
        	this.showPage = 3;
        break;
        case 3:	//AI operation 0
        	switch (this.showPageAI)
        	{
        	case 1:		//page 1: state: no fuel
        		this.switchPage1a[0] = this.entity.getStateFlag(ID.F.NoFuel);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_NoFuel, getInverseInt(this.switchPage1a[0])));
    		break;
        	case 6:		//page 6: show held item
        		this.switchPage6[0] = this.entity.getStateFlag(ID.F.ShowHeldItem);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ShowHeld, getInverseInt(this.switchPage6[0])));
    		break;
        	}
        break;
        case 4:	//AI operation 1
        break;
        case 5:	//AI operation 2
        	switch (this.showPageAI)
        	{
        	case 6:		//page 6: model display switch button 0~3
        		if (this.xClick < btCols[0])
        		{
            		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState01, getInverseInt(this.switchPage6[1])));
        		}
        		else if (this.xClick < btCols[1])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState02, getInverseInt(this.switchPage6[2])));
        		}
        		else if (this.xClick < btCols[2])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState03, getInverseInt(this.switchPage6[3])));
        		}
        		else
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState04, getInverseInt(this.switchPage6[4])));
        		}
        	break;
        	}
        break;
        case 6:	//AI operation 3
        	switch (this.showPageAI)
        	{
        	case 6:		//page 6: model display switch button 4~7
        		if (this.xClick < btCols[0])
        		{
            		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState05, getInverseInt(this.switchPage6[5])));
        		}
        		else if (this.xClick < btCols[1])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState06, getInverseInt(this.switchPage6[6])));
        		}
        		else if (this.xClick < btCols[2])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState07, getInverseInt(this.switchPage6[7])));
        		}
        		else
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState08, getInverseInt(this.switchPage6[8])));
        		}
        	break;
        	}
        break;
        case 7:	//AI operation 4
        	switch (this.showPageAI)
        	{
        	case 6:		//page 6: model display switch button 8~11
        		if (this.xClick < btCols[0])
        		{
            		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState09, getInverseInt(this.switchPage6[9])));
        		}
        		else if (this.xClick < btCols[1])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState10, getInverseInt(this.switchPage6[10])));
        		}
        		else if (this.xClick < btCols[2])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState11, getInverseInt(this.switchPage6[11])));
        		}
        		else
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState12, getInverseInt(this.switchPage6[12])));
        		}
        	break;
        	}
        break;
        case 8:	//AI operation 5
        	switch (this.showPageAI)
        	{
        	case 6:		//page 6: model display switch button 12~15
        		if (this.xClick < btCols[0])
        		{
            		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState13, getInverseInt(this.switchPage6[13])));
        		}
        		else if (this.xClick < btCols[1])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState14, getInverseInt(this.switchPage6[14])));
        		}
        		else if (this.xClick < btCols[2])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState15, getInverseInt(this.switchPage6[15])));
        		}
        		else
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_ModelState16, getInverseInt(this.switchPage6[16])));
        		}
        	break;
        	}
        break;
        case 9:		//AI page 1
        	this.showPageAI = 1;
        break;
        case 10:	//AI page 2
        	this.showPageAI = 2;
        break;
        case 11:	//AI page 3
        	this.showPageAI = 3;
        break;
        case 12:	//AI page 4
        	this.showPageAI = 4;
        break;
        case 13:	//AI page 5
        	this.showPageAI = 5;
        break;
        case 14:	//AI page 6
        	this.showPageAI = 6;
        break;
        case 18:	//AI page 7
        	this.showPageAI = 7;
        break;
        case 19:	//AI page 8
        	this.showPageAI = 8;
        break;
        case 20:	//AI page 9
        	this.showPageAI = 9;
        break;
        case 21:	//AI page 10
        	this.showPageAI = 10;
        break;
        case 22:	//AI page 11
        	this.showPageAI = 11;
        break;
        case 23:	//AI page 12
        	this.showPageAI = 12;
        break;
        case 15:	//inventory page 0
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_InvPage, 0));
        break;
        case 16:	//inventory page 1
        	if (this.entity.getInventoryPageSize() > 0)
        	{
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_InvPage, 1));
        	}
        break;
        case 17:	//inventory page 2
        	if (this.entity.getInventoryPageSize() > 1)
        	{
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.invPlayer.player, C2SGUIPackets.PID.MorphBtn, ID.B.ShipInv_InvPage, 2));
        	}
        break;
    	}//end all page switch
        
        if (this.showPage == 2)
        {	//page 2: damage display switch
        	switch (GuiHelper.getButton(ID.Gui.SHIPINVENTORY, 1, xClick, yClick))
        	{
        	case 0:
        		if (this.showAttack == 1)
        		{
        			this.showAttack = 2;
        		}
        		else
        		{
        			this.showAttack = 1;
        		}
        	break;
        	}
        }

	}
	
	//return 0 if par1 = true
	private int getInverseInt(boolean par1)
	{
		return par1 ? 0 : 1;
	}
	
	//close gui if entity dead or too far away
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		
		if (this.entity == null || !this.invPlayer.player.isEntityAlive())
		{
            this.mc.player.closeScreen();
        }
	}
	
	
}