package com.lulan.shincolle.entity.renderentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRenderLargeShipyard extends BasicRenderEntity {
	
	public EntityRenderLargeShipyard(World world) {
		super(world);
	}
	
	public EntityRenderLargeShipyard(World world, int x, int y, int z) {
		super(world, x, y, z);
		this.posX = x + 0.5D;
		this.posY = y - 0.9D;
		this.posZ = z + 0.5D;
		this.setPosition(posX, posY, posZ);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {	
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {	
	}

}
