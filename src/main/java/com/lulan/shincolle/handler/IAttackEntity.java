package com.lulan.shincolle.handler;

/**
 * entity with attack AI
 */
public interface IAttackEntity
extends IStateEntity, ISoundEntity, IPacketEntity, IParticleEntity
{
    
    /** get handler */
    AttackHandler getAttackHandler();
    
}