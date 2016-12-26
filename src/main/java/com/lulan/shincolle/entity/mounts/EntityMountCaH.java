package com.lulan.shincolle.entity.mounts;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityMountLarge;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;

import net.minecraft.world.World;

public class EntityMountCaH extends BasicEntityMountLarge
{
	
    public EntityMountCaH(World world)
    {
		super(world);
		this.setSize(1.9F, 1.8F);
		this.seatPos = new float[] {0.0F, 0F, 0.5F};
		this.seatPos2 = new float[] {0.0F, 0F, 0.5F};
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
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(10, new EntityAIShipCarrierAttack(this));
	}

	@Override
	public int getTextureID()
	{
		return ID.ShipMisc.CarrierMount;
	}
	

}


