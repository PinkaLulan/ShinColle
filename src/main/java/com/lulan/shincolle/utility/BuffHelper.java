package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.BasicEntityShipHostile;
import com.lulan.shincolle.entity.IShipAttackBase;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.reference.unitclass.AttrsAdv;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;

/**
 * BUFF HELPER
 * 
 * potion setting:
   1  speed:          move spd
   2  slow:           move spd, kb
   3  haste:          attack spd
   4  mining fatigue: attack spd
   5  strength:       dmg, kb
   6  instant heal:   heal (2% + 2)/lv
   7  instant dmg:    hurt (2% + 2)/lv
   8  jump:           range++
   9  nausea:         miss -0.4/lv
   10 regen:          heal (2% + 2)/lv every x ticks
   11 resis:          reduce missile dmg 20%/lv
   12 fire resis:     reduce non-missile dmg 20%/lv
   13 water breath:   dodge, ASM
   14 invis:          no special effect (clear invis target every 64 ticks)
   15 blind:          range--
   16 night vision:   night battle effect -80%
   17 hunger:         grudge consumption +100%/lv (multiplier)
   18 weak:           dmg, kb
   19 poison:         def, kb
   20 wither:         hurt (2% + 2)/lv until dead
   21 heal boost:     max hp
   22 absorption:     def
   23 saturation:     grudge and morale +N/lv every x ticks
   24 glowing:        no effect
   25 levitation:     dodge, AA, kb
   26 luck:           cri/dhit/thit++
   27 bad luck:       cri/dhit/thit--
 *
 */
public class BuffHelper
{
	
	
	public BuffHelper() {}
	
	/* calc attributes by ship class and level (for ship pet) */
	public static void updateAttrsRaw(Attrs attrs, int shipClass, float shipLevel)
	{
		//null check
		if (attrs == null) return;
		if (!Values.ShipAttrMap.containsKey(shipClass)) shipClass = 0;
		
		//get attrs value
		float[] attrBase = Arrays.copyOf(Values.ShipAttrMap.get(shipClass), Attrs.AttrsLength);  //base
		float[] attrType = attrs.getAttrsType();               //type
		byte[] attrBonus = attrs.getAttrsBonus();              //bonus
		
		//reset attrs raw
		attrs.resetAttrsRaw();
		float[] attrRaw = attrs.getAttrsRaw();                 //raw
		
		//HP = (base + equip + (bonus + 1) * level * type) * config scale
		attrRaw[ID.Attrs.HP] = (attrBase[ID.AttrsBase.HP] + (attrBonus[ID.AttrsBase.HP] + 1F) * shipLevel * attrType[ID.AttrsBase.HP]) * (float) ConfigHandler.scaleShip[ID.AttrsBase.HP]; 
		//DEF = (base + equip + (bonus + 1) * level * 0.133 * type) * config scale
		attrRaw[ID.Attrs.DEF] = (attrBase[ID.AttrsBase.DEF] + (attrBonus[ID.AttrsBase.DEF] + 1F) * shipLevel * 0.00133F * attrType[ID.AttrsBase.DEF]) * (float) ConfigHandler.scaleShip[ID.AttrsBase.DEF];
		//SPD = (base + equip + (bonus + 1) * level * 0.004 * type) * config scale
		attrRaw[ID.Attrs.SPD] = (attrBase[ID.AttrsBase.SPD] + (attrBonus[ID.AttrsBase.SPD] + 1F) * shipLevel * 0.004F * attrType[ID.AttrsBase.SPD]) * (float) ConfigHandler.scaleShip[ID.AttrsBase.SPD];
		//MOV = (base + equip + (bonus + 1) * level * 0.002 * type) * config scale
		attrRaw[ID.Attrs.MOV] = (attrBase[ID.AttrsBase.MOV] + (attrBonus[ID.AttrsBase.MOV] + 1F) * shipLevel * 0.002F * attrType[ID.AttrsBase.MOV]) * (float) ConfigHandler.scaleShip[ID.AttrsBase.MOV];
		//HIT = (base + equip + (bonus + 1) * level * 0.02 * type) * config scale
		attrRaw[ID.Attrs.HIT] = (attrBase[ID.AttrsBase.HIT] + (attrBonus[ID.AttrsBase.HIT] + 1F) * shipLevel * 0.02F * attrType[ID.AttrsBase.HIT]) * (float) ConfigHandler.scaleShip[ID.AttrsBase.HIT];
		
		//baseATK = base + (bonus + 1) * level * 0.133 * type
		float baseATK = attrBase[ID.AttrsBase.ATK] + (attrBonus[ID.AttrsBase.ATK] + 1F) * shipLevel * 0.133F * attrType[ID.AttrsBase.ATK];
		//ATK = (baseATK * mod + equip) * config scale
		attrRaw[ID.Attrs.ATK_L] = baseATK * (float) ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		attrRaw[ID.Attrs.ATK_H] = baseATK * 3F * (float) ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		attrRaw[ID.Attrs.ATK_AL] = baseATK * (float) ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		attrRaw[ID.Attrs.ATK_AH] = baseATK * 3F * (float) ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		
		//misc attrs
		attrRaw[ID.Attrs.XP] = 1F;
		attrRaw[ID.Attrs.GRUDGE] = 1F;
		attrRaw[ID.Attrs.AMMO] = 1F;
		attrRaw[ID.Attrs.HPRES] = 1F;
		
		//knockback Resistance
		attrRaw[ID.Attrs.KB] = shipLevel * 0.005F;
	}
	
	/* calc attributes by ship scale and class (for ship mob) */
	public static void updateAttrsRawHostile(Attrs attrs, int shipScale, int shipClass)
	{
		float[] attrmod = Values.HostileShipAttrMap.get(shipClass);
		double[] attrbase;
		float kb = 0.2F;
		
		//reset raw
		attrs.resetAttrsRaw();
		float[] attrsraw = attrs.getAttrsRaw();
		
		switch (shipScale)
		{
		case 1:
			attrbase = ConfigHandler.scaleMobLarge;
			kb = 0.4F;
		break;
		case 2:
			attrbase = ConfigHandler.scaleBossSmall;
			kb = 0.85F;
		break;
		case 3:
			attrbase = ConfigHandler.scaleBossLarge;
			kb = 1F;
		break;
		default:
			attrbase = ConfigHandler.scaleMobSmall;
		break;
		}
		
		//set attrs
		attrsraw[ID.Attrs.HP] = (float)attrbase[ID.AttrsBase.HP] * attrmod[ID.AttrsBase.HP];
		attrsraw[ID.Attrs.ATK_L] = (float)attrbase[ID.AttrsBase.ATK] * attrmod[ID.AttrsBase.ATK];
		attrsraw[ID.Attrs.ATK_H] = (float)attrbase[ID.AttrsBase.ATK] * attrmod[ID.AttrsBase.ATK] * 3F;
		attrsraw[ID.Attrs.ATK_AL] = (float)attrbase[ID.AttrsBase.ATK] * attrmod[ID.AttrsBase.ATK];
		attrsraw[ID.Attrs.ATK_AH] = (float)attrbase[ID.AttrsBase.ATK] * attrmod[ID.AttrsBase.ATK] * 3F;
		attrsraw[ID.Attrs.DEF] = (float)attrbase[ID.AttrsBase.DEF] * attrmod[ID.AttrsBase.DEF];
		attrsraw[ID.Attrs.SPD] = (float)attrbase[ID.AttrsBase.SPD] * attrmod[ID.AttrsBase.SPD];
		attrsraw[ID.Attrs.MOV] = (float)attrbase[ID.AttrsBase.MOV] * attrmod[ID.AttrsBase.MOV];
		attrsraw[ID.Attrs.HIT] = (float)attrbase[ID.AttrsBase.HIT] * attrmod[ID.AttrsBase.HIT];
		attrsraw[ID.Attrs.CRI] = 0.15F;
		attrsraw[ID.Attrs.DHIT] = 0.1F;
		attrsraw[ID.Attrs.THIT] = 0.1F;
		attrsraw[ID.Attrs.MISS] = 0F;
		attrsraw[ID.Attrs.AA] = 0F;
		attrsraw[ID.Attrs.ASM] = 0F;
		attrsraw[ID.Attrs.DODGE] = 0.15F;
		attrsraw[ID.Attrs.XP] = 1F;
		attrsraw[ID.Attrs.GRUDGE] = 1F;
		attrsraw[ID.Attrs.AMMO] = 1F;
		attrsraw[ID.Attrs.HPRES] = 1F;
		attrsraw[ID.Attrs.KB] = kb;
	}
	
	/**
	 * update potion effect on ship
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> void updateBuffPotion(T host)
	{
		//add potion to buff map
		convertPotionToBuffMap(host);
		
		//add buff to attributes
		convertBuffMapToAttrs(host.getBuffMap(), host.getAttrs());
	}
	
	/**
	 * convert potion effect to buff map
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> void convertPotionToBuffMap(T host)
	{
		//null check
		if (host == null) return;
		
		HashMap<Integer, Integer> buffmap = new HashMap<Integer, Integer>();
		
		//potion effects to ship buffs
    	for (PotionEffect potioneffect : host.getActivePotionEffects())
        {
    		buffmap.put(Potion.getIdFromPotion(potioneffect.getPotion()), potioneffect.getAmplifier());
        }
    	
    	host.setBuffMap(buffmap);
	}
	
	/**
	 * convert all buff to attributes
	 */
	public static void convertBuffMapToAttrs(Map<Integer, Integer> buffmap, Attrs attrs)
	{
		//null check
		if (buffmap == null || attrs == null) return;
		
		//reset potion attrs
		attrs.resetAttrsPotion();
		float[] potion = attrs.getAttrsPotion();
		
		//check all buffs
		buffmap.forEach((id, amp) ->
		{
			//limit buff level to 1~5
			int lv = amp > 4 ? 5 : amp < 0 ? 1 : amp + 1;
			
			switch(id)
			{
			case 1:  //speed: mov +0.08
				potion[ID.Attrs.MOV] += 0.08F * lv;
			break;
			case 2:  //slow: mov -0.15, kb +0.15
				potion[ID.Attrs.MOV] += -0.15F * lv;
				potion[ID.Attrs.KB] += 0.15F * lv;
			break;
			case 3:  //haste: aspd +0.8
				potion[ID.Attrs.SPD] += 0.8F * lv;
			break;
			case 4:  //mining fatigue: aspd -1
				potion[ID.Attrs.SPD] += -1F * lv;
			break;
			case 5:  //strength: base dmg +15, kb +0.1
				potion[ID.Attrs.ATK_L] += 15F * lv;
				potion[ID.Attrs.ATK_H] += 15F * lv;
				potion[ID.Attrs.ATK_AL] += 15F * lv;
				potion[ID.Attrs.ATK_AH] += 15F * lv;
				potion[ID.Attrs.KB] += 0.15F * lv;
			break;
			case 8:  //jump: range +2
				potion[ID.Attrs.HIT] += 2F * lv;
			break;
			case 13: //water breath: dodge +0.15,ASM +60
				potion[ID.Attrs.DODGE] += 0.15F * lv;
				potion[ID.Attrs.ASM] += 20F * lv;
			break;
			case 15: //blind: range -24 (fixed level 1)
				potion[ID.Attrs.HIT] += -24F;
			break;
			case 18: //weak: dmg -20, kb -0.15
				potion[ID.Attrs.ATK_L] += -15F * lv;
				potion[ID.Attrs.ATK_H] += -15F * lv;
				potion[ID.Attrs.ATK_AL] += -15F * lv;
				potion[ID.Attrs.ATK_AH] += -15F * lv;
				potion[ID.Attrs.KB] += -0.15F * lv;
			break;
			case 19: //poison: def -0.25, kb -0.1
				potion[ID.Attrs.DEF] += -0.25F * lv;
				potion[ID.Attrs.KB] += -0.1F * lv;
			break;
			case 21: //heal boost: hp +200
				potion[ID.Attrs.HP] += 200F * lv;
			break;
			case 22: //absorb: hp +100, def +20%
				potion[ID.Attrs.HP] += 100F * lv;
				potion[ID.Attrs.DEF] += 0.2F * lv;
			break;
			case 25: //levitation: dodge +0.15, AA +60, kb -0.2
				potion[ID.Attrs.DODGE] += 0.15F * lv;
				potion[ID.Attrs.AA] += 20F * lv;
				potion[ID.Attrs.KB] += -0.2F * lv;
			break;
			case 26: //luck: cri/dhit/thit +0.2
				potion[ID.Attrs.CRI] += 0.2F * lv;
				potion[ID.Attrs.DHIT] += 0.2F * lv;
				potion[ID.Attrs.THIT] += 0.2F * lv;
			break;
			case 27: //bad luck: cri/dhit/thit -0.3
				potion[ID.Attrs.CRI] += -0.3F * lv;
				potion[ID.Attrs.DHIT] += -0.3F * lv;
				potion[ID.Attrs.THIT] += -0.3F * lv;
			break;
			}
		});  //end map foreach
	}

	/**
	 * apply ticking buff to ship: regen/wither...
	 * call this method every x ticks (default: 32)
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> void applyBuffOnTicks(T host)
	{
		//get buff map
		HashMap<Integer, Integer> buffmap = host.getBuffMap();
		
		//get host's 1% hp
		float hp1p = host.getMaxHealth() * 0.01F;
		if (hp1p < 1F) hp1p = 1F;
		
		//check all buffs
		/**
		 * NOTE: eclipse's content assist is bug in lambda + switch now
		 *       (2017/7/13, heavily lag, cpu get to 100%)
		 */
		for (Map.Entry<Integer, Integer> entry : buffmap.entrySet())
		{
			int id = entry.getKey();
			int amp = entry.getValue();
			//limit buff level to 1~5
			int lv = amp > 4 ? 5 : amp < 0 ? 1 : amp + 1;
			
			switch(id)
			{
			case 10: //regen: heal 2% + 2 every ticking
				host.heal((hp1p * 2F + 2F) * lv);
			break;
			case 20: //wither: hurt 2% + 2 until dead
				host.attackEntityFrom(DamageSource.magic, (hp1p * 2F + 2F) * lv);
			break;
			case 23: //saturation: grudge and morale +N/lv, friendly ship only
				if (host instanceof BasicEntityShip)
				{
					BasicEntityShip ship = (BasicEntityShip) host;
					ship.addGrudge((int)((float)(ConfigHandler.buffSaturation * lv) * host.getAttrs().getAttrsBuffed(ID.Attrs.GRUDGE)));
					ship.addMorale(20);
				}
			break;
			}
		}
	}
	
	/** get specific potion level from IShipAttackBase's buff map, return 0 if no such buff */
	public static int getPotionLevel(Entity host, int pid)
	{
		if (host instanceof IShipAttackBase)
		{
			return getPotionLevel(((IShipAttackBase) host).getBuffMap(), pid);
		}
		
		return 0;
	}
	
	/** get specific potion level from buff map, return 0 if no such buff */
	public static int getPotionLevel(Map<Integer, Integer> buffmap, int pid)
	{
		if (buffmap != null)
		{
			//pid必須轉成byte!! key用int的話抓不到key為byte的map的值!!
			return buffmap.containsKey(pid) ? buffmap.get(pid) + 1 : 0;
		}
		
		return 0;
	}
	
	/** get specific potion level from stack, return 0 if no such buff */
	public static int getPotionLevel(ItemStack stack, int pid)
	{
		if (stack != null)
		{
			List<PotionEffect> plist = PotionUtils.getEffectsFromStack(stack);
			
			return getPotionLevel(plist, pid);
		}
		
		return 0;
	}
	
	/** get specific potion level from potion effect list, return 0 if no such buff */
	public static int getPotionLevel(List<PotionEffect> list, int pid)
	{
		if (list != null && !list.isEmpty())
		{
			for (PotionEffect effect : list)
			{
				if (Potion.getIdFromPotion(effect.getPotion()) == pid)
				{
					return effect.getAmplifier() + 1;
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * calc potion damage, used in entity.attackEntityFrom
	 * 
	 * only 1 type of damage potion for now:
	 * Instant Damage Potion (7)
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> float getPotionDamage(T host, DamageSource source, float atk)
	{
		//check attacker is EntityPotion or EntityAreaEffectCloud
		if (host != null && source != null)
		{
			int level = 1;
			
			//get potion level from potion entity
			if (source.getSourceOfDamage() instanceof EntityPotion)
			{
				ItemStack pot = ((EntityPotion)source.getSourceOfDamage()).getPotion();
				
				if (pot != null)
				{
					level = getPotionLevel(PotionUtils.getEffectsFromStack(pot), 7);
				}
			}
			//from cloud entity
			else if (source.getSourceOfDamage() instanceof EntityAreaEffectCloud)
			{
				level = getPotionLevel(PotionUtils.getEffectsFromTag(
						source.getSourceOfDamage().writeToNBT(new NBTTagCompound())), 7);
			}
			//not potion
			else
			{
				return 0F;
			}
			
			//return damage = (2% + 2) * lv + potion raw atk
			float hp1p = host.getMaxHealth() * 0.01F;
			if (hp1p < 1F) hp1p = 1F;
			
			return (hp1p * 2F + 2F) * level + atk;
		}
		
		return 0F;
	}
	
	/**
	 * apply resist potion effect to damage source
	 * 
	 * IN: host entity, source, damage value
	 * OUT: new damage value
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> float applyBuffOnDamageByResist(T host, DamageSource source, float atk)
	{
		//null check
		if (host == null || source == null) return atk;
		
		HashMap<Integer, Integer> buffmap = host.getBuffMap();
		int level = 0;
		
		/** potion buff:
		 *  11: resist, reduce missile
		 *  12: fire resist, reduce non-missile
		 */
		if (source.getSourceOfDamage() instanceof EntityAbyssMissile)
		{
			if ((level = getPotionLevel(buffmap, 11)) > 0)
			{
				//limit buff level to 1~4
				if (level > 4) level = 4;
				
				atk = atk * (1f - level * 0.2f);
			}
		}
		else if (source.getSourceOfDamage() instanceof IShipAttackBase)
		{
			if ((level = getPotionLevel(buffmap, 12)) > 0)
			{
				//limit buff level to 1~4
				if (level > 4) level = 4;
				
				atk = atk * (1f - level * 0.2f);
			}
		}

		return atk;
	}
	
	/**
	 * check light value and apply night vision potion effect to damage source
	 * target: check light value, attacker: check potion buff
	 * 
	 * IN: host entity, source, damage value
	 * OUT: new damage value
	 */
	public static <T extends EntityLivingBase & IShipAttackBase> float applyBuffOnDamageByLight(T host, DamageSource source, float atk)
	{
		//null check
		if (host == null || source == null) return atk;
		
		//night vision potion work in ship vs ship only
		if (source.getEntity() instanceof IShipAttackBase)
		{
			/**
			 * light coefficient for CalcHelper.calcDamageByType:
			 * 0 = night (light <= 2)
			 * 1 = day (light >= 8)
			 * 0.X = night vision potion (if light = 3~7)
			 */
			BlockPos pos = new BlockPos(host);
			float lightCoeff = ((float)host.world.getLight(pos, true) - 2F) / 6F;
			
			if (lightCoeff < 0F) lightCoeff = 0F;
			else if (lightCoeff > 1F) lightCoeff = 1F;
			
			//check night vision potion level
			float level = getPotionLevel(((IShipAttackBase)source.getEntity()).getBuffMap(), 16);
			
			//apply night vision potion to coef
			if (level > 0) lightCoeff += 0.8F;
			
			atk = CombatHelper.modDamageByLight(atk,
					((IShipAttackBase) source.getEntity()).getDamageType(),
					host.getDamageType(), lightCoeff);
		}
		
		return atk;
	}
	
	/**
	 * apply potion heal effect
	 * (for splash and cloud potion only)
	 * 
	 * get potion data from EntityAreaEffectCloud:
	 *   1. get entities within aabb
	 *   2. get entity nbt data
	 *   3. get potion data by PotionUtils
	 *   
	 * get potion data from   
	 * 
	 * TODO tweak this method if potion heal event is added
	 */
	public static float applyBuffOnHeal(EntityLivingBase host, float heal)
	{
		//null check
		if (host == null) return heal;
		
		List<PotionEffect> pts;
		int type, level;
		float hp1p = host.getMaxHealth() * 0.01F;
		if (hp1p < 1F) hp1p = 1F;
		
		//check cloud entities
		List<EntityAreaEffectCloud> getlist = host.world.getEntitiesWithinAABB(
				EntityAreaEffectCloud.class, host.getEntityBoundingBox().expand(3D, 3D, 3D));
		
		if (!getlist.isEmpty())
		{
			NBTTagCompound nbt;
			
			//loop all cloud entities
			for (EntityAreaEffectCloud aecloud : getlist)
			{
				//get nbt data
				nbt = new NBTTagCompound();
				aecloud.writeToNBT(nbt);
				
				//get potion data
				pts = PotionUtils.getEffectsFromTag(nbt);
				level = checkPotionHeal(pts);
				
				//return heal value by potion level
				if (level > 0)
				{
					return heal + (hp1p * 2F + 2F) * level;
				}
			}
		}
		
		//check potion entities
		List<EntityPotion> getlist2 = host.world.getEntitiesWithinAABB(
				EntityPotion.class, host.getEntityBoundingBox().expand(3D, 3D, 3D));
		
		if (!getlist2.isEmpty())
		{
			//loop all potion entities
			for (EntityPotion potion : getlist2)
			{
				ItemStack pstack = potion.getPotion();
				
				if (pstack != null)
				{
					pts = PotionUtils.getEffectsFromStack(pstack);
					level = checkPotionHeal(pts);
					
					//return heal value by potion level
					if (level > 0)
					{
						return heal + (hp1p * 2F + 2F) * level;
					}
				}
			}
		}
		
		return heal;
	}
	
	/**
	 * check heal effect is in list, retrun heal level (first found)
	 * 
	 * heal id:
	 *   6: instant heal
	 *   10: regen
	 * 
	 * return:
	 *   0: NOT heal potion (loop until end)
	 *   1~N: heal potion level
	 */
	public static int checkPotionHeal(List<PotionEffect> list)
	{
		if (list != null)
		{
			int id = -1;
			
			for (PotionEffect pe : list)
			{
				id = Potion.getIdFromPotion(pe.getPotion());
				
				if (id == 6 || id == 10)
				{
					return pe.getAmplifier() + 1;
				}
			}
		}
		
		return 0;
	}
	
	/**
	 * check damage effect is in list, retrun damage level (first found)
	 * 
	 * damage id:
	 *   7:  instant damage
	 *   19: poison
	 *   20: wither
	 * 
	 * return:
	 *   0: NOT damage potion (loop until end)
	 *   1~N: damage potion level
	 */
	public static int checkPotionDamage(List<PotionEffect> list)
	{
		if (list != null)
		{
			int id = -1;
			
			for (PotionEffect pe : list)
			{
				id = Potion.getIdFromPotion(pe.getPotion());
				
				if (id == 7 || id == 19 || id == 20)
				{
					return pe.getAmplifier() + 1;
				}
			}
		}
		
		return 0;
	}
	
  	/* apply morale buffs */
  	public static void updateBuffMorale(AttrsAdv attrs, int moraleValue)
  	{
  		if (attrs == null) return;
  		
  		//reset morale buff
  		attrs.resetAttrsMorale();
  		int id = -1;
  		
  		/* excited id:0 */
  		if (moraleValue > ID.Morale.L_Excited) id = 0;
  		/* happy id:1 */
  		else if (moraleValue > ID.Morale.L_Happy) id = 1;
		/* exhausted id:3 */
  		else if (moraleValue <= ID.Morale.L_Tired) id = 3;
  		/* tired id:2 */
  		else if (moraleValue <= ID.Morale.L_Normal) id = 2;
  		
  		//copy buff value to attrs
  		float[] attrsMorale = Values.MoraleAttrs.get(id);
  		
  		if (attrsMorale != null)
  		{
  			attrs.setAttrsMorale(Arrays.copyOf(attrsMorale, Attrs.AttrsLength));
  		}
  	}
  	
  	//update formation buffs, SERVER SIDE ONLY
  	public static void updateBuffFormation(BasicEntityShip host)
  	{
  		if (host == null) return;
  		
  		//check update flag
  		if (!host.world.isRemote)
  		{
  			CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(host.getPlayerUID());
  			
  			if(capa != null)
  			{
  				//check ship is in formation
  				int[] teamslot = capa.checkIsInFormation(host.getShipUID());
  				AttrsAdv attrs = (AttrsAdv) host.getAttrs();
  				
  				//apply formation buff to ship
  				if (teamslot[0] >= 0 && teamslot[1] >= 0 && capa.getFormatID(teamslot[0]) > 0)
  				{
  					//set formation type and pos
  					host.setStateMinor(ID.M.FormatType, capa.getFormatID(teamslot[0]));
  					
  					//if diamond formation with 5 ships
  					if (host.getStateMinor(ID.M.FormatType) == 3 &&
  						capa.getNumberOfShip(teamslot[0]) == 5)
  					{
  						//diamond with 5 ship: formation position != team position
  						int slotID = capa.getFormationPos(teamslot[0], host.getShipUID());
  						
  						if (slotID >= 0)
  						{
  							host.setStateMinor(ID.M.FormatPos, slotID);
  						}
  					}
  					//other formation
  					else
  					{
  						host.setStateMinor(ID.M.FormatPos, teamslot[1]);
  					}
  					
  					//set buff value
  					attrs.setAttrsFormation(host.getStateMinor(ID.M.FormatType), host.getStateMinor(ID.M.FormatPos));
  				
  					//calc min moving speed in team
  					attrs.setMinMOV(capa.getMinMOVInTeam(teamslot[0]));
  				}
  				//not in team, clear buff
  				else
  				{
  					//reset formation type and pos
  					host.setStateMinor(ID.M.FormatType, 0);
  					host.setStateMinor(ID.M.FormatPos, -1);
  					
  					//reset formation buff value
  					attrs.resetAttrsFormation();
  				}
  			}

  			//update done, reset flag
  			host.setUpdateFlag(ID.FlagUpdate.FormationBuff, false);
  		}
  	}
  	
  	/**
  	 * calc buffed attrs
  	 */
  	public static float[] calcAttrsBuffed(float[] raw, float[] equip, float[] morale, float[] potion, float[] formation)
  	{
  		int id = ID.Attrs.HP;
  		float[] buffed = new float[raw.length];
  		
  		/* apply buff to raw attrs */
  		//HP, HIT, DODGE, XP, GRUDGE, AMMO, HPRES, KB
  		//raw + equip + morale + potion + formation
  		buffed[id] = raw[id] + equip[id] + (morale[id] + potion[id] + formation[id]) * (float)ConfigHandler.scaleShip[ID.AttrsBase.HP];
  		id = ID.Attrs.HIT;
  		buffed[id] = raw[id] + equip[id] + (morale[id] + potion[id] + formation[id]) * (float)ConfigHandler.scaleShip[ID.AttrsBase.HIT];
  		id = ID.Attrs.DODGE;
  		buffed[id] = raw[id] + equip[id] + morale[id] + potion[id] + formation[id];
  		id = ID.Attrs.XP;
  		buffed[id] = raw[id] + equip[id] + morale[id] + potion[id] + formation[id];
  		id = ID.Attrs.GRUDGE;
  		buffed[id] = raw[id] + equip[id] + morale[id] + potion[id] + formation[id];
  		id = ID.Attrs.AMMO;
  		buffed[id] = raw[id] + equip[id] + morale[id] + potion[id] + formation[id];
  		id = ID.Attrs.HPRES;
  		buffed[id] = raw[id] + equip[id] + morale[id] + potion[id] + formation[id];
  		id = ID.Attrs.KB;
  		buffed[id] = raw[id] + equip[id] + morale[id] + potion[id] + formation[id];
  		
  		//MOV
  		//raw + equip + morale + potion
  		id = ID.Attrs.MOV;
  		buffed[id] = raw[id] + equip[id] + (morale[id] + potion[id]) * (float)ConfigHandler.scaleShip[ID.AttrsBase.MOV];
  		
  		//ATK, SPD, CRI, DHIT, THIT, MISS, AA, ASM
  		//(raw + equip + potion) * morale * formation
  		id = ID.Attrs.ATK_L;
  		buffed[id] = (raw[id] + equip[id] + potion[id] * (float)ConfigHandler.scaleShip[ID.AttrsBase.ATK]) * morale[id] * formation[id];
  		id = ID.Attrs.ATK_H;
  		buffed[id] = (raw[id] + equip[id] + potion[id] * 3F * (float)ConfigHandler.scaleShip[ID.AttrsBase.ATK]) * morale[id] * formation[id];
  		id = ID.Attrs.ATK_AL;
  		buffed[id] = (raw[id] + equip[id] + potion[id] * (float)ConfigHandler.scaleShip[ID.AttrsBase.ATK]) * morale[id] * formation[id];
  		id = ID.Attrs.ATK_AH;
  		buffed[id] = (raw[id] + equip[id] + potion[id] * 3F * (float)ConfigHandler.scaleShip[ID.AttrsBase.ATK]) * morale[id] * formation[id];
  		id = ID.Attrs.SPD;
  		buffed[id] = (raw[id] + equip[id] + potion[id] * (float)ConfigHandler.scaleShip[ID.AttrsBase.SPD]) * morale[id] * formation[id];
  		id = ID.Attrs.CRI;
  		buffed[id] = (raw[id] + equip[id] + potion[id]) * morale[id] * formation[id];
  		id = ID.Attrs.DHIT;
  		buffed[id] = (raw[id] + equip[id] + potion[id]) * morale[id] * formation[id];
  		id = ID.Attrs.THIT;
  		buffed[id] = (raw[id] + equip[id] + potion[id]) * morale[id] * formation[id];
  		id = ID.Attrs.MISS;
  		buffed[id] = (raw[id] + equip[id] + potion[id]) * morale[id] * formation[id];
  		id = ID.Attrs.AA;
  		buffed[id] = (raw[id] + equip[id] + potion[id]) * morale[id] * formation[id];
  		id = ID.Attrs.ASM;
  		buffed[id] = (raw[id] + equip[id] + potion[id]) * morale[id] * formation[id];
  		
  		//DEF
  		//raw + equip + morale + potion) * formation
  		id = ID.Attrs.DEF;
  		buffed[id] = (raw[id] + equip[id] + (morale[id] + potion[id]) * (float)ConfigHandler.scaleShip[ID.AttrsBase.DEF]) * formation[id];

  		return buffed;
  	}
  	
  	/**
  	 * calc simulated attrs without some buff
  	 * 
  	 * type: 0: with all buffs
  	 *       1: without equip
  	 *       2: without morale
  	 *       3: without potion
  	 *       4: without formation
  	 *       
  	 * return buffed values
  	 */
  	public static float[] calcAttrsWithoutBuff(AttrsAdv attrs, int type)
  	{
  		float[] data = Attrs.getResetZeroValue();
  		
  		//null check
  		if (attrs != null)
  		{
  			switch (type)
  	  		{
  	  		case 0:  //with all buffs
  	  			data = calcAttrsBuffed(attrs.getAttrsRaw(), attrs.getAttrsEquip(), attrs.getAttrsMorale(), attrs.getAttrsPotion(), attrs.getAttrsFormation());
  	  		break;
  	  		case 1:  //without equip
  	  			data =  calcAttrsBuffed(attrs.getAttrsRaw(), Attrs.getResetZeroValue(), attrs.getAttrsMorale(), attrs.getAttrsPotion(), attrs.getAttrsFormation());
  	  		break;
  	  		case 2:  //without morale
  	  			data =  calcAttrsBuffed(attrs.getAttrsRaw(), attrs.getAttrsEquip(), AttrsAdv.getResetMoraleValue(), attrs.getAttrsPotion(), attrs.getAttrsFormation());
  	  		break;
  	  		case 3:  //without potion
  	  			data =  calcAttrsBuffed(attrs.getAttrsRaw(), attrs.getAttrsEquip(), attrs.getAttrsMorale(), Attrs.getResetZeroValue(), attrs.getAttrsFormation());
  	  		break;
  	  		case 4:  //without formation
  	  			data =  calcAttrsBuffed(attrs.getAttrsRaw(), attrs.getAttrsEquip(), attrs.getAttrsMorale(), attrs.getAttrsPotion(), AttrsAdv.getResetFormationValue());
  	  		break;
  	  		}
  		}
  		
  		//check limit
  		Attrs.checkAttrsLimit(data);
  		
  		return data;
  	}
  	
  	/**
  	 * apply all buffs to attrs
  	 */
  	public static void applyBuffOnAttrs(BasicEntityShip host)
  	{
  		//null check
  		if (host == null || host.getAttrs() == null) return;
  		
  		//get attrs
  		AttrsAdv attrs = (AttrsAdv) host.getAttrs();

  		//apply buff to raw attrs
  		attrs.setAttrsBuffed(calcAttrsBuffed(attrs.getAttrsRaw(),
  											 attrs.getAttrsEquip(),
  											 attrs.getAttrsMorale(),
  											 attrs.getAttrsPotion(),
  											 attrs.getAttrsFormation()));

  		//check attrs limit
  		attrs.checkAttrsLimit();
  	}
  	
  	/**
  	 * apply all buffs to attrs
  	 */
  	public static void applyBuffOnAttrsHostile(BasicEntityShipHostile host)
  	{
  		//null check
  		if (host == null || host.getAttrs() == null) return;
  		
  		//new buffed attrs
  		Attrs attrs = host.getAttrs();
  		attrs.resetAttrsBuffed();
  		float[] buffed = attrs.getAttrsBuffed();
  		float[] raw = attrs.getAttrsRaw();
  		float[] potion = attrs.getAttrsPotion();
  		
  		/* apply buff to raw attrs */
  		for (int i = 0; i < attrs.AttrsLength; i++)
  		{
  			buffed[i] = raw[i] + potion[i];
  			if (buffed[i] < 0F) buffed[i] = 0F;
  		}
  		
  		//check attrs limit
		if (buffed[ID.Attrs.HP] < 1F) buffed[ID.Attrs.HP] = 1F;
		if (buffed[ID.Attrs.ATK_L] < 1F) buffed[ID.Attrs.ATK_L] = 1F;
		if (buffed[ID.Attrs.ATK_H] < 1F) buffed[ID.Attrs.ATK_H] = 1F;
		if (buffed[ID.Attrs.ATK_AL] < 1F) buffed[ID.Attrs.ATK_AL] = 1F;
		if (buffed[ID.Attrs.ATK_AH] < 1F) buffed[ID.Attrs.ATK_AH] = 1F;
		if (buffed[ID.Attrs.HIT] < 1F) buffed[ID.Attrs.HIT] = 1F;
		if (buffed[ID.Attrs.SPD] < 0.2F) buffed[ID.Attrs.SPD] = 0.2F;
  	}
  	
  	/**
  	 * remove debuffs: id: 2, 4, 7, 9, 15, 17, 18, 19, 20, 27
  	 */
  	public static <T extends EntityLivingBase & IShipAttackBase> void removeDebuffs(T host)
  	{
  		if (host != null)
  		{
  			Collection<PotionEffect> effects = host.getActivePotionEffects();
  			HashMap<Integer, Integer> buffs = host.getBuffMap();
  			List<PotionEffect> remove = new ArrayList<PotionEffect>();
  			
  			//get debuffs
  			for (PotionEffect pe : effects)
  			{
  				int id = Potion.getIdFromPotion(pe.getPotion());
  				
  				switch (id)
  				{
  				case 2:
  				case 4:
  				case 7:
  				case 9:
  				case 15:
  				case 17:
  				case 18:
  				case 19:
  				case 20:
  				case 27:
  					remove.add(pe);
				break;
				default:
				break;
  				}
  			}
  			
  			//remove debuffs
  			for (PotionEffect pe : remove)
  			{
  				int id = Potion.getIdFromPotion(pe.getPotion());
  				
				//remove potion in effect list
				host.removePotionEffect(pe.getPotion());
				effects.remove(pe);
				
				//remove potion in buffmap
				buffs.remove(id);
  			}
  		}
  	}
  	
	
}