package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;


public class CombatRation extends BasicItem implements IShipCombatRation
{
	
	byte types;
	IIcon[] icons = new IIcon[6];
	
	
	public CombatRation()
	{
		super();
		this.setUnlocalizedName("CombatRation");
		this.setHasSubtypes(true);
		this.maxStackSize = 16;
		this.types = 6;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i=0; i<types; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
	{
		icons[0] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1));
		icons[1] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"1");
		icons[2] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"2");
		icons[3] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"3");
		icons[4] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"4");
		icons[5] = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".")+1)+"5");
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if (meta >= types) meta = 0;
	    return icons[meta];
	}
	
	@Override
	public float getFoodValue(int meta)
	{
		switch (meta)
		{
		case 1:
			return 3600F;
		case 2:
			return 4200F;
		case 3:
			return 5000F;
		case 4:
			return 5500F;
		case 5:
			return 6000F;
		default:
			return 3000F;
		}
	}

	@Override
	public float getSaturationValue(int meta)
	{
		switch (meta)
		{
		default:
			return 10F;
		}
	}

	@Override
	public int getSpecialEffect(int meta)
	{
		return 6;
	}

	@Override
	public int getMoraleValue(int meta)
	{
		switch (meta)
		{
		case 1:
			return 2100;
		case 2:
			return 2300;
		case 3:
			return 2500;
		case 4:
			return 2700;
		case 5:
			return 2900;
		default:
			return 1900;
		}
	}
	
	//display equip information
    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    {
    	if (itemstack != null)
    	{
    		int meta = itemstack.getItemDamage();
    		list.add(EnumChatFormatting.LIGHT_PURPLE+"+"+getMoraleValue(meta)+I18n.format("gui.shincolle:combatration"));
    		
    		String str = I18n.format("gui.shincolle:combatration"+meta);
    		String[] strs =  CalcHelper.stringConvNewlineToArray(str);
    		
    		for (String s : strs)
    		{
    			list.add(s);
    		}
    	}
    }
	
	
}
