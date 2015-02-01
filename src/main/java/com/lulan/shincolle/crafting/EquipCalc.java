package com.lulan.shincolle.crafting;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.reference.AttrValues;
import com.lulan.shincolle.utility.LogHelper;

/**EQUIP ARRAY ID
 * 0:5SigC 1:6SigC 2:5TwnC 3:6TwnC 4:12.5TwnC 5:14TwnC 6:16TwnC 
 * 7:8TriC 8:16TriC
 */
public class EquipCalc {

	private static Random rand = new Random();
	
	//get equip state
	public static float[] getEquipStat(ItemStack itemstack) {
		float[] eqStat = {0F,0F,0F,0F,0F,0F};
		int equipID = getEquipID(itemstack);
		
		eqStat[AttrID.HP] = AttrValues.EquipHP[equipID];
		eqStat[AttrID.ATK] = AttrValues.EquipATK[equipID];
		eqStat[AttrID.DEF] = AttrValues.EquipDEF[equipID];
		eqStat[AttrID.SPD] = AttrValues.EquipSPD[equipID];
		eqStat[AttrID.MOV] = AttrValues.EquipMOV[equipID];
		eqStat[AttrID.HIT] = AttrValues.EquipHIT[equipID];
	//	LogHelper.info("DEBUG : equip "+eqStat[0]+" "+eqStat[1]+" "+eqStat[2]+" "+eqStat[3]+" "+eqStat[4]+" "+eqStat[5]);
		return eqStat;
	}
	
	//get equip id
	public static int getEquipID(ItemStack itemstack) {
		int meta = 0;
		int equipID = 0;
		
		if(itemstack.getItem() == ModItems.EquipCannon) {	//is equip cannon
			equipID = itemstack.getItemDamage();		
		}
/*		else if() {  //is equip torpedo
			
		}*/

		return equipID;
	}
}
