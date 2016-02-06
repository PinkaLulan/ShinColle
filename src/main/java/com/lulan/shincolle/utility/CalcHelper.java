package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import com.lulan.shincolle.entity.IShipAttributes;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.Values.N;


/**CALC HELPER
 * format and math method
 */
public class CalcHelper {
	
	//normal table, resolution = 4000, calc half curve only
	public static float[] NORM_TABLE = new float[2000];		//norm: mean = 0.5, sd = 0.2
	private static float NORM_MIN = 0.2F;					//min value in norm table
	
	//init norm table (half curve)
	static {
		for(int i = 0; i < 2000; i++) {
			//normalize max to 1.0, normDist(mean 0.5, sd 0.2) peak = 1.9947114F
			NORM_TABLE[i] = calcNormalDist(0.5F - i*0.00025F, 0.5F, 0.2F) * 0.50132566F;
			//set min = 0.2 (if items in roll list = 5, 0.2 = 4%)
			if(NORM_TABLE[i] < NORM_MIN) NORM_TABLE[i] = NORM_MIN;
		}
	}
	
	
	public CalcHelper() {}
	
	/** 將second轉成格式 00:00:00 */
	public static String getTimeFormated(int sec) {
		int timeSec = 0;
		int timeMin = 0;
		int timeHr = 0;
		
		timeSec = sec % 60;
		timeMin = (sec % 3600) / 60;
		timeHr = sec / 3600;
		
		return get2Digit(timeHr) + ":" + get2Digit(timeMin) + ":" + get2Digit(timeSec);
	}

	/** 將數字轉為2字母字串, 注意num > 0 */
	public static String get2Digit(int num) {
	    if(num == 0) {
	        return "00";
	    }
	    
	    if(num < 10) {
	        return "0" + num;
	    }

	    return String.valueOf(num);
	}
	
	/** return 0 if par1 = true */
	public static int boolean2int(boolean par1) {
		return par1 ? 0 : 1;
	}
	
	/** 3 number find min, if equal, return a->b->c 
	 *  return ordinal number (1,2,3)
	 */
	public static int min(double a, double b, double c) {
		if(a <= b) {		//a <= b
			if(a <= c) {
				return 1;
			}
			else {
				return 3;
			}
		}
		else {				//a >  b
			if(b <= c) {
				return 2;
			}
			else {
				return 3;
			}
		}
	}
	
	/** damage calc by damage type 
	 *  dmg: damage value
	 *  typeAtk: attacker type id
	 *  typeDef: defender type id
	 *  modSet: use which damage modifier setting: 0:day 1:night 2+:no use
	 */
    public static float calcDamageByType(float dmg, int typeAtk, int typeDef, int modSet) {
    	//if type = undefined, return org damage
    	if(typeAtk <= 0 || typeDef <= 0) return dmg;
    	
    	//get damage modifier
    	float mod = 1F;
    	
    	if(modSet > 0) {
    		mod = Values.ModDmgNight[typeAtk-1][typeDef-1];
    	}
    	else {
    		mod = Values.ModDmgDay[typeAtk-1][typeDef-1];
    	}
//    	LogHelper.info("DEBUG : calc helper: org dmg "+dmg+" new dmg "+(dmg*mod));
    	return dmg * mod;
    }
    
    /** damage calc by equip effect: AA, ASM
     *  host: attacker
     *  target: target
     *  dmg: attack damage
     *  type: 0:light 1:heavy 2:nagato 3:yamato
     */
    public static float calcDamageByEquipEffect(IShipAttributes host, Entity target, float dmg, int type) {
    	float newDmg = dmg;
    	float modDmg = 1F;
  		
  		//normal or special attack
  		switch(type) {
  		case 2:   //nagato heavy attack
  			modDmg = 4F;
  			break;
  		case 3:   //yamato heavy attack
  			modDmg = 1.5F;
  			break;
		default:  //normal attack
			modDmg = 1F;
			break;
  		}
  		
  		//check target type
  		int targettype = EntityHelper.checkEntityTypeForEquipEffect(target);
  		
  		if(targettype == 1) {		//air mob
  			newDmg = newDmg + host.getEffectEquip(ID.EF_AA) * modDmg;
  		}
  		else if(targettype == 2) {	//water mob
  			newDmg = newDmg + host.getEffectEquip(ID.EF_ASM) * modDmg;
  		}
  		
  		return newDmg;
    }
    
    /** calc normal distribution
     *  f(x) = 1 / (s*sqrt(2*PI)) * exp(-(x-m)^2/(2*s^2))
     *  s = SD, m = mean
     */
    public static float calcNormalDist(float x, float mean, float sd) {
    	float s1 = 2.5066283F;		// sqrt(2*pi)
    	float s2 = 1F / (sd * s1);
    	float s3 = x - mean;
    	float s4 = -(s3 * s3);
    	float s5 = 2 * sd * sd;
    	float s6 = (float)Math.exp(s4 / s5);
    	
    	return s2 * s6;
    }
    
    /** calc normal distribution by table
     *  x = 0~1999, otherwise = 5%
     */
    public static float getNormDist(int x) {
    	if(x > -1 && x < 2000) {
    		return NORM_TABLE[x];
    	}
    	return NORM_MIN;
    }
    
    /** cut string with new line symbol into string array
     *  new line symbol: <BR><BR/><br><br/>
     */
    public static String[] stringConvNewlineToArray(String str) {
    	String[] strSplit = str.split("<BR>|<BR/>|<br>|<br/>");
    	return strSplit;
    }
    
    /** cut string with new line symbol into string list
     *  new line symbol: <BR><BR/><br><br/>
     */
    public static List<String> stringConvNewlineToList(String str) {
    	List<String> result = new ArrayList();
    	String[] strSplit = stringConvNewlineToArray(str);
    	
    	for(String s : strSplit) {
    		result.add(s);
    	}
    	
    	return result;
    }
    
    /** int[] to List<Integer> */
    public static List<Integer> intArrayToList(int[] iarray) {
    	if(iarray != null && iarray.length > 0) {
    		List<Integer> ilist = new ArrayList();
    		
    		for(int i = 0; i < iarray.length; ++i) {
    			ilist.add(iarray[i]);
    		}
    		
    		return ilist;
    	}
    	
    	return null;
    }
    
    /** List<Integer> to int[] */
    public static int[] intListToArray(List<Integer> ilist) {
    	if(ilist != null  && !ilist.isEmpty()) {
    		int[] iarray = new int[ilist.size()];
    		
    		for(int i = 0; i < ilist.size(); ++i) {
    			iarray[i] = ilist.get(i);
    		}
    		
    		return iarray;
    	}
    	
    	return null;
    }
    
    /** union list */
    public static <T> List<T> listUnion(List<T> list1, List<T> list2) {
    	Set set1 = new HashSet();
    	
    	set1.addAll(list1);
    	set1.addAll(list2);
    	
    	List retlist = new ArrayList(set1);
    	
    	return retlist;
    }
    
    /** abs max(a, b), if a > b, return true */
    public static boolean isAbsGreater(double a, double b) {
    	 if(a < 0D) {
             a = -a;
         }

         if(b < 0D) {
             b = -b;
         }

         return a > b ? true : false;
    }

	/**由XYZ三個向量值計算 [XZ夾角(Yaw), XY夾角(Pitch)], return單位為度數
	 */
	public static float[] getLookDegree(double motX, double motY, double motZ, boolean getDegree) {
		//計算模型要轉的角度 (RAD, not DEG)
	    double f1 = MathHelper.sqrt_double(motX*motX + motZ*motZ);
	    float[] degree = new float[2];
	    
	    degree[1] = -(float)(Math.atan2(motY, f1));
	    degree[0] = -(float)(Math.atan2(motX, motZ));
	
	    if(getDegree) {	//get degree value
	    	degree[0] *= Values.N.RAD_DIV;
	    	degree[1] *= Values.N.RAD_DIV;
	    }
	    
	    return degree;
	}
    
    
    
    
}
