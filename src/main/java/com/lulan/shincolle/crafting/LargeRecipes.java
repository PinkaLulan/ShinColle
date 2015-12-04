package com.lulan.shincolle.crafting;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.IShipItemType;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.LogHelper;

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
public class LargeRecipes {
	
	private static Random rand = new Random();
	private static final int MIN_AMOUNT = 100;		//material min amount
	private static final int MAX_STOCK = 400000;	//max amount in stock
	private static final int BASE_POWER = 460800;	//base cost power
	private static final int POWER_PER_MAT = 256;	//cost per item
	
	public LargeRecipes() {}
		
	//檢查材料是否能夠建造
	public static boolean canRecipeBuild(int[] matAmount) {
		return matAmount[0]>=MIN_AMOUNT && matAmount[1]>=MIN_AMOUNT && matAmount[2]>=MIN_AMOUNT && matAmount[3]>=MIN_AMOUNT;
	}
	
	//計算總共需要的燃料
	public static int calcGoalPower(int[] matAmount) {
		int extraAmount;
		
		if(canRecipeBuild(matAmount)) {
			extraAmount = matAmount[0] + matAmount[1] + matAmount[2] + matAmount[3] - MIN_AMOUNT * 4;
			return BASE_POWER + POWER_PER_MAT * extraAmount;
		}
		
		return 0;
	}
	
	//add or set item to slot
	private static void addSlotContents(TileMultiGrudgeHeavy tile, Item item, int meta, int slot) {
		if(tile.getStackInSlot(slot) == null) {	//空slot, 新增itemstack
			tile.setInventorySlotContents(slot, new ItemStack(item, 1, meta));
		}
		else {	//slot已佔用, 物品數+1
			tile.getStackInSlot(slot).stackSize++;
		}
	}
	
	//get fit or empty slot with item
	private static int getFitSlot(TileMultiGrudgeHeavy tile, Item item, int meta) {
		//search slot 1~10
		for(int i = TileMultiGrudgeHeavy.SLOTS_OUT + 1; i < TileMultiGrudgeHeavy.SLOTS_NUM; i++) {
			//slot為空 or 物品相同且尚未達到最大堆疊數
			if((tile.getStackInSlot(i) == null) ||
			   (tile.getStackInSlot(i).getItem() == item &&
			    tile.getStackInSlot(i).getItemDamage() == meta &&
			    tile.getStackInSlot(i).stackSize < tile.getStackInSlot(i).getMaxStackSize())) {
				return i;
			}
		}
		return -1;	//no fit slot
	}
	
	//新增資材到slots中
	public static boolean outputMaterialToSlot(TileMultiGrudgeHeavy tile, int selectMat, boolean compress) {
		Item matchItem = null;
		int meta = 0;
		int slot = -1;
		
		//設定尋找的物品
		if(compress) {	//輸出block, container等壓縮物品
			switch(selectMat) {
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
		else {			//輸出單件物品
			switch(selectMat) {
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
		if(matchItem != null) {
			slot = getFitSlot(tile, matchItem, meta);	
			//輸出物品到該slot
			if(slot > -1) {
				addSlotContents(tile, matchItem, meta, slot);
				return true;
			}
		}		
		
		return false;
	}

	//新增資材到matsStock中
	public static boolean addMaterialStock(TileMultiGrudgeHeavy tile, int slot, int itemType, ItemStack item) {
		int[] addMats = new int[] {0,0,0,0};
		int meta = item.getItemDamage();
		
		//set recycle price
		switch(itemType) {
		case ID.ItemType.AbyssMetal_Abyssium:
			addMats[1] = 1;
			break;
		case ID.ItemType.AbyssMetal_Polymetal:
			addMats[3] = 1;
			break;
		case ID.ItemType.Ammo_L:
			addMats[2] = 1;
			break;
		case ID.ItemType.Ammo_LC:
			addMats[2] = 9;
			break;
		case ID.ItemType.Ammo_H:
			addMats[2] = 4;
			break;
		case ID.ItemType.Ammo_HC:
			addMats[2] = 36;
			break;
		case ID.ItemType.BlockAbyssium:
			addMats[1] = 9;
			break;
		case ID.ItemType.BlockGrudge:
			addMats[0] = 9;
			break;
		case ID.ItemType.BlockPolymetal:
			addMats[3] = 9;
			break;
		case ID.ItemType.BlockPolymetalGravel:
			addMats[3] = 4;
			break;
		case ID.ItemType.Grudge:
			addMats[0] = 1;
			break;
		case ID.ItemType.EquipAirplane:
			switch(meta) {
			case 13:	//256
				addMats[0] = rand.nextInt(12) + 3;
				addMats[1] = rand.nextInt(14) + 5;
				addMats[2] = rand.nextInt(14) + 5;
				addMats[3] = rand.nextInt(16) + 11;
				break;
			case 14:	//1000
				addMats[0] = rand.nextInt(10) + 40;
				addMats[1] = rand.nextInt(15) + 50;
				addMats[2] = rand.nextInt(20) + 60;
				addMats[3] = rand.nextInt(25) + 80;
				break;
			default:	//2400,3800
				addMats[0] = rand.nextInt(20) + 80;
				addMats[1] = rand.nextInt(30) + 100;
				addMats[2] = rand.nextInt(40) + 120;
				addMats[3] = rand.nextInt(50) + 150;
				break;
			}
			break;
		case ID.ItemType.EquipArmor:
			switch(meta) {
			case 0:		//80
				addMats[0] = rand.nextInt(3) + 3;
				addMats[1] = rand.nextInt(4) + 4;
				addMats[2] = rand.nextInt(2) + 2;
				addMats[3] = rand.nextInt(2) + 2;
				break;
			default:	//500
				addMats[0] = rand.nextInt(15) + 35;
				addMats[1] = rand.nextInt(20) + 45;
				addMats[2] = rand.nextInt(10) + 25;
				addMats[3] = rand.nextInt(5) + 15;
				break;
			}
			break;
		case ID.ItemType.EquipCannon:
			switch(meta) {
			case 0:		//128
			case 1:
				addMats[0] = rand.nextInt(4) + 5;
				addMats[1] = rand.nextInt(4) + 5;
				addMats[2] = rand.nextInt(5) + 11;
				addMats[3] = rand.nextInt(3) + 3;
				break;
			case 2:		//320
			case 3:
			case 4:
			case 5:
				addMats[0] = rand.nextInt(7) + 10;
				addMats[1] = rand.nextInt(7) + 10;
				addMats[2] = rand.nextInt(8) + 16;
				addMats[3] = rand.nextInt(6) + 6;
				break;
			case 6:		//1600
			case 7:
			case 8:
				addMats[0] = rand.nextInt(10) + 50;
				addMats[1] = rand.nextInt(15) + 70;
				addMats[2] = rand.nextInt(35) + 90;
				addMats[3] = rand.nextInt(20) + 80;
				break;
			default:	//4400
				addMats[0] = rand.nextInt(20) + 100;
				addMats[1] = rand.nextInt(30) + 130;
				addMats[2] = rand.nextInt(70) + 180;
				addMats[3] = rand.nextInt(40) + 150;
				break;
			}
			break;
		case ID.ItemType.EquipCatapult:
			switch(meta) {
			case 0:		//2800
			case 1:
				addMats[0] = rand.nextInt(30) + 110;
				addMats[1] = rand.nextInt(40) + 130;
				addMats[2] = rand.nextInt(20) + 80;
				addMats[3] = rand.nextInt(50) + 160;
				break;
			default:	//5000
				addMats[0] = rand.nextInt(30) + 130;
				addMats[1] = rand.nextInt(50) + 160;
				addMats[2] = rand.nextInt(20) + 100;
				addMats[3] = rand.nextInt(80) + 190;
				break;
			}
			break;
		case ID.ItemType.EquipMachinegun:
			switch(meta) {
			case 0:		//100
			case 1:
			case 2:
			case 3:
				addMats[0] = rand.nextInt(3) + 2;
				addMats[1] = rand.nextInt(4) + 5;
				addMats[2] = rand.nextInt(5) + 6;
				addMats[3] = rand.nextInt(1) + 1;
				break;
			default:	//800
				addMats[0] = rand.nextInt(10) + 24;
				addMats[1] = rand.nextInt(15) + 30;
				addMats[2] = rand.nextInt(20) + 40;
				addMats[3] = rand.nextInt(5) + 14;
				break;
			}
			break;
		case ID.ItemType.EquipRadar:
			switch(meta) {
			case 0:		//200
			case 1:
			case 2:
			case 3:
			case 4:
				addMats[0] = rand.nextInt(5) + 12;
				addMats[1] = rand.nextInt(4) + 8;
				addMats[2] = rand.nextInt(3) + 8;
				addMats[3] = rand.nextInt(2) + 6;
				break;
			default:	//2000
				addMats[0] = rand.nextInt(40) + 100;
				addMats[1] = rand.nextInt(30) + 80;
				addMats[2] = rand.nextInt(20) + 70;
				addMats[3] = rand.nextInt(10) + 50;
				break;
			}
			break;
		case ID.ItemType.EquipTorpedo:
			switch(meta) {
			case 0:		//160
			case 1:
			case 2:
				addMats[0] = rand.nextInt(4) + 6;
				addMats[1] = rand.nextInt(5) + 6;
				addMats[2] = rand.nextInt(6) + 12;
				addMats[3] = rand.nextInt(3) + 4;
				break;
			default:	//1200
				addMats[0] = rand.nextInt(20) + 60;
				addMats[1] = rand.nextInt(25) + 70;
				addMats[2] = rand.nextInt(30) + 80;
				addMats[3] = rand.nextInt(15) + 45;
				break;
			}
			break;
		case ID.ItemType.EquipTurbine:
			switch(meta) {
			case 0:		//1400
			case 1:
				addMats[0] = rand.nextInt(35) + 90;
				addMats[1] = rand.nextInt(25) + 80;
				addMats[2] = rand.nextInt(15) + 45;
				addMats[3] = rand.nextInt(20) + 60;
				break;
			default:	//3200
				addMats[0] = rand.nextInt(60) + 170;
				addMats[1] = rand.nextInt(45) + 130;
				addMats[2] = rand.nextInt(20) + 80;
				addMats[3] = rand.nextInt(30) + 100;
				break;
			}
			break;
		}//end recycle price
		
		if(ConfigHandler.easyMode && itemType < ID.ItemType.EquipAirplane) {
			addMats[0] *= 10;
			addMats[1] *= 10;
			addMats[2] *= 10;
			addMats[3] *= 10;
		}
		
		//check amount in stock
		int matStockCurrent;
		boolean canAdd = true;
		
		for(int i = 0; i < 4; ++i) {
			matStockCurrent = tile.getMatStock(i);
			if(matStockCurrent > MAX_STOCK) {
				canAdd = false;
				break;
			}
		}
		
		//add material
		if(canAdd) {
			for(int j = 0; j < 4; ++j) {
				tile.addMatStock(j, addMats[j]);
			}
			LogHelper.info("DEBUG: large recipe: recycle: "+addMats[0]+" "+addMats[1]+" "+addMats[2]+" "+addMats[3]);
		}
		
		return canAdd;
	}
	
	//判定材料種類: -1:other 0:fuel 1~N:meterial
	public static int getMaterialType(ItemStack itemstack) {
		int itemType = -1;
		int meta = 0;	
		
		if(itemstack != null) {
			Item item = itemstack.getItem();
			meta = itemstack.getItemDamage();
			
			/**itemtype, ref: ID.ItemType
			 * -1/0: other item
			 */
			if(item instanceof IShipItemType) {
				itemType = ((IShipItemType) item).getItemType();
				
				//special type: item with meta value
				switch(itemType) {
				case ID.ItemType.AbyssMetal:
					if(meta == 0) {
						itemType = ID.ItemType.AbyssMetal_Abyssium;
					}
					else {
						itemType = ID.ItemType.AbyssMetal_Polymetal;
					}
					break;
				case ID.ItemType.Ammo:
					switch(meta) {
					case 0:
						itemType = ID.ItemType.Ammo_L;
						break;
					case 1:
						itemType = ID.ItemType.Ammo_LC;
						break;
					case 2:
						itemType = ID.ItemType.Ammo_H;
						break;
					case 3:
						itemType = ID.ItemType.Ammo_HC;
						break;
					}
					break;
				}
			}//end special item type
			
			/** check block item type */
			if(item == Item.getItemFromBlock(ModBlocks.BlockGrudge)) { itemType = ID.ItemType.BlockGrudge; }
			else if(item == Item.getItemFromBlock(ModBlocks.BlockAbyssium)) { itemType = ID.ItemType.BlockAbyssium; }
			else if(item == Item.getItemFromBlock(ModBlocks.BlockPolymetal)) { itemType = ID.ItemType.BlockPolymetal; }
			else if(item == Item.getItemFromBlock(ModBlocks.BlockPolymetalGravel)) { itemType = ID.ItemType.BlockPolymetalGravel; }
		}

		return itemType;
	}
	
	//將材料數量寫進itemstack回傳
	public static ItemStack getBuildResultShip(int[] matAmount) {
		ItemStack buildResult = new ItemStack(ModItems.ShipSpawnEgg, 1, 1);
		buildResult.stackTagCompound = new NBTTagCompound();
		buildResult.stackTagCompound.setInteger("Grudge", matAmount[0]);
		buildResult.stackTagCompound.setInteger("Abyssium", matAmount[1]);
		buildResult.stackTagCompound.setInteger("Ammo", matAmount[2]);
		buildResult.stackTagCompound.setInteger("Polymetal", matAmount[3]);
		
		return buildResult;
	}
	
	/** ROLL SYSTEM
	 *  1. get material amounts
	 *  2. roll equip type by mat.amounts
	 *  3. roll equip by equip type and mat.amounts
	 */
	public static ItemStack getBuildResultEquip(int[] matAmount) {
		//result item
		ItemStack buildResult = null;
		int rollType = -1;
		int totalMat = matAmount[0]+matAmount[1]+matAmount[2]+matAmount[3];
		float randRate = rand.nextFloat();

		//first roll: roll equip type
		rollType = EquipCalc.rollEquipType(1, matAmount);
		//second roll: roll equips of the type
		return EquipCalc.rollEquipsOfTheType(rollType, totalMat, 1);
	}
	

}

