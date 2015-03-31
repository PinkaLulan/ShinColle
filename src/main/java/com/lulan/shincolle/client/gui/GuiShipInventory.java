package com.lulan.shincolle.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.lulan.shincolle.client.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipLarge;
import com.lulan.shincolle.network.C2SGUIPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

/**ICON_SHIPTYPE(157,18) 
 * NameIcon: LargeShip(0,0)(40x42) SmallShip(0,43)(30x30) 
 *           驅逐(41,0)(28x28) 輕巡(41,29) 重巡(41,58) 雷巡(41,87) 補給(12,74)
 *           戰艦(70,0) 航母(70,29) 輕母(70,58) 姬(70,87) 潛水(99,0) 浮游(99,29)
 *           
 * Color note:gold:16766720 gray:4210752 dark-gray:3158064 white:16777215 green:65280
 *            yellow:16776960 orange:16753920 red:16711680 cyan:65535
 *            magenta:16711935 pink:16751103
 */
public class GuiShipInventory extends GuiContainer {

	private BasicEntityShip entity;
	private InventoryPlayer player;
	private float xMouse, yMouse;
	private int xClick, yClick;
	private static final ResourceLocation TEXTURE_BG = new ResourceLocation(Reference.TEXTURES_GUI+"GuiShipInventory.png");
	private static final ResourceLocation TEXTURE_ICON = new ResourceLocation(Reference.TEXTURES_GUI+"GuiNameIcon.png");
	//draw string
	private List mouseoverList;
	private String titlename, shiplevel, lvMark, hpMark, canMelee, canLATK, canHATK, canALATK, canAHATK, 
	               strATK, strAATK, strLATK, strHATK, strALATK, strAHATK, strDEF, strSPD, strMOV, strHIT, 
	               Kills, Exp, Grudge, Owner, AmmoLight, AmmoHeavy, AirLight, AirHeavy, TarAI,
	               overText, strCri, strDhit, strThit, strMissMin, strMissMax, strMissAir,
	               marriage, followMin, followMax, fleeHP, followMinValue, followMaxValue,
	               fleeHPValue, barPosValue, auraEffect;
	private int hpCurrent, hpMax, color, showPage, showPageAI, pageIndicator, pageIndicatorAI, showAttack,
				fMinPos, fMaxPos, fleeHPPos, barPos, mousePressBar;
	private boolean switchMelee, switchLight, switchHeavy, switchAirLight, switchAirHeavy,
				switchTarAI, mousePress, switchAura;

	//ship type icon array
	private static final short[][] ICON_SHIPTYPE = {
		{41,0}, {41,29}, {41,58}, {41,87}, {70,58}, {70,29}, {70,0}, {12,74}, {99,0},
		{70,87}, {70,87}, {99,29}};
	//ship name icon array
	private static final short[][] ICON_SHIPNAME = {
		{128,0}, {139,0}, {150,0}, {161,0}, {172,0}, {183,0}, {194,0}, {205,0}, {216,0}, {227,0}, {238,0}, 
		{128,60}, {139,60}, {150,60}, {161,60}, {172,60}, {183,60}, {194,60}, {205,60}, {216,60}, {227,60}, {238,60}, 
		{128,120}, {139,120}, {150,120}, {161,120}, {172,120}, {183,120}, {194,120}, {205,120}, {216,120}, {227,120}, {238,120}, 
		{128,180}, {139,180}, {150,180}, {0,120}};
	
	public GuiShipInventory(InventoryPlayer invPlayer, BasicEntityShip entity1) {
		super(new ContainerShipInventory(invPlayer, entity1));
		mouseoverList = new ArrayList();			
		this.entity = entity1;
		this.player = invPlayer;
		this.xSize = 250;
		this.ySize = 214;
		this.showPage = 1;			//show page 1
		this.showPageAI = 1;		//show Ai control page 1
		this.showAttack = 1;		//show attack 1
		this.mousePress = false;	//no key clicked
		this.mousePressBar = -1;	//no bar pressed
	}
	
	//GUI前景: 文字 
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
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
	protected void drawGuiContainerBackgroundLayer(float par1,int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);	//RGBA
		
		//draw background
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_BG);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        
        //draw page indicator
        switch(this.showPage) {
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
        drawTexturedModalRect(guiLeft+127, guiTop+this.pageIndicator, 250, 0, 6, 34);
        
        //draw AI page indicator
        switch(this.showPageAI) {
        case 1:	{	//page 1
        	this.pageIndicatorAI = 131;
        	
        	//draw attack switch
            this.switchMelee = this.entity.getStateFlag(ID.F.UseMelee);
        	this.switchLight = this.entity.getStateFlag(ID.F.UseAmmoLight);
            this.switchHeavy = this.entity.getStateFlag(ID.F.UseAmmoHeavy);
            this.switchAirLight = this.entity.getStateFlag(ID.F.UseAirLight);
            this.switchAirHeavy = this.entity.getStateFlag(ID.F.UseAirHeavy);
            if(this.entity.getStateMinor(ID.N.TargetAI) == 0) {
            	this.switchTarAI = true;
            }
            else {
            	this.switchTarAI = false;
            }
            
            if(this.switchMelee) {
            	drawTexturedModalRect(guiLeft+174, guiTop+132, 0, 214, 11, 11);
            }
            else {
            	drawTexturedModalRect(guiLeft+174, guiTop+132, 11, 214, 11, 11);
            }
            
            if(this.switchLight) {
            	drawTexturedModalRect(guiLeft+174, guiTop+144, 0, 214, 11, 11);
            }
            else {
            	drawTexturedModalRect(guiLeft+174, guiTop+144, 11, 214, 11, 11);
            }
            
            if(this.switchHeavy) {
            	drawTexturedModalRect(guiLeft+174, guiTop+156, 0, 214, 11, 11);
            }
            else {
            	drawTexturedModalRect(guiLeft+174, guiTop+156, 11, 214, 11, 11);
            }
            
            if(this.switchAirLight) {
            	drawTexturedModalRect(guiLeft+174, guiTop+168, 0, 214, 11, 11);
            }
            else {
            	drawTexturedModalRect(guiLeft+174, guiTop+168, 11, 214, 11, 11);
            }
            
            if(this.switchAirHeavy) {
            	drawTexturedModalRect(guiLeft+174, guiTop+180, 0, 214, 11, 11);
            }
            else {
            	drawTexturedModalRect(guiLeft+174, guiTop+180, 11, 214, 11, 11);
            }
            
            if(this.switchTarAI) {
            	drawTexturedModalRect(guiLeft+174, guiTop+192, 0, 214, 11, 11);
            }
            else {
            	drawTexturedModalRect(guiLeft+174, guiTop+192, 11, 214, 11, 11);
            }
            
            break;
        	}	
        case 2: {	//page 2
        	this.pageIndicatorAI = 157;
        	
        	fMinPos = (int)(((float)(entity.getStateMinor(ID.N.FollowMin) - 1) / 30F) * 42F);
        	fMaxPos = (int)(((float)(entity.getStateMinor(ID.N.FollowMax) - 2) / 30F) * 42F);
        	fleeHPPos = (int)(((float)entity.getStateMinor(ID.N.FleeHP) / 100F) * 42F);
        	
        	//draw range bar
        	drawTexturedModalRect(guiLeft+191, guiTop+148, 31, 214, 43, 3);
        	drawTexturedModalRect(guiLeft+191, guiTop+172, 31, 214, 43, 3);
        	drawTexturedModalRect(guiLeft+191, guiTop+196, 31, 214, 43, 3);
        	
        	//draw range indicator
        	if(this.mousePressBar == 0) {
        		drawTexturedModalRect(guiLeft+187+barPos, guiTop+145, 22, 214, 9, 9);
        	}
        	else {
        		drawTexturedModalRect(guiLeft+187+fMinPos, guiTop+145, 22, 214, 9, 9);
        	}
        	
        	if(this.mousePressBar == 1) {
        		drawTexturedModalRect(guiLeft+187+barPos, guiTop+169, 22, 214, 9, 9);
        	}
        	else {
        		drawTexturedModalRect(guiLeft+187+fMaxPos, guiTop+169, 22, 214, 9, 9);
        	}
        	
        	if(this.mousePressBar == 2) {
        		drawTexturedModalRect(guiLeft+187+barPos, guiTop+193, 22, 214, 9, 9);
        	}
        	else {
        		drawTexturedModalRect(guiLeft+187+fleeHPPos, guiTop+193, 22, 214, 9, 9);
        	}
        	
        	break;
        	}
        case 3:	{	//page 3
    		this.pageIndicatorAI = 183;
    		
    		//draw attack switch
            this.switchAura = this.entity.getStateFlag(ID.F.UseRingEffect);
            
            if(this.switchAura) {
            	drawTexturedModalRect(guiLeft+174, guiTop+132, 0, 214, 11, 11);
            }
            else {
            	drawTexturedModalRect(guiLeft+174, guiTop+132, 11, 214, 11, 11);
            }
  
    		break;
    		}
        }//end AI page switch
        //draw AI page indicator
        drawTexturedModalRect(guiLeft+239, guiTop+this.pageIndicatorAI, 250, 0, 6, 24);
        
        //draw level, ship type icon
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_ICON);
        if(entity.getStateMinor(ID.N.ShipLevel) > 99) {
        	drawTexturedModalRect(guiLeft+157, guiTop+18, 0, 0, 40, 42);
        	drawTexturedModalRect(guiLeft+159, guiTop+22, ICON_SHIPTYPE[entity.getShipType()][0], ICON_SHIPTYPE[entity.getShipType()][1], 28, 28);
        }
        else {
        	drawTexturedModalRect(guiLeft+157, guiTop+18, 0, 43, 30, 30);
        	drawTexturedModalRect(guiLeft+157, guiTop+18, ICON_SHIPTYPE[entity.getShipType()][0], ICON_SHIPTYPE[entity.getShipType()][1], 28, 28);
        }
        
        //draw left bottom name
        drawTexturedModalRect(guiLeft+166, guiTop+63, ICON_SHIPNAME[entity.getShipID()][0], ICON_SHIPNAME[entity.getShipID()][1], 11, 59);
        
        //draw entity model
        drawEntityModel(guiLeft+210, guiTop+100, entity.getModelPos(), (float)(guiLeft + 200 - xMouse), (float)(guiTop + 50 - yMouse), this.entity);
        
	}
	
	//draw tooltip
	private void handleHoveringText() {
		//reset text
		mouseoverList.clear();
		
		if(this.showPage == 2) {
			//draw states value
			if(xMouse > 65+guiLeft && xMouse < 120+guiLeft) {
				//show text at ATTACK
				if(yMouse > 18+guiTop && yMouse < 40+guiTop) {
//					LogHelper.info("DEBUg : get tag "+this.entity.getEffectEquip(ID.EF_CRI));
					strCri = String.valueOf((int)(this.entity.getEffectEquip(ID.EF_CRI) * 100F));
					strDhit = String.valueOf((int)(this.entity.getEffectEquip(ID.EF_DHIT) * 100F));
					strThit = String.valueOf((int)(this.entity.getEffectEquip(ID.EF_THIT) * 100F));
				
					//add mouseover text
					overText = I18n.format("gui.shincolle:firepower1") + " " + strATK;
					mouseoverList.add(overText);
					overText = I18n.format("gui.shincolle:firepower2") + " " + strAATK;
					mouseoverList.add(overText);
					overText = I18n.format("gui.shincolle:critical") + " " + strCri + " %";
					mouseoverList.add(overText);
					overText = I18n.format("gui.shincolle:doublehit") + " " + strDhit + " %";
					mouseoverList.add(overText);
					overText = I18n.format("gui.shincolle:triplehit") + " " + strThit + " %";
					mouseoverList.add(overText);
					this.drawHoveringText(mouseoverList, 55, 143, this.fontRendererObj);
				}
				//show text at RANGE
				else if(yMouse > 104+guiTop && yMouse < 126+guiTop) {
					//calc min miss
					int temp = (int) ((0.2F - this.entity.getEffectEquip(ID.EF_MISS) - 0.001F * this.entity.getStateMinor(ID.N.ShipLevel)) * 100F);
					if(temp < 0) temp = 0;
					if(temp > 35) temp = 35;
					strMissMin = String.valueOf(temp);
//					LogHelper.info("DEBUg : miss after "+this.entity.getStateFinal(ID.HIT));
					//calc max miss
					temp = (int) ((0.35F - this.entity.getEffectEquip(ID.EF_MISS) - 0.001F * this.entity.getStateMinor(ID.N.ShipLevel)) * 100F);
					if(temp < 0) temp = 0;
					if(temp > 35) temp = 35;
					strMissMax = String.valueOf(temp);
					
					//calc air miss
					temp = (int) ((0.25F - this.entity.getEffectEquip(ID.EF_MISS) - 0.001F * this.entity.getStateMinor(ID.N.ShipLevel)) * 100F);
					if(temp < 0) temp = 0;
					if(temp > 35) temp = 35;
					strMissAir = String.valueOf(temp);		

					overText = I18n.format("gui.shincolle:missrate") + " " + strMissMin + " ~ " + strMissMax + " %";
					mouseoverList.add(overText);
					overText = I18n.format("gui.shincolle:missrateair") + " " + strMissAir + " %";
					mouseoverList.add(overText);
					this.drawHoveringText(mouseoverList, 55, 143, this.fontRendererObj);
				}
			}
		}
			
	}
	
	//get new mouseX,Y and draw gui
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		xMouse = mouseX;
		yMouse = mouseY;
	}
	
	//draw entity model, copy from player inventory class
	public static void drawEntityModel(int x, int y, float[] modelPos, float yaw, float pitch, BasicEntityShip entity) {		
		//set basic position and rotation
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(x + modelPos[0], y + modelPos[1], 50.0F + modelPos[2]);
		GL11.glScalef(-modelPos[3], modelPos[3], modelPos[3]);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f2 = entity.renderYawOffset;
		float f3 = entity.rotationYaw;
		float f4 = entity.rotationPitch;
		float f5 = entity.prevRotationYawHead;
		float f6 = entity.rotationYawHead;
		
		//set the light of model (face to player)
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		
		//set head look angle
		GL11.glRotatef(-((float) Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		entity.renderYawOffset = (float) Math.atan(yaw / 40.0F) * 20.0F;
		entity.rotationYaw = (float) Math.atan(yaw / 40.0F) * 40.0F;
		entity.rotationPitch = -((float) Math.atan(pitch / 40.0F)) * 20.0F;
		entity.rotationYawHead = entity.rotationYaw;
		entity.prevRotationYawHead = entity.rotationYaw;		
		GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		entity.renderYawOffset = f2;
		entity.rotationYaw = f3;
		entity.rotationPitch = f4;
		entity.prevRotationYawHead = f5;
		entity.rotationYawHead = f6;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	//draw level,hp,atk,def...
	private void drawAttributes() {
		//draw hp, level
		shiplevel = String.valueOf(entity.getStateMinor(ID.N.ShipLevel));
		lvMark = I18n.format("gui.shincolle:level");
		hpMark = I18n.format("gui.shincolle:hp");
		hpCurrent = MathHelper.ceiling_float_int(entity.getHealth());
		hpMax = MathHelper.ceiling_float_int(entity.getMaxHealth());
		color = 0;

		//draw lv/hp name
		this.fontRendererObj.drawStringWithShadow(lvMark, 223-this.fontRendererObj.getStringWidth(lvMark), 6, 65535);
		this.fontRendererObj.drawStringWithShadow(hpMark, 144-this.fontRendererObj.getStringWidth(hpMark), 6, 65535);
		
		//draw level: 150->gold other->white
		if(entity.getStateMinor(ID.N.ShipLevel) < 150) {
			color = 16777215;  //white
		}
		else {
			color = 16766720;  //gold	
		}
		this.fontRendererObj.drawStringWithShadow(shiplevel, xSize-7-this.fontRendererObj.getStringWidth(shiplevel), 6, color);

		//draw hp/maxhp, if currHP < maxHP, use darker color
		color = GuiHelper.pickColor(entity.getBonusPoint(ID.HP));
		this.fontRendererObj.drawStringWithShadow("/"+String.valueOf(hpMax), 148 + this.fontRendererObj.getStringWidth(String.valueOf(hpCurrent)), 6, color);
		if(hpCurrent < hpMax) {
			switch(entity.getBonusPoint(ID.HP)) {
			case 0:
				color = 16119285;	//gray
				break;
			case 1:
				color = 13421568;	//dark yellow
				break;
			case 2:
				color = 16747520;	//dark orange
				break;
			default:
				color = 13107200;	//dark red
				break;
			}
		}
		this.fontRendererObj.drawStringWithShadow(String.valueOf(hpCurrent), 147, 6, color);	
				
		//draw string in different page
		switch(this.showPage) {
		case 2: {	//page 2: attribute page
			strLATK = String.format("%.1f", this.entity.getStateFinal(ID.ATK));
			strHATK = String.format("%.1f", this.entity.getStateFinal(ID.ATK_H));
			strATK = strLATK + "/" + strHATK;
			strALATK = String.format("%.1f", this.entity.getStateFinal(ID.ATK_AL));
			strAHATK = String.format("%.1f", this.entity.getStateFinal(ID.ATK_AH));
			strAATK = strALATK + "/" + strAHATK;
			strDEF = String.format("%.2f", this.entity.getStateFinal(ID.DEF))+"%";
			strSPD = String.format("%.2f", this.entity.getStateFinal(ID.SPD));
			strMOV = String.format("%.2f", this.entity.getStateFinal(ID.MOV));
			strHIT = String.format("%.2f", this.entity.getStateFinal(ID.HIT));
			
			//draw firepower
			if(this.showAttack == 1) {	//show cannon attack
				this.fontRendererObj.drawString(I18n.format("gui.shincolle:firepower1"), 67, 20, GuiHelper.pickColor(5));
				color = GuiHelper.pickColor(entity.getBonusPoint(ID.ATK));
				this.fontRendererObj.drawStringWithShadow(strATK, 125-this.fontRendererObj.getStringWidth(strATK), 30, color);
			}
			else {						//show aircraft attack
				this.fontRendererObj.drawString(I18n.format("gui.shincolle:firepower2"), 67, 20, GuiHelper.pickColor(5));
				color = GuiHelper.pickColor(entity.getBonusPoint(ID.ATK));
				this.fontRendererObj.drawStringWithShadow(strAATK, 125-this.fontRendererObj.getStringWidth(strAATK), 30, color);
			}
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:armor"), 67, 41, GuiHelper.pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:attackspeed"), 67, 62, GuiHelper.pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:movespeed"), 67, 83, GuiHelper.pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:range"), 67, 104, GuiHelper.pickColor(5));
			
			//draw armor
			color = GuiHelper.pickColor(entity.getBonusPoint(ID.DEF));
			this.fontRendererObj.drawStringWithShadow(strDEF, 125-this.fontRendererObj.getStringWidth(strDEF), 51, color);
			
			//draw attack speed
			color = GuiHelper.pickColor(entity.getBonusPoint(ID.SPD));
			this.fontRendererObj.drawStringWithShadow(strSPD, 125-this.fontRendererObj.getStringWidth(strSPD), 72, color);
			
			//draw movement speed
			color = GuiHelper.pickColor(entity.getBonusPoint(ID.MOV));
			this.fontRendererObj.drawStringWithShadow(strMOV, 125-this.fontRendererObj.getStringWidth(strMOV), 93, color);
					
			//draw range
			color = GuiHelper.pickColor(entity.getBonusPoint(ID.HIT));
			this.fontRendererObj.drawStringWithShadow(strHIT, 125-this.fontRendererObj.getStringWidth(strHIT), 114, color);
			break;
			}
		case 1:	{	//page 1: exp, kills, L&H ammo, fuel
			//draw string
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:kills"), 67, 20, GuiHelper.pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:exp"), 67, 41, GuiHelper.pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:ammolight"), 67, 62, GuiHelper.pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:ammoheavy"), 67, 83, GuiHelper.pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:grudge"), 67, 104, GuiHelper.pickColor(5));
			//draw value
			entity.setExpNext();  //update exp value
			Exp = String.valueOf(this.entity.getStateMinor(ID.N.ExpCurrent))+"/"+String.valueOf(this.entity.getStateMinor(ID.N.ExpNext));
			Kills = String.valueOf(this.entity.getStateMinor(ID.N.Kills));
			AmmoLight = String.valueOf(this.entity.getStateMinor(ID.N.NumAmmoLight));
			AmmoHeavy = String.valueOf(this.entity.getStateMinor(ID.N.NumAmmoHeavy));
			Grudge = String.valueOf(this.entity.getStateMinor(ID.N.NumGrudge));
				
			this.fontRendererObj.drawStringWithShadow(Kills, 125-this.fontRendererObj.getStringWidth(Kills), 30, GuiHelper.pickColor(0));
			this.fontRendererObj.drawStringWithShadow(Exp, 125-this.fontRendererObj.getStringWidth(Exp), 51, GuiHelper.pickColor(0));
			this.fontRendererObj.drawStringWithShadow(AmmoLight, 125-this.fontRendererObj.getStringWidth(AmmoLight), 72, GuiHelper.pickColor(0));
			this.fontRendererObj.drawStringWithShadow(AmmoHeavy, 125-this.fontRendererObj.getStringWidth(AmmoHeavy), 93, GuiHelper.pickColor(0));
			this.fontRendererObj.drawStringWithShadow(Grudge, 125-this.fontRendererObj.getStringWidth(Grudge), 114, GuiHelper.pickColor(0));
						
			break;
			}
		case 3: {	//page 3: owner name, light/heavy airplane, marriage
			//draw string
//			this.fontRendererObj.drawString(I18n.format("gui.shincolle:owner"), 67, 20, GuiHelper.pickColor(5));
			this.fontRendererObj.drawString(I18n.format("gui.shincolle:marriage"), 67, 20, GuiHelper.pickColor(5));
			
			//draw value
//			Owner = this.entity.getOwnerName();
			
			if(this.entity.getStateFlag(ID.F.IsMarried)) {
				marriage = I18n.format("gui.shincolle:married");
			}
			else {
				marriage = I18n.format("gui.shincolle:unmarried");
			}	
			
			//大型艦, 顯示艦載機數量
			if(this.entity instanceof BasicEntityShipLarge) {
				this.fontRendererObj.drawString(I18n.format("gui.shincolle:airplanelight"), 67, 83, GuiHelper.pickColor(5));
				this.fontRendererObj.drawString(I18n.format("gui.shincolle:airplaneheavy"), 67, 104, GuiHelper.pickColor(5));
				AirLight = String.valueOf(((BasicEntityShipLarge)this.entity).getNumAircraftLight());
				AirHeavy = String.valueOf(((BasicEntityShipLarge)this.entity).getNumAircraftHeavy());
				this.fontRendererObj.drawStringWithShadow(AirLight, 125-this.fontRendererObj.getStringWidth(AirLight), 93, GuiHelper.pickColor(1));
				this.fontRendererObj.drawStringWithShadow(AirHeavy, 125-this.fontRendererObj.getStringWidth(AirHeavy), 114, GuiHelper.pickColor(1));	
				
			}
			
//			this.fontRendererObj.drawStringWithShadow(Owner, 125-this.fontRendererObj.getStringWidth(Owner), 30, GuiHelper.pickColor(1));
			this.fontRendererObj.drawStringWithShadow(marriage, 125-this.fontRendererObj.getStringWidth(marriage), 30, GuiHelper.pickColor(1));
			
			break;
			}//end case 3
		}//end page switch
		
		//draw AI page
		switch(this.showPageAI) {
		case 1:	{	//attack AI page
				//draw string
				canMelee = I18n.format("gui.shincolle:canmelee");
				canLATK = I18n.format("gui.shincolle:canlightattack");
				canHATK = I18n.format("gui.shincolle:canheavyattack");
				canALATK = I18n.format("gui.shincolle:canairlightattack");
				canAHATK = I18n.format("gui.shincolle:canairheavyattack");
				TarAI = I18n.format("gui.shincolle:targetAI");
				
				this.fontRendererObj.drawString(canMelee, 187, 134, GuiHelper.pickColor(5));
				this.fontRendererObj.drawString(canLATK, 187, 146, GuiHelper.pickColor(5));
				this.fontRendererObj.drawString(canHATK, 187, 158, GuiHelper.pickColor(5));
				this.fontRendererObj.drawString(canALATK, 187, 170, GuiHelper.pickColor(5));
				this.fontRendererObj.drawString(canAHATK, 187, 182, GuiHelper.pickColor(5));
				this.fontRendererObj.drawString(TarAI, 187, 194, GuiHelper.pickColor(5));
			}
			break;
		case 2:	{	//follow, flee AI page
				//draw string
				followMin = I18n.format("gui.shincolle:followmin");
				followMax = I18n.format("gui.shincolle:followmax");
				fleeHP = I18n.format("gui.shincolle:fleehp");
				
				this.fontRendererObj.drawString(followMin, 174, 134, GuiHelper.pickColor(5));
				this.fontRendererObj.drawString(followMax, 174, 158, GuiHelper.pickColor(5));
				this.fontRendererObj.drawString(fleeHP, 174, 182, GuiHelper.pickColor(5));
				
				//draw value
				followMinValue = String.valueOf(entity.getStateMinor(ID.N.FollowMin));
				followMaxValue = String.valueOf(entity.getStateMinor(ID.N.FollowMax));
				fleeHPValue = String.valueOf(entity.getStateMinor(ID.N.FleeHP));
				
				if(this.mousePressBar == 0) {
					barPosValue = String.valueOf((int)((float)barPos / 42F * 30F + 1F));
					this.fontRendererObj.drawStringWithShadow(barPosValue, 174, 145, GuiHelper.pickColor(3));
				}
				else {
					this.fontRendererObj.drawStringWithShadow(followMinValue, 174, 145, GuiHelper.pickColor(1));
				}
				
				if(this.mousePressBar == 1) {
					barPosValue = String.valueOf((int)((float)barPos / 42F * 30F + 2F));
					this.fontRendererObj.drawStringWithShadow(barPosValue, 174, 169, GuiHelper.pickColor(3));		
				}
				else {
					this.fontRendererObj.drawStringWithShadow(followMaxValue, 174, 169, GuiHelper.pickColor(1));
				}
				
				if(this.mousePressBar == 2) {
					barPosValue = String.valueOf((int)((float)barPos / 42F * 100F));
					this.fontRendererObj.drawStringWithShadow(barPosValue, 174, 193, GuiHelper.pickColor(3));
				}
				else {
					this.fontRendererObj.drawStringWithShadow(fleeHPValue, 174, 193, GuiHelper.pickColor(1));
				}	
			}
			break;
		case 3: {	//aura effect
			//draw string
			auraEffect = I18n.format("gui.shincolle:auraeffect");
			
			this.fontRendererObj.drawString(auraEffect, 187, 134, GuiHelper.pickColor(5));
			
			}
			break;
		}//end AI page switch
	}
	
	//mouse press + move
    protected void mouseClickMove(int posX, int posY, int mouseKey, long pressTime) {
    	super.mouseClickMove(posX, posY, mouseKey, pressTime);

    	//get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        
        barPos = xClick - 191;
        if(barPos > 42) barPos = 42;
        if(barPos < 0)  barPos = 0;

    }
    
    //state: -1:move 0:left up 1:right up 2:...
    protected void mouseMovedOrUp(int posX, int posY, int state) {
    	super.mouseMovedOrUp(posX, posY, state);

    	//get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        
    	//get cliuck button
    	switch(mousePressBar) {
    	case 0:	//bar0: follow min
    		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_FollowMin, (int)((float)barPos / 42F * 30F + 1F)));
    		break;
    	case 1:	//bar1: follow max
    		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_FollowMax, (int)((float)barPos / 42F * 30F + 2F)));
    		break;
    	case 2:	//bar2: flee hp
    		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_FleeHP, (int)((float)barPos / 42F * 100F)));
    		break;
    	}
    	
    	//reset flag
    	mousePress = false;
    	mousePressBar = -1;
    }
	
	//handle mouse click, @parm posX, posY, mouseKey (0:left 1:right 2:middle 3:...etc)
	@Override
	protected void mouseClicked(int posX, int posY, int mouseKey) {
        super.mouseClicked(posX, posY, mouseKey);
        
        //get click position
        xClick = posX - this.guiLeft;
        yClick = posY - this.guiTop;
        mousePress = true;
        
        //check press bar
        if(this.showPageAI == 2) {
        	switch(GuiHelper.getButton(ID.G.SHIPINVENTORY, 2, xClick, yClick)) {
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
        
        //match button
        switch(GuiHelper.getButton(ID.G.SHIPINVENTORY, 0, xClick, yClick)) {
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
        	if(this.showPageAI == 1) {	//page 1: can melee button
        		this.switchMelee = this.entity.getStateFlag(ID.F.UseMelee);
        		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_Melee, getInverseInt(this.switchMelee)));
        	}
        	else if(this.showPageAI == 3) {	//page 3: apply aura effect
        		this.switchAura = this.entity.getStateFlag(ID.F.UseRingEffect);
        		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_AuraEffect, getInverseInt(this.switchAura)));
        	}
        	break;
        case 4:	//AI operation 1 
        	if(this.showPageAI == 1) {	//page 1: use ammo light button
        		this.switchLight = this.entity.getStateFlag(ID.F.UseAmmoLight);
        		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_AmmoLight, getInverseInt(this.switchLight)));
        	}
        	break;
        case 5:	//AI operation 2 
        	if(this.showPageAI == 1) {	//page 1: use ammo heavy button
        		this.switchHeavy = this.entity.getStateFlag(ID.F.UseAmmoHeavy);
        		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_AmmoHeavy, getInverseInt(this.switchHeavy)));
        	}
        	break;
        case 6:	//AI operation 3
        	if(this.showPageAI == 1) {	//page 1: use air light button
        		this.switchAirLight = this.entity.getStateFlag(ID.F.UseAirLight);
        		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_AirLight, getInverseInt(this.switchAirLight)));
        	}
        	break;
        case 7:	//AI operation 4
        	if(this.showPageAI == 1) {	//page 1: use air heavy button
        		this.switchAirHeavy = this.entity.getStateFlag(ID.F.UseAirHeavy);
        		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_AirHeavy, getInverseInt(this.switchAirHeavy)));
        	}
        	break;
        case 8:	//AI operation 5
        	if(this.showPageAI == 1) {	//page 1: change target AI
        		if(this.entity.getStateMinor(ID.N.TargetAI) == 0) {
        			this.switchTarAI = true;
        		}
        		else {
        			this.switchTarAI = false;
        		}
        		CommonProxy.channel.sendToServer(new C2SGUIPackets(this.entity, ID.B.ShipInv_TarAI, getInverseInt(!this.switchTarAI)));
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
    	}//end all page switch
        
        if(this.showPage == 2) {	//page 2: damage display switch
        	switch(GuiHelper.getButton(ID.G.SHIPINVENTORY, 1, xClick, yClick)) {
        	case 0:
        		if(this.showAttack == 1) {
        			this.showAttack = 2;
        		}
        		else {
        			this.showAttack = 1;
        		}
        		break;
        	}
        }

	}
	
	//return 0 if par1 = true
	private int getInverseInt(boolean par1) {
		return par1 ? 0 : 1;
	}
	

}
