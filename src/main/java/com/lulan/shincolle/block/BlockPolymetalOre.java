package com.lulan.shincolle.block;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.lulan.shincolle.init.ModItems;

public class BlockPolymetalOre extends BasicBlock
{
	
	public static final String NAME = "BlockPolymetalOre";
	private static Random rand = new Random();
	
	
	public BlockPolymetalOre()
	{
		super();
		this.setUnlocalizedName(NAME);
		this.setRegistryName(NAME);
		this.setHarvestLevel("pickaxe", 1);
	    this.setHardness(3F);
	    
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), this.getRegistryName());
	}
	
	//挖礦經驗設定
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
		//不是絲綢挖的話給經驗
		if (getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
		{
			return (rand.nextInt(4) + 1) * (fortune + 1);	//給(附魔等級+1)x(1~4)的經驗
		}
		
		//絲綢挖的話 給0
		return 0;
    }

	//掉落物設定
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
        return ModItems.AbyssMetal;
    }
	
	//掉落物的meta=1 (polymetal)
	@Override
	public int damageDropped(IBlockState state)
	{
        return 1;
    }
	
	//掉落數量設定: 根據機率跟附魔等級決定掉落數量
	//若附魔等級>0  依照等級隨機增加數量  最少2顆  最多為(1+附魔等級)個
	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand)
	{
		if (fortune > 0)
		{
			return 2 + rand.nextInt(fortune + 1);
		}
		
		return 1 + rand.nextInt(2);  //無附魔 則掉落1~2
	}
	
	
}
