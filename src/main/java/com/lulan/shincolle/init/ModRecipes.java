package com.lulan.shincolle.init;

import com.lulan.shincolle.crafting.RecipeEnchantShell;
import com.lulan.shincolle.reference.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes
{
	//配方範例
	//GameRegistry.addSmelting(取得物, 材料, 0.1f經驗);
	//ForgeRegistries.RECIPES.register(new ShapedOreRecipe(取得物, " s ", "sss", " s ", 's', "stickWood" 材料陣列));
    //ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(取得物, 材料A, 材料B, ...));		
	public static void init()
	{
		//配方材料or成品
		ItemStack abyssiumBlock = new ItemStack(ModBlocks.BlockAbyssium);
		ItemStack abyssiumNugget = new ItemStack(ModItems.AbyssNugget, 1, 0);
		ItemStack abyssiumNugget9 = new ItemStack(ModItems.AbyssNugget, 9, 0);
		ItemStack abyssiumStack = new ItemStack(ModItems.AbyssMetal,1,0);
		ItemStack abyssiumStack9 = new ItemStack(ModItems.AbyssMetal,9,0);
		ItemStack ammo1 = new ItemStack(ModItems.Ammo,1,0);
		ItemStack ammo8 = new ItemStack(ModItems.Ammo,8,0); 
		ItemStack ammo9 = new ItemStack(ModItems.Ammo,9,0); 
		ItemStack ammo16 = new ItemStack(ModItems.Ammo,16,0);
		ItemStack ammo32 = new ItemStack(ModItems.Ammo,32,0);
		ItemStack ammo64 = new ItemStack(ModItems.Ammo,64,0);
		ItemStack ammoContainer = new ItemStack(ModItems.Ammo,1,1);
		ItemStack ammoHeavy1 = new ItemStack(ModItems.Ammo,1,2); 
		ItemStack ammoHeavy2 = new ItemStack(ModItems.Ammo,2,2);
		ItemStack ammoHeavy4 = new ItemStack(ModItems.Ammo,4,2);
		ItemStack ammoHeavy8 = new ItemStack(ModItems.Ammo,8,2);
		ItemStack ammoHeavy9 = new ItemStack(ModItems.Ammo,9,2);
		ItemStack ammoHeavyContainer = new ItemStack(ModItems.Ammo,1,3);
		ItemStack blazepowderStack = new ItemStack(Items.BLAZE_POWDER);
		ItemStack bucketRepairStack = new ItemStack(ModItems.BucketRepair);	
		ItemStack desk = new ItemStack(ModBlocks.BlockDesk);
		ItemStack deskBook = new ItemStack(ModItems.DeskItemBook);
		ItemStack deskRadar = new ItemStack(ModItems.DeskItemRadar);
		ItemStack grudeStack = new ItemStack(ModItems.Grudge);
		ItemStack grudeStack9 = new ItemStack(ModItems.Grudge,9,0);
		ItemStack grudeBlock = new ItemStack(ModBlocks.BlockGrudge);
		ItemStack grudeBlock2 = new ItemStack(ModBlocks.BlockGrudge,2,0);
		ItemStack grudeBlock9 = new ItemStack(ModBlocks.BlockGrudge,9,0);
		ItemStack grudeXPStack = new ItemStack(ModItems.Grudge, 1, 1);
		ItemStack grudeXPBlock = new ItemStack(ModBlocks.BlockGrudgeXP);
		ItemStack grudeHeavyBlock = new ItemStack(ModBlocks.BlockGrudgeHeavy);
		ItemStack grudeHeavyBlockDeco = new ItemStack(ModBlocks.BlockGrudgeHeavyDeco);
		ItemStack gunpowderStack = new ItemStack(Items.GUNPOWDER);
		ItemStack instantMat8 = new ItemStack(ModItems.InstantConMat, 8);
		ItemStack instantMat32 = new ItemStack(ModItems.InstantConMat, 64);
		ItemStack ironBlock = new ItemStack(Blocks.IRON_BLOCK);
		ItemStack kaitaiHammerNew = new ItemStack(ModItems.KaitaiHammer);
		ItemStack kaitaiHammerAll = new ItemStack(ModItems.KaitaiHammer, 1, OreDictionary.WILDCARD_VALUE);
		ItemStack modernKit = new ItemStack(ModItems.ModernKit);
		ItemStack magma = new ItemStack(Blocks.MAGMA);
		ItemStack ownerPaper = new ItemStack(ModItems.OwnerPaper);
		ItemStack pointer = new ItemStack(ModItems.PointerItem);
		ItemStack polymetalNugget = new ItemStack(ModItems.AbyssNugget, 1, 1);
		ItemStack polymetalNugget9 = new ItemStack(ModItems.AbyssNugget, 9, 1);
		ItemStack polymetalStack = new ItemStack(ModItems.AbyssMetal, 1, 1);
		ItemStack polymetalStack4 = new ItemStack(ModItems.AbyssMetal, 4, 1);
		ItemStack polymetalStack5 = new ItemStack(ModItems.AbyssMetal, 5, 1);
		ItemStack polymetalStack9 = new ItemStack(ModItems.AbyssMetal, 9, 1);
		ItemStack polymetalBlock = new ItemStack(ModBlocks.BlockPolymetal);
		ItemStack polymetalGravel = new ItemStack(ModBlocks.BlockPolymetalGravel);
		ItemStack ring = new ItemStack(ModItems.MarriageRing);
		ItemStack repairGoddess = new ItemStack(ModItems.RepairGoddess,1,0);
		ItemStack shipEgg = new ItemStack(ModItems.ShipSpawnEgg, 1, OreDictionary.WILDCARD_VALUE);	//for all meta value
		ItemStack shipEggS = new ItemStack(ModItems.ShipSpawnEgg, 1, 0);
		ItemStack shipEggL = new ItemStack(ModItems.ShipSpawnEgg, 1, 1);
		ItemStack smallshipyardStack = new ItemStack(ModBlocks.BlockSmallShipyard);
		ItemStack toyplane = new ItemStack(ModItems.ToyAirplane);
		ItemStack trainbook = new ItemStack(ModItems.TrainingBook);
		ItemStack wrench = new ItemStack(ModItems.TargetWrench);
		ItemStack volblock = new ItemStack(ModBlocks.BlockVolBlock);
		ItemStack volcore = new ItemStack(ModBlocks.BlockVolCore);
		ItemStack frame16 = new ItemStack(ModBlocks.BlockFrame, 16);
		ItemStack crane = new ItemStack(ModBlocks.BlockCrane);
		ItemStack waypoint = new ItemStack(ModBlocks.BlockWaypoint, 16);
		ItemStack ration0 = new ItemStack(ModItems.CombatRation, 1, 0);
		ItemStack ration1 = new ItemStack(ModItems.CombatRation, 1, 1);
		ItemStack ration2 = new ItemStack(ModItems.CombatRation, 1, 2);
		ItemStack ration3 = new ItemStack(ModItems.CombatRation, 1, 3);
		ItemStack ration4 = new ItemStack(ModItems.CombatRation, 1, 4);
		ItemStack ration5 = new ItemStack(ModItems.CombatRation, 1, 5);
		ItemStack shiptank0 = new ItemStack(ModItems.ShipTank, 1, 0);
		ItemStack shiptank1 = new ItemStack(ModItems.ShipTank, 1, 1);
		ItemStack shiptank2 = new ItemStack(ModItems.ShipTank, 1, 2);
		ItemStack shiptank3 = new ItemStack(ModItems.ShipTank, 1, 3);
		ItemStack recipepaper = new ItemStack(ModItems.RecipePaper);
		
		//SHAPELESS RECIPE
		//abyssium material:
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "abyssium"), abyssiumStack, "ingotIron", grudeStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "abyssium")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "grudge_bucket_repair"), bucketRepairStack, Items.LAVA_BUCKET, grudeStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "grudge_bucket_repair")));
		//1 item to N items
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo9"), ammo9, ammoContainer).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo9")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy9"), ammoHeavy9, ammoHeavyContainer).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy9")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "abyssium_nugget9"), abyssiumNugget9, abyssiumStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "abyssium_nugget9")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "abyssium_stack9"), abyssiumStack9, abyssiumBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "abyssium_stack9")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "abyssium_block1"), abyssiumBlock, ironBlock, grudeBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "abyssium_block1")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "grudge_stack9"), grudeStack9, grudeBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "grudge_stack9")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "grudge_block2"), grudeBlock2, grudeHeavyBlockDeco).setRegistryName(new ResourceLocation(Reference.MOD_ID, "grudge_block2")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "grudge_block9"), grudeBlock9, grudeHeavyBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "grudge_block9")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "grudge_heavy_deco"), grudeHeavyBlockDeco, grudeBlock, grudeBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "grudge_heavy_deco")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "polymetal_stack4"), polymetalStack4, polymetalGravel).setRegistryName(new ResourceLocation(Reference.MOD_ID, "polymetal_stack4")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "polymetal_stack9"), polymetalStack9, polymetalBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "polymetal_stack9")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "polymetal_gravel"), polymetalGravel, polymetalStack, polymetalStack, polymetalStack, polymetalStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "polymetal_gravel")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "polymetal_stack5"), polymetalStack5, toyplane).setRegistryName(new ResourceLocation(Reference.MOD_ID, "polymetal_stack5")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "polymetal_nugget9"), polymetalNugget9, polymetalStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "polymetal_nugget9")));
		//misc
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "instant_mat8"), instantMat8, kaitaiHammerAll, shipEggS).setRegistryName(new ResourceLocation(Reference.MOD_ID, "instant_mat8")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "instant_mat32"), instantMat32, kaitaiHammerAll, shipEggL).setRegistryName(new ResourceLocation(Reference.MOD_ID, "instant_mat32")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "owner_paper"), ownerPaper, grudeStack, Items.PAPER).setRegistryName(new ResourceLocation(Reference.MOD_ID, "owner_paper")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "waypoint"), waypoint, grudeStack, Items.STICK).setRegistryName(new ResourceLocation(Reference.MOD_ID, "waypoint")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "shinc_paper"), recipepaper, grudeStack, Items.PAPER, "gemLapis").setRegistryName(new ResourceLocation(Reference.MOD_ID, "shinc_paper")));
		//combat ration
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration0"), ration0, Items.BREAD, Items.GOLDEN_CARROT, grudeStack, "cookedRice").setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration0")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration1"), ration1, ration0, ModBlocks.BlockGrudge, ModBlocks.BlockPolymetalGravel).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration1")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration21"), ration2, Items.COOKED_BEEF, Items.GOLDEN_CARROT, grudeStack, "cookedRice").setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration21")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration22"), ration2, Items.COOKED_CHICKEN, Items.GOLDEN_CARROT, grudeStack, "cookedRice").setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration22")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration23"), ration2, Items.COOKED_FISH, Items.GOLDEN_CARROT, grudeStack, "cookedRice").setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration23")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration24"), ration2, Items.COOKED_MUTTON, Items.GOLDEN_CARROT, grudeStack, "cookedRice").setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration24")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration25"), ration2, Items.COOKED_PORKCHOP, Items.GOLDEN_CARROT, grudeStack, "cookedRice").setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration25")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration26"), ration2, Items.COOKED_RABBIT, Items.GOLDEN_CARROT, grudeStack, "cookedRice").setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration26")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration3"), ration3, ration2, ModBlocks.BlockGrudge, ModBlocks.BlockPolymetalGravel).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration3")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration4"), ration4, Items.SNOWBALL, Items.SNOWBALL, Items.SNOWBALL, Items.SNOWBALL, Items.MILK_BUCKET, ModItems.Grudge).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration4")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "ration5"), ration5, ration4, ModBlocks.BlockGrudge).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ration5")));
		//xp item
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "modern_kit"), modernKit, kaitaiHammerAll, wrench, grudeXPBlock, grudeXPBlock, grudeXPBlock, grudeXPBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "modern_kit")));
		ForgeRegistries.RECIPES.register(new ShapelessOreRecipe(new ResourceLocation(Reference.MOD_ID, "train_book"), trainbook, kaitaiHammerAll, modernKit, Items.WRITABLE_BOOK, grudeXPBlock, grudeXPBlock, grudeXPBlock, grudeXPBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "train_book")));
		
		//SHAPED RECIPE
		//ammo material: copper/tin/bronze=8 iron/lead=16 abyssium/steel/gold/silver=32 diamond/uranium=64
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo81"), ammo8,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo81")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo82"), ammo8,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo82")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo161"), ammo16,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo161")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo162"), ammo16,"iii","igi","ipi",'i',"ingotLead",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo162")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo83"), ammo8, "iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo83")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo321"), ammo32,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo321")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo322"), ammo32,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo322")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo323"), ammo32,"iii","igi","ipi",'i',"ingotSteel",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo323")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo324"), ammo32,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo324")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo641"), ammo64,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo641")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo642"), ammo64,"iii","igi","ipi",'i',"ingotUranium",'g',grudeStack,'p',gunpowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo642")));
		//heavy ammo: copper/tin/bronze=1 iron/lead=2 abyssium/steel/gold/silver=4 diamond/uranium=8
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy11"), ammoHeavy1,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy11")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy12"), ammoHeavy1,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy12")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy21"), ammoHeavy2,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy21")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy22"), ammoHeavy2,"iii","igi","ipi",'i',"ingotLead",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy22")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy13"), ammoHeavy1,"iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy13")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy41"), ammoHeavy4,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy41")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy42"), ammoHeavy4,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy42")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy43"), ammoHeavy4,"iii","igi","ipi",'i',"ingotSteel",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy43")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy44"), ammoHeavy4,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy44")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy81"), ammoHeavy8,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy81")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy82"), ammoHeavy8,"iii","igi","ipi",'i',"ingotUranium",'g',grudeStack,'p',blazepowderStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy82")));
		//9 items to 1 block
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_container"), ammoContainer,"aaa","aaa","aaa",'a',ammo1).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_container")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ammo_heavy_container"), ammoHeavyContainer,"aaa","aaa","aaa",'a',ammoHeavy1).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ammo_heavy_container")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "abyssium_block2"), abyssiumBlock,"aaa","aaa","aaa",'a',abyssiumStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "abyssium_block2")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "abyssium_stack"), abyssiumStack,"aaa","aaa","aaa",'a',abyssiumNugget).setRegistryName(new ResourceLocation(Reference.MOD_ID, "abyssium_stack")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "grudge_block"), grudeBlock,"aaa","aaa","aaa",'a',grudeStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "grudge_block")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "grudge_heavy_block"), grudeHeavyBlock,"aaa","aaa","aaa",'a',grudeBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "grudge_heavy_block")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "polymetal_block"), polymetalBlock,"aaa","aaa","aaa",'a',polymetalStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "polymetal_block")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "polymetal_stack"), polymetalStack,"aaa","aaa","aaa",'a',polymetalNugget).setRegistryName(new ResourceLocation(Reference.MOD_ID, "polymetal_stack")));
		//desk item
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "desk"), desk,"abc","ooo","o o",'a',deskRadar,'b',deskBook,'c',Blocks.WOOL,'o',Blocks.OBSIDIAN).setRegistryName(new ResourceLocation(Reference.MOD_ID, "desk")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "desk_book"), deskBook,"ggg","gbg","ggg",'g',grudeStack,'b',Items.WRITABLE_BOOK).setRegistryName(new ResourceLocation(Reference.MOD_ID, "desk_book")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "desk_radar"), deskRadar,"ggg","gbg","ggg",'g',grudeStack,'b',Items.COMPASS).setRegistryName(new ResourceLocation(Reference.MOD_ID, "desk_radar")));
		//small shipyard
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "small_shipyard_stack"), smallshipyardStack,"glg","lol","ooo",'g',grudeStack,'l',Items.LAVA_BUCKET,'o',Blocks.OBSIDIAN).setRegistryName(new ResourceLocation(Reference.MOD_ID, "small_shipyard_stack")));
		//marriage ring
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "wedding_ring"), ring," s ","a a"," a ",'s',Items.NETHER_STAR,'a',abyssiumStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "wedding_ring")));
		//kaitai hammer
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "kaitai_hammer"), kaitaiHammerNew,"aaa","aaa"," s ",'s',Items.STICK,'a',abyssiumStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "kaitai_hammer")));
		//pointer staff
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "pointer_staff"), pointer,"  g"," p ","p  ",'g',grudeBlock,'p',polymetalStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "pointer_staff")));
		//toy plane
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "toy_plane"), toyplane," a ","aaa"," a ",'a',polymetalStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "toy_plane")));
		//goddess
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "repair_goddess1"), repairGoddess,"hgh","gdg","hgh",'d',Blocks.DIAMOND_BLOCK,'g',grudeBlock,'h',grudeHeavyBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "repair_goddess1")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "repair_goddess2"), repairGoddess,"ghg","hdh","ghg",'d',Blocks.DIAMOND_BLOCK,'g',grudeBlock,'h',grudeHeavyBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "repair_goddess2")));
		//wrench
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "wrench"), wrench,"a a","aaa"," a ",'a',abyssiumStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "wrench")));
		//volcano block
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "volcano_block1"), volblock,"gog","olo","gog",'g',magma,'l',grudeBlock,'o',Blocks.OBSIDIAN).setRegistryName(new ResourceLocation(Reference.MOD_ID, "volcano_block1")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "volcano_block2"), volblock,"gog","olo","gog",'g',Blocks.OBSIDIAN,'l',grudeBlock,'o',magma).setRegistryName(new ResourceLocation(Reference.MOD_ID, "volcano_block2")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "volcano_core1"), volcore,"gog","olo","gog",'g',volblock,'l',grudeHeavyBlock,'o',Blocks.OBSIDIAN).setRegistryName(new ResourceLocation(Reference.MOD_ID, "volcano_core1")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "volcano_core2"), volcore,"gog","olo","gog",'g',Blocks.OBSIDIAN,'l',grudeHeavyBlock,'o',volblock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "volcano_core2")));
		//frame
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "frame16"), frame16,"a a"," o ","a a",'o',Blocks.OBSIDIAN,'a',abyssiumStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "frame16")));
		//crane
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "crane"), crane,"aaa","aga","apa",'a',abyssiumStack,'p',Blocks.PISTON,'g',grudeBlock).setRegistryName(new ResourceLocation(Reference.MOD_ID, "crane")));
		//combat ration
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration0"), ration0,"www","bgc","www",'b',Items.BREAD,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT).setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration0")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration21"), ration2,"www","bgc","www",'b',Items.COOKED_BEEF,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT).setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration21")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration22"), ration2,"www","bgc","www",'b',Items.COOKED_CHICKEN,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT).setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration22")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration23"), ration2,"www","bgc","www",'b',Items.COOKED_FISH,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT).setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration23")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration24"), ration2,"www","bgc","www",'b',Items.COOKED_MUTTON,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT).setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration24")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration25"), ration2,"www","bgc","www",'b',Items.COOKED_PORKCHOP,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT).setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration25")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration26"), ration2,"www","bgc","www",'b',Items.COOKED_RABBIT,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT).setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration26")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration27"), ration0,"www","bgc","www",'b',Items.BREAD,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour").setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration27")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration28"), ration2,"www","bgc","www",'b',Items.COOKED_BEEF,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour").setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration28")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration29"), ration2,"www","bgc","www",'b',Items.COOKED_CHICKEN,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour").setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration29")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration210"), ration2,"www","bgc","www",'b',Items.COOKED_FISH,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour").setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration210")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration211"), ration2,"www","bgc","www",'b',Items.COOKED_MUTTON,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour").setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration211")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration212"), ration2,"www","bgc","www",'b',Items.COOKED_PORKCHOP,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour").setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration212")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "combat_ration213"), ration2,"www","bgc","www",'b',Items.COOKED_RABBIT,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour").setRegistryName(new ResourceLocation(Reference.MOD_ID, "combat_ration213")));
		//ship tank
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ship_tank0"), shiptank0,"mtm","mtm","mtm",'m',polymetalStack,'t',Items.CAULDRON).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ship_tank0")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ship_tank1"), shiptank1,"mtm","mtm","mtm",'m',Blocks.OBSIDIAN,'t',shiptank0).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ship_tank1")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ship_tank2"), shiptank2,"mtm","mtm","mtm",'m',abyssiumBlock,'t',shiptank1).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ship_tank2")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "ship_tank3"), shiptank3,"mtm","mtm","mtm",'m',grudeHeavyBlock,'t',shiptank2).setRegistryName(new ResourceLocation(Reference.MOD_ID, "ship_tank3")));
		//enchant shell
		ForgeRegistries.RECIPES.register(new RecipeEnchantShell().setRegistryName(new ResourceLocation(Reference.MOD_ID, "enchant_shell")));
		//xp item
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "grudge_xp_stack"), grudeXPStack,"xxx","xgx","xxx",'g',grudeStack,'x',Items.EXPERIENCE_BOTTLE).setRegistryName(new ResourceLocation(Reference.MOD_ID, "grudge_xp_stack")));
		ForgeRegistries.RECIPES.register(new ShapedOreRecipe(new ResourceLocation(Reference.MOD_ID, "grudge_xp_block"), grudeXPBlock,"xxx","xxx","xxx",'x',grudeXPStack).setRegistryName(new ResourceLocation(Reference.MOD_ID, "grudge_xp_block")));
	}

	
}