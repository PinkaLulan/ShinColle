package com.lulan.shincolle.entity.transport;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.handler.EventHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityTransportWa extends BasicEntityShipSmall
{

	
	public EntityTransportWa(World world)
	{
		super(world);
		this.setSize(0.7F, 1.53F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.TRANSPORT);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.TransportWA);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.UNDEFINED);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.AP]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.AP]);
		this.ModelPos = new float[] {-3F, 10F, 0F, 45F};
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		this.StateFlag[ID.F.AtkType_Light] = false;
		this.StateFlag[ID.F.AtkType_Heavy] = false;
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
		this.postInit();
	}
	
  	@Override
	public int getKaitaiType()
  	{
		return 0;
	}

	@Override
	public int getEquipType()
	{
		return 1;
	}
	
	@Override
	public void setAIList()
	{
		super.setAIList();
		
		//pick up item AI
		this.tasks.addTask(5, new EntityAIShipPickItem(this, 8F));	//0111
	}

    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//client side
  		if (this.world.isRemote)
  		{
  			if (this.ticksExisted % 128 == 0)
  			{
  				//show hungry emotes
  				if (this.rand.nextInt(4) == 0)
  				{
  					this.applyParticleEmotion(2);
  				}
  			}//end 128 ticks
  		}//end client side
  		//server side
  		else
  		{
  			//check every 128 ticks
  			if (this.ticksExisted % 128 == 0)
  			{
  				//consume supplies to a fixed level
  				if (this.getStateMinor(ID.M.NumGrudge) <= 5400)
  				{
  					consumeSupplyItems(0);
  				}
  				
				if (this.getStateMinor(ID.M.NumAmmoLight) <= 540)
				{
					consumeSupplyItems(1);
				}
				
				if (this.getStateMinor(ID.M.NumAmmoHeavy) <= 270)
				{
					consumeSupplyItems(2);
				}
  				
				//check every 256 ticks
  				if (this.ticksExisted % 256 == 0 && !this.getStateFlag(ID.F.NoFuel))
  				{
					//supply ammo/grudge to nearby ships
	  				int supCount = this.getLevel() / 50 + 1;
	  				double range = 2D + this.getAttackRange() * 0.5D;
	  				boolean canSupply = false;
		            List<BasicEntityShip> slist = null;
		            TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);

		            //get nearby ships
		            slist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, this.getEntityBoundingBox().expand(range, range, range));

		            for (BasicEntityShip s : slist)
		            {
		            	//名額沒了, break
		            	if (supCount <= 0) break;
		            	
		            	//check same owner
		            	if (TeamHelper.checkSameOwner(this, s))
		            	{
		            		//supply grudge
		            		if (this.getStateMinor(ID.M.NumGrudge) > 5400 && s.getStateMinor(ID.M.NumGrudge) < 2700)
		            		{
		            			canSupply = true;
		            			this.setStateMinor(ID.M.NumGrudge, this.getStateMinor(ID.M.NumGrudge) - 5400);
		            			s.setStateMinor(ID.M.NumGrudge, s.getStateMinor(ID.M.NumGrudge) + 5400);
		            		}
		            		
		            		//supply light ammo
		            		if (this.getStateMinor(ID.M.NumAmmoLight) >= 540 && s.getStateMinor(ID.M.NumAmmoLight) < 270)
		            		{
		            			canSupply = true;
		            			this.setStateMinor(ID.M.NumAmmoLight, this.getStateMinor(ID.M.NumAmmoLight) - 540);
		            			s.setStateMinor(ID.M.NumAmmoLight, s.getStateMinor(ID.M.NumAmmoLight) + 540);
		            		}
		            		
		            		//supply heavy ammo
		            		if (this.getStateMinor(ID.M.NumAmmoHeavy) >= 270 && s.getStateMinor(ID.M.NumAmmoHeavy) < 135)
		            		{
		            			canSupply = true;
		            			this.setStateMinor(ID.M.NumAmmoHeavy, this.getStateMinor(ID.M.NumAmmoHeavy) - 270);
		            			s.setStateMinor(ID.M.NumAmmoHeavy, s.getStateMinor(ID.M.NumAmmoHeavy) + 270);
		            		}
		            		
		            		//count--
		            		if (canSupply)
		            		{
		            			//add exp
		            			addShipExp(ConfigHandler.expGain[6] * 20);
		            			
		            			//sync
		            			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, s, 0.75D, 0D, 0D, 4, false), point);
		            			supCount--;
		            		}
		            	}//end same owner
		            }//end for all ships
  				}//end 256 ticks
  			}//end 128 ticks
  		}//end server side
  	}

	/** type: 0:grudge, 1:light ammo, 2:heavy ammo */
	private void consumeSupplyItems(int type)
	{
		switch (type)
		{
		case 0:
			//try to find grudge
			if (decrSupplies(4))
			{	//find grudge
				if (ConfigHandler.easyMode)
				{
					StateMinor[ID.M.NumGrudge] += Values.N.BaseGrudge * 10;
				}
				else
				{
					StateMinor[ID.M.NumGrudge] += Values.N.BaseGrudge;
				}
			}
			else
			{
				if (decrSupplies(5))
				{	//find grudge block
					if (ConfigHandler.easyMode)
					{
						StateMinor[ID.M.NumGrudge] += Values.N.BaseGrudge * 90;
					}
					else
					{
						StateMinor[ID.M.NumGrudge] += Values.N.BaseGrudge * 9;
					}
				}
			}
		break;
		case 1:
			if (decrSupplies(0))
			{
				if (ConfigHandler.easyMode)
				{
					StateMinor[ID.M.NumAmmoLight] += Values.N.BaseLightAmmo * 10;
				}
				else
				{
					StateMinor[ID.M.NumAmmoLight] += Values.N.BaseLightAmmo;
				}
			}
			else
			{
				if (decrSupplies(2))
				{
					if (ConfigHandler.easyMode)
					{
						StateMinor[ID.M.NumAmmoLight] += Values.N.BaseLightAmmo * 90;
					}
					else
					{
						StateMinor[ID.M.NumAmmoLight] += Values.N.BaseLightAmmo * 9;
					}
				}
			}
		break;
		case 2:
			if (decrSupplies(1))
			{
				if (ConfigHandler.easyMode)
				{
					StateMinor[ID.M.NumAmmoHeavy] += Values.N.BaseHeavyAmmo * 10;
				}
				else
				{
					StateMinor[ID.M.NumAmmoHeavy] += Values.N.BaseHeavyAmmo;
				}
			}
			else
			{
				if (decrSupplies(3))
				{
					if (ConfigHandler.easyMode)
					{
						StateMinor[ID.M.NumAmmoHeavy] += Values.N.BaseHeavyAmmo * 90;
					}
					else
					{
						StateMinor[ID.M.NumAmmoHeavy] += Values.N.BaseHeavyAmmo * 9;
					}
				}
			}
		break;
		}//end switch
	}
	
	//increase inventory size
  	@Override
  	public void calcShipAttributes()
  	{
  		StateMinor[ID.M.InvSize] = 2;
  		
  		super.calcShipAttributes();	
  	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (getStateEmotion(ID.S.State) > ID.State.EQUIP00)
  		{
  			if (this.isSitting())
  	  		{
  				return this.height * 0.5F;
  	  		}
  	  		else
  	  		{
  	  			return this.height * 0.64F;
  	  		}
  		}
  		else
  		{
  			if (this.isSitting())
  	  		{
  	  			return 0F;
  	  		}
  	  		else
  	  		{
  	  			return this.height * 0.64F;
  	  		}
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking)
	{
		if (isSneaking)
		{
			switch (getStateEmotion(ID.S.State2))
			{
			case ID.State.NORMAL_2:
				setStateEmotion(ID.S.State2, ID.State.EQUIP00_2, true);
			break;
			default:
				setStateEmotion(ID.S.State2, ID.State.NORMAL_2, true);
			break;
			}
		}
		else
		{
			switch (getStateEmotion(ID.S.State))
			{
			case ID.State.NORMAL:
				setStateEmotion(ID.S.State, ID.State.EQUIP00, true);
			break;
			case ID.State.EQUIP00:
				setStateEmotion(ID.S.State, ID.State.EQUIP01, true);
			break;
			case ID.State.EQUIP01:
				setStateEmotion(ID.S.State, ID.State.EQUIP02, true);
			break;
			default:
				setStateEmotion(ID.S.State, ID.State.NORMAL, true);
			break;
			}
		}
	}


}