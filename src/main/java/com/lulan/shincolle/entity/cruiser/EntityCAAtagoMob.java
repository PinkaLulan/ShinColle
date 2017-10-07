package com.lulan.shincolle.entity.cruiser;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.reference.ID;

import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

/**
 * model state:
 *   0:cannon, 1:bag, 2:hat, 3:shoes
 */
public class EntityCAAtagoMob extends BasicEntityShipHostile
{
	

	public EntityCAAtagoMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.HeavyCruiserAtago);
        
		//model display
		this.setStateEmotion(ID.S.State, this.rand.nextInt(16), false);
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