package com.lulan.shincolle.entity.mounts;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityMountBaH extends BasicEntityMount
{
	
    public EntityMountBaH(World world)
    {
		super(world);
		this.setSize(1.9F, 3.1F);
		this.seatPos = new float[] {1.2F, -1.3F, 1.1F};
		this.seatPos2 = new float[] {1.2F, -1.3F, 1.1F};
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
    public double getMountedYOffset()
    {
        return 2.754D;
    }
    
    //deal 300% melee attack
  	@Override
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		if (this.host != null) return this.host.getAttackBaseDamage(type, target);
  		return 0F;
  	}
	
	//use host's cluster bomb
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
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		//client side
		if (this.world.isRemote)
		{
			//add smoke particle
			if(this.ticksExisted % 4 == 0)
			{
  				ParticleHelper.spawnAttackParticleAt(posX, posY + 3D, posZ, 0D, 0D, 0D, (byte)20);
  			}
		}
	}

	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.BattleshipMount;
	}


}