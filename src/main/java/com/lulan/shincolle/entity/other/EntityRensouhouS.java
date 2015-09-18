package com.lulan.shincolle.entity.other;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;

public class EntityRensouhouS extends EntityRensouhou {

    public EntityRensouhouS(World world) {
		super(world);
		this.setSize(0.5F, 1.4F);
	}
    
    public EntityRensouhouS(World world, BasicEntityShip host, EntityLivingBase target) {
    	super(world, host, target);
	}

}

