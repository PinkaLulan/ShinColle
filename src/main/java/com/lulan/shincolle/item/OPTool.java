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

/**
 * OP tool
 * 
 * 1. add/remove unattackable target (press numpad 1/2)
 * 2. show unattackable target list (press numpad 3)
 * 3. -
 * 
 */
public class OPTool extends BasicItem
{
	
	private static final String NAME = "OPTool";
	
	
	public OPTool()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
		this.setFull3D();
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item)
	{
        return true;
    }
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
    	list.add(TextFormatting.RED + I18n.format("gui.shincolle:optool1"));
    	list.add(TextFormatting.AQUA + I18n.format("gui.shincolle:optool2"));
	}
	
	
}