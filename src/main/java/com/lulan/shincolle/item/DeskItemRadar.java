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

public class DeskItemRadar extends BasicItem
{
	
	private static final String NAME = "DeskItemRadar";
	
	
	public DeskItemRadar()
	{
		super();
		this.setTranslationKey(NAME);
		this.setMaxStackSize(1);
	}
	
	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
		if (player != null)
		{	//開啟方塊GUI 參數:玩家, mod instance, gui ID, world, 自訂參數1,2,3
			FMLNetworkHandler.openGui(player, ShinColle.instance, ID.Gui.ADMIRALDESK, world, 1, 0, 0);
			return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItemMainhand());
		}
		
        return new ActionResult(EnumActionResult.PASS, player.getHeldItemMainhand());
    }
	
	
}

