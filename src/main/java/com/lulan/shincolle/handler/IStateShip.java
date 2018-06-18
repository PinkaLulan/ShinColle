package com.lulan.shincolle.handler;

/**
 * states handler for ship entity
 */
public interface IStateShip extends IStateEntity
{
    
    /** get handler */
    @Override
    ShipStateHandler getStateHandler();
    
}