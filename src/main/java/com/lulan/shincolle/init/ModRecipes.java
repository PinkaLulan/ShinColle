package com.lulan.shincolle.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
	//配方範例
	//GameRegistry.addSmelting(取得物, 材料, 0.1f經驗);
	//GameRegistry.addRecipe(new ShapedOreRecipe(取得物, " s ", "sss", " s ", 's', "stickWood" 材料陣列));
    //GameRegistry.addRecipe(new ShapelessOreRecipe(取得物, 材料A, 材料B, ...));		
	public static void init() {
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
		ItemStack blazepowderStack = new ItemStack(Items.blaze_powder);
		ItemStack bucketRepairStack = new ItemStack(ModItems.BucketRepair);	
		ItemStack desk = new ItemStack(ModBlocks.BlockDesk);
		ItemStack deskBook = new ItemStack(ModItems.DeskItemBook);
		ItemStack deskRadar = new ItemStack(ModItems.DeskItemRadar);
		ItemStack grudeStack = new ItemStack(ModItems.Grudge);
		ItemStack grudeStack9 = new ItemStack(ModItems.Grudge,9,0);
		ItemStack grudeBlock = new ItemStack(ModBlocks.BlockGrudge);
		ItemStack grudeBlock9 = new ItemStack(ModBlocks.BlockGrudge,9,0);
		ItemStack grudeHeavyBlock = new ItemStack(ModBlocks.BlockGrudgeHeavy);
		ItemStack gunpowderStack = new ItemStack(Items.gunpowder);
		ItemStack instantMat8 = new ItemStack(ModItems.InstantConMat, 8);
		ItemStack instantMat32 = new ItemStack(ModItems.InstantConMat, 64);
		ItemStack ironBlock = new ItemStack(Blocks.iron_block);
		ItemStack kaitaiHammerNew = new ItemStack(ModItems.KaitaiHammer);
		ItemStack kaitaiHammerAll = new ItemStack(ModItems.KaitaiHammer, 1, OreDictionary.WILDCARD_VALUE);
		ItemStack modernKit = new ItemStack(ModItems.ModernKit);
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
		ItemStack wrench = new ItemStack(ModItems.TargetWrench);
		ItemStack volblock = new ItemStack(ModBlocks.BlockVolBlock);
		ItemStack volcore = new ItemStack(ModBlocks.BlockVolCore);
		ItemStack frame16 = new ItemStack(ModBlocks.BlockFrame, 16);
		ItemStack crane = new ItemStack(ModBlocks.BlockCrane);
		ItemStack waypoint = new ItemStack(ModBlocks.BlockWaypoint, 16);
		
		//combat ration TODO random furnace recipe
		ItemStack ration0 = new ItemStack(ModItems.CombatRation, 1, 0);
		ItemStack ration1 = new ItemStack(ModItems.CombatRation, 1, 1);
		ItemStack ration2 = new ItemStack(ModItems.CombatRation, 1, 2);
		ItemStack ration3 = new ItemStack(ModItems.CombatRation, 1, 3);
		ItemStack ration4 = new ItemStack(ModItems.CombatRation, 1, 4);
		ItemStack ration5 = new ItemStack(ModItems.CombatRation, 1, 5);
		
		
		//SHAPELESS RECIPE
		//abyssium material:
		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumStack, "ingotIron", grudeStack));
		GameRegistry.addRecipe(new ShapelessOreRecipe(bucketRepairStack, Items.lava_bucket, grudeStack));		
		//1 item to N items
		GameRegistry.addRecipe(new ShapelessOreRecipe(ammo9, ammoContainer));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ammoHeavy9, ammoHeavyContainer));
		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumNugget9, abyssiumStack));
		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumStack9, abyssiumBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumBlock, ironBlock, grudeBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(grudeStack9, grudeBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(grudeBlock9, grudeHeavyBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalStack4, polymetalGravel));
		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalStack9, polymetalBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalGravel, polymetalStack, polymetalStack, polymetalStack, polymetalStack));
		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalStack5, toyplane));
		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalNugget9, polymetalStack));
		//misc
		GameRegistry.addRecipe(new ShapelessOreRecipe(instantMat8, kaitaiHammerAll, shipEggS));
		GameRegistry.addRecipe(new ShapelessOreRecipe(instantMat32, kaitaiHammerAll, shipEggL));
		GameRegistry.addRecipe(new ShapelessOreRecipe(modernKit, kaitaiHammerAll, shipEgg, shipEgg, shipEgg, shipEgg, shipEgg, shipEgg, shipEgg, shipEgg));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ownerPaper, grudeStack, Items.paper));
		GameRegistry.addRecipe(new ShapelessOreRecipe(waypoint, grudeStack, Items.stick));
		//combat ration
		GameRegistry.addRecipe(new ShapelessOreRecipe(ration0, Items.bread, Items.golden_carrot, grudeStack, "cropRice"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ration1, ration0, ModBlocks.BlockGrudge, ModBlocks.BlockPolymetalGravel));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.cooked_chicken, Items.golden_carrot, grudeStack, "cropRice"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.cooked_beef, Items.golden_carrot, grudeStack, "cropRice"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.cooked_fished, Items.golden_carrot, grudeStack, "cropRice"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ration2, Items.cooked_porkchop, Items.golden_carrot, grudeStack, "cropRice"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ration3, ration2, ModBlocks.BlockGrudge, ModBlocks.BlockPolymetalGravel));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ration4, Items.snowball, Items.snowball, Items.snowball, Items.snowball, Items.milk_bucket, ModItems.Grudge));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ration5, ration4, ModBlocks.BlockGrudge));
		
		//SHAPED RECIPE
		//ammo material: copper/tin/bronze=8 iron/lead=16 abyssium/steel/gold/silver=32 diamond/uranium=64
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo16,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo16,"iii","igi","ipi",'i',"ingotLead",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotSteel",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo64,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',gunpowderStack));		
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo64,"iii","igi","ipi",'i',"ingotUranium",'g',grudeStack,'p',gunpowderStack));		
		//heavy ammo: copper/tin/bronze=1 iron/lead=2 abyssium/steel/gold/silver=4 diamond/uranium=8
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy2,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy2,"iii","igi","ipi",'i',"ingotLead",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotSteel",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy8,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',blazepowderStack));		
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy8,"iii","igi","ipi",'i',"ingotUranium",'g',grudeStack,'p',blazepowderStack));		
		//9 items to 1 block
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoContainer,"aaa","aaa","aaa",'a',ammo1));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavyContainer,"aaa","aaa","aaa",'a',ammoHeavy1));
		GameRegistry.addRecipe(new ShapedOreRecipe(abyssiumBlock,"aaa","aaa","aaa",'a',abyssiumStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(abyssiumStack,"aaa","aaa","aaa",'a',abyssiumNugget));
		GameRegistry.addRecipe(new ShapedOreRecipe(grudeBlock,"aaa","aaa","aaa",'a',grudeStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(grudeHeavyBlock,"aaa","aaa","aaa",'a',grudeBlock));
		GameRegistry.addRecipe(new ShapedOreRecipe(polymetalBlock,"aaa","aaa","aaa",'a',polymetalStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(polymetalStack,"aaa","aaa","aaa",'a',polymetalNugget));
		//desk item
		GameRegistry.addRecipe(new ShapedOreRecipe(desk,"abc","ooo","o o",'a',deskRadar,'b',deskBook,'c',Blocks.wool,'o',Blocks.obsidian));
		GameRegistry.addRecipe(new ShapedOreRecipe(deskBook,"ggg","gbg","ggg",'g',grudeStack,'b',Items.writable_book));
		GameRegistry.addRecipe(new ShapedOreRecipe(deskRadar,"ggg","gbg","ggg",'g',grudeStack,'b',Items.compass));
		//small shipyard
		GameRegistry.addRecipe(new ShapedOreRecipe(smallshipyardStack,"glg","lol","ooo",'g',grudeStack,'l',Items.lava_bucket,'o',Blocks.obsidian));
		//marriage ring
		GameRegistry.addRecipe(new ShapedOreRecipe(ring,"asa","a a","aaa",'s',Items.nether_star,'a',abyssiumStack));
		//kaitai hammer
		GameRegistry.addRecipe(new ShapedOreRecipe(kaitaiHammerNew,"aaa","aaa"," s ",'s',Items.stick,'a',abyssiumStack));
		//pointer staff
		GameRegistry.addRecipe(new ShapedOreRecipe(pointer,"  g"," p ","p  ",'g',grudeBlock,'p',polymetalStack));
		//toy plane
		GameRegistry.addRecipe(new ShapedOreRecipe(toyplane," a ","aaa"," a ",'a',polymetalStack));
		//goddess
		GameRegistry.addRecipe(new ShapedOreRecipe(repairGoddess,"hgh","gdg","hgh",'d',Blocks.diamond_block,'g',grudeBlock,'h',grudeHeavyBlock));
		GameRegistry.addRecipe(new ShapedOreRecipe(repairGoddess,"ghg","hdh","ghg",'d',Blocks.diamond_block,'g',grudeBlock,'h',grudeHeavyBlock));
		//wrench
		GameRegistry.addRecipe(new ShapedOreRecipe(wrench,"a a","aaa"," a ",'a',abyssiumStack));
		//volcano block
		GameRegistry.addRecipe(new ShapedOreRecipe(volblock,"gog","olo","gog",'g',grudeHeavyBlock,'l',Items.lava_bucket,'o',Blocks.obsidian));
		GameRegistry.addRecipe(new ShapedOreRecipe(volblock,"gog","olo","gog",'g',Blocks.obsidian,'l',Items.lava_bucket,'o',grudeHeavyBlock));
		GameRegistry.addRecipe(new ShapedOreRecipe(volcore,"gog","olo","gog",'g',volblock,'l',Items.lava_bucket,'o',Blocks.obsidian));
		GameRegistry.addRecipe(new ShapedOreRecipe(volcore,"gog","olo","gog",'g',Blocks.obsidian,'l',Items.lava_bucket,'o',volblock));
		//frame
		GameRegistry.addRecipe(new ShapedOreRecipe(frame16,"a a"," o ","a a",'o',Blocks.obsidian,'a',abyssiumStack));
		//crane
		GameRegistry.addRecipe(new ShapedOreRecipe(crane,"aaa","aga","apa",'a',abyssiumStack,'p',Blocks.piston,'g',grudeBlock));
		
		
		//combat ration TODO random furnace recipe
		GameRegistry.addRecipe(new ShapedOreRecipe(ration0,"www","bgc","www",'b',Items.bread,'c',Items.golden_carrot,'g',grudeStack,'w',Items.wheat));
		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.cooked_beef,'c',Items.golden_carrot,'g',grudeStack,'w',Items.wheat));
		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.cooked_porkchop,'c',Items.golden_carrot,'g',grudeStack,'w',Items.wheat));
		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.cooked_fished,'c',Items.golden_carrot,'g',grudeStack,'w',Items.wheat));
		GameRegistry.addRecipe(new ShapedOreRecipe(ration2,"www","bgc","www",'b',Items.cooked_chicken,'c',Items.golden_carrot,'g',grudeStack,'w',Items.wheat));
		
	}

	
}
