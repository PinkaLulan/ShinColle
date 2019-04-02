package com.lulan.shincolle.entity.destroyer;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.IShipRiderType;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

/**
 * 六驅單縱陣合體特性:
 * 1. 單縱陣狀態
 * 2. 曉響雷電互相靠近
 * 3. 響雷電全部騎乘在曉身上
 * 4. 必須有響騎乘在曉時, 雷電才會接著騎乘
 * 
 * model state:
 *   0:cannon, 1:head, 2:weapon, 3:armor
 */
public class EntityDestroyerAkatsuki extends BasicEntityShipSmall implements IShipRiderType
{

	/**
	 * 六驅合體狀態: 0:none, 1:只有響, 2:只有電, 4:只有雷
	 * 可合計, ex: 3:有響跟雷, 6:有雷跟電, 7:有響雷電
	 * 
	 * valid: 0, 1, 3, 7
	 * invalid: 2, 4, 5, 6
	 */
	private int riderType;
	private int ridingState;
	
	public EntityDestroyerAkatsuki(World world)
	{
		super(world);
		this.setSize(0.5F, 1.5F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.DESTROYER);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.DDAkatsuki);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.DESTROYER);
		this.setStateMinor(ID.M.NumState, 4);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.DD]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.DD]);
		this.ModelPos = new float[] {0F, 25F, 0F, 50F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
		this.riderType = 0;
		this.ridingState = 0;
		
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
  				//add morale when gattai
  				this.checkRiderType();
  				
  				//validate rider type
  				if (this.riderType == 2 || this.riderType == 4 ||
  					this.riderType == 5 || this.riderType == 6)
  				{
  					this.dismountAllRider();
  				}

  				if (this.riderType > 0)
				{
  					this.addMoraleToRider();
  	  				if (this.getMorale() < (int)(ID.Morale.L_Excited * 1.5F)) this.addMorale(100);
				}
  				
  				if (this.ticksExisted % 128 == 0)
  	  			{
	  				EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
	  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
	  					getStateMinor(ID.M.NumGrudge) > 0 && player != null && getDistanceSq(player) < 256D)
	  				{
	  					//potion effect: id, time, level
	  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.HASTE , 80+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 30, false, false));
	  				}
  	  				
  	  				//try gattai
  	  				tryGattai();
  	  				
  	  				//sync gattai
  	  				if (this.ridingState > 0)
  	  				{
  	  					this.sendSyncPacketRiders();
  	  				}
  	  			}
  			}//end every 32 ticks
  		}
  		//client side
  		else
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				if (EmotionHelper.checkModelState(0, this.getStateEmotion(ID.S.State)) &&
  					!isSitting() && !getStateFlag(ID.F.NoFuel) && this.riderType < 1)
  				{
  					double smokeY = posY + 1.4D;
  					float addz = 0F;
  					
  					if (this.getRidingEntity() != null) addz = -0.2F;
  					
  					//計算煙霧位置
  	  				float[] partPos = CalcHelper.rotateXZByAxis(-0.42F + addz, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				//生成裝備冒煙特效
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], smokeY, posZ+partPos[0], 0D, 0D, 0D, (byte)20);
  				}
  				
  	  			if (this.ticksExisted % 16 == 0)
  	  			{
  	  				this.checkRiderType();
  	  			}//end 16 ticks
  			}//end 4 ticks
  		}
  		
  		//sync rotate when gattai
	  	if (!this.isMorph) this.syncRotateToRider();
  	}
  	
  	@Override
  	protected void updateFuelState(boolean nofuel)
	{
  		if (nofuel) this.dismountAllRider();
  		
  		super.updateFuelState(nofuel);
	}
  	
  	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk)
  	{
		if (this.world.isRemote) return false;
		
		boolean dd = super.attackEntityFrom(attacker, atk);
		
		if (dd)
		{
			//remove all passenger
			this.dismountAllRider();
		}
		
		return dd;
	}

  	@Override
	public double getMountedYOffset()
  	{
		if (this.isSitting())
		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * -0.07F;
  			}
  			else
  			{
  				return this.height * 0.26F;
  			}
  		}
  		else
  		{
  			return this.height * 0.64F;
  		}
	}
  	
  	@Override
    public void updatePassenger(Entity rider)
    {
        if (this.isPassenger(rider))
        {
        	double yoffset = this.posY + rider.getYOffset();
        	
        	if (rider instanceof EntityDestroyerHibiki)
        	{
        		yoffset += this.isSitting() ? 0.22F : 0.68F;
        		float[] partPos = CalcHelper.rotateXZByAxis(-0.2F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
        		rider.setPosition(this.posX + partPos[1], yoffset - 0.45D, this.posZ + partPos[0]);
        	}
        	else if (rider instanceof EntityDestroyerInazuma)
        	{
        		//sync emotion
        		((EntityDestroyerInazuma) rider).setStateEmotion(ID.S.Emotion, this.getStateEmotion(ID.S.Emotion), false);
        		
        		yoffset += this.isSitting() ? -0.08F : 0.68F;
        		float[] partPos = CalcHelper.rotateXZByAxis(-0.48F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
        		rider.setPosition(this.posX + partPos[1], yoffset + 0.1D, this.posZ + partPos[0]);
        	}
        	else if (rider instanceof EntityDestroyerIkazuchi)
        	{
        		List<Entity> riders = this.getPassengers();
        		
        		for (Entity r : riders)
        		{
        			if (r instanceof EntityDestroyerInazuma)
        			{
        				yoffset += this.isSitting() ? -0.08F : 0.68F;
        				//check Inazuma emotion state
        				yoffset += ((EntityDestroyerInazuma) r).getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED ? 0.5F : 0.6F;
        				float[] partPos = CalcHelper.rotateXZByAxis(-0.68F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
                		rider.setPosition(this.posX + partPos[1], yoffset, this.posZ + partPos[0]);
                		break;
        			}
        		}
        	}
        	else
        	{
        		yoffset += this.getMountedYOffset();
        		rider.setPosition(this.posX, yoffset, this.posZ);
        	}
        }
    }
  	
  	//檢查是否可以合體
  	public void tryGattai()
  	{
  		//dismount if craning
  		if (getStateMinor(ID.M.CraneState) > 0)
  		{
  			this.dismountAllRider();
  			this.dismountRidingEntity();
  			return;
  		}
  		
  		//stop gattai if sit/no fuel/already gattai
  		if (isSitting() || getStateFlag(ID.F.NoFuel) || this.riderType == 7) return;
  		
  		//not sitting, hp > 50%, not craning, formation = line ahead
  		if (!getStateFlag(ID.F.NoFuel) && !isSitting() && getHealth() > getMaxHealth() * 0.5F &&
  			getStateMinor(ID.M.CraneState) == 0 && getStateMinor(ID.M.FormatType) == 1)
  		{
  			//get nearby ship
  			List<BasicEntityShip> slist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, this.getEntityBoundingBox().expand(6D, 5D, 6D));
  			BasicEntityShip getHibiki = null;
  			BasicEntityShip getInazuma = null;
  			BasicEntityShip getIkazuchi = null;
  			
  			if (slist != null && !slist.isEmpty())
  			{
  				//check riders
              	for (BasicEntityShip s : slist)
              	{
              		if (s instanceof EntityDestroyerHibiki && TeamHelper.checkSameOwner(this, s) &&
              			s.isEntityAlive() && !s.isRiding() && !s.getStateFlag(ID.F.NoFuel) && !s.isSitting() &&
              			s.getStateMinor(ID.M.CraneState) == 0 && s.getStateMinor(ID.M.FormatType) == 1)
              		{
              			getHibiki = s;
              		}
              		else if (s instanceof EntityDestroyerInazuma && TeamHelper.checkSameOwner(this, s) &&
              				 s.isEntityAlive() && !s.getStateFlag(ID.F.NoFuel) && !s.isSitting() &&
              				 s.getStateMinor(ID.M.CraneState) == 0 && s.getStateMinor(ID.M.FormatType) == 1)
              		{
              			getInazuma = s;
              		}
              		else if (s instanceof EntityDestroyerIkazuchi && TeamHelper.checkSameOwner(this, s) &&
             				 s.isEntityAlive() && !s.getStateFlag(ID.F.NoFuel) && !s.isSitting() &&
             				 s.getStateMinor(ID.M.CraneState) == 0 && s.getStateMinor(ID.M.FormatType) == 1)
             		{
              			getIkazuchi = s;
             		}
              	}
              	
              	//apply gattai
              	switch (this.riderType)
              	{
              	case 0:
              		if (getHibiki != null)
          			{
              			getHibiki.startRiding(this, true);
              			if (getInazuma != null)
              			{
              				getInazuma.startRiding(this, true);
              				if (getIkazuchi != null) getIkazuchi.startRiding(this, true);
              			}
          			}
              	break;
              	case 1:
          			if (getInazuma != null)
          			{
          				getInazuma.startRiding(this, true);
          				if (getIkazuchi != null) getIkazuchi.startRiding(this, true);
          			}
              	break;
              	case 3:
          			if (getIkazuchi != null) getIkazuchi.startRiding(this, true);
              	break;
              	}//end rider type switch
  			}//end get ship
  		}//end can gattai
  	}
  	
  	//add morale when gattai
  	public void addMoraleToRider()
  	{
  		List<Entity> list = this.getPassengers();
  		
  		for (Entity rider : list)
  		{
  			if (rider instanceof BasicEntityShip)
  			{
  				if (((BasicEntityShip) rider).getMorale() < (int)(ID.Morale.L_Excited * 1.5F)) ((BasicEntityShip) rider).addMorale(100);
  				
  				//set rider type to riders
  				if (rider instanceof IShipRiderType)
  				{
  					((IShipRiderType) rider).setRiderType(this.riderType);
  				}
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
  	
	/**
	 * 合體狀態: 0:none, 1:有響, 2:有雷, 4:有電
	 * 可合計: ex: 3:有響跟雷, 7:有響雷電
	 */
  	public void checkRiderType()
  	{
  		boolean getHibiki = false;
  		List<Entity> list = this.getPassengers();
  		this.riderType = 0;
  		
  		for (Entity rider : list)
  		{
  			if (rider instanceof EntityDestroyerHibiki)
  			{
  				this.riderType |= 1;
  				getHibiki = true;
  			}
  			else if (rider instanceof EntityDestroyerInazuma)
  			{
  				this.riderType |= 2;
  			}
  			else if (rider instanceof EntityDestroyerIkazuchi)
  			{
  				this.riderType |= 4;
  			}
  		}
  		
  		if (getHibiki)
		{
  			this.ridingState = 1;
		}
  		else
  		{
  			this.ridingState = 0;
  		}
  	}
  	
  	//kick out all riders
  	public void dismountAllRider()
  	{
  		this.riderType = 0;
  		this.ridingState = 0;
  		
  		List<Entity> riders = this.getPassengers();
  		
  		for (Entity rider : riders)
  		{
  			if (rider instanceof IShipRiderType)
  			{
  				((IShipRiderType) rider).setRiderType(0);
  			}
  			
  			if (rider instanceof IShipEmotion)
  			{
  				((IShipEmotion) rider).setRidingState(0);
  			}
  			
  			rider.dismountRidingEntity();
  		}
  	}
  	
  	//sync rotateYaw to all rider
  	public void syncRotateToRider()
  	{
  		List<Entity> list = this.getPassengers();
  		
  		for (Entity rider : list)
  		{
  			if (rider instanceof IShipRiderType)
  			{
  				((EntityLivingBase) rider).renderYawOffset = this.renderYawOffset;
  				((EntityLivingBase) rider).prevRenderYawOffset = this.prevRenderYawOffset;
  				rider.rotationYaw = this.rotationYaw;
  				rider.prevRotationYaw = this.prevRotationYaw;
  			}
  		}
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
	
	//increase inventory size
  	@Override
  	public void calcShipAttributesAddEquip()
  	{
  		super.calcShipAttributesAddEquip();
  		
  		if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect))
  		{
  			this.StateMinor[ID.M.LevelSearchlight] = 1;
  		}
  	}
  	
  	
}