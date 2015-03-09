package com.lulan.shincolle.handler;

import java.io.File;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;


public class ConfigHandler {
	
	public static Configuration config;	//宣告config檔實體
	
	//設定檔變數
	//GENERAL
	public static boolean debugMode = false;
	public static boolean easyMode = false;
	public static boolean staticMode = true;
	
	//SHIP SETTING
	public static float hpRatio = 1.0f;
	public static float atkRatio = 1.0f;
	public static float defRatio = 1.0f;
	public static float spdRatio = 1.0f;
	public static float movRatio = 1.0f;
	public static float hitRatio = 1.0f;

	
	//讀取設定檔參數
	private static void loadConfiguration() {
		//是否開啟debug mode (spam debug/info message)
		debugMode = config.getBoolean("Debug_Mode", "general", false, "Enable debug message (SPAM WARNING)");
		
		//是否開啟簡單模式 (spam debug/info message)
		easyMode = config.getBoolean("Easy_Mode", "general", false, "Easy mode: decrease Large Construction requirement, ammo / grudge consumption of seikan activity");
		
		//是否把large shipyard設為static entity (只畫一次, 但是此功能跟NEI相衝)
		staticMode = config.getBoolean("Static_Mode", "general", true, "Render LargeShipyard as static or normal entity (for NotEnoughItem: 1283: Stack overflow bug)");
		
		//讀取 ship setting設定
		//hp ratio
		hpRatio = config.getFloat("Scale_HP", "ship setting", 1f, 0.01f, 100f, "Ship HP scale");
		atkRatio = config.getFloat("Scale_ATK", "ship setting", 1f, 0.01f, 100f, "Ship FIREPOWER scale");
		defRatio = config.getFloat("Scale_DEF", "ship setting", 1f, 0.01f, 100f, "Ship ARMOR scale");
		spdRatio = config.getFloat("Scale_SPD", "ship setting", 1f, 0.01f, 100f, "Ship ATTACK SPEED scale");
		movRatio = config.getFloat("Scale_MOV", "ship setting", 1f, 0.01f, 100f, "Ship MOVE SPEED scale");
		hitRatio = config.getFloat("Scale_HIT", "ship setting", 1f, 0.01f, 100f, "Ship RANGE scale");
		
		//若設定檔有更新過 則儲存
		if(config.hasChanged()) {
			config.save();
		}		
	}
	
	//設定檔處理 初始化動作
	public static void init(File configFile) {		
		//如果設定檔實體還未建立 則建立之
		if(config == null) {
			config = new Configuration(configFile);	//建立config檔實體
			loadConfiguration();
		}		
	}
	
	//若版本更新後 設定檔需要更新 則在此區塊增加更新方法
	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		//若設定檔的mod id跟目前mod id不同時 則進行更新
		if(event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}

}
