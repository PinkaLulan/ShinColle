package com.lulan.shincolle.entity.hime;

import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.entity.other.EntityFloatingFort;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 *   0:cannon, 1:hat, 2:umbrella, 3:leg equip
 */
public class EntityNorthernHime extends BasicEntityShipCV
{
	
	private int goRidingTicks;		//騎乘目標尋找時間
	private boolean goRiding;		//是否要找目標騎乘
	private Entity goRideEntity;	//騎乘目標
	
	
	public EntityNorthernHime(World world)
	{
		super(world);
		this.setSize(0.6F, 1F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.NorthernHime);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.AVIATION);
		this.setStateMinor(ID.M.NumState, 4);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BBV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BBV]);
		this.ModelPos = new float[] {-6F, 8F, 0F, 50F};
		this.goRidingTicks = 0;
		this.goRideEntity = null;
		this.goRiding = false;
		this.launchHeight = this.height;
		
		//set attack type
		this.StateFlag[ID.F.HaveRingEffect] = true;
		
		//misc
		this.setFoodSaturationMax(16);
		
		this.postInit();
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
  			//every 64 ticks
        	if (this.ticksExisted % 64 == 0)
        	{
        		//increase morale when riding
        		if (this.isRiding())
        		{
        			if (this.getMorale() < (int)(ID.Morale.L_Excited * 1.5F)) this.addMorale(150);
        		}
        		
        		//1: 增強被動回血
        		if (getStateMinor(ID.M.NumGrudge) > 0 && this.getHealth() < this.getMaxHealth())
        		{
        			this.heal(this.getMaxHealth() * 0.03F + 1F);
        		}
        		
        		//2: 結婚後, 周圍某一目標回血, 包括玩家, 回血目標依等級提昇
				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) && getStateMinor(ID.M.NumGrudge) > 25)
				{
					//判定bounding box內是否有可以回血的目標
					int healCount = this.getLevel() / 25 + 1;
		            List<EntityLivingBase> hitList = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(8D, 8D, 8D));
		            TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
		            
		            for (EntityLivingBase target : hitList)
		            {
		            	boolean canHeal = false;
		            	
		            	//補血名額沒了, break
		            	if (healCount <= 0) break;
		            	
		            	//抓可以補血的目標, 不包含自己
		            	if (target != this && TeamHelper.checkIsAlly(this, target) && target.getHealth() / target.getMaxHealth() < 0.98F)
		            	{
	            			if (target instanceof EntityPlayer)
	            			{
	            				target.heal(1F + this.getLevel() * 0.02F);
	            				canHeal = true;
		            		}
		            		else if (target instanceof BasicEntityShip)
		            		{
		            			target.heal(1F + target.getMaxHealth() * 0.02F + this.getLevel() * 0.1F);
		            			canHeal = true;
			            	}
	            			
	            			if (canHeal)
	            			{
	            				CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, target, 1D, 0D, 0D, 4, false), point);
	            				healCount--;
		            			decrGrudgeNum(25);
	            			}
		            	}
		            }
				}//end heal ability
				
				//every 256 ticks
	        	if (this.ticksExisted % 256 == 0)
	        	{
	        		//每一段時間檢查是否要騎乘其他entity
	        		if (this.rand.nextInt(3) == 0)
	        		{
	        			this.checkRiding();
	        		}
	        	}
        	}//end 64 ticks
        	
        	//若要找騎乘目標
        	if (this.goRiding)
        	{
        		this.goRidingTicks++;
        		
        		//找太久, 放棄騎乘目標
        		if (this.goRidingTicks > 200)
        		{
        			this.cancelGoRiding();
        		}
        		
        		float distRiding = 0F;
        		if (this.goRideEntity != null)
        		{
        			distRiding = this.getDistanceToEntity(this.goRideEntity);
        		}
        		
        		//每32 tick找一次路徑
        		if (this.ticksExisted % 32 == 0)
        		{
        			if (distRiding > 2F)
        			{
        				this.getShipNavigate().tryMoveToEntityLiving(this.goRideEntity, 1D);
        			}
        		}
        		
        		//距離2格內則騎乘目標
        		if (distRiding <= 2F)
        		{
        			if (this.goRideEntity != null && !this.goRideEntity.isRiding() &&
        				this.getPassengers().size() == 0 && this.goRideEntity.getPassengers().size() == 0)
        			{
        				this.startRiding(goRideEntity, true);
        				this.getShipNavigate().clearPathEntity();
        				this.cancelGoRiding();
        			}
        		}
        	}
        	
        	//dismount if mounts is sneaking, SERVER SIDE
        	if (this.getRidingEntity() != null && this.getRidingEntity().isSneaking())
        	{ 	
        		this.dismountRidingEntity();
        		this.sendSyncPacketRiders();
        	}
  		}//end server side
  		//client side
  		else
  		{
  			//drip water effect
  			if (this.ticksExisted % 8 == 0)
  			{
  				if (EmotionHelper.checkModelState(2, this.getStateEmotion(ID.S.State)) &&
  					!getStateFlag(ID.F.NoFuel))
  				{
  					if (this.isSitting() || this.isRiding())
  					{
  						ParticleHelper.spawnAttackParticleAt(this.posX, this.posY+0.9D, this.posZ, 0D, 0D, 0D, (byte)28);
  					}
  					else
  					{
  						ParticleHelper.spawnAttackParticleAt(this.posX, this.posY+1.1D, this.posZ, 0D, 0D, 0D, (byte)28);
  					}
  				}
  			}
  		}
  			
  		super.onLivingUpdate();
  	}
  	
  	//cancel go riding entity
  	private void cancelGoRiding()
  	{
  		this.goRidingTicks = 0;
  		this.goRideEntity = null;
  		this.goRiding = false;
  	}
	
  	//mount other entity
	private void checkRiding()
	{
		//reset flag
		this.cancelGoRiding();
		
		if (this.isSitting() || this.getLeashed() || this.getStateFlag(ID.F.NoFuel))
		{
			return;
		}
		
		//已經在騎乘, 則機率下坐騎
		if (this.isRiding() && this.rand.nextInt(2) == 0)
		{
			this.dismountRidingEntity();
		}
		else
		{
	        List<EntityLivingBase> hitList = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(6D, 4D, 6D));
	        
	        hitList.removeIf(target ->
	        	!(target instanceof BasicEntityShip || target instanceof EntityPlayer) ||
	        	this.equals(target) || target.isRiding() || target.getPassengers().size() > 0 ||
	        	!TeamHelper.checkSameOwner(this, target)
	        );

	        //從可騎乘目標中挑出一個目標騎乘
	        if (hitList.size() > 0)
	        {
	        	this.goRideEntity = hitList.get(rand.nextInt(hitList.size()));
	        	this.goRidingTicks = 0;
    			this.goRiding = true;
	        }
		}//end not riding
	}

	@Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand)
    {
    	//禁用副手
    	if (hand == EnumHand.OFF_HAND) return EnumActionResult.FAIL;
    	
    	//死亡時不反應
    	if (!this.isEntityAlive()) return EnumActionResult.FAIL;
    		
		//pick up northern for riding
		if (this.world.isRemote)
		{
			if (this.isSitting() && this.getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED &&
				(stack == null || stack.getItem() != ModItems.PointerItem))
			{
				//send riding request packet
				CommonProxy.channelI.sendToServer(new C2SInputPackets(C2SInputPackets.PID.Request_Riding, this.getEntityId(), this.world.provider.getDimension()));
				
				return EnumActionResult.FAIL;	//no swing animation
			}
		}
		
		return super.applyPlayerInteraction(player, vec, stack, hand);
  	}
	
	@Override
    public boolean attackEntityFrom(DamageSource attacker, float atk)
	{
		//若騎乘別的ship, 則取消騎乘
		if (this.getRidingEntity() instanceof BasicEntityShip || this.getRidingEntity() instanceof EntityPlayer)
		{
			this.dismountRidingEntity();
		}
		
		return super.attackEntityFrom(attacker, atk);
	}
  	
  	//貓章魚燒攻擊
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
  	{
        //calc dist to target
  		float launchPos = (float)posY + height;
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
		
        if (getShipDepth() > 0D) launchPos += 0.2D;
		
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
		
		//play attack effect
        applySoundAtAttacker(2, target);
	    applyParticleAtAttacker(2, target, distVec);

        //heavy ammo--
        if (!decrAmmoNum(1, this.getAmmoConsumption())) return false;
        
        //spawn missile
        EntityFloatingFort ffort = new EntityFloatingFort(this.world);
        ffort.initAttrs(this, target, 0, launchPos);
        this.world.spawnEntity(ffort);
        
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
      	applyEmotesReaction(3);
        
      	if (ConfigHandler.canFlare) flareTarget(target);
      	
        return true;
  	}
  	
  	@Override
    public double getYOffset()
    {
        return 0.25F;
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
  				return this.height * 0.08F;
  			}
  		}
  		else
  		{
  			return this.height * 0.48F;
  		}
	}
	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 31, this.posX, this.posY, this.posZ, distVec.x, distVec.y, distVec.z, true), point);
  		break;
  		case 2:  //heavy cannon
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
		break;
  		}
  	}
  	
	@Override
	public void applyParticleAtTarget(int type, Entity target, Dist4d distVec)
	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
  		
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 30, false), point);
  	        break;
  		case 2:  //heavy cannon
  			break;
  		case 3:  //light aircraft
  			break;
  		case 4:  //heavy aircraft
  			break;
		default: //melee
    		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target, 1, false), point);
			break;
  		}
  	}
	
	@Override
	public void applySoundAtAttacker(int type, Entity target)
	{
  		switch (type)
  		{
  		case 1:  //light cannon
  		case 2:  //heavy cannon
  			this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, ConfigHandler.volumeFire * 1.3F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
  	  		
  	        //entity sound
  	        if (this.getRNG().nextInt(10) > 7)
  	        {
  	        	this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
  		case 3:  //light aircraft
  		case 4:  //heavy aircraft
  			this.playSound(ModSounds.SHIP_AIRCRAFT, ConfigHandler.volumeFire * 0.5F, this.getSoundPitch() * 0.85F);
  	  	  	
  	        //entity sound
  	        if (this.getRNG().nextInt(10) > 7)
  	        {
  	        	this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
  	        }
  		break;
		default: //melee
			if (this.getRNG().nextInt(2) == 0)
			{
				this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
	        }
		break;
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
  			return this.shipAttrs.getAttackDamageHeavy() * 0.75F;
  		case 3:  //light aircraft
  			return this.shipAttrs.getAttackDamageAir();
  		case 4:  //heavy aircraft
  			return this.shipAttrs.getAttackDamageAirHeavy();
		default: //melee
			return this.shipAttrs.getAttackDamage();
  		}
  	}
	
	
}