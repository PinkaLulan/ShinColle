package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
        
        GameRegistry.register(this);
	}
	
	//item glow effect
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack item)
	{
        return true;
    }
	
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
	{
    	list.add(TextFormatting.RED + I18n.format("gui.shincolle:optool1"));
    	list.add(TextFormatting.AQUA + I18n.format("gui.shincolle:optool2"));
	}
	
	
}