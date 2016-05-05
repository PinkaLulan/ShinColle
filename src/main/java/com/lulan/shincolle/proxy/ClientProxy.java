package com.lulan.shincolle.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

import com.lulan.shincolle.client.model.ModelAirfieldHime;
import com.lulan.shincolle.client.model.ModelAirplane;
import com.lulan.shincolle.client.model.ModelAirplaneT;
import com.lulan.shincolle.client.model.ModelAirplaneZero;
import com.lulan.shincolle.client.model.ModelBattleshipHime;
import com.lulan.shincolle.client.model.ModelBattleshipNagato;
import com.lulan.shincolle.client.model.ModelBattleshipRe;
import com.lulan.shincolle.client.model.ModelBattleshipTa;
import com.lulan.shincolle.client.model.ModelBattleshipYamato;
import com.lulan.shincolle.client.model.ModelCarrierAkagi;
import com.lulan.shincolle.client.model.ModelCarrierKaga;
import com.lulan.shincolle.client.model.ModelCarrierWDemon;
import com.lulan.shincolle.client.model.ModelCarrierWo;
import com.lulan.shincolle.client.model.ModelDestroyerHa;
import com.lulan.shincolle.client.model.ModelDestroyerI;
import com.lulan.shincolle.client.model.ModelDestroyerIkazuchi;
import com.lulan.shincolle.client.model.ModelDestroyerInazuma;
import com.lulan.shincolle.client.model.ModelDestroyerNi;
import com.lulan.shincolle.client.model.ModelDestroyerRo;
import com.lulan.shincolle.client.model.ModelDestroyerShimakaze;
import com.lulan.shincolle.client.model.ModelDestroyerShimakazeBoss;
import com.lulan.shincolle.client.model.ModelFloatingFort;
import com.lulan.shincolle.client.model.ModelHarbourHime;
import com.lulan.shincolle.client.model.ModelHeavyCruiserNe;
import com.lulan.shincolle.client.model.ModelHeavyCruiserRi;
import com.lulan.shincolle.client.model.ModelMountAfH;
import com.lulan.shincolle.client.model.ModelMountBaH;
import com.lulan.shincolle.client.model.ModelMountCaWD;
import com.lulan.shincolle.client.model.ModelMountHbH;
import com.lulan.shincolle.client.model.ModelNorthernHime;
import com.lulan.shincolle.client.model.ModelRensouhou;
import com.lulan.shincolle.client.model.ModelRensouhouS;
import com.lulan.shincolle.client.model.ModelSubmRo500;
import com.lulan.shincolle.client.model.ModelSubmSo;
import com.lulan.shincolle.client.model.ModelSubmU511;
import com.lulan.shincolle.client.model.ModelSubmYo;
import com.lulan.shincolle.client.model.ModelTakoyaki;
import com.lulan.shincolle.client.model.ModelTransportWa;
import com.lulan.shincolle.client.render.BasicShipRenderer;
import com.lulan.shincolle.client.render.BossShipRenderer;
import com.lulan.shincolle.client.render.MiscMobRenderer;
import com.lulan.shincolle.client.render.MountsShipRenderer;
import com.lulan.shincolle.client.render.RenderBlockDesk;
import com.lulan.shincolle.client.render.RenderBlockDeskItem;
import com.lulan.shincolle.client.render.RenderNorthernHime;
import com.lulan.shincolle.client.render.block.RenderLargeShipyard;
import com.lulan.shincolle.client.render.block.RenderSmallShipyard;
import com.lulan.shincolle.client.render.block.RenderSmallShipyardItem;
import com.lulan.shincolle.client.render.item.RenderBasicEntityItem;
import com.lulan.shincolle.client.render.item.RenderPointerItem;
import com.lulan.shincolle.client.render.special.RenderAbyssMissile;
import com.lulan.shincolle.client.render.special.RenderFlare;
import com.lulan.shincolle.client.render.special.RenderProjectileBeam;
import com.lulan.shincolle.client.render.special.RenderVortex;
import com.lulan.shincolle.entity.battleship.EntityBattleshipNGT;
import com.lulan.shincolle.entity.battleship.EntityBattleshipNGTBoss;
import com.lulan.shincolle.entity.battleship.EntityBattleshipRe;
import com.lulan.shincolle.entity.battleship.EntityBattleshipTa;
import com.lulan.shincolle.entity.battleship.EntityBattleshipYMT;
import com.lulan.shincolle.entity.battleship.EntityBattleshipYMTBoss;
import com.lulan.shincolle.entity.carrier.EntityCarrierAkagi;
import com.lulan.shincolle.entity.carrier.EntityCarrierAkagiBoss;
import com.lulan.shincolle.entity.carrier.EntityCarrierKaga;
import com.lulan.shincolle.entity.carrier.EntityCarrierKagaBoss;
import com.lulan.shincolle.entity.carrier.EntityCarrierWo;
import com.lulan.shincolle.entity.cruiser.EntityHeavyCruiserNe;
import com.lulan.shincolle.entity.cruiser.EntityHeavyCruiserRi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerHa;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerI;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerIkazuchi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerIkazuchiMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerInazuma;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerInazumaMob;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerNi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerRo;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerShimakaze;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerShimakazeBoss;
import com.lulan.shincolle.entity.hime.EntityAirfieldHime;
import com.lulan.shincolle.entity.hime.EntityBattleshipHime;
import com.lulan.shincolle.entity.hime.EntityCarrierWD;
import com.lulan.shincolle.entity.hime.EntityHarbourHime;
import com.lulan.shincolle.entity.hime.EntityNorthernHime;
import com.lulan.shincolle.entity.mounts.EntityMountAfH;
import com.lulan.shincolle.entity.mounts.EntityMountBaH;
import com.lulan.shincolle.entity.mounts.EntityMountCaWD;
import com.lulan.shincolle.entity.mounts.EntityMountHbH;
import com.lulan.shincolle.entity.other.EntityAbyssMissile;
import com.lulan.shincolle.entity.other.EntityAirplane;
import com.lulan.shincolle.entity.other.EntityAirplaneT;
import com.lulan.shincolle.entity.other.EntityAirplaneTHostile;
import com.lulan.shincolle.entity.other.EntityAirplaneTakoyaki;
import com.lulan.shincolle.entity.other.EntityAirplaneZero;
import com.lulan.shincolle.entity.other.EntityAirplaneZeroHostile;
import com.lulan.shincolle.entity.other.EntityFloatingFort;
import com.lulan.shincolle.entity.other.EntityProjectileBeam;
import com.lulan.shincolle.entity.other.EntityRensouhou;
import com.lulan.shincolle.entity.other.EntityRensouhouBoss;
import com.lulan.shincolle.entity.other.EntityRensouhouS;
import com.lulan.shincolle.entity.renderentity.EntityRenderFlare;
import com.lulan.shincolle.entity.renderentity.EntityRenderLargeShipyard;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.entity.submarine.EntitySubmRo500;
import com.lulan.shincolle.entity.submarine.EntitySubmRo500Mob;
import com.lulan.shincolle.entity.submarine.EntitySubmSo;
import com.lulan.shincolle.entity.submarine.EntitySubmU511;
import com.lulan.shincolle.entity.submarine.EntitySubmU511Mob;
import com.lulan.shincolle.entity.submarine.EntitySubmYo;
import com.lulan.shincolle.entity.transport.EntityTransportWa;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;
import com.lulan.shincolle.reference.ID;
import com.lulan.shincolle.tileentity.TileEntityDesk;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	//client world會隨玩家所在位置持續改變, 但是dim id永遠都是0不變, 無法反推dim id?
	public static World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
	
	//client player
	public static EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}
	
	//client minecraft
	public static GameSettings getGameSetting() {
		return Minecraft.getMinecraft().gameSettings;
	}
	
	//client minecraft
	public static Minecraft getMineraft() {
		return Minecraft.getMinecraft();
	}

	//登錄偵測按鍵
	@Override
	public void registerKeyBindings() {
	//	ClientRegistry.registerKeyBinding(KeyBindings.repair);
		
	}
	
	//登錄模型render
	@Override
	public void registerRender() {
		
		TileEntitySpecialRenderer tesrBlockSmallShipyard = new RenderSmallShipyard();
		
		//entity render (model class, shadow size)
		RenderingRegistry.registerEntityRenderingHandler(EntityAirfieldHime.class, new BasicShipRenderer(new ModelAirfieldHime(), 0.7F, ID.Ship.AirfieldHime));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipHime.class, new BasicShipRenderer(new ModelBattleshipHime(), 0.7F, ID.Ship.BattleshipHime));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipNGT.class, new BasicShipRenderer(new ModelBattleshipNagato(0), 0.7F, ID.Ship.BattleshipNagato));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipNGTBoss.class, new BossShipRenderer(new ModelBattleshipNagato(1), 1.2F, ID.Ship.BattleshipNagato));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipYMT.class, new BasicShipRenderer(new ModelBattleshipYamato(0), 0.7F, ID.Ship.BattleshipYamato));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipYMTBoss.class, new BossShipRenderer(new ModelBattleshipYamato(1), 1.2F, ID.Ship.BattleshipYamato));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipTa.class, new BasicShipRenderer(new ModelBattleshipTa(), 0.7F, ID.Ship.BattleshipTA));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipRe.class, new BasicShipRenderer(new ModelBattleshipRe(), 0.7F, ID.Ship.BattleshipRE));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierAkagi.class, new BasicShipRenderer(new ModelCarrierAkagi(0), 0.7F, ID.Ship.CarrierAkagi));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierAkagiBoss.class, new BossShipRenderer(new ModelCarrierAkagi(1), 1.2F, ID.Ship.CarrierAkagi));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierKaga.class, new BasicShipRenderer(new ModelCarrierKaga(0), 0.7F, ID.Ship.CarrierKaga));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierKagaBoss.class, new BossShipRenderer(new ModelCarrierKaga(1), 1.2F, ID.Ship.CarrierKaga));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWD.class, new BasicShipRenderer(new ModelCarrierWDemon(), 1F, ID.Ship.CarrierWD));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWo.class, new BasicShipRenderer(new ModelCarrierWo(), 1F, ID.Ship.CarrierWO));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerI.class, new BasicShipRenderer(new ModelDestroyerI(), 1F, ID.Ship.DestroyerI));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerRo.class, new BasicShipRenderer(new ModelDestroyerRo(), 1F, ID.Ship.DestroyerRO));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHa.class, new BasicShipRenderer(new ModelDestroyerHa(), 1F, ID.Ship.DestroyerHA));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerNi.class, new BasicShipRenderer(new ModelDestroyerNi(), 1F, ID.Ship.DestroyerNI));
//		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerAkatsuki.class, new RenderDestroyerAkatsuki(new ModelDestroyerAkatsuki(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerAkatsukiMob.class, new RenderDestroyerAkatsuki(new ModelDestroyerAkatsuki(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHibiki.class, new RenderDestroyerHibiki(new ModelDestroyerHibiki(), 0.5F));
//		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHibikiMob.class, new RenderDestroyerHibiki(new ModelDestroyerHibiki(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerIkazuchi.class, new BasicShipRenderer(new ModelDestroyerIkazuchi(), 0.5F, ID.Ship.DestroyerIkazuchi));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerIkazuchiMob.class, new BasicShipRenderer(new ModelDestroyerIkazuchi(), 0.5F, ID.Ship.DestroyerIkazuchi));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerInazuma.class, new BasicShipRenderer(new ModelDestroyerInazuma(), 0.5F, ID.Ship.DestroyerInazuma));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerInazumaMob.class, new BasicShipRenderer(new ModelDestroyerInazuma(), 0.5F, ID.Ship.DestroyerInazuma));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakaze.class, new BasicShipRenderer(new ModelDestroyerShimakaze(), 0.5F, ID.Ship.DestroyerShimakaze));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakazeBoss.class, new BossShipRenderer(new ModelDestroyerShimakazeBoss(), 1F, ID.Ship.DestroyerShimakaze));
		RenderingRegistry.registerEntityRenderingHandler(EntityHarbourHime.class, new BasicShipRenderer(new ModelHarbourHime(), 0.8F, ID.Ship.HarbourHime));
		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserRi.class, new BasicShipRenderer(new ModelHeavyCruiserRi(), 0.7F, ID.Ship.HeavyCruiserRI));
		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserNe.class, new BasicShipRenderer(new ModelHeavyCruiserNe(), 0.7F, ID.Ship.HeavyCruiserNE));
		RenderingRegistry.registerEntityRenderingHandler(EntityNorthernHime.class, new RenderNorthernHime(new ModelNorthernHime(), 0.5F, ID.Ship.NorthernHime));
		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhou.class, new MiscMobRenderer(new ModelRensouhou(0.3F, 3F), 0.4F, 5));
		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhouBoss.class, new MiscMobRenderer(new ModelRensouhou(1F, 0F), 1F, 5));
		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhouS.class, new MiscMobRenderer(new ModelRensouhouS(), 0.4F, 6));
//		RenderingRegistry.registerEntityRenderingHandler(EntitySubmKa.class, new BasicShipRenderer(new ModelSubmKa(), 0.5F, ID.Ship.SubmarineKA));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmYo.class, new BasicShipRenderer(new ModelSubmYo(), 0.5F, ID.Ship.SubmarineYO));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmSo.class, new BasicShipRenderer(new ModelSubmSo(), 0.5F, ID.Ship.SubmarineSO));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmRo500.class, new BasicShipRenderer(new ModelSubmRo500(), 0.5F, ID.Ship.SubmarineRo500));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmRo500Mob.class, new BasicShipRenderer(new ModelSubmRo500(), 0.5F, ID.Ship.SubmarineRo500));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmU511.class, new BasicShipRenderer(new ModelSubmU511(), 0.5F, ID.Ship.SubmarineU511));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmU511Mob.class, new BasicShipRenderer(new ModelSubmU511(), 0.5F, ID.Ship.SubmarineU511));
		RenderingRegistry.registerEntityRenderingHandler(EntityTransportWa.class, new BasicShipRenderer(new ModelTransportWa(), 0.7F, ID.Ship.TransportWA));
		
		//mount render
		RenderingRegistry.registerEntityRenderingHandler(EntityMountAfH.class, new MountsShipRenderer(new ModelMountAfH(), 1.5F, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityMountBaH.class, new MountsShipRenderer(new ModelMountBaH(), 1.5F, 1));
		RenderingRegistry.registerEntityRenderingHandler(EntityMountCaWD.class, new MountsShipRenderer(new ModelMountCaWD(), 1.5F, 2));
		RenderingRegistry.registerEntityRenderingHandler(EntityMountHbH.class, new MountsShipRenderer(new ModelMountHbH(), 1.5F, 3));
		
		//test entity render
//		RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new RenderTest(new ModelTest(), 1F));

		//projectile render
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssMissile.class, new RenderAbyssMissile(0.75F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplane.class, new MiscMobRenderer(new ModelAirplane(), 0.5F, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneTakoyaki.class, new MiscMobRenderer(new ModelTakoyaki(), 0.5F, 1));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneT.class, new MiscMobRenderer(new ModelAirplaneT(0), 0.5F, 3));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneZero.class, new MiscMobRenderer(new ModelAirplaneZero(0), 0.5F, 2));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneTHostile.class, new MiscMobRenderer(new ModelAirplaneT(1), 0.7F, 3));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneZeroHostile.class, new MiscMobRenderer(new ModelAirplaneZero(1), 0.7F, 2));
		RenderingRegistry.registerEntityRenderingHandler(EntityFloatingFort.class, new MiscMobRenderer(new ModelFloatingFort(), 0.5F, 4));
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBeam.class, new RenderProjectileBeam());

		//render entity render
		RenderingRegistry.registerEntityRenderingHandler(EntityRenderLargeShipyard.class, new RenderLargeShipyard());
		RenderingRegistry.registerEntityRenderingHandler(EntityRenderVortex.class, new RenderVortex());
		RenderingRegistry.registerEntityRenderingHandler(EntityRenderFlare.class, new RenderFlare());
		
		//block tile entity render
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDesk.class, new RenderBlockDesk());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmallShipyard.class, tesrBlockSmallShipyard);
		
		//custom block item render
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.BlockDesk), new RenderBlockDeskItem(new RenderBlockDesk(), new TileEntityDesk()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.BlockSmallShipyard), new RenderSmallShipyardItem(tesrBlockSmallShipyard, new TileEntitySmallShipyard()));
		MinecraftForgeClient.registerItemRenderer(ModItems.PointerItem, new RenderPointerItem());
		
		//custom item entity render
		RenderingRegistry.registerEntityRenderingHandler(BasicEntityItem.class, new RenderBasicEntityItem(0.4F));
	}

	
}
