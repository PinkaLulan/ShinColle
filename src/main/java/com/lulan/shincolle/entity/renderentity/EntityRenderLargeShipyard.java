package com.lulan.shincolle.entity.renderentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRenderLargeShipyard extends BasicRenderEntity {
	
	public EntityRenderLargeShipyard(World world) {
		super(world);
		this.setSize(0.1F, 0.9F);
	}
	
	public EntityRenderLargeShipyard(World world, int x, int y, int z) {
		this(world);
		this.posX = x + 0.1D;	//位置錯開中間, 以免擠到vortex
		this.posY = y - 1D;
		this.posZ = z + 0.1D;
		this.setPosition(posX, posY, posZ);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {	
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {	
	}

}
