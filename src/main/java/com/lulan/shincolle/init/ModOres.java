package com.lulan.shincolle.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)	//登錄object holder使mod的物件容易流通 其他人可以直接讀取該物件
public class ModOres {

	//ore
	public static ItemStack PolymetalDust = new ItemStack(ModItems.AbyssMetal, 1, 1);
	public static ItemStack PolymetalOre = new ItemStack(ModBlocks.BlockPolymetalOre, 1, 0);


	//登錄item到遊戲中 (在pre init階段登錄)
	public static void oreDictRegister() {
		//polymetal = manganese ore
		OreDictionary.registerOre("dustManganese", PolymetalDust);
		OreDictionary.registerOre("oreManganese", ModBlocks.BlockPolymetalOre);
	}
}
