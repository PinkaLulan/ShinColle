package com.lulan.shincolle.crafting;

import javax.annotation.Nullable;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;

/**
 * fake crafting matrix for crafting simulation
 * override event handler, all private field and method
 */
public class InventoryCraftingFake extends InventoryCrafting
{
	
    /** List of the stacks in the crafting matrix. */
    protected final ItemStack[] stacks;
    /** the width of the crafting inventory */
    protected final int width;
    protected final int height;
    /** Class containing the callbacks for the events on_GUIClosed and on_CraftMaxtrixChanged. */
    
    
    public InventoryCraftingFake(int width, int height)
    {
    	super(null, width, height);
    	
        int i = width * height;
        this.stacks = new ItemStack[i];
        this.width = width;
        this.height = height;
    }

    @Override
    public int getSizeInventory()
    {
        return this.stacks.length;
    }

    @Override
    @Nullable
    public ItemStack getStackInSlot(int index)
    {
        return index >= this.getSizeInventory() ? null : this.stacks[index];
    }
    
    @Override
    @Nullable
    public ItemStack getStackInRowAndColumn(int row, int column)
    {
        return row >= 0 && row < this.width && column >= 0 && column <= this.height ? this.getStackInSlot(row + column * this.width) : null;
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
        this.stacks[index] = stack;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.stacks.length; ++i)
        {
            this.stacks[i] = null;
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