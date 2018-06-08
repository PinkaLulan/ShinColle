package com.lulan.shincolle.entity.hime;

import java.util.List;

import javax.annotation.Nullable;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.network.C2SInputPackets;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.reference.unitclass.MissileData;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;
import com.lulan.shincolle.utility.TeamHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * model state:
 *   0:wrist, 1:ring, 2:torpedo
 */
public class EntitySSNH extends BasicEntityShipSmall implements IShipInvisible
{
	
	private int goRidingTicks;		//騎乘目標尋找時間
	private boolean goRiding;		//是否要找目標騎乘
	private Entity goRideEntity;	//騎乘目標
	
	
	public EntitySSNH(World world)
	{
		super(world);
		this.setSize(0.5F, 0.9F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.SSNH);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.SUBMARINE);
		this.setStateMinor(ID.M.NumState, 3);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.SS]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.SS]);
		this.modelPosInGUI = new float[] {-6F, 8F, 0F, 50F};
		this.goRidingTicks = 0;
		this.goRideEntity = null;
		this.goRiding = false;
		
		//set attack type
		this.StateFlag[ID.F.AtkType_AirLight] = false;
		this.StateFlag[ID.F.AtkType_AirHeavy] = false;
		this.StateFlag[ID.F.CanPickItem] = true;
		
		this.postInit();
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
		
		//use range attack
		this.tasks.addTask(11, new EntityAIShipRangeAttack(this));
		
		//pick item
		this.tasks.addTask(20, new EntityAIShipPickItem(this, 4F));
	}

    //check entity state every tick
  	@Override
  	public void onLivingUpdate()
  	{
  		super.onLivingUpdate();
  		
  		if (!this.world.isRemote)
  		{
  			//every 64 ticks
        	if (this.ticksExisted % 64 == 0)
        	{
        		//increase morale when riding
        		if (this.isRiding() && !this.isMorph)
        		{
        			if (this.getMorale() < (int)(ID.Morale.L_Excited * 1.5F)) this.addMorale(150);
        		}
        		
	  			//every 128 ticks
	  			if (this.ticksExisted % 128 == 0)
	  			{
	  				if (getStateFlag(ID.F.UseRingEffect) && getStateMinor(ID.M.NumGrudge) > 0)
	  				{
	  					//owner invisible
	  					if (getStateFlag(ID.F.IsMarried))
	  					{
	  						EntityPlayerMP player = (EntityPlayerMP) EntityHelper.getEntityPlayerByUID(this.getPlayerUID());
	  	  	  				if (player != null && getDistanceSqToEntity(player) < 256D)
	  	  	  				{
	  	  	  					//potion effect: id, time, level
	  	  	  	  	  			player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 80+getLevel(), 0, false, false));
	  	  	  				}
	  					}
	  					
	  					//self invisible
	  					this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 80+getLevel(), 0, false, false));
	  				}
	  				
	  				//every 256 ticks
		        	if (this.ticksExisted % 256 == 0)
		        	{
		        		//每一段時間檢查是否要騎乘其他entity
		        		if (this.rand.nextInt(3) == 0 && !this.isMorph)
		        		{
		        			this.checkRiding();
		        		}
		        	}
	  			}//end 128 ticks
        	}//end 64 ticks
        	
        	//若要找騎乘目標
        	if (this.goRiding && !this.isMorph)
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
	public float getInvisibleLevel()
	{
		return 0.35F;
	}
	
	@Override
	public void setInvisibleLevel(float level) {}
	
	@Override
	public double getShipFloatingDepth()
	{
		return 1D;
	}
	
	/** use double missile to attack */
	@Override
	public boolean attackEntityWithAmmo(Entity target)
	{
		//ammo--
        if (!decrAmmoNum(0, this.getAmmoConsumption())) return false;
        
		//experience++
		addShipExp(ConfigHandler.expGain[1]);
		
		//grudge--
		decrGrudgeNum(ConfigHandler.consumeGrudgeAction[ID.ShipConsume.LAtk]);
		
  		//morale--
  		decrMorale(1);
  		setCombatTick(this.ticksExisted);
  		
        //calc dist to target
        Dist4d distVec = CalcHelper.getDistanceFromA2B(this, target);
        
        //play sound and particle
        applySoundAtAttacker(2, target);
	    applyParticleAtAttacker(2, target, distVec);
		
	    float tarX = (float) target.posX;
	    float tarY = (float) target.posY;
	    float tarZ = (float) target.posZ;
	    
	    //calc miss rate
        if (this.rand.nextFloat() <= CombatHelper.calcMissRate(this, (float)distVec.d))
        {
        	tarX = tarX - 5F + this.rand.nextFloat() * 10F;
        	tarY = tarY + this.rand.nextFloat() * 5F;
        	tarZ = tarZ - 5F + this.rand.nextFloat() * 10F;
        	
        	ParticleHelper.spawnAttackTextParticle(this, 0);  //miss particle
        }
        
        //get attack value
  		float atk = getAttackBaseDamage(1, target);
  		
  		//spawn missile
  		summonMissile(1, atk, tarX, tarY, tarZ, 1F);
  		
        //play target effect
        applySoundAtTarget(2, target);
        applyParticleAtTarget(2, target, distVec);
        applyEmotesReaction(3);
        
        if (ConfigHandler.canFlare) flareTarget(target);
        
        applyAttackPostMotion(1, this, true, atk);
        
        return true;
	}
	
	@Override
  	public float getAttackBaseDamage(int type, Entity target)
  	{
  		switch (type)
  		{
  		case 1:  //light cannon
  			return this.shipAttrs.getAttackDamage();
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
	
	/**
	 * change missile to double missile
	 */
	@Override
	public void summonMissile(int attackType, float atk, float tarX, float tarY, float tarZ, float targetHeight)
	{
		//發射位置: 左右兩側各一個
		float[] mPos1 = CalcHelper.rotateXZByAxis(0F, 1F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
		float[] mPos2 = CalcHelper.rotateXZByAxis(0F, -1F, (this.renderYawOffset % 360) * Values.N.DIV_PI_180, 1F);
			
		//missile type
		float launchPos = (float) posY + height * 0.6F;
		if (this.isMorph) launchPos += 0.5F;
		int moveType = CombatHelper.calcMissileMoveType(this, tarY, attackType);
		
		MissileData md = this.getMissileData(attackType);
		float[] data1 = new float[] {atk, 0.15F, launchPos, tarX, tarY + targetHeight * 0.1F, tarZ, 160, 0.25F, md.vel0, md.accY1, md.accY2, (float)this.posX + mPos1[1], launchPos, (float)this.posZ + mPos1[0], 4};
		float[] data2 = new float[] {atk, 0.15F, launchPos, tarX, tarY + targetHeight * 0.1F, tarZ, 160, 0.25F, md.vel0, md.accY1, md.accY2, (float)this.posX + mPos2[1], launchPos, (float)this.posZ + mPos2[0], 4};
		EntityAbyssMissile missile1 = new EntityAbyssMissile(this.world, this, md.type, moveType, data1);
		EntityAbyssMissile missile2 = new EntityAbyssMissile(this.world, this, md.type, moveType, data2);
        this.world.spawnEntity(missile1);
        this.world.spawnEntity(missile2);
	}
	
	//apply additional missile value
	@Override
	public void calcShipAttributesAddEquip()
	{
		super.calcShipAttributesAddEquip();
		
		MissileData md = this.getMissileData(1);
		
		md.vel0 += 0.3F;
		md.accY1 += 0.06F;
		md.accY2 += 0.06F;
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
    		
		//pick up for riding
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
  	

}