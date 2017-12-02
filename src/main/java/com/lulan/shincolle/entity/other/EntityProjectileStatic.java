package com.lulan.shincolle.entity.other;

import java.util.List;

import com.lulan.shincolle.client.render.IShipCustomTexture;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipAttrs;
import com.lulan.shincolle.entity.IShipOwner;
import com.lulan.shincolle.entity.IShipProjectile;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * static effect entity
 * apply effect over time at a fixed position
 * 
 * type:
 *   0: black hole, pull nearby entities to center, setDead after X ticks
 *      
 */
public class EntityProjectileStatic extends Entity implements IShipOwner, IShipAttrs, IShipCustomTexture, IShipProjectile
{
	
	private IShipAttackBase host;
    private Entity host2;
    private int playerUID;
	public int type, lifeLength;
	public double pullForce, range;
	private double[] data;
	

	public EntityProjectileStatic(World world)
	{
		super(world);
		this.setSize(0.5F, 0.5F);
		this.ignoreFrustumCheck = true;  //always render
		this.noClip = true;				 //can't block
		this.stepHeight = 0F;
		this.type = 0;
		this.lifeLength = 0;
		this.pullForce = 0D;
		this.range = 0D;
	}
	
	/**
	 * init attrs
	 * 
	 * data:
	 *   black hole: data: 0:posX, 1:posY, 2:posZ, 3:life, 4:pullForce, 5:range
	 */
	public void initAttrs(IShipAttackBase host, int type, double[] data)
	{
		//host
		this.host = host;
		this.host2 = (Entity) host;
		this.playerUID = host.getPlayerUID();
		this.type = type;
		this.data = data;
		
		switch (type)
		{
		default:	//black hole
			this.setPosition(data[0], data[1], data[2]);
			this.lifeLength = (int) data[3];
			this.pullForce = data[4];
			this.range = data[5];
		break;
		}
		
		this.prevPosX = this.posX;
    	this.prevPosY = this.posY;
    	this.prevPosZ = this.posZ;
	}

	@Override
	public int getPlayerUID()
	{
		return this.playerUID;
	}

	@Override
	public void setPlayerUID(int uid) {}

	@Override
	public Entity getHostEntity()
	{
		return host2;
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {}
	
	@Override
	public boolean isEntityInvulnerable(DamageSource attacker)
	{
        return true;
    }
	
	@Override
    public boolean canBeCollidedWith()
	{
        return false;
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
	public void onUpdate()
    {
    	/**************** BOTH SIDE ******************/
        super.onUpdate();
        
        /*************** SERVER SIDE *****************/
        if (!this.world.isRemote)
        {
        	//check life
        	if (this.ticksExisted > this.lifeLength || this.host == null)
        	{
        		this.setDead();
        		return;
        	}
        	
        	//sync projectile type at start
        	TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        	
    		if (this.ticksExisted == 1)
    		{
    			float[] data = new float[] {1F, (float)this.type, (float)this.data[3], (float)this.data[4], (float)this.data[5]};
    	  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this, S2CEntitySync.PID.SyncEntity_CustomData, data), point);
    		}
    		
    		switch (this.type)
    		{
    		default:  //0: black hole
    		{
    			//every 4 ticks
    			if ((this.ticksExisted & 3) == 0)
    			{
    				//判定bounding box內是否有可以觸發爆炸的entity
                    List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class,
                    								this.getEntityBoundingBox().expand(this.range, this.range, this.range));
                    
                    //pull entity whose distance > 1D
                    for (Entity ent : hitList)
                    {
                    	if (ent.canBeCollidedWith() && EntityHelper.isNotHost(this, ent) &&
                    		ent.canBePushed() && !TeamHelper.checkSameOwner(this.host2, ent))
                    	{
                    		Dist4d dist = CalcHelper.getDistanceFromA2B(this, ent);
                    		
                    		if (dist.d > 1D)
                    		{
                    			ent.addVelocity(dist.x * -this.pullForce, dist.y * -this.pullForce, dist.z * -this.pullForce);
                    			CommonProxy.channelE.sendToAllAround(new S2CEntitySync(ent, 0, S2CEntitySync.PID.SyncEntity_Motion), point);
        					}
                    	}
                    }
    			}//end every 4 ticks
    		}
			break;
    		}
        }
        /*************** CLIENT SIDE *****************/
        else
        {
        	if (this.ticksExisted == 1)
        	{
        		//spawn beam head particle
            	ParticleHelper.spawnAttackParticleAtEntity(this, 5D, this.lifeLength, this.range * 2D, (byte)24);
        	}
        }
    }
    
    @Override
	public boolean attackEntityFrom(DamageSource attacker, float atk)
    {
    	return false;
    }
    
	//撞擊判定時呼叫此方法
    protected void onImpact(Entity target)
    {
    	//NYI
    }

	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.Invisible;
	}

	@Override
	public Attrs getAttrs()
	{
		return this.host.getAttrs();
	}

	@Override
	public void setAttrs(Attrs data) {}
	
	@Override
	public int getProjectileType()
	{
		return this.type;
	}

	@Override
	public void setProjectileType(int type)
	{
		this.type = type;
	}
	
	
}