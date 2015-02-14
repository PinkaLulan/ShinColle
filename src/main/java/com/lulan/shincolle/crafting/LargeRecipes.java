package com.lulan.shincolle.crafting;

import java.util.Random;

import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicItem;
import com.lulan.shincolle.reference.AttrID;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

/**Large Shipyard Recipe Helper
 *  Fuel Cost = BaseCost + CostPerMaterial * ( TotalMaterialAmount - minAmount * 4 )
 *  Total Build Time = FuelCost / buildSpeed
 *  MaxBuildTime / MaxFuelCost = 24min  / 1382400 (48 fuel per tick)
 *  MinBuildTime / MinFuelCost = 8min   / 460800
 * 	MaxMaterial  / MaxFuelCost = 1000*4 / 1382400
 *  MinMaterial  / MinFuelCost = 100*4  / 460800  = BaseCost(460800) CostPerMaterial(256)
 *  
 *  
 * Equip Build Rate:
 *   Large Shipyard: single & weak twin cannon (equipID 4~8)
 *     grudge   -> +p
 *     abyssium -> +p
 *     ammo     -> +p
 *     polymetal-> +p
 *     
 *     min point (all 100)  = 
 *       result: AmmoCon 25% AmmoHCon % eq4 % eq5 % eq6 % eq7 % eq8 %
 *     max point (all 1000) = 
 *       result: AmmoCon 0%  AmmoHCon % eq4 % eq5 % eq6 % eq7 % eq8 %
 */	
public class LargeRecipes {
	
	private static Random rand = new Random();
	private static final byte MIN_AMOUNT = 100;		//material min amount
	private static final int BASE_POWER = 460800;		//base cost power
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
		for(int i = tile.SLOTS_OUT + 1; i < tile.SLOTS_NUM; i++) {
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
	public static boolean outputMaterialToSlot(TileMultiGrudgeHeavy tile, int selectMat, int i) {
		Item matchItem = null;
		int meta = 0;
		int slot = -1;
		
		//設定尋找的物品
		if(i == 0) {	//輸出單件物品
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
		else if(i == 1) {	//輸出block, container等壓縮物品
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
	public static boolean addMaterialStock(TileMultiGrudgeHeavy tile, int slot, int itemType) {
		int matType = -1;
		int matNum = 0;
		
		switch(itemType) {
		case 1:	//grudge item
			matType = 0;
			matNum = 1;
			break;
		case 2:	//grudge block
			matType = 0;
			matNum = 9;
			break;
		case 3:	//grudge block heavy
			matType = 0;
			matNum = 81;
			break;
		case 4:	//abyssium
			matType = 1;
			matNum = 1;
			break;
		case 5:	//abyssium block
			matType = 1;
			matNum = 9;
			break;
		case 6:	//light ammo
			matType = 2;
			matNum = 1;
			break;
		case 7:	//light ammo container
			matType = 2;
			matNum = 9;
			break;
		case 8:	//polymetal
			matType = 3;
			matNum = 1;
			break;
		case 9:	//polymetal block
			matType = 3;
			matNum = 9;
			break;
		}
		
		//資材存量最多1萬上下, 超過就不收
		int matStockCurrent;
		if(matType > -1) {
			matStockCurrent = tile.getMatStock(matType);
			if(matStockCurrent < 10000) {
				tile.setMatStock(matType, matStockCurrent + matNum);
				return true;
			}
		}

		
		return false;
	}
	
	//判定材料種類: -1:other 0:fuel 1~N:meterial
	public static byte getMaterialType(ItemStack itemstack) {
		Item item = null;
		byte itemType = -1;
		int meta = 0;	
		
		if(itemstack != null) {
			item = itemstack.getItem();
			meta = itemstack.getItemDamage();
			
			if(TileEntityFurnace.isItemFuel(itemstack)) { itemType = 0; }
			else if(item == ModItems.Grudge) { itemType = 1; }
			else if(item == Item.getItemFromBlock(ModBlocks.BlockGrudge)) { itemType = 2; }
//			else if(item == Item.getItemFromBlock(ModBlocks.BlockGrudgeHeavy)) { itemType = 3; }
			else if(item == ModItems.AbyssMetal && meta == 0) { itemType = 4; }
			else if(item == Item.getItemFromBlock(ModBlocks.BlockAbyssium)) { itemType = 5; }
			else if(item == ModItems.Ammo && meta == 0) { itemType = 6; }
			else if(item == ModItems.Ammo && meta == 1) { itemType = 7; }
			else if(item == ModItems.AbyssMetal && meta == 1) { itemType = 8; }
			else if(item == Item.getItemFromBlock(ModBlocks.BlockPolymetal)) { itemType = 9; }
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
	
	//將材料數量寫進itemstack回傳
	public static ItemStack getBuildResultEquip(int[] matAmount) {
		//計算材料總分: grudge 1.5p abyss 4p ammo 1p poly 2p
		float[] equipChance = new float[7];
		float pointMod = (matAmount[0]*1.5F + matAmount[1]*4F + matAmount[2]*1F + matAmount[3]*2F - 136F) / 408F;
		
		//equipChance: 0:AmmoContainer 1:AmmoHeavyContainer 2:eq4 3:eq5 4:eq6 5:eq7 6:eq8
		//此為累積機率(cumulate chance)
		equipChance[0] = 0.25F - 0.25F * pointMod;
		equipChance[1] = 0.25F - 0.25F * pointMod + equipChance[0];
		equipChance[2] = 0.3F - 0.1F * pointMod + equipChance[1];
		equipChance[3] = 0.15F + 0.1F * pointMod + equipChance[2];
		equipChance[4] = 0.05F + 0.15F * pointMod + equipChance[3];
		equipChance[5] = 0F + 0.2F * pointMod + equipChance[4];
		equipChance[6] = 0F + 0.15F * pointMod + equipChance[5];
		
		LogHelper.info("DEBUG : roll equip chance: "+String.format("%.2f", equipChance[0])+" "+
						String.format("%.2f", equipChance[1])+" "+String.format("%.2f", equipChance[2])+" "+
						String.format("%.2f", equipChance[3])+" "+String.format("%.2f", equipChance[4])+" "+
						String.format("%.2f", equipChance[5])+" "+String.format("%.2f", equipChance[6]));
		
		//roll
		float roll = rand.nextFloat();
		int rollResult = 0;
		//從array最後往前兩個開始比, 若骰的比i物品的機率高, 表示可以拿到第i+1個物品
		for(int i = (equipChance.length - 2); i >= 0; i--) {
			if(roll > equipChance[i]) {
				rollResult = i + 1;
				break;
			}
		}
		
		//get result item
		ItemStack buildResult = null;
		switch(rollResult) {
		case 0:
			buildResult = new ItemStack(ModItems.Ammo, 64, 1);
			break;
		case 1:
			buildResult = new ItemStack(ModItems.Ammo, 8+rand.nextInt(7), 3);
			break;
		case 2:
			buildResult = new ItemStack(ModItems.EquipCannon, 1, 4);
			break;
		case 3:
			buildResult = new ItemStack(ModItems.EquipCannon, 1, 5);
			break;
		case 4:
			buildResult = new ItemStack(ModItems.EquipCannon, 1, 6);
			break;
		case 5:
			buildResult = new ItemStack(ModItems.EquipCannon, 1, 7);
			break;
		case 6:
			buildResult = new ItemStack(ModItems.EquipCannon, 1, 8);
			break;
		}
		
		LogHelper.info("DEBUG : roll result: "+roll+" "+buildResult);
		return buildResult;
	}
	

}

