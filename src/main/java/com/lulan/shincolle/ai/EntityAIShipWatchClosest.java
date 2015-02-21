package com.lulan.shincolle.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;

public class EntityAIShipWatchClosest extends EntityAIWatchClosest {

    public EntityAIShipWatchClosest(EntityLiving entity, Class target, float range, float rate) {
        super(entity, target, range, rate);
        this.setMutexBits(2);
    }
}
