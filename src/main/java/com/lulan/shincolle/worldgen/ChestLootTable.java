package com.lulan.shincolle.worldgen;

import java.util.ArrayList;

import com.lulan.shincolle.config.ConfigLoot.ItemEntry;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.item.BasicItem;
import com.lulan.shincolle.reference.Reference;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.event.LootTableLoadEvent;

/**
 * custom loot table:
 * loot table只能在LootTableLoadEvent中修改, 之後就會設為為final而不能再修改
 * 任何再event以外的地方修改table都會跳exception
 * 
 * loot pool:
 * vanilla的loot table除非為空, 不然必有"main"這個pool, 若有多個pool則會取名為 "pool1" "pool2" "pool3" ...
 * 詳見ForgeHooks::readPoolName
 * 
 */
public class ChestLootTable
{

	private static int LootCount = 0;
	
	
	public ChestLootTable() {}
	
	/**
	 * chest_ID:
	 * 0:Spawn Bonus Chest, 1:Igloo, 2:Dungeon, 3:Village Blacksmith, 4:Mineshaft, 5:Pyramid
	 * 6:Jungle Temple, 7:Nether Bridge, 8:Stronghold, 9:End City"+NEW_LINE);
	 */
	public static void editLoot(LootTableLoadEvent event)
	{
		final ResourceLocation host = event.getName();
		final LootTable table = event.getTable();

		if (host.equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST))
		{
			addNewPoolToTable(table, 0);
		}
		else if (host.equals(LootTableList.CHESTS_IGLOO_CHEST))
		{
			addNewPoolToTable(table, 1);
		}
		else if (host.equals(LootTableList.CHESTS_SIMPLE_DUNGEON))
		{
			addNewPoolToTable(table, 2);
		}
		else if (host.equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH))
		{
			addNewPoolToTable(table, 3);
		}
		else if (host.equals(LootTableList.CHESTS_ABANDONED_MINESHAFT))
		{
			addNewPoolToTable(table, 4);
		}
		else if (host.equals(LootTableList.CHESTS_DESERT_PYRAMID))
		{
			addNewPoolToTable(table, 5);
		}
		else if (host.equals(LootTableList.CHESTS_JUNGLE_TEMPLE))
		{
			addNewPoolToTable(table, 6);
		}
		else if (host.equals(LootTableList.CHESTS_NETHER_BRIDGE))
		{
			addNewPoolToTable(table, 7);
		}
		else if (host.equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY) ||
				 host.equals(LootTableList.CHESTS_STRONGHOLD_CROSSING) ||
				 host.equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR))
		{
			addNewPoolToTable(table, 8);
		}
		else if (host.equals(LootTableList.CHESTS_END_CITY_TREASURE))
		{
			addNewPoolToTable(table, 9);
		}	
	}
	
	private static LootEntryItem convItemEntryToLootEntry(ItemEntry ent)
	{
		if  (ent != null)
		{
			//init entry value
			//set item
			Item item = Item.getByNameOrId(ent.itemName);
			
			//set function
			ArrayList<LootFunction> funcList = new ArrayList<LootFunction>();
			
			//add function: meta value
			//specific meta
			if (ent.itemMeta > 0)
			{
				funcList.add(new SetMetadata(new LootCondition[0], new RandomValueRange(ent.itemMeta)));
			}
			//random meta, only for BasicItem
			else if (ent.itemMeta == -1)
			{
				if (item instanceof BasicItem)
				{
					int maxMeta = ((BasicItem) item).getTypes() - 1;
					funcList.add(new SetMetadata(new LootCondition[0], new RandomValueRange(0, maxMeta)));
				}
			}
			
			//add function: count
			funcList.add(new SetCount(new LootCondition[0], new RandomValueRange(ent.min, ent.max)));

			//set condition
			LootCondition[] condList = new LootCondition[] {new RandomChance(ent.chance)};
			
			//set entry name
			String entryName = Reference.MOD_ID + ":lootentry" + LootCount;
			LootCount++;
			
			//return entry
			if (item != null)
			{
				return new LootEntryItem(item, ent.weight, 0,
						 				 funcList.toArray(new LootFunction[funcList.size()]),
						 				 condList, entryName);
			}
		}
		
		return null;
	}
	
	private static void addNewPoolToTable(LootTable table, int chestID)
	{
		//conv item entry to loot entry
		ArrayList<LootEntry> lootList = new ArrayList<LootEntry>();
		ArrayList<ItemEntry> lootlist2 = ConfigHandler.configLoot.LOOTMAP.get(chestID);
		String poolName = Reference.MOD_ID + "Pool";
		
		for (ItemEntry ent : lootlist2)
		{
			LootEntryItem ent2 = convItemEntryToLootEntry(ent);
			if (ent2 != null)
			{
				lootList.add(ent2);
			}
		}
		
		//add pool
		if (lootList.size() > 0)
		{
			table.addPool(new LootPool(lootList.toArray(new LootEntry[lootList.size()]),
							//always can roll
							new LootCondition[0],
							//roll 1 ~ N/2 + 1 times
							new RandomValueRange(1, lootList.size() / 2 + 1),
							//bonus roll +1 per luck level
							new RandomValueRange(1),
							poolName));
		}
	}
	
	
}