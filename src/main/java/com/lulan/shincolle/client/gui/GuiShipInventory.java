package com.lulan.shincolle.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.capability.CapaShipInventory;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerIkazuchi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerInazuma;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.Enums;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.GuiHelper;

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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

/** ship inventory gui
 * 
 */
public class GuiShipInventory extends GuiContainer
{

	public BasicEntityShip entity;
	public InventoryPlayer player;
	private BasicEntityShip[] shipRiding = new BasicEntityShip[3];	//0:host, 1:rider, 2:mount
	private AttrsAdv attrs;
	private static final ResourceLocation TEXTURE_BG = new ResourceLocation(Reference.TEXTURES_GUI+"GuiShipInventory.png");
	private static final ResourceLocation TEXTURE_ICON = new ResourceLocation(Reference.TEXTURES_GUI+"GuiNameIcon.png");
	private static String lvMark, hpMark, strAttrATK, strAttrAIR, strAttrDEF, strAttrSPD, strAttrMOV,
	  strAttrHIT, strAttrCri, strAttrDHIT, strAttrTHIT, strAttrAA, strAttrASM, strAttrMiss,
	  strAttrMissR, strAttrDodge, strAttrFPos, strAttrFormat, strAttrWedding,
	  strAttrWedTrue, strAttrWedFalse, strMiKills, strMiExp, strMiAirL, strMiAirH, strMiAmmoL,
	  strMiAmmoH, strMiGrudge, canMelee, canLATK, canHATK, canALATK, canAHATK, auraEffect,
	  followMin, followMax, fleeHP, tarAI, strOnSight, strPVP, strAA, strASM, strTimeKeep,
	  strPick, strWpStay, strAttrModern, strAttrXP, strAttrGrudge, strAttrAmmo, strAttrHPRES,
	  strShowHeld, strAutoCR, strAutoPump, strAttrKB, strAttrHP, strAppear, strMeta, strDict,
	  strNbt, strInput, strOutput, strFuel, strCook, strFish, strMine, strCraft;
	private static String[] strMorale;
	private static int widthHoveringText1, widthHoveringText2, widthHoveringText3;
	
	private List mouseoverList;
	private String titlename, shiplevel, 
	               strATK, strAATK, strLATK, strHATK, strALATK, strAHATK, strDEF, strSPD, strMOV, strHIT, 
	               Kills, Exp, Grudge, Owner, AmmoLight, AmmoHeavy, AirLight, AirHeavy, overText, marriage,
	               followMinValue, followMaxValue, fleeHPValue, barPosValue, Formation, strWpStayValue;
	private int hpCurrent, hpMax, color, showPage, showPageAI, pageIndicator, pageIndicatorAI, showAttack,
				fMinPos, fMaxPos, fleeHPPos, barPos, mousePressBar, shipType, shipClass, showPageInv,
				wpStayPos, autoCRPos, xClick, yClick, maxBtn;
	private float xMouse, yMouse;
	private boolean mousePress;
	private boolean[] switchPage1a, switchPage1b, switchPage3, switchPage4, switchPage6;
	private int[][] iconXY;  //icon array:  [ship type, ship name][file,x,y]
	

	public GuiShipInventory(InventoryPlayer invPlayer, BasicEntityShip entity)
	{
		super(new ContainerShipInventory(invPlayer, entity));
		
		this.entity = entity;
		this.player = invPlayer;
		this.xSize = 256;
		this.ySize = 214;
		
    	this.mouseoverList = new ArrayList();			
		this.showPage = 1;			//show page 1
		this.showPageAI = 1;		//show AI control page 1
		this.showPageInv = 0;		//get inventory number
		this.showAttack = 1;		//show attack 1
		this.mousePress = false;	//no key clicked
		this.mousePressBar = -1;	//no bar pressed
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
			
			/** special name icon */
			//raiden gattai
			if (this.entity instanceof EntityDestroyerInazuma ||
				this.entity instanceof EntityDestroyerIkazuchi)
			{
				if (this.entity.getRidingState() > 1)
				{
					this.shipType = ID.ShipType.HEAVY_CRUISER;
					this.shipClass = ID.ShipClass.Raiden;
					
					//get rider or mount
					if (this.entity.getRidingEntity() instanceof EntityDestroyerInazuma)
					{
						this.shipRiding[2] = (BasicEntityShip) this.entity.getRidingEntity();
					}
					else if (!this.entity.getPassengers().isEmpty())
					{
						if (this.entity.getPassengers().get(0) instanceof EntityDestroyerIkazuchi)
						{
							this.shipRiding[1] = (BasicEntityShip) this.entity.getPassengers().get(0);
						}
					}
				}
			}
			
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
		strMorale = new String[5];
		strMorale[0] = I18n.format("gui.shincolle:morale0");
		strMorale[1] = I18n.format("gui.shincolle:morale1");
		strMorale[2] = I18n.format("gui.shincolle:morale2");
		strMorale[3] = I18n.format("gui.shincolle:morale3");
		strMorale[4] = I18n.format("gui.shincolle:morale4");
		
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
		followMin = I18n.format("gui.shincolle:followmin");
		followMax = I18n.format("gui.shincolle:followmax");
		fleeHP = I18n.format("gui.shincolle:fleehp");
		tarAI = I18n.format("gui.shincolle:targetAI");
		strOnSight = I18n.format("gui.shincolle:onsightAI");
		strPVP = I18n.format("gui.shincolle:ai.pvp");
		strAA = I18n.format("gui.shincolle:ai.aa");
		strASM = I18n.format("gui.shincolle:ai.asm");
		strTimeKeep = I18n.format("gui.shincolle:ai.timekeeper");
		strPick = I18n.format("gui.shincolle:ai.pickitem");
		strWpStay = I18n.format("gui.shincolle:ai.wpstay");
		strShowHeld = I18n.format("gui.shincolle:showhelditem");
		strAutoCR = I18n.format("gui.shincolle:autocombatration");
		strAutoPump = I18n.format("gui.shincolle:autopump");
		strAppear = I18n.format("gui.shincolle:appearance");
		strMeta = I18n.format("gui.shincolle:crane.usemeta");
		strDict = I18n.format("gui.shincolle:crane.useoredict");
		strNbt = I18n.format("gui.shincolle:crane.usenbt");
		strInput = I18n.format("gui.shincolle:ai.inputside");
		strOutput = I18n.format("gui.shincolle:ai.outputside");
		strFuel = I18n.format("gui.shincolle:ai.fuelside");
		strCook = I18n.format("gui.shincolle:ai.cooking");
		strFish = I18n.format("gui.shincolle:ai.fishing");
		strMine = I18n.format("gui.shincolle:ai.mining");
		strCraft = I18n.format("gui.shincolle:ai.crafting");
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
		
		handleHoveringText();
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
        	this.switchPage1a[0] = this.entity.getStateFlag(ID.F.UseMelee);
        	this.switchPage1a[1] = this.entity.getStateFlag(ID.F.UseAmmoLight);
            this.switchPage1a[2] = this.entity.getStateFlag(ID.F.UseAmmoHeavy);
            this.switchPage1a[3] = this.entity.getStateFlag(ID.F.UseAirLight);
            this.switchPage1a[4] = this.entity.getStateFlag(ID.F.UseAirHeavy);
            this.switchPage1a[5] = this.entity.getStateFlag(ID.F.UseRingEffect);
            
            //get button display flag
        	this.switchPage1b[0] = true;
        	this.switchPage1b[1] = this.entity.getStateFlag(ID.F.AtkType_Light);
            this.switchPage1b[2] = this.entity.getStateFlag(ID.F.AtkType_Heavy);
            this.switchPage1b[3] = this.entity.getStateFlag(ID.F.AtkType_AirLight);
            this.switchPage1b[4] = this.entity.getStateFlag(ID.F.AtkType_AirHeavy);
            this.switchPage1b[5] = this.entity.getStateFlag(ID.F.HaveRingEffect);
            
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
        	
        	//get button value
        	fMinPos = (int)(((entity.getStateMinor(ID.M.FollowMin) - 1) / 30F) * 42F);
        	fMaxPos = (int)(((entity.getStateMinor(ID.M.FollowMax) - 2) / 30F) * 42F);
        	fleeHPPos = (int)((entity.getStateMinor(ID.M.FleeHP) / 100F) * 42F);
        	
        	//draw range bar
        	drawTexturedModalRect(guiLeft+191, guiTop+148, 31, 214, 43, 3);
        	drawTexturedModalRect(guiLeft+191, guiTop+172, 31, 214, 43, 3);
        	drawTexturedModalRect(guiLeft+191, guiTop+196, 31, 214, 43, 3);
        	
        	//draw range indicator by mouse focus target
        	switch (this.mousePressBar)
        	{
        	case 0:
        		drawTexturedModalRect(guiLeft+187+barPos, guiTop+145, 22, 214, 9, 9);
        		break;
        	case 1:
        		drawTexturedModalRect(guiLeft+187+barPos, guiTop+169, 22, 214, 9, 9);
        		break;
        	case 2:
        		drawTexturedModalRect(guiLeft+187+barPos, guiTop+193, 22, 214, 9, 9);
        		break;
        	default:
        		drawTexturedModalRect(guiLeft+187+fMinPos, guiTop+145, 22, 214, 9, 9);
        		drawTexturedModalRect(guiLeft+187+fMaxPos, guiTop+169, 22, 214, 9, 9);
        		drawTexturedModalRect(guiLeft+187+fleeHPPos, guiTop+193, 22, 214, 9, 9);
        		break;
        	}

        	break;
        }
        case 3:
        {	//page 3
        	this.pageIndicator = 239;
    		this.pageIndicatorAI = 157;
    		
    		//get button value
    		this.switchPage3[0] = this.entity.getStateFlag(ID.F.PassiveAI);
    		this.switchPage3[1] = this.entity.getStateFlag(ID.F.OnSightChase);
    		this.switchPage3[2] = this.entity.getStateFlag(ID.F.PVPFirst);
    		this.switchPage3[3] = this.entity.getStateFlag(ID.F.AntiAir);
    		this.switchPage3[4] = this.entity.getStateFlag(ID.F.AntiSS);
    		this.switchPage3[5] = this.entity.getStateFlag(ID.F.TimeKeeper);
    		
            //draw button
            int iconY = 131;
            
            for (boolean b : this.switchPage3)
            {
            	if (b)
            	{
            		drawTexturedModalRect(guiLeft + 174, guiTop + iconY, 0, 214, 11, 11);
            	}
            	else
            	{
            		drawTexturedModalRect(guiLeft + 174, guiTop + iconY, 11, 214, 11, 11);
            	}
            	
            	iconY += 13;
            }
  
    		break;
    	}
        case 4:
        {	//page 4
        	this.pageIndicator = 239;
    		this.pageIndicatorAI = 170;
    		
    		//get button value
    		this.switchPage4[0] = this.entity.getStateFlag(ID.F.PickItem);
    		this.switchPage4[1] = this.entity.getStateFlag(ID.F.AutoPump);
    		
            //draw button
            if (this.entity.getStateFlag(ID.F.CanPickItem))
            {
            	if (this.switchPage4[0])
                {
                	drawTexturedModalRect(guiLeft+174, guiTop+131, 0, 214, 11, 11);
                }
                else
                {
                	drawTexturedModalRect(guiLeft+174, guiTop+131, 11, 214, 11, 11);
                }
            }
            
        	if (this.switchPage4[1])
            {
            	drawTexturedModalRect(guiLeft+174, guiTop+144, 0, 214, 11, 11);
            }
            else
            {
            	drawTexturedModalRect(guiLeft+174, guiTop+144, 11, 214, 11, 11);
            }
            
    		break;
        }
        case 5:
        {	//page 5
        	this.pageIndicator = 239;
        	this.pageIndicatorAI = 183;
        	
        	//get button value
        	wpStayPos = (int)(entity.getStateMinor(ID.M.WpStay) * 0.0625F * 42F);
        	autoCRPos = (int)((entity.getStateMinor(ID.M.UseCombatRation) - 1) * 14F);
        	
        	//draw range bar
        	drawTexturedModalRect(guiLeft+191, guiTop+148, 31, 214, 43, 3);
        	drawTexturedModalRect(guiLeft+191, guiTop+172, 31, 214, 43, 3);
        	
        	//draw range indicator by mouse focus target
        	switch (this.mousePressBar)
        	{
        	case 3:
        		drawTexturedModalRect(guiLeft+187+barPos, guiTop+145, 22, 214, 9, 9);
        		break;
        	case 4:
        		drawTexturedModalRect(guiLeft+187+barPos, guiTop+169, 22, 214, 9, 9);
        		break;
        	default:
        		drawTexturedModalRect(guiLeft+187+wpStayPos, guiTop+145, 22, 214, 9, 9);
        		drawTexturedModalRect(guiLeft+187+autoCRPos, guiTop+169, 22, 214, 9, 9);
        		break;
        	}

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
    		
    		//get button value
    		int tside = this.entity.getStateMinor(ID.M.TaskSide);
    		
    		//draw button: task icon
    		drawTexturedModalRect(guiLeft + 174, guiTop + 136, 87, 214, 64, 16);
    		drawTexturedModalRect(guiLeft + 174, guiTop + 138, 151, 237, 64, 16);
    		
    		switch (this.entity.getStateMinor(ID.M.Task))
    		{
    		case 1:  //cooking
    			drawTexturedModalRect(guiLeft + 174, guiTop + 136, 87, 230, 16, 16);
			break;
    		case 2:  //fishing
    			drawTexturedModalRect(guiLeft + 190, guiTop + 136, 103, 230, 16, 16);
			break;
    		case 3:  //mining
    			drawTexturedModalRect(guiLeft + 206, guiTop + 136, 119, 230, 16, 16);
			break;
    		case 4:  //crafting
    			drawTexturedModalRect(guiLeft + 222, guiTop + 136, 135, 230, 16, 16);
			break;
    		}
    		
    		//draw button: task setting
    		if ((tside & Values.N.Pow2[18]) == Values.N.Pow2[18])
    		{
    			drawTexturedModalRect(guiLeft + 177, guiTop + 157, 0, 236, 11, 11);
    		}
    		else
    		{
    			drawTexturedModalRect(guiLeft + 177, guiTop + 157, 0, 225, 11, 11);
    		}
    		
    		if ((tside & Values.N.Pow2[19]) == Values.N.Pow2[19])
    		{
    			drawTexturedModalRect(guiLeft + 177, guiTop + 170, 11, 236, 11, 11);
    		}
    		else
    		{
    			drawTexturedModalRect(guiLeft + 177, guiTop + 170, 11, 225, 11, 11);
    		}
    		
    		if ((tside & Values.N.Pow2[20]) == Values.N.Pow2[20])
    		{
    			drawTexturedModalRect(guiLeft + 177, guiTop + 183, 22, 236, 11, 11);
    		}
    		else
    		{
    			drawTexturedModalRect(guiLeft + 177, guiTop + 183, 22, 225, 11, 11);
    		}
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
        
        //draw task icon and task slot background
        if (this.showPageInv == 0)
        {
			switch (this.entity.getStateMinor(ID.M.Task))
			{
			case 1:  //cooking
				drawTexturedModalRect(guiLeft + 25, guiTop + 107, 33, 225, 18, 18);
				drawTexturedModalRect(guiLeft + 26, guiTop + 109, 151, 236, 18, 18);
		    break;
			case 2:  //fishing
				drawTexturedModalRect(guiLeft + 25, guiTop + 107, 33, 225, 18, 18);
				drawTexturedModalRect(guiLeft + 26, guiTop + 109, 167, 236, 18, 18);
		    break;
			case 3:  //mining
				drawTexturedModalRect(guiLeft + 25, guiTop + 107, 33, 225, 18, 18);
				drawTexturedModalRect(guiLeft + 26, guiTop + 109, 183, 236, 18, 18);
		    break;
			case 4:  //crafting
				drawTexturedModalRect(guiLeft + 25, guiTop + 107, 33, 225, 18, 18);
				drawTexturedModalRect(guiLeft + 26, guiTop + 109, 199, 236, 18, 18);
				
				CapaShipInventory inv = this.entity.getCapaShipInventory();
				for (int i = 0; i < 9; i++)
				{
					if (inv.getStackInSlot(i + 12) == null)
					{
						drawTexturedModalRect(guiLeft + 7 + (i % 3) * 18, guiTop + 53 + (i / 3) * 18, 33, 225, 18, 18);
					}
					else
					{
						drawTexturedModalRect(guiLeft + 7 + (i % 3) * 18, guiTop + 53 + (i / 3) * 18, 51, 225, 18, 18);
					}
				}
		    break;
			}
        }
        
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
//        		LogHelper.info("Exception : get name icon fail "+e);
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
//        		LogHelper.info("Exception : get name icon fail "+e);
        	}
        }
        
        //draw ship morale
        drawIconMorale();
        
        //draw entity model                                            guiLeft + 200 - xMouse  guiTop + 50 - yMouse
        drawEntityModel(guiLeft+218, guiTop+100, entity.getModelPos(), guiLeft+215-xMouse, guiTop+60-yMouse, this.shipRiding);
        
        GlStateManager.disableBlend();
	}
	
	//draw ship morale
	private void drawIconMorale()
	{
		int ix = EntityHelper.getMoraleLevel(this.entity.getMorale()) * 11;
        drawTexturedModalRect(guiLeft+239, guiTop+18, ix, 240, 11, 11);
        
	}
	
	//draw tooltip
	private void handleHoveringText()
	{
		String str, str2;
		int temp;
		
		//reset text
		mouseoverList.clear();
		
		//Morale tooltip
		if (xMouse >= 238+guiLeft && xMouse < 251+guiLeft && yMouse >= 17+guiTop && yMouse < 30+guiTop)
		{
			mouseoverList.add(this.strMorale[EntityHelper.getMoraleLevel(this.entity.getMorale())]);
			this.drawHoveringText(mouseoverList, 120, 30, this.fontRendererObj);
			
			//draw value
			mouseoverList.clear();
			
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
			mouseoverList.add(TextFormatting.GREEN + strAttrXP);
			mouseoverList.add(TextFormatting.DARK_PURPLE + strAttrGrudge);
			mouseoverList.add(TextFormatting.DARK_AQUA + strAttrAmmo);
			mouseoverList.add(TextFormatting.DARK_GREEN + strAttrHPRES);
			mouseoverList.add(TextFormatting.DARK_RED + strAttrKB);
			mouseoverList.add(TextFormatting.GRAY + strAttrMOV);
			this.drawHoveringText(mouseoverList, 120, 46, this.fontRendererObj);
			
			//draw value
			mouseoverList.clear();
			
			str = String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.ATK_L) * 100F) + " %";
			str2 = String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.ATK_H) * 100F) + " %";
			overText = "x " + str + " / " + str2;
			mouseoverList.add(overText);
			
			str = String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.ATK_AL) * 100F) + " %";
			str2 = String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.ATK_AH) * 100F) + " %";
			overText = "x " + str + " / " + str2;
			mouseoverList.add(overText);
			
			overText = "x " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.SPD) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "+ " + String.format("%.1f", attrs.getAttrsMorale(ID.Attrs.HIT));
			mouseoverList.add(overText);
			overText = "x " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.CRI) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "x " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.DHIT) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "x " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.THIT) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "x " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.MISS) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "x " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.AA) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "x " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.ASM) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "+ " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.DEF) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "+ " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.DODGE) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "+ " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.XP) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "+ " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.GRUDGE) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "+ " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.AMMO) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "+ " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.HPRES) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "+ " + String.format("%.0f", attrs.getAttrsMorale(ID.Attrs.KB) * 100F) + " %";
			mouseoverList.add(overText);
			overText = "+ " + String.format("%.2f", attrs.getAttrsMorale(ID.Attrs.MOV));
			mouseoverList.add(overText);
			
			this.drawHoveringText(mouseoverList, 126 + this.widthHoveringText3, 46, this.fontRendererObj);
		}
		//HP tooltip
		else if (xMouse >= 145+guiLeft && xMouse < 202+guiLeft && yMouse >= 4+guiTop && yMouse < 15+guiTop)
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
		//AI tooltip
		else if (xMouse >= 173+guiLeft && xMouse < 239+guiLeft && yMouse >= 131+guiTop && yMouse < 207+guiTop)
		{
			//AI page 7
			if (this.showPageAI == 7)
			{
				mouseoverList.clear();
				
				if (yMouse <= 157+guiTop)
				{
					if (xMouse <= 189+guiLeft)
					{
						mouseoverList.add(strCook);
					}
					else if (xMouse <= 205+guiLeft)
					{
						mouseoverList.add(strFish);
					}
					else if (xMouse <= 221+guiLeft)
					{
						mouseoverList.add(strMine);
					}
					else
					{
						mouseoverList.add(strCraft);
					}
					
					this.drawHoveringText(mouseoverList, 167, 170, this.fontRendererObj);
				}
				else if (yMouse <= 170+guiTop)
				{
					mouseoverList.add(strMeta);
					this.drawHoveringText(mouseoverList, 145, 185, this.fontRendererObj);
				}
				else if (yMouse <= 183+guiTop)
				{
					mouseoverList.add(strDict);
					this.drawHoveringText(mouseoverList, 145, 198, this.fontRendererObj);
				}
				else if (yMouse <= 196+guiTop)
				{
					mouseoverList.add(strNbt);
					this.drawHoveringText(mouseoverList, 145, 211, this.fontRendererObj);
				}
			}//end AI page 7
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
		//draw hp, level
		shiplevel = String.valueOf(entity.getStateMinor(ID.M.ShipLevel));
		hpCurrent = MathHelper.ceil(entity.getHealth());
		hpMax = MathHelper.ceil(entity.getMaxHealth());
		color = 0;

		//draw lv/hp name
		this.fontRendererObj.drawStringWithShadow(lvMark, 231-this.fontRendererObj.getStringWidth(lvMark), 6, 65535);
		this.fontRendererObj.drawStringWithShadow(hpMark, 145-this.fontRendererObj.getStringWidth(hpMark), 6, 65535);
		
		//draw level: 150->gold other->white
		if (entity.getStateMinor(ID.M.ShipLevel) < 150)
		{
			color = 16777215;  //white
		}
		else
		{
			color = 16766720;  //gold
		}
		this.fontRendererObj.drawStringWithShadow(shiplevel, xSize-6-this.fontRendererObj.getStringWidth(shiplevel), 6, color);

		//draw maxhp
		color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.HP));
		this.fontRendererObj.drawStringWithShadow("/"+String.valueOf(hpMax), 148 + this.fontRendererObj.getStringWidth(String.valueOf(hpCurrent)), 6, color);
		
		//draw current hp, if currHP < maxHP, use darker color
		if (hpCurrent < hpMax) color = GuiHelper.getDarkerColor(color, 0.8F);
		this.fontRendererObj.drawStringWithShadow(String.valueOf(hpCurrent), 147, 6, color);	
				
		//draw string in different page
		switch (this.showPage)
		{
		case 2:
		{	//page 2: attribute page
			strLATK = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.ATK_L));
			strHATK = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.ATK_H));
			strATK = strLATK + " / " + strHATK;
			strALATK = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.ATK_AL));
			strAHATK = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.ATK_AH));
			strAATK = strALATK + " / " + strAHATK;
			strDEF = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.DEF) * 100F)+"%";
			strSPD = String.format("%.2f", attrs.getAttrsBuffed(ID.Attrs.SPD));
			strMOV = String.format("%.2f", attrs.getAttrsBuffed(ID.Attrs.MOV));
			strHIT = String.format("%.1f", attrs.getAttrsBuffed(ID.Attrs.HIT));
			
			//draw ATK, DEF, SPD, MOV, HIT
			if (this.showAttack == 1)
			{	//show cannon attack
				this.fontRendererObj.drawString(strAttrATK, 75, 20, 0);
				color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.ATK));
				this.fontRendererObj.drawStringWithShadow(strATK, 133-this.fontRendererObj.getStringWidth(strATK), 30, color);
			}
			else
			{	//show aircraft attack
				this.fontRendererObj.drawString(strAttrAIR, 75, 20, 0);
				color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.ATK));
				this.fontRendererObj.drawStringWithShadow(strAATK, 133-this.fontRendererObj.getStringWidth(strAATK), 30, color);
			}
			
			this.fontRendererObj.drawString(strAttrDEF, 75, 41, 0);
			this.fontRendererObj.drawString(strAttrSPD, 75, 62, 0);
			this.fontRendererObj.drawString(strAttrMOV, 75, 83, 0);
			this.fontRendererObj.drawString(strAttrHIT, 75, 104, 0);
			
			//draw armor
			color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.DEF));
			this.fontRendererObj.drawStringWithShadow(strDEF, 133-this.fontRendererObj.getStringWidth(strDEF), 51, color);
			
			//draw attack speed
			color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.SPD));
			this.fontRendererObj.drawStringWithShadow(strSPD, 133-this.fontRendererObj.getStringWidth(strSPD), 72, color);
			
			//draw movement speed
			color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.MOV));
			this.fontRendererObj.drawStringWithShadow(strMOV, 133-this.fontRendererObj.getStringWidth(strMOV), 93, color);
					
			//draw range
			color = GuiHelper.getBonusPointColor(attrs.getAttrsBonus(ID.AttrsBase.HIT));
			this.fontRendererObj.drawStringWithShadow(strHIT, 133-this.fontRendererObj.getStringWidth(strHIT), 114, color);
			break;
		}
		case 1:
		{	//page 1: exp, kills, L&H ammo, fuel
			//draw string
			this.fontRendererObj.drawString(strMiKills, 75, 20, 0);
			this.fontRendererObj.drawString(strMiExp, 75, 41, 0);
			this.fontRendererObj.drawString(strMiAmmoL, 75, 62, 0);
			this.fontRendererObj.drawString(strMiAmmoH, 75, 83, 0);
			this.fontRendererObj.drawString(strMiGrudge, 75, 104, 0);
			
			//draw value
			entity.setExpNext();  //update exp value
			Exp = String.valueOf(this.entity.getStateMinor(ID.M.ExpCurrent))+"/"+String.valueOf(this.entity.getStateMinor(ID.M.ExpNext));
			Kills = String.valueOf(this.entity.getStateMinor(ID.M.Kills));
			AmmoLight = String.valueOf(this.entity.getStateMinor(ID.M.NumAmmoLight));
			AmmoHeavy = String.valueOf(this.entity.getStateMinor(ID.M.NumAmmoHeavy));
			Grudge = String.valueOf(this.entity.getStateMinor(ID.M.NumGrudge));
				
			this.fontRendererObj.drawStringWithShadow(Kills, 133-this.fontRendererObj.getStringWidth(Kills), 30, Enums.EnumColors.WHITE.getValue());
			this.fontRendererObj.drawStringWithShadow(Exp, 133-this.fontRendererObj.getStringWidth(Exp), 51, Enums.EnumColors.WHITE.getValue());
			this.fontRendererObj.drawStringWithShadow(AmmoLight, 133-this.fontRendererObj.getStringWidth(AmmoLight), 72, Enums.EnumColors.WHITE.getValue());
			this.fontRendererObj.drawStringWithShadow(AmmoHeavy, 133-this.fontRendererObj.getStringWidth(AmmoHeavy), 93, Enums.EnumColors.WHITE.getValue());
			this.fontRendererObj.drawStringWithShadow(Grudge, 133-this.fontRendererObj.getStringWidth(Grudge), 114, Enums.EnumColors.WHITE.getValue());
						
			break;
		}
		case 3:
		{	//page 3: light/heavy airplane, marriage
			//draw string
			this.fontRendererObj.drawString(strAttrWedding, 75, 20, 0);
			this.fontRendererObj.drawString(strAttrFormat, 75, 41, 0);
			
			//draw value
			//draw marriage
			if (this.entity.getStateFlag(ID.F.IsMarried))
			{
				marriage = strAttrWedTrue;
			}
			else
			{
				marriage = strAttrWedFalse;
			}
			
			//draw formation
			int ftype = this.entity.getStateMinor(ID.M.FormatType);
			this.Formation = I18n.format("gui.shincolle:formation.format"+ftype);
			this.fontRendererObj.drawStringWithShadow(Formation, 133-this.fontRendererObj.getStringWidth(Formation), 51, Enums.EnumColors.WHITE.getValue());
			
			//大型艦, 顯示艦載機數量
			if (this.entity instanceof BasicEntityShipCV)
			{
				this.fontRendererObj.drawString(strMiAirL, 75, 83, 0);
				this.fontRendererObj.drawString(strMiAirH, 75, 104, 0);
				AirLight = String.valueOf(((BasicEntityShipCV)this.entity).getNumAircraftLight());
				AirHeavy = String.valueOf(((BasicEntityShipCV)this.entity).getNumAircraftHeavy());
				this.fontRendererObj.drawStringWithShadow(AirLight, 133-this.fontRendererObj.getStringWidth(AirLight), 93, Enums.EnumColors.YELLOW.getValue());
				this.fontRendererObj.drawStringWithShadow(AirHeavy, 133-this.fontRendererObj.getStringWidth(AirHeavy), 114, Enums.EnumColors.YELLOW.getValue());	
			}
			
			this.fontRendererObj.drawStringWithShadow(marriage, 133-this.fontRendererObj.getStringWidth(marriage), 30, Enums.EnumColors.YELLOW.getValue());
			
			break;
		}//end case 3
		}//end page switch
		
		//draw AI page
		switch (this.showPageAI)
		{
		case 1:
		{	//AI page 1
			//draw string
			this.fontRendererObj.drawString(canMelee, 187, 133, 0);
			if (entity.getAttackType(ID.F.AtkType_Light))
			this.fontRendererObj.drawString(canLATK, 187, 146, 0);
			if (entity.getAttackType(ID.F.AtkType_Heavy))
			this.fontRendererObj.drawString(canHATK, 187, 159, 0);
			if (entity.getAttackType(ID.F.AtkType_AirLight))
			this.fontRendererObj.drawString(canALATK, 187, 172, 0);
			if (entity.getAttackType(ID.F.AtkType_AirHeavy))
			this.fontRendererObj.drawString(canAHATK, 187, 185, 0);
			if (entity.getAttackType(ID.F.HaveRingEffect))
			this.fontRendererObj.drawString(auraEffect, 187, 198, 0);
		}
		break;
		case 2:
		{	//AI page 2
			//draw string
			this.fontRendererObj.drawString(followMin, 174, 134, 0);
			this.fontRendererObj.drawString(followMax, 174, 158, 0);
			this.fontRendererObj.drawString(fleeHP, 174, 182, 0);
			
			//draw value
			followMinValue = String.valueOf(entity.getStateMinor(ID.M.FollowMin));
			followMaxValue = String.valueOf(entity.getStateMinor(ID.M.FollowMax));
			fleeHPValue = String.valueOf(entity.getStateMinor(ID.M.FleeHP));
			
			if (this.mousePressBar == 0)
			{
				barPosValue = String.valueOf((int)(barPos / 42F * 30F + 1F));
				this.fontRendererObj.drawStringWithShadow(barPosValue, 174, 145, Enums.EnumColors.RED_DARK.getValue());
			}
			else
			{
				this.fontRendererObj.drawStringWithShadow(followMinValue, 174, 145, Enums.EnumColors.YELLOW.getValue());
			}
			
			if (this.mousePressBar == 1)
			{
				barPosValue = String.valueOf((int)(barPos / 42F * 30F + 2F));
				this.fontRendererObj.drawStringWithShadow(barPosValue, 174, 169, Enums.EnumColors.RED_LIGHT.getValue());		
			}
			else
			{
				this.fontRendererObj.drawStringWithShadow(followMaxValue, 174, 169, Enums.EnumColors.YELLOW.getValue());
			}
			
			if (this.mousePressBar == 2)
			{
				barPosValue = String.valueOf((int)(barPos / 42F * 100F));
				this.fontRendererObj.drawStringWithShadow(barPosValue, 174, 193, Enums.EnumColors.RED_LIGHT.getValue());
			}
			else
			{
				this.fontRendererObj.drawStringWithShadow(fleeHPValue, 174, 193, Enums.EnumColors.YELLOW.getValue());
			}
		}
		break;
		case 3:
		{	//AI page 3
			//draw string
			this.fontRendererObj.drawString(tarAI, 187, 133, 0);
			this.fontRendererObj.drawString(strOnSight, 187, 146, 0);
			this.fontRendererObj.drawString(strPVP, 187, 159, 0);
			this.fontRendererObj.drawString(strAA, 187, 172, 0);
			this.fontRendererObj.drawString(strASM, 187, 185, 0);
			this.fontRendererObj.drawString(strTimeKeep, 187, 198, 0);
		}
		break;
		case 4:		//AI page 4
		{
			if (this.entity.getStateFlag(ID.F.CanPickItem)) this.fontRendererObj.drawString(strPick, 187, 134, 0);
			this.fontRendererObj.drawString(strAutoPump, 187, 146, 0);
		}
		break;
		case 5:		//AI page 5
		{
			//draw string
			this.fontRendererObj.drawString(strWpStay, 174, 134, 0);
			this.fontRendererObj.drawString(strAutoCR, 174, 158, 0);
			
			//draw value
			strWpStayValue = CalcHelper.tick2SecOrMin(entity.wpStayTime2Ticks(entity.getStateMinor(ID.M.WpStay)));
			int temp = entity.getStateMinor(ID.M.UseCombatRation);
			
			if (this.mousePressBar == 3)
			{
				barPosValue = CalcHelper.tick2SecOrMin(entity.wpStayTime2Ticks((int)(barPos / (42F * 0.0625F))));
				this.fontRendererObj.drawStringWithShadow(barPosValue, 174, 145, Enums.EnumColors.RED_LIGHT.getValue());
			}
			else
			{
				this.fontRendererObj.drawStringWithShadow(strWpStayValue, 174, 145, Enums.EnumColors.YELLOW.getValue());
			}
			
			if (this.mousePressBar == 4)
			{
				int temp2 = (int)(barPos / (43F * 0.25F));
				
				if (temp2 >= 0 && temp2 <= 3)
				{
					barPosValue = this.strMorale[temp2+1];
					this.fontRendererObj.drawStringWithShadow(barPosValue, 174, 169, Enums.EnumColors.RED_LIGHT.getValue());		
				}
			}
			else
			{
				if (temp >= 0 && temp <= 4)
				{
					this.fontRendererObj.drawStringWithShadow(this.strMorale[temp], 174, 169, Enums.EnumColors.YELLOW.getValue());
				}
			}
		}
		break;
		case 6:		//AI page 6
		{	//AI page 6
			//draw string
			this.fontRendererObj.drawString(strShowHeld, 187, 133, 0);
			this.fontRendererObj.drawString(strAppear, 177, 146, 0);
		}
		break;
		case 7:		//AI page 7
		{
			//draw string
			this.fontRendererObj.drawString(" Metadata", 187, 159, 0);
			this.fontRendererObj.drawString(" Ore Dict", 187, 172, 0);
			this.fontRendererObj.drawString(" NBT Tag", 187, 185, 0);
		}
		break;
		case 8:		//AI page 8
		{
			//draw string
			this.fontRendererObj.drawString(strInput, 177, 133, 0);
			this.fontRendererObj.drawString(strOutput, 177, 159, 0);
			this.fontRendererObj.drawString(strFuel, 177, 185, 0);
		}
		break;
		case 9:		//AI page 9
		{
		}
		break;
		case 10:	//AI page 10
		{
		}
		break;
		case 11:	//AI page 11
		{
		}
		break;
		case 12:	//AI page 12
		{
		}
		break;
		}//end AI page switch
	}
	
	//mouse press + move
    @Override
	protected void mouseClickMove(int posX, int posY, int mouseKey, long pressTime)
    {
    	super.mouseClickMove(posX, posY, mouseKey, pressTime);

    	//get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        
        barPos = xClick - 191;
        if (barPos > 42) barPos = 42;
        if (barPos < 0)  barPos = 0;

    }
    
    //state: -1:move 0:left up 1:right up 2:...
    @Override
	protected void mouseReleased(int posX, int posY, int state)
    {
    	super.mouseReleased(posX, posY, state);

    	//get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        
    	//get cliuck button
        int barvalue = 0;
        
    	switch (mousePressBar)
    	{
    	case 0:	//bar0: follow min
    		barvalue = (int)(barPos / 42F * 30F + 1F);
    		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_FollowMin, barvalue));
    		break;
    	case 1:	//bar1: follow max
    		barvalue = (int)(barPos / 42F * 30F + 2F);
    		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_FollowMax, barvalue));
    		break;
    	case 2:	//bar2: flee hp
    		barvalue = (int)(barPos / 42F * 100F);
    		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_FleeHP, barvalue));
    		break;
    	case 3:	//bar3: wp stay time
    		barvalue = (int)(barPos / (42F * 0.0625F));
    		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_WpStay, barvalue));
    		break;
    	case 4:	//bar4: auto use combat ration
    		barvalue = (int)(barPos / (43F * 0.25F)) + 1;
    		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_AutoCR, barvalue));
    		break;
    	}
    	
    	//reset flag
    	mousePress = false;
    	mousePressBar = -1;
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
        this.mousePress = true;
        
        //check press bar
        if (this.showPageAI == 2)
        {
        	switch(GuiHelper.getButton(ID.Gui.SHIPINVENTORY, 2, xClick, yClick))
        	{
        	case 0:
        		mousePressBar = 0;
        		break;
        	case 1:
        		mousePressBar = 1;
        		break;
        	case 2:
        		mousePressBar = 2;
        		break;
    		default:
    			mousePressBar = -1;
        		break;
        	}
        }
        else if (this.showPageAI == 5)
        {
        	switch(GuiHelper.getButton(ID.Gui.SHIPINVENTORY, 2, xClick, yClick))
        	{
        	case 0:
        		mousePressBar = 3;
        		break;
        	case 1:
        		mousePressBar = 4;
        		break;
    		default:
    			mousePressBar = -1;
        		break;
        	}
        }
        else
        {
        	mousePressBar = -1;
        }
        
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
        	case 1:		//page 1: can melee button
        		this.switchPage1a[0] = this.entity.getStateFlag(ID.F.UseMelee);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_Melee, getInverseInt(this.switchPage1a[0])));
    		break;
        	case 3:		//page 3: change target AI
        		this.switchPage3[0] = this.entity.getStateFlag(ID.F.PassiveAI);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_TarAI, getInverseInt(this.switchPage3[0])));
    		break;
        	case 4:		//page 4: pick item AI
        		this.switchPage4[0] = this.entity.getStateFlag(ID.F.PickItem);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_PickitemAI, getInverseInt(this.switchPage4[0])));
    		break;
        	case 6:		//page 6: show held item
        		this.switchPage6[0] = this.entity.getStateFlag(ID.F.ShowHeldItem);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ShowHeld, getInverseInt(this.switchPage6[0])));
    		break;
        	case 7:		//page 7: change task
        		int curTask = this.entity.getStateMinor(ID.M.Task);
        		int newTask = 0;
        		
        		if (xClick < 191)
        		{
        			if (curTask != 1) newTask = 1;
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_Task, newTask));
        		}
        		else if (xClick < 207)
        		{
        			if (curTask != 2) newTask = 2;
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_Task, newTask));
        		}
        		else if (xClick < 223)
        		{
        			if (curTask != 3) newTask = 3;
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_Task, newTask));
        		}
        		else
        		{
        			if (curTask != 4) newTask = 4;
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_Task, newTask));
        		}
        	break;
        	}
        break;
        case 4:	//AI operation 1 
        	switch (this.showPageAI)
        	{
        	case 1:		//page 1: use ammo light button
        		this.switchPage1a[1] = this.entity.getStateFlag(ID.F.UseAmmoLight);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_AmmoLight, getInverseInt(this.switchPage1a[1])));
    		break;
        	case 3:		//page 3: change onsight AI
        		this.switchPage3[1] = this.entity.getStateFlag(ID.F.OnSightChase);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_OnSightAI, getInverseInt(this.switchPage3[1])));
        	break;
        	case 4:		//page 4: auto pump
        		this.switchPage4[1] = this.entity.getStateFlag(ID.F.AutoPump);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_AutoPump, getInverseInt(this.switchPage4[1])));
    		break;
        	case 7:		//page 7: change task
        		int curTask = this.entity.getStateMinor(ID.M.Task);
        		int newTask = 0;
        		
        		if (xClick < 191)
        		{
        			if (curTask != 1) newTask = 1;
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_Task, newTask));
        		}
        		else if (xClick < 207)
        		{
        			if (curTask != 2) newTask = 2;
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_Task, newTask));
        		}
        		else if (xClick < 223)
        		{
        			if (curTask != 3) newTask = 3;
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_Task, newTask));
        		}
        		else
        		{
        			if (curTask != 4) newTask = 4;
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_Task, newTask));
        		}
        	break;
        	case 8:		//page 8: change input side
        		int clickid = 0;
        		if (xClick < 185) { clickid = 0; }
        		else if (xClick < 196) { clickid = 1; }
    			else if (xClick < 207) { clickid = 2; }
				else if (xClick < 218) { clickid = 3; }
				else if (xClick < 229) { clickid = 4; }
				else { clickid = 5; }
        		
        		int newValue = this.entity.getStateMinor(ID.M.TaskSide) ^ Values.N.Pow2[clickid];
    			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_TaskSide, newValue));
        	break;
        	}
        break;
        case 5:	//AI operation 2
        	switch (this.showPageAI)
        	{
        	case 1:		//page 1: use ammo heavy button
        		this.switchPage1a[2] = this.entity.getStateFlag(ID.F.UseAmmoHeavy);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_AmmoHeavy, getInverseInt(this.switchPage1a[2])));
        	break;
        	case 3:		//page 3: change PVP first AI
        		this.switchPage3[2] = this.entity.getStateFlag(ID.F.PVPFirst);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_PVPAI, getInverseInt(this.switchPage3[2])));
        	break;
        	case 6:		//page 6: model display switch button 0~3
        		if (this.xClick < btCols[0])
        		{
            		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState01, getInverseInt(this.switchPage6[1])));
        		}
        		else if (this.xClick < btCols[1])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState02, getInverseInt(this.switchPage6[2])));
        		}
        		else if (this.xClick < btCols[2])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState03, getInverseInt(this.switchPage6[3])));
        		}
        		else
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState04, getInverseInt(this.switchPage6[4])));
        		}
        	break;
        	case 7:		//page 7: check metadata
        		int newValue = this.entity.getStateMinor(ID.M.TaskSide) ^ Values.N.Pow2[18];
    			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_TaskSide, newValue));
        	break;
        	}
        break;
        case 6:	//AI operation 3
        	switch (this.showPageAI)
        	{
        	case 1:		//page 1: use air light button
        		this.switchPage1a[3] = this.entity.getStateFlag(ID.F.UseAirLight);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_AirLight, getInverseInt(this.switchPage1a[3])));
        	break;
        	case 3:		//page 3: change onsight AI
        		this.switchPage3[3] = this.entity.getStateFlag(ID.F.AntiAir);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_AAAI, getInverseInt(this.switchPage3[3])));
        	break;
        	case 6:		//page 6: model display switch button 4~7
        		if (this.xClick < btCols[0])
        		{
            		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState05, getInverseInt(this.switchPage6[5])));
        		}
        		else if (this.xClick < btCols[1])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState06, getInverseInt(this.switchPage6[6])));
        		}
        		else if (this.xClick < btCols[2])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState07, getInverseInt(this.switchPage6[7])));
        		}
        		else
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState08, getInverseInt(this.switchPage6[8])));
        		}
        	break;
        	case 7:		//page 7: check ore dict
        		int newValue = this.entity.getStateMinor(ID.M.TaskSide) ^ Values.N.Pow2[19];
    			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_TaskSide, newValue));
        	break;
        	case 8:		//page 8: change output side
        		int clickid = 6;
        		if (xClick < 185) { clickid = 6; }
        		else if (xClick < 196) { clickid = 7; }
    			else if (xClick < 207) { clickid = 8; }
				else if (xClick < 218) { clickid = 9; }
				else if (xClick < 229) { clickid = 10; }
				else { clickid = 11; }
        		
        		int newValue2 = this.entity.getStateMinor(ID.M.TaskSide) ^ Values.N.Pow2[clickid];
    			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_TaskSide, newValue2));
        	break;
        	}
        break;
        case 7:	//AI operation 4
        	switch (this.showPageAI)
        	{
        	case 1:		//page 1: use air heavy button
        		this.switchPage1a[4] = this.entity.getStateFlag(ID.F.UseAirHeavy);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_AirHeavy, getInverseInt(this.switchPage1a[4])));
        	break;
        	case 3:		//page 3: change onsight AI
        		this.switchPage3[4] = this.entity.getStateFlag(ID.F.AntiSS);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ASMAI, getInverseInt(this.switchPage3[4])));
        	break;
        	case 6:		//page 6: model display switch button 8~11
        		if (this.xClick < btCols[0])
        		{
            		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState09, getInverseInt(this.switchPage6[9])));
        		}
        		else if (this.xClick < btCols[1])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState10, getInverseInt(this.switchPage6[10])));
        		}
        		else if (this.xClick < btCols[2])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState11, getInverseInt(this.switchPage6[11])));
        		}
        		else
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState12, getInverseInt(this.switchPage6[12])));
        		}
        	break;
        	case 7:		//page 7: check nbt tag
        		int newValue = this.entity.getStateMinor(ID.M.TaskSide) ^ Values.N.Pow2[20];
    			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_TaskSide, newValue));
        	break;
        	}
        break;
        case 8:	//AI operation 5
        	switch (this.showPageAI)
        	{
        	case 1:		//page 1: apply aura effect
        		this.switchPage1a[5] = this.entity.getStateFlag(ID.F.UseRingEffect);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_AuraEffect, getInverseInt(this.switchPage1a[5])));
        	break;
        	case 3:		//page 3: timekeeper AI
        		this.switchPage3[5] = this.entity.getStateFlag(ID.F.TimeKeeper);
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_TIMEKEEPAI, getInverseInt(this.switchPage3[5])));
        	break;
        	case 6:		//page 6: model display switch button 12~15
        		if (this.xClick < btCols[0])
        		{
            		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState13, getInverseInt(this.switchPage6[13])));
        		}
        		else if (this.xClick < btCols[1])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState14, getInverseInt(this.switchPage6[14])));
        		}
        		else if (this.xClick < btCols[2])
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState15, getInverseInt(this.switchPage6[15])));
        		}
        		else
        		{
        			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_ModelState16, getInverseInt(this.switchPage6[16])));
        		}
        	break;
        	case 8:		//page 8: change fuel side
        		int clickid = 12;
        		if (xClick < 185) { clickid = 12; }
        		else if (xClick < 196) { clickid = 13; }
    			else if (xClick < 207) { clickid = 14; }
				else if (xClick < 218) { clickid = 15; }
				else if (xClick < 229) { clickid = 16; }
				else { clickid = 17; }
        		
        		int newValue2 = this.entity.getStateMinor(ID.M.TaskSide) ^ Values.N.Pow2[clickid];
    			CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_TaskSide, newValue2));
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
        	CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_InvPage, 0));
        break;
        case 16:	//inventory page 1
        	if (this.entity.getInventoryPageSize() > 0)
        	{
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_InvPage, 1));
        	}
        break;
        case 17:	//inventory page 2
        	if (this.entity.getInventoryPageSize() > 1)
        	{
        		CommonProxy.channelG.sendToServer(new C2SGUIPackets(this.entity, C2SGUIPackets.PID.ShipBtn, ID.B.ShipInv_InvPage, 2));
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
		
		if (this.entity == null || !this.entity.isEntityAlive() ||
			this.entity.getDistanceToEntity(this.mc.player) > ConfigHandler.closeGUIDist)
		{
            this.mc.player.closeScreen();
        }
	}
	
	
}