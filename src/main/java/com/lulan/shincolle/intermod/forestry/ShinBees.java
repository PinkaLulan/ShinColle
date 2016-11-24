package com.lulan.shincolle.intermod.forestry;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicItem;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.FlowerManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.recipes.RecipeManagers;

/** add forestry bees
 *
 *
 *  species名稱取得方法:
 *    AlleleManager.alleleRegistry.getSpeciesRoot() 中有三個root: rootBees, rootTrees, rootButterflies
 *    AlleleManager.alleleRegistry.getSpeciesRoot("rootBees").getGenomeTemplates() 取得註冊species列表
 *    
 *  branch列表:
 *      genus.bees.honey           genus.bees.noble            genus.bees.industrious 
 *      genus.bees.heroic          genus.bees.infernal         genus.bees.austere 
 *      genus.bees.tropical        genus.bees.end              genus.bees.frozen 
 *      genus.bees.vengeful        genus.bees.festive          genus.bees.agrarian 
 *      genus.bees.boggy           genus.bees.monastic  
 *    
 *  species列表:
 *      forestry.speciesIcy        forestry.speciesAvenging    forestry.speciesAgrarian
 *		forestry.speciesVengeful   forestry.speciesHermitic    forestry.speciesExotic
 *		forestry.speciesImperial   forestry.speciesUnweary     forestry.speciesValiant
 *		forestry.speciesBoggy      forestry.speciesDiligent    forestry.speciesGlacial
 *		forestry.speciesSpectral   forestry.speciesWintry      forestry.speciesRural
 *		forestry.speciesMonastic   forestry.speciesIndustrious forestry.speciesForest
 *		forestry.speciesAustere    forestry.speciesEdenic      forestry.speciesMajestic
 *		forestry.speciesTricky     forestry.speciesMarshy      forestry.speciesMeadows
 *		forestry.speciesMerry      forestry.speciesTropical    forestry.speciesCommon
 *		forestry.speciesCultivated forestry.speciesSteadfast   forestry.speciesFiendish
 *		forestry.speciesDemonic    forestry.speciesNoble       forestry.speciesEnded
 *		forestry.speciesPhantasmal forestry.speciesSecluded    forestry.speciesFarmerly
 *		forestry.speciesMiry       forestry.speciesHeroic      forestry.speciesVindictive
 *		forestry.speciesModest     forestry.speciesSinister    forestry.speciesLeporine
 *		forestry.speciesTipsy      forestry.speciesFrugal
 *
 *  effect列表:
 *      forestry.effectNone           無效果
 *      forestry.effectAggressive     傷害附近所有生物
 *      forestry.effectHeroic         傷害附近敵對生物
 *      forestry.effectBeatific       附近玩家獲得持續回復藥水5秒
 *      forestry.effectMiasmic        附近玩家中毒40秒
 *      forestry.effectMisanthrope    重傷害附近玩家
 *      forestry.effectGlacial        在溫度normal以下的生態系，轉換附近水方塊為冰
 *      forestry.effectRadioactive    緩慢持續低傷害附近玩家
 *      forestry.effectCreeper        附近玩家被creeper炸到時附加額外傷害
 *      forestry.effectIgnition       點燃附近生物
 *      forestry.effectExploration    附近玩家緩慢增加經驗
 *      forestry.effectFestiveEaster  特殊節日特效
 *      forestry.effectSnowing        蜂巢附近下雪
 *      forestry.effectDrunkard       附近玩家獲得酒醉效果
 *      forestry.effectReanimation    使附近骷髏/髒比/烈焰怪掉落物變成該怪物
 *      forestry.effectResurrection   使附近苦力怕/安得/蜘蛛/水母/終界龍掉落物變回怪物
 *      forestry.effectRepulsion      使附近怪物遠離玩家
 *      forestry.effectFertile        附近作物隨機獲得骨粉效果
 *      forestry.effectMycophilic     附近土隨機變成菌絲
 *      
 *  屬性列表:    
 *      forestry.speedSlowest
		forestry.speedSlower
		forestry.speedSlow
		forestry.speedNormal
		forestry.speedFast
		forestry.speedFaster
		forestry.speedFastest

		forestry.lifespanShortest
		forestry.lifespanShortened
		forestry.lifespanShorter
		forestry.lifespanShort
		forestry.lifespanNormal
		forestry.lifespanLong
		forestry.lifespanLonger
		forestry.lifespanLongest

		forestry.fertilityLow
		forestry.fertilityNormal
		forestry.fertilityHigh
		forestry.fertilityMaximum

		forestry.toleranceDown2
		forestry.toleranceDown1
		forestry.toleranceNone
		forestry.toleranceUp1
		forestry.toleranceUp2
		forestry.toleranceBoth1
		forestry.toleranceBoth2

		forestry.boolFalse
		forestry.boolTrue

		forestry.flowersVanilla
		forestry.flowersWheat
		forestry.flowersJungle
		forestry.flowersMushrooms
		forestry.flowersSnow
		forestry.flowersCacti
		forestry.flowersGourd
		forestry.flowersEnd
		forestry.flowersNether

		forestry.floweringSlowest
		forestry.floweringFast
		forestry.floweringFaster
		forestry.floweringFastest

		forestry.territoryDefault
		forestry.territoryAverage
		forestry.territoryLarge
		forestry.territoryLargest

 */
public class ShinBees {

	public static final BasicItem ShinComb = new ShinComb();
	
	
	/** 此方法只在forestry有載入時存在 */
	@Optional.Method(modid = Reference.MOD_ID_Forestry)
	public static void init()
	{
		/** 註冊蜂巢 */
		GameRegistry.registerItem(ShinComb, "ShinComb");
		OreDictionary.registerOre("beeComb", new ItemStack(ShinComb, 1, OreDictionary.WILDCARD_VALUE));
		
		/** 註冊蜂巢分離機配方 */
		Map<ItemStack, Float> output = new HashMap();
		output.put(new ItemStack(ModItems.Grudge, 1, 0), 1f);
		output.put(new ItemStack(ModItems.AbyssNugget, 1, 0), 0.6f);
		output.put(new ItemStack(ModItems.AbyssNugget, 1, 1), 0.1f);
		RecipeManagers.centrifugeManager.addRecipe(20, new ItemStack(ShinComb, 1, OreDictionary.WILDCARD_VALUE), output);
		
		/** 註冊蜜蜂使用的花, 注意花必須比蜜蜂先註冊 */
		ShinFlowerProvider fprovider = new ShinFlowerProvider();
		ShinBeeTemplate.flower = AlleleManager.alleleFactory.createFlowers(Reference.MOD_ID, "flower", "grudge", fprovider, true, EnumBeeChromosome.FLOWER_PROVIDER);
		FlowerManager.flowerRegistry.registerAcceptableFlower(ModBlocks.BlockGrudge, fprovider.getFlowerType());
		FlowerManager.flowerRegistry.registerAcceptableFlower(ModBlocks.BlockGrudgeHeavy, fprovider.getFlowerType());
		
		/** 註冊蜜蜂 */
		ShinBeeTemplate.initBees();
		
		 /** 註冊突變方法 */
		BeeManager.beeMutationFactory.createMutation(
				(IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesDemonic"),
				(IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesAvenging"),
				ShinBeeTemplate.getTempBee1(), 15);
		
		BeeManager.beeMutationFactory.createMutation(
				ShinBeeTemplate.bee1,
				(IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesImperial"),
				ShinBeeTemplate.getTempBee2(), 10);
		
		BeeManager.beeMutationFactory.createMutation(
				ShinBeeTemplate.bee2,
				(IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesPhantasmal"),
				ShinBeeTemplate.getTempBee3(), 5);
	}
	
	
}
