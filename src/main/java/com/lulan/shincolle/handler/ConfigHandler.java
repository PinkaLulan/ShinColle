package com.lulan.shincolle.handler;

import java.io.File;

import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


public class ConfigHandler {
	
	public static Configuration config;	//宣告config檔實體
	
	//設定檔變數
	//GENERAL
	public static boolean debugMode = false;
	public static boolean easyMode = false;
	public static boolean staticMode = false;
	public static boolean showTag = true;
	public static boolean friendlyFire = true;
	public static boolean useWakamoto = true;
	
	//SHIP SETTING
	//scale: HP, ATK, DEF, SPD, MOV, HIT
	public static Property propShip, propBossSMKZ, propBossNGT, propMobU511;
	public static double[] scaleShip = new double[] {1D, 1D, 1D, 1D, 1D, 1D};
	public static double[] scaleBossSMKZ = new double[] {900D, 50D, 80D, 1D, 0.6D, 16D};
	public static double[] scaleBossNGT = new double[] {2400D, 200D, 92D, 2D, 0.4D, 24D};
	public static double[] scaleMobU511 = new double[] {100D, 20D, 30D, 1D, 0.4D, 12D, 200D};
	
	public static boolean timeKeeping = true;
	public static float timeKeepingVolume = 1.0F;
	public static float shipVolume = 1.0F;
	public static float fireVolume = 0.7F;
	
	//WORLD GEN
	public static int polyOreBaseRate = 7;
	public static int polyGravelBaseRate = 4;

	
	//讀取設定檔參數
	private static void loadConfiguration() {
		//是否開啟debug mode (spam debug/info message)
		debugMode = config.getBoolean("Debug_Mode", "general", false, "Enable debug message (SPAM WARNING)");
		
		//是否開啟簡單模式 (spam debug/info message)
		easyMode = config.getBoolean("Easy_Mode", "general", false, "Easy mode: decrease Large Construction requirement, ammo / grudge consumption of seikan activity");
		
		//是否把large shipyard設為static entity (只畫一次, 但是此功能跟NEI相衝)
		staticMode = config.getBoolean("Static_Mode", "general", false, "Render LargeShipyard as static or normal entity (for NotEnoughItem: 1283: Stack overflow bug)");
		
		//是否顯示custom name tag
		showTag = config.getBoolean("Show_Name_Tag", "general", true, "Show custom name tag?");
		
		//是否開啟簡單模式 (spam debug/info message)
		friendlyFire = config.getBoolean("Friendly_Fire", "general", true, "false: disable damage done by player (except owner)");
		
		//是否開啟簡單模式 (spam debug/info message)
		useWakamoto = config.getBoolean("Sound_Wakamoto", "general", true, "enable Wakamoto sound for particular ship");
				
		//讀取 ship setting設定
		timeKeeping = config.getBoolean("Timekeeping", "ship setting", true, "Play timekeeping sound every 1000 ticks (1 minecraft hour)");
		timeKeepingVolume = config.getFloat("Timekeeping_Volume", "ship setting", 1.0F, 0F, 10F, "Timekeeping sound volume");
		shipVolume = config.getFloat("Ship_Volume", "ship setting", 1.0F, 0F, 10F, "Other sound volume");
		fireVolume = config.getFloat("Attack_Volume", "ship setting", 0.7F, 0F, 10F, "Attack sound volume");
		
		propShip = config.get("ship setting", "ship_scale", scaleShip, "Ship attributes SCALE: HP, firepower, armor, attack speed, move speed, range");
		propBossSMKZ = config.get("ship setting", "ShimakazeBoss_scale", scaleBossSMKZ, "Boss:Shimakaze Attrs: HP, firepower, armor, attack speed, move speed, range");
		propBossNGT = config.get("ship setting", "NagatoBoss_scale", scaleBossNGT, "Boss:Nagato Attrs: HP, firepower, armor, attack speed, move speed, range");
		propMobU511 = config.get("ship setting", "MobU511_scale", scaleMobU511, "Mob:U511 Attrs: HP, firepower, armor, attack speed, move speed, range, spawnPerSquid");

		//WORLD GEN
		polyOreBaseRate = config.getInt("Polymetal_Ore", "world gen", 7, 0, 100, "Polymetallic Ore clusters in one chunk");
		polyGravelBaseRate = config.getInt("Polymetal_Gravel", "world gen", 4, 0, 100, "Polymetallic Gravel clusters in one chunk");
		
		//若設定檔有更新過 則儲存
		if(config.hasChanged()) {
			config.save();
		}
		
		//設定新值
		scaleShip = propShip.getDoubleList();
		scaleBossSMKZ = propBossSMKZ.getDoubleList();
		scaleBossNGT = propBossNGT.getDoubleList();
		scaleMobU511 = propMobU511.getDoubleList();
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
