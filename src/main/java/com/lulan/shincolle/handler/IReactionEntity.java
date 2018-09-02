package com.lulan.shincolle.handler;

/**
 * entity has react methods
 */
public interface IReactionEntity extends IPacketEntity
{
    
    /** get handler */
    ReactionHandler getReactHandler();
    
}