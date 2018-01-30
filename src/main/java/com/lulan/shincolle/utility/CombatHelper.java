package com.lulan.shincolle.utility;

import java.util.Random;

import com.lulan.shincolle.entity.BasicEntityMount;
import com.lulan.shincolle.entity.BasicEntitySummon;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.IShipAttrs;
import com.lulan.shincolle.entity.IShipInvisible;
import com.lulan.shincolle.entity.IShipMorph;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.network.S2CSpawnParticle;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Attrs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * helper for combat calculation
 *
 */
public class CombatHelper
{
	
	public static Random rand = new Random();
	
	
	public CombatHelper() {}
	
	/**
	 * apply DEF
	 */
	public static float applyDamageReduceByDEF(Random rand, Attrs attrs, float rawAtk)
	{
		return rawAtk * (1F - attrs.getDefense() + (rand.nextFloat() * 0.5F - 0.25F));    
	}
	
	/**
	 * damage reduction on player
	 */
	public static float applyDamageReduceOnPlayer(Entity target, float rawAtk)
	{
  		//damage limit on player target
  		if (target instanceof EntityPlayer)
  		{
  			rawAtk *= 0.25F;
  			if (rawAtk > 59F) rawAtk = 59F;	//same with TNT
  		}
  		
  		return rawAtk;
	}
	
	/**
	 * roll miss/cri/double hit/triple hit, return new damage
	 * 
	 * miss rate = base rate + distance - level - equip + buff
	 * base rate = 0.25
	 * distance = 0.25 * (target dist / max hit range)
	 * level = 0.001 * ship level
	 * equip = total miss reduction on equips
	 * buff = nausea +0.5/level 
	 * 
	 * parms: host, target, can multiple hit, distance, raw damage
	 * return: new damage
	 */
	public static float applyCombatRateToDamage(IShipAttackBase host, Entity target, boolean canMultiHit, float distance, float rawAtk)
	{
		if (host != null)
		{
			//if host is a minion, get host's host
			if ((host instanceof BasicEntitySummon || host instanceof BasicEntityMount) &&
				host.getHostEntity() instanceof IShipAttackBase)
			{
				host = (IShipAttackBase) host.getHostEntity();
			}
			
			/**
			 * priority: miss > cri > dhit > thit
			 */
	        float miss = calcMissRate(host, distance);
	        float cri = host.getAttrs().getAttrsBuffed(ID.Attrs.CRI);
	        float dhit = host.getAttrs().getAttrsBuffed(ID.Attrs.DHIT);
	        float thit = host.getAttrs().getAttrsBuffed(ID.Attrs.THIT);

			//add cri rate
			cri += miss;
			if (cri < miss) cri = miss;
			
			//add double hit rate
			dhit += cri;
			if (dhit < cri) dhit = cri;
			
			//add triple hit rate
			thit += dhit;
			if (thit < dhit) thit = dhit;
			
			//roll
			float roll = host.getRand().nextFloat();
			
			//if miss hit
			if (roll <= miss)
			{
				if (host instanceof Entity) ParticleHelper.spawnAttackTextParticle((Entity) host, 0);
				return 0F;
			}
			//if cri hit
			else if (roll <= cri)
			{
				if (host instanceof Entity) ParticleHelper.spawnAttackTextParticle((Entity) host, 1);
				return rawAtk * 1.5F;
			}
			//if double hit
			else if (canMultiHit && roll <= dhit)
			{
				if (host instanceof Entity) ParticleHelper.spawnAttackTextParticle((Entity) host, 2);
				return rawAtk * 2F;
			}
			//if triple hit
			else if (canMultiHit && roll <= dhit)
			{
				if (host instanceof Entity) ParticleHelper.spawnAttackTextParticle((Entity) host, 3);
				return rawAtk * 3F;
			}
			else {}
		}
		
		//if normal hit
		return rawAtk;
	}
	
	/**
	 * calc miss rate
	 */
	public static float calcMissRate(IShipAttackBase host, float distance)
	{
		float miss = 0F;
		
		//distance < 3 blocks
		if (host.getAttrs().getAttackRange() <= 3F)
		{
			miss = 0.25F - 0.001F * (float)host.getLevel();
		}
		//distance < 6 blocks
		else if (host.getAttrs().getAttackRange() <= 6F)
		{
			miss = 0.25F + 0.15F * (distance / host.getAttrs().getAttackRange()) - 0.001F * (float)host.getLevel();
		}
		else
		{
			miss = 0.25F + 0.25F * (distance / host.getAttrs().getAttackRange()) - 0.001F * (float)host.getLevel();
		}
		
		//miss reduction on equips
		miss -= host.getAttrs().getAttrsBuffed(ID.Attrs.MISS);
		
		//miss min and max limit
        if (miss > 0.5F) miss = 0.5F;	//raw miss chance limit = 50%
        else if (miss < 0F) miss = 0F;
        
        //apply nausea potion effect (after limit)
		if (BuffHelper.getPotionLevel(host.getBuffMap(), 9) > 0) miss += 0.4F;
		
		return miss;
	}
	
	/** roll dodge, parms: distSQ = distance^2 */
	public static boolean canDodge(IShipAttrs host, float distSq)
	{
		if (host != null && !((Entity)host).world.isRemote)
		{
			float dodge = host.getAttrs().getAttrsBuffed(ID.Attrs.DODGE);
			
			//if host is submarine
			if (host instanceof IShipInvisible)
			{
				if (distSq > 36F)
				{
					//dist > 6 blocks
					dodge += ((IShipInvisible) host).getInvisibleLevel();
					//check limit
					if (dodge > ConfigHandler.limitShipAttrs[ID.Attrs.DODGE]) dodge = (float) ConfigHandler.limitShipAttrs[ID.Attrs.DODGE];
				}
				
				//submarine will increase dodge if dist > 16 blocks, without config limit
				if (distSq > 256F)
				{
					dodge += 0.5F;
				}
			}
			
			Entity ent2 = (Entity) host;
			
			//roll dodge
			if (rand.nextFloat() <= dodge)
			{
				//check host is morph
				if (ent2 instanceof IShipMorph && ((IShipMorph)ent2).getMorphHost() != null) ent2 = ((IShipMorph)ent2).getMorphHost();
				//spawn dodge particle
				ParticleHelper.spawnAttackTextParticle(ent2, 4);
				return true;
			}
		}
		
		return false;
	}
	
	/** tweak damage by type and light
	 *  dmg: damage value
	 *  typeAtk: attacker type id
	 *  typeDef: defender type id
	 *  lightCoef: 0=night, 1=day, 0.X=night vision potion
	 */
    public static float modDamageByLight(float dmg, int typeAtk, int typeDef, float lightCoef)
    {
    	//if type = undefined, return org damage
    	if (typeAtk <= 0 || typeDef <= 0) return dmg;
    	
    	//limit value
    	if (lightCoef < 0F) lightCoef = 0F;
    	else if (lightCoef > 1F) lightCoef = 1F;
    	
    	//calc damage modifier
    	float modDay = Values.ModDmgNight[typeAtk-1][typeDef-1];
    	float modNight = Values.ModDmgDay[typeAtk-1][typeDef-1];
    	float mod = modNight + (modDay - modNight) * lightCoef;
    	
    	return dmg * mod;
    }
    
    /** tweak damage by attrs: AA, ASM
     *  host: attacker
     *  target: target
     *  dmg: attack damage
     *  type: 0:light 1:heavy 2:nagato 3:yamato
     *  
     *  TODO damage method changed
     */
    public static float modDamageByAdditionAttrs(IShipAttrs host, Entity target, float dmg, int type)
    {
    	float newDmg = dmg;
    	float modEffect = 1F;
  		
  		//normal or special attack
  		switch(type)
  		{
  		case 2:   //nagato heavy attack
  			modEffect = 4F;
  			break;
  		case 3:   //yamato heavy attack
  			modEffect = 1.5F;
  			break;
		default:  //normal attack
			modEffect = 1F;
			break;
  		}
  		
  		//check target type
  		int targettype = EntityHelper.checkEntityMovingType(target);
  		
  		if (targettype == 1)  //air mob
  		{
  			newDmg = (newDmg + host.getAttrs().getAttrsBuffed(ID.Attrs.AA)) * modEffect;
  		}
  		else if (targettype == 2)  //water mob
  		{
  			newDmg = (newDmg + host.getAttrs().getAttrsBuffed(ID.Attrs.ASM)) * modEffect;
  		}
  		else
  		{
  			newDmg *= modEffect;
  		}
  		
  		return newDmg;
    }
    
    /**
     * calc attack delay
     * 
     * type:
     *   0: melee
     *   1: light
     *   2: heavy
     *   3: air light
     *   4: air heavy
     */
    public static int getAttackDelay(float aspd, int type)
    {
    	if (aspd < 0.01F) aspd = 0.01F;
    	
    	switch (type)
    	{
    	case 0:
    		return (int)(ConfigHandler.baseAttackSpeed[0] / aspd) + ConfigHandler.fixedAttackDelay[0];
    	case 1:
    		return (int)(ConfigHandler.baseAttackSpeed[1] / aspd) + ConfigHandler.fixedAttackDelay[1];
    	case 2:
    		return (int)(ConfigHandler.baseAttackSpeed[2] / aspd) + ConfigHandler.fixedAttackDelay[2];
    	case 3:
    		return (int)(ConfigHandler.baseAttackSpeed[3] / aspd) + ConfigHandler.fixedAttackDelay[3];
    	case 4:
    		return (int)(ConfigHandler.baseAttackSpeed[4] / aspd) + ConfigHandler.fixedAttackDelay[4];
    	}
    	
    	return 40;
    }
    
    /**
     * get missile move type
     * type: 0:melee, 1:light, 2:heavy, 3:air-light, 4:air-heavy
     */
    public static int calcMissileMoveType(IShipAttackBase host, double tarY, int type)
    {
		int moveType = host.getMissileData(type).movetype;
		
		//moveType = -1, check depth
		if (moveType < 0)
		{
			double depth = host.getShipDepth(0);
			
			//in water
			if (depth > 2D)
	        {
				moveType = 0;
	        }
			//on water
			else if (depth > 0D)
	        {
				//target is lower
				if (tarY <= ((Entity) host).posY || tarY - ((Entity) host).posY < depth)
				{
					moveType = 2;
				}
				//target is higher
				else
				{
					moveType = 1;
				}
	        }
			//on solid block
			else
			{
				moveType = 1;
			}
		}
		
		return moveType;
    }
    
    /** get missile move type for airplane */
    public static int calcMissileMoveTypeForAirplane(IShipAttackBase host, Entity target, int type)
    {
		int moveType = host.getMissileData(type).movetype;
		
		if (moveType < 0)
		{
			boolean targetliq = EntityHelper.checkEntityIsInLiquid(target);
			boolean hostliq = EntityHelper.checkEntityIsInLiquid((Entity)host);
			boolean hostunderliq = BlockHelper.checkBlockIsLiquid(target.world.getBlockState(new BlockPos(((Entity)host).posX, target.posY, ((Entity)host).posZ)));
			
			//host in water
			if (hostliq)
	        {
				moveType = 0;
	        }
			//on water
			else if (targetliq && hostunderliq)
	        {
				//target is lower
				if (target.posY <= ((Entity) host).posY)
				{
					moveType = 2;
				}
				//target is higher
				else
				{
					moveType = 0;
				}
	        }
			//on solid block
			else
			{
				moveType = 0;
			}
		}
		
		return moveType;
    }
	
	
}