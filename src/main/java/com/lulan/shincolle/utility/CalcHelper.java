package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

import com.lulan.shincolle.entity.IShipAttributes;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.reference.Values;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


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
	
	/** 將tick轉為秒 or 分字串 */
	//convert wp stay time to string
  	public static String tick2SecOrMin(int ticks)
  	{
  		int t = (int) (ticks * 0.05F);
  		
  		if (t >= 60)
  		{
  			t *= 0.016666667F;
  			return String.valueOf(t) + "m";
  		}
  		else
  		{
  			return String.valueOf(t) + "s";
  		}
  	}
	
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
    
    /** damage calc by special effect: AA, ASM
     *  host: attacker
     *  target: target
     *  dmg: attack damage
     *  type: 0:light 1:heavy 2:nagato 3:yamato
     */
    public static float calcDamageBySpecialEffect(IShipAttributes host, Entity target, float dmg, int type) {
    	float newDmg = dmg;
    	float modEffect = 1F;
  		
  		//normal or special attack
  		switch(type) {
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
  		int targettype = EntityHelper.checkEntityTypeForEquipEffect(target);
  		
  		if(targettype == 1) {		//air mob
  			newDmg = newDmg + host.getEffectEquip(ID.EF_AA) * modEffect;
  		}
  		else if(targettype == 2) {	//water mob
  			newDmg = newDmg + host.getEffectEquip(ID.EF_ASM) * modEffect;
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
    	
    	return new ArrayList();
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
    	
    	return new int[] {};
    }
    
    /** union list */
    public static <T> List<T> listUnion(List<T> list1, List<T> list2) {
    	Set set1 = new HashSet();
    	
    	set1.addAll(list1);  //將list1加入set
    	set1.addAll(list2);  //將list2加入set, 因為是hashset, 所以重複項不會加入
    	
    	List retlist = new ArrayList(set1);  //set轉為list
    	
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
	
	/** get entity hit height by player's sight ray (client player sight)
	 * 
	 */
	@SideOnly(Side.CLIENT)
	public static int getEntityHitHeightByClientPlayer(Entity target) {
		int result = 0;
		
		if(target != null) {
			float eyeH = 1.62F;  //normal player eye height
			
			//calc player eye height
			EntityPlayer player = ClientProxy.getClientPlayer();
			
			if(player.getEyeHeight() != 0.12F) {  //is morph player
				eyeH = (float) (player.boundingBox.maxY - player.boundingBox.minY + player.getEyeHeight());
			}
			
			//calc sight ray (host to target) length
			float dx = (float) (player.posX - target.posX);
			float dz = (float) (player.posZ - target.posZ);
			float rayLen = MathHelper.sqrt_float(dx * dx + dz * dz + eyeH * eyeH);
			rayLen -= target.width;
			rayLen = rayLen * MathHelper.sin(player.rotationPitch * Values.N.RAD_MUL);
			
			//calc hit height
			float hitHeight = (float) (player.boundingBox.minY + eyeH - target.boundingBox.minY - rayLen);
			
			//hit height convert to percentage
			if(hitHeight > 0F && hitHeight <= target.height * 1.5F) {
				result = (int) (hitHeight / target.height * 100F);
			}
			else {
				result = 0;  //not hit, error
			}
		}
		
		return result;
	}
	
	/** get entity hit height by entity's sight ray */
	public static int getEntityHitHeight(Entity host, Entity target) {
		int result = 0;
		
		if(host != null && target != null) {
			//get random eye height
			float eyeH = host.height * (host.worldObj.rand.nextFloat() * 0.5F + 0.5F);
			
			//calc sight ray (host to target) length
			float dx = (float) (host.posX - target.posX);
			float dz = (float) (host.posZ - target.posZ);
			float rayLen = MathHelper.sqrt_float(dx * dx + dz * dz + eyeH * eyeH);
			rayLen -= target.width;
			rayLen = rayLen * MathHelper.sin(host.rotationPitch * Values.N.RAD_MUL);
			
			//calc hit height
			float hitHeight = (float) (host.boundingBox.minY + eyeH - target.boundingBox.minY - rayLen);
			
			//hit height convert to percentage
			if(hitHeight > 0F && hitHeight <= target.height * 1.5F) {
				result = (int) (hitHeight / target.height * 100F);
			}
			else {
				result = 0;  //not hit, error
			}
		}
		
		return result;
	}
    
	/** calc entity hit side
	 * 
	 *  front: 180
	 *  back:  0
	 *  right: 270
	 *  left:  90
	 */
	@SideOnly(Side.CLIENT)
	public static int getEntityHitSideByClientPlayer(Entity target) {
		int result = 0;
		
		if(target != null) {
			float angHost = ClientProxy.getClientPlayer().rotationYawHead;
			float angTarget = 0F;
			
			if(target instanceof EntityLivingBase) {
				angTarget = ((EntityLivingBase) target).renderYawOffset;
			}
			else {
				angTarget = target.rotationYaw;
			}
			
			result = (int) ((angHost % 360 - angTarget % 360) % 360);
			
			if(result < 0) result += 360;
		}

		return result;
	}
	
	/** calc entity hit side
	 * 
	 *  front: 180
	 *  back:  0
	 *  right: 270
	 *  left:  90
	 */
	public static int getEntityHitSide(Entity host, Entity target) {
		int result = 0;
		
		if(host != null && target != null) {
			result = (int) ((host.rotationYaw % 360 - target.rotationYaw % 360) % 360);
			
			if(result < 0) result += 360;
		}

		return result;
	}
	
	/** combine int arrays */
	public static int[] concIntArray(int[] a, int[] b)
	{
		int[] i = new int[a.length + b.length];
		
		System.arraycopy(a, 0, i, 0, a.length);
		System.arraycopy(b, 0, i, a.length, b.length);
		
		return i;
	}
    
    
}
