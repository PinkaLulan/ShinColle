package com.lulan.shincolle.entity;

import com.lulan.shincolle.capability.CapaInventory;

public interface IShipInventory
{
    
    /** update on slot changed */
    public void onContentChanged(int slot, CapaInventory itemhandler);
    
    /** update on general value changed */
    public void onContentChanged(CapaInventory itemhandler);
    
}