package com.lulan.shincolle.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;

public class EntityMountSeat extends Entity {

	public BasicEntityMount host;
	public float seatHeight;
	
	public EntityMountSeat(World world) {
		super(world);
		this.setSize(1F, 1F);
		this.seatHeight = 1F;
		this.noClip = true;
	}
	
	public EntityMountSeat(World world, BasicEntityMount host) {
		super(world);
		this.host = host;
		this.seatHeight = 0F;
		this.isImmuneToFire = true;
		this.noClip = true;
	}
	
	@Override
	public double getMountedYOffset() {
		return seatHeight + 0.0F;
	}
	
	@Override
	public boolean canBeCollidedWith() {
        return false;
    }
	
	@Override
	public boolean canBePushed() {
        return false;
    }
	
	@Override
	public boolean isEntityInvulnerable() {
        return true;
    }
	
	@Override
	public boolean isInvisible() {
        return false;
    }
	
	@Override
	public boolean shouldRenderInPass(int pass){
        return false;
    }
	
	@Override
	public void onUpdate() {
		//get off mount, BOTH side
		if(this.riddenByEntity != null && this.ticksExisted > 10) {
			if(this.riddenByEntity.isSneaking()) {
				this.riddenByEntity = null;
				
				if(host != null) {
					host.setStateEmotion(ID.S.Emotion, 0, false);
				}
			}
//			LogHelper.info("DEBUG : rider 2 state "+this.worldObj.isRemote+" "+this.riddenByEntity.rotationPitch);
			
			if(host == null) {
				this.riddenByEntity = null;
			}
		}
				
//		LogHelper.info("DEBUG : seat state "+this.worldObj.isRemote+" "+this.riddenByEntity);
		if(!this.worldObj.isRemote) { 
			if(host != null) {
				//若rider不在或者host的seat換人, 則消除此entity
				if(this.riddenByEntity == null || this.host.seat2 != this) {
					host.seat2 = null;
					host.setStateEmotion(ID.S.Emotion, 0, false);
					this.setDead();
					return;
				}
			}			
		}
		
		if(host != null) {
			float[] ridePos = ParticleHelper.rotateParticleByAxis(1.2F, -1.3F, host.renderYawOffset/57.2957F, 1F);	
			this.posX = host.posX + ridePos[1];
			this.posY = host.posY;
			this.posZ = host.posZ + ridePos[0];
//			this.setPosition(posX, posY, posZ);
		}
	}

	@Override
	protected void entityInit() {}
	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {}
	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}

}
