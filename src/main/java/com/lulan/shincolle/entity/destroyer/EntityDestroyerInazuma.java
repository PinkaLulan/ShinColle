package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;


/**
 * 六驅單縱陣合體特性: 詳見EntityDestroyerAkatsuki.class
 * 
 * 雷電合體特性:
 * 1. 除了六驅合體以外, 雷電可自行合體
 * 2. 雷跟電在4格內
 * 
 * model state:
 *   0:cannon
 */
public class EntityDestroyerInazuma extends BasicEntityShipSmall implements IShipRiderType
{
	
	/**
	 * 六驅合體狀態: 0:none, 1:只有響, 2:只有電, 4:只有雷
	 * 可合計, ex: 3:有響跟雷, 6:有雷跟電, 7:有響雷電
	 */
	private int riderType;
	/** 雷電合體狀態 */
	public boolean isRaiden;
	private int ridingState;

	
	public EntityDestroyerInazuma(World world)
	{
		super(world);
		this.setSize(0.5F, 1.5F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.DDInazuma);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.DESTROYER);
		this.setStateMinor(ID.M.NumState, 1);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.DD]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.DD]);
		this.ModelPos = new float[] {0F, 25F, 0F, 50F};
		
		//set attack type
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
  			if (this.ticksExisted % 32 == 0 && !this.isMorph)
  			{
  				//check raiden gattai
  				this.checkRiderType();
				this.checkIsRaiden();
  				this.checkRidingState();
					
  				//add morale in raiden mode
  				if (this.riderType == 0 && this.isRaiden)
  				{
  					if (this.getMorale() < (int)(ID.Morale.L_Excited * 1.5F)) this.addMorale(100);
  				}
  				
  				if (this.ticksExisted % 128 == 0)
  	  			{
  					EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  	  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  	  					getStateMinor(ID.M.NumGrudge) > 0 && player != null && getDistanceSq(player) < 256D)
  	  				{
  	  					//potion effect: id, time, level
  	  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.SPEED , 80+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 45, false, false));
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
  				if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)) &&
  					!isSitting() && !getStateFlag(ID.F.NoFuel) && this.riderType < 4)
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
  		else if (this.isRaiden)
  		{
  			List<Entity> riders = this.getPassengers();
  			
  			for (Entity rider : riders)
  			{
  				if (rider instanceof EntityDestroyerIkazuchi)
  				{
  					((EntityDestroyerIkazuchi) rider).renderYawOffset = this.renderYawOffset;
  					((EntityDestroyerIkazuchi) rider).prevRenderYawOffset = this.prevRenderYawOffset;
  					rider.rotationYaw = this.rotationYaw;
  					rider.prevRotationYaw = this.prevRotationYaw;
  				}
  			}
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
  	  			this.dismountRidingEntity();
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
    public void updatePassenger(Entity rider)
    {
        if (this.isPassenger(rider))
        {
        	double yoffset = this.posY + rider.getYOffset();
        	
        	if (rider instanceof EntityDestroyerIkazuchi)
        	{
        		yoffset += this.isSitting() ? 0.26F : 0.68F;
        		yoffset += this.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED ? -0.6D : -0.45D;
        		float[] partPos = CalcHelper.rotateXZByAxis(-0.2F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
        		rider.setPosition(this.posX + partPos[1], yoffset, this.posZ + partPos[0]);
        	}
        	else
        	{
        		yoffset += this.getMountedYOffset();
        		rider.setPosition(this.posX, yoffset, this.posZ);
        	}
        }
    }
  	
  	@Override
	public double getMountedYOffset()
  	{
		if (this.isSitting())
		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.23F;
  			}
  			else
  			{
  				return this.height * 0.44F;
  			}
  		}
  		else
  		{
  			return this.height * 0.64F;
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
  		//dismount if craning
  		if (getStateMinor(ID.M.CraneState) > 0)
  		{
  			this.dismountRaiden();
  			this.dismountRidingEntity();
  			return;
  		}
  		
  		//stop gattai if sit/no fuel/already 6D gattai
  		if (isSitting() || getStateFlag(ID.F.NoFuel) || this.riderType > 0 || this.isRaiden) return;
  		
  		/** 雷電自行合體 */
  		//not sitting, hp > 50%, not craning
  		if (!getStateFlag(ID.F.NoFuel) && !isSitting() && !isRiding() && getHealth() > getMaxHealth() * 0.5F &&
  			getStateMinor(ID.M.CraneState) == 0)
  		{
  			//get nearby ship
  			List<EntityDestroyerIkazuchi> slist = this.world.getEntitiesWithinAABB(EntityDestroyerIkazuchi.class, this.getEntityBoundingBox().expand(4D, 4D, 4D));

  			if (slist != null && !slist.isEmpty())
  			{
              	for (EntityDestroyerIkazuchi s : slist)
              	{
              		if (s != null && TeamHelper.checkSameOwner(this, s) && s.isEntityAlive() &&
              			s.getRiderType() == 0 && !s.getStateFlag(ID.F.NoFuel) && !s.isRaiden &&
              			s.getStateMinor(ID.M.CraneState) == 0)
              		{
              			s.startRiding(this);
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
  	 * state: 0:無騎乘, 1:六驅合體狀態且雷不在, 2:雷電合體狀態, 3:六驅完整合體狀態
  	 */
  	public void checkRidingState()
  	{
  		if (this.riderType == 7)
  		{
  			this.ridingState = 3;
  		}
  		else if (this.isRaiden)
  		{
  			this.ridingState = 2;
  		}
  		else if (this.riderType == 3)
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
  		this.isRaiden = false;
  		
  		List<Entity> riders = this.getPassengers();
  		
  		for (Entity rider : riders)
  		{
  			if (rider instanceof EntityDestroyerIkazuchi)
  			{
  				this.isRaiden = true;
  				break;
  			}
  		}
  	}
  	
  	//kick out all riders
  	public void dismountRaiden()
  	{
  		List<Entity> riders = this.getPassengers();
  		
  		for (Entity rider : riders)
  		{
  			if (rider instanceof EntityDestroyerIkazuchi)
  			{
  				this.isRaiden = false;
  				((EntityDestroyerIkazuchi) rider).isRaiden = false;
  				rider.dismountRidingEntity();
  			}
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