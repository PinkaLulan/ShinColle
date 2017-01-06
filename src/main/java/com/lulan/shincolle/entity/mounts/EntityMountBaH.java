package com.lulan.shincolle.entity.mounts;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.ai.path.ShipMoveHelper;
import com.lulan.shincolle.ai.path.ShipPathNavigate;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityMountBaH extends BasicEntityMount
{
	
    public EntityMountBaH(World world)
    {
		super(world);
		this.setSize(1.9F, 3.1F);
		this.seatPos = new float[] {1.05F, 2.6F, 0F};
		this.seatPos2 = new float[] {1.2F, 0.7F, -1.3F};
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
  	public void applyParticleAtAttacker(int type, Entity target, float[] vec)
  	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 10, 1.2D, 1.8D, 0.5D), point);
  			//發送攻擊flag給host, 設定其attack time
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host, 0, true), point);
  		break;
  		case 2:  //heavy cannon
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
			//發送攻擊flag給host, 設定其attack time
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this.host, 0, true), point);
		break;
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