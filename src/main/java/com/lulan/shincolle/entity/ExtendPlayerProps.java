package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendPlayerProps implements IExtendedEntityProperties {

	public static final String PLAYER_EXTPROP_NAME = "TeitokuExtProps";
	private EntityPlayer player;
	private World world;
	private boolean hasRing;
	private boolean isRingActive;
	private boolean isRingFlying;
	    
	@Override
	public void init(Entity entity, World world) {
		this.world = world;
		this.player = (EntityPlayer) entity;
		this.hasRing = false;
		this.isRingActive = false;
		this.isRingFlying = false;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		nbt.setBoolean("hasRing", hasRing);
		nbt.setBoolean("RingOn", isRingActive);
		nbt.setBoolean("RingFly", isRingFlying);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		hasRing = nbt.getBoolean("hasRing");
		isRingActive = nbt.getBoolean("RingOn");
		isRingFlying = nbt.getBoolean("RingFly");
	}
	
	//getter
	public boolean isRingActive() {
		return isRingActive;
	}
	public boolean isRingFlying() {
		return isRingFlying;
	}
	public boolean hasRing() {
		return hasRing;
	}
	
	//setter
	public void setRingActive(boolean par1) {
		isRingActive = par1;
	}
	public void setRingFlying(boolean par1) {
		isRingFlying = par1;
	}
	public void setHasRing(boolean par1) {
		hasRing = par1;
	}

}
