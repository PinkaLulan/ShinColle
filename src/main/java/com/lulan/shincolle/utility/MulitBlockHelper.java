package com.lulan.shincolle.utility;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.tileentity.BasicTileMulti;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MulitBlockHelper
{
	
	/**pattern array [type][x][y][z]
	 * type:  0:large shipyard  1:large workshop
	 * value: -1:other  0:water,air  1:polymetal  2:grudge
	 * 
	 * TYPE 0001 - Large Shipyard:  o:polymetal block  g:heavy grudge block
	 *      1.ooo   2.o o   4.
	 *        ooo               g
	 *        ooo     o o
	 * 
	 * NYI: TYPE 0010 - Large Workshop:  o:polymetal block  g:heavy grudge block
	 *      1.o o   2.o o   3. ooo
	 *                         ogo
	 *        o o     o o      ooo
	 */
	private static final byte[][][][] PATTERN =
	{
		//type 0001:
		{	   //y = 0       y = 1       y = 2
			{  { 1, 1, 1}, { 1,-1, 1}, {-1,-1,-1}  },	//x = 0
		    {  { 1, 1, 1}, {-1,-1,-1}, {-1, 2,-1}  },	//x = 1
		    {  { 1, 1, 1}, { 1,-1, 1}, {-1,-1,-1}  }	//x = 2
		}
		
//		//type 0010:
//		{      //y = 0       y = 1       y = 2 
//		    {  { 1, 1, 1}, {-1,-1,-1}, {-1,-1,-1}  },	//x = 0
//		    {  { 1, 1, 1}, {-1, 1,-1}, {-1, 2,-1}  },	//x = 1
//		    {  { 1, 1, 1}, {-1,-1,-1}, {-1,-1,-1}  }	//x = 2
//		}		
	};
	
	public void MultiBlockHelper() {}
	
	public static void printPattern()
	{
		LogHelper.info("INFO: PATTERN len "+PATTERN.length+" "+PATTERN[0].length+" "+PATTERN[0][0].length+" "+PATTERN[0][0][0].length);
		
		for (int i = 0; i < PATTERN.length; i++)
		{
			LogHelper.info("INFO: PATTERN TYPE "+i);
			for (int x = 0; x < PATTERN[i].length; x++)
			{
				LogHelper.info("INFO: PATTERN X = "+x);
				for (int y = PATTERN[i][x].length - 1; y >= 0; y--)
				{
					LogHelper.info("INFO: PATTERN   "+PATTERN[i][x][y][2]+","+PATTERN[i][x][y][1]+","+PATTERN[i][x][y][0]);
				}
			}
		}
	}
	
	/**CHECK MULTI BLOCK FORM
	 * called when RIGHT CLICK heavy grudge block
	 * (heavy grudge block is always at TOP-MIDDLE, so check X+-1 Y-2 Z+-1)
	 */
	public static int checkMultiBlockForm(World world, int xCoord, int yCoord, int zCoord)
	{
		IBlockState state = null;
		BlockPos pos = null;
		Block block = null;
		int blockType = -1;
		/** bitwise pattern match
		 *  ex: type = 3 (int) = 0011 (bit) = match pattern 0,1
		 *      type = 2 (int) = 0010 (bit) = match pattern 1
		 *      type = 13(int) = 1101 (bit) = match pattern 0,2,3
		 */
		int patternTemp = 0;
		int patternMatch = 1;  //init match pattern = 0001 (bit)
	    
		//高度3以下不須偵測
		if (yCoord < 3) return -1;
		
	    //scan a 3x3x3 area
		//1.檢查block並設定type 2.判定是否符合pattern 3.檢查tile entity是否有master
	    for (int x = 0; x < 3; x++)
	    {
	    	for (int y = 0; y < 3; y++)
	    	{
	    		for (int z = 0; z < 3; z++)
	    		{
	    			pos = new BlockPos(xCoord - 1 + x, yCoord - 2 + y, zCoord - 1 + z);
	    			
	    			//1. get block
	    			state = world.getBlockState(pos);
	    			if (state != null) 
    				{
	    				block = state.getBlock();
    				}
	    			else
	    			{
	    				block = null;
	    			}
	    			
	    			blockType = -1;
	    			if (block != null)
	    			{
		    			if (block == ModBlocks.BlockPolymetal) blockType = 1;
		    			if (block == ModBlocks.BlockGrudgeHeavy) blockType = 2;
		    			LogHelper.debug("DEBUG: multi block check: pos "+pos.getX()+" "+pos.getY()+" "+pos.getZ()+" "+block.getLocalizedName()+" "+blockType);
	    			}
	    			
	    			//2. match pattern
	    			patternTemp = 0;
	    			for (int t = 0; t < PATTERN.length; t++)
	    			{
	    				if (blockType == PATTERN[t][x][y][z])
	    				{
	    					patternTemp += Math.pow(2, t);		//match pattern t
	    				}
	    			}
	    			patternMatch = (patternMatch & patternTemp);		//進行and運算, 刪去不符合的type
	    			
	    			LogHelper.debug("DEBUG: check structure: type "+patternMatch+" "+patternTemp);
	    			if (patternMatch == 0) return -1;	//全部pattern都被濾掉, 無符合, 結束檢查
	    			
	    			//3. check master block, 無主方塊才能加入, 有主方塊則結束檢查
	    			if (blockType > 0)
    				{
	    				TileEntity t = world.getTileEntity(pos);
	    				if (t instanceof BasicTileMulti && ((BasicTileMulti) t).hasMaster())
	    				{
	    					return -1;
	    				}
    				}
	    			
	            }//end z for
	        }//end y for
	    }//end x for
	    
	    LogHelper.debug("DEBUG: check structure: type "+patternMatch);
	    return patternMatch;
	}
	
	/** setup multi block struct
	 * 
	 *  input: world, masterX, masterY, masterZ, structure type
	 *  
	 *  type: 0:no MBS, 1:large shipyard, 2:-
	 */
	public static void setupStructure(World world, int xCoord, int yCoord, int zCoord, int type)
	{
		List<BasicTileMulti> tiles = new ArrayList<BasicTileMulti>();  //all tile in structure
		BlockPos pos = null;
		BlockPos masterPos = new BlockPos(xCoord, yCoord, zCoord);
		BasicTileMulti masterTile = null;  //master tile
		BasicTileMulti tile2 = null;
		TileEntity tile = null;
		LogHelper.debug("DEBUG: setup structure type: "+type);
		
		//get all tile and master tile
		for (int x = xCoord - 1; x < xCoord + 2; x++)
		{
	        for (int y = yCoord - 2; y < yCoord + 1; y++)
	        {
	            for (int z = zCoord - 1; z < zCoord + 2; z++)
	            {
	            	pos = new BlockPos(x, y, z);
	                tile = world.getTileEntity(pos);
	                
	                // Check if block is master or servant
	                boolean mflag = (x == xCoord && y == yCoord && z == zCoord);
	                
	                if (tile instanceof BasicTileMulti)
	                {
	                	tile2 = (BasicTileMulti) tile;
	                	
	                	tiles.add(tile2);
	                	tile2.setIsMaster(mflag);
	                	tile2.setHasMaster(true);
	                	tile2.setStructType(type, world);
	                	tile2.setMasterCoords(masterPos);
	                	
	                	if (mflag)
	                	{
	                		masterTile = tile2;
	                	}
	                }
	            }//end z loop
	        }//end y loop
	    }//end x loop
		
		//set master value
		for (BasicTileMulti te : tiles)
		{
			te.setMaster(masterTile);
		}
		
	}

	//reset(remove) tile multi
	private static void resetTileMulti(BasicTileMulti parTile)
	{
		parTile.setMasterCoords(BlockPos.ORIGIN);
		parTile.setMaster(null);
		parTile.setHasMaster(false);
		parTile.setIsMaster(false);
		parTile.setStructType(0, parTile.getWorld());
		
	}
	
	//Reset tile multi, called from master block if struct broken
	public static void resetStructure(World world, int xCoord, int yCoord, int zCoord)
	{
		LogHelper.debug("DEBUG: reset struct: client? "+world.isRemote+" "+xCoord+" "+yCoord+" "+zCoord);
		
		for (int x = xCoord - 1; x < xCoord + 2; x++)
		{
			for (int y = yCoord - 2; y < yCoord + 1; y++)
			{
				for (int z = zCoord - 1; z < zCoord + 2; z++)
				{
	            	TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
	            	
	            	if (tile instanceof BasicTileMulti) resetTileMulti((BasicTileMulti)tile);
	            }
	        }
	    }

	}


}
