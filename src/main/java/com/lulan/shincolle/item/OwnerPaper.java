package com.lulan.shincolle.item;

import java.util.List;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
        
        GameRegistry.register(this);
	}
	
	//right click to sign the paper
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
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
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4)
    {
    	if(itemstack.hasTagCompound())
    	{
    		list.add(TextFormatting.RED + String.valueOf(itemstack.getTagCompound().getInteger(SignIDA)) +
    				 " " + TextFormatting.AQUA + itemstack.getTagCompound().getString(SignNameA));
    		list.add(TextFormatting.RED + String.valueOf(itemstack.getTagCompound().getInteger(SignIDB)) +
    				 " " + TextFormatting.AQUA + itemstack.getTagCompound().getString(SignNameB));
    	}
    }

    
}