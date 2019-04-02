package com.lulan.shincolle.init;

import com.lulan.shincolle.item.*;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{

	//spawn egg
	public static final Item ShipSpawnEgg = null;
	//materials
	public static final Item AbyssMetal = null;
	public static final Item AbyssNugget = null;
	public static final Item Ammo = null;
	public static final Item Grudge = null;
	//equip
	public static final Item EquipAirplane = null;
	public static final Item EquipAmmo = null;
	public static final Item EquipArmor = null;
	public static final Item EquipCannon = null;
	public static final Item EquipCatapult = null;
	public static final Item EquipCompass = null;
	public static final Item EquipDrum = null;
	public static final Item EquipFlare = null;
	public static final Item EquipMachinegun = null;
	public static final Item EquipRadar = null;
	public static final Item EquipSearchlight = null;
	public static final Item EquipTorpedo = null;
	public static final Item EquipTurbine = null;
	//misc
	public static final Item BucketRepair = null;
	public static final Item CombatRation = null;
	public static final Item DeskItemBook = null;
	public static final Item DeskItemRadar = null;
	public static final Item InstantConMat = null;
	public static final Item KaitaiHammer = null;
	public static final Item MarriageRing = null;
	public static final Item ModernKit = null;
	public static final Item OwnerPaper = null;
	public static final Item OPTool = null;
	public static final Item PointerItem = null;
	public static final Item RecipePaper = null;
	public static final Item RepairGoddess = null;
	public static final Item ShipTank = null;
	public static final Item TargetWrench = null;
	public static final Item TrainingBook = null;
	//toy
	public static final Item ToyAirplane = null;

	//list for items
	private static List<BasicItem> ListItems;

	private static Item[] ITEMS;

	static
	{
		ListItems = new ArrayList();

		try
		{
			ITEMS = new Item[]
					{
							//spawn egg
							initItems(ShipSpawnEgg.class),

							//materials
							initItems(AbyssMetal.class),
							initItems(AbyssNugget.class),
							initItems(Ammo.class),
							initItems(Grudge.class),

							//equip
							initItems(EquipAirplane.class),
							initItems(EquipAmmo.class),
							initItems(EquipArmor.class),
							initItems(EquipCannon.class),
							initItems(EquipCatapult.class),
							initItems(EquipCompass.class),
							initItems(EquipDrum.class),
							initItems(EquipFlare.class),
							initItems(EquipMachinegun.class),
							initItems(EquipRadar.class),
							initItems(EquipSearchlight.class),
							initItems(EquipTorpedo.class),
							initItems(EquipTurbine.class),

							//misc
							initItems(BucketRepair.class),
							initItems(CombatRation.class),
							initItems(DeskItemBook.class),
							initItems(DeskItemRadar.class),
							initItems(InstantConMat.class),
							initItems(KaitaiHammer.class),
							initItems(MarriageRing.class),
							initItems(ModernKit.class),
							initItems(OwnerPaper.class),
							initItems(OPTool.class),
							initItems(PointerItem.class),
							initItems(RecipePaper.class),
							initItems(RepairGoddess.class),
							initItems(ShipTank.class),
							initItems(TargetWrench.class),
							initItems(TrainingBook.class),

							//toy
							initItems(ToyAirplane.class)
					};
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		for(Item item : ITEMS)
		{
			event.getRegistry().register(item);
		}
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

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void loadItemModels(ModelRegistryEvent event)
	{
		for (BasicItem i : ListItems)
		{
			i.initModel();
		}
	}
}