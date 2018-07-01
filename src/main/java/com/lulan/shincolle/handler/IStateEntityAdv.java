package com.lulan.shincolle.handler;

/**
 * entity with attrs
 */
public interface IStateEntityAdv extends IStateEntity
{
    
    /** get handler */
    @Override
    AttrStateHandler getStateHandler();
    
}