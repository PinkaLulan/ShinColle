package com.lulan.shincolle.capability;

import com.lulan.shincolle.handler.IInventoryShip;

import net.minecraftforge.items.ItemStackHandler;

/**
 * inventory capability<br>
 * <br>
 * 2018/6/5<br>
 *   remove IInventory system<br>
 */
public class CapaInventory<T extends IInventoryShip> extends ItemStackHandler
{
    
    //ship inventory nbt tag name
    private String invName;
    protected T host;
    
    
    public CapaInventory(int size, T host, String invName)
    {
        super(size);
        this.host = host;
        this.invName = invName;
    }
    
    public T getHost()
    {
        return this.host;
    }
    
    /** inv name for NBT data handler */
    public String getInvName()
    {
        return this.invName;
    }
    
    /** sync on general value changed */
    protected void onContentsChanged()
    {
        if (this.host != null) this.host.getItemHandler().onContentChanged(this);
    }
    
    /** sync on itemstack changed */
    @Override
    protected void onContentsChanged(int slot)
    {
        if (this.host != null) this.host.getItemHandler().onContentChanged(slot, this);
    }
    
    /** after nbt data loaded, put some init method here */
    @Override
    protected void onLoad() {}
    
    
}