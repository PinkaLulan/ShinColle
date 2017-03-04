package com.lulan.shincolle.entity.cruiser;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.reference.ID;

import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityCATakaoMob extends BasicEntityShipHostile
{
	

	public EntityCATakaoMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.Ship.HeavyCruiserTakao);
        
		//model display
		this.setStateEmotion(ID.S.State, this.rand.nextInt(4), false);
		this.setStateEmotion(ID.S.State2, this.rand.nextInt(4), false);
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(1.7F, 7F);
		break;
		case 2:
			this.setSize(1.3F, 5.25F);
		break;
		case 1:
			this.setSize(0.9F, 3.5F);
		break;
		default:
			this.setSize(0.75F, 1.75F);
		break;
		}
	}

	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_10);
	}

	//setup AI
	@Override
	protected void setAIList()
	{
		super.setAIList();

		//use range attack
		this.tasks.addTask(1, new EntityAIShipRangeAttack(this));
	}

	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.CRUISER;
	}
	

}