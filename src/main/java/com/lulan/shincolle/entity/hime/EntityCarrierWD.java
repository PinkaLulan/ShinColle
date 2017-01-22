package com.lulan.shincolle.entity.hime;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.mounts.EntityMountCaWD;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CalcHelper;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityCarrierWD extends BasicEntityShipCV
{
	
	public EntityCarrierWD(World world)
	{
		super(world);
		this.setSize(0.7F, 1.9F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DEMON);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.CarrierWD);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.CARRIER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.CV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.CV]);
		this.ModelPos = new float[] {-6F, 30F, 0F, 40F};
		this.launchHeight = this.height * 1.2F;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		
		//misc
		this.setFoodSaturationMax(30);
		
		this.postInit();
	}

	@Override
	public int getEquipType()
	{
		return 3;
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
	public float getAttackBaseDamage(int type, Entity target)
	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			return CalcHelper.calcDamageBySpecialEffect(this, target, StateFinal[ID.ATK_AL], 0);
  		case 2:  //heavy cannon
  			return StateFinal[ID.ATK_H];
  		case 3:  //light aircraft
  			return StateFinal[ID.ATK_AL];
  		case 4:  //heavy aircraft
  			return StateFinal[ID.ATK_AH];
		default: //melee
			return StateFinal[ID.ATK] * 0.125F;
  		}
  	}
	
	//change light cannon particle
    @Override
    public void applyParticleAtAttacker(int type, Entity target, float[] vec)
    {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  	        double shotHeight = 1.85D;
  	        
  	        if(this.isRiding() && this.getRidingEntity() instanceof BasicEntityMount)
  	        {
  	        	shotHeight = 0.8D;
  	        }
  	        
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, target, shotHeight, 0D, 0D, 0, true), point);
  		break;
  		case 2:  //heavy cannon
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
  		}
  	}

    //change light cannon sound
    @Override
    public void applySoundAtAttacker(int type, Entity target)
    {
  		switch (type)
  		{
  		case 1:  //light cannon
  			this.playSound(ModSounds.SHIP_LASER, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  	        
  			//entity sound
  			if (this.rand.nextInt(10) > 7)
  			{
  				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
  		case 2:  //heavy cannon
  			this.playSound(ModSounds.SHIP_FIREHEAVY, ConfigHandler.volumeFire, this.getSoundPitch() * 0.85F);
  	        
  	        //entity sound
  	        if (this.getRNG().nextInt(10) > 7)
  	        {
  	        	this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  			this.playSound(ModSounds.SHIP_AIRCRAFT, ConfigHandler.volumeFire * 0.5F, this.getSoundPitch() * 0.85F);
  	  	break;
		default: //melee
			if (this.getRNG().nextInt(2) == 0)
			{
				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
	        }
		break;
  		}//end switch
  	}
  	
  	//禁用重型攻擊
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
  	{
  		return false;
  	}

  	//true if use mounts
  	@Override
  	public boolean canSummonMounts()
  	{
  		return true;
  	}
  	
  	@Override
  	public BasicEntityMount summonMountEntity()
  	{
		return new EntityMountCaWD(this.world);
	}
  	
  	//被騎乘的高度
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.29F;
  			}
  			else
  			{
  				return this.height * 0.33F;
  			}
  		}
  		else
  		{
  			return this.height * 0.75F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		//切換裝備顯示
		if (isSneaking)
		{
			switch (getStateEmotion(ID.S.State2))
			{
			case ID.State.EQUIP00a:
				setStateEmotion(ID.S.State2, ID.State.NORMALa, true);
			break;
			case ID.State.NORMALa:
			default:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00a, true);
			break;
			}
		}
		//切換是否騎乘座騎
		else
		{
			switch (getStateEmotion(ID.S.State))
			{
			case ID.State.EQUIP00:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
				this.setPositionAndUpdate(posX, posY + 2D, posZ);
			break;
			case ID.State.NORMAL:
			default:
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
			}
		}
	}


}