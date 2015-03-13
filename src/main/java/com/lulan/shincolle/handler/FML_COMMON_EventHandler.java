package com.lulan.shincolle.handler;

import com.lulan.shincolle.entity.ExtendPlayerProps;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class FML_COMMON_EventHandler {

	//player update tick
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		ExtendPlayerProps extProps = (ExtendPlayerProps) event.player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);

		//check ring item (check for first found ring only) every 20 ticks
		if(event.player.ticksExisted % 20 == 0) {
			boolean hasRing = false;
			
			for(int i = 0; i < 36; ++i) {
				if(event.player.inventory.getStackInSlot(i) != null && 
				   event.player.inventory.getStackInSlot(i).getItem() == ModItems.MarriageRing) {
					hasRing = true;
					break;
				}
			}
			
			if(extProps != null) {
				//原本有ring, 變成沒有, 則取消飛行狀態
				if(extProps.hasRing() && !hasRing) {
					event.player.capabilities.isFlying = false;
				}
				//update hasRing flag
				extProps.setHasRing(hasRing);
			}
		}
		
	}//end onPlayerTick
	
}