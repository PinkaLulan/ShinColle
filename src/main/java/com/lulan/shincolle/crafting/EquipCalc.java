package com.lulan.shincolle.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;

/**EQUIP ARRAY ID
 * 0:5SigC 1:6SigC 2:5TwnC 3:6TwnC 4:12.5TwnC 5:14TwnC 6:16TwnC 
 * 7:8TriC 8:16TriC
 */
public class EquipCalc {

	private static Random rand = new Random();
	
	//roll table
	private static List<int[]> EquipSmall = new ArrayList<int[]>();
	private static List<int[]> EquipLarge = new ArrayList<int[]>();
	
	//init roll table
	static {
		/**roll table: 0:equip type, 1:material mean, 2:modified material type
		 * equip type: equip main type + low or high level
		 * material mean: material amount correspond to normal dist, high = need more materials
		 * modified material type: mat can increase build rate: -1:none 0:grudge 1:metal 2:ammo 3:poly
		 */
		//small build
		EquipSmall.add(new int[] {ID.EquipType.ARMOR_LO,     80,   1});
		EquipSmall.add(new int[] {ID.EquipType.GUN_LO,       100,  2});
		EquipSmall.add(new int[] {ID.EquipType.CANNON_SI,    128,  2});
		EquipSmall.add(new int[] {ID.EquipType.TORPEDO_LO,   160,  2});
		EquipSmall.add(new int[] {ID.EquipType.RADAR_LO,     200,  0});
		EquipSmall.add(new int[] {ID.EquipType.AIR_R_LO,     256,  3});
		EquipSmall.add(new int[] {ID.EquipType.CANNON_TW_LO, 320,  2});
		
		//large build
		EquipLarge.add(new int[] {ID.EquipType.ARMOR_HI,     500,  1});
		EquipLarge.add(new int[] {ID.EquipType.GUN_HI,       800,  2});
		EquipLarge.add(new int[] {ID.EquipType.AIR_R_HI,     1000, 3});
		EquipLarge.add(new int[] {ID.EquipType.TORPEDO_HI,   1200, 2});
		EquipLarge.add(new int[] {ID.EquipType.TURBINE_LO,   1400, 0});
		EquipLarge.add(new int[] {ID.EquipType.CANNON_TW_HI, 1600, 2});	
		EquipLarge.add(new int[] {ID.EquipType.RADAR_HI,     2000, 0});
		EquipLarge.add(new int[] {ID.EquipType.AIR_T_LO,     2400, 3});
		EquipLarge.add(new int[] {ID.EquipType.AIR_F_LO,     2400, 3});
		EquipLarge.add(new int[] {ID.EquipType.AIR_B_LO,     2400, 3});
		EquipLarge.add(new int[] {ID.EquipType.CATAPULT_LO,  2800, 3});
		EquipLarge.add(new int[] {ID.EquipType.TURBINE_HI,   3200, 0});
		EquipLarge.add(new int[] {ID.EquipType.AIR_T_HI,     3800, 3});
		EquipLarge.add(new int[] {ID.EquipType.AIR_F_HI,     3800, 3});
		EquipLarge.add(new int[] {ID.EquipType.AIR_B_HI,     3800, 3});
		EquipLarge.add(new int[] {ID.EquipType.CANNON_TR,    4400, 2});
		EquipLarge.add(new int[] {ID.EquipType.CATAPULT_HI,  5000, 3});
	}
	
	
	//get equip state
	public static float[] getEquipStat(BasicEntityShip entity, ItemStack item) {
		if(item != null && entity != null) {
			byte equipID = getEquipID(item);
			float[] getStat = Values.EquipMap.get(equipID);	//get item attributes
			
			if(getStat != null) {
				//cannot use this equip, return null
				if(entity.getEquipType() != 2 && getStat[0] != 2) {
					if(entity.getEquipType() != getStat[0]) return null;
				}
	//			LogHelper.info("DEBUG : equip stat "+equipID+" "+getStat[0]+" "+getStat[1]+" "+getStat[2]+" "+getStat[3]+" "+getStat[4]+" "+getStat[5]+" "+getStat[6]+" "+getStat[7]+" "+getStat[8]);
				return getStat;
			}	
		}
		return null;
	}
	
	//get equip id
	public static byte getEquipID(ItemStack itemstack) {
		byte equipID = 0;
		
		if(itemstack.getItem() == ModItems.EquipAirplane) {
			return (byte) (ID.E_AIRCRAFT_TMK1 + itemstack.getItemDamage());
		}
		if(itemstack.getItem() == ModItems.EquipArmor) {
			return (byte) (ID.E_ARMOR + itemstack.getItemDamage());
		}
		if(itemstack.getItem() == ModItems.EquipCannon) {
			return (byte) (ID.E_CANNON_SINGLE_5 + itemstack.getItemDamage());
		}
		if(itemstack.getItem() == ModItems.EquipRadar) {
			return (byte) (ID.E_RADAR_AIRMK1 + itemstack.getItemDamage());
		}
		if(itemstack.getItem() == ModItems.EquipTorpedo) {
			return (byte) (ID.E_TORPEDO_21MK1 + itemstack.getItemDamage());
		}
		if(itemstack.getItem() == ModItems.EquipTurbine) {
			return (byte) (ID.E_TURBINE + itemstack.getItemDamage());
		}
		if(itemstack.getItem() == ModItems.EquipMachinegun) {
			return (byte) (ID.E_GUN_HA_3 + itemstack.getItemDamage());
		}
		if(itemstack.getItem() == ModItems.EquipCatapult) {
			return (byte) (ID.E_CATAPULT_F + itemstack.getItemDamage());
		}

		return -1;
	}
	
	/**roll equip type by total amount of materials
	 * 
	 * type = 0:small, 1:large
	 * 
	 * 1. get roll list by build type
	 * 2. tweak roll list by mat.amount
	 * 3. roll equip type by roll list
	 */
	public static int rollEquipType(int type, int[] matAmount) {
		List<int[]> eqlistOrg = null;  //raw equip list: 0:equip ID, 1:mean 2:specific material
		int totalMats = matAmount[0] + matAmount[1] + matAmount[2] + matAmount[3];
		float te = 4000F;	//debug
		//get equip list by build type
		if(type == 0) {
			eqlistOrg = EquipSmall;
			te = 256F;
		}
		else {
			eqlistOrg = EquipLarge;
		}
		
		/**roll equip type
		 * 0. tweak roll list by mat.amount: specific material decrease the mean value
		 * 1. get prob of equips in roll list
		 * 2. roll 0~1 to get equip type
		 * 3. return equip type
		 */
		//prob list: map<equip ID, prob parameter>
		Map<Integer, Float> probList = new HashMap<Integer, Float>();
		int meanNew = 0;
		int meanDist = 0;
		float prob = 0F;
		
		for(int[] i : eqlistOrg) {
			//get equip modified material type
			if(i[2] >= 0 && i[2] <= 3) {
				meanNew = i[1] - matAmount[i[2]];
			}
			else {
				meanNew = i[1];
			}
			
			//get mean distance
			meanDist = MathHelper.abs_int(totalMats - meanNew);
			
			//mean value to prob value
			if(type == 0) {
				//for small build, max material = 256
				//change scale to large build resolution
				meanDist = (int) (meanDist * 15.625F); // = mat / 256 * 4000
			}
			
			prob = CalcHelper.getNormDist(meanDist);
			//add to map
			probList.put(i[0], prob);
			LogHelper.info("DEBUG: roll equip type: prob list: ID "+i[0]+" MEAN(ORG) "+i[1]+" MEAN(NEW) "+meanNew+" MEAN(P) "+(meanNew/te)+" MD "+meanDist+" PR "+prob);
		}
		
		//roll equip type
		float random = rand.nextFloat();
		float totalProb = 0F;
		float sumProb = 0.0125F;	//init value to prevent float comparison bug
		int rollresult = -1;

		//get total prob
		Iterator iter = probList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			totalProb += (Float) entry.getValue();
		}
		
		//scale random number to totalProb
		random *= totalProb;
		
		//roll equip
		iter = probList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			sumProb += (Float) entry.getValue();
			LogHelper.info("DEBUG: roll equip type: random: "+random+" sum.pr "+sumProb+" total.pr "+totalProb);
			if(sumProb > random) {	//get item
				rollresult = (Integer) entry.getKey();
				LogHelper.info("DEBUG: roll item: get type:"+rollresult);
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
	public static ItemStack rollEquipsOfTheType(int type, int totalMatParam, int buildType) {
		//null item
		if(type == -1) return null;
		
		//equip roll list: <equip id, float[0:mean value  1:prob parameter]>
		Map<Integer, Float> equipList = new HashMap<Integer, Float>();

		//get equip list
		Iterator iter = Values.EquipMap.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			int eid = (Byte) entry.getKey();
			float[] val = (float[]) entry.getValue();
			
			if(val[ID.E.RARE_TYPE] == type) {
				float prob = 0F;
				int totalMat = 0;
				int meanDist = 0;
				float te = 4000F;	//debug
				
				//tweak mean distance: resolution from 256 to 4000
				if(buildType == 0) {	//for small build
					totalMat = (int)(totalMatParam * 15.625F);	// = mats / 256 * 4000
					te = 256F;
				}
				//get mean distance
				meanDist = MathHelper.abs_int(totalMat - (int)val[ID.E.RARE_MEAN]);
				//get prob by mean dist
				prob = CalcHelper.getNormDist(meanDist);
				//put into map
				equipList.put(eid, prob);
				LogHelper.info("DEBUG: calc equip: prob list: ID "+eid+" MEAN "+val[ID.E.RARE_MEAN]+" MEAN(P) "+(val[ID.E.RARE_MEAN]/te)+" MD "+meanDist+" PR "+prob);
			}
		}		
		
		//roll equips
		float random = rand.nextFloat();
		float totalProb = 0F;
		float sumProb = 0.0125F;	//init value to prevent float comparison bug
		int rollResult = -1;
		
		//get total prob
		iter = equipList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			totalProb += (Float) entry.getValue();
		}
		
		//scale random number to totalProb
		random *= totalProb;
		
		//roll equip
		iter = equipList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			sumProb += (Float) entry.getValue();
			LogHelper.info("DEBUG: roll equip: type: "+type+" random: "+random+" sum.pr "+sumProb+" total.pr "+totalProb);
			if(sumProb > random) {	//get item
				rollResult = (Integer) entry.getKey();
				LogHelper.info("DEBUG: roll item: get item:"+rollResult);
				break;
			}
		}
		
		return getItemStackFromId(rollResult);
	}
	
	//get equip itemstack from id
	private static ItemStack getItemStackFromId(int itemId) {
		ItemStack item = null;
		int itemType = 0;

		//get itemstack, itemId - 該類item的起始值 = 該類item的meta值
		switch(itemId) {
		//cannon
		case ID.E_CANNON_SINGLE_5:
		case ID.E_CANNON_SINGLE_6:
		case ID.E_CANNON_TWIN_5:
		case ID.E_CANNON_TWIN_6:
		case ID.E_CANNON_TWIN_5DP:
		case ID.E_CANNON_TWIN_125:
		case ID.E_CANNON_TWIN_14:
		case ID.E_CANNON_TWIN_16:
		case ID.E_CANNON_TWIN_20:
		case ID.E_CANNON_TRI_8:
		case ID.E_CANNON_TRI_16:
		case ID.E_CANNON_FG_15:
			item = new ItemStack(ModItems.EquipCannon);
			item.setItemDamage(itemId);
			break;
		//machine gun
		case ID.E_GUN_HA_3:
		case ID.E_GUN_HA_5:
		case ID.E_GUN_SINGLE_12:
		case ID.E_GUN_SINGLE_20:
		case ID.E_GUN_TWIN_40:
		case ID.E_GUN_QUAD_40:
		case ID.E_GUN_TWIN_4_CIC:
			item = new ItemStack(ModItems.EquipMachinegun);
			item.setItemDamage(itemId - ID.E_GUN_HA_3);
			break;
		//torpedo
		case ID.E_TORPEDO_21MK1:
		case ID.E_TORPEDO_21MK2:
		case ID.E_TORPEDO_22MK1:
		case ID.E_TORPEDO_CUTTLEFISH:
		case ID.E_TORPEDO_HIGHSPEED:
			item = new ItemStack(ModItems.EquipTorpedo);
			item.setItemDamage(itemId - ID.E_TORPEDO_21MK1);
			break;
		//aircraft
		case ID.E_AIRCRAFT_TMK1:
		case ID.E_AIRCRAFT_TMK2:
		case ID.E_AIRCRAFT_TMK3:
		case ID.E_AIRCRAFT_TAVENGER:
		case ID.E_AIRCRAFT_TAVENGERK:
		case ID.E_AIRCRAFT_FMK1:
		case ID.E_AIRCRAFT_FMK2:
		case ID.E_AIRCRAFT_FMK3:
		case ID.E_AIRCRAFT_FFLYFISH:
		case ID.E_AIRCRAFT_FHELLCAT:
		case ID.E_AIRCRAFT_FHELLCATK:
		case ID.E_AIRCRAFT_BMK1:
		case ID.E_AIRCRAFT_BMK2:
		case ID.E_AIRCRAFT_BFLYFISH:
		case ID.E_AIRCRAFT_BHELL:
		case ID.E_AIRCRAFT_BHELLK:
		case ID.E_AIRCRAFT_R:
		case ID.E_AIRCRAFT_RFLYFISH:
			item = new ItemStack(ModItems.EquipAirplane);
			item.setItemDamage(itemId - ID.E_AIRCRAFT_TMK1);
			break;
		//radar
		case ID.E_RADAR_AIRMK1:
		case ID.E_RADAR_AIRMK2:
		case ID.E_RADAR_SURMK1:
		case ID.E_RADAR_SURMK2:
		case ID.E_RADAR_SONAR:
		case ID.E_RADAR_AIRABYSS:
		case ID.E_RADAR_SURABYSS:
		case ID.E_RADAR_SONARMK2:
		case ID.E_RADAR_FCSCIC:
			item = new ItemStack(ModItems.EquipRadar);
			item.setItemDamage(itemId - ID.E_RADAR_AIRMK1);
			break;
		//turbine 
		case ID.E_TURBINE:
		case ID.E_TURBINE_IMP:
		case ID.E_TURBINE_ENH:
			item = new ItemStack(ModItems.EquipTurbine);
			item.setItemDamage(itemId - ID.E_TURBINE);
			break;
		//armor
		case ID.E_ARMOR:
		case ID.E_ARMOR_ENH:
			item = new ItemStack(ModItems.EquipArmor);
			item.setItemDamage(itemId - ID.E_ARMOR);
			break;
		//catapult
		case ID.E_CATAPULT_F:
		case ID.E_CATAPULT_H:
		case ID.E_CATAPULT_C:
		case ID.E_CATAPULT_E:
			item = new ItemStack(ModItems.EquipCatapult);
			item.setItemDamage(itemId - ID.E_CATAPULT_F);
			break;
		default:
			item = null;
			break;
		}
		
		return item;
	}
	
}
