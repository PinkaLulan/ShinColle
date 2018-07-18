package com.lulan.shincolle.utility;

import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.handler.IStateEntity;
import com.lulan.shincolle.handler.StateHandler;

/**
 * helper for handlers
 */
public class HandlerHelper
{
    
    
    public HandlerHelper() {}
    
    /** get state handler by host trace */
    public static StateHandler getStateHandler(Object host)
    {
        if (host instanceof IStateEntity)
        {
            return ((IStateEntity) host).getStateHandler();
        }
        else if (host instanceof IShipOwner)
        {
            return getStateHandler(((IShipOwner) host).getHostEntity());
        }
        
        return null;
    }
    
    
}