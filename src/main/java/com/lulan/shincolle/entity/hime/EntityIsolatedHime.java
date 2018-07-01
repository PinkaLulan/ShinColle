package com.lulan.shincolle.entity.hime;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.mounts.EntityMountIsH;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.dataclass.MissileData;
import com.lulan.shincolle.utility.CombatHelper;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * model state:
 *   0:mounts, 1:hat, 2:horn, 3:bowtie, 4:shawl, 5:hand, 6:leg, 7:leg armor
 */
public class EntityIsolatedHime extends BasicEntityShipCV
{
	
	public EntityIsolatedHime(World world)
	{
		super(world);
		this.setSize(0.6F, 1.6F);
		this.setStateMinor(ID.M.ShipType, ID.ShipIconType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.IsolatedHime);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.AVIATION);
		this.setStateMinor(ID.M.NumState, 8);
		this.setGrudgeConsumeIdle(ConfigHandler.consumeGrudgeShipIdle[ID.ShipConsume.BBV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BBV]);
		this.modelPosInGUI = new float[] {-6F, 30F, 0F, 40F};
		this.launchHeight = this.height * 0.7F;
		
		//misc
		this.setFoodSaturationMax(18);
		
		this.initPre();
	}

	@Override
	public int getEquipType()
	{
		return 2;
	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipCarrierAttack(this));
		this.tasks.addTask(12, new EntityAIShipRangeAttack(this));
	}
	
	@Override
	public void calcShipAttributesAddEffect()
	{
		super.calcShipAttributesAddEffect();
		
		this.AttackEffectMap.put(15, new int[] {0, 100+this.getLevel(), this.getLevel()});
		
		if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect))
		{
			this.AttackEffectMap.put(19, new int[] {(int)(this.getLevel() / 75), 80+this.getLevel(), this.getLevel()});
		}
	}
	
	@Override
	public void calcShipAttributesAddEquip()
	{
		super.calcShipAttributesAddEquip();
		
		MissileData md = this.getMissileData(2);
		if (md.type == 0) md.type = 5;
	}

	@Override
	public float getAttackBaseDamage(int type, Entity target)
	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			return CombatHelper.modDamageByAdditionAttrs(this, target, this.shipAttrs.getAttackDamage(), 0);
  		case 2:  //heavy cannon
  			return this.shipAttrs.getAttackDamageHeavy();
  		case 3:  //light aircraft
  			return this.shipAttrs.getAttackDamageAir();
  		case 4:  //heavy aircraft
  			return this.shipAttrs.getAttackDamageAirHeavy();
		default: //melee
			return this.shipAttrs.getAttackDamage();
  		}
  	}

  	//true if use mounts
  	@Override
  	public boolean hasShipMounts()
  	{
  		return true;
  	}
  	
  	@Override
  	public BasicEntityMount summonMountEntity()
  	{
		return new EntityMountIsH(this.world);
	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.2F;
  			}
  			else
  			{
  				return this.height * 0.52F;
  			}
  		}
  		else
  		{
  			return this.height * 0.76F;
  		}
	}
	
	
}