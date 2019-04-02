package com.lulan.shincolle.entity.other;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.utility.ParticleHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShipFishingHook extends Entity
{
	
    public EntityLivingBase host;

    
    public EntityShipFishingHook(World worldIn)
    {
        super(worldIn);
        this.setSize(0.25F, 0.25F);
        this.ignoreFrustumCheck = true;
    }

    public EntityShipFishingHook(World worldIn, IShipAttackBase host)
    {
        this(worldIn);
        
        if (host instanceof BasicEntityShip)
        {
        	this.host = (EntityLivingBase) host;
        	((BasicEntityShip) host).fishHook = this;
        }
        else
        {
        	//for ship only now, TODO add hostile ship later?
        	throw new IllegalArgumentException("EntityShipFishingHook requires BasicEntityShip!");
        }
    }
    
	@Override
	protected void entityInit() {}
	
    /**
     * Checks if the entity is in range to render.
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;

        if (Double.isNaN(d0))
        {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }
    
    @Override
    public boolean isEntityInvulnerable(DamageSource source)
    {
        return true;
    }
    
    @Override
    public boolean canBeAttackedWithItem()
    {
        return false;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        
        //time out
        if (this.ticksExisted > ConfigHandler.tickFishing[0] + ConfigHandler.tickFishing[1]) this.setDead();
        
        //server side
        if (!this.world.isRemote)
        {
        	//null check
        	if (this.host == null)
        	{
        		this.setDead();
        		return;
        	}
        	
        	//host dead check
            ItemStack rod = this.host.getHeldItemMainhand();
            if (rod.isEmpty() || rod.getItem() != Items.FISHING_ROD) rod = this.host.getHeldItemOffhand();
            	
        	if (host.isDead || !host.isEntityAlive() || rod.isEmpty() ||
        		rod.getItem() != Items.FISHING_ROD ||
        		this.getDistanceSq(host) > 1024D)
            {
                this.setDead();
                return;
            }
        	
            if (this.ticksExisted == 4)
    		{
            	//sync host
            	TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
				CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncEntity_Host), point);
            	
    			//play splash sound
            	this.playSound(SoundEvents.ENTITY_BOBBER_SPLASH, 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
    		}
        }//end server side
        //client side
        else
        {
        	//move verticaly
        	this.motionX *= 0.9D;
            this.motionY *= 0.9D;
            this.motionZ *= 0.9D;
            this.setPosition(this.posX, this.posY, this.posZ);
        	
        	if (this.ticksExisted == 4)
        	{
        		ParticleHelper.spawnAttackParticleAt(posX, posY - 0.1D, posZ, 1.5D, 0.25D, 1.5D, (byte)48);
        	}
        	
    		//every 64 ticks
        	if ((this.ticksExisted & 63) == 0)
        	{
        		if (this.rand.nextFloat() < 0.35F)
        		{
        			ParticleHelper.spawnAttackParticleAt(posX, posY - 0.1D, posZ, 1.5D, 0.25D, 1.5D, (byte)48);
        		}
        	}//end every 64 ticks
        }//end client side
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        super.setDead();

        if (this.host instanceof BasicEntityShip)
        {
            ((BasicEntityShip) this.host).fishHook = null;
        }
    }
    
    
}