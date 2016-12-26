package com.lulan.shincolle.entity.mounts;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMountLarge;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.world.World;

public class EntityMountAfH extends BasicEntityMountLarge
{
	
    public EntityMountAfH(World world)
    {
		super(world);
		this.setSize(1.9F, 1.3F);
		this.seatPos = new float[] {-1F, -1F, 1.5F};
		this.seatPos2 = new float[] {-1F, -1F, 1.5F};
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
        this.setupAttrs();
        
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
			if (this.ticksExisted % 8 == 0)
			{
				//嘴巴冒紅煙特效
				float[] partPos1 = CalcHelper.rotateXZByAxis(0F, -1.0F, this.renderYawOffset * Values.N.DIV_PI_180, 1F);
				float[] partPos2 = CalcHelper.rotateXZByAxis(0F, -1.8F, this.renderYawOffset * Values.N.DIV_PI_180, 1F);
				ParticleHelper.spawnAttackParticleAt(this.posX + partPos1[1], this.posY + 0.9F, this.posZ + partPos1[0], 
							0D, 0.1D, 0D, (byte)18);
				ParticleHelper.spawnAttackParticleAt(this.posX + partPos2[1], this.posY + 0.9F, this.posZ + partPos2[0], 
							0D, 0.1D, 0D, (byte)18);
			}
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
		return ID.ShipMisc.AirfieldMount;
	}


}