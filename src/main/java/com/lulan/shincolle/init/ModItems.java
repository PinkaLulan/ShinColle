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
	public static final BasicItem BucketRepair = new BucketRepair();
	public static final BasicItem Grudge = new Grudge();
	//equip	
	public static final BasicItem EquipAirplane = new EquipAirplane();
	public static final BasicItem EquipArmor = new EquipArmor();
	public static final BasicItem EquipCannon = new EquipCannon();
	public static final BasicItem EquipRadar = new EquipRadar();
	public static final BasicItem EquipTorpedo = new EquipTorpedo();
	public static final BasicItem EquipTurbine = new EquipTurbine();
	//misc
	public static final BasicItem MarriageRing = new MarriageRing();

	//登錄item到遊戲中 (在pre init階段登錄)
	public static void init() {
		//spawn egg
		GameRegistry.registerItem(ShipSpawnEgg, "ShipSpawnEgg");
		//materials
		GameRegistry.registerItem(AbyssMetal, "AbyssMetal");
		GameRegistry.registerItem(Ammo, "Ammo");
		GameRegistry.registerItem(BucketRepair, "BucketRepair");
		GameRegistry.registerItem(Grudge, "Grudge");
		//equip		
		GameRegistry.registerItem(EquipAirplane, "EquipAirplane");
		GameRegistry.registerItem(EquipArmor, "EquipArmor");
		GameRegistry.registerItem(EquipCannon, "EquipCannon");
		GameRegistry.registerItem(EquipRadar, "EquipRadar");
		GameRegistry.registerItem(EquipTorpedo, "EquipTorpedo");
		GameRegistry.registerItem(EquipTurbine, "EquipTurbine");
		//misc
		GameRegistry.registerItem(MarriageRing, "MarriageRing");
	}
	
}
