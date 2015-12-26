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
		ItemStack polymetalStack = new ItemStack(ModItems.AbyssMetal,1,1);
		ItemStack polymetalStack4 = new ItemStack(ModItems.AbyssMetal,4,1);
		ItemStack polymetalStack9 = new ItemStack(ModItems.AbyssMetal,9,1);
		ItemStack polymetalBlock = new ItemStack(ModBlocks.BlockPolymetal);
		ItemStack polymetalGravel = new ItemStack(ModBlocks.BlockPolymetalGravel);
		ItemStack ring = new ItemStack(ModItems.MarriageRing);
		ItemStack repairGoddess = new ItemStack(ModItems.RepairGoddess,1,0);
		ItemStack shipEgg = new ItemStack(ModItems.ShipSpawnEgg, 1, OreDictionary.WILDCARD_VALUE);	//for all meta value
		ItemStack shipEggS = new ItemStack(ModItems.ShipSpawnEgg, 1, 0);
		ItemStack shipEggL = new ItemStack(ModItems.ShipSpawnEgg, 1, 1);
		ItemStack smallshipyardStack = new ItemStack(ModBlocks.BlockSmallShipyard);
		ItemStack toyplane = new ItemStack(ModItems.ToyAirplane);
		
		//SHAPELESS RECIPE
		//abyssium material:
		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumStack, "ingotIron", grudeStack));
		GameRegistry.addRecipe(new ShapelessOreRecipe(bucketRepairStack, Items.lava_bucket, grudeStack));		
		//1 block to N items
		GameRegistry.addRecipe(new ShapelessOreRecipe(ammo9, ammoContainer));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ammoHeavy9, ammoHeavyContainer));
		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumStack9, abyssiumBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(abyssiumBlock, ironBlock, grudeBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(grudeStack9, grudeBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(grudeBlock9, grudeHeavyBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalStack4, polymetalGravel));
		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalStack9, polymetalBlock));
		GameRegistry.addRecipe(new ShapelessOreRecipe(polymetalGravel, polymetalStack, polymetalStack, polymetalStack, polymetalStack));
		//misc
		GameRegistry.addRecipe(new ShapelessOreRecipe(instantMat8, kaitaiHammerAll, shipEggS));
		GameRegistry.addRecipe(new ShapelessOreRecipe(instantMat32, kaitaiHammerAll, shipEggL));
		GameRegistry.addRecipe(new ShapelessOreRecipe(modernKit, kaitaiHammerAll, shipEgg, shipEgg, shipEgg, shipEgg, shipEgg, shipEgg, shipEgg, shipEgg));
		GameRegistry.addRecipe(new ShapelessOreRecipe(ownerPaper, grudeStack, Items.paper));
		
		//SHAPED RECIPE
		//ammo material: copper/tin=8 iron/bronze=16 abyssium/gold/silver=32 diamond=64
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo16,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo8,"iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo32,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',gunpowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammo64,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',gunpowderStack));		
		//heavy ammo material: copper/tin=1 iron/bronze=2 gold/silver=4 diamond=8
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotCopper",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotTin",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy2,"iii","igi","ipi",'i',"ingotIron",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy1,"iii","igi","ipi",'i',"ingotBronze",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',abyssiumStack,'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotGold",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy4,"iii","igi","ipi",'i',"ingotSilver",'g',grudeStack,'p',blazepowderStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavy8,"iii","igi","ipi",'i',"gemDiamond",'g',grudeStack,'p',blazepowderStack));		
		//9 items to 1 block
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoContainer,"aaa","aaa","aaa",'a',ammo1));
		GameRegistry.addRecipe(new ShapedOreRecipe(ammoHeavyContainer,"aaa","aaa","aaa",'a',ammoHeavy1));
		GameRegistry.addRecipe(new ShapedOreRecipe(abyssiumBlock,"aaa","aaa","aaa",'a',abyssiumStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(grudeBlock,"aaa","aaa","aaa",'a',grudeStack));
		GameRegistry.addRecipe(new ShapedOreRecipe(grudeHeavyBlock,"aaa","aaa","aaa",'a',grudeBlock));
		GameRegistry.addRecipe(new ShapedOreRecipe(polymetalBlock,"aaa","aaa","aaa",'a',"dustManganese"));
		//desk
		GameRegistry.addRecipe(new ShapedOreRecipe(desk,"www","ogo","o o",'w',Blocks.wool,'g',grudeHeavyBlock,'o',Blocks.obsidian));
		//small shipyard
		GameRegistry.addRecipe(new ShapedOreRecipe(smallshipyardStack,"glg","lol","ooo",'g',grudeStack,'l',Items.lava_bucket,'o',Blocks.obsidian));
		//marriage ring
		GameRegistry.addRecipe(new ShapedOreRecipe(ring,"asa","a a","aaa",'s',Items.nether_star,'a',abyssiumStack));
		//kaitai hammer
		GameRegistry.addRecipe(new ShapedOreRecipe(kaitaiHammerNew,"aaa","aaa"," s ",'s',Items.stick,'a',abyssiumStack));
		//pointer staff
		GameRegistry.addRecipe(new ShapedOreRecipe(pointer,"  g"," p ","p  ",'g',grudeBlock,'p',"dustManganese"));
		//toy plane
		GameRegistry.addRecipe(new ShapedOreRecipe(toyplane," a ","aaa"," a ",'a',"dustManganese"));
		//goddess
		GameRegistry.addRecipe(new ShapedOreRecipe(repairGoddess,"hgh","gdg","hgh",'d',Blocks.diamond_block,'g',grudeBlock,'h',grudeHeavyBlock));
		GameRegistry.addRecipe(new ShapedOreRecipe(repairGoddess,"ghg","hdh","ghg",'d',Blocks.diamond_block,'g',grudeBlock,'h',grudeHeavyBlock));
		
	}

}
