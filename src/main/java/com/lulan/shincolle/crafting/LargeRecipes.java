package com.lulan.shincolle.crafting;

import java.util.Random;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.IShipResourceItem;
import com.lulan.shincolle.item.ShipSpawnEgg;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**Large Shipyard Recipe Helper
 *  Fuel Cost = BaseCost + CostPerMaterial * ( TotalMaterialAmount - minAmount * 4 )
 *  Total Build Time = FuelCost / buildSpeed
 *  MaxBuildTime / MaxFuelCost = 24min  / 1382400 (48 fuel per tick)
 *  MinBuildTime / MinFuelCost = 8min   / 460800
 * 	MaxMaterial  / MaxFuelCost = 1000*4 / 1382400
 *  MinMaterial  / MinFuelCost = 100*4  / 460800  = BaseCost(460800) CostPerMaterial(256)
 *  
 *  
 * Equip Build Rate: first roll -> second roll
 * 	 1. FIRST: roll equip type      
 * 	 2. SECOND: roll equips of the type
 */	
public class LargeRecipes
{
	
	private static Random rand = new Random();
	private static final int MIN_AMOUNT = 100;		//material min amount
	private static final int MAX_STOCK = 1000000;	//max amount in stock
	private static final int BASE_POWER = 460800;	//base cost power
	private static final int POWER_PER_MAT = 256;	//cost per item
	
	public LargeRecipes() {}
		
	//檢查材料是否能夠建造
	public static boolean canRecipeBuild(int[] matAmount)
	{
		return matAmount[0]>=MIN_AMOUNT && matAmount[1]>=MIN_AMOUNT && matAmount[2]>=MIN_AMOUNT && matAmount[3]>=MIN_AMOUNT;
	}
	
	//計算總共需要的燃料
	public static int calcGoalPower(int[] matAmount)
	{
		int extraAmount;
		
		if (canRecipeBuild(matAmount))
		{
			extraAmount = matAmount[0] + matAmount[1] + matAmount[2] + matAmount[3] - MIN_AMOUNT * 4;
			return BASE_POWER + POWER_PER_MAT * extraAmount;
		}
		
		return 0;
	}
	
	//add or set item to slot
	private static void addSlotContents(TileMultiGrudgeHeavy tile, Item item, int meta, int slot)
	{
		if (tile.getStackInSlot(slot) == null)
		{	//空slot, 新增itemstack
			tile.setInventorySlotContents(slot, new ItemStack(item, 1, meta));
		}
		else
		{	//slot已佔用, 物品數+1
			tile.getStackInSlot(slot).stackSize++;
		}
	}
	
	//get fit or empty slot with item
	private static int getFitSlot(TileMultiGrudgeHeavy tile, Item item, int meta)
	{
		//search slot 1~10
		for (int i = TileMultiGrudgeHeavy.SLOTS_OUT + 1; i < TileMultiGrudgeHeavy.SLOTS_NUM; i++)
		{
			//slot為空 or 物品相同且尚未達到最大堆疊數
			if ((tile.getStackInSlot(i) == null) ||
				(tile.getStackInSlot(i).getItem() == item &&
				 tile.getStackInSlot(i).getItemDamage() == meta &&
				 tile.getStackInSlot(i).stackSize < tile.getStackInSlot(i).getMaxStackSize()))
			{
				return i;
			}
		}
		return -1;	//no fit slot
	}
	
	//新增資材到slots中
	public static boolean outputMaterialToSlot(TileMultiGrudgeHeavy tile, int selectMat, boolean compress)
	{
		Item matchItem = null;
		int meta = 0;
		int slot = -1;
		
		//設定尋找的物品
		if (compress)
		{	//輸出block, container等壓縮物品
			switch (selectMat)
			{
			case 0:		//block grudge
				matchItem = Item.getItemFromBlock(ModBlocks.BlockGrudge);
				meta = 0;
				break;
			case 1:		//block abyssium
				matchItem = Item.getItemFromBlock(ModBlocks.BlockAbyssium);
				meta = 0;
				break;
			case 2: 	//light ammo container
				matchItem = ModItems.Ammo;
				meta = 1;
				break;
			case 3: 	//block polymetal
				matchItem = Item.getItemFromBlock(ModBlocks.BlockPolymetal);
				meta = 0;
				break;
			}	
		}
		else
		{	//輸出單件物品
			switch(selectMat)
			{
			case 0:		//item grudge
				matchItem = ModItems.Grudge;
				meta = 0;
				break;
			case 1:		//item abyssium
				matchItem = ModItems.AbyssMetal;
				meta = 0;
				break;
			case 2: 	//item light ammo
				matchItem = ModItems.Ammo;
				meta = 0;
				break;
			case 3: 	//item polymetal nodules
				matchItem = ModItems.AbyssMetal;
				meta = 1;
				break;
			}
		}
		
		//找出對應slot
		if (matchItem != null)
		{
			slot = getFitSlot(tile, matchItem, meta);	
			//輸出物品到該slot
			if (slot > -1)
			{
				addSlotContents(tile, matchItem, meta, slot);
				return true;
			}
		}		
		
		return false;
	}

	//新增資材到matsStock中
	public static boolean addMaterialStock(TileMultiGrudgeHeavy tile, ItemStack item)
	{
		boolean canAdd = false;
		
		if (item != null)
		{
			try
			{
				//check MAX amount
				int matStockCurrent = 0;
				canAdd = true;
				
				for (int j = 0; j < 4; ++j)
				{
					matStockCurrent = tile.getMatStock(j);
					if (matStockCurrent > MAX_STOCK)
					{
						canAdd = false;
						break;
					}
				}
				
				//add items
				if (canAdd)
				{
					//is resource item
					if (item.getItem() instanceof IShipResourceItem)
					{
						int meta = item.getItemDamage();

						int[] addMats = ((IShipResourceItem)item.getItem()).getResourceValue(meta);
					
						//check easy mode
						if (ConfigHandler.easyMode)
						{
							//x10
							for (int i = 0; i < 4; i++)
							{
								addMats[i] = addMats[i] * 10;
							}
						}
						
						//add material
						for (int k = 0; k < 4; k++)
						{
							tile.addMatStock(k, addMats[k]);
						}
						
						return true;
					}
					//is ship spawn egg
					else if (item.getItem() instanceof ShipSpawnEgg && item.getItemDamage() > 1)
					{
						//get ship recycle items
						ItemStack[] items = ShipCalc.getKaitaiItems(item.getItemDamage() - 2);
						
						for (ItemStack i : items)
						{
							if (i != null)
							{
								int size = i.stackSize;
								int meta = i.getItemDamage();
								int[] addMats = ((IShipResourceItem)i.getItem()).getResourceValue(meta);
								
								addMats[0] *= size;
								addMats[1] *= size;
								addMats[2] *= size;
								addMats[3] *= size;
								
								//check easy mode
								if (ConfigHandler.easyMode)
								{
									//x10
									for (int j = 0; j < 4; j++)
									{
										addMats[j] = addMats[j] * 10;
									}
								}
								
								//add material
								for (int k = 0; k < 4; k++)
								{
									tile.addMatStock(k, addMats[k]);
								}
							}
							//if ship id is NOT valid id, return false
							else
							{
								return false;
							}
						}//end for all ship kaitai items
						
						return true;
					}//end item is shipegg
				}//end can add
			}//end try
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
		
		return false;
	}

	//將材料數量寫進itemstack回傳
	public static ItemStack getBuildResultShip(int[] matAmount)
	{
		ItemStack buildResult = new ItemStack(ModItems.ShipSpawnEgg, 1, 1);
		
		buildResult.setTagCompound(new NBTTagCompound());
		buildResult.getTagCompound().setInteger("Grudge", matAmount[0]);
		buildResult.getTagCompound().setInteger("Abyssium", matAmount[1]);
		buildResult.getTagCompound().setInteger("Ammo", matAmount[2]);
		buildResult.getTagCompound().setInteger("Polymetal", matAmount[3]);
		
		return buildResult;
	}
	
	/** ROLL SYSTEM
	 *  1. get material amounts
	 *  2. roll equip type by mat.amounts
	 *  3. roll equip by equip type and mat.amounts
	 */
	public static ItemStack getBuildResultEquip(int[] matAmount)
	{
		//result item
		ItemStack buildResult = null;
		int rollType = -1;
		int totalMat = matAmount[0] + matAmount[1] + matAmount[2] + matAmount[3];
		float randRate = rand.nextFloat();

		//first roll: roll equip type
		rollType = EquipCalc.rollEquipType(1, matAmount);
		//second roll: roll equips of the type
		return EquipCalc.rollEquipsOfTheType(rollType, totalMat, 1);
	}
	

}

