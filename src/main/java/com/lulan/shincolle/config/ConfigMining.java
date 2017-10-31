package com.lulan.shincolle.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.lulan.shincolle.utility.LogHelper;

public class ConfigMining extends BasicShipConfig
{
	
	//loot entry
	public class ItemEntry
	{
		public final int itemMeta, weight, min, max, lvShip, lvHeight, lvTool;
		public final float enchant;
		public final String itemName;

		public ItemEntry(String itemName, int itemMeta, int weight, int min, int max, int lvShip, int lvHeight, int lvTool, int enchant)
		{
			this.itemName = itemName;
			this.itemMeta = itemMeta;
			this.weight = weight;
			this.min = min;
			this.max = max;
			this.lvShip = lvShip;
			this.lvHeight = lvHeight;
			this.lvTool = lvTool;
			this.enchant = (float)enchant * 0.01F;
		}
	}
	
	//loot entry list: <world id, entry map by world id<biome id, entry list by biome id>>
	public static HashMap<Integer, HashMap<Integer, ArrayList<ItemEntry>>> MININGMAP;
	public static final int GeneralWorldID = 999999;
	public static final int GeneralBiomeID = -999999;
	
	
	public ConfigMining() {}
	
	public ConfigMining(File file) throws Exception
	{
		super(file);
		
		this.MININGMAP = new HashMap<Integer, HashMap<Integer, ArrayList<ItemEntry>>>();
	}

	/**
	 * format:
	 * 0:world id, 1:biome id, 2:item_name, 3:item_meta, 4:weight, 5:min stack size, 6:max stack size
	 * 7:ship level, 8:y level, 9:tool level, 10:enchant weight
	 * 
	 * comment:
	 * start with "#"
	 */
	@Override
	protected void parse(ArrayList<String> lines)
	{
		//null check
		if (lines != null && lines.size() > 0)
		{
			//check lines
			for (String str : lines)
			{
				//check comment
				if (this.isCommentString(str))
				{
					continue;	//go to next line
				}
				//parse loot entry
				else
				{
					str = str.replaceAll("\\s", "");	//remove all whitespace
					String strs[] = str.split(",");		//split strins by ','
					int[] ints = new int[10];
					
					//check length
					if (strs != null && strs.length == 11 && strs[2].length() > 0)
					{
						try
						{
							//get int value
							ints[0] = Integer.parseInt(strs[0]);   //world
							ints[1] = Integer.parseInt(strs[1]);   //biome
							ints[2] = Integer.parseInt(strs[3]);   //meta
							ints[3] = Integer.parseInt(strs[4]);   //weight
							ints[4] = Integer.parseInt(strs[5]);   //min
							ints[5] = Integer.parseInt(strs[6]);   //max
							ints[6] = Integer.parseInt(strs[7]);   //ship lv
							ints[7] = Integer.parseInt(strs[8]);   //y lv
							ints[8] = Integer.parseInt(strs[9]);   //tool lv
							ints[9] = Integer.parseInt(strs[10]);  //enchant
							
							//check value
							if (ints[3] <= 1) ints[3] = 1;
							if (ints[4] <= 1) ints[4] = 1;
							if (ints[5] <= 1) ints[5] = 1;
							
							//put entry into list
							//get map by world id
							HashMap<Integer, ArrayList<ItemEntry>> map = this.MININGMAP.get(ints[0]);
							
							if (map == null)
							{
								map = new HashMap<Integer, ArrayList<ItemEntry>>();
								this.MININGMAP.put(ints[0], map);
							}
							
							//get list by biome id
							ArrayList<ItemEntry> list = map.get(ints[1]);
							
							if (list == null)
							{
								list = new ArrayList<ItemEntry>();
								map.put(ints[1], list);
							}
							
							list.add(new ItemEntry(strs[2], ints[2], ints[3], ints[4], ints[5], ints[6], ints[7], ints[8], ints[9]));
						}
						catch (Exception e)
						{
							//set item entry fail, discard this string
							LogHelper.info("EXCEPTION : parse error at ConfigMining.cfg : "+str+" "+e);
							continue;	//go to next line
						}
					}//end parse string
				}//end not comment
			}//end for all lines
		}//end lines != null
	}

	@Override
	protected ArrayList<String> getDefaultContent()
	{
		ArrayList<String> strs = new ArrayList<String>();
		
		strs.add("# Mining Loot Table"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		strs.add("# format: world id, biome id, item_name, item_meta, weight, min stack size, max stack size, ship level, y level, tool level, enchant weight"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		strs.add("# world id: 999999:all worlds, 0:Overworld, -1:the Nether, 1:the End, N:for specific world"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		strs.add("# biome id: -999999:for all biome, N:for specific biome"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		strs.add("# item_meta: -1:random meta, only for shincolle item, 0~N:specific meta"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		strs.add("# weight: generate rate, actual rate = weight of this item / total weight of all item"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		strs.add("# ship level: ship minimal level for this item"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		strs.add("# y level: highest y level of ship position for this item"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		strs.add("# tool level: minimal tool level of pickaxe, 0:wood/gold, 1:stone, 2:iron, 3:diamond"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		strs.add("# enchant weight: for fortune level on pickaxe, actual stack size = stack size * (1 + (enchant weight / 100) * fortune level)"+NEW_LINE);
		strs.add("#"+NEW_LINE);
		
		//general
		strs.add("999999,-999999,minecraft:cobblestone,0,100,1,4,1,256,0,0"+NEW_LINE);
		//overworld
		strs.add("0,-999999,minecraft:cobblestone,0,4000,1,4,1,256,0,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:stone,1,500,1,1,1,256,0,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:stone,3,500,1,1,1,256,0,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:stone,5,500,1,1,1,256,0,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:dirt,0,500,1,1,1,256,0,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:sand,0,500,1,1,1,256,0,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:gravel,0,200,1,1,1,256,0,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:obsidian,0,200,1,1,40,24,3,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:flint,0,250,1,1,1,256,0,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:gunpowder,0,400,1,1,40,64,0,0"+NEW_LINE);
		strs.add("0,-999999,minecraft:bone,0,400,1,1,1,64,0,0"+NEW_LINE);
		//overworld ores
		strs.add("0,-999999,minecraft:coal,0,500,1,3,1,100,1,150"+NEW_LINE);
		strs.add("0,-999999,minecraft:redstone,0,500,1,3,20,15,2,150"+NEW_LINE);
		strs.add("0,-999999,minecraft:iron_ore,0,350,1,2,1,64,2,100"+NEW_LINE);
		strs.add("0,-999999,shincolle:AbyssMetal,1,350,1,3,1,64,2,100"+NEW_LINE);
		strs.add("0,-999999,minecraft:gold_ore,0,100,1,1,30,32,2,100"+NEW_LINE);
		strs.add("0,-999999,minecraft:dye,4,200,1,3,30,30,2,150"+NEW_LINE);
		strs.add("0,-999999,minecraft:diamond,0,50,1,1,60,16,3,100"+NEW_LINE);
		strs.add("0,-999999,minecraft:emerald,0,80,1,1,40,32,3,100"+NEW_LINE);
		strs.add("0,-999999,shincolle:MarriageRing,0,25,1,1,1,16,3,0"+NEW_LINE);
		//overworld with biome
		strs.add("0,0,minecraft:prismarine_shard,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,0,minecraft:prismarine_crystals,0,200,1,3,60,128,2,100"+NEW_LINE);
		strs.add("0,0,shincolle:AbyssMetal,1,500,1,3,1,64,2,100"+NEW_LINE);
		strs.add("0,0,minecraft:sponge,0,200,1,1,80,128,0,100"+NEW_LINE);
		strs.add("0,6,minecraft:clay_ball,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,6,minecraft:mycelium,0,500,1,1,50,128,0,0"+NEW_LINE);
		strs.add("0,7,minecraft:clay_ball,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,7,minecraft:mycelium,0,500,1,1,50,128,0,0"+NEW_LINE);
		strs.add("0,10,minecraft:prismarine_shard,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,10,minecraft:prismarine_crystals,0,200,1,3,60,128,2,100"+NEW_LINE);
		strs.add("0,10,shincolle:AbyssMetal,1,500,1,3,1,64,2,100"+NEW_LINE);
		strs.add("0,10,minecraft:sponge,0,200,1,1,80,128,0,100"+NEW_LINE);
		strs.add("0,10,minecraft:packed_ice,0,1000,1,4,1,256,2,0"+NEW_LINE);
		strs.add("0,11,minecraft:clay_ball,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,11,minecraft:mycelium,0,500,1,1,50,128,0,0"+NEW_LINE);
		strs.add("0,11,minecraft:packed_ice,0,1000,1,4,1,256,2,0"+NEW_LINE);
		strs.add("0,12,minecraft:packed_ice,0,1000,1,4,1,256,2,0"+NEW_LINE);
		strs.add("0,13,minecraft:packed_ice,0,1000,1,4,1,256,2,0"+NEW_LINE);
		strs.add("0,16,minecraft:clay_ball,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,16,minecraft:mycelium,0,500,1,1,50,128,0,0"+NEW_LINE);
		strs.add("0,24,minecraft:prismarine_shard,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,24,minecraft:prismarine_crystals,0,200,1,3,60,128,2,100"+NEW_LINE);
		strs.add("0,24,shincolle:AbyssMetal,1,500,1,3,1,64,2,100"+NEW_LINE);
		strs.add("0,24,minecraft:sponge,0,200,1,1,80,128,0,100"+NEW_LINE);
		strs.add("0,25,minecraft:clay_ball,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,25,minecraft:mycelium,0,500,1,1,50,128,0,0"+NEW_LINE);
		strs.add("0,26,minecraft:clay_ball,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,26,minecraft:mycelium,0,500,1,1,50,128,0,0"+NEW_LINE);
		strs.add("0,26,minecraft:packed_ice,0,1000,1,4,1,256,2,0"+NEW_LINE);
		strs.add("0,30,minecraft:packed_ice,0,1000,1,4,1,256,2,0"+NEW_LINE);
		strs.add("0,31,minecraft:packed_ice,0,1000,1,4,1,256,2,0"+NEW_LINE);
		strs.add("0,134,minecraft:clay_ball,0,500,1,4,30,128,0,0"+NEW_LINE);
		strs.add("0,134,minecraft:mycelium,0,500,1,1,50,128,0,0"+NEW_LINE);
		strs.add("0,140,minecraft:packed_ice,0,1000,1,4,1,256,2,0"+NEW_LINE);
		strs.add("0,158,minecraft:packed_ice,0,1000,1,4,1,256,2,0"+NEW_LINE);
		//nether
		strs.add("-1,-999999,minecraft:netherrack,0,4500,1,4,1,256,0,0"+NEW_LINE);
		strs.add("-1,-999999,minecraft:nether_brick,0,1000,1,1,1,256,0,0"+NEW_LINE);
		strs.add("-1,-999999,minecraft:soul_sand,0,1000,1,1,1,256,0,0"+NEW_LINE);
		strs.add("-1,-999999,minecraft:gravel,0,1000,1,1,1,256,0,0"+NEW_LINE);
		strs.add("-1,-999999,minecraft:magma,0,500,1,1,40,256,3,0"+NEW_LINE);
		strs.add("-1,-999999,minecraft:flint,0,500,1,1,1,256,0,0"+NEW_LINE);
		strs.add("-1,-999999,shincolle:MarriageRing,0,50,1,1,1,256,3,0"+NEW_LINE);
		//nether ore
		strs.add("-1,-999999,minecraft:quartz,0,1000,1,3,1,256,2,150"+NEW_LINE);
		strs.add("-1,-999999,minecraft:glowstone,0,500,1,2,1,256,0,100"+NEW_LINE);
		strs.add("-1,-999999,minecraft:ghast_tear,0,50,1,1,90,256,3,100"+NEW_LINE);
		strs.add("-1,-999999,minecraft:blaze_rod,0,80,1,1,60,256,3,100"+NEW_LINE);
		//end
		strs.add("1,-999999,minecraft:end_stone,0,4000,1,4,1,256,0,0"+NEW_LINE);
		//end ores
		strs.add("1,-999999,minecraft:ender_pearl,0,200,1,1,40,256,3,100"+NEW_LINE);
		strs.add("1,-999999,minecraft:chorus_fruit,0,200,1,3,60,256,3,100"+NEW_LINE);
		strs.add("-1,-999999,shincolle:MarriageRing,0,25,1,1,1,256,3,0"+NEW_LINE);
		
		return strs;
	}

	
}