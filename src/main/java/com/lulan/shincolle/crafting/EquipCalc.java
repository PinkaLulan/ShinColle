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
import com.lulan.shincolle.item.BasicEquip;
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
		EquipSmall.add(new int[] {ID.EquipType.FLARE_LO,     80,   2});
		EquipSmall.add(new int[] {ID.EquipType.SEARCHLIGHT_LO,80,  0});
		EquipSmall.add(new int[] {ID.EquipType.COMPASS_LO,   90,   0});
		EquipSmall.add(new int[] {ID.EquipType.GUN_LO,       100,  2});
		EquipSmall.add(new int[] {ID.EquipType.DRUM_LO,      120,  1});
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
	
	
	//get equip stats
	public static float[] getEquipStat(BasicEntityShip entity, ItemStack item) {
		if(entity != null && item != null && item.getItem() instanceof BasicEquip) {
			float[] itemStat = Values.EquipMap.get(((BasicEquip)item.getItem()).getEquipID(item.getItemDamage()));
			
			if(itemStat != null) {
				//cannot use this equip, return null
				if(entity.getEquipType() != 2 && itemStat[0] != 2) {
					if(entity.getEquipType() != itemStat[0]) return null;
				}
	//			LogHelper.info("DEBUG : equip stat "+equipID+" "+getStat[0]+" "+getStat[1]+" "+getStat[2]+" "+getStat[3]+" "+getStat[4]+" "+getStat[5]+" "+getStat[6]+" "+getStat[7]+" "+getStat[8]);
				return itemStat;
			}	
		}
		return null;
	}
	
	/** get special equip stats
	 *  return 0:inv page, 1:chunk loader, 2:flare, 3:searchlight
	 */
	public static float[] getEquipStatMisc(BasicEntityShip entity, ItemStack item) {
		if(entity != null && item != null && item.getItem() instanceof BasicEquip) {
			float[] itemStat = new float[] {0, 0, 0, 0};
					
			//inventory page item
			if(item.getItem() == ModItems.EquipDrum) {
				itemStat[0] = 1;
			}
			else if(item.getItem() == ModItems.EquipCompass) {
				itemStat[1] = 1;
			}
			else if(item.getItem() == ModItems.EquipFlare && entity.getEquipType() != 3) {
				itemStat[2] = 1;
			}
			else if(item.getItem() == ModItems.EquipSearchlight) {
				itemStat[3] = 1;
			}
			
			return itemStat;
		}
		return null;
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
		
		for(int[] i : eqlistOrg) {
			//get material discount, reduce the mean
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

		//get equip list, compare the equip type = input type
		Iterator iter = Values.EquipMap.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			int eid = (Integer) entry.getKey();
			float[] val = (float[]) entry.getValue();
			
			if(val[ID.E.RARE_TYPE] == type) {
				float prob = 0F;
				int totalMat = 0;
				int meanDist = 0;
				float te = 4000F;  //large build base (for log only)
				
				//if SMALL BUILD, tweak mean distance: change resolution from 256 to 4000
				if(buildType == 0) {	//for small build
					totalMat = (int)(totalMatParam * 15.625F);	// = mats / 256 * 4000
					te = 256F;  //small build base (for log only)
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
	private static ItemStack getItemStackFromId(int itemID) {
		//itemID = Equip Type ID + Equip Sub ID * 100
		ItemStack item = null;
		int itemType = itemID % 100;	 //item type value
		int itemSubType = itemID / 100;  //item meta value
		
		switch(itemType) {
		//cannon
		case ID.EquipType.CANNON_SI:
		case ID.EquipType.CANNON_TW_LO:
		case ID.EquipType.CANNON_TW_HI:
		case ID.EquipType.CANNON_TR:
			item = new ItemStack(ModItems.EquipCannon);
			break;
		//machine gun
		case ID.EquipType.GUN_LO:
		case ID.EquipType.GUN_HI:
			item = new ItemStack(ModItems.EquipMachinegun);
			break;
		//torpedo
		case ID.EquipType.TORPEDO_LO:
		case ID.EquipType.TORPEDO_HI:
			item = new ItemStack(ModItems.EquipTorpedo);
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
			break;
		//radar
		case ID.EquipType.RADAR_LO:
		case ID.EquipType.RADAR_HI:
			item = new ItemStack(ModItems.EquipRadar);
			break;
		//turbine 
		case ID.EquipType.TURBINE_LO:
		case ID.EquipType.TURBINE_HI:
			item = new ItemStack(ModItems.EquipTurbine);
			break;
		//armor
		case ID.EquipType.ARMOR_LO:
		case ID.EquipType.ARMOR_HI:
			item = new ItemStack(ModItems.EquipArmor);
			break;
		//catapult
		case ID.EquipType.CATAPULT_LO:
		case ID.EquipType.CATAPULT_HI:
			item = new ItemStack(ModItems.EquipCatapult);
			break;
		//drum
		case ID.EquipType.DRUM_LO:
			item = new ItemStack(ModItems.EquipDrum);
			break;
		//drum
		case ID.EquipType.COMPASS_LO:
			item = new ItemStack(ModItems.EquipCompass);
			break;
			//drum
		case ID.EquipType.FLARE_LO:
			item = new ItemStack(ModItems.EquipFlare);
			break;
			//drum
		case ID.EquipType.SEARCHLIGHT_LO:
			item = new ItemStack(ModItems.EquipSearchlight);
			break;
		default:
			item = null;
			break;
		}
		
		//set item sub type
		if(item != null) item.setItemDamage(itemSubType);
		
		LogHelper.info("DEBUG : equip calc: get itemstack: "+itemType+" "+itemSubType+" "+item);
		return item;
	}
	
}
