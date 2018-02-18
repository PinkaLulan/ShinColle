package com.lulan.shincolle.crafting;

import java.util.Random;

import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.TileEntityHelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**Small Shipyard Recipe Helper
 *  Fuel Cost = BaseCost + CostPerMaterial * ( TotalMaterialAmount - minAmount * 4 )
 *  Total Build Time = FuelCost / buildSpeed
 *  MaxBuildTime / MaxFuelCost = 8min / 460800  (48 fuel per tick)
 *  MinBuildTime / MinFuelCost = 1min / 57600
 * 	MaxMaterial / MaxFuelCost = 64*4 / 460800
 *  MinMaterial / MinFuelCost = 16*4 / 57600 = BaseCost(57600) CostPerMaterial(2100)
 *  
 *  
 * Equip Build Rate: first roll -> second roll -> third roll
 * 	 1. FIRST: roll ammo or equip
 *		ammo rate: total 64 (16x4) = 50%, total 128 = 0%
 *		equip rate: 1 - ammo
 *
 * 	 2. SECOND: if equip, roll equip type
 *              if ammo, roll ammo type and quantity
 *              
 * 	 3. THIRD: roll equips of the type
 */	
public class SmallRecipes
{
	
	private static Random rand = new Random();
	public static final int minAmount = 16;		//material min amount
	private static final int basePower = 57600;		//base cost power
	private static final int powerPerMat = 2100;	//cost per item
	
	public SmallRecipes() {}
		
	//檢查材料是否能夠建造
	public static boolean canRecipeBuild(int[] matAmount)
	{
		return matAmount[0] >= minAmount &&
			   matAmount[1] >= minAmount &&
			   matAmount[2] >= minAmount &&
			   matAmount[3] >= minAmount;
	}
	
	//計算總共需要的燃料
	public static int calcGoalPower(int[] matAmount)
	{
		int extraAmount;
		
		if (canRecipeBuild(matAmount))
		{
			extraAmount = matAmount[0] + matAmount[1] + matAmount[2] + matAmount[3] - (minAmount) * 4;
			return basePower + powerPerMat * extraAmount;
		}
		
		return 0;
	}
	
	//判定物品是否為材料
	public static boolean isMaterial(ItemStack itemstack)
	{
		if (itemstack != null)
		{
			Item item = itemstack.getItem();
			int meta = itemstack.getItemDamage();
			return (item == ModItems.Grudge)||
				   (item == ModItems.AbyssMetal && meta == 0)||
				   (item == ModItems.Ammo && meta == 0)||
				   (item == ModItems.AbyssMetal && meta == 1);
		}
		return false;
	}
	
	//判定材料種類: 0:grudge 1:abyss 2:ammo 3:poly 4:fuel -1:other
	public static int getMaterialType(ItemStack itemstack)
	{
		Item item = itemstack.getItem();
		int meta = itemstack.getItemDamage();
		int itemID = -1;
		
		if(item == ModItems.Grudge) itemID = 0;
		else if(item == ModItems.AbyssMetal && meta == 0) itemID = 1;
		else if(item == ModItems.Ammo && meta == 0) itemID = 2;
		else if(item == ModItems.AbyssMetal && meta == 1) itemID = 3;
		else if(TileEntityHelper.getItemFuelValue(itemstack) > 0)  itemID = 4;
		else if(item == ModItems.InstantConMat) itemID = 4;
		
		return itemID;
	}
	
	//取得四樣材料個數with null check
	//itemstack:0:grudge 1:abyss 2:ammo 3:poly 4:fuel 5:output
	public static int[] getMaterialAmount(ItemStack[] item)
	{
		int[] itemAmount = new int[4];
		
		for (int i = 0; i < 4; i++)
		{	//取得item 0~3的資料, 即四樣材料資料
			if (item[i] != null)
			{	//加上null判斷以免NPE
				itemAmount[i] = item[i].stackSize;
			}
			else
			{
				itemAmount[i] = 0;
			}
		}
		
		return itemAmount;		
	}
	
	//將材料數量寫進itemstack回傳
	public static ItemStack getBuildResultShip(int[] matAmount)
	{
		ItemStack buildResult = new ItemStack(ModItems.ShipSpawnEgg);
		NBTTagCompound tag = new NBTTagCompound();

		tag.setByte("Grudge", (byte)matAmount[0]);
		tag.setByte("Abyssium", (byte)matAmount[1]);
		tag.setByte("Ammo", (byte)matAmount[2]);
		tag.setByte("Polymetal", (byte)matAmount[3]);
		
		buildResult.setItemDamage(0);
		buildResult.setTagCompound(tag);
		
		return buildResult;
	}
	
	/** ROLL SYSTEM
	 *  0. get material amounts
	 *  1. roll junk or equips
	 *  2. roll equip type by mat.amounts
	 *  3. roll equip by equip type and mat.amounts
	 */
	public static ItemStack getBuildResultEquip(int[] matAmount)
	{	
		//result item
		ItemStack buildResult = null;
		int totalMats = matAmount[0] + matAmount[1] + matAmount[2] + matAmount[3];
		int[] matsInt = new int[] {0,0,0,0};
		int rollType = -1;
		float equipRate = totalMats / 128F;		//if total mats < 128, could get ammo
		float randRate = rand.nextFloat();
		
		if (equipRate > 1F) equipRate = 1F;	//min 50%, max 100%	
		LogHelper.debug("DEBUG : equip build roll: rate / random "+String.format("%.2f", equipRate)+" "+String.format("%.2f", randRate));	
		
		//first roll: roll equip or ammo
		if (randRate < equipRate)
		{
			//second roll: roll equip type
			matsInt[0] = matAmount[0];
			matsInt[1] = matAmount[1];
			matsInt[2] = matAmount[2];
			matsInt[3] = matAmount[3];
			rollType = EquipCalc.rollEquipType(0, matsInt);
			
			//third roll: roll equips of the type
			return EquipCalc.rollEquipsOfTheType(rollType, totalMats, 0);
		}
		else
		{
			//second roll: roll ammo type and quantity
			//50% for light or heavy ammo container
			if (rand.nextInt(2) == 0)
			{	
				buildResult = new ItemStack(ModItems.Ammo, 11+rand.nextInt(11), 1);
			}
			else
			{
				buildResult = new ItemStack(ModItems.Ammo, 2+rand.nextInt(2), 3);
			}
			
			return buildResult;	
		}
	}
	

}
