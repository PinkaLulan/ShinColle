package com.lulan.shincolle.init;

import net.minecraft.item.Item;

import com.lulan.shincolle.item.*;
import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)	//登錄object holder使mod的物件容易流通 其他人可以直接讀取該物件
public class ModItems {

	//spawn egg
	public static final ShipSpawnEgg ShipSpawnEgg = new ShipSpawnEgg();
	//materials
	public static final BasicItem AbyssMetal = new AbyssMetal();
	public static final BasicItem Ammo = new Ammo();
	public static final BasicItem Grudge = new Grudge();
	//equip	
	public static final BasicItem EquipAirplane = new EquipAirplane();
	public static final BasicItem EquipArmor = new EquipArmor();
	public static final BasicItem EquipCannon = new EquipCannon();
	public static final BasicItem EquipRadar = new EquipRadar();
	public static final BasicItem EquipTorpedo = new EquipTorpedo();
	public static final BasicItem EquipTurbine = new EquipTurbine();
	//misc
	public static final BasicItem BucketRepair = new RepairBucket();
	public static final BasicItem InstantConMat = new InstantConMat();
	public static final BasicItem KaitaiHammer = new KaitaiHammer();
	public static final BasicItem MarriageRing = new MarriageRing();
	public static final BasicItem ModernKit = new ModernKit();
	public static final BasicItem OwnerPaper = new OwnerPaper();
	public static final BasicItem RepairGoddess = new RepairGoddess();

	//登錄item到遊戲中 (在pre init階段登錄)
	public static void init() {
		//spawn egg
		GameRegistry.registerItem(ShipSpawnEgg, "ShipSpawnEgg");
		//materials
		GameRegistry.registerItem(AbyssMetal, "AbyssMetal");
		GameRegistry.registerItem(Ammo, "Ammo");
		GameRegistry.registerItem(Grudge, "Grudge");
		//equip		
		GameRegistry.registerItem(EquipAirplane, "EquipAirplane");
		GameRegistry.registerItem(EquipArmor, "EquipArmor");
		GameRegistry.registerItem(EquipCannon, "EquipCannon");
		GameRegistry.registerItem(EquipRadar, "EquipRadar");
		GameRegistry.registerItem(EquipTorpedo, "EquipTorpedo");
		GameRegistry.registerItem(EquipTurbine, "EquipTurbine");
		//misc
		GameRegistry.registerItem(BucketRepair, "BucketRepair");
		GameRegistry.registerItem(InstantConMat, "InstantConMat");
		GameRegistry.registerItem(KaitaiHammer, "KaitaiHammer");
		GameRegistry.registerItem(MarriageRing, "MarriageRing");
		GameRegistry.registerItem(ModernKit, "ModernKit");
		GameRegistry.registerItem(OwnerPaper, "OwnerPaper");
		GameRegistry.registerItem(RepairGoddess, "RepairGoddess");
	}
	
}
