package com.lulan.shincolle.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.lulan.shincolle.capability.CapaTeitoku;
import com.lulan.shincolle.client.gui.inventory.ContainerShipInventory;
import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEquip;
import com.lulan.shincolle.item.IShipEffectItem;
import com.lulan.shincolle.reference.Enums.EnumEquipEffectSP;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Attrs;
import com.lulan.shincolle.utility.BuffHelper;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.EnchantHelper;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

/**
 * equip calculation class
 */
public class EquipCalc
{

	private static Random rand = new Random();
	
	//roll table
	private static List<int[]> EquipSmall = new ArrayList<int[]>();
	private static List<int[]> EquipLarge = new ArrayList<int[]>();
	
	//init roll table
	static
	{
		/**roll table: 0:equip type, 1:material mean, 2:modified material type
		 * equip type: equip main type + low or high level
		 * material mean: material amount correspond to normal dist, high = need more materials
		 * modified material type: mat can increase build rate: -1:none 0:grudge 1:metal 2:ammo 3:poly
		 */
		//small build
		EquipSmall.add(new int[] {ID.EquipType.ARMOR_LO,      80,   1});
		EquipSmall.add(new int[] {ID.EquipType.FLARE_LO,      80,   2});
		EquipSmall.add(new int[] {ID.EquipType.SEARCHLIGHT_LO,80,   0});
		EquipSmall.add(new int[] {ID.EquipType.COMPASS_LO,    90,   0});
		EquipSmall.add(new int[] {ID.EquipType.GUN_LO,        100,  2});
		EquipSmall.add(new int[] {ID.EquipType.DRUM_LO,       120,  1});
		EquipSmall.add(new int[] {ID.EquipType.AMMO_LO,       120,  2});
		EquipSmall.add(new int[] {ID.EquipType.CANNON_SI,     128,  2});
		EquipSmall.add(new int[] {ID.EquipType.TORPEDO_LO,    160,  2});
		EquipSmall.add(new int[] {ID.EquipType.RADAR_LO,      200,  0});
		EquipSmall.add(new int[] {ID.EquipType.AIR_R_LO,      256,  3});
		EquipSmall.add(new int[] {ID.EquipType.CANNON_TW_LO,  320,  2});
		
		//large build
		EquipLarge.add(new int[] {ID.EquipType.ARMOR_HI,      500,  1});
		EquipLarge.add(new int[] {ID.EquipType.GUN_HI,        800,  2});
		EquipLarge.add(new int[] {ID.EquipType.AMMO_HI,       1000, 2});
		EquipLarge.add(new int[] {ID.EquipType.AIR_R_HI,      1000, 3});
		EquipLarge.add(new int[] {ID.EquipType.TORPEDO_HI,    1200, 2});
		EquipLarge.add(new int[] {ID.EquipType.TURBINE_LO,    1400, 0});
		EquipLarge.add(new int[] {ID.EquipType.CANNON_TW_HI,  1600, 2});	
		EquipLarge.add(new int[] {ID.EquipType.RADAR_HI,      2000, 0});
		EquipLarge.add(new int[] {ID.EquipType.AIR_T_LO,      2400, 3});
		EquipLarge.add(new int[] {ID.EquipType.AIR_F_LO,      2400, 3});
		EquipLarge.add(new int[] {ID.EquipType.AIR_B_LO,      2400, 3});
		EquipLarge.add(new int[] {ID.EquipType.CATAPULT_LO,   2800, 3});
		EquipLarge.add(new int[] {ID.EquipType.TURBINE_HI,    3200, 0});
		EquipLarge.add(new int[] {ID.EquipType.AIR_T_HI,      3800, 3});
		EquipLarge.add(new int[] {ID.EquipType.AIR_F_HI,      3800, 3});
		EquipLarge.add(new int[] {ID.EquipType.AIR_B_HI,      3800, 3});
		EquipLarge.add(new int[] {ID.EquipType.CANNON_TR,     4400, 2});
		EquipLarge.add(new int[] {ID.EquipType.CATAPULT_HI,   5000, 3});
	}
	
	
	/** calc equip attrs */
	public static void updateAttrsEquip(BasicEntityShip ship)
	{
		Attrs attrs = ship.getAttrs();
		
		//reset equips attrs
		attrs.resetAttrsEquip();				//reset equip values
		ship.resetMissileData();				//reset missile data
		ship.calcShipAttributesAddEffect();		//reset attack effect map
		ship.setStateMinor(ID.M.DrumState, 0);
		ship.setStateMinor(ID.M.LevelChunkLoader, 0);
		ship.setStateMinor(ID.M.LevelFlare, 0);
		ship.setStateMinor(ID.M.LevelSearchlight, 0);
		
		//get equips from equip slot
		for (int i = 0; i < ContainerShipInventory.SLOTS_SHIPINV; i++)
		{
			calcEquipAttrs(ship, ship.getCapaShipInventory().getStackInSlot(i));
		}
		
		//apply attrs scale
		float[] equip = attrs.getAttrsEquip();
		
		equip[ID.Attrs.HP] *= ConfigHandler.scaleShip[ID.AttrsBase.HP];
		equip[ID.Attrs.ATK_L] *= ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		equip[ID.Attrs.ATK_H] *= ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		equip[ID.Attrs.ATK_AL] *= ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		equip[ID.Attrs.ATK_AH] *= ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		equip[ID.Attrs.DEF] *= ConfigHandler.scaleShip[ID.AttrsBase.DEF];
		equip[ID.Attrs.SPD] *= ConfigHandler.scaleShip[ID.AttrsBase.SPD];
		equip[ID.Attrs.MOV] *= ConfigHandler.scaleShip[ID.AttrsBase.MOV];
		equip[ID.Attrs.HIT] *= ConfigHandler.scaleShip[ID.AttrsBase.HIT];
	}
	
	/** calc equip attrs of morph ship */
	public static void updateAttrsEquipOfMorph(BasicEntityShip ship)
	{
		Attrs attrs = ship.getAttrs();
		
		//reset equips attrs
		attrs.resetAttrsEquip();				//reset equip values
		ship.resetMissileData();				//reset missile data
		ship.calcShipAttributesAddEffect();		//reset attack effect map
		ship.setStateMinor(ID.M.DrumState, 0);
		ship.setStateMinor(ID.M.LevelChunkLoader, 0);
		ship.setStateMinor(ID.M.LevelFlare, 0);
		ship.setStateMinor(ID.M.LevelSearchlight, 0);
		
		CapaTeitoku capa = CapaTeitoku.getTeitokuCapability(ship.getMorphHost());
		
		if (capa != null)
		{
			//get equips from equip slot
			for (int i = 0; i < ContainerShipInventory.SLOTS_SHIPINV; i++)
			{
				calcEquipAttrs(ship, capa.getStackInSlot(i));
			}
		}
		
		//apply attrs scale
		float[] equip = attrs.getAttrsEquip();
		
		equip[ID.Attrs.HP] *= ConfigHandler.scaleShip[ID.AttrsBase.HP];
		equip[ID.Attrs.ATK_L] *= ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		equip[ID.Attrs.ATK_H] *= ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		equip[ID.Attrs.ATK_AL] *= ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		equip[ID.Attrs.ATK_AH] *= ConfigHandler.scaleShip[ID.AttrsBase.ATK];
		equip[ID.Attrs.DEF] *= ConfigHandler.scaleShip[ID.AttrsBase.DEF];
		equip[ID.Attrs.SPD] *= ConfigHandler.scaleShip[ID.AttrsBase.SPD];
		equip[ID.Attrs.MOV] *= ConfigHandler.scaleShip[ID.AttrsBase.MOV];
		equip[ID.Attrs.HIT] *= ConfigHandler.scaleShip[ID.AttrsBase.HIT];
	}
	
	/**
	 * add equip attrs to AttrsEquip
	 */
	public static void calcEquipAttrs(BasicEntityShip ship, ItemStack equip)
	{
		Attrs attrs = ship.getAttrs();
		
		//reset equip value
		float[] attrsEquip = attrs.getAttrsEquip();
		//get main attrs from equip item
		float[] getEquip = getEquipAttrsMain(ship, equip);
		//get misc attrs from equip item
		int[] getMisc = getEquipAttrsMisc(equip);
		
		//apply main attrs
		for (int i = 0; i < Attrs.AttrsLength; i++)
		{
			attrsEquip[i] += getEquip[i];
		}
		
		//apply misc attrs
		if (getMisc != null)
		{
			ship.setStateMinor(ID.M.DrumState, ship.getStateMinor(ID.M.DrumState) + getMisc[0]);
			ship.setStateMinor(ID.M.LevelChunkLoader, ship.getStateMinor(ID.M.LevelChunkLoader) + getMisc[1]);
			ship.setStateMinor(ID.M.LevelFlare, ship.getStateMinor(ID.M.LevelFlare) + getMisc[2]);
			ship.setStateMinor(ID.M.LevelSearchlight, ship.getStateMinor(ID.M.LevelSearchlight) + getMisc[3]);
		}
		
		//apply item effect of missile
		if (equip != null)
		{
			if (equip.getItem() instanceof IShipEffectItem)
			{
				int meta = equip.getMetadata();
				IShipEffectItem eitem = (IShipEffectItem) equip.getItem();
				
				//apply enchant shell effect
				if (meta == 7 && eitem == ModItems.EquipAmmo)
				{
					BuffHelper.addEffectToAttackEffectMapFromStack(ship, equip);
				}
				
				//apply effect on attack
				Map<Integer, int[]> emap = eitem.getEffectOnAttack(meta);
				
				if (emap != null && emap.size() > 0)
				{
					BuffHelper.addEffectToAttackEffectMap(ship, emap);
				}
				
				//apply type of missile
				int type = eitem.getMissileType(meta);
				
				if (type > 0)
				{
					ship.getMissileData(0).type = type;
					ship.getMissileData(1).type = type;
					ship.getMissileData(2).type = type;
					ship.getMissileData(3).type = type;
					ship.getMissileData(4).type = type;
				}
				
				//apply move type of missile
				type = eitem.getMissileMoveType(meta);
				
				if (type >= 0)
				{
					ship.getMissileData(0).movetype = type;
					ship.getMissileData(1).movetype = type;
					ship.getMissileData(2).movetype = type;
					ship.getMissileData(3).movetype = type;
					ship.getMissileData(4).movetype = type;
				}
				
				//apply speed of missile
				type = eitem.getMissileSpeedLevel(meta);
				
				if (type != 0)
				{
					float addSpeed = type * 0.025F;
					ship.getMissileData(0).vel0 += addSpeed;
					ship.getMissileData(1).vel0 += addSpeed;
					ship.getMissileData(2).vel0 += addSpeed;
					ship.getMissileData(3).vel0 += addSpeed;
					ship.getMissileData(4).vel0 += addSpeed;
					
					addSpeed = type * 0.004F;
					ship.getMissileData(0).accY1 += addSpeed;
					ship.getMissileData(1).accY1 += addSpeed;
					ship.getMissileData(2).accY1 += addSpeed;
					ship.getMissileData(3).accY1 += addSpeed;
					ship.getMissileData(4).accY1 += addSpeed;
					ship.getMissileData(0).accY2 = ship.getMissileData(0).accY1;
					ship.getMissileData(1).accY2 = ship.getMissileData(1).accY1;
					ship.getMissileData(2).accY2 = ship.getMissileData(2).accY1;
					ship.getMissileData(3).accY2 = ship.getMissileData(3).accY1;
					ship.getMissileData(4).accY2 = ship.getMissileData(4).accY1;
				}
			}//end effect item
		}//end special effect
	}
	
	/** get equip attributes from {@link Values.EquipAttrsMain} */
	public static float[] getEquipAttrsMain(BasicEntityShip entity, ItemStack stack)
	{
		if (entity != null && stack != null && stack.getItem() instanceof BasicEquip)
		{
			float[] itemRaw = Values.EquipAttrsMain.get(((BasicEquip) stack.getItem()).getEquipID(stack.getItemDamage()));
			int[] itemMisc = Values.EquipAttrsMisc.get(((BasicEquip) stack.getItem()).getEquipID(stack.getItemDamage()));
			float[] itemEnch = EnchantHelper.calcEnchantEffect(stack);
			
			if (itemRaw != null && itemMisc != null)
			{
				//cannot use this equip, return zeros
				if (entity.getEquipType() != 2 && itemMisc[ID.EquipMisc.EQUIP_TYPE] != 2)
				{
					if (entity.getEquipType() != itemMisc[ID.EquipMisc.EQUIP_TYPE]) return Attrs.getResetZeroValue();
				}
				
				//apply enchant effect and return
				return calcEquipStatWithEnchant(itemMisc[ID.EquipMisc.EQUIP_TYPE], itemRaw, itemEnch);
			}	
		}
		
		return Attrs.getResetZeroValue();
	}
	
	/**
	 * get special equip attributes
	 * return int[]: 0:drum number, 1:chunk loader, 2:flare, 3:searchlight
	 */
	public static int[] getEquipAttrsMisc(ItemStack stack)
	{
		if (stack != null && stack.getItem() instanceof BasicEquip)
		{
			int[] itemStat = new int[] {0, 0, 0, 0};
			EnumEquipEffectSP effect = ((BasicEquip) stack.getItem()).getSpecialEffect(stack);
			
			switch (effect)
			{
			case DRUM:			//drum inventory page
			case DRUM_LIQUID:	//drum liquid tank
			case DRUM_EU:		//drum EU storage
				itemStat[0] = 1;
			break;
			case COMPASS:		//compass
				itemStat[1] = 1;
			break;
			case FLARE:			//flare
				itemStat[2] = 1;
			break;
			case SEARCHLIGHT:	//searchlight
				itemStat[3] = 1;
			break;
			default:			//no effect
			break;
			}
			
			return itemStat;
		}
		
		return null;
	}
	
	/**
	 * calc equip stats with enchantment effect
	 * 
	 * IN: raw equip stats
	 * OUT: enchanted equip stats
	 */
	public static float[] calcEquipStatWithEnchant(int equipType, float[] raw, float[] enchant)
	{
		float[] newstat = new float[Attrs.AttrsLength];
		float modTemp = 1F;
		
		//hp
		newstat[ID.Attrs.HP] = raw[ID.Attrs.HP] * (1F + enchant[ID.Attrs.HP]);
		
		//atk (weapon only)
		modTemp = equipType == 1F ? 1F + enchant[ID.Attrs.ATK_L] : 1F;
		newstat[ID.Attrs.ATK_L] = raw[ID.Attrs.ATK_L] * modTemp;
		newstat[ID.Attrs.ATK_H] = raw[ID.Attrs.ATK_H] * modTemp;
		newstat[ID.Attrs.ATK_AL] = raw[ID.Attrs.ATK_AL] * modTemp;
		newstat[ID.Attrs.ATK_AH] = raw[ID.Attrs.ATK_AH] * modTemp;
		
		//def (armor only)
		modTemp = equipType == 2F ? 1F + enchant[ID.Attrs.DEF] : 1F;
		newstat[ID.Attrs.DEF] = raw[ID.Attrs.DEF] * modTemp;
		
		//spd
		newstat[ID.Attrs.SPD] = raw[ID.Attrs.SPD] * (1F + enchant[ID.Attrs.SPD]);
		
		//mov (negative: reduce, positive: increase)
		if (raw[ID.Attrs.MOV] < 0F)
		{
			modTemp = 1F - enchant[ID.Attrs.MOV];
			if (modTemp < 0F) modTemp = 0F;
		}
		else
		{
			modTemp = 1F + enchant[ID.Attrs.MOV];
		}
		newstat[ID.Attrs.MOV] = raw[ID.Attrs.MOV] * modTemp;
		
		//range
		newstat[ID.Attrs.HIT] = raw[ID.Attrs.HIT] * (1F + enchant[ID.Attrs.HIT]);
		
		//cri
		newstat[ID.Attrs.CRI] = raw[ID.Attrs.CRI] * (1F + enchant[ID.Attrs.CRI]);
		
		//dhit
		newstat[ID.Attrs.DHIT] = raw[ID.Attrs.DHIT] * (1F + enchant[ID.Attrs.DHIT]);
		
		//thit
		newstat[ID.Attrs.THIT] = raw[ID.Attrs.THIT] * (1F + enchant[ID.Attrs.THIT]);
		
		//miss
		newstat[ID.Attrs.MISS] = raw[ID.Attrs.MISS] * (1F + enchant[ID.Attrs.MISS]);
		
		//aa
		newstat[ID.Attrs.AA] = raw[ID.Attrs.AA] * (1F + enchant[ID.Attrs.AA]);
		
		//asm
		newstat[ID.Attrs.ASM] = raw[ID.Attrs.ASM] * (1F + enchant[ID.Attrs.ASM]);
		
		//dodge (negative: reduce, positive: increase)
		if (raw[ID.Attrs.DODGE] < 0F)
		{
			modTemp = 1F - enchant[ID.Attrs.DODGE];
			if (modTemp < 0F) modTemp = 0F;
		}
		else
		{
			modTemp = 1F + enchant[ID.Attrs.DODGE];
		}
		newstat[ID.Attrs.DODGE] = raw[ID.Attrs.DODGE] * modTemp;
		
		//xp gain (weapon only)
		newstat[ID.Attrs.XP] = equipType == 1F ? raw[ID.Attrs.XP] + enchant[ID.Attrs.XP] : raw[ID.Attrs.XP];
		
		//grudge gain (non-weapon only)
		newstat[ID.Attrs.GRUDGE] = equipType != 1F ? raw[ID.Attrs.GRUDGE] + enchant[ID.Attrs.GRUDGE] : raw[ID.Attrs.GRUDGE];
		
		//ammo gain (weapon only)
		newstat[ID.Attrs.AMMO] = equipType == 1F ? raw[ID.Attrs.AMMO] + enchant[ID.Attrs.AMMO] : raw[ID.Attrs.AMMO];
		
		//hp restore (non-weapon only)
		newstat[ID.Attrs.HPRES] = equipType != 1F ? raw[ID.Attrs.HPRES] + enchant[ID.Attrs.HPRES] : raw[ID.Attrs.HPRES];
		
		//knockback resist (non-weapon only)
		newstat[ID.Attrs.KB] = equipType != 1F ? raw[ID.Attrs.KB] + enchant[ID.Attrs.KB] : raw[ID.Attrs.KB];
		
		return newstat;
	}
	
	/**roll equip type by total amount of materials
	 * 
	 * type = 0:small, 1:large
	 * 
	 * 1. get roll list by build type
	 * 2. tweak roll list by mat.amount
	 * 3. roll equip type by roll list
	 */
	public static int rollEquipType(int type, int[] matAmount)
	{
		List<int[]> eqlistOrg = null;  //raw equip list: 0:equip ID, 1:mean 2:specific material
		int totalMats = matAmount[0] + matAmount[1] + matAmount[2] + matAmount[3];
		float te = 4000F;	//for gui info calc
		
		//get equip list by build type
		if (type == 0)
		{
			eqlistOrg = EquipSmall;
			te = 256F;
		}
		else
		{
			eqlistOrg = EquipLarge;
		}
		
		/**roll equip type
		 * 0. tweak roll list by mats amount: specific material decrease the mean value
		 * 1. get prob of equips in roll list
		 * 2. roll 0~1 to get equip type
		 * 3. return equip type (key value in EquipSmall/EquipLarge)
		 */
		//prob list: map<equip ID, prob parameter>
		Map<Integer, Float> probList = new HashMap<Integer, Float>();
		int meanNew = 0;
		int meanDist = 0;
		float prob = 0F;
		
		for (int[] i : eqlistOrg)
		{
			//get material discount, reduce the mean
			if (i[2] >= 0 && i[2] <= 3)
			{
				meanNew = i[1] - matAmount[i[2]];
			}
			else
			{
				meanNew = i[1];
			}
			
			//get mean distance
			meanDist = MathHelper.abs(totalMats - meanNew);
			
			//mean value to prob value
			if (type == 0)
			{
				//for small build, max material = 256
				//change scale to large build resolution
				meanDist = (int) (meanDist * 15.625F); // = mat / 256 * 4000
			}
			
			prob = CalcHelper.getNormDist(meanDist);
			//add to map
			probList.put(i[0], prob);
			LogHelper.debug("DEBUG: roll equip type: prob list: ID "+i[0]+" MEAN(ORG) "+i[1]+" MEAN(NEW) "+meanNew+" MEAN(P) "+(meanNew/te)+" MD "+meanDist+" PR "+prob);
		}
		
		//roll equip type
		float random = rand.nextFloat();
		float totalProb = 0F;
		float sumProb = 0.0125F;	//init value to prevent float comparison bug
		int rollresult = -1;

		//get total prob
		Iterator iter = probList.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry)iter.next();
			totalProb += (Float) entry.getValue();
		}
		
		//scale random number to totalProb
		random *= totalProb;
		
		//roll equip
		iter = probList.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry)iter.next();
			sumProb += (Float) entry.getValue();
			LogHelper.debug("DEBUG: roll equip type: random: "+random+" sum.pr "+sumProb+" total.pr "+totalProb);
			if (sumProb > random)
			{	//get item
				rollresult = (Integer) entry.getKey();
				LogHelper.debug("DEBUG: roll item: get type:"+rollresult);
				break;
			}
		}

		return rollresult;
	}
	
	/** roll equip by equip type
	 *  type = equip type
	 *  totalMatAmount = total material amount
	 *  buildType = 0:small or 1:large build
	 * 
	 *  1. add equips to roll list by equip type
	 *  2. roll equips by roll list
	 */
	public static ItemStack rollEquipsOfTheType(int type, int totalMats, int buildType)
	{
		//null item
		if (type == -1) return null;
		
		//equip roll list: <equip id, float[0:mean value  1:prob parameter]>
		Map<Integer, Float> equipList = new HashMap<Integer, Float>();

		//get equip list, compare the equip type = input type
		Iterator iter = Values.EquipAttrsMisc.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry)iter.next();
			int eid = (Integer) entry.getKey();
			int[] val = (int[]) entry.getValue();
			
			if (val[ID.EquipMisc.RARE_TYPE] == type)
			{
				float prob = 0F;
				int totalMat = 0;
				int meanDist = 0;
				float te = 4000F;  //large build base (for log only)
				
				//if SMALL BUILD, tweak mean distance: change resolution from 256 to 4000
				if (buildType == 0)
				{	//for small build
					totalMat = (int)(totalMats * 15.625F);	// = mats / 256 * 4000
					te = 256F;  //small build base (for log only)
				}
				
				//get mean distance
				meanDist = MathHelper.abs(totalMat - val[ID.EquipMisc.RARE_MEAN]);
				
				//get prob by mean dist
				prob = CalcHelper.getNormDist(meanDist);
				
				//put into map
				equipList.put(eid, prob);
				LogHelper.debug("DEBUG: calc equip: prob list: ID "+eid+" MEAN "+val[ID.EquipMisc.RARE_MEAN]+" MEAN(P) "+(val[ID.EquipMisc.RARE_MEAN]/te)+" MD "+meanDist+" PR "+prob);
			}
		}		
		
		//roll equips
		float random = rand.nextFloat();
		float totalProb = 0F;
		float sumProb = 0.0125F;	//init value to prevent float comparison bug
		int rollResult = -1;
		
		//get total prob
		iter = equipList.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry)iter.next();
			totalProb += (Float) entry.getValue();
		}
		
		//scale random number to totalProb
		random *= totalProb;
		
		//roll equip
		iter = equipList.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry entry = (Map.Entry)iter.next();
			sumProb += (Float) entry.getValue();
			LogHelper.debug("DEBUG: roll equip: type: "+type+" random: "+random+" sum.pr "+sumProb+" total.pr "+totalProb);
			if (sumProb > random)
			{	//get item
				rollResult = (Integer) entry.getKey();
				LogHelper.debug("DEBUG: roll item: get item:"+rollResult);
				break;
			}
		}
		
		//calc enchant level
		int enchLv = 0;
		
		if (buildType == 0)	//small build: max mats = 256
		{
			if (totalMats > 220)
			{
				enchLv = 3;
			}
			else if (totalMats > 200)
			{
				enchLv = 2;
			}
			else if  (totalMats > 180)
			{
				enchLv = 1;
			}
		}
		else				//large build: max mats = 4000
		{
			if (totalMats > 3500)
			{
				enchLv = 3;
			}
			else if (totalMats > 3000)
			{
				enchLv = 2;
			}
			else if  (totalMats > 2000)
			{
				enchLv = 1;
			}
		}
		
		return getItemStackFromId(rollResult, enchLv);
	}
	
	/**
	 * get equip itemstack from id with enchant level
	 * 
	 * enchLv: 0:none, 1:common ench, 2:rare ench, 3:super rare ench
	 */
	private static ItemStack getItemStackFromId(int itemID, int enchLv)
	{
		//itemID = Equip Type ID + Equip Sub ID * 100
		ItemStack item = null;
		int itemType = itemID % 100;	 //item type value
		int itemSubType = itemID / 100;  //item meta value
		int enchType = 0;				 //enchant type: 0:weapon, 1:armor, 2:misc
		
		switch (itemType)
		{
		//cannon
		case ID.EquipType.CANNON_SI:
		case ID.EquipType.CANNON_TW_LO:
		case ID.EquipType.CANNON_TW_HI:
		case ID.EquipType.CANNON_TR:
			item = new ItemStack(ModItems.EquipCannon);
			enchType = 0;
		break;
		//machine gun
		case ID.EquipType.GUN_LO:
		case ID.EquipType.GUN_HI:
			item = new ItemStack(ModItems.EquipMachinegun);
			enchType = 0;
		break;
		//torpedo
		case ID.EquipType.TORPEDO_LO:
		case ID.EquipType.TORPEDO_HI:
			item = new ItemStack(ModItems.EquipTorpedo);
			enchType = 0;
		break;
		//aircraft
		case ID.EquipType.AIR_T_LO:
		case ID.EquipType.AIR_T_HI:
		case ID.EquipType.AIR_F_LO:
		case ID.EquipType.AIR_F_HI:
		case ID.EquipType.AIR_B_LO:
		case ID.EquipType.AIR_B_HI:
		case ID.EquipType.AIR_R_LO:
		case ID.EquipType.AIR_R_HI:
			item = new ItemStack(ModItems.EquipAirplane);
			enchType = 0;
		break;
		//radar
		case ID.EquipType.RADAR_LO:
		case ID.EquipType.RADAR_HI:
			item = new ItemStack(ModItems.EquipRadar);
			enchType = 2;
		break;
		//turbine 
		case ID.EquipType.TURBINE_LO:
		case ID.EquipType.TURBINE_HI:
			item = new ItemStack(ModItems.EquipTurbine);
			enchType = 2;
		break;
		//armor
		case ID.EquipType.ARMOR_LO:
		case ID.EquipType.ARMOR_HI:
			item = new ItemStack(ModItems.EquipArmor);
			enchType = 1;
		break;
		//catapult
		case ID.EquipType.CATAPULT_LO:
		case ID.EquipType.CATAPULT_HI:
			item = new ItemStack(ModItems.EquipCatapult);
			enchType = 2;
		break;
		//drum
		case ID.EquipType.DRUM_LO:
			item = new ItemStack(ModItems.EquipDrum);
			enchType = 2;
		break;
		//compass
		case ID.EquipType.COMPASS_LO:
			item = new ItemStack(ModItems.EquipCompass);
			enchType = 2;
		break;
		//flare
		case ID.EquipType.FLARE_LO:
			item = new ItemStack(ModItems.EquipFlare);
			enchType = 2;
		break;
		//searchlight
		case ID.EquipType.SEARCHLIGHT_LO:
			item = new ItemStack(ModItems.EquipSearchlight);
			enchType = 2;
		break;
		//ammo
		case ID.EquipType.AMMO_LO:
		case ID.EquipType.AMMO_HI:
			item = new ItemStack(ModItems.EquipAmmo);
			enchType = 0;
		break;
		default:
			item = null;
		break;
		}
		
		//set item sub type
		if (item != null) item.setItemDamage(itemSubType);
		
		//apply random enchant
		if (enchLv > 0)
		{
			EnchantHelper.applyRandomEnchantToEquip(item, enchType, enchLv);
		}
		
		LogHelper.debug("DEBUG: equip calc: get itemstack: "+itemType+" "+itemSubType+" "+item);
		return item;
	}
	
	
}