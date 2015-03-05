package com.lulan.shincolle.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.utility.LogHelper;

/**EQUIP ARRAY ID
 * 0:5SigC 1:6SigC 2:5TwnC 3:6TwnC 4:12.5TwnC 5:14TwnC 6:16TwnC 
 * 7:8TriC 8:16TriC
 */
public class EquipCalc {

	private static Random rand = new Random();
	
	//get equip state
	public static float[] getEquipStat(BasicEntityShip entity, ItemStack item) {
		byte equipID = getEquipID(item);
		float[] getStat = Values.EquipMap.get(equipID);
		float[] eqStat = new float[] {0F,0F,0F,0F,0F,0F,0F,0F,0F,0F,0F,0F,0F};
		
		if(getStat != null) {
			//cannot use the equip, return 0
			if(entity.getEquipType() != 2 && getStat[0] != 2) {
				if(entity.getEquipType() != getStat[0]) return eqStat;
			}
			
			eqStat[ID.HP] = getStat[ID.E.HP];
			eqStat[ID.DEF] = getStat[ID.E.DEF];
			eqStat[ID.SPD] = getStat[ID.E.SPD];
			eqStat[ID.MOV] = getStat[ID.E.MOV];
			eqStat[ID.HIT] = getStat[ID.E.HIT];
			eqStat[ID.ATK] = getStat[ID.E.ATK_L];
			eqStat[ID.ATK_H] = getStat[ID.E.ATK_H];
			eqStat[ID.ATK_AL] = getStat[ID.E.ATK_AL];
			eqStat[ID.ATK_AH] = getStat[ID.E.ATK_AH];
			eqStat[ID.CRI] = getStat[ID.E.CRI];
			eqStat[ID.DHIT] = getStat[ID.E.DHIT];
			eqStat[ID.THIT] = getStat[ID.E.THIT];
			eqStat[ID.MISS] = getStat[ID.E.MISS];
//			LogHelper.info("DEBUG : equip stat "+eqStat[0]+" "+eqStat[1]+" "+eqStat[2]+" "+eqStat[3]+" "+eqStat[4]+" "+eqStat[5]+" "+eqStat[6]+" "+eqStat[7]+" "+eqStat[8]);
		}	
		
		return eqStat;
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

		return -1;
	}
	
	/**roll equip type by total amount of materials
	 * rate type: common(151~300) uncommon(101~150) rare(61~100) superrare(30~60)
	 */
	public static int rollEquipType(int type, int[] matAmount) {
		List<int[]> equipList = new ArrayList<int[]>();
		int totalMats = matAmount[0] + matAmount[1] + matAmount[2] + matAmount[3];
		
		//add roll list
		if(type == 0) {	//small build
			//CANNON_SI uncommon, ARMOR_LO common, RADAR_LO uncommon
			equipList.add(new int[] {ID.EquipType.CANNON_SI, 140});
			equipList.add(new int[] {ID.EquipType.ARMOR_LO, 200});
			equipList.add(new int[] {ID.EquipType.RADAR_LO, 120});
			
			if(totalMats > 128) {
				//TORPEDO_LO uncommon, AIR_R_LO rare
				equipList.add(new int[] {ID.EquipType.TORPEDO_LO, 140});
				equipList.add(new int[] {ID.EquipType.AIR_R_LO, 100});
			}
			
			if(totalMats > 160) {
				//CANNON_TW_LO rare
				equipList.add(new int[] {ID.EquipType.CANNON_TW_LO, 70});
			}
		}
		else {			//large build
			equipList.add(new int[] {ID.EquipType.CANNON_TW_LO, 300 + matAmount[2] / 2});
			equipList.add(new int[] {ID.EquipType.ARMOR_LO, 200 + matAmount[1] / 2});
			equipList.add(new int[] {ID.EquipType.ARMOR_HI, 300 + matAmount[1] / 2});
			equipList.add(new int[] {ID.EquipType.RADAR_LO, 100 + matAmount[0] / 3});
			equipList.add(new int[] {ID.EquipType.TURBINE_LO, 100 + matAmount[0] / 4 + matAmount[1] / 4});
			equipList.add(new int[] {ID.EquipType.TORPEDO_LO, 100 + matAmount[2] / 2});
			equipList.add(new int[] {ID.EquipType.AIR_R_LO, 250 + matAmount[3] / 3});
			equipList.add(new int[] {ID.EquipType.AIR_T_LO, 150 + matAmount[3] / 3});
			equipList.add(new int[] {ID.EquipType.AIR_F_LO, 150 + matAmount[3] / 3});
			equipList.add(new int[] {ID.EquipType.AIR_B_LO, 150 + matAmount[3] / 3});
			
			if(totalMats > 799) {
				equipList.add(new int[] {ID.EquipType.TORPEDO_HI, 100 + matAmount[2] / 2});
				equipList.add(new int[] {ID.EquipType.RADAR_HI, 100 + matAmount[0] / 2});
			}
			
			if(totalMats > 1199) {
				equipList.add(new int[] {ID.EquipType.TURBINE_HI, 100 + matAmount[0] / 4 + matAmount[1] / 4});
				equipList.add(new int[] {ID.EquipType.CANNON_TW_HI, 200 + matAmount[2] / 2});	
				equipList.add(new int[] {ID.EquipType.AIR_R_HI, 200 + matAmount[3] / 3});
				equipList.add(new int[] {ID.EquipType.AIR_T_HI, 100 + matAmount[3] / 3});
				equipList.add(new int[] {ID.EquipType.AIR_F_HI, 100 + matAmount[3] / 3});
				equipList.add(new int[] {ID.EquipType.AIR_B_HI, 100 + matAmount[3] / 3});
			}
			
			if(totalMats > 1599) {
				equipList.add(new int[] {ID.EquipType.CANNON_TR, 100 + matAmount[2] / 2});
			}
		}
		
		//roll item
		int totalRate = 0;
		int sumRate = 0;
		int randNum = 0;
		int rollResult = -1;
		
		//get sum of rate
		for(int[] iter : equipList) {	
			totalRate += iter[1];
		}
		
		//get random number
		randNum = rand.nextInt(totalRate);
		
		//get result
		for(int i = 0; i < equipList.size(); ++i) {
			sumRate += equipList.get(i)[1];
			
			if(sumRate > randNum) {
				rollResult = equipList.get(i)[0];
				break;
			}	
		}
		LogHelper.info("DEBUG : roll equip type: total "+totalRate+" rand "+randNum+" type "+rollResult);	
		return rollResult;
	}
	
	//roll equips of the type
	public static ItemStack rollEquipsOfTheType(int type) {
		if(type == -1) return null;
		
		Map<Byte, float[]> equipList = new HashMap<Byte, float[]>();

		//get equip list
		Iterator iter = Values.EquipMap.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			
			Byte key = (Byte) entry.getKey();
			float[] val = (float[]) entry.getValue();
		    
			if(val[14] == type) {
				equipList.put(key, val);
			}
		}
		
		//roll equips
		int totalRate = 0;
		int sumRate = 0;
		int randNum = 0;
		int rollResult = -1;
		
		//get sum of rate
		iter = equipList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			totalRate += (int)((float[])entry.getValue())[15];
			LogHelper.info("DEBUG : first while "+(int)((float[])entry.getValue())[15]);
		}
		
		//get random number
		randNum = rand.nextInt(totalRate);
		
		//get roll result
		iter = equipList.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			sumRate += (int)((float[])entry.getValue())[15];
			LogHelper.info("DEBUG : second while "+(int)((float[])entry.getValue())[15]+" rand "+randNum);
			
			if(sumRate > randNum) {
				rollResult = ((Byte)entry.getKey()).intValue();
				LogHelper.info("DEBUG : second while get result "+rollResult);
				break;
			}
		}
		
		return getItemStackFromId(rollResult);
	}
	
	//get equip itemstack from id
	private static ItemStack getItemStackFromId(int itemId) {
		ItemStack item = null;
		int itemType = 0;
		
		//get itemstack
		if(itemId >= ID.E_ARMOR) {
			item = new ItemStack(ModItems.EquipArmor);
			item.setItemDamage(itemId - ID.E_ARMOR);
		}
		else if(itemId >= ID.E_TURBINE) {
			item = new ItemStack(ModItems.EquipTurbine);
			item.setItemDamage(itemId - ID.E_TURBINE);
		}
		else if(itemId >= ID.E_RADAR_AIRMK1) {
			item = new ItemStack(ModItems.EquipRadar);
			item.setItemDamage(itemId - ID.E_RADAR_AIRMK1);
		}
		else if(itemId >= ID.E_AIRCRAFT_TMK1) {
			item = new ItemStack(ModItems.EquipAirplane);
			item.setItemDamage(itemId - ID.E_AIRCRAFT_TMK1);
		}
		else if(itemId >= ID.E_TORPEDO_21MK1) {
			item = new ItemStack(ModItems.EquipTorpedo);
			item.setItemDamage(itemId - ID.E_TORPEDO_21MK1);
		}
		else {
			item = new ItemStack(ModItems.EquipCannon);
			item.setItemDamage(itemId);
		}
		
		return item;
	}
	
}
