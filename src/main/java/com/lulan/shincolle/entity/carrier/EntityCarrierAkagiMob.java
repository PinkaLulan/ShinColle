package com.lulan.shincolle.entity.carrier;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.entity.BasicEntityShipHostileCV;
import com.lulan.shincolle.reference.ID;

import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityCarrierAkagiMob extends BasicEntityShipHostileCV
{

	
	public EntityCarrierAkagiMob(World world)
	{
		super(world);
		
		//init values
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.CarrierAkagi);
        this.launchHeight = this.height * 0.65F;
        
		//model display
		this.setStateEmotion(ID.S.State2, ID.ModelState.EQUIP03a, false);
		
		if (this.getRNG().nextInt(3) == 0)
		{
			this.setStateEmotion(ID.S.State, ID.ModelState.EQUIP06, false);
		}
		else
		{
			this.setStateEmotion(ID.S.State, ID.ModelState.EQUIP02, false);
		}
	}
	
	@Override
	protected void setSizeWithScaleLevel()
	{
		switch (this.getScaleLevel())
		{
		case 3:
			this.setSize(2.4F, 7.5F);
		break;
		case 2:
			this.setSize(1.8F, 5.625F);
		break;
		case 1:
			this.setSize(1.2F, 3.75F);
		break;
		default:
			this.setSize(0.6F, 1.875F);
		break;
		}
	}
	
	@Override
	protected void setBossInfo()
	{
		this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_10);
	}

	//setup AI
	@Override
	protected void setAIList()
	{
		super.setAIList();

		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
	}

	@Override
	public int getDamageType()
	{
		return ID.ShipDmgType.CARRIER;
	}
	

}