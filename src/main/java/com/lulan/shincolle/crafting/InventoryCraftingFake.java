package com.lulan.shincolle.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

/**
 * fake crafting matrix for crafting simulation
 * override event handler, all private field and method
 */
public class InventoryCraftingFake extends InventoryCrafting
{
	
    /** List of the stacks in the crafting matrix. */
    protected final NonNullList<ItemStack> stacks;
    /** the width of the crafting inventory */
    protected final int width;
    protected final int height;
    /** Class containing the callbacks for the events on_GUIClosed and on_CraftMaxtrixChanged. */
    
    
    public InventoryCraftingFake(int width, int height)
    {
    	super(null, width, height);

        this.stacks = NonNullList.<ItemStack>withSize(width * height, ItemStack.EMPTY);
        this.width = width;
        this.height = height;
    }

    @Override
    public int getSizeInventory()
    {
        return this.stacks.size();
    }

    @Override
    @Nullable
    public ItemStack getStackInSlot(int index)
    {
        return index >= this.getSizeInventory() ? ItemStack.EMPTY : this.stacks.get(index);
    }
    
    @Override
    @Nullable
    public ItemStack getStackInRowAndColumn(int row, int column)
    {
        return row >= 0 && row < this.width && column >= 0 && column <= this.height ? this.getStackInSlot(row + column * this.width) : ItemStack.EMPTY;
    }

    @Override
    @Nullable
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.stacks, index);
    }

    @Override
    @Nullable
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.stacks, index, count);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        this.stacks.set(index, stack);
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.stacks.size(); ++i)
        {
            this.stacks.set(i, ItemStack.EMPTY);
        }
    }

    @Override
    public int getHeight()
    {
        return this.height;
    }

    @Override
    public int getWidth()
    {
        return this.width;
    }
    
    
}