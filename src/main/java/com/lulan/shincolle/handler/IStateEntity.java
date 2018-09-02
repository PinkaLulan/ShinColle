package com.lulan.shincolle.handler;

import java.util.Random;

/**
 * states handler for entity
 */
public interface IStateEntity extends IPacketEntity
{
    
    /** get handler */
    StateHandler getStateHandler();
    
    /** get entity's random */
    Random getEntityRandom();
    
}