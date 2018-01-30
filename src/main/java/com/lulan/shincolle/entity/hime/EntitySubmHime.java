package com.lulan.shincolle.entity.hime;

import com.lulan.shincolle.ai.EntityAIShipPickItem;
import com.lulan.shincolle.ai.EntityAIShipRangeAttack;
import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntityShipSmall;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.mounts.EntityMountSuH;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import com.lulan.shincolle.reference.unitclass.MissileData;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.CombatHelper;
import com.lulan.shincolle.utility.EntityHelper;
import com.lulan.shincolle.utility.ParticleHelper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * model state:
 *   0:mount, 1:collar, 2:tails
 */
public class EntitySubmHime extends BasicEntityShipSmall implements IShipInvisible
{
	

	public EntitySubmHime(World world)
	{
		super(world);
		this.setSize(0.7F, 1.85F);
		this.setStateMinor(ID.M.ShipType, ID.ShipType.HIME);
		this.setStateMinor(ID.M.ShipClass, ID.ShipClass.SubmarineHime);
		this.setStateMinor(ID.M.DamageType, ID.ShipDmgType.SUBMARINE);
		this.setStateMinor(ID.M.NumState, 3);
		this.setGrudgeConsumption(ConfigHandler.consumeGrudgeShip[ID.ShipConsume.SS]);
		this.setAmmoConsumption(ConfigHandler.consumeAmmoShip[ID.ShipConsume.SS]);
		this.ModelPos = new float[] {0F, 25F, 0F, 45F};
		
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
  			//every 128 ticks
  			if (this.ticksExisted % 128 == 0 && !this.isMorph)
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
  			}//end 128 ticks
  		}
  	}
  	
  	@Override
	public double getMountedYOffset()
  	{
  		if (this.isSitting())
  		{
			if (getStateEmotion(ID.S.Emotion) == ID.Emotion.BORED)
			{
				return this.height * 0.25F;
  			}
  			else
  			{
  				return 0F;
  			}
  		}
  		else
  		{
  			return this.height * 0.69F;
  		}
	}

	@Override
	public float getInvisibleLevel()
	{
		return 0.3F;
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
			return this.shipAttrs.getAttackDamage() * 0.125F;
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
	
  	//true if use mounts
  	@Override
  	public boolean hasShipMounts()
  	{
  		return true;
  	}
  	
  	@Override
  	public BasicEntityMount summonMountEntity()
  	{
		return new EntityMountSuH(this.world);
	}
  	

}