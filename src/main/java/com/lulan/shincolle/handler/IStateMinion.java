package com.lulan.shincolle.handler;

/**
 * state handler for minions
 */
public interface IStateMinion extends IStateEntity
{
    
    /** get handler */
    @Override
    MinionStateHandler getStateHandler();
    
}