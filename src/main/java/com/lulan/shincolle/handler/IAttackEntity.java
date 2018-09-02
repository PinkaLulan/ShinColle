package com.lulan.shincolle.handler;

import com.lulan.shincolle.entity.IShipOwner;

import com.lulan.shincolle.entity.IShipOwner;

/**
 * entity with attack AI
 */
public interface IAttackEntity
extends IStateEntityAdv, ISoundEntity, IParticleEntity, IReactionEntity, IShipOwner
{
    
    /** get handler */
    AttackHandler getAttackHandler();
    
}