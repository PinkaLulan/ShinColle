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
import com.lulan.shincolle.client.model.ModelCarrierKaga;
import com.lulan.shincolle.client.model.ModelCarrierWDemon;
import com.lulan.shincolle.client.model.ModelCarrierWo;
import com.lulan.shincolle.client.model.ModelDestroyerHa;
import com.lulan.shincolle.client.model.ModelDestroyerI;
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
import com.lulan.shincolle.client.model.ModelSubmU511;
import com.lulan.shincolle.client.model.ModelTakoyaki;
import com.lulan.shincolle.client.render.RenderAirfieldHime;
import com.lulan.shincolle.client.render.RenderAirplane;
import com.lulan.shincolle.client.render.RenderAirplaneT;
import com.lulan.shincolle.client.render.RenderAirplaneTakoyaki;
import com.lulan.shincolle.client.render.RenderAirplaneZero;
import com.lulan.shincolle.client.render.RenderBattleshipHime;
import com.lulan.shincolle.client.render.RenderBattleshipNGT;
import com.lulan.shincolle.client.render.RenderBattleshipNGTBoss;
import com.lulan.shincolle.client.render.RenderBattleshipRe;
import com.lulan.shincolle.client.render.RenderBattleshipTa;
import com.lulan.shincolle.client.render.RenderBattleshipYMT;
import com.lulan.shincolle.client.render.RenderBattleshipYMTBoss;
import com.lulan.shincolle.client.render.RenderBlockDesk;
import com.lulan.shincolle.client.render.RenderBlockDeskItem;
import com.lulan.shincolle.client.render.RenderCarrierKaga;
import com.lulan.shincolle.client.render.RenderCarrierKagaBoss;
import com.lulan.shincolle.client.render.RenderCarrierWDemon;
import com.lulan.shincolle.client.render.RenderCarrierWo;
import com.lulan.shincolle.client.render.RenderDestroyerHa;
import com.lulan.shincolle.client.render.RenderDestroyerI;
import com.lulan.shincolle.client.render.RenderDestroyerNi;
import com.lulan.shincolle.client.render.RenderDestroyerRo;
import com.lulan.shincolle.client.render.RenderDestroyerShimakaze;
import com.lulan.shincolle.client.render.RenderDestroyerShimakazeBoss;
import com.lulan.shincolle.client.render.RenderFloatingFort;
import com.lulan.shincolle.client.render.RenderHarbourHime;
import com.lulan.shincolle.client.render.RenderHeavyCruiserNe;
import com.lulan.shincolle.client.render.RenderHeavyCruiserRi;
import com.lulan.shincolle.client.render.RenderMountAfH;
import com.lulan.shincolle.client.render.RenderMountBaH;
import com.lulan.shincolle.client.render.RenderMountCaWD;
import com.lulan.shincolle.client.render.RenderMountHbH;
import com.lulan.shincolle.client.render.RenderNorthernHime;
import com.lulan.shincolle.client.render.RenderRensouhou;
import com.lulan.shincolle.client.render.RenderRensouhouS;
import com.lulan.shincolle.client.render.RenderSubmRo500;
import com.lulan.shincolle.client.render.RenderSubmU511;
import com.lulan.shincolle.client.render.block.RenderLargeShipyard;
import com.lulan.shincolle.client.render.block.RenderSmallShipyard;
import com.lulan.shincolle.client.render.block.RenderSmallShipyardItem;
import com.lulan.shincolle.client.render.item.RenderBasicEntityItem;
import com.lulan.shincolle.client.render.item.RenderPointerItem;
import com.lulan.shincolle.client.render.special.RenderAbyssMissile;
import com.lulan.shincolle.client.render.special.RenderProjectileBeam;
import com.lulan.shincolle.client.render.special.RenderVortex;
import com.lulan.shincolle.entity.battleship.EntityBattleshipNGT;
import com.lulan.shincolle.entity.battleship.EntityBattleshipNGTBoss;
import com.lulan.shincolle.entity.battleship.EntityBattleshipRe;
import com.lulan.shincolle.entity.battleship.EntityBattleshipTa;
import com.lulan.shincolle.entity.battleship.EntityBattleshipYMT;
import com.lulan.shincolle.entity.battleship.EntityBattleshipYMTBoss;
import com.lulan.shincolle.entity.carrier.EntityCarrierKaga;
import com.lulan.shincolle.entity.carrier.EntityCarrierKagaBoss;
import com.lulan.shincolle.entity.carrier.EntityCarrierWo;
import com.lulan.shincolle.entity.cruiser.EntityHeavyCruiserNe;
import com.lulan.shincolle.entity.cruiser.EntityHeavyCruiserRi;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerHa;
import com.lulan.shincolle.entity.destroyer.EntityDestroyerI;
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
import com.lulan.shincolle.entity.renderentity.EntityRenderLargeShipyard;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.entity.submarine.EntitySubmRo500;
import com.lulan.shincolle.entity.submarine.EntitySubmRo500Mob;
import com.lulan.shincolle.entity.submarine.EntitySubmU511;
import com.lulan.shincolle.entity.submarine.EntitySubmU511Mob;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.item.BasicEntityItem;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityAirfieldHime.class, new RenderAirfieldHime(new ModelAirfieldHime(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipHime.class, new RenderBattleshipHime(new ModelBattleshipHime(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipNGT.class, new RenderBattleshipNGT(new ModelBattleshipNagato(0), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipNGTBoss.class, new RenderBattleshipNGTBoss(new ModelBattleshipNagato(1), 1.2F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipYMT.class, new RenderBattleshipYMT(new ModelBattleshipYamato(0), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipYMTBoss.class, new RenderBattleshipYMTBoss(new ModelBattleshipYamato(1), 1.2F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipTa.class, new RenderBattleshipTa(new ModelBattleshipTa(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipRe.class, new RenderBattleshipRe(new ModelBattleshipRe(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierKaga.class, new RenderCarrierKaga(new ModelCarrierKaga(0), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierKagaBoss.class, new RenderCarrierKagaBoss(new ModelCarrierKaga(1), 1.2F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWD.class, new RenderCarrierWDemon(new ModelCarrierWDemon(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWo.class, new RenderCarrierWo(new ModelCarrierWo(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerI.class, new RenderDestroyerI(new ModelDestroyerI(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerRo.class, new RenderDestroyerRo(new ModelDestroyerRo(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHa.class, new RenderDestroyerHa(new ModelDestroyerHa(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerNi.class, new RenderDestroyerNi(new ModelDestroyerNi(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakaze.class, new RenderDestroyerShimakaze(new ModelDestroyerShimakaze(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakazeBoss.class, new RenderDestroyerShimakazeBoss(new ModelDestroyerShimakazeBoss(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHarbourHime.class, new RenderHarbourHime(new ModelHarbourHime(), 0.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserRi.class, new RenderHeavyCruiserRi(new ModelHeavyCruiserRi(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserNe.class, new RenderHeavyCruiserNe(new ModelHeavyCruiserNe(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityNorthernHime.class, new RenderNorthernHime(new ModelNorthernHime(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhou.class, new RenderRensouhou(new ModelRensouhou(0.3F, 3F), 0.4F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhouBoss.class, new RenderRensouhou(new ModelRensouhou(1F, 0F), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRensouhouS.class, new RenderRensouhouS(new ModelRensouhouS(), 0.4F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmRo500.class, new RenderSubmRo500(new ModelSubmRo500(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmRo500Mob.class, new RenderSubmRo500(new ModelSubmRo500(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmU511.class, new RenderSubmU511(new ModelSubmU511(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmU511Mob.class, new RenderSubmU511(new ModelSubmU511(), 0.5F));
		
		//mount render
		RenderingRegistry.registerEntityRenderingHandler(EntityMountAfH.class, new RenderMountAfH(new ModelMountAfH(), 1.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMountBaH.class, new RenderMountBaH(new ModelMountBaH(), 1.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMountCaWD.class, new RenderMountCaWD(new ModelMountCaWD(), 1.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityMountHbH.class, new RenderMountHbH(new ModelMountHbH(), 1.5F));
		
		//test entity render
//		RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new RenderTest(new ModelTest(), 1F));

		//projectile render
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssMissile.class, new RenderAbyssMissile(0.75F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplane.class, new RenderAirplane(new ModelAirplane(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneTakoyaki.class, new RenderAirplaneTakoyaki(new ModelTakoyaki(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneT.class, new RenderAirplaneT(new ModelAirplaneT(0), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneZero.class, new RenderAirplaneZero(new ModelAirplaneZero(0), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneTHostile.class, new RenderAirplaneT(new ModelAirplaneT(1), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneZeroHostile.class, new RenderAirplaneZero(new ModelAirplaneZero(1), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityFloatingFort.class, new RenderFloatingFort(new ModelFloatingFort(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBeam.class, new RenderProjectileBeam());

		//render entity render
		RenderingRegistry.registerEntityRenderingHandler(EntityRenderLargeShipyard.class, new RenderLargeShipyard());
		RenderingRegistry.registerEntityRenderingHandler(EntityRenderVortex.class, new RenderVortex());
		
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
