package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.reference.ID;

import net.minecraft.world.World;

public class EntityRensouhouS extends EntityRensouhou
{

    public EntityRensouhouS(World world)
    {
		super(world);
		this.setSize(0.5F, 1.4F);
	}

    @Override
	public int getDamageType()
    {
		return ID.ShipDmgType.BATTLESHIP;
	}
    
	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.RensouhouS;
	}
    

}

