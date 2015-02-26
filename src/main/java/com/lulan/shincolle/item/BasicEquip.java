package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import com.lulan.shincolle.crafting.EquipCalc;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicEquip extends BasicItem {	
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
    		if(itemStat[ID.HP_E] != 0F) list.add(EnumChatFormatting.RED + String.valueOf(itemStat[ID.HP_E])+ " " + I18n.format("gui.shincolle:hp"));
    		if(itemStat[ID.ATK_E] != 0F) list.add(EnumChatFormatting.AQUA + String.valueOf(itemStat[ID.ATK_E])+ " " + I18n.format("gui.shincolle:firepower1"));
    		if(itemStat[ID.ATK_H_E] != 0F) list.add(EnumChatFormatting.AQUA + String.valueOf(itemStat[ID.ATK_H_E])+ " " + I18n.format("gui.shincolle:torpedo"));
    		if(itemStat[ID.ATK_AL_E] != 0F) list.add(EnumChatFormatting.AQUA + String.valueOf(itemStat[ID.ATK_AL_E])+ " " + I18n.format("gui.shincolle:airfirepower"));
    		if(itemStat[ID.ATK_AH_E] != 0F) list.add(EnumChatFormatting.AQUA + String.valueOf(itemStat[ID.ATK_AH_E])+ " " + I18n.format("gui.shincolle:airtorpedo"));
    		if(itemStat[ID.DEF_E] != 0F) list.add(EnumChatFormatting.WHITE + String.valueOf(itemStat[ID.DEF_E])+ " " + I18n.format("gui.shincolle:armor"));
    		if(itemStat[ID.SPD_E] != 0F) list.add(EnumChatFormatting.YELLOW + String.valueOf(itemStat[ID.SPD_E])+ " " + I18n.format("gui.shincolle:attackspeed"));
    		if(itemStat[ID.MOV_E] != 0F) list.add(EnumChatFormatting.GREEN + String.valueOf(itemStat[ID.MOV_E])+ " " + I18n.format("gui.shincolle:movespeed"));
    		if(itemStat[ID.HIT_E] != 0F) list.add(EnumChatFormatting.LIGHT_PURPLE + String.valueOf(itemStat[ID.HIT_E])+ " " + I18n.format("gui.shincolle:range"));
    	
    		if(itemStat[ID.LEVEL_E] == 1F) {
    			list.add(EnumChatFormatting.DARK_RED + I18n.format("gui.shincolle:notforcarrier"));
    		}
    		if(itemStat[ID.LEVEL_E] == 3F) {
    			list.add(EnumChatFormatting.DARK_AQUA + I18n.format("gui.shincolle:carrieronly"));
    		}
    	}
    	
    }

}
