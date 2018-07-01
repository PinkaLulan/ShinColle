package com.lulan.shincolle.handler;

public interface IReactionShip extends IReactionEntity
{
    
    /** get handler */
    @Override
    ShipReactionHandler getReactHandler();
    
}