package com.lulan.shincolle.proxy;

import com.lulan.shincolle.client.render.RenderMiscEntity;
import com.lulan.shincolle.client.render.RenderShipEntity;
import com.lulan.shincolle.client.render.RenderSummonEntity;
import com.lulan.shincolle.client.render.item.RenderBasicEntityItem;
import com.lulan.shincolle.client.render.item.RenderTileEntityItem;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerAkatsuki;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerAkatsukiMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerHa;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerHibiki;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerHibikiMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerI;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerIkazuchi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerIkazuchiMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerInazuma;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerInazumaMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerNi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerRo;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerShimakaze;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerShimakazeBoss;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityAirplane;
import com.lulan.shincolle.entity.other.EntityAirplaneT;
import com.lulan.shincolle.entity.other.EntityAirplaneTHostile;
import com.lulan.shincolle.entity.other.EntityAirplaneTakoyaki;
import com.lulan.shincolle.entity.other.EntityAirplaneZero;
import com.lulan.shincolle.entity.other.EntityAirplaneZeroHostile;
import com.lulan.shincolle.entity.other.EntityFloatingFort;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{

	//client world會隨玩家所在位置持續改變, 但是dim id永遠都是0不變, 無法反推dim id?
	public static World getClientWorld()
	{
		return Minecraft.getMinecraft().world;
	}
	
	//client player
	public static EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().player;
	}
	
	//client minecraft
	public static GameSettings getGameSetting()
	{
		return Minecraft.getMinecraft().gameSettings;
	}
	
	//client minecraft
	public static Minecraft getMineraft()
	{
		return Minecraft.getMinecraft();
	}

	//登錄偵測按鍵
	@Override
	public void registerKeyBindings() {}
	
	//init entity, item, block models
	@Override
	public void registerRender() throws Exception
	{
		//init item models
		ModItems.initModels();
		
		//init block models
		ModBlocks.initModels();
		
		//取代mc原本的tesr item renderer, 改為自訂的renderer
		TileEntityItemStackRenderer.instance = new RenderTileEntityItem();
		
		//custom item entity render
		RenderingRegistry.registerEntityRenderingHandler(BasicEntityItem.class, RenderBasicEntityItem.FACTORY);

		//entity render (model class, shadow size)
//		RenderingRegistry.registerEntityRenderingHandler(EntityAirfieldHime.class, new BasicShipRenderer(new ModelAirfieldHime(), 0.7F, ID.Ship.AirfieldHime));
//		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipHime.class, new BasicShipRenderer(new ModelBattleshipHime(), 0.7F, ID.Ship.BattleshipHime));
//		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipNGT.class, new BasicShipRenderer(new ModelBattleshipNagato(0), 0.7F, ID.Ship.BattleshipNagato));
//		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipNGTBoss.class, new BossShipRenderer(new ModelBattleshipNagato(1), 1.2F, ID.Ship.BattleshipNagato));
//		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipYMT.class, new BasicShipRenderer(new ModelBattleshipYamato(0), 0.7F, ID.Ship.BattleshipYamato));
//		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipYMTBoss.class, new BossShipRenderer(new ModelBattleshipYamato(1), 1.2F, ID.Ship.BattleshipYamato));
//		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipTa.class, new BasicShipRenderer(new ModelBattleshipTa(), 0.7F, ID.Ship.BattleshipTA));
//		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipRe.class, new BasicShipRenderer(new ModelBattleshipRe(), 0.7F, ID.Ship.BattleshipRE));
//		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierAkagi.class, new BasicShipRenderer(new ModelCarrierAkagi(0), 0.7F, ID.Ship.CarrierAkagi));
//		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierAkagiBoss.class, new BossShipRenderer(new ModelCarrierAkagi(1), 1.2F, ID.Ship.CarrierAkagi));
//		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierKaga.class, new BasicShipRenderer(new ModelCarrierKaga(0), 0.7F, ID.Ship.CarrierKaga));
//		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierKagaBoss.class, new BossShipRenderer(new ModelCarrierKaga(1), 1.2F, ID.Ship.CarrierKaga));
//		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierHime.class, new BasicShipRenderer(new ModelCarrierHime(), 1F, ID.Ship.CarrierHime));
//		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWD.class, new BasicShipRenderer(new ModelCarrierWDemon(), 1F, ID.Ship.CarrierWD));
//		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWo.class, new BasicShipRenderer(new ModelCarrierWo(), 1F, ID.Ship.CarrierWO));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerI.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerRo.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHa.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerNi.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerAkatsuki.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerAkatsukiMob.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHibiki.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHibikiMob.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerIkazuchi.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerIkazuchiMob.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerInazuma.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerInazumaMob.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakaze.class, RenderShipEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakazeBoss.class, RenderShipEntity.FACTORY_DEFAULT);
//		RenderingRegistry.registerEntityRenderingHandler(EntityHarbourHime.class, new BasicShipRenderer(new ModelHarbourHime(), 0.8F, ID.Ship.HarbourHime));
//		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserRi.class, new BasicShipRenderer(new ModelHeavyCruiserRi(), 0.7F, ID.Ship.HeavyCruiserRI));
//		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserNe.class, new BasicShipRenderer(new ModelHeavyCruiserNe(), 0.7F, ID.Ship.HeavyCruiserNE));
//		RenderingRegistry.registerEntityRenderingHandler(EntityNorthernHime.class, new RenderNorthernHime(new ModelNorthernHime(), 0.5F, ID.Ship.NorthernHime));
//		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhou.class, new MiscMobRenderer(new ModelRensouhou(0.3F, 3F), 0.4F, 5));
//		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhouBoss.class, new MiscMobRenderer(new ModelRensouhou(1F, 0F), 1F, 5));
//		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhouS.class, new MiscMobRenderer(new ModelRensouhouS(), 0.4F, 6));
//		RenderingRegistry.registerEntityRenderingHandler(EntitySubmKa.class, new BasicShipRenderer(new ModelSubmKa(), 0.5F, ID.Ship.SubmarineKA));
//		RenderingRegistry.registerEntityRenderingHandler(EntitySubmYo.class, new BasicShipRenderer(new ModelSubmYo(), 0.5F, ID.Ship.SubmarineYO));
//		RenderingRegistry.registerEntityRenderingHandler(EntitySubmSo.class, new BasicShipRenderer(new ModelSubmSo(), 0.5F, ID.Ship.SubmarineSO));
//		RenderingRegistry.registerEntityRenderingHandler(EntitySubmRo500.class, new BasicShipRenderer(new ModelSubmRo500(), 0.5F, ID.Ship.SubmarineRo500));
//		RenderingRegistry.registerEntityRenderingHandler(EntitySubmRo500Mob.class, new BasicShipRenderer(new ModelSubmRo500(), 0.5F, ID.Ship.SubmarineRo500));
//		RenderingRegistry.registerEntityRenderingHandler(EntitySubmU511.class, new BasicShipRenderer(new ModelSubmU511(), 0.5F, ID.Ship.SubmarineU511));
//		RenderingRegistry.registerEntityRenderingHandler(EntitySubmU511Mob.class, new BasicShipRenderer(new ModelSubmU511(), 0.5F, ID.Ship.SubmarineU511));
//		RenderingRegistry.registerEntityRenderingHandler(EntityTransportWa.class, new BasicShipRenderer(new ModelTransportWa(), 0.7F, ID.Ship.TransportWA));
//		
//		//mount render
//		RenderingRegistry.registerEntityRenderingHandler(EntityMountAfH.class, new MountsShipRenderer(new ModelMountAfH(), 1.5F, 0));
//		RenderingRegistry.registerEntityRenderingHandler(EntityMountBaH.class, new MountsShipRenderer(new ModelMountBaH(), 1.5F, 1));
//		RenderingRegistry.registerEntityRenderingHandler(EntityMountCaWD.class, new MountsShipRenderer(new ModelMountCaWD(), 1.5F, 2));
//		RenderingRegistry.registerEntityRenderingHandler(EntityMountHbH.class, new MountsShipRenderer(new ModelMountHbH(), 1.5F, 3));
//		RenderingRegistry.registerEntityRenderingHandler(EntityMountCaH.class, new MountsShipRenderer(new ModelMountCaH(), 1.5F, 4));
		
		//summons render
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssMissile.class, RenderMiscEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplane.class, RenderSummonEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneTakoyaki.class, RenderSummonEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneT.class, RenderSummonEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneZero.class, RenderSummonEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneTHostile.class, RenderSummonEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneZeroHostile.class, RenderSummonEntity.FACTORY_DEFAULT);
		RenderingRegistry.registerEntityRenderingHandler(EntityFloatingFort.class, RenderSummonEntity.FACTORY_DEFAULT);
//		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBeam.class, new RenderProjectileBeam());
	
	}
	
	
}
