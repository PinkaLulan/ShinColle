package com.lulan.shincolle.intermod.forestry;

import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.reference.Reference;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.core.EnumHumidity;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IClassification;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

/** bee template
 *  蜜蜂屬性設定
 */
public class ShinBeeTemplate
{
	
	public static IClassification beebranch;
	public static IAlleleFlowers flower;
	public static IAlleleBeeSpecies bee1;
	public static IAlleleBeeSpecies bee2;
	public static IAlleleBeeSpecies bee3;
	
	
	//init bees
	public static void initBees()
	{
		/** 註冊品種 */
		beebranch = BeeManager.beeFactory.createBranch("abyss", "Abyssapis");
		AlleleManager.alleleRegistry.getClassification("family.apidae").addMemberGroup(ShinBeeTemplate.beebranch);
		
		/** 註冊蜜蜂
		 *  bee1: demon
		 *  bee2: princess
		 *  bee3: water demon
		 */
		bee1 = (IAlleleBeeSpecies) BeeManager.beeFactory.createSpecies(Reference.MOD_ID + ".abyssdemon",
				true, "Abyssal Admiral", "inter.shincolle:bee1.name",
				I18n.format("inter.shincolle:bee1.desc"), beebranch,
				"demonic", 0xFF0000, 0x7F0000)
				.setJubilanceProvider(new ShinJubilance())
				.addProduct(new ItemStack(ShinBees.ShinComb, 1), 0.2F)
				.setNocturnal()
				.setHumidity(EnumHumidity.DAMP);
		
		bee2 = (IAlleleBeeSpecies) BeeManager.beeFactory.createSpecies(Reference.MOD_ID + ".abyssprincess",
				true, "Abyssal Admiral", "inter.shincolle:bee2.name",
				I18n.format("inter.shincolle:bee2.desc"), beebranch,
				"princess", 0xFF0000, 0x7F0000)
				.setJubilanceProvider(new ShinJubilance())
				.addProduct(new ItemStack(ShinBees.ShinComb, 1), 0.2F)
				.addProduct(new ItemStack(ModItems.Grudge, 1, 0), 0.1F)
				.setNocturnal()
				.setHumidity(EnumHumidity.DAMP);
		
		bee3 = (IAlleleBeeSpecies) BeeManager.beeFactory.createSpecies(Reference.MOD_ID + ".abysswaterdemon",
				true, "Abyssal Admiral", "inter.shincolle:bee3.name",
				I18n.format("inter.shincolle:bee3.desc"), beebranch,
				"waterdemonic", 0xFF0000, 0x7F0000)
				.setJubilanceProvider(new ShinJubilance())
				.addProduct(new ItemStack(ShinBees.ShinComb, 1), 0.2F)
				.addProduct(new ItemStack(ModItems.Grudge, 1, 0), 0.1F)
				.addSpecialty(new ItemStack(ModItems.AbyssNugget, 1, 1), 0.05F)
				.setNocturnal()
				.setHumidity(EnumHumidity.DAMP)
				.setHasEffect();
		
		/** 註冊蜜蜂屬性樣板 */
		BeeManager.beeRoot.registerTemplate(getTempBee1());
		BeeManager.beeRoot.registerTemplate(getTempBee2());
		BeeManager.beeRoot.registerTemplate(getTempBee3());
	}
	
	//base bee template
	public static IAllele[] getTempBaseBee()
	{
		IAllele[] temp = new IAllele[EnumBeeChromosome.values().length];
		
		temp[EnumBeeChromosome.SPECIES.ordinal()] = null;
		temp[EnumBeeChromosome.SPEED.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.speedSlow");
		temp[EnumBeeChromosome.LIFESPAN.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.lifespanLong");
		temp[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.toleranceNone");
		temp[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.toleranceDown1");
		temp[EnumBeeChromosome.TERRITORY.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.territoryAverage");
		temp[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.effectAggressive");
		temp[EnumBeeChromosome.FERTILITY.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.fertilityNormal");
		temp[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = flower;
		temp[EnumBeeChromosome.FLOWERING.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.floweringSlowest");
		temp[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolTrue");
		temp[EnumBeeChromosome.NEVER_SLEEPS.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolTrue");
		temp[EnumBeeChromosome.TOLERATES_RAIN.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolTrue");

		return temp;
	}
	
	//bee level 1
	public static IAllele[] getTempBee1()
	{
		IAllele[] temp = getTempBaseBee().clone();
		
		temp[EnumBeeChromosome.SPECIES.ordinal()] = bee1;
		
		return temp;
	}
	
	//bee level 2
	public static IAllele[] getTempBee2()
	{
		IAllele[] temp = getTempBee1().clone();
		
		temp[EnumBeeChromosome.SPECIES.ordinal()] = bee2;
		temp[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.toleranceDown2");
		
		return temp;
	}
	
	//bee level 3
	public static IAllele[] getTempBee3()
	{
		IAllele[] temp = getTempBee2().clone();
		
		temp[EnumBeeChromosome.SPECIES.ordinal()] = bee3;
		temp[EnumBeeChromosome.LIFESPAN.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.lifespanLonger");
		temp[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.effectMisanthrope");
		
		return temp;
	}
	
}
