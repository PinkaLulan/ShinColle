package com.lulan.shincolle.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.lulan.shincolle.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


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
	public static boolean alwaysShowTeam = false;
	public static int bossCooldown = 4800;
	public static float dropGrudge = 1.0F;
	public static int closeGUIDist = 16;
	
	//SHIP SETTING
	//scale: HP, ATK, DEF, SPD, MOV, HIT
	public static Property propShip, propShipLimit, propBossSMKZ, propBossNGT, propMobU511;
	public static double[] limitShip = new double[] {-1D, -1D, 75D, 4D, 0.8D, -1D};
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
	public static boolean[] polyGravelBaseBlock = new boolean[] {true, true, false, false};	//stone gravel sand dirt
	public static Property propPolyGravel;
	
	
	//讀取設定檔參數
	private static void loadConfiguration() {
		
		//是否顯示custom name tag
		alwaysShowTeam = config.getBoolean("Always_Show_Team", "general", false, "Always show team circles");
		
		//boss生成cd設定 (ticks)
		bossCooldown = config.getInt("Boss_Cooldown", "general", 4800, 20, 1728000, "Boss spawn cooldown");
		
		//玩家離開多遠時關閉GUI
		closeGUIDist = config.getInt("Close_GUI_Distance", "general", 16, 2, 64, "Close inventory GUI if ship away from player X blocks");
		
		//是否開啟debug mode (spam debug/info message)
		debugMode = config.getBoolean("Debug_Mode", "general", false, "Enable debug message (SPAM WARNING)");
		
		//grudge掉落率設定
		dropGrudge = config.getFloat("DropRate_Grudge", "general", 1F, 0F, 64F, "Grudge drop rate (ex: 0.5 = 50% drop 1 grudge, 5.5 = drop 5 grudge + 50% drop 1 grudge)");
		
		//是否開啟簡單模式 (spam debug/info message)
		easyMode = config.getBoolean("Easy_Mode", "general", false, "Easy mode: decrease Large Construction requirement, ammo / grudge consumption of seikan activity");
		
		//是否開啟簡單模式 (spam debug/info message)
		friendlyFire = config.getBoolean("Friendly_Fire", "general", true, "false: disable damage done by player (except owner)");
				
		//是否把large shipyard設為static entity (只畫一次, 但是此功能跟NEI相衝)
		staticMode = config.getBoolean("Static_Mode", "general", false, "Render LargeShipyard as static or normal entity (for NotEnoughItem: 1283: Stack overflow bug)");
		
		//是否顯示custom name tag
		showTag = config.getBoolean("Show_Name_Tag", "general", true, "Show custom name tag?");
		
		//是否開啟簡單模式 (spam debug/info message)
		useWakamoto = config.getBoolean("Sound_Wakamoto", "general", true, "enable Wakamoto sound for particular ship");
		
		//讀取 ship setting設定
		timeKeeping = config.getBoolean("Timekeeping", "ship setting", true, "Play timekeeping sound every 1000 ticks (1 minecraft hour)");
		timeKeepingVolume = config.getFloat("Timekeeping_Volume", "ship setting", 1.0F, 0F, 10F, "Timekeeping sound volume");
		shipVolume = config.getFloat("Ship_Volume", "ship setting", 1.0F, 0F, 10F, "Other sound volume");
		fireVolume = config.getFloat("Attack_Volume", "ship setting", 0.7F, 0F, 10F, "Attack sound volume");
		
		propShip = config.get("ship setting", "ship_scale", scaleShip, "Ship attributes SCALE: HP, firepower, armor, attack speed, move speed, range");
		propShipLimit = config.get("ship setting", "ship_limit", limitShip, "Ship attributes LIMIT (-1 = no limit): HP, firepower, armor(%), attack speed(per second), move speed, range(blocks)");
		propBossSMKZ = config.get("ship setting", "ShimakazeBoss_scale", scaleBossSMKZ, "Boss:Shimakaze Attrs: HP, firepower, armor, attack speed, move speed, range");
		propBossNGT = config.get("ship setting", "NagatoBoss_scale", scaleBossNGT, "Boss:Nagato Attrs: HP, firepower, armor, attack speed, move speed, range");
		propMobU511 = config.get("ship setting", "MobU511_scale", scaleMobU511, "Mob:U511/Ro500 Attrs: HP, firepower, armor, attack speed, move speed, range, spawnPerSquid");

		//WORLD GEN
		polyOreBaseRate = config.getInt("Polymetal_Ore", "world gen", 7, 0, 100, "Polymetallic Ore clusters in one chunk");
		polyGravelBaseRate = config.getInt("Polymetal_Gravel", "world gen", 4, 0, 100, "Polymetallic Gravel clusters in one chunk");
		propPolyGravel = config.get("world gen", "Polymetal_Gravel_Replace", polyGravelBaseBlock, "PolyGravel replaced block: stone, gravel, sand, dirt", true, 4);
		
		//若設定檔有更新過 則儲存
		if(config.hasChanged()) {
			config.save();
		}
		
		//設定新值
		limitShip = propShipLimit.getDoubleList();
		scaleShip = propShip.getDoubleList();
		scaleBossSMKZ = propBossSMKZ.getDoubleList();
		scaleBossNGT = propBossNGT.getDoubleList();
		scaleMobU511 = propMobU511.getDoubleList();
		polyGravelBaseBlock = propPolyGravel.getBooleanList();
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
