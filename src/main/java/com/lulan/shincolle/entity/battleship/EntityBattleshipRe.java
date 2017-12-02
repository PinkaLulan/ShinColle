package com.lulan.shincolle.entity.battleship;

import java.util.List;

import com.lulan.shincolle.ai.EntityAIShipCarrierAttack;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShipCV;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.network.S2CEntitySync;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.TargetHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * model state:
 *   0:head, 1:bag, 2:ear
 */
public class EntityBattleshipRe extends BasicEntityShipCV
{
	
	private boolean isPushing = false;
	private int tickPush = 0;
	private EntityLivingBase targetPush = null;
	
	
	public EntityBattleshipRe(World world)
	{
		super(world);
		this.setSize(0.6F, 1.55F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.BATTLESHIP);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.BattleshipRE);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.AVIATION);
		this.setStateMinor(ID.M.NumState, 3);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.BBV]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.BBV]);
		this.ModelPos = new float[] {-6F, 25F, 0F, 40F};
		this.launchHeight = this.height * 0.8F;
		
		this.StateFlag[ID.F.HaveRingEffect] = true;
		
		this.postInit();
	}

	//equip type: 1:cannon+misc 2:cannon+airplane+misc 3:airplane+misc
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
	public void calcShipAttributesAddRaw()
	{
		super.calcShipAttributesAddRaw();
		
		this.maxAircraftLight += this.getLevel() * 0.1F;
		this.maxAircraftHeavy += this.getLevel() * 0.05F;
		
		this.getAttrs().setAttrsRaw(ID.Attrs.DHIT, this.getAttrs().getAttrsRaw(ID.Attrs.DHIT) + 0.1F);
		this.getAttrs().setAttrsRaw(ID.Attrs.THIT, this.getAttrs().getAttrsRaw(ID.Attrs.THIT) + 0.1F);
	}
	
	@Override
	public void calcShipAttributesAddEffect()
	{
		super.calcShipAttributesAddEffect();
		this.AttackEffectMap.put(4, new int[] {(int)(this.getLevel() / 100), 100+this.getLevel(), this.getLevel()});
	}
	
	@Override
    public void onLivingUpdate()
	{
    	//check server side
    	if (!this.world.isRemote)
    	{

  			//every 128 ticks
  			if ((this.ticksExisted & 127) == 0)
  			{
  				//add aura to master
  				EntityPlayer player = EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
  				if (getStateFlag(ID.F.IsMarried) && getStateFlag(ID.F.UseRingEffect) &&
  					getStateMinor(ID.M.NumGrudge) > 0 && player != null && getDistanceSqToEntity(player) < 256D)
  				{
  					//potion effect: id, time, level
  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE , 50+getStateMinor(ID.M.ShipLevel), getStateMinor(ID.M.ShipLevel) / 50, false, false));
  				}
  				
  				//every 256 ticks
  	        	if ((this.ticksExisted & 255) == 0)
  	        	{
  	        		//push other people
  	        		if (this.getRNG().nextInt(5) == 0 && !this.isSitting() && !this.isRiding() &&
  	        			!this.getStateFlag(ID.F.NoFuel) && !this.getIsLeashed())
  	        		{
  	        			//find target
  	        			this.findTargetPush();
  	        		}
  	        	}//end every 256 ticks
  			}//end every 128 ticks
    		
    		//若要找騎乘目標
        	if (this.isPushing)
        	{
        		this.tickPush++;
        		
        		//找太久, 放棄騎乘目標
        		if (this.tickPush > 200 || this.targetPush == null)
        		{
        			this.cancelPush();
        		}
        		else
        		{
        			float distPush = this.getDistanceToEntity(this.targetPush);
        			
            		if (distPush <= 2.5F)
            		{
            			this.targetPush.addVelocity(-MathHelper.sin(rotationYaw * Values.N.DIV_PI_180) * 0.5F, 
         	                   0.5D, MathHelper.cos(rotationYaw * Values.N.DIV_PI_180) * 0.5F);
            			
            			//for other player, send ship state for display
            	  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 48D);
            	  		CommonProxy.channelE.sendToAllAround(new S2CEntitySync(this.targetPush, 0, S2CEntitySync.PID.SyncEntity_Motion), point);
					    
					    //play entity attack sound
            	  		this.swingArm(EnumHand.MAIN_HAND);
            	  		this.playSound(getCustomSound(1, this), this.getSoundVolume(), this.getSoundPitch());
					    this.cancelPush();
            		}
            		else
            		{
            			//每32 tick找一次路徑
                		if (this.ticksExisted % 32 == 0)
                		{
                			this.getShipNavigate().tryMoveToEntityLiving(this.targetPush, 1D);
                		}
            		}
        		}
        	}//end push target
    	}
    	
    	super.onLivingUpdate();
    }
    
    private void cancelPush()
    {
    	this.isPushing = false;
    	this.tickPush = 0;
    	this.targetPush = null;
    }
    
    //find target to push
    private void findTargetPush()
    {
        AxisAlignedBB impactBox = this.getEntityBoundingBox().expand(12D, 6D, 12D); 
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, impactBox);
        
        //valid entity
        list.removeIf(ent -> this.equals(ent) || !ent.canBePushed() || !ent.canBeCollidedWith());
        
        //從可騎乘目標中挑出一個目標騎乘
        if (list.size() > 0)
        {
        	this.targetPush = list.get(rand.nextInt(list.size()));
        	this.tickPush = 0;
			this.isPushing = true;
        }
    }
    
	//if attack successfully, spread light beam to nearby target
    @Override
    public void applyAttackPostMotion(int type, Entity target, boolean isTargetHurt, float atk)
	{
    	if (type == 1 && isTargetHurt)
    	{
    		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
    		//max number = LV * 0.05F = 0~7
    		int num = (int) (this.getLevel() * 0.05F);
    		
            Entity hitEntity = null;
            //range = 3.5D
            AxisAlignedBB impactBox = target.getEntityBoundingBox().expand(3.5D, 3.5D, 3.5D); 
            List<Entity> hitList = this.world.getEntitiesWithinAABB(Entity.class, impactBox);
            //atk = 20% main target atk
            float atkTemp = atk * 0.2F;
            
            //搜尋list, 找出第一個可以判定的目標, 即傳給onImpact
            if (hitList != null && !hitList.isEmpty())
            {
                for (int i = 0; num > 0 && i < hitList.size(); ++i)
                {
                	hitEntity = hitList.get(i);

                	//目標不能是自己 or 主人
                	if (!hitEntity.equals(this) && !hitEntity.equals(target) &&
                		!TargetHelper.checkUnattackTargetList(hitEntity) &&
                		hitEntity.canBeCollidedWith() && !TeamHelper.checkSameOwner(this, hitEntity))
                	{
                		//roll miss, cri, dhit, thit
                		atkTemp = CombatHelper.applyCombatRateToDamage(this, hitEntity, true, 3F, atkTemp);
                  		
                		//damage limit on player target
                		atkTemp = CombatHelper.applyDamageReduceOnPlayer(hitEntity, atkTemp);
                  		
                  		//check friendly fire
                		if (!TeamHelper.doFriendlyFire(this, hitEntity)) atkTemp = 0F;

                		//attack
                		num--;
                	    hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), atkTemp);
                	    
                	    //apply attack effect
                	    CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(target,
                	    		hitEntity, target.height * 0.5D, 0.025D, 0.008D, 6, true), point);
            	    	applySoundAtTarget(1, hitEntity);
            	        applyParticleAtTarget(1, hitEntity, null);
                	}//end can be damaged
                }//end hit target list for loop
            }//end hit target list != null
    	}//end if successfully light attack
	}
    
    //change light cannon particle
    @Override
    public void applyParticleAtAttacker(int type, Entity target, Dist4d distVec)
    {
  		TargetPoint point = new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 64D);
        
  		switch (type)
  		{
  		case 1:  //light cannon
  			CommonProxy.channelP.sendToAllAround(new S2CSpawnParticle(this, target, this.isRiding() ? 1.45D : 1.7D, 0.08D, 0.02D, 6, true), point);
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
  			this.playSound(ModSounds.SHIP_LASER, ConfigHandler.volumeFire * 0.25F, this.getSoundPitch() * 0.85F);
  	        
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
  		}
  	}

	@Override
	public double getMountedYOffset()
	{
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.35F;
  			}
  			else
  			{
  				return 0F;
  			}
  		}
  		else
  		{
  			return this.height * 0.55F;
  		}
	}
	
	
}