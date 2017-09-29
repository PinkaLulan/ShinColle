package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.unitclass.Attrs;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

/**
 * enchant helper for equip enchantment
 * 
 */
public class EnchantHelper
{

	//enchant table
	private static ArrayList<int[]> EnchantTable = new ArrayList<int[]>();
	
	//init roll table
	static
	{
		/**
		 * table: 0:for weapon, 1:armor, 2:misc
		 */
		EnchantTable.add(new int[] {5, 7, 9, 16, 17, 18, 19, 20, 21, 32, 35, 48, 49, 50, 51, 61});
		EnchantTable.add(new int[] {0, 1, 3, 4, 33, 34, 70});
		EnchantTable.add(new int[] {2, 6, 8, 33, 62, 70});
	}
	
	public EnchantHelper() {}
	
	/**
	 * equip enchant type: armor, weapon, misc
	 * 
	 * usable enchant:
	 *   XXX_protection  (max 4, C~R) : +hp
	 *     protection, fire_protection, blast_protection, projectile_protection
	 *   damage          (max 5, C~UC): +atk (weapon only)
	 *     sharpness, smite, bane_of_arthropods, power
	 *   unbreaking      (max 3, UC)  : +def (armor only)
	 *   efficiency      (max 5, C)   : +spd
	 *   depth_strider   (max 3, R)   : -weight
	 *   feather_falling (max 4, UC)  : -weight
	 *   aqua_affinity   (max 1, R)   : -weight
	 *   knockback       (max 2, UC)  : +ran
	 *     knockback, punch
	 *   frost_walker    (max 2, R)   : +cri
	 *   fire_aspect     (max 2, R)   : +dhit, +thit
	 *     fire_aspect, flame
	 *   lure            (max 3, R)   : +miss
	 *   thorns          (max 3, VR)  : +AA
	 *   respiration     (max 3, R)   : +ASM
	 *   looting         (max 3, R)   : +xp gain (weapon only)
	 *     looting, fortune, luck_of_the_sea
	 *   silk_touch      (max 1, VR)  : grudge gain (non-weapon only)
	 *   infinity        (max 1, VR)  : ammo gain (weapon only)
	 *   mending         (max 1, R)   : hp restore effect (non-weapon only)
	 * 
	 * return array: ref: ID.EquipEnch
	 *   hp, atk, def, spd, mov, range, cri, dhit, thit, miss,
	 *   aa, asm, dodge, xp gain, grudge gain, ammo gain, -hp delay
	 */
	public static float[] calcEnchantEffect(ItemStack stack)
	{
		float[] ench = new float[Attrs.AttrsLength];
        NBTTagList nbttaglist = stack.getEnchantmentTagList();

        if (nbttaglist != null)
        {
        	//loop all enchantments
            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
            	boolean nonVanilla = false;		//not vanilla enchantment flag
                int id = nbttaglist.getCompoundTagAt(i).getShort("id");
                int lv = nbttaglist.getCompoundTagAt(i).getShort("lvl");

                //for vanilla enchant, check id only
                switch (id)
                {
                //hp
                case 3:		//R  4 blast_protection
                	ench[ID.Attrs.HP] += 0.05F * lv;
                	ench[ID.Attrs.KB] += 0.1F * lv;
                case 1:		//UC 4 fire_protection
                case 4:		//UC 4 projectile_protection
                	ench[ID.Attrs.HP] += 0.05F * lv;
                case 0:		//C  4 protection
                	ench[ID.Attrs.HP] += 0.1F * lv;
            	break;
            	//atk
                case 17:	//UC 5 smite
                case 18:	//UC 5 bane_of_arthropods
                	ench[ID.Attrs.ATK_L] += 0.08F * lv;
                case 16:	//C  5 sharpness
                case 48:	//C  5 power
                	ench[ID.Attrs.ATK_L] += 0.08F * lv;
                break;
                //def
                case 34:	//UC 3 unbreaking
                	ench[ID.Attrs.DEF] += 0.2F * lv;
            	break;
            	//spd
                case 32:	//C  5 efficiency
                	ench[ID.Attrs.SPD] += 0.1F * lv;
            	break;
            	//mov
                case 6:		//R  1 aqua_affinity
                case 8:		//R  3 depth_strider
                	ench[ID.Attrs.MOV] += 0.05F * lv;
                	ench[ID.Attrs.DODGE] += 0.25F * lv;
            	break;
                case 2:		//UC 4 feather_falling
                	ench[ID.Attrs.MOV] += 0.1F * lv;
                	ench[ID.Attrs.KB] -= 0.1F * lv;
            	break;
            	//range
                case 49:	//R  2 punch
                case 19:	//UC 2 knockback
                	ench[ID.Attrs.HIT] += 0.15F * lv;
                	ench[ID.Attrs.KB] += 0.05F * lv;
            	break;
            	//cri
                case 9:		//R  2 frost_walker
                	ench[ID.Attrs.CRI] += 0.25F * lv;
            	break;
            	//dhit, thit
                case 20:	//R  2 fire_aspect
                case 50:	//R  1 flame
                	ench[ID.Attrs.DHIT] += 0.25F * lv;
                	ench[ID.Attrs.THIT] += 0.25F * lv;
            	break;
            	//miss
                case 62:	//R  3 lure
                	ench[ID.Attrs.MISS] += 0.25F * lv;
            	break;
            	//aa
                case 7:		//VR 3 thorns
                	ench[ID.Attrs.AA] += 0.15F * lv;
            	break;
            	//asm
                case 5:		//R  3 respiration
                	ench[ID.Attrs.ASM] += 0.15F * lv;
            	break;
                //xp gain
                case 21:	//R  3 looting
                case 35:	//R  3 fortune
                case 61:	//R  3 luck_of_the_sea
                	ench[ID.Attrs.XP] += 0.25F * lv;
            	break;
            	//grudge gain
                case 33:	//VR 1 silk_touch
                	ench[ID.Attrs.GRUDGE] += 0.25F * lv;
            	break;
            	//ammo gain
                case 51:	//VR 1 infinity
                	ench[ID.Attrs.AMMO] += 0.25F * lv;
            	break;
            	//hp restore
                case 70:	//R  1 mending
                	ench[ID.Attrs.HPRES] += 0.5F * lv;
            	break;
            	default:
            		nonVanilla = true;
                break;
                }
                
                //for non vanilla enchantment
                if (nonVanilla && Enchantment.getEnchantmentByID(id) != null)
                {
                	//increase all effect by 1%
                	for (int j = 0; j < ench.length; j++)
                	{
                		ench[j] += 0.01F * lv;
                	}
                }
            }//end for all ench
        }//end get ench list
        
        return ench;
	}
	
	/**
	 * sum of enchantment number
	 */
	public static int calcEnchantNumber(ItemStack stack)
	{
		int number = 0;
        NBTTagList nbttaglist = stack.getEnchantmentTagList();

        if (nbttaglist != null)
        {
            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                int lv = nbttaglist.getCompoundTagAt(i).getShort("lvl");
                number += lv;
            }
        }
        
        return number;
	}
	
	/**
	 * apply random enchant to equip by lv and type
	 * 
	 * enchLv: 0:none, 1:40%=1 ench, 2:30%=1 30%=2, 3:30%=1 30%=2 20%=3  
	 */
	public static void applyRandomEnchantToEquip(ItemStack stack, int enchType, int enchLv)
	{
		if (stack == null || enchLv == 0) return;
		
		//roll #enchant
		Random rand = new Random();
		int enchNum = 0;
		int ranNum = rand.nextInt(10);
		
		switch (enchLv)
		{
		case 1:
			enchNum = ranNum > 5 ? 1 : 0;
		break;
		case 2:
			enchNum = ranNum > 6 ? 2 : ranNum > 3 ? 1 : 0;
		break;
		case 3:
			enchNum = ranNum > 7 ? 3 : ranNum > 4 ? 2 : ranNum > 1 ? 1 : 0;
		break;
		}
		
		if (enchNum <= 0) return;
		
		//roll enchant id
		int[] enchs = EnchantTable.get(enchType);
		Enchantment ench;
		
		HashMap<Enchantment, Integer> enchmap = new HashMap<Enchantment, Integer>();
		
		for (int i = 0; i < enchNum; i++)
		{
			ench = Enchantment.getEnchantmentByID(enchs[rand.nextInt(enchs.length)]);
			
			//if enchant already exist, lv++
			if (enchmap.containsKey(ench))
			{
				//add level
				int lv = enchmap.get(ench) + 1;
				if (lv > ench.getMaxLevel()) lv = ench.getMaxLevel();
				//save ench
				enchmap.replace(ench, lv);
			}
			else
			{
				enchmap.put(ench, 1);
			}
		}
		
		//apply enchant
		EnchantmentHelper.setEnchantments(enchmap, stack);
	}

//	/** check enchant effect for HP */
//	public static boolean isEnchantHP(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for ATK */
//	public static boolean isEnchantATK(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for DEF */
//	public static boolean isEnchantDEF(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for SPD */
//	public static boolean isEnchantSPD(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for MOV */
//	public static boolean isEnchantMOV(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for HIT */
//	public static boolean isEnchantHIT(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for CRI */
//	public static boolean isEnchantCRI(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for DHIT */
//	public static boolean isEnchantDHIT(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for THIT */
//	public static boolean isEnchantTHIT(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for MISS */
//	public static boolean isEnchantMISS(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for AA */
//	public static boolean isEnchantAA(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for ASM */
//	public static boolean isEnchantASM(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for DODGE */
//	public static boolean isEnchantDODGE(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for XP GAIN */
//	public static boolean isEnchantXPGAIN(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for GRUDGE GAIN */
//	public static boolean isEnchantGRUDGE(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for AMMO GAIN */
//	public static boolean isEnchantAMMO(Enchantment ench)
//	{
//		return false;
//	}
//	
//	/** check enchant effect for HP RESTORE */
//	public static boolean isEnchantHPRES(Enchantment ench)
//	{
//		return false;
//	}
	
	
}