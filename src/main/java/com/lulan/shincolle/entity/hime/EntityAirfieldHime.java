package com.lulan.shincolle.entity.hime;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.mounts.EntityMountAfH;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 * 0: mounts, 1: arm armor, 2: pose1, 3: pose2
 */
public class EntityAirfieldHime extends BasicEntityShipCV
{
	
	public EntityAirfieldHime(World world)
	{
		super(world);
		this.setSize(0.7F, 1.9F);
		this.setStateMinor(ID.M.ShipType, ID.ShipIconType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.AirfieldHime);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.AVIATION);
		this.setStateMinor(ID.M.NumState, 4);
		this.setGrudgeConsumeIdle(ConfigHandler.consumeGrudgeShipIdle[ID.ShipConsume.BBV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BBV]);
		this.modelPosInGUI = new float[] {-6F, 30F, 0F, 40F};
		this.launchHeight = this.height * 0.7F;
		
		//misc
		this.setFoodSaturationMax(16);
		
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
	
	//check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		//server side
  		if (!this.world.isRemote)
  		{
  			//飛行場特殊能力
        	if (this.ticksExisted % 128 == 0)
        	{
        		//1: 增強被動回血
        		if (getStateMinor(ID.M.NumGrudge) > 0 && this.getHealth() < this.getMaxHealth())
        		{
        			this.heal(this.getMaxHealth() * 0.06F + 1F);
        		}
        		
        		//2: 結婚後, 周圍某一目標回血, 包括玩家, 回血目標依等級提昇
				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) && getStateMinor(ID.M.NumGrudge) > 50)
				{
					//判定bounding box內是否有可以回血的目標
					int healCount = this.getLevel() / 15 + 2;
		            List<EntityLivingBase> hitList = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(12D, 12D, 12D));
		            TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		            
		            for (EntityLivingBase target : hitList)
		            {
		            	boolean canHeal = false;
		            	
		            	//補血名額沒了, break
		            	if (healCount <= 0) break;
		            	
		            	//抓可以補血的目標, 不包含自己
		            	if (target != this && TeamHelper.checkIsAlly(this, target) && target.getHealth() / target.getMaxHealth() < 0.96F)
		            	{
		            		if (target instanceof EntityPlayer)
	            			{
	            				target.heal(1F + target.getMaxHealth() * 0.04F + this.getLevel() * 0.04F);
	            				canHeal = true;
		            		}
		            		else if (target instanceof BasicEntityShip)
		            		{
		            			target.heal(1F + target.getMaxHealth() * 0.04F + this.getLevel() * 0.1F);
		            			canHeal = true;
			            	}
	            			
	            			if (canHeal)
	            			{
	            				CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, target, 1D, 0D, 0D, 4, false), point);
	            				healCount--;
		            			decrGrudgeNum(50);
	            			}
		            	}
		            }
				}//end heal ability
        	}
  		}//end server side
  		
  		super.onLivingUpdate();
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
		return new EntityMountAfH(this.world);
	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.65F;
  			}
  			else
  			{
  				return this.height * 0.56F;
  			}
  		}
  		else
  		{
  			return this.height * 0.75F;
  		}
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
	
	
}