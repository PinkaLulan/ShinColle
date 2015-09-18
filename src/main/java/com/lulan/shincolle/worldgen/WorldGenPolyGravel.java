package com.lulan.shincolle.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;

/**generate polymetallic gravel on the seabed
 * mod from clay generator
 */
public class WorldGenPolyGravel extends WorldGenerator {
	
	private Block genBlock;
    private int numberOfBlocks;
    

    public WorldGenPolyGravel(int num) {
        this.genBlock = ModBlocks.BlockPolymetalGravel;
        this.numberOfBlocks = num;
    }

    public boolean generate(World world, Random rand, int x, int y, int z) {
    	boolean notFrozen = true;
    	
    	//碰到frozen ocean, 會抓到海面的冰塊, 必須往下找海底
    	if(world.getBlock(x, y - 1, z).getMaterial() == Material.ice &&
    	   world.getBlock(x, y - 2, z).getMaterial() == Material.water) {
    		Block getblock = null;
    		int newy = 1;
    		
    		//從目前y往下兩格開始找海底
    		for(newy = y - 3; newy > 3; newy--) {
    			getblock = world.getBlock(x, newy, z);
    			//找到非水方塊, 為海底
    			if(getblock.getMaterial() != Material.water) {
    				y = newy;	//指定新高度
    				notFrozen = false;
    				break;
    			}
    		}
    	}
    	
    	//若y高度方塊不是水(表示非海底), 或高度超過55, 則不生成
        if(notFrozen && (world.getBlock(x, y, z).getMaterial() != Material.water || y > 55)) {
            return false;
        }
        else {
            int l = rand.nextInt(this.numberOfBlocks - 1) + 1;	//生成數量
            byte b0 = 1;										//生成厚度1的polymetal

            //以x,z為中心, 半徑延伸l長度
            for(int i1 = x - l; i1 <= x + l; ++i1) {
                for(int j1 = z - l; j1 <= z + l; ++j1) {
                    int k1 = i1 - x;
                    int l1 = j1 - z;

                    //check radius of cluster <= num
                    if(k1 * k1 + l1 * l1 <= l * l) {
                    	//在y高度+-l的範圍內生成
                        for(int i2 = y - b0; i2 <= y + b0; ++i2) {
                            Block block = world.getBlock(i1, i2, j1);
                            //若該方塊為土/沙/礫石/石頭, 則取代為polymetal gravel
                            if((ConfigHandler.polyGravelBaseBlock[0] && block == Blocks.stone) ||
                               (ConfigHandler.polyGravelBaseBlock[1] && block == Blocks.gravel) ||
                               (ConfigHandler.polyGravelBaseBlock[2] && block == Blocks.sand) ||
                               (ConfigHandler.polyGravelBaseBlock[3] && block == Blocks.dirt)) {
                                world.setBlock(i1, i2, j1, this.genBlock, 0, 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
