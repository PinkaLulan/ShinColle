package com.lulan.shincolle.entity.battleship;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityBattleshipRu extends BasicEntityShip
{
	
	
	public EntityBattleshipRu(World world)
	{
		super(world);
		this.setSize(0.7F, 1.8F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.BattleshipRU);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.ModelPos = new float[] {0F, 25F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		this.postInit();
	}

	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
	@Override
	public int getEquipType()
	{
		return 1;
	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();

		//use range attack
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));
	}

	//Ta級額外增加屬性
	@Override
	public void calcShipAttributes()
	{
		EffectEquip[ID.EF_DHIT] += 0.15F;
		EffectEquip[ID.EF_THIT] += 0.15F;
		
		super.calcShipAttributes();	
	}
	
	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
  				return 0F;
  			}
  			else
  			{
  				return this.height * 0.47F;
  			}
  		}
  		else
  		{
  			return this.height * 0.76F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			int i = getStateEmotion(ID.S.State2) + 1;
			if (i > ID.State.EQUIP00a) i = ID.State.NORMALa;
			setStateEmotion(ID.S.State2, i, true);
		}
		else
		{
			int i = getStateEmotion(ID.S.State) + 1;
			if (i > ID.State.EQUIP02) i = ID.State.NORMAL;
			setStateEmotion(ID.S.State, i, true);
		}
	}
	
	@Override
	public boolean attackEntityWithHeavyAmmo(Entity target)
	{
		return super.attackEntityWithHeavyAmmo(target);
	}
	
    //change light cannon particle
    @Override
    public void applyParticleAtAttacker(int type, Entity target, float[] vec)
    {
  		super.applyParticleAtAttacker(type, target, vec);
  	}
	
	
}
