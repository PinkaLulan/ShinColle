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
import com.lulan.shincolle.item.OwnerPaper;
import com.lulan.shincolle.item.PointerItem;
import com.lulan.shincolle.item.RepairGoddess;
import com.lulan.shincolle.item.ShipSpawnEgg;
import com.lulan.shincolle.item.TargetWrench;
import com.lulan.shincolle.item.ToyAirplane;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@GameRegistry.ObjectHolder(Reference.MOD_ID)	//登錄object holder使mod的物件容易流通 其他人可以直接讀取該物件
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
	public static BasicItem PointerItem;
	public static BasicItem RepairGoddess;
	public static BasicItem TargetWrench;
	//toy
	public static BasicItem ToyAirplane;

	//list for items
	private static List<BasicItem> ListItems;
	
	
	//item instance init, used in MOD PREINIT
	public static void init() throws Exception
	{
		ListItems = new ArrayList();
		
		//spawn egg
		ShipSpawnEgg = initItems(ShipSpawnEgg.class);
		
		//materials
		AbyssMetal = initItems(AbyssMetal.class);
		AbyssNugget = initItems(AbyssNugget.class);
		Ammo = initItems(Ammo.class);
		Grudge = initItems(Grudge.class);
		
		//equip	
		EquipAirplane = initItems(EquipAirplane.class);
		EquipArmor = initItems(EquipArmor.class);
		EquipCannon = initItems(EquipCannon.class);
		EquipCatapult = initItems(EquipCatapult.class);
		EquipCompass = initItems(EquipCompass.class);
		EquipDrum = initItems(EquipDrum.class);
		EquipFlare = initItems(EquipFlare.class);
		EquipMachinegun = initItems(EquipMachinegun.class);
		EquipRadar = initItems(EquipRadar.class);
		EquipSearchlight = initItems(EquipSearchlight.class);
		EquipTorpedo = initItems(EquipTorpedo.class);
		EquipTurbine = initItems(EquipTurbine.class);
		
		//misc
		BucketRepair = initItems(BucketRepair.class);
		CombatRation = initItems(CombatRation.class);
		DeskItemBook = initItems(DeskItemBook.class);
		DeskItemRadar = initItems(DeskItemRadar.class);
		InstantConMat = initItems(InstantConMat.class);
		KaitaiHammer = initItems(KaitaiHammer.class);
		MarriageRing = initItems(MarriageRing.class);
		ModernKit = initItems(ModernKit.class);
		OwnerPaper = initItems(OwnerPaper.class);
		PointerItem = initItems(PointerItem.class);
		RepairGoddess = initItems(RepairGoddess.class);
		TargetWrench = initItems(TargetWrench.class);
		
		//toy
		ToyAirplane = initItems(ToyAirplane.class);
		
	}
	
	//create instance and add instance to list
	private static BasicItem initItems(Class<? extends BasicItem> itemClass) throws Exception
	{
		try
		{
			BasicItem i = itemClass.newInstance();
			ListItems.add(i);
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
