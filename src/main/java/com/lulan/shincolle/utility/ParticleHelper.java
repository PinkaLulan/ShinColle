package com.lulan.shincolle.utility;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**粒子特效處理class
 * 包含呼叫特效, 旋轉特效位置(NxNxN旋轉), 
 */
public class ParticleHelper {
	
	private static World world;
	/**ROTATE PARTICLE POSITION (NxNxN)
	 * in:原始座標, 邊長, 以及要轉的面向 	out:轉完的新位置
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
	
	/**SPAWN ATTACK PARTICLE 
	 * 
	 */
	public static void spawnAttackParticle(Entity target, byte type) {
		//get target position
		if(target != null) {
			double tarX = target.posX;
			double tarY = target.posY;
			double tarZ = target.posZ;
			world = target.worldObj;
			
			//spawn particle
			switch(type) {
			case 0:	//explode
				world.spawnParticle("explode", tarX, tarY+2, tarZ, 0.0D, 0.0D, 0.0D);
				break;
			case 1: //largeexplode
				world.spawnParticle("largeexplode", tarX, tarY+2, tarZ, 0.0D, 0.0D, 0.0D);
				break;
			case 2: //hugeexplosion
				world.spawnParticle("hugeexplosion", tarX, tarY+1, tarZ, 0.0D, 0.0D, 0.0D);
				break;
			case 3: //crit
				world.spawnParticle("crit", tarX, tarY+2, tarZ, 0.0D, 0.0D, 0.0D);
				break;
			case 4: //magicCrit
				world.spawnParticle("magicCrit", tarX, tarY+2, tarZ, 0.0D, 0.0D, 0.0D);
				break;
			case 5: //smoke
				world.spawnParticle("smoke", tarX, tarY+2, tarZ, 0.0D, 0.0D, 0.0D);
				break;
			case 6: //largesmoke
				world.spawnParticle("largesmoke", tarX, tarY+2, tarZ, 0.0D, 0.0D, 0.0D);
				break;
			case 7: //angryVillager
				world.spawnParticle("angryVillager", tarX, tarY+1, tarZ, 0.0D, 0.0D, 0.0D);
				break;
			case 8: //flame
				world.spawnParticle("flame", tarX, tarY+2, tarZ, 0.0D, 0.0D, 0.0D);
				break;
			case 9: //lava+explode
				world.spawnParticle("largeexplode", tarX, tarY+1.5, tarZ, 0.0D, 0.0D, 0.0D);
				for(int i=0;i<5;i++) {
					world.spawnParticle("lava", tarX, tarY+1, tarZ, 0.0D, 0.0D, 0.0D);
				}			
				break;
			default:
				break;		
			}
		}		
	}
	
	
}
