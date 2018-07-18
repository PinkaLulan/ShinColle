package com.lulan.shincolle.handler;

/**
 * state handler for minions
 */
public interface IStateMinion extends IStateShip
{
    
    /** get handler */
    @Override
    MinionStateHandler getStateHandler();
    
}