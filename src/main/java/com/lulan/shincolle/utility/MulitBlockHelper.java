package com.lulan.shincolle.utility;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.lulan.shincolle.entity.renderentity.BasicRenderEntity;
import com.lulan.shincolle.entity.renderentity.EntityRenderLargeShipyard;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.tileentity.BasicTileMulti;

public class MulitBlockHelper {
	
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
	private static final int NUM_PATTERN = 3;	//2 pattern for now = 0011 = 3
	private static final byte[][][][] PATTERN = {
		  //type 0001:
		  {  //  y = 0      y = 1      y = 2
		    {  { 1, 1, 1}, { 1,-1, 1}, {-1,-1,-1}  },	//x = 0
		    {  { 1, 1, 1}, {-1,-1,-1}, {-1, 2,-1}  },	//x = 1
		    {  { 1, 1, 1}, { 1,-1, 1}, {-1,-1,-1}  }	//x = 2
		  },
		  
		  //type 0010:
		  {  //  y = 0      y = 1      y = 2 
		    {  { 1, 1, 1}, {-1,-1,-1}, {-1,-1,-1}  },	//x = 0
		    {  { 1, 1, 1}, {-1, 1,-1}, {-1, 2,-1}  },	//x = 1
		    {  { 1, 1, 1}, {-1,-1,-1}, {-1,-1,-1}  }	//x = 2
		  }		
		};
	
	public void MultiBlockHelper() {}
	
	public static void printPattern() {
		LogHelper.info("DEBUG : PATTERN len "+PATTERN.length+" "+PATTERN[0].length+" "+PATTERN[0][0].length+" "+PATTERN[0][0][0].length);
		for(int i = 0; i < PATTERN.length; i++) {
			LogHelper.info("DEBUG : PATTERN TYPE "+i);
			for(int x = 0; x < PATTERN[i].length; x++) {
				LogHelper.info("DEBUG : PATTERN X = "+x);
				for(int y = PATTERN[i][x].length - 1; y >= 0; y--) {
					LogHelper.info("DEBUG : PATTERN   "+PATTERN[i][x][y][2]+","+PATTERN[i][x][y][1]+","+PATTERN[i][x][y][0]);
				}
			}
		}
	}
	
	/**CHECK MULTI BLOCK FORM
	 * called when RIGHT CLICK or PLACE heavy grudge block
	 * (heavy grudge block is always at top layer, so check X+-1 Y-2 Z+-1)
	 */
	public static int checkMultiBlockForm(World world, int xCoord, int yCoord, int zCoord) {
		BasicTileMulti tile = null;
		Block block = null;
		int code = -1;
		//bitwise pattern match ex: 1011 = match pattern 0,1,3
		//init 15 = 1111
		int typeTemp = 0;
		int typeMatch = NUM_PATTERN;
		int checkX, checkY, checkZ;		//檢查block的位置
	    
		//高度4以下不須偵測
		if(yCoord < 4) return -1;
		
	    //scan a 3x3x3 area
		//1.檢查block並設定代號(code) 2.判定是否符合pattern 3.檢查tile entity是否有master
	    for(int x = 0; x < 3; x++) {
	    	for(int y = 0; y < 3; y++) {
	    		for(int z = 0; z < 3; z++) {
	    			checkX = xCoord - 1 + x;
	    			checkY = yCoord - 2 + y;
	    			checkZ = zCoord - 1 + z;
	    			
	    			//1.檢查block並設定代號
	    			block = world.getBlock(checkX, checkY, checkZ);
	    			
	    			code = -1;
	    			if(block != null) {
//	    				if(block == Blocks.water || block == Blocks.air || block == Blocks.lava) code = 0;
		    			if(block == ModBlocks.BlockPolymetal) code = 1;
		    			if(block == ModBlocks.BlockGrudgeHeavy) code = 2;
		    			LogHelper.info("DEBUG : multi block check: pos "+checkX+" "+checkY+" "+checkZ+" "+block.getLocalizedName()+" "+code);
	    			}
	    			
	    			//2.判定是否符合pattern
	    			typeTemp = 0;
	    			for(int t = 0; t < PATTERN.length; t++) {
	    				if(code == PATTERN[t][x][y][z]) {
	    					typeTemp += Math.pow(2, t);		//match pattern t
	    				}
	    			}
	    			typeMatch = (typeMatch & typeTemp);		//進行and運算, 刪去不符合的type
	    			
	    			LogHelper.info("DEBUG : check MB: type "+typeMatch+" "+typeTemp);
	    			if(typeMatch == 0) return -1;	//全部pattern都被濾掉, 無符合, 結束檢查
	    			
	    			//3.若為MultiBlock則再抓TileEntity, 檢查其主從, 無主方塊才能加入, 有主方塊則結束檢查
	    			if(code > 0) tile = (BasicTileMulti)world.getTileEntity(checkX, checkY, checkZ);
	    			if(tile != null) {
	    				if(tile.hasMaster()) {
	    					return -1;
	    				}
					}
	            }//end z for
	        }//end y for
	    }//end x for
	    LogHelper.info("DEBUG : check MB: type "+typeMatch);
	    return typeMatch;
	}
	
	//setup multi block struct, add tile entity
	public static void setupStructure(World world, int xCoord, int yCoord, int zCoord, int type) {
		LogHelper.info("DEBUG : setup MB");
		for(int x = xCoord - 1; x < xCoord + 2; x++) {
	        for(int y = yCoord - 2; y < yCoord + 1; y++) {
	            for(int z = zCoord - 1; z < zCoord + 2; z++) {
	                TileEntity tile = world.getTileEntity(x, y, z);
	                
	                // Check if block is master or servant
	                boolean master = (x == xCoord && y == yCoord && z == zCoord);
	                
	                if(tile != null && (tile instanceof BasicTileMulti)) {
	                    ((BasicTileMulti)tile).setMasterCoords(xCoord, yCoord, zCoord);
	                    ((BasicTileMulti)tile).setHasMaster(true);
	                    ((BasicTileMulti)tile).setIsMaster(master);
	                    //type: 0:normal 1:large shipyard off 2:large shipyard on 
	                    //      3:large workshop off 4:large workshop on
	                    //servant block is always = off type
	                    if(type == 1) {	//large shipyard
	                    	LogHelper.info("DEBUG : set MB: type 1");
	                    	((BasicTileMulti)tile).setStructType(1, world);
	                    }
//	                    if(type == 2) {	//large workshop
//	                    	((BasicTileMulti)tile).setStructType(3, world);
//	                    }
//	                    if(type == 4) {	//large
//	                    	((BasicTileMulti)tile).setStructType(5, world);
//	                    }
	                    
	                }
	            }//end z loop
	        }//end y loop
	    }//end x loop
		
		//spawn render entity at position master y-1
		LogHelper.info("DEBUG : spawn render entity "+xCoord+" "+yCoord+" "+zCoord);
		EntityRenderLargeShipyard entBase = new EntityRenderLargeShipyard(world, xCoord, yCoord, zCoord);
		EntityRenderVortex entVortex = new EntityRenderVortex(world, xCoord, yCoord, zCoord);
		
		world.spawnEntityInWorld(entBase);
		world.spawnEntityInWorld(entVortex);
	}

	//reset(remove) tile multi
	private static void resetTileMulti(BasicTileMulti parTile) {
		parTile.setMasterCoords(0, 0, 0);
		parTile.setHasMaster(false);
		parTile.setIsMaster(false);
		parTile.setStructType(0, parTile.getWorldObj());	
	}
	
	//Reset tile multi, called from master block if struct broken
	public static void resetStructure(World world, int xCoord, int yCoord, int zCoord) {
		LogHelper.info("DEBUG : reset struct: client? "+world.isRemote+" "+xCoord+" "+yCoord+" "+zCoord);
		
		for(int x = xCoord - 1; x < xCoord + 2; x++) {
			for(int y = yCoord - 2; y < yCoord + 1; y++) {
				for(int z = zCoord - 1; z < zCoord + 2; z++) {
	            	TileEntity tile = world.getTileEntity(x, y, z);
	            	if(tile != null && tile instanceof BasicTileMulti) {
	            		resetTileMulti((BasicTileMulti)tile);
	                }
	            }
	        }
	    }
		
		//set render entity dead
		if(!world.isRemote) {
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord-1.5D, yCoord-2D, zCoord-1.5D, xCoord+1.5D, yCoord+1D, zCoord+1.5D);
			List renderEntityList = world.getEntitiesWithinAABB(BasicRenderEntity.class, aabb);

            for(int i = 0; i < renderEntityList.size(); i++) { 
            	LogHelper.info("DEBUG : clear render entity "+renderEntityList.get(i)+xCoord+" "+yCoord+" "+zCoord);
            	((Entity)renderEntityList.get(i)).setDead();
            }
		}
	}


}
