package com.lulan.shincolle.entity.renderentity;

import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRenderVortex extends BasicRenderEntity {
	
	public EntityRenderVortex(World world) {
		super(world);
	}
	
	public EntityRenderVortex(World world, int x, int y, int z) {
		super(world, x, y, z);
		this.posX = x + 0.5D;
		this.posY = y - 0.95D;
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
