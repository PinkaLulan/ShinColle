package com.lulan.shincolle.entity.other;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

public class EntityRensouhouS extends EntityRensouhou {

    public EntityRensouhouS(World world) {
		super(world);
		this.setSize(0.5F, 1.4F);
	}
    
    public EntityRensouhouS(World world, BasicEntityShip host, Entity target) {
    	super(world, host, target);
	}
    
    @Override
	public int getDamageType() {
		return ID.ShipDmgType.BATTLESHIP;
	}
    

}

