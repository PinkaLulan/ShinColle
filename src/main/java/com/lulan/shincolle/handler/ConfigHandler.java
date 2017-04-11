package com.lulan.shincolle.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.lulan.shincolle.reference.Reference;
import com.lulan.shincolle.utility.CalcHelper;
import com.lulan.shincolle.utility.LogHelper;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;


public class ConfigHandler {
	
	public static Configuration config;		//宣告config檔實體
	
	//GENERAL
	public static boolean debugMode = false;
	public static boolean easyMode = false;
	public static boolean staticMode = false;
	public static boolean showTag = true;
	public static boolean friendlyFire = true;
	public static boolean useWakamoto = true;
	public static boolean alwaysShowTeamParticle = false;
	public static boolean polyAsMn = true;
	public static float dropGrudge = 1.0F;
	public static int closeGUIDist = 64;
	public static int bossCooldown = 4800;
	public static int teamCooldown = 6000;
	public static int kaitaiAmountSmall = 20;
	public static int kaitaiAmountLarge = 20;
	public static int baseCaressMorale = 15;
	public static int volcoreGrudgeValue = 75;
	public static int spawnBossNum = 2;
	public static int spawnMobNum = 4;
	public static int chunkloaderMode = 2;
	
	//INTER-MOD
	public static boolean enableForestry = true;
	
	//DESK
	public static int radarUpdate = 128;	//radar update interval (ticks)
	
	//SHIP SETTING
	public static Property propShip, propShipLimitBasic, propShipLimitEffect, propMobSpawn,
						   propBossSmall, propBossLarge, propMobSmall, propMobLarge, propGrudgeShip,
						   propGrudgeAction, propAmmoShip, propAtkSpd, propAtkDly, propExp,
						   propCustomSoundShip, propCustomSoundRate, propCustomSoundRate2,
						   propShipTeleport;
	//                                                    HP, ATK, DEF, SPD, MOV, HIT
	public static double[] limitShipBasic = new double[] {-1D, -1D, 75D, 4D, 0.6D, 64D};
	//                                                    CRI, DHIT, THIT, MISS, AA, ASM, DODGE
	public static double[] limitShipEffect = new double[] {-1D, -1D, -1D, -1D, -1D, -1D, 75D};
	public static double[] scaleShip = new double[] {1D, 1D, 1D, 1D, 1D, 1D};
	//													  HP, ATK, DEF, SPD, MOV, HIT
	public static double[] scaleBossSmall = new double[] {2000D, 120D, 80D, 1D, 0.6D, 20D};
	public static double[] scaleBossLarge = new double[] {5000D, 200D, 92D, 2D, 0.36D, 24D};
	//	  												HP, ATK, DEF, SPD, MOV, HIT
	public static double[] scaleMobSmall = new double[] {200D, 36D, 20D, 0.8D, 0.3D, 12D};
	public static double[] scaleMobLarge = new double[] {600D, 70D, 40D, 0.9D, 0.3D, 16D};
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
	//teleport AI                                 cooldown ticks, distance^2
	public static int[] shipTeleport = new int[] {200,            256};
	
	public static int dmgSvS = 100;		//ship vs ship damage modifier, 20 = dmg * 20%
	public static int dmgSummon = 100;	//summons damage modifier, 20 = dmg * 20%
	public static int expMod = 20;		//ship exp per level, ex: 20 => lv 15 exp req = 15*20+20
	
	public static boolean timeKeeping = true;
	public static boolean canFlare = true;
	public static boolean canSearchlight = true;
	public static boolean checkRing = true;
	public static float volumeTimekeep = 1.0F;
	public static float volumeShip = 1.0F;
	public static float volumeFire = 0.7F;
	
	//custom sound                                    id, idle, hit, hurt, dead, love, kb, item, feed, time
	public static int[] customSoundRate = new int[] {};
	public static int[] customSoundRate2 = new int[] {56, 50,   50,  50,   100,  0,    0,  50,   0,    0,
													  54, 25,   0,   25,   0,    50,   0,  50,   0,    0};
	

	//WORLD GEN
	public static int polyOreBaseRate = 7;
	public static int polyGravelBaseRate = 4;
	public static boolean[] polyGravelBaseBlock = new boolean[] {true, true, false, false};	//stone gravel sand dirt
	public static Property propPolyGravel;
	
	
	//讀取設定檔參數
	private static void loadConfiguration()
	{
		
		//是否顯示custom name tag
		alwaysShowTeamParticle = config.getBoolean("Always_Show_Team", "general", false, "Always show team circle animation");
		
		//boss生成cd設定 (ticks)
		bossCooldown = config.getInt("Boss_Cooldown", "general", 4800, 20, 1728000, "Boss spawn cooldown");
		
		//玩家離開多遠時關閉GUI
		closeGUIDist = config.getInt("Close_GUI_Distance", "general", 64, 2, 64, "Close inventory GUI if ship away from player X blocks");
		
		//chuknk loader使用模式
		chunkloaderMode = config.getInt("ChunkLoader_Mode", "general", 2, 0, 2, "Chunk loader mode of ship: 0: disable, 1: only 1 chunk each ship, 2: 3x3 chunks each ship");
				
		//是否開啟debug mode
		debugMode = config.getBoolean("Debug_Mode", "general", false, "Enable debug message (SPAM WARNING)");
		
		//grudge掉落率設定
		dropGrudge = config.getFloat("DropRate_Grudge", "general", 1F, 0F, 64F, "Grudge drop rate (ex: 0.5 = 50% drop 1 grudge, 5.5 = drop 5 grudge + 50% drop 1 grudge)");
		
		//是否開啟簡單模式
		easyMode = config.getBoolean("Easy_Mode", "general", false, "Easy mode: decrease Large Construction resources requirement, increase ammo / grudge gained from items");
		
		//是否開啟誤傷模式
		friendlyFire = config.getBoolean("Friendly_Fire", "general", true, "false: disable damage done by player (except owner)");
			
		//解體獲得材料量設定, mob drop類ship限定
		kaitaiAmountSmall = config.getInt("Recycle_Small", "general", 20, 0, 1000, "Recycle amount by Dismantle Hammer (SMALL Mob Drop Ship)");
		kaitaiAmountLarge = config.getInt("Recycle_Large", "general", 20, 0, 1000, "Recycle amount by Dismantle Hammer (LARGE Mob Drop Ship)");
				
		//是否把多金屬當成錳礦
		polyAsMn = config.getBoolean("Polymetal_as_Mn", "general", true, "true: Polymetallic Nodules = Manganese Dust, Polymetallic Ore = Manganese Ore");
		
		//desk雷達更新間隔
		radarUpdate = config.getInt("Radar_Update", "desk", 128, 20, 6000, "Radar update interval (ticks) in Admiral's Desk GUI");
		
		//是否把large shipyard設為static entity (只畫一次, 但是此功能跟NEI相衝)
		staticMode = config.getBoolean("Static_Mode", "general", false, "Render LargeShipyard as static or normal entity (for NotEnoughItem: 1283: Stack overflow bug)");
		
		//是否顯示custom name tag
		showTag = config.getBoolean("Show_Name_Tag", "general", true, "Show custom name tag?");
		
		//team改動cd (ticks)
		teamCooldown = config.getInt("Battle_Cooldown", "general", 6000, 20, 1728000, "Create/Disband Team Cooldown");

		//是否開啟若本語音
		useWakamoto = config.getBoolean("Sound_Wakamoto", "general", true, "enable Wakamoto sound for particular ship");
		
		//深海火山怨念轉換值
		volcoreGrudgeValue = config.getInt("VolcanoCore_Grudge", "general", 75, 1, 1000, "fuel value per grudge item in Abyssal Volcano Core");
		
		//野生艦隊生成數量
		spawnBossNum = config.getInt("Spawn_Boss_Number", "general", 2, 1, 10, "large hostile ship (boss) number per spawn");
		spawnMobNum = config.getInt("Spawn_Mob_Number", "general", 4, 1, 10, "small hostile ship number per spawn");
			
		
		//是否開啟林業支援
		enableForestry = config.getBoolean("Enable_Forestry", "inter-mod", true, "Enable Forestry module if Forestry exists: add bees and comb.");
				
				
		//讀取 ship setting設定
		canFlare = config.getBoolean("Can_Flare", "ship setting", true, "Can ship use Flare");
		canSearchlight = config.getBoolean("Can_Searchlight", "ship setting", true, "Can ship use Searchlight");
		checkRing = config.getBoolean("Check_Ring", "ship setting", true, "Check wedding ring when spawning NON-BOSS ship mob");
		timeKeeping = config.getBoolean("Timekeeping", "ship setting", true, "Play timekeeping sound every 1000 ticks (1 minecraft hour)");
		volumeTimekeep = config.getFloat("Timekeeping_Volume", "ship setting", 1.0F, 0F, 10F, "Timekeeping sound volume");
		volumeShip = config.getFloat("Ship_Volume", "ship setting", 1.0F, 0F, 10F, "Other sound volume");
		volumeFire = config.getFloat("Attack_Volume", "ship setting", 0.7F, 0F, 10F, "Attack sound volume");
		baseCaressMorale = config.getInt("BaseMorale_Caress", "ship setting", 15, 1, 1000, "base morale value per CaressTick (4 ticks)");
		
		propShip = config.get("ship setting", "ship_scale", scaleShip, "Ship attributes SCALE: HP, firepower, armor, attack speed, move speed, range");
		propShipLimitBasic = config.get("ship setting", "ship_limit_basic", limitShipBasic, "Ship basic attributes LIMIT (-1 = no limit): HP, firepower, armor%, attack speed, move speed, range(blocks)");
		propShipLimitEffect = config.get("ship setting", "ship_limit_effect", limitShipEffect, "Ship effect attributes LIMIT (-1 = no limit, 12 = limit 12%): critical%, double hit%, triple hit%, miss reduction%, anti-air, anti-ss, dodge%");
		propBossSmall = config.get("ship setting", "SmallBoss_scale", scaleBossSmall, "Small Boss, values: HP, firepower, armor, attack speed, move speed, range");
		propBossLarge = config.get("ship setting", "LargeBoss_scale", scaleBossLarge, "Large Boss, values: HP, firepower, armor, attack speed, move speed, range");
		propMobSmall = config.get("ship setting", "SmallMob_scale", scaleMobSmall, "Small mob ship like DD and SS, values: HP, firepower, armor, attack speed, move speed, range");
		propMobLarge = config.get("ship setting", "LargeMob_scale", scaleMobLarge, "Large mob ship like CL and CA, values: HP, firepower, armor, attack speed, move speed, range");
		propAmmoShip = config.get("ship setting", "Ammo_Ship", consumeAmmoShip, "Ammo consumption for ship type: DD CL CA CAV CLT CVL CV BB BBV SS AP (MAX = 45)");
		propGrudgeShip = config.get("ship setting", "Grudge_Ship", consumeGrudgeShip, "Grudge consumption for ship type: DD CL CA CAV CLT CVL CV BB BBV SS AP (MAX = 120)");
		propGrudgeAction = config.get("ship setting", "Grudge_Action", consumeGrudgeAction, "Grudge consumption for ship action: Light attack, Heavy attack, Light aircraft, Heavy aircraft, Moving per block");
		propAtkSpd = config.get("ship setting", "Attack_Base_Speed", baseAttackSpeed, "Base attack speed for: Melee, Light attack, Heavy attack, Carrier attack, Airplane attack, ex: base speed 160, fixed delay 30 means (160 / ship attack speed +30) ticks per attack");
		propAtkDly = config.get("ship setting", "Attack_Fixed_Delay", fixedAttackDelay, "Fixed attack delay for: Melee, Light attack, Heavy attack, Carrier attack, Airplane attack, ex: base speed 160, fixed delay 30 means (160 / ship attack speed +30) ticks per attack");
		propExp = config.get("ship setting", "Exp_Gain", expGain, "Exp gain for: Melee, Light Attack, Heavy Attack, Light Aircraft, Heavy Aircraft, Move per Block(AP only), Other Action(AP only)");
		propMobSpawn = config.get("ship setting", "Mob_Spawn", mobSpawn, "Mob ship spawn: Max number in the world, Spawn prob (roll once per player every 128 ticks), #groups each spawn, #min each group, #max each group");
		propCustomSoundRate = config.get("ship setting", "Custom_Sound_Rate", customSoundRate, "Probability of custom sound, 0 = no custom sound, 100 = always custom sound. Format: ship id A, idle, attack, hurt, dead, marry, knockback, item get, feed, timekeep, ship id B, idle, ...(loop), the ship id is same with meta value of ship spawn egg.");
		propCustomSoundRate2 = config.get("ship setting", "Custom_Sound_Rate2", customSoundRate2, "Custom sound by mod author, the priority is customSoundRate > customSoundRate2, you can set this sound rate to 0 (except ship id!!) or add your setting in 'customSoundRate' to disable this setting.");
		propShipTeleport = config.get("ship setting", "ship_teleport", shipTeleport, "Ship teleport when following and guarding: cooldown (ticks), distance (blocks^2)");
		
		//ship vs ship damage modifier
		dmgSvS = config.getInt("SVS_DmgTaken", "ship setting", 100, 0, 10000, "Ship vs Ship damage modifier, 20 = damage * 20% ");
		dmgSummon = config.getInt("Summon_DmgTaken", "ship setting", 100, 0, 10000, "summons (mounts, aircraft ...etc) damage modifier, 20 = damage * 20% ");
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
		shipTeleport = getIntArrayFromConfig(shipTeleport, propShipTeleport);
		setCustomSoundValue();
		
		//若設定檔有更新過, 則儲存
		if (config.hasChanged())
		{
			config.save();
		}
	}
	
	//check custom sound
	private static void setCustomSoundValue()
	{
		//custom sound by mod user
		int[] geti = propCustomSoundRate.getIntList();
		
		if (geti != null && geti.length % 10 == 0)
		{
			customSoundRate = geti;
		}
		else
		{
			geti = new int[] {};
			propCustomSoundRate.set(geti);
			customSoundRate = geti;
		}
		
		//custom sound by mod author
		geti = propCustomSoundRate2.getIntList();
		
		if (geti != null && geti.length % 10 == 0)
		{
			//is not default value, add default to existed value
			/** TODO the setting for rv.26 */
			if (geti.length != 20 || (geti.length >= 20 && geti[10] != 54))
			{
				propCustomSoundRate2.set(customSoundRate2);
			}
		}
		else
		{
			propCustomSoundRate2.set(customSoundRate2);
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
	
	//設定檔處理 初始化動作
	public static void init(File configFile)
	{		
		//如果設定檔實體還未建立 則建立之
		if (config == null)
		{
			config = new Configuration(configFile);	//建立config檔實體
			loadConfiguration();
		}
	}
	
	//若版本更新後 設定檔需要更新 則在此區塊增加更新方法
	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		//若設定檔的mod id跟目前mod id不同時 則進行更新
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
		{
			loadConfiguration();
		}
	}

}
