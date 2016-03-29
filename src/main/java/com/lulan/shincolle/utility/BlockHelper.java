package com.lulan.shincolle.utility;

import java.util.Random;

import com.lulan.shincolle.proxy.ClientProxy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.IFluidBlock;

public class BlockHelper {
	
	private static Random rand = new Random();

	
	public BlockHelper() {}
	
	/** find safe block around target block
	 *  return safe position or origin x,y,z if no safe position
	 */
	public static int[] getSafeBlockWithin5x5(World world, int x, int y, int z) {
		//check block safe within 5x5 (range = 2)
		return getSafeBlockWithinRange(world, x, y, z, 3, 4, 3);
	}
	
	/** get safe block within NxN block 
	 * 
	 *  rangeXZ: 0: 1x1,  1: 3x3,  2: 5x5,  3: 7x7 ...
	 *  
	 *  return safe xyz or null (no safe block)
	 */
	public static int[] getSafeBlockWithinRange(World world, int x, int y, int z, int ranX, int ranY, int ranZ) {
		int[] pos = new int[] {x, y, z};

		//find x2,y2,z2 = 0, 1, -1, 2, -2, 3, -3, ...
		//find block priority: Y > Z > X
		int xlimit = ranX * 2;
		int ylimit = ranY * 2;
		int zlimit = ranZ * 2;
		int x2 = 0;
		int y2 = 0;
		int z2 = 0;
		int x3, y3, z3;
		int addx;
		int addy;
		int addz;
		
		for(int ix = 0; ix <= xlimit; ix++) {
			//calc sequence number
			addx = (ix & 1) == 0 ? ix * -1 : ix;
			x2 += addx;
			
			//reset z2
			z2 = 0;
			
			for(int iz = 0; iz <= zlimit; iz++) {
				//calc sequence number
				addz = (iz & 1) == 0 ? iz * -1 : iz;
				z2 += addz;
				
				//reset y2
				y2 = 0;
				
				for(int iy = 0; iy <= ylimit; iy++) {
					//calc sequence number
					addy = (iy & 1) == 0 ? iy * -1 : iy;
					y2 += addy;
					
					//check block is safe
					x3 = pos[0] + x2;
					y3 = pos[1] + y2;
					z3 = pos[2] + z2;
					
					if(checkBlockSafe(world, x3, y3, z3)) {
						//check block is safe to stand at
						int decY = 0;  //max Y dist below target
						
						while(decY <= MathHelper.abs_int(y2)) {
							if(checkBlockCanStandAt(world.getBlock(x3, y3 - decY - 1, z3))) {
								pos[0] = x3;
								pos[1] = y3 - decY;
								pos[2] = z3;
								return pos;
							}
							
							decY++;
						}
						
//						while(decY <= MathHelper.abs_int(y2)) {
//							if(checkBlockCanStandAt(world.getBlock(x3, y3 - decY - 1, z3))) {
//								pos[0] = x3;
//								pos[1] = y3 - decY;
//								pos[2] = z3;
//								return pos;
//							}
//							LogHelper.info("DEBUG : find block -Y: "+decY);
//							decY++;
//						}
					}//end block is safe
				}//end y
			}//end z
		}//end x
		LogHelper.info("DEBUG : find block fail");
		return null;
	}

	/** check block is safe (not solid block) */
	public static boolean checkBlockSafe(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return checkBlockSafe(block);
	}

	/** check block is safe (not solid block) */
	public static boolean checkBlockSafe(Block block) {
		if(block.getMaterial() == Material.air || block == null || checkBlockIsLiquid(block)) {
			return true;
		}	
		return false;
	}

	/** check block is liquid (not air or solid block) */
	public static boolean checkBlockIsLiquid(Block block) {
	    if(block instanceof BlockLiquid || block instanceof IFluidBlock || block instanceof BlockFluidBase) {
	        return true;
	    }		
		return false;
	}
	
	/** check ship entity can stand at the block */
	public static boolean checkBlockCanStandAt(Block block) {
        if(block != null) {
        	Material mat = block.getMaterial();
        	
        	if(mat != Material.air && mat != Material.fire ) {
        		if(checkBlockIsLiquid(block) ||				//liquid block
	               block.getMaterial().blocksMovement()		//solid block
	               ) {
	            	return true;
	            }
        	}
        }
        
        return false;
	}

	/**find random position with block check
	//mode 0: find y = Y+1 ~ Y+3 and XZ at side of target
	//mode 1: find y = Y-2 ~ Y+2 (NYI)
	 */
	public static double[] findRandomPosition(Entity host, Entity target, double minDist, double randDist, int mode) {
		Block findBlock = null;
		double[] newPos = new double[] {0D, 0D, 0D};
		
		//try 25 times
		for(int i = 0; i < 25; i++) {
			switch(mode) {
			case 0:	 //隨機選擇目標周圍四個象限
				//find side position
				newPos[0] = rand.nextDouble() * randDist + minDist;	//ran = min + randN
				newPos[1] = rand.nextDouble() * randDist + target.posY + 1D;
				newPos[2] = rand.nextDouble() * randDist + minDist;
	
				switch(rand.nextInt(4)) {
				case 0:
					newPos[0] = target.posX + newPos[0];
					newPos[2] = target.posZ - newPos[2];
					break;
				case 1:
					newPos[0] = target.posX - newPos[0];
					newPos[2] = target.posZ + newPos[2];
					break;
				case 2:
					newPos[0] = target.posX - newPos[0];
					newPos[2] = target.posZ - newPos[2];
					break;
				case 3:
					newPos[0] = target.posX + newPos[0];
					newPos[2] = target.posZ + newPos[2];
					break;
				}//end inner switch
				break;
			case 1:  //繞背法, 隨機選擇背面兩個象限
				//find side position
				newPos[0] = rand.nextDouble() * randDist + minDist;	//ran = min + randN
				newPos[1] = rand.nextDouble() * randDist + target.posY - 1D;
				newPos[2] = rand.nextDouble() * randDist + minDist;
				
				//get direction
				double dx = host.posX - target.posX;
				double dz = host.posZ - target.posZ;
				
				if(dx > 0) {
					newPos[0] = target.posX - newPos[0];
				}
				else {
					newPos[0] = target.posX + newPos[0];
				}
				
				if(dz > 0) {
					newPos[2] = target.posZ - newPos[2];
				}
				else {
					newPos[2] = target.posZ - newPos[2];
				}
				break;
			case 2:  //直線前進法, 依照移動方向繼續往前
				//find side position
				newPos[0] = rand.nextDouble() * randDist + minDist;	//ran = min + randN
				newPos[1] = rand.nextDouble() * randDist + target.posY + 1D;
				newPos[2] = rand.nextDouble() * randDist + minDist;
				
				if(host.motionX < 0) {
					newPos[0] = target.posX - newPos[0];
				}
				else {
					newPos[0] = target.posX + newPos[0];
				}
				
				if(host.motionZ < 0) {
					newPos[2] = target.posZ - newPos[2];
				}
				else {
					newPos[2] = target.posZ + newPos[2];
				}
				break;
			}//end mode switch
			
			//check block
			findBlock = host.worldObj.getBlock((int)newPos[0], (int)newPos[1], (int)newPos[2]);
			if(findBlock != null && (findBlock == Blocks.air || findBlock == Blocks.water)) {
				return newPos;
			}	
		}
		
		//find block fail, return target position
		newPos[0] = target.posX;
		newPos[1] = target.posY + 2D;
		newPos[2] = target.posZ;
		
		return newPos;
	}

	/**ray trace for block, include liquid block
	 * 
	 */
	@SideOnly(Side.CLIENT)
	public static MovingObjectPosition getPlayerMouseOverBlock(double dist, float duringTicks) {
	    EntityPlayer player = ClientProxy.getClientPlayer();
		
		Vec3 vec3 = player.getPosition(duringTicks);
	    Vec3 vec31 = player.getLook(duringTicks);
	    Vec3 vec32 = vec3.addVector(vec31.xCoord * dist, vec31.yCoord * dist, vec31.zCoord * dist);
	    
	    //func_147447_a(所有視線追蹤皆用此方法) 參數: entity位置, entity視線最遠位置, 是否抓液體方塊, ??, 距離內沒抓到任何東西回傳MISS(而不是回傳null)
	    return player.worldObj.func_147447_a(vec3, vec32, true, false, false);
	}
	
	
	
}
