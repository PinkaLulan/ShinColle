package com.lulan.shincolle.utility;

import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Values;
import com.lulan.shincolle.reference.unitclass.Dist4d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**CALC HELPER
 * format and math method
 */
public class CalcHelper
{
	
	//normal table, resolution = 4000, calc half curve only
	public static float[] NORM_TABLE = new float[2000];		//norm: mean = 0.5, sd = 0.2
	private static float NORM_MIN = 0.2F;					//min value in norm table
	
	//init norm table (half curve)
	static
	{
		for (int i = 0; i < 2000; i++)
		{
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
	public static String getTimeFormated(int sec)
	{
		int timeSec = 0;
		int timeMin = 0;
		int timeHr = 0;
		
		timeSec = sec % 60;
		timeMin = (sec % 3600) / 60;
		timeHr = sec / 3600;
		
		return get2Digit(timeHr) + ":" + get2Digit(timeMin) + ":" + get2Digit(timeSec);
	}

	/** 將數字轉為2字母字串, 注意num > 0 */
	public static String get2Digit(int num)
	{
	    if (num == 0)
	    {
	        return "00";
	    }
	    
	    if (num < 10)
	    {
	        return "0" + num;
	    }

	    return String.valueOf(num);
	}
	
	/** return 0 if par1 = true */
	public static int boolean2int(boolean par1)
	{
		return par1 ? 0 : 1;
	}
	
	/** 3 number find min, if equal, return a->b->c 
	 *  return ordinal number (1,2,3)
	 */
	public static int min(double a, double b, double c)
	{
		if (a <= b)
		{
			if (a <= c)
			{
				return 1;
			}
			else
			{
				return 3;
			}
		}
		else
		{
			if (b <= c)
			{
				return 2;
			}
			else
			{
				return 3;
			}
		}
	}
	
    /** calc normal distribution
     *  f(x) = 1 / (s*sqrt(2*PI)) * exp(-(x-m)^2/(2*s^2))
     *  s = SD, m = mean
     */
    public static float calcNormalDist(float x, float mean, float sd)
    {
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
    public static float getNormDist(int x)
    {
    	if (x > -1 && x < 2000)
    	{
    		return NORM_TABLE[x];
    	}
    	
    	return NORM_MIN;
    }
    
    /** cut string with new line symbol into string array
     *  new line symbol: <BR><BR/><br><br/>
     */
    public static String[] stringConvNewlineToArray(String str)
    {
    	String[] strSplit = str.split("<BR>|<BR/>|<br>|<br/>");
    	return strSplit;
    }
    
    /** cut string with new line symbol into string list
     *  new line symbol: <BR><BR/><br><br/>
     */
    public static List<String> stringConvNewlineToList(String str)
    {
    	List<String> result = new ArrayList();
    	String[] strSplit = stringConvNewlineToArray(str);
    	
    	for (String s : strSplit)
    	{
    		result.add(s);
    	}
    	
    	return result;
    }
    
    /** Set<Integer> to int[] */
    public static int[] intSetToArray(Set<Integer> iset)
    {
    	if (iset != null && iset.size() > 0)
    	{
    		int[] iarray = new int[iset.size()];
    		int id = 0;
    		
    		for (int i : iset)
    		{
    			iarray[id] = i;
    			id++;
    		}
    		
    		return iarray;
    	}
    	
    	return new int[] {};
    }
    
    /** int[] to List<Integer> */
    public static ArrayList<Integer> intArrayToList(int[] iarray)
    {
    	if (iarray != null && iarray.length > 0)
    	{
    		ArrayList<Integer> ilist = new ArrayList();
    		
    		for (int i = 0; i < iarray.length; ++i)
    		{
    			ilist.add(iarray[i]);
    		}
    		
    		return ilist;
    	}
    	
    	return new ArrayList();
    }
    
    /** List<Integer> to int[] */
    public static int[] intListToArray(List<Integer> ilist)
    {
    	if (ilist != null  && !ilist.isEmpty())
    	{
    		int[] iarray = new int[ilist.size()];
    		
    		for (int i = 0; i < ilist.size(); ++i)
    		{
    			iarray[i] = ilist.get(i);
    		}
    		
    		return iarray;
    	}
    	
    	return new int[] {};
    }
    
    /** union list */
    public static ArrayList listUnion(ArrayList list1, ArrayList list2)
    {
    	Set set1 = new HashSet();
    	
    	set1.addAll(list1);  //將list1加入set
    	set1.addAll(list2);  //將list2加入set, 因為是hashset, 所以重複項不會加入
    	
    	ArrayList retlist = new ArrayList(set1);  //set轉為list
    	
    	return retlist;
    }
    
    /** abs max(a, b), if a > b, return true */
    public static boolean isAbsGreater(double a, double b)
    {
    	 if(a < 0D)
    	 {
             a = -a;
         }

         if(b < 0D)
         {
             b = -b;
         }

         return a > b ? true : false;
    }

	/**由XYZ三個向量值計算 [XZ夾角(Yaw), XY夾角(Pitch)], return單位為度數
	 */
	public static float[] getLookDegree(double motX, double motY, double motZ, boolean getDegree)
	{
		//normalize
		double d1 = MathHelper.sqrt(motX * motX + motY * motY + motZ * motZ);
		
		if (d1 > 1.0E-4D)
		{
			motX /= d1;
			motY /= d1;
			motZ /= d1;
		}
		
		//計算模型要轉的角度 (RAD, not DEG)
	    double f1 = MathHelper.sqrt(motX*motX + motZ*motZ);
	    float[] degree = new float[2];
	    
	    degree[1] = -(float)(Math.atan2(motY, f1));
	    degree[0] = -(float)(Math.atan2(motX, motZ));
	    
	    if (getDegree)
	    {	//get degree value
	    	degree[0] *= Values.N.DIV_180_PI;
	    	degree[1] *= Values.N.DIV_180_PI;
	    }
	    
	    return degree;
	}
	
	/** get entity hit height by player's sight ray (client player sight)
	 *  return height percent by entity height
	 *  
	 *  calculation:
	 *    x1 = (distance - target.width / 2) * tan(pitch)
	 *    x2 = host.posY + host.eyeHeight - target.posY
	 *    hit height = x2 - x1
	 *    
	 *  note:
	 *    1. too close -> tan approach infinity -> retrurn 110 or -10
	 */
	@SideOnly(Side.CLIENT)
	public static int getEntityHitHeightByClientPlayer(Entity target)
	{
		return getEntityHitHeight(ClientProxy.getClientPlayer(), target);
		
	}
	
	/** get entity hit height by entity's sight ray */
	public static int getEntityHitHeight(Entity host, Entity target)
	{
		int result = 0;
		
		if (target != null && host != null && target.height > 0.1F)
		{
			//calc tan(pitch)
			float x1 = (float) Math.tan(host.rotationPitch * Values.N.DIV_PI_180);
			
			//check tan infinity
			if (x1 > 30) return -10;		//look down inf
			else if (x1 < -30) return 110;	//look up inf
			
			//calc distance
			float dist = host.getDistance(target) - target.width * 0.5F;
			
			//check dist > 0
			if (dist < 0) dist = 0;
			
			x1 *= dist;
			
			float x2 = (float) (host.posY + host.getEyeHeight() - target.posY);
			float x = x2 - x1;
			
			//turn height(float) to percent(int)
			result = (int) (x / target.height * 100F);
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
	public static int getEntityHitSideByClientPlayer(Entity target)
	{
		int result = 0;
		
		if (target != null)
		{
			float angHost = ClientProxy.getClientPlayer().rotationYawHead;
			float angTarget = 0F;
			
			if (target instanceof EntityLivingBase)
			{
				angTarget = ((EntityLivingBase) target).renderYawOffset;
			}
			else
			{
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
	public static int getEntityHitSide(Entity host, Entity target)
	{
		int result = 0;
		
		if (host != null && target != null)
		{
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
	
	/**ROTATE PARTICLE POSITION (NxNxN)
	 * in:原始座標, 邊長, 以及要轉的面向 	out:轉完的新位置
	 * 現階段沒有做上下翻轉, 所以y值不會變動
	 * f = face = DOWN-0  UP-1  NORTH-2  SOUTH-3  WEST-4  EAST-5
	 */
	public static double[] rotateParticleByFace(double x, double y, double z, int f, int len)
	{
		double[] newParm = new double[3];
		newParm[1] = y;
		
		//依照面向, 旋轉原始位置
		switch (f)
		{
		case 5:		//turn east
			newParm[0] = len - z;
			newParm[2] = x;
			break;
		case 3:		//turn south
			newParm[0] = len - x;
			newParm[2] = len - z;
			break;
		case 4:		//turn west
			newParm[0] = z;
			newParm[2] = len - x;
			break;
		default:	//default north, no change
			newParm[0] = x;
			newParm[2] = z;
			break;			
		}
			
		return newParm;
	}
	
	/**ROTATE PARTICLE FOR ENTITY
	 * entity專用的特效位置旋轉方法, 使用yaw跟pitch為參數
	 * 模型是以Z軸正向轉X軸負向為正角度, 且Z軸正向為0度
	 * 
	 * 注意必須先旋轉pitch後才旋轉yaw 否則pitch位移會因為yaw的正負號而跟著變號
	 */
	public static float[] rotateXYZByYawPitch(float x, float y, float z, float yaw, float pitch, float scale)
	{
		float cosYaw = MathHelper.cos(yaw);
		float sinYaw = MathHelper.sin(yaw);
		float cosPitch = MathHelper.cos(-pitch);
		float sinPitch = MathHelper.sin(-pitch);
		float[] newPos = new float[] {x, y, z};

		//計算pitch
		newPos[1] = y * cosPitch + z * sinPitch;
		newPos[2] = z * cosPitch - y * sinPitch;
		
		//計算yaw
		float x2 = newPos[0];
		float z2 = newPos[2];
		newPos[0] = x2 * cosYaw - z2 * sinYaw;
		newPos[2] = z2 * cosYaw + x2 * sinYaw;
		
		//計算scale
		newPos[0] *= scale;
		newPos[1] *= scale;
		newPos[2] *= scale;
		
		return newPos;
	}
	
	/**ROTATE BY AXIS
	 * 針對entity的某一軸做旋轉, 角度單位為RAD
	 * 注意entity前後為Z軸, 左右為X軸
	 */
	public static float[] rotateXZByAxis(float z, float x, float rad, float scale)
	{
		float cosD = MathHelper.cos(rad);
		float sinD = MathHelper.sin(rad);
		float[] newPos = new float[] {0F, 0F};
		
		//計算水平旋轉: X+Z
		newPos[0] = z * cosD + x * sinD;
		newPos[1] = x* cosD - z * sinD;
		
		newPos[0] *= scale;
		newPos[1] *= scale;
		
		return newPos;
	}
	
	/**ROTATE PARTICLE FOR ENTITY
	 * 針對entity的某一軸做旋轉, 以目標的位置為參數
	 */
	public static float[] rotateXZByLook(float x, float y, float lookX, float lookY, float scale)
	{		
		float[] degree = getLookDegree(lookX, 0, lookY, false);
		float cosZdeg = MathHelper.cos(degree[0]);
		float sinZdeg = MathHelper.sin(degree[0]);
		float[] newPos = new float[] {0F, 0F};
		
		//計算水平旋轉: X+Z
		newPos[0] = x * cosZdeg + y * sinZdeg;
		newPos[1] = y* cosZdeg - x * sinZdeg;
		
		newPos[0] *= scale;
		newPos[1] *= scale;
		
		return newPos;
	}
	
	/** check number is NOT in array */
	public static boolean checkIntNotInArray(int target, int[] host)
	{
		if (host == null || host.length <= 0) return true;
		
		for (int i : host)
		{
			if (target == i) return false;
		}
		
		return true;
	}
	
	/** check number is in array */
	public static boolean checkIntInArray(int target, int[] host)
	{
		if (host == null || host.length <= 0) return false;
		
		for (int i : host)
		{
			if (target == i) return true;
		}
		
		return false;
	}
	
	/** remove all B element in A */
	public static int[] arrayRemoveAll(int[] a, int[] b)
	{
		if (a == null || b == null) return new int[] {};
		
		Set<Integer> sa = new HashSet<Integer>();
		
		for (int ia : a)
		{
			sa.add(ia);
		}
		
		for (int ib : b)
		{
			sa.remove(ib);
		}
		
		return intSetToArray(sa);
	}
	
    /**
     * get distance vector from host A to target B (B-A)
     * NOTE: using BlockPos is less precise than entity position
     * 
     * parms: host A, target B
     * return: "normalized" distance vector [x, y, z, distance]
     */
    public static Dist4d getDistanceFromA2B(BlockPos host, BlockPos target)
    {
    	if (host != null && target != null)
    	{
    		return getDistanceFromA2B(new Vec3d(host.getX(), host.getY(), host.getZ()),
    								  new Vec3d(target.getX(), target.getY(), target.getZ()));
    	}
    	
    	return Dist4d.ZERO;
    }
    
    /**
     * get distance vector from host A to target B (B-A)
     * parms: host A, target B
     * return: "normalized" distance vector [x, y, z, distance]
     */
    public static Dist4d getDistanceFromA2B(Entity host, Entity target)
    {
    	if (host != null && target != null)
    	{
    		return getDistanceFromA2B(host.getPositionVector(), target.getPositionVector());
    	}
    	
    	return Dist4d.ZERO;
    }
	
    /**
     * get distance vector from host A to target B (B-A)
     * parms: host A, target B
     * return: "normalized" distance vector [x, y, z, distance]
     */
    public static Dist4d getDistanceFromA2B(Vec3d host, Vec3d target)
    {
    	if (host != null && target != null)
    	{
    		double x = target.x - host.x;
    		double y = target.y - host.y;
    		double z = target.z - host.z;
    		double dist = MathHelper.sqrt(x * x + y * y + z * z);
    		
    		//避免sqrt過小時, 算出的xyz過大
    		if (dist > 1.0E-4D)
            {
                x = x / dist;
                y = y / dist;
                z = z / dist;
                
                return new Dist4d(x, y, z, dist);
            }
    	}
    	
    	return Dist4d.ZERO;
    }
    
    /**
     * get distance vector from host A to target B (B-A)
     * parms: host A, target B
     * return: "normalized" distance vector [x, y, z, distance]
     */
    public static Vec3d getUnitVectorFromA2B(Vec3d from, Vec3d to)
    {
    	if (from != null && to != null)
    	{
    		double x = to.x - from.x;
    		double y = to.y - from.y;
    		double z = to.z - from.z;
    		double dist = MathHelper.sqrt(x * x + y * y + z * z);
    		
    		//避免sqrt過小時, 算出的xyz過大
    		if (dist > 1.0E-4D)
            {
                x = x / dist;
                y = y / dist;
                z = z / dist;
                
                return new Vec3d(x, y, z);
            }
    	}
    	
    	return Vec3d.ZERO;
    }
	
    
}