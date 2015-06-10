package com.lulan.shincolle.entity.mounts;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityMountSeat extends Entity {

	public BasicEntityMount host;
	public float seatHeight = 1F;
	
	public EntityMountSeat(World world) {
		super(world);
		this.setSize(1F, 1F);
		this.noClip = true;
	}
	
	public EntityMountSeat(World world, BasicEntityMount host) {
		super(world);
		this.host = host;
		this.seatHeight = host.getRidePos()[2];
		this.isImmuneToFire = true;
		this.noClip = true;
	}
	
	@Override
	public double getMountedYOffset() {
		if(host != null) return host.getRidePos()[2];
		return seatHeight;
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
		//server side
		if(!this.worldObj.isRemote) {
			//sync every 60 ticks
			if(this.ticksExisted % 40 == 0) {
				if(host != null) {
					//若rider不在或者host的seat換人, 則消除此entity
					if(host.getHealth() <= 1F || this.riddenByEntity == null || this.host.seat2 != this) {
		  	  			this.setRiderNull();
		  	  			return;
					}
					
					//sync packet
					TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
					CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, 6), point);
				}
				else {
					this.setRiderNull();
					return;
				}
			}
		}
		
		//host check, BOTH side
		if(host != null) {
			float[] ridePos = ParticleHelper.rotateParticleByAxis(host.getRidePos()[0], host.getRidePos()[1], host.renderYawOffset/57.2957F, 1F);	
			this.posX = host.posX + ridePos[1];
			this.posY = host.posY;
			this.posZ = host.posZ + ridePos[0];
			this.setPosition(posX, posY, posZ);
		}
		else {
			this.setRiderNull();
		}
		
//		//get off mount, BOTH side
//		if(this.riddenByEntity != null && this.ticksExisted > 10) {
//			if(this.riddenByEntity.isSneaking()) {
//				this.setRiderNull();
//			}
//		}
	}

	@Override
	protected void entityInit() {}
	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {}
	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {}
	
	//set rider null and clear this seat entity, call by package
	public void setRiderNull() {
		//clear host side
		if(host != null) {
			host.seat2 = null;				//clear seat2
			host.riddenByEntity2 = null;	//clear rider2
			host.setStateEmotion(ID.S.Emotion, 0, false);
			host = null;					//clear host
		}
		
		//clear seat2(this) side
		if(this.riddenByEntity != null) {	
			this.riddenByEntity.ridingEntity = null;
			this.riddenByEntity = null;		//clear seat2 rider
		}
		
		//若是server則發送sync packet
		if(!this.worldObj.isRemote) {
			TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, 6), point);
		}

		this.setDead();	//此setDead只有發生在server side, client side必須由sync packet處理
	}
	
	
}
