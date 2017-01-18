package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TrainingBook extends BasicItem
{
	
	private static final String NAME = "TrainingBook";
	
	public TrainingBook()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
        
        GameRegistry.register(this);
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    {  	
    	list.add(TextFormatting.GOLD + I18n.format("gui.shincolle:trainingbook"));
    }
	
    
}