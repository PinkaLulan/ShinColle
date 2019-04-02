package com.lulan.shincolle.item;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.reference.ID;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

import java.util.List;

public class RecipePaper extends BasicItem
{
	
	private static final String NAME = "RecipePaper";
	
	
	public RecipePaper()
	{
		super();
		this.setTranslationKey(NAME);
		this.setMaxStackSize(1);
	}
	
	//開啟GUI 參數:玩家, mod instance, gui ID, world, 自訂參數1,2,3
	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		if (player != null)
		{
			FMLNetworkHandler.openGui(player, ShinColle.instance, ID.Gui.RECIPE, world, 0, 0, 0);
			return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		}
		
        return new ActionResult(EnumActionResult.PASS, ItemStack.EMPTY);
    }
	
	//show recipe content
    @Override
    public void addInformation(ItemStack stack, World world, List list, ITooltipFlag advanced)
    {
    	super.addInformation(stack, world, list, advanced);
    	
    	if(stack.hasTagCompound())
    	{
    		//get recipe itemstack
    		NBTTagCompound nbt = stack.getTagCompound();
        	NBTTagList tagList = nbt.getTagList("Recipe", Constants.NBT.TAG_COMPOUND);
        	
        	if (tagList != null && tagList.tagCount() > 0)
        	{
        		NonNullList<ItemStack> stacks = NonNullList.withSize(10, ItemStack.EMPTY);
        		
	            for (int i = 0; i < tagList.tagCount(); i++)
	            {
	                NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
	                int slot = itemTags.getInteger("Slot");
	                
	                if (slot >= 0 && slot < 10)
	                {
	                	stacks.set(slot, new ItemStack(itemTags));
	                }
	            }
	    		
	            //show result stack
	            if (!stacks.get(9).isEmpty())
	            {
	            	list.add(TextFormatting.YELLOW + I18n.format("gui.shincolle:recipepaper.result") + " " + TextFormatting.WHITE + stacks.get(9).getDisplayName());
	            }
	            
	            list.add(TextFormatting.AQUA + I18n.format("gui.shincolle:recipepaper.material"));
	    		
	            
	            //show material stack
	    		for (int i = 0; i < 9; i++)
	    		{
	    			if (!stacks.get(i).isEmpty())
	        			list.add(TextFormatting.GRAY + "  " + stacks.get(i).getDisplayName());
				}
        	}
		}
    }
	
	
}