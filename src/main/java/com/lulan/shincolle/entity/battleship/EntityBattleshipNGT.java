package com.lulan.shincolle.entity.battleship;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipEmotion;
import com.lulan.shincolle.entity.hime.EntityNorthernHime;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EmotionHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**特殊heavy attack:
 * 用StateEmotion[ID.S.Phase]來儲存攻擊階段
 * Phase 1:集氣 2:爆氣 3:集氣 
 * 
 * model state:
 *   0:head , 1:equip
 */
public class EntityBattleshipNGT extends BasicEntityShipSmall
{

	public EntityBattleshipNGT(World world)
	{
		super(world);
		this.setSize(0.7F, 2F);	//碰撞大小 跟模型大小無關
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.BBNagato);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.BATTLESHIP);
		this.setStateMinor(ID.M.NumState, 2);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BB]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BB]);
		this.modelPosInGUI = new float[] {0F, 25F, 0F, 40F};
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		
		//misc
		this.setFoodSaturationMax(20);
		
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
	}
	
    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		//client side
  		if (this.world.isRemote)
  		{
  			if (this.ticksExisted % 4 == 0)
  			{
  				//生成裝備冒煙特效
  				if (EmotionHelper.checkModelState(1, this.getStateEmotion(ID.S.State)) && !isSitting() && !getStateFlag(ID.F.NoFuel))
  				{
  					//計算煙霧位置
  	  				float[] partPos = CalcHelper.rotateXZByAxis(-0.56F, 0F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
  	  				ParticleHelper.spawnAttackParticleAt(posX+partPos[1], posY + 1.5D, posZ+partPos[0], 1D, 0D, 0D, (byte)43);
  				}
  				
  				if (this.ticksExisted % 8 == 0)
  	  			{
  					//生成氣彈特效
  	  				if (getStateEmotion(ID.S.Phase) == 1 || getStateEmotion(ID.S.Phase) == 3)
  	  				{
  	  	  				ParticleHelper.spawnAttackParticleAtEntity(this, 0.12D, 1D, 0D, (byte)1);
  	  				}
  	  			}//end 8 ticks
  			}//end 4 ticks
  		}//end client side
  		//server side
  		else
  		{
  			if (this.ticksExisted % 128 == 0)
  			{
  				//add morale if DD or Hoppo nearby
  				addMoraleSpecialEvent(this);
  				
  				//married effect: apply str buff to nearby ships
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0)
  				{
  					List<BasicEntityShip> shiplist = this.world.getEntitiesWithinAABB(BasicEntityShip.class, this.getEntityBoundingBox().expand(16D, 16D, 16D));
  	  	  			
  					if (shiplist != null && shiplist.size() > 0)
  					{
  						for (BasicEntityShip s : shiplist)
  						{
  							if (TeamHelper.checkSameOwner(this, s))
  							{
  								//potion effect: id, time, level
  								s.addPotionEffect(new PotionEffect(MobEffects.STRENGTH , 50+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 70, false, false));
  							}
  						}
  					}
  				}//end married buff
  			}
  		}//end server
  	}
  	
	//add morale if DD or Hoppo nearby, add 150 per ship found
  	protected static void addMoraleSpecialEvent(BasicEntityShip host)
  	{
  		if (host.isMorph()) return;
  		
		//get nearby ship
		List<EntityLivingBase> slist = host.world.getEntitiesWithinAABB(EntityLivingBase.class, host.getEntityBoundingBox().expand(16D, 12D, 16D));
		List<EntityLivingBase> target = new ArrayList<EntityLivingBase>();
		
		if (slist != null && !slist.isEmpty())
		{
			//check riders
        	for (EntityLivingBase ent : slist)
        	{
        		if (ent instanceof EntityNorthernHime || (ent instanceof IShipEmotion &&
        			((IShipEmotion)ent).getStateMinor(ID.M.ShipType) == ID.ShipType.DESTROYER))
        		{
        			target.add(ent);
        		}
        	}
        	
        	if (target.size() > 0)
        	{
        		//add morale = ship * 150
        		if (host.getMorale() < (int)(ID.Morale.L_Excited * 1.5F)) host.addMorale(150 * target.size());
        		
        		//try move to target if not in combat
        		if (!host.isSitting() && !host.isRiding() && !host.getStateFlag(ID.F.NoFuel) &&
        			EntityHelper.checkShipOutOfCombat(host) && host.getRand().nextFloat() > 0.5F)
        		{
        			host.getShipNavigate().tryMoveToEntityLiving(target.get(host.getRand().nextInt(target.size())), 1F);
        		
        			//show emote
        			switch (host.getRand().nextInt(5))
        			{
        			case 1:
        				host.applyParticleEmotion(31);  //shy
    				break;
        			case 2:
        				host.applyParticleEmotion(1);  //heart
    				break;
        			case 3:
        				host.applyParticleEmotion(7);  //note
    				break;
        			case 4:
        				host.applyParticleEmotion(16);  //haha
    				break;
        			default:
        				host.applyParticleEmotion(29);  //blink
    				break;
        			}
        		}
        	}
		}
  	}
	
	@Override
	public void calcShipAttributesAddEffect()
	{
		super.calcShipAttributesAddEffect();
		this.AttackEffectMap.put(19, new int[] {(int)(this.getLevel() / 75), 60+this.getLevel(), this.getLevel()});
	}
  	
  	/**Type 91 Armor-Piercing Fist
  	 * 需要進行4階段攻擊(3階段準備), 對目標造成重攻擊的4倍傷害, 額外追加8x8範圍1倍傷害
  	 * 
  	 * phase: 0:charge, 1:burst, 2:charge, 3:attack
  	 */
  	@Override
  	public boolean attackEntityWithHeavyAmmo(Entity target)
  	{
		//calc equip special dmg: AA, ASM
  		float atk1 = CombatHelper.modDamageByAdditionAttrs(this, target, this.getAttackBaseDamage(2, target), 2);
  		float atk2 = atk1 * 0.5F;
  		
		boolean isTargetHurt = false;
		
		//experience++
		addShipExp(ConfigHandler.expGain[2]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.HAtk]);
		
  		//morale--
		decrMorale(2);
  		setCombatTick(this.ticksExisted);
		
		//heavy ammo--
        if (!decrAmmoNum(1, this.getAmmoConsumption())) return false;
	
		//play cannon fire sound at attacker
		int atkPhase = getStateEmotion(ID.S.Phase);
		
        switch (atkPhase)
        {
        case 1:
        	this.playSound(ModSounds.SHIP_AP_P2, ConfigHandler.volumeFire, 1F);
        break;
        case 3:
        	this.playSound(ModSounds.SHIP_AP_ATTACK, ConfigHandler.volumeFire, 1F);
        break;
    	default:
    		this.playSound(ModSounds.SHIP_AP_P1, ConfigHandler.volumeFire, 1F);
    	break;
        }
        
        //play entity attack sound
        if (this.getRNG().nextInt(10) > 7)
        {
        	this.playSound(this.getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
        }
        
        //phase++
        atkPhase++;
        TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
      
        if (atkPhase > 3)
        {	//攻擊準備完成, 計算攻擊傷害
	        //calc dist to target
	        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
	        
        	//display hit particle on target
	        CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 21, posX, posY, posZ, target.posX, target.posY, target.posZ, true), point);
        	
		    //roll miss, cri, dhit, thit
		    atk1 = CombatHelper.applyCombatRateToDamage(this, target, false, (float)distVec.d, atk1);
	  		
	  		//damage limit on player target
		    atk1 = CombatHelper.applyDamageReduceOnPlayer(target, atk1);
	  		
	  		//check friendly fire
			if (!TeamHelper.doFriendlyFire(this, target)) atk1 = 0F;
			
			//aoe damage
			atk2 = atk1 * 0.5F;
	        
      		//對本體造成atk1傷害
      		isTargetHurt = target.attackEntityFrom(DamageSource.causeMobDamage(this), atk1);
      		
      		//move host to target
  			this.motionX = 0D;
  			this.motionY = 0D;
  			this.motionZ = 0D;
  			this.posX = target.posX + distVec.x * 2F;
  			this.posY = target.posY;
  			this.posZ = target.posZ + distVec.z * 2F;
  			this.setPosition(posX, posY, posZ);
      		
      		//對範圍造成atk2傷害
            Entity hitEntity = null;
            AxisAlignedBB impactBox = this.getEntityBoundingBox().expand(3.5D, 3.5D, 3.5D); 
            List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class, impactBox);
            float atkTemp = atk2;
            
            //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
            if (hitList != null && !hitList.isEmpty())
            {
                for (int i = 0; i < hitList.size(); ++i)
                {
                	atkTemp = atk2;
                	hitEntity = hitList.get(i);
                	
                	//目標不能是自己 or 主人
                	if (hitEntity != this && !TargetHelper.isEntityInvulnerable(hitEntity) && hitEntity.canBeCollidedWith())
                	{
                		//若攻擊到同陣營entity (ex: owner), 則傷害設為0 (但是依然觸發擊飛特效)
                		if (TeamHelper.checkSameOwner(this, hitEntity))
                		{
                			atkTemp = 0F;
                    	}
                		
            		    //roll miss, cri, dhit, thit
                		atkTemp = CombatHelper.applyCombatRateToDamage(this, hitEntity, false, (float)distVec.d, atkTemp);
            	  		
            	  		//damage limit on player target
                		atkTemp = CombatHelper.applyDamageReduceOnPlayer(hitEntity, atkTemp);
            	  		
            	  		//check friendly fire
            			if (!TeamHelper.doFriendlyFire(this, hitEntity)) atkTemp = 0F;
                		
                		//attack
                	    hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), atkTemp);
                	}//end can be collided with
                }//end hit target list for loop
            }//end hit target list != null
        	
        	this.setStateEmotion(ID.S.Phase, 0, true);
        }
        else
        {
        	//show attack particle
        	if (atkPhase == 2)
        	{
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 23, this.posX, this.posY, this.posZ, 0.35D, 0.3D, 0D, true), point);
        	}
        	else
        	{
        		CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 22, this.posX, this.posY, this.posZ, 2D, 1D, 0D, true), point);
        	}
    		
        	this.setStateEmotion(ID.S.Phase, atkPhase, true);
        }
        
        //show emotes
      	applyEmotesReaction(3);
      	
      	if (ConfigHandler.canFlare) this.flareTarget(target);
      	
        return isTargetHurt;
	}

	@Override
	public double getMountedYOffset()
	{
		if (this.isSitting())
		{
			if (EmotionHelper.checkModelState(1, this.getStateEmotion(ID.S.State)))
			{
				return this.height * 0.42F;
			}
			else
			{
				if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
				{
					return 0F;
	  			}
	  			else
	  			{
	  				return this.height * 0.35F;
	  			}
			}
  		}
  		else
  		{
  			return this.height * 0.75F;
  		}
	}
	
	@Override
	public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
	{
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 5, 0.9D, 1D, 1.1D), point);
  		break;
		default: //melee
			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, 0, true), point);
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
  			return this.shipAttrs.getAttackDamageHeavy();
  		case 3:  //light aircraft
  			return this.shipAttrs.getAttackDamageAir();
  		case 4:  //heavy aircraft
  			return this.shipAttrs.getAttackDamageAirHeavy();
		default: //melee
			return this.shipAttrs.getAttackDamage() * 3F;
  		}
  	}


}