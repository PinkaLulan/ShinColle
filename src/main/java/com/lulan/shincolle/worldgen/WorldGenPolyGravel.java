package com.lulan.shincolle.worldgen;

import java.util.Random;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**generate polymetallic gravel on the seabed
 * mod from clay generator
 */
public class WorldGenPolyGravel extends WorldGenerator
{
	
	private IBlockState genBlock = ModBlocks.BlockPolymetalGravel.getDefaultState();
    private int numberOfBlocks;
    

    public WorldGenPolyGravel(int num)
    {
        this.numberOfBlocks = num;
    }

    /**
     * polymetal gravel生成方法:
     * 1. 傳入pos為水底+1格的方塊
     * 2. 判定高度是否 <= 55, 且pos位置為WATER方塊
     * 3. 依照設定檔取代y-1格的方塊為polymetal gravel
     */
	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
    	boolean notFrozen = true;
    	int x = pos.getX();
    	int y = pos.getY();
    	int z = pos.getZ();
    	
    	//碰到frozen ocean, 會抓到海面的冰塊, 必須往下找海底
    	if(world.getBlockState(pos.down(1)).getMaterial() == Material.ICE &&
    	   world.getBlockState(pos.down(2)).getMaterial() == Material.WATER)
    	{
    		IBlockState getblock = null;
    		int newy = 1;
    		
    		//從目前y往下3格開始找海底, 即水深必須至少3格
    		for (newy = y - 3; newy > 3; newy--)
    		{
    			getblock = world.getBlockState(new BlockPos(x, newy, z));
    			
    			//找到非水方塊, 為海底
    			if (getblock.getMaterial() != Material.WATER)
    			{
    				y = newy;	//指定新高度
    				notFrozen = false;
    				break;
    			}
    		}
    	}
    	
    	//若為FROZEN OCEAN: 判定y高度方塊不是水(表示非海底)
    	//一般OCEAN: 高度超過55, 則不生成
        if (notFrozen && (world.getBlockState(new BlockPos(x, y, z)).getMaterial() != Material.WATER || y > 55))
        {
            return false;
        }
        else
        {
            int l = rand.nextInt(this.numberOfBlocks - 1) + 1;	//生成數量
            byte b0 = 1;										//生成厚度1的polymetal

            //以x,z為中心, 半徑延伸l長度
            for (int i1 = x - l; i1 <= x + l; ++i1)
            {
                for (int j1 = z - l; j1 <= z + l; ++j1)
                {
                	//計算生成距離: 預計生成為球形
                    int k1 = i1 - x;
                    int l1 = j1 - z;

                    //判定沒有超過生成距離: ex:預計生成5個, 則生成距離不能超出5x5
                    if (k1 * k1 + l1 * l1 <= l * l)
                    {
                    	//在y高度+-l的範圍內生成
                        for (int i2 = y - b0; i2 <= y + b0; ++i2)
                        {
                            BlockPos blockpos = new BlockPos(i1, i2, j1);
                            Block b = world.getBlockState(blockpos).getBlock();

                            //若該方塊為土/沙/礫石/石頭, 則取代為polymetal gravel
                            if ((ConfigHandler.polyGravelBaseBlock[0] && b == Blocks.STONE) ||
                            	(ConfigHandler.polyGravelBaseBlock[1] && b == Blocks.GRAVEL) ||
                            	(ConfigHandler.polyGravelBaseBlock[2] && b == Blocks.SAND) ||
                            	(ConfigHandler.polyGravelBaseBlock[3] && b == Blocks.DIRT))
                            {
                                world.setBlockState(blockpos, this.genBlock, 2);
                            }
                        }//end for 生成厚度
                    }//end 生成距離判定
                }//end for z
            }//end for x

            return true;
        }
    }
    
    
}
