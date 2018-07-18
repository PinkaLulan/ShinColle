package com.lulan.shincolle.item;

import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.capability.CapaTeitoku;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class OwnerPaper extends BasicItem
{
	
	public static final String SignNameA = "SignNameA";	//player name tag
	public static final String SignNameB = "SignNameB";
	public static final String SignIDA = "SignIDA";		//player id tag
	public static final String SignIDB = "SignIDB";
	private static final String NAME = "OwnerPaper";
	
	
	public OwnerPaper()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
	}
	
	//right click to sign the paper
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		ItemStack stack = player.getHeldItem(hand);
		
		//server side
		if (!world.isRemote && !player.isSneaking())
		{
			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(player);

			if (capa != null)
			{
				//first time use
				if (!stack.hasTagCompound())
				{
					stack.setTagCompound(new NBTTagCompound());
					stack.getTagCompound().setString(SignNameA, player.getName());
					stack.getTagCompound().setString(SignNameB, "");
					stack.getTagCompound().setInteger(SignIDA, capa.getPlayerUID());
					stack.getTagCompound().setInteger(SignIDB, -1);
					stack.getTagCompound().setBoolean("signPos", false);
				}
				//use > second time
				else
				{
					//signPos: true -> sign at A, false -> sign at B
					if (stack.getTagCompound().getBoolean("signPos"))
					{
						stack.getTagCompound().setString(SignNameA, player.getName());
						stack.getTagCompound().setInteger(SignIDA, capa.getPlayerUID());
						stack.getTagCompound().setBoolean("signPos", false);
					}
					else
					{
						stack.getTagCompound().setString(SignNameB, player.getName());
						stack.getTagCompound().setInteger(SignIDB, capa.getPlayerUID());
						stack.getTagCompound().setBoolean("signPos", true);
					}
				}
			}//end extprops != null
		}
			
        return new ActionResult(EnumActionResult.PASS, stack);
    }
	
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
    	if(stack.hasTagCompound())
    	{
    		list.add(TextFormatting.RED + String.valueOf(stack.getTagCompound().getInteger(SignIDA)) +
    				 " " + TextFormatting.AQUA + stack.getTagCompound().getString(SignNameA));
    		list.add(TextFormatting.RED + String.valueOf(stack.getTagCompound().getInteger(SignIDB)) +
    				 " " + TextFormatting.AQUA + stack.getTagCompound().getString(SignNameB));
    	}
    }

    
}