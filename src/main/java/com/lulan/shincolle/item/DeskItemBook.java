package com.lulan.shincolle.item;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DeskItemBook extends BasicItem
{
	
	private static final String NAME = "DeskItemBook";
	
	
	public DeskItemBook()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setMaxStackSize(1);
        
        GameRegistry.register(this);
	}
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
		if (player != null)
		{	//開啟方塊GUI 參數:玩家, mod instance, gui ID, world, 自訂參數1,2,3
			FMLNetworkHandler.openGui(player, ShinColle.instance, ID.Gui.ADMIRALDESK, world, 2, 0, 0);
			return new ActionResult(EnumActionResult.SUCCESS, stack);
		}
		
        return new ActionResult(EnumActionResult.PASS, stack);
    }
	
	
}


