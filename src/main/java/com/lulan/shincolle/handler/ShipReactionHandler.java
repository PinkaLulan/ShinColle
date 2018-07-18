package com.lulan.shincolle.handler;

import com.lulan.shincolle.entity.BasicEntityShip;

public class ShipReactionHandler extends ReactionHandler
{
    
    protected BasicEntityShip host;
    protected ShipStateHandler state;
    
    
    public ShipReactionHandler(BasicEntityShip host)
    {
        super(host);
        
        this.host = host;
        this.state = this.host.getStateHandler();
    }
    
    @Override
    public void reactEatItem()
    {
        if (this.state.getEmoteDelay() <= 0)
        {
            this.state.setEmoteDelay(40);
            super.reactEatItem();
        }
    }
    
    @Override
    public void reactItemNotFound()
    {
        if (this.state.getEmoteDelay() <= 0)
        {
            this.state.setEmoteDelay(20);
            super.reactItemNotFound();
        }
    }
    
    
}