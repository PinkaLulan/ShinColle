package com.lulan.shincolle.init;

import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.worldgen.ShinColleWorldGen;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**ore gen and chest loot
 */
public class ModWorldGen
{
	
	public static final ShinColleWorldGen scWorldGen = new ShinColleWorldGen();
	
	
	public static void init()
	{
		//generate ore
		GameRegistry.registerWorldGenerator(scWorldGen , 0);
		
		//normal item
		ItemStack abyssium = new ItemStack(ModBlocks.BlockAbyssium, 1);
		ItemStack polymetal = new ItemStack(ModBlocks.BlockPolymetal, 1);
		ItemStack instantCon = new ItemStack(ModItems.InstantConMat, 1);
		ItemStack desk = new ItemStack(ModBlocks.BlockDesk);
		//ship egg
		ItemStack spawneggRE = new ItemStack(ModItems.ShipSpawnEgg, 1, ID.Ship.BattleshipRE+2);
		ItemStack spawneggWO = new ItemStack(ModItems.ShipSpawnEgg, 1, ID.Ship.CarrierWO+2);
		//rare item, equips
		ItemStack ring = new ItemStack(ModItems.MarriageRing, 1);
		ItemStack cannon = new ItemStack(ModItems.EquipCannon, 1, 10);
		ItemStack torpedo = new ItemStack(ModItems.EquipTorpedo, 1, 4);
		ItemStack airT = new ItemStack(ModItems.EquipAirplane, 1, 3);
		ItemStack airF = new ItemStack(ModItems.EquipAirplane, 1, 8);
		ItemStack airB = new ItemStack(ModItems.EquipAirplane, 1, 12);
		ItemStack modernKit = new ItemStack(ModItems.ModernKit, 1);
		ItemStack repair = new ItemStack(ModItems.RepairGoddess, 1);

		//chest string TODO
//		String[] rareChest = new String[] {ChestGenHooks.DUNGEON_CHEST,
//										   ChestGenHooks.PYRAMID_DESERT_CHEST,
//										   ChestGenHooks.PYRAMID_JUNGLE_CHEST,
//										   ChestGenHooks.STRONGHOLD_LIBRARY,
//										   ChestGenHooks.STRONGHOLD_CORRIDOR,
//										   ChestGenHooks.STRONGHOLD_CROSSING};
//		
//		String[] commChest = new String[] {ChestGenHooks.MINESHAFT_CORRIDOR,
//										   ChestGenHooks.VILLAGE_BLACKSMITH};
//		
//		//rare chest
//		for(int i = 0; i < rareChest.length; ++i) {
//			//                                                                 item, min, max, prob
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(spawneggRE, 1, 1, 8));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(spawneggWO, 1, 1, 8));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(ring, 1, 1, 8));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(cannon, 1, 1, 15));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(torpedo, 1, 1, 15));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(airT, 1, 1, 10));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(airF, 1, 1, 10));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(airB, 1, 1, 10));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(polymetal, 1, 3, 30));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(modernKit, 2, 3, 15));
//			ChestGenHooks.addItem(rareChest[i], new WeightedRandomChestContent(instantCon, 20, 40, 30));
//		}
//	
//		//common chest
//		for(int i = 0; i < commChest.length; ++i) {
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(abyssium, 1, 3, 40));
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(polymetal, 1, 3, 50));
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(ring, 1, 1, 4));
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(cannon, 1, 1, 5));
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(torpedo, 1, 1, 5));
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(airT, 1, 1, 5));
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(airF, 1, 1, 5));
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(airB, 1, 1, 5));
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(modernKit, 1, 2, 10));
//			ChestGenHooks.addItem(commChest[i], new WeightedRandomChestContent(instantCon, 15, 30, 60));
//		}
//		
//		//starter chest
//		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(desk, 1, 1, 90));
//		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(desk, 1, 1, 25));
		
	}

}
