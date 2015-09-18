package com.lulan.shincolle.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BasicEntityItem extends Entity {
	
	public int age;
	public ItemStack item;
	
	public BasicEntityItem(World world) {
		super(world);
		this.setSize(1F, 1F);
	}
	
	public BasicEntityItem(World world, double x, double y, double z, ItemStack item) {
		super(world);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.setPosition(x, y, z);
		this.yOffset = this.height / 2.0F;
		this.motionX = 0D;
		this.motionY = 0D;
		this.motionZ = 0D;	
		this.isImmuneToFire = true;
		this.onGround = true;
		this.noClip = false;
		this.item = item;
	}
	
	//can not damage this item
	@Override
	public boolean attackEntityFrom(DamageSource attacker, float dmg) {
		return false;
	}
	
	//immune to fire and lava
	@Override
	protected void dealFireDamage(int fire) {}
	@Override
	public void setFire(int time) {}
	@Override
	protected void setOnFireFromLava() {}
	@Override
	public boolean handleLavaMovement() {
		return false;
	}
	@Override
	public boolean handleWaterMovement() {
		return false;
	}
	
	@Override
	public void moveEntity(double x, double y, double z) {}
	
	@Override
	protected void updateFallState(double p_70064_1_, boolean p_70064_3_) {}
	
	@Override
	protected void fall(float p_70069_1_) {}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }
	
	@Override
	public boolean shouldRenderInPass(int pass){
        return true;
    }
	
	@Override
	public boolean isInvisible() {
		return false;
	}
	
	@Override
	public void setInvisible(boolean p_82142_1_) {}
	
	//cancel motionY
	@Override
    public void onUpdate() {
		this.setPosition(posX, posY, posZ);
//        onEntityUpdate();

		//play ender portal sound
		if (this.worldObj.isRemote && this.worldObj.rand.nextInt(50) == 0) {
			this.worldObj.playSound((double)posX + 0.5D, (double)posY + 0.5D, (double)posZ + 0.5D, "portal.portal", 0.5F, this.worldObj.rand.nextFloat() * 0.4F + 0.8F, false);
        }
		
        if(this.getEntityItem() == null) {
            this.setDead();
        }
        else {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            
            this.motionX = 0D;
            this.motionY = 0D;
            this.motionZ = 0D;

            ++this.age;

            ItemStack item = this.item;
 
            //2015/5/27: change to not despawn
//            if(!this.worldObj.isRemote && this.age >= 6000) {
//            	this.setDead();
//            }
    
            if(item != null && item.stackSize <= 0) {
                this.setDead();
            }
        }
    }
	
	public ItemStack getEntityItem() {
        ItemStack itemstack = this.item;
        return itemstack == null ? new ItemStack(Blocks.stone) : itemstack;
    }
	
	public void setEntityItemStack(ItemStack item) {
		this.item = item;
    }
	
	/**
     * Called by a player entity when they collide with an entity
     */
	@Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if(!this.worldObj.isRemote) {
            ItemStack itemstack = this.getEntityItem();
            int i = itemstack.stackSize;
//            LogHelper.info("DEBUg : drop entity: i "+i);
            this.worldObj.playSoundAtEntity(player, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            
            if(player.inventory.addItemStackToInventory(itemstack)) {
//            	i = itemstack.stackSize;
//            	LogHelper.info("DEBUg : get drop entity true: i "+i);
            }
//            else {
//            	i = itemstack.stackSize;
//            	LogHelper.info("DEBUg : get drop entity false: i "+i);
//            }
            
            if(itemstack.stackSize <= 0) {
                this.setDead();
            }
        }
    }

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
        NBTTagCompound nbttagcompound1 = nbt.getCompoundTag("Item");
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(nbttagcompound1));

        ItemStack item = this.item;

        if(item == null || item.stackSize <= 0) {
            this.setDead();
        }
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
        if(this.getEntityItem() != null) {
            nbt.setTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
	}


}
