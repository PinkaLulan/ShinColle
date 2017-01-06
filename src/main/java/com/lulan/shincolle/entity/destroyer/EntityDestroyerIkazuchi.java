package com.lulan.shincolle.entity.destroyer;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;


public class EntityDestroyerIkazuchi extends BasicEntityShipSmall implements IShipRiderType
{

	/**
	 * 六驅合體狀態: 0:none, 1:只有響, 2:只有電, 4:只有雷
	 * 可合計, ex: 3:有響跟雷, 6:有雷跟電, 7:有響雷電
	 */
	private int riderType;
	/** 雷電合體狀態 */
	public boolean isRaiden;
	private int ridingState;
	
	
	public EntityDestroyerIkazuchi(World world)
	{
		super(world);
		this.setSize(0.5F, 1.5F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.Ship.DestroyerIkazuchi);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.DESTROYER);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.DD]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.DD]);
		this.ModelPos = new float[] {0F, 25F, 0F, 50F};
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
		this.riderType = 0;
		this.isRaiden = false;
		this.ridingState = 0;
		
		this.postInit();
	}
	
	//for morph
	@Override
	public float getEyeHeight()
	{
		return 1.4F;
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
		
		//use range attack (light)
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
		
		//pick item
		this.tasks.addTask(20, new EntityAIShipPickItem(this, 4F));
	}
    
    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//server side
  		if (!world.isRemote)
  		{
  			if (this.ticksExisted % 32 == 0)
  			{
  				//check raiden gattai
  				this.checkRiderType();
				this.checkIsRaiden();
  				this.checkRidingState();
				
  				//add morale in raiden mode
  				if (this.riderType == 0 && this.isRaiden)
  				{
  					int m = this.getStateMinor(ID.M.Morale);
  					if (m < 7000) this.setStateMinor(ID.M.Morale, m + 100);
  				}
  				
  				if (this.ticksExisted % 128 == 0)
  	  			{
  	  				//add aura to master every 128 ticks
  	  				EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  	  				
  	  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  	  					getStateMinor(ID.M.NumGrudge) > 0 && getStateMinor(ID.M.CraneState) == 0 &&
  	  					player != null && getDistanceSqToEntity(player) < 256D)
  	  				{
  	  					//potion effect: id, time, level
  	  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, getStateMinor(ID.M.ShipLevel) / 50));
  	  				}
  	  				
  	  				//try gattai
  	  				tryRaidenGattai();
  	  			}//end 128 ticks
  			}//end 32 ticks
  		}
  		//client side
  		else
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				if (getStateEmotion(ID.S.State) >= ID.State.EQUIP01 && !isSitting() && !getStateFlag(ID.F.NoFuel))
  				{
  					double smokeY = posY + 1.4D;
  					
  					//計算煙霧位置
  	  				float[] partPos = CalcHelper.rotateXZByAxis(-0.42F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
  				}
  				
  	  			if (this.ticksExisted % 16 == 0)
  	  			{
  	  				this.checkRiderType();
  					this.checkIsRaiden();
  	  				this.checkRidingState();
  	  			}//end 16 ticks
  			}//end 4 ticks
  		}
  		
  		//sync rotate when gattai
  		if (this.getRidingEntity() instanceof EntityDestroyerAkatsuki)
		{
  			((EntityDestroyerAkatsuki) this.getRidingEntity()).syncRotateToRider();
		}
  		//sync rotate when raiden gattai
  		else if (this.getRidingEntity() instanceof EntityDestroyerInazuma)
		{
  			EntityDestroyerInazuma mount = (EntityDestroyerInazuma) this.getRidingEntity();
  			this.renderYawOffset = mount.renderYawOffset;
  			this.prevRenderYawOffset = mount.prevRenderYawOffset;
  			this.rotationYaw = mount.rotationYaw;
  			this.prevRotationYaw = mount.prevRotationYaw;
		}
  	}
  	
  	@Override
  	protected void updateFuelState(boolean nofuel)
	{
  		if (nofuel)
  		{
  	  		if (this.getRidingEntity() instanceof EntityDestroyerAkatsuki)
  	  		{
  	  			((EntityDestroyerAkatsuki) this.getRidingEntity()).dismountAllRider();
  	  		}
  	  		
  			//cancel raiden gattai
  			if (this.isRaiden)
  			{
  				this.dismountRaiden();
  			}
  		}
  		
  		super.updateFuelState(nofuel);
	}
  	
  	@Override
	public int getKaitaiType()
  	{
		return 2;
	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
  			return this.height * 0.15F;
  		}
  		else {
  			return this.height * 0.47F;
  		}
	}

	@Override
	public void setShipOutfit(boolean isSneaking)
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
	
  	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk)
  	{
		if (this.world.isRemote) return false;
		
		boolean dd = super.attackEntityFrom(attacker, atk);
		
		if (dd)
		{
			//cancel gattai
			if (this.getRidingEntity() instanceof EntityDestroyerAkatsuki)
			{
				((EntityDestroyerAkatsuki) this.getRidingEntity()).dismountAllRider();
			}
			
			//cancel raiden gattai
			if (this.isRaiden)
			{
				this.dismountRaiden();
			}
		}
		
		return dd;
	}
	
	//檢查是否可以合體
	public void tryRaidenGattai()
	{
		//stop gattai if sit/no fuel/already 6D gattai
  		if (isSitting() || getStateFlag(ID.F.NoFuel) || this.riderType > 0 || this.isRaiden) return;
		
		//not sitting, hp > 50%, not craning
		if (!getStateFlag(ID.F.NoFuel) && !isSitting() && !isRiding() && getHealth() > getMaxHealth() * 0.5F &&
			getStateMinor(ID.M.CraneState) == 0)
		{
			//get nearby ship
            List<EntityDestroyerInazuma> slist = this.world.getEntitiesWithinAABB(EntityDestroyerInazuma.class, this.getEntityBoundingBox().expand(4D, 4D, 4D));

            if (slist != null && !slist.isEmpty())
            {
            	for (EntityDestroyerInazuma s : slist)
            	{
            		if (s != null && TeamHelper.checkSameOwner(this, s) && s.isEntityAlive() &&
            			s.getRiderType() == 0 && !s.isRaiden && !s.getStateFlag(ID.F.NoFuel) &&
            			s.getStateMinor(ID.M.CraneState) == 0)
            		{
            			this.startRiding(s);
              			this.isRaiden = true;
              			s.isRaiden = true;
              			return;
            		}
            	}
            }//end get ship
		}//end can gattai
	}
	
	/**
	 * 合體狀態: 0:none, 1:有響, 2:有雷, 4:有電
	 * 可合計: ex: 3:有響跟雷, 7:有響雷電
	 */
  	public void checkRiderType()
  	{
  		this.riderType = 0;
  		
  		if (this.getRidingEntity() instanceof EntityDestroyerAkatsuki)
  		{
  			this.riderType = ((EntityDestroyerAkatsuki) this.getRidingEntity()).getRiderType();
  		}
  	}
	
  	/**
  	 * state: 0:無騎乘, 1:六驅合體 or 雷電合體
  	 */
  	public void checkRidingState()
  	{
  		if (this.riderType == 7 || this.isRaiden)
  		{
  			this.ridingState = 1;
  		}
  		else
  		{
  			this.ridingState = 0;
  		}
  	}
	
  	public void checkIsRaiden()
  	{
		if (this.getRidingEntity() instanceof EntityDestroyerInazuma)
		{
			this.isRaiden = true;
		}
		else
		{
			this.isRaiden = false;
		}
  	}
  	
  	//kick out all riders
  	public void dismountRaiden()
  	{
		if (this.getRidingEntity() instanceof EntityDestroyerInazuma)
		{
			this.isRaiden = false;
			((EntityDestroyerInazuma) this.getRidingEntity()).isRaiden = false;
			this.dismountRidingEntity();
		}
  	}
  	
	@Override
	public int getRiderType()
	{
		return this.riderType;
	}

	@Override
	public void setRiderType(int type)
	{
		this.riderType = type;
	}
	
  	@Override
  	public int getRidingState()
  	{
  		return this.ridingState;
  	}
  	
	@Override
	public void setRidingState(int state)
	{
		this.ridingState = state;
	}
  	

}
