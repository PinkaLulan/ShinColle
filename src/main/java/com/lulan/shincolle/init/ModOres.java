package com.lulan.shincolle.init;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@GameRegistry.ObjectHolder(Reference.MOD_ID)	//登錄object holder使mod的物件容易流通 其他人可以直接讀取該物件
public class ModOres
{


	//登錄item到遊戲中 (在pre init階段登錄)
	public static void oreDictRegister()
	{
		ItemStack ingotAbyssium = new ItemStack(ModItems.AbyssMetal, 1, 0);
		ItemStack ingotPolymetal = new ItemStack(ModItems.AbyssMetal, 1, 1);
		ItemStack nuggetAbyssium = new ItemStack(ModItems.AbyssNugget, 1, 0);
		ItemStack nuggetPolymetal = new ItemStack(ModItems.AbyssNugget, 1, 1);
		
		//abyssium
		OreDictionary.registerOre("ingotAbyssium", ingotAbyssium);
		OreDictionary.registerOre("nuggetAbyssium", nuggetAbyssium);
		OreDictionary.registerOre("blockAbyssium", ModBlocks.BlockAbyssium);
		
		//polymetal
		if (ConfigHandler.polyAsMn)
		{
			OreDictionary.registerOre("dustManganese", ingotPolymetal);
			OreDictionary.registerOre("oreManganese", ModBlocks.BlockPolymetalOre);
			OreDictionary.registerOre("nuggetManganese", nuggetPolymetal);
			OreDictionary.registerOre("blockManganese", ModBlocks.BlockAbyssium);
		}
		else
		{
			OreDictionary.registerOre("ingotPolymetal", ingotPolymetal);
			OreDictionary.registerOre("orePolymetal", ModBlocks.BlockPolymetalOre);
			OreDictionary.registerOre("nuggetPolymetal", nuggetPolymetal);
			OreDictionary.registerOre("blockPolymetal", ModBlocks.BlockPolymetal);
		}
		
		//grudge
		OreDictionary.registerOre("grudge", ModItems.Grudge);
		OreDictionary.registerOre("blockGrudge", ModBlocks.BlockGrudge);
		OreDictionary.registerOre("blockHeavyGrudge", ModBlocks.BlockGrudgeHeavy);
		
		//food
		OreDictionary.registerOre("foodCombatRation", ModItems.CombatRation);
		
	}
	
	
}
