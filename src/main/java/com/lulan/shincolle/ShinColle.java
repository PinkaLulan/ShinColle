package com.lulan.shincolle;


import java.util.Iterator;
import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraftforge.common.MinecraftForge;

import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.handler.EVENT_BUS_EventHandler;
import com.lulan.shincolle.handler.GuiHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModEntity;
import com.lulan.shincolle.init.ModEvents;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModTileEntity;
import com.lulan.shincolle.init.ModWorldGen;
import com.lulan.shincolle.init.ModRecipes;
import com.lulan.shincolle.proxy.IProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.GuiHelper;
import com.lulan.shincolle.utility.LogHelper;
import com.lulan.shincolle.utility.MulitBlockHelper;

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
	public static IProxy proxy;
	
	
	//pre initial: load config, block/item/entity/render init, event handler init
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {		
		//config inti
		ConfigHandler.init(event.getSuggestedConfigurationFile());	//load config file

		ModItems.init();

		ModBlocks.init();

		ModEntity.init();
		//keybinding register
	//	proxy.registerKeyBindings();
		//render register
		proxy.registerRender();
		//Packet channel register (simple network)
		proxy.registerChannel();
		
		LogHelper.info("preInit complete.");	//debug
	}
	
	//initial: recipe/tileentity/gui/worldgen init, event handler regist, create data handler, request mod interact
	//         AND oreDictionary registr
	@Mod.EventHandler
	public void Init(FMLInitializationEvent event) {
		//GUI register
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		ModEvents.init();
		
		ModRecipes.init();

		ModTileEntity.init();

		ModWorldGen.init();
		
		LogHelper.info("Init complete.");	//debug	
		
		//Waila tooltip provider (NYI)
        //FMLInterModComms.sendMessage("Waila", "register", "com.lulan.shincolle.waila.WailaDataProvider.callbackRegister");
	}
	
	//post initial: mod interact
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		
//		//list all entity
//		Iterator iter = EntityList.classToStringMapping.entrySet().iterator();
//		while(iter.hasNext()) {
//			Map.Entry entry = (Map.Entry)iter.next();
//		    Object key = entry.getKey();
//		    Object val = entry.getValue();
//		    LogHelper.info("DEBUG : list entity class: "+key+" , "+val);
//		}	
		
//		for(String oreName : OreDictionary.getOreNames()) {	//list all oreDictionary  (DEBUG)
//			LogHelper.info(oreName);
//		}
		
//		MulitBlockHelper.printPattern();
//		GuiHelper.printButtons();
		LogHelper.info("postInit complete.");	//debug
	}
	

}
