package com.lulan.shincolle.utility;

import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.lulan.shincolle.proxy.ClientProxy;

public class BlockHelper
{
	
	private static Random rand = new Random();

	
	public BlockHelper() {}
	
	/** find safe block around target block
	 *  return safe position or origin x,y,z if no safe position
	 */
	public static int[] getSafeBlockWithin5x5(World world, int x, int y, int z)
	{
		//check block safe within 5x5 (range = 2)
		return getSafeBlockWithinRange(world, x, y, z, 3, 4, 3);
	}
	
	/** get safe block within NxN block
	 * 
	 *  rangeXZ: 0: 1x1,  1: 3x3,  2: 5x5,  3: 7x7 ...
	 *  
	 *  return safe xyz or null (no safe block)
	 */
	public static int[] getSafeBlockWithinRange(World world, int x, int y, int z, int ranX, int ranY, int ranZ)
	{
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
		
		for (int ix = 0; ix <= xlimit; ix++)
		{
			//calc sequence number
			addx = (ix & 1) == 0 ? ix * -1 : ix;
			x2 += addx;
			
			//reset z2
			z2 = 0;
			
			for (int iz = 0; iz <= zlimit; iz++)
			{
				//calc sequence number
				addz = (iz & 1) == 0 ? iz * -1 : iz;
				z2 += addz;
				
				//reset y2
				y2 = 0;
				
				for (int iy = 0; iy <= ylimit; iy++)
				{
					//calc sequence number
					addy = (iy & 1) == 0 ? iy * -1 : iy;
					y2 += addy;
					
					//check block is safe
					x3 = pos[0] + x2;
					y3 = pos[1] + y2;
					z3 = pos[2] + z2;
					
					if (checkBlockSafe(world, x3, y3, z3))
					{
						//check block is safe to stand at
						int decY = 0;  //max Y dist below target
						
						while (decY <= MathHelper.abs_int(y2))
						{
							if (checkBlockCanStandAt(world.getBlockState(new BlockPos(x3, y3 - decY - 1, z3))))
							{
								pos[0] = x3;
								pos[1] = y3 - decY;
								pos[2] = z3;
								return pos;
							}
							
							decY++;
						}
					}//end block is safe
				}//end y
			}//end z
		}//end x
		
		LogHelper.info("DEBUG : find block fail");
		return null;
	}

	/** check block is safe (not solid block) */
	public static boolean checkBlockSafe(World world, int x, int y, int z)
	{
		IBlockState block = world.getBlockState(new BlockPos(x, y, z));
		return checkBlockSafe(block);
	}

	/** check block is safe (not solid block) */
	public static boolean checkBlockSafe(IBlockState block)
	{
		if (block == null || block.getMaterial() == Material.AIR || checkBlockIsLiquid(block))
		{
			return true;
		}
		
		return false;
	}

	/** check block is liquid (not air or solid block) */
	public static boolean checkBlockIsLiquid(IBlockState block)
	{
	    if (block != null && (block instanceof IFluidBlock || block instanceof BlockLiquid || block.getMaterial().isLiquid()))
	    {
	        return true;
	    }
	    
		return false;
	}
	
	/** check nearby block is liquid */
	public static boolean checkBlockNearbyIsLiquid(World world, int x, int y, int z, int range)
	{
		for (int ix = -range; ix <= range; ix++)
		{
			for (int iy = -range; iy <= range; iy++)
			{
				for (int iz = -range; iz <= range; iz++)
				{
					if (checkBlockIsLiquid(world.getBlockState(new BlockPos(x+ix, y+iy, z+iz))))
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/** check ship entity can stand at the block */
	public static boolean checkBlockCanStandAt(IBlockState block)
	{
        if (block != null)
        {
        	Material mat = block.getMaterial();
        	
        	if (mat != Material.AIR && mat != Material.FIRE )
        	{
        		if (checkBlockIsLiquid(block) ||	block.getMaterial().blocksMovement())
        		{
	            	return true;
	            }
        	}
        }
        
        return false;
	}

	/**find random position with block check
	 * mode 0: 目標周圍四象限隨機
	 * mode 1: 往目標背後兩個象限
	 * mode 2: 直線前進
	 */
	public static double[] findRandomPosition(Entity host, Entity target, double minDist, double randDist, int mode)
	{
		double[] newPos = new double[] {0D, 0D, 0D};
		
		//try 25 times
		for (int i = 0; i < 25; i++)
		{
			switch (mode)
			{
			case 0:	 //隨機選擇目標周圍四個象限
				//find side position
				newPos[0] = rand.nextDouble() * randDist + minDist;	//ran = min + randN
				newPos[1] = rand.nextDouble() * randDist + target.posY + 1D;
				newPos[2] = rand.nextDouble() * randDist + minDist;
	
				switch (rand.nextInt(4))
				{
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
				newPos[1] = rand.nextDouble() * randDist + target.posY + 0.5D;
				newPos[2] = rand.nextDouble() * randDist + minDist;
				
				//get direction
				double dx = host.posX - target.posX;
				double dz = host.posZ - target.posZ;
				
				if (dx > 0)
				{
					newPos[0] = target.posX - newPos[0];
				}
				else
				{
					newPos[0] = target.posX + newPos[0];
				}
				
				if (dz > 0)
				{
					newPos[2] = target.posZ - newPos[2];
				}
				else
				{
					newPos[2] = target.posZ - newPos[2];
				}
				break;
			case 2:  //直線前進法, 依照移動方向繼續往前
				//find side position
				newPos[0] = rand.nextDouble() * randDist + minDist;	//ran = min + randN
				newPos[1] = rand.nextDouble() * randDist + target.posY + 1D;
				newPos[2] = rand.nextDouble() * randDist + minDist;
				
				if (host.motionX < 0)
				{
					newPos[0] = target.posX - newPos[0];
				}
				else
				{
					newPos[0] = target.posX + newPos[0];
				}
				
				if (host.motionZ < 0)
				{
					newPos[2] = target.posZ - newPos[2];
				}
				else
				{
					newPos[2] = target.posZ + newPos[2];
				}
				break;
			}//end mode switch
			
			//check block safe
			if (checkBlockSafe(host.worldObj.getBlockState(new BlockPos((int)newPos[0], (int)newPos[1], (int)newPos[2]))))
			{
				return newPos;
			}	
		}
		
		//find block fail, return target position
		newPos[0] = target.posX;
		newPos[1] = target.posY + 2D;
		newPos[2] = target.posZ;
		
		return newPos;
	}

	/** ray trace for block, include liquid block
	 * 
	 */
	@SideOnly(Side.CLIENT)
	public static RayTraceResult getPlayerMouseOverBlock(double dist, float duringTicks)
	{
	    EntityPlayer player = ClientProxy.getClientPlayer();
		
		Vec3d vec3 = player.getPositionEyes(duringTicks);
	    Vec3d vec31 = player.getLook(duringTicks);
	    Vec3d vec32 = vec3.addVector(vec31.xCoord * dist, vec31.yCoord * dist, vec31.zCoord * dist);
	    
	    //參數: entity位置, entity視線最遠位置, 停止在液體方塊上, 忽略沒有AABB的方塊, 回傳距離內最遠的不可碰撞方塊
	    return player.worldObj.rayTraceBlocks(vec3, vec32, true, false, false);
	}
	
//	/** place light block */ TODO
//	public static void placeLightBlock(World world, int x, int y, int z)
//	{
//		//swarch space to place light block
//		for (int i = -1; i <= 1; i++)
//		{
//			for (int j = 1; j <= 2; j++)
//			{
//				for (int k = -1; k <= 1; k++)
//				{
//					Block b = world.getBlock(x+i, y+j, z+k);
//					
//					if (b == Blocks.water)
//					{
//						world.setBlock(x+i, y+j, z+k, ModBlocks.BlockLightFluid);
//						return;
//					}
//					else if (b == Blocks.air)
//					{
//						world.setBlock(x+i, y+j, z+k, ModBlocks.BlockLightAir);
//						return;
//					}
//					else if (b == ModBlocks.BlockLightFluid)
//					{
//						TileEntity te = world.getTileEntity(x+i, y+j, z+k);
//						
//						//renew lifespan
//						if (te instanceof TileEntityLightBlock)
//						{
//							((TileEntityLightBlock) te).tick = 1;
//							return;
//						}
//					}
//				}
//			}
//		}
//	}
//	
//	/** reset nearby light block lifespan */
//	public static void updateNearbyLightBlock(World world, int x, int y, int z)
//	{
//		for (int i = -1; i <= 1; i++)
//		{
//			for (int j = 1; j <= 2; j++)
//			{
//				for (int k = -1; k <= 1; k++)
//				{
//					TileEntity te = world.getTileEntity(x+i, y+j, z+k);
//					
//					//renew lifespan
//					if (te instanceof TileEntityLightBlock)
//					{
//						((TileEntityLightBlock) te).tick = 1;
//						return;
//					}
//				}
//			}
//		}
//	}
	
	/** get chunks within range
	 *  
	 *  input: posX, posZ
	 *  mode: 1:1 chunk, 2:3x3 chunks
	 */
	public static HashSet<ChunkPos> getChunksWithinRange(World world, int x, int z, int mode)
	{
		HashSet<ChunkPos> chunks = new HashSet<ChunkPos>();
		
		//get chunks by mode
		switch (mode)
		{
		case 1:  //1 chunk
			chunks.add(new ChunkPos(x, z));
			break;
		case 2:  //3x3 chunks
			for (int i = -1; i <= 1; i++)
			{
				for (int j = -1; j <= 1; j++)
				{
					chunks.add(new ChunkPos(x+i, z+j));
				}
			}
			break;
		}
		
		return chunks;
	}
	
	/** get toppest water height */
	public static int getToppestWaterHeight(World world, int x, int y, int z)
	{
		int cy = y + 1;
		IBlockState b = world.getBlockState(new BlockPos(x, y, z));
		
		if (checkBlockIsLiquid(b))
		{
			while (cy < 255)
			{
				b = world.getBlockState(new BlockPos(x, cy, z));
				
				if(checkBlockIsLiquid(b))
				{
					cy++;
					continue;
				}
				else
				{
					break;
				}
			}
		}
		
		return cy--;
	}
	
	/** check is openable door
	 *  ex: trapdoor, wooddoor
	 */
	public static boolean isOpenableDoor(IBlockState block)
	{
		if (block != null)
		{
			Material mat = block.getMaterial();
			
			if (block == Blocks.TRAPDOOR || block instanceof BlockFence)
			{
				return true;
			}
			
			if (block instanceof BlockDoor)
			{
				if (mat == Material.WOOD) return true;
			}
		}
		
		return false;
	}
	
	
}
