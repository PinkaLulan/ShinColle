package com.lulan.shincolle.handler;

import java.io.File;

import com.lulan.shincolle.config.ConfigLoot;
import com.lulan.shincolle.config.ConfigSound;
import com.lulan.shincolle.reference.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ConfigHandler
{
	
	public static Configuration config;		//main config
	public static ConfigSound configSound;	//sound config
	public static ConfigLoot configLoot;	//loot config
	
	//GENERAL
	public static boolean debugMode = false;
	public static boolean easyMode = false;
	public static boolean showTag = true;
	public static boolean friendlyFire = true;
	public static boolean useWakamoto = true;
	public static boolean alwaysShowTeamParticle = false;
	public static boolean polyAsMn = false;
	public static float dropGrudge = 1.0F;
	public static int closeGUIDist = 64;
	public static int bossCooldown = 4800;
	public static int teamCooldown = 6000;
	public static int despawnBoss = 12000;
	public static int despawnMinion = 600;
	public static int kaitaiAmountSmall = 20;
	public static int kaitaiAmountLarge = 20;
	public static int baseCaressMorale = 15;
	public static int spawnBossNum = 2;
	public static int spawnMobNum = 4;
	public static int shipNumPerPage = 5;
	public static int chunkloaderMode = 2;
	public static int deathTick = 400;
	
	//INTER-MOD
	public static boolean enableForestry = true;
	
	//DESK
	public static int radarUpdate = 128;	//radar update interval (ticks)
	
	//array data
	public static Property propShip, propShipLimitBasic, propShipLimitEffect, propMobSpawn,
						   propBossSmall, propBossLarge, propMobSmall, propMobLarge, propGrudgeShip,
						   propGrudgeAction, propAmmoShip, propAtkSpd, propAtkDly, propExp,
						   propShipyardSmall, propShipyardLarge, propVolCore, propRingAbility,
						   propPolyGravel;
	
	//SHIP SETTING
	//                                                    HP, ATK, DEF, SPD, MOV, HIT
	public static double[] limitShipBasic = new double[] {-1D, -1D, 75D, 4D, 0.6D, 64D};
	//                                                    CRI, DHIT, THIT, MISS, AA, ASM, DODGE, XP, GRUDGE, AMMO, HPRES
	public static double[] limitShipEffect = new double[] {-1D, -1D, -1D, -1D, -1D, -1D, 75D, -1D, -1D, -1D, -1D};
	public static double[] scaleShip = new double[] {1D, 1D, 1D, 1D, 1D, 1D};
	//													  HP, ATK, DEF, SPD, MOV, HIT
	public static double[] scaleBossSmall = new double[] {1600D, 120D, 50D, 1.6D, 0.38D, 18D};
	public static double[] scaleBossLarge = new double[] {3200D, 240D, 75D, 2D, 0.35D, 22D};
	//	  												HP, ATK, DEF, SPD, MOV, HIT
	public static double[] scaleMobSmall = new double[] {250D, 25D, 15D, 0.7D, 0.45D, 12D};
	public static double[] scaleMobLarge = new double[] {500D, 50D, 30D, 0.9D, 0.4D, 15D};
	//ammo consumption:                              DD CL CA CAV CLT CVL CV BB BBV SS AP 
	public static int[] consumeAmmoShip = new int[] {1, 2, 2, 2,  2,  3,  3, 4, 4,  1, 1};
	//grudge consumption:                              DD CL CA CAV CLT CVL CV BB BBV SS AP 
	public static int[] consumeGrudgeShip = new int[] {5, 7, 8, 9,  8,  11, 12,15,14, 4, 3};
	//grudge consumption:                                LAtk, HAtk, LAir, HAir, moving
	public static int[] consumeGrudgeAction = new int[] {4,    8,    6,    12,   3};
	//attack speed                                    melee, Latk, Hatk, CV,  Air
	public static int[] baseAttackSpeed = new int[] { 80,    80,   120,  100, 100};
	public static int[] fixedAttackDelay = new int[] {0,     20,   50,   35,  35};
	//exp gain                               melee, LAtk, HAtk, LAir, HAir, move/b, pick
	public static int[] expGain = new int[] {2,     4,    12,   8,    24,   1,      2};
	//mob spawn                               Max, Prob, GroupNum, MinPS, MaxPS
	public static int[] mobSpawn = new int[] {50,  10,   1,        1,     1};
	//marriage ring ability                      breath, fly, dig, fog, immune fire
	public static int[] ringAbility = new int[] {0,      6,   30,  20,  12};
	
	public static int dmgSvS = 100;		//ship vs ship damage modifier, 20 = dmg * 20%
	public static int expMod = 20;		//ship exp per level, ex: 20 => lv 15 exp req = 15*20+20
	public static int modernLimit = 3;	//ship attrs upgrade level limit
	
	public static boolean timeKeeping = true;
	public static boolean canFlare = true;
	public static boolean canSearchlight = true;
	public static boolean checkRing = true;
	public static float volumeTimekeep = 1.0F;
	public static float volumeShip = 1.0F;
	public static float volumeFire = 0.7F;
	
	//shipyard setting                                   max storage, build speed, fuel magn
	public static double[] shipyardSmall = new double[] {460800D,     48D,         1D};
	public static double[] shipyardLarge = new double[] {1382400D,    48D,         1D};
	public static double[] volCore = new double[] {      9600D,       16D,         240D};

	//WORLD GEN
	public static int polyOreBaseRate = 7;
	public static int polyGravelBaseRate = 4;
	public static boolean[] polyGravelBaseBlock = new boolean[] {true, true, false, false};	//stone gravel sand dirt
	
	
	//config for BOTH SIDE
	private static void loadConfiguration()
	{
		//是否顯示custom name tag
		alwaysShowTeamParticle = config.getBoolean("AlwaysShow_TeamCircle", "general", false, "Always show team circle indicator particle");
		
		//boss生成cd設定 (ticks)
		bossCooldown = config.getInt("Cooldown_Boss", "general", 4800, 20, 1728000, "Boss spawn cooldown");
		
		//玩家離開多遠時關閉GUI
		closeGUIDist = config.getInt("Close_GUI_Distance", "general", 64, 2, 64, "Close inventory GUI if ship away from player X blocks");
		
		//chuknk loader使用模式
		chunkloaderMode = config.getInt("Mode_ChunkLoader", "general", 2, 0, 2, "Chunk loader mode: 0: disable, 1: only 1 chunk each ship, 2: 3x3 chunks each ship");
		
		//ship death動畫時間長度
		deathTick = config.getInt("Death_Time", "general", 400, 0, 3600, "Ship death animation time");
				
		//是否開啟debug mode
		debugMode = config.getBoolean("Mode_Debug", "general", false, "Enable debug message (SPAM WARNING)");
		
		//boss刪除時間 (ticks)
		despawnBoss = config.getInt("Despawn_Boss", "general", 12000, -1, 1728000, "Boss ship despawn time on chunk unloading, -1 = do NOT despawn");
		
		//雜魚刪除時間 (ticks)
		despawnMinion = config.getInt("Despawn_Minion", "general", 600, -1, 1728000, "Nonboss ship despawn time on chunk unloading, -1 = do NOT despawn");
				
		//grudge掉落率設定
		dropGrudge = config.getFloat("DropRate_Grudge", "general", 1F, 0F, 64F, "Grudge drop rate (ex: 0.5 = 50% drop 1 grudge, 5.5 = drop 5 grudge + 50% drop 1 grudge)");
		
		//是否開啟簡單模式
		easyMode = config.getBoolean("Mode_Easy", "general", false, "Easy mode: decrease Large Construction resources requirement, increase ammo / grudge gained from items");
		
		//是否開啟誤傷模式
		friendlyFire = config.getBoolean("Friendly_Fire", "general", true, "false: disable damage done by player (except owner)");
			
		//解體獲得材料量設定, mob drop類ship限定
		kaitaiAmountSmall = config.getInt("Recycle_Small", "general", 20, 0, 1000, "Recycle amount by Dismantle Hammer for copmmon ship, ex: Ro500.");
		kaitaiAmountLarge = config.getInt("Recycle_Large", "general", 20, 0, 1000, "Recycle amount by Dismantle Hammer for rare ship, ex: Yamato.");
				
		//是否把多金屬當成錳礦
		polyAsMn = config.getBoolean("Polymetal_as_Mn", "general", false, "true: Polymetallic Nodules = Manganese Dust, Polymetallic Ore = Manganese Ore");
		
		//desk雷達更新間隔
		radarUpdate = config.getInt("Radar_Update", "desk", 128, 20, 6000, "Radar update interval (ticks) in Admiral's Desk GUI");
		
		//是否顯示custom name tag
		showTag = config.getBoolean("AlwaysShow_NameTag", "general", true, "Always show custom name tag");
		
		//ship列表顯示數量
		shipNumPerPage = config.getInt("Command_ShipNum", "general", 5, 1, 5000, "#Ship per page for command: /ship list");
		
		//team改動cd (ticks)
		teamCooldown = config.getInt("Cooldown_Team", "general", 6000, 20, 1728000, "Create/Disband Team Cooldown");

		//是否開啟若本語音
		useWakamoto = config.getBoolean("Sound_Wakamoto", "general", true, "enable Wakamoto sound for mounts");
		
		//野生艦隊生成數量
		spawnBossNum = config.getInt("Spawn_Boss_Number", "general", 2, 1, 10, "large hostile ship (boss) number per spawn");
		spawnMobNum = config.getInt("Spawn_Mob_Number", "general", 4, 1, 10, "small hostile ship number per spawn");
		
		//是否開啟林業支援
		enableForestry = config.getBoolean("Mod_Forestry", "inter-mod", true, "Enable Forestry module if Forestry exists: add bees and comb.");
				
		//讀取 ship setting設定
		canFlare = config.getBoolean("Can_Flare", "ship setting", true, "Can ship spawn Flare lighting effect, CLIENT SIDE only");
		canSearchlight = config.getBoolean("Can_Searchlight", "ship setting", true, "Can ship spawn Searchlight lighting effect, CLIENT SIDE only");
		checkRing = config.getBoolean("Check_Ring", "ship setting", true, "Should check wedding ring when spawning NON-BOSS ship mob");
		timeKeeping = config.getBoolean("Can_Timekeeping", "ship setting", true, "Play timekeeping sound every 1000 ticks (1 minecraft hour)");
		volumeTimekeep = config.getFloat("Volume_Timekeeping", "ship setting", 1.0F, 0F, 10F, "Timekeeping sound volume");
		volumeShip = config.getFloat("Volume_Ship", "ship setting", 1.0F, 0F, 10F, "Other sound volume");
		volumeFire = config.getFloat("Volume_Attack", "ship setting", 0.7F, 0F, 10F, "Attack sound volume");
		baseCaressMorale = config.getInt("Caress_BaseMorale", "ship setting", 15, 1, 1000, "base morale value per CaressTick (4 ticks)");
		modernLimit = config.getInt("Attrs_Limit_Modernization", "ship setting", 3, 3, 100, "Max upgrade level by Modernization Toolkit");
		
		//array data
		propShip = config.get("ship setting", "Attrs_Scale", scaleShip, "Ship attributes SCALE: HP, firepower, armor, attack speed, move speed, range");
		propShipLimitBasic = config.get("ship setting", "Attrs_Limit_Basic", limitShipBasic, "Ship basic attributes LIMIT (-1 = no limit): HP, firepower, armor%, attack speed, move speed, range(blocks)");
		propShipLimitEffect = config.get("ship setting", "Attrs_Limit_Effect", limitShipEffect, "Ship effect attributes LIMIT (-1 = no limit, 12 = limit 12%): critical%, double hit%, triple hit%, miss reduction%, anti-air, anti-ss, dodge%, xp gain%, grudge gain%, ammo gain%, hp regen delay");
		propBossSmall = config.get("ship setting", "Attrs_Hostile_SmallBoss", scaleBossSmall, "Small boss base attribute values: HP, firepower, armor, attack speed, move speed, range");
		propBossLarge = config.get("ship setting", "Attrs_Hostile_LargeBoss", scaleBossLarge, "Large boss base attribute values: HP, firepower, armor, attack speed, move speed, range");
		propMobSmall = config.get("ship setting", "Attrs_Hostile_SmallMob", scaleMobSmall, "Small mob ship like DD and SS base attribute values: HP, firepower, armor, attack speed, move speed, range");
		propMobLarge = config.get("ship setting", "Attrs_Hostile_LargeMob", scaleMobLarge, "Large mob ship like CL and CA base attribute values: HP, firepower, armor, attack speed, move speed, range");
		propAmmoShip = config.get("ship setting", "Consume_Ammo", consumeAmmoShip, "Ammo consumption for ship type: DD CL CA CAV CLT CVL CV BB BBV SS AP (MAX = 45)");
		propGrudgeShip = config.get("ship setting", "Consume_Grudge_Idle", consumeGrudgeShip, "Grudge consumption for ship type: DD CL CA CAV CLT CVL CV BB BBV SS AP (MAX = 120)");
		propGrudgeAction = config.get("ship setting", "Consume_Grudge_Action", consumeGrudgeAction, "Grudge consumption for ship action: Light attack, Heavy attack, Light aircraft, Heavy aircraft, Moving per block");
		propAtkSpd = config.get("ship setting", "Attack_Base_Speed", baseAttackSpeed, "Base attack speed for: Melee, Light attack, Heavy attack, Carrier attack, Airplane attack, ex: base speed 160, fixed delay 30 means (160 / ship attack speed +30) ticks per attack");
		propAtkDly = config.get("ship setting", "Attack_Fixed_Delay", fixedAttackDelay, "Fixed attack delay for: Melee, Light attack, Heavy attack, Carrier attack, Airplane attack, ex: base speed 160, fixed delay 30 means (160 / ship attack speed +30) ticks per attack");
		propExp = config.get("ship setting", "Exp_Gain", expGain, "Exp gain for: Melee, Light Attack, Heavy Attack, Light Aircraft, Heavy Aircraft, Move per Block(AP only), Other Action(AP only)");
		propMobSpawn = config.get("ship setting", "Limit_MobSpawnNumber", mobSpawn, "Mob ship spawn MAX number in the world, Spawn prob (roll once per player every 128 ticks), #groups each spawn, #min each group, #max each group");
		
		propShipyardSmall = config.get("general", "Tile_SmallShipyard", shipyardSmall, "Small shipyard: max fuel storage, build speed, fuel magnification");
		propShipyardLarge = config.get("general", "Tile_LargeShipyard", shipyardLarge, "Large shipyard: max fuel storage, build speed, fuel magnification");
		propVolCore = config.get("general", "Tile_VolCore", volCore, "Volcano Core: max fuel storage, fuel consume speed, fuel value per grudge item");
		propRingAbility = config.get("general", "Ring_Ability", ringAbility, "Ring ability related married number, -1 = disable, 0~N = active or max limit number: water breath (active number), fly in water (active number), dig speed boost (max limit number), fog in liquid (max limit number), immune to fire (active number)");
		
		//ship vs ship damage modifier
		dmgSvS = config.getInt("DmgTaken_SvS", "ship setting", 100, 0, 10000, "Ship vs Ship damage modifier, 20 = damage * 20% ");
		expMod = config.getInt("EXP_Modifier", "ship setting", 20, 1, 10000, "ship experience modifier, 20 = level 150: 150*20+20 = 3020");
		
		//WORLD GEN
		polyOreBaseRate = config.getInt("Polymetal_Ore", "world gen", 7, 0, 100, "Polymetallic Ore clusters in one chunk");
		polyGravelBaseRate = config.getInt("Polymetal_Gravel", "world gen", 4, 0, 100, "Polymetallic Gravel clusters in one chunk");
		propPolyGravel = config.get("world gen", "Polymetal_Gravel_Replace", polyGravelBaseBlock, "PolyGravel replaced block: stone, gravel, sand, dirt", true, 4);
		
		//設定新值
		limitShipBasic = getDoubleArrayFromConfig(limitShipBasic, propShipLimitBasic);
		limitShipEffect = getDoubleArrayFromConfig(limitShipEffect, propShipLimitEffect);
		scaleShip = getDoubleArrayFromConfig(scaleShip, propShip);
		scaleBossSmall = getDoubleArrayFromConfig(scaleBossSmall, propBossSmall);
		scaleBossLarge = getDoubleArrayFromConfig(scaleBossLarge, propBossLarge);
		scaleMobSmall = getDoubleArrayFromConfig(scaleMobSmall, propMobSmall);
		scaleMobLarge = getDoubleArrayFromConfig(scaleMobLarge, propMobLarge);
		polyGravelBaseBlock = getBooleanArrayFromConfig(polyGravelBaseBlock, propPolyGravel);
		consumeAmmoShip = getIntArrayFromConfig(consumeAmmoShip, propAmmoShip);
		consumeGrudgeShip = getIntArrayFromConfig(consumeGrudgeShip, propGrudgeShip);
		consumeGrudgeAction = getIntArrayFromConfig(consumeGrudgeAction, propGrudgeAction);
		baseAttackSpeed = getIntArrayFromConfig(baseAttackSpeed, propAtkSpd);
		fixedAttackDelay = getIntArrayFromConfig(fixedAttackDelay, propAtkDly);
		expGain = getIntArrayFromConfig(expGain, propExp);
		mobSpawn = getIntArrayFromConfig(mobSpawn, propMobSpawn);
		shipyardSmall = getDoubleArrayFromConfig(shipyardSmall, propShipyardSmall);
		shipyardLarge = getDoubleArrayFromConfig(shipyardLarge, propShipyardLarge);
		volCore = getDoubleArrayFromConfig(volCore, propVolCore);
		ringAbility = getIntArrayFromConfig(ringAbility, propRingAbility);
		
		checkChange(config);
	}
	
	//設定檔處理 初始化動作
	public static void init(FMLPreInitializationEvent event) throws Exception
	{		
		//如果設定檔實體還未建立 則建立之
		if (config == null)
		{
			//get file
			String configRootLoc = event.getModConfigurationDirectory() + "/" + Reference.MOD_ID + "/";
			File fileMainConfig = new File(configRootLoc + Reference.MOD_ID + ".cfg");
			File fileSounds = new File(configRootLoc + "sounds.cfg");
			File fileLootTable = new File(configRootLoc + "loottable.cfg");
			
			config = new Configuration(fileMainConfig);
			configSound = new ConfigSound(fileSounds);
			configLoot = new ConfigLoot(fileLootTable);
			
			/** BOTH SIDE CONFIG */
			loadConfiguration();
			configLoot.runConfig();
			configSound.runConfig();
			
//			/** CLIENT SIDE CONFIG */ no use for now
//			if (event.getSide().isClient())
//			{
//			}
//			/** SERVER SIDE CONFIG */
//			else
//			{
//			}
//			
//			//TODO for debug
//			configSound.SOUNDRATE.forEach((k, v) ->
//			{
//				LogHelper.info("KKKKKKKKKKK "+k);
//				for (float v2 : v)
//				{
//					LogHelper.info("VVVVVVVVV "+v2);
//				}
//			});
		}
	}
	
	//若版本更新後 設定檔需要更新 則在此區塊增加更新方法
	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		//若設定檔的mod id跟目前mod id不同時 則進行更新
		if (event.getModID().equalsIgnoreCase(Reference.MOD_ID))
		{
			loadConfiguration();
		}
		
		//TODO 若設定檔版本跟目前版本不同, 則更新
		
	}
	
	//若設定檔有更新過, 則儲存
	public static void checkChange(Configuration cfg)
	{
		if (cfg != null && cfg.hasChanged())
		{
			cfg.save();
		}
	}
	
	//check get value
	public static int[] getIntArrayFromConfig(int[] defaultValue, Property target)
	{
		int size = defaultValue.length;
		int[] geti = target.getIntList();
		
		if (geti != null && geti.length == size)
		{
			return geti;
		}
		else
		{
			target.set(defaultValue);
			return defaultValue;
		}
	}
	
	//check get value
	public static double[] getDoubleArrayFromConfig(double[] defaultValue, Property target)
	{
		int size = defaultValue.length;
		double[] getd = target.getDoubleList();
		
		if (getd != null && getd.length == size)
		{
			return getd;
		}
		else
		{
			target.set(defaultValue);
			return defaultValue;
		}
	}
	
	//check get value
	public static boolean[] getBooleanArrayFromConfig(boolean[] defaultValue, Property target)
	{
		int size = defaultValue.length;
		boolean[] getd = target.getBooleanList();
		
		if(getd != null && getd.length == size)
		{
			return getd;
		}
		else
		{
			target.set(defaultValue);
			return defaultValue;
		}
	}
	
	
}
