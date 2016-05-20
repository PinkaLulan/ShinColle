package com.lulan.shincolle;


import net.minecraftforge.common.ForgeChunkManager;

import com.lulan.shincolle.handler.ChunkLoaderHandler;
import com.lulan.shincolle.handler.CommandHandler;
import com.lulan.shincolle.handler.ConfigHandler;
import com.lulan.shincolle.handler.GuiHandler;
import com.lulan.shincolle.init.ModBlocks;
import com.lulan.shincolle.init.ModEntity;
import com.lulan.shincolle.init.ModEvents;
import com.lulan.shincolle.init.ModItems;
import com.lulan.shincolle.init.ModOres;
import com.lulan.shincolle.init.ModRecipes;
import com.lulan.shincolle.init.ModTileEntity;
import com.lulan.shincolle.init.ModWorldGen;
import com.lulan.shincolle.intermod.forestry.ShinBees;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.IProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;		//mod基本資訊
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;	//mod init
import cpw.mods.fml.common.event.FMLPostInitializationEvent;//mod post init
import cpw.mods.fml.common.event.FMLPreInitializationEvent;	//mod pre init
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies="required-after:Forge@[10.13.4.1614,)")
public class ShinColle
{
	
	//mod instance
	@Mod.Instance( Reference.MOD_ID )
	public static ShinColle instance;
	
	//proxy for client/server event
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;
	
	
	/** pre initial: load config, block/item/entity/render init, event handler init */
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{		
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
		
		LogHelper.info("DEBUG : preInit complete.");	//debug
	}
	
	/**initial: recipe/tileentity/gui/worldgen init, event handler regist, create data handler, 
	 *          request mod interact AND oreDictionary registr
	 */
	@Mod.EventHandler
	public void Init(FMLInitializationEvent event)
	{
		//GUI register
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		ModOres.oreDictRegister();
		
		ModEvents.init();
		
		ModRecipes.init();

		ModTileEntity.init();
		
		LogHelper.info("DEBUG : Init complete.");	//debug	
		
		//Waila tooltip provider (NYI)
        //FMLInterModComms.sendMessage("Waila", "register", "com.lulan.shincolle.waila.WailaDataProvider.callbackRegister");
	}
	
	/** post initial: mod interact */
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		//world gen跟entity spawn放在postInit, 以便能讀取到其他mod的biome
		ModWorldGen.init();
		
		//register chunk loader callback
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoaderHandler());
		
		//add forestry bees
		if (Loader.isModLoaded(Reference.MOD_ID_Forestry) && ConfigHandler.enableForestry)
		{
			ShinBees.init();
			
//			Iterator iter = EntityList.classToStringMapping.entrySet().iterator();
//			Iterator iter = AlleleManager.alleleRegistry.getSpeciesRoot("rootBees").getGenomeTemplates().entrySet().iterator();
//			while (iter.hasNext())
//			{
//				Map.Entry entry = (Map.Entry)iter.next();
//			    Object key = entry.getKey();
//			    Object val = entry.getValue();
//			    LogHelper.info("AAAAAA:  "+key+" , "+val);
//			    
//			    IAllele[] data = (IAllele[]) val;
//			    
//			    for (IAllele d : data)
//			    {
//			    	LogHelper.info("            BBBB:  "+d);
//			    }
//			}
		}
		
//		LogHelper.info("DEBUG : biome spawn: "+this.worldObj.getBiomeGenForCoords((int)this.posX, (int)this.posZ).getSpawnableList(EnumCreatureType.waterCreature).get(1));
//		for(String oreName : OreDictionary.getOreNames()) {	//list all oreDictionary  (DEBUG)
//			LogHelper.info(oreName);
//		}
		LogHelper.info("DEBUG : postInit complete.");	//debug
	}
	
	/** server starting event
	 *  
	 */
	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		LogHelper.info("DEBUG : server starting event: is MP server? "+event.getSide().isServer());	//debug
		
		//register command
		CommandHandler.init(event);
		
	}
	
	/** server about to start event 
	 *  當開啟一個存檔或者MP伺服器開啟時會丟出此事件
	 *  在此事件中將MapStorage的讀取紀錄設為false
	 *  使每次開不同存檔都會重讀該存檔的MapStorage
	 */
	@Mod.EventHandler
	public void onServerStarted(FMLServerAboutToStartEvent event) {
		LogHelper.info("DEBUG : server about to start: is MP server? "+event.getSide().isServer());	//debug
	    ServerProxy.initServerFile = false;
	    CommonProxy.isMultiplayer = event.getSide().isServer();
	}
	

}