package com.lulan.shincolle;


import com.lulan.shincolle.handler.ConfigurationHandler;
import com.lulan.shincolle.handler.GuiHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModEntity;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModTileEntity;
import com.lulan.shincolle.init.ModWorldGen;
import com.lulan.shincolle.init.Recipes;
import com.lulan.shincolle.proxy.ClientProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;		//mod基本資訊
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;	//mod init
import cpw.mods.fml.common.event.FMLPostInitializationEvent;//mod post init
import cpw.mods.fml.common.event.FMLPreInitializationEvent;	//mod pre init
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY)
public class ShinColle {
	
	//mod instance
	@Mod.Instance( Reference.MOD_ID )
	public static ShinColle instance;
	
	//proxy for client/server event
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static ClientProxy clientProxy;
	
	//pre initial: load config, block/item/entity/render init, event handler init
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {		
		//config inti
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());	//load config file
		//item init
		ModItems.init();
		//block init
		ModBlocks.init();
		//entity init
		ModEntity.init();
		//keybinding init
	//	clientProxy.registerKeyBindings();
		//render init
		clientProxy.registerRender();
		
	//	LogHelper.info("preInit complete.");	//debug
	}
	
	//initial: recipe/tileentity/gui/worldgen init, event handler regist, create data handler, request mod interact
	//         AND oreDictionary registr
	@Mod.EventHandler
	public void Init(FMLInitializationEvent event) {
		//登錄以下handler到event handler中 使其能接收event
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());	 //config event handler
	//	FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());  //key event handler
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		//recipe init
		Recipes.init();
		//tile entity init
		ModTileEntity.init();
		//worldgen init
		ModWorldGen.init();
		
		LogHelper.info("Init complete.");	//debug	
	}
	
	//post initial: mod interact
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	//	LogHelper.info("postInit complete.");	//debug
		
		/*
		for(String oreName : OreDictionary.getOreNames()) {	//list all oreDictionary  (DEBUG)
			LogHelper.info(oreName);
		}
		*/
	}
	

}
