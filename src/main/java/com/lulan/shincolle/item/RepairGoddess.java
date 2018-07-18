package com.lulan.shincolle.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RepairGoddess extends BasicItem
{
	
	private static final String NAME = "RepairGoddess";
	
	public RepairGoddess()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(16);
	}

	//item glow effect
  	@Override
  	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack item)
  	{
  		return true;
	}
  	
	//display equip information
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {  	
    	list.add(TextFormatting.RED + I18n.format("gui.shincolle:repairgoddess"));
    }
    
  	
}