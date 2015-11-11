package com.lulan.shincolle.entity.renderentity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

abstract public class BasicRenderEntity extends Entity {
	
	public BasicRenderEntity(World world) {
		super(world);
		this.ignoreFrustumCheck = true;	//即使不在視線內一樣render
	}
	
	@Override
	public boolean isEntityInvulnerable() {
        return true;
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
    public void addVelocity(double d1, double d2, double d3) {
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0F;
    }

	@Override
	protected void entityInit() {}

	@Override
	abstract protected void readEntityFromNBT(NBTTagCompound nbt);

	@Override
	abstract protected void writeEntityToNBT(NBTTagCompound nbt);
	
	@Override
	public void onUpdate() {
//		if(this.ticksExisted > 200) {
//			this.setDead();
//		}
		
//		if(!this.worldObj.isRemote && this.ticksExisted % 21 == 0) {
//			LogHelper.info("DEBUG : set inv "+(this.ticksExisted % 2 == 0));
//			this.setInvisible(this.ticksExisted % 2 == 0);
//		}
	}
	

}
