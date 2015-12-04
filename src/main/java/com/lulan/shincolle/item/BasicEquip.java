package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import com.lulan.shincolle.crafting.EquipCalc;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;

abstract public class BasicEquip extends BasicItem implements IShipItemType {	
	byte types;
	
	public BasicEquip() {
		super();
		this.maxStackSize = 1;	
		this.setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i=0; i<types; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {  	
    	float[] itemStat = Values.EquipMap.get(EquipCalc.getEquipID(itemstack));
    	
//    	LogHelper.info("DEBUG : item info "+EquipCalc.getEquipID(itemstack)+" "+itemStat[0]);
    	
    	if(itemStat != null) {
    		if(itemStat[ID.E.HP] != 0F) list.add(EnumChatFormatting.RED + String.valueOf(itemStat[ID.E.HP])+ " " + I18n.format("gui.shincolle:hp"));
    		if(itemStat[ID.E.ATK_L] != 0F) list.add(EnumChatFormatting.RED + String.valueOf(itemStat[ID.E.ATK_L])+ " " + I18n.format("gui.shincolle:firepower1"));
    		if(itemStat[ID.E.ATK_H] != 0F) list.add(EnumChatFormatting.GREEN + String.valueOf(itemStat[ID.E.ATK_H])+ " " + I18n.format("gui.shincolle:torpedo"));
    		if(itemStat[ID.E.ATK_AL] != 0F) list.add(EnumChatFormatting.RED + String.valueOf(itemStat[ID.E.ATK_AL])+ " " + I18n.format("gui.shincolle:airfirepower"));
    		if(itemStat[ID.E.ATK_AH] != 0F) list.add(EnumChatFormatting.GREEN + String.valueOf(itemStat[ID.E.ATK_AH])+ " " + I18n.format("gui.shincolle:airtorpedo"));
    		if(itemStat[ID.E.DEF] != 0F) list.add(EnumChatFormatting.WHITE + String.valueOf(itemStat[ID.E.DEF])+ " " + I18n.format("gui.shincolle:armor"));
    		if(itemStat[ID.E.SPD] != 0F) list.add(EnumChatFormatting.WHITE + String.valueOf(itemStat[ID.E.SPD])+ " " + I18n.format("gui.shincolle:attackspeed"));
    		if(itemStat[ID.E.MOV] != 0F) list.add(EnumChatFormatting.GRAY + String.valueOf(itemStat[ID.E.MOV])+ " " + I18n.format("gui.shincolle:movespeed"));
    		if(itemStat[ID.E.HIT] != 0F) list.add(EnumChatFormatting.LIGHT_PURPLE + String.valueOf(itemStat[ID.E.HIT])+ " " + I18n.format("gui.shincolle:range"));
    		if(itemStat[ID.E.CRI] != 0F) list.add(EnumChatFormatting.AQUA + String.valueOf(itemStat[ID.E.CRI])+ " " + I18n.format("gui.shincolle:critical"));
    		if(itemStat[ID.E.DHIT] != 0F) list.add(EnumChatFormatting.YELLOW + String.valueOf(itemStat[ID.E.DHIT])+ " " + I18n.format("gui.shincolle:doublehit"));
    		if(itemStat[ID.E.THIT] != 0F) list.add(EnumChatFormatting.GOLD + String.valueOf(itemStat[ID.E.THIT])+ " " + I18n.format("gui.shincolle:triplehit"));
    		if(itemStat[ID.E.MISS] != 0F) list.add(EnumChatFormatting.RED + String.valueOf(itemStat[ID.E.MISS])+ " " + I18n.format("gui.shincolle:missreduce"));
    		if(itemStat[ID.E.AA] != 0F) list.add(EnumChatFormatting.YELLOW + String.valueOf(itemStat[ID.E.AA])+ " " + I18n.format("gui.shincolle:antiair"));
    		if(itemStat[ID.E.ASM] != 0F) list.add(EnumChatFormatting.AQUA + String.valueOf(itemStat[ID.E.ASM])+ " " + I18n.format("gui.shincolle:antiss"));
        	
    		if(itemStat[ID.E.LEVEL] == 1F) {
    			list.add(EnumChatFormatting.DARK_RED + I18n.format("gui.shincolle:notforcarrier"));
    		}
    		if(itemStat[ID.E.LEVEL] == 3F) {
    			list.add(EnumChatFormatting.DARK_AQUA + I18n.format("gui.shincolle:carrieronly"));
    		}
    	}
    	
    }

}
