package com.lulan.shincolle.handler;

/**
 * entity with states, attrs and buffs
 */
public interface IStateEntityAdv extends IStateEntity
{
    
    /** get state and attr handler */
    @Override
    AttrStateHandler getStateHandler();
    
    /** get buff handler */
    BuffHandler getBuffHandler();
    
}