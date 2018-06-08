package com.lulan.shincolle.client.gui.inventory;

import com.lulan.shincolle.tileentity.TileEntityCrane;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotCrane extends SlotItemHandler
{
    
    private TileEntityCrane tile;

    
    public SlotCrane(TileEntityCrane tile, IItemHandler itemHandler, int slotIndex, int x, int y)
    {
        super(itemHandler, slotIndex, x, y);
        this.tile = (TileEntityCrane) tile;
    }
    
    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        return false;
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer player)
    {
        return false;
    }

    
}