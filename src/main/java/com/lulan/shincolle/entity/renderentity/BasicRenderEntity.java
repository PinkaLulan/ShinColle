package com.lulan.shincolle.entity.renderentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

abstract public class BasicRenderEntity extends Entity {
	
	public BasicRenderEntity(World world) {
		super(world);
		this.ignoreFrustumCheck = true;	//即使不在視線內一樣render
		this.noClip = true;
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
	public void onUpdate() {}
	
	/** override this method to make entity NOCLIP */
	@Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int par1) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }
	

}
