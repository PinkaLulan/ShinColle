package com.lulan.shincolle.entity.mounts;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityMountLarge;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityMountCaWD extends BasicEntityMountLarge
{
	
    public EntityMountCaWD(World world)
    {
		super(world);
		this.setSize(1.9F, 2.1F);
		this.seatPos = new float[] {0.25F, 1.1F, 0F};
		this.seatPos2 = new float[] {0.14F, -0.39F, 0F};
        this.shipNavigator = new ShipPathNavigate(this);
		this.shipMoveHelper = new ShipMoveHelper(this, 45F);
	}
    
    @Override
    public void initAttrs(BasicEntityShip host)
    {
        this.host = host;
        
        //設定位置
        this.posX = host.posX;
        this.posY = host.posY;
        this.posZ = host.posZ;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.setPosition(this.posX, this.posY, this.posZ);
 
	    //設定基本屬性
        this.setupAttrs();
        
		if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
				
		//設定AI
		this.setAIList();
	}

	//輕攻擊, 特效為雙光束砲
	@Override
	public boolean attackEntityWithAmmo(Entity target)
	{
		if (this.host != null)
		{
			return host.attackEntityWithAmmo(target);
		}
		
		return false;
	}
	
	//停用重攻擊
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		return false;
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
		return ID.ShipMisc.CarrierWDMount;
	}
	

}