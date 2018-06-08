package com.lulan.shincolle.client.gui.inventory;

import javax.annotation.Nonnull;

import com.lulan.shincolle.capability.CapaInventoryExtend;
import com.lulan.shincolle.item.BasicEquip;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * slot for ship equips
 */
public class SlotShipEquip extends SlotExtend
{
    
    
    public SlotShipEquip(CapaInventoryExtend cpInv, int index, int x, int y)
    {
        super(cpInv, index, x, y);
    }
    
    /** accept equip item only */
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        Item item = stack.getItem();
        
        if (item instanceof BasicEquip)
        {
            return true;
        }
        
        return false;
    }
    
    
}