package com.lulan.shincolle.crafting;

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
		byte equipLevel = Values.EquipType[entity.getShipID()];
		float[] getStat = Values.EquipMap.get(equipID);
		float[] eqStat = new float[] {0F,0F,0F,0F,0F,0F,0F,0F,0F,0F,0F,0F,0F};
		
		if(getStat != null) {
			//cannot use the equip, return 0
			if(getStat[0] != 2 && equipLevel != getStat[0]) return eqStat;
			
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
}
