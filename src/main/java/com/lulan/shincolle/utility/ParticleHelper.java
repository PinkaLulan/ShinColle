package com.lulan.shincolle.utility;

import net.minecraft.world.World;

/**粒子特效處理class
 * 包含呼叫特效, 旋轉特效位置(NxNxN旋轉), 
 */
public class ParticleHelper {
	
	/**in:原始座標, 邊長, 以及要轉的面向 	out:轉完的新位置
	 * 現階段沒有做上下翻轉, 所以y值不會變動
	 * f = face = 0,4:north  1,5:east  2,6:south  3,7:west
	 */
	public static double[] getNewPosition(double x, double y, double z, int f, int len) {
		
		double[] newParm = new double[3];
		newParm[1] = y;
		
		//依照面向, 旋轉原始位置
		switch(f) {
		case 1:		//turn east
		case 5:
			newParm[0] = (double)len - z;
			newParm[2] = x;
			break;
		case 2:		//turn south
		case 6:
			newParm[0] = (double)len - x;
			newParm[2] = (double)len - z;
			break;
		case 3:		//turn west
		case 7:
			newParm[0] = z;
			newParm[2] = (double)len - x;
			break;
		default:	//default north, no change
			newParm[0] = x;
			newParm[2] = z;
			break;			
		}
			
		return newParm;
	}

}
