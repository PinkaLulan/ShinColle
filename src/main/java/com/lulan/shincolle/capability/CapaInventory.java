package com.lulan.shincolle.capability;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.tileentity.BasicTileInventory;

import net.minecraftforge.items.ItemStackHandler;

/**
 * inventory capability<br>
 * <br>
 * 2018/6/5<br>
 *   remove IInventory system<br>
 */
public class CapaInventory<T> extends ItemStackHandler
{
	
	//ship inventory nbt tag name
	public static final String InvName = "CpInv";
	
	//host type:  -1:null host  0:ship  1:tile entity
	protected int hostType = -1;
	protected T host;
	
	
    public CapaInventory(int size, T host)
    {
        super(size);
        this.host = host;
        
        //check host
        if (this.host instanceof BasicEntityShip) { hostType = 0; }
        else if (this.host instanceof BasicTileInventory) { hostType = 1; }
        else { hostType = -1; }  //null host
    }
    
    public T getHost()
    {
    	return this.host;
    }
    
    /** sync method */
    protected void onContentsChanged()
	{
        switch (hostType)
        {
        case 0:  //ship
        	//TODO send sync packet
    	break;
        case 1:  //tile
        	((BasicTileInventory) this.host).markDirty();
        break;
        }
    }
    
	/** sync method for slot */
	@Override
    protected void onContentsChanged(int slot)
	{
    }
	
	/** after nbt data loaded, put some init method here */
	@Override
	protected void onLoad() {}
	
	
}