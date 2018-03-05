package com.lulan.shincolle.init;

import java.util.ArrayList;
import java.util.List;

import com.lulan.shincolle.item.AbyssMetal;
import com.lulan.shincolle.item.AbyssNugget;
import com.lulan.shincolle.item.Ammo;
import com.lulan.shincolle.item.BasicItem;
import com.lulan.shincolle.item.BucketRepair;
import com.lulan.shincolle.item.CombatRation;
import com.lulan.shincolle.item.DeskItemBook;
import com.lulan.shincolle.item.DeskItemRadar;
import com.lulan.shincolle.item.EquipAirplane;
import com.lulan.shincolle.item.EquipAmmo;
import com.lulan.shincolle.item.EquipArmor;
import com.lulan.shincolle.item.EquipCannon;
import com.lulan.shincolle.item.EquipCatapult;
import com.lulan.shincolle.item.EquipCompass;
import com.lulan.shincolle.item.EquipDrum;
import com.lulan.shincolle.item.EquipFlare;
import com.lulan.shincolle.item.EquipMachinegun;
import com.lulan.shincolle.item.EquipRadar;
import com.lulan.shincolle.item.EquipSearchlight;
import com.lulan.shincolle.item.EquipTorpedo;
import com.lulan.shincolle.item.EquipTurbine;
import com.lulan.shincolle.item.Grudge;
import com.lulan.shincolle.item.InstantConMat;
import com.lulan.shincolle.item.KaitaiHammer;
import com.lulan.shincolle.item.MarriageRing;
import com.lulan.shincolle.item.ModernKit;
import com.lulan.shincolle.item.OPTool;
import com.lulan.shincolle.item.OwnerPaper;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.item.RecipePaper;
import com.lulan.shincolle.item.RepairGoddess;
import com.lulan.shincolle.item.ShipSpawnEgg;
import com.lulan.shincolle.item.ShipTank;
import com.lulan.shincolle.item.TargetWrench;
import com.lulan.shincolle.item.ToyAirplane;
import com.lulan.shincolle.item.TrainingBook;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems
{

	//spawn egg
	public static BasicItem ShipSpawnEgg;
	//materials
	public static BasicItem AbyssMetal;
	public static BasicItem AbyssNugget;
	public static BasicItem Ammo;
	public static BasicItem Grudge;
	//equip	
	public static BasicItem EquipAirplane;
	public static BasicItem EquipAmmo;
	public static BasicItem EquipArmor;
	public static BasicItem EquipCannon;
	public static BasicItem EquipCatapult;
	public static BasicItem EquipCompass;
	public static BasicItem EquipDrum;
	public static BasicItem EquipFlare;
	public static BasicItem EquipMachinegun;
	public static BasicItem EquipRadar;
	public static BasicItem EquipSearchlight;
	public static BasicItem EquipTorpedo;
	public static BasicItem EquipTurbine;
	//misc
	public static BasicItem BucketRepair;
	public static BasicItem CombatRation;
	public static BasicItem DeskItemBook;
	public static BasicItem DeskItemRadar;
	public static BasicItem InstantConMat;
	public static BasicItem KaitaiHammer;
	public static BasicItem MarriageRing;
	public static BasicItem ModernKit;
	public static BasicItem OwnerPaper;
	public static BasicItem OPTool;
	public static BasicItem PointerItem;
	public static BasicItem RecipePaper;
	public static BasicItem RepairGoddess;
	public static BasicItem ShipTank;
	public static BasicItem TargetWrench;
	public static BasicItem TrainingBook;
	//toy
	public static BasicItem ToyAirplane;

	//list for items
	private static List<BasicItem> ListItems;
	
	
	//item instance init, used in MOD PREINIT
	public static void register(RegistryEvent.Register<Item> event) throws Exception
	{
		ListItems = new ArrayList();
		
		//spawn egg
		ShipSpawnEgg = initItems(event, ShipSpawnEgg.class);
		
		//materials
		AbyssMetal = initItems(event, AbyssMetal.class);
		AbyssNugget = initItems(event, AbyssNugget.class);
		Ammo = initItems(event, Ammo.class);
		Grudge = initItems(event, Grudge.class);
		
		//equip	
		EquipAirplane = initItems(event, EquipAirplane.class);
		EquipAmmo = initItems(event, EquipAmmo.class);
		EquipArmor = initItems(event, EquipArmor.class);
		EquipCannon = initItems(event, EquipCannon.class);
		EquipCatapult = initItems(event, EquipCatapult.class);
		EquipCompass = initItems(event, EquipCompass.class);
		EquipDrum = initItems(event, EquipDrum.class);
		EquipFlare = initItems(event, EquipFlare.class);
		EquipMachinegun = initItems(event, EquipMachinegun.class);
		EquipRadar = initItems(event, EquipRadar.class);
		EquipSearchlight = initItems(event, EquipSearchlight.class);
		EquipTorpedo = initItems(event, EquipTorpedo.class);
		EquipTurbine = initItems(event, EquipTurbine.class);
		
		//misc
		BucketRepair = initItems(event, BucketRepair.class);
		CombatRation = initItems(event, CombatRation.class);
		DeskItemBook = initItems(event, DeskItemBook.class);
		DeskItemRadar = initItems(event, DeskItemRadar.class);
		InstantConMat = initItems(event, InstantConMat.class);
		KaitaiHammer = initItems(event, KaitaiHammer.class);
		MarriageRing = initItems(event, MarriageRing.class);
		ModernKit = initItems(event, ModernKit.class);
		OwnerPaper = initItems(event, OwnerPaper.class);
		OPTool = initItems(event, OPTool.class);
		PointerItem = initItems(event, PointerItem.class);
		RecipePaper = initItems(event, RecipePaper.class);
		RepairGoddess = initItems(event, RepairGoddess.class);
		ShipTank = initItems(event, ShipTank.class);
		TargetWrench = initItems(event, TargetWrench.class);
		TrainingBook = initItems(event, TrainingBook.class);
		
		//toy
		ToyAirplane = initItems(event, ToyAirplane.class);
	}
	
	//create instance and add instance to list
	private static BasicItem initItems(RegistryEvent.Register<Item> event,
									   Class<? extends BasicItem> itemClass) throws Exception
	{
		try
		{
			//new instance
			BasicItem i = itemClass.newInstance();
			
			//add item to list (for model init)
			ListItems.add(i);
			
			//register item
			event.getRegistry().register(i);
			return i;
		}
		catch (Exception e)
		{
			//item建立instance失敗, 此例外必須丟出以強制中止遊戲
			LogHelper.info("EXCEPTION: instancing fail: "+itemClass);
			e.printStackTrace();
			throw e;
		}
	}
	
	//item model init, used in CLIENT PROXY INIT
	@SideOnly(Side.CLIENT)
    public static void initModels()
	{
		for (BasicItem i : ListItems)
		{
			i.initModel();
		}
    }
	
	
}