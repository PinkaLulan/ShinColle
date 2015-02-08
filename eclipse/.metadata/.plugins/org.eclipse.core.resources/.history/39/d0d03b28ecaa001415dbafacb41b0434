package com.lulan.shincolle.proxy;

import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.lulan.shincolle.ShinColle;
import com.lulan.shincolle.client.settings.KeyBindings;
import com.lulan.shincolle.entity.EntityAbyssMissile;
import com.lulan.shincolle.entity.EntityDestroyerI;
import com.lulan.shincolle.entity.EntityTest;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.model.ModelAbyssMissile;
import com.lulan.shincolle.model.ModelDestroyerI;
import com.lulan.shincolle.model.ModelEntityTest;
import com.lulan.shincolle.render.RenderAbyssMissile;
import com.lulan.shincolle.render.RenderDestroyerI;
import com.lulan.shincolle.render.RenderSmallShipyard;
import com.lulan.shincolle.render.RenderSmallShipyardItem;
import com.lulan.shincolle.render.RenderTest;
import com.lulan.shincolle.tileentity.TileEntitySmallShipyard;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityDestroyerI.class, new RenderDestroyerI(new ModelDestroyerI(), 0.3F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTest.class, new RenderTest(new ModelEntityTest(), 1F));
		
		//projectile render
		RenderingRegistry.registerEntityRenderingHandler(EntityAbyssMissile.class, new RenderAbyssMissile(new ModelAbyssMissile(), 0.75F));

		//block tile entity render
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmallShipyard.class, tesrBlockSmallShipyard);
	
		//custom block item render
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.BlockSmallShipyard), new RenderSmallShipyardItem(tesrBlockSmallShipyard, new TileEntitySmallShipyard()));
	
	}

	
}
