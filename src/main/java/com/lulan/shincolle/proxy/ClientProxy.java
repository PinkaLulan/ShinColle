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
import com.lulan.shincolle.client.model.ModelBattleshipHime;
import com.lulan.shincolle.client.model.ModelBattleshipNagato;
import com.lulan.shincolle.client.model.ModelBattleshipRe;
import com.lulan.shincolle.client.model.ModelBattleshipTa;
import com.lulan.shincolle.client.model.ModelCarrierWo;
import com.lulan.shincolle.client.model.ModelDestroyerHa;
import com.lulan.shincolle.client.model.ModelDestroyerI;
import com.lulan.shincolle.client.model.ModelDestroyerNi;
import com.lulan.shincolle.client.model.ModelDestroyerRo;
import com.lulan.shincolle.client.model.ModelDestroyerShimakaze;
import com.lulan.shincolle.client.model.ModelDestroyerShimakazeBoss;
import com.lulan.shincolle.client.model.ModelHeavyCruiserRi;
import com.lulan.shincolle.client.model.ModelMountAfH;
import com.lulan.shincolle.client.model.ModelMountBaH;
import com.lulan.shincolle.client.model.ModelRensouhou;
import com.lulan.shincolle.client.model.ModelRensouhouS;
import com.lulan.shincolle.client.model.ModelSubmRo500;
import com.lulan.shincolle.client.model.ModelSubmU511;
import com.lulan.shincolle.client.model.ModelTakoyaki;
import com.lulan.shincolle.client.render.RenderAbyssMissile;
import com.lulan.shincolle.client.render.RenderAirfieldHime;
import com.lulan.shincolle.client.render.RenderAirplane;
import com.lulan.shincolle.client.render.RenderAirplaneTakoyaki;
import com.lulan.shincolle.client.render.RenderBasicEntityItem;
import com.lulan.shincolle.client.render.RenderBattleshipHime;
import com.lulan.shincolle.client.render.RenderBattleshipNGT;
import com.lulan.shincolle.client.render.RenderBattleshipNGTBoss;
import com.lulan.shincolle.client.render.RenderBattleshipRe;
import com.lulan.shincolle.client.render.RenderBattleshipTa;
import com.lulan.shincolle.client.render.RenderCarrierWo;
import com.lulan.shincolle.client.render.RenderDestroyerHa;
import com.lulan.shincolle.client.render.RenderDestroyerI;
import com.lulan.shincolle.client.render.RenderDestroyerNi;
import com.lulan.shincolle.client.render.RenderDestroyerRo;
import com.lulan.shincolle.client.render.RenderDestroyerShimakaze;
import com.lulan.shincolle.client.render.RenderDestroyerShimakazeBoss;
import com.lulan.shincolle.client.render.RenderHeavyCruiserRi;
import com.lulan.shincolle.client.render.RenderLargeShipyard;
import com.lulan.shincolle.client.render.RenderMountAfH;
import com.lulan.shincolle.client.render.RenderMountBaH;
import com.lulan.shincolle.client.render.RenderRensouhou;
import com.lulan.shincolle.client.render.RenderRensouhouS;
import com.lulan.shincolle.client.render.RenderSmallShipyard;
import com.lulan.shincolle.client.render.RenderSmallShipyardItem;
import com.lulan.shincolle.client.render.RenderSubmRo500;
import com.lulan.shincolle.client.render.RenderSubmU511;
import com.lulan.shincolle.client.render.RenderVortex;
import com.lulan.shincolle.entity.EntityAbyssMissile;
import com.lulan.shincolle.entity.EntityAirfieldHime;
import com.lulan.shincolle.entity.EntityAirplane;
import com.lulan.shincolle.entity.EntityAirplaneTakoyaki;
import com.lulan.shincolle.entity.EntityBattleshipHime;
import com.lulan.shincolle.entity.EntityBattleshipNGT;
import com.lulan.shincolle.entity.EntityBattleshipNGTBoss;
import com.lulan.shincolle.entity.EntityBattleshipRe;
import com.lulan.shincolle.entity.EntityBattleshipTa;
import com.lulan.shincolle.entity.EntityCarrierWo;
import com.lulan.shincolle.entity.EntityDestroyerHa;
import com.lulan.shincolle.entity.EntityDestroyerI;
import com.lulan.shincolle.entity.EntityDestroyerNi;
import com.lulan.shincolle.entity.EntityDestroyerRo;
import com.lulan.shincolle.entity.EntityDestroyerShimakaze;
import com.lulan.shincolle.entity.EntityDestroyerShimakazeBoss;
import com.lulan.shincolle.entity.EntityHeavyCruiserRi;
import com.lulan.shincolle.entity.EntityMountAfH;
import com.lulan.shincolle.entity.EntityMountBaH;
import com.lulan.shincolle.entity.EntityRensouhou;
import com.lulan.shincolle.entity.EntityRensouhouBoss;
import com.lulan.shincolle.entity.EntityRensouhouS;
import com.lulan.shincolle.entity.EntitySubmRo500;
import com.lulan.shincolle.entity.EntitySubmRo500Mob;
import com.lulan.shincolle.entity.EntitySubmU511;
import com.lulan.shincolle.entity.EntitySubmU511Mob;
import com.lulan.shincolle.entity.renderentity.EntityRenderLargeShipyard;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.item.BasicEntityItem;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipTa.class, new RenderBattleshipTa(new ModelBattleshipTa(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipRe.class, new RenderBattleshipRe(new ModelBattleshipRe(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWo.class, new RenderCarrierWo(new ModelCarrierWo(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerI.class, new RenderDestroyerI(new ModelDestroyerI(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerRo.class, new RenderDestroyerRo(new ModelDestroyerRo(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerHa.class, new RenderDestroyerHa(new ModelDestroyerHa(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerNi.class, new RenderDestroyerNi(new ModelDestroyerNi(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakaze.class, new RenderDestroyerShimakaze(new ModelDestroyerShimakaze(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerShimakazeBoss.class, new RenderDestroyerShimakazeBoss(new ModelDestroyerShimakazeBoss(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserRi.class, new RenderHeavyCruiserRi(new ModelHeavyCruiserRi(), 0.7F));
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
		
		//test entity render
//		RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new RenderTest(new ModelTest(), 1F));

		//projectile render
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssMissile.class, new RenderAbyssMissile(0.75F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplane.class, new RenderAirplane(new ModelAirplane(), 0.5F));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirplaneTakoyaki.class, new RenderAirplaneTakoyaki(new ModelTakoyaki(), 0.5F));

		//render entity render
		RenderingRegistry.registerEntityRenderingHandler(EntityRenderLargeShipyard.class, new RenderLargeShipyard());
		RenderingRegistry.registerEntityRenderingHandler(EntityRenderVortex.class, new RenderVortex());
		
		//block tile entity render
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmallShipyard.class, tesrBlockSmallShipyard);
	
		//custom block item render
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.BlockSmallShipyard), new RenderSmallShipyardItem(tesrBlockSmallShipyard, new TileEntitySmallShipyard()));

		//custom item entity render
		RenderingRegistry.registerEntityRenderingHandler(BasicEntityItem.class, new RenderBasicEntityItem(0.4F));
	}

	
}
