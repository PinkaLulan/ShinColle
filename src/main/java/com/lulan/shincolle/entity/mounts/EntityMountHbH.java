package com.lulan.shincolle.entity.mounts;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMountLarge;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityMountHbH extends BasicEntityMountLarge
{
	
    public EntityMountHbH(World world)
    {
		super(world);
		this.setSize(1.9F, 1.3F);
		this.seatPos = new float[] {-1.4F, 0.4F, 1.5F};
		this.seatPos2 = new float[] {-1.4F, 0.4F, 1.5F};
	}
    
    @Override
    public void initAttrs(BasicEntityShip host)
    {
        this.host = host;
           
        //設定位置
        this.posX = host.posX;
        this.posY = host.posY;
        this.posZ = host.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
 
	    //設定基本屬性
        setupAttrs();
        
		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
	}
    
    @Override
	public float getEyeHeight()
    {
		return 1.7F;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		//client side
		if (this.world.isRemote)
		{
			if (this.ticksExisted % 16 == 0)
			{
				//railgun particle
				ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 0D, 0D, (byte)3);
				if (rand.nextInt(3) == 0)
				ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 0D, 0D, (byte)3);
				if (rand.nextInt(3) == 0)
				ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 0D, 0D, (byte)3);
				if (rand.nextInt(3) == 0)
				ParticleHelper.spawnAttackParticleAtEntity(this, 0D, 0D, 0D, (byte)3);
			}
		}
	}
	
	//use host's railgun
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		if (this.host != null)
		{
			return this.host.attackEntityWithHeavyAmmo(target);
		}
		else
		{
			return super.attackEntityWithHeavyAmmo(target);
		}
	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(10, new EntityAIShipCarrierAttack(this));
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}

	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.HarbourMount;
	}


}