package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.entity.ExtendPlayerProps;

/**SLOT POSITION
 * no slot
 */
public class ContainerFormation extends Container {
	
	private InventoryPlayer playerInv;
	private EntityPlayer player;
	private ExtendPlayerProps extProps;
	
	
	public ContainerFormation(InventoryPlayer invPlayer, EntityPlayer player) {
		this.playerInv = invPlayer;
		this.player = player;
		this.extProps = (ExtendPlayerProps) player.getExtendedProperties(ExtendPlayerProps.PLAYER_EXTPROP_NAME);
		
	}

	//玩家是否可以觸發右鍵點方塊事件
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
	
	/** shift點物品的動作 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid) {
        return null;
    }

	
}



