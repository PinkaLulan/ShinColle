package com.lulan.shincolle;

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
import com.lulan.shincolle.init.ModSounds;
import com.lulan.shincolle.init.ModWorldGen;
import com.lulan.shincolle.proxy.CommonProxy;
import com.lulan.shincolle.proxy.IProxy;
import com.lulan.shincolle.proxy.ServerProxy;
import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;


@Mod(modid = Reference.MOD_ID,
	 name = Reference.MOD_NAME,
	 version = Reference.MOD_VERSION,
	 dependencies="required-after:Forge@[12.18.3.2185,)",
	 guiFactory = "com.lulan.shincolle.config.ConfigGuiFactory")
public class ShinColle
{
	
	//mod instance
	@Mod.Instance(Reference.MOD_ID)
	public static ShinColle instance;
	
	//proxy for client/server event
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static IProxy proxy;
	
	
	/** pre-init: config/block/tile/item/entity init, render/packet/capability register
	 */
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Exception
	{		
		//config inti
		ConfigHandler.init(event);	//load config file
		
		ModItems.init();

		ModBlocks.init();

		ModEntity.init();
		
		ModSounds.init();
		
		//render & model register
		proxy.registerRender();
		
		//Packet channel register (simple network)
		proxy.registerChannel();
		
		//capability register
		proxy.registerCapability();
		
		LogHelper.info("INFO: Pre-Init completed.");
	}
	
	/** initial: recipe/gui/worldgen init, event handler regist, create data handler, 
	 *           request mod interact ,oreDictionary registr
	 */
	@Mod.EventHandler
	public void Init(FMLInitializationEvent event)
	{
		//GUI register
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		
		ModOres.oreDictRegister();
		
		ModEvents.init();
		
		ModRecipes.init();
		
		LogHelper.info("INFO: Init completed.");
		
		//Waila tooltip provider TODO
        //FMLInterModComms.sendMessage("Waila", "register", "com.lulan.shincolle.waila.WailaDataProvider.callbackRegister");
	}
	
	/** post-init: mod interact */
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		//world gen跟entity spawn放在postInit, 以便能讀取到其他mod的biome
		ModWorldGen.init();
		
		//register chunk loader callback
		ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoaderHandler());
		
		//check config changed
        ConfigHandler.checkChange(ConfigHandler.config);
		
		//add forestry bees
        CommonProxy.checkModLoaded();
		
//		//DEBUG
//        Map<String, ModContainer> modlist = Loader.instance().getIndexedModList();
//        modlist.forEach((name, v) ->
//        {
//        	LogHelper.info("AAAAAAAA "+name);
//        });
//		LogHelper.info("DEBUG : biome spawn: "+this.worldObj.getBiomeGenForCoords((int)this.posX, (int)this.posZ).getSpawnableList(EnumCreatureType.waterCreature).get(1));
//		for (String oreName : OreDictionary.getOreNames())
//		{	//list all oreDictionary  (DEBUG)
//			LogHelper.info(oreName);
//		}
		
		LogHelper.info("INFO: Post-Init completed.");
	}
	
	/** server about to start
	 *  當開啟一個存檔或者MP伺服器開啟時會丟出此事件
	 *  在此事件中將MapStorage的讀取紀錄設為false
	 *  使每次開不同存檔都會重讀該存檔的MapStorage
	 */
	@Mod.EventHandler
	public void onServerAboutToStart(FMLServerAboutToStartEvent event)
	{
		LogHelper.info("INFO: server about to start: is MP server? "+event.getSide().isServer());
		//set init flag
	    ServerProxy.initServerFile = true;
	    ServerProxy.saveServerFile = false;
	    CommonProxy.isMultiplayer = event.getSide().isServer();
	}
	
	/** server starting
	 *  command必須在此註冊 (每個地圖檔會依照權限設定, 註冊不同command)
	 */
	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		LogHelper.info("INFO: Server starting event: is MP server? "+event.getSide().isServer());
		
		//register command
		CommandHandler.init(event);
	}
	
	/** server stopping
	 *  before world unload
	 *  標記server即將關閉, server world data需要標記存回disk
	 */
	@Mod.EventHandler
	public void onServerStopping(FMLServerStoppingEvent event)
	{
		LogHelper.info("INFO: Server stopping event");
		//set init flag
	    ServerProxy.initServerFile = false;
	    ServerProxy.saveServerFile = true;
	}
	

}
