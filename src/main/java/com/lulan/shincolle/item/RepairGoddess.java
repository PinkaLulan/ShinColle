package com.lulan.shincolle.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class RepairGoddess extends BasicItem
{
	
	private static final String NAME = "RepairGoddess";
	
	public RepairGoddess()
	{
		super();
		this.setTranslationKey(NAME);
		this.setMaxStackSize(16);
	}

	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, World world, List list, ITooltipFlag par4)
    {  	
    	list.add(TextFormatting.RED + I18n.format("gui.shincolle:repairgoddess"));
    }
    
	//item glow effect
  	@Override
  	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack item)
  	{
  		return true;
	}

  	
}
