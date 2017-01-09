package com.lulan.shincolle.entity.battleship;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipSummonAttack;
import com.lulan.shincolle.entity.other.EntityRensouhou;
import com.lulan.shincolle.entity.other.EntityRensouhouS;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityBattleshipTa extends BasicEntityShip implements IShipSummonAttack
{
	
	public int numRensouhou;
	
	
	public EntityBattleshipTa(World world)
	{
		super(world);
		this.setSize(0.7F, 1.9F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.BattleshipTA);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.ModelPos = new float[] {0F, 25F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		this.postInit();
	}
	
	@Override
	public float getEyeHeight()
	{
		return 1.7375F;
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
	
	//check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		if (!world.isRemote)
  		{
  			//every 128 ticks
  			if (this.ticksExisted % 128 == 0)
  			{
  				//add num of rensouhou
  				if (this.numRensouhou < 6) numRensouhou++;
  			}
  		}    
  	}

	//Ta級額外增加屬性
	@Override
	public void calcShipAttributes()
	{
		EffectEquip[ID.EF_CRI] = EffectEquip[ID.EF_CRI] + 0.1F;
		EffectEquip[ID.EF_MISS] = EffectEquip[ID.EF_MISS] + 0.1F;
		
		super.calcShipAttributes();	
	}
	
	@Override
	//range attack method, cost light ammo, attack delay = 20 / attack speed, damage = 100% atk 
	public boolean attackEntityWithAmmo(Entity target)
	{	
		//check num rensouhou
  		if (this.numRensouhou <= 0)
  		{
  			return false;
  		}
  		else
  		{
  			this.numRensouhou--;
  		}
  		
        //play entity attack sound
		if (this.rand.nextInt(10) > 7)
		{
			this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
        }
        
        //light ammo--
        if (!decrAmmoNum(0, 4 * this.getAmmoConsumption()))
        {	//not enough ammo
        	return false;
        }
        
        //experience++
  		addShipExp(ConfigHandler.expGain[1] * 2);
  		
  		//grudge--
  		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk] * 4);
  		
  		//morale--
  		decrMorale(1);
  		setCombatTick(this.ticksExisted);

        //發射者煙霧特效 (招喚連裝砲不使用特效, 但是要發送封包來設定attackTime)
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 32D);
		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
  		
		//spawn airplane
    	if (this.getStateEmotion(ID.S.State2) > ID.State.NORMALa)
    	{
    		EntityRensouhou rensoho1 = new EntityRensouhou(this.world);
    		rensoho1.initAttrs(this, target, 0);
            this.world.spawnEntity(rensoho1);
    	}
    	else
    	{
    		EntityRensouhouS rensoho2 = new EntityRensouhouS(this.world);
    		rensoho2.initAttrs(this, target, 0);
            this.world.spawnEntity(rensoho2);
    	}
    	
    	//show emotes
      	applyEmotesReaction(3);
      	
      	if (ConfigHandler.canFlare) this.flareTarget(target);
      	
        return true;
	}

	@Override
	public int getNumServant()
	{
		return this.numRensouhou;
	}

	@Override
	public void setNumServant(int num)
	{
		this.numRensouhou = num;
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
			switch(getStateEmotion(ID.S.State2))
			{
			case ID.State.NORMALa:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00a, true);
			break;
			case ID.State.EQUIP00a:
				setStateEmotion(ID.S.State2, ID.State.NORMALa, true);
			break;	
			default:
				setStateEmotion(ID.S.State2, ID.State.NORMALa, true);
			break;
			}
		}
		else
		{
			switch (getStateEmotion(ID.S.State))
			{
			case ID.State.NORMAL:	//都沒有
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
			case ID.State.EQUIP00:	//只有披風
				setStateEmotion(ID.S.State, ID.State.EQUIP01, true);
			break;
			case ID.State.EQUIP01:	//只有護肩
				setStateEmotion(ID.S.State, ID.State.EQUIP02, true);
			break;
			case ID.State.EQUIP02:	//披風+護肩
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
			default:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
			}
		}
	}
	
	
}