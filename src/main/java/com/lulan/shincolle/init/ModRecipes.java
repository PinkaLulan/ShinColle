package com.lulan.shincolle.init;

import com.lulan.shincolle.crafting.RecipeEnchantShell;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;

public class ModRecipes
{
	//配方範例
	//GameRegistry.addSmelting(取得物, 材料, 0.1f經驗);
	//GameRegistry.addRecipe(new ShapedOreRecipe(取得物, " s ", "sss", " s ", 's', "stickWood" 材料陣列));
    //GameRegistry.addRecipe(new ShapelessOreRecipe(取得物, 材料A, 材料B, ...));		
	public static void register(RegistryEvent.Register<IRecipe> event)
	{
		event.getRegistry().register(new RecipeEnchantShell());
		
		
		
		/** dep, mc1.10.2 */
		//配方材料or成品
//		ItemStack abyssiumBlock = new ItemStack(ModBlocks.BlockAbyssium);
//		ItemStack abyssiumNugget = new ItemStack(ModItems.AbyssNugget, 1, 0);
//		ItemStack abyssiumNugget9 = new ItemStack(ModItems.AbyssNugget, 9, 0);
//		ItemStack abyssiumStack = new ItemStack(ModItems.AbyssMetal,1,0);
//		ItemStack abyssiumStack9 = new ItemStack(ModItems.AbyssMetal,9,0);
//		ItemStack ammo1 = new ItemStack(ModItems.Ammo,1,0);
//		ItemStack ammo8 = new ItemStack(ModItems.Ammo,8,0); 
//		ItemStack ammo9 = new ItemStack(ModItems.Ammo,9,0); 
//		ItemStack ammo16 = new ItemStack(ModItems.Ammo,16,0);
//		ItemStack ammo32 = new ItemStack(ModItems.Ammo,32,0);
//		ItemStack ammo64 = new ItemStack(ModItems.Ammo,64,0);
//		ItemStack ammoContainer = new ItemStack(ModItems.Ammo,1,1);
//		ItemStack ammoHeavy1 = new ItemStack(ModItems.Ammo,1,2); 
//		ItemStack ammoHeavy2 = new ItemStack(ModItems.Ammo,2,2);
//		ItemStack ammoHeavy4 = new ItemStack(ModItems.Ammo,4,2);
//		ItemStack ammoHeavy8 = new ItemStack(ModItems.Ammo,8,2);
//		ItemStack ammoHeavy9 = new ItemStack(ModItems.Ammo,9,2);
//		ItemStack ammoHeavyContainer = new ItemStack(ModItems.Ammo,1,3);
//		ItemStack blazepowderStack = new ItemStack(Items.BLAZE_POWDER);
//		ItemStack bucketRepairStack = new ItemStack(ModItems.BucketRepair);	
//		ItemStack desk = new ItemStack(ModBlocks.BlockDesk);
//		ItemStack deskBook = new ItemStack(ModItems.DeskItemBook);
//		ItemStack deskRadar = new ItemStack(ModItems.DeskItemRadar);
//		ItemStack grudeStack = new ItemStack(ModItems.Grudge);
//		ItemStack grudeStack9 = new ItemStack(ModItems.Grudge,9,0);
//		ItemStack grudeBlock = new ItemStack(ModBlocks.BlockGrudge);
//		ItemStack grudeBlock2 = new ItemStack(ModBlocks.BlockGrudge,2,0);
//		ItemStack grudeBlock9 = new ItemStack(ModBlocks.BlockGrudge,9,0);
//		ItemStack grudeXPStack = new ItemStack(ModItems.Grudge, 1, 1);
//		ItemStack grudeXPBlock = new ItemStack(ModBlocks.BlockGrudgeXP);
//		ItemStack grudeHeavyBlock = new ItemStack(ModBlocks.BlockGrudgeHeavy);
//		ItemStack grudeHeavyBlockDeco = new ItemStack(ModBlocks.BlockGrudgeHeavyDeco);
//		ItemStack gunpowderStack = new ItemStack(Items.GUNPOWDER);
//		ItemStack instantMat8 = new ItemStack(ModItems.InstantConMat, 8);
//		ItemStack instantMat32 = new ItemStack(ModItems.InstantConMat, 64);
//		ItemStack ironBlock = new ItemStack(Blocks.IRON_BLOCK);
//		ItemStack kaitaiHammerNew = new ItemStack(ModItems.KaitaiHammer);
//		ItemStack kaitaiHammerAll = new ItemStack(ModItems.KaitaiHammer, 1, OreDictionary.WILDCARD_VALUE);
//		ItemStack modernKit = new ItemStack(ModItems.ModernKit);
//		ItemStack magma = new ItemStack(Blocks.MAGMA);
//		ItemStack ownerPaper = new ItemStack(ModItems.OwnerPaper);
//		ItemStack pointer = new ItemStack(ModItems.PointerItem);
//		ItemStack polymetalNugget = new ItemStack(ModItems.AbyssNugget, 1, 1);
//		ItemStack polymetalNugget9 = new ItemStack(ModItems.AbyssNugget, 9, 1);
//		ItemStack polymetalStack = new ItemStack(ModItems.AbyssMetal, 1, 1);
//		ItemStack polymetalStack4 = new ItemStack(ModItems.AbyssMetal, 4, 1);
//		ItemStack polymetalStack5 = new ItemStack(ModItems.AbyssMetal, 5, 1);
//		ItemStack polymetalStack9 = new ItemStack(ModItems.AbyssMetal, 9, 1);
//		ItemStack polymetalBlock = new ItemStack(ModBlocks.BlockPolymetal);
//		ItemStack polymetalGravel = new ItemStack(ModBlocks.BlockPolymetalGravel);
//		ItemStack ring = new ItemStack(ModItems.MarriageRing);
//		ItemStack repairGoddess = new ItemStack(ModItems.RepairGoddess,1,0);
//		ItemStack shipEgg = new ItemStack(ModItems.ShipSpawnEgg, 1, OreDictionary.WILDCARD_VALUE);	//for all meta value
//		ItemStack shipEggS = new ItemStack(ModItems.ShipSpawnEgg, 1, 0);
//		ItemStack shipEggL = new ItemStack(ModItems.ShipSpawnEgg, 1, 1);
//		ItemStack smallshipyardStack = new ItemStack(ModBlocks.BlockSmallShipyard);
//		ItemStack toyplane = new ItemStack(ModItems.ToyAirplane);
//		ItemStack trainbook = new ItemStack(ModItems.TrainingBook);
//		ItemStack wrench = new ItemStack(ModItems.TargetWrench);
//		ItemStack volblock = new ItemStack(ModBlocks.BlockVolBlock);
//		ItemStack volcore = new ItemStack(ModBlocks.BlockVolCore);
//		ItemStack frame16 = new ItemStack(ModBlocks.BlockFrame, 16);
//		ItemStack crane = new ItemStack(ModBlocks.BlockCrane);
//		ItemStack waypoint = new ItemStack(ModBlocks.BlockWaypoint, 16);
//		ItemStack ration0 = new ItemStack(ModItems.CombatRation, 1, 0);
//		ItemStack ration1 = new ItemStack(ModItems.CombatRation, 1, 1);
//		ItemStack ration2 = new ItemStack(ModItems.CombatRation, 1, 2);
//		ItemStack ration3 = new ItemStack(ModItems.CombatRation, 1, 3);
//		ItemStack ration4 = new ItemStack(ModItems.CombatRation, 1, 4);
//		ItemStack ration5 = new ItemStack(ModItems.CombatRation, 1, 5);
//		ItemStack shiptank0 = new ItemStack(ModItems.ShipTank, 1, 0);
//		ItemStack shiptank1 = new ItemStack(ModItems.ShipTank, 1, 1);
//		ItemStack shiptank2 = new ItemStack(ModItems.ShipTank, 1, 2);
//		ItemStack shiptank3 = new ItemStack(ModItems.ShipTank, 1, 3);
//		ItemStack recipepaper = new ItemStack(ModItems.RecipePaper);
		//SHAPELESS RECIPE
		//abyssium material:
//		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumStack, "ingotIron", grudeStack));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(bucketRepairStack, Items.LAVA_BUCKET, grudeStack));		
		//1 item to N items
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ammo9, ammoContainer));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ammoHeavy9, ammoHeavyContainer));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumNugget9, abyssiumStack));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumStack9, abyssiumBlock));
//removed		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumBlock, ironBlock, grudeBlock));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(grudeStack9, grudeBlock));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(grudeBlock2, grudeHeavyBlockDeco));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(grudeBlock9, grudeHeavyBlock));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(grudeHeavyBlockDeco, grudeBlock, grudeBlock));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalStack4, polymetalGravel));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalStack9, polymetalBlock));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalGravel, polymetalStack, polymetalStack, polymetalStack, polymetalStack));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalStack5, toyplane));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalNugget9, polymetalStack));
		//misc
//		GameRegistry.addRecipe(new ShapelessOreRecipe(instantMat8, kaitaiHammerAll, shipEggS));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(instantMat32, kaitaiHammerAll, shipEggL));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ownerPaper, grudeStack, Items.PAPER));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(waypoint, grudeStack, Items.STICK));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(recipepaper, grudeStack, Items.PAPER, "gemLapis"));
		//combat ration
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration0, Items.BREAD, Items.GOLDEN_CARROT, grudeStack, "cookedRice"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration1, ration0, ModBlocks.BlockGrudge, ModBlocks.BlockPolymetalGravel));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.COOKED_BEEF, Items.GOLDEN_CARROT, grudeStack, "cookedRice"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.COOKED_CHICKEN, Items.GOLDEN_CARROT, grudeStack, "cookedRice"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.COOKED_FISH, Items.GOLDEN_CARROT, grudeStack, "cookedRice"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.COOKED_MUTTON, Items.GOLDEN_CARROT, grudeStack, "cookedRice"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.COOKED_PORKCHOP, Items.GOLDEN_CARROT, grudeStack, "cookedRice"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.COOKED_RABBIT, Items.GOLDEN_CARROT, grudeStack, "cookedRice"));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration3, ration2, ModBlocks.BlockGrudge, ModBlocks.BlockPolymetalGravel));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration4, Items.SNOWBALL, Items.SNOWBALL, Items.SNOWBALL, Items.SNOWBALL, Items.MILK_BUCKET, ModItems.Grudge));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(ration5, ration4, ModBlocks.BlockGrudge));
		//xp item
//		GameRegistry.addRecipe(new ShapelessOreRecipe(modernKit, kaitaiHammerAll, wrench, grudeXPBlock, grudeXPBlock, grudeXPBlock, grudeXPBlock));
//		GameRegistry.addRecipe(new ShapelessOreRecipe(trainbook, kaitaiHammerAll, modernKit, Items.WRITABLE_BOOK, grudeXPBlock, grudeXPBlock, grudeXPBlock, grudeXPBlock));
		
		//SHAPED RECIPE
		//ammo material: copper/tin/bronze=8 iron/lead=16 abyssium/steel/gold/silver=32 diamond/uranium=64
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',gunpowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',gunpowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo16,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',gunpowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo16,"iii","igi","ipi",'i',"ingotLead",'g',grudeStack,'p',gunpowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',gunpowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',gunpowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',gunpowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotSteel",'g',grudeStack,'p',gunpowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',gunpowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo64,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',gunpowderStack));		
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammo64,"iii","igi","ipi",'i',"ingotUranium",'g',grudeStack,'p',gunpowderStack));		
		//heavy ammo: copper/tin/bronze=1 iron/lead=2 abyssium/steel/gold/silver=4 diamond/uranium=8
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',blazepowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',blazepowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy2,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',blazepowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy2,"iii","igi","ipi",'i',"ingotLead",'g',grudeStack,'p',blazepowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',blazepowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',blazepowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',blazepowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotSteel",'g',grudeStack,'p',blazepowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',blazepowderStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy8,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',blazepowderStack));		
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy8,"iii","igi","ipi",'i',"ingotUranium",'g',grudeStack,'p',blazepowderStack));		
		//9 items to 1 block
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoContainer,"aaa","aaa","aaa",'a',ammo1));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavyContainer,"aaa","aaa","aaa",'a',ammoHeavy1));
//		GameRegistry.addRecipe(new ShapedOreRecipe(abyssiumBlock,"aaa","aaa","aaa",'a',abyssiumStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(abyssiumStack,"aaa","aaa","aaa",'a',abyssiumNugget));
//		GameRegistry.addRecipe(new ShapedOreRecipe(grudeBlock,"aaa","aaa","aaa",'a',grudeStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(grudeHeavyBlock,"aaa","aaa","aaa",'a',grudeBlock));
//		GameRegistry.addRecipe(new ShapedOreRecipe(polymetalBlock,"aaa","aaa","aaa",'a',polymetalStack));
//		GameRegistry.addRecipe(new ShapedOreRecipe(polymetalStack,"aaa","aaa","aaa",'a',polymetalNugget));
		//desk item
//		GameRegistry.addRecipe(new ShapedOreRecipe(desk,"abc","ooo","o o",'a',deskRadar,'b',deskBook,'c',Blocks.WOOL,'o',Blocks.OBSIDIAN));
//		GameRegistry.addRecipe(new ShapedOreRecipe(deskBook,"ggg","gbg","ggg",'g',grudeStack,'b',Items.WRITABLE_BOOK));
//		GameRegistry.addRecipe(new ShapedOreRecipe(deskRadar,"ggg","gbg","ggg",'g',grudeStack,'b',Items.COMPASS));
		//small shipyard
//		GameRegistry.addRecipe(new ShapedOreRecipe(smallshipyardStack,"glg","lol","ooo",'g',grudeStack,'l',Items.LAVA_BUCKET,'o',Blocks.OBSIDIAN));
		//marriage ring
//		GameRegistry.addRecipe(new ShapedOreRecipe(ring," s ","a a"," a ",'s',Items.NETHER_STAR,'a',abyssiumStack));
		//kaitai hammer
//		GameRegistry.addRecipe(new ShapedOreRecipe(kaitaiHammerNew,"aaa","aaa"," s ",'s',Items.STICK,'a',abyssiumStack));
		//pointer staff
//		GameRegistry.addRecipe(new ShapedOreRecipe(pointer,"  g"," p ","p  ",'g',grudeBlock,'p',polymetalStack));
		//toy plane
//		GameRegistry.addRecipe(new ShapedOreRecipe(toyplane," a ","aaa"," a ",'a',polymetalStack));
		//goddess
//		GameRegistry.addRecipe(new ShapedOreRecipe(repairGoddess,"hgh","gdg","hgh",'d',Blocks.DIAMOND_BLOCK,'g',grudeBlock,'h',grudeHeavyBlock));
//		GameRegistry.addRecipe(new ShapedOreRecipe(repairGoddess,"ghg","hdh","ghg",'d',Blocks.DIAMOND_BLOCK,'g',grudeBlock,'h',grudeHeavyBlock));
		//wrench
//		GameRegistry.addRecipe(new ShapedOreRecipe(wrench,"a a","aaa"," a ",'a',abyssiumStack));
		//volcano block
//		GameRegistry.addRecipe(new ShapedOreRecipe(volblock,"gog","olo","gog",'g',magma,'l',grudeBlock,'o',Blocks.OBSIDIAN));
//		GameRegistry.addRecipe(new ShapedOreRecipe(volblock,"gog","olo","gog",'g',Blocks.OBSIDIAN,'l',grudeBlock,'o',magma));
//		GameRegistry.addRecipe(new ShapedOreRecipe(volcore,"gog","olo","gog",'g',volblock,'l',grudeHeavyBlock,'o',Blocks.OBSIDIAN));
//		GameRegistry.addRecipe(new ShapedOreRecipe(volcore,"gog","olo","gog",'g',Blocks.OBSIDIAN,'l',grudeHeavyBlock,'o',volblock));
		//frame
//		GameRegistry.addRecipe(new ShapedOreRecipe(frame16,"a a"," o ","a a",'o',Blocks.OBSIDIAN,'a',abyssiumStack));
		//crane
//		GameRegistry.addRecipe(new ShapedOreRecipe(crane,"aaa","aga","apa",'a',abyssiumStack,'p',Blocks.PISTON,'g',grudeBlock));
		//combat ration
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration0,"www","bgc","www",'b',Items.BREAD,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_BEEF,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_CHICKEN,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_FISH,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_MUTTON,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_PORKCHOP,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_RABBIT,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',Items.WHEAT));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration0,"www","bgc","www",'b',Items.BREAD,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_BEEF,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_CHICKEN,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_FISH,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_MUTTON,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_PORKCHOP,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour"));
//		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.COOKED_RABBIT,'c',Items.GOLDEN_CARROT,'g',grudeStack,'w',"foodFlour"));
		//ship tank
//		GameRegistry.addRecipe(new ShapedOreRecipe(shiptank0,"mtm","mtm","mtm",'m',polymetalStack,'t',Items.CAULDRON));
//		GameRegistry.addRecipe(new ShapedOreRecipe(shiptank1,"mtm","mtm","mtm",'m',Blocks.OBSIDIAN,'t',shiptank0));
//		GameRegistry.addRecipe(new ShapedOreRecipe(shiptank2,"mtm","mtm","mtm",'m',abyssiumBlock,'t',shiptank1));
//		GameRegistry.addRecipe(new ShapedOreRecipe(shiptank3,"mtm","mtm","mtm",'m',grudeHeavyBlock,'t',shiptank2));
		//enchant shell
//		GameRegistry.addRecipe(new RecipeEnchantShell()); 
		//xp item
//		GameRegistry.addRecipe(new ShapedOreRecipe(grudeXPStack,"xxx","xgx","xxx",'g',grudeStack,'x',Items.EXPERIENCE_BOTTLE));
//		GameRegistry.addRecipe(new ShapedOreRecipe(grudeXPBlock,"xxx","xxx","xxx",'x',grudeXPStack));
	}

	
}