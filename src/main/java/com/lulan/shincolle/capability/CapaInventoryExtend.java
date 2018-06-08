package com.lulan.shincolle.capability;

import java.util.ArrayList;

import com.lulan.shincolle.entity.IShipInventory;
import com.lulan.shincolle.entity.IStateHandler;
import com.lulan.shincolle.utility.InventoryHelper;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;


/**
 * extensible inventory, GUI show slots with scroll bar or page button
 * when slot size changed, copy data from old list to new list
 * if size decreased, drop items on floor
 *  
 * 2018/6/7
 *   separate equip and item inventory
 *   item slots are extensible by equip or ship ability
 *   equip slots are paged (default 3 pages)
 * 
 * 2018/6/5
 *   remove IInventory system
 */
public class CapaInventoryExtend<T extends IStateHandler & IShipInventory> extends CapaInventory<T>
{
    
    /** current inventory line/page/...etc number for GUI display */
    protected int currentExtendNumber = 0;
    /** slots per line/page/...etc */
    protected int slotsPerExtend;
    
    
    public CapaInventoryExtend(int size, int slotsPerExtend, T host, String invName)
    {
        super(size, host, invName);
        this.slotsPerExtend = slotsPerExtend;
    }
    
    /** get current extend number for GUI display, ex: lines, pages, ... */
    public int getCurrentExtendNumber()
    {
        return this.currentExtendNumber;
    }
    
    /** get current extend number for GUI display, ex: lines, pages, ... */
    public void setCurrentExtendNumber(int value)
    {
        this.currentExtendNumber = value;
        this.onContentsChanged();
    }
    
    /** get base slot index for GUI display */
    public int getBaseSlotIndex()
    {
        return this.currentExtendNumber * this.slotsPerExtend;
    }
    
    /** change inventory size, drop items on floor if size decreased */
    public void resize(int size)
    {
        //save old items
        ArrayList<ItemStack> oldList = new ArrayList<ItemStack>();
        
        for (ItemStack s : this.stacks)
        {
            if (!s.isEmpty())
            {
                oldList.add(s);
            }
        }
        
        //move old items to new list
        ArrayList<ItemStack> dropList = new ArrayList<ItemStack>();
        this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
        
        for (int i = 0; i < oldList.size(); i++)
        {
            if (i < size) this.stacks.set(i, oldList.get(i));
            else dropList.add(oldList.get(i));
        }
        
        //drop items
        if (this.host != null)
        {
            double x, y, z;
            World w;
            
            if (this.host instanceof Entity)
            {
                x = ((Entity) this.host).posX;
                y = ((Entity) this.host).posY;
                z = ((Entity) this.host).posZ;
                w = ((Entity) this.host).getEntityWorld();
            }
            else if (this.host instanceof TileEntity)
            {
                x = ((TileEntity) this.host).getPos().getX() + 0.5D;
                y = ((TileEntity) this.host).getPos().getY() + 0.5D;
                z = ((TileEntity) this.host).getPos().getZ() + 0.5D;
                w = ((TileEntity) this.host).getWorld();
            }
            else return;
            
            if (w.isRemote) return;  //no client side
            
            for (ItemStack s : dropList)
            {
                InventoryHelper.dropItemOnGround(w, s, x, y, z);
            }
        }
    }
    
    
}