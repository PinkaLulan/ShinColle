package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class EquipCannon extends BasicEquip {
	
	public EquipCannon() {
		super();
		this.setUnlocalizedName("EquipCannon");
		this.types = 9;
	}	
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
    	int meta = itemstack.getItemDamage();   	
    	switch(meta) {
    	case 0:	//5-Inch Single Cannon
    		list.add(EnumChatFormatting.RED +  "+2 " + I18n.format("gui.shincolle:firepower"));
    		list.add(EnumChatFormatting.AQUA + "+1 " + I18n.format("gui.shincolle:range"));
    		break;
    	case 1: //6-Inch Single Cannon
    		list.add(EnumChatFormatting.RED +  "+3 " + I18n.format("gui.shincolle:firepower"));
    		list.add(EnumChatFormatting.AQUA + "+1 " + I18n.format("gui.shincolle:range"));
    		break;
    	case 2: //5-Inch Twin Cannon
    		list.add(EnumChatFormatting.RED +        "+3    " + I18n.format("gui.shincolle:firepower"));
    		list.add(EnumChatFormatting.GREEN +      "+0.2  " + I18n.format("gui.shincolle:attackspeed"));
    		list.add(EnumChatFormatting.LIGHT_PURPLE+"-0.04 " + I18n.format("gui.shincolle:movespeed"));
    		list.add(EnumChatFormatting.AQUA +       "+1    " + I18n.format("gui.shincolle:range"));
    		break;
    	case 3: //6-Inch Twin Rapid-Fire Cannon
    		list.add(EnumChatFormatting.RED +        "+4    " + I18n.format("gui.shincolle:firepower"));
    		list.add(EnumChatFormatting.GREEN +      "+0.4  " + I18n.format("gui.shincolle:attackspeed"));
    		list.add(EnumChatFormatting.LIGHT_PURPLE+"-0.04 " + I18n.format("gui.shincolle:movespeed"));
    		list.add(EnumChatFormatting.AQUA +       "+1    " + I18n.format("gui.shincolle:range"));
    		break;
    	case 4: //12.5-Inch Twin Secondary Cannon
    		list.add(EnumChatFormatting.RED +        "+8    " + I18n.format("gui.shincolle:firepower"));
    		list.add(EnumChatFormatting.GREEN +      "+0.2  " + I18n.format("gui.shincolle:attackspeed"));
    		list.add(EnumChatFormatting.LIGHT_PURPLE+"-0.08 " + I18n.format("gui.shincolle:movespeed"));
    		list.add(EnumChatFormatting.AQUA +       "+2    " + I18n.format("gui.shincolle:range"));
    		break;
    	case 5: //14-Inch Twin Cannon
    		list.add(EnumChatFormatting.RED +        "+12   " + I18n.format("gui.shincolle:firepower"));
    		list.add(EnumChatFormatting.GREEN +      "+0.2  " + I18n.format("gui.shincolle:attackspeed"));
    		list.add(EnumChatFormatting.LIGHT_PURPLE+"-0.08 " + I18n.format("gui.shincolle:movespeed"));
    		list.add(EnumChatFormatting.AQUA +       "+3    " + I18n.format("gui.shincolle:range"));
    		break;
    	case 6: //16-Inch Twin Cannon
    		list.add(EnumChatFormatting.RED +        "+14   " + I18n.format("gui.shincolle:firepower"));
    		list.add(EnumChatFormatting.GREEN +      "+0.2  " + I18n.format("gui.shincolle:attackspeed"));
    		list.add(EnumChatFormatting.LIGHT_PURPLE+"-0.08 " + I18n.format("gui.shincolle:movespeed"));
    		list.add(EnumChatFormatting.AQUA +       "+4    " + I18n.format("gui.shincolle:range"));
    		break;
    	case 7: //8-Inch Triple Cannon
    		list.add(EnumChatFormatting.RED +        "+9    " + I18n.format("gui.shincolle:firepower"));
    		list.add(EnumChatFormatting.GREEN +      "+0.4  " + I18n.format("gui.shincolle:attackspeed"));
    		list.add(EnumChatFormatting.LIGHT_PURPLE+"-0.12 " + I18n.format("gui.shincolle:movespeed"));
    		list.add(EnumChatFormatting.AQUA +       "+3    " + I18n.format("gui.shincolle:range"));
    		break;
    	case 8: //16-Inch Triple Cannon
    		list.add(EnumChatFormatting.RED +        "+18   " + I18n.format("gui.shincolle:firepower"));
    		list.add(EnumChatFormatting.GREEN +      "+0.4  " + I18n.format("gui.shincolle:attackspeed"));
    		list.add(EnumChatFormatting.LIGHT_PURPLE+"-0.12 " + I18n.format("gui.shincolle:movespeed"));
    		list.add(EnumChatFormatting.AQUA +       "+4    " + I18n.format("gui.shincolle:range"));
    		break;
    	}
    }

}
