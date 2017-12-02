package com.lulan.shincolle.reference;

//for array ID
public class ID
{
	
	
	/** ship attributes index */
	public static final class Attrs
	{
		/**
		 * HP: float, 0~N
		 * ATK: float, 1~N
		 * DEF: int, -N~1 (1 = 100%)
		 * SPD: float, 0.1~4
		 * MOV: float, 0~0.6 (blocks per tick)
		 * HIT: int, 1~64 (blocks)
		 * CRI, DHIT, THIT, MISS, DODGE, KB: float, 0.0~1.0 (1 = 100%)
		 * XP, GRUDGE, AMMO, HPRES: float, 0~N (1 = 100%, default = 100%)
		 */
		public static final byte HP = 0;
		public static final byte ATK_L = 1;
		public static final byte ATK_H = 2;
		public static final byte ATK_AL = 3;
		public static final byte ATK_AH = 4;
		public static final byte DEF = 5;
		public static final byte SPD = 6;
		public static final byte MOV = 7;
		public static final byte HIT = 8;
		public static final byte CRI = 9;
		public static final byte DHIT = 10;
		public static final byte THIT = 11;
		public static final byte MISS = 12;
		public static final byte AA = 13;
		public static final byte ASM = 14;
		public static final byte DODGE = 15;
		public static final byte XP = 16;
		public static final byte GRUDGE = 17;
		public static final byte AMMO = 18;
		public static final byte HPRES = 19;
		public static final byte KB = 20;
	}
	
	/** index for bonus point, type modify, scale config */
	public static final class AttrsBase
	{
		public static final byte HP = 0;
		public static final byte ATK = 1;
		public static final byte DEF = 2;
		public static final byte SPD = 3;
		public static final byte MOV = 4;
		public static final byte HIT = 5;
		public static final byte modHP = 6;
		public static final byte modATK = 7;
		public static final byte modDEF = 8;
		public static final byte modSPD = 9;
		public static final byte modMOV = 10;
		public static final byte modHIT = 11;
	}
	
	/**GUI Button ID*/
	public static final class B
	{
		public static final byte ShipInv_Melee = 0;
		public static final byte ShipInv_AmmoLight = 1;
		public static final byte ShipInv_AmmoHeavy = 2;
		public static final byte ShipInv_AirLight = 3;
		public static final byte ShipInv_AirHeavy = 4;
		public static final byte ShipInv_FollowMin = 5;
		public static final byte ShipInv_FollowMax = 6;
		public static final byte ShipInv_FleeHP = 7;
		public static final byte ShipInv_TarAI = 8;
		public static final byte ShipInv_AuraEffect = 9;
		public static final byte ShipInv_OnSightAI = 10;
		public static final byte ShipInv_PVPAI = 11;
		public static final byte ShipInv_AAAI = 12;
		public static final byte ShipInv_ASMAI = 13;
		public static final byte ShipInv_TIMEKEEPAI = 14;
		public static final byte ShipInv_InvPage = 15;
		public static final byte ShipInv_PickitemAI = 16;
		public static final byte ShipInv_WpStay = 17;
		public static final byte ShipInv_ShowHeld = 18;
		public static final byte ShipInv_AutoCR = 19;
		public static final byte ShipInv_AutoPump = 20;
		public static final byte ShipInv_ModelState01 = 21;
		public static final byte ShipInv_ModelState02 = 22;
		public static final byte ShipInv_ModelState03 = 23;
		public static final byte ShipInv_ModelState04 = 24;
		public static final byte ShipInv_ModelState05 = 25;
		public static final byte ShipInv_ModelState06 = 26;
		public static final byte ShipInv_ModelState07 = 27;
		public static final byte ShipInv_ModelState08 = 28;
		public static final byte ShipInv_ModelState09 = 29;
		public static final byte ShipInv_ModelState10 = 30;
		public static final byte ShipInv_ModelState11 = 31;
		public static final byte ShipInv_ModelState12 = 32;
		public static final byte ShipInv_ModelState13 = 33;
		public static final byte ShipInv_ModelState14 = 34;
		public static final byte ShipInv_ModelState15 = 35;
		public static final byte ShipInv_ModelState16 = 36;
		public static final byte ShipInv_Task = 37;
		public static final byte ShipInv_TaskSide = 38;
		
		public static final byte Shipyard_Type = 0;
		public static final byte Shipyard_InvMode = 1;
		public static final byte Shipyard_SelectMat = 2;
		public static final byte Shipyard_INCDEC = 3;
		
		public static final byte Desk_Sync = 0;
		
		public static final byte Crane_Power = 0;
		public static final byte Crane_Mode = 1;
		public static final byte Crane_Meta = 2;
		public static final byte Crane_Dict = 3;
		public static final byte Crane_Load = 4;
		public static final byte Crane_Unload = 5;
		public static final byte Crane_Nbt = 6;
		public static final byte Crane_Red = 7;
		public static final byte Crane_Liquid = 8;
		public static final byte Crane_Energy = 9;
		
		public static final byte VolCore_Power = 0;
	}
	
	/** ship body part */
	public static final class Body
	{
		public static final byte UBelly = 0;
		public static final byte Chest = 1;
		public static final byte Butt = 2;
		public static final byte Neck = 3;
		public static final byte Face = 4;
		public static final byte Back = 5;
		public static final byte Belly = 6;
		public static final byte Top = 7;
		public static final byte Head = 8;
		public static final byte Leg = 9;
		public static final byte Arm = 10;
	}
	
	/** Shipyard build type*/
	public static final class Build
	{
		public static final byte NONE = 0;
		public static final byte SHIP = 1;
		public static final byte EQUIP = 2;
		public static final byte SHIP_LOOP = 3;
		public static final byte EQUIP_LOOP = 4;
	}
	
	/** Emotion / Emotion4 */
	public static final class Emotion
	{
		public static final byte NORMAL = 0;			//no emotion
		public static final byte BLINK = 1;				//blink eye
		public static final byte T_T = 2;				//sad
		public static final byte O_O = 3;				//stare
		public static final byte BORED = 4;				//bored
		public static final byte HUNGRY = 5;			//no grudge
		public static final byte ANGRY = 6;				//angry
		public static final byte SHY = 7;				//shy
		public static final byte XD = 8;				//happy
	}
	
	/** Emotion3 */
	public static final class Emotion3
	{
		public static final byte NORMAL = 0;			//no emotion
		public static final byte CARESS = 1;			//caressed
	}
	
	/** equip raw data array */
	public static final class EquipMisc
	{
		public static final byte EQUIP_TYPE = 0;	//0:none, 1:cannon, 2:misc, 3:aircraft
		public static final byte RARE_TYPE = 1;
		public static final byte RARE_MEAN = 2;
		public static final byte DEVELOP_NUM = 3;
		public static final byte DEVELOP_MAT = 4;
		public static final byte ENCH_TYPE = 5;	//0:none, 1:weapon, 2:armor, 3:misc
	}

	/** Equip type */
	public static final class EquipType
	{
		public static final byte CANNON_SI = 0;			//single cannon
		public static final byte CANNON_TW_LO = 1;		//low level twin cannon
		public static final byte CANNON_TW_HI = 2;		//high level twin cannon
		public static final byte CANNON_TR = 3;			//triple cannon
		public static final byte TORPEDO_LO = 4;		//low level torpedo
		public static final byte TORPEDO_HI = 5;		//high level torpedo
		public static final byte AIR_T_LO = 6;			//low level aircraft T
		public static final byte AIR_T_HI = 7;			//high level aircraft T
		public static final byte AIR_F_LO = 8;			//low level aircraft F
		public static final byte AIR_F_HI = 9;			//high level aircraft F
		public static final byte AIR_B_LO = 10;			//low level aircraft B
		public static final byte AIR_B_HI = 11;			//high level aircraft B
		public static final byte AIR_R_LO = 12;			//low level aircraft R
		public static final byte AIR_R_HI = 13;			//high level aircraft R
		public static final byte RADAR_LO = 14;			//low level radar
		public static final byte RADAR_HI = 15;			//high level radar
		public static final byte TURBINE_LO = 16;		//low level turbine
		public static final byte TURBINE_HI = 17;		//high level turbine
		public static final byte ARMOR_LO = 18;			//low level armor
		public static final byte ARMOR_HI = 19;			//high level armor
		public static final byte GUN_LO = 20;			//low level machine gun
		public static final byte GUN_HI = 21;			//high level machine gun
		public static final byte CATAPULT_LO = 22;		//low level catapult
		public static final byte CATAPULT_HI = 23;		//high level catapult
		public static final byte DRUM_LO = 24;			//low level drum
		public static final byte COMPASS_LO = 25;		//low level compass
		public static final byte FLARE_LO = 26;			//low level flare
		public static final byte SEARCHLIGHT_LO = 27;	//low level searchlight
		public static final byte AMMO_LO = 28;			//low level ammo
		public static final byte AMMO_HI = 29;          //high level ammo
	}
	
	/** Equip Sub ID = item meta value
	 * 
	 *  EquipID = EquipType + EquipSubID * 100
	 * 
	 */
	public static final class EquipSubID
	{
		//cannon
		public static final byte CANNON_SINGLE_5 = 0;
		public static final byte CANNON_SINGLE_6 = 1;
		public static final byte CANNON_TWIN_5 = 2;
		public static final byte CANNON_TWIN_6 = 3;
		public static final byte CANNON_TWIN_5DP = 4;
		public static final byte CANNON_TWIN_125 = 5;
		public static final byte CANNON_TWIN_14 = 6;
		public static final byte CANNON_TWIN_16 = 7;
		public static final byte CANNON_TWIN_20 = 8;
		public static final byte CANNON_TRI_8 = 9;
		public static final byte CANNON_TRI_16 = 10;
		public static final byte CANNON_FG_15 = 11;
		public static final byte CANNON_CG_5 = 12;
		public static final byte CANNON_TWIN_8 = 13;
		public static final byte CANNON_QUAD_15 = 14;
		public static final byte CANNON_TRI_12 = 15;
		//torpedo
		public static final byte TORPEDO_21MK1 = 0;
		public static final byte TORPEDO_21MK2 = 1;
		public static final byte TORPEDO_22MK1 = 2;
		public static final byte TORPEDO_CUTTLEFISH = 3;
		public static final byte TORPEDO_HIGHSPEED = 4;
		public static final byte TORPEDO_HIGHSPEED2 = 5;
		public static final byte TORPEDO_AMB = 6;
		//aircraft
		public static final byte AIRCRAFT_TMK1 = 0;
		public static final byte AIRCRAFT_TMK2 = 1;
		public static final byte AIRCRAFT_TMK3 = 2;
		public static final byte AIRCRAFT_TAVENGER = 3;
		public static final byte AIRCRAFT_FMK1 = 4;
		public static final byte AIRCRAFT_FMK2 = 5;
		public static final byte AIRCRAFT_FMK3 = 6;
		public static final byte AIRCRAFT_FFLYFISH = 7;
		public static final byte AIRCRAFT_FHELLCAT = 8;
		public static final byte AIRCRAFT_BMK1 = 9;
		public static final byte AIRCRAFT_BMK2 = 10;
		public static final byte AIRCRAFT_BFLYFISH = 11;
		public static final byte AIRCRAFT_BHELL = 12;
		public static final byte AIRCRAFT_R = 13;
		public static final byte AIRCRAFT_RFLYFISH = 14;
		public static final byte AIRCRAFT_TAVENGERK = 15;
		public static final byte AIRCRAFT_FHELLCATK = 16;
		public static final byte AIRCRAFT_BHELLK = 17;
		public static final byte AIRCRAFT_FHELLCATB = 18;
		public static final byte AIRCRAFT_BLAND = 19;
		public static final byte AIRCRAFT_BLANDA = 20;
		public static final byte AIRCRAFT_FBC = 21;
		//radar
		public static final byte RADAR_AIRMK1 = 0;
		public static final byte RADAR_AIRMK2 = 1;
		public static final byte RADAR_SURMK1 = 2;
		public static final byte RADAR_SURMK2 = 3;
		public static final byte RADAR_SONAR = 4;
		public static final byte RADAR_AIRABYSS = 5;
		public static final byte RADAR_SURABYSS = 6;
		public static final byte RADAR_SONARMK2 = 7;
		public static final byte RADAR_FCSCIC = 8;
		//turbine
		public static final byte TURBINE = 0;
		public static final byte TURBINE_IMP = 1;
		public static final byte TURBINE_ENH = 2;
		public static final byte TURBINE_GE = 3;
		public static final byte TURBINE_GENEW = 4;
		//armor
		public static final byte ARMOR = 0;
		public static final byte ARMOR_ENH = 1;
		public static final byte ARMOR_ATBS = 2;
		public static final byte ARMOR_ATBM = 3;
		public static final byte ARMOR_ATBL = 4;
		public static final byte ARMOR_ATBA = 5;
		public static final byte ARMOR_APB = 6;
		//machine gun
		public static final byte GUN_HA_3 = 0;
		public static final byte GUN_HA_5 = 1;
		public static final byte GUN_SINGLE_12 = 2;
		public static final byte GUN_SINGLE_20 = 3;
		public static final byte GUN_TWIN_40 = 4;
		public static final byte GUN_QUAD_40 = 5;
		public static final byte GUN_TWIN_4_CIC = 6;
		//catapult
		public static final byte CATAPULT_F = 0;
		public static final byte CATAPULT_H = 1;
		public static final byte CATAPULT_C = 2;
		public static final byte CATAPULT_E = 3;
		//drum
		public static final byte DRUM = 0;		//item drum
		public static final byte DRUM_F = 1;	//fluid drum
		public static final byte DRUM_E = 2;	//EU drum
		//compass
		public static final byte COMPASS = 0;
		//flare
		public static final byte FLARE = 0;
		//searchlight
		public static final byte SEARCHLIGHT = 0;
		//ammo
		public static final byte AMMO_T91 = 0;
		public static final byte AMMO_T1 = 1;
		public static final byte AMMO_AA = 2;
		public static final byte AMMO_T3 = 3;
		public static final byte AMMO_DU = 4;
		public static final byte AMMO_G = 5;
		public static final byte AMMO_AG = 6;
		public static final byte AMMO_E = 6;
	}
	
	/** entity flag index */
	public static final class F
	{
		public static final byte CanFloatUp = 0;
		public static final byte IsMarried = 1;
		public static final byte NoFuel = 2;
		public static final byte UseMelee = 3;
		public static final byte UseAmmoLight = 4;
		public static final byte UseAmmoHeavy = 5;
		public static final byte UseAirLight = 6;
		public static final byte UseAirHeavy = 7;
		public static final byte HeadTilt = 8;			//client only, no sync
		public static final byte UseRingEffect = 9;
		public static final byte CanDrop = 10;			//server only, no sync
		public static final byte CanFollow = 11;
		public static final byte OnSightChase = 12;
		public static final byte AtkType_Light = 13;
		public static final byte AtkType_Heavy = 14;
		public static final byte AtkType_AirLight = 15;
		public static final byte AtkType_AirHeavy = 16;
		public static final byte HaveRingEffect = 17;
		public static final byte PVPFirst = 18;
		public static final byte AntiAir = 19;
		public static final byte AntiSS = 20;
		public static final byte PassiveAI = 21;
		public static final byte TimeKeeper = 22;
		public static final byte PickItem = 23;			//active picking item
		public static final byte CanPickItem = 24;		//can pick item
		public static final byte ShowHeldItem = 25;
		public static final byte AutoPump = 26;
	}
	
	/** GUI ID */
	public static final class Gui
	{
		public static final int SHIPINVENTORY = 0;
		public static final int SMALLSHIPYARD = 1;
		public static final int LARGESHIPYARD = 2;
		public static final int ADMIRALDESK = 3;
		public static final int ADMIRALDESK_ITEM = 4;
		public static final int FORMATION = 5;
		public static final int CRANE = 6;
		public static final int VOLCORE = 7;
		public static final int TASK = 8;
	}
	
	/** icon id for book GUI */
	public static final class Icon
	{
		public static final short IronIG = 0;
		public static final short Grudge = 1;
		public static final short GrudgeB = 2;
		public static final short GrudgeBH = 3;
		public static final short AbyssIG = 4;
		public static final short AbyssB = 5;
		public static final short PolymIG = 6;
		public static final short PolymB = 7;
		public static final short PolymBG = 8;
		public static final short PolymOre = 9;
		public static final short Gunpowder = 10;
		public static final short Blazepowder = 11;
		public static final short AmmoL = 12;
		public static final short AmmoLC = 13;
		public static final short AmmoH = 14;
		public static final short AmmoHC = 15;
		public static final short RpBucket = 16;
		public static final short LaBucket = 17;
		public static final short NStar = 18;
		public static final short Ring = 19;
		public static final short Paper = 20;
		public static final short OwnPaper = 21;
		public static final short Stick = 22;
		public static final short KHammer = 23;
		public static final short ModTool = 24;
		public static final short SpawnEgg0 = 25;
		public static final short SpawnEgg1 = 26;
		public static final short SpawnEgg2 = 27;
		public static final short InstantMat = 28;
		public static final short DiamondB = 29;
		public static final short RpGod = 30;
		public static final short Pointer = 31;
		public static final short ModelZF = 32;
		public static final short Desk = 33;
		public static final short ObsidianB = 34;
		public static final short WoolB = 35;
		public static final short SmallSY = 36;
		public static final short EqCannon0 = 37;
		public static final short EqCannon1 = 38;
		public static final short EqCannon2 = 39;
		public static final short EqMGun = 40;
		public static final short EqCatap = 41;
		public static final short EqRadar = 42;
		public static final short EqTurbine = 43;
		public static final short EqTorpedo = 44;
		public static final short EqAirT = 45;
		public static final short EqAirF = 46;
		public static final short EqAirB = 47;
		public static final short EqAirR = 48;
		public static final short EqArmor = 49;
		public static final short DeskBook = 50;
		public static final short DeskRadar = 51;
		public static final short WriteBook = 52;
		public static final short Compass = 53;
		public static final short Wrench = 54;
		public static final short VolCore = 55;
		public static final short VolBlock = 56;
		public static final short EqDrum = 57;
		public static final short EqCompass = 58;
		public static final short EqFlare = 59;
		public static final short EqSearchlight = 60;
		public static final short Frame = 61;
		public static final short Waypoint = 62;
		public static final short Crane = 63;
		public static final short Piston = 64;
		public static final short TrainBook = 65;
		public static final short MagmaB = 66;
		public static final short Tank0 = 67;
		public static final short Tank1 = 68;
		public static final short Tank2 = 69;
		public static final short Tank3 = 70;
		public static final short Cauldron = 71;
	}
	
	/** hp state index */
	public static final class HPState
	{
		public static final byte NORMAL = 0;	//正常
		public static final byte MINOR = 1;		//小破
		public static final byte MODERATE = 2;	//中破
		public static final byte HEAVY = 3;		//大破
	}
	
	/** minor state index */
	public static final class M
	{
		public static final byte ShipLevel = 0;
		public static final byte Kills = 1;
		public static final byte ExpCurrent = 2;	//exp curr/next
		public static final byte ExpNext = 3;
		public static final byte NumAmmoLight = 4;
		public static final byte NumAmmoHeavy = 5;
		public static final byte NumGrudge = 6;
		public static final byte NumAirLight = 7;
		public static final byte NumAirHeavy = 8;
		public static final byte UseCombatRation = 9;	//morale level of auto using combat ration
		public static final byte FollowMin = 10;	//follow range min/max
		public static final byte FollowMax = 11;	
		public static final byte FleeHP = 12;		//flee hp%
		public static final byte NumState = 13;		//total model state number
		public static final byte GuardX = 14;		//guard xyz pos
		public static final byte GuardY = 15;
		public static final byte GuardZ = 16;
		public static final byte GuardDim = 17;		//guard entity world id
		public static final byte GuardID = 18;		//guard entity id
		public static final byte ShipType = 19;		//ship type
		public static final byte ShipClass = 20;	//ship class
		public static final byte PlayerUID = 21;	//player UID
		public static final byte ShipUID = 22;		//ship UID
		public static final byte PlayerEID = 23;	//player entity id
		public static final byte GuardType = 24;	//guard type: 0:move 1:move & attack
		public static final byte DamageType = 25;	//damage type
		public static final byte FormatType = 26;	//formation type
		public static final byte FormatPos = 27;	//formation position
		public static final byte GrudgeCon = 28;	//grudge consumption when idle
		public static final byte AmmoCon = 29;		//ammo base consumption
		public static final byte Morale = 30;		//morale value
		public static final byte Food = 31;			//food saturation
		public static final byte FoodMax = 32;		//max food saturation
		public static final byte HitHeight = 33;	//hit height by pointer item, NO SYNC
		public static final byte HitAngle = 34;		//hit angle by pointer item, NO SYNC
		public static final byte SensBody = 35;		//sensitive body part id
		public static final byte DrumState = 36;	//drum state, ref: EquipDrum.class
		public static final byte LevelChunkLoader = 37;	//level of chunk loader
		public static final byte LevelFlare = 38;		//level of flare
		public static final byte LevelSearchlight = 39;	//level of searchlight
		public static final byte Task = 40;			//doing task id
		public static final byte TaskSide = 41;		//side setting for task
		public static final byte NO_USE = 42;		//NO_USE
		public static final byte CraneState = 43;	//crane state: 0:none 1:wait 2:craning
		public static final byte WpStay = 44;		//waypoint stay setting
	}
	
	/** morale level */
	public static final class Morale
	{
		/* morale id */
		public static final byte Excited = 0;
		public static final byte Happy = 1;
		public static final byte Normal = 2;
		public static final byte Tired = 3;
		public static final byte Exhausted = 4;
		/* morale level value */
		public static final int L_Excited = 5100;
		public static final int L_Happy = 3900;
		public static final int L_Normal = 2100;
		public static final int L_Tired = 900;
	}
	
	/** packet type ID */
	public static final class Packets
	{
		public static final byte TEST = 0;
		public static final byte S2C_EntitySync = 1;
		public static final byte S2C_Particle = 2;
		public static final byte C2S_GUIInput = 3;
		public static final byte S2C_GUISync = 4;
		public static final byte S2C_PlayerSync = 5;  //no use for now
		public static final byte C2S_CmdInput = 6;
		public static final byte S2C_CmdReact = 7;
	}
	
	/** ring effect, no used for now */
	public static final class R
	{
		public static final byte Haste = 0;
		public static final byte Speed = 1;
		public static final byte Jump = 2;
		public static final byte Damage = 3;
	}
	
	/** entity state array index */
	public static final class S
	{
		public static final byte State = 0;				//model state (1 bit for 1 state; if has mounts, first bit = mounts bit)
		public static final byte Emotion = 1;			//emotion, for face emotion
		public static final byte Emotion2 = 2;			//emotion 2, for head tilt
		public static final byte HPState = 3;			//hp state
		public static final byte NO_USE = 4;			//
		public static final byte Phase = 5;				//skill phase
		public static final byte Emotion3 = 6;			//emotion 3, for caress reaction
		public static final byte Emotion4 = 7;			//emotion 4, for the other pose emotion
	}
	
	/** ship class id */
	public static final class ShipClass
	{
		public static final short DestroyerI = 0;
		public static final short DestroyerRO = 1;
		public static final short DestroyerHA = 2;
		public static final short DestroyerNI = 3;
		
		public static final short LightCruiserHO = 4;
		public static final short LightCruiserHE = 5;
		public static final short LightCruiserTO = 6;
		public static final short LightCruiserTSU = 7;
		
		public static final short TorpedoCruiserCHI = 8;
		public static final short HeavyCruiserRI = 9;
		public static final short HeavyCruiserNE = 10;
		
		public static final short LightCarrierNU = 11;
		public static final short CarrierWO = 12;
		
		public static final short BattleshipRU = 13;
		public static final short BattleshipTA = 14;
		public static final short BattleshipRE = 15;
		
		public static final short TransportWA = 16;
		public static final short SubmarineKA = 17;
		public static final short SubmarineYO = 18;
		public static final short SubmarineSO = 19;
		
		public static final short CarrierHime = 20;
		public static final short AirfieldHime = 21;
		public static final short ArmoredCarrierHime = 22;
		public static final short AnchorageHime = 23;
		public static final short HarbourWD = 24;
		public static final short AnchorageWD = 25;
		public static final short BattleshipHime = 26;
		public static final short DestroyerHime = 27;
		public static final short HarbourHime = 28;
		public static final short IsolatedHime = 29;
		public static final short MidwayHime = 30;
		public static final short NorthernHime = 31;
		public static final short SouthernHime = 32;
		public static final short CarrierWD = 33;
		public static final short LightCruiserDemon = 34;
		public static final short BattleshipWD = 35;
		
		public static final short DestroyerShimakaze = 36;
		public static final short BattleshipNagato = 37;
		public static final short SubmarineU511 = 38;
		public static final short SubmarineRo500 = 39;
		
		public static final short SeaplaneHime = 40;
		public static final short AirdefenseHime = 41;
		public static final short PTImp = 42;
		public static final short LightCruiserHime = 43;
		public static final short SubmarineHime = 44;
		public static final short DestroyerWD = 45;
		
		public static final short BattleshipYamato = 46;
		public static final short CarrierKaga = 47;
		public static final short CarrierAkagi = 48;
		
		public static final short HeavyCruiserHime = 49;
		public static final short SupplyDepotHime = 50;
		
		public static final short DestroyerAkatsuki = 51;
		public static final short DestroyerHibiki = 52;
		public static final short DestroyerIkazuchi = 53;
		public static final short DestroyerInazuma = 54;
		public static final short Raiden = 55;
		
		public static final short LightCruiserTenryuu = 56;
		public static final short LightCruiserTatsuta = 57;
		public static final short HeavyCruiserAtago = 58;
		public static final short HeavyCruiserTakao = 59;
		
		public static final short BattleshipKongou = 60;
		public static final short BattleshipHiei = 61;
		public static final short BattleshipHaruna = 62;
		public static final short BattleshipKirishima = 63;
	}
	
	/** ship misc entity id for renderer */
	public static final class ShipMisc
	{
		public static final short Invisible = -1;
		public static final short AbyssalMissile = 0;
		public static final short Airplane = 1;
		public static final short AirplaneT = 2;
		public static final short AirplaneTako = 3;
		public static final short AirplaneZero = 4;
		public static final short FloatingFort = 5;
		public static final short Rensouhou = 6;
		public static final short RensouhouS = 7;
		public static final short AirfieldMount = 8;
		public static final short BattleshipMount = 9;
		public static final short CarrierWDMount = 10;
		public static final short HarbourMount = 11;
		public static final short CarrierMount = 12;
		public static final short IsloatedMount = 13;
		public static final short MidwayMount = 14;
	}
	
	/** ship type for damage calc */
	public static final class ShipDmgType
	{
		public static final byte UNDEFINED = 0;			//未定義
		public static final byte CARRIER = 1;			//航母
		public static final byte AVIATION = 2;			//航戰
		public static final byte BATTLESHIP	= 3;		//戰艦
		public static final byte CRUISER = 4;			//巡洋
		public static final byte DESTROYER = 5;			//驅逐
		public static final byte SUBMARINE = 6;			//潛艇
		public static final byte AIRPLANE = 7;			//飛機
	}
	
	/** ship type for GUI display */
	public static final class ShipType
	{
		public static final byte DESTROYER = -1;		//DD
		public static final byte LIGHT_CRUISER = 1;		//CL
		public static final byte HEAVY_CRUISER = 2;		//CA CAV
		public static final byte TORPEDO_CRUISER = 3;	//CLT
		public static final byte LIGHT_CARRIER = 4;		//CVL
		public static final byte STANDARD_CARRIER = 5;	//CV
		public static final byte BATTLESHIP	= 6;		//BB BBV
		public static final byte TRANSPORT = 7;			//AR AO
		public static final byte SUBMARINE = 8;			//SS
		public static final byte DEMON = 9;				//demon + water demon (De)
		public static final byte HIME = 10;				//princess (Pr)
	}
	
	/** consumption type and action index */
	public static final class ShipConsume
	{
		/** ship type */
		public static final byte DD = 0;
		public static final byte CL = 1;
		public static final byte CA = 2;
		public static final byte CAV = 3;
		public static final byte CLT = 4;
		public static final byte CVL = 5;
		public static final byte CV	= 6;
		public static final byte BB = 7;
		public static final byte BBV = 8;
		public static final byte SS = 9;
		public static final byte AP = 10;
		/** ship action */
		public static final byte LAtk = 0;
		public static final byte HAtk = 1;
		public static final byte LAir = 2;
		public static final byte HAir = 3;
		public static final byte Move = 4;
	}
	
	/** Timer Array */
	public static final class T
	{
		public static final byte RevengeTime = 0;		//SERVER: revenge target time
		public static final byte CraneTime = 1;			//BOTH:   craning time
		public static final byte ImmuneTime = 2;		//SERVER: immune time
		public static final byte CrandDelay = 3;		//SERVER: crane state changing delay
		public static final byte WpStayTime = 4;		//SERVER: waypoint stay timer
		public static final byte Emotion3Time = 5;		//SERVER: emotion 3 tick
		public static final byte SoundTime = 6;			//SERVER: sound event cooldown
		public static final byte FaceTime = 7;			//CLIENT: face emotion time
		public static final byte HeadTilt = 8;			//CLIENT: head tilt time
		public static final byte NO_USE = 9;			//no use
		public static final byte EmoteDelay = 10;		//SERVER: emote reaction delay
		public static final byte LastCombat = 11;		//SERVER: last combat time
		public static final byte AttackTime = 12;		//CLIENT: attack time for model display
		public static final byte AttackTime2 = 13;		//CLIENT: attack time 2 for model display
		public static final byte AttackTime3 = 14;		//SERVER: attack time 3 for skill AI
		public static final byte TaskTime = 15;			//SERVER: task start time
		public static final byte MountSkillCD1 = 16;	//BOTH:   mount skill cooldwon 1~4
		public static final byte MountSkillCD2 = 17;
		public static final byte MountSkillCD3 = 18;
		public static final byte MountSkillCD4 = 19;
	}
	
	/** Update Flag */
	public static final class FlagUpdate
	{
		public static final byte FormationBuff = 0;
		public static final byte AttrsBuffed = 1;
		public static final byte AttrsBonus = 2;
		public static final byte AttrsEquip = 3;
		public static final byte AttrsMorale = 4;
		public static final byte AttrsPotion = 5;
		public static final byte AttrsFormation = 6;
		public static final byte AttrsRaw = 7;
	}
	
	
}