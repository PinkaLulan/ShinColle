package com.lulan.shincolle.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

/**SLOT POSITION
 * no slot
 */
public class ContainerFormation extends Container
{
	
	
	public ContainerFormation() {}

	//玩家是否可以觸發右鍵點方塊事件
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	/** shift點物品的動作 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid)
	{
        return null;
    }

	
}
