package com.lulan.shincolle.entity.renderentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityRenderVortex extends BasicRenderEntity {
	
	private boolean isActive;
	
	public EntityRenderVortex(World world) {
		super(world);
		this.setSize(0.8F, 3F);
	}
	
	public EntityRenderVortex(World world, int x, int y, int z) {
		this(world);
		this.posX = x + 0.5D;
		this.posY = y - 2.0D;
		this.posZ = z + 0.5D;
		this.setPosition(posX, posY, posZ);
		this.isActive = false;
		
	}
	
	public void setIsActive(boolean par1) {
		isActive = par1;
	}
	
	public boolean getIsActive() {
		return isActive;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {	
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {	
	}

}
