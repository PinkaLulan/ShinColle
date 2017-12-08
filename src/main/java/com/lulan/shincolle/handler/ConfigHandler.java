package com.lulan.shincolle.handler;

import java.io.File;

import com.lulan.shincolle.config.ConfigLoot;
import com.lulan.shincolle.config.ConfigMining;
import com.lulan.shincolle.config.ConfigSound;
import com.lulan.shincolle.reference.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ConfigHandler
{
	
	//category
	public static final String CATE_GENERAL = "general";
	public static final String CATE_SHIP = "ship setting";
	public static final String CATE_WORLD = "world gen";
	public static final String CATE_INTERMOD = "inter-mod";
	public static final String CATE_BUFF = "buff";
	
	//config file
	public static Configuration config;		//main config
	public static ConfigSound configSound;	//sound config
	public static ConfigLoot configLoot;	//loot config
	public static ConfigMining configMining;//mining config
	
	//GENERAL
	public static boolean debugMode = false;
	public static boolean easyMode = false;
	public static boolean showTag = true;
	public static boolean friendlyFire = true;
	public static boolean useWakamoto = true;
	public static boolean alwaysShowTeamParticle = false;
	public static boolean polyAsMn = false;
	public static boolean vortexDepth = false;
	public static boolean mobAttackPlayer = true;
	public static float dropGrudge = 1.0F;
	public static int closeGUIDist = 64;
	public static int bossCooldown = 4800;
	public static int teamCooldown = 6000;
	public static int despawnBoss = 12000;
	public static int despawnMinion = 600;
	public static int despawnEgg = 12000;
	public static int kaitaiAmountSmall = 20;
	public static int kaitaiAmountLarge = 20;
	public static int baseCaressMorale = 20;
	public static int nameTagDist = 16;
	public static int spawnBossNum = 2;
	public static int spawnMobNum = 4;
	public static int shipNumPerPage = 5;
	public static int chunkloaderMode = 2;
	public static int deathMaxTick = 400;
	
	//INTER-MOD
	public static boolean enableIC2 = true;
	
	//BUFF DEBUFF
	public static int buffSaturation = 100;
	
	//DESK
	public static int radarUpdate = 128;	//radar update interval (ticks)
	
	//array data
	private static Property propShip, propLimitShipAttrs, propMobSpawn,
						   propBossSmall, propBossLarge, propMobSmall, propMobLarge, propGrudgeShip,
						   propGrudgeAction, propAmmoShip, propAtkSpd, propAtkDly, propExp, propExpTask,
						   propShipyardSmall, propShipyardLarge, propVolCore, propRingAbility,
						   propPolyGravel, propHeldItem, propDrumLiquid, propDrumEU, propCrane,
						   propInfLiquid, propShipTeleport, propFishing, propMining, propTask,
						   propGrudgeTask, propPosHUD;
	
	//                                                    HP, ATK_L, ATK_H, ATK_AL, ATK_AH
	public static double[] limitShipAttrs = new double[] {-1D, -1D, -1D, -1D, -1D,
	//                                                    DEF, SPD, MOV, HIT, CRI
														  0.8D, 4D, 0.6D, 64D, 0.9D,
	//                                                    DHIT, THIT, MISS, AA, ASM
														  0.9D, 0.9D, 0.9D, -1D, -1D,
	//                                                    DODGE, XP, GRUDGE, AMMO, HPRES
														  0.75D, -1D, -1D, -1D, -1D,
	//                                                    KB
														  1D};
	public static double[] scaleShip = new double[] {1D, 1D, 1D, 1D, 1D, 1D};
	//													  HP,    ATK,  DEF,   SPD,  MOV,   HIT
	public static double[] scaleBossSmall = new double[] {1600D, 120D, 0.5D,  1.6D, 0.38D, 18D};
	public static double[] scaleBossLarge = new double[] {3200D, 240D, 0.75D, 2D,   0.35D, 22D};
	//	  												  HP,    ATK,  DEF,   SPD,  MOV,   HIT
	public static double[] scaleMobSmall = new double[] {250D,   25D,  0.15D, 0.7D, 0.45D, 12D};
	public static double[] scaleMobLarge = new double[] {500D,   50D,  0.30D, 0.9D, 0.4D,  15D};
	//item scaling                                       scale, offX, offY, offZ
	public static double[] scaleHeldItem = new double[] {2.5D,    0D,   0D,   0D};
	//ammo consumption:                              DD CL CA CAV CLT CVL CV BB BBV SS AP 
	public static int[] consumeAmmoShip = new int[] {1, 2, 2, 2,  2,  3,  3, 4, 4,  1, 1};
	//grudge consumption:                              DD CL CA CAV CLT CVL CV BB BBV SS AP 
	public static int[] consumeGrudgeShip = new int[] {5, 7, 8, 9,  8,  11, 12,15,14, 4, 3};
	//grudge consumption:                                LAtk, HAtk, LAir, HAir, moving
	public static int[] consumeGrudgeAction = new int[] {4,    8,    6,    12,   3};
	//grudge consumption:                              cook fish mine craft
	public static int[] consumeGrudgeTask = new int[] {3,   30,  300, 2};
	//attack speed                                    melee, Latk, Hatk, CV,  Air
	public static int[] baseAttackSpeed = new int[] { 40,    80,   120,  100, 100};
	public static int[] fixedAttackDelay = new int[] {0,     20,   50,   35,  35};
	//exp gain                               melee, LAtk, HAtk, LAir, HAir, move/b, pick
	public static int[] expGain = new int[] {2,     4,    12,   8,    24,   1,      2};
	//exp gain by task                           cook fish mine craft
	public static int[] expGainTask = new int[] {2,   20,  10,  1};
	//fishing time                               base, random
	public static int[] tickFishing = new int[] {400,  600};
	//mining time                                base, random
	public static int[] tickMining = new int[]  {100,  200};
	//mob spawn                               Max, Prob, GroupNum, MinPS, MaxPS
	public static int[] mobSpawn = new int[] {50,  10,   1,        1,     1};
	//marriage ring ability                      breath, fly, dig, fog, immune fire
	public static int[] ringAbility = new int[] {0,      6,   30,  20,  12};
	//liquid drum setting                       base, enchant
	public static int[] drumLiquid = new int[] {40,   5};
	//EU drum setting                       base, enchant
	public static int[] drumEU = new int[] {400, 100};
	//can ship pump infinite liquid            min water depth, min lava depth
	public static int[] infLiquid = new int[] {12,              8};
	//ship teleport AI setting                    cooldown(ticks), distance(blocks^2)
	public static int[] shipTeleport = new int[] {200,             256};
	//task enable setting                               cook  fish  mine  craft
	public static boolean[] enableTask = new boolean[] {true, true, true, true};
	//HUD position                                x     y
	public static double[] posHUD = new double[] {0.5D, 0.6D};
	
	public static int dmgSvS = 100;		//ship vs ship damage modifier, 20 = dmg * 20%
	public static int expMod = 20;		//ship exp per level, ex: 20 => lv 15 exp req = 15*20+20
	public static int modernLimit = 3;	//ship attrs upgrade level limit
	public static int searchlightCD = 4;
	public static int maxLevel = 150;   //TODO not configurable now
	public static boolean timeKeeping = true;
	public static boolean canFlare = true;
	public static boolean canSearchlight = true;
	public static boolean checkRing = true;
	public static boolean canTeleport = false;
	public static float volumeTimekeep = 1.0F;
	public static float volumeShip = 1.0F;
	public static float volumeFire = 0.7F;
	
	//tile entity setting                                max storage, build speed, fuel magn
	public static double[] tileShipyardSmall = new double[] {460800D,     48D,         1D};
	public static double[] tileShipyardLarge = new double[] {1382400D,    48D,         1D};
	public static double[] tileVolCore = new double[] {      9600D,       16D,         240D};
	//crane setting                            liquid tank capa, EU capa
	public static int[] tileCrane = new int[] {2048000,         160000000};

	//WORLD GEN
	public static int polyOreBaseRate = 7;
	public static int polyGravelBaseRate = 4;
	public static boolean[] polyGravelBaseBlock = new boolean[] {true, true, false, false};	//stone gravel sand dirt
	
	
	//config for BOTH SIDE
	private static void loadConfiguration()
	{
		config.addCustomCategoryComment(CATE_GENERAL, "general setting");
		config.addCustomCategoryComment(CATE_SHIP, "ship setting");
		config.addCustomCategoryComment(CATE_WORLD, "world generate setting");
		config.addCustomCategoryComment(CATE_INTERMOD, "mod interaction setting");
		config.addCustomCategoryComment(CATE_BUFF, "potion buff and debuff setting");
		
		//是否顯示custom name tag
		alwaysShowTeamParticle = config.getBoolean("AlwaysShow_TeamCircle", CATE_GENERAL, false, "Always show team circle indicator particle");
		
		//boss生成cd設定 (ticks)
		bossCooldown = config.getInt("Cooldown_Boss", CATE_GENERAL, 4800, 20, 1728000, "Boss spawn cooldown");
		
		//玩家離開多遠時關閉GUI
		closeGUIDist = config.getInt("Close_GUI_Distance", CATE_GENERAL, 64, 2, 64, "Close inventory GUI if ship away from player X blocks");
		
		//chuknk loader使用模式
		chunkloaderMode = config.getInt("Mode_ChunkLoader", CATE_GENERAL, 2, 0, 2, "Chunk loader mode: 0: disable, 1: only 1 chunk each ship, 2: 3x3 chunks each ship");
		
		//ship death動畫時間長度
		deathMaxTick = config.getInt("Death_Time", CATE_GENERAL, 400, 0, 3600, "Ship death animation time");
				
		//是否開啟debug mode
		debugMode = config.getBoolean("Mode_Debug", CATE_GENERAL, false, "Enable debug message (SPAM WARNING)");
		
		//boss刪除時間 (ticks)
		despawnBoss = config.getInt("Despawn_Boss", CATE_GENERAL, 12000, -1, 1728000, "Despawn time of boss ship , -1 = do NOT despawn");
		
		//雜魚刪除時間 (ticks)
		despawnMinion = config.getInt("Despawn_Minion", CATE_GENERAL, 600, -1, 1728000, "Despawn time of nonboss ship, -1 = do NOT despawn");
		
		//野生生怪蛋刪除時間 (ticks)
		despawnEgg = config.getInt("Despawn_Egg", CATE_GENERAL, 12000, -1, 1728000, "Despawn time of spawn egg of ship mob, -1 = do NOT despawn");
				
		//grudge掉落率設定
		dropGrudge = config.getFloat("DropRate_Grudge", CATE_GENERAL, 1F, 0F, 64F, "Grudge drop rate (ex: 0.5 = 50% drop 1 grudge, 5.5 = drop 5 grudge + 50% drop 1 grudge)");
		
		//是否開啟簡單模式
		easyMode = config.getBoolean("Mode_Easy", CATE_GENERAL, false, "Easy mode: decrease Large Construction resources requirement, increase ammo / grudge gained from items");
		
		//是否開啟誤傷模式
		friendlyFire = config.getBoolean("Friendly_Fire", CATE_GENERAL, true, "false: disable damage done by player (except owner)");
			
		//解體獲得材料量設定, mob drop類ship限定
		kaitaiAmountSmall = config.getInt("Recycle_Small", CATE_GENERAL, 20, 0, 1000, "Recycle amount by Dismantle Hammer for copmmon ship, ex: Ro500.");
		kaitaiAmountLarge = config.getInt("Recycle_Large", CATE_GENERAL, 20, 0, 1000, "Recycle amount by Dismantle Hammer for rare ship, ex: Yamato.");
		
		//野生船艦是否主動攻擊玩家
		mobAttackPlayer = config.getBoolean("Attack_Player", CATE_GENERAL, true, "true: ship mob will attack player automatically");
		
		//是否把多金屬當成錳礦
		polyAsMn = config.getBoolean("Polymetal_as_Mn", CATE_GENERAL, false, "true: Polymetallic Nodules = Manganese Dust, Polymetallic Ore = Manganese Ore");
		
		//desk雷達更新間隔
		radarUpdate = config.getInt("Radar_Update", CATE_GENERAL, 128, 20, 6000, "Radar update interval (ticks) in Admiral's Desk GUI");
		
		//是否顯示custom name tag
		showTag = config.getBoolean("NameTag_AlwaysShow", CATE_GENERAL, true, "Always show custom name tag");
		
		//name tag顯示距離
		nameTagDist = config.getInt("NameTag_Distance", CATE_GENERAL, 16, 1, 64, "Show name tag if player get close to ship X blocks");
		
		//ship列表顯示數量
		shipNumPerPage = config.getInt("Command_ShipNum", CATE_GENERAL, 5, 1, 5000, "#Ship per page for command: /ship list");
		
		//team改動cd (ticks)
		teamCooldown = config.getInt("Cooldown_Team", CATE_GENERAL, 6000, 20, 1728000, "Create/Disband Team Cooldown");

		//是否開啟若本語音
		useWakamoto = config.getBoolean("Sound_Wakamoto", CATE_GENERAL, true, "enable Wakamoto sound for mounts");
		
		//畫深海熱漩是否開啟景深
		vortexDepth = config.getBoolean("Depth_HadalVortex", CATE_GENERAL, false, "Enable depth while rendering Hadal Vortex block.");
				
		//野生艦隊生成數量
		spawnBossNum = config.getInt("Spawn_Boss_Number", CATE_GENERAL, 2, 1, 10, "large hostile ship (boss) number per spawn");
		spawnMobNum = config.getInt("Spawn_Mob_Number", CATE_GENERAL, 4, 1, 10, "small hostile ship number per spawn");
		
		//buff debuff TODO not configurable for now
//		buffSaturation = config.getInt("Saturation", CATE_BUFF, 20, 0, 5000, "add X grudge value to ship every 32 ticks");
		
		//是否開啟林業支援
		enableIC2 = config.getBoolean("Mod_IC2", CATE_INTERMOD, true, "Enable IC2 module if mod existed: add EU related function.");
		propDrumEU = config.get(CATE_INTERMOD, "Drum_EU", drumEU, "EU transport rate: base transfer rate (EU/t), additional rate per enchantment (EU/t). Total Rate = (ShipLV * 0.1 + 1) * (BaseRate * #TotalTransformers + EnchantRate * #TotalEnchantments)");
		
		//讀取 ship setting設定
		canFlare = config.getBoolean("Can_Flare", CATE_SHIP, true, "Can ship spawn Flare lighting effect, CLIENT SIDE only");
		canSearchlight = config.getBoolean("Can_Searchlight", CATE_SHIP, true, "Can ship spawn Searchlight lighting effect, CLIENT SIDE only");
		canTeleport = config.getBoolean("Can_Teleport", CATE_SHIP, false, "Can ship teleport to owner/guarding position if too far away. NOTE: set false if ship usually disappear/despawn after teleport!");
		checkRing = config.getBoolean("Check_Ring", CATE_SHIP, true, "Should check wedding ring when spawning NON-BOSS ship mob");
		timeKeeping = config.getBoolean("Can_Timekeeping", CATE_SHIP, true, "Play timekeeping sound every 1000 ticks (1 minecraft hour)");
		volumeTimekeep = config.getFloat("Volume_Timekeeping", CATE_SHIP, 1.0F, 0F, 10F, "Timekeeping sound volume");
		volumeShip = config.getFloat("Volume_Ship", CATE_SHIP, 1.0F, 0F, 10F, "Other sound volume");
		volumeFire = config.getFloat("Volume_Attack", CATE_SHIP, 0.7F, 0F, 10F, "Attack sound volume");
		baseCaressMorale = config.getInt("Caress_BaseMorale", CATE_SHIP, 20, 1, 5000, "base morale value per CaressTick (4 ticks)");
		modernLimit = config.getInt("Attrs_Limit_Modernization", CATE_SHIP, 3, 3, 100, "Max upgrade level by Modernization Toolkit");
		searchlightCD = config.getInt("CD_SearchLight", CATE_SHIP, 4, 1, 256, "Cooldown for placing light block of searchlight");
		
		//array data
		propShip = config.get(CATE_SHIP, "Attrs_Scale", scaleShip, "Ship attributes SCALE: HP, firepower, armor, attack speed, move speed, range");
		propLimitShipAttrs = config.get(CATE_SHIP, "Attrs_Limit", limitShipAttrs, "Ship attributes max limit (-1 = no limit): HP, damage(light), damage(heavy), damage(air_light), damage(air_heavy), armor%, attack speed, move speed, range(blocks), critical, double hit, triple hit, miss reduction, anti-air, anti-ss, dodge, xp gain, grudge gain, ammo gain, hp regen, knockback resist");
		propBossSmall = config.get(CATE_SHIP, "Attrs_Hostile_SmallBoss", scaleBossSmall, "Small boss base attribute values: HP, firepower, armor, attack speed, move speed, range");
		propBossLarge = config.get(CATE_SHIP, "Attrs_Hostile_LargeBoss", scaleBossLarge, "Large boss base attribute values: HP, firepower, armor, attack speed, move speed, range");
		propMobSmall = config.get(CATE_SHIP, "Attrs_Hostile_SmallMob", scaleMobSmall, "Small mob ship like DD and SS base attribute values: HP, firepower, armor, attack speed, move speed, range");
		propMobLarge = config.get(CATE_SHIP, "Attrs_Hostile_LargeMob", scaleMobLarge, "Large mob ship like CL and CA base attribute values: HP, firepower, armor, attack speed, move speed, range");
		propAmmoShip = config.get(CATE_SHIP, "Consume_Ammo", consumeAmmoShip, "Ammo consumption for ship type: DD CL CA CAV CLT CVL CV BB BBV SS AP (MAX = 45)");
		propGrudgeShip = config.get(CATE_SHIP, "Consume_Grudge_Idle", consumeGrudgeShip, "Grudge consumption for ship type: DD CL CA CAV CLT CVL CV BB BBV SS AP (MAX = 120)");
		propGrudgeAction = config.get(CATE_SHIP, "Consume_Grudge_Action", consumeGrudgeAction, "Grudge consumption for ship action: Light attack, Heavy attack, Light aircraft, Heavy aircraft, Moving per block");
		propGrudgeTask = config.get(CATE_SHIP, "Consume_Grudge_Task", consumeGrudgeTask, "Grudge consumption for task: cooking, fishing, mining, crafting");
		propAtkSpd = config.get(CATE_SHIP, "Attack_Base_Speed", baseAttackSpeed, "Base attack speed for: Melee, Light attack, Heavy attack, Carrier attack, Airplane attack, ex: base speed 160, fixed delay 30 means (160 / ship attack speed +30) ticks per attack");
		propAtkDly = config.get(CATE_SHIP, "Attack_Fixed_Delay", fixedAttackDelay, "Fixed attack delay for: Melee, Light attack, Heavy attack, Carrier attack, Airplane attack, ex: base speed 160, fixed delay 30 means (160 / ship attack speed +30) ticks per attack");
		propExp = config.get(CATE_SHIP, "Exp_Gain", expGain, "Exp gain for: Melee, Light Attack, Heavy Attack, Light Aircraft, Heavy Aircraft, Move per Block(AP only), Other Action(AP only)");
		propExpTask = config.get(CATE_SHIP, "Exp_Gain_Task", expGainTask, "Exp gain for task: Cooking, Fishing, Mining, Crafting");
		propMobSpawn = config.get(CATE_SHIP, "Limit_MobSpawnNumber", mobSpawn, "Mob ship spawn MAX number in the world, Spawn prob (roll once per player every 128 ticks), #groups each spawn, #min each group, #max each group");
		propHeldItem = config.get(CATE_SHIP, "Held_Item", scaleHeldItem, "Ship held item scaling: scale, offset X, offset Y, offset Z");
		propDrumLiquid = config.get(CATE_SHIP, "Drum_Liquid", drumLiquid, "liquid transport rate: base transfer rate (mB/t), additional rate per enchantment (mB/t). Total Rate = (ShipLV * 0.1 + 1) * (BaseRate * #TotalPumps + EnchantRate * #TotalEnchantments)");
		propShipTeleport = config.get("ship setting", "ship_teleport", shipTeleport, "Ship teleport when following and guarding: cooldown (ticks), distance (blocks^2)");
		propFishing = config.get(CATE_SHIP, "Tick_Fishing", tickFishing, "Fishing time setting: base, random (ticks)");
		propMining = config.get(CATE_SHIP, "Tick_Mining", tickMining, "Mining time setting: base, random (ticks)");
		propTask = config.get(CATE_SHIP, "Task_Enable", enableTask, "set true to enable the task: cooking, fishing, mining, crafting");
		
		propShipyardSmall = config.get(CATE_GENERAL, "Tile_SmallShipyard", tileShipyardSmall, "Small shipyard: max fuel storage, build speed, fuel magnification");
		propShipyardLarge = config.get(CATE_GENERAL, "Tile_LargeShipyard", tileShipyardLarge, "Large shipyard: max fuel storage, build speed, fuel magnification");
		propVolCore = config.get(CATE_GENERAL, "Tile_VolCore", tileVolCore, "Volcano Core: max fuel storage, fuel consume speed, fuel value per grudge item");
		propCrane = config.get(CATE_GENERAL, "Tile_Crane", tileCrane, "Crane: internal fluid tank capacity (mB), internal energy capacity (EU)");
		propRingAbility = config.get(CATE_GENERAL, "Ring_Ability", ringAbility, "Ring ability related married number, -1 = disable, 0~N = active or max limit number: water breath (active number), fly in water (active number), dig speed boost (max limit number), fog in liquid (max limit number), immune to fire (active number)");
		propInfLiquid = config.get(CATE_GENERAL, "Infinite_Pump", infLiquid, "Can ship pump infinite water or lava without destroying block: min water depth, min lava depth");
		propPosHUD = config.get(CATE_GENERAL, "Position_HUD", posHUD, "HUD position of mounts skills: x, y (0.5D = middle of window)");
		
		//ship vs ship damage modifier
		dmgSvS = config.getInt("DmgTaken_SvS", CATE_SHIP, 100, 0, 10000, "Ship vs Ship damage modifier, 20 = damage * 20% ");
		expMod = config.getInt("EXP_Modifier", CATE_SHIP, 20, 1, 10000, "ship experience modifier, 20 = level 150: 150*20+20 = 3020");
		
		//WORLD GEN
		polyOreBaseRate = config.getInt("Polymetal_Ore", CATE_WORLD, 7, 0, 100, "Polymetallic Ore clusters in one chunk");
		polyGravelBaseRate = config.getInt("Polymetal_Gravel", CATE_WORLD, 4, 0, 100, "Polymetallic Gravel clusters in one chunk");
		propPolyGravel = config.get(CATE_WORLD, "Polymetal_Gravel_Replace", polyGravelBaseBlock, "PolyGravel replaced block: stone, gravel, sand, dirt", true, 4);
		
		//設定新值
		limitShipAttrs = getDoubleArrayFromConfig(limitShipAttrs, propLimitShipAttrs);
		scaleShip = getDoubleArrayFromConfig(scaleShip, propShip);
		scaleBossSmall = getDoubleArrayFromConfig(scaleBossSmall, propBossSmall);
		scaleBossLarge = getDoubleArrayFromConfig(scaleBossLarge, propBossLarge);
		scaleMobSmall = getDoubleArrayFromConfig(scaleMobSmall, propMobSmall);
		scaleMobLarge = getDoubleArrayFromConfig(scaleMobLarge, propMobLarge);
		polyGravelBaseBlock = getBooleanArrayFromConfig(polyGravelBaseBlock, propPolyGravel);
		consumeAmmoShip = getIntArrayFromConfig(consumeAmmoShip, propAmmoShip);
		consumeGrudgeShip = getIntArrayFromConfig(consumeGrudgeShip, propGrudgeShip);
		consumeGrudgeAction = getIntArrayFromConfig(consumeGrudgeAction, propGrudgeAction);
		consumeGrudgeTask = getIntArrayFromConfig(consumeGrudgeTask, propGrudgeTask);
		baseAttackSpeed = getIntArrayFromConfig(baseAttackSpeed, propAtkSpd);
		fixedAttackDelay = getIntArrayFromConfig(fixedAttackDelay, propAtkDly);
		expGain = getIntArrayFromConfig(expGain, propExp);
		expGainTask = getIntArrayFromConfig(expGainTask, propExpTask);
		mobSpawn = getIntArrayFromConfig(mobSpawn, propMobSpawn);
		tileShipyardSmall = getDoubleArrayFromConfig(tileShipyardSmall, propShipyardSmall);
		tileShipyardLarge = getDoubleArrayFromConfig(tileShipyardLarge, propShipyardLarge);
		tileVolCore = getDoubleArrayFromConfig(tileVolCore, propVolCore);
		tileCrane = getIntArrayFromConfig(tileCrane, propCrane);
		ringAbility = getIntArrayFromConfig(ringAbility, propRingAbility);
		scaleHeldItem = getDoubleArrayFromConfig(scaleHeldItem, propHeldItem);
		drumLiquid = getIntArrayFromConfig(drumLiquid, propDrumLiquid);
		drumEU = getIntArrayFromConfig(drumEU, propDrumEU);
		shipTeleport = getIntArrayFromConfig(shipTeleport, propShipTeleport);
		tickFishing = getIntArrayFromConfig(tickFishing, propFishing);
		tickMining = getIntArrayFromConfig(tickMining, propMining);
		enableTask = getBooleanArrayFromConfig(enableTask, propTask);
		posHUD = getDoubleArrayFromConfig(posHUD, propPosHUD);
		
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
			File fileMining = new File(configRootLoc + "mining.cfg");
			
			config = new Configuration(fileMainConfig);
			configSound = new ConfigSound(fileSounds);
			configLoot = new ConfigLoot(fileLootTable);
			configMining = new ConfigMining(fileMining);
			
			/** BOTH SIDE CONFIG */
			loadConfiguration();
			configSound.runConfig();
			configLoot.runConfig();
			configMining.runConfig();
			
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