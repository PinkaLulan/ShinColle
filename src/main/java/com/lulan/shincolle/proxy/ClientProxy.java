package com.lulan.shincolle.proxy;

import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.client.model.ModelAbyssMissile;
import com.lulan.shincolle.client.model.ModelAirplane;
import com.lulan.shincolle.client.model.ModelBattleshipRe;
import com.lulan.shincolle.client.model.ModelCarrierWo;
import com.lulan.shincolle.client.model.ModelDestroyerI;
import com.lulan.shincolle.client.model.ModelEntityTest;
import com.lulan.shincolle.client.model.ModelHeavyCruiserRi;
import com.lulan.shincolle.client.model.ModelLargeShipyard;
import com.lulan.shincolle.client.model.ModelTakoyaki;
import com.lulan.shincolle.client.model.ModelVortex;
import com.lulan.shincolle.client.render.RenderAbyssMissile;
import com.lulan.shincolle.client.render.RenderAirplane;
import com.lulan.shincolle.client.render.RenderAirplaneTakoyaki;
import com.lulan.shincolle.client.render.RenderBattleshipRe;
import com.lulan.shincolle.client.render.RenderCarrierWo;
import com.lulan.shincolle.client.render.RenderDestroyerI;
import com.lulan.shincolle.client.render.RenderHeavyCruiserRi;
import com.lulan.shincolle.client.render.RenderLargeShipyard;
import com.lulan.shincolle.client.render.RenderSmallShipyard;
import com.lulan.shincolle.client.render.RenderSmallShipyardItem;
import com.lulan.shincolle.client.render.RenderTest;
import com.lulan.shincolle.client.render.RenderVortex;
import com.lulan.shincolle.client.settings.KeyBindings;
import com.lulan.shincolle.entity.EntityAbyssMissile;
import com.lulan.shincolle.entity.EntityAirplane;
import com.lulan.shincolle.entity.EntityAirplaneTakoyaki;
import com.lulan.shincolle.entity.EntityBattleshipRe;
import com.lulan.shincolle.entity.EntityCarrierWo;
import com.lulan.shincolle.entity.EntityDestroyerI;
import com.lulan.shincolle.entity.EntityHeavyCruiserRi;
import com.lulan.shincolle.entity.EntityTest;
import com.lulan.shincolle.entity.renderentity.EntityRenderLargeShipyard;
import com.lulan.shincolle.entity.renderentity.EntityRenderVortex;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
import com.lulan.shincolle.tileentity.TileMultiGrudgeHeavy;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	//µn¿ý°»´ú«öÁä
	@Override
	public void registerKeyBindings() {
	//	ClientRegistry.registerKeyBinding(KeyBindings.repair);
		
	}
	
	//µn¿ý¼Ò«¬render
	@Override
	public void registerRender() {
		
		TileEntitySpecialRenderer tesrBlockSmallShipyard = new RenderSmallShipyard();
		
		//entity render
		RenderingRegistry.registerEntityRenderingHandler(EntityBattleshipRe.class, new RenderBattleshipRe(new ModelBattleshipRe(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarrierWo.class, new RenderCarrierWo(new ModelCarrierWo(), 1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerI.class, new RenderDestroyerI(new ModelDestroyerI(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(EntityHeavyCruiserRi.class, new RenderHeavyCruiserRi(new ModelHeavyCruiserRi(), 1F));
		
		//test entity render
		RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new RenderTest(new ModelEntityTest(), 1F));

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

	}

	
}
