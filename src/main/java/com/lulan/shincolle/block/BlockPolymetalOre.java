package com.lulan.shincolle.block;

import java.util.Random;

import com.lulan.shincolle.init.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;

public class BlockPolymetalOre extends BasicBlock {
	
	public BlockPolymetalOre() {
	    super();
	    this.setBlockName("BlockPolymetalOre");
	    this.setHardness(3.0F);
	    this.setHarvestLevel("pickaxe", 2);
   
	}
	
	//挖礦經驗設定
	//getExpDrop(IBlockAccess world, int metadata, int fortune)
	private Random rand = new Random();
	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune){
		if (this.getItemDropped(metadata, rand, fortune) != Item.getItemFromBlock(this)) { //不是絲綢挖的話給經驗
			return (rand.nextInt(4)+1)*(fortune+1);	//給(附魔等級+1)x(1~4)的經驗
		}
		return 0;	//絲綢挖的話 給0
	}

	//掉落物設定
	@Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return ModItems.Polymetal;
    }
	
	//掉落數量設定: 根據機率跟附魔等級決定掉落數量
	//若附魔等級>0  依照等級隨機增加數量  最少2顆  最多為(1+附魔等級)個
	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand) {
		if (fortune > 0) {
			return 2 + rand.nextInt(fortune);
		}
		return 1;  //無附魔 則掉落一顆
	}
	
	
//	//箱子類方塊打掉才使用damageDropped 使其掉落metadata內的物品  (quantityDropped也有metadata版本)
//	@Override
//	public int damageDropped(int metadata) {
//	    return this.meta;
//	}

}
