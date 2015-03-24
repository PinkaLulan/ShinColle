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
	/** 0:haste 1:speed 2:jump 3:damage*/
	private int[] ringEffect;
	private int marriageNum;
	    
	@Override
	public void init(Entity entity, World world) {
		this.world = world;
		this.player = (EntityPlayer) entity;
		this.hasRing = false;
		this.isRingActive = false;
		this.isRingFlying = false;
		this.ringEffect = new int[] {0, 0, 0, 0};
		this.marriageNum = 0;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound nbt) {
		NBTTagCompound nbtExt = new NBTTagCompound();
		
		nbtExt.setBoolean("hasRing", hasRing);
		nbtExt.setBoolean("RingOn", isRingActive);
		nbtExt.setBoolean("RingFly", isRingFlying);
		nbtExt.setIntArray("RingEffect", ringEffect);
		nbtExt.setInteger("MarriageNum", marriageNum);
		
		nbt.setTag(PLAYER_EXTPROP_NAME, nbtExt);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt) {
		NBTTagCompound nbtExt = (NBTTagCompound) nbt.getTag(PLAYER_EXTPROP_NAME);
		
		hasRing = nbtExt.getBoolean("hasRing");
		isRingActive = nbtExt.getBoolean("RingOn");
		isRingFlying = nbtExt.getBoolean("RingFly");
		ringEffect = nbtExt.getIntArray("RingEffect");
		marriageNum = nbtExt.getInteger("MarriageNum");
	}
	
	//getter
	public boolean isRingActive() {
		return isRingActive;
	}
	public int isRingActiveI() {
		return isRingActive ? 1 : 0;
	}
	public boolean isRingFlying() {
		return isRingFlying;
	}
	public boolean hasRing() {
		return hasRing;
	}
	public int getRingEffect(int id) {
		return ringEffect[id];
	}
	public int getMarriageNum() {
		return marriageNum;
	}
	public int getDigSpeedBoost() {
		return isRingActive ? marriageNum : 0;
	}
	
	//setter
	public void setRingActive(boolean par1) {
		isRingActive = par1;
	}
	public void setRingActiveI(int par1) {
		if(par1 == 0) {
			isRingActive = false;
		}
		else {
			isRingActive = true;
		}
	}
	public void setRingFlying(boolean par1) {
		isRingFlying = par1;
	}
	public void setHasRing(boolean par1) {
		hasRing = par1;
	}
	public void setRingEffect(int id, int par1) {
		ringEffect[id] = par1;
	}
	public void setMarriageNum(int par1) {
		marriageNum = par1;
	}


}
