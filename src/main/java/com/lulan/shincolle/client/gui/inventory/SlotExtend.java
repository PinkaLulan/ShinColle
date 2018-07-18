package com.lulan.shincolle.client.gui.inventory;

import javax.annotation.Nonnull;

import com.lulan.shincolle.capability.CapaInventoryExtend;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

/**
 * slot handler for extensible inventory {@link CapaInventoryExtend}
 * copy from {@link SlotItemHandler}
 */
public class SlotExtend extends Slot
{
    
    private static IInventory emptyInventory = new InventoryBasic("[Null]", true, 0);
    private final CapaInventoryExtend cpInv;
    private final int index;
    
    
    public SlotExtend(CapaInventoryExtend cpInv, int index, int xPosition, int yPosition)
    {
        super(emptyInventory, index, xPosition, yPosition);
        this.cpInv = cpInv;
        this.index = index;
    }
    
    public int getSlotIndex()
    {
        return this.cpInv.getBaseSlotIndex() + index;
    }
    
    /**
     * Helper fnct to get the stack in the slot.
     */
    @Override
    @Nonnull
    public ItemStack getStack()
    {
        return this.cpInv.getStackInSlot(this.getSlotIndex());
    }
    
    // Override if your IItemHandler does not implement IItemHandlerModifiable
    /**
     * Helper method to put a stack in the slot.
     */
    @Override
    public void putStack(@Nonnull ItemStack stack)
    {
        this.cpInv.setStackInSlot(this.getSlotIndex(), stack);
        this.onSlotChanged();
    }
    
    /**
     * if stack2 has more items than stack1, onCrafting(item,countIncrease) is called
     */
    @Override
    public void onSlotChange(@Nonnull ItemStack stack1, @Nonnull ItemStack stack2) {}
    
    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    @Override
    public int getSlotStackLimit()
    {
        return this.cpInv.getSlotLimit(this.getSlotIndex());
    }
    
    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack)
    {
        ItemStack maxAdd = stack.copy();
        int maxInput = stack.getMaxStackSize();
        maxAdd.setCount(maxInput);
        
        int slotIndex = this.getSlotIndex();
        IItemHandler handler = this.getItemHandler();
        ItemStack currentStack = handler.getStackInSlot(slotIndex);
        if (handler instanceof IItemHandlerModifiable) {
            IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;

            handlerModifiable.setStackInSlot(slotIndex, ItemStack.EMPTY);

            ItemStack remainder = handlerModifiable.insertItem(slotIndex, maxAdd, true);

            handlerModifiable.setStackInSlot(slotIndex, currentStack);

            return maxInput - remainder.getCount();
        }
        else
        {
            ItemStack remainder = handler.insertItem(slotIndex, maxAdd, true);

            int current = currentStack.getCount();
            int added = maxInput - remainder.getCount();
            return current + added;
        }
    }
    
    /**
     * Return whether this slot's stack can be taken from this slot.
     */
    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return !this.getItemHandler().extractItem(this.getSlotIndex(), 1, true).isEmpty();
    }
    
    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
    @Nonnull
    public ItemStack decrStackSize(int amount)
    {
        return this.getItemHandler().extractItem(this.getSlotIndex(), amount, false);
    }
    
    public IItemHandler getItemHandler()
    {
        return this.cpInv;
    }
    
    @Override
    public boolean isSameInventory(Slot other)
    {
        return other instanceof SlotExtend && ((SlotExtend) other).getItemHandler() == this.cpInv;
    }
    
    
}