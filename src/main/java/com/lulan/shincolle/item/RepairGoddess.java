package com.lulan.shincolle.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
        
        GameRegistry.register(this);
	}

	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
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
